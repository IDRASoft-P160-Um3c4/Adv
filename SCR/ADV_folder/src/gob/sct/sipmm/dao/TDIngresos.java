package gob.sct.sipmm.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.micper.ingsw.TParametro;

public class TDIngresos {
	private static TParametro vParametros = new TParametro("44");
	private static String dataSourceName = vParametros.getPropEspecifica("IngresosDS");
	
	public static TDIngresosResult ConsultaUsuarioIngresos(String username, String password){
		  Vector vcListIng = new Vector();
		  
		  String cError = "";
		  ResultSet rset =null;
		  Connection conn = null;
		  DataSource dataSource;
		  PreparedStatement lpst = null;
		 
		  try{			  
			  ////System.out.println("---- INICIA CONEXION DIRECTA A INGRESOS ------");			  
			  InitialContext iCtx = new InitialContext();
			  dataSource = (javax.sql.DataSource) iCtx.lookup(dataSourceName);
			  conn = dataSource.getConnection();
			  conn.setAutoCommit(false);
		      conn.setTransactionIsolation(2); 
				  		  
			  String cSQLIng = "SELECT ICVEUSUARIO FROM SEGUSUARIO WHERE CUSUARIO = '"+ username +"' AND CPASSWORD='"+ password + "'";
			  lpst = conn.prepareStatement(cSQLIng);
		      rset = lpst.executeQuery();
		      
		      TDConsulta objConsulta = new TDConsulta();		      
		      vcListIng = objConsulta.getVO("",rset);
			  
		  } catch(SQLException ex){
			   	System.out.println("INICIA ERROR: "+ex.getMessage());
			    cError="validaUsrIngADVErr";
			    StringWriter sw = new StringWriter();
			    ex.printStackTrace(new PrintWriter(sw));
			    String msg  = sw.toString();
			    System.out.print("SQLEXCEPTION: " + msg);
			   
	      } catch(Exception ex){
				System.out.println("INICIA ERROR 2: "+ex.getMessage());
				StringWriter sw = new StringWriter();
				ex.printStackTrace(new PrintWriter(sw));
				String msg  = sw.toString();
				System.out.print("EXCEPTION: " + msg);
	      } finally{
	        try{
	          if(rset != null){
	            rset.close();
	          }
	          if(lpst != null){
	        	  lpst.close();
	          }
	          if(conn != null){
	            conn.close();
	          }
	        } catch(Exception ex2){
	        	System.out.println("INICIA ERROR 3: "+ex2.getMessage());
	        	 StringWriter sw = new StringWriter();
	     	    ex2.printStackTrace(new PrintWriter(sw));
	     	    String msg  = sw.toString();
	     	    System.out.print("EXCEPTION 3: " + msg);
	        }
	     }
		 
		 return new TDIngresosResult(cError, vcListIng);
	}	
}

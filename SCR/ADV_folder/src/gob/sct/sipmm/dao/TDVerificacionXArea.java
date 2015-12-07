package gob.sct.sipmm.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDTRARegSolicitud.java</p>
 * <p>Description: DAO de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDVerificacionXArea extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepción de tipo DAO
   * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
   */
  public Vector findByCustom(String cKey,String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    java.sql.Date dtNulo = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null, lPStmt2 = null;;
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    
    try{
    	
    	
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      
      
      //Integer consec;
      
      /*
      Vector vcMax= new Vector();
      vcMax=findByCustom("", "select max(ICVEEVALXAREA) as CONSEC from TRAREGEVAREQXAREA");
      
      if(vcMax.size() > 0 )
          consec = ( (TVDinRep) vcMax.get(0)).getInt("CONSEC") + 1;
        else
          consec = 1;
      
      String lSQL =
              "insert into TRAREGEVAREQXAREA " +
              "(ICVEEVALXAREA," +
              "IEJERCICIO," +
              "INUMSOLICITUD," +
              "ICVETRAMITE," +
              "ICVEMODALIDAD," +
              "ICVEREQUISITO," +
              "ICVEUSUARIO,"+
              "LVALIDO," +
              "ICONSECUTIVOPNC," +
              "DTEVALUACION)" +
              " values (?,?,?,?,?,?,?,?,?,(current_date))";
	  */
      
      //System.out.println(vData.getInt("iCveUs"));
      
      String upRegEv = "UPDATE TRAREGEVAREQXAREA SET ICVEUSUARIO="+vData.getInt("iCveUs")+","+
      				   "LVALIDO="+vData.getInt("lValido")+", DTEVALUACION=CURRENT_DATE WHERE " +
      		           "IEJERCICIO="+vData.getInt("iEjercicio")+" AND INUMSOLICITUD="+vData.getInt("iNumSolicitud")+" AND ICVEREQUISITO="+vData.getInt("iCveRequisito");
      
      //System.out.println(upRegEv);
      
      lPStmt = conn.prepareStatement(upRegEv);
      /*
      vData.addPK(consec);
      
      lPStmt.setInt(1,consec.intValue());
      lPStmt.setInt(2,v Data.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(4,vData.getInt("iCveTramite"));
      lPStmt.setInt(5,vData.getInt("iCveModalidad"));
      lPStmt.setInt(6,vData.getInt("iCveRequisito"));    
      lPStmt.setInt(7,vData.getInt("iCveUs"));
      lPStmt.setInt(8,vData.getInt("lValido"));
      lPStmt.setNull(9,Types.INTEGER);
      */
      try{
			lPStmt.executeUpdate();
			lPStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
      
      String lSqlUpdateTraRegReqXTram ="update TRAREGREQXTRAM set LTIENEPNC= 0, LVALIDO= 1, DTEVALUACION = CURRENT_DATE"+
      		" where IEJERCICIO=" + vData.getInt("iEjercicio") +
      		" and INUMSOLICITUD=" + vData.getInt("iNumSolicitud")+
      		" and ICVETRAMITE=" + vData.getInt("iCveTramite") +
      		" and ICVEMODALIDAD="+vData.getInt("iCveModalidad")+
      		" and ICVEREQUISITO="+vData.getInt("iCveRequisito");
      
      lPStmt=null;
      
      lPStmt=conn.prepareStatement(lSqlUpdateTraRegReqXTram);
      lPStmt.executeUpdate();
      
      if(cnNested == null){
        conn.commit();
      }
      
    } catch(Exception ex){
      ex.printStackTrace();
      warn("insert",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");
      return vData;
    }
  }
  
  
  
  
  
 public TVDinRep insertMultCausas(TVDinRep vData,Connection cnNested) throws
  DAOException{

DbConnection dbConn = null;
Connection conn = cnNested;
PreparedStatement lPStmt2 = null;
boolean lSuccess = true;
boolean lInsertaCausa = true;
try{
  if(cnNested == null){
    dbConn = new DbConnection(dataSourceName);
    conn = dbConn.getConnection();
    conn.setAutoCommit(false);
    conn.setTransactionIsolation(2);
  }
  
 
  Integer icveRegEva=0;
  
  Vector vCveRegEva= new Vector();
  vCveRegEva=findByCustom("", "select ICVEEVALXAREA from TRAREGEVAREQXAREA where iejercicio="+vData.getInt("iEjer")
		  					  +" and inumsolicitud="+vData.getInt("iNumSol")
		                      +" and ICVEREQUISITO="+vData.getInt("iCveRequi"));
  
  if(vCveRegEva.size() > 0 )
	  icveRegEva = ( (TVDinRep) vCveRegEva.get(0)).getInt("ICVEEVALXAREA");

  
  String upRegEv = "UPDATE TRAREGEVAREQXAREA SET ICVEUSUARIO="+vData.getInt("iCveUs")+","+
			   "LVALIDO=0, DTEVALUACION=CURRENT_DATE WHERE " +
	           "IEJERCICIO="+vData.getInt("iEjer")+" AND INUMSOLICITUD="+vData.getInt("iNumSol")+" AND ICVEREQUISITO="+vData.getInt("iCveRequi");

  lPStmt2 = conn.prepareStatement(upRegEv);

  /*
  String lSQL2 =
          "insert into TRAREGEVAREQXAREA " +
          "(ICVEEVALXAREA," +
          "IEJERCICIO," +
          "INUMSOLICITUD," +
          "ICVETRAMITE," +
          "ICVEMODALIDAD," +
          "ICVEREQUISITO," +
          "ICVEUSUARIO,"+
          "LVALIDO," +
          "ICONSECUTIVOPNC," +
          "DTEVALUACION)" +
          " values (?,?,?,?,?,?,?,?,?,(current date))";

  lPStmt2 = conn.prepareStatement(lSQL2);
  /*
  vData.addPK(consec);
  
  lPStmt2.setInt(1,consec.intValue());
  lPStmt2.setInt(2,vData.getInt("iEjer"));
  lPStmt2.setInt(3,vData.getInt("iNumSol"));
  lPStmt2.setInt(4,vData.getInt("iCveTram"));
  lPStmt2.setInt(5,vData.getInt("iCveModal"));
  lPStmt2.setInt(6,vData.getInt("iCveRequi"));    
  lPStmt2.setInt(7,vData.getInt("CveUser"));
  lPStmt2.setInt(8,0);
  lPStmt2.setNull(9,Types.INTEGER);
  */
  try {
		lPStmt2.executeUpdate();
		lPStmt2.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
  
  
  /***********************************/
  
  String lSqlUpTraRegReqXTram ="update TRAREGREQXTRAM set LVALIDO= 0, DTEVALUACION = CURRENT_DATE"+
    		" where IEJERCICIO=" + vData.getInt("iEjer") +
    		" and INUMSOLICITUD=" + vData.getInt("iNumSol")+
    		" and ICVETRAMITE=" + vData.getInt("iCveTram") +
    		" and ICVEMODALIDAD="+vData.getInt("iCveModal")+
    		" and ICVEREQUISITO="+vData.getInt("iCveRequi");
  
  
  lPStmt2=null;
  lPStmt2 = conn.prepareStatement(lSqlUpTraRegReqXTram);
 
  try {
		lPStmt2.executeUpdate();
		lPStmt2.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
  
  

  /************************************/
  
  
  String lSQL2 = "INSERT INTO TRAREGCAUSASXAREA " +
  		"(ICVECAUSAXAREA," +
  		"ICVEEVALXAREA," +
  		"ICVEPROCESO," +
  		"ICVEPRODUCTO," +
  		"ICVECAUSAPNC," +
  		"CDSCOTRACAUSA)"+
  		"VALUES (?,?,?,?,?,?)";
          
  lPStmt2=null;
  lPStmt2 = conn.prepareStatement(lSQL2);

  String [] strCveCausaPNC = vData.getString("iCveCausaPNC").split(",");

  for(int i=0; i< strCveCausaPNC.length ;i++){
	  
	  Vector vcMaxCausa;
	  
	  vcMaxCausa=findByCustom("", "select max(ICVECAUSAXAREA) as CONSEC from TRAREGCAUSASXAREA");
	  
	  Integer conseCausas;
	  

	  
	  if(vcMaxCausa.size() > 0 )
	      conseCausas = ( (TVDinRep) vcMaxCausa.get(0)).getInt("CONSEC") + 1;
	    else
	      conseCausas = 1;
	  
      
      vData.addPK(conseCausas);
      
      lPStmt2.setInt(1,conseCausas.intValue());
      lPStmt2.setInt(2,icveRegEva.intValue());
      lPStmt2.setInt(3,vData.getInt("iCveProceso"));
      lPStmt2.setInt(4,vData.getInt("iCveProducto"));
      lPStmt2.setInt(5,Integer.parseInt(strCveCausaPNC[i]));
      lPStmt2.setString(6, vData.getString("cDscOtraCausa"));
 
      lPStmt2.executeUpdate();
      
      if(cnNested == null){
    	    conn.commit();
    	  }
      
  }

} catch(Exception ex){
  warn("insert",ex);
  if(cnNested == null){
    try{
      conn.rollback();
    } catch(Exception e){
      fatal("insert.rollback",e);
    }
  }
  lSuccess = false;
} finally{
  try{
    if(lPStmt2 != null){
      lPStmt2.close();
    }
    if(cnNested == null){
      if(conn != null){
        conn.close();
      }
      dbConn.closeConnection();
    }
  } catch(Exception ex2){
    warn("insert.close",ex2);
  }
  if(lSuccess == false)
    throw new DAOException("");
  return vData;
}
}

  
 
 
 public TVDinRep updTerminoArea(TVDinRep vData,Connection cnNested) throws
 DAOException{

DbConnection dbConn = null;
Connection conn = cnNested;
PreparedStatement lPStmt = null;
boolean lSuccess = true;


try{
 if(cnNested == null){
   dbConn = new DbConnection(dataSourceName);
   conn = dbConn.getConnection();
   conn.setAutoCommit(false);
   conn.setTransactionIsolation(2);
 }
   
 Vector vcDptoUsr;
 vcDptoUsr=findByCustom("", "SELECT ICVEDEPARTAMENTO FROM GRLUSUARIOXOFIC WHERE ICVEUSUARIO =" + vData.getInt("iCveUs"));
 
 String lSQL="";
  
 if ( ((TVDinRep)vcDptoUsr.get(0)).getInt("ICVEDEPARTAMENTO") == Integer.parseInt(VParametros.getPropEspecifica("TECNICO")) )
   lSQL= "UPDATE TRAREGSOLICITUD SET LTECNICO = 1 WHERE IEJERCICIO="+ vData.getInt("iEjercicio") +" AND INUMSOLICITUD="+ vData.getInt("iNumSolicitud");
 else if ( ((TVDinRep)vcDptoUsr.get(0)).getInt("ICVEDEPARTAMENTO") == Integer.parseInt(VParametros.getPropEspecifica("JURIDICO")) )
   lSQL= "UPDATE TRAREGSOLICITUD SET LJURIDICO = 1 WHERE IEJERCICIO="+ vData.getInt("iEjercicio") +" AND INUMSOLICITUD="+ vData.getInt("iNumSolicitud");
 
 lPStmt = conn.prepareStatement(lSQL);
 
 lPStmt.executeUpdate();
 
      if(cnNested == null){
   	    conn.commit();
   	  }

} catch(Exception ex){
 warn("insert",ex);
 if(cnNested == null){
   try{
     conn.rollback();
   } catch(Exception e){
     fatal("insert.rollback",e);
   }
 }
 lSuccess = false;
} finally{
 try{
   if(lPStmt != null){
     lPStmt.close();
   }
   if(cnNested == null){
     if(conn != null){
       conn.close();
     }
     dbConn.closeConnection();
   }
 } catch(Exception ex2){
   warn("insert.close",ex2);
 }
 if(lSuccess == false)
   throw new DAOException("");
 return vData;
}
}
 

}
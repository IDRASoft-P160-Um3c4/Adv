package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.poi.ss.formula.functions.Vlookup;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDGRLRegPNC.java</p>
 * <p>Description: DAO de la entidad GRLRegistroPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Levi Equihua
 * @version 1.0
 */
public class TDGRLRegPNC extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas tFecha = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private int iConsecutivo;
  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepci�n de tipo DAO
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
   * <p><b> insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - VO Din�mico que contiene a la entidad a Insertada, as� como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    TFechas dtFechaActual = new TFechas();

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado,iCveOficina,iCveDepartamento,iCveProceso) values (?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " + vData.getInt("iEjercicio"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
      }else
         vData.put("iConsecutivoPNC",1);
      vData.addPK(vData.getString("iConsecutivoPNC"));
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,dtFechaActual.getIntYear(dtFechaActual.TodaySQL()));
        lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
        iConsecutivo = vData.getInt("iConsecutivoPNC");
        lPStmt.setDate(3,tFecha.TodaySQL());
        lPStmt.setInt(4,vData.getInt("iCveUsuario"));
        lPStmt.setInt(5,vData.getInt("iCveProducto"));
        lPStmt.setInt(6,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(7,Types.DATE);
        else
          lPStmt.setDate(7,vData.getDate("dtResolucion"));
        lPStmt.setInt(8,vData.getInt("iCveOficinaUsrAsg"));
        lPStmt.setInt(9,vData.getInt("iCveDeptoUsrAsg"));
        lPStmt.setInt(10,vData.getInt("iCveOficinaUsr"));
        lPStmt.setInt(11,vData.getInt("iCveDeptoUsr"));
        lPStmt.setInt(12,vData.getInt("iCveProceso"));

        lPStmt.executeUpdate();
        lPStmt.close();
        try{
          TDGRLRegCausaPNC1 dGRLRegCausaPNC = new TDGRLRegCausaPNC1();
          dGRLRegCausaPNC.insert(vData,conn,iConsecutivo);
        }catch(Exception exCausa){
          exCausa.printStackTrace();
          lSuccess = false;
          throw new Exception("Error Causa");
        }
        if(cnNested == null && lSuccess)
          conn.commit();
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
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


  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - VO Din�mico que contiene a la entidad a Insertada, as� como a la llave de esta entidad.
   */
   
  public TVDinRep insertExiste(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    boolean lAgregarAPNC = false;
    boolean lInsertaCausa = false;
    TFechas dtFechaActual = new TFechas();
    int iEjercicio,iNumSolicitud, iCveTramite, iCveModalidad,iCveUsuario = 0;
    int iConsecutivoPNC = 0;
    boolean tecnico=false;
    boolean juridico=false;
    
    try{
    	
    	
    	iEjercicio=vData.getInt("iEjercicio");
    	iNumSolicitud=vData.getInt("iNumSolicitud");
    	iCveTramite=vData.getInt("iCveTramite");
    	iCveModalidad=vData.getInt("iCveModalidad");
    	iCveUsuario=vData.getInt("iCveUsuario");
    	
    	System.out.print(vData.getInt("iCveOficina"));
    	System.out.print(vData.getInt("iCveDepartamento"));

      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      
      
	        Vector vcData = findByCustom("",
	                                     "select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " +
	                                     iEjercicio);
	        if(vcData.size() > 0){
	          TVDinRep vUltimo = (TVDinRep) vcData.get(0);
	          vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
	        } else
	          vData.put("iConsecutivoPNC",1);
      
  	String lSqlEval= "SELECT ICVEEVALXAREA,ICVEREQUISITO,LVALIDO,ICONSECUTIVOPNC FROM TRAREGEVAREQXAREA where"+
      	  " IEJERCICIO="+iEjercicio+
      	  " AND INUMSOLICITUD="+iNumSolicitud+
      	  " AND ICVETRAMITE="+iCveTramite+
      	  " AND ICVEMODALIDAD="+iCveModalidad+
      	  //" AND ICONSECUTIVOPNC IS NULL AND LVALIDO = 0";
      	  " AND LVALIDO = 0";
      	
      	Vector vcDatosEvalXArea = new Vector();
      	
      	vcDatosEvalXArea = findByCustom("", lSqlEval);//todas las evaluaciones de los requisitos de esa solicitud
     
      
       TVDinRep vDatosPrimerEvalNoValido = new TVDinRep();
       
       if(vcDatosEvalXArea.size() > 0)
    	   vDatosPrimerEvalNoValido = (TVDinRep) vcDatosEvalXArea.get(0); //saco la primera evaluacion no valida de la solicitud 
       
       Vector vcCausasXEval= new Vector();
       
       String lSqlCausaxEva = "SELECT " +
       		"ICVECAUSAXAREA," +
       		"ICVEEVALXAREA," +
       		"ICVEPROCESO," +
       		"ICVEPRODUCTO," +
       		"ICVECAUSAPNC," +
       		"CDSCOTRACAUSA " +
       		"FROM TRAREGCAUSASXAREA where ICVEEVALXAREA="+vDatosPrimerEvalNoValido.getInt("ICVEEVALXAREA"); //busco todas las causas asociadas a esa primera evaluacion no valida
      
       vcCausasXEval = findByCustom("", lSqlCausaxEva);//busco todas las causas asociadas a esa primera evaluacion no valida
       
       TVDinRep vDatosPrimeraCausaXPrimeraEval = new TVDinRep();
       
       if(vcDatosEvalXArea.size() > 0)
    	   vDatosPrimeraCausaXPrimeraEval = (TVDinRep) vcCausasXEval.get(0); //obtengo la primera causa
       
       
       //del requisito de la primera evaluacion obtengo la oficina y el dpto de quien lo reviso
       int iCvePrimerRequisito = vDatosPrimerEvalNoValido.getInt("ICVEREQUISITO");
       
       String lSqlOficinaDptoxReq = "SELECT " +
       		"ICVEOFICINAEVAL," +
       		"ICVEDEPTOEVAL " +
       		"FROM TRAREQUISITO WHERE ICVEREQUISITO="+iCvePrimerRequisito;
       
 
       Vector vOficDptoXReq= new Vector();
       vOficDptoXReq = findByCustom("", lSqlOficinaDptoxReq); //OBTENGO LA OFICINA Y EL DPTO ASOCIADOS AL REQUISITO

       TVDinRep vDatosOficDptoXReq = new TVDinRep();
       
       if(vOficDptoXReq.size()>0){
    	   vDatosOficDptoXReq= (TVDinRep) vOficDptoXReq.get(0);//OBTENGO LA OFICINA Y EL DPTO ASOCIADOS AL REQUISITO
       }
       
       
       TVDinRep vDatosFinalesPNC= new TVDinRep();
       
       vDatosFinalesPNC.put("iEjercicio", iEjercicio);
       vDatosFinalesPNC.put("iConsecutivoPNC", vData.getInt("iConsecutivoPNC"));
       vDatosFinalesPNC.put("dtRegistro", tFecha.TodaySQL());
       vDatosFinalesPNC.put("iCveUsuario", vData.getInt("iCveUsuario"));
       vDatosFinalesPNC.put("iCveProducto", vDatosPrimeraCausaXPrimeraEval.get("ICVEPRODUCTO"));
       vDatosFinalesPNC.put("lResuelto", vData.getInt("lResuelto"));
       vDatosFinalesPNC.put("dtResolucion", vData.getInt("lResuelto"));
       vDatosFinalesPNC.put("iCveOficina", vDatosOficDptoXReq.getInt("ICVEOFICINAEVAL"));
       vDatosFinalesPNC.put("iCveDepartamento", vDatosOficDptoXReq.getInt("ICVEDEPTOEVAL"));
       
       vDatosFinalesPNC.put("iCveProceso", vDatosPrimeraCausaXPrimeraEval.get("ICVEPROCESO"));
       
       
       vDatosFinalesPNC.addPK(vData.getString("iConsecutivoPNC"));
       
       String lSQL =
    	          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado,iCveOficina,iCveDepartamento,iCveProceso,iNumSolicitud,dtoficio) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

       lPStmt = conn.prepareStatement(lSQL);
        
        lPStmt.setInt(1,vDatosFinalesPNC.getInt("iEjercicio"));
        lPStmt.setInt(2,vDatosFinalesPNC.getInt("iConsecutivoPNC"));
        lPStmt.setDate(3,vDatosFinalesPNC.getDate("dtRegistro"));
        lPStmt.setInt(4,vDatosFinalesPNC.getInt("iCveUsuario"));
        lPStmt.setInt(5,vDatosFinalesPNC.getInt("iCveProducto"));
        lPStmt.setInt(6,vDatosFinalesPNC.getInt("lResuelto"));
        
        if(vData.getDate("dtResolucion") == null)
            lPStmt.setNull(7,Types.DATE);
          else
            lPStmt.setDate(7,vData.getDate("dtResolucion"));
        
        lPStmt.setInt(8,vData.getInt("iCveOficina"));
        lPStmt.setInt(9,vData.getInt("iCveDepartamento"));
        lPStmt.setInt(10,vDatosFinalesPNC.getInt("iCveOficina"));
        lPStmt.setInt(11,vDatosFinalesPNC.getInt("iCveDepartamento"));
        lPStmt.setInt(12,vDatosFinalesPNC.getInt("iCveProceso"));
        lPStmt.setInt(13,iNumSolicitud);
        lPStmt.setDate(14,new java.sql.Date(new Date().getTime()));//para actualizar dtoficio en el pnc para que aparezcan en la notificacion del pnc
        
//        System.out.print("of---> "+vData.getInt("iCveOficina"));
//        System.out.print("dpto---> "+vData.getInt("iCveDepartamento"));
//        
//        System.out.print("ejer---> "+vDatosFinalesPNC.getInt("iEjercicio"));
//        System.out.print("PNC---> "+vDatosFinalesPNC.getInt("iConsecutivoPNC"));
//        System.out.print("ofPROD---> "+vDatosFinalesPNC.getInt("iCveOficina"));
//        System.out.print("dptoPROD---> "+vDatosFinalesPNC.getInt("iCveDepartamento"));
//        System.out.print("proc- " + vDatosFinalesPNC.getInt("iCveProceso") + " prod- " + vDatosFinalesPNC.getInt("iCveProducto"));
        
           lPStmt.executeUpdate();  //hago el registro en la tabla de GRLRegistroPNC
           
           
        List<String> cadLis =  new ArrayList<String>();
        String cadActual="";
        		
    	for (int i=0;i < vcDatosEvalXArea.size();i++) {
    		
    		TVDinRep vReqAct = (TVDinRep) vcDatosEvalXArea.get(i);
    		           
            String lSqlCausasxReq = "SELECT " +
            		"ICVECAUSAXAREA," +
            		"ICVEEVALXAREA," +
            		"ICVEPROCESO," +
            		"ICVEPRODUCTO," +
            		"ICVECAUSAPNC," +
            		"CDSCOTRACAUSA " +
            		"FROM TRAREGCAUSASXAREA where ICVEEVALXAREA="+vReqAct.getInt("ICVEEVALXAREA"); //busco todas las causas asociadas a ese rquisito evaluado
           
    		Vector vcCausasXReq = new Vector();
            vcCausasXReq = findByCustom("", lSqlCausasxReq);
            
            if(vcCausasXReq.size() > 0){
	            
            	for(int j=0;j < vcCausasXReq.size(); j++){
	            	
	        		TVDinRep causaActXReq = (TVDinRep) vcCausasXReq.get(j);
	            	
	        		
	        		String lSqlGRLCausaPNC = "INSERT INTO GRLREGCAUSAPNC " +
	        				"(IEJERCICIO," +
	        				"ICONSECUTIVOPNC," +
	        				"ICVEPRODUCTO," +
	        				"ICVECAUSAPNC," +
	        				"ICVEUSUCORRIGE," +
	        				"CDSCOTRACAUSA," +
	        				"LRESUELTO," +
	        				"DTRESOLUCION," +
	        				"ICVEOFICINA," +
	        				"ICVEDEPARTAMENTO," +
	        				"ICVEPROCESO," +
	        				"COBSLEY1," +
	        				"COBSLEY2," +
	        				"COBSLEY3," +
	        				"ICVEREQUISITO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	        		
	        		int iCveReqAct = vReqAct.getInt("ICVEREQUISITO");
	        	       
	    	       String lSqlOficinaDptoReqAct = "SELECT " +
	    	       		"ICVEOFICINAEVAL," +
	    	       		"ICVEDEPTOEVAL " +
	    	       		"FROM TRAREQUISITO WHERE ICVEREQUISITO="+iCveReqAct;
        	       
        	 
        	       Vector vOficDptoReqAct= new Vector();
        	       vOficDptoReqAct = findByCustom("", lSqlOficinaDptoReqAct); //OBTENGO LA OFICINA Y EL DPTO ASOCIADOS AL REQUISITO ACTUAL

        	       TVDinRep vDatosOficDptoReqAct = new TVDinRep();
        	       
        	       if(vOficDptoReqAct.size()>0){
        	    	   vDatosOficDptoReqAct= (TVDinRep) vOficDptoReqAct.get(0);//OBTENGO LA OFICINA Y EL DPTO ASOCIADOS AL REQUISITO actual
        	       }
            		
            	    lPStmt = null;
               		lPStmt=conn.prepareStatement(lSqlGRLCausaPNC);
               		
               		lPStmt.setInt(1, iEjercicio);
               		lPStmt.setInt(2, vData.getInt("iConsecutivoPNC"));
               		lPStmt.setInt(3, causaActXReq.getInt("ICVEPRODUCTO"));
               		lPStmt.setInt(4, causaActXReq.getInt("ICVECAUSAPNC"));
               		lPStmt.setInt(5,0);
               		lPStmt.setString(6, causaActXReq.getString("CDSCOTRACAUSA"));
               		lPStmt.setInt(7, vDatosFinalesPNC.getInt("lResuelto"));
               		
               		if(vData.getDate("dtResolucion") == null)
                           lPStmt.setNull(8,Types.DATE);
                         else
                           lPStmt.setDate(8,vData.getDate("dtResolucion"));
            		
            		lPStmt.setInt(9, vDatosOficDptoReqAct.getInt("ICVEOFICINAEVAL"));
            		lPStmt.setInt(10, vDatosOficDptoReqAct.getInt("ICVEDEPTOEVAL"));
            		lPStmt.setInt(11, causaActXReq.getInt("ICVEPROCESO"));
            		lPStmt.setString(12,"");
            		lPStmt.setString(13,"");
            		lPStmt.setString(14,"");
            		lPStmt.setInt(15,iCveReqAct);
            		
            		lPStmt.executeUpdate();//hago los insert en GRLREGCAUSAPNC
            		
            		//
            		if(!tecnico || !juridico){
                	
                		String lupdSQL="";
                		
                		if ( vDatosOficDptoReqAct.getInt("ICVEDEPTOEVAL") == Integer.parseInt(VParametros.getPropEspecifica("TECNICO")) ){
                 		   lupdSQL= "UPDATE TRAREGSOLICITUD SET LTECNICO = 0 WHERE IEJERCICIO="+ vData.getInt("iEjercicio") +" AND INUMSOLICITUD="+ vData.getInt("iNumSolicitud");
                 		   tecnico=true;
                		}
                 		else if ( vDatosOficDptoReqAct.getInt("ICVEDEPTOEVAL") == Integer.parseInt(VParametros.getPropEspecifica("JURIDICO")) ){
                 		   lupdSQL= "UPDATE TRAREGSOLICITUD SET LJURIDICO = 0 WHERE IEJERCICIO="+ vData.getInt("iEjercicio") +" AND INUMSOLICITUD="+ vData.getInt("iNumSolicitud");
                 		   juridico=true;
                 		}
                	
                		System.out.print(lupdSQL);
                	 lPStmt=null;
               		 lPStmt = conn.prepareStatement(lupdSQL);
            		 
               		 lPStmt.executeUpdate();
            		}
            		
            		
            		cadActual= causaActXReq.getString("CDSCOTRACAUSA");
            		
            		if(!cadLis.contains(cadActual) && !cadActual.trim().equals(""))
            			cadLis.add(cadActual);
	            }
            }
		}
    	    	
    	String cadFin = "";
    	
    	for(String a : cadLis){
    		if(!a.trim().equals("")){
	    		if(cadFin.equals(""))
	    			cadFin += a;
	    		else
	    			cadFin += "; " + a;
	    	}
    	}
    	
    	if(!cadFin.trim().equals("")){
    	
    	String lCausaDscOtros = "INSERT INTO GRLREGCAUSAPNC " +
				"(IEJERCICIO," +
				"ICONSECUTIVOPNC," +
				"ICVEPRODUCTO," +
				"ICVECAUSAPNC," +
				"ICVEUSUCORRIGE," +
				"CDSCOTRACAUSA," +
				"LRESUELTO," +
				"DTRESOLUCION," +
				"ICVEOFICINA," +
				"ICVEDEPARTAMENTO," +
				"ICVEPROCESO," +
				"COBSLEY1," +
				"COBSLEY2," +
				"COBSLEY3) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	
    	
    	    lPStmt = null;
     		lPStmt=conn.prepareStatement(lCausaDscOtros);
     		
     		lPStmt.setInt(1, iEjercicio);
     		lPStmt.setInt(2, vData.getInt("iConsecutivoPNC"));
     		lPStmt.setInt(3, 0);
     		lPStmt.setInt(4, 0);
     		lPStmt.setInt(5,0);
     		lPStmt.setString(6, cadFin);
     		lPStmt.setInt(7, 0);
     		
     		if(vData.getDate("dtResolucion") == null)
                 lPStmt.setNull(8,Types.DATE);
               else
                 lPStmt.setDate(8,vData.getDate("dtResolucion"));


	  		lPStmt.setInt(9, vData.getInt("iCveOficina"));
	  		lPStmt.setInt(10, vData.getInt("iCveDepartamento"));
	  		lPStmt.setInt(11, 0);
	  		lPStmt.setString(12,"");
	  		lPStmt.setString(13,"");
	  		lPStmt.setString(14,"");
  		
  		lPStmt.executeUpdate();//hago el insert de todas las otras causas para el reporte
    	}
   	    	
		for (int i=0;i < vcDatosEvalXArea.size();i++) {
    		
    		TVDinRep vReqAct = (TVDinRep) vcDatosEvalXArea.get(i);
    		           
            String lSqlCausasxReq = "SELECT " +
            		"ICVECAUSAXAREA," +
            		"ICVEEVALXAREA," +
            		"ICVEPROCESO," +
            		"ICVEPRODUCTO," +
            		"ICVECAUSAPNC," +
            		"CDSCOTRACAUSA " +
            		"FROM TRAREGCAUSASXAREA where ICVEEVALXAREA="+vReqAct.getInt("ICVEEVALXAREA"); //busco todas las causas asociadas a ese rquisito evaluado
           
    		Vector vcCausasXReq = new Vector();
            vcCausasXReq = findByCustom("", lSqlCausasxReq);
            
            if(vcCausasXReq.size() > 0){
	            
            	for(int j=0;j < vcCausasXReq.size(); j++){
	            	
	        		TVDinRep causaActXReq = (TVDinRep) vcCausasXReq.get(j);
	        		
	        		String lSqlTraRegReqXCausa ="INSERT INTO TRAREGREQXCAUSA (" +
	            			"IEJERCICIO," +
	            			"ICONSECUTIVOPNC," +
	            			"ICVEPRODUCTO,"+
	            			"ICVECAUSAPNC," +
	            			"ICVEREQUISITO) VALUES (?,?,?,?,?)";
	        		
	        		
	        		lPStmt=null;
	        		lPStmt = conn.prepareStatement(lSqlTraRegReqXCausa);
	        		
	        		System.out.println(" ejer-"+iEjercicio+" consecPNC-"+vData.getInt("iConsecutivoPNC")+" PROD-"+causaActXReq.getInt("ICVEPRODUCTO")+" CAUSA-"+causaActXReq.getInt("ICVECAUSAPNC")+" REQ-"+vReqAct.getInt("ICVEREQUISITO"));
	        		
	        		lPStmt.setInt(1, iEjercicio);
	        		lPStmt.setInt(2, vData.getInt("iConsecutivoPNC"));
	        		lPStmt.setInt(3, causaActXReq.getInt("ICVEPRODUCTO"));
	        		lPStmt.setInt(4, causaActXReq.getInt("ICVECAUSAPNC"));
	        		lPStmt.setInt(5, vReqAct.getInt("ICVEREQUISITO"));
	        		
	        		lPStmt.executeUpdate();
            	}
            	
            	String cadDel ="delete FROM TRAREGCAUSASXAREA where ICVEEVALXAREA="+vReqAct.getInt("ICVEEVALXAREA");
            	
            	lPStmt=null;
            	lPStmt = conn.prepareStatement(cadDel);
            	lPStmt.executeUpdate();
            }
		}
		
		
		String lSqlEvalValido= "SELECT ICVEEVALXAREA,ICVEREQUISITO,LVALIDO,ICONSECUTIVOPNC FROM TRAREGEVAREQXAREA where"+
		      	  " IEJERCICIO="+iEjercicio+
		      	  " AND INUMSOLICITUD="+iNumSolicitud+
		      	  " AND ICVETRAMITE="+iCveTramite+
		      	  " AND ICVEMODALIDAD="+iCveModalidad+
		      	  " AND ICONSECUTIVOPNC IS NULL AND LVALIDO = 1";
		      	
      	Vector vcDatosEvalValido = new Vector();
      	
      	vcDatosEvalValido = findByCustom("", lSqlEval);
      	
      	if(vcDatosEvalValido.size()>0){
	      	for(int i=0;i<vcDatosEvalValido.size();i++){
	      		
	      		TVDinRep datosValido = new TVDinRep();
	      		datosValido = (TVDinRep) vcDatosEvalValido.get(i);
	      		
	      		String lSqlActValido = "UPDATE TRAREGEVAREQXAREA SET ICONSECUTIVOPNC = 0 WHERE ICVEEVALXAREA ="+datosValido.get("ICVEEVALXAREA");
	      		
	      		lPStmt=null;
	      		lPStmt=conn.prepareStatement(lSqlActValido);
	      		lPStmt.executeUpdate();
	      	}
      	}
		
      	
      //actualizo los registros de la tabla TraRegEvaXArea	
      	
      String lSqlTraeEval= "SELECT ICVEEVALXAREA,ICVEREQUISITO,LVALIDO,ICONSECUTIVOPNC FROM TRAREGEVAREQXAREA where"+
    		  	  //" ICONSECUTIVOPNC IS NULL AND "+
            	  " IEJERCICIO="+iEjercicio+
            	  " AND INUMSOLICITUD="+iNumSolicitud+
            	  " AND ICVETRAMITE="+iCveTramite+
            	  " AND ICVEMODALIDAD="+iCveModalidad;
            	
            	Vector vcDatosEvalTodo = new Vector();
            	
            	vcDatosEvalTodo = findByCustom("", lSqlTraeEval);//todas las evaluaciones de los requisitos de esa solicitud
           
       for(int i=0;i<vcDatosEvalTodo.size();i++){     

    	   TVDinRep vDatosItera = new TVDinRep();
          	
    	   vDatosItera = (TVDinRep) vcDatosEvalTodo.get(i); //saco la evaluacion del iesimo reqisito
    	   
    	   
    	   String lSqlUpdateEva = "UPDATE TRAREGEVAREQXAREA SET ICONSECUTIVOPNC=";
    	   
    	   if(vDatosItera.getInt("LVALIDO")==1){
    		   lSqlUpdateEva +="0";
    	   }
    	   else if (vDatosItera.getInt("LVALIDO")==0){
    		   lSqlUpdateEva += vData.getInt("iConsecutivoPNC");
    	   }
    	   
    	   lSqlUpdateEva += " WHERE IEJERCICIO="+iEjercicio+
     	  " AND INUMSOLICITUD="+iNumSolicitud+
     	  " AND ICVETRAMITE="+iCveTramite+
     	  " AND ICVEMODALIDAD="+iCveModalidad +
     	  " AND ICVEREQUISITO="+vDatosItera.getInt("ICVEREQUISITO");
    	   
    	   lPStmt = null;
    	   lPStmt = conn.prepareStatement(lSqlUpdateEva);
    	   lPStmt.executeUpdate();
    	   
    	   
    	  /*********************************/ 
    	   
    	   
    	   String lSqLRecNotif = "SELECT LRECNOTIFICADO FROM TRAREGREQXTRAM" +
    			   	  " WHERE IEJERCICIO="+iEjercicio+
    		     	  " AND INUMSOLICITUD="+iNumSolicitud+
    		     	  " AND ICVETRAMITE="+iCveTramite+
    		     	  " AND ICVEMODALIDAD="+iCveModalidad +
    		     	  " AND ICVEREQUISITO="+vDatosItera.getInt("ICVEREQUISITO");
    	   
    	   System.out.print("lrecnotificaado del requisito -->>> "+lSqLRecNotif);
    	   
    	   Vector vcLRecNotificadoXReq = this.findByCustom("", lSqLRecNotif);
    	   
    	   TVDinRep vDatosRecNotifxReq = (TVDinRep) vcLRecNotificadoXReq.get(0);
    	   
    	   
    	   String lSqlUpdateTraRegReqxTram=null; 
    	   
    	   System.out.print("lo que trae la cadena lrecnotif------>"+vDatosRecNotifxReq.getString("LRECNOTIFICADO"));
    	   
    	   
    	   if(vDatosRecNotifxReq.getInt("LRECNOTIFICADO") > 0 && vDatosItera.getInt("LVALIDO") > 0){
    		  lSqlUpdateTraRegReqxTram = "UPDATE TRAREGREQXTRAM SET";  
    	   }else if ( (vDatosRecNotifxReq.getInt("LRECNOTIFICADO") > 0 && vDatosItera.getInt("LVALIDO") == 0) || vDatosRecNotifxReq.getInt("LRECNOTIFICADO") == 0){
    		   lSqlUpdateTraRegReqxTram = "UPDATE TRAREGREQXTRAM SET LRECNOTIFICADO=0, ";
    	   }
    		   
       	   if(vDatosItera.getInt("LVALIDO")==1){
    		   lSqlUpdateTraRegReqxTram += " LTIENEPNC=0, LVALIDO=1";
    	   }
    	   else if (vDatosItera.getInt("LVALIDO")==0){
    		   lSqlUpdateTraRegReqxTram += " DTCOTEJO=NULL, LTIENEPNC=1, LVALIDO=0, DTEVALUACION = null ";
    	   }

		   lSqlUpdateTraRegReqxTram += " WHERE IEJERCICIO="+iEjercicio+
     	  " AND INUMSOLICITUD="+iNumSolicitud+
     	  " AND ICVETRAMITE="+iCveTramite+
     	  " AND ICVEMODALIDAD="+iCveModalidad +
     	  " AND ICVEREQUISITO="+vDatosItera.getInt("ICVEREQUISITO");
		   
		   System.out.print("requisito que se actualiza -->>> "+lSqlUpdateTraRegReqxTram);
    	   
    	   lPStmt = null;
    	   lPStmt = conn.prepareStatement(lSqlUpdateTraRegReqxTram);
    	   lPStmt.executeUpdate(); 
       }
      	
       lPStmt.close();
     	if(cnNested == null){   
	   		conn.commit();
	   	}
       
       //para actualizar dtoficio en los requisitos para que aparezcan en ka notificacion del pnc
       
       lSQL = "update TRAREGREQXTRAM set dtoficio = ? where iejercicio = ? and inumsolicitud= ? and lvalido=0";

       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.setDate(1,new java.sql.Date(new Date().getTime()));
       lPStmt.setInt(2,iEjercicio);
       lPStmt.setInt(3,iNumSolicitud);
       lPStmt.executeUpdate();  
       
	   	if(cnNested == null){   
	   		conn.commit();
	   	}
       
		TVDinRep vDatosTraRegPNCEtapa = new TVDinRep();
		
		vDatosTraRegPNCEtapa.put("iEjercicio",iEjercicio);
		vDatosTraRegPNCEtapa.put("iNumSolicitud", iNumSolicitud);
		vDatosTraRegPNCEtapa.put("iCveTramite", iCveTramite);
		vDatosTraRegPNCEtapa.put("iCveModalidad", iCveModalidad);
		vDatosTraRegPNCEtapa.put("iEjercicioPNC", iEjercicio);
		vDatosTraRegPNCEtapa.put("iConsecutivoPNC", vData.getInt("iConsecutivoPNC"));

		TDTRARegPNCEtapa tdtraRegPNCEtapa = new TDTRARegPNCEtapa();
		tdtraRegPNCEtapa.insert(vDatosTraRegPNCEtapa, null);
       
    	
          
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
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
  
  // */
/*
 
  ////ORIGINAL/////
    public TVDinRep insertExiste(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    boolean lAgregarAPNC = false;
    boolean lInsertaCausa = false;
    TFechas dtFechaActual = new TFechas();
    int iNumSolicitud, iCveTramite, iCveModalidad = 0;
    int iConsecutivoPNC = 0;
    int iEjercicio = 0;
    try{
      //LEL26092006
      Vector vcDataA = findByCustom("","SELECT " +
         "TRAREGPNCETAPA.IEJERCICIOPNC, " +
         "TRAREGPNCETAPA.INUMSOLICITUD, " +
         "TRAREGPNCETAPA.ICONSECUTIVOPNC, " +
         "TRAREGPNCETAPA.ICVETRAMITE, " +
         "TRAREGPNCETAPA.ICVEMODALIDAD " +
         "FROM TRAREGPNCETAPA " +
         "where TRAREGPNCETAPA.IEJERCICIO = " + vData.getInt("iEjercicio") +
         " and TRAREGPNCETAPA.INUMSOLICITUD = "+ vData.getInt("iNumSolicitud") +
         " ORDER BY ICONSECUTIVOPNC DESC ");
      TVDinRep vSoli;
      if(vcDataA.size() > 0){
        vSoli = (TVDinRep) vcDataA.get(0);
        iEjercicio = vSoli.getInt("IEJERCICIOPNC");
        iNumSolicitud = vSoli.getInt("INUMSOLICITUD");
        iConsecutivoPNC = vSoli.getInt("ICONSECUTIVOPNC");
        iCveTramite = vSoli.getInt("ICVETRAMITE");
        iCveModalidad = vSoli.getInt("ICVEMODALIDAD");
        vcDataA = null;
        vcDataA = findByCustom("","SELECT " +
                  "DTNOTIFICACION FROM TRAREGREQXTRAM " +
                  "WHERE IEJERCICIO = " + iEjercicio +
                  " AND INUMSOLICITUD = " + iNumSolicitud +
                  " AND ICVETRAMITE = " + iCveTramite +
                  " AND ICVEMODALIDAD = " + iCveModalidad +
                  " AND LTIENEPNC = 1");
        lAgregarAPNC = true;
        for(int i=0; i < vcDataA.size() && lAgregarAPNC == false; i++){
          vSoli = (TVDinRep) vcDataA.get(i);
          if(vSoli.getDate("DTNOTIFICACION") == null)
            lAgregarAPNC = true;
          else
            lAgregarAPNC = false;
        }
      }
      if(lAgregarAPNC){
        TVDinRep vResuelto;
        Vector vcDataR = findByCustom("","SELECT lResuelto " +
                                      "FROM GRLREGISTROPNC WHERE " +
                                      "IEJERCICIO = " + iEjercicio +
                                      " AND ICONSECUTIVOPNC = " + iConsecutivoPNC);
        if(vcDataR.size() > 0){
          vResuelto = (TVDinRep) vcDataR.get(0);
          if(vResuelto.getInt("lResuelto") != 0)
            lAgregarAPNC = false;
        }
      }
      //FinLEL26092006
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado,iCveOficina,iCveDepartamento,iCveProceso) values (?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      if(lAgregarAPNC == false){
        Vector vcData = findByCustom("",
                                     "select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " +
                                     vData.getInt("iEjercicio"));
        if(vcData.size() > 0){
          TVDinRep vUltimo = (TVDinRep) vcData.get(0);
          vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
        } else
          vData.put("iConsecutivoPNC",1);
      }else{
        vData.put("iConsecutivoPNC",iConsecutivoPNC);
      }
      vData.addPK(vData.getString("iConsecutivoPNC"));
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,dtFechaActual.getIntYear(dtFechaActual.TodaySQL()));
        lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
        iConsecutivo = vData.getInt("iConsecutivoPNC");
        lPStmt.setDate(3,tFecha.TodaySQL());
        lPStmt.setInt(4,vData.getInt("iCveUsuario"));
        lPStmt.setInt(5,vData.getInt("iCveProducto"));
        lPStmt.setInt(6,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(7,Types.DATE);
        else
          lPStmt.setDate(7,vData.getDate("dtResolucion"));
        lPStmt.setInt(8,vData.getInt("iCveOficinaUsrAsg"));
        lPStmt.setInt(9,vData.getInt("iCveDeptoUsrAsg"));
        lPStmt.setInt(10,vData.getInt("iCveOficinaUsr"));
        lPStmt.setInt(11,vData.getInt("iCveDeptoUsr"));
        lPStmt.setInt(12,vData.getInt("iCveProceso"));
        

        System.out.print(">>>TDRELRegPNC  1:"+vData.getString("cObsLey1"));
        System.out.print(">>>TDRELRegPNC  2:"+vData.getString("cObsLey2"));
        System.out.print(">>>TDRELRegPNC  3:"+vData.getString("cObsLey3"));

        if(lAgregarAPNC == false)
           lPStmt.executeUpdate();
        lPStmt.close();
        try{
          TDGRLRegCausaPNC1 dGRLRegCausaPNC = new TDGRLRegCausaPNC1();
      //    TDGRLRegCausaPNC dGRLRegCausaPNC = new TDGRLRegCausaPNC();
          if(lAgregarAPNC == false){
            dGRLRegCausaPNC.insert(vData,conn,iConsecutivo);
          //   dGRLRegCausaPNC.insert(vData,conn);
          }else{
              dGRLRegCausaPNC.insertA(vData,conn,iConsecutivo);
            //   dGRLRegCausaPNC.insertA(vData,conn);
          }
        }catch(Exception exCausa){
          exCausa.printStackTrace();
          lSuccess = false;
        }
        if(cnNested == null && lSuccess)
          conn.commit();
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
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
  /*


  /**
   * Elimina al registro a trav�s de la entidad dada por vData.
   * <p><b> delete from GRLRegistroPNC where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested)throws
    DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from GRLRegistroPNC where iEjercicio = ? AND iConsecutivoPNC = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = ""+sqle.getErrorCode();
    } catch(Exception ex){
      warn("delete",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("delete.rollback",e);
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
        warn("delete.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMsg);
      return lSuccess;
    }
  }
  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update GRLRegistroPNC set dtRegistro=?, iCveUsuRegistro=?, iCveProducto=?, lResuelto=?, dtResolucion=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update GRLRegistroPNC set dtRegistro=?, iCveUsuRegistro=?, iCveProducto=?, lResuelto=?, dtResolucion=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iConsecutivoPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setInt(2,vData.getInt("iCveUsuRegistro"));
      lPStmt.setInt(3,vData.getInt("iCveProducto"));
      lPStmt.setInt(4,vData.getInt("lResuelto"));
      lPStmt.setDate(5,vData.getDate("dtResolucion"));
      lPStmt.setInt(6,vData.getInt("iCveOficinaAsignado"));
      lPStmt.setInt(7,vData.getInt("iCveDeptoAsignado"));
      lPStmt.setInt(8,vData.getInt("iEjercicio"));
      lPStmt.setInt(9,vData.getInt("iConsecutivoPNC"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("update",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback",e);
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
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");

      return vData;
    }
 }

 /**
  * Actualiza el campo de Producto No Conforme de la persona que recibe la notificaci�n.
  * <p><b> update GRLRegistroPNC set cRecibeNotif=? where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
  * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
  * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
  * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
  * @throws DAOException       - Excepci�n de tipo DAO
  * @return TVDinRep           - Entidad Modificada.
  */
 public TVDinRep updateRecibe(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   int iConsecutivoPNC = 0,iEjercicioPNC=0;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     Vector vcPNC = findByCustom("","SELECT " +
                              "iEjercicioPNC,ICONSECUTIVOPNC " +
                              "FROM TRAREGPNCETAPA " +
                              "where IEJERCICIO = " + vData.getInt("iEjercicio")+
                              " and INUMSOLICITUD = "+ vData.getInt("iNumSolicitud") +
                              " order by iorden desc");
     if(vcPNC.size() > 0){
       TVDinRep vRegPNC = (TVDinRep) vcPNC.get(0);
       iConsecutivoPNC = vRegPNC.getInt("ICONSECUTIVOPNC");
       iEjercicioPNC = vRegPNC.getInt("iEjercicioPNC");
     }

     String lSQL = "update GRLRegistroPNC set cRecibeNotif=?,dtNotificacion= (current date) where iEjercicio = ? AND iConsecutivoPNC = ? ";

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setString(1,vData.getString("cRecibeNotif"));
     lPStmt.setInt(2,iEjercicioPNC);
     lPStmt.setInt(3,iConsecutivoPNC);
        
     lPStmt.executeUpdate();
     
     if(cnNested == null){
    	 conn.commit();
     }
     
   } catch(Exception ex){
     warn("update",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("update.rollback",e);
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
       warn("update.close",ex2);
     }
     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }
}


 //FUNCION Obtiene el valor del consecutivo:
  public int getConsecutivoPNC(){
     return iConsecutivo;
  }
}



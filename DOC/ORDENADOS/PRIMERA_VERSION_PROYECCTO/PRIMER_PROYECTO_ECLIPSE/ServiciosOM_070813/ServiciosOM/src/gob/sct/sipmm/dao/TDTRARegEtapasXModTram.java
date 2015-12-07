package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;
import gob.sct.sipmm.dao.ws.TDCis;
/**
 * <p>Title: TDTRARegEtapasXModTram.java</p>
 * <p>Description: DAO de la entidad TRARegEtapasXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
public class TDTRARegEtapasXModTram extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String errorExc = "";
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
      e.printStackTrace();
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }
  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into TRARegEtapasXModTram(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCveOficina,iCveDepartamento,iCveUsuario,tsRegistro) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      Exception{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    Vector vcOfiDepData = new Vector();
    boolean lSuccess = true;
    String cMsgError="";
    
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String cOficDep = "SELECT " +
          "ICVEOFICINA,ICVEDEPARTAMENTO " +
          "FROM TRAREGETAPASXMODTRAM " +
          "where IEJERCICIO = " + vData.getString("iEjercicio") +
          " and INUMSOLICITUD = " + vData.getString("iNumSolicitud") +
          " and icveetapa = " + VParametros.getPropEspecifica("EtapaRecepcion");

      vcOfiDepData = this.findByCustom("",cOficDep);

      if(vcOfiDepData.size() > 0){
         TVDinRep vOfiDep = (TVDinRep) vcOfiDepData.get(0);
         vData.put("iCveOficina",vOfiDep.getInt("iCveOficina"));
         vData.put("iCveDepartamento",vOfiDep.getInt("iCveDepartamento"));
      } else {
        vData.put("iCveOficina",0);
        vData.put("iCveDepartamento",0);
      }

      TVDinRep vCambiaEtapa = new TVDinRep();
      vCambiaEtapa.put("iEjercicio",vData.getInt("iEjercicio"));
      vCambiaEtapa.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
      vCambiaEtapa.put("iCveTramite",vData.getInt("iCveTramite"));
      vCambiaEtapa.put("iCveModalidad",vData.getInt("iCveModalidad"));
      vCambiaEtapa.put("iCveEtapa",vData.getInt("iCveEtapa"));
      vCambiaEtapa.put("iCveOficina",vData.getInt("iCveOficina"));
      vCambiaEtapa.put("iCveDepartamento",vData.getInt("iCveDepartamento"));
      vCambiaEtapa.put("iCveUsuario",vData.getInt("iCveUsuario"));
      vCambiaEtapa.put("lAnexo",vData.getInt("lAnexo"));
      vCambiaEtapa.put("lResolucion",vData.getInt("lResolucion"));
      vCambiaEtapa.put("cObservaciones", vData.getString("cObsCIS"));

      if(vData.getInt("iCveEtapa") == Integer.parseInt(VParametros.getPropEspecifica("EtapaConcluidoArea"))){
        this.cambiarEtapa(vCambiaEtapa,true,"",false,conn);
      }
      else{
        this.cambiarEtapa(vCambiaEtapa,false,"",false, conn);
      }

      if(vData.getInt("iCveEtapa") == Integer.parseInt(VParametros.getPropEspecifica("EtapaConcluidoArea"))){
        String cEnviaResolucion = "";
        String cSQLDirigidoA = "SELECT " +
                               "iEjercicio,iNumSolicitud,CENVIARRESOLUCIONA " +
                               "FROM TRAREGSOLICITUD " +
                               "where IEJERCICIO = " + vData.getString("iEjercicio") +
                               " and INUMSOLICITUD = " + vData.getString("iNumSolicitud");
        try{
          vcOfiDepData = super.findByNessted("iEjercicio,iNumSolicitud",cSQLDirigidoA,conn);
        }catch(Exception e){
          vcOfiDepData = new Vector();
        }

        if(vcOfiDepData.size() > 0){
          TVDinRep vDirigidoA = (TVDinRep) vcOfiDepData.get(0);
          cEnviaResolucion = vDirigidoA.getString("CENVIARRESOLUCIONA");
        }
        int iCveDeptoEntregarA;
        int iCveEtapa;

        if(!cEnviaResolucion.equalsIgnoreCase("") && !cEnviaResolucion.equalsIgnoreCase("null")
           && !cEnviaResolucion.equalsIgnoreCase(null)){
          iCveDeptoEntregarA = Integer.parseInt(VParametros.getPropEspecifica("DeptoOficialiaPartes"));
          iCveEtapa = Integer.parseInt(VParametros.getPropEspecifica("EtapaEntregarOficialia"));
        }else{
          iCveDeptoEntregarA = Integer.parseInt(VParametros.getPropEspecifica("DeptoVentanillaUnica"));
          iCveEtapa = Integer.parseInt(VParametros.getPropEspecifica("EtapaEntregarVU"));
        }

        TVDinRep vEtapaEntregarA = new TVDinRep();
        vEtapaEntregarA.put("iEjercicio",vData.getInt("iEjercicio"));
        vEtapaEntregarA.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
        vEtapaEntregarA.put("iCveTramite",vData.getInt("iCveTramite"));
        vEtapaEntregarA.put("iCveModalidad",vData.getInt("iCveModalidad"));
        vEtapaEntregarA.put("iCveEtapa",iCveEtapa);
        vEtapaEntregarA.put("iCveOficina",vData.getInt("iCveOficina"));
        vEtapaEntregarA.put("iCveDepartamento",iCveDeptoEntregarA);
        vEtapaEntregarA.put("iCveUsuario",vData.getInt("iCveUsuario"));
        vEtapaEntregarA.put("lAnexo",vData.getInt("lAnexo"));
        vEtapaEntregarA.put("cObservaciones", vData.getString("cObsCIS"));
        this.cambiarEtapa(vEtapaEntregarA,false,"",false,conn);
      }


      if(cnNested == null)
        conn.commit();

    } catch(DAOException ex){
        errorExc = "" + ex.getMessage() +"";
      warn(ex.getMessage(),ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }
      }
      lSuccess = false;
            

    } 
    catch(Exception ex){
        errorExc = "" + ex.getMessage() +"";
      warn(ex.getMessage(),ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }
      }
      lSuccess = false;
    }
    finally{
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
    	 //throw new DAOException(errorExc+"s");      
      if(lSuccess == false){
    	throw new Exception(errorExc);
      }
      
      return vData;
    }
  }
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRARegEtapasXModTram where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
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
      String lSQL = "delete from TRARegEtapasXModTram where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveEtapa"));
      lPStmt.setInt(6,vData.getInt("iOrden"));

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
   * <p><b> update TRARegEtapasXModTram set iCveOficina=?, iCveDepartamento=?, iCveUsuario=?, tsRegistro=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
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
      String lSQL = "update TRARegEtapasXModTram set iCveOficina=?, iCveDepartamento=?, iCveUsuario=?, tsRegistro=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveEtapa"));
      vData.addPK(vData.getString("iOrden"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iCveDepartamento"));
      lPStmt.setInt(3,vData.getInt("iCveUsuario"));
      lPStmt.setString(4,vData.getString("tsRegistro"));
      lPStmt.setInt(5,vData.getInt("iEjercicio"));
      lPStmt.setInt(6,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(7,vData.getInt("iCveTramite"));
      lPStmt.setInt(8,vData.getInt("iCveModalidad"));
      lPStmt.setInt(9,vData.getInt("iCveEtapa"));
      lPStmt.setInt(10,vData.getInt("iOrden"));
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

  public void cambiaEtapaWSCIS(TVDinRep vData,Connection cnNested){
	  /*** Cambiar Etapa Por medio de WS al CIS     
	  1.- Validar que exista clave de tramite CIS y envio al CIS TABLA TRAETAPAXMODTRAM ***/
	  Vector vcValidTramCIS = new Vector();
	    try {
		  String cValidTramCIS = " SELECT ICVEETAPACIS,LENVIARFECHACIS FROM TRAETAPAXMODTRAM " +
		  " WHERE ICVETRAMITE = " + String.valueOf(vData.getInt("iCveTramite")) + " AND ICVEMODALIDAD = " + String.valueOf(vData.getInt("iCveModalidad")) +
		  " AND ICVEETAPA =  " + String.valueOf(vData.getInt("iCveEtapa"));/* +
		  " AND VIGENTE = 1 ";*/
		  		    
		  vcValidTramCIS = this.findByCustom("",cValidTramCIS);
		  TVDinRep vValidTramCIS = new TVDinRep();
		  
		   if(vcValidTramCIS.size() > 0){
			   vValidTramCIS = (TVDinRep) vcValidTramCIS.get(0);
			   
			   if((vValidTramCIS.getInt("ICVEETAPACIS")!=0) && (vValidTramCIS.getInt("LENVIARFECHACIS")!=0)){			   
				 this.upDateEstadoCita(vData.getInt("iEjercicio"), vData.getInt("iNumSolicitud"), vValidTramCIS.getInt("ICVEETAPACIS"), null);
			  }		      			       
		  }    		
	  } catch (Exception e) {
	  }	  	
  }
  
 public TVDinRep insertEtapa(TVDinRep vData,Connection cnNested) throws DAOException{
   TVDinRep InsertaEtapa = new TVDinRep();
   try{
     InsertaEtapa=insertEtapa(vData,cnNested,false);
   }
   catch(Exception e){
     throw new DAOException(e.getMessage());
   }
   return InsertaEtapa;
 }
 public TVDinRep insertEtapa(TVDinRep vData,Connection cnNested, boolean lRepetirEtapa) throws Exception{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   boolean lGrabaEtapa = true;
   String RFCSolicitante = "";
   
   
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     
     String lSQLRfc = " SELECT per.CRFC from TRAREGSOLICITUD sol " +
                     " join GRLPERSONA per on sol.ICVESOLICITANTE = per.ICVEPERSONA "+ 
                     " where sol.IEJERCICIO = " + vData.getInt("iEjercicio") +
                     " AND sol.INUMSOLICITUD = " + vData.getInt("iNumSolicitud") ;
     
     Vector vcDataRFC = this.findByNessted("",lSQLRfc,conn);	

      if(vcDataRFC.size() > 0){
	     TVDinRep vUltimoRFC = (TVDinRep) vcDataRFC.get(0);
	     RFCSolicitante = vUltimoRFC.getString("CRFC");	        
	  }
     
	     String lSQL =
	         " insert into TRARegEtapasXModTram( " +
	         "             iEjercicio, " +
	         "             iNumSolicitud, " +
	         "             iCveTramite, " +
	         "             iCveModalidad, " +
	         "             iCveEtapa, " +
	         "             iOrden, " +
	         "             iCveOficina, " +
	         "             iCveDepartamento, " +
	         "             iCveUsuario, " +
	         "             tsRegistro, " +
	         "             lAnexo, cObsEnviadaCIS) " +
	         " values (?,?,?,?,?,?,?,?,?,?,?,?)";
	
	     
	     
	     //AGREGAR AL ULTIMO ...
	     String cEtapa = " select IORDEN, ICVEETAPA " +
	                     "        from TRARegEtapasXModTram " +
	                     " where iEjercicio = " + vData.getInt("iEjercicio") +
	                     "   and iNumSolicitud = " + vData.getInt("iNumSolicitud") +
	                     " ORDER BY IORDEN DESC ";
	
	     Vector vcData = this.findByNessted("",cEtapa,conn);
	
	     if(vcData.size() > 0){
	        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
	        vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
	        if(!lRepetirEtapa && vUltimo.getInt("ICVEETAPA")==vData.getInt("iCveEtapa")){
	        	lGrabaEtapa=false;
	        }
	     }else
	        vData.put("iOrden",1);
	     vData.addPK(vData.getString("iEjercicio"));
	     vData.addPK(vData.getString("iNumSolicitud"));
	     vData.addPK(vData.getString("iCveTramite"));
	     vData.addPK(vData.getString("iCveModalidad"));
	     vData.addPK(vData.getString("iCveEtapa"));
	     vData.addPK(vData.getString("iOrden"));
	
	     //se agrega Etapa en caso de que la etapa a insertar no sea la misma a la ultima insertada
	     if(lGrabaEtapa==true){
	    	 System.out.println("+++++++++++se agrega Etapa en caso de que la etapa a insertar no sea la misma a la ultima insertada");
	    	 System.out.println("+++++++ iCveEtapa:"+vData.getInt("iCveEtapa")+"    iOrden:"+vData.getInt("iOrden"));
	       lPStmt = conn.prepareStatement(lSQL);
	       lPStmt.setInt(1,vData.getInt("iEjercicio"));
	       lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
	       lPStmt.setInt(3,vData.getInt("iCveTramite"));
	       lPStmt.setInt(4,vData.getInt("iCveModalidad"));
	       lPStmt.setInt(5,vData.getInt("iCveEtapa"));
	       lPStmt.setInt(6,vData.getInt("iOrden"));
	       lPStmt.setInt(7,vData.getInt("iCveOficina"));
	       lPStmt.setInt(8,vData.getInt("iCveDepartamento"));
	       lPStmt.setInt(9,vData.getInt("iCveUsuario"));
	       lPStmt.setString(10,vData.getString("tsRegistro"));
	       lPStmt.setInt(11,vData.getInt("lAnexo"));
	       //lPStmt.setString(12, vData.getString("cObservaciones"));
	       lPStmt.setString(12, " Ejercicio "  + String.valueOf(vData.getInt("iEjercicio")) + " No. Solicitud " + String.valueOf(vData.getInt("iNumSolicitud")) + " RFC " + RFCSolicitante );
	
	       

	       
	       lPStmt.executeUpdate();
	
	       String cVinculadas = "SELECT INUMSOLICITUDPRINC,ev.ICVEETAPA,s1.ICVETRAMITE,s1.ICVEMODALIDAD " +
	                            "FROM TRASOLICITUDREL SR " +
	                            "JOIN TRAREGSOLICITUD S ON S.IEJERCICIO=SR.IEJERCICIO AND S.INUMSOLICITUD=SR.INUMSOLICITUDREL " +
	                            "join TRAREGSOLICITUD s1 on s1.IEJERCICIO=sr.IEJERCICIO and s1.INUMSOLICITUD=sr.INUMSOLICITUDPRINC " +
	                            "JOIN TRAETAPAVINCULADA EV ON EV.ICVETRAMITEHIJO=S.ICVETRAMITE AND EV.ICVEMODALIDADHIJO=S.ICVEMODALIDAD " +
	                            "AND EV.ICVETRAMITE=S1.ICVETRAMITE AND EV.ICVEMODALIDAD=S1.ICVEMODALIDAD " +
	                            "where SR.iejercicio="+vData.getInt("iEjercicio")+
	                            " and SR.INUMSOLICITUDREL="+vData.getInt("iNumSolicitud")+
	                            " and ev.ICVEETAPAVINC="+vData.getInt("iCveEtapa");
	
	       Vector vcVinculadas = this.findByNessted("",cVinculadas,conn);
	       if(vcVinculadas.size()>0){
	         TVDinRep vVinculadas = (TVDinRep) vcVinculadas.get(0);
	         int iSolPadre = vVinculadas.getInt("INUMSOLICITUDPRINC");
	         vData.remove("iNumSolicitud");
	         vData.remove("iCveTramite");
	         vData.remove("iCveModalidad");
	         vData.remove("iCveEtapa");
	         vData.put("iNumSolicitud",vVinculadas.getInt("INUMSOLICITUDPRINC"));
	         vData.put("iCveTramite",vVinculadas.getInt("ICVETRAMITE"));
	         vData.put("iCveModalidad",vVinculadas.getInt("ICVEMODALIDAD"));
	         vData.put("iCveEtapa",vVinculadas.getInt("ICVEETAPA"));
	         String cOrden = "SELECT EMT.IORDEN,EMT.ICVEETAPA,E.CDSCETAPA,EMT.LOBLIGATORIO,RE.TSREGISTRO " +
	                         "FROM TRAREGSOLICITUD s " +
	                         "JOIN TRAETAPAXMODTRAM EMT ON EMT.ICVETRAMITE=S.ICVETRAMITE AND EMT.ICVEMODALIDAD=S.ICVEMODALIDAD " +
	                         "LEFT JOIN TRAREGETAPASXMODTRAM RE ON RE.IEJERCICIO=S.IEJERCICIO AND RE.INUMSOLICITUD=S.INUMSOLICITUD AND RE.ICVEETAPA=EMT.ICVEETAPA " +
	                         "JOIN TRAETAPA E ON E.ICVEETAPA=EMT.ICVEETAPA " +
	                         "where S.IEJERCICIO="+vData.getInt("iEjercicio")+" AND S.INUMSOLICITUD="+vData.getInt("iNumSolicitud")+" AND EMT.LVIGENTE=1 " +
	                         "ORDER BY EMT.IORDEN ";
	         Vector vcOrdenDep = this.findByNessted("",cOrden,conn);
	         if(vcOrdenDep.size()>0){
	           for(int iO=0;iO<vcOrdenDep.size();iO++){
	             TVDinRep vOrden = (TVDinRep) vcOrdenDep.get(iO);
	             System.out.println("****************ICVEETAPA:"+vOrden.getInt("ICVEETAPA")+"  ***vVinculadas ICVEETAPA"+vVinculadas.getInt("ICVEETAPA"));
	             if(vOrden.getInt("ICVEETAPA")==vVinculadas.getInt("ICVEETAPA")) break;
	             else if(vOrden.getInt("LOBLIGATORIO")==1 && vOrden.getTimeStamp("TSREGISTRO")==null){
	            	 System.out.println("No es posible actualizar la Etapa Requerida, debido a que la Etapa <<" + vOrden.getString("CDSCETAPA") + ">> del Trámite padre es Obligatoria");
	            	 cMensaje ="No es posible actualizar la Etapa Requerida, debido a que la Etapa <<" + vOrden.getString("CDSCETAPA") + ">> del Trámite padre es Obligatoria"; 
	               throw new Exception("No es posible actualizar la Etapa Requerida, debido a que la Etapa <<" + vOrden.getString("CDSCETAPA") + ">> del Trámite padre es Obligatoria");
	             }
	           }
	         }
	         if(vVinculadas.getInt("ICVEETAPA") == Integer.parseInt(VParametros.getPropEspecifica("EtapaConcluidoArea"))){
	        	 System.out.println("+++++++ ICVEETAPA = EtapaConcluidoArea");
	           this.cambiarEtapa(vData,true,"",false,conn);
	         }
	         else{
	        	 System.out.println("+++++++ ICVEETAPA != EtapaConcluidoArea");
	           this.cambiarEtapa(vData,false,"",false,conn);
	         }
	       }
	     }

       if(cnNested == null){
         conn.commit();
       }
      //inserta el estado de la solicitud en el CIS
       /*try{
         this.incertaEstadoCita(vData.getInt("iEjercicio"),vData.getInt("iNumSolicitud"),vData.getInt("iCveEtapa"),conn);
       }catch(Exception e){
         e.printStackTrace();
       }*/

   } catch(SQLException se){
     se.printStackTrace();
     cMensaje = super.getSQLMessages(se);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
    	   e.printStackTrace();
         fatal("insert.rollback",e);
       }
     }
     lSuccess = false;
   } catch(Exception ex){
		   ex.printStackTrace();
     warn("insert",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
    	   e.printStackTrace();
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
     if(lSuccess == false){
       throw new Exception(cMensaje);
     }
     return vData;
   }
 }
 /**
  * Método que recibe un vector TVDinRep con los datos necesarios para cambiar de etapa un tramite, y un valor booleano para ver si
  * se actualiza la solicitud como resolución positiva.
  * <p><b> cambiarEtapa  </b></p>
  * El vector debe contener: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iCveOficina,iCveDepartamento,iCveUsuario,lAnexo,lResolucionPositiva
  * Autor: iCaballero
  * Fecha: 10 - Oct - 2006
 * @throws Exception 
  */

  public void cambiarEtapa(TVDinRep vData,boolean lResolucionPositiva, String cObservacion) throws Exception{
    try{
      this.cambiarEtapa(vData,lResolucionPositiva,cObservacion,false);
    }catch(Exception e){
      throw new Exception(e.getMessage());
    }
  }
  
  
  public void cambiarEtapa(TVDinRep vData,boolean lResolucionPositiva, String cObservacion, Connection cnNested) throws DAOException{
  try{
    this.cambiarEtapa(vData,lResolucionPositiva,cObservacion,false,cnNested);
  }catch(Exception e){
    throw new DAOException(e.getMessage());
  }
}


public void cambiarEtapa(TVDinRep vData,boolean lResolucionPositiva, String cObservacion,boolean lPermiteRepetidos,Connection cnNested) throws DAOException{
  TFechas dFecha = new TFechas();
  boolean lSuccess = true;
  try{
    vData.put("tsRegistro",dFecha.getThisTime());
    TVDinRep vData1 = new TVDinRep();
    vData1 = vData;
    TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
    
    //Original
    if(lResolucionPositiva){
        TVDinRep vDataResPos = new TVDinRep();
        vDataResPos.put("lResolucion",vData1.getInt("lResolucion"));
        vDataResPos.put("iEjercicio",vData1.getInt("iEjercicio"));
        vDataResPos.put("iNumSolicitud",vData1.getInt("iNumSolicitud"));
        
        
        
        dSolicitud.updEtapas(vDataResPos,cnNested);
      }
    
    this.insertEtapa(vData,cnNested,lPermiteRepetidos);
  } catch(DAOException ex){
    cMensaje = ex.getMessage();
    lSuccess = false;
  }
  catch (Exception ex){
	  cMensaje = ex.getMessage();
	  lSuccess = false;
	  
  }
  if(!lSuccess)
      throw new DAOException(cMensaje);
  
 }

 public void cambiarEtapa(TVDinRep vData,boolean lResolucionPositiva, String cObservacion,boolean lPermiteRepetidos) throws Exception{
   TFechas dFecha = new TFechas();
   boolean lSuccess = true;
   try{
     vData.put("tsRegistro",dFecha.getThisTime());
     this.insertEtapa(vData,null,lPermiteRepetidos);
     if(lResolucionPositiva){
       TVDinRep vDataResPos = new TVDinRep();
       TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
       vDataResPos.put("lResolucion",vData.getInt("lResolucion"));
       vDataResPos.put("iEjercicio",vData.getInt("iEjercicio"));
       vDataResPos.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
       dSolicitud.updEtapas(vDataResPos,null);
     }
   } catch(Exception ex){ 
	 ex.printStackTrace();
     cMensaje = ex.getMessage();
     lSuccess = false;
   }  
   if(!lSuccess)
       throw new Exception(cMensaje);
   
   }
 
   public void cambiarEtapa(TVDinRep vData,boolean lResolucionPositiva, boolean lPermiteRepetidos,Connection cnNested) throws DAOException{

     TFechas dFecha = new TFechas();
     boolean lSuccess = true;
     try{
       vData.put("tsRegistro",dFecha.getThisTime());
       this.insertEtapa(vData,cnNested,lPermiteRepetidos);
       if(lResolucionPositiva){
         TVDinRep vDataResPos = new TVDinRep();
         TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
         vDataResPos.put("lResolucion",vData.getInt("lResolucion"));
         vDataResPos.put("iEjercicio",vData.getInt("iEjercicio"));
         vDataResPos.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
         dSolicitud.updEtapas(vDataResPos,cnNested);
       }
     } catch(Exception ex){
       cMensaje = ex.getMessage();
       lSuccess = false;
     }
     if(!lSuccess)
         throw new DAOException(cMensaje);
   }

 public void incertaEstadoCita(int iEjercicio, int iNumSolicitud, int iEtapa,Connection conn){
   TDCis dCis = new TDCis();
   TFechas fecha = new TFechas();
   Vector vcEstadoCis = new Vector();
   TVDinRep vEstadoCis = new TVDinRep();
   boolean lEstado=false;
   try{
     vcEstadoCis = this.findByNessted("","SELECT RS.IEJERCICIOCITA, RS.IIDCITA,O.ICVEOFICINACIS,EMT.ICVEETAPACIS,EMT.LENVIARFECHACIS,CT.ICVETRAMITECIS, REMT.cObsEnviadaCIS " +
                                            "FROM TRAREGSOLICITUD RS " +
                                            "LEFT JOIN GRLOFICINA O ON O.ICVEOFICINA=RS.ICVEOFICINA " +
                                            "LEFT JOIN TRAETAPAXMODTRAM EMT ON EMT.ICVETRAMITE=RS.ICVETRAMITE AND EMT.ICVEMODALIDAD=RS.ICVEMODALIDAD " +
                                            //"LEFT JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=RS.ICVETRAMITE AND CT.ICVEMODALIDAD=RS.ICVEMODALIDAD AND CT.DTINIVIGENCIA > CURRENT_DATE " +
                                            "LEFT JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=RS.ICVETRAMITE " +
                                            "LEFT JOIN TRARegEtapasXModTram REMT ON REMT.IEJERCICIO = RS.IEJERCICIO AND REMT.INUMSOLICITUD = RS.INUMSOLICITUD AND REMT.ICVEETAPA = EMT.ICVEETAPA " +
                                            "WHERE RS.IEJERCICIO = " +iEjercicio+
                                            " AND RS.INUMSOLICITUD = " +iNumSolicitud+
                                            " AND EMT.ICVEETAPA = "+iEtapa, conn);
   }catch(Exception e){
   }
   if(vcEstadoCis.size()>0){
     vEstadoCis = (TVDinRep) vcEstadoCis.get(0);
     try{
       lEstado = dCis.insertaEstadoTramite(
                 vEstadoCis.getInt("IIDCITA")/*cita*/,
                 vEstadoCis.getInt("ICVETRAMITECIS")/*tramite*/,
                 1/*no tramite*/,
                 vEstadoCis.getInt("ICVEETAPACIS")/*estado*/,
                 //vEstadoCis.getInt("LENVIARFECHACIS")==1?fecha.getDateSQL(fecha.getThisTime())+" " + vEstadoCis.getString("cObsEnviadaCIS"):""/*observacion*/,
                 vEstadoCis.getInt("LENVIARFECHACIS")==1?vEstadoCis.getString("cObsEnviadaCIS"):""/*observacion*/,
                 vEstadoCis.getInt("ICVEOFICINACIS")/*area*/);
     }catch(Exception e){
       System.out.println(e);
     }finally{
     }
   }

 }
 
 public void upDateEstadoCita(int iEjercicio, int iNumSolicitud, int iEtapa,Connection conn){
	   TDCis dCis = new TDCis();
	   TFechas fecha = new TFechas();
	   Vector vcEstadoCis = new Vector();
	   TVDinRep vEstadoCis = new TVDinRep();
	   boolean lEstado=false;
	   try{
	     vcEstadoCis = this.findByNessted("","SELECT RS.IEJERCICIOCITA, RS.IIDCITA,O.ICVEOFICINACIS,EMT.ICVEETAPACIS,EMT.LENVIARFECHACIS,CT.ICVETRAMITECIS, REMT.cObsEnviadaCIS " +
	                                            "FROM TRAREGSOLICITUD RS " +
	                                            "LEFT JOIN GRLOFICINA O ON O.ICVEOFICINA=RS.ICVEOFICINA " +
	                                            "LEFT JOIN TRAETAPAXMODTRAM EMT ON EMT.ICVETRAMITE=RS.ICVETRAMITE AND EMT.ICVEMODALIDAD=RS.ICVEMODALIDAD " +
	                                            //"LEFT JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=RS.ICVETRAMITE AND CT.ICVEMODALIDAD=RS.ICVEMODALIDAD AND CT.DTINIVIGENCIA > CURRENT_DATE " +
	                                            "LEFT JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=RS.ICVETRAMITE " +
	                                            "LEFT JOIN TRARegEtapasXModTram REMT ON REMT.IEJERCICIO = RS.IEJERCICIO AND REMT.INUMSOLICITUD = RS.INUMSOLICITUD AND REMT.ICVEETAPA = EMT.ICVEETAPA " +
	                                            "WHERE RS.IEJERCICIO = " +iEjercicio+
	                                            " AND RS.INUMSOLICITUD = " +iNumSolicitud+
	                                            " AND EMT.ICVEETAPACIS = "+iEtapa, conn);
	   }catch(Exception e){
	   }
	   if(vcEstadoCis.size()>0){
	     vEstadoCis = (TVDinRep) vcEstadoCis.get(0);
	     try{
	       lEstado = dCis.insertaEstadoTramite(
	                 vEstadoCis.getInt("IIDCITA")/*cita*/,
	                 vEstadoCis.getInt("ICVETRAMITECIS")/*tramite*/,
	                 1/*no tramite*/,
	                 vEstadoCis.getInt("ICVEETAPACIS")/*estado*/,
	                 vEstadoCis.getInt("LENVIARFECHACIS")==1?vEstadoCis.getString("cObsEnviadaCIS"):""/*observacion*/,
	                 vEstadoCis.getInt("ICVEOFICINACIS")/*area*/);
	     }catch(Exception e){
	       System.out.println(e);
	     }finally{
	     }
	   }

	 }
 
 /* Metodo que publica un estado informativo del tramite en el CIS
    Por ejemplo la cancelación del Tramite ó el Incumplimiento de un requisito
    lo que para el SIPYMM es un Producto NO Conforme. */
public void upDateEstadoInformativoCIS(int iEjercicio, int iNumSolicitud, int iEtapaInformativaCIS,Connection conn){
	   TDCis dCis = new TDCis();
	   TFechas fecha = new TFechas();
	   Vector vcEstadoCis = new Vector();
	   TVDinRep vEstadoCis = new TVDinRep();
	   boolean lEstado=false;
	   try{
	     vcEstadoCis = this.findByNessted("","SELECT RS.IEJERCICIOCITA, RS.IIDCITA,O.ICVEOFICINACIS,EMT.ICVEETAPACIS,EMT.LENVIARFECHACIS,CT.ICVETRAMITECIS, REMT.cObsEnviadaCIS " +
	                                            "FROM TRAREGSOLICITUD RS " +
	                                            "LEFT JOIN GRLOFICINA O ON O.ICVEOFICINA=RS.ICVEOFICINA " +
	                                            "LEFT JOIN TRAETAPAXMODTRAM EMT ON EMT.ICVETRAMITE=RS.ICVETRAMITE AND EMT.ICVEMODALIDAD=RS.ICVEMODALIDAD " +
	                                            "LEFT JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=RS.ICVETRAMITE " +
	                                            "LEFT JOIN TRARegEtapasXModTram REMT ON REMT.IEJERCICIO = RS.IEJERCICIO AND REMT.INUMSOLICITUD = RS.INUMSOLICITUD AND REMT.ICVEETAPA = EMT.ICVEETAPA " +
	                                            "WHERE RS.IEJERCICIO = " +iEjercicio+
	                                            " AND RS.INUMSOLICITUD = " +iNumSolicitud , conn);
	                                            
	   }catch(Exception e){
	   }
	   if(vcEstadoCis.size()>0){
	     vEstadoCis = (TVDinRep) vcEstadoCis.get(0);
	     try{
	       lEstado = dCis.insertaEstadoTramite(
	                 vEstadoCis.getInt("IIDCITA"), /*cita*/
	                 vEstadoCis.getInt("ICVETRAMITECIS"), /*tramite*/
	                 1, /*no tramite*/
	                 iEtapaInformativaCIS, /*Estado del CIS*/
	                 vEstadoCis.getInt("LENVIARFECHACIS")==1?vEstadoCis.getString("cObsEnviadaCIS"):""/*observacion*/,
	                 vEstadoCis.getInt("ICVEOFICINACIS")/*area*/);
	     }catch(Exception e){ 
	       System.out.println(e);
	     }finally{
	     }
	   }

	 } 

/*
 public void EtapaConclusionArea(TVDinRep vData,boolean lResolucionPositiva, String cObservacion) throws
     DAOException{
   TDCis dCis = new TDCis();
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   boolean lGrabaEtapa = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }

     String lSQL =
         " insert into TRARegEtapasXModTram( " +
         "             iEjercicio, " +
         "             iNumSolicitud, " +
         "             iCveTramite, " +
         "             iCveModalidad, " +
         "             iCveEtapa, " +
         "             iOrden, " +
         "             iCveOficina, " +
         "             iCveDepartamento, " +
         "             iCveUsuario, " +
         "             tsRegistro, " +
         "             lAnexo) " +
         " values (?,?,?,?,?,?,?,?,?,?,?)";

     //AGREGAR AL ULTIMO ...
     Vector vcData = findByCustom("",
                                  " select IORDEN, ICVEETAPA " +
                                  "        from TRARegEtapasXModTram " +
                                  " where iEjercicio = " + vData.getInt("iEjercicio") +
                                  "   and iNumSolicitud = " + vData.getInt("iNumSolicitud") +
                                  " ORDER BY IORDEN DESC ");
     if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
        if(vUltimo.getInt("ICVEETAPA")==vData.getInt("iCveEtapa"))lGrabaEtapa=false;
     }else
        vData.put("iOrden",1);

     vData.addPK(vData.getString("iEjercicio"));
     vData.addPK(vData.getString("iNumSolicitud"));
     vData.addPK(vData.getString("iCveTramite"));
     vData.addPK(vData.getString("iCveModalidad"));
     vData.addPK(vData.getString("iCveEtapa"));
     vData.addPK(vData.getString("iOrden"));

     //se agrega Etapa en caso de que la etapa a insertar no sea la misma a la ultima insertada
     if(lGrabaEtapa){
       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.setInt(1,vData.getInt("iEjercicio"));
       lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
       lPStmt.setInt(3,vData.getInt("iCveTramite"));
       lPStmt.setInt(4,vData.getInt("iCveModalidad"));
       lPStmt.setInt(5,vData.getInt("iCveEtapa"));
       lPStmt.setInt(6,vData.getInt("iOrden"));
       lPStmt.setInt(7,vData.getInt("iCveOficina"));
       lPStmt.setInt(8,vData.getInt("iCveDepartamento"));
       lPStmt.setInt(9,vData.getInt("iCveUsuario"));
       lPStmt.setString(10,vData.getString("tsRegistro"));
       lPStmt.setInt(11,vData.getInt("lAnexo"));
       lPStmt.executeUpdate();
       if(cnNested == null)
         conn.commit();
      //inserta el estado de la solicitud en el CIS
       try{
         this.incertaEstadoCita(vData.getInt("iEjercicio"),vData.getInt("iNumSolicitud"),vData.getInt("iCveEtapa"));
       }catch(Exception e){
         System.out.println("Error en la incercion de la etapa en el CIS");
       }
     }

   } catch(SQLException se){
     se.printStackTrace();
     cMensaje = super.getSQLMessages(se);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("insert.rollback",e);
       }
     }
     lSuccess = false;
   } catch(Exception ex){
     cMensaje = "";
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
       throw new DAOException(cMensaje);
     return vData;
   }

 }*/


}



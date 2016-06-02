package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRARegTramXSol.java</p>
 * <p>Description: DAO de la entidad TRARegTramXSol</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRARegTramXSol extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
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
   * <p><b> insert into TRARegTramXSol(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveFormatoReg,dtCancelacion,iCveMotivoCancela,iCveUsuario,iEjercicioObs,iCveObservacion) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad, </b></p>
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
	TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL =
          "insert into TRARegTramXSol(iEjercicio, " +
          "iNumSolicitud, " +
          "dtCancelacion, " +
          "iCveMotivoCancela, " +
          "iCveUsuario, " +
          "cObs, cFolCancela) values (?,?,(current date),?,?,?,?)";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveMotivoCancela"));
      lPStmt.setInt(4,vData.getInt("iCveUsuario"));
      lPStmt.setString(5,vData.getString("cObs"));
      lPStmt.setString(6,vData.getString("cFolCancela"));
      lPStmt.executeUpdate();  //inserta la cancelacion
      
      String sqlOficDptoUsr = "SELECT GRLUSUARIOXOFIC.ICVEOFICINA, GRLUSUARIOXOFIC.ICVEDEPARTAMENTO FROM GRLUSUARIOXOFIC"+ 
    		  				  " join GRLOFICINA on GRLUSUARIOXOFIC.ICVEOFICINA=GRLOFICINA.ICVEOFICINA"+
    		  				  " join GRLDEPARTAMENTO on GRLUSUARIOXOFIC.ICVEDEPARTAMENTO=GRLDEPARTAMENTO.ICVEDEPARTAMENTO"+
    		  				  " where GRLUSUARIOXOFIC.ICVEUSUARIO="+vData.getInt("iCveUsuario");
      
     Vector vOfDp= new Vector();
     TVDinRep vdinOfDp= new TVDinRep();
     
     vOfDp=this.findByCustom("", sqlOficDptoUsr);
     
     if( vOfDp.size()>0 ){
    	 vdinOfDp=(TVDinRep)vOfDp.get(0);
    	 vData.put("iCveOfic", vdinOfDp.getInt("ICVEOFICINA"));
    	 vData.put("iCveDpto", vdinOfDp.getInt("ICVEDEPARTAMENTO"));
     }//se agrega la clave de oficina y dpto del usuario que registra la etapa de cancelacion     
     
     String sqlTramMod = "SELECT ICVETRAMITE,ICVEMODALIDAD FROM"+ 
    		 			 " TRAREGSOLICITUD where IEJERCICIO="+vData.getInt("iEjercicio")+
    		 			 " and INUMSOLICITUD="+vData.getInt("iNumSolicitud");

	Vector vTraMod= new Vector();
	TVDinRep vdinTraMod= new TVDinRep();
	
	vTraMod=this.findByCustom("", sqlTramMod);
	
	if( vTraMod.size()>0 ){
		vdinTraMod=(TVDinRep)vTraMod.get(0);
		vData.put("iCveTram", vdinTraMod.getInt("ICVETRAMITE"));
		vData.put("iCveMod", vdinTraMod.getInt("ICVEMODALIDAD"));
	}//se agrega la clave de tramite y modalidad de la solicitud para la etapa de cancelacion

	vData.put("iCveEtapa", Integer.parseInt(VParametros.getPropEspecifica("EtapaTramiteCancelado")) );
	vData.put("Observacion", "La solicitud ha sido cancelada.");
	
	regEtapasXModTram.cambiaEtapaADV(vData, conn);
      
      Vector vA=new Vector();
      TVDinRep din = new TVDinRep();
      String sqlIsTramInt= "SELECT LTRAMINERNET FROM TRAREGSOLICITUD where INUMSOLICITUD="+vData.getInt("iEjercicio")+
    		  			   " and IEJERCICIO="+vData.getInt("iNumSolicitud");
      vA=this.findByCustom("",sqlIsTramInt );
      
      if(vA.size()>0){
    	  din = (TVDinRep) vA.get(0);
    	  if(din.getInt("LTRAMINERNET")>0){
    		  regEtapasXModTram.upDateEstadoInformativoCIS(vData.getInt("iEjercicio"),vData.getInt("iNumSolicitud"),
                     Integer.parseInt(VParametros.getPropEspecifica("estadoCIS_Cancelado")),conn);
    	  }
      }
    	  
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
  /**
   * Elimina al registro a trav�s de la entidad dada por vData.
   * <p><b> delete from TRARegTramXSol where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad, </b></p>
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
      String lSQL = "delete from TRARegTramXSol where iEjercicio = ? AND iNumSolicitud = ? ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));

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
   * <p><b> update TRARegTramXSol set iCveFormatoReg=?, dtCancelacion=?, iCveMotivoCancela=?, iCveUsuario=?, iEjercicioObs=?, iCveObservacion=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad, </b></p>
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

      vData =  new TDGRLObservacion2().insert(vData,conn);

      String lSQL = "update TRARegTramXSol set dtCancelacion=(current date),iCveMotivoCancela=?,cObs=? where iEjercicio = ? AND iNumSolicitud = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveMotivoCancela"));
      lPStmt.setString(2,vData.getString("cObs"));
      lPStmt.setInt(3,vData.getInt("iEjercicio"));
      lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
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
}

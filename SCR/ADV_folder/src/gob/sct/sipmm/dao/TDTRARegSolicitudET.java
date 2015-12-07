package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*; 
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRARegSolicitudET.java</p>
 * <p>Description: DAO de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author cabrito
 * @version 1.0
 */
public class TDTRARegSolicitudET extends DAOBase{
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
   * <p><b> insert into TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
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
      String lSQL =
          "insert into TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iEjercicio) AS iEjercicio from TRARegSolicitud"); 
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iEjercicio",vUltimo.getInt("iEjercicio") + 1);
      }else
         vData.put("iEjercicio",1);
      vData.addPK(vData.getString("iEjercicio"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveSolicitante"));
      lPStmt.setInt(6,vData.getInt("iCveRepLegal"));
      lPStmt.setString(7,vData.getString("cNomAutorizaRecoger"));
      lPStmt.setInt(8,vData.getInt("iCveUsuRegistra"));
      lPStmt.setString(9,vData.getString("tsRegistro"));
      lPStmt.setDate(10,vData.getDate("dtLimiteEntregaDocs"));
      lPStmt.setDate(11,vData.getDate("dtEstimadaEntrega"));
      lPStmt.setInt(12,vData.getInt("lPagado"));
      lPStmt.setDate(13,vData.getDate("dtEntrega"));
      lPStmt.setInt(14,vData.getInt("iCveUsuEntrega"));
      lPStmt.setInt(15,vData.getInt("lResolucionPositiva"));
      lPStmt.setInt(16,vData.getInt("lDatosPreregistro"));
      lPStmt.setInt(17,vData.getInt("iIdBien"));
      lPStmt.setInt(18,vData.getInt("iCveOficina"));
      lPStmt.setInt(19,vData.getInt("iCveDepartamento"));
      lPStmt.setInt(20,vData.getInt("iEjercicioCita"));
      lPStmt.setInt(21,vData.getInt("iIdCita"));
      lPStmt.setInt(22,vData.getInt("iCveForma"));
      lPStmt.setInt(23,vData.getInt("lPrincipal"));
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
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRARegSolicitud where iEjercicio = ? AND iNumSolicitud = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
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
      String lSQL = "delete from TRARegSolicitud where iEjercicio = ? AND iNumSolicitud = ?  ";
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
   * <p><b> update TRARegSolicitud set iCveTramite=?, iCveModalidad=?, iCveSolicitante=?, iCveRepLegal=?, cNomAutorizaRecoger=?, iCveUsuRegistra=?, tsRegistro=?, dtLimiteEntregaDocs=?, dtEstimadaEntrega=?, lPagado=?, dtEntrega=?, iCveUsuEntrega=?, lResolucionPositiva=?, lDatosPreregistro=?, iIdBien=?, iCveOficina=?, iCveDepartamento=?, iEjercicioCita=?, iIdCita=?, iCveForma=?, lPrincipal=? where iEjercicio = ? AND iNumSolicitud = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
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
      String lSQL = "update TRARegSolicitud set iCveTramite=?, iCveModalidad=?, iCveSolicitante=?, iCveRepLegal=?, cNomAutorizaRecoger=?, iCveUsuRegistra=?, tsRegistro=?, dtLimiteEntregaDocs=?, dtEstimadaEntrega=?, lPagado=?, dtEntrega=?, iCveUsuEntrega=?, lResolucionPositiva=?, lDatosPreregistro=?, iIdBien=?, iCveOficina=?, iCveDepartamento=?, iEjercicioCita=?, iIdCita=?, iCveForma=?, lPrincipal=? where iEjercicio = ? AND iNumSolicitud = ? "; 

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iCveSolicitante"));
      lPStmt.setInt(4,vData.getInt("iCveRepLegal"));
      lPStmt.setString(5,vData.getString("cNomAutorizaRecoger"));
      lPStmt.setInt(6,vData.getInt("iCveUsuRegistra"));
      lPStmt.setString(7,vData.getString("tsRegistro"));
      lPStmt.setDate(8,vData.getDate("dtLimiteEntregaDocs"));
      lPStmt.setDate(9,vData.getDate("dtEstimadaEntrega"));
      lPStmt.setInt(10,vData.getInt("lPagado"));
      lPStmt.setDate(11,vData.getDate("dtEntrega"));
      lPStmt.setInt(12,vData.getInt("iCveUsuEntrega"));
      lPStmt.setInt(13,vData.getInt("lResolucionPositiva"));
      lPStmt.setInt(14,vData.getInt("lDatosPreregistro"));
      lPStmt.setInt(15,vData.getInt("iIdBien"));
      lPStmt.setInt(16,vData.getInt("iCveOficina"));
      lPStmt.setInt(17,vData.getInt("iCveDepartamento"));
      lPStmt.setInt(18,vData.getInt("iEjercicioCita"));
      lPStmt.setInt(19,vData.getInt("iIdCita"));
      lPStmt.setInt(20,vData.getInt("iCveForma"));
      lPStmt.setInt(21,vData.getInt("lPrincipal"));
      lPStmt.setInt(22,vData.getInt("iEjercicio"));
      lPStmt.setInt(23,vData.getInt("iNumSolicitud"));
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

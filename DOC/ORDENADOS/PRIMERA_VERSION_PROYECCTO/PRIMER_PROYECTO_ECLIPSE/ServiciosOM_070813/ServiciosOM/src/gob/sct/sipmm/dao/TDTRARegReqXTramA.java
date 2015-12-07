package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*; 
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRARegReqXTramA.java</p>
 * <p>Description: DAO de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
public class TDTRARegReqXTramA extends DAOBase{
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
   * <p><b> insert into TRARegReqXTram(iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,dtNotificacion,cNumOficio,dtOficio,lValido) values (?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
          "insert into TRARegReqXTram(iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,dtNotificacion,cNumOficio,dtOficio,lValido) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iNumSolicitud) AS iNumSolicitud from TRARegReqXTram"); 
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iNumSolicitud",vUltimo.getInt("iNumSolicitud") + 1);
      }else
         vData.put("iNumSolicitud",1);
      vData.addPK(vData.getString("iNumSolicitud"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveRequisito"));
      lPStmt.setInt(6,vData.getInt("iEjercicioFormato"));
      lPStmt.setInt(7,vData.getInt("iCveFormatoReg"));
      lPStmt.setDate(8,vData.getDate("dtRecepcion"));
      lPStmt.setInt(9,vData.getInt("iCveUsuRecibe"));
      lPStmt.setDate(10,vData.getDate("dtNotificacion"));
      lPStmt.setString(11,vData.getString("cNumOficio"));
      lPStmt.setDate(12,vData.getDate("dtOficio"));
      lPStmt.setInt(13,vData.getInt("lValido"));
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
   * <p><b> delete from TRARegReqXTram where iNumSolicitud = ? AND iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  </b></p>
   * <p><b> Campos Llave: iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
      String lSQL = "delete from TRARegReqXTram where iNumSolicitud = ? AND iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveRequisito"));

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
   * <p><b> update TRARegReqXTram set iEjercicioFormato=?, iCveFormatoReg=?, dtRecepcion=?, iCveUsuRecibe=?, dtNotificacion=?, cNumOficio=?, dtOficio=?, lValido=? where iNumSolicitud = ? AND iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  </b></p>
   * <p><b> Campos Llave: iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
      String lSQL = "update TRARegReqXTram set iEjercicioFormato=?, iCveFormatoReg=?, dtRecepcion=?, iCveUsuRecibe=?, dtNotificacion=?, cNumOficio=?, dtOficio=?, lValido=? where iNumSolicitud = ? AND iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? "; 

      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioFormato"));
      lPStmt.setInt(2,vData.getInt("iCveFormatoReg"));
      lPStmt.setDate(3,vData.getDate("dtRecepcion"));
      lPStmt.setInt(4,vData.getInt("iCveUsuRecibe"));
      lPStmt.setDate(5,vData.getDate("dtNotificacion"));
      lPStmt.setString(6,vData.getString("cNumOficio"));
      lPStmt.setDate(7,vData.getDate("dtOficio"));
      lPStmt.setInt(8,vData.getInt("lValido"));
      lPStmt.setInt(9,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(10,vData.getInt("iEjercicio"));
      lPStmt.setInt(11,vData.getInt("iCveTramite"));
      lPStmt.setInt(12,vData.getInt("iCveModalidad"));
      lPStmt.setInt(13,vData.getInt("iCveRequisito"));
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

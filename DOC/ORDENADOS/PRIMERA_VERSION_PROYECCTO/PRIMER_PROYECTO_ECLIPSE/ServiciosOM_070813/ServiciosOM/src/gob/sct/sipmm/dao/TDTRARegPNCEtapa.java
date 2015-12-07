package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRARegPNCEtapa.java</p>
 * <p>Description: DAO de la entidad TRARegPNCEtapa</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author RMiranda
 * @version 1.0
 */
public class TDTRARegPNCEtapa extends DAOBase{
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
   * <p><b> insert into TRARegPNCEtapa(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCvePNCXEtapa,iEjercicioPNC,iConsecutivoPNC) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCvePNCXEtapa, </b></p>
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
      String lSqlOrden = "SELECT IORDEN as iOrdenE,  " +
             "ICVEETAPA as iCveEtapa " +
             "FROM TRAREGETAPASXMODTRAM " +
             "WHERE IEJERCICIO = " + vData.getInt("iEjercicio") +
             " AND INUMSOLICITUD = " + vData.getInt("iNumSolicitud") +
             " AND ICVETRAMITE = " + vData.getInt("iCveTramite") +
             " AND ICVEMODALIDAD = " + vData.getInt("iCveModalidad") +
             " ORDER BY IORDEN DESC";
      //Buscar Orden
      Vector vcOrden = findByCustom("",lSqlOrden);
      if(vcOrden.size() > 0){
        TVDinRep vOrden = (TVDinRep) vcOrden.get(0);
        vData.put("iOrden", vOrden.getInt("iOrdenE"));
        vData.put("iCveEtapa", vOrden.getInt("iCveEtapa"));
      }

      //AGREGAR AL ULTIMO ...
      String lSQL = "insert into TRARegPNCEtapa(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCvePNCXEtapa,iEjercicioPNC,iConsecutivoPNC) values (?,?,?,?,?,?,?,?,?)";
      String lSQLMax = "select MAX(iCvePNCXEtapa) AS iCvePNCXEtapa from TRARegPNCEtapa"+
                                  " WHERE iEjercicio = " + vData.getInt("iEjercicio")+
                                  " AND iNumSolicitud = " + vData.getInt("iNumSolicitud")+
                                  " AND iCveTramite = " + vData.getInt("iCveTramite")+
                                  " AND iCveModalidad = " + vData.getInt("iCveModalidad")+
                                  " AND iCveEtapa = " + vData.getInt("iCveEtapa")+
                                  " AND iOrden = " + vData.getInt("iOrden");

      Vector vcData = findByCustom("",lSQLMax);
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCvePNCXEtapa",vUltimo.getInt("iCvePNCXEtapa") + 1);
      }else
         vData.put("iCvePNCXEtapa",1);
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveEtapa"));
      vData.addPK(vData.getString("iOrden"));
      vData.addPK(vData.getString("iCvePNCXEtapa"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveEtapa"));
      lPStmt.setInt(6,vData.getInt("iOrden"));
      lPStmt.setInt(7,vData.getInt("iCvePNCXEtapa"));
      lPStmt.setInt(8,vData.getInt("iEjercicioPNC"));
      lPStmt.setInt(9,vData.getInt("iConsecutivoPNC"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException e){
      e.printStackTrace();
      lSuccess = false;
      cMensaje = super.getSQLMessages(e);
    } catch(Exception ex){
      ex.printStackTrace();
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
        throw new DAOException(cMensaje);
      return vData;
    }
  }
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRARegPNCEtapa where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ? AND iCvePNCXEtapa = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCvePNCXEtapa, </b></p>
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
      String lSQL = "delete from TRARegPNCEtapa where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ? AND iCvePNCXEtapa = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveEtapa"));
      lPStmt.setInt(6,vData.getInt("iOrden"));
      lPStmt.setInt(7,vData.getInt("iCvePNCXEtapa"));

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
   * <p><b> update TRARegPNCEtapa set iEjercicioPNC=?, iConsecutivoPNC=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ? AND iCvePNCXEtapa = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCvePNCXEtapa, </b></p>
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
      String lSQL = "update TRARegPNCEtapa set iEjercicioPNC=?, iConsecutivoPNC=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? AND iOrden = ? AND iCvePNCXEtapa = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveEtapa"));
      vData.addPK(vData.getString("iOrden"));
      vData.addPK(vData.getString("iCvePNCXEtapa"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioPNC"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(3,vData.getInt("iEjercicio"));
      lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(5,vData.getInt("iCveTramite"));
      lPStmt.setInt(6,vData.getInt("iCveModalidad"));
      lPStmt.setInt(7,vData.getInt("iCveEtapa"));
      lPStmt.setInt(8,vData.getInt("iOrden"));
      lPStmt.setInt(9,vData.getInt("iCvePNCXEtapa"));
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

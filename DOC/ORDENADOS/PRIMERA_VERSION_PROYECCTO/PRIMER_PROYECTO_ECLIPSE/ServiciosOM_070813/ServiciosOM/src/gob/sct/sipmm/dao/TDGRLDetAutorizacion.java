package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLDetAutorizacion.java</p>
 * <p>Description: DAO de la entidad GRLDetAutorizacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author CSEN
 * @version 1.0
 */
public class TDGRLDetAutorizacion extends DAOBase{
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
   * <p><b> insert into GRLDetAutorizacion(iEjercicioAutorizacion,iNumAutorizacion,iOrden,iEstatus,tsRegistro,iCveUsuario,cObses,iEjercicioAutorizacionAnterior,iNumAutorizacionAnterior) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioAutorizacion,iNumAutorizacion, </b></p>
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
    TFechas fecha = new TFechas();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      // Se obtiene el ejercicio(año) del servidor
      TFechas tfSol = new TFechas("44");
      java.sql.Date dtHoy = new java.sql.Date(new java.util.Date().getTime());
      String cDmy = tfSol.getFechaDDMMYYYY(dtHoy,"/");
      String cEjercicio = cDmy.substring(6);

      String lSQL =
          "insert into GRLDetAutorizacion(iEjercicioAutorizacion,iNumAutorizacion,iOrden,iEstatus,tsRegistro,iCveUsuario,cObses,iEjercicioAutorizacionAnterior,iNumAutorizacionAnterior) values (?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iNumAutorizacion) AS iNumAutorizacion from GRLDetAutorizacion where iEjercicioAutorizacion = " + cEjercicio);
      vData.put("iEjercicioAutorizacion",cEjercicio);
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iNumAutorizacion",vUltimo.getInt("iNumAutorizacion") + 1);
      }else
         vData.put("iNumAutorizacion",1);

         vData.addPK(vData.getString("iEjercicioAutorizacion"));
         vData.addPK(vData.getString("iNumAutorizacion"));
      //...
  //       vData.put("iEjercicioAutorizacion",cEjercicio);

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEjercicioAutorizacion"));
        lPStmt.setInt(2,vData.getInt("iNumAutorizacion"));


      int iEstatus = vData.getInt("iEstatus");

      if(iEstatus == 1){  // 1 = captura
        lPStmt.setInt(3,1); // el orden es = 1 ya que el iEstatus es captura
        lPStmt.setInt(4,vData.getInt("iEstatus"));
        lPStmt.setTimestamp(5,vData.getTimeStamp("tsRegistro"));
        lPStmt.setInt(6,vData.getInt("iCveUsuario"));
        lPStmt.setString(7,"");
        lPStmt.setString(8,vData.getString("iEjercicioAutorizacion"));
        lPStmt.setInt(9,vData.getInt("iNumAutorizacion"));
      }else{
        lPStmt.setInt(3,vData.getInt("iOrden"));
        lPStmt.setInt(4,vData.getInt("iEstatus"));
        lPStmt.setTimestamp(5,fecha.getThisTime());
        lPStmt.setInt(6,vData.getInt("iCveUsuario"));
        lPStmt.setString(7,vData.getString("cObses"));
        lPStmt.setInt(8,vData.getInt("iEjercicioAutorizacionAnterior"));
        lPStmt.setInt(9,vData.getInt("iNumAutorizacionAnterior"));

        }


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
   * <p><b> delete from GRLDetAutorizacion where iEjercicioAutorizacion = ? AND iNumAutorizacion = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioAutorizacion,iNumAutorizacion, </b></p>
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
      String lSQL = "delete from GRLDetAutorizacion where iEjercicioAutorizacion = ? AND iNumAutorizacion = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioAutorizacion"));
      lPStmt.setInt(2,vData.getInt("iNumAutorizacion"));

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
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from GRLDetAutorizacion where iEjercicioAutorizacion = ? AND iNumAutorizacion = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioAutorizacion,iNumAutorizacion, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
   //SE HIZO OTRO DELETE PARA GRLDETAUTORIZACION IEJERCICIOAUTORIZACIONANTERIOR && INUMAUTORIZACIONANTERIOR
  public boolean deleteA(TVDinRep vData,Connection cnNested)throws
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
      String lSQL = "delete from GRLDetAutorizacion where iEjercicioAutorizacionAnterior = ? AND iNumAutorizacionAnterior = ? ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioAutorizacionAnterior"));
      lPStmt.setInt(2,vData.getInt("iNumAutorizacionAnterior"));

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
   * <p><b> update GRLDetAutorizacion set iOrden=?, iEstatus=?, tsRegistro=?, iCveUsuario=?, cObses=?, iEjercicioAutorizacionAnterior=?, iNumAutorizacionAnterior=? where iEjercicioAutorizacion = ? AND iNumAutorizacion = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioAutorizacion,iNumAutorizacion, </b></p>
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
      String lSQL = "update GRLDetAutorizacion set iOrden=?, iEstatus=?, tsRegistro=?, iCveUsuario=?, cObses=?, iEjercicioAutorizacionAnterior=?, iNumAutorizacionAnterior=? where iEjercicioAutorizacion = ? AND iNumAutorizacion = ? ";

      vData.addPK(vData.getString("iEjercicioAutorizacion"));
      vData.addPK(vData.getString("iNumAutorizacion"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iOrden"));
      lPStmt.setInt(2,vData.getInt("iEstatus"));
      lPStmt.setString(3,vData.getString("tsRegistro"));
      lPStmt.setInt(4,vData.getInt("iCveUsuario"));
      lPStmt.setString(5,vData.getString("cObses"));
      lPStmt.setInt(6,vData.getInt("iEjercicioAutorizacionAnterior"));
      lPStmt.setInt(7,vData.getInt("iNumAutorizacionAnterior"));
      lPStmt.setInt(8,vData.getInt("iEjercicioAutorizacion"));
      lPStmt.setInt(9,vData.getInt("iNumAutorizacion"));
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

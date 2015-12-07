package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLProdXOficDepto.java</p>
 * <p>Description: DAO de la entidad GRLProdXOficDepto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
public class TDGRLProdXOficDepto extends DAOBase{
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
   * <p><b> insert into GRLProdXOficDepto(iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,dtIniAsignacion,iCveFormatoSalida) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,iCveFormatoSalida, </b></p>
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
          "insert into GRLProdXOficDepto(iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,dtIniAsignacion,iCveFormatoSalida) values (?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...

      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iCveDepartamento"));
      lPStmt.setInt(3,vData.getInt("iCveProceso"));
      lPStmt.setInt(4,vData.getInt("iCveProducto"));
      lPStmt.setDate(5,vData.getDate("dtIniAsignacion"));
      lPStmt.setInt(6,vData.getInt("iCveFormatoSalida"));
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
  public TVDinRep guardaCambios(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    TFechas tFecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      if(!vData.getString("cAgregar").equals("")){
        String[] cAgregar = vData.getString("cAgregar").split(",");
        for(int i = 0;i < cAgregar.length;i++){
          String lSQL =
              "insert into GRLProdXOficDepto(iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,dtIniAsignacion) values (?,?,?,?,?)";

          lPStmt = conn.prepareStatement(lSQL);
          lPStmt.setInt(1,vData.getInt("iCveOficina"));
          lPStmt.setInt(2,Integer.parseInt(cAgregar[i]));
          lPStmt.setInt(3,vData.getInt("iCveProceso"));
          lPStmt.setInt(4,vData.getInt("iCveProducto"));
          lPStmt.setDate(5,tFecha.getDateSQL(tFecha.getThisTime()));
          lPStmt.executeUpdate();
          conn.commit();
          lPStmt.close();
        }
      }
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert  ",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(SQLException e){
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
    }
    if(!vData.getString("cBorrar").equals("")){
      String cMsg="";
      String[] cQuitar = vData.getString("cBorrar").split(",");

      for (int i = 0; i<cQuitar.length;i++){
        try{
          if(cnNested == null){
            dbConn = new DbConnection(dataSourceName);
            conn = dbConn.getConnection();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(2);
          }
        }
        catch(Exception ex){
          warn("delete.close",ex);
        }
      String cSql = "delete from GRLProdXOficDepto where iCveOficina = ? AND iCveDepartamento = ? AND iCveProceso = ? AND iCveProducto = ?";

        try{

          lPStmt = conn.prepareStatement(cSql);

          lPStmt.setInt(1,vData.getInt("iCveOficina"));
          lPStmt.setInt(2,Integer.parseInt(cQuitar[i]));
          lPStmt.setInt(3,vData.getInt("iCveProceso"));
          lPStmt.setInt(4,vData.getInt("iCveProducto"));
          lPStmt.executeUpdate();
          conn.commit();
          lPStmt.close();
        } catch(SQLException sqle){
          lSuccess = false;
          cMsg += "\n"+sqle.getErrorCode();
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
        }
      }
      if(lSuccess == false)
        throw new DAOException(cMsg);
      try{
      }catch(Exception ex){
        warn("delete",ex);
      }
    }
    return vData;

  }


  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from GRLProdXOficDepto where iCveOficina = ? AND iCveDepartamento = ? AND iCveProceso = ? AND iCveProducto = ? AND iCveFormatoSalida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,iCveFormatoSalida, </b></p>
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
      String lSQL = "delete from GRLProdXOficDepto where iCveOficina = ? AND iCveDepartamento = ? AND iCveProceso = ? AND iCveProducto = ? AND iCveFormatoSalida = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iCveDepartamento"));
      lPStmt.setInt(3,vData.getInt("iCveProceso"));
      lPStmt.setInt(4,vData.getInt("iCveProducto"));
      lPStmt.setInt(5,vData.getInt("iCveFormatoSalida"));
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
   * <p><b> update GRLProdXOficDepto set iCveFormatoSalida=? where iCveOficina = ? AND iCveDepartamento = ? AND iCveProceso = ? AND iCveProducto = ? AND iCveFormatoSalida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,iCveFormatoSalida, </b></p>
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
      String lSQL = "update GRLProdXOficDepto set iCveFormatoSalida=?, dtIniAsignacion = ? where iCveOficina = ? AND iCveDepartamento = ? AND iCveProceso = ? AND iCveProducto = ? ";

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iCveDepartamento"));
      vData.addPK(vData.getString("iCveProceso"));
      vData.addPK(vData.getString("iCveProducto"));
      vData.addPK(vData.getString("iCveFormatoSalida"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveFormatoSalida"));
      lPStmt.setDate(2,vData.getDate("dtInicioModificar"));
      lPStmt.setInt(3,vData.getInt("iCveOficina"));
      lPStmt.setInt(4,vData.getInt("iCveDepartamento"));
      lPStmt.setInt(5,vData.getInt("iCveProceso"));
      lPStmt.setInt(6,vData.getInt("iCveProducto"));
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

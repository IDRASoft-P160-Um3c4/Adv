package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>Title: TDRGSLuzEclipse.java</p>
 * <p>Description: DAO de la entidad RGSLuzEclipse</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
public class TDRGSLuzEclipse
  extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepción de tipo DAO
   * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
   */
  public Vector findByCustom(String cKey, String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey, cSQL, dataSourceName);
    } catch(Exception e){
      cError = e.getMessage();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into RGSLuzEclipse(iCveSenal,iConsecutivo,iOrden,dLuz,dEclipse) values (?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveSenal,iConsecutivo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData, Connection cnNested) throws
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
        "insert into RGSLuzEclipse(iCveSenal,iConsecutivo,iOrden,dLuz,dEclipse) values (?,?,?,?,?)";

      Vector vcData = findByCustom("", "select MAX(iConsecutivo) AS iConsecutivo from RGSLuzEclipse WHERE iCveSenal = " + vData.getInt("iCveSenal"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivo", vUltimo.getInt("iConsecutivo") + 1);
      } else
        vData.put("iConsecutivo", 1);
      vData.addPK(vData.getString("iConsecutivo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, vData.getInt("iCveSenal"));
      lPStmt.setInt(2, vData.getInt("iConsecutivo"));
      lPStmt.setInt(3, vData.getInt("iOrden"));
      lPStmt.setDouble(4, vData.getDouble("dLuz"));
      lPStmt.setDouble(5, vData.getDouble("dEclipse"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("insert", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback", e);
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
        warn("insert.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from RGSLuzEclipse where iCveSenal = ? AND iConsecutivo = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal,iConsecutivo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData, Connection cnNested) throws
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

      String lSQL = "delete from RGSLuzEclipse where iCveSenal = ? AND iConsecutivo = ?  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1, vData.getInt("iCveSenal"));
      lPStmt.setInt(2, vData.getInt("iConsecutivo"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("delete", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("delete.rollback", e);
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
        warn("delete.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return lSuccess;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update RGSLuzEclipse set iOrden=?, dLuz=?, dEclipse=? where iCveSenal = ? AND iConsecutivo = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal,iConsecutivo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData, Connection cnNested) throws
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
      String lSQL = "update RGSLuzEclipse set iOrden=?, dLuz=?, dEclipse=? where iCveSenal = ? AND iConsecutivo = ? ";

      vData.addPK(vData.getString("iCveSenal"));
      vData.addPK(vData.getString("iConsecutivo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, vData.getInt("iOrden"));
      lPStmt.setDouble(2, vData.getDouble("dLuz"));
      lPStmt.setDouble(3, vData.getDouble("dEclipse"));
      lPStmt.setInt(4, vData.getInt("iCveSenal"));
      lPStmt.setInt(5, vData.getInt("iConsecutivo"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("update", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback", e);
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
        warn("update.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);

      return vData;
    }
  }

  public Vector findLuzEclipse(int iSenal) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = "SELECT LE.iCveSenal, LE.iConsecutivo, LE.iOrden, LE.dLuz, LE.dEclipse "+
                    " FROM RGSLuzEclipse LE "+
                    ((iSenal > 0)?" WHERE LE.iCveSenal = "+iSenal:"" )+
                    " ORDER BY LE.iCveSenal, LE.iOrden ";
      vcRecords = this.FindByGeneric("", cSQL, dataSourceName);
    } catch(Exception e){
      cError = e.getMessage();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }
}

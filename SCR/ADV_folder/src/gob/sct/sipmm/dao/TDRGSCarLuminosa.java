package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>Title: TDRGSCarLuminosa.java</p>
 * <p>Description: DAO de la entidad RGSCarLuminosa</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
public class TDRGSCarLuminosa
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
   * <p><b> insert into RGSCarLuminosa(iCveSenal,iConsCarLuminosa,iOrden,lDestellante,iCveTipoDestello,iNumDestellos,iNumDestellosMinuto,iGradosIniCobertura,iGradosFinCobertura,iCveColor) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveSenal,iConsCarLuminosa, </b></p>
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
        "insert into RGSCarLuminosa(iCveSenal,iConsCarLuminosa,iOrden,lDestellante,iCveTipoDestello,iNumDestellos,iNumDestellosMinuto,iGradosIniCobertura,iGradosFinCobertura,iCveColor) values (?,?,?,?,?,?,?,?,?,?)";

      Vector vcData = findByCustom("", "select MAX(iConsCarLuminosa) AS iConsCarLuminosa from RGSCarLuminosa WHERE icveSenal = " + vData.getInt("iCveSenal"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsCarLuminosa", vUltimo.getInt("iConsCarLuminosa") + 1);
      } else
        vData.put("iConsCarLuminosa", 1);
      vData.addPK(vData.getString("iConsCarLuminosa"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, vData.getInt("iCveSenal"));
      lPStmt.setInt(2, vData.getInt("iConsCarLuminosa"));
      lPStmt.setInt(3, vData.getInt("iOrden"));
      lPStmt.setInt(4, vData.getInt("lDestellante"));
      lPStmt.setInt(5, vData.getInt("iCveTipoDestello"));
      lPStmt.setInt(6, vData.getInt("iNumDestellos"));
      lPStmt.setInt(7, vData.getInt("iNumDestellosMinuto"));
      lPStmt.setInt(8, vData.getInt("iGradosIniCobertura"));
      lPStmt.setInt(9, vData.getInt("iGradosFinCobertura"));
      lPStmt.setInt(10, vData.getInt("iCveColor"));
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
   * <p><b> delete from RGSCarLuminosa where iCveSenal = ? AND iConsCarLuminosa = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal,iConsCarLuminosa, </b></p>
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

      String lSQL = "delete from RGSCarLuminosa where iCveSenal = ? AND iConsCarLuminosa = ?  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1, vData.getInt("iCveSenal"));
      lPStmt.setInt(2, vData.getInt("iConsCarLuminosa"));

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
   * <p><b> update RGSCarLuminosa set iOrden=?, lDestellante=?, iCveTipoDestello=?, iNumDestellos=?, iNumDestellosMinuto=?, iGradosIniCobertura=?, iGradosFinCobertura=?, iCveColor=? where iCveSenal = ? AND iConsCarLuminosa = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal,iConsCarLuminosa, </b></p>
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
      String lSQL = "update RGSCarLuminosa set iOrden=?, lDestellante=?, iCveTipoDestello=?, iNumDestellos=?, iNumDestellosMinuto=?, iGradosIniCobertura=?, iGradosFinCobertura=?, iCveColor=? where iCveSenal = ? AND iConsCarLuminosa = ? ";

      vData.addPK(vData.getString("iCveSenal"));
      vData.addPK(vData.getString("iConsCarLuminosa"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, vData.getInt("iOrden"));
      lPStmt.setInt(2, vData.getInt("lDestellante"));
      lPStmt.setInt(3, vData.getInt("iCveTipoDestello"));
      lPStmt.setInt(4, vData.getInt("iNumDestellos"));
      lPStmt.setInt(5, vData.getInt("iNumDestellosMinuto"));
      lPStmt.setInt(6, vData.getInt("iGradosIniCobertura"));
      lPStmt.setInt(7, vData.getInt("iGradosFinCobertura"));
      lPStmt.setInt(8, vData.getInt("iCveColor"));
      lPStmt.setInt(9, vData.getInt("iCveSenal"));
      lPStmt.setInt(10, vData.getInt("iConsCarLuminosa"));
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

  public Vector findCarLuminosa(int iSenal) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = "SELECT CL.iCveSenal, CL.iConsCarLuminosa, CL.iOrden, CL.lDestellante, "+
                    " CL.iCveTipoDestello, CL.iNumDestellos, CL.iNumDestellosMinuto, "+
                    " CL.iGradosIniCobertura, CL.iGradosFinCobertura, CL.iCveColor, "+
                    " C.cDscColor, C.cAbreviatura AS cAbrevColor, TD.cDscTipoDestello, "+
                    " TD.cAbreviatura AS cAbrevDestello "+
                    " FROM RGSCarLuminosa CL "+
                    " JOIN RGSColor C ON C.iCveColor = CL.iCveColor "+
                    " JOIN RGSTipoDestello TD ON TD.iCveTipoDestello = CL.iCveTipoDestello "+
                    ((iSenal > 0)?" WHERE CL.iCveSenal = "+iSenal:"" )+
                    " ORDER BY CL.iCveSenal, CL.iOrden ";
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

package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLObservaLarga.java</p>
 * <p>Description: DAO de la entidad GRLObservaLarga</p>
 * <p>Copyright: Copyright (c) 2006 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Angel Zamora
 * @version 1.0
 */
public class TDGRLObservaLarga extends DAOBase{
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
   * <p><b> insert into GRLObservaLarga(iEjercicioObsLarga,iCveObsLarga,cDscObsLarga) values (?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioObsLarga,iCveObsLarga, </b></p>
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

      // Se obtiene el ejercicio(año) del servidor
    TFechas tfSol = new TFechas("44");
    java.sql.Date dtHoy = new java.sql.Date(new java.util.Date().getTime());
    String cDmy = tfSol.getFechaDDMMYYYY(dtHoy,"/");
    String cEjercicio = cDmy.substring(6);

      String lSQL =
          "insert into GRLObservaLarga(iEjercicioObsLarga,iCveObsLarga,cDscObsLarga) values (?,?,?)";



      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveObsLarga) AS iCveObsLarga from GRLObservaLarga where iEjercicioObsLarga = "+cEjercicio);
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iEjercicioObsLarga", cEjercicio);
         vData.put("iCveObsLarga",vUltimo.getInt("iCveObsLarga") + 1);
      }else{
        vData.put("iEjercicioObsLarga", cEjercicio);
        vData.put("iCveObsLarga",1);
      }
      vData.addPK(vData.getString("iEjercicioObsLarga"));
      vData.addPK(vData.getString("iCveObsLarga"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,cEjercicio);
      lPStmt.setInt(2,vData.getInt("iCveObsLarga"));
      lPStmt.setString(3,vData.getString("cDscObsLarga"));
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      ex.printStackTrace();
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
   * <p><b> delete from GRLObservaLarga where iEjercicioObsLarga = ? AND iCveObsLarga = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioObsLarga,iCveObsLarga, </b></p>
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
      String lSQL = "delete from GRLObservaLarga where iEjercicioObsLarga = ? AND iCveObsLarga = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioObsLarga"));
      lPStmt.setInt(2,vData.getInt("iCveObsLarga"));

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
   * <p><b> update GRLObservaLarga set cDscObsLarga=? where iEjercicioObsLarga = ? AND iCveObsLarga = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioObsLarga,iCveObsLarga, </b></p>
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
      String lSQL = "update GRLObservaLarga set cDscObsLarga=? where iEjercicioObsLarga = ? AND iCveObsLarga = ? ";

      vData.addPK(vData.getString("iEjercicioObsLarga"));
      vData.addPK(vData.getString("iCveObsLarga"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDscObsLarga"));
      lPStmt.setInt(2,vData.getInt("iEjercicioObsLarga"));
      lPStmt.setInt(3,vData.getInt("iCveObsLarga"));
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

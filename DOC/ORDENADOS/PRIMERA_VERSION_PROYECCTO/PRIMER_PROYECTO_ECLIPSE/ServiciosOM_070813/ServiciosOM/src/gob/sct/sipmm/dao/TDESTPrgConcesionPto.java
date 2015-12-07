package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDESTPrgConcesionPto.java</p>
 * <p>Description: DAO de la entidad ESTPrgConcesionPto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
public class TDESTPrgConcesionPto extends DAOBase{
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
   * <p><b> insert into ESTPrgConcesionPto(iEjercicioSeguimiento,iNumMovtoSegto,iConsecutivoPrgConcesionPto,iCvePuerto,dProgramado1,dProgramado2) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iConsecutivoPrgConcesionPto, </b></p>
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
      }else
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      String lSQL =
          "insert into ESTPrgConcesionPto("+
          "iEjercicioSeguimiento,iNumMovtoSegto,"+
          "iConsecutivoPrgConcesionPto,iCvePuerto,"+
          "dProgramado1,dProgramado2) "+
          "values (?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = this.FindByGenericReadUncommitted("","select MAX(iConsecutivoPrgConcesionPto) AS iConsecutivoPrgConcesionPto "+
                                   "from ESTPrgConcesionPto where IEJERCICIOSEGUIMIENTO = "+vData.getString("iEjercicioSeguimiento")+
                                   " AND INUMMOVTOSEGTO = "+vData.getString("iNumMovtoSegto"),dataSourceName);
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iConsecutivoPrgConcesionPto",vUltimo.getInt("iConsecutivoPrgConcesionPto") + 1);
      }else
         vData.put("iConsecutivoPrgConcesionPto",1);
      vData.addPK(vData.getString("iEjercicioSeguimiento"));
      vData.addPK(vData.getString("iNumMovtoSegto"));
      vData.addPK(vData.getString("iConsecutivoPrgConcesionPto"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.setInt(3,vData.getInt("iConsecutivoPrgConcesionPto"));
      lPStmt.setInt(4,vData.getInt("iCvePuerto"));
      lPStmt.setFloat(5,vData.getFloat("dProgramado1"));
      lPStmt.setFloat(6,vData.getFloat("dProgramado2"));
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
   * <p><b> delete from ESTPrgConcesionPto where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iConsecutivoPrgConcesionPto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iConsecutivoPrgConcesionPto, </b></p>
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
      String lSQL = "delete from ESTPrgConcesionPto where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iConsecutivoPrgConcesionPto = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.setInt(3,vData.getInt("iConsecutivoPrgConcesionPto"));

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
   * <p><b> update ESTPrgConcesionPto set iCvePuerto=?, dProgramado1=?, dProgramado2=? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iConsecutivoPrgConcesionPto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iConsecutivoPrgConcesionPto, </b></p>
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
      String lSQL = "update ESTPrgConcesionPto set iCvePuerto=?, dProgramado1=?, dProgramado2=? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iConsecutivoPrgConcesionPto = ? ";

      vData.addPK(vData.getString("iEjercicioSeguimiento"));
      vData.addPK(vData.getString("iNumMovtoSegto"));
      vData.addPK(vData.getString("iConsecutivoPrgConcesionPto"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePuerto"));
      lPStmt.setFloat(2,vData.getFloat("dProgramado1"));
      lPStmt.setFloat(3,vData.getFloat("dProgramado2"));

      lPStmt.setInt(4,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(5,vData.getInt("iNumMovtoSegto"));
      lPStmt.setInt(6,vData.getInt("iConsecutivoPrgConcesionPto"));
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

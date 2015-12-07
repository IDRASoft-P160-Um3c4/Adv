package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*; 
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRACaracxReqB.java</p>
 * <p>Description: DAO de la entidad TRACaracxRequisito</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author aHernandez
 * @version 1.0
 */
public class TDTRACaracxReqB extends DAOBase{
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
   * <p><b> insert into TRACaracxRequisito(iCveRequisito,iCveCaracteristica,cDscCaracteristica,cDscBreve,lVigente,lEnRecepcion,lEnProceso,lMandatorio,iCveRequisito,iCveCaracteristica,cDscCaracteristica,cDscBreve,lVigente,lEnRecepcion,lEnProceso,lMandatorio) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveRequisito,iCveCaracteristica, </b></p>
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
          "insert into TRACaracxRequisito(iCveRequisito,iCveCaracteristica,cDscCaracteristica,cDscBreve,lVigente,lEnRecepcion,lEnProceso,lMandatorio,iCveRequisito,iCveCaracteristica,cDscCaracteristica,cDscBreve,lVigente,lEnRecepcion,lEnProceso,lMandatorio) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveRequisito) AS iCveRequisito from TRACaracxRequisito"); 
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveRequisito",vUltimo.getInt("iCveRequisito") + 1);
      }else
         vData.put("iCveRequisito",1);
      vData.addPK(vData.getString("iCveRequisito"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveRequisito"));
      lPStmt.setInt(2,vData.getInt("iCveCaracteristica"));
      lPStmt.setString(3,vData.getString("cDscCaracteristica"));
      lPStmt.setString(4,vData.getString("cDscBreve"));
      lPStmt.setInt(5,vData.getInt("lVigente"));
      lPStmt.setInt(6,vData.getInt("lEnRecepcion"));
      lPStmt.setInt(7,vData.getInt("lEnProceso"));
      lPStmt.setInt(8,vData.getInt("lMandatorio"));
      lPStmt.setInt(9,vData.getInt("iCveRequisito"));
      lPStmt.setInt(10,vData.getInt("iCveCaracteristica"));
      lPStmt.setString(11,vData.getString("cDscCaracteristica"));
      lPStmt.setString(12,vData.getString("cDscBreve"));
      lPStmt.setInt(13,vData.getInt("lVigente"));
      lPStmt.setInt(14,vData.getInt("lEnRecepcion"));
      lPStmt.setInt(15,vData.getInt("lEnProceso"));
      lPStmt.setInt(16,vData.getInt("lMandatorio"));
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
   * <p><b> delete from TRACaracxRequisito where iCveRequisito = ? AND iCveCaracteristica = ?  </b></p>
   * <p><b> Campos Llave: iCveRequisito,iCveCaracteristica, </b></p>
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
      String lSQL = "delete from TRACaracxRequisito where iCveRequisito = ? AND iCveCaracteristica = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveRequisito"));
      lPStmt.setInt(2,vData.getInt("iCveCaracteristica"));

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
   * <p><b> update TRACaracxRequisito set cDscCaracteristica=?, cDscBreve=?, lVigente=?, lEnRecepcion=?, lEnProceso=?, lMandatorio=?, iCveRequisito=?, iCveCaracteristica=?, cDscCaracteristica=?, cDscBreve=?, lVigente=?, lEnRecepcion=?, lEnProceso=?, lMandatorio=? where iCveRequisito = ? AND iCveCaracteristica = ?  </b></p>
   * <p><b> Campos Llave: iCveRequisito,iCveCaracteristica, </b></p>
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
      String lSQL = "update TRACaracxRequisito set cDscCaracteristica=?, cDscBreve=?, lVigente=?, lEnRecepcion=?, lEnProceso=?, lMandatorio=?, iCveRequisito=?, iCveCaracteristica=?, cDscCaracteristica=?, cDscBreve=?, lVigente=?, lEnRecepcion=?, lEnProceso=?, lMandatorio=? where iCveRequisito = ? AND iCveCaracteristica = ? "; 

      vData.addPK(vData.getString("iCveRequisito"));
      vData.addPK(vData.getString("iCveCaracteristica"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDscCaracteristica"));
      lPStmt.setString(2,vData.getString("cDscBreve"));
      lPStmt.setInt(3,vData.getInt("lVigente"));
      lPStmt.setInt(4,vData.getInt("lEnRecepcion"));
      lPStmt.setInt(5,vData.getInt("lEnProceso"));
      lPStmt.setInt(6,vData.getInt("lMandatorio"));
      lPStmt.setInt(7,vData.getInt("iCveRequisito"));
      lPStmt.setInt(8,vData.getInt("iCveCaracteristica"));
      lPStmt.setString(9,vData.getString("cDscCaracteristica"));
      lPStmt.setString(10,vData.getString("cDscBreve"));
      lPStmt.setInt(11,vData.getInt("lVigente"));
      lPStmt.setInt(12,vData.getInt("lEnRecepcion"));
      lPStmt.setInt(13,vData.getInt("lEnProceso"));
      lPStmt.setInt(14,vData.getInt("lMandatorio"));
      lPStmt.setInt(15,vData.getInt("iCveRequisito"));
      lPStmt.setInt(16,vData.getInt("iCveCaracteristica"));
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

package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*; 
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLPuertoCPO.java</p>
 * <p>Description: DAO de la entidad GRLPuerto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo L Peña
 * @version 1.0
 */
public class TDGRLPuertoCPO extends DAOBase{
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
   * <p><b> insert into GRLPuerto(iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCvePuerto, </b></p>
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
          "insert into GRLPuerto(iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCvePuerto) AS iCvePuerto from GRLPuerto"); 
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCvePuerto",vUltimo.getInt("iCvePuerto") + 1);
      }else
         vData.put("iCvePuerto",1);
      vData.addPK(vData.getString("iCvePuerto"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePuerto"));
      lPStmt.setString(2,vData.getString("cDscPuerto"));
      lPStmt.setInt(3,vData.getInt("iCveTipoPuerto"));
      lPStmt.setInt(4,vData.getInt("iCvePais"));
      lPStmt.setInt(5,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(6,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(7,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(8,vData.getInt("iCvePuerto"));
      lPStmt.setString(9,vData.getString("cDscPuerto"));
      lPStmt.setInt(10,vData.getInt("iCveTipoPuerto"));
      lPStmt.setInt(11,vData.getInt("iCvePais"));
      lPStmt.setInt(12,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(13,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(14,vData.getInt("iCveLocalidad"));
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
   * <p><b> delete from GRLPuerto where iCvePuerto = ?  </b></p>
   * <p><b> Campos Llave: iCvePuerto, </b></p>
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
      String lSQL = "delete from GRLPuerto where iCvePuerto = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCvePuerto"));

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
   * <p><b> update GRLPuerto set cDscPuerto=?, iCveTipoPuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=?, iCvePuerto=?, cDscPuerto=?, iCveTipoPuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCvePuerto = ?  </b></p>
   * <p><b> Campos Llave: iCvePuerto, </b></p>
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
      String lSQL = "update GRLPuerto set cDscPuerto=?, iCveTipoPuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=?, iCvePuerto=?, cDscPuerto=?, iCveTipoPuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCvePuerto = ? "; 

      vData.addPK(vData.getString("iCvePuerto"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDscPuerto"));
      lPStmt.setInt(2,vData.getInt("iCveTipoPuerto"));
      lPStmt.setInt(3,vData.getInt("iCvePais"));
      lPStmt.setInt(4,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(5,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(6,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(7,vData.getInt("iCvePuerto"));
      lPStmt.setString(8,vData.getString("cDscPuerto"));
      lPStmt.setInt(9,vData.getInt("iCveTipoPuerto"));
      lPStmt.setInt(10,vData.getInt("iCvePais"));
      lPStmt.setInt(11,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(12,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(13,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(14,vData.getInt("iCvePuerto"));
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

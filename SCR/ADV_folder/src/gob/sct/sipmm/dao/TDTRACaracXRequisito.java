package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRACaracXRequisito.java</p>
 * <p>Description: DAO de la entidad TRACaracXRequisito</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDTRACaracXRequisito extends DAOBase{
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
    } catch(SQLException e){
      cError = e.getMessage();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }
  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into VEHTipoVehiculo(iCveTipoVeh,cDscTipoVeh,cDscBreve,lActivo,lMotorUnico,lRequiereOrdOpera) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTipoVeh, </b></p>
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
          "insert into TRACaracXRequisito(iCveRequisito,iCveCaracteristica,cDscCaracteristica,cDscBreve,lVigente,lEnRecepcion,lEnProceso,lMandatorio) values (?,?,?,?,?,?,?,?)";
//System.out.print(" ****** "+vData.getInt("iCveRequisito")+" ****** "+vData.getInt("iCveCaracteristica")+" ****** "+vData.getInt("lVigente"));
      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveCaracteristica) AS iCveCaracteristica from TRACaracXRequisito WHERE iCveRequisito = " + vData.getString("iCveRequisito"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveCaracteristica",vUltimo.getInt("iCveCaracteristica") + 1);
      }else
         vData.put("iCveCaracteristica",1);
      vData.addPK(vData.getString("iCveRequisito"));
      vData.addPK(vData.getString("iCveCaracteristica"));
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
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
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
   * <p><b> delete from VEHTipoVehiculo where iCveTipoVeh = ?  </b></p>
   * <p><b> Campos Llave: iCveTipoVeh, </b></p>
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
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from TRACaracXRequisito where iCveRequisito = ? and iCveCaracteristica = ? ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveRequisito"));
      lPStmt.setInt(2,vData.getInt("iCveCaracteristica"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex2){
      warn("delete",ex2);
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
        throw new DAOException(cMensaje);
      return lSuccess;
    }
  }
  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update VEHTipoVehiculo set cDscTipoVeh=?, cDscBreve=?, lActivo=?, lMotorUnico=?, lRequiereOrdOpera=? where iCveTipoVeh = ?  </b></p>
   * <p><b> Campos Llave: iCveTipoVeh, </b></p>
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
      String lSQL = "update TRACaracXRequisito set cDscCaracteristica=?, cDscBreve=?, lVigente=?, lEnRecepcion=?, lEnProceso=?, lMandatorio=? where iCveRequisito = ? and iCveCaracteristica = ?  ";
      vData.addPK(vData.getString("iCveRequisito"));
      vData.addPK(vData.getString("iCveCaracteristica"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(7,vData.getInt("iCveRequisito"));
      lPStmt.setInt(8,vData.getInt("iCveCaracteristica"));
      lPStmt.setString(1,vData.getString("cDscCaracteristica"));
      lPStmt.setString(2,vData.getString("cDscBreve"));
      lPStmt.setInt(3,vData.getInt("lVigente"));
      lPStmt.setInt(4,vData.getInt("lEnRecepcion"));
      lPStmt.setInt(5,vData.getInt("lEnProceso"));
      lPStmt.setInt(6,vData.getInt("lMandatorio"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
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
        throw new DAOException(cMensaje);
      return vData;
    }
 }
}



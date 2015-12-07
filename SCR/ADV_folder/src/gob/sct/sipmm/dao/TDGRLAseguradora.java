package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDNAVPermisos.java</p>
 * <p>Description: DAO de la entidad NAVPermisos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author GPerez
 * @version 1.0
 */
public class TDGRLAseguradora extends DAOBase{
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
      vcRecords = super.FindByGeneric(cKey,cSQL,dataSourceName);
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
   * <p><b> insert into NAVPermisos(iEjercicioPermiso,iConsecutivoPermiso,iEjercicioSolicitud,iNumSolicitud,iCveVehiculo,cPrestaServAEmp,dtIniServicio,dtFinServicio,dtIniContFlet,dtFinContFlet,dtIniContServ,dtFinContServ,dtIniSegProt,dtFinSegProt,iNumViajes,cMotivoContrato,dtAutorizacion,dtFinVigencia,iNumPasajeros,iNumTripulantesMex,cObs,lServicioaPemex,cNumContrato,iHolograma,iGradosLatitud,iMinutosLatitud,dSegundosLatitud,iGradosLongitud,iMinutosLongitud,dSegundosLongitud,dtExpedicion) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioPermiso,iConsecutivoPermiso, </b></p>
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
          "insert into GRLASEGURADORA (iCveAseguradora,iCvePersona,iCveDomicilio,cObses,lActivo) values (?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveAseguradora) AS iCveAseguradora from GRLASEGURADORA");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveAseguradora",vUltimo.getInt("iCveAseguradora") + 1);
      }else
         vData.put("iCveAseguradora",1);
      vData.addPK(vData.getString("iCveAseguradora"));
      //...
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt    (1,vData.getInt("iCveAseguradora"));
      lPStmt.setInt    (2,vData.getInt("iCvePersona"));
      lPStmt.setInt    (3,vData.getInt("iCveDomicilio"));
      lPStmt.setString (4,vData.getString("cObses"));
      lPStmt.setInt    (5,vData.getInt("lActivo"));
      /*//System.out.print("*****   iCveAseguradora "+vData.getString("iCveAseguradora"));
      //System.out.print("*****   iCvePersona     "+vData.getString("iCvePersona"));
      //System.out.print("*****   iCveDomicilio   "+vData.getString("iCveDomicilio"));
      //System.out.print("*****   cObses          "+vData.getString("cObses"));
      //System.out.print("*****   lActivo         "+vData.getString("lActivo"));*/
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      ex.printStackTrace();
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
          e.printStackTrace();
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
   * <p><b> delete from NAVPermisos where iEjercicioPermiso = ? AND iConsecutivoPermiso = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioPermiso,iConsecutivoPermiso, </b></p>
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
      String lSQL = "delete from GRLASEGURADORA where iCveAseguradora = ?";
      //...
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveAseguradora"));
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
   * <p><b> update NAVPermisos set iEjercicioSolicitud=?, iNumSolicitud=?, iCveVehiculo=?, cPrestaServAEmp=?, dtIniServicio=?, dtFinServicio=?, dtIniContFlet=?, dtFinContFlet=?, dtIniContServ=?, dtFinContServ=?, dtIniSegProt=?, dtFinSegProt=?, iNumViajes=?, cMotivoContrato=?, dtAutorizacion=?, dtFinVigencia=?, iNumPasajeros=?, iNumTripulantesMex=?, cObs=?, lServicioaPemex=?, cNumContrato=?, iHolograma=?, iGradosLatitud=?, iMinutosLatitud=?, dSegundosLatitud=?, iGradosLongitud=?, iMinutosLongitud=?, dSegundosLongitud=? where iEjercicioPermiso = ? AND iConsecutivoPermiso = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioPermiso,iConsecutivoPermiso, </b></p>
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
      String lSQL = "update GRLASEGURADORA set iCvePersona=?,iCveDomicilio=?,cObses=?,lActivo=? "+
                    "where iCveAseguradora = ? ";

      vData.addPK(vData.getString("iEjercicioPermiso"));
      vData.addPK(vData.getString("iConsecutivoPermiso"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePersona"));
      lPStmt.setInt(2,vData.getInt("iCveDomicilio"));
      lPStmt.setString(3,vData.getString("cObses"));
      //System.out.print("*****    cObses "+vData.getString("cObses"));
      lPStmt.setInt(4,vData.getInt("lActivo"));
      lPStmt.setInt(5,vData.getInt("iCveAseguradora"));
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

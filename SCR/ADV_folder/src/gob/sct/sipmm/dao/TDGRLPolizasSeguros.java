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
public class TDGRLPolizasSeguros extends DAOBase{
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
    TDGRLObservaLarga dGRLObservaLarga = new TDGRLObservaLarga();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      TVDinRep vObs = new TVDinRep();
      vObs.put("cDscObsLarga",vData.getString("cObses"));
      TVDinRep vObses = dGRLObservaLarga.insert(vObs,conn);

      String lSQL =
          "insert into GRLPOLIZASSEGUROS (ICVEPOLIZA,ICVEASEGURADORA,CNUMPOLIZA,DTINICIOVIGENCIA,DTFINVIGENCIA,CNOMASEGURADO,ICVEVEHICULO,IOBJETOPOLIZA,IEJERCICIOOBSLARGA,ICVEOBSLARGA) values (?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCvePoliza) AS iCvePoliza from GRLPOLIZASSEGUROS");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCvePoliza",vUltimo.getInt("iCvePoliza") + 1);
      }else
         vData.put("iCvePoliza",1);
      vData.addPK(vData.getString("iCvePoliza"));
      //...
      lPStmt = conn.prepareStatement(lSQL);//iCvePoliza,iCveAseguradora,cNumPoliza,dtInicioVigencia,dtFinVigencia,cNomAsegurado,iCveTipoVehiculo,iObjetoPoliza,IEjercicioObsLarga,iCveObsLarga
      lPStmt.setInt    (1,vData.getInt("iCvePoliza"));
      lPStmt.setInt    (2,vData.getInt("iCveAseguradora"));
      lPStmt.setString (3,vData.getString("cNumPoliza"));
      lPStmt.setDate   (4,vData.getDate("dtInicioVigencia"));
      lPStmt.setDate   (5,vData.getDate("dtFinVigencia"));
      lPStmt.setString (6,vData.getString("cNomAsegurado"));
      //System.out.print("*****    Asegurado =>> " + vData.getString("cNomAsegurado"));
      lPStmt.setInt    (7,vData.getInt("iCveTipoVehiculo"));
      lPStmt.setInt    (8,vData.getInt("iObjetoPoliza"));
      lPStmt.setInt    (9,vObses.getInt("IEjercicioObsLarga"));
      lPStmt.setInt    (10,vObses.getInt("iCveObsLarga"));


     /* //System.out.print("*****     Poliza:       "+vData.getInt("iCvePoliza"));
      //System.out.print("*****     Aseguradora:  "+vData.getInt("iCveAseguradora"));
      //System.out.print("*****     NumPoliza:    "+vData.getString("cNumPoliza"));
      //System.out.print("*****     dtInicio:     "+vData.getDate("dtInicioVigencia"));
      //System.out.print("*****     dtFin:        "+vData.getDate("dtFinVigencia"));
      //System.out.print("*****     Asegurado:    "+vData.getString("cNomAsegurado"));
      //System.out.print("*****     TipoVehiculo: "+vData.getInt("iCveTipoVehiculo"));
      //System.out.print("*****     ObjetoPoli:   "+vData.getInt("iObjetoPoliza"));
      //System.out.print("*****     Ejercicio:    "+vObses.getInt("IEjercicioObsLarga"));
      //System.out.print("*****     Observacion:  "+vObses.getInt("iCveObsLarga"));*/
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
      String lSQL = "delete from GRLPOLIZASSEGUROS where iCvePoliza = ?";
      //...
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePoliza"));
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
      String lSQL = "update GRLPOLIZASSEGUROS set ICVEASEGURADORA=?,CNUMPOLIZA=?,DTINICIOVIGENCIA=?,DTFINVIGENCIA=?,CNOMASEGURADO=?,ICVEVEHICULO=?,IOBJETOPOLIZA=?,IEJERCICIOOBSLARGA=?,ICVEOBSLARGA=? "+
                    "where iCvePoliza = ? ";

      vData.addPK(vData.getString("iEjercicioPermiso"));
      vData.addPK(vData.getString("iConsecutivoPermiso"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt    (1,vData.getInt("iCveAseguradora"));
      lPStmt.setString (2,vData.getString("cNumPoliza"));
      lPStmt.setDate   (3,vData.getDate("dtInicioVigencia"));
      lPStmt.setDate   (4,vData.getDate("dtFinVigencia"));
      lPStmt.setString (5,vData.getString("cNomAsegurado"));
      lPStmt.setInt    (6,vData.getInt("iCveTipoVehiculo"));
      lPStmt.setInt    (7,vData.getInt("iObjetoPoliza"));
      lPStmt.setInt    (8,vData.getInt("IEjercicioObsLarga"));
      lPStmt.setInt    (9,vData.getInt("iCveObsLarga"));
      lPStmt.setInt    (10,vData.getInt("iCvePoliza"));

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

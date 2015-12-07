package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCYSFianza.java</p>
 * <p>Description: DAO de la entidad CYSFianza</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSFianza extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private static final String cFIANZA_VENCIDA = "FIANZA VENCIDA";
  private static final String cFIANZA_NO_PRESENTADA = "FIANZA NO PRESENTADA";
  private static final String cBENEFICIARIO_INCORRECTO = "BENEFICIARIO INCORRECTO";
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
   * <p><b> insert into CYSFianza(iEjercicio,iCveFianza,dtRegistro,dtPresentacion,dtEmision,iCveContratante,cNumFianza,iCveAfianzadora,dImporteFianza,dtIniVigencia,dtFinVigencia,iCveBeneficiario) values (?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveFianza, </b></p>
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
          "insert into CYSFianza(iEjercicio,iCveFianza,dtRegistro,dtPresentacion,dtEmision,iCveContratante,cNumFianza," +
          "iCveAfianzadora,dImporteFianza,dtIniVigencia,dtFinVigencia,iCveBeneficiario,cNumEscrito,dtEscrito,iCveTitulo) " +
          "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveFianza) AS iCveFianza from CYSFianza");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveFianza",vUltimo.getInt("iCveFianza") + 1);
      }
      else
      vData.put("iCveFianza",1);

       vData.addPK(vData.getString("iEjercicio"));
      //...
       vData.addPK(vData.getString("iCveFianza"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveFianza"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setDate(5,vData.getDate("dtEmision"));
      lPStmt.setInt(6,vData.getInt("iCveContratante"));
      lPStmt.setString(7,vData.getString("cNumFianza"));
      lPStmt.setInt(8,vData.getInt("iCveAfianzadora"));
      lPStmt.setDouble(9,vData.getDouble("dImporteFianza"));
      lPStmt.setDate(10,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(11,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(12,vData.getInt("iCveBeneficiario"));
      lPStmt.setString(13,vData.getString("cNumEscrito"));
      lPStmt.setDate(14,vData.getDate("dtEscrito"));
      lPStmt.setInt(15,vData.getInt("iCveTitulo"));
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
   * <p><b> delete from CYSFianza where iEjercicio = ? AND iCveFianza = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveFianza, </b></p>
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
      String lSQL = "delete from CYSFianza where iEjercicio = ? AND iCveFianza = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveFianza"));

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
   * <p><b> update CYSFianza set dtRegistro=?, dtPresentacion=?, dtEmision=?, iCveContratante=?, cNumFianza=?, iCveAfianzadora=?, dImporteFianza=?, dtIniVigencia=?, dtFinVigencia=?, iCveBeneficiario=? where iEjercicio = ? AND iCveFianza = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveFianza, </b></p>
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
      String lSQL = "update CYSFianza set dtRegistro=?, dtPresentacion=?, dtEmision=?, iCveContratante=?, cNumFianza=?, iCveAfianzadora=?, " +
                    "dImporteFianza=?, dtIniVigencia=?, dtFinVigencia=?, iCveBeneficiario=?, cNumEscrito=?, dtEscrito=?, iCveTitulo=? " +
                    "where iEjercicio = ? AND iCveFianza = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveFianza"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setDate(3,vData.getDate("dtEmision"));
      lPStmt.setInt(4,vData.getInt("iCveContratante"));
      lPStmt.setString(5,vData.getString("cNumFianza"));
      lPStmt.setInt(6,vData.getInt("iCveAfianzadora"));
      lPStmt.setDouble(7,vData.getDouble("dImporteFianza"));
      lPStmt.setDate(8,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(9,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(10,vData.getInt("iCveBeneficiario"));
      lPStmt.setString(11,vData.getString("cNumEscrito"));
      lPStmt.setDate(12,vData.getDate("dtEscrito"));
      lPStmt.setInt(13,vData.getInt("iCveTitulo"));
      lPStmt.setInt(14,vData.getInt("iEjercicio"));
      lPStmt.setInt(15,vData.getInt("iCveFianza"));
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

 /**
  * Agregado por Rafael Alcocer Caldera 29/Nov/2006
  *
  * @param cKey String
  * @param iTitulo int
  * @param dtFecha Date
  * @throws DAOException
  * @return Vector
  */
 public Vector findBySitObligacion(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
   Vector vcSitObligacion = new Vector();
   TFechas tFechas = new TFechas();
   int iEjercicio = tFechas.getIntYear(dtFecha);

   /*
     ***************************************************************
     FALTA INTEGRAR...ACTUALIZAR FIANZA CON AVALUO EN BASE AL AVALUO
     ***************************************************************
    */
     String cSQL = "select iEjercicio, " +
                   "       iCveFianza, " +
                   "       dtRegistro, " +
                   "       dtPresentacion, " +
                   "       dtEmision, " +
                   "       iCveContratante, " +
                   "       cNumFianza, " +
                   "       iCveAfianzadora, " +
                   "       dImporteFianza, " +
                   "       dtIniVigencia, " +
                   "       dtFinVigencia, " +
                   "       iCveBeneficiario, " +
                   "       cNumEscrito, " +
                   "       dtEscrito, " +
                   "       iCveTitulo " +
                   " from CYSFianza " +
                   " where iCveTitulo = " + iTitulo +
                   " and iEjercicio = " + iEjercicio +
                   " and iCveFianza = (select max(iCveFianza) from CYSFianza where iCveTitulo = " + iTitulo + ")";

    // findByCustom regresa un Vector no null aunque puede estar vacio
    // del query solo voy a obtener un registro
    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();

    int iCYSBeneficiario = (VParametros.getPropEspecifica("CYSBeneficiario").compareToIgnoreCase("")!=0)?
                           Integer.parseInt(VParametros.getPropEspecifica("CYSBeneficiario")):0;

    if (iCYSBeneficiario>0)
    if (vcRecords.size() > 0) {
      // Solo hay un registro obtenido
      vDinRep = (TVDinRep)vcRecords.get(0);

      if (vDinRep.getDate("dtPresentacion") != null) {
        if ((vDinRep.getDate("dtIniVigencia").getTime() <= dtFecha.getTime()) &&
            (dtFecha.getTime() <= vDinRep.getDate("dtFinVigencia").getTime())) {
          if (vDinRep.getInt("iCveBeneficiario") == iCYSBeneficiario) {
            vcSitObligacion.add(new Boolean(true));
            vcSitObligacion.add(""); // Motivo
          } else {
            vcSitObligacion.add(new Boolean(false));
            vcSitObligacion.add(cBENEFICIARIO_INCORRECTO); // Motivo
          }
        } else {
          vcSitObligacion.add(new Boolean(false));
          vcSitObligacion.add(cFIANZA_VENCIDA); // Motivo
        }
      } else {
        vcSitObligacion.add(new Boolean(false));
        vcSitObligacion.add(cFIANZA_NO_PRESENTADA); // Motivo
      }
    } else {
       vcSitObligacion.add(new Boolean(false));
       vcSitObligacion.add(cFIANZA_NO_PRESENTADA); // Motivo
    }
    else{
      vcSitObligacion.add(new Boolean(false));
      vcSitObligacion.add(cFIANZA_NO_PRESENTADA); // Motivo
    }
    return vcSitObligacion;
 }
}

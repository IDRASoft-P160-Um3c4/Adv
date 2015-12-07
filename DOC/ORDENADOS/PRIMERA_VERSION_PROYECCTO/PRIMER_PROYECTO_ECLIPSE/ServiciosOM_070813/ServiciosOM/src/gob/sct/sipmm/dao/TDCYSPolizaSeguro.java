package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCYSPolizaSeguro.java</p>
 * <p>Description: DAO de la entidad CYSPolizaSeguro</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSPolizaSeguro extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private static final String cPOLIZA_VENCIDA = "PÓLIZA VENCIDA";
  private static final String cPOLIZA_NO_PRESENTADA = "PÓLIZA NO PRESENTADA";
  private static final String cEQUIPO_NO_EXISTENTE = "EQUIPO NO EXISTENTE";
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
   * <p><b> insert into CYSPolizaSeguro(iEjercicio,iCvePoliza,dtRegistro,dtPresentacion,iTipoPoliza,dtEmision,iCveContratante,cNumPoliza,iCveAseguradora,dPrima,dtIniVigencia,dtFinVigencia,iCveBeneficiario) values (?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iCvePoliza, </b></p>
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
          "insert into CYSPolizaSeguro(iEjercicio,iCvePoliza,dtRegistro,dtPresentacion,iTipoPoliza,dtEmision,iCveContratante,cNumPoliza," +
          "iCveAseguradora,dPrima,dtIniVigencia,dtFinVigencia,iCveBeneficiario,cNumEscrito,dtEscrito,iCveTitulo) " +
          "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCvePoliza) AS iCvePoliza from CYSPolizaSeguro");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCvePoliza",vUltimo.getInt("iCvePoliza") + 1);
      }else
         vData.put("iCvePoliza",1);

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCvePoliza"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCvePoliza"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setInt(5,vData.getInt("iTipoPoliza"));
      lPStmt.setDate(6,vData.getDate("dtEmision"));
      lPStmt.setInt(7,vData.getInt("iCveContratante"));
      lPStmt.setString(8,vData.getString("cNumPoliza"));
      lPStmt.setInt(9,vData.getInt("iCveAseguradora"));
      lPStmt.setDouble(10,vData.getDouble("dPrima"));
      lPStmt.setDate(11,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(12,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(13,vData.getInt("iCveBeneficiario"));
      lPStmt.setString(14,vData.getString("cNumEscrito"));
      lPStmt.setDate(15,vData.getDate("dtEscrito"));
      lPStmt.setInt(16,vData.getInt("iCveTitulo"));
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
   * <p><b> delete from CYSPolizaSeguro where iEjercicio = ? AND iCvePoliza = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCvePoliza, </b></p>
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
      String lSQL = "delete from CYSPolizaSeguro where iEjercicio = ? AND iCvePoliza = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCvePoliza"));

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
   * <p><b> update CYSPolizaSeguro set dtRegistro=?, dtPresentacion=?, iTipoPoliza=?, dtEmision=?, iCveContratante=?, cNumPoliza=?, iCveAseguradora=?, dPrima=?, dtIniVigencia=?, dtFinVigencia=?, iCveBeneficiario=? where iEjercicio = ? AND iCvePoliza = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCvePoliza, </b></p>
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
      String lSQL = "update CYSPolizaSeguro set dtRegistro=?, dtPresentacion=?, iTipoPoliza=?, dtEmision=?, iCveContratante=?, cNumPoliza=?, " +
                    "iCveAseguradora=?, dPrima=?, dtIniVigencia=?, dtFinVigencia=?, iCveBeneficiario=?, cNumEscrito=?, dtEscrito=?, " +
                    "iCveTitulo=? where iEjercicio = ? AND iCvePoliza = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCvePoliza"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setInt(3,vData.getInt("iTipoPoliza"));
      lPStmt.setDate(4,vData.getDate("dtEmision"));
      lPStmt.setInt(5,vData.getInt("iCveContratante"));
      lPStmt.setString(6,vData.getString("cNumPoliza"));
      lPStmt.setInt(7,vData.getInt("iCveAseguradora"));
      lPStmt.setFloat(8,vData.getFloat("dPrima"));
      lPStmt.setDate(9,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(10,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(11,vData.getInt("iCveBeneficiario"));
      lPStmt.setString(12,vData.getString("cNumEscrito"));
      lPStmt.setDate(13,vData.getDate("dtEscrito"));
      lPStmt.setInt(14,vData.getInt("iCveTitulo"));
      lPStmt.setInt(15,vData.getInt("iEjercicio"));
      lPStmt.setInt(16,vData.getInt("iCvePoliza"));
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
  * Agregado por Rafael Alcocer Caldera
  *
  * El Vector obtenido de este metodo nos regresa:
  * tipo de poliza, true o false, motivo
  *
  * Ejemplo:
  * 1, true, "", 1, false, "POLIZA VENCIDA", 3, false, "BENEFICIARIO INCORRECTO"
  *
  * En el ejemplo se observa que hay 3 resultados por cada poliza:
  * 1) 1, true, ""
  * 2) false, "POLIZA VENCIDA"
  * 3) false, "BENEFICIARIO INCORRECTO"
  *
  * @param cKey String
  * @param iTitulo int
  * @param dtFecha Date
  * @param tiposPoliza List
  * @throws DAOException
  * @return Vector
  */
 public Vector findBySitObligacion(String cKey, int iTitulo, java.sql.Date dtFecha, List tiposPoliza) throws DAOException{
   Vector vcSitObligacion = new Vector();
   TFechas tFechas = new TFechas();
   int iEjercicio = tFechas.getIntYear(dtFecha);

     String cSQL1 = "select iEjercicio, " +
                    "       iCvePoliza, " +
                    "       dtRegistro, " +
                    "       dtPresentacion, " +
                    "       iTipoPoliza, " +
                    "       dtEmision, " +
                    "       iCveContratante, " +
                    "       cNumPoliza, " +
                    "       iCveAseguradora, " +
                    "       dPrima, " +
                    "       dtIniVigencia, " +
                    "       dtFinVigencia, " +
                    "       iCveBeneficiario, " +
                    "       cNumEscrito, " +
                    "       dtEscrito, " +
                    "       iCveTitulo " +
                    " from CYSPolizaSeguro " +
                    " where iCveTitulo = " + iTitulo +
                    " and iEjercicio = " + iEjercicio +
                    " and iTipoPoliza = 1 ";

     String cSQL2 = "select iEjercicio, " +
                    "       iCvePoliza, " +
                    "       dtRegistro, " +
                    "       dtPresentacion, " +
                    "       iTipoPoliza, " +
                    "       dtEmision, " +
                    "       iCveContratante, " +
                    "       cNumPoliza, " +
                    "       iCveAseguradora, " +
                    "       dPrima, " +
                    "       dtIniVigencia, " +
                    "       dtFinVigencia, " +
                    "       iCveBeneficiario, " +
                    "       cNumEscrito, " +
                    "       dtEscrito, " +
                    "       iCveTitulo " +
                    " from CYSPolizaSeguro " +
                    " where iCveTitulo = " + iTitulo +
                    " and iEjercicio = " + iEjercicio +
                    " and iTipoPoliza = 2 ";

     String cSQL3 = "select cysps.iEjercicio, " +
                    "       cysps.iCvePoliza, " +
                    "       cysps.dtRegistro, " +
                    "       cysps.dtPresentacion, " +
                    "       cysps.iTipoPoliza, " +
                    "       cysps.dtEmision, " +
                    "       cysps.iCveContratante, " +
                    "       cysps.cNumPoliza, " +
                    "       cysps.iCveAseguradora, " +
                    "       cysps.dPrima, " +
                    "       cysps.dtIniVigencia, " +
                    "       cysps.dtFinVigencia, " +
                    "       cysps.iCveBeneficiario, " +
                    "       cysps.cNumEscrito, " +
                    "       cysps.dtEscrito, " +
                    "       cysps.iCveTitulo, " +
                    "       cyspse.iCveEquipo " +
                    " from CYSPolizaSeguro cysps " +
                    " inner join CYSPolSegEquipo cyspse on cysps.iCvePoliza = cyspse.iCvePoliza " +
                    " where iCveTitulo = " + iTitulo +
                    " and cysps.iEjercicio = " + iEjercicio +
                    " and iTipoPoliza = 3 ";

    // findByCustom regresa un Vector no null aunque puede estar vacio
    // del query solo voy a obtener un registro
    Vector vcRecords1 = findByCustom(cKey, cSQL1);
    TVDinRep vDinRep1 = new TVDinRep();
    Vector vcRecords2 = findByCustom(cKey, cSQL2);
    TVDinRep vDinRep2 = new TVDinRep();
    Vector vcRecords3 = findByCustom(cKey, cSQL3);
    TVDinRep vDinRep3 = new TVDinRep();

    int iCYSBeneficiario = (VParametros.getPropEspecifica("CYSBeneficiario").compareToIgnoreCase("")!=0)?
                           Integer.parseInt(VParametros.getPropEspecifica("CYSBeneficiario")):0;

    if (iCYSBeneficiario>0)
    for (int i = 0; i < tiposPoliza.size(); i++) {
      int iTipoPoliza = ((Integer)tiposPoliza.get(i)).intValue();

      switch (iTipoPoliza) {
          // Obras e Instalacion
          case 1:
            for (int j = 0; j < vcRecords1.size(); j++) {
              vDinRep1 = (TVDinRep)vcRecords1.get(j);

              if (vDinRep1.getDate("dtPresentacion") != null) {
                if ((vDinRep1.getDate("dtIniVigencia").getTime() <= dtFecha.getTime()) &&
                    (dtFecha.getTime() <= vDinRep1.getDate("dtFinVigencia").getTime())) {
                  if (vDinRep1.getInt("iCveBeneficiario") == iCYSBeneficiario) {
                    vcSitObligacion.add(new Integer(1));
                    vcSitObligacion.add(new Boolean(true));
                    vcSitObligacion.add("");
                  } else {
                    vcSitObligacion.add(new Integer(1));
                    vcSitObligacion.add(new Boolean(false));
                    vcSitObligacion.add(cBENEFICIARIO_INCORRECTO);
                  }
                } else {
                  vcSitObligacion.add(new Integer(1));
                  vcSitObligacion.add(new Boolean(false));
                  vcSitObligacion.add(cPOLIZA_VENCIDA);
                }
              } else if (vDinRep1.getDate("dtPresentacion") == null) {
                  vcSitObligacion.add(new Integer(1));
                  vcSitObligacion.add(new Boolean(false));
                  vcSitObligacion.add(cPOLIZA_NO_PRESENTADA);
              }
            }
            if (vcSitObligacion.isEmpty()){
              vcSitObligacion.add(new Integer(1));
              vcSitObligacion.add(new Boolean(false));
              vcSitObligacion.add(cPOLIZA_NO_PRESENTADA);
            }
            continue;
          // Responsabilidad Civil
          case 2:
            for (int j = 0; j < vcRecords2.size(); j++) {
              vDinRep2 = (TVDinRep)vcRecords2.get(j);

              if (vDinRep2.getDate("dtPresentacion") != null) {
                if ((vDinRep2.getDate("dtIniVigencia").getTime() <= dtFecha.getTime()) &&
                    (dtFecha.getTime() <= vDinRep2.getDate("dtFinVigencia").getTime())) {
                  if (vDinRep2.getInt("iCveBeneficiario") == iCYSBeneficiario) {
                    vcSitObligacion.add(new Integer(2));
                    vcSitObligacion.add(new Boolean(true));
                    vcSitObligacion.add("");
                  } else {
                    vcSitObligacion.add(new Integer(2));
                    vcSitObligacion.add(new Boolean(false));
                    vcSitObligacion.add(cBENEFICIARIO_INCORRECTO);
                  }
                } else {
                  vcSitObligacion.add(new Integer(2));
                  vcSitObligacion.add(new Boolean(false));
                  vcSitObligacion.add(cPOLIZA_VENCIDA);
                }
              } else if (vDinRep2.getDate("dtPresentacion") == null) {
                  vcSitObligacion.add(new Integer(2));
                  vcSitObligacion.add(new Boolean(false));
                  vcSitObligacion.add(cPOLIZA_NO_PRESENTADA);
              }
            }
            if (vcSitObligacion.isEmpty()){
              vcSitObligacion.add(new Integer(2));
              vcSitObligacion.add(new Boolean(false));
              vcSitObligacion.add(cPOLIZA_NO_PRESENTADA);
            }
            continue;
          // Seguro de los Equipos
          case 3:
            for (int j = 0; j < vcRecords3.size(); j++) {
              vDinRep3 = (TVDinRep)vcRecords3.get(j);

              if (vDinRep3.getDate("dtPresentacion") != null) {
                if((vDinRep3.getDate("dtIniVigencia").getTime() <= dtFecha.getTime()) &&
                   (dtFecha.getTime() <= vDinRep3.getDate("dtFinVigencia").getTime())){
                  if (new Integer(vDinRep3.getInt("iCveEquipo")) != null) {
                    if (vDinRep3.getInt("iCveBeneficiario") == iCYSBeneficiario) {
                      vcSitObligacion.add(new Integer(3));
                      vcSitObligacion.add(new Boolean(true));
                      vcSitObligacion.add("");
                    } else {
                      vcSitObligacion.add(new Integer(3));
                      vcSitObligacion.add(new Boolean(false));
                      vcSitObligacion.add(cBENEFICIARIO_INCORRECTO);
                    }
                  } else {
                    vcSitObligacion.add(new Integer(3));
                    vcSitObligacion.add(new Boolean(false));
                    vcSitObligacion.add(cEQUIPO_NO_EXISTENTE);
                  }
                } else {
                  vcSitObligacion.add(new Integer(3));
                  vcSitObligacion.add(new Boolean(false));
                  vcSitObligacion.add(cPOLIZA_VENCIDA);
                }
              } else if (vDinRep3.getDate("dtPresentacion") == null) {
                  vcSitObligacion.add(new Integer(3));
                  vcSitObligacion.add(new Boolean(false));
                  vcSitObligacion.add(cPOLIZA_NO_PRESENTADA);
              }
            }
            if (vcSitObligacion.isEmpty()){
              vcSitObligacion.add(new Integer(3));
              vcSitObligacion.add(new Boolean(false));
              vcSitObligacion.add(cPOLIZA_NO_PRESENTADA);
            }
            continue;
      }
    }
    else {
      try{
        for(int m = 0;m < tiposPoliza.size();m++){
          int iTipPol = ( (Integer) tiposPoliza.get(m)).intValue();
          vcSitObligacion.add(new Integer(iTipPol));
          vcSitObligacion.add(new Boolean(false));
          vcSitObligacion.add(cPOLIZA_NO_PRESENTADA);
        }
      } catch(Exception ex){
        //ex.printStackTrace();
      }
    }

    return vcSitObligacion;
 }
}

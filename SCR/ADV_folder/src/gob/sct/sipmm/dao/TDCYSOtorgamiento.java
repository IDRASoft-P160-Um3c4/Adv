package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCYSOtorgamiento.java</p>
 * <p>Description: DAO de la entidad CYSOtorgamiento</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDCYSOtorgamiento extends DAOBase{
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
   * <p><b> insert into CYSOtorgamiento(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
    int iConsecutivo = this.findMax(vData.getInt("iCveTitulo"),vData.getInt("iEjercicio"));
    vData.put("iMovOtorgamiento",iConsecutivo);
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSOtorgamiento(iCveTitulo, " +
                    "iEjercicio, " +
                    "iMovOtorgamiento, " +
                    "dtRegistro, " +
                    "cNumEscrito, " +
                    "dtEscrito, " +
                    "dtPresentacion, " +
                    "dtPago, " +
                    "iEjercicioIniPago, " +
                    "iPeriodoIniPago, " +
                    "iEjercicioFinPago, " +
                    "iPeriodoFinPago, " +
                    "dImpDerechos, " +
                    "dImpActualizacion, " +
                    "dImpRecargos, " +
                    "dImpTotalPagado, " +
                    "cObsFormato, " +
                    "iCveUsuario, " +
                    "cNumFactura, " +
                    "cCveSATDerechos, " +
                    "cCveDGPOPDerechos, " +
                    "cCveDGPOPActualizacion, " +
                    "cCveDGPOPRecargos, " +
                    "cMovDGPOPDerechos, " +
                    "cMovDGPOPActualizacion, " +
                    "cMovDGPOPRecargos, " +
                    "lCopiaLegible, " +
                    "iEjercicioAcredita ) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovOtorgamiento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovOtorgamiento"));
      lPStmt.setDate(4,vData.getDate("dtRegistro"));
      lPStmt.setString(5,vData.getString("cNumEscrito"));
      lPStmt.setDate(6,vData.getDate("dtEscrito"));
      lPStmt.setDate(7,vData.getDate("dtPresentacion"));
      lPStmt.setDate(8,vData.getDate("dtPago"));
      lPStmt.setInt(9,vData.getInt("iEjercicioIniPago"));
      lPStmt.setInt(10,vData.getInt("iPeriodoIniPago"));
      lPStmt.setInt(11,vData.getInt("iEjercicioFinPago"));
      lPStmt.setInt(12,vData.getInt("iPeriodoFinPago"));
      lPStmt.setDouble(13,vData.getDouble("dImpDerechos"));
      lPStmt.setDouble(14,vData.getDouble("dImpActualizacion"));
      lPStmt.setDouble(15,vData.getDouble("dImpRecargos"));
      lPStmt.setDouble(16,vData.getDouble("dImpTotalPagado"));
      lPStmt.setString(17,vData.getString("cObsFormato"));
      lPStmt.setInt(18,vData.getInt("iCveUsuario"));
      lPStmt.setString(19,vData.getString("cNumFactura"));
      lPStmt.setString(20,vData.getString("cCveSATDerechos"));
      lPStmt.setString(21,vData.getString("cCveDGPOPDerechos"));
      lPStmt.setString(22,vData.getString("cCveDGPOPActualizacion"));
      lPStmt.setString(23,vData.getString("cCveDGPOPRecargos"));
      lPStmt.setString(24,vData.getString("cMovDGPOPDerechos"));
      lPStmt.setString(25,vData.getString("cMovDGPOPActualizacion"));
      lPStmt.setString(26,vData.getString("cMovDGPOPRecargos"));
      lPStmt.setInt(27,vData.getInt("lCopiaLegible"));
      lPStmt.setInt(28,vData.getInt("iEjercicioAcredita"));
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
   * <p><b> delete from CYSOtorgamiento where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "delete from CYSOtorgamiento where iCveTitulo = ? " +
                    "and iEjercicio=? " +
                    "and iMovOtorgamiento=? ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovOtorgamiento"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = "" + sqle.getErrorCode();
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
   * <p><b> update CYSOtorgamiento set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
      String lSQL = "update CYSOtorgamiento " +
                    "set dtRegistro=?, " +
                    "cNumEscrito=?, " +
                    "dtEscrito=?, " +
                    "dtPresentacion=?, " +
                    "dtPago=?, " +
                    "iEjercicioIniPago=?, " +
                    "iPeriodoIniPago=?, " +
                    "iEjercicioFinPago=?, " +
                    "iPeriodoFinPago=?, " +
                    "dImpDerechos=?, " +
                    "dImpActualizacion=?, " +
                    "dImpRecargos=?, " +
                    "dImpTotalPagado=?, " +
                    "cObsFormato=?, " +
                    "iCveUsuario=?, " +
                    "cNumFactura=?, " +
                    "cCveSATDerechos=?, " +
                    "cCveDGPOPDerechos=?, " +
                    "cCveDGPOPActualizacion=?, " +
                    "cCveDGPOPRecargos=?, " +
                    "cMovDGPOPDerechos=?, " +
                    "cMovDGPOPActualizacion=?, " +
                    "cMovDGPOPRecargos=?, " +
                    "lCopiaLegible=?, " +
                    "iEjercicioAcredita=? " +
                    "where iCveTitulo=? " +
                    "and iEjercicio=? " +
                    "and iMovOtorgamiento=? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovOtorgamiento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setString(2,vData.getString("cNumEscrito"));
      lPStmt.setDate(3,vData.getDate("dtEscrito"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setDate(5,vData.getDate("dtPago"));
      lPStmt.setInt(6,vData.getInt("iEjercicioIniPago"));
      lPStmt.setInt(7,vData.getInt("iPeriodoIniPago"));
      lPStmt.setInt(8,vData.getInt("iEjercicioFinPago"));
      lPStmt.setInt(9,vData.getInt("iPeriodoFinPago"));
      lPStmt.setDouble(10,vData.getDouble("dImpDerechos"));
      lPStmt.setDouble(11,vData.getDouble("dImpActualizacion"));
      lPStmt.setDouble(12,vData.getDouble("dImpRecargos"));
      lPStmt.setDouble(13,vData.getDouble("dImpTotalPagado"));
      lPStmt.setString(14,vData.getString("cObsFormato"));
      lPStmt.setInt(15,vData.getInt("iCveUsuario"));
      lPStmt.setString(16,vData.getString("cNumFactura"));
      lPStmt.setString(17,vData.getString("cCveSATDerechos"));
      lPStmt.setString(18,vData.getString("cCveDGPOPDerechos"));
      lPStmt.setString(19,vData.getString("cCveDGPOPActualizacion"));
      lPStmt.setString(20,vData.getString("cCveDGPOPRecargos"));
      lPStmt.setString(21,vData.getString("cMovDGPOPDerechos"));
      lPStmt.setString(22,vData.getString("cMovDGPOPActualizacion"));
      lPStmt.setString(23,vData.getString("cMovDGPOPRecargos"));
      lPStmt.setInt(24,vData.getInt("lCopiaLegible"));
      lPStmt.setInt(25,vData.getInt("iEjercicioAcredita"));
      lPStmt.setInt(26,vData.getInt("iCveTitulo"));
      lPStmt.setInt(27,vData.getInt("iEjericicio"));
      lPStmt.setInt(28,vData.getInt("iMovOtorgamiento"));
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

  public int findMax(int iCveTitulo,int iEjercicio) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    Statement lStmt = null;
    ResultSet rs = null;
    int iConsecutivo = 0;
    try{

      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      lStmt = conn.createStatement();
      String lSQL =
          " SELECT MAX(iMovOtorgamiento) AS iMovOtorgamiento " +
          " FROM CYSOtorgamiento " +
          " WHERE iCveTitulo = " + iCveTitulo +
          " and iEjercicio = " + iEjercicio;

      rs = lStmt.executeQuery(lSQL);
      while(rs.next()){
        iConsecutivo = rs.getInt("iMovOtorgamiento");
      }
      iConsecutivo++;
    } catch(Exception ex){
      ex.printStackTrace();
    } finally{
      try{
        if(lStmt != null){
          lStmt.close();
        }
        if(rs != null){
          rs.close();
        }
        if(conn != null){
          conn.close();
        }
        dbConn.closeConnection();
      } catch(Exception ex2){
      }
      return iConsecutivo;
    }
  }

  /**
   * Agregado por Rafael Alcocer Caldera 30/Nov/2006
   *
   * @param cKey String
   * @param iTitulo int
   * @param dtFecha Date
   * @throws DAOException
   * @return Vector
   */
  public Vector findBySitObligacion(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
    TFechas tFechas = new TFechas();
    java.sql.Date dtFechaLimite = tFechas.getDateSQL("31/12/2005");
    java.sql.Date dtFechaActual = tFechas.TodaySQL();
    java.sql.Date dtFecha16Feb = tFechas.getDateSQL("16/02/" + tFechas.getStringYear(dtFechaActual));

    boolean lTomarEnCuentaAnioActual = false;
    // iYearIni = Año siguiente a dtFecha
    int iYearIni = tFechas.getIntYear(dtFecha) + 1;
    // iYearFin = Año actual o un anterior si es menor a 16/Feb
    int iYearFin = tFechas.getIntYear(dtFechaActual);

    int iYearDif = iYearFin - iYearIni;

    Vector vcSitObligacion = new Vector();
    List listEjercicioNoPagadosTotales = new ArrayList();
    List listEjercicioPagados = new ArrayList();
    List listEjercicioNoPagados = new ArrayList();
    List listPeriodoNoPagado = new ArrayList();
    List listPeriodoPagado = new ArrayList();
    // El LinkedHashSet lo utilizo para eliminar
    // duplicados y que esten ordenados por insercion
    Set setPeriodoNoPagado = new LinkedHashSet();

    for (int i = 0; i < iYearDif; i++) {
        // Agregar a la lista todo el rango de años que esta y no esta en la BD
        listEjercicioNoPagadosTotales.add(new Integer(iYearIni + i));
    }

    /*
     * Regresa:
     * el valor 0 si son iguales
     * un valor menor a 0 si dtFecha es anterior a dtFechaLimite
     * un valor mayor a 0 si dtFecha es despues a dtFechaLimite
     *
     * dtFecha debe ser < al 31/Dic/2005
     */
    if (dtFecha.compareTo(dtFechaLimite) < 0) {
        /*
         * Verificar si dtFechaActual es >= a 16/Feb/AñoActual
         * en caso afirmativo SI se toma en cuenta este año
         * en caso negativo NO se toma en cuenta este año
         */
        if (dtFechaActual.compareTo(dtFecha16Feb) >= 0) {
            lTomarEnCuentaAnioActual = true;
        } else {
            lTomarEnCuentaAnioActual = false;
            iYearFin = iYearFin - 1;
        }

        String cSQL = "select iCveTitulo, " +
                      "       iEjercicio, " +
                      "       iMovOtorgamiento, " +
                      "       dtRegistro, " +
                      "       cNumEscrito, " +
                      "       dtEscrito, " +
                      "       dtPresentacion, " +
                      "       dtPago, " +
                      "       iEjercicioIniPago, " +
                      "       iPeriodoIniPago, " +
                      "       iEjercicioFinPago, " +
                      "       iPeriodoFinPago, " +
                      "       dImpDerechos, " +
                      "       dImpActualizacion, " +
                      "       dImpRecargos, " +
                      "       dImpTotalPagado, " +
                      "       cObsFormato, " +
                      "       iCveUsuario, " +
                      "       cNumFactura, " +
                      "       cCveSatDerechos, " +
                      "       cCveDGPOPDerechos, " +
                      "       cCveDGPOPActualizacion, " +
                      "       cCveDGPOPRecargos, " +
                      "       cMovDGPOPDerechos, " +
                      "       cMovDGPOPActualizacion, " +
                      "       cMovDGPOPRecargos, " +
                      "       lCopiaLegible, " +
                      "       iEjercicioAcredita " +
                      " from CYSOtorgamiento " +
                      " where iCveTitulo = " + iTitulo +
                      " and iEjercicio between " + iYearIni + " and " + iYearFin;

        // findByCustom regresa un Vector no null aunque puede estar vacio
        // del query solo voy a obtener un registro
        Vector vcRecords = findByCustom(cKey, cSQL);
        TVDinRep vDinRep = new TVDinRep();

        if (vcRecords.size() > 0) {
            for (int i = 0; i < vcRecords.size(); i++) {
                vDinRep = (TVDinRep)vcRecords.get(i);

                // Si no existe valor automaticamente se asigna 0 a vDinRep.getInt()
                if (vDinRep.getInt("iEjercicioIniPago") == 0) {
                    listEjercicioNoPagados.add(new Integer(vDinRep.getInt("iEjercicio")));
                } else {
                    listEjercicioPagados.add(new Integer(vDinRep.getInt("iEjercicio")));
                    // Del rango total de años que existen entre la Fecha de Inicio y
                    // la de Fin quito los Ejercicios Pagados para obtener el total.
                    // Esto es, los que estan en la BD y lo que no
                    listEjercicioNoPagadosTotales.remove(new Integer(vDinRep.getInt("iEjercicio")));
                }

                // Verificar que al menos exista un pago
                if (vDinRep.getDouble("dImpDerechos") > 0 ||
                    vDinRep.getDouble("dImpActualizacion") > 0 ||
                    vDinRep.getDouble("dImpRecargos") > 0) {
                    // Verificar que exista el total
                    if (vDinRep.getDouble("dImpTotalPagado") > 0) {
                        // Periodo Inicial
                        int iIni = 1;
                        int iPeriodoIniPago = vDinRep.getInt("iPeriodoIniPago");

                        // Periodo Final
                        int iFin = 12;
                        // Le agrego 1 para no incluir el ya pagado
                        int iPeriodoFinPago = vDinRep.getInt("iPeriodoFinPago") + 1;

                        // PERIODOS DE INICIO
                        while (vDinRep.getInt("iPeriodoIniPago") > iIni) {
                            listPeriodoNoPagado.add("" + iIni + "," + vDinRep.getInt("iEjercicioIniPago"));
                            ++iIni;
                        }

                        while (iPeriodoIniPago <= iFin) {
                            listPeriodoPagado.add("" + iPeriodoIniPago + "," + vDinRep.getInt("iEjercicioIniPago"));
                            ++iPeriodoIniPago;
                        }

                        // PERIODOS DE FIN
                        while (iPeriodoFinPago <= iFin) {
                            listPeriodoNoPagado.add("" + iPeriodoFinPago + "," + vDinRep.getInt("iEjercicioFinPago"));
                            ++iPeriodoFinPago;
                        }

                        iIni = 1; // Vuelvo a inicializar iIni a 1

                        while (vDinRep.getInt("iPeriodoFinPago") >= iIni) {
                            listPeriodoPagado.add("" + iIni + "," + vDinRep.getInt("iEjercicioFinPago"));
                            ++iIni;
                        }
                    }

                }
            }
        }
    }

    for (int i = 0; i < listEjercicioNoPagadosTotales.size(); i++) {
      System.out.print("FINAL EJERCICIO NO PAGADO: " + listEjercicioNoPagadosTotales.get(i));
      TVDinRep bean = new TVDinRep();
      bean.put("iEjercicioNoPagado", ((Integer)listEjercicioNoPagadosTotales.get(i)).intValue());
      vcSitObligacion.add(bean);
    }

    /*
    for (int i = 0; i < listEjercicioPagados.size(); i++) {
        System.out.print("EJERCICIO PAGADO: " + listEjercicioPagados.get(i));
    }
    */

    /*
    for (int i = 0; i < listEjercicioNoPagados.size(); i++) {
        System.out.print("EJERCICIO NO PAGADO: " + listEjercicioNoPagados.get(i));
    }
     */

    // A continuacion comparo el primer Periodo Pagado de la
    // lista con todos los No Pagados y si es igual o quito
    // de la lista de los No Pagados y asi me sigo con los
    // siguientes Pagados.
    for (int i = 0; i < listPeriodoPagado.size(); i++) {
        for (int j = 0; j < listPeriodoNoPagado.size(); j++) {
          if (((String)listPeriodoPagado.get(i)).equals(((String)listPeriodoNoPagado.get(j)))) {
                System.out.print("PAGADO A COMPARAR..." + listPeriodoPagado.get(i));
                System.out.print("NO PAGADO A ELIMINAR..." + listPeriodoNoPagado.get(j));
                listPeriodoNoPagado.remove(j);
            }
        }
    }

    // Agrego los duplicados si hay al Set para eliminarlos
    for (int i = 0; i < listPeriodoNoPagado.size(); i++) {
        setPeriodoNoPagado.add(listPeriodoNoPagado.get(i));
    }

    Iterator it = setPeriodoNoPagado.iterator();

    for (int i = 0; it.hasNext(); i++) {
        System.out.print("PERIODO NO PAGADO: " + it.next());
        TVDinRep bean = new TVDinRep();
        bean.put("cPeriodoNoPagado", (String)it.next());
        vcSitObligacion.add(bean);

    }

    /*
     *
    System.out.print("SIZE...." + vcSitObligacion.size());

    for (int i = 0; i < vcSitObligacion.size(); i++) {
      TVDinRep x = new TVDinRep();
      x = (TVDinRep)vcSitObligacion.get(i);
      System.out.print("Ej No Pag..." + x.getInt("iEjercicioNoPagado"));
      System.out.print("Per No Pag..." + x.getString("cPeriodoNoPagado"));
    }
    * Con lo anterior recupero la informacion:

    * 09:13:41,156 INFO  [STDOUT] FINAL EJERCICIO NO PAGADO: 2002
    * 09:13:41,156 INFO  [STDOUT] FINAL EJERCICIO NO PAGADO: 2003
    * 09:13:41,156 INFO  [STDOUT] FINAL EJERCICIO NO PAGADO: 2005
    * 09:13:41,156 INFO  [STDOUT] SIZE....7
    * 09:13:41,156 INFO  [STDOUT] Ej No Pag...2002
    * 09:13:41,156 INFO  [STDOUT] Per No Pag...null
    * 09:13:41,156 INFO  [STDOUT] Ej No Pag...2003
    * 09:13:41,156 INFO  [STDOUT] Per No Pag...null
    * 09:13:41,156 INFO  [STDOUT] Ej No Pag...2005
    * 09:13:41,156 INFO  [STDOUT] Per No Pag...null
    * 09:13:41,156 INFO  [STDOUT] Ej No Pag...0
    * 09:13:41,156 INFO  [STDOUT] Per No Pag...4,2001
    * 09:13:41,156 INFO  [STDOUT] Ej No Pag...0
    * 09:13:41,156 INFO  [STDOUT] Per No Pag...10,2002
    * 09:13:41,156 INFO  [STDOUT] Ej No Pag...0
    * 09:13:41,156 INFO  [STDOUT] Per No Pag...11,2002
    * 09:13:41,156 INFO  [STDOUT] Ej No Pag...0
    * 09:13:41,156 INFO  [STDOUT] Per No Pag...12,2002
    */

     return vcSitObligacion;
  }
}


package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;

/**
 * <p>Title: TDRGSSenal.java</p>
 * <p>Description: DAO de la entidad RGSSenal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDRGSSenal
  extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas tFecha = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepción de tipo DAO
   * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
   */
  public Vector findByCustom(String cKey, String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey, cSQL, dataSourceName);
    } catch(Exception e){
      cError = e.getMessage();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into RGSSenal(iCveSenal,iCveRegion,iCvePais,iCveEntidadFed,iCveMunicipio,iPuntoCardinal,iNumNacional,iConsecNumNacional,cLetraInternacional,iNumInternacional,cNomSenal,iAnioConstruccion,cDscUbicacion,dAlturaEstructura,iCveTipoSenal,iCveForma,iCveMaterial,cDscMarcaDiurna,cDscColor,iGradosIniVisible,iGradosFinVisible,lLuminosa,lRadioelectrica,iCveSistEnergia,lMotogenerador,cDatosAdicionales,lCargoSCT,iCveOficinaResponsable,lCargoAPI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveSenal, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData, Connection cnNested) throws
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
        "insert into RGSSenal(iCveSenal,iCveRegion,iCvePais,iCveEntidadFed,iCveMunicipio,iPuntoCardinal,iNumNacional,iConsecNumNacional,cLetraInternacional,iNumInternacional,cNomSenal,iAnioConstruccion,cDscUbicacion,dAlturaEstructura,iCveTipoSenal,iCveForma,iCveMaterial,cDscMarcaDiurna,cDscColor,iGradosIniVisible,iGradosFinVisible,lLuminosa,lRadioelectrica,iCveSistEnergia,lMotogenerador,cDatosAdicionales,lCargoSCT,iCveOficinaResponsable,lCargoAPI,lEnConstruccion,cUbicacionPlano,iCvePuerto) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      Vector vcDataNum = findByCustom("", "select iCveSenal from RGSSenal "+
                                          " WHERE iCveEntidadFed = " + vData.getInt("iCveEntidadFed") +
                                          " AND iNumNacional = " + vData.getInt("iNumNacional") +
                                          " AND iConsecNumNacional = " + vData.getInt("iConsecNumNacional")
                                     );
      if(vcDataNum.size() > 0){
        throw new DAOException("Insertar - Ya existe una señal con el número nacional proporcionado");
      }

      Vector vcData = findByCustom("", "select MAX(iCveSenal) AS iCveSenal from RGSSenal");
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iCveSenal", vUltimo.getInt("iCveSenal") + 1);
      } else
        vData.put("iCveSenal", 1);
      vData.addPK(vData.getString("iCveSenal"));


      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, vData.getInt("iCveSenal"));
      lPStmt.setInt(2, vData.getInt("iCveRegion"));
      lPStmt.setInt(3, vData.getInt("iCvePais"));
      lPStmt.setInt(4, vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(5, vData.getInt("iCveMunicipio"));
      lPStmt.setInt(6, vData.getInt("iPuntoCardinal"));
      lPStmt.setInt(7, vData.getInt("iNumNacional"));
      lPStmt.setInt(8, vData.getInt("iConsecNumNacional"));
      lPStmt.setString(9, vData.getString("cLetraInternacional"));
      lPStmt.setInt(10, vData.getInt("iNumInternacional"));
      lPStmt.setString(11, vData.getString("cNomSenal"));
      lPStmt.setInt(12, vData.getInt("iAnioConstruccion"));
      lPStmt.setString(13, vData.getString("cDscUbicacion"));
      lPStmt.setDouble(14, vData.getDouble("dAlturaEstructura"));
      lPStmt.setInt(15, vData.getInt("iCveTipoSenal"));
      lPStmt.setInt(16, vData.getInt("iCveForma"));
      lPStmt.setInt(17, vData.getInt("iCveMaterial"));
      lPStmt.setString(18, vData.getString("cDscMarcaDiurna"));
      lPStmt.setString(19, vData.getString("cDscColor"));
      lPStmt.setInt(20, vData.getInt("iGradosIniVisible"));
      lPStmt.setInt(21, vData.getInt("iGradosFinVisible"));
      lPStmt.setInt(22, vData.getInt("lLuminosa"));
      lPStmt.setInt(23, vData.getInt("lRadioelectrica"));
      lPStmt.setInt(24, vData.getInt("iCveSistEnergia"));
      lPStmt.setInt(25, vData.getInt("lMotogenerador"));
      lPStmt.setString(26, vData.getString("cDatosAdicionales"));
      lPStmt.setInt(27, vData.getInt("lCargoSCT"));
      lPStmt.setInt(28, vData.getInt("iCveOficinaResponsable"));
      lPStmt.setInt(29, vData.getInt("lCargoAPI"));
      lPStmt.setInt(30, vData.getInt("lEnConstruccion"));
      lPStmt.setString(31, vData.getString("cUbicacionPlano"));
      lPStmt.setInt(32, vData.getInt("iCvePuerto"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("insert", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback", e);
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
        warn("insert.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from RGSSenal where iCveSenal = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData, Connection cnNested) throws
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

      String lSQL = "update RGSSenal set lBajaDefinitiva=?, dtBajaDefinitiva=? where iCveSenal = ?  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,1);
      lPStmt.setDate(2, tFecha.TodaySQL());
      lPStmt.setInt(3, vData.getInt("iCveSenal"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("delete", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("delete.rollback", e);
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
        warn("delete.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return lSuccess;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update RGSSenal set iCveRegion=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iPuntoCardinal=?, iNumNacional=?, iConsecNumNacional=?, cLetraInternacional=?, iNumInternacional=?, cNomSenal=?, iAnioConstruccion=?, cDscUbicacion=?, dAlturaEstructura=?, iCveTipoSenal=?, iCveForma=?, iCveMaterial=?, cDscMarcaDiurna=?, cDscColor=?, iGradosIniVisible=?, iGradosFinVisible=?, lLuminosa=?, lRadioelectrica=?, iCveSistEnergia=?, lMotogenerador=?, cDatosAdicionales=?, lCargoSCT=?, iCveOficinaResponsable=?, lCargoAPI=? where iCveSenal = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData, Connection cnNested) throws
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
      String lSQL = "update RGSSenal set iCveRegion=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iPuntoCardinal=?, "+
                    "iNumNacional=?, iConsecNumNacional=?, cLetraInternacional=?, iNumInternacional=?, cNomSenal=?, "+
                    "iAnioConstruccion=?, cDscUbicacion=?, dAlturaEstructura=?, iCveTipoSenal=?, iCveForma=?, "+
                    "iCveMaterial=?, cDscMarcaDiurna=?, cDscColor=?, iGradosIniVisible=?, iGradosFinVisible=?, "+
                    "lLuminosa=?, lRadioelectrica=?, iCveSistEnergia=?, lMotogenerador=?, cDatosAdicionales=?, "+
                    "lCargoSCT=?, iCveOficinaResponsable=?, lCargoAPI=?, cUbicacionPlano=?, iCvePuerto=?, "+
                    "lAccesoXTierra=?, lAccesoXAgua=?, dDistanciaXTierra=?, dDistanciaXAgua=? "+
                    "where iCveSenal = ? ";

      Vector vcDataNum = findByCustom("", "select iCveSenal from RGSSenal "+
                                          " WHERE iCveEntidadFed = " + vData.getInt("iCveEntidadFed") +
                                          " AND iNumNacional = " + vData.getInt("iNumNacional") +
                                          " AND iConsecNumNacional = " + vData.getInt("iConsecNumNacional")
                                     );
      if(vcDataNum.size() > 0){
        if(((TVDinRep)vcDataNum.get(0)).getInt("iCveSenal") != vData.getInt("iCveSenal"))
          throw new DAOException("Actualizar - Ya existe una señal con el número nacional proporcionado");
      }

      vData.addPK(vData.getString("iCveSenal"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, vData.getInt("iCveRegion"));
      lPStmt.setInt(2, vData.getInt("iCvePais"));
      lPStmt.setInt(3, vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(4, vData.getInt("iCveMunicipio"));
      lPStmt.setInt(5, vData.getInt("iPuntoCardinal"));
      lPStmt.setInt(6, vData.getInt("iNumNacional"));
      lPStmt.setInt(7, vData.getInt("iConsecNumNacional"));
      lPStmt.setString(8, vData.getString("cLetraInternacional"));
      lPStmt.setInt(9, vData.getInt("iNumInternacional"));
      lPStmt.setString(10, vData.getString("cNomSenal"));
      lPStmt.setInt(11, vData.getInt("iAnioConstruccion"));
      lPStmt.setString(12, vData.getString("cDscUbicacion"));
      lPStmt.setDouble(13, vData.getDouble("dAlturaEstructura"));
      lPStmt.setInt(14, vData.getInt("iCveTipoSenal"));
      lPStmt.setInt(15, vData.getInt("iCveForma"));
      lPStmt.setInt(16, vData.getInt("iCveMaterial"));
      lPStmt.setString(17, vData.getString("cDscMarcaDiurna"));
      lPStmt.setString(18, vData.getString("cDscColor"));
      lPStmt.setInt(19, vData.getInt("iGradosIniVisible"));
      lPStmt.setInt(20, vData.getInt("iGradosFinVisible"));
      lPStmt.setInt(21, vData.getInt("lLuminosa"));
      lPStmt.setInt(22, vData.getInt("lRadioelectrica"));
      lPStmt.setInt(23, vData.getInt("iCveSistEnergia"));
      lPStmt.setInt(24, vData.getInt("lMotogenerador"));
      lPStmt.setString(25, vData.getString("cDatosAdicionales"));
      lPStmt.setInt(26, vData.getInt("lCargoSCT"));
      lPStmt.setInt(27, vData.getInt("iCveOficinaResponsable"));
      lPStmt.setInt(28, vData.getInt("lCargoAPI"));
      lPStmt.setString(29, vData.getString("cUbicacionPlano"));
      lPStmt.setInt(30, vData.getInt("iCvePuerto"));
      lPStmt.setInt(31,vData.getInt("lAccesoXTierra"));
      lPStmt.setInt(32,vData.getInt("lAccesoXAgua"));
      lPStmt.setDouble(33,vData.getDouble("dDistanciaXTierra"));
      lPStmt.setDouble(34,vData.getDouble("dDistanciaXAgua"));
      lPStmt.setInt(35, vData.getInt("iCveSenal"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("update", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback", e);
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
        warn("update.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update RGSSenal set iCveRegion=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iPuntoCardinal=?, iNumNacional=?, iConsecNumNacional=?, cLetraInternacional=?, iNumInternacional=?, cNomSenal=?, iAnioConstruccion=?, cDscUbicacion=?, dAlturaEstructura=?, iCveTipoSenal=?, iCveForma=?, iCveMaterial=?, cDscMarcaDiurna=?, cDscColor=?, iGradosIniVisible=?, iGradosFinVisible=?, lLuminosa=?, lRadioelectrica=?, iCveSistEnergia=?, lMotogenerador=?, cDatosAdicionales=?, lCargoSCT=?, iCveOficinaResponsable=?, lCargoAPI=? where iCveSenal = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep updateGenerales(TVDinRep vData, Connection cnNested) throws
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
      String lSQL = "update RGSSenal set iCveRegion=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iPuntoCardinal=?, iNumNacional=?, iConsecNumNacional=?, cLetraInternacional=?, iNumInternacional=?, cNomSenal=?, iAnioConstruccion=?, cDscUbicacion=?, iCveTipoSenal=?, lLuminosa=?, lRadioelectrica=?, lCargoSCT=?, iCveOficinaResponsable=?, lCargoAPI=?, cUbicacionPlano=?, iCvePuerto=? where iCveSenal = ? ";

      Vector vcDataNum = findByCustom("", "select iCveSenal from RGSSenal "+
                                          " WHERE iCveEntidadFed = " + vData.getInt("iCveEntidadFed") +
                                          " AND iNumNacional = " + vData.getInt("iNumNacional") +
                                          " AND iConsecNumNacional = " + vData.getInt("iConsecNumNacional")
                                     );
      if(vcDataNum.size() > 0){
        if(((TVDinRep)vcDataNum.get(0)).getInt("iCveSenal") != vData.getInt("iCveSenal"))
          throw new DAOException("Actualizar - Ya existe una señal con el número nacional proporcionado");
      }

      vData.addPK(vData.getString("iCveSenal"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, vData.getInt("iCveRegion"));
      lPStmt.setInt(2, vData.getInt("iCvePais"));
      lPStmt.setInt(3, vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(4, vData.getInt("iCveMunicipio"));
      lPStmt.setInt(5, vData.getInt("iPuntoCardinal"));
      lPStmt.setInt(6, vData.getInt("iNumNacional"));
      lPStmt.setInt(7, vData.getInt("iConsecNumNacional"));
      lPStmt.setString(8, vData.getString("cLetraInternacional"));
      lPStmt.setInt(9, vData.getInt("iNumInternacional"));
      lPStmt.setString(10, vData.getString("cNomSenal"));
      lPStmt.setInt(11, vData.getInt("iAnioConstruccion"));
      lPStmt.setString(12, vData.getString("cDscUbicacion"));
      lPStmt.setInt(13, vData.getInt("iCveTipoSenal"));
      lPStmt.setInt(14, vData.getInt("lLuminosa"));
      lPStmt.setInt(15, vData.getInt("lRadioelectrica"));
      lPStmt.setInt(16, vData.getInt("lCargoSCT"));
      lPStmt.setInt(17, vData.getInt("iCveOficinaResponsable"));
      lPStmt.setInt(18, vData.getInt("lCargoAPI"));
      lPStmt.setString(19, vData.getString("cUbicacionPlano"));
      lPStmt.setInt(20, vData.getInt("iCvePuerto"));
      lPStmt.setInt(21, vData.getInt("iCveSenal"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("update", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback", e);
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
        warn("update.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update RGSSenal set iCveRegion=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iPuntoCardinal=?, iNumNacional=?, iConsecNumNacional=?, cLetraInternacional=?, iNumInternacional=?, cNomSenal=?, iAnioConstruccion=?, cDscUbicacion=?, dAlturaEstructura=?, iCveTipoSenal=?, iCveForma=?, iCveMaterial=?, cDscMarcaDiurna=?, cDscColor=?, iGradosIniVisible=?, iGradosFinVisible=?, lLuminosa=?, lRadioelectrica=?, iCveSistEnergia=?, lMotogenerador=?, cDatosAdicionales=?, lCargoSCT=?, iCveOficinaResponsable=?, lCargoAPI=? where iCveSenal = ?  </b></p>
   * <p><b> Campos Llave: iCveSenal, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep updateDescriptivos(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update RGSSenal set dAlturaEstructura=?, iCveForma=?, iCveMaterial=?, cDscMarcaDiurna=?, "+
                    "cDscColor=?, iGradosIniVisible=?, iGradosFinVisible=?, iCveSistEnergia=?, lMotogenerador=?, "+
                    "cDatosAdicionales=?, lAccesoXTierra=?, lAccesoXAgua=?, dDistanciaXTierra=?, dDistanciaXAgua=? "+
                    "where iCveSenal = ? ";

      vData.addPK(vData.getString("iCveSenal"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDouble(1,vData.getDouble("dAlturaEstructura"));
      lPStmt.setInt(2,vData.getInt("iCveForma"));
      lPStmt.setInt(3,vData.getInt("iCveMaterial"));
      lPStmt.setString(4,vData.getString("cDscMarcaDiurna"));
      lPStmt.setString(5,vData.getString("cDscColor"));
      lPStmt.setInt(6,vData.getInt("iGradosIniVisible"));
      lPStmt.setInt(7,vData.getInt("iGradosFinVisible"));
      lPStmt.setInt(8,vData.getInt("iCveSistEnergia"));
      lPStmt.setInt(9,vData.getInt("lMotogenerador"));
      lPStmt.setString(10,vData.getString("cDatosAdicionales"));
      lPStmt.setInt(11,vData.getInt("lAccesoXTierra"));
      lPStmt.setInt(12,vData.getInt("lAccesoXAgua"));
      lPStmt.setDouble(13,vData.getDouble("dDistanciaXTierra"));
      lPStmt.setDouble(14,vData.getDouble("dDistanciaXAgua"));
      lPStmt.setInt(15,vData.getInt("iCveSenal"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
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
        throw new DAOException(cError);
      return vData;
    }
 }

 public Vector findCuadernoFaros(int iLitoral, int iEntidadFed, int iPuerto, boolean lEnConstruccion) throws DAOException{
   Vector vcRecords = new Vector();
   cError = "";
   try{
     String cSQL = "SELECT EFL.iCveLitoral, EFL.iOrden, L.cDscLitoral, EFL.iCveEntidadFed, EF.cNombre AS cDscEntidad, "+
                   " S.iPuntoCardinal, S.iCveSenal, S.iNumNacional, S.iConsecNumNacional, S.cNomSenal, S.cDscUbicacion, "+
                   " S.cLetraInternacional, S.iNumInternacional, S.dAlturaEstructura, S.cDscMarcaDiurna, S.cDscColor, "+
                   " S.iGradosIniVisible, S.iGradosFinVisible, S.lMotogenerador, S.cDatosAdicionales, "+
                   " SL.dPeriodo, SL.dAlcanceGeografico, SL.dAlcanceLuminosoMin, SL.dAlcanceLuminosoMax, SL.iOptica, "+
                   " SL.iCveTipoLinterna, TL.cDscTipoLinterna, RE.cCodigoMorse, RE.iRespuestaAzimutal, RE.iCobertura, "+
                   " S.iCveTipoSenal, TS.cDscTipoSenal, S.iCveForma, F.cDscForma, S.iCveMaterial, M.cDscMaterial, "+
                   " S.iCveSistEnergia, SE.cDscSistEnergia, S.lLuminosa, S.lRadioelectrica, S.iCvePuerto, P.cDscPuerto "+
                   " FROM GRLEntidadFedXLitoral EFL "+
                   " JOIN GRLLitoral L ON L.iCveLitoral = EFL.iCveLitoral "+
                   " JOIN GRLEntidadFed EF ON EF.iCvePais = EFL.iCvePais AND EF.iCveEntidadFed = EFL.iCveEntidadFed "+
                   " JOIN RGSSenal S ON S.iCvePais = EFL.iCvePais AND S.iCveEntidadFed = EFL.iCveEntidadFed "+
                   " LEFT JOIN RGSLuminosa SL ON SL.iCveSenal = S.iCveSenal "+
                   " LEFT JOIN RGSTipoLinterna TL ON TL.iCveTipoLinterna = SL.iCveTipoLinterna "+
                   " LEFT JOIN RGSRadioElectrica RE ON RE.iCveSenal = S.iCveSenal "+
                   " LEFT JOIN RGSTipoSenal TS ON TS.iCveTipoSenal = S.iCveTipoSenal "+
                   " LEFT JOIN RGSForma F ON F.iCveForma = S.iCveForma "+
                   " LEFT JOIN RGSMaterial M ON M.iCveMaterial = S.iCveMaterial "+
                   " LEFT JOIN RGSSistEnergia SE ON SE.iCvesistEnergia = S.iCveSistEnergia "+
                   " LEFT JOIN GRLPuerto P ON P.iCvePuerto = S.iCvePuerto "+
                   " WHERE S.lEnConstruccion = " + ((lEnConstruccion)?"1":"0")+
                   ((iLitoral > 0)?" AND EFL.iCveLitoral = " + iLitoral:"") +
                   ((iEntidadFed > 0)?" AND EFL.iCveEntidadFed = " + iEntidadFed:"")+
                   ((iPuerto >= 0)?" AND P.iCvePuerto = " + iPuerto:"")+
                   " ORDER BY EFL.iCveLitoral, EFL.iOrden, S.iPuntoCardinal DESC, S.iNumNacional, S.iConsecNumNacional ";
     vcRecords = this.FindByGeneric("", cSQL, dataSourceName);
   } catch(Exception e){
     cError = e.getMessage();
   } finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vcRecords;
   }
 }

 public Vector findCuadernoFaros(int iLitoral, int iEntidadFed, int iPuerto,int iCvePersona, boolean lEnConstruccion) throws DAOException{
   Vector vcRecords = new Vector();
   cError = "";
   try{
     String cSQL = "SELECT EFL.iCveLitoral, EFL.iOrden, L.cDscLitoral, EFL.iCveEntidadFed, EF.cNombre AS cDscEntidad, "+
                   " S.iPuntoCardinal, S.iCveSenal, S.iNumNacional, S.iConsecNumNacional, S.cNomSenal, S.cDscUbicacion, "+
                   " S.cLetraInternacional, S.iNumInternacional, S.dAlturaEstructura, S.cDscMarcaDiurna, S.cDscColor, "+
                   " S.iGradosIniVisible, S.iGradosFinVisible, S.lMotogenerador, S.cDatosAdicionales, "+
                   " SL.dPeriodo, SL.dAlcanceGeografico, SL.dAlcanceLuminosoMin, SL.dAlcanceLuminosoMax, SL.iOptica, "+
                   " SL.iCveTipoLinterna, TL.cDscTipoLinterna, RE.cCodigoMorse, RE.iRespuestaAzimutal, RE.iCobertura, "+
                   " S.iCveTipoSenal, TS.cDscTipoSenal, S.iCveForma, F.cDscForma, S.iCveMaterial, M.cDscMaterial, "+
                   " S.iCveSistEnergia, SE.cDscSistEnergia, S.lLuminosa, S.lRadioelectrica, S.iCvePuerto, P.cDscPuerto "+
                   " FROM GRLEntidadFedXLitoral EFL "+
                   " JOIN GRLLitoral L ON L.iCveLitoral = EFL.iCveLitoral "+
                   " JOIN GRLEntidadFed EF ON EF.iCvePais = EFL.iCvePais AND EF.iCveEntidadFed = EFL.iCveEntidadFed "+
                   " JOIN RGSSenal S ON S.iCvePais = EFL.iCvePais AND S.iCveEntidadFed = EFL.iCveEntidadFed "+
                   " LEFT JOIN RGSLuminosa SL ON SL.iCveSenal = S.iCveSenal "+
                   " LEFT JOIN RGSTipoLinterna TL ON TL.iCveTipoLinterna = SL.iCveTipoLinterna "+
                   " LEFT JOIN RGSRadioElectrica RE ON RE.iCveSenal = S.iCveSenal "+
                   " LEFT JOIN RGSTipoSenal TS ON TS.iCveTipoSenal = S.iCveTipoSenal "+
                   " LEFT JOIN RGSForma F ON F.iCveForma = S.iCveForma "+
                   " LEFT JOIN RGSMaterial M ON M.iCveMaterial = S.iCveMaterial "+
                   " LEFT JOIN RGSSistEnergia SE ON SE.iCvesistEnergia = S.iCveSistEnergia "+
                   " LEFT JOIN GRLPuerto P ON P.iCvePuerto = S.iCvePuerto "+
                   " LEFT JOIN RGSRESPONSABLESENAL RS ON S.ICVESENAL = RS.ICVESENAL" +
                   " WHERE S.lEnConstruccion = " + ((lEnConstruccion)?"1":"0")+
                   ((iLitoral > 0)?" AND EFL.iCveLitoral = " + iLitoral:"") +
                   ((iEntidadFed > 0)?" AND EFL.iCveEntidadFed = " + iEntidadFed:"")+
                   ((iPuerto >= 0)?" AND P.iCvePuerto = " + iPuerto:"")+
                   ((iCvePersona >= 0)?" AND RS.iCvePersona = " + iCvePersona:"")+
                   " ORDER BY EFL.iCveLitoral, EFL.iOrden, S.iPuntoCardinal DESC, S.iNumNacional, S.iConsecNumNacional ";
     vcRecords = this.FindByGeneric("", cSQL, dataSourceName);
   } catch(Exception e){
     cError = e.getMessage();
   } finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vcRecords;
   }
 }

}

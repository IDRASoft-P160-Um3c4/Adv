package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;


/**
 * <p>Title: TDCYSAutImpactoAmbiental.java</p>
 * <p>Description: DAO de la entidad CYSAutImpactoAmbiental</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSAutImpactoAmbiental extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private static final int iAUTORIZACION_EN_MATERIA_DE_IMPACTO_AMBIENTAL = 5;
  private static final int iDOC_ACREDITA_AUT_EN_MATERIA_DE_IMPACTO_AMBIENTAL = 6;
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
   * <p><b> insert into CYSAutImpactoAmbiental(iEjercicio,iMovEdoFinanciero,dtRegistro,dtPresentacion,dtPublicacion,iCveTitulo,cObsEdoFinanciero) values (?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
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
      String lSQL = "insert into CYSAutImpactoAmbiental(iCveTitulo, " +
                    "iMovAutImpacto, " +
                    "dtRegistro, " +
                    "dtPresentacion, " +
                    "dtAutorizacion, " +
                    "cNumAutorizacion, " +
                    "cObjeto, " +
                    "dtIniVigenciaConstruccion, " +
                    "dtFinVigenciaConstruccion, " +
                    "dtIniVigenciaOperacion, " +
                    "dtFinVigenciaOperacion, " +
                    "cObsAutorizacion, " +
                    "iCveTituloDocto, " +
                    "iMovTitDocumento, " +
                    "cNumSolSemarnat, " +
                    "dtSolSemarnat) " +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovAutImpacto) AS iMovAutImpacto " +
                                      "from CYSAutImpactoAmbiental  " +
                                      "where iCveTitulo = " + vData.getInt("iCveTitulo"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovAutImpacto",vUltimo.getInt("iMovAutImpacto") + 1);
      }else
         vData.put("iMovAutImpacto",1);

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovAutImpacto"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovAutImpacto"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setDate(5,vData.getDate("dtAutorizacion"));
      lPStmt.setString(6,vData.getString("cNumAutorizacion"));
      lPStmt.setString(7,vData.getString("cObjeto"));
      lPStmt.setDate(8,vData.getDate("dtIniVigenciaConstruccion"));
      lPStmt.setDate(9,vData.getDate("dtFinVigenciaConstruccion"));
      lPStmt.setDate(10,vData.getDate("dtIniVigenciaOperacion"));
      lPStmt.setDate(11,vData.getDate("dtFinVigenciaOperacion"));
      lPStmt.setString(12,vData.getString("cObsAutorizacion"));
      lPStmt.setInt(13,vData.getInt("iCveTituloDocto"));
      lPStmt.setInt(14,vData.getInt("iMovTitDocumento"));
      lPStmt.setString(15,vData.getString("cNumSolSemarnat"));
      lPStmt.setDate(16,vData.getDate("dtSolSemarnat"));
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
   * <p><b> delete from CYSAutImpactoAmbiental where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
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
      String lSQL = "delete from CYSAutImpactoAmbiental where iCveTitulo = ? AND iMovAutImpacto = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovAutImpacto"));

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
   * <p><b> update CYSAutImpactoAmbiental set dtRegistro=?, dtPresentacion=?, dtPublicacion=?, iCveTitulo=?, cObsEdoFinanciero=? where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
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
      String lSQL = "update CYSAutImpactoAmbiental set dtRegistro=?, " +
                    "dtPresentacion=?, " +
                    "dtAutorizacion=?, " +
                    "cNumAutorizacion=?, " +
                    "cObjeto=?, " +
                    "dtIniVigenciaConstruccion=?, " +
                    "dtFinVigenciaConstruccion=?, " +
                    "dtIniVigenciaOperacion=?, " +
                    "dtFinVigenciaOperacion=?, " +
                    "cObsAutorizacion=?, " +
                    "iCveTituloDocto=?, " +
                    "iMovTitDocumento=?, " +
                    "cNumSolSemarnat=?, " +
                    "dtSolSemarnat=? " +
                    "where iCveTitulo=? " +
                    "and iMovAutImpacto=? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovAutImpacto"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setDate(3,vData.getDate("dtAutorizacion"));
      lPStmt.setString(4,vData.getString("cNumAutorizacion"));
      lPStmt.setString(5,vData.getString("cObjeto"));
      lPStmt.setDate(6,vData.getDate("dtIniVigenciaConstruccion"));
      lPStmt.setDate(7,vData.getDate("dtFinVigenciaConstruccion"));
      lPStmt.setDate(8,vData.getDate("dtIniVigenciaOperacion"));
      lPStmt.setDate(9,vData.getDate("dtFinVigenciaOperacion"));
      lPStmt.setString(10,vData.getString("cObsAutorizacion"));
      lPStmt.setInt(11,vData.getInt("iCveTituloDocto"));
      lPStmt.setInt(12,vData.getInt("iMovTitDocumento"));
      lPStmt.setString(13,vData.getString("cNumSolSemarnat"));
      lPStmt.setDate(14,vData.getDate("dtSolSemarnat"));
      lPStmt.setInt(15,vData.getInt("iCveTitulo"));
      lPStmt.setInt(16,vData.getInt("iMovAutImpacto"));
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
  * Agregado por Rafael Alcocer Caldera 27/Nov/2006
  *
  * De acuerdo al query itera sobre los registros obtenidos
  * para obtener el que tenga el maximo iMovTitDocumento.
  * Una vez obtenido el registro se verifica el iCveTipoDocumento
  * Si es 5:
  * -------
  *            Si dtIniVigenciaDocto <= dtFceha y
  *               dtFecha <= dtVigenciaDocumento
  *            => regresa true
  *            En caso contrario
  *            => regresa false
  * Si es 6:
  * -------
  *           Si cNumSolSemarnat != "" y
  *              dtSolSemarnat != null
  *           => regresa false, cNumSolSemarnat, dtSolSemarnat
  *           En caso contrario
  *              Si cNumAutorizacion != "" y
  *                 dtAutorizacion !=  null
  *              Entonces Si dtIniVigenciaConstruccion <= dtFecha y
  *                          dtFecha <= dtFinVigenciaConstruccion
  *                       => regresa true
  *              En caso contrario
  *                       => regresa false

  *
  * @param cKey String
  * @param iTitulo int
  * @param dtFecha Date
  * @throws DAOException
  * @return Vector
  */
 public Vector findBySitObligacionConst(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
   Vector vcSitObligacion = new Vector();

     String cSQL = "select cysaia.iCveTitulo, " +
                   "       cysaia.iMovAutImpacto, " +
                   "       cysaia.dtRegistro, " +
                   "       cysaia.dtPresentacion, " +
                   "       cysaia.dtAutorizacion, " +
                   "       cysaia.cNumAutorizacion, " +
                   "       cysaia.cObjeto, " +
                   "       cysaia.dtIniVigenciaConstruccion, " +
                   "       cysaia.dtFinVigenciaConstruccion, " +
                   "       cysaia.dtIniVigenciaOperacion, " +
                   "       cysaia.dtFinVigenciaOperacion, " +
                   "       cysaia.cObsAutorizacion, " +
                   "       cysaia.iCveTituloDocto, " +
                   "       cysaia.iMovTitDocumento, " +
                   "       cysaia.cNumSolSemarnat, " +
                   "       cysaia.dtSolSemarnat, " +
                   "       cpatd.iCveTitulo, " +
                   "       cpatd.iMovTitDocumento, " +
                   "       cpatd.iCveTipoDocumento, " +
                   "       cpatd.cNumDocumento, " +
                   "       cpatd.dtDocumento, " +
                   "       cpatd.dtVigenciaDocumento, " +
                   "       cpatd.iCveDepEmiteDoc, " +
                   "       cpatd.cObjetoDocumento, " +
                   "       cpatd.cSuperficieDocumento, " +
                   "       cpatd.lMaritimoTerrestre, " +
                   "       cpatd.iCveClasificacionDocto, " +
                   "       cpatd.dtIniVigenciaDocto, " +
                   "       cpatd.cUniAdmvaDepemite, " +
                   "       cpatd.dtIniVigConstruccion, " +
                   "       cpatd.dtFinVigConstruccion, " +
                   "       cpatd.dtIniVigOperacion, " +
                   "       cpatd.dtFinVigOperacion " +
                   " from CYSAutImpactoAmbiental cysaia " +
                   " inner join CPATituloDocumento cpatd on cysaia.iCveTituloDocto = cpatd.iCveTitulo " +
                   "                                    and cysaia.iMovTitDocumento = cpatd.iMovTitDocumento " +
                   " where cysaia.iCveTitulo = " + iTitulo;

    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();
    int iIndex = 0;
    int iTemp = 0;

    // Se itera para obtener el registro que tenga el mayor iMovTitDocumento
    for (int i = 0; i < vcRecords.size(); i++) {
      vDinRep = (TVDinRep)vcRecords.get(i);
      int iMovTitDocumento = vDinRep.getInt("iMovTitDocumento");

      if (iMovTitDocumento > iTemp) {
        iTemp = iMovTitDocumento;
        iIndex = i;
      }
    }

    if (iIndex == 0) {
      vcSitObligacion.add(new Boolean(false));
    } else if (iIndex > 0) {
      vDinRep = (TVDinRep)vcRecords.get(iIndex);
      int iCveTipoDocumento = vDinRep.getInt("iCveTipoDocumento");

      switch (iCveTipoDocumento) {
          case 5:
            if ((vDinRep.getDate("dtIniVigenciaDocto").getTime() <= dtFecha.getTime()) &&
                (dtFecha.getTime() <= vDinRep.getDate("dtVigenciaDocumento").getTime())) {
              vcSitObligacion.add(new Boolean(true));
            } else {
              vcSitObligacion.add(new Boolean(false));
            }

            break;
          case 6:
            if (!vDinRep.getString("cNumSolSemarnat").equalsIgnoreCase("") &&
                vDinRep.getDate("dtSolSemarnat") !=  null) {
              vcSitObligacion.add(new Boolean(false));
              vcSitObligacion.add(vDinRep.getString("cNumSolSemarnat"));
              vcSitObligacion.add(vDinRep.getDate("dtSolSemarnat"));
            } else if (!vDinRep.getString("cNumAutorizacion").equalsIgnoreCase("") &&
                       vDinRep.getDate("dtAutorizacion") !=  null) {
                if ((vDinRep.getDate("dtIniVigenciaConstruccion").getTime() <= dtFecha.getTime()) &&
                    (dtFecha.getTime() <= vDinRep.getDate("dtFinVigenciaConstruccion").getTime())) {
                  vcSitObligacion.add(new Boolean(true));
                } else {
                  vcSitObligacion.add(new Boolean(false));
                }
            }

            break;
      }
    }

    return vcSitObligacion;
 }
 /**
  * Agregado por Rafael Alcocer Caldera 27/Nov/2006
  *
  * De acuerdo al query itera sobre los registros obtenidos
  * para obtener el que tenga el maximo iMovTitDocumento.
  * Una vez obtenido el registro se verifica el iCveTipoDocumento
  * Si es 5:
  * -------
  *            Si dtIniVigenciaDocto <= dtFceha y
  *               dtFecha <= dtVigenciaDocumento
  *            => regresa true
  *            En caso contrario
  *            => regresa false
  * Si es 6:
  * -------
  *           Si cNumSolSemarnat != "" y
  *              dtSolSemarnat != null
  *           => regresa false, cNumSolSemarnat, dtSolSemarnat
  *           En caso contrario
  *              Si cNumAutorizacion != "" y
  *                 dtAutorizacion !=  null
  *              Entonces Si dtIniVigenciaOperacion <= dtFecha y
  *                          dtFecha <= dtFinVigenciaOperacion
  *                       => regresa true
  *              En caso contrario
  *                       => regresa false

  *
  * @param cKey String
  * @param iTitulo int
  * @param dtFecha Date
  * @throws DAOException
  * @return Vector
  */
 public Vector findBySitObligacionOp(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
   Vector vcSitObligacion = new Vector();

     String cSQL = "select cysaia.iCveTitulo, " +
                   "       cysaia.iMovAutImpacto, " +
                   "       cysaia.dtRegistro, " +
                   "       cysaia.dtPresentacion, " +
                   "       cysaia.dtAutorizacion, " +
                   "       cysaia.cNumAutorizacion, " +
                   "       cysaia.cObjeto, " +
                   "       cysaia.dtIniVigenciaConstruccion, " +
                   "       cysaia.dtFinVigenciaConstruccion, " +
                   "       cysaia.dtIniVigenciaOperacion, " +
                   "       cysaia.dtFinVigenciaOperacion, " +
                   "       cysaia.cObsAutorizacion, " +
                   "       cysaia.iCveTituloDocto, " +
                   "       cysaia.iMovTitDocumento, " +
                   "       cysaia.cNumSolSemarnat, " +
                   "       cysaia.dtSolSemarnat, " +
                   "       cpatd.iCveTitulo, " +
                   "       cpatd.iMovTitDocumento, " +
                   "       cpatd.iCveTipoDocumento, " +
                   "       cpatd.cNumDocumento, " +
                   "       cpatd.dtDocumento, " +
                   "       cpatd.dtVigenciaDocumento, " +
                   "       cpatd.iCveDepEmiteDoc, " +
                   "       cpatd.cObjetoDocumento, " +
                   "       cpatd.cSuperficieDocumento, " +
                   "       cpatd.lMaritimoTerrestre, " +
                   "       cpatd.iCveClasificacionDocto, " +
                   "       cpatd.dtIniVigenciaDocto, " +
                   "       cpatd.cUniAdmvaDepemite, " +
                   "       cpatd.dtIniVigConstruccion, " +
                   "       cpatd.dtFinVigConstruccion, " +
                   "       cpatd.dtIniVigOperacion, " +
                   "       cpatd.dtFinVigOperacion " +
                   " from CYSAutImpactoAmbiental cysaia " +
                   " inner join CPATituloDocumento cpatd on cysaia.iCveTituloDocto = cpatd.iCveTitulo " +
                   "                                    and cysaia.iMovTitDocumento = cpatd.iMovTitDocumento " +
                   " where cysaia.iCveTitulo = " + iTitulo;

    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();
    int iIndex = 0;
    int iTemp = 0;

    // Se itera para obtener el registro que tenga el mayor iMovTitDocumento
    for (int i = 0; i < vcRecords.size(); i++) {
      vDinRep = (TVDinRep)vcRecords.get(i);
      int iMovTitDocumento = vDinRep.getInt("iMovTitDocumento");

      if (iMovTitDocumento > iTemp) {
        iTemp = iMovTitDocumento;
        iIndex = i;
      }
    }

    if (iIndex == 0) {
      vcSitObligacion.add(new Boolean(false));
    } else if (iIndex > 0) {
      vDinRep = (TVDinRep)vcRecords.get(iIndex);
      int iCveTipoDocumento = vDinRep.getInt("iCveTipoDocumento");

      switch (iCveTipoDocumento) {
          case iAUTORIZACION_EN_MATERIA_DE_IMPACTO_AMBIENTAL:
            if ((vDinRep.getDate("dtIniVigenciaDocto").getTime() <= dtFecha.getTime()) &&
                (dtFecha.getTime() <= vDinRep.getDate("dtVigenciaDocumento").getTime())) {
              vcSitObligacion.add(new Boolean(true));
            } else {
              vcSitObligacion.add(new Boolean(false));
            }

            break;
          case iDOC_ACREDITA_AUT_EN_MATERIA_DE_IMPACTO_AMBIENTAL:
            if (!vDinRep.getString("cNumSolSemarnat").equalsIgnoreCase("") &&
                vDinRep.getDate("dtSolSemarnat") !=  null) {
              vcSitObligacion.add(new Boolean(false));
              vcSitObligacion.add(vDinRep.getString("cNumSolSemarnat"));
              vcSitObligacion.add(vDinRep.getDate("dtSolSemarnat"));
            } else if (!vDinRep.getString("cNumAutorizacion").equalsIgnoreCase("") &&
                       vDinRep.getDate("dtAutorizacion") !=  null) {
                if ((vDinRep.getDate("dtIniVigenciaOperacion").getTime() <= dtFecha.getTime()) &&
                    (dtFecha.getTime() <= vDinRep.getDate("dtFinVigenciaOperacion").getTime())) {
                  vcSitObligacion.add(new Boolean(true));
                } else {
                  vcSitObligacion.add(new Boolean(false));
                }
            }

            break;
      }
    }

    return vcSitObligacion;
 }

}

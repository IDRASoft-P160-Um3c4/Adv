package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;


/**
 * <p>Title: TDCYSConcesionZonaFederal.java</p>
 * <p>Description: DAO de la entidad CYSConcesionZonaFederal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSConcesionZonaFederal extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private static final int iCONCESION_DE_ZONA_FEDERAL_MARITIMO_TERRESTRE = 1; // RAC
  private static final int iCONCESION_DE_ZONA_FEDERAL_TERRESTRE = 2; // RAC
  private static final int iSOLICITUD_DE_CONCESION_DE_ZONA_FEDERAL_MARITIMO_TERRESTRE = 3; // RAC
  private static final int iSOLICITUD_DE_CONCESION_DE_ZONA_FEDERAL_TERRESTRE = 4; // RAC
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
   * <p><b> insert into CYSConcesionZonaFederal(iEjercicio,iMovEdoFinanciero,dtRegistro,dtPresentacion,dtPublicacion,iCveTitulo,cObsEdoFinanciero) values (?,?,?,?,?,?,?) </b></p>
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
      String lSQL = "insert into CYSConcesionZonaFederal(iCveTitulo, " +
                    "iMovConcesionZona, " +
                    "dtRegistro, " +
                    "dtPresentacion, " +
                    "dtConcesion, " +
                    "cNumConcesion, " +
                    "cObjeto, " +
                    "dtIniVigencia, " +
                    "dtFinVigencia, " +
                    "cObsConcesion, " +
                    "iCveTituloDocto, " +
                    "iMovTitDocumento, " +
                    "cNumSolZoFeMat, " +
                    "dtSolZoFeMat) " +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovConcesionZona) AS iMovConcesionZona " +
                                      "from CYSConcesionZonaFederal  " +
                                      "where iCveTitulo = " + vData.getInt("iCveTitulo"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovConcesionZona",vUltimo.getInt("iMovConcesionZona") + 1);
      }else
         vData.put("iMovConcesionZona",1);

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovConcesionZona"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovConcesionZona"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setDate(5,vData.getDate("dtConcesion"));
      lPStmt.setString(6,vData.getString("cNumConcesion"));
      lPStmt.setString(7,vData.getString("cObjeto"));
      lPStmt.setDate(8,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(9,vData.getDate("dtFinVigencia"));
      lPStmt.setString(10,vData.getString("cObsConcesion"));
      lPStmt.setInt(11,vData.getInt("iCveTituloDocto"));
      lPStmt.setInt(12,vData.getInt("iMovTitDocumento"));
      lPStmt.setString(13,vData.getString("cNumSolZoFeMat"));
      lPStmt.setDate(14,vData.getDate("dtSolZoFeMat"));
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
   * <p><b> delete from CYSConcesionZonaFederal where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
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
      String lSQL = "delete from CYSConcesionZonaFederal where iCveTitulo = ? AND iMovConcesionZona = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovConcesionZona"));

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
   * <p><b> update CYSConcesionZonaFederal set dtRegistro=?, dtPresentacion=?, dtPublicacion=?, iCveTitulo=?, cObsEdoFinanciero=? where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
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
      String lSQL = "update CYSConcesionZonaFederal set dtRegistro=?, " +
                    "dtPresentacion=?, " +
                    "dtConcesion=?, " +
                    "cNumConcesion=?, " +
                    "cObjeto=?, " +
                    "dtIniVigencia=?, " +
                    "dtFinVigencia=?, " +
                    "cObsConcesion=?, " +
                    "iCveTituloDocto=?, " +
                    "iMovTitDocumento=?, " +
                    "cNumSolZoFeMat=?, " +
                    "dtSolZoFeMat=? " +
                    "where iCveTitulo=? " +
                    "and iMovConcesionZona=? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovConcesionZona"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setDate(3,vData.getDate("dtConcesion"));
      lPStmt.setString(4,vData.getString("cNumConcesion"));
      lPStmt.setString(5,vData.getString("cObjeto"));
      lPStmt.setDate(6,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(7,vData.getDate("dtFinVigencia"));
      lPStmt.setString(8,vData.getString("cObsConcesion"));
      lPStmt.setInt(9,vData.getInt("iCveTituloDocto"));
      lPStmt.setInt(10,vData.getInt("iMovTitDocumento"));
      lPStmt.setString(11,vData.getString("cNumSolZoFeMat"));
      lPStmt.setDate(12,vData.getDate("dtSolZoFeMat"));
      lPStmt.setInt(13,vData.getInt("iCveTitulo"));
      lPStmt.setInt(14,vData.getInt("iMovConcesionZona"));
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
  * Si es 1 ó 2:
  * ------------
  *            Si dtIniVigenciaDocto <= dtFceha y
  *               dtFecha <= dtFinVigenciaDocumento
  *            => regresa true
  *            En caso contrario
  *            => regresa false
  * Si es 3 ó 4:
  * -----------
  *           Si cNumSolZoFeMat != "" y
  *              dtSolZoFeMat != null
  *           => regresa false, cNumSolZoFeMat, dtSolZoFeMat
  *           En caso contrario
  *              Si cNumConcesion != "" y
  *                 dtConcesion !=  null
  *              Entonces Si dtIniVigencia <= dtFecha y
  *                          dtFecha <= dtFinVigencia
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
 public Vector findBySitObligacion(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
   Vector vcSitObligacion = new Vector();

     String cSQL = "select cysczf.iCveTitulo, " +
                   "       cysczf.iMovConcesionZona, " +
                   "       cysczf.dtRegistro, " +
                   "       cysczf.dtPresentacion, " +
                   "       cysczf.dtConcesion, " +
                   "       cysczf.cNumConcesion, " +
                   "       cysczf.cObjeto, " +
                   "       cysczf.dtIniVigencia, " +
                   "       cysczf.dtFinVigencia, " +
                   "       cysczf.cObsConcesion, " +
                   "       cysczf.iCveTituloDocto, " +
                   "       cysczf.iMovTitDocumento, " +
                   "       cysczf.cNumSolZoFeMat, " +
                   "       cysczf.dtSolZoFeMat, " +
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
                   " from CYSConcesionZonaFederal cysczf " +
                   " inner join CPATituloDocumento cpatd on cysczf.iCveTituloDocto = cpatd.iCveTitulo " +
                   "                                    and cysczf.iMovTitDocumento = cpatd.iMovTitDocumento " +
                   " where cysczf.iCveTitulo = " + iTitulo;

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
          case iCONCESION_DE_ZONA_FEDERAL_MARITIMO_TERRESTRE:
          case iCONCESION_DE_ZONA_FEDERAL_TERRESTRE:
            if ((vDinRep.getDate("dtIniVigenciaDocto").getTime() <= dtFecha.getTime()) &&
                (dtFecha.getTime() <= vDinRep.getDate("dtVigenciaDocumento").getTime())) {
              vcSitObligacion.add(new Boolean(true));
            } else {
              vcSitObligacion.add(new Boolean(false));
            }

            break;
          case iSOLICITUD_DE_CONCESION_DE_ZONA_FEDERAL_MARITIMO_TERRESTRE:
          case iSOLICITUD_DE_CONCESION_DE_ZONA_FEDERAL_TERRESTRE:
            if (!vDinRep.getString("cNumSolZoFeMat").equalsIgnoreCase("") &&
                vDinRep.getDate("dtSolZoFeMat") !=  null) {
              vcSitObligacion.add(new Boolean(false));
              vcSitObligacion.add(vDinRep.getString("cNumSolZoFeMat"));
              vcSitObligacion.add(vDinRep.getDate("dtSolZoFeMat"));
            } else if (!vDinRep.getString("cNumConcesion").equalsIgnoreCase("") &&
                       vDinRep.getDate("dtConcesion") !=  null) {
                if ((vDinRep.getDate("dtIniVigencia").getTime() <= dtFecha.getTime()) &&
                    (dtFecha.getTime() <= vDinRep.getDate("dtFinVigencia").getTime())) {
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

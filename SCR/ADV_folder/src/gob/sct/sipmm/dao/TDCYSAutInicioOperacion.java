package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;


/**
 * <p>Title: TDCYSAutInicioOperacion.java</p>
 * <p>Description: DAO de la entidad CYSAutInicioOperacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSAutInicioOperacion extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private static final int iTITULO_DE_AUTORIZACION = 3; // RAC
  private static final int iINICIO_DE_OPERACION = 16; // RAC
  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepci�n de tipo DAO
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
   * <p><b> insert into CYSAutInicioOperacion(iEjercicio,iMovEdoFinanciero,dtRegistro,dtPresentacion,dtPublicacion,iCveTitulo,cObsEdoFinanciero) values (?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - VO Din�mico que contiene a la entidad a Insertada, as� como a la llave de esta entidad.
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
      String lSQL = "insert into CYSAutInicioOperacion(iCveTitulo, " +
                    "iMovAutIO, " +
                    "dtRegistro, " +
                    "dtPresentacion, " +
                    "cObsAutorizacion, " +
                    "iCveTituloAIO) " +
                    " values (?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovAutIO) AS iMovAutIO " +
                                      "from CYSAutInicioOperacion  " +
                                      "where iCveTitulo = " + vData.getInt("iCveTitulo"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovAutIO",vUltimo.getInt("iMovAutIO") + 1);
      }else
         vData.put("iMovAutIO",1);

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovAutIO"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovAutIO"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setString(5,vData.getString("cObsAutorizacion"));
      lPStmt.setInt(6,vData.getInt("iCveTituloAIO"));
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
   * Elimina al registro a trav�s de la entidad dada por vData.
   * <p><b> delete from CYSAutInicioOperacion where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
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
      String lSQL = "delete from CYSAutInicioOperacion where iCveTitulo = ? AND iMovAutIO = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovAutIO"));

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
   * <p><b> update CYSAutInicioOperacion set dtRegistro=?, dtPresentacion=?, dtPublicacion=?, iCveTitulo=?, cObsEdoFinanciero=? where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
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
      String lSQL = "update CYSAutInicioOperacion set dtRegistro=?, " +
                    "dtPresentacion=?, " +
                    "cObsAutorizacion=?, " +
                    "iCveTituloAIO=? " +
                    "where iCveTitulo=? " +
                    "and iMovAutIO=? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovAutIO"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setString(3,vData.getString("cObsAutorizacion"));
      lPStmt.setInt(4,vData.getInt("iCveTituloAIO"));
      lPStmt.setInt(5,vData.getInt("iCveTitulo"));
      lPStmt.setInt(6,vData.getInt("iMovAutIO"));
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
  * Agregado por Rafael Alcocer Caldera 28/Nov/2006
  *
  * @param cKey String
  * @param iTitulo int
  * @param dtFecha Date
  * @throws DAOException
  * @return Vector
  */
 public Vector findBySitObligacion(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
   Vector vcSitObligacion = new Vector();

     String cSQL = "select cysaio.iCveTitulo, " +
                   "       cysaio.iMovAutIO, " +
                   "       cysaio.dtRegistro, " +
                   "       cysaio.dtPresentacion, " +
                   "       cysaio.cObsAutorizacion, " +
                   "       cysaio.iCveTituloAIO, " +
                   "       cpat.iCveTitulo, " +
                   "       cpat.iCveTipoTitulo, " +
                   "       cpat.cNumTitulo, " +
                   "       cpat.cNumTituloAnterior, " +
                   "       cpat.cNumTituloRelacionado, " +
                   "       cpat.dtVigenciaTitulo, " +
                   "       cpat.dtIniVigenciaTitulo, " +
                   "       cpat.cZonaFedAfectadaTerrestre, " +
                   "       cpat.cZonaFedAfectadaAgua, " +
                   "       cpat.cObjetoTitulo, " +
                   "       cpat.iCveUsoTitulo, " +
                   "       cpat.cMontoInversion, " +
                   "       cpat.dtPublicacionDOF, " +
                   "       cpat.lFirmado, " +
                   "       cpat.cZonaOpNoExcTerrestre, " +
                   "       cpat.cZonaOpNoExcAgua, " +
                   "       cpat.cZonaFedTotalTerrestre, " +
                   "       cpat.cZonaFedTotalAgua, " +
                   "       cpat.iCveClasificacion, " +
                   "       cpat.iCveTituloAnterior, " +
                   "       cpat.iPropiedad, " +
                   "       cpat.lAPI, " +
                   "       cpat.lTerminado, " +
                   "       cpat.lSustituido, " +
                   "       cpat.lArchivado " +
                   " from CYSAutInicioOperacion cysaio " +
                   " inner join CPATitulo cpat on cysaio.iCveTituloAIO = cpat.iCveTitulo " +
                   " where cysaio.iCveTitulo = " + iTitulo +
                   " and cysaio.iMovAutIO = (select max(iMovAutIO) from CYSAutInicioOperacion where iCveTitulo = " + iTitulo + ")";

    // findByCustom regresa un Vector no null aunque puede estar vacio
    // del query solo voy a obtener un registro
    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();

    if (vcRecords.size() > 0) {
      // Solo hay un registro obtenido
      vDinRep = (TVDinRep)vcRecords.get(0);

      // Se verifica que el registro obtenido tenga los isg. datos
      if (vDinRep.getInt("iCveTipoTitulo") == iTITULO_DE_AUTORIZACION &&
          vDinRep.getInt("iCveClasificacion") == iINICIO_DE_OPERACION) {
        // Se verifica que se encuentre dentro de la vigencia
        if ((vDinRep.getDate("dtIniVigenciaTitulo").getTime() <= dtFecha.getTime()) &&
            (dtFecha.getTime() <= vDinRep.getDate("dtVigenciaTitulo").getTime())) {
            vcSitObligacion.add(new Boolean(true));
        } else {
            vcSitObligacion.add(new Boolean(false));
        }
      }
    } else {
      vcSitObligacion.add(new Boolean(false));
    }

    return vcSitObligacion;
 }
}

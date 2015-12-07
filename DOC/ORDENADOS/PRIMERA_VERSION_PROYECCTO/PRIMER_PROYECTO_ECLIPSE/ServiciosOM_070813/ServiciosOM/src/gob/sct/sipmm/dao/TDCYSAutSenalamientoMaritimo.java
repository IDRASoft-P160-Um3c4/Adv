package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;


/**
 * <p>Title: TDCYSAutSenalamientoMaritimo.java</p>
 * <p>Description: DAO de la entidad CYSAutSenalamientoMaritimo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
public class TDCYSAutSenalamientoMaritimo extends DAOBase{
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
   * <p><b> insert into CYSAutSenalamientoMaritimo(iEjercicio,iMovEdoFinanciero,dtRegistro,dtPresentacion,dtPublicacion,iCveTitulo,cObsEdoFinanciero) values (?,?,?,?,?,?,?) </b></p>
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
      String lSQL = "insert into CYSAutSenalamientoMaritimo(iCveTitulo, " +
                    "iMovAutSenalamiento, " +
                    "dtRegistro, " +
                    "lRequiereSenalamiento, " +
                    "dtPresentacion, " +
                    "dtAutorizacion, " +
                    "cObsSenalamiento, " +
                    "iEjercicioSenalamiento, " +
                    "iConsecutivoSenalamiento) " +
                    " values (?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovAutSenalamiento) AS iMovAutSenalamiento " +
                                      "from CYSAutSenalamientoMaritimo  " +
                                      "where iCveTitulo = " + vData.getInt("iCveTitulo"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovAutSenalamiento",vUltimo.getInt("iMovAutSenalamiento") + 1);
      }else
         vData.put("iMovAutSenalamiento",1);

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovAutSenalamiento"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovAutSenalamiento"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setInt(4,vData.getInt("lRequiereSenalamiento"));
      lPStmt.setDate(5,vData.getDate("dtPresentacion"));
      lPStmt.setDate(6,vData.getDate("dtAutorizacion"));
      lPStmt.setString(7,vData.getString("cObsSenalamiento"));
      lPStmt.setInt(8,vData.getInt("iEjercicioSenalamiento"));
      lPStmt.setInt(9,vData.getInt("iConsecutivoSenalamiento"));
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
   * <p><b> delete from CYSAutSenalamientoMaritimo where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
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
      String lSQL = "delete from CYSAutSenalamientoMaritimo where iCveTitulo = ? AND iMovAutSenalamiento = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovAutSenalamiento"));

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
   * <p><b> update CYSAutSenalamientoMaritimo set dtRegistro=?, dtPresentacion=?, dtPublicacion=?, iCveTitulo=?, cObsEdoFinanciero=? where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
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
      String lSQL = "update CYSAutSenalamientoMaritimo set dtRegistro=?, " +
                    "lRequiereSenalamiento=?, " +
                    "dtPresentacion=?, " +
                    "dtAutorizacion=?, " +
                    "cObsSenalamiento=?, " +
                    "iEjercicioSenalamiento=?, " +
                    "iConsecutivoSenalamiento=? " +
                    "where iCveTitulo=? " +
                    "and iMovAutSenalamiento=? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovAutSenalamiento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setInt(2,vData.getInt("lRequiereSenalamiento"));
      lPStmt.setDate(3,vData.getDate("dtPresentacion"));
      lPStmt.setDate(4,vData.getDate("dtAutorizacion"));
      lPStmt.setString(5,vData.getString("cObsSenalamiento"));
      lPStmt.setInt(6,vData.getInt("iEjercicioSenalamiento"));
      lPStmt.setInt(7,vData.getInt("iConsecutivoSenalamiento"));
      lPStmt.setInt(8,vData.getInt("iCveTitulo"));
      lPStmt.setInt(9,vData.getInt("iMovAutSenalamiento"));
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

     String cSQL = "select cysasm.iCveTitulo, " +
                   "       cysasm.iMovAutSenalamiento, " +
                   "       cysasm.dtRegistro, " +
                   "       cysasm.lRequiereSenalamiento, " +
                   "       cysasm.dtPresentacion, " +
                   "       cysasm.dtAutorizacion, " +
                   "       cysasm.cObsSenalamiento, " +
                   "       cysasm.iEjercicioSenalamiento, " +
                   "       cysasm.iConsecutivoSenalamiento, " +
                   "       rgsda.iEjercicio, " +
                   "       rgsda.iConsecutivoSolicitud, " +
                   "       rgsda.iEjercicioSoltramite, " +
                   "       rgsda.iNumSolTramite, " +
                   "       rgsda.dImporteTotalPagar, " +
                   "       rgsda.dtAutorizacion, " +
                   "       rgsda.iCveUsuAutoriza, " +
                   "       rgsda.dtFinalInspeccion, " +
                   "       rgsda.dtFinalEntraOperacion, " +
                   "       rgsda.lConcluido, " +
                   "       rgsda.iEjercicioObservacion, " +
                   "       rgsda.iCveObservacion, " +
                   "       rgsda.iCveOficina, " +
                   "       rgsda.iCveDepartamento, " +
                   "       rgsda.lExentoPago, " +
                   "       rgsda.cMotivosExentoPago, " +
                   "       trars.tsRegistro " +
                   " from CYSAutSenalamientoMaritimo cysasm " +
                   " inner join RGSDictaminaAutoriza rgsda on cysasm.iEjercicioSenalamiento = rgsda.iEjercicio " +
                   "                                      and cysasm.iConsecutivoSenalamiento = rgsda.iConsecutivoSolicitud " +
                   " left join TRARegSolicitud trars on rgsda.iEjercicio = trars.iEjercicio " +
                   "                                and rgsda.iNumSolTramite = trars.iNumSolicitud " +
                   " where cysasm.iCveTitulo = " + iTitulo +
                   " and cysasm.iConsecutivoSenalamiento = (select max(iConsecutivoSenalamiento) from CYSAutSenalamientoMaritimo where iCveTitulo = " + iTitulo + ")";

    // findByCustom regresa un Vector no null aunque puede estar vacio
    // del query solo voy a obtener un registro
    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();

    if (vcRecords.size() > 0) {
      // Solo hay un registro obtenido
      vDinRep = (TVDinRep)vcRecords.get(0);

      if (vDinRep.getDate("dtAutorizacion") != null) {
        vcSitObligacion.add(new Boolean(true));
      } else {
        vcSitObligacion.add(new Boolean(false));
        vcSitObligacion.add(new Integer(vDinRep.getInt("iNumSolTramite")));
        vcSitObligacion.add(new java.sql.Date(vDinRep.getTimeStamp("tsRegistro").getTime()));
      }
    } else {
      vcSitObligacion.add(new Boolean(false));
    }

    return vcSitObligacion;
 }
}

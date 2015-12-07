package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLFolio.java</p>
 * <p>Description: DAO de la entidad GRLFolio</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
public class TDGRLFolio extends DAOBase{
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
  * Asigna un folio dependiendo a los parámetros enviados
  * @param iCveOficina int      - Contiene la clave de la oficina del usuario que solicitó el folio.
  * @param iCveDepartamento int - Contiene la clave del departamento del usuario que solicitó el folio.
  * @param digitosFolio String  - Contiene el prefijo del folio que corresponde a la oficina y departamento
  * @param iCveUsuario          - Contiene la clave del usuario al cual se le va a asignar el folio
  * @return String              - Contiene el folio asignado.
  */
 public String asignaFolio(int iCveOficina, int iCveDepartamento, String cDigitosFolio, int iCveUsuario){
    TVDinRep vData = new TVDinRep();
    String cFolioAsignado = "";
    int iEjercicio = 0;
    TFechas dtFechaActual = new TFechas();

    java.text.DecimalFormat dfConsecutivo = new java.text.DecimalFormat("000");

    try{
      vData.put("iCveOficina",iCveOficina);
      vData.put("iCveDepartamento",iCveDepartamento);
      vData.put("cDigitosFolio", cDigitosFolio);
      vData.put("iCveUsuAsigna",iCveUsuario);
      vData.put("dtAsignacion", dtFechaActual.TodaySQL() );

      vData.put("cDirigidoA","");
      vData.put("cAsunto","");
      vData.put("cTitularFirma","");
      vData.put("dtEnvio","");
      vData.put("dtRecepcion","");
      vData.put("cNumOficialiaPartes","");
      vData.put("cNumControlGestion","");
      vData.put("dtCancelacion","");
      vData.put("iCveUsuCancela",0);
      vData.put("cMotivoCancela","");

      TVDinRep vFolioAsignado = this.insert(vData,null);

      iEjercicio = vFolioAsignado.getInt("iEjercicio");

      cFolioAsignado = vFolioAsignado.getString("cDigitosFolio") + "." + dfConsecutivo.format(vFolioAsignado.getInt("iConsecutivo")) + "/" + iEjercicio;
    }
    catch(Exception e){ e.printStackTrace(); }

    return cFolioAsignado;
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into GRLFolio(iEjercicio,cDigitosFolio,iConsecutivo,dtAsignacion,iCveUsuAsigna,cDirigidoA,cAsunto,cTitularFirma,dtEnvio,dtRecepcion,cNumOficialiaPartes,cNumControlGestion,dtCancelacion,iCveUsuCancela,cMotivoCancela) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,cDigitosFolio,iConsecutivo, </b></p>
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
          "insert into GRLFolio(iEjercicio,cDigitosFolio,iConsecutivo,dtAsignacion,iCveUsuAsigna,cDirigidoA,cAsunto,cTitularFirma,dtEnvio,dtRecepcion,cNumOficialiaPartes,cNumControlGestion,dtCancelacion,iCveUsuCancela,cMotivoCancela,iCveOficina,iCveDepartamento) values (year(current date),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iConsecutivo) AS iConsecutivo, (year(current date)) as iEjercicio from GRLFolio " +
                                      " WHERE iEjercicio = (year(current date))  AND   iCveOficina = " + vData.getInt("iCveOficina") +
                                      " AND iCveDepartamento = " + vData.getInt("iCveDepartamento") +
                                      " AND cDigitosFolio = '" + vData.getString("cDigitosFolio") + "'"  );

      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iConsecutivo",vUltimo.getInt("iConsecutivo") + 1);
         vData.put("iEjercicio",vUltimo.getInt("iEjercicio"));
      }else
         vData.put("iConsecutivo",1);
      vData.addPK(vData.getString("iEjercicio"));
      //vData.addPK(vData.getString("iCveOficina"));
      //vData.addPK(vData.getString("iCveDepartamento"));
      vData.addPK(vData.getString("cDigitosFolio"));
      vData.addPK(vData.getString("iConsecutivo"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      //lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setString(1,vData.getString("cDigitosFolio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivo"));
      lPStmt.setDate(3,vData.getDate("dtAsignacion"));
      lPStmt.setInt(4,vData.getInt("iCveUsuAsigna"));
      lPStmt.setString(5,vData.getString("cDirigidoA"));
      lPStmt.setString(6,vData.getString("cAsunto"));
      lPStmt.setString(7,vData.getString("cTitularFirma"));
      lPStmt.setDate(8,vData.getDate("dtEnvio"));
      lPStmt.setDate(9,vData.getDate("dtRecepcion"));
      lPStmt.setString(10,vData.getString("cNumOficialiaPartes"));
      lPStmt.setString(11,vData.getString("cNumControlGestion"));
      lPStmt.setDate(12,vData.getDate("dtCancelacion"));
      lPStmt.setInt(13,vData.getInt("iCveUsuCancela"));
      lPStmt.setString(14,vData.getString("cMotivoCancela"));

      lPStmt.setInt(15,vData.getInt("iCveOficina"));
      lPStmt.setInt(16,vData.getInt("iCveDepartamento"));


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
   * <p><b> delete from GRLFolio where iEjercicio = ? AND cDigitosFolio = ? AND iConsecutivo = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,cDigitosFolio,iConsecutivo, </b></p>
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
      String lSQL = "delete from GRLFolio where iEjercicio = ? AND cDigitosFolio = ? AND iConsecutivo = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setString(2,vData.getString("cDigitosFolio"));
      lPStmt.setInt(3,vData.getInt("iConsecutivo"));

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
   * <p><b> update GRLFolio set dtAsignacion=?, iCveUsuAsigna=?, cDirigidoA=?, cAsunto=?, cTitularFirma=?, dtEnvio=?, dtRecepcion=?, cNumOficialiaPartes=?, cNumControlGestion=?, dtCancelacion=?, iCveUsuCancela=?, cMotivoCancela=? where iEjercicio = ? AND cDigitosFolio = ? AND iConsecutivo = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,cDigitosFolio,iConsecutivo, </b></p>
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
      String lSQL = "update GRLFolio set dtAsignacion=?, iCveUsuAsigna=?, cDirigidoA=?, cAsunto=?, cTitularFirma=?, dtEnvio=?, dtRecepcion=?, cNumOficialiaPartes=?, cNumControlGestion=?, dtCancelacion=?, iCveUsuCancela=?, cMotivoCancela=?, iCveOficina=?, iCveDepartamento=?  where iEjercicio = ? AND cDigitosFolio = ? AND iConsecutivo = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("cDigitosFolio"));
      vData.addPK(vData.getString("iConsecutivo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtAsignacion"));
      lPStmt.setInt(2,vData.getInt("iCveUsuAsigna"));
      lPStmt.setString(3,vData.getString("cDirigidoA"));
      lPStmt.setString(4,vData.getString("cAsunto"));
      lPStmt.setString(5,vData.getString("cTitularFirma"));
      lPStmt.setDate(6,vData.getDate("dtEnvio"));
      lPStmt.setDate(7,vData.getDate("dtRecepcion"));
      lPStmt.setString(8,vData.getString("cNumOficialiaPartes"));
      lPStmt.setString(9,vData.getString("cNumControlGestion"));
      lPStmt.setDate(10,vData.getDate("dtCancelacion"));
      lPStmt.setInt(11,vData.getInt("iCveUsuCancela"));
      lPStmt.setString(12,vData.getString("cMotivoCancela"));

      lPStmt.setInt(13,vData.getInt("iCveOficina"));
      lPStmt.setInt(14,vData.getInt("iCveDepartamento"));

      lPStmt.setInt(15,vData.getInt("iEjercicio"));
      lPStmt.setString(16,vData.getString("cDigitosFolio"));
      lPStmt.setInt(17,vData.getInt("iConsecutivo"));
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

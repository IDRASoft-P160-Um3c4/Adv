package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRAOpnEntTramite.java</p>
 * <p>Description: DAO de la entidad TRAOpnEntTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
public class TDTRAOpnEntTramite extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  TVDinRep vLlaves = new TVDinRep();
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
   * <p><b> insert into TRAOpnEntTramite(iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad,cOpnDirigidoA,cPtoOpinion) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    //System.out.print("...DAO 1..");
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    PreparedStatement lPStmt1 = null;
    PreparedStatement lPStmt2 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
//Inserta datos en GRLSegtoEntidad

      String lSQL =
          "insert into GRLSegtoEntidad(iCveSegtoEntidad,lEsContestacion,cObsesSegto,cOficio) values (?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveSegtoEntidad) AS iCveSegtoEntidad from GRLSegtoEntidad");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveSegtoEntidad",vUltimo.getInt("iCveSegtoEntidad") + 1);
      }else
         vData.put("iCveSegtoEntidad",1);


      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveSegtoEntidad"));
      lPStmt.setInt(2,vData.getInt("lEsContestacion"));
      lPStmt.setString(3,vData.getString("cObsesSegto"));
      lPStmt.setString(4,vData.getString("cOficio"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

//Insertar Registro en GRLFolioXSegtoEnt
      //System.out.print("...DAO 2..");
      String lSQL2 =
          "insert into GRLFolioXSegtoEnt(iCveSegtoEntidad,iConsecutivoSegtoRef) values (?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData1 = findByCustom("","select MAX(iConsecutivoSegtoRef) AS iConsecutivoSegtoRef from GRLFolioXSegtoEnt where iCveSegtoEntidad = " + vData.getInt("iCveSegtoEntidad"));
      if(vcData1.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData1.get(0);
         vData.put("iConsecutivoSegtoRef",vUltimo.getInt("iConsecutivoSegtoRef") + 1);
      }else
         vData.put("iConsecutivoSegtoRef",1);
      //...

      lPStmt2 = conn.prepareStatement(lSQL2);
      lPStmt2.setInt(1,vData.getInt("iCveSegtoEntidad"));
      lPStmt2.setInt(2,vData.getInt("iConsecutivoSegtoRef"));
      lPStmt2.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
      //System.out.print("...DAO 3..");
// Insertar registro en TRAOpnEntTRamite
      String lSQL1 =
            "insert into TRAOpnEntTramite(iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad) values (?,?,?,?)";

         vData.addPK(vData.getString("iEjercicioSolicitud"));
         vData.addPK(vData.getString("iNumSolicitud"));
         vData.addPK(vData.getString("hdCveOpinionEntidad"));
         vData.addPK(vData.getString("iCveSegtoEntidad"));

            lPStmt1 = conn.prepareStatement(lSQL1);
            lPStmt1.setInt(1,vData.getInt("iEjercicioSolicitud"));
            lPStmt1.setInt(2,vData.getInt("iNumSolicitud"));
            lPStmt1.setInt(3,vData.getInt("hdCveOpinionEntidad"));
            lPStmt1.setInt(4,vData.getInt("iCveSegtoEntidad"));
            lPStmt1.executeUpdate();
            if(cnNested == null){
              conn.commit();
            }

      //System.out.print("...DAO 4..");
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
        if(lPStmt1 != null){
                  lPStmt1.close();
        }
        if(lPStmt2 != null){
          lPStmt2.close();
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
   * <p><b> delete from TRAOpnEntTramite where iEjercicioSolicitud = ? AND iNumSolicitud = ? AND iCveOpinionEntidad = ? AND iCveSegtoEntidad = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad, </b></p>
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
      String lSQL = "delete from TRAOpnEntTramite where iEjercicioSolicitud = ? AND iNumSolicitud = ? AND iCveOpinionEntidad = ? AND iCveSegtoEntidad = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioSolicitud"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveOpinionEntidad"));
      lPStmt.setInt(4,vData.getInt("iCveSegtoEntidad"));

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
   * <p><b> update TRAOpnEntTramite set cOpnDirigidoA=?, cPtoOpinion=? where iEjercicioSolicitud = ? AND iNumSolicitud = ? AND iCveOpinionEntidad = ? AND iCveSegtoEntidad = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad, </b></p>
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
      String lSQL = "update GRLSegtoEntidad set cObsesSegto=?,cOficio=? where iCveSegtoEntidad = ? ";


      vData.addPK(vData.getString("iEjercicioSolicitud"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("hdCveOpinionEntidad"));
      vData.addPK(vData.getString("iCveSegtoEntidad"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cObsesSegto"));
      lPStmt.setString(2,vData.getString("cOficio"));
      lPStmt.setInt(3,vData.getInt("iCveSegtoEntidad"));
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

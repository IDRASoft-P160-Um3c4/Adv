package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLSegtoEntidad.java</p>
 * <p>Description: DAO de la entidad GRLSegtoEntidad</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
public class TDGRLSegtoEntidad extends DAOBase{
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
   * <p><b> insert into GRLSegtoEntidad(iCveSegtoEntidad,lEsContestacion,cDirigidoACon,cAsuntoCon,cTitularFirmaCon,dtOficioCon,dtRecepcionCon,cNumOficialiaPartesCon,cNumOficioCon,cObsesSegto) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveSegtoEntidad, </b></p>
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
          "insert into GRLSegtoEntidad(iCveSegtoEntidad,lEsContestacion,cDirigidoACon,cAsuntoCon,cTitularFirmaCon,dtOficioCon,dtRecepcionCon,cNumOficialiaPartesCon,cNumOficioCon,cObsesSegto,cSiglas,cOficio) values (?,?,?,?,?,?,?,?,?,?,?,?)";
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
      lPStmt.setString(3,vData.getString("cDirigidoACon"));
      lPStmt.setString(4,vData.getString("cAsuntoCon"));
      lPStmt.setString(5,vData.getString("cTitularFirmaCon"));
      lPStmt.setDate(6,vData.getDate("dtOficioCon"));
      lPStmt.setDate(7,vData.getDate("dtRecepcionCon"));
      lPStmt.setString(8,vData.getString("cNumOficialiaPartesCon"));
      lPStmt.setString(9,vData.getString("cNumOficioCon"));
      lPStmt.setString(10,vData.getString("cObsesSegto"));
      lPStmt.setString(11,vData.getString("cSiglas"));
      lPStmt.setString(12,vData.getString("cOficio"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

//Insertar Registro en GRLFolioXSegtoEnt

      String lSQL2 =
          "insert into GRLFolioXSegtoEnt(iCveSegtoEntidad,iConsecutivoSegtoRef) values (?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData1 = findByCustom("","select MAX(iConsecutivoSegtoRef) AS iConsecutivoSegtoRef from GRLFolioXSegtoEnt where iCveSegtoEntidad = " + vData.getInt("iCveSegtoEntidad"));
      if(vcData1.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData1.get(0);
         vData.put("iConsecutivoSegtoRef",vUltimo.getInt("iConsecutivoSegtoRef") + 1);
      }else
         vData.put("iConsecutivoSegtoRef",1);

      lPStmt2 = conn.prepareStatement(lSQL2);
      lPStmt2.setInt(1,vData.getInt("iCveSegtoEntidad"));
      lPStmt2.setInt(2,vData.getInt("iConsecutivoSegtoRef"));
      lPStmt2.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

// Insertar registro en TRAOpnEntTRamite
      String lSQL1 =
            "insert into TRAOpnEntTramite(iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad,cOpnDirigidoA,cPtoOpinion) values (?,?,?,?,?,?)";

        vData.addPK(vData.getString("iEjercicioSolicitud"));
        vData.addPK(vData.getString("iNumSolicitud"));
        vData.addPK(vData.getString("iCveOpinionEntidad"));
        vData.addPK(vData.getString("iCveSegtoEntidad"));

            lPStmt1 = conn.prepareStatement(lSQL1);
            lPStmt1.setInt(1,vData.getInt("iEjercicioSolicitud"));
            lPStmt1.setInt(2,vData.getInt("iNumSolicitud"));
            lPStmt1.setInt(3,vData.getInt("iCveOpinionEntidad"));
            lPStmt1.setInt(4,vData.getInt("iCveSegtoEntidad"));
            lPStmt1.setString(5,vData.getString("cOpnDirigidoA"));
            lPStmt1.setString(6,vData.getString("cPtoOpinion"));
            lPStmt1.executeUpdate();
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
   * <p><b> delete from GRLSegtoEntidad where iCveSegtoEntidad = ?  </b></p>
   * <p><b> Campos Llave: iCveSegtoEntidad, </b></p>
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
      String lSQL = "delete from GRLSegtoEntidad where iCveSegtoEntidad = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveSegtoEntidad"));

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
   * <p><b> update GRLSegtoEntidad set lEsContestacion=?, cDirigidoACon=?, cAsuntoCon=?, cTitularFirmaCon=?, dtOficioCon=?, dtRecepcionCon=?, cNumOficialiaPartesCon=?, cNumOficioCon=?, cObsesSegto=? where iCveSegtoEntidad = ?  </b></p>
   * <p><b> Campos Llave: iCveSegtoEntidad, </b></p>
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
    PreparedStatement lPStmt1= null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

//Update TRAOpnEntTramite
      String lSQL1 = "update TRAOpnEntTramite set cOpnDirigidoA=?, cPtoOpinion=? where iEjercicioSolicitud = ? AND iNumSolicitud = ? AND iCveOpinionEntidad = ? AND iCveSegtoEntidad = ? ";

      vData.addPK(vData.getString("iEjercicioSolicitud"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iCveOpinionEntidad"));
      vData.addPK(vData.getString("iCveSegtoEntidad"));

      lPStmt1 = conn.prepareStatement(lSQL1);
      lPStmt1.setString(1,vData.getString("cOpnDirigidoA"));
      lPStmt1.setString(2,vData.getString("cPtoOpinion"));
      lPStmt1.setInt(3,vData.getInt("iEjercicioSolicitud"));
      lPStmt1.setInt(4,vData.getInt("iNumSolicitud"));
      lPStmt1.setInt(5,vData.getInt("iCveOpinionEntidad"));
      lPStmt1.setInt(6,vData.getInt("iCveSegtoEntidad"));
      lPStmt1.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }


    //Update GRLSegtoEntidad

      String lSQL = "update GRLSegtoEntidad set lEsContestacion=?, cDirigidoACon=?, cAsuntoCon=?, cTitularFirmaCon=?, dtOficioCon=?, dtRecepcionCon=?, cNumOficialiaPartesCon=?, cNumOficioCon=?, cObsesSegto=?, cSiglas=?,cOficio=? where iCveSegtoEntidad = ? ";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lEsContestacion"));
      lPStmt.setString(2,vData.getString("cDirigidoACon"));
      lPStmt.setString(3,vData.getString("cAsuntoCon"));
      lPStmt.setString(4,vData.getString("cTitularFirmaCon"));
      lPStmt.setDate(5,vData.getDate("dtOficioCon"));
      lPStmt.setDate(6,vData.getDate("dtRecepcionCon"));
      lPStmt.setString(7,vData.getString("cNumOficialiaPartesCon"));
      lPStmt.setString(8,vData.getString("cNumOficioCon"));
      lPStmt.setString(9,vData.getString("cObsesSegto"));
      lPStmt.setString(10,vData.getString("cSiglas"));
      lPStmt.setString(11,vData.getString("cOficio"));
      lPStmt.setInt(12,vData.getInt("iCveSegtoEntidad"));
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
        if(lPStmt1 != null){
          lPStmt1.close();
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

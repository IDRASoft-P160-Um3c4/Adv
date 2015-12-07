package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDINSProgInsp.java</p>
 * <p>Description: DAO de la entidad INSProgInsp</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author cabrito
 * @version 1.0
 */
public class TDINSProgInsp extends DAOBase{
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
   * <p><b> insert into INSProgInsp(iCveInspProg,iNumSolicitud,iCveInspector,iCveTipoInsp,iTipoCertificado,iCveEmbarcacion,iCvePuerto,iCveOficina,tsRegistro,tsInsp,iEjercicio,lCancelada,cMotivoCancelacion,dtCancelacion) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveInspProg, </b></p>
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
//        conn.setAutoCommit(false);
//        conn.setTransactionIsolation(2);
      }
      String lSQL =
          " insert into INSProgInsp(iCveInspProg,iNumSolicitud,iCveInspector,iCveTipoInsp, "+
          " iTipoCertificado,iCveEmbarcacion,iCvePuerto,iCveOficina,dtRegistro,dtInspeccion,iEjercicio, "+
          " lCancelada,cMotivoCancelacion,dtCancelacion) "+
          " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveInspProg) AS iCveInspProg from INSProgInsp");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveInspProg",vUltimo.getInt("iCveInspProg") + 1);
      }else
         vData.put("iCveInspProg",1);
      vData.addPK(vData.getString("iCveInspProg"));
      //...
      TFechas fecha = new TFechas();
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveInspProg"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveInspector"));
      lPStmt.setInt(4,vData.getInt("iCveTipoInsp"));
      lPStmt.setInt(5,vData.getInt("iTipoCertificado"));
      lPStmt.setInt(6,vData.getInt("iCveEmbarcacion"));
      lPStmt.setInt(7,vData.getInt("iCvePuerto"));
      lPStmt.setInt(8,vData.getInt("iCveOficina"));
      lPStmt.setTimestamp(9, fecha.getThisTime());
      lPStmt.setTimestamp(10,vData.getTimeStamp("tsInsp"));
      lPStmt.setInt(11,vData.getInt("iEjercicio"));
      lPStmt.setInt(12,vData.getInt("lCancelada"));
      lPStmt.setString(13,vData.getString("cMotivoCancelacion"));
      lPStmt.setDate(14,vData.getDate("dtCancelacion"));
      lPStmt.executeUpdate();
    } catch(Exception ex){
      ex.printStackTrace();
      warn("insert",ex);
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
   * <p><b> delete from INSProgInsp where iCveInspProg = ?  </b></p>
   * <p><b> Campos Llave: iCveInspProg, </b></p>
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
      String lSQL = "delete from INSProgInsp where iCveInspProg = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveInspProg"));

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
   * <p><b> update INSProgInsp set iNumSolicitud=?, iCveInspector=?, iCveTipoInsp=?, iTipoCertificado=?, iCveEmbarcacion=?, iCvePuerto=?, iCveOficina=?, tsRegistro=?, tsInsp=?, iEjercicio=?, lCancelada=?, cMotivoCancelacion=?, dtCancelacion=? where iCveInspProg = ?  </b></p>
   * <p><b> Campos Llave: iCveInspProg, </b></p>
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
      String lSQL = "update INSProgInsp set iNumSolicitud=?, iCveInspector=?, iCveTipoInsp=?, iTipoCertificado=?, iCveEmbarcacion=?, iCvePuerto=?, iCveOficina=?, tsInsp=?, iEjercicio=?, lCancelada=?, cMotivoCancelacion=?, dtCancelacion=? where iCveInspProg = ? ";

      vData.addPK(vData.getString("iCveInspProg"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(2,vData.getInt("iCveInspector"));
      lPStmt.setInt(3,vData.getInt("iCveTipoInsp"));
      lPStmt.setInt(4,vData.getInt("iTipoCertificado"));
      lPStmt.setInt(5,vData.getInt("iCveEmbarcacion"));
      lPStmt.setInt(6,vData.getInt("iCvePuerto"));
      lPStmt.setInt(7,vData.getInt("iCveOficina"));
//      lPStmt.setString(8,vData.getString("tsRegistro"));
      lPStmt.setTimestamp(8,vData.getTimeStamp("tsInsp"));
      lPStmt.setInt(9,vData.getInt("iEjercicio"));
      lPStmt.setInt(10,vData.getInt("lCancelada"));
      lPStmt.setString(11,vData.getString("cMotivoCancelacion"));
      lPStmt.setDate(12,vData.getDate("dtCancelacion"));
      lPStmt.setInt(13,vData.getInt("iCveInspProg"));
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
 public TVDinRep insertNuevo(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
     }
     String lSQL =
         " insert into INSProgInsp(iCveInspProg,iNumSolicitud,iCveInspector,iCveTipoInsp, "+
         " iTipoCertificado,iCveEmbarcacion,iCvePuerto,iCveOficina,iEjercicio,larqMatriculda,ICVEGRUPOCERTIF) "+
         " values (?,?,?,?,?,?,?,?,?,1,?)";

     //AGREGAR AL ULTIMO ...
     Vector vcData = findByCustom("","select MAX(iCveInspProg) AS iCveInspProg from INSProgInsp");
     if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iCveInspProg",vUltimo.getInt("iCveInspProg") + 1);
     }else
        vData.put("iCveInspProg",1);
     vData.addPK(vData.getString("iCveInspProg"));
     //...
     TFechas fecha = new TFechas();
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveInspProg"));
     lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
     lPStmt.setInt(3,vData.getInt("iCveInspector"));
     lPStmt.setInt(4,vData.getInt("iCveTipoInsp"));
     lPStmt.setInt(5,vData.getInt("iTipoCertificado"));
     lPStmt.setInt(6,vData.getInt("iCveEmbarcacion"));
     lPStmt.setInt(7,vData.getInt("iCvePuerto"));
     lPStmt.setInt(8,vData.getInt("iCveOficina"));
     lPStmt.setInt(9,vData.getInt("iEjercicio"));
     lPStmt.setInt(10,vData.getInt("iCveGrupoCert"));

     lPStmt.executeUpdate();
   } catch(Exception ex){
     ex.printStackTrace();
     warn("insert",ex);
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
}

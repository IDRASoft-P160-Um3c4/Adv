package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDSOCSupervisionInmueble.java</p>
 * <p>Description: DAO de la entidad SOCSupervisionInmueble</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
public class TDSOCSupervisionInmueble extends DAOBase{
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
   * <p><b> insert into SOCSupervisionInmueble(iEjercicioSupervision,iCveSolicitudSupervision,iCveOficina,iCveInmueble,lInmuebleEvaluado,lCondicionEvaluada,lPagoEvaluado,iNumHabitantes,cParentescoFuncionario,cOtrosPagosServ,iEjercicioObservacion,iCveObservacion) values (?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioSupervision,iCveSolicitudSupervision,iCveOficina,iCveInmueble, </b></p>
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
          "insert into SOCSupervisionInmueble(iEjercicioSupervision,iCveSolicitudSupervision,iCveOficina,iCveInmueble,lInmuebleEvaluado,lCondicionEvaluada,lPagoEvaluado,iNumHabitantes,cParentescoFuncionario,cOtrosPagosServ,iEjercicioObservacion,iCveObservacion) values (?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
     /* Vector vcData = findByCustom("","select MAX(iEjercicioSupervision) AS iEjercicioSupervision from SOCSupervisionInmueble");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iEjercicioSupervision",vUltimo.getInt("iEjercicioSupervision") + 1);
      }else
         vData.put("iEjercicioSupervision",1);
      */vData.addPK(vData.getString("iEjercicioSupervision"));
     vData.addPK(vData.getString("iCveSolicitudSupervision"));
     vData.addPK(vData.getString("iCveOficina"));
     vData.addPK(vData.getString("iCveInmueble"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioSupervision"));
      lPStmt.setInt(2,vData.getInt("iCveSolicitudSupervision"));
      lPStmt.setInt(3,vData.getInt("iCveOficina"));
      lPStmt.setInt(4,vData.getInt("iCveInmueble"));
      System.out.print("vData.getInt(lInmuebleEvaluado): "+vData.getInt("lInmuebleEvaluado"));
      lPStmt.setInt(5,vData.getInt("lInmuebleEvaluado"));
      lPStmt.setInt(6,vData.getInt("lCondicionEvaluada"));
      lPStmt.setInt(7,vData.getInt("lPagoEvaluado"));
      lPStmt.setInt(8,vData.getInt("iNumHabitantes"));
      lPStmt.setString(9,vData.getString("cParentescoFuncionario"));
    // System.out.print("vl"+vData.getString("cOtrosPagosServ"));
    //  lPStmt.setString(9,vData.getString("cParentescoFuncionario"));
      if(vData.getString("cOtrosPagosServ")!=null)
          lPStmt.setString(10,vData.getString("cOtrosPagosServ"));
      else
              lPStmt.setString(10,null);
      lPStmt.setInt(11,vData.getInt("iEjercicioObservacion"));
      lPStmt.setInt(12,vData.getInt("iCveObservacion"));
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
   * <p><b> delete from SOCSupervisionInmueble where iEjercicioSupervision = ? AND iCveSolicitudSupervision = ? AND iCveOficina = ? AND iCveInmueble = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSupervision,iCveSolicitudSupervision,iCveOficina,iCveInmueble, </b></p>
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
      String lSQL = "delete from SOCSupervisionInmueble where iEjercicioSupervision = ? AND iCveSolicitudSupervision = ? AND iCveOficina = ? AND iCveInmueble = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioSupervision"));
      lPStmt.setInt(2,vData.getInt("iCveSolicitudSupervision"));
      lPStmt.setInt(3,vData.getInt("iCveOficina"));
      lPStmt.setInt(4,vData.getInt("iCveInmueble"));

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
   * <p><b> update SOCSupervisionInmueble set lInmuebleEvaluado=?, lCondicionEvaluada=?, lPagoEvaluado=?, iNumHabitantes=?, cParentescoFuncionario=?, cOtrosPagosServ=?, iEjercicioObservacion=?, iCveObservacion=? where iEjercicioSupervision = ? AND iCveSolicitudSupervision = ? AND iCveOficina = ? AND iCveInmueble = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSupervision,iCveSolicitudSupervision,iCveOficina,iCveInmueble, </b></p>
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
    PreparedStatement lPStmt2 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update SOCSupervisionInmueble set  "+
          "iNumHabitantes=?, "+
          "cParentescoFuncionario=?, "+
          "iEjercicioObservacion=year(current date), "+
          "iCveObservacion=?, "+
          "lInmuebleEvaluado = ?,  "+
          "cNomFuncionario = ?, "+
          "cCargoFuncionario = ?, "+
          "dtRecepcionInmueble = ?, "+
          "dtQueHabitaron = ?, "+
          "cNumAlojamientos = ?, "+
          "lTieneGarage = ?, " +
          "lTieneCisterna = ?, "+
          "lTieneTinaco = ?, " +
          "lTieneBodega = ?, "+
          "lTieneJardin = ?, "+
          "cConstruidaEn = ?, "+
          "iNumPisos = ? " +
          "where iEjercicioSupervision = ? AND iCveSolicitudSupervision = ? AND iCveOficina = ? AND iCveInmueble = ? ";

      String lSQL2 = "update SOCCondicionXSupervision set iCveNivel=? where iEjercicioSupervision = ? AND iCveSolicitudSupervision = ? AND iCveOficina = ? AND iCveInmueble = ? AND iCveCondicion = ? ";

      vData.addPK(vData.getString("iEjercicioSupervision"));
      vData.addPK(vData.getString("iCveSolicitudSupervision"));
      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iCveInmueble"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iNumHabitantes"));
      lPStmt.setString(2,vData.getString("cParentescoFuncionario"));
      lPStmt.setInt(3,vData.getInt("iCveObservacion"));
      lPStmt.setInt(4,vData.getInt("lInmuebleEvaluado"));
      lPStmt.setString(5,vData.getString("cNomFuncionario"));
      lPStmt.setString(6,vData.getString("cCargoFuncionario"));
      lPStmt.setDate(7,vData.getDate("dtRecepcionInmueble"));
      lPStmt.setDate(8,vData.getDate("dtQueHabitaron"));
      lPStmt.setString(9,vData.getString("cNumAlojamientos"));
      lPStmt.setInt(10,vData.getInt("lTieneGarage"));
      lPStmt.setInt(11,vData.getInt("lTieneCisterna"));
      lPStmt.setInt(12,vData.getInt("lTieneTinaco"));
      lPStmt.setInt(13,vData.getInt("lTieneBodega"));
      lPStmt.setInt(14,vData.getInt("lTieneJardin"));
      lPStmt.setString(15,vData.getString("cConstruidaEn"));
      lPStmt.setInt(16,vData.getInt("iNumPisos"));

      lPStmt.setInt(17,vData.getInt("iEjercicioSupervision"));
      lPStmt.setInt(18,vData.getInt("iCveSolicitudSupervision"));
      lPStmt.setInt(19,vData.getInt("iCveOficina"));
      lPStmt.setInt(20,vData.getInt("iCveInmueble"));
      lPStmt.executeUpdate();

      if (vData.getInt("iCveCondicion") != 0 && vData.getInt("iCveNivel")!= 0){
       //vData.addPK(vData.getString("iCveCondicion"));
       lPStmt2 = conn.prepareStatement(lSQL2);
       lPStmt2.setInt(1,vData.getInt("iCveNivel"));
       lPStmt2.setInt(2,vData.getInt("iEjercicioSupervision"));
       lPStmt2.setInt(3,vData.getInt("iCveSolicitudSupervision"));
       lPStmt2.setInt(4,vData.getInt("iCveOficina"));
       lPStmt2.setInt(5,vData.getInt("iCveInmueble"));
       lPStmt2.setInt(6,vData.getInt("iCveCondicion"));
       lPStmt2.executeUpdate();
     }


     // vData=new TDGRLObservacion().update(vData,conn);

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
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");

      return vData;
    }
 }
}

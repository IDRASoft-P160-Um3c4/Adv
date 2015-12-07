package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCYSCompromisosCalidad.java</p>
 * <p>Description: DAO de la entidad CYSCompromisosCalidad</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSCompromisosCalidad extends DAOBase{
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
   * <p><b> insert into CYSCompromisosCalidad(iEjercicio,iMovComCalidad,dtRegistro,dtPresentacion,iCveTitulo,cObsCompCalidad) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovComCalidad, </b></p>
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
      String lSQL = "insert into CYSCompromisosCalidad(iEjercicio,iMovComCalidad,dtRegistro,dtPresentacion,iCveTitulo,cObsCompCalidad) " +
                    "values (?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovComCalidad) AS iMovComCalidad from CYSCompromisosCalidad");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovComCalidad",vUltimo.getInt("iMovComCalidad") + 1);
      }else
         vData.put("iMovComCalidad",1);
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovComCalidad"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovComCalidad"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setInt(5,vData.getInt("iCveTitulo"));
      lPStmt.setString(6,vData.getString("cObsCompCalidad"));
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
   * <p><b> delete from CYSCompromisosCalidad where iEjercicio = ? AND iMovComCalidad = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovComCalidad, </b></p>
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
      String lSQL = "delete from CYSCompromisosCalidad where iEjercicio = ? AND iMovComCalidad = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovComCalidad"));

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
   * <p><b> update CYSCompromisosCalidad set dtRegistro=?, dtPresentacion=?, iCveTitulo=?, cObsCompCalidad=? where iEjercicio = ? AND iMovComCalidad = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovComCalidad, </b></p>
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
      String lSQL = "update CYSCompromisosCalidad set dtRegistro=?, dtPresentacion=?, iCveTitulo=?, cObsCompCalidad=? where iEjercicio = ? AND iMovComCalidad = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovComCalidad"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setInt(3,vData.getInt("iCveTitulo"));
      lPStmt.setString(4,vData.getString("cObsCompCalidad"));
      lPStmt.setInt(5,vData.getInt("iEjercicio"));
      lPStmt.setInt(6,vData.getInt("iMovComCalidad"));
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
   TFechas tFechas = new TFechas();

   int iEjercicio = tFechas.getIntYear(dtFecha);

     String cSQL = "select iEjercicio, " +
                   "       iMovComCalidad, " +
                   "       dtRegistro, " +
                   "       dtPresentacion, " +
                   "       iCveTitulo, " +
                   "       cObsCompCalidad " +
                   " from CYSCompromisosCalidad " +
                   " where iCveTitulo = " + iTitulo +
                   " and iEjercicio = " + iEjercicio +
                   " and iMovComCalidad = (select max(iMovComCalidad) from CYSCompromisosCalidad where iCveTitulo = " + iTitulo + ")";

    // findByCustom regresa un Vector no null aunque puede estar vacio
    // del query solo voy a obtener un registro
    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();

    if (vcRecords.size() > 0) {
      // Solo hay un registro obtenido
      vDinRep = (TVDinRep)vcRecords.get(0);

      if (vDinRep.getDate("dtPresentacion") != null) {
        vcSitObligacion.add(new Boolean(true));
      } else {
        vcSitObligacion.add(new Boolean(false));
      }
    } else {
        vcSitObligacion.add(new Boolean(false));
    }

    return vcSitObligacion;
 }
}

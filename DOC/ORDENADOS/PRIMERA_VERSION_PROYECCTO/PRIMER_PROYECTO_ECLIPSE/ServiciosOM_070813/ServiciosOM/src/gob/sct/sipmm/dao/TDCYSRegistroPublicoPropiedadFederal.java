
package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDCYSRegistroPublicoPropiedadFederal.java</p>
 * <p>Description: DAO de la entidad CYSRegistroPublicoPropiedadFederal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSRegistroPublicoPropiedadFederal extends DAOBase{
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
   * <p><b> insert into CYSRegistroPublicoPropiedadFederal(iCveTitulo,iMovRegPubProFed,dtRegistro,dtPresentacion,cObsRegPubProFed) values (?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTitulo,iMovRegPubProFed, </b></p>
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
          "insert into CYSRegistroPublicoPropiedadFederal(iCveTitulo,iMovRegPubProFed,dtRegistro,dtPresentacion,cObsRegPubProFed," +
          "cNumRPPF,cNumEscrito,dtEscrito,dtRPPF) values (?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovRegPubProFed) AS iMovRegPubProFed from CYSRegistroPublicoPropiedadFederal");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovRegPubProFed",vUltimo.getInt("iMovRegPubProFed") + 1);
      }else
         vData.put("iMovRegPubProFed",1);

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovRegPubProFed"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovRegPubProFed"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setString(5,vData.getString("cObsRegPubProFed"));
      lPStmt.setString(6,vData.getString("cNumRPPF"));
      lPStmt.setString(7,vData.getString("cNumEscrito"));
      lPStmt.setDate(8,vData.getDate("dtEscrito"));
      lPStmt.setDate(9,vData.getDate("dtRPPF"));
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
   * <p><b> delete from CYSRegistroPublicoPropiedadFederal where iCveTitulo = ? AND iMovRegPubProFed = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iMovRegPubProFed, </b></p>
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
      String lSQL = "delete from CYSRegistroPublicoPropiedadFederal where iCveTitulo = ? AND iMovRegPubProFed = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovRegPubProFed"));

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
   * <p><b> update CYSRegistroPublicoPropiedadFederal set dtRegistro=?, dtPresentacion=?, cObsRegPubProFed=? where iCveTitulo = ? AND iMovRegPubProFed = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iMovRegPubProFed, </b></p>
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
      String lSQL = "update CYSRegistroPublicoPropiedadFederal set dtRegistro=?, dtPresentacion=?, cObsRegPubProFed=?, " +
                    "cNumRPPF=?, cNumEscrito=?, dtEscrito=?, dtRPPF=? where iCveTitulo = ? AND iMovRegPubProFed = ? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovRegPubProFed"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setString(3,vData.getString("cObsRegPubProFed"));
      lPStmt.setString(4,vData.getString("cNumRPPF"));
      lPStmt.setString(5,vData.getString("cNumEscrito"));
      lPStmt.setDate(6,vData.getDate("dtEscrito"));
      lPStmt.setDate(7,vData.getDate("dtRPPF"));
      lPStmt.setInt(8,vData.getInt("iCveTitulo"));
      lPStmt.setInt(9,vData.getInt("iMovRegPubProFed"));
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
  * Agregado por Rafael Alcocer Caldera 29/Nov/2006
  *
  * @param cKey String
  * @param iTitulo int
  * @param dtFecha Date
  * @throws DAOException
  * @return Vector
  */
 public Vector findBySitObligacion(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
   Vector vcSitObligacion = new Vector();

     String cSQL = "select iCveTitulo, " +
                   "       iMovRegPubProFed, " +
                   "       dtRegistro, " +
                   "       dtPresentacion, " +
                   "       cObsRegPubProFed, " +
                   "       cNumRPPF, " +
                   "       cNumEscrito, " +
                   "       dtEscrito, " +
                   "       dtRPPF " +
                   " from CYSRegistroPublicoPropiedadFederal " +
                   " where iCveTitulo = " + iTitulo +
                   " and iMovRegPubProFed = (select max(iMovRegPubProFed) from CYSRegistroPublicoPropiedadFederal where iCveTitulo = " + iTitulo + ")";

    // findByCustom regresa un Vector no null aunque puede estar vacio
    // del query solo voy a obtener un registro
    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();

    if (vcRecords.size() > 0) {
      // Solo hay un registro obtenido
      vDinRep = (TVDinRep)vcRecords.get(0);

      if (vDinRep.getDate("dtPresentacion") != null) {
        if (!vDinRep.getString("cNumRPPF").equalsIgnoreCase("")) {
          vcSitObligacion.add(new Boolean(true));
        } else {
          vcSitObligacion.add(new Boolean(false));
        }
      } else {
        vcSitObligacion.add(new Boolean(false));
      }
    } else
        vcSitObligacion.add(new Boolean(false));

    return vcSitObligacion;
 }
}

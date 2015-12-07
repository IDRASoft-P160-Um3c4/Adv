package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.*;


/*
page import="com.micper.util.logging.*" %>
page import="com.micper.util.*" %>
page import="com.micper.seguridad.vo.*" %>
page import="com.micper.seguridad.dao.*" %>
page import="gob.sct.sipmm.dao.*" %>
page import="com.micper.sql.*" %>
*/

/**
 * <p>Title: TDGRLDocumento.java</p>
 * <p>Description: DAO de la entidad GRLDocumento</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author cabrito
 * @version 1.0
 */
public class TDGRLDocumento extends DAOBase{
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
   * <p><b> insert into GRLDocumento(iEjercicio,iCveTipoDocumento,iIdDocumento,iIdGestorDocumento,iCveUsuario,dtRegistro,cDscDocumento,cObses) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveTipoDocumento,iIdDocumento, </b></p>
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
      String lSQL = " insert into GRLDocumento(iEjercicio, " +
                    " iCveTipoDocumento, " +
                    " iIdDocumento, " +
                    " iIdGestorDocumento, " +
                    " iCveUsuario, " +
                    " dtRegistro, " +
                    " cDscDocumento, " +
                    " cObses, " +
                    " cNomArchivo, " +
                    " cExtensionArchivo,"   +
                    " cMimeType ) " +
                    " values (?,?,?,?,?,(CURRENT DATE),?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("",
                      " select MAX(iIdDocumento) AS iIdDocumento " +
                      " from GRLDocumento " +
                      " where iEjercicio = " + vData.getInt("iEjercicio") +
                      " and iCveTipoDocumento = " + vData.getInt("iCveTipoDocumento"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iIdDocumento",vUltimo.getInt("iIdDocumento") + 1);
      }else
         vData.put("iIdDocumento",1);

       vData.addPK(vData.getString("iEjercicio"));
       vData.addPK(vData.getString("iCveTipoDocumento"));
       vData.addPK(vData.getString("iIdDocumento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveTipoDocumento"));
      lPStmt.setInt(3,vData.getInt("iIdDocumento"));
      lPStmt.setInt(4,vData.getInt("iIdGestorDocumento"));
      lPStmt.setInt(5,vData.getInt("iCveUsuario"));
      lPStmt.setString(6,vData.getString("cDscDocumento"));
      lPStmt.setString(7,vData.getString("cObses"));
      lPStmt.setString(8,vData.getString("cNomArchivo"));
      lPStmt.setString(9,vData.getString("cExtensionArchivo"));
      lPStmt.setString(10,vData.getString("cMimeType"));
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      ex.printStackTrace();
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
   * <p><b> delete from GRLDocumento where iEjercicio = ? AND iCveTipoDocumento = ? AND iIdDocumento = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveTipoDocumento,iIdDocumento, </b></p>
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
      String lSQL = "delete from GRLDocumento where iEjercicio = ? AND iCveTipoDocumento = ? AND iIdDocumento = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveTipoDocumento"));
      lPStmt.setInt(3,vData.getInt("iIdDocumento"));

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
   * <p><b> update GRLDocumento set iIdGestorDocumento=?, iCveUsuario=?, dtRegistro=?, cDscDocumento=?, cObses=? where iEjercicio = ? AND iCveTipoDocumento = ? AND iIdDocumento = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveTipoDocumento,iIdDocumento, </b></p>
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
      String lSQL = "update GRLDocumento set iIdGestorDocumento=?, iCveUsuario=?, dtRegistro=?, cDscDocumento=?, cObses=? , cNomArchivo=? where iEjercicio = ? AND iCveTipoDocumento = ? AND iIdDocumento = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveTipoDocumento"));
      vData.addPK(vData.getString("iIdDocumento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iIdGestorDocumento"));
      lPStmt.setInt(2,vData.getInt("iCveUsuario"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setString(4,vData.getString("cDscDocumento"));
      lPStmt.setString(5,vData.getString("cObses"));
      lPStmt.setString(6,vData.getString("cNomArchivo"));
      lPStmt.setInt(7,vData.getInt("iEjercicio"));
      lPStmt.setInt(8,vData.getInt("iCveTipoDocumento"));
      lPStmt.setInt(9,vData.getInt("iIdDocumento"));
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

 public void insertDinamico(String elSQL,TVDinRep vData,Connection cnNested) throws
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

     String lSQL = elSQL;

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iEjercicio"));
     lPStmt.setInt(2,vData.getInt("iCveTipoDocumento"));
     lPStmt.setInt(3,vData.getInt("iIdDocumento"));

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
   }
 }













}

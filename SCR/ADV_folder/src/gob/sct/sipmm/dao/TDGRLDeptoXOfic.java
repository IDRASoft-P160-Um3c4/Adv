package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLDeptoXOfic.java</p>
 * <p>Description: DAO de la entidad GRLDeptoXOfic</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
public class TDGRLDeptoXOfic extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
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
   * <p><b> insert into GRLDeptoXOfic(iCveOficina,iCveDepartamento,cTitular,cTelefono,cCorreoE) values (?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveDepartamento, </b></p>
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
      String lSQL =
          "insert into GRLDeptoXOfic(iCveOficina,iCveDepartamento,cTitular,cTelefono,cCorreoE) values (?,?,?,?,?)";

      //AGREGAR AL ULTIMO ... Borrado por que se le pasan los valores y no los tiene que calcular

      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iCveDepartamento"));
      lPStmt.setString(3,vData.getString("cTitular"));
      lPStmt.setString(4,vData.getString("cTelefono"));
      lPStmt.setString(5,vData.getString("cCorreoE"));
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
   * <p><b> delete from GRLDeptoXOfic where iCveOficina = ? AND iCveDepartamento = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveDepartamento, </b></p>
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
      String lSQL = "delete from GRLDeptoXOfic where iCveOficina = ? AND iCveDepartamento = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iCveDepartamento"));

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
   * <p><b> update GRLDeptoXOfic set cTitular=?, cTelefono=?, cCorreoE=? where iCveOficina = ? AND iCveDepartamento = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveDepartamento, </b></p>
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
      String lSQL = "update GRLDeptoXOfic set cTitular=?, cTelefono=?, cCorreoE=? where iCveOficina = ? AND iCveDepartamento = ? ";

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iCveDepartamento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cTitular"));
      lPStmt.setString(2,vData.getString("cTelefono"));
      lPStmt.setString(3,vData.getString("cCorreoE"));
      lPStmt.setInt(4,vData.getInt("iCveOficina"));
      lPStmt.setInt(5,vData.getInt("iCveDepartamento"));
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

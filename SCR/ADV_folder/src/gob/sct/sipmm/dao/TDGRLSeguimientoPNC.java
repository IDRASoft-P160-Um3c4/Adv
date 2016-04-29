package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLSeguimientoPNC.java</p>
 * <p>Description: DAO de la entidad GRLSeguimientoPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Levi Equihua
 * @version 1.0
 */
public class TDGRLSeguimientoPNC extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private int iSeguimiento;
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
   * <p><b> insert into GRLSeguimientoPNC(iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveSeguimiento,tsMomentoSeguimiento,cComentarios,iCveUsuRegistra,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveSeguimiento, </b></p>
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
    TFechas tFecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLSeguimientoPNC(iEjercicio,iConsecutivoPNC,iCveSeguimiento,tsMomentoSeguimiento,cComentarios,iCveUsuRegistra,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      String strUltimo = "select MAX(iCveSeguimiento) AS iCveSeguimiento from GRLSeguimientoPNC " +
          "where iEjercicio = " + vData.getInt("iEjercicio") + " and iConsecutivoPNC = " +
          vData.getInt("iConsecutivoPNC");
      
      Vector vcData = findByCustom("",strUltimo);
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveSeguimiento",vUltimo.getInt("iCveSeguimiento") + 1);
      }else
         vData.put("iCveSeguimiento",1);
      vData.addPK(vData.getString("iCveSeguimiento"));
      //...
      iSeguimiento = vData.getInt("iCveSeguimiento");
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(3,vData.getInt("iCveSeguimiento"));
      lPStmt.setString(4,String.valueOf(tFecha.getThisTime()));
      lPStmt.setString(5,vData.getString("cComentarios"));
      lPStmt.setInt(6,vData.getInt("iCveUsuRegistra"));
      lPStmt.setInt(7,vData.getInt("iCveOficinaAsignado"));
      lPStmt.setInt(8,vData.getInt("iCveDeptoAsignado"));
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
   * <p><b> delete from GRLSeguimientoPNC where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ? AND iCveSeguimiento = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveSeguimiento, </b></p>
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
      String lSQL = "delete from GRLSeguimientoPNC where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ? AND iCveSeguimiento = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(3,vData.getInt("iCveProducto"));
      lPStmt.setInt(4,vData.getInt("iCveCausaPNC"));
      lPStmt.setInt(5,vData.getInt("iCveSeguimiento"));

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
   * <p><b> update GRLSeguimientoPNC set tsMomentoSeguimiento=?, cComentarios=?, iCveUsuRegistra=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ? AND iCveSeguimiento = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveSeguimiento, </b></p>
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
    TFechas tf = new TFechas();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update GRLSeguimientoPNC set tsMomentoSeguimiento=?, cComentarios=?, iCveUsuRegistra=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ? AND iCveSeguimiento = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iConsecutivoPNC"));
      vData.addPK(vData.getString("iCveProducto"));
      vData.addPK(vData.getString("iCveCausaPNC"));
      vData.addPK(vData.getString("iCveSeguimiento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("tsMomentoSeguimiento"));
      lPStmt.setString(2,vData.getString("cComentarios"));
      lPStmt.setInt(3,vData.getInt("iCveUsuRegistra"));
      lPStmt.setInt(4,vData.getInt("iCveOficinaAsignado"));
      lPStmt.setInt(5,vData.getInt("iCveDeptoAsignado"));
      lPStmt.setInt(6,vData.getInt("iEjercicio"));
      lPStmt.setInt(7,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(8,vData.getInt("iCveProducto"));
      lPStmt.setInt(9,vData.getInt("iCveCausaPNC"));
      lPStmt.setInt(10,vData.getInt("iCveSeguimiento"));
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

 public int getSeguimiento(){
   return iSeguimiento;
 }

}

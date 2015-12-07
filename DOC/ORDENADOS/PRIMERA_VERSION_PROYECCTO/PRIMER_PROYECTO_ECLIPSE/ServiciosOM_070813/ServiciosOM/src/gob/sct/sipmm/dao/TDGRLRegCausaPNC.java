package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLRegCausaPNC.java</p>
 * <p>Description: DAO de la entidad GRLRegCausaPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
public class TDGRLRegCausaPNC extends DAOBase{
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
   * <p><b> insert into GRLRegCausaPNC(iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveUsuCorrige,cDscOtraCausa,lResuelto,dtResolucion) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
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
          "insert into GRLRegCausaPNC(iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveUsuCorrige,cDscOtraCausa,lResuelto,dtResolucion) values (?,?,?,?,?,?,?,?)";

      String [] strCveCausaPNC = vData.getString("iCveCausaPNC").split(",");
      String [] strCveUsuCorrige = vData.getString("iCveUsuCorrige").split(",");

      for(int i=0;i< strCveCausaPNC.length ;i++){

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEjercicio"));
        lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
        lPStmt.setInt(3,vData.getInt("iCveProducto"));

        lPStmt.setInt(4,Integer.parseInt(strCveCausaPNC[i]));
        lPStmt.setInt(5,Integer.parseInt(strCveUsuCorrige[i]));

        lPStmt.setString(6,vData.getString("cDscOtraCausa"));
        lPStmt.setInt(7,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(8,Types.DATE);
        else
          lPStmt.setDate(8,vData.getDate("dtResolucion"));
        lPStmt.executeUpdate();
        conn.commit();
        lPStmt.close();
      }

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
   * <p><b> delete from GRLRegCausaPNC where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
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
      String lSQL = "delete from GRLRegCausaPNC where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(3,vData.getInt("iCveProducto"));
      lPStmt.setInt(4,vData.getInt("iCveCausaPNC"));

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
   * <p><b> update GRLRegCausaPNC set iCveUsuCorrige=?, cDscOtraCausa=?, lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
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
      String lSQL = "update GRLRegCausaPNC set iCveUsuCorrige=?, cDscOtraCausa=?, lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iConsecutivoPNC"));
      vData.addPK(vData.getString("iCveProducto"));
      vData.addPK(vData.getString("iCveCausaPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveUsuCorrige"));
      lPStmt.setString(2,vData.getString("cDscOtraCausa"));
      lPStmt.setInt(3,vData.getInt("lResuelto"));
      lPStmt.setDate(4,vData.getDate("dtResolucion"));
      lPStmt.setInt(5,vData.getInt("iEjercicio"));
      lPStmt.setInt(6,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(7,vData.getInt("iCveProducto"));
      lPStmt.setInt(8,vData.getInt("iCveCausaPNC"));
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

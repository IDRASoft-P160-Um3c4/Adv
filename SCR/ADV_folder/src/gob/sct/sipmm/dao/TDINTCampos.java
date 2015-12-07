package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>Title: TDINTCAMPOS.java</p>
 * <p>Description: DAO de la entidad INTCAMPOS</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author JESR
 * @version 1.0
 */
public class TDINTCampos extends DAOBase
{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepción de tipo DAO
   * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
   */
  public Vector findByCustom(String cKey,String cWhere) throws DAOException
  {
    Vector vcRecords = new Vector();
    cError = "";
    try{
         String cSQL = cWhere;
         vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
       }
    catch(Exception e)
    {
      e.printStackTrace();
      cError = e.toString();
    } 
    finally
    {
      if(!cError.equals(""))
        throw new DAOException(cError);
    }
    return vcRecords;
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into INTCAMPOS(ICVECAMPO,CCAMPO,CETIQUETA,ICVETIPOCAMPO,CTABLA,CCVE,CDSC,LMANDATORIO,LFIJO,LENCABEZADO) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: ICVECAMPO, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws DAOException
  {
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    try{
         if(cnNested == null)
         {
           dbConn = new DbConnection(dataSourceName);
           conn = dbConn.getConnection();
           conn.setAutoCommit(false);
           conn.setTransactionIsolation(2);
         }
         String lSQL = "insert into INTCAMPOS(ICVECAMPO,CCAMPO,CETIQUETA,ICVETIPOCAMPO,CTABLA,CCVE,CDSC,LMANDATORIO," + 
                       "LFIJO,LENCABEZADO,ILARGO,LSELECCIONAR,CLIGADO,CCAMPOCOP,CSCRIPT) values " + 
                       "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

         //AGREGAR AL ULTIMO ...
         Vector vcData = findByCustom("", "select MAX(ICVECAMPO) AS ICVECAMPO from INTCAMPOS");
         if(vcData.size() > 0)
         {
           TVDinRep vUltimo = (TVDinRep) vcData.get(0);
           vData.put("ICVECAMPO",vUltimo.getInt("ICVECAMPO") + 1);
         }
         else
           vData.put("ICVECAMPO",1);
         vData.addPK(vData.getString("ICVECAMPO"));
         //...

         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.setInt(1,vData.getInt("ICVECAMPO"));
         lPStmt.setString(2,vData.getString("CCAMPO"));
         lPStmt.setString(3,vData.getString("CETIQUETA"));
         lPStmt.setInt(4,vData.getInt("ICVETIPOCAMPO"));
         lPStmt.setString(5,vData.getString("CTABLA"));
         lPStmt.setString(6,vData.getString("CCVE"));
         lPStmt.setString(7,vData.getString("CDSC"));
         lPStmt.setInt(8,vData.getInt("LMANDATORIO"));
         lPStmt.setInt(9,vData.getInt("LFIJO"));
         lPStmt.setInt(10,vData.getInt("LENCABEZADO"));
         lPStmt.setInt(11,vData.getInt("ILARGO"));
         lPStmt.setInt(12,vData.getInt("LSELECCIONAR"));
         lPStmt.setString(13,vData.getString("CLIGADO"));
         lPStmt.setString(14,vData.getString("CCAMPOCOP"));
         lPStmt.setString(15,vData.getString("CSCRIPT"));
         lPStmt.executeUpdate();
         
         if(cnNested == null){ conn.commit(); }
       }
    catch(Exception ex)
    {
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
    }
    return vData;
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from INTCAMPOS where ICVECAMPO = ?  </b></p>
   * <p><b> Campos Llave: ICVECAMPO, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested) throws DAOException
  {
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    String cMsg = "";
    try{
         if(cnNested == null)
         {
           dbConn = new DbConnection(dataSourceName);
           conn = dbConn.getConnection();
           conn.setAutoCommit(false);
           conn.setTransactionIsolation(2);
         }

         //Ajustar Where de acuerdo a requerimientos...
         String lSQL = "delete from INTCAMPOS where ICVECAMPO = ?  ";
         //...

         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.setInt(1,vData.getInt("ICVECAMPO"));
         lPStmt.executeUpdate();
         
         if(cnNested == null){ conn.commit(); }
       }
    catch(SQLException sqle)
    {
      sqle.printStackTrace();
      lSuccess = false;
      cMsg = "" + sqle.getErrorCode();
    } 
    catch(Exception ex)
    {
      ex.printStackTrace();
      warn("delete",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("delete.rollback",e);
        }
      }
      lSuccess = false;
    } 
    finally{
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
    }
    return lSuccess;
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update INTCAMPOS set CCAMPO=?, CETIQUETA=?, ICVETIPOCAMPO=?, CTABLA=?, CCVE=?, CDSC=?, LMANDATORIO=?, LFIJO=?, LENCABEZADO=? where ICVECAMPO = ?  </b></p>
   * <p><b> Campos Llave: ICVECAMPO, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws DAOException
  {
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    try{
         if(cnNested == null)
         {
           dbConn = new DbConnection(dataSourceName);
           conn = dbConn.getConnection();
           conn.setAutoCommit(false);
           conn.setTransactionIsolation(2);
         }
         String lSQL = "update INTCAMPOS set CCAMPO=?, CETIQUETA=?, ICVETIPOCAMPO=?, CTABLA=?, CCVE=?, CDSC=?, " + 
                       "LMANDATORIO=?, LFIJO=?, LENCABEZADO=?, ILARGO=?, LSELECCIONAR=?, CLIGADO=?, CCAMPOCOP=?, " + 
                       "CSCRIPT=? where ICVECAMPO = ? ";

         vData.addPK(vData.getString("ICVECAMPO"));

         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.setString(1,vData.getString("CCAMPO"));
         lPStmt.setString(2,vData.getString("CETIQUETA"));
         lPStmt.setInt(3,vData.getInt("ICVETIPOCAMPO"));
         lPStmt.setString(4,vData.getString("CTABLA"));
         lPStmt.setString(5,vData.getString("CCVE"));
         lPStmt.setString(6,vData.getString("CDSC"));
         lPStmt.setInt(7,vData.getInt("LMANDATORIO"));
         lPStmt.setInt(8,vData.getInt("LFIJO"));
         lPStmt.setInt(9,vData.getInt("LENCABEZADO"));
         lPStmt.setInt(10,vData.getInt("ILARGO"));
         lPStmt.setInt(11,vData.getInt("LSELECCIONAR"));
         lPStmt.setString(12,vData.getString("CLIGADO"));
         lPStmt.setString(13,vData.getString("CCAMPOCOP"));
         lPStmt.setString(14,vData.getString("CSCRIPT"));
         lPStmt.setInt(15,vData.getInt("ICVECAMPO"));
         lPStmt.executeUpdate();
         
         if(cnNested == null){ conn.commit(); }
       }
    catch(Exception ex)
    {
      ex.printStackTrace();
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
    }
    return vData;
  }
}

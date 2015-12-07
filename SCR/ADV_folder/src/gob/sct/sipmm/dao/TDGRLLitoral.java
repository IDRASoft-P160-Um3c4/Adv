package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import javax.swing.*;
/**
 * <p>Title: TDGRLLitoral.java</p>
 * <p>Description: DAO de la entidad GRLLitoral</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author arodriguez
 * @version 1.0
 */
public class TDGRLLitoral extends DAOBase{
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
   * <p><b> insert into GRLLitoral(iCveLitoral,cDscLitoral,lVigente) values (?,?,?) </b></p>
   * <p><b> Campos Llave: iCveLitoral, </b></p>
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
    ResultSet lRSset = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL =
          "select * from GRLLitoral where cDscLitoral = '"+vData.getString("cDscLitoral") +"'";
      lPStmt = conn.prepareStatement(lSQL);
      lRSset=lPStmt.executeQuery();
      if (lRSset.next())
         {

         vData = null;
         }
         else
         {
           lSQL =
                "insert into GRLLitoral(iCveLitoral,cDscLitoral,lVigente) values (?,?,?)";

            //AGREGAR AL ULTIMO ...
            Vector vcData = findByCustom("",
                "select MAX(iCveLitoral) AS iCveLitoral from GRLLitoral");
            if(vcData.size() > 0){
              TVDinRep vUltimo = (TVDinRep) vcData.get(0);
              vData.put("iCveLitoral",vUltimo.getInt("iCveLitoral") + 1);
            } else
              vData.put("iCveLitoral",1);
            vData.addPK(vData.getString("iCveLitoral"));
            //...
            lPStmt = conn.prepareStatement(lSQL);
            lPStmt.setInt(1,vData.getInt("iCveLitoral"));
            lPStmt.setString(2,vData.getString("cDscLitoral"));
            lPStmt.setInt(3,vData.getInt("lVigente"));
            lPStmt.executeUpdate();
            if(cnNested == null){
              conn.commit();
            }
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
   * <p><b> delete from GRLLitoral where iCveLitoral = ?  </b></p>
   * <p><b> Campos Llave: iCveLitoral, </b></p>
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
      String lSQL = "delete from GRLLitoral where iCveLitoral = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveLitoral"));

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
   * <p><b> update GRLLitoral set cDscLitoral=?, lVigente=? where iCveLitoral = ?  </b></p>
   * <p><b> Campos Llave: iCveLitoral, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    ResultSet lRSset = null;
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
          "select * from GRLLitoral where cDscLitoral = '"+vData.getString("cDscLitoral") +"' "+
          " AND iCveLitoral NOT IN ("+vData.getInt("iCveLitoral")+") ";
      lPStmt = conn.prepareStatement(lSQL);
      lRSset=lPStmt.executeQuery();
      if (lRSset.next())
         {
         vData = null;
         }
         else
         {
           lSQL =
               "update GRLLitoral set cDscLitoral=?, lVigente=? where iCveLitoral = ? ";

           vData.addPK(vData.getString("iCveLitoral"));

           lPStmt = conn.prepareStatement(lSQL);
           lPStmt.setString(1,vData.getString("cDscLitoral"));
           lPStmt.setInt(2,vData.getInt("lVigente"));
           lPStmt.setInt(3,vData.getInt("iCveLitoral"));
           lPStmt.executeUpdate();
           if(cnNested == null){
             conn.commit();
           }
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

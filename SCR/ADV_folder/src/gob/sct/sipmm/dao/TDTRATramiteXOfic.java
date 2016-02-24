package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRATramiteXOfic.java</p>
 * <p>Description: DAO de la entidad TRATramiteXOfic</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRATramiteXOfic extends DAOBase{
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
   * <p><b> insert into TRATramiteXOfic(iCveOficina,iCveTramite,iCveOficinaResuelve,iCveDeptoResuelve) values (?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveTramite, </b></p>
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
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into TRATramiteXOfic(iCveOficina,iCveTramite,iCveOficinaResuelve,iCveDeptoResuelve) values (?,?,?,?)";
      
      String lSQLA =
              "SELECT ICVEOFICINA FROM GRLOFICINA where CDSCBREVE='D.G.D.C.';";
      //AGREGAR AL ULTIMO ...

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iCveTramite"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iCveTramite"));
      lPStmt.setInt(3,vData.getInt("iCveOficinaResuelve"));
      lPStmt.setInt(4,vData.getInt("iCveDeptoResuelve"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
      }catch(SQLException sqle){
       lSuccess = false;
       cMsg = "" + sqle.getErrorCode();

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
        throw new DAOException(cMsg);
      return vData;
    }
  }
  public TVDinRep Agregar(TVDinRep vData,Connection cnNested) throws
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
//      String lSQL =
//          "insert into TRATramiteXOfic(iCveOficina,iCveTramite) values (?,?)";
      String lSQL = "insert into TRATramiteXOfic(iCveOficina,iCveTramite,iCveOficinaResuelve,iCveDeptoResuelve) values (?,?,?,?)";
      System.out.print(vData.getInt("iCveOficina1")+"  **********  "+vData.getInt("iCveTramite"));

		/*SE SOLICITA PONER COMO DEFAULT OFICINA DGDC Y DPTO DPA*/
		Integer iCveOficina=0, iCveDpto = 0;
		
		String lSQLA =
		"SELECT ICVEOFICINA FROM GRLOFICINA where CDSCBREVE='D.G.D.C.'";
		
		iCveOficina = ((TVDinRep) findByCustom("ICVEOFICINA",lSQLA).get(0)).getInt("ICVEOFICINA");
		
		lSQLA = "SELECT ICVEDEPARTAMENTO FROM GRLDEPARTAMENTO where CDSCBREVE='DPA'";             
		
		iCveDpto= ((TVDinRep) findByCustom("ICVEDEPARTAMENTO",lSQLA).get(0)).getInt("ICVEDEPARTAMENTO");

		System.out.print(vData.getInt("iCveOficina1"));
		System.out.print(vData.getInt("iCveTramite"));
		
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveOficina1"));
      lPStmt.setInt(2,vData.getInt("iCveTramite"));      
      lPStmt.setInt(3,iCveOficina);
      lPStmt.setInt(4,iCveDpto);
      
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
      }catch(SQLException sqle){
       lSuccess = false;
       cMsg = "" + sqle.getErrorCode();

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
        throw new DAOException(cMsg);
      return vData;
    }
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRATramiteXOfic where iCveOficina = ? AND iCveTramite = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveTramite, </b></p>
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
      String lSQL = "delete from TRATramiteXOfic where iCveOficina = ? AND iCveTramite = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iCveTramite"));

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
   * <p><b> update TRATramiteXOfic set iCveOficinaResuelve=?, iCveDeptoResuelve=? where iCveOficina = ? AND iCveTramite = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iCveTramite, </b></p>
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
      String lSQL = "update TRATramiteXOfic set iCveOficinaResuelve=?, iCveDeptoResuelve=? where iCveOficina = ? AND iCveTramite = ? ";

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iCveTramite"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveOficinaResuelve"));
      lPStmt.setInt(2,vData.getInt("iCveDeptoResuelve"));
      lPStmt.setInt(3,vData.getInt("iCveOficina"));
      lPStmt.setInt(4,vData.getInt("iCveTramite"));
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

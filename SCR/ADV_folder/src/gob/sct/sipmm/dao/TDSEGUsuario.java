package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDSEGUsuario.java</p>
 * <p>Description: DAO de la entidad SEGUsuario</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Leopoldo Beristain González
 * @version 1.0
 */
public class TDSEGUsuario extends DAOBase{
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
      System.out.print("*****  Tamaño del vector de usuarios:  "+vcRecords.size());
      return vcRecords;
    }
 }
  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into SEGUsuario(iCveUsuario,dtRegistro,cUsuario,cPassword,cNombre,cApPaterno,cApMaterno,cCalle,cColonia,iCvePais,iCveEntidadFed,iCveMunicipio,iCodigoPostal,cTelefono,iCveUnidadOrg,lBloqueado) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveUsuario, </b></p>
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
          "insert into SEGUsuario(iCveUsuario,dtRegistro,cUsuario,cPassword,cNombre,cApPaterno,cApMaterno,cCalle,cColonia,iCvePais,iCveEntidadFed,iCveMunicipio,iCodigoPostal,cTelefono,iCveUnidadOrg,lBloqueado) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveUsuario) AS iCveUsuario from SEGUsuario");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveUsuario",vUltimo.getInt("iCveUsuario") + 1);
      }else
         vData.put("iCveUsuario",1);
      vData.addPK(vData.getString("iCveUsuario"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveUsuario"));
      lPStmt.setDate(2,vData.getDate("dtRegistro"));
      lPStmt.setString(3,vData.getString("cUsuario"));
      lPStmt.setString(4,vData.getString("cPassword"));
      lPStmt.setString(5,vData.getString("cNombre"));
      lPStmt.setString(6,vData.getString("cApPaterno"));
      lPStmt.setString(7,vData.getString("cApMaterno"));
      lPStmt.setString(8,vData.getString("cCalle"));
      lPStmt.setString(9,vData.getString("cColonia"));
      lPStmt.setInt(10,vData.getInt("iCvePais"));
      lPStmt.setInt(11,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(12,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(13,vData.getInt("iCodigoPostal"));
      lPStmt.setString(14,vData.getString("cTelefono"));
      lPStmt.setInt(15,vData.getInt("iCveUnidadOrg"));
      lPStmt.setInt(16,vData.getInt("lBloqueado"));
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
   * <p><b> delete from SEGUsuario where iCveUsuario = ?  </b></p>
   * <p><b> Campos Llave: iCveUsuario, </b></p>
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
      String lSQL = "delete from SEGUsuario where iCveUsuario = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveUsuario"));

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
   * <p><b> update SEGUsuario set dtRegistro=?, cUsuario=?, cPassword=?, cNombre=?, cApPaterno=?, cApMaterno=?, cCalle=?, cColonia=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCodigoPostal=?, cTelefono=?, iCveUnidadOrg=?, lBloqueado=? where iCveUsuario = ?  </b></p>
   * <p><b> Campos Llave: iCveUsuario, </b></p>
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
      String lSQL = "update SEGUsuario set dtRegistro=?, cUsuario=?, cPassword=?, cNombre=?, cApPaterno=?, cApMaterno=?, cCalle=?, cColonia=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCodigoPostal=?, cTelefono=?, iCveUnidadOrg=?, lBloqueado=? where iCveUsuario = ? ";

      vData.addPK(vData.getString("iCveUsuario"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setString(2,vData.getString("cUsuario"));
      lPStmt.setString(3,vData.getString("cPassword"));
      lPStmt.setString(4,vData.getString("cNombre"));
      lPStmt.setString(5,vData.getString("cApPaterno"));
      lPStmt.setString(6,vData.getString("cApMaterno"));
      lPStmt.setString(7,vData.getString("cCalle"));
      lPStmt.setString(8,vData.getString("cColonia"));
      lPStmt.setInt(9,vData.getInt("iCvePais"));
      lPStmt.setInt(10,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(11,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(12,vData.getInt("iCodigoPostal"));
      lPStmt.setString(13,vData.getString("cTelefono"));
      lPStmt.setInt(14,vData.getInt("iCveUnidadOrg"));
      lPStmt.setInt(15,vData.getInt("lBloqueado"));
      lPStmt.setInt(16,vData.getInt("iCveUsuario"));
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
   public int esAdministrador(TVDinRep vData,Connection cnNested) throws
       DAOException{
     String dataSourceADMSege = VParametros.getPropEspecifica("ConDB");
     Vector vAdministrador = new Vector();
     String cQuery = "SELECT * FROM SEGGPOXUSR GU " +
                     "WHERE GU.ICVESISTEMA = 44 " +
                     "AND GU.ICVEGRUPO = 1 " +
                     "AND ICVEUSUARIO= "+vData.getInt("iCveUsuario");
     try{
       vAdministrador = this.FindByGeneric("",cQuery,dataSourceADMSege);
     }catch(Exception e){
       e.printStackTrace();
     }
     if(vAdministrador.size()>0) return 1;
     else return 0;
   }

}

package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLOpinionEntidad.java</p>
 * <p>Description: DAO de la entidad GRLOpinionEntidad</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
public class TDGRLOpinionEntidad extends DAOBase{
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
//    cMensaje = "No se pueden insertar datos repetidos.";
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
   * <p><b> insert into GRLOpinionEntidad(iCveOpinionEntidad,lEsTramite,lEsOpinionInterna,iCveTramite,iCveModalidad,iCveSistema,iCveModulo,iCveOficinaOpn,iCveDepartamentoOpn,iCvePersona,iCveDomicilio,lVigente) values (?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveOpinionEntidad, </b></p>
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
          "insert into GRLOpinionEntidad(iCveOpinionEntidad,lEsTramite,lEsOpinionInterna,iCveTramite,iCveModalidad,iCveOficinaOpn,iCveDepartamentoOpn,lVigente) values (?,1,1,?,?,?,?,?)";

             //AGREGAR AL ULTIMO ...
             Vector vcData1 = findByCustom("","select MAX(iCveOpinionEntidad) AS iCveOpinionEntidad from GRLOpinionEntidad");
             if(vcData1.size() > 0){
               TVDinRep vUltimo = (TVDinRep) vcData1.get(0);
               vData.put("iCveOpinionEntidad",
                         vUltimo.getInt("iCveOpinionEntidad") + 1);
             } else
               vData.put("iCveOpinionEntidad",1);
             vData.addPK(vData.getString("iCveOpinionEntidad"));
             //...

             lPStmt = conn.prepareStatement(lSQL);
             lPStmt.setInt(1,vData.getInt("iCveOpinionEntidad"));
     //      lPStmt.setInt(2,vData.getInt("lEsTramite"));
     //      lPStmt.setInt(3,vData.getInt("lEsOpinionInterna"));
             lPStmt.setInt(2,vData.getInt("iCveTramite"));
             lPStmt.setInt(3,vData.getInt("iCveModalidad"));
             lPStmt.setInt(4,vData.getInt("iCveOficinaUsr"));
             lPStmt.setInt(5,vData.getInt("iCveDeptoUsr"));
             lPStmt.setInt(6,vData.getInt("lVigente"));
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
   * <p><b> delete from GRLOpinionEntidad where iCveOpinionEntidad = ?  </b></p>
   * <p><b> Campos Llave: iCveOpinionEntidad, </b></p>
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
      String lSQL = "delete from GRLOpinionEntidad where iCveOpinionEntidad = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveOpinionEntidad"));

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
   * <p><b> update GRLOpinionEntidad set lEsTramite=?, lEsOpinionInterna=?, iCveTramite=?, iCveModalidad=?, iCveSistema=?, iCveModulo=?, iCveOficinaOpn=?, iCveDepartamentoOpn=?, iCvePersona=?, iCveDomicilio=?, lVigente=? where iCveOpinionEntidad = ?  </b></p>
   * <p><b> Campos Llave: iCveOpinionEntidad, </b></p>
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
      String lSQL = "update GRLOpinionEntidad set iCveOficinaOpn=?, iCveDepartamentoOpn=?, lVigente=? where iCveOpinionEntidad=?";

      vData.addPK(vData.getString("iCveOpinionEntidad"));

      lPStmt = conn.prepareStatement(lSQL);
//      lPStmt.setInt(1,vData.getInt("lEsTramite"));
//      lPStmt.setInt(2,vData.getInt("lEsOpinionInterna"));
//      lPStmt.setInt(3,vData.getInt("iCveTramite"));
//      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
//      lPStmt.setInt(5,vData.getInt("iCveSistema"));
//      lPStmt.setInt(6,vData.getInt("iCveModulo"));
      lPStmt.setInt(1,vData.getInt("iCveOficinaUsr"));
      lPStmt.setInt(2,vData.getInt("iCveDeptoUsr"));
//      lPStmt.setInt(9,vData.getInt("iCvePersona"));
//      lPStmt.setInt(10,vData.getInt("iCveDomicilio"));
      lPStmt.setInt(3,vData.getInt("lVigente"));
      lPStmt.setInt(4,vData.getInt("iCveOpinionEntidad"));
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

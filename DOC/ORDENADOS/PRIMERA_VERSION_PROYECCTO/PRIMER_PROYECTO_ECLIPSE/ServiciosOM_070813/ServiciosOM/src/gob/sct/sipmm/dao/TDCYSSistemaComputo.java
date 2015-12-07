package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDCYSSistemaComputo.java</p>
 * <p>Description: DAO de la entidad CYSSistemaComputo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSSistemaComputo extends DAOBase{
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
   * <p><b> insert into CYSSistemaComputo(iCveTitulo,iMovSistemaComputo,dtRegistro,dtPresentacion,cObsSistemaComputo) values (?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTitulo,iMovSistemaComputo, </b></p>
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
          "insert into CYSSistemaComputo(iCveTitulo,iMovSistemaComputo,dtRegistro,dtPresentacion,cObsSistemaComputo," +
          "cNumEscrito,dtEscrito,dtEstablecimientoSC) values (?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovSistemaComputo) AS iMovSistemaComputo from CYSSistemaComputo");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovSistemaComputo",vUltimo.getInt("iMovSistemaComputo") + 1);
      }else
         vData.put("iMovSistemaComputo",1);

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovSistemaComputo"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovSistemaComputo"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setString(5,vData.getString("cObsSistemaComputo"));
      lPStmt.setString(6,vData.getString("cNumEscrito"));
      lPStmt.setDate(7,vData.getDate("dtEscrito"));
      lPStmt.setDate(8,vData.getDate("dtEstablecimientoSC"));
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
   * <p><b> delete from CYSSistemaComputo where iCveTitulo = ? AND iMovSistemaComputo = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iMovSistemaComputo, </b></p>
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
      String lSQL = "delete from CYSSistemaComputo where iCveTitulo = ? AND iMovSistemaComputo = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovSistemaComputo"));

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
   * <p><b> update CYSSistemaComputo set dtRegistro=?, dtPresentacion=?, cObsSistemaComputo=? where iCveTitulo = ? AND iMovSistemaComputo = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iMovSistemaComputo, </b></p>
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
      String lSQL = "update CYSSistemaComputo set dtRegistro=?, dtPresentacion=?, cObsSistemaComputo=?, " +
                    "cNumEscrito=?, dtEscrito=?, dtEstablecimientoSC=? where iCveTitulo = ? AND iMovSistemaComputo = ? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovSistemaComputo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setString(3,vData.getString("cObsSistemaComputo"));
      lPStmt.setString(4,vData.getString("cNumEscrito"));
      lPStmt.setDate(5,vData.getDate("dtEscrito"));
      lPStmt.setDate(6,vData.getDate("dtEstablecimientoSC"));
      lPStmt.setInt(7,vData.getInt("iCveTitulo"));
      lPStmt.setInt(8,vData.getInt("iMovSistemaComputo"));
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

 public Vector findSistemaComputo(int iCveTitulo) throws DAOException{
  Vector vcSistema = new Vector();
  TVDinRep vExiste;

try{
  iCveTitulo = iCveTitulo;
  String cSQL = "SELECT DTPRESENTACION FROM CYSSISTEMACOMPUTO " +
                " WHERE ICVETITULO = " + iCveTitulo +
                " AND IMOVSISTEMACOMPUTO = (SELECT MAX(S.IMOVSISTEMACOMPUTO) " +
                "                           FROM CYSSISTEMACOMPUTO S WHERE S.ICVETITULO = " + iCveTitulo + ") ";
  Vector vcData = findByCustom("",cSQL);
  if(vcData.size()==0){
     vExiste = new TVDinRep();
     vExiste.put("cMotivo","NO PRESENTADO");
//     vcOficina.add(new Boolean(false));
     vcSistema.add(vExiste);
  } else {
    vExiste = new TVDinRep();
    vExiste.put("cMotivo","PRESENTADO");
//     vcOficina.add(new Boolean(false));
    vcSistema.add(vExiste);
  }


} catch(Exception e){
     cError = e.toString();
     e.printStackTrace();
  } finally{
  if(!cError.equals(""))
     throw new DAOException(cError);
  return vcSistema;
    }
}

}

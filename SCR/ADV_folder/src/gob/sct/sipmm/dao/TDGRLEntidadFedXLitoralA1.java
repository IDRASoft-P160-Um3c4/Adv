package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLEntidadFedXLitoralA1.java</p>
 * <p>Description: DAO de la entidad GRLEntidadFedXLitoralA1</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
public class TDGRLEntidadFedXLitoralA1 extends DAOBase{
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
   * <p><b> insert into GRLEntidadFedXLitoralA1(iCvePais,iCveEntidadFed,iCveLitoral,iOrden) values (?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCvePais,iCveEntidadFed, </b></p>
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
          "insert into GRLEntidadFedXLitoral(iCvePais,iCveEntidadFed,iCveLitoral,iOrden) values (?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iOrden) AS iOrden from GRLEntidadFedXLitoral where iCveLitoral = " + vData.getInt("iCveLitoral"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
      }else
         vData.put("iOrden",1);
      vData.addPK(vData.getString("iOrden"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePais"));
      lPStmt.setInt(2,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(3,vData.getInt("iCveLitoral"));
      lPStmt.setInt(4,vData.getInt("iOrden"));
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
   * <p><b> delete from GRLEntidadFedXLitoralA1 where iCvePais = ? AND iCveEntidadFed = ?  </b></p>
   * <p><b> Campos Llave: iCvePais,iCveEntidadFed, </b></p>
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
    PreparedStatement lPStmt1 = null;
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
      String lSQL = "delete from GRLEntidadFedXLitoral where iCvePais = ? AND iCveEntidadFed = ? AND iCveLitoral = ? ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCvePais1"));
      lPStmt.setInt(2,vData.getInt("iCveEntidadFed1"));
      lPStmt.setInt(3,vData.getInt("iCveLitoral"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
      String lSQL1 = "update GRLEntidadFedXLitoral set iOrden = (iOrden - 1) where iCveLitoral = ? And iOrden > ? ";
      lPStmt1 = conn.prepareStatement(lSQL1);
      lPStmt1.setInt(1,vData.getInt("iCveLitoral"));
      lPStmt1.setInt(2,vData.getInt("iOrden"));
      lPStmt1.executeUpdate();
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
          lPStmt1.close();
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
   * <p><b> update GRLEntidadFedXLitoralA1 set iCveLitoral=?, iOrden=? where iCvePais = ? AND iCveEntidadFed = ?  </b></p>
   * <p><b> Campos Llave: iCvePais,iCveEntidadFed, </b></p>
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
      String lSQL = "update GRLEntidadFedXLitoral set iOrden=? where iCvePais = ? AND iCveEntidadFed = ? AND iCveLitoral = ? ";

      vData.addPK(vData.getString("iCvePais"));
      vData.addPK(vData.getString("iCveEntidadFed"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iOrden"));
      lPStmt.setInt(2,vData.getInt("iCvePais"));
      lPStmt.setInt(3,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(4,vData.getInt("iCveLitoral"));
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
 public TVDinRep update1(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   PreparedStatement lPStmt2 = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String lSQL = "update GRLEntidadFedXLitoral set iOrden= 0 where iOrden = ?  AND iCveLitoral = ? ";
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iOrden")-1);
     lPStmt.setInt(2,vData.getInt("iCveLitoral"));
     lPStmt.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }
     String lSQL1 = "update GRLEntidadFedXLitoral set iOrden= ? where iOrden = ?  AND iCveLitoral = ?";
     lPStmt1 = conn.prepareStatement(lSQL1);
     lPStmt1.setInt(1,vData.getInt("iOrden")-1);
     lPStmt1.setInt(2,vData.getInt("iOrden"));
     lPStmt1.setInt(3,vData.getInt("iCveLitoral"));
     lPStmt1.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }
     String lSQL2 = "update GRLEntidadFedXLitoral set iOrden= ? where iOrden = 0 AND iCveLitoral = ?";
     lPStmt2 = conn.prepareStatement(lSQL2);
     lPStmt2.setInt(1,vData.getInt("iOrden"));
     lPStmt2.setInt(2,vData.getInt("iCveLitoral"));
     lPStmt2.executeUpdate();
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
         lPStmt1.close();
         lPStmt2.close();
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
 public TVDinRep update2(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   PreparedStatement lPStmt2 = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String lSQL = "update GRLEntidadFedXLitoral set iOrden= 0 where iOrden = ? AND iCveLitoral = ?";
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iOrden")+1);
     lPStmt.setInt(2,vData.getInt("iCveLitoral"));
     lPStmt.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }
     String lSQL1 = "update GRLEntidadFedXLitoral set iOrden= ? where iOrden = ? AND iCveLitoral = ? ";
     lPStmt1 = conn.prepareStatement(lSQL1);
     lPStmt1.setInt(1,vData.getInt("iOrden")+1);
     lPStmt1.setInt(2,vData.getInt("iOrden"));
     lPStmt1.setInt(3,vData.getInt("iCveLitoral"));
     lPStmt1.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }//GRLEntidadFedXLitoral;
     String lSQL2 = "update GRLEntidadFedXLitoral set iOrden= ? where iOrden = 0 AND iCveLitoral = ? ";
     lPStmt2 = conn.prepareStatement(lSQL2);
     lPStmt2.setInt(1,vData.getInt("iOrden"));
     lPStmt2.setInt(2,vData.getInt("iCveLitoral"));
     lPStmt2.executeUpdate();
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
         lPStmt1.close();
         lPStmt2.close();
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

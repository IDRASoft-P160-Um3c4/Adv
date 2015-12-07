package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRAEtapaXModTram.java</p>
 * <p>Description: DAO de la entidad TRAEtapaXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRAEtapaXModTram extends DAOBase{
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
   * <p><b> insert into TRAEtapaXModTram(iCveTramite,iCveModalidad,iCveEtapa,iOrden,lObligatorio,lVigente) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,iCveEtapa, </b></p>
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
          "insert into TRAEtapaXModTram(iCveTramite,iCveModalidad,iCveEtapa,iOrden,lObligatorio,lVigente) values (?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveEtapa"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iCveEtapa"));
      lPStmt.setInt(4,vData.getInt("iOrden"));
      lPStmt.setInt(5,vData.getInt("lObligatorio"));
      lPStmt.setInt(6,vData.getInt("lVigente"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
     } catch(SQLException sqle){
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
      String lSQL =
          "insert into TRAEtapaXModTram(iCveTramite,iCveModalidad,iCveEtapa,iOrden,lObligatorio,lVigente) values (?,?,?,?,?,1)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iOrden) AS iOrden from TRAEtapaXModTram where iCveTramite = " + vData.getString("iCveTramite") +" AND iCveModalidad = " + vData.getString("iCveModalidad"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
      }else
         vData.put("iOrden",1);

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveEtapa"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iCveEtapa1"));
      lPStmt.setInt(4,vData.getInt("iOrden"));
      lPStmt.setInt(5,vData.getInt("lObligatorio"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
     } catch(SQLException sqle){
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
  public TVDinRep Cambia(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    PreparedStatement lPStmt1 = null;
    boolean lSuccess = true;
    String[] cConjunto;
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
    cConjunto = vData.getString("cConjunto").split(",");
      String lSQL1 = "update TRAEtapaXModTram set lObligatorio = 0 where iCveTRamite = ? AND iCveModalidad = ?";
      lPStmt1 = conn.prepareStatement(lSQL1);
      lPStmt1.setInt(1,vData.getInt("iCveTramite"));
      lPStmt1.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt1.executeUpdate();

    for(int tm=0;tm<cConjunto.length;tm++){
      String lSQL =
          "update TRAEtapaXModTram set lObligatorio = 1 where iCveTRamite = ? AND iCveModalidad = ? AND iCveEtapa= ?";
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveEtapa"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,Integer.parseInt(cConjunto[tm]));
      lPStmt.executeUpdate();
      lPStmt.close();
    }
      if(cnNested == null){
        conn.commit();
      }
     } catch(SQLException sqle){
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
          lPStmt1.close();
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
   * <p><b> delete from TRAEtapaXModTram where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,iCveEtapa, </b></p>
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
      String lSQL = "delete from TRAEtapaXModTram where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iCveEtapa"));

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
  public boolean Eliminar(TVDinRep vData,Connection cnNested)throws
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
      boolean lBorro = true;

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from TRAEtapaXModTram where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ?  ";
      //...
      try{
        lPStmt = conn.prepareStatement(lSQL);

        lPStmt.setInt(1,vData.getInt("iCveTramite"));
        lPStmt.setInt(2,vData.getInt("iCveModalidad"));
        lPStmt.setInt(3,vData.getInt("iCveEtapa"));
        lPStmt.executeUpdate();
        conn.commit();
        lPStmt.close();
      }catch(SQLException eBorrado){
        lBorro = false;
        cMsg = "Error en Borrado";
        lSuccess = false;
      }
      String lSQL1 = "update TRAEtapaXModTram set iOrden = (iOrden -1) where iCveTramite = ? AND iCveModalidad = ? AND iOrden > ? ";
      //...

      lPStmt1 = conn.prepareStatement(lSQL1);

      lPStmt1.setInt(1,vData.getInt("iCveTramite"));
      lPStmt1.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt1.setInt(3,vData.getInt("iOrden"));
      if(lBorro){
        lPStmt1.executeUpdate();
        conn.commit();
      }
      if(lPStmt1 != null) lPStmt1.close();

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
      try{
        if(lPStmt1 != null){
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
   * <p><b> update TRAEtapaXModTram set iOrden=?, lObligatorio=?, lVigente=? where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,iCveEtapa, </b></p>
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
      String lSQL = "update TRAEtapaXModTram set iOrden=?, lObligatorio=?, lVigente=? where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? ";

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveEtapa"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iOrden"));
      lPStmt.setInt(2,vData.getInt("lObligatorio"));
      lPStmt.setInt(3,vData.getInt("lVigente"));
      lPStmt.setInt(4,vData.getInt("iCveTramite"));
      lPStmt.setInt(5,vData.getInt("iCveModalidad"));
      lPStmt.setInt(6,vData.getInt("iCveEtapa"));
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
 public TVDinRep updateCIS(TVDinRep vData,Connection cnNested) throws
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
     String lSQL = "update TRAEtapaXModTram set lEnviarFechaCIS=?,iCveEtapaCIS=? where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? ";

     vData.addPK(vData.getString("iCveTramite"));
     vData.addPK(vData.getString("iCveModalidad"));
     vData.addPK(vData.getString("iCveEtapa"));

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("lEnviarFechaCIS"));
     lPStmt.setInt(2,vData.getInt("iCveEtapaCIS"));
     lPStmt.setInt(3,vData.getInt("iCveTramite"));
     lPStmt.setInt(4,vData.getInt("iCveModalidad"));
     lPStmt.setInt(5,vData.getInt("iCveEtapa"));
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

 public TVDinRep Arriba(TVDinRep vData,Connection cnNested) throws
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
     String lSQL = "update TRAEtapaXModTram set iOrden=-1 where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? ";
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveTramite"));
     lPStmt.setInt(2,vData.getInt("iCveModalidad"));
     lPStmt.setInt(3,vData.getInt("iCveEtapa"));
     lPStmt.executeUpdate();
     conn.commit();
     lPStmt.close();

     String lSQL1 = "update TRAEtapaXModTram set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? ";
     lPStmt1 = conn.prepareStatement(lSQL1);
     lPStmt1.setInt(1,vData.getInt("iOrdenI"));
     lPStmt1.setInt(2,vData.getInt("iCveTramite"));
     lPStmt1.setInt(3,vData.getInt("iCveModalidad"));
     lPStmt1.setInt(4,vData.getInt("iEtapaTemp"));
     lPStmt1.executeUpdate();
     conn.commit();
     lPStmt1.close();

     String lSQL2 = "update TRAEtapaXModTram set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? ";
     lPStmt1 = conn.prepareStatement(lSQL2);
     lPStmt1.setInt(1,vData.getInt("iOrdenF"));
     lPStmt1.setInt(2,vData.getInt("iCveTramite"));
     lPStmt1.setInt(3,vData.getInt("iCveModalidad"));
     lPStmt1.setInt(4,vData.getInt("iCveEtapa"));
     lPStmt1.executeUpdate();
     conn.commit();
     lPStmt1.close();
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
         //lPStmt2.close();
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
 public TVDinRep Abajo(TVDinRep vData,Connection cnNested) throws
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

     String lSQL = "update TRAEtapaXModTram set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? ";
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iOrdenF"));
     lPStmt.setInt(2,vData.getInt("iCveTramite"));
     lPStmt.setInt(3,vData.getInt("iCveModalidad"));
     lPStmt.setInt(4,vData.getInt("iCveEtapa"));
     lPStmt.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }
     String lSQL1 = "update TRAEtapaXModTram set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iCveEtapa = ? ";
     lPStmt1 = conn.prepareStatement(lSQL1);
     lPStmt1.setInt(1,vData.getInt("iOrdenI"));
     lPStmt1.setInt(2,vData.getInt("iCveTramite"));
     lPStmt1.setInt(3,vData.getInt("iCveModalidad"));
     lPStmt1.setInt(4,vData.getInt("iEtapaTemp"));
     lPStmt1.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }
     String lSQL2 = "update TRAEtapaXModTram set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iOrden = 0 ";
     lPStmt2 = conn.prepareStatement(lSQL2);
     lPStmt2.setInt(1,vData.getInt("iOrden"));
     lPStmt2.setInt(2,vData.getInt("iCveTramite"));
     lPStmt2.setInt(3,vData.getInt("iCveModalidad"));
//     lPStmt2.setInt(4,vData.getInt("iCveEtapa"));
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

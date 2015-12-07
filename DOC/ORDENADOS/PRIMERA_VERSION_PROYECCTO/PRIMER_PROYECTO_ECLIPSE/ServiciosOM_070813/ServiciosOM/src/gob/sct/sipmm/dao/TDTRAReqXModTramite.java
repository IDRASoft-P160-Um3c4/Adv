package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;

/**
 * <p>Title: TDTRAReqXModTramite.java</p>
 * <p>Description: DAO de la entidad TRAReqXModTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRAReqXModTramite extends DAOBase{
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
   * <p><b> insert into TRAReqXModTramite(iCveTramite,iCveModalidad,iCveRequisito,iOrden) values (?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
          "insert into TRAReqXModTramite(iCveTramite,iCveModalidad,iCveRequisito,iOrden,lRequerido) values (?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iCveRequisito"));
      lPStmt.setInt(4,vData.getInt("iOrden"));
      lPStmt.setInt(5,vData.getInt("lRequerido"));

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
/*
   public TVDinRep update1(TVDinRep vData,Connection cnNested) throws
    DAOException{
  int i = 0;
  for (i=0;i< vData.getInt("iMax");i++){
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
      String lSQL = "update TRAReqXModTramite set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iOrden"));
      lPStmt.setInt(2,vData.getInt("iCveTramite"));
      lPStmt.setInt(3,vData.getInt("iCveModalidad"));
      lPStmt.setInt(4,vData.getInt("iCveRequisito"));
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

*/
public TVDinRep Agregar(TVDinRep vData,Connection cnNested) throws
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
        "insert into TRAReqXModTramite(iCveTramite,iCveModalidad,iCveREquisito,iOrden,lRequerido) values (?,?,?,?,?)";

    //AGREGAR AL ULTIMO ...
    Vector vcData = findByCustom("","select MAX(iOrden) AS iOrden from TRAReqXModTramite where iCveTramite = " + vData.getInt("iCveTramite") + " And iCveModalidad = " + vData.getInt("iCveModalidad"));
    if(vcData.size() > 0){
       TVDinRep vUltimo = (TVDinRep) vcData.get(0);
       vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
    }else
       vData.put("iOrden",1);
    vData.addPK(vData.getString("iOrden"));
    //...

    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iCveTramite"));
    lPStmt.setInt(2,vData.getInt("iCveModalidad"));
    lPStmt.setInt(3,vData.getInt("iCveRequisito1"));
    lPStmt.setInt(4,vData.getInt("iOrden"));
    lPStmt.setInt(5,vData.getInt("lRequerido"));
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
public TVDinRep AgregarT(TVDinRep vData,Connection cnNested) throws
    DAOException{
  DbConnection dbConn = null;
  Connection conn = cnNested;
  PreparedStatement lPStmt = null;
  String[] Var1;
  boolean lSuccess = true;
  try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }
    //AGREGAR AL ULTIMO ...
  Vector vcData = findByCustom("","select MAX(iOrden) AS iOrden from TRAReqXModTramite where iCveTramite = " + vData.getInt("iCveTramite") + " And iCveModalidad = " + vData.getInt("iCveModalidad"));
  if(vcData.size() > 0){
     TVDinRep vUltimo = (TVDinRep) vcData.get(0);
     vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
  }else
     vData.put("iOrden",1);
  vData.addPK(vData.getString("iOrden"));
  //...

Var1 = vData.getString("var1").split(",");
    for(int tm=0;tm<Var1.length;tm++){
      String lSQL = "insert into TRAReqXModTramite(iCveTramite,iCveModalidad,iCveREquisito,iOrden,lRequerido) values(?,?,?,?,?)";
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,Integer.parseInt(Var1[tm]));
      lPStmt.setInt(4,vData.getInt("iOrden")+tm);
      lPStmt.setInt(5,vData.getInt("lRequerido"));

      lPStmt.executeUpdate();

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
public TVDinRep Subir(TVDinRep vData,Connection cnNested) throws
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

    String lSQL = "update TRAReqXModTramite set iOrden= 0 where iOrden = ? AND iCveTramite = ? AND iCveModalidad = ? ";
    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iOrden")-1);
    lPStmt.setInt(2,vData.getInt("iCveTramite"));
    lPStmt.setInt(3,vData.getInt("iCveModalidad"));
    lPStmt.executeUpdate();
    if(cnNested == null){
      conn.commit();
    }
    String lSQL1 = "update TRAReqXModTramite set iOrden= ? where iOrden = ? AND iCveTramite = ? AND iCveModalidad = ? ";
    lPStmt1 = conn.prepareStatement(lSQL1);
    lPStmt1.setInt(1,vData.getInt("iOrden")-1);
    lPStmt1.setInt(2,vData.getInt("iOrden"));
    lPStmt1.setInt(3,vData.getInt("iCveTramite"));
    lPStmt1.setInt(4,vData.getInt("iCveModalidad"));
    lPStmt1.executeUpdate();
    if(cnNested == null){
      conn.commit();
    }
    String lSQL2 = "update TRAReqXModTramite set iOrden= ? where iOrden = 0  AND iCveTramite = ? AND iCveModalidad = ?";
    lPStmt2 = conn.prepareStatement(lSQL2);
    lPStmt2.setInt(1,vData.getInt("iOrden"));
    lPStmt2.setInt(2,vData.getInt("iCveTramite"));
    lPStmt2.setInt(3,vData.getInt("iCveModalidad"));
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
public TVDinRep Bajar(TVDinRep vData,Connection cnNested) throws
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

    String lSQL = "update TRAReqXModTramite set iOrden = 0 where iOrden = ? AND iCveTramite = ? AND iCveModalidad = ?";
    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iOrden")+1);
    lPStmt.setInt(2,vData.getInt("iCveTramite"));
    lPStmt.setInt(3,vData.getInt("iCveModalidad"));
    lPStmt.executeUpdate();
    if(cnNested == null){
      conn.commit();
    }
    String lSQL1 = "update TRAReqXModTramite set iOrden= ? where iOrden = ? AND iCveTramite = ? AND iCveModalidad = ?";
    lPStmt1 = conn.prepareStatement(lSQL1);
    lPStmt1.setInt(1,vData.getInt("iOrden")+1);
    lPStmt1.setInt(2,vData.getInt("iOrden"));
    lPStmt1.setInt(3,vData.getInt("iCveTramite"));
    lPStmt1.setInt(4,vData.getInt("iCveModalidad"));
    lPStmt1.executeUpdate();
    if(cnNested == null){
      conn.commit();
    }//GRLEntidadFedXLitoral;
    String lSQL2 = "update TRAReqXModTramite set iOrden= ? where iOrden = 0 AND iCveTramite = ? AND iCveModalidad = ?";
    lPStmt2 = conn.prepareStatement(lSQL2);
    lPStmt2.setInt(1,vData.getInt("iOrden"));
    lPStmt2.setInt(2,vData.getInt("iCveTramite"));
    lPStmt2.setInt(3,vData.getInt("iCveModalidad"));
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

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update TRAReqXModTramite set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
      String lSQL = "update TRAReqXModTramite set iOrden=? where iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iOrden"));
      lPStmt.setInt(2,vData.getInt("iCveTramite"));
      lPStmt.setInt(3,vData.getInt("iCveModalidad"));
      lPStmt.setInt(4,vData.getInt("iCveRequisito"));
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
 public TVDinRep Cambia(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       //String[] cConjunto;
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String lSQL = "update TRAReqXModTramite set lRequerido = 0 where iCveTramite = ? AND iCveModalidad = ? ";

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveTramite"));
     lPStmt.setInt(2,vData.getInt("iCveModalidad"));
     lPStmt.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }
   String [] cConjunto = vData.getString("cConjunto").split(",");
   for(int tm=0;tm<cConjunto.length;tm++){
     lSQL = "update TRAReqXModTramite set lRequerido = 1 Where iCveTRamite=? AND iCveModalidad=? AND iCveRequisito =?";
     lPStmt1 = conn.prepareStatement(lSQL);
     lPStmt1.setInt(1,vData.getInt("iCveTramite"));
     lPStmt1.setInt(2,vData.getInt("iCveModalidad"));
     lPStmt1.setInt(3,Integer.parseInt(cConjunto[tm]));

     lPStmt1.executeUpdate();

     lPStmt1.close();
   }
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
       warn("update.close",ex2);
     }
     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }
}

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

    String lSQL = "Delete From TRAReqXModTramite where iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";
    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iCveTramite"));
    lPStmt.setInt(2,vData.getInt("iCveModalidad"));
    lPStmt.setInt(3,vData.getInt("iCveRequisito"));
    lPStmt.executeUpdate();
    if(cnNested == null){
      conn.commit();
    }
    String lSQL1 = "update TRAReqXModTramite set iOrden = (iOrden - 1) where iCveTramite = ? AND iCveModalidad =? And iOrden > ? ";
    lPStmt1 = conn.prepareStatement(lSQL1);
    lPStmt1.setInt(1,vData.getInt("iCveTramite"));
    lPStmt1.setInt(2,vData.getInt("iCveModalidad"));
    lPStmt1.setInt(3,vData.getInt("iOrden"));
    lPStmt1.executeUpdate();
//    if(cnNested == null){
      conn.commit();
//    }
   } catch(SQLException sqle){
     //sqle.printStackTrace();
     lSuccess = false;
     cMsg = ""+sqle.getErrorCode();
   } catch(Exception ex){
     //ex.printStackTrace();
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
       if(lPStmt != null)
         lPStmt.close();
       if(lPStmt1 != null)
         lPStmt1.close();

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

}













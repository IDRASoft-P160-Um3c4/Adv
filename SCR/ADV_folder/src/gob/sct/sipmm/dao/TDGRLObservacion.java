package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLObservacion.java</p>
 * <p>Description: DAO de la entidad GRLObservacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDGRLObservacion extends DAOBase{
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
   * <p><b> insert into GRLObservacion(iEjercicio,iCveObservacion,cObsRegistrada) values (?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveObservacion, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws DAOException{
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
          "insert into GRLObservacion(iEjercicio,iCveObservacion,cObsRegistrada) values (YEAR(CURRENT DATE),?,?)";

      String cSql =  "Select distinct year(current date) as ANIO from tracattramite";

      Vector vcFecha = findByCustom("",cSql);

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveObservacion) AS iCveObservacion from GRLObservacion where iEjercicio = year(current date)");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveObservacion",vUltimo.getInt("iCveObservacion") + 1);
      }else
         vData.put("iCveObservacion",1);
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveObservacion"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveObservacion"));
      lPStmt.setString(2,vData.getString("cObsRegistrada"));
      lPStmt.executeUpdate();

      vData.put("iEjercicioObs",((TVDinRep)vcFecha.get(0)).getInt("ANIO"));
      new TDTRARegTramXSol().insert(vData,conn);
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


  /********************************************************************/
  public TVDinRep insert2(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    int maximo=1;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLObservacion(iEjercicio,iCveObservacion,cObsRegistrada) values (YEAR(CURRENT DATE),?,?)";

      //String cSql =  "Select distinct year(current date) as ANIO from tracattramite";

     // Vector vcFecha = findByCustom("",cSql);

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveObservacion) AS iCveObservacion from GRLObservacion where iEjercicio = year(current date)");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveObservacion",vUltimo.getInt("iCveObservacion") + 1);
          maximo=vUltimo.getInt("iCveObservacion") + 1;
      }else
         vData.put("iCveObservacion",1);
   //   vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveObservacion"));
      //...

     lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveObservacion"));
      lPStmt.setString(2,vData.getString("cObsRegistrada"));
     lPStmt.executeUpdate();
     vData.put("iCveObservacion",maximo);
     new TDSSMRegistroPrecaucion().insert(vData,conn);

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
  /********************************************************************/
 public TVDinRep insert3(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   int maximo=1;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String lSQL =
         "insert into GRLObservacion(iEjercicio,iCveObservacion,cObsRegistrada) values (YEAR(CURRENT DATE),?,?)";
    // debug("ddespues string");

     //String cSql =  "Select distinct year(current date) as ANIO from tracattramite";

    // Vector vcFecha = findByCustom("",cSql);

     //AGREGAR AL ULTIMO ...
     Vector vcData = findByCustom("","select MAX(iCveObservacion) AS iCveObservacion from GRLObservacion where iEjercicio = year(current date)");
        //  debug("antes del vcdata");
     if(vcData.size() > 0){
       //debug("antes del vdinrep"+vData.getInt("iCveObservacion"));
       TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        //debug("antes vdataput"+vUltimo);

        vData.put("iCveObservacion",vUltimo.getInt("iCveObservacion") + 1);
         //debug("antes del obtener maximo");
       maximo=vUltimo.getInt("iCveObservacion") + 1;
         //debug("despues maximo");
     }else
       maximo = 1;
           //    debug("despues else");
  //   vData.addPK(vData.getString("iEjercicio"));
     vData.addPK(vData.getString("iCveObservacion"));
             //debug("despues agregar primarias ");
     //...

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveObservacion"));
     lPStmt.setString(2,vData.getString("cObsRegistrada"));
     lPStmt.executeUpdate();
     vData = new TDCBBSenalXComB().update(vData,conn);

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
 /********************************************************************/
public TVDinRep insert4(TVDinRep vData,Connection cnNested) throws
    DAOException{
  DbConnection dbConn = null;
  Connection conn = cnNested;
  PreparedStatement lPStmt = null;
  int maximo=1;
  boolean lSuccess = true;
  try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }
    String lSQL =
        "insert into GRLObservacion(iEjercicio,iCveObservacion,cObsRegistrada) values (YEAR(CURRENT DATE),?,?)";
   // debug("ddespues string");

    //String cSql =  "Select distinct year(current date) as ANIO from tracattramite";

   // Vector vcFecha = findByCustom("",cSql);

    //AGREGAR AL ULTIMO ...
    Vector vcData = findByCustom("","select MAX(iCveObservacion) AS iCveObservacion from GRLObservacion where iEjercicio = year(current date)");
       //  debug("antes del vcdata");
    if(vcData.size() > 0){
      //debug("antes del vdinrep"+vData.getInt("iCveObservacion"));
      TVDinRep vUltimo = (TVDinRep) vcData.get(0);
       //debug("antes vdataput"+vUltimo);

       vData.put("iCveObservacion",vUltimo.getInt("iCveObservacion") + 1);
        //debug("antes del obtener maximo");
      maximo=vUltimo.getInt("iCveObservacion") + 1;
        //debug("despues maximo");
    }else
      maximo = 1;
          //    debug("despues else");
 //   vData.addPK(vData.getString("iEjercicio"));
    vData.addPK(vData.getString("iCveObservacion"));
            //debug("despues agregar primarias ");
    //...

    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iCveObservacion"));
    lPStmt.setString(2,vData.getString("cObsRegistrada"));

    System.out.print("obs en obs"+vData.getInt("iCveObservacion"));
    lPStmt.executeUpdate();
    if(cnNested == null){
       conn.commit();
     }

    vData = new TDSOCSupervisionInmueble().update(vData,conn);

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


public TVDinRep insert5(TVDinRep vData,Connection cnNested) throws DAOException{
  DbConnection dbConn = null;
  Connection conn = cnNested;
  PreparedStatement lPStmt = null;
  boolean lSuccess = true;
  TFechas fecha = new TFechas();
  int iAnioActual=fecha.getIntYear(fecha.TodaySQL());
  try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }
    String lSQL =
        "insert into GRLObservacion(iEjercicio,iCveObservacion,cObsRegistrada) values (YEAR(CURRENT DATE),?,?)";

    //AGREGAR AL ULTIMO ...
    Vector vcData = findByCustom("","select MAX(iCveObservacion) AS iCveObservacion from GRLObservacion where iEjercicio = "+iAnioActual );
    if(vcData.size() > 0){
       TVDinRep vUltimo = (TVDinRep) vcData.get(0);
       vData.put("iCveObservacion",vUltimo.getInt("iCveObservacion") + 1);
    }else
       vData.put("iCveObservacion",1);

    vData.put("iEjercicio", iAnioActual);
    vData.addPK(vData.getString("iEjercicio"));
    vData.addPK(vData.getString("iCveObservacion"));
    //...

    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iCveObservacion"));
    lPStmt.setString(2,vData.getString("cObsRegistrada"));
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
   * <p><b> delete from GRLObservacion where iEjercicio = ? AND iCveObservacion = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveObservacion, </b></p>
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
      String lSQL = "delete from GRLObservacion where iEjercicio = ? AND iCveObservacion = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveObservacion"));

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
   * <p><b> update GRLObservacion set cObsRegistrada=? where iEjercicio = ? AND iCveObservacion = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveObservacion, </b></p>
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
      /*String cSql = "select GRLOBSERVACION.ICVEOBSERVACION,COBSREGISTRADA from GRLOBSERVACION "+
                    "join TRAREGTRAMXSOL on grlobservacion.ICVEOBSERVACION = traregtramxsol.ICVEOBSERVACION " +
                    " where traregtramxsol.iejercicio ="+vData.getInt("iEjercicio")+
                    " and inumsolicitud = "+vData.getInt("iNumSoolicitud")+
                    " and ICVETRAMITE = "+vData.getInt("iCveTramite")+
                    " and ICVEMODALIDAD = "+vData.getInt("iCveModalidad");

      Vector vcClave = findByCustom("",cSql);

      vData.put("iCveObservacion",((TVDinRep)vcClave.get(0)).getInt("iCveObservacion"));*/

      String lSQL = "update GRLObservacion set cObsRegistrada=? where iEjercicio = ? AND iCveObservacion = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveObservacion"));
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cObsRegistrada"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iCveObservacion"));
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

 public int findMaxiCveObservacion() throws DAOException{
   DbConnection dbConn = null;
   Connection conn = null;
   Statement lStmt = null;
   ResultSet rs = null;
   int iCveObservacion = 0;
   try{
     dbConn = new DbConnection(dataSourceName);
     conn = dbConn.getConnection();
     lStmt = conn.createStatement();

     String lSQL =
         " select MAX(iCveObservacion) AS iCveObservacion from GRLObservacion where iEjercicio = year(current date) ";
     rs = lStmt.executeQuery(lSQL);
     while(rs.next()){
       iCveObservacion = rs.getInt("iCveObservacion");
     }
     iCveObservacion++;
   }
   catch(Exception ex){
     ex.printStackTrace();
   } finally{
     try{
       if(lStmt != null){
         lStmt.close();
       }
       if(rs != null){
         rs.close();
       }
       if(conn != null){
         conn.close();
       }
       dbConn.closeConnection();
     }
     catch(Exception ex2){
     }
     return iCveObservacion;
   }
 }


}

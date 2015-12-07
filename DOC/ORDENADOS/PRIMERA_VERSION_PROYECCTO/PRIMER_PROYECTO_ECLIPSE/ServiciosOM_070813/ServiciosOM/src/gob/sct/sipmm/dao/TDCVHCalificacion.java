package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCVHCalificacion.java</p>
 * <p>Description: DAO de la entidad CVHCalificacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDCVHCalificacion
    extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas("44");

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
      cError = e.getMessage();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into CVHCalificacion(iCveVehiculo,iEjercicioCalifica,iMesCalifica,iCveUsuRegistra,dtRegistro,iEjercicioObs,iCveObservacion,lConcluido,iCveUsuValida,dtValidacion) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioCalifica,iMesCalifica, </b></p>
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
          "insert into CVHCalificacion(iCveVehiculo,iEjercicioCalifica,iMesCalifica,iCveUsuRegistra,dtRegistro,iEjercicioObs,iCveObservacion) values (?,?,?,?,(CURRENT DATE),YEAR(CURRENT DATE),?)";

      Vector vcData = findByCustom(""," select * from CVHCalificacion  where " +
                                   " iCveVehiculo = " + String.valueOf(vData.getInt("iCveVehiculo")) +
                                   " AND iEjercicioCalifica = " + String.valueOf(vData.getInt("iEjercicioCalifica")) +
                                   " AND iMesCalifica = " + String.valueOf(vData.getInt("iMesCalifica")) + " ");
      if(vcData.size() == 0){
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
        lPStmt.setInt(2,vData.getInt("iEjercicioCalifica"));
        lPStmt.setInt(3,vData.getInt("iMesCalifica"));
        //lPStmt.setInt(4,vData.getInt("iCveUsuRegistra"));
        //lPStmt.setDate(5,vData.getDate("dtRegistro"));
        //lPStmt.setInt(6,vData.getInt("iEjercicioObs"));
        lPStmt.setInt(5,vData.getInt("iCveObservacion"));
        //lPStmt.setInt(8,vData.getInt("lConcluido"));
        //lPStmt.setInt(9,vData.getInt("iCveUsuValida"));
        //lPStmt.setDate(10,vData.getDate("dtValidacion"));
        lPStmt.executeUpdate();

        TDCVHDatosCalifica dCVHDatosCalifica = new TDCVHDatosCalifica();
        dCVHDatosCalifica.insert(vData,conn);
      } else{
        TDCVHDatosCalifica dCVHDatosCalifica = new TDCVHDatosCalifica();
        dCVHDatosCalifica.insert(vData,conn);
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

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from CVHCalificacion where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioCalifica,iMesCalifica, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "delete from CVHCalificacion where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(3,vData.getInt("iMesCalifica"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = "" + sqle.getErrorCode();
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
   * <p><b> update CVHCalificacion set iCveUsuRegistra=?, dtRegistro=?, iEjercicioObs=?, iCveObservacion=?, lConcluido=?, iCveUsuValida=?, dtValidacion=? where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioCalifica,iMesCalifica, </b></p>
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
      String lSQL = "update CVHCalificacion set  lConcluido=1, iCveUsuValida=?, dtValidacion=(CURRENT DATE) where CVHCalificacion.iCveVehiculo = ? AND CVHCalificacion.iEjercicioCalifica = ? AND CVHCalificacion.iMesCalifica = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioCalifica"));
      vData.addPK(vData.getString("iMesCalifica"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveUsuRegistra"));
      lPStmt.setInt(2,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(3,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(4,vData.getInt("iMesCalifica"));

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

  public TVDinRep insertUpdateBatch(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    try{
      int iEjercicioObs = 0,
        iCveObservacion = 0;
      TDCVHDatosCalifica dDatosCalifica = new TDCVHDatosCalifica();
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      Vector vcData = findByCustom(""," select * from CVHCalificacion  where " +
                                   " iCveVehiculo = " + vData.getInt("iCveVehiculo") +
                                   " AND iEjercicioCalifica = " + vData.getInt("iEjercicioCalifica") +
                                   " AND iMesCalifica = " + vData.getInt("iMesCalifica") + " ");
      TVDinRep vDataObs = new TVDinRep();
      TDGRLObservacion dObserva = new TDGRLObservacion();
      if(vData.getString("cObsRegistrada") !=  null && !vData.getString("cObsRegistrada").equals("")){
        try{
          if(vData.getInt("iEjercicioObs") <=0)
            vDataObs.put("iEjercicio", vData.getInt("iEjercicioCalifica"));
          else
            vDataObs.put("iEjercicio", vData.getInt("iEjercicioObs"));
          vDataObs.put("iCveObservacion", vData.getInt("iCveObservacion"));
          vDataObs.put("cObsRegistrada", vData.getString("cObsRegistrada"));
          if(vcData.size() == 0)
            vDataObs = dObserva.insert4(vDataObs,conn);
          else if (vData.getInt("iEjercicioObs") <= 0 && vData.getInt("iCveObservacion") <= 0)
            vDataObs = dObserva.insert4(vDataObs,conn);
          else
            vDataObs = dObserva.update(vDataObs,conn);
          if(vDataObs != null){
            iEjercicioObs = vDataObs.getInt("iEjercicio");
            iCveObservacion = vDataObs.getInt("iCveObservacion");
          }
        }catch(Exception e){
          iEjercicioObs = 0;
          iCveObservacion = 0;
        }
      }else{
        iEjercicioObs = 0;
        iCveObservacion = 0;
      }
      vData.put("iEjercicioObs", iEjercicioObs+"");
      vData.put("iCveObservacion",iCveObservacion+"");
      if(vcData.size() == 0){
        String lSQL = "insert into CVHCalificacion(iCveVehiculo,iEjercicioCalifica,iMesCalifica," +
                      "iEjercicioObs,iCveObservacion,lConcluido) values (?,?,?,?,?,?)";
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
        lPStmt.setInt(2,vData.getInt("iEjercicioCalifica"));
        lPStmt.setInt(3,vData.getInt("iMesCalifica"));
        lPStmt.setInt(4,iEjercicioObs);
        lPStmt.setInt(5,iCveObservacion);
        lPStmt.setInt(6,vData.getInt("lConcluido"));
        lPStmt.executeUpdate();
      }else{
        String lSQL = "update CVHCalificacion set iEjercicioObs=?,"+
                      "iCveObservacion=?, lConcluido=? " +
                      "where iCveVehiculo=? and iEjercicioCalifica=? and iMesCalifica=? ";
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,iEjercicioObs);
        lPStmt.setInt(2,iCveObservacion);
        lPStmt.setInt(3,vData.getInt("lConcluido"));
        lPStmt.setInt(4,vData.getInt("iCveVehiculo"));
        lPStmt.setInt(5,vData.getInt("iEjercicioCalifica"));
        lPStmt.setInt(6,vData.getInt("iMesCalifica"));
        lPStmt.executeUpdate();
      }
      //Inserta o actualiza datos de calificación (Nivel)
      try{
      dDatosCalifica.insertUpdateBatch(vData,conn);
      }catch(Exception e){
        e.printStackTrace();
      }
      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

  public TVDinRep concluyeReporte(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update CVHCalificacion set lConcluido=?, iCveUsuRegistra=?, dtRegistro=? where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioCalifica"));
      vData.addPK(vData.getString("iMesCalifica"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, 0);
      lPStmt.setInt(2,vData.getInt("iCveUsuRegistra"));
      lPStmt.setDate(3, tFecha.TodaySQL());
      lPStmt.setInt(4,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(5,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(6,vData.getInt("iMesCalifica"));

      lPStmt.executeUpdate();
      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

  public TVDinRep rechazaReporte(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update CVHCalificacion set lConcluido=?, iCveUsuRegistra=?, dtRegistro=? where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioCalifica"));
      vData.addPK(vData.getString("iMesCalifica"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, 0);
      lPStmt.setNull(2,Types.INTEGER);
      lPStmt.setNull(3, Types.DATE);
      lPStmt.setInt(4,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(5,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(6,vData.getInt("iMesCalifica"));

      lPStmt.executeUpdate();
      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

  public TVDinRep validaConcluye(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update CVHCalificacion set lConcluido=?, iCveUsuValida=?, dtValidacion=? where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioCalifica"));
      vData.addPK(vData.getString("iMesCalifica"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, 1);
      lPStmt.setInt(2,vData.getInt("iCveUsuValida"));
      lPStmt.setDate(3, tFecha.TodaySQL());
      lPStmt.setInt(4,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(5,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(6,vData.getInt("iMesCalifica"));

      lPStmt.executeUpdate();
      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return vData;
    }
  }

}

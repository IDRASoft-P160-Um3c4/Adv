package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCVHDatosRepMaqAux.java</p>
 * <p>Description: DAO de la entidad CVHDatosRepMaqAux</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDCVHDatosRepMaqAux
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
   * <p><b> insert into CVHDatosRepMaqAux(iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoMaquinaAux,dtAsignacion,lEnServicio,iHorasTrabajadas) values (?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoMaquinaAux,dtAsignacion, </b></p>
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
          "insert into CVHDatosRepMaqAux(iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoMaquinaAux,dtAsignacion,lEnServicio,iHorasTrabajadas) values (?,?,?,?,?,?,?)";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(3,vData.getInt("iMesReporte"));

      lPStmt.setInt(4,vData.getInt("iCveTipoMaquinaAux"));
      lPStmt.setDate(5,vData.getDate("dtAsignacion"));
      lPStmt.setInt(6,vData.getInt("lEnServicio"));
      lPStmt.setInt(7,vData.getInt("iHorasTrabajadas"));
      lPStmt.executeUpdate();
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

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from CVHDatosRepMaqAux where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoMaquinaAux = ? AND dtAsignacion = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoMaquinaAux,dtAsignacion, </b></p>
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
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL = "delete from CVHDatosRepMaqAux where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoMaquinaAux = ? AND dtAsignacion = ?  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(3,vData.getInt("iMesReporte"));
      lPStmt.setInt(4,vData.getInt("iCveTipoMaquinaAux"));
      lPStmt.setDate(5,vData.getDate("dtAsignacion"));

      lPStmt.executeUpdate();
      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
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
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("delete.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);
      return lSuccess;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update CVHDatosRepMaqAux set lEnServicio=?, iHorasTrabajadas=? where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoMaquinaAux = ? AND dtAsignacion = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoMaquinaAux,dtAsignacion, </b></p>
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
      String lSQL = "update CVHDatosRepMaqAux set lEnServicio=?, iHorasTrabajadas=? where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoMaquinaAux = ? AND dtAsignacion = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioReporte"));
      vData.addPK(vData.getString("iMesReporte"));
      vData.addPK(vData.getString("iCveTipoMaquinaAux"));
      vData.addPK(vData.getString("dtAsignacion"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lEnServicio"));
      lPStmt.setInt(2,vData.getInt("iHorasTrabajadas"));
      lPStmt.setInt(3,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(4,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(5,vData.getInt("iMesReporte"));
      lPStmt.setInt(6,vData.getInt("iCveTipoMaquinaAux"));
      lPStmt.setDate(7,vData.getDate("dtAsignacion"));
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

  public TVDinRep insertUpdateBatch(TVDinRep vData,Connection cnNested) throws
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
      Vector vcValida = new Vector();
      String[] aTipoMaqAux  = vData.getString("cCveTipoMaquinaAux").split(",");
      String[] aFecAsigna  = vData.getString("cFechaAsignacion").split(",");
      String[] aEnServicio = vData.getString("cEnServicio").split(",");
      String[] aHorasTrab  = vData.getString("cHorasTrabajadas").split(",");
      TVDinRep vTemp;
      if(aTipoMaqAux.length != aFecAsigna.length || aTipoMaqAux.length != aEnServicio.length ||
         aTipoMaqAux.length != aHorasTrab.length)
        cError = "No se proporcionaron claves de máquina auxiliar, fecha de asignación, si esta en servicio y horas trabajadas de forma equivalente";
      else{
        for(int i = 0;i < aTipoMaqAux.length;i++){
          vcValida = findByCustom(""," select lEnServicio from CVHDatosRepMaqAux  where " +
                                  " iCveVehiculo = " + vData.getInt("iCveVehiculo") +
                                  " AND iEjercicioReporte = " + vData.getInt("iEjercicioReporte") +
                                  " AND iMesReporte = " + vData.getInt("iMesReporte") +
                                  " AND iCveTipoMaquinaAux = " + Integer.parseInt(aTipoMaqAux[i],10) +
                                  " AND dtAsignacion   = '" + tFecha.getFechaYYYYMMDD(tFecha.getDateSQL(aFecAsigna[i]),"-") + "'");
          vTemp = new TVDinRep();
          vTemp.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
          vTemp.put("iEjercicioReporte",vData.getInt("iEjercicioReporte"));
          vTemp.put("iMesReporte",vData.getInt("iMesReporte"));
          vTemp.put("iCveTipoMaquinaAux",Integer.parseInt(aTipoMaqAux[i],10));
          vTemp.put("dtAsignacion",tFecha.getDateSQL(aFecAsigna[i]));
          vTemp.put("lEnServicio",Integer.parseInt(aEnServicio[i],10));
          vTemp.put("iHorasTrabajadas",Integer.parseInt(aHorasTrab[i],10));
          if(vcValida != null && vcValida.size() > 0)
            this.update(vTemp,conn);
          else
            this.insert(vTemp,conn);
        }
      }

      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      ex.printStackTrace();
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

}

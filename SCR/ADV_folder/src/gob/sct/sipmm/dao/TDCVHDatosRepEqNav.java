package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCVHDatosRepEqNav.java</p>
 * <p>Description: DAO de la entidad CVHDatosRepEqNav</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDCVHDatosRepEqNav
    extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas("44");

  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepci�n de tipo DAO
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
   * <p><b> insert into CVHDatosRepEqNav(iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoEquipoNav,dtAsignacion,lEnServicio) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: dtAsignacion,iCveTipoEquipoNav,iMesReporte,iEjercicioReporte,iCveVehiculo, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - VO Din�mico que contiene a la entidad a Insertada, as� como a la llave de esta entidad.
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
          "insert into CVHDatosRepEqNav(iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoEquipoNav,dtAsignacion,lEnServicio) values (?,?,?,?,?,?)";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(3,vData.getInt("iMesReporte"));
      lPStmt.setInt(4,vData.getInt("iCveTipoEquipoNav"));
      lPStmt.setDate(5,vData.getDate("dtAsignacion"));
      lPStmt.setInt(6,vData.getInt("lEnServicio"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
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
   * Elimina al registro a trav�s de la entidad dada por vData.
   * <p><b> delete from CVHDatosRepEqNav where dtAsignacion = ? AND iCveTipoEquipoNav = ? AND iMesReporte = ? AND iEjercicioReporte = ? AND iCveVehiculo = ?  </b></p>
   * <p><b> Campos Llave: dtAsignacion,iCveTipoEquipoNav,iMesReporte,iEjercicioReporte,iCveVehiculo, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
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

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from CVHDatosRepEqNav where dtAsignacion = ? AND iCveTipoEquipoNav = ? AND iMesReporte = ? AND iEjercicioReporte = ? AND iCveVehiculo = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setDate(1,vData.getDate("dtAsignacion"));
      lPStmt.setInt(2,vData.getInt("iCveTipoEquipoNav"));
      lPStmt.setInt(3,vData.getInt("iMesReporte"));
      lPStmt.setInt(4,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(5,vData.getInt("iCveVehiculo"));

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
   * <p><b> update CVHDatosRepEqNav set lEnServicio=? where dtAsignacion = ? AND iCveTipoEquipoNav = ? AND iMesReporte = ? AND iEjercicioReporte = ? AND iCveVehiculo = ?  </b></p>
   * <p><b> Campos Llave: dtAsignacion,iCveTipoEquipoNav,iMesReporte,iEjercicioReporte,iCveVehiculo, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
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
      String lSQL = "update CVHDatosRepEqNav set lEnServicio=? where dtAsignacion = ? AND iCveTipoEquipoNav = ? AND iMesReporte = ? AND iEjercicioReporte = ? AND iCveVehiculo = ? ";

      vData.addPK(vData.getString("dtAsignacion"));
      vData.addPK(vData.getString("iCveTipoEquipoNav"));
      vData.addPK(vData.getString("iMesReporte"));
      vData.addPK(vData.getString("iEjercicioReporte"));
      vData.addPK(vData.getString("iCveVehiculo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lEnServicio"));
      lPStmt.setDate(2,vData.getDate("dtAsignacion"));
      lPStmt.setInt(3,vData.getInt("iCveTipoEquipoNav"));
      lPStmt.setInt(4,vData.getInt("iMesReporte"));
      lPStmt.setInt(5,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(6,vData.getInt("iCveVehiculo"));
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
      String[] aTipoEqNav  = vData.getString("cCveTipoEquipoNav").split(",");
      String[] aFecAsigna  = vData.getString("cFechaAsignacion").split(",");
      String[] aEnServicio = vData.getString("cEnServicio").split(",");
      TVDinRep vTemp;
      if(aTipoEqNav.length != aFecAsigna.length || aTipoEqNav.length != aEnServicio.length)
        cError = "No se proporcionaron claves de tipo de equipo, fecha de asignaci�n y si esta en servicio de forma equivalente";
      else{
        for(int i = 0;i < aTipoEqNav.length;i++){
          String cSQL1 = " select lEnServicio from CVHDatosRepEqNav  where " +
                         " iCveVehiculo = " + vData.getInt("iCveVehiculo") +
                         " AND iEjercicioReporte = " + vData.getInt("iEjercicioReporte") +
                         " AND iMesReporte = " + vData.getInt("iMesReporte") +
                         " AND iCveTipoEquipoNav = " + Integer.parseInt(aTipoEqNav[i],10);
          cSQL1 += ((!aFecAsigna[i].equals("") && aFecAsigna[i]!=null) ? " AND dtAsignacion   = '" + tFecha.getFechaYYYYMMDD(tFecha.getDateSQL(aFecAsigna[i]),"-") + "'":"");
          vcValida = findByCustom("",cSQL1);
          vTemp = new TVDinRep();
          vTemp.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
          vTemp.put("iEjercicioReporte",vData.getInt("iEjercicioReporte"));
          vTemp.put("iMesReporte",vData.getInt("iMesReporte"));
          vTemp.put("iCveTipoEquipoNav",Integer.parseInt(aTipoEqNav[i],10));
          vTemp.put("dtAsignacion",tFecha.getDateSQL(aFecAsigna[i]));
          vTemp.put("lEnServicio",Integer.parseInt(aEnServicio[i],10));
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

package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCVHDatosRepSeg.java</p>
 * <p>Description: DAO de la entidad CVHDatosRepSeg</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDCVHDatosRepSeg
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
   * <p><b> insert into CVHDatosRepSeg(iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoEquipoSeg,iCantidad,dtExpiracion,lCompleto,iFaltante,lEnServicio) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoEquipoSeg, </b></p>
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
          "INSERT INTO CVHDATOSREPSEG " +
          "(ICVEVEHICULO,IEJERCICIOREPORTE,IMESREPORTE,ICVETIPOEQUIPOSEG,ICVETIPOVEH,ICANTIDAD,DTEXPIRACION,LCOMPLETO,IFALTANTE,LENSERVICIO) " +
          "VALUES (?,?,?,?,?,?,?,?,?,?) ";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(3,vData.getInt("iMesReporte"));
      lPStmt.setInt(4,vData.getInt("iCveTipoEquipoSeg"));
      lPStmt.setInt(5,vData.getInt("iCveTipoVeh"));
      lPStmt.setInt(6,vData.getInt("iCantidad"));
      if(vData.getDate("dtExpiracion") == null)
        lPStmt.setNull(7, Types.DATE);
      else
        lPStmt.setDate(7,vData.getDate("dtExpiracion"));
      lPStmt.setInt(8,vData.getInt("lCompleto"));
      lPStmt.setInt(9,vData.getInt("iFaltante"));
      lPStmt.setInt(10,vData.getInt("lEnServicio"));
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
   * <p><b> delete from CVHDatosRepSeg where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoEquipoSeg = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoEquipoSeg, </b></p>
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

      String lSQL = "delete from CVHDatosRepSeg where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoEquipoSeg = ?  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(3,vData.getInt("iMesReporte"));
      lPStmt.setInt(4,vData.getInt("iCveTipoEquipoSeg"));

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
   * <p><b> update CVHDatosRepSeg set iCantidad=?, dtExpiracion=?, lCompleto=?, iFaltante=?, lEnServicio=? where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoEquipoSeg = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioReporte,iMesReporte,iCveTipoEquipoSeg, </b></p>
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
      String lSQL = "update CVHDatosRepSeg set iCantidad=?, dtExpiracion=?, lCompleto=?, iFaltante=?, lEnServicio=? where iCveVehiculo = ? AND iEjercicioReporte = ? AND iMesReporte = ? AND iCveTipoEquipoSeg = ? AND iCveTipoVeh = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioReporte"));
      vData.addPK(vData.getString("iMesReporte"));
      vData.addPK(vData.getString("iCveTipoEquipoSeg"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCantidad"));
      if(vData.getDate("dtExpiracion") == null)
        lPStmt.setNull(2, Types.DATE);
      else
        lPStmt.setDate(2,vData.getDate("dtExpiracion"));
      lPStmt.setInt(3,vData.getInt("lCompleto"));
      lPStmt.setInt(4,vData.getInt("iFaltante"));
      lPStmt.setInt(5,vData.getInt("lEnServicio"));
      lPStmt.setInt(6,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(7,vData.getInt("iEjercicioReporte"));
      lPStmt.setInt(8,vData.getInt("iMesReporte"));
      lPStmt.setInt(9,vData.getInt("iCveTipoEquipoSeg"));
      lPStmt.setInt(10,vData.getInt("iCveTipoVeh"));
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
      String[] aTipoEqSeg  = vData.getString("cCveTipoEquipoSeg").split(",");
      String[] aEnServicio = vData.getString("cEnServicio").split(",");
      String[] aCantidad = vData.getString("cCantidad").split(",");
      String[] aFechaExpira = vData.getString("cFechaExpiracion").split(",");
      String[] aCompleto = vData.getString("cCompleto").split(",");
      String[] aFaltante = vData.getString("cFaltante").split(",");
      TVDinRep vTemp;
      if(aTipoEqSeg.length != aEnServicio.length || aTipoEqSeg.length != aCantidad.length ||
         aTipoEqSeg.length != aFechaExpira.length || aTipoEqSeg.length != aCompleto.length ||
         aTipoEqSeg.length != aFaltante.length)
        cError = "No se proporcionaron claves de tipo de equipo, fecha de asignación y si esta en servicio de forma equivalente";
      else{
        for(int i = 0;i < aTipoEqSeg.length;i++){
          vcValida = findByCustom(""," select lEnServicio from CVHDatosRepSeg  where " +
                                  " iCveVehiculo = " + vData.getInt("iCveVehiculo") +
                                  " AND iEjercicioReporte = " + vData.getInt("iEjercicioReporte") +
                                  " AND iMesReporte = " + vData.getInt("iMesReporte") +
                                  " AND iCveTipoEquipoSeg = " + Integer.parseInt(aTipoEqSeg[i],10));
          vTemp = new TVDinRep();
          vTemp.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
          vTemp.put("iEjercicioReporte",vData.getInt("iEjercicioReporte"));
          vTemp.put("iMesReporte",vData.getInt("iMesReporte"));
          vTemp.put("iCveTipoEquipoSeg",Integer.parseInt(aTipoEqSeg[i],10));
          vTemp.put("iCantidad",Integer.parseInt(aCantidad[i],10));
          vTemp.put("iCveTipoVeh",vData.getInt("iCveTipoVeh"));
          if(aFechaExpira[i] != null && !aFechaExpira[i].equals("") && !aFechaExpira[i].equals("NO APLICA"))
            vTemp.put("dtExpiracion",tFecha.getDateSQL(aFechaExpira[i]));
          else
            vTemp.put("dtExpiracion", (java.sql.Date)null);
          vTemp.put("lCompleto",Integer.parseInt(aCompleto[i],10));
          vTemp.put("iFaltante",Integer.parseInt(aFaltante[i],10));
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

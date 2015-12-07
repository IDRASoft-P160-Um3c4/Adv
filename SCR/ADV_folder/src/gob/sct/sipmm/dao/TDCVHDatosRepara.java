package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCVHDatosRepara.java</p>
 * <p>Description: DAO de la entidad CVHDatosRepara</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDCVHDatosRepara
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
   * <p><b> insert into CVHDatosRepara(iCveVehiculo,iEjercicioRepara,iMesRepara,iCveTipoVeh,iCveTipoRepara,lEfectuadaPendiente,dtReparacion,dImporte) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioRepara,iMesRepara,iCveTipoVeh,iCveTipoRepara, </b></p>
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
          "insert into CVHDatosRepara(iCveVehiculo,iEjercicioRepara,iMesRepara,iCveTipoVeh,iCveTipoRepara,lEfectuadaPendiente,dtReparacion,dImporte) values (?,?,?,?,?,?,?,?)";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioRepara"));
      lPStmt.setInt(3,vData.getInt("iMesRepara"));
      lPStmt.setInt(4,vData.getInt("iCveTipoVeh"));
      lPStmt.setInt(5,vData.getInt("iCveTipoRepara"));
      lPStmt.setInt(6,vData.getInt("lEfectuadaPendiente"));
      if(vData.getString("dtReparacion") == null || vData.getString("dtReparacion").equals(""))
        lPStmt.setNull(7, Types.DATE);
      else
        lPStmt.setDate(7,vData.getDate("dtReparacion"));
      lPStmt.setFloat(8,vData.getFloat("dImporte"));

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
   * <p><b> delete from CVHDatosRepara where iCveVehiculo = ? AND iEjercicioRepara = ? AND iMesRepara = ? AND iCveTipoVeh = ? AND iCveTipoRepara = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioRepara,iMesRepara,iCveTipoVeh,iCveTipoRepara, </b></p>
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

      String lSQL = "delete from CVHDatosRepara where iCveVehiculo = ? AND iEjercicioRepara = ? AND iMesRepara = ? AND iCveTipoVeh = ? AND iCveTipoRepara = ?  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioRepara"));
      lPStmt.setInt(3,vData.getInt("iMesRepara"));
      lPStmt.setInt(4,vData.getInt("iCveTipoVeh"));
      lPStmt.setInt(5,vData.getInt("iCveTipoRepara"));

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
   * <p><b> update CVHDatosRepara set lEfectuadaPendiente=?, dtReparacion=?, dImporte=? where iCveVehiculo = ? AND iEjercicioRepara = ? AND iMesRepara = ? AND iCveTipoVeh = ? AND iCveTipoRepara = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioRepara,iMesRepara,iCveTipoVeh,iCveTipoRepara, </b></p>
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
      String lSQL = "update CVHDatosRepara set lEfectuadaPendiente=?, dtReparacion=?, dImporte=? where iCveVehiculo = ? AND iEjercicioRepara = ? AND iMesRepara = ? AND iCveTipoVeh = ? AND iCveTipoRepara = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioRepara"));
      vData.addPK(vData.getString("iMesRepara"));
      vData.addPK(vData.getString("iCveTipoVeh"));
      vData.addPK(vData.getString("iCveTipoRepara"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lEfectuadaPendiente"));
      if(vData.getString("dtReparacion") == null || vData.getString("dtReparacion").equals(""))
        lPStmt.setNull(2, Types.DATE);
      else
        lPStmt.setDate(2,vData.getDate("dtReparacion"));
      lPStmt.setFloat(3,vData.getFloat("dImporte"));
      lPStmt.setInt(4,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(5,vData.getInt("iEjercicioRepara"));
      lPStmt.setInt(6,vData.getInt("iMesRepara"));
      lPStmt.setInt(7,vData.getInt("iCveTipoVeh"));
      lPStmt.setInt(8,vData.getInt("iCveTipoRepara"));

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
      String[] aTipoRep  = vData.getString("cCveTipoRepara").split(",");
      String[] aEfecPen  = vData.getString("cEfectuadaPendiente").split(",");
      String[] aFechaRep = vData.getString("cFechaReparacion").split(",");
      String[] aImporte  = vData.getString("cImporte").split(",");
      TVDinRep vTemp;
      if(aTipoRep.length != aEfecPen.length || aTipoRep.length != aFechaRep.length || aTipoRep.length != aImporte.length)
        cError = "No se proporcionaron claves de reparación, pendiente o efectuada, fecha e importes equivalentes";
      else{
        for(int i = 0;i < aTipoRep.length;i++){
          vcValida = findByCustom(""," select lEfectuadaPendiente from CVHDatosRepara  where " +
                                  " iCveVehiculo = " + vData.getInt("iCveVehiculo") +
                                  " AND iEjercicioRepara = " + vData.getInt("iEjercicioRepara") +
                                  " AND iMesRepara = " + vData.getInt("iMesRepara") +
                                  " AND iCveTipoVeh = " + vData.getInt("iCveTipoVeh") +
                                  " AND iCveTipoRepara   = " + Integer.parseInt(aTipoRep[i],10));
          vTemp = new TVDinRep();
          vTemp.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
          vTemp.put("iEjercicioRepara",vData.getInt("iEjercicioRepara"));
          vTemp.put("iMesRepara",vData.getInt("iMesRepara"));
          vTemp.put("iCveTipoVeh",vData.getInt("iCveTipoVeh"));
          vTemp.put("iCveTipoRepara",Integer.parseInt(aTipoRep[i],10));
          vTemp.put("lEfectuadaPendiente",Integer.parseInt(aEfecPen[i],10));
          vTemp.put("dtReparacion",aFechaRep[i]);
          vTemp.put("dImporte",Double.parseDouble(aImporte[i]));
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

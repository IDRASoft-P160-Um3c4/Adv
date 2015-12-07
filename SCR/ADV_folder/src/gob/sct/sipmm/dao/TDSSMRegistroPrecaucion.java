package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDSSMRegistroPrecaucion.java</p>
 * <p>Description: DAO de la entidad SSMRegistroPrecaucion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author d a<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDSSMRegistroPrecaucion
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
   * <p><b> insert into SSMRegistroPrecaucion(iEjercicio,tsRegistro,iCveUsuario,iCveOficinaReporta,cExternoReporta,lAplicaLitoral,lAplicaCapitania,iCveOficinaAplica,dtIniPrecaucion,dtFinPrecaucion,iEjercicioFenomeno,iNumFenomeno,iEjercicioPrecaucion,iDscPrecaucion,iCveTipoPrecaucion) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,tsRegistro, </b></p>
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
    int iEjercicioObs = 0,
        iCveObservacion = 0;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into SSMRegistroPrecaucion(iEjercicio,tsRegistro,iCveUsuario,iCveOficinaReporta,"+
                       "cExternoReporta,lAplicaLitoral,lAplicaCapitania,iCveOficinaAplica,dtIniPrecaucion,"+
                       "dtFinPrecaucion,iEjercicioFenomeno,iNumFenomeno,iEjercicioPrecaucion,iDscPrecaucion,"+
                       "iCveTipoPrecaucion,iCveLitoral) "+
                       "values (YEAR(CURRENT DATE),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("tsRegistro"));

      // Inserta observacion para relacionar en seguimiento
      TVDinRep vDataObs = new TVDinRep();
      TDGRLObservacion dObserva = new TDGRLObservacion();
      if(vData.getString("cObsRegistrada") != null && !vData.getString("cObsRegistrada").equals("")){
        try{
          vDataObs.put("cObsRegistrada",vData.getString("cObsRegistrada"));
          vDataObs = dObserva.insert4(vDataObs,conn);
          if(vDataObs != null){
            iEjercicioObs = vDataObs.getInt("iEjercicio");
            iCveObservacion = vDataObs.getInt("iCveObservacion");
          }
        } catch(Exception e){
          iEjercicioObs = 0;
          iCveObservacion = 0;
        }
      } else{
        iEjercicioObs = 0;
        iCveObservacion = 0;
      }
      vData.put("iEjercicioPrecaucion",tFecha.getIntYear(tFecha.TodaySQL())+"");
      vData.put("iDscPrecaucion",iCveObservacion + "");

      lPStmt = conn.prepareStatement(lSQL);
      java.sql.Timestamp tActual = tFecha.getThisTime();
      java.sql.Date dtActual = tFecha.getDateSQL(tActual);
      lPStmt.setTimestamp(1, tFecha.getTimeStamp(tFecha.getIntYear(dtActual),
                                                 tFecha.getIntMonth(dtActual),
                                                 tFecha.getIntDay(dtActual),
                                                 tFecha.getHora24H(tActual),
                                                 tFecha.getMinuto(tActual)));
      lPStmt.setInt(2,vData.getInt("iCveUsuario"));
      if(vData.getInt("iCveOficinaReporta") <= 0)
        lPStmt.setNull(3, Types.INTEGER);
      else
        lPStmt.setInt(3,vData.getInt("iCveOficinaReporta"));
      if(vData.getString("cExternoReporta") == null)
        lPStmt.setNull(4, Types.VARCHAR);
      else
        lPStmt.setString(4,vData.getString("cExternoReporta"));
      lPStmt.setInt(5,vData.getInt("lAplicaLitoral"));
      lPStmt.setInt(6,vData.getInt("lAplicaCapitania"));
      if(vData.getInt("lAplicaCapitania") == 1)
        lPStmt.setInt(7,vData.getInt("iCveOficinaAplica"));
      else
        lPStmt.setNull(7, Types.INTEGER);
      lPStmt.setDate(8,vData.getDate("dtIniPrecaucion"));
      lPStmt.setDate(9,vData.getDate("dtFinPrecaucion"));
      if(vData.getInt("iEjercicioFenomeno") >= 0 && vData.getInt("iNumFenomeno") >= 0){
        lPStmt.setInt(10,vData.getInt("iEjercicioFenomeno"));
        lPStmt.setInt(11,vData.getInt("iNumFenomeno"));
      }else{
        lPStmt.setNull(10,Types.INTEGER);
        lPStmt.setNull(11,Types.INTEGER);
      }
      lPStmt.setInt(12,vData.getInt("iEjercicioPrecaucion"));
      lPStmt.setInt(13,vData.getInt("iDscPrecaucion"));
      lPStmt.setInt(14,vData.getInt("iCveTipoPrecaucion"));
      if(vData.getInt("lAplicaLitoral") == 1)
        lPStmt.setInt(15,vData.getInt("iCveLitoral"));
      else
        lPStmt.setNull(15,Types.INTEGER);
      lPStmt.executeUpdate();

      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      ex.printStackTrace();
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      ex.printStackTrace();
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
   * <p><b> delete from SSMRegistroPrecaucion where iEjercicio = ? AND tsRegistro = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,tsRegistro, </b></p>
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

      String lSQL = "delete from SSMRegistroPrecaucion where iEjercicio = ? AND tsRegistro = ?  ";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setTimestamp(2,tFecha.getTimeStamp(vData.getString("tsRegistro").replace('-',' ')+":00"));

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
   * <p><b> update SSMRegistroPrecaucion set iCveUsuario=?, iCveOficinaReporta=?, cExternoReporta=?, lAplicaLitoral=?, lAplicaCapitania=?, iCveOficinaAplica=?, dtIniPrecaucion=?, dtFinPrecaucion=?, iEjercicioFenomeno=?, iNumFenomeno=?, iEjercicioPrecaucion=?, iDscPrecaucion=?, iCveTipoPrecaucion=? where iEjercicio = ? AND tsRegistro = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,tsRegistro, </b></p>
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
      String lSQL = "update SSMRegistroPrecaucion set iCveUsuario=?, iCveOficinaReporta=?, cExternoReporta=?, lAplicaLitoral=?, lAplicaCapitania=?, iCveOficinaAplica=?, dtIniPrecaucion=?, dtFinPrecaucion=?, iEjercicioFenomeno=?, iNumFenomeno=?, iEjercicioPrecaucion=?, iDscPrecaucion=?, iCveTipoPrecaucion=? , iCveLitoral=?where iEjercicio = ? AND tsRegistro = ?  ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("tsRegistro"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveUsuario"));
      lPStmt.setInt(2,vData.getInt("iCveOficinaReporta"));
      lPStmt.setString(3,vData.getString("cExternoReporta"));
      lPStmt.setInt(4,vData.getInt("lAplicaLitoral"));
      lPStmt.setInt(5,vData.getInt("lAplicaCapitania"));
      lPStmt.setInt(6,vData.getInt("iCveOficinaAplica"));
      lPStmt.setDate(7,vData.getDate("dtIniPrecaucion"));
      lPStmt.setDate(8,vData.getDate("dtFinPrecaucion"));
      lPStmt.setInt(9,vData.getInt("iEjercicioFenomeno"));
      lPStmt.setInt(10,vData.getInt("iNumFenomeno"));
      lPStmt.setInt(11,vData.getInt("iEjercicioPrecaucion"));
      lPStmt.setInt(12,vData.getInt("iDscPrecaucion"));
      lPStmt.setInt(13,vData.getInt("iCveTipoPrecaucion"));
      lPStmt.setInt(14,vData.getInt("iEjercicio"));
      lPStmt.setString(15,vData.getString("tsRegistro"));
      lPStmt.setInt(16,vData.getInt("iCveLitoral"));
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

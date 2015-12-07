package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>Title: TDCVHDatosCalifica.java</p>
 * <p>Description: DAO de la entidad CVHDatosCalifica</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDCVHDatosCalifica
    extends DAOBase{
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
      cError = e.getMessage();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into CVHDatosCalifica(iCveVehiculo,iEjercicioCalifica,iMesCalifica,iCveTipoVeh,iCveParte,iCveNivel) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioCalifica,iMesCalifica,iCveTipoVeh,iCveParte, </b></p>
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
          "insert into CVHDatosCalifica(iCveVehiculo,iEjercicioCalifica,iMesCalifica,iCveTipoVeh,iCveParte,iCveNivel) values (?,?,?,?,?,?)";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(3,vData.getInt("iMesCalifica"));
      lPStmt.setInt(4,vData.getInt("iCveTipoVeh"));
      lPStmt.setInt(5,vData.getInt("iCveParte"));
      lPStmt.setInt(6,vData.getInt("iCveNivel"));
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
   * <p><b> delete from CVHDatosCalifica where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ? AND iCveTipoVeh = ? AND iCveParte = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioCalifica,iMesCalifica,iCveTipoVeh,iCveParte, </b></p>
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

      String lSQL = "delete from CVHDatosCalifica where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ? AND iCveTipoVeh = ? AND iCveParte = ?  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(3,vData.getInt("iMesCalifica"));
      lPStmt.setInt(4,vData.getInt("iCveTipoVeh"));
      lPStmt.setInt(5,vData.getInt("iCveParte"));

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
   * <p><b> update CVHDatosCalifica set iCveNivel=? where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ? AND iCveTipoVeh = ? AND iCveParte = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iEjercicioCalifica,iMesCalifica,iCveTipoVeh,iCveParte, </b></p>
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
      String lSQL = "update CVHDatosCalifica set iCveNivel=? where iCveVehiculo = ? AND iEjercicioCalifica = ? AND iMesCalifica = ? AND iCveTipoVeh = ? AND iCveParte = ? ";

      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iEjercicioCalifica"));
      vData.addPK(vData.getString("iMesCalifica"));
      vData.addPK(vData.getString("iCveTipoVeh"));
      vData.addPK(vData.getString("iCveParte"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveNivel"));
      lPStmt.setInt(2,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(3,vData.getInt("iEjercicioCalifica"));
      lPStmt.setInt(4,vData.getInt("iMesCalifica"));
      lPStmt.setInt(5,vData.getInt("iCveTipoVeh"));
      lPStmt.setInt(6,vData.getInt("iCveParte"));
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
      String[] aPartes = vData.getString("cCveParte").split(",");
      String[] aNiveles = vData.getString("cCveNivel").split(",");
      TVDinRep vTemp;
      if(aPartes.length != aNiveles.length)
        cError = "No se proporcionaron claves de parte y nivel equivalentes";
      else{
        for(int i = 0;i < aPartes.length;i++){
          vcValida = findByCustom(""," select iCveNivel from CVHDatosCalifica  where " +
                                  " iCveVehiculo = " + vData.getInt("iCveVehiculo") +
                                  " AND iEjercicioCalifica = " + vData.getInt("iEjercicioCalifica") +
                                  " AND iMesCalifica = " + vData.getInt("iMesCalifica") +
                                  " AND iCveTipoVeh = " + vData.getInt("iCveTipoVeh") +
                                  " AND iCveParte   = " + Integer.parseInt(aPartes[i],10));
          vTemp = new TVDinRep();
          vTemp.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
          vTemp.put("iEjercicioCalifica",vData.getInt("iEjercicioCalifica"));
          vTemp.put("iMesCalifica",vData.getInt("iMesCalifica"));
          vTemp.put("iCveTipoVeh",vData.getInt("iCveTipoVeh"));
          vTemp.put("iCveParte",Integer.parseInt(aPartes[i],10));
          vTemp.put("iCveNivel",Integer.parseInt(aNiveles[i],10));
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

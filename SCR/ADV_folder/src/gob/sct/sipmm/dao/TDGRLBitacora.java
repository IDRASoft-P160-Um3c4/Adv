package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCRCMensaje.java</p>
 * <p>Description: DAO de la entidad GRLBitacora</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: INFOTEC </p>
 * @author aLopez
 * @version 1.0
 */
public class TDGRLBitacora
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
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into CRCMensaje(dtMensaje,iNumMensaje,iCveUsuRegistra,tsRegistro,iCvePrioridad,cMensaje,iCveEstacion,cDscProcedencia,lCanalizado,lSeguimientoConcluido) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: dtMensaje,iNumMensaje, </b></p>
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
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    TFechas Fechas = new TFechas();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      vData.put("tsMovimiento",fecha.getThisTime());
      String lSQL =
          "INSERT INTO GRLBITACORA (iCveUsuario,tsMovimiento,cTablaAfecta,cLlaveReg,iTipoMov) values(?,'"+vData.getString("tsMovimiento")+"',?,?,?)";
      //...



      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveUsuario"));
      lPStmt.setString(2,vData.getString("cTablaAfecta"));
      lPStmt.setString(3,vData.getString("cLlaveReg"));
      lPStmt.setInt(4,vData.getInt("iTipoMov"));//1:Insert; 2:Update; 3:Delete
      lPStmt.executeUpdate();

      conn.commit();
      if(vData.getString("cRegistro")!=""){
        TVDinRep dMov = new TVDinRep();
        dMov.put("iCveUsuario",vData.getInt("iCveUsuario"));
        dMov.put("tsMovimiento",vData.getTimeStamp("tsMovimiento"));
        dMov.put("cRegistro",vData.getString("cRegistro"));
        this.insertMov(dMov,conn);
      }
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      ex.printStackTrace();
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          e.printStackTrace();
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

  public TVDinRep insertMov(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    TFechas Fechas = new TFechas();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "INSERT INTO GRLBitacoraMov (iCveUsuario,tsMovimiento,cRegistro) values(?,'"+vData.getString("tsMovimiento")+"',?)";

      vData.addPK(vData.getString("iCveUsuario"));
      vData.addPK(vData.getString("tsMovimiento"));
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveUsuario"));
      //lPStmt.setString(2,vData.getString("tsMovimiento"));
      if(vData.getString("cRegistro").length()>=500)
        lPStmt.setString(2,vData.getString("cRegistro").substring(0,499));
      else lPStmt.setString(2,vData.getString("cRegistro"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      ex.printStackTrace();
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

  public TVDinRep insertConsultaMov(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    TFechas Fechas = new TFechas();
    vData.put("tsMovimiento",Fechas.getThisTime());
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "INSERT INTO GRLBitConsEmbarcac (iCveEmbarcacion,tsConsulta,cNomEmbarcacion,iCvePropietario,iCvePoseedor,iNumSolicitudes,"+
                                          "iNumMatriculas,iNumRPMN,iNumInspecciones,iNumCertificados,iNumPermisos,iNumArribosDesp,"+
                                          "iNumMotores,iNumAbanderamiento,iNumComunicaciones,iNumAccidentes,iCveUsuario) "+
                                          "values(?,'"+vData.getString("tsMovimiento")+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveEmbarcacion"));
      lPStmt.setString(2,vData.getString("cNomEmbarcacion"));
      lPStmt.setInt(3,vData.getInt("iCvePropietario"));
      lPStmt.setInt(4,vData.getInt("iCvePoseedor"));
      lPStmt.setInt(5,vData.getInt("iNumSolicitudes"));
      lPStmt.setInt(6,vData.getInt("iNumMatriculas"));
      lPStmt.setInt(7,vData.getInt("iNumRPMN"));
      lPStmt.setInt(8,vData.getInt("iNumInspecciones"));
      lPStmt.setInt(9,vData.getInt("iNumCertificados"));
      lPStmt.setInt(10,vData.getInt("iNumPermisos"));
      lPStmt.setInt(11,vData.getInt("iNumArribosDesp"));
      lPStmt.setInt(12,vData.getInt("iNumMotores"));
      lPStmt.setInt(13,vData.getInt("iNumAbanderamiento"));
      lPStmt.setInt(14,vData.getInt("iNumComunicaciones"));
      lPStmt.setInt(15,vData.getInt("iNumAccidentes"));
      lPStmt.setInt(16,vData.getInt("iCveUsuario"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      ex.printStackTrace();
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


}

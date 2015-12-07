package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;import com.micper.util.TFechas;

import gob.sct.sipmm.dao.ws.TDCis;
/**
 * <p>Title: TDTRAConfiguraTramite.java</p>
 * <p>Description: DAO de la entidad TRAConfiguraTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRAConfiguraTramite extends DAOBase{
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
   * <p><b> insert into TRAConfiguraTramite(iCveTramite,iCveModalidad,dtIniVigencia,iNumDiasResol,lDiasNaturalesResol,iNumDiasCubrirReq,lDiasNaturalesReq,lRequierePago,iCveFormato) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,dtIniVigencia, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    TFechas fecha = new TFechas();
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
          "insert into TRAConfiguraTramite(iCveTramite,iCveModalidad,dtIniVigencia,iNumDiasResol,lDiasNaturalesResol,iNumDiasCubrirReq,lDiasNaturalesReq,lRequierePago,iCveFormato,iCveTramiteCIS,cNotas,iDiasDespuesNotif,lActivo,iCveUsuario,tsModificacion,lTramInt) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("dtIniVigencia"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setDate(3,vData.getDate("dtIniVigencia"));
      lPStmt.setInt(4,vData.getInt("iNumDiasResol"));
      lPStmt.setInt(5,vData.getInt("lDiasNaturalesResol"));
      lPStmt.setInt(6,vData.getInt("iNumDiasCubrirReq"));
      lPStmt.setInt(7,vData.getInt("lDiasNaturalesReq"));
      lPStmt.setInt(8,vData.getInt("lRequierePago"));
      lPStmt.setInt(9,vData.getInt("iCveFormato"));
      lPStmt.setInt(10,vData.getInt("iCveTramiteCIS"));
      lPStmt.setString(11,vData.getString("cNotas"));
      lPStmt.setInt(12,vData.getInt("iDiasDespuesNotif"));
      lPStmt.setInt(13,vData.getInt("lActivo"));
      lPStmt.setInt(14,vData.getInt("iCveUsuario"));
      lPStmt.setTimestamp(15,fecha.getThisTime());
      lPStmt.setInt(16,vData.getInt("lTramInt"));
      
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
    	ex.printStackTrace();
      warn("insert",ex);
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
    	  ex2.printStackTrace();
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");
      return vData;
    }
  }
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRAConfiguraTramite where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,dtIniVigencia, </b></p>
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
      String lSQL = "delete from TRAConfiguraTramite where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setDate(3,vData.getDate("dtIniVigencia"));

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
   * <p><b> update TRAConfiguraTramite set iNumDiasResol=?, lDiasNaturalesResol=?, iNumDiasCubrirReq=?, lDiasNaturalesReq=?, lRequierePago=?, iCveFormato=? where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,dtIniVigencia, </b></p>
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
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update TRAConfiguraTramite set iNumDiasResol=?, lDiasNaturalesResol=?, iNumDiasCubrirReq=?, lDiasNaturalesReq=?, lRequierePago=?, iCveFormato=?,iCveTramiteCIS=?,cNotas=?,iDiasDespuesNotif=?, lActivo=?,iCveUsuario=?,tsModificacion=?,lTramInt=? where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ? ";

      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("dtIniVigencia"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iNumDiasResol"));
      lPStmt.setInt(2,vData.getInt("lDiasNaturalesResol"));
      lPStmt.setInt(3,vData.getInt("iNumDiasCubrirReq"));
      lPStmt.setInt(4,vData.getInt("lDiasNaturalesReq"));
      lPStmt.setInt(5,vData.getInt("lRequierePago"));
      lPStmt.setInt(6,vData.getInt("iCveFormato"));
      lPStmt.setInt(7,vData.getInt("iCveTramiteCIS"));
      lPStmt.setString(8,vData.getString("cNotas"));
      lPStmt.setInt(9,vData.getInt("iDiasDespuesNotif"));
      lPStmt.setInt(10,vData.getInt("lActivo"));
      lPStmt.setInt(11,vData.getInt("iCveUsuario"));
      lPStmt.setTimestamp(12,fecha.getThisTime());
      lPStmt.setInt(13,vData.getInt("lTramInt"));
      lPStmt.setInt(14,vData.getInt("iCveTramite"));
      lPStmt.setInt(15,vData.getInt("iCveModalidad"));
      lPStmt.setDate(16,vData.getDate("dtIniVigencia"));
      lPStmt.executeUpdate();
      TDCis dCis = new TDCis();
      Vector vcInteresado = new Vector();
      int iCveCita = 0;
      boolean lError = false;
      try{
        iCveCita = dCis.insertaCita(vData.getInt("iCveTramiteCIS"),1,1,"",1);
      }catch(Exception ex){
        lError  = true;
      }finally{
      }
      if(vcInteresado.size()>0){
        for(int i=0;i<vcInteresado.size();i++){
          TVDinRep vInteresado = (TVDinRep) vcInteresado.get(i);
        }
      }


      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      ex.printStackTrace();
      warn("update",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          e.printStackTrace();
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
    	ex2.printStackTrace();
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");

      return vData;
    }
 }
}

package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>Title: TDGRLDomicilioA1.java</p>
 * <p>Description: DAO de la entidad GRLDomicilio</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez <dd>LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDGRLDomicilioA1
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
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
    } finally{
      if(!cMensaje.equals(""))
        throw new DAOException(cMensaje);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into GRLDomicilio(iCvePersona,iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCvePersona,iCveDomicilio, </b></p>
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
          "insert into GRLDomicilio(iCvePersona,iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,lPredeterminado) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      // Buscar clave de domicilio mayor y agregar uno para el registro a crear.
      Vector vcData = findByCustom("",
                                   "select MAX(iCveDomicilio) AS iCveDomicilio from GRLDomicilio where iCvePersona = " +
                                   vData.getInt("iCvePersona"));
      vData.put("iCveDomicilio",
                (vcData.size() > 0) ? (
                (TVDinRep) vcData.get(0)).getInt("iCveDomicilio") + 1 : 1);

      vData.addPK(vData.getString("iCvePersona"));
      vData.addPK(vData.getString("iCveDomicilio"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePersona"));
      lPStmt.setInt(2,vData.getInt("iCveDomicilio"));
      lPStmt.setInt(3,vData.getInt("iCveTipoDomicilio"));
      lPStmt.setString(4,vData.getString("cCalle"));
      lPStmt.setString(5,vData.getString("cNumExterior"));
      lPStmt.setString(6,vData.getString("cNumInterior"));
      lPStmt.setString(7,vData.getString("cColonia"));
      lPStmt.setString(8,vData.getString("cCodPostal"));
      lPStmt.setString(9,vData.getString("cTelefono"));
      lPStmt.setInt(10,vData.getInt("iCvePais"));
      lPStmt.setInt(11,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(12,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(13,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(14,vData.getInt("lPredeterminado"));

      if(vData.getInt("lPredeterminado") == 1){
        String cSql =
            "select iCvePersona, iCveDomicilio, lPredeterminado from GRLDomicilio " +
            "where lPredeterminado=1 and iCvePersona=" +
            vData.getInt("iCvePersona");
        Vector vcData2 = findByCustom("",cSql);
        if(vcData2.size() > 0){
          TVDinRep dinRepTemp;
          for(int i = 0;i < vcData2.size();i++){
            dinRepTemp = (TVDinRep) vcData2.get(i);
            dinRepTemp.put("lPredeterminado",0);
            this.updatePredeterminado(dinRepTemp,conn);
          }
        }
      }

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
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
        throw new DAOException(cMensaje);
      return vData;
    }
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from GRLDomicilio where iCvePersona = ? AND iCveDomicilio = ?  </b></p>
   * <p><b> Campos Llave: iCvePersona,iCveDomicilio, </b></p>
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

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL =
          "delete from GRLDomicilio where iCvePersona = ? AND iCveDomicilio = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCvePersona"));
      lPStmt.setInt(2,vData.getInt("iCveDomicilio"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
      lSuccess = false;
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
        throw new DAOException(cMensaje);
      return lSuccess;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update GRLDomicilio set iCveTipoDomicilio=?, cCalle=?, cNumExterior=?, cNumInterior=?, cColonia=?, cCodPostal=?, cTelefono=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCvePersona = ? AND iCveDomicilio = ?  </b></p>
   * <p><b> Campos Llave: iCvePersona,iCveDomicilio, </b></p>
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
      String lSQL = "update GRLDomicilio set iCveTipoDomicilio=?, cCalle=?, cNumExterior=?, cNumInterior=?, cColonia=?, cCodPostal=?, cTelefono=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=?, lPredeterminado=? where iCvePersona = ? AND iCveDomicilio = ? ";

      vData.addPK(vData.getString("iCvePersona"));
      vData.addPK(vData.getString("iCveDomicilio"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTipoDomicilio"));
      lPStmt.setString(2,vData.getString("cCalle"));
      lPStmt.setString(3,vData.getString("cNumExterior"));
      lPStmt.setString(4,vData.getString("cNumInterior"));
      lPStmt.setString(5,vData.getString("cColonia"));
      lPStmt.setString(6,vData.getString("cCodPostal"));
      lPStmt.setString(7,vData.getString("cTelefono"));
      lPStmt.setInt(8,vData.getInt("iCvePais"));
      lPStmt.setInt(9,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(10,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(11,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(12,vData.getInt("lPredeterminado"));
      lPStmt.setInt(13,vData.getInt("iCvePersona"));
      lPStmt.setInt(14,vData.getInt("iCveDomicilio"));

      if(vData.getInt("lPredeterminado") == 1){
        String cSql =
            "select iCvePersona,iCveDomicilio, lPredeterminado from GRLDomicilio " +
            "where lPredeterminado=1 and iCvePersona=" +
            vData.getInt("iCvePersona");
        Vector vcData2 = findByCustom("",cSql);
        if(vcData2.size() > 0){
          TVDinRep dinRepTemp;
          for(int i = 0;i < vcData2.size();i++){
            dinRepTemp = (TVDinRep) vcData2.get(i);
            dinRepTemp.put("lPredeterminado",0);
            this.updatePredeterminado(dinRepTemp,conn);
          }
        }
      }

      lPStmt.executeUpdate();
      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
      warn("update",ex);
      if(cnNested == null)
        try{conn.rollback();
        } catch(Exception e){fatal("update.rollback",e);
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
      } catch(Exception ex2){warn("update.close",ex2);
      }
      if(!lSuccess)
        throw new DAOException(cMensaje);

      return vData;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update GRLDomicilio set =? where iCvePersona = ? AND iCveDomicilio = ?  </b></p>
   * <p><b> Campos Llave: iCvePersona,iCveDomicilio, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep updatePredeterminado(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update GRLDomicilio set lPredeterminado=? where iCvePersona = ? AND iCveDomicilio = ? ";

      vData.addPK(vData.getString("iCvePersona"));
      vData.addPK(vData.getString("iCveDomicilio"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lPredeterminado"));
      lPStmt.setInt(2,vData.getInt("iCvePersona"));
      lPStmt.setInt(3,vData.getInt("iCveDomicilio"));
      lPStmt.executeUpdate();
      if(cnNested == null)
        conn.commit();
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
      warn("update",ex);
      if(cnNested == null)
        try{conn.rollback();
        } catch(Exception e){fatal("update.rollback",e);
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
      } catch(Exception ex2){warn("update.close",ex2);
      }
      if(!lSuccess)
        throw new DAOException(cMensaje);

      return vData;
    }
  }

}

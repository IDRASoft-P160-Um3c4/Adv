package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDGRLRepLegalA1.java</p>
 * <p>Description: DAO de la entidad GRLRepLegal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
public class TDGRLRepLegalA1
    extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

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
   * <p><b> insert into GRLRepLegal(iCveEmpresa,iCvePersona,dtAsignacion,lPrincipal) values (?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveEmpresa,iCvePersona, </b></p>
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
    String[] aPersonas;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      aPersonas = vData.getString("iCvePersona").split(",");

      for(int i = 0;i < aPersonas.length;i++){ //<---insertar todos los requisitos
        String lSQL =
            "insert into GRLRepLegal(iCveEmpresa,iCvePersona,dtAsignacion,lPrincipal) values (?,?,?,?)";

        vData.addPK(vData.getString("iCveEmpresa"));
        vData.addPK(vData.getString("iCvePersona"));

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iCveEmpresa"));
        lPStmt.setInt(2,Integer.parseInt(aPersonas[i]));
        lPStmt.setDate(3,new TFechas().TodaySQL());
        lPStmt.setInt(4,vData.getInt("lPrincipal"));
        lPStmt.executeUpdate();
      }
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
      lSuccess = false;
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
        throw new DAOException(cMensaje);
      return vData;
    }
  }

  /**
   * Elimina al registro a trav�s de la entidad dada por vData.
   * <p><b> delete from GRLRepLegal where iCveEmpresa = ? AND iCvePersona = ?  </b></p>
   * <p><b> Campos Llave: iCveEmpresa,iCvePersona, </b></p>
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
      String lSQL =
          "delete from GRLRepLegal where iCveEmpresa = ? AND iCvePersona = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveEmpresa"));
      lPStmt.setInt(2,vData.getInt("iCvePersona"));

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
   * <p><b> update GRLRepLegal set dtAsignacion=?, lPrincipal=? where iCveEmpresa = ? AND iCvePersona = ?  </b></p>
   * <p><b> Campos Llave: iCveEmpresa,iCvePersona, </b></p>
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
      String lSQL = "update GRLRepLegal set lPrincipal=? where iCveEmpresa = ? AND iCvePersona = ? ";

      vData.addPK(vData.getString("iCveEmpresa"));
      vData.addPK(vData.getString("iCvePersona"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lPrincipal"));
      lPStmt.setInt(2,vData.getInt("iCveEmpresa"));
      lPStmt.setInt(3,vData.getInt("iCvePersona"));

      if(vData.getInt("lPrincipal") == 1){
        String cSql =
              "select iCvePersona,iCveEmpresa from GRLRepLegal " +
              "where lPrincipal=1 and iCveEmpresa=" +
              vData.getInt("iCveEmpresa");
        Vector vcData2 = findByCustom("",cSql);
        if(vcData2.size() > 0){
          TVDinRep dinRepTemp;
          for(int i = 0;i < vcData2.size();i++){
            dinRepTemp = (TVDinRep) vcData2.get(i);
            dinRepTemp.put("lPrincipal",0);
            this.updatePredeterminado(dinRepTemp,conn);
          }
        }
      }

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
      String lSQL = "update GRLRepLegal set lPrincipal=? where iCveEmpresa = ? AND iCvePersona = ? ";

      vData.addPK(vData.getString("iCveEmpresa"));
      vData.addPK(vData.getString("iCvePersona"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lPrincipal"));
      lPStmt.setInt(2,vData.getInt("iCveEmpresa"));
      lPStmt.setInt(3,vData.getInt("iCvePersona"));

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

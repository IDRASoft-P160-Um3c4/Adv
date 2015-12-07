package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import gob.sct.sipmm.dao.reporte.TDObtenDatos;
/**
 * <p>Title: TDGRLFolioXSegtoEnt.java</p>
 * <p>Description: DAO de la entidad GRLFolioXSegtoEnt</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
public class TDGRLFolioXSegtoEnt extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  String cFolio;
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
   * <p><b> insert into GRLFolioXSegtoEnt(iCveSegtoEntidad,iConsecutivoSegtoRef,lFolioReferenInterno,cNumOfPartesRefExterna,cNumOficioRefExterna,iEjercicioFolio,ClaveOficina,ClaveDepartamento,cDigitosFolio,iConsecutivo) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveSegtoEntidad,iConsecutivoSegtoRef, </b></p>
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
    int iConsecutivo=0;
    int iDepartamento = 0;
    String cDigitosFolio;
    int iEjercicio = 0;
    int iOficina = 0;

    TDObtenDatos obtenerDatos = new TDObtenDatos();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      cFolio = vData.getString("cNumOficioRef");
      obtenerDatos.dFolio.setDatosFolio(cFolio);

      iConsecutivo = obtenerDatos.dFolio.getCveConsecutivo();
      iDepartamento = obtenerDatos.dFolio.getCveDepartamento();
      cDigitosFolio = obtenerDatos.dFolio.getCveDigitosFolio();
      iEjercicio = obtenerDatos.dFolio.getCveEjercicio();
      iOficina = obtenerDatos.dFolio.getCveOficina();


      String lSQL1 =
          "insert into GRLFolioXSegtoEnt(iCveSegtoEntidad,iConsecutivoSegtoRef,lFolioReferenInterno,iEjercicioFolio,iCveOficina,iCveDepartamento,cDigitosFolio,iConsecutivo,lExterno) values (?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData1 = findByCustom("","select MAX(iConsecutivoSegtoRef) AS iConsecutivoSegtoRef "+
                                    " from GRLFolioXSegtoEnt where iCveSegtoEntidad = "+vData.getInt("iCveSegtoEntidad"));
      if(vcData1.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData1.get(0);
         vData.put("iConsecutivoSegtoRef",vUltimo.getInt("iConsecutivoSegtoRef") + 1);
      }else
         vData.put("iConsecutivoSegtoRef",1);
     vData.addPK(vData.getString("iCveSegtoEntidad"));
      vData.addPK(vData.getString("iConsecutivoSegtoRef"));
      //...

      lPStmt = conn.prepareStatement(lSQL1);
      lPStmt.setInt(1,vData.getInt("iCveSegtoEntidad"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoSegtoRef"));
      lPStmt.setInt(3,vData.getInt("lFolioReferenInterno"));
      lPStmt.setInt(4,iEjercicio);
      lPStmt.setInt(5,iOficina);
      lPStmt.setInt(6,iDepartamento);
      lPStmt.setString(7,cDigitosFolio);
      lPStmt.setInt(8,iConsecutivo);
      lPStmt.setInt(9,vData.getInt("lExterno"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
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
        throw new DAOException("");
      return vData;
    }
  }
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from GRLFolioXSegtoEnt where iCveSegtoEntidad = ? AND iConsecutivoSegtoRef = ?  </b></p>
   * <p><b> Campos Llave: iCveSegtoEntidad,iConsecutivoSegtoRef, </b></p>
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
      String lSQL = "delete from GRLFolioXSegtoEnt where iCveSegtoEntidad = ? AND iConsecutivoSegtoRef = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("hdCveSegtoEntidad"));
      lPStmt.setInt(2,vData.getInt("hdConsecutivoSegtoRef"));

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
   * <p><b> update GRLFolioXSegtoEnt set lFolioReferenInterno=?, cNumOfPartesRefExterna=?, cNumOficioRefExterna=?, iEjercicioFolio=?, ClaveOficina=?, ClaveDepartamento=?, cDigitosFolio=?, iConsecutivo=? where iCveSegtoEntidad = ? AND iConsecutivoSegtoRef = ?  </b></p>
   * <p><b> Campos Llave: iCveSegtoEntidad,iConsecutivoSegtoRef, </b></p>
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
    int iConsecutivo=0;
    int iDepartamento = 0;
    String cDigitosFolio;
    int iEjercicio = 0;
    int iOficina = 0;


    TDObtenDatos obtenerDatos = new TDObtenDatos();
    cFolio = vData.getString("cNumOficioRef");
    obtenerDatos.dFolio.setDatosFolio(cFolio);

    iConsecutivo = obtenerDatos.dFolio.getCveConsecutivo();
    iDepartamento = obtenerDatos.dFolio.getCveDepartamento();
    cDigitosFolio = obtenerDatos.dFolio.getCveDigitosFolio();
    iEjercicio = obtenerDatos.dFolio.getCveEjercicio();
    iOficina = obtenerDatos.dFolio.getCveOficina();


    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update GRLFolioXSegtoEnt set iEjercicioFolio=?, iCveOficina=?, iCveDepartamento=?, cDigitosFolio=?, iConsecutivo=?, lExterno=? where iCveSegtoEntidad = ? AND iConsecutivoSegtoRef = ? ";

      vData.addPK(vData.getString("iCveSegtoEntidad"));
      vData.addPK(vData.getString("iConsecutivoSegtoRef"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,iEjercicio);
      lPStmt.setInt(2,iOficina);
      lPStmt.setInt(3,iDepartamento);
      lPStmt.setString(4,cDigitosFolio);
      lPStmt.setInt(5,iConsecutivo);
      lPStmt.setInt(6,vData.getInt("lExterno"));
      lPStmt.setInt(7,vData.getInt("hdCveSegtoEntidad"));
      lPStmt.setInt(8,vData.getInt("hdConsecutivoSegtoRef"));
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

 public TVDinRep insertB(TVDinRep vData,Connection cnNested) throws
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
        "insert into GRLFolioXSegtoEnt(iCveSegtoEntidad,iConsecutivoSegtoRef,LFOLIOREFERENINTERNO,cNumOfPartesRefExterna,cNumOficioRefExterna,lExterno) values (?,?,1,?,?,?)";

    //AGREGAR AL ULTIMO ...
    Vector vcData = findByCustom("","select MAX(iConsecutivoSegtoRef) AS iConsecutivoSegtoRef "+
                                 " from GRLFolioXSegtoEnt where iCveSegtoEntidad = "+vData.getInt("iCveSegtoEntidad"));
    if(vcData.size() > 0){
       TVDinRep vUltimo = (TVDinRep) vcData.get(0);
       vData.put("iConsecutivoSegtoRef",vUltimo.getInt("iConsecutivoSegtoRef") + 1);
    }else
       vData.put("iConsecutivoSegtoRef",1);
    vData.addPK(vData.getString("iConsecutivoSegtoRef"));
    //...

    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iCveSegtoEntidad"));
    lPStmt.setInt(2,vData.getInt("iConsecutivoSegtoRef"));
    lPStmt.setString(3,vData.getString("cNumOfPartesRefExterna"));
    lPStmt.setString(4,vData.getString("cNumOficioRefExterna"));
    lPStmt.setInt(5,vData.getInt("lExterno"));
    lPStmt.executeUpdate();
    if(cnNested == null){
      conn.commit();
    }
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
      throw new DAOException("");
    return vData;
  }
 }

 public TVDinRep updateB(TVDinRep vData,Connection cnNested) throws
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
    String lSQL = "update GRLFolioXSegtoEnt set cNumOfPartesRefExterna=?, cNumOficioRefExterna=?, lExterno=? where iCveSegtoEntidad = ? AND iConsecutivoSegtoRef = ? ";

    vData.addPK(vData.getString("iCveSegtoEntidad"));
    vData.addPK(vData.getString("iConsecutivoSegtoRef"));

    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setString(1,vData.getString("cNumOfPartesRefExterna"));
    lPStmt.setString(2,vData.getString("cNumOficioRefExterna"));
    lPStmt.setInt(3,vData.getInt("lExterno"));
    lPStmt.setInt(4,vData.getInt("hdCveSegtoEntidad"));
    lPStmt.setInt(5,vData.getInt("hdConsecutivoSegtoRef"));
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

}

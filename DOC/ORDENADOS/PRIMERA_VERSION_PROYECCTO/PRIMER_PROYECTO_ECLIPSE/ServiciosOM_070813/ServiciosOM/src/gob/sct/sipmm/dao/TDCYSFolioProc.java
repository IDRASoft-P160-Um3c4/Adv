package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
/**
 * <p>Title: TDCYSFolioProc.java</p>
 * <p>Description: DAO de la entidad CYSFolioProc</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDCYSFolioProc extends DAOBase{
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
   * <p><b> insert into CYSFolioProc(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
    int iConsecutivo = this.findMax(vData.getInt("iEjercicio"),vData.getInt("iMovProcedimiento"));
    vData.put("iMovFolioProc",iConsecutivo);

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSFolioProc(iEjercicio, " +
                    "iMovProcedimiento, " +
                    "iMovFolioProc, " +
                    "iEjercicioFol," +
                    "iCveOficina, " +
                    "iCveDepartamento, " +
                    "cDigitosFolio, " +
                    "iConsecutivo, " +
                    "lEsProrroga, " +
                    "lEsAlegato, " +
                    "lEsSinEfecto, " +
                    "lEsNotificacion, " +
                    "lEsRecordatorio, " +
                    "lEsResolucion ) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovFolioProc"));
      lPStmt.setInt(4,vData.getInt("iEjercicioFol"));
      lPStmt.setInt(5,vData.getInt("iCveOficina"));
      lPStmt.setInt(6,vData.getInt("iCveDepartamento"));
      lPStmt.setString(7,vData.getString("cDigitosFolio"));
      lPStmt.setInt(8,vData.getInt("iConsecutivo"));
      lPStmt.setInt(9,vData.getInt("lEsProrroga"));
      lPStmt.setInt(10,vData.getInt("lEsAlegato"));
      lPStmt.setInt(11,vData.getInt("lEsSinEfecto"));
      lPStmt.setInt(12,vData.getInt("lEsNotificacion"));
      lPStmt.setInt(13,vData.getInt("lEsRecordatorio"));
      lPStmt.setInt(14,vData.getInt("lEsResolucion"));
      lPStmt.executeUpdate();
      lPStmt.close();

      lSQL = "update CYSSegProcedimiento " +
             "set cNumOficio=? " +
             "where iEjercicio=? " +
             "and iMovProcedimiento=? " +
             "";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDigitosFolio") + "." + vData.getInt("iConsecutivo") + "/" + vData.getInt("iEjercicioFol"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovProcedimiento"));
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      //ex.printStackTrace();
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

  public TVDinRep insertSinUpdate(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    int iConsecutivo = this.findMax(vData.getInt("iEjercicio"),vData.getInt("iMovProcedimiento"));
    vData.put("iMovFolioProc",iConsecutivo);

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSFolioProc(iEjercicio, " +
                    "iMovProcedimiento, " +
                    "iMovFolioProc, " +
                    "iEjercicioFol," +
                    "iCveOficina, " +
                    "iCveDepartamento, " +
                    "cDigitosFolio, " +
                    "iConsecutivo, " +
                    "lEsProrroga, " +
                    "lEsAlegato, " +
                    "lEsSinEfecto, " +
                    "lEsNotificacion, " +
                    "lEsRecordatorio, " +
                    "lEsResolucion, "+
                    "lEsReversion, "+
                    "lEsActaAdmva, "+
                    "lEsActaCircunstanciada ) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovFolioProc"));
      lPStmt.setInt(4,vData.getInt("iEjercicioFol"));
      lPStmt.setInt(5,vData.getInt("iCveOficina"));
      lPStmt.setInt(6,vData.getInt("iCveDepartamento"));
      lPStmt.setString(7,vData.getString("cDigitosFolio"));
      lPStmt.setInt(8,vData.getInt("iConsecutivo"));
      lPStmt.setInt(9,vData.getInt("lEsProrroga"));
      lPStmt.setInt(10,vData.getInt("lEsAlegato"));
      lPStmt.setInt(11,vData.getInt("lEsSinEfecto"));
      lPStmt.setInt(12,vData.getInt("lEsNotificacion"));
      lPStmt.setInt(13,vData.getInt("lEsRecordatorio"));
      lPStmt.setInt(14,vData.getInt("lEsResolucion"));
      lPStmt.setInt(15,vData.getInt("lEsReversion"));
      lPStmt.setInt(16,vData.getInt("lEsActaAdmva"));
      lPStmt.setInt(17,vData.getInt("lEsActaCircunstanciada"));

      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      //ex.printStackTrace();
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
   * <p><b> delete from CYSFolioProc where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from CYSFolioProc where iEjercicio=? " +
                    "and iMovProcedimiento=? "   +
                    "and iMovFolioProc=? ";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovFolioProc"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = "" + sqle.getErrorCode();
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
   * <p><b> update CYSFolioProc set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
      String lSQL = "update CYSFolioProc " +
                    "set iEjercicioFol=?," +
                    "iCveOficina=?, " +
                    "iCveDepartamento=?, " +
                    "cDigitosFolio=?, " +
                    "iConsecutivo=?, " +
                    "lEsProrroga=?, " +
                    "lEsAlegato=?, " +
                    "lEsSinEfecto=?, " +
                    "lEsNotificacion=?, " +
                    "lEsRecordatorio=?, " +
                    "lEsResolucion=? " +
                    "where iEjercicio=? " +
                    "and iMovProcedimiento=? " +
                    "and iMovFolioProc=? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioFol"));
      lPStmt.setInt(2,vData.getInt("iCveOficina"));
      lPStmt.setInt(3,vData.getInt("iCveDepartamento"));
      lPStmt.setString(4,vData.getString("cDigitosFolio"));
      lPStmt.setInt(5,vData.getInt("iConsecutivo"));
      lPStmt.setInt(6,vData.getInt("lEsProrroga"));
      lPStmt.setInt(7,vData.getInt("lEsAlegato"));
      lPStmt.setInt(8,vData.getInt("lEsSinEfecto"));
      lPStmt.setInt(9,vData.getInt("lEsNotificacion"));
      lPStmt.setInt(10,vData.getInt("lEsRecordatorio"));
      lPStmt.setInt(11,vData.getInt("lEsResolucion"));

      lPStmt.setInt(12,vData.getInt("iEjercicio"));
      lPStmt.setInt(13,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(14,vData.getInt("iMovFolioProc"));
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

  public int findMax(int iEjercicio,int iMovProcedimiento) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    Statement lStmt = null;
    ResultSet rs = null;
    int iConsecutivo = 0;
    try{

      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      lStmt = conn.createStatement();
      String lSQL =
          " SELECT MAX(iMovFolioProc) AS iMovFolioProc " +
          " FROM CYSFolioProc " +
          " WHERE iEjercicio = " + iEjercicio +
          " and iMovProcedimiento = " + iMovProcedimiento;

      rs = lStmt.executeQuery(lSQL);
      while(rs.next()){
        iConsecutivo = rs.getInt("iMovFolioProc");
      }
      iConsecutivo++;
    } catch(Exception ex){
      ex.printStackTrace();
    } finally{
      try{
        if(lStmt != null){
          lStmt.close();
        }
        if(rs != null){
          rs.close();
        }
        if(conn != null){
          conn.close();
        }
        dbConn.closeConnection();
      } catch(Exception ex2){
      }
      return iConsecutivo;
    }
  }

  public TVDinRep updateProrroga(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    //int iConsecutivo = this.findMax(vData.getInt("iEjercicio"),vData.getInt("iMovProcedimiento"));
    //vData.put("iMovFolioProc",iConsecutivo);

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update CYSFolioProc " +
             "set iEjercicioFol=?, " +
             "iCveOficina=?, " +
             "iCveDepartamento=?, " +
             "cDigitosFolio=?, " +
             "iConsecutivo=? " +
             "where iEjercicio=? " +
             "and iMovProcedimiento=? " +
             "and iMovFolioProc=? " +
             "";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioFol"));
      lPStmt.setInt(2,vData.getInt("iCveOficina"));
      lPStmt.setInt(3,vData.getInt("iCveDepartamento"));
      lPStmt.setString(4,vData.getString("cDigitosFolio"));
      lPStmt.setInt(5,vData.getInt("iConsecutivo"));
      lPStmt.setInt(6,vData.getInt("iEjercicio"));
      lPStmt.setInt(7,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(8,vData.getInt("iMovFolioProc"));
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      //ex.printStackTrace();
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

  public TVDinRep updateAlegatos(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    //int iConsecutivo = this.findMax(vData.getInt("iEjercicio"),vData.getInt("iMovProcedimiento"));
    //vData.put("iMovFolioProc",iConsecutivo);

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update CYSFolioProc " +
             "set iEjercicioFol=?, " +
             "iCveOficina=?, " +
             "iCveDepartamento=?, " +
             "cDigitosFolio=?, " +
             "iConsecutivo=? " +
             "where iEjercicio=? " +
             "and iMovProcedimiento=? " +
             "and iMovFolioProc=? " +
             "";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioFol"));
      lPStmt.setInt(2,vData.getInt("iCveOficina"));
      lPStmt.setInt(3,vData.getInt("iCveDepartamento"));
      lPStmt.setString(4,vData.getString("cDigitosFolio"));
      lPStmt.setInt(5,vData.getInt("iConsecutivo"));
      lPStmt.setInt(6,vData.getInt("iEjercicio"));
      lPStmt.setInt(7,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(8,vData.getInt("iMovFolioProc"));
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      //ex.printStackTrace();
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

  public TVDinRep updateSinEfectos(TVDinRep vData,Connection cnNested) throws
        DAOException{
      DbConnection dbConn = null;
      Connection conn = cnNested;
      PreparedStatement lPStmt = null;
      boolean lSuccess = true;
      //int iConsecutivo = this.findMax(vData.getInt("iEjercicio"),vData.getInt("iMovProcedimiento"));
      //vData.put("iMovFolioProc",iConsecutivo);

      try{
        if(cnNested == null){
          dbConn = new DbConnection(dataSourceName);
          conn = dbConn.getConnection();
          conn.setAutoCommit(false);
          conn.setTransactionIsolation(2);
        }
        String lSQL = "update CYSFolioProc " +
               "set iEjercicioFol=?, " +
               "iCveOficina=?, " +
               "iCveDepartamento=?, " +
               "cDigitosFolio=?, " +
               "iConsecutivo=? " +
               "where iEjercicio=? " +
               "and iMovProcedimiento=? " +
               "and iMovFolioProc=? " +
               "";

        vData.addPK(vData.getString("iEjercicio"));
        vData.addPK(vData.getString("iMovProcedimiento"));
        vData.addPK(vData.getString("iMovFolioProc"));

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEjercicioFol"));
        lPStmt.setInt(2,vData.getInt("iCveOficina"));
        lPStmt.setInt(3,vData.getInt("iCveDepartamento"));
        lPStmt.setString(4,vData.getString("cDigitosFolio"));
        lPStmt.setInt(5,vData.getInt("iConsecutivo"));
        lPStmt.setInt(6,vData.getInt("iEjercicio"));
        lPStmt.setInt(7,vData.getInt("iMovProcedimiento"));
        lPStmt.setInt(8,vData.getInt("iMovFolioProc"));
        lPStmt.executeUpdate();

        if(cnNested == null){
          conn.commit();
        }
      } catch(Exception ex){
        //ex.printStackTrace();
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

  public TVDinRep insertNotificacion(TVDinRep vData,Connection cnNested) throws
          DAOException{
        DbConnection dbConn = null;
        Connection conn = cnNested;
        PreparedStatement lPStmt = null;
        boolean lSuccess = true;
        int iConsecutivo = this.findMax(vData.getInt("iEjercicio"),vData.getInt("iMovProcedimiento"));
        vData.put("iMovFolioProc",iConsecutivo);

        try{
          if(cnNested == null){
            dbConn = new DbConnection(dataSourceName);
            conn = dbConn.getConnection();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(2);
          }
          String lSQL = "insert into CYSFolioProc(iEjercicio, " +
                        "iMovProcedimiento, " +
                        "iMovFolioProc, " +
                        "iEjercicioFol," +
                        "iCveOficina, " +
                        "iCveDepartamento, " +
                        "cDigitosFolio, " +
                        "iConsecutivo, " +
                        "lEsProrroga, " +
                        "lEsAlegato, " +
                        "lEsSinEfecto, " +
                        "lEsNotificacion, " +
                        "lEsRecordatorio, " +
                        "lEsResolucion ) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

          vData.addPK(vData.getString("iEjercicio"));
          vData.addPK(vData.getString("iMovProcedimiento"));
          vData.addPK(vData.getString("iMovFolioProc"));

          lPStmt = conn.prepareStatement(lSQL);
          lPStmt.setInt(1,vData.getInt("iEjercicio"));
          lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
          lPStmt.setInt(3,vData.getInt("iMovFolioProc"));
          lPStmt.setInt(4,vData.getInt("iEjercicioFol"));
          lPStmt.setInt(5,vData.getInt("iCveOficina"));
          lPStmt.setInt(6,vData.getInt("iCveDepartamento"));
          lPStmt.setString(7,vData.getString("cDigitosFolio"));
          lPStmt.setInt(8,vData.getInt("iConsecutivo"));
          lPStmt.setInt(9,vData.getInt("lEsProrroga"));
          lPStmt.setInt(10,vData.getInt("lEsAlegato"));
          lPStmt.setInt(11,vData.getInt("lEsSinEfecto"));
          lPStmt.setInt(12,vData.getInt("lEsNotificacion"));
          lPStmt.setInt(13,vData.getInt("lEsRecordatorio"));
          lPStmt.setInt(14,vData.getInt("lEsResolucion"));
          lPStmt.executeUpdate();

          if(cnNested == null){
            conn.commit();
          }
        } catch(Exception ex){
          //ex.printStackTrace();
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

  public TVDinRep insertSancion(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    int iConsecutivo = this.findMax(vData.getInt("iEjercicio"),vData.getInt("iMovProcedimiento"));
    vData.put("iMovFolioProc",iConsecutivo);

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSFolioProc(iEjercicio, " +
                    "iMovProcedimiento, " +
                    "iMovFolioProc, " +
                    "iEjercicioFol," +
                    "iCveOficina, " +
                    "iCveDepartamento, " +
                    "cDigitosFolio, " +
                    "iConsecutivo, " +
                    "lEsProrroga, " +
                    "lEsAlegato, " +
                    "lEsSinEfecto, " +
                    "lEsNotificacion, " +
                    "lEsRecordatorio, "+
                    "lEsResolucion ) "+
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovFolioProc"));
      lPStmt.setInt(4,vData.getInt("iEjercicioFol"));
      lPStmt.setInt(5,vData.getInt("iCveOficina"));
      lPStmt.setInt(6,vData.getInt("iCveDepartamento"));
      lPStmt.setString(7,vData.getString("cDigitosFolio"));
      lPStmt.setInt(8,vData.getInt("iConsecutivo"));
      lPStmt.setInt(9,vData.getInt("lEsProrroga"));
      lPStmt.setInt(10,vData.getInt("lEsAlegato"));
      lPStmt.setInt(11,vData.getInt("lEsSinEfecto"));
      lPStmt.setInt(12,vData.getInt("lEsNotificacion"));
      lPStmt.setInt(13,vData.getInt("lEsRecordatorio"));
      lPStmt.setInt(14,vData.getInt("lEsResolucion"));
      lPStmt.executeUpdate();
      lPStmt.close();

      lSQL = "update CYSSegProcedimiento " +
             "set cNumOficio=? " +
             "where iEjercicio=? " +
             "and iMovProcedimiento=? " +
             "";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDigitosFolio") + "." + vData.getInt("iConsecutivo") + "/" + vData.getInt("iEjercicioFol"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovProcedimiento"));
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      //ex.printStackTrace();
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



}


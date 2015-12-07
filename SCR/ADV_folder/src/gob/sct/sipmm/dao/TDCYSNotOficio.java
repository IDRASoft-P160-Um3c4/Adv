package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
/**
 * <p>Title: TDCYSNotOficio.java</p>
 * <p>Description: DAO de la entidad CYSNotOficio</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDCYSNotOficio extends DAOBase{
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
   * <p><b> insert into CYSNotOficio(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
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

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSNotOficio(iEjercicio, " +
                    "iMovProcedimiento, " +
                    "iMovFolioProc, " +
                    "iMovNotCitaNotificacion, " +
                    "iEjercicioNot," +
                    "iMovProcedimientoNot, " +
                    "iMovFolioProcNot, " +
                    "iEjercicioRec," +
                    "iMovProcedimientoRec, " +
                    "iMovFolioProcRec) " +
                    "values (?,?,?,?,?,?,?,?,?,?) ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));
      vData.addPK(vData.getString("iMovCitaNotificacion"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovFolioProc"));
      lPStmt.setInt(4,vData.getInt("iMovCitaNotificacion"));
      lPStmt.setInt(5,vData.getInt("iEjercicioNot"));
      lPStmt.setInt(6,vData.getInt("iMovProcedimientoNot"));
      lPStmt.setInt(7,vData.getInt("iMovFolioProcNot"));
      lPStmt.setInt(8,vData.getInt("iEjercicioRec"));
      lPStmt.setInt(9,vData.getInt("iMovProcedimientoRec"));
      lPStmt.setInt(10,vData.getInt("iMovFolioProcRec"));
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

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from CYSNotOficio where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
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
      String lSQL = "delete from CYSNotOficio where iEjercicio=? " +
                    "and iMovProcedimiento=? "   +
                    "and iMovFolioProc=? " +
                    "and iMovCitaNotificacion=? " +
                    "";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovFolioProc"));
      lPStmt.setInt(4,vData.getInt("iMovCitaNotificacion"));
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
   * <p><b> update CYSNotOficio set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
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
      String lSQL = "update CYSNotOficio " +
                    "set iEjercicioNot=?," +
                    "iMovProcedimientoNot=?," +
                    "iMovFolioProcNot=?, " +
                    "iEjercicioRec=?," +
                    "iMovProcedimientoRec=?," +
                    "iMovFolioProcRec=? " +
                    "where iEjercicio=? " +
                    "and iMovProcedimiento=? " +
                    "and iMovFolioProc=? " +
                    "and iMovCitaNotificacion=? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));
      vData.addPK(vData.getString("iMovCitaNotificacion"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioNot"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimientoNot"));
      lPStmt.setInt(3,vData.getInt("iMovFolioProcNot"));
      lPStmt.setInt(4,vData.getInt("iEjercicioRec"));
      lPStmt.setInt(5,vData.getInt("iMovProcedimientoRec"));
      lPStmt.setInt(6,vData.getInt("iMovFolioProcRec"));
      lPStmt.setInt(7,vData.getInt("iEjercicio"));
      lPStmt.setInt(8,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(9,vData.getInt("iMovFolioProc"));
      lPStmt.setInt(10,vData.getInt("iMovCitaNotificacion"));
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

  public TVDinRep updNotificacion(TVDinRep vData,Connection cnNested) throws
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
        String lSQL = "update CYSNotOficio " +
                      "set iEjercicioNot=?," +
                      "iMovProcedimientoNot=?," +
                      "iMovFolioProcNot=? " +
                      "where iEjercicio=? " +
                      "and iMovProcedimiento=? " +
                      "and iMovFolioProc=? " +
                      "and iMovCitaNotificacion=? ";

        vData.addPK(vData.getString("iEjercicio"));
        vData.addPK(vData.getString("iMovProcedimiento"));
        vData.addPK(vData.getString("iMovFolioProc"));
        vData.addPK(vData.getString("iMovCitaNotificacion"));

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEjercicioNot"));
        lPStmt.setInt(2,vData.getInt("iMovProcedimientoNot"));
        lPStmt.setInt(3,vData.getInt("iMovFolioProcNot"));
        lPStmt.setInt(4,vData.getInt("iEjercicio"));
        lPStmt.setInt(5,vData.getInt("iMovProcedimiento"));
        lPStmt.setInt(6,vData.getInt("iMovFolioProc"));
        lPStmt.setInt(7,vData.getInt("iMovCitaNotificacion"));
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

    public TVDinRep updRecordatorio(TVDinRep vData,Connection cnNested) throws
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
          String lSQL = "update CYSNotOficio " +
                        "set iEjercicioRec=?," +
                        "iMovProcedimientoRec=?," +
                        "iMovFolioProcRec=? " +
                        "where iEjercicio=? " +
                        "and iMovProcedimiento=? " +
                        "and iMovFolioProc=? " +
                        "and iMovCitaNotificacion=? ";

          vData.addPK(vData.getString("iEjercicio"));
          vData.addPK(vData.getString("iMovProcedimiento"));
          vData.addPK(vData.getString("iMovFolioProc"));
          vData.addPK(vData.getString("iMovCitaNotificacion"));

          lPStmt = conn.prepareStatement(lSQL);
          lPStmt.setInt(1,vData.getInt("iEjercicioRec"));
          lPStmt.setInt(2,vData.getInt("iMovProcedimientoRec"));
          lPStmt.setInt(3,vData.getInt("iMovFolioProcRec"));
          lPStmt.setInt(4,vData.getInt("iEjercicio"));
          lPStmt.setInt(5,vData.getInt("iMovProcedimiento"));
          lPStmt.setInt(6,vData.getInt("iMovFolioProc"));
          lPStmt.setInt(7,vData.getInt("iMovCitaNotificacion"));
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


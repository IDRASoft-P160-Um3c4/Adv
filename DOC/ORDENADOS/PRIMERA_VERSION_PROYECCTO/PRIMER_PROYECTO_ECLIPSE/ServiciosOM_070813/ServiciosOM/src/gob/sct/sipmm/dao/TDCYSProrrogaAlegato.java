package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCYSProrrogaAlegato.java</p>
 * <p>Description: DAO de la entidad CYSProrrogaAlegato</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSProrrogaAlegato
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
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into CYSProrrogaAlegato(iEjercicio,iMovProcedimiento,iMovFolioProc,dtRegistro,lProrrogaAlegato,lSeConcede,iDiasOtorgados,cObsProAle,iEjercicioProAle,iMovProcedimientoProAle,iMovFolioProcProAle) values (?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovProcedimiento,iMovFolioProc, </b></p>
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
    PreparedStatement lPStmt1 = null;
    boolean lSuccess = true;
    int iEjercicio = 0,iMovProcedimiento = 0,iMovFolioProc = 0;

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      iEjercicio = vData.getInt("iEjercicio");
      iMovProcedimiento = vData.getInt("iMovProcedimiento");

      //Calcular el siguiente folio
      Vector vcData = findByCustom("",
          "SELECT MAX(IMOVFOLIOPROC) as IMOVFOLIOPROC FROM CYSFOLIOPROC " +
                                   "WHERE IEJERCICIO =" + iEjercicio +
                                   " AND IMOVPROCEDIMIENTO =" +
                                   iMovProcedimiento);
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        iMovFolioProc = vUltimo.getInt("IMOVFOLIOPROC") + 1;
      }

      String lSQL1 = "insert into CysFolioProc(iEjercicio, " +
          "iMovProcedimiento, " +
          "iMovFolioProc, " +
          "lEsProrroga, " +
          "lEsAlegato, " +
          "lEsSinEfecto, " +
          "lEsNotificacion, " +
          "lEsRecordatorio ) " +
          "values(?,?,?,?,?,?,?,?) ";
      lPStmt1 = conn.prepareStatement(lSQL1);
      lPStmt1.setInt(1,iEjercicio);
      lPStmt1.setInt(2,iMovProcedimiento);
      lPStmt1.setInt(3,iMovFolioProc);
      lPStmt1.setInt(4,vData.getInt("lEsProrroga"));
      lPStmt1.setInt(5,vData.getInt("lEsAlegato"));
      lPStmt1.setInt(6,vData.getInt("lEsSinEfecto"));
      lPStmt1.setInt(7,0);
      lPStmt1.setInt(8,0);
      lPStmt1.executeUpdate();
      lPStmt1.close();

      String lSQL = "insert into CYSProrrogaAlegato(iEjercicio, " +
          "iMovProcedimiento, " +
          "iMovFolioProc, " +
          "dtRegistro, " +
          "lProrrogaAlegato, " +
          "lSeConcede," +
          "iDiasOtorgados, " +
          "cObsProAle, " +
          "iEjercicioProAle, " +
          "iMovProcedimientoProAle, " +
          "iMovFolioProcProAle, " +
          "cNumEscrito, " +
          "dtEscrito ) " +
          "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(Integer.toString(iMovFolioProc));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,iEjercicio);
      lPStmt.setInt(2,iMovProcedimiento);
      lPStmt.setInt(3,iMovFolioProc);
      lPStmt.setDate(4,vData.getDate("dtRegistro"));
      lPStmt.setInt(5,vData.getInt("lProrrogaAlegato"));
      lPStmt.setInt(6,vData.getInt("lSeConcede"));
      lPStmt.setInt(7,vData.getInt("iDiasOtorgados"));
      lPStmt.setString(8,vData.getString("cObsProAle"));
      lPStmt.setInt(9,vData.getInt("iEjercicioProAle"));
      lPStmt.setInt(10,vData.getInt("iMovProcedimientoProAle"));
      lPStmt.setInt(11,vData.getInt("iMovFolioProcProAle"));
      lPStmt.setString(12,vData.getString("cNumEscrito"));
      lPStmt.setDate(13,vData.getDate("dtEscrito"));
      lPStmt.executeUpdate();
      lPStmt.close();

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
        if(lPStmt1 != null){
          lPStmt1.close();
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
   * <p><b> delete from CYSProrrogaAlegato where iEjercicio = ? AND iMovProcedimiento = ? AND iMovFolioProc = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovProcedimiento,iMovFolioProc, </b></p>
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
    PreparedStatement lPStmt1 = null;
    boolean lSuccess = true;
    int iEjercicio = 0,iMovProcedimiento = 0,iMovFolioProc = 0;
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      }

      iEjercicio = vData.getInt("iEjercicio");
      iMovProcedimiento = vData.getInt("iMovProcedimiento");
      iMovFolioProc = vData.getInt("iMovFolioProc");
      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from CYSProrrogaAlegato where iEjercicio = ? AND iMovProcedimiento = ? AND iMovFolioProc = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,iEjercicio);
      lPStmt.setInt(2,iMovProcedimiento);
      lPStmt.setInt(3,iMovFolioProc);
      lPStmt.executeUpdate();

      String lSQL1 = "delete from CYSFolioProc where iEjercicio = ? AND iMovProcedimiento = ? AND iMovFolioProc = ?  ";
      //...

      lPStmt1 = conn.prepareStatement(lSQL1);

      lPStmt1.setInt(1,iEjercicio);
      lPStmt1.setInt(2,iMovProcedimiento);
      lPStmt1.setInt(3,iMovFolioProc);
      lPStmt1.executeUpdate();

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
        if(lPStmt1 != null){
          lPStmt1.close();
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
   * <p><b> update CYSProrrogaAlegato set dtRegistro=?, lProrrogaAlegato=?, lSeConcede=?, iDiasOtorgados=?, cObsProAle=?, iEjercicioProAle=?, iMovProcedimientoProAle=?, iMovFolioProcProAle=? where iEjercicio = ? AND iMovProcedimiento = ? AND iMovFolioProc = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovProcedimiento,iMovFolioProc, </b></p>
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
      String lSQL = "update CYSProrrogaAlegato set dtRegistro=?, " +
          "lProrrogaAlegato=?, " +
          "lSeConcede=?, " +
          "iDiasOtorgados=?, " +
          "cObsProAle=?, " +
          "iEjercicioProAle=?, " +
          "iMovProcedimientoProAle=?, " +
          "iMovFolioProcProAle=?, " +
          "cNumEscrito=?, " +
          "dtEscrito=?  " +
          "where iEjercicio = ? AND iMovProcedimiento = ? AND iMovFolioProc = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovFolioProc"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setInt(2,vData.getInt("lProrrogaAlegato"));
      lPStmt.setInt(3,vData.getInt("lSeConcede"));
      lPStmt.setInt(4,vData.getInt("iDiasOtorgados"));
      lPStmt.setString(5,vData.getString("cObsProAle"));
      lPStmt.setInt(6,vData.getInt("iEjercicioProAle"));
      lPStmt.setInt(7,vData.getInt("iMovProcedimientoProAle"));
      lPStmt.setInt(8,vData.getInt("iMovFolioProcProAle"));
      lPStmt.setString(9,vData.getString("cNumEscrito"));
      lPStmt.setDate(10,vData.getDate("dtEscrito"));
      lPStmt.setInt(11,vData.getInt("iEjercicio"));
      lPStmt.setInt(12,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(13,vData.getInt("iMovFolioProc"));
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

  public TVDinRep fGeneraProrroga(int iEjercicio,int iMovProcedimiento) throws
      DAOException{
    TFechas Fecha = new TFechas();
    String cQry =
        "SELECT FP.CDIGITOSFOLIO, FP.ICONSECUTIVO, FP.IEJERCICIOFOL, F.DTASIGNACION, pa.IDIASOTORGADOS,PA.LSECONCEDE,PA.CNUMESCRITO,PA.DTESCRITO, " +
        "       FP1.CDIGITOSFOLIO as CDIGITOSFOLIONOT, FP1.ICONSECUTIVO as ICONSECUTIVONOT, FP1.IEJERCICIOFOL AS IEJERCICIOFOLNOT, F1.DTASIGNACION AS DTASIGNACIONNOT, " +
        "       FP2.CDIGITOSFOLIO as CDIGITOSFOLIOREC, FP2.ICONSECUTIVO AS ICONSECUTIVOREC, FP2.IEJERCICIOFOL AS IEJERCICIOFOLREC, F2.DTASIGNACION AS DTASIGNACIONREC " +
        "  FROM CYSSEGPROCEDIMIENTO P " +
        "    JOIN CYSFOLIOPROC FP ON P.IEJERCICIO = FP.IEJERCICIO AND P.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
        "    JOIN CYSPRORROGAALEGATO PA ON FP.IEJERCICIO = PA.IEJERCICIO AND FP.IMOVPROCEDIMIENTO = PA.IMOVPROCEDIMIENTO AND FP.IMOVFOLIOPROC = PA.IMOVFOLIOPROC " +
        "    JOIN GRLFOLIO F ON FP.IEJERCICIOFOL = F.IEJERCICIO AND FP.ICVEOFICINA = F.ICVEOFICINA AND FP.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO " +
        "      AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
        " LEFT JOIN CYSNOTOFICIO O1 ON FP.IEJERCICIO = O1.IEJERCICIO AND FP.IMOVPROCEDIMIENTO = O1.IMOVPROCEDIMIENTO AND FP.IMOVFOLIOPROC = O1.IMOVFOLIOPROC " +
        "    LEFT JOIN CYSFOLIOPROC FP1 ON FP1.IEJERCICIO = O1.IEJERCICIONOT AND FP1.IMOVPROCEDIMIENTO = O1.IMOVPROCEDIMIENTONOT AND FP1.IMOVFOLIOPROC = O1.IMOVFOLIOPROCNOT " +
        "    LEFT JOIN GRLFOLIO F1 ON FP1.IEJERCICIOFOL = F1.IEJERCICIO AND FP1.ICVEOFICINA = F1.ICVEOFICINA AND FP1.ICVEDEPARTAMENTO = F1.ICVEDEPARTAMENTO " +
        "    LEFT JOIN CYSFOLIOPROC FP2 ON FP2.IEJERCICIO = O1.IEJERCICIOREC AND FP2.IMOVPROCEDIMIENTO = O1.IMOVPROCEDIMIENTOREC AND FP2.IMOVFOLIOPROC = O1.IMOVFOLIOPROCNOT " +
        "    LEFT JOIN GRLFOLIO F2 ON FP2.IEJERCICIOFOL = F2.IEJERCICIO AND FP2.ICVEOFICINA = F2.ICVEOFICINA AND FP2.ICVEDEPARTAMENTO = F2.ICVEDEPARTAMENTO " +
        "      AND FP2.CDIGITOSFOLIO = F2.CDIGITOSFOLIO AND FP2.ICONSECUTIVO = F2.ICONSECUTIVO " +
        "  WHERE P.IEJERCICIO = " + iEjercicio + " AND  P.IMOVPROCEDIMIENTO = " + iMovProcedimiento +
        "   AND PA.LPRORROGAALEGATO = 1 AND FP.LESPRORROGA = 1 AND FP.IMOVFOLIOPROC IN " +
        "   (SELECT MAX(IMOVFOLIOPROC) FROM CYSPRORROGAALEGATO WHERE IEJERCICIO = " + iEjercicio +
        "   AND  IMOVPROCEDIMIENTO =  " + iMovProcedimiento + ") ";
    Vector vcRecords = new Vector();
    try{
      vcRecords = this.FindByGeneric("",cQry,dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      TVDinRep Folios = new TVDinRep();
      if(vcRecords.size() > 0){
        Folios = (TVDinRep) vcRecords.get(0);
        if(Folios.getDate("DTASIGNACION")!=null && Folios.getInt("IDIASOTORGADOS")>0)
          Folios.put("dtFin",Fecha.aumentaDisminuyeDias(Folios.getDate("DTASIGNACION"),Folios.getInt("IDIASOTORGADOS")));
        else Folios.put("dtFin","");
      }

      return Folios;
    }
  }

  public TVDinRep fGeneraAlegato(int iEjercicio,int iMovProcedimiento) throws
      DAOException{
    TFechas Fecha = new TFechas();
    String cQry =
        "SELECT FP.CDIGITOSFOLIO, FP.ICONSECUTIVO, FP.IEJERCICIOFOL, F.DTASIGNACION, pa.IDIASOTORGADOS,PA.LSECONCEDE,PA.CNUMESCRITO,PA.DTESCRITO, " +
        "       FP1.CDIGITOSFOLIO as CDIGITOSFOLIONOT, FP1.ICONSECUTIVO as ICONSECUTIVONOT, FP1.IEJERCICIOFOL AS IEJERCICIOFOLNOT, F1.DTASIGNACION AS DTASIGNACIONNOT, " +
        "       FP2.CDIGITOSFOLIO as CDIGITOSFOLIOREC, FP2.ICONSECUTIVO AS ICONSECUTIVOREC, FP2.IEJERCICIOFOL AS IEJERCICIOFOLREC, F2.DTASIGNACION AS DTASIGNACIONREC " +
        "  FROM CYSSEGPROCEDIMIENTO P " +
        "    JOIN CYSFOLIOPROC FP ON P.IEJERCICIO = FP.IEJERCICIO AND P.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
        "    JOIN CYSPRORROGAALEGATO PA ON FP.IEJERCICIO = PA.IEJERCICIO AND FP.IMOVPROCEDIMIENTO = PA.IMOVPROCEDIMIENTO AND FP.IMOVFOLIOPROC = PA.IMOVFOLIOPROC " +
        "    JOIN GRLFOLIO F ON FP.IEJERCICIOFOL = F.IEJERCICIO AND FP.ICVEOFICINA = F.ICVEOFICINA AND FP.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO " +
        "      AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
        " LEFT JOIN CYSNOTOFICIO O1 ON FP.IEJERCICIO = O1.IEJERCICIO AND FP.IMOVPROCEDIMIENTO = O1.IMOVPROCEDIMIENTO AND FP.IMOVFOLIOPROC = O1.IMOVFOLIOPROC " +
        "    LEFT JOIN CYSFOLIOPROC FP1 ON FP1.IEJERCICIO = O1.IEJERCICIONOT AND FP1.IMOVPROCEDIMIENTO = O1.IMOVPROCEDIMIENTONOT AND FP1.IMOVFOLIOPROC = O1.IMOVFOLIOPROCNOT " +
        "    LEFT JOIN GRLFOLIO F1 ON FP1.IEJERCICIOFOL = F1.IEJERCICIO AND FP1.ICVEOFICINA = F1.ICVEOFICINA AND FP1.ICVEDEPARTAMENTO = F1.ICVEDEPARTAMENTO " +
        "    LEFT JOIN CYSFOLIOPROC FP2 ON FP2.IEJERCICIO = O1.IEJERCICIOREC AND FP2.IMOVPROCEDIMIENTO = O1.IMOVPROCEDIMIENTOREC AND FP2.IMOVFOLIOPROC = O1.IMOVFOLIOPROCNOT " +
        "    LEFT JOIN GRLFOLIO F2 ON FP2.IEJERCICIOFOL = F2.IEJERCICIO AND FP2.ICVEOFICINA = F2.ICVEOFICINA AND FP2.ICVEDEPARTAMENTO = F2.ICVEDEPARTAMENTO " +
        "      AND FP2.CDIGITOSFOLIO = F2.CDIGITOSFOLIO AND FP2.ICONSECUTIVO = F2.ICONSECUTIVO " +
        "  WHERE P.IEJERCICIO = " + iEjercicio + " AND  P.IMOVPROCEDIMIENTO = " + iMovProcedimiento +
        "   AND PA.LPRORROGAALEGATO = 0 AND FP.LESALEGATO = 1 AND FP.IMOVFOLIOPROC IN " +
        "   (SELECT MAX(IMOVFOLIOPROC) FROM CYSPRORROGAALEGATO WHERE IEJERCICIO = " + iEjercicio +
        "   AND  IMOVPROCEDIMIENTO =  " + iMovProcedimiento + ") ";
    Vector vcRecords = new Vector();
    try{
      vcRecords = this.FindByGeneric("",cQry,dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      TVDinRep Folios = new TVDinRep();
      if(vcRecords.size() > 0){
        Folios = (TVDinRep) vcRecords.get(0);
        if(Folios.getDate("DTASIGNACION")!=null && Folios.getInt("IDIASOTORGADOS")>0)
          Folios.put("dtFin",Fecha.aumentaDisminuyeDias(Folios.getDate("DTASIGNACION"),Folios.getInt("IDIASOTORGADOS")));
        else Folios.put("dtFin","");
      }

      return Folios;
    }
  }

}

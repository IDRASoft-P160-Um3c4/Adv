package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLDiaNoHabil.java</p>
 * <p>Description: DAO de la entidad GRLDiaNoHabil</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDGRLDiaNoHabil extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  TFechas tFecha = new TFechas("44");
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
   * <p><b> insert into GRLDiaNoHabil(iEjercicio,dtNoHabil,cDscDia) values (?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,dtNoHabil, </b></p>
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
    Vector vcActuales = new Vector();
    Vector vcPorInsertar = new Vector();
    String cSQLActual = "";
    int iAnio;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      iAnio = vData.getInt("iEjercicio");
      cSQLActual = "SELECT iEjercicio, dtNoHabil, cDscDia FROM GRLDiaNoHabil "+
                   "WHERE iEjercicio = " + vData.getInt("iEjercicio");
      vcActuales = this.findByCustom("iEjercicio,dtNoHabil",cSQLActual);
      if (vcActuales.size() == 0){
        java.sql.Date dtFechaIni = tFecha.getDateSQL(new Integer(1), new Integer(1), new Integer(iAnio));
        java.sql.Date dtFechaFin = tFecha.getDateSQL(new Integer(31), new Integer(12), new Integer(iAnio));
        String[] aNoHabiles = VParametros.getPropEspecifica("DiaNoHabil").split(",");
        for (int j=0; j<aNoHabiles.length; j++){
          Vector vcTemp = tFecha.getDaysNumBetween(dtFechaIni,dtFechaFin,Integer.parseInt(aNoHabiles[j]));
          for(int i = 0;i < vcTemp.size();i++)
            vcPorInsertar.add(vcTemp.get(i));
        }
      }

      String lSQL = "insert into GRLDiaNoHabil(iEjercicio,dtNoHabil,cDscDia) values (?,?,?)";
      boolean lInsertado = false;
      for (int i=0; i<vcPorInsertar.size(); i++){
        vData.addPK(vData.getString("iEjercicio"));
        vData.addPK(vcPorInsertar.get(i));

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,iAnio);
        lPStmt.setDate(2, (java.sql.Date) vcPorInsertar.get(i));
        lPStmt.setString(3,"DIA NO HABIL PREDETERMINADO");
        lPStmt.executeUpdate();
        if(vcPorInsertar.get(i).equals(vData.getDate("dtNoHabil")))
          lInsertado = true;
      }
      if (!lInsertado){
        vData.addPK(vData.getString("iEjercicio"));
        vData.addPK(vData.getDate("dtNoHabil"));

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,iAnio);
        lPStmt.setDate(2, vData.getDate("dtNoHabil"));
        lPStmt.setString(3,vData.getString("cDscDia"));
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
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from GRLDiaNoHabil where iEjercicio = ? AND dtNoHabil = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,dtNoHabil, </b></p>
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
      String lSQL = "delete from GRLDiaNoHabil where iEjercicio = ? AND dtNoHabil = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setDate(2,vData.getDate("dtNoHabil"));

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
   * <p><b> update GRLDiaNoHabil set cDscDia=? where iEjercicio = ? AND dtNoHabil = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,dtNoHabil, </b></p>
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
      String lSQL = "update GRLDiaNoHabil set cDscDia=? where iEjercicio = ? AND dtNoHabil = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("dtNoHabil"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDscDia"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setDate(3,vData.getDate("dtNoHabil"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
      lSuccess = false;
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
        throw new DAOException(cMensaje);

      return vData;
    }
 }

 /**
  * Este método se encarga de obtener una fecha final a partir de una fecha inicial
  * mas el número de días que se desea contar descartando los no hábiles en BD
  * @param dtIni Date Fecha inicial a partir de la cual se cuentan días.
  * @param iNumDias int Número de días que se desea obtener como periodo.
  * @param lNaturales boolean Indica si se desea hacer el calculo con días naturales o no habiles.
  * @return Date Fecha resultante de la fecha inicial mas el número de días a contar.
  */
 public java.sql.Date getFechaFinal(java.sql.Date dtIni,int iNumDias, boolean lNaturales){
   System.out.print("Llamado con fecha=" + dtIni.toString() + " Num Dias=" + iNumDias + " Naturales=" + lNaturales);
   java.sql.Date dtFin = dtIni;
   int iEjercicio = tFecha.getIntYear(dtIni);
   Vector vcTemp = new Vector();
   String cSQL = "SELECT dtNoHabil  " +
                 "FROM GRLDiaNoHabil " +
                 "WHERE dtNoHabil BETWEEN ";
//                 "WHERE iEjercicio = " + iEjercicio + " AND dtNoHabil BETWEEN ";
  if (iNumDias == 0)
    return dtIni;

   dtFin = tFecha.aumentaDisminuyeDias(dtIni,iNumDias);
   if(lNaturales)
     return dtFin;
   else{
     try{
       cSQL += "'" + tFecha.getFechaYYYYMMDD(tFecha.aumentaDisminuyeDias(dtIni,1),"-") + "' ";
       cSQL += "AND '" + tFecha.getFechaYYYYMMDD(dtFin,"-") + "' ";
       vcTemp = super.FindByGeneric("",cSQL,dataSourceName);
     } catch(SQLException e){
       cMensaje += e.getMessage();
     }
     if(vcTemp.size() > 0)
       dtFin = this.getFechaFinal(dtFin,vcTemp.size(),lNaturales);
     return dtFin;
   }
 }

}

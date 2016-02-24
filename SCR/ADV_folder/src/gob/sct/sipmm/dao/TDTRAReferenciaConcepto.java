package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
import java.sql.Date;
/**
 * <p>Title: TDTRAReferenciaConcepto.java</p>
 * <p>Description: DAO de la entidad TRAReferenciaConcepto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRAReferenciaConcepto extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private com.micper.util.TFechas tFecha = new com.micper.util.TFechas("44");

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
   * <p><b> insert into TRAReferenciaConcepto(iEjercicio,iCveConcepto,dtIniVigencia,iCategoriaIngresos,iConceptoIngresos,iRefNumericaIngresos,lEsTarifa,lEsPorcentaje,dImporteSinAjuste,dImporteAjustado) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveConcepto,dtIniVigencia, </b></p>
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
    TFechas fecha = new TFechas();
    int iAnioActual = fecha.getIntYear(fecha.getDateSQL(fecha.getThisTime()));
    System.out.print("*****     Año Actual:  "+iAnioActual);
    Date dtIniAnioSig = new Date((iAnioActual-1900+1),0,1);
    System.out.print("*****     Fecha inicio Año siguiente:  "+dtIniAnioSig);
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL2 = "SELECT MAX(dtFinVigencia) as dtFinVigencia FROM TRAREFERENCIACONCEPTO WHERE iEjercicio = " + vData.getInt("iEjercicio") + " AND iCveConcepto = " + vData.getInt("iCveConcepto");
      String lSQL =  "insert into TRAReferenciaConcepto(iEjercicio,iCveConcepto,dtIniVigencia,iCategoriaIngresos,iConceptoIngresos,iRefNumericaIngresos,lEsTarifa,lEsPorcentaje,dImporteSinAjuste,dImporteAjustado,dtFinVigencia) values (?,?,?,?,?,?,?,?,?,?,?)";

      Vector vMaximo = this.findByCustom("iEjercicio,iCveConcepto,dtIniVigencia", lSQL2);
      java.sql.Date dtVigIni = null;
      if(vMaximo != null && vMaximo.size() > 0 && ((TVDinRep)vMaximo.get(0)).getDate("dtFinVigencia") != null){
        dtVigIni = tFecha.aumentaDisminuyeDias(((TVDinRep)vMaximo.get(0)).getDate("dtFinVigencia"),1);
      }
      else if(fecha.getThisTime().before(dtIniAnioSig)){
        dtVigIni = dtIniAnioSig;
      }
      else dtVigIni = fecha.getDateSQL(fecha.getThisTime());
      //java.sql.Date dtVigIni = tFecha.aumentaDisminuyeDias(((TVDinRep)vMaximo.get(0)).getDate("dtFinVigencia"),1);

      //AGREGAR AL ULTIMO ...

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveConcepto"));
      vData.addPK(dtVigIni);
      //...
      System.out.print("*****     "+dtVigIni);
      System.out.print("*****     "+vData.getDate("dtIniVigenciaIzq"));
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveConcepto"));
      System.out.print("*****     Concepto:"+vData.getInt("iCveConcepto"));
      lPStmt.setDate(3,dtVigIni);
      lPStmt.setInt(4,vData.getInt("iCategoriaIngresos"));
      lPStmt.setInt(5,vData.getInt("iConceptoIngresos"));
      System.out.print("*****     "+vData.getInt("iConceptoIngresos"));
      lPStmt.setInt(6,vData.getInt("iRefNumericaIngresos"));
      lPStmt.setInt(7,vData.getInt("lEsTarifaIzq"));
      lPStmt.setInt(8,vData.getInt("lEsPorcentajeIzq"));
      lPStmt.setFloat(9,vData.getFloat("dImporteSinAjusteIzq"));
      lPStmt.setFloat(10,vData.getFloat("dImporteAjustadoIzq"));
      lPStmt.setDate(11,vData.getDate("dtIniVigenciaIzq"));
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
   * <p><b> delete from TRAReferenciaConcepto where iEjercicio = ? AND iCveConcepto = ? AND dtIniVigencia = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveConcepto,dtIniVigencia, </b></p>
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
      String lSQL = "delete from TRAReferenciaConcepto where iEjercicio = ? AND iCveConcepto = ? AND dtIniVigencia = ?  ";
      //...
System.out.print(" **** iEjercicio="+vData.getInt("iEjercicio")+"  iCveConcepto=" + vData.getInt("iCveConcepto")+"dtIniVigencia="+vData.getString("dtIniVigenciaIzq"));
      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveConcepto"));
      lPStmt.setDate(3,vData.getDate("dtIniVigencia"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      sqle.printStackTrace();
      lSuccess = false;
      cMsg = ""+sqle.getErrorCode();
    } catch(Exception ex){
      ex.printStackTrace();
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
   * <p><b> update TRAReferenciaConcepto set iCategoriaIngresos=?, iConceptoIngresos=?, iRefNumericaIngresos=?, lEsTarifa=?, lEsPorcentaje=?, dImporteSinAjuste=?, dImporteAjustado=? where iEjercicio = ? AND iCveConcepto = ? AND dtIniVigencia = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveConcepto,dtIniVigencia, </b></p>
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
      String lSQL = "update TRAReferenciaConcepto set lEsTarifa=?, lEsPorcentaje=?, dImporteSinAjuste=?, dImporteAjustado=? where iEjercicio = ? AND iCveConcepto = ? AND dtIniVigencia = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveConcepto"));
      vData.addPK(vData.getString("dtIniVigencia"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lEsTarifa"));
      lPStmt.setInt(2,vData.getInt("lEsPorcentaje"));
      lPStmt.setFloat(3,vData.getFloat("dImporteSinAjuste"));
      lPStmt.setFloat(4,vData.getFloat("dImporteAjustado"));
      lPStmt.setInt(5,vData.getInt("iEjercicio"));
      lPStmt.setInt(6,vData.getInt("iCveConcepto"));
      lPStmt.setDate(7,vData.getDate("dtIniVigencia"));
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

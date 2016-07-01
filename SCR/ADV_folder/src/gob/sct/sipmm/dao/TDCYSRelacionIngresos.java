package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDCYSRelacionIngresos.java</p>
 * <p>Description: DAO de la entidad CYSRelacionIngresos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ralcocer
 * @version 1.0
 */
public class TDCYSRelacionIngresos extends DAOBase{
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
   * <p><b> insert into CYSRelacionIngresos(iCveTitulo,iEjercicio,dIngresoEne,dIngresoFeb,dIngresoMar,dIngresoAbr,dIngresoMay,dIngresoJun,dIngresoJul,dIngresoAgo,dIngresoSep,dIngresoOct,dIngresoNov,dIngresoDic,cObsRelacion) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTitulo,iEjercicio, </b></p>
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
          "insert into CYSRelacionIngresos(iCveTitulo,iEjercicio,dIngresoEne,dIngresoFeb,dIngresoMar,dIngresoAbr,dIngresoMay,dIngresoJun,dIngresoJul,dIngresoAgo,dIngresoSep,dIngresoOct,dIngresoNov,dIngresoDic,cObsRelacion) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setDouble(3,vData.getDouble("dIngresoEne"));
      lPStmt.setDouble(4,vData.getDouble("dIngresoFeb"));
      lPStmt.setDouble(5,vData.getDouble("dIngresoMar"));
      lPStmt.setDouble(6,vData.getDouble("dIngresoAbr"));
      lPStmt.setDouble(7,vData.getDouble("dIngresoMay"));
      lPStmt.setDouble(8,vData.getDouble("dIngresoJun"));
      lPStmt.setDouble(9,vData.getDouble("dIngresoJul"));
      lPStmt.setDouble(10,vData.getDouble("dIngresoAgo"));
      lPStmt.setDouble(11,vData.getDouble("dIngresoSep"));
      lPStmt.setDouble(12,vData.getDouble("dIngresoOct"));
      lPStmt.setDouble(13,vData.getDouble("dIngresoNov"));
      lPStmt.setDouble(14,vData.getDouble("dIngresoDic"));
      lPStmt.setString(15,vData.getString("cObsRelacion"));
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
   * <p><b> delete from CYSRelacionIngresos where iCveTitulo = ? AND iEjercicio = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iEjercicio, </b></p>
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
      String lSQL = "delete from CYSRelacionIngresos where iCveTitulo = ? AND iEjercicio = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));

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
   * <p><b> update CYSRelacionIngresos set dIngresoEne=?, dIngresoFeb=?, dIngresoMar=?, dIngresoAbr=?, dIngresoMay=?, dIngresoJun=?, dIngresoJul=?, dIngresoAgo=?, dIngresoSep=?, dIngresoOct=?, dIngresoNov=?, dIngresoDic=?, cObsRelacion=? where iCveTitulo = ? AND iEjercicio = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iEjercicio, </b></p>
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
      String lSQL = "update CYSRelacionIngresos set dIngresoEne=?, dIngresoFeb=?, dIngresoMar=?, dIngresoAbr=?, dIngresoMay=?, dIngresoJun=?, dIngresoJul=?, dIngresoAgo=?, dIngresoSep=?, dIngresoOct=?, dIngresoNov=?, dIngresoDic=?, cObsRelacion=? where iCveTitulo = ? AND iEjercicio = ? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDouble(1,vData.getDouble("dIngresoEne"));
      lPStmt.setDouble(2,vData.getDouble("dIngresoFeb"));
      lPStmt.setDouble(3,vData.getDouble("dIngresoMar"));
      lPStmt.setDouble(4,vData.getDouble("dIngresoAbr"));
      lPStmt.setDouble(5,vData.getDouble("dIngresoMay"));
      lPStmt.setDouble(6,vData.getDouble("dIngresoJun"));
      lPStmt.setDouble(7,vData.getDouble("dIngresoJul"));
      lPStmt.setDouble(8,vData.getDouble("dIngresoAgo"));
      lPStmt.setDouble(9,vData.getDouble("dIngresoSep"));
      lPStmt.setDouble(10,vData.getDouble("dIngresoOct"));
      lPStmt.setDouble(11,vData.getDouble("dIngresoNov"));
      lPStmt.setDouble(12,vData.getDouble("dIngresoDic"));
      lPStmt.setString(13,vData.getString("cObsRelacion"));
      lPStmt.setInt(14,vData.getInt("iCveTitulo"));
      lPStmt.setInt(15,vData.getInt("iEjercicio"));
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

 /*
  ******* De acuerdo a un Título, Ejercicio, Período inicial
          y período Final verificar que existan registros si
          es si, regresar por bimestre la sumatorio de los
          ingresos, en caso contrario regresar un 0:false

   * @param cKey String
   * @param iTitulo int
   * @throws DAOException
   * @return Vector
   */

  public Vector findRelacionIngresosBim(TVDinRep vData,Connection cnNested) throws
     DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true, lCompleto = false;
    Vector vcIngresos = new Vector();
    int iCveTitulo=0, iEjercicio=0, iBimestre=0, iPeriodoInicial=0,iPeriodoFinal=0;
    double dImporte=0.0;

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      iCveTitulo = vData.getInt("iCveTitulo");
      iEjercicio = vData.getInt("iEjercicio");
      iPeriodoInicial = vData.getInt("iPeriodoInicial");
      iPeriodoFinal = vData.getInt("iPeriodoFinal");

      String cSql = " SELECT ICVETITULO,IEJERCICIO ";
      if(iPeriodoInicial <= 1 && iPeriodoFinal >= 1) cSql += ",DINGRESOENE ";
      if(iPeriodoInicial <= 2 && iPeriodoFinal >= 2) cSql += ",DINGRESOFEB ";
      if(iPeriodoInicial <= 3 && iPeriodoFinal >= 3) cSql += ",DINGRESOMAR ";
      if(iPeriodoInicial <= 4 && iPeriodoFinal >= 4) cSql += ",DINGRESOABR ";
      if(iPeriodoInicial <= 5 && iPeriodoFinal >= 5) cSql += ",DINGRESOMAY ";
      if(iPeriodoInicial <= 6 && iPeriodoFinal >= 6) cSql += ",DINGRESOJUN ";
      if(iPeriodoInicial <= 7 && iPeriodoFinal >= 7) cSql += ",DINGRESOJUL ";
      if(iPeriodoInicial <= 8 && iPeriodoFinal >= 8) cSql += ",DINGRESOAGO ";
      if(iPeriodoInicial <= 9 && iPeriodoFinal >= 9) cSql += ",DINGRESOSEP ";
      if(iPeriodoInicial <= 10 && iPeriodoFinal >= 10) cSql += ",DINGRESOOCT ";
      if(iPeriodoInicial <= 11 && iPeriodoFinal >= 11) cSql += ",DINGRESONOV ";
      if(iPeriodoInicial <= 12 && iPeriodoFinal >= 12) cSql += ",DINGRESODIC ";
      cSql +=
          " FROM CYSRELACIONINGRESOS WHERE ICVETITULO = " + iCveTitulo +
          " AND IEJERCICIO = " + iEjercicio;

      Vector vcData = findByCustom("",cSql);
      if(vcData.size() > 0){
         TVDinRep vPeriodo = (TVDinRep)vcData.get(0);
         if(iPeriodoInicial <= 1 && iPeriodoFinal >= 1) dImporte += vPeriodo.getDouble("DINGRESOENE");
         if(iPeriodoInicial <= 2 && iPeriodoFinal >= 2){
            if(dImporte>0) lCompleto = true;
            dImporte += vPeriodo.getDouble("DINGRESOFEB");
         }
         else lCompleto = false;

         if(dImporte>0.0){
            TVDinRep vBimestre = new TVDinRep();
            iBimestre = 1;
            vBimestre.put("iBimestre",iBimestre);
            vBimestre.put("dPeriodo",dImporte);
            vBimestre.put("lCompleto",lCompleto);
            vcIngresos.add(vBimestre);
            dImporte=0.0;
            lCompleto = false;
         }

         if(iPeriodoInicial <= 3 && iPeriodoFinal >= 3) dImporte += vPeriodo.getDouble("DINGRESOMAR");
         if(iPeriodoInicial <= 4 && iPeriodoFinal >= 4){
            if(dImporte>0) lCompleto = true;
            dImporte += vPeriodo.getDouble("DINGRESOABR");
         }
         else lCompleto = false;

         if(dImporte>0.0){
            TVDinRep vBimestre = new TVDinRep();
            iBimestre = 2;
            vBimestre.put("iBimestre",iBimestre);
            vBimestre.put("dPeriodo",dImporte);
            vBimestre.put("lCompleto",lCompleto);
            vcIngresos.add(vBimestre);
            dImporte=0;
            lCompleto = false;
         }

         if(iPeriodoInicial <= 5 && iPeriodoFinal >= 5) dImporte += vPeriodo.getDouble("DINGRESOMAY");
         if(iPeriodoInicial <= 6 && iPeriodoFinal >= 6){
            if(dImporte>0) lCompleto = true;
            dImporte += vPeriodo.getDouble("DINGRESOJUN");
         }
         else lCompleto = false;

         if(dImporte>0.0){
            TVDinRep vBimestre = new TVDinRep();
            iBimestre = 3;
            vBimestre.put("iBimestre",iBimestre);
            vBimestre.put("dPeriodo",dImporte);
            vBimestre.put("lCompleto",lCompleto);
            vcIngresos.add(vBimestre);
            dImporte=0;
            lCompleto = false;
         }

         if(iPeriodoInicial <= 7 && iPeriodoFinal >= 7) dImporte += vPeriodo.getDouble("DINGRESOJUL");
         if(iPeriodoInicial <= 8 && iPeriodoFinal >= 8){
            if(dImporte>0) lCompleto = true;
            dImporte += vPeriodo.getDouble("DINGRESOAGO");
         }
         else lCompleto = false;

         if(dImporte>0.0){
            TVDinRep vBimestre = new TVDinRep();
            iBimestre = 4;
            vBimestre.put("iBimestre",iBimestre);
            vBimestre.put("dPeriodo",dImporte);
            vBimestre.put("lCompleto",lCompleto);
            vcIngresos.add(vBimestre);
            dImporte=0;
            lCompleto = false;
         }

         if(iPeriodoInicial <= 9 && iPeriodoFinal >= 9) dImporte += vPeriodo.getDouble("DINGRESOSEP");
         if(iPeriodoInicial <= 10 && iPeriodoFinal >= 10){
            if(dImporte>0) lCompleto = true;
            dImporte += vPeriodo.getDouble("DINGRESOOCT");
          }
         else lCompleto = false;

         if(dImporte>0.0){
            TVDinRep vBimestre = new TVDinRep();
            iBimestre = 5;
            vBimestre.put("iBimestre",iBimestre);
            vBimestre.put("dPeriodo",dImporte);
            vBimestre.put("lCompleto",lCompleto);
            vcIngresos.add(vBimestre);
            dImporte=0;
            lCompleto = false;
          }

         if(iPeriodoInicial <= 11 && iPeriodoFinal >= 11) dImporte += vPeriodo.getDouble("DINGRESONOV");
         if(iPeriodoInicial <= 12 && iPeriodoFinal >= 12){
            if(dImporte>0) lCompleto = true;
            dImporte += vPeriodo.getDouble("DINGRESODIC");
         }
         else lCompleto = false;

         if(dImporte>0.0){
            TVDinRep vBimestre = new TVDinRep();
            iBimestre = 6;
            vBimestre.put("iBimestre",iBimestre);
            vBimestre.put("dPeriodo",dImporte);
            vBimestre.put("lCompleto",lCompleto);
            vcIngresos.add(vBimestre);
            dImporte=0;}
      }
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
      return vcIngresos;
  }
}

public Vector findRelacionIng(TVDinRep vData) throws DAOException{
  boolean lCompleto = false;
  Vector vcIngresos1 = new Vector();
  int iCveTitulo=0, iEjercicio=0, iPeriodoInicial=0,iPeriodoFinal=0;
  int iTotal=0;
  double dImporte=0;
  TVDinRep vBimestre = new TVDinRep();

  try{
    iCveTitulo = vData.getInt("iCveTitulo");
    iEjercicio = vData.getInt("iEjercicio");
    iPeriodoInicial = vData.getInt("iPeriodoInicial");
    iPeriodoFinal = vData.getInt("iPeriodoFinal");

    String cSql = " SELECT ICVETITULO,IEJERCICIO ";
    if(iPeriodoInicial <= 1 && iPeriodoFinal >= 1) cSql += ",DINGRESOENE ";
    if(iPeriodoInicial <= 2 && iPeriodoFinal >= 2) cSql += ",DINGRESOFEB ";
    if(iPeriodoInicial <= 3 && iPeriodoFinal >= 3) cSql += ",DINGRESOMAR ";
    if(iPeriodoInicial <= 4 && iPeriodoFinal >= 4) cSql += ",DINGRESOABR ";
    if(iPeriodoInicial <= 5 && iPeriodoFinal >= 5) cSql += ",DINGRESOMAY ";
    if(iPeriodoInicial <= 6 && iPeriodoFinal >= 6) cSql += ",DINGRESOJUN ";
    if(iPeriodoInicial <= 7 && iPeriodoFinal >= 7) cSql += ",DINGRESOJUL ";
    if(iPeriodoInicial <= 8 && iPeriodoFinal >= 8) cSql += ",DINGRESOAGO ";
    if(iPeriodoInicial <= 9 && iPeriodoFinal >= 9) cSql += ",DINGRESOSEP ";
    if(iPeriodoInicial <= 10 && iPeriodoFinal >= 10) cSql += ",DINGRESOOCT ";
    if(iPeriodoInicial <= 11 && iPeriodoFinal >= 11) cSql += ",DINGRESONOV ";
    if(iPeriodoInicial <= 12 && iPeriodoFinal >= 12) cSql += ",DINGRESODIC ";
    cSql +=
        " FROM CYSRELACIONINGRESOS WHERE ICVETITULO = " + iCveTitulo +
        " AND IEJERCICIO = " + iEjercicio;

    Vector vcData = findByCustom("",cSql);
    if(vcData.size() > 0){
       iTotal = (iPeriodoInicial - 1);
       TVDinRep vPeriodo = (TVDinRep)vcData.get(0);
       if(iPeriodoInicial <= 1 && iPeriodoFinal >= 1){
          if(vPeriodo.getDouble("DINGRESOENE") > 0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOENE");
          vBimestre.put("dPeriodo1",vPeriodo.getDouble("DINGRESOENE"));
       }
       if(iPeriodoInicial <= 2 && iPeriodoFinal >= 2){
          if(vPeriodo.getDouble("DINGRESOFEB")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOFEB");
          vBimestre.put("dPeriodo2",vPeriodo.getDouble("DINGRESOFEB"));
       }
       if(iPeriodoInicial <= 3 && iPeriodoFinal >= 3){
          if(vPeriodo.getDouble("DINGRESOMAR")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOMAR");
          vBimestre.put("dPeriodo3",vPeriodo.getDouble("DINGRESOMAR"));
       }
       if(iPeriodoInicial <= 4 && iPeriodoFinal >= 4){
          if(vPeriodo.getDouble("DINGRESOABR") > 0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOABR");
          vBimestre.put("dPeriodo4",vPeriodo.getDouble("DINGRESOABR"));
       }
       if(iPeriodoInicial <= 5 && iPeriodoFinal >= 5){
          if(vPeriodo.getDouble("DINGRESOMAY")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOMAY");
          vBimestre.put("dPeriodo5",vPeriodo.getDouble("DINGRESOMAY"));
       }
       if(iPeriodoInicial <= 6 && iPeriodoFinal >= 6){
          if(vPeriodo.getDouble("DINGRESOJUN")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOJUN");
          vBimestre.put("dPeriodo6",vPeriodo.getDouble("DINGRESOJUN"));
       }
       if(iPeriodoInicial <= 7 && iPeriodoFinal >= 7){
          if(vPeriodo.getDouble("DINGRESOJUL")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOJUL");
          vBimestre.put("dPeriodo7",vPeriodo.getDouble("DINGRESOJUL"));
       }
       if(iPeriodoInicial <= 8 && iPeriodoFinal >= 8){
          if(vPeriodo.getDouble("DINGRESOAGO")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOAGO");
          vBimestre.put("dPeriodo8",vPeriodo.getDouble("DINGRESOAGO"));
       }
       if(iPeriodoInicial <= 9 && iPeriodoFinal >= 9){
          if(vPeriodo.getDouble("DINGRESOSEP")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOSEP");
          vBimestre.put("dPeriodo9",vPeriodo.getDouble("DINGRESOSEP"));
       }
       if(iPeriodoInicial <= 10 && iPeriodoFinal >= 10){
          if(vPeriodo.getDouble("DINGRESOOCT")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESOOCT");
          vBimestre.put("dPeriodo10",vPeriodo.getDouble("DINGRESOOCT"));
       }
       if(iPeriodoInicial <= 11 && iPeriodoFinal >= 11){
          if(vPeriodo.getDouble("DINGRESONOV")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESONOV");
          vBimestre.put("dPeriodo11",vPeriodo.getDouble("DINGRESONOV"));
       }
       if(iPeriodoInicial <= 12 && iPeriodoFinal >= 12){
          if(vPeriodo.getDouble("DINGRESODIC")>0) iTotal += 1;
          dImporte += vPeriodo.getDouble("DINGRESODIC");
          vBimestre.put("dPeriodo12",vPeriodo.getDouble("DINGRESODIC"));
       }

       if(iTotal == iPeriodoFinal) lCompleto = true;
       vBimestre.put("dImpTotal",dImporte);
       vBimestre.put("lCompleto",lCompleto);
       vcIngresos1.addElement(vBimestre);
//       //System.out.print("***** Relación Ingresos  " + ((TVDinRep)vcIngresos1.get(0)).toHashMap().toString());
    }

  } catch(Exception e){
   cError = e.toString();
   e.printStackTrace();
 } finally{
   if(!cError.equals(""))
     throw new DAOException(cError);
   return vcIngresos1;
   }
  }





}

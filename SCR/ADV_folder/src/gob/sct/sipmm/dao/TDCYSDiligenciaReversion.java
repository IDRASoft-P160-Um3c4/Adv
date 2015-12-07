package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDCYSDiligenciaReversion.java</p>
 * <p>Description: DAO de la entidad CYSDiligenciaReversion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
public class TDCYSDiligenciaReversion extends DAOBase{
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
  * Regresa el registro los datos de la diligencia
  * @param iEjercicioDiligencia int - Ejercicio de la diligencia
  * @param iMovProc int - MovProc
  * @throws DAOException
  * @return TVDinRep
  */
 public TVDinRep regresaDiligencia(int iEjercicioDiligencia, int iMovProc) throws DAOException{
   Vector vRegs = new Vector();
   StringBuffer sbSQL = new StringBuffer(""), sbUsuarios = null;
   boolean lSuccess = true;
   String cUsuarios = "";
   TVDinRep vDinRep = new TVDinRep();

   sbSQL.append("SELECT dr.IEJERCICIO, dr.IMOVPROCEDIMIENTO, dr.IMOVDILIGENCIA, dr.DTREGISTRO, dr.TSDILIGENCIA, dr.COBSDILIGENCIA ");
   sbSQL.append("FROM  CYSDILIGENCIAREVERSION dr ");
   sbSQL.append("WHERE dr.IEJERCICIO = "+iEjercicioDiligencia+" AND dr.IMOVPROCEDIMIENTO = "+iMovProc+" ");
   sbSQL.append("ORDER BY dr.IMOVDILIGENCIA DESC ");

   try{
     //Obtiene datos de la diligencia
     vRegs = this.FindByGeneric("",sbSQL.toString(),dataSourceName);
     if(vRegs.size()>0){
       vDinRep = (TVDinRep) vRegs.get(0);
       sbSQL = new StringBuffer("");
       sbSQL.append("SELECT ud.ICVEUSUARIO, u.CNOMBRE || ' ' || u.CAPPATERNO || ' ' || u.CAPMATERNO as cNombreUsuario, ud.cNomUsu ");
       sbSQL.append("FROM  CYSUSUDILIGENCIA ud ");
       sbSQL.append("left JOIN SEGUSUARIO u ON u.ICVEUSUARIO = ud.ICVEUSUARIO ");
       sbSQL.append("WHERE ud.IEJERCICIO = "+iEjercicioDiligencia+" AND ud.IMOVPROCEDIMIENTO = "+iMovProc+" AND ud.IMOVDILIGENCIA = "+ vDinRep.getString("IMOVDILIGENCIA") +" ");
       sbSQL.append("ORDER BY u.CNOMBRE ");

       vRegs = new Vector();
       //Obtiene datos de los usuarios asignados a la diligencia
       vRegs = this.FindByGeneric("",sbSQL.toString(),dataSourceName);
       if(vRegs.size()>0){
         TVDinRep vDinRepUsu = new TVDinRep();
         sbUsuarios = new StringBuffer();
         for(int iCont=0;iCont<vRegs.size();iCont++){
           vDinRepUsu = (TVDinRep) vRegs.get(iCont);
           String cUsuario = vDinRepUsu.getString("cNomUsu").equals("")   ? vDinRepUsu.getString("cNombreUsuario") : vDinRepUsu.getString("cNomUsu") ;
           if(iCont==0)
             sbUsuarios.append(cUsuario);
           else
             sbUsuarios.append(","+cUsuario);
         }
         vDinRep.put("cUsuarios",sbUsuarios.toString());
         cUsuarios = sbUsuarios.toString();
       }
       else{
         vDinRep.put("cUsuarios","no hay usuarios asignados a la diligencia");
       }

     }
     else{
       lSuccess = false;
     }

   }
   catch(Exception exRec){
     exRec.printStackTrace();
     cError = exRec.toString();
   } finally{
     if(!cError.equals("") || !lSuccess)
       throw new DAOException(cError);
     return vDinRep;
   }

 }


  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into CYSDiligenciaReversion(iEjercicio,iMovProcedimiento,iMovDiligencia,dtRegistro,tsDiligencia,cObsDiligencia) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovProcedimiento,iMovDiligencia, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws DAOException{
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
      String lSQL =
          "insert into CYSDiligenciaReversion(iEjercicio,iMovProcedimiento,iMovDiligencia,dtRegistro,tsDiligencia,cObsDiligencia) values (?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovDiligencia) AS iMovDiligencia from CYSDiligenciaReversion " +
                                      " WHERE iEjercicio = " + vData.getString("iEjercicio") + " and iMovProcedimiento = " + vData.getString("iMovProcedimiento"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovDiligencia",vUltimo.getInt("iMovDiligencia") + 1);
      }else
         vData.put("iMovDiligencia",1);
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovDiligencia"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovDiligencia"));
      lPStmt.setDate(4,vData.getDate("dtRegistro"));
      lPStmt.setTimestamp(5,vData.getTimeStamp("tsDiligencia"));
      lPStmt.setString(6,vData.getString("cObsDiligencia"));
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
   * <p><b> delete from CYSDiligenciaReversion where iEjercicio = ? AND iMovProcedimiento = ? AND iMovDiligencia = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovProcedimiento,iMovDiligencia, </b></p>
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
      String lSQL = "delete from CYSDiligenciaReversion where iEjercicio = ? AND iMovProcedimiento = ? AND iMovDiligencia = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(3,vData.getInt("iMovDiligencia"));

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
   * <p><b> update CYSDiligenciaReversion set dtRegistro=?, tsDiligencia=?, cObsDiligencia=? where iEjercicio = ? AND iMovProcedimiento = ? AND iMovDiligencia = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovProcedimiento,iMovDiligencia, </b></p>
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
      String lSQL = "update CYSDiligenciaReversion set dtRegistro=?, tsDiligencia=?, cObsDiligencia=? where iEjercicio = ? AND iMovProcedimiento = ? AND iMovDiligencia = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovProcedimiento"));
      vData.addPK(vData.getString("iMovDiligencia"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setTimestamp(2,vData.getTimeStamp("tsDiligencia"));
      lPStmt.setString(3,vData.getString("cObsDiligencia"));
      lPStmt.setInt(4,vData.getInt("iEjercicio"));
      lPStmt.setInt(5,vData.getInt("iMovProcedimiento"));
      lPStmt.setInt(6,vData.getInt("iMovDiligencia"));
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

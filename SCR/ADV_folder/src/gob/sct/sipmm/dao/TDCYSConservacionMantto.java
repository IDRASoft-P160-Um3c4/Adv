package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCYSConservacionMantto.java</p>
 * <p>Description: DAO de la entidad CYSConservacionMantto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSConservacionMantto extends DAOBase{
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
   * <p><b> insert into CYSConservacionMantto(iEjercicio,iMovConservacionMantto,iTipoConservacion,dtRegistro,dtPresentacion,iCveTitulo,lConFotografias,cObsConservacionMantto) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovConservacionMantto, </b></p>
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
          "insert into CYSConservacionMantto(iEjercicio,iMovConservacionMantto,iTipoConservacion,dtRegistro,dtPresentacion,iCveTitulo," +
          "lConFotografias,cObsConservacionMantto,cNumEscrito,dtEscrito,dtIniPresUsuPuerto,dtFinPresUsuPuerto) " +
          "values (?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovConservacionMantto) AS iMovConservacionMantto from CYSConservacionMantto");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovConservacionMantto",vUltimo.getInt("iMovConservacionMantto") + 1);
      }else
         vData.put("iMovConservacionMantto",1);

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovConservacionMantto"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovConservacionMantto"));
      lPStmt.setInt(3,vData.getInt("iTipoConservacion"));
      lPStmt.setDate(4,vData.getDate("dtRegistro"));
      lPStmt.setDate(5,vData.getDate("dtPresentacion"));
      lPStmt.setInt(6,vData.getInt("iCveTitulo"));
      lPStmt.setInt(7,vData.getInt("lConFotografias"));
      lPStmt.setString(8,vData.getString("cObsConservacionMantto"));
      lPStmt.setString(9,vData.getString("cNumEscrito"));
      lPStmt.setDate(10,vData.getDate("dtEscrito"));
      lPStmt.setDate(11,vData.getDate("dtIniPresUsuPuerto"));
      lPStmt.setDate(12,vData.getDate("dtFinPresUsuPuerto"));
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
   * <p><b> delete from CYSConservacionMantto where iEjercicio = ? AND iMovConservacionMantto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovConservacionMantto, </b></p>
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
      String lSQL = "delete from CYSConservacionMantto where iEjercicio = ? AND iMovConservacionMantto = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovConservacionMantto"));

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
   * <p><b> update CYSConservacionMantto set iTipoConservacion=?, dtRegistro=?, dtPresentacion=?, iCveTitulo=?, lConFotografias=?, cObsConservacionMantto=? where iEjercicio = ? AND iMovConservacionMantto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovConservacionMantto, </b></p>
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
      String lSQL = "update CYSConservacionMantto set iTipoConservacion=?, dtRegistro=?, dtPresentacion=?, iCveTitulo=?, " +
                    "lConFotografias=?, cObsConservacionMantto=?, cNumEscrito=?, dtEscrito=?, dtIniPresUsuPuerto=?, " +
                    "dtFinPresUsuPuerto= ? where iEjercicio = ? AND iMovConservacionMantto = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovConservacionMantto"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iTipoConservacion"));
      lPStmt.setDate(2,vData.getDate("dtRegistro"));
      lPStmt.setDate(3,vData.getDate("dtPresentacion"));
      lPStmt.setInt(4,vData.getInt("iCveTitulo"));
      lPStmt.setInt(5,vData.getInt("lConFotografias"));
      lPStmt.setString(6,vData.getString("cObsConservacionMantto"));
      lPStmt.setString(7,vData.getString("cNumEscrito"));
      lPStmt.setDate(8,vData.getDate("dtEscrito"));
      lPStmt.setDate(9,vData.getDate("dtIniPresUsuPuerto"));
      lPStmt.setDate(10,vData.getDate("dtFinPresUsuPuerto"));
      lPStmt.setInt(11,vData.getInt("iEjercicio"));
      lPStmt.setInt(12,vData.getInt("iMovConservacionMantto"));
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
 /**
  * Agregado por Rafael Alcocer Caldera 28/Nov/2006
  *
  * @param cKey String
  * @param iTitulo int
  * @param dtFecha Date
  * @throws DAOException
  * @return Vector
  */
 public Vector findBySitObligacion(String cKey, int iTitulo, java.sql.Date dtFecha) throws DAOException{
   Vector vcSitObligacion = new Vector();
   TFechas tFechas = new TFechas();
   int iEjercicio = tFechas.getIntYear(dtFecha);

     String cSQL = "select iEjercicio, " +
                   "       iMovConservacionMantto, " +
                   "       iTipoConservacion, " +
                   "       dtRegistro, " +
                   "       dtPresentacion, " +
                   "       iCveTitulo, " +
                   "       lConFotografias, " +
                   "       cObsConservacionMantto, " +
                   "       cNumEscrito, " +
                   "       dtEscrito, " +
                   "       dtIniPresUsuPuerto, " +
                   "       dtFinPresUsuPuerto " +
                   " from CYSConservacionMantto " +
                   " where iCveTitulo = " + iTitulo +
                   " and iEjercicio = " + iEjercicio +
                   " and iMovConservacionMantto = (select max(iMovConservacionMantto) from CYSConservacionMantto where iCveTitulo = " + iTitulo + ")";

    // findByCustom regresa un Vector no null aunque puede estar vacio
    // del query solo voy a obtener un registro
    Vector vcRecords = findByCustom(cKey, cSQL);
    TVDinRep vDinRep = new TVDinRep();

    if (vcRecords.size() > 0) {
      // Solo hay un registro obtenido
      vDinRep = (TVDinRep)vcRecords.get(0);

      if (vDinRep.getDate("dtPresentacion") != null) {
        vcSitObligacion.add(new Boolean(true));
      } else {
        vcSitObligacion.add(new Boolean(false));
      }
    } else
        vcSitObligacion.add(new Boolean(false));

    return vcSitObligacion;
 }

 public Vector findConservacion(TVDinRep vData) throws DAOException{
 Vector vcPMDP = new Vector();
 int iCveTitulo=0, iEjercicioI=0,iEjercicioF=0;
 java.sql.Date dtConcesion =null;
 TFechas dtFecha = new TFechas();
 TVDinRep vDatos, vNoExisten;
 boolean lExiste = false;


 try{
    iCveTitulo = vData.getInt("iCveTitulo");
    dtConcesion = vData.getDate("dtIniVigencia");
    iEjercicioI = (dtFecha.getIntYear(dtConcesion)+1);
    iEjercicioF = dtFecha.getIntYear(dtFecha.TodaySQL());
    String cSQL = " SELECT DISTINCT year(DTPRESENTACION) AS IEJERCICIO FROM  CYSCONSERVACIONMANTTO  " +
                  " WHERE ICVETITULO = " + iCveTitulo + " ORDER BY year(DTPRESENTACION) ";
    Vector vcData = findByCustom("",cSQL);
    if(vcData.size()>0){
       while(iEjercicioI<=iEjercicioF){
          lExiste = false;
          for(int i=0;i<vcData.size();i++){
            vDatos = (TVDinRep) vcData.get(i);
            if(iEjercicioI==vDatos.getInt("iEjercicio")){
               vcData.removeElementAt(i);
               lExiste = true;
               break;
            }
          }

          if(!lExiste){
             vNoExisten = new TVDinRep();
             vNoExisten.put("iEjercicio",iEjercicioI);
             vNoExisten.put("cMotivo","NO PRESENTADO");
             vcPMDP.add(vNoExisten);
          }
          iEjercicioI++;
       }
    }

 } catch(Exception e){
 cError = e.toString();
 e.printStackTrace();
} finally{
 if(!cError.equals(""))
   throw new DAOException(cError);
 return vcPMDP;
 }
}

}

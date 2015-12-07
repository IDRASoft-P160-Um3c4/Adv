package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCYSEdoFinanciero.java</p>
 * <p>Description: DAO de la entidad CYSEdoFinanciero</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDCYSEdoFinanciero extends DAOBase{
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
   * <p><b> insert into CYSEdoFinanciero(iEjercicio,iMovEdoFinanciero,dtRegistro,dtPresentacion,dtPublicacion,iCveTitulo,cObsEdoFinanciero) values (?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
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
          "insert into CYSEdoFinanciero(iEjercicio,iMovEdoFinanciero,dtRegistro,dtPresentacion,dtPublicacion,iCveTitulo," +
          "cObsEdoFinanciero,cNumEscrito,dtEscrito,iPubNacionalLocal,cNomPeriodicoNacional,cNomPeriodicoLocal,dtLocal," +
          "lEsValidaPubNac,lEsValidaPubLocal) " +
          " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iMovEdoFinanciero) AS iMovEdoFinanciero from CYSEdoFinanciero");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iMovEdoFinanciero",vUltimo.getInt("iMovEdoFinanciero") + 1);
      }else
         vData.put("iMovEdoFinanciero",1);

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovEdoFinanciero"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovEdoFinanciero"));
      lPStmt.setDate(3,vData.getDate("dtRegistro"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setDate(5,vData.getDate("dtPublicacion"));
      lPStmt.setInt(6,vData.getInt("iCveTitulo"));
      lPStmt.setString(7,vData.getString("cObsEdoFinanciero"));
      lPStmt.setString(8,vData.getString("cNumEscrito"));
      lPStmt.setDate(9,vData.getDate("dtEscrito"));
      lPStmt.setInt(10,vData.getInt("iPubNacionalLocal"));
      lPStmt.setString(11,vData.getString("cNomPeriodicoNacional"));
      lPStmt.setString(12,vData.getString("cNomPeriodicoLocal"));
      lPStmt.setDate(13,vData.getDate("dtLocal"));
      lPStmt.setInt(14,vData.getInt("lEsValidaPubNac"));
      lPStmt.setInt(15,vData.getInt("lEsValidaPubLocal"));
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
   * <p><b> delete from CYSEdoFinanciero where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
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
      String lSQL = "delete from CYSEdoFinanciero where iEjercicio = ? AND iMovEdoFinanciero = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iMovEdoFinanciero"));

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
   * <p><b> update CYSEdoFinanciero set dtRegistro=?, dtPresentacion=?, dtPublicacion=?, iCveTitulo=?, cObsEdoFinanciero=? where iEjercicio = ? AND iMovEdoFinanciero = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iMovEdoFinanciero, </b></p>
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
      String lSQL = "update CYSEdoFinanciero set dtRegistro=?, dtPresentacion=?, dtPublicacion=?, iCveTitulo=?, cObsEdoFinanciero=?, " +
                    "cNumEscrito=?, dtEscrito=?, iPubNacionalLocal=?, cNomPeriodicoNacional=?, cNomPeriodicoLocal=?, dtLocal=?, " +
                    "lEsValidaPubNac=?, lEsValidaPubLocal=? where iEjercicio = ? AND iMovEdoFinanciero = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovEdoFinanciero"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setDate(2,vData.getDate("dtPresentacion"));
      lPStmt.setDate(3,vData.getDate("dtPublicacion"));
      lPStmt.setInt(4,vData.getInt("iCveTitulo"));
      lPStmt.setString(5,vData.getString("cObsEdoFinanciero"));
      lPStmt.setString(6,vData.getString("cNumEscrito"));
      lPStmt.setDate(7,vData.getDate("dtEscrito"));
      lPStmt.setInt(8,vData.getInt("iPubNacionalLocal"));
      lPStmt.setString(9,vData.getString("cNomPeriodicoNacional"));
      lPStmt.setString(10,vData.getString("cNomPeriodicoLocal"));
      lPStmt.setDate(11,vData.getDate("dtLocal"));
      lPStmt.setInt(12,vData.getInt("lEsValidaPubNac"));
      lPStmt.setInt(13,vData.getInt("lEsValidaPubLocal"));
      lPStmt.setInt(14,vData.getInt("iEjercicio"));
      lPStmt.setInt(15,vData.getInt("iMovEdoFinanciero"));
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

 public Vector findEdoFinanciero(TVDinRep vData) throws DAOException{
 Vector vcEdoFin = new Vector();
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
    String cSQL = " SELECT DISTINCT year(DTPRESENTACION) AS IEJERCICIO FROM  CYSEDOFINANCIERO  " +
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
             vcEdoFin.add(vNoExisten);
          }
          iEjercicioI++;
       }
       if (vcEdoFin.isEmpty()){
         vNoExisten = new TVDinRep();
         vNoExisten.put("iEjercicio",iEjercicioI);
         vNoExisten.put("cMotivo","NO PRESENTADO");
         vcEdoFin.add(vNoExisten);
       }
    } else {
      vNoExisten = new TVDinRep();
      vNoExisten.put("iEjercicio",iEjercicioI);
      vNoExisten.put("cMotivo","NO PRESENTADO");
      vcEdoFin.add(vNoExisten);
    }

 } catch(Exception e){
 cError = e.toString();
 e.printStackTrace();
} finally{
 if(!cError.equals(""))
   throw new DAOException(cError);
 return vcEdoFin;
 }
}


}

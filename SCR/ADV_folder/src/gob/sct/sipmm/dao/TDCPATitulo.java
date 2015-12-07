package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCPATitulo.java</p>
 * <p>Description: DAO de la entidad CPATitulo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author lmorales
 * @version 1.0
 */
public class TDCPATitulo extends DAOBase{
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
   * <p><b> insert into CPATitulo(iCveTitulo,iCveTipoTitulo,cNumTitulo,cNumTituloAnterior,dtVigenciaTitulo,dtIniVigenciaTitulo,cZonaFedAfectadaTerrestre,cZonaFedAfectadaAgua,cObjetoTitulo,iCveUsoTitulo,cMontoInversion,lFirmado) values (?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTipoTitulo,iCveTitulo,iCveUsoTitulo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    Statement lStmt1 = null;
    Statement lStmt2 = null;
    Statement lStmt3 = null;
    boolean lSuccess = true;
    int iCveTitulo = this.getiCveTitulo();
    int iOrden = this.getiOrden(iCveTitulo, vData.getInt("iCvePersona"));
    TFechas tFechas = new TFechas();
    int[] iUpdate = null;
    try{
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
      lStmt1 = conn.createStatement();
      lStmt2 = conn.createStatement();
      lStmt3 = conn.createStatement();
      vData.addPK(""+iCveTitulo);

      String lSQL =
          " INSERT INTO CPATitulo( iCveTitulo, iCveTipoTitulo, cNumTitulo, cNumTituloAnterior, "+
                                 " dtVigenciaTitulo, dtIniVigenciaTitulo, cZonaFedAfectadaTerrestre, "+
                                 " cZonaFedAfectadaAgua, cObjetoTitulo, iCveUsoTitulo, cMontoInversion, dtPublicacionDOF, lFirmado,"+
                                 " cZonaOpNoExcTerrestre,cZonaOpNoExcAgua,cZonaFedTotalTerrestre,cZonaFedTotalAgua)" +
                         " VALUES("+iCveTitulo+", "+vData.getInt("iCveTipoTitulo")+",'"+vData.getInt("cNumTitulo")+"','', "+
                                  ""+vData.getDateStringSQL("dtVigenciaTitulo")+","+vData.getDateStringSQL("dtIniVigenciaTitulo")+",'"+vData.getString("cZonaFedAfectadaTerrestre")+"',"+
                                  "'"+vData.getString("cZonaFedAfectadaAgua")+"','"+vData.getString("cObjetoTitulo")+"',"+vData.getString("iCveUsoTitulo")+",'"+vData.getString("cMontoInversion")+"',"+vData.getDateStringSQL("dtPublicacionDOF")+",0,"+
                                  vData.getString("cZonaOpNoExcTerrestre")+","+vData.getString("cZonaOpNoExcAgua")+","+vData.getString("cZonaFedTotalTerrestre")+","+vData.getString("cZonaFedTotalAgua")+")";
      lStmt1.executeUpdate(lSQL);

      lSQL =
          " INSERT INTO CPASolTitulo(iCveTitulo, iEjercicio, iNumSolicitud, iCveConceptoSolicitud )"+
                            " VALUES("+iCveTitulo+", "+vData.getInt("iEjercicio")+", "+vData.getInt("iNumSolicitud")+", "+vData.getInt("iCveConceptoSolicitud")+") ";
      lStmt2.executeUpdate(lSQL);

      lSQL =
          " INSERT INTO CPATitular(iCveTitulo, iCveTitular, iOrden ) "+
                          " VALUES("+iCveTitulo+", "+vData.getInt("iCvePersona")+", "+iOrden+") ";
      lStmt3.executeUpdate(lSQL);

      conn.commit();


    }
    catch(Exception ex){
      ex.printStackTrace();
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
        if(lStmt1 != null){
          lStmt1.close();
        }
        if(lStmt2 != null){
          lStmt2.close();
        }
        if(lStmt3 != null){
          lStmt3.close();
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
   * <p><b> delete from CPATitulo where iCveTipoTitulo = ? AND iCveTitulo = ? AND iCveUsoTitulo = ?  </b></p>
   * <p><b> Campos Llave: iCveTipoTitulo,iCveTitulo,iCveUsoTitulo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested)throws
    DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    Statement lStmt1 = null;
    Statement lStmt2 = null;
    Statement lStmt3 = null;
    boolean lSuccess = true;
    String cMsg = "";
    try{
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
      lStmt1 = conn.createStatement();
      lStmt2 = conn.createStatement();
      lStmt3 = conn.createStatement();

      String lSQL =
          " DELETE FROM CPATitular "+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo")+
          " AND iCveTitular = "+vData.getInt("iCvePersona");
      lStmt1.executeUpdate(lSQL);

      lSQL =
          " DELETE FROM CPASolTitulo "+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo")+
          " AND iEjercicio = "+vData.getInt("iEjercicio")+
          " AND iNumSolicitud = "+vData.getInt("iNumSolicitud");
      lStmt2.executeUpdate(lSQL);

      lSQL =
          " DELETE FROM CPATitulo "+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo");
      lStmt3.executeUpdate(lSQL);

      conn.commit();

    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = ""+sqle.getErrorCode();
    } catch(Exception ex){
      warn("delete",ex);
      try{
        conn.rollback();
      } catch(Exception e){
        fatal("delete.rollback",e);
      }
      lSuccess = false;
    } finally{
      try{
        if(lStmt1 != null){
          lStmt1.close();
        }
        if(lStmt2 != null){
          lStmt2.close();
        }
        if(lStmt3 != null){
          lStmt3.close();
        }

          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();

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
   * <p><b> update CPATitulo set cNumTituloAnterior=?, dtVigenciaTitulo=?, dtIniVigenciaTitulo=?, cZonaFedAfectadaTerrestre=?, cZonaFedAfectadaAgua=?, cObjetoTitulo=?, iCveUsoTitulo=?, cMontoInversion=?, lFirmado=? where iCveTipoTitulo = ? AND iCveTitulo = ? AND iCveUsoTitulo = ?  </b></p>
   * <p><b> Campos Llave: iCveTipoTitulo,iCveTitulo,iCveUsoTitulo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    boolean lSuccess = true;
    Statement lStmt1 = null;
    Statement lStmt2 = null;
/*    lPStmt.setString(1,vData.getString("cNomAreaAgua"));
    lPStmt.setString(2,vData.getString("cLocalizacion"));
    lPStmt.setString(3,vData.getString("iNoLocalizacion"));
    lPStmt.setFloat(4,vData.getFloat("dLongitudM"));
    lPStmt.setFloat(5,vData.getFloat("dAreaM2"));
    lPStmt.setFloat(6,vData.getFloat("dAnchoPlantillaM"));
    lPStmt.setFloat(7,vData.getFloat("dProfundidadM"));
    lPStmt.setFloat(8,vData.getFloat("dDiamMaxCiabogaM"));
    lPStmt.setDate(9,vData.getDate("dtSondeo"));
    lPStmt.setString(10,vData.getString("cObses"));
    lPStmt.setInt(11,vData.getInt("iCveClasificaAA"));
    lPStmt.setInt(12,vData.getInt("lActivo"));
    lPStmt.setInt(13,vData.getInt("iEjercicio"));
    lPStmt.setInt(14,vData.getInt("iCvePuerto"));
    lPStmt.setInt(15,vData.getInt("iConsecutivoAA"));
*/
    try{
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
      lStmt1 = conn.createStatement();
      lStmt2 = conn.createStatement();
      String lSQL =
          " UPDATE CPATitulo SET "+
          " iCveTipoTitulo = "+vData.getInt("iCveTipoTitulo")+
          " ,cNumTitulo = '"+vData.getInt("cNumTitulo")+"'"+
//          " ,cNumTituloAnterior = "+"'"+vData.getInt("cNumTituloAnterior")+"'"+
          " ,dtVigenciaTitulo = "+vData.getDateStringSQL("dtVigenciaTitulo")+
          " ,dtIniVigenciaTitulo = "+vData.getDateStringSQL("dtIniVigenciaTitulo")+
          " ,cZonaFedAfectadaTerrestre = '"+vData.getString("cZonaFedAfectadaTerrestre")+"'"+
          " ,cZonaFedAfectadaAgua = '"+vData.getString("cZonaFedAfectadaAgua")+"'"+
          " ,cObjetoTitulo = '"+vData.getString("cObjetoTitulo")+"'"+
          " ,iCveUsoTitulo = "+vData.getInt("iCveUsoTitulo")+
          " ,cMontoInversion = '"+vData.getString("cMontoInversion")+"'"+
          " ,dtPublicacionDOF = "+vData.getDateStringSQL("dtPublicacionDOF")+
          " ,cZonaOpNoExcTerrestre = "+vData.getString("cZonaOpNoExcTerrestre")+
          " ,cZonaOpNoExcAgua = "+vData.getString("cZonaOpNoExcAgua")+
          " ,cZonaFedTotalTerrestre = "+vData.getString("cZonaFedTotalTerrestre")+
          " ,cZonaFedTotalAgua = "+vData.getString("cZonaFedTotalAgua")+
//          " ,lFirmado "+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo");
      lStmt1.executeUpdate(lSQL);

      lSQL =
          " UPDATE CPASolTitulo SET "+
          " iCveConceptoSolicitud = "+vData.getInt("iCveConceptoSolicitud")+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo")+
          " AND iEjercicio = "+vData.getInt("iEjercicio")+
          " AND iNumSolicitud = "+vData.getInt("iNumSolicitud");
      lStmt2.executeUpdate(lSQL);


      conn.commit();

    } catch(Exception ex){
      ex.printStackTrace();
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
        if(lStmt1 != null){
          lStmt1.close();
        }
        if(lStmt2 != null){
          lStmt2.close();
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

 public int getiCveTitulo()throws DAOException{
   DbConnection dbConn = null;
   Connection conn = null;
   Statement lStmt = null;
   ResultSet rs = null;
   int iCveTitulo = 0;
   try{
     dbConn = new DbConnection(dataSourceName);
     conn = dbConn.getConnection();
     lStmt = conn.createStatement();

     String lSQL = " select MAX(iCveTitulo) AS iCveTitulo from CPATitulo ";
     rs = lStmt.executeQuery(lSQL);
     while(rs.next()){
       iCveTitulo = rs.getInt("iCveTitulo");
     }
     iCveTitulo++;

   }
   catch(SQLException sqle){
     sqle.getErrorCode();
   }
   catch(Exception ex){
     ex.printStackTrace();
   }
   finally{
     try{
       if(lStmt != null){
         lStmt.close();
       }

         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();

     } catch(Exception ex2){
       warn("delete.close",ex2);
     }

     return iCveTitulo;
   }
 }

 public int getiOrden(int iCveTitulo, int iCveTitular)throws DAOException{
   DbConnection dbConn = null;
   Connection conn = null;
   Statement lStmt = null;
   ResultSet rs = null;
   int iOrden = 0;
   try{
     dbConn = new DbConnection(dataSourceName);
     conn = dbConn.getConnection();
     lStmt = conn.createStatement();

     String lSQL = " select MAX(iOrden) AS iOrden from CPATitular "+
                   " WHERE iCveTitulo = "+iCveTitulo+
                   " AND iCveTitular = "+iCveTitular;

     rs = lStmt.executeQuery(lSQL);
     while(rs.next()){
       iOrden = rs.getInt("iOrden");
     }
     iOrden++;

   }
   catch(SQLException sqle){
     sqle.getErrorCode();
   }
   catch(Exception ex){
     ex.printStackTrace();
   }
   finally{
     try{
       if(lStmt != null){
         lStmt.close();
       }

         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();

     } catch(Exception ex2){
       warn("delete.close",ex2);
     }

     return iOrden;
   }
 }

 /**
  * Regresa un vector con el historial de un título dado, asi como sus ubicaciones.
  * @param iCveTitulo int          - Clave del Título inicial para obtener todos los posteriores, si este es mayor a cero se buscará por iCveTitulo.
  * @param iCveTituloAnterior int  - Clave del Título Anterior; como es un método recursivo una vez encontrado el primer título se llamará asímismo y iCveTitulo se recibira 0 y este con la clave encontrada
  * @param lFirmado int            - 0 si se quiere buscar no firmados, 1 si se quiere buscar firmados.
  * @param lCveTipoTitulo int      - Indica la clave del tipo de título.
  * @param vRegresa Vector         - La primera vez recibe un vector vacio ya que éste es el que va almacenando el histórico
  * @param iInfQueRegresa int      - Entero que recibe para saber como se hace la búsqueda de las ubicaciones 1 = Toda la información, 2 = Solo Puertos, 3=Diferentes de Puertos
  * @throws DAOException
  * @return Vector                 - Vector que contiene los datos, de donde se llame hacer el cast a VO Dinámicos, las ubicaciones van en "Ubicaciones" del VO Dinamico, ej. TDEstSegtoFormulario.insert (línea aprox 200)
  */
 public Vector datosTitulo(int iCveTitulo, int iCveTituloAnterior, int lFirmado, int lCveTipoTitulo, Vector vRegresa, int iInfQueRegresa) throws DAOException{
    String cSQL="";
    Vector vDatosTemp = new Vector(), vPuertos = new Vector();
    TVDinRep vDinRep = null;

    cSQL =
    "SELECT t.ICVETITULO, t.ICVETIPOTITULO, t.CNUMTITULO, t.ICVETITULOANTERIOR, t.CNUMTITULORELACIONADO, " +
    "       t.DTVIGENCIATITULO, t.DTINIVIGENCIATITULO, t.LFIRMADO " +
    "FROM CPATITULO t " +
    "WHERE ";

    //Si iCveTitulo es mayor a cero se busca por título, la idea es q este sea el título base para generar el historial.
    if(iCveTitulo>0)
      cSQL+="t.ICVETITULO = "+iCveTitulo+" AND t.ICVETIPOTITULO = "+lCveTipoTitulo+" AND t.LFIRMADO = "+lFirmado;
    else
      cSQL += " t.ICVETITULOANTERIOR = " + iCveTituloAnterior+" AND t.ICVETIPOTITULO = "+lCveTipoTitulo+" AND t.LFIRMADO = "+lFirmado;

    vDatosTemp = this.findByCustom("",cSQL);

    //Si encontró registros
    if(vDatosTemp.size()>0){
      vDinRep =  new TVDinRep();
      vDinRep = (TVDinRep) vDatosTemp.get(0);

      //Se buscan los datos de las ubicaciones
      cSQL =
          "SELECT tu.ICVETITULO, tu.INUMMOVUBICACION, tu.CCALLETITULO, tu.CKM, tu.CCOLONIATITULO, tu.ICVEPUERTO, " +
          "       p.CDSCPUERTO " +
          "FROM CPATITULOUBICACION tu " +
          "JOIN GRLPUERTO p ON p.ICVEPUERTO = tu.ICVEPUERTO " +
          "where tu.ICVETITULO = "+vDinRep.getInt("ICVETITULO");

      if(iInfQueRegresa == 2)
        cSQL+=" AND tu.ICVEPUERTO > 0 ";
      else if(iInfQueRegresa == 3)
        cSQL+=" AND tu.ICVEPUERTO == 0 ";

      vPuertos = this.findByCustom("",cSQL);

      //Si encuentra ubicaciones las agrega al VO dinámico y si no agrega a "Ubicaciones un vector vacio.
      if(vPuertos.size()>0)
        vDinRep.put("Ubicaciones",vPuertos);
      else
        vDinRep.put("Ubicaciones",new Vector());
      vRegresa.addElement(vDinRep);

      //Se llama así mismo para buscar als relaciones
      if(vDinRep.getInt("ICVETITULO")>0)
        this.datosTitulo(0,vDinRep.getInt("ICVETITULO"),1,1,vRegresa,2);
    }
    //Si no encontró registros regresa un vector vacio.
    return vRegresa;

 }


}

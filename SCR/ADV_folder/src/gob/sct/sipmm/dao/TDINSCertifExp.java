package gob.sct.sipmm.dao;
import java.sql.*; 
import java.util.*; 
import com.micper.excepciones.*;
import com.micper.ingsw.*; 
import com.micper.seguridad.vo.*; 
import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDINSCertifExp.java</p>
 * <p>Description: DAO de la entidad INSCertifExpedidos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
public class TDINSCertifExp extends DAOBase{
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
   * <p><b> insert into INSCertifExpedidos(iCveVehiculo,iConsecutivoCertExp,iNumSolicitud,iEjercicio,iTipoCertificado,iConsecutivoCertifExp,dtExpedicionCert,dtIniVigencia,dtFinVigencia,lVigente,lExpedicion,iCveGrupoCertif) values (?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iConsecutivoCertExp, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    TFechas fecha = new TFechas();
    int iAnioActual = fecha.getIntYear(fecha.getDateSQL(fecha.getThisTime()));
    PreparedStatement lPStmt = null;
    PreparedStatement lPStmt1 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into INSCertifExpedidos(iCveVehiculo,iConsecutivoCertExp,iNumSolicitud,iEjercicio,iTipoCertificado,iConsecutivoCertifExp,dtExpedicionCert,dtIniVigencia,dtFinVigencia,lVigente,lExpedicion,iCveGrupoCertif,iCveUsuario,iCveOficina,cObses) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iConsecutivoCertExp) AS iConsecutivoCertExp from INSCertifExpedidos Where iCveVehiculo = "+vData.getInt("iCveVehiculo"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iConsecutivoCertExp",vUltimo.getInt("iConsecutivoCertExp") + 1);
      }else
         vData.put("iConsecutivoCertExp",1);
      vData.addPK(vData.getString("iConsecutivoCertExp"));
      //...
      TVDinRep vPuerto = new TVDinRep();

      
      iAnioActual = fecha.getIntYear(vData.getDate("dtIniVigencia"));
      String cMaxXpto =   "SELECT MAX(ICONSECUTIVOCERTIFEXP) AS iConsecutivoCertifExp " +
      " FROM INSCERTIFEXPEDIDOS c " +
      " JOIN INSPROGINSP pi on c.IEJERCICIO=pi.IEJERCICIO and c.INUMSOLICITUD=pi.INUMSOLICITUD " +
      " WHERE PI.ICVEINSPECTOR = ( SELECT ICVEINSPECTOR FROM INSPROGINSP WHERE ICVEINSPPROG ="+vData.getInt("iCveInspProg")+") " +
      " AND DTINIVIGENCIA BETWEEN '"+iAnioActual+"-01-01' AND '"+iAnioActual+"-12-31' ";

      //Vector vcData1 = findByCustom("","select MAX(iConsecutivoCertifExp) AS iConsecutivoCertifExp from INSCertifExpedidos Where iEjercicio = "+vData.getInt("iEjercicio") + " and icveOficina = " + vData.getInt("iCveOficina"));
      Vector vcData1 = findByCustom("",cMaxXpto);
      if(vcData1.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData1.get(0);
         vData.put("iConsecutivoCertifExp",vUltimo.getInt("iConsecutivoCertifExp") + 1);
      }else
         vData.put("iConsecutivoCertifExp",1);
      vData.addPK(vData.getString("iConsecutivoCertifExp"));
      //...
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoCertExp"));
      lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(4,vData.getInt("iEjercicio"));
      lPStmt.setInt(5,vData.getInt("iTipoCertificado"));
      lPStmt.setInt(6,vData.getInt("iConsecutivoCertifExp"));
      lPStmt.setDate(7,vData.getDate("dtExpedicionCert"));
      lPStmt.setDate(8,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(9,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(10,vData.getInt("lVigente"));
      lPStmt.setInt(11,vData.getInt("lExpedicion"));
      lPStmt.setInt(12,vData.getInt("iCveGrupoCertif"));
      lPStmt.setInt(13,vData.getInt("iCveUsuario"));
      lPStmt.setInt(14,vData.getInt("iCveOficina"));
      lPStmt.setString(15,vData.getString("cObses"));
      lPStmt.executeUpdate();
      lPStmt.close();
      if(cnNested == null){
        conn.commit();
      }
      TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
      TVDinRep vDataSol =new TVDinRep();
      vDataSol.put("iEjercicio",vData.getInt("iEjercicio"));
      vDataSol.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
      vDataSol.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
      vDataSol.put("cNomEmbarcacion",vData.getString("cNomEmbarcacion"));
      dSolicitud.cambiaBien(vDataSol,conn);
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
   * <p><b> delete from INSCertifExpedidos where iCveVehiculo = ? AND iConsecutivoCertExp = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iConsecutivoCertExp, </b></p>
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
      String lSQL = "delete from INSCertifExpedidos where iCveVehiculo = ? AND iConsecutivoCertExp = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoCertExp"));

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
   * <p><b> update INSCertifExpedidos set iNumSolicitud=?, iEjercicio=?, iTipoCertificado=?, iConsecutivoCertifExp=?, dtExpedicionCert=?, dtIniVigencia=?, dtFinVigencia=?, lVigente=?, lExpedicion=?, iCveGrupoCertif=? where iCveVehiculo = ? AND iConsecutivoCertExp = ?  </b></p>
   * <p><b> Campos Llave: iCveVehiculo,iConsecutivoCertExp, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null, lPStmt2 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      
      String lSQLPI = "update INSProgInsp set iCvePuerto=? where iCveInspProg = ? ";
      vData.addPK(vData.getString("iCveInspProg"));
      lPStmt2 = conn.prepareStatement(lSQLPI);
            
      lPStmt2.setInt(1,vData.getInt("iCvePuerto"));
      lPStmt2.setInt(2,vData.getInt("iCveInspProg"));
      lPStmt2.executeUpdate();
      
      String lSQL = "update INSCertifExpedidos set iNumSolicitud=?, iEjercicio=?, iTipoCertificado=?, iConsecutivoCertifExp=?, dtExpedicionCert=?, dtIniVigencia=?, dtFinVigencia=?, lVigente=?, lExpedicion=?, iCveGrupoCertif=?,cObses=? where iCveVehiculo = ? AND iConsecutivoCertExp = ? ";
      vData.addPK(vData.getString("iCveVehiculo"));
      vData.addPK(vData.getString("iConsecutivoCertExp"));
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iTipoCertificado"));
      lPStmt.setInt(4,vData.getInt("iConsecutivoCertifExp"));
      lPStmt.setDate(5,vData.getDate("dtExpedicionCert"));
      lPStmt.setDate(6,vData.getDate("dtIniVigencia"));
      lPStmt.setDate(7,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(8,vData.getInt("lVigente"));
      lPStmt.setInt(9,vData.getInt("lExpedicion"));
      lPStmt.setInt(10,vData.getInt("iCveGrupoCertif"));
      lPStmt.setString(11,vData.getString("cObses"));
      lPStmt.setInt(12,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(13,vData.getInt("iConsecutivoCertExp"));
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
 public TVDinRep insertCert(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   TFechas fecha = new TFechas();
   int iAnioActual = fecha.getIntYear(fecha.getDateSQL(fecha.getThisTime()));
      
   iAnioActual = fecha.getIntYear(vData.getDate("dtIniVigencia"));
   
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;

   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String lSQL =
         "insert into INSCertifExpedidos(iCveVehiculo,iConsecutivoCertExp,iNumSolicitud,iEjercicio,iTipoCertificado,iConsecutivoCertifExp,dtExpedicionCert,dtIniVigencia,dtFinVigencia,lVigente,lExpedicion,iCveGrupoCertif,iCveUsuario,iCveOficina,cObses ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     //AGREGAR AL ULTIMO ...
    // Vector vcData = findByCustom("","select MAX(iConsecutivoCertExp) AS iConsecutivoCertExp from INSCertifExpedidos Where iCveVehiculo = "+vData.getInt("iCveVehiculo"));
     Vector vcData = this.findByNessted("","select MAX(iConsecutivoCertExp) AS iConsecutivoCertExp from INSCertifExpedidos Where iCveVehiculo = "+vData.getInt("iCveVehiculo"),conn);
     if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivoCertExp",vUltimo.getInt("iConsecutivoCertExp") + 1);
     }else
        vData.put("iConsecutivoCertExp",1);
     vData.addPK(vData.getString("iConsecutivoCertExp"));
     //...
     TVDinRep vPuerto = new TVDinRep();

     String cMaxXpto = "SELECT MAX(ICONSECUTIVOCERTIFEXP) AS iConsecutivoCertifExp " +
                       " FROM INSCERTIFEXPEDIDOS c " +
                       " JOIN INSPROGINSP pi on c.IEJERCICIO=pi.IEJERCICIO and c.INUMSOLICITUD=pi.INUMSOLICITUD " +
                       " WHERE PI.ICVEINSPECTOR = ( SELECT ICVEINSPECTOR FROM INSPROGINSP WHERE ICVEINSPPROG ="+vData.getInt("iCveInspProg")+") " +
                       " AND DTINIVIGENCIA BETWEEN '"+iAnioActual+"-01-01' AND '"+iAnioActual+"-12-31' ";

     TVDinRep vInspProg = (TVDinRep) this.findByNessted("","SELECT ICVEOFICINA,iEjercicio,iNumSolicitud FROM INSPROGINSP where ICVEINSPPROG="+vData.getInt("iCveInspProg"),conn).get(0);
     int iOficina = vInspProg.getInt("ICVEOFICINA");
     int iEjercicio = vInspProg.getInt("iEjercicio");
     int iNumSolicitud = vInspProg.getInt("iNumSolicitud");

     vData.remove("iConsecutivoCertifExp");
     //Vector vcData1 = findByCustom("","select MAX(iConsecutivoCertifExp) AS iConsecutivoCertifExp from INSCertifExpedidos Where iEjercicio = "+vData.getInt("iEjercicio") + " and icveOficina = " + vData.getInt("iCveOficina"));
     Vector vcData1 = findByNessted("",cMaxXpto,conn);
     if(vcData1.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData1.get(0);
        vData.put("iConsecutivoCertifExp",(vUltimo.getInt("iConsecutivoCertifExp") + 1));
     }else
        vData.put("iConsecutivoCertifExp",1);
     //...
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
     lPStmt.setInt(2,vData.getInt("iConsecutivoCertExp"));
     lPStmt.setInt(3,iNumSolicitud);
     lPStmt.setInt(4,iEjercicio);
     lPStmt.setInt(5,vData.getInt("iTipoCertificado"));
     lPStmt.setInt(6,vData.getInt("iConsecutivoCertifExp"));
     lPStmt.setDate(7,fecha.getDateSQL(fecha.getThisTime()));
     lPStmt.setDate(8,vData.getDate("dtIniVigencia"));
     lPStmt.setDate(9,vData.getDate("dtFinVigencia"));
     lPStmt.setInt(10,1);
     lPStmt.setInt(11,1);
     lPStmt.setInt(12,vData.getInt("iCveGrupoCertif"));
     lPStmt.setInt(13,vData.getInt("iCveUsuario"));
     lPStmt.setInt(14,iOficina);
     lPStmt.setString(15,vData.getString("cObses"));
     if(vData.getInt("iCveVehiculo")>0){
       lPStmt.executeUpdate();
     }
     lPStmt.close();
     if(cnNested == null){
       conn.commit();
     }
     String cSQL = "UPDATE INSCERTXINSPECCION SET ICVEVEHICULO=?, ICONSECUTIVOCERTEXP=? WHERE ICVEINSPPROG=? AND INUMCERTIFICADO=? ";
     lPStmt1 = conn.prepareStatement(cSQL);
     lPStmt1.setInt(1,vData.getInt("iCveVehiculo"));
     lPStmt1.setInt(2,vData.getInt("iConsecutivoCertExp"));
     lPStmt1.setInt(3,vData.getInt("iCveInspProg"));
     lPStmt1.setInt(4,vData.getInt("iNumCertificado"));
     lPStmt1.executeUpdate();
     lPStmt1.close();
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

 public TVDinRep insertCI(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   TFechas fecha = new TFechas();
   int iAnioActual = fecha.getIntYear(fecha.getDateSQL(fecha.getThisTime()));
   //PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     //Primero se crea la inspeccion virtual.
     TDINSProgInsp dProgInsp = new TDINSProgInsp();
     TDINSInspeccion dInsp = new TDINSInspeccion();
     TDINSCertxInspeccion dCertIns = new TDINSCertxInspeccion();
     TVDinRep vProgInsp = new TVDinRep();
     vProgInsp.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
     vProgInsp.put("iCveInspector",vData.getInt("iCveUsuario"));
     vProgInsp.put("iCveTipoInsp",1);
     vProgInsp.put("iTipoCertificado",vData.getInt("iTipoCertificado"));
     vProgInsp.put("iCveEmbarcacion",vData.getInt("iCveVehiculo"));
     vProgInsp.put("iCvePuerto",vData.getInt("iCvePuerto"));
     vProgInsp.put("iCveOficina",vData.getInt("iCveOficina"));
     vProgInsp.put("iEjercicio",vData.getInt("iEjercicio"));
     vProgInsp.put("iCveGrupoCert",vData.getInt("iCveGrupoCertif"));
     TVDinRep vPog = dProgInsp.insertNuevo(vProgInsp,conn);
     TVDinRep vInsp = new TVDinRep();

     vInsp.put("iCveInspProg",vPog.getInt("iCveInspProg"));
     vInsp.put("dtIniInsp",fecha.getDateSQL(fecha.getThisTime()));
     vInsp.put("dtFinInsp",fecha.getDateSQL(fecha.getThisTime()));
     vInsp.put("iCveOficina",vData.getInt("iCveOficina"));
     vInsp.put("iCvePuerto",vData.getInt("iCvePuerto"));
     vInsp.put("cObses","");
     vInsp.put("lRegistroFinalizado",1);
     vInsp = dInsp.insert(vProgInsp,conn);

     TVDinRep vCertInsp = new TVDinRep();
     vCertInsp.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
     vCertInsp.put("iCveInspProg",vPog.getInt("iCveInspProg"));
     vCertInsp.put("iEjercicio",vData.getInt("iEjercicio"));
     vCertInsp.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
     vCertInsp.put("iCveGrupoCertif",vData.getInt("iCveGrupoCertif"));
     vCertInsp.put("iTipoCertificado",vData.getInt("iTipoCertificado"));
     vCertInsp.put("lAprobada",0);
     vCertInsp.put("dtIniVigencia",vData.getDate("dtIniVigencia"));
     vCertInsp.put("dtFinVigencia",vData.getDate("dtFinVigencia"));
     vCertInsp.put("iCveUsuario",vData.getInt("iCveUsuario"));
     vCertInsp.put("iNumCertificado",vData.getInt("iNumCertificado"));
     dCertIns.insert(vCertInsp,conn);
     //Termina la Inspeccion Virtual.
/*
     String lSQL =
         "insert into INSCertifExpedidos(iCveVehiculo,iConsecutivoCertExp,iNumSolicitud,iEjercicio,iTipoCertificado,iConsecutivoCertifExp,dtExpedicionCert,dtIniVigencia,dtFinVigencia,lVigente,lExpedicion,iCveGrupoCertif,iCveUsuario,iCveOficina,cObses) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     //AGREGAR AL ULTIMO ...
     Vector vcData = findByNessted("","select MAX(iConsecutivoCertExp) AS iConsecutivoCertExp from INSCertifExpedidos Where iCveVehiculo = "+vData.getInt("iCveVehiculo"),conn);
     if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivoCertExp",vUltimo.getInt("iConsecutivoCertExp") + 1);
     }else
        vData.put("iConsecutivoCertExp",1);
     vData.addPK(vData.getString("iConsecutivoCertExp"));

     String cMaxUsr =  "SELECT max(ICONSECUTIVOCERTIFEXP) as ICONSECUTIVOCERTIFEXP " +
                        "FROM INSCERTIFEXPEDIDOS " +
                        "where icveusuario = " +vData.getInt("iCveUsuario")+
                        " AND DTEXPEDICIONCERT BETWEEN '"+iAnioActual+"-01-01' AND '"+iAnioActual+"-12-31'";

     //Vector vcData1 = findByCustom("","select MAX(iConsecutivoCertifExp) AS iConsecutivoCertifExp from INSCertifExpedidos Where iEjercicio = "+vData.getInt("iEjercicio") + " and icveOficina = " + vData.getInt("iCveOficina"));
     Vector vcData1 = findByCustom("",cMaxUsr);
     if(vcData1.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData1.get(0);
        vData.put("iConsecutivoCertifExp",vUltimo.getInt("iConsecutivoCertifExp") + 1);
     }else
        vData.put("iConsecutivoCertifExp",1);
     vData.addPK(vData.getString("iConsecutivoCertifExp"));*/
     //...
     /*lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
     lPStmt.setInt(2,vData.getInt("iConsecutivoCertExp"));
     lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
     lPStmt.setInt(4,vData.getInt("iEjercicio"));
     lPStmt.setInt(5,vData.getInt("iTipoCertificado"));
     lPStmt.setInt(6,vData.getInt("iConsecutivoCertifExp"));
     lPStmt.setDate(7,vData.getDate("dtExpedicionCert"));
     lPStmt.setDate(8,vData.getDate("dtIniVigencia"));
     lPStmt.setDate(9,vData.getDate("dtFinVigencia"));
     lPStmt.setInt(10,vData.getInt("lVigente"));
     lPStmt.setInt(11,vData.getInt("lExpedicion"));
     lPStmt.setInt(12,vData.getInt("iCveGrupoCertif"));
     lPStmt.setInt(13,vData.getInt("iCveUsuario"));
     lPStmt.setInt(14,vData.getInt("iCveOficina"));
     lPStmt.setString(15,vData.getString("cObses"));
     if(vData.getInt("iCveVehiculo")>0)
    	 lPStmt.executeUpdate();

     lPStmt.close();*/
     if(cnNested == null){
       conn.commit();
     }

     TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
     TVDinRep vDataSol =new TVDinRep();
     vDataSol.put("iEjercicio",vData.getInt("iEjercicio"));
     vDataSol.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
     vDataSol.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
     vDataSol.put("cNomEmbarcacion",vData.getString("cNomEmbarcacion"));
     dSolicitud.cambiaBien(vDataSol,conn);
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
       /*if(lPStmt != null){
         lPStmt.close();
       }*/
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
 public TVDinRep insertCara(TVDinRep vData,Connection cnNested) throws
    DAOException{
  DbConnection dbConn = null;
  Connection conn = cnNested;
  TDINSCaractXInsp Caract = new TDINSCaractXInsp();
  TFechas fecha = new TFechas();
  int iAnioActual = fecha.getIntYear(fecha.getDateSQL(fecha.getThisTime()));
  PreparedStatement lPStmt = null;
  PreparedStatement lPStmt1 = null;
  boolean lSuccess = true;
  try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }

    String [] aCampos = vData.getString("cCampos").split(","); 
    String [] aValores = vData.getString("cValores").split(",");
    String [] aConceptos = vData.getString("cConceptos").split(",");
    String [] aCaracteristicas = vData.getString("cCaracteristicas").split(",");
    
    for(int iCamp = 0;iCamp < aCampos.length;iCamp++){
      TVDinRep vCaract = new TVDinRep();
      Vector vcExistente = this.findByNessted("",
          "SELECT TSMODIFICACION FROM INSCARACTXINSP Where ICVEINSPECCION = " +
                                              vData.getString("iCveInspeccion")
                                              + " and ICVECONCEPTOEVAL=" +
                                              aConceptos[iCamp]
                                              + " and ICVECARACTERISITIC=" +
                                              aCaracteristicas[iCamp],conn);

      vCaract.put("iCveInspeccion",vData.getString("iCveInspeccion"));
      vCaract.put("iCveConceptoEval",aConceptos[iCamp]);
      vCaract.put("iCveCaracteristic",aCaracteristicas[iCamp]);
      vCaract.put("iCveUsuario",vData.getString("iCveUsuario"));
      vCaract.put("cVariable",aCampos[iCamp]);
      
      if(iCamp<aValores.length)
    	  vCaract.put("cValor",aValores[iCamp]);
      else
    	  vCaract.put("cValor","");
      
      vCaract.put("lAprobado",1);

      if(vcExistente.size() > 0) Caract.update(vCaract,conn);
      else Caract.insert(vCaract,conn);
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

}

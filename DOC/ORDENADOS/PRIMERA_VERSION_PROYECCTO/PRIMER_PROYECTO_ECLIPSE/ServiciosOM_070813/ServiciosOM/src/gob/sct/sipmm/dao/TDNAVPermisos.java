package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDNAVPermisos.java</p>
 * <p>Description: DAO de la entidad NAVPermisos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author GPerez
 * @version 1.0
 */
public class TDNAVPermisos extends DAOBase{
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
    Vector vcRecords = new Vector(), vcOrden=new Vector();
    cError = "";
    String cOrdinaria="";
    TParametro  vParametros = new TParametro("44");
    String [] aFechaRevPermisos = vParametros.getPropEspecifica("cNavFechaRevPermisos").split(",");

    try{
      String cSQL = cWhere;
      vcRecords = this.findByCustom1(cKey,cSQL);
      if(vcRecords.size()>0){
        for(int i=0;i<vcRecords.size();i++){
          TVDinRep vRecords = (TVDinRep) vcRecords.get(i);
          java.sql.Date dtNorma = new java.sql.Date((Integer.parseInt(aFechaRevPermisos[0])-1900),Integer.parseInt(aFechaRevPermisos[1]),Integer.parseInt(aFechaRevPermisos[2]));
          if(!vRecords.getString("dtIniServicio").equals("null")){
            if(vRecords.getDate("dtIniServicio").compareTo(dtNorma) > 0){
              String cOrdenPermiso =
                  " SELECT iEjercicioPermiso, iConsecutivoPermiso,dtIniServicio " +
                  " FROM NAVPERMISOS " +
                  " WHERE iCveVehiculo = " + vRecords.getInt("iCveVehiculo") +
                  " AND dtIniServicio>'"+aFechaRevPermisos[0]+"-"+aFechaRevPermisos[1]+"-"+aFechaRevPermisos[2]+"' "+
                  " ORDER BY dtIniServicio ";
              vcOrden = this.findByCustom1("",cOrdenPermiso);
              for(int j = 0;j < vcOrden.size();j++){
                TVDinRep vOrden = (TVDinRep) vcOrden.get(j);
                if(vRecords.getInt("iEjercicioPermiso") ==
                   vOrden.getInt("iEjercicioPermiso") &&
                   vRecords.getInt("iConsecutivoPermiso") ==
                   vOrden.getInt("iConsecutivoPermiso")){
                  cOrdinaria = this.fNumOrdinaria(j,'a');
                  if(cOrdinaria != "") cOrdinaria += " Revalidación ";
                }
              }
              vRecords.put("cOrdinaria",cOrdinaria);
            } else vRecords.put("cOrdinaria","");
          }else vRecords.put("cOrdinaria","");
        }
      }
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }

 public Vector findByCustom1(String cKey,String cWhere) throws DAOException{
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
   * <p><b> insert into NAVPermisos(iEjercicioPermiso,iConsecutivoPermiso,iEjercicioSolicitud,iNumSolicitud,iCveVehiculo,cPrestaServAEmp,dtIniServicio,dtFinServicio,dtIniContFlet,dtFinContFlet,dtIniContServ,dtFinContServ,dtIniSegProt,dtFinSegProt,iNumViajes,cMotivoContrato,dtAutorizacion,dtFinVigencia,iNumPasajeros,iNumTripulantesMex,cObs,lServicioaPemex,cNumContrato,iHolograma,iGradosLatitud,iMinutosLatitud,dSegundosLatitud,iGradosLongitud,iMinutosLongitud,dSegundosLongitud,dtExpedicion) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioPermiso,iConsecutivoPermiso, </b></p>
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
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into NAVPermisos(iEjercicioPermiso,iConsecutivoPermiso,iEjercicioSolicitud,iNumSolicitud,iCveVehiculo,cPrestaServAEmp,dtIniServicio,dtFinServicio,dtIniContFlet,dtFinContFlet,dtIniContServ,dtFinContServ,dtIniSegProt,dtFinSegProt,iNumViajes,cMotivoContrato,dtAutorizacion,dtFinVigencia,iNumPasajeros,iNumTripulantesMex,cObs,lServicioaPemex,cNumContrato,iHolograma,iGradosLatitud,iMinutosLatitud,dSegundosLatitud,iGradosLongitud,iMinutosLongitud,dSegundosLongitud,dtExpedicion,cNumTestNotarial,cPoderNotarial,cNotariaPublica,iCveEntidadNotaria,cSiglas,cSolicitudPrimerPermiso,cFolioPrimerPermiso) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom1("","select MAX(iConsecutivoPermiso) AS iConsecutivoPermiso from NAVPermisos WHERE iEjercicioPermiso = "+vData.getInt("iEjercicioPermiso"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iConsecutivoPermiso",vUltimo.getInt("iConsecutivoPermiso") + 1);
      }else
         vData.put("iConsecutivoPermiso",1);
      vData.addPK(vData.getString("iConsecutivoPermiso"));
      //...
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioPermiso"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPermiso"));
      lPStmt.setInt(3,vData.getInt("iEjercicioSolicitud"));
      lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(5,vData.getInt("iCveVehiculo"));
      lPStmt.setString(6,vData.getString("cPrestaServAEmp"));
      lPStmt.setDate(7,vData.getDate("dtIniServicio"));
      lPStmt.setDate(8,vData.getDate("dtFinServicio"));
      lPStmt.setDate(9,vData.getDate("dtIniContFlet"));
      lPStmt.setDate(10,vData.getDate("dtFinContFlet"));
      lPStmt.setDate(11,vData.getDate("dtIniContServ"));
      lPStmt.setDate(12,vData.getDate("dtFinContServ"));
      lPStmt.setDate(13,vData.getDate("dtIniSegProt"));
      lPStmt.setDate(14,vData.getDate("dtFinSegProt"));
      lPStmt.setInt(15,vData.getInt("iNumViajes"));
      lPStmt.setString(16,vData.getString("cMotivoContrato"));
      lPStmt.setDate(17,vData.getDate("dtAutorizacion"));
      lPStmt.setDate(18,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(19,vData.getInt("iNumPasajeros"));
      lPStmt.setInt(20,vData.getInt("iNumTripulantesMex"));
      lPStmt.setString(21,vData.getString("cObs"));
      lPStmt.setInt(22,vData.getInt("lServicioaPemex"));
      lPStmt.setString(23,vData.getString("cNumContrato"));
      lPStmt.setInt(24,vData.getInt("iHolograma"));
      lPStmt.setInt(25,vData.getInt("iGradosLatitud"));
      lPStmt.setInt(26,vData.getInt("iMinutosLatitud"));
      lPStmt.setFloat(27,vData.getFloat("dSegundosLatitud"));
      lPStmt.setInt(28,vData.getInt("iGradosLongitud"));
      lPStmt.setInt(29,vData.getInt("iMinutosLongitud"));
      lPStmt.setFloat(30,vData.getFloat("dSegundosLongitud"));
      lPStmt.setDate(31,vData.getDate("dtExpedicion"));
      lPStmt.setString(32,vData.getString("cNumTestNotarial"));
      //,cPoderNotarial,cNotariaPublica,iCveEntidadNotaria,cSiglas
      lPStmt.setString(33,vData.getString("cPoderNotarial"));
      lPStmt.setString(34,vData.getString("cNotariaPublica"));
      lPStmt.setInt(35,vData.getInt("iCveEntidadNotaria"));
      lPStmt.setString(36,vData.getString("cSiglas"));
      lPStmt.setString(38,vData.getString("cSolicitudPrimerPermiso"));
      lPStmt.setString(37,vData.getString("cFolioPrimerPermiso"));
      lPStmt.executeUpdate();
      conn.commit();

      String cSql = "update NAVPermisos Set cSolicitudPrimerPermiso=?, cFolioPrimerPermiso=? "+
                    "Where iCveVehiculo=? and ("+
                    "(cSolicitudPrimerPermiso='' And cFolioPrimerPermiso='')"+
                    " OR (cSolicitudPrimerPermiso is null AND cFolioPrimerPermiso is null ) )";
      lPStmt1 = conn.prepareStatement(cSql);
      lPStmt1.setString(1,vData.getString("cSolicitudPrimerPermiso"));
      lPStmt1.setString(2,vData.getString("cFolioPrimerPermiso"));
      lPStmt1.setInt(3,vData.getInt("iCveVehiculo"));
      lPStmt1.executeUpdate();
      conn.commit();

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
   * <p><b> delete from NAVPermisos where iEjercicioPermiso = ? AND iConsecutivoPermiso = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioPermiso,iConsecutivoPermiso, </b></p>
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
      String lSQL = "delete from NAVPermisos where iEjercicioPermiso = ? AND iConsecutivoPermiso = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioPermiso"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPermiso"));

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
   * <p><b> update NAVPermisos set iEjercicioSolicitud=?, iNumSolicitud=?, iCveVehiculo=?, cPrestaServAEmp=?, dtIniServicio=?, dtFinServicio=?, dtIniContFlet=?, dtFinContFlet=?, dtIniContServ=?, dtFinContServ=?, dtIniSegProt=?, dtFinSegProt=?, iNumViajes=?, cMotivoContrato=?, dtAutorizacion=?, dtFinVigencia=?, iNumPasajeros=?, iNumTripulantesMex=?, cObs=?, lServicioaPemex=?, cNumContrato=?, iHolograma=?, iGradosLatitud=?, iMinutosLatitud=?, dSegundosLatitud=?, iGradosLongitud=?, iMinutosLongitud=?, dSegundosLongitud=? where iEjercicioPermiso = ? AND iConsecutivoPermiso = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioPermiso,iConsecutivoPermiso, </b></p>
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
    PreparedStatement lPStmt1 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update NAVPermisos set iCveVehiculo=?, cPrestaServAEmp=?, dtIniServicio=?, dtFinServicio=?, dtIniContFlet=?, "+
                    "dtFinContFlet=?, dtIniContServ=?, dtFinContServ=?, dtIniSegProt=?, dtFinSegProt=?, "+
                    "iNumViajes=?, cMotivoContrato=?, dtAutorizacion=?, dtFinVigencia=?, iNumPasajeros=?, "+
                    "iNumTripulantesMex=?, cObs=?, lServicioaPemex=?, cNumContrato=?, iHolograma=?, "+
                    "iGradosLatitud=?, iMinutosLatitud=?, dSegundosLatitud=?, iGradosLongitud=?, iMinutosLongitud=?, "+
                    "dSegundosLongitud=?, dtExpedicion=?, cNumTestNotarial=?,cPoderNotarial=?,cNotariaPublica=?,"+
                    "iCveEntidadNotaria=?,cSiglas=? where iEjercicioPermiso = ? AND iConsecutivoPermiso = ? ";

      vData.addPK(vData.getString("iEjercicioPermiso"));
      vData.addPK(vData.getString("iConsecutivoPermiso"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
      lPStmt.setString(2,vData.getString("cPrestaServAEmp"));
      lPStmt.setDate(3,vData.getDate("dtIniServicio"));
      lPStmt.setDate(4,vData.getDate("dtFinServicio"));
      lPStmt.setDate(5,vData.getDate("dtIniContFlet"));
      lPStmt.setDate(6,vData.getDate("dtFinContFlet"));
      lPStmt.setDate(7,vData.getDate("dtIniContServ"));
      lPStmt.setDate(8,vData.getDate("dtFinContServ"));
      lPStmt.setDate(9,vData.getDate("dtIniSegProt"));
      lPStmt.setDate(10,vData.getDate("dtFinSegProt"));
      lPStmt.setInt(11,vData.getInt("iNumViajes"));
      lPStmt.setString(12,vData.getString("cMotivoContrato"));
      lPStmt.setDate(13,vData.getDate("dtAutorizacion"));
      lPStmt.setDate(14,vData.getDate("dtFinVigencia"));
      lPStmt.setInt(15,vData.getInt("iNumPasajeros"));
      lPStmt.setInt(16,vData.getInt("iNumTripulantesMex"));
      lPStmt.setString(17,vData.getString("cObs"));
      lPStmt.setInt(18,vData.getInt("lServicioaPemex"));
      lPStmt.setString(19,vData.getString("cNumContrato"));
      lPStmt.setInt(20,vData.getInt("iHolograma"));
      lPStmt.setInt(21,vData.getInt("iGradosLatitud"));
      lPStmt.setInt(22,vData.getInt("iMinutosLatitud"));
      lPStmt.setFloat(23,vData.getFloat("dSegundosLatitud"));
      lPStmt.setInt(24,vData.getInt("iGradosLongitud"));
      lPStmt.setInt(25,vData.getInt("iMinutosLongitud"));
      lPStmt.setFloat(26,vData.getFloat("dSegundosLongitud"));
      lPStmt.setDate(27,vData.getDate("dtExpedicion"));
      lPStmt.setString(28,vData.getString("cNumTestNotarial"));
      lPStmt.setString(29,vData.getString("cPoderNotarial"));
      lPStmt.setString(30,vData.getString("cNotariaPublica"));
      lPStmt.setInt(31,vData.getInt("iCveEntidadNotaria"));
      lPStmt.setString(32,vData.getString("cSiglas"));
      lPStmt.setInt(33,vData.getInt("iEjercicioPermiso"));
      lPStmt.setInt(34,vData.getInt("iConsecutivoPermiso"));
      lPStmt.executeUpdate();
      conn.commit();

      String cSql = "update NAVPermisos Set cSolicitudPrimerPermiso=?, cFolioPrimerPermiso=? "+
                    "Where iCveVehiculo=? and ("+
                    "(cSolicitudPrimerPermiso='' And cFolioPrimerPermiso='')"+
                    " OR (cSolicitudPrimerPermiso is null AND cFolioPrimerPermiso is null ) )";


      lPStmt1 = conn.prepareStatement(cSql);
      lPStmt1.setString(1,vData.getString("cSolicitudPrimerPermiso"));
      lPStmt1.setString(2,vData.getString("cFolioPrimerPermiso"));
      lPStmt1.setInt(3,vData.getInt("iCveVehiculo"));
      lPStmt1.executeUpdate();
      conn.commit();

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
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");

      return vData;
    }
 }

 public TVDinRep insertP(TVDinRep vData,Connection cnNested) throws
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
         "insert into NAVPermisos(iEjercicioPermiso,iConsecutivoPermiso,iEjercicioSolicitud,iNumSolicitud,iCveVehiculo) values (?,?,?,?,?)";

     //AGREGAR AL ULTIMO ...
     Vector vcData = findByCustom1("","select MAX(iConsecutivoPermiso) AS iConsecutivoPermiso from NAVPermisos WHERE iEjercicioPermiso = "+vData.getInt("iEjercicioPermiso"));
     if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivoPermiso",vUltimo.getInt("iConsecutivoPermiso") + 1);
     }else
        vData.put("iConsecutivoPermiso",1);
     vData.addPK(vData.getString("iConsecutivoPermiso"));
     //...

     lPStmt = conn.prepareStatement(lSQL);

     lPStmt.setInt(1,vData.getInt("iEjercicioPermiso"));
     lPStmt.setInt(2,vData.getInt("iConsecutivoPermiso"));
     lPStmt.setInt(3,vData.getInt("iEjercicioSolicitud"));
     lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
     lPStmt.setInt(5,vData.getInt("iCveVehiculo"));
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
    public String fNumOrdinaria(int i, char Terminacion){
      String cOrdinaria=  "";
      if(i==1)cOrdinaria= "Primer";
      if(i==2)cOrdinaria= "Segund";
      if(i==3)cOrdinaria= "Tercer";
      if(i==4)cOrdinaria= "Cuart";
      if(i==5)cOrdinaria= "Quint";
      if(i==6)cOrdinaria= "Sext";
      if(i==7)cOrdinaria= "Septim";
      if(i==8)cOrdinaria= "Octav";
      if(i==9)cOrdinaria= "Noven";
      if(i==10)cOrdinaria="Decim";
      if(i==11)cOrdinaria="Decimo Primer";
      if(i==12)cOrdinaria="Decimo Segund";
      if(i==13)cOrdinaria="Decimo Tercer";
      if(i==14)cOrdinaria="Decimo Cuart";
      if(i==15)cOrdinaria="Decimo Quint";
      if(i==16)cOrdinaria="Decimo Sext";
      if(i==17)cOrdinaria="Decimo Septim";
      if(i==18)cOrdinaria="Decimo Octav";
      if(i==19)cOrdinaria="Decimo Noven";
      if(i==20)cOrdinaria="Duodecim";
      if(cOrdinaria!="")cOrdinaria+=Terminacion;
      return cOrdinaria;
    }
    public boolean fGuardaFolios(String cSolicitd,String cFolio,int iCveVehiculo){

      String cSql = "update NAVPermisos Set cSolicitudPrimerPermiso=?, cFolioPrimerPermiso=? "+
                    "Where iCveVehiculo=? and ("+
                    "(cSolicitudPrimerPermiso='' And cFolioPrimerPermiso='')"+
                    " OR (cSolicitudPrimerPermiso is null AND cFolioPrimerPermiso is null ) )";


      DbConnection dbConn = null;
      Connection conn = null;
      PreparedStatement lPStmt = null;
      PreparedStatement lPStmt1 = null;
      boolean lSuccess = true;
      try{

        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);


        lPStmt1 = conn.prepareStatement(cSql);
        lPStmt1.setString(1,cSolicitd);
        lPStmt1.setString(2,cFolio);
        lPStmt1.setInt(3,iCveVehiculo);
        lPStmt1.executeUpdate();
        conn.commit();
      } catch(Exception ex){
        warn("update",ex);
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback",e);
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
        if(conn != null){
          conn.close();
        }
        dbConn.closeConnection();

      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      return true;
   }
    }


}

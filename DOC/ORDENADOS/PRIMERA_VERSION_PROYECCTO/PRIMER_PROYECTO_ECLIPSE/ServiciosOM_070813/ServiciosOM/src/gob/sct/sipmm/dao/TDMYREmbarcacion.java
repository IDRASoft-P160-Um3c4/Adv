package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDMYREmbarcacion.java</p>
 * <p>Description: DAO de la entidad MYREmbarcacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
public class TDMYREmbarcacion extends DAOBase{
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
   * <p><b> insert into MYREmbarcacion(iCveOficina,iFolioRPMN,iPartida,cNomEmbarcacion,iCveVehiculo,iCvePersona,iCveTipoEmbarcacion,dEslora,iUniMedEslora,dManga,iUniMedManga,dPuntal,iUniMedPuntal,dArqueoBruto,dArqueoNeto) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
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
    TDMYRRPMN DMYRRPMN = new TDMYRRPMN();
    TVDinRep VMYRRPMN = new TVDinRep();
    TVDinRep vRegresa;
    int iFolioRPMN = 0;
    int iPartida = 0;

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Obtencion del Folio del RPMN correspondiente.
      Vector vcMYRRPMN = DMYRRPMN.findByCustom("iCveOficina,iFolioRPMN,iPartida",
                         " select distinct(iFolioRPMN) as iFolioRPMN " +
                         " from MYRRPMN " +
                         " where iCvePersona = " + vData.getInt("iCvePersona"));

      if (vcMYRRPMN.size() > 0){
        VMYRRPMN = (TVDinRep) vcMYRRPMN.get(0);
        iFolioRPMN = VMYRRPMN.getInt("iFolioRPMN");
      } else {
        vcMYRRPMN = DMYRRPMN.findByCustom("iCveOficina,iFolioRPMN,iPartida",
                    " select max(iFolioRPMN) as iFolioRPMN " +
                    " from MYRRPMN " +
                    " where iCveOficina = " + vData.getInt("iCveOficina"));
        if (vcMYRRPMN.size() > 0){
          VMYRRPMN = (TVDinRep) vcMYRRPMN.get(0);
          iFolioRPMN = VMYRRPMN.getInt("iFolioRPMN");
          iFolioRPMN = iFolioRPMN + 1;
        } else {
          iFolioRPMN = 1;
        }
      }

      //Obtencion de la Partida que le toca.
      if (iFolioRPMN == 1){
        vcMYRRPMN = DMYRRPMN.findByCustom("iCveOficina,iFolioRPMN,iPartida",
                   " select max(iPartida) as iPartida " +
                   " from MYRRPMN " +
                   " where iCveOficina = " + vData.getInt("iCveOficina") +
                   " and iFolioRPMN    = " + iFolioRPMN);
        if (vcMYRRPMN.size() > 0){
          VMYRRPMN = (TVDinRep) vcMYRRPMN.get(0);
          iPartida = VMYRRPMN.getInt("iPartida");
          iPartida = iPartida + 1;
        } else
          iPartida = 1;
      } else
        iPartida = 1;

        //Insert en el RPMN.
        VMYRRPMN.put("iCveOficina",      vData.getInt("iCveOficina"));
        VMYRRPMN.put("iFolioRPMN",       iFolioRPMN);
        VMYRRPMN.put("iPartida",         iPartida);
        VMYRRPMN.put("iEjercicio",       vData.getInt("iEjercicio"));
        VMYRRPMN.put("iNumSolicitud",    vData.getInt("iNumSolicitud"));
        VMYRRPMN.put("dtRegistro",       vData.getDate("dtRegistro"));
        VMYRRPMN.put("iRamo",            vData.getInt("iRamo"));
        VMYRRPMN.put("cDscDocumento",    "");
        VMYRRPMN.put("iCvePersona",      vData.getInt("iCvePersona"));
        VMYRRPMN.put("cInscripcion",     "");
        VMYRRPMN.put("cSintesis",        vData.getString("cSintesis"));
        VMYRRPMN.put("iCveSeccion",      vData.getInt("iCveSeccion"));
        VMYRRPMN.put("iCveTipoAsiento",  vData.getInt("iCveTipoAsiento"));
        VMYRRPMN.put("cTitular",         vData.getString("cNombre"));
        VMYRRPMN.put("iCalificacion",    0);
        VMYRRPMN.put("cDscCalificacion", vData.getString("cDscCalificacion"));
        VMYRRPMN.put("iPartidaCancela",  0);
        VMYRRPMN = DMYRRPMN.insert(VMYRRPMN,conn);



      String lSQL = " insert into MYREmbarcacion(iCveOficina, " +
                    " iFolioRPMN, " +
                    " iPartida, " +
                    " cNomEmbarcacion, " +
                    " iCveVehiculo, " +
                    " iCvePersona, " +
                    " iCveTipoEmbarcacion, " +
                    " dEslora, " +
                    " iUniMedEslora, " +
                    " dManga, " +
                    " iUniMedManga, " +
                    " dPuntal, " +
                    " iUniMedPuntal, " +
                    " dArqueoBruto, " +
                    " dArqueoNeto) " +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(String.valueOf(iFolioRPMN));
      vData.addPK(String.valueOf(iPartida));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,iFolioRPMN);
      lPStmt.setInt(3,iPartida);
      lPStmt.setString(4,vData.getString("cNomEmbarcacion"));
      lPStmt.setInt(5,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(6,vData.getInt("iCvePersona"));
      lPStmt.setInt(7,vData.getInt("iCveTipoEmbarcacion"));
      lPStmt.setFloat(8,vData.getFloat("dEslora"));
      lPStmt.setInt(9,vData.getInt("iUniMedEslora"));
      lPStmt.setFloat(10,vData.getFloat("dManga"));
      lPStmt.setInt(11,vData.getInt("iUniMedManga"));
      lPStmt.setFloat(12,vData.getFloat("dPuntal"));
      lPStmt.setInt(13,vData.getInt("iUniMedPuntal"));
      lPStmt.setFloat(14,vData.getFloat("dArqueoBruto"));
      lPStmt.setFloat(15,vData.getFloat("dArqueoNeto"));
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
   * <p><b> delete from MYREmbarcacion where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
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
      String lSQL = "delete from MYREmbarcacion where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iFolioRPMN"));
      lPStmt.setInt(3,vData.getInt("iPartida"));

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
   * <p><b> update MYREmbarcacion set cNomEmbarcacion=?, iCveVehiculo=?, iCvePersona=?, iCveTipoEmbarcacion=?, dEslora=?, iUniMedEslora=?, dManga=?, iUniMedManga=?, dPuntal=?, iUniMedPuntal=?, dArqueoBruto=?, dArqueoNeto=? where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
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
    TDMYRRPMN DMYRRPMN = new TDMYRRPMN();
    TVDinRep VMYRRPMN = new TVDinRep();

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      VMYRRPMN.put("iCveOficina",      vData.getInt("iCveOficina"));
      VMYRRPMN.put("iFolioRPMN",       vData.getInt("iFolioRPMN"));
      VMYRRPMN.put("iPartida",         vData.getInt("iPartida"));
      VMYRRPMN.put("iEjercicio",       vData.getInt("iEjercicio"));
      VMYRRPMN.put("iNumSolicitud",    vData.getInt("iNumSolicitud"));
      VMYRRPMN.put("dtRegistro",       vData.getDate("dtRegistro"));
      VMYRRPMN.put("iRamo",            vData.getInt("iRamo"));
      VMYRRPMN.put("cDscDocumento",    "");
      VMYRRPMN.put("iCvePersona",      vData.getInt("iCvePersona"));
      VMYRRPMN.put("cInscripcion",     "");
      VMYRRPMN.put("cSintesis",        vData.getString("cSintesis"));
      VMYRRPMN.put("iCveSeccion",      vData.getInt("iCveSeccion"));
      VMYRRPMN.put("iCveTipoAsiento",  vData.getInt("iCveTipoAsiento"));
      VMYRRPMN.put("cTitular",         vData.getString("cNombre"));
      VMYRRPMN.put("iCalificacion",    0);
      VMYRRPMN.put("cDscCalificacion", vData.getString("cDscCalificacion"));
      VMYRRPMN.put("iPartidaCancela",  0);

      VMYRRPMN = DMYRRPMN.update(VMYRRPMN,null);

      String lSQL = " update MYREmbarcacion set cNomEmbarcacion=?, " +
                    " iCveVehiculo=?, " +
                    " iCvePersona=?, " +
                    " iCveTipoEmbarcacion=?, " +
                    " dEslora=?, " +
                    " iUniMedEslora=?, " +
                    " dManga=?, " +
                    " iUniMedManga=?, " +
                    " dPuntal=?, " +
                    " iUniMedPuntal=?, " +
                    " dArqueoBruto=?, " +
                    " dArqueoNeto=? " +
                    " where iCveOficina = ? " +
                    " AND iFolioRPMN = ? " +
                    " AND iPartida = ? " +
                    " AND iEjercicioIns = ? ";

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iFolioRPMN"));
      vData.addPK(vData.getString("iPartida"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cNomEmbarcacion"));
      lPStmt.setInt(2,vData.getInt("iCveVehiculo"));
      lPStmt.setInt(3,vData.getInt("iCvePersona"));
      lPStmt.setInt(4,vData.getInt("iCveTipoEmbarcacion"));
      lPStmt.setFloat(5,vData.getFloat("dEslora"));
      lPStmt.setInt(6,vData.getInt("iUniMedEslora"));
      lPStmt.setFloat(7,vData.getFloat("dManga"));
      lPStmt.setInt(8,vData.getInt("iUniMedManga"));
      lPStmt.setFloat(9,vData.getFloat("dPuntal"));
      lPStmt.setInt(10,vData.getInt("iUniMedPuntal"));
      lPStmt.setFloat(11,vData.getFloat("dArqueoBruto"));
      lPStmt.setFloat(12,vData.getFloat("dArqueoNeto"));
      lPStmt.setInt(13,vData.getInt("iCveOficina"));
      lPStmt.setInt(14,vData.getInt("iFolioRPMN"));
      lPStmt.setInt(15,vData.getInt("iPartida"));
      lPStmt.setInt(15,vData.getInt("iEjercicioIns"));
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


 public TVDinRep integraRPMN(TVDinRep vData,Connection cnNested) throws
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
     String lSQL = " update MYREmbarcacion set "+
                   " iCveVehiculo=? " +
                   " where iEjercicioIns=? and iCveOficina=? and iFolioRPMN=? and iPartida=? ";

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
     lPStmt.setInt(2,vData.getInt("iEjercicioInsOri"));
     lPStmt.setInt(3,vData.getInt("iCveOficinaOri"));
     lPStmt.setInt(4,vData.getInt("iFolioOri"));
     lPStmt.setInt(5,vData.getInt("iPartidaOri"));
     lPStmt.executeUpdate();
     if(cnNested == null){
       conn.commit();
     }
   } catch(Exception ex){
     warn("update",ex);
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
 public TVDinRep updEmbarcacion(TVDinRep vData,Connection cnNested) throws
 DAOException{
DbConnection dbConn = null;
Connection conn = cnNested;
PreparedStatement lPStmt = null;
boolean lSuccess = true;
TDMYRRPMN DMYRRPMN = new TDMYRRPMN();
TVDinRep VMYRRPMN = new TVDinRep();

try{
 if(cnNested == null){
   dbConn = new DbConnection(dataSourceName);
   conn = dbConn.getConnection();
   conn.setAutoCommit(false);
   conn.setTransactionIsolation(2);
 }
 String lSQL = " update MYREmbarcacion " +
 				" set iCveVehiculo=? " +
               " where iCveOficina = ? " +
               " AND iEjercicioINS = ?" +
               " AND iFolioRPMN = ? ";

 vData.addPK(vData.getString("iCveOficina"));
 vData.addPK(vData.getString("iFolioRPMN"));
 vData.addPK(vData.getString("iPartida"));
 vData.addPK(vData.getString("iEjercicioIns"));

 lPStmt = conn.prepareStatement(lSQL);
 lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
 lPStmt.setInt(2,vData.getInt("iCveOficina"));
 lPStmt.setInt(3,vData.getInt("iEjercicioIns"));
 lPStmt.setInt(4,vData.getInt("iFolioRPMN"));
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

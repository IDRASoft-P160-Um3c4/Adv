package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDMYRMatricula.java</p>
 * <p>Description: DAO de la entidad MYRMatricula</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDMYRMatricula extends DAOBase{
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
     * <p><b> insert into MYRMatricula(iEjercicio,iCveMatricula,iCvePais,iCveEntidadFed,iCveOficina,iCveCapitaniaParaMat,cConsecutivoMatricula,iDigVerificador,lVigente,iCveEmbarcacion,iNumSolicitud,iEjercicioSolicitud,lAprobada,iCvePropietario,cNomEmbarcacion,iEstatus,iCveTipoServ,iCveTipoNavega,iCveTipoRespons) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
     * <p><b> Campos Llave: iEjercicio,iCveMatricula, </b></p>
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
            "insert into MYRMatricula(iEjercicio,iCveMatricula,iCvePais,iCveEntidadFed,iCveOficina,iCveCapitaniaParaMat,cConsecutivoMatricula,iDigVerificador,lVigente,iCveEmbarcacion,iNumSolicitud,iEjercicioSolicitud,lAprobada,iCvePropietario,cNomEmbarcacion,iEstatus,iCveTipoServ,iCveTipoNavega,iCveTipoRespons) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveMatricula) AS iCveMatricula from MYRMatricula");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveMatricula",vUltimo.getInt("iCveMatricula") + 1);
      }else
         vData.put("iCveMatricula",1);
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveMatricula"));
      //...

      TFechas fecha = new TFechas();
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, fecha.getIntYear(fecha.TodaySQL()));
        lPStmt.setInt(2,vData.getInt("iCveMatricula"));
        lPStmt.setInt(3,vData.getInt("iCvePais"));
        lPStmt.setInt(4,vData.getInt("iCveEntidadFed"));
        lPStmt.setInt(5,vData.getInt("iCveOficina"));
        lPStmt.setInt(6,vData.getInt("iCveCapitaniaParaMat"));
        lPStmt.setString(7,vData.getString("cConsecutivoMatricula"));
        lPStmt.setInt(8,vData.getInt("iDigVerificador"));
        lPStmt.setInt(9,vData.getInt("lVigente"));
        lPStmt.setInt(10,vData.getInt("iCveEmbarcacion"));
        lPStmt.setInt(11,vData.getInt("iNumSolicitud"));
        lPStmt.setInt(12,vData.getInt("iEjercicioSolicitud"));
        lPStmt.setInt(13,vData.getInt("lAprobada"));
        lPStmt.setInt(14,vData.getInt("iCvePropietario"));
        lPStmt.setString(15,vData.getString("cNomEmbarcacion"));
        lPStmt.setInt(16,vData.getInt("iEstatus"));
        lPStmt.setInt(17,vData.getInt("iCveTipoServ"));
        lPStmt.setInt(18,vData.getInt("iCveTipoNavega"));
        lPStmt.setInt(19,vData.getInt("iCveTipoRespons"));
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
   * <p><b> delete from MYRMatricula where iEjercicio = ? AND iCveMatricula = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveMatricula, </b></p>
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
      String lSQL = "delete from MYRMatricula where iEjercicio = ? AND iCveMatricula = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveMatricula"));

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
   * <p><b> update MYRMatricula set iCvePais=?, iCveEntidadFed=?, iCveOficina=?, iCveCapitaniaParaMat=?, iConsecutivoMatricula=?, iDigVerificador=?, iTipoNavegacion=?, iTipoServicio=?, lVigente=?, iCveEmbarcacion=?, iCveOficinaRPMN=?, iFolioRPMN=?, iPartida=?, iNumSolicitud=?, iEjercicioSolicitud=?, lAprobada=?, lEstatus=? where iEjercicio = ? AND iCveMatricula = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveMatricula, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws DAOException{
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
      String lSQL = "update MYRMatricula set iCvePais=?, iCveEntidadFed=?, iCveOficina=?, iCveCapitaniaParaMat=?, iConsecutivoMatricula=?, iDigVerificador=?, iTipoNavegacion=?, iTipoServicio=?, lVigente=?, iCveEmbarcacion=?, iCveOficinaRPMN=?, iFolioRPMN=?, iPartida=?, iNumSolicitud=?, iEjercicioSolicitud=?, lAprobada=?, lEstatus=? where iEjercicio = ? AND iCveMatricula = ? ";
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveMatricula"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePais"));
      lPStmt.setInt(2,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(3,vData.getInt("iCveOficina"));
      lPStmt.setString(4,vData.getString("iCveCapitaniaParaMat"));
      lPStmt.setInt(5,vData.getInt("iConsecutivoMatricula"));
      lPStmt.setInt(6,vData.getInt("iDigVerificador"));
      lPStmt.setInt(7,vData.getInt("iTipoNavegacion"));
      lPStmt.setInt(8,vData.getInt("iTipoServicio"));
      lPStmt.setInt(9,vData.getInt("lVigente"));
      lPStmt.setInt(10,vData.getInt("iCveEmbarcacion"));
      lPStmt.setInt(11,vData.getInt("iCveOficinaRPMN"));
      lPStmt.setInt(12,vData.getInt("iFolioRPMN"));
      lPStmt.setInt(13,vData.getInt("iPartida"));
      lPStmt.setInt(14,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(15,vData.getInt("iEjercicioSolicitud"));
      lPStmt.setInt(16,vData.getInt("lAprobada"));
      lPStmt.setInt(17,vData.getInt("lEstatus"));
      lPStmt.setInt(18,vData.getInt("iEjercicio"));
      lPStmt.setInt(19,vData.getInt("iCveMatricula"));
      lPStmt.executeUpdate();
      

      TDMYRMatriculaHist MatHist = new TDMYRMatriculaHist();
      TVDinRep vHistorico = new TVDinRep();
      vHistorico.put("iEjercicio",vData.getInt("iEjercicio"));
      vHistorico.put("iCveMatricula",vData.getInt("iCveMatricula"));
      vHistorico.put("iCveEmbarcacion",vData.getInt("iCveEmbarcacion"));
      vHistorico.put("iCveUsuario",vData.getInt("iCveUsuario"));
      MatHist.insert(vHistorico,conn);
      
      
      
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

  public TVDinRep integraMat(TVDinRep vData,Connection cnNested) throws DAOException{
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
    String lSQL = "update MYRMatricula set iCveEmbarcacion=? where iEjercicio = ? and iCveMatricula= ?";

    lPStmt = conn.prepareStatement(lSQL);

    lPStmt.setInt(1,vData.getInt("iCveVehiculo"));
    lPStmt.setInt(2,vData.getInt("iEjercicioMatOri"));
    lPStmt.setInt(3,vData.getInt("iCveMatriculaOri"));
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


  public TVDinRep ActualizaAdmin(TVDinRep vData,Connection cnNested) throws DAOException{
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
       //iCveEntidadFed,iCveCapitania,cSerie,iConsecutivo,iCveTipoServicio,iCveTipoNavega,iDigVerificador,iEstatus,iEjercicio,iNumSolicitud,cEmbarcacion,cPropietario,iCveEmbarcacion,iCveMatricula,iEjercicio,iCvePropietario,
       String lSQL = "update MYRMatricula set iCvePais=?, iCveEntidadFed=?, iCveCapitaniaParaMat=?, cSerie=?, "+
                     "iConsecutivoMatricula=?, iDigVerificador=?, iCveTipoNavega=?, iCveTipoServ=?, lVigente=?,"+
                     "iCveEmbarcacion=?, lAprobada=?, iEstatus=?,iCvePropietario=?,dtBaja=?,iEjercicioSolicitud=?," +
                     "iNumSolicitud=?,iCvePuertoMat=? where iEjercicio = ? AND iCveMatricula = ? ";
       vData.addPK(vData.getString("iEjercicio"));
       vData.addPK(vData.getString("iCveMatricula"));

       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.setInt(1,1);
       lPStmt.setInt(2,vData.getInt("iCveEntidadFed"));
       lPStmt.setString(3,vData.getString("iCveCapitania"));
       lPStmt.setString(4,vData.getString("cSerie"));
       lPStmt.setInt(5,vData.getInt("iConsecutivo"));
       lPStmt.setInt(6,vData.getInt("iDigVerificador"));
       lPStmt.setInt(7,vData.getInt("iCveTipoNavega"));
       lPStmt.setInt(8,vData.getInt("iCveTipoServicio"));
       lPStmt.setInt(9,vData.getInt("lVigente"));
       lPStmt.setInt(10,vData.getInt("iCveEmbarcacion"));
       lPStmt.setInt(11,vData.getInt("lAprobada"));
       lPStmt.setInt(12,vData.getInt("iEstatus"));
       lPStmt.setInt(13,vData.getInt("iCvePropietario"));
       lPStmt.setDate(14,vData.getDate("dtBaja"));
       lPStmt.setInt(15,vData.getInt("iEjercicioSolicitud"));
       lPStmt.setInt(16,vData.getInt("iNumSolicitud"));
       lPStmt.setInt(17,vData.getInt("iCvePtoMatricula"));
       lPStmt.setInt(18,vData.getInt("iEjercicio"));
       lPStmt.setInt(19,vData.getInt("iCveMatricula"));

       lPStmt.executeUpdate();
       
       conn.commit();
       lPStmt.close();

       String cMatricula = ""+(vData.getInt("iCveEntidadFed")>9?""+vData.getInt("iCveEntidadFed"):"0"+vData.getInt("iCveEntidadFed"))+//Entidad
                           ""+(vData.getInt("iCveCapitania")>9?""+vData.getString("iCveCapitania"):"0"+vData.getString("iCveCapitania"))+//Capitania
                           vData.getString("cSerie")+
                           ""+(vData.getInt("iConsecutivo")>99?""+vData.getInt("iConsecutivo"):vData.getInt("iConsecutivo")>9?"0"+vData.getInt("iConsecutivo"):"00"+vData.getInt("iConsecutivo"))+
                           ""+vData.getInt("iCveTipoNavega")+""+vData.getInt("iCveTipoServicio")+""+vData.getInt("iDigVerificador");
       TVDinRep vVeh = new TVDinRep();
       vVeh.put("cMatricula",cMatricula);
       vVeh.put("iCveEmbarcacion",vData.getInt("iCveEmbarcacion"));
       this.ActualizaVehAdmin(vVeh,conn);

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

public TVDinRep ActualizaVehAdmin(TVDinRep vData,Connection cnNested) throws DAOException{
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
     vData.addPK(vData.getString("iCveEmbarcacion"));
     String cVehMat = "UPDATE VEHVEHICULO SET CMATRICULA = ? WHERE ICVEVEHICULO=?";
     lPStmt = conn.prepareStatement(cVehMat);
     lPStmt.setString(1,vData.getString("cMatricula"));
     lPStmt.setInt(2,vData.getInt("iCveEmbarcacion"));
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
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update MYRMatricula set iCvePais=?, iCveEntidadFed=?, iCveOficina=?, iCveCapitaniaParaMat=?, iConsecutivoMatricula=?, iDigVerificador=?, iTipoNavegacion=?, iTipoServicio=?, lVigente=?, iCveEmbarcacion=?, iCveOficinaRPMN=?, iFolioRPMN=?, iPartida=?, iNumSolicitud=?, iEjercicioSolicitud=?, lAprobada=?, lEstatus=? where iEjercicio = ? AND iCveMatricula = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveMatricula, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep updateiEstatus(TVDinRep vData,Connection cnNested) throws DAOException{
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

      String aIcveMat[] = vData.getString("iCveMatricula").split(",");
      String lSQL = "";
      for(int iContMat=0;iContMat<aIcveMat.length;iContMat++){
        lSQL = " update MYRMatricula set  iEstatus=? where iCveMatricula = ? AND iCveEntidadFed = ? and iejercicio = ? ";
        vData.put("iEstatus", VParametros.getPropEspecifica("MatriculaRecibida"));

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEstatus"));
        //lPStmt.setInt(2,vData.getInt("iCveMatricula"));
        lPStmt.setInt(2,Integer.parseInt(aIcveMat[iContMat]));
        lPStmt.setInt(3,vData.getInt("iCveEntidadFed"));
        lPStmt.setInt(4,vData.getInt("iEjercicio"));

        lPStmt.executeUpdate();
        if(lPStmt != null){
          lPStmt.close();
        }

      }



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


  public TVDinRep updateEstatus(TVDinRep vData,Connection cnNested) throws DAOException{
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

      String aIcveMat[] = vData.getString("iCveMatricula").split(",");
      String aIEjercicio[] = vData.getString("iEjercicio").split(",");
      String lSQL = "";
      for(int iContMat=0;iContMat<aIcveMat.length;iContMat++){
        lSQL = " update MYRMatricula set  iEstatus=? where iCveMatricula = ? AND iEjercicio = ? ";
        vData.put("iEstatus", VParametros.getPropEspecifica("MatriculaRecibida"));

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEstatus"));
        //lPStmt.setInt(2,vData.getInt("iCveMatricula"));
        lPStmt.setInt(2,Integer.parseInt(aIcveMat[iContMat]));
        lPStmt.setInt(3,Integer.parseInt(aIEjercicio[iContMat]));

        lPStmt.executeUpdate();
        if(lPStmt != null){
          lPStmt.close();
        }

      }



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


 public TVDinRep cancelaMatricula(TVDinRep vData,Connection cnNested) throws DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   TParametro  vParametros = new TParametro("44");
   boolean lSuccess = true;
   int iUpdate = 0;

   String lSQL = "";
   TFechas fecha = new TFechas();

   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     if(cnNested == null){
       conn.commit();
     }

     lSQL = "UPDATE MYRMATRICULA SET DTBAJA = '" + fecha.TodaySQL() +"', " +
            " INUMSOLCANC = " + vData.getInt("iNumSolicitud") + ", " +
            " IEJERCICIOSOLCANC = "  + vData.getInt("iEjercicioSolicitud") +
            " WHERE MYRMATRICULA.IEJERCICIO = " + vData.getInt("iEjercicioMat") + " AND MYRMATRICULA.ICVEMATRICULA = " + vData.getInt("iCveMatricula") ;
     lPStmt = conn.prepareStatement(lSQL);
     iUpdate = lPStmt.executeUpdate();

     if(vData.getInt("iEjercicioMat")>0 && vData.getInt("iCveMatricula")>0){
       TDMYRMatriculaHist MatHist = new TDMYRMatriculaHist();
       TVDinRep vHistorico = new TVDinRep();
       vHistorico.put("iEjercicio",vData.getInt("iEjercicioMat"));
       vHistorico.put("iCveMatricula",vData.getInt("iCveMatricula"));
       vHistorico.put("iCveEmbarcacion",vData.getInt("iCveEmbarcacion"));
       vHistorico.put("iCveUsuario",vData.getInt("iCveUsuario"));
       MatHist.insert(vHistorico,conn);
     }


     try {
         if(lPStmt != null){
           lPStmt.close();
         }
     }
     catch (Exception ex) {
       ex.printStackTrace();
       iUpdate = 0;
     }

     if(iUpdate>0){
       lSQL = "UPDATE VEHVehiculo SET cMatricula = '' WHERE iCveVehiculo =  " + vData.getInt("iCveEmbarcacion");
       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.executeUpdate();
       if(lPStmt != null){
         lPStmt.close();
       }

     }


   } catch(Exception ex){
     ex.printStackTrace();
     warn("cancelaMatricula",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("cancelaMatricula.rollback",e);
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
       warn("cancelaMatricula.close",ex2);
     }

     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }

 }
 public TVDinRep asignaHistMatricula(TVDinRep vData,Connection cnNested) throws DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   TParametro  vParametros = new TParametro("44");
   boolean lSuccess = true;
   int iUpdate = 0;

   String lSQL = "";
   String lSQL1 = "";
   TFechas fecha = new TFechas();

   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     if(cnNested == null){
       conn.commit();
     }
     String cEmbarcacion = "SELECT ICVETIPONAVEGA,ICVETIPOSERV,icvepropietario,CNOMEMBARCACION FROM vehembarcacion where icvevehiculo = "+vData.getInt("iCveEmbarcacion");
     Vector vcEmbarcacion = this.findByCustom("",cEmbarcacion);
     TVDinRep vEmbarcacion = (TVDinRep) vcEmbarcacion.get(0);

     lSQL = "UPDATE MYRMatricula SET iCveTipoNavega=?,iCveTipoServ=?,iCveEmbarcacion=?,"+
            "iNumSolicitud=?,iEjercicioSolicitud=?,iCvePropietario=?,cNomEmbarcacion=?, iEstatus=?, lAprobada = 1, "+
            "iCveUsuAsigna=?, lVigente=1, dtAsignacion = ?, iCvePuertoMat = ?, iCveTipoPosesion = ? " +
            " WHERE iEjercicio = " + vData.getInt("iEjercicio") + " AND iCveMatricula = " + vData.getInt("iCveMatricula");

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iTipoNav"));
     lPStmt.setInt(2,vData.getInt("iTipoServ"));
     lPStmt.setInt(3,vData.getInt("iCveEmbarcacion"));
     lPStmt.setInt(4,0);
     lPStmt.setInt(5,0);
     lPStmt.setInt(6,vEmbarcacion.getInt("iCvePropietario"));
     lPStmt.setString(7,vEmbarcacion.getString("cNomEmbarcacion"));
     lPStmt.setInt(8,new Integer(vParametros.getPropEspecifica("MatriculaAsignada")).intValue());
     lPStmt.setInt(9,vData.getInt("iCveUsuario"));
     lPStmt.setDate(10,fecha.TodaySQL());
     lPStmt.setInt(11,vData.getInt("iCvePuertoMat"));
     lPStmt.setInt(12,vData.getInt("iCveTipoRespons"));
     iUpdate = lPStmt.executeUpdate();
     conn.commit();


     lSQL1 = "UPDATE VEHVEHICULO SET VEHVEHICULO.CMATRICULA = '" + vData.getString("cMatricula") +"' " +
                   "WHERE VEHVEHICULO.ICVEVEHICULO = " + vData.getInt("iCveEmbarcacion") ;
     lPStmt1 = conn.prepareStatement(lSQL1);
     lPStmt1.executeUpdate();
     conn.commit();


     try {
         if(lPStmt != null){
           lPStmt.close();
         }
     }
     catch (Exception ex) {
       ex.printStackTrace();
       iUpdate = 0;
     }

     /*if(iUpdate>0){
       lSQL = "UPDATE VEHVehiculo SET cMatricula = '' WHERE iCveVehiculo =  " + vData.getInt("iCveEmbarcacion");
       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.executeUpdate();
       if(lPStmt != null){
         lPStmt.close();
         lPStmt1.close();
       }

     }*/


   } catch(Exception ex){
     ex.printStackTrace();
     warn("cancelaMatricula",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("cancelaMatricula.rollback",e);
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
       warn("cancelaMatricula.close",ex2);
     }

     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }

 }

 public TVDinRep asignaMatricula(TVDinRep vData,Connection cnNested) throws DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   TParametro  vParametros = new TParametro("44");
   int iEjercicio = 0, iCveMatricula = 0, iUpdate = 0, iCapMatricula=0;
   Vector vRegs = new Vector();
   boolean lSuccess = true, lHayMatriculas = true;
   String lSQL = "";
   TFechas fecha = new TFechas();
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }

     //Cuando es una actualización de matrícula, si tiene una asignada se cancela en automatico
     if(vData.getString("hdCambioMatric").equals("1")){
       lSQL = "SELECT MYRMATRICULA.IEJERCICIO, MYRMATRICULA.ICVEMATRICULA " +
              "FROM MYRMATRICULA " +
              "WHERE MYRMATRICULA.ICVEEMBARCACION = " + vData.getInt("iCveEmbarcacion") + " AND MYRMATRICULA.DTBAJA IS NULL ";

       vRegs = super.FindByGeneric("",lSQL,dataSourceName);
       if (vRegs.size() > 0){
         lSQL = "UPDATE MYRMATRICULA set MYRMATRICULA.DTBAJA = '" + fecha.TodaySQL() + "' " +
                "WHERE MYRMATRICULA.ICVEEMBARCACION = " + vData.getInt("iCveEmbarcacion") + " AND MYRMATRICULA.DTBAJA IS NULL ";

         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.executeUpdate();
         conn.commit();
         if(lPStmt != null){
           lPStmt.close();
         }

       }

     }


     lSQL = "SELECT MYRMATRICULA.IEJERCICIO, MYRMATRICULA.ICVEMATRICULA, MYRMATRICULA.ICVEENTIDADFED, " +
            "       MYRMATRICULA.ICVECAPITANIAPARAMAT, MYRMATRICULA.CSERIE, " +
            "       MYRMATRICULA.ICONSECUTIVOMATRICULA, MYRMATRICULA.IESTATUS, MYRMATRICULA.IDIGVERIFICADOR " +
            "FROM MYRMATRICULA " +
            " WHERE MYRMATRICULA.ICVEENTIDADFED = " + vData.getString("iCveEntidadFed") +
            " AND   MYRMATRICULA.ICVEOFICINA = " + vData.getString("iCvePuertoMat") +
            " AND  MYRMATRICULA.IESTATUS = " + vParametros.getPropEspecifica("MatriculaRecibida")  +
            " ORDER BY MYRMATRICULA.ICVEENTIDADFED, MYRMATRICULA.ICVECAPITANIAPARAMAT, MYRMATRICULA.CSERIE, MYRMATRICULA.ICONSECUTIVOMATRICULA ";
     vRegs = super.FindByGeneric("",lSQL,dataSourceName);
      if (vRegs.size() > 0){
        TVDinRep vDatos = (TVDinRep) vRegs.get(0);
        iEjercicio = vDatos.getInt("iEjercicio") ;
        iCveMatricula = vDatos.getInt("iCveMatricula");
        iCapMatricula = vDatos.getInt("ICVECAPITANIAPARAMAT");
        vData.put("iEjercicio",vDatos.getInt("iEjercicio"));
        vData.put("iCveMatricula",vDatos.getInt("iCveMatricula"));
        vData.put("iConsecutivoMatricula",vDatos.getInt("iConsecutivoMatricula"));
        vData.put("cSerie",vDatos.getString("cSerie"));
        vData.put("iDigVerificador",vDatos.getString("iDigVerificador"));
      }
      else
        lHayMatriculas = false;

      lSQL = "UPDATE MYRMatricula SET iCveTipoNavega=?,iCveTipoServ=?,iCveEmbarcacion=?,"+
             "iNumSolicitud=?,iEjercicioSolicitud=?,iCvePropietario=?,cNomEmbarcacion=?, iEstatus=?, lAprobada = 1, "+
             "iCveUsuAsigna=?, lVigente=1, dtAsignacion = ?, iCvePuertoMat = ?, iCveTipoPosesion = ? " +
             " WHERE iEjercicio = " + iEjercicio + " AND iCveMatricula = " + iCveMatricula;

     vData.addPK(iEjercicio+"");
     vData.addPK(iCveMatricula+"");

     lPStmt = conn.prepareStatement(lSQL);

     lPStmt.setInt(1,vData.getInt("iCveTipoNavega"));
     lPStmt.setInt(2,vData.getInt("iCveTipoServ"));
     lPStmt.setInt(3,vData.getInt("iCveEmbarcacion"));
     lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
     lPStmt.setInt(5,vData.getInt("iEjercicioSolicitud"));
     lPStmt.setInt(6,vData.getInt("iCvePropietario"));
     lPStmt.setString(7,vData.getString("cNomEmbarcacion"));
     lPStmt.setInt(8,new Integer(vParametros.getPropEspecifica("MatriculaAsignada")).intValue());
     lPStmt.setInt(9,vData.getInt("iCveUsuario"));
     lPStmt.setDate(10,fecha.TodaySQL());
     lPStmt.setInt(11,vData.getInt("iCvePuertoMat"));
     lPStmt.setInt(12,vData.getInt("iCveTipoRespons"));
     iUpdate = lPStmt.executeUpdate();

     TDMYRMatriculaHist MatHist = new TDMYRMatriculaHist();
     TVDinRep vHistorico = new TVDinRep();
     vHistorico.put("iEjercicio",iEjercicio);
     vHistorico.put("iCveMatricula",iCveMatricula);
     vHistorico.put("iCveEmbarcacion",vData.getInt("iCveEmbarcacion"));
     vHistorico.put("iCveUsuario",vData.getInt("iCveUsuario"));
     MatHist.insert(vHistorico,conn);
     try {
         if(lPStmt != null){
           lPStmt.close();
         }
     }
     catch (Exception ex) {
       ex.printStackTrace();
       iUpdate = 0;
     }
     //Si asigna la matrícula entonces actualiza la tabla de VEHVehiculo
     if (iUpdate>0){
       String cMatricula="";
       int iEntidad = vData.getInt("iCveEntidadFed");
       int iCapitania = vData.getInt("iCveCapitaniaParaMat");
       int iConsec = vData.getInt("iConsecutivoMatricula");
       cMatricula = "";

       //Entidad
       if (iEntidad<10)
         cMatricula = "0"+iEntidad;
       else
         cMatricula = iEntidad+"";

       //capitania
       if(iCapitania<10)
         cMatricula+="0"+iCapMatricula;
       else
         cMatricula+=iCapMatricula;

       //serie
         cMatricula+=vData.getString("cSerie");

       //Consecutivo
       if(iConsec<10)
         cMatricula+="00"+iConsec;
       if(iConsec>=10 && iConsec<100)
         cMatricula+="0"+iConsec;
       if(iConsec>=100)
         cMatricula+=iConsec;
       cMatricula+=vData.getInt("iCveTipoNavega");
       cMatricula+=vData.getInt("iCveTipoServ");
       cMatricula+=vData.getString("IDIGVERIFICADOR");


       lSQL = "UPDATE VEHVEHICULO SET VEHVEHICULO.CMATRICULA = '" + cMatricula +"' " +
              "WHERE VEHVEHICULO.ICVEVEHICULO = " + vData.getInt("iCveEmbarcacion") ;
       lPStmt = conn.prepareStatement(lSQL);
       iUpdate = lPStmt.executeUpdate();

     }


     if(cnNested == null){
       conn.commit();
     }
   } catch(Exception ex){
     ex.printStackTrace();
     warn("asignaMatricula",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("asignaMatricula.rollback",e);
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
       warn("asignaMatricula.close",ex2);
     }
     if(!lHayMatriculas)
       throw new DAOException("No existen matrículas para asignar, por favor reportelo al departamento de matrículas de Oficinas Cenrtales.");

     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }
}
 public TVDinRep guardaHistorico(TVDinRep vData,Connection cnNested) throws DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   TParametro vParametros = new TParametro("44");
   boolean lSuccess = true,lHayMatriculas = true;

   String lSQL = "";
   TFechas fecha = new TFechas();
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     vData.remove("iCveMatricula");
     Vector vcMaxHistoico = this.findByCustom("","SELECT MAX(ICVEMATRICULA) as iCveMatricula FROM MYRMATRICULA WHERE IEJERCICIO = 0");
     if(vcMaxHistoico.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcMaxHistoico.get(0);
         vData.put("iCveMatricula",vUltimo.getInt("iCveMatricula") + 1);
      }else
         vData.put("iCveMatricula",1);
       String InsertMat = "insert into MYRMATRICULA (iEjercicio,iCveMatricula,iCvePais,iCveEntidadFed,iCveCapitaniaParaMat, " +
                         "                          cSerie,iConsecutivoMatricula,iDigVerificador,iCveOficina,iEstatus, " +
                         "                          LVIGENTE,LAPROBADA,ICVEPUERTOMAT,iCveEmbarcacion,iNumSolicitud, " +
                         "                          iEjercicioSolicitud,iCvePropietario,cNomEmbarcacion,iCveTipoServ,iCveTipoNavega, " +
                         "                          iCveUsuRecibe,iCveUsuAsigna,iCveTipoPosesion,dtAsignacion) " +
                         "                  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

     lPStmt = conn.prepareStatement(InsertMat);

     int iEntidad     = Integer.parseInt(vData.getString("cMatricula").substring(0,2),10);
     int iCapitania   = Integer.parseInt(vData.getString("cMatricula").substring(2,4),10);
     String cSerie    = vData.getString("cMatricula").substring(4,5);
     int iConsecutivo = Integer.parseInt(vData.getString("cMatricula").substring(5,8),10);
     int iTipoNav     = Integer.parseInt(vData.getString("cMatricula").substring(8,9),10);
     int iTipoServ    = Integer.parseInt(vData.getString("cMatricula").substring(9,10),10);
     int iDigVerif    = Integer.parseInt(vData.getString("cMatricula").substring(10,11),10);

     lPStmt.setInt(1,0);
     lPStmt.setInt(2,vData.getInt("iCveMatricula"));
     lPStmt.setInt(3,1);
     lPStmt.setInt(4,iEntidad);
     lPStmt.setInt(5,iCapitania);
     lPStmt.setString(6,cSerie);
     lPStmt.setInt(7,iConsecutivo);
     lPStmt.setInt(8,iDigVerif);
     lPStmt.setInt(9,vData.getInt("iCvePuertoMat"));
     lPStmt.setInt(10,Integer.parseInt(VParametros.getPropEspecifica("MatriculaAsignada")));
     lPStmt.setInt(11,1);
     lPStmt.setInt(12,1);
     lPStmt.setInt(13,vData.getInt("iCvePuertoMat"));
     lPStmt.setInt(14,vData.getInt("iCveEmbarcacion"));
     lPStmt.setInt(15,0);
     lPStmt.setInt(16,0);
     lPStmt.setInt(17,vData.getInt("iCvePropietario"));
     lPStmt.setString(18,vData.getString("cNomEmbarcacion"));
     lPStmt.setInt(19,iTipoServ);
     lPStmt.setInt(20,iTipoNav);
     lPStmt.setInt(21,0);
     lPStmt.setInt(22,vData.getInt("iCveUsuario"));
     lPStmt.setInt(23,0);
     lPStmt.setDate(24,fecha.getDateSQL(fecha.getThisTime()));
     lPStmt.executeUpdate();
     conn.commit();
     lPStmt.close();

     lSQL = "UPDATE VEHVEHICULO SET VEHVEHICULO.CMATRICULA = '" + vData.getString("cMatricula") +
            "' " + "WHERE VEHVEHICULO.ICVEVEHICULO = " +vData.getInt("iCveEmbarcacion");
     lPStmt1 = conn.prepareStatement(lSQL);
     lPStmt1.executeUpdate();
     conn.commit();
     lPStmt1.close();

     //Se comentan estas lineas para el registro de MYRMatriculaHist debido a que los datos con los que contamos son los actuales
     //de inscribirse un dato historico en esta tabla quedaría con la fecha y datos actuales lo cual no sería coherente
     //TDMYRMatriculaHist matriculaHist = new TDMYRMatriculaHist();
     //matriculaHist.insert(vData,conn);
     if(cnNested == null){
       conn.commit();
     }
   } catch(Exception ex){
     ex.printStackTrace();
     warn("asignaMatricula",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("asignaMatricula.rollback",e);
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
       warn("asignaMatricula.close",ex2);
     }
     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }
 }

 public TVDinRep insert(TVDinRep vData) throws DAOException{
   DbConnection dbConn = null;
   Connection conn = null;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt2 = null;
   Statement lStmt = null;
   ResultSet rs = null;
   boolean lSuccess = true;
   String cConsecutivoMatricula = "";
   int iEjercicio = 0;
   int iCveMatricula = 0;
   int iDigVerificador = 0;
   String cMatricula = "";
   try{
     dbConn = new DbConnection(dataSourceName);
     conn = dbConn.getConnection();
     conn.setAutoCommit(false);
     conn.setTransactionIsolation(2);
     lStmt = conn.createStatement();
     String lSQL =
         " SELECT cConsecutivoMatricula, iEjercicio, iCveMatricula "+//18
         " FROM MYRMatricula mat "+
         " WHERE iEjercicio = "+vData.getInt("iEjercicio")+
         " AND iNumSolicitud = "+vData.getInt("iNumSolicitud")+
         " AND iCveEntidadFed = "+vData.getInt("iEntidadFed")+
         " AND iCveOficina = "+ vData.getInt("iCveOficina")+
         " AND iCveEmbarcacion = "+vData.getInt("iCveVehiculo")+
         " AND iCveCapitaniaParaMat = "+vData.getInt("iCveCapitaniaParaMat");

     rs = lStmt.executeQuery(lSQL);
     while(rs.next()){
        cConsecutivoMatricula = rs.getString("cConsecutivoMatricula");
        iEjercicio = rs.getInt("iEjercicio");
        iCveMatricula = rs.getInt("iCveMatricula");
     }

     if(vData.getInt("iEntidadFed") < 10)
       cMatricula += "0"+vData.getInt("iEntidadFed");
     else
       cMatricula += vData.getInt("iEntidadFed");

     cMatricula += vData.getInt("iCveCapitaniaParaMat");

     cMatricula += cConsecutivoMatricula;

     cMatricula +=vData.getInt("iCveTipoNavegacion")+""+vData.getInt("iCveTipoServ");

     TDigitoVerificador dvDV = new TDigitoVerificador(cMatricula);
     iDigVerificador = dvDV.getiDigito();

     lSQL =
         " UPDATE  MYRMatricula SET "+
         " iDigVerificador = ?, "+
         " lVigente = 1, "+
         " iNumSolicitud = ?, "+
         " iEjercicioSolicitud = ?, "+
         " lAprobada = 1, "+
         " lVigente = 1, "+
         " iCvePropietario = ?, "+
         " cNomEmbarcacion = ?, "+
         " iCveTipoServ = ?, "+
         " iCveTipoNavega = ? "+
         " WHERE iEjercicio = ? "+
         " AND iCveMatricula = ? ";

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,iDigVerificador);
     lPStmt.setInt(2,iEjercicio);
     lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
     lPStmt.setInt(4,vData.getInt("iEjercicio"));
     lPStmt.setInt(5,vData.getInt("iCvePropietario"));
     lPStmt.setInt(6,vData.getInt("cNombre"));
     lPStmt.setInt(7,vData.getInt("iCveTipoServ"));
     lPStmt.setInt(8,vData.getInt("iCveTipoNavegacion"));

     lPStmt.setInt(9,iEjercicio);
     lPStmt.setInt(10,iCveMatricula);

     lPStmt.executeUpdate();

     lSQL =
         " UPDATE VEHVehiculo SET "+
         " cMatricula = ? "+
         " WHERE iCveVehiculo = ? ";

     lPStmt2 = conn.prepareStatement(lSQL);
     lPStmt2.setString(1,cMatricula);
     lPStmt2.setInt(2,vData.getInt("iCveVehiculo"));
     lPStmt2.executeUpdate();
     conn.commit();

   }
   catch(Exception ex){
     ex.printStackTrace();
     warn("insert",ex);

       try{
         conn.rollback();
       } catch(Exception e){
         fatal("insert.rollback",e);
       }

     lSuccess = false;
   } finally{
     try{
       if(lPStmt != null){
         lPStmt.close();
       }
       if(lPStmt2 != null){
         lPStmt2.close();
       }
       if(lStmt != null){
         lStmt.close();
       }
       if(rs != null){
         rs.close();
       }

         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();

     } catch(Exception ex2){
       warn("insert.close",ex2);
     }
     if(lSuccess == false)
       throw new DAOException("");
     return vData;
   }
 }


}

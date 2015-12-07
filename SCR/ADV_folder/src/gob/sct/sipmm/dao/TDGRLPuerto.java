package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLPuerto.java</p>
 * <p>Description: DAO de la entidad GRLPuerto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author cabrito
 * @version 1.0
 */
public class TDGRLPuerto extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private int hdCveEntidad;
  private int iCveEntidad;

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
   * <p><b> insert into GRLPuerto(iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCvePuerto, </b></p>
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
          "insert into GRLPuerto(iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad," +
          "iCveLitoral,iCveOficinaAdscrita,iOrden,iPortId,lEstadisticaDGP,lEstadisticaRutaCarga,lEstadisticaRutaPasaje) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCvePuerto) AS iCvePuerto from GRLPuerto");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCvePuerto",vUltimo.getInt("iCvePuerto") + 1);
      }else
         vData.put("iCvePuerto",1);
         vData.addPK(vData.getString("iCvePuerto"));


      //AGREGAR AL ULTIMO iOrden ...
      Vector vcData1 = findByCustom("","select MAX(iOrden) AS iOrden "+
                                   "from GRLPuerto where iCveEntidadFed = "+vData.getInt("iCveEntidadFed"));
      if(vcData1.size() > 0){
        TVDinRep vUltimoOrden = (TVDinRep) vcData1.get(0);
        vData.put("iOrden",vUltimoOrden.getInt("iOrden") + 1);
      }else
        vData.put("iOrden",1);

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePuerto"));
      lPStmt.setString(2,vData.getString("cDscPuerto"));
      lPStmt.setInt(3,vData.getInt("iCveTipoPuerto"));
      lPStmt.setInt(4,vData.getInt("iCvePais"));
      lPStmt.setInt(5,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(6,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(7,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(8,vData.getInt("iCveLitoral"));
      lPStmt.setInt(9,vData.getInt("iCveOficinaAdscrita"));
      lPStmt.setInt(10,vData.getInt("iOrden"));
      lPStmt.setInt(11,vData.getInt("iPortId"));
      lPStmt.setInt(12,vData.getInt("lEstadisticaDGP"));
      lPStmt.setInt(13,vData.getInt("lEstadisticaRutaCarga"));
      lPStmt.setInt(14,vData.getInt("lEstadisticaRutaPasaje"));
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
   * <p><b> delete from GRLPuerto where iCvePuerto = ?  </b></p>
   * <p><b> Campos Llave: iCvePuerto, </b></p>
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
    PreparedStatement lPStmt1 = null;
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
      String lSQL = "delete from GRLPuerto where iCvePuerto = ?  ";
      //...
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCvePuerto"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

      String lSQL1 = "update GRLPuerto set  iOrden = (iOrden - 1) where iCveEntidadFed = ? and iOrden > ?";
      //...
      lPStmt1 = conn.prepareStatement(lSQL1);
      lPStmt1.setInt(1,vData.getInt("iCveEntidadFed"));
      lPStmt1.setInt(2,vData.getInt("iOrden"));
      lPStmt1.executeUpdate();
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
        warn("delete.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMsg);
      return lSuccess;
    }
  }
  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update GRLPuerto set cDscPuerto=?, iCveTipoPuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCvePuerto = ?  </b></p>
   * <p><b> Campos Llave: iCvePuerto, </b></p>
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


      hdCveEntidad = vData.getInt("hdiCveEntidad");
      iCveEntidad = vData.getInt("iCveEntidadFed");

      String lSQL = "update GRLPuerto set cDscPuerto=?, iCveTipoPuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, " +
                    "iCveLocalidad=?,iCveLitoral=?,iCveOficinaAdscrita=?,iOrden=?, iPortId= ?,lEstadisticaDGP = ?,lEstadisticaRutaCarga = ?,lEstadisticaRutaPasaje = ?,LHABILITADO=? where iCvePuerto = ? ";

      //AGREGAR AL ULTIMO iOrden ...
      Vector vcData1 = findByCustom("","select MAX(iOrden) AS iOrden "+
                                    "from GRLPuerto where iCveEntidadFed = "+vData.getInt("iCveEntidadFed"));
      if(vcData1.size() > 0){
        TVDinRep vUltimoOrden = (TVDinRep) vcData1.get(0);
        vData.put("iOrden",vUltimoOrden.getInt("iOrden") + 1);
      }else
        vData.put("iOrden",1);


      vData.addPK(vData.getString("iCvePuerto"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDscPuerto"));
      lPStmt.setInt(2,vData.getInt("iCveTipoPuerto"));
      lPStmt.setInt(3,vData.getInt("iCvePais"));
      lPStmt.setInt(4,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(5,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(6,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(7,vData.getInt("iCveLitoral"));
      lPStmt.setInt(8,vData.getInt("iCveOficinaAdscrita"));

      if(hdCveEntidad != iCveEntidad)
        lPStmt.setInt(9,vData.getInt("iOrden"));
      else
        lPStmt.setInt(9,vData.getInt("hdiOrden"));

      lPStmt.setInt(10,vData.getInt("iPortId"));
      lPStmt.setInt(11,vData.getInt("lEstadisticaDGP"));
      lPStmt.setInt(12,vData.getInt("lEstadisticaRutaCarga"));
      lPStmt.setInt(13,vData.getInt("lEstadisticaRutaPasaje"));
      lPStmt.setInt(14,vData.getInt("lHabilitado"));
      lPStmt.setInt(15,vData.getInt("iCvePuerto"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

      if(hdCveEntidad != iCveEntidad){
        String lSQL1 = "update GRLPuerto set  iOrden = (iOrden - 1) where iCveEntidadFed = ? and iOrden > ?";

        lPStmt1 = conn.prepareStatement(lSQL1);
        lPStmt1.setInt(1,vData.getInt("hdiCveEntidad"));
        lPStmt1.setInt(2,vData.getInt("hdiOrden"));
        lPStmt1.executeUpdate();
        if(cnNested == null){
          conn.commit();
        }
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

 public TVDinRep DatosOrden(TVDinRep vData, Connection cnNested) throws
    DAOException{
    boolean lSuccess = true;
    String[] cEliminar = null,
             cAgregar  = null,
             cPuertoOrden = null,
             cOrdenPuerto = null;
    if(vData.getString("iCvePuertoOrden") != null && !vData.getString("iCvePuertoOrden").equals(""))
      cPuertoOrden = vData.getString("iCvePuertoOrden").split(",");
    if(vData.getString("iOrdenPuerto") != null && !vData.getString("iOrdenPuerto").equals(""))
      cOrdenPuerto = vData.getString("iOrdenPuerto").split(",");
    TVDinRep vTemp;
    cError = "";
    try{  // Actualizar el orden de las señales de acuerdo a lo especificado
      if(cPuertoOrden != null && cPuertoOrden.length > 0 &&
         cOrdenPuerto != null && cOrdenPuerto.length > 0 &&
         cPuertoOrden.length == cOrdenPuerto.length){
        for(int i=0; i<cPuertoOrden.length; i++){
          vTemp = new TVDinRep();
          vTemp.put("iCvePuerto",cPuertoOrden[i]);
          vTemp.put("iOrden", cOrdenPuerto[i]);
          updateOrden(vTemp, null);
        }
      }
    }catch(Exception ex){
      ex.printStackTrace();
      cError += "No fue posible actualizar el orden completo.\\n" + ex.getMessage();
      lSuccess = false;
    }
    if(lSuccess == false)
       throw new DAOException(cError);
    return vData;
  }

  public TVDinRep updateOrden(TVDinRep vData, Connection cnNested) throws
    DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    String lSQL = null;

    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      lSQL = "UPDATE GRLPuerto set iOrden=? " +
             "WHERE iCvePuerto = ? ";

      vData.addPK(vData.getString("iCvePuerto"));
      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1, vData.getInt("iOrden"));
      lPStmt.setInt(2, vData.getInt("iCvePuerto"));
      lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException ex){
      cError = super.getSQLMessages(ex);
      lSuccess = false;
    } catch(Exception ex){
      cError = ex.getMessage();
      warn("update", ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback", e);
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
        warn("update.close", ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cError);

      return vData;
    }
  }


}

package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
/**
 * <p>Title: TDCYSDerechoSenalamiento.java</p>
 * <p>Description: DAO de la entidad CYSDerechoSenalamiento</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDCYSDerechoSenalamiento extends DAOBase{
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
   * <p><b> insert into CYSDerechoSenalamiento(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
    int iConsecutivo = this.findMax(vData.getInt("iCveTitulo"),vData.getInt("iEjercicio"));
    vData.put("iMovDerSenalamiento",iConsecutivo);
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSDerechoSenalamiento(iCveTitulo, " +
                    "iEjercicio, " +
                    "iMovDerSenalamiento, " +
                    "dtRegistro, " +
                    "cNumEscrito, " +
                    "dtEscrito, " +
                    "dtPresentacion, " +
                    "dtPago, " +
                    "iEjercicioIniPago, " +
                    "iPeriodoIniPago, " +
                    "iEjercicioFinPago, " +
                    "iPeriodoFinPago, " +
                    "dImpDerechos, " +
                    "dImpActualizacion, " +
                    "dImpRecargos, " +
                    "dImpTotalPagado, " +
                    "cObsFormato, " +
                    "iCveUsuario, " +
                    "cNumFactura, " +
                    "cCveSATDerechos, " +
                    "cCveDGPOPDerechos, " +
                    "cCveDGPOPActualizacion, " +
                    "cCveDGPOPRecargos, " +
                    "cMovDGPOPDerechos, " +
                    "cMovDGPOPActualizacion, " +
                    "cMovDGPOPRecargos, " +
                    "lCopiaLegible ) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovDerSenalamiento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovDerSenalamiento"));
      lPStmt.setDate(4,vData.getDate("dtRegistro"));
      lPStmt.setString(5,vData.getString("cNumEscrito"));
      lPStmt.setDate(6,vData.getDate("dtEscrito"));
      lPStmt.setDate(7,vData.getDate("dtPresentacion"));
      lPStmt.setDate(8,vData.getDate("dtPago"));
      lPStmt.setInt(9,vData.getInt("iEjercicioIniPago"));
      lPStmt.setInt(10,vData.getInt("iPeriodoIniPago"));
      lPStmt.setInt(11,vData.getInt("iEjercicioFinPago"));
      lPStmt.setInt(12,vData.getInt("iPeriodoFinPago"));
      lPStmt.setDouble(13,vData.getDouble("dImpDerechos"));
      lPStmt.setDouble(14,vData.getDouble("dImpActualizacion"));
      lPStmt.setDouble(15,vData.getDouble("dImpRecargos"));
      lPStmt.setDouble(16,vData.getDouble("dImpTotalPagado"));
      lPStmt.setString(17,vData.getString("cObsFormato"));
      lPStmt.setInt(18,vData.getInt("iCveUsuario"));
      lPStmt.setString(19,vData.getString("cNumFactura"));
      lPStmt.setString(20,vData.getString("cCveSATDerechos"));
      lPStmt.setString(21,vData.getString("cCveDGPOPDerechos"));
      lPStmt.setString(22,vData.getString("cCveDGPOPActualizacion"));
      lPStmt.setString(23,vData.getString("cCveDGPOPRecargos"));
      lPStmt.setString(24,vData.getString("cMovDGPOPDerechos"));
      lPStmt.setString(25,vData.getString("cMovDGPOPActualizacion"));
      lPStmt.setString(26,vData.getString("cMovDGPOPRecargos"));
      lPStmt.setInt(27,vData.getInt("lCopiaLegible"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
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
   * <p><b> delete from CYSDerechoSenalamiento where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "delete from CYSDerechoSenalamiento where iCveTitulo = ? " +
                    "and iEjercicio=? " +
                    "and iMovDerSenalamiento=? ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovDerSenalamiento"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = "" + sqle.getErrorCode();
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
   * <p><b> update CYSDerechoSenalamiento set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
      String lSQL = "update CYSDerechoSenalamiento " +
                    "set dtRegistro=?, " +
                    "cNumEscrito=?, " +
                    "dtEscrito=?, " +
                    "dtPresentacion=?, " +
                    "dtPago=?, " +
                    "iEjercicioIniPago=?, " +
                    "iPeriodoIniPago=?, " +
                    "iEjercicioFinPago=?, " +
                    "iPeriodoFinPago=?, " +
                    "dImpDerechos=?, " +
                    "dImpActualizacion=?, " +
                    "dImpRecargos=?, " +
                    "dImpTotalPagado=?, " +
                    "cObsFormato=?, " +
                    "iCveUsuario=?, " +
                    "cNumFactura=?, " +
                    "cCveSATDerechos=?, " +
                    "cCveDGPOPDerechos=?, " +
                    "cCveDGPOPActualizacion=?, " +
                    "cCveDGPOPRecargos=?, " +
                    "cMovDGPOPDerechos=?, " +
                    "cMovDGPOPActualizacion=?, " +
                    "cMovDGPOPRecargos=?, " +
                    "lCopiaLegible=? " +
                    "where iCveTitulo=? " +
                    "and iEjercicio=? " +
                    "and iMovDerSenalamiento=? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovDerSenalamiento"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setString(2,vData.getString("cNumEscrito"));
      lPStmt.setDate(3,vData.getDate("dtEscrito"));
      lPStmt.setDate(4,vData.getDate("dtPresentacion"));
      lPStmt.setDate(5,vData.getDate("dtPago"));
      lPStmt.setInt(6,vData.getInt("iEjercicioIniPago"));
      lPStmt.setInt(7,vData.getInt("iPeriodoIniPago"));
      lPStmt.setInt(8,vData.getInt("iEjercicioFinPago"));
      lPStmt.setInt(9,vData.getInt("iPeriodoFinPago"));
      lPStmt.setDouble(10,vData.getDouble("dImpDerechos"));
      lPStmt.setDouble(11,vData.getDouble("dImpActualizacion"));
      lPStmt.setDouble(12,vData.getDouble("dImpRecargos"));
      lPStmt.setDouble(13,vData.getDouble("dImpTotalPagado"));
      lPStmt.setString(14,vData.getString("cObsFormato"));
      lPStmt.setInt(15,vData.getInt("iCveUsuario"));
      lPStmt.setString(16,vData.getString("cNumFactura"));
      lPStmt.setString(17,vData.getString("cCveSATDerechos"));
      lPStmt.setString(18,vData.getString("cCveDGPOPDerechos"));
      lPStmt.setString(19,vData.getString("cCveDGPOPActualizacion"));
      lPStmt.setString(20,vData.getString("cCveDGPOPRecargos"));
      lPStmt.setString(21,vData.getString("cMovDGPOPDerechos"));
      lPStmt.setString(22,vData.getString("cMovDGPOPActualizacion"));
      lPStmt.setString(23,vData.getString("cMovDGPOPRecargos"));
      lPStmt.setInt(24,vData.getInt("lCopiaLegible"));
      lPStmt.setInt(25,vData.getInt("iCveTitulo"));
      lPStmt.setInt(26,vData.getInt("iEjericicio"));
      lPStmt.setInt(27,vData.getInt("iMovDerSenalamiento"));
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

  public int findMax(int iCveTitulo,int iEjercicio) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    Statement lStmt = null;
    ResultSet rs = null;
    int iConsecutivo = 0;
    try{

      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      lStmt = conn.createStatement();
      String lSQL =
          " SELECT MAX(iMovDerSenalamiento) AS iMovDerSenalamiento " +
          " FROM CYSDerechoSenalamiento " +
          " WHERE iCveTitulo = " + iCveTitulo +
          " and iEjercicio = " + iEjercicio;

      rs = lStmt.executeQuery(lSQL);
      while(rs.next()){
        iConsecutivo = rs.getInt("iMovDerSenalamiento");
      }
      iConsecutivo++;
    } catch(Exception ex){
      ex.printStackTrace();
    } finally{
      try{
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
      }
      return iConsecutivo;
    }
  }

  /*
  ******* De acuerdo a un Título se obtendrán los siguientes
          campos Fecha de Pago, Importe Total pagado e Importe
          de Derechos. Verificar que al menos un registro tenga
          estos tres importes diferentes de vacio o nulo, si al
          menos uno cumple se regresará un 1:True, en caso con--
          trario un 0:False
   * @param Vector
   * @throws DAOException
   * @return Vector
   */

  public Vector SitObligacion(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true, lEncontro = false;
    Vector vcSitObligacion = new Vector();
    int iTitulo=0, iEncontro = 0;

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
         conn.setTransactionIsolation(2);
      }

        iTitulo = vData.getInt("iCveTitulo");

        Vector vcData = findByCustom("","Select dtPago, " +
                 "       dImpDerechos, " +
                 "       dImpTotalPagado " +
                 "From CysDerechoSenalamiento " +
                 "Where iCveTitulo = " + iTitulo);

        if(vcData.size()>0){
          for(int i=0;i<vcData.size();i++){
                TVDinRep vUltimo = (TVDinRep) vcData.get(i);
                if(vUltimo.getDate("dtPago") != null && vUltimo.getDouble("dImpDerechos") != 0 &&
                   vUltimo.getDouble("dImpTotalPagado") != 0) {
                   lEncontro = true;
                   break;
                }
             }

             if(lEncontro){
               iEncontro = 1;
               TVDinRep vObligacion = new TVDinRep();
               vObligacion.put("iExiste",iEncontro);
               vcSitObligacion.add(vObligacion);
             }
        } else {
          TVDinRep vObligacion = new TVDinRep();
          vObligacion.put("iExiste",0);
          vcSitObligacion.add(vObligacion);
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
      return vcSitObligacion;
    }
  }
}


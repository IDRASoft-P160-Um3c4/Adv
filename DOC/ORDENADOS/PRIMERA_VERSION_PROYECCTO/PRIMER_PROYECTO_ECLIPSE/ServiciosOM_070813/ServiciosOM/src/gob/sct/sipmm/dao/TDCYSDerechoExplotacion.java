package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDCYSDerechoExplotacion.java</p>
 * <p>Description: DAO de la entidad CYSDerechoExplotacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
public class TDCYSDerechoExplotacion extends DAOBase{
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
   * <p><b> insert into CYSDerechoExplotacion(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
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
    vData.put("iMovDerechoE",iConsecutivo);
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSDerechoExplotacion(iCveTitulo, " +
                    "iEjercicio, " +
                    "iMovDerechoE, " +
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
                    "lCopiaLegible) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovDerechoE"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovDerechoE"));
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
   * <p><b> delete from CYSDerechoExplotacion where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
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
      String lSQL = "delete from CYSDerechoExplotacion where iCveTitulo = ? " +
                    "and iEjercicio=? " +
                    "and iMovDerechoE=? ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iMovDerechoE"));
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
   * <p><b> update CYSDerechoExplotacion set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
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
      String lSQL = "update CYSDerechoExplotacion " +
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
                    "and iMovDerechoE=? ";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iMovDerechoE"));

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
      lPStmt.setInt(25,vData.getInt("iEjercicioAcredita"));
      lPStmt.setInt(26,vData.getInt("iCveTitulo"));
      lPStmt.setInt(27,vData.getInt("iEjericicio"));
      lPStmt.setInt(28,vData.getInt("iMovDerechoE"));
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
          " SELECT MAX(iMovDerechoE) AS iMovDerechoE " +
          " FROM CYSDerechoExplotacion " +
          " WHERE iCveTitulo = " + iCveTitulo +
          " and iEjercicio = " + iEjercicio;

      rs = lStmt.executeQuery(lSQL);
      while(rs.next()){
        iConsecutivo = rs.getInt("iMovDerechoE");
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

  public Vector findDerechoExplota(TVDinRep vData) throws DAOException{
    TFechas tFecha = new TFechas();
    TDCYSRelacionIngresos dCYSRelacionIngresos = new TDCYSRelacionIngresos();
    Vector vcFinal = new Vector();
    Vector vcInicial = new Vector();
    int iCveTitulo=0, iEjercicioI=0, iEjercicioF=0, iPeriodoInicial=0,iPeriodoFinal=0;
    int iConsecutivo = 0;
    String cNombre="", cCveSatDerechos="";
    double dPago = 0, dDerechos=0, dPagoReal=0,dPagoIngresos=0;
    java.sql.Date dtIniVigencia = null, dtFechaActual=null, dtPago=null,dtCorrecta=null;
    java.sql.Date dtPresentacion = null;
    TVDinRep vInicial = new TVDinRep();

    try{

      iCveTitulo = vData.getInt("iCveTitulo");
      dtFechaActual = tFecha.TodaySQL();
      dtIniVigencia = vData.getDate("dtIniVigencia");

      if(dtIniVigencia.compareTo(dtFechaActual)<0){
          iEjercicioI = tFecha.getIntYear(dtIniVigencia);
          iPeriodoInicial = tFecha.getIntMonth(dtIniVigencia)+1;
          iEjercicioF = tFecha.getIntYear(dtFechaActual);

          while(iEjercicioI <= iEjercicioF){
              while(iPeriodoInicial <= 12){
                String cSQL =
                    "Select * from CysDerechoExplotacion where iCveTitulo = " +
                    iCveTitulo +
                    " and iEjercicioIniPago = " + iEjercicioI +
                    " and iPeriodoIniPago = " + iPeriodoInicial;
                Vector vcData = findByCustom("",cSQL);
                iConsecutivo += 1;
                if(vcData.size() > 0){
                  TVDinRep vDatos = (TVDinRep) vcData.get(0);
                  iPeriodoFinal = vDatos.getInt("iPeriodoFinPago");
                  dtPago = vDatos.getDate("dtPago");
                  dtPresentacion = vDatos.getDate("dtPresentacion");
                  dDerechos = vDatos.getDouble("dImpDerechos");
                  dtCorrecta = tFecha.getDateSQL("17",""+(iPeriodoFinal+1),""+iEjercicioI);
                  dPagoReal = (((0.05/12)*dDerechos)*((iPeriodoFinal-iPeriodoInicial)+1));
                  if(vDatos.getString("cCveSatDerechos") != null && vDatos.getString("cCveSatDerechos") != "" )
                    cCveSatDerechos = vDatos.getString("cCveSatDerechos");
                 else
                   cCveSatDerechos = vDatos.getString("cCveDGPOPDerechos");


                  TVDinRep vDinRep = new TVDinRep();
                  vDinRep.put("iCveTitulo",vDatos.getInt("iCveTitulo"));
                  vDinRep.put("iEjercicio",vDatos.getInt("iEjercicioIniPago"));
                  vDinRep.put("iPeriodoInicial",iPeriodoInicial);
                  vDinRep.put("iPeriodoFinal",iPeriodoFinal);

                  Vector vcRelIng = new Vector();
                  vcRelIng = dCYSRelacionIngresos.findRelacionIng(vDinRep);
                  if(vcRelIng.size() > 0){
                    int iMes = tFecha.getIntMonth(dtPago);
                    TVDinRep vValidacion = (TVDinRep) vcRelIng.get(0);
                    for(int i = iPeriodoInicial;i<=iPeriodoFinal;i++){
                       vInicial = new TVDinRep();
                       dPagoIngresos = vValidacion.getDouble("dImpTotal");
                       cNombre = "dPeriodo" + i;
                       dPago = vValidacion.getDouble(cNombre);
                       vInicial.put("iEjercicio",iEjercicioI);
                       vInicial.put("iPeriodo",i);
                       vInicial.put("dPago",dPago);

                       // Se checa que se haya realizado un pago
                       if(dPago>0) vInicial.put("lPagado",1);
                       else vInicial.put("lPagado",0);

                       //Se checa la fecha de pago
                       switch(i){
                          case 1:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==3)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 2:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==3)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 3:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==5)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 4:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==5)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 5:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==7)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 6:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==7)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 7:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==9)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 8:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==9)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 9:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==11)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 10:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==11)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 11:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==1)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                          case 12:
                            if(dtPago.getTime() <= dtCorrecta.getTime() && iMes==1)
                              vInicial.put("lCorrectoFe",1);
                            else vInicial.put("lCorrectoFe",0);
                            break;
                       }

                       //Se checa el importe del pago
                       if(dPagoReal==dPagoIngresos){
                          vInicial.put("lCorrecto",1);
                          vInicial.put("dDiferencia",0);
                       }
                       else{
                         if(i != iPeriodoFinal){
                            vInicial.put("lCorrecto",1);
                            vInicial.put("dDiferencia", (dPagoReal - dPagoIngresos));
                         }
                         else{
                            vInicial.put("lCorrecto",0);
                            vInicial.put("dDiferencia", (dPagoReal - dPagoIngresos));
                         }
                       }
                       vInicial.put("dIngresos",dDerechos);
                       vInicial.put("dImporte",dPagoReal);
                       vInicial.put("dtPago",dtPago);
                       vInicial.put("dtPresentacion",dtPresentacion);
                       vInicial.put("cCveSatDerechos",cCveSatDerechos);
                       vInicial.put("dImpPagado",dPagoIngresos);
                       vInicial.put("iConsecutivo",iConsecutivo);
                       vcInicial.addElement(vInicial);
                       iPeriodoInicial = i;
                    }
                }
                else{
                  vInicial = new TVDinRep();
                  vInicial.put("iEjercicio",iEjercicioI);
                  vInicial.put("iPeriodo",iPeriodoInicial);
                  vInicial.put("dPago",0);
                  vInicial.put("lPagado",0);
                  vInicial.put("lCorrecto",0);
                  vInicial.put("lCorrectoFe",0);
                  vInicial.put("dDiferencia",0);
                  vInicial.put("dIngresos",0);
                  vInicial.put("dImporte",0);
                  vInicial.put("dtPago",0);
                  vInicial.put("dtPresentacion",0);
                  vInicial.put("cCveSatDerechos",0);
                  vInicial.put("dImpPagado",0);
                  vInicial.put("iConsecutivo",iConsecutivo);
                  vcInicial.addElement(vInicial);
                }
              }
              else{
                vInicial = new TVDinRep();
                vInicial.put("iEjercicio",iEjercicioI);
                vInicial.put("iPeriodo",iPeriodoInicial);
                vInicial.put("dPago",0);
                vInicial.put("lPagado",0);
                vInicial.put("lCorrecto",0);
                vInicial.put("lCorrectoFe",0);
                vInicial.put("dDiferencia",0);
                vInicial.put("dIngresos",0);
                vInicial.put("dImporte",0);
                vInicial.put("dtPago",0);
                vInicial.put("dtPresentacion",0);
                vInicial.put("cCveSatDerechos",0);
                vInicial.put("dImpPagado",0);
                vInicial.put("iConsecutivo",iConsecutivo);
                vcInicial.addElement(vInicial);
              }
              iPeriodoInicial++;
            }
            iEjercicioI++;
          }
      }

      if(vcInicial.size()>0){
        int iConse = 1;
        int i=0, x=0;
        iPeriodoInicial = 0; iPeriodoFinal=0;
        for(int j=0;j<vcInicial.size();j++){
           x=j;
           vInicial = (TVDinRep) vcInicial.get(j);
           TVDinRep vFinal = new TVDinRep();
            i = vInicial.getInt("iConsecutivo");
               if(i==iConse){
                  if(iPeriodoInicial == 0) iPeriodoInicial = vInicial.getInt("iPeriodo");
                  iPeriodoFinal = vInicial.getInt("iPeriodo");
                  iConse=i;
               }
               else{
                  vInicial = (TVDinRep) vcInicial.get(j - 1);
                  if((vInicial.getInt("lCorrectoFe")==0) || (vInicial.getInt("lCorrecto")==0) ||
                     ( vInicial.getInt("lPagado") ==0)){
                      vFinal.put("dIngresos",vInicial.getDouble("dIngresos"));
                      vFinal.put("dImporte",vInicial.getDouble("dImporte"));
                      vFinal.put("iMesI",iPeriodoInicial);
                      vFinal.put("iEjercicioI",vInicial.getInt("iEjercicio"));
                      vFinal.put("iMesF",iPeriodoFinal);
                      vFinal.put("iEjercicioF",vInicial.getInt("iEjercicio"));
                      vFinal.put("dtPago",vInicial.getDate("dtPago"));
                      vFinal.put("dtPresentacion",vInicial.getDate("dtPresentacion"));
                      vFinal.put("cClave",vInicial.getString("cCveSatDerechos"));
                      vFinal.put("dImpPagado",vInicial.getDouble("dImpPagado"));
                      vFinal.put("dImpCorrecto",vInicial.getDouble("dImporte"));
                      vFinal.put("dDiferencia",vInicial.getDouble("dDiferencia"));
                      vcFinal.addElement(vFinal);
                  }

                  vInicial = (TVDinRep) vcInicial.get(j);
                  iPeriodoInicial=0;iPeriodoFinal=0;
                  if(iPeriodoInicial == 0) iPeriodoInicial = vInicial.getInt("iPeriodo");
                  iPeriodoFinal = vInicial.getInt("iPeriodo");
                  iConse=i;
               }
            i++;
         }

         vInicial = (TVDinRep) vcInicial.get(x);
         if(vInicial.getInt("lCorrectoFe")==0 || vInicial.getInt("lCorrecto")==0 || vInicial.getInt("lPagado") ==0){
           TVDinRep vFinal = new TVDinRep();
           vFinal.put("dIngresos",vInicial.getDouble("dIngresos"));
           vFinal.put("dImporte",vInicial.getDouble("dImporte"));
           vFinal.put("iMesI",iPeriodoInicial);
           vFinal.put("iEjercicioI",vInicial.getInt("iEjercicio"));
           vFinal.put("iMesF",iPeriodoFinal);
           vFinal.put("iEjercicioF",vInicial.getInt("iEjercicio"));
           vFinal.put("dtPago",vInicial.getDate("dtPago"));
           vFinal.put("dtPresentacion",vInicial.getDate("dtPresentacion"));
           vFinal.put("cClave",vInicial.getString("cCveSatDerechos"));
           vFinal.put("dImpPagado",vInicial.getDouble("dImpPagado"));
           vFinal.put("dImpCorrecto",vInicial.getDouble("dImporte"));
           vFinal.put("dDiferencia",vInicial.getDouble("dDiferencia"));
           vcFinal.addElement(vFinal);
         }
      }

    } catch(Exception e){
    cError = e.toString();
    e.printStackTrace();
  } finally{
    if(!cError.equals(""))
      throw new DAOException(cError);
    return vcFinal;
    }
 }

}


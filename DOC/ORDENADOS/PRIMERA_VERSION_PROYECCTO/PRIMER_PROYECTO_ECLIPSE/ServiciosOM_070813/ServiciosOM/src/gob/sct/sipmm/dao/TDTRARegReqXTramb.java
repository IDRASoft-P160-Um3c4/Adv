package gob.sct.sipmm.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;

/**
 * <p>Title: TDTRARegReqXTramb.java</p>
 * <p>Description: DAO de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina ||ICaballero
 * @version 1.0
 */
public class TDTRARegReqXTramb
    extends DAOBase{
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
   * <p><b> insert into TRARegReqXTram(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,iIdDocDigitalizado) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
          "insert into TRARegReqXTram(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe) values (?,?,?,?,?,0,0,(CURRENT DATE),?)";

      //AGREGAR AL ULTIMO ...
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveRequisito"));
      //lPStmt.setInt(6,vData.getInt("iEjercicioFormato"));
      //lPStmt.setInt(7,vData.getInt("iCveFormatoReg"));
      //lPStmt.setDate(8,vData.getDate("dtRecepcion"));
      lPStmt.setInt(6,vData.getInt("iCveUsuRecibe"));
      //lPStmt.setInt(10,vData.getInt("iIdDocDigitalizado"));

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
   * <p><b> delete from TRARegReqXTram where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
      String lSQL = "delete from TRARegReqXTram where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveRequisito"));

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
   * <p><b> update TRARegReqXTram set iEjercicioFormato=?, iCveFormatoReg=?, dtRecepcion=?, iCveUsuRecibe=?, iIdDocDigitalizado=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
    TFechas fechas = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL = "update TRARegReqXTram set  dtRecepcion=(CURRENT DATE) where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";
//      String lSQL = "update TRARegReqXTram set  dtRecepcion=(CURRENT DATE),dtNotificacion= NULL where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveRequisito"));
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

  public TVDinRep Recibe(TVDinRep vData,Connection cnNested) throws
    DAOException{
  DbConnection dbConn = null;
  Connection conn = cnNested;
  PreparedStatement lPStmt = null;
  PreparedStatement lPStmt1 = null;
  String[] cConjunto;

  boolean lSuccess = true;
  TDGRLDiaNoHabil diaNoHabil = new TDGRLDiaNoHabil();
  TFechas fecha = new TFechas();
  java.sql.Date dtHoy = fecha.TodaySQL(); //ELEL Fecha actual
  int ja = 0;
  int lbandera = 0; // Para saber si existen aun requisitos sin entregar
  int iNumDias = 0;
  int iNumDiasRes = 0;
  int lDiasNaturales = 0;
  int iDias = 0;
  int iDiasASumar = 0;
  boolean lDias;
  Vector cRequisito = new Vector();
  Vector cReqValido = new Vector();
  Vector cReqCan = new Vector();
  try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }

    /**** ELEL Se busca si la fecha de Recepción a excedido la Estimada de Entrega */
    /*El siguiente query se realiza para obtener la primer fecha de notificacion en caso de que existan otras*/
    cConjunto = vData.getString("cConjunto").split(",");

    for(int ij = 0;ij < cConjunto.length;ij++){
      cRequisito.add(cConjunto[ij]);
    }
    for(int ij = 0;ij < cRequisito.size();ij++){
      java.sql.Date dtNotificacion;
      Vector vcDataTemp = findByCustom("","SELECT " +
                                       "TRAREGREQXTRAM.DTNOTIFICACION as dtNotificacion, " +
                                       /*  "TRAREGSOLICITUD.DTESTIMADAENTREGA as dtEstimadaEntrega, " + */
                                       "TRACONFIGURATRAMITE.INUMDIASRESOL as iNumDiasRes, " +
                                       "TRACONFIGURATRAMITE.LDIASNATURALESRESOL as iDiasNat " +
                                       "FROM TRAREGREQXTRAM " +
                                       /*      "join TRAREGSOLICITUD ON TRAREGSOLICITUD.IEJERCICIO = TRAREGREQXTRAM.iejercicio " +
                                       "and TRAREGSOLICITUD.INUMSOLICITUD = TRAREGREQXTRAM.inumsolicitud " + */
                                       "JOIN TRACONFIGURATRAMITE ON TRACONFIGURATRAMITE.ICVETRAMITE = TRAREGREQXTRAM.ICVETRAMITE " +
                                       "AND TRACONFIGURATRAMITE.ICVEMODALIDAD = TRAREGREQXTRAM.ICVEMODALIDAD " +
                                       "where TRAREGREQXTRAM.IEJERCICIO = " +
                                       vData.getInt("iEjercicio") +
                                       " AND TRAREGREQXTRAM.INUMSOLICITUD = " +
                                       vData.getInt("iNumSolicitud") +
                                       " AND TRAREGREQXTRAM.ICVETRAMITE = " +
                                       vData.getInt("iCveTramite") +
                                       " AND TRAREGREQXTRAM.ICVEMODALIDAD = " +
                                       vData.getInt("iCveModalidad") +
                                       " AND TRAREGREQXTRAM.ICVEREQUISITO = " +
                                       cRequisito.get(ij));
      if(vcDataTemp.size() > 0){
        TVDinRep vNotificacionA;
        vNotificacionA = (TVDinRep) vcDataTemp.get(0);
        dtNotificacion = vNotificacionA.getDate("dtNotificacion");
        iNumDiasRes = vNotificacionA.getInt("iNumDiasRes");
        lDiasNaturales = vNotificacionA.getInt("iDiasNat");

        int iDif = 0;

        iDif = fecha.getDaysBetweenDates(dtNotificacion,dtHoy);
        java.sql.Date dtEstimada = dtHoy;
        if(lDiasNaturales == 1){
          dtEstimada = diaNoHabil.getFechaFinal(
              dtNotificacion,iNumDiasRes,true); //Hace el estimado de la fecha de entrega con naturales
        } else{
          if(iNumDiasRes > -1){
            dtEstimada = diaNoHabil.getFechaFinal(
                dtNotificacion,iNumDiasRes,false); //Hace el estimado de la fecha de entrega

          }
        }
        String strFHoy = fecha.getStringYear(dtHoy) +
            fecha.getStringMonth(dtHoy) + fecha.getStringDay(dtHoy);
        String strFEst = fecha.getStringYear(dtEstimada) +
            fecha.getStringMonth(dtEstimada) + fecha.getStringDay(dtEstimada);
        if(Integer.parseInt(strFHoy,10) <= Integer.parseInt(strFEst,10)){
          cReqValido.add(cRequisito.get(ij));
        } else{
          cReqCan.add(cRequisito.get(ij));
        }
        /**** Fin ELEL */


        //     cConjunto = vData.getString("cConjunto").split(",");
        for(int tm = 0;tm < cReqValido.size();tm++){

          String lSQL = "update TRARegReqXTram set  dtRecepcion=(CURRENT DATE),lValido=0 where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";

          lPStmt = conn.prepareStatement(lSQL);
          lPStmt.setInt(1,vData.getInt("iEjercicio"));
          lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
          lPStmt.setInt(5,Integer.parseInt(String.valueOf(cReqValido.get(tm))));
          lPStmt.setInt(4,vData.getInt("iCveModalidad"));
          lPStmt.setInt(3,vData.getInt("iCveTramite"));
          lPStmt.executeUpdate();
        }
        if(cnNested == null){
          conn.commit();
        }

        /*Update a la Tabla de TRARegSolicitud. Se va a actualizar dtEstimadaEntrega en base al dtRecepcion que acabamos de actualizar
         en la instruccion anterior.*/

        /*El siguiente query es para verificar si aun hay requisitos pendientes por entregar*/
        Vector vcData = findByCustom("",
                                     "SELECT count(iCveRequisito) as iCveRequisito " +
                                     "FROM TRAREGREQXTRAM " +
                                     "where iEjercicio = " +
                                     vData.getInt("iEjercicio") +
                                     " and INUMSOLICITUD = " +
                                     vData.getInt("iNumSolicitud") +
                                     " and DTRECEPCION is null");

        TVDinRep vCount = (TVDinRep) vcData.get(0);
        lbandera = vCount.getInt("iCveRequisito");
        if(lbandera == 0){
          /*El siguiente query es para obtener los datos con los cuales vamos a calcular la fecha de entrega*/
          Vector vcData1 = findByCustom("","select DTINIVIGENCIA, iNumDiasResol, lDiasNaturalesResol from TRACONFIGURATRAMITE " +
                                        "where icvetramite = " +
                                        vData.getInt("iCveTramite") +
                                        " and icvemodalidad = " +
                                        vData.getInt("iCveModalidad") +
                                        " order by DTINIVIGENCIA desc");

          TVDinRep vTRAConfiguraTra = (TVDinRep) vcData1.get(0);
          java.sql.Date dtInicio = vTRAConfiguraTra.getDate("dtIniVigencia"); //Es la fecha de Inicio la cual se ocupará para reliazar el cálculo para la fecha de Entrega
          iNumDias = vTRAConfiguraTra.getInt("iNumDiasResol");
          lDiasNaturales = vTRAConfiguraTra.getInt("lDiasNaturalesResol");

          if(lDiasNaturales == 1)
            lDias = true;
          else
            lDias = false;

            /*El siguiente query se realiza para obtener la primer fecha de notificacion en caso de que existan otras*/
          Vector vcData2 = findByCustom("","SELECT DTNOTIFICACION " +
                                        "FROM TRAREGREQXTRAM " +
                                        "where INUMSOLICITUD = " +
                                        vData.getInt("iNumSolicitud") +
                                        " order by DTNOTIFICACION asc ");

          TVDinRep vNotificacion = (TVDinRep) vcData2.get(0);

          dtNotificacion = vNotificacion.getDate("dtNotificacion");
          iDias = fecha.getDaysBetweenDates(dtInicio,dtNotificacion); //Hace el calculo de dias
          iDiasASumar = iNumDias - iDias; //Resta los dias que transcurrieron para saber cuales se van a sumar en el nuevo calculo

          /*El siguiente query se efectua para obtener la ultima fecha de recepcion*/
          Vector vcData3 = findByCustom("","SELECT DTRECEPCION " +
                                        "FROM TRAREGREQXTRAM " +
                                        "where INUMSOLICITUD = " +
                                        vData.getInt("iNumSolicitud") +
                                        " order by DTRECEPCION desc ");

          TVDinRep vRecepcion = (TVDinRep) vcData3.get(0);
          java.sql.Date dtRecepcion = vRecepcion.getDate("dtRecepcion");

          java.sql.Date dtEstimaEntrega = diaNoHabil.getFechaFinal(
              dtRecepcion,iDiasASumar,lDias); //Hace el estimado de la fecha de entrega

          /*Update ya con los datos que hemos obtenido*/
          String lSQL1 = "update TRARegSolicitud set dtEstimadaEntrega = ? where iEjercicio = ? AND iNumSolicitud = ? ";
          lPStmt1 = conn.prepareStatement(lSQL1);
          if(iNumDias > -1){
            lPStmt1.setDate(1,dtEstimada);
          } else
            lPStmt1.setNull(1,Types.DATE);
          lPStmt1.setInt(2,vData.getInt("iEjercicio"));
          lPStmt1.setInt(3,vData.getInt("iNumSolicitud"));

          if(cnNested == null){
            conn.commit();
          }
        }
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


  public TVDinRep Cambia(TVDinRep vData,Connection cnNested) throws
      Exception{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    PreparedStatement lPStmt1 = null;
    String[] cConjunto;

    String cErrorMsg = "";
    boolean lSuccess = true;
    TDGRLDiaNoHabil diaNoHabil = new TDGRLDiaNoHabil();
    TParametro  vParametros = new TParametro("44");
    TFechas fecha = new TFechas();
    java.sql.Date dtHoy = fecha.TodaySQL(); //ELEL Fecha actual
    int ja = 0;
    int lbandera = 0; // Para saber si existen aun requisitos sin entregar
    int iNumDias = 0;
    int iNumDiasRes = 0;
    int lDiasNaturales = 0;
    int iDias = 0;
    int iDiasASumar = 0;
    boolean lDias;
    int iDiasReEntrega = Integer.parseInt(vParametros.getPropEspecifica("cDiasRecReqNotificados"));

    Vector vDiasResolucion = this.findByCustom("","SELECT IDIASDESPUESNOTIF FROM TRACONFIGURATRAMITE where ICVETRAMITE="+vData.getInt("iCveTramite")+" and ICVEMODALIDAD="+vData.getInt("iCveModalidad"));
    if(vDiasResolucion.size()>0){
      iDiasReEntrega = ((TVDinRep) vDiasResolucion.get(0)).getInt("IDIASDESPUESNOTIF");
    }
    Vector cRequisito = new Vector();
    Vector cReqValido = new Vector();
    Vector cReqCan = new Vector();
    TFechas fechas = new TFechas();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      /**** 
       * ELEL Se busca si la fecha de Recepción a excedido la Estimada de Entrega 
       */
      /*El siguiente query se realiza para obtener la primer fecha de notificacion en caso de que existan otras*/
      cConjunto = vData.getString("cConjunto").split(",");

      for(int ij = 0;ij < cConjunto.length;ij++){
        cRequisito.add(cConjunto[ij]);
      }
      for(int ij = 0;ij < cRequisito.size();ij++){
        java.sql.Date dtNotificacion;
        Vector vcDataTemp = new Vector();
        if(Integer.parseInt(""+cRequisito.get(ij),10)>0){
          vcDataTemp = findByCustom("", "SELECT " +
                                        "COALESCE (TRAREGREQXTRAM.DTNOTIFICACION,rp.DTNOTIFICACION) as dtNotificacion, " +
                                        "TRACONFIGURATRAMITE.INUMDIASRESOL as iNumDiasRes, " +
                                        "TRACONFIGURATRAMITE.LDIASNATURALESRESOL as iDiasNat," +
                                        "S.LTRAMINERNET " +
                                        "FROM TRAREGSOLICITUD S "+
                                        "JOIN TRAREGREQXTRAM ON S.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO AND S.INUMSOLICITUD=TRAREGREQXTRAM.INUMSOLICITUD " +
                                        "JOIN TRACONFIGURATRAMITE ON TRACONFIGURATRAMITE.ICVETRAMITE = TRAREGREQXTRAM.ICVETRAMITE " +
                                        "AND TRACONFIGURATRAMITE.ICVEMODALIDAD = TRAREGREQXTRAM.ICVEMODALIDAD " +
                                        "  LEFT JOIN TRAREGPNCETAPA PE ON PE.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO AND PE.INUMSOLICITUD=TRAREGREQXTRAM.INUMSOLICITUD " +
                                        "  LEFT JOIN GRLREGISTROPNC RP ON RP.IEJERCICIO=PE.IEJERCICIOPNC AND RP.ICONSECUTIVOPNC=PE.ICONSECUTIVOPNC " +
                                        "where TRAREGREQXTRAM.IEJERCICIO = " + vData.getInt("iEjercicio") +
                                        " AND TRAREGREQXTRAM.INUMSOLICITUD = " + vData.getInt("iNumSolicitud") +
                                        " AND TRAREGREQXTRAM.ICVETRAMITE = " + vData.getInt("iCveTramite") +
                                        " AND TRAREGREQXTRAM.ICVEMODALIDAD = " + vData.getInt("iCveModalidad") +
                                        " AND TRAREGREQXTRAM.ICVEREQUISITO = " + cRequisito.get(ij));
        }
        if(vcDataTemp.size() > 0){
          TVDinRep vNotificacionA;
          vNotificacionA = (TVDinRep) vcDataTemp.get(0);
          dtNotificacion = vNotificacionA.getDate("dtNotificacion");
          iNumDiasRes = vNotificacionA.getInt("iNumDiasRes");
          lDiasNaturales = vNotificacionA.getInt("iDiasNat");
          boolean lInternet = vNotificacionA.getInt("LTRAMINERNET")==1;
          int iDif = 0;

          iDif = fecha.getDaysBetweenDates(dtNotificacion,dtHoy);
          java.sql.Date dtEstimada = dtHoy;

          
          String strFEst = "";         
          
          
          /**
           * En caso de que el trámite sea por internet se calcula la fecha apartir del 15 o el primero 
           * del mes siguiente de la publicacion de la notificación, en caso contrario se toma apartir 
           * de la fecha de notificación
           */
          if(lInternet==true){
        	  int iLimite = 16;
        	  //el año de la fecha se toma apartir de 1900
        	  //el mes se toma a enero como el mes 0
        	  java.sql.Date dtTest = new Date(112,11,27);
        	  String dtNotif = "";
        	  if(dtTest.getDate() < iLimite){
        		  strFEst = fecha.getStringYear(dtTest) + fecha.getStringMonth(dtTest) +(iNumDiasRes+15);
        	  }
        	  else{
        		  int itemp = iNumDiasRes+1;
        		  String cTemp = itemp<10?"0"+itemp:""+itemp;
        		  if((dtTest.getMonth()+1)==12){
        			  strFEst = (Integer.parseInt(fecha.getStringYear(dtTest))+1)+"01"+cTemp;
        		  }
        		  else{
        			  strFEst = fecha.getStringYear(dtTest) + (dtTest.getMonth()+2) +cTemp;
        		  }
        	  }
          }
          else{
              dtEstimada = diaNoHabil.getFechaFinal(dtNotificacion,iDiasReEntrega,false); //Hace el estimado de la fecha de entrega
          }
          String strFHoy = fecha.getStringYear(dtHoy) +
          fecha.getStringMonth(dtHoy) + fecha.getStringDay(dtHoy);
          if(strFEst=="")
        	  strFEst = fecha.getStringYear(dtEstimada) + fecha.getStringMonth(dtEstimada) + fecha.getStringDay(dtEstimada);
          if(Integer.parseInt(strFHoy,10) <= Integer.parseInt(strFEst,10)){
            cReqValido.add(cRequisito.get(ij));
          } else{
            cReqCan.add(cRequisito.get(ij));
          }
          
          if(cReqCan.size()>0){
        	  cErrorMsg = "El tiempo para la recepción del requisito ha caducado.";
        	  throw new Exception("El tiempo para la recepción del requisito ha caducado.");
          }
        	  
          /**** Fin ELEL */

          /***
           * El siguiente query se realiza para obtener la ultima fecha de notificacion 
           * en caso de que existan otras
           */
        Vector vcData2 = findByCustom("","SELECT COALESCE (r.dtNotificacion,rp.DTNOTIFICACION) as DTNOTIFICACION FROM TRAREGSOLICITUD s " +
        		                         "left join TRAREGREQXTRAM r on r.IEJERCICIO=s.IEJERCICIO and r.INUMSOLICITUD=s.INUMSOLICITUD " +
        		                         "left join TRAREGPNCETAPA pe on pe.IEJERCICIO = s.IEJERCICIO and pe.INUMSOLICITUD = s.INUMSOLICITUD " +
        		                         "left join GRLREGISTROPNC rp on rp.IEJERCICIO=pe.IEJERCICIOPNC and rp.ICONSECUTIVOPNC=pe.ICONSECUTIVOPNC " +
                                         "where s.INUMSOLICITUD = " + vData.getInt("iNumSolicitud") +
                                         " and s.iEjercicio = " + vData.getInt("iEjercicio") +
                                         " order by DTNOTIFICACION ");
        TVDinRep vNotificacion = (TVDinRep) vcData2.get(0);

          //     cConjunto = vData.getString("cConjunto").split(",");
          for(int tm = 0;tm < cReqValido.size();tm++){

            String lSQL = "update TRARegReqXTram set  dtRecepcion=(CURRENT DATE), lValido=0, lRecNotificado=1, iCveUsuRecibe = ? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";

            lPStmt = conn.prepareStatement(lSQL);
            lPStmt.setInt(1,vData.getInt("iCveUsuario"));
            lPStmt.setInt(2,vData.getInt("iEjercicio"));
            lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
            lPStmt.setInt(4,vData.getInt("iCveTramite"));
            lPStmt.setInt(5,vData.getInt("iCveModalidad"));
            lPStmt.setInt(6,Integer.parseInt(String.valueOf(cReqValido.get(tm))));

            lPStmt.executeUpdate();
          }
          if(cnNested == null){
            conn.commit();
          }


          /*El siguiente query es para verificar si aun hay requisitos pendientes por entregar*/
          Vector vcData = findByCustom("",
                                       "SELECT " +
                                       "   count(REG.iCveRequisito) as iCveRequisito " +
                                       "FROM TRAREGREQXTRAM REG " +
                                       "JOIN TRAREQXMODTRAMITE REQ ON REQ.ICVETRAMITE=REG.ICVETRAMITE " +
                                       "    AND REQ.ICVEMODALIDAD=REG.ICVEMODALIDAD " +
                                       "    AND REQ.ICVEREQUISITO=REG.ICVEREQUISITO " +
                                       "  WHERE REG.IEJERCICIO = " +vData.getInt("iEjercicio")+
                                       "    AND REG.INUMSOLICITUD = " +vData.getInt("iNumSolicitud") +
                                       "    AND REG.DTRECEPCION IS NULL " +
                                       "    AND REG.DTNOTIFICACION is not null");
          TVDinRep vCount = (TVDinRep) vcData.get(0);
          lbandera = vCount.getInt("iCveRequisito");
          
          
          if(lbandera == 0){
            /*El siguiente query es para obtener los datos con los cuales vamos a calcular la fecha de entrega*/
            Vector vcData1 = findByCustom("","select DTINIVIGENCIA, iNumDiasResol, lDiasNaturalesResol from TRACONFIGURATRAMITE " +
                                          "where icvetramite = " +
                                          vData.getInt("iCveTramite") +
                                          " and icvemodalidad = " +
                                          vData.getInt("iCveModalidad") +
                                          " order by DTINIVIGENCIA desc");

            TVDinRep vTRAConfiguraTra = (TVDinRep) vcData1.get(0);

            Vector vcRegistro = findByCustom("","SELECT TSREGISTRO " +
                                                "FROM TRAREGSOLICITUD S " +
                                                "WHERE S.IEJERCICIO = "+vData.getInt("iEjercicio")+
                                                " AND S.INUMSOLICITUD = "+vData.getInt("iNumSolicitud"));
            java.sql.Date dtInicioSol = fecha.getDateSQL(((TVDinRep)vcRegistro.get(0)).getTimeStamp("TSREGISTRO"));
            java.sql.Date dtInicio = vTRAConfiguraTra.getDate("dtIniVigencia"); //Es la fecha de Inicio la cual se ocupará para reliazar el cálculo para la fecha de Entrega
            iNumDias = vTRAConfiguraTra.getInt("iNumDiasResol");
            lDiasNaturales = vTRAConfiguraTra.getInt("lDiasNaturalesResol");

            if(lDiasNaturales == 1)
              lDias = true;
            else
              lDias = false;
            dtNotificacion = vNotificacion.getDate("dtNotificacion");
            iDias = fecha.getDaysBetweenDates(dtInicioSol,dtNotificacion); //Hace el calculo de dias
            iDiasASumar = iNumDias - iDias; //Resta los dias que transcurrieron para saber cuales se van a sumar en el nuevo calculo

            /*El siguiente query se efectua para obtener la ultima fecha de recepcion*/
            Vector vcData3 = findByCustom("","SELECT DTRECEPCION " +
                                          "FROM TRAREGREQXTRAM " +
                                          "where INUMSOLICITUD = " +
                                          vData.getInt("iNumSolicitud") +
                                          " AND IEJERCICIO = "+vData.getInt("iEjercicio")+
                                          " AND DTRECEPCION IS NOT NULL "+
                                          " order by DTRECEPCION desc ");

            TVDinRep vRecepcion = (TVDinRep) vcData3.get(0);
            java.sql.Date dtRecepcion = vRecepcion.getDate("dtRecepcion");

            java.sql.Date dtEstimaEntrega = diaNoHabil.getFechaFinal(
                dtRecepcion,iDiasASumar,lDias); //Hace el estimado de la fecha de entrega

            /*Update ya con los datos que hemos obtenido*/
            String lSQL1 = "update TRARegSolicitud set dtEstimadaEntrega = ? where iEjercicio = ? AND iNumSolicitud = ? ";
            lPStmt1 = conn.prepareStatement(lSQL1);
            if(iNumDias > -1)
              lPStmt1.setDate(1,dtEstimaEntrega);
            else
              lPStmt1.setNull(1,Types.DATE);
            lPStmt1.setInt(2,vData.getInt("iEjercicio"));
            lPStmt1.setInt(3,vData.getInt("iNumSolicitud"));
            lPStmt1.executeUpdate();
            conn.commit();
            lPStmt1.close();

            Vector vcOficDep = this.findByCustom(""," SELECT iCveOficina,iCveDepartamento FROM TRAREGETAPASXMODTRAM "+
                                                    " where iejercicio = "+vData.getString("iEjercicio")+
                                                    " and inumsolicitud = "+vData.getString("iNumSolicitud"));
            TVDinRep vOficDep = new TVDinRep();
            if(vcOficDep.size()>0){
              vOficDep = (TVDinRep) vcOficDep.get(0);
            }


            TVDinRep vCambiaEtapa = new TVDinRep();
            

            vCambiaEtapa.put("lResolucion",1);
            vCambiaEtapa.put("iEjercicio",vData.getString("iEjercicio"));
            vCambiaEtapa.put("iNumSolicitud",vData.getString("iNumSolicitud"));
            vCambiaEtapa.put("iCveTramite",vData.getString("iCveTramite"));
            vCambiaEtapa.put("iCveModalidad",vData.getString("iCveModalidad"));
            vCambiaEtapa.put("iCveEtapa",Integer.parseInt(VParametros.getPropEspecifica("EtapaModificarTramite")));
            vCambiaEtapa.put("iCveOficina",vOficDep.getInt("iCveOficina"));
            vCambiaEtapa.put("iCveDepartamento",vOficDep.getInt("iCveDepartamento"));
            vCambiaEtapa.put("iCveUsuario",vData.getInt("iCveUsuario"));
            vCambiaEtapa.put("tsRegistro",fechas.getThisTime());
            vCambiaEtapa.put("lAnexo",1);
            try{

            	TDTRARegEtapasXModTram etapa = new TDTRARegEtapasXModTram();
            	etapa.cambiarEtapa(vCambiaEtapa, false, "", false, conn);
            }catch(Exception ex){
              System.out.println("\n\n>>>  "+ex.getMessage()+"\n\n");
              cErrorMsg = ex.getMessage();
              ex.printStackTrace();
              throw new Exception(ex.getMessage());
            }

            if(cnNested == null){
              conn.commit();
            }
          }
        }
      }
    } catch(Exception ex){
      warn("update",ex);
      ex.printStackTrace();
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          e.printStackTrace();
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
        throw new DAOException(cErrorMsg);

      return vData;
    }
  }

  /*
      Datos requeridos que debe de tener vData para hacer esta actualizacion:
      lResolucion,iEjercicio,iNumSolicitud,iEjercicio,iNumSolicitud,iCveTramite,
      iCveModalidad,iCveEtapa,iOrden,iCveOficina,iCveDepartamento,iCveUsuario,
      tsRegistro,lAnexo

   */
  public void cambiarEtapa(TVDinRep vData,boolean lResolucionPositiva, String cObservacion) throws
    DAOException{
  TFechas dFecha = new TFechas();
  boolean lSuccess = true;
  try{
    vData.put("tsRegistro",dFecha.getThisTime());
    this.insertEtapa(vData,null);
    if(lResolucionPositiva){
      TVDinRep vDataResPos = new TVDinRep();
      TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
      vDataResPos.put("lResolucion",   vData.getInt("lResolucion"));
      vDataResPos.put("iEjercicio",    vData.getInt("iEjercicio"));
      vDataResPos.put("iNumSolicitud", vData.getInt("iNumSolicitud"));
      dSolicitud.updEtapas(vDataResPos,null);
    }
  } catch(Exception ex){
    cMensaje = ex.getMessage();
    lSuccess = false;
  }
  if(!lSuccess)
      throw new DAOException(cMensaje);
}
  

  public void cambiarEtapa(TVDinRep vData,boolean lResolucionPositiva, String cObservacion, Connection conn) throws
    DAOException{
  TFechas dFecha = new TFechas();
  boolean lSuccess = true;
  try{
    vData.put("tsRegistro",dFecha.getThisTime());
    this.insertEtapa(vData,conn);
    if(lResolucionPositiva){
      TVDinRep vDataResPos = new TVDinRep();
      TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
      vDataResPos.put("lResolucion",vData.getInt("lResolucion"));
      vDataResPos.put("iEjercicio",vData.getInt("iEjercicio"));
      vDataResPos.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
      dSolicitud.updEtapas(vDataResPos,conn);
    }
  } catch(Exception ex){
    cMensaje = ex.getMessage();
    lSuccess = false;
  }
  if(!lSuccess)
      throw new DAOException(cMensaje);
}
  public TVDinRep insertEtapa(TVDinRep vData,Connection cnNested) throws
    DAOException{
  DbConnection dbConn = null;
  Connection conn = cnNested;
  PreparedStatement lPStmt1 = null;
  boolean lSuccess = true;
  TFechas fechas = new TFechas();
  try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }

    //AGREGAR AL ULTIMO ...
    Vector vcData = findByCustom("",
                                 " select MAX(iOrden) AS iOrden " +
                                 "        from TRARegEtapasXModTram " +
                                 " where iEjercicio = " + vData.getInt("iEjercicio") +
                                 "   and iNumSolicitud = " + vData.getInt("iNumSolicitud") +
                                 "   and iCveTramite = " + vData.getInt("iCveTramite") +
                                 " and iCveModalidad = " + vData.getInt("iCveModalidad"));
    if(vcData.size() > 0){
       TVDinRep vUltimo = (TVDinRep) vcData.get(0);
       vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
    }else
       vData.put("iOrden",1);

            String lSQL =
                " insert into TRARegEtapasXModTram  ( " +
                " iEjercicio, " +
                " iNumSolicitud, " +
                " iCveTramite, " +
                " iCveModalidad, " +
                " iCveEtapa, " +
                " iOrden, " +
                " iCveOficina, " +
                " iCveDepartamento, " +
                " iCveUsuario, " +
                " tsRegistro, " +
                " lAnexo) " +
                " values ("+vData.getInt("iEjercicio")+","+
                            vData.getInt("iNumSolicitud")+","+
                            vData.getInt("iCveTramite")+","+
                            vData.getInt("iCveModalidad")+","+
                            vData.getInt("iCveEtapa")+","+
                            vData.getInt("iOrden")+","+
                            vData.getInt("iCveOficina")+","+
                            vData.getInt("iCveDepartamento")+","+
                            vData.getInt("iCveUsuario")+",'"+
                            fechas.getThisTime()+"',"+
                            vData.getInt("lAnexo")+")";

    lPStmt1 = conn.prepareStatement(lSQL);
    lPStmt1.executeUpdate();
    conn.commit();
    lPStmt1.close();
  } catch(SQLException se){
    se.printStackTrace();
    cMensaje = super.getSQLMessages(se);
    if(cnNested == null){
      try{
        conn.rollback();
      } catch(Exception e){
        fatal("insert.rollback",e);
      }
    }
    lSuccess = false;
  } catch(Exception ex){
    ex.printStackTrace();
    cMensaje = "";
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
      if(lPStmt1 != null)
        lPStmt1.close();
      if(cnNested == null){
        if(conn != null)
          conn.close();
        dbConn.closeConnection();
      }
    } catch(Exception ex2){
      warn("insert.close",ex2);
    }
    if(lSuccess == false)
      throw new DAOException(cMensaje);
    return vData;
  }
 }
  public TVDinRep updtReqPNC(TVDinRep vData,Connection cnNested) throws
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
	
	  String lSQL = "update TRAREGREQXTRAM set LTIENEPNC=0 where iejercicio=? and INUMSOLICITUD=?";
	
	
	  lPStmt = conn.prepareStatement(lSQL);
	  
	  lPStmt.setInt(1,vData.getInt("iEjercicio"));
	  lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
	  
	  
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

	public void FinalizaPNC(TVDinRep vData) throws
	  DAOException{
		TFechas dFecha = new TFechas();
		boolean lSuccess = true;
		try{
		  vData.put("tsRegistro",dFecha.getThisTime());
		    TVDinRep vSolPNC = new TVDinRep();
		    TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
		    TDGRLRegistroPNC pnc = new TDGRLRegistroPNC();
		    vSolPNC.put("lResuelto",1);
		    vSolPNC.put("dtFechaRes",dFecha.getDateSQL(dFecha.getThisTime()));
		    vSolPNC.put("iEjercicio",vData.getInt("iEjercicio"));
		    vSolPNC.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
		    vSolPNC.put("iConsecutivoPNC", vData.getInt("iConsecutivoPNC"));
		    pnc.update2(vSolPNC, conn);
		    this.updtReqPNC(vSolPNC,conn);
		    
		    dSolicitud.updateFechaCompromiso(vData.getInt("iEjercicio"), vData.getInt("iNumSolicitud"), conn);
		    
		} catch(Exception ex){
		  cMensaje = ex.getMessage();
		  lSuccess = false;
		}
		if(!lSuccess)
		    throw new DAOException(cMensaje);
	}

}

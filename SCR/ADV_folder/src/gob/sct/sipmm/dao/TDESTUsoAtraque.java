package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDESTUsoAtraque.java</p>
 * <p>Description: DAO de la entidad ESTUsoAtraque</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Ing. Arturo Lopez Peña
 * @version 1.0
 */
public class TDESTUsoAtraque extends DAOBase{
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
 public Vector findByCustom1(String cKey,String cWhere) throws DAOException{
   Vector vcRecords = new Vector();
   Vector vcRecords2 = new Vector();

   cError = "";
   try{
     String cSQL = cWhere;
     System.out.print("*****  ");
     System.out.print("*****  ");
     System.out.print("*****  \n"+cSQL);
     vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
     if(vcRecords.size() > 0){
       for(int i=0; i<vcRecords.size();i++){
         TVDinRep vUsoAtraque = (TVDinRep) vcRecords.get(i);
         String cSql =
             "SELECT TC.CDSCTIPOCARGA || ' ' || STC.CDESCRIP_100 AS CTIPOSCARGA FROM ESTOPNCAPACIDAD OC " +
             "JOIN VEHTIPOCARGA TC ON OC.ICVETIPOCARGA = TC.ICVETIPOCARGA " +
             "JOIN VEHSUBTIPOCARGA STC ON OC.INUMSUBTIPOCARGA = STC.INUMSUBTIPOCARGA AND OC.ICVETIPOCARGA = STC.ICVETIPOCARGA " +
             "WHERE OC.IEJERCICIOSEGUIMIENTO = " + vUsoAtraque.getInt("IEJERCICIOSEGUIMIENTO") +
             "  AND OC.INUMMOVTOSEGTO        = " + vUsoAtraque.getInt("INUMMOVTOSEGTO") +
             "  AND OC.IEJERCICIOPOA         = " + vUsoAtraque.getInt("IEJERCICIOPOA") +
             "  AND OC.ICVETITULO            = " + vUsoAtraque.getInt("ICVETITULO") +
             "  AND OC.ICVETITULAR           = " + vUsoAtraque.getInt("ICVETITULAR") +
             "  AND OC.ICVEBALANCEDSC        = " + vUsoAtraque.getInt("ICVEBALANCEDSC") +
             "  AND OC.INUMINDICADORINS      = " + vUsoAtraque.getInt("INUMINDICADORINS") +
             "  AND OC.INUMCAPACIDADATRAQUE  = " + vUsoAtraque.getInt("INUMCAPACIDADATRAQUE") ;
         Vector vcCapacidad = this.FindByGeneric("IEJERCICIOSEGUIMIENTO,INUMMOVTOSEGTO,IEJERCICIOPOA,ICVETITULO,ICVETITULAR,ICVEBALANCEDSC,INUMINDICADORINS,INUMCAPACIDADATRAQUE,",cSql,dataSourceName);
         String cTiposCarga = "";
         for(int j=0;j<vcCapacidad.size();j++){
           TVDinRep vTipoCarga = (TVDinRep) vcCapacidad.get(j);
           if(cTiposCarga=="")cTiposCarga += vTipoCarga.getString("CTIPOSCARGA");
           else cTiposCarga += ", <br>"+vTipoCarga.getString("CTIPOSCARGA");
         }
         vUsoAtraque.put("cTipoCarga",cTiposCarga);
         vcRecords2.addElement(vUsoAtraque);
       }
     }

   } catch(Exception e){
     cError = e.toString();
   } finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vcRecords2;
   }
}

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into ESTUsoAtraque(iEjercicioSeguimiento,iNumMovtoSegto,iEjercicioPOA,iCveTitulo,iCveTitular,iCveBalancedSC,iNumIndicadorIns,iNumCapacidadAtraque,dToneladasOperadas,dCapacidadUtilizada) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iEjercicioPOA,iCveTitulo,iCveTitular,iCveBalancedSC,iNumIndicadorIns,iNumCapacidadAtraque, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;

    PreparedStatement lPStmt1 = null;
    PreparedStatement lPStmt3 = null;
    PreparedStatement lPStmt4 = null;
    PreparedStatement lPStmt5 = null;

    ResultSet lResultado1 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      }

      TFechas tfFechas = new TFechas("44");

      java.sql.Date dtPrimerDia = null;
      java.sql.Date dtUltimoDia = null;

      String cFechaZarpeRealInicio = new String("");
      String cFechaZarpeRealFin    = new String("");

      double dTotalCapacidadInstalada = 0, dTotalToneladasOperadas = 0;

      Integer iEjercicioReportado = new Integer(vData.getInt("iEjercicioReportado"));
      Integer iPeriodoReportado   = new Integer(vData.getInt("iPeriodoReportado"));
      Integer iPrimerDia          = new Integer(1);

      dtPrimerDia = tfFechas.getDateSQL(iPrimerDia,iPeriodoReportado,iEjercicioReportado);

      cFechaZarpeRealInicio = tfFechas.getFechaYYYYMMDD(dtPrimerDia,"-");

      if(vData.getInt("iPeriodoReportado") == 12)
        dtUltimoDia = tfFechas.getDateSQL(iPrimerDia,new Integer(1),new Integer(iEjercicioReportado.intValue() + 1));
      else
        dtUltimoDia = tfFechas.getDateSQL(iPrimerDia,new Integer(iPeriodoReportado.intValue() + 1),iEjercicioReportado);

      dtUltimoDia = tfFechas.aumentaDisminuyeDias(dtUltimoDia,-1);
      cFechaZarpeRealFin = tfFechas.getFechaYYYYMMDD(dtUltimoDia,"-");

      StringBuffer sbSQL1 = new StringBuffer();

      sbSQL1.append("SELECT CI.IEJERCICIOPOA, CI.ICVETITULO, CI.ICVETITULAR, CI.ICVEBALANCEDSC, CI.INUMINDICADORINS, ");
      sbSQL1.append("       CI.INUMCAPACIDADATRAQUE, CI.IEJERCICIO, CI.ICVEPUERTO, CI.ICONSECUTIVOOA, CI.INUMPOSICIONATRAQUE, ");
      sbSQL1.append("       CI.DCAPACIDADINSTALADA, SUM(CD.DTONELADASMETRICAS) AS dOneladasOperadas, CD.ICVETIPOCARGA, CD.INUMSUBTIPOCARGA ");
      sbSQL1.append("FROM NAVARRIBOS NA ");
      sbSQL1.append("JOIN ESTOPERACION EO ON EO.IEJERCICIO = NA.IEJERCICIO ");
      sbSQL1.append("                    AND EO.INUMARRIBO = NA.INUMARRIBO ");
      sbSQL1.append("JOIN ESTCAPACINSTALADA CI ON CI.IEJERCICIOPOA       = " + vData.getInt("iEjercicioPOA"));
      sbSQL1.append("                         AND CI.ICVETITULO          = " + vData.getInt("iCveTitulo"));
      sbSQL1.append("                         AND CI.ICVETITULAR         = " + vData.getInt("iCveTitular"));
      sbSQL1.append("                         AND CI.IEJERCICIO          = EO.IEJERCICIOCP ");
      sbSQL1.append("                         AND CI.ICVEPUERTO          = EO.ICVEPUERTO ");
      sbSQL1.append("                         AND CI.ICONSECUTIVOOA      = EO.ICONSECUTIVOOA ");
      sbSQL1.append("                         AND CI.INUMPOSICIONATRAQUE = EO.INUMPOSICIONATRAQUE ");
      sbSQL1.append("JOIN ESTTIPOCARGAOPERACION CO ON CO.IEJERCICIO     = EO.IEJERCICIO ");
      sbSQL1.append("                             AND CO.INUMARRIBO     = EO.INUMARRIBO ");
      sbSQL1.append("                             AND CO.INUMMOVIMIENTO = EO.INUMMOVIMIENTO ");
      sbSQL1.append("JOIN ESTCARGADET CD ON CD.IEJERCICIO        = CO.IEJERCICIO ");
      sbSQL1.append("                   AND CD.INUMARRIBO        = CO.INUMARRIBO ");
      sbSQL1.append("                   AND CD.INUMMOVIMIENTO    = CO.INUMMOVIMIENTO ");
      sbSQL1.append("                   AND CD.ICVETIPOCARGA     = CO.ICVETIPOCARGA ");
      sbSQL1.append("                   AND CD.INUMSUBTIPOCARGA  = CO.INUMSUBTIPOCARGA ");
      sbSQL1.append("                   AND CD.ICVESISTOPERACION = CO.ICVESISTOPERACION ");
      //sbSQL1.append("WHERE NA.ICVETITULO = " + vData.getString("iCveTitulo"));
      //sbSQL1.append("  AND NA.TSZARPEREAL BETWEEN " +"'"+ cFechaZarpeRealInicio +" 00:00:00' "+" AND "+"'"+cFechaZarpeRealFin+" 23:59:59' ");
      sbSQL1.append("GROUP BY CI.IEJERCICIOPOA, CI.ICVETITULO, CI.ICVETITULAR, CI.ICVEBALANCEDSC, CI.INUMINDICADORINS, CI.INUMCAPACIDADATRAQUE, ");
      sbSQL1.append("CI.IEJERCICIO, CI.ICVEPUERTO, CI.ICONSECUTIVOOA, CI.INUMPOSICIONATRAQUE, CI.DCAPACIDADINSTALADA, CD.ICVETIPOCARGA, CD.INUMSUBTIPOCARGA ");

      lPStmt1 = conn.prepareStatement(sbSQL1.toString());
      lResultado1 = lPStmt1.executeQuery();

      while (lResultado1.next())
      {
        StringBuffer sbSQL2 = new StringBuffer();

        sbSQL2.append("SELECT COUNT(UA.IEJERCICIOSEGUIMIENTO) AS iCuentaRegistros FROM ESTUSOATRAQUE UA WHERE ");
        sbSQL2.append("UA.IEJERCICIOSEGUIMIENTO = " + vData.getInt("iEjercicioSeguimiento"));
        sbSQL2.append(" AND UA.INUMMOVTOSEGTO = " + vData.getInt("iNumMovtoSegto"));
        sbSQL2.append(" AND UA.IEJERCICIOPOA = " + vData.getInt("iEjercicioPOA"));
        sbSQL2.append(" AND UA.ICVETITULO = " + vData.getInt("iCveTitulo"));
        sbSQL2.append(" AND UA.ICVETITULAR = " + vData.getInt("iCveTitular"));
        sbSQL2.append(" AND UA.ICVEBALANCEDSC = " + lResultado1.getInt("iCveBalancedSC"));
        sbSQL2.append(" AND UA.INUMINDICADORINS = " + lResultado1.getInt("iNumIndicadorIns"));
        sbSQL2.append(" AND UA.INUMCAPACIDADATRAQUE = " + lResultado1.getInt("INUMCAPACIDADATRAQUE"));

        TDESTSegtoFormulario SegtoFormulario = new TDESTSegtoFormulario();
        Vector vcData = SegtoFormulario.findByGenericLocal("",sbSQL2.toString(),dataSourceName,conn);
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);

        if(vUltimo.getInt("iCuentaRegistros")==0){ // No existió un registro con la llave en la Tabla ESTUSOATRAQUE, se genera uno
          String lSQL3 =
           "INSERT INTO ESTUsoAtraque(iEjercicioSeguimiento,iNumMovtoSegto,iEjercicioPOA,iCveTitulo,iCveTitular,iCveBalancedSC,iNumIndicadorIns,iNumCapacidadAtraque,dToneladasOperadas,dCapacidadUtilizada) values (?,?,?,?,?,?,?,?,?,?)";

          lPStmt3 = conn.prepareStatement(lSQL3);
          lPStmt3.setInt(1,vData.getInt("iEjercicioSeguimiento"));
          lPStmt3.setInt(2,vData.getInt("iNumMovtoSegto"));
          lPStmt3.setInt(3,vData.getInt("iEjercicioPOA"));
          lPStmt3.setInt(4,vData.getInt("iCveTitulo"));
          lPStmt3.setInt(5,vData.getInt("iCveTitular"));
          lPStmt3.setInt(6,lResultado1.getInt("iCveBalancedSC"));
          lPStmt3.setInt(7,lResultado1.getInt("iNumIndicadorIns"));
          lPStmt3.setInt(8,lResultado1.getInt("iNumCapacidadAtraque"));
          lPStmt3.setDouble(9,lResultado1.getDouble("dOneladasOperadas"));
          lPStmt3.setFloat(10,(float)(lResultado1.getDouble("dOneladasOperadas") / lResultado1.getDouble("DCAPACIDADINSTALADA") * 100));
          lPStmt3.executeUpdate();
          lPStmt3.close();

          dTotalCapacidadInstalada += lResultado1.getDouble("DCAPACIDADINSTALADA");
          dTotalToneladasOperadas  += lResultado1.getDouble("dOneladasOperadas");

        } // if(vUltimo.getInt("iCuentaRegistros")==0){

        // Se generan registros también en la tabla ESTOpnCapacidad
        String lSQL4 =
         "INSERT INTO ESTOpnCapacidad(iEjercicioSeguimiento,iNumMovtoSegto,iEjercicioPOA,iCveTitulo,iCveTitular,iCveBalancedSC,iNumIndicadorIns,iNumCapacidadAtraque,iCveTipoCarga,iNumSubtipoCarga) values (?,?,?,?,?,?,?,?,?,?)";

        lPStmt4 = conn.prepareStatement(lSQL4);
        lPStmt4.setInt(1,vData.getInt("iEjercicioSeguimiento"));
        lPStmt4.setInt(2,vData.getInt("iNumMovtoSegto"));
        lPStmt4.setInt(3,vData.getInt("iEjercicioPOA"));
        lPStmt4.setInt(4,vData.getInt("iCveTitulo"));
        lPStmt4.setInt(5,vData.getInt("iCveTitular"));
        lPStmt4.setInt(6,lResultado1.getInt("iCveBalancedSC"));
        lPStmt4.setInt(7,lResultado1.getInt("iNumIndicadorIns"));
        lPStmt4.setInt(8,lResultado1.getInt("iNumCapacidadAtraque"));
        lPStmt4.setInt(9,lResultado1.getInt("ICVETIPOCARGA"));
        lPStmt4.setInt(10,lResultado1.getInt("INUMSUBTIPOCARGA"));
        lPStmt4.executeUpdate();
        lPStmt4.close();

      } // while (lResultado1.next())

      // Se genera un registro de los totales en la tabla de ESTCapacUtilTotal
      String lSQL5 =
       "INSERT INTO ESTCAPACUTILTOTAL(IEJERCICIOSEGUIMIENTO,INUMMOVTOSEGTO,DTOTALCAPACIDADINSTALADA,DTOTALTONELADASOPERADAS,DTOTALCAPACIDADUTILIZADA) values (?,?,?,?,?)";

      lPStmt5 = conn.prepareStatement(lSQL5);
      lPStmt5.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt5.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt5.setDouble(3,dTotalCapacidadInstalada);
      lPStmt5.setDouble(4,dTotalToneladasOperadas);
      lPStmt5.setFloat(5,(float)(dTotalToneladasOperadas / dTotalCapacidadInstalada * 100));
      lPStmt5.executeUpdate();
      lPStmt5.close();

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
        if(lPStmt1 != null){
          lPStmt1.close();
        }
        if(lPStmt3 != null){
          lPStmt3.close();
        }
        if(lPStmt4 != null){
          lPStmt4.close();
        }
        if(lPStmt5 != null){
          lPStmt5.close();
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
   * <p><b> delete from ESTUsoAtraque where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iEjercicioPOA = ? AND iCveTitulo = ? AND iCveTitular = ? AND iCveBalancedSC = ? AND iNumIndicadorIns = ? AND iNumCapacidadAtraque = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iEjercicioPOA,iCveTitulo,iCveTitular,iCveBalancedSC,iNumIndicadorIns,iNumCapacidadAtraque, </b></p>
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
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      }

      //Ajustar Where de acuerdo a requerimientos...
      String lSQLOpnCapac = "delete from ESTOpnCapacidad where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? ";
      lPStmt = conn.prepareStatement(lSQLOpnCapac);
      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.executeUpdate();
      lPStmt.close();

      String lSQLUsoAtraque = "delete from ESTUsoAtraque where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? ";
      lPStmt = conn.prepareStatement(lSQLUsoAtraque);
      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.executeUpdate();
      lPStmt.close();

      String lSQLCapacTotal = "delete from ESTCapacUtilTotal where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? ";
      lPStmt = conn.prepareStatement(lSQLCapacTotal);
      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.executeUpdate();
      lPStmt.close();

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
   * <p><b> update ESTUsoAtraque set dToneladasOperadas=?, dCapacidadUtilizada=? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iEjercicioPOA = ? AND iCveTitulo = ? AND iCveTitular = ? AND iCveBalancedSC = ? AND iNumIndicadorIns = ? AND iNumCapacidadAtraque = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iEjercicioPOA,iCveTitulo,iCveTitular,iCveBalancedSC,iNumIndicadorIns,iNumCapacidadAtraque, </b></p>
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
      String lSQL = "update ESTUsoAtraque set dToneladasOperadas=?, dCapacidadUtilizada=? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iEjercicioPOA = ? AND iCveTitulo = ? AND iCveTitular = ? AND iCveBalancedSC = ? AND iNumIndicadorIns = ? AND iNumCapacidadAtraque = ? ";

      vData.addPK(vData.getString("iEjercicioSeguimiento"));
      vData.addPK(vData.getString("iNumMovtoSegto"));
      vData.addPK(vData.getString("iEjercicioPOA"));
      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iCveTitular"));
      vData.addPK(vData.getString("iCveBalancedSC"));
      vData.addPK(vData.getString("iNumIndicadorIns"));
      vData.addPK(vData.getString("iNumCapacidadAtraque"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dToneladasOperadas"));
      lPStmt.setFloat(2,vData.getFloat("dCapacidadUtilizada"));
      lPStmt.setInt(3,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(4,vData.getInt("iNumMovtoSegto"));
      lPStmt.setInt(5,vData.getInt("iEjercicioPOA"));
      lPStmt.setInt(6,vData.getInt("iCveTitulo"));
      lPStmt.setInt(7,vData.getInt("iCveTitular"));
      lPStmt.setInt(8,vData.getInt("iCveBalancedSC"));
      lPStmt.setInt(9,vData.getInt("iNumIndicadorIns"));
      lPStmt.setInt(10,vData.getInt("iNumCapacidadAtraque"));
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

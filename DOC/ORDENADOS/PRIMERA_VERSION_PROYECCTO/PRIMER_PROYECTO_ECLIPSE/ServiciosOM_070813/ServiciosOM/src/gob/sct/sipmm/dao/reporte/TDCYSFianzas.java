package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;


/**
 * <p>Title: TDCYSFianzas.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p> *
 * @version 1.0
 */

public class TDCYSFianzas extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private TFechas    tFecha      = new TFechas("44");
        private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
        private int iEjercicioOficio = 0;
        private String cDigitosFolio = "";
        private int iConsecutivo=0, iDiasSalMin = 0;



	public TDCYSFianzas(){
	}

	public Vector genActaAdmva(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

          Vector vcCuerpo = new Vector();
          Vector vcCopiasPara = new Vector();
          Vector vRegs = new Vector();
          Vector vcProrroga = new Vector();
          TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
          TDCYSFolioProc FP = new TDCYSFolioProc();
          TWord rep = new TWord();
          TFechas Fechas = new TFechas("44");
          TVDinRep vRegDilig = new TVDinRep();

          int iCveOficinaDest=0,iCveDeptoDest=0,iEjercicio=0,iMovProcedimiento,iAniosDuracion=0;
          String cEntidadTitulo="", cMunicipioTitulo="",cIniVigencia="";
          String cTipoTitulo="", cDscOtorgado="",cObjetoTitulo="";
          String cMunicipioEnt="", cPuerto="",cAsunto="";
          String[] aFiltros = cQuery.split(",");
          StringTokenizer st = null;
          Vector vDataSancionAd  = new Vector();
          java.sql.Date dtFin = null;

          iEjercicio = Integer.parseInt(aFiltros[1]);
          iMovProcedimiento = Integer.parseInt(aFiltros[2]);
          Vector vPto = new Vector();
          this.setDatosFolio(cNumFolio);

          String cSQL =
              "SELECT SP6.IEJERCICIO, SP6.IMOVPROCEDIMIENTO, SP1.IMOVPROCEDIMIENTO, CN1.TSFECHANOTIFICACION, " +
              "CN1.DTREGISTRO,CN1.TSFECHANOTIFICACION, FP1.CDIGITOSFOLIO,FP1.ICONSECUTIVO,FP1.IEJERCICIOFOL, " +
              "PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CTITULAR, " +
              "TT.ICVETIPOTITULO,TT.CDSCTIPOTITULO,T.DTINIVIGENCIATITULO,T.DTVIGENCIATITULO,T.COBJETOTITULO,T.iCveTitulo, " +
              "FP6.CDIGITOSFOLIO AS CDIGITOSFOLIOREV,FP6.ICONSECUTIVO AS CCONSECUTIVOREV,FP6.IEJERCICIOFOL AS CEJERCICIOREV,CN6.TSFECHANOTIFICACION AS TSFECHANOTIFICACIONREV " +
              "FROM CYSSEGPROCEDIMIENTO SP6 " +
              "JOIN CYSSEGPROCEDIMIENTO SP5 ON SP6.IEJERCICIOANT = SP5.IEJERCICIO AND SP6.IMOVPROCEDIMIENTOANT = SP5.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP4 ON SP5.IEJERCICIOANT = SP4.IEJERCICIO AND SP5.IMOVPROCEDIMIENTOANT = SP4.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP3 ON SP4.IEJERCICIOANT = SP3.IEJERCICIO AND SP4.IMOVPROCEDIMIENTOANT = SP3.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP2 ON SP3.IEJERCICIOANT = SP2.IEJERCICIO AND SP3.IMOVPROCEDIMIENTOANT = SP2.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP1 ON SP2.IEJERCICIOANT = SP1.IEJERCICIO AND SP2.IMOVPROCEDIMIENTOANT = SP1.IMOVPROCEDIMIENTO " +
              "JOIN CYSFOLIOPROC FP1 ON SP1.IEJERCICIO = FP1.IEJERCICIO AND SP1.IMOVPROCEDIMIENTO = FP1.IMOVPROCEDIMIENTO " +
              "AND FP1.LESPRORROGA = 0 " +
              "AND FP1.LESALEGATO = 0 " +
              "AND FP1.LESSINEFECTO = 0 " +
              "AND FP1.LESNOTIFICACION = 0 " +
              "AND FP1.LESRECORDATORIO = 0 " +
              "AND FP1.LESRESOLUCION = 0 " +
              "AND FP1.LESREVERSION = 0 " +
              "AND FP1.LESACTAADMVA = 0 " +
              "AND FP1.LESACTACIRCUNSTANCIADA = 0 " +
              "JOIN CYSFOLIOPROC FP6 ON SP6.IEJERCICIO = FP6.IEJERCICIO AND SP6.IMOVPROCEDIMIENTO = FP6.IMOVPROCEDIMIENTO " +
              "AND FP6.LESPRORROGA = 0 " +
              "AND FP6.LESALEGATO = 0 " +
              "AND FP6.LESSINEFECTO = 0 " +
              "AND FP6.LESNOTIFICACION = 0 " +
              "AND FP6.LESRECORDATORIO = 0 " +
              "AND FP6.LESRESOLUCION = 0 " +
              "AND FP6.LESREVERSION = 1 " +
              "AND FP6.LESACTAADMVA = 0 " +
              "AND FP6.LESACTACIRCUNSTANCIADA = 0 " +
              "LEFT JOIN CYSNOTOFICIO NO6 ON FP6.IEJERCICIO = NO6.IEJERCICIO AND FP6.IMOVPROCEDIMIENTO = NO6.IMOVPROCEDIMIENTO AND FP6.IMOVFOLIOPROC = NO6.IMOVFOLIOPROC " +
              "LEFT JOIN CYSCITANOTIFICACION CN6 ON NO6.IMOVCITANOTIFICACION = CN6.IMOVCITANOTIFICACION " +
              "JOIN CYSNOTOFICIO NO1 ON FP1.IEJERCICIO = NO1.IEJERCICIO AND FP1.IMOVPROCEDIMIENTO = NO1.IMOVPROCEDIMIENTO " +
              "AND FP1.IMOVFOLIOPROC = NO1.IMOVFOLIOPROC " +
              "JOIN CYSCITANOTIFICACION CN1 ON NO1.IMOVCITANOTIFICACION = CN1.IMOVCITANOTIFICACION " +
              "JOIN CPATITULO T ON SP1.ICVETITULO = T.ICVETITULO " +
              "JOIN CPATITULAR TP ON T.ICVETITULO = TP.ICVETITULO " +
              "JOIN GRLPERSONA PER ON TP.ICVETITULAR = PER.ICVEPERSONA " +
              "LEFT JOIN CPATIPOTITULO TT ON T.ICVETIPOTITULO = TT.ICVETIPOTITULO " +
              "WHERE SP6.IEJERCICIO = "+iEjercicio+" AND SP6.IMOVPROCEDIMIENTO = "+iMovProcedimiento;

          String cDirPto =
              "SELECT EF.CNOMBRE AS CENTIDADFED, MU.CNOMBRE AS CMUNICIPIO, PU.ICVEPUERTO,PU.CDSCPUERTO, EFP.CNOMBRE AS CEFPUERTO, MUP.CNOMBRE AS CMUPUERTO " +
              "FROM CPATITULOUBICACION TU " +
              "LEFT JOIN GRLENTIDADFED EF ON TU.ICVEPAIS = EF.ICVEPAIS AND TU.ICVEENTIDADFED = EF.ICVEENTIDADFED " +
              "LEFT JOIN GRLMUNICIPIO MU ON TU.ICVEPAIS = MU.ICVEPAIS AND TU.ICVEENTIDADFED = MU.ICVEENTIDADFED AND TU.ICVEMUNICIPIO = MU.ICVEMUNICIPIO " +
              "LEFT JOIN GRLPUERTO PU ON TU.ICVEPUERTO = PU.ICVEPUERTO " +
              "LEFT JOIN GRLENTIDADFED EFP ON PU.ICVEPAIS = EFP.ICVEPAIS AND PU.ICVEENTIDADFED = EFP.ICVEENTIDADFED " +
              "LEFT JOIN GRLMUNICIPIO MUP ON PU.ICVEPAIS = MUP.ICVEPAIS AND PU.ICVEENTIDADFED = MUP.ICVEENTIDADFED AND PU.ICVEMUNICIPIO = MUP.ICVEMUNICIPIO "+
              "WHERE TU.ICVETITULO = ";


          try{
            vRegs = super.FindByGeneric("", cSQL, dataSourceName);
          }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
          }catch(Exception ex2){
            ex2.printStackTrace();
          }

          // Elaboración del Reporte.
          rep.iniciaReporte();
          if (vRegs.size() > 0){
            TVDinRep vDatos = (TVDinRep) vRegs.get(0);
            try{
              vPto = super.FindByGeneric("",cDirPto+vDatos.getInt("iCveTitulo"),dataSourceName);
            } catch(SQLException ex){
              ex.printStackTrace();
              cMensaje = ex.getMessage();
            } catch(Exception ex2){
              ex2.printStackTrace();
            }
            if(vPto.size() > 0){
              TVDinRep vcPto = (TVDinRep) vPto.get(0);
              if(vDatos.getInt("iCvePuerto") > 1){
                cEntidadTitulo = vcPto.getString("CEFPUERTO");
                cMunicipioTitulo = vcPto.getString("CMUPUERTO");
              } else{
                cEntidadTitulo = vcPto.getString("CENTIDADFED");
                cMunicipioTitulo = vcPto.getString("CMUNICIPIO");
              }
              rep.comRemplaza("[cEntidadTit]",cEntidadTitulo);
              rep.comRemplaza("[cMunicipioTit]",cMunicipioTitulo);
            }



            rep.comRemplaza("[cFechaHoy]",Fechas.getDateSPN(Fechas.getDateSQL(Fechas.getThisTime())));
            rep.comRemplaza("[cFechaEmicion]",Fechas.getDateSPN(vDatos.getDate("DTREGISTRO")));
            rep.comRemplaza("[cOficioRequerimiento]",vDatos.getString("CDIGITOSFOLIO")+"."+vDatos.getString("ICONSECUTIVO")+"/"+vDatos.getString("IEJERCICIOFOL"));
            if(vDatos.getTimeStamp("TSFECHANOTIFICACION")!=null && !vDatos.getString("TSFECHANOTIFICACION").equals(""))rep.comRemplaza("[cFechaOfiReq]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"))));
            rep.comRemplaza("[cFechaNotifOfReq]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"))));
            if(vDatos.getDate("DTINIVIGENCIATITULO")!=null && !vDatos.getString("DTINIVIGENCIATITULO").equals(""))rep.comRemplaza("[cFechaIniTitulo]",Fechas.getDateSPN(vDatos.getDate("DTINIVIGENCIATITULO")));
            if(vDatos.getDate("DTVIGENCIATITULO")!=null && !vDatos.getString("DTVIGENCIATITULO").equals(""))rep.comRemplaza("[cVigenciaTitulo]",this.getAniosEntre(vDatos.getDate("DTINIVIGENCIATITULO"),vDatos.getDate("DTVIGENCIATITULO")));
            rep.comRemplaza("[cObjetoTitulo]",vDatos.getString("COBJETOTITULO"));
            rep.comRemplaza("[cTitular]",vDatos.getString("CTITULAR"));
            rep.comRemplaza("[cTipoTitulo]",vDatos.getString("CDSCTIPOTITULO"));
            if(vDatos.getTimeStamp("DTINIVIGENCIATITULO")!=null && !vDatos.getString("DTINIVIGENCIATITULO").equals(""))rep.comRemplaza("[cFechaIniTitulo]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("DTINIVIGENCIATITULO"))));
            rep.comRemplaza("[cNumOficioReversion]",vDatos.getString("CDIGITOSFOLIOREV")+"."+vDatos.getString("CCONSECUTIVOREV")+"/"+vDatos.getString("CEJERCICIOFOLREV"));
            if(vDatos.getTimeStamp("TSFECHANOTIFICACIONREV")!=null && !vDatos.getString("TSFECHANOTIFICACIONREV").equals(""))rep.comRemplaza("[cFechaNotificacionReversion]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACIONREV"))));
            if(vDatos.getInt("ICVETIPOTITULO")==1){
              rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL CONCESIONARIO'");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","CONCESIONADOS");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","SECRETARIO DE COMUNICACIONES Y TRANSPORTES");
            }
            else if(vDatos.getInt("ICVETIPOTITULO")==2){
              rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL PERMISIARIO'");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","PERMISIONADOS");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","DIRECTOR GENERAL DE PUERTOS DE LA SECRETARÍA DE COMUNICACIONES Y TRASPORTES");
            }
            else if(vDatos.getInt("ICVETIPOTITULO")==3){
              rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL AUTORIZADO'");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","AUTORIZADOS");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","DIRECTOR GENERAL DE PUERTOS DE LA SECRETARÍA DE COMUNICACIONES Y TRASPORTES");
            } else {
              rep.comRemplaza("[cPseudonimoTipoTitulo]","___________________");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","___________________");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","___________________");
            }
          }
          else {
          }



          Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                                 Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                                 0,0,
                                 0,0,0,
                                 "",cAsunto,
                                 "", "",
                                 true,"cCuerpo",vcCuerpo,
                                 true, vcCopiasPara,
                                 rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

                             this.setDatosFolio(cNumFolio);
                             try{
                                     TVDinRep vDinRep = new TVDinRep();
                                     vDinRep.put("iEjercicio",iEjercicio);
                                     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
                                     vDinRep.put("iEjercicioFol",iEjercicioOficio);
                                     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
                                     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
                                     vDinRep.put("cDigitosFolio",cDigitosFolio);
                                     vDinRep.put("iConsecutivo",iConsecutivo);
                                     vDinRep.put("lEsProrroga",0);
                                     vDinRep.put("lEsAlegato",0);
                                     vDinRep.put("lEsSinEfecto",0);
                                     vDinRep.put("lEsNotificacion",0);
                                     vDinRep.put("lEsRecordatorio",0);
                                     vDinRep.put("lEsResolucion",0);
                                     vDinRep.put("lEsReversion",0);
                                     vDinRep.put("lEsActaAdmva",1);
                                     vDinRep.put("lEsActaCircunstanciada",0);
                                     vDinRep = FP.insertSinUpdate(vDinRep,null);
                             } catch(Exception ex){
                                     ex.printStackTrace();
                             }

                             return vRetorno;

	}

	public Vector genActaCircunstanciada(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

          Vector vcCuerpo = new Vector();
          Vector vcCopiasPara = new Vector();
          Vector vRegs = new Vector();
          Vector vcProrroga = new Vector();
          TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
          TDCYSFolioProc FP = new TDCYSFolioProc();
          TWord rep = new TWord();
          TFechas Fechas = new TFechas("44");
          Vector vPto = new Vector();

          int iCveOficinaDest=0,iCveDeptoDest=0,iEjercicio=0,iMovProcedimiento;
          String cAsunto="",cEntidadTitulo="", cMunicipioTitulo="";
          String[] aFiltros = cQuery.split(",");
          StringTokenizer st = null;
          Vector vDataSancionAd  = new Vector();
          java.sql.Date dtFin = null;

          iEjercicio = Integer.parseInt(aFiltros[1]);
          iMovProcedimiento = Integer.parseInt(aFiltros[2]);
          this.setDatosFolio(cNumFolio);

          String cSQL =
              "SELECT SP6.IEJERCICIO, SP6.IMOVPROCEDIMIENTO, SP1.IMOVPROCEDIMIENTO, CN1.TSFECHANOTIFICACION, " +
              "CN1.DTREGISTRO,CN1.TSFECHANOTIFICACION, FP1.CDIGITOSFOLIO,FP1.ICONSECUTIVO,FP1.IEJERCICIOFOL, " +
              "PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CTITULAR, " +
              "TT.ICVETIPOTITULO,TT.CDSCTIPOTITULO,T.DTINIVIGENCIATITULO,T.DTVIGENCIATITULO,T.COBJETOTITULO,T.iCveTtitulo, " +
              "FP6.CDIGITOSFOLIO AS CDIGITOSFOLIOREV,FP6.ICONSECUTIVO AS CCONSECUTIVOREV,FP6.IEJERCICIOFOL AS CEJERCICIOREV,CN6.TSFECHANOTIFICACION AS TSFECHANOTIFICACIONREV " +
              "FROM CYSSEGPROCEDIMIENTO SP6 " +
              "JOIN CYSSEGPROCEDIMIENTO SP5 ON SP6.IEJERCICIOANT = SP5.IEJERCICIO AND SP6.IMOVPROCEDIMIENTOANT = SP5.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP4 ON SP5.IEJERCICIOANT = SP4.IEJERCICIO AND SP5.IMOVPROCEDIMIENTOANT = SP4.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP3 ON SP4.IEJERCICIOANT = SP3.IEJERCICIO AND SP4.IMOVPROCEDIMIENTOANT = SP3.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP2 ON SP3.IEJERCICIOANT = SP2.IEJERCICIO AND SP3.IMOVPROCEDIMIENTOANT = SP2.IMOVPROCEDIMIENTO " +
              "JOIN CYSSEGPROCEDIMIENTO SP1 ON SP2.IEJERCICIOANT = SP1.IEJERCICIO AND SP2.IMOVPROCEDIMIENTOANT = SP1.IMOVPROCEDIMIENTO " +
              "JOIN CYSFOLIOPROC FP1 ON SP1.IEJERCICIO = FP1.IEJERCICIO AND SP1.IMOVPROCEDIMIENTO = FP1.IMOVPROCEDIMIENTO " +
              "AND FP1.LESPRORROGA = 0 " +
              "AND FP1.LESALEGATO = 0 " +
              "AND FP1.LESSINEFECTO = 0 " +
              "AND FP1.LESNOTIFICACION = 0 " +
              "AND FP1.LESRECORDATORIO = 0 " +
              "AND FP1.LESRESOLUCION = 0 " +
              "AND FP1.LESREVERSION = 0 " +
              "AND FP1.LESACTAADMVA = 0 " +
              "AND FP1.LESACTACIRCUNSTANCIADA = 0 " +
              "JOIN CYSFOLIOPROC FP6 ON SP6.IEJERCICIO = FP6.IEJERCICIO AND SP6.IMOVPROCEDIMIENTO = FP6.IMOVPROCEDIMIENTO " +
              "AND FP6.LESPRORROGA = 0 " +
              "AND FP6.LESALEGATO = 0 " +
              "AND FP6.LESSINEFECTO = 0 " +
              "AND FP6.LESNOTIFICACION = 0 " +
              "AND FP6.LESRECORDATORIO = 0 " +
              "AND FP6.LESRESOLUCION = 0 " +
              "AND FP6.LESREVERSION = 1 " +
              "AND FP6.LESACTAADMVA = 0 " +
              "AND FP6.LESACTACIRCUNSTANCIADA = 0 " +
              "LEFT JOIN CYSNOTOFICIO NO6 ON FP6.IEJERCICIO = NO6.IEJERCICIO AND FP6.IMOVPROCEDIMIENTO = NO6.IMOVPROCEDIMIENTO AND FP6.IMOVFOLIOPROC = NO6.IMOVFOLIOPROC " +
              "LEFT JOIN CYSCITANOTIFICACION CN6 ON NO6.IMOVCITANOTIFICACION = CN6.IMOVCITANOTIFICACION " +
              "JOIN CYSNOTOFICIO NO1 ON FP1.IEJERCICIO = NO1.IEJERCICIO AND FP1.IMOVPROCEDIMIENTO = NO1.IMOVPROCEDIMIENTO " +
              "AND FP1.IMOVFOLIOPROC = NO1.IMOVFOLIOPROC " +
              "JOIN CYSCITANOTIFICACION CN1 ON NO1.IMOVCITANOTIFICACION = CN1.IMOVCITANOTIFICACION " +
              "JOIN CPATITULO T ON SP1.ICVETITULO = T.ICVETITULO " +
              "JOIN CPATITULAR TP ON T.ICVETITULO = TP.ICVETITULO " +
              "JOIN GRLPERSONA PER ON TP.ICVETITULAR = PER.ICVEPERSONA " +
              "LEFT JOIN CPATIPOTITULO TT ON T.ICVETIPOTITULO = TT.ICVETIPOTITULO " +
              "WHERE SP6.IEJERCICIO = "+iEjercicio+" AND SP6.IMOVPROCEDIMIENTO = "+iMovProcedimiento;

          String cDirPto =
              "SELECT EF.CNOMBRE AS CENTIDADFED, MU.CNOMBRE AS CMUNICIPIO, PU.ICVEPUERTO,PU.CDSCPUERTO, EFP.CNOMBRE AS CEFPUERTO, MUP.CNOMBRE AS CMUPUERTO " +
              "FROM CPATITULOUBICACION TU " +
              "LEFT JOIN GRLENTIDADFED EF ON TU.ICVEPAIS = EF.ICVEPAIS AND TU.ICVEENTIDADFED = EF.ICVEENTIDADFED " +
              "LEFT JOIN GRLMUNICIPIO MU ON TU.ICVEPAIS = MU.ICVEPAIS AND TU.ICVEENTIDADFED = MU.ICVEENTIDADFED AND TU.ICVEMUNICIPIO = MU.ICVEMUNICIPIO " +
              "LEFT JOIN GRLPUERTO PU ON TU.ICVEPUERTO = PU.ICVEPUERTO " +
              "LEFT JOIN GRLENTIDADFED EFP ON PU.ICVEPAIS = EFP.ICVEPAIS AND PU.ICVEENTIDADFED = EFP.ICVEENTIDADFED " +
              "LEFT JOIN GRLMUNICIPIO MUP ON PU.ICVEPAIS = MUP.ICVEPAIS AND PU.ICVEENTIDADFED = MUP.ICVEENTIDADFED AND PU.ICVEMUNICIPIO = MUP.ICVEMUNICIPIO "+
              " Where TU.iCveTitulo = ";


          try{
            vRegs = super.FindByGeneric("", cSQL, dataSourceName);

          }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
          }catch(Exception ex2){
            ex2.printStackTrace();
          }

          // Elaboración del Reporte.
          rep.iniciaReporte();

          if (vRegs.size() > 0){
            TVDinRep vDatos = (TVDinRep) vRegs.get(0);
            try{
              vPto = super.FindByGeneric("",cDirPto+vDatos.getInt("iCveTitulo"),dataSourceName);
            } catch(SQLException ex){
              ex.printStackTrace();
              cMensaje = ex.getMessage();
            } catch(Exception ex2){
              ex2.printStackTrace();
            }


            rep.comRemplaza("[cFechaHoy]",Fechas.getDateSPN(Fechas.getDateSQL(Fechas.getThisTime())));
            rep.comRemplaza("[cFechaEmicion]",Fechas.getDateSPN(vDatos.getDate("DTREGISTRO")));
            rep.comRemplaza("[cOficioRequerimiento]",vDatos.getString("CDIGITOSFOLIO")+"."+vDatos.getString("ICONSECUTIVO")+"/"+vDatos.getString("IEJERCICIOFOL"));
            if(vDatos.getTimeStamp("TSFECHANOTIFICACION")!=null && !vDatos.getString("TSFECHANOTIFICACION").equals(""))rep.comRemplaza("[cFechaOfiReq]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"))));
            rep.comRemplaza("[cFechaNotifOfReq]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"))));
            if(vDatos.getDate("DTINIVIGENCIATITULO")!=null && !vDatos.getString("DTINIVIGENCIATITULO").equals(""))rep.comRemplaza("[cFechaIniTitulo]",Fechas.getDateSPN(vDatos.getDate("DTINIVIGENCIATITULO")));
            if(vDatos.getDate("DTVIGENCIATITULO")!=null && !vDatos.getString("DTVIGENCIATITULO").equals(""))rep.comRemplaza("[cVigenciaTitulo]",this.getAniosEntre(vDatos.getDate("DTINIVIGENCIATITULO"),vDatos.getDate("DTVIGENCIATITULO")));
            rep.comRemplaza("[cObjetoTitulo]",vDatos.getString("COBJETOTITULO"));
            rep.comRemplaza("[cTitular]",vDatos.getString("CTITULAR"));
            rep.comRemplaza("[cTipoTitulo]",vDatos.getString("CDSCTIPOTITULO"));
            if(vDatos.getTimeStamp("DTINIVIGENCIATITULO")!=null && !vDatos.getString("DTINIVIGENCIATITULO").equals(""))rep.comRemplaza("[cFechaIniTitulo]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("DTINIVIGENCIATITULO"))));
            rep.comRemplaza("[cNumOficioReversion]",vDatos.getString("CDIGITOSFOLIOREV")+"."+vDatos.getString("CCONSECUTIVOREV")+"/"+vDatos.getString("CEJERCICIOFOLREV"));
            if(vDatos.getTimeStamp("TSFECHANOTIFICACIONREV")!=null && !vDatos.getString("TSFECHANOTIFICACIONREV").equals(""))rep.comRemplaza("[cFechaNotificacionReversion]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACIONREV"))));
            if(vDatos.getInt("ICVETIPOTITULO")==1){
              rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL CONCESIONARIO'");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","CONCESIONADOS");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","SECRETARIO DE COMUNICACIONES Y TRANSPORTES");
            }
            else if(vDatos.getInt("ICVETIPOTITULO")==2){
              rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL PERMISIARIO'");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","PERMISIONADOS");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","DIRECTOR GENERAL DE PUERTOS DE LA SECRETARÍA DE COMUNICACIONES Y TRASPORTES");
            }
            else if(vDatos.getInt("ICVETIPOTITULO")==3){
              rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL AUTORIZADO'");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","AUTORIZADOS");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","DIRECTOR GENERAL DE PUERTOS DE LA SECRETARÍA DE COMUNICACIONES Y TRASPORTES");
            } else {
              rep.comRemplaza("[cPseudonimoTipoTitulo]","___________________");
              rep.comRemplaza("[cBienesPseudoTipoTitulo]","___________________");
              rep.comRemplaza("[cOtorgoTipoTitulo1]","___________________");
            }
            if(vPto.size() > 0){
              TVDinRep vPuertos = (TVDinRep) vRegs.get(0);
              if(vDatos.getInt("iCvePuerto") > 1){
                cEntidadTitulo = vPuertos.getString("CEFPUERTO");
                cMunicipioTitulo = vPuertos.getString("CMUPUERTO");
              } else{
                cEntidadTitulo = vPuertos.getString("CENTIDADFED");
                cMunicipioTitulo = vPuertos.getString("CMUNICIPIO");
              }
              rep.comRemplaza("[cEntidadTit]",cEntidadTitulo);
              rep.comRemplaza("[cMunicipioTit]",cMunicipioTitulo);
            }


          }
          else {
          }


          Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                                 Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                                 0,0,
                                 0,0,0,
                                 "",cAsunto,
                                 "", "",
                                 true,"cCuerpo",vcCuerpo,
                                 true, vcCopiasPara,
                                 rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

                             this.setDatosFolio(cNumFolio);
                             try{
                                     TVDinRep vDinRep = new TVDinRep();
                                     vDinRep.put("iEjercicio",iEjercicio);
                                     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
                                     vDinRep.put("iEjercicioFol",iEjercicioOficio);
                                     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
                                     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
                                     vDinRep.put("cDigitosFolio",cDigitosFolio);
                                     vDinRep.put("iConsecutivo",iConsecutivo);
                                     vDinRep.put("lEsProrroga",0);
                                     vDinRep.put("lEsAlegato",0);
                                     vDinRep.put("lEsSinEfecto",0);
                                     vDinRep.put("lEsNotificacion",0);
                                     vDinRep.put("lEsRecordatorio",0);
                                     vDinRep.put("lEsResolucion",0);
                                     vDinRep.put("lEsReversion",0);
                                     vDinRep.put("lEsActaAdmva",0);
                                     vDinRep.put("lEsActaCircunstanciada",1);
                                     vDinRep = FP.insertSinUpdate(vDinRep,null);
                             } catch(Exception ex){
                                     ex.printStackTrace();
                             }


         return vRetorno;

	}

	public Vector genEjecucion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

                Vector vcCuerpo = new Vector();
                Vector vcCopiasPara = new Vector();
                Vector vRegs = new Vector();
                Vector vcProrroga = new Vector();
                TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
                TWord rep = new TWord();
                TFechas Fechas = new TFechas("44");
                TVDinRep vRegDilig = new TVDinRep();
                Vector vActaAdmva = new Vector();
                Vector vActaCircunstanciada = new Vector();


                int iCveOficinaDest=0,iCveDeptoDest=0,iEjercicio=0,iMovProcedimiento,iAniosDuracion=0;
                String cAsunto="";
                String[] aFiltros = cQuery.split(",");
                StringTokenizer st = null;
                java.sql.Date dtFin = null;

                iEjercicio = Integer.parseInt(aFiltros[1]);
                iMovProcedimiento = Integer.parseInt(aFiltros[2]);
                Vector vNotifRec = new Vector();
                this.setDatosFolio(cNumFolio);

                String cSQL =
                    "SELECT SP6.IEJERCICIO, SP6.IMOVPROCEDIMIENTO, SP1.IMOVPROCEDIMIENTO, CN1.TSFECHANOTIFICACION, " +
                    "CN1.DTREGISTRO,CN1.TSFECHANOTIFICACION, FP1.CDIGITOSFOLIO,FP1.ICONSECUTIVO,FP1.IEJERCICIOFOL, " +
                    "PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CTITULAR, " +
                    "TT.ICVETIPOTITULO,TT.CDSCTIPOTITULO,T.DTINIVIGENCIATITULO, " +
                    "FP6.CDIGITOSFOLIO AS CDIGITOSFOLIOREV,FP6.ICONSECUTIVO AS CCONSECUTIVOREV,FP6.IEJERCICIOFOL AS CEJERCICIOREV,CN6.TSFECHANOTIFICACION AS TSFECHANOTIFICACIONREV " +
                    "FROM CYSSEGPROCEDIMIENTO SP6 " +
                    "JOIN CYSSEGPROCEDIMIENTO SP5 ON SP6.IEJERCICIOANT = SP5.IEJERCICIO AND SP6.IMOVPROCEDIMIENTOANT = SP5.IMOVPROCEDIMIENTO " +
                    "JOIN CYSSEGPROCEDIMIENTO SP4 ON SP5.IEJERCICIOANT = SP4.IEJERCICIO AND SP5.IMOVPROCEDIMIENTOANT = SP4.IMOVPROCEDIMIENTO " +
                    "JOIN CYSSEGPROCEDIMIENTO SP3 ON SP4.IEJERCICIOANT = SP3.IEJERCICIO AND SP4.IMOVPROCEDIMIENTOANT = SP3.IMOVPROCEDIMIENTO " +
                    "JOIN CYSSEGPROCEDIMIENTO SP2 ON SP3.IEJERCICIOANT = SP2.IEJERCICIO AND SP3.IMOVPROCEDIMIENTOANT = SP2.IMOVPROCEDIMIENTO " +
                    "JOIN CYSSEGPROCEDIMIENTO SP1 ON SP2.IEJERCICIOANT = SP1.IEJERCICIO AND SP2.IMOVPROCEDIMIENTOANT = SP1.IMOVPROCEDIMIENTO " +
                    "JOIN CYSFOLIOPROC FP1 ON SP1.IEJERCICIO = FP1.IEJERCICIO AND SP1.IMOVPROCEDIMIENTO = FP1.IMOVPROCEDIMIENTO " +
                    "AND FP1.LESPRORROGA = 0 " +
                    "AND FP1.LESALEGATO = 0 " +
                    "AND FP1.LESSINEFECTO = 0 " +
                    "AND FP1.LESNOTIFICACION = 0 " +
                    "AND FP1.LESRECORDATORIO = 0 " +
                    "AND FP1.LESRESOLUCION = 0 " +
                    "AND FP1.LESREVERSION = 0 " +
                    "AND FP1.LESACTAADMVA = 0 " +
                    "AND FP1.LESACTACIRCUNSTANCIADA = 0 " +
                    "JOIN CYSFOLIOPROC FP6 ON SP6.IEJERCICIO = FP6.IEJERCICIO AND SP6.IMOVPROCEDIMIENTO = FP6.IMOVPROCEDIMIENTO " +
                    "AND FP6.LESPRORROGA = 0 " +
                    "AND FP6.LESALEGATO = 0 " +
                    "AND FP6.LESSINEFECTO = 0 " +
                    "AND FP6.LESNOTIFICACION = 0 " +
                    "AND FP6.LESRECORDATORIO = 0 " +
                    "AND FP6.LESRESOLUCION = 0 " +
                    "AND FP6.LESREVERSION = 1 " +
                    "AND FP6.LESACTAADMVA = 0 " +
                    "AND FP6.LESACTACIRCUNSTANCIADA = 0 " +
                    "LEFT JOIN CYSNOTOFICIO NO6 ON FP6.IEJERCICIO = NO6.IEJERCICIO AND FP6.IMOVPROCEDIMIENTO = NO6.IMOVPROCEDIMIENTO AND FP6.IMOVFOLIOPROC = NO6.IMOVFOLIOPROC " +
                    "LEFT JOIN CYSCITANOTIFICACION CN6 ON NO6.IMOVCITANOTIFICACION = CN6.IMOVCITANOTIFICACION " +
                    "JOIN CYSNOTOFICIO NO1 ON FP1.IEJERCICIO = NO1.IEJERCICIO AND FP1.IMOVPROCEDIMIENTO = NO1.IMOVPROCEDIMIENTO " +
                    "AND FP1.IMOVFOLIOPROC = NO1.IMOVFOLIOPROC " +
                    "JOIN CYSCITANOTIFICACION CN1 ON NO1.IMOVCITANOTIFICACION = CN1.IMOVCITANOTIFICACION " +
                    "JOIN CPATITULO T ON SP1.ICVETITULO = T.ICVETITULO " +
                    "JOIN CPATITULAR TP ON T.ICVETITULO = TP.ICVETITULO " +
                    "JOIN GRLPERSONA PER ON TP.ICVETITULAR = PER.ICVEPERSONA " +
                    "LEFT JOIN CPATIPOTITULO TT ON T.ICVETIPOTITULO = TT.ICVETIPOTITULO " +
                    "WHERE SP6.IEJERCICIO = "+iEjercicio+" AND SP6.IMOVPROCEDIMIENTO = "+iMovProcedimiento;
          String cActaAdmva =
              "SELECT F.DTASIGNACION FROM CYSSEGPROCEDIMIENTO sp " +
              " JOIN CYSFOLIOPROC FP " +
              "    ON SP.IEJERCICIO = FP.IEJERCICIO " +
              "    AND SP.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
              "    AND FP.LESPRORROGA = 0 " +
              "    AND FP.LESALEGATO = 0 " +
              "    AND FP.LESSINEFECTO = 0 " +
              "    AND FP.LESNOTIFICACION = 0 " +
              "    AND FP.LESRECORDATORIO = 0 " +
              "    AND FP.LESRESOLUCION = 0 " +
              "    AND FP.LESREVERSION = 0 " +
              "    AND FP.LESACTAADMVA = 1 " +
              "    AND FP.LESACTACIRCUNSTANCIADA = 0 " +
              "LEFT JOIN GRLFOLIO F " +
              "ON FP.IEJERCICIOFOL = F.IEJERCICIO " +
              "AND FP.ICVEOFICINA = F.ICVEOFICINA " +
              "AND FP.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO " +
              "AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
              "AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
              "where sp.IEJERCICIO = "+iEjercicio+" and sp.IMOVPROCEDIMIENTO  = " +iMovProcedimiento +" "+
              "AND FP.IMOVFOLIOPROC = (SELECT MAX(IMOVFOLIOPROC) FROM CYSFOLIOPROC WHERE IEJERCICIO = "+iEjercicio+" and IMOVPROCEDIMIENTO  = "+iMovProcedimiento+" AND LESACTAADMVA = 1) ";

          String cActaCircunstanciada =
              "SELECT F.DTASIGNACION FROM CYSSEGPROCEDIMIENTO sp " +
              " JOIN CYSFOLIOPROC FP " +
              "    ON SP.IEJERCICIO = FP.IEJERCICIO " +
              "    AND SP.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
              "    AND FP.LESPRORROGA = 0 " +
              "    AND FP.LESALEGATO = 0 " +
              "    AND FP.LESSINEFECTO = 0 " +
              "    AND FP.LESNOTIFICACION = 0 " +
              "    AND FP.LESRECORDATORIO = 0 " +
              "    AND FP.LESRESOLUCION = 0 " +
              "    AND FP.LESREVERSION = 0 " +
              "    AND FP.LESACTAADMVA = 0 " +
              "    AND FP.LESACTACIRCUNSTANCIADA = 1 " +
              "JOIN GRLFOLIO F " +
              "ON FP.IEJERCICIOFOL = F.IEJERCICIO " +
              "AND FP.ICVEOFICINA = F.ICVEOFICINA " +
              "AND FP.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO " +
              "AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
              "AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
              "where sp.IEJERCICIO = "+iEjercicio+" and sp.IMOVPROCEDIMIENTO  = " +iMovProcedimiento +" "+
              "AND FP.IMOVFOLIOPROC = (SELECT MAX(IMOVFOLIOPROC) FROM CYSFOLIOPROC WHERE IEJERCICIO = "+iEjercicio+" and IMOVPROCEDIMIENTO  = "+iMovProcedimiento+" AND LESACTACIRCUNSTANCIADA = 1) ";


                try{
                  vRegs = super.FindByGeneric("", cSQL, dataSourceName);
                  vActaAdmva = super.FindByGeneric("",cActaAdmva,dataSourceName);
                  vActaCircunstanciada = super.FindByGeneric("",cActaCircunstanciada,dataSourceName);

                }catch(SQLException ex){
                  ex.printStackTrace();
                  cMensaje = ex.getMessage();
                }catch(Exception ex2){
                  ex2.printStackTrace();
                }

                // Elaboración del Reporte.
                rep.iniciaReporte();
          if(vActaAdmva.size()>0){
            TVDinRep vAdmva = (TVDinRep) vActaAdmva.get(0);
            rep.comRemplaza("[dtActaAdmva]",Fechas.getDateSPN(vAdmva.getDate("DTASIGNACION")));
          }
          if(vActaCircunstanciada.size()>0){
            TVDinRep vActaCir = (TVDinRep) vActaCircunstanciada.get(0);
            rep.comRemplaza("[dtActaCircunstanciada]",Fechas.getDateSPN(vActaCir.getDate("DTASIGNACION")));
          }

                if (vRegs.size() > 0){
                  TVDinRep vDatos = (TVDinRep) vRegs.get(0);

                  rep.comRemplaza("[cFechaHoy]",Fechas.getDateSPN(Fechas.getDateSQL(Fechas.getThisTime())));
                  rep.comRemplaza("[cFechaEmicion]",Fechas.getDateSPN(vDatos.getDate("DTREGISTRO")));
                  rep.comRemplaza("[cOficioRequerimiento]",vDatos.getString("CDIGITOSFOLIO")+"."+vDatos.getString("ICONSECUTIVO")+"/"+vDatos.getString("IEJERCICIOFOL"));
                  if(vDatos.getTimeStamp("TSFECHANOTIFICACION")!=null && !vDatos.getString("TSFECHANOTIFICACION").equals(""))rep.comRemplaza("[cFechaOfiReq]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"))));
                  rep.comRemplaza("[cFechaNotifOfReq]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"))));
                  rep.comRemplaza("[cTitular]",vDatos.getString("CTITULAR"));
                  rep.comRemplaza("[cTipoTitulo]",vDatos.getString("CDSCTIPOTITULO"));
                  if(vDatos.getTimeStamp("DTINIVIGENCIATITULO")!=null && !vDatos.getString("DTINIVIGENCIATITULO").equals(""))rep.comRemplaza("[cFechaIniTitulo]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("DTINIVIGENCIATITULO"))));
                  rep.comRemplaza("[cNumOficioReversion]",vDatos.getString("CDIGITOSFOLIOREV")+"."+vDatos.getString("CCONSECUTIVOREV")+"/"+vDatos.getString("CEJERCICIOFOLREV"));
                  if(vDatos.getTimeStamp("TSFECHANOTIFICACIONREV")!=null && !vDatos.getString("TSFECHANOTIFICACIONREV").equals(""))rep.comRemplaza("[cFechaNotificacionReversion]",Fechas.getDateSPN(Fechas.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACIONREV"))));
                  if(vDatos.getInt("ICVETIPOTITULO")==1)rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL CONCESIONARIO'");
                    else if(vDatos.getInt("ICVETIPOTITULO")==2)rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL PERMISIARIO'");
                      else if(vDatos.getInt("ICVETIPOTITULO")==3)rep.comRemplaza("[cPseudonimoTipoTitulo]","'EL AUTORIZADO'");
                        else rep.comRemplaza("[cPseudonimoTipoTitulo]","___________________");
                }
                else {
                }


                Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                                       Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                                       0,0,
                                       0,0,0,
                                       "",cAsunto,
                                       "", "",
                                       true,"cCuerpo",vcCuerpo,
                                       true, vcCopiasPara,
                                       rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

               return vRetorno;

	}
        private void setDatosFolio(String cNumFolio){
           String[] cDatosFolio = cNumFolio.split("/");
           String cTemp = cDatosFolio[0].replace('.', '/');
           String[] cDigitosTemp = cTemp.split("/");
           int Ejercicio = Integer.parseInt(cDatosFolio[cDatosFolio.length-1], 10);
           int Consecutivo = Integer.parseInt(cDigitosTemp[cDigitosTemp.length-1], 10);
           String Digitos = "";
           for(int i=0; i<cDigitosTemp.length-1; i++){
             Digitos += cDigitosTemp[i];
             if (i<cDigitosTemp.length-2)
               Digitos += ".";
           }
           iEjercicioOficio = Ejercicio;
           cDigitosFolio = Digitos;
           iConsecutivo = Consecutivo;
         }

         private String getAniosEntre (java.sql.Date Date1, java.sql.Date Date2){
           int iAnios=0;
           //double dAnios = 0.0;
           TFechas fecha = new TFechas();
           fecha.getStringDay(Date1);
           //dAnios = (fecha.getDaysBetweenDates(Date1,Date2))%365;
           //dAnios = (dAnios>182)?1:0;
           iAnios = ((fecha.getDaysBetweenDates(Date1,Date2))/365);
           //iAnios +=(int)dAnios;
           return ""+iAnios;
         }


}

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
 * <p>Title: TDCYSReversion.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ALOPEZ
 * @version 1.0
 */

public class TDCYSRRContrato extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private TFechas    tFecha      = new TFechas("44");
	TDObtenDatos dObten = new TDObtenDatos();
	TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

	public TDCYSRRContrato(){
	}


	//public Vector genReversionPFP(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
	public Vector generaRecuperacion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");
		TDCYSDiligenciaReversion diligencia = new TDCYSDiligenciaReversion();
		TVDinRep comisionados = new TVDinRep();

		int iCveOficinaDest=0,iCveDeptoDest=0;
		String cAsunto="";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio = Integer.parseInt(aFiltros[0]), iMovProcedimiento = Integer.parseInt(aFiltros[1]);
		String cSQL =
                    "SELECT " +
                    "       CU.CSUPERFICIE, " +
                    "       PC.ICVEPERSONA, " +
                    "       PC.CNOMRAZONSOCIAL ||' '|| PC.CAPPATERNO ||' '|| PC.CAPMATERNO AS CCESIONARIO, " +
                    "       TU.LDENTRORECINTOPORT, " +
                    "       P.CDSCPUERTO, " +
                    "       TU.CCALLETITULO ||' '|| TU.CCOLONIATITULO ||' '|| MU.CNOMBRE||', '|| EF.CNOMBRE  AS CDIRECCIONTITULO , " +
                    "       PUC.CDSCPUERTO AS PUERTOCONTRATO, O.CDSCOFICINA, O.CTITULAR, " +
                    "       O.CCALLEYNO||', '|| O.CCOLONIA||'. C.P. '|| O.CCODPOSTAL||'. '|| MO.CNOMBRE||', '|| EFO.CNOMBRE AS CDIRCAPITANIA, " +
                    "       EFO.CNOMBRE AS CEFCAPITANIA, DO.CPUESTOTITULAR, DO.CTITULAR " +
                    "FROM CYSSEGPROCEDIMIENTO SP " +
                    "  JOIN RGCCONTRATO C ON SP.ICVECONTRATO = C.ICVECONTRATO " +
                    "  JOIN GRLPERSONA PC ON C.ICVECESIONARIO = PC.ICVEPERSONA " +
                    "  JOIN CPATITULO T ON C.ICVETITULO = T.ICVETITULO " +
                    "  JOIN CPATITULAR TI ON T.ICVETITULO = TI.ICVETITULO " +
                    "  JOIN GRLPERSONA PT ON TI.ICVETITULAR = PT.ICVEPERSONA " +
                    "  JOIN CPATITULOUBICACION TU ON T.ICVETITULO = TU.ICVETITULO  LEFT " +
                    "  JOIN GRLENTIDADFED EF ON TU.ICVEENTIDADFED = EF.ICVEENTIDADFED " +
                    "    AND EF.ICVEPAIS = 1  LEFT " +
                    "  JOIN GRLMUNICIPIO MU ON TU.ICVEENTIDADFED = MU.ICVEENTIDADFED " +
                    "    AND TU.ICVEMUNICIPIO = MU.ICVEMUNICIPIO " +
                    "    AND MU.ICVEPAIS = 1  LEFT " +
                    "  JOIN RGCCONTRATOUBICACION CU ON C.ICVECONTRATO = CU.ICVECONTRATO  LEFT " +
                    "  JOIN GRLPUERTO P ON TU.ICVEPUERTO = P.ICVEPUERTO  LEFT " +
                    "  JOIN GRLPUERTO PUC ON CU.ICVEPUERTO = PUC.ICVEPUERTO " +
                    "  LEFT JOIN GRLOFICINA O ON PUC.ICVEOFICINAADSCRITA = O.ICVEOFICINA " +
                    "  LEFT JOIN GRLENTIDADFED EFO ON O.ICVEPAIS = EFO.ICVEPAIS AND O.ICVEENTIDADFED = EFO.ICVEENTIDADFED " +
                    "  LEFT JOIN GRLMUNICIPIO MO ON O.ICVEPAIS= MO.ICVEPAIS AND O.ICVEENTIDADFED= MO.ICVEENTIDADFED AND O.ICVEMUNICIPIO= MO.ICVEMUNICIPIO " +
                    "  LEFT JOIN GRLDEPTOXOFIC DO ON O.ICVEOFICINA = DO.ICVEOFICINA AND DO.ICVEDEPARTAMENTO = 0 " +
                    "WHERE SP.IEJERCICIO = "+iEjercicio+
                    "  AND SP.IMOVPROCEDIMIENTO = "+iMovProcedimiento;

		try{
			vRegs = super.FindByGeneric("", cSQL, dataSourceName);
		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}
		try{
			comisionados = diligencia.regresaDiligencia(iEjercicio,iMovProcedimiento);
		}catch(Exception ex2){
			ex2.printStackTrace();
		}


		// Elaboración del Reporte.
		rep.iniciaReporte();
		if (vRegs.size() > 0){
			TVDinRep comision = (TVDinRep) vRegs.get(0);
			if(!comisionados.getString("cUsuarios").equals("null") && comisionados.getString("cUsuarios")!=null) rep.comRemplaza("[cComisionados]",comisionados.getString("cUsuarios")); else rep.comRemplaza("[cComisionados]","_________________________________");
			if(!comision.getString("CSUPERFICIE").equals("null") && comision.getString("CSUPERFICIE")!=null)rep.comRemplaza("[cSuperficie]",comision.getString("CSUPERFICIE"));else rep.comRemplaza("[cSuperficie]","_____________");
			if(!comision.getString("CCESIONARIO").equals("null") && comision.getString("CCESIONARIO")!=null)rep.comRemplaza("[cCesionario]",comision.getString("CCESIONARIO"));else rep.comRemplaza("[cCesionario]","__________________________________");
			if(!comision.getString("PUERTOCONTRATO").equals("null") && comision.getString("PUERTOCONTRATO")!=null)rep.comRemplaza("[cRecinto]",comision.getString("PUERTOCONTRATO"));else rep.comRemplaza("[cRecinto]","____________________________");
			if(comisionados.getTimeStamp("TSDILIGENCIA")!=null)rep.comRemplaza("[cFechaDiligencia]",Fechas.getDateSPN(Fechas.getDateSQL(comisionados.getTimeStamp("TSDILIGENCIA"))));else rep.comRemplaza("[cFechaDiligencia]","_____/______/_____");
			if(comisionados.getTimeStamp("TSDILIGENCIA")!=null)rep.comRemplaza("[cHoraDiligencia]",Fechas.getHora12H(comisionados.getTimeStamp("TSDILIGENCIA"))+":"+Fechas.getMinuto(comisionados.getTimeStamp("TSDILIGENCIA")));else rep.comRemplaza("[cHoraDiligencia]","______:______");
			if(!comision.getString("CDIRCAPITANIA").equals("null") && comision.getString("CDIRCAPITANIA")!=null)rep.comRemplaza("[cDireccionCapitania]",comision.getString("CDIRCAPITANIA"));else rep.comRemplaza("[cDireccionCapitania]","_________________________________");
                        if(!comision.getString("CTITULAR").equals("null") && comision.getString("CTITULAR")!=null)rep.comRemplaza("[cEncargadoCapitania]",comision.getString("CTITULAR"));else rep.comRemplaza("[cEncargadoCapitania]","_________________________________");
                        if(!comision.getString("CEFCAPITANIA").equals("null") && comision.getString("CEFCAPITANIA")!=null)rep.comRemplaza("[cEntidadCapitania]",comision.getString("CEFCAPITANIA"));else rep.comRemplaza("[cEntidadCapitania]","_________________________________");
                        if(!comision.getString("CPUESTOTITULAR").equals("null") && comision.getString("CPUESTOTITULAR")!=null)rep.comRemplaza("[cPuesto]",comision.getString("CPUESTOTITULAR"));else rep.comRemplaza("[cPuesto]","_________________________________");

		} else {
			//Sin Valores en la Base.
		}
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				0,0,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());


                TVDinRep vDinRep = new TVDinRep();
                vDinRep.put("iEjercicio",iEjercicio);
                vDinRep.put("iMovProcedimiento",iMovProcedimiento);
                vDinRep.put("iEjercicioFol",dObten.dFolio.getCveEjercicio());
                vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
                vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
                vDinRep.put("cDigitosFolio",dObten.dFolio.getCveDigitosFolio());
                vDinRep.put("iConsecutivo",dObten.dFolio.getCveConsecutivo());
                vDinRep.put("lEsProrroga",0);
                vDinRep.put("lEsAlegato",0);
                vDinRep.put("lEsSinEfecto",0);
                vDinRep.put("lEsNotificacion",0);
                vDinRep.put("lEsRecordatorio",0);
                vDinRep.put("lEsResolucion",1);

                try{
                        vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
                } catch(Exception ex){
                        ex.printStackTrace();
                }


		return vRetorno;


	}

	public Vector generaAlegato(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");

		int iCveOficinaDest=0,iCveDeptoDest=0;
		String cAsunto = "";
		int iCveCesionario = 0;
		String[] aFiltros = cQuery.split(",");
		int iEjercicio = Integer.parseInt(aFiltros[0]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[1]);
		String cSQL =
			"SELECT " +
			"IP.CNUMESCRITO,IP.CNUMFOLIODGP, " +
			"FP.CDIGITOSFOLIO,FP.ICONSECUTIVO,FP.IEJERCICIO,F.DTASIGNACION, " +
			"TC.CDSCTIPOCONTRATO,C.DTCONTRATO, " +
			"C.ICVECESIONARIO,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CCESIONARIO, " +
			"C.DTREGISTRO,C.CNUMCONTRATO " +
			"FROM CYSSEGPROCEDIMIENTO SP " +
			"JOIN CYSSEGPROCEDIMIENTO SPA ON SP.IEJERCICIOANT = SPA.IEJERCICIO "+
			"                            AND SP.IMOVPROCEDIMIENTOANT = SPA.IMOVPROCEDIMIENTO "+
			"JOIN RGCCONTRATO C ON SP.ICVECONTRATO = C.ICVECONTRATO " +
			"JOIN GRLPERSONA P ON C.ICVECESIONARIO = P.ICVEPERSONA " +
			"LEFT JOIN CYSINFOPRELIMINAR IP ON SPA.IEJERCICIOANT = IP.IEJERCICIO " +
			"                              AND SPA.IMOVPROCEDIMIENTOANT = IP.IMOVPROCEDIMIENTO " +
			"JOIN RGCTIPOCONTRATO TC ON C.ICVETIPOCONTRATO = TC.ICVETIPOCONTRATO " +
			"LEFT JOIN CYSFOLIOPROC FP ON SP.IEJERCICIO = FP.IEJERCICIO " +
			"                         AND SP.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
			"                         AND FP.LESPRORROGA <> 1 " +
			"                         AND FP.LESALEGATO <> 1 " +
			"                         AND FP.LESSINEFECTO <> 1 " +
			"                         AND FP.LESNOTIFICACION <> 1 " +
			"                         AND FP.LESRECORDATORIO <> 1 " +
			"                         AND FP.LESRESOLUCION <> 1 " +
			"                         AND FP.LESREVERSION <> 1 " +
			"                         AND FP.LESACTAADMVA <> 1 " +
			"                         AND FP.LESACTACIRCUNSTANCIADA <> 1 " +
			"JOIN GRLFOLIO F ON FP.IEJERCICIO = F.IEJERCICIO " +
			"               AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
			"               AND FP.ICVEOFICINA = F.ICVEOFICINA " +
			"               AND FP.ICVEDEPARTAMENTO  = F.ICVEDEPARTAMENTO " +
			"               AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
			" WHERE SP.iEjercicio = " + iEjercicio +
			" AND SP.IMOVPROCEDIMIENTO = " + iMovProcedimiento +
			" ORDER BY FP.IMOVFOLIOPROC DESC";

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
			TVDinRep alegato = (TVDinRep) vRegs.get(0);
			if(alegato.getString("CNUMESCRITO")!=null && !alegato.getString("CNUMESCRITO").equals("null"))
				rep.comRemplaza("[cNumOficioSolicitudROP]",alegato.getString("CNUMESCRITO"));
			if(alegato.getString("CNUMFOLIODGP")!=null && !alegato.getString("CNUMFOLIODGP").equals("null"))
				rep.comRemplaza("[cNumOficialiaROP]",alegato.getString("CNUMFOLIODGP"));
			if(alegato.getString("CDIGITOSFOLIO")!=null && !alegato.getString("CDIGITOSFOLIO").equals("null"))
				rep.comRemplaza("[cNumOficioInicioRevocacion]",alegato.getString("CDIGITOSFOLIO")+
						"."+alegato.getString("ICONSECUTIVO")+
						"/"+alegato.getString("IEJERCICIO"));
			if(alegato.getDate("DTASIGNACION")!=null)rep.comRemplaza("[cFechaOficioInicioRevocacion]",Fechas.getDateSPN(alegato.getDate("DTASIGNACION")));
			rep.comRemplaza("[cTipoContrato]",alegato.getString("CDSCTIPOCONTRATO"));
			if(alegato.getDate("DTASIGNACION")!=null)rep.comRemplaza("[cFechaContrato]",Fechas.getDateSPN(alegato.getDate("DTCONTRATO")));
			rep.comRemplaza("[cCesionario]",alegato.getString("CCESIONARIO"));
			iCveCesionario = alegato.getInt("ICVECESIONARIO");
			if(alegato.getDate("DTASIGNACION")!=null)rep.comRemplaza("[cFechaRegistroContrato]",Fechas.getDateSPN(alegato.getDate("DTREGISTRO")));
			rep.comRemplaza("[cNumContrato]",alegato.getString("CNUMCONTRATO"));
		} else {
			//Sin Valores en la Base.
		}
		dObten.dFolio.setDatosFolio(cNumFolio);
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				iCveCesionario,1,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());


		TVDinRep vDinRep = new TVDinRep();
		vDinRep.put("iEjercicio",iEjercicio);
		vDinRep.put("iMovProcedimiento",iMovProcedimiento);
		vDinRep.put("iEjercicioFol",dObten.dFolio.getCveEjercicio());
		vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
		vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
		vDinRep.put("cDigitosFolio",dObten.dFolio.getCveDigitosFolio());
		vDinRep.put("iConsecutivo",dObten.dFolio.getCveConsecutivo());
		vDinRep.put("lEsProrroga",0);
		vDinRep.put("lEsAlegato",1);
		vDinRep.put("lEsSinEfecto",0);
		vDinRep.put("lEsNotificacion",0);
		vDinRep.put("lEsRecordatorio",0);
		vDinRep.put("lEsResolucion",0);

		try{
			vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}


		return vRetorno;

	}

	public Vector generaInicioProcedimiento(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TWord rep = new TWord();
		String cAsunto="";
		String[] aFiltros = cQuery.split(",");

		int iEjercicio = Integer.parseInt(aFiltros[0]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[1]);
		dObten.dFolio.setDatosFolio(cNumFolio);

		rep.iniciaReporte();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("RGC.DTREGISTRO AS DTREGISTRO, ");
		sb.append("RGC.DTCONTRATO AS DTCONTRATO,");
		sb.append("RGC.CNUMCONTRATO AS CNUMCONTRATO, ");
		sb.append("PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CNOMBRE, ");
		sb.append("RGC.ICVECONTRATOANT AS ICVECONTRATOANT, ");
		sb.append("INFO.DTESCRITO AS DTREGISTROINFO ");
		sb.append("FROM CYSSEGPROCEDIMIENTO SP ");
		sb.append("JOIN RGCCONTRATO RGC ON SP.ICVECONTRATO = RGC.ICVECONTRATO ");
		sb.append("JOIN CPATITULAR TIT ON RGC.ICVETITULO = TIT.ICVETITULO ");
		sb.append("JOIN GRLPERSONA PER ON TIT.ICVETITULAR = PER.ICVEPERSONA ");
		sb.append("JOIN CYSSEGPROCEDIMIENTO SPANT ON SP.IEJERCICIOANT = SPANT.IEJERCICIO ");
		sb.append("AND SP.IMOVPROCEDIMIENTOANT = SPANT.IMOVPROCEDIMIENTO ");
		sb.append("JOIN CYSSEGPROCEDIMIENTO SPPRE ON SPANT.IEJERCICIOANT = SPPRE.IEJERCICIO ");
		sb.append("AND  SPANT.IMOVPROCEDIMIENTOANT = SPPRE.IMOVPROCEDIMIENTO ");
		sb.append("JOIN CYSINFOPRELIMINAR INFO ON SPPRE.IEJERCICIO = INFO.IEJERCICIO ");
		sb.append("AND SPPRE.IMOVPROCEDIMIENTO = INFO.IMOVPROCEDIMIENTO ");
		sb.append("WHERE SP.IEJERCICIO = " + iEjercicio + " AND SP.IMOVPROCEDIMIENTO = " + iMovProcedimiento);
		Vector vDatosGrales = new Vector();

		try {
			vDatosGrales  = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TVDinRep vData = new TVDinRep();
		if(vDatosGrales.size() > 0)
			vData = (TVDinRep) vDatosGrales.get(0);

		Vector vModi = new Vector();
		if(vData.getInt("ICVECONTRATOANT") > 0)
			vModi =  this.regresaContratosAnt(vData.getInt("ICVECONTRATOANT"));

		String cNumContratoMod = "";
		String cFechaRegMod = "";
		String cFechaContrato = "";
		for(int i = 0;i < vModi.size(); i++){
			TVDinRep vPrueba = (TVDinRep) vModi.get(i);
			if(!cNumContratoMod.equalsIgnoreCase(""))
				cNumContratoMod  += ", " + vPrueba.getString("CNUMCONTRATO");
			else
				cNumContratoMod  = vPrueba.getString("CNUMCONTRATO");

			if(!cFechaRegMod.equalsIgnoreCase(""))
				cFechaRegMod += ", " + vPrueba.getDate("DTREGISTRO") != null ? tFecha.getDateSPN(vPrueba.getDate("DTREGISTRO")):"____/_____/______";
				else
					cFechaRegMod = vPrueba.getDate("DTREGISTRO") != null ? tFecha.getDateSPN(vPrueba.getDate("DTREGISTRO")):"____/_____/______";

					if(!cFechaContrato.equalsIgnoreCase(""))
						cFechaContrato = vPrueba.getDate("DTCONTRATO") != null ? tFecha.getDateSPN(vPrueba.getDate("DTCONTRATO")):"____/_____/______";
						else
							cFechaContrato += ", " + vPrueba.getDate("DTCONTRATO") != null ? tFecha.getDateSPN(vPrueba.getDate("DTCONTRATO")):"____/_____/______";

		}

		//System.out.println("FEcha 1 " + vData.getDate("DTREGISTRO") );
		rep.comRemplaza("[cFechaRegistroContrato]", tFecha.getDateSPN(vData.getDate("DTREGISTRO")));
		//System.out.println("Sale FEcha 1");
		rep.comRemplaza("[cNumContrato]", vData.getString("CNUMCONTRATO"));
		rep.comRemplaza("[cAPI]", vData.getString("CNOMBRE"));
		//System.out.println("FEcha 2");
		rep.comRemplaza("[cFechaContrato]", tFecha.getDateSPN(vData.getDate("DTCONTRATO")));
		rep.comRemplaza("[cNumContratoModif]", cNumContratoMod);
		rep.comRemplaza("[cFechaRegConModif]", cFechaRegMod);
		rep.comRemplaza("[cFechaContratoModif]", cFechaContrato);
		//System.out.println("\n\n\n\n " + vData.getDate("DTREGISTROINFO"));
		rep.comRemplaza("[cFechaEscrito]", tFecha.getDateSPN(vData.getDate("DTREGISTROINFO")));

		// Elaboración del Reporte.


		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				0,0,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		TVDinRep vDinRep = new TVDinRep();
		vDinRep.put("iEjercicio",iEjercicio);
		vDinRep.put("iMovProcedimiento",iMovProcedimiento);
		vDinRep.put("iEjercicioFol",dObten.dFolio.getCveEjercicio());
		vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
		vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
		vDinRep.put("cDigitosFolio",dObten.dFolio.getCveDigitosFolio());
		vDinRep.put("iConsecutivo",dObten.dFolio.getCveConsecutivo());
		vDinRep.put("lEsProrroga",0);
		vDinRep.put("lEsAlegato",0);
		vDinRep.put("lEsSinEfecto",0);
		vDinRep.put("lEsNotificacion",0);
		vDinRep.put("lEsRecordatorio",0);
		vDinRep.put("lEsResolucion",0);

		try{
			vDinRep = dCYSFolioProc.insert(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return vRetorno;

	}


	public Vector generaComision(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");
		TDCYSDiligenciaReversion diligencia = new TDCYSDiligenciaReversion();
		TVDinRep comisionados = new TVDinRep();

		int iCveOficinaDest=0,iCveDeptoDest=0;
		String cAsunto="";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio = Integer.parseInt(aFiltros[0]), iMovProcedimiento = Integer.parseInt(aFiltros[1]);
		String cSQL =
			"SELECT " +
			"CU.CSUPERFICIE, " +
			"PC.ICVEPERSONA, " +
			"PC.CNOMRAZONSOCIAL||' '|| PC.CAPPATERNO||' '|| PC.CAPMATERNO AS CCESIONARIO, " +
			"TU.LDENTRORECINTOPORT, " +
			"P.CDSCPUERTO, " +
			"TU.CCALLETITULO||' '|| TU.CCOLONIATITULO||' '|| EF.CNOMBRE||', '|| MU.CNOMBRE AS CDIRECCIONTITULO, " +
			"PUC.CDSCPUERTO AS PUERTOCONTRATO " +
			" FROM CYSSEGPROCEDIMIENTO SP " +
			"JOIN RGCCONTRATO C ON SP.ICVECONTRATO = C.ICVECONTRATO " +
			"JOIN GRLPERSONA PC ON C.ICVECESIONARIO = PC.ICVEPERSONA " +
			"JOIN CPATITULO T ON C.ICVETITULO = T.ICVETITULO " +
			"JOIN CPATITULAR TI ON T.ICVETITULO = TI.ICVETITULO " +
			"JOIN GRLPERSONA PT ON TI.ICVETITULAR = PT.ICVEPERSONA " +
			"JOIN CPATITULOUBICACION TU ON T.ICVETITULO = TU.ICVETITULO " +
			"LEFT JOIN GRLENTIDADFED EF ON TU.ICVEENTIDADFED = EF.ICVEENTIDADFED AND EF.ICVEPAIS = 1 " +
			"LEFT JOIN GRLMUNICIPIO MU ON TU.ICVEENTIDADFED = MU.ICVEENTIDADFED AND TU.ICVEMUNICIPIO = MU.ICVEMUNICIPIO AND MU.ICVEPAIS = 1 " +
			"JOIN RGCCONTRATOUBICACION CU ON C.ICVECONTRATO = CU.ICVECONTRATO " +
			"JOIN GRLPUERTO P ON TU.ICVEPUERTO = P.ICVEPUERTO " +
			"JOIN GRLPUERTO PUC ON CU.ICVEPUERTO = PUC.ICVEPUERTO "+
			"WHERE SP.IEJERCICIO = "+iEjercicio+
			" AND SP.IMOVPROCEDIMIENTO = "+iMovProcedimiento;
		try{
			vRegs = super.FindByGeneric("", cSQL, dataSourceName);
		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}
		try{
			comisionados = diligencia.regresaDiligencia(iEjercicio,iMovProcedimiento);
		}catch(Exception ex2){
			ex2.printStackTrace();
		}


		// Elaboración del Reporte.
		rep.iniciaReporte();
		if (vRegs.size() > 0){
			TVDinRep comision = (TVDinRep) vRegs.get(0);
			rep.comRemplaza("[cComisionados]",comisionados.getString("cUsuarios"));
			rep.comRemplaza("[cSuperficie]",comision.getString("CSUPERFICIE"));
			rep.comRemplaza("[cCesionario]",comision.getString("CCESIONARIO"));
			rep.comRemplaza("[cRecinto]",comision.getString("PUERTOCONTRATO"));
			rep.comRemplaza("[cFechaDiligencia]",Fechas.getDateSPN(Fechas.getDateSQL(comisionados.getTimeStamp("TSDILIGENCIA"))));
			rep.comRemplaza("[xxx]",Fechas.getHora12H(comisionados.getTimeStamp("TSDILIGENCIA"))+":"+Fechas.getMinuto(comisionados.getTimeStamp("TSDILIGENCIA")));
		} else {
			//Sin Valores en la Base.
		}
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				0,0,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		TVDinRep vDinRep = new TVDinRep();

		return vRetorno;

	}

	public Vector generaResolRevocacion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
                Vector vcCuerpo = new Vector();
                Vector vcCopiasPara = new Vector();
                Vector vProcedimiento = new Vector();
                Vector vNotificacion = new Vector();
                Vector vProrroga = new Vector();
                Vector vAlegato = new Vector();
                TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
                TWord rep = new TWord();
                TFechas Fechas = new TFechas("44");
                int iCveContrato=0;
                int iCveTitular = 0;

		int iCveOficinaDest=0,iCveDeptoDest=0;
		String cAsunto="";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio = Integer.parseInt(aFiltros[0]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[1]);
		String cProcedimiento =
                    "SELECT " +
                    "  P1.CNOMRAZONSOCIAL||' '|| P1.CAPPATERNO||' '|| P1.CAPMATERNO AS CAPI, " +
                    "  P2.CNOMRAZONSOCIAL||' '|| P2.CAPPATERNO||' '|| P2.CAPMATERNO AS CCESIONARIO, " +
                    "  C.DTCONTRATO,"+
                    "C.DTREGISTRO,"+
                    "C.DTINIVIGENCIA,"+
                    "C.DTVENCIMIENTO,"+
                    "C.CNUMCONTRATO,"+
                    "C.CUSOINSTALPORTUARIA, " +
                    "  CU.CSUPERFICIE,PC.ICVEPUERTO,OC.CCALLEYNO||', '|| OC.CCOLONIA|| " +
                    "  '. C.P. '|| OC.CCODPOSTAL||' '|| MO.CNOMBRE||', '|| EFO.CNOMBRE AS CDIROFIC, pc.CDSCPUERTO, " +
                    " SP.ICVECONTRATO, TI.ICVETITULAR "+
                    "FROM CYSSEGPROCEDIMIENTO SP " +
                    "JOIN RGCCONTRATO C ON SP.ICVECONTRATO = C.ICVECONTRATO " +
                    "JOIN GRLPERSONA P1 ON C.ICVECESIONARIO = P1.ICVEPERSONA " +
                    "JOIN CPATITULO T ON C.ICVETITULO = T.ICVETITULO " +
                    "JOIN CPATITULAR TI ON T.ICVETITULO = TI.ICVETITULO " +
                    "JOIN GRLPERSONA P2 ON TI.ICVETITULAR = P2.ICVEPERSONA " +
                    "JOIN RGCCONTRATOUBICACION CU ON C.ICVECONTRATO = CU.ICVECONTRATO " +
                    "LEFT JOIN GRLPUERTO PC ON CU.ICVEPUERTO = PC.ICVEPUERTO " +
                    "LEFT JOIN GRLOFICINA OC ON PC.ICVEOFICINAADSCRITA = OC.ICVEOFICINA " +
                    "LEFT JOIN GRLENTIDADFED EFO ON EFO.ICVEPAIS = OC.ICVEPAIS " +
                    "  AND EFO.ICVEENTIDADFED = OC.ICVEENTIDADFED " +
                    "LEFT JOIN GRLMUNICIPIO MO ON PC.ICVEPAIS = MO.ICVEPAIS " +
                    "  AND PC.ICVEENTIDADFED = MO.ICVEENTIDADFED " +
                    "  AND PC.ICVEMUNICIPIO = MO.ICVEMUNICIPIO "+
                    "Where SP.IEJERCICIO = " + iEjercicio +
                    "  AND SP.iMovProcedimiento = " + iMovProcedimiento;

                String cNotificacion =
                    "SELECT " +
                    "FP.CDIGITOSFOLIO,FP.ICONSECUTIVO,FP.IEJERCICIOFOL, " +
                    "F.DTASIGNACION, " +
                    "CN.TSFECHANOTIFICACION " +
                    "FROM CYSSEGPROCEDIMIENTO SP " +
                    "JOIN CYSFOLIOPROC FP ON SP.IEJERCICIO = FP.IEJERCICIO " +
                    "  AND SP.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
                    "  AND FP.LESPRORROGA <> 1 " +
                    "  AND FP.LESALEGATO <> 1 " +
                    "  AND FP.LESSINEFECTO <> 1 " +
                    "  AND FP.LESNOTIFICACION <> 1 " +
                    "  AND FP.LESRECORDATORIO <> 1 " +
                    "  AND FP.LESRESOLUCION <> 1 " +
                    "  AND FP.LESREVERSION <> 1 " +
                    "  AND FP.LESACTAADMVA <> 1 " +
                    "  AND FP.LESACTACIRCUNSTANCIADA <> 1 " +
                    "LEFT JOIN CYSNOTOFICIO CNO ON FP.IEJERCICIO = CNO.IEJERCICIO " +
                    "  AND FP.IMOVPROCEDIMIENTO = CNO.IMOVPROCEDIMIENTO " +
                    "  AND FP.IMOVFOLIOPROC = CNO.IMOVFOLIOPROC " +
                    "LEFT JOIN CYSCITANOTIFICACION CN ON CNO.IMOVCITANOTIFICACION = CN.IMOVCITANOTIFICACION " +
                    "LEFT JOIN GRLFOLIO F ON FP.IEJERCICIOFOL = F.IEJERCICIO " +
                    "  AND FP.ICVEOFICINA = F.ICVEOFICINA " +
                    "  AND FP.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO " +
                    "  AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
                    "  AND FP.ICONSECUTIVO = F.ICONSECUTIVO "+
                    "Where SP.IEJERCICIO = " + iEjercicio +
                    "  AND SP.iMovProcedimiento = " + iMovProcedimiento;

                String cProrroga =
                    "SELECT " +
                    "FP.CDIGITOSFOLIO,FP.ICONSECUTIVO,FP.IEJERCICIOFOL, " +
                    "F.DTASIGNACION, " +
                    "CN.TSFECHANOTIFICACION, " +
                    "PA.IDIASOTORGADOS " +
                    "FROM CYSSEGPROCEDIMIENTO SP " +
                    "JOIN CYSFOLIOPROC FP ON SP.IEJERCICIO = FP.IEJERCICIO " +
                    "  AND SP.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
                    "  AND FP.LESPRORROGA = 1 " +
                    "  AND FP.LESALEGATO <> 1 " +
                    "  AND FP.LESSINEFECTO <> 1 " +
                    "  AND FP.LESNOTIFICACION <> 1 " +
                    "  AND FP.LESRECORDATORIO <> 1 " +
                    "  AND FP.LESRESOLUCION <> 1 " +
                    "  AND FP.LESREVERSION <> 1 " +
                    "  AND FP.LESACTAADMVA <> 1 " +
                    "  AND FP.LESACTACIRCUNSTANCIADA <> 1 " +
                    "LEFT JOIN CYSNOTOFICIO CNO ON FP.IEJERCICIO = CNO.IEJERCICIO " +
                    "  AND FP.IMOVPROCEDIMIENTO = CNO.IMOVPROCEDIMIENTO " +
                    "  AND FP.IMOVFOLIOPROC = CNO.IMOVFOLIOPROC " +
                    "LEFT JOIN CYSCITANOTIFICACION CN ON CNO.IMOVCITANOTIFICACION = CN.IMOVCITANOTIFICACION " +
                    "LEFT JOIN GRLFOLIO F ON FP.IEJERCICIOFOL = F.IEJERCICIO " +
                    "  AND FP.ICVEOFICINA = F.ICVEOFICINA " +
                    "  AND FP.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO " +
                    "  AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
                    "  AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
                    "LEFT JOIN CYSPRORROGAALEGATO PA ON FP.IEJERCICIO = PA.IEJERCICIO " +
                    "  AND FP.IMOVPROCEDIMIENTO = PA.IMOVPROCEDIMIENTO " +
                    "  AND FP.IMOVFOLIOPROC = PA.IMOVFOLIOPROC " +
                    "Where SP.IEJERCICIO = " + iEjercicio +
                    "  AND SP.iMovProcedimiento = " + iMovProcedimiento+
                    " ORDER BY FP.IMOVFOLIOPROC, CNO.IMOVCITANOTIFICACION ";
                String cAlegato =
                    "SELECT " +
                    "fp.IEJERCICIO,fp.IMOVPROCEDIMIENTO,fp.IMOVFOLIOPROC,cn.IMOVCITANOTIFICACION, " +
                    "FP.CDIGITOSFOLIO,FP.ICONSECUTIVO,FP.IEJERCICIOFOL, " +
                    "F.DTASIGNACION, " +
                    "CN.TSFECHANOTIFICACION " +
                    "FROM CYSSEGPROCEDIMIENTO SP " +
                    "JOIN CYSFOLIOPROC FP ON SP.IEJERCICIO = FP.IEJERCICIO " +
                    "  AND SP.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
                    "  AND FP.LESPRORROGA <> 1 " +
                    "  AND FP.LESALEGATO = 1 " +
                    "  AND FP.LESSINEFECTO <> 1 " +
                    "  AND FP.LESNOTIFICACION <> 1 " +
                    "  AND FP.LESRECORDATORIO <> 1 " +
                    "  AND FP.LESRESOLUCION <> 1 " +
                    "  AND FP.LESREVERSION <> 1 " +
                    "  AND FP.LESACTAADMVA <> 1 " +
                    "  AND FP.LESACTACIRCUNSTANCIADA <> 1 " +
                    "LEFT JOIN CYSNOTOFICIO CNO ON FP.IEJERCICIO = CNO.IEJERCICIO " +
                    "  AND FP.IMOVPROCEDIMIENTO = CNO.IMOVPROCEDIMIENTO " +
                    "  AND FP.IMOVFOLIOPROC = CNO.IMOVFOLIOPROC " +
                    "LEFT JOIN CYSCITANOTIFICACION CN ON CNO.IMOVCITANOTIFICACION = CN.IMOVCITANOTIFICACION " +
                    "LEFT JOIN GRLFOLIO F ON FP.IEJERCICIOFOL = F.IEJERCICIO " +
                    "  AND FP.ICVEOFICINA = F.ICVEOFICINA " +
                    "  AND FP.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO " +
                    "  AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
                    "  AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
                    "Where SP.IEJERCICIO = " + iEjercicio +
                    "  AND SP.iMovProcedimiento = " + iMovProcedimiento+
                    " ORDER BY FP.IMOVFOLIOPROC, CNO.IMOVCITANOTIFICACION ";
		dObten.dFolio.setDatosFolio(cNumFolio);
		try{
			vProcedimiento = super.FindByGeneric("", cProcedimiento, dataSourceName);

		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}
                try{
                        vNotificacion = super.FindByGeneric("", cNotificacion, dataSourceName);

                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                }
                try{
                        vProrroga = super.FindByGeneric("", cProrroga, dataSourceName);

                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                }
                try{
                        vAlegato = super.FindByGeneric("", cAlegato, dataSourceName);

                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                }

		// Elaboración del Reporte.
		rep.iniciaReporte();
		if (vProcedimiento.size() > 0){
                  TVDinRep vcProcedimiento = (TVDinRep) vProcedimiento.get(0);

                  if(!vcProcedimiento.getString("DTREGISTRO").equals("null") && vcProcedimiento.getString("DTREGISTRO")!=null)
                    rep.comRemplaza("[cFechaRegistroContrato]",Fechas.getDateSPN(vcProcedimiento.getDate("DTREGISTRO")));
                  else rep.comRemplaza("[cFechaRegistroContrato]","_________________________________");

                  if(!vcProcedimiento.getString("DTCONTRATO").equals("null") && vcProcedimiento.getString("DTCONTRATO")!=null)
                    rep.comRemplaza("[cFechaContrato]",Fechas.getDateSPN(vcProcedimiento.getDate("DTCONTRATO")));
                  else rep.comRemplaza("[cFechaContrato]","_________________________________");

                  if(!vcProcedimiento.getString("CAPI").equals("null") && vcProcedimiento.getString("CAPI")!=null)
                    rep.comRemplaza("[cAPI]",vcProcedimiento.getString("CAPI"));
                  else rep.comRemplaza("[cAPI]","_________________________________");

                  if(!vcProcedimiento.getString("CNUMCONTRATO").equals("null") && vcProcedimiento.getString("CNUMCONTRATO")!=null)
                    rep.comRemplaza("[cNumContrato]",vcProcedimiento.getString("CNUMCONTRATO"));
                  else rep.comRemplaza("[cNumContrato]","_________________________________");

                  if(!vcProcedimiento.getString("CUSOINSTALPORTUARIA").equals("null") && vcProcedimiento.getString("CUSOINSTALPORTUARIA")!=null)
                    rep.comRemplaza("[cObjeto]",vcProcedimiento.getString("CUSOINSTALPORTUARIA"));
                  else rep.comRemplaza("[cObjeto]","_________________________________");

                  if(!vcProcedimiento.getString("CCESIONARIO").equals("null") && vcProcedimiento.getString("CCESIONARIO")!=null)
                    rep.comRemplaza("[cCesionario]",vcProcedimiento.getString("CCESIONARIO"));
                  else rep.comRemplaza("[cCesionario]","_________________________________");

                  if(!vcProcedimiento.getString("CDIROFIC").equals("null") && vcProcedimiento.getString("CDIROFIC")!=null)
                    rep.comRemplaza("[cRecinto]",vcProcedimiento.getString("CDIROFIC"));
                  else rep.comRemplaza("[cRecinto]","_________________________________");

                  if(!vcProcedimiento.getString("CDSCPUERTO").equals("null") && vcProcedimiento.getString("CDSCPUERTO")!=null)
                    rep.comRemplaza("[cRecinto]",vcProcedimiento.getString("CDSCPUERTO"));
                  else rep.comRemplaza("[cRecinto]","_________________________________");

                  if(!vcProcedimiento.getString("CSUPERFICIE").equals("null") && vcProcedimiento.getString("CSUPERFICIE")!=null)
                    rep.comRemplaza("[cSuperficie]",vcProcedimiento.getString("CSUPERFICIE"));
                  else rep.comRemplaza("[cSuperficie]","_________________________________");

                  if(!vcProcedimiento.getString("DTVENCIMIENTO").equals("null") && vcProcedimiento.getString("DTVENCIMIENTO")!=null  && !vcProcedimiento.getString("DTINIVIGENCIA").equals("null") && vcProcedimiento.getString("DTINIVIGENCIA")!=null)
                    rep.comRemplaza("[cVigencia]",""+(Fechas.getIntYear(vcProcedimiento.getDate("DTVENCIMIENTO"))-Fechas.getIntYear(vcProcedimiento.getDate("DTINIVIGENCIA"))));
                  else rep.comRemplaza("[cVigencia]","_________________________________");


                  iCveContrato = vcProcedimiento.getInt("ICVECONTRATO");
                  iCveTitular = vcProcedimiento.getInt("ICVETITULAR");
                } else {
                  rep.comRemplaza("[cFechaRegistroContrato]","____________________________________");
                  rep.comRemplaza("[cFechaContrato]",        "____________________________________");
                  rep.comRemplaza("[cAPI]",                  "____________________________________");
                  rep.comRemplaza("[cNumContrato]",          "____________________________________");
                  rep.comRemplaza("[cObjeto]",               "____________________________________");
                  rep.comRemplaza("[cCesionario]",           "____________________________________");
                  rep.comRemplaza("[cSuperficie]",           "____________________________________");
                  rep.comRemplaza("[cRecinto]",              "____________________________________");
                  rep.comRemplaza("[cVigencia]",             "____________________________________");
		}

                if(vNotificacion.size()>0){
                  TVDinRep vcNotificacion = (TVDinRep) vNotificacion.get(0);
                  TDGRLDiaNoHabil DNH = new TDGRLDiaNoHabil();

                  if(!vcNotificacion.getString("CDIGITOSFOLIO").equals("null") && vcNotificacion.getString("CDIGITOSFOLIO")!=null)
                    rep.comRemplaza("[cNumOficioInicioProcedimiento]",vcNotificacion.getString("CDIGITOSFOLIO")+"."+vcNotificacion.getInt("ICONSECUTIVO")+"/"+vcNotificacion.getInt("IEJERCICIOFOL"));
                  else rep.comRemplaza("[cNumOficioInicioProcedimiento]","_______._____/_______");

                  if(!vcNotificacion.getString("DTASIGNACION").equals("null") && vcNotificacion.getString("DTASIGNACION")!=null)
                    rep.comRemplaza("[cFechaInicioProcedimiento]",Fechas.getDateSPN(vcNotificacion.getDate("DTASIGNACION")));
                  else rep.comRemplaza("[cFechaInicioProcedimiento]","_________________________________");


                  if(!vcNotificacion.getString("TSFECHANOTIFICACION").equals("null") && vcNotificacion.getString("TSFECHANOTIFICACION")!=null)
                    rep.comRemplaza("[cFechaNotifInicioProc]",Fechas.getDateSPN(Fechas.getDateSQL( vcNotificacion.getTimeStamp("TSFECHANOTIFICACION"))));
                  else rep.comRemplaza("[cFechaNotifInicioProc]","_________________________________");

                  if(!vcNotificacion.getString("TSFECHANOTIFICACION").equals("null") && vcNotificacion.getString("TSFECHANOTIFICACION")!=null)
                    rep.comRemplaza("[cFechaInicioTranscurrida]",Fechas.getDateSPN(DNH.getFechaFinal(Fechas.getDateSQL( vcNotificacion.getTimeStamp("TSFECHANOTIFICACION")),1,false)));
                  else rep.comRemplaza("[cFechaInicioTranscurrida]","_________________________________");

                  if(!vcNotificacion.getString("TSFECHANOTIFICACION").equals("null") && vcNotificacion.getString("TSFECHANOTIFICACION")!=null)
                    rep.comRemplaza("[cFechaInicioComputo]",Fechas.getDateSPN(DNH.getFechaFinal(Fechas.getDateSQL( vcNotificacion.getTimeStamp("TSFECHANOTIFICACION")),1,false)));
                  else rep.comRemplaza("[cFechaInicioComputo]","_________________________________");

                  if(!vcNotificacion.getString("TSFECHANOTIFICACION").equals("null") && vcNotificacion.getString("TSFECHANOTIFICACION")!=null)
                    rep.comRemplaza("[cFechaFinComputo]",Fechas.getDateSPN(DNH.getFechaFinal(Fechas.getDateSQL( vcNotificacion.getTimeStamp("TSFECHANOTIFICACION")),16,false)));
                  else rep.comRemplaza("[cFechaFinComputo]","_________________________________");

                }
                else{
                  rep.comRemplaza("[cNumOficioInicioProcedimiento]","_______._____/_______");
                  rep.comRemplaza("[cFechaInicioProcedimiento]","____________________________");
                  rep.comRemplaza("[cFechaNotifInicioProc]","____________________________");
                  rep.comRemplaza("[cFechaInicioTranscurrida]","____________________________");
                }
                if(vProrroga.size()>0){
                  TVDinRep vcProrroga = (TVDinRep) vProrroga.get(0);

                  if(!vcProrroga.getString("CDIGITOSFOLIO").equals("null") && vcProrroga.getString("CDIGITOSFOLIO")!=null)
                    rep.comRemplaza("[cOficioProrroga]",vcProrroga.getString("CDIGITOSFOLIO")+"."+vcProrroga.getInt("ICONSECUTIVO")+"/"+vcProrroga.getInt("IEJERCICIOFOL"));
                  else rep.comRemplaza("[cOficioProrroga]","_______._____/_______");

                  if(!vcProrroga.getString("DTASIGNACION").equals("null") && vcProrroga.getString("DTASIGNACION")!=null)
                    rep.comRemplaza("[cFechaProrroga]",Fechas.getDateSPN(vcProrroga.getDate("DTASIGNACION")));
                  else rep.comRemplaza("[cFechaProrroga]","_________________________________");

                  if(!vcProrroga.getString("TSFECHANOTIFICACION").equals("null") && vcProrroga.getString("TSFECHANOTIFICACION")!=null)
                    rep.comRemplaza("[cFechaNotifProrroga]",Fechas.getDateSPN(Fechas.getDateSQL(vcProrroga.getTimeStamp("TSFECHANOTIFICACION"))));
                  else rep.comRemplaza("[cFechaNotifProrroga]","_________________________________");

                  if(!vcProrroga.getString("IDIASOTORGADOS").equals("null") && vcProrroga.getString("IDIASOTORGADOS")!=null)
                    rep.comRemplaza("[iDiasProrroga]",""+vcProrroga.getInt("IDIASOTORGADOS"));
                  else rep.comRemplaza("[iDiasProrroga]","_______");
                }
                else{
                  rep.comRemplaza("[cOficioProrroga]","_______._____/_______");
                  rep.comRemplaza("[cFechaProrroga]","____________________________");
                  rep.comRemplaza("[cFechaNotifProrroga]","____________________________");
                  rep.comRemplaza("[iDiasProrroga]","_______");
                }
                if(vAlegato.size()>0){
                  TVDinRep vcAlegato = (TVDinRep) vAlegato.get(0);

                  if(!vcAlegato.getString("CDIGITOSFOLIO").equals("null") && vcAlegato.getString("CDIGITOSFOLIO")!=null)
                    rep.comRemplaza("[cOficioAlegatos]",vcAlegato.getString("CDIGITOSFOLIO")+"."+vcAlegato.getInt("ICONSECUTIVO")+"/"+vcAlegato.getInt("IEJERCICIOFOL"));
                  else rep.comRemplaza("[cOficioAlegatos]","_______._____/_______");

                  if(!vcAlegato.getString("DTASIGNACION").equals("null") && vcAlegato.getString("DTASIGNACION")!=null)
                    rep.comRemplaza("[cFechaAlegatos]",Fechas.getDateSPN(vcAlegato.getDate("DTASIGNACION")));
                  else rep.comRemplaza("[cFechaAlegatos]","_________________________________");

                  if(!vcAlegato.getString("TSFECHANOTIFICACION").equals("null") && vcAlegato.getString("TSFECHANOTIFICACION")!=null)
                    rep.comRemplaza("[cFechaNotifAlegatos]",Fechas.getDateSPN(Fechas.getDateSQL(vcAlegato.getTimeStamp("TSFECHANOTIFICACION"))));
                  else rep.comRemplaza("[cFechaNotifAlegatos]","_________________________________");

                }
                if(vAlegato.size()>1){
                  TVDinRep vcAlegato = (TVDinRep) vAlegato.get(1);

                  if(!vcAlegato.getString("CDIGITOSFOLIO").equals("null") && vcAlegato.getString("CDIGITOSFOLIO")!=null)
                    rep.comRemplaza("[cOficioAlegatosOperadora]",vcAlegato.getString("CDIGITOSFOLIO")+"."+vcAlegato.getInt("ICONSECUTIVO")+"/"+vcAlegato.getInt("IEJERCICIOFOL"));
                  else rep.comRemplaza("[cOficioAlegatosOperadora]","_______._____/_______");

                  if(!vcAlegato.getString("DTASIGNACION").equals("null") && vcAlegato.getString("DTASIGNACION")!=null)
                    rep.comRemplaza("[cFechaAlegatosOperadora]",Fechas.getDateSPN(vcAlegato.getDate("DTASIGNACION")));
                  else rep.comRemplaza("[cFechaAlegatosOperadora]","_________________________________");

                  if(!vcAlegato.getString("TSFECHANOTIFICACION").equals("null") && vcAlegato.getString("TSFECHANOTIFICACION")!=null)
                    rep.comRemplaza("[cFechaNotifAlegatosOperadora]",Fechas.getDateSPN(Fechas.getDateSQL(vcAlegato.getTimeStamp("TSFECHANOTIFICACION"))));
                  else rep.comRemplaza("[cFechaNotifAlegatosOperadora]","_________________________________");

                }
                Vector vContratos = this.regresaContratosAnt(iCveContrato);
                String cDtRegistro  = "";
                String cDtContrato  = "";
                String cNumContrato = "";
                for (int i=0;i<vContratos.size();i++){
                  TVDinRep vcContratos = (TVDinRep) vContratos.get(i);
                  cDtRegistro  += " " + Fechas.getDateSPN(vcContratos.getDate("dtRegistro"));
                  cDtContrato  += " " + Fechas.getDateSPN(vcContratos.getDate("dtContrato"));
                  cNumContrato += " " + vcContratos.getString("cNumContrato");
                  if(i<(vcContratos.size() - 1)){
                    cDtRegistro  +=",";
                    cDtContrato  +=",";
                    cNumContrato +=",";
                  }
                }
                if(cDtRegistro!="") rep.comRemplaza("[cFechaRegConModif]",cDtRegistro);else rep.comRemplaza("[cFechaRegConModif]","___________________________");
                if(cDtContrato!="") rep.comRemplaza("[cFechaContratoModif]",cDtContrato);else rep.comRemplaza("[cFechaContratoModif]","___________________________");
                if(cNumContrato!="") rep.comRemplaza("[cNumContratoModif]",cNumContrato);else rep.comRemplaza("[cNumContratoModif]","___________________________");

		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				iCveTitular,1,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		TVDinRep vDinRep = new TVDinRep();
		vDinRep.put("iEjercicio",iEjercicio);
		vDinRep.put("iMovProcedimiento",iMovProcedimiento);
		vDinRep.put("iEjercicioFol",dObten.dFolio.getCveEjercicio());
		vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
		vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
		vDinRep.put("cDigitosFolio",dObten.dFolio.getCveDigitosFolio());
		vDinRep.put("iConsecutivo",dObten.dFolio.getCveConsecutivo());
		vDinRep.put("lEsProrroga",0);
		vDinRep.put("lEsAlegato",0);
		vDinRep.put("lEsSinEfecto",0);
		vDinRep.put("lEsNotificacion",0);
		vDinRep.put("lEsRecordatorio",0);
		vDinRep.put("lEsResolucion",1);

		try{
			vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return vRetorno;

	}


	public Vector generaSinEfectos(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");

		int iCveOficinaDest=0,iCveDeptoDest=0;
		String cAsunto="";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio = Integer.parseInt(aFiltros[0]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[1]);
		String cSQL =
                    "SELECT " +
                    "FP.CDIGITOSFOLIO,FP.ICONSECUTIVO,FP.IEJERCICIO, " +
                    "P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CAPI, " +
                    "C.CNUMCONTRATO,CN.TSFECHANOTIFICACION " +
                    "FROM CYSSEGPROCEDIMIENTO SP " +
                    "JOIN CYSSEGPROCEDIMIENTO SPA ON SP.IEJERCICIOANT = SPA.IEJERCICIO " +
                    "                            AND SP.IMOVPROCEDIMIENTOANT = SPA.IMOVPROCEDIMIENTO " +
                    "JOIN RGCCONTRATO C ON SP.ICVECONTRATO = C.ICVECONTRATO " +
                    "JOIN GRLPERSONA P ON C.ICVECESIONARIO = P.ICVEPERSONA " +
                    "LEFT JOIN CYSFOLIOPROC FP ON SP.IEJERCICIO = FP.IEJERCICIO " +
                    "                         AND SP.IMOVPROCEDIMIENTO = FP.IMOVPROCEDIMIENTO " +
                    "                         AND FP.LESPRORROGA <> 1 " +
                    "                         AND FP.LESALEGATO <> 1 " +
                    "                         AND FP.LESSINEFECTO <> 1 " +
                    "                         AND FP.LESNOTIFICACION <> 1 " +
                    "                         AND FP.LESRECORDATORIO <> 1 " +
                    "                         AND FP.LESRESOLUCION <> 1 " +
                    "                         AND FP.LESREVERSION <> 1 " +
                    "                         AND FP.LESACTAADMVA <> 1 " +
                    "                         AND FP.LESACTACIRCUNSTANCIADA <> 1 " +
                    "JOIN GRLFOLIO F ON FP.IEJERCICIO = F.IEJERCICIO " +
                    "               AND FP.ICONSECUTIVO = F.ICONSECUTIVO " +
                    "               AND FP.ICVEOFICINA = F.ICVEOFICINA " +
                    "               AND FP.ICVEDEPARTAMENTO  = F.ICVEDEPARTAMENTO " +
                    "               AND FP.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
                    "LEFT JOIN CYSNOTOFICIO CNO ON FP.IEJERCICIO = CNO.IEJERCICIO " +
                    "                     AND FP.IMOVFOLIOPROC = CNO.IMOVFOLIOPROC " +
                    "                     AND FP.IMOVPROCEDIMIENTO = CNO.IMOVPROCEDIMIENTO " +
                    "LEFT JOIN CYSCITANOTIFICACION CN ON CNO.IMOVCITANOTIFICACION = CN.IMOVCITANOTIFICACION "+
                    "WHERE SP.IEJERCICIO = "+iEjercicio+
                    " AND SP.IMOVPROCEDIMIENTO = "+iMovProcedimiento;

		dObten.dFolio.setDatosFolio(cNumFolio);
		try{
			vRegs = super.FindByGeneric("", cSQL, dataSourceName);
		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		rep.iniciaReporte();
		if (vRegs.size() > 0){
                  TVDinRep sinEfectos = (TVDinRep) vRegs.get(0);
                  if(!sinEfectos.getString("TSFECHANOTIFICACION").equals("null") && sinEfectos.getString("TSFECHANOTIFICACION")!=null)rep.comRemplaza("[cFechaNotifOficioRevocacion]",Fechas.getDateSPN(Fechas.getDateSQL(sinEfectos.getTimeStamp("TSFECHANOTIFICACION"))));else rep.comRemplaza("[cFechaNotifOficioRevocacion]","____________________________");
                  if(!sinEfectos.getString("CDIGITOSFOLIO").equals("null") && sinEfectos.getString("CDIGITOSFOLIO")!=null)rep.comRemplaza("[cNumOficioRevocacion]",sinEfectos.getString("CDIGITOSFOLIO")+"."+sinEfectos.getString("ICONSECUTIVO")+"/"+sinEfectos.getString("IEJERCICIO"));else rep.comRemplaza("[cNumOficioRevocacion]","____________________________");
                  if(!sinEfectos.getString("CNUMCONTRATO").equals("null") && sinEfectos.getString("CNUMCONTRATO")!=null)rep.comRemplaza("[cNumContrato]",sinEfectos.getString("CNUMCONTRATO"));else rep.comRemplaza("[cNumContrato]","____________________________");
                  if(!sinEfectos.getString("CAPI").equals("null") && sinEfectos.getString("CAPI")!=null)rep.comRemplaza("[cAPI]",sinEfectos.getString("CAPI"));else rep.comRemplaza("[cAPI]","____________________________");
		} else {
			//Sin Valores en la Base.
		}
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				0,0,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		TVDinRep vDinRep = new TVDinRep();
		vDinRep.put("iEjercicio",iEjercicio);
		vDinRep.put("iMovProcedimiento",iMovProcedimiento);
		vDinRep.put("iEjercicioFol",dObten.dFolio.getCveEjercicio());
		vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
		vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
		vDinRep.put("cDigitosFolio",dObten.dFolio.getCveDigitosFolio());
		vDinRep.put("iConsecutivo",dObten.dFolio.getCveConsecutivo());
		vDinRep.put("lEsProrroga",0);
		vDinRep.put("lEsAlegato",0);
		vDinRep.put("lEsSinEfecto",1);
		vDinRep.put("lEsNotificacion",0);
		vDinRep.put("lEsRecordatorio",0);
		vDinRep.put("lEsResolucion",0);

		try{
			vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return vRetorno;

	}

	/**
	 * Método que regresa en un vector de TVDInRep datos de modificaciones a un contrato
	 * @param iCveContrato
	 * @return
	 * Autor: iCaballero
	 */
	public Vector regresaContratosAnt(int iCveContrato){
		TVDinRep vData = new TVDinRep();
		Vector vRegresa = new Vector();

		vData = this.consultaContratosAnt(iCveContrato);
		vRegresa.add(vData);
		while(vData.getInt("ICVECONTRATOANT") > 0){
			vData = this.consultaContratosAnt(vData.getInt("ICVECONTRATOANT"));
			vRegresa.add(vData);
		}

		return vRegresa;
	}

	public TVDinRep consultaContratosAnt(int iCveContrato){
		TVDinRep vRegresa = new TVDinRep();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("ICVECONTRATOANT, "); //0
		sb.append("DTREGISTRO, ");
		sb.append("DTCONTRATO, ");
		sb.append("CNUMCONTRATO, ");
		sb.append("PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CNOMBRE, ");
		sb.append("TPO.CDSCTIPOCONTRATO AS CTIPOCONTRATO, "); //5
		sb.append("CUSOINSTALPORTUARIA AS COBJETO, ");
		sb.append("(YEAR(DTVENCIMIENTO) - YEAR(DTINIVIGENCIA)) AS CVIGENCIA, ");
		sb.append("COBSCONTRATO AS COBSERVACION, ");
		sb.append("CNUMCONVOCATORIA, ");
		sb.append("ICVECONTRATO "); //10
		sb.append("FROM RGCCONTRATO ");
		sb.append("LEFT JOIN RGCTIPOCONTRATO TPO ON RGCCONTRATO.ICVETIPOCONTRATO = TPO.ICVETIPOCONTRATO ");
		sb.append("LEFT JOIN GRLPERSONA PER ON RGCCONTRATO.ICVECESIONARIO = PER.ICVEPERSONA ");
		sb.append("where RGCCONTRATO.ICVECONTRATO =  " + iCveContrato);
		Vector vData = new Vector();
		try {
			vData = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(vData.size() > 0)
			vRegresa = (TVDinRep) vData.get(0);

		return vRegresa;
	}



}

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
 * @author ABarrientos
 * @version 1.0
 */

public class TDCYSDerFiscales extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private TFechas    tFecha      = new TFechas("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
	private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
	private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
	private int iEjercicioOficio = 0;
	private String cDigitosFolio = "";
	private int iConsecutivo=0;
	public TDCYSDerFiscales(){
	}


	//public Vector genReversionPFP(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
	public Vector derechosFiscales(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");
		TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
		TDCYSDiligenciaReversion dCYSDilRev = new TDCYSDiligenciaReversion();
		TVDinRep vRegDilig = new TVDinRep();

		int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0,iAniosDuracion=0;
		String cObjetoTitulo="";
		String cTitular="", cUsoTitulo="", cFechaNotif="", cPropiedad="", cComisionados="";
		String cEntidad="", cTipoTitulo="",cPublicDOF="",cAsunto="Se remite información y documentación sobre adeudo de derechos fiscales ", cAreaSolic="", cMunEntTit="", cPaisEntTit="";
		String[] aFiltros = cQuery.split(","), aComisionados=null;
		int iEjercicio = Integer.parseInt(aFiltros[1],10), iMovProcedimiento = Integer.parseInt(aFiltros[2],10),
		iMovFolioProc = Integer.parseInt(aFiltros[4],10);
		StringTokenizer st = null;

		String cMinuto = "";
		int iCveTitulo = Integer.parseInt(aFiltros[0]);
		//java.sql.Timestamp tsDiligencia = Fechas.getTimeStampTC(aFiltros[1]);
		Vector vNotifRec = new Vector();
		this.setDatosFolio(cNumFolio);


		String cSQL = "select CPATitulo.iCveTitulo, " +
		"GRLPersona.iTipoPersona, " +
		"GRLPersona.cNomRazonSocial, " +
		"GRLPersona.cApPaterno, " +
		"GRLPersona.cApMaterno, " +
		"GRLPersona.cRFC, " +
		"CPAActaNacimiento.cNumActaNacimiento, " +
		"CPAActaNacimiento.dtNacimiento, " +
		"CPAActaNacimiento.cNumJuzgado, " +
		"Juez.cNomRazonSocial cNomJuez, " +
		"Juez.cApPaterno cApPatJuez, " +
		"Juez.cApMaterno cApMatJuez, " +
		"CPAActaNacimiento.cNumLibroActa, " +
		"CPAActaNacimiento.cNumFojaActa, " +
		"GRLDomicilio.cCalle, " +
		"GRLDomicilio.cNumExterior, " +
		"GRLDomicilio.cNumInterior, " +
		"GRLDomicilio.cColonia, " +
		"GRLDomicilio.cCodPostal, " +
		"GRLDomicilio.cTelefono, " +
		"GRLPais.cNombre as cNomPais, " +
		"GRLEntidadFed.cNombre as cNomEntidadFed, " +
		"GRLMunicipio.cNombre as cNomMunicipio, " +
		"GRLLocalidad.cNombre as cNomLocalidad, " +
		"CPATitulo.cNumTitulo, " +
		"CPATitulo.cNumTituloAnterior, " +
		"CPATitulo.dtIniVigenciaTitulo, " +
		"CPATitulo.cZonaFedAfectadaTerrestre, " +
		"CPATitulo.cZonaFedAfectadaAgua, " +
		"CPATitulo.cObjetoTitulo, " +
		"CPATitulo.cMontoInversion, " +
		"CPATitulo.dtPublicacionDOF, " +
		"CPATitulo.iCveClasificacion, " +
		"CPAUsoTitulo.iCveUsoTitulo, " +
		"CPAUsoTitulo.cDscUsoTitulo, " +
		"CPATitulo.iCveTituloAnterior, " +
		"CPATitulo.dtVigenciaTitulo, " +
		"CPATitulo.iCveTipoTitulo, "+
		"CPATitulo.iPropiedad, "+
		"CPATituloUbicacion.cCalleTitulo, " +
		"CPATituloUbicacion.cKm, " +
		"CPATituloUbicacion.cColoniaTitulo, " +
		"entTitUbic.cNombre as cEntidadTitUbic, " +
		"munTitUbi.cNombre as cMunicipioTitUbic, "+
		"PaisTitUbic.cNombre AS cPaisTitUbi "+
		"FROM CPATitulo " +
		"join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
		"join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
		"left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
		"left join GRLPais PaisTitUbic on PaisTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
		"left join GRLEntidadFed entTitUbic on entTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
		"and entTitUbic.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
		"left join GRLMunicipio munTitUbi on munTitUbi.iCvePais = CPATituloUbicacion.iCvePais " + //--
		"and munTitUbi.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " + //--
		"and munTitUbi.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " + //--
		"join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo " +
		"join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
		"left join CPAActaNacimiento on CPAActaNacimiento.iCvePersona = GRLPersona.iCvePersona " +
		"left join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
		"and GRLDomicilio.lPredeterminado = 1 " +
		"left join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
		"left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
		"and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
		"left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
		"and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
		"and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
		"left join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
		"and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
		"and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
		"and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
		"left join GRLPersona Juez on Juez.iCvePersona = CPAActaNacimiento.iCveJuez " +
		"where CPATitulo.iCveTitulo = " + iCveTitulo;


		// Query de Consulta del Reporte.
		try{
			vRegs = super.FindByGeneric("", cSQL, dataSourceName);

		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		Vector vOficio = new Vector();
		try{
			vOficio = dSegPRoc.findByOficioNotRec(Integer.parseInt(aFiltros[1],10),Integer.parseInt(aFiltros[2],10),0);
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		if (vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			String cPseudonimoTitular = "";
			//Identificación que se trata de Concesión.
			if (vDatos.getInt("iCveTipoTitulo") == 1){
				cPseudonimoTitular  = "el Concesionario";
				cTipoTitulo = "la Concesión";
			}
			//Identificación que se trata de Permiso.
			if (vDatos.getInt("iCveTipoTitulo") == 2){
				cPseudonimoTitular = "el Concesionario";
				cTipoTitulo = "el Permiso";
			}

//			Identificación que se trata de Autorización
			if (vDatos.getInt("iCveTipoTitulo") == 3){
				cPseudonimoTitular = "el Concesionario";
				cTipoTitulo = "la Autorización";
			}

			rep.comRemplaza("[cPseudonimoTitular]",cPseudonimoTitular);

			/*
			 * Obtener los días de sanción
			 */

			StringBuffer sbValidaMovProc = new StringBuffer();
			sbValidaMovProc.append("SELECT ")
			.append("seg.ICVEPROCEDIMIENTO as iCveProcedimiento, ")
			.append("case char(ICVEPROCEDIMIENTO) ")
			.append("when '2' then seg.IMOVPROCEDIMIENTO ")
			.append("when '4' then ")
			.append("(select segAntAnt.IMOVPROCEDIMIENTO from CYSSEGPROCEDIMIENTO segAnt join CYSSEGPROCEDIMIENTO segAntAnt ")
			.append("on segAnt.IEJERCICIOANT = segAntAnt.IEJERCICIO and segAnt.IMOVPROCEDIMIENTOANT = segAntAnt.IMOVPROCEDIMIENTO ")
			.append("where segAnt.IEJERCICIO = seg.iEjercicioAnt and segAnt.IMOVPROCEDIMIENTO = seg.iMovProcedimientoAnt) ")
			.append(" end iMovProcedimientoFinal, ")
			.append("case char(ICVEPROCEDIMIENTO) ")
			.append("when '2' then seg.iejercicio ")
			.append("when '4' then ")
			.append("(select segAntAnt.IEjercicio from CYSSEGPROCEDIMIENTO segAnt join CYSSEGPROCEDIMIENTO segAntAnt ")
			.append("on segAnt.IEJERCICIOANT = segAntAnt.IEJERCICIO and segAnt.IMOVPROCEDIMIENTOANT = segAntAnt.IMOVPROCEDIMIENTO ")
			.append("where segAnt.IEJERCICIO = seg.iEjercicioAnt and segAnt.IMOVPROCEDIMIENTO = seg.iMovProcedimientoAnt) ")
			.append(" end iEjercicioFinal ")
			.append("FROM CYSSEGPROCEDIMIENTO seg ")
			.append("where seg.IEJERCICIO = " + iEjercicio)
			.append(" and seg.IMOVPROCEDIMIENTO = " + iMovProcedimiento);

			Vector vValidaMovProced = new Vector();
			int iMovProcedimientoFinal = 0, iEjercicioFinal = 0;
			try {
				vValidaMovProced = super.FindByGeneric("", sbValidaMovProc.toString(), dataSourceName);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			TVDinRep tvValidaMovProce = new TVDinRep();
			if(vValidaMovProced.size() > 0){
				tvValidaMovProce = (TVDinRep) vValidaMovProced.get(0);
				iMovProcedimientoFinal = tvValidaMovProce.getInt("iMovProcedimientoFinal");
				iEjercicioFinal = tvValidaMovProce.getInt("iEjercicioFinal");
			}

			StringBuffer sbDiasSan = new StringBuffer();
			sbDiasSan.append("SELECT ")
			.append("DTREGISTRO, DTSANCION, IDIASSMGDF ")
			.append("FROM CYSSANCIONADMVA ")
			.append("WHERE IEJERCICIO = " + iEjercicioFinal)
			.append(" AND IMOVPROCEDIMIENTO = " + iMovProcedimientoFinal)
			.append(" order by DTREGISTRO desc ");

			Vector vDiasSan = new Vector();
			try {
				vDiasSan = super.FindByGeneric("", sbDiasSan.toString(), dataSourceName);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			int iDiasSan=0;
			if(vDiasSan.size() > 0){
				TVDinRep tvDiasSan = (TVDinRep) vDiasSan.get(0);
				iDiasSan = tvDiasSan.getInt("IDIASSMGDF");
			}
			if(iDiasSan > 0)
				rep.comRemplaza("[iDiasSancion]", String.valueOf(iDiasSan));
			else
				rep.comRemplaza("[iDiasSancion]", "___");

			rep.comRemplaza("[cAnioActual]", tFecha.getStringYear(tFecha.TodaySQL()));


			if(vNotifRec.size()>0){
				TVDinRep vDinNotifRec = new TVDinRep();
				vDinNotifRec = (TVDinRep) vNotifRec.get(0);
				rep.comRemplaza("[cFolio]",vDinNotifRec.getString("cDigitosFolio")+"."+vDinNotifRec.getString("iConsecutivo")+"/"+vDinNotifRec.getString("iEjercicioFol"));
				if(vDinNotifRec.getDate("dtAsignacion")!=null)rep.comRemplaza("[cFechaFolio]",Fechas.getDateSPN(vDinNotifRec.getDate("dtAsignacion")));
				else rep.comRemplaza("[cFechaFolio]","___/________/_____");

				if(vDatos.getDate("dtRecAsignacion")!=null)
					cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtRecAsignacion"));
				else if(vDinNotifRec.getDate("dtNorAsignacion")!=null)
					cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtNorAsignacion"));
				else
					cFechaNotif = "___/________/_____";
				rep.comRemplaza("[cFechaNotif]",cFechaNotif);
			}
			else{
				rep.comRemplaza("[cFolio]"," ");
				rep.comRemplaza("[cFechaFolio]","___/________/_____");
				rep.comRemplaza("[cFechaNotif]","___/________/_____");
			}


			//Identificación que se trata de Autorizacion.
			if (vDatos.getInt("iCveTipoTitulo") == 3){
				cTipoTitulo = "la Autorización";
			}

			rep.comRemplaza("[cTipoTitulo]",cTipoTitulo);
			rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));

			cTitular = vDatos.getString("cNomRazonSocial")+" "+vDatos.getString("cApPaterno")+" "+vDatos.getString("cApMaterno");

			st = new StringTokenizer(cTitular);
			cTitular="";

			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				if(cToken.indexOf("S.A.")==-1 && cToken.indexOf("C.V.")==-1)
					cTitular += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
				else
					cTitular += " " + cToken;
			}

			rep.comRemplaza("[cTitular]",cTitular);// cCalle cNumExterior cColonia cCodPostal cNomPais cNomEntidadFed cNomMunicipio cNomLocalidad
			rep.comRemplaza("[cDirTitular]",vDatos.getString("cCalle")+" "+vDatos.getString("cNumExterior")+", Col. "+vDatos.getString("")+", C.P.:"+vDatos.getString("cCodPostal"));
			rep.comRemplaza("[cRFCTitular]",vDatos.getString("cRFC"));
			if(vDatos.getDate("dtIniVigenciaTitulo")!=null)
				cPublicDOF = Fechas.getDateSPN(vDatos.getDate("dtIniVigenciaTitulo"));
			else
				cPublicDOF = "___/________/_____";
			rep.comRemplaza("[dtIniVigenciaTitulo]",cPublicDOF);


			cObjetoTitulo =  vDatos.getString("cObjetoTitulo");
			st = new StringTokenizer(cObjetoTitulo);
			cObjetoTitulo="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cObjetoTitulo += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}

			TDCPATitulo1 T1 = new TDCPATitulo1();
			Vector vcUbicacion = new Vector();
			try{
				vcUbicacion = T1.findByInfTituloUbicacion(iCveTitulo);
			} catch(Exception e){
				e.printStackTrace();
			}
			if(vcUbicacion.size()>0){
				TVDinRep vUbicacion = (TVDinRep) vcUbicacion.get(0);
				String puerto = vUbicacion.getString("cDscPuerto");
				st = new StringTokenizer(puerto);
				puerto="";
				while (st.hasMoreTokens()) {
					String cToken = st.nextToken();
					puerto += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
				}
				rep.comRemplaza("[cPuerto]",puerto);
			}
			else rep.comRemplaza("[cPuerto]","___________________");

			rep.comRemplaza("[cObjetoTitulo]",cObjetoTitulo);

			if(vDatos.getString("cDscUsoTitulo").indexOf("USO")==-1)
				cUsoTitulo = "uso "+vDatos.getString("cDscUsoTitulo").toLowerCase();
			else
				cUsoTitulo = vDatos.getString("cDscUsoTitulo").toLowerCase();
			rep.comRemplaza("[cUsoTitulo]",cUsoTitulo);

			if(Integer.parseInt(vDatos.getString("iPropiedad"))==1)
				cPropiedad = "Propiedad Nacional";
			else if(Integer.parseInt(vDatos.getString("iPropiedad"))==2)
				cPropiedad = "Propiedad Particular";
			else
				cPropiedad = "";

			rep.comRemplaza("[cPropiedad]",""+cPropiedad);

			cAreaSolic = vDatos.getString("cCalleTitulo")+" "+vDatos.getString("cKm")+" "+vDatos.getString("cColoniaTitulo");
			st = new StringTokenizer(cAreaSolic);
			cAreaSolic="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cAreaSolic += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}


			rep.comRemplaza("[cAreaSolic]",""+cAreaSolic);

			st = new StringTokenizer(vDatos.getString("cEntidadTitUbic"));
			cEntidad="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cEntidad += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}

			st = new StringTokenizer(vDatos.getString("cMunicipioTitUbic"));
			cMunEntTit="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cMunEntTit += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}

			st = new StringTokenizer(vDatos.getString("cPaisTitUbi"));
			cPaisEntTit="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cPaisEntTit += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}

			rep.comRemplaza("[cMunicipioTit]",cMunEntTit);
			rep.comRemplaza("[cEntidadTit]",cEntidad);
			rep.comRemplaza("[cPaisTit]",cPaisEntTit);

			rep.comRemplaza("[cEntidadFed]",cEntidad);
			iAniosDuracion = Fechas.getDaysBetweenDates(vDatos.getDate("dtIniVigenciaTitulo"),vDatos.getDate("dtVigenciaTitulo"));
			iAniosDuracion = iAniosDuracion/365;
			rep.comRemplaza("[cAniosDuracion]",""+iAniosDuracion);

		} else {
			//Sin Valores en la Base.



		}
		if(vOficio.size() > 0){
			TVDinRep vcOficio = (TVDinRep) vOficio.get(0);
			rep.comRemplaza("[cOficio]",vcOficio.getString("cDigitosFolio")+"."+vcOficio.getString("iConsecutivo")+"/"+vcOficio.getString("iEjercicioFol"));
			rep.comRemplaza("[dtAsignacionOficio]",Fechas.getDateSPN(vcOficio.getDate("dtAsignacion")));
		}
		//return ;
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
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
		//vDinRep.put("iMovFolioProc",iMovFolioProc);
		vDinRep.put("iEjercicioFol",iEjercicioOficio);
		vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
		vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
		vDinRep.put("cDigitosFolio",cDigitosFolio);
		vDinRep.put("iConsecutivo",iConsecutivo);
		vDinRep.put("lEsProrroga",1);
		vDinRep.put("lEsAlegato",1);
		vDinRep.put("lEsSinEfecto",1);
		vDinRep.put("lEsNotificacion",1);
		vDinRep.put("lEsRecordatorio",1);
		vDinRep.put("lEsResolucion",1);

		try{
			vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

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
}

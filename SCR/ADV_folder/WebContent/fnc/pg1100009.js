// MetaCD=1.0
// Title: pg115010030
// Description: Consultas Generales y por área
// Company: Secretaría de Comunicaciones y Electrónica
// Author: Itandehui Ortiz Celis
var cTitulo = "";
var FRMListado = "";
var frm;
var cDeptosPuertos = "4,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,"
		+ "26,27,29,30,31,32,34,37,38,39,40,42,43,44,114,115,116,117,"
		+ "118,119";
var cGrupoLicencias = "353,354,355,356,357,358,359,360,365";

var lEjecutaEncuesta = 0;
var aEstado = new Array();
var idGpo = -1;
aEstado[0] = [ 0, "Pendientes en Tiempo" ];
aEstado[1] = [ 1, "Pendientes Fuera de Tiempo" ];
aEstado[2] = [ 2, "Terminadas" ];
// aEstado[0] = [0,"Cambio reciente"];
fWrite(JSSource("Carpetas.js"));
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg1100009.js";
	if (top.fGetTituloPagina) {
		;
		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	}
	fSetWindowTitle();
	idGpo = top.usrGpoId;
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJSIni);
	InicioTabla("", 0, "100%", "", "", "", "1");
	FTDTR();
	InicioTabla("", 0, "100%", "", "center", "bottom", ".1", ".1");
	ITRTD("", "", "", "", "center");
	InicioTabla("", 0, "", "", "center");
	ITRTD("", "", "100", "", "center", "bottom");
	Img("inicioADV.jpg", "ADV - Página de Inicio del Sistema");
	FTDTR
	FinTabla();
	// DinTabla("TDMarquee","",0,"95%","","center");
	FTDTR();
	FinTabla();
	/*
	 * InicioTabla("",0,"95%","","center","top");
	 * ITRTD("",1,"100","","center","top");
	 * 
	 * ITRTD("ESTitulo",0,"100%","","center","top"); //Un comentario perdido =D
	 * TextoSimple("Ligas de interes"); FTDTR();
	 * 
	 * InicioTabla("",0,"95%","","center","top"); ITR();
	 * ITD("",0,"","","center"); Liga("[Encuesta de Satisfacción del
	 * Sistema]","fAbreEncuesta();","Buscar","lEncuesta");
	 * FITD("",0,"","","center");
	 * 
	 * FTR(); FinTabla();
	 */
	InicioTabla("", 0, "95%", "", "center", "top");
	ITRTD("", 1, "100", "", "center", "top");

	ITRTD("ESTitulo", 0, "100%", "", "center", "top");

	TextoSimple("Ligas de Interés");

	FTDTR();
	InicioTabla("", 0, "95%", "", "center", "top");
	// Liga("Conceptos
	// Generales","fAbreWindowHTML(false,cRutaAyuda+'ManualGen.pdf','yes','yes','yes','yes','no',750,580,10,10);","Buscar");
	switch (parseInt(idGpo)) {
	case 5:// dgdc
		ITR();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Cambiar contraseña", "fCambiaContraADV()", "");
		FTD();
//		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
//		Liga("Descargar Formato Solicitud", "fFormatoSol()", "");
//		FTD();
		ITD("", 0, "", "", "center");
		Liga("Manual de DGDC-DPA", "fDGDC()", "");
		FTD();
		FTR();
		break;

	case 4:// tec
		ITR();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Cambiar contraseña", "fCambiaContraADV()", "");
		FTD();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Manual de DGST", "fTecnico()", "");
		FTD();
		FTR();
		break;

	case 3:// jurid
		ITR();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Cambiar contraseña", "fCambiaContraADV()", "");
		FTD();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Manual de DGDC-DAJL", "fJurid()", "");
		FTD();
		FTR();
		break;

	case 2:// csct
		ITR();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Cambiar contraseña", "fCambiaContraADV()", "");
		FTD();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Descargar Formato Solicitud", "fFormatoSol()", "");
		FTD();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Manual de CSCT", "fCSCT()", "");
		FTD();
		FTR();
		break;
	case 1:// admin
		ITR();
		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
		Liga("Cambiar contraseña", "fCambiaContraADV()", "");
		FTD();
		FTR();
		break;
	}

	FTR();
	FTR();
	FinTabla();
	FinTabla();

	FinTabla();

	ITRTD("cColorGenJSFolder", 0, "", "310", "", "");

	Hidden("iCveSistema", 44);
	Hidden("Modulo", 30);
	Hidden("hdSelect");
	Hidden("hdLlave");
	Hidden("cSolicitante");
	fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
}

function fSolicitudes() {
	if (frm.dtInicioTra.value == "" || frm.dtFinTra.value == "") {
		fAlert("Los valores del Filtro de Trámites son requeridos");
	} else {
		frm.hdLlave.value = "";
		frm.hdNumReg.value = 1000;
		frm.hdFiltro.value = "E1.TSREGISTRO BETWEEN '"
				+ fGetFechaSQL(frm.dtInicioTra.value) + "' AND '"
				+ fGetFechaSQL(frm.dtFinTra.value) + "' AND e2.ICVEOFICINA="
				+ frm.iCveOficina.value + " AND e2.ICVEDEPARTAMENTO="
				+ frm.iCveDepartamento.value;

		if (frm.iEstado.value == 0)
			frm.hdFiltro.value += " AND E8.TSREGISTRO IS NULL AND {fn NOW()}<=S.DTESTIMADAENTREGA";
		if (frm.iEstado.value == 1)
			frm.hdFiltro.value += " AND E8.TSREGISTRO IS NULL AND {fn NOW()} > S.DTESTIMADAENTREGA";
		if (frm.iEstado.value == 2)
			frm.hdFiltro.value += " AND E8.TSREGISTRO IS NOT NULL AND E8.IORDEN <> 2";

		fEngSubmite("pgTRASemaforo.jsp", "Listado");
	}
}

// RECEPCIoN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave) {

	if (cError == "Guardar")
		fAlert("Existió un error en el Guardado!");
	if (cError == "Borrar")
		fAlert("Existió un error en el Borrado!");
	if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	}
	if (cError != "")
		fAlert(cError);

	if (cId == "Listado" && cError == "") {
		frm.hdRowPag.value = iRowPag;
		FRMListado.fSetListado(aRes);
		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
		fPagFolder(1);
	}

	if (cId == "idFechaActual" && cError == "") {
		frm.dtInicioTra.value = aRes[0];
		frm.dtFinTra.value = aRes[0];
		fOficinas();
	}

	if (cId == "IDMen" && cError == "") {
		for (i = 0; tCpoMarquee.rows.length; i++) {
			tCpoMarquee.deleteRow(0);
		}
		cMrq = "";
		for (i = 0; i < aRes.length; i++) {
			aTmp = aRes[i];
			cMrq += aTmp[2] + " - " + aTmp[3] + " \n<br>";
		}
		newRow = tCpoMarquee.insertRow(0);
		newCell = newRow.insertCell(0);
		newCell.innerHTML = Marquee(cMrq, "MRQ", "center", "10", "up", "100%",
				"100");
		fTraeFechaActual();
	}
	if (cId == "cIdOficina" && cError == "") {
		alert(aRes);
		if (parseInt(aRes.length, 10) > 0) {
			fFillSelect(frm.iCveOficina, aRes, false, 0, 0, 1);
			fDepartamento();
		} else {
			fEsUsuarioLicencias();
		}
	}
	if (cId == "cIdDepartamento" && cError == "") {
		fFillSelect(frm.iCveDepartamento, aRes, false, 0, 0, 1);
		fEsUsrPuertos();
	}

	if (cId == "cIdDeptosPuertos" && cError == "") {
		if (parseInt(aRes.length, 10) == 0) {
			lEjecutaEncuesta = 0;
			fGO("lEncuesta").disabled = true;
			fGO("lEncuesta").style.innerHTML = "blank";
		} else
			lEjecutaEncuesta = 1;

	}

	if (cId == "cIdGrupoLicencias" && cError == "") {
		/*
		 * if(parseInt(aRes.length,10)>0){ lEjecutaEncuesta=1;
		 * fGO("lEncuesta").disabled = false; } else lEjecutaEncuesta=0;
		 */
	}

	if (cId == "cIdUsername" && cError == ""){
		open(
				cRutaADMSEGPWD + '?iCveUsuario=' + aRes[0][0] + '&cUsuario='
						+ aRes[0][1],
				'',
				'dependent=yes,hotKeys=no,location=no,menubar=no,personalbar=no,resizable=no,scrollbars=no,status=yes,titlebar=no,toolbar=no,width=900,height=590,screenX=50,screenY=50');

		    fEngSubmite("pgDelUsrSes.jsp","CerrarSesion");
		    fSalirTotal();
	}

}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fBlanked();
	fDisabled(false, "iConsecutivSenal,", "--");
	FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fDisabled(false, "iConsecutivSenal,");
	FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar() {
	if (FRMListado.fGetLength() > 0)
		FRMPanel.fSetTraStatus("UpdateComplete");
	else
		FRMPanel.fSetTraStatus("AddOnly");
	fDisabled(true);
	FRMListado.fSetDisabled(false);
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato) {
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo() {
	cMsg = fValElements();

	if (cMsg != "") {
		fAlert(cMsg);
		return false;
	}
	return true;
}
function fImprimir() {
	self.focus();
	window.print();
}

function fCambiaTex() {
}

function fEnvia() {
	frm.hdBoton.value = "Itan";
	frm.hdSelect.value = "";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsultaItan.jsp", "cIdPrueba");
}

function fFolderOnChange(iPag) {
	FRM1 = fBuscaFrame("CONSULTA1");
	FRM2 = fBuscaFrame("CONSULTA2");
	FRM3 = fBuscaFrame("CONSULTA3");
	FRM4 = fBuscaFrame("CONSULTA4");
	/*
	 * if(iPag == 1) FRM1.fInicia(); if(iPag == 2) FRM2.fInicia(); if(iPag == 3)
	 * FRM3.fInicia(); if(iPag == 4) FRM4.fInicia();
	 */
}

function fGetInicio(dtFecha) {
	cDia = "01";
	cMes = dtFecha.substring(3, 5);
	cAnio = dtFecha.substring(6, 10);
	dtFecha = cDia + "/" + cMes + "/" + cAnio;
	return dtFecha;
}
function fGetFin(dtFecha) {
	cDia = "01";
	cMes = dtFecha.substring(3, 5);
	cMes = parseInt(cMes) + 1;
	cAnio = dtFecha.substring(6, 10);
	if (cMes < 10) {
		cMes = "0" + cMes;
	}
	if (cMes == "13") {
		cMes = "01";
		cAnio = parseInt(cAnio) + 1;
	}
	dtFecha = cDia + "/" + cMes + "/" + cAnio;
	return dtFecha;
}
function fOficinas() {
	frm.hdSelect.value = "SELECT DISTINCT(UO.ICVEOFICINA),O.CDSCBREVE FROM GRLUSUARIOXOFIC UO "
			+ "JOIN grloficina o ON O.ICVEOFICINA=UO.ICVEOFICINA "
			+ "JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO=UO.ICVEDEPARTAMENTO "
			+ "WHERE ICVEUSUARIO= " + top.fGetUsrID();
	frm.hdLlave.values = "UO.ICVEOFICINA";
	fEngSubmite("pgConsulta.jsp", "cIdOficina");
}
function fDepartamento() {
	frm.hdSelect.value = "SELECT DISTINCT(UO.ICVEDEPARTAMENTO),d.CDSCBREVE FROM GRLUSUARIOXOFIC UO "
			+ "JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO=UO.ICVEDEPARTAMENTO "
			+ "WHERE ICVEUSUARIO= "
			+ top.fGetUsrID()
			+ " and uo.ICVEOFICINA= "
			+ frm.iCveOficina.value;
	frm.hdLlave.values = "UO.ICVEDEPARTAMENTO";
	fEngSubmite("pgConsulta.jsp", "cIdDepartamento");
}
function fEsUsrPuertos() {
	frm.hdSelect.value = "SELECT ICVEDEPARTAMENTO FROM GRLUSUARIOXOFIC "
			+ " WHERE ICVEUSUARIO = " + top.fGetUsrID()
			+ " AND ICVEDEPARTAMENTO " + " IN (" + cDeptosPuertos + ")";
	// fGO("lEncuesta").disabled = true;
	// Se Comenta el Submite debido a que por el momento no se habilitara la
	// consulta.
	fEngSubmite("pgConsulta.jsp", "cIdDeptosPuertos");
}

function fAbreEncuesta() {
	if (lEjecutaEncuesta == 1)
		fAbreWindowHTML(false,
				'http://aplicaciones2.sct.gob.mx/cis/pgEncUTIC.jsp?hdBoton=Solicita&IID='
						+ top.fGetUsrID(), 'yes', 'yes', 'yes', 'yes', 'no',
				750, 580, 10, 10);
}

function fEsUsuarioLicencias() {
	frm.hdSelect.value = "SELECT ICVEGRUPO FROM SEGGPOXUSR where ICVESISTEMA = 44 "
			+ "and ICVEGRUPO in ("
			+ cGrupoLicencias
			+ ") and ICVEUSUARIO="
			+ top.fGetUsrID();
	fEngSubmite("pgConsultaSeg.jsp", "cIdGrupoLicencias");
}

function fGetUsername() {
	frm.hdSelect.value = "SELECT ICVEUSUARIO,CUSUARIO FROM SEGUSUARIO WHERE ICVEUSUARIO = "
			+ top.fGetUsrID();
	fEngSubmite("pgConsultaSeg.jsp", "cIdUsername");
}

function fCambiaContraADV() {
	if (confirm("Será redireccionado fuera del sistema, ¿Desea continuar?")){
		fGetUsername();
	}
}

function fGetFechaSQL(fecha) {
	var dt = "";
	return dt = fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-"
			+ fecha.substring(0, 2)
}

function fFormatoSol() {
	var urlForm = cRutaProgMM+"DownloadFormatoSolicitud?nombreDoc=SOLICITUD_CSCT.docx";
	 window.open(urlForm);
}

function fTecnico() {
	var urlForm = cRutaProgMM+"DownloadFormatoSolicitud?nombreDoc=MANUAL_DGST.pdf";
	window.open(urlForm);
}

function fDGDC() {
	var urlForm = cRutaProgMM+"DownloadFormatoSolicitud?nombreDoc=MANUAL_DGDC.pdf";
	 window.open(urlForm);
}

function fJurid() {
	var urlForm = cRutaProgMM+"DownloadFormatoSolicitud?nombreDoc=MANUAL_DGAJL.pdf";
	 window.open(urlForm);
}

function fCSCT() {	
	var urlForm = cRutaProgMM+"DownloadFormatoSolicitud?nombreDoc=MANUAL_CSCT.pdf";
	 window.open(urlForm);
}
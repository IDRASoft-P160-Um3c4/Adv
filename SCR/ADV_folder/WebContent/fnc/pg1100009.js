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
var strFecha="";
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
	strFecha = top.strFecha;

}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJSIni);
	InicioTabla("", 0, "100%", "", "", "", "1");
	FTDTR();
	
	ITRTD("", 0, "95%", "", "center");
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
	FTDTR();
		
	ITRTD("", 0, "50%", "", "center");
	InicioTabla("ETablaInfo", 0, "50%", "", "", "", 1);
		ITRTD("ETablaST", 5, "", "", "center");
			InicioTabla("", 0, "50%", "", "", "", 1);
				ITD("ETablaST");
					TextoSimple("SOLICITUDES AL "+strFecha);
				FITD()
			FinTabla();
		FTDTR();
		ITRTD("", 0, "", "", "center");
			IFrame("IListado", "100%", "100", "Listado.js", "yes", true);
		FTDTR();
		ITRTD("", 0, "", "", "center");
		TextoSimple("Nota: Para mayor detalle vaya al menú 'Consultas', opción 'Solicitudes'.");
		FTDTR();
	FinTabla();
	FTDTR();
	
	ITRTD("", 0, "95%", "", "center");
	
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
//		ITD("", 0, "", "", "center");// ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign)
//		Liga("Descargar Formato Solicitud", "fFormatoSol()", "");
//		FTD();
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
	FTDTR();
	FinTabla();

	ITRTD("cColorGenJSFolder", 0, "", "310", "", "");

	Hidden("iCveSistema", 44);
	Hidden("Modulo", 30);
	Hidden("hdSelect");
	Hidden("hdLlave");
	Hidden("cSolicitante");
	Hidden("iCveGrupoUsr",-1);
	Hidden("iCveUsuario",fGetIdUsrSesion());
	Hidden("iCveOficina",0);	
	Hidden("cFiltroOficina","");
	
	
	fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);
	FRMListado.fSetTitulo("Solicitudes,Número,");
	FRMListado.fSetCampos("1,2,");
	FRMListado.fSetDespliega("texto,texto,");
	FRMListado.fSetAlinea(",center,center,");
	fCargaGrupoUsr();
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

	if (cId == "tableroInicial" && cError == "") {
		FRMListado.fSetListado(aRes);
		FRMListado.fSetLlave(cLlave);
		FRMListado.fShow();
	}
	
	
	if (cId == "CIDOficinaDeptoXUsr") {
		if (aRes.length > 0) {
			frm.iCveOficina.value = aRes[0][1];
			frm.cFiltroOficina.value = " AND SOL.ICVEOFICINA = "+ frm.iCveOficina.value; 
		}
		fObtieneDatosTablero();
	}
	
	
	
	if (cId == "CIDGrupoXUsr" && cError == "") {
		if (aRes.length > 0) {
				frm.iCveGrupoUsr.value = aRes[0][0];
		}
			
		if(frm.iCveGrupoUsr.value==2){//grupo centros sct
			fCargaOficDeptoUsr(false);
		}else{
			fObtieneDatosTablero();
		}	
	}
	
	
	
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato) {
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

function fObtieneDatosTablero() {
	frm.hdBoton.value="tableroInicial";
	fEngSubmite("pg111020015ADV.jsp", "tableroInicial");
}



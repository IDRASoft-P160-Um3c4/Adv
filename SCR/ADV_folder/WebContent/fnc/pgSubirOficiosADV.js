// MetaCD=1.0 
// Title: pgShowRUPA.js
// Description: Consulta de RUPA
// Company: SCT 
// Author: JESR
var cTitulo = "";
var FRMListado = "";
var frm;
var aUsrDepto;
var ICVETRAMITE = 0;
var aNotifica = new Array();
var lFirst = true;

var bloqueaGuardar = false;
var cPagRet;

// valores en la base

var ACUSE_DE_SOLICITUD = 1;
// var RESOLUCION_VISITA_TECNICA = 2; //se sube antes de llegar a esta pantalla
var CONSTANCIA_NO_AFECTACION = 3;
var DICTAMEN_FACTIBILIDAD = 4;
var RESOLUCION_TRAMITE = 5;
var PERMISO = 6;

var ENVIO_DGST=11;
var MEMO_DAJL=12;
var ENTREGA_CONCE=13;
var ENTREGA_SCT=14;
var ENTREGA_SCT_PERM=15;
var ENTREGA_PERM=16;
var NOTIF_PNC=17;

var tramInt = false;
var existInput = false;
var arrTmp = new Array();
var msgErr = "";

// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgSubirOficiosADV.js";
	cPagRet = "pgSubirOficiosADV";
	cTitulo = "ANEXO DE OFICIOS AL TRÁMITE";
	fSetWindowTitle();
}
// SEGMENTO Definicin de la pgina (Definicin Mandatoria)
function fDefPag() {

	cGPD += '<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'
			+ '<form name="form1" enctype="multipart/form-data" method="post" action="UploadOficioADV">';
	fInicioPagina(cColorGenJSFolder);
	InicioTabla("", 0, "100%", "", "", "", "1");
	ITRTD("EEtiquetaC", 7, "", "", "center");
	// TextoSimple(cTitulo);
	FTDTR();
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "100%", "100%", "center", "", ".1", ".1");
	ITRTD("ESTitulo1", 0, "100%", "100%", "center", "top");
	InicioTabla("", 0, "100%", "1", "center");
	ITRTD("EEtiquetaC", 0, "100%", "25", "center", "top");
	TextoSimple("ANEXO DE OFICIOS AL TRÁMITE");

	FTDTR();
	ITRTD("", 0, "", "", "center");
	DinTabla("BarraWizard", "", 0, "", "90%", "center", "top", "", ".1", ".1");
	FTDTR();
	ITRTD("", 0, "", "", "center");
	DinTabla("TDBdy", "", 0, "90%", "", "center", "", "", ".1", ".1");
	FTDTR();
	ITRTD("", "", "", "40", "center");
	IFrame("IPanel", "515", "34", "Paneles.js");
	FTDTR();
	FinTabla();
	FTDTR();
	FinTabla();
	FTDTR();
	FinTabla();

	Hidden("iNumSolicitud", -1);
	Hidden("iEjercicio", -1);

	fFinPagina();
}
function fAnexaDoc() {

}
// SEGMENTO Despus de Cargar la pgina (Definicin Mandatoria)
function fOnLoad() {
	frm = document.forms[0];

	theTable = (document.all) ? document.all.TDBdy : document
			.getElementById("TDBdy");
	tBdy = theTable.tBodies[0];

	tBarraWizard = document.getElementById("BarraWizard");

	// fDelTBdy();
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	resetBloqueaGuardar();
	fNavega();
	// fShowDatos();
}
function fDelTBdyFIEL() {
	for (i = 0; tBdyFIEL.rows.length; i++) {
		tBdyFIEL.deleteRow(0);
	}
}
// LLAMADO al JSP especfico para la navegacin de la pgina
function fNavega() {
	if (top.opener) {
		frm.iNumSolicitud.value = top.opener.getNumSol();
		frm.iEjercicio.value = top.opener.getEjercicio();
	}

	if (frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {
		frm.hdBoton.value = "verDocsOficios";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "Listado");
	}
}
// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {

	if (iID == "esTramInt")
		tramInt = true;

	if (cId == "Listado" && cError == "") {
		fShowDatos(aRes);
	}

}

function fNuevo() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fBlanked();
	fDisabled(false);
	FRMListado.fSetDisabled(true);
}

function fGuardar() {
	if (fValidaTodo() == false) {
		frm.action = "UploadOficioADV?paramA=" + frm.iNumSolicitud.value
				+ "&paramB=" + cPagRet;
		fEnProceso(true);
		setTimeout(function() {
			frm.submit();
		}, 250);
	}
}

function fCancelar() {
	if (FRMListado.fGetLength() > 0)
		FRMPanel.fSetTraStatus("Disabled");
	else
		FRMPanel.fSetTraStatus("AddOnly");
	fDisabled(true);
	FRMListado.fSetDisabled(false);
}
// LLAMADO desde el Panel cada vez que se presiona al botn Borrar
function fBorrar() {
	/*
	 * if (confirm(cAlertMsgGen + "\n\n Desea usted eliminar el registro?")) {
	 * fNavega(); }
	 */
}

function fValidaTodo() {
	var vRet = validaAchivos();
	if (msgErr != "") {
		fAlert(msgErr);
	}
	return vRet;
}

function fCarga(lValor) {

}

function fShowDatos(aRes) {

	while (tBarraWizard.rows.length > 0) {
		tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
	}

	tBarraWizard.className = "ETablaInfo";
	tBarraWizard.width = "90%";

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.innerHTML = TextoSimple("ANEXO DE OFICIOS AL TRÁMITE");
	tCell.className = "ETablaST";

	var obj;

	var hasAcuse = false;
	var hasConstancia = false;
	var hasDictamen = false;
	var hasResol = false;
	var hasPermiso = false;
	var hasEnvioDGST = false;
	var hasMemoDAJL = false;
	var hasEntConce = false;
	var hasEntSCT = false;
	var hasEntSCTPerm= false;
	var hasEntPerm = false;
	var hasNotifPNC= false;
	
	for ( var i = 0; i < aRes.length; i++) {
		switch (parseInt(aRes[i][0])) {
		case ACUSE_DE_SOLICITUD:
			hasAcuse = true;
			break;
		case CONSTANCIA_NO_AFECTACION:
			hasConstancia = true;
			break;
		case DICTAMEN_FACTIBILIDAD:
			hasDictamen = true;
			break;
		case RESOLUCION_TRAMITE:
			hasResol = true;
			break;
		case PERMISO:
			hasPermiso = true;
			break;
			
		case ENVIO_DGST:
			hasEnvioDGST = true;
			break;
		case MEMO_DAJL:
			hasMemoDAJL = true;
			break;
		case ENTREGA_CONCE:
			hasEntConce = true;
			break;
		case ENTREGA_SCT:
			hasEntSCT = true;
			break;
		case ENTREGA_SCT_PERM:
			hasEntSCTPerm = true;
			break;
		case ENTREGA_PERM:
			hasEntPerm = true;
			break;
		case NOTIF_PNC:	
			hasNotifPNC = true;
			break;
		}	
	}

	var idx = 0;

	if (hasAcuse == false) {
		if (tramInt != true) {

			obj = [ "acuse", ACUSE_DE_SOLICITUD, "0", "0" ];
			arrTmp.push(obj);

			tRw = tBarraWizard.insertRow();
			tCell = tRw.insertCell();
			tCell.innerHTML = "ACUSE DE SOLICITUD: ";
			tCell.className = "EEtiqueta";
			tCell = tRw.insertCell();
			tCell.innerHTML = '<input id="file_id_' + ACUSE_DE_SOLICITUD
					+ '" type="file" onChange="checkFile(this,' + idx
					+ ')" name="fileButonADV' + ACUSE_DE_SOLICITUD
					+ '" size="25">';
			idx++;
		}
	}

	if (hasConstancia == false) {
		obj = [ "constancia", CONSTANCIA_NO_AFECTACION, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "CONSTANCIA DE NO AFECTACIÓN: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + CONSTANCIA_NO_AFECTACION
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + CONSTANCIA_NO_AFECTACION
				+ '" size="25">';
		idx++;
	}

	if (hasDictamen == false) {
		obj = [ "dictamen", DICTAMEN_FACTIBILIDAD, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "DICTAMEN DE FACTIBILIDAD: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + DICTAMEN_FACTIBILIDAD
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + DICTAMEN_FACTIBILIDAD
				+ '" size="25">';
		idx++;
	}

	if (hasResol == false) {
		obj = [ "resol", RESOLUCION_TRAMITE, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "RESOLUCIÓN DE TRAMITE: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + RESOLUCION_TRAMITE
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + RESOLUCION_TRAMITE + '" size="25">';
		idx++;
	}

	if (hasPermiso == false) {
		obj = [ "permiso", PERMISO, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "PERMISO: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + PERMISO
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + PERMISO + '" size="25">';
		idx++;
	}
	
	if (hasEnvioDGST == false) {
		obj = [ "envioDGST", ENVIO_DGST, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "OFICIO DE ENVÍO A DGST: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + ENVIO_DGST
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + ENVIO_DGST + '" size="25">';
		idx++;
	}
	
	if (hasMemoDAJL == false) {
		obj = [ "memoDAJL", MEMO_DAJL, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "MEMO DE ENVÍO A DAJL: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + MEMO_DAJL
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + MEMO_DAJL + '" size="25">';
		idx++;
	}
	
	if (hasEntConce == false) {
		obj = [ "entConce", ENTREGA_CONCE, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "ENTREGA CONCESIONARIO: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + ENTREGA_CONCE
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + ENTREGA_CONCE + '" size="25">';
		idx++;
	}
	
	if (hasEntSCT == false) {
		obj = [ "entSCT", ENTREGA_SCT, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "ENTREGA SCT: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + ENTREGA_SCT
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + ENTREGA_SCT + '" size="25">';
		idx++;
	}
	
	if (hasEntSCTPerm == false) {
		obj = [ "entSCTPerm", ENTREGA_SCT_PERM, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "ENTREGA SCT - PERMISIONARIO: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + ENTREGA_SCT_PERM
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + ENTREGA_SCT_PERM + '" size="25">';
		idx++;
	}
	
	if (hasEntPerm == false) {
		obj = [ "entPerm", ENTREGA_PERM, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "ENTREGA PERMISIONARIO: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + ENTREGA_PERM
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + ENTREGA_PERM + '" size="25">';
		idx++;
	}
	
	if (hasNotifPNC == false) {
		obj = [ "notifPNC", NOTIF_PNC, "0", "0" ];
		arrTmp.push(obj);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "NOTIFICACIÓN DE PNC: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = '<input id="file_id_' + NOTIF_PNC
				+ '" type="file" onChange="checkFile(this,' + idx
				+ ')" name="fileButonADV' + NOTIF_PNC+ '" size="25">';
	}
	

	 bloqueaGuardar = (hasAcuse&hasConstancia&hasDictamen&hasResol&hasPermiso&hasEnvioDGST&hasMemoDAJL&hasEntConce&hasEntSCT&hasEntSCTPerm&hasEntPerm&hasNotifPNC);

	if (bloqueaGuardar == true) {
		FRMPanel.fSetTraStatus("Disabled");
	} else {
		FRMPanel.fSetTraStatus("UpdateBegin");
	}

}

function resetBloqueaGuardar() {
	bloqueaGuardar = false;
}

function validaAchivos() {

	var valRet = false;
	var arrayFilesNames = fGetFilesValues(frm);

	if (validaArchVacios(arrayFilesNames) == true) {
		valRet = true;
	}

	if (validaDocExt(arrayFilesNames) == true) {
		valRet = true;
	}

	var valBand = false;

	if (existInput == true)
		valBand = validaTamInvalid(arrTmp);

	if (valBand == true) {
		valRet = true;
	}

	if (existInput == true)
		valBand = checkAllFiles(arrTmp);

	if (valBand == true) {
		valRet = true;
	}

	return valRet;
}

function validaDocExt(arrayDocsName) {
	for ( var ab = 0; ab < arrayDocsName.length; ab++) {

		var nomDoc = arrayDocsName[ab][0];

		if (nomDoc != "") {

			var arrNom = nomDoc.split('.');
			var tam = arrNom.length;
			var extDoc = arrNom[tam - 1];

			if (extDoc.toUpperCase() != "PDF") {
				msgErr += "\n- Existen documentos que no cumplen con el formato PDF. Favor de verificarlos.";
				return true;
			}

		}
	}
	return false;
}

function validaTamInvalid(arrF) {
	for ( var ab = 0; ab < arrF.length; ab++) {
		if (arrF[ab][9] == "1") {
			msgErr += "\n- Existen documentos que exceden el tamaño máximo de 15Mb. Favor de verificarlos.";
			return true;
		}
	}
	return false;
}

function getIdx(id) {

	var arrId = id.split("_");

	for ( var i = 0; i < arrTmp.length; i++) {
		if (arrTmp[i][1] == arrId[2])
			return parseInt(i);
	}
	return -1;
}

function validaArchVacios(arrayDocsName) {

	for ( var ab = 0; ab < arrayDocsName.length; ab++) {

		var nomDoc = arrayDocsName[ab][0];

		if (nomDoc == "") {
			msgErr += "\n- Debe subir todos los documentos para completar la operación.";
			return true;
		}
	}
	return false;

}

function fValidaTodo() {
	msgErr = "";
	var ret = validaAchivos();
	if (msgErr != "")
		fAlert(msgErr);
	return ret;
}

function checkFile(e, idx) {

	var myFSO = new ActiveXObject("Scripting.FileSystemObject");
	var filepath = e.value;

	if (filepath != "" && filepath != undefined) {
		var thefile = myFSO.getFile(filepath);
		var size = thefile.size;

		arrTmp[idx][3] = size;

		if (size > (15 * 1024 * 1024)) {
			fAlert("- El tamaño del archivo es mayor a 15Mb. Reemplace el archivo por uno válido");
			arrTmp[idx][2] = "1";
			return;
		}
	}

	arrTmp[idx][2] = "0";
	arrTmp[idx][3] = "0";
}

function checkAllFiles(arrF) {
	var totSize = 0;

	for ( var i = 0; i < arrF.length; i++) {
		totSize += parseInt(arrF[i][3]);
	}

	if (totSize > (50 * 1024 * 1024)) {
		msgErr += "\n- El tamaño del conjunto de archivos supera los 50Mb permitidos. Verifique los archivos.";
		return true;
	}

	return false;
}
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
var existInputPNC = false;
var existInput = false;
var msgErr = "";

var cPagRet;

var arrTmp;
var arrTmpPNC;

// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad(cMsgError) {

	if (cMsgError !== undefined)
		msgErr = cMsgError;

	cPaginaWebJS = "pgSubirDocsCotejo.js";
	cPagRet = "pgSubirDocsCotejo";
	cTitulo = "ANEXAR DOCUMENTOS AL TRÁMITE";
	fSetWindowTitle();
}
// SEGMENTO Definicin de la pgina (Definicin Mandatoria)
function fDefPag() {

	cGPD += '<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'
			+ '<form name="form1" enctype="multipart/form-data" method="post" action="UploadADVDocs">';
	fInicioPagina(cColorGenJSFolder);
	InicioTabla("", 0, "100%", "", "", "", "1");
	ITRTD("EEtiquetaC", 7, "", "", "center");
	TextoSimple(cTitulo);
	FTDTR();
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "100%", "100%", "center", "", ".1", ".1");
	ITRTD("ESTitulo1", 0, "100%", "100%", "center", "top");
	InicioTabla("", 0, "100%", "1", "center");
	ITRTD("EEtiquetaC", 0, "100%", "25", "center", "top");
	// TextoSimple("DOCUMENTOS ANEXOS AL TRÁMITE");

	FTDTR();

	ITRTD("", 0, "", "", "center");
	DinTabla("BarraWizard", "", 0, "", "90%", "center", "top", "", ".1", ".1");
	FTDTR();

	ITRTD("", 0, "", "", "center");
	DinTabla("BarraWizardA", "", 0, "", "90%", "center", "top", "", ".1", ".1");
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
	Hidden("tienePNC", -1);

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
	tBarraWizardA = document.getElementById("BarraWizardA");

	// fDelTBdy();
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	FRMPanel.fSetTraStatus("Disabled");

	if (top.opener.getTienePNC) {
		frm.tienePNC.value = top.opener.getTienePNC();
	}

	fNavega();
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

		frm.hdBoton.value = "subirDocsCotejo";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "Listado");

	}
}

function fNavegaA() {

	if (top.opener) {
		frm.iNumSolicitud.value = top.opener.getNumSol();
		frm.iEjercicio.value = top.opener.getEjercicio();
	}

	if (frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {

		frm.hdBoton.value = "subirDocsPNC";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "ListadoPNC");

	}
}
// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {

	if (msgErr != "") {// si ocurre un error en el servidor al cargar los
		// archivos
		fAlert(msgErr);
		msgErr = "";
	}

	if (cError == "Guardar") {
		fAlert("Existió un error en el Guardado!\n");
		return;
	} else if (cError == "Borrar") {
		fAlert("Existió un error en el Borrado!");
		return;
	} else if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	}

	if (cId == "Listado" && cError == "") {
		fShowDatos(aRes);
		fNavegaA();
	}

	if (cId == "ListadoPNC" && cError == "") {
		fShowDatosPNC(aRes);
		bloqGuardar();
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
		frm.action = "UploadADVDocs?paramA=" + frm.iNumSolicitud.value
				+ "&paramB=" + cPagRet + "&paramC=" + frm.tienePNC.value;
		fEnProceso(true);
		setTimeout(function() {
			frm.submit();
		}, 250);
	}

}

function fCancelar() {
	if (FRMListado!=undefined&&FRMListado.fGetLength() > 0)
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
//
// function fValidaTodo() {
// return true;
// }

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
	tCell.innerHTML = TextoSimple("ANEXAR DOCUMENTOS AL TRÁMITE");
	tCell.className = "ETablaST";

	for ( var t = 0; t < aRes.length; t++) {

		aRes[t].push("0"); // bandera archivo invalido
		aRes[t].push("0"); // tamnanio

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();

		tCell.innerHTML = aRes[t][0] + ": ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();

		if (aRes[t][8] == "0") {
			if (aRes[t][7] == "") {
				existInput = true;
				tCell.innerHTML = '<input id="file_id_' + aRes[t][1]
						+ '" type="file" name="fileButonADV' + aRes[t][1]
						+ '" size="25">';
			} else {
				tCell.innerHTML = '<label><b>&nbsp;&nbsp;Entregado digitalmente.</b></label>';
			}
		} else {
			tCell.innerHTML = '<label><b>&nbsp;&nbsp;Entregado físicamente.</b></label>';
		}
	}

	if (existInput == true) {
		FRMPanel.fSetTraStatus("UpdateBegin");
		// window.parent.setPermiteImpresion(false);
	}

	arrTmp = fCopiaArreglo(aRes);

}

function fShowDatosPNC(aRes) {

	while (tBarraWizardA.rows.length > 0) {
		tBarraWizardA.deleteRow(tBarraWizardA.rows.length - 1);
	}

	tBarraWizardA.className = "ETablaInfo";
	tBarraWizardA.width = "90%";

	tRw = tBarraWizardA.insertRow();
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.innerHTML = TextoSimple("ANEXAR DOCUMENTOS COMO RESPUESTA A LA NOTIFICACIÓN");
	tCell.className = "ETablaST";

	for ( var t = 0; t < aRes.length; t++) {

		aRes[t].push("0"); // bandera archivo invalido
		aRes[t].push("0"); // tamnanio

		tRw = tBarraWizardA.insertRow();
		tCell = tRw.insertCell();

		tCell.innerHTML = aRes[t][0] + ": ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();

		if (aRes[t][9] == "0") {
			if (aRes[t][7] == "") {
				existInputPNC = true;
				tCell.innerHTML = '<input id="file_id_' + aRes[t][1]
						+ '" type="file" name="fileButonADV' + aRes[t][1]
						+ '" onChange="checkFile(this,' + t
						+ ',true);" size="25">';
			} else {
				tCell.innerHTML = '<label><b>&nbsp;&nbsp;Entregado digitalmente.</b></label>';
			}
		} else {
			tCell.innerHTML = '<label><b>&nbsp;&nbsp;Entregado físicamente.</b></label>';
		}
	}

	if (existInputPNC == true) {
		FRMPanel.fSetTraStatus("UpdateBegin");
	}

	arrTmpPNC = fCopiaArreglo(aRes);

}

function resetBloqueaGuardar() {
	bloqueaGuardar = false;
}

function bloqGuardar() {

	if (existInput == false && existInputPNC == false)
		FRMPanel.fSetTraStatus("Disabled");
	else
		FRMPanel.fSetTraStatus("UpdateBegin");
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

function getIdx(id) {

	var arrId = id.split("_");

	for ( var i = 0; i < arrTmp.length; i++) {
		if (arrTmp[i][1] == arrId[2])
			return parseInt(i);
	}
	return -1;
}

function getIdxPNC(id) {

	var arrId = id.split("_");

	for ( var i = 0; i < arrTmpPNC.length; i++) {
		if (arrTmpPNC[i][1] == arrId[2])
			return parseInt(i);
	}
	return -1;
}

function validaArchVacios(arrayDocsName) {

	for ( var ab = 0; ab < arrayDocsName.length; ab++) {

		var nomDoc = arrayDocsName[ab][0];

		if (nomDoc == "" && arrTmp[getIdx(arrayDocsName[ab][1])][8] == "0") {
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

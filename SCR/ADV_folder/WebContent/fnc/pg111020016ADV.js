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
var msgErr = "";
var arrTmp = new Array();

var bloqueaGuardar = false;

// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg111020016ADV.js";
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
	TextoSimple("ANEXO DE DOCUMENTACIÓN PARA REQUSITOS ENTREGADOS");

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
	
	frm.iNumSolicitud.value = window.parent.getNumSolADVFiles();
	frm.iEjercicio.value = window.parent.getEjercicioADVFiles();
	
	if (frm.iNumSolicitud.value > -1) {

		frm.hdBoton.value = "requisitosEntregados";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		fEngSubmite("pgDocsSolADV.jsp", "Listado");
	}

}

function getNumSol(){
	return frm.iNumSolicitud.value;
};

function getEjercicio(){
	return frm.iEjercicio.value;
};

// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {

	if (cError != "")
		fAlert("Error\n" + cError);

	if (cId == "Listado" && cError == "") {
		fShowDatos(aRes);
		fEnProceso(false);
	}
}

function fNuevo() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fBlanked();
	fDisabled(false);
	FRMListado.fSetDisabled(true);
}

function fGuardar() {
	if (fValidaTodo() == false) { // si no existieron errores
		frm.iNumSolicitud.value = window.parent.getNumSolADVFiles();
		frm.action = "UploadADVDocs?paramA=" + frm.iNumSolicitud.value;
		fEnProceso(true);
		frm.submit();
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
	tCell.innerHTML = TextoSimple("ANEXO DE DOCUMENTACIÓN PARA REQUSITOS ENTREGADOS");
	tCell.className = "ETablaST";

	for ( var t = 0; t < aRes.length; t++) {

		aRes[t].push("0"); //bandera archivo invalido
		aRes[t].push("0"); //tamnanio

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();

		tCell.innerHTML = aRes[t][0] + ": ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();

		if (aRes[t][8] == "0") {
			if (aRes[t][7] != "") {
				tCell.innerHTML = Liga("[Ver Documento]", "fShowIntDocDig("
						+ aRes[t][7] + ");");
				bloqueaGuardar = true;
			} else {
				bloqueaGuardar = false;
				tCell.innerHTML = '<input id="file_id_' + aRes[t][1]
						+ '" type="file" name="fileButonADV' + aRes[t][1]
						+ '" onChange="checkFile(this,' + t + ');" size="25">';
			}	
		} else {
			tCell.innerHTML = '<label><b>&nbsp;&nbsp;Entregado físicamente.</b></label>';
		}
	}

//	if (bloqueaGuardar == true) {
//		FRMPanel.fSetTraStatus("Disabled");
//		window.parent.setPermiteImpresion(false);
//	} else {
		FRMPanel.fSetTraStatus("UpdateBegin");
		window.parent.setPermiteImpresion(false);
//	}

	arrTmp = fCopiaArreglo(aRes);

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

	if (validaTamInvalid() == true) {
		valRet = true;
	}
	
	if (checkAllFiles() == true) {
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

function validaTamInvalid() {
	for ( var ab = 0; ab < arrTmp.length; ab++) {
		if (arrTmp[ab][9] == "1") {
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

function checkFile(e, idx) {

	var myFSO = new ActiveXObject("Scripting.FileSystemObject");
	var filepath = e.value;

	if (filepath != "" && filepath != undefined) {
		var thefile = myFSO.getFile(filepath);
		var size = thefile.size;
		arrTmp[idx][10]=size; 
		if (size > (15* 1024 * 1024)) {
			fAlert("- El tamaño del archivo es mayor a 15Mb. Reemplace el archivo por uno válido");
			arrTmp[idx][9] = "1";
			return;
		}
	}
	
	arrTmp[idx][10]="0";
	arrTmp[idx][9]="0";
}

function checkAllFiles(){
	var totSize = 0;
	
	for(var i=0;i<arrTmp.length;i++){
		totSize+=parseInt(arrTmp[i][10]);
	}
	
	if(totSize>(50*1024*1024)){
		msgErr += "\n- El tamaño del conjunto de archivos supera los 50Mb permitidos. Verifique los archivos.";
		return true;
	}
	
	return false;
}




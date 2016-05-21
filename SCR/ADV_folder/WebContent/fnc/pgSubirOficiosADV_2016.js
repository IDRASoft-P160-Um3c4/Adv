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

var existInput = false;
var arrTmp = new Array();
var msgErr = "";

// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad(cMsgError) {

	if (cMsgError != undefined)
		msgErr = cMsgError;

	cPaginaWebJS = "pgSubirOficiosADV_2016.js";
	cPagRet = "pgSubirOficiosADV_2016";
	cTitulo = "ANEXO DE OFICIOS AL TRÁMITE";
	fSetWindowTitle();
}
// SEGMENTO Definicin de la pgina (Definicin Mandatoria)
function fDefPag() {

	cGPD += '<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'
			+ '<form name="form1" enctype="multipart/form-data" method="post" action="UploadOficioADV2016">';
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
	Hidden("iCveOficio", -1);
	Hidden("iCveUsuario", fGetIdUsrSesion());

	fFinPagina();
}
function fAnexaDoc() {

}
// SEGMENTO Despus de Cargar la pgina (Definicin Mandatoria)
function fOnLoad() {
	frm = document.forms[0];

	theTable = (document.all) ? document.all.TDBdy : document.getElementById("TDBdy");
	tBdy = theTable.tBodies[0];

	tBarraWizard = document.getElementById("BarraWizard");

	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	resetBloqueaGuardar();
	fGetDatos();
	fNavega();
}
function fDelTBdyFIEL() {
	for (i = 0; tBdyFIEL.rows.length; i++) {
		tBdyFIEL.deleteRow(0);
	}
}
// LLAMADO al JSP especfico para la navegacin de la pgina
function fNavega() {
	
	if (frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {
		frm.hdFiltro.value = "REGOF.IEJERCICIO = "+frm.iEjercicio.value+ " AND REGOF.INUMSOLICITUD="+frm.iNumSolicitud.value+" AND REGOF.ICVEOFICIOADV="+frm.iCveOficio.value;
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgGestionOficios.jsp", "buscaRegistroOficio");
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

	if (cId == "buscaRegistroOficio" && cError == "") {
		fShowDatos(aRes);
	}

}

function fGuardar() {
	if (fValidaTodo() == false) {
		frm.action = "UploadOficioADV2016?paramA=" + frm.iNumSolicitud.value
				+ "&paramB=" + cPagRet
				+ "&paramC=" + frm.iCveOficio.value
				+ "&paramD=" + frm.iCveUsuario.value;
				
		fEnProceso(true);
		setTimeout(function() {
			frm.submit();
		}, 250);
	}
}


function fValidaTodo() {
	msgErr="";
	var vRet = validaAchivos();
	if (msgErr != "") {
		fAlert(msgErr);
	}
	return vRet;
}

function fGetDatos() {
	if(top.opener){
		frm.iEjercicio.value=top.opener.getEjercicio();
		frm.iNumSolicitud.value=top.opener.getSolicitud();
		frm.iCveOficio.value=top.opener.getOficioSel();
	}
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

	for ( var i = 0; i < aRes.length; i++) {
		
			tRw = tBarraWizard.insertRow();
			tCell = tRw.insertCell();
			tCell.innerHTML = aRes[i][2]+": ";
			tCell.className = "EEtiqueta";
			tCell = tRw.insertCell();
			
			if(aRes[i][1]>0){
				tCell.innerHTML = Liga("[Ver Documento]", "fShowIntDocDig("+aRes[i][1]+");");
				bloqueaGuardar = true;
			}else{
				tCell.innerHTML = '<input id="file_id_' + aRes[i][0]
				+ '" type="file" name="fileButonADV' + aRes[i][0]
				+ '" size="25">';	
				bloqueaGuardar = false;
			}
	}

	if (bloqueaGuardar == true) {
		FRMPanel.fSetTraStatus("Disabled");
		if(top.opener){
			top.opener.fNavega();
		}
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

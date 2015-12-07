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

var cveDoc = -1;

// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg111040033.js";
	cTitulo="HISTÓRICO ADV";
	fSetWindowTitle();
}
// SEGMENTO Definicin de la pgina (Definicin Mandatoria)
function fDefPag() {

	cGPD += '<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'
			+ '<form name="form1" enctype="multipart/form-data" method="post" action="UploadDocHistorico">';
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
							//TextoSimple("HISTÓRICO ADV");
							
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
	
	Hidden("iNumSolicitud",-1);
	Hidden("iCveUsr",fGetIdUsrSesion());
	
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

	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	FRMPanel.fSetTraStatus("Disabled");
	fNavega();
}
function fDelTBdyFIEL() {
	for (i = 0; tBdyFIEL.rows.length; i++) {
		tBdyFIEL.deleteRow(0);
	}
}
// LLAMADO al JSP especfico para la navegacin de la pgina
function fNavega() {
	
		frm.hdBoton.value="docHistorico";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		fEngSubmite("pgDocsSolADV.jsp", "Listado");
	
}
// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {
	if (cId == "Listado" && cError == "") {
		fShowDatos(aRes);
		fEnProceso(false);
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
	tCell.innerHTML = TextoSimple("HISTÓRICO ADV");
	tCell.className = "ETablaST";	
	
	for(var t=0; t < aRes.length;t++){
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		//tCell.colSpan = 2;
		tCell.innerHTML = "Última versión: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		
		if(aRes[0][0]!=""){
			cveDoc=aRes[0][0];
			tCell.innerHTML = Liga("[Descargar Histórico]", "fShowHistoricoExcel();");
		}else{
			tCell.innerHTML = TextoSimple("No existen versiones anteriores.");
		}
	}
		
}


function getParamUsr(){
	return frm.iCveUsr.value; 
}

function getCveDoc(){
	return cveDoc;
}
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

var bloqueaGuardar=false;

// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgVerDocsEval.js";
}
// SEGMENTO Definicin de la pgina (Definicin Mandatoria)
function fDefPag() {

	cGPD += '<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'
			+ '<form name="form1" enctype="multipart/form-data" method="post" action="UploadOficioADV">';
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
						 TextoSimple("DOCUMENTOS ANEXOS AL TRÁMITE");
							
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
	Hidden("iEjercicio",-1);

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

	//fDelTBdy();
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	resetBloqueaGuardar();
	fNavega();
	//fShowDatos();
}
function fDelTBdyFIEL() {
	for (i = 0; tBdyFIEL.rows.length; i++) {
		tBdyFIEL.deleteRow(0);
	}
}
// LLAMADO al JSP especfico para la navegacin de la pgina
function fNavega() {
	
	if(top.opener){
		frm.iNumSolicitud.value=top.opener.getNumSol();
		frm.iEjercicio.value=top.opener.getEjercicio();
	}
	
	
	if(	frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '' ){
		
		frm.hdBoton.value = "verDocsCotejo";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "Listado");

	}
}
// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {
	if (cId == "Listado" && cError == "") {
		fShowDatos(aRes);
		FRMPanel.fSetTraStatus("Disabled");
	}
}

function fNuevo() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fBlanked();
	fDisabled(false);
	FRMListado.fSetDisabled(true);
}

function fGuardar() {
    frm.action = "UploadADVDocs?paramA="+frm.iNumSolicitud.value;              
	//frm.submit();//	probar que envie la peticion, que regrese y se redibuje todo bn
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
	/*if (confirm(cAlertMsgGen + "\n\n Desea usted eliminar el registro?")) {
		fNavega();
	}*/
}

function fValidaTodo() {
	return true;
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
	tCell.innerHTML = TextoSimple("DOCUMENTOS ANEXOS AL TRÁMITE");
	tCell.className = "ETablaST";	
	
	for(var t=0; t < aRes.length;t++){
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		
		tCell.innerHTML = aRes[t][0]+": ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		
		if(aRes[t][7]!="" && !(aRes[t][4]>0) ){
			tCell.innerHTML = Liga("[Ver Documento]", "fShowIntDocDig("+aRes[t][7]+");");
			bloqueaGuardar=true;
		}
		else{
			bloqueaGuardar=false;
			tCell.innerHTML = '<input type="file" name="fileButonADV'+aRes[t][1]+'" size="25">';
		}
			
	}
	
	if(bloqueaGuardar==true){
		FRMPanel.fSetTraStatus("Disabled");
	}else{
		FRMPanel.fSetTraStatus("UpdateBegin");
	}
	
}

function resetBloqueaGuardar(){
	bloqueaGuardar=false;
}

/*
function setNumSolFiles(valor){
	frm.iNumSolicitud.value=valor;
}
*/
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
var cPagRet;
var tamanio = 0;
var msgErr = "";


// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad(cMsgError) {
	 if(cMsgError!==undefined)
			msgErr = cMsgError;
	
	cPaginaWebJS = "pgSubirMinutaVT.js";
	cPagRet = "pgSubirMinutaVT";
	cTitulo="ANEXO DE OFICIO DE MINUTA TÉCNICA";
	fSetWindowTitle();
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
						//	TextoSimple("ANEXO DE OFICIO DE MINUTA TÉCNICA");
							
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
		frm.hdBoton.value = "oficioMinuta";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "Listado");
	}
}
// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {
	if (msgErr != "") {// si ocurre un error en el servidor al cargar los archivos
	fAlert(msgErr);
	msgErr = "";
	}
	
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
	if(valBeforeSave()==false){
	    frm.action = "UploadOficioADV?paramA="+frm.iNumSolicitud.value+"&paramB="+cPagRet;
	    fEnProceso(true);
		setTimeout(function(){frm.submit();},250);
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
	tCell.innerHTML = TextoSimple("ANEXO DE OFICIO DE MINUTA TÉCNICA");
	tCell.className = "ETablaST";	
	
	if(aRes.length>0){
		
		bloqueaGuardar=true;
		
		for(var t=0; t < aRes.length;t++){
			tRw = tBarraWizard.insertRow();
			tCell = tRw.insertCell();
			
			tCell.innerHTML = aRes[t][1]+": ";
			tCell.className = "EEtiqueta";
			tCell = tRw.insertCell();
			tCell.innerHTML = Liga("[Ver Documento]", "fShowIntDocDig("+aRes[t][2]+");");
		}
		
		if(top.opener){
			top.opener.setSubioFormato(true);
		}
		
	}else{
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		
		tCell.innerHTML = "Oficio de Minuta de Visita Técnica: ";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		bloqueaGuardar=false;
		
		//2 id del oficio de minuta tecnica
		tCell.innerHTML = '<input type="file" name="fileButonADV2" size="25">';
		
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

function validaArchVacioExt() {
	msgErr="";

	var arrDocName = fGetFilesValues(frm);

		var nomDoc = arrDocName[0][0];

		if (nomDoc == "") {
			msgErr += "\n- Debe subir el documento para completar la operación.";
			return true;
		}else{
			var arrNom = nomDoc.split('.');
			var tam = arrNom.length;
			var extDoc = arrNom[tam - 1];

			if (extDoc.toUpperCase() != "PDF") {
				msgErr += "\n- Existen documentos que no cumplen con el formato PDF. Favor de verificarlos.";
				return true;
			}
		}
		
	return false;
}

function valBeforeSave(){
	
	var err = false;
	
	if(validaArchVacioExt()==true) {
		err =true;
	}
	
	if(msgErr!="")
		fAlert(msgErr);
	
	return err;
}



  
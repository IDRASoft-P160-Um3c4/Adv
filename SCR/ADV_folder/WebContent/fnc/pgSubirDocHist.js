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
var existInputPNC=false;
var existInput=false;
var msgErr="";

var cPagRet;

var icveDocHist=-1;

var validFile=true;
var fSize = 0;


// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgSubirDocHist.js";
	cPagRet="pgSubirDocHist";
	cTitulo="ANEXAR DOCUMENTO A HISTÓRICO";
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
						 //TextoSimple("DOCUMENTOS ANEXOS AL TRÁMITE");
							
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
	
	Hidden("iCveHistADV",-1);
	Hidden("iCveUsuario",-1);
	Hidden("fName",-1);
	
	
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

	//fDelTBdy();
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	FRMPanel.fSetTraStatus("Disabled");
	frm.iCveUsuario.value = fGetIdUsrSesion();
	fNavega();
}
function fDelTBdyFIEL() {
	for (i = 0; tBdyFIEL.rows.length; i++) {
		tBdyFIEL.deleteRow(0);
	}
}
// LLAMADO al JSP especfico para la navegacin de la pgina
function fNavega() {
	
	if(top.opener){
		frm.iCveHistADV.value=top.opener.getCveHist();
	}
	
		frm.hdBoton.value = "docHistorico";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "Listado");

}


// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {
	
	if (cId == "Listado" && cError == "") {
		fShowDatos(aRes);
		fNuevo();
	}

}

function fNuevo() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fBlanked();
	fDisabled(false);
}

function fGuardar() {
	
	if(fValidaTodo()==false){
	    frm.action = "UploadDocHistorico?paramA="+frm.iCveHistADV.value+"&fName="+frm.fName.value+"&iCveUsr="+frm.iCveUsuario.value;      
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
}
// LLAMADO desde el Panel cada vez que se presiona al botn Borrar
function fBorrar() {
	/*if (confirm(cAlertMsgGen + "\n\n Desea usted eliminar el registro?")) {
		fNavega();
	}*/
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
	 tCell.innerHTML = TextoSimple("DOCUEMNTOS HISTÓRICO ADV");
	 tCell.className = "ETablaST";
		
	 tRw = tBarraWizard.insertRow();
	 tCell = tRw.insertCell();
	 
	if(aRes.length > 0){ 
	 for(var t=0; t < aRes.length;t++){
			
		 tCell.innerHTML = "Último archivo: ";
		 tCell.className = "EEtiqueta";
		 tCell = tRw.insertCell();
				
		 if(aRes[0][0]!=""){
			 icveDocHist=aRes[0][0];
		 tCell.innerHTML = Liga("[Descargar archivo]", "fShowHistoricoExcel();");
		 }
	 }
	 
	}else{
		tCell.innerHTML = TextoSimple("No existen archivos anteriores.");
	}
				
		 tRw = tBarraWizard.insertRow();
		 tCell = tRw.insertCell();
		 tCell.innerHTML = "Nueva archivo: ";
		 tCell.className = "EEtiqueta";
		 tCell = tRw.insertCell();
				
		 tCell.innerHTML = '<input type="file" onChange="checkFile(this)" name="fileButonADV" size="25">';
}


function validaArchVacios(arrayDocsName) {

	for ( var ab = 0; ab < arrayDocsName.length; ab++) {
		
		var nomDoc = arrayDocsName[ab][0];
		
		if (nomDoc == "") {
			frm.fName.value="";
			msgErr += "\n- Debe subir todos los documentos para completar la operación.";
			return true;
		}
	}
	return false;

}

function fValidaTodo() {
	
	msgErr = "";
	var ret = false;
	
	if(validaArchVacios(fGetFilesValues(frm))==true){
		ret = true;
	}
	
	if(validFile==false){
		ret = true;
		msgErr+="\n- El tamaño del archivo es mayor a 15Mb. Reemplace el archivo por uno válido";
	}
	
	if (msgErr != "")
		fAlert(msgErr);
	return ret;
}

function checkFile(e) {
	
	 validFile=true;
	 fSize = 0;

	var myFSO = new ActiveXObject("Scripting.FileSystemObject");
	var filepath = e.value;
	
	var arrName = filepath.split("\\");
	
	frm.fName.value=arrName[arrName.length-1];
	
	if (filepath != "" && filepath != undefined) {
		var thefile = myFSO.getFile(filepath);
		
		var size = thefile.size;
		
		if (size > (15* 1024 * 1024)) {
			validFile = false;
			fAlert("- El tamaño del archivo es mayor a 15Mb. Reemplace el archivo por uno válido");
			return;
		}
	}
}

function getCveDoc(){
	return icveDocHist;
}

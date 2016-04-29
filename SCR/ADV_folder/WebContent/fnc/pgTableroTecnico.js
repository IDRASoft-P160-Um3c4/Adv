var cTitulo = "";
var FRMListado = "";
var frm;
var aResTemp = new Array();
var iCveEtapa = 0;
var cPermisoPag;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgTableroTecnico.js";
	cTitulo = "ÉVALUACIÓN DE DOCUMENTOS TÉCNICOS";
	cPermisoPag = fGetPermisos(cPaginaWebJS);
	fSetWindowTitle();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJS);
	InicioTabla("", 0, "100%", "", "", "", "1");
	ITRTD("ESTitulo", 0, "100%", "", "center");
	TextoSimple(cTitulo);
	FTDTR();
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "100%", "", "center");
	ITRTD("", 0, "", "10", "center", "top");
	InicioTabla("ETablaInfo", 0, "75%", "", "", "", 1);
	fDefOficXUsr();
	FinTabla();
	FTDTR();
	ITRTD("", 0, "", "175", "center", "top");
	IFrame("IListado", "95%", "170", "Listado.js", "yes", true);
	FTDTR();
	
	FTDTR();

	Hidden("hdLlave", "");
	Hidden("hdSelect", "");
	Hidden("iEjercicio", "");
	Hidden("iNumSolicitud", "");
	Hidden("iCveTramite", "");
	Hidden("iCveModalidad", "");
	 Hidden("hdOfic","");
	    Hidden("hdDpto","");
	FinTabla();
	FTDTR();
	FinTabla();
	fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);
	FRMListado.fSetTitulo("Ejercicio, Núm. Solicitud, Solicitante, Trámite, Oficina de Origen, Tiene PNC, Documentos, Evaluación,");
	FRMListado.fSetCampos("0,1,2,3,4,5,6,7,");
	FRMListado.fSetAlinea("center,center,center,center,center,center,center,center,");
	frm.hdBoton.value = "Primero";
	fCargaOficDeptoUsr(false);
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega() {
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "iEjercicio,iNumSolicitud";
	frm.hdNumReg.value = 1000;
	return fEngSubmite("pgTRAEvaluacionArea.jsp", "ListadoTecnico");
}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave) {
	if (cError == "Guardar")
		fAlert("Existió un error en el Guardado!");
	if (cError == "Borrar")
		fAlert("Existió un error en el Borrado!");
	if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	}
	if (cError != "") {
		fAlert('n - ' + cError);
		return;
	}

	if (cId == "ListadoTecnico" && cError == "") {
		if (aRes.length > 0) {
			
			for ( var o = 0; o < aRes.length; o++) {
				if(aRes[o][5]>0){
					aRes[o][5]="Si";
				}else{
					aRes[o][5]="";
				}
				aRes[o].push("<font color=blue>VER DOCUMENTOS</font>");
				aRes[o].push("<font color=blue>EVALUACIÓN DE DOCUMENTOS</font>");
			}
		}
		frm.hdRowPag.value = iRowPag;
		FRMListado.fSetListado(aRes);

		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
	}
	
	if (cId == "ValidaDocsEvaluacion" && cError == "") {
//		if(aRes[0][0] == 0){ //descomentar para validación de documentos
			fAbreSubWindowSinPermisos("pgVerifSolNew", "800", "460");
//		}else{
//			fAlert("\nNo es posible realizar la evaluación, los documentos no han sido cargados");
//		}	
	}
	
	if(cId == "CIDOficinaDeptoXUsr"&&cError==""){
		   if(aRes.length > 0){
		     frm.hdOfic.value=aRes[0][1];
		     frm.hdDpto.value=aRes[0][2];
		   }
	   }

	fResOficDeptoUsr(aRes, cId, cError);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar() {
	fDisabled(false);
	FRMListado.fSetDisabled(false);
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato, iCol) {
	
	frm.iEjercicio.value = aDato[0];
	frm.iNumSolicitud.value = aDato[1];

	if (iCol == 7){//click acciones
		
		if(aDato[5]=="Si"){
			fAlert("\nNo es posible realizar la evaluación, la solcitud tiene un PNC que no ha sido cerrado.");
			return;
		}else{
			fValidaDocumentos();
		}	
	}
	
	if (iCol == 6){//click acciones
		fDocsTramite();
	}
}

function fValidaDocumentos() {
	//MANDAR PETICION PARA VERIFICAR LOS DOCUMENTOS CARGADOS
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "";
	frm.hdNumReg.value = 1000;
	return fEngSubmite("pgTRAEvaluacionArea.jsp", "ValidaDocsEvaluacion");
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

function fDefOficXUsr() {
	// Necesario definir los campos ocultos Hidden("fResultado "); y
	// Hidden("hdLlave");
	var cTx;
	cTx = ITRTD("EEtiquetaC", 0, "100%", "20", "center")
			+ InicioTabla("", 0, "", "", "center") + ITR()
			+ ITD("EEtiqueta", 0, "0", "", "center", "middle")
			+ TextoSimple("Oficina:") + FTD()
			+ ITD("EEtiquetaL", 0, "0", "", "center", "middle")
			+ Select("iCveOficinaUsr", "fOficinaUsrOnChange();") + FTD()
			+ ITD("EEtiqueta", 0, "0", "", "center", "middle")
			+ TextoSimple("Departamento:") + FTD()
			+ ITD("EEtiquetaL", 0, "0", "", "center", "middle")
			+ Select("iCveDeptoUsr", "fBuscar();") + FTD() + FTR() + FinTabla()
			+ FTDTR();
	return cTx;
}

function fOficinaUsrOnChangeLocal() {
	fBuscar();
}

function fTerminaEvaluacion() {
	
	fNavega();
}

function fBuscar() {
	fNavega();
}

function fGetiEjercicio() {
	return frm.iEjercicio.value;
}
function fGetiNumSolicitud() {
	return frm.iNumSolicitud.value;
}

function fDocsTramite(){
	if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != '' &&
		       frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != ''){
			   if(fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)){
				   
				   fAbreSubWindow(false,"pgVerDocsCotejo","no","yes","yes","yes","800","600",50,50);
				   
			   }else{
				   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
			   }
	   }else{
		   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	   }
}

function getNumSol(){
	return frm.iNumSolicitud.value;
}

function getEjercicio(){
	return frm.iEjercicio.value;
};


function getOfic(){
	return frm.hdOfic.value;
    
}

function getDpto(){
	return frm.hdDpto.value;
}

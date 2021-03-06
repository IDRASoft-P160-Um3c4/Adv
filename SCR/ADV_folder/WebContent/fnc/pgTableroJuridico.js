var cTitulo = "";
var FRMListado = "";
var frm;
var aResTemp = new Array();
var iCveEtapa = 0;
var cPermisoPag;
var mostrarMensaje = true;

// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgTableroJuridico.js";
	cTitulo = "�VALUACI�N DE DOCUMENTOS JUR�DICOS Y LEGALES";
	cPermisoPag = fGetPermisos(cPaginaWebJS);
	fSetWindowTitle();
}
// SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
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
	Hidden("iDiasUltimaEtapa", 0);
	Hidden("iCveUsuario", fGetIdUsrSesion());

	 Hidden("hdOfic","");
	    Hidden("hdDpto","");
	FinTabla();
	FTDTR();
	FinTabla();
	fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);
	FRMListado.fSetTitulo("Ejercicio, N�m. Solicitud, Solicitante, Tr�mite, Oficina de Origen, Tiene PNC, Requisitos, Evaluaci�n,");
	FRMListado.fSetCampos("0,1,2,3,4,6,7,8,");
	FRMListado.fSetAlinea("center,center,center,center,center,center,center,center,");
	frm.hdBoton.value = "Primero";
	fCargaOficDeptoUsr(false);
}

// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega() {
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "iEjercicio,iNumSolicitud";
	frm.hdNumReg.value = 1000;
	return fEngSubmite("pgTRAEvaluacionArea.jsp", "ListadoJuridico");
}

// RECEPCI�N de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, msgRetraso) {
	if (cError == "Guardar")
		fAlert("Existi� un error en el Guardado!");
	if (cError == "Borrar")
		fAlert("Existi� un error en el Borrado!");
	if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	}
	if (cError != "") {
		fAlert('n - ' + cError);
		return;
	}

	if (cId == "ListadoJuridico" && cError == "") {
		if (aRes.length > 0) {
			
			for ( var o = 0; o < aRes.length; o++) {
				if(aRes[o][5]>0){
					aRes[o][5]="Si";
				}else{
					aRes[o][5]="";
				}
				
				if(aRes[o][6]>0){
					aRes[o][6]="Si";
				}else{
					aRes[o][6]="";
				}
				
				aRes[o].push("<font color=blue>VER REQUSITOS</font>");
				aRes[o].push("<font color=blue>EVALUACI�N DE REQUISITOS</font>");
			}
		}
		frm.hdRowPag.value = iRowPag;
		FRMListado.fSetListado(aRes);

		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
	}
	
	/****MANEJO DE CONTROL DE TIEMPOS****/
	
	if (cId == "buscaRetraso" && cError == "" ) {
		if(aRes.length>0&&parseInt(aRes[0][0])>0 && mostrarMensaje==true)
			fAlert("\nLa solicitud tiene un retraso en etapas anteriores de "+aRes[0][0]+" d�as.");
		fDiasDesdeUltimaEtapa();
	}
	
	if (cId == "obtenerDiasDesdeUltimaEtapa" && cError == "") {
		frm.iDiasUltimaEtapa.value = parseInt(aRes[0][0]);
	}
	
	if (cId == "registraRetrasoDAJLDGST" && cError == "" ) {
		if(msgRetraso!="" && parseInt(msgRetraso)>0)
			fAlert("\n Se ha registrado un retraso para esta solicitud de "+msgRetraso+ " d�as.");
		fNavega();
	}
	
	if(cId == "CIDOficinaDeptoXUsr"&&cError==""){
		   if(aRes.length > 0){
		     frm.hdOfic.value=aRes[0][1];
		     frm.hdDpto.value=aRes[0][2];
		   }
	   }
	
	
	/****MANEJO DE CONTROL DE TIEMPOS****/
	
	if(cId == "buscaDocumentosDAJL" && cError == ""){
		if(msgRetraso!=""){
			fAlert("No es posible realizar la acci�n. "+msgRetraso);
		}else{
			fAbreSubWindowSinPermisos("pgVerifSolNewB", "800", "460");
			}
	}

	fResOficDeptoUsr(aRes, cId, cError);
}

// LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
function fCancelar() {
	fDisabled(false);
	FRMListado.fSetDisabled(false);
}
// LLAMADO desde el Listado cada vez que se selecciona un rengl�n
function fSelReg(aDato, iCol) {
	
	frm.iEjercicio.value = aDato[0];
	frm.iNumSolicitud.value = aDato[1];
	
	if(frm.iEjercicio.value > 0 &&  frm.iNumSolicitud.value >0){
	
		if (iCol == 7){//click acciones
			
			if(aDato[5]=="Si"){
				fAlert("\nNo es posible realizar la evaluaci�n, la solcitud tiene un PNC que no ha sido cerrado.");
				return;
			}else{
				mostrarMensaje =false;
				fBuscaRetraso(); //
				
				setTimeout(function (){			
					fBuscaDocumentos();
				},300);
			}	
		}else if (iCol == 6){//click acciones
			fDocsTramite();
		}else{
			mostrarMensaje =true;
			fBuscaRetraso(); 
		}
	}
}





// FUNCI�N donde se generan las validaciones de los datos ingresados
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
	doRegistraRetraso();
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
				   fAlert("\n Debe proporcionar un Ejercicio y N�mero de Solicitud v�lidos.");
			   }
	   }else{
		   fAlert("\n Debe proporcionar un Ejercicio y N�mero de Solicitud v�lidos.");
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

function fBuscaDocumentos(){
	
	if (frm.iEjercicio.value>0 && frm.iNumSolicitud.value>0) {			
		frm.hdBoton.value = "buscaDocumentosDAJL";
		frm.hdFiltro.value = "IEJERCICIO ="+ frm.iEjercicio.value+ " AND INUMSOLICITUD = "+ frm.iNumSolicitud.value; 
		
	  fEngSubmite("pgGestionOficios.jsp","buscaDocumentosDAJL");
	}
}

function doRegistraRetraso(){
	if (frm.iEjercicio.value>0 && frm.iNumSolicitud.value>0) {	  
		frm.hdBoton.value="";
	    fEngSubmite("pgGestionOficios.jsp","registraRetrasoDAJLDGST");
	}
}
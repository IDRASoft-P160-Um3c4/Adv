var cTitulo = "";
var FRMListado = "";
var frm;
var aResTemp = new Array();
var iCveEtapa = 0;
var cPermisoPag;

// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgTableroJuridico.js";
	cTitulo = "OFICIOS INICIALES PARA SOLICITUDES POR INTERNET";
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
	Hidden("iCveOficina",0);
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
	FRMListado.fSetTitulo("Ejercicio, N�m. Solicitud, Solicitante, Tr�mite, Oficina de Origen, Fecha de Registro, Oficios,");
	FRMListado.fSetCampos("0,1,2,3,4,5,6,");
	FRMListado.fSetAlinea("center,center,center,center,center,center,center,");
	frm.hdBoton.value = "Primero";
	fCargaOficDeptoUsr(false);
}

// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega() {
	
	frm.hdBoton.value="ListadoInternet";
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "iEjercicio,iNumSolicitud";
	frm.hdNumReg.value = 1000;
	return fEngSubmite("pg111020015ADV.jsp", "ListadoInternet");
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

	if (cId == "ListadoInternet" && cError == "") {
	
		if (aRes.length > 0) {
			for ( var o = 0; o < aRes.length; o++) {
				aRes[o].push("<font color=blue>GENERAR OFICIOS</font>");
				}
		}
	
		frm.hdRowPag.value = iRowPag;
		FRMListado.fSetListado(aRes);
		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
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
	
	if(aDato!=null && iCol==6){
		fOficiosIniciales();
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
	frm.iCveOficina.value = frm.iCveOficinaUsr.value;
	fBuscar();
}

function fBuscar(){
	frm.iCveOficina.value = frm.iCveOficinaUsr.value;
	fNavega();
}

function fOficiosIniciales(){
	if( frm.iEjercicio.value >0 && frm.iNumSolicitud.value>0){
	 cClavesModulo="3,3,";
	 cNumerosRep="76,77,";
	 cFiltrosRep=  frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
	 cFiltrosRep+=cFiltrosRep;
	 fReportes();
	}else{
		fAlert("\n-Debe seleccionar un tr�mite para poder imprimir el acuse de recibo");
	}
}
// MetaCD=1.0
// Title: pg111020110.js
// Company: Tecnología InRed S.A. de C.V.
// Author: ABarrientos, iCaballero

var cTitulo = "";
var FRMListado = "";
var frm;
var aResTemp = new Array();
var recibeDGDC = false;
var cPermisoPag;
var tieneDatosEnvios = false;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg111020110.js";
	// if(top.fGetTituloPagina){;
	// cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	// }
	cTitulo = "RECEPCIÓN DE TRÁMITES EN D.G.D.C.";
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
	ITRTD("", 0, "", "10", "center", "top");
	InicioTabla("ETablaInfo", 0, "75%", "", "", "", 1);
	ITR();
	TextoSimple("NOTA: Antes de generar los oficios de envío requisite los siguientes campos para cada solicitud.");
	FTR();
	ITR();
	TDEtiCampo(true, "EEtiqueta", 0, "Folio Memo DGAJL:", "cFolDGAJL", "", 30,
			50, "Trámite", "fMayus(this);");
	FTD();
	TDEtiCampo(true, "EEtiqueta", 0, "Folio DGST:", "cFolDGST", "", 30, 50,
			"Trámite", "fMayus(this);");
	FTD();
	TDEtiCampo(true, "EEtiqueta", 0, "Referencia CSCT:", "cRefDGST", "", 30,
			50, "Trámite", "fMayus(this);");
	FTD();
	TDEtiCampo(true, "EEtiqueta", 0, "Fecha Referencia CSCT:", "dtDGST", "",
			50, 10, "Fecha Referencia CSCT", "fMayus(this);", "", "", false,
			"", 0);
	FTD();

	FITR();
	TDEtiAreaTexto(
			true,
			"EEtiqueta",
			0,
			"Director DGAJL:",
			50,
			2,
			"cDirDGAJL",
			"",
			"TooTip",
			"",
			"fMayus(this);",
			'onchange="fMxTx(this,250);" onkeydown="fMxTx(this,250);" onblur="fMxTx(this,250);"',
			true, true, true, "", 0);
	FTD();
	TDEtiAreaTexto(
			true,
			"EEtiqueta",
			0,
			"Director DGST:",
			50,
			2,
			"cDirDGST",
			"",
			"TooTip",
			"",
			"fMayus(this);",
			'onchange="fMxTx(this,250);" onkeydown="fMxTx(this,250);" onblur="fMxTx(this,250);"',
			true, true, true, "", 0);
	FTD();
	TDEtiAreaTexto(
			true,
			"EEtiqueta",
			0,
			"Director general DGDC:",
			50,
			2,
			"cDirGen",
			"",
			"TooTip",
			"",
			"fMayus(this);",
			'onchange="fMxTx(this,250);" onkeydown="fMxTx(this,250);" onblur="fMxTx(this,250);"',
			true, true, true, "", 0);
	FTDTR();
	FinTabla();
	FTDTR();
	ITRTD("", 0, "", "30", "center", "top");
	Liga("Recibir solicitud en D.G.D.C.", "fRecibeSolicitud();",
			"Registrar como recibida la solicitud");
	FTDTR();
	ITRTD("", 0, "", "40", "center", "bottom");
	IFrame("IPanel", "95%", "34", "Paneles.js");
	FTDTR();
	
	Hidden("hdCveUsuario", fGetIdUsrSesion());
	Hidden("hdLlave", "");
	Hidden("hdSelect", "");
	Hidden("iEjercicio", "");
	Hidden("iNumSolicitud", "");
	Hidden("iCveTramite", "");
	Hidden("iCveModalidad", "");
	Hidden("iCveEtapa", 2); //recepcion en dgdc (hacer propiedad)
	Hidden("lAnexo", "");
	
	Hidden("iDiasUltimaEtapa", 0);
	Hidden("iCveUsuario", fGetIdUsrSesion());
	
	// Hidden("hdEtapa","EtapaRegistro");
	Hidden("hdEtapa", "EtapaCotejoDoc");
	Hidden("hdEtapaGuardar", "EtapaRecepcion");
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
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");

	frm.cFolDGAJL.disabled = true;
	frm.cDirDGAJL.disabled = true;
	frm.cFolDGST.disabled = true;
	frm.cDirDGST.disabled = true;
	frm.cDirGen.disabled = true;
	frm.dtDGST.disabled = true;
	frm.cRefDGST.disabled = true;
	// FRMListado.fSetTitulo("Recibido,Anexos,Requisitos,Ejercicio,Núm.
	// Solicitud,Trámite,Modalidad,Oficina Origen,");
	FRMListado
			.fSetTitulo("Ejercicio,Núm. Solicitud,Trámite,Modalidad,Centro SCT,Oficios,");
	FRMListado.fSetCampos("0,1,4,5,8,9,");
	FRMListado.fSetAlinea("center,center,center,center,center,center,center,");
	fDisabled(false);
	frm.hdBoton.value = "Primero";

	fCargaOficDeptoUsr();
}
// LLAMADO al JSP específico para la navegación de la página

function fNavega() {
	frm.hdFiltro.value = "";
	// frm.hdFiltro.value = " (LTRAMINERNET is null or LTRAMINERNET=0) ";
	// //RECEPCION ORIGINAL
	frm.hdOrden.value = "iEjercicio,iNumSolicitud";
	frm.hdNumReg.value = 1000;
	return fEngSubmite("pgTRARecepcion.jsp", "Listado");
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, msgOficios) {
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

	if (cId == "cIdCompFol" && cError == "") {
		fGenOficios(frm.iEjercicio.value, frm.iNumSolicitud.value);
	}

	if (cId == "Listado" && cError == "") {

		if (aRes.length > 0) {
			for ( var o = 0; o < aRes.length; o++) {
				aRes[o].push("<font color=blue>OFICIOS ENVÍO</font>");
			}
		}

		aResTemp = aRes;
		frm.hdRowPag.value = iRowPag;
		FRMListado.fSetListado(aRes);

		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
		fCancelar();

		if (recibeDGDC == true) {
			fAlert("\nSe ha(n) recibido la solicitud(es) en la D.G.D.C. con éxito, ahora es posible realizar la evaluación jurídica y técnica.");
			recibeDGDC = false;
		}

		if (FRMListado.fGetLength() > 0)
			FRMPanel.fSetTraStatus("Mod,");
		else
			FRMPanel.fSetTraStatus(",");
		
		frm.iEjercicio.value = "";
		frm.iNumSolicitud.value = "";
		frm.iCveTramite.value = "";
		frm.iCveModalidad.value = "";
		frm.lAnexo.value = "";
	}

	if (cId == "datosEnvios" && cError == "") {

		if (aRes.length > 0) {
			tieneDatosEnvios=true;
			frm.cFolDGAJL.value = aRes[0][2];
			frm.cFolDGST.value = aRes[0][3];
			frm.cDirDGAJL.value = aRes[0][4];
			frm.cDirDGST.value = aRes[0][5];
			frm.cDirGen.value = aRes[0][6];
			frm.dtDGST.value = aRes[0][7];
			frm.cRefDGST.value = aRes[0][8];
			FRMPanel.fSetTraStatus(",");
		} else {
			tieneDatosEnvios=false;
			frm.cFolDGAJL.value = "";
			frm.cDirDGAJL.value = "";
			frm.cFolDGST.value = "";
			frm.cDirDGST.value = "";
			frm.cDirGen.value = "";
			frm.dtDGST.value = "";
			frm.cRefDGST.value = "";
			FRMPanel.fSetTraStatus("Mod,");			
		}
		
		if(frm.iEjercicio.value > 0&& frm.iNumSolicitud.value >0)
			fBuscaRetraso();
	}

	if (cId == "guardaDatosEnvios" && cError == "") {
		frm.cFolDGAJL.disabled = true;
		frm.cDirDGAJL.disabled = true
		frm.cFolDGST.disabled = true;
		frm.cDirDGST.disabled = true;
		frm.cDirGen.disabled = true;
		frm.dtDGST.disabled = true;
		frm.cRefDGST.disabled = true;
		tieneDatosEnvios=true;
		FRMPanel.fSetTraStatus(",");
	}
	
	if(cId == "buscaDocumentosEtapa" && cError == ""){
		if(msgOficios!=""){
			fAlert("No es posible realizar la acción. "+msgOficios);
		}else{
			fRegistraRetraso();
		}
	}
	

	/****MANEJO DE CONTROL DE TIEMPOS****/
	
	if (cId == "buscaRetraso" && cError == "" ) {
		if(aRes.length>0&&parseInt(aRes[0][0])>0)
			fAlert("\nLa solicitud tiene un retraso en etapas anteriores de "+aRes[0][0]+" días.");
		fDiasDesdeUltimaEtapa();
	}
	
	if (cId == "obtenerDiasDesdeUltimaEtapa" && cError == "") {
		frm.iDiasUltimaEtapa.value = parseInt(aRes[0][0]);
	}
	
	if (cId == "registraRetraso" && cError == "" ) {
		if(msgOficios!="" && parseInt(msgOficios)>0)
			fAlert("\n Se ha registrado un retraso para esta solicitud de "+msgOficios+ " días.");
		
		doRecibeSolicitud();
	}
	
	/****MANEJO DE CONTROL DE TIEMPOS****/

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
	frm.iCveTramite.value = aDato[2];
	frm.iCveModalidad.value = aDato[3];
			frm.lAnexo.value = "0";

	if (frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "")
		fGetDatosEnvios();

	if (iCol == 5 && frm.iEjercicio.value != ""
			&& frm.iNumSolicitud.value != "")
		fCompruebaFolio(frm.iEjercicio.value, frm.iNumSolicitud.value);

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
function fImprimir() {
	self.focus();
	window.print();
}

function fGetDatosEnvios() {
	frm.hdFiltro.value = "DE.IEJERCICIO=" + frm.iEjercicio.value
			+ " AND DE.INUMSOLICITUD=" + frm.iNumSolicitud.value;
	frm.hdNumReg.value = 10000;
	fEngSubmite("pgTRARecepcion.jsp", "datosEnvios");
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

function fBuscar() {
	fNavega();
}
function fOficinaUsrOnChangeLocal() {
	fBuscar();
}
function fDeptoUsrOnChangeLocal() {
}

function fRecibeSolicitud() {

	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	}

	if (confirm("¿Esta seguro que desea recibir el trámite?")) {
		buscaDocumentos();
	}	else{
		recibeDGDC = false;
		return;
	} 

}

function buscaDocumentos(){
	
	if (frm.iEjercicio.value>0 && frm.iNumSolicitud.value>0 && frm.iCveEtapa.value>0) {		
		frm.hdBoton.value = "buscaDocumentosEtapa";
		fEngSubmite("pgGestionOficios.jsp","buscaDocumentosEtapa");
	}
}

function doRecibeSolicitud(){
	
	if (frm.iEjercicio.value == "") {
		fAlert('\n - Seleccione un registro para realizar esta operación.');
		return;
	}

	recibeDGDC = true; 
	frm.hdBoton.value = "EtapaRecepVisita";
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "iEjercicio,iNumSolicitud";
	frm.hdNumReg.value = 1000;
	fEngSubmite("pgTRARecepcion.jsp", "Listado");
}

function fEnviaDatosMuestraRequisitos(objWindow) {
	if (objWindow && objWindow.fAsignaEjercicioSolicitud)
		objWindow.fAsignaEjercicioSolicitud(frm.iEjercicio.value,
				frm.iNumSolicitud.value);
}

function fGenOficios(ejer, sol) {
	/*
	 * cClavesModulo="3,3,3,"; cNumerosRep="24,25,26,"; cFiltrosRep= ejer+ "," +
	 * sol + "," + cSeparadorRep; cFiltrosRep+=cFiltrosRep+cFiltrosRep;
	 */

	// cClavesModulo="3,3,";
	// cNumerosRep="35,36,";
	// cFiltrosRep= ejer+ "," + sol + "," + cSeparadorRep;
	// cFiltrosRep+=cFiltrosRep;
	cClavesModulo = "3,3,";
	cNumerosRep = "46,47,";
	cFiltrosRep = ejer + "," + sol + "," + cSeparadorRep;
	cFiltrosRep += cFiltrosRep;

	fReportes();
}

function fCompruebaFolio(iEjer, iNumSol) {
	if (iEjer != "" && iNumSol != "") {
		frm.hdBoton.value = "compFol";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = 1000;
		fEngSubmite("pgTRARecepcion.jsp", "cIdCompFol");
	}
}

function fCancelarEditar() {
	if (lGuardar) {
		lGuardar = false;
		frm.cNomAutorizaRecoger.disabled = true;
		frm.iEjercicio.disabled = false;
		frm.iNumSolicitud.disabled = false;
		FRMListado.fShow();
		FRMListadoA.fShow();
	}
}

function fCancelar() {

	lModificando = false;
	if (FRMListado.fGetLength() > 0)
		FRMPanel.fSetTraStatus("Mod,");
	else
		FRMPanel.fSetTraStatus(",");

	FRMPanel.fHabilitaReporte(false);

	fDisabled(false);
	fDisabled(true, "iEjercicio,iNumSolicitud,");

	frm.cFolDGAJL.disabled = true;
	frm.cDirDGAJL.disabled = true
	frm.cFolDGST.disabled = true;
	frm.cDirDGST.disabled = true;
	frm.cDirGen.disabled = true;
	frm.dtDGST.disabled = true;
	frm.cRefDGST.disabled = true;
}

function fModificar() {
	lModificando = true;
	FRMPanel.fSetTraStatus("UpdateBegin");
	FRMPanel.fHabilitaReporte(false);
	frm.cFolDGAJL.disabled = false;
	frm.cDirDGAJL.disabled = false;
	frm.cFolDGST.disabled = false;
	frm.cDirDGST.disabled = false;
	frm.cDirGen.disabled = false;
	frm.dtDGST.disabled = false;
	frm.cRefDGST.disabled = false;
}

function fGuardarA() {
	var cMsg = fValElements();
	
	if(cMsg!=""){
		fAlert(cMsg);
		return;
	}
	
	if (frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "" && 
			confirm("Se guardará la información de los oficios de envío, una vez guardada no será posible modificarla.\n¿Desea continuar con la información en pantalla?")) {
		frm.hdBoton.value = "guardaDatosEnvios";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = 1000;
		fEngSubmite("pgTRARecepcion.jsp", "guardaDatosEnvios");
	}
}
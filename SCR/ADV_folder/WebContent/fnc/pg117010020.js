// MetaCD=1.0
// Title: pg111020080.js
// Description: JS "Catálogo" de la entidad TRARegReqXTram
// Company: Tecnología InRed S.A. de C.V.
// Author: Hanksel Fierro Medina || ICaballero || LEquihua
var cTitulo = "";
var FRMListado = "";
var frm;
var lCancelado = false;
var lModificando = false;
var aArreglo = new Array();
var lGuardar = false;
var lFueraTiempo = false;
var iCveEtapaUltimaEtapaSol = 0;
var iEtapaEntregarVU = 0;
;
var iEtapaEntregarOficialia = 0;
var iEtapaRecibeResol = 0;
var DescReqActual = "";
var ClaveReqActual = "";
var lModifica = false;

var diasTrans = -1;
var diasEntReq = -1;
var totReq = -1;
var reqEnt = -1;
var tienePNC = -1;
var diasNohabTrans = -1;
var dtNotif = "-1";
var dictamen = false;
var tieneDatosAfectacion = false;
var subio = false;
var cPermisoPag;
var accionGuardar = false;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg117010020.js";
	if (top.fGetTituloPagina) {
		;
		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	}
	cPermisoPag = fGetPermisos(cPaginaWebJS);
	fSetWindowTitle();
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJS);
	JSSource("jquery.js");
	JSSource("jquery-ui.js");

	InicioTabla("", 0, "100%", "", "", "", "1");
	ITRTD("ESTitulo", 0, "100%", "", "center");
	TextoSimple(cTitulo);
	FTDTR();
	ITRTD("", 0, "", "0", "center");
	// Liga("Buscar Solicitud","fAbreBuscaSolicitud();","Búsqueda de la
	// Solicitud");
	InicioTabla("ETablaInfo", 0, "75%", "", "", "", 1);
	ITRTD("", 0, "", "1", "center");
	InicioTabla("", 0, "100%", "", "", "", 1);
	ITRTD("ETablaST", 5, "", "", "center");
	TextoSimple("SOLICITUD");
	FTDTR();
	ITR();
	TDEtiCampo(false, "EEtiqueta", 0, "Ejercicio:", "iEjercicio", "", 6, 6,
			"Ejercicio", "fMayus(this);");
	TDEtiCampo(false, "EEtiqueta", 0, "No. Solicitud:", "iNumSolicitud", "", 6,
			6, "Solicitud", "fMayus(this);");
	ITD();
	BtnImg("Buscar", "lupa", "fNavegaDatosAfect();");
	FTD();
	FTR();
	FinTabla();
	FinTabla();
	FTDTR();
	ITRTD("", 0, "", "1", "center");
	InicioTabla("ETablaInfo", 0, "75%", "", "", "", 1);
	ITRTD("ETablaST", 5, "", "", "center");
	TextoSimple("DESCRIPCIÓN DEL REGISTRO DEL TRÁMITE");
	FTDTR();
	ITR();
	TDEtiCampo(false, "EEtiqueta", 0, "Promovente:", "cDscSolicitante", "", 50,
			50, "Ejercicio", "fMayus(this);");
	TDEtiCampo(false, "EEtiqueta", 0, "RFC:", "cDscRFC", "", 15, 15,
			"Ejercicio", "fMayus(this);");
	FITR();

	ITR();
	TDEtiCampo(false, "EEtiqueta", 0, "Trámite:", "cDscTramite", "", 50, 50,
			"Trámite", "fMayus(this);");
	TDEtiCampo(false, "EEtiqueta", 0, "Modalidad:", "cDscModalidad", "", 50,
			50, "Modalidad", "fMayus(this);");
	FITR();

	ITR();
//	TDEtiCampo(false, "EEtiqueta", 0, " Autorizado a Recoger:",
//			"cNomAutorizaRecoger", "", 50, 100,
//			" Persona Autorizada a Recoger Trámite", "fMayus(this);", "", "",
//			false, "EEtiquetaL", 0);
	ITD("", 2, "", "", "center");
	Liga("*Anexar Documentos      ", "fSubirDocsADV()", "");
	FTD();
	ITD("", 2, "", "", "center");
	Liga("*Ver Documentos Anexos      ", "fVerDocsADV()", "");
	FTD();

	FITR();
	FinTabla();
	FTDTR();
	ITRTD("", 0, "95%", "", "center");
	InicioTabla("ETablaInfo", 0, "95%", "", "", "", 1);
	ITRTD("ETablaST", 5, "", "", "center");
	TextoSimple("REQUISITOS COTEJADOS");
	FTDTR();
	ITRTD("", 0, "", "", "center");
	IFrame("IListadoA", "100%", "170", "Listado.js", "yes", true);
	FTDTR();
	FinTabla();
	FTDTR();

	FITR();
	FITR();
	ITRTD("", 0, "95%", "", "center");
	InicioTabla("ETablaInfo", 0, "95%", "", "", "", 1);
	ITRTD("ETablaST", 5, "", "", "center");
	InicioTabla("", 0, "95%", "", "", "", 1);
	ITD("ETablaST");
	TextoSimple("REQUISITOS POR COTEJAR");
	FITD()
	FinTabla();
	FTDTR();
	ITRTD("", 0, "", "", "center");
	IFrame("IListado", "100%", "170", "Listado.js", "yes", true);
	FTDTR();
	FinTabla();
	ITRTD("", 0, "95%", "", "center");
	InicioTabla("ETablaInfo", 0, "95%", "", "", "", 1);
	ITR();
	TextoSimple("NOTA: Si existen instalaciones requisite los campos para generar la Constancia de no Afectación");
	FITR();
	TDEtiAreaTexto(false, "EEtiqueta", 0, "Propietario:", 50, 2,
			"cPropietario", "", "TooTip", "", "fMayus(this);",
			'onchange="fMxTx(this,250);" onkeydown="fMxTx(this,250);" onblur="fMxTx(this,250);"', true, true, true, "", 10);
	TDEtiAreaTexto(false, "EEtiqueta", 0, "Instalaciones:", 50, 2,
			"cInstalacion", "", "TooTip", "", "fMayus(this);",
			'onchange="fMxTx(this,250);" onkeydown="fMxTx(this,250);" onblur="fMxTx(this,250);"', true, true, true, "", 10);
	TDEtiCampo(false, "EEtiqueta", 0, "Folio Notificación Dictamen:", "cNotDict", "", 50,
			50, "Folio", "fMayus(this);");
	FTDTR();
	FinTabla();
	Liga("*Generar Constancia de no Afectación y  Dictamen de Factibiidad",
			"fCompruebaFolio()", "");
		FTDTR();
	ITRTD("", 0, "", "40", "center", "bottom");
	IFrame("IPanel", "95%", "34", "Paneles.js");
	FTDTR();
	FinTabla();
	Hidden("iCveRequisito", "");
	Hidden("lEntregado", "");
	Hidden("iCveUsuRecibe", "");
	Hidden("iDiasUltimaEtapa", 0);
	Hidden("iCveEtapa", 27);//cotejo documentacion (hacer propiedad)
	
	Hidden("cConjunto");
	Hidden("iLlave");
	Hidden("hdLlave");
	Hidden("hdSelect");
	Hidden("dtNotificacion");
	Hidden("dtfechaActual");
	Hidden("cNomAutorizaRecoger","");	
	Hidden("iCveUsuario", fGetIdUsrSesion());

	Hidden("iCveTramite", "");
	Hidden("iCveModalidad", "");
	Hidden("iCveOficina", "");
	Hidden("iCveDepartamento", "");

	Hidden("hdEjer", "");
	Hidden("hdNumSol", "");

	Hidden("lAutoImprimir", false);
	Hidden("iNumCopias", 1);
	Hidden("HDcPropietario");
	Hidden("HDcInstalacion");
	Hidden("HDcNotDict");
	Hidden("lMostrarAplicacion", true);
	Hidden("cArchivoOrig", "");
	Hidden("cNomDestino", "");
	Hidden("cDAOEjecutar", "");
	Hidden("cMetodoTemp", "");
	Hidden("cDigitosFolio", "00");
	Hidden("lRequiereFolio", false);
	Hidden("hdFiltrosRep", "");
	Hidden("cFirmante", "");

	cGPD += '<div id="ALConceptos" style="text-align:justify;"></div>';
	fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);

//	FRMListado.fSetTitulo("Cotejado,Físico,Requisito,");
//	FRMListado.fSetCampos("9,5,");
//	FRMListado.fSetDespliega("texto,texto,texto,");
	
	FRMListado.fSetTitulo("Cotejado,Requisito,");
	FRMListado.fSetCampos("5,");
	FRMListado.fSetDespliega("texto,texto,");

	FRMListado.fSetAlinea("center,left,");
	FRMListado.fSetObjs(0, "Caja");

	FRMListadoA = fBuscaFrame("IListadoA");
	FRMListadoA.fSetControl(self);
	FRMListadoA.fSetSelReg(2);

//	FRMListadoA.fSetTitulo("F. Entrega,Físico,Fecha. Cotejo,Requisito,");
//	FRMListadoA.fSetCampos("3,9,4,5,");
//	FRMListadoA.fSetDespliega("texto,texto,texto,texto,");
//	FRMListadoA.fSetAlinea("center,center,center,left,");
	
	FRMListadoA.fSetTitulo("Fecha de Entrega,Fecha de Cotejo,Requisito,");
	FRMListadoA.fSetCampos("3,4,5,");
	FRMListadoA.fSetDespliega("textotexto,texto,");
	FRMListadoA.fSetAlinea("center,center,left,");


	fDisabled(true, "iEjercicio,iNumSolicitud,");
	frm.hdBoton.value = "Primero";

	frm.iCveUsuario.value = fGetIdUsrSesion();
	var dateToday = new Date();
	frm.iEjercicio.value = dateToday.getFullYear();
	frm.iNumSolicitud.focus();

	fCargaOficDeptoUsr(false);
}
// LLAMADO al JSP específico para la navegación de la página

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, msgRetraso) {
	if (cError == "Guardar") {
		fAlert("Existió un error en el Guardado!");
		return false;
	} else if (cError == "Borrar") {
		fAlert("Existió un error en el Borrado!");
		return false;
	} else if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return false;
	} else if (cError != "") {
		fAlert(cError);
		fCancelar();
	}

	if (cId == "cIdCompFol" && cError == "") {
		fNoAfectacion();
	}

	if (cId == "DatosAfec" && cError == "") {
		if (aRes.length > 0) {
			frm.cPropietario.value = aRes[0][0];
			frm.cInstalacion.value = aRes[0][1];
			frm.cNotDict.value = aRes[0][3];
			
			tieneDatosAfectacion=true;
		}else{
			frm.cPropietario.value = "";
			frm.cInstalacion.value = "";
			frm.cNotDict.value = "";
			tieneDatosAfectacion=false;
		}
		
		fBuscaSol();
	}

	if (cId == "Listado" && cError == "") {
		frm.hdRowPag.value = iRowPag;
		
		for ( var i = 0; aRes[i]; i++) {
			var cad ="";
			
			if (aRes[i][8] != "" && aRes[i][8] == "1")
				cad = "Si";
			else
				cad= "No";
			
			aRes[i].push(cad);
			
			if (aRes[i][5] != "" && aRes[i][4] == "")
				aRes[i][8] = 1;
			else
				aRes[i][8] = 0;
	
		}
		
		aArreglo = fCopiaArregloBidim(aRes);
		FRMListado.fSetListado(aRes);
		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
		FRMListado.fSetDefaultValues(0, 0);

		fCancelar();

		if (aRes.length == 0) {
			dictamen = true;
			if(accionGuardar==true){
				accionGuardar=false;
				fRegistraRetraso();
			}
			
		}else{
			if(frm.hdBoton.value == "Cambia")
				fAlert("\n Se guardo correctamente la información.");
		}
	}

	if (cId == "ListadoA" && cError == "") {
		frm.hdRowPag.value = iRowPag;
		
		  for (i=0;aRes[i];i++){	
			var cad ="";
			
			if (aRes[i][8] != "" && aRes[i][8] == "1")
				cad = "Si";
			else
				cad= "No";
			
			aRes[i].push(cad);
		  }
		
		FRMListadoA.fSetListado(aRes);
		FRMListadoA.fShow();
		FRMListadoA.fSetLlave(cLlave);
		fNavega1();

	}

	if (cId == "Reporte" && cError == "") {
		aResReporte = aRes;

		FRMPanel.fHabilitaReporte(false);
	}

	if (cId == "cIdAbandonada" && cError == "") {
		fAlert("El tiempo para entregar requisitos ha terminado. La solicitud ha sido abandonada y solo es posible cancelarla.");
		FRMPanel.fHabilitaReporte(false);
		FRMPanel.fSetTraStatus("Disabled");
		fDisabled(false);
		fDisabled(true, "iEjercicio,iNumSolicitud,");
	}

	if (cId == "cIdSolicitud" && cError == "") {
		
		if (aRes.length > 0) {

			if (aRes[0][2] != "") {
				frm.cDscSolicitante.value = aRes[0][2]
			}
			;
			if (aRes[0][3] != "") {
				frm.cDscRFC.value = aRes[0][3]
			}
			;
			if (aRes[0][4] != "") {
				frm.cDscTramite.value = aRes[0][4]
			}
			;
			if (aRes[0][5] != "") {
				frm.cDscModalidad.value = aRes[0][5]
			}
			;
			if (aRes[0][6] != "") {
				frm.cNomAutorizaRecoger.value = aRes[0][6]
			}
			;
			if (aRes[0][9] != "") {
				frm.iCveTramite.value = aRes[0][9]
			}
			;
			if (aRes[0][10] != "") {
				frm.iCveModalidad.value = aRes[0][10]
			}
			;
			if (aRes[0][11] != "") {
				tienePNC = parseInt(aRes[0][11], 10)
			}
			;
			if (aRes[0][12] != "") {
				diasTrans = parseInt(aRes[0][12], 10)
			}
			;
			if (aRes[0][13] != "") {
				diasEntReq = parseInt(aRes[0][13], 10)
			}
			;

			if (tienePNC > 0) {
				if (aRes[0][14] != "") {
					diasNohabTrans = parseInt(aRes[0][14], 10)
				}
				;
				if (aRes[0][15] != "") {
					dtNotif = aRes[0][15]
				}
				;

			} else {

				if (aRes[0][14] != "") {
					totReq = parseInt(aRes[0][14], 10)
				}
				;
				if (aRes[0][15] != "") {
					reqEnt = parseInt(aRes[0][15], 10)
				}
				;
				if (aRes[0][16] != "") {
					diasNohabTrans = parseInt(aRes[0][16], 10)
				}
				;

			}

			if (tienePNC > 0) {
				if (dtNotif != "-1") {

					//if (((diasTrans - diasNohabTrans) > diasEntReq)) {//descomentar para que sean dias naturales, es necesaria la gestion de dias no habiles en las pantallas del administrador
					
					if (diasTrans > diasEntReq) {
						// fAlert("El tiempo para entregar requisitos ha
						// terminado. La solicitud ha sido abandonada.");
						FRMPanel.fHabilitaReporte(false);
						fAbandonar();
					} else {
						//fNavegaDatosAfect();
						fBuscaRetraso();
					}
				} else {
					fAlert("No es posible cotejar documentos para esta solicitud. No se ha notificado del PNC al promovente.");
					FRMPanel.fHabilitaReporte(false);
				}

			} else {

				//if (((diasTrans - diasNohabTrans) > diasEntReq)) {//descomentar para que sean dias naturales, es necesaria la gestion de dias no habiles en las pantallas del administrador
								
				if ((diasTrans > diasEntReq)
						&& ((totReq - reqEnt) > 0)) {
					// fAlert("El tiempo para entregar requisitos ha terminado.
					// La solicitud ha sido abandonada.")
					FRMPanel.fHabilitaReporte(false);
					fAbandonar();
				} else {
					fBuscaRetraso();
				}
			}

		} else {
			fAlert("\n La solicitud no existe o se encuentra en una etapa en la cual no es posible realizar esta operación.");
		}

	}
	
	if (cId == "etapaCotejo" && cError == "" ) {
			fAlert("\n Se ha terminado de cotejar la documentación para esta solicitud. Ahora es posible generar los oficios.");
	}

	if (cId == "CIDOficinaDeptoXUsr") {
		if (aRes.length > 0) {
			frm.iCveOficina.value = aRes[0][1];
			frm.iCveDepartamento.value = aRes[0][2];
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
		fNavega();
	}
	
	if (cId == "registraRetraso" && cError == "" ) {
		if(msgRetraso!="" && parseInt(msgRetraso)>0)
			fAlert("\n Se ha registrado un retraso para esta solicitud de "+msgRetraso+ " días.");
		
		fEtapaCotejo();
	}
	
	/****MANEJO DE CONTROL DE TIEMPOS****/
	
	if(cId == "buscaDocumentosEtapa" && cError == ""){
		if(msgRetraso!=""){
			fAlert("No es posible realizar la acción. "+msgRetraso);
		}else{
			preparaCamposModificar();
		}
	}
	
	return true;
}

function fBuscaDocumentos(){
	
	if (frm.iEjercicio.value>0 && frm.iNumSolicitud.value>0 && frm.iCveEtapa.value>0) {		
			
		frm.hdBoton.value = "buscaDocumentosEtapa";
		frm.hdFiltro.value = "IEJERCICIO ="+ frm.iEjercicio.value+ " AND INUMSOLICITUD = "+ frm.iNumSolicitud.value; 
		
	  fEngSubmite("pgGestionOficios.jsp","buscaDocumentosEtapa");
	}
}

function fEtapaCotejo(){
	frm.hdBoton.value = "etapaCotejo";
	frm.hdFiltro.value = "";
	fEngSubmite("pgTRACotejoDoc.jsp", "etapaCotejo");
}

function fBuscaSol() {
	if (frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "") {

		frm.hdEjer.value = frm.iEjercicio.value;
		frm.hdNumSol.value = frm.iNumSolicitud.value;

		frm.hdBoton.value = "GetSolicitudCotejo";
		frm.hdFiltro.value = "S.iEjercicio= " + frm.iEjercicio.value
				+ " AND S.iNumSolicitud = " + frm.iNumSolicitud.value
				+ " AND S.ICVEOFICINA=" + frm.iCveOficina.value
				+ " AND S.ICVEDEPARTAMENTO=" + frm.iCveDepartamento.value
				+ " AND S.LABANDONADA=0";

		frm.hdOrden.value = "";
		frm.hdNumReg.value = 100;
		fEngSubmite("pgTRAModSolicitud.jsp", "cIdSolicitud");
	} else {
		fAlert("Introduzca el Ejercicio y el Número de Solicitud para hacer la búsqueda");
	}

}

function fAbandonar() {

	frm.hdBoton.value = "Abandonada";
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "";
	frm.hdSelect.value = "";
	fEngSubmite("pgTRAModSolicitud.jsp", "cIdAbandonada");

}

function fNavegaDatosAfect() {

	frm.hdFiltro.value = " S.IEJERCICIO=" + frm.iEjercicio.value
			+ " AND S.INUMSOLICITUD=" + frm.iNumSolicitud.value
			+ " AND S.LABANDONADA= 0";
	frm.hdNumReg.value = 10000;
	fEngSubmite("pgTRACotejoDoc.jsp", "DatosAfec");
}

function fNavega1() {
	frm.hdFiltro.value = " RR.DTCOTEJO IS NULL" + " AND S.IEJERCICIO="
			+ frm.iEjercicio.value + " AND S.INUMSOLICITUD="
			+ frm.iNumSolicitud.value + " AND S.LABANDONADA= 0";
	frm.hdNumReg.value = 10000;
	fEngSubmite("pgTRACotejoDoc.jsp", "Listado");
}

function fNavega() {
	frm.hdFiltro.value = " RR.DTCOTEJO IS NOT NULL" + " AND S.IEJERCICIO="
			+ frm.iEjercicio.value + " AND S.INUMSOLICITUD="
			+ frm.iNumSolicitud.value + " AND S.LABANDONADA= 0";
	frm.hdNumReg.value = 10000;
	fEngSubmite("pgTRACotejoDoc.jsp", "ListadoA");
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
	fDesactiva();
	if (FRMListado.fGetLength() > 0)
		FRMPanel.fSetTraStatus("Mod,");
	else
		FRMPanel.fSetTraStatus(",");

	FRMPanel.fHabilitaReporte(false);

	fDisabled(false);
	fDisabled(true, "iEjercicio,iNumSolicitud,");
}

function fDesactiva() {
	for ( var i = 0; i < aArreglo.length; i++) {
		oCaja = eval("FRMListado.document.forms[0].OCajaB0" + i);
		if (lCancelado == false && lModificando == true)
			oCaja.disabled = false;
		else
			oCaja.disabled = true;
	}
}

function fModificar() {
	
	if(tienePNC>0){
		fBuscaDocumentos();
	}else{
		preparaCamposModificar();
	}
	
}

function preparaCamposModificar(){
	lModificando = true;
	FRMPanel.fSetTraStatus("UpdateBegin");
	FRMPanel.fHabilitaReporte(false);
	fDisabled(true);

	frm.HDcPropietario.disabled = false;
	frm.HDcInstalacion.disabled = false;
	frm.HDcNotDict.disabled =false;

	if(tieneDatosAfectacion==false){
		frm.cPropietario.disabled = false;
		frm.cInstalacion.disabled = false;
		frm.cNotDict.disabled =false;
	}

	FRMListado.fSetDisabled(false);	
}



function fGuardarA() {
	// alert("fGuardarA");
	aCBox = FRMListado.fGetObjs(0);
	aRes = FRMListado.fGetARes();
	lModificando = false;
	frm.cConjunto.value = "";
	accionGuardar =true;

	for ( var aux = 0; aux < aCBox.length; aux++) {
		// alert("aCBox[aux]----->"+aCBox[aux]);
		if (aCBox[aux] == true) {
			if (aRes[aux][2] != "") {
				frm.iLlave.value = 1;
				if (frm.cConjunto.value == "") {
					frm.cConjunto.value = aRes[aux][2];
				} else
					frm.cConjunto.value += "," + aRes[aux][2];
			}
		}
	}

	frm.HDcPropietario.value = frm.cPropietario.value;
	frm.HDcInstalacion.value = frm.cInstalacion.value;
	frm.HDcNotDict.value =frm.cNotDict.value;

	if (frm.iLlave.value == 0)
		frm.cConjunto.value = -1;
	frm.hdBoton.value = "Cambia";
	lModifica = true;

	if (frm.cConjunto.value == -1) {
		fAlert("\n Debe marcar como cotejado al menos un requisito.");
	} else {
		if(tieneDatosAfectacion==false){
			if (confirm("\n-Una vez cotejados TODOS los requisitos no podrá anexar más documentos asociados a requisitos." +
					    "\n-Se guardará la información de instalaciones, una vez guardada no podrá modificar la información." +
					    "\n\n¿Desea continuar con la información en pantalla?")) {
				fNavega();
			}
		}else{
			if (confirm("\n-Una vez cotejados TODOS los requisitos no podrá anexar más documentos asociados a requisitos.\n\n¿Desea continuar con la información en pantalla?")) {
				fNavega();
			}
		}
	}
}

function fSelReg(aDato, iCol) {
//	frm.iCveTramite.value = aDato[0];
//	frm.iCveModalidad.value = aDato[1];
	frm.iCveRequisito.value = aDato[2];
	frm.dtNotificacion.value = aDato[4];
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

function fReporte() {
	// if (lEjecuta)
	cClavesModulo = "2,";
	cNumerosRep = "3,";
	cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value
			+ cSeparadorRep;
	fReportes();
	// else
	// fAlert("\n-Debe seleccionar un trámite para poder imprimir el acuse de
	// recibo");
}

function fDictamenFactibilidad() {
	/*
	 * cClavesModulo="3,3,3,"; cNumerosRep="24,25,26,"; cFiltrosRep=
	 * frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," +
	 * cSeparadorRep; cFiltrosRep+=cFiltrosRep+cFiltrosRep; fReportes();
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	if (dictamen == true) {
		cClavesModulo = "3,";
		cNumerosRep = "17,";
		cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value
				+ "," + cSeparadorRep;
		fReportes();
	} else {
		fAlert("\n No es posible generar los formatos, debe cotejar todos los requisitos de la solicitud.");
	}
}

function fNoAfectacion() { // SE AGREGA EL DICTAMEN DE FACTIBILIDAD JUNTO CON
	// EL DE NO AFECTACION

//	if(tienePNC>0){ por si solicitan el acuse de solventacion
//		cClavesModulo = "3,";
//		cNumerosRep = "78,";
//		cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value + ","
//				+ cSeparadorRep;
//	}else{
		cClavesModulo = "3,3,3,3,3,";
		cNumerosRep = "42,45,43,44,81,";
		cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value + ","
				+ cSeparadorRep;
		cFiltrosRep += cFiltrosRep + cFiltrosRep + cFiltrosRep+ cFiltrosRep;
//	}
	
	fReportes();
}

/*
 * function fDictamenFact(){ cClavesModulo="4,"; cNumerosRep="17,"; cFiltrosRep=
 * frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
 * fReportes(); }
 */

function fCompruebaFolio() {
	
	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	}

	if (dictamen == true) {
		var iEjer = frm.iEjercicio.value;
		var iNumSol = frm.iNumSolicitud.value;

		if (iEjer != "" && iNumSol != "") {
			frm.hdBoton.value = "compFolNoAfec";
			frm.hdFiltro.value = "";
			frm.hdOrden.value = "";
			frm.hdNumReg.value = 1000;
			fEngSubmite("pgVerificacionArea.jsp", "cIdCompFol");
		}
	} else {
		fAlert("\n No es posible generar la Constancia de No Afectación debe cotejar todos los requisitos de la solicitud.");
	}

}

function fSubirDocsADV() {
	
	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	}
	
	if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != ''
			&& frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {
		if (fSoloNumeros(frm.iEjercicio.value)
				&& fSoloNumeros(frm.iNumSolicitud.value)) {

			fAbreSubWindow(false, "pgSubirDocsCotejo", "no", "yes", "yes",
					"yes", "800", "600", 50, 50);

		} else {
			fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
		}
	} else {
		fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	}
}

function fVerDocsADV() {
	if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != ''
			&& frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {
		if (fSoloNumeros(frm.iEjercicio.value)
				&& fSoloNumeros(frm.iNumSolicitud.value)) {

			fAbreSubWindow(false, "pgVerDocsCotejo", "no", "yes", "yes", "yes",
					"800", "600", 50, 50);

		} else {
			fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
		}
	} else {
		fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	}

}

function getNumSol() {
	return frm.iNumSolicitud.value;
}

function getEjercicio(){
	return frm.iEjercicio.value;
};


function getTienePNC() {
	return tienePNC;
}

function getSubio() {
	return subio;
}

function setSubio(valor) {
	subio = valor;
}
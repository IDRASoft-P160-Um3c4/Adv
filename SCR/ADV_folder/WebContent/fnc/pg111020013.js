// MetaCD=1.0
// Title: pg111020013.js
// Description: JS "Catálogo" de la entidad TRARegReqXTram
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda<dd>Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm;
var cNumSolicitud = "";
var cEjercicio = "";
var iAux = 0;
var aResSolicitudes = new Array();
var aDatosReferencia = new Array();
var lHabImpresas = false;
var lExistenImpresas = false;
var lEjecutado = true;
var lImprocedente = false;
var lRequierePago = false;
var iEjercicioSel = 0;
var iNumSolSel = 0;
var aSeleccion = new Array();
var aSeleccionados = new Array();
var aListado;
var lSelAutomatico = false;
var iSeleccionadosEj = 0;
var iSeleccionadosiN = 0;
var lCheck = false;
var lColoca = 0;
var cColoca = "";
var lInicioDeTramite = false;
var FRMContenedor = null;
var aSolMarcadas = new Array();

var idOf, idDp;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg111020013.js";
	if (top.fGetTituloPagina) {
		;
		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	}
	if (fGetPermisos("pg111020013p.js") == 2)
		lHabImpresas = false;
	else
		lHabImpresas = true;
}

function fDefPag() {
	fInicioPagina(cColorGenJS);
	InicioTabla("", 0, "100%", "", "", "", "1");

	ITRTD("EEtiquetaC", 0, "95%", "0", "center");
	InicioTabla("", 0, "95%", "", "center");
	ITRTD("", 0, "", "0", "center", "top");
	IFrame("IFiltro13", "0", "0", "Filtros.js");
	FTDTR();
	FinTabla();
	FTDTR();

	ITRTD("", 0, "", "1", "center");
	InicioTabla("ETablaInfo", 0, "95%", "", "", "", 1);
	ITRTD("ETablaST", 6, "", "", "center");
	TextoSimple("Solicitudes Registradas para el Promovente");
	BtnImgNombre("vgbuscar", "lupa", "if(fNavega(true));",
			"Actualizar solicitudes del promovente", false, "",
			"BuscarSolicitudes");
	FTDTR();
	ITR();
	TDEtiCheckBox("EEtiqueta", 0, "Mostrar Impresas:", "lImpresasBOX", "0",
			true, " Activo", "", "");
	// LigaNombre("aaaa","window.parent.blankCamposFrm();","Registrar
	// trámite...","RegistraTramite");
	TDEtiCampo(false, "EEtiqueta", 0, "Ejercicio Solicitud:", "iEjercicioSol",
			"", 4, 4, "Ejercicio de la solicitud", "", "", "", "", "ECampo", 0);
	TDEtiCampo(false, "EEtiqueta", 0, "Número de Solicitud:",
			"iNumSolicitudBusca", "", 10, 10, "Número de Solicitud", "", "",
			"", "", "ECampo", 0);
	// TDEtiCheckBox("EEtiqueta",0,"Mostrar Impresas:","lImpresasBOX","0",true,"
	// Activo","","onClick=fNavega();");
	FTR();
	ITR();
	TDEtiCampo(true, "EEtiqueta", 0, " Promovente:", "Persona_cNomRazonSocial",
			"", 50, 50, " Promovente", "fMayus(this);", "", "", false,
			"EEtiquetaL", 0);
	TDEtiCampo(true, "EEtiqueta", 0, " Rep. Legal:",
			"RepLegal_cNomRazonSocial", "", 50, 50, " Representante Legal",
			"fMayus(this);", "", "", false, "EEtiquetaL", 3);
	FITR();
	TDEtiAreaTexto(false, "EEtiqueta", 0, "Dom. Promovente:", 49, 3,
			"Persona_cDscDomicilio", "", "Domicilio", "fMayus(this);", "", "",
			false, false, false, "EEtiquetaL", 0);
	TDEtiAreaTexto(false, "EEtiqueta", 0, "Dom. Rep. Legal:", 49, 3,
			"RepLegal_cDscDomicilio", "", "Domicilio", "fMayus(this);", "", "",
			false, false, false, "EEtiquetaL", 3);
	FITR();
	TDEtiCampo(false, "EEtiqueta", 0, " R.F.C.:", "cRFC", "", 14, 13,
			" R.F.C.", "fMayus(this);", "", "", false, "EEtiquetaL", 0);
//	TDEtiAreaTexto(false, "EEtiqueta \" rowspan=\"2", 0, "Observación:", 49, 4,
//			"cObsTramite", "", "Observaciones", "fMayus(this);", "", "", false,
//			false, false, "EEtiquetaL\" rowspan=\"3", 3);
//	FITR();
//	TDEtiCampo(false, "EEtiqueta", 0, " Autorizado a Recoger:",
//			"cNomAutorizaRecoger", "", 50, 100,
//			" Persona Autorizada a Recoger Trámite", "fMayus(this);", "", "",
//			false, "EEtiquetaL", 0);
	FTR();
	// Liga("Pagar varias solicitudes","fPagaSol();");
	FinTabla();

	FTDTR();

	ITRTD("", 0, "95%", "0", "center", "top");
	IFrame("IListado13", "95%", "170", "Listado.js", "yes", true);
	FTDTR();

	ITRTD("", 0, "", "40", "center", "bottom");
	IFrame("IPanel13", "95%", "34", "Paneles.js");
	FTDTR();
	
	Hidden("cNomAutorizaRecoger","");
	Hidden("cObsTramite","");

	Hidden("iCveTipoPersona");
	Hidden("iCveSolicitante");
	Hidden("iCveDomicilioSolicitante");
	Hidden("iCveRepLegal");
	Hidden("iCveDomicilioRepLegal");

	Hidden("cFiltroImpreso");
	Hidden("lImpreso");

	Hidden("iEjercicio");
	Hidden("iNumSolicitud");
	Hidden("cDscTramite");
	Hidden("iCveTramite");
	Hidden("iCveModalidad");

	Hidden("Mov_iRefNumerica");
	Hidden("Mov_iNumUnidades");
	Hidden("Mov_cRefAlfaNum");
	Hidden("Mov_dImportePagar");
	Hidden("Mov_iCveConcepto");
	Hidden("Mov_iCveSolicitanteIngresos");
	Hidden("Mov_dFechaRef");
	Hidden("lMovGenerados");
	Hidden("lPagoAnticipadoNoPagado", "");
	Hidden("hdBien", "");
	Hidden("dUnidCalculo", "");
	Hidden("hdIdBien", "");

	Hidden("hdCveOfic");

	Hidden("hdCveDpto");

	Hidden("hdLlave");
	Hidden("hdSelect");
	FinTabla();
	fFinPagina();
}

function fTodos(theCheck) {
	fSeleccionaTodosEnListado(FRMListado, 0, theCheck);
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];

	FRMPanel = fBuscaFrame("IPanel13");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fHabilitaReporte(true);
	FRMPanel.fShow(",");

	FRMListado = fBuscaFrame("IListado13");
	FRMListado.fSetControl(self);

//	FRMListado
//			.fSetTitulo(fCheckBoxTodos("cbTodos", "fTodos")
//					+ ",Ejercicio,Núm. Sol.,Aprovechamiento,Carretera,Cadenamientos y Sentidos,F.Visita Técnica,Oficina de Origen,");
//	FRMListado.fSetObjs(0, "Caja");
//	FRMListado.fSetCampos("0,1,15,22,23,24,21,");
//	FRMListado
//			.fSetAlinea("center,center,center,center,center,center,center,center,");
//	FRMListado.fSetDespliega("text,text,text,text,text,text,text,text,");

	FRMListado
	.fSetTitulo("Ejercicio,Núm. Sol.,Aprovechamiento,Carretera,Cadenamientos y Sentidos,F.Visita Técnica,Oficina de Origen,");
FRMListado.fSetCampos("0,1,15,22,23,24,21,");
FRMListado
	.fSetAlinea("center,center,center,center,center,center,center,");
FRMListado.fSetDespliega("text,text,text,text,text,text,text,");

	
	FRMFiltro = fBuscaFrame("IFiltro13");
	FRMFiltro.fSetControl(self);
	FRMFiltro.fShow(",");

	fDisabled(true, "lImpresasBOX,iEjercicioSol,iNumSolicitudBusca,");
	frm.hdBoton.value = "Primero";
}

function fNavega(lMensaje) {
	frm.hdFiltro.value = "";
	if (frm.iCveSolicitante.value != "")
		frm.hdFiltro.value = " RS.iCveSolicitante = "
				+ frm.iCveSolicitante.value + " ";
	if (frm.iEjercicioSol.value != "") {
		if (frm.hdFiltro.value != "")
			frm.hdFiltro.value += " AND ";
		frm.hdFiltro.value += " RS.IEJERCICIO = " + frm.iEjercicioSol.value
				+ " ";
		if (frm.iNumSolicitudBusca.value != "")
			frm.hdFiltro.value += " AND RS.INUMSOLICITUD = "
					+ frm.iNumSolicitudBusca.value + " ";
	}
	frm.hdFiltro.value += ((frm.hdFiltro.value != "") ? " AND " : " ");
	frm.hdFiltro.value += " RS.lImpreso = "
			+ ((frm.lImpresasBOX.checked) ? "1" : "0") + " ";
	frm.hdOrden.value = FRMFiltro.fGetOrden();
	frm.hdNumReg.value = FRMFiltro.fGetNumReg();

	if (frm.iCveSolicitante.value != ""
			|| (frm.iEjercicioSol.value != "" && frm.iNumSolicitudBusca.value != "")) {

		if (frm.hdCveOfic.value != "" && frm.hdCveDpto.value != "") {

			lExistenImpresas == frm.lImpresasBOX.checked;
			fEngSubmite("pgTRARegSolicitud2.jsp", "Listado");
		} else {
			fCargaOficDeptoUsr(false);
		}

	} else if (lMensaje == true)
		fAlert("\n- Debe proporcionar un usuario o bien ejercicio y solicitud específicos");
}

//function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError) {
//	var aReportes = fCopiaArreglo(aRes);
//	var aDatoRep = fCopiaArreglo(aDato);
//	if (cError == "") {
//		frm.hdBoton.value = "";
//		frm.cFiltroImpreso.value = cFiltro;
//		frm.hdFiltro.value = " RS.iCveSolicitante = "
//				+ frm.iCveSolicitante.value + " AND RS.lImpreso = "
//				+ ((frm.lImpresasBOX.checked) ? "1" : "0") + " ";
//		fEngSubmite("pgTRARegSolicitud2.jsp", "ImpresoActualizado");
//	}
//	
////	  if (theWindow){ theWindow.close(); }
////	 
////	lEjecutado = true;
//}

function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave) {
	if (cError != "") {
		fAlert(cError);
		FRMFiltro.fSetNavStatus("Record");
	} else {
//		if (cId == "ImpresoActualizado"){
//			FRMListado.fSetListado(new Array());
//			FRMListado.fShow();
//			window.parent.blankCamposFrm();// fNavega();
//		}

		if (cId == "MovimientoGenerado") {
			fNavega();
			fAlert('Los movimientos se guardaron exitosamente');
		}
		if (cId == "Listado") {
			
			aResSolicitudes = fCopiaArreglo(aRes);
			frm.hdRowPag.value = iRowPag;
			FRMListado.fSetListado(aResSolicitudes);
			FRMListado.fShow();
			FRMListado.fSetLlave(cLlave);
			FRMFiltro.fSetNavStatus(cNavStatus);
			if (lColoca == 1)
				fReposicionaListado(FRMListado, "0,1", cColoca);
			frm.hdFiltro.value = "";
		}
		if (cId == "idMovimientos" && cError == "") {
			if (aRes.length == 0) {
				fAbreSubWindow(true, "pg111020170", 'yes', "yes", "yes", "yes",
						800, 600, "", "");
			} else {
				fAlert("La solicitud ya tiene movimientos");
			}
		}
		if (cId == "idRelacionadas" && cError == "") {
			if (aRes.length > 0) {
				if (aRes[0][0] == iEjercicioSel && aRes[0][1] == iNumSolSel) {
					aSeleccionados = new Array();
					aSeleccion = new Array();
					aSeleccion[0] = aRes[0][0];
					aSeleccion[1] = aRes[0][1];
					aSeleccionados[0] = aSeleccion;
					for (o = 0; o < aRes.length; o++) {
						aSeleccion = new Array();
						aSeleccion[0] = aRes[o][0];
						aSeleccion[1] = aRes[o][2];
						aSeleccionados[o + 1] = aSeleccion;
					}

					iSeleccionadosEj = aSeleccionados[0][0];
					iSeleccionadosiN = aSeleccionados[0][1];
					lSelAutomatico = true;
				} else {
					k = 0;
					while (k < aRes.length && aRes[k][0] != iEjercicioSel
							&& aRes[k][2] != iNumSel) {
						k++;
					}
					if (aRes[k][0] == iEjercicioSel && aRes[k][2] == iNumSolSel) {
						fBuscaRelacionadas(aRes[k][0], aRes[k][1]);
					}
				}
				fSelecciona();
			}
		}
		if (cId == "DatosSolicBusquedaDirecta" && aRes && aRes.length
				&& aRes.length > 0) {
			if (FRMContenedor) {
				FRMContenedor.iCvePersona.value = aRes[0][0];
				FRMContenedor.cRFC.value = aRes[0][1];
				FRMContenedor.cRPA.value = aRes[0][2];
				FRMContenedor.iTipoPersona.value = aRes[0][3];
				FRMContenedor.cNombreSolo.value = aRes[0][4];
				FRMContenedor.cApPaterno.value = aRes[0][5];
				FRMContenedor.cApMaterno.value = aRes[0][6];
				FRMContenedor.cCorreoE.value = aRes[0][7];
				FRMContenedor.cPseudonimoEmp.value = aRes[0][8];
				FRMContenedor.iCveDomicilio.value = aRes[0][9];
				FRMContenedor.iCveTipoDomicilio.value = aRes[0][10];
				FRMContenedor.cCalle.value = aRes[0][11];
				FRMContenedor.cNumExterior.value = aRes[0][12];
				FRMContenedor.cNumInterior.value = aRes[0][13];
				FRMContenedor.cColonia.value = aRes[0][14];
				FRMContenedor.cCodPostal.value = aRes[0][15];
				FRMContenedor.cTelefono.value = aRes[0][16];
				FRMContenedor.iCvePais.value = aRes[0][17];
				FRMContenedor.cDscPais.value = aRes[0][18];
				FRMContenedor.iCveEntidadFed.value = aRes[0][19];
				FRMContenedor.cDscEntidadFed.value = aRes[0][20];
				FRMContenedor.iCveMunicipio.value = aRes[0][21];
				FRMContenedor.cDscMunicipio.value = aRes[0][22];
				FRMContenedor.iCveLocalidad.value = aRes[0][23];
				FRMContenedor.cDscLocalidad.value = aRes[0][24];
				FRMContenedor.lPredeterminado.value = aRes[0][25];
				FRMContenedor.cDscTipoDomicilio.value = aRes[0][26];
				FRMContenedor.cDscDomicilio.value = aRes[0][34];
				FRMContenedor.cNomRazonSocial.value = aRes[0][33];
			}
			frm.iCveSolicitante.value = aRes[0][0];
			frm.iCveDomicilioSolicitante.value = aRes[0][9];
			frm.Persona_cNomRazonSocial.value = aRes[0][33];
			frm.Persona_cDscDomicilio.value = aRes[0][34];
			frm.cRFC.value = aRes[0][1];
			frm.iCveTipoPersona.value = aRes[0][3];

			if (parseInt(aRes[0][3], 10) > 1) {
				frm.iCveRepLegal.value = aRes[0][35];
				frm.iCveDomicilioRepLegal.value = aRes[0][36];
				frm.RepLegal_cNomRazonSocial.value = aRes[0][37];
				frm.RepLegal_cDscDomicilio.value = aRes[0][38];
			} else {
				frm.iCveRepLegal.value = "";
				frm.iCveDomicilioRepLegal.value = "";
				frm.RepLegal_cNomRazonSocial.value = "NO APLICA PARA PERSONAS FÍSICAS";
				frm.RepLegal_cDscDomicilio.value = "";
			}

			frm.iEjercicio.value = aRes[0][29];
			frm.iNumSolicitud.value = aRes[0][30];
			frm.cNomAutorizaRecoger.value = aRes[0][31];
			frm.cObsTramite.value = aRes[0][32];
			frm.iCveTramite.value = aRes[0][27];
			frm.iCveModalidad.value = aRes[0][28];
			frm.hdBien.value = aRes[0][39];
			frm.hdIdBien.value = aRes[0][40];
			frm.dUnidCalculo.value = aRes[0][41];
			frm.cDscTramite.value = aRes[0][42];

		}
		if (cId == "cIdFechaImp" && cError == "") {
			fReportes();
		}

		if (cId == "CIDOficinaDeptoXUsr" && cError == "") {
			frm.hdCveOfic.value = aRes[0][1];
			frm.hdCveDpto.value = aRes[0][2];
			fNavega();
		}
	}
}

function fValidaMovimientos() {
	if (lImprocedente) {
		fAlert('\n - Esta opción no es válida para trámites improcedentes.');
		return;
	}
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "";
	frm.hdNumReg.value = 100000;
	frm.hdLlave.value = "IEJERCICIO,INUMSOLICITUD";
	select = "SELECT traregrefpago.IEJERCICIO, traregrefpago.INUMSOLICITUD, "
			+ "traregrefpago.ICONSECUTIVO, traregrefpago.IREFNUMERICA, traregrefpago.DTMOVIMIENTO "
			+ "FROM traregrefpago " + "WHERE traregrefpago.IEJERCICIO =  "
			+ frm.iEjercicio.value + " AND traregrefpago.INUMSOLICITUD = "
			+ frm.iNumSolicitud.value;
	frm.hdSelect.value = select;
	fEngSubmite("pgConsulta.jsp", "idMovimientos");
}

// function fCBuscaTipoSol(iEjer,iSol){
function fBuscaRelacionadas(iEjer, iSol) {
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "";
	frm.hdNumReg.value = 100000;
	frm.hdLlave.value = "IEJERCICIO,INUMSOLICITUDPRINC";
	select = "SELECT IEJERCICIO, INUMSOLICITUDPRINC, INUMSOLICITUDREL  "
			+ "FROM TRASOLICITUDREL " + "WHERE IEJERCICIO =  " + iEjer
			+ " AND (INUMSOLICITUDPRINC = " + iSol + " OR INUMSOLICITUDREL = "
			+ iSol + ")";
	frm.hdSelect.value = select;
	iEjercicioSel = iEjer;
	iNumSolSel = iSol;
	if (iEjer && iSol && iEjer != "" && iSol != "")
		fEngSubmite("pgConsulta.jsp", "idRelacionadas");
}

/*
 * function fBuscaRelacionadas(iEjer,iSol){ frm.hdFiltro.value = "";
 * frm.hdOrden.value = ""; frm.hdNumReg.value = 100000; frm.hdLlave.value =
 * "IEJERCICIO,INUMSOLICITUDPRINC"; select = "SELECT IEJERCICIO,
 * INUMSOLICITUDPRINC, INUMSOLICITUDREL " + "FROM TRASOLICITUDREL " + "WHERE
 * IEJERCICIO = " + iEjer + " AND (INUMSOLICITUDREL = " +
 * frm.iNumSolicitud.value + " OR "; frm.hdSelect.value = select;
 * fEngSubmite("pgConsulta.jsp","idMovimientos"); }
 */

function fSelReg(aDato, iCol, lChecked) {
	frm.iEjercicio.value = aDato[0];
	frm.iNumSolicitud.value = aDato[1];
	frm.iCveTramite.value = aDato[2];
	frm.iCveModalidad.value = aDato[3];
	frm.cNomAutorizaRecoger.value = aDato[6];
	frm.lImpreso.value = aDato[12];
	frm.cObsTramite.value = aDato[14];
	frm.cDscTramite.value = aDato[15];

	if (aDato[16] != "" && parseInt(aDato[18], 10) == 0)
		lImprocedente = true;
	else
		lImprocedente = false;

	frm.hdBien.value = aDato[17];
	frm.dUnidCalculo.value = aDato[19];
	frm.hdIdBien.value = aDato[20];
	frm.lPagoAnticipadoNoPagado.value = aDato[21];
	frm.lMovGenerados.value = aDato[22];
	if (aDato[parseInt(aDato.length, 10) - 1] != ""
			&& parseInt(aDato[parseInt(aDato.length, 10) - 1], 10) == 1)
		lRequierePago = true;
	else
		lRequierePago = false;

	if (iCol == 6 || iCol == 7) {
		if (parseInt(aDato[parseInt(aDato.length, 10) - 1], 10) == 0)
			fAlert('\n - Esta opción solo es válida para trámites que requieren pago.');
		else {
			if (iCol == 6) {
				// fValidaMovimientos();
			} else
				fAbreRegistraPago();
		}
	}
	if (iCol == 0) {
		lCheck = lChecked;
		// fBuscaRelacionadas(aDato[0],aDato[1]);
	}
	if (frm.iEjercicioSol.value != "" && frm.iNumSolicitudBusca.value != "")
		if (aDato[0] != "" && aDato[1] != "")
			fGetDatosSolicitanteBuscaDirecto();
		else
			fLimpiaCamposBuscaDirecto();
}

function fImprimir() {
	self.focus();
	window.print();
}


//function fReporte() {
//	aCBoxTra = FRMListado.fGetObjs(0);
//	var lEjecuta = false;
//	cClavesModulo = "2,";
//
//	cNumerosRep = "1,";
//	if (frm.lImpreso.value == 1)
//		cNumerosRep = "15,";
//	cFiltrosRep = "";
//	lSelAutomatico = false;
//	lContieneImpresas = false;
//	cMsg = "";
//	for ( var i = 0; i < aCBoxTra.length; i++) {
//		if (aCBoxTra[i] == true) {
//			if (aResSolicitudes[i][12] == 1)
//				lContieneImpresas = true;
//			if (aResSolicitudes[i][21] == 1 && aResSolicitudes[i][22] == 0
//					&& aResSolicitudes[i][23] == 1
//					&& aResSolicitudes[i][16] == "")
//				cMsg += "\nEjercicio: " + aResSolicitudes[i][0] + " Número: "
//						+ aResSolicitudes[i][1];
//			lEjecuta = true;
//			if (cFiltrosRep != "")
//				cFiltrosRep += "/";
//			cFiltrosRep += aResSolicitudes[i][0] + "," + aResSolicitudes[i][1];
//		}
//	}
//
//	if (cMsg != "") {
//		lEjecuta = false;
//		fAlert("\n\na)Las siguientes solicitudes no pueden entregarse por faltar registro de conceptos a pagar"
//				+ cMsg);
//		return;
//	}
//
//	if (lEjecuta)
//		cFiltrosRep += cSeparadorRep;
//	else {
//		if (frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "") {
//			if (frm.lImpresasBOX.checked)
//				lContieneImpresas = true;
//			if (parseInt(frm.lMovGenerados.value, 10) == 1
//					&& parseInt(frm.lPagoAnticipadoNoPagado.value, 10) == 1
//					&& lImprocedente == false && lRequierePago) {
//				cMsg = "\nEjercicio: " + frm.iEjercicio.value + " Número: "
//						+ frm.iNumSolicitud.value;
//				if (parseInt(frm.lMovGenerados.value, 10) == 1)
//					fAlert("\n\nb)Las siguientes solicitudes no pueden entregarse por faltar registro de pagos para movimientos generados"
//							+ cMsg);
//				else
//					fAlert("\n\nb)Las siguientes solicitudes no pueden entregarse por no tener generados movimientos anticipados"
//							+ cMsg);
//				return;
//			}
//			if (parseInt(frm.lPagoAnticipadoNoPagado.value, 10) == 1
//					&& lImprocedente == false && lRequierePago) {
//				cMsg = "\nEjercicio: " + frm.iEjercicio.value + " Número: "
//						+ frm.iNumSolicitud.value;
//				fAlert("\n\nb)Las siguientes solicitudes no pueden entregarse por faltar registro de pagos anticipados requeridos."
//						+ cMsg);
//				return;
//			}
//			cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value
//					+ cSeparadorRep;
//			lEjecuta = true;
//		}
//	}
//	if (lEjecuta) {
//		if (lContieneImpresas && lHabImpresas == false)
//			fAlert("\n- Su selección contiene solicitudes previamente impresas, Usted no tiene privilegios para reimpresión.");
//		else {
//			// fFechaImp();
//			fReportes();
//		}
//
//	} else
//		fAlert("\n-Debe seleccionar un trámite para poder imprimir el acuse de recibo");
//}

function fReporte(){
	if( frm.iEjercicio.value >0 && frm.iNumSolicitud.value>0){
	 cClavesModulo="3,3,";
	 cNumerosRep="76,77,";
	 cFiltrosRep=  frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
	 cFiltrosRep+=cFiltrosRep;
	 fReportes();
	}else{
		fAlert("\n-Debe seleccionar un trámite para poder imprimir el acuse de recibo");
	}
}


function fSetContainerForm(theForm) {
	FRMContenedor = theForm;
}

function fValoresDisponibles(theContFrm) {
	frm.Persona_cNomRazonSocial.value = theContFrm.cNomRazonSocial.value;
	frm.RepLegal_cNomRazonSocial.value = theContFrm.cNomRazonSocial2.value;
	frm.Persona_cDscDomicilio.value = theContFrm.cDscDomicilio.value;
	frm.RepLegal_cDscDomicilio.value = theContFrm.cDscDomicilio2.value;
	frm.cRFC.value = theContFrm.cRFC.value;

	frm.iCveTipoPersona.value = theContFrm.iTipoPersona.value;
	frm.iCveSolicitante.value = theContFrm.iCvePersona.value;
	frm.iCveDomicilioSolicitante.value = theContFrm.iCveDomicilio.value;
	frm.iCveRepLegal.value = theContFrm.iCveRepLegal.value;
	frm.iCveDomicilioRepLegal.value = theContFrm.iCveDomRepLegal.value;

	frm.iEjercicio.value = "";
	frm.iNumSolicitud.value = "";
	frm.cNomAutorizaRecoger.value = "";
	frm.cObsTramite.value = "";

	FRMListado.fSetListado(new Array());
	FRMListado.fShow();

	// fNavega();
}

function fGetPermiteEditarPagos() {
	return true;
}

function fGetDatosSolicitud(objWindow) {
	if (objWindow)
		if (objWindow.fSetDatosSolicitud)
			objWindow.fSetDatosSolicitud(frm.Persona_cNomRazonSocial.value,
					frm.Persona_cDscDomicilio.value,
					frm.RepLegal_cNomRazonSocial.value,
					frm.RepLegal_cDscDomicilio.value, frm.iEjercicio.value,
					frm.iNumSolicitud.value, frm.cDscTramite.value,
					frm.cRFC.value);
}

function fPagosRegistro(objWindow, cEjercicios, cNumSolics, cConsecs, cRefNums,
		cRefAlfaNums) {
	if (objWindow)
		objWindow.close();
	fNavega();
	lColoca = 1;
	cColoca = frm.iEjercicio.value + "," + frm.iNumSolicitud.value;
	// ****************************************
}

function fGetDatosPersona(objRef) {
	if (window.parent)
		if (window.parent.document)
			if (window.parent.document.forms[0])
				FRMOrigen = window.parent.document.forms[0];
	if (objRef && FRMOrigen)
		if (objRef.fValoresPersona)
			objRef.fValoresPersona(FRMOrigen.iCvePersona.value,
					FRMOrigen.cRFC.value, FRMOrigen.cRPA.value,
					FRMOrigen.iTipoPersona.value, FRMOrigen.cNombreSolo.value,
					FRMOrigen.cApPaterno.value, FRMOrigen.cApMaterno.value,
					FRMOrigen.cCorreoE.value, FRMOrigen.cPseudonimoEmp.value,
					FRMOrigen.iCveDomicilio.value,
					FRMOrigen.iCveTipoDomicilio.value, FRMOrigen.cCalle.value,
					FRMOrigen.cNumExterior.value, FRMOrigen.cNumInterior.value,
					FRMOrigen.cColonia.value, FRMOrigen.cCodPostal.value,
					FRMOrigen.cTelefono.value, FRMOrigen.iCvePais.value,
					FRMOrigen.cDscPais.value, FRMOrigen.iCveEntidadFed.value,
					FRMOrigen.cDscEntidadFed.value,
					FRMOrigen.iCveMunicipio.value,
					FRMOrigen.cDscMunicipio.value,
					FRMOrigen.iCveLocalidad.value,
					FRMOrigen.cDscLocalidad.value,
					FRMOrigen.lPredeterminado.value,
					FRMOrigen.cDscTipoDomicilio.value,
					FRMOrigen.cDscDomicilio.value, parseInt(
							frm.iCveTramite.value, 10), parseInt(
							frm.iCveModalidad.value, 10), true, false, false);
		else
			alert("No existe funcion");
	else
		alert("no existe ventana destino o fuente de datos");
}

function fDatosReferencia(aResDatosRef, objRef) {
	aDatosReferencia = new Array();
	for ( var i = 0; i < aResDatosRef.length; i++)
		aDatosReferencia[aDatosReferencia.length] = fCopiaArreglo(aResDatosRef[i]);
	frm.Mov_iRefNumerica.value = "";
	frm.Mov_iNumUnidades.value = "";
	frm.Mov_cRefAlfaNum.value = "";
	frm.Mov_dImportePagar.value = "";
	frm.Mov_iCveConcepto.value = "";
	frm.Mov_iCveSolicitanteIngresos.value = "";
	frm.Mov_dFechaRef.value = "";
	if (aDatosReferencia && aDatosReferencia.length > 0) {
		for ( var i = 0; i < aDatosReferencia.length; i++) {
			frm.Mov_iRefNumerica.value += (i == 0) ? "" : ",";
			frm.Mov_iRefNumerica.value += aDatosReferencia[i][1];
			frm.Mov_iNumUnidades.value += (i == 0) ? "1" : ",1";
			frm.Mov_cRefAlfaNum.value += (i == 0) ? "" : ",";
			frm.Mov_cRefAlfaNum.value += aDatosReferencia[i][2];
			frm.Mov_dImportePagar.value += (i == 0) ? "" : ",";
			frm.Mov_dImportePagar.value += aDatosReferencia[i][7];
			frm.Mov_iCveConcepto.value += (i == 0) ? "" : ",";
			frm.Mov_iCveConcepto.value += aDatosReferencia[i][8];
			frm.Mov_iCveSolicitanteIngresos.value += (i == 0) ? "" : ",";
			frm.Mov_iCveSolicitanteIngresos.value += aDatosReferencia[i][9];
			frm.Mov_dFechaRef.value += (i == 0) ? "" : ",";
			frm.Mov_dFechaRef.value += aDatosReferencia[i][10];

		}
		frm.hdBoton.value = "CreaRegPago";
		fEngSubmite("pgTRARegRefPago.jsp", "MovimientoGenerado");
	} else
		fNavega();
	if (objRef)
		objRef.close();
}

function fSelecciona() {
	var i = 0;

	iSalir = 0;
	for (i = 0; i < aResSolicitudes.length && iSalir == 0; i++) {
		lMarca = true;

		if (aResSolicitudes[i][0] == iSeleccionadosEj
				&& aResSolicitudes[i][1] == iSeleccionadosiN) {
			iSalir = 1;
		}
	}
	i--;
	for (y = 0; y < aSeleccionados.length; y++) {
		oCaja = eval("FRMListado.document.forms[0].OCajaB0" + i);
		oCaja.checked = lCheck;
		i++;
	}
	aSeleccionados = new Array();
	lSelAutomatico = false;
}

function fGetIEjercicio() {
	return frm.iEjercicio.value;
}

function fGetINumSolicitud() {
	return frm.iNumSolicitud.value;
}

function fGetHDBien() {
	return frm.hdBien.value;
}

function fGetUnidCalculo() {
	return frm.dUnidCalculo.value;
}

function fGetIdBien() {
	return frm.hdIdBien.value;
}

function fGetInicioTramite() {
	return lInicioDeTramite;
}

function fMostrarGenerarFicha() {
	return true;
}

function fGetDatosSolicitanteBuscaDirecto() {
	if (frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "") {
		frm.hdSelect.value = "SELECT SOL.iCvePersona, SOL.cRFC, SOl.cRPA, SOL.iTipoPersona, "
				+ // 3
				" SOL.cNomRazonSocial, SOL.cApPaterno, SOL.cApMaterno, SOL.cCorreoE, "
				+ // 7
				" SOL.cPseudonimoEmp, DOM.iCveDomicilio, DOM.iCveTipoDomicilio, "
				+ // 10
				" DOM.cCalle, DOM.cNumExterior, DOM.cNumInterior, DOM.cColonia, "
				+ // 14
				" DOM.cCodPostal, DOM.cTelefono, DOM.iCvePais, P.cNombre AS cDscPais, "
				+ // 18
				" DOM.iCveEntidadFed, EF.cNombre AS cDscEntidadFed, DOM.iCveMunicipio, "
				+ // 21
				" MUN.cNombre AS cDscMunicipio, DOM.iCveLocalidad, LOC.cNombre AS cDscLocalidad, "
				+ // 24
				" DOM.lPredeterminado, TD.cDscTipoDomicilio, RS.iCveTramite, RS.iCveModalidad, "
				+ // 28
				" RS.iEjercicio, RS.iNumSolicitud, RS.cNomAutorizaRecoger, RS.cObsTramite, "
				+ // 32
				" SOL.cNomRazonSocial || ' ' || SOL.cApPaterno || ' '|| SOL.cApMaterno AS cNomSolicitante, "
				+ // 33
				" DOM.cCalle || ' No. ' || DOM.cNumExterior || ' Int. ' || DOM.cNumInterior || ' COL. ' || DOM.cColonia || ', ' || MUN.cNombre || ' (' || EF.cAbreviatura ||'.)' AS cDomSolicitante, "
				+ // 34
				" RS.iCveRepLegal, RS.iCveDomicilioRepLegal, "
				+ // 36
				" REP.cNomRazonSocial || ' ' || REP.cApPaterno || ' '|| REP.cApMaterno AS cNomRepLegal, "
				+ // 37
				" DRL.cCalle || ' No. ' || DRL.cNumExterior || ' Int. ' || DRL.cNumInterior || ' COL. ' || DRL.cColonia || ', ' || MUNRL.cNombre || ' (' || EFRL.cAbreviatura ||'.)' AS cDomRepLegal, "
				+ // 38
				" RS.cDscBien, RS.iIdBien, RS.dUnidadesCalculo, TRA.CCVEINTERNA || ' - ' || TRA.CDSCBREVE AS cDscTramite "
				+ // 42
				" FROM TRARegSolicitud RS "
				+ " JOIN GRLPersona SOL ON SOL.iCvePersona = RS.iCveSolicitante "
				+ " JOIN GRLDomicilio DOM ON DOM.iCvePersona = SOL.iCvePersona AND DOM.iCveDomicilio = RS.iCveDomicilioSolicitante "
				+ " JOIN GRLPais P ON P.iCvePais = DOM.iCvePais "
				+ " JOIN GRLEntidadFed EF ON EF.iCvePais = DOM.iCvePais AND EF.iCveEntidadFed = DOM.iCveEntidadFed "
				+ " JOIN GRLMunicipio MUN ON MUN.iCvePais = DOM.iCvePais AND MUN.iCveEntidadFed = DOM.iCveEntidadFed AND MUN.iCveMunicipio = DOM.iCveMunicipio "
				+ " JOIN GRLLocalidad LOC ON LOC.iCvePais = DOM.iCvePais AND LOC.iCveEntidadFed = DOM.iCveEntidadFed AND LOC.iCveMunicipio = DOM.iCveMunicipio AND LOC.iCveLocalidad = DOM.iCveLocalidad "
				+ " JOIN GRLTipoDomicilio TD ON TD.iCveTipoDomicilio = DOM.iCveTipoDomicilio "
				+ " JOIN GRLPersona REP ON REP.iCvePersona = RS.iCveRepLegal "
				+ " JOIN GRLDomicilio DRL ON DRL.iCvePersona = RS.iCveRepLegal AND DRL.iCveDomicilio = RS.iCveDomicilioRepLegal "
				+ " JOIN GRLPais PRL ON PRL.iCvePais = DRL.iCvePais "
				+ " JOIN GRLEntidadFed EFRL ON EFRL.iCvePais = DRL.iCvePais AND EFRL.iCveEntidadFed = DRL.iCveEntidadFed "
				+ " JOIN GRLMunicipio MUNRL ON MUNRL.iCvePais = DRL.iCvePais AND MUNRL.iCveEntidadFed = DRL.iCveEntidadFed AND MUNRL.iCveMunicipio = DRL.iCveMunicipio "
				+ " JOIN GRLLocalidad LOCRL ON LOCRL.iCvePais = DRL.iCvePais AND LOCRL.iCveEntidadFed = DRL.iCveEntidadFed AND LOCRL.iCveMunicipio = DRL.iCveMunicipio AND LOCRL.iCveLocalidad = DRL.iCveLocalidad "
				+ " JOIN GRLTipoDomicilio TDRL ON TDRL.iCveTipoDomicilio = DRL.iCveTipoDomicilio "
				+ " JOIN TRACatTramite TRA ON TRA.ICVETRAMITE = RS.ICVETRAMITE "
				+ " WHERE RS.iEjercicio = "
				+ frm.iEjercicio.value
				+ "   AND RS.iNumSolicitud = " + frm.iNumSolicitud.value + " ";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		fEngSubmite("pgConsulta.jsp", "DatosSolicBusquedaDirecta");
	}
}

function fLimpiaCamposBuscaDirecto(lLimpiaEjerSol) {
	if (frm.iEjercicioSol.value != "" && frm.iNumSolicitudBusca.value != "")
		if (FRMContenedor != null) {
			FRMContenedor.iCvePersona.value = "";
			FRMContenedor.cRFC.value = "";
			FRMContenedor.cRPA.value = "";
			FRMContenedor.iTipoPersona.value = "";
			FRMContenedor.cNombreSolo.value = "";
			FRMContenedor.cApPaterno.value = "";
			FRMContenedor.cApMaterno.value = "";
			FRMContenedor.cCorreoE.value = "";
			FRMContenedor.cPseudonimoEmp.value = "";
			FRMContenedor.iCveDomicilio.value = "";
			FRMContenedor.iCveTipoDomicilio.value = "";
			FRMContenedor.cCalle.value = "";
			FRMContenedor.cNumExterior.value = "";
			FRMContenedor.cNumInterior.value = "";
			FRMContenedor.cColonia.value = "";
			FRMContenedor.cCodPostal.value = "";
			FRMContenedor.cTelefono.value = "";
			FRMContenedor.iCvePais.value = "";
			FRMContenedor.cDscPais.value = "";
			FRMContenedor.iCveEntidadFed.value = "";
			FRMContenedor.cDscEntidadFed.value = "";
			FRMContenedor.iCveMunicipio.value = "";
			FRMContenedor.cDscMunicipio.value = "";
			FRMContenedor.iCveLocalidad.value = "";
			FRMContenedor.cDscLocalidad.value = "";
			FRMContenedor.lPredeterminado.value = "";
			FRMContenedor.cDscTipoDomicilio.value = "";
			FRMContenedor.cDscDomicilio.value = "";
			FRMContenedor.cNomRazonSocial.value = "";
		}

	frm.iCveSolicitante.value = "";
	frm.iCveDomicilioSolicitante.value = "";
	frm.Persona_cNomRazonSocial.value = "";
	frm.Persona_cDscDomicilio.value = "";
	frm.cRFC.value = "";
	frm.iCveTipoPersona.value = "";

	frm.iCveRepLegal.value = "";
	frm.iCveDomicilioRepLegal.value = "";
	frm.RepLegal_cNomRazonSocial.value = "";
	frm.RepLegal_cDscDomicilio.value = "";

	frm.iEjercicio.value = "";
	frm.iNumSolicitud.value = "";
	frm.cNomAutorizaRecoger.value = "";
	frm.cObsTramite.value = "";
	frm.iCveTramite.value = "";
	frm.iCveModalidad.value = "";
	frm.hdBien.value = "";
	frm.hdIdBien.value = "";
	frm.dUnidCalculo.value = "";
	frm.cDscTramite.value = "";

	if (lLimpiaEjerSol == true) {
		frm.iEjercicioSol.value = "";
		frm.iNumSolicitudBusca.value = "";
		frm.lImpresasBOX.checked = false;
		FRMListado.fSetListado(new Array());
		FRMListado.fShow();
	}

}

function fPagaSol() {
	var aRes = FRMListado.fGetARes();
	var aCBox = FRMListado.fGetObjs(0);
	aSolMarcadas = new Array();
	var lSolMarcadas = false;
	for (i = 0; i < aCBox.length; i++) {
		if (aCBox[i] == true) {
			alert(aSolMarcadas.length + "  ++  " + i + "  ++  " + aRes[i][0]
					+ "/" + aRes[i][1]);
			lSolMarcadas = true;
			aSolMarcadas[aSolMarcadas.length] = aRes[i][0] + "/" + aRes[i][1];
			// aSolMarcadas[aSolMarcadas.length][1]=aRes[i][1];
		}
	}
	if (lSolMarcadas == false)
		fAlert("Es necesario marcar al menos una solicitud.");
	else
		alert(aSolMarcadas);
}

function fGetASolMarcadas() {
	return aSolMarcadas;
}

function fFechaImp() {
	frm.hdBoton.value = "cIdFechaImp";
	fEngSubmite("pgTRARegSolicitud.jsp", "cIdFechaImp");
}
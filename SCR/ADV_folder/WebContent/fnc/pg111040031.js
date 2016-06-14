// MetaCD=1.0
// Title: pg111020015.js
// Description: JS "Catalogo" de la entidad TRARegSolicitud
// Company: Tecnologia InRed S.A. de C.V.
// Author: mbeano && iCaballero
var cTitulo = "";
var FRMListado = "";
var frm;
var iEtapaConcluida = 0;
var iEtapaEntregarVU = 0;
var iEtapaEntregarOfi = 0;
var iEtapaRecibeResolucion = 0;
var iEtapaResEnviadaOficialia = 0;
var isDGDCusr = false;
var cPermisoPag;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg111040031.js";
	if (top.fGetTituloPagina)
		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO") ? "BÚSQUEDA DE SOLICITUD"
			: cTitulo;
	cPermisoPag = fGetPermisos(cPaginaWebJS);
	fSetWindowTitle();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJS);
	InicioTabla("ETablaInfo", 0, "100%", "", "center", "", "1");
	ITRTD("", 0, "", "", "center");
	InicioTabla("ETablaInfo1", 0, "", "", "center", "", "1");
	ITD("EEtiqueta", 0, "0", "", "center", "middle");
	TextoSimple("Trámite:");
	FTD();
	ITD("EEtiquetaL", 0, "0", "", "center", "middle");
	// Text(
	// false,
	// "cCveTramite",
	// "",
	// 7,
	// 6,
	// "Teclee la clave interna del trámite para ubicarlo",
	// "fTamOnChange();this.value='';",
	// " onKeyPress=\"return fReposSelectFromField(event, true,
	// this.form.iCveTramite, this);\"",
	// "", true, true)+
	Select("iCveTramite", "fTamOnChange();");
	FTD();
	FTR();
	FinTabla();
	Hidden("hdSelect");
	Hidden("hdLlave");
	ITRTD("", 0, "", "", "center");
	InicioTabla("ETablaInfo1", 0, "", "", "center", "", "1");
	TDEtiSelect("true", "EEtiqueta", 0, "Centro SCT: ",
			"iCveOficinaFiltro", "", "", 0);
	FinTabla();

	InicioTabla("ETablaInfo", 0, "", "", "center", "", "1");
	ITRTD("", 0, "", "", "center");
	TDEtiCampo(false, "EEtiqueta", 0, "RFC Inicie con:", "cRfc", "", 15, 15,
			"RFC", "fMayus(this);",
			" onKeyPress='return fCheckForEnter(event, this, window);' ", "",
			false, "");
	TDEtiCampo(false, "EEtiqueta", 0, "Promovente:", "cNombre", "", 65, 65,
			"Promovente", "fMayus(this);",
			" onKeyPress='return fCheckForEnter(event, this, window);' ", "",
			false, "", "");
	ITD("EEtiquetaL", 2, "");
	BtnImgNombre("btnBusSol", "buscar", "fAbreSolicitante();",
			"Buscar personas físicas o morales", false, "", "BuscaSol");
	TextoSimple("Buscar Persona");
	FTD();
	FTDTR();
	ITRTD("", 0, "", "", "center");
	TDEtiCampo(false, "EEtiqueta", 0, "Ejercicio:", "iEjercicioFiltro", "", 4,
			4, "Ejercicio", "fMayus(this);",
			" onKeyPress='return fCheckForEnter(event, this, window);' ");
	TDEtiCampo(false, "EEtiqueta", 0, "No. Solicitud:", "iNumSolicitudFiltro",
			"", 8, 8, "Núm. Solicitud", "fMayus(this);",
			" onKeyPress='return fCheckForEnter(event, this, window);' ");
	TDEtiCheckBox("EEtiqueta", 0, "Con Retraso:", "lRetrasoBOX", "0",
			true, " Activo", "", "");
	ITD("", 0, "", "", "Right", "Center");
	BtnImg("Buscar", "lupa", "fBuscar();");
	FTD();

	ITD();
	InicioTabla("ETablaInfo", 0, "", "", "center", "", "1");
	ITR();
	ITD();
	LigaNombre("Exportar Consulta", "fShowConsultaExcel();", "", "");
	FTD();
	ITD();
	FTR();
	ITR();
	ITD();
	LigaNombre("Aprovechamientos Irregulares", "fShowIrregulares();", "", "");
	FTD();
	FTR();

	FinTabla();

	FTDTR();

	FinTabla();

	FTDTR();
	FinTabla();

	InicioTabla("", 0, "98%", "", "center", "", "1");
	ITRTD("ESTitulo", 0, "100%", "", "center");
	TextoSimple("Busqueda de Solicitudes");
	FTDTR();
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "100%", "", "center");
	ITRTD("", 0, "", "175", "center", "top");
	IFrame("IListado", "100%", "300", "Listado.js", "yes", true);
	FTDTR();
	ITRTD("", 0, "", "1", "center");
	FTDTR();
	/*
	 * ITRTD("",0,"","20","center","top");
	 * Liga("Seleccionar","fSeleccionar();","Selecciónn de la Solicitud");
	 * FTDTR();
	 */
	FinTabla();
	FTDTR();
	FinTabla();
	Hidden("iEjercicio", "");
	Hidden("iNumSolicitud", "");
	Hidden("cCveTramite", "");

	// Hidden("hdLlave","");
	// Hidden("hdSelect","");
	// Hidden("cNomRazonSocial");
	Hidden("iCveEjercicio", "");
	Hidden("cDscTramite", "");
	Hidden("iCveModalidad", "");
	Hidden("cDscModalidad", "");
	Hidden("iCveOficinaUsr", "");
	Hidden("cDscOficinaUsr", "");
	Hidden("iCveTramiteA1", "");
	Hidden("iCveUsuario", fGetIdUsrSesion());
	fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	FRMPanel = fBuscaFrame("IPanel");
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);

	// FRMListado
	// .fSetTitulo("Ejercicio,Solicitud,F. Registro,Tipo Permiso,Promovente,
	// Autopista, Cadenamientos y Sentidos, Justificación, Días
	// Transcurridos,Estatus,Resolución V.T.,Eval. S. Técnicos,Eval. A.
	// Jurídicos,Núm. Permiso,F. Impr. de Permiso,Unidad Administrativa,F.
	// Visita Técnica,Tiene PNC,Georeferencia,");
	// FRMListado.fSetCampos("0,1,10,3,8,29,43,34,26,27,35,30,31,37,38,16,40,39,44,");
	//
	// FRMListado
	// .fSetDespliega("texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,");
	// FRMListado
	// .fSetAlinea("center,center,center,center,center,center,center,center,left,center,center,center,center,center,center,center,center,center,center,");

//	FRMListado
//			.fSetTitulo("Ejercicio,Solicitud,F. Registro,Tipo Permiso,Promovente, Autopista, Cadenamientos y Sentidos, Justificación, Días Transcurridos,Estatus,Resolución V.T.,Eval. S. Técnicos,Eval. A. Jurídicos,Núm. Permiso,F. Impr. de Permiso,Unidad Administrativa,F. Visita Técnica,Tiene PNC, Tiene Retraso, Georeferencia,");
//	FRMListado
//			.fSetCampos("0,1,10,4,8,29,43,34,26,27,35,30,31,37,38,16,40,39,47,48,");
	

	FRMListado
	.fSetTitulo("Ejercicio, Solicitud, Fecha Registro, Promovente, Tipo Permiso, Unidad Administrativa, Autopista , Cadenamientos y Sentidos, " +
			"Georeferencia, Destino, F. Visita Técnica, Resolución V.T., Eval. S. Técnicos,Eval. A. Jurídicos, Días Transcurridos, Estatus,Núm. Permiso,F. Impr. de Permiso,Tiene PNC, Tiene Retraso, ");
	
	FRMListado
	.fSetCampos("0,1,10,8,4,16,29,43,48,34,40,35,30,31,26,27,37,38,39,47,")

	FRMListado
			.fSetDespliega("texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,");
	FRMListado
			.fSetAlinea("center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,");
	// fDisabled(true,"iCveTramiteFiltro,cRfc,iEjercicioFiltro,iNumSolicitudFiltro,");
	fDisabled(
			true,
			"cCveTramite,iCveTramite,iEjercicioFiltro,iNumSolicitudFiltro,iCveOficinaFiltro,lRetrasoBOX,");
	frm.hdBoton.value = "Primero";
	fTramite();
	// fCargaTramite();
}

function fNavega() {

	setFiltroConsulta();
	setOrdenConsulta();
	// return fEngSubmite("pg111020015A2.jsp","Listado");
	return fEngSubmite("pg111020015ADV.jsp", "Listado");

}

// RECEPCIoN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, cEtapas) {
	if (cError == "Guardar")
		fAlert("Existió un error en el Guardado!");
	if (cError == "Borrar")
		fAlert("Existió un error en el Borrado!");
	if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	}
	if (cId == "Listado" && cError != "") {
		fAlert("El servidor respondió de forma inseperada. Intente nuevamente.");
		return;
	}

	if (cError != "")
		FRMFiltro.fSetNavStatus("Record");

	if (cId == "Listado" && cError == "") {
		var aEtapas = new Array();
		aEtapas = cEtapas.split(",");
		frm.hdRowPag.value = iRowPag;

		if (aRes.length > 0) {

			for ( var i = 0; i < aRes.length; i++) {

				if (aRes[i][28] != "")
					aRes[i][27] = 'CANCELADO';
				else if (parseInt(aRes[i][27], 10) > 0) {
					aRes[i][27] = 'ABANDONADO';
				} else if (parseInt(aRes[i][14], 10) > 0) {
					aRes[i][27] = 'OTORGADO';
				} else if (parseInt(aRes[i][14], 10) == 0 && aRes[i][36] != "") {
					aRes[i][27] = 'NEGADO';
				} else if (parseInt(aRes[i][14], 10) == 0 && aRes[i][36] == "") {
					aRes[i][27] = 'EN PROCESO';
				}

				if (parseInt(aRes[i][30], 10) > 0)
					aRes[i][30] = "EVALUADO";
				else
					aRes[i][30] = "PENDIENTE";

				if (parseInt(aRes[i][31], 10) > 0)
					aRes[i][31] = "EVALUADO";
				else
					aRes[i][31] = "PENDIENTE";

				if (aRes[i][26] != "0.0") { // DIAS
					aRes[i][26] = parseInt(aRes[i][26], 10);
				} else {
					aRes[i][26] = "0";
				}

				if (aRes[i][35] == "NEGATIVO") {
					aRes[i][27] = 'NEGADO';
					aRes[i][30] = "NO APLICA";
					aRes[i][31] = "NO APLICA";
					aRes[i][37] = "NO APLICA";
					aRes[i][38] = "NO APLICA";
					aRes[i][26] = "NO APLICA";
				}

				if (aRes[i][27] === "OTORGADO" && aRes[i][38] != "") {

					if (aRes[i][44] != "0.0") {
						aRes[i][44] = parseInt(aRes[i][44], 10);
					} else {
						aRes[i][44] = "0";
					}
					aRes[i][26] = aRes[i][44];
				}// FECHA DE REGISTRO A FECHA DE PERMISO

				else if (aRes[i][27] === "NEGADO") {
					if (parseInt(aRes[i][46]) >= 0)
						aRes[i][26] = parseInt(aRes[i][46], 10)
					else
						aRes[i][26] = "NO APLICA";
				}// fecha de registro a fecha de resolucion para negadas

				else if (aRes[i][27] === "CANCELADO") {
					aRes[i][26] = parseInt(aRes[i][45], 10);
				}

				if (aRes[i][39] != "" && aRes[i][39] != "null"
						&& aRes[i][39] > 0) {
					aRes[i][39] = 'SI';
				} else
					aRes[i][39] = '';
				
				if (aRes[i][47] != "" && aRes[i][47] != "null"
					&& aRes[i][47] > 0) {
				aRes[i][47] = 'SI';
			} else
				aRes[i][47] = '';

				
				if (aRes[i][41] != "" && aRes[i][42] != "") {
					aRes[i].push("<font color=blue>Ir..</font>");
				} else {
					aRes[i].push("No proporcionado.");
				}
			}

		}

		FRMListado.fSetListado(aRes);
		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
		fCancelar();
	}

	if (cId == "idTramite" && cError == "") {

		for (i = 0; i < aRes.length; i++) {
			aRes[i][1] = aRes[i][2] + " - " + aRes[i][1];
		}

		fFillSelect(frm.iCveTramite, aRes, true, 0, 0, 1);

		verificaDGDC();
	}

	if (cId == "cIdVerifDGDC" && cError == "") {
		if (aRes.length > 0) {
			fOficina(true);
			isDGDCusr = true;
		} else {
			fOficina(false);
			isDGDCusr = false;
		}
	}

	if (cId == "cIdOficina" && cError == "") {
		fFillSelect(frm.iCveOficinaFiltro, aRes, true,
				frm.iCveOficinaFiltro.value, 0, 1);
		if (top.opener) {
			if (top.opener.fGetiNumSolicitud) {
				if (top.opener.fGetiNumSolicitud() > 0)
					fBuscar();
			}
		}
	}
}

function fCargaTramite() {
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "TRACatTramite.cDscBreve";
	frm.hdNumReg.value = "10000";
	fEngSubmite("pgTraCatTramite.jsp", "idTramite");
}

// LLAMADO desde el Panel cada vez que se presiona al boton Nuevo
function fNuevo() {
	// FRMPanel.fSetTraStatus("UpdateBegin");
	fBlanked();
	fDisabled(false, "iEjercicioFiltro,", "--");
	FRMListado.fSetDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al boton Guardar
function fGuardar() {
	if (fValidaTodo() == true) {
		if (fNavega() == true) {
			// FRMPanel.fSetTraStatus("UpdateComplete");
			fDisabled(true);
			FRMListado.fSetDisabled(false);
		}
	}
}

// LLAMADO desde el Panel cada vez que se presiona al boton GuardarA
// "Actualizar"
function fGuardarA() {
	if (fValidaTodo() == true) {
		if (fNavega() == true) {
			// FRMPanel.fSetTraStatus("UpdateComplete");
			fDisabled(true);
			FRMListado.fSetDisabled(false);
		}
	}
}

// LLAMADO desde el Panel cada vez que se presiona al boton Modificar
function fModificar() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fDisabled(false, "iEjercicio,");
	FRMListado.fSetDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al boton Cancelar
function fCancelar() {
	fDisabled(false, "cRfc,cNombre,");
	FRMListado.fSetDisabled(false);
}

// LLAMADO desde el Panel cada vez que se presiona al boton Borrar
function fBorrar() {
	if (confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")) {
		fNavega();
	}
}

// LLAMADO desde el Listado cada vez que se selecciona un renglon
function fSelReg(aDato, iCol) {
	
	frm.iEjercicio.value = aDato[0];
	frm.iNumSolicitud.value = aDato[1];
	// frm.iCveTramiteA1.value = aDato[2];
	frm.cDscTramite.value = aDato[4];
	frm.cDscModalidad.value = aDato[6];
	frm.cDscOficinaUsr.value = aDato[16];
	
	if (iCol == 8 && aDato[41] != "" && aDato[42] != "")
		window.open("https://maps.google.com/maps?q=" + aDato[41] + ","
				+ aDato[42]);
}

// FUNCION donde se generan las validaciones de los datos ingresados
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

function fBuscar() {
	
	fNavega();
}

/*
 * function fSeleccionar(){ if (frm.iEjercicio.value != '' &&
 * frm.iNumSolicitud.value != ''){
 * top.opener.fSetSolicitud(frm.iEjercicio.value,frm.iNumSolicitud.value,frm.iCveOficinaUsr.value,frm.iCveDeptoUsr.value);
 * self.focus(); top.close(); } else { alert('Debe Seleccionar Primero la
 * Solicitud'); } }
 */

// Definir en paginas que requieran datos de persona o persona y representante
// legal
function fGetParametrosConsulta(frmDestino) {
	var lShowPersona = true;
	var lShowRepLegal = false;
	var lEditPersona = false;
	var lEditDomPersona = false;
	var lEditRepLegal = false;
	var lEditDomRepLegal = false;
	if (frmDestino) {
		if (frmDestino.setShowValues)
			frmDestino.setShowValues(lShowPersona, lShowRepLegal, "");
		if (frmDestino.setEditValues)
			frmDestino.setEditValues(lEditPersona, lEditDomPersona,
					lEditRepLegal, lEditDomRepLegal);
	}
}

function fValoresPersona(iCvePersona, cRFC, cRPA, iTipoPersona,
		cNomRazonSocial, cApPaterno, cApMaterno, cCorreoE, cPseudonimoEmp,
		iCveDomicilio, iCveTipoDomicilio, cCalle, cNumExterior, cNumInterior,
		cColonia, cCodPostal, cTelefono, iCvePais, cDscPais, iCveEntidadFed,
		cDscEntidadFed, iCveMunicipio, cDscMunicipio, iCveLocalidad,
		cDscLocalidad, lPredeterminado, cDscTipoDomicilio, cDscDomicilio,
		iCveTramite, iCveModalidad, lPagoAnticipado, lDesactivarAnticipado,
		lBuscarSolicitante) {
	frm.cRfc.value = cRFC;
	frm.cNombre.value = cNomRazonSocial + " " + cApPaterno + " " + cApMaterno;
	frm.iEjercicioFiltro.focus();
}

function fTramite() {
	frm.hdSelect.value = "SELECT ICVETRAMITE,CDSCBREVE,CCVEINTERNA FROM TRACATTRAMITE Where LVIGENTE=1 order by CCVEINTERNA ";
	frm.hdLlave.value = "ICVETRAMITE";
	return fEngSubmite("pgConsulta.jsp", "idTramite");
}
function fTamOnChange() {
	fActualizaDatos();
}
function fActualizaDatos() {
	frm.iCveTramiteA1.value = frm.iCveTramite.value
}

function fGetiCveEjercicio() {
	frm.iCveEjercicio.value = frm.iEjercicio.value;
	return frm.iCveEjercicio.value;
}
function fGetiNumSolicitud() {
	return frm.iNumSolicitud.value;
}
function fGetcTramite() {
	// frm.iCveTramite.value = frm.cDscTramite.value;
	return frm.iCveTramite.value = frm.cDscTramite.value;
}
function fGetcModalidad() {
	frm.iCveModalidad.value = frm.cDscModalidad.value;
	return frm.iCveModalidad.value;
}
function fGetcOficina() {
	frm.iCveOficinaUsr.value = frm.cDscOficinaUsr.value;
	return frm.iCveOficinaUsr.value;
}
function fEnterLocal(theObject, theEvent, theWindow) {
	objName = theObject.name;
	if (objName == 'cRfc' || objName == 'cNombre'
			|| objName == 'iEjercicioFiltro'
			|| objName == 'iNumSolicitudFiltro') {
		fMayus(theObject);
		fNavega();
	}
}
function fOficina(isDGDC) {

	if (isDGDC == true) {
		frm.hdSelect.value = "SELECT o.icveoficina, O.CDSCBREVE FROM GRLOFICINA o";
		frm.hdLlave.value = "";
		fEngSubmite("pgConsulta.jsp", "cIdOficina");
	} else {
		// alert("userrr--"+frm.iCveUsuario.value);
		if (frm.iCveUsuario.value != "" && frm.iCveUsuario.value > 0) {
			frm.hdSelect.value = "SELECT GRLOFICINA.ICVEOFICINA, GRLOFICINA.CDSCBREVE FROM GRLOFICINA "
					+ "JOIN GRLUSUARIOXOFIC ON GRLOFICINA.ICVEOFICINA = GRLUSUARIOXOFIC.ICVEOFICINA "
					+ "JOIN SEGUSUARIO ON GRLUSUARIOXOFIC.ICVEUSUARIO = SEGUSUARIO.ICVEUSUARIO "
					+ "WHERE SEGUSUARIO.ICVEUSUARIO=" + frm.iCveUsuario.value;

			frm.hdLlave.value = "";
			fEngSubmite("pgConsulta.jsp", "cIdOficina");
		}
	}
}

function verificaDGDC() {
	frm.hdSelect.value = "VERIF_DGDC";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdVerifDGDC");
}

function getFiltroADVExcel() {
	setFiltroConsulta();
	return frm.hdFiltro.value;
}

function getOrdenADVExcel() {
	setOrdenConsulta();
	return frm.hdOrden.value;
}

function setFiltroConsulta() {

	var cmbOf = document.getElementById("iCveOficinaFiltro");

	frm.hdFiltro.value = '';
	frm.hdOrden.value = ""; // FRMFiltro.fGetOrden();
	frm.hdNumReg.value = "10000"; // FRMFiltro.fGetNumReg();

	if (isDGDCusr == false) {

		cmbOf.value = cmbOf.options[1].value;

		if (frm.hdFiltro.value != "")
			frm.hdFiltro.value = frm.hdFiltro.value
					+ " and TRARegSolicitud.ICVEOFICINA = "
					+ frm.iCveOficinaFiltro.value;
		// " and TXO.ICVEOFICINARESUELVE = "+frm.iCveOficinaFiltro.value;
		// //original
		else
			frm.hdFiltro.value = " TRARegSolicitud.ICVEOFICINA = "
					+ frm.iCveOficinaFiltro.value; // original

	} else {
		if (frm.iCveOficinaFiltro.value > 0) {
			if (frm.hdFiltro.value != "")
				frm.hdFiltro.value = frm.hdFiltro.value
						+ " and TRARegSolicitud.ICVEOFICINA = "
						+ frm.iCveOficinaFiltro.value;
			// " and TXO.ICVEOFICINARESUELVE = "+frm.iCveOficinaFiltro.value;
			// //original
			else
				frm.hdFiltro.value = " TRARegSolicitud.ICVEOFICINA = "
						+ frm.iCveOficinaFiltro.value; // original
			// frm.hdFiltro.value = " TXO.ICVEOFICINARESUELVE =
			// "+frm.iCveOficinaFiltro.value; //original
		}
	}

	if (frm.iEjercicioFiltro.value != "") {

		frm.iEjercicio.value = frm.iEjercicioFiltro.value;

		if (frm.hdFiltro.value != "")
			frm.hdFiltro.value = frm.hdFiltro.value
					+ " and TRARegSolicitud.iEjercicio = "
					+ frm.iEjercicioFiltro.value;
		else
			frm.hdFiltro.value = " TRARegSolicitud.iEjercicio = "
					+ frm.iEjercicioFiltro.value;
	}

	if (frm.iNumSolicitudFiltro.value != '') {

		frm.iNumSolicitud.value = frm.iNumSolicitudFiltro.value;

		if (frm.hdFiltro.value != '')
			frm.hdFiltro.value = frm.hdFiltro.value
					+ " and TRARegSolicitud.iNumSolicitud = "
					+ frm.iNumSolicitudFiltro.value;
		else
			frm.hdFiltro.value = " TRARegSolicitud.iNumSolicitud = "
					+ frm.iNumSolicitudFiltro.value;
	}

//	if (frm.hdFiltro.value != "")
//		frm.hdFiltro.value = frm.hdFiltro.value
//				+ " and TRARegSolicitud.lImpreso is not null ";
//	else
//		frm.hdFiltro.value = " TRARegSolicitud.lImpreso is not null ";

	if (frm.iCveTramiteA1.value != "" && frm.iCveTramiteA1.value != "-1") {
		if (frm.hdFiltro.value != '')
			frm.hdFiltro.value = frm.hdFiltro.value
					+ " and TRARegSolicitud.iCveTramite = "
					+ frm.iCveTramiteA1.value;
		else
			frm.hdFiltro.value = " TRARegSolicitud.iCveTramite = "
					+ frm.iCveTramiteA1.value;
	}

	if (frm.cRfc.value != '') {
		if (frm.hdFiltro.value != '')
			frm.hdFiltro.value = frm.hdFiltro.value
					+ " and GRLPERSONA.CRFC like '" + frm.cRfc.value + "%'";
		else
			frm.hdFiltro.value = " GRLPERSONA.CRFC like '" + frm.cRfc.value
					+ "%'";
	}
	
	if (frm.lRetrasoBOX.checked) {
		if (frm.hdFiltro.value != '')
			frm.hdFiltro.value = frm.hdFiltro.value
					+ " and (SELECT COUNT(ICVERETRASO) FROM TRAREGRETRASO WHERE IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) >0 ";
		else
			frm.hdFiltro.value = " (SELECT COUNT(ICVERETRASO) FROM TRAREGRETRASO WHERE IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) >0 ";
	}
	
}

function setOrdenConsulta() {
	frm.hdOrden.value = " TRAREGSOLICITUD.TSREGISTRO asc";// TRAREGSOLICITUD.IEJERCICIO
															// desc,
															// TRAREGSOLICITUD.INUMSOLICITUD
															// desc";
}

function fShowIrregulares() {
	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	} else {
		fAbreSubWindowSinPermisos("pg117010050", "750", "425");
	}
}


function fGetConsultaSolicitud(){
	return true;
}


function setDatosPersona(obj){	
	frm.cRfc.value = obj.cRFC;
	frm.cNombre.value =obj.cNomRazonSocial
}
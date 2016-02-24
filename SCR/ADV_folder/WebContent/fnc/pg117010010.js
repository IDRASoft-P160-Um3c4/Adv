// MetaCD=1.0
// Title: pg111020020.js
// Description: JS "Catálogo" de la entidad 
// Company: Tecnología InRed S.A. de C.V.
// Author: ocastrejon; lequihua; iCaballero
var cTitulo = "";
var FRMListado = "";
var frm;
var lConsulta = true;
var lEncontre = false;
var lCambiar = false;
var iCveEtapaFinal = 0;
var iCveUltimaEtapa = 0;
var iCveEtapaVerificacion = 0;
var iCveEtapaEntregaVU = 0;
var iCveEtapaEntregaOfi = 0;
var iCveEtapaRecibeResol = 0;
var iCveEtapaEntregaResol = 0;
var iCveEtapaDocRetorno = 0;
var iCveEtapaConclusionTramite = 0;
var iCveEtapaNotificado = 0;
var iCveEtapaResEnviadaOfic = 0;
var iCveEtapaTramiteCancelado = 0;
var iCveTramiteCertificaDoc = 0;
var iCveUltimaOficina = 0;
var iCveUltimoDepto = 0;
var negativa = false, ejerN = 0, solN = 0;
var subioFormato = false;
var puedeAgregarAcuerdo = false;

var indiceAcuerdos = 0;
var arrayAcuerdos = new Array();

var prefijoAcuerdo = 'idAcuerdo_';

var permiteSubir = false;

var resolcionArrayC = new Array();
resolcionArrayC[0] = [ '0', 'Negativo' ];
resolcionArrayC[1] = [ '1', 'Positivo' ];
var cPermisoPag;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {

	cPaginaWebJS = "pg117010010.js";
	cTitulo = "RESOLUCIÓN DE VISITA TÉCNICA";
	cPermisoPag = fGetPermisos(cPaginaWebJS);
	fSetWindowTitle();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJS);

	InicioTabla("ETablaInfo", 0, "", "", "center", "", "1");
	ITRTD("ECampoC", 6, "100%", "center", "top");
	// Liga("Buscar Solicitud","fAbreBuscaSolicitud();","Búsqueda de la
	// Solicitud");

	ITRTD("ETablaST", 6, "100%", "", "center");
	TextoSimple("Selección de la Solicitud");
	FTDTR();
	TDEtiCampo(true, "EEtiqueta", 0, "Ejercicio:", "iEjercicioFiltro", "", 4,
			4, "Ejercicio", "fMayus(this);");
	TDEtiCampo(true, "EEtiqueta", 0, "No. Solicitud:", "iNumSolicitudFiltro",
			"", 8, 8, "Núm. Solicitud", "fMayus(this);", "", "blankFields();");
	ITD("EEcampo", 0, "", "", "LEFT", "LEFT");
	BtnImg("Buscar", "lupa", "fNavega();");
	FITR();
	FinTabla();

	InicioTabla("ETablaInfo", 0, "75%", "", "center", "", 1);
	ITRTD("ETablaST", 12, "", "", "center");
	TextoSimple("Datos de la Solicitud");
	FTDTR();
	TDEtiCampo(false, "EEtiqueta", 0, "Homoclave:", "cHomoclave", "", 18, 18,
			"Homoclave del Trámite", "", "", "", true, "", 0);
	FTDTR();
	Hidden("iCveTramite", "");
	TDEtiAreaTexto(false, "EEtiqueta", 0, "Trámite:", 80, 3, "cDscTramite", "",
			"Trámite", "", "fMayus(this);", 'onkeydown="fMxTx(this,150);"', "",
			"", false, "", 8);
	FTDTR();
	ITD("EEtiqueta", 0, "", "", "LEFT", "LEFT");
	TextoSimple("Modalidad:");
	ITD("ECampo", 5, "", "", "LEFT", "LEFT");
	Text(false, "cDscModalidad", "", 70, 70, "Modalidad del Trámite", "", "",
			"", false, false);
	Hidden("iCveModalidad", "");
	FITR();
	ITD("EEtiqueta", 0, "", "", "LEFT", "LEFT");
	TextoSimple("Promovente:");
	ITD("ECampo", 5, "", "", "LEFT", "LEFT");
	Text(false, "cSolicitante", "", 70, 70, "Promovente del Trámite", "", "",
			"", false, false);
	Hidden("iCveSolicitante", "");
	FITR();
	ITD("EEtiqueta", 0, "", "", "LEFT", "LEFT");
	TextoSimple("Fecha de Visita Técnica:");
	ITD("ECampo", 5, "", "", "LEFT", "LEFT");
	Text(false, "tFechaVisita", "", 70, 70,
			"Fecha en la que se realiza la Visita Técnica", "", "", "", false,
			false);
	FTDTR();
	FinTabla();

	InicioTabla("", 0, "100%", "", "", "", "1");
	ITRTD("", 0, "", "", "top");
	InicioTabla("ETablaInfo", 0, "100%", "", "", "", 0);
	ITRTD("ETablaST", 9, "", "", "center");
	TextoSimple("Resolución de Visita Técnica");
	FTDTR();
	TDEtiSelect(true, "EEtiqueta", 3, "Resolución:", "lResolucion",
			"onChangeResol();");
	ITD("ECampo", 5, "", "", "LEFT", "LEFT");
	Liga("*Anexar Oficio de Minuta Técnica", "fSubirMinutaVT();", "");
	FTDTR();
	TDEtiCampo(false, "EEtiqueta", 2, "Representante Centro SCT:", "cRepCSCT",
			"", 50, 80, "Representante", "fMayus(this);");
	TDEtiCampo(false, "EEtiqueta", 4, "Cargo:", "cCargoCSCT", "", 50, 80,
			"Cargo", "fMayus(this);");
	FTDTR();
	TDEtiCampo(false, "EEtiqueta", 2, "Representante Promovente:", "cRepProm",
			"", 50, 80, "Representante", "fMayus(this);");
	TDEtiCampo(false, "EEtiqueta", 4, "Cargo:", "cCargoProm", "", 50, 80,
			"Cargo", "fMayus(this);");
	FTDTR();
	TDEtiCampo(false, "EEtiqueta", 2, "Representante Concesionario:",
			"cRepConce", "", 50, 80, "Representante", "fMayus(this);");
	TDEtiCampo(false, "EEtiqueta", 4, "Cargo:", "cCargoConce", "", 50, 80,
			"Cargo", "fMayus(this);");
	FTDTR();
	TDEtiAreaTexto(
			false,
			"EEtiqueta",
			1,
			"Comentario Centro SCT:",
			75,
			4,
			"cComentCSCT",
			"",
			"Comentario Centro SCT:",
			"",
			"fMayus(this);",
			'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);" ',
			true, true, true, "ECampo", 4);
	TDEtiAreaTexto(
			false,
			"EEtiqueta",
			1,
			"Comentario Promovente:",
			75,
			4,
			"cComentProm",
			"",
			"Comentario Promovente:",
			"",
			"fMayus(this);",
			'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',
			true, true, true, "ECampo", 3);
	FTDTR();
	TDEtiAreaTexto(
			false,
			"EEtiqueta",
			1,
			"Comentario Concesionario:",
			75,
			4,
			"cComentConce",
			"",
			"Comentario Concesionario:",
			"",
			"fMayus(this);",
			'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',
			true, true, true, "ECampo", 4);
	TDEtiAreaTexto(
			false,
			"EEtiqueta",
			1,
			"Observaciones:",
			75,
			4,
			"cObs",
			"",
			"Observaciones:",
			"",
			"fMayus(this);",
			'onchange="fMxTx(this,250);" onkeydown="fMxTx(this,250);" onblur="fMxTx(this,250);"',
			true, true, true, "ECampo", 3);
	// TDEtiAreaTexto(false,"EEtiqueta",1,"Conclusiones:",75,4,"cConclusion","","Conclusiones:","","fMayus(this);",'onkeydown="fMxTx(this,750);"',true,true,true,"ECampo",3);
	FTDTR();
	// TDEtiAreaTexto(false,"EEtiqueta",1,"Acuerdos:",75,4,"cAcuerdo","","Acuerdos:","","fMayus(this);",'onkeydown="fMxTx(this,750);"',true,true,true,"ECampo",4);

	ITRTD("", 10, "", "", "center");
	Liga("Agregar acuerdo/conclusión", "agregarComentario();",
			"Búsqueda de la Solicitud");
	FTDTR();
	ITRTD("", 10, "", "", "center");
	DinTabla("BarraWizard", "", 0, "", "90%", "center", "top", "", ".1", ".1");
	FTDTR();
	FinTabla();
	InicioTabla("", 0, "100%", "", "", "", "1");
	IFrame("IPanel", "95%", "34", "Paneles.js");
	FinTabla();
	FTDTR();
	FinTabla();

	Hidden("iCveUsuario", fGetIdUsrSesion());
	Hidden("hdLlave");
	// Hidden("hdBoton","");
	Hidden("hdSelect");
	Hidden("dtEntrega");
	Hidden("iEjercicio");
	Hidden("iNumSolicitud");
	Hidden("iCveOfic");
	Hidden("iCveDpto");
	Hidden("iCveTram");
	Hidden("iCveMod");

	Hidden("cAcuerdos", "");
	Hidden("lAutoImprimir", false);
	Hidden("iNumCopias", 1);
	Hidden("lMostrarAplicacion", true);
	Hidden("cArchivoOrig", "");
	Hidden("cNomDestino", "");
	Hidden("cDAOEjecutar", "");
	Hidden("cMetodoTemp", "");
	Hidden("cDigitosFolio", "00");
	Hidden("lRequiereFolio", false);
	Hidden("hdFiltrosRep", "");
	Hidden("cFirmante", "");

	fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {

	tBarraWizard = document.getElementById("BarraWizard");

	frm = document.forms[0];
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	FRMPanel.fSetTraStatus("AddOnly");
	fFillSelect(frm.lResolucion, resolcionArrayC, true, 0, 0, 1);
	// frm.btnArchRes.disabled=true;

	// fDisabled(true);
	// frm.hdBoton.value="Primero";

	fDisabled(true, "iEjercicioFiltro,iNumSolicitudFiltro,");

	var dateToday = new Date();
	fCargaOficDeptoUsr(false);
	frm.iEjercicioFiltro.value = dateToday.getFullYear();
	frm.iNumSolicitudFiltro.focus();
	// fTraeFechaActual();

}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(vModo) {
	if (frm.iEjercicioFiltro.value != 0 && frm.iEjercicioFiltro.value != ''
			&& frm.iNumSolicitudFiltro.value != 0
			&& frm.iNumSolicitudFiltro.value != '') {
		if (fSoloNumeros(frm.iEjercicioFiltro.value)
				&& fSoloNumeros(frm.iNumSolicitudFiltro.value)) {

			frm.hdFiltro.value = " TRAREGSOLICITUD.IEJERCICIO="
					+ frm.iEjercicioFiltro.value
					+ " AND TRAREGSOLICITUD.INUMSOLICITUD="
					+ frm.iNumSolicitudFiltro.value
					+ " AND TRAREGSOLICITUD.INUMSOLICITUD NOT IN"
					+ " (SELECT TRAREGRESOLVTECXSOL.INUMSOLICITUD FROM TRAREGRESOLVTECXSOL WHERE"
					+ " TRAREGRESOLVTECXSOL.IEJERCICIO="
					+ frm.iEjercicioFiltro.value
					+ " AND TRAREGRESOLVTECXSOL.INUMSOLICITUD="
					+ frm.iNumSolicitudFiltro.value + ")"
					+ " and TRAREGETAPASXMODTRAM.iCveOficina = "
					+ frm.iCveOfic.value
					+ " AND TRAREGETAPASXMODTRAM.iCveDepartamento = "
					+ frm.iCveDpto.value;

			if (vModo == "Guardar") {
				// if(subioFormato==true||negativa==true)
				fEngSubmite("pgTRAResoViTecXSol.jsp", "Guardar");
				// else

			} else
				fEngSubmite("pgTRAResoViTecXSol.jsp", "Buscar");
		} else {
			fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
		}
	} else {
		fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	}
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, cEtapas) {

	if (cError == "Guardar")
		fAlert("Existió un error en el Guardado!\n");
	else if (cError == "Borrar")
		fAlert("Existió un error en el Borrado!");
	else if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	} else if (cError != "")
		fAlert(cError);
	
	if (cId == "Buscar" && cError == "") {
		if (aRes.length > 0) {
			frm.cHomoclave.value = aRes[0][0];
			frm.cDscTramite.value = aRes[0][1];
			frm.cDscModalidad.value = aRes[0][2];
			frm.cSolicitante.value = aRes[0][3];
			frm.tFechaVisita.value = aRes[0][4];

			frm.iCveTram.value = aRes[0][5];
			frm.iCveMod.value = aRes[0][6];

			frm.iEjercicio.value = frm.iEjercicioFiltro.value;
			frm.iNumSolicitud.value = frm.iNumSolicitudFiltro.value;
			permiteSubir = true;
		} else {
			blankFields();
			fAlert("\n La solicitud no existe o se encuentra en una etapa en la cual no es posible relizar ésta operación.");

		}
	}

	if (cId == "Guardar" && cError == "") {
		fCancelar();
		fAlert("\n Se ha registrado con éxito la resolución de visita técnica para ésta solicitud.");
		fAlert("\nDebe subir el formato de Minuta de Visita Técnica.");
		// if(negativa==true&&solN>0&&ejerN>0){
		permiteSubir = true;
		if (negativa == true)
			fOficios(ejerN, solN);
		else
			fOficios(ejerN, solN);

		// }
	}

	if (cId == "CIDOficinaDeptoXUsr" && cError == "") {
		if (aRes.length > 0) {
			frm.iCveOfic.value = aRes[0][1];
			frm.iCveDpto.value = aRes[0][2];
		}
	}
}

function blankFields() {
	fCancelar();
	frm.cHomoclave.value = "";
	frm.cDscTramite.value = "";
	frm.cDscModalidad.value = "";
	frm.cSolicitante.value = "";
	frm.tFechaVisita.value = "";

	frm.cRepCSCT.value = "";
	frm.cCargoCSCT.value = "";
	frm.cComentCSCT.value = "";

	frm.cRepProm.value = "";
	frm.cCargoProm.value = "";
	frm.cComentProm.value = "";

	frm.cRepConce.value = "";
	frm.cCargoConce.value = "";
	frm.cComentConce.value = "";

	frm.cObs.value = "";
	frm.cAcuerdos.value = "";

	frm.lResolucion.value = -1;
	frm.iEjercicio.value = "";
	frm.iNumSolicitud.value = "";
	setSubioFormato(false);
	permiteSubir = false;

}

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo() {

	if (frm.cSolicitante.value != "" && frm.cHomoclave.value != ""
			&& frm.cDscTramite.value != "" && frm.cDscModalidad.value != ""
			&& frm.tFechaVisita.value != "") {
		puedeAgregarAcuerdo = true;
		FRMPanel.fSetTraStatus("UpdateBegin");
		frm.lResolucion.disabled = false;

		frm.cRepCSCT.disabled = false;
		frm.cCargoCSCT.disabled = false;
		frm.cComentCSCT.disabled = false;

		frm.cRepProm.disabled = false;
		frm.cCargoProm.disabled = false;
		frm.cComentProm.disabled = false;

		frm.cRepConce.disabled = false;
		frm.cCargoConce.disabled = false;
		frm.cComentConce.disabled = false;

		frm.cObs.disabled = false;
		frm.cAcuerdos.disabled = false;

		permiteSubir = true;
	} else {
		fAlert("\nDebe buscar una solicitud existente.");
	}

}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar() {
	var txRes = "";

	frm.cAcuerdos.value = generaCadenaAcuerdos();

	if (indiceAcuerdos == 0 || frm.cAcuerdos.value == "") {
		fAlert("\n Debe proporcionar al menos un acuerdo.");
		return false;
	}

	if (frm.lResolucion.value == -1) {
		fAlert("\n Debe seleccionar el resultado de la resolución.");
		return false;
	} else {

		if (frm.lResolucion.value == 0) {
			txRes = "NEGATIVA";
			negativa = true;
		} else if (frm.lResolucion.value == 1) {
			txRes = "POSITIVA";
			negativa = false;
		}

		if (confirm("\n ¿Desea guardar una resolución " + txRes
				+ " a la visita técnica?")) {
			frm.hdBoton.value = "Guardar";
			ejerN = frm.iEjercicio.value;
			solN = frm.iNumSolicitud.value;
			fNavega("Guardar");
		}
	}
}

// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar() {
	return;
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar() {
	puedeAgregarAcuerdo = false;
	indiceAcuerdos = 0;

	while (tBarraWizard.rows.length > 0) {
		tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
	}

	FRMPanel.fSetTraStatus("AddOnly");
	frm.lResolucion.disabled = true;

	frm.cRepCSCT.disabled = true;
	frm.cCargoCSCT.disabled = true;
	frm.cComentCSCT.disabled = true;

	frm.cRepProm.disabled = true;
	frm.cCargoProm.disabled = true;
	frm.cComentProm.disabled = true;

	frm.cRepConce.disabled = true;
	frm.cCargoConce.disabled = true;
	frm.cComentConce.disabled = true;

	frm.cObs.disabled = true;
	frm.cAcuerdos.disabled = true;

	frm.iEjercicioFiltro.disabled = false;
	frm.iNumSolicitudFiltro.disabled = false;
}
// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar() {
	return;
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo() {
	return true;
}

function fImprimir() {
	self.focus();
	window.print();
}



function fTraerSolicitud() {
	if (frm.iEjercicioFiltro.value != 0 && frm.iEjercicioFiltro.value != ''
			&& frm.iNumSolicitudFiltro.value != 0
			&& frm.iNumSolicitudFiltro.value != '') {

		frm.hdFiltro.value = " iEjercicio = " + frm.iEjercicioFiltro.value
				+ " and iNumSolicitud = " + frm.iNumSolicitudFiltro.value;

	} else
		frm.hdFiltro.value = "";
	frm.hdOrden.value = "";
	frm.hdNumReg.value = "";
	fEngSubmite("pg111020020A1.jsp", "idSolicitud");
}

function fTraeOficDepUsrLocal() {
	frm.hdLlave.value = "";
	frm.hdSelect.value = "SELECT " + "ICVEOFICINA,ICVEDEPARTAMENTO "
			+ "FROM GRLUSUARIOXOFIC " + "where ICVEUSUARIO = "
			+ frm.iCveUsuario.value;

	fEngSubmite("pgConsulta.jsp", "OficDepUsrLocal");
}

function fMinutaVT() {

	var cNomFormato = 'MinutaVTADV.docx';
	var urlForm = cRutaProgMM + cRutaFormatosADV + cNomFormato;
	window.open(urlForm, 'download', 'width=1, height=1');

}

function fOficios(ejer, sol) {

	if (negativa == true) {
		cClavesModulo = "3,3,";
		cNumerosRep = "75,45,";
		cFiltrosRep = ejer + "," + sol + "," + cSeparadorRep;
		cFiltrosRep += cFiltrosRep;
	} else {
		cClavesModulo = "3,";
		cNumerosRep = "75,";
		cFiltrosRep = ejer + "," + sol + "," + cSeparadorRep;
	}

	fReportes();
}

function fSubirMinutaVT() {

	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	}

	permiteSubir = true;

	if (frm.iEjercicioFiltro.value != 0 && frm.iEjercicioFiltro.value != ''
			&& frm.iNumSolicitudFiltro.value != 0
			&& frm.iNumSolicitudFiltro.value != '') {
		if (fSoloNumeros(frm.iEjercicioFiltro.value)
				&& fSoloNumeros(frm.iNumSolicitudFiltro.value)
				&& (permiteSubir == true || negativa == true)) {
			fAbreSubWindow(false, "pgSubirMinutaVT", "no", "yes", "yes", "yes",
					"800", "600", 50, 50);
		} else
			fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	} else {
		fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	}
}

function getNumSol() {
	return frm.iNumSolicitudFiltro.value;
}

function getEjercicio() {
	return frm.iEjercicio.value;
}

function setSubioFormato(valor) {
	subioFormato = valor;
}

function onChangeResol() {
	if (frm.lResolucion.value == 1) {
		permiteSubir = true;
		subioFormato = false;
	} else {
		permiteSubir = false;
		subioFormato = false;
	}
}

function agregarComentario() {
	if (puedeAgregarAcuerdo == true) {
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.className = "EEtiqueta";
		tCell.align = "center";
		tCell.innerHTML = TDEtiAreaTextoID(
				prefijoAcuerdo + indiceAcuerdos,
				false,
				"EEtiqueta",
				1,
				"Acuerdo/Conclusión:",
				75,
				4,
				"",
				"",
				"Acuerdo/Conclusión:",
				"",
				"fMayus(this);",
				'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',
				true, true, true, "ECampo", 3);
		indiceAcuerdos++;
	}
}

function generaCadenaAcuerdos() {
	var acuerdoCadena = "";
	for ( var a = 0; a < indiceAcuerdos; a++) {
		var el = document.getElementById(prefijoAcuerdo + a);

		if (el != null && myTrim(el.value)) {
			// elimino el token separador de la cadena si existe
			var cad = el.value.replace(/~/g, "");
			// agrego el token separador
			acuerdoCadena += cad + "~";
		}
	}
	return acuerdoCadena;
}

function myTrim(x) {
	return x.replace(/^\s+|\s+$/gm, '');
}
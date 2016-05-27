// MetaCD=1.0
// Title: pg10053015000.js
// Description: Carpeta de creacion de grupos de placas
// Company: INFOTEC
// Author: alopez

var cTitulo = "";
var FRMListado = "";
var frm;
var tBarraWizard;
var tBarraWizardReqs;
var tBarraWizardBotones;
var tBarraConsecutivo;
var guardado = 0;
var entrar = 0;
var CAR_ESCENCIAL = 22;
var aIngresos = new Array();
var traerAutopistas = false;
var obtenSentido = true;

var arrReqs = new Array();

var registrado = false;

var modo;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgINTSol01FIEL.js";
	if (top.fGetTituloPagina) {
		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	} else {
		cTitulo = "Solicitud por internet";
	}
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	Estilo(
			".ETablaInfoPanel",
			"height:30px;border:1px solid #6B96AD;background-color:#C0C0C0;text-align:center;font-family:Verdana;font-size:10pt;TEXT-DECORATION:none;font-weight:Bold;background-color: #C0C0C0;");
	Estilo(".ESUP1",
			"COLOR:800000;font-family:Verdana;font-size:9pt;font-weight: Normal;");
	Estilo(".ESUP2",
			"COLOR:800000;font-family:Verdana;font-size:9pt;font-weight: Normal;");
	Estilo(
			".ECOR",
			"COLOR:2B0082;font-family:Verdana;font-size:8pt;font-weight: Normal;text-align:center;");
	fInicioPagina("E4E4E4");
	Estilo(
			"A:Link",
			"COLOR:00049E;font-family:Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
	Estilo(
			"A:Visited",
			"COLOR:00049E;font-family: Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
	InicioTabla("", 0, "100%", "", "", "", "1");
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "100%", "", "center");
	ITRTD("EEtiquetaC", 0, "100%", "25", "center", "top");
	TextoSimple("SI DESEAS BUSCAR UNA SOLICITUD ANTES CAPTURADA, POR FAVOR TECLEA LA CLAVE DEL TRÁMITE:");
	FTDTR();
	ITRTD("EEtiquetaC", 0, "100%", "25", "center", "top");
	Text(false, "cBusca", "", 10, 10, "", "fMayus(this);",
			'onKeyDown="return fCheckReturn(event);"');

	// Liga("[BUSCAR]", "fBuscar();", "NUEVO TRÁMITE", "lBusca");
	Liga("[BUSCAR]", "buscaSolicitante(1);", "NUEVO TRÁMITE", "lBusca");

	FTDTR();
	ITRTD("EEtiquetaC", 0, "100%", "25", "center", "top");
	cGPD += "<div id='panelNuevoTramite1' style='display:block;'>";
	TextoSimple("SI DESEAS INICIAR UNA NUEVA SOLICITUD DA CLIC EN LA SIGUIENTE LIGA:");
	cGPD += "</div>";
	FTDTR();
	ITRTD("ELIGA", 0, "100%", "25", "center", "top");
	cGPD += "<div id='panelNuevoTramite2' style='display:block;'>";
	Liga("[NUEVO TRÁMITE]", "buscaSolicitante(2);", "NUEVO TRÁMITE");
	cGPD += "</div>";
	FTDTR();
	ITRTD("ESUP1", 0, "", "", "center", "");

	InicioTabla("", 0, "100%", "", "center");
	ITRTD("ESUP1", 0, "70%", "", "center", "");
	TextoSimple('"En caso de Dudas o Aclaraciones enviar correo electrónico a:"');
	cGPD += '<a href="mailto:cheredia@sct.gob.mx">cheredia@sct.gob.mx</a>';
	// TextoSimple('<BR>"Si su trámite es del interior de la República enviar
	// correo electrónico a:"');
	// cGPD+='<a href="mailto:cheredia@sct.gob.mx">cheredia@sct.gob.mx</a>';
	FTDTR();
	ITRTD("ESUP2", 0, "", "", "center", "middle");
	TextoSimple('<BR>Recuerde que sus documentos digitalizados deben ir en formato PDF no mayor a 2MB.');
	TextoSimple('<BR>Deberá concluir la <B>Declaración de características</B> para que su solicitud sea procesada en ventanilla.');
	FTDTR();
	FinTabla();

	FTDTR();
	ITRTD("", 0, "", "40", "center", "bottom");
	DinTabla("BarraWizard", "", 0, "", "100%", "center", "top", "", ".1", ".1");// IFrame("IPanelINT","95%","34","Paneles.js");
	FTDTR();
	ITRTD("", 0, "", "40", "center", "bottom");
	DinTabla("BarraWizardReqs", "", 0, "", "100%", "center", "top", "", ".1",
			".1");// IFrame("IPanelINT","95%","34","Paneles.js");
	FTDTR();
	ITRTD("", 0, "", "40", "center", "bottom");
	DinTabla("BarraWizardBotones", "", 0, "", "100%", "center", "top", "",
			".1", ".1");// IFrame("IPanelINT","95%","34","Paneles.js");
	FTDTR();
	ITRTD("", 0, "", "175", "center", "top");
	cGPD += "<div id='panelBusqueda' style='display:none;'>";
	IFrame("IListadoINT", "95%", "170", "ListadoINT.js", "yes", true);
	cGPD += "</div>";
	FTDTR();
	ITRTD("", 0, "", "40", "center", "bottom");
	DinTabla("BarraConsecutivo", "", 0, "", "100%", "center", "top", "", ".1",
			".1");
	FTDTR();

	FTDTR();
	FinTabla();
	FTDTR();
	ITRTD("", 0, "", "40", "center", "bottom");
	// IFrame("IPanelINTC","95%","34","Paneles.js");
	FTDTR();
	FinTabla();
	Hidden("ICONSECUTIVO", "");
	Hidden("LFINALIZADO", "0");
	Hidden("ICVEDEPTO", "");
	Hidden("CPERMISIONA", "");
	Hidden("LCONCLUIDO", "");
	Hidden("iCveSolicitante", 0);
	Hidden("iCveRepLegal", 0);
	Hidden("iEjercicio", 0);
	Hidden("iNumSolicitud", 0);
	Hidden("iNumCita", 0);
	Hidden("iCveTramiteTmp", 0);
	Hidden("iCveModalidadTmp", 0);
	Hidden("cRFCRep", "");
	Hidden("cCveInterna", "");
	Hidden("cModalidad", "");
	Hidden("hdSelect");
	Hidden("hdLlave");
	Hidden("hdNomAuto", "");
	Hidden("hdEjer");
	Hidden("");
	Hidden("cSentido", "");
	Hidden("cCadReqs", "");
	fFinPagina();
}

function fOnLoad() {
	frm = document.forms[0];
	FRMListado = fBuscaFrame("IListadoINT");

	FRMListado.fSetControl(self);
	FRMListado
			.fSetTitulo("Llenado,Ejercicio/Solicitud,Clave del Trámite,Tipo de trámite, Modalidad,");
	FRMListado.fSetCampos("1,0,5,6,");
	FRMListado.fSetObjs(0, "Liga", {
		label : "Declaración de características",
		toolTip : "",
		style : "color:#0000FF;text-decoration:underline;"
	});
	// FRMListado.fSetObjs(1,"Liga",{label:"Borrar", toolTip: "", style:
	// "color:#0000FF;text-decoration:underline;"});

	fDisabled(false);
	frm.hdBoton.value = "";

	tBarraWizard = document.getElementById("BarraWizard");
	tBarraWizardReqs = document.getElementById("BarraWizardReqs");
	tBarraWizardBotones = document.getElementById("BarraWizardBotones");
	tBarraConsecutivo = document.getElementById("BarraConsecutivo");
}

/*
 * function fNuevoTramite(){ fShowFormulario(); fHideListado(); frm.cBusca.value =
 * ""; frm.iNumCita.value = "0"; frm.ICONSECUTIVO.value = "0";
 * frm.cBusca.disabled = true; frm.hdOrden.value = "iCveOficina";
 * frm.hdFiltro.value = " iCveOficina > 0"; frm.hdNumReg.value = 10000;
 * fEngSubmite("pgGRLOficina.jsp","cIdOficina"); frm.hdOrden.value = ""; }
 */

function fBuscar(liga) {
	var patron = /^([0-9]+)$/
	// if(!fGO("lBusca").disabled){

	if (frm.cBusca.value.match(patron)) {
		frm.iNumCita.value = frm.cBusca.value;
		frm.hdNumReg.value = 10000;
		frm.hdBoton.value = "Buscar";
		frm.hdOrden.value = "";
		frm.hdFiltro.value = "";
		fEngSubmite("pgINTTram1A.jsp", "IDTram");
	} else {
		fAlert("\n - El campo solo acepta valores numericos");
	}
	// }
}

function fGuardarNuevo() {
	
	if(generaCadenaReqs()==true)
		return;

	var resul = validaTodo();

	if (resul != "") {
		fAlert(resul);
	} else {
		
		if(confirm("¿Desea continuar con la información en pantalla?")){
			frm.hdNumReg.value = 10000;
			frm.hdBoton.value = "GuardarSolADV";
			frm.hdOrden.value = "";
			frm.hdFiltro.value = "";
			fEnProceso(true);
			setTimeout(function() {
				fEngSubmite("pgINTTram1A.jsp", "IDTram");
			}, 250);
		}
	}
	
	
}

function fBorrar() {
	frm.hdNumReg.value = 10000;
	frm.hdBoton.value = "Borrar";
	frm.hdOrden.value = "";
	frm.hdFiltro.value = "";
	fEngSubmite("pgINTTram1A.jsp", "IDTram");
}

function fFinalizar() {
	if (frm.LCONCLUIDO.value > 0) {
		frm.hdNumReg.value = 10000;
		frm.hdBoton.value = "Finalizar";
		frm.hdOrden.value = "";
		frm.hdFiltro.value = "";
		fEngSubmite("pgINTTram1A.jsp", "IDTram");
	} else {
		fAlert("\n - No puedes FINALIZAR el Trámite hasta que realices el registro del vehículo");
	}
}

function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, grd) {
	if (cError == "Guardar")
		fAlert("Existió un error en el Guardado!");
	if (cError == "Borrar")
		fAlert("Existió un error en el Borrado!");
	if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	}

	if (cId == "cIdOrganoAdm" && cError == "") {

	}

	if (cId == "cIdBuscaSolicitante" && cError == "") {

		if (aRes.length > 0) {
			registrado = true;
		} else {
			registrado = false;
		}

		if (window.parent) {
			if (window.parent.setExisteSolicitante) {
				window.parent.setExisteSolicitante(registrado);
			}
		}

		if (modo == 1) {
			fBuscar();
		}

		if (modo == 2) {
			fNuevoTramite();
		}
	}

	if (cId == "cIdTramite") {
		for (i = 0; i < aRes.length; i++) {
			aRes[i][25] = aRes[i][1] + " - " + aRes[i][3];
		}
		frm.hdOrden.value = "";
		frm.hdFiltro.value = "";
		fFillSelect(frm.iCveTramite, aRes, false, frm.iCveTramite.value, 0, 25);
		fLoadModalidad();

	}

	if (cId == "cIdModalidad") {
		fFillSelect(frm.iCveModalidad, aRes, false, frm.iCveModalidad.value, 1,
				11);
		var indice = frm.iCveModalidad.selectedIndex;
		if (indice > 0)
			frm.CPERMISIONA.value = frm.iCveModalidad.options[indice].text;

		fLoadAutopistas();
	}

	if (cId == "cIdCarretera" && cError == "") {

		if (aRes.length > 0) {
			for ( var f = 0; f < aRes.length; f++) {
				aRes[f][1] = f + 1;
			}
		}

		fFillSelect(frm.iCveCarretera, aRes, false, frm.iCveCarretera.value, 0,
				0);
		fLoadCentros();

	}

	if (cId == "cIOficADV") {
		fFillSelect(frm.iCveOficina, aRes, false, frm.iCveOficina.value, 0, 1);
		getReqs();
	}

	if (cId == "cIdBuscaReqInternet") {
		arrReqs = fCopiaArreglo(aRes);
		fShowReqsADV(arrReqs);
	}

	if (cId == "cIdCadenamiento" && cError == "") {
		fFillSelect(frm.iCveCadenamiento, aRes, true,
				frm.iCveCadenamiento.value, 0, 1);
		if (aRes.length == 0)
			frm.cCad.value = "";
	}

	if (cId == "IDTram" && cError == "") {
		fEnProceso(false);
		frm.hdBoton.value = "";
		if (aRes.length > 0) {
			fCancelarNuevoTramite();
			fShowListado();
			frm.hdRowPag.value = iRowPag;
			FRMListado.fSetListado(aRes);
			FRMListado.fShow();
			FRMListado.fSetLlave(cLlave);
			guardado = grd;
			fConsultaIngresos();
		} else {
			fAlert("\n - El trámite " + frm.iNumCita.value
					+ " no se encuetra registrado.");
			frm.iNumCita.value = "";
		}
		aIngresos = new Array();
	}
	if (cId == "cIdIngresos" && cError == "") {
		aIngresos = aRes;
	}
}

// function fShowFormulario() {
// bGuardar = "<div class style='padding: 2 15 2 15;cursor:pointer;'
// onclick='fGuardarNuevo();'>Guardar</div>";
// bCancelar = "<div class style='padding: 2 15 2 15;cursor:pointer;'
// onclick='fCancelarNuevoTramite();'>Cancelar</div>";
// while (tBarraWizard.rows.length > 0) {
// tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
// }
//
// tBarraWizard.className = "ETablaInfo";
// tBarraWizard.width = "100%";
// tRw = tBarraWizard.insertRow();
//
// tCell = tRw.insertCell();
// if (top.fGetUA() == 1)
// tCell.innerHTML = "*Seleccione la Oficina donde su solicitud será atendida:";
// if (top.fGetUA() == 2)
// tCell.innerHTML = "*Seleccione la Oficina donde su solicitud será
// entregada:";
// tCell.className = "EEtiqueta";
// tCell = tRw.insertCell();
// tCell.innerHTML = Select("iCveOficina", "onChangeOf();");
//
// tRw = tBarraWizard.insertRow();
// tCell = tRw.insertCell();
// tCell.innerHTML = "*Tipo de Tramite:";
// tCell.className = "EEtiqueta";
// tCell = tRw.insertCell();
// tCell.innerHTML = Select("iCveTramite", "fLoadModalidad();");
//
// tRw = tBarraWizard.insertRow();
// tCell = tRw.insertCell();
// tCell.innerHTML = "*Modalidad:";
// tCell.className = "EEtiqueta";
// tCell = tRw.insertCell();
// tCell.innerHTML = Select("iCveModalidad","getReqs();");
//
// tRw = tBarraWizard.insertRow();
// tCell = tRw.insertCell();
// tCell.colSpan = 2;
// tCell.align = "center";
// tCell.innerHTML = "<table border='0'><tr><td class='ETablaInfoPanel'>"
// + bGuardar + "</td><td class='ETablaInfoPanel'>" + bCancelar
// + "</td></tr></table>";
// }

function fShowFormularioADV() {
	bGuardar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fGuardarNuevo();'>Guardar</div>";
	bCancelar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fCancelarNuevoTramite();'>Cancelar</div>";
	while (tBarraWizard.rows.length > 0) {
		tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
	}

	tBarraWizard.className = "ETablaInfo";
	tBarraWizard.width = "100%";

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "DATOS DE LA SOLICITUD:";
	tCell.className = "ETablaST";
	tCell.colSpan = 2;
	tCell.align = "center";

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	if (top.fGetUA() == 1)
		tCell.innerHTML = "*Tipo de aprovechamiento:";
	if (top.fGetUA() == 2)
		tCell.innerHTML = "*Tipo de aprovechamiento:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Select("iCveTramite", "fLoadModalidad();");

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Nuevo o regularización:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Select("iCveModalidad", "fLoadAutopistas()");

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Autopista:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Select("iCveCarretera", "fOnChangeAutopista();");

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Oficina donde su solicitud será atendida:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Select("iCveOficina", "");

	/*
	 * tRw = tBarraWizard.insertRow(); tCell = tRw.insertCell(); tCell.innerHTML =
	 * "Órgano Administrativo:"; tCell.className = "EEtiqueta"; tCell =
	 * tRw.insertCell(); tCell.innerHTML = Text(false, "cOrgano", "", 70, 70,
	 * "Organo", "", "", "", false, false);
	 */

	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "Acceso Común:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = CheckBox("chkComun", "", false, "", "",
	// "onClick='fVerifCheck(1)'", "", true, false);
	//
	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "Acceso a Estación o Parador:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = CheckBox("chkEstacion", "", false, "", "",
	// "onClick='fVerifCheck(2)'", "", true, false);
	//
	//
	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "Sentido:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = Select("iCveSentido", "fOnChangeSentido();");
	//
	//	
	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "Cadenamientos Disponibles:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = Select("iCveCadenamiento", "fOnChangeCad();");
	//
	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "Cadenamiento Inicio:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = Text(false, "cCadIni", "000+000", 20, 7, "CadIni", "",
	// "", "",
	// false, false);
	//
	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "Cadenamiento Final:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = Text(false, "cCadFin", "000+000", 20, 7, "CadFin", "",
	// "", "",
	// false, false);
	//
	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "Cadenamiento:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = Text(false, "cCad", "000+000", 30, 7, "Cad", "", "",
	// "", false,
	// false);
	/*
	 * tRw = tBarraWizard.insertRow(); tCell = tRw.insertCell(); tCell.innerHTML =
	 * "Sentido:"; tCell.className = "EEtiqueta"; tCell = tRw.insertCell();
	 * tCell.innerHTML = Text(false, "cSentido", "", 45, 30, "Sentido", "", "",
	 * "", false, false);
	 */

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Destino:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = '<textarea name="cHechos" rows="4" cols="50"/>';

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Cadenamientos y sentidos:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = '<textarea name="cKmSentido" rows="4" cols="50"/>';

	// tRw = tBarraWizard.insertRow();
	// tCell = tRw.insertCell();
	// tCell.innerHTML = "*Tramo:";
	// tCell.className = "EEtiqueta";
	// tCell = tRw.insertCell();
	// tCell.innerHTML = Text(false, "cTramo", "", 60, 100, "Tramo", "", "", "",
	// false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Autorizado a Recoger:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(false, "cAutorizado", "", 70, 70, "Autorizado", "",
			"", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "SELECCIONE LOS REQUISITOS QUE ENTREGARÁ EN FORMATO DIGITAL (PDF)";
	tCell.className = "ETablaST";
	tCell.colSpan = 2;
	tCell.align = "center";
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.className = "EEtiqueta";
	tCell.colSpan = 2;
	tCell.align = "left";
	tCell.innerHTML = "** NOTA: UNA VEZ GUARDADOS LOS REQUISITOS SELECCIONADOS DEBERA PROPORCIONAR UN DOCUMENTO DIGITAL POR REQUISITO.";

	tRw = tBarraWizardBotones.insertRow();
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.align = "center";
	tCell.innerHTML = "<table border='0'><tr><td class='ETablaInfoPanel'>"
			+ bGuardar + "</td><td class='ETablaInfoPanel'>" + bCancelar
			+ "</td></tr></table>";

}

function getReqs() {

	if (frm.iCveTramite.value > 0 && frm.iCveModalidad.value > 0) {

		frm.hdBoton.value = "buscaReqInternet";
		fEngSubmite("pgDocsSolADV.jsp", "cIdBuscaReqInternet");
	}
}

function fShowReqsADV(aResReqs) {

	while (tBarraWizardReqs.rows.length > 0) {
		tBarraWizardReqs.deleteRow(tBarraWizardReqs.rows.length - 1);
	}

	tBarraWizardReqs.className = "ETablaInfo";
	tBarraWizardReqs.width = "100%";

	for ( var k = 0; k < aResReqs.length; k++) {

		aResReqs[k][0] = false;

		tRw = tBarraWizardReqs.insertRow();
		tCell = tRw.insertCell();
		tCell.align = "left";
		tCell.colspan = 2;

		tCell.innerHTML = CheckBox("chk_req_" + aResReqs[k][1], "", false, "",
				"", "onClick='fCheckReq(" + k + ",this)'", "", true, false);

		tCell.innerHTML += '<label class="EEtiqueta">' + aResReqs[k][2]
				+ '</label>';
	}

}

function fCheckReq(idx, caja) {
	arrReqs[idx][0] = caja['checked'];
}

function fNuevoTramite() {
	if (window.parent) {
		if (window.parent.fGetRFCRep) {
			frm.cRFCRep.value = window.parent.fGetRFCRep();
		}
	}

	fShowFormularioADV();

	fHideListado();
	frm.hdOrden.value = "T.cCveInterna";
	frm.hdFiltro.value = " lTramInt=1 AND lVigente=1 and LTRAMITEFINAL=1";
	if (top.fGetUA() == 1)
		frm.hdFiltro.value += " AND T.cCveInterna not like 'PT%'";
	if (top.fGetUA() == 2)
		frm.hdFiltro.value += " AND T.cCveInterna like 'PT%'";
	fEngSubmite("pgTRACatTramite1.jsp", "cIdTramite");
}

function fLoadAutopistas() {
	frm.hdSelect.value = "SELECT DISTINCT(CDSCARRETERA) FROM TRACATCARRETERA ORDER BY CDSCARRETERA ASC";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdCarretera");
}
/*
 * function fLoadAutopistas(){
 * 
 * frm.cBusca.value = ""; frm.iNumCita.value = "0"; frm.ICONSECUTIVO.value =
 * "0"; frm.cBusca.disabled = true; frm.hdOrden.value = "iCveOficina";
 * frm.hdFiltro.value = " iCveOficina > 0"; frm.hdNumReg.value = 10000;
 * fEngSubmite("pgGRLOficina.jsp","cIdOficina"); frm.hdOrden.value = ""; }
 */
function fLoadCentros() {

	var e = document.getElementById("iCveCarretera");
	var descAutopista = e.options[e.selectedIndex].text;

	frm.hdNomAuto.value = descAutopista;

	frm.hdSelect.value = "SELECT OFC.ICVEOFICINA, OFC.CDSCBREVE FROM GRLOFICINA OFC "
			+ "JOIN TRACATCARRETERA CAR ON CAR.ICVEOFICINA = OFC.ICVEOFICINA WHERE CAR.CDSCARRETERA LIKE '%"
			+ descAutopista + "%' ORDER BY ICVEOFICINA";
	frm.hdLlave.value = "ICVEOFICINA";
	fEngSubmite("pgConsulta.jsp", "cIOficADV");
}

function fLoadCampos() {
}

function fShowListado() {
	document.getElementById("panelBusqueda").style.display = "block";

	bAgregar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fGuardarNuevo();'>Nuevo Movimiento</div>";
	bFinalizar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fFinalizar();'>Finalizar Trámite</div>";

	while (tBarraConsecutivo.rows.length > 0) {
		tBarraConsecutivo.deleteRow(tBarraConsecutivo.rows.length - 1);
	}

	tBarraConsecutivo.className = "ETablaInfo";
	tBarraConsecutivo.width = "90%";
	tRw = tBarraConsecutivo.insertRow(0);
	tCell = tRw.insertCell();
	tCell.innerHTML = "Tipo Trámite:" + Hidden("ICVETIPOPERMISIONA", "")
			+ Hidden("ICVETIPOTRAMITE", "");
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(false, "CTIPOPERMISIONARIO", "", 100, 100, "", "",
			"disabled='disabled'");
	tRw = tBarraConsecutivo.insertRow(1);
	tCell = tRw.insertCell();
	tCell.innerHTML = "Modalidad:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(false, "CDSCTIPOTRAMITE", "", 100, 100, "", "",
			"disabled='disabled'");
	tRw = tBarraConsecutivo.insertRow(2);
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.id = "trTramiteButtons";
	tCell.align = "center";
	// tCell.innerHTML = "<table border='0'><tr><td class='ETablaInfoPanel'>" +
	// bAgregar ;
}

function fHideListado() {
	document.getElementById("panelBusqueda").style.display = "none";
	while (tBarraConsecutivo.rows.length > 0) {
		tBarraConsecutivo.deleteRow(tBarraConsecutivo.rows.length - 1);
	}
}

function fCancelarNuevoTramite() {
	while (tBarraWizard.rows.length > 0) {
		tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
	}
	while (tBarraWizardReqs.rows.length > 0) {
		tBarraWizardReqs.deleteRow(tBarraWizardReqs.rows.length - 1);
	}
	while (tBarraWizardBotones.rows.length > 0) {
		tBarraWizardBotones.deleteRow(tBarraWizardBotones.rows.length - 1);
	}
	frm.cBusca.disabled = false;
	// fGO("lBusca").disabled = false;
}

function fCancelar() {
	fCancelarNuevoTramite();
}

function fLoadModalidad() {
	if (frm.iCveTramite.value > 0) {
		frm.hdNumReg.value = 10000;
		frm.hdOrden.value = "";
		frm.hdFiltro.value = "lActivo=1 AND TC.iCveTramite = "
				+ frm.iCveTramite.value + " AND tc.LTRAMINT=1 ";
		fEngSubmite("pgTRAConfiguraTramite.jsp", "cIdModalidad");
	}
}

function fSelReg(aDato, col) {
	

		frm.iNumCita.value = aDato[0];
		frm.cBusca.value = aDato[0];
		frm.ICONSECUTIVO.value = aDato[1];
		frm.CTIPOPERMISIONARIO.value = aDato[5];
		frm.CDSCTIPOTRAMITE.value = aDato[6];
		frm.iCveTramiteTmp.value = aDato[2];
		frm.iCveModalidadTmp.value = aDato[3];
		frm.ICVEDEPTO.value = aDato[8];
		frm.LCONCLUIDO.value = aDato[11];
		frm.iEjercicio.value = aDato[9];
		frm.iNumSolicitud.value = aDato[10];
		if (aDato[7] != "") {
			// fGO("trTramiteButtons").innerHTML = "<td></td>";
			frm.LFINALIZADO.value = 1;
		} else {
			frm.LFINALIZADO.value = 0;
		}
		if (col == 0) {
			parent.setIREGISTRO(frm.ICONSECUTIVO.value);
			parent.fPagFolder(2);
		}

	/***************************************************************************
	 * else if(col == 1){ if(getLFINALIZADO() == 0){ fBorrar(); }else{
	 * fAlert("\n - Trámite finalizado. No se pueden modificar ni borrar
	 * datos."); } }
	 */
}

function fCheckReturn(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode == 13) {
		// fBuscar();
		buscaSolicitante(1);
		return false;
	}
	return true;
}

function getICVETRAMITE() {
	return frm.iNumCita.value;
}

function getICONSECUTIVO() {
	return frm.ICONSECUTIVO.value;
}

function getICVETIPOTRAMITE() {
	return frm.ICVETIPOTRAMITE.value;
}

function getLFINALIZADO() {
	return frm.LFINALIZADO.value;
}

function getICVETIPOPERMISIONA() {
	return frm.ICVETIPOPERMISIONA.value;
}

function getCTIPOPERMISIONARIO() {
	return frm.CTIPOPERMISIONARIO.value;
}

function getCDSCTIPOTRAMITE() {
	return frm.CDSCTIPOTRAMITE.value;
}

function setValues(guard) {
	guardado = guard;
}
function fGetCveTramite() {
	return frm.iCveTramiteTmp.value;
}
function fGetModalidad() {
	return frm.iCveModalidadTmp.value;
}

function fGetEjercicio() {
	return frm.iEjercicio.value;
}
function fGetSolicitud() {
	return frm.iNumSolicitud.value;
}
function fGetTramiteDsc() {
	return frm.iCveTramite.options[frm.iCveTramite.selectedIndex].text
}
function fGetModalidadDsc() {
	return frm.iCveModalidad.options[frm.iCveModalidad.selectedIndex].text
}
function fConsultaIngresos() {
	frm.hdNumReg.value = 10000;
	fEngSubmite("pgObtieneIngresos.jsp", "cIdIngresos");
}
function fGetIngresos() {
	return aIngresos;
}

function fDistingueTramite(tipo) {
	/*
	 * 2 - acessos 6,10 - instalaciones marginales 4 - anuncios 7 - modificacion
	 * 8,9,3 - cruzamientos 5 - señales
	 */
	// desHab();
	//
	// if (frm.iCveTramite.value == 2) {
	// camposAccesos();
	// } else {
	// frm.chkComun.checked = false;
	// frm.chkEstacion.checked = false;
	// frm.chkComun.disabled = true;
	// frm.chkEstacion.disabled = true;
	// }
	//
	// if (frm.iCveTramite.value == 6 || frm.iCveTramite.value == 10) {
	// camposMarginal();
	// }
	//
	// if (frm.iCveTramite.value == 4) {
	// camposAnuncios();
	// }
	//
	// if (frm.iCveTramite.value == 7) {
	// camposModif();
	// obtenSentido=false;
	// frm.iCveSentido.disabled=true;
	// }
	//
	// if (frm.iCveTramite.value == 8 || frm.iCveTramite.value == 9
	// || frm.iCveTramite.value == 3) {
	// camposCruzamiento();
	// obtenSentido=false;
	// frm.iCveSentido.disabled=true;
	// }
	//
	// if (frm.iCveTramite.value == 5) {
	// camposSenial();
	// }

	fLoadModalidad(tipo);

}

// function camposModif() {
// frm.cCad.disabled = false;
// frm.iCveSentido.disabled = false;
// }
//
// function camposCruzamiento() {
// frm.cCad.disabled = false;
// }
//
// function camposSenial() {
// frm.cCad.disabled = false;
// frm.iCveSentido.disabled = false;
// }
//
// function camposAnuncios() {
// frm.cCad.disabled = false;
// frm.iCveSentido.disabled = false;
// }
//
// function camposMarginal() {
//
// frm.cCadIni.disabled = false;
// frm.cCadFin.disabled = false;
// frm.cCadIni.value = "";
// frm.cCadFin.value = "";
//
// frm.cCad.value = "";
// frm.cCad.disabled = true;
//
// frm.iCveCadenamiento.disabled = true;
//
// }
//
// function camposAccesos() {
//
// frm.chkComun.disable = false;
// frm.chkEstacion.disable = false;
//
// frm.cCad.disabled = false;
// frm.iCveSentido.disabled = false;
//
// frm.chkComun.checked = true;
// frm.chkEstacion.checked = false;
//
// frm.cCadIni.disabled = true;
// frm.cCadFin.disabled = true;
// frm.cCadIni.value = "";
// frm.cCadFin.value = "";
//
// fVerifCheck();
// }
//
function fOnChangeAutopista() {
	fLoadCentros();
}

// function fLoadCadenamientos() {
//
// if (frm.hdNomAuto.value != "" && frm.chkEstacion.checked == true
// && frm.iCveOficina.value > 0 && frm.iCveSentido.value > 0) {
// var cvAuto = " (SELECT ICVECARRETERA FROM TRACATCARRETERA WHERE "
// + " CDSCARRETERA ='" + frm.hdNomAuto.value + "' "
// + " AND ICVEOFICINA =" + frm.iCveOficina.value + ") ";
//
// frm.hdSelect.value = "SELECT ICVECAD, CDSCAD FROM TRACADXCAR "
// + "WHERE ICVECARRETERA = " + cvAuto + " AND ICVESENTIDO="+
// frm.iCveSentido.value +" ORDER BY CDSCAD ASC";
// frm.hdLlave.value = "ICVECAD";
// fEngSubmite("pgConsulta.jsp", "cIdCadenamiento");
// }
//
// }
//
// function fVerifCheck(idchk) {
//
// frm.chkComun.disabled = false;
// frm.chkEstacion.disabled = false;
// frm.iCveCadenamiento.disabled = true;
//
// if (idchk == 1) {
// if (frm.chkComun.checked == true) {
// frm.cCad.disabled = false;
// frm.cCad.value = "";
// frm.iCveCadenamiento.disabled = true;
// frm.chkEstacion.checked = false
// } else {
// frm.chkEstacion.checked = true;
// }
// }
//
// if (idchk == 2) {
// if (frm.chkEstacion.checked == true) {
// frm.cCad.disabled = true;
// frm.cCad.value = "";
// frm.iCveCadenamiento.disabled = false;
// frm.chkComun.checked = false;
// fLoadCadenamientos();
// } else {
// frm.chkComun.checked = true;
// }
//
// }
// }

function desHab() {

	frm.chkComun.disabled = true;
	frm.chkEstacion.disabled = true;
	frm.iCveCadenamiento.value = true;
	frm.cCadIni.disabled = true;
	frm.cCadFin.disabled = true;
	frm.cCad.disabled = true;
	frm.iCveSentido.disabled = true;
}

// function onChangeCad() {
//
// if (frm.iCveCadenamiento.value > 0) {
// var e = document.getElementById("iCveCadenamiento");
// var strCad = e.options[e.selectedIndex].text;
// frm.cCad.value = strCad;
// } else {
// frm.cCad.value = "";
// }
// }
//
// function onChangeOf() {
// fLoadCadenamientos();
// }

function buscaSolicitante(valor) {
	modo = valor;
	getRFCParam();
	frm.hdSelect.value = "SELECT " + " DISTINCT(P.ICVEPERSONA) "
			+ " FROM GRLPERSONA P  " + " WHERE P.CRFC ='" + frm.cRFCRep.value
			+ "'";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdBuscaSolicitante");
}

// function fCadenamiento(cMsg){
// if(frm.iCveTramite.value == 10 || frm.iCveTramite.value == 6){ //marginales
//		
// if(validaCadenamiento(frm.cCadIni.value)!=true)
// cMsg+="\n-El cadenamiento de inicio no cumple con el formato '000+000'";
//		
// if(validaCadenamiento(frm.cCadFin.value)!=true)
// cMsg+="\n-El cadenamiento de fin no cumple con el formato '000+000'";
//		
// if(cMsg=="")
// frm.cCad.value = frm.cCadIni.value + ' al ' + frm.cCadFin.value;
// }else{
// if(validaCadenamiento(frm.cCad.value)!=true)
// cMsg+="\n-El cadenamiento no cumple con el formato '000+000'";
// }
//	
// return cMsg;
// }

function getRFCParam() {
	if (window.parent) {
		if (window.parent.fGetRFCRep) {
			frm.cRFCRep.value = window.parent.fGetRFCRep();
			// alert(frm.cRFCRep.value);
		}
	}
}

//
// function fOnChangeSentido(){
// getSentido();
// fLoadCadenamientos();
// }
//
// function getSentido(){
// if(obtenSentido==true){
// var xe = document.getElementById("iCveSentido");
// var strCads = xe.options[xe.selectedIndex].text;
// frm.cSentido.value = strCads;
// }
// else{
// frm.cSentido.value = "NO APLICA";
// }
// }
//
// function fOnChangeCad(){
//	
// var xe = document.getElementById("iCveCadenamiento");
// var strCadc = xe.options[xe.selectedIndex].text;
// frm.cCad.value = strCadc;
// }

function validaTodo() {

	var errores = "";

	if (frm.cHechos.value == "")
		errores += "\n-El campo 'Destino' es obligatorio.";

	if (frm.cAutorizado.value == "")
		errores += "\n-El campo 'Autorizado a recoger' es obligatorio.";

	if (frm.cKmSentido.value == "")
		errores += "\n-El campo 'Cadenamientos y sentidos' es obligatorio.";

	if (fSoloLetras(frm.cHechos.value) == false)
		errores += "\n-El campo 'Hechos o Razón' solo acepta letras.";

	if (fSoloLetras(frm.cAutorizado.value) == false)
		errores += "\n-El campo 'Autorizado a recoger' solo acepta letras.";

	return errores;
}

function generaCadenaReqs(){
	for(var o=0; o<arrReqs.length; o++){
		if(arrReqs[o][0]==true)
			addReq(o);
	}
	
	if(frm.cCadReqs.value==""){
		fAlert("- Debe seleccionar al menos un requsito para que sea firmado digitalmente.");
		return true;
	}
	
	return false;
}

function addReq(idx){
	if(frm.cCadReqs.value!="")
		frm.cCadReqs.value+=",";
	frm.cCadReqs.value+=arrReqs[idx][1];
}
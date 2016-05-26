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
var lModifica = false;
var cPermisoPag;
var datosSol = [];

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgGestionOficios.js";
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

		InicioTabla("", 0, "100%", "", "", "", "1");
			ITRTD("ESTitulo", 0, "100%", "", "center");
				TextoSimple(cTitulo);
			FTDTR();
			
			ITRTD("", 0, "", "0", "center");
				InicioTabla("ETablaInfo", 0, "75%", "", "", "", 1);
					ITRTD("", 0, "", "1", "center");
					InicioTabla("", 0, "100%", "", "", "", 1);
						ITRTD("ETablaST", 5, "", "", "center");
						TextoSimple("DATOS DE SOLICITUD");
						FTDTR();
						ITR();
								TDEtiCampo(false, "EEtiqueta", 0, "Ejercicio:", "iEjercicio", "", 6, 6,
										"Ejercicio", "fMayus(this);");
								TDEtiCampo(false, "EEtiqueta", 0, "No. Solicitud:", "iNumSolicitud", "", 6,
										6, "Solicitud", "fMayus(this);");
							ITD();
								BtnImg("Buscar", "lupa", "fBuscaSol();");
							FTD();
						FTR();
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
					FinTabla();
					FTDTR();
				FinTabla();
			FTDTR();
			
			ITRTD("", 0, "", "1", "center");
				BR();
			FTDTR();
			
			ITRTD("", 0, "95%", "", "center");
			InicioTabla("ETablaInfo", 0, "95%", "", "", "", 1);
				ITRTD("ETablaST", 5, "", "", "center");
					InicioTabla("", 0, "95%", "", "", "", 1);
						ITD("ETablaST");
							TextoSimple("OFICIOS DE PNC");
						FITD()
					FinTabla();
				FTDTR();
				ITRTD("", 0, "", "", "center");
					IFrame("IListado", "100%", "170", "Listado.js", "yes", true);
				FTDTR();
			FinTabla();
		FTDTR();
			
			ITRTD("", 0, "95%", "", "center");
			BR();
			FTDTR();
			
			ITRTD("", 0, "95%", "", "center");
			InicioTabla("ETablaInfo", 0, "95%", "", "", "", 1);
			ITRTD("ETablaST", 5, "", "", "center");
				TextoSimple("OFICIOS DE LA SOLICITUD");
			FTDTR();
			ITRTD("", 0, "", "", "center");
				IFrame("IListadoA", "100%", "170", "Listado.js", "yes", true);
			FTDTR();
			FinTabla();
		FTDTR();
			
		FinTabla();
		
		Hidden("hdLlave");
		Hidden("hdSelect");
		Hidden("iCveOficioSel");
		Hidden("iCveOficina");
		Hidden("iCveDepartamento");
		Hidden("iCveGrupoUsr");
		Hidden("hdLlave");
		Hidden("hdiEjercicio");
		Hidden("hdiNumSolicitud");
		Hidden("hdLlave");
		Hidden("iCveUsuario", fGetIdUsrSesion());

	fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);
	FRMListado.fSetTitulo("Oficio,Fecha,Usuario, Acción,");
	FRMListado.fSetCampos("1,5,6,7,");
	FRMListado.fSetDespliega("texto,texto,texto,texto,");
	FRMListado.fSetAlinea("left,center,center,center,");

	FRMListadoA = fBuscaFrame("IListadoA");
	FRMListadoA.fSetControl(self);
	FRMListadoA.fSetTitulo("Oficio,Fecha,Usuario, OFICIOS,");
	FRMListadoA.fSetCampos("1,5,6,7,");
	FRMListadoA.fSetDespliega("texto,texto,texto,texto,");
	FRMListadoA.fSetAlinea("left,center,center,center,");

	var dateToday = new Date();
	frm.iEjercicio.value = dateToday.getFullYear();
	fDisabled(true, "iEjercicio,iNumSolicitud,");
	frm.iNumSolicitud.focus();
	
	fCargaOficDeptoUsr(false);
}
// LLAMADO al JSP específico para la navegación de la página

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, lValido) {
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

	if (cId == "CIDOficinaDeptoXUsr") {
		if (aRes.length > 0) {
			frm.iCveOficina.value = aRes[0][1];
			frm.iCveDepartamento.value = aRes[0][2];
		}
		fCargaGrupoUsr();
	}
	
	if (cId == "CIDGrupoXUsr") {
		if (aRes.length > 0) {
			frm.iCveGrupoUsr.value = aRes[0][0];
		}
	}

	if (cId == "buscaOficiosSolicitud" && cError == "") {
		if(aRes.length>0){
			for(var i =0; i<aRes.length;i++){
				if(aRes[i][4]>0){
					aRes[i].push("<font color=blue>VER OFICIO</font>");
				}else{
					aRes[i].push("<font color=blue>SUBIR OFICIO</font>");
				}
			}
		}
		
		FRMListado.fSetListado(aRes);
		FRMListado.fSetLlave(cLlave);
		FRMListado.fShow();
		
		fNavegaA();
	}
	
	if (cId == "buscaOficiosSolicitudPNC" && cError == "") {
		
		if(aRes.length>0){
			for(var i =0; i<aRes.length;i++){
				if(aRes[i][4]>0){
					aRes[i].push("<font color=blue>Ver documento...</font>");
				}else{
					aRes[i].push("<font color=blue>Subir documento...</font>");
				}
			}
		}
		
		FRMListadoA.fSetListado(aRes);
		FRMListadoA.fSetLlave(cLlave);
		FRMListadoA.fShow();
	}

	if (cId == "cIdSolicitud" && cError == "") {
		
		if (aRes.length > 0) {
			
			datosSol = aRes;
			
			if (aRes[0][2] != "") {
				frm.cDscSolicitante.value = aRes[0][2];
			}
			
			if (aRes[0][3] != "") {
				frm.cDscRFC.value = aRes[0][3];
			}
			
			if (aRes[0][4] != "") {
				frm.cDscTramite.value = aRes[0][4];
			}
			
			if (aRes[0][5] != "") {
				frm.cDscModalidad.value = aRes[0][5];
			}
		
			fNavega(); //busca los documentos
		}
	}
}

function fSelReg(aDato, iCol) {
	
	if(iCol==3){
		frm.iCveOficioSel.value = aDato[0]; 
		if(aDato[2]!==frm.iCveGrupoUsr.value){
			fAlert("\n No tiene permisos para operar este oficio.")
		}else{
			fAbreSubWindowSinPermisos("pgSubirOficiosADV_2016", "650", "400");
		}
	}
}

function fBuscaSol() {
	if (frm.iEjercicio.value>0&& frm.iNumSolicitud.value>0) {
		
		frm.hdBoton.value= "GetSolicitud";
		frm.hdFiltro.value = "S.iEjercicio= " + frm.iEjercicio.value
				+ " AND S.iNumSolicitud = " + frm.iNumSolicitud.value;
		
		fEngSubmite("pgTRAModSolicitud.jsp", "cIdSolicitud");
		
	} else {
		fAlert("Introduzca el Ejercicio y el Número de Solicitud válidos para hacer la búsqueda");
	}

}

function fNavega() {
	if (fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)) {
		frm.hdFiltro.value = "REGOF.IEJERCICIO = " + frm.iEjercicio.value
		+ " AND REGOF.INUMSOLICITUD = " + frm.iNumSolicitud.value;
		frm.hdBoton.value= "buscaOficiosSolicitud";
		fEngSubmite("pgGestionOficios.jsp", "buscaOficiosSolicitud");
	}
}

function fNavegaA() {
	if (fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value )) {
		
		frm.hdiEjercicio.value=frm.iEjercicio.value;
		frm.hdiNumSolicitud.value= frm.iNumSolicitud.value;
		frm.hdBoton.value= "buscaOficiosSolicitudPNC";
		fEngSubmite("pgGestionOficios.jsp", "buscaOficiosSolicitudPNC");
	}
}

function fSubirOficiosADV() {	
	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	}
	
	if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != ''
			&& frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {
		if (fSoloNumeros(frm.iEjercicio.value)
				&& fSoloNumeros(frm.iNumSolicitud.value)) {

			fAbreSubWindow(false, "pgSubiVerOficio", "no", "yes", "yes",
					"yes", "800", "600", 50, 50);

		} else {
			fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
		}
	} else {
		fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	}
}

function getEjercicio(){
	return frm.iEjercicio.value;
}

function getSolicitud(){
	return frm.iNumSolicitud.value;
}

function getOficioSel(){
	return frm.iCveOficioSel.value;
}
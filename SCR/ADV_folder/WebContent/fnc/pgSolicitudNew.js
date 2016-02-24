// MetaCD=1.0
// Title: pgSolicitud.js
// Description: JS "Catálogo" de la entidad TRARegSolicitud
// Company: Tecnología InRed S.A. de C.V.
// Author: ABarrientos
var cTitulo = "";
var frm;
var inTramites = "";
var lTienePNCNR = false;
var iEtapaVerif = 0;
var cEtapasRestringidas = "";
var aDocDig = new Array();
var esTecnico=false;
var cPermisoPag;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgSolicitudNew.js";
	cPermisoPag = fGetPermisos(cPaginaWebJS);
//	if (top.fGetTituloPagina) {
//		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
//	}
	cTitulo ="EVALUACIÓN DE DOCUMENTOS TÉCNICOS ";
	fSetWindowTitle();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJS);
	InicioTabla("", 0, "", "", "center", "", "", 0);
	if (window.parent) {
		if (window.parent.fFiltroSolicitud) {
			ITRTD("", 0, "", "", "top");
			InicioTabla("", 0, "", "", "center");
			ITRTD("ETablaST", 5, "", "", "center");
			TextoSimple("Busqueda de la Solicitud.");
			FTDTR();
			if (window.parent.fGetFiltroA) {
				ITR();
				TDEtiCampo(true, "EEtiqueta", 0, window.parent
						.fGetDescFiltroA()
						+ ":", window.parent.fGetFiltroA(), "", 10, 30,
						"Filtro de busqueda de la solicitud", "fMayus(this);",
						" onKeyPress='return fCheckForEnter(event, this, window);' ");
			}
			if (window.parent.fGetFiltroB) {
				ITD();
				TDEtiCampo(true, "EEtiqueta", 0, window.parent
						.fGetDescFiltroB()
						+ ":", window.parent.fGetFiltroA(), "", 10, 30,
						"Filtro de busqueda de la solicitud", "fMayus(this);",
						" onKeyPress='return fCheckForEnter(event, this, window);' ");
			}
			if (window.parent.fGetFiltroC) {
				FITD();
				TDEtiCampo(true, "EEtiqueta", 0, window.parent
						.fGetDescFiltroC()
						+ ":", window.parent.fGetFiltroA(), "", 10, 30,
						"Filtro de busqueda de la solicitud", "fMayus(this);",
						" onKeyPress='return fCheckForEnter(event, this, window);' ");
			}
			if (window.parent.fGetFiltroD) {
				FITD();
				TDEtiCampo(true, "EEtiqueta", 0, window.parent
						.fGetDescFiltroD()
						+ ":", window.parent.fGetFiltroA(), "", 10, 30,
						"Filtro de busqueda de la solicitud", "fMayus(this);",
						" onKeyPress='return fCheckForEnter(event, this, window);' ");
			}
			FITD();
			BtnImg("Buscar", "lupa", "window.parent.fFiltroSolicitud();");
			FinTabla();
		}
	}
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "", "", "center");
	ITRTD("", 0, "", "", "center");
	InicioTabla("ETablaInfo", 0, "", "", "", "", 1);
	ITRTD("ETablaST", 6, "", "", "center");
	TextoSimple("TRÁMITE");
	FTDTR();
	ITR();
	ITD("EEtiquetaC", 6, "", "", "center");
	//Liga("Buscar Solicitud", "fBuscaSolicitud();", "Busca una solicitud");
	FTD();
	FTR();
	ITR();
	TDEtiCampo(true, "EEtiqueta", 0, " Ejercicio:", "iEjercicio", "", 4, 4,
			" Ejercicio ", "fMayus(this);",
			" onKeyPress='return fCheckForEnter(event, this, window);' ");
	TDEtiCampo(true, "EEtiqueta", 0, " Número de Solicitud:", "iNumSolicitud",
			"", 5, 5, " Solicitud ", "fMayus(this);",
			" onKeyPress='return fCheckForEnter(event, this, window);' ");
	ITD("EEtiquetaL", 2, "", "", "left", "");
	BtnImg("Buscar", "lupa", "fValidaCampos();");
	//BtnImg("Restaurar", "restaura", "fBlanked();");
	FTD();
	FTR();
	ITR();
	TDEtiCampo(false, "EEtiqueta", 0, " Trámite:", "cTramite", "", 80, 60,
			" Trámite ", "fMayus(this);", "", "", false, "ECampo", 5);
	FITR();
	TDEtiCampo(false, "EEtiqueta", 0, " Modalidad:", "cModalidad", "", 80, 60,
			" Modalidad ", "fMayus(this);", "", "", false, "ECampo", 5);
//	FITR();
//	TDEtiCampo(false, "EEtiqueta", 0, " Aplica a:", "cDscBien", "", 80, 60,
//			" Descripción del Bien ", "fMayus(this);", "", "", false, "ECampo",
//			5);
	FTR();
	FinTabla();
	FTDTR();
	FinTabla();
	FTDTR();
	FinTabla();
	InicioTabla("", 0, "", "", "center", "", "", 0);
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "100%", "", "center");
	InicioTabla("ETablaInfo", 0, "", "", "", "", 1);
	ITRTD("ETablaST", 5, "", "", "center");
	TextoSimple("DATOS DEL PROMOVENTE");
	FTDTR();

	ITR();
	TDEtiCampo(false, "EEtiqueta", 2, " RFC:", "CRFCSol", "", 15, 15, " RFC ",
			"fMayus(this);");
	FITR();
	TDEtiCampo(false, "EEtiqueta", 2, " Correo Electrónico:", "CElectSol", "",
			30, 30, " Correo Electrónico ", "fMayus(this);");
	FITR();
	TDEtiCampo(false, "EEtiqueta", 2, " Nombre o Razón Social:", "CNomSol", "",
			110, 210, " Nombre o Razón Social ", "fMayus(this);", "", "", "",
			"EEtiquetaL", "3");
	FITR();
	TDEtiCampo(false, "EEtiqueta", 2, " Dirección:", "cDirSol", "", 110, 210,
			" Nombre o Razón Social ", "fMayus(this);", "", "", "",
			"EEtiquetaL", "3");
	FTR();

	ITRTD("ETablaST", 5, "", "", "center");
	TextoSimple("DATOS DEL REPRESENTANTE LEGAL");
	FTDTR();
	TDEtiCampo(false, "EEtiqueta", 2, " RFC:", "cRFCrl", "", 15, 15, " RFC ",
			"fMayus(this);");
	FITR();
	TDEtiCampo(false, "EEtiqueta", 2, " Correo Electrónico:", "CElectRL", "",
			30, 30, " Correo Electrónico ", "fMayus(this);");
	FITR();
	TDEtiCampo(false, "EEtiqueta", 2, " Nombre:", "cNombrerl", "", 110, 210,
			" Nombre o Razón Social ", "fMayus(this);", "", "", "",
			"EEtiquetaL", "3");
	FITR();
	TDEtiCampo(false, "EEtiqueta", 2, " Dirección:", "cDirRep", "", 110, 210,
			" dirección del Representante ", "fMayus(this);", "", "", "",
			"EEtiquetaL", "3");
	FITR();
	//TDEtiCheckBox("EEtiqueta", 3, "Cumple con los Requisitos de la Solicitud:",
	//		"lCumpleBOX", "1", true, "Cumple con los Requisitos", "", "",
	//		"EEtiquetaL", "3");
	Hidden("iCveProceso", "");
	FITR();
	//TDEtiCheckBox("EEtiqueta", 3, "Cumple con la Evaluación Técnica:",
	//		"lCumpleETBOX", "1", true, "Cumple con los Requisitos");
	//Hidden("iCveProceso", "");
	ITD("", 0, "", "", "left", "");

	FinTabla();

	ITRTD("", 0, "", "", "top");
	InicioTabla("ETablaInfo", 0, "100%", "", "center");
	ITRTD("ETablaST", 5, "", "", "center");
	TextoSimple("Controles de la Solicitud");
	FTDTR();
	ITR("ETablaInfo", 0, "", "", "center", "");
	ITD("EEtiquetaC");
	Liga("[Evaluación de documentos técnicos.]", "fVerifica();",
			"Verifica requisitos cualitativamente");
	FITD("", 0, "", "", "center", "");
	Liga("[Documentos Digitales Anexos al Trámite.]",
			"fDocsTramite()", "...");

	//Liga("[Adjuntar Documentos]", "fEjecutaDocumentos();",
	//		"Adjunta Documentos a la Solicitud");
	FTD();
	FITR();
	ITD("", 0, "", "", "center", "");
//	Liga(
//			"[Mostrar Solicitudes Relacionadas]",
//			"if(frm.iEjercicio.value!='' && frm.iNumSolicitud.value!='')fAbreSolicitudesRelacionadas();else fAlert('\n-Debe seleccionar ejercicio y solicitud para mostrar las relacionadas');",
//			"Muestra las solicitudes relacionadas y sus etapas");
	FITD("", 0, "", "", "center", "");
	//Liga(
			//"[Consulta de Solicitudes]",
			//"if(frm.iEjercicio.value!='' && frm.iNumSolicitud.value!='' && frm.cTramite.value != '')fAbreConsSolicitud();else fAlert('\n-Debe seleccionar ejercicio y solicitud validos para consultar dicha solicitud');",
			//"Muestra las solicitudes relacionadas y sus etapas");
	FTD();
	FITR();
	ITD();
	//Liga("[Documentos Digitales Anexos al Trámite por Internet con FIEL]",
	//		"fDocDig();", "...");

	FITD();
	// Liga("[Importar Campos del Trámite por Internet con
	// FIEL]","fImportaCampo();","...");
	//Liga("[Notificar al Usuario por Internet]", "fNotificar();", "...");
	FTD();
	FTR();
	FinTabla();
	Hidden("cumpleRequisitos", "1");
	Hidden("cDscBien", "");	
	Hidden("iCvePersona", "");
	Hidden("iIdBien", "");
	Hidden("iCveDomicilio", "");
	Hidden("iCveDomicilioRL", "");
	Hidden("iCveUsuario", "");
	Hidden("iCveTramite");
	Hidden("iCveModalidad");
	Hidden("hdFiltroUsrXDepto", "");
	Hidden("iCveRepLegal");
	Hidden("tsRegistro", "");
	Hidden("cCalle", "");
	Hidden("cNumExterior", "");
	Hidden("cNumInterior", "");
	Hidden("cColonia", "");
	Hidden("cCodPostal", "");
	Hidden("iCvePais", "");
	Hidden("iCveEntidadFed", "");
	Hidden("iCveMunicipio", "");
	Hidden("cDscEntidad", "");
	Hidden("cDscMunicipio", "");
	Hidden("iCveLocalidad", "");
	Hidden("iTipoPersona", "");
	Hidden("hdSelect");
	Hidden("hdLlave");
	Hidden("cUsuario");
	Hidden("iCveEtapaVerif", 0);
	Hidden("iCveEtapa");
	Hidden("iCveDepartamento");
	Hidden("iCveOficina")
	Hidden("lAnexo");
	Hidden("cObservaciones", "");
	Hidden("existePNC", false);
	Hidden("lOficinas", true);
	
	 Hidden("hdOfic","");
	    Hidden("hdDpto","");

	
	FTDTR();
	FinTabla();
	FTDTR();
	FinTabla();
	fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	fDisabled(true, "iEjercicio,iNumSolicitud,hdFiltroUsrXDepto,");
	frm.hdBoton.value = "Primero";
	frm.cUsuario.value = fGetIdUsrSesion();
	inTramites = fGetClavesTramiteFiltrar();
	fTraeFechaActual();
	frm.lOficinas.value = !fBuscaTramite(0);
	   fCargaOficDeptoUsr(false);

}
// LLAMADO al JSP específico para la navegación de la página
function fNavega() {
	var cTramites = "";
	if (inTramites != "")
		cTramites = " AND TRARegSolicitud.iCveTramite IN (" + inTramites + ") ";
					frm.hdFiltro.value = " TRARegSolicitud.iEjercicio = " + frm.iEjercicio.value + 
					" AND TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitud.value + 
					" AND TRAREGRESOLVTECXSOL.LPOSITIVA = 1 " + 
					cTramites;
	//		+ frm.hdFiltroUsrXDepto.value;
	frm.hdOrden.value = "";
	frm.hdNumReg.value = 10000;

	if (window.parent.cPaginaWebJS == "pg111020270.js")
		frm.hdBoton.value = "Certificacion";
	fEngSubmite("pgTRADatosSolicitud.jsp", "Listado");
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave,
		cEtapasRestringidas) {
	if (cError == "Guardar")
		fAlert("Existió un error en el Guardado!");
	if (cError == "Borrar")
		fAlert("Existió un error en el Borrado!");
	if (cError == "Cascada") {
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
		return;
	}
	
	if (cError == "") {
		if (cId == "DocDig" && cError == "") {
			aDocDig = aRes;
			fAbreSubWindow(false, "pgShowDocDig", "no", "no", "no", "no",
					"800", "600", 50, 50);
		}

		if (cId == "Listado") {
			if (aRes.length == 0) {
				fAlert("La solicitud no existe o no puede darle seguimiento. \nVerifique su estado en Consulta de Solicitudes.");
				fBlanked("iEjercicio,iNumSolicitud,");
				return;
			}
			frm.hdRowPag.value = iRowPag;
			aDato = fCopiaArreglo(aRes[0]);
			frm.iEjercicio.value = aDato[0];
			frm.iNumSolicitud.value = aDato[1];
			frm.iCveTramite.value = aDato[2];
			frm.cTramite.value = aDato[3];
			frm.iCveModalidad.value = aDato[4];
			frm.cModalidad.value = aDato[5];
			frm.CRFCSol.value = aDato[6];
			frm.CNomSol.value = aDato[7];
			frm.cRFCrl.value = aDato[8];
			frm.cNombrerl.value = aDato[9];
			frm.iCvePersona.value = aDato[10];
			frm.iIdBien.value = aDato[11];
			frm.cDirSol.value = aDato[12] + " #" + aDato[13];
			if (aDato[14] != "")
				frm.cDirSol.value = frm.cDirSol.value + " Int." + aDato[14];
			frm.cDirSol.value = frm.cDirSol.value + " Col. " + aDato[15] + ", "
					+ aDato[17] + ", " + aDato[16];
			
			if(frm.cRFCrl.value!=""){
				frm.cDirRep.value = aDato[18] + " #" + aDato[19];
				if (aDato[20] != "")
					frm.cDirRep.value = frm.cDirRep.value + " Int." + aDato[20];
				
				if (frm.cDirRep.value != "")
					frm.cDirRep.value = frm.cDirRep.value + " Col. " + aDato[21] + ", "
					+ aDato[23] + ", " + aDato[22];
			}
			
			if (frm.iEjercicio.value == "") {
				frm.iEjercicio.value = "";
				fBlanked();
			}
			if (frm.iNumSolicitud.value == "") {
				frm.iNumSolicitud.value = "";
				fBlanked();
			}
			frm.iCveDomicilio.value = aDato[35];
			frm.iCveDomicilioRL.value = aDato[24];
			frm.CElectSol.value = aDato[25];
			frm.CElectRL.value = aDato[26];
			frm.iCveRepLegal.value = aDato[27];
			frm.tsRegistro.value = aDato[28];
			frm.cCalle.value = aDato[18];
			frm.cNumExterior.value = aDato[19];
			frm.cNumInterior.value = aDato[20];
			frm.cColonia.value = aDato[15];
			frm.cCodPostal.value = aDato[33];
			frm.iCvePais.value = aDato[29];
			frm.iCveEntidadFed.value = aDato[30];
			frm.iCveMunicipio.value = aDato[31];
			frm.iCveLocalidad.value = aDato[32];
			frm.iTipoPersona.value = aDato[34];
			frm.cDscEntidad.value = aDato[16];
			frm.cDscMunicipio.value = aDato[17];
			frm.cDscBien.value = aDato[36];

			if (aDato[37] > 0)
				frm.existePNC.value = true;

			var cFechaRegSol = "";
			cFechaRegSol = fGetTsRegistroSoloFecha();
			if (window.parent.fGetParms)
				window.parent.fGetParms(frm.iEjercicio.value,
						frm.iNumSolicitud.value, frm.cTramite.value,
						frm.cModalidad.value, frm.CRFCSol.value,
						frm.CNomSol.value, cFechaRegSol, frm.iCvePersona.value,
						frm.iCveDomicilio.value, frm.iCveRepLegal.value,
						frm.iCveTramite.value);
			fCancelar();
			frm.hdFiltro.value = "";
			/** Eliminar ELEL */
			frm.hdFiltro.value = " TRAREGREQXTRAM.IEJERCICIO = "
					+ frm.iEjercicio.value
					+ " AND TRAREGREQXTRAM.INUMSOLICITUD = "
					+ frm.iNumSolicitud.value
					+ " AND TRAREGREQXTRAM.DTRECEPCION IS NULL";
			fEngSubmite("pgVerificaReq.jsp", "idVerificacion");
			/** * FIN ELIMINAR ELEL */
		}
	}

	if (cId == "idVerificacion" && cError == "") {
		this.cEtapasRestringidas = cEtapasRestringidas;
		//frm.lCumpleBOX.checked = aRes.length > 0 ? 0 : 1;
		fRequisitos();
	}
	if (cId == "IDUsrXOfic" && cError == "") {
		if (aRes.length == 0) {
			fAlert("No tienen oficinas y departamentos asignados, reportelo al administrador del sistema");
			return;
		}
		/*if (!fBuscaTramite(0)) {
			frm.hdFiltroUsrXDepto.value = " AND TRAREGSOLICITUD.DTENTREGA IS NULL AND (";
			for (y = 0; y < aRes.length; y++) {
				frm.hdFiltroUsrXDepto.value += " (trexmt.iCveOficina = "
						+ aRes[y][0] + " AND trexmt.iCveDepartamento = "
						+ aRes[y][1] + ") ";
				if (y < (aRes.length - 1))
					frm.hdFiltroUsrXDepto.value += " OR ";
			}
			frm.hdFiltroUsrXDepto.value += ")";
			fNavega();
		}*/
		if (!fBuscaTramite(0)) {
			frm.hdFiltroUsrXDepto.value = " AND TRAREGSOLICITUD.DTENTREGA IS NULL AND ";
			/*/for (y = 0; y < aRes.length; y++) {
				frm.hdFiltroUsrXDepto.value += " (trexmt.iCveOficina = "
						+ aRes[y][0] + " AND trexmt.iCveDepartamento = "
						+ aRes[y][1] + ") ";
				if (y < (aRes.length - 1))
					frm.hdFiltroUsrXDepto.value += " OR ";
			}
			frm.hdFiltroUsrXDepto.value += ")";*/
			fNavega();
		}
		else
			fNavega();
	}
	if (cId == "idOficeDepto" && cError == "") {
		if (aRes.length > 0) {
			frm.iCveOficina.value = aRes[0][0];
			frm.iCveDepartamento.value = aRes[0][1];
			frm.lAnexo.value = aRes[0][2];
			fBuscaPNC();
		}
	}
	if (cId == "cIdRequisitos" && cError == "") {
		//frm.lCumpleBOX.checked = aRes.length == 0 ? true : false;
		fEvaluacion();
	}
	if (cId == "cIdEvaluacion" && cError == "") {
		//frm.lCumpleETBOX.checked = aRes.length == 0 ? true : false;
		fObtenOficDepto();
	}
	if (cId == "cIdPNC" && cError == "") {
		if (aRes.length > 0)
			lTienePNCNR = true;
		else
			lTienePNCNR = false;
	}
	if (cId == "idFechaActual" && cError == "") {
		iNumEjercicio = parseInt(aRes[1][2], 10);
		if (iNumEjercicio && iNumEjercicio != NaN && iNumEjercicio > 0)
			frm.iEjercicio.value = aRes[1][2];
	}
	
	if(cId == "CIDOficinaDeptoXUsr"&&cError==""){
		   if(aRes.length > 0){
		     frm.hdOfic.value=aRes[0][1];
		     frm.hdDpto.value=aRes[0][2];
		   }
		   if(frm.hdDpto.value == "100") //ID depto TECNICO
			   esTecnico=true;
	   }
}
// Busca oficina y departamento de etapa recepción en el área
function fObtenOficDepto() {
	frm.hdLlave.value = "";
	frm.hdSelect.value = "SELECT ICVEOFICINA, ICVEDEPARTAMENTO, LANEXO "
			+ "FROM TRAREGETAPASXMODTRAM " + "WHERE iEjercicio = "
			+ frm.iEjercicio.value + " AND iNumSolicitud = "
			+ frm.iNumSolicitud.value + " AND iCveTramite = "
			+ frm.iCveTramite.value + " AND iCveModalidad = "
			+ frm.iCveModalidad.value + " ORDER BY IORDEN DESC";
	fEngSubmite("pgConsulta.jsp", "idOficeDepto");
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar() {
	fDisabled(true, "iEjercicio,iNumSolicitud,hdFiltroUsrXDepto,");
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
function fValidaCampos() {
	if (frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "") {
		if ('' + parseInt(frm.iEjercicio.value, 10) != "NaN"
				&& '' + parseInt(frm.iNumSolicitud.value, 10) != "NaN")
			fTraeUsrXDeptos();
		else {
			fAlert(" El valor para el Ejercicio y Solicitud debe ser numérico!");
			if ('' + parseInt(frm.iEjercicio.value, 10) == "NaN"
					&& '' + parseInt(frm.iNumSolicitud.value, 10) == "NaN") {
				frm.iEjercicio.value = "";
				frm.iNumSolicitud.value = "";
				frm.iEjercicio.focus();
			} else {
				if ('' + parseInt(frm.iEjercicio.value, 10) == "NaN") {
					frm.iEjercicio.value = "";
					frm.iEjercicio.focus();
				}
				if ('' + parseInt(frm.iNumSolicitud.value, 10) == "NaN") {
					frm.iNumSolicitud.value = "";
					frm.iNumSolicitud.focus();
				}
			}
		}
	} else {
		fAlert(" El valor para el Ejercicio y Número de Solicitud no puede ser núlo.\n por favor ingrese ambos datos!");
		if (frm.iEjercicio.value == "")
			frm.iEjercicio.focus();
		else
			frm.iNumSolicitud.focus();
	}
}
function fVerifica() {
	
	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	}
	
	if(lTienePNCNR==false){
		
		   if (frm.cTramite.value != "") {
			   fAbreSubWindowPermisos("pgVerifSolNew", "800", "460");
		      fTraeUsrXDeptos();
		   }
		   else
		     fAlert (" \nDebe seleccionar una solicitud.");
		}
		else{
			fAlert("\nNo es posible realizar la evaluación, la solcitud tiene un PNC que no ha sido cerrado.");
		}	
}
function fBuscaSolicitud() {
	fAbreBuscaSolicitud();
	return;
}
function fGetiEjercicio() {
	return frm.iEjercicio.value;
}
function fGetiNumSolicitud() {
	return frm.iNumSolicitud.value;
}
function fGetiCvePersona() {
	return frm.iCvePersona.value;
}
function fGetiCveDomicilio() {
	return frm.iCveDomicilio.value;
}
function fGetSolicitante() {
	return frm.CNomSol.value;
}
function fGetCveRepLegal() {
	return frm.iCveRepLegal.value;
}
function fGetDomRepLegal() {
	return frm.iCveDomicilioRL.value;
}
function fGetRFCSolicitante() {
	return frm.CRFCSol.value;
}
function fGetDirSolicitante() {
	return frm.cDirSol.value;
}
function fGetiCveTramite() {
	return frm.iCveTramite.value;
}
function fGetcDscTramite() {
	return frm.cTramite.value;
}
function fGetiCveModalidad() {
	return frm.iCveModalidad.value;
}
function fGetDscModalidad() {
	return frm.cModalidad.value;
}
function fGetIdBien() {
	return frm.iIdBien.value;
}
function fGetRepresentante() {
	return frm.cNombrerl.value;
}
function fGetRFCRepresentante() {
	return frm.CRFCSol.value;
}
function fGetDirRepresentante() {
	return frm.cDirRep.value;
}
function fGetCalle() {
	return frm.cCalle.value;
}
function fGetNumExterior() {
	return frm.cNumExterior.value;
}
function fGetNumInterior() {
	return frm.cNumInterior.value;
}
function fGetColonia() {
	return frm.cColonia.value;
}
function fGetCodPostal() {
	return frm.cCodPostal.value;
}
function fGetCvePais() {
	return frm.iCvePais.value;
}
function fGetCveEntidadFed() {
	return frm.iCveEntidadFed.value;
}
function fGetCveMunicipio() {
	return frm.iCveMunicipio.value;
}
function fGetCveLocalidad() {
	return frm.iCveLocalidad.value;
}
function fGetTsRegistro() {
	return frm.tsRegistro.value;
}
function fGetCumpleEvaluacion() {
	//return frm.lCumpleETBOX.checked;
}
function fGetTsRegistroSoloFecha() {
	var dtTsRegistro;
	var mes = "";
	if (frm.tsRegistro.value == "")
		dtTsRegistro = "";
	else {
		mes = nombreMes(frm.tsRegistro.value.substring(3, 5));
		dtTsRegistro = frm.tsRegistro.value.substring(0, 2) + " de " + mes
				+ " de " + frm.tsRegistro.value.substring(6, 10);
	}
	return dtTsRegistro;
}
function fGetITipoPersona() {
	return frm.iTipoPersona.value;
}
function fGetExistePNC() {
	return frm.existePNC.value;
}
function fGetCDscEntidad() {
	return frm.cDscEntidad.value
}
function fGetCDscMunicipio() {
	return frm.cDscMunicipio.value
}
function fGetCDscBien() {
	return frm.cDscBien.value
}
function fGetTramites() {
	return inTramites;
}
function fSetSolicitud(iEjercicio, iNumSolicitud) {
	frm.iEjercicio.value = iEjercicio;
	frm.iNumSolicitud.value = iNumSolicitud;
	fTraeUsrXDeptos();
}
function fTraeUsrXDeptos() {
	if (top.fGetUsrID())
		frm.iCveUsuario.value = top.fGetUsrID();
	frm.hdBoton.value = "Deptos1";
	frm.hdNumReg.value = 1000;
	frm.hdFiltro.value = " GRLUSUARIOXOFIC.ICVEUSUARIO = "
			+ frm.iCveUsuario.value;
	fEngSubmite("pgGRLUsuarioXOfice.jsp", "IDUsrXOfic");
}

function nombreMes(numeroMes) {
	var aMes = new Array();
	aMes[1] = "Enero";
	aMes[2] = "Febrero";
	aMes[3] = "Marzo";
	aMes[4] = "Abril";
	aMes[5] = "Mayo";
	aMes[6] = "Junio";
	aMes[7] = "Julio";
	aMes[8] = "Agosto";
	aMes[9] = "Septiembre";
	aMes[10] = "Octubre";
	aMes[11] = "Noviembre";
	aMes[12] = "Diciembre";

	if (numeroMes < 1 && numeroMes > 12) {
		return ("No Valido");
	}
	return aMes[parseInt(numeroMes, 10)];
}
function fRequisitos() {
	frm.hdSelect.value = "SELECT RS.ICVETRAMITE,RS.ICVEMODALIDAD,RRT.LVALIDO FROM TRAREGSOLICITUD RS "
			+ "JOIN TRARegReqxTram RRT ON RS.iEjercicio = RRT.iEjercicio AND RS.iNumSolicitud = RRT.iNumSolicitud "
			+ "and RS.ICVETRAMITE = RRT.ICVETRAMITE AND RS.ICVEMODALIDAD = RRT.ICVEMODALIDAD "
			+ "and RRT.DTRECEPCION IS NOT NULL "
			+ "Where LVALIDO = 0 and RS.iEjercicio = "
			+ frm.iEjercicio.value
			+ " AND RS.iNumSolicitud = " + frm.iNumSolicitud.value;
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdEvaluacion");
}

function fBuscaPNC() {
	frm.hdSelect.value = "SELECT PNC.LRESUELTO, PNC.DTRESOLUCION FROM TRAREGPNCETAPA PNCE "
			+ "JOIN GRLREGISTROPNC PNC ON PNC.IEJERCICIO = PNCE.IEJERCICIOPNC AND "
			+ " PNC.ICONSECUTIVOPNC = PNCE.ICONSECUTIVOPNC "
			+ "Where PNCE.iEjercicio = "
			+ frm.iEjercicio.value
			+ " AND PNCE.iNumSolicitud = "
			+ frm.iNumSolicitud.value
			+ " AND PNC.LRESUELTO = 0";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdPNC");
}

function fEvaluacion() {
	frm.hdSelect.value = "SELECT TS.ICVETRAMITE, TS.ICVEMODALIDAD, RRT.DTRECEPCION "
			+ "FROM TRAREGSOLICITUD TS "
			+ "JOIN TRARegReqxTram RRT ON TS.iEjercicio = RRT.iEjercicio AND TS.iNumSolicitud = RRT.iNumSolicitud "
			+ "and TS.ICVETRAMITE = RRT.ICVETRAMITE AND TS.ICVEMODALIDAD = RRT.ICVEMODALIDAD "
			+ "Where RRT.DTRECEPCION is null and TS.iEjercicio = "
			+ frm.iEjercicio.value
			+ " AND TS.iNumSolicitud = "
			+ frm.iNumSolicitud.value;
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdRequisitos");
}
function fSetEvaluacion(wVerifica, iVerif, iOfic, iDepto, lAnex) {
	frm.iCveEtapaVerif.value = iVerif;
	frm.iCveOficina.value = iOfic;
	frm.iCveDepartamento.value = iDepto;
	frm.lAnexo.value = lAnex;
	wVerifica.top.close();
	fRequisitos();
}
function fEsSolicitud() {
	return true;
}
function fTienePNC() {
	return lTienePNCNR;
}
function fEnterLocal(theObject, theEvent, theWindow) {
	objName = theObject.name;
	if (objName == 'iNumSolicitud' || objName == 'iEjercicio') {
		fMayus(theObject);
		fValidaCampos();
	}
}
function fEnviaDatosMuestraSolRel(objWindow) {
	if (objWindow && objWindow.fAsignaEjercicioSolicitud)
		objWindow.fAsignaEjercicioSolicitud(frm.iEjercicio.value,
				frm.iNumSolicitud.value);
}
function fBuscaTramite(tramite) {
	var aTramites = new Array;
	aTramites = fGetClavesTramiteFiltrar().split(",");
	for (i = 0; i < aTramites.length; i++) {
		if (parseInt(aTramites[i], 10) == parseInt(tramite, 10))
			return true;
	}
	return false;
}

function fEjecutaDocumentos() {
	// if(frm.cSintesis.disabled == false){
	if (frm.iNumSolicitud.value != "" && frm.cTramite.value != "")
		fRegDocumentos();
	else
		fAlert("\nPara adjuntar documentos seleccione un registro.");
	// }
}
function fGetParametros() {
	var aParametros = new Array();
	aParametros[0] = ""; // deprecated.
	aParametros[1] = "Documentos por Solicitud"; // Descripcion del Proceso
	aParametros[2] = ""; // deprecated.
	aParametros[3] = ""; // deprecated.
	aParametros[4] = "GRLDocumentoXSol"; // Nombre de la Tabla.
	aParametros[5] = ""; // deprecated.
	aParametros[6] = "2"; // No. de Modulo.
	aParametros[7] = "Escritura"; // Modo de la Pagina Escritura o Consulta.
	return aParametros;
}
function fGetArregloCampos() {
	var aCampos = new Array();
	aCampos[0] = "iNumSolicitud";
	return aCampos;
}
function fGetArregloDatos() {
	var aDatos = new Array();
	aDatos[0] = frm.iNumSolicitud.value;
	return aDatos;
}
function fAbreConsSolicitud() {
	fAbreSubWindowPermisos("pg111040030", "1200", "585");
}

function fDocDig() {
	// frm.iCveTramiteDocDig.value = (parseInt(frm.ICVETRAMITE.value,10) >
	// 0?frm.ICVETRAMITE.value:parent.fGetiCveTramiteINT());
	if (parseInt(frm.iNumSolicitud.value, 10) > 0) {
		frm.hdNumReg.value = "100000";
		fEngSubmite("pgINTDocDig.jsp", "DocDig");
	} else
		fAlert("\n - No tiene asignado un número de trámite.");
}
function fGetDocDig() {
	return aDocDig;
}

function fImportaCampo() {
	if (window.parent) {
		if (window.parent.fGetCampos) {
			if (frm.iEjercicio.value > 0 && frm.iNumSolicitud.value > 0) {
				window.parent.fGetCampos(frm.iEjercicio.value,
						frm.iNumSolicitud.value);
			}
		}
	}
}
function fNotificar() {
	if (frm.cTramite.value != "") {
		fAbreSubWindowPermisos("pgNotificaciones", "1200", "585");
	}
}

function getEsTecnico(){
	return esTecnico;
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

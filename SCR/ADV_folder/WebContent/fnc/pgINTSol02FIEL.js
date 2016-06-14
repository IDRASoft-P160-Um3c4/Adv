// MetaCD=1.0 f
var tBdy;
var aSelect;
var aAT;
var iSel;
var iAT;
var fScripts = "";
var iCont1 = 0;
var guardado = 0;
var iAdjuntar = 0;
var aListado = "";
var aEtiqueta = "";
var aValor = "";
var lValores = false;
var lGuardar = false;
var cCADOriginal = "";
var cCADFirmada = "";
var cNOMCOM = "";
var aObj = "";
var iOrdHF = 0;
var lFin = 0;
var aIngresos;
var tBarraWizard;
var existe=false;
var RFCx="";
var msgErr="";

var existeInput=false;
var arrReqFiles= new Array();

function fBefLoad(cMsgError) { // Carga información antes de que la página sea
						// mostrada.
	if(cMsgError!=undefined)
		msgErr = cMsgError;
		
	cPaginaWebJS = "pgINTSol02FIEL.js";
	cTitulo = "SOLICITUD DE TRÁMITES POR INTERNET CON F.I.E.L. DE LA D.G.D.C.";
}
function fDefPag() {
	guardado = 0;
	cGPD += '<SCRIPT LANGUAGE="JavaScript" SRC="http://servidorimagenes.sct.gob.mx/siiaf/vehmarcacat.js"></SCRIPT>';
	cGPD += '<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'
			+ '<form name="form1" enctype="multipart/form-data" method="post" action="UploadINTDocs">';
	fInicioPagina("E4E4E4");
	Estilo(
			"A:Link",
			"COLOR:BLACK;font-family:Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
	Estilo(
			"A:Visited",
			"COLOR:BLACK;font-family: Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
	Estilo(
			".ELIGA",
			"COLOR:BLACK;font-family: Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
	Estilo(".ESTitulo1",
			"COLOR:BLACK;font-family: Verdana;font-size:10pt;font-weight:bold;");
	Estilo(
			".EFirma",
			"COLOR:BLACK;font-family:Lucida Console;font-size:10pt;font-weight:normal;text-align:left;");

	InicioTabla("", 0, "100%", "", "", "", "1");// InicioTabla("",0,"100%","100%","center","",".1",".1");
	ITRTD("EEtiquetaC", 7, "", "", "center");// ITRTD("ESTitulo",2,"","1","","center");
	TextoSimple(cTitulo);
	FTDTR();
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "100%", "100%", "center", "", ".1", ".1");
	ITRTD("ESTitulo1", 0, "100%", "100%", "center", "top");
	InicioTabla("", 0, "100%", "1", "center");
	ITRTD("EEtiquetaC", 0, "100%", "25", "center", "top");
	TextoSimple("TRÁMITE NÚMERO <span id='_icvetramite'></span>");
	FTDTR();
	ITRTD("", 0, "", "", "center");
	DinTabla("BarraWizard", "", 0, "", "90%", "center", "top", "", ".1", ".1");
	FTDTR();
	ITRTD("", 0, "", "", "center");
	DinTabla("TDBdy", "", 0, "90%", "", "center", "", "", ".1", ".1");
	FTDTR();

	ITRTD("EEtiquetaC", 0, "90%", "", "center", "top");
	// IFrame("IADJUNTAR","0px","0px","pgINTSolAdjuntos.js");
	// DinTabla("TDADJUNTAR","",0,"90%","","center","middle","","","");
	FTDTR();

	ITRTD("EEtiquetaC", 0, "1%", "", "center", "top");
	TextoSimple("Declaro bajo protesta de decir la verdad que la documentación presentada para acreditar el cumplimiento de todos y cada uno de los requisitos son auténticos, dejando a salvo la facultad de la Secretaría de Comunicaciones y Transportes para constatar lo manifestado, aceptando que de comprobarse lo contrario dará motivo a la revocación del permiso que en su caso se genere");
	FTDTR();
	
	ITRTD("EEtiquetaC", 0, "1%", "", "center", "top");
	TextoSimple("NOTA: A FIN DE CONTINUAR CON EL TRÁMITE, DEBERÁ ACUDIR AL CENTRO SCT CUYA JURISDICCIÓN CORRESPONDA A LA UBICACIÓN DEL APROVECHAMIENTO SOLICITADO");
	FTDTR();
	
	ITRTD();
		InicioTabla("", 0, "90%", "", "center", "", 1);
		FITD("EEtiquetaC", 0, "33%", "", "center", "top");
		Etiqueta("", "", "<BR>FIRMA DIGITAL DEL REPRESENTANTE LEGAL");
		FTD();
		FTR();
	//
		ITRTD("", 1, "", "", "center");
			DinTabla("TDFIEL", "", 0, "5px", "5px", "center", "middle", "", "", "");
		FTDTR();
	//
	FinTabla();
	FTDTR();
	
	ITRTD("", "", "", "40", "center");
	IFrame("IPanel", "515", "34", "Paneles.js");
//    Liga("Simular firma de documentos.","simulaFirma();");
	FTDTR();
	ITRTD("", "", "", "40", "center");
    Liga("Salir de registro.","cerrarVentana();");
	FTDTR();
	FinTabla();
	FTDTR();
	FinTabla();
	FTDTR();
	FinTabla();
	Hidden("ICVETRAMITE", "");
	Hidden("ICONSECUTIVO", "");
	Hidden("ICVETIPOTRAMITE", "");
	Hidden("LFINALIZADO", "0");
	Hidden("cCVE", "");
	Hidden("LFGA", "0");
	Hidden("cDSC", "");
	Hidden("cTABLA", "");
	Hidden("cOBJ", "");
	Hidden("cCamSol", "");
	Hidden("iGRLDepto", "");
	Hidden("cVD", "");
	Hidden("iCveSol", "");
	Hidden("ICVETIPOPERMISIONATRAMITE"); // es el ICVETIPOPERMISIONA del
											// trámite que se está registrando,
	// para saber los tipos de filtros que se apliquen a algunos catalogos
	// configurables.
	Hidden("CTIPOPERMISIONARIO");
	Hidden("C2012");
	Hidden("iNumCita")
	Hidden("iCveTramite");
	Hidden("iCveModalidad");
	Hidden("iEjercicio");
	Hidden("iNumSolicitud");
	Hidden("cRequisitos");
	Hidden("cRFCRep1", "");
	Hidden("cCveCertificado");
	Hidden("cFirmante");
	Hidden("hdSelect");
	Hidden("hdLlave");
	Hidden("hdValorX");
	
	///adv
	
	Hidden("hdcRFC_RepL");
	Hidden("hdcNomRaz_RepL");
	Hidden("hdcApPat_RepL");
	Hidden("hdcApMat_RepL");
	Hidden("hdcMail_RepL");
	Hidden("hdcTel_RepL");
	Hidden("hdcCalle_RepL");
	Hidden("hdcNumExt_RepL");
	Hidden("hdcNumInt_RepL");
	Hidden("hdcCol_RepL");
	Hidden("hdcCP_RepL");
	Hidden("hdcNomEnt_RepL");
	Hidden("hdcNomMun_RepL");
	Hidden("hdcRFC_Sol");
	Hidden("hdcNomRaz_Sol");
	Hidden("hdcApPat_Sol");
	Hidden("hdcApMat_Sol");
	Hidden("hdcMail_Sol");
	Hidden("hdcTel_Sol");
	Hidden("hdcCalle_Sol");
	Hidden("hdcNumExt_Sol");
	Hidden("hdcNumInt_Sol");
	Hidden("hdcCol_Sol");
	Hidden("hdcCP_Sol");
	Hidden("hdcNomEnt_Sol");
	Hidden("hdcNomMun_Sol");

	
	///adv
	fFinPagina();
}

function fOnLoad() { // Carga información al mostrar la página.
	frm = document.forms[0];
	theTable = (document.all) ? document.all.TDBdy : document
			.getElementById("TDBdy");
	tBdy = theTable.tBodies[0];
	theTableFIEL = (document.all) ? document.all.TDFIEL : document
			.getElementById("TDFIEL");
	tBdyFIEL = theTableFIEL.tBodies[0];
	theTableADJUNTA = (document.all) ? document.all.TDADJUNTAR : document
			.getElementById("TDADJUNTAR");
	tBdyADJUNTA = theTableFIEL.tBodies[0];

	tBarraWizard = document.getElementById("BarraWizard");

	fDelTBdy();
	fDelTBdyFIEL();
	// fGenADJUNTA();
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	guardado = 0;
	frm.LFGA.value = "0";
	fEnProceso(false);
	fNewTram();
}

/*******************************************************************************
 * se modicia las funciones de adjuntar archivos por medio de esta funcion
 * 
 * @return
 */
/*
 * function fGenADJUNTA(){
 * 
 * fDelTBdyADJUNTA(); newRow = theTableADJUNTA.insertRow(0); newCell =
 * newRow.insertCell(0); newCell.className = "EEtiquetaC"; newCell.innerHTML =
 * IFrame("IADJUNTAR","0px","0px","pgINTSolAdjuntos.js"); }
 */

function setValues(nodo) {
	frm.iNumCita.value = nodo.ICONSECUTIVO;
	frm.iCveTramite.value = nodo.ICVETRAMITE;
	frm.iCveModalidad.value = nodo.ICVEMODALIDAD;
	// frm.iNumCita.value = iNumCita;
	// frm.iCveTramite.value = icveTramite;
	// frm.iCveModalidad.value = iCveModalidad;
	frm.LFGA.value = "0";
	FRMTramites = fBuscaFrame("INT1");
	/*
	 * fGO("_icvetramite").innerHTML = FRMTramites.fGetEjercicio() + "/" +
	 * FRMTramites.fGetSolicitud() + "<BR><BR> Tramite:
	 * "+FRMTramites.getCTIPOPERMISIONARIO() +"<BR>Modalidad:
	 * "+FRMTramites.getCDSCTIPOTRAMITE()+ "<BR><BR> Los documentos digitales
	 * que se anexen deberán encontrarse digitalizados bajo formato PDF,<BR>y
	 * no deberán de exceder de 2 MBytes por archivo." + "<BR><BR>Deberá
	 * adjuntar los documentos que se indican como obligatorios (*), en caso de
	 * no adjuntar los<BR>documentos requeridos será sujeto a una notificación
	 * de prevención o rechazo.";
	 */
}

function fNewTram() {
	FRMTramites = fBuscaFrame("INT1");
	
	frm.iEjercicio.value = FRMTramites.fGetEjercicio();
	frm.iNumSolicitud.value = FRMTramites.fGetSolicitud();
	
	if (FRMTramites.fGetEjercicio() > 0) {
		fGO("_icvetramite").innerHTML = FRMTramites.fGetEjercicio()
				+ "/"
				+ FRMTramites.fGetSolicitud()
				+ "<BR><BR> Trámite: "
				+ FRMTramites.getCTIPOPERMISIONARIO()
				+ "<BR>Modalidad: "
				+ FRMTramites.getCDSCTIPOTRAMITE()
				+ "<BR><BR> Los documentos digitales que se anexen deberán encontrarse digitalizados bajo formato PDF,<BR>y no deberán de exceder de 8 MB por archivo y 50 MB en conjunto."
				+ "<BR><BR>Deberá adjuntar los documentos que se indican como obligatorios (*), en caso de no adjuntar los<BR>documentos requeridos será sujeto a una notificación de prevención o rechazo.";
		frm.iNumCita.value = FRMTramites.frm.iNumCita.value;
		frm.iCveTramite.value = FRMTramites.frm.iCveTramiteTmp.value;
		frm.iCveModalidad.value = FRMTramites.frm.iCveModalidadTmp.value;
		frm.cRFCRep1.value = window.parent.fGetRFCRep();
		if (FRMTramites.frm.iCveTramiteTmp.value > 0
				&& FRMTramites.frm.iCveModalidadTmp.value > 0) {
			frm.hdFiltro.value = "INTTRAMXCAMPO.ICVETRAMITE = "
					+ frm.iCveTramite.value
					+ " and INTTRAMXCAMPO.ICVEMODALIDAD = "
					+ frm.iCveModalidad.value;
			frm.hdOrden.value = "ICVEREQUISITO,IORDEN";
			frm.hdNumReg.value = "10000";
			fEngSubmite("pgINTShow.jsp", "IDShow");
		}
	}
}

function fResultado(aRes, cId, cError, cNavStatus, cSol, aRes2, cOrigen,
		cFirma, cFirmante) {
	
	if(msgErr!=""){
		fAlert(msgErr);
		msgErr="";
	}
	
	if (cError != "")
		fAlert("Error\n" + cError);
	
	
	if (cId == "cIdDatos" && cError == "") {
		if(aRes.length>0){
			var reg = aRes[0];
		
			frm.cRFC_RepL.value = reg[0];
			frm.cNomRaz_RepL.value = reg[1];
			frm.cApPat_RepL.value = reg[2];
			frm.cApMat_RepL.value = reg[3];
			frm.cTel_RepL.value = reg[4];
			frm.cCalle_RepL.value = reg[5];
			frm.cNumExt_RepL.value = reg[6];
			frm.cNumInt_RepL.value = reg[7];
			frm.cCol_RepL.value = reg[8];
			frm.cCP_RepL.value = reg[9];
			frm.cNomEnt_RepL.value = reg[10];
			frm.cNomMun_RepL.value = reg[11];
			frm.cRFC_Sol.value = reg[12];
			frm.cNomRaz_Sol.value = reg[13];
			frm.cApPat_Sol.value = reg[14];
			frm.cApMat_Sol.value = reg[15];
			frm.cTel_Sol.value = reg[16];
			frm.cCalle_Sol.value = reg[17];
			frm.cNumExt_Sol.value = reg[18];
			frm.cNumInt_Sol.value = reg[19];
			frm.cCol_Sol.value = reg[20];
			frm.cCP_Sol.value = reg[21];
			frm.cNomEnt_Sol.value = reg[22];
			frm.cNomMun_Sol.value = reg[23];
			frm.cMail_RepL.value= reg[24];
			frm.cMail_Sol.value = reg[25];
			
			
			bloquearCampos();
		}
	}
	
	
	if (cId == "IDShow" && cError == "") {
		
		arrReqFiles = fCopiaArreglo(aRes);
		
		for(var o=0; o<arrReqFiles.length;o++){
			arrReqFiles[o][0]=true;	
		}
		
		if (aRes.length > 0) {
			if (aRes2.length > 0)
				guardado = 1;
			else
				guardado = 0;
			aObj = fCopiaArreglo(aRes);
			
			compruebaSol();
			fShowDatos();			
			getRFCSolicitante();
			RFCx=frm.cRFC_RepL.value;
			//fGenSol(aRes, cSol, aRes2, cOrigen, cFirma);
			fGenSol(aRes,cSol,aRes2,cOrigen,cFirma,cFirmante.replace(/~/g,'<BR>'));
			if (frm.cRFCRep) {
				frm.cRFCRep.value = window.parent.fGetRFCRep();
				frm.cRFCRep.disabled = true;
			}
			if (frm.cRFC) {
				frm.cRFC.value = window.parent.fGetRFCRep();
			}

			if (lGuardar == true) {
				lGuardar = false;
				fAbreSubWindow(true, 'IntReciboINT', 'yes', 'yes', 'yes',
						'yes', '976', '800', 50, 50);
			}
			
			frm.cRFC_RepL.disabled=true;

			if(existe==false){
				fLoadEntidades();
			}else{
				llenaCampos();
			}
					
		
		} else {
			fAlert("\n - La solicitud o el trámite no fueron encontrados.");
			fCancelar();
		}
	}
	if (cId.substring(0, 4) == "IDCS" && cError == "") {
		for (sdf = 0; sdf < frm.elements.length; sdf++) {
			obj = frm.elements[sdf];
			if (obj.name == cNavStatus) {
				if (obj.name == "ICVEPAIS1" || obj.name == "ICVEPAIS2") {
					fFillSelect(obj, aRes, false, 1);
				} else {
					fFillSelect(obj, aRes, false, cSol);
				}
			}
		}
	}
	
	
	if (cId == "cIdEntidad" && cError == "") {
		fFillSelect(frm.iCveEntidadFedA, aRes, false, frm.iCveEntidadFedA.value, 0, 1);
		fFillSelect(frm.iCveEntidadFedB, aRes, false, frm.iCveEntidadFedB.value, 0, 1);
	}
	
	if(cId == "cIdMunA" && cError == "") {
		fFillSelect(frm.iCveMunicipioA, aRes, false, frm.iCveMunicipioA.value, 0, 1);
	}
	
	if(cId == "cIdMunB" && cError == "") {
		fFillSelect(frm.iCveMunicipioB, aRes, false, frm.iCveMunicipioB.value, 0, 1);
	}
	 
}

function fDelTBdy() {
	for (i = 0; tBdy.rows.length; i++) {
		tBdy.deleteRow(0);
	}
}
function fDelTBdyFIEL() {
	for (i = 0; tBdyFIEL.rows.length; i++) {
		tBdyFIEL.deleteRow(0);
	}
}
function fDelTBdyADJUNTA() {
	for (i = 0; tBdyADJUNTA.rows.length; i++) {
		tBdyADJUNTA.deleteRow(0);
	}
}
/**
 * @param _cFiltro
 *            es el filtro de la consulta prefabricado con el cCVE
 */
function fSetOnchange(cCampos, cCampoFiltro, _cObj, _vd, _cFiltro) {
	if (cCampos != "" && cCampos != "NOLLENAR") {
		aChange = cCampos.split(",");

		frm.cTABLA.value = aChange[0];
		frm.cCVE.value = aChange[1];
		frm.cDSC.value = aChange[2];
		frm.cOBJ.value = aChange[3];
		frm.cVD.value = _vd;
		frm.hdNumReg.value = 10000;
		frm.hdFiltro.value = cCampoFiltro + " = "
				+ eval("frm." + _cObj + ".value");
		frm.hdOrden.value = frm.cDSC.value;
		fEngSubmite("pgINTCatShow.jsp", "IDCS" + _cObj);
	}
}

function validaArch(){
	
	msgErr="";
	var arrayFilesNames = fGetFilesValues(frm);
	
	var existeError = false;
	
	if(validaArchVacios(arrayFilesNames)){
		existeError = true;
	}
	
	if(validaDocExt(arrayFilesNames)){
		existeError = true;
	}
	
	if(msgErr!="")
		fAlert(msgErr);
	
	return existeError;
	
}

function validaArchVacios(arrayDocsName) {
	
	for ( var ab = 0; ab < arrayDocsName.length; ab++) {
		
		var nomDoc = arrayDocsName[ab][0];
		
		if (nomDoc == "") {
			msgErr += "\n- Debe subir todos los documentos para completar la operación.";
			return true;
		}
	}
	return false;

}

function validaDocExt(arrayDocsName) {
	for ( var ab = 0; ab < arrayDocsName.length; ab++) {

		var nomDoc = arrayDocsName[ab][0];

		if (nomDoc != "") {

			var arrNom = nomDoc.split('.');
			var tam = arrNom.length;
			var extDoc = arrNom[tam - 1];

			if (extDoc.toUpperCase() != "PDF") {
				msgErr += "\n- Existen documentos que no cumplen con el formato PDF. Favor de verificarlos.";
				return true;
			}

		}
	}
	return false;
}

function fGenSol(aRes, cSol, aRes2, cOrigen, cFirma, cFirmante){
	fDelTBdy();
	fScripts = "";
	if (aRes.length > 0) {
		aAT = new Array();
		iAT = 0;
		aSelect = new Array();
		iSel = 0;
		iCell = 0;
		iHdrAnt = 0;
		fCallback = "";
		cTabla = InicioTabla("ETablaInfo", 0, "100%", "", "", "", ".1", ".1");
		cIntroTabla = '';
		cTitTabla = '';
		lValores = false;

		if (aRes2.length > 0)
			lFin = 1;
		else
			lFin = 0;

		for (var i = 0; i < aRes.length; i++) {
						
			if (aRes[i][10] == 0) {
				if (cIntroTabla == '')
					cTabla += ITR() + ITD("EEtiqueta")
							+ fSetField(aRes[i], true, aRes2) + FTR();
				else {
					cTabla += ITRTD("", 20, "", "", "center")
							+ InicioTabla("ETablaInfo", 0) + ITR() + cTitTabla
							+ FTR() + ITR() + cIntroTabla + FTR() + FinTabla()
							+ FTDTR();
					cIntroTabla = '';
					cTitTabla = '';
				}
			} else {
				cCampoTit = '';
				cIntroTabla += ITD("EEtiqueta") + TextoSimple(aRes[i][14])
						+ fSetField(aRes[i], false, aRes2);
				cTitTabla += ITD("ESTitulo") + aRes[i][2] + FTD();
				if (aRes[i][4] == 5)
					cTitTabla = '';
			}
			iHdrAnt = aRes[i][10];
		}
		
		if (cIntroTabla != '')
			cTabla += ITRTD("", 20, "", "", "center")
					+ InicioTabla("ETablaInfo", 0) + ITR() + cTitTabla + FTR()
					+ ITR() + cIntroTabla + FTR() + FinTabla() + FTDTR();
		iRR = 0;
		newRow = tBdy.insertRow(iRR++);
		newCell = newRow.insertCell(0);
		newCell.innerHTML = Hidden("ICVETRAMITEDIN", "" + frm.ICVETRAMITE.value)
				+ Hidden("ICONSECUTIVODIN", "" + frm.ICONSECUTIVO.value)
				+ Hidden("cCamSolDIN", "");
		if (cTabla != '') {
			if (cSol != '') {
				newRow = tBdy.insertRow(iRR++);
				newCell = newRow.insertCell(0);
				newCell.className = "ESTitulo";
				newCell.innerHTML = "SOLICITUD: " + cSol;
				frm.iCveSol.value = cSol;
			}
			newRow = tBdy.insertRow(iRR++);
			newCell = newRow.insertCell(0);
			newCell.innerHTML = cTabla + FinTabla();
		}

		/***********************************************************************
		 * Campos de Ingresos
		 */
		FRMTramites = fBuscaFrame("INT1");
		aIngresos = FRMTramites.fGetIngresos();
		if (aIngresos.length > 0) {
			cTabla = InicioTabla("ETablaInfo", "95%");
			cTabla += ITRTD("ESTitulo", 10)
					+ "Conceptos de Ingresos ligados a este trámite." + FTDTR();
			cTabla += ITRTD("ESTitulo") + "Ejercicio" + FITD("ESTitulo")
					+ "Inicio Vigencia" + FITD("ESTitulo") + "Fin Vigencia"
					+ FITD("ESTitulo") + "Sin Ajuste" + FITD("ESTitulo")
					+ "Con Ajuste" + FITD("ESTitulo") + "Trámite o Concepto"
					+ FTDTR();
			for (h = 0; h < aIngresos.length; h++) {
				cTabla += ITRTD("EEtiquetaC") + aIngresos[h][0]
						+ FITD("EEtiquetaC") + aIngresos[h][2]
						+ FITD("EEtiquetaC") + aIngresos[h][3]
						+ FITD("EEtiquetaC") + aIngresos[h][6]
						+ FITD("EEtiquetaC") + aIngresos[h][7]
						+ FITD("EEtiquetaL") + aIngresos[h][1] + " - "
						+ aIngresos[h][8] + "..." + FTDTR();
			}
			cTabla += ITRTD("EEtiquetaC", 10);

			cTabla += FinTabla();
			newRow = tBdy.insertRow(iRR++);
			newCell = newRow.insertCell(0);
			newCell.colSpan = 20;
			newCell.className = "EEtiquetaC";
			newCell.innerHTML = cTabla;
		}
		if (cOrigen != "null") {
			newRow = tBdyFIEL.insertRow(0);
			newCell = newRow.insertCell(0);
			newRow.className = "EEtiquetaL";
			newRow.className = "EFirma";
			// newCell.innerHTML = '<BR><p align="center">CADENA DE
			// FIRMADO:</p>' + cFirma;
			newCell.innerHTML = '<BR><p align="center" class="EEtiquetaC">CADENA ORIGINAL:</p>'
					+ '<div id="Layer1" style="width:600px; height:150px; overflow: scroll;">'+
					cOrigen +"</div>";

			cFirma = cFirma.replace(/~/g, '<BR>');

			// newCell.innerHTML = '<BR><p align="center">CADENA ORIGINAL:</p>'
			// + cOrigen ;
			

			newRow = tBdyFIEL.insertRow(1);
			newCell = newRow.insertCell(0);
			newRow.className = "EEtiquetaL";
			newRow.className = "EFirma";
			// newCell.innerHTML = '<BR><p align="center">CADENA DE
			// FIRMADO:</p>' + cFirma;
			newCell.innerHTML = '<BR><p align="center" class="EEtiquetaC">CADENA DE FIRMADO:</p>'
					+ InicioTabla("", 1, "600px")
					+ ITRTD("EFirma", 0, "1px")
					+ cFirma
					+ FTDTR()
					+ FinTabla()
					+ '<BR><p align="center" class="EFirma">CERTIFICADO:</p><p align="JUSTIFY">'
					+ cFirmante + "</p>";
		}
		// Genera Campos de Captura
		newRow = tBdy.insertRow(iRR++);
		newCell = newRow.insertCell(0);
		newRow.className = "EEtiquetaL";
		newCell.innerHTML = Hidden("CCADORIGEN", "") + Hidden("CCADFIRMA", "")
				+ Hidden("CRFCFirma", "");

		for (it = 0; it < aAT.length; it++) {
			fGetObj(aAT[it][0], aAT[it][1]);
		}
		for (it = 0; it < aSelect.length; it++) {
			fLlenaCombos(aSelect[it]);
		}
		if (aSelect.length > 1) {
			fLlenaCombos(aSelect[1]);
		}
		if (aSelect.length > 2) { // NO SE PORQUE PERO HAY QUE HACER ESTO SI
									// NO NI SE LLENAN LOS COMBOS
			fLlenaCombos(aSelect[2]);
		}
		if (aSelect.length > 3) { // NO SE PORQUE PERO HAY QUE HACER ESTO SI
									// NO NI SE LLENAN LOS COMBOS
			fLlenaCombos(aSelect[3]);
		}
		
		
		if (aRes2.length > 0) {
			if (frm.LFINALIZADO.value == 0) {
				FRMPanel.fSetTraStatus(",");
			} else {
				FRMPanel.fSetTraStatus("Can,");
			}
			fDisabled(true);
		}
	}
}

function fLlenaCombos(aRowCombo) {
	frm.cTABLA.value = aRowCombo[0];
	frm.cCVE.value = aRowCombo[1];
	frm.cDSC.value = aRowCombo[2];
	frm.cOBJ.value = aRowCombo[3];
	frm.cVD.value = aRowCombo[4];
	if (frm.cTABLA.value == "ACUSTOM") {
		iLength = fNumEntries(frm.cCVE.value, ",");
		aCustom = new Array();
		if (iLength > 0) {
			for (i = 0; i <= iLength; i++) {
				aCustom[i] = [ fEntry(i + 1, frm.cCVE.value, ","),
						fEntry(i + 1, frm.cDSC.value, ",") ];
			}
		}
		fFillSelect(eval("frm." + frm.cOBJ.value), aCustom, false,
				frm.cVD.value, 0, 1);
	} else if (frm.cTABLA.value == "ANUMERIC") {
		iInicio = parseInt(frm.cCVE.value, 10);
		iFin = parseInt(frm.cDSC.value, 10);
		aNumeric = new Array();
		iIndex = 0
		for (iI = iInicio; iI <= iFin; iI++) {
			aNumeric[iIndex] = [ iI, iI ];
			iIndex++;
		}
		fFillSelect(eval("frm." + frm.cOBJ.value), aNumeric, false,
				frm.cVD.value, 0, 1);
	} else if (frm.cTABLA.value == "ALOGICAL") {
		aLogical = new Array([ 0, "No" ], [ 1, "Si" ]);
		fFillSelect(eval("frm." + frm.cOBJ.value), aLogical, false,
				frm.cVD.value, 0, 1);
	} else if (frm.cTABLA.value == "VEHMARCA") {
		fFillSelect(eval("frm." + frm.cOBJ.value), aMarca, false,
				frm.cVD.value, 0, 1);
	} else if (frm.cTABLA.value == "PERTIPOPERMISIONAR") {
		fAsignaSelect(eval("frm." + frm.cOBJ.value),
				frm.ICVETIPOPERMISIONATRAMITE.value,
				frm.CTIPOPERMISIONARIO.value);
	} else {
		frm.hdNumReg.value = 10000;
		if (frm.cTABLA.value == "PERTIPOSERVICIO") {
			frm.hdFiltro.value = 'ICVETIPOPERMISIONA = '
					+ frm.ICVETIPOPERMISIONATRAMITE.value;
		} else {
			frm.hdFiltro.value = '';
		}
		if (aRowCombo[5] != "NOLLENAR") {
			frm.hdOrden.value = frm.cDSC.value;
			fEngSubmite("pgINTCatShow.jsp", "IDCS");
		}
	}
}

function fGetObj(cNombre, cValor) {
	for (xc = 0; xc < frm.elements.length; xc++) {
		obj = frm.elements[xc];
		if (obj.name == cNombre) {
			obj.value = cValor;
			// break;
		}
	}
}
function fSetField(aField, lEtiqueta, aRes2) {
	cMan = '';
	cCampo = '';
	cAst = '';
		
	if (aField[3] == 7) { // Adjuntar
		cValor = '';
		
		if (aField[2] > 0) {
			existe=true;
			cLiga = Liga("[Ver Documento]", "fShowIntDocDig("+aField[2]+");");
			cCampo += ITD() + ITRTD("EEtiqueta", 0, "", "20")
			+ ITD("EEtiqueta", 0, "", "20") + TextoSimple(aField[14])
			+ FITD("EEtiquetaC") + SP() + cLiga + FTDTR();
			
			FRMPanel.fSetTraStatus("Disabled");
			
		} else {
			cCampo += ITD("EEtiquetaL", 0) + TextoSimple(aField[14] + ":")
			+ FITD() + cMan
			+ Hidden("CDOCUMENTO" + (iOrdHF++), aField[2])
			+ Hidden("CCAMPO" + (iOrdHF++), aField[1])
			+ '<input type="file" name="' + aField[1].trim() + '" size="50">'
			+ FTDTR();
			
			FRMPanel.fSetTraStatus("UpdateBegin");
		}
		
		aEtiqueta += "||" + aField[2];
		aListado += "||" + aField[1];
		aValor += "||" + cValor;
		if (cValor != "")
			lValores = true;
	}
	
	return cCampo;
}

function fGetValue(aRes2, cNCam) {
	for (tyu = 0; tyu < aRes2.length; tyu++) {
		if (aRes2[tyu][2] == cNCam) {
			return tyu;
		}
	}
	return -1;
}

function fNuevo() {
	if (fSoloNumeros(frm.iCveTipoTramBus.value) == true
			&& frm.iCveTipoTramBus.value != '') {
		frm.iCveSol.value = '';
		// frm.cBusca.value = '';
		fNewTram();
		// frm.cBusca.disabled=true;
	} else {
		fAlert("\n - No ha seleccionado él trámite a precapturar.");
	}
}

function firmaSuccess(outputPaths, cCadFirmada, cNombre, cCURP, cRFC, cFirmNum,
		cLocSerialNumber, cLocIssuerDN, cLocSubjectDN, iCveCertificado) {
	
	fEnProceso(true);
	
	frm.cFirmante.value = 'Asunto:~' + cLocSubjectDN + '~~Emisor:~'
			+ cLocIssuerDN + '~~Número Serial:~' + cLocSerialNumber;
	frm.cCveCertificado.value = iCveCertificado;
	cCADFirmada = cFirmNum;
	frm.CCADFIRMA.value = cFirmNum;
	frm.CRFCFirma.value = cRFC;
	cNOMCOM = cRFC + ' - ' + cNombre;
	FRMPanel.fSetTraStatus("UpdateComplete");
	lGuardar = true;
	frm.hdBoton.value = 'Guardar';
	fDelTBdyFIEL();
	frm.LFGA.value = 1;
	
	//alert("regresa de la firma y hace submit");
	setTimeout(function(){frm.submit();},250);
}

function fGetICVETRAMITEINT() {
	return frm.iNumCita.value;
}
function fGetICONSECUTIVO() {
	return frm.ICONSECUTIVO.value;
}
function fGetCCADORIGINAL() {
	return cCADOriginal;
}
function fGetCCADFIRMADA() {
	return cCADFirmada;
}
function fGetCNOMPERSONA() {
	return cNOMCOM;
}

function firmaCancel() {
	fCancelar();
	fDelTBdyFIEL();
}
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
}
function fGetCadOriginal() {
	cRequisitos = "";
	cCadena = "";
	for (iQ = 0; iQ < form.elements.length; iQ++) {
		obj = form.elements[iQ];
/*
		if (obj.type == 'text' || obj.type == 'textarea'
				|| obj.type == 'select-one') {
			cValor = obj.value;
			if (obj.type == 'select-one' && cValor != "") {
				cValor2 = "";
				if (obj[obj.selectedIndex].text != "") {
					cValor2 = obj[obj.selectedIndex].text;
				}
				cCadena += "/" + obj.name + "-" + cValor.trim() + "-"
						+ cValor2.trim();
			} else {
				cCadena += "/" + obj.name + "-" + cValor.trim();
			}
		}*/
		for (i = 0; i < aObj.length; i++) {
			if (obj.name == aObj[i][1]) {
				cRequisitos += aObj[i][14] + ",";
			}
		}
	}

	var aReq = new Array();
	var aNuevo = new Array();
	aReq = cRequisitos.split(",");
	for (i = 0; i < aReq.length; i++) {
		var lEncontrado = false;
		for (j = 0; j < aNuevo.length; j++) {
			if (aReq[i] == aNuevo[j])
				lEncontrado = true;
		}
		if (lEncontrado == false) {
			aNuevo[aNuevo.length] = aReq[i];
		}
	}
	frm.cRequisitos.value = "";
	for (i = 0; i < aNuevo.length; i++) {
		frm.cRequisitos.value += aNuevo[i]
				+ ((i + 1) < aNuevo.length ? "," : "");
	}

	if (cCadena.length > 0)
		cCadena = cCadena.substring(1);
	cCADOriginal = cCadena;
	return cCADOriginal;
}
function fGetDocs() {
	cFiles = "";
	for (i = 0; i < frm.elements.length; i++) {
		obj = frm.elements[i];
		if (obj.type == 'file') {
			cFiles += obj.value.replace(/\\/g, '/') + '||';
		}
	}
	return cFiles;
}
function fGuardar() {
	
	if(validaArch()==true)
		return;
	
	 cCadenaOriginal = cadDatosADV();
		cCadenaOriginal += fGetCadOriginal();	 

	if (fValidaTodo() == true) {
		var cMsg1 = '¿Todos los datos ya se encuentran revisados? \n\nDado que se firman digitalmente los archivos junto con el formulario, \nuna vez guardado no podrá realizar modificaciones a la solicitud: '
				+ frm.iNumCita.value;
		if (confirm(cMsg1) == true) {
			// cCadenaOriginal = fGetCadOriginal();
			lGuardar = false;
			iRow = 0;
			newRow = tBdyFIEL.insertRow(iRow++);
			newCell = newRow.insertCell(0);
			newRow.className = "EEtiquetaC";
			newCell.align = "center";

			FRMPanel.fSetTraStatus("UpdateComplete");
			lGuardar = true;
			frm.hdBoton.value = 'Guardar';
			FRMTramites = fBuscaFrame("INT1");
			frm.iEjercicio.value = FRMTramites.fGetEjercicio();
			frm.iNumSolicitud.value = FRMTramites.fGetSolicitud();

			cCadenaOriginal = cadDatosADV();
			cCadenaOriginal += fGetCadOriginal();

			// FRMAdj = fBuscaFrame("IADJUNTAR");
			newCell.innerHTML = '<iframe name="FIEL" width="550px" height="350px" frameborder=0 scrolling=no SRC="'
					+ cRutaFIEL
					+ '?cListado='
					+ fGetDocs()
					+ '&cCadena='
					+ cCadenaOriginal
					+ '&cRutaJSPFIEL='
					+ cRutaProgMM
					+ '&ICVETRAMITE='
					+ frm.iNumCita.value
					+ '&cArea='
					+ cAreaCISFIEL + '"></iframe>';
			frm.CCADORIGEN.value = cCadenaOriginal;
			FRMPanel.fSetTraStatus(",");
		}
	}
}

function fGuardarA() {
	fGuardar();
}

function fModificar() {
	fDisabled(false);
	FRMPanel.fSetTraStatus("UpdateBegin");
}

function fCancelar() {
	if (frm.LFINALIZADO.value == 0) {
		FRMPanel.fSetTraStatus(",");
	} else {
		FRMPanel.fSetTraStatus("Can,");
		parent.fPagFolder(1);
	}
	fDisabled(true);
}

/**
 * Valida los Scripts registrados en la tabla de los campos por trámite.
 */
function fValScripts() {
	cMsg = "";
	var patronRFC = /^([A-Z]{3,4}[0-9]{6}[A-Z]{3})$/;
	var patronRFCRP = /^([A-Z]{3,4}[0-9]{6}[A-Z]{0,3})$/;
	var patronNombre = /^([A-Z]*[a-z]*)$/;

	if (fScripts != "") {
		cScriptList = fScripts.split(";");
	} else {
		cScriptList = new Array();
	}

	for (s = 0; s < cScriptList.length; s++) {
		cScript = cScriptList[s];
		if (cScript.indexOf("@") >= 0) {
			cScript = cScript.replace(/@/g, '"').replace(/@/g, '"');
		}
		if (cScript.indexOf("#") >= 0) {
			cScript = cScript.replace(/#/g, '+').replace(/#/g, '+');
		}

		if (cScript.length > 0) {
			eval(cScript);
		}
	}
	return cMsg;
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo() {
	cMsg = fValElements();
	cMsg += fValScripts();
	if (cMsg != "") {
		fAlert(cMsg);
		return false;
	} else {
		frm.cCamSol.value = '';
		frm.iGRLDepto.value = '0';
		for (iQ = 0; iQ < form.elements.length; iQ++) {
			obj = form.elements[iQ];
			if (obj.type == 'text' || obj.type == 'textarea'
					|| obj.type == 'select-one') {
				cValor = obj.value;
				if (cValor == '')
					cValor = ' ';

				if (obj.type == 'select-one' && cValor != " ") {
					cValor2 = " ";
					if (obj[obj.selectedIndex].text != "") {
						cValor2 = obj[obj.selectedIndex].text;
					}
					frm.cCamSol.value += "^" + obj.name + "=" + cValor + "="
							+ cValor2 + "=";
				} else {
					frm.cCamSol.value += "^" + obj.name + "=" + cValor + "= =";
				}
				frm.cCamSol.value += iQ + "=";
				if (obj.name.toUpperCase() == "ICVEDEPARTAMENTO") {
					frm.iGRLDepto.value = obj.value;
				}
			}
		}
		if (frm.cCamSol.value.length > 0)
			frm.cCamSol.value = frm.cCamSol.value.substring(1);
		frm.cCamSolDIN.value = frm.cCamSol.value;
	}
	return true;
}
function fImprimir() {
	self.focus();
	window.print();
}

function fGetGuardado() {
	return guardado;
}

function fLiga1(cpag) {
	// var indice = frm.TIPODOCDIG.selectedIndex;
	// frm.CTIPODOC.value = frm.TIPODOCDIG.options[indice].text;

	fAbreSubWindow(true, cpag, 'no', 'yes', 'yes', 'yes', '500', '600', 10, 10);
}

function fGetICVETRAMITE() {
	return frm.ICVETRAMITE.value;
}

function fFirmaElectr() {
	frm.C2012.value = "firmaXML.html";
	fAbreSubWindow(true, "pgFirmaXML", 'no', 'yes', 'yes', 'yes', '500', '600',
			10, 10);
}

function fFirmaElectr2() {
	frm.C2012.value = "firmaXML.html";
	fAbrePaginaHTML("firmaXML.html");
	// fAbreSubWindow(true,pgFirma2012.js,'no','yes','yes','yes','500','600',10,10);
}

function fCarga(lSuccess, cArchivos) {
	if (lSuccess == false)
		fAlert("\n- La captura u obtención de los documentos presentó un problema, consulte a su administrador de sistemas.");
	else {
		iFrm = document.getElementById("IADJUNTAR");
		iFrm.style.width = '0%';
		iFrm.style.height = '0%';
		// fGenADJUNTA();
		fAlert("\n- La captura u obtención de los documentos se realizó correctamente.");
	}
}

function getCTIPOPERMISIONARIO() {
	FRMSol = fBuscaFrame("INT1");
	return FRMSol.getCTIPOPERMISIONARIO();
}

function getCDSCTIPOTRAMITE() {
	FRMSol = fBuscaFrame("INT1");
	return FRMSol.getCDSCTIPOTRAMITE();
}

function fLlamae5() {
	fAbreWindowHTML(false, "http://aplicaciones.sct.gob.mx/e5Cinco/", "yes",
			"yes", "yes", "yes", "yes", 800, 600, 50, 50);
}

function cadDatosADV() {
	getValores();
	var cadPre = frm.iEjercicio.value + "/" + frm.iNumSolicitud.value + "/"
			+ frm.iCveTramite.value + "/" + frm.iCveModalidad.value + "/"+
			"RepL:"+frm.hdcRFC_RepL.value+"/"+frm.hdcNomRaz_RepL.value+"/"+
			frm.hdcApPat_RepL.value+"/"+frm.hdcApMat_RepL.value+"/"+
			frm.hdcMail_RepL.value+"/"+frm.hdcTel_RepL.value+"/"+
			frm.hdcCalle_RepL.value+"/"+frm.hdcNumExt_RepL.value+"/"+
			frm.hdcNumInt_RepL.value+"/"+frm.hdcCol_RepL.value+"/"+
			frm.hdcCP_RepL.value+"/"+frm.hdcNomEnt_RepL.value+"/"+frm.hdcNomMun_RepL.value+"/"+
			"Empr:"+frm.hdcRFC_Sol.value+"/"+frm.hdcNomRaz_Sol.value+"/"+
			frm.hdcApPat_Sol.value+"/"+frm.hdcApMat_Sol.value+"/"+
			frm.hdcMail_Sol.value+"/"+frm.hdcTel_Sol.value+"/"+
			frm.hdcCalle_Sol.value+"/"+frm.hdcNumExt_Sol.value+"/"+
			frm.hdcNumInt_Sol.value+"/"+frm.hdcCol_Sol.value+"/"+
			frm.hdcCP_Sol.value+"/"+frm.hdcNomEnt_Sol.value+"/"+frm.hdcNomMun_Sol.value+"/";
	
	var cadFin = reemplazaCaracteres(cadPre);
	
	return cadFin;
}
	
function reemplazaCaracteres(cadSimbolos){

	var cadAnt=cadSimbolos;
	cadAnt= cadAnt.replace(/á/g, "a");
	cadAnt= cadAnt.replace(/Á/g, "A");

	cadAnt= cadAnt.replace(/é/g, "e");
	cadAnt= cadAnt.replace(/É/g, "E");
	
	cadAnt= cadAnt.replace(/í/g, "i");
	cadAnt= cadAnt.replace(/Í/g, "I");
	
	cadAnt= cadAnt.replace(/ó/g, "o");
	cadAnt= cadAnt.replace(/Ó/g, "O");
	
	cadAnt= cadAnt.replace(/ú/g, "u");
	cadAnt= cadAnt.replace(/Ú/g, "U");

	return cadAnt;
}

function fShowDatos() {

	while (tBarraWizard.rows.length > 0) {
		tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
	}

	tBarraWizard.className = "ETablaInfo";
	tBarraWizard.width = "90%";

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.innerHTML = TextoSimple("Datos del Representante Legal");
	tCell.className = "ETablaST";

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*RFC Representante Legal:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cRFC_RepL", "", 70, 70, "RFC", "", "", "",
			false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Nombre:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cNomRaz_RepL", "", 70, 70, "Nombre", "", "",
			"", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Ap. Paterno:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cApPat_RepL", "", 70, 70, "Ap. Paterno", "",
			"", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Ap. Materno:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cApMat_RepL", "", 70, 70, "Ap. Materno", "",
			"", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*e-Mail:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cMail_RepL", "", 70, 70, "Mail", "", "",
			"", false, false);
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Teléfono:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cTel_RepL", "", 70, 70, "Teléfono", "", "",
			"", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Calle:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cCalle_RepL", "", 70, 70, "Calle", "", "",
			"", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Núm. Exterior:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cNumExt_RepL", "", 70, 70, "Núm. Exterior",
			"", "", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Núm. Interior:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cNumInt_RepL", "", 70, 70, "Núm. Interior",
			"", "", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*Colonia:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cCol_RepL", "", 70, 70, "Colonia", "", "",
			"", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "*C.P.:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cCP_RepL", "", 70, 70, "C.P.", "", "", "",
			false, false);
	
	if(existe==false){
	
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "*Estado:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Select("iCveEntidadFedA", "fLoadMunicipiosA();");
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "*Municipio/Delegación:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Select("iCveMunicipioA", "");
	}else{
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "*Estado:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Text(true, "cNomEnt_RepL", "", 70, 70, "Estado", "", "",
				"", false, false);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "*Municipio:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Text(true, "cNomMun_RepL", "", 70, 70, "C.P.", "", "", "",
				false, false);
	}
	
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.innerHTML = TextoSimple("Datos del Solicitante (Empresa)");
	tCell.className = "ETablaST";
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "RFC Empresa:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cRFC_Sol", "", 70, 70, "RFC", "", "", "",
			false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Nombre:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cNomRaz_Sol", "", 70, 70, "Nombre", "", "",
			"", false, false);
	
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Ap. Paterno:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cApPat_Sol", "", 70, 70, "Ap. Paterno", "",
			"", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Ap. Materno:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cApMat_Sol", "", 70, 70, "Ap. Materno", "",
			"", "", false, false);
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "e-Mail:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cMail_Sol", "", 70, 70, "Mail", "", "",
			"", false, false);
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Teléfono:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cTel_Sol", "", 70, 70, "Teléfono", "", "",
			"", false, false);


	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Calle:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cCalle_Sol", "", 70, 70, "Calle", "", "",
			"", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Núm. Exterior:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cNumExt_Sol", "", 70, 70, "Núm. Exterior",
			"", "", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Núm. Interior:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cNumInt_Sol", "", 70, 70, "Núm. Interior",
			"", "", "", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Colonia:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cCol_Sol", "", 70, 70, "Colonia", "", "",
			"", false, false);

	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "C.P.:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(true, "cCP_Sol", "", 70, 70, "C.P.", "", "", "",
			false, false);

	if(existe==false){
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "Estado:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Select("iCveEntidadFedB", "fLoadMunicipiosB();");
		
		tRw = tBarraWizard.insertRow();  
		tCell = tRw.insertCell();
		tCell.innerHTML = "Municipio/Delegación:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Select("iCveMunicipioB", "");
		
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.colSpan = 2;
		tCell.innerHTML = TextoSimple("Documentación Requerida");
		tCell.className = "ETablaST";
	}else{
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "Estado:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Text(true, "cNomEnt_Sol", "", 70, 70, "Estado", "", "",
				"", false, false);

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = "Municipio:";
		tCell.className = "EEtiqueta";
		tCell = tRw.insertCell();
		tCell.innerHTML = Text(true, "cNomMun_Sol", "", 70, 70, "C.P.", "", "", "",
				false, false);
	}
}


function buscaTram() {
	frm.hdOrden.value = "T.cCveInterna";
	frm.hdFiltro.value = " lTramInt=1 AND lVigente=1 and LTRAMITEFINAL=1";
	frm.hdFiltro.value += " AND T.cCveInterna not like 'PT%'";
	fEngSubmite("pgTRACatTramite1.jsp", "cIdTramite");
}

function fLoadEntidades(){
	frm.hdSelect.value = "SELECT ICVEENTIDADFED,CNOMBRE FROM GRLENTIDADFED where ICVEPAIS=1 and ICVEENTIDADFED > 0 order by CNOMBRE asc";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdEntidad");
}


function fLoadMunicipiosA(){
	frm.hdSelect.value = "SELECT ICVEMUNICIPIO, CNOMBRE FROM GRLMUNICIPIO where ICVEPAIS=1 and ICVEMUNICIPIO > 0 and ICVEENTIDADFED ="+ frm.iCveEntidadFedA.value +" order by CNOMBRE asc";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdMunA");
}

function fLoadMunicipiosB(){
	frm.hdSelect.value = "SELECT ICVEMUNICIPIO, CNOMBRE FROM GRLMUNICIPIO where ICVEPAIS=1 and ICVEMUNICIPIO > 0 and ICVEENTIDADFED ="+ frm.iCveEntidadFedB.value +" order by CNOMBRE asc";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdMunB");
}


function getRFCSolicitante(){
	
	if (window.parent) {
		if (window.parent.fGetRFCRep) {
			frm.cRFC_RepL.value= window.parent.fGetRFCRep();
		}
	}
}


function getExisteSolicitante(){
	
	if (window.parent) {
		if (window.parent.fGetRFCRep) {
			frm.cRFC_RepL.value= window.parent.fGetRFCRep();
		}
	}
}


function llenaCampos(){
	
	frm.hdSelect.value = "BUSCA_SOLICITANTE";
	
	if(frm.cRFC_RepL.value!=""){
		frm.hdValorX.value=frm.cRFC_RepL.value;
		RFCx=frm.cRFC_RepL.value;
	}else{
		frm.hdValorX.value=RFCx;
	}
	
	fEngSubmite("pgConsulta.jsp", "cIdDatos");
	
}



function compruebaSol(){
	if (window.parent) {
		if (window.parent.getExisteSolicitante) {
			existe= window.parent.getExisteSolicitante();
		}
	}
}


function bloquearCampos(){
	
	frm.cRFC_RepL.disabled=true;
	frm.cNomRaz_RepL.disabled=true;
	frm.cApPat_RepL.disabled=true;
	frm.cApMat_RepL.disabled=true;
	frm.cMail_RepL.disabled=true;
	frm.cTel_RepL.disabled=true;
	frm.cCalle_RepL.disabled=true;
	frm.cNumExt_RepL.disabled=true;
	frm.cNumInt_RepL.disabled=true;
	frm.cCol_RepL.disabled=true;
	frm.cCP_RepL.disabled=true;
	frm.cNomEnt_RepL.disabled=true;
	frm.cNomMun_RepL.disabled=true;
	frm.cRFC_Sol.disabled=true;
	frm.cNomRaz_Sol.disabled=true;
	frm.cApPat_Sol.disabled=true;
	frm.cApMat_Sol.disabled=true;
	frm.cMail_Sol.disabled=true;
	frm.cTel_Sol.disabled=true;
	frm.cCalle_Sol.disabled=true;
	frm.cNumExt_Sol.disabled=true;
	frm.cNumInt_Sol.disabled=true;
	frm.cCol_Sol.disabled=true;
	frm.cCP_Sol.disabled=true;
	frm.cNomEnt_Sol.disabled=true;
	frm.cNomMun_Sol.disabled=true;
	
}

function getValores(){
	
	frm.hdcRFC_RepL.value =  	frm.cRFC_RepL.value;
	frm.hdcNomRaz_RepL.value =	frm.cNomRaz_RepL.value;
	frm.hdcApPat_RepL.value =	frm.cApPat_RepL.value;
	frm.hdcApMat_RepL.value =	frm.cApMat_RepL.value;
	frm.hdcMail_RepL.value =	frm.cMail_RepL.value;
	frm.hdcTel_RepL.value =	frm.cTel_RepL.value;
	frm.hdcCalle_RepL.value =	frm.cCalle_RepL.value;
	frm.hdcNumExt_RepL.value =	frm.cNumExt_RepL.value;
	frm.hdcNumInt_RepL.value =	frm.cNumInt_RepL.value;
	frm.hdcCol_RepL.value =	frm.cCol_RepL.value;
	frm.hdcCP_RepL.value =	frm.cCP_RepL.value;
	
	frm.hdcRFC_Sol.value =	frm.cRFC_Sol.value;
	frm.hdcNomRaz_Sol.value =	frm.cNomRaz_Sol.value;
	frm.hdcApPat_Sol.value =	frm.cApPat_Sol.value;
	frm.hdcApMat_Sol.value =	frm.cApMat_Sol.value;
	frm.hdcMail_Sol.value =	frm.cMail_Sol.value;
	frm.hdcTel_Sol.value =	frm.cTel_Sol.value;
	frm.hdcCalle_Sol.value =	frm.cCalle_Sol.value;
	frm.hdcNumExt_Sol.value =	frm.cNumExt_Sol.value;
	frm.hdcNumInt_Sol.value =	frm.cNumInt_Sol.value;
	frm.hdcCol_Sol.value =	frm.cCol_Sol.value;
	frm.hdcCP_Sol.value =	frm.cCP_Sol.value;
	
	if(existe==false){
		
		var cmbE = document.getElementById("iCveEntidadFedA");
		frm.hdcNomEnt_RepL.value = cmbE.options[cmbE.selectedIndex].text;
		
		var cmbM = document.getElementById("iCveMunicipioA");
		frm.hdcNomMun_RepL.value = cmbM.options[cmbM.selectedIndex].text;
		
		if(frm.hdcRFC_Sol.value!=""){
			var e2 = document.getElementById("iCveEntidadFedB");
			frm.hdcNomEnt_Sol.value = e2.options[e2.selectedIndex].text;
			
			var e3 = document.getElementById("iCveMunicipioB");
			frm.hdcNomMun_Sol.value = e3.options[e3.selectedIndex].text;
		}
		
	} 
else {
	
	frm.hdcNomEnt_RepL.value = frm.cNomEnt_RepL.value;
	frm.hdcNomMun_RepL.value = frm.cNomMun_RepL.value;
		
		if(frm.hdcRFC_Sol.value!=""){
			frm.hdcNomEnt_Sol.value = frm.cNomEnt_Sol.value;
			frm.hdcNomMun_Sol.value = frm.cNomMun_Sol.value;  
		}
	}
}


function simulaFirma(){
	 cCadenaOriginal = cadDatosADV();
	 cCadenaOriginal += fGetCadOriginal();	
	 firmaSuccess("outputPaths", "cadFRIMADA", "cNOMBRE", "cCURP", "MOR0000001O1", "12345","cLocSerialNumber", "cLocIssuerDN", "cLocSubjectDN", "0");
}

function cerrarVentana(){
	if(top)
		top.close();
}
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

var statusArr = new Array();
statusArr[0] = ['1','EN PROCESO'];
statusArr[1] = ['2','OTORGADO'];
statusArr[2] = ['3','ABANDONADO'];
statusArr[3] = ['4','CANCELADO'];
statusArr[4] = ['5','NEGADO'];

var stFilter ="";




// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pg111040033.js";
//	if (top.fGetTituloPagina)
//		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
//	cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO") ? "CONSULTA HISTÓRICA ADV"
//			: cTitulo;
	cTitulo = "CONSULTA HISTÓRICA ADV";
	fSetWindowTitle();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJS);
	InicioTabla("ETablaInfo", 0, "100%", "", "center", "", "1");
	ITR("", 0, "", "", "center");
	ITD();
	InicioTabla("ETablaInfo", 0, "", "", "center", "", "1");
	ITR("", 0, "", "", "left");
	FTD();
	TDEtiCampo(false, "EEtiqueta", 0, "Promovente:", "cPromovente", "", 65, 65,
			"Promovente", "fMayus(this);",
			"", "",
			false, "", "");
	TDEtiCampo(false, "EEtiqueta", 0, "Concesionario:", "cConcesionario", "", 65, 65,
			"Concesionario", "fMayus(this);",
			"", "",
			false, "", "");
	FTD();
	FTR();

	ITR("", 0, "", "", "center");
	TDEtiSelect("true", "EEtiqueta", 0, "Tipo de aprovechamiento: ",
			"iCveTramite", "fTamOnChange();", "", 0);
	FTD();
	TDEtiSelect("true", "EEtiqueta", 0, "Centro SCT: ", "iCveOficinaFiltro",
			"", "", 0);
	FTD();
	FTR();

	ITR("", 0, "", "", "center");
	TDEtiSelect("true", "EEtiqueta", 0, "Autopista: ", "iCveCarretera", "", "", 0);
	TDEtiSelect("true", "EEtiqueta", 0, "Estatus: ", "iCveStatus", "", "", 0);
	FTD();	
	FTR();
	
	
	ITR("", 0, "", "", "center");
	TDEtiCampo(true, "EEtiqueta", 0, " Fecha de registro Inicio:",
			"dtRegInit", "", 10, 10, " Fecha de registro Inicio",
			"fMayus(this);", "", "", false, "", 0);
	
	TDEtiCampo(true, "EEtiqueta", 0, " Fecha de registro Fin:",
			"dtRegEnd", "", 10, 10, " Fecha de registro Fin",
			"fMayus(this);", "", "", false, "", 0);
	FTR();
	
	ITR("", 0, "", "", "center");
	TDEtiCampo(true, "EEtiqueta", 0, " Fecha de ingreso DGDC Inicio:",
			"dtIngInit", "", 10, 10, " Fecha de ingreso DGDC Inicio",
			"fMayus(this);", "", "", false, "", 0);
	
	TDEtiCampo(true, "EEtiqueta", 0, " Fecha de ingreso DGDC Fin:",
			"dtIngEnd", "", 10, 10, " Fecha de ingreso DGDC Fin",
			"fMayus(this);", "", "", false, "", 0);
	FTR();
	
	ITR("", 0, "", "", "center");
		ITD("", 4, "", "", "center");
			BtnImg("Buscar", "lupa", "fBuscar();");
		FTD();
	FTR();
	
	
	FinTabla();
	FTDTR();

	ITRTD();
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
	FinTabla();
	FTDTR();
	FinTabla();
	FTDTR();
	FinTabla();
	Hidden("iEjercicio", "");
	Hidden("iNumSolicitud", "");
	// Hidden("hdLlave","");
	// Hidden("hdSelect","");
	Hidden("cDscTramite", "");
	Hidden("iCveModalidad", "");
	Hidden("cDscModalidad", "");
	Hidden("iCveOficinaUsr", "");
	Hidden("cDscOficinaUsr", "");
	Hidden("iCveTramiteA1", "");
	Hidden("hdSelect");
	Hidden("hdLlave");
	
	Hidden("cFiltroADV", "");
	Hidden("cFiltroHist", "");
	Hidden("iCveHist", "");

	Hidden("iCveUsuario", fGetIdUsrSesion());
	fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
	FRMPanel = fBuscaFrame("IPanel");
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);

	FRMListado.fSetTitulo("Tipo de aprovechamiento,Trámite nuevo o regularización ,F. Registro de solicitud ,F. Ingreso DGDC, No. Permiso, Fecha de otorgamiento, Fecha de conclusión, Motivo de conclusión, Permiso cedido a, Centro SCT, Nombre o razón social del promovente, Autopista, Concesionario, Kilometraje/Sentido, Destino del aprovechamiento, Estatus,Documentos,");
	FRMListado.fSetCampos("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,18,");
	FRMListado.fSetDespliega("texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,texto,");
	FRMListado.fSetAlinea("center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,");
    frm.hdBoton.value = "Primero";
    fFillSelect(frm.iCveStatus,statusArr,true,0,0,1);
	fTramite();
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
	
	if (cError != ""){
		fAlert("Ha ocurrido un error, intente más tarde.");
	}

	if (cId == "cIdCarretera" && cError == "") {
		fFillSelect(frm.iCveCarretera, aRes, true, frm.iCveCarretera.value, 0,
				1);
		fOficina();
	}

	
	if (cId == "ListadoHist" && cError == "") {
		
		aRes = setStatus(aRes);
		if(parseInt(frm.iCveStatus.value)>1)
			aRes = filterSt(aRes);
		
		FRMListado.fSetListado(aRes);
		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
	}
	

	if (cId == "idTramite" && cError == "") {
		
		for (var i = 0; i < aRes.length; i++) {
			aRes[i][1] = aRes[i][2] + " - " + aRes[i][1];
		}
		
		fFillSelect(frm.iCveTramite, aRes, true, 0, 0, 1);
		
		fObtenCarreteras();
	}

	if (cId == "cIdOficina" && cError == "") {
		fFillSelect(frm.iCveOficinaFiltro, aRes, true,
				frm.iCveOficinaFiltro.value, 0, 1);
	}
}

function verificaDGDC() {
	frm.hdSelect.value = "VERIF_DGDC";
	frm.hdLlave.value = "";
	fEngSubmite("pgConsulta.jsp", "cIdVerifDGDC");
}

function fCargaTramite() {
	frm.hdFiltro.value = "";
	frm.hdOrden.value = "TRACatTramite.cDscBreve";
	frm.hdNumReg.value = "10000";
	fEngSubmite("pgTraCatTramite.jsp", "idTramite");
}

function fObtenCarreteras() {
	frm.hdSelect.value = "select MAX(ICVECARRETERA) as ICVECARRETERA, CDSCARRETERA from TRACATCARRETERA GROUP BY CDSCARRETERA";
	frm.hdLlave.value = "ICVECARRETERA";
	fEngSubmite("pgConsulta.jsp", "cIdCarretera");
}

// LLAMADO desde el Listado cada vez que se selecciona un renglon
function fSelReg(aDato, iCol) {
	
	if(aDato[0]=="1"){
		frm.iEjercicio.value=aDato[23];
		frm.iNumSolicitud.value=aDato[24];
		frm.iCveHist.value="";
		
	}else{
		frm.iEjercicio.value=-1;
		frm.iNumSolicitud.value=-1;
		frm.iCveHist.value=aDato[17];
	}
	
	   if(iCol==16 && aDato[0]=="1"){
		   fArchivosADV();
	   }else if(iCol==16 && aDato[0]=="0"){
		   fArchivosHist();
	   }
}

function fArchivosADV(){
	fVerDocsADV();
}

function fArchivosHist(){
	fVerDocsHist();
}

function fVerDocsHist(){
	if (frm.iCveHist.value != '') {		
		fAbreSubWindow(false, "pgSubirDocHist", "no", "yes", "yes", "yes",
				"800", "600", 50, 50);
	}
		else
	fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
}

function fBuscar() {

	var msgErr = "";
	
	
	if((frm.dtRegInit.value!=""&&frm.dtRegEnd.value=="")||(frm.dtRegInit.value==""&&frm.dtRegEnd.value!=""))
		msgErr+="- Debe proporcionar valor de inicio y fin para buscar por Fecha de registro. ";
		
	if((frm.dtIngInit.value!=""&&frm.dtIngEnd.value=="")||(frm.dtIngInit.value==""&&frm.dtIngEnd.value!=""))
		msgErr+="- Debe proporcionar valor de inicio y fin para buscar por Fecha de ingreso. ";
	
	if(msgErr!="")
		fAlert(msgErr);
	else
		fNavegaHist();
}


function fNavegaHist(){
	getParamsHist();
	frm.hdBoton.value = "Historico";
	frm.hdOrden.value = " HIST.DTREGISTRO ";
	frm.hdNumReg.value = "10000";
	fEngSubmite("pg111020015ADV.jsp", "ListadoHist");
}

function getParamsHist(){
	stFilter="";
	
	var arrDt = new Array();
	
	frm.cFiltroHist.value = "";
	frm.cFiltroADV.value= "";
	
	if(frm.cPromovente.value!=""){
		
		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";

		frm.cFiltroHist.value +=" HIST.CSOLICITANTE LIKE '%"+frm.cPromovente.value+"%' ";
		
		
		frm.cFiltroADV.value +=" AND (GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO) LIKE '%"+frm.cPromovente.value+"%' ";
	}
	
	if(frm.cConcesionario.value!=""){
		
		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";
		frm.cFiltroHist.value +=" CARR.CCONSECIONARIO LIKE '%"+frm.cConcesionario.value+"%' ";

			frm.cFiltroADV.value +=" AND CAR.CCONSECIONARIO LIKE '%"+frm.cConcesionario.value+"%' ";

	}
	
	if(parseInt(frm.iCveTramite.value)>1){
		
		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";
		frm.cFiltroHist.value +=" HIST.ICVETRAMITE = "+ frm.iCveTramite.value;
		
			frm.cFiltroADV.value +=" AND TRAREGSOLICITUD.ICVETRAMITE = "+ frm.iCveTramite.value;
	}
	
	if(parseInt(frm.iCveOficinaFiltro.value)>1){
		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";
		frm.cFiltroHist.value +=" HIST.ICVEOFICINA = "+ frm.iCveOficinaFiltro.value;
		
			frm.cFiltroADV.value +=" AND TRAREGSOLICITUD.ICVEOFICINA = "+ frm.iCveOficinaFiltro.value;
	}
	
	if(parseInt(frm.iCveCarretera.value)>1){
		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";
		frm.cFiltroHist.value +=" CARR.CDSCARRETERA = '"+ frm.iCveCarretera.options[frm.iCveCarretera.selectedIndex].text+"'";
		
	
			frm.cFiltroADV.value +=" AND CAR.CDSCARRETERA = '"+ frm.iCveCarretera.options[frm.iCveCarretera.selectedIndex].text+"'";
	}
	
	if(parseInt(frm.iCveStatus.value)>1){
		
		stFilter=frm.iCveStatus.options[frm.iCveStatus.selectedIndex].text;
		
		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";
		frm.cFiltroHist.value +=" HIST.CESTATUS = '"+ stFilter+"'";
	}

	if(frm.dtRegInit.value!=""&&frm.dtRegEnd.value!=""){

		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";
		frm.cFiltroHist.value +=" HIST.DTREGISTRO BETWEEN  '"+ getFormatedDt(frm.dtRegInit.value) +" 00:00:00' AND '"+ getFormatedDt(frm.dtRegEnd.value) +" 23:59:59'";
		
			frm.cFiltroADV.value +=" AND TRAREGSOLICITUD.TSREGISTRO BETWEEN '"+ getFormatedDt(frm.dtRegInit.value) +" 00:00:00' AND '"+ getFormatedDt(frm.dtRegEnd.value) +" 23:59:59'";
	}

	
	if(frm.dtIngInit.value!=""&&frm.dtIngEnd.value!=""){

		if(frm.cFiltroHist.value == "")
			frm.cFiltroHist.value +=" WHERE ";
		else
			frm.cFiltroHist.value +=" AND ";
		frm.cFiltroHist.value +=" HIST.DTINGRESO BETWEEN  '"+ getFormatedDt(frm.dtIngInit.value) +" 00:00:00' AND '"+ getFormatedDt(frm.dtIngEnd.value) +" 23:59:59'";
		
			frm.cFiltroADV.value +=" AND TRAREGSOLICITUD.DTRECEPCION BETWEEN '"+ getFormatedDt(frm.dtIngInit.value) +" 00:00:00' AND '"+ getFormatedDt(frm.dtIngEnd.value) +" 23:59:59'";
	}
	
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
	frm.iCveTramiteA1.value = frm.iCveTramite.value;
}

function fGetiCveEjercicio() {
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

function fOficina() {
		frm.hdSelect.value = "SELECT o.icveoficina, O.CDSCBREVE FROM GRLOFICINA o";
		frm.hdLlave.value = "";
		fEngSubmite("pgConsulta.jsp", "cIdOficina");
}

function getFormatedDt(dtA) {
	var arrDt = dtA.split("/");	
	return arrDt[2]+"-"+arrDt[1]+"-"+arrDt[0]; 
}

function setStatus(arr){
	
	
	
	for(var a=0; a<arr.length;a++){
		
		arr[a][18]="<font color=blue> Ver documentos digitales </font>";
		
		if(arr[a][0]=="1")
		{ 
		    if (arr[a][7] != "")  //TRAREGTRAMXSOL.DTCANCELACION
		        arr[a][16] = 'CANCELADO';
		      else 
	      if (parseInt(arr[a][21], 10) > 0) {//TRAREGSOLICITUD.LABANDONADA
	        arr[a][16] = 'ABANDONADO';
	      } else 
	      if (parseInt(arr[a][19], 10) > 0) { //TRAREGSOLICITUD.LRESOLUCIONPOSITIVA 
	        arr[a][16] = 'OTORGADO';
	      } else 
	      if (parseInt(arr[a][19], 10) == 0 && arr[a][22] != "") {//TRAREGSOLICITUD.LRESOLUCIONPOSITIVA , TRAREGSOLICITUD.DTRESOLTRAM
	        arr[a][16] = 'NEGADO';
	      } else 
	      if (parseInt(arr[a][19], 10) == 0 && arr[a][22] == "") {//TRAREGSOLICITUD.LRESOLUCIONPOSITIVA , TRAREGSOLICITUD.DTRESOLTRAM
	        arr[a][16] = 'EN PROCESO';
	      }
		}
	}
	
	return arr;
	
	
	/*
		* "'1' AS COLA, "+ 0
      "TRACATTRAMITE.CDSCBREVE AS COLB, "+ 1
      "TRAMODALIDAD.CDSCMODALIDAD AS COLC, "+ 2
      "TRAREGSOLICITUD.TSREGISTRO AS COLD, "+ 3
      "TRAREGSOLICITUD.DTRECEPCION AS COLE, "+ 44
      "FOL.CNUMPERMISO AS COLF, "+ 5
      "FOL.DTPERMISO AS COLG, "+ 6
      "TRAREGTRAMXSOL.DTCANCELACION AS COLH, "+ 7
      "GRLMOTIVOCANCELA.CDSCMOTIVO AS COLI, "+ 8
      "'DAT.CCEDIDO' AS COLJ, "+ 9
      "GRLOFICINA.CDSCBREVE AS COLK, "+ 10
	  "GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS COLL, "+ 11
	  "CAR.CDSCARRETERA AS COLM, "+ 12
      "CAR.CCONSECIONARIO AS COLN, "+ 13
      "DAT.CKMSENTIDO AS COLO, "+ 14 
      "DAT.CHECHOS AS COLP, "+ 15
      "'ST' AS COLQ,   "+ 16
      "1 AS COLR, "+ 17
      "TRAREGSOLICITUD.DTENTREGA AS COLS, "+ 18
      "TRAREGSOLICITUD.LRESOLUCIONPOSITIVA AS COLT, "+ 19
      "TRAREGSOLICITUD.DTENTREGA AS COLU, "+ 20
      "TRAREGSOLICITUD.LABANDONADA AS COLV, "+ 21
      "TRAREGSOLICITUD.DTRESOLTRAM AS COLW, "+ 22
	  "TRAREGSOLICITUD.IEJERCICIO AS COLX, "+ 23
      "TRAREGSOLICITUD.INUMSOLICITUD AS COLY "+ 24
	 */
}



function filterSt(arr){
	
	for(var b = arr.length-1; b>=0;b--){
		if(arr[b][16]!=stFilter)
			arr.splice(b,1);
	}
	
	return arr;
}

function getNumSol(){
	return frm.iNumSolicitud.value;
};

function getEjercicio(){
	return frm.iEjercicio.value;
};


function fVerDocsADV() {
	
//	if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != ''
//			&& frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {		
//			fAbreSubWindow(false, "pgVerDocsCotejo", "no", "yes", "yes", "yes",
//					"800", "600", 50, 50);
//		}
	
	if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != ''
		&& frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '') {		
		fAbreSubWindow(false, "pgVerDocsTramite", "no", "yes", "yes", "yes",
				"800", "600", 50, 50);
	}
			else
		fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
}


function getCveHist(){
	return frm.iCveHist.value;
}

// /PAGINA ORGINAL, DESCOMENTAR DESPUES DE QUE SE APRUEBE LA PANTALLA

// // MetaCD=1.0
// // Title: pgShowRUPA.js
// // Description: Consulta de RUPA
// // Company: SCT
// // Author: JESR
// var cTitulo = "";
// var FRMListado = "";
// var frm;
// var aUsrDepto;
// var ICVETRAMITE = 0;
// var aNotifica = new Array();
// var lFirst = true;
//
// var cveDoc = -1;
//
// // SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
// function fBefLoad() {
// cPaginaWebJS = "pg111040033.js";
// cTitulo="HISTÓRICO ADV";
// fSetWindowTitle();
// }
// // SEGMENTO Definicin de la pgina (Definicin Mandatoria)
// function fDefPag() {
//
// cGPD += '<html><body bgcolor="" topmargin="0" leftmargin="0"
// onLoad="fOnLoad();">'
// + '<form name="form1" enctype="multipart/form-data" method="post"
// action="UploadDocHistorico">';
// fInicioPagina(cColorGenJSFolder);
// InicioTabla("", 0, "100%", "", "", "", "1");
// ITRTD("EEtiquetaC", 7, "", "", "center");
// TextoSimple(cTitulo);
// FTDTR();
// ITRTD("", 0, "", "", "top");
// InicioTabla("", 0, "100%", "100%", "center", "", ".1", ".1");
// ITRTD("ESTitulo1", 0, "100%", "100%", "center", "top");
// InicioTabla("", 0, "100%", "1", "center");
// ITRTD("EEtiquetaC", 0, "100%", "25", "center", "top");
// //TextoSimple("HISTÓRICO ADV");
//							
// FTDTR();
// ITRTD("", 0, "", "", "center");
// DinTabla("BarraWizard", "", 0, "", "90%", "center", "top", "", ".1", ".1");
// FTDTR();
// ITRTD("", 0, "", "", "center");
// DinTabla("TDBdy", "", 0, "90%", "", "center", "", "", ".1", ".1");
// FTDTR();
// ITRTD("", "", "", "40", "center");
// IFrame("IPanel", "515", "34", "Paneles.js");
// FTDTR();
// FinTabla();
// FTDTR();
// FinTabla();
// FTDTR();
// FinTabla();
//	
// Hidden("iNumSolicitud",-1);
// Hidden("iCveUsr",fGetIdUsrSesion());
//	
// fFinPagina();
// }
// function fAnexaDoc() {
//
// }
// // SEGMENTO Despus de Cargar la pgina (Definicin Mandatoria)
// function fOnLoad() {
// frm = document.forms[0];
// 
// theTable = (document.all) ? document.all.TDBdy : document
// .getElementById("TDBdy");
// tBdy = theTable.tBodies[0];
//
// tBarraWizard = document.getElementById("BarraWizard");
//
// //fDelTBdy();
// FRMPanel = fBuscaFrame("IPanel");
// FRMPanel.fSetControl(self, cPaginaWebJS);
// FRMPanel.fShow("Tra,");
// FRMPanel.fSetTraStatus("UpdateBegin");
// fNavega();
// //fShowDatos();
// }
// function fDelTBdyFIEL() {
// for (i = 0; tBdyFIEL.rows.length; i++) {
// tBdyFIEL.deleteRow(0);
// }
// }
// // LLAMADO al JSP especfico para la navegacin de la pgina
// function fNavega() {
//	
// frm.hdBoton.value="docHistorico";
// frm.hdFiltro.value = "";
// frm.hdOrden.value = "";
// frm.hdNumReg.value = "1000";
// fEngSubmite("pgDocsSolADV.jsp", "Listado");
//	
// }
// // RECEPCIN de Datos de provenientes del Servidor
// function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {
// if (cId == "Listado" && cError == "") {
// fShowDatos(aRes);
// fEnProceso(false);
// }
// }
//
// function fNuevo() {
// /*FRMPanel.fSetTraStatus("UpdateBegin");
// fBlanked();
// fDisabled(false);
// FRMListado.fSetDisabled(true);*/
// }
//
// function fGuardar() {
// if(fValidaTodo()==false){ // si no existieron errores
//		
// if(confirm("¿Está seguro que desea continuar con la información en
// pantalla?")){
// frm.action = "UploadDocHistorico?paramA="+frm.iCveUsr.value;//el id del
// usuario logeado
// fEnProceso(true);
// setTimeout(function(){frm.submit();},250);
// }
//		
// }
// }
//
// function fCancelar() {
//
// }
// // LLAMADO desde el Panel cada vez que se presiona al botn Borrar
// function fBorrar() {
// /*if (confirm(cAlertMsgGen + "\n\n Desea usted eliminar el registro?")) {
// fNavega();
// }*/
// }
//
// function fCarga(lValor) {
//
// }
//
// function fShowDatos(aRes) {
//
// while (tBarraWizard.rows.length > 0) {
// tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
// }
//
// tBarraWizard.className = "ETablaInfo";
// tBarraWizard.width = "90%";
//
// tRw = tBarraWizard.insertRow();
// tCell = tRw.insertCell();
// tCell.colSpan = 2;
// tCell.innerHTML = TextoSimple("HISTÓRICO ADV");
// tCell.className = "ETablaST";
//	
// for(var t=0; t < aRes.length;t++){
//		
// tRw = tBarraWizard.insertRow();
// tCell = tRw.insertCell();
// //tCell.colSpan = 2;
// tCell.innerHTML = "Última versión: ";
// tCell.className = "EEtiqueta";
// tCell = tRw.insertCell();
//		
// if(aRes[0][0]!=""){
// cveDoc=aRes[0][0];
// tCell.innerHTML = Liga("[Descargar Histórico]", "fShowHistoricoExcel();");
// }else{
// tCell.innerHTML = TextoSimple("No existen versiones anteriores.");
// }
//		
// tRw = tBarraWizard.insertRow();
// tCell = tRw.insertCell();
// //tCell.colSpan = 2;
// tCell.innerHTML = "Nueva versión: ";
// tCell.className = "EEtiqueta";
// tCell = tRw.insertCell();
//		
// tCell.innerHTML = '<input type="file" name="fileButonADV" size="25">';
// }
//		
// }
//
// function resetBloqueaGuardar(){
// bloqueaGuardar=false;
// }
//
// function validaAchivos(){
//	
// var valRet=false;
// var arrayFilesNames = fGetFilesValues(frm);
//	
// if (validaArchVacios(arrayFilesNames) == true){
// valRet=true;
// }
//	 
// if (validaDocExt(arrayFilesNames) == true){
// valRet=true;
// }
//	
// return valRet;
//	
// }
//
// function validaDocExt(arrayDocsName){
// for(var ab=0;ab<arrayDocsName.length;ab++){
//		
// var nomDoc= arrayDocsName[ab];
//		
// if(nomDoc!=""){
//		
// var arrNom = nomDoc.split('.');
// var tam=arrNom.length;
// var extDoc=arrNom[tam-1];
//			
// if(extDoc.toUpperCase()!="XLSX"){
// fAlert("\nExisten documentos que no cumplen con el formato XLSX. Favor de
// verificar los documentos.");
// return true;
// }
//			
// }
// }
// return false;
// }
//
// function validaArchVacios(arrayDocsName){
//	
// for(var ab=0;ab<arrayDocsName.length;ab++){
//			
// var nomDoc= arrayDocsName[ab];
//			
// if(nomDoc==""){
// fAlert("\nDebe subir todos los documentos para completar la operación.");
// return true;
// }
// }
// return false;
//		
// }
//
// function fValidaTodo() {
// return validaAchivos();
// }
//
// function getParamUsr(){
// return frm.iCveUsr.value;
// }
//
// function getCveDoc(){
// return cveDoc;
// }

// /PAGINA ORGINAL, DESCOMENTAR DESPUES DE QUE SE APRUEBE LA PANTALLA


/*
 * 
  ^(?:(?:31(\/)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$
 * */

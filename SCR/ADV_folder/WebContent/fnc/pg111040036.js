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

function getCveHist(){
	return frm.iCveHist.value;
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
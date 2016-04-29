// MetaCD=1.0
var cTitulo = "";
var FRMListado = "";
var frm;
var iPagAnt = 1;
var iPagAct = 1;
var lEditPersona = true;
var lEditDomPersona = true;
var FRMObj, FRMObj1, FRMObj2, FRMObj3, FRMObj4, FRMFiltro;
var lEditando = false;

var existePersona = false;
var iCvePersonaSel = -1;
var buscarRepl = false;
var iCveRepresentado = -1;
var iniciaTramite = false;


fWrite(JSSource("Carpetas.js"));

function fBefLoad() {
	cPaginaWebJS = "pg111010011.js";
	
	if(top.opener){
		if(top.opener.getBuscarRepl)
			buscarRepl=top.opener.getBuscarRepl();
	
		if(top.opener.getICveRepresentado)
			iCveRepresentado=top.opener.getICveRepresentado();
		
		if(top.opener.getIniciaTramite)
			iniciaTramite =top.opener.getIniciaTramite();
	
	}
	if (top.fGetTituloPagina) 
		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	
	
	fSetWindowTitle();
}

function fDefPag() { // Define la página a ser mostrada
	fInicioPagina(cColorGenJSFolder);
	JSSource("pais.js");
	JSSource("estados.js");
	InicioTabla("", 0, "100%", "100%", "", "");
	ITRTD("EEtiquetaC", 0, "", "100", "", "");
		InicioTabla("ETablaInfo", 0, "0", "", "center", "", 1);
			ITRTD("ETablaST", 16, "", "", "center");
				TextoSimple("BÚSQUEDA DE SOLICITANTE");
			FTDTR();
			ITR();
				TDEtiCampo(false, "EEtiqueta", 0, "R.F.C.:", "cRFC", "", 14, 13, " R.F.C.",
						"fMayus(this);",
						" onKeyPress='return fCheckForEnter(event, this, window);' ", "",
						false, "EEtiquetaL", 3);
				Hidden("cRPA", "");
				Hidden("cCURP", "");
			FITR();
				TDEtiCampo(false, "EEtiqueta", 0, "Nombre o Razón Social:",
						"cNomRazonSocial", "", 87, 80, " Nombre o Razon Social",
						"fMayus(this);",
						" onKeyPress='return fCheckForEnter(event, this, window);' ", "",
						false, "EEtiquetaL", 11);
			FITR();
				TDEtiCampo(false, "EEtiqueta", 0, "Ap. Paterno:", "cApPaterno", "", 33, 30,
						" Apellido Paterno", "fMayus(this);",
						" onKeyPress='return fCheckForEnter(event, this, window);' ", "",
						false, "EEtiquetaL", 7);
			TDEtiCampo(false, "EEtiqueta", 0, "Ap. Materno:", "cApMaterno", "", 33, 30,
					" Apellido Materno", "fMayus(this);",
					" onKeyPress='return fCheckForEnter(event, this, window);' ", "",
					false, "EEtiquetaL", 3);
			FTR();
			FITD("EEtiquetaC", 16);
			BtnImg("vgbuscar", "lupa", "fBuscaDatos();", "");
			FTDTR();
		FinTabla();
		FTDTR();
		ITRTD("EEtiquetaC", 0, "", "150", "", "");
		InicioTabla("", 0, "95%", "50%", "center");
			ITRTD("", 0, "", "15", "center", "top");
				IFrame("IListado20", "100%", "150", "Listado.js", "yes", true);
			FTDTR();
		FinTabla(); // Despliegue de búsqueda
		ITRTD("EEtiquetaC", 0, "", "", "", "");
		InicioTabla("", 0, "100%", "100%", "center");
		ITRTD("", 0, "100%", "100%", "center", "middle");
			var cCadTitulos = "", cCadPaginas = "";
			
			if(buscarRepl!=true&&iniciaTramite!=true){
				cCadTitulos += "Datos Generales<br>de la Persona|Representante<br>Legal|";
				cCadPaginas += "pg111010011A.js|pg111010013.js|";
			}else{
				cCadTitulos += "Datos Generales<br>de la Persona|";
				cCadPaginas += "pg111010011A.js|";
			}
			
			fDefCarpeta(cCadTitulos, cCadPaginas + "false", "PEM", "99%", "99%", true);
		FTDTR();
	FinTabla(); // Folder
	FTDTR();
	FinTabla();
	Hidden("cNombreFRM");
	fFinPagina();

}

function fOnLoad() { // Carga informacion al mostrar la página.
	
	
	
	FRMListado = fBuscaFrame("IListado20");
	FRMListado.fSetControl(self);
	FRMObj = frm = document.forms[0];
	FRMObj1 = fBuscaFrame("PEM1");
	
	if(buscarRepl!=true)
		FRMObj2 = fBuscaFrame("PEM2");
	
	fPagFolder(1);
	iPagAct = 1;
	fListado();
	if (FRMObj.cRFC)
		FRMObj.cRFC.focus();
}

function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave) {

	if (cId == "cIdBuscaPersona" && cError == "") {
		for ( var a = 0; a < aRes.length; a++) {
			if (parseInt(aRes[a][3]) == 1) {
				aRes[a].push("FÍSICA");
			} else if (parseInt(aRes[a][3]) == 2) {
				aRes[a].push("MORAL");
			}
		}
		FRMListado.fSetListado(aRes);
		FRMListado.fShow();
	}
}

function fLimpiaSolicitante() {
	FRMObj1.resetAllForm();
	FRMObj1.setEstausPanel("AddOnly");
}

function fFolderOnChange(iPag) { // iPag indica a la página que se desea
									// cambiar
	iPagAct = iPag;
	if (iPag == 1)
		frm.cNombreFRM.value = "PEM1";

	if (iPag == 2) {
		frm.cNombreFRM.value = "PEM2";
		iPagAnt = 1;
		if (existePersona == true) {
			return true;
		} else {
			fAlert("Debe haber una persona seleccionada para poder asignar un Representante Legal.");
			return false;
		}
	}
}

function fSelReg(aDato) {
	if (parseInt(aDato[0]) > 0) {
		FRMObj1.setPersonaSel(cambioPersona(aDato));
		FRMObj1.setArrConsulta(aDato);
		
		if(FRMObj2){
			cargaRepresentantesAsignados();
		}
	}else{
		fPagFolder(1);
		fLimpiaSolicitante();
	}
}

function fNavega() {

	if (frm.cRFC.value == "" && frm.cApPaterno.value == ""
			&& frm.cApMaterno.value == "" && frm.cNomRazonSocial.value == "") {
		fAlert("Debe proporcionar al menos un campo para realizar la búsqueda");
		return;
	}
	frm.hdFiltro.value = "";

	if (frm.cRFC.value != "")
		frm.hdFiltro.value += frm.hdFiltro.value == "" ? " GRLPERSONA.CRFC LIKE '%"
				+ frm.cRFC.value + "%' "
				: " AND GRLPERSONA.CRFC LIKE '%" + frm.cRFC.value + "%'";

	if (frm.cNomRazonSocial.value != "")
		frm.hdFiltro.value += frm.hdFiltro.value == "" ? " GRLPERSONA.CNOMRAZONSOCIAL LIKE '%"
				+ frm.cNomRazonSocial.value + "%' "
				: " AND GRLPERSONA.CNOMRAZONSOCIAL LIKE '%"
						+ frm.cNomRazonSocial.value + "%'";

	if (frm.cApPaterno.value != "")
		frm.hdFiltro.value += frm.hdFiltro.value == "" ? " GRLPERSONA.CAPPATERNO LIKE '%"
				+ frm.cApPaterno.value + "%' "
				: " AND GRLPERSONA.CAPPATERNO LIKE '%" + frm.cApPaterno.value
						+ "%'";

	if (frm.cApMaterno.value != "")
		frm.hdFiltro.value += frm.hdFiltro.value == "" ? " GRLPERSONA.CAPMATERNO LIKE '%"
				+ frm.cApMaterno.value + "%' "
				: " AND GRLPERSONA.CAPMATERNO LIKE '%" + frm.cApMaterno.value
						+ "%'";
	
	if (buscarRepl == true)
		frm.hdFiltro.value += frm.hdFiltro.value == "" ? " GRLPERSONA.ICVEPERSONA != "+ iCveRepresentado : " AND GRLPERSONA.ICVEPERSONA != "+ iCveRepresentado;
		

	frm.hdOrden.value = " GRLPERSONA.CRFC ASC";
	frm.hdBoton.value = "buscaPersona";

	fEngSubmite("pgGRLPersonaADV.jsp", "cIdBuscaPersona");

}

function fListado() {
	FRMListado.fSetTitulo("R.F.C.,Tipo, Nombre o Razón Social,");
	FRMListado.fSetCampos("2,35,4,");
	FRMListado.fSetAlinea("center,center,left,");
}

function fBuscaDatos() {
	fNavega();
}

function setExistePersona(val) {
	existePersona = val;
}

function setIcvePersona(icvePersona) {
	iCvePersonaSel = icvePersona;
}

function cambioPersona(aDato) {
	var obj = {};

	obj.iCvePersona = aDato[0];
	obj.iCveDomicilio = aDato[1];
	obj.cRFC = aDato[2];
	obj.iTipo = aDato[3];
	obj.cNomRazonSocial = aDato[4];
	obj.cApPaterno = aDato[5];
	obj.cApMaterno = aDato[6];
	obj.cCorreoE = aDato[7];
	obj.cCalle = aDato[8];
	obj.cNumExterior = aDato[9];
	obj.cNumInterior = aDato[10];
	obj.cColonia = aDato[11];
	obj.cCodPostal = aDato[12];
	obj.cTelefono = aDato[13];
	obj.iCveEntidadFed = aDato[14];
	obj.iCveMunicipio = aDato[15];
	obj.iCveLocalidad = aDato[16];

	return obj;
}

function cambioRepL(aDato) {
	var obj = {};
	obj.iCvePersona = aDato[17];
	obj.iCveDomicilio = aDato[18];
	obj.cRFC = aDato[19];
	obj.iTipo = aDato[20];
	obj.cNomRazonSocial = aDato[21];
	obj.cApPaterno = aDato[22];
	obj.cApMaterno = aDato[23];
	obj.cCorreoE = aDato[24];
	obj.cCalle = aDato[25];
	obj.cNumExterior = aDato[26];
	obj.cNumInterior = aDato[27];
	obj.cColonia = aDato[28];
	obj.cCodPostal = aDato[29];
	obj.cTelefono = aDato[30];
	obj.cPais = aDato[31];
	obj.cEntidad= aDato[32];
	obj.cMunicipio = aDato[33];
	obj.cLocalidad = aDato[34];
	obj.iCveRepresentado = aDato[0];
	return obj;
}

function getBuscarRepL(){
	return buscarRepl;
}

function fEnterLocal(theObject, theEvent, theWindow){
	  objName = theObject.name;
	  if (objName == 'cRFC' || objName == 'cNomRazonSocial' || objName == 'cApPaterno' || objName == 'cApMaterno'){
	    fMayus(theObject);
	    if(theObject.value.length > 2)
	       fBuscaDatos();
	    else
	       fAlert("Teclee almenos 3 letras por opción de búsqueda");
	  }
}

function fBuscaPostGuardar(personaGuardada){
	frm.cRFC.value= personaGuardada.cRFC;
	frm.cNomRazonSocial.value= personaGuardada.cNomRazonSocial;
	
	if(parseInt(personaGuardada.iTipo)==1){
		frm.cApPaterno.value= personaGuardada.cApPaterno;
		frm.cApMaterno.value= personaGuardada.cApMaterno;
	}else{
		frm.cApPaterno.value="";
		frm.cApMaterno.value= "";
	}
	
	fBuscaDatos();
}

function getIniciaTramite(){
	return iniciaTramite;
}


function cargaRepresentantesAsignados(){
	if(FRMObj2){
		FRMObj2.setICveRepresentado(iCvePersonaSel);
		FRMObj2.fBuscaAsignados();
	}
}

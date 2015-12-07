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
	cPaginaWebJS = "pg111040031.js";
	if (top.fGetTituloPagina)
		cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO") ? "REPORTES ADV"
			: cTitulo;
	fSetWindowTitle();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
//	FITD("EEtiqueta",0);
//    Radio(true,"iTipo",1,false,"","","",'onClick="fOnChange(false);"');
//  FITD("EEtiquetaL",0);
//    Etiqueta("Fisica","EEtiquetaL","Física");
//  FITD("EEtiqueta",0);
	
	fInicioPagina(cColorGenJS);
	
	InicioTabla("ETablaInfo", 0, "100%", "", "center", "", "1");
	ITRTD("", 0, "", "", "center");

		InicioTabla("ETablaInfo", 0, "50%", "", "center", "", "1");
	
			ITRTD("ETablaST",8,"100%","","center");
				TextoSimple("Rango de fechas");
			FTDTR();
			ITR("",0,0,"center","center",0,"");
			ITD();
			FTD();
				TDEtiCampo(true, "EEtiqueta", 0, " Fecha de registro Inicio:",
						"dtInit", "", 10, 10, " Fecha de registro Inicio",
						"fMayus(this);", "", "", false, "center", 0);
				
				TDEtiCampo(true, "EEtiqueta", 0, " Fecha de registro Fin:",
						"dtEnd", "", 10, 10, " Fecha de registro Fin",
						"fMayus(this);", "", "", false, "center", 0);	
			FTR();
			ITD();
			FTD();
			ITRTD("ETablaST",8,"100%","","center");
				TextoSimple("Reporte de Tipo de Aprovechamiento por:");
			FTDTR();
			ITR("",0,0,"center","center",0,"");
			
				TDEtiRadio(false,"EEtiqueta",0,"Autopista","iTipoTram",1,true, "", "", true, 'onClick="changeTipoTram(1);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Concesionario","iTipoTram",2,false, "", "", true, 'onClick="changeTipoTram(2);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Centro SCT","iTipoTram",3,false, "", "", true, 'onClick="changeTipoTram(3);"', "", "");
				ITD("EEtiqueta",5,"","","center","center");
    				Liga("Generar reporte tipo de aprovechamiento","fTipoTramite();","Generar reporte tipo de aprovechamiento");
    			FTD();
			FTR();
			ITRTD("ETablaST",8,"100%","","center");
				TextoSimple("Reporte de Estatus de solicitud por:");
			FTDTR();
			ITR("",0,0,"center","center",0,"");		
				TDEtiRadio(false,"EEtiqueta",0,"Año","iEstatus",1,true, "", "", true, 'onClick="changeEstatus(1);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Autopista","iEstatus",2,false, "", "", true, 'onClick="changeEstatus(2);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Concesionario","iEstatus",3,false, "", "", true, 'onClick="changeEstatus(3);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Centro SCT","iEstatus",4,false, "", "", true, 'onClick="changeEstatus(4);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Tipo de aprovechamiento","iEstatus",5,false, "", "", true, 'onClick="changeEstatus(5);"', "", "");
				ITD("EEtiqueta",3,"","","center","center");
					Liga("Generar reporte estatus de solicitudes","fEstatus();","Generar reporte estatus de solicitudes");
				FTD();
			FTR();
			ITRTD("ETablaST",8,"100%","","center");
				TextoSimple("Reporte de Solicitudes En Proceso al Año por:");
				FTDTR();
			ITR("",0,0,"center","center",0,"");		
				TDEtiRadio(false,"EEtiqueta",2,"Tipo aprovechamiento","iSolxAnio",1,true, "", "", true, 'onClick="changeAnio(1);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Autopista","iSolxAnio",2,false, "", "", true, 'onClick="changeAnio(2);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Concesionario","iSolxAnio",3,false, "", "", true, 'onClick="changeAnio(3);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Centro SCT","iSolxAnio",4,false, "", "", true, 'onClick="changeAnio(4);"', "", "");
				ITD("EEtiqueta",3,"","","center","center");
					Liga("Generar reporte solicitudes en proceso","fSolxAnio();","Generar reporte solicitudes en proceso");
				FTD();
			FTR();
			ITRTD("ETablaST",8,"100%","","center");
			TextoSimple("Reporte de Solicitudes Otorgadas al Año por:");
			FTDTR();
			ITR("",0,0,"center","center",0,"");		
				TDEtiRadio(false,"EEtiqueta",2,"Tipo aprovechamiento","iSolxAnioOt",1,true, "", "", true, 'onClick="changeAnioOt(1);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Autopista","iSolxAnioOt",2,false, "", "", true, 'onClick="changeAnioOt(2);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Concesionario","iSolxAnioOt",3,false, "", "", true, 'onClick="changeAnioOt(3);"', "", "");
				TDEtiRadio(false,"EEtiqueta",0,"Centro SCT","iSolxAnioOt",4,false, "", "", true, 'onClick="changeAnioOt(4);"', "", "");
				ITD("EEtiqueta",3,"","","center","center");
					Liga("Generar reporte solicitudes en otorgado","fSolxAnioOt();","Generar reporte solicitudes otorgadas");
				FTD();
			FTR();
			ITRTD("ETablaST",8,"100%","","center");
				TextoSimple("Reporte de tiempos de atención:");
			FTDTR();
			ITR("",0,0,"center","center",0,"");		
				ITD("EEtiqueta",4,"","","center","center");
					Liga("Generar reporte tiempos de atención","fTiempos();","Generar reporte tiempos de atención");
				FTD();
			FTR();


		FinTabla();    
	    FTDTR();    
	 FinTabla();
	
	Hidden("iEjercicio", "");
	Hidden("iNumSolicitud", "");
	Hidden("iTipoTram", -1);
	Hidden("iSolxAnio", -1);
	Hidden("iSolxAnioOt", -1);
	Hidden("iCveUsuario", fGetIdUsrSesion());
	fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad() {
	 frm = document.forms[0];
	 frm.iTipoTram.value=1;
	 frm.iSolxAnio.value=1;
	 frm.iSolxAnioOt.value=1;
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


}


function fTipoTramite() {
	if(frm.dtInit.value!=""&&frm.dtEnd.value!=""){
	  cClavesModulo="3,";
	  
	  switch(frm.iTipoTram.value){
		  case 1:
			  cNumerosRep="57,";
			  break;
		  case 2:
			  cNumerosRep="58,";
			  break;
		  case 3:
			  cNumerosRep="59,";
			  break;
	  }
	  
 	  cFiltrosRep= frm.dtInit.value+","+frm.dtEnd.value+"," +cSeparadorRep;
 	  fReportes();
	}else{
		fAlert("- Debe proporcionar el rango de fechas para generar el reporte.")
	}
	
}

function changeTipoTram(val){
	frm.iTipoTram.value = val;
}

function fSolxAnio() {
	if(frm.dtInit.value!=""&&frm.dtEnd.value!=""){
	  cClavesModulo="3,";
	  
	  switch(frm.iSolxAnio.value){
		  case 1:
			  cNumerosRep="60,";
			  break;
		  case 2:
			  cNumerosRep="61,";
			  break;
		  case 3:
			  cNumerosRep="62,";
			  break;
		  case 4:
			  cNumerosRep="63,";
			  break;
	  }
	  
 	  cFiltrosRep= frm.dtInit.value+","+frm.dtEnd.value+"," +cSeparadorRep;
 	  fReportes();
	}else{
		fAlert("- Debe proporcionar el rango de fechas para generar el reporte.")
	}
	
}

function changeAnio(val){
	frm.iSolxAnio.value = val;
}


function fSolxAnioOt() {
	if(frm.dtInit.value!=""&&frm.dtEnd.value!=""){
	  cClavesModulo="3,";
	  
	  switch(frm.iSolxAnioOt.value){
		  case 1:
			  cNumerosRep="64,";
			  break;
		  case 2:
			  cNumerosRep="65,";
			  break;
		  case 3:
			  cNumerosRep="66,";
			  break;
		  case 4:
			  cNumerosRep="67,";
			  break;
	  }
	  
 	  cFiltrosRep= frm.dtInit.value+","+frm.dtEnd.value+"," +cSeparadorRep;
 	  fReportes();
	}else{
		fAlert("- Debe proporcionar el rango de fechas para generar el reporte.")
	}
	
}

function changeAnioOt(val){
	frm.iSolxAnioOt.value = val;
}


function fTiempos() {
	if(frm.dtInit.value!=""&&frm.dtEnd.value!=""){
	  cClavesModulo="3,";
	  cNumerosRep="68,";
 	  cFiltrosRep= frm.dtInit.value+","+frm.dtEnd.value+"," +cSeparadorRep;
 	  fReportes();
	}else{
		fAlert("- Debe proporcionar el rango de fechas para generar el reporte.");
	}
	
}

function changeEstatus(val){
	frm.iEstatus.value = val;
}


function fEstatus() {
	if(frm.dtInit.value!=""&&frm.dtEnd.value!=""){
	  cClavesModulo="3,";
	  
	  switch(frm.iEstatus.value){
		  case 1:
			  cNumerosRep="70,";
			  break;
		  case 2:
			  cNumerosRep="69,";
			  break;
		  case 3:
			  cNumerosRep="71,";
			  break;
		  case 4:
			  cNumerosRep="72,";
			  break;
		  case 5:
			  cNumerosRep="73,";
			  break;
	  }
	  
 	  cFiltrosRep= frm.dtInit.value+","+frm.dtEnd.value+"," +cSeparadorRep;
 	  fReportes();
	}else{
		fAlert("- Debe proporcionar el rango de fechas para generar el reporte.")
	}
	
}
// MetaCD=1.0 
// Title: pgShowRUPA.js
// Description: Consulta de RUPA
// Company: SCT 
// Author: JESR
var cTitulo = "";
var FRMListado = "";
var frm;
var msgErr="";
var cPagRet;

// SEGMENTO antes de cargar la pgina (Definicin Mandatoria)
function fBefLoad() {
	cPaginaWebJS = "pgDatosPermiso.js";
	cPagRet="pgDatosPermiso";
	cTitulo="INFORMACIÓN PARA PERMISOS OTORGADOS";
	fSetWindowTitle();
}
// SEGMENTO Definicin de la pgina (Definicin Mandatoria)
function fDefPag(){
	   fInicioPagina(cColorGenJS);

	   InicioTabla("ETablaInfo",0,"75%","","center","",1);
	   
		   ITRTD("ETablaST",6,"100%","","center");
	       	TextoSimple("DATOS PARA OFICIO CONCESIONARIO");
	       FTDTR();
	   
	       ITR();
	         TDEtiCampo(false,"EEtiqueta",0,"Folio oficio concesionario:","cFolConce","",18,20,"Folio oficio concesionario","fMayus(this)","","",true,"",0);
	       FTR();
	      
     	 ITRTD("ETablaST",6,"100%","","center");
       		TextoSimple("DATOS PARA OFICIO CENTRO SCT");
         FTDTR();
         
         ITR();
         	TDEtiCampo(false,"EEtiqueta",0,"Folio oficio Centro SCT:","cFolSCT","",18,20,"Folio oficio Centro SCT","fMayus(this)","","",true,"",0);
         	TDEtiCampo(false,"EEtiqueta",0,"Nombre Director General Centro SCT:","cNomDGSCT","",18,50,"Nombre Director General Centro SCT","fMayus(this)","","",true,"",0);
         FTR();
         
     	 ITRTD("ETablaST",6,"100%","","center");
       		TextoSimple("DATOS PARA OFICIO PERMISIONARIO");
         FTDTR();
         
         ITR();
         	TDEtiCampo(false,"EEtiqueta",0,"Folio oficio permisionario:","cFolPerm","",18,20,"Folio oficio permisionario","fMayus(this)","","",true,"",0);
         FTR();

     	 ITRTD("ETablaST",6,"100%","","center");
       		TextoSimple("DATOS PARA OFICIO PERMISIONARIO - CENTRO SCT");
         FTDTR();
	       
         ITR();
         	TDEtiCampo(false,"EEtiqueta",0,"Folio oficio permisionario - Centro SCT:","cFolSCTPerm","",18,20,"Folio oficio permisionario - Centro SCT","fMayus(this)","","",true,"",0);
         	TDEtiCampo(false,"EEtiqueta",0,"Nombre Director General Centro SCT:","cNomDGSCTPerm","",18,50,"ombre Director General Centro SCT","fMayus(this)","","",true,"",0);
         FTR();

     	 ITRTD("ETablaST",6,"100%","","center");
       		TextoSimple("DATOS PARA PERMISO");
         FTDTR();
         
         ITR();
         	TDEtiCampo(false,"EEtiqueta",0,"Volante:","cVolante","",18,20,"Volante","fMayus(this)","","",true,"",0);
         	TDEtiCampo(false,"EEtiqueta",0,"Folio permiso:","cFolPermiso","",18,20,"Folio permiso","fMayus(this)","","",true,"",0);
         FTR();
         
         ITR();
         	TDEtiCampo(false,"EEtiqueta",0,"Director General Desarrollo Carretero:","cDGDC","",18,50,"Director General Desarrollo Carretero","fMayus(this)","","",true,"",0);
	      	TDEtiCampo(false,"EEtiqueta",0,"Numeral referencia:","cNumeral","",18,20,"Numeral referencia","fMayus(this)","","",true,"",0);
	      	TDEtiCampo(false,"EEtiqueta",0,"Plazo:","cPlazo","",18,18,"Plazo","fMayus(this)","","",true,"",0);
	     FTR();
	                
         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Información referida a el Acta Constitutiva de la Empresa:",75,4,"cParraf1","","Información referida a el Acta Constitutiva de la Empresa","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();

         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Información relacionada al Poder Notarial:",75,4,"cParraf2","","Información relacionada al Poder Notarial","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();
         
         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Información relacionada a la solicitud del Permiso:",75,4,"cParraf3","","Información referida a el Acta Constitutiva de la Empresa","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();
         
         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Información relacionada al Dictamen de Factibilidad:",75,4,"cParraf4","","Información relacionada al Dictamen de Factibilidad","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();

         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Información relacionada a la Revisión DGST:",75,4,"cParraf5","","Información relacionada a la Revisión DGST","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();

         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Información relacionada a la conformidad del concesionario:",75,4,"cParraf6","","Información relacionada a la conformidad del concesionario","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();

         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Articulos para aprovechamiento:",75,4,"cArticulos","","Articulos para aprovechamiento","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();

         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Revisión DGDC:",75,4,"cRevDGDC","","Revisión DGDC","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();

         ITR();
         	TDEtiAreaTexto(false,"EEtiqueta",1,"Calidad de los Materiales:",75,4,"cCalMat","","Calidad de los Materiales","","fMayus(this);",'onchange="fMxTx(this,750);" onkeydown="fMxTx(this,750);" onblur="fMxTx(this,750);"',true,true,true,"ECampo",5);
         FTR();
	    FinTabla();
	    
	    InicioTabla("ETablaInfo",0,"75%","","center","",1);
		    ITRTD("", "", "", "40", "center");
		    	Liga("* Generar Oficios, Resolución, Permiso","fOficiosEnvio()","");
		    FTDTR();
		    ITRTD("", "", "", "40", "center");
				IFrame("IPanel", "515", "34", "Paneles.js");
			FTDTR();
		FinTabla();
	    
	     Hidden("hdLlave");
	     Hidden("hdSelect");
	     Hidden("iEjercicio");
	     Hidden("iNumSolicitud");
	     fFinPagina();
}

// SEGMENTO Despus de Cargar la pgina (Definicin Mandatoria)
function fOnLoad() {
	frm = document.forms[0];
 
	FRMPanel = fBuscaFrame("IPanel");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	
	if(top.opener){
		frm.iEjercicio.value = top.opener.fGetIEjercicio();
		frm.iNumSolicitud.value = top.opener.fGetINumSolicitud();
	}
	 
	FRMPanel.fSetTraStatus("UpdateBegin");
	
	fNavega();
}

// LLAMADO al JSP especfico para la navegacin de la pgina
function fNavega() {	
	if(	frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '' ){
		
		frm.hdBoton.value = "buscarDatosPermisos";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "Listado");
	}
}

// RECEPCIN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave, iID) {
	if (cId == "Listado" && cError != "") {
		fAlert("Ha ocurrido un error, intente más tarde.");
	}
	
	if (cId == "Listado" && cError == "") {
		fRellenaCampos(aRes);
	}
	
	if (cId == "guardaDatos" && cError == "") {
		fAlert("La información se ha guardado con éxito. Ahora puede generar los documentos.");
	}
}

function fRellenaCampos(aRes){
	if(aRes.length>0){ 
		frm.cFolConce.value= aRes[0][0];
		frm.cFolSCT.value= aRes[0][1];
		frm.cFolPerm.value= aRes[0][2];
		frm.cFolSCTPerm.value= aRes[0][3];
		frm.cNomDGSCT.value= aRes[0][4];
		frm.cNomDGSCTPerm.value= aRes[0][5];
		frm.cVolante.value= aRes[0][6];
		frm.cFolPermiso.value= aRes[0][7];
		frm.cDGDC.value= aRes[0][8];
		frm.cNumeral.value= aRes[0][9];
		frm.cParraf1.value= aRes[0][10];
		frm.cParraf2.value= aRes[0][11];
		frm.cParraf3.value= aRes[0][12];
		frm.cParraf4.value= aRes[0][13];
		frm.cParraf5.value= aRes[0][14];
		frm.cParraf6.value= aRes[0][15];
		frm.cArticulos.value= aRes[0][16];
		frm.cRevDGDC.value= aRes[0][17];
		frm.cPlazo.value= aRes[0][18];
		frm.cCalMat.value= aRes[0][19];
	}
}

function fNuevo() {
	FRMPanel.fSetTraStatus("UpdateBegin");
	fBlanked();
	fDisabled(false);
	FRMListado.fSetDisabled(true);
}

function fGuardar() {
if(	frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '' ){
		
		frm.hdBoton.value = "guardarDatosPermisos";
		frm.hdFiltro.value = "";
		frm.hdOrden.value = "";
		frm.hdNumReg.value = "1000";
		return fEngSubmite("pgDocsSolADV.jsp", "guardaDatos");
	}
}

function fCancelar() {
	if (FRMListado.fGetLength() > 0)
		FRMPanel.fSetTraStatus("Disabled");
	else
		FRMPanel.fSetTraStatus("AddOnly");
	fDisabled(true);
	FRMListado.fSetDisabled(false);
}

function fOficiosEnvio(){
	if(frm.iEjercicio.value!="" && frm.iNumSolicitud.value!=""){

//	      cClavesModulo="3,3,3,3,";
//		  cNumerosRep="38,39,40,41,";
//	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
//	 	  cFiltrosRep += cFiltrosRep + cFiltrosRep + cFiltrosRep; 
//	 	  fReportes();
//		  cClavesModulo="3,3,3,3,3,3,3,";
//		  cNumerosRep="27,49,50,51,52,55,56,";
//	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
//	 	  cFiltrosRep += cFiltrosRep + cFiltrosRep + cFiltrosRep + cFiltrosRep+ cFiltrosRep+ cFiltrosRep; 
//	 	  fReportes();
	 	  
	 	 cClavesModulo="3,3,3,3,3,3,";
		  cNumerosRep="27,49,50,51,52,56,";
	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
	 	  cFiltrosRep += cFiltrosRep + cFiltrosRep + cFiltrosRep + cFiltrosRep+ cFiltrosRep; 
	 	  fReportes();
	}else{
		fAlert('\nDebe buscar una solicitud existente.');
	} 
}

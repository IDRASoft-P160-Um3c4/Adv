// MetaCD=1.0
// Title: pg111010013.js
// Description: JS "Catalogo" de la entidad GRLRepLegal
// Company: Tecnologia InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var frm;
var lEditRepLegal = true;
var lEditando = false;

var cambioRepl = false;
var FRMPanel = null;
var iCveRepresentado=-1;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
	 cPaginaWebJS = "pg111010013.js";
	// if (top.fGetTituloPagina) {
	// cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
	// }
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag() {
	fInicioPagina(cColorGenJS);
		InicioTabla("", 0, "100%", "100%", "", "");
			ITRTD("EEtiquetaC", 0, "", "", "", "");
				InicioTabla("ETablaInfo", 0, "0", "", "center", "", 1);
					ITRTD("ETablaST", 12, "", "", "center");
						cTitulo = "REPRESENTANTE LEGAL ASIGNADO";
						TextoSimple(cTitulo);
					FTDTR();
					ITRTD("EEtiqueta", 0, "", "", "", "");
						TextoSimple("Tipo de Persona: ");
					FITD("EEtiqueta", 0);
						Radio(true, "iTipoRepL", 1, true, "", "", "", '');
					FITD("EEtiquetaL", 0);
						Etiqueta("Fisica", "EEtiquetaL", "Física");
					FITD("EEtiqueta", 0);
						Radio(true, "iTipoRepL", 2, false, "", "", "", '');
					FITD("EEtiquetaL", 0);
						Etiqueta("Moral", "EEtiquetaL", "Moral");
					FTDTR();
					ITR();
						TDEtiCampo(true, "EEtiqueta", 0, "R.F.C.:", "cRFCRepL", "", 14, 13, " R.F.C.",
								"fMayus(this);", "", "", false, "EEtiquetaL", 7);
						TDEtiCampo(true, "EEtiqueta", 3, "Correo Electrónico:", "cCorreoRepL", "",
								33, 50, "Correo Electrónico", "", "", "", false, "EEtiquetaL", 4);
					FITR();
						TDEtiAreaTexto(false, "EEtiqueta", 0, "Nombre Representante Legal:", 94, 2,
						"cNombreRepL", "", "Nombre Representante Legal", "fMayus(this);",
						"", "", false, false, false, "EEtiquetaL", 11);
					FITR();
							TDEtiAreaTexto(false, "EEtiqueta", 0, "Domicilio Persona:", 94, 2,
									"cDomRepL", "", "Domicilio Representante Legal", "fMayus(this);",
									"", "", false, false, false, "EEtiquetaL", 11);
						FTR();
				FinTabla();
			FTDTR();
			 ITRTD("",0,"","40","center","bottom");
		      IFrame("IPanelRepl","95%","34","Paneles.js");
		    FTDTR();
		FinTabla();
	Hidden("iCveRepL", -1);
	Hidden("iCveRepresentado", -1);
	fFinPagina();
}

function fOnLoad() {
	frm = document.forms[0];
	FRMPanel = fBuscaFrame("IPanelRepl");
	FRMPanel.fSetControl(self, cPaginaWebJS);
	FRMPanel.fShow("Tra,");
	FRMPanel.fSetTraStatus("AddOnly");
	fDisabled(true,"iCveRepL,iCveRepresentado,");
}

// LLAMADO al JSP especifico para la navegacion de la pagina
function fNavega() {

}

function fBuscaRepresentante() {
	
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
	
	  if(cError!=""){
	    fAlert (cError);
	  }
	  
	  if(cError==""&&cId=="cIdGuardaRepL"){
		  fAlert("Se ha asociado al representante legal con éxito.");
		  if(window.parent)
			  window.parent.fBuscaDatos();
	  }
	  
	  if(cError==""&&cId=="cIdBorrarRepL"){
		  fAlert("Se ha desasociado al representante legal con éxito.");
		  fBlanked();
		  if(window.parent)
			  window.parent.fBuscaDatos();
	  }
}


var repExistente=undefined;
var repNuevo=undefined;

function fSetRepresentante(representanteObj, existente) {
	
	if (parseInt(representanteObj.iCvePersona) > 0) {
		
		if(existente==true){
			cambioRepl = false;
			repExistente=representanteObj;
		}
		else{
			cambioRepl = true;
			repNuevo=representanteObj;
		}
		
		frm.iCveRepL.value = representanteObj.iCvePersona;
		fSetRadioValue(frm.iTipoRepL, representanteObj.iTipo);
		frm.cRFCRepL.value = representanteObj.cRFC; 
		frm.cCorreoRepL.value = representanteObj.cCorreoE;
		frm.cNombreRepL.value = representanteObj.cNomRazonSocial
				+ " " + representanteObj.cApPaterno
				+ " " + representanteObj.cApMaterno;
		frm.cDomRepL.value = "CALLE: " + representanteObj.cCalle + ", EXT. "
				+ representanteObj.cNumExterior + ", INT."
				+ representanteObj.cNumInterior + ", COL. "
				+ representanteObj.cColonia + ", C.P. "
				+ representanteObj.cCodPostal + ", "
				+ representanteObj.cLocalidad + ", " + representanteObj.cMunicipio + ", "
				+ representanteObj.cEntidad;
		
		if(existente==true)
			FRMPanel.fSetTraStatus("UpdateCompleteRepL");
		else
		if(cambioRepl==true)
			FRMPanel.fSetTraStatus("UpdateBegin");
		
		return;
	}else{
		frm.iCveRepL.value = -1;
		fSetRadioValue(frm.iTipoRepL, 1);
		frm.cRFCRepL.value =""; 
		frm.cCorreoRepL.value = "";
		frm.cNombreRepL.value = "";
		frm.cDomRepL.value = "";
		cambioRepl=false;
		repExistente=undefined;
		repNuevo=undefined;
		FRMPanel.fSetTraStatus("AddOnly");
	}
	
}

function fCancelar(){
	if(repExistente!=undefined){
		frm.iCveRepL.value = repExistente.iCvePersona;
		fSetRadioValue(frm.iTipoRepL, repExistente.iTipo);
		frm.cRFCRepL.value = repExistente.cRFC; 
		frm.cCorreoRepL.value = repExistente.cCorreoE;
		frm.cNombreRepL.value = repExistente.cNomRazonSocial
				+ " " + repExistente.cApPaterno
				+ " " + repExistente.cApMaterno;
		frm.cDomRepL.value = "CALLE: " + repExistente.cCalle + ", EXT. "
				+ repExistente.cNumExterior + ", INT."
				+ repExistente.cNumInterior + ", COL. "
				+ repExistente.cColonia + ", C.P. "
				+ repExistente.cCodPostal + ", "
				+ repExistente.cLocalidad + ", " + repExistente.cMunicipio + ", "
				+ repExistente.cEntidad;
	}else{
		frm.iCveRepL.value = -1;
		fSetRadioValue(frm.iTipoRepL, 1);
		frm.cRFCRepL.value =""; 
		frm.cCorreoRepL.value = "";
		frm.cNombreRepL.value = "";
		frm.cDomRepL.value = "";
	}
	cambioRepl=false;
	FRMPanel.fSetTraStatus("AddOnly");
	repNuevo=undefined;
}

function getBuscarRepl(){
	return true;
}

function getICveRepresentado(){
	return iCveRepresentado;
}

function fNuevo(){
	fAbreSubWindowPermisos("pg111010011","800","585");
}

function setICveRepresentado(cveRep){
	iCveRepresentado=cveRep;
	frm.iCveRepresentado.value=cveRep;
}

function fGuardar(){
	if(cambioRepl==true)
		if(confirm("Se asociará el representante legal a la persona, ¿Está seguro de continuar con los datos en pantalla?")==true){
			frm.hdBoton.value="GuardarRepL";
			fEngSubmite("pgGRLPersonaADV.jsp", "cIdGuardaRepL");
		}
}

function fBorrar(cveRep){
	if(frm.iCveRepL.value>0){
		if(confirm("Se eliminará la asociación con éste representante legal. ¿Está seguro de continuar?")==true){
				frm.hdBoton.value="BorrarRepL";
				fEngSubmite("pgGRLPersonaADV.jsp", "cIdBorrarRepL");
		}
	}else
		fAlert("La persona no tiene un representante legal asociado.");	
}

function resetAllForm(){
	frm.iCveRepL.value = -1;
	frm.iCveRepresentado.value=-1;
	fSetRadioValue(frm.iTipoRepL, 1);
	frm.cRFCRepL.value =""; 
	frm.cCorreoRepL.value = "";
	frm.cNombreRepL.value = "";
	frm.cDomRepL.value = "";
	cambioRepl=false;
	FRMPanel.fSetTraStatus("Disabled");
	repNuevo=undefined;
	repExistente=undefined;
}

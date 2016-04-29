// MetaCD=1.0
// Title: pg111010013.js
// Description: JS "Catalogo" de la entidad GRLRepLegal
// Company: Tecnologia InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkronaaaaa
var cTitulo = "";
var frm;
var lEditRepLegal = true;
var lEditando = false;

var cambioRepl = false;
var FRMPanel = null;
var iCveRepresentado=-1;
var guardarNuevo=false;
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
    
    InicioTabla("ETablaInfo",0,"80%","","center","",1);
        ITRTD("ETablaST",0,"","","center");
          cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"DATOS GENERALES":cTitulo;
          TextoSimple(cTitulo);
        FTDTR();
        ITRTD("EEtiquetaC",0,"","","","");
	        InicioTabla("ETablaInfo",0,"0","","center","",1);
		        ITRTD("EEtiqueta",0,"","","","");
			        	TextoSimple("Tipo de Persona: ");
			        FITD("EEtiqueta",0);
			        	Radio(false,"iTipoRepL",1,true,"","","");
			        FITD("EEtiquetaL",0);
			        	Etiqueta("Fisica","EEtiquetaL","Física");
			        FITD("EEtiqueta",0);
			        	Radio(false,"iTipoRepL",2,false,"","","");
			        FITD("EEtiquetaL",0);
			        	Etiqueta("Moral","EEtiquetaL","Moral");
			        FITD("EEtiquetaL",0);
		        FTR();
	        FinTabla();
        FTDTR();
        
        ITRTD("EEtiquetaC",0,"","","","");
	        InicioTabla("ETablaInfo",0,"0","","center","",1);
		        ITR("EEtiqueta",0,"","","","");
			        TDEtiCampo(false,"EEtiqueta",0,"R.F.C.:","cRFCRepL","",30,13," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",0);
			        TDEtiCampo(false,"EEtiqueta",0,"Nombre :","cNombreRepL","",60,80," Nombre ","fMayus(this);","","",false,"EEtiquetaL",0);
		        FTR();
		        FITR();
		        TDEtiAreaTexto(false, "EEtiqueta", 0, "Domicilio:", 80, 3, "cDomRepL", "",
		    			"Domicilio", "", "fMayus(this);", 'onkeydown="fMxTx(this,250);"', "",
		    			"", false, "", 8);
		        FTR();
	        FinTabla();
        FTDTR();
        
        ITRTD("",0,"","","","center","bottom");
	    InicioTabla("ETablaInfo", 0, "100%", "", "", "", 1);
	    ITRTD("EEtiquetaC", 0, "", "", "center");
	    Liga("Establecer como principal", "fRepresentantePrincipal();", "");
    FITD("EEtiquetaC",0);
    Liga("Eliminar asignación", "fEliminaAsignacion();", "");
		FTDTR();
		FinTabla();
	    FTDTR();
	    
	    ITRTD("",0,"","","","center","bottom");
	    InicioTabla("ETablaInfo", 0, "100%", "", "", "", 1);
	    ITRTD("", 0, "", "", "center");
	    IFrame("IPanelRepl","60%","34","Paneles.js");
		FTDTR();
		FinTabla();
	    FTDTR();
	    
	    ITRTD("", 0, "", "200", "center", "top");
	    InicioTabla("ETablaInfo", 0, "80%", "", "", "", 1);
	    ITRTD("ESTitulo", 0, "100%", "", "center");
		TextoSimple("REPRESENTANTES LEGALES ASIGNADOS");
		FTDTR();
		ITRTD("", 0, "100%", "", "center");
			IFrame("IListado", "95%", "170", "Listado.js", "yes", true);	
		FTDTR();
		FinTabla();
		FTDTR();
      FinTabla();
  	Hidden("iCveRepL", -1);
	Hidden("iCveRepresentado", -1);
  Hidden("hdiTipo",0);
      
  fFinPagina();

}

function fOnLoad() {
	frm = document.forms[0];
	FRMListado = fBuscaFrame("IListado");
	FRMListado.fSetControl(self);
	FRMListado.fSetTitulo("RFC, Nombre, Domicilio, Contacto, Principal,");
	FRMListado.fSetCampos("2,4,6,5,7,");
	FRMListado.fSetAlinea("left,left,left,left,center,");
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
	  
	  if(cError==""&&cId=="guardarRepresentanteEmergente"){
		  fAlert("Se ha asociado al representante legal con éxito.");
		  fCancelar();
		  guardarNuevo=false;
	  }
	  
	  if(cError==""&&cId=="eliminarAsociacionRepresentante"){
		  fAlert("Se ha desasociado al representante legal con éxito.");
		  fCancelar();
	  }
	  
	  if(cError==""&&cId=="setearRepresentantePrincipal"){
		  fAlert("Se ha establecido como principal al representante legal con éxito.");
		  fCancelar();
	  }
	  
	  if(cError==""&&cId=="buscaRepresentantesAsignados"){
		  	if(aRes.length>0){
		  		for(var i = 0;i<aRes.length; i++){
		  			if(aRes[i][7]==1){
		  				aRes[i][7]="Si";
		  			}else{
		  				aRes[i][7]="";
		  			}
		  			
		  		}
		  	}
		  	
			FRMListado.fSetListado(aRes);
			FRMListado.fShow();
	  }
	  
	  if(cError==""&&cId=="buscaRepresentanteEmergente"){
		  	if(aRes.length>0){
		  		setRepresentanteEmergente(aRes[0]);
		  	}else{
		  		fAlert("\n-La persona seleccionada ya esta asignada como representante legal o es una persona inválida.");
		  		fCancelar();
		  	}
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
	guardarNuevo=true;
}

function setICveRepresentado(cveRep){
	iCveRepresentado=cveRep;
	frm.iCveRepresentado.value=cveRep;
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
	fSetRadioValue(frm.iTipoRepL, 1);
	frm.cRFCRepL.value =""; 
	frm.cNombreRepL.value = "";
	frm.cDomRepL.value = "";
	FRMPanel.fSetTraStatus("AddOnly");
	guardarNuevo=false;
}

function fBuscaAsignados(){
	setTimeout(function(){
		if(frm.iCveRepresentado.value>0){
			frm.hdBoton.value="buscaRepresentantesAsignados";
			fEngSubmite("pgGRLPersonaADV.jsp", "buscaRepresentantesAsignados");
		}	
	},500);
		
}

function fSelReg(aDato) {
	if (parseInt(aDato[0]) > 0) {
		seleccionaReplegalListado(aDato);
	}else{
		fLimpiaRepLegalLoc();
	}
}

function fCancelar() {	
	var aux = frm.iCveRepresentado.value;
	fLimpiaRepLegalLoc();
	guardarNuevo=false;
	frm.iCveRepresentado.value=aux;
	fBuscaAsignados();
}

function seleccionaReplegalListado(aDato){
	frm.iCveRepL.value = aDato[0];
	fSetRadioValue(frm.iTipoRepL, aDato[8]);
	frm.cRFCRepL.value =aDato[2]; 
	frm.cNombreRepL.value = aDato[4];
	frm.cDomRepL.value = aDato[6];
}

function seleccionaReplegalEmergente(iCveNuevoRepL){
	if(iCveNuevoRepL>0){
		setTimeout(function(){frm.iCveRepL.value = iCveNuevoRepL;
		frm.hdBoton.value="buscaRepresentanteEmergente";
		fEngSubmite("pgGRLPersonaADV.jsp", "buscaRepresentanteEmergente");
		},500);
	}
}


function setRepresentanteEmergente(aDato){
	frm.iCveRepL.value = aDato[0];
	fSetRadioValue(frm.iTipoRepL, aDato[3]);
	frm.cRFCRepL.value =aDato[2]; 
	frm.cNombreRepL.value = aDato[4];
	frm.cDomRepL.value = aDato[6];
	FRMPanel.fSetTraStatus("UpdateBegin");
}

function fLimpiaRepLegalLoc(){
	resetAllForm();
}

function fGuardar(){
	if( guardarNuevo==true &&frm.iCveRepL.value>0&&frm.iCveRepresentado.value>0&&confirm("\n-Se asignará la persona como representante legal.\nDesea continuar con la información en pantalla?")){
	frm.hdBoton.value="guardarRepresentanteEmergente";
	fEngSubmite("pgGRLPersonaADV.jsp", "guardarRepresentanteEmergente");
}
	}

function fGuardarA(){
}

function fRepresentantePrincipal(){
	if(guardarNuevo==false&&frm.iCveRepL.value>0&&frm.iCveRepresentado.value>0&&confirm("\n-Se asignará el representante legal seleccionado como principal.\nDesea continuar con la información en pantalla?")){
		frm.hdBoton.value="setearRepresentantePrincipal";
		fEngSubmite("pgGRLPersonaADV.jsp", "setearRepresentantePrincipal");
	}	
}

function fEliminaAsignacion(){
	if(guardarNuevo==false&&frm.iCveRepL.value>0&&frm.iCveRepresentado.value>0&&confirm("\n-Se eliminará la asociación con el representante legal seleccionado." +
			"\nSi continua no podrá ver los trámites registrados con este representante legal en las pantallas de seguimiento, pero podrá generar los documentos." +
			"\n¿Desea continuar con la información en pantalla?")){
		frm.hdBoton.value="eliminarAsociacionRepresentante";
		fEngSubmite("pgGRLPersonaADV.jsp", "eliminarAsociacionRepresentante");
	}
}

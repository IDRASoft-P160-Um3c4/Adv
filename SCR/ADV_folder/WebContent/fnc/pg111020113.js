// MetaCD=1.0 
// Title: pg....js
// Description: JS "Catálogo/Proceso" de la entidad MODTabla
// Company: S.C.T. 
// Author: 
var cTitulo = "";
var FRMListado = "";
var frm;
var form;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
    cPaginaWebJS = "pg111020113.js";
    if (top.fGetTituloPagina) {
	cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
    }
}
function fDefPag() {
    fInicioPagina(cColorGenJS);
    InicioTabla("", 0, "100%", "", "", "", "1");
        InicioTabla("", 0, "100%", "", "", "", "1"); 
            ITRTD("",0,"","100%","center","top");
              ITD("","","","","center"); 
                BtnImg("Nombre", "guardar", "fGuardaSolicitante();");
              FTD();
            FTDTR();
        FinTabla();
        InicioTabla("", 0, "90%", "90%", "center", "", "1");
            ITRTD("",0,"90%","90%","center","top");
                IFrame("IPersona","100%","100%","pg111010011.js","yes",true);
            FTDTR();
        FinTabla();
        Hidden("iEjercicio");
        Hidden("iNumSolicitud");
        Hidden("iCvePersona");
        Hidden("iCveDomPersona");
        Hidden("iCveRepLegal");
        Hidden("iCveDomRepLegal");        
        Hidden("cRFC");
        Hidden("cRFCRep");
        Hidden("cRPA");
        FinTabla();
    fFinPagina();
}

function fOnLoad() {
    frm = document.forms[0];
}
function fNavega() {}
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave) {
    if(cId=="cIdSolicitante" && cError == ""){
	alert("Solicitante agregado correctamente");
	FRM1 = fBuscaFrame("PAG1");
	FRM1.fBuscar();
	window.parent.fPagFolder(1);
    }
}
function fImprimir() {
    self.focus();
    window.print();
}
function fSetCampo(campo,valor1,valor2){
    elCampo = null;
    elCampo = eval('frm.' + campo+"");
    elCampo.value = valor1;
    elCampo1 = null;
    FRMPersona = fBuscaFrame("IPersona");
    elCampo1 = eval('FRMPersona.FRMObj.'+campo);
    elCampo1.value = valor1;
    fCargaPaices();
    //FRMPersona.fNavega();
}

function fGuardaSolicitante(){

    frm.iCvePersona.value = 0;
    frm.iCveDomPersona.value = 0;
    frm.iCveRepLegal.value = 0;
    frm.iCveDomRepLegal.value = 0;    
    
    FRMPersona = fBuscaFrame("IPersona");
    FRM1 = fBuscaFrame("PAG1");
    frm.iEjercicio.value = FRM1.frm.iEjercicio.value>0?FRM1.frm.iEjercicio.value:0;
    frm.iNumSolicitud.value = FRM1.frm.iNumSolicitud.value>0?FRM1.frm.iNumSolicitud.value:0;
    frm.iCvePersona.value = FRMPersona.FRMObj.Persona_iCvePersona.value>0?FRMPersona.FRMObj.Persona_iCvePersona.value:0;
    frm.iCveDomPersona.value = FRMPersona.FRMObj.Persona_iCveDomicilio.value>0?FRMPersona.FRMObj.Persona_iCveDomicilio.value:0;
    frm.iCveRepLegal.value = FRMPersona.FRMObj.RepLegal_iCvePersona.value>0?FRMPersona.FRMObj.RepLegal_iCvePersona.value:0;
    frm.iCveDomRepLegal.value = FRMPersona.FRMObj.RepLegal_iCveDomicilio.value>0?FRMPersona.FRMObj.RepLegal_iCveDomicilio.value:0;
    frm.hdBoton.value = "agregaSolicitante";
    fEngSubmite("pgTRARegSolicitud.jsp","cIdSolicitante");
}

function fCargaPaices(){
    FRMPersona.fCargaCatalogoPaises();
}
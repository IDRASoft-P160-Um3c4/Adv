// MetaCD=1.0
// Title: pg111010014.js
// Description: JS "Catálogo" de la entidad GRLDomicilio
// Company: Tecnología InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm, FRMObj;
var lEditDomRepLegal  = true;
var lEditando = false;
var aResDomRepLegal;
var DomOriginal;
var RepOriginal;
var tipoDomActual;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010014.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","100%","","");
    // Tabla de listado de representantes legales asignados
    ITRTD("EEtiquetaC",0,"","","","");
      InicioTabla("ETablaInfo",0,"95%","","center","",1);
        ITR("",0,"95%","","center","top");
          TDEtiCampo(false,"EEtiqueta",0," Nombre o Razón Social:","cNomRazonSocial","",95,100," Nombre","fMayus(this);","","",false,"EEtiquetaL",1);
        FTR();
      FinTabla();
    FTDTR();

    // Tabla de listado de domicilios del representante
    ITRTD("EEtiquetaC",0,"","","","");
      InicioTabla("ETablaInfo",0,"95%","","center","",1);
        ITRTD("ETablaST",1,"100%","","center");
          TextoSimple("DOMICILIOS DEL REPRESENTANTE");
        FTDTR();
        ITRTD("",0,"95%","","center","top");
        IFrame("IListado14","100%","170","Listado.js","yes",true);
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","40","center","bottom");
      IFrame("IPanel14","95%","34","Paneles.js");
    FTDTR();
    Hidden("iCvePersona");
    Hidden("iCveDomicilio");
    Hidden("iCveTipoDomicilio");
    Hidden("cCalle");
    Hidden("cNumExterior");
    Hidden("cNumInterior");
    Hidden("cColonia");
    Hidden("cCodPostal");
    Hidden("cTelefono");
    Hidden("iCvePais");
    Hidden("iCveEntidadFed");
    Hidden("iCveMunicipio");
    Hidden("iCveLocalidad");
    Hidden("lPredeterminado","");

    Hidden("iCveRepLegal");
    Hidden("iCveDomRepLegal");
  Hidden("hdFiltro11");
  Hidden("hdFiltro11A");
  Hidden("hdFiltro12");
  Hidden("hdFiltro13");
  Hidden("hdFiltro14");
  FinTabla();
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMObj = window.parent.document.forms[0];
  FRMPanel = fBuscaFrame("IPanel14");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Imp,");
  FRMListado = fBuscaFrame("IListado14");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Tipo,Domicilio,");
  FRMListado.fSetCampos("17,18,");
  FRMListado.fSetAlinea("left,left,");
  fDisabled(true);
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  var lSubmiteBusca = false;
  frm.hdOrden.value="";
  if(frm.iCvePersona.value==""){
    frm.hdFiltro14.value =  "";
    FRMPanel.fSetTraStatus("Disabled");
    return;
  }else{
    frm.hdFiltro14.value = " GRLRepLegal.iCvePersona="+ frm.iCvePersona.value;
    lSubmiteBusca = true;
    if (frm.iCveTipoDomicilio.value != "")
      if (parseInt(frm.iCveTipoDomicilio.value, 10) > 0)
        frm.hdFiltro14.value +=  " and GRLTipoDomicilio.iCveTipoDomicilio=" + frm.iCveTipoDomicilio.value;
    if (frm.iCveDomicilio.value==""){
      if (lEditDomRepLegal)
        FRMPanel.fSetTraStatus("AddOnly");
    }
    cCadTrans = (lEditDomRepLegal)?"UpdateComplete":"Disabled";
    FRMPanel.fSetTraStatus(cCadTrans);
  }
  if (!lEditDomRepLegal)
    FRMPanel.fSetTraStatus("Disabled");
  if (lSubmiteBusca && !lEditando){
    frm.hdFiltro.value = frm.hdFiltro14.value;
    fEngSubmite("pgGRLDomRepLegal.jsp","ListadoDomRL");
  }
  frm.iCveTipoDomicilio.disabled = false;
}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert (cError);
  }

  if(cId == "ListadoDomRL" && cError==""){
    aResDomRepLegal = fCopiaArreglo(aRes);
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aResDomRepLegal);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    if (DomOriginal && DomOriginal != "")
      fReposicionaListado(FRMListado, "1", DomOriginal);
    if(aResDomRepLegal.length == 0)
      window.parent.fLimpiaRepLegal();
  }
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
  frm.iCveRepLegal.value=aDato[0];
  frm.iCvePersona.value=aDato[0];
  frm.iCveDomRepLegal.value=aDato[1];
  frm.iCveDomicilio.value = aDato[1];
  tipoDomActual = aDato[2];
  frm.cCalle.value = aDato[3];
  frm.cNumExterior.value = aDato[4];
  frm.cNumInterior.value = aDato[5];
  frm.cColonia.value = aDato[6];
  frm.cCodPostal.value = aDato[7];
  frm.cTelefono.value = aDato[8];

  if (aDato[0] != "")
    fAsignaParentValues(aDato);
}

function fAsignaParentValues(aDato){
  FRMObj.RepLegal_iCveDomicilio.value = aDato[1];
  FRMObj.RepLegal_iCveTipoDomicilio.value = aDato[2];
  FRMObj.RepLegal_cCalle.value = aDato[3];
  FRMObj.RepLegal_cNumExterior.value = aDato[4];
  FRMObj.RepLegal_cNumInterior.value = aDato[5];
  FRMObj.RepLegal_cColonia.value = aDato[6];
  FRMObj.RepLegal_cCodPostal.value = aDato[7];
  FRMObj.RepLegal_cTelefono.value = aDato[8];
  FRMObj.RepLegal_iCvePais.value = aDato[9];
  FRMObj.RepLegal_cDscPais.value = aDato[11];
  FRMObj.RepLegal_iCveEntidadFed.value = aDato[12];
  FRMObj.RepLegal_cDscEntidadFed.value = aDato[13];
  FRMObj.RepLegal_iCveMunicipio.value = aDato[15];
  FRMObj.RepLegal_cDscMunicipio.value = aDato[14];
  FRMObj.RepLegal_iCveLocalidad.value = aDato[19];
  FRMObj.RepLegal_cDscLocalidad.value = aDato[10];
  FRMObj.RepLegal_lPredeterminado.value = aDato[16];
  FRMObj.RepLegal_cDscTipoDomicilio.value = aDato[17];
  FRMObj.RepLegal_cDscDomicilio.value = aDato[18];
  window.parent.dispRepLegal_Domicilio();
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
  cMsg = "";
  cMsg = fValElements("");

  if(cMsg != ""){
    fAlert(cMsg);
    return false;
  }
  return true;
}

function fImprimir(){
   self.focus();
   window.print();
}

function fSetNombre(cNomRazonSocial,cApPaterno,cApMaterno,iCvePersona, cDomPersona){
  RepOriginal = iCvePersona;
  DomOriginal = cDomPersona;
  frm.cNomRazonSocial.value = cNomRazonSocial + " " + cApPaterno + " " + cApMaterno;
  frm.iCvePersona.value = iCvePersona;
  frm.iCveDomicilio.value = cDomPersona;
  fNavega();
}

function fSetClavePersona(iCvePersona, iCveDomicilio){
  DomOriginal = iCveDomicilio;
  frm.iCvePersona.value = iCvePersona;
  frm.iCveDomicilio.value = iCveDomicilio;
  lEditando = false;
  fNavega();
}

function fTipoDomicilio(){
  if (!lEditando)
    fNavega();
}

function setEditValues(EditDomRepLegal){
  lEditDomRepLegal = EditDomRepLegal;
  fNavega();
}

// MetaCD=1.0
// Title: pg111010012.js
// Description: JS "Catálogo" de la entidad GRLDomicilio
// Company: Tecnología InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm, FRMObj;
var lEditDomPersona  = true;
var lEditando = false;
var aResDomPersona;
var DomOriginal;
var tipoDomActual;
var lHayDefault=false;
var lCargaPaices = true;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010012.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","100%","","");
    ITRTD("",0,"","","center");
      InicioTabla("ETablaInfo",0,"0","","center","",1);
        ITRTD("",0,"","","center");
          TDEtiSelect(true,"EEtiquetaC",0,"Tipo de Domicilio:", "iCveTipoDomicilio","fTipoDomicilio();");
        FTDTR();
      FinTabla();
    FTDTR();
    ITRTD("",0,"","","center");
      IFrame("IListado12","95%","69","Listado.js","yes",true);
    FTDTR();
    ITRTD("",0,"","center","");
      InicioTabla("ETablaInfo",0,"0","","center","",1);
        ITRTD("ETablaST",8,"","","center");
          cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"DATOS DEL DOMICILIO":cTitulo;
          TextoSimple(cTitulo);
        FTDTR();
        ITR();
          TDEtiCampo(true,"EEtiqueta",0," Clave:","iCveDomicilio","",3,3," Clave","fMayus(this);","","",false,"EEtiquetaL",1);
          TDEtiCheckBox("EEtiqueta",0," Predeterminado:","lPrincipalBOX","1",true," Predeterminado","","","",false,true,"EEtiquetaL",3);
        FITR();
          TDEtiCampo(true,"EEtiqueta",0," Calle:","cCalle","",40,40," Calle","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0," Núm. Exterior:","cNumExterior","",10,10," Número Exterior","fMayus(this);","","",false,"EEtiquetaL",1);
          TDEtiCampo(false,"EEtiqueta",0," Núm. Interior:","cNumInterior","",11,11," Número Interior","fMayus(this);","","",false,"EEtiquetaL",1);
        FITR();
          TDEtiCampo(true,"EEtiqueta",0," Colonia:","cColonia","",40,40," Colonia","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0," C.P.:","cCodPostal","",5,5," C.P.","fMayus(this);","","",false,"EEtiquetaL",1);
          TDEtiCampo(false,"EEtiqueta",0," Teléfono:","cTelefono","",25,50," Teléfono","fMayus(this);","","",false,"EEtiquetaL",1);
        FTR();
        ITRTD("EEtiqueta",0,"","","left","");
          TextoSimple("País, Entidad,Municipio:");
        FITD("",7);
          Select("iCvePais","fChangePais();");
          Select("iCveEntidadFed","fChangeEntidadFed();");
          Select("iCveMunicipio","fChangeMunicipio();");
        FTDTR();
        ITRTD("EEtiqueta",0,"","","left","");
          TextoSimple("Localidad:");
        FITD("",7);
          Select("iCveLocalidad");
        FTDTR();
      FinTabla();
    FTDTR();
    ITRTD("",0,"","40","center","bottom");
      IFrame("IPanel12","95%","34","Paneles.js");
    FTDTR();
  FinTabla();
  Hidden("lPredeterminado","");
  Hidden("hdFiltroMun","");
  Hidden("hdOrdenMun","");
  Hidden("iCvePersona");
  Hidden("hdFiltro11");
  Hidden("hdFiltro11A");
  Hidden("hdFiltro12");
  Hidden("hdFiltro13");
  Hidden("hdFiltro14");
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMObj = window.parent.document.forms[0];
  FRMPanel = fBuscaFrame("IPanel12");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  if (!lEditDomPersona)
    FRMPanel.fSetTraStatus("Disabled");
  else
    FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado12");
  FRMListado.fSetControl(self);

  FRMListado.fSetTitulo("Tipo,Domicilio,");
  FRMListado.fSetCampos("17,19,");
  FRMListado.fSetAlinea("left,left,");
  frm.hdNumReg.value = 10000;
  fDisabled(true);
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
    frm.hdNumReg.value = 10000;
  var lSubmiteBusca = false;
  frm.hdOrden.value =  "lPredeterminado desc";
  if(frm.iCvePersona.value==""){
    frm.hdFiltro12.value =  "";
    FRMPanel.fSetTraStatus("Disabled");
    return;
  }else{
    frm.hdFiltro12.value = " iCvePersona="+ frm.iCvePersona.value;
    lSubmiteBusca = true;
    if (frm.iCveTipoDomicilio.value != "")
      if (parseInt(frm.iCveTipoDomicilio.value, 10) > 0)
        frm.hdFiltro12.value +=  " and GRLTipoDomicilio.iCveTipoDomicilio=" + frm.iCveTipoDomicilio.value;
    if (frm.iCveDomicilio.value==""){
      if (lEditDomPersona)
        FRMPanel.fSetTraStatus("AddOnly");
    }
    cCadTrans = (lEditDomPersona)?"UpdateComplete":"Disabled";
    if (lEditDomPersona)
      FRMPanel.fSetTraStatus(cCadTrans);
  }
  if (!lEditDomPersona)
    FRMPanel.fSetTraStatus("Disabled");
  if (lSubmiteBusca && !lEditando){
    if(fEngSubmite("pgGRLDomicilioA1.jsp","ListadoDom"));
  }
  frm.hdFiltro12.value="";
  frm.iCveTipoDomicilio.disabled = false;
}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert (cError);
    fCancelar();
  }
  if(cId == "ListadoDom" && cError==""){
    aResDomPersona = fCopiaArreglo(aRes);
    lHayDefault = false;
    for(i=0;i<aRes.length;i++){
      if(aRes[i][18]==1) lHayDefault = true;
    }
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aResDomPersona);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fCancelar();
    if (DomOriginal != "")
      fReposicionaListado(FRMListado, "1", DomOriginal);
    if(aResDomPersona.length == 0)
      window.parent.fLimpiaSolicitante();
    window.parent.fBuscaRepLegal();
  }

  if(cId == "Municipio" && cError=="")
    fFillSelect(frm.iCveMunicipio,aRes,false,frm.iCveMunicipio.value,2,3);
  if(cId=="Localidad" && cError=="")
     fFillSelect(frm.iCveLocalidad,aRes,false,frm.iCveLocalidad.value,3,4);
  if(cId=="CIDTipoDom" && cError==""){
     fFillSelect(frm.iCveTipoDomicilio,aRes,true,frm.iCveTipoDomicilio.value,0,1);
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
  frm.hdFiltro12.value="";
  fBlanked();
  fSelectSetIndexFromValue(frm.iCveTipoDomicilio, "-1");
  fFillSelect(frm.iCvePais,window.parent.aPais,false,frm.iCvePais.value);
  //fFillSelect(frm.iCvePais,aPais);
  fChangePais();
  lEditando = true;
  FRMPanel.fSetTraStatus("UpdateBegin");
  fDisabled(false,"iCvePersona,iCveDomicilio,");
  if(!lHayDefault) {
    frm.lPrincipalBOX.disabled = true;
    frm.lPrincipalBOX.checked  = true;
  }
  FRMListado.fSetDisabled(true);
  window.parent.fDeshabilita();
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  if(fValidaTodo()==true){
    frm.hdFiltro12.value = "iCvePersona="+ frm.iCvePersona.value;
    frm.hdOrden.value =  "lPredeterminado desc";
    frm.lPrincipalBOX.checked==true?frm.lPredeterminado.value=1:frm.lPredeterminado.value=0;
    if(fEngSubmite("pgGRLDomicilioA1.jsp","ListadoDom")==true){
      lEditando = false;
      FRMPanel.fSetTraStatus("UpdateComplete");
      fDisabled(false,"iCveTipoDomicilio,");
      fSelectSetIndexFromValue(frm.iCveTipoDomicilio, "-1");
      FRMListado.fSetDisabled(false);
      window.parent.fHabilita(false);
      fNavega();
    }
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
  if(fValidaTodo()==true){
    frm.hdFiltro12.value = "iCvePersona="+ frm.iCvePersona.value;
    frm.hdOrden.value =  "lPredeterminado desc";
    frm.lPrincipalBOX.checked==true?frm.lPredeterminado.value=1:frm.lPredeterminado.value=0;
    if(fEngSubmite("pgGRLDomicilioA1.jsp","ListadoDom")==true){
      lEditando = false;
      FRMPanel.fSetTraStatus("UpdateComplete");
      fDisabled(false,"iCveTipoDomicilio,");
      fSelectSetIndexFromValue(frm.iCveTipoDomicilio, "-1");
      FRMListado.fSetDisabled(false);
      window.parent.fHabilita(false);
      fNavega();
    }
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
  fSelectSetIndexFromValue(frm.iCveTipoDomicilio, tipoDomActual);
  fFillSelect(frm.iCvePais,window.parent.aPais,false,frm.iCvePais.value);
  fChangePais();
  lEditando = true;
  FRMPanel.fSetTraStatus("UpdateBegin");
  fDisabled(false,"iCvePersona,iCveDomicilio,");
  if(frm.lPrincipalBOX.checked  == true)
    frm.lPrincipalBOX.disabled = true;
  FRMListado.fSetDisabled(true);
  window.parent.fDeshabilita();
}

// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
  if(lEditando)
    window.parent.fHabilita();
  lEditando = false;
  if(FRMListado.fGetLength() > 0){
    if(lEditDomPersona)
      FRMPanel.fSetTraStatus("UpdateComplete");
  }
  else{
    if(lEditDomPersona)
      FRMPanel.fSetTraStatus("AddOnly");
  }
  fDisabled(true,"iCveTipoDomicilio,");
  fSelectSetIndexFromValue(frm.iCveTipoDomicilio, "-1");
  FRMListado.fSetDisabled(false);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
      fNavega();
   }
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){

  frm.iCveDomicilio.value = aDato[1];
  tipoDomActual = aDato[2];
  frm.cCalle.value = aDato[3];
  frm.cNumExterior.value = aDato[4];
  frm.cNumInterior.value = aDato[5];
  frm.cColonia.value = aDato[6];
  frm.cCodPostal.value = aDato[7];
  frm.cTelefono.value = aDato[8];
  fAsignaSelect(frm.iCvePais,aDato[9],aDato[11]);
  fAsignaSelect(frm.iCveEntidadFed,aDato[12],aDato[13]);
  fAsignaSelect(frm.iCveMunicipio,aDato[15],aDato[14]);
  fAsignaSelect(frm.iCveLocalidad,aDato[16],aDato[10]);
  fAsignaCheckBox(frm.lPrincipalBOX,aDato[18]);

  fAsignaParentValues(aDato);
}

function fAsignaParentValues(aDato){
  FRMObj.Persona_iCveDomicilio.value = aDato[1];
  FRMObj.Persona_iCveTipoDomicilio.value = aDato[2];
  FRMObj.Persona_cCalle.value = aDato[3];
  FRMObj.Persona_cNumExterior.value = aDato[4];
  FRMObj.Persona_cNumInterior.value = aDato[5];
  FRMObj.Persona_cColonia.value = aDato[6];
  FRMObj.Persona_cCodPostal.value = aDato[7];
  FRMObj.Persona_cTelefono.value = aDato[8];
  FRMObj.Persona_iCvePais.value = aDato[9];
  FRMObj.Persona_cDscPais.value = aDato[11];
  FRMObj.Persona_iCveEntidadFed.value = aDato[12];
  FRMObj.Persona_cDscEntidadFed.value = aDato[13];
  FRMObj.Persona_iCveMunicipio.value = aDato[15];
  FRMObj.Persona_cDscMunicipio.value = aDato[14];
  FRMObj.Persona_iCveLocalidad.value = aDato[16];
  FRMObj.Persona_cDscLocalidad.value = aDato[10];
  FRMObj.Persona_lPredeterminado.value = aDato[18];
  FRMObj.Persona_cDscTipoDomicilio.value = aDato[17];
  FRMObj.Persona_cDscDomicilio.value = aDato[19];
  window.parent.dispPersona_Domicilio();
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
  cMsg = "";
  cMsg = fValElements("cNumExterior,cNumInterior,cTelefono,cCodPostal,");

  if(fEvaluaCampo(frm.cNumExterior.value)==false)
      cMsg = cMsg+"\n - El Campo 'Núm Exterior' no permite comillas";
  if(fEvaluaCampo(frm.cNumInterior.value)==false)
      cMsg = cMsg+"\n - El Campo 'Núm Interior' no permite comillas";
  if(fEvaluaCampo(frm.cTelefono.value)==false)
      cMsg = cMsg+"\n - El Campo 'Teléfono' no permite comillas";
  if (parseInt(frm.iCveTipoDomicilio.value,10) < 1)
    cMsg += '\n-Favor de especificar el tipo de domicilio.';
  if (!fSoloNumeros(frm.cCodPostal.value))
    cMsg += '\n-Favor de verificar el código postal, solo se aceptan números';

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

function fChangePais() {
  fFilterSelect(frm.iCveEntidadFed,window.parent.aEstados,frm.iCvePais.value,false,frm.iCveEntidadFed.value,1,2);
  fChangeEntidadFed();
}

function fChangeEntidadFed() {
  if(frm.iCveEntidadFed.value != ""){
    frm.hdOrdenMun.value = " GRLMUNICIPIO.cNombre ";
    frm.hdFiltroMun.value = " iCvePais= " + frm.iCvePais.value +
                            " AND iCveEntidadFed= " + frm.iCveEntidadFed.value;
    fEngSubmite("pgGRLMUNICIPIO.jsp","Municipio");
    if(frm.iCveMunicipio && frm.iCveMunicipio.optios && frm.iCveMunicipio.options.length && frm.iCveMunicipio.options.length > 0){
      //frm.iCveMunicipio.selectedIndex = 0;
      frm.iCveMunicipio.value = frm.iCveMunicipio.options[frm.iCveMunicipio.selectedIndex].value;
    }else{
      frm.iCveMunicipio.value = "";
      frm.iCveMunicipio.selectedIndex = 0;
    }
  }else
    frm.iCveMunicipio.length = 0;
  fChangeMunicipio();
}

function fChangeMunicipio(){
  frm.hdFiltro.value = "";
  frm.hdOrden.value = "";
  if(frm.iCveMunicipio.value != ""){
    frm.hdFiltro.value = " GRLLocalidad.iCvePais= " + frm.iCvePais.value +
                         " AND GRLLocalidad.iCveEntidadFed= " + frm.iCveEntidadFed.value +
                         " AND GRLLocalidad.iCveMunicipio= " + frm.iCveMunicipio.value;
    frm.hdOrden.value = " GRLLocalidad.cNombre ";
    frm.hdNumReg.value = 10000;
    fEngSubmite("pgGRLLocalidad.jsp","Localidad");
  }else
    frm.iCveLocalidad.length = 0;
}

function fSetClavePersona(iCvePersona, iCveDomicilio){
  DomOriginal = iCveDomicilio;
  lEditando = false;
  frm.iCvePersona.value = iCvePersona;
  fNavega();
}

function fTipoDomicilio(){
  if (!lEditando)
    fNavega();
}

function setEditValues(EditDomPersona){
  lEditDomPersona = EditDomPersona;
  if (!lEditDomPersona)
    FRMPanel.fSetTraStatus("Disabled");
  fNavega();
}
function fEvaluaCampo(cVCadena){
    if(cVCadena == "")
      return true;

    if (fRaros2(cVCadena))
         return false;
    else
        return true;
}
function fRaros2(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"¬")  ||
       fEncCaract(cVCadena.toUpperCase(),'"')  ||
       fEncCaract(cVCadena.toUpperCase(),"'"))
       return true;
   else
      return false;
}

function fCargaTipoDom(){
    frm.hdFiltro.value = "";
    frm.hdOrden.value = "";
    fEngSubmite("pgGRLTipoDomicilio.jsp","CIDTipoDom");
}


function fCargaTipoD(){
    lCargaPaices=true;
    if(window.parent){
        if(window.parent.window.parent){
  	  if(window.parent.window.parent.fCargaPaices){
  	      lCargaPaices=false;
  	  }
        }
    }   
    if(lCargaPaices==true) fEngSubmite("pgGRLTipoDomicilio.jsp","CIDTipoDom");
}
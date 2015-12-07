// MetaCD=1.0
// Title: pg111010013.js
// Description: JS "Catálogo" de la entidad GRLRepLegal
// Company: Tecnología InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var frm;
var FRMListado = "";
var FRMListado2 = "";

var lEditRepLegal  = true;
var lEditando = false;
var RepOriginal;
var aResRepLegal;
var objDocTop;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010013.js";
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
        ITRTD("ETablaST",1,"100%","","center");
          TextoSimple("REPRESENTANTES LEGALES ASIGNADOS");
        FTDTR();
        ITRTD("",0,"95%","","center","top");
          IFrame("IListado13","100%","110","Listado.js","yes",true);
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("EEtiquetaC",0,"","","","");
      InicioTabla("ETablaInfo",0,"95%","","center","",1);
        ITRTD("ETablaST",1,"100%","","center");
          IFrame("IFiltro13","0","0","Filtros.js");
          cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"BUSCAR PERSONA PARA ASIGNAR COMO REPRESENTANTE":cTitulo;
          TextoSimple(cTitulo);
        FTDTR();
        ITRTD("",0,"95%","","center","top");
          IFrame("IListado13A","100%","80","Listado.js","yes",true);
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD();
      InicioTabla("",0,"95%","","center","",1);
        ITR();
          TDEtiCheckBox("EEtiqueta",0," Predeterminado:","lPrincipalBOX","1",true," Predeterminado");
          ITD("",0);
            IFrame("IPanel13","100%","34","Paneles.js");
          FTD();
        FTR();
      FinTabla();
    FTDTR();
  FinTabla();
  Hidden("RepLegal_iCveEmpresa");
  Hidden("RepLegal_lPrincipal");

  Hidden("iCveEmpresa");
  Hidden("iCvePersona");
  Hidden("lPrincipal");

  Hidden("RepLegal_iCvePersona");
  Hidden("RepLegal_cRFC");
  Hidden("RepLegal_cRPA");
  Hidden("RepLegal_iTipoPersona");
  Hidden("RepLegal_cNomRazonSocial");
  Hidden("RepLegal_cApPaterno");
  Hidden("RepLegal_cApMaterno");
  Hidden("RepLegal_cCorreoE");
  Hidden("RepLegal_cPseudonimoEmp");
  Hidden("hdFiltro11");
  Hidden("hdFiltro11A");
  Hidden("hdFiltro12");
  Hidden("hdFiltro13");
  Hidden("hdFiltro14");
  fFinPagina();
}

function fOnLoad(){
  frm = document.forms[0];
  FRMObj = window.parent.document.forms[0];
  FRMPanel = fBuscaFrame("IPanel13");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(true);
  if (!lEditRepLegal)
    FRMPanel.fSetTraStatus("Disabled");
  else
  FRMPanel.fShow("Tra,");

  FRMListado = fBuscaFrame("IListado13");
  FRMListado.fSetControl(self);
//  FRMListado.fSetTitulo("Predeterminado,R.F.C.,R.P.A.,Nombre o Razón Social,");
  FRMListado.fSetTitulo("Predet.,R.F.C.,R.P.A.,Nombre o Razón Social,Dirección,Modificar,");
  FRMListado.fSetCampos("8,1,2,9,10,11,");
  FRMListado.fSetAlinea("center,left,left,left,left,center,");
  FRMListado.fSetDespliega("logico,texto,texto,texto,texto,texto,");

  FRMListado2 = fBuscaFrame("IListado13A");
  FRMListado2.fSetControl(self);
  FRMListado2.fSetSelReg(2);
  FRMListado2.fSetTitulo(" ,Clave,R.F.C.,R.P.A.,Nombre o Razón Social,Dirección,");
  FRMListado2.fSetCampos("0,1,2,9,10,");
  FRMListado2.fSetAlinea("center,left,left,left,left,left,");
  FRMListado2.fSetObjs(0,"Caja");

  FRMFiltro = fBuscaFrame("IFiltro13");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow(",");
  FRMFiltro.fSetFiltra("Clave,iCvePersona,R.F.C.,cRFC,R.P.A.,cRPA,Nombre,cNomRazonSocial,Ap. Paterno,cPaterno,Ap. Materno,cMaterno,");
  FRMFiltro.fSetOrdena("Empresa,iCveEmpresa,Rep. Legal,iCvePersona,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  var lSubmiteBusca = false;
  frm.hdOrden.value="";
  if(frm.RepLegal_iCveEmpresa.value==""){
    frm.hdFiltro13.value =  "";
    FRMPanel.fSetTraStatus("Disabled");
    return;
  }else{
    frm.hdFiltro13.value = "GRLRepLegal.iCveEmpresa ="+ frm.RepLegal_iCveEmpresa.value;
    lSubmiteBusca = true;
    if (frm.RepLegal_iCvePersona.value==""){
      if (lEditRepLegal)
        FRMPanel.fSetTraStatus("AddOnly");
    }
    cCadTrans = (lEditRepLegal)?"UpdateComplete":"Disabled";
    if(lEditRepLegal)
      FRMPanel.fSetTraStatus(cCadTrans);
  }
  if (!lEditRepLegal)
    FRMPanel.fSetTraStatus("Disabled");
  if (lSubmiteBusca && !lEditando){
    frm.hdFiltro.value = frm.hdFiltro13.value;
    fEngSubmite("pgGRLRepLegalA1.jsp","ListadoRepLegal");
  }
}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    if(cError.indexOf("(-803)") != -1){
      fAlert("\n-La persona seleccionada ya está asignada como representante legal");
      cError = "";
      if(window.parent.fBuscaRepLegal)
        window.parent.fBuscaRepLegal();
    }
    if(cError!= "")
      fAlert (cError);
    fCancelar();
    FRMFiltro.fSetNavStatus("Record");
  }

  if(cId == "ListadoRepLegal" && cError==""){
    aResRepLegal = fCopiaArreglo(aRes);
    frm.hdRowPag.value = iRowPag;
    var aResRepL2 = new Array();
    var aResRepL1;
    for(i=0; i < aResRepLegal.length; i++){
      aResRepL1 = new Array();
      aResRepL1[0] = aResRepLegal[i][0];
      aResRepL1[1] = aResRepLegal[i][1];
      aResRepL1[2] = aResRepLegal[i][2];
      aResRepL1[3] = aResRepLegal[i][3];
      aResRepL1[4] = aResRepLegal[i][4];
      aResRepL1[5] = aResRepLegal[i][5];
      aResRepL1[6] = aResRepLegal[i][6];
      aResRepL1[7] = aResRepLegal[i][7];
      aResRepL1[8] = aResRepLegal[i][8];
      aResRepL1[9] = aResRepLegal[i][9];
      aResRepL1[10] = aResRepLegal[i][10];
      aResRepL1[11] = "Mod. Datos";
      aResRepL2[i] = aResRepL1;
    }
    aResRepLegal = fCopiaArreglo(aResRepL2);
    FRMListado.fSetListado(aResRepLegal);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fCancelar();
    if (RepOriginal && RepOriginal != "")
      fReposicionaListado(FRMListado, "0", RepOriginal);
    if(aResRepLegal.length == 0)
      window.parent.fLimpiaRepLegal();
  }

  if(cId == "Listado2" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListado2.fSetListado(aRes);
    FRMListado2.fShow();
    FRMListado2.fSetLlave(cLlave);
    fCancelar();
    FRMFiltro.fSetNavStatus(cNavStatus);
    if(lEditRepLegal && aRes.length>0)
      FRMPanel.fSetTraStatus("Add,Sav,");
    fDisabled(true);
  }
}

function fGetParametrosConsulta(frmDestino){
  var lShowPersona     = true;
  var lShowRepLegal    = false;
  var lEditPersona     = true;
  var lEditDomPersona  = true;
  var lEditRepLegal    = false;
  var lEditDomRepLegal = false;
  if (frmDestino){
    if (frmDestino.setShowValues)
      frmDestino.setShowValues(lShowPersona, lShowRepLegal, "1");
    if (frmDestino.setEditValues)
      frmDestino.setEditValues(lEditPersona, lEditDomPersona, lEditRepLegal, lEditDomRepLegal);
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
  lEditando = true;
  fAbreSolicitante();
}

function fEditRepres(){
  lEditando = false;
  fAbreSolicitante();
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetClaves(frmDestino){
  if (frmDestino.setClaves)
     frmDestino.setClaves(1, frm.RepLegal_iCvePersona.value, 0, frm.RepLegal_iCvePersona.value, 0);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  if(fValidaTodo()==true){
    frm.hdFiltro13.value = "GRLRepLegal.iCveEmpresa ="+ frm.RepLegal_iCveEmpresa.value;
    frm.hdNumReg.value =  FRMFiltro.fGetNumReg();

    frm.iCveEmpresa.value = frm.RepLegal_iCveEmpresa.value;
    frm.iCvePersona.value = frm.RepLegal_iCvePersona.value;

    aCBox = FRMListado2.fGetObjs(0);
    aRes = FRMListado2.fGetARes();

    frm.iCvePersona.value = "";
    for(aux=0; aux<aCBox.length; aux++){
      if (aCBox[aux]){
        if (frm.iCvePersona.value == "")
          frm.iCvePersona.value = aRes[aux][0];
        else
          frm.iCvePersona.value += "," + aRes[aux][0];
      }
    }
    if (frm.iCvePersona.value == ""){
      fAlert ('\nSeleccione al menos un registro para hacer esta operación.');
      return;
    }

    if(fEngSubmite("pgGRLRepLegalA1.jsp","ListadoRepLegal")){
      lEditando = false;
      FRMPanel.fSetTraStatus("UpdateComplete");
      fDisabled(true);
      FRMListado.fSetDisabled(false);
    }
  }
  aRes = "";
  FRMListado2.fSetListado(aRes);
  FRMListado2.fShow();
  if (objDocTop.fOnChange)
    objDocTop.fOnChange(true);
  fCancelar();
}

// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
  if(fValidaTodo()){
    frm.hdFiltro13.value = "GRLRepLegal.iCveEmpresa ="+ frm.RepLegal_iCveEmpresa.value;

    frm.iCveEmpresa.value = frm.RepLegal_iCveEmpresa.value;
    frm.iCvePersona.value = frm.RepLegal_iCvePersona.value;
    frm.lPrincipal.value = (frm.lPrincipalBOX.checked)?"1":"0";

    if(fEngSubmite("pgGRLRepLegalA1.jsp","ListadoRepLegal")){
      lEditando = false;
      FRMPanel.fSetTraStatus("UpdateComplete");
      fDisabled(true);
      FRMListado.fSetDisabled(false);
    }
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
  lEditando = true;
  FRMPanel.fSetTraStatus("UpdateBegin");
  fDisabled(false,"iCveEmpresa,");
  FRMListado.fSetDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
  lEditando = false;
  FRMFiltro.fSetNavStatus("ReposRecord");
  if(FRMListado.fGetLength() > 0){
    if(lEditRepLegal)
      FRMPanel.fSetTraStatus("UpdateComplete");
  }else{
    if(lEditRepLegal)
      FRMPanel.fSetTraStatus("AddOnly");
  }
  fDisabled(true);
  FRMListado.fSetDisabled(false);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
  if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
    frm.iCveEmpresa.value = frm.RepLegal_iCveEmpresa.value;
    frm.iCvePersona.value = frm.RepLegal_iCvePersona.value;
    fNavega();
  }
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato,iCol,lChecked){
  frm.RepLegal_iCvePersona.value     = aDato[0];
  frm.RepLegal_cRFC.value            = aDato[1];
  frm.RepLegal_cRPA.value            = aDato[2];
  frm.RepLegal_cNomRazonSocial.value = aDato[3];
  frm.RepLegal_cApPaterno.value      = aDato[4];
  frm.RepLegal_cApMaterno.value      = aDato[5];
  frm.RepLegal_cCorreoE.value        = aDato[6];
  frm.RepLegal_cPseudonimoEmp.value  = aDato[7];
  frm.RepLegal_lPrincipal.value      = aDato[8];
  frm.lPrincipalBOX.checked = (aDato[8]==1)?true:false;
  if (aDato[0] != "")
    fAsignaParentValues(aDato);
  window.parent.fBuscaDomRepLegal();
  if(iCol == 5){
    fEditRepres();
  }
}

function fAsignaParentValues(aDato){
  FRMObj.RepLegal_iTipoPersona.value    = "1";
  FRMObj.RepLegal_iCvePersona.value     = aDato[0];
  FRMObj.RepLegal_cRFC.value            = aDato[1];
  FRMObj.RepLegal_cRPA.value            = aDato[2];
  FRMObj.RepLegal_cNomRazonSocial.value = aDato[3];
  FRMObj.RepLegal_cApPaterno.value      = aDato[4];
  FRMObj.RepLegal_cApMaterno.value      = aDato[5];
  FRMObj.RepLegal_cCorreoE.value        = aDato[6];
  FRMObj.RepLegal_cPseudonimoEmp.value  = aDato[7];
  FRMObj.RepLegal_lPrincipal.value      = aDato[8];
  window.parent.dispRepLegal_Nombre();
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   cMsg = fValElements();

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

/******Segundo Listado*******/
function fNavega3(objDoc){
  objDocTop = objDoc;
  frm.hdFiltro13.value="";
  frm.hdOrden.value = "";
  var cadRFC = (FRMObj.cRFC.value!="")?" and cRFC LIKE '" + FRMObj.cRFC.value + "%'":"";
  var cadRPA = (FRMObj.cRPA.value!="")?" and cRPA LIKE '" + FRMObj.cRPA.value + "%'":"";
  var cadNom = (FRMObj.cNomRazonSocial.value!="")?" and cNomRazonSocial LIKE '" + FRMObj.cNomRazonSocial.value + "%'":"";
  var cadApPat = (FRMObj.cApPaterno.value!="")?" and cApPaterno LIKE '" + FRMObj.cApPaterno.value + "%'":"";
  var cadApMat = (FRMObj.cApMaterno.value!="")?" and cApMaterno LIKE '" + FRMObj.cApMaterno.value + "%'":"";

  frm.hdFiltro13.value = "iTipoPersona=1 ";
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
  var lSubmiteBusca = false;

  if((cadRFC == "") && (cadRPA == "") && ((cadNom == "") || ((cadApPat == "") && (cadApMat == ""))))
    fAlert("Favor de proporcionar RFC, RPA, o Nombre y Un Apellido");
  else{
    frm.hdFiltro13.value += cadRFC + cadRPA + cadNom + cadApPat + cadApMat;
    lSubmiteBusca = true;
  }
  if (lSubmiteBusca){
    frm.hdFiltro.value = frm.hdFiltro13.value;
  //  fEngSubmite("pgGRLPersonaA1.jsp","Listado2");
    fEngSubmite("pgGRLPersonaA3.jsp","Listado2");
  }
}

function fSetClavePersona(iCvePersona, iCveRepLegal){
  RepOriginal = iCveRepLegal;
  frm.RepLegal_iCveEmpresa.value = iCvePersona;
  frm.RepLegal_iCvePersona.value = iCveRepLegal;
  lEditando = false;
  fNavega();
}

function setEditValues(EditRepLegal){
  lEditRepLegal = EditRepLegal;
  if (!lEditRepLegal)
    FRMPanel.fSetTraStatus("Disabled");
  fNavega();
}

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio){
  if(window.parent.fSetDatosBuscar)
    window.parent.fSetDatosBuscar(iTipoPersona, cRFC, cRPA, cPseudonimoEmp, cNomRazonSocial, cApPaterno, cApMaterno, true);
  if(cRFC!="" || cRPA!="" || cPseudonimoEmp!="" || cNomRazonSocial!=""){
    if(window.parent.fBuscaDatos)
      window.parent.fBuscaDatos();
  }
}

function fReporte(){
  if(window.parent.frm.Persona_iCvePersona.value != ""){
    cClavesModulo = "2,";
    cNumerosRep = "9,";
    cFiltrosRep = window.parent.frm.Persona_iCvePersona.value + cSeparadorRep;
  }
  if(window.parent.frm.RepLegal_iCvePersona.value != ""){
    cClavesModulo += "2,";
    cNumerosRep += "10,";
    cFiltrosRep += window.parent.frm.RepLegal_iCvePersona.value + cSeparadorRep;
  }
  if(window.parent.frm.Persona_iCvePersona.value != "")
    fReportes();
  else
    fAlert('Debe elegir una persona con o sin representantes legales para poder ejecutar esta acción');
}

// MetaCD=1.0
 // Title: pgRegistraPago.js
 // Description: JS para el registro de pagos
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Rafael Miranda Blumenkron
var cTitulo = "";
var frm;
var lPermiteEdicion = true;
var FRMListado, FRMPanel;
var aBancos = new Array();
var aPagos = new Array();
var lRegistroUno = false;
var cEjercicioRegistro = "";
var cNumSolicRegistro  = "";
var cConsecRegistro    = "";
var cRefNumRegistro    = "";
var cRefAlfaNumRegistro = "";
var lNuevo = false;
function fBefLoad(){
  cPaginaWebJS = "pgRegistraPago.js";
  if(top.fGetTituloPagina(cPaginaWebJS))
    cTitulo = top.fGetTituloPagina(cPaginaWebJS);
  fSetWindowTitle();
}

function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","");
    ITRTD("ESTitulo",0,"100%","","center");
       fTituloEmergente(cTitulo);
    FTDTR();
    ITRTD("",0,"","1","center");
      InicioTabla("ETablaInfo",0,"95%","","","",1);
        ITRTD("ETablaST",6,"","","center");
          TextoSimple("Datos del Solicitante");
        FTDTR();
        ITR();
          TDEtiCampo(false,"EEtiqueta",0," Solicitante:","Persona_cNomRazonSocial","",50,50," Solicitante","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0," Rep. Legal:","RepLegal_cNomRazonSocial","",50,50," Representante Legal","fMayus(this);","","",false,"EEtiquetaL",0);
        FITR();
          TDEtiAreaTexto(false,"EEtiqueta",0,"Dom. Solicitante:",49,3,"Persona_cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",3);
          TDEtiAreaTexto(false,"EEtiqueta",0,"Dom. Rep. Legal:",49,3,"RepLegal_cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",0);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0," Ejercicio:","iEjercicio","",6,6," Ejercicio de Solicitud","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiCampo(false,"EEtiqueta",0," Núm. Solicitud:","iNumSolicitud","",6,6," Número de Solicitud","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiAreaTexto(false,"EEtiqueta",0,"Trámite:",49,2,"cDscTramite","","Trámite Registrado","fMayus(this);","","",false,false,false,"EEtiquetaL",0);
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"95%","","center","top");
      IFrame("IListado","95%","120","Listado.js","yes",true);
    FTDTR();

    ITRTD("",0,"45%","","center","top");
    InicioTabla("ETablaInfo",0,"50%","","","",1);
      ITD("",0,"50%","","center","top");
        Liga("Reimpresión de Fichas","fAbreFichaIngresos();","Reimpresión de fichas generadas previamente");
      FTD();
      if(top.opener && top.opener.fMostrarGenerarFicha && top.opener.fMostrarGenerarFicha()){
        ITD("",0,"50%","","center","top");
          Liga("Generar Ficha","fAbreGenerarFicha();","Generar Ficha de Pago");
        FTD();
      }
    FinTabla();
    FTDTR();

    ITRTD("",0,"","","center");
      InicioTabla("ETablaInfo",0,"","","","",0);
        ITR();
          TDEtiAreaTexto(false,"EEtiqueta",0,"Concepto:",49,3,"cDscConcepto","","Concepto de pago","fMayus(this);","","",false,false,false,"EEtiquetaL",3);
        FITR();
          TDEtiCampo(true,"EEtiqueta",0,"Ref. Numérica:","iRefNumerica","",10,10,"Referencia Numérica ","fMayus(this);");
          TDEtiCampo(true,"EEtiqueta",0,"Importe a Pagar:","cImportePagar","",18,15,"Importe a pagar","fMayus(this);");
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","","center");
      InicioTabla("ETablaInfo",0,"","","","",1);
        ITR();
          TDEtiCampo(true,"EEtiqueta",0,"Fecha de Pago:","dtPago","",10,10,"Fecha de pago","fMayus(this);");
          TDEtiCampo(true,"EEtiqueta",0,"Importe Pagado:","dImportePagado","",18,15,"Importe pagado","fMayus(this);");
        FITR();
          TDEtiCampo(false,"EEtiqueta",0,"Ref. Alfanumérica:","cRefAlfaNum","",18,15,"Referencia alfanumérica","fMayus(this);");
          TDEtiCampo(false,"EEtiqueta",0,"Formato SAT:","cFormatoSAT","",18,15,"Formato SAT de pago","fMayus(this);");
        FITR();
          TDEtiSelect(true,"EEtiqueta",0,"Banco:","iCveBanco","","EEtiquetaL",0);
          TDEtiCheckBox("EEtiqueta",0," Pagado:","lPagadoBOX","1",true,"Pagado");
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","","center","bottom");
      InicioTabla("ETablaInfo",0,"95%","","","",1);
        ITRTD("",0,"75%","","center","bottom");
          IFrame("IPanel","100%","34","Paneles.js");
        FITD("ETablaInfo",0,"","","center","bottom")
          BtnImgNombre("vgcerrar","aceptar","fCerrarVentana();","");
        FTDTR();
    FTDTR();

  FinTabla();

  Hidden("iConsecutivo");
  Hidden("iNumUnidades");
  Hidden("dImportePagar");
  Hidden("lPagado");
  Hidden("cRefNumerica");
  Hidden("cImportePagado");
  Hidden("dtActual");
  Hidden("cRFCSolicitante");
  Hidden("dtFechaRef");
  Hidden("iCveConcepto");
  Hidden("hdLlave");
  Hidden("hdSelect");

  fFinPagina();
}

function fSetDatosSolicitud(cSolicitante, cDomSolicitante,
                            cRepLegal, cDomRepLegal,
                            iEjercicio, iNumSolicitud, cDscTramite, cRFC){
  frm.Persona_cNomRazonSocial.value = cSolicitante;
  frm.Persona_cDscDomicilio.value = cDomSolicitante;
  frm.RepLegal_cNomRazonSocial.value = cRepLegal;
  frm.RepLegal_cDscDomicilio.value = cDomRepLegal;
  frm.iEjercicio.value = iEjercicio;
  frm.iNumSolicitud.value = iNumSolicitud;
  frm.cDscTramite.value = cDscTramite;
  if(cRFC && cRFC != 'undefined')
    frm.cRFCSolicitante.value = cRFC;
  else
    frm.cRFCSolicitante.value = "";
  fCargaPagos();
}

function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  fCargaBancos();

  FRMListado = fBuscaFrame("IListado");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Ref. Numérica,Importe Pagar,Fecha Pago,Importe Pagado,Ref. Alfanumérica,Banco,Formato SAT,");
//  FRMListado.fSetCampos("14,15,7,16,8,12,11,");
//  FRMListado.fSetCampos("4,5,7,9,8,12,11,");
  FRMListado.fSetCampos("15,16,7,17,8,12,11,");
  FRMListado.fSetAlinea("center,right,center,right,center,left,left,");

  FRMPanel = fBuscaFrame("IPanel");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");

  if (top.opener){
    if(top.opener.fGetPermiteEditarPagos)
      lPermiteEdicion = top.opener.fGetPermiteEditarPagos();
    if(top.opener.fGetDatosSolicitud)
      top.opener.fGetDatosSolicitud(window);
  }
  fDisabled(true);
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if (cError != ""){
    fAlert(cError);
    return;
  }else{
    if(cId == "CIdBanco") {
      aBancos = fCopiaArreglo(aRes);
      fFillSelect(frm.iCveBanco,aBancos,false,frm.iCveBanco.value,0,1);
    }
    if(cId == "CIdPagos"){
      aPagos = fCopiaArreglo(aRes);
      FRMListado.fSetLlave(cLlave);
      FRMListado.fSetListado(aPagos);
      FRMListado.fShow();
    }
    if(cId == "ValidaRefNumerica"){
      if(aRes && aRes.length > 0){
        frm.iCveConcepto.value = aRes[0][3];
        fGuardarValidado();
      }
      else
        fAlert('\n - La referencia numérica proporcionada no es correcta.');
    }
  }
}

function fCargaBancos(){
  frm.hdFiltro.value = "lVigente = 1";
  frm.hdOrden.value =  "cDscBanco";
  frm.hdNumReg.value =  10000;
  fEngSubmite("pgGRLBanco.jsp","CIdBanco");
}

function fCargaPagos(){
  if(frm.iEjercicio.value != "" &&
     frm.iNumSolicitud.value != ""){
    frm.hdBoton.value = "ObtenerPagos";
    fEngSubmite("pgTRARegRefPago.jsp","CIdPagos");
  }
}
/*
function fSelReg(aDato,iCol,lChecked){
  if (aDato && aDato.length>0 && aDato[2] != ""){
    alert("aDato: " + aDato);
    alert("aDato.length: " + aDato.length);
    alert("aDato[18]: " + aDato[18]);
    frm.iConsecutivo.value   = aDato[2];
    frm.iNumUnidades.value   = aDato[3];
    frm.iRefNumerica.value   = aDato[14];
    frm.dImportePagar.value  = aDato[5];
    frm.lPagado.value        = aDato[6];
    frm.lPagadoBOX.checked   = (aDato[6]==1)?true:false;
    frm.dtPago.value         = aDato[7];
    frm.cRefAlfaNum.value    = aDato[8];
    frm.dImportePagado.value = (aDato[9]>0)?aDato[9]:"";
    fSelectSetIndexFromValue(frm.iCveBanco, aDato[10]);
    frm.cFormatoSAT.value    = aDato[11];
    frm.cDscConcepto.value   = aDato[13];
    frm.cRefNumerica.value   = aDato[14];
    frm.cImportePagar.value  = aDato[15];
    frm.cImportePagado.value = aDato[16];
//    alert("aDato[16]: " + aDato[16]);
    frm.dtActual.value       = aDato[17];
    frm.dtFechaRef.value     = aDato[18];
    fSetPanelStatus();
  }
}
*/

function fSelReg(aDato,iCol,lChecked){
//  if (aDato && aDato.length>0 && aDato[2] != ""){
    frm.iConsecutivo.value   = aDato[2];
    frm.iNumUnidades.value   = aDato[3];
    frm.iRefNumerica.value   = aDato[4];
    frm.dImportePagar.value  = aDato[5];
    frm.lPagado.value        = aDato[6];
    frm.lPagadoBOX.checked   = (aDato[6]==1)?true:false;
    frm.dtPago.value         = aDato[7];
    frm.cRefAlfaNum.value    = aDato[8];
    frm.dImportePagado.value = (aDato[9]>0)?aDato[9]:"";
    if(aDato[10] != "" && aDato[10] > 0)
      fSelectSetIndexFromValue(frm.iCveBanco, aDato[10]);
    else
      fSelectSetIndexFromValue(frm.iCveBanco, 0);
    frm.cFormatoSAT.value    = aDato[11];
    frm.cDscConcepto.value   = aDato[13];
    frm.cRefNumerica.value   = aDato[15];
    frm.cImportePagar.value  = aDato[16];
    frm.cImportePagado.value = aDato[17];
    frm.dtActual.value       = aDato[18];
    frm.dtFechaRef.value     = aDato[14];

//  }
  fSetPanelStatus();
}

function fSetPanelStatus(){
  if(lPermiteEdicion){
    if (frm.lPagado.value == 1)
      FRMPanel.fSetTraStatus("Add,");
    if(frm.lPagado.value == 0){
      if(FRMListado.fGetLength() > 0)
        FRMPanel.fSetTraStatus("Add,Mod,");
      else if(top.opener && top.opener.fMostrarGenerarFicha && top.opener.fMostrarGenerarFicha())
        FRMPanel.fSetTraStatus("Add,");
      else
        FRMPanel.fSetTraStatus(",");
    }
  }
}

function fGuardarA(){
  var lGuardar = true;
  frm.dImportePagado.value = fEliminaCaracteres(frm.dImportePagado.value);
  if(frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "" && frm.iConsecutivo.value != ""){
    if(fValidaTodo()){
      frm.lPagadoBOX.checked = true;
      frm.lPagado.value = 1;
      if(fEngSubmite("pgTRARegRefPago.jsp","CIdPagos")){
        lRegistroUno = true;
        cEjercicioRegistro += (cEjercicioRegistro=="")?"":",";
        cEjercicioRegistro += frm.iEjercicio.value;
        cNumSolicRegistro += (cNumSolicRegistro=="")?"":",";
        cNumSolicRegistro += frm.iNumSolicitud.value;
        cConsecRegistro += (cConsecRegistro=="")?"":",";
        cConsecRegistro += frm.iConsecutivo.value;
        cRefNumRegistro += (cRefNumRegistro=="")?"":",";
        cRefNumRegistro += frm.cRefNumerica.value;
        cRefAlfaNumRegistro += (cRefAlfaNumRegistro=="")?"":",";
        cRefAlfaNumRegistro += frm.cRefAlfaNum.value;
        fCancelar();
      }
      else
        fAlert('Por el momento no pudo enviarse su petición, intente nuevamente');
    }
  }
}

function fModificar(){
  if(parseInt(frm.iRefNumerica.value,10) <= 0)
    fAlert('\n\n - Este pago no tiene referencia numérica asociada, favor de generar movimiento correctamente.');
  else{

    if(frm.dImportePagado.value ==  "$ 0.00" || frm.dImportePagado.value == ""){
      frm.dImportePagado.value = frm.cImportePagar.value;
      frm.dtPago.value = frm.dtActual.value.substring(2,10);
    }


    frm.cRefAlfaNum.value = fTrim(frm.cRefAlfaNum.value);
    frm.cFormatoSAT.value = fTrim(frm.cFormatoSAT.value);
    FRMListado.fSetDisabled(true);
    FRMPanel.fSetTraStatus("Sav,Can,");
    fDisabled(false,"Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,iEjercicio,iNumSolicitud,cDscTramite,cDscConcepto,iRefNumerica,cImportePagar,lPagadoBOX,");
  }
}

function fCancelar(){
  FRMListado.fSetDisabled(false);
  fSetPanelStatus();
  fDisabled(true);
  lNuevo = false;
}

function fValidaTodo(){
  frm.cRefAlfaNum.value = fTrim(frm.cRefAlfaNum.value);
  frm.cFormatoSAT.value = fTrim(frm.cFormatoSAT.value);
  cMsg = fValElements("");
  if(!lNuevo){
    if(frm.dtPago.value != "")
      if(!fComparaFecha(frm.dtPago.value, frm.dtActual.value, true))
        cMsg += '\n - La fecha de pago no puede ser mayor a la fecha actual.';
    // La siguiente validación se elimina para permitir registrar pagos hechos por el usuario de forma previa
    // Solicitado por Tania el 23/Oct/2007
    //if(frm.dtPago.value != "")
      //if(!fComparaFecha(frm.dtFechaRef.value, frm.dtPago.value, true))
        //cMsg += '\n - La fecha de pago no puede ser menor \na la fecha de Generación de Ficha.';
  }


  if(parseFloat(frm.dImportePagado.value, 10) <= 0)
    cMsg += '\n - El importe pagado debe ser positivo.';
  if(frm.cRefAlfaNum.value == "" && frm.cFormatoSAT.value == "")
    cMsg += '\n - Debe proporcionar Referencia Alfanumérica o formato SAT.';
  if(frm.cRefAlfaNum.value != "" && frm.iCveBanco.value < 1)
    cMsg += '\n - Debe especificar el banco en que se realizó el pago.';
  if(cMsg != ""){
    fAlert(cMsg);
    return false;
  }
  cMsg = "";
  if(!lNuevo){
    if(parseFloat(frm.dImportePagado.value,10) != parseFloat(frm.dImportePagar.value,10))
      cMsg += "\nEl importe a pagar y el importe pagado no son iguales.";
  }
  if(frm.cRefAlfaNum.value != "" && frm.cFormatoSAT.value != "")
    cMsg += "\nEstá proporcionando referencia alfanumérica y formato SAT simultáneamente.";
  if(cMsg != "")
      return confirm(cAlertMsgGen + "\n" + cMsg + "\n\n¿Desea continuar con estos datos?");
  return true;
}

function fImprimir(){
  self.focus();
  window.print();
}

function fCerrarVentana(){
  lTodos  = true;
  lCierra = true;
  for(var i=0; i<aPagos.length; i++)
    if(aPagos[i][6] == "0" && parseInt(aPagos[i][14],10) > 0){
      lTodos = false;
      break;
    }
  if(!lTodos)
    lCierra = confirm(cAlertMsgGen + "\n\nNo se han registrado todos los pagos.\n¿Desea salir del registro de pagos?");
  if(lCierra){
    if(lRegistroUno){
      if(top.opener)
        if (top.opener.fPagosRegistro)
          top.opener.fPagosRegistro(top, cEjercicioRegistro, cNumSolicRegistro, cConsecRegistro, cRefNumRegistro, cRefAlfaNumRegistro);
    }else
      if(top.opener)
        if (top.opener.fPagosRegistro)
          top.opener.fPagosRegistro(top);
  }
}

function fGetModoReimpresion(){
  return true;
}

function fGetAResReimpresion(){
  var aReimpresion = new Array();
  var iNumDep = 0;
  for(var i=0; i<aPagos.length; i++)
    if(aPagos[i][6] == "0" && parseInt(aPagos[i][14],10) > 0){
      iNumDep++;
      aReimpresion[aReimpresion.length] = new Array();
      aReimpresion[aReimpresion.length-1][0] = iNumDep;
      aReimpresion[aReimpresion.length-1][1] = aPagos[i][14];
      aReimpresion[aReimpresion.length-1][2] = aPagos[i][8];
      aReimpresion[aReimpresion.length-1][3] = aPagos[i][15];
    }
  return aReimpresion;
}
function fNuevo(){
  lNuevo = true;
  FRMPanel.fSetTraStatus("UpdateBegin");
  fBlanked("Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,iEjercicio,iNumSolicitud,cDscTramite,");
  fDisabled(false,"Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,iEjercicio,iNumSolicitud,cDscTramite,lPagadoBOX,","--");
  FRMListado.fSetDisabled(true);
 }
function fAbreGenerarFicha(){
  fAbreSubWindowPermisos("pgGeneraFicha", "800", "600");
}
function fGetDatosPersona(frmEmergente){
  frmEmergente.fSetDatosSolicitante(top.opener.frm.iCveSolicitante.value,top.opener.frm.cRFC.value,
  frm.Persona_cNomRazonSocial.value,frm.Persona_cDscDomicilio.value,top.opener.frm.iCveTramite.value,
  top.opener.frm.iCveModalidad.value,frm.iEjercicio.value,frm.iNumSolicitud.value)
}
function fDatosReferenciaAlfanumerica(top){
  top.close();
  fCargaPagos();
}
function fGuardar(){
  fValidaRefNumerica();
}
function fValidaRefNumerica(){
  frm.hdLlave.value = "iEjercicio,iCveConcepto,";
  frm.hdSelect.value =
                      "SELECT TM.ICVETRAMITE, TM.ICVEMODALIDAD,RC.IREFNUMERICAINGRESOS,C.ICVECONCEPTO FROM TRAREFERENCIACONCEPTO RC " +
                      "JOIN TRACONCEPTOPAGO C ON C.ICVECONCEPTO = RC.ICVECONCEPTO " +
                      "JOIN TRACONCEPTOXTRAMMOD TM ON TM.ICVECONCEPTO=C.ICVECONCEPTO " +
                      "JOIN TRAREGSOLICITUD S ON S.ICVETRAMITE = TM.ICVETRAMITE AND S.ICVEMODALIDAD=TM.ICVEMODALIDAD " +
                      "WHERE RC.IEJERCICIO = "+frm.iEjercicio.value+
                      " AND RC.IREFNUMERICAINGRESOS= " +frm.iRefNumerica.value
                      " AND substr((char(S.TSREGISTRO)),1,10) BETWEEN RC.DTINIVIGENCIA AND RC.DTFINVIGENCIA " +
                      " AND S.IEJERCICIO="+frm.iEjercicio.value+
                      " AND S.INUMSOLICITUD= "+frm.iNumSolicitud.value;
  /*"SELECT " +
		"ICVECONCEPTO " +
		"FROM TRAREFERENCIACONCEPTO " +
		"where IEJERCICIO = " + frm.iEjercicio.value +
		" and IREFNUMERICAINGRESOS = " + frm.iRefNumerica.value +
		" order by DTINIVIGENCIA DESC";*/
  fEngSubmite("pgConsulta.jsp","ValidaRefNumerica");
}
function fGuardarValidado(){
  frm.hdBoton.value = "Guardar";
  frm.dImportePagado.value = fEliminaCaracteres(frm.dImportePagado.value);
  frm.cImportePagar.value = fEliminaCaracteres(frm.cImportePagar.value);
  frm.lPagadoBOX.checked = true;
  frm.lPagado.value = 1;

  if(fValidaTodo()==true){
    if(fEngSubmite("pgTRARegRefPago.jsp","CIdPagos")){
      FRMPanel.fSetTraStatus("UpdateComplete");
      fDisabled(true);
      FRMListado.fSetDisabled(false);
      lNuevo = false;
    }
  }
}

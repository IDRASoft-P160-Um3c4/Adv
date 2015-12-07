// MetaCD=1.0
// Title: pgBuscaCitaCIS.js
// Description: JS para realizar búsqueda de cistas en CIS
// Company: Tecnología InRed S.A. de C.V.
// Author: Rafael Miranda Blumenkron
// Comentarios: Esta pantalla se puede ser llamada por otra y asignarle a esta los valores del solicitante
//              para esto la pantalla opener debe de tener las funciones fDatosReferencia(aResDatosRef,frmEmergente) y
//              fGetDatosPersona(frmEmergente).
//              a fGetDatosPersona(frmEmergente) se le manda el apuntador al frame de esta ventana, y en
//              esa función deben de ejecutar la función
//              frmEmergente.fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
//                        iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
//                        iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
//                        cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio,iCveTramite,iCveModalidad,
//                        lDesactivarFechas, lBuscarSolicitante, cFechaInicial, cFechaFinal)
//              En fDatosReferencia(aResDatosRef,frmEmergente) se manda el aRes con la información de la cita
//              seleccionada, el contenido del arreglo en sus diferentes posiciones es el siguiente:
//              0 - No de deposito, 1 - referencia númerica,  2 - referencia alfanumérica,
//              3 - Importe con formato, 4 - Area recaudadora, 5 - sucursal banamex,
//              6 - cuenta banamex, 7 - importe sin formato, 8 - Cve del concepto
var cTitulo = "";
var FRMListado = "", FRMListadoUsuCIS = "";
var frm;
var CveModalidad = 0;
var CveTramite = 0;
var lDesactivarFechas = false;
var BuscarSolicitante = true;
var aResCitasCIS = new Array();
var aResSolicitanteCIS = new Array();
var lAutoCargadoUsu = false;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pgBuscaCitaCIS.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
  fSetWindowTitle();
}

 // SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",0,"100%","","center");
      TextoSimple(cTitulo);
    FTDTR();

    ITRTD("",0,"","","top");
      InicioTabla("",0,"95%","","center");
        ITRTD("",0,"","","center","middle");
          TDEtiCampo(false,"EEtiqueta",0,"Fecha Inicial:","dtInicial","",10,10,"Fecha inicial del periodo","fMayus(this);");
        FITD("",0,"","","center","middle");
          TDEtiCampo(false,"EEtiqueta",0,"Fecha Final:","dtFinal","",10,10,"Fecha final del periodo","fMayus(this);");
        FITD("",0,"","","center","middle");
          BtnImg("Buscar","lupa","fNavega();");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","","center","center");
      InicioTabla("",0,"95%","","center");
        ITRTD("ESTitulo");
          TextoSimple("Listado de Citas localizadas en el periodo proporcionado");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","95","center","center");
      IFrame("IListadoCita","95%","110","Listado.js","yes",true);
    FTDTR();

    ITRTD("","","","35","center");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"","50%","center");
          Liga("Buscar Solicitante","fBuscarSolicitante();","Búsqueda de solicitante en el sistema...");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"95%","","center","top");
      InicioTabla("",0,"","","center");
        ITR();
          TDEtiCampo(false,"EEtiqueta",0,"R.F.C.:","cRFC","",16,16," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiCampo(false,"EEtiqueta",0,"Nombre o<br>Razón Social:","cNomRazonSocial","",75,95," Nombre o Razón Social","fMayus(this);","","",false,"EEtiquetaL",0);
        FITR();
          TDEtiAreaTexto(false,"EEtiqueta",0,"Domicilio:",107,2,"cDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",3);
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","","center","center");
      InicioTabla("",0,"95%","","center");
        ITRTD("ESTitulo");
          TextoSimple("Listado de solicitantes registrados en CIS");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","","center","center");
      IFrame("IListadoUsuCIS","95%","130","Listado.js","yes",true);
    FTDTR();

/*
    ITRTD("",0,"","","top");
      InicioTabla("",0,"95%","","center");
        fRequisitoModalidad(true);
      FinTabla();
    FTDTR();

    ITRTD("","","","35","center");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"","50%","center");
          Liga("Calcular","fCalcula();","Cálcula el importe a pagar");
        FITD();
          Liga("Búsqueda de Solicitante","fBuscarSolicitante();","Búsqueda de solicitante...");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","40","center","top");
      InicioTabla("",0,"90%","","center");
        ITRTD("ENormalC",0,"","20","center","top");
          TextoSimple("Seleccione el Solicitante que corresponda en el listado de ingresos. <br> "+
                      "Si no existe en el listado de ingresos de clic en <b>registrar solicitante en ingresos</b> \n"+
                      "y selecciónelo");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","30","center","center");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"30%","","center");
          Liga("Registrar Solicitante en Ingresos","fRegistraSolIng();","Registrar solicitante en Ingresos...");
        FITD("",0,"30%","","center");
          Liga("Generar Fichas","fGeneraFichas();","Genera las fichas de pago");
        FITD("",0,"30%","","center");
          BtnImg("Aceptar","aceptar","fAceptar();","");
        FTDTR();
      FinTabla();
    FTDTR();
*/
  FinTabla();

  Hidden("iCvePersona","");
  Hidden("cCURP","");
  Hidden("cRPA","");
  Hidden("iTipoPersona","");
  Hidden("cNombre","");
  Hidden("cApPaterno","");
  Hidden("cApMaterno","");
  Hidden("cCorreoE","");
  Hidden("cPseudonimoEmp","");

  Hidden("iCveDomicilio","");
  Hidden("iCveTipoDomicilio","");
  Hidden("cCalle","");
  Hidden("cNumExterior","");
  Hidden("cNumInterior","");
  Hidden("cColonia","");
  Hidden("cCodPostal","");
  Hidden("cTelefono","");
  Hidden("iCvePais","");
  Hidden("cDscPais","");
  Hidden("iCveEntidadFed","");
  Hidden("cDscEntidadFed","");
  Hidden("iCveMunicipio","");
  Hidden("cDscMunicipio","");
  Hidden("iCveLocalidad","");
  Hidden("cDscLocalidad","");
  Hidden("lPredeterminado","");
  Hidden("cDscTipoDomicilio","");
  Hidden("cDscDomicilio","");

  Hidden("iCveRepLegal","");
  Hidden("iCveDomRepLegal","");

  Hidden("iCvePersonaCIS","");
  Hidden("iEjercicioCita","");
  Hidden("iIdCita","");

  Hidden("iCveUsuario");
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  frm.iCveUsuario.value = fGetIdUsrSesion();

  frm.cNomRazonSocial.disabled = true;
  frm.cDomicilio.disabled = true;
  frm.cRFC.disabled = true;

  FRMListado = fBuscaFrame("IListadoCita");
  FRMListado.fSetControl(self);
  /*
  FRMListado.fSetTitulo(",Descripción,Unidades,Importe,");
  FRMListado.fSetCampos("5,6,");
  FRMListado.fSetObjs(0,"Caja")
  FRMListado.fSetObjs(2,"Campo");
  FRMListado.fSetAlinea("center,left,center,right,");
  FRMListado.fSetColTit( 3,"right",100)
  */
  FRMListadoUsuCIS = fBuscaFrame("IListadoUsuCIS");
  FRMListadoUsuCIS.fSetControl(self);
  FRMListadoUsuCIS.fSetSelReg(2);
  /*
  FRMListadoUsuIng.fSetTitulo("RFC,Nombre,Domicilio,");
  FRMListadoUsuIng.fSetCampos("10,13,15,");
  */
/*
  frm.hdBoton.value="Primero";
  fCargaTramites();
*/
  if(top.opener){
    if(top.opener.fGetDatosPersona)
      top.opener.fGetDatosPersona(this);
  }
}

function fNavega(){
  if(frm.dtInicial.value!=""){
    if(frm.dtFinal.value == "")
      frm.dtFinal.value = frm.dtInicial.value;
    frm.hdBoton.value = "BuscaCitasCIS";
    fEngSubmite("pgTRACitasCIS.jsp","IdCitasCIS");
  }
}

function fBuscarSolicitanteCIS(){
  if(frm.cRFC.value!=""){
    frm.hdBoton.value = "BuscaSolicitanteCIS";
    fEngSubmite("pgTRACitasCIS.jsp","IdSolicitanteCIS");
  }
}

function fRegistraSolCIS(){
  if(frm.cRFC.value!=""){
    frm.hdBoton.value = "RegistraSolicitanteCIS";
    fEngSubmite("pgTRACitasCIS.jsp","IdRegSolicitanteCIS");
  }else
    fAlert("Debe de seleccionar un solicitante.")
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert(cError);
    return;
  }else{

    if(cId == "IdCitasCIS"){
      for(var i=0; i<aRes.length; i++)
        aResCitasCIS[aResCitasCIS.length] = fCopiaArreglo(aRes[i]);
      FRMListado.fSetListado(aResCitasCIS);
      FRMListado.fShow();
      FRMListado.fSetLlave(cLlave);
      if(lAutoCargadoUsu == false)
        fBuscarSolicitanteCIS();
    }

    if(cId == "IdSolicitanteCIS"){
      lAutoCargadoUsu = true;
      for(var i=0; i<aRes.length; i++)
        aResSolicitanteCIS[aResSolicitanteCIS.length] = fCopiaArreglo(aRes[i]);
      FRMListadoUsuCIS.fSetListado(aResSolicitanteCIS);
      FRMListadoUsuCIS.fShow();
      FRMListadoUsuCIS.fSetLlave(cLlave);
    }

    if(cId == "IdRegSolicitanteCIS"){
      fBuscarSolicitanteCIS();
    }
  }
}
/*
function fTramiteOnChangeLocal(){
  if(CveTramite > 0){
    fSelectSetIndexFromValue(frm.iCveTramite,CveTramite);
    frm.cCveTramite.blur();
  }
}
*/
function fSelReg(aDato){
  alert("fSelReg=" + aDato);
/*  frm.iEjercicioCita.value = aDato[xx];
  frm.iIdCita.value        = aDato[xy];*/
}

function fSelReg2(aDato){
  alert("fSelReg2=" + aDato);
/*  frm.iCvePersonaCIS.value = aDato[xx];*/
}

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

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio, iCveTramite, iCveModalidad,
                         lDesactivarFechas, lBuscarSolicitante, cFechaInicial, cFechaFinal){
  frm.iCvePersona.value     = iCvePersona
  frm.cRFC.value            = cRFC;
  frm.cRPA.value            = cRPA;
  frm.iTipoPersona.value    = iTipoPersona;
  frm.cNomRazonSocial.value = cNomRazonSocial;
  frm.cNombre.value         = cNomRazonSocial;
  if (iTipoPersona == "1")
    frm.cNomRazonSocial.value += " " + cApPaterno + " " + cApMaterno;
  frm.cApPaterno.value      = cApPaterno;
  frm.cApMaterno.value      = cApMaterno;
  frm.cCorreoE.value        = cCorreoE;
  frm.cPseudonimoEmp.value  = cPseudonimoEmp;

  frm.iCveDomicilio.value   = iCveDomicilio;
  frm.iCveTipoDomicilio.value = iCveTipoDomicilio;
  frm.cCalle.value          = cCalle;
  frm.cNumExterior.value    = cNumExterior;
  frm.cNumInterior.value    = cNumInterior;
  frm.cColonia.value        = cColonia;
  frm.cCodPostal.value      = cCodPostal;
  frm.cTelefono.value       = cTelefono;
  frm.iCvePais.value        = iCvePais;
  frm.cDscPais.value        = cDscPais;
  frm.iCveEntidadFed.value  = iCveEntidadFed;
  frm.cDscEntidadFed.value  = cDscEntidadFed;
  frm.iCveMunicipio.value   = iCveMunicipio;
  frm.cDscMunicipio.value   = cDscMunicipio;
  frm.iCveLocalidad.value   = iCveLocalidad;
  frm.cDscLocalidad.value   = cDscLocalidad;
  frm.lPredeterminado.value = lPredeterminado;
  frm.cDscTipoDomicilio.value = cDscTipoDomicilio;
  frm.cDscDomicilio.value   = cDscDomicilio;
  frm.cDomicilio.value      = cDscDomicilio;
  if(cDscDomicilio == "")
    frm.cDomicilio.value = cCalle+", No. "+cNumExterior+" Int. "+cNumInterior+" Col. "+cColonia+", "+cDscMunicipio+", "+cDscEntidadFed+", "+cDscPais;

  if (iCveTramite && iCveModalidad){
    CveTramite   = iCveTramite;
    CveModalidad = iCveModalidad;
  }

  if (cFechaInicial)
    frm.dtInicial.value = cFechaInicial;
  if (cFechaFinal)
    frm.dtFinal.value = cFechaFinal;
  else
    frm.dtFinal.value = frm.dtInicial.value;

  if (lBuscarSolicitante == false)
    BuscarSolicitante = false;

  if (cFechaInicial){
    frm.dtInicial.disabled = lDesactivarFechas;
    frm.dtFinal.disabled   = lDesactivarFechas;
    DesactivarFechas       = lDesactivarFechas;
    fNavega();
  }

  if(lAutoCargadoUsu && !iCveTramite && !iCveModalidad && !cFechaInicial)
    fBuscarSolicitanteCIS();
}

function fValoresRepLegal(){
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetParametrosConsulta(frmDestino){
  var lShowPersona     = true;
  var lShowRepLegal    = false;
  var lEditPersona     = true;
  var lEditDomPersona  = true;
  var lEditRepLegal    = false;
  var lEditDomRepLegal = false;
  if (frmDestino){
    if (frmDestino.setShowValues)
      frmDestino.setShowValues(lShowPersona, lShowRepLegal, "");
    if (frmDestino.setEditValues)
      frmDestino.setEditValues(lEditPersona, lEditDomPersona, lEditRepLegal, lEditDomRepLegal);
  }
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetClaves(frmDestino){
  if (frmDestino.setClaves)
    frmDestino.setClaves(frm.iTipoPersona.value, frm.iCvePersona.value, frm.iCveDomicilio.value, frm.iCveRepLegal.value, frm.iCveDomRepLegal.value);
}

function fDatosReferencia(paraResDatos,frmPopup){
  /*
  aResDatosRef = new Array();
  for (var i=0; i<paraResDatos.length; i++)
    aResDatosRef[aResDatosRef.length] = fCopiaArreglo(paraResDatos[i]);
  if(frmPopup)
     frmPopup.close();
     */
}

function fAceptar(){
  if(top.opener)
    if(top.opener.fDatosReferencia)
      top.opener.fDatosReferencia(aResDatosRef,top);
}

function fBuscarSolicitante(){
  if(BuscarSolicitante)
    fAbreSolicitante();
}

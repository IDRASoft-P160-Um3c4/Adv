// MetaCD=1.0
 // Title: pg111020010.js
 // Description:
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
var frm, FRMSolic, FRMTramite, FRMSolicitud;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));

function fBefLoad(){
  cPaginaWebJS = "pg111020190.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
//  fSetWindowTitle();
}

function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();

    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta("Solicitante y<br>Rep. Legal a Distancia|Trámites y Requisitos a Distancia|Solicitudes a Distancia|",
                   "pg111020191.js|pg111020192.js|pg111020193.js|",
                   "PEM", "99%", "99%",true);
    FTDTR();
    Hidden("iCvePersona");
    Hidden("cRFC");
    Hidden("cRPA");
    Hidden("iTipoPersona");
    Hidden("cNomRazonSocial");
    Hidden("cNombreSolo");
    Hidden("cApPaterno");
    Hidden("cApMaterno");
    Hidden("cCorreoE");
    Hidden("cPseudonimoEmp");

    Hidden("iCveDomicilio");
    Hidden("iCveTipoDomicilio");
    Hidden("cCalle");
    Hidden("cNumExterior");
    Hidden("cNumInterior");
    Hidden("cColonia");
    Hidden("cCodPostal");
    Hidden("cTelefono");
    Hidden("iCvePais");
    Hidden("cDscPais");
    Hidden("iCveEntidadFed");
    Hidden("cDscEntidadFed");
    Hidden("iCveMunicipio");
    Hidden("cDscMunicipio");
    Hidden("iCveLocalidad");
    Hidden("cDscLocalidad");
    Hidden("lPredeterminado");
    Hidden("cDscTipoDomicilio");
    Hidden("cDscDomicilio");

    Hidden("iCveRepLegal");
    Hidden("cRFC2");
    Hidden("cRPA2");
    Hidden("iTipoPersona2");
    Hidden("cNomRazonSocial2");
    Hidden("cNombreSolo2");
    Hidden("cApPaterno2");
    Hidden("cApMaterno2");
    Hidden("cCorreoE2");
    Hidden("cPseudonimoEmp2");

    Hidden("iCveDomRepLegal");
    Hidden("iCveTipoDomicilio2");
    Hidden("cCalle2");
    Hidden("cNumExterior2");
    Hidden("cNumInterior2");
    Hidden("cColonia2");
    Hidden("cCodPostal2");
    Hidden("cTelefono2");
    Hidden("iCvePais2");
    Hidden("cDscPais2");
    Hidden("iCveEntidadFed2");
    Hidden("cDscEntidadFed2");
    Hidden("iCveMunicipio2");
    Hidden("cDscMunicipio2");
    Hidden("iCveLocalidad2");
    Hidden("cDscLocalidad2");
    Hidden("lPredeterminado2");
    Hidden("cDscTipoDomicilio2");
    Hidden("cDscDomicilio2");
  FinTabla();
  fFinPagina();
}

function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  fPagFolder(1);
  FRMSolic = fBuscaFrame("PEM1");
  FRMTramite   = fBuscaFrame("PEM2");
  FRMSolicitud = fBuscaFrame("PEM3");
}

function fFocusBuscarSolicitante(){
  if (FRMSolic)
    FRMSolic.fFocusBuscaSolicitante();
}

function fResultado(aRes, cId){ // Recibe el resultado en el Vector aRes.
}

function fFolderOnChange( iPag ) { // iPag indica a la página que se desea cambiar
  if(iPag == 1)
    iPagAnt = 1;
  if(iPag == 2 && iPagAnt > 2)
    iPagAnt = 2;
  if(iPag == 3 && iPagAnt > 3)
    iPagAnt = 3;

  if(iPag>1){
    if(iPag==2 && iPagAnt <= iPag) {
      if (fValidaCambioPag(FRMTramite)){
        FRMTramite.fCargaTramites();
//        FRMTramite.fCargaOficDeptoUsr(false);
        iPagAnt = iPag;
      }else{
        fAlert("\n ¡Debe Seleccionar un Solicitante y un Representante legal!");
        fFocusBuscarSolicitante();
        return false;
      }
    }

    if(iPag==3 && iPagAnt <= iPag) {
      if (fValidaCambioPag(FRMSolicitud)){
        FRMSolicitud.fNavega();
        iPagAnt = iPag;
      }else{
        fAlert("\n ¡Debe Seleccionar un Solicitante y un Representante legal!");
        fFocusBuscarSolicitante();
        return false;
      }
    }
  }
}

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio){
  frm.iCvePersona.value     = iCvePersona
  frm.cRFC.value            = cRFC;
  frm.cRPA.value            = cRPA;
  frm.iTipoPersona.value    = iTipoPersona;
  frm.cNombreSolo.value     = cNomRazonSocial;
  frm.cNomRazonSocial.value = cNomRazonSocial;
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
}

// Definir en paginas que requieran datos de persona y representante legal
function fValoresRepLegal(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio){
  frm.iCveRepLegal.value     = iCvePersona
  frm.cRFC2.value            = cRFC;
  frm.cRPA2.value            = cRPA;
  frm.iTipoPersona2.value    = iTipoPersona;
  frm.cNombreSolo2.value     = cNomRazonSocial;
  frm.cNomRazonSocial2.value = cNomRazonSocial;
  if (iTipoPersona == "1")
    frm.cNomRazonSocial2.value += " " + cApPaterno + " " + cApMaterno;
  frm.cApPaterno2.value      = cApPaterno;
  frm.cApMaterno2.value      = cApMaterno;
  frm.cCorreoE2.value        = cCorreoE;
  frm.cPseudonimoEmp2.value  = cPseudonimoEmp;

  frm.iCveDomRepLegal.value   = iCveDomicilio;
  frm.iCveTipoDomicilio2.value = iCveTipoDomicilio;
  frm.cCalle2.value          = cCalle;
  frm.cNumExterior2.value    = cNumExterior;
  frm.cNumInterior2.value    = cNumInterior;
  frm.cColonia2.value        = cColonia;
  frm.cCodPostal2.value      = cCodPostal;
  frm.cTelefono2.value       = cTelefono;
  frm.iCvePais2.value        = iCvePais;
  frm.cDscPais2.value        = cDscPais;
  frm.iCveEntidadFed2.value  = iCveEntidadFed;
  frm.cDscEntidadFed2.value  = cDscEntidadFed;
  frm.iCveMunicipio2.value   = iCveMunicipio;
  frm.cDscMunicipio2.value   = cDscMunicipio;
  frm.iCveLocalidad2.value   = iCveLocalidad;
  frm.cDscLocalidad2.value   = cDscLocalidad;
  frm.lPredeterminado2.value = lPredeterminado;
  frm.cDscTipoDomicilio2.value = cDscTipoDomicilio;
  frm.cDscDomicilio2.value   = cDscDomicilio;
}

function fValidaCambioPag(theTarget){
  if (theTarget){
    if (theTarget.fValoresDisponibles){
      if (frm.iTipoPersona.value != "1"){
        if (frm.iCveRepLegal.value != "" && frm.iCveDomRepLegal.value != "" &&
            frm.iCvePersona.value != "" && frm.iCveDomicilio.value != ""){
        }else
          return false;
      }
      if (frm.iCvePersona.value != "" && frm.iCveDomicilio.value != ""){
        theTarget.fValoresDisponibles(frm);
        return true;
      }else
        return false;
    }
  }
}

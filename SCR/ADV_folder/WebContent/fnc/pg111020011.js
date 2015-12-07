// MetaCD=1.0
 // Title: pg111020011.js
 // Description:
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Jorge Arturo Wong Mozqueda<dd>Rafael Miranda Blumenkron
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111020011.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","","center");
      ITRTD("",0,"","1","center");
        InicioTabla("",0,"100%","","center");
          ITD("",0,"0","","center","left");
            LigaNombre("Búsqueda de Promovente","fAbreSolicitante();","Búsqueda de promovente...","BuscaSolicitante");
          FITD();
//            LigaNombre("Buscar Cita en CIS","fAbreBuscaCitaCIS();","Buscar cita en CIS...","BuscaCita");
          FITD();
            LigaNombre("Registrar Trámite","window.parent.fPagFolder(2);","Registrar trámite...","RegistraTramite");

          FTD();
        FinTabla();
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"0","","","",1);
          ITRTD("ETablaST",6,"","","center");
            TextoSimple("Datos del Promovente");
          FTDTR();
          ITR();
             TDEtiCampo(false,"EEtiqueta",0,"Nombre o Razón Social:","cNomRazonSocial","",95,95," Nombre o Razón Social","fMayus(this);","","",false,"EEtiquetaL",5);
          FITR();
             TDEtiCampo(false,"EEtiqueta",0,"R.F.C.:","cRFC","",14,14," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",0);
             TDEtiCampo(false,"EEtiqueta",0,"Teléfono:","cTelefono","",20,20," Teléfono","fMayus(this);","","",false,"EEtiquetaL",0);
             TDEtiCampo(false,"EEtiqueta",0,"Correo Electrónico:","cCorreoE","",30,30," Correo Electrónico","fMayus(this);","","",false,"EEtiquetaL",0);
          FITR();
          FITR();
             TDEtiAreaTexto(false,"EEtiqueta",0,"Domicilio:",94,3,"cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",5);
          FTR();
        FinTabla();
      FTDTR();
        ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"0","","center","",1);
          ITRTD("ETablaST",6,"","","center");
            TextoSimple("Datos del Representante Legal");
          FTDTR();
          ITR();
            TDEtiCampo(false,"EEtiqueta",0," Nombre o Razón Social:","cNomRazonSocial2","",95,95," Nombre o Razón Social","fMayus(this);","","",false,"EEtiquetaL",5);
          FITR();
            TDEtiCampo(false,"EEtiqueta",0," R.F.C.:","cRFC2","",14,14," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",0);
            TDEtiCampo(false,"EEtiqueta",0," Teléfono:","cTelefono2","",20,20," Teléfono","fMayus(this);","","",false,"EEtiquetaL",0);
            TDEtiCampo(false,"EEtiqueta",0," Correo Electrónico:","cCorreoE2","",30,30," Correo Electrónico","fMayus(this);","","",false,"EEtiquetaL",0);
          FITR();
            TDEtiAreaTexto(false,"EEtiqueta",0,"Domicilio:",94,3,"cDscDomicilio2","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",5);
          FTR();
        FinTabla();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel11","95%","34","Paneles.js");
      FTDTR();
      Hidden("iCvePersona");
      Hidden("cRPA");
      Hidden("iTipoPersona");
      Hidden("cApPaterno");
      Hidden("cApMaterno");
      Hidden("cPseudonimoEmp");

      Hidden("iCveDomicilio");
      Hidden("iCveTipoDomicilio");
      Hidden("cCalle");
      Hidden("cNumExterior");
      Hidden("cNumInterior");
      Hidden("cColonia");
      Hidden("cCodPostal");
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

      Hidden("iCveRepLegal");
      Hidden("cRPA2");
      Hidden("iTipoPersona2");
      Hidden("cApPaterno2");
      Hidden("cApMaterno2");
      Hidden("cPseudonimoEmp2");

      Hidden("iCveDomRepLegal");
      Hidden("iCveTipoDomicilio2");
      Hidden("cCalle2");
      Hidden("cNumExterior2");
      Hidden("cNumInterior2");
      Hidden("cColonia2");
      Hidden("cCodPostal2");
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
    FinTabla();
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel11");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow(",");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  fAbreSolicitante();
}

function fFocusBuscaSolicitante(){
  fFocusLiga(document, 'BuscaSolicitante');
}

function fFocusRegistraTramite(){
  fFocusLiga(document, 'RegistraTramite');
}

function fImprimir(){
  self.focus();
  window.print();
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio){
  frm.iCvePersona.value     = iCvePersona
  frm.cRFC.value            = cRFC;
  frm.cRPA.value            = cRPA;
  frm.iTipoPersona.value    = iTipoPersona;
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
  if (window.parent){
    if (window.parent.fValoresPersona)
      window.parent.fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                              iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                              iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                              cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio);
  }
  fFocusRegistraTramite();
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
  if (window.parent){
    if (window.parent.fValoresRepLegal)
      window.parent.fValoresRepLegal(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                               iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                               iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                               cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio);
  }
  fFocusRegistraTramite();
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetParametrosConsulta(frmDestino){
  var lShowPersona     = true;
  var lShowRepLegal    = true;
  var lEditPersona     = true;
  var lEditDomPersona  = true;
  var lEditRepLegal    = true;
  var lEditDomRepLegal = true;
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

function fGetDatosPersona(objRef){
  if(window.parent)
    if(window.parent.document)
      if (window.parent.document.forms[0])
        FRMOrigen = window.parent.document.forms[0];
  if(objRef && FRMOrigen)
    if(objRef.fValoresPersona)
      objRef.fValoresPersona(
             FRMOrigen.iCvePersona.value, FRMOrigen.cRFC.value,
             FRMOrigen.cRPA.value, FRMOrigen.iTipoPersona.value,
             FRMOrigen.cNombreSolo.value, FRMOrigen.cApPaterno.value,
             FRMOrigen.cApMaterno.value, FRMOrigen.cCorreoE.value,
             FRMOrigen.cPseudonimoEmp.value,

             FRMOrigen.iCveDomicilio.value, FRMOrigen.iCveTipoDomicilio.value,
             FRMOrigen.cCalle.value, FRMOrigen.cNumExterior.value,
             FRMOrigen.cNumInterior.value, FRMOrigen.cColonia.value,
             FRMOrigen.cCodPostal.value, FRMOrigen.cTelefono.value,
             FRMOrigen.iCvePais.value, FRMOrigen.cDscPais.value,
             FRMOrigen.iCveEntidadFed.value, FRMOrigen.cDscEntidadFed.value,
             FRMOrigen.iCveMunicipio.value, FRMOrigen.cDscMunicipio.value,
             FRMOrigen.iCveLocalidad.value, FRMOrigen.cDscLocalidad.value,
             FRMOrigen.lPredeterminado.value, FRMOrigen.cDscTipoDomicilio.value,
             FRMOrigen.cDscDomicilio.value,
             0, 0, true, false,
             '01/01/2006', '30/03/2006');
             //parseInt(frm.iCveTramite.value,10),
             //parseInt(frm.iCveModalidad.value,10),
    else alert("No existe funcion");
  else alert("no existe ventana destino o fuente de datos");
}

function fValidaRUPA(){
    return true;
}

function getIniciaTramite(){
	return true;
}
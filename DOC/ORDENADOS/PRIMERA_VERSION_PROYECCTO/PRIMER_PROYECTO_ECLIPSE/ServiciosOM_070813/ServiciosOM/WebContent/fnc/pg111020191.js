// MetaCD=1.0
 // Title: pg111020011.js
 // Description:
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var iCveTitular;
 var lEncontro = false;
 var cFiltroTramites = "";

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111020191.js";
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
            LigaNombre("Búsqueda de Solicitante","fAbreSolicitante();","Búsqueda de solicitante...","BuscaSolicitante");
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
            TextoSimple("Datos del Solicitante");
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
      Hidden("hdLlave");
      Hidden("hdSelect");
      Hidden("idUser",fGetIdUsrSesion());
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
  fObtenerTituloTitular();
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
  var lEditPersona     = false;
  var lEditDomPersona  = false;
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
    else fAlert("No existe función");
  else fAlert("No existe ventana destino o fuente de datos");
}

function fGetlPermiteBusqueda(){
  return false;
}

function fObtenerTituloTitular(){
  frm.hdLlave.value = "iCveUsuario";
  frm.hdSelect.value = "SELECT " +
			"ICVETITULO, " +
			"ICVETITULAR " +
			"FROM DPOUSUARIOTITULO " +
			"WHERE ICVEUSUARIO = "+frm.idUser.value;

  fEngSubmite("pgConsulta.jsp","TituloTitular");
}

function fObtenPersona(){
  frm.hdLlave.value = "iCvePersona";
  frm.hdSelect.value = "SELECT " +
			"PER.cNomRazonSocial||' '||PER.cApPaterno||' '||PER.cApMaterno as cNomPersona, " +
			"PER.CRFC, " +
			"PER.CCORREOE, " +
			"DOM.CTELEFONO, " +
			"DOM.cCalle || ' No. ' || DOM.cNumExterior || ' Int. ' || DOM.cNumInterior || ' COL. ' || DOM.cColonia || ', ' || MUN.cNombre || ' (' || FED.cAbreviatura ||'.)' || ' C.P. ' || DOM.cCodPostal AS cDomicilio, " +
			"PER.ITIPOPERSONA AS ITIPOPER,"+
			"DOM.ICVEDOMICILIO AS ICVEDOM "+
                        "FROM GRLPERSONA PER " +
			"join GRLDOMICILIO DOM on PER.ICVEPERSONA = DOM.ICVEPERSONA " +
			"join GRLPAIS PAIS ON DOM.ICVEPAIS = PAIS.ICVEPAIS " +
			"join GRLENTIDADFED FED ON FED.ICVEPAIS = PAIS.ICVEPAIS AND FED.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
			"join GRLMUNICIPIO  MUN ON MUN.ICVEENTIDADFED = FED.ICVEENTIDADFED AND MUN.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
			"where PER.ICVEPERSONA = "+iCveTitular;

  fEngSubmite("pgConsulta.jsp","Persona");
}

function fCargaRepLegal(){
  frm.hdLlave.value = "iCveUsuario";
  frm.hdSelect.value = "SELECT " +
			"PER.cNomRazonSocial||' '||PER.cApPaterno||' '||PER.cApMaterno as cNomRepLegal, " +
			"PER.CRFC, " +
			"PER.CCORREOE, " +
			"DOM.CTELEFONO, " +
			"DOM.cCalle || ' No. ' || DOM.cNumExterior || ' Int. ' || DOM.cNumInterior || ' COL. ' || DOM.cColonia || ', ' || MUN.cNombre || ' (' || FED.cAbreviatura ||'.)' || ' C.P. ' || DOM.cCodPostal AS cDomicilio, " +
                        "REP.LPRINCIPAL as lPrin, " +
                        "REP.ICVEPERSONA AS iCveRL,"+
			"DOM.ICVEDOMICILIO AS iCveDomRL "+
			"FROM GRLREPLEGAL REP " +
			"join GRLPERSONA PER ON REP.ICVEPERSONA = PER.ICVEPERSONA " +
			"join GRLDOMICILIO DOM on PER.ICVEPERSONA = DOM.ICVEPERSONA " +
			"join GRLPAIS PAIS ON DOM.ICVEPAIS = PAIS.ICVEPAIS " +
			"join GRLENTIDADFED FED ON FED.ICVEPAIS = PAIS.ICVEPAIS AND FED.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
			"join GRLMUNICIPIO  MUN ON MUN.ICVEENTIDADFED = FED.ICVEENTIDADFED AND MUN.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
			"where ICVEEMPRESA = "+iCveTitular;

  fEngSubmite("pgConsulta.jsp","RepLegal");
}

function fCargaTramitesxOfic(){
  frm.hdNumReg.value = 100000;
  frm.hdLlave.value = "iCveOficina, iCveTramite";
  frm.hdSelect.value = "SELECT " +
			"ICVEOFICINA, " +
			"ICVETRAMITE " +
			"FROM TRATRAMITEXOFIC " +
			"where ICVEOFICINA in " +
			"( " +
			"   select " +
			"   icveoficina " +
			"   from GRLUSUARIOXOFIC " +
			"   where ICVEUSUARIO = " +frm.idUser.value+
			") ";

  fEngSubmite("pgConsulta.jsp","TraxOfic");
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){


   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }

   if(cId == "TituloTitular" && cError==""){
     if(aRes.length > 0){
       iCveTitular = aRes[0][1];
       fObtenPersona();
     }
   }

   if(cId == "Persona" && cError == ""){
     if(aRes.length > 0){
       frm.cNomRazonSocial.value = aRes[0][0];
       if(!window.parent.frm)
         window.parent.frm = window.parent.document.forms[0];
       window.parent.frm.cNomRazonSocial.value = aRes[0][0];
       frm.cRFC.value = aRes[0][1];
       window.parent.frm.cRFC.value = aRes[0][1];
       frm.cTelefono.value = aRes[0][3];
       frm.cCorreoE.value = aRes[0][2];
       frm.cDscDomicilio.value = aRes[0][4];
       window.parent.frm.cDscDomicilio.value = aRes[0][4];
       frm.iTipoPersona.value = aRes[0][5];
       window.parent.frm.iTipoPersona.value = aRes[0][5];
       frm.iCvePersona.value = iCveTitular;
       window.parent.frm.iCvePersona.value = iCveTitular;
       frm.iCveDomicilio.value = aRes[0][6];
       window.parent.frm.iCveDomicilio.value = aRes[0][6];
       fCargaRepLegal();
     }
   }

   if(cId == "RepLegal" && cError == ""){
     if(aRes.length > 0){
       for(var i = 0; i<aRes.length; i++)
         if(aRes[i][5] == 1){
	    frm.cNomRazonSocial2.value = aRes[i][0];
            window.parent.frm.cNomRazonSocial2.value = aRes[i][0];
	    frm.cRFC2.value = aRes[i][1];
	    frm.cTelefono2.value = aRes[i][3];
	    frm.cCorreoE2.value = aRes[i][2];
	    frm.cDscDomicilio2.value = aRes[i][4];
            window.parent.frm.cDscDomicilio2.value = aRes[i][4];
            frm.iCveRepLegal.value = aRes[i][6];
            window.parent.frm.iCveRepLegal.value = aRes[i][6];
            frm.iCveDomRepLegal.value = aRes[i][7];
            window.parent.frm.iCveDomRepLegal.value = aRes[i][7];
            lEncontro = true;
            break;
         }

         if(!lEncontro){
           frm.cNomRazonSocial2.value = aRes[0][0];
           window.parent.frm.cNomRazonSocial2.value = aRes[0][0];
	    frm.cRFC2.value = aRes[0][1];
	    frm.cTelefono2.value = aRes[0][3];
	    frm.cCorreoE2.value = aRes[0][2];
	    frm.cDscDomicilio2.value = aRes[0][4];
            window.parent.frm.cDscDomicilio2.value = aRes[0][4];
            frm.iCveRepLegal.value = aRes[0][6];
            window.parent.frm.iCveRepLegal.value = aRes[0][6];
            frm.iCveDomRepLegal.value = aRes[0][7];
            window.parent.frm.iCveDomRepLegal.value = aRes[0][7];
         }

         fCargaTramitesxOfic();
     }else{
       fAlert("No existe un Representante Legal asignado para ésta persona. \nFavor de verificarlo");
       fSalirSistema();
     }
   }

   if(cId == "TraxOfic" && cError == ""){
    cFiltroTramites = "";
    if(aRes.length > 0){
     for(var j = 0; j<aRes.length; j++)
       if(cFiltroTramites == "")
         cFiltroTramites = aRes[j][1];
       else
         cFiltroTramites += ","+aRes[j][1];

    // if(!window.parent.FRMTramite)
  //     window.parent.FRMTramite = window.parent.fBuscaFrame("PEM2");

//     window.parent.FRMTramite.fCargaTramites();
    }else{
      fAlert("No existen trámites relacionados a la Oficina de este Usuario.\nFavor de verificarlo con el administrador.");
      window.parent.FRMSolic.fSalirSistema();
    }
  }
 }
 function fSalirSistema(){
   fEngSubmite("pgDelUsrSes.jsp","CerrarSesion");
   fSalirTotal();
 }

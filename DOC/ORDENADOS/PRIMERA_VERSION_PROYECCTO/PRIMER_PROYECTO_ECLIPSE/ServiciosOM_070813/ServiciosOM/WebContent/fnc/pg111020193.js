// MetaCD=1.0
 // Title: pg111020013.js
 // Description: JS "Catálogo" de la entidad TRARegReqXTram
 // Company: Tecnología InRed S.A. de C.V.
 // Author: iCaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var cNumSolicitud = "";
 var cEjercicio = "";
 var iAux = 0;
 var aResSolicitudes = new Array();
 var aDatosReferencia = new Array();
 var iReposiciona = 0;
 var lReposiciona = false;
 var lCheck = false;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020193.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
   if(fGetPermisos("pg111020193p.js") == 2)
     lHabImpresas = false;
   else
     lHabImpresas = true;
 }

function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");

    ITRTD("EEtiquetaC",0,"95%","0","center");
      InicioTabla("",0,"95%","","center");
        ITRTD("",0,"","0","center","top");
          IFrame("IFiltro13","0","0","Filtros.js");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","1","center");
      InicioTabla("ETablaInfo",0,"95%","","","",1);
        ITRTD("ETablaST",4,"","","center");
          TextoSimple("Solicitudes Registradas para el Solicitante");
          BtnImgNombre("vgbuscar","lupa","if(fNavega());","Actualizar solicitudes del solicitante", false, "", "BuscarSolicitudes");
        FTDTR();
        ITR();
          TDEtiCheckBox("EEtiqueta",0,"Mostrar Impresas:","lImpresasBOX","0",true," Activo","","");
        FTR();

        ITR();
          TDEtiCampo(true,"EEtiqueta",0," Solicitante:","Persona_cNomRazonSocial","",50,50," Solicitante","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiCampo(true,"EEtiqueta",0," Rep. Legal:","RepLegal_cNomRazonSocial","",50,50," Representante Legal","fMayus(this);","","",false,"EEtiquetaL",0);
        FITR();
          TDEtiAreaTexto(false,"EEtiqueta",0,"Dom. Solicitante:",49,3,"Persona_cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",0);
          TDEtiAreaTexto(false,"EEtiqueta",0,"Dom. Rep. Legal:",49,3,"RepLegal_cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",0);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0," R.F.C.:","cRFC","",14,13," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiAreaTexto(false,"EEtiqueta \" rowspan=\"2",0,"Observación:",49,4,"cObsTramite","","Observaciones","fMayus(this);","","",false,false,false,"EEtiquetaL\" rowspan=\"3",0);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0," Autorizado a Recoger:","cNomAutorizaRecoger","",50,100," Persona Autorizada a Recoger Trámite","fMayus(this);","","",false,"EEtiquetaL",0);
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"95%","0","center","top");
      IFrame("IListado13","95%","170","Listado.js","yes",true);
    FTDTR();

    ITRTD("",0,"","40","center","bottom");
      IFrame("IPanel13","95%","34","Paneles.js");
    FTDTR();

    Hidden("iCveTipoPersona");
    Hidden("iCveSolicitante");
    Hidden("iCveDomicilioSolicitante");
    Hidden("iCveRepLegal");
    Hidden("iCveDomicilioRepLegal");

    Hidden("cFiltroImpreso");
    Hidden("lImpreso");

    Hidden("iEjercicio");
    Hidden("iNumSolicitud");
    Hidden("cDscTramite");
    Hidden("iCveTramite");
    Hidden("iCveModalidad");
    Hidden("cModalidad");

    Hidden("Mov_iRefNumerica");
    Hidden("Mov_iNumUnidades");
    Hidden("Mov_cRefAlfaNum");
    Hidden("Mov_dImportePagar");
    Hidden("Mov_iCveConcepto");
    Hidden("Mov_iCveSolicitanteIngresos");
//    Hidden("lMovGenerados");
    Hidden("hdLlave");
    Hidden("hdSelect");
  FinTabla();
  fFinPagina();
}

function fTodos(theCheck){
  fSeleccionaTodosEnListado(FRMListado, 0, theCheck);
}

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];

  FRMPanel = fBuscaFrame("IPanel13");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(true);
  FRMPanel.fShow(",");

  FRMListado = fBuscaFrame("IListado13");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(fCheckBoxTodos("cbTodos", "fTodos")+",Ejercicio,Núm. Sol.,Lim.Ent.Docs.,Estim. Entrega,Principal,Trámite,¿Requiere Digitalización?,¿Digitalizado?,");
  FRMListado.fSetObjs(0,"Caja");
  FRMListado.fSetCampos("0,1,8,9,11,15,16,18,");
  FRMListado.fSetAlinea("center,center,center,center,center,center,left,center,center,");
  FRMListado.fSetDespliega("text,text,text,text,text,logico,text,texto,texto,");

  FRMFiltro = fBuscaFrame("IFiltro13");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow(",");

  fDisabled(true);
  if(lHabImpresas == true){
     frm.lImpresasBOX.disabled = false;
  }else{
     frm.lImpresasBOX.disabled = true;
  }
  frm.hdBoton.value="Primero";
//  fNavega();
}

function fNavega(){
  var select = "";
  frm.hdNumReg.value = 100000;
  frm.hdLlave.value = "";
  select = "SELECT " +
		"distinct(RS.iEjercicio), " +//0
		"RS.iNumSolicitud, " +//1
		"RS.iCveTramite, " +//2
		"RS.iCveModalidad, " +//3
                "RS.iCveSolicitante, " +//4
		"RS.iCveRepLegal, " +//5
		"RS.cNomAutorizaRecoger, " +//6
		"RS.tsRegistro, " +//7
		"RS.dtLimiteEntregaDocs, " +//8
		"RS.dtEstimadaEntrega, " +//9
		"RS.lPagado, " +//10
		"RS.lPrincipal, " +//11
		"RS.lImpreso, " +//12
		"RS.cEnviarResolucionA, " +//13
		"RS.cObsTramite, " +//14
		"T.cCveInterna || ' - ' || T.cDscBreve AS cCveDscTramite, " +//15
		"("+
		"select "+
		"count(R.ICVEREQUISITO) as lReq "+
		"from TRAREGREQXTRAM RRT "+
		"join TRAREQUISITO R on RRT.ICVEREQUISITO = R.ICVEREQUISITO and R.LDIGITALIZA = 1 "+
		"where RRT.IEJERCICIO = RS.iejercicio "+
		"and RRT.INUMSOLICITUD = RS.inumsolicitud "+
		") as lDigit,  " +//16
                "MOD.CDSCMODALIDAD, "+//17
                "( " +
		"SELECT " +
		"COUNT(DOC.IEJERCICIOREQ) AS ITRA " +
		"FROM TRAREGREQXTRAM RRT " +
		"join TRADOCXREQUIS DOC on RRT.IEJERCICIO = DOC.IEJERCICIOREQ " +
		"AND RRT.INUMSOLICITUD = DOC.INUMSOLICITUD " +
		"AND RRT.ICVETRAMITE = DOC.ICVETRAMITE " +
		"AND RRT.ICVEMODALIDAD = DOC.ICVEMODALIDAD " +
		"AND RRT.ICVEREQUISITO = DOC.ICVEREQUISITO " +
		"where RRT.IEJERCICIO = RS.iejercicio " +
		"and RRT.INUMSOLICITUD = RS.inumsolicitud " +
		") as lAdj," +//18
                "( " +
                "SELECT " +
		"count(REQ.ICVEREQUISITO) as iReq " +
		"FROM TRAREGREQXTRAM RRT " +
		"JOIN TRAREQUISITO REQ ON RRT.ICVEREQUISITO = REQ.ICVEREQUISITO " +
		"WHERE RRT.IEJERCICIO = RS.iEjercicio " +
		" AND RRT.INUMSOLICITUD = RS.iNumSolicitud " +
		" and REQ.LDIGITALIZA = 1 " +
		") as lReqNec " +//19
		"FROM TRAREGSOLICITUD RS " +
		"JOIN TRACATTRAMITE T ON RS.iCveTramite = T.iCveTramite " +
                "JOIN TRAMODALIDAD MOD ON RS.ICVEMODALIDAD = MOD.ICVEMODALIDAD "+
		"where RS.iCveSolicitante = " + frm.iCveSolicitante.value +
      		" and RS.icvetramite in ("+window.parent.FRMSolic.cFiltroTramites+
                ") ";
  //LEL20122006
    //frm.hdFiltro.value =  " RS.iCveSolicitante = " + frm.iCveSolicitante.value + " AND RS.lImpreso = 0";
  if(frm.lImpresasBOX.checked)
     frm.lImpresasBOX.value = 1;
  else
     frm.lImpresasBOX.value = 0;
  select +=   " AND RS.lImpreso = " + frm.lImpresasBOX.value + " ";
  //FinLEL20122006

//		" AND RS.lImpreso = 0 "  +
  select +=     " and RS.iNumSolicitud not in " +
                " ( " +
                "SELECT TRAREGTRAMXSOL.INUMSOLICITUD FROM TRAREGTRAMXSOL WHERE " +
		"TRAREGTRAMXSOL.IEJERCICIO = RS.IEJERCICIO " +
		"AND TRAREGTRAMXSOL.INUMSOLICITUD = RS.INUMSOLICITUD " +
                ")";
  select += "ORDER BY RS.iEjercicio, RS.iNumSolicitud ";
  frm.hdSelect.value = select;
  if(frm.lImpresasBOX.checked && !lExistenImpresas){
     lExistenImpresas = true;
     fNavega();
     fEngSubmite("pgConsulta.jsp","Listado");
  }else if(frm.lImpresasBOX.checked == false){
     lExistenImpresas = false;
     fEngSubmite("pgConsulta.jsp","Listado");
  }

}

function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError){
  var aReportes = fCopiaArreglo(aRes);
  var aDatoRep  = fCopiaArreglo(aDato);
  if (cError == ""){
    frm.hdBoton.value = "UpdateImpreso";
    frm.cFiltroImpreso.value = cFiltro;
    frm.lImpreso.value = "1";
    //LEL20122006
    frm.hdFiltro.value =  " RS.iCveSolicitante = " + frm.iCveSolicitante.value + " AND RS.lImpreso = " + frm.lImpresasBOX.value + " ";
    //FINLEL20122006

        if(frm.lImpresasBOX.checked && !lExistenImpresas){
       lExistenImpresas = true;
       return fEngSubmite("pgTRARegSolicitud2.jsp","ImpresoActualizado");
   }else if(frm.lImpresasBOX.checked == false){
       lExistenImpresas = false;
       return fEngSubmite("pgTRARegSolicitud2.jsp","ImpresoActualizado");
   }

  /*
    frm.hdFiltro.value = " RS.iCveSolicitante = " + frm.iCveSolicitante.value + " AND RS.lImpreso = 0 ";
    fEngSubmite("pgTRARegSolicitud2.jsp", "ImpresoActualizado"); */
  }
  if (theWindow)
    theWindow.close();
  lEjecutado = true;
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert(cError);
    FRMFiltro.fSetNavStatus("Record");
  }else{
    if(cId == "ImpresoActualizado")
      fNavega();

    if(cId == "Listado"){
      aResSolicitudes = fCopiaArreglo(aRes);
      for(var i=0; i<aRes.length; i++){
        if(aResSolicitudes[i][16] > 0)
          aResSolicitudes[i][16] = "<font color=red>" + "SI" + "</font>";
        else
          aResSolicitudes[i][16] = "<font color=blue>" + "NO" + "</font>";

        if(aResSolicitudes[i][16] == "<font color=red>SI</font>"){
          if(aResSolicitudes[i][18] >= aResSolicitudes[i][19] && aResSolicitudes[i][18] != 0){
            aResSolicitudes[i][18] = "<font color=blue>" + "SI" + "</font>";
            aResSolicitudes[i][16] = "<font color=blue>" + "SI" + "</font>";
          }else
            aResSolicitudes[i][18] = "<font color=red>" + "NO" + "</font>";
        }else
          aResSolicitudes[i][18] = "--";
      }

      frm.hdRowPag.value = iRowPag;
      FRMListado.fSetListado(aResSolicitudes);
      FRMListado.fShow();
      FRMListado.fSetLlave(cLlave);
      FRMFiltro.fSetNavStatus(cNavStatus);

      if(lReposiciona)
        fReposicionaListado(FRMListado, "1", iReposiciona);

      lReposiciona = false;
    }
  }
}

function fSelReg(aDato,iCol,lChecked){
  frm.iEjercicio.value          = aDato[0];
  frm.iNumSolicitud.value       = aDato[1];
  frm.iCveTramite.value         = aDato[2];
  frm.iCveModalidad.value       = aDato[3];
  frm.cNomAutorizaRecoger.value = aDato[6];
  frm.cObsTramite.value         = aDato[14];
  frm.cDscTramite.value         = aDato[15];
  frm.cModalidad.value = aDato[17];
//  frm.lMovGenerados.value       = aDato[17];

  if(iCol==7){
    if(aDato[16] == "<font color=red>SI</font>" || aDato[16] == "<font color=blue>SI</font>")
     fAbreSubWindowPermisos("pg111020194","800","375");
    else
     fAlert("No es necesario adjuntar Documentos para ésta Solicitud.");
  }
}

function fImprimir(){
  self.focus();
  window.print();
}

function fReporte(){
  aCBoxTra = FRMListado.fGetObjs(0);
  var lEjecuta = false;
  var lDigitalizado = true;
  cClavesModulo = "2,";
  cNumerosRep = "1,";
  cFiltrosRep = "";

  cMsg = "";
  for (var i=0; i<aCBoxTra.length; i++){
    if (aCBoxTra[i] == true){
      if(aResSolicitudes[i][16] == "<font color=red>SI</font>"&& aResSolicitudes[i][18] == "<font color=red>NO</font>")
        lDigitalizado = false;
      else
        lEjecuta = true;
      if (cFiltrosRep != "")
        cFiltrosRep += "/";
      cFiltrosRep += aResSolicitudes[i][0] + "," + aResSolicitudes[i][1];
    }
  }

  if(!lDigitalizado){
    fAlert("No se puede ejecutar el Reporte ya que no todas las solicitudes seleccionadas cuentan con sus respectivos archivos digitalizados.");
    return;
  }

  if(lEjecuta){
    cFiltrosRep += cSeparadorRep;
    fReportes();
  }else
    fAlert("\n-Debe seleccionar un trámite para poder imprimir el acuse de recibo");
}

function fValoresDisponibles(theContFrm){
  frm.Persona_cNomRazonSocial.value  = theContFrm.cNomRazonSocial.value;
  frm.RepLegal_cNomRazonSocial.value = theContFrm.cNomRazonSocial2.value;
  frm.Persona_cDscDomicilio.value    = theContFrm.cDscDomicilio.value;
  frm.RepLegal_cDscDomicilio.value   = theContFrm.cDscDomicilio2.value;
  frm.cRFC.value                     = theContFrm.cRFC.value;

  frm.iCveTipoPersona.value          = theContFrm.iTipoPersona.value;
  frm.iCveSolicitante.value          = theContFrm.iCvePersona.value;
  frm.iCveDomicilioSolicitante.value = theContFrm.iCveDomicilio.value;
  frm.iCveRepLegal.value             = theContFrm.iCveRepLegal.value;
  frm.iCveDomicilioRepLegal.value    = theContFrm.iCveDomRepLegal.value;

  frm.iEjercicio.value          = "";
  frm.iNumSolicitud.value       = "";
  frm.cNomAutorizaRecoger.value = "";
  frm.cObsTramite.value         = "";

  fNavega();
}

function fGetDatosSolicitud(objWindow){
  if(objWindow)
    if(objWindow.fSetDatosSolicitud)
      objWindow.fSetDatosSolicitud(frm.Persona_cNomRazonSocial.value, frm.Persona_cDscDomicilio.value,
                                   frm.RepLegal_cNomRazonSocial.value, frm.RepLegal_cDscDomicilio.value,
                                   frm.iEjercicio.value, frm.iNumSolicitud.value, frm.cDscTramite.value,
                                   frm.cRFC.value);
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
             parseInt(frm.iCveTramite.value,10),
             parseInt(frm.iCveModalidad.value,10),
             true, true, false);
    else alert("No existe funcion");
  else alert("no existe ventana destino o fuente de datos");
}

function fGetiNumSolicitud(){
  return frm.iNumSolicitud.value;
}
function fGetiEjercicio(){
  return frm.iEjercicio.value;
}
function fGetcRFC(){
  return frm.cRFC.value;
}
function fGetcSolicitante(){
  return frm.Persona_cNomRazonSocial.value;
}
function fGetcDscTramite(){
  return frm.cDscTramite.value;
}
function fGetDscModalidad(){
  return frm.cModalidad.value;
}
function fGetiCveTramite(){
  return frm.iCveTramite.value;
}
function fGetiCveModalidad(){
  return frm.iCveModalidad.value;
}
function fDigitalizaEjecutado(lReposicionaD, iNumSol){
  iReposiciona = iNumSol;
  lReposiciona = lReposicionaD;
  fNavega();
}

// MetaCD=1.0
 // Title: pg111020100.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero<dd>Rafael Miranda Blumenkron
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var cNumSolicitud = "";
 var cEjercicio = "";
 var iAux = 0;
 var aResSolicitudes = new Array();
 var aDatosReferencia = new Array();
 var lGuardar = false;
 var lInicioDeTramite = false;
 var lActualizado = false;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020100.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
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

    ITRTD("EEtiquetaC",0,"95%","0","center");
      InicioTabla("",0,"95%","","center");
      ITD();TDEtiCampo(false,"EEtiqueta",0," Solicitante:","cNomSolicitanteFil","",60,60," Ejercicio ","fMayus(this);");
      FITD();Liga("Busca Solicitante","fAbreSolicitante();","Busca al Solicitante");
      FTD();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","1","center");
      InicioTabla("ETablaInfo",0,"95%","","","",1);
        ITRTD("ETablaST",4,"","","center");
          TextoSimple("Búsqueda por Solicitud");
        FTDTR();
        ITR();
          TDEtiCampo(false,"EEtiqueta",0," Ejercicio :","iEjercicioBusqueda","",4,4," Ejercicio ","fMayus(this);");
        FITR();
          TDEtiCampo(false,"EEtiqueta",0," Número de Solicitud :","iNumSolicitudBusqueda","",5,5," Solicitud ","fMayus(this);");
          ITD();
          InicioTabla("",0,"95%","","","",1);
          ITD("EEtiquetaL",0,"5%");
            BtnImg("Buscar","lupa","fNavega();");
          FTD();
          ITD("EEtiquetaL",0);
            BtnImg("Restaurar","restaura","fRestaura();");
          FTD();
          FinTabla();
          FTD;
        FITR();
          TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","");
        ITD();
          LigaNombre("Buscar Solicitud (Reimpresion)","fConsultaEntregado();","busca una solicitud que tenga la etapa de recepción pero no necesariamente la ultima.");
        FTD();
        FTR();
      FinTabla();
      InicioTabla("ETablaInfo",0,"95%","","","",1);
        ITRTD("ETablaST",4,"","","center");
          TextoSimple("Solicitudes Registradas para el Solicitante");
          BtnImgNombre("vgbuscar","lupa","fNavega();","Actualizar solicitudes del solicitante", false, "", "BuscarSolicitudes");
        FTDTR();
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
        FITR();
          ITD("EEtiquetaC",0);
            LigaNombre("Editar","fEditar();","Permite la edición del campo Autorizado a Recoger:");
          FTD();
          ITD("EEtiquetaC",0);
            LigaNombre("Guardar Cambios","fActualizaAutRec();","Guarda los Cambios realizados");
          FTD();
          ITD("EEtiquetaC",0);
            LigaNombre("Cancelar","fCancelar();","Cancela la acción.");
          FTD();
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

    Hidden("cRPA");
    Hidden("cNomSolicitante");
    Hidden("cApPaterno");
    Hidden("cApMaterno");
    Hidden("cCorreoE");
    Hidden("cPseudonimo");
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
    Hidden("lResolucionPositiva");

//    Hidden("iCveRepLegal");
//    Hidden("iCveDomicilioRepLegal");
    Hidden("hdLlave");
    Hidden("hdSelect");

    Hidden("cFiltroImpreso");
    Hidden("lImpreso");
    Hidden("lPagado");
    Hidden("iPorPagar");

    Hidden("iEjercicio");
    Hidden("iNumSolicitud");
    Hidden("cDscTramite");
    Hidden("iCveTramite");
    Hidden("iCveModalidad");

    Hidden("Mov_iRefNumerica");
    Hidden("Mov_iNumUnidades");
    Hidden("Mov_cRefAlfaNum");
    Hidden("Mov_dImportePagar");
    Hidden("Mov_iCveConcepto");
    Hidden("Mov_iCveSolicitanteIngresos");
    Hidden("Mov_dFechaRef");
    Hidden("lMovGenerados");

    Hidden("dUnidCalculo","");
    Hidden("hdIdBien","");

    Hidden("idUser");
    Hidden("cSolicitudesEnviar");
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
  FRMListado.fSetTitulo(fCheckBoxTodos("cbTodos", "fTodos")+",Ejercicio,Núm. Sol.,Lim.Ent.Docs.,Estim. Entrega,Principal,Conceptos,Pagar,Trámite,");
  FRMListado.fSetObjs(0,"Caja");
//  FRMListado.fSetObjs(6,"Boton");
  FRMListado.fSetObjs(6,"Boton");
  FRMListado.fSetObjs(7,"Boton");
  FRMListado.fSetCampos("0,1,8,9,11,15,");
  FRMListado.fSetAlinea("center,center,center,center,center,center,center,center,left,");
  FRMListado.fSetDespliega("text,text,text,text,text,logico,text,text,text,");

  FRMFiltro = fBuscaFrame("IFiltro13");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow(",");

  fDisabled(true,"iEjercicioBusqueda,iNumSolicitudBusqueda,iCveOficina,");
  frm.hdBoton.value="Primero";
  fTraeFechaActual();
//  fAbreSolicitante();
}

function fNavega(){
  frm.hdFiltro.value = "";
  if (frm.iCveSolicitante.value != ""){
    if(!fSoloNumeros(frm.iEjercicioBusqueda.value) || frm.iEjercicioBusqueda.value==""){
      fAlert("El ejercicio es necesario para la consulta.");
      return;
    }
    frm.hdFiltro.value = " RS.iCveSolicitante = " + frm.iCveSolicitante.value + " AND RS.iEjercicio ="+frm.iEjercicioBusqueda.value+" and RS.DTENTREGA is null ";
    frm.hdOrden.value =  FRMFiltro.fGetOrden();
    frm.hdNumReg.value = 100000;
    if(frm.iCveOficina.value>0) frm.hdFiltro.value += " AND RS.iCveOficina= "+frm.iCveOficina.value;
    fEngSubmite("pgTRARegSolicitud3B.jsp","Listado");
  }else if(frm.iEjercicioBusqueda.value != "" || frm.iNumSolicitudBusqueda.value != ""){
    if(frm.iEjercicioBusqueda.value != "")
      if(!fSoloNumeros(frm.iEjercicioBusqueda.value)){
         fAlert('n - El campo "Ejercicio" solo permite números.');
         frm.iEjercicioBusqueda.value = "";
         frm.iEjercicioBusqueda.focus();
         return;
      }
    if(frm.iNumSolicitudBusqueda.value != "")
      if(!fSoloNumeros(frm.iNumSolicitudBusqueda.value)){
        fAlert('n - El campo "Solicitud" solo permite números.');
        frm.iNumSolicitudBusqueda.value = "";
        frm.iNumSolicitudBusqueda.focus();
        return;
      }

    if(frm.iEjercicioBusqueda.value != "")
      if(frm.hdFiltro.value == "")
        frm.hdFiltro.value = "RS.iEjercicio = " + frm.iEjercicioBusqueda.value;
      else
        frm.hdFiltro.value += " and RS.iEjercicio = " + frm.iEjercicioBusqueda.value;

    if(frm.iNumSolicitudBusqueda.value != "")
      if(frm.hdFiltro.value == "")
        frm.hdFiltro.value = "RS.iNumSolicitud = " + frm.iNumSolicitudBusqueda.value;
      else
        frm.hdFiltro.value += " and RS.iNumSolicitud = " + frm.iNumSolicitudBusqueda.value;


    frm.hdFiltro.value += " and RS.DTENTREGA is null";
    if(frm.iCveOficina.value>0) frm.hdFiltro.value += " AND RS.iCveOficina= "+frm.iCveOficina.value;
    frm.hdOrden.value =  FRMFiltro.fGetOrden();
    frm.hdNumReg.value = 100000;
    fEngSubmite("pgTRARegSolicitud3B.jsp","Listado");
  }
}

function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError){
  var aReportes = fCopiaArreglo(aRes);
  var aDatoRep  = fCopiaArreglo(aDato);
  var aSolicitudes = cFiltro.split(",");
  cFiltro = "";
  var aList = FRMListado.fGetARes();
  for(j=0;j<aList.length;j++){
    for (i=1;i<aSolicitudes.length;i++){
      if((parseInt(aSolicitudes[0],10) == parseInt(aList[j][0],10))&&(parseInt(aSolicitudes[i],10) == parseInt(aList[j][1],10))){
        cFiltro += aList[j][0] + "," + aList[j][1]+"," + aList[j][2]+"," + aList[j][3]+"/";
      }
    }
  }
  if (cError == "" && frm.hdFiltro.value != ""){
    frm.hdBoton.value = "UpdateImpresoB";

    frm.cFiltroImpreso.value = cFiltro;
    frm.lImpreso.value = "1";
    frm.idUser.value = fGetIdUsrSesion();
    //if(frm.hdFiltro.value != "")// frm.hdFiltro.value = "rs.iejercicio="+frm.iEjercicioBusqueda.value+" and rs.INUMSOLICITUD="+frm.iNumSolicitudBusqueda.value;
    fEngSubmite("pgTRARegSolicitud3B.jsp", "ImpresoActualizado");
  }
  if (theWindow)
    theWindow.close();
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert(cError);
    FRMFiltro.fSetNavStatus("Record");
  }else{
    if(cId == "ImpresoActualizado"){
      lActualizado=true;
      fNavega();
    }
    if(cId == "MovimientoGenerado"){
      fAlert('Los movimientos se guardaron exitosamente');
      fNavega();
    }
    if(cId == "Listado"){
      aResSolicitudes = fCopiaArreglo(aRes);
      
      if(!lActualizado){
	      if(aRes.length==0){
	    	  fAlert("No se encontraron datos con el filtro proporcionado.");
	      }
      }
      
      frm.hdRowPag.value = iRowPag;
      FRMListado.fSetListado(aResSolicitudes);
      FRMListado.fShow();
      FRMListado.fSetLlave(cLlave);
      FRMFiltro.fSetNavStatus(cNavStatus);
      lActualizado=false;
    }
    if(cId == "idFechaActual" && cError == ""){
     frm.iEjercicioBusqueda.value = aRes[1][2];
     frm.hdSelect.value =
                    "SELECT distinct(U.iCveOficina),o.CDSCBREVE FROM GRLUSUARIOXOFIC U " +
                    "JOIN GRLOficina O on O.iCveOficina = U.iCveOficina " +
                    "where iCveUsuario = " + top.fGetUsrID();
     frm.hdLlave.value  = "";
     fEngSubmite("pgConsulta.jsp","cIdOficina");
    }
    if(cId=="cIdOficina"){
      if(aRes.length>0){
        fFillSelect(frm.iCveOficina,aRes,false,0,0,1);
        //fAbreSolicitante();
      }
      else fAlert("El usuario no cuenta con oficina");
    }
    if(cId == "MovimientoGenerado"){
      frm.hdBoton.value = "";
      fAlert('Los movimientos se guardaron exitosamente');
      fNavega();
    }
    if(cId=="cIdEntregaTramite"){
      fAlert("Reporte ejecutado.");
    }
  }
}

function fSelReg(aDato,iCol,lChecked){
  frm.iEjercicio.value          = aDato[0];
  frm.iNumSolicitud.value       = aDato[1];
  frm.iEjercicioBusqueda.value  = aDato[0];
  frm.iNumSolicitudBusqueda.value = aDato[1];
  frm.iCveTramite.value         = aDato[2];
  frm.iCveModalidad.value       = aDato[3];
  frm.cNomAutorizaRecoger.value = aDato[6];
  frm.cObsTramite.value         = aDato[14];
  frm.cDscTramite.value         = aDato[15];
  frm.iPorPagar.value           = aDato[56];
  frm.lPagado.value             = aDato[57];

  if (aDato[5] > 0){
    frm.RepLegal_cDscDomicilio.value = aDato[34];
    frm.RepLegal_cNomRazonSocial.value = aDato[37];
    if (aDato[36] == 1)
      frm.RepLegal_cNomRazonSocial.value += " " + aDato[38] + " " + aDato[39];
  }else{
    frm.RepLegal_cDscDomicilio.value = "";
    frm.RepLegal_cNomRazonSocial.value = "";
  }
  frm.Persona_cNomRazonSocial.value = aDato[40];

  frm.Persona_cDscDomicilio.value = aDato[41];
  frm.cRFC.value = aDato[42];
  frm.cCalle.value = aDato[43];
  frm.cNumExterior.value = aDato[44];
  frm.cNumInterior.value = aDato[45];
  frm.cColonia.value = aDato[46];
  frm.cDscMunicipio.value = aDato[47];
  frm.cDscEntidadFed.value = aDato[48];
  frm.cCodPostal.value = aDato[49];
  frm.cNomSolicitante.value = aDato[50];
  frm.cApPaterno.value = aDato[51];
  frm.cApMaterno.value = aDato[52];
  frm.iCvePais.value = aDato[53];
  frm.iCveEntidadFed.value = aDato[54];
  frm.iCveMunicipio.value = aDato[55];
  frm.lResolucionPositiva.value = aDato[57];
  frm.dUnidCalculo.value        = aDato[58];
  frm.hdIdBien.value            = aDato[59];

  if(iCol==6)
    if(parseInt(frm.lResolucionPositiva.value,10) == 1)
      fAbreGeneraMovimientos();
    else
      fAlert("No puede generar movimientos para la solicitud debido a que tiene resolución NO positiva");
  if(iCol==7)
    fAbreRegistraPago();

}

function fImprimir(){
  self.focus();
  window.print();
}

/*****/
function fReporte(){
  aCBoxTra = FRMListado.fGetObjs(0);
  var lEjecuta = false;
  cClavesModulo = "2,";
  cNumerosRep = "13,";
  frm.cSolicitudesEnviar.value = "";
  cMsg = "";
  //Busca que alguna de las solicitudes haya sido seleccionada
  for (var i=0; i<aCBoxTra.length; i++){
    if (aCBoxTra[i] == true)
      lEjecuta = true;
  }
  frm.cSolicitudesEnviar.value = frm.iEjercicio.value;
  for (var i=0; i<aCBoxTra.length; i++){
    if (aCBoxTra[i] == true){
      if(parseInt(aResSolicitudes[i][56],10) > 0)
        cMsg += "\nEjercicio: " + aResSolicitudes[i][0] + " Número: " + aResSolicitudes[i][1];
      if (frm.cSolicitudesEnviar.value != "")
        frm.cSolicitudesEnviar.value += ",";
      frm.cSolicitudesEnviar.value += aResSolicitudes[i][1];
    }
  }
  if(cMsg != ""){
    lEjecuta = false;
    fAlert("\n\nLas siguientes solicitudes no pueden entregarse por faltar registro de pago" + cMsg);
    return;
  }
  else if (lEjecuta && frm.cSolicitudesEnviar.value!=""){
    cFiltrosRep = frm.cSolicitudesEnviar.value +cSeparadorRep;
    fReportes();
  }
  else fAlert("No hay un registro valido a imprimir.");
}
/******/
/** /
function fReporte(){
  aCBoxTra = FRMListado.fGetObjs(0);
  var lEjecuta = false;
  cClavesModulo = "2,";
  cNumerosRep = "2,";
  cFiltrosRep = "";

  cMsg = "";
  var lSeleccionado = false;
  for (var i=0; i<aCBoxTra.length; i++){
    if (aCBoxTra[i] == true)
      lEjecuta = true;
  }
  for (var i=0; i<aCBoxTra.length; i++){
    if (aCBoxTra[i] == true){
      if(parseInt(aResSolicitudes[i][56],10) > 0)
        cMsg += "\nEjercicio: " + aResSolicitudes[i][0] + " Número: " + aResSolicitudes[i][1];
      if (cFiltrosRep != "")
        cFiltrosRep += "/";
      cFiltrosRep += aResSolicitudes[i][0] + "," + aResSolicitudes[i][1] + "," + aResSolicitudes[i][2] + "," + aResSolicitudes[i][3];
    }
  }
  if(cMsg != ""){
    lEjecuta = false;
    fAlert("\n\nLas siguientes solicitudes no pueden entregarse por faltar registro de pago" + cMsg);
    return;
  }
  if (lEjecuta)
    cFiltrosRep += cSeparadorRep;
  else{
    if (frm.iEjercicio.value != "" && frm.iNumSolicitud.value != ""){
      if(parseInt(frm.lResolucionPositiva.value,10) == 1)
        if(frm.lPagado.value == 0 || parseInt(frm.iPorPagar.value,10) >0){
          cMsg = "\nEjercicio: " + frm.iEjercicio.value + " Número: " + frm.iNumSolicitud.value;
          fAlert("\n\nLas siguientes solicitudes no pueden entregarse por faltar registro de pago" + cMsg);
          return;
        }
      cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + frm.iCveTramite.value + "," + frm.iCveModalidad.value + cSeparadorRep;
      lEjecuta = true;
    }
  }

  if (lEjecuta){
    fReportes();
  }
  else
    fAlert("\n-Debe seleccionar un trámite para poder imprimir el acuse de recibo");
}
/******/
function fGetPermiteEditarPagos(){
  return true;
}

function fGetDatosSolicitud(objWindow){
  if(objWindow)
    if(objWindow.fSetDatosSolicitud)
      objWindow.fSetDatosSolicitud(frm.Persona_cNomRazonSocial.value, frm.Persona_cDscDomicilio.value,
                                   frm.RepLegal_cNomRazonSocial.value, frm.RepLegal_cDscDomicilio.value,
                                   frm.iEjercicio.value, frm.iNumSolicitud.value, frm.cDscTramite.value,
                                   frm.cRFC.value);
}

function fPagosRegistro(objWindow, cEjercicios, cNumSolics, cConsecs, cRefNums, cRefAlfaNums){
  if(objWindow)
    objWindow.close();
  if(cEjercicios && cEjercicios.length>0)
    fNavega();
}

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio){
  frm.iCveTipoPersona.value = iTipoPersona;
  frm.iCveSolicitante.value = iCvePersona;
  frm.iCveDomicilioSolicitante.value = iCveDomicilio;
  frm.Persona_cNomRazonSocial.value = cNomRazonSocial;
  //if(cNomRazonSocial != "")
     //frm.iEjercicioBusqueda.value = "";
  if(parseInt(""+iTipoPersona,10) == 1)
    frm.Persona_cNomRazonSocial.value += " " + cApPaterno + " " + cApMaterno;
  frm.cNomSolicitanteFil.value = frm.Persona_cNomRazonSocial.value;
  frm.Persona_cDscDomicilio.value   = cDscDomicilio;
  frm.cRFC.value = cRFC;
  frm.cRPA.value = cRPA;
  frm.cNomSolicitante.value = cNomRazonSocial;
  frm.cApPaterno.value = cApPaterno;
  frm.cApMaterno.value = cApMaterno;
  frm.cCorreoE.value = cCorreoE;
  frm.cPseudonimo.value = cPseudonimoEmp;
  frm.iCveTipoDomicilio.value = iCveTipoDomicilio;
  frm.cCalle.value = cCalle;
  frm.cNumExterior.value = cNumExterior;
  frm.cNumInterior.value = cNumInterior;
  frm.cColonia.value = cColonia;
  frm.cCodPostal.value = cCodPostal;
  frm.cTelefono.value = cTelefono;
  frm.iCvePais.value = iCvePais;
  frm.cDscPais.value = cDscPais;
  frm.iCveEntidadFed.value = iCveEntidadFed;
  frm.cDscEntidadFed.value = cDscEntidadFed;
  frm.iCveMunicipio.value = iCveMunicipio;
  frm.cDscMunicipio.value = cDscMunicipio;
  frm.iCveLocalidad.value = iCveLocalidad;
  frm.cDscLocalidad.value = cDscLocalidad;
  frm.lPredeterminado.value = lPredeterminado;
  frm.cDscTipoDomicilio.value = cDscTipoDomicilio;
  frm.cDscDomicilio.value = cDscDomicilio;
  fNavega();
}

function fGetClaves(frmDestino){
  if (frmDestino.setClaves)
    frmDestino.setClaves(frm.iCveTipoPersona.value, frm.iCveSolicitante.value, frm.iCveDomicilioSolicitante.value, "", "");
}

function fGetParametrosConsulta(frmDestino){
  var lShowPersona     = true;
  var lShowRepLegal    = false;
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
function fEditar(){
  if(FRMListado.fGetLength() > 0){
    FRMListado.fSetDisabled(true);
    frm.cObsTramite.disabled = false; 
    frm.cNomAutorizaRecoger.disabled = true;
    frm.iEjercicioBusqueda.disabled = true;
    frm.iNumSolicitudBusqueda.disabled = true;
    frm.cObsTramite.focus();
    lGuardar = true;
  }else
    fAlert('n - Es necesario que exista al menos un registro para ejecutar esta acción.');
}
function fCancelar(){
  if(lGuardar){
    lGuardar = false;
    frm.cNomAutorizaRecoger.disabled = true;
    frm.iEjercicioBusqueda.disabled = false;
    frm.iNumSolicitudBusqueda.disabled = false;
    FRMListado.fShow();
    FRMListado.fSetDisabled(false);
  }
}
function fActualizaAutRec(){
  if(lGuardar){
    frm.hdBoton.value = "GuardarCambios";
    lGuardar = false;
    frm.cObsTramite.disabled = true; 
    frm.cNomAutorizaRecoger.disabled = true;
    frm.iEjercicioBusqueda.disabled = false;
    frm.iNumSolicitudBusqueda.disabled = false;

    if(frm.iCveSolicitante.value != ""){
      frm.hdFiltro.value = " RS.iCveSolicitante = " + frm.iCveSolicitante.value + " and RS.DTENTREGA is null ";
      frm.hdOrden.value =  FRMFiltro.fGetOrden();
      frm.hdNumReg.value = 100000;
      fEngSubmite("pgTRARegSolicitud3B.jsp","Listado");
    }else if(frm.iEjercicioBusqueda.value != "" || frm.iNumSolicitudBusqueda.value != ""){
       if(frm.iEjercicioBusqueda.value != "")
         if(frm.hdFiltro.value == "")
           frm.hdFiltro.value = "RS.iEjercicio = " + frm.iEjercicioBusqueda.value;
         else
           frm.hdFiltro.value += " and RS.iEjercicio = " + frm.iEjercicioBusqueda.value;

       if(frm.iNumSolicitudBusqueda.value != "")
         if(frm.hdFiltro.value == "")
           frm.hdFiltro.value = "RS.iNumSolicitud = " + frm.iNumSolicitudBusqueda.value;
         else
           frm.hdFiltro.value += " and RS.iNumSolicitud = " + frm.iNumSolicitudBusqueda.value;


       frm.hdFiltro.value += " and RS.DTENTREGA is null";
       frm.hdOrden.value =  FRMFiltro.fGetOrden();
       frm.hdNumReg.value = 100000;
       fEngSubmite("pgTRARegSolicitud3B.jsp","Listado");
    }
    FRMListado.fSetDisabled(false);
  }
}
function fRestaura(){
  fBlanked();
  frm.iCveSolicitante.value = "";
}
function fMostrarGenerarFicha(){
  return true;
}

function fGetDatosPersona(objRef){
  if(objRef)
    if(objRef.fValoresPersona)
      objRef.fValoresPersona(
             frm.iCveSolicitante.value,
             frm.cRFC.value,
             frm.cRPA.value,
             frm.iCveTipoPersona.value,
             frm.Persona_cNomRazonSocial.value,
             frm.cApPaterno.value,
             frm.cApMaterno.value,
             frm.cCorreoE.value,
             frm.cPseudonimo.value,
             frm.iCveDomicilioSolicitante.value,
             frm.iCveTipoDomicilio.value,
             frm.cCalle.value,
             frm.cNumExterior.value,
             frm.cNumInterior.value,
             frm.cColonia.value,
             frm.cCodPostal.value,
             frm.cTelefono.value,
             frm.iCvePais.value,
             frm.cDscPais.value,
             frm.iCveEntidadFed.value,
             frm.cDscEntidadFed.value,
             frm.iCveMunicipio.value,
             frm.cDscMunicipio.value,
             frm.iCveLocalidad.value,
             frm.cDscLocalidad.value,
             frm.lPredeterminado.value,
             frm.cDscTipoDomicilio.value,
             frm.cDscDomicilio.value,
             parseInt(frm.iCveTramite.value,10),
             parseInt(frm.iCveModalidad.value,10),
             false, false, false);
    else fAlert("No existe funcion");
  else fAlert("no existe ventana destino o fuente de datos");
}

function fDatosReferencia(aResDatosRef,objRef){
  frm.hdBoton.value = "CreaRegPago";
  fEngSubmite("pgTRARegRefPago.jsp", "MovimientoGenerado");
  if(objRef)
    objRef.close();
}

function fGetIEjercicio(){
  return frm.iEjercicio.value;
}

function fGetINumSolicitud(){
  return frm.iNumSolicitud.value;
}

function fGetUnidCalculo(){
  return frm.dUnidCalculo.value;
}

function fGetIdBien(){
  return frm.hdIdBien.value;
}

function fGetInicioTramite(){
  return lInicioDeTramite;
}

function fConsultaEntregado(){
  if(frm.iEjercicioBusqueda.value >0 && frm.iNumSolicitudBusqueda.value>0){
  frm.hdSelect.value = "SELECT " +
                       "       RS.IEJERCICIO, " +
                       "       RS.INUMSOLICITUD, " +
                       "       RS.ICVETRAMITE, " +
                       "       RS.ICVEMODALIDAD , " +
                       "       RS.ICVESOLICITANTE, " +
                       "       RS.ICVEREPLEGAL, " +
                       "       RS.CNOMAUTORIZARECOGER, " +
                       "       RS.TSREGISTRO , " +
                       "       RS.DTLIMITEENTREGADOCS, " +
                       "       RS.DTESTIMADAENTREGA, " +
                       "       RS.LPAGADO, " +
                       "       RS.LPRINCIPAL , " +
                       "       RS.LIMPRESO, " +
                       "       RS.CENVIARRESOLUCIONA, " +
                       "       RS.COBSTRAMITE , " +
                       "       T.CCVEINTERNA || ' - ' || T.CDSCBREVE AS CCVEDSCTRAMITE , " +
                       "       D.ICVEDOMICILIO, " +
                       "       D.ICVETIPODOMICILIO, " +
                       "       D.CCALLE, " +
                       "       D.CNUMEXTERIOR , " +
                       "       D.CNUMINTERIOR, " +
                       "       D.CCOLONIA, " +
                       "       D.CCODPOSTAL, " +
                       "       D.CTELEFONO, " +
                       "       D.ICVEPAIS , " +
                       "       GRLLOCALIDAD.CNOMBRE, " +
                       "       GRLPAIS.CNOMBRE AS PAIS, " +
                       "       D.ICVEENTIDADFED, " +
                       "       GRLENTIDADFED.CNOMBRE AS ESTADO, " +
                       "       GRLMUNICIPIO.CNOMBRE AS MUNICIPIO , " +
                       "       D.ICVEMUNICIPIO, " +
                       "       GRLLOCALIDAD.ICVELOCALIDAD, " +
                       "       GRLTIPODOMICILIO.CDSCTIPODOMICILIO, " +
                       "       D.LPREDETERMINADO , " +
                       "       D.CCALLE || ' NO. ' || D.CNUMEXTERIOR || ' INT. ' || D.CNUMINTERIOR || ' COL. ' || D.CCOLONIA || ', " +
                       "       ' || GRLMUNICIPIO.CNOMBRE || ' (' || GRLENTIDADFED.CABREVIATURA ||'.)' || ' C.P. ' || D.CCODPOSTAL AS CDOMICILIO , " +
                       "       P.CRFC AS CRFCREPLEGAL, " +
                       "       P.ITIPOPERSONA AS ITIPOPERSONAREPLEGAL, " +
                       "       P.CNOMRAZONSOCIAL AS CNOMRAZONSOCIALREPLEGAL, " +
                       "       P.CAPPATERNO AS CAPPATERNOREPLEGAL, " +
                       "       P.CAPMATERNO AS CAPMATERNOREPLEGAL, " +
                       "       PS.CNOMRAZONSOCIAL || ' ' || PS.CAPPATERNO || ' ' || PS.CAPMATERNO AS CNOMBREPRSONA, " +
                       "       DP.CCALLE || ' NO. ' || DP.CNUMEXTERIOR || ' INT. ' || DP.CNUMINTERIOR || ' COL. ' || DP.CCOLONIA || ', " +
                       "       ' || MUNP.CNOMBRE || ' (' || EFP.CABREVIATURA ||'.)' || ' C.P. ' || DP.CCODPOSTAL AS CDOMICILIOPERSONA , " +
                       "       PS.CRFC, " +
                       "       DP.CCALLE AS CCALLEPER, " +
                       "       DP.CNUMEXTERIOR AS CNUMENTERIORPER , " +
                       "       DP.CNUMINTERIOR AS CNUMINTERIORPER , " +
                       "       DP.CCOLONIA AS CCOLONIAPER , " +
                       "       MUNP.CNOMBRE AS CNOMBREMUNPER , " +
                       "       EFP.CABREVIATURA AS CABREVMUNPER , " +
                       "       DP.CCODPOSTAL AS CCODPOSTALPER, " +
                       "       PS.CNOMRAZONSOCIAL AS CNOMPERSOLIC, " +
                       "       PS.CAPPATERNO AS CAPPATERNOSOLIC, " +
                       "       PS.CAPMATERNO AS CAPMATERNOSOLIC, " +
                       "       DP.ICVEPAIS AS ICVEPAISSOLIC, " +
                       "       DP.ICVEENTIDADFED AS ICVEENTIDADSOLIC, " +
                       "       DP.ICVEMUNICIPIO AS ICVEMUNICSOLIC, " +
                       "         (SELECT " +
                       "          COUNT(1) " +
                       "          FROM TRAREGREFPAGO RP " +
                       "          WHERE RP.IEJERCICIO = RS.IEJERCICIO " +
                       "          AND RP.INUMSOLICITUD = RS.INUMSOLICITUD " +
                       "          AND RP.LPAGADO = 0) AS INUMPENDPAGO, " +
                       "       RS.LRESOLUCIONPOSITIVA, " +
                       "       RS.DUNIDADESCALCULO, " +
                       "       RS.IIDBIEN " +
                       "FROM TRAREGSOLICITUD RS " +
                       "  LEFT JOIN TRAREGTRAMXSOL ON TRAREGTRAMXSOL.IEJERCICIO = RS.IEJERCICIO " +
                       "    AND TRAREGTRAMXSOL.INUMSOLICITUD = RS.INUMSOLICITUD " +
                       "  JOIN TRACATTRAMITE T ON RS.ICVETRAMITE = T.ICVETRAMITE " +
                       "  JOIN TRAREGETAPASXMODTRAM ET ON ET.IEJERCICIO = RS.IEJERCICIO " +
                       "    AND ET.INUMSOLICITUD = RS.INUMSOLICITUD " +
                       "    AND ET.ICVETRAMITE = RS.ICVETRAMITE " +
                       "    AND ET.ICVEMODALIDAD = RS.ICVEMODALIDAD " +
                       "    AND ET.ICVEETAPA in (17,21) " +
                       "  JOIN GRLDOMICILIO D ON D.ICVEPERSONA = RS.ICVEREPLEGAL " +
                       "    AND D.ICVEDOMICILIO = RS.ICVEDOMICILIOREPLEGAL " +
                       "  JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = D.ICVEPAIS " +
                       "  JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = D.ICVEPAIS " +
                       "    AND GRLENTIDADFED.ICVEENTIDADFED = D.ICVEENTIDADFED " +
                       "  JOIN GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS = D.ICVEPAIS " +
                       "    AND GRLMUNICIPIO.ICVEENTIDADFED = D.ICVEENTIDADFED " +
                       "    AND GRLMUNICIPIO.ICVEMUNICIPIO = D.ICVEMUNICIPIO " +
                       "  JOIN GRLLOCALIDAD ON D.ICVEPAIS = GRLLOCALIDAD.ICVEPAIS " +
                       "    AND GRLLOCALIDAD.ICVEENTIDADFED = D.ICVEENTIDADFED " +
                       "    AND GRLLOCALIDAD.ICVEMUNICIPIO = D.ICVEMUNICIPIO " +
                       "    AND GRLLOCALIDAD.ICVELOCALIDAD = D.ICVELOCALIDAD " +
                       "  JOIN GRLTIPODOMICILIO ON GRLTIPODOMICILIO.ICVETIPODOMICILIO = D.ICVETIPODOMICILIO " +
                       "  JOIN GRLPERSONA P ON P.ICVEPERSONA = RS.ICVEREPLEGAL " +
                       "  JOIN GRLDOMICILIO DP ON DP.ICVEPERSONA = RS.ICVESOLICITANTE " +
                       "    AND DP.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE " +
                       "  JOIN GRLPAIS PAISP ON PAISP.ICVEPAIS = DP.ICVEPAIS " +
                       "  JOIN GRLENTIDADFED EFP ON EFP.ICVEPAIS = DP.ICVEPAIS " +
                       "    AND EFP.ICVEENTIDADFED = DP.ICVEENTIDADFED " +
                       "  JOIN GRLMUNICIPIO MUNP ON MUNP.ICVEPAIS = DP.ICVEPAIS " +
                       "    AND MUNP.ICVEENTIDADFED = DP.ICVEENTIDADFED " +
                       "    AND MUNP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO " +
                       "  JOIN GRLLOCALIDAD LOCP ON DP.ICVEPAIS = LOCP.ICVEPAIS " +
                       "    AND LOCP.ICVEENTIDADFED = DP.ICVEENTIDADFED " +
                       "    AND LOCP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO " +
                       "    AND LOCP.ICVELOCALIDAD = DP.ICVELOCALIDAD " +
                       "  JOIN GRLPERSONA PS ON PS.ICVEPERSONA = RS.ICVESOLICITANTE " +
                       "  WHERE RS.IEJERCICIO = " + frm.iEjercicioBusqueda.value +
                       "    AND RS.INUMSOLICITUD = " + frm.iNumSolicitudBusqueda.value +
                       "    AND TRAREGTRAMXSOL.DTCANCELACION IS NULL " +
                       "    and RS.DTENTREGA is not null "
                       "  ORDER BY IEJERCICIO, " +
                       "       INUMSOLICITUD "
                       /*"UNION " +
                       "SELECT " +
                       "       RS.IEJERCICIO, " +
                       "       RS.INUMSOLICITUD, " +
                       "       RS.ICVETRAMITE, " +
                       "       RS.ICVEMODALIDAD , " +
                       "       RS.ICVESOLICITANTE, " +
                       "       RS.ICVEREPLEGAL, " +
                       "       RS.CNOMAUTORIZARECOGER, " +
                       "       RS.TSREGISTRO , " +
                       "       RS.DTLIMITEENTREGADOCS, " +
                       "       RS.DTESTIMADAENTREGA, " +
                       "       RS.LPAGADO, " +
                       "       RS.LPRINCIPAL , " +
                       "       RS.LIMPRESO, " +
                       "       RS.CENVIARRESOLUCIONA, " +
                       "       RS.COBSTRAMITE , " +
                       "       T.CCVEINTERNA || ' - ' || T.CDSCBREVE AS CCVEDSCTRAMITE , " +
                       "       D.ICVEDOMICILIO, " +
                       "       D.ICVETIPODOMICILIO, " +
                       "       D.CCALLE, " +
                       "       D.CNUMEXTERIOR , " +
                       "       D.CNUMINTERIOR, " +
                       "       D.CCOLONIA, " +
                       "       D.CCODPOSTAL, " +
                       "       D.CTELEFONO, " +
                       "       D.ICVEPAIS , " +
                       "       GRLLOCALIDAD.CNOMBRE, " +
                       "       GRLPAIS.CNOMBRE AS PAIS, " +
                       "       D.ICVEENTIDADFED, " +
                       "       GRLENTIDADFED.CNOMBRE AS ESTADO, " +
                       "       GRLMUNICIPIO.CNOMBRE AS MUNICIPIO , " +
                       "       D.ICVEMUNICIPIO, " +
                       "       GRLLOCALIDAD.ICVELOCALIDAD, " +
                       "       GRLTIPODOMICILIO.CDSCTIPODOMICILIO, " +
                       "       D.LPREDETERMINADO , " +
                       "       D.CCALLE || ' NO. ' || D.CNUMEXTERIOR || ' INT. ' || D.CNUMINTERIOR || ' COL. ' || D.CCOLONIA || ', " +
                       "       ' || GRLMUNICIPIO.CNOMBRE || ' (' || GRLENTIDADFED.CABREVIATURA ||'.)' || ' C.P. ' || D.CCODPOSTAL AS CDOMICILIO , " +
                       "       P.CRFC AS CRFCREPLEGAL, " +
                       "       P.ITIPOPERSONA AS ITIPOPERSONAREPLEGAL, " +
                       "       P.CNOMRAZONSOCIAL AS CNOMRAZONSOCIALREPLEGAL, " +
                       "       P.CAPPATERNO AS CAPPATERNOREPLEGAL, " +
                       "       P.CAPMATERNO AS CAPMATERNOREPLEGAL, " +
                       "       PS.CNOMRAZONSOCIAL || ' ' || PS.CAPPATERNO || ' ' || PS.CAPMATERNO AS CNOMBREPRSONA, " +
                       "       DP.CCALLE || ' NO. ' || DP.CNUMEXTERIOR || ' INT. ' || DP.CNUMINTERIOR || ' COL. ' || DP.CCOLONIA || ', " +
                       "       ' || MUNP.CNOMBRE || ' (' || EFP.CABREVIATURA ||'.)' || ' C.P. ' || DP.CCODPOSTAL AS CDOMICILIOPERSONA , " +
                       "       PS.CRFC, " +
                       "       DP.CCALLE AS CCALLEPER, " +
                       "       DP.CNUMEXTERIOR AS CNUMENTERIORPER , " +
                       "       DP.CNUMINTERIOR AS CNUMINTERIORPER , " +
                       "       DP.CCOLONIA AS CCOLONIAPER , " +
                       "       MUNP.CNOMBRE AS CNOMBREMUNPER , " +
                       "       EFP.CABREVIATURA AS CABREVMUNPER , " +
                       "       DP.CCODPOSTAL AS CCODPOSTALPER, " +
                       "       PS.CNOMRAZONSOCIAL AS CNOMPERSOLIC, " +
                       "       PS.CAPPATERNO AS CAPPATERNOSOLIC, " +
                       "       PS.CAPMATERNO AS CAPMATERNOSOLIC, " +
                       "       DP.ICVEPAIS AS ICVEPAISSOLIC, " +
                       "       DP.ICVEENTIDADFED AS ICVEENTIDADSOLIC, " +
                       "       DP.ICVEMUNICIPIO AS ICVEMUNICSOLIC, " +
                       "         (SELECT " +
                       "          COUNT(1) " +
                       "          FROM TRAREGREFPAGO RP " +
                       "          WHERE RP.IEJERCICIO = RS.IEJERCICIO " +
                       "          AND RP.INUMSOLICITUD = RS.INUMSOLICITUD " +
                       "          AND RP.LPAGADO = 0) AS INUMPENDPAGO, " +
                       "       RS.LRESOLUCIONPOSITIVA, " +
                       "       RS.DUNIDADESCALCULO, " +
                       "       RS.IIDBIEN " +
                       "FROM TRAREGSOLICITUD RS " +
                       "  LEFT JOIN TRAREGTRAMXSOL ON TRAREGTRAMXSOL.IEJERCICIO = RS.IEJERCICIO " +
                       "    AND TRAREGTRAMXSOL.INUMSOLICITUD = RS.INUMSOLICITUD " +
                       "  JOIN TRACATTRAMITE T ON RS.ICVETRAMITE = T.ICVETRAMITE " +
                       "  JOIN TRAREGETAPASXMODTRAM ET2 ON ET2.IEJERCICIO = RS.IEJERCICIO " +
                       "    AND ET2.INUMSOLICITUD = RS.INUMSOLICITUD " +
                       "    AND ET2.ICVETRAMITE = RS.ICVETRAMITE " +
                       "    AND ET2.ICVEMODALIDAD = RS.ICVEMODALIDAD " +
                       "    AND ET2.ICVEETAPA = 21 " +
                       "  JOIN GRLDOMICILIO D ON D.ICVEPERSONA = RS.ICVEREPLEGAL " +
                       "    AND D.ICVEDOMICILIO = RS.ICVEDOMICILIOREPLEGAL " +
                       "  JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = D.ICVEPAIS " +
                       "  JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = D.ICVEPAIS " +
                       "    AND GRLENTIDADFED.ICVEENTIDADFED = D.ICVEENTIDADFED " +
                       "  JOIN GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS = D.ICVEPAIS " +
                       "    AND GRLMUNICIPIO.ICVEENTIDADFED = D.ICVEENTIDADFED " +
                       "    AND GRLMUNICIPIO.ICVEMUNICIPIO = D.ICVEMUNICIPIO " +
                       "  JOIN GRLLOCALIDAD ON D.ICVEPAIS = GRLLOCALIDAD.ICVEPAIS " +
                       "    AND GRLLOCALIDAD.ICVEENTIDADFED = D.ICVEENTIDADFED " +
                       "    AND GRLLOCALIDAD.ICVEMUNICIPIO = D.ICVEMUNICIPIO " +
                       "    AND GRLLOCALIDAD.ICVELOCALIDAD = D.ICVELOCALIDAD " +
                       "  JOIN GRLTIPODOMICILIO ON GRLTIPODOMICILIO.ICVETIPODOMICILIO = D.ICVETIPODOMICILIO " +
                       "  JOIN GRLPERSONA P ON P.ICVEPERSONA = RS.ICVEREPLEGAL " +
                       "  JOIN GRLDOMICILIO DP ON DP.ICVEPERSONA = RS.ICVESOLICITANTE " +
                       "    AND DP.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE " +
                       "  JOIN GRLPAIS PAISP ON PAISP.ICVEPAIS = DP.ICVEPAIS " +
                       "  JOIN GRLENTIDADFED EFP ON EFP.ICVEPAIS = DP.ICVEPAIS " +
                       "    AND EFP.ICVEENTIDADFED = DP.ICVEENTIDADFED " +
                       "  JOIN GRLMUNICIPIO MUNP ON MUNP.ICVEPAIS = DP.ICVEPAIS " +
                       "    AND MUNP.ICVEENTIDADFED = DP.ICVEENTIDADFED " +
                       "    AND MUNP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO " +
                       "  JOIN GRLLOCALIDAD LOCP ON DP.ICVEPAIS = LOCP.ICVEPAIS " +
                       "    AND LOCP.ICVEENTIDADFED = DP.ICVEENTIDADFED " +
                       "    AND LOCP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO " +
                       "    AND LOCP.ICVELOCALIDAD = DP.ICVELOCALIDAD " +
                       "  JOIN GRLPERSONA PS ON PS.ICVEPERSONA = RS.ICVESOLICITANTE " +
                       "  WHERE RS.IEJERCICIO = " + frm.iEjercicioBusqueda.value +
                       "    AND RS.INUMSOLICITUD = " + frm.iNumSolicitudBusqueda.value +
                       "    AND TRAREGTRAMXSOL.DTCANCELACION IS NULL " +
                       "  ORDER BY IEJERCICIO, " +
                       "       INUMSOLICITUD ";*/
  frm.hdNumReg.value = 10000;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp","Listado");
  }else fAlert("Es necesario llenar los campos de Ejercicio y Solicitud para esta busqueda.");
}

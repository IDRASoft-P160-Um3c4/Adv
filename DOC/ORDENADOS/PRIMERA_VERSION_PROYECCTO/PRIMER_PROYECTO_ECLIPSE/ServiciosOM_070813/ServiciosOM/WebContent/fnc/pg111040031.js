// MetaCD=1.0
 // Title: pg111020015.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author: mbeano && iCaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var iEtapaConcluida = 0;
 var iEtapaEntregarVU = 0;
 var iEtapaEntregarOfi = 0;
 var iEtapaRecibeResolucion = 0;
 var iEtapaResEnviadaOficialia = 0;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
  cPaginaWebJS = "pg111040031.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"BÚSQUEDA DE SOLICITUD":cTitulo;
   fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("ETablaInfo",0,"100%","","center","","1");
     ITRTD("",0,"","","center");
     InicioTabla("ETablaInfo1",0,"","","center","","1");
               ITD("EEtiqueta",0,"0","","center","middle");
                 TextoSimple("Trámite:");
              FTD();
            ITD("EEtiquetaL",0,"0","","center","middle");
              Text(false,"cCveTramite","",7,6,"Teclee la clave interna del trámite para ubicarlo","fTamOnChange();this.value='';"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramite, this);\"","",true,true)+
              Select("iCveTramite","fTamOnChange();");
            FTD();
            FTR();
       FinTabla();
        Hidden("hdSelect");
        Hidden("hdLlave");
     ITRTD("",0,"","","center");
     InicioTabla("ETablaInfo1",0,"","","center","","1");
       TDEtiSelect("true","EEtiqueta",0,"Oficina","iCveOficinaFiltro","","",0);
     FinTabla();

      InicioTabla("ETablaInfo",0,"","","center","","1");
         ITRTD("",0,"","","center");
           TDEtiCampo(false,"EEtiqueta",0,"RFC Inicie con:","cRfc","",15,15,"RFC","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"");
           TDEtiCampo(false,"EEtiqueta",0,"Solicitante:","cNombre","",65,65,"Solicitante","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"","");
           ITD("EEtiquetaL",2,"");
               BtnImgNombre("btnBusSol","buscar","fAbreSolicitante();","Buscar personas físicas o morales", false, "", "BuscaSol");
               TextoSimple("Buscar Persona");
           FTD();
         FTDTR();
         ITRTD("",0,"","","center");
            TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicioFiltro","",4,4,"Ejercicio","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ");
            TDEtiCampo(false,"EEtiqueta",0,"No. Solicitud:","iNumSolicitudFiltro","",8,8,"Núm. Solicitud","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ");
            ITD("",0,"","","Right","Center");
              BtnImg("Buscar","lupa","fBuscar();");
            FTD();
         FTDTR();
       FinTabla();
     FTDTR();
   FinTabla();

  InicioTabla("",0,"98%","","center","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple("Busqueda de Solicitudes");
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","175","center","top");
         IFrame("IListado","100%","300","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
       FTDTR();
/*       ITRTD("",0,"","20","center","top");
         Liga("Seleccionar","fSeleccionar();","Selección de la Solicitud");
       FTDTR();*/
       FinTabla();
     FTDTR();
     FinTabla();
     Hidden("iEjercicio","");
     Hidden("iNumSolicitud","");
//     Hidden("hdLlave","");
//     Hidden("hdSelect","");
//     Hidden("cNomRazonSocial");
     Hidden("iCveEjercicio","");
     Hidden("cDscTramite","");
     Hidden("iCveModalidad","");
     Hidden("cDscModalidad","");
     Hidden("iCveOficinaUsr","");
     Hidden("cDscOficinaUsr","");
     Hidden("iCveTramiteA1","");
     Hidden("iCveUsuario",fGetIdUsrSesion());
   fFinPagina();
 }

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Ejercicio,Solicitud,Trámite,Modalidad,RFC,Solicitante,Registro,Resolución,Oficina,Departamento,Bien,Ofi. Resolución,Depto. Resolución,Enviar Resolución,");
   FRMListado.fSetCampos("0,1,4,6,9,8,10,14,16,18,19,23,24,25,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,texto,texto,");
   FRMListado.fSetAlinea("center,center,left,left,center,left,right,center,left,left,left,");
//   fDisabled(true,"iCveTramiteFiltro,cRfc,iEjercicioFiltro,iNumSolicitudFiltro,");
   fDisabled(true,"cCveTramite,iCveTramite,iEjercicioFiltro,iNumSolicitudFiltro,iCveOficinaFiltro,");
   frm.hdBoton.value="Primero";
   fTramite();
//   fCargaTramite();
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = '';
   frm.hdOrden.value =  ""; //FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  "10000"; //FRMFiltro.fGetNumReg();
   if (frm.iCveOficinaFiltro.value > 0){
     if (frm.hdFiltro.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TXO.ICVEOFICINARESUELVE = "+frm.iCveOficinaFiltro.value;
     else
       frm.hdFiltro.value = " TXO.ICVEOFICINARESUELVE = "+frm.iCveOficinaFiltro.value;

   }
   if (frm.iEjercicioFiltro.value != ""){
     if (frm.hdFiltro.value != "")
	frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.iEjercicio = " + frm.iEjercicioFiltro.value;
     else
       frm.hdFiltro.value = " TRARegSolicitud.iEjercicio = " + frm.iEjercicioFiltro.value;
   }

   if (frm.iNumSolicitudFiltro.value != ''){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
     else
       frm.hdFiltro.value = " TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
   }

   if (frm.hdFiltro.value != "")
	frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.lImpreso = 1 " ;
   else
       frm.hdFiltro.value = " TRARegSolicitud.lImpreso = 1 ";

   if (frm.iCveTramiteA1.value != "" && frm.iCveTramiteA1.value != "-1"){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.iCveTramite = " + frm.iCveTramiteA1.value;
     else
       frm.hdFiltro.value = " TRARegSolicitud.iCveTramite = " + frm.iCveTramiteA1.value;
   }

   if (frm.cRfc.value != ''){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and GRLPERSONA.CRFC like '"+frm.cRfc.value +"%'";
     else
       frm.hdFiltro.value = " GRLPERSONA.CRFC like '"+frm.cRfc.value +"%'";
   }
   frm.hdOrden.value = " TRAREGSOLICITUD.IEJERCICIO desc, TRAREGSOLICITUD.INUMSOLICITUD desc";
   return fEngSubmite("pg111020015A2.jsp","Listado");
 }

 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,cEtapas){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cError!="")
     FRMFiltro.fSetNavStatus("Record");

   if(cId == "Listado" && cError==""){
     var aEtapas = new Array();
     aEtapas = cEtapas.split(",");
     frm.hdRowPag.value = iRowPag;
     if (aRes.length > 0)
       //iLong_aResMasUno = aRes[0].length;
     for (var i=0;i<aRes.length;i++){
       if(aRes[i][14]==0 && (aEtapas[0] == aRes[i][20] || aEtapas[1] == aRes[i][20] || aEtapas[2] == aRes[i][20] ||
          aEtapas[3] == aRes[i][20] || aEtapas[4] == aRes[i][20] || aEtapas[5] == aRes[i][20] || aEtapas[6] == aRes[i][20]))
         aRes[i][14]="NEGATIVO";
       else if(aRes[i][14]==0)
         aRes[i][14]="PENDIENTE";

       if(aRes[i][14]==1)
         aRes[i][14]="POSITIVO";
     }

     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
   }

   if(cId == "idTramite" && cError==""){
     for(i=0;i<aRes.length;i++){
       aRes[i][1]=aRes[i][2]+" - "+aRes[i][1];
     }
     fFillSelect(frm.iCveTramite,aRes,true,0,0,1);
     fOficina();
   }
   if(cId=="cIdOficina" && cError==""){
     fFillSelect(frm.iCveOficinaFiltro,aRes,false,frm.iCveOficinaFiltro.value,0,1);
     if(top.opener){
	 if(top.opener.fGetiNumSolicitud){
	     if(top.opener.fGetiNumSolicitud()>0)fBuscar();
	 }
     }
   }
 }

 function fCargaTramite(){
     frm.hdFiltro.value =  "";
     frm.hdOrden.value =  "TRACatTramite.cDscBreve";
     frm.hdNumReg.value =  "10000";
     fEngSubmite("pgTraCatTramite.jsp","idTramite");
}

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    //FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iEjercicioFiltro,","--");
    FRMListado.fSetDisabled(true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
          //FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         //FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicio,");
    FRMListado.fSetDisabled(true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    fDisabled(false, "cRfc,cNombre,");
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
    frm.iEjercicio.value = aDato[0];
    frm.iNumSolicitud.value = aDato[1];
//    frm.iCveTramiteA1.value = aDato[2];
    frm.cDscTramite.value = aDato[4];
    frm.cDscModalidad.value = aDato[6];
    frm.cDscOficinaUsr.value = aDato[16];
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

 function fBuscar(){
   fNavega();
 }

/* function fSeleccionar(){
   if (frm.iEjercicio.value != '' && frm.iNumSolicitud.value != ''){
     top.opener.fSetSolicitud(frm.iEjercicio.value,frm.iNumSolicitud.value,frm.iCveOficinaUsr.value,frm.iCveDeptoUsr.value);
     self.focus();
     top.close();
   } else {
     alert('Debe Seleccionar Primero la Solicitud');
   }
 }*/

// Definir en paginas que requieran datos de persona o persona y representante legal
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

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio, iCveTramite, iCveModalidad,
                         lPagoAnticipado, lDesactivarAnticipado, lBuscarSolicitante){
     frm.cRfc.value = cRFC;
     frm.cNombre.value = cNomRazonSocial + " " + cApPaterno + " " + cApMaterno;
     frm.iEjercicioFiltro.focus();
}

function fTramite(){
   frm.hdSelect.value = "SELECT ICVETRAMITE,CDSCBREVE,CCVEINTERNA FROM TRACATTRAMITE Where LVIGENTE=1 order by CCVEINTERNA ";
   frm.hdLlave.value = "ICVETRAMITE";
   return fEngSubmite("pgConsulta.jsp","idTramite");
}
function fTamOnChange(){
  fActualizaDatos();
}
function fActualizaDatos(){
    frm.iCveTramiteA1.value = frm.iCveTramite.value
}

 function fGetiCveEjercicio(){
      frm.iCveEjercicio.value = frm.iEjercicio.value;
  return  frm.iCveEjercicio.value;
}
function fGetiNumSolicitud(){
  return  frm.iNumSolicitud.value;
}
function fGetcTramite(){
//         frm.iCveTramite.value = frm.cDscTramite.value;
  return frm.iCveTramite.value = frm.cDscTramite.value;
}
function fGetcModalidad(){
         frm.iCveModalidad.value = frm.cDscModalidad.value;
  return frm.iCveModalidad.value;
}
function fGetcOficina(){
         frm.iCveOficinaUsr.value = frm.cDscOficinaUsr.value;
  return frm.iCveOficinaUsr.value;
}
function fEnterLocal(theObject, theEvent, theWindow){
  objName = theObject.name;
  if (objName == 'cRfc' || objName == 'cNombre' || objName == 'iEjercicioFiltro' ||
      objName == 'iNumSolicitudFiltro'){
    fMayus(theObject);
    fNavega();
  }
}
function fOficina(){
  frm.hdSelect.value = "SELECT distinct(UO.icveoficina),O.CDSCBREVE " +
                       "FROM GRLUSUARIOXOFIC UO " +
                       "JOIN GRLOFICINA O ON O.ICVEOFICINA=UO.ICVEOFICINA " +
                       "where icveusuario= "+frm.iCveUsuario.value;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp","cIdOficina");
}

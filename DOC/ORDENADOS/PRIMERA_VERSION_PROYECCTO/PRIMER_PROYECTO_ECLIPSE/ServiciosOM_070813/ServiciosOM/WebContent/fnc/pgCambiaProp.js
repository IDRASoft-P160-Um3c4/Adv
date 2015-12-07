// MetaCD=1.0
 // Title: pgCambiaPropietario.js
 // Description: JS "Catálogo" de la entidad VEHEmbarcacion
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ABarrientos
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pgCambiaProp.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       /*ITRTD("",0,"","40","center","top");
         IFrame("IFiltro","95%","34","Filtros.js");
       FTDTR();*/
       /*ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","170","Listado.js","yes",true);
       FTDTR();*/
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              TDEtiCampo(true,"EEtiqueta",0,"NombreEmbarcacion:","cNomEmbarcacion","",75,75,"NombreEmbarcacion","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Propietario:","cPropietario","",80,80,"Propietario","fMayus(this);","","",false,"",3);
              ITD();
                Liga("Cambiar Propietario","fAbreSolicitante();","Cambia el propietario de una embarcación");
              FTD();
           FITR();
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
       Hidden("iCvePropietario","");
       Hidden("iCvePersona","");
       Hidden("iCveVehiculo","");
       Hidden("cRFC","");
       Hidden("iTipoPersona","");

     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
       FITR();
        ITD("",9,"","","center","");
          BtnImgNombre("vgcerrar","aceptar","fRegresa();","Regresar a la ventana anterior con la embarcación seleccionada");
       FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   /*FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("ClaveVehiculo,NombreEmbarcacion,ClavePaisAbanderamiento,ClaveTipoEmbarcacion,ClaveTipoNavega,ClaveTipoServ,GranVelocidad,Clave,ClaveMaterial,Eslora,ClaveUnidadMedida,Manga,UniMedManga,Puntal,CaladoPopa,UniMedPuntal,CaladoProa,CaladoMax,UniMedCaladoPopa,ArqueoBruto,ArqueoNeto,UniMedCaladoProa,PesoMuerto,VelocidadMaxima,UniMedCaladoMax,ClaveTipoCarga,NumeroBodegas,UniMedArqueoBruto,NumeroTanques,NumeroOMI,UniMedArqueoNeto,Numeral,Clase,UniMedPesoMuerto,PotenciaTotal,UnidadMedPotencia,UniMedVelocidadMax,TripulacionMin,TripulacionMax,ValorEmbarcacion,LugarConstruccion,Combustible,");*/
   /*FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("ClaveVehiculo,iCveVehiculo,NombreEmbarcacion,cNomEmbarcacion,");
   FRMFiltro.fSetOrdena("ClaveVehiculo,iCveVehiculo,NombreEmbarcacion,cNomEmbarcacion,");*/
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fObtieneDatos();
   //fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   /*frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();*/
   return fEngSubmite("pgVEHEmbarcacionA.jsp","Listado");
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   /*if(cError!="")
     FRMFiltro.fSetNavStatus("Record");*/

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     /*FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);*/
     fCancelar();
     //FRMFiltro.fSetNavStatus(cNavStatus);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveVehiculo,","--");
    //FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    frm.hdBoton.value = "GuardarA";
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          fRegresa();
          //FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         //FRMListado.fSetDisabled(false);
        }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    //fDisabled(false,"iCveVehiculo,");
    //FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    //FRMFiltro.fSetNavStatus("ReposRecord");
    /*if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");*/
    fDisabled(true);
    //FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    /*frm.iCveVehiculo.value = aDato[0];
    frm.cNomEmbarcacion.value = aDato[1];
    frm.iCvePropietario.value = aDato[7];*/

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

// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetClaves(frmDestino){
  if (frmDestino.setClaves)
    frmDestino.setClaves(frm.iTipoPersona.value, frm.iCvePersona.value);
}

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio, iCveTramite, iCveModalidad,
                         lPagoAnticipado, lDesactivarAnticipado, lBuscarSolicitante){

     frm.iCvePersona.value = iCvePersona;
     frm.iCvePropietario.value = iCvePersona;
     frm.cRFC.value = cRFC;
     frm.cPropietario.value = cNomRazonSocial + " " + cApPaterno + " " + cApMaterno;
     frm.iTipoPersona.value  = iTipoPersona;

     /*frm.cCalle.value        = cCalle;
     frm.cNumExterior.value  = cNumExterior;
     frm.cNumInterior.value  = cNumInterior;
     frm.cColonia.value      = cColonia;
     frm.cCodPostal.value    = cCodPostal;
     frm.iCvePais.value      = iCvePais;
     frm.iCveEntidadFed.value = iCveEntidadFed;
     frm.iCveMunicipio.value = iCveMunicipio;
     frm.cDscEntidad.value   = cDscEntidadFed;
     frm.cDscMunicipio.value = cDscMunicipio;

     frm.iCveLocalidad.value = iCveLocalidad;*/
     /*if(parseInt(frm.iCvePersona.value,10)>0)
       FRMPanel.fSetTraStatus("Sav,");*/
     fModificar();

}

function fObtieneDatos(){
  if(top.opener){
    if(top.opener.fGetICveVehiculo)
      frm.iCveVehiculo.value = top.opener.fGetICveVehiculo();

    if(top.opener.fGetCNomEmbarcacion)
      frm.cNomEmbarcacion.value = top.opener.fGetCNomEmbarcacion();

    if(top.opener.fGetCPropietario)
      frm.cPropietario.value = top.opener.fGetCPropietario();

    if(top.opener.fGetITipoPersona)
      frm.iTipoPersona.value = top.opener.fGetITipoPersona();

    if(top.opener.fGetICvePropietario)
      frm.iCvePersona.value = top.opener.fGetICvePropietario();

  }

}

 function fRegresa(){
   if(top.opener){
     if(top.opener.fNavega)
       top.opener.fNavega();

     self.focus();
     top.close();
   }

 }














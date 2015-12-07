// MetaCD=1.0 
 // Title: pg111020101.js
 // Description: JS "Catálogo" de la entidad TRAConceptoXTramMod
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: cabrito
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg111020101.js";
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
       ITRTD("",0,"","0","center","top"); 
         IFrame("IFiltro101","0","0","Filtros.js"); 
       FTDTR(); 
       ITRTD("",0,"","90","center","top"); 
         IFrame("IListado101","95%","85","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"75%","","","",1); 
           ITR(); 
              TDEtiCampo(true,"EEtiqueta",0,"Ref. Numerica:","iRefNumerica","",10,10," Comentario: ","fMayus(this);"); 
              TDEtiCampo(true,"EEtiqueta",0,"Importe a Pagar:","dImportePagar","",10,10," Comentario: ","fMayus(this);"); 
           FITR();
            
           FTR(); 
         FinTabla(); 
       FTDTR(); 
       FinTabla(); 
     FTDTR(); 
       ITRTD("",0,"","0","center","bottom"); 
         IFrame("IPanel101","0","0","Paneles.js"); 
       FTDTR(); 
       ITRTD("",0,"","100","center","bottom"); 
         IFrame("IRefPago","100%","135","pg111020102.js"); 
       FTDTR(); 

     FinTabla(); 

     Hidden("cDscConcepto","");
     Hidden("dtIniVigencia","");
     Hidden("iConsecutivo","");
     Hidden("dImportePagado","");
     Hidden("dtPago","");
     Hidden("iCveBanco","");
     Hidden("cFormatoSAT","");
     Hidden("viNumSolicitud","");
     Hidden("iRefNumericaIngresos",""); 
     Hidden("iEjercicio","");
     Hidden("iCveTramite","");
     Hidden("iCveModalidad","");
     Hidden("iCveConcepto","");
     Hidden("cRefAlfaNum",""); 
     Hidden("varReqPago","");
     Hidden("dtEntrega","");
   fFinPagina(); 
 } 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("IPanel101"); 
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("IListado101"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo("Concepto, Referencia Numerica,"); 
   FRMListado.fSetCampos("4,6,"); 
   FRMFiltro = fBuscaFrame("IFiltro101"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra(" Comentario: ,iEjercicio, Comentario: ,iCveTramite,"); 
   FRMFiltro.fSetOrdena(" Comentario: ,iEjercicio, Comentario: ,iCveTramite,"); 
   fDisabled(true); 
   //frm.hdBoton.value="Primero"; 
   //fNavega(); 
 } 
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "";  
   if (frm.iEjercicio.value != "" && frm.iEjercicio.value != 0)
     frm.hdFiltro.value = " TRAConceptoXTramMod.iEjercicio = " + frm.iEjercicio.value;
   
   if (frm.hdFiltro.value != ""){
     if (frm.iCveTramite.value != "" && frm.iCveTramite.value != 0)
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRAConceptoXTramMod.iCveTramite = " + frm.iCveTramite.value
   } else {
     if (frm.iCveTramite.value != "" && frm.iCveTramite.value != 0)
       frm.hdFiltro.value = " TRAConceptoXTramMod.iCveTramite = " + frm.iCveTramite.value
   }
   
   if (frm.hdFiltro.value != ""){
     if (frm.iCveModalidad.value != "" && frm.iCveModalidad.value != 0)
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRAConceptoXTramMod.iCveModalidad = " + frm.iCveModalidad.value;
   } else {
     if (frm.iCveModalidad.value != "" && frm.iCveModalidad.value != 0)
       frm.hdFiltro.value = " TRAConceptoXTramMod.iCveModalidad = " + frm.iCveModalidad.value;
   } 
   
   frm.hdOrden.value =  ""; 
   frm.hdNumReg.value =  10000;
   
   return fEngSubmite("pgTRACTM.jsp","Listado");
 
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
   if(cError!="") 
     FRMFiltro.fSetNavStatus("Record"); 
  
   if(cId == "Listado" && cError==""){ 
     frm.hdRowPag.value = iRowPag; 
     FRMListado.fSetListado(aRes); 
     FRMListado.fShow(); 
     FRMListado.fSetLlave(cLlave); 
     fCancelar(); 
     FRMFiltro.fSetNavStatus(cNavStatus);

     //fOtros(frm.iEjercicio.value,frm.viNumSolicitud.value);
   } 
   if(cId == "idUnidades" && cError==""){
      frm.varReqPago.value = aRes[0][24];
  } 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fBlanked(); 
    fDisabled(false,"iEjercicio,","--"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
          FRMPanel.fSetTraStatus("Mod,"); 
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
         FRMPanel.fSetTraStatus("Mod,"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false,"iEjercicio,iCveTramite,iCveModalidad,iCveConcepto,cDscConcepto,dtIniVigencia,iRefNumericaIngresos,iRefNumerica,cRefAlfaNum,dImportePagar,"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){ 
    FRMFiltro.fSetNavStatus("ReposRecord"); 
    if(FRMListado.fGetLength() > 0) 
      FRMPanel.fSetTraStatus("Mod,"); 
    else 
      FRMPanel.fSetTraStatus("AddOnly"); 
    fDisabled(true); 
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
    frm.iCveTramite.value = aDato[1]; 
    frm.iCveModalidad.value = aDato[2]; 
    frm.iCveConcepto.value = aDato[3]; 
    frm.cDscConcepto.value = aDato[4]; 
    frm.dtIniVigencia.value = aDato[5]; 
    frm.iRefNumericaIngresos.value = aDato[6]; 
    frm.iRefNumerica.value = aDato[7]; 
    frm.cRefAlfaNum.value = aDato[8]; 
    frm.dImportePagar.value = aDato[9]; 
    frm.dImportePagado.value = aDato[10]; 
    frm.dtPago.value = aDato[11]; 
    frm.iCveBanco.value = aDato[12]; 
    frm.cFormatoSAT.value = aDato[13]; 
    frm.iConsecutivo.value = aDato[14];
    
     if ((frm.iEjercicio.value != "" && frm.iEjercicio.value != 0) && 
         (frm.iCveTramite.value != "" && frm.iCveTramite.value != 0) && 
         (frm.iCveModalidad.value != "" && frm.iCveModalidad.value != 0)){
       FRMPrimero = fBuscaFrame("IRefPago");
       FRMPrimero.fSetSolicitud(frm.iEjercicio.value,frm.viNumSolicitud.value,frm.iRefNumericaIngresos.value,frm.dtEntrega.value);
     }
     //else{
     //  FRMPrimero = fBuscaFrame("IRefPago");
     //  FRMPrimero.fSetSolicitud("0","0","0");
     //  fOtros(frm.iEjercicio.value,frm.viNumSolicitud.value);       
     //}
 
    
    
     
    
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

function fSetSolicitud(viEjercicio,viCveTramite,viCveModalidad,viNumSolicitud,dtEntrega){
  frm.iEjercicio.value = viEjercicio;
  frm.iCveTramite.value = viCveTramite;
  frm.iCveModalidad.value = viCveModalidad;
  frm.viNumSolicitud.value = viNumSolicitud;
  frm.dtEntrega.value = dtEntrega;
  fNavega();
}

function fGetRequierePago(){
   return frm.varReqPago.value;
}
function fGetEjercicio(){
   return frm.iEjercicio.value;
}
function fGetSolicitud(){
   return frm.viNumSolicitud.value;
}

function fOtros(iEjercicio,iNumSolicitud){
    frm.hdFiltro.value ="";
    if (iEjercicio != 0 && iNumSolicitud != 0)
      frm.hdFiltro.value =  " iejercicio = " + iEjercicio +
                            " and inumsolicitud = " + iNumSolicitud +
                            " and TRAConfiguraTramite.dtIniVigencia = ( " +
                            " select max(cfgtra.dtIniVigencia) " +
                            " from TRARegSolicitud sol " +
                            " join TRAConfiguraTramite cfgtra on cfgtra.iCveTramite = sol.iCveTramite " +
                            " and cfgtra.iCveModalidad = sol.iCveModalidad " +
                            " where sol.iEjercicio = " + iEjercicio  + " and sol.iNumSolicitud = " + iNumSolicitud + ") ";
                                
    frm.hdOrden.value =  ""; 
    frm.hdNumReg.value =  10000; 
    fEngSubmite("pgTRARegSolicitudA2.jsp","idUnidades");
}

 function fSetdtEntrega(dtEntrega){
   frm.dtEntrega.value = dtEntrega;
 }


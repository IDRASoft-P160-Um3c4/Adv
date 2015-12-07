// MetaCD=1.0 
 // Title: pg111020102.js
 // Description: JS "Catálogo" de la entidad TRARegRefPago
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: cabrito
 var cTitulo = ""; 
 var FRMListado = ""; 
 var aCombo = new Array(); 
 var frm; 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg111020102.js";
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
         IFrame("IFiltro102","0","0","Filtros.js"); 
       FTDTR(); 
       ITRTD("",0,"","0","center","top"); 
         IFrame("IListado102","0","0","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"75%","","","",1); 
           ITR();       
              TDEtiCampo(true,"EEtiqueta",0,"Ref. Alfanumerica:","cRefAlfaNum","",15,15," Comentario: ","fMayus(this);"); 
              TDEtiCampo(true,"EEtiqueta",0,"Importe Pagado:","dImportePagado","",8,8," Comentario: ","fMayus(this);"); 
              TDEtiCampo(true,"EEtiqueta",0,"Fecha de Pago:","dtPago","",10,10," Comentario: ","fMayus(this);"); 
           FITR(); 
              //TDEtiCampo(true,"EEtiqueta",0,"Banco:","iCveBanco","",3,3," Comentario: ","fMayus(this);"); 
              TDEtiSelect(true,"EEtiqueta",0,"","iCveBanco",""); 
              TDEtiCampo(true,"EEtiqueta",0,"Formato SAT:","cFormatoSAT","",15,15," Comentario: ","fMayus(this);");
              TDEtiCheckBox("EEtiqueta",0," Pagado:","lPagadoBOX","1",true,"Pagado"); 
              Hidden("lPagado","");
               
           FTR(); 
         FinTabla(); 
       FTDTR(); 
       FinTabla(); 
     FTDTR(); 
       ITRTD("",0,"","40","center","bottom"); 
         IFrame("IPanel102","95%","34","Paneles.js"); 
       FTDTR(); 
     FinTabla();
     Hidden("iEjercicio","");
     Hidden("iNumSolicitud","");
     Hidden("iConsecutivo","");
     Hidden("iRefNumerica","");
     Hidden("dImportePagar","");
     Hidden("dtEntrega","");
   fFinPagina(); 
 } 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("IPanel102"); 
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("IListado102"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo(" Comentario: , Comentario: , Comentario: , Comentario: , Comentario: , Comentario: , Comentario: , Comentario: , Comentario: , Comentario: ,"); 
   FRMFiltro = fBuscaFrame("IFiltro102"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra(" Comentario: ,iEjercicio, Comentario: ,iNumSolicitud,"); 
   FRMFiltro.fSetOrdena(" Comentario: ,iEjercicio, Comentario: ,iNumSolicitud,"); 
   fDisabled(true); 
   frm.hdBoton.value="Primero"; 

   //fCargaBancos(); 
 } 
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "";
   if (frm.iEjercicio.value != "")
     frm.hdFiltro.value = " iEjercicio = " + frm.iEjercicio.value; 
   
   if (frm.hdFiltro.value != ""){
     if (frm.iNumSolicitud.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and iNumSolicitud = " + frm.iNumSolicitud.value;
   } else {
     if (frm.iNumSolicitud.value != "")
       frm.hdFiltro.value = " iNumSolicitud = " + frm.iNumSolicitud.value;
   }

   if (frm.hdFiltro.value != ""){
     if (frm.iRefNumerica.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and iRefNumerica = " + frm.iRefNumerica.value;
   } else {
     if (frm.iRefNumerica.value != "" && frm.iRefNumerica.value != 0)
       frm.hdFiltro.value = " iRefNumerica = " + frm.iRefNumerica.value;
   }
   frm.hdOrden.value =  ""; 
   frm.hdNumReg.value =  10000;
   
   return fEngSubmite("pgTRARegRefPagoA1.jsp","Listado"); 
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
   } 
   if( cId == "idBanco" && cError == "" ) {
     aCombo = aRes;
     fNavega();
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
   frm.lPagadoBOX.checked==true?frm.lPagado.value=1:frm.lPagado.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         if (frm.dtEntrega.value != undefined && frm.dtEntrega.value != "")
            FRMPanel.fSetTraStatus("Disabled");         
         else
            FRMPanel.fSetTraStatus("Mod,");         
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){ 
   frm.lPagadoBOX.checked==true?frm.lPagado.value=1:frm.lPagado.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         if (frm.dtEntrega.value != undefined && frm.dtEntrega.value != "")
           FRMPanel.fSetTraStatus("Disabled");
         else  
           FRMPanel.fSetTraStatus("Mod,");         
         FRMPanel.fSetTraStatus("Mod,"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){ 
    fFillSelect(frm.iCveBanco,aCombo,false,frm.iCveBanco.value,0,1);
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false,"iEjercicio,"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){ 
    FRMFiltro.fSetNavStatus("ReposRecord"); 
    if(FRMListado.fGetLength() > 0){
      if (frm.dtEntrega.value != undefined && frm.dtEntrega.value != "")
        FRMPanel.fSetTraStatus("Disabled");      
      else  
        FRMPanel.fSetTraStatus("Mod,");
    }   
    else 
      FRMPanel.fSetTraStatus("Can,"); 
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
    frm.iNumSolicitud.value = aDato[1]; 
    frm.iConsecutivo.value = aDato[2]; 
    frm.iRefNumerica.value = aDato[3]; 
    frm.cRefAlfaNum.value = aDato[4]; 
    frm.dImportePagar.value = aDato[5]; 
    frm.dImportePagado.value = aDato[6]; 
    frm.dtPago.value = aDato[7]; 
    //frm.iCveBanco.value = aDato[8];
    fAsignaSelect(frm.iCveBanco,aDato[8],fAsgCombos(aDato[8]));     
    frm.cFormatoSAT.value = aDato[9]; 
    fAsignaCheckBox(frm.lPagadoBOX,aDato[10]);
    
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

 function fSetSolicitud(viEjercicio,viNumSolicitud,viRefNumerica,dtEntrega){
   frm.iEjercicio.value = viEjercicio;
   frm.iNumSolicitud.value = viNumSolicitud;
   frm.iRefNumerica.value = viRefNumerica;
   frm.dtEntrega.value = dtEntrega;
   
   //fNavega();
   fCargaBancos();
 }

 function fAsgCombos(valor) {
   if( valor == "" ) {
     return "";
   }  
   else {
     return aCombo[valor-1][1];
   }  
 }
 
 function fCargaBancos(){
   frm.hdFiltro.value = ""; 
   frm.hdOrden.value =  ""; 
   frm.hdNumReg.value =  10000; 
   fEngSubmite("pgGRLBanco.jsp","idBanco"); 
 }

 function fSetdtEntrega(dtEntrega){
   frm.dtEntrega.value = dtEntrega;
   fCancelar();
 }
 
  
  
    
// MetaCD=1.0 
 // Title: pg111020104.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: cabrito
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 var aRegRefPago = new Array();
 
  
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg111020104.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
 } 
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){ 
   fInicioPagina(cColorGenJS); 
   InicioTabla("",0,"0","","","","1"); 
     ITRTD("",0,"","","top"); 
     InicioTabla("",0,"0","","center"); 
       ITRTD("",0,"","0","center","top"); 
         IFrame("IFiltro104","0","0","Filtros.js"); 
       FTDTR(); 
       ITRTD("",0,"0","0","center","top"); 
         IFrame("IListado104","0","0","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","0","center"); 
         InicioTabla("ETablaInfo",0,"0","","","",1); 
           ITR(); 
              //TDEtiCheckBox("EEtiqueta",0,"Pagado:","lPagadoBOX","1",true," Comentario: "); 
              Hidden("lPagado","");
              //TDEtiCheckBox("EEtiqueta",0,"Resolucion Positiva:","lResolucionPositivaBOX","1",true," Comentario: "); 
              Hidden("lResolucionPositiva","");
           FTR(); 
           ITR(); 
              //TDEtiCheckBox("EEtiqueta",0,"Pagado 2:","lResolucionPositivaBOX2","1",true," Comentario: "); 
              Hidden("lResolucionPositiva2","");
              //TDEtiCheckBox("EEtiqueta",0,"ResolucPositiva 2:","lResolucionPositivaBOX2","1",true," Comentario: "); 
              Hidden("lResolucionPositiva2","");
           FITR();
         FinTabla(); 
       FTDTR(); 
       FinTabla(); 
     FTDTR(); 
       ITRTD("",0,"","0","center","bottom"); 
         IFrame("IPanel104","0","0","Paneles.js"); 
       FTDTR(); 
     FinTabla();
     InicioTabla("",0,"100%","","center"); 
          ITRTD("",0,"100%","200","center","bottom"); 
            IFrame("ISegA","95%","100%","pg111020105.js"); 
          FTDTR(); 
     FinTabla();


     
     Hidden("iEjercicio","");
     Hidden("iNumSolicitud","");
     Hidden("iCveTramite","");
     Hidden("iCveModalidad","");
     Hidden("iCveSolicitante","");
     Hidden("iCveRepLegal","");
     Hidden("cNomAutorizaRecoger","");
     Hidden("iCveUsuRegistra","");
     Hidden("tsRegistro","");
     Hidden("dtLimiteEntregaDocs","");
     Hidden("dtEstimadaEntrega","");
     Hidden("lPagado","");
     Hidden("dtEntrega","");
     Hidden("iCveUsuEntrega","");
     Hidden("lResolucionPositiva","");
     Hidden("lDatosPreregistro","");
     Hidden("iIdBien","");
     Hidden("iCveOficina","");
     Hidden("iCveDepartamento","");
     Hidden("iEjercicioCita","");
     Hidden("iIdCita","");
     Hidden("iCveForma","");
     Hidden("lPrincipal","");
     
   fFinPagina(); 
 } 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("IPanel104"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("IListado104"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo("Resolucion Positiva,Pagado,"); 
   FRMListado.fSetCampos("14,11,"); 
   FRMFiltro = fBuscaFrame("IFiltro104"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra(" Comentario: ,iEjercicio, Comentario: ,iNumSolicitud,"); 
   FRMFiltro.fSetOrdena(" Comentario: ,iEjercicio, Comentario: ,iNumSolicitud,"); 
   fDisabled(true); 
   frm.hdBoton.value="Primero";
   
 
 } 
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "";
   if (frm.iEjercicio.value != "")
     frm.hdFiltro.value =  " TRARegRefPago.iEjercicio = " + frm.iEjercicio.value;
   
   if (frm.hdFiltro.value != ""){
     if (frm.iNumSolicitud.value != "")
        frm.hdFiltro.value = frm.hdFiltro.value + 
                             " and TRARegRefPago.iNumSolicitud = " + frm.iNumSolicitud.value
   } else {
     if (frm.iNumSolicitud.value != "")
        frm.hdFiltro.value = " TRARegRefPago.iNumSolicitud = " + frm.iNumSolicitud.value
   }   
     
   frm.hdOrden.value =  ""; 
   frm.hdNumReg.value =  10000; 
   return fEngSubmite("pgTRARegSolicitudXRRPA1.jsp","Listado"); 
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
          FRMPanel.fSetTraStatus("UpdateComplete"); 
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
         FRMPanel.fSetTraStatus("UpdateComplete"); 
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
    FRMFiltro.fSetNavStatus("ReposRecord"); 
    if(FRMListado.fGetLength() > 0) 
      FRMPanel.fSetTraStatus("UpdateComplete"); 
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
    frm.iNumSolicitud.value = aDato[1]; 
    frm.iCveTramite.value = aDato[2]; 
    frm.iCveModalidad.value = aDato[3]; 
    frm.iCveSolicitante.value = aDato[4]; 
    frm.iCveRepLegal.value = aDato[5]; 
    frm.cNomAutorizaRecoger.value = aDato[6]; 
    frm.iCveUsuRegistra.value = aDato[7]; 
    frm.tsRegistro.value = aDato[8]; 
    frm.dtLimiteEntregaDocs.value = aDato[9]; 
    frm.dtEstimadaEntrega.value = aDato[10]; 
    frm.lPagado.value = aDato[11]; 
    //fAsignaCheckBox(frm.lPagadoBOX,aDato[11]);
    frm.dtEntrega.value = aDato[12]; 
    frm.iCveUsuEntrega.value = aDato[13]; 
    frm.lResolucionPositiva.value = aDato[14]; 
    //fAsignaCheckBox(frm.lResolucionPositivaBOX,aDato[14]);
    frm.lDatosPreregistro.value = aDato[15]; 
    frm.iIdBien.value = aDato[16]; 
    frm.iCveOficina.value = aDato[17]; 
    frm.iCveDepartamento.value = aDato[18]; 
    frm.iEjercicioCita.value = aDato[19]; 
    frm.iIdCita.value = aDato[20]; 
    frm.iCveForma.value = aDato[21]; 
    frm.lPrincipal.value = aDato[22]; 
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

function fSetEjercicioSolicitud(vEjercicio,vSolicitud){
   frm.iEjercicio.value = vEjercicio;
   frm.iNumSolicitud.value = vSolicitud;
   fNavega();
}

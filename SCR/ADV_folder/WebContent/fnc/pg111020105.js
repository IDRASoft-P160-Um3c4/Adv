// MetaCD=1.0 
 // Title: pg111020105.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: cabrito
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg111020105.js";
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
       ITRTD("",0,"","0","center","top"); 
         IFrame("IFiltro105","0","0","Filtros.js"); 
       FTDTR(); 
       ITRTD("",0,"","0","center","top"); 
         IFrame("IListado105","0","0","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"75%","","","",1); 
           ITRTD("ETablaST",5,"","","center"); 
             TextoSimple(cTitulo); 
           FTDTR(); 
           ITR(); 
//              TDEtiCheckBox("EEtiqueta",0,"lPagado:","lPagadoBOX","1",true," Comentario: "); 
//              TDEtiCheckBox("EEtiqueta",0,"lReolucion:","lResolucionPositivaBOX","1",true," Comentario: "); 
           FTR(); 
           ITR(); 
              TDEtiCheckBox("EEtiqueta",0,"Pagado:","lPagadoBOX2","1",true," Comentario: "); 
              Hidden("lPagado2","");
              TDEtiCheckBox("EEtiqueta",0,"Resolucion Positiva:","lResolucionPositivaBOX","1",true," Comentario: "); 
              Hidden("lResolucionPositiva","");
           FTR(); 
           ITD("",4,"","","center"); 
              //BtnImg("Guardar","guardar","fGuardaPagado();","Guardar");
           FTD(); 
           
         FinTabla(); 
       FTDTR(); 
       FinTabla(); 
     FTDTR(); 
       ITRTD("",0,"","40","center","bottom"); 
         IFrame("IPanel105","100%","100%","Paneles.js"); 
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
     Hidden("dtEntrega","");
     Hidden("iCveUsuEntrega","");
     Hidden("lDatosPreregistro","");
     Hidden("iIdBien","");
     Hidden("iCveOficina","");
     Hidden("iCveDepartamento","");
     Hidden("iEjercicioCita","");
     Hidden("iIdCita","");
     Hidden("iCveForma","");
     Hidden("lPrincipal","");     
     Hidden("lPagado","");

   fFinPagina(); 
 } 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("IPanel105"); 
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(true);    
   FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("IListado105"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo("Ejercicio,Solicitud,Pagado,Resuelto,"); 
   FRMListado.fSetCampos("0,1,11,14,"); 
   FRMFiltro = fBuscaFrame("IFiltro105"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra(" Comentario: ,iEjercicio, Comentario: ,iNumSolicitud,"); 
   FRMFiltro.fSetOrdena(" Comentario: ,iEjercicio, Comentario: ,iNumSolicitud,"); 
   fDisabled(true); 
   //frm.hdBoton.value="Primero";
 
//   fNavega(); 
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
       
   frm.hdOrden.value =  ""; 
   frm.hdNumReg.value =  10000; 
   return fEngSubmite("pgTRARegSolicitud4.jsp","Listado"); 
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
     fCargaPagos(); 
   } 

   if(cId == "idPago" && cError==""){
      var i;
      var estaPagado = 1;
      for(i=0;i<aRes.length;i++){
         if (parseInt(aRes[i][10])== 0){ 
             alert("asigna no pagado");
             estaPagado = 0;
             break;
         }
      }
      if (estaPagado == 1){
          frm.lPagado.value = 1;
          frm.lPagado2.value = 1;
          fAsignaCheckBox(frm.lPagadoBOX2,1);
      }
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
       if(confirm(cAlertMsgGen + "\n\n ¿ Desea Registrar la Entrega de la Solicitud ? ")){    
          if(fNavega()==true){
             if (frm.dtEntrega.value != "")
                FRMPanel.fSetTraStatus("Disabled");             
             else
                FRMPanel.fSetTraStatus("Sav,"); 
             fDisabled(true); 
             FRMListado.fSetDisabled(false); 
          }
       }  
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){ 
   if(fValidaTodo()==true){
     if(confirm(cAlertMsgGen + "\n\n ¿ Desea Registrar la Entrega de la Solicitud ? ")){ 
        if(fNavega()==true){ 
          FRMPanel.fSetTraStatus("Sav,"); 
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
        }
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
    if(FRMListado.fGetLength() > 0){
      if (frm.dtEntrega.value != "")
        FRMPanel.fSetTraStatus("Disabled");      
      else
        FRMPanel.fSetTraStatus("Sav,");
    }  
    else 
      FRMPanel.fSetTraStatus("Disabled"); 
    fDisabled(true); 
    FRMListado.fSetDisabled(false); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){ 
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){ 
       fNavega(); 
    } 
 }

 function fReporte(){
   alert("Oficio en Desarrollo");
   //cClavesModulo="4,4,4,";
   //cNumerosRep="1,2,3,";
   //cFiltrosRep= "¨";
   //fReportes();
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
    fAsignaCheckBox(frm.lResolucionPositivaBOX,aDato[14]);
    frm.lDatosPreregistro.value = aDato[15]; 
    frm.iIdBien.value = aDato[16]; 
    frm.iCveOficina.value = aDato[17]; 
    frm.iCveDepartamento.value = aDato[18]; 
    frm.iEjercicioCita.value = aDato[19]; 
    frm.iIdCita.value = aDato[20]; 
    frm.iCveForma.value = aDato[21]; 
    frm.lPrincipal.value = aDato[22];
    if (frm.dtEntrega.value != "")
      FRMPanel.fSetTraStatus("Disabled");
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

function fGuardaPagado(){
  var var1;
  if (parseInt(frm.lPagado2.value)>0){
    if (confirm("¿ Desea Entregar la Solicitud ? ")){
      var1 = top.fGetUsrID();
      if(parseInt(var1) != "NaN"){
        frm.iCveUsuEntrega.value = 1;
      }
      else {
        frm.iCveUsuEntrega.value = var1;
      }
      frm.lPagado.value = frm.lPagado2.value;
      frm.hdBoton.value="GuardarA";
      fEngSubmite("pgTRARegSolicitud4.jsp","Listado");
    } 
  } else   
    fAlert("No se encuentan pagados todos los conceptos de este tramite!! ");
}

function fCargaPagos(){
   frm.hdFiltro.value = "";
   if (frm.iEjercicio.value != "")
     frm.hdFiltro.value =  " iEjercicio = " + frm.iEjercicio.value;
   
   if (frm.hdFiltro.value != ""){
     if (frm.iNumSolicitud.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value + 
                            " and iNumSolicitud = " + frm.iNumSolicitud.value;
   } else {
     if (frm.iNumSolicitud.value != "")
       frm.hdFiltro.value = " iNumSolicitud = " + frm.iNumSolicitud.value;
   
   }
   frm.hdOrden.value =  ""; 
   frm.hdNumReg.value =  10000;
    
   fEngSubmite("pgTRARegRefPagoA1.jsp","idPago","hdFiltro=" + frm.hdFiltro.value +
                                                "&hdOrden=" + frm.hdOrden.value + 
                                                "&hdNumReg=" + frm.hdNumReg.value);
}

 function fGetdtEntrega(){
   return frm.dtEntrega.value;
 }


// MetaCD=1.0 
 // Title: pg114020071.js
 // Description: JS "Cat�logo" de la entidad INSInspeccion
 // Company: Tecnolog�a InRed S.A. de C.V. 
 // Author: Abarrientos
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg114020071.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
 } 
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){ 
   fInicioPagina(cColorGenJS); 
   InicioTabla("",0,"95%","","","","1"); 
     ITRTD("ESTitulo",0,"100%","","center"); 
       TextoSimple("DATOS DE SOLICITUD"); 
     FTDTR(); 
     ITRTD("",0,"","","top"); 
     InicioTabla("",0,"100%","","center"); 
       ITRTD("",0,"","0","center","top"); 
         IFrame("IFiltro71","0%","0","Filtros.js"); 
       FTDTR();
       
       
       ITRTD("",0,"","175","center","top"); 
         IFrame("ISolicitud","100%","490","pgSolicitud.js","yes",true); 
       FTDTR();
       
        
       ITRTD("",0,"","0","center","top"); 
         IFrame("IListado71","0%","0","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"75%","","","",1); 
           ITRTD("ETablaST",5,"","","center"); 
             TextoSimple("Estado de la Inspecci�n"); 
           FTDTR(); 
           ITR(); 
              //TDEtiCampo(true,"EEtiqueta",0," Clave de la Inspecci�n:","iCveInspeccion","",3,3," Clave de la inspecci�n ","fMayus(this);");
              TDEtiCheckBox("EEtiqueta",3," Inspecci�n finalizada:","lRegistroFinalizadoBOX","1",true," Indica si el registro de la inspecci�n fue finalizada "); 
              Hidden("lRegistroFinalizado","");
               
           FTR(); 
         FinTabla(); 
       FTDTR();
       
       FinTabla(); 
     FTDTR(); 
       //ITRTD("",0,"","40","center","bottom"); 
         //IFrame("IPanel","95%","34","Paneles.js"); 
       //FTDTR();
       Hidden("iCveEjercicio","0");
       Hidden("iNumSolicitud","0"); 
     FinTabla(); 
   fFinPagina(); 
 } 
 
 function fGetParms(parEjercicio, parNumSolicitud, parcTramite, parcModalidad, parCRFCSol, parCNomSol, parCAPPatSol, parCAPMatSol){
   //alert ('Ejercicio..'+parEjercicio+'....Solicitud..'+parSolicitud);
   frm.iCveEjercicio.value = parEjercicio;
   frm.iNumSolicitud.value = parNumSolicitud;
   fNavega();  
 }
 
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0];
   
   FMRSolicitud = fBuscaFrame("ISolicitud");
    
   //FRMPanel = fBuscaFrame("IPanel"); 
   //FRMPanel.fSetControl(self,cPaginaWebJS); 
   //FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("IListado71"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo(" Comentario: , Comentario: , Comentario: , Comentario: , Comentario: , Comentario: Clave del Puerto, Comentario: , Comentario: ,");
   
   FRMFiltro = fBuscaFrame("IFiltro71"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra(" Comentario: ,iCveInspeccion, Comentario: ,iCveInspProg,"); 
   FRMFiltro.fSetOrdena(" Comentario: ,iCveInspeccion, Comentario: ,iCveInspProg,"); 
   fDisabled(true); 
   frm.hdBoton.value="Primero"; 
   fNavega(); 
 } 
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){ 
   //frm.hdFiltro.value = FRMFiltro.fGetFiltro(); 
   frm.hdFiltro.value =  "INSProgInsp.iEjercicio = " + frm.iCveEjercicio.value + " AND INSProgInsp.iNumSolicitud = " + frm.iNumSolicitud.value;
   frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
   return fEngSubmite("pgVerifInsp.jsp","Listado"); 
 } 
 // RECEPCI�N de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){ 
   if(cError=="Guardar") 
     fAlert("Existi� un error en el Guardado!"); 
   if(cError=="Borrar") 
     fAlert("Existi� un error en el Borrado!"); 
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
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
 function fNuevo(){ 
    //FRMPanel.fSetTraStatus("UpdateBegin"); 
    fBlanked(); 
    fDisabled(false,"iCveInspeccion,","--"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
          //FRMPanel.fSetTraStatus("UpdateComplete"); 
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
 function fGuardarA(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
         //FRMPanel.fSetTraStatus("UpdateComplete"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
 function fModificar(){ 
    //FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false,"iCveInspeccion,"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
 function fCancelar(){ 
    FRMFiltro.fSetNavStatus("ReposRecord"); 
    /*if(FRMListado.fGetLength() > 0) 
      FRMPanel.fSetTraStatus("UpdateComplete"); 
    else 
      FRMPanel.fSetTraStatus("AddOnly");*/ 
    fDisabled(true); 
    FRMListado.fSetDisabled(false); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Borrar
 function fBorrar(){ 
    if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){ 
       fNavega(); 
    } 
 } 
 // LLAMADO desde el Listado cada vez que se selecciona un rengl�n
 function fSelReg(aDato){
    
    if (aDato[0] > 0){
      fAsignaCheckBox(frm.lRegistroFinalizadoBOX, 1);
    }else{
      fAsignaCheckBox(frm.lRegistroFinalizadoBOX, 0);
    }
  
 } 
 // FUNCI�N donde se generan las validaciones de los datos ingresados
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

 function fGetSolicitud(){
  return frm.iNumSolicitud.value;
 }
 
 function fGetEjercicio(){
  return frm.iCveEjercicio.value;
 }
 
 function fGetInspeccion(){
  return frm.lRegistroFinalizadoBOX; 
 }
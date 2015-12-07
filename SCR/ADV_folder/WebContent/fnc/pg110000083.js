// MetaCD=1.0 
 // Title: pg110000083.js
 // Description: JS "Catálogo" de la entidad GRLMimeType
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: Levi Equihua
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg110000083.js";
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
       ITRTD("",0,"","40","center","top"); 
         IFrame("IFiltro83","95%","34","Filtros.js"); 
       FTDTR(); 
       ITRTD("",0,"","175","center","top"); 
         IFrame("IListado83","95%","170","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"75%","","","",1); 
           ITRTD("ETablaST",5,"","","center"); 
             TextoSimple(cTitulo); 
           FTDTR(); 
           ITR(); 
              TDEtiCampo(true,"EEtiqueta",0,"MimeType:","cMimeType","",30,30,"MimeType","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0,"Extensiones:","cExtensiones","",100,100,"Extensiones","fMayus(this);"); 
           FTR(); 
         FinTabla(); 
       FTDTR(); 
       FinTabla(); 
     FTDTR(); 
       ITRTD("",0,"","40","center","bottom"); 
         IFrame("IPanel83","95%","34","Paneles.js"); 
       FTDTR(); 
     FinTabla(); 
   fFinPagina(); 
 } 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("IPanel83"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("IListado83"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo("MimeType,Extensiones,"); 
   FRMFiltro = fBuscaFrame("IFiltro83"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra("MimeType,cMimeType,Extensiones,cExtensiones,"); 
   FRMFiltro.fSetOrdena("MimeType,cMimeType,Extensiones,cExtensiones,"); 
   fDisabled(true); 
   frm.hdBoton.value="Primero"; 
   //fNavega(); 
 } 
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){ 
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
   frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
   return fEngSubmite("pgGRLMimeType.jsp","Listado"); 
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
 // LLAMADO al JSP específico para la navegación de la página
  function fNavega(){ 
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
   frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
   return fEngSubmite("pgGRLMimeType.jsp","Listado"); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fBlanked(); 
    fDisabled(false); 
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
    fDisabled(false,"cMimeType,"); 
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
    frm.cMimeType.value = aDato[0]; 
    frm.cExtensiones.value = aDato[1]; 
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

function fEjecutafNavega(){
  fNavega();
} 
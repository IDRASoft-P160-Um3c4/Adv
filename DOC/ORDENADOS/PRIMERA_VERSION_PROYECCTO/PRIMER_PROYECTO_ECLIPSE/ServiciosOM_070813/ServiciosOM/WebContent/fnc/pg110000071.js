// MetaCD=1.0 
 // Title: pg110000072.js
 // Description: JS "Cat�logo" de la entidad GRLModulo
 // Company: Tecnolog�a InRed S.A. de C.V. 
 // Author: ijimenez
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg110000071.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
 } 
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){ 
   fInicioPagina(cColorGenJS); 
   InicioTabla("",0,"100%","","","","1"); 
     ITRTD("ESTitulo",0,"100%","","center"); 
       TextoSimple(cTitulo); 
     FTDTR(); 
     ITRTD("",0,"","","top"); 
     InicioTabla("",0,"100%","","center"); 
       ITRTD("",0,"","40","center","top"); 
         IFrame("iFiltro72","95%","34","Filtros.js"); 
       FTDTR(); 
       ITRTD("",0,"","175","center","top"); 
         IFrame("iListado72","95%","170","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"75%","","","",1); 
           ITRTD("ETablaST",5,"","","center"); 
             TextoSimple(cTitulo); 
           FTDTR(); 
           ITR(); 
              TDEtiCampo(true,"EEtiqueta",0,"Clave Sistema:","iCveSistema","",3,3,"Clave Sistema","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0,"Consecitivo:","iCveModulo","",3,3,"Consecitivo","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0,"Descripci�n M�dulo:","cDscModulo","",100,100,"Descripci�n M�dulo","fMayus(this);"); 
           FITR(); 
              TDEtiCheckBox("EEtiqueta",0,"Activo:","lActivoBOX","1",true,"Activo"); 
              Hidden("lActivo","");
           FTR(); 
         FinTabla(); 
       FTDTR(); 
       FinTabla(); 
     FTDTR(); 
       ITRTD("",0,"","40","center","bottom"); 
         IFrame("iPanel72","95%","34","Paneles.js"); 
       FTDTR(); 
     FinTabla(); 
   fFinPagina(); 
 } 
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("iPanel72"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("iListado72"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo("Clave Sistema,Consecutivo, Descripci�n M�dulo,Activo,");
   FRMListado.fSetAlinea("center,center,left,center,");   
   FRMListado.fSetDespliega("texto,texto,texto,logico,");
    
   FRMFiltro = fBuscaFrame("iFiltro72"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra("Clave Sistema,iCveSistema,Consecutivo,iCveModulo,"); 
   FRMFiltro.fSetOrdena("Clave Sistema,iCveSistema,Consecutivo,iCveModulo,"); 
   fDisabled(true); 
   frm.hdBoton.value="Primero"; 
   fNavega(); 
 } 
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){ 
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
   frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
   return fEngSubmite("pgGRLModulo.jsp","Listado"); 
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
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fBlanked(); 
    fDisabled(false,"iCveSistema,iCveModulo,","--"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){
 frm.lActivoBOX.checked==true?frm.lActivo.value=1:frm.lActivo.value=0; 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
          FRMPanel.fSetTraStatus("UpdateComplete"); 
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
 function fGuardarA(){
  frm.lActivoBOX.checked==true?frm.lActivo.value=1:frm.lActivo.value=0; 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
         FRMPanel.fSetTraStatus("UpdateComplete"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
 function fModificar(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false,"iCveSistema,"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
 function fCancelar(){ 
    FRMFiltro.fSetNavStatus("ReposRecord"); 
    if(FRMListado.fGetLength() > 0) 
      FRMPanel.fSetTraStatus("UpdateComplete"); 
    else 
      FRMPanel.fSetTraStatus("AddOnly"); 
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
    frm.iCveSistema.value = aDato[0]; 
    frm.iCveModulo.value = aDato[1]; 
    frm.cDscModulo.value = aDato[2]; 
    frm.lActivo.value = aDato[3];
    if(frm.lActivo.value == 1)
          frm.lActivoBOX.checked=true;
     else 
          frm.lActivoBOX.checked=false;  
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
   function fGetiCveSistema(){
  return  frm.iCveSistema.value;
 }
 function fGetiCveModulo(){
  return  frm.iCveModulo.value;
 } 


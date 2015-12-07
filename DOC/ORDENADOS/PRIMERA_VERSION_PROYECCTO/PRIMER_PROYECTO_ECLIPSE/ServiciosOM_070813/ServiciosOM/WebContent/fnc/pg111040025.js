// MetaCD=1.0
 // Title: pg111040025.js
 // Description: JS "Cat�logo" de la entidad TRATiempoTraslado
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: aHernandez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040025.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
      ITRTD("",0,"","1","center");
        InicioTabla("",0,"75%","","","",1);

          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Tr�mite:","cDscTramite","",100,100,"cDscTramite","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Modalidad:","cDscModalidad","",50,50,"cDscModalidad","fMayus(this);");

           FTR();
        FinTabla();
      FTDTR();
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro25","95%","34","Filtros.js");
       FTDTR();
     FinTabla();
    FTDTR();


    ITRTD("",0,"95%","0","center","top");
      InicioTabla("",0,"100%","","center");
        ITRTD("ETablaST",0,"49%","","center");
          TextoSimple("Oficina Or�gen");
        FITD("",0,"2%","","center");
        FITD("ETablaST",0,"49%","","center");
          TextoSimple("Oficina Destino");
        FTDTR();

        ITRTD("",0,"49%","","center","middle");
          IFrame("IListado25A","100%","130","Listado.js","yes",true);
        FITD("",0,"2%","","center","middle");
        FITD("",0,"49%","","center","middle");
          IFrame("IListado25B","100%","130","Listado.js","yes",true);
        FTDTR();
      FinTabla();
    FTDTR();

       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel25","95%","34","Paneles.js");
       FTDTR();
        Hidden("iCveTramite");
       Hidden("iCveModalidad");
       Hidden("iCveOficinaOrigen","");
       Hidden("dtIniVigencia","");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel25");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado25A");
   FRMListado.fSetSelReg(1);
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Tr�mite,Modalidad,Oficina Origen,");
   FRMListado.fSetCampos("7,8,9,");
   FRMListado2 = fBuscaFrame("IListado25B");
   FRMListado2.fSetControl(self);
   FRMListado2.fSetTitulo("Oficina Destino,Inicio Vigencia,N�m. d�as Traslado,D�as Naturales,");
   FRMListado2.fSetCampos("9,4,5,6,");
   FRMListado2.fSetAlinea("left,center,center,center,");
   FRMListado2.fSetSelReg(2);
   FRMFiltro = fBuscaFrame("IFiltro25");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow(",");
   FRMFiltro.fSetFiltra("Tr�mite,iCveTramite,Modalidad,iCveModalidad,");
   FRMFiltro.fSetOrdena("Tr�mite,iCveTramite,Modalidad,iCveModalidad,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   //fNavega();
 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   frm.hdFiltro.value =  " TT.iCveTramite= "+ frm.iCveTramite.value + " and TT.iCveModalidad =  "+ frm.iCveModalidad.value ;
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   fEngSubmite("pgTRATiempoTraslado.jsp","Listado");
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
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

    if(cId == "Listado2" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado2.fSetListado(aRes);
     FRMListado2.fShow();
     FRMListado2.fSetLlave(cLlave);
     FRMFiltro.fSetNavStatus(cNavStatus);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveTramite,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){
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
    fDisabled(false,"iCveTramite,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
 function fCancelar(){

 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un rengl�n
 function fSelReg(aDato){
   if(aDato[2] > 0){
      frm.iCveOficinaOrigen.value =aDato[2];
   }else
      frm.iCveOficinaOrigen.value =-1;
  fOficinaDestino( frm.iCveOficinaOrigen.value);
 }
 function fSelReg2(aDato){

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

 function fOficinaDestino(OfDest){
   var IniVigencia=fGetDateSQL(frm.dtIniVigencia.value);
   //frm.hdFiltro.value = "";
   //if(OfDest >=0){
     frm.hdFiltro.value =  " TT.iCveTramite= "+ frm.iCveTramite.value + " and TT.iCveModalidad = "+ frm.iCveModalidad.value + " and  iCveOficinaOrigen= "+OfDest; //+" and dtIniVigencia= '"+ IniVigencia+"'";
     frm.hdOrden.value =  FRMFiltro.fGetOrden();
     frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
     fEngSubmite("pgTRATiempoTraslado.jsp","Listado2");
  //}
 }


 function fSetTpoTraslado(Tramite,Modalidad/*,Vigencia*/,DscTram,DscMod){
     frm.iCveTramite.value=Tramite;
     frm.iCveModalidad.value=Modalidad;
    // frm.dtIniVigencia.value=Vigencia;
     frm.cDscTramite.value=DscTram;
     frm.cDscModalidad.value=DscMod;
    // frm.hdBoton.value="Primero";
     fNavega();

     }

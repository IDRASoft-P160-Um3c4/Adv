// MetaCD=1.0
 // Title: pg111040024.js
 // Description: JS "Catálogo" de la entidad GRLCausaPNC
 // Company: Tecnología InRed S.A. de C.V.
 // Author: AHernandez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040024.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"95%","","center");
        ITRTD("",0,"","0","center","top");
          IFrame("IFiltro24","0","0","Filtros.js");
        FTDTR();
      FinTabla();
       ITRTD("",0,"","1","center");
        InicioTabla("",0,"75%","","","",1);

          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Trámite:","cDscTramite","",100,100,"cDscTramite","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Modalidad:","cDscModalidad","",50,50,"cDscModalidad","fMayus(this);");

           FTR();
        FinTabla();
      FTDTR();
      ITRTD("",0,"","4","center","top");
      FTDTR();
       InicioTabla("",0,"95%","30","center","",1);
       ITRTD("ETablaST",5,"","","center");
             TextoSimple("Etapas");
           FTDTR();
        ITRTD("",0,"100%","100","center","top");
         IFrame("IListado24","100%","105","Listado.js","yes",true);
       FTDTR();
     FinTabla();

      ITRTD("",0,"","4","center","top");
     FTDTR();
         InicioTabla("",0,"95%","30","center","",1);
          ITRTD("ETablaST",5,"","","center");
             TextoSimple("Dependencias");
           FTDTR();
           ITRTD("",0,"100%","100","center","top");
            IFrame("IListado24B","100%","105","Listado.js","yes",true);
           FTDTR();
         FinTabla();



     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel24","95%","34","Paneles.js");
       FTDTR();
       Hidden("iCveTramite");
       Hidden("iCveModalidad");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel24");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado24");
   FRMListado.fSetSelReg(1);
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Etapa,Obligatorio,Vigente,");
   FRMListado.fSetCampos("5,3,4,");
   FRMListado.fSetDespliega("texto,logico,logico,");
   FRMListado.fSetAlinea("left,center,center,");
   FRMListado2 = fBuscaFrame("IListado24B");
   FRMListado2.fSetSelReg(2);
   FRMListado2.fSetControl(self);
   FRMListado2.fSetTitulo("Trámite,Modalidad,Trámite Dependiente,Modalidad Dependiente,");
   FRMListado2.fSetCampos("4,5,6,7,");

   FRMFiltro = fBuscaFrame("IFiltro24");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Etapa,iCveEtapa,Obligatorio,lObligatorio,Vigente,lVigente,");
   FRMFiltro.fSetOrdena("Etapa,iCveEtapa,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
//   fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  "iCveTramite= "+frm.iCveTramite.value+" and iCveModalidad = "+frm.iCveModalidad.value;
   frm.hdOrden.value =  " iOrden";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   fEngSubmite("pgTRAEtapaXModTramA1.jsp","Listado");

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
     //frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
    // fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);

     frm.hdFiltro.value =  " D.iCveTramite= "+frm.iCveTramite.value+" and D.iCveModalidad = "+frm.iCveModalidad.value;
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
     fEngSubmite("pgTRADependencia.jsp","Listado2");

   }

    if(cId == "Listado2" && cError==""){
     //frm.hdRowPag.value = iRowPag;
     FRMListado2.fSetListado(aRes);
     FRMListado2.fShow();
     FRMListado2.fSetLlave(cLlave);
    // fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
  }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveProducto,","--");
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
    fDisabled(false,"iCveProducto,");
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
   /* frm.iCveProducto.value = aDato[0];
    frm.iCveCausaPNC.value = aDato[1];
    frm.cDscCausaPNC.value = aDato[2];
    frm.lVigente.value = aDato[3];
    frm.lRequisito.value = aDato[4]; */
 }

  // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg2(aDato){

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

 function fSetEtapas(Tramite,Modalidad,DscTram,DscMod){
     frm.iCveTramite.value=Tramite;
     frm.iCveModalidad.value=Modalidad;
     frm.cDscTramite.value=DscTram;
     frm.cDscModalidad.value=DscMod;
     fNavega();

     }


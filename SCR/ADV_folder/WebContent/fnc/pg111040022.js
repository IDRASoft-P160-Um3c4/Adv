// MetaCD=1.0
 // Title: pg111040022.js
 // Description: JS "Catálogo" de la entidad GRLCausaPNC
 // Company: Tecnología InRed S.A. de C.V.
 // Author: AHernandez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040022.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("EEtiquetaC",0,"80%","0","center");
      InicioTabla("",0,"95%","","center");
        ITRTD("",0,"","0","center","top");
          IFrame("IFiltro22","0","0","Filtros.js");
        FTDTR();
      FinTabla();
    FTDTR();

  ITRTD("",0,"","1","center");
        InicioTabla("",0,"75%","","","",1);

          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Trámite:","cDscTramite","",100,100,"cDscTramite","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Modalidad:","cDscModalidad","",50,50,"cDscModalidad","fMayus(this);");

           FTR();
        FinTabla();
      FTDTR();


     InicioTabla("",0,"95%","","center","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Requisitos");
           FTDTR();
            ITRTD("",0,"100%","110","center","top");
         IFrame("IListado22A","100%","190","Listado.js","yes",true);
       FTDTR();
     FinTabla();
     ITRTD("",0,"","4","center","top");
     FTDTR();
         InicioTabla("",0,"95%","30","center","",1);
          ITRTD("ETablaST",5,"","","center");
             TextoSimple("Características");
           FTDTR();
           ITRTD("",0,"100%","100","center","top");
            IFrame("IListado22B","100%","120","Listado.js","yes",true);
           FTDTR();
         FinTabla();


     ITRTD("",0,"95%","0","center","top");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"49%","40","center","middle");
          InicioTabla("ETablaInfo",0,"100%","","","",1);

        FITD("",0,"49%","","center","middle");
          IFrame("IPanel22","100%","34","Paneles.js");
        FTDTR();
      FinTabla();
    FTDTR();


         Hidden("iCveRequisito","");
         Hidden("iCveTramite");
         Hidden("iCveModalidad");


  FinTabla();
  fFinPagina();
}

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel22");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado22A");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Descripción Breve,Requisto,Fund. Legal,lDigitaliza,lRequerido,");
   FRMListado.fSetCampos("6,5,7,8,4,");
   FRMListado.fSetDespliega("texto,texto,texto,logico,logico,");
   FRMListado.fSetAlinea("left,left,left,center,center,");
   FRMListado.fSetSelReg(1);
   FRMListado2 = fBuscaFrame("IListado22B");
   FRMListado2.fSetControl(self);
   FRMListado2.fSetSelReg(2);
   FRMListado2.fSetTitulo("Característica,Descripción Breve,En Recepcion,En Proceso,Mandatorio,");
   FRMListado2.fSetCampos("2,3,5,6,7,");
   FRMListado2.fSetDespliega("texto,texto,logico,logico,logico,");
   FRMListado2.fSetAlinea("left,left,center,center,center,");

   FRMFiltro = fBuscaFrame("IFiltro22");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Producto,iCveProducto,Clave,iCveCausaPNC,");
   FRMFiltro.fSetOrdena("Producto,iCveProducto,Clave,iCveCausaPNC,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   //fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =" iCveTramite= "+frm.iCveTramite.value + " and iCveModalidad= "+frm.iCveModalidad.value;
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  10000;
   fEngSubmite("pgTRAReqXModTramiteA2.jsp","Listado");
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
    // fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

    if(cId == "Listado2" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado2.fSetListado(aRes);
     FRMListado2.fShow();
     FRMListado2.fSetLlave(cLlave);
     //fCancelar();
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
    frm.iCveRequisito.value = aDato[2];
    if(aDato[2]!="")
    fOnChangeRequisito(aDato[2]);
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

 function fOnChangeRequisito(var1){
   frm.hdFiltro.value =" TRARequisito.iCveRequisito= " + var1;
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  "";
   fEngSubmite("pgTRACaracxRequisitoB.jsp","Listado2");

   }

   function fSetDatos(Tramite,Modalidad,DscTram,DscMod){

     frm.iCveTramite.value=Tramite;
     frm.iCveModalidad.value=Modalidad;
     frm.cDscTramite.value=DscTram;
     frm.cDscModalidad.value=DscMod;
     fNavega();

     }


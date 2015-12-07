// MetaCD=1.0
 // Title: pg110000052.js
 // Description: JS "Catálogo" de la entidad GRLDigitosFolioXDepto
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ABarrientos
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110000052.js";
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
         IFrame("IFiltro52","95%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado52","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","fTraeAreas();");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDepartamento","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Dígitos que le corresponden:","cDigitosFolio","",20,20,"Dígitos que le corresponden","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Fecha de Asignación:","dtAsignacion","",10,10,"Fecha de Asignación","fMayus(this);");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel52","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel52");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMPanel.fSetTraStatus("Add,");
   FRMListado = fBuscaFrame("IListado52");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Digitos Folio,Asignación,Oficina,Departamento,");
   FRMListado.fSetCampos("0,1,4,6,");
   FRMListado.fSetCol( 1, "center")
   FRMFiltro = fBuscaFrame("IFiltro52");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Digitos Folio,cDigitosFolio,Asignación,dtAsignacion,");
   FRMFiltro.fSetOrdena("Digitos Folio,cDigitosFolio,Asignación,dtAsignacion,");
   fDisabled(true);
   frm.hdBoton.value="Primero";

   //fTraeOficinas();

 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgGRLDigitosFolioXDepto.jsp","Listado");
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

   if(cId == "IDOficina" && cError == ""){
    fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value);
   fNavega();
   }
   if(cId == "IDDepto" && cError == ""){
     iIdDepto = frm.iCveDepartamento.value;
     fFillSelect(frm.iCveDepartamento,aRes,true,frm.iCveDepartamento.value,1,6);
     fSelectSetIndexFromValue(frm.iCveDepartamento, iIdDepto);
   }


 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    fSelectSetIndexFromValue(frm.iCveOficina, -1);
    fAsignaSelect(frm.iCveDepartamento,0,"Seleccione...");

    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"dtAsignacion,");
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
    fTraeAreas();
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"dtAsignacion,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("AddOnly");
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
    frm.cDigitosFolio.value = aDato[0];
    frm.dtAsignacion.value = aDato[1];
    //frm.iCveOficina.value = aDato[2];

    if (parseInt(aDato[2]) > 0)
      fSelectSetIndexFromValue(frm.iCveOficina, aDato[2]);
    else
      fSelectSetIndexFromValue(frm.iCveOficina, 0);

    //frm.iCveDepartamento.value = aDato[3];
    if ( aDato[3] != "" )
      fAsignaSelect(frm.iCveDepartamento,aDato[3],aDato[6]);
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


 function fTraeOficinas(){
   frm.hdOrden.value  = "";
   frm.hdFiltro.value = "";
   frm.hdNumReg.value = 10000;
   fEngSubmite("pgGRLOficina.jsp","IDOficina");

 }

 function fTraeAreas(){
   frm.hdNumReg.value = 10000;
   if(frm.iCveOficina.value != "" && parseInt(frm.iCveOficina.value, 10) > 0 ){
     frm.hdOrden.value =  " GRLDepartamento.cDscDepartamento ";
     frm.hdFiltro.value = " GRLDeptoXOfic.iCveOficina = "+frm.iCveOficina.value;
   }
   else{
     fAsignaSelect(frm.iCveDepartamento,0,"Seleccione...");
     return;
   }
   fEngSubmite("pgGRLDeptoXOfic.jsp","IDDepto");
 }


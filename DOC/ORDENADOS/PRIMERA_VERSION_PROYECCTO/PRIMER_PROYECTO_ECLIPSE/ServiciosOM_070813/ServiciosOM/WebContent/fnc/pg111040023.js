// MetaCD=1.0
 // Title: pg111040023.js
 // Description: JS "Cat�logo" de la entidad GRLCausaPNC
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: AHernandez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040023.js";
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
         IFrame("IFiltro","95%","34","Filtros.js");
       FTDTR();


       ITRTD("",0,"","1","center");
        InicioTabla("",0,"75%","","","",1);

          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Tr�mite:","cDscTramite","",100,100,"cDscTramite","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Modalidad:","cDscModalidad","",50,50,"cDscModalidad","fMayus(this);");

           FTR();
        FinTabla();
      FTDTR();


       ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","195","Listado.js","yes",true);
       FTDTR();
     FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
       Hidden("iCveTramite");
       Hidden("iCveModalidad");
       Hidden("iEjercicio");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Concepto,Categor�a Ingresos,Concepto Ingresos, Referencia, Es Tarifa, Es Factor,Importe sin Ajuste,Importe Ajustado,Pago Anticipado,");
   FRMListado.fSetCampos("5,6,7,8,9,10,11,12,3,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,logico,logico,texto,texto,logico,");
   FRMListado.fSetAlinea("left,center,center,center,center,center,center,center,center,");
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Fil,");
   aFiltra=new Array();
   aFiltra[0]=["=","="];
   FRMFiltro.fEspecial(aFiltra);
   FRMFiltro.fSetFiltra("Ejercicio,iEjercicio,");
   FRMFiltro.fSetOrdena("Ejercicio,iEjercicio,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   FRMFiltro.frm.cFiltro.value=2006;
  // fNavega();
 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   frm.hdFiltro.value = " TRAConceptoXTramMod.iEjercicio="+FRMFiltro.frm.cFiltro.value+" and TRAConceptoXTramMod.iCveTramite= "+frm.iCveTramite.value+ " and TRAConceptoXTramMod.iCveModalidad="+frm.iCveModalidad.value;
   //+ FRMFiltro.fGetFiltro()+" and TRAConceptoXTramMod.iCveTramite= "+frm.iCveTramite.value+ " and TRAConceptoXTramMod.iCveModalidad="+frm.iCveModalidad.value;
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  "100000";
   fEngSubmite("pgTRAConceptoxTraModA.jsp","Listado");
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
    // fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveProducto,","--");
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
    fDisabled(false,"iCveProducto,");
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
    frm.iEjercicio.value = aDato[0];

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

 function fSetPagos(Tramite,Modalidad,DscTram,DscMod){
     frm.iCveTramite.value=Tramite;
     frm.iCveModalidad.value=Modalidad;
     frm.cDscTramite.value=DscTram;
     frm.cDscModalidad.value=DscMod;
    // FRMFiltro.frm.cFiltro.value=2006;

     }
function fGetEjercicio(){
       return  FRMFiltro.frm.cFiltro.value;
       }
function fSetEjer(){
  FRMFiltro.frm.cFiltro.value=2006;
  //alert("filtro"+FRMFiltro.frm.cFiltro.value);
  }

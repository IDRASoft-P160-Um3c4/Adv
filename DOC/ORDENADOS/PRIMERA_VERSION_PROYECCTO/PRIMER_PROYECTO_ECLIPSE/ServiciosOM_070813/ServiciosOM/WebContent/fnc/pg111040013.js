// MetaCD=1.0
 // Title: pg111040013.js
 // Description: JS "Catálogo" de la entidad TRARegReqXTram
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040013.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         InicioTabla("ETablaInfo",0,"65%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           FTDTR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",4,4,"Año","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumeroSolicitud","",4,4,"Numero de Solicitud","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cDscTramite","",100,100,"Descripción del Trámite","fMayus(this);","","","","EEtiquetaL", "4");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cDscModalidad","",70,70,"Descripción de la Modalidad","fMayus(this);");
           FTR();
         FinTabla();
         IFrame("IFiltro13","0%","34","Filtros.js");
         FTDTR();
         FITR();

         ITRTD("",0,"95%","","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Requisitos");
           FTDTR();
           ITRTD("",0,"","","center");
             IFrame("IListado13","100%","170","Listado.js","yes",true);
           FTDTR();
         FinTabla();
         FTDTR();

         FITR();
         FITR();
         ITRTD("",0,"95%","","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Pagos");
           FTDTR();
           ITRTD("",0,"","","center");
             IFrame("IListado13A","100%","170","Listado.js","yes",true);
           FTDTR();
         FinTabla();


       FinTabla();
       FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel13","95%","34","Paneles.js");
       FTDTR();
       Hidden("hdLlave");
       Hidden("hdSelect");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel13");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow(",");
   FRMListado = fBuscaFrame("IListado13");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Requisito,Recepción,Usuario,Válido,");
   FRMListado.fSetCampos("11,5,12,10,");
   FRMListado.fSetAlinea("left,center,center,center,left,center,center,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,texto,texto,logico");
   FRMListadoA = fBuscaFrame("IListado13A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Referencia Numérica, Unidades, Referencia Alfanumérica, Importe a Pagar, Importe Pagado, Fecha de Pago, Banco, Formato SAT, Pagado,");
   FRMListadoA.fSetAlinea("left,left,left,right,right,center,left,left,center,");
   FRMListadoA.fSetDespliega("texto,texto,texto,texto,texto,texto,texto,texto,logico");
   FRMFiltro = fBuscaFrame("IFiltro13");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
   FRMFiltro.fSetFiltra("Núm. Solicitud,iNumSolicitud,Ejercicio,iEjercicio,");
   FRMFiltro.fSetOrdena("Núm. Solicitud,iNumSolicitud,Ejercicio,iEjercicio,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  "TRAREGREQXTRAM.IEJERCICIO = "+frm.iEjercicio.value+"  And TRAREGREQXTRAM.INUMSOLICITUD="+frm.iNumeroSolicitud.value;
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  50;
 fEngSubmite("pgTRARegReqXTramA.jsp","Listado");


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
     FRMFiltro.fSetNavStatus(cNavStatus);
     fEngSubmite("pgConsulta.jsp","ListadoA");
   }
   if(cId == "ListadoA" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
     FRMFiltro.fSetNavStatus(cNavStatus);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
 }

 function fImprimir(){
    self.focus();
    window.print();
 }

 function fGetListadoA(){
    frm.hdLlave.value = " iEjercicio,iNumSolicitud,iConsecutivo ";
    frm.hdSelect.value = " select IREFNUMERICA,INUMUNIDADES,CREFALFANUM,DIMPORTEPAGAR,DIMPORTEPAGADO,DTPAGO,GRLBANCO.CDSCBANCO,CFORMATOSAT,LPAGADO "+
                         " from TRAREGREFPAGO "+
                         " join GRLBANCO ON GRLBANCO.ICVEBANCO = TRAREGREFPAGO.ICVEBANCO "+
                         " where iEjercicio="+frm.iEjercicio.value+"  AND iNumSolicitud="+frm.iNumeroSolicitud.value;
    frm.hdNumReg.value =  50;
    fNavega();
 }

function fSetSolicitud(iEjercicio,iNumSolicitud, cTramite, cModalidad){
   frm.iEjercicio.value = iEjercicio;
   frm.iNumeroSolicitud.value = iNumSolicitud;
   frm.cDscTramite.value = cTramite;
   frm.cDscModalidad.value = cModalidad;
   fGetListadoA();
 }

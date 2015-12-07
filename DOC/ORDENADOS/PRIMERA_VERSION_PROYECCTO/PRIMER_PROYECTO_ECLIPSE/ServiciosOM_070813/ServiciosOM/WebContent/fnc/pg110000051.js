// MetaCD=1.0
 // Title: pg110000051.js
 // Description: JS "Catálogo" de la entidad GRLFolio
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ABarrientos
 var cTitulo = "";
 var FRMListado = "";
 var frm;


 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110000051.js";
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
         IFrame("IFiltro","95%","34","Filtros.js");
         InicioTabla("",0,"","","","",1);
           FTDTR();
           ITR();
              TDEtiSelect(false,"EEtiqueta",0,"Oficina:","iCveOficina","fTraeAreas();");
              TDEtiSelect(false,"EEtiqueta",0,"Departamento:","iCveDepartamento","fTraeDigitosFolio();","EEtiquetaL",7);
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",4,4,"Ejercicio","");
              TDEtiCampo(false,"EEtiqueta",0,"Asignación:","dtAsignacion","",10,10,"Fecha de Asignación del folio","");
           ITD();BtnImg("Buscar","lupa","fNavega();");
           FITR();
          FinTabla();
       FTDTR();
       ITRTD("",0,"","105","center","top");
         IFrame("IListado","95%","100","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"85%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Dígitos Folio:","cDigitosFolio","",20,20,"Dígitos Folio","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Consecutivo:","iConsecutivo","",3,3,"Consecutivo","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Dirigido A:","cDirigidoA","",50,100,"Dirigido A","fMayus(this);","","",false,"ECampo",3);
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Asunto:",50,2,"cAsunto","","Asunto","","fMayus(this);",'onkeydown="fMxTx(this,200);"',false,true,true,"ECampo",3);

           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Titular Firma:","cTitularFirma","",50,100,"Titular Firma","fMayus(this);","","",false,"ECampo",3);
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Fecha de Envío:","dtEnvio","",10,10,"Fecha de Envío","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Fecha de Recepción:","dtRecepcion","",10,10,"Fecha de Recepción","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Núm. de Oficialía de Partes:","cNumOficialiaPartes","",15,15,"Número de Oficialia de Partes","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Núm. de Control de Gestión:","cNumControlGestion","",15,15,"Número de Control de Gestión","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Fecha de Cancelación:","dtCancelacion","",10,10,"Fecha de Cancelación","");
           FITR();
              TDEtiAreaTexto(false,"EEtiqueta",0,"Motivo Cancelación:",50,2,"cMotivoCancela","","Motivo de Cancelación","","fMayus(this);",'onkeydown="fMxTx(this,200);"',false,true,true,"ECampo",3);

           FTR();
         FinTabla();
         if (top.fGetUsrID()){
           Hidden("iCveUsuario",top.fGetUsrID());
           Hidden("iCveUsuAsigna",top.fGetUsrID());
         }
         else{
           Hidden("iCveUsuario",1);
           Hidden("iCveUsuAsigna",1);
         }

         Hidden("iCveUsuCancela","");
         Hidden("fechaActual","");
         Hidden("hdFiltroUsrXDepto");

         Hidden("hdFiltroLlave","");
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();

     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];

   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");

   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Ejercicio,Dígitos Folio,Consecutivo,Asignación,Dirigido A,Asunto,Titular Firma,");
   FRMListado.fSetCampos("0,1,2,3,5,6,7,");
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Nav,Reg,");
   //FRMFiltro.fSetFiltra("Ejercicio,GRLFolio.iEjercicio,Consecutivo,GRLFolio.iConsecutivo,Fecha de Asignación,GRLFolio.dtAsignacion,");
   //FRMFiltro.fSetOrdena("Ejercicio,GRLFolio.iEjercicio,Consecutivo,GRLFolio.iConsecutivo,Fecha de Asignación,GRLFolio.dtAsignacion,");
   fDisabled(true,"iEjercicio,dtAsignacion,iCveOficina,iCveDepartamento,");
   frm.hdBoton.value="Primero";

   fTraeOficinas();

   //fNavega(); se ejecuta en fResultado en cuando id es IDUsrXOfic
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "1=1 "
   if(frm.dtAsignacion.value!=""){
     var cFecha = ""+frm.dtAsignacion.value;
     frm.hdFiltro.value += " ANd dtAsignacion = '"+cFecha.substring(6,10)+"-"+cFecha.substring(3,5)+"-"+cFecha.substring(0,2) +"'";
   }
   if(frm.iEjercicio.value!="") frm.hdFiltro.value += " AND iEjercicio = "+frm.iEjercicio.value;
   if(frm.iCveDepartamento.value> 0) frm.hdFiltro.value += " AND GRLFolio.iCveDepartamento = "+frm.iCveDepartamento.value+" And GRLFolio.iCveOficina = "+frm.iCveOficina.value;
   if (frm.hdFiltroUsrXDepto.value !="")
     frm.hdFiltro.value += " AND (" + frm.hdFiltroUsrXDepto.value +")";
  frm.hdFiltroUsrXDepto.value = "";
   frm.hdOrden.value =  "GRLFolio.iConsecutivo";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   fEngSubmite("pgGRLFolio.jsp","Listado");
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
    fFillSelect(frm.iCveOficina,aRes,false,frm.iCveOficina.value);
    fTraeAreas();
   }
   if(cId == "IDDepto" && cError == ""){
     fFillSelect(frm.iCveDepartamento,aRes,false,frm.iCveDepartamento.value,1,2);
     fTraeFechaActual();
   }

   if(cId == "IDUsrXOfic" && cError == ""){
     frm.hdFiltroUsrXDepto.value = "";
     for(y=0; y < aRes.length ; y++){
       frm.hdFiltroUsrXDepto.value += " (GRLFolio.iCveOficina = " + aRes[y][0] +
                            " AND GRLFolio.iCveDepartamento = " + aRes[y][1] + ") ";

       if (y < (aRes.length - 1) )
         frm.hdFiltroUsrXDepto.value += " OR ";
     }
   }

   if(cId == "IDDigitos" && cError == ""){
     //alert("digito.."+aRes[0][0]);
     if (aRes[0]){
         frm.cDigitosFolio.value = aRes[0][0];
         frm.cTitularFirma.value = aRes[0][8];
     }
     else{
         fAlert("El Departamento que seleccionó no tiene asignado el prefijo del folio");
         frm.cDigitosFolio.value = "";
         return false;
     }
   }

   if(cId == "idFechaActual" && cError==""){
      frm.fechaActual.value = aRes[0,0];
      fTraeUsrXDeptos();
   }


 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    frm.dtAsignacion.value = frm.fechaActual.value;
    var cFecha = "" + frm.fechaActual.value;
    frm.iEjercicio.value = cFecha.substring(6,10);
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iCveUsuario,fechaActual,dtAsignacion,iCveUsuAsigna,hdFiltroUsrXDepto,iEjercicio,cDigitosFolio,");
    fDisabled(false,"iEjercicio,dtAsignacion,cDigitosFolio,iConsecutivo,iEjercicio,dtAsignacion,iCveOficina,iCveDepartamento,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    if (frm.cDigitosFolio.value==""){
      fAlert("Debe de seleccionar un departamento que tenga digitos de folio");
      return false;
    }


    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true,"iEjercicio,dtAsignacion,iCveOficina,iCveDepartamento,");
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    if ( frm.dtCancelacion.value != "" )
       frm.iCveUsuCancela.value =  frm.iCveUsuario.value;


    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true,"iEjercicio,dtAsignacion,iCveOficina,iCveDepartamento,");
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicio,cDigitosFolio,iConsecutivo,iCveOficina,iCveDepartamento,dtAsignacion,iEjercicio,dtAsignacion,iCveOficina,iCveDepartamento,");
    if ( frm.dtEnvio.value != "" )
       frm.dtEnvio.disabled = true;
    if ( frm.dtRecepcion.value != "" )
       frm.dtRecepcion.disabled = true;
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");
    fDisabled(false);
    fDisabled(true,"iEjercicio,dtAsignacion,iCveOficina,iCveDepartamento,");
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
    //frm.iEjercicio.value = aDato[0];
    frm.cDigitosFolio.value = aDato[1];
    frm.iConsecutivo.value = aDato[2];
    //frm.dtAsignacion.value = aDato[3];

    if ( aDato[4] != "" )
      frm.iCveUsuAsigna.value = aDato[4];


    frm.cDirigidoA.value = aDato[5];
    frm.cAsunto.value = aDato[6];
    frm.cTitularFirma.value = aDato[7];
    frm.dtEnvio.value = aDato[8];
    frm.dtRecepcion.value = aDato[9];



    frm.cNumOficialiaPartes.value = aDato[10];
    frm.cNumControlGestion.value = aDato[11];
    frm.dtCancelacion.value = aDato[12];

    if ( frm.dtCancelacion.value != "" )
      FRMPanel.fSetTraStatus("Add,");
    else
      FRMPanel.fSetTraStatus("Add,Mod,");

    if ( frm.dtEnvio.value != "" &&  frm.dtRecepcion.value != "")
      FRMPanel.fSetTraStatus("Add,");
    frm.cMotivoCancela.value = aDato[14];
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
   frm.hdBoton.value = "Oficinas";
   frm.hdOrden.value  = "";
   frm.hdFiltro.value = " GRLUSUARIOXOFIC.ICVEUSUARIO = " + frm.iCveUsuario.value;
   fEngSubmite("pgGRLUsuarioXOfice.jsp","IDOficina");

 }

 function fTraeAreas(){
   frm.hdBoton.value = "Deptos1";
   if(frm.iCveOficina.value != "" && parseInt(frm.iCveOficina.value, 10) > 0 ){
     frm.hdOrden.value =  " GRLDepartamento.cDscDepartamento ";
     frm.hdFiltro.value = " GRLUSUARIOXOFIC.ICVEOFICINA = "+frm.iCveOficina.value +
                          " AND GRLUSUARIOXOFIC.ICVEUSUARIO = "+frm.iCveUsuario.value;
   fEngSubmite("pgGRLUsuarioXOfice.jsp","IDDepto");
   }
   else{
     fAsignaSelect(frm.iCveDepartamento,0,"Seleccione...");
   }
 }

 function fTraeUsrXDeptos(){
   frm.hdBoton.value = "Deptos";
   frm.hdFiltro.value = " GRLUSUARIOXOFIC.ICVEUSUARIO = "+frm.iCveUsuario.value;
   fEngSubmite("pgGRLUsuarioXOfice.jsp","IDUsrXOfic");
 }


 function fTraeDigitosFolio(){
   if(frm.iCveOficina.value != "" && parseInt(frm.iCveOficina.value, 10) > 0 ){
     if ( frm.iCveDepartamento.value != "" && parseInt(frm.iCveDepartamento.value,10) > 0 ){
       frm.hdOrden.value =  " GRLDigitosFolioXDepto.dtAsignacion DESC ";
       frm.hdFiltro.value = " GRLDigitosFolioXDepto.iCveOficina = " +frm.iCveOficina.value +
                            " AND GRLDigitosFolioXDepto.iCveDepartamento = " + frm.iCveDepartamento.value;
       fEngSubmite("pgGRLDigitosFolioXDepto.jsp","IDDigitos");
     }
     else
       fAlert("Seleccione un departamento");
   }
   else
     fAlert("Seleccione una oficina");

 }

 function fseleccionaTitular(indice){
   return aTitular[indice];
 }



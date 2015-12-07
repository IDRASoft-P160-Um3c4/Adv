// MetaCD=1.0
 // Title: pg111040021.js
 // Description: JS "Catálogo" de la entidad TRACatTramite
 // Company: Tecnología InRed S.A. de C.V.
 // Author: AHernandez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 //var aResTramiteOriginal= new Array();
 /*var aResCompleto, aResTramite = new Array(), aResRequisito = new Array(),
    aResReqBase = new Array(), aResOficUsr = new Array(), aResTramiteOriginal = new Array(),
    aResReqPNC = new Array();
aRegistros = new Array();*/
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS ="pg111040021.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    fRequisitoModalidad(true);

    ITRTD("EEtiquetaC",0,"80%","0","center");
      InicioTabla("",0,"95%","","center");
        ITRTD("",0,"","0","center","top");
          IFrame("IFiltro21","0","0","Filtros.js");
        FTDTR();
      FinTabla();
    FTDTR();

     InicioTabla("",0,"95%","","center","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Trámites");
           FTDTR();
            ITRTD("",0,"100%","95","center","top");
         IFrame("IListado21A","100%","95","Listado.js","yes",true);
       FTDTR();
     FinTabla();

     InicioTabla("",0,"95%","30","center","",1);
          ITRTD("ETablaST",5,"","","center");
             TextoSimple("Oficina");
           FTDTR();
           ITRTD("",0,"100%","100","center","top");
            IFrame("IListado21C","100%","105","Listado.js","yes",true);
           FTDTR();
         FinTabla();


     ITRTD("",0,"","4","center","top");
     FTDTR();
         InicioTabla("",0,"95%","30","center","",1);
          ITRTD("ETablaST",5,"","","center");
             TextoSimple("Modalidad");
           FTDTR();
           ITRTD("",0,"100%","100","center","top");
            IFrame("IListado21B","100%","105","Listado.js","yes",true);
           FTDTR();
         FinTabla();

    ITRTD("",0,"95%","0","center","top");
      InicioTabla("",0,"100%","","center");

    FTDTR();
    ITRTD("",0,"49%","40","center","bottom");
         IFrame("IPanel21A","100%","34","Paneles.js");
    FTDTR();

         Hidden("cCveInterna","");
         Hidden("cDscTramite","");
         Hidden("lReqFirmaDigital","");
         Hidden("lVigente","");
         Hidden("cBienBuscar","");
         Hidden("cDscBreve","");
         Hidden("dtIniVigencia","");
         Hidden("cDscModalidad","");
         Hidden("iCveDeptoResuelve","");
         Hidden("iCveOficinaResuelve","");
         Hidden("iEjercicio","");

  FinTabla();

  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel21A");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(true);
  FRMPanel.fShow(",");
  FRMLstTra = fBuscaFrame("IListado21A");
  FRMLstTra.fSetControl(self);
  FRMLstTra.fSetSelReg(1);
  FRMLstTra.fSetTitulo("Clave Interna,Descripción,Descripción Breve,Req. Firma Digital,Vigente,Bien,");
  FRMLstTra.fSetCampos("1,2,3,4,5,6,");
  FRMLstTra.fSetDespliega("texto,texto,texto,logico,logico,texto,");
  FRMLstTra.fSetAlinea("left,left,left,center,center,left,");
  FRMLstMod = fBuscaFrame("IListado21B");
  FRMLstMod.fSetControl(self);
  FRMLstMod.fSetSelReg(2);
  FRMLstMod.fSetTitulo("Modalidad,Fecha Vigencia,Días Resolución, Días Naturales Resolución,Días Cubrir Req.,Días Naturales Req.,Requiere Pago,COFEMER,");
  FRMLstMod.fSetCampos("9,2,3,4,5,6,7,10,");
  FRMLstMod.fSetDespliega("texto,texto,texto,logico,texto,logico,logico,texto,");
  FRMLstMod.fSetAlinea("left,center,center,center,center,center,center,left,");
  FRMLstOf = fBuscaFrame("IListado21C");
  FRMLstOf.fSetControl(self);
  FRMLstOf.fSetSelReg(3);
  FRMLstOf.fSetTitulo("Oficina,Oficina Resuelve,Departamento Resuelve,");
  FRMLstOf.fSetCampos("4,5,7,");
  FRMFiltro = fBuscaFrame("IFiltro21");
  FRMFiltro.fSetControl(self);
  fCargaTramites();
  FRMLstTra.fSetDisabled(false);
  FRMLstOf.fSetDisabled(false);
  FRMLstMod.fSetDisabled(false);
 // fDisabled(true, "cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,cDscBien,");

}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  //frm.hdFiltro.value = "TRAReqXModTramite.iCveTramite = " + frm.iCveTramite.value + " and TRAReqXModTramite.iCveModalidad = " + frm.iCveModalidad.value;
  FRMPanel.fSetTraStatus("UpdateBegin");
  frm.hdBoton.value = "";
  frm.hdFiltro.value ="";
  frm.hdFiltro.value = " TRACatTramite.iCveTramite= "+frm.iCveTramite.value;
  frm.hdOrden.value  = "TRACatTramite.iCveTramite";
  frm.hdNumReg.value =  10000000;
  FRMLstTra.fSetDisabled(true);
  FRMLstMod.fSetDisabled(true);
  FRMPanel.fDisabled(true);
  if(frm.iCveTramite.value >= 0)
    fEngSubmite("pgTRACatTramiteA1.jsp","Listado");
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
    // FRMFiltro.fSetNavStatus("Record");
   if(cId == "CIDTramite" && cError==""){
    aResTramiteOriginal = fCopiaArreglo(aRes);
  }
  fResTramiteModalidad(aRes,cId,cError,true);

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;

     FRMLstTra.fSetListado(aRes);
     FRMLstTra.fShow();
     FRMLstTra.fSetLlave(cLlave);
     frm.cDscBreve.value=aRes[0][3];
     FRMFiltro.fSetNavStatus(cNavStatus);
     if (frm.iCveTramite.value >= 0 && frm.iCveModalidad.value >= 0){
    frm.hdFiltro.value = " ct.iCveTramite= "+frm.iCveTramite.value+" and" + " tm.iCveModalidad= "+ frm.iCveModalidad.value;
    frm.hdOrden.value  = "ct.iCveTramite,ct.iCveModalidad";
    fEngSubmite("pgTRAConfiguraTram.jsp","Listado2");
  }
   }

    if(cId == "Listado2" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMLstMod.fSetListado(aRes);
     FRMLstMod.fShow();
     FRMLstMod.fSetLlave(cLlave);

     FRMFiltro.fSetNavStatus(cNavStatus);
	 frm.hdFiltro.value = " tc.iCveTramite= "+frm.iCveTramite.value;
    frm.hdOrden.value  = "tc.iCveTramite";
    fEngSubmite("pgTRATramiteXOfic.jsp","Listado3");
    }

    if(cId == "Listado3" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMLstOf.fSetListado(aRes);
     //frm.dtIniVigencia.value=aRes[0][2];
     FRMLstOf.fShow();
     FRMLstOf.fSetLlave(cLlave);
     FRMLstTra.fSetDisabled(false);
     FRMLstOf.fSetDisabled(false);
     FRMLstMod.fSetDisabled(false);
     FRMFiltro.fSetNavStatus(cNavStatus);

    }

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

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveGpoProducto,","--");
    FRMListado.fSetDisabled(true);
}


// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  if (frm.lActivoBOX.checked==true)frm.lActivo.value=1;
       else frm.lActivo.value=0;
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
if (frm.lActivoBOX.checked==true)frm.lActivo.value=1;
       else frm.lActivo.value=0;
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
    fDisabled(false,"iCveGpoProducto,");
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
   fDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
}

function fSelReg(aDato){
 //frm.iCveTramite.value = aDato[0];
    frm.cCveInterna.value = aDato[1];
    frm.cDscTramite.value = aDato[2];
   // frm.cDscBreve.value = aDato[3];
    frm.lReqFirmaDigital.value = aDato[4];
    frm.lVigente.value = aDato[5];
    frm.cBienBuscar.value = aDato[6];
    frm.cDscModalidad.value = aDato[9];

}

function fSelReg2(aDato){

    frm.dtIniVigencia.value = aDato[2];
    frm.cDscModalidad.value = aDato[9];

    /*frm.lVigente.value = aDato[5];
    frm.cBienBuscar.value = aDato[6];*/
}
function fSelReg3(aDato){

    frm.iCveOficinaResuelve.value = aDato[2];
    frm.iCveDeptoResuelve.value = aDato[3];

}


function fImprimir(){
  self.focus();
  window.print();
}



function fTramiteOnChangeLocal(){
  frm.cCveTramite.value="";

  if (frm.iCveTramite && frm.iCveTramite.value >= 0){
   // fNavega();
   /* frm.hdFiltro.value = " tc.iCveTramite= "+frm.iCveTramite.value;
    frm.hdOrden.value  = "tc.iCveTramite";
    fEngSubmite("pgTRATramiteXOfic.jsp","Listado3");*/
  }//else
//    FRMPanel.fSetTraStatus("Add,Can,");
}

function fModalidadOnChangeLocal() {
  fNavega();
  /*if (frm.iCveTramite.value >= 0 && frm.iCveModalidad.value >= 0){
    frm.hdFiltro.value = " ct.iCveTramite= "+frm.iCveTramite.value+" and" + " tm.iCveModalidad= "+ frm.iCveModalidad.value;
    frm.hdOrden.value  = "ct.iCveTramite,ct.iCveModalidad";
    fEngSubmite("pgTRAConfiguraTram.jsp","Listado2");
  }*////si

}

function fGetiCveTramite(){

  return frm.iCveTramite.value;
}


function fGetModalidad(){
   return frm.iCveModalidad.value;
}

function fGetDscTramite(){
    return frm.cDscBreve.value;
}
function fGetDscModalidad(){
    return frm.cDscModalidad.value;
}


/*function fGetdtVigencia(){
  return frm.dtIniVigencia.value;
}*/

function fSetEjercicio(Ejer){
    frm.iEjercicio.value=Ejer;
  }

function fReporte(){
  aCBoxTra = FRMLstTra.fGetObjs(0);
  var formato = null;
  cClavesModulo = "2,";
  cNumerosRep = "4,";
if( frm.iEjercicio.value=="")
  alert("\n Seleccione un Concepto de Pago");
else{
  cFiltrosRep = frm.iCveTramite.value+","+frm.iCveModalidad.value+","+frm.iCveOficinaResuelve.value+","+frm.iCveDeptoResuelve.value+","+  frm.iEjercicio.value+","+cSeparadorRep;
      // alert(cFiltrosRep);
  fReportes();
}
}

function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError){
  if (theWindow)
    theWindow.close();
}

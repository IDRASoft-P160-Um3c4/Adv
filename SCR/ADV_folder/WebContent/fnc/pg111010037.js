// MetaCD=1.0
 // Title: pg111010037.js
 // Description: JS "Catálogo" de la entidad TRADependencia
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010037.js";
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
       ITRTD("",0,"","0","center","top");
         IFrame("IFiltro37","0","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"90%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              //fRequisitoModalidad();
           FITR();
              fTramiteModalidad();
           FTR();
         FinTabla();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado37","95%","170","Listado.js","yes",true);
       FTDTR();

       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel37","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
  Hidden("iCveTramite");
  Hidden("iCveModalidad");
  Hidden("cDscTramiteHijo");
  Hidden("cDscModalidadHijo");
  Hidden("iCveTramiteHijo1");
  Hidden("iCveModalidadHijo1");
  Hidden("iTemiCveTramite");
  Hidden("flag1");
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel37");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado37");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Trámite,Modalidad,Trámite Dependiente,Modalidad Dependiente,");
   FRMListado.fSetCampos("4,5,6,7,");
   FRMFiltro = fBuscaFrame("IFiltro37");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Trámite,iCveTramite,Modalidad,iCveModalidad,");
   FRMFiltro.fSetOrdena("Trámite,iCveTramite,Modalidad,iCveModalidad,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   frm.hdNumReg.value = 10000;
   fDisabled(false);
   fDisabled(true,"iCveTramite,iCveModalidad,cCveTramite,");
 }

 function fTramiteHijo(){
    frm.hdOrden.value = " cCveInterna ASC";
    frm.hdNumReg.value = 10000;
    fEngSubmite("pgTRACatTramite1A.jsp","cIDTramiteHijo");
 }

 function fModalidadHijo(){
    frm.hdOrden.value = " cDscModalidad ASC";
    frm.hdNumReg.value = 10000;
    fEngSubmite("pgTRAModalidad.jsp","cIDModalidadHijo");
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  "D.iCveTramite = " + frm.iCveTramite.value +" And D.iCveModalidad = "+frm.iCveModalidad.value;
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgTRADependencia.jsp","Listado");
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
  if(cId == "cIDModalidadHijo"){
    fFillSelect(frm.iCveModalidadHijo,aRes,false,frm.iCveModalidadHijo.value,0,1);
  }
  if(cId == "cIDModalidadH"){
    fFillSelect(frm.iCveModalidadHijo,aRes,false,frm.iCveModalidadHijo.value,0,1);
    fNavega();
  }
  if(cId == "cIDTramiteHijo"){
     cTemTra = aRes;
    for(var i =0; i<aRes.length;i++){
     aRes[i][7] = aRes[i][6] +"-"+ aRes[i][3];
     }
     frm.iTemiCveTramite.value = frm.iCveTramite.value;
     fFillSelect(frm.iCveTramiteHijo,aRes,false,frm.iCveTramiteHijo.value,0,7);
     fNavega();
  }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
   fTramiteHijoOnChange();
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(true);
    fDisabled(false,"iCveTramite,iCveModalidad,cCveTramite,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(false);
          fDisabled(true,"iCveTramite,iCveModalidad,cCveTramite,");
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(false);
         fDisabled(true,"iCveTramite,iCveModalidad,cCveTramite,");
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
   fTramiteHijoOnChange();
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(true);
    fDisabled(false,"iCveTramite,iCveModalidad,cCveTramite,,");
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
    fDisabled(true,"iCveTramite,iCveModalidad,cCveTramite,");
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
    frm.iCveTramiteHijo1.value = aDato[2];
    frm.iCveModalidadHijo1.value = aDato[3];
    fSelectSetIndexFromValue(frm.iCveTramiteHijo,aDato[2]);
    fAsignaSelect(frm.iCveModalidadHijo,aDato[3],aDato[7]);
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
function fTramiteModalidad(){
  var cTx;
  cTx = ITRTD("EEtiquetaC",0,"100%","20","center")+
      InicioTabla("",0,"","","center")+
        ITR()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Trámites Disponibles:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Text(false,"cCveTramiteHijo","",7,6,"Teclee la clave interna del trámite para ubicarlo","this.value='';fTramiteHijoOnChange();"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramiteHijo, this);\"","",true,true)+
            Select("iCveTramiteHijo","fTramiteHijoOnChange();")+
          FTD()+
          ITR()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Modalidades Disponibles:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveModalidadHijo","")+
          FTD()+
        FTR()+
      FinTabla()+
    FTDTR();
  return cTx;
}
function fTramiteHijoOnChange() {
    frm.hdBoton.value = "GetModalidadesTramite";
    frm.hdFiltro.value = " CT.iCveTramite = " + frm.iCveTramiteHijo.value + " AND lVigente = 1 ";
    frm.hdOrden.value = " M.cDscModalidad ";
    fEngSubmite("pgTRAModalidad.jsp","cIDModalidadHijo");
}

function fCargaTramitesHijo(){
  frm.hdBoton.value="GetTramites";
  frm.hdOrden.value = " cCveInterna "
  fEngSubmite("pgTRACatTramite.jsp","CIDTramiteHijo");
}

function fSetValores(iTramite,iModalidad){
  frm.iCveTramite.value=iTramite;
  frm.iCveModalidad.value=iModalidad;
  frm.hdFiltro.value =  "iCveTramite != "+frm.iCveTramite.value;
  frm.hdOrden.value = " cCveInterna "
  fEngSubmite("pgTRACatTramite1A.jsp","cIDTramiteHijo");
  //fNavega();
}

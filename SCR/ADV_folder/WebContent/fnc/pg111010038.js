// MetaCD=1.0
 // Title: pg111010037.js
 // Description: JS "Catálogo" de la entidad TRADependencia
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aTram = new Array();
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010038.js";
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
         IFrame("IFiltro38","0","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado38","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"90%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              fTramiteModalidad();
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel38","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
  Hidden("iCveTramite");
  Hidden("iCveModalidad");
  Hidden("iTemiCveTramite");
  Hidden("iCveModalidadHijo");
  Hidden("iTramHijo");
  Hidden("iModHijo");
  Hidden("iCveEtapa");
  Hidden("hdSelect");
  Hidden("hdLlave");
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel38");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado38");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Etapa Támite Padre,Trámite Hijo,Modalidad Hijo,Etapa Relacionada,");
   FRMListado.fSetCampos("10,12,13,14,");
   FRMFiltro = fBuscaFrame("IFiltro38");
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

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  "ET.ICVETRAMITE="+ frm.iCveTramite.value +" and ET.ICVEMODALIDAD="+frm.iCveModalidad.value;
   frm.hdOrden.value =  "ET.IORDEN";
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgTRAEtapaVinculada.jsp","Listado");
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
     fCancelar();
   }
  if(cId == "cIDTramiteHijo"){
    cTemTra = aRes;
    for(var i =0; i<aRes.length;i++){
       aTram = fCopiaArregloBidim(aRes);
       aRes[i][7] = aRes[i][2] +"-"+ aRes[i][3] + " (Modalidad:  "+aRes[i][4]+")";
     }
     frm.iTemiCveTramite.value = frm.iCveTramite.value;
     fFillSelect(frm.iCveTramiteHijo,aRes,false,frm.iCveTramiteHijo.value,0,7);
  }
  if(cId=="cIdEtapa" && cError==""){
    fFillSelect(frm.iCveEtapaVinc,aRes,false,frm.iCveEtapaVinc.value,0,1);
  }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    fCargaTramMod();
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
          FRMPanel.fSetTraStatus("Add,Del,");
          fDisabled(false);
          fDisabled(true,"iCveTramite,iCveModalidad,cCveTramite,");
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Add,Del,");
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
   frm.iTramHijo.value = aDato[2];
   frm.iModHijo.value = aDato[3];
   frm.iCveEtapa.value = aDato[4];
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
            TextoSimple("Etapa:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveEtapaVinc","")+
          FTD()+
        FTR()+
      FinTabla()+
    FTDTR();
  return cTx;
}
function fTramiteHijoOnChange() {
  for(i=0;i<aTram.length;i++){
    if(aTram[i][0]==frm.iCveTramiteHijo.value)frm.iCveModalidadHijo.value = aTram[i][1];
  }
  frm.hdLlave.value =  "";
  frm.hdSelect.value = "SELECT E.ICVEETAPA,E.CDSCETAPA FROM TRAETAPAXMODTRAM EMT " +
                       "JOIN TRAETAPA E ON E.ICVEETAPA=EMT.ICVEETAPA " +
                       "WHERE EMT.ICVETRAMITE="+frm.iCveTramiteHijo.value+" AND EMT.ICVEMODALIDAD= "+frm.iCveModalidadHijo.value;
                       " order by E.CDSCETAPA ";
  fEngSubmite("pgConsulta.jsp","cIdEtapa");
}

function fSetValores(iTramite,iModalidad){
  frm.iCveTramite.value=iTramite;
  frm.iCveModalidad.value=iModalidad;
  fNavega();
}
function fCargaTramMod(){
  frm.hdLlave.value =  "";
  frm.hdSelect.value = "SELECT t.ICVETRAMITE,m.iCveModalidad,t.CCVEINTERNA,t.CDSCBREVE,m.CDSCMODALIDAD FROM TRADEPENDENCIA td " +
                       "join TRACATTRAMITE t on t.ICVETRAMITE=td.ICVETRAMITEHIJO " +
                       "join tramodalidad m on m.ICVEMODALIDAD=td.ICVEMODALIDADHIJO " +
                       "where td.ICVETRAMITE="+frm.iCveTramite.value+" and td.icvemodalidad=" +frm.iCveModalidad.value+
                       " order by t.CCVEINTERNA ";
  fEngSubmite("pgConsulta.jsp","cIDTramiteHijo");
}

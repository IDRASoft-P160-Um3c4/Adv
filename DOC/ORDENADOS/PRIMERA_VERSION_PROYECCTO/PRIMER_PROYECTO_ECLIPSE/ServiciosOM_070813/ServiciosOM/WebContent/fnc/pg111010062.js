// MetaCD=1.0
// Title: pg111010062.js
// Description: JS "Catálogo" de la entidad TRAEtapaXModTram
// Company: Tecnología InRed S.A. de C.V.
// Author: Arturo López Peña
var cTitulo = "";
var FRMListado = "";
var frm;
var aResTemp = new Array;
var aEtapa = new Array;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010062.js";
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
         IFrame("IFiltro62","0","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"90%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              fRequisitoModalidad(true);
           FITR();
         FinTabla();


     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","","center","");
         IFrame("IFiltro62","0","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","1","center");
       FTDTR();
         ITRTD("",0,"","","center","");
           IFrame("IListado62A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fGuardar();");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnquitar","fBorrar();");
             FTDTR();
             ITRTD("",0,"","100%","center","");
                 Liga("Subir","fArriba();","Sube un registro de Orden");
             FTDTR();
             ITRTD("",0,"","100%","center","");
                 Liga("Bajar","fAbajo();","Sube un registro de Orden");
             FTDTR();
             ITRTD("",0,"","100%","center","");
           FinTabla();
         ITD("",0,"","","center","");
           IFrame("IListado62","95%","220","Listado.js","yes",true);
           Liga("Guardar","Recorre();"," ");
       FTDTR();
         FinTabla();
       FTDTR();
       FinTabla();
         ITRTD("",0,"","1","center");
           InicioTabla("ETablaInfo",0,"85%","","","",1);
             FTDTR();
               TDEtiCampo(true,"EEtiqueta",0,"Etapa CIS:","iCveEtapaCIS","",10,10,"Etapa CIS...");
             ITD();
               TDEtiCheckBox("EEtiqueta",0,"Envar fecha al CIS:","lEnviarFechaCISBOX","1",true,"Vigente");
               Hidden("lEnviarFechaCIS","");
           FinTabla();
           FTDTR();
           ITRTD("",0,"","40","center","bottom");
             IFrame("IPanel62","95%","34","Paneles.js");
           FTDTR();
     FinTabla();
     Hidden("lVigente",1);
     Hidden("iCveEtapa");
     Hidden("iCveEtapa1");
     Hidden("iOrden");
     Hidden("lObligatorio");
     Hidden("Orden",0);
     Hidden("cConjunto");
     Hidden("iLlave");
     Hidden("flag1");
     Hidden("flag2");
     Hidden("iTemiCveTramite");
     Hidden("iOrdenI");
     Hidden("iOrdenF");
     Hidden("iEtapaTemp");
     Hidden("hdSelect");
     Hidden("hdLlave");
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel62");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado62");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Obligatoria,Id,Etapas por Modalidad,");
  FRMListado.fSetDespliega("texto,texto,texto,");
  FRMListado.fSetAlinea("center,right,left,");
  FRMListado.fSetCampos("2,8,");
  FRMListado.fSetSelReg(1);
  FRMListado.fSetObjs(0,"Caja")

  FRMFiltro = fBuscaFrame("IFiltro62");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Trámite,iCveTramite,Modalidad,iCveModalidad,");
  FRMFiltro.fSetOrdena("Trámite,iCveTramite,Modalidad,iCveModalidad,");
  FRMListado.fSetSelReg(1);

  FRMListadoA = fBuscaFrame("IListado62A");
  FRMListadoA.fSetControl(self);
  FRMListadoA.fSetTitulo("Id,Etapas Disponibles,");
  FRMListadoA.fSetCampos("0,1,");
  FRMListadoA.fSetSelReg(2);
  FRMListadoA.fSetAlinea("right,left,");

  frm.hdBoton.value="Primero";
  frm.flag1.value = 0;
  frm.flag2.value = 0;
}

function fTramite(){
  if(frm.iCveTramite.value == -1)
    {
     FRMListadoA.fSetListado(new Array);
     FRMListadoA.fShow();
     FRMListado.fSetListado(new Array);
     FRMListado.fShow();
     frm.flag1.value = 0;
     frm.flag2.value = 0;
    }
   else
    {
       frm.hdFiltro.value = "lVigente = 1 and LTRAMITEFINAL=1 ";
       frm.hdOrden.value =  " cCveInterna ASC ";
       frm.hdNumReg.value =  100000;
       fEngSubmite("pgTRACatTramite1A.jsp","CIDTramite");
    }
}

function fModalidad(){
  if(frm.iCveModalidad.value == -1)
    {
     FRMListadoA.fSetListado(new Array);
     FRMListadoA.fShow();
     FRMListado.fSetListado(new Array);
     FRMListado.fShow();
     frm.flag1.value = 0;
     frm.flag2.value = 0;
    }
   else
    {
     frm.hdOrden.value =  " cDscModalidad ASC ";
     frm.hdNumReg.value =  100000;
     fEngSubmite("pgTRAModalidad.jsp","CIDModalidad");
    }
}

function fPreNavega(){
     fNavega();
     frm.flag1.value = 0;
     frm.flag2.value = 0;
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  if(frm.iCveTramite.value == -1 || frm.iCveModalidad.value == -1)
    {
     FRMListadoA.fSetListado(new Array);
     FRMListadoA.fShow();
     FRMListado.fSetListado(new Array);
     FRMListado.fShow();
     frm.flag1.value = 0;
     frm.flag2.value = 0;
    }
   else
    {
     frm.hdFiltro.value =  " TRAEtapaXModTram.iCveTramite = " + frm.iCveTramite.value +
                           " AND TRAEtapaXModTram.iCveModalidad = " + frm.iCveModalidad.value;
     frm.hdOrden.value =  "iOrden";
     frm.hdNumReg.value =  10000;
     return fEngSubmite("pgTRAEtapaXModTram.jsp","Listado");
    }
}
 function fNavega1(){
   var aRes1 = FRMListado.fGetARes();
   var aEtapaLocal = new Array;
   var lEncontrada = false;
   for(i=0;i<aEtapa.length;i++){
     for(j=0;j<aRes1.length;j++){
       if(aRes1[j][2]==aEtapa[i][0]) lEncontrada=true;
     }
     if(!lEncontrada) aEtapaLocal[aEtapaLocal.length] = fCopiaArreglo(aEtapa[i]);
     lEncontrada = false;
   }
   fResultado(aEtapaLocal,"AListado1","");
 }
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Duplicado")
    fAlert("Registro Existente!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }
  if(cError==""){
    if(cId == "Listado"){
      frm.hdRowPag.value = iRowPag;
      FRMListado.fSetListado(aRes);
      FRMListado.fShow();
      aResTemp = fCopiaArreglo(aRes);
      if(FRMListado.fGetLength() > 0){
        frm.flag1.value = 1;
      }
      else
        frm.flag1.value = 0;
      FRMListado.fSetLlave(cLlave);
      if(frm.Orden.value!="") FRMListado.fSelReg(frm.Orden.value);
      else FRMListado.fSelReg(0);
      FRMListado.fSetDefaultValues(0,4);
      fNavega1();
    }
    if(cId=="cIdTodasEtapas"){
      aEtapa = fCopiaArregloBidim(aRes);
      fTramite();
    }
    if(cId == "AListado1" && cError==""){
      frm.hdRowPag.value = iRowPag;
      FRMListadoA.fSetListado(aRes);
      FRMListadoA.fShow();
      if(FRMListadoA.fGetLength() > 0) frm.flag2.value = 1;
      else frm.flag2.value = 0;
      fCancelar();
    }
    if(cId == "CIDTramite" && cError==""){
      cTemTra = aRes;
      for(var i =0; i<aRes.length;i++)
        aRes[i][7] = aRes[i][6] +"-"+ aRes[i][3];
      frm.iTemiCveTramite.value = frm.iCveTramite.value;
      fFillSelect(frm.iCveTramite,aRes,false,frm.iCveTramite.value,0,7);
      fTramiteOnChange();
    }
    if(cId == "CIDModalidad" && cError=="")
      fFillSelect(frm.iCveModalidad,aRes,true,frm.iCveModalidad.value,0,1);
  }
}

function fGuardarA(){
   if(fValidaTodo()==true){
     frm.hdBoton.value = "GuardarC";
     frm.lEnviarFechaCIS.value = frm.lEnviarFechaCISBOX.checked==true?1:0;
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("Mod,");
        fDisabled(false);
        fDisabled(true,"cCveTramite,iCveTramite, iCveModalidad,");
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(true);
   fDisabled(false,"cCveTramite,iCveTramite,iCveModalidad,");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
   FRMFiltro.fSetNavStatus("ReposRecord");
   if(FRMListado.fGetLength() > 0)
     FRMPanel.fSetTraStatus("Mod,");
   else
     FRMPanel.fSetTraStatus("");
   fDisabled(false);
   fDisabled(true,"cCveTramite,iCveTramite,iCveModalidad,");
   FRMListado.fSetDisabled(false);
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
  frm.iCveEtapa.value=aDato[2];
  frm.iOrden.value = aDato[3];
  frm.iCveEtapaCIS.value = aDato[9];
  frm.lEnviarFechaCIS.value = aDato[10];
  frm.lEnviarFechaCISBOX.checked = frm.lEnviarFechaCIS.value==1?true:false;
}

function fSelReg2(aDato){
   frm.iCveEtapa1.value=aDato[0];
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

 function fGuardar(){
   if(frm.flag2.value == 1)
     {
      frm.lObligatorio.value=1;
      frm.hdBoton.value = "GuardarB";
      fNavega();
     }
    else
       fAlert("No hay 'Etapas Disponibles' del lado Izquierdo");
 }
 function fBorrar(){
   if(frm.flag1.value == 1)
     {
      frm.hdBoton.value = "Eliminar";
      fNavega();
     }
    else
       fAlert("No hay 'Etapas por modalidad' del lado Derecho");
 }
 function fArriba(){
   frm.Orden.value=(frm.iCveEtapa.value-2);
   for(i=0;i<aResTemp.length;i++){
     if(aResTemp[i][2]==frm.iCveEtapa.value){
       if(i>0){
         frm.iOrdenI.value = aResTemp[i][3];
         frm.iOrdenF.value = aResTemp[i-1][3];
         frm.iEtapaTemp.value = aResTemp[i-1][2];
         frm.Orden.value = i-1;
         frm.hdBoton.value = "Arriba";
         fNavega();
       }
       else{
         fAlert("No puede recorrer más este campo.");
       }
     }
   }
 }
 function fAbajo(){
   frm.Orden.value=frm.iCveEtapa.value;
   for(i=0;i<aResTemp.length;i++){
     if(aResTemp[i][2]==frm.iCveEtapa.value){
       if(i<(aResTemp.length-1)){
         frm.iOrdenI.value = aResTemp[i][3];
         frm.iOrdenF.value = aResTemp[i+1][3];
         frm.iEtapaTemp.value = aResTemp[i+1][2];
         frm.Orden.value = i+1;
         frm.hdBoton.value = "Arriba";
         fNavega();
       }
       else{
         fAlert("No puede recorrer más este campo.");
       }
     }
   }
 }

 function Recorre(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted Guardar los Registros?")){
      frm.iLlave.value=0;
      aCBox = FRMListado.fGetObjs(0);
      aRes = FRMListado.fGetARes();
      frm.cConjunto.value="";
      for(aux=0; aux<aCBox.length; aux++){
        if (aCBox[aux]==true){
            frm.iLlave.value=1;
          if (frm.cConjunto.value == ""){
            frm.cConjunto.value = aRes[aux][2];
          }else
            frm.cConjunto.value += "," + aRes[aux][2];
        }
      }
      if (frm.iLlave.value == 0) frm.cConjunto.value = -1;
      frm.hdBoton.value = "Cambia";
      fNavega();
   }
   else fNavega();
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
  frm.cCveTramite.value="";
  if (frm.iCveTramiteHijo && frm.iCveTramiteHijo.value && frm.iCveTramiteHijo.value !="" && frm.iCveTramiteHijo.value >= 0){
    frm.hdBoton.value = "GetModalidadesTramite";
    frm.hdFiltro.value = " CT.iCveTramite = " + frm.iCveTramiteHijo.value + " AND lVigente = 1 ";
    frm.hdOrden.value = " cCveInterna "
    frm.hdNumReg.value =  100000;
    fSelectSetIndexFromValue(frm.iCveModalidadHijo, "-1");
    fEngSubmite("pgTRAModalidad.jsp","CIDModalidadHijo");
  }
}
function fModalidadHijoOnChange(){
}

function fCargaTramitesHijo(){
  frm.hdBoton.value="GetTramites";
  frm.hdOrden.value = " cCveInterna "
  frm.hdNumReg.value =  100000;
  fEngSubmite("pgTRACatTramite.jsp","CIDTramiteHijo");
}
function fModalidadOnChangeLocal(){
   if (frm.iCveTramite.value != -1 && frm.iCveModalidad.value != -1 &&frm.iCveTramite.value != "" && frm.iCveModalidad.value != "")
     fNavega();
}

function fTramiteOnChange() {
  frm.cCveTramite.value="";
   FRMListado.fSetListado(new Array);
   FRMListado.fShow();
   FRMListadoA.fSetListado(new Array);
   FRMListadoA.fShow();
  if (frm.iCveTramite && frm.iCveTramite.value && frm.iCveTramite.value !="" ){
    frm.hdBoton.value = "GetModalidadesTramite";
    frm.hdFiltro.value = " CT.iCveTramite = " + frm.iCveTramite.value + " AND lVigente = 1 ";
    frm.hdOrden.value = " cDscModalidad "
    frm.hdNumReg.value =  100000;
    fSelectSetIndexFromValue(frm.iCveModalidad, "-1");
    fEngSubmite("pgTRAModalidad.jsp","CIDModalidad");
  }
}

function fModalidadOnChange(){
  FRMListado.fSetListado(new Array);
  FRMListado.fShow();
  FRMListadoA.fSetListado(new Array);
  FRMListadoA.fShow();
  if (fModalidadOnChangeLocal) fModalidadOnChangeLocal();
  else if (fModalidadOnChangeLocal) fModalidadOnChangeLocal();
}

function fTraeEtapas(){
  frm.hdSelect.value = "SELECT ICVEETAPA,CDSCETAPA FROM TRAETAPA WHERE LVIGENTE = 1";
  frm.hdLlave.value  = "ICVEETAPA";
  fEngSubmite("pgConsulta.jsp","cIdTodasEtapas");
}

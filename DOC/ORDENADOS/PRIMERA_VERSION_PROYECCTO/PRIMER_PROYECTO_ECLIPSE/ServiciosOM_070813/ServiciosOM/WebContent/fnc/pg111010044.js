// MetaCD=1.0
// Title: pg111010050.js
// Description: JS "Catálogo" de la entidad TRARequisitoXGpo
// Company: Tecnología InRed S.A. de C.V.
// Author: Arturo López Peña.
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010044.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
     ITRTD("ETablaST",0,"","","center");
        TextoSimple(cTitulo);
     FTDTR();
     InicioTabla("",1,"100%","","","","1");
     FTDTR();
        InicioTabla("",1,"100%","","","","1");
          FTDTR();
          ITRTD("",0,"90%","","center","");
             IFrame("IListado44B","50%","150","Listado.js","yes",true);
             IFrame("IListado44C","50%","150","Listado.js","yes",true);
        FinTabla();
        FTDTR();
        InicioTabla("",0,"100%","","","","1");
          InicioTabla("",0,"100%","","","","1");
            //TDEtiCampo(true,"EEtiqueta",0,"Descripcion de Requisito:","cDscRequisito","",100,100,"Clave","fMayus(this);");
            TDEtiAreaTexto(false,"EEtiqueta",0,"Descripción de Requisito:",100,3,"cDscRequisito","","Descripcion de Requisito...","","fMayus(this);",'onkeydown="fMxTx(this,50);"',"","","");
          FinTabla();
          FTDTR();
            IFrame("IListado44","100%","120","Listado.js","yes",true);
          FTDTR();
          ITRTD("",0,"95%","","rigth","");
          FinTabla();
     FinTabla();

     Hidden("iCveGrupo");
     Hidden("iCveRequisito");
  FinTabla();
     InicioTabla("",0,"100%","","","",1);
          ITRTD("",0,"90%","","center","");
            InicioTabla("ETablaInfo",0,"75%","","","",1);
               ITRTD("ETablaST",10,"","","center");
                 TextoSimple("Detalle de Características.");
               FTDTR();
                 TDEtiAreaTexto(true,"EEtiqueta",0,"Características:",80,2,"cDscCaracteristica","","Descripción","","fMayus(this);",'onkeydown="fMxTx(this,150);"',"","","","EEtiquetaL","11");
               FTDTR();
                 TDEtiCampo(true,"EEtiqueta",0,"Desc. Breve:","cDscBreve","",50,50,"Desc. Breve","fMayus(this);","","","","EEtiquetaL","11");
               FTDTR();
                 TDEtiCheckBox("EEtiqueta",0,"En Recepción:","lEnRecepcionBOX","1",true,"Vigente");
                 Hidden("lEnRecepcion","");
                 TDEtiCheckBox("EEtiqueta",0,"En Proceso:","lEnProcesoBOX","1",true,"Vigente");
                 Hidden("lEnProceso","");
               FTDTR();
                 TDEtiCheckBox("EEtiqueta",0,"Mandatorio:","lMandatorioBOX","1",true,"Vigente");
                 Hidden("lMandatorio","");
                 TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
                 Hidden("lVigente","");
            FinTabla();
     FinTabla();
     InicioTabla("",0,"100%","","","",1);
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel44","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
    Hidden("iCveCaracteristica");
    Hidden("");
    Hidden("");
  fFinPagina();
}

function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel44");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
   frm.hdNumReg.value =  10000;
   frm.hdOrden.value =  "TRACatTramite.cCveInterna";
   fEngSubmite("pgTRACatTramite1A.jsp","cIDTramite");
   frm.hdOrden.value =  ""; //cDscModalidad
   fEngSubmite("pgTRAModalidad.jsp","cIDModalidad");
   frm.hdOrden.value =  "";
   FRMListado = fBuscaFrame("IListado44");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Consecutivo,Características,En Recepción,En Proceso,");
   FRMListado.fSetAlinea("center,left,center,center,");
   FRMListado.fSetDespliega("texto,texto,logico,logico,");
   FRMListado.fSetCampos("1,2,5,6,");
   FRMListado.fSetSelReg(1);
   FRMListadoB = fBuscaFrame("IListado44B");
   FRMListadoB.fSetControl(self);
   FRMListadoB.fSetTitulo("Grupos,");
   FRMListadoB.fSetCampos("1,");
   FRMListadoB.fSetSelReg(2);
    FRMListadoC = fBuscaFrame("IListado44C");
    FRMListadoC.fSetControl(self);
    FRMListadoC.fSetTitulo("Requisitos,");
    FRMListadoC.fSetCampos("3,");
    FRMListadoC.fSetSelReg(3);
  fDisabled(true,"iCveGrupo,");
  frm.hdBoton.value="Primero";
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  10000;
  fDisabled(false);
  //fListaGrupo();
}
 function fListaGrupo(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgTRAGpoRequisito.jsp","CIDGrupo");
 }
 function fListaRequisito(){
   frm.hdFiltro.value =  "TRARequisitoXGpo.iCveGrupo = " + frm.iCveGrupo.value;
   frm.hdOrden.value =  "iOrden";
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgTRARequisitoXGpo.jsp","CIDRequisitos");
 }
 function fNavega(){
   frm.hdFiltro.value =  "TRACaracXRequisito.iCveRequisito = " + frm.iCveRequisito.value;
   frm.hdOrden.value =  "iCveCaracteristica";
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgTRACaracXRequisito1.jsp","Listado");
 }
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
   }
   if(cId == "CIDGrupo" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListadoB.fSetListado(aRes);
     FRMListadoB.fShow();
     FRMListadoB.fSetLlave(cLlave);
   }
      if(cId == "CIDRequisitos" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListadoC.fSetListado(aRes);
     FRMListadoC.fShow();
     FRMListadoC.fSetLlave(cLlave);
   }
 }

function fGuardarA(){
  frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
  frm.lEnRecepcionBOX.checked==true?frm.lEnRecepcion.value=1:frm.lEnRecepcion.value=0;
  frm.lEnProcesoBOX.checked==true?frm.lEnProceso.value=1:frm.lEnProceso.value=0;
  frm.lMandatorioBOX.checked==true?frm.lMandatorio.value=1:frm.lMandatorio.value=0;
   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("UpdateComplete");
        fDisabled(true);
        FRMListado.fSetDisabled(false);
      }
   }
}

function fNuevo(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked("cDscRequisito,");
   fDisabled(false,"cDscRequisito,");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
  frm.lEnRecepcionBOX.checked==true?frm.lEnRecepcion.value=1:frm.lEnRecepcion.value=0;
  frm.lEnProcesoBOX.checked==true?frm.lEnProceso.value=1:frm.lEnProceso.value=0;
  frm.lMandatorioBOX.checked==true?frm.lMandatorio.value=1:frm.lMandatorio.value=0;
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
   fDisabled(false,"cDscRequisito,");//,"iCveOficina,");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
   //FRMFiltro.fSetNavStatus("ReposRecord");
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

 function fSelReg(aDato){
   frm.cDscCaracteristica.value = aDato[2];
   frm.cDscBreve.value = aDato[3];
   frm.iCveCaracteristica.value = aDato[1];
   frm.lVigente.value=aDato[4];
   frm.lVigente.value == 1?frm.lVigenteBOX.checked=true:frm.lVigenteBOX.checked=false
   frm.lEnRecepcion.value=aDato[5];
   frm.lEnRecepcion.value == 1?frm.lEnRecepcionBOX.checked=true:frm.lEnRecepcionBOX.checked=false
   frm.lEnProceso.value=aDato[6];
   frm.lEnProceso.value == 1?frm.lEnProcesoBOX.checked=true:frm.lEnProcesoBOX.checked=false
   frm.lMandatorio.value=aDato[7];
   frm.lMandatorio.value == 1?frm.lMandatorioBOX.checked=true:frm.lMandatorioBOX.checked=false
 }
 function fSelReg2(aDato){
    frm.iCveGrupo.value = aDato[0];
    fListaRequisito();
 }
 function fSelReg3(aDato){
    frm.iCveRequisito.value = aDato[1];
    frm.cDscRequisito.value = aDato[5];
    fNavega();
 }
function fValidaTodo(){
   cMsg = fValElements("cDscCaracteristica,");
   if(fEvaluaCampo(frm.cDscCaracteristica.value)==false) cMsg=+"\n - El campo de caracteristicas tiene formato incorrecto.";

   if(cMsg != ""){
      fAlert(cMsg);
      return false;
   }
   return true;
}
function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if ( fRaros(cVCadena)       ||
         fSignos(cVCadena)      ||  //fLetras(cVCadena)     ||
         fArroba(cVCadena)       || //fParentesis(cVCadena)  ||
         fGuionBajo(cVCadena))
        return false;
    else
        return true;
}
function fImprimir(){
   self.focus();
   window.print();
}

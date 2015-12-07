// MetaCD=1.0
// Title: pg111010061.js
// Description: JS "Cat�logo" de la entidad TRAEtapa
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = "";
var FRMListado = "";
var frm;
var iPagAnt = 1;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010061.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","","center");
      ITRTD("",0,"","40","center","top");
        IFrame("IFiltro61","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado61","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(false,"EEtiqueta",0," Consecutivo:","iCveEtapa","",3,3," Consecutivo","fMayus(this);");
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0," Descripci�n:",75,2,"cDscEtapa",""," Descripci�n","","fMayus(this);",'onkeydown="fMxTx(this,150);"');
           FITR();
              TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente");
              Hidden("lVigente","");
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel61","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel61");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado61");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(" Consecutivo, Descripci�n,Vigente, ");
  FRMListado.fSetAlinea("center,left,center,");
  FRMListado.fSetDespliega("texto,texto,logico,");
  FRMListado.fSetCampos("0,1,2,");
  FRMListado.fSetCol(1,"left");
  FRMFiltro = fBuscaFrame("IFiltro61");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra(" Consecutivo,iCveEtapa, Descripci�n,cDscEtapa,");
  FRMFiltro.fSetOrdena(" Consecutivo,iCveEtapa, Descripci�n,cDscEtapa,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  fNavega();
}
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
  return fEngSubmite("pgTRAEtapa.jsp","Listado");
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
    fCancelar();
    FRMFiltro.fSetNavStatus(cNavStatus);
  }
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
function fNuevo(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked();
   fDisabled(false,"iCveEtapa,","--");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
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
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
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
   fDisabled(false,"iCveEtapa,");
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
   FRMListado.focus();
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un rengl�n
function fSelReg(aDato){
   frm.iCveEtapa.value = aDato[0];
   frm.cDscEtapa.value = aDato[1];
   fAsignaCheckBox(frm.lVigenteBOX,aDato[2]);
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
   FRMListado.focus();
   window.print();
}

function fGetCveEtapa(){
   return frm.iCveEtapa.value;
}

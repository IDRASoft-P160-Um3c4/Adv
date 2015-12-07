// MetaCD=1.0
// Title: pg111010032.js
// Description: JS "Cat�logo" de la entidad TRAModalidad
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010032.js";
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
        IFrame("IFiltro32","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado32","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
            TDEtiCampo(false,"EEtiqueta",0," Consecutivo:","iCveModalidad","",3,3," Consecutivo","fMayus(this);");
          FITR();
            TDEtiCampo(true,"EEtiqueta",0," Descripci�n:","cDscModalidad","",100,100," Descripci�n","fMayus(this);");
          FITR();
            TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente");
            Hidden("lVigente","");
          FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel32","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
    Hidden("iClaveModalidad");
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel32");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado32");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(" Consecutivo, Descripci�n, Vigente, ");
  FRMListado.fSetAlinea("center,left,center,");
  FRMListado.fSetDespliega("texto,texto,logico,");
  FRMListado.fSetCampos("0,1,2,");
  FRMListado.fSetCol(1,"left");
  FRMFiltro = fBuscaFrame("IFiltro32");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra(" Consecutivo,iCveModalidad, Descripci�n,cDscModalidad,");
  FRMFiltro.fSetOrdena(" Consecutivo,iCveModalidad, Descripci�n,cDscModalidad,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  //fNavega();
}
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value = FRMFiltro.document.forms[0].iNumReg.value;
  return fEngSubmite("pgTRAModalidad.jsp","Listado");
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
  if(cError!=""){
    fAlert(cError);
    FRMFiltro.fSetNavStatus("Record");
    }

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
   fDisabled(false,"iCveModalidad,","--");
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
   fDisabled(false,"iCveModalidad,");
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
   frm.iCveModalidad.value = aDato[0];
   frm.iClaveModalidad.value = aDato[0];
   frm.cDscModalidad.value = aDato[1];
   fAsignaCheckBox(frm.lVigenteBOX,aDato[2]);
}
// FUNCI�N donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   cMsg = fValElements("cDscModalidad,");
   if(fRaros32(frm.cDscModalidad.value)==true)
     cMsg += "\n - El campo Descripci�n contiene caracteres no aceptados."

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

function fGetCveMod(){
  return frm.iClaveModalidad.value;
}
function fGetDscMod(){
  return frm.cDscModalidad.value;
}
function fSetValues(iCveModalidad){
  frm.iCveModalidad.value = iCveModalidad;
  fReposicionaListado(FRMListado,"0", frm.iCveModalidad.value);
}
function fRaros32(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"%")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"?")    ||
       fEncCaract(cVCadena.toUpperCase(),"|")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"$")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"!")    ||
       fEncCaract(cVCadena.toUpperCase(),"{")    ||
       fEncCaract(cVCadena.toUpperCase(),"}")    ||
       fEncCaract(cVCadena.toUpperCase(),"[")    ||
       fEncCaract(cVCadena.toUpperCase(),"]")    ||
       fEncCaract(cVCadena.toUpperCase(),"~")    ||
       fEncCaract(cVCadena.toUpperCase(),"� ")   ||
       fEncCaract(cVCadena.toUpperCase(),"'")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�")    ||
       fEncCaract(cVCadena.toUpperCase(),"�") )
       return true;
   else
      return false;
}

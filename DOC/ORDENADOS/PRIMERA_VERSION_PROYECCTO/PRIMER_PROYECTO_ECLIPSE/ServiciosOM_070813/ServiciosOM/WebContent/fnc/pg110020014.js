// MetaCD=1.0
// Title: pg110020014.js
// Description: JS "Cat�logo" de la entidad GRLCausaPNC
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Hanksel Fierro Medina
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110020014.js";
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
        IFrame("IFiltro14","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado14","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
              TDEtiSelect(true,"EEtiqueta",0,"Producto:","iCveProducto","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Consecutivo:","iCveCausaPNC","",3,3,"Clave","fMayus(this);");
           FITR();
              TDEtiAreaTexto(false,"EEtiqueta",0,"Descripci�n de la Causa:",50,5,"cDscCausaPNC","","Descripci�n","","fMayus(this);",'onkeydown="fMxTx(this,150);"');
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
              Hidden("lVigente","");
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel14","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel14");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado14");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Producto,Consecutivo,Descripci�n de la Causa,Vigente,");
  FRMListado.fSetCampos("4,1,2,3,");
  FRMListado.fSetAlinea("left,center,left,center,");
  FRMListado.fSetDespliega("texto,texto,texto,logico,");
  FRMFiltro = fBuscaFrame("IFiltro14");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Producto,iCveProducto,Clave,iCveCausaPNC,");
  FRMFiltro.fSetOrdena("Producto,iCveProducto,Clave,iCveCausaPNC,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  //fNavega();
}
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
  return fEngSubmite("pgGRLCausaPNC.jsp","Listado");
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

  if(cId == "idproducto" && cError==""){
   fFillSelect(frm.iCveProducto,aRes,false,frm.iCveProducto.value,0,1);
  }

}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
function fNuevo(){
  frm.hdNumReg.value = 100000;
  frm.hdBoton.value = "Primero";
  frm.hdOrden.value = "";
  frm.hdFiltro.value = " 1=1 ";
  if(fEngSubmite("pgGRLProducto.jsp","idproducto")==true){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iCveProducto,");
    fDisabled(false,"iCveCausaPNC,","--");
    FRMListado.fSetDisabled(true);
  }
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){
if( frm.lVigenteBOX.checked ) frm.lVigente.value = 1;
   else frm.lVigente.value = 0;

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
if( frm.lVigenteBOX.checked ) frm.lVigente.value = 1;
   else frm.lVigente.value = 0;

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
  if(frm.iCveCausaPNC.value == 0){
    fAlert('\n - El consecutivo "0" no puede ser modificado.');
    return;
  }

if( frm.lVigenteBOX.checked ) frm.lVigente.value = 1;
   else frm.lVigente.value = 0;

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
  if(frm.iCveCausaPNC.value == 0){
    fAlert('\n - El consecutivo "0" no puede ser borrado.');
    return;
  }
   if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un rengl�n
function fSelReg(aDato){
   fAsignaCheckBox(frm.iCveProducto,aDato[4]);
   //frm.iCveProducto.value = aDato[0];
   frm.iCveCausaPNC.value = aDato[1];
   frm.cDscCausaPNC.value = aDato[2];
   frm.lVigente.value = aDato[3];
   fAsignaCheckBox(frm.lVigenteBOX,aDato[3]);
   fAsignaSelect(frm.iCveProducto,aDato[0],aDato[4]);
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

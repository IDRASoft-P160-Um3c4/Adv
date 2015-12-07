// MetaCD=1.0
// Title: pg111010041.js
// Description: JS "Cat�logo" de la entidad TRARequisito
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = "";
var FRMListado = "";
var frm;
var checa1;
var checa2;
var checa3;
var checa4;

var iPagAnt = 1;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010041.js";
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
        IFrame("IFiltro41","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado41","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"95%","","","",1);
          ITRTD("ETablaST",6,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(false,"EEtiqueta",0," Consecutivo:","iCveRequisito","",3,3," Consecutivo","fMayus(this);","","",true,"",5);
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0," Descripci�n:",85,2,"cDscRequisito",""," Descripci�n","","fMayus(this);",'onkeydown="fMxTx(this,700);"',true,true,true,"",10);
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0," Desc. Breve:",85,2,"cDscBreve",""," Desc. Breve","","fMayus(this);",'onkeydown="fMxTx(this,150);"',true,true,true,"",10);
              //TDEtiCampo(false,"EEtiqueta",0," Desc. Breve:","cDscBreve","",75,150," Desc. Breve","fMayus(this);");
           FITR();
              TDEtiAreaTexto(false,"EEtiqueta",0," Fundamento Legal:",85,2,"cFundLegal",""," Fundamento Legal","","fMayus(this);",'onkeydown="fMxTx(this,500);"',true,true,true,"",10);
              //TDEtiCampo(false,"EEtiqueta",0," Fundamento Legal:","cFundLegal","",75,500," Fundamento Legal","fMayus(this);");
           FITR();
              TDEtiSelect(false,"EEtiqueta",0," Formato:","iCveFormato","");
              TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente");
              Hidden("lVigente","");
              TDEtiCheckBox("EEtiqueta",0," Digitaliza:","lDigitalizaBOX","1",true," Vigente");
              Hidden("lDigitaliza","");
           FITR(); 
              //TDEtiAreaTexto(true,"EEtiqueta",0,"Fundamento Legal:",50,3,"cFundLegal","","Fundamento Legal","","fMayus(this);",'onkeydown="fMxTx(this,3);"');

//=========================================================================
//FCSReq1 -Se Agrego el Campo cMetodoEjecuta y Etiqueta "Metodo Ejecutar" -
           FITR();
             TDEtiCampo(false,"EEtiqueta",0," Metodo Ejecutar:","cMetodoEjecuta","",80,80," Metodo ejecutar","fMayus(this);","","",false,"",5);
           FITR();
//FCSReq1 -Se Agrego el Campo cMetodoEjecuta y Etiqueta "Metodo Ejecuta "-
//=========================================================================


           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel41","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel41");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado41");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(" Consecutivo, Descripci�n, ");
  FRMListado.fSetAlinea("center,left,");
  FRMListado.fSetCampos("0,1,")
  FRMListado.fSetCol(1,"left");
  FRMFiltro = fBuscaFrame("IFiltro41");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra(" Consecutivo,iCveRequisito, Descripci�n,cDscRequisito,");
  FRMFiltro.fSetOrdena(" Consecutivo,iCveRequisito, Descripci�n,cDscRequisito,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  fNavega();
}
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
  return fEngSubmite("pgTRARequisito.jsp","Listado");
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
   if(cId == "CIDFormato" && cError==""){
       fFillSelect(frm.iCveFormato,aRes,false,frm.iCveFormato.value,0,1);
   }
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
function fNuevo(){
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  100;
   if(fEngSubmite("pgGRLFormato.jsp","CIDFormato")==true){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked();
   fDisabled(false,"iCveRequisito,","--");
   FRMListado.fSetDisabled(true);
   }
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
   frm.lDigitalizaBOX.checked==true?frm.lDigitaliza.value=1:frm.lDigitaliza.value=0;
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
   frm.lDigitalizaBOX.checked==true?frm.lDigitaliza.value=1:frm.lDigitaliza.value=0;
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
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  100;
   if(fEngSubmite("pgGRLFormato.jsp","CIDFormato")==true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fDisabled(false,"iCveRequisito,");
     FRMListado.fSetDisabled(true);
   }
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
//fAlert(aDato);
   frm.iCveRequisito.value = aDato[0];
   frm.cDscRequisito.value = aDato[1];
   frm.cDscBreve.value = aDato[2];
   frm.cFundLegal.value = aDato[3];
   frm.lDigitaliza.value = aDato[5];
   fAsignaCheckBox(frm.lVigenteBOX,aDato[4]);
   //if (frm.lDigitaliza.value==1) frm.lDigitalizaBOX.checked = true;
   //  else frm.lDigitalizaBOX.checked = false;
   fAsignaCheckBox(frm.lDigitalizaBOX,aDato[5]);
   fAsignaSelect(frm.iCveFormato,aDato[6],aDato[7]);

//====================================================================================
//FCSReq1 -Se Agrego para obtener el valor de cMetodoEjecuta y Mostrarlo en Pantalla -
   frm.cMetodoEjecuta.value = aDato[8];
//FCSReq1 -Se Agrego para obtener el valor de cMetodoEjecuta y Mostrarlo en Pantalla -
//====================================================================================

}
// FUNCI�N donde se generan las validaciones de los datos ingresados
function fValidaTodo(){

//===============================
//FCSReq1 -Agregar Campo cMetodoEjecuta-
   //cMsg = fValElements("cDscRequisito,cDscBreve,cFundLegal,");
   cMsg = fValElements("cDscRequisito,cDscBreve,cFundLegal,cMetodoEjecuta,");
//FCSReq1 -Agregar Campo cMetodoEjecuta-
//===============================


     if(frm.cDscRequisito.value !="")  checa1 = fEvaluaCampo(frm.cDscRequisito.value);
     if(frm.cDscBreve.value !="")  checa2 = fEvaluaCampo(frm.cDscBreve.value);
     if(frm.cFundLegal.value !="")  checa3 = fEvaluaCampo(frm.cFundLegal.value);

//============================================
//FCSReq1 -Se Valida el Campo cMetodoEjecuta -
     if(frm.cMetodoEjecuta.value !="")  checa4 = fEvaluaCampo(frm.cMetodoEjecuta.value);
//FCSReq1 -Se Valida el Campo cMetodoEjecuta -
//============================================


//==========================================
//FCSReq1 -Se Agrego "checa4" para evaluar -
      if(checa1==false || checa2==false || checa3==false || checa4==false)          //evalua los parametros que son aceptados en el
       cMsg = cMsg+"\n Solo se permiten caracteres alfanum�ricos, coma, punto, par�ntesis y diagonal";    // cDscTramite como son parentesis
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
function fGetCveReq(){
   return frm.iCveRequisito.value;
}

function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if (fRaros2(cVCadena))
        {
         return false;  //Este regresa a la funcion fValidaTodo en donde est� la sig instrucci�n if(fEvaluaCampo(frm.cDscTramite.value)==false)
        }
    else
        return true;
 }

function fRaros2(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"^") ||
       fEncCaract(cVCadena.toUpperCase(),"`") ||
       fEncCaract(cVCadena.toUpperCase(),"�") ||
       fEncCaract(cVCadena.toUpperCase(),"�") ||
       fEncCaract(cVCadena.toUpperCase(),"�") )
       return true;
   else
      return false;
}

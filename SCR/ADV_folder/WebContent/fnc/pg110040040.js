// MetaCD=1.0
// Title: pg110040040.js
// Description: JS "Catálogo" de la entidad GRLDiaNoHabil
// Company: Tecnología InRed S.A. de C.V.
// Author: Richard Martinez<dd>Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110040040.js";
  if(top.fGetTituloPagina)
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  fSetWindowTitle();
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
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(true,"EEtiqueta",0," Ejercicio:","iEjercicio","",4,4," Ejercicio","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Fecha No Hábil:","dtNoHabil","",10,10," Fecha No Hábil","fValoresFecha(this.value, 'dd/mm/aaaa', '/', frm.iEjercicio);");
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0," Descripción del día:",25,2,"cDscDia",""," Descripción del día","","fMayus(this);",'onkeydown="fMxTx(this,30);"');
           FTR();
        FinTabla();
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
  FRMListado.fSetTitulo("Ejercicio,Fecha No Hábil,Descripción del día,");

  FRMFiltro = fBuscaFrame("IFiltro");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Ejercicio,iEjercicio,Fecha No Hábil,dtNoHabil,");
  FRMFiltro.fSetOrdena("Ejercicio,iEjercicio,Fecha No Hábil,dtNoHabil,");

  fDisabled(true);
  frm.hdBoton.value="Primero";
  fNavega();
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
  return fEngSubmite("pgGRLDiaNoHabil.jsp","Listado");
}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
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

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked();
   fDisabled(false,"iEjercicio,");
   FRMListado.fSetDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
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
   fDisabled(false,"iEjercicio,");
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
}

// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
      fNavega();
   }
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
   frm.iEjercicio.value = aDato[0];
   frm.dtNoHabil.value = aDato[1];
   frm.cDscDia.value = aDato[2];
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

function fCambioFecha(elObjeto){
  fValoresFecha(elObjeto.value, "", "", frm.iEjercicio);
}

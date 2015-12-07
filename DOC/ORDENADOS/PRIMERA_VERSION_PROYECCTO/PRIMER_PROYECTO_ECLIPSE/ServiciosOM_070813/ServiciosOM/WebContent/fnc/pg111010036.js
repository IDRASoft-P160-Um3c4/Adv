// MetaCD=1.0
// Title: pg110010036.js
// Description: JS "Cat�logo" de la entidad TRACofemer
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010036.js";
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
        IFrame("IFiltro36","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado36","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(true,"EEtiqueta",0," Ejercicio:","iEjercicio","",4,4," Ejercicio","fMayus(this);");
           FITR();
             // TDEtiCampo(false,"EEtiqueta",0," Tr�mite:","cDscTramite","",75,75," Tr�mite","fMayus(this);");
              Hidden("iCveTramite");
              Hidden("flag1");
              Hidden("flag2");
              Hidden("flag3");
           FITR();
             // TDEtiCampo(false,"EEtiqueta",0," Modalidad:","cDscModalidad","",30,30," Modalidad","fMayus(this);");
              Hidden("iCveModalidad");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Clave en COFEMER:","cCveCofemer","",50,50," Clave en COFEMER","fMayus(this);");
           FTR();
           FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel36","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel36");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado36");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(" Ejercicio, Tr�mite, Modalidad, Clave en COFEMER,");
  FRMListado.fSetAlinea("center,left,left,center,");
  FRMListado.fSetCampos("0,4,5,3,");
  FRMListado.fSetCol(1,"left");
  FRMListado.fSetCol(2,"left");
  FRMListado.fSetCol(3,"left");
  FRMFiltro = fBuscaFrame("IFiltro36");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow("Nav,Fil,Reg,");
  FRMFiltro.fSetFiltra(" Ejercicio,iEjercicio, ");
  FRMFiltro.fSetOrdena(" Ejercicio,iEjercicio, ");
  fDisabled(true);
  frm.hdBoton.value="Primero";
}
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){
  if(frm.iCveModalidad.value == "")
        	frm.iCveModalidad.value = 1;

//  if(FRMFiltro.fGetFiltro() == "")
     frm.hdFiltro.value =  "TRACofemer.iCveModalidad = " + frm.iCveModalidad.value + " and TRACofemer.iCveTramite = " + frm.iCveTramite.value;
//  if(FRMFiltro.fGetFiltro() != "")
//     frm.hdFiltro.value =  "TRACofemer.iCveModalidad = " + frm.iCveModalidad.value + " and TRACofemer.iCveTramite = " + frm.iCveTramite.value + " and " + FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  10000;
  return fEngSubmite("pgTRACofemer.jsp","Listado");
}
// RECEPCI�N de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError=="Guardar")
    fAlert("Existi� un error en el Guardado!");
  if(cError=="Duplicado")
    fAlert("Registro existente!");
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
   fBlanked("iCveTramite,iCveModalidad,");
   fDisabled(false,"iCveTramite,iCveModalidad,","--");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){
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
   fDisabled(false,"iEjercicio,");
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
   frm.iEjercicio.value = aDato[0];
   frm.cCveCofemer.value = aDato[3];

}
// FUNCI�N donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   cMsg = fValElements("cCveCofemer,");
   if(fRarosRFC(frm.cCveCofemer.value)==true) cMsg += '\n - El campo "* Clave en COFEMER" no acepta algunos de los simbolos agregados.';
   if(cMsg != ""){
      fAlert(cMsg);
      return false;
   }
   return true;
}
function fRarosRFC(cVCadena){
   if (
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
function fImprimir(){
   self.focus();
   window.print();
}

function fSetMod(iCveModalidad){
   frm.iCveModalidad.value = iCveModalidad;
   if(frm.iCveTramite.value == -1 || frm.iCveTramite.value == ""){}
   else
    {
     fNavega();
    }
}
function fSetTra(iCveTramite){
   frm.iCveTramite.value = iCveTramite;
   if(frm.iCveModalidad.value == -1 || frm.iCveModalidad.value == ""){}
   else
    {
     fNavega();
    }
   }
function fSetValores(iCveTramite,iCveModalidad){
    frm.iCveModalidad.value = iCveModalidad;
    frm.iCveTramite.value = iCveTramite;
    fNavega();
}
/*
function fSetValores(iCveTramite,cDscTramite,iCveModalidad,cDscModalidad,bandera){
    frm.iCveModalidad.value = iCveModalidad;
    frm.iCveTramite.value = iCveTramite;
    frm.cDscTramite.value = cDscTramite;
    frm.cDscModalidad.value = cDscModalidad;
    frm.flag1.value = 0;
    if(frm.flag2.value == 1 && bandera == undefined)
    {
     fNavega();
    }
    else
     {
      if(frm.flag1.value == 0)
       {
        frm.flag2.value=1;
       }
     }

}
*/

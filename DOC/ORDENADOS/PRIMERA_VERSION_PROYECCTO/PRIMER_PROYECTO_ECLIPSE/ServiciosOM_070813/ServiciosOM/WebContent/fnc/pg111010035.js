// MetaCD=1.0
// Title: pg110010035.js
// Description: JS "Catálogo" de la entidad TRATiempoTraslado
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = "";
var FRMListado = "";
var frm;
var lNuevo = true;
var cTemOficinas = new Array();
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010035.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","","center");
      ITRTD("",0,"","40","center","top");
        IFrame("IFiltro35","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado35","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
              TDEtiSelect(true,"EEtiqueta",0," Oficina Origen:","iCveOficinaOrigen","");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0," Oficina Destino:","iCveOficinaDestino","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Inicio Vigencia:","dtIniVigencia","",10,10," Inicio Vigencia","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Núm. días Traslado:","iNumDiasTraslado","",3,3," Núm. días Traslado","fMayus(this);");
           FITR();
              TDEtiCheckBox("EEtiqueta",0," Días Naturales:","lDiasNaturalesTrasladoBOX","1",true," Días Naturales");
              Hidden("lDiasNaturalesTraslado","");
              Hidden("flag1");
              Hidden("flag2");
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel35","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
    Hidden("iCveTramite");
    Hidden("iCveModalidad");
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel35");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado35");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(" Trámite, Modalidad, Oficina Origen, Oficina Destino, Inicio Vigencia, Núm. días Traslado, ");
  FRMListado.fSetAlinea("left,left,left,left,center,center,");
  FRMListado.fSetCampos("7,8,9,10,4,5,");
  FRMListado.fSetCol(0,"left");
  FRMListado.fSetCol(1,"left");
  FRMListado.fSetCol(2,"left");
  FRMListado.fSetCol(3,"left");
  FRMFiltro = fBuscaFrame("IFiltro35");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow("Nav,Reg,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  //frm.iCveTramite.value = 1;
  //frm.iCveModalidad.value = 1;
  //fNavega();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  if(frm.iCveModalidad.value == "")
        	frm.iCveModalidad.value = 1;
  frm.hdFiltro.value =  "TT.iCveModalidad = " + frm.iCveModalidad.value + " and TT.iCveTramite = " + frm.iCveTramite.value;
  frm.hdOrden.value =  " TT.DTINIVIGENCIA DESC ";
  frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
  return fEngSubmite("pgTRATiempoTraslado.jsp","Listado");
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Duplicado"){
    fAlert("Registro existente!");
    //fDisabled(true,"iCveTramite,iCveModalidad,cDscTramite,cDscModalidad,","--");
  }
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
  if(cId == "CIDOficina" && cError == ""){
    frm.hdNumReg.value = 100000;
    if(lNuevo){
        fFillSelect(frm.iCveOficinaOrigen,aRes,false,frm.iCveOficinaOrigen.value,0,2);
    	fFillSelect(frm.iCveOficinaDestino,aRes,false,frm.iCveOficinaDestino.value,0,2);
    }
    else{
      	fFillSelect(frm.iCveOficinaOrigen,aRes,true,-1,0,2);
	fFillSelect(frm.iCveOficinaDestino,aRes,true,-1,0,2);
        lNuevo = true;
      }
    //frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   frm.hdFiltro.value = "";
   frm.hdOrden.value = " GRLOficina.CDSCOFICINA asc "
   frm.hdNumReg.value = 100000;
   lNuevo = false;
   if(fEngSubmite("pgGRLOficina.jsp","CIDOficina") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fBlanked("cDscTramite,cDscModalidad,iCveTramite,iCveModalidad,");
     fDisabled(false,"iCveTramite,iCveModalidad,cDscTramite,cDscModalidad,","--");
     FRMListado.fSetDisabled(true);
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
   frm.lDiasNaturalesTrasladoBOX.checked==true?frm.lDiasNaturalesTraslado.value=1:frm.lDiasNaturalesTraslado.value=0;
   //fAlert("" + frm.iCveOficinaOrigen.value + " 2.- " + frm.iCveOficinaDestino.value );
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
   frm.lDiasNaturalesTrasladoBOX.checked==true?frm.lDiasNaturalesTraslado.value=1:frm.lDiasNaturalesTraslado.value=0;
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
   frm.hdFiltro.value = "";
   //if(fEngSubmite("pgGRLOficina.jsp","CIDOficina") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fDisabled(false,"iCveTramite,iCveModalidad,cDscTramite,cDscModalidad,iCveOficinaOrigen,iCveOficinaDestino,","--");
     fDisabled(true,"iNumDiasTraslado,lDiasNaturalesTrasladoBOX,");
     FRMListado.fSetDisabled(true);

   //}
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
   FRMListado.focus();
}
// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
   fAsignaSelect(frm.iCveOficinaOrigen,aDato[2],aDato[9]);
   fAsignaSelect(frm.iCveOficinaDestino,aDato[3],aDato[10]);
   frm.dtIniVigencia.value = aDato[4];
   frm.iNumDiasTraslado.value = aDato[5];
    fAsignaCheckBox(frm.lDiasNaturalesTrasladoBOX,aDato[6]);
    //frm.cDscTramite.value = aDato[7];
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   if(frm.iCveOficinaOrigen.value == frm.iCveOficinaDestino.value){
        fAlert("No puedes seleccionar la misma oficina en origen y destino");
        return false;
   }
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

function fSetMod(iCveModalidad,cDscMod){
   frm.cDscModalidad.value = cDscMod;
   frm.iCveModalidad.value = iCveModalidad;
   if(frm.iCveTramite.value == -1 || frm.iCveTramite.value == ""){}
   else
     fNavega();
}
function fSetTra(iCveTramite,cDscTra){
   frm.iCveTramite.value = iCveTramite;
   frm.cDscTramite.value = cDscTra;
   if(frm.iCveModalidad.value == -1 || frm.iCveModalidad.value == ""){}
   else
     fNavega();
}
function fSetValores(iCveTramite,iCveModalidad){
    frm.iCveModalidad.value = iCveModalidad;
    frm.iCveTramite.value = iCveTramite;
    fNavega();
    /*
    //frm.cDscTramite.value = cDscTramite;
    //frm.cDscModalidad.value = cDscModalidad;
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
     }*/
}

// MetaCD=1.0
// Title: pg111010095.js
// Description: JS "Cat�logo" de la entidad GRLTipoRespuesta
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Leopoldo Beristain Gonz�lez
 var cTitulo = "";
 var FRMListado = "";
 var frm;

 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010095.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }

 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   JSSource("./ValidaTxtFormatos.js");
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro95","95%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado95","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              TDEtiCampo(true,"EEtiqueta",0," Clave:","iCveTipoRespuesta","",3,3," Clave","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Descripci�n:","cDscTipoRespuesta","",50,50," Descripci�n","fMayus(this);");
           FITR();
              TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente");
              Hidden("lVigente","");
              Hidden("iCveFormatoAux","");
              Hidden("cTituloAux","");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel95","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }

 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel95");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado95");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo(" Clave, Descripci�n,Vigente,");
   FRMListado.fSetAlinea("center,left,center,");
   FRMListado.fSetDespliega("texto,texto,logico,");
   FRMListado.fSetCampos("0,1,2,");
   FRMListado.fSetCol(1,"left");
   FRMFiltro = fBuscaFrame("IFiltro95");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra(" Clave,iCveTipoRespuesta, Descripci�n,cDscTipoRespuesta,");
   FRMFiltro.fSetOrdena(" Clave,iCveTipoRespuesta, Descripci�n,cDscTipoRespuesta,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   //fNavega();
 }

 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
   return fEngSubmite("pgGRLTipoRespuesta.jsp","Listado");
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

   // Monitoreo de Errores realacionados a SQL y la Base
   if(cError=="Datos")
     fAlert("Existe un conflicto de Datos.");

   if(cError!="" && cError!="Datos")
     FRMFiltro.fSetNavStatus("Record");

   // Obtengo el Listado para mostrar en la pantalla
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
    fDisabled(false,"iCveTipoRespuesta,","--");
    FRMListado.fSetDisabled(true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){
    // Obtener valor de Vigencia
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
    // Obtener valor de Vigencia
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
    fDisabled(false,"iCveTipoRespuesta,");
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
    if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){
       fNavega();
    }
 }

 // LLAMADO desde el Listado cada vez que se selecciona un rengl�n
 function fSelReg(aDato){
    frm.iCveTipoRespuesta.value = aDato[0];
    frm.cDscTipoRespuesta.value = aDato[1];
    fAsignaCheckBox(frm.lVigenteBOX,aDato[2]);
 }

 // FUNCI�N donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    cMsg = fValElements();

    // Valida Descripci�n de Tipo Respuesta
    if(cMsg != ""){
       fAlert(cMsg);
       return false;
    }
    return true;
 }

 // Imprimir Pantalla
 function fImprimir(){
    self.focus();
    window.print();
 }

 // Establece Valor de CveFormato y Titulo por Default
 function fSetVarIni(iCveFormato,cTitulo){
    frm.iCveFormatoAux.value = iCveFormato;
    frm.cTituloAux.value = cTitulo;
    fNavega();
 }

 // Obtener variable de iCveFormatoAux
 function fGetCveFormatoAux(){
   return frm.iCveFormatoAux.value;
 }

 // Obtener variable de cTituloAux
 function fGetTituloAux(){
   return frm.cTituloAux.value;
 }


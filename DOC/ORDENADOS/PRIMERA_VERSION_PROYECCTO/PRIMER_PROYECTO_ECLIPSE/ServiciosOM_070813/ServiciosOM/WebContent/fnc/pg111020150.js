// MetaCD=1.0
 // Title: pg111020150.js
 // Description: JS "Catálogo" de la entidad GRLFolioXSegtoEnt
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020150.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       fTituloEmergente(cTitulo);
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
              TDEtiCampo(true,"EEtiqueta",0,"Número de Oficio Referenciado:","cNumOficioRefExterna","",20,20,"NumOfPartesRefExterna","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Número de Oficialía de Partes Referenciado:","cNumOfPartesRefExterna","",20,20,"NumOficioRefExterna","fMayus(this);");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
         TextoSimple("<br>");
         TextoSimple("<br>");
	  Liga("Regresar","fRegresar();","Regresa a la pantalla anterior");
       FTDTR();
       Hidden("iCveSegtoEntidad");
       Hidden("hdCveSegtoEntidad");
       Hidden("hdConsecutivoSegtoRef");
       Hidden("Temp");
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
   FRMListado.fSetTitulo("Num. Oficialía Partes Referenciado, Num. Oficio Referenciado,");
   FRMListado.fSetCampos("4,3,");
   FRMListado.fSetAlinea("center,center,");
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
//   FRMFiltro.fSetFiltra("ClaveSegtoEntidad,iCveSegtoEntidad,ConsecutivoSegtoReferencia,iConsecutivoSegtoRef,");
//   FRMFiltro.fSetOrdena("ClaveSegtoEntidad,iCveSegtoEntidad,ConsecutivoSegtoReferencia,iConsecutivoSegtoRef,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fRecibeValores();
   fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  " iCveSegtoEntidad = "+frm.iCveSegtoEntidad.value+" and LFOLIOREFERENINTERNO = 1 and IEJERCICIOFOLIO is null and CNUMOFICIOREFEXTERNA is not null";
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgGRLFolioXSegtoEntA.jsp","Listado");
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
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
     if (frm.Temp.value!="")fReposicionaListado(FRMListado,"3", frm.Temp.value);
     else fReposicionaListado(FRMListado,"3", 0);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveSegtoEntidad,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
   frm.Temp.value = frm.cNumOfPartesRefExterna.value;
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
   frm.Temp.value = frm.cNumOfPartesRefExterna.value;
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
    fDisabled(false,"iCveSegtoEntidad,");
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
    frm.hdCveSegtoEntidad.value = aDato[0];
    frm.hdConsecutivoSegtoRef.value = aDato[1];
    frm.cNumOfPartesRefExterna.value = aDato[3];
    frm.cNumOficioRefExterna.value = aDato[4];

 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
   cMsg = fValElements("cNumOficioRefExterna,");
   if(fEvaluaCampo(frm.cNumOficioRefExterna.value)==false)          //evalua los parametros que son aceptados en el
   cMsg = cMsg+"\n Parametro incorrecto Descripción";

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

 function fRecibeValores(){
  frm.iCveSegtoEntidad.value = top.opener.fGetCveSegtoEntidad();
  fNavega();
}

function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if ( fRaros(cVCadena)       ||
         fSignos(cVCadena)      ||
         fArroba(cVCadena)       || fParentesis(cVCadena)  ||
         fGuionBajo(cVCadena)   ||  fComa(cVCadena) ||
         fEspacio(cVCadena))
        return false;
    else
        return true;
 }

  function fRegresar(){
   top.close();
}

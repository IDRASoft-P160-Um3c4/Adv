// MetaCD=1.0
 // Title: pg110000072.js
 // Description: JS "Catálogo" de la entidad GRLReporte
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ijimenez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110000072.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
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
         IFrame("IFiltro71","80%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("iListado71","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",6,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              TDEtiCampo(true,"EEtiqueta",0,"Clave sistema:","iCveSistema","",3,3,"ClaveSistema","fMayus(this);");
           //FITR();
              //TDEtiCampo(true,"EEtiqueta",0,"Módulo: ","iCveModulo","",30,30,"ClaveModulo","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Módulo:","iCveModulo","","Módulo","fMayus(this);");
           //FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Consecutivo:","iNumReporte","",3,3,"Consecutivo","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Nombre :","cNomReporte","",80,150,"Nombre","fMayus(this);","","","","EEtiquetaL", "5");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Excel:","lExcelBOX","1",true,"Excel");
              Hidden("lExcel","");
           //FITR();
              TDEtiCheckBox("EEtiqueta",0,"Word:","lWordBOX","1",true,"Word");
              Hidden("lWord","");
           //FITR();
              TDEtiCheckBox("EEtiqueta",0,"PDF:","lPDFBOX","1",true,"PDF");
              Hidden("lPDF","");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Plantilla Excel:","cPlantillaExcel","",30,30,"Plantilla Excel","","","","","EEtiquetaL", "1");
           //FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Plantilla Word:","cPlantillaWord","",30,30,"Plantilla Word","","","","","EEtiquetaL", "1");
           //FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Plantilla PDF:","cPlantillaPDF","",30,30,"Plantilla PDF","","","","","EEtiquetaL", "1");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Método Excel:","cMetodoExcel","",30,50,"Método Excel","","","","","EEtiquetaL", "1");
           //FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Método Word:","cMetodoWord","",30,50,"Método Word","","","","","EEtiquetaL", "1");
           //FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Método PDF:","cMetodoPDF","",30,50,"Método PDF","");
           FITR();
           TDEtiCheckBox("EEtiqueta",0,"Multipart:","lMultiPartBOX","1",true,"Multipart");
              Hidden("lMultiPart","");
           //FITR();
           TDEtiCampo(false,"EEtiqueta",0,"DAO Ejecutar:","cDAOEjecutar","",30,30,"DAO Ejecutar","","","","","EEtiquetaL", "1");
           //FITR();
              //TDEtiCampo(true,"EEtiqueta",0,"Clave:","iCveFormatoFiltro","",3,3,"Clave","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Formato:","iCveFormatoFiltro","","Clave","fMayus(this);");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"AutoImprimir:","lAutoImprimirBOX","1",true,"AutoImprimir");
              Hidden("lAutoImprimir","");
           //FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Número Copias:","iNumCopias","",3,3,"Número Copias","fMayus(this);");
           //FITR();
              TDEtiCheckBox("EEtiqueta",0,"Mostrar Aplicación:","lMostrarAplicacionBOX","1",true,"Mostrar Aplicación");
              Hidden("lMostrarAplicacion","");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Parámetro bModificable:","lParametroModificableBOX","1",true,"Parámetro Modificable");
              Hidden("lParametroModificable","");
           //FITR();
              TDEtiCheckBox("EEtiqueta",0,"Requiere Folio:","lRequiereFolioBOX","1",true,"Requiere Folio");
              Hidden("lRequiereFolio","");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("iPanel71","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
     Hidden("hdLlave","");
     Hidden("hdSelect","");
     Hidden("iClaveSistema","");
     Hidden("iClaveModulo","");
     Hidden("lBusqueda");

   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("iPanel71");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("iListado71");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Clave Sistema,Nombre del Módulo,Nombre Reporte,  Reporte Excel, Reporte Word, Reporte PDF, Multipart, ");
   FRMListado.fSetCampos("0,21,3,4,5,6,7,");
   FRMListado.fSetAlinea("center,left,left,center,center,center,center,");
   FRMListado.fSetDespliega("texto,texto,texto,logico,logico,logico,logico,");
   FRMFiltro = fBuscaFrame("IFiltro71");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Clave Sistema,GRLReporte.iCveSistema,Consecutivo Módulo,GRLReporte.iCveModulo,");
   FRMFiltro.fSetOrdena("Clave Sistema,iCveSistema,Consecutivo Módulo,iCveModulo,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   //fNavega();
   //fSetFiltro(11,0, 1);
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   fCargaFormato();
   fCargaModulos();
   //alert(frm.lBusqueda.value);
   if(frm.lBusqueda.value == 1){
       //alert("carga");
      frm.hdFiltro.value = " GRLReporte.iCveSistema = "+frm.iClaveSistema.value + " AND GRLReporte.iCveModulo = " + frm.iClaveModulo.value ;
      frm.hdOrden.value =  FRMFiltro.fGetOrden();
      frm.lBusqueda.value=0;

   }

   else{
          //alert("busqueda");
       frm.hdFiltro.value = FRMFiltro.fGetFiltro();
   }   frm.hdOrden.value =  FRMFiltro.fGetOrden();


   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgGRLReporte1A.jsp","Listado");
 }

  function fNavega2(){
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgGRLReporte1A.jsp","Listado");
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
   }

   if(cId == "GRLModulo" && cError==""){
     fFillSelect(frm.iCveModulo,aRes,false,frm.iCveModulo.value,1,2);
   }
   if(cId == "GRLFormato" && cError==""){
     fFillSelect(frm.iCveFormatoFiltro,aRes,false,frm.iCveFormatoFiltro.value,0,1);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
     fDisabled(false,"iCveSistema,iNumReporte,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
  frm.lExcelBOX.checked==true?frm.lExcel.value=1:frm.lExcel.value=0;
  frm.lWordBOX.checked==true?frm.lWord.value=1:frm.lWord.value=0;
  frm.lPDFBOX.checked==true?frm.lPDF.value=1:frm.lPDF.value=0;
  frm.lMultiPartBOX.checked==true?frm.lMultiPart.value=1:frm.lMultiPart.value=0;
  frm.lAutoImprimirBOX.checked==true?frm.lAutoImprimir.value=1:frm.lAutoImprimir.value=0;
  frm.lMostrarAplicacionBOX.checked==true?frm.lMostrarAplicacion.value=1:frm.lMostrarAplicacion.value=0;
  frm.lParametroModificableBOX.checked==true?frm.lParametroModificable.value=1:frm.lParametroModificable.value=0;
  frm.lRequiereFolioBOX.checked==true?frm.lRequiereFolio.value=1:frm.lRequiereFolio.value=0;

    //if(fValidaTodo()==true){
       if(fNavega2()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    //}
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
  frm.lExcelBOX.checked==true?frm.lExcel.value=1:frm.lExcel.value=0;
  frm.lWordBOX.checked==true?frm.lWord.value=1:frm.lWord.value=0;
  frm.lPDFBOX.checked==true?frm.lPDF.value=1:frm.lPDF.value=0;
  frm.lMultiPartBOX.checked==true?frm.lMultiPart.value=1:frm.lMultiPart.value=0;
  frm.lAutoImprimirBOX.checked==true?frm.lAutoImprimir.value=1:frm.lAutoImprimir.value=0;
  frm.lMostrarAplicacionBOX.checked==true?frm.lMostrarAplicacion.value=1:frm.lMostrarAplicacion.value=0;
  frm.lParametroModificableBOX.checked==true?frm.lParametroModificable.value=1:frm.lParametroModificable.value=0;
  frm.lRequiereFolioBOX.checked==true?frm.lRequiereFolio.value=1:frm.lRequiereFolio.value=0;

    //if(fValidaTodo()==true){
       if(fNavega2()==true){

         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    //}
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveSistema,");
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
       fNavega2();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.iCveSistema.value = aDato[0];
    frm.iCveModulo.value = aDato[1];
    frm.iNumReporte.value = aDato[2];
    frm.cNomReporte.value = aDato[3];
    frm.lExcel.value = aDato[4];
     if(frm.lExcel.value == 1)
          frm.lExcelBOX.checked=true;
     else
          frm.lExcelBOX.checked=false;
    frm.lWord.value = aDato[5];
    if(frm.lWord.value == 1)
          frm.lWordBOX.checked=true;
     else
          frm.lWordBOX.checked=false;
    frm.lPDF.value = aDato[6];
    if(frm.lPDF.value == 1)
          frm.lPDFBOX.checked=true;
     else
          frm.lPDFBOX.checked=false;
    frm.lMultiPart.value = aDato[7];
    if(frm.lMultiPart.value == 1)
          frm.lMultiPartBOX.checked=true;
     else
          frm.lMultiPartBOX.checked=false;
    frm.cPlantillaExcel.value = aDato[8];
    frm.cPlantillaWord.value = aDato[9];
    frm.cPlantillaPDF.value = aDato[10];
    frm.cDAOEjecutar.value = aDato[11];
    frm.cMetodoExcel.value = aDato[12];
    frm.cMetodoWord.value = aDato[13];
    frm.cMetodoPDF.value = aDato[14];
    frm.iCveFormatoFiltro.value = aDato[15];
    frm.lAutoImprimir.value = aDato[16];
    if(frm.lAutoImprimir.value == 1)
          frm.lAutoImprimirBOX.checked=true;
     else
          frm.lAutoImprimirBOX.checked=false;

    frm.iNumCopias.value = aDato[17];
    frm.lMostrarAplicacion.value = aDato[18];
    if(frm.lMostrarAplicacion.value == 1)
          frm.lMostrarAplicacionBOX.checked=true;
     else
          frm.lMostrarAplicacionBOX.checked=false;

    frm.lParametroModificable.value = aDato[19];
    if(frm.lParametroModificable.value == 1)
          frm.lParametroModificableBOX.checked=true;
     else
          frm.lParametroModificableBOX.checked=false;

    frm.lRequiereFolio.value = aDato[20];
    if(frm.lRequiereFolio.value == 1)
          frm.lRequiereFolioBOX.checked=true;
     else
          frm.lRequiereFolioBOX.checked=false;

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

  function fCargaModulos(){
      frm.hdLlave.value =  " iCveSistema,iCveModulo ";
      frm.hdSelect.value =  " Select iCveSistema,iCveModulo,cDscModulo,lActivo from GRLModulo ";
    frm.hdNumReg.value = 100;
    fEngSubmite("pgConsulta.jsp","GRLModulo");
 }

  function fCargaFormato(){
      frm.hdLlave.value =  " iCveFormato, ";
      frm.hdSelect.value =  " Select ICVEFORMATO,CDSCFORMATO,DTINIVIGENCIA,CTABLABUSCA,CCAMPOLLAVEBUSCA,INUMCOLUMNAS from GRLFormato ";
    frm.hdNumReg.value = 100;
    fEngSubmite("pgConsulta.jsp","GRLFormato");
 }

   function fSetFiltro(iCveSistema, iCveModulo, lBusqueda){
     frm.iClaveSistema.value = iCveSistema;
     frm.iClaveModulo.value =   iCveModulo;
     frm.lBusqueda.value = lBusqueda;
     //alert(frm.lBusqueda.value);
     fNavega();
 }

 function fDeshabilitar(){
 alert("hello");
 fDisabled(true,"cPlantillaExcel")

 }


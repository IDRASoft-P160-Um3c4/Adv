// MetaCD=1.0
 // Title: pg110000020.js
 // Description: JS "Catálogo" de la entidad GRLReporte
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ABarrientos<dd>Rafael Miranda Blumenkron
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var Modulos = "";
 var Reportes = "";
 var iModulos = 0;
 var iReporte = 0;
 var cFiltroXReporte = "";
 var aResReportes, aReporteSelected;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110000020.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"GENERACIÓN DE REPORTES":cTitulo;
   fSetWindowTitle();
 }

 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("ETablaST",0,"100%","","","",0);
     ITRTD("ESTitulo",0,"","","center");
       fTituloEmergente(cTitulo);
       //TextoSimple(cTitulo);
     FITD("ESTitulo",0,"","","center");
       fDefProcesoActual(false,false,false,"","");
     FTD();
     FTR();
   FinTabla();
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","2","center","top");
         IFrame("IFiltro","0%","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","115","center","top");
         IFrame("IListado","95%","110","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",6,"","","center");
             TextoSimple("Reporte Seleccionado");
           FTDTR();
           ITR();
              //TDEtiCampo(false,"EEtiqueta",0,"Sistema:","iCveSistema","",3,3,"ClaveSistema","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Módulo:","cModulo","",73,73,"cModulo","fMayus(this);","","",false,"ECampo",5);
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Reporte:","cNomReporte","",73,73,"Reporte","fMayus(this);","","",false,"ECampo",5);
           FTR();
         FinTabla();
      FTDTR();
       ITRTD("",0,"","","center");
         InicioTabla("",0,"30%","","","",0);
           ITR();
              //TDEtiCheckBox("EEtiqueta",0,"Excel:","lExcelBOX","1",true,"Excel");
             ITD("EEtiquetaC");
              BtnImg("Excel","excel","fGeneraExcel();", "",true,"03");
             FITD("EEtiquetaC");
              BtnImg("Word","word","fGeneraWord();", "",true,"03");
             FITD("EEtiquetaC");
              BtnImg("PDF","pdf","fGeneraPDF();","",true,"03");
             FTD();
           FTR();
         FinTabla();
      FTDTR();
      ITRTD("",0,"","0","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",6,"","","center");
             TextoSimple("Configuración del reporte");
           FTDTR();
           ITR();
              TDEtiCheckBox("EEtiqueta",0,"Auto Imprimir:","lAutoImprimirBOX","1",true,"AutoImprimir","","","fAsignaImprimir();");
              Hidden("lAutoImprimir","");
              TDEtiCampo(false,"EEtiqueta",0,"Número de Copias:","iNumCopias","",3,3,"Número de Copias","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Nombre Archivo Destino:","cNomDestino","",51,50,"Nombre del archivo destino","","","fOnChangeDestino(this.value);",false,"ECampo",5);
           FTR();
         FinTabla();
       FTDTR();
       ITRTD("",0,"","0","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",2,"","","center");
             TextoSimple("Asignación de Folio");
           FTDTR();
           ITR();
              TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","fTraeAreas();");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDepartamento","fTraeDigitosFolio();");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Firmante por Ausencia:","iCveFirmante","");
           FTR();
         FinTabla();
       FTDTR();
         if (top.fGetUsrID()){
           Hidden("iCveUsuario",top.fGetUsrID());
         }
         else{
           Hidden("iCveUsuario",1);
         }
       FinTabla();
       Hidden("iCveSistema","");
       Hidden("iCveModulo","");
       Hidden("iNumReporte","");
       Hidden("lMultiPart","");
       Hidden("cPlantillaExcel","");
       Hidden("cPlantillaWord","");
       Hidden("cPlantillaPDF","");
       Hidden("cDAOEjecutar","");
       Hidden("cMetodoExcel","");
       Hidden("cMetodoWord","");
       Hidden("cMetodoPDF","");
       Hidden("iCveFormatoFiltro","");
       Hidden("lMostrarAplicacion","");
       Hidden("lParametroModificable","");
       Hidden("lExcel","");
       Hidden("lWord","");
       Hidden("lPDF","");
       Hidden("cMetodoTemp","");
       Hidden("cArchivoOrig","");
       Hidden("hdFiltrosRep","");
       Hidden("lRequiereFolio","");
       Hidden("cDigitosFolio","");
       Hidden("cFirmante");
       Hidden("iNumSolicitud");
       Hidden("iEjercicioSol");
     FTDTR();
       ITRTD("",0,"","0","center","bottom");
         IFrame("IPanel","0%","0","Paneles.js");
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
   FRMListado.fSetTitulo("Módulo,Nombre del Reporte,");
   FRMListado.fSetCampos("21,3,");
   FRMListado.fSetDespliega("texto,texto,");
   FRMListado.fSetAlinea("left,left,");
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Módulo,GRLReporte.iCveModulo,");
   FRMFiltro.fSetOrdena("Modulo,GRLReporte.iCveModulo,");
   fDisabled(false);
   fDisabled(true,"cNomDestino,");
   frm.hdBoton.value="Primero";

   //Obtiene de la pantalla que abra a esta los modulos y reportes a los que tiene permiso,
   //tambien obtiene el filtro o query que se vaya a ejecutar para obtener la información del reporte o documento word
   Modulos         = top.opener.cClavesModulo;
   Reportes        = top.opener.cNumerosRep;
   cFiltroXReporte = top.opener.cFiltrosRep;

   if(top.opener){
     if(top.opener.fSetHandleReportes)
       top.opener.fSetHandleReportes(window);
     
     if (top.opener.fGetNumsol)
         frm.iNumSolicitud.value = top.opener.fGetNumsol(); 
     
     if (top.opener.fGetiEjercicio)
    	 frm.iEjercicioSol.value = top.opener.fGetiEjercicio();  
   }

   fTraeOficinas();
   fNavega();
}

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
	//var cFiltro=" GRLReporte.ICVESISTEMA=" + iNDSADM + " AND (";
	 var cFiltro=" GRLReporte.ICVESISTEMA=" + iIDSistema + " AND (";
	 
   iModulos = parseInt(fNumEntries(Modulos,','),10);

   //Arma el filtro de acuerdo a los módulos y reportes q se obtienen de las
   //variables cClavesModulo y  cNumerosRep
   for (i=0;i<iModulos;i++){
     cFiltro+= " (GRLReporte.iCveModulo = " + fEntry(i+1,Modulos,",") +
               " AND GRLReporte.iNumReporte = " + fEntry(i+1,Reportes,",") + ") ";

     if( i < iModulos - 1 )
       cFiltro+=" OR ";

   }

   frm.hdFiltro.value =  cFiltro + ") ";
   frm.hdOrden.value =  " GRLREPORTE.ICVESISTEMA, GRLReporte.iCveModulo, GRLREPORTE.INUMREPORTE ";
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgGRLReporte.jsp","Listado");
 }

 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError!=""){
     fAlert(cError);
     FRMFiltro.fSetNavStatus("Record");
   }
   if (cId == "CIdDocCargado"){
     if (top.opener)
       if (top.opener.fReporteEjecutado)
         top.opener.fReporteEjecutado(top, aResReportes, aReporteSelected, frm.hdFiltrosRep.value, cId, cError);
   }

   if(cId == "Listado" && cError==""){
     aResReportes = fCopiaArreglo(aRes);
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

   if(cId == "IDOficina" && cError == ""){
    fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value);
   }

   if(cId == "IDDepto" && cError == ""){
    fFillSelect(frm.iCveDepartamento,aRes,true,frm.iCveDepartamento.value,1,3);
   }


   if(cId == "IDDigitos" && cError == ""){
     if(aRes[0] && aRes[0][0] && aRes[0][0]+'' != 'undefined' && aRes[0][0]+'' != ''){
       frm.cDigitosFolio.value = aRes[0][0];
       if(parseInt(frm.iCveOficina.value,10)>0 && parseInt(frm.iCveDepartamento.value,10)>0){
         frm.hdFiltro.value = "iCveOficina = "+frm.iCveOficina.value+
                              " AND iCveDepartamento="+frm.iCveDepartamento.value+
                              " AND lFirmante=1 ";
         frm.hdOrden.value = "cNom";
         fEngSubmite("pgGRLUsuarioXOficB1.jsp","cIdFirmantes");
       }
     }
     else{
         fAlert("El Departamento que seleccionó no tiene asignado el prefijo del folio");
         fSelectSetIndexFromValue(frm.iCveDepartamento, -1);
         frm.cDigitosFolio.value = "";
         return false;
     }
   }
   if(cId == "cIdFirmantes" && cError == ""){
     fFillSelect(frm.iCveFirmante,aRes,true,frm.iCveFirmante.value,2,5);
   }

 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");
    fDisabled(false);
    fDisabled(true,"cNomDestino,");
    FRMListado.fSetDisabled(false);
 }

 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
   aReporteSelected = fCopiaArreglo(aDato);
    frm.iCveSistema.value = aDato[0];
    frm.iCveModulo.value  = aDato[1];
    frm.iNumReporte.value = aDato[2];
    frm.cNomReporte.value = aDato[3];
    frm.lExcel.value      = aDato[4];
    frm.lWord.value       = aDato[5];
    frm.lPDF.value        = aDato[6];
    frm.lMultiPart.value            = aDato[7];
    frm.cPlantillaExcel.value       = aDato[8];
    frm.cPlantillaWord.value        = aDato[9];
    frm.cPlantillaPDF.value         = aDato[10];
    frm.cDAOEjecutar.value          = aDato[11];
    frm.cMetodoExcel.value          = aDato[12];
    frm.cMetodoWord.value           = aDato[13];
    frm.cMetodoPDF.value            = aDato[14];
    frm.iCveFormatoFiltro.value     = aDato[15];
    frm.lAutoImprimir.value         = aDato[16];
    frm.iNumCopias.value            = aDato[17];
    frm.lMostrarAplicacion.value    = aDato[18];
    frm.lParametroModificable.value = aDato[19];
    frm.cModulo.value               = aDato[21];
    frm.lRequiereFolio.value        = aDato[25];

    //Activa la imagen correspondiente si el reporte esta configurado para el formato
    if (frm.lExcel.value == 1) fSrc(frm.Excel,'1'); else fSrc(frm.Excel,'3');

    if (frm.lWord.value == 1) fSrc(frm.Word,'1'); else fSrc(frm.Word,'3');

    if (frm.lPDF.value == 1) fSrc(frm.PDF,'1'); else fSrc(frm.PDF,'3');

    if (parseInt(frm.lAutoImprimir.value,10)==1)
      frm.lAutoImprimirBOX.checked=true;
    if (parseInt(frm.lAutoImprimir.value,10)==0)
      frm.lAutoImprimirBOX.checked=false;

    if ( parseInt(frm.lParametroModificable.value,10) ==1 ){
      fDisabled(false);
      fDisabled(true,"lAutoImprimirBOX,iNumCopias,cNomDestino,");
    }
    else
      fDisabled(true);

   iModulos = parseInt(fNumEntries(Modulos,','),10);

   for (i=0;i<iModulos;i++){
     if ( parseInt(frm.iCveModulo.value,10) == parseInt(fEntry(i+1,Modulos,","),10) &&
          parseInt(frm.iNumReporte.value,10) == parseInt(fEntry(i+1,Reportes,","),10) )
        frm.hdFiltrosRep.value = fEntry(i+1,cFiltroXReporte,"$") ;
   }

    if ( parseInt(frm.lRequiereFolio.value,10)==1){
      frm.iCveOficina.disabled = false;
      frm.iCveDepartamento.disabled = false;
      frm.iCveFirmante.disabled = false;
    }
    else{
      frm.iCveOficina.disabled = true;
      frm.iCveDepartamento.disabled = true;
      frm.iCveFirmante.disabled = true;
    }

  if(top.opener)
    if(top.opener.fSelRegReporte)
      top.opener.fSelRegReporte(frm.iCveSistema.value, frm.iCveModulo.value, frm.iNumReporte.value,
                                frm.cPlantillaExcel.value, frm.cPlantillaWord.value, frm.cPlantillaPDF.value);

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

 //Función para generar reporte en formato excel
 function fGeneraExcel(){
   //Asigna a hdBoton Excel para que pgReporteGeneral.jsp reconozca que reporte va a generar
   frm.hdBoton.value = "Excel";
   if (fValidaRequiereFolio()){
     if (parseInt(frm.lExcel.value,10)==1){
       if(!confirm(cAlertMsgGen + "\n\n ¿Está seguro de querer ejecutar el reporte?"))
         return;
       frm.cMetodoTemp.value  = frm.cMetodoExcel.value;
       frm.cArchivoOrig.value = frm.cPlantillaExcel.value;
       fEngSubmite("pgReporteGeneral.jsp","Reporte");
     }
     else
       return;
   }
 }

 //Función para generar reporte en formato word
 function fGeneraWord(){
   //Asigna a hdBoton Word para que pgReporteGeneral.jsp reconozca que reporte va a generar
   frm.hdBoton.value = "Word";
   
	if( (frm.iNumSolicitud.value != null) && (frm.iEjercicioSol.value != null)){ //Generar word desde plantillas XML (Ej. Agentes Navieros, etc)
	
		if( (frm.iNumSolicitud.value != "") && (frm.iEjercicioSol.value != "")){
	       //alert("Generacion de doc. Agente Naviero");
		 frm.hdBoton.value = "WordXML";
		}
	}
		    
   if(fValidaRequiereFolio()){
     if(parseInt(frm.lWord.value,10)==1){
       if(!confirm(cAlertMsgGen + "\n\n ¿Está seguro de querer ejecutar el reporte?"))
         return;
       frm.cMetodoTemp.value = frm.cMetodoWord.value;
       frm.cArchivoOrig.value = frm.cPlantillaWord.value;
       if(parseInt(frm.iCveFirmante.value,10)>0)
         frm.cFirmante.value = frm.iCveFirmante.options[frm.iCveFirmante.selectedIndex].text;
       else frm.cFirmante.value = "";
       fEngSubmite("pgReporteGeneral.jsp","Reporte");
     }
     else
       return;
   }
 }

 //Función para generar reporte en formato pdf
 function fGeneraPDF(){
   //Asigna a hdBoton PDF para que pgReporteGeneral.jsp reconozca que reporte va a generar
   frm.hdBoton.value = "PDF";
   if (fValidaRequiereFolio()){
     if (parseInt(frm.lPDF.value,10)==1){
       if(!confirm(cAlertMsgGen + "\n\n ¿Está seguro de querer ejecutar el reporte?"))
         return;
       frm.cMetodoTemp.value = frm.cMetodoPDF.value;
       frm.cArchivoOrig.value = frm.cPlantillaPDF.value;
       fEngSubmite("pgReporteGeneral.jsp","Reporte");
     }
     else
       return;
   }
 }

 function fAsignaImprimir(){
   if (frm.lAutoImprimirBOX.checked){
     frm.lAutoImprimir.value=1;
   }
   else{
     frm.lAutoImprimir.value=0;
   }
 }

 //Función para cambiar la imagen cuando el puntero del mouse esta sobre esta
 function fMouseOver(parDocument, parObjeto){
   if (parObjeto == "excel"){
     if (frm && frm.lExcel)
       if (frm.lExcel.value ==1) fSrc(frm.Excel,"2"); else fSrc(frm.Excel,"3");
   }
   if (parObjeto == "word"){
     if (frm && frm.lWord)
       if (frm.lWord.value ==1) fSrc(frm.Word,"2"); else fSrc(frm.Word,"3");
   }
   if (parObjeto == "pdf"){
     if (frm && frm.lPDF)
       if(frm.lPDF.value ==1) fSrc(frm.PDF,"2"); else fSrc(frm.PDF,"3");
   }
 }

 //Función para cambiar la imagen cuando el puntero del mouse salga del área de esta
 function fMouseOut(parDocument, parObjeto){
   if (parObjeto == "excel"){
     if (frm && frm.lExcel)
       if (frm.lExcel.value ==1) fSrc(frm.Excel,"1"); else fSrc(frm.Excel,"3");
   }
   if (parObjeto == "word"){
     if (frm && frm.lWord)
       if (frm.lWord.value ==1) fSrc(frm.Word,"1"); else fSrc(frm.Word,"3");
   }
   if (parObjeto == "pdf"){
     if (frm && frm.lPDF)
       if (frm.lPDF.value ==1) fSrc(frm.PDF,"1"); else fSrc(frm.PDF,"3");
   }
 }

  function fTraeOficinas(){
   frm.hdNumReg.value = 1000;
   frm.hdBoton.value = "Oficinas";
   frm.hdOrden.value  = "";
   frm.hdFiltro.value = " GRLUSUARIOXOFIC.ICVEUSUARIO = " + frm.iCveUsuario.value;
   fEngSubmite("pgGRLUsuarioXOfice.jsp","IDOficina");

 }

 function fTraeAreas(){
   frm.hdBoton.value = "Deptos";
   frm.hdNumReg.value = 1000;
   if(frm.iCveOficina.value != "" && parseInt(frm.iCveOficina.value, 10) > 0 ){
     frm.hdOrden.value =  " GRLDepartamento.cDscDepartamento ";
     frm.hdFiltro.value = " GRLUSUARIOXOFIC.ICVEOFICINA = "+frm.iCveOficina.value +
                          " AND GRLUSUARIOXOFIC.ICVEUSUARIO = "+frm.iCveUsuario.value;
   }
   else{
     fAsignaSelect(frm.iCveDepartamento,-1,"Seleccione...");
     return;
   }
   fEngSubmite("pgGRLUsuarioXOfice.jsp","IDDepto");
 }

 function fTraeDigitosFolio(){
   if(frm.iCveOficina.value != "" && parseInt(frm.iCveOficina.value, 10) >= 0 ){
     if ( frm.iCveDepartamento.value != "" && parseInt(frm.iCveDepartamento.value,10) >= 0 ){
       frm.cDigitosFolio.value = '';
       frm.hdOrden.value =  " GRLDigitosFolioXDepto.dtAsignacion DESC ";
       frm.hdFiltro.value = " GRLDigitosFolioXDepto.iCveOficina = " +frm.iCveOficina.value +
                            " AND GRLDigitosFolioXDepto.iCveDepartamento = " + frm.iCveDepartamento.value;
       fEngSubmite("pgGRLDigitosFolioXDepto.jsp","IDDigitos");
     }
     else
       fAlert("Seleccione un departamento");
   }
   else
     fAlert("Seleccione una oficina");

 }

 function fValidaRequiereFolio(){
   if ( parseInt(frm.lRequiereFolio.value,10 ) == 1 ){
     if ( parseInt(frm.iCveOficina.value,10) < 0 || parseInt( frm.iCveDepartamento.value,10) < 0 ){
       fAlert("El documento que va a imprimir requiere folio, favor de seleccionar oficina y departamento al que corresponde");
       return false;
     }
     if(frm.cDigitosFolio.value == ''){
       fAlert("El Departamento que seleccionó no tiene asignado el prefijo del folio");
       fSelectSetIndexFromValue(frm.iCveDepartamento, -1);
       return false;
     }
   }
   return true;
 }

function fOnChangeDestino(cValor){
  if(top.opener)
    if(top.opener.fChangeDestinoRegReporte)
      top.opener.fChangeDestinoRegReporte(cValor);
}

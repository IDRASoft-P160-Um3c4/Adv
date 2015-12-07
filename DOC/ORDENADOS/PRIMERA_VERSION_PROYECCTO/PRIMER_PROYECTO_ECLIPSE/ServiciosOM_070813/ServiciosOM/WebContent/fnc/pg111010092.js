// MetaCD=1.0
// Title: pg111010092.js
// Description: JS "Cat�logo" de la entidad GRLConfigFormato
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Leopoldo Beristain Gonz�lez
 var cTitulo = "";
 var FRMListado = "";
 var frm;

 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010092.js";
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
         IFrame("IFiltro92","95%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado92","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"90%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* T�tulo:");
              ITD("ECampo",6,"","","left","");
              Text(true,"cTitulo","",75,75,"T�tulo","","","fMayus(this);",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Clave de Atributo:");
              ITD("ECampo",0,"","","left","");
              Text(true,"iCveAtributo","",3,3,"Clave de Atributo","","","fMayus(this);",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Orden:");
              ITD("ECampo",0,"","","left","");
              Text(true,"iOrden","",3,3,"Orden","","","fMayus(this);",false,false);
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Rengl�n:","iRenglon","",5,4," Rengl�n","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0," Columna:","iColumna","",5,4," Columna","fMayus(this);");
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Descripci�n:");
              ITD("ECampo",6,"","","left","");
              Text(true,"cDscAtributo","",50,100,"Descripci�n","","","fMayus(this);",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Etiqueta Anterior:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cEtiquetaAnterior","",50,100,"Etiqueta Anterior","","","",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Etiqueta Posterior:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cEtiquetaPosterior","",50,100,"Etiqueta Posterior","","","",false,false);
           FITR();
              TDEtiSelect(true,"EEtiqueta",0," Tipo Respuesta:","iCveTipoRespuesta","");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Obligatorio:");
              ITD("ECampo",5,"","","left","");
              CheckBox("lObligatorioBOX",1, false,"Obligatorio","","","",false,false);
              Hidden("lObligatorio","");
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Valor Ini. Omisi�n:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cValorIniOmision","",30,30,"Valor Ini. Omisi�n","","","",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Valor Fin. Omisi�n:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cValorFinOmision","",30,30,"Valor Fin. Omisi�n","","","",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Nombre Campo en Pantalla:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cNomCampoPantalla","",30,50,"Nombre Campo en Pantalla","","","",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* JS de Validaciones:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cJSValidaciones","",20,20,"JS de Validaciones","","","",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Tabla para Mapeo:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cTablaMapeo","",30,30,"Tabla para Mapeo","","","",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Campo para Mapeo:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cCampoMapeo","",30,30,"Campo para Mapeo","","","",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* M�xima Longitud:");
              ITD("ECampo",0,"","","left","");
              Text(true,"iMaxLongitud","",3,3,"M�xima Longitud","","","fMayus(this);",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Vigente:");
              ITD("ECampo",5,"","","left","");
              CheckBox("lVigenteBOX",1, false,"Vigente","","","",false,false);
              Hidden("lVigente","");
           FITR();
              Hidden("iCveFormatoAux","");
              Hidden("cTituloAux","");
              Hidden("iTipoRespuestaFormato","");
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       InicioTabla("",0,"100%","","","",1);
         ITRTD("EEtiquetaC",0,"","40","center","bottom");
           TextoSimple("Anidados:");
           Text(false,"cVariosFormatos","",10,10,"Capture separado por comas los n�meros de formato que desea prever de forma continua","","","",false,true);
           Liga("Vista Preliminar","fPrever();","Abrir vista preliminar del formato");
         FITD("",0,"","40","center","bottom");
           IFrame("IPanel92","95%","34","Paneles.js");
         FTDTR();
        FinTabla();
     FinTabla();
   fFinPagina();
 }

 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel92");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado92");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Clave de Atributo, Descripci�n, Orden, Etiqueta Anterior, Etiqueta Posterior,");
   FRMListado.fSetCampos("2,4,3,5,6,");
   FRMListado.fSetCol(1,"left");
   FRMListado.fSetCol(3,"left");
   FRMListado.fSetCol(4,"left");
   FRMFiltro = fBuscaFrame("IFiltro92");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Clave de Atributo,iCveAtributo,Descripci�n,cDscAtributo,Orden,iOrden,Etiqueta Ant.,cEtiquetaAnterior,Etiqueta Post.,cEtiquetaPosterior");
   FRMFiltro.fSetOrdena("Clave de Atributo,iCveAtributo,Descripci�n,cDscAtributo,Orden,iOrden,Etiqueta Ant.,cEtiquetaAnterior,Etiqueta Post.,cEtiquetaPosterior");
   fDisabled(true,"cVariosFormatos,");
   frm.hdBoton.value="Primero";
   //fNavega();
 }

 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   frm.cTitulo.value = frm.cTituloAux.value;
   // Solo se mostraran los datos correspondientes al Formato seleccionado
   if(FRMFiltro.fGetFiltro()!= "")
   	frm.hdFiltro.value = FRMFiltro.fGetFiltro() + " and GRLCFMT.iCveFormato = " + frm.iCveFormatoAux.value;
   else
   	frm.hdFiltro.value = " GRLCFMT.iCveFormato = " + frm.iCveFormatoAux.value;

   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
   return fEngSubmite("pgGRLConfigFormato.jsp","Listado");
 }

 // RECEPCI�N de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave, iTipoRespuestaF){
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
     // Se carga el valor de Tipo Respuesta por Default para validar <> 5
     frm.iTipoRespuestaFormato.value = iTipoRespuestaF;
     frm.hdRowPag.value = iRowPag;
     frm.cTitulo.value =
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

   // Obtener datos para el combo de Tipo de Respuesta.
   if(cId == "CIDTipoRespuesta" && cError==""){
     fFillSelect(frm.iCveTipoRespuesta,aRes,false,frm.iCveTipoRespuesta.value,0,1);
   }
 }

 // LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
 function fNuevo(){
   frm.hdFiltro.value = "GrlTipoRespuesta.lVigente=1";
   frm.hdOrden.value = "GrlTipoRespuesta.cDscTipoRespuesta";
   if(fEngSubmite("pgGRLTipoRespuesta.jsp","CIDTipoRespuesta") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fBlanked();
     frm.cTitulo.value = frm.cTituloAux.value;
     fDisabled(false,"iCveAtributo,cTitulo,cVariosFormatos,","--");
     FRMListado.fSetDisabled(true);
   }
 }

 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){
    // Obtener valor de Vigencia y Obligatoriedad
    frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
    frm.lObligatorioBOX.checked==true?frm.lObligatorio.value=1:frm.lObligatorio.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true,"cVariosFormatos,");
          FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
 function fGuardarA(){
    // Obtener valor de Vigencia y Obligatoriedad
    frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
    frm.lObligatorioBOX.checked==true?frm.lObligatorio.value=1:frm.lObligatorio.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true,"cVariosFormatos,");
         FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
 function fModificar(){
   frm.hdFiltro.value = "GrlTipoRespuesta.lVigente=1";
   frm.hdOrden.value = "GrlTipoRespuesta.cDscTipoRespuesta";
   if(fEngSubmite("pgGRLTipoRespuesta.jsp","CIDTipoRespuesta") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     frm.cTitulo.value = frm.cTituloAux.value;
     fDisabled(false,"iCveAtributo,cTitulo,cVariosFormatos,","--");
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
    fDisabled(true,"cVariosFormatos,");
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
    frm.iCveFormatoAux.value = aDato[0];
    frm.cTituloAux.value = aDato[1];
    frm.cTitulo.value = frm.cTituloAux.value;
    frm.iCveAtributo.value = aDato[2];
    frm.iOrden.value = aDato[3];
    frm.cDscAtributo.value = aDato[4];
    frm.cEtiquetaAnterior.value = aDato[5];
    frm.cEtiquetaPosterior.value = aDato[6];
    fAsignaSelect(frm.iCveTipoRespuesta,aDato[7],aDato[8]);
    fAsignaCheckBox(frm.lObligatorioBOX,aDato[9]);
    frm.cValorIniOmision.value = aDato[10];
    frm.cValorFinOmision.value = aDato[11];
    frm.iMaxLongitud.value = aDato[12];
    frm.cNomCampoPantalla.value = aDato[13];
    frm.cTablaMapeo.value = aDato[14];
    frm.cCampoMapeo.value = aDato[15];
    frm.cJSValidaciones.value = aDato[16];
    fAsignaCheckBox(frm.lVigenteBOX,aDato[17]);
    frm.iRenglon.value = aDato[18];
    frm.iColumna.value = aDato[19];
 }

 // FUNCI�N donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    var cPref = "\n - El campo ";
    var cSuf = " tiene caracteres no v�lidos.";

    cMsg = fValElements("cDscAtributo,"+
    			"cEtiquetaAnterior,"+
			"cEtiquetaPosterior,"+
			"cValorIniOmision,"+
			"cValorFinOmision,"+
			"cNomCampoPantalla,"+
			"cJSValidaciones,"+
			"cTablaMapeo,"+
			"cCampoMapeo,");

    // Valida Descripci�n Descripci�n
    if(fValidaTextFormatos(frm.cDscAtributo) == false)
      cMsg = cMsg + cPref + "'Descripci�n'" + cSuf;

    // Valida Descripci�n Etiqueta Anterior
    if(fValidaTextFormatos(frm.cEtiquetaAnterior) == false)
      cMsg = cMsg + cPref + "'Etiqueta Anterior'" + cSuf;

    // Valida Descripci�n Etiqueta Posterior
    if(fValidaTextFormatos(frm.cEtiquetaPosterior) == false)
      cMsg = cMsg + cPref + "'Etiqueta Posterior'" + cSuf;

    // Valida Descripci�n Valor Ini. Omisi�n
    if(fValidaTextFormatos(frm.cValorIniOmision) == false)
      cMsg = cMsg + cPref + "'Valor Ini. Omisi�n'" + cSuf;

    // Valida Descripci�n Valor Fin. Omisi�n
    if(fValidaTextFormatos(frm.cValorFinOmision) == false)
      cMsg = cMsg + cPref + "'Valor Fin. Omisi�n'" + cSuf;

    // Valida Descripci�n Nombre Campo en Pantalla
    if(fValidaTextFormatos(frm.cNomCampoPantalla) == false)
      cMsg = cMsg + cPref + "'Nombre Campo en Pantalla'" + cSuf;

    // Valida Descripci�n JS de Validaciones
    if(fValidaTextFormatos(frm.cJSValidaciones) == false)
      cMsg = cMsg + cPref + "'JS de Validaciones'" + cSuf;

    // Valida Descripci�n Tabla para Mapeo
    if(fValidaTextFormatos(frm.cTablaMapeo) == false)
      cMsg = cMsg + cPref + "'Tabla para Mapeo'" + cSuf;

    // Valida Descripci�n Campo para Mapeo
    if(fValidaTextFormatos(frm.cCampoMapeo) == false)
      cMsg = cMsg + cPref + "'Campo para Mapeo'" + cSuf;

    if(cMsg != ""){
       fAlert(cMsg);
       return false;
    }
    return true;
 }

 // Imprimir Pagina
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

 // Obtener variable de iCveAtributo
 function fGetCveAtributo(){
   return frm.iCveAtributo.value;
 }

 // Obtener variable de iCveAtributo
 function fGetDscAtributo(){
   return frm.cDscAtributo.value;
 }

 // Obtener variable de cTitulo
 function fGetTitulo(){
   return frm.cTitulo.value;
 }

 // Obtener variable perteneciente a Tipo de Respuesta
 function fGetTipoRespuestaValida(){
   //if(1=1)
   if(frm.iCveTipoRespuesta.options[frm.iCveTipoRespuesta.selectedIndex].value == frm.iTipoRespuestaFormato.value)
     return true;
   else
     return false;
 }

function fPrever(){
  if(frm.cVariosFormatos.value == "")
    frm.cVariosFormatos.value = frm.iCveFormatoAux.value;
  if(parseInt(frm.cVariosFormatos.value,10) == 0){
    fAlert("Solo es posible generar vista preliminar para formatos cuyo ID sea mayor a cero");
    frm.cVariosFormatos.value = "";
  }else{
    cRutaAbrir = cRutaProg + "pgMuestraFormato.jsp?Modo=Despliega&cDscTituloFormato=SOLICITUD DE TRAMITE / VENTANILLA UNICA&iCveFormato=" + frm.cVariosFormatos.value;
    fAbreWindowHTML(true,cRutaAbrir,"Formatos","yes","yes","yes","yes","1024","768");
  }
}

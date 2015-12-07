// MetaCD=1.0
// Title: pg111010091.js
// Description: JS "Catálogo" de la entidad GRLFormato
// Company: Tecnología InRed S.A. de C.V.
// Author: Leopoldo Beristain González

 var cTitulo = "";
 var FRMListado = "";
 var frm;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010091.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }

 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   JSSource("./ValidaTxtFormatos.js");
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro91","95%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado91","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"85%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              TDEtiCampo(true,"EEtiqueta",0," Clave:","iCveFormato","",3,3," Clave","fMayus(this);");
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Descripción:");
              ITD("ECampo",6,"","","left","");
              AreaTexto(true,75,2,"cDscFormato","","Descripción","","fMayus(this);",false ,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Título:");
              ITD("ECampo",6,"","","left","");
              Text(true,"cTitulo","",75,75,"Título","","","fMayus(this);",false,false);
           FITR();
              TDEtiCampo(true,"EEtiqueta",1," Inicio de Vigencia:","dtIniVigencia","",10,10," Inicio Vigencia","fMayus(this);");
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Vigente:");
              ITD("ECampo",5,"","","left","");
              CheckBox("lVigenteBOX",1, false,"Vigente","","","",false,false);
              Hidden("lVigente","");
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Tabla para Búsqueda:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cTablaBusca","",30,30,"Tabla para Búsqueda","","","fMayus(this);",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Campo Llave en Búsqueda:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cCampoLlaveBusca","",30,30,"Campo Llave en Búsqueda","","","fMayus(this);",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Núm. Columnas Despliegue:");
              ITD("ECampo",0,"","","left","");
              Text(true,"iNumColumnas","",3,3,"Núm. Columnas Despliegue","","","fMayus(this);",false,false);
           FITR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       InicioTabla("",0,"100%","","","",1);
         ITRTD("EEtiquetaC",0,"","40","center","bottom");
           TextoSimple("Anidados:");
           Text(false,"cVariosFormatos","",10,10,"Capture separado por comas los números de formato que desea prever de forma continua","","","",false,true);
           Liga("Vista Preliminar","fPrever();","Abrir vista preliminar del formato");
         FITD("",0,"","40","center","bottom");
           IFrame("IPanel91","95%","34","Paneles.js");
         FTDTR();
        FinTabla();
     FinTabla();
   fFinPagina();
 }

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel91");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado91");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo(" Clave, Descripción, Título, Inicio de Vigencia, Tabla para Búsqueda, Campo Llave en Búsqueda, Núm. Columnas Despliegue,");
   FRMListado.fSetCampos("0,1,2,4,5,6,7,8");
   FRMListado.fSetCol(1,"left");
   FRMListado.fSetCol(2,"left");
   FRMListado.fSetCol(4,"left");
   FRMListado.fSetCol(5,"left");
   FRMFiltro = fBuscaFrame("IFiltro91");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra(" Clave,iCveFormato,Descripción,cDscFormato,Título,cTitulo,Tabla para Búsqueda,cTablaBusca,Campo Llave de Busqueda,cCampoLlaveBusca,Núm. Columnas Despliegue,iNumColumnas");
   FRMFiltro.fSetOrdena(" Clave,iCveFormato,Descripción,cDscFormato,Título,cTitulo,Tabla para Búsqueda,cTablaBusca,Campo Llave de Busqueda,cCampoLlaveBusca,Núm. Columnas Despliegue,iNumColumnas");
   fDisabled(true,"cVariosFormatos,");
   frm.hdBoton.value="Primero";
   fNavega();
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
   return fEngSubmite("pgGRLFormato.jsp","Listado");
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

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveFormato,","--");
    FRMListado.fSetDisabled(true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    // Obtener valor de Vigencia
    frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true,"cVariosFormatos,");
          FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    // Obtener valor de Vigencia
    frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true,"cVariosFormatos,");
         FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveFormato,");
    FRMListado.fSetDisabled(true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");
    fDisabled(true,"cVariosFormatos,");
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
    frm.iCveFormato.value = aDato[0];
    frm.cDscFormato.value = aDato[1];
    frm.cTitulo.value = aDato[2];
    fAsignaCheckBox(frm.lVigenteBOX,aDato[3]);
    frm.dtIniVigencia.value = aDato[4];
    frm.cTablaBusca.value = aDato[5];
    frm.cCampoLlaveBusca.value = aDato[6];
    frm.iNumColumnas.value = aDato[7];
 }

 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    var cPref = "\n - El campo ";
    var cSuf = " tiene caracteres no válidos.";

    cMsg = fValElements("cDscFormato,cTitulo,cTablaBusca,cCampoLlaveBusca,");

    // Valida Descripción Formato
    if(fValidaTextFormatos(frm.cDscFormato) == false)
      cMsg = cMsg + cPref + "'Descripción de Formato'" + cSuf;

    // Valida Descripción Título
    if(fValidaTextFormatos(frm.cTitulo) == false)
      cMsg = cMsg + cPref + "'Título'" + cSuf;

    // Valida Descripción Tabla para Búsqueda
    if(fValidaTextFormatos(frm.cTablaBusca) == false)
      cMsg = cMsg + cPref + "'Tabla para Búsqueda'" + cSuf;

    // Valida Descripción Campo Llave en Búsqueda
    if(fValidaTextFormatos(frm.cCampoLlaveBusca) == false)
      cMsg = cMsg + cPref + "'Campo Llave en Búsqueda'" + cSuf;

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

 // Establecer variable iCveFormato
 function fSetFormato(iCveFormato){
   frm.iCveFormato.value=iCveFormato;
   fNavega();
 }

 // Obtener variable de iCveFormato
 function fGetCveFormato(){
   return frm.iCveFormato.value;
 }

 // Obtener variable de iCveFormato
 function fGetTitulo(){
   return frm.cTitulo.value;
 }

 function fPrever(){
   if(frm.cVariosFormatos.value == "")
     frm.cVariosFormatos.value = frm.iCveFormato.value;
   if(parseInt(frm.cVariosFormatos.value,10) == 0){
     fAlert("Solo es posible generar vista preliminar para formatos cuyo ID sea mayor a cero");
     frm.cVariosFormatos.value = "";
   }else{
     cRutaAbrir = cRutaProg + "pgMuestraFormato.jsp?Modo=Despliega&cDscTituloFormato=SOLICITUD DE TRAMITE / VENTANILLA UNICA&iCveFormato=" + frm.cVariosFormatos.value;
     fAbreWindowHTML(true,cRutaAbrir,"Formatos","yes","yes","yes","yes","1024","768");
   }
 }

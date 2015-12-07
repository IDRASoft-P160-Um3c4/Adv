// MetaCD=1.0
// Title: pg111010094.js
// Description: JS "Catálogo" de la entidad GRLConfigDespBusca
// Company: Tecnología InRed S.A. de C.V.
// Author: Leopoldo Beristain González
 var cTitulo = "";
 var FRMListado = "";
 var frm;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010094.js";
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
         IFrame("IFiltro94","95%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado94","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"80%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Título:");
              ITD("ECampo",6,"","","left","");
              Text(true,"cTitulo","",75,75,"Título","","","fMayus(this);",false,false);
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
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Nombre de Tabla:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cNomTabla","",30,30,"Nombre de Tabla","","","fMayus(this);",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Nombre de Campo:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cNomCampo","",30,30,"Nombre de Campo","","","fMayus(this);",false,false);
           FITR();
              TDEtiSelect(true,"EEtiqueta",0," Tipo Respuesta:","iCveTipoRespuesta","");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Etiqueta Columna:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cEtiquetaAnterior","",20,20,"Etiqueta Columna","","","fMayus(this);",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Formato Despliegue:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cFormato","",30,30,"Formato Despliegue","","","fMayus(this);",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Alineación:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cAlineacion","",15,15,"Alineación","","","fMayus(this);",false,false);
           FITR();
              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Tabla para Join:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cJoinTabla","",30,30,"Tabla para Join","","","fMayus(this);",false,false);

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("&nbsp;&nbsp;&nbsp;&nbsp;");

              ITD("EEtiqueta",0,"","","left","");
              TextoSimple("* Campo para Join:");
              ITD("ECampo",0,"","","left","");
              Text(true,"cJoinCampo","",15,15,"Campo para Join","","","fMayus(this);",false,false);
           FITR();
              Hidden("iCveFormatoAux","");
              Hidden("cTituloAux","");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel94","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel94");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado94");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo(" Clave de Atributo, Orden, Nombre de Tabla, Nombre de Campo, Etiqueta Columna, Tipo Respuesta,");
   FRMListado.fSetCampos("2,3,4,5,6,8,");
   FRMListado.fSetCol(2,"left");
   FRMListado.fSetCol(3,"left");
   FRMListado.fSetCol(4,"left");
   FRMListado.fSetCol(5,"left");
   FRMFiltro = fBuscaFrame("IFiltro94");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Clave,iCveAtributo,Orden,iOrden,Nombre de Tabla,cNomTabla,Nombre de Campo,cNomCampo,Etiqueta Columna,cEtiquetaAnterior,Tipo Respuesta,cDescripciónTipoRespuesta,");
   FRMFiltro.fSetOrdena("Clave,iCveAtributo,Orden,iOrden,Nombre de Tabla,cNomTabla,Nombre de Campo,cNomCampo,Etiqueta Columna,cEtiquetaAnterior,Tipo Respuesta,cDescripciónTipoRespuesta,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   //fNavega();
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.cTitulo.value = frm.cTituloAux.value;
   // Solo se mostraran los datos correspondientes al Formato seleccionado
   if(FRMFiltro.fGetFiltro()!= "")
   	frm.hdFiltro.value = FRMFiltro.fGetFiltro() + " and GRLCDB.iCveFormato = " + frm.iCveFormatoAux.value;
   else
   	frm.hdFiltro.value = " GRLCDB.iCveFormato = " + frm.iCveFormatoAux.value;

   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
   return fEngSubmite("pgGRLConfigDespBusca.jsp","Listado");
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

   // Obtener datos para el combo de Tipo de Respuesta.
   if(cId == "CIDTipoRespuesta" && cError==""){
     fFillSelect(frm.iCveTipoRespuesta,aRes,false,frm.iCveTipoRespuesta.value,0,1);
   }

 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
   frm.hdFiltro.value = "GrlTipoRespuesta.lVigente=1";
   frm.hdOrden.value =  "";
   if(fEngSubmite("pgGRLTipoRespuesta.jsp","CIDTipoRespuesta") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fBlanked();
     frm.cTitulo.value = frm.cTituloAux.value;
     fDisabled(false,"iCveAtributo,cTitulo,","--");
     FRMListado.fSetDisabled(true);
   }
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
   frm.hdFiltro.value = "GrlTipoRespuesta.lVigente=1";
   frm.hdOrden.value =  "";
   if(fEngSubmite("pgGRLTipoRespuesta.jsp","CIDTipoRespuesta") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     frm.cTitulo.value = frm.cTituloAux.value;
     fDisabled(false,"iCveAtributo,cTitulo,","--");
     FRMListado.fSetDisabled(true);
   }
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
    //frm.iCveFormatoAux.value = aDato[0];
    frm.cTitulo.value = frm.cTituloAux.value;
    frm.iCveAtributo.value = aDato[2];
    frm.iOrden.value = aDato[3];
    frm.cNomTabla.value = aDato[4];
    frm.cNomCampo.value = aDato[5];
    frm.cEtiquetaAnterior.value = aDato[6];
    fAsignaSelect(frm.iCveTipoRespuesta,aDato[7],aDato[8]);
    frm.cFormato.value = aDato[9];
    frm.cAlineacion.value = aDato[10];
    frm.cJoinTabla.value = aDato[11];
    frm.cJoinCampo.value = aDato[12];
 }

 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    var cPref = "\n - El campo ";
    var cSuf = " tiene caracteres no válidos.";

    cMsg = fValElements("cTitulo,"+
			"cNomTabla,"+
			"cNomCampo,"+
			"cEtiquetaAnterior,"+
			"cFormato,"+
			"cAlineacion,"+
			"cJoinTabla,"+
			"cJoinCampo,");

    // Valida Descripción Título
    if(fValidaTextFormatos(frm.cTitulo) == false)
      cMsg = cMsg + cPref + "'Título'" + cSuf;

    // Valida Descripción Nombre de Tabla
    if(fValidaTextFormatos(frm.cNomTabla) == false)
      cMsg = cMsg + cPref + "'Nombre de Tabla'" + cSuf;

    // Valida Descripción Nombre de Campo
    if(fValidaTextFormatos(frm.cNomCampo) == false)
      cMsg = cMsg + cPref + "'Nombre de Campo'" + cSuf;

    // Valida Descripción Etiqueta Columna
    if(fValidaTextFormatos(frm.cEtiquetaAnterior) == false)
      cMsg = cMsg + cPref + "'Etiqueta Columna'" + cSuf;

    // Valida Descripción Formato Despliegue
    if(fValidaTextFormatos(frm.cFormato) == false)
      cMsg = cMsg + cPref + "'Formato Despliegue'" + cSuf;

    // Valida Descripción Alineación
    if(fValidaTextFormatos(frm.cAlineacion) == false)
      cMsg = cMsg + cPref + "'Alineación'" + cSuf;

    // Valida Descripción Tabla para Join
    if(fValidaTextFormatos(frm.cJoinTabla) == false)
      cMsg = cMsg + cPref + "'Tabla para Join'" + cSuf;

    // Valida Descripción Campo para Join
    if(fValidaTextFormatos(frm.cJoinCampo) == false)
      cMsg = cMsg + cPref + "'Campo para Join'" + cSuf;

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

 // Obtener variable de iCveFormato
 function fGetTitulo(){
   return frm.cTitulo.value;
 }

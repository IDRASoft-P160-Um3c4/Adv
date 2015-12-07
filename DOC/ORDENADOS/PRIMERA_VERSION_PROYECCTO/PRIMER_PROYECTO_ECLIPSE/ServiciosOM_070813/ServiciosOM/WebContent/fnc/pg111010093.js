// MetaCD=1.0
// Title: pg111010093.js
// Description: JS "Catálogo" de la entidad GRLConfigCatFormato
// Company: Tecnología InRed S.A. de C.V.
// Author: Leopoldo Beristain González
 var cTitulo = "";
 var FRMListado = "";
 var frm;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010093.js";
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
         IFrame("IFiltro93","95%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado93","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0," Título:","cTitulo","",75,75," Título","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Atributo:","cDscAtributo","",50,50," Descripción","fMayus(this);");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0," Nivel Anterior:","iCveAtributoNivelAnterior","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Tabla:","cTabla","",50,50," Tabla","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Campo Llave:","cCampoLlave","",50,50," Campo Llave","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Campo Ordenamiento:","cCampoOrden","",50,50," Campo Ordenamiento","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," JSP Controlador de llenado:","cJSPControlador","",20,20," JSP Controlador de llenado","");
              Hidden("iCveFormato","");
              Hidden("iCveAtributo","");
              Hidden("iCveAtributoNivelAnteriorAux","");
              Hidden("cDscAtributoAux","");
              Hidden("cTituloAux","");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel93","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel93");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado93");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Nivel Anterior, Tabla, Campo Llave, Campo de Ordenamiento,");
   FRMListado.fSetCampos("5,6,7,8,");
   FRMListado.fSetCol(0,"left");
   FRMListado.fSetCol(1,"left");
   FRMListado.fSetCol(2,"left");
   FRMListado.fSetCol(3,"left");
   FRMFiltro = fBuscaFrame("IFiltro93");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Nivel Anterior,cDscAtributoNivelAnterior,Tabla,cTabla,Campo Llave,cCampoLlave,Campo de Ordenamiento,cCampoOrden,");
   FRMFiltro.fSetOrdena("Nivel Anterior,cDscAtributoNivelAnterior,Tabla,cTabla,Campo Llave,cCampoLlave,Campo de Ordenamiento,cCampoOrden,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   //fNavega();
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.cTitulo.value = frm.cTituloAux.value;
   frm.cDscAtributo.value = frm.cDscAtributoAux.value;
   // Solo se mostraran los datos correspondientes al Formato seleccionado
   if(FRMFiltro.fGetFiltro()!= "")
   	frm.hdFiltro.value = FRMFiltro.fGetFiltro() + " and GRLCatFmt.iCveFormato = " + frm.iCveFormato.value +
        					      " and GRLCatFmt.iCveAtributoCfgCat = " + frm.iCveAtributo.value +
						      " and GRLCatFmt.lVigenteAtributo = 1 " +
						      " and GRLCfgB.lVigenteNivelAnterior = 1";
   else
   	frm.hdFiltro.value = " GRLCatFmt.iCveFormato = " + frm.iCveFormato.value +
			     " and GRLCatFmt.iCveAtributoCfgCat = " + frm.iCveAtributo.value +
			     " and GRLCatFmt.lVigenteAtributo = 1 " +
			     " and GRLCfgB.lVigenteNivelAnterior = 1";

   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
   return fEngSubmite("pgGRLConfigCatFormato.jsp","Listado");
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
    if(!aRes || aRes.length==0)
      FRMPanel.fSetTraStatus("Add,");
    else
      FRMPanel.fSetTraStatus("Mod,Del,");
   }

   // Obtener datos para el combo de Tipo de Respuesta.
   if(cId == "CIDNivelAnterior" && cError==""){
     fFillSelect(frm.iCveAtributoNivelAnterior,aRes,false,frm.iCveAtributoNivelAnterior.value,2,4);
   }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
   frm.hdFiltro.value = "GRLCFMT.lVigente=1 "+
                        //"and GRLCFMT.iCveAtributo <>"+ frm.iCveAtributo.value+
                        " AND GRLCFMT.iCveFormato="+frm.iCveFormato.value;
   frm.hdNumReg.value = 10000;
   frm.hdOrden.value="";
   if(fEngSubmite("pgGRLConfigFormato.jsp","CIDNivelAnterior") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fBlanked();
     frm.cTitulo.value = frm.cTituloAux.value;
     frm.cDscAtributo.value = frm.cDscAtributoAux.value;
     fDisabled(false,"cTitulo,cDscAtributo,","--");
     FRMListado.fSetDisabled(true);
   }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
   frm.iCveAtributoNivelAnteriorAux.value = (frm.iCveAtributoNivelAnterior.value == -1)?0:frm.iCveAtributoNivelAnterior.value;
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
   frm.iCveAtributoNivelAnteriorAux.value = frm.iCveAtributoNivelAnterior.options[frm.iCveAtributoNivelAnterior.selectedIndex].value;
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
   frm.hdFiltro.value = "GRLCFMT.lVigente=1 and GRLCFMT.iCveAtributo <>"+ frm.iCveAtributo.value;
   frm.hdNumReg.value = 10000;
   frm.hdOrden.value="";
   if(fEngSubmite("pgGRLConfigFormato.jsp","CIDNivelAnterior") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     frm.cTitulo.value = frm.cTituloAux.value;
     frm.cDscAtributo.value = frm.cDscAtributoAux.value;
     fDisabled(false,"cTitulo,cDscAtributo,","--");
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
    //frm.iCveFormato.value = aDato[];
    //frm.iCveAtributo.value = aDato[];
    frm.cDscAtributo.value = frm.cDscAtributoAux.value;
    frm.cTitulo.value = frm.cTituloAux.value;
    fAsignaSelect(frm.iCveAtributoNivelAnterior,aDato[4],aDato[5]);
    frm.cTabla.value = aDato[6];
    frm.cCampoLlave.value = aDato[7];
    frm.cCampoOrden.value = aDato[8];
    frm.cJSPControlador.value = aDato[9];
 }

 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    var cPref = "\n - El campo ";
    var cSuf = " tiene caracteres no válidos.";

    cMsg = fValElements("cTitulo,"+
			"cDscAtributo,"+
			"iCveAtributoNivelAnterior,"+
			"cTabla,"+
			"cCampoLlave,"+
			"cCampoOrden,"+
			"cJSPControlador,");

    // Valida Descripción Título
    if(fValidaTextFormatos(frm.cTitulo) == false)
      cMsg = cMsg + cPref + "'Título'" + cSuf;

    // Valida Descripción Atributo
    if(fValidaTextFormatos(frm.cDscAtributo) == false)
      cMsg = cMsg + cPref + "'Atributo'" + cSuf;

    // Valida Descripción Nivel Anterior
    if(fValidaTextFormatos(frm.iCveAtributoNivelAnterior) == false)
      cMsg = cMsg + cPref + "'Nivel Anterior'" + cSuf;

    // Valida Descripción Tabla
    if(fValidaTextFormatos(frm.cTabla) == false)
      cMsg = cMsg + cPref + "'Tabla'" + cSuf;

    // Valida Descripción Campo Llave
    if(fValidaTextFormatos(frm.cCampoLlave) == false)
      cMsg = cMsg + cPref + "'Campo Llave'" + cSuf;

    // Valida Descripción Campo Ordenamiento
    if(fValidaTextFormatos(frm.cCampoOrden) == false)
      cMsg = cMsg + cPref + "'Campo Ordenamiento'" + cSuf;

    // Valida Descripción JSP Controlador de llenado
    if(fValidaTextFormatos(frm.cJSPControlador) == false)
      cMsg = cMsg + cPref + "'JSP Controlador de llenado'" + cSuf;

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
 function fSetVarIni(iCveFormato,cTitulo,iCveAtributo,cDscAtributo){
    frm.iCveFormato.value = iCveFormato;
    frm.iCveAtributo.value = iCveAtributo;
    frm.cDscAtributoAux.value = cDscAtributo;
    frm.cTituloAux.value = cTitulo;
    frm.cDscAtributo.value = cDscAtributo;
    frm.cTitulo.value = cTitulo;
    fNavega();
 }

 // Obtener variable de iCveFormato
 function fGetCveFormato(){
   return frm.iCveFormato.value;
 }

 // Obtener variable de iCveAtributo
 function fGetCveAtributo(){
   return frm.iCveAtributo.value;
 }

 // Obtener variable de cTitulo
 function fGetTitulo(){
   return frm.cTitulo.value;
 }

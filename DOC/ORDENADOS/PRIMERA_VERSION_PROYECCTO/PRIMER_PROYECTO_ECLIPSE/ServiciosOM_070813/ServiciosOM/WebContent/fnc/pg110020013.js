// MetaCD=1.0
 // Title: pg115020063.js
 // Description: Guia de creacion de JS's
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Ing. Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aInicial = new Array;
 var aFinal = new Array;
 var aResLocal = new Array;

 /*******Variables de creacion de JS's********/
 var ListadoA = 2;//ListadoA(0) No muestra los listados, ListadoA(1) Muestra un listado,
                  //ListadoA(2) Muestra 2 listados y botones de paso de agregar y quitar
 var ListadoB = 0;//ListadoB(0) No muestra los listados, ListadoB(1) Muestra un listado,
                  //ListadoB(2) Muestra 2 listados y botones de paso de agregar y quitar
 var ListadoC = 1;//ListadoC(0) No muestra los listados,
                  //ListadoC(1) Muestra un listado y Area para campode texto
 var FiltroTC = 0;//Filtro(1) Muestra el filtro de Thinck Client
 var Filtro   = 2;//De el valor de la cantidad de listas desplegables que desee en el filtro (0 - 3).
 var Panel    = 1;//En caso de 1 se mostrara el panel, de lo contrario... no.
 var Titulo   = 1;//Titulo(1) = Titulo normal, Titulo(2) = Titulo Emergente


 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "g117GuiaJS.js";
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
       FTDTR();
     /******************************************************/
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
         FTR();
           ITRTD("",0,"","","center","");
             IFrame("IListadoA","95%","150","Listado.js","yes",true);
           ITD("",0,"","","center","center");
             ITD("",0,"","","center","");
             IFrame("IListadoA1","95%","150","Listado.js","yes",true);
         FTDTR();
     FinTabla();
     /******************************************************/
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
         TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","");
       ITD();
         BtnImg("Buscar","lupa","fNavega();","Buscar");
     FinTabla();
     /******************************************************/
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
         FTR();
           ITRTD("",0,"","","center","");
             IFrame("IListadoC","95%","150","Listado.js","yes",true);
           ITD("",0,"","","center","center");
             InicioTabla("",0,"100%","","");
               ITD("",0,"","","center","center");
                 InicioTabla("",0,"100%","","");
                 FinTabla();
               FTDTR();
               ITD("EEtiquetaC");
                   Liga("Guardar los Cambios","fGuardarTodos();");
               FITR();
             FinTabla();
         FTDTR();
         FinTabla();
       /******************************************************************/
     ITRTD("",0,"","","top");
       InicioTabla("",0,"100%","","");
         FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Inicio de Asignación: ","dtInicioModificar","",50,50," Inicio de asignación ","fMayus(this);");
         FITR();
           TDEtiSelect(true,"EEtiqueta",0,"Formato de Salida:","iCveFormatoSalida","");
         FTDTR();
       FinTabla();
       FTDTR();
       FinTabla();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       if(Panel==1){
         ITRTD("",0,"","40","center","bottom");
           IFrame("IPanelA","95%","34","Paneles.js");
       }
     FTDTR();
   FinTabla();
   Hidden("hdSelect");
   Hidden("hdLlave");
   Hidden("iCveDepartamento");
   Hidden("iCveProducto");
   Hidden("iCveProceso");

   Hidden("cAgregar","");
   Hidden("cBorrar","");
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
     FRMPanel = fBuscaFrame("IPanelA");
     FRMPanel.fSetControl(self,cPaginaWebJS);
     FRMPanel.fShow("Tra,");

     //Listado de Procesos
     FRMListado = fBuscaFrame("IListadoA");
     FRMListado.fSetControl(self);
     FRMListado.fSetTitulo("Consecutivo,Proceso,");
     FRMListado.fSetCampos("0,1,");
     FRMListado.fSetSelReg(1);
     //FRMListado.fSetObjs(0,"Caja");

     //Listado de Productos
     FRMListado2 = fBuscaFrame("IListadoA1");
     FRMListado2.fSetControl(self);
     FRMListado2.fSetTitulo("Consecutivo,Productos,");
     FRMListado2.fSetCampos("0,1,");
     FRMListado2.fSetSelReg(2);
     //FRMListado2.fSetObjs(0,"Caja")

     //Departamentos
     FRMListado3 = fBuscaFrame("IListadoC");
     FRMListado3.fSetControl(self);
     FRMListado3.fSetTitulo(",Departamentos,Fecha Asignación,");
     FRMListado3.fSetCampos("1,3,");
     FRMListado3.fSetObjs(0,"Caja");
     FRMListado3.fSetAlinea("left,left,center,");
     FRMListado3.fSetSelReg(3);
   frm.hdBoton.value="Primero";
   //fListaFormatos();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "";
   frm.hdOrden.value  = "";
   frm.hdNumReg.value = 1000;
   fEngSubmite("pgGRLProdXOficDepto1.jsp","Listado");
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

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     var cInicial = "";
     aResLocal = fCopiaArregloBidim(aRes);
     for(i=0;i<aRes.length;i++){
       aRes[i][10] = aRes[i][2]>0?1:0;
       if(aRes[i][10]==1)cInicial += aRes[i][0]+",";
     }
     aInicial = cInicial.split(",");
     FRMListado3.fSetListado(aRes);
     FRMListado3.fShow();
     FRMListado3.fSetDefaultValues(0,10);
     FRMListado3.fSetLlave(cLlave);
     fCancelar();
   }

   if(cId == "cIdProcesos" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fListaProductos();
   }
   if(cId == "cIdProductos" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado2.fSetListado(aRes);
     FRMListado2.fShow();
     FRMListado2.fSetLlave(cLlave);
     fListaOficinas();
   }
   if(cId == "cIdOficinas" && cError==""){
     fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value,0,1);
   }
   if(cId == "cIdFormatos" && cError==""){
     fFillSelect(frm.iCveFormatoSalida,aRes,true,frm.iCveFormatoSalida.value,0,1);
     fListaProcesos();
   }

 }
 function fGuardarA(){
   if(fValidaTodo()==true){
      if(fNavega()==true){
        //FRMPanel.fSetTraStatus("Mod,");
        frm.iCveFormatoSalida.disabled = true;
        frm.dtInicioModificar.disabled = true;
        FRMListado.fSetDisabled(false);
        FRMListado2.fSetDisabled(false);
        FRMListado3.fSetDisabled(false);
      }
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
   FRMListado.fSetDisabled(true);
   FRMListado2.fSetDisabled(true);
   FRMListado3.fSetDisabled(true);
   frm.iCveFormatoSalida.disabled = false;
   frm.dtInicioModificar.disabled = false;
   FRMPanel.fSetTraStatus("UpdateBegin");

 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMPanel.fSetTraStatus(",");
    fDisabled(false);
    FRMListado.fSetDisabled(false);
    FRMListado2.fSetDisabled(false);
    FRMListado3.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
   frm.iCveProceso.value = aDato[0];
 }
 function fSelReg2(aDato){
   frm.iCveProducto.value = aDato[0];
 }
 function fSelReg3(aDato){
   frm.iCveDepartamento.value  = aDato[0];
   frm.dtInicioModificar.value = aDato[3];
   fSelectSetIndexFromValue(frm.iCveFormatoSalida, aDato[4]);
   if(aDato[10]==1)FRMPanel.fSetTraStatus("Mod,");
   else FRMPanel.fSetTraStatus(",");
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
 function fAgregar(){
}

 function fListaProcesos(){
   frm.hdSelect.value = "SELECT ICVEPROCESO,CDSCPROCESO FROM GRLPROCESO WHERE LVIGENTE = 1 ORDER BY CDSCPROCESO";
   frm.hdLlave.value  = "";
   fEngSubmite("pgConsulta.jsp","cIdProcesos");
 }

 function fListaProductos(){
   frm.hdSelect.value = "SELECT ICVEPRODUCTO,CDSCBREVE FROM GRLPRODUCTO WHERE LVIGENTE = 1 ORDER BY CDSCBREVE";
   frm.hdLlave.value  = "";
   fEngSubmite("pgConsulta.jsp","cIdProductos");
 }

 function fListaOficinas(){
   frm.hdSelect.value = "SELECT ICVEOFICINA,CDSCBREVE FROM GRLOFICINA WHERE LVIGENTE = 1 ORDER BY CDSCBREVE";
   frm.hdLlave.value  = "";
   fEngSubmite("pgConsulta.jsp","cIdOficinas");
 }
 function fGuardarTodos(){
   //var aResLocal = fCopiaArregloBidim(FRMListado3.fGetARes());
   var cFinal = "";
   var lEncontrado = false;
   frm.cAgregar.value = "";
   frm.cBorrar.value = "";
   aCBox = FRMListado3.fGetObjs(0);
   for(aux=0; aux<aCBox.length; aux++){
     if (aCBox[aux]==true){
       cFinal += aResLocal[aux][0]+",";
     }
   }

   aFinal = cFinal.split(",");
   for (i=0;i<aInicial.length;i++){
     for (j=0;j<aFinal.length;j++){
       if(aInicial[i]==aFinal[j]){
         lEncontrado = true;
         j=aFinal.length;
       }
     }

     if(!lEncontrado)
       frm.cBorrar.value += aInicial[i]+",";
     lEncontrado = false;
   }

   for (i=0;i<aFinal.length;i++){
     for (j=0;j<aInicial.length;j++){
       if(aFinal[i]==aInicial[j]){
         lEncontrado = true;
         j=aInicial.length;
       }
     }
     if(!lEncontrado)
       frm.cAgregar.value += aFinal[i]+",";
     lEncontrado = false;
   }
   frm.hdBoton.value = "GuardarCambios";
   fNavega();
 }
 function fListaFormatos(){
   frm.hdLlave.value = "ICVEFORMATOSALIDA";
   frm.hdSelect.value = "SELECT ICVEFORMATOSALIDA,CDSCFORMATOSALIDA FROM GRLFORMATOSALIDA order by CDSCFORMATOSALIDA";
   fEngSubmite("pgConsulta.jsp","cIdFormatos");
 }

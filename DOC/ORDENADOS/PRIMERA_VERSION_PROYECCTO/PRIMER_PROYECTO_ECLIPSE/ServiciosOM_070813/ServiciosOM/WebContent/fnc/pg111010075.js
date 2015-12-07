// MetaCD=1.0
 // Title: pg111010075.js
 // Description: JS "Catálogo" de la entidad TRAConceptoXGrupo
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResTmp = new Array();
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010075.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
       ITR("",0,"","0","center","top");
              TDEtiSelect(true,"EEtiqueta",0,"Grupo:","iCveGrupo","fNavega();");
       FITR();
       //ITR();
         TDEtiAreaTexto(false,"EEtiqueta",0,"Descripción grupo:",120,4,"cDscGrupo","","Descricpión del grupo","","fMayus(this);","",false,false,false);

       FITR();
     InicioTabla("",0,"100%","","center");
       FTDTR();
       ITRTD("",0,"","","center","top");
         ITRTD("",0,"","","center","");
           IFrame("IListado75A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnquitar","fEliminar();");
             FTDTR();
             ITRTD("",0,"","100%","center","");
           FinTabla();
         ITD("",0,"","","center","");
           IFrame("IListado75","95%","220","Listado.js","yes",true);
       ITRTD("",0,"","1","center");
     FinTabla();
     InicioTabla("ETablaInfo",0,"75%","","center");
        FTDTR();
              TDEtiCampo(true,"EEtiqueta",0,"Limite Inferior:","dLimiteInferior","",8,8,"Limite Inferior...","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"Limite Superior:","dLimiteSuperior","",8,8,"Limite Superior...","fMayus(this);");
           FTR();
       FTDTR();
       FinTabla();
     InicioTabla("",0,"100%","","center");
        FTDTR();
        ITRTD("",0,"","40","center","bottom");
           IFrame("IPanel75","95%","34","Paneles.js");
        FTDTR();
     FinTabla();
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("iCveConcepto");
     Hidden("iCveConcepto1");
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel75");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado75");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Consecutivo,Concepto asignado,");
   FRMListado.fSetAlinea("center,left,");
   FRMListado.fSetCampos("1,4,");
  FRMListado.fSetSelReg(1);
   FRMListadoA = fBuscaFrame("IListado75A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Consecutivo,Concepto disponible,");
   FRMListadoA.fSetAlinea("center,left,");
  FRMListadoA.fSetSelReg(2);
   fDisabled(true,"iCveGrupo,");
   frm.hdBoton.value="Primero";
   frm.hdSelect.value= " SELECT ICVEGRUPO, CDSCGRUPO, LVIGENTE, LAPLICAFACTORDIRECTO FROM TRAGRUPOCONCEPTO " +
   										 " UNION "+
   										 " SELECT Distinct(iCveGrupo), 'Grupo: ' || CHAR(iCveGrupo) AS cDscGrupo, 1 AS lVigente, 0 AS lAplicaFactorDirecto " +
   										 "        FROM TRAConceptoXGrupo WHERE iCveGrupo NOT IN (SELECT iCveGrupo FROM TRAGrupoConcepto) " +
   										 " ORDER BY cDscGrupo ";
   frm.hdLlave.value="iCveGrupo";
   fEngSubmite("pgConsulta.jsp","CIDGrupo");
   //fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.cDscGrupo.value = aResTmp[frm.iCveGrupo.value][1];
   frm.hdFiltro.value =  "iCveGrupo = "+frm.iCveGrupo.value;
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgTRAConceptoXGrupo.jsp","Listado");
 }
 function fNavega1(){
   frm.hdSelect.value=" select iCveConcepto, cDscConcepto From TraConceptoPago Where iCveConcepto not in (Select iCveConcepto From TRAConceptoXGrupo Where iCveGrupo = "+frm.iCveGrupo.value+") Order By iCveConcepto ";
   frm.hdLlave.value="iCveConcepto";
   return fEngSubmite("pgConsulta.jsp","Listado1");
   frm.hdNumReg.value =  10000;
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
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     fNavega1();
   }
   if(cId == "Listado1" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
     fCancelar();
   }
   if(cId == "CIDGrupo"){
      //aResTmp = fCopiaArregloBidim(aRes);
      for (var i=0; i < aRes.length; i++){
        aResTmp[aRes[i][0]] = aRes[i];
      	aRes[i][2] = aRes[i][1].substring(0,100);
      }
      fFillSelect(frm.iCveGrupo,aRes,true,frm.iCveGrupo.value,0,2);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveGrupo,cDscGrupo,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("Mod,");
          fDisabled(true,"iCveGrupo,");
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("Mod,");
         fDisabled(true,"iCveGrupo,");
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveGrupo,cDscGrupo,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Mod,");
    else
      FRMPanel.fSetTraStatus("");
    fDisabled(true,"iCveGrupo,");
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 function fAgregar(){
   if (FRMListadoA.fGetLength() > 0){
     frm.hdBoton.value = "Guardar";
     fNavega();
   }
 }
 function fEliminar(){
  if(FRMListado.fGetLength() > 0){
   if (frm.dLimiteInferior.value!=0 || frm.dLimiteSuperior.value!=0){
     if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       frm.hdBoton.value = "Borrar";
       fNavega();
     }
   }else {
     frm.hdBoton.value = "Borrar";
     fNavega();
   }
  }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.iCveConcepto.value = aDato[1];
    frm.dLimiteInferior.value = aDato[2];
    frm.dLimiteSuperior.value = aDato[3];
 }
 function fSelReg2(aDato){
    frm.iCveConcepto1.value = aDato[0];
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


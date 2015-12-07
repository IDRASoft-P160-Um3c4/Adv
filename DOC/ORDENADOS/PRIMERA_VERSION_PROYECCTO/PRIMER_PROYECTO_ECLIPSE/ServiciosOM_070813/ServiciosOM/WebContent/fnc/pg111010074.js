// MetaCD=1.0
 // Title: pg111010074.js
 // Description: JS "Cat�logo" de la entidad TRAGrupoConcepto
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: Arturo Lopez Pe�a
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010074.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro74","95%","34","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado74","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
              TDEtiCampo(true,"EEtiqueta",0,"Consecutivo:","iCveGrupo","",3,3,"Clave","fMayus(this);","","",false,"",4);

           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Descripci�n:",75,3,"cDscGrupo","","Descripci�n","","fMayus(this);",'onkeydown="fMxTx(this,150);"',false,false,true,"",4);
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"�Aplica factor directo a este grupo?:","lAplicaFactorDirectoBOX","1",true,"En c�lculo de tarifas a conceptos de este grupo se multiplicar� las unidades por el factor del concepto.");
              Hidden("lAplicaFactorDirecto","");
              TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
              Hidden("lVigente","");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel74","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel74");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado74");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Consecutivo,Descripci�n,Vigente,");
   FRMListado.fSetAlinea("center,left,center,");
   FRMListado.fSetDespliega("texto,texto,logico,");
   FRMFiltro = fBuscaFrame("IFiltro74");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Consecutivo,iCveGrupo,Descripci�n,cDscGrupo,");
   FRMFiltro.fSetOrdena("Consecutivo,iCveGrupo,Descripci�n,cDscGrupo,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fNavega();
 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgTRAGrupoConcepto.jsp","Listado");
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
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveGrupo,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
   frm.lAplicaFactorDirectoBOX.checked==true?frm.lAplicaFactorDirecto.value=1:frm.lAplicaFactorDirecto.value=0;

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
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
   frm.lAplicaFactorDirectoBOX.checked==true?frm.lAplicaFactorDirecto.value=1:frm.lAplicaFactorDirecto.value=0;

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
    fDisabled(false,"iCveGrupo,");
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
    frm.iCveGrupo.value = aDato[0];
    frm.cDscGrupo.value = aDato[1];
    frm.lVigente.value = aDato[2];
    frm.lAplicaFactorDirecto.value = aDato[3];
    frm.lVigente.value ==1?frm.lVigenteBOX.checked=true:frm.lVigenteBOX.checked=false;
    frm.lAplicaFactorDirecto.value ==1?frm.lAplicaFactorDirectoBOX.checked=true:frm.lAplicaFactorDirectoBOX.checked=false;

 }
 // FUNCI�N donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    cMsg = fValElements("cDscGrupo,");
   if(frm.cDscGrupo.value=="")
     cMsg += "\n El campo descripci�n no puede estar vac�o.";
   if(!fValidaDescripcion(frm.cDscGrupo.value))
     cMsg += "\n- El campo descripci�n solo acepta letras, n�meros, par�ntesis y diagonales";

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
function fValidaDescripcion(cVCadena){
    if ( fRaros(cVCadena)      || //fPuntuacion(cVCadena) ||
         fSignos(cVCadena)     // || fArroba(cVCadena)     ||
         //fParentesis(cVCadena) || fPunto(cVCadena)      ||
         //fDiagonal(cVCadena)   || fComa(cVCadena)
         )
        return false;
    else
        return true;
}

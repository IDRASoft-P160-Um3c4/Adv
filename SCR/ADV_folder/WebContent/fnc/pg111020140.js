// MetaCD=1.0
 // Title: pg111020140.js
 // Description: JS "Catálogo" de la entidad GRLFolioXSegtoEnt
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResFolio = new Array();
 var aResActualizar = new Array();
 var aDatosFolio = new Array();
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020140.js";
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
           FTDTR();
            TDEtiCheckBox("EEtiqueta",0,"Oficio Externo?:","lExternoBOX","1",true,"Oficio Externo","","onClick=fActivaCampos();");
           FTDTR();
//           TDEtiCampo(cOnAnyEvent,cOnChange,lSelectOnFocus,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,Campo
              TDEtiCampo(true,"EEtiqueta",0,"Número de Oficio Referenciado:","cNumOficioRef","",20,20,"Número de Oficio Requerido","fActivaFolio();");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Número de Oficialía de Partes Referenciado:","cNumOfPartesRef","",20,20,"Número de Oficialíade Partes Referenciadas","fMayus(this);");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
       Hidden("lExterno","0");
       Hidden("iCveSegtoEntidad");
       Hidden("hdLlave");
       Hidden("hdSelect");
       Hidden("hdEjercicio");
       Hidden("iEjercicio");
       Hidden("lFolioReferenInterno");
       Hidden("hdCveSegtoEntidad");
       Hidden("hdConsecutivoSegtoRef");
       Hidden("Temp");
       Hidden("cNumOfPartesRefExterna");
       Hidden("cNumOficioRefExterna");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   if(top.opener && top.opener.window.parent && top.opener.window.parent.lConsulta)
     FRMPanel.fShow(",");
   else
     FRMPanel.fShow("Tra,");  
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Num de Oficio Referenciado,Num de Oficialía de Partes Referenciado,Oficio Externo,");
   FRMListado.fSetCampos("8,15,16,");
   FRMListado.fSetAlinea("center,center,center,");
   FRMListado.fSetDespliega("texto,texto,logico,");
   FRMListado.fSetSelReg("1");
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
   FRMFiltro.fSetFiltra("");
   FRMFiltro.fSetOrdena("");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fRecibeValores();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = " GRLFOLIOXSEGTOENT.ICVESEGTOENTIDAD = "+frm.iCveSegtoEntidad.value+" and GRLFOLIOXSEGTOENT.LFOLIOREFERENINTERNO = 1 ";
   frm.hdOrden.value =  " GRLFOLIOXSEGTOENT.ICONSECUTIVO ASC";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgGRLFolioXSegtoEnt.jsp","Listado");
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
     aResTemp = fCopiaArreglo(aRes);

     for(var i =0; i<aResTemp.length;i++){
      if(aResTemp[i][16] == 0){
       if(aResTemp[i][9]<10){
         aRes[i][9] = "00"+aResTemp[i][9];
       }
       if(aRes[i][9]>=10 & aResTemp[i][9]<100){
         aRes[i][9] = "0"+aResTemp[i][9];
       }
       aRes[i][8] = aResTemp[i][8]+"."+aResTemp[i][9] +"/"+ aResTemp[i][5];
       aRes[i][15] = aRes[i][15];
      }else{
       aRes[i][8] = aResTemp[i][17];
       aRes[i][15] = aRes[i][18];
      }
     }

     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
     if (frm.Temp.value!="")fReposicionaListado(FRMListado,"8", frm.Temp.value);
     else fReposicionaListado(FRMListado,"8", 0);
   }

    if(cId == "Folio" && cError==""){
     aResFolio = aRes;
     if(aResFolio != null & aResFolio != ""){
        frm.cNumOfPartesRef.value = aResFolio[0][5];
     }else fAlert("No es válido el Oficio que ha ingresado.");

   }

 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iCveSegtoEntidad,");
    fDisabled(false,"iCveSegtoEntidad,cNumOfPartesRef,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    frm.lExternoBOX.checked==true?frm.lExterno.value=1:frm.lExterno.value=0;
    frm.Temp.value = frm.cNumOficioRef.value;

    if(frm.lExterno.value == 1){
      frm.cNumOfPartesRefExterna.value = frm.cNumOfPartesRef.value;
      frm.cNumOficioRefExterna.value = frm.cNumOficioRef.value;
      frm.hdBoton.value = "GuardarB";

      if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
     }

    }else{
      if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
      }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
   frm.lExternoBOX.checked==true?frm.lExterno.value=1:frm.lExterno.value=0;
   frm.Temp.value = frm.cNumOficioRef.value;

    if(frm.lExterno.value == 1){
      frm.cNumOfPartesRefExterna.value = frm.cNumOfPartesRef.value;
      frm.cNumOficioRefExterna.value = frm.cNumOficioRef.value;
      frm.hdBoton.value = "GuardarC";
      if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
     }
    }else{
      if(fValidaTodo()==true){
         if(fNavega()==true){
           FRMPanel.fSetTraStatus("UpdateComplete");
           fDisabled(true);
           FRMListado.fSetDisabled(false);
         }
      }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    if(frm.lExterno.value == 0)
      fDisabled(false,"iCveSegtoEntidad,cNumOfPartesRef,");
    else
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

    if(frm.lExterno.value==1){
      frm.cNumOfPartesRef.value = aDato[18];
      frm.cNumOficioRef.value = aDato[8];
    }
    else{
      frm.cNumOfPartesRef.value = aDato[15];
      frm.cNumOficioRef.value = aDato[8];
    }

    frm.lFolioReferenInterno.value = 1;
    frm.hdConsecutivoSegtoRef.value= aDato[1];
    frm.hdCveSegtoEntidad.value= aDato[0];
    frm.lExterno.value = aDato[16];
    if (frm.lExterno.value==1)
      frm.lExternoBOX.checked=true;
    else
      frm.lExternoBOX.checked=false;

 }

 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){

   cMsg = fValElements("cNumOficioRef,");
   if(fEvaluaCampo(frm.cNumOficioRef.value)==false)          //evalua los parametros que son aceptados en el
   cMsg = cMsg+"\n Es incorrecto el Número de Oficio";

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


 function fActivaFolio(){
   frm.lExternoBOX.checked==true?frm.lExterno.value=1:frm.lExterno.value=0;
     if(frm.lExterno.value == 0){
      aDatosFolio=getDatosFolio(frm.cNumOficioRef.value);

      frm.hdLlave.value =   "iEjercicio, iCveOficina, iCveDepartamento, cDigitosFolio, iConsecutivo";
      frm.hdSelect.value =  "select iEjercicio,iCveOficina,iCveDepartamento,cDigitosFolio,iConsecutivo, cNumOficialiaPartes " +
                            "from GRLFOLIO "+
                            " where iEjercicio = "+aDatosFolio[0]+
                            " and cDigitosFolio = '"+aDatosFolio[1]+"'"+
                            " and iConsecutivo = "+aDatosFolio[2];


      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","Folio");
     }else
       return;

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

 function fActivaCampos(){
   frm.lExternoBOX.checked==true?frm.lExterno.value=1:frm.lExterno.value=0;
   if(frm.lExterno.value == 1){
     fDisabled(false);
     frm.lExterno.value = 0;
   }else{
     fDisabled(true,"cNumOficioRef,lExternoBOX,");
     fBlanked("lExternoBOX,iCveSegtoEntidad,");
     frm.lExterno.value = 1;
   }

 }

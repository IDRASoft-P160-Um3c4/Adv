// MetaCD=1.0
// Title: pg111010031.js
// Description: JS "Catálogo" de la entidad TRACatTramite
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda<BR>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm;
var cBien="";
var cBienSelect="";
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010031.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","","center");
      ITRTD("",0,"","40","center","top");
        IFrame("IFiltro31","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado31","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",15,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
//             TDEtiCampo(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,cEstiloCM,iColExtiendeCM);
             TDEtiCampo(false,"EEtiqueta",0," Consecutivo:","iCveTramite","",3,3," Clave","fMayus(this);","","",true,"",9);
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Clave Interna:","cCveInterna","",11,15," Clave Interna","fMayus(this);","","",true,"",9);
           FITR();
//              TDEtiAreaTexto(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo,lContador,cEstiloCM,iColExtiendeCM);
              TDEtiAreaTexto(true,"EEtiqueta",0,"Descripción:",80,3,"cDscTramite","","Descripción","","fMayus(this);",'onkeydown="fMxTx(this,500);"',true,true,true,"",9);
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Descripción Breve:","cDscBreve","",120,100," Desc. Breve","fMayus(this);","","",true,"",9);
           FITR();
              //TDEtiAreaTexto(false,"EEtiqueta",0,"Objetivo Trámite:",80,3,"cObjTramite","","Objetivo del Trámite","","fMayus(this);",'onkeydown="fMxTx(this,500);"',true,true,true,"",9);
           	Hidden("cObjTramite","");
           FITR();
              //TDEtiAreaTexto(false,"EEtiqueta",0,"Comprobante:",80,3,"cComprobante","","Comprobantes del Trámite","","fMayus(this);",'onkeydown="fMxTx(this,500);"',true,true,true,"",9);
           Hidden("cComprobante","");
           FITR();
              //TDEtiAreaTexto(false,"EEtiqueta",0,"Notas:",80,3,"cNotas","","Notas","","fMayus(this);",'onkeydown="fMxTx(this,1200);"',true,true,true,"",9);
           Hidden("cNotas","");
           FITR();
       //       TDEtiSelect(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cOnChange,cEstiloCM,iColExtiendeCM);
              //TDEtiSelect(true,"EEtiqueta",0,"Bien:","cBienBuscar","","",5);
           Hidden("cBienBuscar","");
       //    FITR();
              //TDEtiCheckBox("EEtiqueta",0," Requiere Firma Digital?:","lReqFirmaDigitalBOX","1",true," Req. Firma Digital");
              Hidden("lReqFirmaDigital",0);
       //    FITR();
              //TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente");
              Hidden("lVigente",1);
              //TDEtiCheckBox("EEtiqueta",0," Impresion en el Manual:","lImprimeManualBOX","1",true," Vigente");
              Hidden("lImprimeManual",0);
              Hidden("flag1");
              Hidden("iFlag1");
           FITR();
           //TDEtiCheckBox(cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo,cEstiloCM,iColExtiendeCM)
           		//TDEtiCheckBox("EEtiqueta",0," Tiene un tramite padre?:","ltramitePadre","1",true," Tramite Padre","","","ftramitepadre();");
           		Hidden("ltramitePadre",0);
           		//TDEtiCheckBox("EEtiqueta",0," Es tramite Final?:","lTramiteFinalBOX","1",true," Tramite Final");
           		//TDEtiSelect(false,"EEtiqueta",0,"Tramite Padre:","iCveTramite2","","",7);
           		Hidden("lTramiteFinal",1);
               FTD();
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel31","95%","34","Paneles.js");
      FTDTR();
      Hidden("iClaveTramite");
      Hidden("iCveUsuario",top.fGetUsrID());
    FinTabla();
  fFinPagina();
}
function ftramitepadre(){
	//if(frm.ltramitePadre.checked==true){
	//	fDisabled(false,"iCveTramite,","--");
	//}else{
	//	fDisabled(false,"iCveTramite,iCveTramite2,","--");
	//}

}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel31");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(false);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado31");
  FRMListado.fSetControl(self);
  //FRMListado.fSetTitulo(" Consecutivo, Clave Interna, Descripción Breve, Vigente, ");
  //FRMListado.fSetAlinea("center,center,left,center,");
  //FRMListado.fSetDespliega("texto,texto,texto,logico,");
  //FRMListado.fSetCampos("0,1,3,5,");
  
  FRMListado.fSetTitulo(" Consecutivo, Clave Interna, Descripción Breve, ");
  FRMListado.fSetAlinea("center,center,left,");
  FRMListado.fSetDespliega("texto,texto,texto,");
  FRMListado.fSetCampos("0,1,3,");

  
  FRMListado.fSetCol(2,"left");
  FRMFiltro = fBuscaFrame("IFiltro31");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Clave Interna,cCveInterna,Consecutivo,TRACatTramite.iCveTramite,");
  FRMFiltro.fSetOrdena("Clave Interna,cCveInterna,Consecutivo,TRACatTramite.iCveTramite,Descripción,cDscBreve,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  fLlenaComboBien();
  //fNavega();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value = FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  10000;//FRMFiltro.document.forms[0].iNumReg.value;
  return fEngSubmite("pgTRACatTramite2A.jsp","Listado");
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,cBienPar){

  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }

  if(cError!=""){
    fAlert(cError);
    FRMFiltro.fSetNavStatus("Record");
    }

  if(cId == "Listado" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    FRMFiltro.fSetNavStatus(cNavStatus);
    frm.iFlag1.value=0;
    //fFillSelect(frm.iCveTramite2,aRes,true,frm.iCveTramite2.value,0,3);
    fCancelar();
  }

    if(cId == "Bien" && cError==""){
      cBien = cBienPar;
      cBienSelect = cBien.split(",");
      var cTemp = cBienSelect[0];
      var aBien = new Array();
      aBien = cBienSelect;
      //fFillSelect(frm.cBienBuscar,aBien,true,frm.cBienBuscar.value,0,1);
    }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked();
   //fDisabled(false,"iCveTramite,iCveTramite2,","--");
   fDisabled(false,"iCveTramite,","--");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
	/*
	if(frm.ltramitePadre.checked==false){
		fSelectSetIndexFromValue(frm.iCveTramite2,0);
	}
	if(frm.lTramiteFinalBOX.checked==true){
		frm.lTramiteFinal.value=1;
	}else{
		frm.lTramiteFinal.value=0;
	}
        if(frm.iCveTramite2.value==-1)frm.iCveTramite2.value=0;
        frm.lImprimeManual.value = frm.lImprimeManualBOX.checked==true?1:0;
  if(frm.cBienBuscar.value==-1)frm.cBienBuscar.value='';
  frm.lReqFirmaDigitalBOX.checked==true?frm.lReqFirmaDigital.value=1:frm.lReqFirmaDigital.value=0;
  frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
  */
  if(fValidaTodo()==true){
      if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
         frm.iFlag1.value = 1;
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
	/*
	if(frm.ltramitePadre.checked==false){
		fSelectSetIndexFromValue(frm.iCveTramite2,0);
	}
	if(frm.lTramiteFinalBOX.checked==true){
		frm.lTramiteFinal.value=1;
	}else{
		frm.lTramiteFinal.value=0;
	}
	if(frm.cBienBuscar.value==-1)frm.cBienBuscar.value='';
        if(frm.iCveTramite2.value==-1)frm.iCveTramite2.value=0;
   frm.lReqFirmaDigitalBOX.checked==true?frm.lReqFirmaDigital.value=1:frm.lReqFirmaDigital.value=0;
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
   frm.lImprimeManual.value = frm.lImprimeManualBOX.checked==true?1:0;
   */
   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("UpdateComplete");
        fDisabled(true);
        FRMListado.fSetDisabled(false);
        frm.iFlag1.value = 1;
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iCveTramite,");
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
   FRMListado.focus();
}
// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
      frm.iFlag1.value = 1;
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
   //FRMContenedor = fBuscaFrame("PEM0");
   window.parent.fSetTram(aDato[0]);
   //FRMContenedor.fSelectSetIndexFromValue(frm.iCveTramite, aDato[0]);
   frm.iClaveTramite.value = aDato[0];
   frm.iCveTramite.value = aDato[0];
   frm.cCveInterna.value = aDato[1];
   frm.cDscTramite.value = aDato[2];
   frm.cDscBreve.value = aDato[3];
   //frm.lImprimeManualBOX.checked = aDato[13]==1?true:false;
   //frm.lImprimeManual.value = aDato[13];
   //fAsignaCheckBox(frm.lReqFirmaDigitalBOX,aDato[4]);
   //fAsignaCheckBox(frm.lVigenteBOX,aDato[5]);
   //fSelectSetIndexFromValue(frm.cBienBuscar,-1);
   //if(aDato[7]!="")
   //   fSelectSetIndexFromValue(frm.cBienBuscar,aDato[7]);
   //else
   //   fSelectSetIndexFromText(frm.cBienBuscar,"Seleccione...");
   //if(aDato[8]=="null")
   //   frm.cObjTramite.value = "";
   //else
   //   frm.cObjTramite.value = aDato[8];
   //   frm.cComprobante.value = aDato[9];
   //   frm.cNotas.value = aDato[10];
   //if((aDato[11]!="" || aDato[11]!="null") && aDato[11] > 0 ){
//	   fSelectSetIndexFromValue(frm.iCveTramite2,aDato[11]);
//	   frm.ltramitePadre.checked=true;
 //  }else{
//	   fSelectSetIndexFromValue(frm.iCveTramite2,-1);
//	   frm.ltramitePadre.checked=false;
 //  }
 //  if(aDato[12]>=1){
//	   frm.lTramiteFinalBOX.checked=true;
//   }else{
//	   frm.lTramiteFinalBOX.checked=false;
//   }
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
      cMsg = fValElements("cDscTramite,cDscBreve,cObjTramite,cNotas,cComprobante,cDscTramite");
      
      if(fEvaluaCampo(frm.cDscTramite.value)==false)          //evalua los parametros que son aceptados en el
       cMsg = cMsg+"\n Parametro incorrecto Descripción";    // cDscTramite como son parentesis
      
      //if(frm.cObjTramite.value!="")
      //  if(fEvaluaCampo(frm.cObjTramite.value)==false)          //evalua los parametros que son aceptados en el
      //   cMsg = cMsg+"\n Parametro incorrecto Objetivo";       // cObjTramite como son parentesis
      
      //if(frm.cComprobante.value!="")
      //  if(fEvaluaCampo(frm.cComprobante.value)==false)          //evalua los parametros que son aceptados en el
      //   cMsg = cMsg+"\n Parametro incorrecto Comprovante";       // cComprobante como son parentesis
      
     // if(frm.cNotas.value!="")
     //   if(fEvaluaCampo(frm.cNotas.value)==false)          //evalua los parametros que son aceptados en
     //    cMsg = cMsg+"\n Parametro incorrecto Notas";       //Notas como son parentesis
      
      if(frm.cDscTramite.value!="")
        if(fEvaluaCampo(frm.cDscTramite.value)==false)          //evalua los parametros que son aceptados en
         cMsg = cMsg+"\n Parametro incorrecto Descripción";       //Notas como son parentesis
      if(frm.cDscBreve.value!="")
        if(fEvaluaCampo(frm.cDscBreve.value)==false)          //evalua los parametros que son aceptados en
         cMsg = cMsg+"\n Parametro incorrecto Descripción Breve";       //Notas como son parentesis
      
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
function fGetCveTra(){
   return frm.iClaveTramite.value;
}
function fGetDscTra(){
   return frm.cDscTramite.value;
}
function fGetObjTra(){
//   return frm.cObjTramite.value;
}
function fSetValues(iCveTramite){
	frm.iCveTramite.value = iCveTramite;
        fReposicionaListado(FRMListado,"0", frm.iCveTramite.value);

  }
function fLlenaComboBien(){
//return fEngSubmite("pgTRAPropiedad.jsp","Bien");
}
function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if (fRaros2(cVCadena))
         return false;  //Este regresa a la funcion fValidaTodo en donde está la sig instrucción if(fEvaluaCampo(frm.cDscTramite.value)==false)
    else
      return true;
 }

function fRaros2(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"^") ||
       fEncCaract(cVCadena.toUpperCase(),"`") ||
       fEncCaract(cVCadena.toUpperCase(),"´") ||
       fEncCaract(cVCadena.toUpperCase(),"¨") )
       return true;
   else
      return false;
}

function fReporte(){
  cClavesModulo = "2,2,2,";
  cNumerosRep = "7,8,11,";
  cFiltrosRep = " " + cSeparadorRep + " " + cSeparadorRep + frm.iCveTramite.value + cSeparadorRep;
  fReportes();
}

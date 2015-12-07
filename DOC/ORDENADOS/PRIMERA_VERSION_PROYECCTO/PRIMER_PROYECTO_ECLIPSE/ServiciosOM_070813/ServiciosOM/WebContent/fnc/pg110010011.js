// MetaCD=1.0

// Title: pg110010011.js
// Description: JS "Catálogo" de la entidad GRLOficina
// Company: Tecnología InRed S.A. de C.V.
// Author: Arturo L Peña
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110010011.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  JSSource("pais.js");
  JSSource("estados.js");
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",0,"100%","","center");
      TextoSimple(cTitulo);
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
        InicioTabla("ETablaInfo",0,"85%","","","",1);
          ITRTD("ETablaST",10,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Consecutivo:","iCveOficina","",3,3,"Clave","fMayus(this);");
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Descripción:",120,2,"cDscOficina","","Descripción","","fMayus(this);",'onkeydown="fMxTx(this,150);"',"","","","EEtiquetaL","5");
           FITR();
              //TDEtiAreaTexto(true,"EEtiqueta",0,"Desc. Breve:",25,2,"cDscBreve","","Desc. Breve","","fMayus(this);",'onkeydown="fMxTx(this,50);"');
              TDEtiCampo(true,"EEtiqueta",0,"Desc. Breve:","cDscBreve","",50,50,"Desc. Breve","fMayus(this);","","","","EEtiquetaL","5");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Categoría:","iCveCategoria","","","","","EEtiquetaL","7");
           FITR();
             TDEtiSelect(true,"EEtiqueta",0,"País:","iCvePais","fPaisOnChange();fEstadoOnChange();");
           //FITR();
             TDEtiSelect(true,"EEtiqueta",0,"Estado:","iCveEntidadFed","fEstadoOnChange();");
           //FITR();
             TDEtiSelect(true,"EEtiqueta",0,"Municipio:","iCveMunicipio","");
             FITR();
              //TDEtiAreaTexto(true,"EEtiqueta",0,"Calle y No.:",25,2,"cCalleYNo","","Calle y No.","","fMayus(this);",'onkeydown="fMxTx(this,100);"');
              TDEtiCampo(false,"EEtiqueta",0,"Calle y No.:","cCalleYNo","",100,100,"Calle y No.","fMayus(this);","","","","EEtiquetaL","9");
           FITR();
              //TDEtiAreaTexto(true,"EEtiqueta",0,"Colonia:",25,2,"cColonia","","Colonia","","fMayus(this);",'onkeydown="fMxTx(this,75);"');
              TDEtiCampo(false,"EEtiqueta",0,"Colonia:","cColonia","",50,75,"Colonia","fMayus(this);","","","","EEtiquetaL","3");
           //FITR();
              TDEtiCampo(false,"EEtiqueta",0,"C.P.:","cCodPostal","",5,5,"C.P.","fMayus(this);");
           FITR();
              //TDEtiAreaTexto(true,"EEtiqueta",0,"Titular:",25,2,"cTitular","","Titular","","fMayus(this);",'onkeydown="fMxTx(this,100);"');
              TDEtiCampo(false,"EEtiqueta",0,"Titular:","cTitular","",100,100,"Titular","fMayus(this);","","","","EEtiquetaL","9");
           FITR();
              //TDEtiAreaTexto(true,"EEtiqueta",0,"Teléfono:",25,2,"cTelefono","","Teléfono","","fMayus(this);",'onkeydown="fMxTx(this,50);"');
              TDEtiCampo(false,"EEtiqueta",0,"Teléfono:","cTelefono","",50,50,"Teléfono","fMayus(this);","","","","EEtiquetaL","7");
           FITR();
              //TDEtiAreaTexto(true,"EEtiqueta",0,"Correo Electrónico:",25,2,"cCorreoE","","Correo Electrónico","","fMayus(this);",'onkeydown="fMxTx(this,50);"');
              TDEtiCampo(false,"EEtiqueta",0,"Correo Electrónico:","cCorreoE","",50,50,"Correo Electrónico","","","","","EEtiquetaL","9");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Oficina de Ingresos:","iCveOficIngresos","",10,10,"Oficina de Ingresos...","","","","","","");
              TDEtiCampo(true,"EEtiqueta",0,"Unidad Admva.:","iCveUnidadMVA","",10,10,"Unidad MVA...","","","","","","");
              TDEtiCampo(true,"EEtiqueta",0,"Oficina CIS:","iCveOficinaCIS","",10,10,"Área CIS...","","","","","","");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
              Hidden("lVigente","");
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
      Hidden("hdFiltroMun","");
      Hidden("hdOrdenMun","");
      Hidden("");
      Hidden("");
  fFinPagina(); }
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Consecutivo,Desc. Breve,Categoría,País,Estado,Municipio,Vigente,");
  FRMListado.fSetAlinea("center,left,left,left,left,left,center,");
  FRMListado.fSetDespliega("texto,texto,texto,texto,texto,texto,logico,");
  FRMListado.fSetCampos("0,2,14,15,16,17,13,");
  FRMFiltro = fBuscaFrame("IFiltro");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Consecutivo,iCveOficina,Desc. Breve,cDscBreve,");
  FRMFiltro.fSetOrdena("Consecutivo,iCveOficina,Desc. Breve,cDscBreve,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  frm.hdFiltro.value = "";
  frm.hdNumReg.value = 10000;
  fCargaCatalogoPaises();

  //fNavega();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
  return fEngSubmite("pgGRLOficina.jsp","Listado");
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
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fCancelar();
    FRMFiltro.fSetNavStatus(cNavStatus);
  }


  if(cId == "Municipio" && cError=="")
     fFillSelect(frm.iCveMunicipio,aRes,false,frm.iCveMunicipio.value,2,3);

  if (cId == "CIDCategoriaOfic" && cError == "")
     fFillSelect(frm.iCveCategoria,aRes,false,frm.iCveCategoria.value,0,1);

  fResCatalogoPaisEntidad(aRes,cId,cError);


}


// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   fFillSelect(frm.iCvePais,aPais);
if(fPaisOnChange()==true){
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  10000;
  if (fEngSubmite("pgGRLCategoriaOfic.jsp","CIDCategoriaOfic") == true){
    frm.hdNumReg.value = 100000;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveOficina,","--");
    FRMListado.fSetDisabled(true);
  }
}
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
if (frm.lVigenteBOX.checked==true)frm.lVigente.value=1;
else frm.lVigente.value=0;
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
   if(frm.lVigenteBOX.checked == true)
     frm.lVigente.value =1;
    else
     frm.lVigente.value=0;

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
   frm.hdFiltro.value = "";
   fFillSelect(frm.iCvePais,aPais,false,frm.iCvePais.value);
 if(fPaisOnChange()==true ) {
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  10000;
   if (fEngSubmite("pgGRLCategoriaOfic.jsp","CIDCategoriaOfic") == true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fDisabled(false,"iCveOficina,");
     FRMListado.fSetDisabled(true);
   }
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
   frm.iCveOficina.value = aDato[0];
   frm.cDscOficina.value = aDato[1];
   frm.cDscBreve.value = aDato[2];
   frm.cCalleYNo.value = aDato[7];
   frm.cColonia.value = aDato[8];
   frm.cCodPostal.value = aDato[9];
   frm.cTitular.value = aDato[10];
   frm.cTelefono.value = aDato[11];
   frm.cCorreoE.value = aDato[12];
   frm.lVigente.value = aDato[13];
   fAsignaSelect(frm.iCveCategoria,aDato[3],aDato[14]);
   fAsignaSelect(frm.iCvePais,aDato[4],aDato[15]);
   fAsignaSelect(frm.iCveEntidadFed,aDato[5],aDato[16]);
   fAsignaSelect(frm.iCveMunicipio,aDato[6],aDato[17]);
   frm.iCveOficIngresos.value=aDato[18];
   frm.iCveUnidadMVA.value=aDato[19];
   frm.iCveOficinaCIS.value = aDato[20];
   if (frm.lVigente.value== 1) frm.lVigenteBOX.checked=true;
    else frm.lVigenteBOX.checked=false;
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
  cMsg = fValElements("cCalleYNo,");
  if(frm.cCalleYNo.value != "")
   if(fEvaluaCampo(frm.cCalleYNo.value)==false)          //evalua los parametros que son aceptados en el
   cMsg = cMsg+"\n No se permiten \" y \' para Calle y Número";

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
function fPaisOnChange() {
   fFilterSelect(frm.iCveEntidadFed,aEstados,frm.iCvePais.value,false,frm.iCveEntidadFed.value,1,2);
   return fEstadoOnChange();
}
function fEstadoOnChange() {
   if(frm.iCveEntidadFed.value != ""){
      frm.hdOrdenMun.value = " GRLMUNICIPIO.CNOMBRE ";
      frm.hdFiltroMun.value = " iCvePais= " + frm.iCvePais.value + " AND iCveEntidadFed= " + frm.iCveEntidadFed.value;
      return fEngSubmite("pgGRLMUNICIPIO.jsp","Municipio");
   }else
      frm.iCveMunicipio.length = 0;
}

function fGetOfi(){
   return frm.iCveOficina.value;
}

 function fComilla(cVCadena){
   if (fEncCaract(cVCadena,"'") &
   fEncCaract(cVCadena,'"'))
       return true;
   else
       return false;
}

function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if ( fComilla(cVCadena))
        return false;
    else
        return true;
 }
function fCatalogoPaisCargado(){
  frm.hdFiltro.value = "iCvePais = 1";
  fCargaCatalogoEntidades();
}
function fTerminaCargaPaisEntidad(){
  fNavega();
}

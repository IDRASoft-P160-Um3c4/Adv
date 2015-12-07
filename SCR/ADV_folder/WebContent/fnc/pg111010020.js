// MetaCD=1.0
// Title: pg111010020.js
// Description: JS "Catálogo" de la entidad GRLAseguradora
// Company: Tecnología InRed S.A. de C.V.
// Author: Arturo López
var cTitulo = "";
var FRMListado = "";
var frm;
var cBien="";
var cBienSelect="";
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010020.js";
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
        IFrame("IFiltro20","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado20","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",10,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITRTD("",0,"","1","center");
          InicioTabla("",0,"75%","","","",1);
            FITR();
              ITD();Liga("Buscar Persona","fBuscaPersona();");
          FinTabla();
          ITRTD("",0,"","1","center");
          InicioTabla("",0,"75%","","","",1);
            ITR();
              TDEtiCampo(false,"EEtiqueta",0," Nombre:","cAseguradora","",100,100," Clave","fMayus(this);","","",true,"",9);
            FITR();
              TDEtiCampo(true,"EEtiqueta",0," RFC:","cRFC","",100,100," Clave Interna","fMayus(this);","","",true,"",9);
            FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Dirección:",100,3,"cDireccion","","Dirección de la aseguradora","","fMayus(this);",'onkeydown="fMxTx(this,500);"',true,true,true,"",9);
            FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Observaciones:",100,2,"cObses","","Observaciones de la aseguradora.","","fMayus(this);",'onkeydown="fMxTx(this,500);"',true,true,true,"",9);
            FITR();
              TDEtiCheckBox("EEtiqueta",0," Activa:","lActivoBOX","1",true," Vigente");
              Hidden("lActivo","");
            FTR();
          FinTabla();
        FinTabla();
      FTDTR();
      FinTabla();
      FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel20","95%","34","Paneles.js");
      FTDTR();
      Hidden("iCveAseguradora");
      Hidden("iCvePersona");
      Hidden("iCveDomicilio");
    FinTabla();
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];

  FRMPanel = fBuscaFrame("IPanel20");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");

  FRMListado = fBuscaFrame("IListado20");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Aseguradora,Vigente, ");
  FRMListado.fSetAlinea("left,center,");
  FRMListado.fSetDespliega("texto,logico,");
  FRMListado.fSetCampos("5,4,");

  FRMFiltro = fBuscaFrame("IFiltro20");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Nombre Aseguradora,cAseguradora,Consecutivo,iCveAseguradora,");
  FRMFiltro.fSetOrdena("Nombre Aseguradora,cAseguradora,Consecutivo,iCveAseguradora,");

  fDisabled(true);
  frm.hdBoton.value="Primero";
  fNavega();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value = FRMFiltro.fGetFiltro();
  frm.hdOrden.value  = FRMFiltro.fGetOrden();
  frm.hdNumReg.value = 10000;
  return fEngSubmite("pgGRLAseguradora.jsp","Listado");
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
  else{
    if(cId == "Listado"){
      frm.hdRowPag.value = iRowPag;
      FRMListado.fSetListado(aRes);
      FRMListado.fShow();
      FRMListado.fSetLlave(cLlave);
      fCancelar();
      FRMFiltro.fSetNavStatus(cNavStatus);
    }
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked();
   fDisabled(false,"cAseguradora,cRFC,cDireccion,");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  frm.lActivoBOX.checked==true?frm.lActivo.value=1:frm.lActivo.value=0;
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
   frm.lActivoBOX.checked==true?frm.lActivo.value=1:frm.lActivo.value=0;
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
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"cAseguradora,cRFC,cDireccion,");
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
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
  frm.iCveAseguradora.value = aDato[0];
  frm.iCvePersona.value = aDato[1];
  frm.iCveDomicilio.value = aDato[2];
  frm.cAseguradora.value = aDato[5];
  frm.cRFC.value = aDato[6];
  frm.cDireccion.value = aDato[7]+" # "+aDato[8]+(aDato[9]!=""?(" int "+aDato[9]):"") + " Col. "+aDato[10];
  frm.lActivoBOX.checked = aDato[4]==1?true:false;
  frm.lActivo.value = aDato[4];
  frm.cObses.value = aDato[3];
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
      cMsg = fValElements("");
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

function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if (fRaros2(cVCadena))
         return false;
    else
      return true;
 }

function fRaros2(cVCadena){
   if ( fEncCaract(cVCadena.toUpperCase(),"^") ||
        fEncCaract(cVCadena.toUpperCase(),"`") ||
        fEncCaract(cVCadena.toUpperCase(),"´") ||
        fEncCaract(cVCadena.toUpperCase(),"¨") )
       return true;
   else
      return false;
}

function fGetlPermiteBusqueda(){
  return true;
}

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,
			 cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,iCveDomicilio,
                         iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,
                         cCodPostal,cTelefono,iCvePais,cDscPais,iCveEntidadFed,
                         cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,cDscLocalidad,
                         lPredeterminado,cDscTipoDomicilio,cDscDomicilio){
    frm.iCvePersona.value   = iCvePersona;
    frm.iCveDomicilio.value = iCveDomicilio;
    frm.cAseguradora.value  = cNomRazonSocial+" "+cApPaterno+" "+cApMaterno;
    frm.cRFC.value          = cRFC;
    frm.cDireccion.value    = cDscDomicilio;
}

function fBuscaPersona(){
  if(frm.cObses.disabled==false)
    fAbreSolicitante();
}
function fGetParametrosConsulta(buscaPer){
  buscaPer.setShowValues(true,false,false);
}

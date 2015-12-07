// MetaCD=1.0
// Title: pg110020011.js
// Description: JS "Catálogo" de la entidad GRLProceso
// Company: Tecnología InRed S.A. de C.V.
// Author: Hanksel Fierro Medina
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110020011.js";
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
        IFrame("IFiltro11","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"","175","center","top");
        IFrame("IListado11","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Consecutivo:","iCveProceso","",3,3,"ClaveProceso","fMayus(this);");
           FITR();
              TDEtiAreaTexto(false,"EEtiqueta",0,"Proceso:",50,5,"cDscProceso","","DescripcionProceso","","fMayus(this);",'onkeydown="fMxTx(this,200);"');
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Trámite:","cDscCveTramite","");
              Hidden("iCveTramite","");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
              Hidden("lVigente","");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Proceso Certificado:","lProcesoCertificadoBOX","1",true,"ProcesoCertificado");
              Hidden("lProcesoCertificado","");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Referencia o Código:","cDscCertificacion","",75,75,"DescripcionCertificacion","fMayus(this);");
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel11","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
    Hidden("iCveModalidad");
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel11");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado11");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Consecutivo,Proceso,Descripción de Trámite,Vigente,Proceso Certificado,Referencia o Código,");
  FRMListado.fSetAlinea("center,left,left,center,center,left,");
  FRMListado.fSetDespliega("texto,texto,texto,logico,logico,texto,");
  FRMListado.fSetCampos("0,1,7,3,4,5,");
  FRMFiltro = fBuscaFrame("IFiltro11");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Tramite,TRACatTramite.cDscBreve,Proceso,cDscProceso,");
  FRMFiltro.fSetOrdena("Tramite,TRACatTramite.cDscBreve,Proceso,cDscProceso,Consecutivo,GRLProceso.iCveProceso,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  //fNavega();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
  return fEngSubmite("pgGRLProceso.jsp","Listado");
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

  if(cId == "idTramite" && cError==""){
   fFillSelect(frm.cDscCveTramite,aRes,false,frm.cDscCveTramite.value,0,3);
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
  frm.hdNumReg.value = 100000;
  frm.hdBoton.value = "Primero";
  frm.hdOrden.value = "TRACatTramite.cDscBreve";
  frm.hdFiltro.value = " 1=1 ";
  if(fEngSubmite("pgTRACatTramite.jsp","idTramite")==true){
       frm.hdNumReg.value = 100000;
       frm.hdBoton.value = "Primero";
       frm.hdOrden.value = "TRAModalidad.iCveModalidad";
       frm.hdFiltro.value = " 1=1 ";
       if(fEngSubmite("pgTRAModalidad.jsp","idModalidad")==true){
            FRMPanel.fSetTraStatus("UpdateBegin");
            fBlanked();
            fDisabled(false,"iCveProceso,","--");
            FRMListado.fSetDisabled(true);
       }
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){


if(frm.lProcesoCertificadoBOX.checked)
frm.lProcesoCertificado.value = 1;
else
frm.lProcesoCertificado.value = 0;
if(frm.lVigenteBOX.checked)
frm.lVigente.value = 1;
else
frm.lVigente.value = 0;

   frm.iCveTramite.value = frm.cDscCveTramite.value;


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
if(frm.lProcesoCertificadoBOX.checked)
frm.lProcesoCertificado.value = 1;
else
frm.lProcesoCertificado.value = 0;
if(frm.lVigenteBOX.checked)
frm.lVigente.value = 1;
else
frm.lVigente.value = 0;

   frm.iCveTramite.value = frm.cDscCveTramite.value;



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
if(frm.lProcesoCertificadoBOX.checked)
frm.lProcesoCertificado.value = 1;
else
frm.lProcesoCertificado.value = 0;

if(frm.lVigenteBOX.checked)
frm.lVigente.value = 1;
else
frm.lVigente.value = 0;

   frm.iCveTramite.value = frm.cDscCveTramite.value;



   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iCveProceso,");
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
   frm.iCveProceso.value = aDato[0];
   frm.cDscProceso.value = aDato[1];
   frm.iCveTramite.value = aDato[2];
   frm.lVigente.value = aDato[3];
   if(frm.lVigente.value == 1) frm.lVigenteBOX.checked=true;
      else frm.lVigenteBOX.checked=false;
   frm.lProcesoCertificado.value = aDato[4];
   if(frm.lProcesoCertificado.value == 1)frm.lProcesoCertificadoBOX.checked=true;
      else frm.lProcesoCertificadoBOX.checked=false;
   frm.cDscCertificacion.value = aDato[5];
   fAsignaSelect(frm.cDscCveTramite,aDato[2],aDato[7]);
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   cMsg = fValElements("cDscCertificacion,");

   if(!fSoloAlfanumericosLocal(frm.cDscCertificacion.value))
     cMsg += "\n - El campo 'Descripción  de Certificación:' solo permite carácteres alfanuméricos. \n (Números, Letras y Punto)";

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
function fSoloAlfanumericosLocal(cVCadena){
    if ( fRaros(cVCadena)      ||  fSignosLocal(cVCadena)     ||
         fParentesis(cVCadena) ||  fDiagonal(cVCadena) )
        return false;
    else
        return true;
}
function fSignosLocal(cVCadena){
   if (fEncCaract(cVCadena,"+"))
       return true;
   else
       return false;
}

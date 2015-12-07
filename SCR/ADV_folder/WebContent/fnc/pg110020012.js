// MetaCD=1.0
// Title: pg110020012
// Description: JS "Catálogo" de la entidad GRLProducto
// Company: Tecnología InRed S.A. de C.V.
// Author: Hanksel Fierro Medina
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110020012.js";
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
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Consecutivo:","iCveProducto","",5,5,"Clave","fMayus(this);");
           FITR();
              TDEtiAreaTexto(false,"EEtiqueta",0,"Descripción:",25,2,"cDscProducto","","Descripción","","fMayus(this);",'onkeydown="fMxTx(this,150);"');
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Desc. Breve:","cDscBreve","",50,50,"Desc. Breve","fMayus(this);");
           //FITR();
              //TDEtiCheckBox("EEtiqueta",0,"Req. Firma Digital:","lReqFirmaDigitalBOX","1",true,"Req. Firma Digital");
              Hidden("lReqFirmaDigital",0);
           //FITR();
           //   TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
              Hidden("lVigente",1);
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel20","95%","34","Paneles.js");
      FTDTR();
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
  
  //FRMListado.fSetTitulo("Consecutivo,Descripción,Desc. Breve,Req. Firma Digital,Vigente,");
  //FRMListado.fSetAlinea("center,left,left,center,center,");	//SE MODIFICA EL LISTADO PARA NO MOSTRAR (VGIENTE, REQ FIRMA DIGITAL)
  //FRMListado.fSetDespliega("texto,texto,texto,logico,logico,");
  
  FRMListado.fSetTitulo("Consecutivo,Descripción,Desc. Breve,");
  FRMListado.fSetAlinea("center,center,center,");
  FRMListado.fSetDespliega("texto,texto,texto,");
  FRMListado.fSetCampos("0,1,2,");

  
  FRMFiltro = fBuscaFrame("IFiltro20");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow();
  FRMFiltro.fSetFiltra("Consecutivo,iCveProducto,Descripción,cDscProducto,");
  FRMFiltro.fSetOrdena("Consecutivo,iCveProducto,Descripción,cDscProducto,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  //fNavega();
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
  return fEngSubmite("pgGRLProducto.jsp","Listado");
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
}

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked();
   fDisabled(false,"iCveProducto,","--");
   FRMListado.fSetDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){

//if(frm.lReqFirmaDigitalBOX.checked)
// frm.lReqFirmaDigital.value = 1;
//else
// frm.lReqFirmaDigital.value = 0;
									//COMENTADO PARA NO MOSTRAR LOS CAMPOS (VIGENTE, REQUIERE FIRMA DIGITAL)
//if(frm.lVigenteBOX.checked)
//frm.lVigente.value = 1;
//else
//frm.lVigente.value = 0;

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
//if(frm.lReqFirmaDigitalBOX.checked)
// frm.lReqFirmaDigital.value = 1;
//else
// frm.lReqFirmaDigital.value = 0;

//if(frm.lVigenteBOX.checked)
//frm.lVigente.value = 1;
//else
//frm.lVigente.value = 0;

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
//if(frm.lReqFirmaDigitalBOX.checked)
// frm.lReqFirmaDigital.value = 1;
//else
// frm.lReqFirmaDigital.value = 0;

//if(frm.lVigenteBOX.checked)
//frm.lVigente.value = 1;
//else
//frm.lVigente.value = 0;

   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iCveProducto,");
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
   frm.iCveProducto.value = aDato[0];
   frm.cDscProducto.value = aDato[1];
   frm.cDscBreve.value = aDato[2];
   frm.lReqFirmaDigital.value = aDato[3];
   frm.lVigente.value = aDato[4];
   //if (frm.lReqFirmaDigital.value == 1)frm.lReqFirmaDigitalBOX.checked = true
   //   else frm.lReqFirmaDigitalBOX.checked = false;
   //if (frm.lVigente.value == 1)frm.lVigenteBOX.checked = true
   //   else frm.lVigenteBOX.checked = false;
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

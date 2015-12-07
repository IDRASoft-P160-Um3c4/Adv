// MetaCD=1.0 
// Title: pg11004002.js
// Description: JS "Catálogo" de la entidad GRLBanco
// Company: Tecnología InRed S.A. de C.V. 
// Author: Richard Maritnez
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg110040020.js";
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
             TDEtiCampo(true,"EEtiqueta",0," Consecutivo:","iCveBanco","",3,3," Clave","fMayus(this);"); 
           FITR(); 
              TDEtiAreaTexto(true,"EEtiqueta",0," Descripción:",25,2,"cDscBanco",""," Descripción","","fMayus(this);",'onkeydown="fMxTx(this,30);"'); 
           FITR(); 
              TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente"); 
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
  fFinPagina(); 
} 
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo(" Consecutivo, Descripción, Vigente,"); 
  FRMListado.fSetAlinea("center,left,center,");
  FRMListado.fSetDespliega("texto,texto,logico,");
  FRMFiltro = fBuscaFrame("IFiltro"); 
  FRMFiltro.fSetControl(self); 
  FRMFiltro.fShow(); 
  FRMFiltro.fSetFiltra(" Consecutivo,iCveBanco, Descripción,cDscBanco,"); 
  FRMFiltro.fSetOrdena(" Consecutivo,iCveBanco, Descripción,cDscBanco,"); 
  fDisabled(true); 
  frm.hdBoton.value="Primero"; 
  fNavega(); 
} 
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){ 
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
  frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
  return fEngSubmite("pgGRLBanco.jsp","Listado"); 
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
   fDisabled(false,"iCveBanco,","--"); 
   FRMListado.fSetDisabled(true); 
} 
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){ 
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
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
   frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
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
   fDisabled(false,"iCveBanco,"); 
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
   frm.iCveBanco.value = aDato[0]; 
   frm.cDscBanco.value = aDato[1]; 
   frm.lVigente.value = aDato[2]; 
   frm.lVigente.value==1?frm.lVigenteBOX.checked=true:frm.lVigenteBOX.checked=false;
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

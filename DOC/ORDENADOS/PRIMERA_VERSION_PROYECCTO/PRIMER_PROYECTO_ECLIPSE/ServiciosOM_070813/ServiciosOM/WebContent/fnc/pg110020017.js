// MetaCD=1.0 
// Title: pg110020017.js
// Description: JS "Catálogo" de la entidad GRLConcVerXProd
// Company: Tecnología InRed S.A. de C.V. 
// Author: Hanksel Fierro Medina
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg110020017.js";
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
        IFrame("IFiltro17","95%","34","Filtros.js"); 
      FTDTR(); 
      ITRTD("",0,"","175","center","top"); 
        IFrame("IListado17","95%","170","Listado.js","yes",true); 
      FTDTR(); 
      ITRTD("",0,"","1","center"); 
        InicioTabla("ETablaInfo",0,"75%","","","",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple(cTitulo); 
          FTDTR(); 
          ITR(); 
             TDEtiSelect(true,"EEtiqueta",0,"ClaveProceso:","iCveProceso",""); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0,"Clave:","iCveProducto",""); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0,"ClaveConcVerifica:","iCveConcVerifica",""); 
           FITR(); 
              TDEtiCheckBox("EEtiqueta",0,"Mandatorio:","lMandatorioBOX","1",true,"Mandatorio"); 
              Hidden("lMandatorio","");
           FTR(); 
        FinTabla(); 
      FTDTR(); 
      FinTabla(); 
    FTDTR(); 
      ITRTD("",0,"","40","center","bottom"); 
        IFrame("IPanel17","95%","34","Paneles.js"); 
      FTDTR(); 
    FinTabla(); 
  fFinPagina(); 
} 
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel17"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado17"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo("ClaveProceso,Clave,ClaveConcVerifica,Mandatorio,"); 
  FRMListado.fSetCampos("4,4,6,3,");
  FRMFiltro = fBuscaFrame("IFiltro17"); 
  FRMFiltro.fSetControl(self); 
  FRMFiltro.fShow(); 
  FRMFiltro.fSetFiltra("ClaveProceso,iCveProceso,Clave,iCveProducto,"); 
  FRMFiltro.fSetOrdena("ClaveProceso,iCveProceso,Clave,iCveProducto,"); 
  fDisabled(true); 
  frm.hdBoton.value="Primero"; 
  fNavega(); 
} 
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){ 
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
  frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
  return fEngSubmite("pgGRLConcVerXProd.jsp","Listado"); 
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


  if(cId == "idProceso" && cError==""){
   fFillSelect(frm.iCveProceso,aRes,false,frm.iCveProceso.value,0,1);
  }
  if(cId == "idProducto" && cError==""){
    fFillSelect(frm.iCveProducto,aRes,false,frm.iCveProducto.value,0,1);
  }
  if(cId == "idConcVerifica" && cError==""){
   fFillSelect(frm.iCveConcVerifica,aRes,false,frm.iCveConcVerifica.value,0,1);
  }

} 
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){ 
  frm.hdNumReg.value = 100000;
  frm.hdBoton.value = "Primero";
  frm.hdOrden.value = "GRLProceso.iCveProceso"; 
  frm.hdFiltro.value = " 1=1 ";
  if(fEngSubmite("pgGRLProceso.jsp","idProceso")==true){
       frm.hdNumReg.value = 100000;
       frm.hdBoton.value = "Primero";
       frm.hdOrden.value = "GRLProducto.iCveProducto"; 
       frm.hdFiltro.value = " 1 = 1 ";
       if(fEngSubmite("pgGRLProducto.jsp","idProducto")==true){
            frm.hdNumReg.value = 100000;
            frm.hdBoton.value = "Primero";
            frm.hdOrden.value = "GRLConcVerifica.iCveConcVerifica"; 
            frm.hdFiltro.value = " 1=1 ";
            if(fEngSubmite("pgGRLConcVerifica.jsp","idConcVerifica")==true){
                FRMPanel.fSetTraStatus("UpdateBegin"); 
                fBlanked(); 
                fDisabled(false,"","--"); 
                FRMListado.fSetDisabled(true);
            }
        }
   } 
} 
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){ 
if(frm.lMandatorioBOX.checked)
 frm.lMandatorio.value = 1;
else
 frm.lMandatorio.value = 0;

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
if(lMandatorioBOX.checked)
 frm.lMandatorio.value = 1;
else
 frm.lMandatorio.value = 0;


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

if(lMandatorioBOX.checked)
 frm.lMandatorio.value = 1;
else
 frm.lMandatorio.value = 0;


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
   fAsignaSelect(frm.iCveProceso,aDato[0],aDato[4]);
   fAsignaSelect(frm.iCveProducto,aDato[1],aDato[5]);
   fAsignaSelect(frm.iCveConcVerifica,aDato[2],aDato[6]);
   frm.iCveProceso.value = aDato[0]; 
   frm.iCveProducto.value = aDato[1]; 
   frm.iCveConcVerifica.value = aDato[2]; 
   frm.lMandatorio.value = aDato[3]; 
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

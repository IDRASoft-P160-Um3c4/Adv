// MetaCD=1.0 
// Title: pg110010021.js
// Description: JS "Cat�logo" de la entidad GRLDepartamento
// Company: Tecnolog�a InRed S.A. de C.V. 
// Author: Hanksel Fierro Medina
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg110010021.js";
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
        IFrame("IFiltro21","95%","34","Filtros.js"); 
      FTDTR(); 
      ITRTD("",0,"","175","center","top"); 
        IFrame("IListado21","95%","170","Listado.js","yes",true); 
      FTDTR(); 
      ITRTD("",0,"","1","center"); 
        InicioTabla("ETablaInfo",0,"75%","","","",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple(cTitulo); 
          FTDTR(); 
          ITR(); 
             TDEtiCampo(true,"EEtiqueta",0,"Consecutivo","iCveDepartamento","",3,3," Comentario: %AttDef","fMayus(this);"); 
           FITR(); 
              TDEtiAreaTexto(true,"EEtiqueta",0,"Descripci�n departamento",75,2,"cDscDepartamento",""," Comentario: %AttDef","","fMayus(this);",'onkeydown="fMxTx(this,150);"'); 
           FITR(); 
              //TDEtiAreaTexto(false,"EEtiqueta",0,"Descripci�n corta",25,2,"cDscBreve",""," Comentario: %AttDef","","fMayus(this);",'onkeydown="fMxTx(this,50);"');
              TDEtiCampo(false,"EEtiqueta",0,"Descripci�n corta","cDscBreve","",75,50,"Descripci�n corta","fMayus(this);"); 
           FITR(); 
              TDEtiCheckBox("EEtiqueta",0,"Vigente","lVigenteBOX","1",true," Comentario: %AttDef");
               
              Hidden("lVigente","");
           FTR(); 
        FinTabla(); 
      FTDTR(); 
      FinTabla(); 
    FTDTR(); 
      ITRTD("",0,"","40","center","bottom"); 
        IFrame("IPanel21","95%","34","Paneles.js"); 
      FTDTR(); 
    FinTabla(); 
  fFinPagina(); 
} 
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel21"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado21"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo(" Consecutivo,Descripci�n departamento, Descripci�n corta,Vigente,");
  FRMListado.fSetAlinea("center,left,left,center,");
  FRMListado.fSetDespliega("texto,texto,texto,logico,"); 
  FRMListado.fSetCampos("0,1,2,3,");
  FRMFiltro = fBuscaFrame("IFiltro21"); 
  FRMFiltro.fSetControl(self); 
  FRMFiltro.fShow(); 
  FRMFiltro.fSetFiltra(" Consecutivo,iCveDepartamento, Descripci�n departamento,cDscDepartamento,"); 
  FRMFiltro.fSetOrdena(" Consecutivo,iCveDepartamento, Descripci�n departamento,cDscDepartamento,"); 
  fDisabled(true); 
  frm.hdBoton.value="Primero"; 
  fNavega(); 
} 
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){ 
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
  frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
  frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value; 
  return fEngSubmite("pgGRLDepartamento.jsp","Listado"); 
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
   fDisabled(false,"iCveDepartamento,","--"); 
   FRMListado.fSetDisabled(true); 
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){ 
if( frm.lVigenteBOX.checked ) frm.lVigente.value = 1;
   else frm.lVigente.value = 0;
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
if( frm.lVigenteBOX.checked ) frm.lVigente.value = 1;
   else frm.lVigente.value = 0;
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
   fDisabled(false,"iCveDepartamento,"); 
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
   frm.iCveDepartamento.value = aDato[0]; 
   frm.cDscDepartamento.value = aDato[1]; 
   frm.cDscBreve.value = aDato[2]; 
   fAsignaCheckBox(frm.lVigenteBOX,aDato[3]); 
} 
// FUNCI�N donde se generan las validaciones de los datos ingresados
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

function fGetCveDep(){
return frm.iCveDepartamento.value;
}

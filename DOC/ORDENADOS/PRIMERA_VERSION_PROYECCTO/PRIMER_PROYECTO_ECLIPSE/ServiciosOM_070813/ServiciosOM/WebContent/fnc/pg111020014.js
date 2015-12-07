// MetaCD=1.0 
// Title: pg111020014.js
// Description: JS "Cat�logo" de la entidad TRARegRefPago
// Company: Tecnolog�a InRed S.A. de C.V. 
// Author: ahernandez
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg111020014.js";
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
        IFrame("IFiltro14","95%","34","Filtros.js"); 
      FTDTR(); 
      ITRTD("",0,"","175","center","top"); 
        //IFrame("IListado14","95%","170","Listado.js","yes",true); 
       IFrame("IListado14","0","0","Listado.js","yes",true); 
      FTDTR(); 
      ITRTD("",0,"","1","center",""); 
        InicioTabla("ETablaInfo",0,"75%","100","","top",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple(cTitulo); 
          FTDTR(); 
          ITR();               
              TDEtiCampo(true,"EEtiqueta",0," Solicitud:","iNumSolicitud","",8,8," Solicitud","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Homoclave:","cCveCofemer","",8,8," Homoclave","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Tr�mite:","iCveTramite","",8,8," Tr�mite","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Descripci�n:","cDscTramite","",8,8," Descripci�n","fMayus(this);"); 
              TDEtiSelect(true,"EEtiqueta",0," Departamento:","iCveDepartamento",""); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Ref. Num�rica:","cRefNumerica","",8,8," Ref. Num�rica","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Ref. Num�rica:","cRefAlfaNum","",8,8," Ref. Num�rica","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Importe a Pagar:","dImportePagar","",8,8," Importe a Pagar","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Formato SAT:","cFormatoSAT","",15,15," Formato SAT","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Fecha de Pago:","dtPago","",10,10," Fecha de Pago","fMayus(this);"); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0," Banco:","iCveBanco",""); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Pago:","dImportePagado","",10,10," Pago","fMayus(this);"); 
           FTR(); 
        FinTabla(); 
      FTDTR(); 
      FinTabla(); 
    FTDTR(); 
      ITRTD("",0,"","40","center","bottom"); 
        IFrame("IPanel14","95%","34","Paneles.js"); 
      FTDTR(); 
    FinTabla(); 
Hidden("iConsecutivo","2");
  fFinPagina(); 
} 
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel14"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado14"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo(" N�m. Solicitud, Homoclave, Clave Tr�mite,Nombre Tramite,Departamento,Ref. Num�rica, Ref. Alfanum�rica, Importe Pagado, Formato SAT, Fecha de Pago, Banco, Importe a Pagar"); 
  FRMListado.fSetCampos("0,7,8,9,11,1,2,4,6,5,12,3,"); 
FRMFiltro = fBuscaFrame("IFiltro14"); 
  FRMFiltro.fSetControl(self); 
  FRMFiltro.fShow("Fil,");
  //Filtra por RFC o RPA
  afiltra= new Array();
  afiltra[0]=["=","="];
  FRMFiltro.fEspecial(afiltra);
  FRMFiltro.fSetFiltra(" N�m. Solicitud,iNumSolicitud,"); 
  FRMFiltro.fSetOrdena(" N�m. Solicitud,iNumSolicitud,"); 
  fDisabled(true); 
frm.hdBoton.value="Primero"; 
  //fNavega(); 
} 
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){ 
  frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
  if(FRMFiltro.fGetFiltro()=="")
    frm.hdFiltro.value="";
  else
    frm.hdFiltro.value = " TRARegSolicitud." + FRMFiltro.fGetFiltro();
 return fEngSubmite("pgTRARegRefPago.jsp","Listado"); 
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

if(cId=="CIDBanco" && cError==""){
     fFillSelect(frm.iCveBanco,aRes,false,frm.iCveBanco.value,0,1);
fAlert("Hola");
  } 
if(cId=="CIDDepto" && cError==""){
     fFillSelect(frm.iCveDepartamento,aRes,false,frm.iCveDepartamento.value,0,1);
  } 

 //muestra los datos
  if(cId == "Listado" && cError==""){
      if(aRes ==""){
          fAlert("No existe");
          fNuevo();
        }
     else{
       FRMListado.fSetListado(aRes); 
       FRMListado.fShow();
      //habilita los campos
       
fdos();FRMPanel.fSetTraStatus("UpdateBegin"); 
       fDisabled(false,"iNumSolicitud,cCveCofemer,iCveTramite,cDscTramite,cRefAlfaNum,iCveDepartamento,cRefNumerica,dImportePagado,cFormatoSAT,");
       FRMListado.fSetDisabled(true); 
       fdos();
       fBlanked("iNumSolicitud,cCveCofemer,iCveTramite,cDscTramite,cRefAlfaNum,cRefNumerica,dImportePagado,cFormatoSAT,");
     //fModificar();
    }
 
  }

} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
function fNuevo(){ 
   
if(fEngSubmite("pgGRLBanco.jsp","CIDBanco")==true){
 if(fEngSubmite("pgGRLDepartamento.jsp","CIDDepto")==true){
   FRMPanel.fSetTraStatus("UpdateBegin"); 
   fBlanked(); 
   fDisabled(false,"iEjercicio,","--"); 
   FRMListado.fSetDisabled(true); 
  }
 }
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){ 
   if(fValidaTodo()==true){
      if(fEngSubmite("pgTRARegRefPago.jsp","Listado")==true){
         FRMPanel.fSetTraStatus("UpdateComplete"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
      }
   }
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
function fGuardarA(){ 
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
   fDisabled(false,"iNumSolicitud,cCveCofemer,iCveTramite,cDscTramite,cRefAlfaNum,cRefNumerica,dImportePagado,cFormatoSAT,");
   FRMListado.fSetDisabled(true); 
   fDepto();fBanco();
  

  //}
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
  // frm.iEjercicio.value = aDato[0]; 
   frm.iNumSolicitud.value = aDato[0]; 
  // frm.iConsecutivo.value = aDato[2]; 
   frm.iCveTramite.value = aDato[8]; 
   frm.cDscTramite.value = aDato[9]; 
   frm.iCveDepartamento.value = aDato[11]; 
   frm.cRefAlfaNum.value = aDato[2];
   frm.cRefNumerica.value = aDato[1]; 
   frm.dImportePagar.value = aDato[3]; 
   frm.cFormatoSAT.value = aDato[6];
   frm.dImportePagado.value = aDato[4]; 
   frm.dtPago.value = aDato[5]; 
   frm.iCveBanco.value = aDato[12]; 
   frm.cCveCofemer.value = aDato[7]; 
   

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
function fDepto(){ 
        
      fEngSubmite("pgGRLDepartamento.jsp","CIDDepto");
      fAlert("entro depto");
} 
function fBanco(){ 
        
      fEngSubmite("pgGRLBanco.jsp","CIDBanco");
fAlert("entro banco");
} 
function fdos(){ 
      
fDepto ();
 fBanco();
} 

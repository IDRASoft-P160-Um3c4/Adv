// MetaCD=1.0 
// Title: pg111010042.js
// Description: JS "Cat�logo" de la entidad TRAGpoRequisito
// Company: Tecnolog�a InRed S.A. de C.V. 
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg111010042.js";
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
        IFrame("IFiltro42","95%","34","Filtros.js"); 
      FTDTR(); 
      ITRTD("",0,"","175","center","top"); 
        IFrame("IListado42","95%","170","Listado.js","yes",true); 
      FTDTR(); 
      ITRTD("",0,"","1","center"); 
        InicioTabla("ETablaInfo",0,"75%","","","",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple(cTitulo); 
          FTDTR(); 
          ITR(); 
             TDEtiCampo(false,"EEtiqueta",0," Consecutivo:","iCveGrupo","",3,3," Clave","fMayus(this);"); 
           FITR(); 
//              TDEtiAreaTexto(false,"EEtiqueta",0," Descripci�n:",25,2,"cDscGrupo",""," Descripci�n","","fMayus(this);",'onkeydown="fMxTx(this,50);"');
             TDEtiCampo(true,"EEtiqueta",0," Descripcci�n:","cDscGrupo","",50,50," Descripcci�n","fMayus(this);"); 
           FITR(); 
             TDEtiAreaTexto(true,"EEtiqueta",0," Fundamento Legal:",100,4,"cFundLegal",""," Fundamento Legal","","fMayus(this);",'onkeydown="fMxTx(this,500);"');
             //TDEtiCampo(false,"EEtiqueta",0,"Fundamento Legal:","cDscGrupo","",50,50,"Fundamento Legal","fMayus(this);"); 
           FITR(); 

        FinTabla(); 
      FTDTR(); 
      FinTabla(); 
    FTDTR(); 
      ITRTD("",0,"","40","center","bottom"); 
        IFrame("IPanel42","95%","34","Paneles.js"); 
      FTDTR(); 
    FinTabla(); 
    Hidden("iFlag1");
  fFinPagina(); 
} 
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel42"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado42"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo(" Consecutivo, Descripci�n, ");
  
  FRMListado.fSetCampos("0,1,");
  FRMListado.fSetAlinea("center,left,");  
  FRMFiltro = fBuscaFrame("IFiltro42"); 
  FRMFiltro.fSetControl(self); 
  FRMFiltro.fShow(); 
  FRMFiltro.fSetFiltra(" Consecutivo,iCveGrupo, Descripci�n,cDscGrupo,"); 
  FRMFiltro.fSetOrdena(" Consecutivo,iCveGrupo, Descripci�n,cDscGrupo,"); 
  fDisabled(true); 
  frm.hdBoton.value="Primero"; 
  fNavega(); 
} 
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){ 
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
  frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
  frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
  return fEngSubmite("pgTRAGpoRequisito.jsp","Listado"); 
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
    if (frm.iFlag1.value==1) {
        FRMTipoGpo = fBuscaFrame("PEM3");
        FRMTipoGpo.CargaGrupo();
    } 
    frm.iFlag1.value=0;
  } 
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
function fNuevo(){ 
   FRMPanel.fSetTraStatus("UpdateBegin"); 
   fBlanked(); 
   fDisabled(false,"iCveGrupo,","--"); 
   FRMListado.fSetDisabled(true); 
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){ 
   
   if(fValidaTodo()==true){
      if(fNavega()==true){ 
         FRMPanel.fSetTraStatus("UpdateComplete"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
         frm.iFlag1.value = 1;
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
         frm.iFlag1.value = 1;
      }
   }
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
function fModificar(){ 
   FRMPanel.fSetTraStatus("UpdateBegin"); 
   fDisabled(false,"iCveGrupo,"); 
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
   FRMListado.focus();  
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Borrar
function fBorrar(){ 
   if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){ 
     //FRMTipoGpo = fBuscaFrame("PEM3");    
     frm.iFlag1.value = 1;
    // FRMTipoGpo.fNavega();  
     fNavega(); 
   } 
} 
// LLAMADO desde el Listado cada vez que se selecciona un rengl�n
function fSelReg(aDato){ 
   frm.iCveGrupo.value = aDato[0]; 
   frm.cDscGrupo.value = aDato[1]; 
   frm.cFundLegal.value = aDato[2]; 
} 
// FUNCI�N donde se generan las validaciones de los datos ingresados
function fValidaTodo(){ 
cMsg = fValElements("cFundLegal,");
      if(fEvaluaCampo(frm.cFundLegal.value)==false)          //evalua los parametros que son aceptados en el 
       cMsg = cMsg+"\n Solo se permiten caracteres alfanum�ricos, coma, punto, par�ntesis y diagonal";    // cDscTramite como son parentesis
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
function fGetCveGpo(){
   return frm.iCveGrupo.value;
}

function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if ( fRaros(cVCadena)        || fPuntuacion(cVCadena) || 
         fSignos(cVCadena)       || fArroba(cVCadena)     ||
         fGuionBajo(cVCadena)    || fRaros2(cVCadena))
        {
         return false;  //Este regresa a la funcion fValidaTodo en donde est� la sig instrucci�n if(fEvaluaCampo(frm.cDscTramite.value)==false)
        } 
    else
        return true;
 }
 
function fRaros2(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"^") ||
       fEncCaract(cVCadena.toUpperCase(),"`") ||
       fEncCaract(cVCadena.toUpperCase(),"�") ||
       fEncCaract(cVCadena.toUpperCase(),"�") ||
       fEncCaract(cVCadena.toUpperCase(),"�") )
       return true;
   else
      return false;
}
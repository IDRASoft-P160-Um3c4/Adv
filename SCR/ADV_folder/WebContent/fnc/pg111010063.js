// MetaCD=1.0 
// Title: pg111010063.js
// Description: JS "Catálogo" de la entidad TRAEtapaXObjeto
// Company: Tecnología InRed S.A. de C.V. 
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg111010063.js";
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
        IFrame("IFiltro63","95%","34","Filtros.js"); 
      FTDTR(); 
      ITRTD("",0,"","175","center","top"); 
        IFrame("IListado63","95%","170","Listado.js","yes",true); 
      FTDTR(); 
      ITRTD("",0,"","1","center"); 
        InicioTabla("ETablaInfo",0,"75%","","","",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple(cTitulo); 
          FTDTR(); 
          ITR(); 
             TDEtiCampo(false,"EEtiqueta",0," Consecutivo:","iConsecutivo","",3,3," Consecutivo","fMayus(this);"); 
           FITR();
             TDEtiSelect(true,"EEtiqueta",0," Sistema:","iCveSistema","fProgramaOnChange();"); 
           FITR();  
              TDEtiSelect(true,"EEtiqueta",0," Programa:","iCvePrograma",""); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Objeto:","cObjeto","",15,15," Objeto","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Orden:","iOrden","",3,3," Orden","fMayus(this);"); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0," Trámite:","iCveTramite","fTramiteOnChange();"); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0," Modalidad:","iCveModalidad","fModalidadOnCahange();"); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0," Etapa:","iCveEtapa",""); 
           FITR();
              
              TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente");
              Hidden("hdLlave");Hidden("hdSelect"); 
              Hidden("lVigente","");
              //Hidden("iCveEtapa");
           FTR(); 
        FinTabla(); 
      FTDTR(); 
      FinTabla(); 
    FTDTR(); 
      ITRTD("",0,"","40","center","bottom"); 
        IFrame("IPanel63","95%","34","Paneles.js"); 
      FTDTR(); 
    FinTabla(); 
  fFinPagina(); 
} 
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel63"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado63"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo(" Sistema, Programa, Objeto, Consecutivo, Orden, Trámite, Modalidad, Etapa,"); 
  FRMListado.fSetCampos("11,12,2,3,4,9,10,13,");
  FRMListado.fSetCol(0,"left"); 
  FRMListado.fSetCol(1,"left"); 
  FRMListado.fSetCol(2,"left"); 
  FRMListado.fSetCol(5,"left"); 
  FRMListado.fSetCol(6,"left"); 
  FRMListado.fSetCol(7,"left");
  FRMFiltro = fBuscaFrame("IFiltro63"); 
  FRMFiltro.fSetControl(self); 
  FRMFiltro.fShow(); 
  FRMFiltro.fSetFiltra(" Sistema,iCveSistema, Programa,iCvePrograma,"); 
  FRMFiltro.fSetOrdena(" Sistema,iCveSistema, Programa,iCvePrograma,"); 
  fDisabled(true); 
  frm.hdBoton.value="Primero"; 
  fNavega(); 
} 
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  if(FRMFiltro.fGetFiltro() != "")
      frm.hdFiltro.value = "TRAEtapaXObjeto." +  FRMFiltro.fGetFiltro();
 
  frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
  frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;

  return fEngSubmite("pgTRAEtapaXObjeto.jsp","Listado"); 
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
  if(cId == "CIDTramite" && cError==""){
    fFillSelect(frm.iCveTramite,aRes,false,frm.iCveTramite.value,0,3);

    //fEngSubmite("pgTRAModalidad.jsp","CIDModalidad");
  }
  if(cId == "CIDSistema" && cError==""){
    fFillSelect(frm.iCveSistema,aRes,false,frm.iCveSistema.value,0,3);
    fProgramaOnChange();
  }
  if(cId == "CIDPrograma" && cError==""){
    fFillSelect(frm.iCvePrograma,aRes,false,frm.iCvePrograma.value,1,3);    
  }
  if(cId == "CIDModalidad" && cError==""){
    fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,0,1);
    fModalidadOnCahange();
  }
  if(cId == "CIDEtapa" && cError==""){ 
    fFillSelect(frm.iCveEtapa,aRes,false,frm.iCveEtapa.value,0,1);
  }

 
} 
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   frm.hdFiltro.value = "";
   frm.hdOrden.value="";
   frm.hdNumReg.value = 1000000;    
   if(fEngSubmite("pgTRACatTramite.jsp","CIDTramite") == true){
     FRMPanel.fSetTraStatus("UpdateBegin"); 
     fBlanked(); 
     fDisabled(false,"iConsecutivo,","--"); 
     FRMListado.fSetDisabled(true);
  } 
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
  frm.hdFiltro.value = "";
  frm.hdOrden.value="";
  frm.hdNumReg.value = 1000000;
  if(fEngSubmite("pgTRACatTramite.jsp","CIDTramite") == true){
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false,"iCveSistema,iCvePrograma,cObjeto,iConsecutivo,"); 
    FRMListado.fSetDisabled(true);
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
   fAsignaSelect(frm.iCveSistema,aDato[0],aDato[11]);
   fAsignaSelect(frm.iCvePrograma,aDato[1],aDato[12]); 
   frm.cObjeto.value = aDato[2]; 
   frm.iConsecutivo.value = aDato[3]; 
   frm.iOrden.value = aDato[4];
   fAsignaSelect(frm.iCveTramite,aDato[5],aDato[9]);
   fAsignaSelect(frm.iCveModalidad,aDato[6],aDato[10]);  
   fAsignaCheckBox(frm.lVigenteBOX,aDato[8]);
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
function fProgramaOnChange(){
  frm.hdFiltro.value= "iCveSistema = " + frm.iCveSistema.value;
  fEngSubmite("pgSEGPrograma.jsp","CIDPrograma");   
  frm.hdFiltro.value = "";
}

function fTramiteOnChange(){
    frm.hdLlave.value = "";
    frm.hdSelect.value="select distinct EMT.iCveModalidad,cDscModalidad From TRAEtapaXModTram EMT Join TraModalidad M On EMT.iCveModalidad = M.iCveModalidad Where iCveTramite = " + frm.iCveTramite.value;
    frm.hdNumReg.value = 1000000;
    fEngSubmite("pgConsulta.jsp","CIDModalidad");
}
function fModalidadOnCahange(){
    frm.hdLlave.value = "";
    frm.hdSelect.value="select distinct EMT.iCveEtapa,E.cDscEtapa From TRAEtapaXmodTram EMT join TRAEtapa E On E.iCveEtapa = EMT.iCveEtapa "+
                       " Where EMT.iCveTramite = "+frm.iCveTramite.value+" AND EMT.iCveModalidad = "+frm.iCveModalidad.value;
    frm.hdNumReg.value = 1000000;
    fEngSubmite("pgConsulta.jsp","CIDEtapa");
}   
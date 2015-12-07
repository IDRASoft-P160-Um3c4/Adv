// MetaCD=1.0 
// Title: pg111010080.js
// Description: JS "Catálogo" de la entidad GRLMotivoCancela
// Company: Tecnología InRed S.A. de C.V. 
// Author: ahernandez
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg111010080.js";
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
              TDEtiCampo(true,"EEtiqueta",0," Consecutivo:","iCveMotivoCancela","",3,3," Consecutivo","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(true,"EEtiqueta",0," Descripción:","cDscMotivo","",60,50," Descripción","fMayus(this);"); 
           FITR(); 
              TDEtiCheckBox("EEtiqueta",0," Vigente:","lVigenteBOX","1",true," Vigente"); 
              Hidden("lVigente","");
//           FITR(); 
//              TDEtiCheckBox("EEtiqueta",0," Asigna Vent.Unica:","lAsignaVUBOX","1",true," Asigna Vent.Unica"); 
              Hidden("lAsignaVU",1);
//           FITR(); 
//              TDEtiCheckBox("EEtiqueta",0," Asigna Area","lAsignaAreaBOX","1",true," Asigna Area"); 
              Hidden("lAsignaArea",1);
//                      
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
//  FRMListado.fSetTitulo(" Consecutivo, Descripción, Vigente, Asigna Vent.Unica, Asigna Area,");  
//  FRMListado.fSetAlinea("center,left,center,center,center,");
//  FRMListado.fSetDespliega("texto,texto,logico,logico,logico,");
//  FRMListado.fSetCampos("0,1,2,3,4,");
  FRMListado.fSetTitulo(" Consecutivo, Descripción, Vigente,");  
  FRMListado.fSetAlinea("center,left,center,");
  FRMListado.fSetDespliega("texto,texto,logico,");
  FRMListado.fSetCampos("0,1,2,");
  
  FRMFiltro = fBuscaFrame("IFiltro"); 
  FRMFiltro.fSetControl(self); 
  FRMFiltro.fShow(); 
  FRMFiltro.fSetFiltra(" Consecutivo,iCveMotivoCancela, Descripción,cDscMotivo,"); 
  FRMFiltro.fSetOrdena(" Consecutivo,iCveMotivoCancela, Descripción,cDscMotivo,"); 
  fDisabled(true); 
  frm.hdBoton.value="Primero"; 
  fNavega(); 
} 
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){ 
  frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
  frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
  frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
  return fEngSubmite("pgGRLMotivoCancela.jsp","Listado"); 
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
   fDisabled(false,"iCveMotivoCancela,","--"); 
   FRMListado.fSetDisabled(true); 
} 
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){ 
   if(frm.lVigenteBOX.checked == true)
        frm.lVigente.value =1;
       else
        frm.lVigente.value=0;

//      if(frm.lAsignaVUBOX.checked == true)
//        frm.lAsignaVU.value =1;
//       else
//        frm.lAsignaVU.value=0;
//      
//      if(frm.lAsignaAreaBOX.checked == true)
//        frm.lAsignaArea.value =1;
//       else
//        frm.lAsignaArea.value=0;
        
   if(fValidaTodo()==true){
      if(fEngSubmite("pgGRLMotivoCancela.jsp","Listado")==true){     
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

//      if(frm.lAsignaVUBOX.checked == true)
//        frm.lAsignaVU.value =1;
//       else
//        frm.lAsignaVU.value=0;
//      
//      if(frm.lAsignaAreaBOX.checked == true)
//        frm.lAsignaArea.value =1;
//       else
//        frm.lAsignaArea.value=0;
      
      
      if(fValidaTodo()==true){
       if(fEngSubmite("pgGRLMotivoCancela.jsp","Listado")==true){ 
         FRMPanel.fSetTraStatus("UpdateComplete"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
} 
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){ 
   FRMPanel.fSetTraStatus("UpdateBegin"); 
   fDisabled(false,"iCveMotivoCancela,"); 
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
   frm.iCveMotivoCancela.value = aDato[0]; 
   frm.cDscMotivo.value = aDato[1]; 
   frm.lVigente.value = aDato[2];
   frm.lAsignaVU.value = aDato[3];
   frm.lAsignaArea.value = aDato[4];
   
   if(frm.lVigente.value == 1) 
     frm.lVigenteBOX.checked = true;
    else
     frm.lVigenteBOX.checked = false;
    
//   if(frm.lAsignaVU.value == 1) 
//     frm.lAsignaVUBOX.checked = true;
//    else
//     frm.lAsignaVUBOX.checked = false;
//   
//   if(frm.lAsignaArea.value == 1) 
//     frm.lAsignaAreaBOX.checked = true;
//    else
//     frm.lAsignaAreaBOX.checked = false;    
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

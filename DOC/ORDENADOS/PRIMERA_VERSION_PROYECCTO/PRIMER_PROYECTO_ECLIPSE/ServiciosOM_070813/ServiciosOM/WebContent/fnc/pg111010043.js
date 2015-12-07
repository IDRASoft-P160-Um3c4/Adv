// MetaCD=1.0
// Title: pg111010043.js
// Description: JS "Catálogo" de la entidad TRARequisitoXGpo
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010043.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
         InicioTabla("",0,"100%","","","","1");
     InicioTabla("IFiltro43",0,"95%","","","",1);
          ITRTD("ETablaST",10,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Grupo:","iCveGrupo","fGpoOnChange();");
              Hidden("cDscGrupo");
              Hidden("flag1");
              Hidden("flag2");
           FITR();
           FITR();
     FinTabla();
    FinTabla();
    InicioTabla("",0,"100%","","center");
      ITRTD("",0,"","40","center","top");
        IFrame("IFiltro43","0%","0","Filtros.js");
      FTDTR();
  FTDTR();
         ITRTD("",0,"","","center","");
           IFrame("IListado42A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               //Liga("     Agregar  >>> ","fAgregar();","Agrega un Registro");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");
               //Liga(" <<< Eliminar     ","fEliminar();","Elimina un Registro");
               BtnImg("Buscar","btnquitar","fEliminar();");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               Liga("     Subir ","fSubir();","");
               //BtnImg("Buscar","ir2.gif","");
             ITRTD("",0,"","100%","center","");
               Liga(" Bajar     ","fBajar();","");
               //BtnImg("Buscar","ir1","");
             FTDTR();
           FinTabla();
         ITD("",0,"","","center","");
           IFrame("IListado43","95%","220","Listado.js","yes",true);
       FTDTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
    Hidden("iOrden");
    Hidden("iCveRequisito1");
    Hidden("iCveRequisito");
    Hidden("OrdenVar",0);
    Hidden("iFlag",1);
    Hidden("OrdenVar1",0);
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel43","0%","0","Paneles.js");
      FTDTR();
    FinTabla();
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel43");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado43");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Cve.<br>Req.,Requisitos por Grupo, ");
  FRMListado.fSetCampos("1,3,");
  FRMListado.fSetCol(0,"left");
  FRMListado.fSetCol(1,"left");
  FRMListado.fSetCol(2,"left");
  FRMFiltro = fBuscaFrame("IFiltro43");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow("Reg,Nav,Ord,");
   FRMListadoA = fBuscaFrame("IListado42A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Cve.<br>Req.,Requisitos,");
   FRMListadoA.fSetCampos("0,2,");
   FRMListadoA.fSetSelReg(2);
  FRMFiltro.fSetOrdena(" Grupo,iCveGrupo, Requisito,iCveRequisito,");
  fDisabled(true,"iCveGrupo,");
  frm.hdBoton.value="Primero";
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  10000;
  //fNavega2();
  CargaGrupo();

//  fNavega2();
}
// LLAMADO al JSP específico para la navegación de la página
function CargaGrupo(){
    FRMListadoA.fSetListado(new Array);
    FRMListadoA.fShow();
    FRMListado.fSetListado(new Array);
    FRMListado.fShow();
    fFillSelect(frm.iCveGrupo,new Array,true,frm.iCveGrupo.value,0,0);
    frm.hdFiltro.value = "";
    frm.hdOrden.value = "";
    frm.hdNumReg.value = 10000;
    fEngSubmite("pgTRAGpoRequisito.jsp","CIDGrupo");
//  fResultado();
}

function fNavega(){
  if(frm.iCveGrupo.value == -1)
    {
     FRMListadoA.fSetListado(new Array);
     FRMListadoA.fShow();
     FRMListado.fSetListado(new Array);
     FRMListado.fShow();
     frm.flag1.value = 0;
     frm.flag2.value = 0;
    }
   else
    {
     frm.hdFiltro.value =  "TRARequisitoXGpo.iCveGrupo = " + frm.iCveGrupo.value;
     frm.hdOrden.value =  "iOrden";
     frm.hdNumReg.value =  10000;
     return fEngSubmite("pgTRARequisitoXGpo.jsp","Listado");
    }
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Duplicado")
    fAlert("Registro Existente!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }
  if(cError!="")
    FRMFiltro.fSetNavStatus("Record");

  if(cId == "Listado" && cError==""){
  //fAlert(aRes);
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    if(FRMListado.fGetLength() > 0)
      {
       frm.flag1.value = 1;
      }
     else
      {
       frm.flag1.value = 0;
      }
    FRMListado.fSetLlave(cLlave);
    fCancelar();
    FRMFiltro.fSetNavStatus(cNavStatus);
    fReposicionaListado(FRMListado,"1", frm.OrdenVar.value);
    //fAlert(aRes);
    if (frm.iFlag.value == 1) fNavega2();
  }

  if(cId == "CIDMod" && cError==""){
    fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,0,1);
  }
  if(cId == "CIDGrupo" && cError==""){

    fFillSelect(frm.iCveGrupo,aRes,true,frm.iCveGrupo.value,0,1);
    fSelectReposFromValue(frm.iCveGrupo,"-1");
  }
  if(cId == "CIDRequisito" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListadoA.fSetListado(aRes);
    FRMListadoA.fShow();
    fReposicionaListado(FRMListadoA,"0", frm.OrdenVar1.value);
    if(FRMListadoA.fGetLength() > 0)
      {
       frm.flag2.value = 1;
      }
     else
      {
       frm.flag2.value = 0;
      }
    FRMListadoA.fSetLlave(cLlave);
    fCancelar();
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   frm.hdFiltro.value = "";
   if(fEngSubmite("pgTRAModalidad.jsp","CIDMod")== true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fBlanked("iCveGrupo,iCveRequisito,");
     fDisabled(false,"iCveGrupo,iCveRequisito,iCveGrupo,","--");
     FRMListado.fSetDisabled(true);
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
   if(fValidaTodo()==true){
      if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true,"iCveGrupo,");
         FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("UpdateComplete");
        fDisabled(true,"iCveGrupo,");
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
   frm.hdFiltro.value = "";
   if(fEngSubmite("pgTRAModalidad.jsp","CIDMod")== true){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fDisabled(false,"iCveGrupo,iCveRequisito,iCveGrupo,");
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
   fDisabled(true,"iCveGrupo,");
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
   //fAsignaSelect(frm.iCveModalidad,aDato[2],aDato[4]);
   frm.iCveRequisito.value = aDato[1];
   frm.iOrden.value = aDato[2];
}
 function fSelReg2(aDato){
   frm.iCveRequisito1.value = aDato[0];
   //fAlert (frm.iCveRequisito1.value);
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
 function fNavega2(){
   frm.hdFiltro.value =  "iCveRequisito not in (Select iCveRequisito from TRARequisitoXGpo where iCveGrupo = " + frm.iCveGrupo.value + ")";
   frm.hdOrden.value =  "cDscBreve";
   frm.hdNumReg.value = 10000;
   fEngSubmite("pgTRARequisito.jsp","CIDRequisito");
 }
 function fAgregar(){
 frm.iFlag.value = 1;
 if(frm.flag2.value == 1)
   {
    frm.hdBoton.value = "Agregar";
    frm.OrdenVar.value = frm.iCveRequisito1.value;
    fNavega();
   }
  else
   {
    alert("No hay 'Requisitos' del lado Izquierdo");
   }
 }
 function fEliminar(){
 frm.iFlag.value = 1;
   if(frm.flag1.value == 1)
     {
      frm.hdBoton.value = "Eliminar";
      frm.OrdenVar1.value = frm.iCveRequisito.value;
      fNavega();
     }
    else
     {
      alert("No hay 'Requisitos por Grupo' del lado Derecho");
     }
 }
 function fSubir(){
 frm.iFlag.value = 0;
   if (frm.iOrden.value>0){
     frm.hdBoton.value = "Subir";
     frm.OrdenVar.value = frm.iCveRequisito.value;
     fNavega();
   } else fAlert(" No se puede subir mas este registro ");
 }
 function fBajar(){
 frm.iFlag.value = 0;
     if(frm.iOrden.value<FRMListado.fGetLength()) {
        frm.hdBoton.value = "Bajar";
        frm.OrdenVar.value = frm.iCveRequisito.value;
        fNavega();
     } else fAlert(" No se puede subir mas este registro ");
 }

function fReload (bandera){
   if (bandera == 1)
    {
     fOnLoad();
    }
   else
    {}
 }
function fGpoOnChange(){
  frm.iFlag.value = 1;
  fNavega();
}

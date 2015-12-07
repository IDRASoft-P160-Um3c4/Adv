// MetaCD=1.0
// Title: pg113020031.js
// Description: JS "Catálogo" de la entidad CYSTituloObligacion
// Company: Tecnología InRed S.A. de C.V.
// Author: ICE Arturo López Peña
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010034.js";
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
        ITR();
          ITD("EEtiqueta",0,"0","","center","middle");
             // TDEtiSelect(true,"EEtiqueta",0,"Tramite:","iCveTramite","fNavega();");
         //    TextoSimple("Trámite:");
          //FTD();
          ITD("EEtiquetaL",0,"0","","center","middle");
            //Text(false,"cCveTramite","",7,6,"Teclee la clave interna del trámite para ubicarlo","fTamOnChange();this.value='';"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramite, this);\"","",true,true);
            //Select("iCveTramite","fNavega();");
          FTD();
          FTR();
      ITRTD("",2,"","175","center","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","1","center");
       FTDTR();
         ITRTD("",0,"","","center","");
           IFrame("IListado34A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnquitar","fBorrar();");
             FTDTR();
           FinTabla();
         ITD("",0,"","","center","");
           IFrame("IListado34","95%","220","Listado.js","yes",true);
       FTDTR();
         FinTabla();
//        InicioTabla("ETablaInfo",0,"75%","","","",1);
//          ITRTD("ETablaST",5,"","","center");
//            TextoSimple("Resuelve");
//          FTDTR();
//          ITR();
//           FITR();
//              TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficinaResuelve","fOficinaOnChange();");
//           FITR();
//              TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDeptoResuelve","");
//           FTR();
//        FinTabla();
      FTDTR();
      ITRTD("",2,"","1","center");
        Hidden("iFlag","");
        Hidden("hdBotonAux","");
        Hidden("var1");
        Hidden("hdLlave");
        Hidden("hdSelect");
        Hidden("iCveOficina");
        Hidden("iCveOficina1");
        Hidden("iCveTitulo");
        Hidden("iCveTramite");
      FTDTR();
      FinTabla();

    FTDTR();
//      ITRTD("",0,"","40","center","bottom");
//        IFrame("IPanel","95%","34","Paneles.js");
//      FTDTR();
    FinTabla();
  fFinPagina();
}
function fOnLoad(){
  frm = document.forms[0];
//  FRMPanel = fBuscaFrame("IPanel");
//  FRMPanel.fSetControl(self,cPaginaWebJS);
//  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado34");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Oficinas Asignadas,");
  FRMListado.fSetCampos("8,");
  FRMListado.fSetSelReg(1);
   FRMListadoA = fBuscaFrame("IListado34A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Oficinas Disponibles,");
   FRMListadoA.fSetCampos("1,");
   FRMListadoA.fSetSelReg(2);
  fCancelar();
  frm.hdBoton.value="Primero";
}
function fTamOnChange(){
  if(frm.iCveTramite.value!="")  fNavega();
}
function fBuscaTramite(iTramite){
  frm.iCveTramite.value = iTramite;
  fNavega();
  /*frm.hdNumReg.value = 100000;
  frm.hdLlave.value ="iCveOficina,iCveTramite";
  frm.hdSelect.value = "Select iCveTramite,cDscBreve from TraCatTramite order by cDscBreve";
  fEngSubmite("pgConsulta.jsp","Tramite");*/
}
function fNavega(){
  frm.hdFiltro.value = "TT.iCveTramite = " + frm.iCveTramite.value;
  frm.hdOrden.value = "oficina1";
  frm.hdNumReg.value = 100000;
  return fEngSubmite("pgTRATramiteXOfic.jsp","Listado");
}
function fNavega2(){
  frm.hdLlave.value = "iCveOficina";
  frm.hdSelect.value = "Select O.iCveOficina, O.cDscBreve||' - '|| EF.CNOMBRE AS COFICINA From GRLOficina O " +
    "Join GRLENTIDADFED EF ON EF.ICVEPAIS = O.ICVEPAIS AND EF.ICVEENTIDADFED = O.ICVEENTIDADFED " +
    "where iCveOficina not in (Select iCveOficina from TRATramiteXOfic Where iCveTramite = "+frm.iCveTramite.value+" ) and O.LVIGENTE = 1  " +
    "order by cDscBreve ";
  frm.hdNumReg.value = 100000;
  return fEngSubmite("pgConsulta.jsp","Listado2");
}
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }
  if(cId == "Listado" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fNavega2();
  }
  if(cId == "Listado2" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListadoA.fSetListado(aRes);
    FRMListadoA.fShow();
    FRMListadoA.fSetLlave(cLlave);
  }
  if(cId == "Tramite" && cError==""){
    fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,1);
  }
//  if(cId == "CIDOficina" && cError==""){
//    fFillSelect(frm.iCveOficinaResuelve,aRes,false,frm.iCveOficinaResuelve.value,0,1);
//    fOficinaOnChange();
//  }
//  if(cId == "CIDDepartamento" && cError==""){
//    fFillSelect(frm.iCveDeptoResuelve,aRes,false,frm.iCveDeptoResuelve.value,0,1);
//  }
}
function fAgregar(){
  frm.hdBoton.value = "Agregar";
  fNavega();
}
function fGuardar(){
//alert ("Oficina="+frm.iCveoficina.value+"  Tramite="+frm.iCveTramite.value+"/n  OficinaRes"+frm.iCveOficinaResuelve.value+"  DeptoRes="+frm.iCveOficinaResuelve.value)
   if(fValidaTodo()==true){
      if(fNavega()==true){
//        FRMPanel.fSetTraStatus("Mod,");
        fDisabled(true,"iCveTramite,");
        FRMListado.fSetDisabled(false);
      }
   }
}
function fGuardarA(){
//alert ("Oficina="+frm.iCveOficina.value+"  Tramite="+frm.iCveTramite.value+"/n  OficinaRes"+frm.iCveOficinaResuelve.value+"  DeptoRes="+frm.iCveDeptoResuelve.value)
   if(fValidaTodo()==true){
      if(fNavega()==true){
//        FRMPanel.fSetTraStatus("Mod,");
        fDisabled(true,"iCveTramite,");
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
   frm.hdLlave.value =""
   frm.hdSelect.value = "Select O.iCveOficina,O.cDscBreve from GRLOficina O "
//                        +" Join GRLOficina O On O.iCveOficina = TO.iCveOficina "
                 //       +" Where iCveTramite = " +frm.iCveTramite.value
                        +" Order By cDscOficina";
   fEngSubmite("pgConsulta.jsp","CIDOficina");
//   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"");
   //FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
//     FRMPanel.fSetTraStatus("Mod,");
   fDisabled(true,"iCveTramite,cCveTramite,");
   FRMListado.fSetDisabled(false);
}
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
      frm.hdBoton.value = "Borrar";
      fNavega();
   }
}
function fSelReg(aDato){
   frm.iCveOficina.value = aDato[0];
//   fAsignaSelect(frm.iCveOficinaResuelve,aDato[2],aDato[5]);
//   fAsignaSelect(frm.iCveDeptoResuelve,aDato[3],aDato[7]);
   //alert(aDato[3]+" - "+aDato[6])
}
function fSelReg2(aDato){
  frm.iCveOficina1.value = aDato[0];
}

function fOficinaOnChange(){
  frm.hdLlave.value = "iCveDepartamento";
  frm.hdSelect.value = "Select grldepartamento.iCveDepartamento,cDscBreve from GRLDepartamento " +
                      "  join GRLDEPTOXOFIC on GRLDEPTOXOFIC.ICVEDEPARTAMENTO = GRLDEPARTAMENTO.ICVEDEPARTAMENTO Where iCveOficina = " +
  // "Select Distinct iCveDepartamento,cDscBreve from GRLDepartamento Where iCveDepartamento in (Select Distinct iCveDepartamento From GRLDeptoXOfic Where iCveOficina = "+
                      frm.iCveOficinaResuelve.value +
                      " Order By cDscDepartamento";
  fEngSubmite("pgConsulta.jsp","CIDDepartamento");
}
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
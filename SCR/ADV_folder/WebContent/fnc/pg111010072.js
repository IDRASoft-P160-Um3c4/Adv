// MetaCD=1.0
 // Title: pg111010075.js
 // Description: JS "Catálogo" de la entidad TRAConceptoXGrupo
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111010072.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
       ITRTD("",0,"","0","center","top");
           FITR();
             InicioTabla("",1,"100%","","center");
               ITRTD("",0,"35%","","center","");
                 IFrame("IListado72B","100%","450","Listado.js","yes",true);
               ITD("",0,"","","center","center");
                 InicioTabla("",0,"100%","100%","center");
                   ITRTD("",0,"","","center","");
                   InicioTabla("",0,"100%","","center");
                     TDEtiCampo(false,"EEtiqueta",0," Ejercicio:","iEjercicio","",4,4," Ejercicio","fMayus(this);","","fNavega1();");
                     TDEtiSelect(false,"EEtiqueta",0,"Categoria:","iCategoriaIngresos","fNavega1();");
                     TDEtiCampo(false,"EEtiqueta",0," Referencia:","iReferencia","",8,8," Referencia","fMayus(this);");
                     FITD("EEtiquetaL",3);
                       BtnImg("vgbuscar","lupa","fBuscaListado();","");
                     FTDTR();
                   FinTabla();
                   ITRTD("",0,"","","center","");
                     InicioTabla("",0,"100%","","center");
                       FITR();
                         IFrame("IListado72A","95%","250","Listado.js","yes",true);
                       FITR();
                     FinTabla();
                     ITRTD("",0,"","","center","");
                     InicioTabla("",0,"100%","","center");
                       FITR();
                         ITD();
                           Liga("Agregar","fAgregar();");
                         ITD();
                           Liga("Eliminar","fEliminar();");
                     FinTabla();
                   FITR();
                   ITRTD("",0,"","","center","");
                     InicioTabla("",0,"100%","","center");
                       FITR();
                         IFrame("IListado72","95%","70","Listado.js","yes",true);
                       FTR();
                     FinTabla();
                   FITR();
                   InicioTabla("",0,"75%","","center");
                     TDEtiCampo(true,"EEtiqueta",0," Importe sin ajuste:","dImporteSinAjuste","",8,8," Importe sin ajuste","fMayus(this);");
                     TDEtiCampo(true,"EEtiqueta",0," Importe con ajuste:","dImporteAjustado","",8,8," Importe con ajuste","fMayus(this);");
                     FITR();
                       FITD("EEtiqueta",0);
                         Radio(true,"iTipo",1,false,"","","",'onClick="fRadio();"');
                       FITD("EEtiquetaL",0);
                         Etiqueta("Es tarifa","EEtiquetaL","Es Tarifa");
                       FITD("EEtiqueta",0);
                         Radio(true,"iTipo",2,false,"","","",'onClick="fRadio1();"');
                       FITD("EEtiquetaL",0);
                         Etiqueta("Es factor","EEtiquetaL","Es Factor");
                       FTR();
                   FinTabla();
                   InicioTabla("",0,"75%","","center");
                     FTDTR();
                       ITRTD("",0,"","40","center","bottom");
                         IFrame("IPanel72","95%","34","Paneles.js");
                       FTDTR();
                   FinTabla();
                 FinTabla();
             FinTabla();
           ITD();
           FITR();
         FinTabla();
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("cDsc1");
     Hidden("iCveConcepto");
     Hidden("Listadoa");
     Hidden("iTipoRef",0);
     Hidden("iCveConcepto1");
     Hidden("lEsPorcentaje");
     Hidden("lEsTarifa");
     Hidden("lEsPorcentajeIzq");
     Hidden("lEsTarifaIzq");
     Hidden("dtIniVigenciaIzq");
     Hidden("dtFinVigenciaIzq");
     Hidden("dtIniVigencia");
     Hidden("iConceptoIngresos");
     Hidden("iRefNumericaIngresos");
     Hidden("dImporteSinAjusteIzq");
     Hidden("dImporteAjustadoIzq");
   fFinPagina();
 }
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel72");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado72");
     FRMListado.fSetControl(self);
     FRMListado.fSetTitulo("Vig. Ini,Vig. Fin,Ref.Numerica,Categoría, Concepto,");
     FRMListado.fSetCampos("2,11,5,3,10,");
     FRMListado.fSetSelReg(1);
   FRMListadoA = fBuscaFrame("IListado72A");
     FRMListadoA.fSetControl(self);
     FRMListadoA.fSetTitulo("Fecha,Ref.Numerica,Concepto,Importe sin Ajuste, Importe con Ajuste,Tarifa,Porcentaje,Cve,");
     FRMListadoA.fSetDespliega("texto,texto,texto,texto,texto,logico,logico,");
     FRMListadoA.fSetAlinea("center,center,left,left,left,center,center,");
     FRMListadoA.fSetCampos("0,2,5,4,3,8,7,1,");
     FRMListadoA.fSetSelReg(2);
   FRMListadoB = fBuscaFrame("IListado72B");
     FRMListadoB.fSetControl(self);
     FRMListadoB.fSetTitulo("Consecutivo,Concepto de pago,");
     FRMListadoB.fSetSelReg(3);
   fDisabled(true,"iCveGrupo,");
   frm.hdBoton.value="Primero";
   frm.Listadoa.value = 0;
   fDisabled(false);
   fDisabled(true,"iEjercicio,iCategoriaIngresos,iReferencia,");

 }

 function fInicia(){
   frm.hdSelect.value=" select iCveConcepto, cDscConcepto from TRAConceptoPago Where lVigente = 1 Order by iCveConcepto";
   frm.hdLlave.value="iCveGrupo";
   frm.hdNumReg.value =  10000;
   fEngSubmite("pgConsulta.jsp","CIDConcepto");
 }
 function fNavega(){
   if (frm.iEjercicio.value!=""){
     frm.hdFiltro.value = "TRAReferenciaConcepto.iCveConcepto = " + frm.iCveConcepto.value +
                          " And iEjercicio = " + frm.iEjercicio.value;// +
                         // " AND dtIniVigencia in ( "+
//                          " Select Max(dtinivigencia) from TRAReferenciaConcepto " +
//                          " where TRAReferenciaConcepto.iCveConcepto = "+frm.iCveConcepto.value+
//                          " And iEjercicio = "+frm.iEjercicio.value+") ";
     frm.hdOrden.value = "";
     frm.hdNumReg.value =  10000;
     return fEngSubmite("pgTRAReferenciaConcepto.jsp","Listado");
   }else alert ("Necesita declarar un ejercicio");
 }

 function fNavega1(){
   // alert("Ejercicio="+frm.iEjercicio.value+"  Num.Sol="+frm.iNumSolicitud.value+"  iCveConcepto"+frm.iCveConcepto.value+"  iTipoRef="+frm.iTipoRef.value);
   // return fEngSubmite("pgIngresos1.jsp","Listado1");
   return fEngSubmite("pgINGConceptos.jsp","Listado1");
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
     if (aRes.length==1){
       if (aRes[0][3]==2)aRes[0][3]="Derecho";
       else if (aRes[0][3]==3)aRes[0][3]="Producto";
       else if (aRes[0][3]==4)aRes[0][3]="Aprovechamiento";
       else if (aRes[0][3]=="")alert("");
       else aRes[0][3]="Valor no valido";
     }
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     if (aRes.length==1){
       if (aRes[0][3]=="Derecho")aRes[0][3]=2;
       else if (aRes[0][3]=="Producto")aRes[0][3]=3;
       else if (aRes[0][3]=="Aprovechamiento")aRes[0][3]=4;
       else aRes[0][3]=1;
     }
     fCancelar();
     //alert(frm.Listadoa.value)
     if (FRMListadoA.fGetLength()==0)fNavega1();
   }
   if(cId == "Listado1" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
   }
   if(cId == "CIDConcepto"){
     frm.hdRowPag.value = iRowPag;
     FRMListadoB.fSetListado(aRes);
     FRMListadoB.fShow();
     FRMListadoB.fSetLlave(cLlave);
   }
   if(cId == "idFechaActual" && cError==""){
 //    frm.fechaActual.value = aRes[0,0];
     frm.iEjercicio.value = aRes[1][2];
     fLlenaCombo();
   }
 }
function fGuardarA(){
   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("Mod,");
        fDisabled(true,"iEjercicio,iCategoriaIngresos,iReferencia,cDsc1,");
        FRMListado.fSetDisabled(false);
        FRMListadoA.fSetDisabled(false);
        //FRMListadoB.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iEjercicio,iCategoriaIngresos,iReferencia,cDsc1,");
   FRMListado.fSetDisabled(true);
   FRMListadoA.fSetDisabled(true);
   //FRMListadoB.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
   if(FRMListado.fGetLength() > 0)
     FRMPanel.fSetTraStatus("Mod,");
   else
     FRMPanel.fSetTraStatus("");
   fDisabled(true,"iEjercicio,iCategoriaIngresos,iReferencia,cDsc1,");
   FRMListado.fSetDisabled(false);
   //FRMListadoB.fSetDisabled(false);
   FRMListadoA.fSetDisabled(false);
   FRMListado.focus();
}
function fSelReg(aDato){
   frm.dtIniVigencia.value=aDato[2];
   frm.dImporteAjustado.value = aDato[9];
   frm.dImporteSinAjuste.value = aDato[8];
   if (aDato[6]==1)fSetRadioValue(frm.iTipo,1);
     else if(aDato[7]==1)fSetRadioValue(frm.iTipo,2);
        else fSetRadioValue(frm.iTipo, 0);
}
function fSelReg2(aDato){
   frm.dtIniVigenciaIzq.value = aDato[0];
   frm.iConceptoIngresos.value = aDato[1];
   frm.iRefNumericaIngresos.value = aDato[2];
   frm.lEsTarifaIzq.value = aDato[8];
   frm.lEsPorcentajeIzq.value = aDato[7];
   frm.dImporteSinAjusteIzq.value = aDato[4];
   frm.dImporteAjustadoIzq.value = aDato[3];
   if (aDato[0]!="")frm.cDsc1.value = aDato[5];
}
function fSelReg3(aDato){
	
   if(aDato[0]!="")	
	   frm.iCveConcepto.value = aDato[0];
   else
	   frm.iCveConcepto.value=0;
   fNavega();
   
}


function fLlenaCombo()
{
var arreglo = new Array();
     arreglo[0] = ['2','Derecho'];
     arreglo[1] = ['3','Producto'];
     arreglo[2] = ['4','Aprovechamiento'];
     fFillSelect(frm.iCategoriaIngresos,arreglo,false,frm.iCategoriaIngresos.value,0,1);
     fInicia();
}
function fAgregar(){
//  if(FRMListado.fGetLength() == 0){
    frm.hdBoton.value = "Guardar";
    fNavega();
//  }
}
function fEliminar(){
  if(FRMListado.fGetLength() >0){
    frm.hdBoton.value = "Borrar";
    fNavega();
  }
}
function fValidaTodo(){
   cMsg = fValElements();

   if(cMsg != ""){
      fAlert(cMsg);
      return false;
   }
   return true;
}

function fRadio(){
   frm.lEsTarifa.value = 1;
   frm.lEsPorcentaje.value =0;
}
function fRadio1(){
   frm.lEsTarifa.value = 0;
   frm.lEsPorcentaje.value = 1;
}

function fBuscaListado(){
//  if (frm.iReferencia.value != "") {
   frm.cDsc1.value = "Registro no encontrado.";
   fReposicionaListado(FRMListadoA,"2", frm.iReferencia.value);
//   }
//    else fAlert("Debe de proporcionar una referencia.");
}

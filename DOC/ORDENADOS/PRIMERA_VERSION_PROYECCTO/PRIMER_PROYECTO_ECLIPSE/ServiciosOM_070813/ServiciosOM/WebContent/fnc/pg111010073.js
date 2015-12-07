// MetaCD=1.0
// Title: pg111010073.js
// Description: JS "Catálogo" de la entidad General
// Company: Tecnología InRed S.A. de C.V.
// Author: Arturo López Peña
var cTitulo = "";
var FRMListado = "";
var frm;
var lNuevo=true;
var aListadoTemp = new Array;
var aNoIncluir = new Array;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010073.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("ETablaInfo",0,"100%","","","",1);
          ITRTD("ETablaST",10,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
InicioTabla("",0,"100%","","center");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Ejercicio:","iEjercicio","fNavega1();");
           FITR();
FinTabla();
InicioTabla("",0,"100%","","center");
fRequisitoModalidad(true);
FinTabla();
           FITR();
     FinTabla();
   FinTabla();
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","1","center");
       FTDTR();
         ITRTD("",0,"","","center","");
           IFrame("IListado73A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fGuardar();");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnquitar","fBorrar();");
             FTDTR();
             ITRTD("",0,"","100%","center","");
           FinTabla();
         ITD("",0,"","","center","");
           IFrame("IListado73","95%","220","Listado.js","yes",true);
           Liga("Guardar","Recorre();"," ");
       FTDTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
     FinTabla();
     Hidden("iCveConcepto");
     Hidden("iCveConcepto1");
     Hidden("iCveEtapa");
     Hidden("iCveEtapa1");
     Hidden("iOrden");
     Hidden("lObligatorio");
     Hidden("Orden",0);
     Hidden("cConjunto");
     Hidden("iLlave");
     Hidden("hdSelect");
     Hidden("hdLlave");
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];

  FRMListado = fBuscaFrame("IListado73");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Pago Anticipado,Consecutivo,Conceptos Asignados,");
  FRMListado.fSetCampos("8,7,");
  FRMListado.fSetSelReg(1);
  FRMListado.fSetObjs(0,"Caja");
  FRMListado.fSetAlinea("right,left,");

  FRMListadoA = fBuscaFrame("IListado73A");
  FRMListadoA.fSetControl(self);
  FRMListadoA.fSetTitulo("Consecutivo,Conceptos Disponibles,");
  FRMListadoA.fSetCampos("2,3,");
  FRMListadoA.fSetSelReg(2);
  FRMListadoA.fSetAlinea("right,left,");
  frm.hdBoton.value="Primero";

}
function fEjercicio(){
  frm.hdSelect.value = " Select distinct(iEjercicio) from TraReferenciaConcepto order by iEjercicio desc";
  frm.hdLlave.value ="";
  frm.hdNumReg.value = 10000;
  fEngSubmite("pgConsulta.jsp","CIDEjercicio");
}

function fModalidad(){
  frm.hdFiltro.value = "lVigente = 1 and LTRAMITEFINAL=1 ";
  frm.hdOrden.value =  " TRACatTramite.cCveInterna";
  frm.hdNumReg.value = 10000;
  fEngSubmite("pgTRACatTramite.jsp","CIDTramite");
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value = "TRAConceptoXTramMod.iCveTramite = " + frm.iCveTramite.value + " and TRAConceptoXTramMod.iCveMOdalidad = " + frm.iCveModalidad.value + " AND TRAConceptoXTramMod.iEjercicio = "+frm.iEjercicio.value;
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  10000;
  return fEngSubmite("pgTRAConceptoXTramMod.jsp","Listado");
}
function fNavega1(){
  frm.hdSelect.value =
    "SELECT RC.iCveConcepto, RC.dtIniVigencia, RC.iRefNumericaIngresos, CP.cDscConcepto, CP.lVigente " +
    "FROM TRAReferenciaConcepto RC " +
    "LEFT JOIN TRAConceptoPago CP ON CP.iCveConcepto = RC.iCveConcepto " +
    "WHERE RC.iEjercicio = " + frm.iEjercicio.value + " " +
    "AND RC.dtIniVigencia = (SELECT MAX(dtIniVigencia) FROM TRAReferenciaConcepto R2 WHERE R2.iEjercicio = RC.iEjercicio AND R2.iCveConcepto = RC.iCveConcepto) " +
    "ORDER BY RC.iRefNumericaIngresos, RC.dtIniVigencia DESC ";
  frm.hdLlave.value =  "";
  frm.hdNumReg.value =  10000;
  return fEngSubmite("pgConsulta.jsp","Listado1");
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
  if(cId == "Listado" && cError==""){
    frm.hdRowPag.value = iRowPag;
    aNoIncluir = new Array();
    for(i=0;i<aRes.length;i++)
      aNoIncluir[i] = aRes[i][3];
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    FRMListado.fSetDefaultValues(0,4);
    fListado2();
  }
  if(cId == "Listado1" && cError==""){
    aListadoTemp = fCopiaArregloBidim(aRes);
   fFillSelect(frm.iCveModalidad,new Array,false,frm.iCveModalidad.value,0,1);
    fFillSelect(frm.iCveTramite,new Array,true,frm.iCveTramite.value,0,10);
    FRMListadoA.fSetListado(new Array);
    FRMListadoA.fShow();
    FRMListado.fSetListado(new Array);
    FRMListado.fShow();
    fModalidad();
  }
 if(cId == "CIDTramite" && cError==""){
    for (i=0;i<aRes.length;i++){
       aRes[i][10]= aRes[i][1]+" - "+aRes[i][3];
    }
    fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,10);
  }
   if(cId == "CIDModalidad" && cError==""){
   fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,0,1);
    if(aRes.length>0){
       fNavega();
    }else{
       FRMListadoA.fSetListado(new Array);
       FRMListadoA.fShow();
       FRMListadoA.fSetLlave(cLlave);
       FRMListado.fSetListado(new Array);
       FRMListado.fShow();
       FRMListado.fSetLlave(cLlave);
    }
  }
   if(cId == "CIDEjercicio" && cError==""){
    fFillSelect(frm.iEjercicio,aRes,false,frm.iEjercicio.value,0,0);
    fNavega1();
  }
  if(cId == "cIDTramite"){
    for(var i =0; i<aRes.length;i++){
      aRes[i][7] = aRes[i][6] +"-"+ aRes[i][3];
    }
    fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,7);
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
   frm.lPagoAnticipadoBOX.checked==true?frm.lPagoAnticipado.value=1:frm.lPagoAnticipado.value=0;
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
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
  frm.hdFiltro.value = "";
   if(fEngSubmite("pgTRACatTramite.jsp","CIDTramite") == true){
     fEngSubmite("pgTRAModalidad.jsp","CIDModalidad");
     FRMPanel.fSetTraStatus("UpdateBegin");
     fDisabled(false,"iEjercicio,iCveConcepto,");
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
     frm.iCveConcepto.value=aDato[3];
}
function fSelReg2(aDato){
     frm.iCveConcepto1.value=aDato[0];
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
function fSetReferencia(){
  fEjercicio();
}
 function Recorre(){
    aCBox = FRMListado.fGetObjs(0);
    aRes = FRMListado.fGetARes();
    frm.cConjunto.value="";
    for(aux=0; aux<aCBox.length; aux++){
      if (aCBox[aux]==true){
          frm.iLlave.value=1;
        if (frm.cConjunto.value == ""){
          frm.cConjunto.value = aRes[aux][3];
        }else
          frm.cConjunto.value += "," + aRes[aux][3];
      }
    }
   if (frm.iLlave.value == 0) frm.cConjunto.value = -1;
   frm.hdBoton.value = "Cambia";
   fNavega();
 }
 function fGuardar(){
   //frm.lObligatorio.value=1;
   frm.hdBoton.value = "Guardar";
   fNavega();
 }
 function fBorrar(){
   frm.hdBoton.value = "Borrar";
   fNavega();
 }
 function fModalidadOnChangeLocal(){
    fNavega();
 }
 function fListado2(){
   var aResp1 = fCopiaArregloBidim(aListadoTemp);
   var aResp2 = null;

   for(j=0;j<aNoIncluir.length;j++){
     for(i=0;i<(aResp1.length);i++){
       if(aResp1 && aResp1.length && aResp1[i] && aResp1[i][0] && parseInt(aResp1[i][0],10)==parseInt(aNoIncluir[j],10)){
         delete aResp1[i];
         i = aResp1.length;
       }
     }
   }
   aResp2 = fCopiaArregloBidimNotNull(aResp1);

   FRMListadoA.fSetListado(aResp2);
   FRMListadoA.fShow();
 }

function fCopiaArregloBidimNotNull(aResFuente){
  aTemporal = new Array();
  for (var i=0; i<aResFuente.length; i++)
    if(aResFuente[i] != null)
      aTemporal[aTemporal.length] = fCopiaArreglo(aResFuente[i]);
  return aTemporal;
}

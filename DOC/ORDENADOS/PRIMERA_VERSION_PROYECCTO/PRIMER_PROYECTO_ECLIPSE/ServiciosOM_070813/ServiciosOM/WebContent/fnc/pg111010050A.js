// MetaCD=1.0
// Title: pg111010050.js
// Description: JS "Catálogo" de la entidad TRARequisitoXGpo
// Company: Tecnología InRed S.A. de C.V.
// Author: Arturo López Peña.
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010050A.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
     ITRTD("ETablaST",0,"","","center");
        TextoSimple(cTitulo);
     FTDTR();
     InicioTabla("",0,"100%","","","","1");
     FTDTR();
       InicioTabla("",0,"100%","","","","1");
         ITRTD("",0,"","","center","center");
           IFrame("IListado50A","0","0","Listado.js","yes",true);
           fRequisitoModalidad(true);
         FTDTR();
       FinTabla();
     FITR();
     InicioTabla("",0,"100%","75%","","","1");
         ITRTD("",0,"40%","","center","top");
           InicioTabla("",0,"100%","30","","","1");
               IFrame("IListado50B","100%","150","Listado.js","yes",true);
               IFrame("IListado50C","100%","150","Listado.js","yes",true);
               ITRTD("",0,"","100","center","top");
           FinTabla();
         ITD("",0,"20%","","center","center");
           InicioTabla("",0,"100%","40%","");
             ITRTD("EEtiquetaC",0,"","100%","center","");
               TextoSimple("Agregar Grupo");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fAgregarT();");
             ITRTD("",0,"","100%","center","");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("EEtiquetaC",0,"","100%","center","");
               TextoSimple("Selección por Requisito");
            ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnquitar","fEliminar();");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               Liga("     Subir ","fSubir();","");
             ITRTD("",0,"","100%","center","");
               Liga(" Bajar     ","fBajar();","");
              ITRTD("",0,"","120","center","top");
             FTDTR();
           FinTabla();

           ITD("",0,"","","center","top");
           IFrame("IListado50","100%","300","Listado.js","yes",true);
           Liga("Guardar","fGuardaListado();"," ");
          //FinTabla();

       FTDTR();

         /*ITD("",0,"40%","","center","");
           IFrame("IListado50","100%","73%","Listado.js","yes",true);
       FTDTR();*/

     FinTabla();

     FinTabla();
  FinTabla();
  Hidden("iCveGrupo");
  Hidden("iCveRequisito");
  Hidden("iCveRequisito1");
  Hidden("iOrden");
  Hidden("var1");
  Hidden("hdSelect");
  Hidden("hdLlave");
  Hidden("OrdenVar",0);
  Hidden("cDscTramite");
  Hidden("cDscModalidad");
  Hidden("iLlave");
  Hidden("cConjunto");
  Hidden("Orden",0);
  Hidden("flag1");
  Hidden("flag2");
  Hidden("flag3");
}

function fOnLoad(){
  frm = document.forms[0];
   frm.hdNumReg.value =  10000;
   frm.hdFiltro.value = "lVigente = 1 and LTRAMITEFINAL=1 ";
   frm.hdOrden.value =  "TRACatTramite.cCveInterna";
   //frm.hdFiltro.value = " TRACatTramite.lVigente = 1 ";
   fEngSubmite("pgTRACatTramite1A.jsp","cIDTramite");
   frm.hdOrden.value =  ""; //cDscModalidad
   //fEngSubmite("pgTRAModalidad.jsp","cIDModalidad");
   frm.hdOrden.value =  "";
   FRMListado = fBuscaFrame("IListado50");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Requerido,Requisitos por Modalidad de Trámite,");
   FRMListado.fSetCampos("10,");
   FRMListado.fSetSelReg(1);
   FRMListado.fSetObjs(0,"Caja")
   FRMListadoB = fBuscaFrame("IListado50B");
   FRMListadoB.fSetControl(self);
   FRMListadoB.fSetTitulo("Id,Grupos de Requisitos,");
   FRMListadoB.fSetCampos("0,1,");
   FRMListadoB.fSetSelReg(2);
    FRMListadoC = fBuscaFrame("IListado50C");
    FRMListadoC.fSetControl(self);
    FRMListadoC.fSetTitulo("Id,Requisitos Disponibles,");
    FRMListadoC.fSetCampos("1,3,");
    FRMListadoC.fSetSelReg(3);
  fDisabled(true,"iCveGrupo,");
  frm.hdBoton.value="Primero";
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  100;
  fDisabled(false);
}

function fNavega(){
  if  (frm.iCveGrupo.value!="" && frm.iCveTramite.value!="" && frm.iCveModalidad.value!="" && frm.iCveModalidad.value!=NaN)   {
    frm.hdFiltro.value =  "TRAReqXModTramite.iCveTramite = " + frm.iCveTramite.value + " AND TRAReqXModTramite.iCveModalidad = " + frm.iCveModalidad.value;
    frm.hdOrden.value =  " iOrden ";
    frm.hdNumReg.value =  10000;
    return fEngSubmite("pgTRAReqXModTramite.jsp","Listado");
  }
}
function fNavega3(){
  if  (frm.iCveGrupo.value!="" && frm.iCveTramite.value!="" && frm.iCveModalidad.value!="" && frm.iCveModalidad.value!=NaN)   {
    frm.hdFiltro.value =  "TRARequisitoXGpo.iCveGrupo = " + frm.iCveGrupo.value + " and TRARequisitoXGpo.iCveRequisito not in (select iCveRequisito from TRAReqXModTramite where iCveTramite = " + frm.iCveTramite.value + " And iCveModalidad = " + frm.iCveModalidad.value + ")";
    frm.hdOrden.value =  " cDscRequisito ";
    frm.hdNumReg.value =  10000;
    return fEngSubmite("pgTRARequisitoXGpo.jsp","Listado3");
  }
}
function fNavega2(){
  frm.hdFiltro.value =  "";
  frm.hdOrden.value =  "cDscGrupo";
  frm.hdNumReg.value =  10000;
  return fEngSubmite("pgTRAGpoRequisito.jsp","Listado2");
}
function fNavega5(){
  if  (frm.iCveTramite.value!="" && frm.iCveModalidad.value!="" && frm.iCveModalidad.value!=NaN)   {
    frm.hdFiltro.value =  "TRAReqXModTramite.iCveTramite = " + frm.iCveTramite.value + " AND TRAReqXModTramite.iCveModalidad = " + frm.iCveModalidad.value;
    frm.hdOrden.value =  "iOrden";
    frm.hdNumReg.value =  1000;
    fEngSubmite("pgTRAReqXModTramite.jsp","");
    return fNavega3();
  }
}
function fNavegaGrup(){
  frm.hdFiltro.value =  "TRAReqXModTramite.iCveTramite = " + frm.iCveTramite.value + " AND TRAReqXModTramite.iCveModalidad = " + frm.iCveModalidad.value;
  frm.hdOrden.value =  "iOrden";
  frm.hdNumReg.value =  1000;
  return fEngSubmite("pgTRAReqXModTramite.jsp","Listado");
}
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
  if(cId == "Listado" && cError==""){
    frm.hdRowPag.value = iRowPag;
    for (i=0;i<aRes.length;i++)
      aRes[i][10] = "("+aRes[i][2]+")  "+aRes[i][7];
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fReposicionaListado(FRMListado,"2", frm.OrdenVar.value);
    FRMListado.fSetDefaultValues(0,4);
  }
  if(cId == "Listado3" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListadoC.fSetListado(aRes);
    FRMListadoC.fShow();
    FRMListadoC.fSetLlave(cLlave);
    frm.var1.value = "";
    for (i=0;i<FRMListadoC.fGetLength();i++){
       frm.var1.value = frm.var1.value + aRes[i][1] + ",";
    }
  }
  if(cId == "Listado2" && cError==""){
  frm.var1.value = "";
    frm.hdRowPag.value = iRowPag;

    FRMListadoB.fSetListado(aRes);
    FRMListadoB.fShow();
    FRMListadoB.fSetLlave(cLlave);
  }
  if(cId == "CIDMod" && cError==""){
    fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,0,1);
  }
  if(cId == "CIDGrupo" && cError==""){
    fFillSelect(frm.iCveGrupo,aRes,true,frm.iCveGrupo.value,0,1);
  }
  if(cId == "CIDRequisito" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListadoA.fSetListado(aRes);
    FRMListadoA.fShow();
    FRMListadoA.fSetLlave(cLlave);
    fCancelar();
  }
  if(cId == "cIDModalidad"){
    fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,0,1);
    fModalidadOnChangeLocal();
  }
  if(cId == "cIDTramite"){
    for (i=0;i<aRes.length;i++)
       aRes[i][10]=aRes[i][1]+ "  " +aRes[i][3];
    fFillSelect(frm.iCveTramite,aRes,false,frm.iCveTramite.value,0,10);
     fTramiteOnChange();
  }
}
function fSelReg(aDato){
  frm.iCveRequisito.value = aDato[2];
  frm.iOrden.value = aDato[3];
}

function fSelReg2(aDato){
  frm.iCveGrupo.value = aDato[0];
  if(FRMListadoB.fGetLength()>0)fNavega3();
}
function fSelReg3(aDato){
  frm.iCveRequisito1.value = aDato[1];
  fNavega();
}
 function fAgregar(){
   if(FRMListadoC.fGetLength()>0){
     frm.hdBoton.value = "Agregar";
     fNavega5();
   }else fAlert("No hay requisitos por agregar");
 }
 function fAgregarT(){
// alert(FRMListadoC.fGetLength());
   if(FRMListadoC.fGetLength()>0){
     frm.hdBoton.value = "AgregarT";
     fNavega5();
   }else fAlert("No hay requisitos por agregar");
 }
 function fEliminar(){
   if (FRMListado.fGetLength()>0){
     frm.hdBoton.value = "Eliminar";
     fNavega5();
   }else fAlert("No hay requisitos por eliminar");
 }
 function fSubir(){
   if(frm.iOrden.value > 0)
     {
      frm.OrdenVar.value = frm.iCveRequisito.value;
      frm.hdBoton.value = "Subir";
      fNavega5();
     }
    else
     {
      alert("No hay 'Requisitos por Modalidad de Trámite ' que Subir");
     }

 }
 function fBajar(){
   if(frm.iOrden.value < FRMListado.fGetLength())
     {
      frm.OrdenVar.value = frm.iCveRequisito.value;
      frm.hdBoton.value = "Bajar";
      fNavega5();
     }
    else
     {
      alert("No hay ' Requisitos por Modalidad de Trámite ' que Bajar");
     }
 }
function fTramiteOnChangeLocal(){
  //fNavega2();

}
function fModalidadOnChangeLocal(){
 if (frm.iCveTramite.value!="" && frm.iCveModalidad.value!="") fNavega2();
   else{
    FRMListado.fSetListado(new Array);
    FRMListado.fShow();
    FRMListadoB.fSetListado(new Array);
    FRMListadoB.fShow();
    FRMListadoC.fSetListado(new Array);
    FRMListadoC.fShow();
   }
}

function fTramiteOnChange() {
  frm.cCveTramite.value="";
  if (frm.iCveTramite && frm.iCveTramite.value && frm.iCveTramite.value !="" ){
    frm.hdBoton.value = "GetModalidadesTramite";
    frm.hdFiltro.value = " CT.iCveTramite = " + frm.iCveTramite.value + " AND lVigente = 1 ";
    frm.hdOrden.value = " cDscModalidad ";
    //fSelectSetIndexFromValue(frm.iCveModalidad, "-1");
    fEngSubmite("pgTRAModalidad.jsp","cIDModalidad");
  }
  if (fTramiteOnChangeLocal)
    fTramiteOnChangeLocal();
  else if (fTramiteOnChangeLocal)
    fTramiteOnChangeLocal();
}

function fGuardaListado(){
  if(confirm(cAlertMsgGen + "\n\n ¿Desea usted Guardar los registros?"))
    {
     frm.iLlave.value=0;
     aCBox = FRMListado.fGetObjs(0);
     aRes = FRMListado.fGetARes();
     frm.cConjunto.value="";
     for(aux=0; aux<aCBox.length; aux++){
       if (aCBox[aux]==true){
           frm.iLlave.value=1;
         if (frm.cConjunto.value == ""){
           frm.cConjunto.value = aRes[aux][2];
         }else
           frm.cConjunto.value += "," + aRes[aux][2];
       }
     }

      if (frm.iLlave.value == 0) frm.cConjunto.value = -1;
      frm.hdBoton.value = "Cambia";
      fNavega();
    }
   else
    {
      fNavega();
    }

 }
function fRequisitoModalidad(lRenglonIntermedio){
    var cTx;
    cTx = ITRTD("EEtiquetaC",0,"100%","20","center")+
        InicioTabla("",0,"","","center")+
          ITR()+
            ITD("EEtiqueta",0,"0","","center","middle")+
              TextoSimple("Trámite:")+
            FTD()+
            ITD("EEtiquetaL",0,"0","","center","middle")+
              Text(false,"cCveTramite","",11,10,"Teclee la clave interna del trámite para ubicarlo","fTramiteOnChange();this.value='';"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramite, this);\"","",true,true)+
              Select("iCveTramite","fTramiteOnChange(true);")+
            FTD();
    if(lRenglonIntermedio)
      cTx += FITR();
    cTx += ITD("EEtiqueta",0,"0","","center","middle")+
              TextoSimple("Modalidad:")+
            FTD()+
            ITD("EEtiquetaL",0,"0","","center","middle")+
              Select("iCveModalidad","if(fModalidadOnChange)fModalidadOnChange();")+
            FTD()+
          FTR()+
        FinTabla()+
      FTDTR();
    return cTx;
  }
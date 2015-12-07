 // MetaCD=1.0
 // Title: pg110000070.js
 // Description: JS Programas
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var iPagActual;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 fWrite(JSSource("Carpetas.js"));
 function fBefLoad(){
   cPaginaWebJS = "pg111010030.js";
   if(top.fGetTituloPagina){
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
    InicioTabla("",0,"100%","","center");
        ITR();
          ITD("EEtiqueta",0,"0","","center","middle");
             TextoSimple("Trámite:");
          FTD();
          ITD("EEtiquetaL",0,"0","","center","middle");
            Text(false,"cCveTramite","",7,6,"Teclee la clave interna del trámite para ubicarlo","fTamOnChange();this.value='';"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramite, this);\"","",true,true);
            Select("iCveTramite","fTamOnChange();");
          FTD();
          FTR();
    FinTabla();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Trámites|Modalidad|Configuración de Trámite|Trámites por Oficina|" ,
                     "pg111010031.js|pg111010032.js|pg111010033.js|pg111010034.js|" ,
                     "PEM" , "99%" , "99%", true);
    FTDTR();
  FinTabla();
  Hidden("hdSelect");
  Hidden("hdLlave");
  //Hidden("iCveTramite");
  Hidden("");
  fFinPagina();
}
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
   frm = document.forms[0];
   fPagFolder(1);
   fTramite();
    fDisabled(true);
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
  if(cError!=""){
    fAlert(cError);
    }
  if(cId == "cIdTramite" && cError==""){
    for(i=0;i<aRes.length;i++){
      aRes[i][1]=aRes[i][2]+" - "+aRes[i][1];
    }
    fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,1);
    //fTamOnChange();
  }
}
 // LLAMADO al JSP específico para la navegación de la página
function fFolderOnChange(iPag){ // iPag indica a la página que se desea cambiar
  //frm.iPag.value = iPag;
  if(iPag==1){
    FRMPag1 = fBuscaFrame("PEM1");
    fDisabled(true);
    iPagActual=1;
  }
  if(iPag==2){
    FRMPag2 = fBuscaFrame("PEM2");
    fDisabled(true);
    FRMPag2.fNavega();
    iPagActual=2;
  }
  if(iPag==3){
    FRMPag3 = fBuscaFrame("PEM3");
    fDisabled(false);
    fDisabled(true,"iCveTramite,cCveTramite,");
    if(frm.iCveTramite.value > 0)
        FRMPag3.fSetValues(frm.iCveTramite.value);
    else return false;
    iPagActual=3;
  }
  if(iPag==4){
    FRMPag4 = fBuscaFrame("PEM4");
    fDisabled(false);
    fDisabled(true,"iCveTramite,cCveTramite,");
    FRMPag4.fBuscaTramite(frm.iCveTramite.value);
    iPagActual=4;
  }
}
function fTramite(){
    frm.hdSelect.value = "SELECT ICVETRAMITE, CDSCBREVE, CCVEINTERNA FROM TRACATTRAMITE Where LVIGENTE=1 and LTRAMITEFINAL=1 order by CCVEINTERNA ";
  frm.hdLlave.value = "ICVETRAMITE";
  return fEngSubmite("pgConsulta.jsp","cIdTramite");
}
function fTamOnChange(){
  fActualizaDatos();
}
function fActualizaDatos(){
  FRMPag1 = fBuscaFrame("PEM1");
  FRMPag2 = fBuscaFrame("PEM2");
  FRMPag3 = fBuscaFrame("PEM3");
  FRMPag4 = fBuscaFrame("PEM4");
  FRMPag5 = fBuscaFrame("PEM5");
  FRMPag6 = fBuscaFrame("PEM6");
  if(iPagActual==1){
  }
  if(iPagActual==2){
  }
  if(iPagActual==4){
    FRMPag4.fBuscaTramite(frm.iCveTramite.value);
  }
  if(iPagActual==3){
    FRMPag3.fSetValues2(frm.iCveTramite.value);
  }
  if(iPagActual==5){
  }
}
function fSetTram(iTram){
  fSelectSetIndexFromValue(frm.iCveTramite, iTram);
}

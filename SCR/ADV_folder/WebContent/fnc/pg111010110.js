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
   cPaginaWebJS = "pg111010110.js";
   if(top.fGetTituloPagina){
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
	fRequisitoModalidad(true);
     FTDTR();
    FinTabla();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Tiempo de Traslado|COFEMER|Dependencias|Etapas Vinculadas|" ,
                     "pg111010035.js|pg111010036.js|pg111010037.js|pg111010038.js|" ,
                     "PEM" , "99%" , "99%", true);
    FTDTR();
  FinTabla();
  Hidden("hdSelect");
  Hidden("hdLlave");
  Hidden("");
  Hidden("");
  fFinPagina();
}
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
   frm = document.forms[0];
   fPagFolder(1);
   fCargaTramites();
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
  if(cId == "CIDTramite" && cError==""){
    fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,6);
    fTramiteOnChange();
  }

  if(cId == "CIDModalidad" && cError==""){
    fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,0,1);
    fActualizaDatos();
  }
}
 // LLAMADO al JSP específico para la navegación de la página
function fFolderOnChange(iPag){ // iPag indica a la página que se desea cambiar
  if(iPag==1){
    FRMPag1 = fBuscaFrame("PEM1");
    iPagActual=1;
    if(frm.iCveTramite.value!="" && frm.iCveModalidad.value!="")
       FRMPag1.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
    else FRMPag1.fSetValores(0,0);
  }
  if(iPag==2){
    iPagActual=2
    FRMPag2 = fBuscaFrame("PEM2");
    if(frm.iCveTramite.value!="" && frm.iCveModalidad.value!="")
      FRMPag2.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
    else return false;
  }
  if(iPag==3){
    iPagActual=3
    FRMPag3 = fBuscaFrame("PEM3");
    if(frm.iCveTramite.value!="" && frm.iCveModalidad.value!="")
      FRMPag3.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
    else return false;
  }
  if(iPag==4){
    iPagActual=4;
    FRMPag4 = fBuscaFrame("PEM4");
    if(frm.iCveTramite.value!="" && frm.iCveModalidad.value!="")
      FRMPag4.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
    else return false;
  }
}
function fActualizaDatos(){
  FRMPag1 = fBuscaFrame("PEM1");
  FRMPag2 = fBuscaFrame("PEM2");
  FRMPag3 = fBuscaFrame("PEM3");
  FRMPag4 = fBuscaFrame("PEM4");
  FRMPag5 = fBuscaFrame("PEM5");
  FRMPag6 = fBuscaFrame("PEM6");
  if(iPagActual==1){
    FRMPag1.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
  }
  if(iPagActual==2){
    FRMPag2.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
  }
  if(iPagActual==4){
  }
  if(iPagActual==3){
    FRMPag3.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
  }
  if(iPagActual==5){
    FRMPag4.fSetValores(frm.iCveTramite.value,frm.iCveModalidad.value);
  }
}
function fModalidadOnChangeLocal(){
  fActualizaDatos();
}

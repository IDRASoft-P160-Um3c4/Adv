// MetaCD=1.0
var frm;
var iPagAnt = 1;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111020100A.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Pagos|Entrega|","pg111020101.js|pg111020104.js|","PEM" , "99%" , "99%", true);
       //fDefCarpeta( "Instalaciones|Areas/Personal|Actividades|Equipo C.I|","pg114020082.js|pg114020084_1.js|pg114020086A.js|pg114020088A.js|","PEM" , "99%" , "99%", true);
    FTDTR();
  FinTabla();
  fFinPagina();
}
function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  fPagFolder(1);
}
function fResultado(aRes, cId){ // Recibe el resultado en el Vector aRes.
}
function fFolderOnChange( iPag ){
  if(iPag == 1) {

    FRMPrimero  = fBuscaFrame("PEM1");
    FRMPrimeroA = fBuscaFrame("IRefPago");
    FRMSegundo  = fBuscaFrame("PEM2");
    FRMSegundoA = fBuscaFrame("ISegA");
    if (FRMSegundoA.fGetdtEntrega() != ""){
      FRMPrimero.fSetdtEntrega(FRMSegundoA.fGetdtEntrega());
      FRMPrimeroA.fSetdtEntrega(FRMSegundoA.fGetdtEntrega());
    }
  }
  if(iPag == 2) {
    FRMPrimero  = fBuscaFrame("PEM1");
    FRMSegundo  = fBuscaFrame("PEM2");
    FRMSegundoA = fBuscaFrame("ISegA");
    //requierePago = FRMPrimero.fGetRequierePago();
    if((parseInt(FRMPrimero.fGetEjercicio())>0)  && (parseInt(FRMPrimero.fGetSolicitud())>0)        ){
       FRMSegundo.fSetEjercicioSolicitud(FRMPrimero.fGetEjercicio(),FRMPrimero.fGetSolicitud());
       FRMSegundoA.fSetEjercicioSolicitud(FRMPrimero.fGetEjercicio(),FRMPrimero.fGetSolicitud());
    }

  }
}

// MetaCD=1.0
var frm;
var lCambiarPagina = false;
var iHabCarpetas = 1;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111010050.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
       fTituloCodigo(cTitulo,cPaginaWebJS);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "CAMPOS DE REQUISITOS|CAMPOS x REQUISITO|" ,
                     "pg111010051.js|pg111010052.js|" ,
                             "PEM" , "99%" , "99%",true);
    FTDTR();
  FinTabla();
  fFinPagina();
}

function fOnLoad(){
  fPagFolder(1);
}
function fFolderOnChange( iPag ){ // iPag indica a la página que se desea cambiar
   FRMPag2 = fBuscaFrame("PEM2");
   if(iPag == 2){FRMPag2.fGetCampos();}
}


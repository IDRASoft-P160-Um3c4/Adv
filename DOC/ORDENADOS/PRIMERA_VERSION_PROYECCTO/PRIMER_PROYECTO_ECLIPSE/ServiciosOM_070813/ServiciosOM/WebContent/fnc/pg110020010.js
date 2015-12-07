// MetaCD=1.0
var frm;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110020010.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

function fBefLoad(){ // Carga información antes de que la página sea mostrada.
}


function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(" ");
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Procesos|Productos|Productos por Oficina y Depto|Causa PNC|" ,
                             "pg110020011.js|pg110020012.js|pg110020013.js|pg110020014.js|" ,
                             "PEM" , "99%" , "99%",true);
    FTDTR();
   FinTabla();
  fFinPagina();
}


function fOnLoad(){
  fPagFolder(1);
}


function fValidaTodo(){
   cMsg = fValElements();

   if(cMsg != ''){
      fAlert(cMsg);
   }
}


function fFolderOnChange( iPag ){ // iPag indica a la página que se desea cambiar
   FRMPag1 = fBuscaFrame("PEM1");
   FRMPag2 = fBuscaFrame("PEM2");
   FRMPag3 = fBuscaFrame("PEM3");
   FRMPag4 = fBuscaFrame("PEM4");
   if(iPag == 1 ){
     FRMPag1.fNavega();
   }
   if(iPag == 2 ){
     FRMPag2.fNavega();
   }
   if(iPag == 3 ){
     FRMPag3.fListaFormatos();
   }
   if(iPag == 4 ){
     FRMPag4.fNavega();
   }
}

// MetaCD=1.0
var frm;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110000080.js";
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
       fDefCarpeta( "Tipo de Documento|Módulo Tipo de Documento|Tipo Mime|" ,
                     "pg110000081.js|pg110000082.js|pg110000083.js|" ,
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
   FRMPag2 = fBuscaFrame("PEM2");
   FRMPag3 = fBuscaFrame("PEM3");
   FRMPag4 = fBuscaFrame("PEM4");
   if(iPag == 2 ){
     FRMPag2.fFiltro();
   }
   if(iPag == 3 ){
     FRMPag3.fEjecutafNavega();
   }
   if(iPag == 4 ){
     FRMPag4.fEjecutafNavega();
   }
}


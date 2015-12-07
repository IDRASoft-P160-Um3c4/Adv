// MetaCD=1.0
var frm;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110010010.js";
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
       fDefCarpeta( "Usuarios por<br>Oficina por Depto|" ,
                             "pg110010031.js|" ,
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
function fFolderOnChange( iPag ){
  FRMPag2 = fBuscaFrame("PEM2");
  if(iPag==2){
    FRMPag2.fOficinas();
  }
}

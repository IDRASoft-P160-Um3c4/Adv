// MetaCD=1.0
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110000050.js";
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
       fDefCarpeta( "Gen. de Folios|Asignaci�n D�gitos del Folio|","pg110000051.js|pg110000052.js|","PEM" , "99%" , "99%", true);
    FTDTR();
  FinTabla();
  fFinPagina();
}

function fOnLoad(){ // Carga informaci�n al mostrar la p�gina.
  frm = document.forms[0];
  fPagFolder(1);
}


function fFolderOnChange( iPag ){
  if(iPag == 2){
    pag2 = fBuscaFrame("PEM2");
    pag2.fTraeOficinas();
  }

}

// MetaCD=1.0
//// MetaCD=1.0
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110020050.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
function fDefPag(){ // Define la p�gina a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
    fDefCarpeta( "Solicitud - Admin|RPMN - Admin|Matriculas - Admin|Integraci�n de Embarcaciones|Integraci�n de Personas|" ,
                             "pg110020051.js|pg110020052.js|pg110020053.js|pg110020054.js|pg110020055.js|" ,
                             "PEM" , "99%" , "99%",true);
    FTDTR();
  FinTabla();
  fFinPagina();
}


function fOnLoad(){ // Carga informaci�n al mostrar la p�gina.
  frm = document.forms[0];
  fPagFolder(1);
}

function fFolderOnChange( iPag ) { // iPag indica a la p�gina que se desea cambiar
  if(iPag==2){
    FRMRPMN = fBuscaFrame("PEM2");
    FRMRPMN.fInicia();
  }
  if(iPag==3){
    FRMMat = fBuscaFrame("PEM3");
    FRMMat.fInicia();
  }
}

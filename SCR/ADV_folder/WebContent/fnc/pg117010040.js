// MetaCD=1.0
//// MetaCD=1.0
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg117010040.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
  fSetWindowTitle();
}

function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
  ITRTD("ESTitulo",0,"100%","20","center");
  TextoSimple(cTitulo);
  FTDTR();
  ITRTD("",0,"100%","100%","center","middle");
  fDefCarpeta( "Solicitud|" ,
             "pgSolicitudNewB.js|" ,
             "PEM" , "99%" , "99%",true);
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

// funcion para que no truene pgSolicitud Ja!
//function fGetParms(parEjercicio, parNumSolicitud, parcTramite, parcModalidad, parCRFCSol, parCNomSol, parCAPPatSol, parCAPMatSol){}

function fFolderOnChange(iPag){ // iPag indica a la página que se desea cambiar
}

// MetaCD=1.0
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg114020070.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}


function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple("Esta opcion ya no esta disponible");
    FTDTR();
    /*
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Solicitud|Asignar Certíficado|Embarcación|","pgSolicitud.js|pg114020072.js|pg114020073.js|","PEM" , "99%" , "99%", true);
//       fDefCarpeta( "Solicitud|Asignar Certíficado|Embarcación|","pg114020071.js|pg114020072.js|pg114020073.js|","PEM" , "99%" , "99%", true);
    FTDTR();*/
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
    /*
  if(iPag == 2 ) {
    FRMSoli = fBuscaFrame("PEM1");

    if(parseInt(FRMSoli.fGetiNumSolicitud(),10) > 0 && parseInt(FRMSoli.fGetiEjercicio(),10) > 0) {
       FRMAsigna = fBuscaFrame("PEM2");
       FRMAsigna.fSetValues(FRMSoli.fGetiEjercicio(), FRMSoli.fGetiNumSolicitud());
    }
  }

  if(iPag == 3 ) {
    FRMCertif = fBuscaFrame("PEM2");
    FRMSoli = fBuscaFrame("PEM1");
    if(parseInt(FRMSoli.fGetiNumSolicitud(),10) > 0 && parseInt(FRMSoli.fGetiEjercicio(),10) > 0) {
       FRMAsigna2 = fBuscaFrame("PEM3");
       FRMAsigna2.fSetValues(FRMSoli.fGetiEjercicio(), FRMSoli.fGetiNumSolicitud());
    }
  }

*/
}

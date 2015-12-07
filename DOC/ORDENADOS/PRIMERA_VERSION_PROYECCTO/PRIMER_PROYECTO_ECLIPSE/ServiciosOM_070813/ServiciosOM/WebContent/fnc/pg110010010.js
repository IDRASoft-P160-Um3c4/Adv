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
       fDefCarpeta( "Oficina|Categoría de la Oficina|" ,
                             "pg110010011.js|pg110010012.js|" ,
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
//  if(iPag > 1){
     // Nombre genérico del frame por pestaña.


        if(iPag == 2){
           FRMCatego = fBuscaFrame("PEM2");
           FRMCatego.fNavega();
        }
//   }
}






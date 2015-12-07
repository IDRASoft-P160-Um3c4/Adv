// MetaCD=1.0
 // Title: pg111010101.js
 // Description: JS "Catálogo" de la entidad GRLOpinionEntidad
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
var frm;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111020160.js";
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
       fDefCarpeta( "Opiniones<br>Internas|Opiniones<br>Externas|" ,
                             "pg111020161.js|pg111020162.js|" ,
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

function fFolderOnChange(iPag){ // iPag indica a la página que se desea cambiar

}

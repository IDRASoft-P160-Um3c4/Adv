// MetaCD=1.0
 // Title: pg111010101.js
 // Description: JS "Cat�logo" de la entidad GRLOpinionEntidad
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: ICaballero
var frm;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111010100.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      fTituloEmergente(cTitulo);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "�reas<br>Internas|�reas<br>Externas|" ,
                             "pg111010101.js|pg111010102.js|" ,
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

function fFolderOnChange(iPag){ // iPag indica a la p�gina que se desea cambiar

}

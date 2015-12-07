// MetaCD=1.0
var frm;
var aRegCausas = new Array();
var iEjercicioM;
var iEjercicioS;
var iConsecutivoPNCM;
var iCveProductoM;
var iCveCausaM;
var iCveOficinaM;
var iCveDeptoM;
var iCveUsuarioM;
var cCausaM = "";
var cCausaRetM = "";
var iEditSeguimiento = 0;
var lAsignado = false;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110020030.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  iEjercicioM = -1;
  iConsecutivoPNCM = -1;
  iCveProductoM = -1;
  iCveCausaM = -1;
  iCveUsuarioM = -1;
  InicioTabla("",0,"100%","100%");
     if (top.opener){
        ITRTD("ETablaST",0,"100%","","top");
           fTituloEmergente(cTitulo,false,cPaginaWebJS);
        FTDTR();
     }else{
        ITRTD("ESTitulo",0,"100%","20","center");
        fTituloCodigo(cTitulo,cPaginaWebJS);
        FTDTR();
     }
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Consulta de PNC|Seguimiento PNC|" ,
                     "pg110020031.js|pg110020032.js|" ,
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
     FRMPag1.fEjecutaSel();
   }
   if(iPag == 2 ){
     FRMPag2.fEjecutafNavega();
   }
   if(iPag == 3 ){
     FRMPag3.fEjecutafNavega();
   }
   if(iPag == 4 ){
     FRMPag4.fEjecutafNavega();
   }
}

function fGetCausas(){
   FRMPag1 = fBuscaFrame("PEM1");
   FRMPag2 = fBuscaFrame("PEM2");
   FRMPag1.fNavega1();
}

function fCopiaCausas(){
  FRMPag2 = fBuscaFrame("PEM2");
  FRMPag2.fEjecutafNavega();
}

function fSetVentanilla(iVentanillaU){
}

// MetaCD=1.0
 // Title: pg111010101.js
 // Description: JS "Catálogo" de la entidad GRLOpinionEntidad
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
var frm;
var lConsulta = false;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111020130.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
  if(top.opener && top.opener.fIsConsultaOpiniones)
    lConsulta = top.opener.fIsConsultaOpiniones();
}

function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      fTituloEmergente(cTitulo);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Opiniones<br>Internas|Opiniones<br>Externas|" ,
                             "pg111020131.js|pg111020132.js|" ,
                             "PEM" , "99%" , "99%",true);
    FTDTR();
   FinTabla();
  fFinPagina();
}

function fOnLoad(){
  frm = document.forms[0];
  fPagFolder(1);
}
function fValidaTodo(){
   cMsg = fValElements();

   if(cMsg != ''){
      fAlert(cMsg);
   }
}

function fFolderOnChange(iPag){ // iPag indica a la página que se desea cambiar

  ConTraMod = fBuscaFrame("PEM2");

  if(iPag == 2)
    ConTraMod.fRecibeValores();


}

 function fDatosEntidad(cEstado, cGobernador, cMunicipio){
   if(top.opener.frm.cNombreEstado && top.opener.frm.cNombreGobernador &&
      top.opener.frm.cNombreMunicipio){
      top.opener.frm.cNombreEstado.value = cEstado;
      top.opener.frm.cNombreGobernador.value = cGobernador;
      top.opener.frm.cNombreMunicipio.value = cMunicipio;
   }
}

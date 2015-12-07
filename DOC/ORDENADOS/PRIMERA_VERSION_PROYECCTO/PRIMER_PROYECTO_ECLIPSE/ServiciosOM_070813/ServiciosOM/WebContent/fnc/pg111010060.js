// MetaCD=1.0
// Title: pg111010060.js
// Description: JS "Catálogo" de la entidad CPAAcreditacionRepLegal
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
// MetaCD=1.0
//// MetaCD=1.0
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111010060.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
      Hidden("iPag");
//    ITRTD("EFolderMSG",0,"100%","20","center");
//    ITRTD("EFolderMSG",0,"100%","20","center");

    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Etapa|EtapaXModalidad<br>de Trámite|" ,
                             "pg111010061.js|pg111010062.js|" ,
                             "PEM" , "99%" , "99%",true );
    FTDTR();
  FinTabla();
  fFinPagina();
}
function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  fPagFolder(1);
  frm.hdNumReg.value = 1000000;
  fEngSubmite("pgTRAEtapa.jsp","cIDEtapa");
}
function fResultado(aRes, cId){ // Recibe el resultado en el Vector aRes.
/* if(cId == "cIDEtapa"){
    fFillSelect(frm.iCveEtapa,aRes,false,frm.iCveEtapa.value,0,1);
  }
*/
}
function fFolderOnChange( iPag ) {
  frm.iPag.value = iPag;
     if(iPag==2 ) {
        FRMEtapa = fBuscaFrame("PEM2");
        FRMEtapa.fTraeEtapas();
     }



}
function fEtapaOnChange() {
 if(frm.iPag.value==2){
    FRMTipoEtapa = fBuscaFrame("PEM2");
    FRMTipoEtapa.fSetEtapaXMod(frm.iCveEtapa.value);
 }
 if(frm.iPag.value==3){
    FRMTipoEtapa = fBuscaFrame("PEM3");
    FRMTipoEtapa.fSetEtapaXMod(frm.iCveEtapa.value);
 }

}

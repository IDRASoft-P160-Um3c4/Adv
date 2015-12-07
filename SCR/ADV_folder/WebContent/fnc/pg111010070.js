// MetaCD=1.0
// Title: pg111010070.js

// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111010070.js";
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

    ITRTD("EEtiquetaC",0,"100%","0","center");
        //TextoSimple("Concepto:");
        //Select("iCveConcepto","fConceptoOnChange();");

      FTDTR();
      Hidden("iPag");
    ITRTD("EFolderMSG",0,"100%","0","center");

    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
//       fDefCarpeta( "Concepto Pago|Referencia<br>Concepto|Concepto por<br>Trámite|Grupos de<br>Concepto|Conceptos del<br>Grupo|" ,
//                             "pg111010071.js|pg111010072.js|pg111010073.js|pg111010074.js|pg111010075.js|" ,
//                             "PEM" , "99%" , "99%",true );
       fDefCarpeta( "Concepto Pago|Referencia<br>Concepto|Concepto por<br>Trámite|" ,
               "pg111010071.js|pg111010072.js|pg111010073.js|" ,
               "PEM" , "99%" , "99%",true );
    FTDTR();
  FinTabla();
  fFinPagina();
}
function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  fPagFolder(1);
  frm.hdNumReg.value = 1000000;
  fEngSubmite("pgTRAConceptoPago.jsp","cIDConcepto");
}
function fResultado(aRes, cId){ // Recibe el resultado en el Vector aRes.
 if(cId == "cIDConcepto"){
    //fFillSelect(frm.iCveConcepto,aRes,false,frm.iCveConcepto.value,0,1);
  }

}
function fFolderOnChange( iPag ) { // iPag indica a la página que se desea cambiar
    if(iPag == 2){
       FRMCon = fBuscaFrame("PEM2");
       FRMCon.fTraeFechaActual();
    }
 if(iPag==3){
    FRMTipoCon = fBuscaFrame("PEM3");
    FRMTipoCon.fEjercicio();
 }
}
function fConceptoOnChange(){
 if(frm.iPag.value==2){
    FRMTipoCon = fBuscaFrame("PEM2");
    FRMTipoCon.fSetReferencia(frm.iCveConcepto.value);
 }
 if(frm.iPag.value==3){
    FRMTipoCon = fBuscaFrame("PEM3");
    FRMTipoCon.fEjercicio();
 }
}

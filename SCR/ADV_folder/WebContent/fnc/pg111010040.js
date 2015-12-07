// MetaCD=1.0
// Title: pg111010040.js
// Description: JS "Catálogo" de la entidad CPAAcreditacionRepLegal
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
// MetaCD=1.0
//// MetaCD=1.0
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111010040.js";
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
      FTDTR();
      Hidden("iPag");

    ITRTD("EFolderMSG",0,"100%","20","center");

    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
//       fDefCarpeta( "Requisitos|Grupos|Requisitos<br> por Grupo|Características|" ,
//                             "pg111010041.js|pg111010042.js|pg111010043.js|pg111010044.js|" ,
//                             "PEM" , "99%" , "99%",true );
    fDefCarpeta( "Requisitos|Grupos|Requisitos<br> por Grupo|" ,
            "pg111010041.js|pg111010042.js|pg111010043.js|" ,
            "PEM" , "99%" , "99%",true );

    FTDTR();
  FinTabla();
  fFinPagina();
}
function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  fPagFolder(1);
//  frm.hdNumReg.value = 1000000;
}
function fResultado(aRes, cId){ // Recibe el resultado en el Vector aRes.
 if(cId == "cIDRequisito"){
    //fFillSelect(frm.iCveRequisito,aRes,false,frm.iCveRequisito.value,0,2);
  }
  if(cId == "cIDGrupo"){
    //fFillSelect(frm.iCveGrupo,aRes,false,frm.iCveGrupo.value,0,1);
  }
}
function fFolderOnChange( iPag ) { // iPag indica a la página que se desea cambiar
  //FRMCaracteristicas = fBuscaFrame("PEM4");
  //FRMCaracteristicas.fListaGrupo();

 /* if(iPag == 3)
   {
     FRMTipoGpo.fReload(1);
   } */
}


function  fGrupoOnChange(){
 if(frm.iPag.value==3){
    FRMTipoGpo = fBuscaFrame("PEM3");
    FRMTipoGpo.fSetGpo(frm.iCveGrupo.value);

 }

}
function fRequisitoOnChange() {
  if(frm.iPag.value==3){
     FRMTipoReq = fBuscaFrame("PEM3");
     FRMTipoReq.fSetReq(frm.iCveRequisito.value);
  }

}

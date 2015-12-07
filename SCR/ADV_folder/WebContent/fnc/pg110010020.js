// MetaCD=1.0
var frm;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110010020.js";
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
      // fDefCarpeta( "Departamentos|Deptos por<br>Oficinas|Deptos Dependientes|Almacén por Departamento|" ,  // COMENTADO PARA QUE NO SE MUESTRE DEPTOS DEPENDIENTES
      //                       "pg110010021.js|pg110010022.js|pg110010023.js|pg110010024.js|" ,
      //                       "PEM" , "99%" , "99%",true);
    fDefCarpeta( "Departamentos|Deptos por<br>Oficinas|" ,
            "pg110010021.js|pg110010022.js|" ,
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
  if(iPag == 4){
    FRMPag4 = fBuscaFrame("PEM4");
    
    FRMPag4.fCargaOficDeptoUsr(true);
  }

}

// MetaCD=1.0
var frm;
var iPagAnt = 1;
var iPagDesp = 1;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111040020.js";
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
       fDefCarpeta( "Modalidades y Oficinas|Requisitos y Carac.|Conceptos de Pago|Etapas y Dependencias|Tiempo de Traslado|",
                    "pg111040021.js|pg111040022.js|pg111040023.js|pg111040024.js|pg111040025.js|","PEM" , "99%" , "99%", true);
    FTDTR();
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
  if(iPag==1){
    iPagDesp=1;
    iPagAnt=1;
    FRMPag3 = fBuscaFrame("PEM3");
    FRMPag1 = fBuscaFrame("PEM1");
    FRMPag1.fSetEjercicio(FRMPag3.fGetEjercicio());

    }
  if(iPag==2){
    iPagAnt=2;
     FRMPag1 = fBuscaFrame("PEM1");
     FRMPag3 = fBuscaFrame("PEM3");
     if(parseInt(FRMPag1.fGetiCveTramite(),10) > 0 && parseInt(FRMPag1.fGetModalidad(),10) > 0){
        FRMPag2 = fBuscaFrame("PEM2");
        FRMPag2.fSetDatos(FRMPag1.fGetiCveTramite(), FRMPag1.fGetModalidad(),FRMPag1.fGetDscTramite(),FRMPag1.fGetDscModalidad());
     }else
            { alert("\n Debe seleccionar un Trámite y Modalidad");
        	return false;
         }

    }
      else if(iPag == 3 ){
        iPagDesp=3;
        FRMPag1 = fBuscaFrame("PEM1");
        if(parseInt(FRMPag1.fGetiCveTramite(),10) > 0 && parseInt(FRMPag1.fGetModalidad(),10) > 0){
            FRMPag3 = fBuscaFrame("PEM3");
            FRMPag3.fSetPagos(FRMPag1.fGetiCveTramite(), FRMPag1.fGetModalidad(),FRMPag1.fGetDscTramite(),FRMPag1.fGetDscModalidad());

            if(iPagAnt==1){
              FRMPag3.fSetEjer();

              }
               FRMPag3.fNavega();
        }else  { alert("\n Debe seleccionar un Trámite y Modalidad");
        	return false;
         }
      }
      else if(iPag == 4 ){
        iPagAnt=3;
        iPagDesp=4;
        FRMPag1 = fBuscaFrame("PEM1");
        FRMPag3 = fBuscaFrame("PEM3");
        if(parseInt(FRMPag1.fGetiCveTramite(),10) > 0 && parseInt(FRMPag1.fGetModalidad(),10) > 0){
            FRMPag4 = fBuscaFrame("PEM4");
            FRMPag4.fSetEtapas(FRMPag1.fGetiCveTramite(), FRMPag1.fGetModalidad(),FRMPag1.fGetDscTramite(),FRMPag1.fGetDscModalidad());

        }else    { alert("\n Debe seleccionar un Trámite y Modalidad");
        	return false;
         }
      }
       else if(iPag == 5 ){
        iPagDesp=5;
        FRMPag1 = fBuscaFrame("PEM1");
        if(parseInt(FRMPag1.fGetiCveTramite(),10) > 0 && parseInt(FRMPag1.fGetModalidad(),10) > 0){
            FRMPag5 = fBuscaFrame("PEM5");
            FRMPag5.fSetTpoTraslado(FRMPag1.fGetiCveTramite(), FRMPag1.fGetModalidad(),FRMPag1.fGetDscTramite(),FRMPag1.fGetDscModalidad());

        }else  { alert("\n Debe seleccionar un Trámite y Modalidad");
        	return false;
         }
       }
}


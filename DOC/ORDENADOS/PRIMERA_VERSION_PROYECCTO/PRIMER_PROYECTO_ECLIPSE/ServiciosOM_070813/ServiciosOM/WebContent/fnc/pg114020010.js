// MetaCD=1.0
var frm;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg114020010.js";
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
      fDefCarpeta( "Solicitud|Datos de la Inspección|Medidas Pendientes|",//Certificados por Inspección|
                   //"pgSolicitud.js|pg114020011.js|pg114020012.js|pg114020013.js|" ,
                   "pgSolicitud.js|pg114020011.js|pg114020013.js|" ,
                   "PEM" , "99%" , "99%",true);
    FTDTR();
  FinTabla();
  Hidden("iEjercicio");
  Hidden("iNumSolicitud");
  Hidden("iEjercicioSol");
  Hidden("cNomEmbarcacion");
  Hidden("iInspeccion");
  fFinPagina();
}

function fOnLoad(){
   frm = document.forms[0];
  fPagFolder(1);
  FRM1 = fBuscaFrame("PEM1");
  FRM2 = fBuscaFrame("PEM2");
  FRM3 = fBuscaFrame("PEM3");
}

function fValidaTodo(){
   cMsg = fValElements();

   if(cMsg != ''){
      fAlert(cMsg);
   }
}



function fFolderOnChange( iPag ){
  //FRM4 = fBuscaFrame("PEM4");
  if(iPag > 1){
    if(FRM1.fGetcDscTramite()!=""){
      frm.iNumSolicitud.value = FRM1.fGetiNumSolicitud();
      frm.iEjercicioSol.value = FRM1.fGetiEjercicio();
      if(iPag == 2){
        FRM2.fSetSolicitud(frm.iEjercicioSol.value,frm.iNumSolicitud.value);
      }
      /*if(iPag == 3){
           frm.iInspeccion.value = FRM2.fGetICveInspeccion();
           frm.cNomEmbarcacion.value = FRM2.fGetCNomEmbarcacion();
           if(frm.iInspeccion.value>0 && FRM2.fGetiCveEmbarcacion()!="")
             FRM3.fSetValues(frm.iInspeccion.value,frm.cNomEmbarcacion.value,FRM2.fGetiCveEmbarcacion());
           else return false;
      }*/
      if(iPag == 3){
           frm.iInspeccion.value = FRM2.fGetICveInspeccion();
           frm.cNomEmbarcacion.value = FRM2.fGetCNomEmbarcacion();
           if(FRM2.fGetiCveEmbarcacion()>0)
             FRM3.fSetValues(FRM2.fGetiCveEmbarcacion(),frm.cNomEmbarcacion.value);
           else return false;
      }
    }
    else return false;
  }
}

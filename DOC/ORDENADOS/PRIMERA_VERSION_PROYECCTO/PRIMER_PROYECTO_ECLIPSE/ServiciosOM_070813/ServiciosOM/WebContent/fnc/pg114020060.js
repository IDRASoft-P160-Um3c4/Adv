// MetaCD=1.0
//// MetaCD=1.0
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg114020060.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
  ITRTD("ESTitulo",0,"100%","20","center");
  TextoSimple(cTitulo);
  FTDTR();
  ITRTD("",0,"100%","100%","center","middle"); 
// fDefCarpeta( "Solicitud|Certificado|Arqueo de Embarcación|Anexo Arqueo|Eslora < 24|Expedir Cert Nac.|" ,
// "pg114020010.js|pg114020062.js|pg114020063.js|pg114020010.js|pg114020010.js|pg114020010.js|" ,
  fDefCarpeta( "Solicitud|Características de<br>la Embarcación|Certificado|" ,
             "pgSolicitud.js|pg114020063.js|pg114020062.js|" ,
             "PEM" , "99%" , "99%",true);
  FTDTR();
  Hidden("hdLlave");
  Hidden("hdSelect");
  Hidden("iCveUsuario");
  Hidden("hdPropEspecifica1","certArqueo");
  Hidden("iEjercicio");
  Hidden("iNumSolicitud");
  Hidden("iCveVehiculo");
  Hidden("cNomEmbarcacion");
  FinTabla();
  fFinPagina();
}
function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  frm.iCveUsuario.value = top.fGetUsrID();
  fPagFolder(1);
// fEngSubmite("pgCOMOrdenCompra.jsp","IDOrdenCompra");
}
function fResultado(aRes, cId,cError){ // Recibe el resultado en el Vector aRes.
   if(cError!="") fAlert(cError);
   else{
     if(cId=="cIdChecaGrupo"){
       if(aRes.length>0){
         FRMMatriculadas = fBuscaFrame("PEM3");
         FRMMatriculadas.fInicio(FRMSolicitud.fGetiEjercicio(), FRMSolicitud.fGetiNumSolicitud());
       }
       else{
         fAlert("Para este tipo de trámite es requerido el registro de una inspección.");
         fPagFolder(1);
       }
     }
     if(cId=="cIdActualiza" && cError==""){
       FRMEmbarcacion = fBuscaFrame("PEM2");
       FRMCert = fBuscaFrame("PEM3");
     }
     if(cId=="cIdEtapa" && cError==""){
       if(aRes.length>0){
         if(parseInt(aRes[0][0],10)==0){
           fAlert("La Solicitud no cuenta con Etapa de Análisis");
           return false;
         }else fEnviaDatos2();
       }
       else return false;
     }
   }
}

function fFolderOnChange(iPag){ // iPag indica a la página que se desea cambiar
  if(iPag == 2){
    FRMSolicitud = fBuscaFrame("PEM1");
    if(!(parseInt(FRMSolicitud.fGetiEjercicio()) > 0) || !(parseInt(FRMSolicitud.fGetiNumSolicitud(),10) > 0)) {
	return false;
    }
    frm.hdSelect.value = "SELECT count(*) as iEtapa FROM TRAREGETAPASXMODTRAM where iEjercicio="+FRMSolicitud.fGetiEjercicio()+" and INUMSOLICITUD="+FRMSolicitud.fGetiNumSolicitud()+" and iCveEtapa=27";
    frm.hdLlave.value = "";
    fEngSubmite("pgConsulta.jsp","cIdEtapa");
  }
  if(iPag == 3 ) {
    FRMSolicitud = fBuscaFrame("PEM1");
    FRMEmbarcacion = fBuscaFrame("PEM2");
    FRMCert = fBuscaFrame("PEM3");
    var cNombre = FRMEmbarcacion.fGetNomEmbarcacion();
    var cOMI = FRMEmbarcacion.fGetOMI();
    var cMatricula = FRMEmbarcacion.fGetMatricula();
    if(!(parseInt(FRMSolicitud.fGetiEjercicio()) > 0) || !(parseInt(FRMSolicitud.fGetiNumSolicitud(),10) > 0)) {
	return false;
    }
    
    if(parseInt(FRMSolicitud.fGetiEjercicio()) > 0 && parseInt(FRMSolicitud.fGetiNumSolicitud(),10) > 0 && parseInt(FRMSolicitud.fGetIdBien(),10) > 0 ) {
       FRMCert.fSetValues(frm.iEjercicio.value,frm.iNumSolicitud.value,FRMEmbarcacion.fGetVehiculo(),FRMSolicitud.fGetiCveTramite(),FRMSolicitud.fGetiCveModalidad(),cNombre,cOMI,cMatricula);
    }else{
      if(parseInt(FRMEmbarcacion.fGetVehiculo(),10)>0 && parseInt(FRMSolicitud.fGetIdBien(),10)==0){
          frm.iEjercicio.value = FRMSolicitud.fGetiEjercicio();
          frm.iNumSolicitud.value = FRMSolicitud.fGetiNumSolicitud();
          frm.iCveVehiculo.value = FRMEmbarcacion.fGetVehiculo();
          frm.cNomEmbarcacion.value = FRMEmbarcacion.fGetNomEmbarcacion();
          FRMCert.fSetValues(frm.iEjercicio.value,frm.iNumSolicitud.value,frm.iCveVehiculo.value,FRMSolicitud.fGetiCveTramite(),FRMSolicitud.fGetiCveModalidad(),
                          frm.cNomEmbarcacion.value,FRMEmbarcacion.fGetOMI(),FRMEmbarcacion.fGetMatricula());
          //fEngSubmite("pgTRARegSolicitud.jsp","cIdActualiza");
      }
    }
  }
  /*if(iPag == 4 ) {
    FRMSolicitud = fBuscaFrame("PEM1");
    FRMMatriculadas = fBuscaFrame("PEM3");
    if(parseInt(FRMSolicitud.fGetiEjercicio()) > 0 && parseInt(FRMSolicitud.fGetiNumSolicitud(),10) > 0 && parseInt(FRMSolicitud.fGetIdBien(),10) > 0 ) {
      FRMCertificado = fBuscaFrame("PEM4");
      FRMCertificado.fSetValues(FRMSolicitud.fGetiEjercicio(), FRMSolicitud.fGetiNumSolicitud());
    }else return false;
  }*/

  if(iPag == 5 ) {
    FRMCertificado = fBuscaFrame("PEM3");
    if(parseInt(FRMCertificado.fGetCveEmbarcacion(),10)>0,parseInt(FRMCertificado.fGetGrupoCertif(),10)>0,
       parseInt(FRMCertificado.fGetCveTipoCertificado(),10)>0,parseInt(FRMCertificado.fGetCertificadoExp(),10)>0) {
      FRMRevalidacion = fBuscaFrame("PEM4");
      FRMRevalidacion.fSetValues(FRMCertificado.fGetCveEmbarcacion(),FRMCertificado.fGetGrupoCertif(),
                                 FRMCertificado.fGetCveTipoCertificado(),FRMCertificado.fGetCertificadoExp(),
                                 FRMCertificado.fGetNomEmbarcacion(),FRMCertificado.fGetTipoCertificado());
    }else return false;  //
  }
  if(iPag == 6 ) {
    FRMHistorico = fBuscaFrame("PEM5");
    FRMHistorico.fGpoCertif();
  }
}
function fEnviaDatos2(){
    FRMSolicitud = fBuscaFrame("PEM1");
    if(parseInt(FRMSolicitud.fGetiEjercicio(),10) > 0 && parseInt(FRMSolicitud.fGetiNumSolicitud(),10) > 0 ) {
      //if(parseInt(FRMSolicitud.fGetIdBien(),10) > 0){
        FRMEmbarcacion = fBuscaFrame("PEM2");
        frm.iEjercicio.value = FRMSolicitud.fGetiEjercicio();
        frm.iNumSolicitud.value = FRMSolicitud.fGetiNumSolicitud();
        FRMEmbarcacion.fSetValues(FRMSolicitud.fGetiEjercicio(), FRMSolicitud.fGetiNumSolicitud());
    }else return false;
}

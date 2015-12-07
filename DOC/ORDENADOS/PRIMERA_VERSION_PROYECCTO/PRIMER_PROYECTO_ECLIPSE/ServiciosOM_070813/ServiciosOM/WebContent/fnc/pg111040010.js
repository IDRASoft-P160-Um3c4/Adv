                                           // MetaCD=1.0
 // Title: pg111040010.js
 // Description: JS Programas
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ijimenez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 fWrite(JSSource("Carpetas.js"));
 function fBefLoad(){
   cPaginaWebJS = "pg111040010.js";
   if(top.fGetTituloPagina){
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Listado de Solicitudes|Datos Generales|Requisitos y pagos|Etapas Registradas|" ,
                     "pg111040011.js|pg111040012.js|pg111040013.js|pg111040014.js|" ,
                     "PEM" , "99%" , "99%", true);
    FTDTR();
  FinTabla();
  fFinPagina();
}
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
   frm = document.forms[0];
   fPagFolder(1);
}
 // LLAMADO al JSP específico para la navegación de la página
function fFolderOnChange(iPag){ // iPag indica a la página que se desea cambiar
     if(iPag > 1){
      ConSolicitudes = fBuscaFrame("PEM1"); // Nombre genérico del frame por pestaña.
      ConTraMod = fBuscaFrame("PEM2");
      iCveEjercicio = ConSolicitudes.fGetiCveEjercicio();
      iNumSolicitud = ConSolicitudes.fGetiNumSolicitud();
//      cDscTramite   = ConTraMod.fGetiClaveTramite();
//      cDscModalidad = ConTraMod.fGetiClaveModalidad();
      cTramite = ConSolicitudes.fGetcTramite();
      cModalidad = ConSolicitudes.fGetcModalidad();
      cOficina = ConSolicitudes.fGetcOficina();

        if(iPag == 2 && iCveEjercicio != "" && iNumSolicitud != ""){
              ConSolicitudes = fBuscaFrame("PEM2");
              ConSolicitudes.fSetSolicitud(iCveEjercicio,iNumSolicitud, cTramite, cModalidad, cOficina);
        }
        if(iPag == 3 && iCveEjercicio != "" && iNumSolicitud != ""){
              ConSolicitudes = fBuscaFrame("PEM3");
              ConSolicitudes.fSetSolicitud(iCveEjercicio,iNumSolicitud, cTramite, cModalidad);
        }
        if(iPag == 4 && iCveEjercicio != "" && iNumSolicitud != ""){
              ConSolicitudes = fBuscaFrame("PEM4");
              ConSolicitudes.fSetSolicitud(iCveEjercicio,iNumSolicitud, cTramite, cModalidad);
        }
        if(iPag == 5 && iCveEjercicio != "" && iNumSolicitud != ""){
              ConSolicitudes = fBuscaFrame("PEM4");
              ConSolicitudes.fSetSolicitud(iCveEjercicio,iNumSolicitud, cTramite, cModalidad);
        }
        if (iCveEjercicio == "" && iNumSolicitud == "") {
              alert("Se necesita que exista un registro seleccionado");
              return false;
        }

   }
}



 // MetaCD=1.0
 // Title: pg110000070.js
 // Description: JS Programas
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ijimenez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 fWrite(JSSource("Carpetas.js"));
 function fBefLoad(){
   cPaginaWebJS = "pg110000070.js";
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
       fDefCarpeta( "Módulos|Reportes|" ,
                     "pg110000071.js|pg110000072.js|" ,
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
      //ConReportes = fBuscaFrame("PEM1"); // Nombre genérico del frame por pestaña.
      ConModulos = fBuscaFrame("PEM1");
      iCveSistema = ConModulos.fGetiCveSistema();
      iCveModulo = ConModulos.fGetiCveModulo();


        if(iPag == 2 && iCveSistema != "" && iCveModulo != ""){
              ConModulos = fBuscaFrame("PEM2");
              ConModulos.fSetFiltro(iCveSistema,iCveModulo, 1 );
              //ConReportes.
              //alert("Hola 2");
        }
   }
}

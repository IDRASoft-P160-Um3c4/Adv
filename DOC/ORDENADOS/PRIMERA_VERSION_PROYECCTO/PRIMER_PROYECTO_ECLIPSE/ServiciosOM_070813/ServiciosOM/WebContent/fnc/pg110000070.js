 // MetaCD=1.0
 // Title: pg110000070.js
 // Description: JS Programas
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: ijimenez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 fWrite(JSSource("Carpetas.js"));
 function fBefLoad(){
   cPaginaWebJS = "pg110000070.js";
   if(top.fGetTituloPagina){
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "M�dulos|Reportes|" ,
                     "pg110000071.js|pg110000072.js|" ,
                     "PEM" , "99%" , "99%", true);
    FTDTR();
  FinTabla();
  fFinPagina();
}
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
   frm = document.forms[0];
   fPagFolder(1);
}
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fFolderOnChange(iPag){ // iPag indica a la p�gina que se desea cambiar
     if(iPag > 1){
      //ConReportes = fBuscaFrame("PEM1"); // Nombre gen�rico del frame por pesta�a.
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

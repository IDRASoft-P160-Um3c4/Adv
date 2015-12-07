// MetaCD=1.0
 // Title: pg110000051.js
 // Description: JS "Cat�logo" de la entidad GRLFolio
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: Arturo L�pez Pe�a
 var cTitulo = "";
 var FRMListado = "";
 var frm;


 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110030052.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       FTDTR();
       ITRTD("",0,"","185","center","top");
         IFrame("IListado","95%","180","Listado.js","yes",true);
       FTDTR();
       Hidden("hdSelect");
       Hidden("hdLlave");
       Hidden("iCveEmbarcacion");
       Hidden("");
       Hidden("");
       Hidden("");
       FinTabla();
     FinTabla();
   fFinPagina2();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Permiso,Puerto,Oficina,Fecha de Inicio,Fecha de Vigencia,Aprobado,Permiso de Transporte,");
   FRMListado.fSetCampos("0,1,5,4,2,3,6,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,texto,logico,logico");
   FRMListado.fSetAlinea("left,left,left,center,center,center,center,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   if(top.opener)
     if(top.opener.fGetEmbarcacion)
        frm.iCveEmbarcacion.value = top.opener.fGetEmbarcacion();
   fNavega();
 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   frm.hdSelect.value =
"SELECT TP.CDSCTIPOPERMISO,P.CDSCPUERTO ,PS.DTFINVIGENCIA,PS.lAprobado,ps.DTOTORGAMIENTO,O.CDSCBREVE AS COFICINA,PS.LPERMISOTRANSPORTE " +
"FROM MYRPERMISOSSERV PS " +
"join MYREMBARCACIONESXPERM EP ON PS.ICONSECPERMISO = EP.ICONSECPERMISO AND PS.IEJERCICIOPERMISO = EP.IEJERCICIOPERMISO " +
"JOIN MYRTIPOPERMISO TP ON TP.ICVETIPOPERMISO = PS.ICVETIPOPERMISO " +
"join GRLPUERTO P ON P.ICVEPUERTO = PS.ICVEPUERTO " +
"JOIN GRLOFICINA O ON O.ICVEOFICINA = PS.ICVEOFICEXTIENDE " +
"WHERE EP.ICVEVEHICULO = "+frm.iCveEmbarcacion.value;
   frm.hdLlave.value  = "ICVEINSPECCION,ICVEINSPPROG";
   fEngSubmite("pgConsulta.jsp","Listado");
 }
 // RECEPCI�N de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existi� un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existi� un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
   }
 }

function fSelReg(aDato){
}

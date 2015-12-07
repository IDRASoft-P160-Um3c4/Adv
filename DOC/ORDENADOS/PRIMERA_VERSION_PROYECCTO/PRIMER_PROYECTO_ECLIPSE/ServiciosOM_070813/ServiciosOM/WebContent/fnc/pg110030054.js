// MetaCD=1.0
 // Title: pg110000051.js
 // Description: JS "Catálogo" de la entidad GRLFolio
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo López Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var dtActual=null;


 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110030054.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
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
       ITRTD("",0,"","1","center");
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
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Ejercicio,Solicitud,Certificado,Fecha de Inicio,Fecha de Fin,Vigente (Calculo por fecha),");
   FRMListado.fSetCampos("2,1,3,4,5,10,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,texto,texto,");
   FRMListado.fSetAlinea("center,center,left,left,center,center,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   if(top.opener)
     if(top.opener.fGetEmbarcacion)
        frm.iCveEmbarcacion.value = top.opener.fGetEmbarcacion();
   //fNavega();
   fTraeFechaActual();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdSelect.value =
     "SELECT ICONSECUTIVOCERTEXP,INUMSOLICITUD,IEJERCICIO,TC.CDSCCERTIFICADO,CE.DTINIVIGENCIA,CE.DTFINVIGENCIA,CE.LVIGENTE " +
     "FROM INSCERTIFEXPEDIDOS CE " +
     "JOIN INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CE.ITIPOCERTIFICADO AND CE.ICVEGRUPOCERTIF = TC.ICVEGRUPOCERTIF " +
     "WHERE CE.ICVEVEHICULO = "+frm.iCveEmbarcacion.value;
   frm.hdLlave.value  = "ICVEINSPECCION,ICVEINSPPROG";
   fEngSubmite("pgConsulta.jsp","Listado");
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cId == "idFechaActual" && cError == ""){
       dtActual = aRes[0];
       fNavega();
   }
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     for (i=0;i<aRes.length;i++){
	 aRes[i][10] = fComparaFechas(dtActual,aRes[i][5],true);
     }
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
   }
 }

function fSelReg(aDato){
}
function fComparaFechas(dtIni,dtFin,lIguales){
    var iFechaIni = parseInt((dtIni.substring(6,10)*10000)+(dtIni.substring(3,5)*100)+(dtIni.substring(0,2)),10);
    var iFechaFin = parseInt((dtFin.substring(6,10)*10000)+(dtFin.substring(3,5)*100)+(dtFin.substring(0,2)),10);
    var lRes = 'Sí';
    if(lIguales==true){ 
	if(iFechaIni>=iFechaFin){
	    lRes = 'No';
	}
    }else{
	if(iFechaIni>iFechaFin){
	    lRes = 'No';
	}
    }
    return(lRes);
}
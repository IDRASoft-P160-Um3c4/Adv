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
   cPaginaWebJS = "pg110030051.js";
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
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"85%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Solicitante:","cSolicitante","",60,60,"D�gitos Folio","fMayus(this);");
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Oficina:","cOficina","",60,60,"D�gitos Folio","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Departamento:","cDepartamento","",60,60,"Consecutivo","");
           FITR();
         FinTabla();
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
   FRMListado.fSetTitulo("Ejercicio,Solicitud,Tramite,Modalidad,Etapa,");
   FRMListado.fSetCampos("0,1,2,3,4,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,texto");
   FRMListado.fSetAlinea("center,center,left,left,left,");
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
"SELECT S.IEJERCICIO,S.INUMSOLICITUD,CT.CDSCBREVE,MO.CDSCMODALIDAD,E.CDSCETAPA,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CSOLICITANTE, " +
"O.CDSCBREVE as cOfic,D.CDSCBREVE as cDepto " +
"FROM TRAREGSOLICITUD S " +
"JOIN TRACATTRAMITE CT ON CT.ICVETRAMITE = S.ICVETRAMITE " +
"JOIN TRAMODALIDAD MO ON MO.ICVEMODALIDAD = S.ICVEMODALIDAD " +
"JOIN TRAREGETAPASXMODTRAM EM ON EM.IEJERCICIO = S.IEJERCICIO AND S.INUMSOLICITUD= EM.INUMSOLICITUD " +
"  AND EM.ICVEMODALIDAD = S.ICVEMODALIDAD AND EM.ICVETRAMITE = S.ICVETRAMITE and ICVEETAPA = 1 " +
"JOIN TRAETAPA E ON E.ICVEETAPA = EM.ICVEETAPA " +
"JOIN GRLPERSONA P ON P.ICVEPERSONA = S.ICVESOLICITANTE " +
"JOIN GRLOFICINA O ON O.ICVEOFICINA = S.ICVEOFICINA " +
"JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO = S.ICVEDEPARTAMENTO " +
"WHERE IIDBIEN = "+frm.iCveEmbarcacion.value;
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
  frm.cSolicitante.value = aDato[5];
  frm.cOficina.value = aDato[6];
  frm.cDepartamento.value = aDato[6];
}

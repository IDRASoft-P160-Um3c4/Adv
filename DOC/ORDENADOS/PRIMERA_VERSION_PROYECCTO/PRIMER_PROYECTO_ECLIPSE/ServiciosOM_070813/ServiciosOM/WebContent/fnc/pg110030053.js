// MetaCD=1.0
 // Title: pg110000051.js
 // Description: JS "Catálogo" de la entidad GRLFolio
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo López Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;


 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110030053.js";
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
       ITRTD("",0,"","135","center","top");
         IFrame("IListado","95%","130","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","105","center","top");
         IFrame("IListado2","95%","100","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"85%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Ultimo dique seco:","dtUltimoDiqueSeco","",20,20,"Dígitos Folio","fMayus(this);");
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Número de Tripulantes:","iNumTripulantes","",20,20,"Dígitos Folio","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Número de Pasajeros:","iNumPasajeros","",20,20,"Consecutivo","");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Activo:","lRegistroFinalizadoBOX","1",true,"Activo");
              Hidden("lRegistroFinalizado","");
              TDEtiCheckBox("EEtiqueta",0,"Aprobado:","lAprobadoBOX","1",true,"Activo");
              Hidden("lAprobado","");
         FinTabla();
       FTDTR();
       Hidden("hdSelect");
       Hidden("hdLlave");
       Hidden("iCveEmbarcacion");
       Hidden("iCveInspeccion");
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
   FRMListado.fSetTitulo("Oficina,Puerto,Fecha de Inicio,Fecha de Fin,Observaciones,");
   FRMListado.fSetCampos("2,3,4,5,6,");
   FRMListado.fSetSelReg(1);
   FRMListado2 = fBuscaFrame("IListado2");
   FRMListado2.fSetControl(self);
   FRMListado2.fSetTitulo("Deficiencia,Medida Adoptada,Fecha de Inspeccion,Limite de Cumplimiento,");
   FRMListado2.fSetCampos("0,1,2,3,");
   FRMListado2.fSetSelReg(2);
   fDisabled(true,"iEjercicio,dtAsignacion,iCveOficina,iCveDepartamento,");
   frm.hdBoton.value="Primero";
   if(top.opener)
     if(top.opener.fGetEmbarcacion)
        frm.iCveEmbarcacion.value = top.opener.fGetEmbarcacion();
   fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdSelect.value =
      "SELECT I.ICVEINSPECCION,I.ICVEINSPPROG,O.CDSCBREVE,P.CDSCPUERTO,DTINIINSP,DTFININSP,COBSES,LREGISTROFINALIZADO, " +
      "DTULTDIQUESECO,INUMTRIPINSPEC,INUMPASINSPEC,LAPROBADA FROM INSINSPECCION I " +
      "JOIN GRLOFICINA O ON O.ICVEOFICINA = I.ICVEOFICINA " +
      "JOIN GRLPUERTO P ON P.ICVEPUERTO = I.ICVEPUERTO " +
      "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
      "WHERE PI.ICVEEMBARCACION = "+frm.iCveEmbarcacion.value;
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
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
   }
   if(cId == "Listado2" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado2.fSetListado(aRes);
     FRMListado2.fShow();
     FRMListado2.fSetLlave(cLlave);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
  frm.iCveInspeccion.value = aDato[0];
  frm.dtUltimoDiqueSeco.value = aDato[8];
  frm.iNumTripulantes.value = aDato[9];
  frm.iNumPasajeros.value = aDato[10];
  frm.lRegistroFinalizado.value = aDato[7];
  frm.lRegistroFinalizadoBOX.checked = frm.lRegistroFinalizado.value==1?true:false;
  frm.lAprobado.value = aDato[11];
  frm.lAprobadoBOX.checked = frm.lAprobado.value==1?true:false;
  fDeficiencias();
}
function fDeficiencias(){
  frm.hdLlave.value    = "";
  frm.hdSelect.value   =
      "SELECT CD.CDSCCODDEFICIENCIAESP,MA.CDSCMEDIDAESP,I.DTFININSP,M.DTCUMPLIMIENTO, E.CNOMEMBARCACION " +
      "FROM INSDEFICXINSP D " +
      "join INSINSPECCION I on I.ICVEINSPECCION = D.ICVEINSPECCION " +
      "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
      "JOIN VEHEMBARCACION E ON PI.ICVEEMBARCACION = E.ICVEVEHICULO " +
      "LEFT JOIN INSMEDXDEFICIENCIA M ON M.ICVEINSPECCION = I.ICVEINSPECCION AND M.ICVECODDEFICIENCIA = D.ICVECODDEFICIENCIA " +
      "JOIN INSCODDEFICIENCIA CD ON CD.ICVECODDEFICIENCIA = D.ICVECODDEFICIENCIA " +
      "JOIN INSMEDADOPTADAS MA ON MA.ICODIGOMEDIDA = M.ICODIGOMEDIDA " +
      "WHERE E.ICVEVEHICULO = " + frm.iCveEmbarcacion.value +
      " and d.ICVEINSPECCION = " + frm.iCveInspeccion.value;
   fEngSubmite("pgConsulta.jsp","Listado2");
}

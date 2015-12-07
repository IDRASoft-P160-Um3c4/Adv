// MetaCD=1.0
 // Title: PG110020051.js
 // Description: JS "Catálogo" de la entidad Modificaciones Admin / Solicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var lActivar=false;
 var iUser = fGetIdUsrSesion();
 var aResListado=new Array();
 var lYaEstaReg = false;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020051.js";
   if(top.fGetTituloPagina){
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       fTituloEmergente("Modificacion de Solicitud");
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","0","center","top");
         IFrame("IFiltro72","0%","0","Filtros.js");
       FTDTR();
       ITRTD("",6,"","0","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",9,"","","center");
             TextoSimple("Modificacion de Solicitud");
           FTDTR();
             InicioTabla("ETablaInfo",0,"75%","","","",1);
               FITR();
                   TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicioFil","",4,4,"Ejercicio de la solicitud");
                   TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitudFil","",6,6,"Numero de la solicitud");
                   ITD();
                     BtnImg("Buscar","lupa","fNavega();");
                   FTD();
                 FTD();
               FTR();
             FinTabla();
             InicioTabla("ETablaInfo",0,"75%","","","",1);
               ITRTD("ETablaST",9,"","","center");
                 TextoSimple(cTitulo);
               FTDTR();
               FITR();
                   TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cTramite","",100,100,"Trámite de la solicitud.","fMayus(this);");
                 FITR();
                   TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cModalidad","",70,70,"Modalidad de la solicitud.","fMayus(this);","","","","EEtiquetaL","7");
                 FITR();
                   TDEtiCampo(false,"EEtiqueta",0,"Fecha de registro:","tsRegistro","",20,20,"Fecha en que se registro la solicitud","fMayus(this);","","","","EEtiquetaL","7");
                 FITR();
                   TDEtiCampo(false,"EEtiqueta",0,"Solicitante:","cSolicitante","",70,70,"Solicitante","fMayus(this);","","","","EEtiquetaL","7");
                   ITD();
                     Liga("Cambiar Solicitante","fBuscaSolicitante();","Cambiar Solicitante");
                   FTD();
                 FITR();
                   TDEtiCampo(false,"EEtiqueta",0,"Embarcación:","cNomEmbarcacion","",70,70,"La búsqueda se hará por folio","fMayus(this);","","","","EEtiquetaL","7");
                   ITD();
                     Liga("Cambiar Embarcación","fBuscaEmbarcacion();","Cambiar Embarcación");
                   FTD();
                 FITR();
                   ITD();
                     TDEtiCheckBox("EEtiqueta", 0, "Resolución:", "lPositivaBOX", "1", true,"¿Positiva?");
		     Hidden("lPositiva", "");
		   FITD();
		     Liga("Cambiar Resolución","fResolucion();","Cambiar Resolución");
		   FTD();
		   
                 FTD();
               FTR();
             FinTabla();
           ITRTD("",6,"","0","center");
        FinTabla();
        Hidden("iEjercicio");
        Hidden("iNumSolicitud");
        Hidden("iCveVehiculo");
        Hidden("iCveSolicitante");
        Hidden("iCveRepLegal");
        Hidden("iCveUsuario",top.fGetUsrID());
        Hidden("hdSelect");
        Hidden("hdLlave");
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel51","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   fDisabled(true);
   frm.iEjercicioFil.disabled=false;
   frm.iNumSolicitudFil.disabled=false;
   

  
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   if(frm.iEjercicioFil.value != "" && frm.iNumSolicitudFil.value != ""){
     frm.hdSelect.value = "SELECT " +
                          "S.IEJERCICIO,S.INUMSOLICITUD, " +
                          "T.CCVEINTERNA||' - '|| T.CDSCBREVE AS CTRAMITE,M.CDSCMODALIDAD,E.CNOMEMBARCACION,E.ICVEVEHICULO, " +
                          "S.TSREGISTRO, P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS SOLICITANTE, " +
                          "S.LRESOLUCIONPOSITIVA "+
                          "FROM TRAREGSOLICITUD S " +
                          "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=S.ICVETRAMITE " +
                          "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=S.ICVEMODALIDAD " +
                          "LEFT JOIN VEHEMBARCACION E ON E.ICVEVEHICULO=S.IIDBIEN " +
                          "LEFT JOIN GRLPERSONA P ON P.ICVEPERSONA=S.ICVESOLICITANTE " +
                          "WHERE IEJERCICIO="+frm.iEjercicioFil.value+" AND INUMSOLICITUD="+frm.iNumSolicitudFil.value;
     frm.hdLlave.value  = "";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgConsulta.jsp","Listado");
   }
   else fAlert("Para la busqueda son necesarios los valores del ejercicio y numero de solicitud.");
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
   if(cError!=""){
     fAlert(cError);
     return false;
   }
   else{
     if(cId=="Listado"){
       if(aRes.length>0){
         frm.iEjercicio.value=aRes[0][0];
         frm.iNumSolicitud.value = aRes[0][1];
         frm.cTramite.value = aRes[0][2];
         frm.cModalidad.value = aRes[0][3];
         frm.tsRegistro.value = aRes[0][6];
         frm.cSolicitante.value = aRes[0][7];
         frm.lPositiva.value = aRes[0][8];
         frm.lPositivaBOX.checked = frm.lPositiva.value==1?true:false;
         frm.cNomEmbarcacion.value = aRes[0][4];
       }
     }
     if(cId=="cIdEtapa"){
	 if(aRes>0){
	     if(frm.lPositivaBOX.checked == true){
		 frm.lPositivaBOX.checked = false;
		 frm.lPositiva.value = 0;
	     }else {
		 frm.lPositivaBOX.checked = true;
		 frm.lPositiva.value = 1;
	     }
	     frm.hdBoton.value = "CambiarResolucion";
	     fEngSubmite("pgTRARegSolicitud.jsp","cIdCambiaResolucion");	     
	 }else fAlert("La solicitud se encuentra en una etapa en la cual no es posible cambiar la resolución");
     }
   }
 }
 function fImprimir(){
   self.focus();
   window.print();
 }

 function fBuscaEmbarcacion(){
   fAbreBuscaEmbarcacion();
 }
 function fSetEmbarcacion(iCveVehiculo,cNomEmbarcacion){
   frm.iCveVehiculo.value = iCveVehiculo;
   frm.cNomEmbarcacion.value = cNomEmbarcacion;
   frm.hdBoton.value = "CambiarBien";
   fEngSubmite("pgTRARegSolicitud.jsp","cIdCambiaBien");
 }
 function fBuscaSolicitante(){
   fAbreSolicitante();
 }
 function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno){
   frm.iCveSolicitante.value = iCvePersona;
   frm.cSolicitante.value = cNomRazonSocial+" "+cApPaterno+" "+cApMaterno;
 }
 function fValoresRepLegal(iCvePersona){
   frm.iCveRepLegal.value = iCvePersona;
   frm.hdBoton.value = "CambiarSolicitante";
   fEngSubmite("pgTRARegSolicitud.jsp","cIdCambiaSolicitante");
 }
 function fResolucion(){
     
     frm.hdSelect.value = "SELECT IEJERCICIO FROM TRAREGETAPASXMODTRAM"+
                          " WHERE IEJERCICIO="+frm.iEjercicio.value+
                          " AND INUMSOLICITUD="+frm.iNumSolicitud.value+
                          " AND ICVEETAPA=8 AND IORDEN > 2";
     frm.hdLlave.value = "";
     fEngSubmite("pgConsulta.jsp","cIdEtapa");     
 }

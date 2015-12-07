// MetaCD=1.0
 // Title: pg111040014.js
 // Description: JS "Catálogo" de la entidad TRARegReqXTram
 // Company: Tecnología InRed S.A. de C.V.
 // Author: IJimenez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040014.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         InicioTabla("ETablaInfo",0,"65%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           FTDTR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",4,4,"Año","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumeroSolicitud","",4,4,"Número de Solicitud","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cDscTramite","",100,100,"Descripción del Trámite","fMayus(this);","","","","EEtiquetaL", "7");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cDscModalidad","",70,70,"Descripción de la Modalidad","fMayus(this);","","","","EEtiquetaL", "7");
           FTR();
         FinTabla();


         ITRTD("",0,"95%","","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Etapas");
           FTDTR();
           ITRTD("",0,"","","center");
             IFrame("IListado14","100%","150","Listado.js","yes",true);
           FTDTR();
         FinTabla();
         FTDTR();

         FITR();
         FITR();
         ITRTD("",0,"95%","","center");
         InicioTabla("",0,"95%","","","",1);
           ITD();
             InicioTabla("",0,"95%","","","",1);
               ITRTD("ETablaST",5,"","","center");
                 TextoSimple("Canceladas");
               FTDTR();
               ITRTD("",0,"","","center");
                 IFrame("IListado14A","100%","120","Listado.js","yes",true);
               FTDTR();
             FinTabla();
           FITD();
             InicioTabla("",0,"95%","","","",1);
               ITRTD("ETablaST",5,"","","center");
                 TextoSimple("Producto No Conforme");
               FTDTR();
               ITRTD("",0,"","","center");
                 IFrame("IListado14B","100%","120","Listado.js","yes",true);
               FTDTR();
             FinTabla();
         


       FinTabla();
       FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel14","95%","34","Paneles.js");
       FTDTR();
       Hidden("hdLlave");
       Hidden("hdSelect");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel14");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow(",");
   FRMListado = fBuscaFrame("IListado14");
   FRMListado.fSetControl(self);
   //FRMListado.fSetTitulo("Etapa,Oficina,Departamento,Usuario,Registro,Anexo,");
   //FRMListado.fSetCampos("7,0,1,2,5,6,");
   FRMListado.fSetTitulo("Etapa,Oficina,Departamento,Usuario,Registro,");
   FRMListado.fSetCampos("7,0,1,2,5,");

   
   FRMListado.fSetAlinea("left,center,left,center,left,center,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,texto,logico,");
   FRMListadoA = fBuscaFrame("IListado14A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Fecha Cancelación, Usuario,Observación,");
   FRMListadoA.fSetCampos("0,1,4,");
   FRMListadoA.fSetAlinea("center,center,left,");
   FRMListadoA.fSetDespliega("texto,texto,texto,");
   FRMListadoB = fBuscaFrame("IListado14B");
   FRMListadoB.fSetControl(self);
   FRMListadoB.fSetTitulo("PNC,Descripción de Causa,Área que corrige,Fecha de Resolución," +
   		          "Fecha Oficio,Numero Oficio, Fecha Notificación,");
   FRMListadoB.fSetCampos("20,4,6,7,8,10,9,");
   FRMListadoB.fSetAlinea("left,left,center,");
   FRMListadoB.fSetDespliega("texto,texto,texto,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   fGetListado();
   fGetListadoA();
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){


   if(cId == "Listado" && cError==""){
      for (i=0;i<aRes.length;i++){
        aRes[i][2]= aRes[i][2] + " " + aRes[i][3] + " " + aRes[i][4];
      }
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
   }

   if(cId == "ListadoA" && cError==""){
     for (i=0;i<aRes.length;i++){
        aRes[i][1]= aRes[i][1] + " " + aRes[i][2] + " " + aRes[i][3];
     }
     frm.hdRowPag.value = iRowPag;
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
     fGetListadoB();
   }
   if(cId == "ListadoB" && cError==""){
       frm.hdRowPag.value = iRowPag;
       for (i=0;i<aRes.length;i++){
	   aRes[i][20] = aRes[i][2]+"/"+aRes[i][3];
       }
       FRMListadoB.fSetListado(aRes);
       FRMListadoB.fShow();
       FRMListadoB.fSetLlave(cLlave);
     }
 }
 function fImprimir(){
    self.focus();
    window.print();
 }
  function fSelReg(aDato){
 }

 function fGetListado(){
    frm.hdLlave.value = " iEjercicio,iNumSolicitud ";
    frm.hdSelect.value = " SELECT GRLOficina.cDscOficina, GRLDepartamento.cDscDepartamento, SEGUsuario.cNombre,  SEGUsuario.cApPaterno, SEGUsuario.cApMaterno, TRARegEtapasXmodTram.tsRegistro, TRARegEtapasXmodTram.lAnexo, cDscEtapa "+
                         " FROM  TRARegEtapasXmodTram "+
                         " JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = TRARegEtapasXmodTram.iCveDepartamento "+
                         " JOIN GRLOficina ON GRLOficina.iCveOficina = TRARegEtapasXmodTram.iCveOficina "+
                         " JOIN SEGUsuario ON SEGUsuario.iCveUsuario = TRARegEtapasXmodTram.iCveUsuario "+
                         " JOIN TRAEtapa TE ON TE.iCveEtapa = TRARegEtapasXmodTram.iCveEtapa "+
                         " WHERE  TRARegEtapasXmodTram.iEjercicio = "+ frm.iEjercicio.value + " AND TRARegEtapasXmodTram.iNumSolicitud = " +frm.iNumeroSolicitud.value +
                         " Order by iOrden ";
    frm.hdNumReg.value =  50;
    return fEngSubmite("pgConsulta.jsp","Listado");
//fNavega();
 }
 function fGetListadoA(){
    frm.hdLlave.value = "";
    frm.hdSelect.value = "";
    frm.hdLlave.value = " iEjercicio,iNumSolicitud ";
    frm.hdSelect.value = " SELECT TraRegTramXSol.dtCancelacion, SEGUsuario.cNombre,  SEGUsuario.cApPaterno, SEGUsuario.cApMaterno,  TraRegTramXSol.cObs "+
                         " FROM TraRegTramXSol "+
                         " JOIN SEGUsuario ON SEGUsuario.iCveUsuario = TraRegTramXSol.iCveUsuario "+
                         " JOIN GRLMotivoCancela ON GRLMotivoCancela.iCveMotivoCancela = TraRegTramXSol.iCveMotivoCancela "+
                         " WHERE  TraRegTramXSol.iEjercicio = "+ frm.iEjercicio.value + " AND TraRegTramXSol.iNumSolicitud = " +frm.iNumeroSolicitud.value ;
    frm.hdNumReg.value =  50;
    return fEngSubmite("pgConsulta.jsp","ListadoA");
    //fNavega();
 }
 function fGetListadoB(){
     frm.hdLlave.value = "";
     frm.hdSelect.value = "";
     frm.hdLlave.value = " iEjercicio,iNumSolicitud ";
     frm.hdSelect.value = "SELECT " +
     		          "	PE.IEJERCICIO," +
     		          "	PE.INUMSOLICITUD," +
     		          "	PE.IEJERCICIOPNC," +
     		          "	PE.ICONSECUTIVOPNC, " +
                          "	CP.CDSCCAUSAPNC," +
                          "	RC.CDSCOTRACAUSA," +
                          "	O.CDSCBREVE || ' - ' || D.CDSCBREVE AS CArea, " +
                          "	rp.DTRESOLUCION, " +
                          "	rp.DTOFICIO, " +
                          "	rp.DTNOTIFICACION, " +
                          "	rp.CNUMOFICIO " +
                          "FROM TRAREGPNCETAPA PE " +
                          "JOIN TRAETAPA E ON E.ICVEETAPA = PE.ICVEETAPA " +
                          "LEFT JOIN GRLREGISTROPNC RP ON RP.IEJERCICIO=PE.IEJERCICIOPNC AND RP.ICONSECUTIVOPNC = PE.ICONSECUTIVOPNC " +
                          "JOIN GRLREGCAUSAPNC RC ON RC.IEJERCICIO = PE.IEJERCICIOPNC AND RC.ICONSECUTIVOPNC = PE.ICONSECUTIVOPNC " +
                          "LEFT JOIN GRLCAUSAPNC CP ON CP.ICVEPRODUCTO = RC.ICVEPRODUCTO AND CP.ICVECAUSAPNC = RC.ICVECAUSAPNC " +
                          "LEFT JOIN GRLOFICINA O ON O.ICVEOFICINA = RP.ICVEOFICINAASIGNADO " +
                          "LEFT JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO = RP.ICVEDEPTOASIGNADO " +
                          "WHERE PE.IEJERCICIO = " +frm.iEjercicio.value+
                          "  AND PE.INUMSOLICITUD = "+frm.iNumeroSolicitud.value;
     frm.hdNumReg.value =  50;
     return fEngSubmite("pgConsulta.jsp","ListadoB");     
 }
 function fSetSolicitud(iEjercicio,iNumSolicitud, cTramite, cModalidad){
   frm.iEjercicio.value = iEjercicio;
   frm.iNumeroSolicitud.value = iNumSolicitud;
   frm.cDscTramite.value = cTramite;
   frm.cDscModalidad.value = cModalidad;
   fNavega();
 }

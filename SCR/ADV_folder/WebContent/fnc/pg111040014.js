// MetaCD=1.0
 // Title: pg111040014.js
 // Description: JS "Catálogo" de la entidad TRARegReqXTram
 // Company: Tecnología InRed S.A. de C.V.
 // Author: IJimenez
 var cTitulo = "";
 var FRMListado = "";
 var FRMListadoA = "";
 var FRMListadoB = "";
 var FRMListadoC = "";
 var FRMListadoD = "";
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
                 TextoSimple("Cancelación");
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
          
          ITRTD("",0,"95%","","center");
          InicioTabla("",0,"95%","","","",1);
            ITD();
              InicioTabla("",0,"95%","","","",1);
                ITRTD("ETablaST",5,"","","center");
                  TextoSimple("Evaluadores por DGST - DAJL");
                FTDTR();
                ITRTD("",0,"","","center");
                  IFrame("IListado14C","100%","120","Listado.js","yes",true);
                FTDTR();
              FinTabla();
            FITD();
              InicioTabla("",0,"95%","","","",1);
                ITRTD("ETablaST",5,"","","center");
                  TextoSimple("Retrasos registrados");
                FTDTR();
                ITRTD("",0,"","","center");
                  IFrame("IListado14D","100%","120","Listado.js","yes",true);
                FTDTR();
              FinTabla();
           FinTabla();
           FTDTR();

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
   FRMListado.fSetTitulo("Etapa,Usuario,Registro,");
   FRMListado.fSetCampos("3,0,1,");
   FRMListado.fSetAlinea("center,center,center,");
   FRMListado.fSetDespliega("texto,texto,texto,");
   
   FRMListadoA = fBuscaFrame("IListado14A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Fecha Cancelación, Usuario,Motivo,");
   FRMListadoA.fSetCampos("0,1,2,");
   FRMListadoA.fSetAlinea("center,center,left,");
   FRMListadoA.fSetDespliega("texto,texto,texto,");
   
   FRMListadoB = fBuscaFrame("IListado14B");
   FRMListadoB.fSetControl(self);
   FRMListadoB.fSetTitulo("Folio PNC,Fecha Notificación, Fecha de Resolución,");
   FRMListadoB.fSetCampos("0,1,2,");
   FRMListadoB.fSetAlinea("center,center,center,");
   FRMListadoB.fSetDespliega("texto,texto,texto,");
   
   FRMListadoC = fBuscaFrame("IListado14C");
   FRMListadoC.fSetControl(self);
   FRMListadoC.fSetTitulo("Usuario, Última evaluación,");
   FRMListadoC.fSetCampos("0,1,");
   FRMListadoC.fSetAlinea("left,center,");
   FRMListadoC.fSetDespliega("texto,texto,");
   
   FRMListadoD = fBuscaFrame("IListado14D");
   FRMListadoD.fSetControl(self);
   FRMListadoD.fSetTitulo("Usuario, Fecha registro, Días,");
   FRMListadoD.fSetCampos("0,1,2,");
   FRMListadoD.fSetAlinea("left,center,center,");
   FRMListadoD.fSetDespliega("texto,texto,texto,");
   
   fDisabled(true);
   frm.hdBoton.value="Primero";
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   fGetListado();
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){


   if(cId == "Listado" && cError==""){
      
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fGetListadoA();
   }

   if(cId == "ListadoA" && cError==""){
     
     frm.hdRowPag.value = iRowPag;
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
     fGetListadoB();
   }
   if(cId == "ListadoB" && cError==""){
       frm.hdRowPag.value = iRowPag;
       for (i=0;i<aRes.length;i++){
    	   aRes[i].push(aRes[i][2]+"/"+aRes[i][3]);
       }
       FRMListadoB.fSetListado(aRes);
       FRMListadoB.fShow();
       FRMListadoB.fSetLlave(cLlave);
       fGetListadoC();
     }
   if(cId == "ListadoC" && cError==""){
       frm.hdRowPag.value = iRowPag;
       FRMListadoC.fSetListado(aRes);
       FRMListadoC.fShow();
       FRMListadoC.fSetLlave(cLlave);
       fGetListadoD();
     }
   
   if(cId == "ListadoD" && cError==""){
       frm.hdRowPag.value = iRowPag;
       FRMListadoD.fSetListado(aRes);
       FRMListadoD.fShow();
       FRMListadoD.fSetLlave(cLlave);
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
    frm.hdSelect.value = " SELECT SEGUsuario.CNOMBRE||' '||SEGUsuario.CAPPATERNO||' '||SEGUsuario.CAPMATERNO||' ('|| GRLOficina.CDSCBREVE||' - '|| GRLDepartamento.CDSCBREVE||')' cnombre,  " +
    					 " TRARegEtapasXmodTram.tsRegistro, TRARegEtapasXmodTram.lAnexo, cDscEtapa "+
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
    frm.hdSelect.value = " SELECT TraRegTramXSol.dtCancelacion, SEGUsuario.CNOMBRE||' '||SEGUsuario.CAPPATERNO||' '||SEGUsuario.CAPMATERNO||' ('|| GRLOficina.CDSCBREVE||' - '|| GRLDepartamento.CDSCBREVE||')' cnombre,  GRLMotivoCancela.CDSCMOTIVO  "+
                         " FROM TraRegTramXSol "+
                         " JOIN SEGUsuario ON SEGUsuario.iCveUsuario = TraRegTramXSol.iCveUsuario "+
                         " JOIN GRLMotivoCancela ON GRLMotivoCancela.iCveMotivoCancela = TraRegTramXSol.iCveMotivoCancela "+
                         " JOIN GRLUSUARIOXOFIC ON GRLUSUARIOXOFIC.icveusuario= SEGUsuario.iCveUsuario  "+
                         " JOIN GRLoficina ON GRLoficina .icveoficina= GRLUSUARIOXOFIC.icveoficina  "+
                         " JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLUSUARIOXOFIC.iCveDepartamento "+
                         " WHERE  TraRegTramXSol.iEjercicio = "+ frm.iEjercicio.value + " AND TraRegTramXSol.iNumSolicitud = " +frm.iNumeroSolicitud.value ;
    frm.hdNumReg.value =  50;
    return fEngSubmite("pgConsulta.jsp","ListadoA");
    //fNavega();
 }
 function fGetListadoB(){
     frm.hdLlave.value = "";
     frm.hdSelect.value = "";
     frm.hdLlave.value = " iEjercicio,iNumSolicitud ";
     
     frm.hdSelect.value ="SELECT DAT.CFOLIOPNC,PNC.DTNOTIFICACION, PNC.DTRESOLUCION FROM GRLREGISTROPNC PNC "
    	 				 +"INNER JOIN TRAREGDATOSADVXSOL DAT ON DAT.IEJERCICIO = PNC.IEJERCICIO AND DAT.INUMSOLICITUD = PNC.INUMSOLICITUD "
    	 				 +"WHERE PNC.IEJERCICIO ="+frm.iEjercicio.value+" AND PNC.INUMSOLICITUD ="+frm.iNumeroSolicitud.value;

//     frm.hdSelect.value = "SELECT"
//       +" PE.IEJERCICIO,"
//       +" 	PE.INUMSOLICITUD,"
//       +" 	PE.IEJERCICIOPNC,"
//       +" 	PE.ICONSECUTIVOPNC,"
//       +" 	CONCAT(SUBSTR(RC.CDSCOTRACAUSA,1,15),'...') as CDSCOTRACAUSA,"
//       +" 	O.CDSCBREVE || ' - ' || D.CDSCBREVE AS CAREA,"
//       +" RP.DTRESOLUCION,"
//       +" 	RP.DTNOTIFICACION"
//       +" FROM TRAREGPNCETAPA PE"
//       +" JOIN TRAETAPA E ON E.ICVEETAPA = PE.ICVEETAPA"
//       +" LEFT JOIN GRLREGISTROPNC RP ON RP.IEJERCICIO=PE.IEJERCICIOPNC"
//       +" AND RP.ICONSECUTIVOPNC = PE.ICONSECUTIVOPNC"
//       +" JOIN GRLREGCAUSAPNC RC ON RC.IEJERCICIO = PE.IEJERCICIOPNC"
//       +" AND RC.ICONSECUTIVOPNC = PE.ICONSECUTIVOPNC"
//       +" LEFT JOIN GRLOFICINA O ON O.ICVEOFICINA = RP.ICVEOFICINAASIGNADO"
//       +" LEFT JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO = RP.ICVEDEPTOASIGNADO"
//                          " WHERE PE.IEJERCICIO = " +frm.iEjercicio.value+
//                          "  AND PE.INUMSOLICITUD = "+frm.iNumeroSolicitud.value;
     frm.hdNumReg.value =  50;
     return fEngSubmite("pgConsulta.jsp","ListadoB");     
 }
 
 function fGetListadoC(){
     frm.hdLlave.value = "";
     frm.hdSelect.value = "";
     frm.hdLlave.value = " iEjercicio,iNumSolicitud ";
     frm.hdSelect.value = " SELECT USR.CNOMBRE||' '||USR.CAPPATERNO||' '||USR.CAPMATERNO||' ('|| OFI.CDSCBREVE||' - '|| DPT.CDSCBREVE||')' AS CNOMBRE,"
    	 +" VARCHAR_FORMAT((SELECT MAX(EVA2.DTEVALUACION) FROM TRAREGEVAREQXAREA EVA2 WHERE EVA2.ICVEUSUARIO = USR.ICVEUSUARIO AND EVA2.IEJERCICIO ="+frm.iEjercicio.value+" and EVA2.INUMSOLICITUD="+frm.iNumeroSolicitud.value+"),'DD/MM/YYYY') AS ULTEVAL " 
    	 +" FROM SEGUSUARIO USR"
    	 +" JOIN GRLUSUARIOXOFIC REL ON USR.ICVEUSUARIO = REL.ICVEUSUARIO"
    	 +" JOIN GRLOFICINA OFI ON REL.ICVEOFICINA = OFI.ICVEOFICINA"
    	 +" JOIN GRLDEPARTAMENTO DPT ON REL.ICVEDEPARTAMENTO = DPT.ICVEDEPARTAMENTO"
    	 +" WHERE USR.ICVEUSUARIO IN (SELECT DISTINCT(ICVEUSUARIO) FROM TRAREGEVAREQXAREA EVA1 where EVA1.IEJERCICIO ="+frm.iEjercicio.value+" and EVA1.INUMSOLICITUD="+frm.iNumeroSolicitud.value+")";
     frm.hdNumReg.value =  50;
     return fEngSubmite("pgConsulta.jsp","ListadoC");     
 }
 
 
 function fGetListadoD(){
	 
	 frm.hdLlave.value = "";
     frm.hdSelect.value = "";
     frm.hdLlave.value = " iEjercicio,iNumSolicitud ";
     frm.hdSelect.value = "SELECT USR.CNOMBRE||' '||USR.CAPPATERNO||' '||USR.CAPMATERNO||' ('|| OFI.CDSCBREVE||' - '|| DEPT.CDSCBREVE||')' cusuario, VARCHAR_FORMAT(RET.TSREGISTRO, 'DD/MM/YYYY') cfecha, RET.INUMDIAS FROM TRAREGRETRASO RET  " 
    	 +"INNER JOIN GRLUSUARIOXOFIC USRXOF ON USRXOF.ICVEUSUARIO = RET.ICVEUSUARIO "
    	 +"INNER JOIN GRLOFICINA OFI ON OFI.ICVEOFICINA = USRXOF.ICVEOFICINA "
    	 +"INNER JOIN GRLDEPARTAMENTO DEPT ON DEPT.ICVEDEPARTAMENTO = USRXOF.ICVEDEPARTAMENTO " 
    	 +"INNER JOIN SEGUSUARIO USR ON USR.ICVEUSUARIO = USRXOF.ICVEUSUARIO "
    	 +"WHERE RET.IEJERCICIO ="+frm.iEjercicio.value+" AND RET.INUMSOLICITUD ="+frm.iNumeroSolicitud.value;
     return fEngSubmite("pgConsulta.jsp","ListadoD");     
 }
 
 
 function fSetSolicitud(iEjercicio,iNumSolicitud, cTramite, cModalidad){
   frm.iEjercicio.value = iEjercicio;
   frm.iNumeroSolicitud.value = iNumSolicitud;
   frm.cDscTramite.value = cTramite;
   frm.cDscModalidad.value = cModalidad;
   fNavega();
 }

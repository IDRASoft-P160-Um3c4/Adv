// MetaCD=1.0
 // Title: pg111020080.js
 // Description: JS "Catálogo" de la entidad TRARegReqXTram
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Hanksel Fierro Medina || ICaballero || LEquihua
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var lCancelado = false;
 var lModificando = false;
 var aArreglo = new Array();
 var lGuardar = false;
 var lFueraTiempo = false;
 var iCveEtapaUltimaEtapaSol = 0;
 var iEtapaEntregarVU = 0;;
 var iEtapaEntregarOficialia = 0;
 var iEtapaRecibeResol = 0;
 var DescReqActual="";
 var ClaveReqActual="";
 var lModifica = false;
 var lfaltaCotejo=false;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020080.js";
//   if(top.fGetTituloPagina){
//    // cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
//	 
//   }
   cTitulo = "CERRAR PRODUCTO NO CONFORME";
   fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   JSSource("jquery.js");
   JSSource("jquery-ui.js");

   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","0","center");
    // Liga("Buscar Solicitud","fAbreBuscaSolicitud();","Búsqueda de la Solicitud");
       InicioTabla("ETablaInfo",0,"75%","","","",1);
         ITRTD("",0,"","1","center");
         InicioTabla("",0,"100%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("SOLICITUD");
           FTDTR();
             ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",6,6,"Ejercicio","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"No. Solicitud:","iNumSolicitud","",6,6,"Solicitud","fMayus(this);");
              ITD();
               BtnImg("Buscar","lupa","fBuscaSol();");
              FTD();
           FTR();
         FinTabla();
       FinTabla();
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("DESCRIPCIÓN DEL REGISTRO DEL TRÁMITE");
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Promovente:","cDscSolicitante","",50,50,"Ejercicio","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"RFC:","cDscRFC","",15,15,"Ejercicio","fMayus(this);");
             ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cDscTramite","",50,50,"Trámite","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cDscModalidad","",50,50,"Modalidad","fMayus(this);");
             FITR();
              //TDEtiCampo(false,"EEtiqueta",0," Autorizado a Recoger:","cNomAutorizaRecoger","",50,100," Persona Autorizada a Recoger Trámite","fMayus(this);","","",false,"EEtiquetaL",0);
              //TDEtiSelect(true, "EEtiqueta", 0, "PNC:", "iConsecutivoPNC", "fCambiaPNC();");
              
              Hidden("iCveOficina",0);
              Hidden("cEnviarResolucionA","");
//              Hidden("iConsecutivoPNC",0);
              
//              ITD("EEtiqueta",0,"0","","center","middle");
//                TextoSimple("Enviar resolución a:");
//              FITD("EEtiquetaL",0,"0","","center","middle");
//                Select("iCveOficina","fChangeOficinaEnvio();");
//              FTD();
//              FITR();
//                ITD("EEtiqueta",3,"0","","center","middle");
//                  TextoSimple("");
//                FITD("EEtiquetaL",2,"0","","center","middle");
//                  Text(false,"cEnviarResolucionA","",40,40,"","fMayus(this);","","",false,true);
//               FTD();
//             FITR();
//               TDEtiSelect(true, "EEtiqueta", 0, "PNC:", "iConsecutivoPNC", "fCambiaPNC();");
               
               ITR();
               	ITD();
               		Liga("*Ver Documentos Anexos","fVerDocsADV()","");
                FTD();
               FTR();
          		
         FinTabla();
       FTDTR();
     ITRTD("",0,"95%","","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Requisitos Entregados");
           FTDTR();
           ITRTD("",0,"","","center");
             IFrame("IListadoA","100%","170","Listado.js","yes",true);
           FTDTR();
         FinTabla();
         FTDTR();

         FITR();
         FITR();
         ITRTD("",0,"95%","","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             InicioTabla("",0,"95%","","","",1);
               ITD("ETablaST");TextoSimple("Requisitos Pendientes ");FITD();//Liga("Mostrar PNC","fGetPNC();");
             FinTabla();
           FTDTR();
           ITRTD("",0,"","","center");
             IFrame("IListado","100%","170","Listado.js","yes",true);
           FTDTR();
         FinTabla();
     ITRTD("",0,"95%","","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
         	TDEtiAreaTexto(true, "EEtiqueta", 0, "Observaciones:", 50,
			2, "cObservacion", "", "TooTip", "", "fMayus(this);",
			'onkeydown="fMxTx(this,250);"', true, true, true, "",
			10);
         FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
     Hidden("iCveTramite","");
     Hidden("iCveModalidad","");
     Hidden("iCveRequisito","");
     Hidden("lEntregado","");
     Hidden("iCveUsuRecibe","");
     Hidden("cConjunto");
     Hidden("iLlave");
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("dtNotificacion");
     Hidden("dtfechaActual");
     Hidden("iCveUsuario","");
     
     Hidden("iCveOficinaU","");
     Hidden("iCveDepartamentoU","");
     Hidden("cNomAutorizaRecoger","");
     Hidden("iConsecutivoPNC",-1);
     
     cGPD += '<div id="ALConceptos" style="text-align:justify;"></div>';
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(false);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetSelReg(1);
   FRMListado.fSetTitulo("Entregado,Requisito,Válido,Notificación,Entregado,");
   FRMListado.fSetCampos("6,7,4,5,");
   FRMListado.fSetDespliega("texto,texto,logico,texto,");
   FRMListado.fSetAlinea("left,left,center,center,");
   FRMListado.fSetObjs(0,"Caja");
   FRMListadoA = fBuscaFrame("IListadoA");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetSelReg(2);
   FRMListadoA.fSetTitulo("Requisito,Válido,Notificación,Entregado,");
   FRMListadoA.fSetCampos("6,7,4,5,");
   FRMListadoA.fSetDespliega("texto,logico,texto,texto,");
   FRMListadoA.fSetAlinea("left,center,center,");
   fDisabled(true,"iEjercicio,iNumSolicitud,");
   frm.hdBoton.value="Primero";   

   $("#ALConceptos").dialog({
	    resizable: false,
	    autoOpen: false,
	    width: 420});
   
   frm.iCveUsuario.value = fGetIdUsrSesion();
   fTraeFechaActual();
   fObtenOficDepto();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
	  
     if(frm.iConsecutivoPNC.value>0){
	 frm.hdFiltro.value = " s.iEjercicio= "+frm.iEjercicio.value +
         " AND s.iNumSolicitud = " + frm.iNumSolicitud.value +
         //" AND RP.DTNOTIFICACION IS NOT NULL "+
         " and RP.ICONSECUTIVOPNC= "+frm.iConsecutivoPNC.value +
         " AND (LRECNOTIFICADO = 0 OR LRECNOTIFICADO IS NULL)"+
         //" AND RR.LRECNOTIFICADO IS NOT NULL"+
         " AND rc.ICVEREQUISITO is not null ";
         frm.hdOrden.value =  "";
         frm.hdNumReg.value = 10000;
         
         fEngSubmite("pgTRAModSolicitud.jsp","Listado");
     } else fAlert("No se encontraron Productos No Conformes para esta solicitud.");
 }
 function fNavega1(){
//	 alert("fnavega1");
	  frm.hdFiltro.value = " s.iEjercicio= "+frm.iEjercicio.value +
	                        " AND s.iNumSolicitud = " + frm.iNumSolicitud.value +
	                        " and RP.ICONSECUTIVOPNC= "+frm.iConsecutivoPNC.value +
	                        " AND RMT.ICVEREQUISITO not in " +
	                        "	( SELECT DISTINCT(C.ICVEREQUISITO) FROM TRAREGPNCETAPA PE1 " +
	                        "	JOIN TRAREGREQXCAUSA C ON C.IEJERCICIO=PE1.IEJERCICIOPNC AND C.ICONSECUTIVOPNC=PE1.ICONSECUTIVOPNC " +
	                        //"	WHERE PE1.IEJERCICIO = s.iejercicio AND PE1.INUMSOLICITUD= s.INUMSOLICITUD  AND RR.LRECNOTIFICADO IS NOT NULL) ";
	                        " WHERE PE1.IEJERCICIO = s.iejercicio AND PE1.INUMSOLICITUD= s.INUMSOLICITUD  AND (RR.LRECNOTIFICADO = 0 OR RR.LRECNOTIFICADO IS NULL)) ";
	  

	   frm.hdOrden.value =  "";
	   frm.hdNumReg.value =  10000;
	   fEngSubmite("pgTRAModSolicitud.jsp","ListadoA");
   }
 function fNavega2(){
	 //alert("fnavega2");
	  frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio= "+frm.iEjercicio.value +
	                        " AND TRAREGSOLICITUD.iNumSolicitud = " + frm.iNumSolicitud.value +
	                        " AND TRAReqXModTramite.ICVEREQUISITO not in " +
	                        "	( SELECT DISTINCT(C.ICVEREQUISITO) FROM TRAREGPNCETAPA PE1 " +
	                        "	JOIN TRAREGREQXCAUSA C ON C.IEJERCICIO=PE1.IEJERCICIOPNC AND C.ICONSECUTIVOPNC=PE1.ICONSECUTIVOPNC " +
	                        "	WHERE PE1.IEJERCICIO = TRAREGSOLICITUD.iejercicio AND PE1.INUMSOLICITUD= TRAREGSOLICITUD.INUMSOLICITUD) ";

	   frm.hdOrden.value =  "";
	   frm.hdNumReg.value =  10000;
	   fEngSubmite("pg111020080.jsp","ListadoA");
}
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,lValido){
   if(cError=="Guardar"){
     fAlert("Existió un error en el Guardado!");
     return false;
   }
   else if(cError=="Borrar"){
     fAlert("Existió un error en el Borrado!");
     return false;
   }
   else if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return false;
   }
   else if(cError!=""){
       fAlert(cError);
       fCancelar();
   }
   if(cId == "idFechaActual" && cError == ""){
     frm.iEjercicio.value = aRes[1][2];
     fCargaOficina();
   }

   if(cId == "CIDOficina" && cError=="")
     fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value,0,1);
   
   if(cId == "cIdRegPNC" && cError == ""){
	   if(aRes.length>0){
		   frm.iConsecutivoPNC.value = aRes[0][8];
	       fPNCOnChange();
	   }
   }
   
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     if(lModifica == true) {
	 fAlert("Se ha cerrado el Producto No Conforme");
	 lModifica = false;
     }
     for (i=0;aRes[i];i++){
        if (aRes[i][5]!="" && aRes[i][4]=="") aRes[i][8]=1
        else aRes[i][8]=0;
        
        if(aRes[i][13]=="")
        	lfaltaCotejo=true;

     }
     aArreglo = fCopiaArregloBidim(aRes);
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     FRMListado.fSetDefaultValues(0,0);
     fNavega1();
   }

   if(cId == "Cambia" && cError==""){
     fCancelado();
   }
   if(cId == "idEncabezado" && cError==""){
     if(aRes.length > 0){
         frm.cDscSolicitante.value = aRes[0][0]+" "+aRes[0][1]+" "+aRes[0][2];
         frm.cDscRFC.value = aRes[0][3];
         frm.cDscTramite.value = aRes[0][4];
         frm.cDscModalidad.value = aRes[0][5];
     }else{
       fAlert('\n - El trámite ya ha sido entregado, ya no se puede efectuar modificación alguna.');
       return false;
     }


     //Sigo utilizando lValido por que en esa posición ya se esta ocupando en otro jsp
     var aEtapas = new Array();
     aEtapas = lValido.split(",");
     iEtapaEntregarVU = aEtapas[0];
     iEtapaEntregarOficialia = aEtapas[1];
     iEtapaRecibeResol = aEtapas[2];
     fEtapaUltima();
   }

   if(cId == "EtapaUltima" && cError == ""){
     if(aRes && aRes.length > 0)
       iCveEtapaUltimaEtapaSol = aRes[0][0];
     fNavega();
   }

   if(cId == "ListadoA" && cError==""){
     frm.hdRowPag.value = iRowPag;
     for (i=0;aRes[i];i++){//FRMListado.fGetLength()
        if (aRes[i][5]!="" && aRes[i][4]=="") aRes[i][8]=1
          else aRes[i][8]=0;
     }
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
     fCancelar();
     fCancelado();

   }
   if(cId == "idCancelado" && cError == ""){
      if(aRes.length > 0){
         lCancelado = true;
         fAlert("Esta Solicitud está cancelada por " + aRes[0][2]);
        FRMPanel.fSetTraStatus("");
      }else
         lCancelado = false;

      fDesactiva();
   }
   if(cId == "Reporte" && cError==""){
     aResReporte = aRes;
   }

   if(cId == "ValidaNotif" && cError == ""){
     if(parseInt(lValido,10) == 0){
       lFueraTiempo = true;
       fAlert('\n - La solicitud se encuentra fuera de tiempo, no se puede realizar esta acción.');
       fCancelar();
       FRMPanel.fSetTraStatus(",");
     }
   }
   if(cId == "cIdSolicitud" && cError == ""){
       if(aRes[0][2]!=""){frm.cDscSolicitante.value = aRes[0][2]};
       if(aRes[0][3]!=""){frm.cDscRFC.value = aRes[0][3]};
       if(aRes[0][4]!=""){frm.cDscTramite.value = aRes[0][3]};
       if(aRes[0][5]!=""){frm.cDscModalidad.value = aRes[0][5]};
       if(aRes[0][6]!=""){frm.cNomAutorizaRecoger.value = aRes[0][6]};
       if(aRes[0][7]!=""){frm.iCveOficina.value = aRes[0][2]};
       if(aRes[0][8]!=""){frm.cEnviarResolucionA.value = aRes[0][8]};
       fBuscaPNC();
   }

   if(cId=="cIdPNC" && cError==""){
	   var cMsgAlert = "";
	   if(aRes.length>0){
		   cMsgAlert +=  "<br> Ejercicio PNC:  "+aRes[0][0];
		   cMsgAlert +=  "<br> Numero de PNC:  "+aRes[0][1];
		   for(iA=0;iA<aRes.length;iA++){
			   cMsgAlert +=  "<br></br>"
			   cMsgAlert +=  "<br> Causa #"+(iA+1)+"  Producto:    "+aRes[iA][2];
			   cMsgAlert +=  "<br> Causa #"+(iA+1)+"  Causa:       "+aRes[iA][3];
			   cMsgAlert +=  "<br> Causa #"+(iA+1)+"  Otra Causa:  "+aRes[iA][4];
			   cMsgAlert +=  "<br> Causa #"+(iA+1)+"  Resuelto:    "+((parseInt(aRes[iA][5],10)==1)?"Sí":"No");
		   }
		   fAlertCaract("DATOS DEL PNC", cMsgAlert);
	   }
   }
   
   
   if(cId == "idOficeDepto" && cError==""){
	    if(aRes.length > 0){
	       frm.iCveOficinaU.value = aRes[0][0];
	       frm.iCveDepartamentoU.value = aRes[0][1];
	    }
   }
	 
   return true;
 }
 
 function fObtenOficDepto(){
	  //alert("ObtenOficDepto");
	  frm.hdLlave.value = "";
	    frm.hdSelect.value = "SELECT ICVEOFICINA, ICVEDEPARTAMENTO, 0 AS LANEXO FROM GRLUSUARIOXOFIC WHERE ICVEUSUARIO =" + frm.iCveUsuario.value;
	  /*frm.hdSelect.value = "SELECT ICVEOFICINA, ICVEDEPARTAMENTO, 0 as LANEXO "+
	                       "FROM TRAREGETAPASXMODTRAM "+
	                       "WHERE iEjercicio = " + frm.iEjercicio.value + " AND iNumSolicitud = " + frm.iNumSolicitud.value +
	                       " AND iCveTramite = " + frm.iCveTramite.value + " AND iCveModalidad = " + frm.iCveModalidad.value +
	                       " ORDER BY IORDEN DESC";*/
	    fEngSubmite("pgConsulta.jsp","idOficeDepto");
	}

 function fBuscaSol(){
	 if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != '' &&
		       frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != '' && fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)){
			     frm.hdBoton.value = "GetSolicitud";
			     frm.hdFiltro.value = "S.iEjercicio= "+frm.iEjercicio.value+" AND S.iNumSolicitud = "+frm.iNumSolicitud.value;
			     frm.hdOrden.value = "";
			     frm.hdNumReg.value = 100;
			     fEngSubmite("pgTRAModSolicitud.jsp","cIdSolicitud");
	 }else{
		 fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	 }
 }
 
 function fBuscaPNC(){
     frm.hdFiltro.value = "iEjercicio= "+frm.iEjercicio.value+" AND iNumSolicitud = "+frm.iNumSolicitud.value;
     frm.hdOrden.value = " ICONSECUTIVOPNC DESC";
     frm.hdNumReg.value = 100;
     fEngSubmite("pgTRARegPNCEtapa.jsp","cIdRegPNC");
 }
 
 function fEncabezado(){
     if(frm.iEjercicio.value != "" && frm.iNumSolicitud.value != ""){
	 frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio= "+frm.iEjercicio.value +
	                      " AND TRAREGSOLICITUD.iNumSolicitud = " + frm.iNumSolicitud.value +
	                      " and TRAREGSOLICITUD.dtEntrega is null ";
	 frm.hdOrden.value =  "";
	 frm.hdNumReg.value =  10000;
	 fEngSubmite("pgTRARegSolicitudc.jsp","idEncabezado");
     }else{
	 fAlert("Introduzca el Ejercicio y el Número de Solicitud para hacer la búsqueda");
     }
 }
 
 
 
 
 function fCargaOficina(){
     frm.hdLlave.value = "iCveOficina";
     frm.hdSelect.value = "Select iCveOficina,cDscBreve from GRLOficina order by cDscBreve";
     fEngSubmite("pgConsulta.jsp","CIDOficina");
 }
 
 function fPNCOnChange(){
     fNavega();
 }
 
 function fActualizaAutRec(){
	 
	 
	 
     if(lGuardar){
    	 //alert("fActualizaAutRec");
       frm.hdBoton.value = "GuardarCambios";
       lGuardar = false;
       frm.cNomAutorizaRecoger.disabled = true;
      // frm.cEnviarResolucionA.disabled = true;
       frm.iCveOficina.disabled = true;
       frm.iEjercicio.disabled = false;
       frm.iNumSolicitud.disabled = false;
       fNavega();
     }
 }
 function fEditar(){
     if(frm.cDscSolicitante.value != ""){
    	 //alert("fEditar");
         frm.cNomAutorizaRecoger.disabled = false;
         frm.iEjercicio.disabled = true;
         frm.iNumSolicitud.disabled = true;
         frm.cNomAutorizaRecoger.focus();
         if(iCveEtapaUltimaEtapaSol != iEtapaEntregarVU && iCveEtapaUltimaEtapaSol != iEtapaEntregarOficialia &&
            iCveEtapaUltimaEtapaSol != iEtapaRecibeResol){
           //frm.cEnviarResolucionA.disabled = false;
           frm.iCveOficina.disabled = false;
         }
         lGuardar = true;
     }else
       fAlert('n - Es necesario que exista al menos un registro para ejecutar esta acción.');
 }
 function fCancelarEditar(){
     if(lGuardar){
       lGuardar = false;
       frm.cNomAutorizaRecoger.disabled = true;
       //frm.cEnviarResolucionA.disabled = true;
       frm.iCveOficina.disabled = true;
       frm.iEjercicio.disabled = false;
       frm.iNumSolicitud.disabled = false;
       FRMListado.fShow();
       FRMListadoA.fShow();
     }
 }
 
 function fCambiaPNC(){
	 //alert("fCambiaPNC");
     fNavega();
 }
 function fCancelar(){
     lModificando = false;
     fDesactiva();
     if(FRMListado.fGetLength() > 0)
       FRMPanel.fSetTraStatus("Mod,");
     else
       FRMPanel.fSetTraStatus(",");

     fDisabled(false);
     fDisabled(true,"iEjercicio,iNumSolicitud,");
  }

 
 function fDesactiva(){
     for(i = 0; i < aArreglo.length; i++){
        oCaja = eval("FRMListado.document.forms[0].OCajaB0"+i);
        if(lCancelado == false && lModificando == true)
          oCaja.disabled = false;
        else
          oCaja.disabled = true;
     }
   }

 function fCancelado(){
    frm.hdLlave.value = "ICVEUSUARIO";
    frm.hdSelect.value = "SELECT " +
                         "DTCANCELACION, " +
                         "COBS, " +
                         "CDSCMOTIVO " +
                         "FROM traregtramxsol " +
                         "JOIN GRLMOTIVOCANCELA ON GRLMOTIVOCANCELA.ICVEMOTIVOCANCELA = " +
                         "traregtramxsol.ICVEMOTIVOCANCELA " +
                         " where iEjercicio = "+frm.iEjercicio.value +
                         " AND iNumSolicitud = " + frm.iNumSolicitud.value;
    frm.hdNumReg.value = 1000;
    fEngSubmite("pgConsulta.jsp","idCancelado");
 }
 function fModificar(){
	 //alert("fModificar");
    if(lfaltaCotejo == false){
		lModificando = true;
	    FRMPanel.fSetTraStatus("UpdateBegin");
	    fDisabled(true);
	    frm.cObservacion.disabled = false;
	    FRMListado.fSetDisabled(false);
	   // fValidaFechaNotif();
    }else
    	fAlert("\nNo puede cerrrar el Producto No Conforme, no se ha terminado de cotejar la documentación faltante.");
	    
 }
 function fGuardarA(){
	 //alert("fGuardarA");
     aCBox = FRMListado.fGetObjs(0);
     aRes = FRMListado.fGetARes();
     lModificando = false;
     frm.cConjunto.value="";
     for(aux=0; aux<aCBox.length; aux++){
    	 //alert("aCBox[aux]----->"+aCBox[aux]);
       if (aCBox[aux]==true){
         if(aRes[aux][4]!="" || aRes[aux][5]==""){
             frm.iLlave.value=1;
           if (frm.cConjunto.value == ""){
             frm.cConjunto.value = aRes[aux][2];
           }else
             frm.cConjunto.value += "," + aRes[aux][2];
         }
       }

     }
    if (frm.iLlave.value == 0)
      frm.cConjunto.value = -1;
    frm.hdBoton.value = "Cambia";
    lModifica = true;
    fNavega();
  }
 function fValidaFechaNotif(){
     frm.hdBoton.value = "ValidaNotif";
     frm.hdOrden.value = "";
     frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value + " AND TRAREGSOLICITUD.iNumSolicitud = " + frm.iNumSolicitud.value ;
     fEngSubmite("pg111020080.jsp","ValidaNotif");
   }
 function fSelReg(aDato,iCol){
     frm.iCveTramite.value = aDato[0];
     frm.iCveModalidad.value = aDato[1];
     frm.iCveRequisito.value = aDato[2];
     frm.dtNotificacion.value = aDato[4];
     frm.cNomAutorizaRecoger.value = aDato[9];
     if(aDato[10] && aDato[10] != "")
       fSelectSetIndexFromValue(frm.iCveOficina,aDato[10]);
     else
       fSelectSetIndexFromValue(frm.iCveOficina,-1);
     frm.cEnviarResolucionA.value = aDato[11];
  }

  function fSelReg2(aDato,iCol){
    frm.cNomAutorizaRecoger.value = aDato[9];
    if(aDato[10] && aDato[10] != "")
      fSelectSetIndexFromValue(frm.iCveOficina,aDato[10]);
    else
      fSelectSetIndexFromValue(frm.iCveOficina,-1);
    frm.cEnviarResolucionA.value = aDato[11];
  }
  // FUNCIÓN donde se generan las validaciones de los datos ingresados
  function fValidaTodo(){
     cMsg = fValElements();

     if(cMsg != ""){
        fAlert(cMsg);
        return false;
     }
     return true;
  }
  function fImprimir(){
     self.focus();
     window.print();
  }
  function fReporte(){
  //  if(lCancelado == false){
      if(lFueraTiempo || lCancelado){
        cClavesModulo="2,";
        cNumerosRep="6,";
        cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + fGetIdUsrSesion() + "," + frm.iCveTramite.value + "." + frm.iCveModalidad.value +  cSeparadorRep;
      }else{
        cClavesModulo="2,";
        cNumerosRep="3,";
        cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value + cSeparadorRep;
      }

       lEjecuta = true;

       if (lEjecuta)
         fReportes();
       else
         fAlert("\n-Debe seleccionar un trámite para poder imprimir el acuse de recibo");
  }
 
 
 
 
 
 
 
 
 
 /*


 function fEtapaUltima(){
   frm.hdLlave.value = "";
   frm.hdSelect.value = "SELECT " +
		"ICVEETAPA " +
		"FROM TRAREGETAPASXMODTRAM " +
		"where IEJERCICIO = " + frm.iEjercicio.value +
		" and INUMSOLICITUD = " + frm.iNumSolicitud.value +
		" order by IORDEN desc ";

   fEngSubmite("pgConsulta.jsp","EtapaUltima");
 }





 function fDesactiva1(){
   for(i = 0; i < aArreglo.length; i++){
      oCaja = eval("FRMListado.document.forms[0].OCajaB0"+i);
      oCaja.disabled = true;
   }
 }



 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    lModificando = true;
    fDesactiva();
    FRMPanel.fSetTraStatus("Sav,");
    fBlanked();
    fDisabled(false,"iEjercicio,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    aCBox = FRMListado.fGetObjs(0);
    aRes = FRMListado.fGetARes();
    lModificando = false;
    frm.cConjunto.value="";

    for(aux=0; aux<aCBox.length; aux++){
      if (aCBox[aux]==true){
        if(aRes[aux][4]!="" || aRes[aux][5]==""){
            frm.iLlave.value=1;
          if (frm.cConjunto.value == ""){
            frm.cConjunto.value = aRes[aux][2];
          }else{
            frm.cConjunto.value += "," + aRes[aux][2];
          }
        }
      }

    }
    if (frm.iLlave.value == 0)
      frm.cConjunto.value = -1;
    frm.hdBoton.value = "Otro";
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"

 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar

 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar

 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       lModificando = false;
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón




function fSetiEjercicioYiNumSolicitud(iValor1,iValor2){
   frm.iEjercicio.value = iValor1;
   frm.iNumSolicitud.value = iValor2;
}

function fBuscaSolicitud(){
   frm.iEjercicio.value ;
   frm.iNumSolicitud.value ;
}

 function fSetSolicitud(iEjercicio,iNumSolicitud){
   frm.iEjercicio.value = iEjercicio;
   frm.iNumSolicitud.value = iNumSolicitud;
   fBuscaSolicitud();
 }
 function Recorre(){
    aCBox = FRMListado.fGetObjs(0);
    aRes = FRMListado.fGetARes();
    frm.cConjunto.value="";
    for(aux=0; aux<aCBox.length; aux++){
      if (aCBox[aux]==true){
        if((aRes[aux][7]==0&&aRes[aux][4]!="")||aRes[aux][5]==""){
            frm.iLlave.value=1;
          if (frm.cConjunto.value == ""){
            frm.cConjunto.value = aRes[aux][2];
          }else
            frm.cConjunto.value += "," + aRes[aux][2];
        }
      }

    }
   if (frm.iLlave.value == 0) frm.cConjunto.value = -1;
   frm.hdBoton.value = "Cambia";
}











function fChangeOficinaEnvio(){
  if (frm.iCveOficina.value > 0)
    frm.cEnviarResolucionA.value = frm.iCveOficina.options[frm.iCveOficina.selectedIndex].text;
  else
    frm.cEnviarResolucionA.value = "";
}

function fEnviaReqCalificar(targetForm){
  if (targetForm){
    if (targetForm.iCveRequisito && ClaveReqActual != ""){
      targetForm.iCveRequisito.value = ClaveReqActual;
      targetForm.cDscBreve.value     = DescReqActual;
      targetForm.lEnRecepcion.value = 1;
    }
  }
}


function fValoresActuales(){
	return 0;

}
function fRecibeCalifica(iCveRequisito, aDatos, window){
   
}

function fAlertCaract(cTitulo,cTexto){
    $("#ALConceptos").html(cTexto);
    $("#ALConceptos").dialog('option', 'title',cTitulo);
    $("#ALConceptos").dialog('open');
}

function fGetPNC(){
  frm.hdSelect.value = "SELECT RE.IEJERCICIOPNC,RE.ICONSECUTIVOPNC,P.CDSCBREVE,C.CDSCCAUSAPNC,CP.CDSCOTRACAUSA,CP.LRESUELTO " +
  					   "FROM TRAREGPNCETAPA RE " +
  					   "JOIN GRLREGCAUSAPNC CP ON CP.IEJERCICIO=RE.IEJERCICIOPNC AND CP.ICONSECUTIVOPNC=RE.ICONSECUTIVOPNC " +
  					   "JOIN GRLPRODUCTO P ON P.ICVEPRODUCTO=CP.ICVEPRODUCTO " +
  					   "JOIN GRLCAUSAPNC C ON C.ICVEPRODUCTO=CP.ICVEPRODUCTO AND C.ICVECAUSAPNC=CP.ICVECAUSAPNC " +
  					   "WHERE RE.IEJERCICIO="+frm.iEjercicio.value+" AND INUMSOLICITUD="+frm.iNumSolicitud.value;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp","cIdPNC")
}*/
  
  

  function fVerDocsADV(){
  	 if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != '' &&
  		       frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != ''){
  			   if(fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)){
  				   
  				   fAbreSubWindow(false,"pgVerDocsCotejo","no","yes","yes","yes","800","600",50,50);
  				   
  			   }else{
  				   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
  			   }
  	   }else{
  		   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
  	   }
    
  }
  
  function getNumSol(){
		return  frm.iNumSolicitud.value;
	}
  
  function getEjercicio(){
		return  frm.iEjercicio.value;
	}
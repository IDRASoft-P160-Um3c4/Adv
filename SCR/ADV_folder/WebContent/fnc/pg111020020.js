// MetaCD=1.0
 // Title: pg111020020.js
 // Description: JS "Catálogo" de la entidad TRARegEtapasXModTram
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ocastrejon; lequihua; iCaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var lConsulta = true;
 var lEncontre = false;
 var lCambiar = false;
 var iCveEtapaFinal = 0;
 var iCveUltimaEtapa = 0;
 var iCveEtapaVerificacion = 0;
 var iCveEtapaEntregaVU = 0;
 var iCveEtapaEntregaOfi = 0;
 var iCveEtapaRecibeResol = 0;
 var iCveEtapaEntregaResol = 0;
 var iCveEtapaDocRetorno = 0;
 var iCveEtapaConclusionTramite = 0;
 var iCveEtapaNotificado = 0;
 var iCveEtapaResEnviadaOfic = 0;
 var iCveEtapaTramiteCancelado = 0;
 var iCveTramiteCertificaDoc = 0;
 var iCveUltimaOficina = 0;
 var iCveUltimoDepto = 0;
 var iCveEtapaEntregaPermiso = 0;
 var iCveEtapaVisita=0;
 var permiso = 0;
 var entResol = 0; 
 var cvRes = 7;//id etapa resolucion
 var cNomTitulo="notFound"; 

 var resolcionArrayC = new Array();
 resolcionArrayC[0] = ['0','Negativo'];
 resolcionArrayC[1] = ['1','Positivo'];

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020020.js";
//   if(top.fGetTituloPagina){
//     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
//   }
	 cTitulo= "RESOLUCIÓN - CONCLUSIÓN DE TRÁMITE";
   fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);

   InicioTabla("ETablaInfo",0,"","","center","","1");
     ITRTD("ECampoC",6,"100%","center","top");
     //  Liga("Buscar Solicitud","fAbreBuscaSolicitud();","Búsqueda de la Solicitud");

     ITRTD("ETablaST",6,"100%","","center");
       TextoSimple("Selección de la Solicitud");
     FTDTR();
       TDEtiCampo(true,"EEtiqueta",0,"Ejercicio:","iEjercicioFiltro","",4,4,"Ejercicio","fMayus(this);");
       TDEtiCampo(true,"EEtiqueta",0,"No. Solicitud:","iNumSolicitudFiltro","",8,8,"Núm. Solicitud","fMayus(this);");
       ITD("EEcampo",0,"","","LEFT","LEFT");
       BtnImg("Buscar","lupa","fNavega();");
     FITR();
   FinTabla();
   InicioTabla("ETablaInfo",0,"75%","","center","",1);
           ITRTD("ETablaST",11,"","","center");
           FTDTR();
             TDEtiCampo(false,"EEtiqueta",0,"Homoclave:","cHomoclave","",18,18,"Homoclave del Trámite","","","",true,"",0);
           FTDTR();
             Hidden("iCveTramite","");

             TDEtiAreaTexto(false,"EEtiqueta",0,"Trámite:",80,3,"cDscTramite","","Trámite","","fMayus(this);",'onkeydown="fMxTx(this,150);"',"","",false,"",8);
           FTDTR();
             ITD("EEtiqueta",0,"","","LEFT","LEFT");
             TextoSimple("Modalidad:");
             ITD("ECampo",5,"","","LEFT","LEFT");
             Text(false,"cDscModalidad","",70,70,"Modalidad del Trámite","","","",false,false);
             Hidden("iCveModalidad","");
           FITR();
             ITD("EEtiqueta",0,"","","LEFT","LEFT");
             TextoSimple("Promovente:");
             ITD("ECampo",5,"","","LEFT","LEFT");
             Text(false,"cSolicitante","",70,70,"Promovente","","","",false,false);
             Hidden("iCveSolicitante","");
           FITR();
         FinTabla();
       FinTabla();
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple("Histórico de las Etapas");
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         
       
       InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",4,"","","center");
             TextoSimple("Resolución Final de Trámite");
           FTDTR();
          
           ITR("","","","","","");
           	ITD();
           	  Hidden("iCveEtapa","");
              Hidden("iCveEtapaAnt","");
              Hidden("lAnexo",1);
              FTD();
           FTR();
           
           ITR("","","","","");
           
           	   ITD();FTD();
           	ITD();Liga("*Ver Documentación Anexa al Trámite","verDocsTramite()","Buscar");FTD();
           	ITD();Liga("*Generar Oficios, Resolución, Permiso","fDocsFinales()","Buscar");FTD();
	           ITD();Liga("*Generar hoja de ayuda ingresos","fBuscaDocumentos()","Buscar");FTD();
	           
           FTR();
           
           ITR("","","","","");
           
           ITD();FTD();
	       	 ITD();FTD();
//	       	  
	       	   ITD();FTD();
	           ITD();FTD();
	           
           FTR();
           
           ITR("","","","","");
           
	       	   ITD();FTD();
//	       	   ITD();Liga("*Subir Oficios Anexos al Trámite","subirOficiosTramite()","Buscar");FTD();
//	       	   ITD();Liga("*Ver Documentación Anexa al Trámite","verDocsTramite()","Buscar");FTD();
	           ITD();FTD();
           
           FTR();
                     
          ITRTD("","","","","");              
          	TDEtiSelect(true, "EEtiqueta", 0, "Resolución:", "lResolucion", "change();");
          FTDTR();
          
          ITRTD("","","","","");
          	TDEtiAreaTexto(false,"EEtiqueta",1,"Observaciones:",75,4,"cObsCIS","","Observaciones:","","fMayus(this);",'onkeydown="fMxTx(this,250);"',true,true,true,"ECampo",5);
          FTDTR();

         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
     Hidden("iCveUsuario",fGetIdUsrSesion());
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("dtEntrega");
     Hidden("iEjercicio");
     Hidden("iNumSolicitud");
     
     
     Hidden("hdiCveSolicitante");
     Hidden("hdcRFC");
     Hidden("hdcRPA");
     Hidden("hdiCveTipoPersona");
     Hidden("hdPersona_cNomRazonSocial");
     Hidden("hdcApPaterno");
     Hidden("hdcApMaterno");
     Hidden("hdcCorreoE");
     Hidden("hdcPseudonimo");
     Hidden("hdiCveDomicilioSolicitante");
     Hidden("hdiCveTipoDomicilio");
     Hidden("hdcCalle");
     Hidden("hdcNumExterior");
     Hidden("hdcNumInterior");
     Hidden("hdcColonia");
     Hidden("hdcCodPostal");
     Hidden("hdcTelefono");
     Hidden("hdiCvePais");
     Hidden("hdcDscPais");
     Hidden("hdiCveEntidadFed");
     Hidden("hdcDscEntidadFed");
     Hidden("hdiCveMunicipio");
     Hidden("hdcDscMunicipio");
     Hidden("hdiCveLocalidad");
     Hidden("hdcDscLocalidad");
     Hidden("hdlPredeterminado");
     Hidden("hdcDscTipoDomicilio");
     Hidden("hdcDscDomicilio");
     Hidden("hdiCveTramite");
     Hidden("hdiCveModalidad");
     Hidden("hdUsrIdADV");
     
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   frm.hdUsrIdADV.value = fGetIdUsrSesion();
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   
   fFillSelect(frm.lResolucion,resolcionArrayC,true,0,0,1);
   
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   
   FRMListado.fSetTitulo("Registro,Descripción,Fecha,Usuario,Oficina,");
   FRMListado.fSetCampos("8,7,13,12,15,");
   FRMListado.fSetAlinea("right,left,center,left,left,")

   
//   FRMListado.fSetTitulo("Registro,Descripción,Fecha,Usuario,Oficina,Depto,");
//   FRMListado.fSetCampos("8,7,13,12,15,16,");
//   FRMListado.fSetAlinea("right,left,center,left,left,left,")


   fDisabled(false);
   frm.hdBoton.value="Primero";

   fDisabled(true,"iEjercicioFiltro,iNumSolicitudFiltro,");
   fTraeFechaActual();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   if (frm.iEjercicioFiltro.value != 0 && frm.iEjercicioFiltro.value != '' &&
       frm.iNumSolicitudFiltro.value != 0 && frm.iNumSolicitudFiltro.value != ''){

	   
	   frm.hdFiltro.value =  " TRARegEtapasXModTram.iEjercicio    = " + frm.iEjercicioFiltro.value +
                            " and TRARegEtapasXModTram.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
       frm.hdFiltro.value = frm.hdFiltro.value  +
                          " and ((select count(*) from TRARegTramXSol where TRARegTramXSol.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
                          " and TRARegTramXSol.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud) = 0) ";
 
       frm.hdFiltro.value = frm.hdFiltro.value  +
                          " and ((select count(*) from TRARegPNCEtapa " +
                          " join GRLRegistroPNC on GRLRegistroPNC.iEjercicio = TRARegPNCEtapa.iEjercicioPNC " +
                          " and GRLRegistroPNC.iConsecutivoPNC = TRARegPNCEtapa.iConsecutivoPNC " +
                          " and GRLRegistroPNC.lResuelto = 0 " +
                          " where TRARegPNCEtapa.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
                          " and TRARegPNCEtapa.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
                          " and TRARegPNCEtapa.iCveEtapa in (select iCveEtapa " +
                          " from  TRARegEtapasXModTram " +
                          " where TRARegEtapasXModTram.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
                          " and TRARegEtapasXModTram.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
                          " and TRARegEtapasXModTram.iOrden = (select max(iOrden) " +
                          " from TRARegEtapasXModTram " +
                          " where TRARegEtapasXModTram.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
                          " and TRARegEtapasXModTram.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud)) ) = 0) ";
   
       frm.hdOrden.value =  " TRARegEtapasXModTram.iOrden ";
       frm.hdNumReg.value =  10000;
       fEngSubmite("pgTRARegEtapasXModTram.jsp","Listado");
   }else{
	   fAlert("\nDebe ingresar un Ejercicio/No. Solicitud válido.");
   }
 }
 /* FUNCION ORIGINAL
 function fNavega(){
	   if (frm.iEjercicioFiltro.value != 0 && frm.iEjercicioFiltro.value != '' &&
	       frm.iNumSolicitudFiltro.value != 0 && frm.iNumSolicitudFiltro.value != '')
	      frm.hdFiltro.value =  " TRARegEtapasXModTram.iEjercicio    = " + frm.iEjercicioFiltro.value +
	                            " and TRARegEtapasXModTram.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
	   else
	      frm.hdFiltro.value =  " TRARegEtapasXModTram.iEjercicio    = 0" +
	                            " and TRARegEtapasXModTram.iNumSolicitud = 0 ";

	   if (frm.hdFiltro.value != "")
	     frm.hdFiltro.value = frm.hdFiltro.value  +
	                          " and ((select count(*) from TRARegTramXSol where TRARegTramXSol.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegTramXSol.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud) = 0) ";
	   else
	     frm.hdFiltro.value = " ((select count(*) from TRARegTramXSol where TRARegTramXSol.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegTramXSol.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud) = 0) ";
	 // LEL120906 Permitir ver entregadas y bloquear modificar
	
	   if (frm.hdFiltro.value != "")
	     frm.hdFiltro.value = frm.hdFiltro.value  +
	                          " and ((select count(*) from TRARegPNCEtapa " +
	                          " join GRLRegistroPNC on GRLRegistroPNC.iEjercicio = TRARegPNCEtapa.iEjercicioPNC " +
	                          " and GRLRegistroPNC.iConsecutivoPNC = TRARegPNCEtapa.iConsecutivoPNC " +
	                          " and GRLRegistroPNC.lResuelto = 0 " +
	                          " where TRARegPNCEtapa.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegPNCEtapa.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
	                          " and TRARegPNCEtapa.iCveEtapa in (select iCveEtapa " +
	                          " from  TRARegEtapasXModTram " +
	                          " where TRARegEtapasXModTram.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegEtapasXModTram.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
	                          " and TRARegEtapasXModTram.iOrden = (select max(iOrden) " +
	                          " from TRARegEtapasXModTram " +
	                          " where TRARegEtapasXModTram.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegEtapasXModTram.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud)) ) = 0) ";
	   else
	     frm.hdFiltro.value = " ((select count(*) from TRARegPNCEtapa " +
	                          " join GRLRegistroPNC on GRLRegistroPNC.iEjercicio = TRARegPNCEtapa.iEjercicioPNC " +
	                          " and GRLRegistroPNC.iConsecutivoPNC = TRARegPNCEtapa.iConsecutivoPNC " +
	                          " and GRLRegistroPNC.lResuelto = 0 " +
	                          " where TRARegPNCEtapa.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegPNCEtapa.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
	                          " and TRARegPNCEtapa.iCveEtapa in (select iCveEtapa " +
	                          " from  TRARegEtapasXModTram " +
	                          " where TRARegEtapasXModTram.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegEtapasXModTram.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
	                          " and TRARegEtapasXModTram.iOrden = (select max(iOrden) " +
	                          " from TRARegEtapasXModTram " +
	                          " where TRARegEtapasXModTram.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
	                          " and TRARegEtapasXModTram.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud)) ) = 0) ";

	   frm.hdOrden.value =  " TRARegEtapasXModTram.iOrden ";
	   frm.hdNumReg.value =  10000;
	   fEngSubmite("pgTRARegEtapasXModTram.jsp","Listado");
	 }
*/
 
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,cEtapas){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!\n");
   else if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   else if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   else if(cError=="validaUsrIngADVErr"){
	     fAlert("No es posible validar al usuario en Ingresos. Intente más tarde.");
	     cError="";
	     return;
	   }
   else if (cError != "")fAlert(cError);
   
   if(cId == "cIdCompFol" && cError==""){
	   fOficiosEnvio();
   }
   
   if(cId == "cIdCompFolPermiso" && cError==""){
	   fPermisosADV();
   }
   
   if(cId == "cIdVerifUsrIng" && cError==""){
	   if(aRes.length>0){
		   getDatosPerPago();
	   }else{
		   fAlert("\n-Usted no tiene permisos en Ingresos para generar la hoja de pago.");
	   }
   }  
   
   if(cId=="cIdPersonaPago" && cError==""){
	   if(aRes.length>0){
	       frm.hdiCveSolicitante.value=aRes[0][0];
	       frm.hdcRFC.value=aRes[0][1];
	       frm.hdcRPA.value=aRes[0][2];
	       frm.hdiCveTipoPersona.value=aRes[0][3];
	       frm.hdPersona_cNomRazonSocial.value=aRes[0][4];
	       frm.hdcApPaterno.value=aRes[0][5];
	       frm.hdcApMaterno.value=aRes[0][6];
	       frm.hdcCorreoE.value=aRes[0][7];
	       frm.hdcPseudonimo.value=aRes[0][8];
	       frm.hdiCveDomicilioSolicitante.value=aRes[0][9];
	       frm.hdiCveTipoDomicilio.value=aRes[0][10];
	       frm.hdcCalle.value=aRes[0][11];
	       frm.hdcNumExterior.value=aRes[0][12];
	       frm.hdcNumInterior.value=aRes[0][13];
	       frm.hdcColonia.value=aRes[0][14];
	       frm.hdcCodPostal.value=aRes[0][15];
	       frm.hdcTelefono.value=aRes[0][16];
	       frm.hdiCvePais.value=aRes[0][17];
	       frm.hdcDscPais.value=aRes[0][18];
	       frm.hdiCveEntidadFed.value=aRes[0][19];
	       frm.hdcDscEntidadFed.value=aRes[0][20];
	       frm.hdiCveMunicipio.value=aRes[0][21];
	       frm.hdcDscMunicipio.value=aRes[0][22];
	       frm.hdiCveLocalidad.value=aRes[0][23];
	       frm.hdcDscLocalidad.value=aRes[0][24];
	       frm.hdlPredeterminado.value=aRes[0][25];
	       frm.hdcDscTipoDomicilio.value=aRes[0][26];
	       frm.hdcDscDomicilio.value=aRes[0][27];
	       frm.hdiCveTramite.value=aRes[0][28];
	       frm.hdiCveModalidad.value=aRes[0][29];
	       
	      fAbreGeneraMovimientosX();
	   }
   }
   
   if(cId == "buscaDocumentosEtapa" && cError == ""){
		if(cEtapas!=""){
			fAlert("No es posible realizar la acción. "+cEtapas); //se ocupa cetapas para mostrar el mensaje si faltan documentos
		}else{
			validaUsrIng();
		}
	}

   if(cId == "Listado" && cError==""){

	 var validEtapasResolucion = 0;
	 var valorResolucion = 0;
	 
     aResTemp = fCopiaArregloBidim(aRes);
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();

     if(aResTemp.length == 0)
        lEncontre = false;
     else
        lEncontre = true;


     
     for(iPos=0; iPos < aResTemp.length; iPos++){    	 
    	    //7 y 8 Etapas de Conclusion en el Area y Entrega de Resolucion 
    	if( (aResTemp[iPos][6] == 7) || (aResTemp[iPos][6] == 8) ){     		
    	   validEtapasResolucion = 1;
    	   valorResolucion = aResTemp[iPos][17];
    	   //alert("Existen Etapas Finales")
        }
     }
     
     var tamAr = aRes.length;
          
     if(tamAr > 0 ){
    	 var ar=aRes[tamAr-1]; 
    	    iCveUltimaEtapa = ar[6];
    	    iCveUltimaOficina = ar[9];
    	    iCveUltimoDepto = ar[10];

    	    fSelReg(aRes[tamAr-1]);
    	    FRMListado.fSelReg(tamAr-1,0);
    	    
    	    //FRMListado.fSelRow(tamAr);
     }
     
     if(validEtapasResolucion == 1)
    	 fSelectSetIndexFromValue(frm.lResolucion, valorResolucion);
     else	 
         fSelectSetIndexFromValue(frm.lResolucion, -1);
   
     fCancelar();
     
     if(frm.iEjercicioFiltro.value != '' && frm.iNumSolicitudFiltro.value != ''){
       fTraerSolicitud();
     }
     
   }
   
   if(cId == "idTramite" && cError==""){
     fFillSelect(frm.iCveTramite,aRes,true,0,0,3);
   }
   if(cId == "idModalidad" && cError==""){
     fFillSelect(frm.iCveModalidad,aRes,true,0,0,1);
   }
   if(cId == "idEtapa" && cError==""){
     var aEtapas = new Array();
     
     //alert("-->>"+cEtapas);
     
     aEtapas = cEtapas.split(",");
     iCveEtapaFinal = aEtapas[0];
     iCveEtapaVerificacion = aEtapas[1];
     iCveEtapaEntregaVU = aEtapas[2];
     
//     
     
     iCveEtapaEntregaOfi = aEtapas[3];
     iCveEtapaRecibeResol = aEtapas[4];
     iCveEtapaEntregaResol = aEtapas[5];
     iCveEtapaDocRetorno = aEtapas[6];
     iCveEtapaConclusionTramite = aEtapas[7];
     iCveEtapaNotificado = aEtapas[8];
     iCveEtapaResEnviadaOfic = aEtapas[9];
     iCveEtapaTramiteCancelado = aEtapas[10];
     iCveTramiteCertificaDoc = aEtapas[11];
     iCveEtapaVisita = aEtapas[12];
     iCveEtapaResolucionVisita=aEtapas[13];  
     iCveEtapaEntregaPermiso = aEtapas[14];  
     
     
     //alert("certDoc "+iCveTramiteCertificaDoc);
     aResEtapa = fCopiaArregloBidim(aRes);
     //fFillSelect(frm.iCveEtapa,aRes,true,0,0,1);
     frm.iCveEtapa.value = iCveEtapaFinal;
     fCompara();
   }
   if(cId == "idFechaActual" && cError == ""){
     frm.iEjercicioFiltro.value = aRes[1][2];
     fTraeOficDepUsrLocal();
   }
   if(cId == "idPNC" && cError == ""){
     lCambiar = false;
     if(aRes.length > 0){
        if(aRes[0][1] > 0){
           lCambiar = true;
        }
     }else{
       lCambiar = true;
     }
   }
   if(cId == "idOficina" && cError==""){
     fFillSelect(frm.iCveOficina,aRes,true,0,0,1);
   }
   if(cId == "idDepartamento" && cError==""){
     fFillSelect(frm.iCveDepartamento,aRes,true,0,0,1);
   }
   if(cId == "idUsuario" && cError==""){
     fFillSelect(frm.iCveUsuario,aRes,true,0,0,2);
   }
   if(cId == "idSolicitud" && cError==""){
     if(lEncontre == true){
        lEncontre = false;
        if (aRes && aRes.length > 0){
          frm.iEjercicio.value = aRes[0][0];
          frm.iNumSolicitud.value = aRes[0][1];
          frm.iCveTramite.value = aRes[0][2];
          frm.cDscTramite.value = aRes[0][3];
          frm.iCveModalidad.value = aRes[0][4];
          frm.cDscModalidad.value = aRes[0][5];
          frm.iCveSolicitante.value = aRes[0][6];
          frm.cSolicitante.value = aRes[0][7];
          frm.cHomoclave.value = aRes[0][8];
          frm.dtEntrega.value = aRes[0][9];
          cNomTitulo = aRes[0][10];
          permiso = aRes[0][11];
          
          if(frm.dtEntrega.value != "")
             FRMPanel.fShow(",");
          else
             FRMPanel.fShow("Tra,");
          fTraerEtapa();
        } else {
          frm.iCveTramite.value = "";
          frm.cDscTramite.value = "";
          frm.iCveModalidad.value = "";
          frm.cDscModalidad.value = "";
          frm.iCveSolicitante.value = "";
          frm.cSolicitante.value = "";
          frm.cHomoclave.value = "";
          frm.dtEntrega.value = "";

        }
     }else{
          frm.iCveTramite.value = "";
          frm.cDscTramite.value = "";
          frm.iCveModalidad.value = "";
          frm.cDscModalidad.value = "";
          frm.iCveSolicitante.value = "";
          frm.cSolicitante.value = "";
          frm.cHomoclave.value = "";

          fAlert("La solicitud no existe o se encuentra en una etapa en la que no puede dar resolución");
     }
   }

   if(cError == "" && cId == "OficDepUsrLocal")
     aResOficDep = fCopiaArregloBidim(aRes);

 }
 
 function fBuscaDocumentos(){
	 if(permiso>0){
		 if (frm.iEjercicioFiltro.value>0 && frm.iNumSolicitudFiltro.value>0 && frm.iCveEtapa.value>0) {		
				frm.hdBoton.value = "buscaDocumentosEtapa";
				frm.iCveEtapa.value = iCveEtapaEntregaPermiso;
			  fEngSubmite("pgGestionOficios.jsp","buscaDocumentosEtapa");
			}	
		}
		else{
			fAlert('\nDebe buscar una solicitud valida. La solicitud debe estar en la etapa \"Entrega de Permiso\".');
		} 
}
 
 
	

 function fBuscarPNC(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = "";
   frm.hdSelect.value = "SELECT GRLREGISTROPNC.ICONSECUTIVOPNC " +
      "FROM TRAREGPNCETAPA " +
      "JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC " +
      " AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC " +
      "WHERE TRAREGPNCETAPA.IEJERCICIO =  " + frm.iEjercicioFiltro.value +
      " AND TRAREGPNCETAPA.INUMSOLICITUD = " + frm.iNumSolicitudFiltro.value +
      " AND TRAREGPNCETAPA.ICVETRAMITE = " + frm.iCveTramite.value +
      " AND TRAREGPNCETAPA.ICVEMODALIDAD = " + frm.iCveModalidad.value +
      " AND LRESUELTO = 0";
   fEngSubmite("pgConsulta.jsp","idPNC");
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){

	 
	 /*
   if(!fValidaUltimaOficDep()){
     fAlert("\n - No se puede realizar esta operación ya que el trámite se encuentra en una etapa la cuál pertenece a otra Oficina y Departamento.\n");
     return;
   }*/
   if(frm.iCveTramite.value != iCveTramiteCertificaDoc)
     if(parseInt(iCveUltimaEtapa,10) < parseInt(iCveEtapaVerificacion,10)){
       fAlert('\n - El trámite se encuentra en una etapa en la cuál no se permite efectuar ésta operación.');
       return;
     }
   if(iCveUltimaEtapa == iCveEtapaNotificado ||
   iCveUltimaEtapa == iCveEtapaEntregaVU || iCveUltimaEtapa == iCveEtapaEntregaOfi ||
   iCveUltimaEtapa == iCveEtapaRecibeResol || iCveUltimaEtapa == iCveEtapaEntregaResol ||
   iCveUltimaEtapa == iCveEtapaDocRetorno || iCveUltimaEtapa == iCveEtapaConclusionTramite ||
   iCveUltimaEtapa == iCveEtapaResEnviadaOfic || iCveUltimaEtapa == iCveEtapaTramiteCancelado ||
   iCveUltimaEtapa == iCveEtapaVisita || iCveUltimaEtapa ==  iCveEtapaResolucionVisita || iCveUltimaEtapa == iCveEtapaEntregaPermiso){  
     fAlert('\n - El trámite se encuentra en una etapa en la cuál no se permite efectuar ésta operación.');
     return;
   }

    lConsulta = false;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iEjercicioFiltro,iNumSolicitudFiltro,iCveTramite,cDscTramite,iCveModalidad,cDscModalidad,cHomoclave,iCveSolicitante,cSolicitante,iCveEtapaAnt,");
    fDisabled(false);
    //fDisabled(true,"iCveEtapa,lAnexosBOX,iCveEtapaAnt,cObsCIS,");
    fDisabled(true,"iCveEtapa,iCveEtapaAnt,cObsCIS,");
    
    frm.lResolucion.disabled = false;
    //FRMListado.fSetDisabled(true);
    fBuscarPNC();
    //fCargaEtapas();
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
   var cMensajeResolucion = "";
    if(lCambiar){

       if(fValidaTodo()==true){
    	   
         if(frm.iCveEtapa.value == iCveEtapaFinal){
        	
          if(frm.lResolucion.value != -1){           	  	 
           if(frm.lResolucion.value == 1)        	   
             cMensajeResolucion = " POSITIVA ";
           
           else
             cMensajeResolucion = " NEGATIVA ";
           
           if(confirm(cAlertMsgGen + "\n\n ¿Esta usted seguro de dar una resolución"+cMensajeResolucion+"al Trámite?")){
              if(fNavega()==true){
                 lConsulta = true;
                 fDisabled(true);
                 FRMListado.fSetDisabled(false);
              }
           }
          }
          
          if(frm.lResolucion.value == -1){        	  
        	  fAlert(" Seleccione una Opción de Resolución ");
          }
          
         }else{
           if(fNavega()==true){
             lConsulta = true;
             //FRMPanel.fSetTraStatus("AddOnly");
             fDisabled(true);
             FRMListado.fSetDisabled(false);
           }
         }
       }              
    }else
      fAlert("La solicitud tiene Productos No Conformes Pendientes \ny no puede cambiar de etapa");

 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){	 
//	// alert("guardarAA");
//    if(fValidaTodo()==true){
//       if(fNavega()==true){
//         lConsulta = true;
//         //FRMPanel.fSetTraStatus("AddOnly");
//         fDisabled(true);
//         FRMListado.fSetDisabled(false);
//       }
//    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    lConsulta = false;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicioFiltro,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    //fFillSelect(frm.iCveEtapa,aResTemp,false,0,6,7);
    fSelectSetIndexFromValue(frm.lResolucion,-1);
    
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Disabled");
    else {
      if (frm.cDscTramite.value != '' && frm.cDscModalidad.value != '')
        FRMPanel.fSetTraStatus("Disabled");
      else
        FRMPanel.fSetTraStatus("");
    }
    fDisabled(true);
    lConsulta = true;
    FRMListado.fSetDisabled(false);
    frm.iEjercicioFiltro.disabled = false;
    frm.iNumSolicitudFiltro.disabled = false;
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       lConsulta = false;
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
   // alert(aDato);
  // frm.iEjercicio.value = aDato[0];
  // frm.iNumSolicitud.value = aDato[1];
   frm.iCveEtapa.value = aDato[6];
   
   //alert("et->"+frm.iCveEtapa.value);
   
   //alert("fin-"+frm.iCveEtapa.value); 
    frm.lAnexo.value = aDato[14];
   fSelectSetIndexFromValue(frm.iCveEtapa, aDato[6]);

   //frm.lAnexosBOX.checked = ((aDato[14] == 1)?true:false);
   
   frm.cObsCIS.value = ((aDato[18]=="null")?"":aDato[18]);
   
   if(iCveUltimaEtapa == cvRes)
	   entResol = 1;
	   
   if(iCveUltimaEtapa == iCveEtapaFinal
		   || iCveUltimaEtapa == iCveEtapaEntregaPermiso ){
	   FRMPanel.fSetTraStatus("Disabled");
}
   else
	   FRMPanel.fSetTraStatus("AddOnly");  //l aultima etapa debe ser la de verificacion de reqs y no debe tener productos no conformes.
   
   if(permiso>0)
		  FRMPanel.fSetTraStatus("Disabled");
 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    cMsg = fValElements();
    if(frm.cObsCIS.value.length>250)cMsg+="\n - El Campo Observación no puede contener más de 250 caracteres."
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
 function fTraerEtapa(){
   frm.hdFiltro.value = " iCveTramite = " + frm.iCveTramite.value +
                            " and iCveModalidad = " + frm.iCveModalidad.value ;
   frm.hdNumReg.value =  "";
   frm.hdOrden.value = "iOrden";
   fEngSubmite("pg111020020A2.jsp","idEtapa");
 }

 function fTraerSolicitud(){
   if (frm.iEjercicioFiltro.value != 0 && frm.iEjercicioFiltro.value != '' &&
       frm.iNumSolicitudFiltro.value != 0 && frm.iNumSolicitudFiltro.value != '')
     frm.hdFiltro.value = " traregsolicitud.iEjercicio = " + frm.iEjercicioFiltro.value +
                          " and traregsolicitud.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
   else
     frm.hdFiltro.value = "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  "";
   fEngSubmite("pg111020020ADV.jsp","idSolicitud");
 }

 function fCompara(){
   if (frm.iCveEtapa.value == iCveEtapaFinal || frm.iCveEtapa.value == iCveEtapaEntregaPermiso){
	   fSelectSetIndexFromValue(frm.lResolucion,-1);
     //frm.lResolucionBOX.checked = false;
     //frm.lResolucionBOX.disabled = false;
	   //frm.lResolucion.disabled = false;
     //frm.lResolucion.value = 0;
   } else {
	   fSelectSetIndexFromValue(frm.lResolucion,-1);
	   frm.lResolucion.disabled = true;
     //frm.lResolucionBOX.checked = false;
     //frm.lResolucionBOX.disabled = true;
     //frm.lResolucion.value = 0;
   }
 }

 function fSetSolicitud(iEjercicio,iNumSolicitud,iCveOficinaUsr,iCveDeptoUsr){
   frm.iEjercicioFiltro.value = iEjercicio;
   frm.iNumSolicitudFiltro.value = iNumSolicitud;

   fNavega();
 }

 function fCargaEtapas(){
   var aEtapaDesplegar = new Array();

   var lEtapaEncontrada = false;
   var j=0;
   for(var i=0; i<aResEtapa.length; i++){
     if(lEtapaEncontrada){
       if(aResEtapa[i][2] == 1){
         if(aResEtapa[i][3] == 1){
           aEtapaDesplegar[aEtapaDesplegar.length] = fCopiaArreglo(aResEtapa[i]);
           break;
         }else{
           aEtapaDesplegar[aEtapaDesplegar.length] = fCopiaArreglo(aResEtapa[i]);
         }
         j++;
         if(aResEtapa[i][0] == iCveEtapaFinal)
           break;
       }
     }
     if(iCveUltimaEtapa == aResEtapa[i][0]){
       if(iCveUltimaEtapa == iCveEtapaFinal){
         aEtapaDesplegar[aEtapaDesplegar.length] = fCopiaArreglo(aResEtapa[i-1]);
         break;
       }else if(iCveUltimaEtapa > iCveEtapaVerificacion)
         aEtapaDesplegar[aEtapaDesplegar.length] = fCopiaArreglo(aResEtapa[i-1]);

       lEtapaEncontrada = true;
       j++;
     }
   }
   fFillSelect(frm.iCveEtapa,aEtapaDesplegar,true,frm.iCveEtapa.value,0,1);
 }

 function fTraeOficDepUsrLocal(){
   frm.hdLlave.value = "";
   frm.hdSelect.value = "SELECT " +
				"ICVEOFICINA,ICVEDEPARTAMENTO " +
				"FROM GRLUSUARIOXOFIC " +
				"where ICVEUSUARIO = " + frm.iCveUsuario.value;

   fEngSubmite("pgConsulta.jsp","OficDepUsrLocal");
 }

 function fValidaUltimaOficDep(){
   var lOficDepValido = false;
   for(var i=0; i < aResOficDep.length; i++)
    if(parseInt(aResOficDep[i][0],10) == parseInt(iCveUltimaOficina,10) &&
    parseInt(aResOficDep[i][1],10) == parseInt(iCveUltimoDepto,10)){
      lOficDepValido = true;
      break;
    }
   return lOficDepValido;
 }

 function change(){	

 }	
 
function fOficiosEnvio(){
	
	if(frm.iEjercicio.value!="" && frm.iNumSolicitud.value!="" && permiso>0){

//	      cClavesModulo="3,3,3,3,";
//		  cNumerosRep="38,39,40,41,";
//	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
//	 	  cFiltrosRep += cFiltrosRep + cFiltrosRep + cFiltrosRep; 
//	 	  fReportes();
		  cClavesModulo="3,3,3,3,";
		  cNumerosRep="49,50,51,52,";
	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
	 	  cFiltrosRep += cFiltrosRep + cFiltrosRep + cFiltrosRep; 
	 	  fReportes();

	}else{
		fAlert('\nDebe buscar una solicitud existente.');
	} 
}


function abreTitulo(){
	if(cNomTitulo=="notFound")
		fAlert("\No es posible mostrar el título de concesión. Debe buscar una solicitud existente.");
	else{
	    //window.open("http://localhost:7001/ServiciosOM/wwwrooting/titulos/tituloGeneral.pdf",'Título de concesión','resizable=true,scrollbars=true');
	    window.open(cRutaProgMM+cRutaTitulos+cNomTitulo,"_blank","scrollbars=yes, resizable=yes, top=250, left=250, width=700, height=500");
	}
}

function fCompruebaFolio(valor){
	
	if(permiso>0){
		
		 frm.hdBoton.value = "compFolEnv";
		 frm.hdFiltro.value =  "";
		 frm.hdOrden.value =  "";
		 frm.hdNumReg.value =  1000;
	
		if(valor != null && valor==1){
			
			if(frm.iEjercicio.value!=""&&frm.iNumSolicitud.value!=""){
				 fEngSubmite("pg111020020ADV.jsp","cIdCompFol");
			}
			else{
				fAlert("\n Favor de realizar la busqueda de la solicitud nuevamente.");
			}
			
		}else if(valor != null && valor==2){
			
			if(frm.iEjercicio.value!=""&&frm.iNumSolicitud.value!=""){
				 fEngSubmite("pg111020020ADV.jsp","cIdCompFolPermiso");
			}
			else{
				fAlert("\n Favor de realizar la busqueda de la solicitud nuevamente.");
			}
		}
	}
	else
		fAlert("\n No es posible generar el reporte. La solicitud debe estar en la etapa \"Entrega de Permiso\".");
}

function fEntResol(){
	
	if(frm.iEjercicio.value!="" && frm.iNumSolicitud.value!="" && (permiso>0 || entResol > 0)){
		  cClavesModulo="3,";
		  //cNumerosRep="27,";
		  cNumerosRep="37,";
	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep; 
	 	  fReportes();
	}else{
		fAlert('\nDebe buscar una solicitud valida. La solicitud debe estar en la etapa \"Entrega de Permiso" o "Entrega de Resolución"\".');
	} 

	
}

function fPermisosADV(){
	
	if(frm.iEjercicio.value!="" && frm.iNumSolicitud.value!="" && permiso>0){
//		  cClavesModulo="3,3,3,3,";
//		  cNumerosRep="28,29,30,31,";
//	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
//	 	  cFiltrosRep+= cFiltrosRep+cFiltrosRep+cFiltrosRep;
		
		  cClavesModulo="3,";
		  cNumerosRep="55,";
	 	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
	 	  fReportes();
	}else{
		fAlert('\nDebe buscar una solicitud valida. La solicitud debe estar en la etapa \"Entrega de Permiso\".');
	} 
	
}

function fGetPermiteEditarPagos(){
	  return true;
}

function fGetDatosSolicitud(objWindow){
	  if(objWindow)
	    if(objWindow.fSetDatosSolicitud)
	      objWindow.fSetDatosSolicitud(frm.cSolicitante.value, "DomicilioSol",
	                                   "razonSocialRepL", "DomicilioREpl",
	                                   frm.iEjercicio.value, frm.iNumSolicitud.value, frm.cDscTramite.value,
	                                   "RFC_SOL");
}


function fGetDatosPersona(objRef){
	  if(objRef)
	    if(objRef.fValoresPersona)
	      objRef.fValoresPersona(
	    		  frm.hdiCveSolicitante.value,
		             frm.hdcRFC.value,
		             frm.hdcRPA.value,
		             frm.hdiCveTipoPersona.value,
		             frm.hdPersona_cNomRazonSocial.value,
		             frm.hdcApPaterno.value,
		             frm.hdcApMaterno.value,
		             frm.hdcCorreoE.value,
		             frm.hdcPseudonimo.value,
		             frm.hdiCveDomicilioSolicitante.value,
		             frm.hdiCveTipoDomicilio.value,
		             frm.hdcCalle.value,
		             frm.hdcNumExterior.value,
		             frm.hdcNumInterior.value,
		             frm.hdcColonia.value,
		             frm.hdcCodPostal.value,
		             frm.hdcTelefono.value,
		             frm.hdiCvePais.value,
		             frm.hdcDscPais.value,
		             frm.hdiCveEntidadFed.value,
		             frm.hdcDscEntidadFed.value,
		             frm.hdiCveMunicipio.value,
		             frm.hdcDscMunicipio.value,
		             frm.hdiCveLocalidad.value,
		             frm.hdcDscLocalidad.value,
		             frm.hdlPredeterminado.value,
		             frm.hdcDscTipoDomicilio.value,
		             frm.hdcDscDomicilio.value,
		             parseInt(frm.hdiCveTramite.value,10),
		             parseInt(frm.hdiCveModalidad.value,10),
	             false, false, false);
	    else fAlert("No existe funcion");
	  else fAlert("no existe ventana destino o fuente de datos");
	}


function validaUsrIng(){	
	if(frm.iEjercicio.value!="" && frm.iNumSolicitud.value!="" ){
		frm.hdLlave.value="";
		fEngSubmite("pgVerifUsrIngresosADV.jsp", "cIdVerifUsrIng");
	}
}

function getDatosPerPago(){
	
	if(frm.iEjercicio.value!="" && frm.iNumSolicitud.value!="" && permiso>0){
		frm.hdSelect.value="PERSONA_PAGO";
		frm.hdLlave.value="";
		fEngSubmite("pgConsulta.jsp", "cIdPersonaPago");
	}
	else{
		fAlert('\nDebe buscar una solicitud valida. La solicitud debe estar en la etapa \"Entrega de Permiso\".');
	} 
}

function fGetIEjercicio(){
    return frm.iEjercicio.value;
}

function fGetINumSolicitud(){
    return frm.iNumSolicitud.value;
}


function getEjercicio(){
    return frm.iEjercicio.value;
}

function getNumSol(){
    return frm.iNumSolicitud.value;
}

function verDocsTramite(){
	   if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != '' &&
		       frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != ''){
			   if(fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value))
				   fAbreSubWindow(false,"pgVerDocsTramite","no","yes","yes","yes","800","600",50,50);
			   else
				   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	   }else{
		   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	   }
}

function subirOficiosTramite(){
	 if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != '' &&
		       frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != ''){
			   if(fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)&&permiso){
				  if(permiso>0) 
				   	fAbreSubWindowPermisos("pgSubirOficiosADV","800","600",false);
				  else
			          fAlert("La debe estar en la etapa \"ENTREGA DE PERMISO\".");
			   }else
				   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	   }else{
		   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	   }
}

function fDocsFinales(){
	
	if(fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)&&permiso){
		  if(permiso>0) 
			  fAbreSubWindowPermisos("pgDatosPermiso","800","600",false);
		  else
	          fAlert("La debe estar en la etapa \"ENTREGA DE PERMISO\".");
	} else{
		   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos.");
	   }
}
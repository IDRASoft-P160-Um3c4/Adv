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

 var resolcionArrayC = new Array();
 resolcionArrayC[0] = ['0','Negativo'];
 resolcionArrayC[1] = ['1','Positivo'];

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020020.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);

   InicioTabla("ETablaInfo",0,"","","center","","1");
     ITRTD("ECampoC",6,"100%","center","top");
       Liga("Buscar Solicitud","fAbreBuscaSolicitud();","Búsqueda de la Solicitud");

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
             TextoSimple("Solicitante:");
             ITD("ECampo",5,"","","LEFT","LEFT");
             Text(false,"cSolicitante","",70,70,"Solicitante del Trámite","","","",false,false);
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
           ITRTD("ETablaST",6,"","","center");
             TextoSimple("Cambio de Etapa");
           FTDTR();
           ITR();
              TDEtiSelect(true,"EEtiqueta",0,"Etapa Seleccionada:","iCveEtapa","fCompara();");
              Hidden("iCveEtapaAnt","");
              TDEtiCheckBox("EEtiqueta",0,"Anexos:","lAnexosBOX","1",true,"Anexos");
              Hidden("lAnexo","");
              
               TDEtiSelect(true, "EEtiqueta", 0, "Resolución:", "lResolucion", "change();");
              
              //TDEtiCheckBox("EEtiqueta",0,"Resolución Positiva:","lResolucionBOX","1",true,"Resolución Positiva");
              //Hidden("lResolucion","");
            FTDTR();
            ITR();
            	TDEtiAreaTexto(false,"EEtiqueta",1,"Observación CIS",75,4,"cObsCIS","","Observaciones que se enviarán al CIS","","fMayus(this);",'onkeydown="fMxTx(this,250);"',true,true,true,"ECampo",5);
            	FTD();
            FTR();
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
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   
   fFillSelect(frm.lResolucion,resolcionArrayC,true,0,0,1);
   
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Etapa,Descripción,Fecha,Usuario,Oficina,Depto,");
   FRMListado.fSetCampos("6,7,13,12,15,16,");
   FRMListado.fSetAlinea("right,left,center,left,left,left,")


   fDisabled(false);
   frm.hdBoton.value="Primero";

   fDisabled(true,"iEjercicioFiltro,iNumSolicitudFiltro,");
   fTraeFechaActual();
 }
 // LLAMADO al JSP específico para la navegación de la página
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
/* // LEL120906 Permitir ver entregadas y bloquear modificar
*/
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
   else if (cError != "")fAlert(cError);

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
    	   validEtapasResolucion = 1
    	   valorResolucion = aResTemp[iPos][17];
    	   //alert("Existen Etapas Finales")
        }	         	         	     	     	     	    		 
     }
     
     if(validEtapasResolucion == 1)
    	 fSelectSetIndexFromValue(frm.lResolucion, valorResolucion);
     
     else	 
         fSelectSetIndexFromValue(frm.lResolucion, -1);
     
     if(aResTemp && aResTemp.length > 0){
       iCveUltimaEtapa = aResTemp[aResTemp.length-1][6];
       iCveUltimaOficina = aResTemp[aResTemp.length-1][9];
       iCveUltimoDepto = aResTemp[aResTemp.length-1][10];
       FRMListado.fSelReg(aResTemp.length-1);
     }

     if(frm.iEjercicioFiltro.value != '' && frm.iNumSolicitudFiltro.value != '')
       fTraerSolicitud();

   }
   
   if(cId == "Listado" && cError!=""){	 
       fCancelar();
    }

   if(cId == "idTramite" && cError==""){
     fFillSelect(frm.iCveTramite,aRes,true,0,0,3);
   }
   if(cId == "idModalidad" && cError==""){
     fFillSelect(frm.iCveModalidad,aRes,true,0,0,1);
   }
   if(cId == "idEtapa" && cError==""){
     var aEtapas = new Array();
     aEtapas = cEtapas.split(",");
     iCveEtapaFinal = aEtapas[0];
     iCveEtapaVerificacion = aEtapas[1];
     iCveEtapaEntregaVU = aEtapas[2];
     iCveEtapaEntregaOfi = aEtapas[3];
     iCveEtapaRecibeResol = aEtapas[4];
     iCveEtapaEntregaResol = aEtapas[5];
     iCveEtapaDocRetorno = aEtapas[6];
     iCveEtapaConclusionTramite = aEtapas[7];
     iCveEtapaNotificado = aEtapas[8];
     iCveEtapaResEnviadaOfic = aEtapas[9];
     iCveEtapaTramiteCancelado = aEtapas[10];
     iCveTramiteCertificaDoc = aEtapas[11];
     aResEtapa = fCopiaArregloBidim(aRes);
     fFillSelect(frm.iCveEtapa,aRes,true,0,0,1);
   }
   if(cId == "idFechaActual" && cError == ""){
     frm.iEjercicioFiltro.value = aRes[1][2];
     fTraeOficDepUsrLocal();
   }
   if(cId == "idPNC" && cError == ""){
     lCambiar = false;
     if(aRes.length > 0){
        if(aRes[0][3]){
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
          frm.iCveTramite.value = aRes[0][2];
          frm.cDscTramite.value = aRes[0][3];
          frm.iCveModalidad.value = aRes[0][4];
          frm.cDscModalidad.value = aRes[0][5];
          frm.iCveSolicitante.value = aRes[0][6];
          frm.cSolicitante.value = aRes[0][7];
          frm.cHomoclave.value = aRes[0][8];
          frm.dtEntrega.value = aRes[0][9];
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

          fAlert("La solicitud no existe o no pertenece a este departamento");
     }
   }

   if(cError == "" && cId == "OficDepUsrLocal")
     aResOficDep = fCopiaArregloBidim(aRes);

 }

 function fBuscarPNC(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = "IEJERCICIO,INUMSOLICITUD,ICVETRAMITE,ICVEMODALIDAD";
   select = "SELECT TRAREGPNCETAPA.IEJERCICIOPNC, TRAREGPNCETAPA.ICONSECUTIVOPNC, " +
      "TRAREGPNCETAPA.IORDEN, GRLREGISTROPNC.DTRESOLUCION " +
      "FROM TRAREGPNCETAPA " +
      "JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC " +
      " AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC " +
      "WHERE TRAREGPNCETAPA.IEJERCICIO =  " + frm.iEjercicioFiltro.value +
      " AND TRAREGPNCETAPA.INUMSOLICITUD = " + frm.iNumSolicitudFiltro.value +
      " AND TRAREGPNCETAPA.ICVETRAMITE = " + frm.iCveTramite.value +
      " AND TRAREGPNCETAPA.ICVEMODALIDAD = " + frm.iCveModalidad.value +
      " ORDER BY TRAREGPNCETAPA.IORDEN DESC ";
   frm.hdSelect.value = select;
   fEngSubmite("pgConsulta.jsp","idPNC");
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){

   if(!fValidaUltimaOficDep()){
     fAlert("\n - No se puede realizar esta operación ya que el trámite se encuentra en una etapa la cuál pertenece a otra Oficina y Departamento.\n");
     return;
   }
   if(frm.iCveTramite.value != iCveTramiteCertificaDoc)
     if(parseInt(iCveUltimaEtapa,10) < parseInt(iCveEtapaVerificacion,10)){
       fAlert('\n - El trámite se encuentra en una etapa en la cuál no se permite efectuar ésta operación.');
       return;
     }
   if(iCveUltimaEtapa == iCveEtapaNotificado ||
   iCveUltimaEtapa == iCveEtapaEntregaVU || iCveUltimaEtapa == iCveEtapaEntregaOfi ||
   iCveUltimaEtapa == iCveEtapaRecibeResol || iCveUltimaEtapa == iCveEtapaEntregaResol ||
   iCveUltimaEtapa == iCveEtapaDocRetorno || iCveUltimaEtapa == iCveEtapaConclusionTramite ||
   iCveUltimaEtapa == iCveEtapaResEnviadaOfic || iCveUltimaEtapa == iCveEtapaTramiteCancelado){
     fAlert('\n - El trámite se encuentra en una etapa en la cuál no se permite efectuar ésta operación.');
     return;
   }

    lConsulta = false;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iEjercicioFiltro,iNumSolicitudFiltro,iCveTramite,cDscTramite,iCveModalidad,cDscModalidad,cHomoclave,iCveSolicitante,cSolicitante,iCveEtapaAnt,");
    fDisabled(false);
    fDisabled(true,"iCveEtapa,lAnexosBOX,iCveEtapaAnt,cObsCIS,");
    FRMListado.fSetDisabled(true);
    fBuscarPNC();
    fCargaEtapas();
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
   var cMensajeResolucion = "";
    if(lCambiar){
       if (frm.lAnexosBOX.checked == true)
          frm.lAnexo.value = 1;
       else
          frm.lAnexo.value = 0;
       
       /*if (frm.lResolucionBOX.checked == true)
          frm.lResolucion.value = 1;
       else
          frm.lResolucion.value = 0;*/

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
                 FRMPanel.fSetTraStatus("AddOnly");
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
             FRMPanel.fSetTraStatus("AddOnly");
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
    if(fValidaTodo()==true){
       if(fNavega()==true){
         lConsulta = true;
         FRMPanel.fSetTraStatus("AddOnly");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
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
    fFillSelect(frm.iCveEtapa,aResTemp,false,0,6,7);
    fSelectSetIndexFromValue(frm.lResolucion,-1);
    
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("AddOnly");
    else {
      if (frm.cDscTramite.value != '' && frm.cDscModalidad.value != '')
        FRMPanel.fSetTraStatus("AddOnly");
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
   frm.iEjercicio.value = aDato[0];
   frm.iNumSolicitud.value = aDato[1];
   frm.iCveEtapa.value = aDato[6];
   frm.lAnexo.value = aDato[14];
   fSelectSetIndexFromValue(frm.iCveEtapa, aDato[6]);

   frm.lAnexosBOX.checked = ((aDato[14] == 1)?true:false);
   
   frm.cObsCIS.value = ((aDato[18]=="null")?"":aDato[18]);
 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    cMsg = fValElements();
    if(frm.cObsCIS.value.length>250)cMsg+="\n - El Campo Observación CIS no puede contener más de 250 caracteres."
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
     frm.hdFiltro.value = " iEjercicio = " + frm.iEjercicioFiltro.value +
                          " and iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
   else
     frm.hdFiltro.value = "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  "";
   fEngSubmite("pg111020020A1.jsp","idSolicitud");
 }

 function fCompara(){
   if (frm.iCveEtapa.value == iCveEtapaFinal){
	   fSelectSetIndexFromValue(frm.lResolucion,-1);
     //frm.lResolucionBOX.checked = false;
     //frm.lResolucionBOX.disabled = false;
	   frm.lResolucion.disabled = false;
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
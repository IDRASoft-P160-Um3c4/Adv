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
 var ultimoReg=-1;
 
 var INICIO_OBRA = 7;
 var INICIO_FISICO = 8;
 var CONCLUSION_FISICA = 9; 
 var CONCLUSION_OBRA = 10;
 var FINALIZADO=0;
 
var ofA= "AVISO DE INICIO DE OBRA";
var ofB= "INICIO FÍSICO";
var ofC= "CONCLUSIÓN FÍSICA DE OBRA";
var ofD= "VERIFICACIÓN DE CONCLUSIÓN DE OBRA";

 
 var nombreOficio="";
 
 var puedeSubir=false;
 var subioArchivo=false;
	  
var	inicioObra=false;
var	finObra=false;

var dtInicioObra="";
var dtFinObra="";
var valorOficio=-1;
var cPermisoPag;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg117010030.js";
   //if(top.fGetTituloPagina){
   //  cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   //}
   cTitulo =  "REGISTRO DE SEGUIMIENTO DE OBRA";
   cPermisoPag = fGetPermisos(cPaginaWebJS);
   fSetWindowTitle();
 }
 
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);

   InicioTabla("ETablaInfo",0,"","","center","","1");
     ITRTD("ECampoC",6,"100%","center","top");
       //Liga("Buscar Solicitud","fAbreBuscaSolicitud();","Búsqueda de la Solicitud");

     ITRTD("ETablaST",6,"100%","","center");
       TextoSimple("Selección de la Solicitud");
     FTDTR();
       TDEtiCampo(true,"EEtiqueta",0,"Ejercicio:","iEjercicioFiltro","",4,4,"Ejercicio","fMayus(this);");
       TDEtiCampo(true,"EEtiqueta",0,"No. Solicitud:","iNumSolicitudFiltro","",8,8,"Núm. Solicitud","fMayus(this);");
       ITD("EEcampo",0,"","","LEFT","LEFT");
       BtnImg("Buscar","lupa","fBuscaSol();");
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
             Text(false,"cSolicitante","",70,70,"Promovente del Trámite","","","",false,false);
             Hidden("iCveSolicitante","");
           FITR();
         FinTabla();
       FinTabla();
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple("Histórico del Seguimiento");
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",6,"","","center");
             TextoSimple("Registro de Seguimiento de Obra");
           FTDTR();
	       ITR();
	           ITD("EEtiqueta",0,"","","LEFT","LEFT");
	           TextoSimple("Días Transcurridos desde Inicio Físico de Obra:");
	           ITD("ECampo",10,"","","LEFT","LEFT");
	           Text(false,"tDiasTrans","",20,70," Siguiente registro de seguimiento de obra.","","","",false,false);
           FTDTR();
           
           ITR();
	           ITD("EEtiqueta",0,"","","LEFT","LEFT");
	           TextoSimple("Siguiente Registro:");
	           ITD("ECampo",10,"","","LEFT","LEFT");
	           Text(false,"tSigReg","",60,70," Siguiente registro de seguimiento de obra.","","","",false,false);
	           Hidden("hdValSigReg");
	           ITR();
	           Liga("Subir Oficio de Seguimiento","subirDoc();","Registrar Notificación");
	           //fInput("button", "btnArchRes", '"Subir Archivo" onclick="window.open(\'http://localhost\', \'\', \'width=200,height=100\')"');
	           
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
	 Hidden("iCveTram");
	 Hidden("iCveMod");

     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("hdBotonAux");
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
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Evento,Fecha Registro,Oficina de Origen,");

   FRMListado.fSetCampos("0,1,2,");
   FRMListado.fSetAlinea("center,center,center,");

   fDisabled(false);
   
   fDisabled(true,"iEjercicioFiltro,iNumSolicitudFiltro,");
   //fTraeFechaActual();
   var dateToday = new Date();
   frm.iEjercicioFiltro.value= dateToday.getFullYear();
   frm.iNumSolicitudFiltro.focus();
   fCargaOficDeptoUsr(false);
 }
 
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
	 
   if (frm.iEjercicio.value != "" &&  frm.iNumSolicitud.value != ''){
	   
       frm.hdFiltro.value = " SOL.IEJERCICIO =" + frm.iEjercicio.value + 
      						" AND SOL.INUMSOLICITUD ="+ frm.iNumSolicitud.value;

	   frm.hdOrden.value =  " SEG.ICVETIPO ";
	   frm.hdNumReg.value =  10000;
	   frm.hdBotonAux.value = "Seguimiento";
	   fEngSubmite("pgTRARegSegObraXSol.jsp","Listado");
   }else{
	   fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos");
   }
   
 }
 
 function fBuscaSol(){
	 
	 if (frm.iEjercicioFiltro.value != 0 && frm.iEjercicioFiltro.value != '' &&
		       frm.iNumSolicitudFiltro.value != 0 && frm.iNumSolicitudFiltro.value != ''){
		 
		     frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = " + frm.iEjercicioFiltro.value +
		                          " and TRAREGSOLICITUD.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;

	 	   frm.hdBotonAux.value =  "Solicitud";
		   fEngSubmite("pgTRARegSegObraXSol.jsp","idSolicitud");
		   
	 }else{
		 fAlert("\n Debe proporcionar un Ejercicio y Número de Solicitud válidos");
	 }
	 
 }
 
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,cEtapas){

   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!\n");
   else if (cError != "")
	 fAlert(cError);

   if(cId=="idSolicitud"){
	   if(aRes.length > 0){ 
		   frm.cHomoclave.value=aRes[0][0];
		   frm.cDscTramite.value=aRes[0][1];
		   frm.cDscModalidad.value=aRes[0][2];
		   frm.cSolicitante.value=aRes[0][3];
		   frm.iCveTram.value=aRes[0][4];
		   frm.iCveMod.value=aRes[0][5];
		   
		   frm.iEjercicio.value= frm.iEjercicioFiltro.value;
		   frm.iNumSolicitud.value= frm.iNumSolicitudFiltro.value;
		   fNavega();
	   }else{
		   puedeSubir=false;
		   blankFields();
		   fAlert("\n La solicitud no existe o se encuentra en una etapa en la cual no es posible relizar ésta operación.");
		   }

   }
   
   if(cId == "Listado" && cError==""){
	   
	     aResTemp = fCopiaArregloBidim(aRes);
	     frm.hdRowPag.value = iRowPag;
	     FRMListado.fSetLlave(cLlave);

	     
	     if(aRes.length > 0){
	    	 
    	    frm.iEjercicioFiltro.disabled = true;
    	    frm.iNumSolicitudFiltro.disabled = true;
	    	 
		     var tam = aRes.length;
		     ultimoReg = aRes[tam-1][0];
	     
		     for(var iPos=0; iPos < aRes.length; iPos++){    	 
		 
		    	if(aRes[iPos][0] == 1){
		    		aRes[iPos][0] = "INICIO ESTIMADO DE OBRA";
		    	}
		    	else if(aRes[iPos][0] == 2){
		    		aRes[iPos][0]="INICIO FÍSICO DE OBRA";
		    		inicioObra=true;
		    		dtInicioObra=aRes[iPos][1];
		    	}else if(aRes[iPos][0] == 3){
		    		aRes[iPos][0]="CONCLUSIÓN FÍSICA DE OBRA";
		    		finObra=true;
		    		dtFinObra=aRes[iPos][1];
		    	}else if(aRes[iPos][0] == 4){
		    		aRes[iPos][0]="VERIFICACIÓN DE CONCLUSIÓN DE OBRA POR C.S.C.T.";
		    	}
		     }
		     calcularDias();
	     }
	     
	     FRMListado.fSetListado(aRes);
	     FRMListado.fShow();
	     
	     ultimoReg = parseInt(ultimoReg);

	     
	     switch(ultimoReg){
	     
		     case -1:
		    	   frm.tSigReg.value = "INICIO ESTIMADO DE OBRA";
		    	   frm.hdValSigReg.value=1;
		    	   setValorOficio(INICIO_OBRA);
		    	   setNombreOficio(ofA);
		    	   FRMPanel.fSetTraStatus("UpdateBegin");
		    	 break;
		     case 1:
		    	   frm.tSigReg.value = "INICIO FÍSICO DE OBRA";
		    	   frm.hdValSigReg.value=2;
		    	   setValorOficio(INICIO_FISICO);
		    	   setNombreOficio(ofB);
		    	   FRMPanel.fSetTraStatus("UpdateBegin");
		    	 break;
		     case 2:
		    	   frm.tSigReg.value = "CONCLUSIÓN FÍSICA DE OBRA";
		    	   frm.hdValSigReg.value=3;
		    	   setValorOficio(CONCLUSION_FISICA);
		    	   setNombreOficio(ofC);
		    	   FRMPanel.fSetTraStatus("UpdateBegin");
		    	 break;
		     case 3:
		    	   frm.tSigReg.value = "VERIFICACIÓN DE CONCLUSIÓN DE OBRA POR C.S.C.T.";
		    	   frm.hdValSigReg.value=4;
		    	   setValorOficio(CONCLUSION_OBRA);
		    	   setNombreOficio(ofD);
		    	   FRMPanel.fSetTraStatus("UpdateBegin");
		    	 break;
		     case 4:
		    	 fAlert("\n Ha finalizado el seguimiento de obra para ésta solicitud.");
		    	 frm.tSigReg.value = "NO APLICA - SEGUIMIENTO FINALIZADO";
		    	 frm.hdValSigReg.value=0;
		    	 setValorOficio(FINALIZADO);
		    	 FRMPanel.fSetTraStatus("Disabled");
	    	     frm.iEjercicioFiltro.disabled = false;
	    	     frm.iNumSolicitudFiltro.disabled = false;
		    	 break;
	     }
	}
}
 

 function fNuevo(){	 
	 
 }
 
 
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){

	 if(confirm("¿Desea guardar el registro de " + frm.tSigReg.value + " para ésta solicitud?")){
		 
		 if(frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "" && frm.hdValSigReg.value > 0){
			 
			 if(subioArchivo==true){
				 subioArchivo=false;
				 frm.hdBoton.value = "Guardar";
				 fEngSubmite("pgTRARegSegObraXSol.jsp","Listado");
			 }else{
				 fAlert("\nDebe subir el archivo asociado a esta etapa de seguimiento."); 
			 }
			 
			 
		 }else{
			 fAlert("\nNo se han cargado los datos. Realice nuevamente la búsqueda");
		 }		 
	 }
	 
}

 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    
    FRMPanel.fSetTraStatus("Disabled");
    
    frm.iEjercicioFiltro.disabled = false;
    frm.iNumSolicitudFiltro.disabled = false;
    blankFields();
    FRMListado.fSetListado(new Array());
    FRMListado.fShow();
    
 }
 
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
	 /*
   frm.iEjercicio.value = aDato[0];
   frm.iNumSolicitud.value = aDato[1];
   frm.iCveEtapa.value = aDato[6];
   
   if(iCveUltimaEtapa == iCveEtapaFinal)
	   FRMPanel.fSetTraStatus("Disabled");
   else
	   FRMPanel.fSetTraStatus("AddOnly");
	   
   frm.lAnexo.value = aDato[14];
   fSelectSetIndexFromValue(frm.iCveEtapa, aDato[6]);

   frm.lAnexosBOX.checked = ((aDato[14] == 1)?true:false);
   
   frm.cObsCIS.value = ((aDato[18]=="null")?"":aDato[18]);
   */
 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
 /*
	 cMsg = fValElements();
    if(frm.cObsCIS.value.length>250)cMsg+="\n - El Campo Observación CIS no puede contener más de 250 caracteres."
    if(cMsg != ""){
       fAlert(cMsg);
       return false;
    }
    return true;*/
 }
 
 function fImprimir(){
    self.focus();
    window.print();
 }

 function blankFields(){
	   frm.cHomoclave.value="";
	   frm.cDscTramite.value="";
	   frm.cDscModalidad.value="";
	   frm.cSolicitante.value="";
	   frm.tSigReg.value="";
	   frm.tDiasTrans.value="";
}

 
 function calcularDias(){
	 
	 if(finObra==true){//si ya se registro el fin de obra
		 var arrIni = dtInicioObra.split("/");//arreglo con los elementos de la fecha de inicio
		 var arrFin = dtFinObra.split("/");//arreglo con los elementos de la fecha de fin
		 
		 var dateIniObra = new Date (arrIni[2]+'/'+arrIni[1]+'/'+arrIni[0]); //milisegundos de inicio
		 var dateFinObra = new Date (arrFin[2]+'/'+arrFin[1]+'/'+arrFin[0]);//milisegundos de fin
	 
		 frm.tDiasTrans.value=parseInt((dateFinObra -dateIniObra)/86400000, 10);
	 }else if(inicioObra==true && finObra==false){

		 var arrIni = dtInicioObra.split("/");//arreglo con los elementos de la fecha de inicio
		 var arrAct = fGetTodayDate().split("/"); //arreglo con los elementos de la fecha actual
		 
		 var dateIniObra = new Date (arrIni[2]+'/'+arrIni[1]+'/'+arrIni[0]); //milisegundos de inicio
		 var dateAct = new Date (arrAct[2]+'/'+arrAct[1]+'/'+arrAct[0]);//milisegundos de fin
	 
		 frm.tDiasTrans.value=parseInt((dateAct-dateIniObra)/86400000, 10);
	 }else if(inicioObra==false && finObra==false){
		 
		 frm.tDiasTrans.value="SIN REGISTRO";
	 }
 }
 
 
 
 
 function setValorOficio(valor){
	 valorOficio=valor;
 }
 
 function getValorOficio(){
	 return valorOficio;
 }
 
 
 function subirDoc(){
	 
	 if (cPermisoPag != 1) {
			fAlert("No tiene Permiso de ejecutar esta acción");
			return;
		}
	 
	 if( getValorOficio() > 0){
		 fAbreSubWindow(false,"pgSubirOficioSeguimiento","no","yes","yes","yes","800","600",50,50);
	 }else
		 fAlert("\nNo es posible subir documentos. Verifique la solicitud.");
	 
 }
 
 function setSubioArchivo(valor){
	 subioArchivo=valor;
 }
 
 function getNumSol(){
	 return frm.iNumSolicitudFiltro.value;
 }
 
 function getEjercicio(){
		return frm.iEjercicio.value;
	};

 
 function setNombreOficio(valor){
	 nombreOficio=valor;
 }
 
 function getNombreOficio(){
	 return nombreOficio;
 }
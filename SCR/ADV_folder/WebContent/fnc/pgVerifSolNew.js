// MetaCD=1.0
// Title: pgVerificacion.js
// Description: JS "Catálogo" de la entidad CPATitulo
// Company: Tecnología InRed S.A. de C.V.
// Author: Sandor Trujillo Q.
var cTitulo = "";
var FRMListado = "";
var frm;
var recibePNC=false;
var faltaCotejo = false;
var constanciaNoAf=false;
var term=true;
var dtCotejo="";
var dtEvaluacion="dtE";

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pgVerifSolNew.js";
  if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"EVALUACIÓN DE DOCUMENTOS TÉCNICOS":cTitulo;
  fSetWindowTitle();
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",4,"100%","","center");
       fTituloEmergente(cTitulo);
     FTDTR();

     ITRTD("",2,"","","top");
     InicioTabla("",1,"100%","","center");

      ITR();
        InicioTabla("",0,"100%","","center");
          ITR();
            TDEtiCampo(true,"EEtiqueta",0," Ejercicio :","iEjercicio","",4,4," Ejercicio ","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0," Número de Solicitud :","iNumSolicitud","",30,30," Solicitud ","fMayus(this);");
          FTR();
        FinTabla();
      FITR();
         TDEtiAreaTexto(false,"EEtiqueta",0," Trámite:",90,2,"cDscTramite",""," Trámite","fMayus(this);","","",true,true,false,"",0);
      FITR();
         TDEtiCampo(false,"EEtiqueta",0," Modalidad:","cDscModalidad","",90,90," Modalidad","fMayus(this);");
      FTR();
      ITRTD("",4,"","145","center","top");
         IFrame("IListado01A","95%","140","Listado.js","yes",true);
      FTDTR();
      ITR("",0,"","175","center","top");
        InicioTabla("",0,"90%","","center");
           ITR();
              ITD();
                 InicioTabla("",1,"100%","top","center");
                    ITR();
                       ITD("ESTitulo",4,"100%","","center");
                          TextoSimple("EVALUACIÓN DE REQUISITO");
                       FTD();
                    FTR();
                    ITR("",0,"","175","center","top");
                       //TDEtiCheckBox("EEtiqueta",0,"Requisito Válido:","lValidoBOX","1",true," Activo","","");
                       TDEtiCheckBox("EEtiqueta",0,"Requisito Válido:","lValidoBOX","1",true," Activo","","");
                    FTR();
                 FinTabla();
              FTD();
              ITD();
              FTD();
           FTR();
        FinTabla();
      FTR();
      ITR();
        InicioTabla("",0,"75%","","center");
          ITRTD("",0,"","40","center","bottom");
             IFrame("IPanel01A","95%","34","Paneles.js");
          ITD();
             BtnImg("Salir","aceptar","fSalir();");
          FTDTR();
        FinTabla();
      FTR();
      FinTabla();
    FTDTR();
    
    Hidden("iCveUsuario",fGetIdUsrSesion());
    Hidden("hdBotonAux","");
    Hidden("dtRecepcion");
    
    
    Hidden("hdEjercicio","");
    Hidden("lGuardar","");
    Hidden("hdNumSolicitud","");
    Hidden("iCveTramite","");
    Hidden("iCveModalidad","");
    Hidden("iCveRequisito","");
    Hidden("lValido","");
    Hidden("hdDscRequisito","");
    
    Hidden("iCveProducto","");
    Hidden("iCveCausaPNC","");
    Hidden("cDscOtraCausa","");
    Hidden("iCveUs",fGetIdUsrSesion());
      FTDTR();
     
    FinTabla();
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel01A");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(false);
  FRMPanel.fShow("Tra,");
  FRMPanel.fSetTraStatus("Sav,");
  FRMListado = fBuscaFrame("IListado01A");
  FRMListado.fSetControl(self);
  //FRMListado.fSetTitulo("Requisitos,¿Contiene Docto. Digitalizado?,Entregado,Válido,");
  FRMListado.fSetTitulo("Requisitos,Entregado,Físico, Válido,");
  //FRMListado.fSetDespliega("texto,texto,texto,texto,");
  FRMListado.fSetDespliega("texto,texto,texto,texto,");
  //FRMListado.fSetObjs(2,"Boton");
  //FRMListado.fSetCampos("10,14,4,5,");
  FRMListado.fSetCampos("10,4,22,5,");
  //FRMListado.fSetAlinea("left,center,center,center,");
  FRMListado.fSetAlinea("left,center,center,center,");

  fDisabled(true,"lValidoBOX");

  frm.hdBoton.value="Primero";
  frm.lValidoBOX.disabled = false;
  fTraeFechaActual();
//
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdNumReg.value = 10000;
  var bReturn = false;
  if(frm.iNumSolicitud.value != "" && frm.iNumSolicitud.value.length > 0 &&
     frm.iEjercicio.value != "" && frm.iEjercicio.value.length > 0){
	  
	  var modif=" and ICONSECUTIVOPNC is NOT null)"; //null or ICONSECUTIVOPNC = 0))"; //con iconsecutivo = 0 ya no trae los que fueron evaluados como validos
	  
     frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
                          " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value +
                          " and TRAREQUISITO.ICVEOFICINAEVAL in (SELECT ICVEOFICINA from GRLUSUARIOXOFIC where ICVEUSUARIO ="+ frm.iCveUs.value +")" +
                          " and TRAREQUISITO.ICVEDEPTOEVAL in (SELECT ICVEDEPARTAMENTO from GRLUSUARIOXOFIC where ICVEUSUARIO ="+ frm.iCveUs.value +")";

    frm.hdBotonAux.value = "VERIFICACIONxAREA";
    bReturn = true;
    fEngSubmite("pgVerificacionArea.jsp","Listado01A");

  }
  return bReturn;
}


var cFechaActual = "";
var numrequisito="";
var cPosicionaReq = "";

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iEtapaVerifica,iEtapaRecepcion,irPNC,iNotificacion,iVentanillaU,iModifTram){
 
	
	if(cId == "idFechaActual" && cError==""){
	    cFechaActual = aRes[0];
	    fRecibeValores();
	    }
		
	 if(cId == "Listado01A" && cError==""){
		 	term=true;

		    var requitomodif = numrequisito;
		    aResCompleto = fCopiaArregloBidim(aRes); //LEL070906
		    iCambios = 0;
		    
		    for(var i=0; i<aRes.length; i++){

		      if(aRes[i][14] > 0)
		        aRes[i][14] = 'SI';
		      else
		        aRes[i][14] = 'NO';
		      
		      if(aRes[i][5] > 0)
		    	  aRes[i][5]="<font color=green><b>&radic;</b></font>";
		      else{
		    	  aRes[i][5]="<font color=red><b>X</b></font>";
		    	  noValido= true;
		      }
		      if(aRes[i][20] == "")
		    	  faltaCotejo=true;
		      
		      if(aRes[i][21] == "")
		    	  term=false;
		      
		      if(aRes[i][22] == "1")
		    	  aRes[i][22] = "Sí";
		      else
		    	  aRes[i][22] = "No";
		    }
		    
		    frm.hdRowPag.value = iRowPag;
		    FRMListado.fSetListado(aRes);
		    FRMListado.fShow();
		    FRMListado.fSetLlave(cLlave);
		    fReposicionaListado(FRMListado,"9",requitomodif);
		    numrequisito="";

		    if(cPosicionaReq != "")
		      fReposicionaListado(FRMListado,"9",cPosicionaReq);
		    
		    cPosicionaReq = "";
		    
		    
		    if(term==true){
		    	fTerminadoArea();
		    }
	}	
	 
	 if(cId == "cIdTerminoArea" && cError==""){ 	
		   fAlert("\nHa finalizado la evaluación de requisitos por parte de la DGST para ésta solicitud.");
	 }

}

function fGuardar(){
	
	if(frm.iCveRequisito.value == ""){
    	fAlert("\n\nDebe seleccionar un registro para continuar.");
    	return false;
	}
	else 
		if(frm.lValidoBOX.checked== true && frm.iCveRequisito.value != "" && frm.dtRecepcion.value == ""){
	    	fAlert("No es posible marcar como válido un requisito que no ha sido entregado.");
	    	return false;
	    }
	    else{
	       fGuardarProcesa();
	    }
    
     return true;
}


function fGuardarProcesa(){

if(faltaCotejo!=true){	
   valValida = frm.lValidoBOX.checked;
     
   if( frm.lValidoBOX.checked == true ){
    	 frm.lValido.value = 1;
     }
     else{
    	 frm.lValido.value = 0;
     }
     
     if(fValidaTodo()==true && frm.lValidoBOX.checked == true && dtEvaluacion==""){
    	 frm.hdBoton.value = "Guardar";
 
	     if(confirm("\n¿Desea guardar la evaluación del requisito como válido?")==true){
	        
	    	 frm.lGuardar.value=true;
//	    	 
//	    	 alert(frm.iEjercicio.value);
//	    	 alert(frm.iNumSolicitud.value);
//	    	 alert(frm.iCveTramite.value);
//	    			 alert(frm.iCveModalidad.value);
//	    					 alert(frm.iCveRequisito.value);
//	    							 alert(frm.lValido.value);
//	    									 alert(frm.iCveUs.value);
	       
	        if(fNavega()==true){
	           FRMPanel.fSetTraStatus("Sav,");
	           FRMListado.fSetDisabled(false);
	        }
	     }
	     return true;
	 }
     
     if(fValidaTodo()==true && frm.lValidoBOX.checked == false&&dtEvaluacion==""){
    	 frm.hdBoton.value = "Guardar";
    	 if(confirm("\n¿Desea guardar la evaluación del requisito como no válido?")==true){
    		 fAbreRegistroCausasNoValido();
     }
    	 return true;
     }
}
else{
	fAlert("\nNo es posible evaluar los requisitos. No se ha cotejado toda la documentación.")
}
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato,iCol){
	
	frm.hdEjercicio.value = aDato[0];
	frm.hdNumSolicitud.value = aDato[1];
	frm.iCveTramite.value = aDato[2];
    frm.iCveModalidad.value = aDato[3];
    
    frm.iCveRequisito.value = aDato[9];
    
    
    if(aDato[5] === "<font color=green><b>&radic;</b></font>")
	  frm.lValido.value = 1;
    else
    	frm.lValido.value = 0;
 
    dtCotejo = aDato[20];
    dtEvaluacion= aDato[21];
      
   if (aDato[11]!="")
     frm.cDscTramite.value = aDato[11];
  
   if (aDato[12]!="")
     frm.cDscModalidad.value = aDato[12];
   
   if(frm.lValido.value == 0 && aDato[21] == ""){
      frm.lValidoBOX.checked = false;
      frm.lValidoBOX.disabled = false;
      FRMPanel.fSetTraStatus("Sav,");
   }
   else{
      frm.lValidoBOX.checked = true;
      frm.lValidoBOX.disabled = true;
      FRMPanel.fSetTraStatus("Disabled,");
   }
  
   numrequisito = frm.iCveRequisito.value;
   
   frm.hdDscRequisito.value = aDato[10];

   frm.dtRecepcion.value = aDato[4];
   if(aDato[4] == "")
      lEntregado = false;
   else
      lEntregado = true;
   
   if( frm.lValido.value == 1 ) frm.lValidoBOX.checked = true;
   else frm.lValidoBOX.checked = false;
   
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
	var	cMsg="";
	
	
	 if(frm.iCveProducto.value==""){
    	 frm.iCveProducto.value=null;
     }
     
     if(frm.iCveCausaPNC.value==""){
    	 frm.iCveCausaPNC.value=null;
     }
     
     if(frm.cDscOtraCausa.value==""){
    	 frm.cDscOtraCausa.value=null;
     }
	
     if(cMsg != ""){
      fAlert(cMsg);
      return false;
   }
          
   return true;
}


function fRecibeValores(){
  frm.iNumSolicitud.value = top.opener.fGetiNumSolicitud();
  frm.iEjercicio.value    = top.opener.fGetiEjercicio();
  frm.cDscTramite.value   = top.opener.fGetcDscTramite();
  frm.cDscModalidad.value = top.opener.fGetDscModalidad();
  constanciaNoAf = top.opener.getEsTecnico();
  recibePNC = top.opener.fGetExistePNC();
  fNavega();
}

function fSalir(){
  if(top.opener.fSetEvaluacion)
    top.opener.fSetEvaluacion(window, frm.iVerificacion.value, frm.iCveOficina.value, frm.iCveDepartamento.value, frm.lAnexo.value);
}


function fSetSolicitud(iEjercicio, iNumSolicitud){
  frm.iEjercicio.value = iEjercicio;
  frm.iNumSolicitud.value = iNumSolicitud;
  fNavega();
}

function fGethdEjercicio(){
  return frm.hdEjercicio.value;
}

function fGethdNumSolicitud(){
	  return frm.hdNumSolicitud.value;
	}

function fGetiCveRequisito(){
	  return frm.iCveRequisito.value;
}

function fGetiCveTramite(){
	  return frm.iCveTramite.value;
}

function fGetiCveModalidad(){
	  return frm.iCveModalidad.value;
}

function fGetcDscTramite(){
  return frm.cDscTramite.value;
}

function fGetcDscModalidad(){
  return frm.cDscModalidad.value;
}

function fSoloAlfanumericos2(cVCadena){
    if ( fRaros(cVCadena)      || fPuntuacion(cVCadena) ||
         fComa(cVCadena)    || fSignos(cVCadena) || fArroba(cVCadena) ||
         fParentesis(cVCadena))
        return false;
    else
        return true;
}

function fSalir(){
	if(top.opener){
		top.close();
	}
}


function fTerminadoArea(){
	
	if(frm.iNumSolicitud.value != "" && frm.iNumSolicitud.value.length > 0 &&
		     frm.iEjercicio.value != "" && frm.iEjercicio.value.length > 0){
			  var modif=" and ICONSECUTIVOPNC is NOT null)";// or ICONSECUTIVOPNC = 0))"; //con iconsecutivo = 0 ya no trae los que fueron evaluados como validos

			  frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
		                          " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value +
		                          " and TRAREQUISITO.ICVEOFICINAEVAL in (SELECT ICVEOFICINA from GRLUSUARIOXOFIC where ICVEUSUARIO ="+ frm.iCveUs.value +")" +
		                          " and TRAREQUISITO.ICVEDEPTOEVAL in (SELECT ICVEDEPARTAMENTO from GRLUSUARIOXOFIC where ICVEUSUARIO ="+ frm.iCveUs.value +")";

            frm.hdBoton.value = "GuardarTerminoArea";

			frm.hdBotonAux.value = "VERIFICACIONxAREA";
		    
		    bReturn = true;
		    fEngSubmite("pgVerificacionArea.jsp","cIdTerminoArea");
	}
	
}

function fConstanciaNoAfec(){
		 cClavesModulo="4,";
		 cNumerosRep="16,";
		 cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
		 fReportes();
}
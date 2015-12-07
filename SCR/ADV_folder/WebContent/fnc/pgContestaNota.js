// MetaCD=1.0 
// Title: pgShowRUPA.js
// Description: Consulta de RUPA
// Company: SCT 
// Author: JESR
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
var aUsrDepto;
var ICVETRAMITE=0;
var aNotifica = new Array();
var lFirst = true;
var cMsg="";
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pgContestaNota.js";
} 
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){ 
  cGPD+='<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'+
  '<form name="form1" enctype="multipart/form-data" method="post" action="UploadContestaNotifica">';
  fInicioPagina(cColorGenJS,"Respuesta a una Notificación",true); 
  InicioTabla("",0,"100%","100%","","","1"); 
    ITRTD("ESTitulo",0,"100%","1","center"); 
      TextoSimple("Respuesta a una Notificación."); 
    FTDTR(); 
    ITRTD("",0,"100%","100%","top"); 
    InicioTabla("",0,"100%","100%","center"); 
      ITRTD("",0,"100%","100%","center","middle"); 
        IFrame("IListado","95%","100%","Listado.js","yes",true); 
      FTDTR(); 
      ITRTD("",0,"","1","top");
      
      DinTabla("BarraWizard", "", 0, "", "90%", "center", "top", "", ".1", ".1");
      
/*        InicioTabla("ETablaInfo",0,"","","center","",1);
        ITRTD("ETablaST",5,"","15","center"); 
          TextoSimple("Anexar documentos a una Notificaión"); 
        FTDTR(); 
        ITR();        
          TDEtiAreaTexto(true,"EEtiqueta",0,"Observación",75,3,"COBSERVACION","","","","fMayus(this);",'onkeydown="fMxTx(this,240);"');
        FTR();
        ITR(); 
        
		ITRTD("", 0, "", "", "center");
		
		FTDTR();
        
/*          ITD("EEtiqueta",0,"");
             TextoSimple("Documento (PDF) a Anexar");
          FITD("EEtiquetaL",0,"");
             cGPD += '<input type="file" name="cFile" size="50">';
          FTD(); */
        FinTabla();
      FTDTR(); 
    FinTabla(); 
    Hidden("INTDOCDIG","");
    Hidden("ICVETRAMITEINT","");
    Hidden("iEjercicio","");
    Hidden("iNumSolicitud","");
    Hidden("cRutaFIEL",cRutaFIEL);
    FTDTR(); 
	ITRTD("",5,"","","center");
	  DinTabla("TDFIEL","",0,"90%","","center","middle","","","");
	FTDTR();	
    ITRTD("",0,"","40","center","bottom"); 
      IFrame("IPanel","95%","34","Paneles.js"); 
    FTDTR(); 
    FinTabla(); 
  fFinPagina(); 
} 
function fAnexaDoc(){
	
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo("--,Registro,Documento Anexado,Id Documento,Tipo de Notificación,Observación,"); 
  FRMListado.fSetCampos("8,5,2,11,10,");
  FRMListado.fSetObjs(0,"Liga",{label:"[Mostrar]", toolTip: "", style: "color:RED;text-decoration:none;font-weight:Bold;"});
  fDisabled(true); 
  frm.hdBoton.value="Primero"; 
  theTableFIEL = (document.all) ? document.all.TDFIEL:document.getElementById("TDFIEL");
  tBdyFIEL = theTableFIEL.tBodies[0];  
  fDelTBdyFIEL();
  tBarraWizard = document.getElementById("BarraWizard");
  fNavega(); 
} 
function fDelTBdyFIEL(){
	   for(i=0;tBdyFIEL.rows.length;i++){
	      tBdyFIEL.deleteRow(0);
	   }
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){ 
  frm.hdBoton.value = "Show";
  frm.hdFiltro.value =  ""; 
  frm.hdOrden.value  =  ""; 
  frm.hdNumReg.value =  "1000"; 
  return fEngSubmite("pgContestaNota.jsp","Listado"); 
} 
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iEjercicio,iNumSolicitud,iNumCita){ 
  if(cId == "Listado" && cError==""){ 
        frm.hdRowPag.value = iRowPag; 
        frm.iEjercicio.value = iEjercicio;
        frm.iNumSolicitud.value = iNumSolicitud;
        frm.ICVETRAMITEINT.value = iNumCita;
        FRMListado.fSetListado(aRes); 
        FRMListado.fShow(); 
        FRMListado.fSetLlave(cLlave);  
		if(aRes.length>0){
			fAlert("\nYa ha dado respuesta a esta notificación, no es posible agregar mas documentos.");
		}
		else{
	        getReqs();
		}
  }
  
  if(cId == "ListadoReqs" && cError==""){
	  renderFiles(aRes);
  }
  
}
function fNuevo(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fBlanked(); 
    fDisabled(false); 
    FRMListado.fSetDisabled(true); 
 } 
 
 function fGetDocs(){
	 cFiles = "";
	 for (i=0; i<frm.elements.length;i++){
	     obj = frm.elements[i]; 
	     if (obj.type == 'file'){
	    	 cFiles += obj.value.replace(/\\/g,'/') + '||';
	     }
	 }
	 return cFiles;
}
 
 function fGuardar(){
	   
	/* alert(cRutaFIEL +"\n" 
			 +fGetDocs()+"\n"
			 +cRutaProgMM+"\n"
			 +frm.ICVETRAMITEINT.value+"\n"
			 +cAreaCISFIEL);
	 
	 alert('<iframe name="FIEL" width="550px" height="350px" frameborder=0 scrolling=no SRC="'
	    		+cRutaFIEL
	    		+'?cListado='
	    		+fGetDocs()+
	    		+
	    		+'&cRutaJSPFIEL='
	    		+cRutaProgMM
	    		+'&ICVETRAMITE='
	    		+frm.ICVETRAMITEINT.value
	    		+'&cArea='
	    		+cAreaCISFIEL+'"></iframe>');
	 */
	 	if(fValidaTodo()==true){
	     if(confirm(cAlertMsgGen + '\n\n¿Desea usted generar la notificación? \n\nDado que se firman digitalmente los archivos junto con el formulario, \nuna vez generada la FIRMA no podrá realizar modificaciones a la respuesta.') == true){
	    	iRow = 0;
	    	newRow  = tBdyFIEL.insertRow(iRow++);
	    	newCell = newRow.insertCell(0);
	    	newRow.className = "EEtiquetaC";
	    	newCell.align = "center";
	    	newCell.innerHTML =	'<iframe name="FIEL" width="550px" height="350px" frameborder=0 scrolling=no SRC="'
	    		+cRutaFIEL
	    		+'?cListado='
	    		+fGetDocs()+
	    		'&cCadena='
	    		+getCAD()+
	    		'&cRutaJSPFIEL='
	    		+cRutaProgMM
	    		+'&ICVETRAMITE='
	    		+frm.ICVETRAMITEINT.value
	    		+'&cArea='
	    		+cAreaCISFIEL+'"></iframe>';
	    	//newCell.innerHTML =	'<iframe name="FIEL" width="550px" height="350px" frameborder=0 scrolling=no SRC="'+cRutaFIEL+'?cListado='+fGetDocs()+'&cCadena=&cRutaJSPFIEL='+cRutaSIIAF+'&ICVETRAMITE='+frm.ICVETRAMITEINT.value+'&cArea='+cAreaCISFIEL+'"></iframe>';
	    	
	    	FRMPanel.fSetTraStatus(",");
	     }
	   }   
	}
 
 function firmaSuccess(outputPaths,cCadFirmada,cNombre,cCURP,cRFC){
		cCADFirmada = cCadFirmada;
		cNOMCOM = cRFC + ' - ' + cNombre; 
		fDelTBdyFIEL();
		fEnProceso(true);
		setTimeout(function (){frm.submit();},250);
 }
 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function firmaCancel(){
		fCancelar();
		fDelTBdyFIEL();
 }
 
 function fCancelar(){ 
    if(FRMListado.fGetLength() > 0) 
      FRMPanel.fSetTraStatus("Disabled"); 
    else 
      FRMPanel.fSetTraStatus("AddOnly"); 
    fDisabled(true); 
    FRMListado.fSetDisabled(false); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){ 
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){ 
       fNavega(); 
    } 
 } 
 
function fSelReg(aDato,lCol){	
 	if(lCol == 0 && aDato[5] != ""){
 		if(lFirst == false){
 	       frm.INTDOCDIG.value = aDato[2]; 	       
 		   fAbreSubWindow(true,'pgDownINTDOCDIG','no','yes','no','yes','500','500',50,50);
 		}else
 			lFirst = false;
 	}
 	//frm.COBSERVACION.value = '';
 	//frm.cFile.value = '';
}
function fValidaTodo(){ 
    //cMsg = fValElements(); 
 
    if(cMsg != ""){ 
       fAlert(cMsg); 
       return false; 
    } 
    return true; 
}
function fGetINTDOCDIG(){
	return frm.INTDOCDIG.value;	
}

function fCarga(lValor){
	if(lValor == false)
		fAlert("\n - El documento no pudo ser recuperado. Contacte a su administrador de Sistemas.");
}



function fGenSol(aRes,cSol,aRes2,cOrigen,cFirma){
    fDelTBdy();
    fScripts = "";
    if(aRes.length > 0){
       aAT = new Array();
       iAT=0;     
       aSelect=new Array();
       iSel = 0;   
       iCell = 0;     
       iHdrAnt = 0;
       fCallback = "";
       cTabla = InicioTabla("ETablaInfo",0,"100%","","","",".1",".1");
       cIntroTabla = '';cTitTabla='';
       lValores = false;

       if(aRes2.length > 0)
          lFin = 1;
       else
     	 lFin=0;
       
       for(i=0;i<aRes.length;i++){
          if(aRes[i][10] == 0){
             if(cIntroTabla == '')
                cTabla += ITR()+ITD("EEtiqueta")+fSetField(aRes[i],true,aRes2)+FTR();
             else{
                cTabla += ITRTD("",20,"","","center")+InicioTabla("ETablaInfo",0)+
                          ITR()+cTitTabla+FTR()+               
                          ITR()+cIntroTabla+FTR()+
                          FinTabla()+FTDTR();
                cIntroTabla = '';
                cTitTabla='';             
             }            
          }else{
             cCampoTit = '';
             cIntroTabla += ITD("EEtiqueta")+TextoSimple(aRes[i][14])+fSetField(aRes[i],false,aRes2); 
             cTitTabla += ITD("ESTitulo")+aRes[i][2]+FTD();    
             if(aRes[i][4] == 5) cTitTabla = '';                             
          }                                 
          iHdrAnt = aRes[i][10];
       }
       
       
       if(cIntroTabla != '')
          cTabla += ITRTD("",20,"","","center")+InicioTabla("ETablaInfo",0)+
                    ITR()+cTitTabla+FTR()+               
                    ITR()+cIntroTabla+FTR()+
                    FinTabla()+FTDTR();
       iRR=0;                   
       newRow  = tBdy.insertRow(iRR++);
       newCell = newRow.insertCell(0);                                                        
       newCell.innerHTML = Hidden("ICVETRAMITEDIN",""+frm.ICVETRAMITE.value)+
                           Hidden("ICONSECUTIVODIN",""+frm.ICONSECUTIVO.value)+
                           Hidden("cCamSolDIN","");            
       if(cTabla != ''){      
          if(cSol != ''){
             newRow  = tBdy.insertRow(iRR++);
             newCell = newRow.insertCell(0);
             newCell.className = "ESTitulo";                                                          
             newCell.innerHTML = "SOLICITUD: "+cSol;   
             frm.iCveSol.value = cSol;     
          }      
          newRow  = tBdy.insertRow(iRR++);
          newCell = newRow.insertCell(0);                                                         
          newCell.innerHTML = cTabla+FinTabla();
       } 
    }
}


function getReqs(){
		//if(frm.iNumSolicitud.value>-1){
			frm.hdBoton.value = "subirDocsPNC";
			frm.hdFiltro.value = "";
			frm.hdOrden.value = "";
			frm.hdNumReg.value = "1000";
			return fEngSubmite("pgDocsSolADV.jsp", "ListadoReqs");
		//}
}



function renderFiles(aRes){

		while (tBarraWizard.rows.length > 0) {
			tBarraWizard.deleteRow(tBarraWizard.rows.length - 1);
		}

		tBarraWizard.className = "ETablaInfo";
		tBarraWizard.width = "90%";

		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.colSpan = 2;
		tCell.innerHTML = TextoSimple("CORRECCIÓN DE DOCUMENTACIÓN INVÁLIDA");
		tCell.className = "ETablaST";	
		

		for(var t=0;t < aRes.length;t++){
			tRw = tBarraWizard.insertRow();
			tCell = tRw.insertCell();
			tCell.innerHTML = aRes[t][0]+": ";
			tCell.className = "EEtiqueta";
			tCell = tRw.insertCell();
			tCell.innerHTML = '<input type="file" name="fileButonADV'+aRes[t][1]+'" size="25">';
		}
		
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.innerHTML = TDEtiAreaTexto(true,"EEtiqueta",0,"Observación",75,3,"COBSERVACION","","","","fMayus(this);",'onkeydown="fMxTx(this,240);"');

		fCancelar();		
		
}

function getCAD(){
	
	var cadX="a";
	//alert(cadX);
	return cadX;
}

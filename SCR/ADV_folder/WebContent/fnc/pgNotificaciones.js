// MetaCD=1.0 
// Title: pgShowRUPA.js
// Description: Consulta de RUPA
// Company: SCT 
// Author: JESR
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
var aUsrDepto;
var ICVETRAMITE=top.opener.fGetiEjercicio()+" / "+top.opener.fGetiNumSolicitud();
var aNotifica = new Array();
var lFirst = true;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pgNotificaciones.js";
  //ICVETRAMITE = top.opener.fGetICVETRAMITE();
  aNotifica[0]=[15,'NOTIFICACIÓN'];
} 
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){ 
  cGPD+='<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'+
  '<form name="form1" enctype="multipart/form-data" method="post" action="UploadNotifica">';
  fInicioPagina(cColorGenJS,"Notificaciones",true); 
  InicioTabla("",0,"100%","90%","","","1"); 
    ITRTD("ESTitulo",0,"100%","1","center"); 
      TextoSimple("Generación de Notificaciones para el trámite: " + ICVETRAMITE); 
    FTDTR(); 
      Hidden("iEjercicio",top.opener.fGetiEjercicio());
      Hidden("iNumSolicitud",top.opener.fGetiNumSolicitud());
      Hidden("CTIPODSC",0);
      Hidden("iCveUsuario",top.fGetUsrID());

    ITRTD("",0,"100%","260","top"); 
      InicioTabla("",0,"100%","260","center"); 
          ITRTD("",0,"100%","100%","center","middle"); 
            IFrame("IListado","95%","250","Listado.js","yes",true); 
          FTDTR(); 
      FinTabla();
      ITRTD("",0,"","1","top");
        InicioTabla("ETablaInfo",0,"","","center","",1);
        ITRTD("ETablaST",5,"","15","center"); 
          TextoSimple("Notificación a realizarse por el tablero de trámites con Firma Electrónica Avanzada"); 
        FTDTR(); 
        ITR();
          TDEtiSelect(false,"EEtiqueta",0,"Tipo de Notificación","CTIPO","");
        FTR();
        ITR();        
          TDEtiAreaTexto(true,"EEtiqueta",0,"Observación",75,3,"COBSERVACION","","","","fMayus(this);",'onkeydown="fMxTx(this,240);"');
        FTR();
        ITR(); 
          ITD("EEtiqueta",0,"");
             TextoSimple("Documento (PDF) a Anexar");
          FITD("EEtiquetaL",0,"");
             cGPD += '<input type="file" name="cFile" size="50">';
          FTD();
        FTR();
        FinTabla();
      FTDTR(); 
    FinTabla(); 
    Hidden("INTDOCDIG","");
    Hidden("IID",""+top.fGetUsrID());
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
  FRMListado.fSetTitulo("--,Registro,Documento Anexado,Id Documento,Usuario,Tipo de Notificación,Observación,"); 
  FRMListado.fSetCampos("8,5,2,11,13,10,");
  FRMListado.fSetObjs(0,"Liga",{label:"[Mostrar]", toolTip: "", style: "color:RED;text-decoration:none;font-weight:Bold;"});
  fFillSelect(frm.CTIPO,aNotifica,false,0,0,1);
  fDisabled(true,"iEjercicio,iNumSolicitud,"); 
  frm.hdBoton.value="Primero"; 
  fNavega(); 
} 
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){ 
    if(frm.iEjercicio.value>0 && frm.iNumSolicitud.value>0){
      frm.hdFiltro.value =  " iEjercicio=  " +frm.iEjercicio.value+ 
                            " AND iNumSolicitud = "+frm.iNumSolicitud.value+
                            " AND TSREGNOTIFICA is not null "; 
      
      frm.hdOrden.value  =  "TSREGNOTIFICA DESC"; 
      frm.hdNumReg.value =  "1000"; 
      fEngSubmite("pgNotificaciones.jsp","Listado"); 
    }
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
    fCancelar(); 
    
    if(aRes.length > 0){
    	refrescaOpener();
    	FRMPanel.fSetTraStatus("Disabled");
    }
  } 
}
function fNuevo(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fBlanked(); 
    fDisabled(false); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){ 
     frm.CTIPODSC.value = frm.CTIPO.options[frm.CTIPO.selectedIndex].text;
    if(fValidaTodo()==true){
       if(confirm(cAlertMsgGen + "\n\n ¿Desea usted generar la notificación? \n\nUna vez generada ésta no podrá ser eliminada ni modificada.")){ 
    	 frm.submit();
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){ 
    lFirst = true;
    if(FRMListado.fGetLength() > 0) 
      FRMPanel.fSetTraStatus("AddOnly"); 
    else 
      FRMPanel.fSetTraStatus("AddOnly"); 
    fDisabled(true,"iEjercicio,iNumSolicitud,"); 
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
	}else lFirst = false;
    }
    if(aDato[28]==1)FRMPanel.fSetTraStatus(",");
    else {
	if(FRMListado.fGetLength() > 0)
	    FRMPanel.fSetTraStatus("AddOnly");
	else
	    FRMPanel.fSetTraStatus("AddOnly"); 
    }
 }
 function fValidaTodo(){ 
    cMsg = fValElements(); 
 
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

function fBuscaSol(){
    frm.hdSelect.value = "";
    frm.hdLlave.value  = "";
    fEngSubmite("pgTRARegSolicitud.jsp","cIdSolicitud");
}

function refrescaOpener(){
	
	if(top.opener)
		top.opener.fNavega();
	
}
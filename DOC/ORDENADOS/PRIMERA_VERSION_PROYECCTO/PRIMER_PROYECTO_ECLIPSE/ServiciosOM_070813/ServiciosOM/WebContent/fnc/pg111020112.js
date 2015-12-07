// MetaCD=1.0
 // Title: pg111020112.js
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ABarrientos, iCaballero

 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResTemp = new Array();
 var iCveEtapa = 0;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020112.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","10","center","top");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           fDefOficXUsr();
         FinTabla();
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado_12","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","30","center","top");
         Liga("[Recibir las solicitudes]","fRecibeSolicitud();","Registrar como recibida la solicitud");

         ITRTD("",0,"","30","center","top");
         Liga("[Documentos Digitales Anexos al Trámite por Internet con FIEL]","fDocDig();","...");SP();
       FTDTR();
       if (top.fGetUsrID())
         Hidden("hdCveUsuario",top.fGetUsrID());
       else
        Hidden("hdCveUsuario",1);

       Hidden("hdLlave","");
       Hidden("hdSelect","");
       Hidden("iEjercicio","");
       Hidden("iNumSolicitud","");
       Hidden("iCveSolicitante")
       Hidden("iCveTramite","");
       Hidden("iCveModalidad","");
       Hidden("lAnexo","");
       Hidden("hdEtapa","EtapaRegistro");
       Hidden("hdEtapaGuardar","EtapaRecepcion");
       FinTabla();
     FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMListado12 = fBuscaFrame("IListado_12");
   FRMListado12.fSetControl(self);
   FRMListado12.fSetTitulo("Recibido,Anexos,Requisitos,Ejercicio,Núm. Solicitud,Trámite,Modalidad,Solicitante,Asignar,");
   FRMListado12.fSetCampos("0,1,4,5,6,20,");
   FRMListado12.fSetObjs(0,"Caja");
   FRMListado12.fSetObjs(1,"Caja");
   FRMListado12.fSetObjs(2,"Boton");
   FRMListado12.fSetAlinea("center,center,center,left,left,left,");
   fDisabled(false);
   frm.hdBoton.value="Primero";

   fCargaOficDeptoUsr();
 }
 // LLAMADO al JSP específico para la navegación de la página

 function fNavega(){
     if(frm.iCveOficinaUsr.value>0 && frm.iCveDeptoUsr.value >0){
       frm.hdFiltro.value =  "  TRARegSolicitud.LTRAMINERNET=1  ";
       frm.hdOrden.value =  " iEjercicio,iNumSolicitud";
       frm.hdNumReg.value =  1000;
       fEngSubmite("pgTRARecepcion.jsp","Listado");
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
   if(cError!=""){
     fAlert('\n - '+cError);
     return;
   }
   
   if(cId == "DocDig" && cError==""){	   
	 aDocDig = aRes;
	 fAbreSubWindow(false,"pgShowDocDig","no","no","no","no","800","600",50,50);
   }

   if(cId == "Listado" && cError==""){
     aResTemp = aRes;
     frm.hdRowPag.value = iRowPag;
     
     for(i=0;i<aRes.length;i++){
	   aRes[i][20] = '<font color=blue> Asignar Solicitante </font>';
     }
     
     FRMListado12.fSetListado(aRes);

     FRMListado12.fShow();
     FRMListado12.fSetLlave(cLlave);
     fCancelar();
   }

   fResOficDeptoUsr(aRes,cId,cError,false,true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    fDisabled(false);
    FRMListado12.fSetDisabled(false);
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato, iCol){
     window.parent.frm.lLiga.value = false
     frm.iEjercicio.value    = aDato[0];
     frm.iNumSolicitud.value = aDato[1];
     frm.iCveSolicitante.value = aDato[7];
     if(iCol==2){
	 if(parseInt(aDato[0],10) > 0 && parseInt(aDato[1],10)>0)
	     fAbreMuestraReq();
	 else
	     fAlert("No puede aconsultar requisitos de una solicitud si no es seleccionada");
     }
     if(iCol==8){
	 fImportaCampo();
     }
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

function fDefOficXUsr(){
  //Necesario definir los campos ocultos  Hidden("fResultado "); y Hidden("hdLlave");
  var cTx;
  cTx = ITRTD("EEtiquetaC",0,"100%","20","center")+
      InicioTabla("",0,"","","center")+
        ITR()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Oficina:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveOficinaUsr","fOficinaUsrOnChange();")+
          FTD()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Departamento:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveDeptoUsr","fBuscar();")+
          FTD()+
        FTR()+
      FinTabla()+
    FTDTR();
  return cTx;
}


function fBuscar(){
  fNavega();
}
function fOficinaUsrOnChangeLocal(){
  fBuscar();
}
function fDeptoUsrOnChangeLocal(){
}

function fRecibeSolicitud(){
    if(!(frm.iCveSolicitante.value > 0)){
	fAlert("Es requerido asignar un solicitante.");
	return;
    }
 frm.iEjercicio.value    = "";
 frm.iNumSolicitud.value = "";
 frm.iCveTramite.value   = "";
 frm.iCveModalidad.value = "";
 frm.lAnexo.value="";


 if(!confirm("¿Esta seguro que desea recibir el trámite?")){
   return;
 }

 aCBoxReq    = FRMListado12.fGetObjs(0);
 aCBoxAnexo = FRMListado12.fGetObjs(1);

 for(cont=0;cont < aCBoxReq.length;cont++){
  if(aCBoxReq[cont]){
    if (frm.iEjercicio.value=="") frm.iEjercicio.value=aResTemp[cont][0]; else frm.iEjercicio.value+=","+aResTemp[cont][0];
    if (frm.iNumSolicitud.value=="" ) frm.iNumSolicitud.value=aResTemp[cont][1]; else frm.iNumSolicitud.value+=","+aResTemp[cont][1];
    if (frm.iCveTramite.value=="" ) frm.iCveTramite.value=aResTemp[cont][2]; else frm.iCveTramite.value+=","+aResTemp[cont][2];
    if (frm.iCveModalidad.value=="" ) frm.iCveModalidad.value=aResTemp[cont][3]; else frm.iCveModalidad.value+=","+aResTemp[cont][3];

    if (frm.lAnexo.value==""){
      if (aCBoxAnexo[cont]) frm.lAnexo.value="1"; else frm.lAnexo.value="0";
    }
    else{
      if (aCBoxAnexo[cont]) frm.lAnexo.value+=",1"; else frm.lAnexo.value+=",0";
    }
  }
 }

 if (frm.iEjercicio.value == ""){  
   fAlert ('\n - Seleccione al menos un registro para hacer esta operación.');
   return;
 }

 frm.hdBoton.value = "RecibeTramiteInt";
 frm.hdFiltro.value =  " ( LTRAMINERNET = 1 ) ";
 frm.hdOrden.value =  "iEjercicio,iNumSolicitud";
 frm.hdNumReg.value =  1000;
 fEngSubmite("pgTRARecepcion.jsp","Listado");

}

function fEnviaDatosMuestraRequisitos(objWindow){
  if(objWindow && objWindow.fAsignaEjercicioSolicitud)
    objWindow.fAsignaEjercicioSolicitud(frm.iEjercicio.value, frm.iNumSolicitud.value);
}

function fImportaCampo(){
    if(window.parent){
	if(window.parent.fGetCampos){
	    if(frm.iEjercicio.value>0 && frm.iNumSolicitud.value>0){
		window.parent.fGetCampos(frm.iEjercicio.value, frm.iNumSolicitud.value);
	    }
	}
    }
}

function fDocDig(){	 
	 //frm.iCveTramiteDocDig.value = (parseInt(frm.ICVETRAMITE.value,10) > 0?frm.ICVETRAMITE.value:parent.fGetiCveTramiteINT());
	 if(parseInt(frm.iNumSolicitud.value,10) > 0){
	     //alert("Ejercicio: "+frm.iEjercicio.value + "  \nSolicitud: "+frm.iNumSolicitud.value);
	    frm.hdNumReg.value =  "100000";
	    fEngSubmite("pgINTDocDig.jsp","DocDig");
	 }else
		 fAlert("\n - No tiene asignado un número de trámite.");
}
function fGetDocDig(){
return aDocDig;
}
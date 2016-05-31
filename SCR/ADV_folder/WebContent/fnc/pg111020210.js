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
 var cPermisoPag;
 var isTramInt;
 var notifInternet = false;
 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020210.js";
//   if(top.fGetTituloPagina){;
//     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
//   }
   cTitulo = "NOTIFICACIÓN DE PRODUCTO NO CONFORME";  
   cPermisoPag = fGetPermisos(cPaginaWebJS);
   fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);

   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple(cTitulo);
     FTDTR();
       //Encabezado
     ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"100%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("SOLICITUDES");
           FTDTR();
                   Hidden("iCveTramite","");
                   Hidden("iCveModalidad","");
                   Hidden("iCveRequisito","");
                   Hidden("lEntregado","");
                   Hidden("iCveUsuRecibe","");
                   Hidden("cConjunto");
                   Hidden("iLlave");
                   Hidden("hdLlave");
                   Hidden("hdSelect");
                   Hidden("iEjercicio");
                   Hidden("iNumSolicitud");
                   Hidden("cDscTramite");
                   Hidden("cDscModalidad");
                   Hidden("cCveTramite");
                   Hidden("iCveEtapa",20)//notificacion, hacer propiedad;
                   
           FITR();
       ITRTD("",0,"","0","center","top");
         IFrame("IFiltro","100%","34","Filtros.js");
       FTDTR();
         ITRTD("",0,"","1","center");
           InicioTabla("",0,"100%","","","",1);
           ITRTD("",0,"","1","center");
             InicioTabla("",0,"100%","","","",1);
             ITRTD("",0,"","1","center");
               InicioTabla("",0,"100%","","","",1);
                 FITR();
                   TDEtiCampo(true,"EEtiqueta",0,"Ejercicio Sol. :","iEjercicioFiltro","",4,4,"Ejercicio de la solicitud registrada","fMayus(this);","","",true,"");
                   TDEtiCampo(true,"EEtiqueta",0,"Num. Solicitud:","iNumSolicitudFiltro","",5,5,"Número de solicitud registrada","fMayus(this);","","",true,"");
                 FTDTR();
               FinTabla();
               ITRTD("",0,"","1","center");
               InicioTabla("",0,"100%","","","",1);
                 ITD("EEtiquetaL",0,"0","","center","middle")+
                   //Text(false,"cCveTramite","",11,10,"Teclee la clave interna del trámite para ubicarlo","this.value='';"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramiteFiltro, this);\"","",true,true);
                   Select("iCveTramiteFiltro","fTramiteOnChange(true);");
                  FITR();
               FinTabla();
               ITRTD("",0,"","1","center");
               InicioTabla("",0,"100%","","","",1);
                  FITR();
//                    TDEtiCampo(true,"EEtiqueta",0,"Fecha de oficio Mayor o Igual a:","dtOficiio","",4,4,"Ejercicio de la solicitud registrada","fMayus(this);","","",true,"");
//                    TDEtiCampo(true,"EEtiqueta",0,"y menor o Igual a:","dtOficiio2","",4,4,"Ejercicio de la solicitud registrada","fMayus(this);","","",true,"");
                  FTDTR();
               FinTabla();
            FinTabla();
            ITD();
              BtnImg("Buscar","lupa","fNavega();");
          FinTabla();
          ITRTD("",0,"","","center");
            IFrame("IListado","100%","370","Listado.js","yes",true);
              FTDTR();
              ITR();
                ITD("EEtiquetaC",0,"","","center");
                  Liga("Registrar Notificación","fBuscaDocumentos();","Registrar Notificación"); 
               FTD();
              FTD();
           FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
       FTDTR();
     FinTabla();
     
     Hidden("dtOficiio", "");
     Hidden("dtOficiio2", "");
     
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];

   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");

  FRMFiltro.fSetFiltra(" Consecutivo,iCveRequisito, Descripción,cDscRequisito,");
  FRMFiltro.fSetOrdena(" Consecutivo,iCveRequisito, Descripción,cDscRequisito,");


   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetSelReg(1);
   
//   FRMListado.fSetTitulo("Ejercicio,Solicitud,Promovente,Trámite,Modalidad,P.N.C.,Fecha Oficio,Núm. Oficio,Fecha Notificación,Recibió Notificación,Internet,");
//   FRMListado.fSetCampos("0,                1,         2,      3,        4,     9,           5,           6,                7,                   8,      10,");
//   FRMListado.fSetDespliega("texto,texto,texto,texto,texto,date,texto,date,texto,texto,");
//   FRMListado.fSetAlinea("center,center,left,center,center,center,center,center,left,center,");
   
   FRMListado.fSetTitulo("Ejercicio,Solicitud,Promovente,Trámite,Modalidad,Fecha Oficio,Fecha Notificación,Recibió Notificación,Internet,");
   FRMListado.fSetCampos("0,1,2,3,4,5,7,8,10,");
   FRMListado.fSetDespliega("texto,texto,texto,date,texto,date,texto,texto,texto,");
   FRMListado.fSetAlinea("center,center,center,center,center,center,center,center,");

   
   fDisabled(true,"iEjercicioFiltro,iNumSolicitudFiltro,iCveTramiteFiltro,dtOficiio,cCveTramite,dtOficiio2,");
   frm.hdBoton.value="Primero";
   fTraeFechaActual();
   //fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "";
   if(frm.iEjercicioFiltro.value!="")
     frm.hdFiltro.value += " AND TRAREGSOLICITUD.IEJERCICIO = "+frm.iEjercicioFiltro.value;
   if(frm.iNumSolicitudFiltro.value!="")
     frm.hdFiltro.value += " AND TRAREGSOLICITUD.INUMSOLICITUD = "+frm.iNumSolicitudFiltro.value;
   if(frm.iCveTramiteFiltro.value>0)
     frm.hdFiltro.value += " AND TRAREGSOLICITUD.ICVETRAMITE = "+frm.iCveTramiteFiltro.value;
   if(frm.dtOficiio.value!="" && frm.dtOficiio2.value!="")
     frm.hdFiltro.value += " AND TRAREGREQXTRAM.DTOFICIO BETWEEN '"+fGetDateSQL(frm.dtOficiio.value)+"' AND '" + fGetDateSQL(frm.dtOficiio2.value)+"'";;

   frm.hdFiltro.value+= "    AND TRAREGREQXTRAM.ICVEREQUISITO = ( " +
                        "       SELECT MAX(RT.iCveRequisito) FROM TRAREGREQXTRAM RT " +
                        "          Where RT.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD " +
                        "            AND RT.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO " +
                        "            AND (RT.DTOFICIO IS NOT NULL  OR GRLREGISTROPNC.DTOFICIO is not null))" ;
   frm.hdOrden.value =  " TRAREGSOLICITUD.IEJERCICIO DESC, TRAREGSOLICITUD.INUMSOLICITUD DESC, " +
                        " TRAREGPNCETAPA.IORDEN DESC ";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   fEngSubmite("pgTRARegSolicitudT.jsp","Listado");
 }


 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave, msgOficios){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
  if(cError!="")
    FRMFiltro.fSetNavStatus("Record");
   if(cId == "Listado" && cError==""){
	 
	 for(var f=0;f<aRes.length;f++){
		 if(aRes[f][10]==1)
			 aRes[f][10]="SI";
		 else
			 aRes[f][10]="NO";
	 }
	   
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     frm.hdRowPag.value = iRowPag;
     FRMFiltro.fSetNavStatus(cNavStatus);
   }
   if(cId == "Reporte" && cError=="")
     aResReporte = aRes;
   if(cId == "idFechaActual" && cError==""){
     frm.iEjercicioFiltro.value = aRes[1][2];
     fCargaTramites();
   }
   if(cId == "CIDTramite" && cError==""){
     fFillSelect(frm.iCveTramiteFiltro,aRes,true,frm.iCveTramiteFiltro.value,0,6);
   }
   
   if (cId == "buscaRetraso" && cError == "" ) {
		if(aRes.length>0&&parseInt(aRes[0][0])>0)
			fAlert("\nLa solicitud tiene un retraso en etapas anteriores de "+aRes[0][0]+" días.");
	}
   
   if(cId == "buscaDocumentosEtapa" && cError == ""){
		if(msgOficios!=""){
			fAlert("No es posible realizar la acción. "+msgOficios);
		}else{
			if(isTramInt==false)
				fVerifica();
			else
				fNotificaInt();
		}
	}
 }
 
 function fBuscaDocumentos(){
		if (frm.iEjercicio.value>0 && frm.iNumSolicitud.value>0 && frm.iCveEtapa.value>0) {		
					
			frm.hdBoton.value = "buscaDocumentosEtapa";
			frm.hdFiltro.value = "IEJERCICIO ="+ frm.iEjercicio.value+ " AND INUMSOLICITUD = "+ frm.iNumSolicitud.value; 
			
		  fEngSubmite("pgGestionOficios.jsp","buscaDocumentosEtapa");
		}else{
			fAlert("Debe seleccionar una solicitud válida.");
		}
	}
/*
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    lModificando = true;
    fDesactiva();
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
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    aCBox = FRMListado.fGetObjs(0);
    aRes = FRMListado.fGetARes();
    lModificando = false;
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
   fNavega();
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    lModificando = true;
    fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,");
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    lModificando = false;
    fDesactiva();
    fDisabled(true,"iEjercicio,iNumSolicitud,");
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       lModificando = false;
    }
 }*/
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato,iCol){
   frm.iEjercicio.value = aDato[0];
   frm.iNumSolicitud.value = aDato[1];
   frm.cDscTramite.value = aDato[3];
   frm.cDscModalidad.value = aDato[4];
     
   if(aDato[10]=="SI")
		 isTramInt=true;
	 else
		 isTramInt=false;
   
   if(frm.iEjercicio.value > 0 &&  frm.iNumSolicitud.value >0&&iCol>0)
		fBuscaRetraso();
 }

 function fImprimir(){
    self.focus();
    window.print();
 }

 

 function fSetSolicitud(iEjercicio,iNumSolicitud){
   frm.iEjercicio.value = iEjercicio;
   frm.iNumSolicitud.value = iNumSolicitud;
   fBuscaSolicitud();
 }

function fReporte(){
  if(lCancelado == false){
     cClavesModulo="2,";
     cNumerosRep="3,";
     cFiltrosRep = frm.iEjercicio.value + "," + frm.iNumSolicitud.value + cSeparadorRep;
     lEjecuta = true;

     if (lEjecuta)
       fReportes();
     else
       fAlert("\n-Debe seleccionar un trámite para poder imprimir el acuse de recibo");
  }else{
    fAlert("No se pueden generar reportes si la Solicitud está cancelada");
  }
}

function fGetiNumSolicitud(){
  return frm.iNumSolicitud.value;
}

function fGetiEjercicio(){
  return frm.iEjercicio.value;
}

function fGetcDscTramite(){
  return frm.cDscTramite.value;
}

function fGetDscModalidad(){
  return frm.cDscModalidad.value;
}

//window, frm.iVerificacion.value, frm.iCveOficina.value, frm.iCveDepartamento.value, frm.lAnexo.value
function fSetEvaluacion(wVerifica,iVer,iOf,iDep,lAnxo){
  wVerifica.top.close();
  fNavega();
}
function fTraeTramites(){
}

function fGetICVETRAMITE(){
	return frm.iNumSolicitud.value;
}

function fNotificaInt(){

	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	} 
	
	if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != '' &&
		       frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != ''){
			   if(fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)){
				   
				   if(isTramInt==true)
						fAbreSubWindowPermisos("pgNotificaciones","810","460");
					else
						fAlert("\nHa seleccionado un trámite que inicio por ventanilla, debe usar la liga \"Registrar Notificación\".");
				   
			   }else{
				   fAlert("\n Debe seleccionar una solicitud del listado.");
			   }
	   }else{
		   fAlert("\n Debe seleccionar una solicitud del listado.");
	   }
}

function fVerifica(){
	

	if (cPermisoPag != 1) {
		fAlert("No tiene Permiso de ejecutar esta acción");
		return;
	}
	 
	 if (frm.iEjercicio.value != 0 && frm.iEjercicio.value != '' &&
		       frm.iNumSolicitud.value != 0 && frm.iNumSolicitud.value != ''){
			   if(fSoloNumeros(frm.iEjercicio.value) && fSoloNumeros(frm.iNumSolicitud.value)){
				   
				   if(isTramInt==false)
						 fAbreSubWindowPermisos("pgVerifNotif","810","460");
					 else
						 fAlert("\nHa seleccionado un trámite que inicio por intenet, debe usar la liga \"Registrar Notificación A Solicitud por Internet.\""); 
				   
			   }else{
				   fAlert("\n Debe seleccionar una solicitud del listado.");
			   }
	   }else{
		   fAlert("\n Debe seleccionar una solicitud del listado.");
	   } 
	 
}
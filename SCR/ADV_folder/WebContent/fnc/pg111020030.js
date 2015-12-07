// MetaCD=1.0
 // Title: pg111020030.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Jorge Arturo Wong Mozqueda
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aVentanillaUnica=new Array();
 var aPorArea=new Array();
 var aCompleto=new Array();
 var cVentUnica="0";
 var cPermisosPag;
 var cancelada=false;


 //var iEjercicio2;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020030.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
   fSetWindowTitle();
   cPermisosPag = fGetPermisos("pg111020031.js");
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
       ITRTD("",0,"","0","center","top");
         IFrame("IFiltro","0%","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","1","center");
        //Liga("Buscar Solicitud","fAbreBuscaSolicitud();","Busqueda de la Solicitud");

       InicioTabla("ETablaInfo",0,"75%","","","center",1);
         ITRTD("ETablaST",5,"","","center");
           TextoSimple("SOLICITUD");
          FTDTR();
         fDefOficXUsr();
       FinTabla();

       InicioTabla("ETablaInfo",0,"75%","","","center",1);
           ITR();

        //   FITR();
              TDEtiSelect(false,"EEtiqueta",0," Estado:","iCveEstado","fOnChange();");
       //    FITR();
              TDEtiCampo(false,"EEtiqueta",0," Ejercicio:","iEjercicioFiltro","",4,4," Ejercicio","fMayus(this);");
          // FITR();
              TDEtiCampo(false,"EEtiqueta",0," No. Solicitud:","iNumSolicitudFiltro","",8,8," Núm. Solicitud","fMayus(this);","","","","","");
           //FITR();
              ITD();
              BtnImg("vgbuscar","lupa","fNavega();","");
           FITR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0," Fecha Inicio:","dtInicio","",10,10," Fecha de Inicio de Búsqueda","");
              TDEtiCampo(false,"EEtiqueta",0," Fecha Fin:","dtFin","",10,10," Fecha de Fin de Búsqueda","");
           FITR();
       FinTabla();

       ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","center",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("MODIFICAR TRÁMITE");
           FTDTR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0," Ejercicio:","iEjercicio","",4,4," Ejercicio","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0," No. Solicitud:","iNumSolicitud","",8,8," Núm. Solicitud","fMayus(this);","","","","","");
           FITR();
               TDEtiSelect(true,"EEtiqueta",0," Motivo de Cancelación:","iCveMotivoCancela","");
           FITR();
              TDEtiAreaTexto(false,"EEtiqueta",0," Observaciones:",50,2,"cObs",""," Usuario que Cancela","","fMayus(this);",'onkeydown="fMxTx(this,2000);"');
           FITR();

         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
       Hidden("iCveTramite");
       Hidden("iCveModalidad");
       Hidden("iCveFormatoReg");
       Hidden("iCveUsuario");
       Hidden("hdLlave","");
       Hidden("hdSelect","");
       Hidden("hdCancela");
       Hidden("lMuestraDepto");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(true);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   //ORIGINAL FRMListado.fSetTitulo("Ejercicio,Solicitud,Ofi. Recibe,Área Recibe,Ofi. Resuelve,Área Resuelve,Solicitante,Lim. Entrega,Trámite,Cancelación,");
   //FRMListado.fSetCampos("0,1,4,5,11,12,6,7,2,8,");
//   FRMListado.fSetTitulo("Ejercicio,Solicitud,F. Inicio,Solicitante,Lim. Entrega,Trámite,F. Cancelación,");
//   FRMListado.fSetCampos("0,1,13,6,7,2,8,");
   //FRMListado.fSetTitulo("Ejercicio,Solicitud,F. Inicio,Solicitante,Trámite,F. Cancelación,");
   FRMListado.fSetTitulo("Ejercicio,Solicitud,F. Inicio,Promovente,Aprovechamiento,F. Cancelación,");
   FRMListado.fSetCampos("0,1,13,6,2,8,");

//   FRMListado.fSetCampos("0,1,4,11,6,7,2,8,");
   //FRMListado.fSetAlinea("center,center,center,left,center,left,center,");
   FRMListado.fSetAlinea("center,center,center,left,left,center,");
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow(",");
   aSelect=new Array();
   //frm.hdBoton.value="Primero";
   aSelect[0]=["0","En Proceso"];
   aSelect[1]=["1","Abandonadas"];
   aSelect[2]=["2","Canceladas"];
   aSelect[3]=["3","Otorgadas"];
   fDisabled(true,"iCveEstado,iEjercicioFiltro,iNumSolicitudFiltro,iCveOficinaUsr,iCveDeptoUsr,dtInicio,dtFin,lMuestraDeptoBOX,");
   fFillSelect(frm.iCveEstado,aSelect,false,frm.iCveEstado.value,0,1);
   fCargaOficDeptoUsr();
   fCargaMotivos();
 //  fNavega();
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "";
   //LEL250906
   if(frm.dtInicio.value != "" && frm.dtFin.value != ""){
     if(fComparaFecha(frm.dtInicio.value, frm.dtFin.value, true)){
       frm.hdFiltro.value = " TRAREGSOLICITUD.tsRegistro >= '" + frm.dtInicio.value.substring(6,10) + "-" +
                          frm.dtInicio.value.substring(3,5) + "-" +
                          frm.dtInicio.value.substring(0,2) + " 00:00:00.0'";
       frm.hdFiltro.value += " AND TRAREGSOLICITUD.tsRegistro <= '" + frm.dtFin.value.substring(6,10) + "-" +
                          frm.dtFin.value.substring(3,5) + "-" +
                          frm.dtFin.value.substring(0,2) + " 23:59:59.9' ";

     }else{
       fAlert("La Fecha de Inicio debe ser menor o igual a la de Fin");
       return;
     }
    //FinLEL250906
   }else{
     if (frm.iEjercicioFiltro.value != ""){
      frm.hdFiltro.value = " TRARegSolicitud.iEjercicio = " + frm.iEjercicioFiltro.value;
     }

     if (frm.iNumSolicitudFiltro.value != ""){
       if (frm.hdFiltro.value != "")
         frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
       else
         frm.hdFiltro.value = " TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
     }
   }
  
   
//   //Solicitudes en Tiempo. //ORIGINAL
//   if (frm.iCveEstado.value == 0){
//     if (frm.hdFiltro.value != "")
//       frm.hdFiltro.value = frm.hdFiltro.value +
//                            " and (TRARegSolicitud.dtLimiteEntregaDocs >= (current date) and TRARegTramXSol.dtCancelacion is null";
//     else
//       frm.hdFiltro.value = " (TRARegSolicitud.dtLimiteEntregaDocs >= (current date) and TRARegTramXSol.dtCancelacion is null";
//     frm.hdFiltro.value += "  OR TRARegSolicitud.dtLimiteEntregaDocs is null and TRARegTramXSol.dtCancelacion is null and TRARegSolicitud.labandonada=0 ) ";
//   }
//
//   //Solicitudes Vencidas.
//   if (frm.iCveEstado.value == 1){
//     if (frm.hdFiltro.value != "")
//       frm.hdFiltro.value = frm.hdFiltro.value +
//                            " and TRARegSolicitud.dtLimiteEntregaDocs < (current date) or TRARegSolicitud.labandonada=1 ";
//     else
//       frm.hdFiltro.value = " TRARegSolicitud.dtLimiteEntregaDocs < (current date) or TRARegSolicitud.labandonada=1 ";
//    frm.hdFiltro.value = frm.hdFiltro.value + " and TRARegTramXSol.dtCancelacion is null";
//   }
//
//   //Solicitudes Canceladas.
//   if (frm.iCveEstado.value == 2){
//     if (frm.hdFiltro.value != "")
//       frm.hdFiltro.value = frm.hdFiltro.value +
//                            " and TRARegTramXSol.dtCancelacion is not null";
//     else
//       frm.hdFiltro.value = " TRARegTramXSol.dtCancelacion is not null";
//   } //ORIGINAL
   
 //Solicitudes en proceso.
   if (frm.iCveEstado.value == 0){
     if (frm.hdFiltro.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value +
       " and TRARegSolicitud.DTRESOLTRAM is null AND TRARegSolicitud.LABANDONADA = 0 AND TRARegSolicitud.LPERMISO is null and TRARegTramXSol.dtCancelacion is null";
     else
       frm.hdFiltro.value = " TRARegSolicitud.DTRESOLTRAM is null AND TRARegSolicitud.LABANDONADA = 0 AND TRARegSolicitud.LPERMISO is null and TRARegTramXSol.dtCancelacion is null";
   }

   //Solicitudes ABANDONADAS.
   if (frm.iCveEstado.value == 1){
     if (frm.hdFiltro.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.LABANDONADA = 1";
     else
       frm.hdFiltro.value = " TRARegSolicitud.LABANDONADA = 1";
   }

   //Solicitudes Canceladas.
   if (frm.iCveEstado.value == 2){
     if (frm.hdFiltro.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegTramXSol.dtCancelacion is not null";
     else
       frm.hdFiltro.value = " TRARegTramXSol.dtCancelacion is not null";
   }
   
   //Solicitudes OTORGADAS.
   if (frm.iCveEstado.value == 3){
     if (frm.hdFiltro.value != "")
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.DTRESOLTRAM is NOT null AND TRARegSolicitud.LABANDONADA = 0 AND TRARegSolicitud.LPERMISO = 1 and TRARegTramXSol.dtCancelacion is null AND LRESOLUCIONPOSITIVA=1";
     else
       frm.hdFiltro.value = " TRARegSolicitud.DTRESOLTRAM is NOT null AND TRARegSolicitud.LABANDONADA = 0 AND TRARegSolicitud.LPERMISO = 1 and TRARegTramXSol.dtCancelacion is null AND LRESOLUCIONPOSITIVA=1";
   }
   

//   if (frm.hdFiltro.value != "")
//     frm.hdFiltro.value = frm.hdFiltro.value +
//                          " and TRARegSolicitud.dtEntrega is null";
//   else
//     frm.hdFiltro.value = " TRARegSolicitud.dtEntrega is null";

//   frm.hdOrden.value =  " TRARegSolicitud.dtLimiteEntregaDocs";
   frm.hdOrden.value =  " TRARegSolicitud.inumsolicitud ";
   frm.hdNumReg.value =  10000;
   fEngSubmite("pgTRARegTramXSol.jsp","Listado");
 }

 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iCveUsuario,iVentUnica){
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
   
   
   if(cId=="cIdCompFol" && cError==""){
	   fEjecutaReporte();
   }
   
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
     frm.iCveUsuario.value = iCveUsuario;
     cVentUnica=iVentUnica;
     if (aRes.length<=0)
       FRMPanel.fSetTraStatus("disabled");
  //   FRMListado.fSelReg(0);
   }
   if(cId == "CIDMotivo" && cError == ""){
     for(i=0;i<aRes.length;i++){
       if (aRes[i][3] == 1){
         aVentanillaUnica[aVentanillaUnica.length] = aRes[i];
       }
       if (aRes[i][4] == 1){
         aPorArea[aPorArea.length] = aRes[i];
       }
       aCompleto[aCompleto.length] = aRes[i];
     }

     fFillSelect(frm.iCveMotivoCancela,aRes,true,frm.iCveMotivoCancela.value,0,1);
     fTraeFechaActual();
//     fSelectSetIndexFromValue(frm.iCveMotivoCancela, frm.hdCancela.value);
   }
   if(cId == "idFechaActual" && cError == ""){
     frm.iEjercicioFiltro.value = aRes[1][2];
   }
   if(cId == "Estado"){
       fFillSelect(frm.iCveEstado,aRes,false,frm.iCveEstado.value,0,1);
   }

   fResOficDeptoUsr(aRes,cId,cError);

   if(cId == "CIDOficinaDeptoXUsrTodas" && cError == ""){
     fFillSelect(frm.iCveDeptoUsr,aRes,false,frm.iCveDeptoUsr.value,0,2);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
/* function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iEjercicio,iNumSolicitud,");
    fDisabled(false,"iEjercicio,iNumSolicitud,iCveEstado,","--");
    FRMListado.fSetDisabled(true);
 }*/
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
/* function fGuardar(){
    if(fValidaTodo()==true){
      FRMPanel.fSetTraStatus("UpdateComplete");
      fDisabled(true,"iCveEstado,iEjercicio,iNumSolicitud,dtInicio,dtFin,");
      FRMListado.fSetDisabled(false);
    }
}*/
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
  if(frm.iEjercicio.value != "" && frm.iNumSolicitud.value != 0 && frm.iCveMotivoCancela.value > 0){
     if(confirm("¿Desea usted cancelar la solicitud " + frm.iNumSolicitud.value + " del ejercicio " + frm.iEjercicio.value+"?")){ //" y todas las solicitudes relacionadas a esta?")){
       if(fValidaTodo()==true){
          fNavega();
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
     } else return;
  }else{
    fAlert("\n-Seleccione una Solicitud para cancelar, además de indicar el motivo de cancelación.");
  }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fDisabled(false,"iCveEstado,iEjercicioFiltro,iNumSolicitudFiltro,iEjercicio,iNumSolicitud,dtInicio,dtFin,");
     frm.iCveEstado.disabled = true;
     frm.iEjercicioFiltro.disabled = true;
     frm.iNumSolicitudFiltro.disabled = true;
     frm.iEjercicio.disabled = true;
     frm.iNumSolicitud.disabled = true;
     frm.dtInicio.disabled = true;
     frm.dtFin.disabled = true;
     FRMListado.fSetDisabled(true);
     if (cVentUnica == "1")
       fFillSelect(frm.iCveMotivoCancela,aVentanillaUnica,true,frm.iCveMotivoCancela.value,0,1);
     else
       fFillSelect(frm.iCveMotivoCancela,aPorArea,true,frm.iCveMotivoCancela.value,0,1);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Mod,");
    else
      FRMPanel.fSetTraStatus("disabled");
      fDisabled(false);
    fDisabled(true,"iCveEstado,iEjercicioFiltro,iNumSolicitudFiltro,iCveOficinaUsr,iCveDeptoUsr,dtInicio,dtFin,lMuestraDeptoBOX,");
    FRMListado.fSetDisabled(false);
    FRMListado.focus();
    //fFillSelect(frm.iCveMotivoCancela,aCompleto,true,frm.iCveMotivoCancela.value,0,1);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
/* function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
*/
 function fReporte(){
	 fCompruebaFolio();
 }
 
 function fEjecutaReporte(){
	  cClavesModulo="2,";
	  cNumerosRep="12,";
	  cFiltrosRep= frm.iEjercicio.value + "," + frm.iNumSolicitud.value + "," + cSeparadorRep;
	  fReportes();
	 
 }

 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
//   fSelectSetIndexFromValue(frm.iCveMotivoCancela, -1);
    if(aDato){
       frm.iCveModalidad.value = aDato[7];
       frm.iCveTramite.value = aDato[6];
       frm.iEjercicio.value = aDato[0];
       frm.iNumSolicitud.value = aDato[1];
       frm.cObs.value = aDato[9];
       frm.hdCancela.value = aDato[10];
//     fSelectSetIndexFromValue(frm.iCveMotivoCancela, aDato[10]);

       if (frm.hdCancela.value > 0)
          fSelectSetIndexFromValue(frm.iCveMotivoCancela, aDato[10]);
       else
          fSelectSetIndexFromValue(frm.iCveMotivoCancela, -1);

       if (aDato[8] != ""){
          FRMPanel.fSetTraStatus("disabled");
          cancelada=true;
       }
       else{
          FRMPanel.fSetTraStatus("Mod,");
          cancelada=false;
       }
    }else{
      frm.iEjercicio.value = "";
      frm.iNumSolicitud.value = "";
      fSelectSetIndexFromValue(frm.iCveMotivoCancela, -1);
      frm.cObs.value = "";
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
 function fOnChange(){
//    fAlert("" + frm.iCveEstado.value);
 }

 function fSetSolicitud(iEjercicio,iNumSolicitud,iCveOficinaUsr,iCveDeptoUsr){
   frm.iEjercicio.value = iEjercicio;
   frm.iEjercicioFiltro.value = iEjercicio;
   frm.iNumSolicitud.value = iNumSolicitud;
   frm.iNumSolicitudFiltro.value = iNumSolicitud;
   frm.iCveOficinaUsr.value = iCveOficinaUsr;
   frm.iCveDeptoUsr.value = iCveDeptoUsr;
   fNavega();
 }
 function fCargaMotivos(){
   frm.hdFiltro.value = "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  10000;
   fEngSubmite("pgGRLMotivoCancela.jsp","CIDMotivo");
 }

 function fDeptoUsrOnChangeLocal(){
 }

 function fDefOficXUsr(lTodas,lRenglonIntermedio,lSeleccione){
  if(!lTodas)
    lTodas = false;
  if(!lSeleccione)
    lSeleccione = false;
  //Necesario definir los campos ocultos  Hidden("fResultado "); y Hidden("hdLlave");
  var cTx;
  cTx = ITRTD("EEtiquetaC",0,"100%","20","center")+
      InicioTabla("",0,"","","center")+
        ITR()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Oficina:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle");
  cTx += Select("iCveOficinaUsr","fOficinaUsrOnChangeValidaDeptos();");
  cTx+=   FTD();
  if(lRenglonIntermedio)
    cTx += FITR();
  cTx +=  ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Departamento:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveDeptoUsr","if(fDeptoUsrOnChange)fDeptoUsrOnChange();")+
          FTD()+
        FTR()+
      FinTabla();
    if(cPermisosPag == 1){

     cTx += InicioTabla("",0,"","","center")+
        ITR()+
          TDEtiCheckBox("EEtiqueta",0," Mostrar todos los Departamentos:","lMuestraDeptoBOX","0",true,"Muestra todos los departamentos asignados a la oficina seleccionada","","onClick=fCargaDeptos();") +
        FTR()+
      FinTabla();
    }
    cTx += FTDTR();
  return cTx;
}
function fCargaDeptos(){
  if(frm.lMuestraDeptoBOX){
    if(frm.lMuestraDeptoBOX.checked)
      fCargaTodosDeptos();
  }
  else
    fOficinaUsrOnChange(false,false);
}
function fCargaTodosDeptos(){
  frm.hdLlave.value = "GRLDeptoXOfic.iCveOficina,GRLDeptoXOfic.iCveDepartamento";
  frm.hdSelect.value = "SELECT GRLDeptoXOfic.iCveDepartamento, "+
                           "GRLDepartamento.cDscDepartamento, GRLDepartamento.cDscBreve as cDscBreveDepto "+
                           "FROM GRLDeptoXOfic "+
                           "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLDeptoXOfic.iCveDepartamento "+
                           "WHERE GRLDeptoXOfic.iCveOficina = " + frm.iCveOficinaUsr.value +
                           " and  GRLDeptoXOfic.iCveDepartamento >= 0 "+
                           "ORDER BY cDscBreveDepto";
  frm.hdNumReg.value = 100000;
  fEngSubmite("pgConsulta.jsp","CIDOficinaDeptoXUsrTodas");
}
function fOficinaUsrOnChangeValidaDeptos(){
  fCargaDeptos();
}


function fCompruebaFolio(iEjer, iNumSol){
	if(iEjer!=""&&iNumSol!=""&&cancelada==true){
	 cancelada=false;
	 frm.hdBoton.value = "compFolCancel";
	 frm.hdFiltro.value =  "";
	 frm.hdOrden.value =  "";
	 frm.hdNumReg.value =  1000;
	 fEngSubmite("pgTRARegTramXSol.jsp","cIdCompFol");
	}else{
		fAlert("Para generar el reporte debe seleccionar una solicitud con fecha de cancelación.");
	}
}
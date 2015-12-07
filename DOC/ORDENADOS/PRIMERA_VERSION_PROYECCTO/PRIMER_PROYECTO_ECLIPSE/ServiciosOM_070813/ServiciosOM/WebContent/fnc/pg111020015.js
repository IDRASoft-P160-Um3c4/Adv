// MetaCD=1.0
 // Title: pg111020015.js
 // Description: JS "Cat�logo" de la entidad TRARegSolicitud
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: ocastrejon
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   //cPaginaWebJS = "pg111020015.js";
   cPaginaWebJS = "pg" + iNDSADM + "1020015.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "T�TULO NO ENCONTRADO")?"B�SQUEDA DE SOLICITUD":cTitulo;
   fSetWindowTitle();

 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("ETablaInfo",0,"100%","","center","","1");
     ITRTD("ETablaST",0,"100%","","center");
       fTituloEmergente(cTitulo);
     FTDTR();

     ITRTD("",2,"","","center");
       fDefOficXUsr(false,true);
     FTDTR();
     ITRTD("",0,"","","center");
       InicioTabla("",0,"","","center","","1");
         ITRTD("",0,"","","center");
           TDEtiSelect(false,"EEtiqueta",0,"Tr�mite:","iCveTramiteFiltro","","",7);
         FTDTR();
         ITRTD("",0,"","","center");
           TDEtiCampo(false,"EEtiqueta",0,"RFC Inicie con:","cRfc","",13,13,"RFC","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicioFiltro","",5,5,"Ejercicio","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"No. Solicitud:","iNumSolicitudFiltro","",8,8,"N�m. Solicitud","fMayus(this);");
           ITD("",0,"","","Right","Center");
             BtnImg("Buscar","lupa","fBuscar();");
           FTD();
           //ITD("",1,"","","center");
           //  BtnImg("Buscar","buscar","fBuscar();","Buscar");
           //FTD();
         FTDTR();
       FinTabla();
     FTDTR();
   FinTabla();

   InicioTabla("",0,"90%","","center","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple("Busqueda de Solicitudes");
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
       FTDTR();
       ITRTD("",0,"","20","center","top");
         Liga("Seleccionar","fSeleccionar();","Selecci�n de la Solicitud");
       FTDTR();
       FinTabla();
     FTDTR();
     FinTabla();
     Hidden("iCveUsuario","");
     Hidden("iEjercicio","");
     Hidden("iNumSolicitud","");
     Hidden("hdLlave","");
     Hidden("hdSelect","");
     Hidden("cTramite");
   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Ejercicio,Solicitud,Tr�mite,Modalidad,RFC,Etapa,Bien,");
   FRMListado.fSetCampos("0,1,4,6,9,21,19,");
   frm.hdBoton.value="Primero";
   fCargaOficDeptoUsr(false);

 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   frm.hdFiltro.value = '';
   frm.hdOrden.value =  "TRARegSolicitud.iEjercicio DESC,TRARegSolicitud.iNumSolicitud DESC"; //FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  "10000"; //FRMFiltro.fGetNumReg();

   if (frm.iEjercicioFiltro.value != ""){
     if (frm.hdFiltro.value != "")
	frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.iEjercicio = " + frm.iEjercicioFiltro.value;
     else
       frm.hdFiltro.value = " TRARegSolicitud.iEjercicio = " + frm.iEjercicioFiltro.value;
   }

   if (frm.iNumSolicitudFiltro.value != ''){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
     else
       frm.hdFiltro.value = " TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitudFiltro.value;
   }

   if (frm.hdFiltro.value != "")
	frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.lImpreso = 1 " ;
   else
       frm.hdFiltro.value = " TRARegSolicitud.lImpreso = 1 ";


   if (frm.iCveTramiteFiltro.value != "" && frm.iCveTramiteFiltro.value != "-1"){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and TRARegSolicitud.iCveTramite = " + frm.iCveTramiteFiltro.value;
     else
       frm.hdFiltro.value = " TRARegSolicitud.iCveTramite = " + frm.iCveTramiteFiltro.value;
   }
   else {
     if (frm.iCveDeptoUsr.value != iCveDeptoVentanillaUnica) {
     var cTramites="";
     if(top.opener){
      if(top.opener.fGetTramites){
        cTramites = top.opener.fGetTramites();
        if (frm.hdFiltro.value != '')
           frm.hdFiltro.value = frm.hdFiltro.value + " and TRARegSolicitud.icvetramite IN (" + cTramites + ")";
        else
           frm.hdFiltro.value = frm.hdFiltro.value + " TRARegSolicitud.icvetramite IN (" + cTramites + ")";
      }
     }
    }
   }


   if (frm.iCveOficinaUsr.value != ''){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and trexmt.iCveOficina = " + frm.iCveOficinaUsr.value;
     else
       frm.hdFiltro.value = " trexmt.iCveOficina = " + frm.iCveOficinaUsr.value;
   }

   if (frm.iCveDeptoUsr.value != ''){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and trexmt.iCveDepartamento = " + frm.iCveDeptoUsr.value;
     else
       frm.hdFiltro.value = " trexmt.iCveDepartamento = " + frm.iCveDeptoUsr.value;
   }

   if (frm.cRfc.value != ''){
     if (frm.hdFiltro.value != '')
       frm.hdFiltro.value = frm.hdFiltro.value +
                            " and GRLPERSONA.CRFC like '"+frm.cRfc.value +"%'";
     else
       frm.hdFiltro.value = " GRLPERSONA.CRFC like '"+frm.cRfc.value +"%'";
   }

   if (frm.iCveOficinaUsr.value != '' && frm.iCveDeptoUsr.value != '')
     return fEngSubmite("pg111020015A1.jsp","Listado");
 }
 // RECEPCI�N de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existi� un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existi� un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cError!="")
     FRMFiltro.fSetNavStatus("Record");

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
   }

   fResOficDeptoUsr(aRes,cId,cError,false);

   if(cId == "idTramite" && cError==""){
    for (var i=0; i< aRes.length;i++){
         aRes[i][6] = aRes[i][5] + " - " + aRes[i][4];
      }
    fFillSelect(frm.iCveTramiteFiltro,aRes,true,0,2,6);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
 function fNuevo(){
    //FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iEjercicioFiltro,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
          //FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         //FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicio,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
 function fCancelar(){
    fDisabled(false);
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un rengl�n
 function fSelReg(aDato){
    frm.iEjercicio.value = aDato[0];
    frm.iNumSolicitud.value = aDato[1];
    frm.cTramite.value = aDato[4];
    //frm.iCveTramite.value = aDato[2];
 }
 // FUNCI�N donde se generan las validaciones de los datos ingresados
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


 function fCargaTramite(){
   if (frm.iCveOficinaUsr.value != "" && frm.iCveDeptoUsr.value != ""){
     frm.hdFiltro.value =  " TRATRAMITEXOFIC.iCveOficinaResuelve = " + frm.iCveOficinaUsr.value ;
      if (frm.iCveDeptoUsr.value != iCveDeptoVentanillaUnica) {
          frm.hdFiltro.value =  frm.hdFiltro.value + " AND TRATRAMITEXOFIC.iCveDeptoResuelve = " + frm.iCveDeptoUsr.value;

	  var cTramites="";
	  if(top.opener){
	      if(top.opener.fGetTramites){
        	cTramites = top.opener.fGetTramites();
	        if (frm.hdFiltro.value != '')
	           frm.hdFiltro.value = frm.hdFiltro.value + " and TRATRAMITEXOFIC.icvetramite IN (" + cTramites + ")";
	        else
        	   frm.hdFiltro.value = frm.hdFiltro.value + " TRATRAMITEXOFIC.icvetramite IN (" + cTramites + ")";
	      }
	  }
      }
      frm.hdOrden.value =  " TRACATTRAMITE.CCVEINTERNA ";
      frm.hdNumReg.value =  "10000";
      fEngSubmite("pgGRLUsuarioXOficA2.jsp","idTramite");


   }
 }
 function fBuscar(){
   fNavega();
 }
 function fSeleccionar(){

   if (frm.iEjercicio.value != '' && frm.iNumSolicitud.value != ''){
     top.opener.fSetSolicitud(frm.iEjercicio.value,frm.iNumSolicitud.value,frm.iCveOficinaUsr.value,frm.iCveDeptoUsr.value,frm.cTramite.value);
     self.focus();
     top.close();
   } else {
     alert('Debe Seleccionar Primero la Solicitud');
   }
 }


 function fDeptoUsrOnChange(){
   fCargaTramite();
 }

 function fOficinaUsrOnChangeLocal(){
   fCargaTramite();
 }

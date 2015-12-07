// MetaCD=1.0
 // Title: pg110020021.js
 // Description: JS Regsitro del PNC
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: ABarrientos
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var cveTramite = 0;
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020021.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");

       ITRTD("",0,"","0","center","top");
         IFrame("IListado","0","0","Listado.js","yes",true);
       FTDTR();
       fDefOficXUsr(false);

       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"60%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Procesos, Productos y Causas");
           FTDTR();
           ITR();

           AreaTexto(false,100,4,"ATTramyReq");

           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Procesos:","iCveProceso","fTraeProductos();");
           FTR();
           ITR();
              TDEtiSelect(true,"EEtiqueta",0,"Productos:","iCveProducto","fTraeCausas();");
           FTR();
           ITR();
              TDEtiSelect(true,"EEtiqueta",0,"Causas:","iCveCausaPNC","");
           FITR();
             BR();
           FITR();
             ITD("EEtiquetaC",2,"","50");
               LigaNombre("Registrar Producto No Conforme","fRegistraPNC();","Registra el producto no conforme","RegistraPNC");
             FTD();
           FTR();
           FITR();
             ITD("EEtiquetaC",2,"","20");
               BtnImgNombre("vgcerrar","aceptar","fCierra();","");
             FTD();
           FTR();

         FinTabla();
       FTDTR();

       FinTabla();
     FTDTR();
       ITRTD("",0,"","0","center","bottom");
         IFrame("IPanel","0%","0","Paneles.js");
       FTDTR();
     FinTabla();
     Hidden("iCveUsuario","1");
     Hidden("hdLlave","");
     Hidden("hdSelect","");
     //Cambiarian a combos si se desea que se seleccionen de la pantalla
     Hidden("iCveOficinaAsignado","0");
     Hidden("iCveDeptoAsignado","0");
     Hidden("iCveUsuRegistro",fGetIdUsrSesion());
     Hidden("hdNumSol","");
     Hidden("hdEjercicio","");
     Hidden("hdRequisito","");
     Hidden("iCveUsuCorrige","0");
     Hidden("lResuelto","0");
     Hidden("cDscOtraCausa","");
     Hidden("iCveRequisito","");
     Hidden("iEjercicio","");
     Hidden("iNumSolicitud","");
     Hidden("iCveTramite","");
     Hidden("iCveModalidad","");

   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Oficina,Departamento,ClaveProceso,Producto,Inicio Asignaci�n,ClaveFormatoSalida,");

   frm.hdBoton.value="Primero";
   fCargaOficDeptoUsr(false);

 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
   //alert("hdboton..."+frm.hdBoton.value);
   frm.hdNumReg.value =  10000;
   fEngSubmite("pgGRLRegistroPNC.jsp","RegistraPNC");
   frm.hdBoton.value = "";
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

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     //FRMFiltro.fSetNavStatus(cNavStatus);
   }

   if(cId == "Procesos" && cError==""){
     fFillSelect(frm.iCveProceso,aRes,true,frm.iCveProceso.value);
     fTraeProductos();
   }

   if(cId == "Productos" && cError==""){
     fFillSelect(frm.iCveProducto,aRes,true,frm.iCveProducto.value,3,4);
     fTraeCausas();
   }
   if(cId == "Causas" && cError==""){
     fFillSelect(frm.iCveCausaPNC,aRes,true,frm.iCveCausaPNC.value,1,2);
   }

   if(cId == "RegistraPNC"){
      var msg="";
      if(frm.hdNumSol.value!="")
        msg = "Se Registro Producto No Conforme para la solicitud " + frm.hdNumSol.value +"\n";
      if(frm.hdEjercicio.value != "")
        msg+="del Ejercicio "+frm.hdEjercicio.value+"\n";
      if(frm.hdRequisito.value!="");
        msg+="del requisito "+ frm.hdRequisito.value;
      fAlert(msg);
   }


   fResOficDeptoUsr(aRes,cId,cError,false);

 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveOficina,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
 function fGuardar(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveOficina,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
 function fCancelar(){
    //FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");
    fDisabled(true);
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
    frm.iCveOficinaUsr.value = aDato[0];
    frm.iCveDeptoUsr.value = aDato[1];
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

 function fTraeProcesos(){
   if (top.opener)
     if(top.opener.fObtieneValoresTOP)
        top.opener.fObtieneValoresTOP(this);
 }

 function fDeptoUsrOnChangeLocal(){
   fTraeProcesos();
 }

 function fOficinaUsrOnChangeLocal(){
   fDeptoUsrOnChangeLocal();
 }

 function fTraeProductos(){
     frm.hdBoton.value = "Productos";
     frm.hdFiltro.value =  " GRLPRODXOFICDEPTO.ICVEOFICINA = " + frm.iCveOficinaUsr.value +
                           " AND GRLPRODXOFICDEPTO.ICVEDEPARTAMENTO = " + frm.iCveDeptoUsr.value +
                           " AND GRLProdXOficDepto.iCveProceso = " + frm.iCveProceso.value;
     fEngSubmite("pgGRLProdXOficDeptob.jsp","Productos");

 }

 function fTraeCausas(){
   frm.hdBoton.value = "Causas";
   frm.hdFiltro.value = "GRLCausaPNC.iCveProducto = " + frm.iCveProducto.value;
   fEngSubmite("pgGRLCausaPNC.jsp","Causas");
 }

 function fRecibeValoresTOP(iCveTramite,iRequisito,iCveModalidad,cDscTramite,cDscModalidad,cDscRequisito,iNumsolicitud,iEjercicio){
   cveTramite = iCveTramite;

   frm.iCveRequisito.value = iRequisito;
   frm.iEjercicio.value = iEjercicio;
   frm.iNumSolicitud.value = iNumsolicitud;
   frm.iCveTramite.value   = iCveTramite;
   frm.iCveModalidad.value = iCveModalidad;

   frm.ATTramyReq.value = " Registro de Producto No Conforme ";
   if(iNumsolicitud!=""){
     frm.ATTramyReq.value += " para la solicitud " + iNumsolicitud;
     frm.hdNumSol.value  = iNumsolicitud;
   }
   if(iEjercicio!=""){
     frm.ATTramyReq.value += " del Ejercicio "+ iEjercicio;
     frm.hdEjercicio.value = iEjercicio;
   }
   if(cDscRequisito!=""){
     frm.ATTramyReq.value += " del Requisito "+ cDscRequisito;
     frm.hdRequisito.value = cDscRequisito;
   }
   frm.ATTramyReq.value +=
  frm.ATTramyReq.disabled = true;

   //Si esta pantalla fue abierta de otra talvez recibio la clave del tr�mite entonces busca los procesos de acuerdo al tr�mite
   if(parseInt(cveTramite,10)>0){
     frm.hdFiltro.value =  "GRLProceso.iCveTramite = " + cveTramite;
     fEngSubmite("pgGRLProcesob.jsp","Procesos");
   }
   else{
     frm.hdBoton.value = "Procesos";
     frm.hdFiltro.value =  " GRLPRODXOFICDEPTO.ICVEOFICINA = " + frm.iCveOficinaUsr.value +
                           " AND GRLPRODXOFICDEPTO.ICVEDEPARTAMENTO = " + frm.iCveDeptoUsr.value ;
     fEngSubmite("pgGRLProdXOficDeptob.jsp","Procesos");

   }

 }

 function fRegistraPNC(){
   if(parseInt(frm.iCveProceso.value,10)>0 && parseInt(frm.iCveProducto.value,10)>0 && parseInt(frm.iCveCausaPNC.value,10)>0){
     frm.hdBoton.value = "RegistraPNC";
     fNavega();
     if(top.opener)
       if(top.opener.fNavega)
       top.opener.fNavega();
   }
   else{
     fAlert("Seleccione el proceso, el producto y la causa");
     return;
   }
 }

 function fCierra(){
   top.close();
 }








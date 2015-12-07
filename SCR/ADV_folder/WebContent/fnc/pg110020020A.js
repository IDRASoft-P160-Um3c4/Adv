// MetaCD=1.0
 // Title: pg110020020
 // Description: JS "Catálogo" de la entidad GRLRegistroPNC
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Levi Equihua López<dd>Rafael Miranda Blumenkron

 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var idUser = fGetIdUsrSesion();
 var aOficinaDeptoUsrAsg, aOficinaUsrAsg;
 var lOtrasCausas = false;
 var lHabTodas = false;
 var lExisteSol = false;
 var lGuardar = false;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020020A.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
     fSetWindowTitle();
   }
   if(fGetPermisos("pg110020020p.js") == 2)
     lHabTodas = false;
   else
     lHabTodas = true;

 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","0");
     if (top.opener){
       ITRTD("ETablaST",0,"100%","","top");
         fTituloEmergente(cTitulo,false,cPaginaWebJS);
       FTDTR();
     }else{
        ITRTD("ESTitulo",0,"100%","20","center");
        fTituloCodigo(cTitulo,cPaginaWebJS);
        FTDTR();
     }
     ITRTD("",0,"","","center","top");
       InicioTabla("ETablaInfo",0,"75%","","","",1);
         
         ITRTD("",4);
           InicioTabla("",0);
           FinTabla();
         FTDTR();
         ITR();
         	TDEtiCampo(false,"EEtiqueta",0,"Usuario:","cNomUsuario","",80,50,"Usuario","fMayus(this);","","","","ECampo",5);
         FTR();
         ITR();
           TDEtiSelect(false,"EEtiqueta",0,"Proceso:","iCveProceso","fCargaProducto();");
           TDEtiSelect(false,"EEtiqueta",0,"Producto:","iCveProducto","fNavega();");
         FTR();
       FinTabla();
     FTDTR();

     ITRTD("ETablaST",0,"95%","","center","top");
       TextoSimple("CAUSAS COMUNES PARA REQUISITO NO VÁLIDO");
     FTDTR();
     ITRTD("",0,"","","center","top");
       IFrame("IListado20","95%","170","Listado.js","yes",true);
     FTDTR();

     ITRTD("",0,"100%","","center","top");
       InicioTabla("ETablaInfo",0,"75%","","","",1);
         ITR("EEtiqueta",0,"","","center");
            TDEtiAreaTexto(false,"EEtiqueta",0,"Observacion:",100,3,"cDscOtraCausa","","Descripción de otra causa ajena a las comunes","","fMayus(this);",'onkeydown="fMxTx(this,500);"',false,true,true,"ECampo",0);
         FTR();
       FinTabla();
     FTDTR();
     ITR();
        InicioTabla("",0,"75%","","center");
          ITRTD("",0,"","40","center","bottom");
             IFrame("IPanel","95%","34","Paneles.js");
          FTDTR();
        FinTabla();
      FTR();
   FinTabla();
     Hidden("hdLlave");
     Hidden("hdSelect");

     Hidden("iOficina","");
     Hidden("iDepto","");
     Hidden("iOficinaEnvia","");
     Hidden("iDeptoEnvia","");

     Hidden("iProceso","");
     Hidden("iConsec",0);

     Hidden("iEjercicioPNC");
     Hidden("iConsecutivoPNC");

     Hidden("iCveEtapa");
     Hidden("iOrden");
     Hidden("iRecepcion");
     Hidden("iCveVentanillaU");
 
     Hidden("cOtrasCausas");
     Hidden("iEjer","");
     Hidden("iNumSol","");
     Hidden("iCveTram","");
     Hidden("iCveModal","");
     Hidden("iCveRequi","");
     Hidden("CveUser",idUser);
     Hidden("iCveCausaPNC","");
     Hidden("hdBotonAux","");
     
     
   fFinPagina();

 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
	 
   frm = document.forms[0];
   frm.iDepto.value="";
   FRMListado = fBuscaFrame("IListado20");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Seleccione,Descripción de la Causa,");
   FRMListado.fSetCampos("1,");
   FRMListado.fSetObjs(0,"Caja");
   FRMListado.fSetAlinea("center,left,");
   frm.hdBoton.value="Primero";
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(false);
   FRMPanel.fShow("Tra,");
   FRMPanel.fSetTraStatus("Sav,");
   if (top.opener){
	   fRecibeValores();
   }
   
   fCargaUsuario();

   frm.hdLlave.value ="";
   frm.hdSelect.value = "select GRLPROCESO.ICVEPROCESO, GRLPROCESO.CDSCPROCESO from GRLPROCESO where GRLPROCESO.ICVEPROCESO in ("+
		   " SELECT GRLPRODXOFICDEPTO.ICVEPROCESO FROM GRLPRODXOFICDEPTO where GRLPRODXOFICDEPTO.ICVEOFICINA"+
		   " in (SELECT TRAREQUISITO.ICVEOFICINAEVAL FROM TRAREQUISITO where TRAREQUISITO.ICVEREQUISITO="+frm.iCveRequi.value+")"+ 
		   " and GRLPRODXOFICDEPTO.ICVEDEPARTAMENTO"+ 
		   " in (SELECT TRAREQUISITO.ICVEDEPTOEVAL FROM TRAREQUISITO where TRAREQUISITO.ICVEREQUISITO="+frm.iCveRequi.value+"))";
   
   fEngSubmite("pgConsulta.jsp","CIDProceso");
   
   frm.cNomUsuario.disabled=true;
   frm.iCveProceso.disabled=true;
   frm.iCveProducto.disabled=true;
 }

 
 // LLAMADO al JSP específico para obtener datos del usuario
 function fCargaUsuario(){
   
   frm.hdLlave.value = "ICVEUSUARIO";
   frm.hdSelect.value = "SELECT CNOMBRE||' '|| CAPPATERNO||' '|| CAPMATERNO AS cNOMBREUSU FROM SEGUsuario" +
   " where ICVEUSUARIO="+idUser;
   fEngSubmite("pgConsulta.jsp","cNomUsuario");
 }
  // LLAMADO al JSP para obtener datos del proceso segun oficina y depto
 function fCargaProceso(){
   frm.hdLlave.value = "ICVEOFICINA,ICVEDEPARTAMENTO";
   frm.hdSelect.value = "SELECT DISTINCT(GRLPRODXOFICDEPTO.ICVEPROCESO), " +
   "CDSCPROCESO, ICVEOFICINA, ICVEDEPARTAMENTO FROM GRLPRODXOFICDEPTO " +
   "JOIN GRLPROCESO ON GRLPRODXOFICDEPTO.ICVEPROCESO = GRLPROCESO.ICVEPROCESO " +
   "WHERE ICVEOFICINA = " + frm.iCveOficinaUsr.value +
   " AND ICVEDEPARTAMENTO = " + frm.iCveDeptoUsr.value;
   fEngSubmite("pgConsulta.jsp","cIdProceso");
 }
 
  // LLAMADO al JSP para obtener datos del Producto según oficina, depto y proceso
 function fCargaProducto(){

	 frm.hdLlave.value = "";
    
     frm.hdSelect.value = "SELECT GRLPRODUCTO.ICVEPRODUCTO, GRLPRODUCTO.CDSCBREVE FROM GRLPRODUCTO where GRLPRODUCTO.ICVEPRODUCTO in ("+
    		 " SELECT GRLPRODXOFICDEPTO.ICVEPRODUCTO FROM GRLPRODXOFICDEPTO where GRLPRODXOFICDEPTO.ICVEOFICINA in"+ 
    		 " (select TRAREQUISITO.ICVEOFICINAEVAL from TRAREQUISITO where TRAREQUISITO.ICVEREQUISITO ="+frm.iCveRequi.value+") and"+
    		 " GRLPRODXOFICDEPTO.ICVEDEPARTAMENTO in"+
    		 " (select TRAREQUISITO.ICVEDEPTOEVAL from TRAREQUISITO where TRAREQUISITO.ICVEREQUISITO ="+frm.iCveRequi.value+") and"+
    		 " GRLPRODXOFICDEPTO.ICVEPROCESO ="+frm.iCveProceso.value+")";
     
     fEngSubmite("pgConsulta.jsp","CIDProducto");
 }
 
  // LLAMADO al JSP específico para la navegación de la página
  function fNavega(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdSelect.value = "SELECT ICVECAUSAPNC, CDSCCAUSAPNC FROM GRLCAUSAPNC " +
   "WHERE ICVEPRODUCTO = " + frm.iCveProducto.value +
   " AND ICVECAUSAPNC > 0 order by cDscCausaPNC";
   
   fEngSubmite("pgConsulta.jsp","Listado");
 }

 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iNConsecutivo){
	 
	 if(cError=="Guardar")
		 fAlert("Existió un error en el Guardado!");
	 
   
	// fResOficDeptoUsr(aRes,cId,cError,frm.lVerTodosBOX.checked);
	 
    if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     aResListado = fCopiaArregloBidim(aRes);
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     FRMListado.fSetDisabled(false);
   }
   
   if(cId == "Listado01A"){
	   if(top.opener){
		   	top.opener.fNavega();
		   	top.close();
	   }
   }
   
  
   if(cId == "cNomUsuario" && cError==""){
     frm.cNomUsuario.value=aRes[0][0];
     frm.CveUser.value = idUser;
   }
   
   
   if(cId == "CIDProceso" && cError==""){
     fFillSelect(frm.iCveProceso,aRes,false,frm.iCveProceso.value,0,1);
     fCargaProducto();
   }
   
   
   if(cId == "CIDProducto" && cError==""){
     fFillSelect(frm.iCveProducto,aRes,false,frm.iCveProducto.value,0,1);
     fNavega();
   }
 }

  // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
	 
   if(FRMListado.fGetLength() == 0){
     fAlert("Configure causas de Producto no Conforme para los procesos y productos indicados ");
   }else{
      if(!top.opener){
         if(frm.iNumSolicitud.value != "")
            fBuscaSolicitud();
         else
            fGuardaDatos();
      }else{
        fGuardaDatos();
      }
   }
 }

 function fGuardaDatos(){
	 fRegEvaCausaXArea();
 }
 
 function fRegEvaCausaXArea(){
	  var cont;
	  var selElementos=false;
	   aCausas = FRMListado.fGetObjs(0);
	   frm.iCveCausaPNC.value="";
	   
	   
	   for(cont=0;cont < aCausas.length;cont++)
	       if(aCausas[cont] == true){
	    	   selElementos=true;
	       }
	    	   
	   if(selElementos==true){ 
	   
		   for(cont=0;cont < aCausas.length;cont++)
		       if(aCausas[cont] == true)
		         frm.iCveCausaPNC.value = (frm.iCveCausaPNC.value == "")?aResListado[cont][0]:frm.iCveCausaPNC.value+","+aResListado[cont][0];
		
		   if(confirm("¿Desea guardar la información en pantalla?")){
			   frm.hdBoton.value = "GuardarMultXArea";
			   frm.hdBotonAux.value = "VERIFICACIONxAREA";
			   fEngSubmite("pgVerificacionArea.jsp","Listado01A");
		   }
	 }
	 else{
		 fAlert("\n\nDebe selccionar al menos una causa para el requisito no válido");
	 }
	}

 
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
//    frm.iEjercicio.value = aDato[0];
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


function fRecibeValores(){
	
  frm.iEjer.value = top.opener.fGethdEjercicio();
  
  frm.iNumSol.value = top.opener.fGethdNumSolicitud();
  
  frm.iCveTram.value = top.opener.fGetiCveTramite();
  
  frm.iCveModal.value = top.opener.fGetiCveModalidad();
  
  frm.iCveRequi.value = top.opener.fGetiCveRequisito();
  //fNavega();
}



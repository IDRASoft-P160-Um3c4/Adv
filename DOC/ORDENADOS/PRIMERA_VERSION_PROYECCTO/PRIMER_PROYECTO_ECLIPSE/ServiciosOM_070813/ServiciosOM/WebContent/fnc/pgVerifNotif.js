// MetaCD=1.0
// Title: pgVerificacion.js
// Description: JS "Cat�logo" de la entidad CPATitulo
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Sandor Trujillo Q.
var cTitulo = "";
var FRMListado = "";
var frm;
var ClaveReqActual = "";
var DescReqActual  = "";
var aCalifica;
var numrequisito="";
var cPosicionaReq = "";
var cFechaActual = "";
var lEsSolicitud = false;
var lValido = false;
var lTienePNC = false;
var lEntregado = false;
var lNotificar = false;
var lNotificado = false;
var lNuevoPNC = false;
var lFaltanCarac = false;
var aCaracPend = new Array();
var aResCompleto, aResTramite, aResTramite = new Array();
var aResReqPNC, aCBoxReq = new Array();
var aResReqBase = new Array();
var idUser = fGetIdUsrSesion();
var aResPNC = new Array();
var iPID;
var iReq = 0;
var aCalif = new Array();
var iCambios = 0;
var lEnVerificacion = false;
var lEnNotificacion = false;
var lGuardar = false;
var lEtapaPNC = false;
var lGuardado = false;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pgVerificacion.js";
  if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  cTitulo = (cTitulo == "" || cTitulo == "T�TULO NO ENCONTRADO")?"EVALUACI�N T�CNICA":cTitulo;
  fSetWindowTitle();
    if(top.opener){
//    cPaginaWebJS = "pgSolicitud.js";
     if(top.opener.cPaginaWebJS == "pgSolicitud.js")
        lEsSolicitud = true;
     else
        lEsSolicitud = false;
  }

}
// SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
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
            TDEtiCampo(false,"EEtiqueta",0," Ejercicio :","iEjercicio","",4,4," Ejercicio ","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0," N�mero de Solicitud :","iNumSolicitud","",30,30," Solicitud ","fMayus(this);");
          FTR();
        FinTabla();
      FITR();
         TDEtiAreaTexto(false,"EEtiqueta",0," Tr�mite:",90,2,"cDscTramite",""," Tr�mite","fMayus(this);","","",true,true,false,"",0);
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
                          TextoSimple("V�lido");
                       FTD();
                    FTR();
                    ITR();
                       //TDEtiCheckBox("EEtiqueta",0,"Requisito V�lido:","lValidoBOX","1",true," Activo","","onClick=fHabDeshab();");
                       TDEtiCheckBox("EEtiqueta",0,"Requisito V�lido:","lValidoBOX","1",true," Activo","","");
                    FTR();
                 FinTabla();
              FTD();
              ITD();
                 InicioTabla("",1,"100%","","center");
                    ITR("EEtiqueta","","","","top");
                       ITD("ESTitulo",4,"100%","","center");
                          TextoSimple("No V�lido");
                       FTD();
                    FTR();
                    ITR();
                       TDEtiCampo(false,"EEtiqueta",0," Fecha de Oficio:","dtOficio","",10,10," Notificaci�n","fMayus(this);");
                       TDEtiCampo(false,"EEtiqueta",0," Num. Oficio:","cNumOficio","",20,20," Notificaci�n","fMayus(this);");
                    FTR();
                    if(lEsSolicitud == false){
                       ITR();
                          TDEtiAreaTexto(true,"EEtiqueta",0," Recibe la Notificaci�n:",80,3,"cRecibeNotif",""," Persona que recibe la notificaci�n","","fMayus(this);",'onkeydown="fMxTx(this,500);"',true,true,false,"",3);
                       FTR();
                    }else{
                      Hidden("cRecibeNotif","");
                    }
                    ITR();

                       TDEtiCampo(!lEsSolicitud,"EEtiqueta",0," Notificaci�n:","dtNotificacion","",10,10," Notificaci�n","fMayus(this);","","",false,"",3);
                    FTR();
                 FinTabla();
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


      FTDTR();
      Hidden("lConcAprobado","");
      Hidden("lValido","");

      Hidden("iCveTramite","");
      Hidden("iCveModalidad","");
      Hidden("iCveRequisito","");

      Hidden("iHDEjercicio","");
      Hidden("iNumVerifica","");
      Hidden("iCveProceso","");
      Hidden("iCveProducto","");
      Hidden("iCveConcVerifica","");
      Hidden("hdDscRequisito","");
      Hidden("wPNC");
      Hidden("wCaracteristicas");
      Hidden("hdBotonAux","");
      Hidden("dtRecepcion");
      Hidden("wOficio");
      Hidden("cNumOfReg");
      Hidden("dtOfReg");
      Hidden("iModificarTra");

      //LEL070906
      Hidden("ClavesTramite");
      Hidden("ClavesModalidad");
      Hidden("Caracteristicas");
      Hidden("RequisitoPNC");
      Hidden("cCveCarac");
      Hidden("iEjercicioPNC");
      Hidden("hdLlave");
      Hidden("hdSelect");
      Hidden("iRecepcion",2);
      Hidden("iVerificacion",3);
      Hidden("iNotificacion",20);
      Hidden("iCveEtapa");
      Hidden("iCveOficina");
      Hidden("iCveDepartamento");
      Hidden("iCveUsuario",fGetIdUsrSesion());
      Hidden("lAnexo");
      Hidden("cObservaciones","");
   //   Hidden("hdBoton");
      Hidden("iCveUsuRegistro",idUser);
      Hidden("iCveCausaPNC");
      Hidden("lResuelto");
      Hidden("cRecibeH");
      Hidden("iCveVentanillaU");
      //FinLEL070906

      Hidden("lRecNotificado");
      Hidden("lGuardar",false);
      Hidden("cObsCIS");
    FinTabla();
  fFinPagina();
}

// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel01A");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(false);
  FRMPanel.fShow("Tra,");
  FRMPanel.fSetTraStatus("Sav,");


  FRMListado = fBuscaFrame("IListado01A");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Requisitos,�Contiene Docto. Digitalizado?,Carac.,Entregado,V�lido,Tiene PNC,");
  FRMListado.fSetDespliega("texto,texto,texto,texto,logico,logico,");
  FRMListado.fSetObjs(2,"Boton");
  FRMListado.fSetCampos("10,14,4,5,13,");
  FRMListado.fSetAlinea("left,center,center,center,center,center,");

  fDisabled(true,"dtNotificacion,lValidoBOX,cObs,lConcAprobadoBOX,");

  frm.hdBoton.value="Primero";
  frm.dtNotificacion.disabled = false;
  frm.lValidoBOX.disabled = false;
  frm.cNumOficio.disabled=false
  frm.dtOficio.disabled=false
  fTraeFechaActual();
}

// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){
    frm.hdNumReg.value = 10000;
    var bReturn = false;
    if(frm.iNumSolicitud.value != "" && frm.iNumSolicitud.value.length > 0 &&
       frm.iEjercicio.value != "" && frm.iEjercicio.value.length > 0){
       frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
                            " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value+
                            " And TRAREGPNCETAPA.ICONSECUTIVOPNC = ( SELECT max(r1.ICONSECUTIVOPNC) FROM TRAREGPNCETAPA r1 where r1.IEJERCICIO=TRAREGSOLICITUD.IEJERCICIO and r1.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD )";
       				
                            //" AND TRARegReqXTram.dtRecepcion IS NOT NULL";
      frm.iEjercicioPNC.value = frm.iEjercicio.value;
      frm.hdBotonAux.value = "VERIFICACION";
      bReturn = true;
      fEngSubmite("pgVerificacion.jsp","Listado01A");
    }
    return bReturn;
}

//RECEPCI�N de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iEtapaVerifica,iEtapaRecepcion,irPNC,iNotificacion,iVentanillaU,iModifTram){
    if(cError=="Guardar")
     fAlert("La Etapa de Verificaci�n no est� configurada para este tr�mite o\nExisti� un error en el Guardado!");
    if(cError=="Borrar")
     fAlert("Existi� un error en el Borrado!");
    if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return false;
    }
    if(cId == "GPNCCarac" && cError == ""){
     fAlert("Producto No Conforme n�mero: " + irPNC + " generado");
     fNavega();
    }
    if(cId == "GOficio" && cError == ""){
     fNavega();
    }
    if(cId == "ActReqXTram" && cError == ""){
     wPNC.top.close();
     fNavega();
    }
    if(cId == "Listado01A" && cError==""){
	
         var requitomodif = numrequisito;
         lNotificar = true;
         lNuevoPNC = true;
         aResCompleto = fCopiaArregloBidim(aRes); //LEL070906
         iCambios = 0;
         for(var i=0; i<aRes.length; i++){
           if(aRes[i][6]!=""){
               FRMPanel.fSetTraStatus(",");
               lGuardado = true;
           }
           if(aRes[i][14] > 0)
             aRes[i][14] = "<font color=blue>" + "SI" + "</font>";
           else
             aRes[i][14] = "NO";
           if(aRes[i][13] != 0)
             lNuevoPNC = false;
           if(aRes[i][13] == 0 && aRes[i][5] == 0){
                lNotificar = false;
           }
           if(aRes[i][13] == 1 || aRes[i][5] == 1){
              lEnVerificacion = true;
              iCambios++;
           }
         }
         frm.hdRowPag.value = iRowPag;
         FRMListado.fSetListado(aRes);
         FRMListado.fShow();
         FRMListado.fSetLlave(cLlave);
         fGeneraCalifica(aRes);
         fReposicionaListado(FRMListado,"9",requitomodif);
         numrequisito="";
        
         if(cPosicionaReq != "")
           fReposicionaListado(FRMListado,"9",cPosicionaReq);
        
         cPosicionaReq = "";
        
         if(top.opener){
            top.opener.fNavega();
         }
         if(aRes.length > 0){
            fBuscaEtapa();
         }
         if(lGuardado==true){
             frm.cRecibeNotif.disabled = true;
             frm.dtNotificacion.disabled = true;
             FRMPanel.fSetTraStatus(",");
         }   
    }
    if(cId == "idFechaActual" && cError==""){
       cFechaActual = aRes[0];
       fRecibeValores();
    }
    if(cId == "idCveEtapa" && cError==""){
     frm.iVerificacion.value = iEtapaVerifica;
     frm.iCveEtapa.value = frm.iVerificacion.value;
     frm.iRecepcion.value = iEtapaRecepcion;
     frm.iNotificacion.value = iNotificacion;
     frm.iCveVentanillaU.value = iVentanillaU;
     frm.iModificarTra.value = iModifTram;
     if(aRes.length > 0 && !lEsSolicitud){
        if(aRes[0][0] != frm.iRecepcion.value &&
           aRes[0][0] != frm.iVerificacion.value &&
           aRes[0][0] != frm.iModificarTra.value){
           FRMPanel.fShow(",");
        }else{
           if(lGuardado!=true && lGuardado!=true){
             FRMPanel.fShow("Tra,");
             FRMPanel.fSetTraStatus("Sav,");
           }
           else FRMPanel.fSetTraStatus(",");
        }
     }
     if(lNotificado == true)
	fRecibioNotif();
     else{
        if(iCambios == 0 && lEnVerificacion == false){
           if(frm.iCveOficina.value == "")
             fObtenOficDepto();
           else{
              if(lEnVerificacion == false)
                fEtapaVerificacion();
           }
        }
     }
    }
    if(cId == "CIDPNCSolCom"){
       if(aRes.lenght>0)
    	  aResPNC = fCopiaArregloBidim(aRes);
       else aResPNC = aRes;
       fConsultaPNC();
    }
    if(cId == "cRecibio" && cError==""){	
       if(aRes.length>0) frm.cRecibeH.value = aRes[0][0];
       if(FRMListado.fGetLength() == 0)fNavega();
    }
    if(cId == "idOficeDepto" && cError==""){
     if(aRes.length > 0){
        frm.iCveOficina.value = aRes[0][0];
        frm.iCveDepartamento.value = aRes[0][1];
        frm.lAnexo.value = aRes[0][2];
     }
    }
    if(cId == "idOficeDepto1" && cError==""){
     frm.iCveOficina.value = aRes[0][0];
     frm.iCveDepartamento.value = aRes[0][1];
     fEtapaVerificacion();
    }
    if(cId == "idCambiaEtapa" && cError==""){
     if(lEtapaPNC){
       if(lNuevoPNC == false){
         frm.hdBoton.value = "GPNCCarac";
         fEngSubmite("pgGRLRegistroPNC.jsp","GPNCCarac");
        lEtapaPNC = true;
      }else{
        fAbreRegistroPNC();
      }
      lEtapaPNC = false;
     }
     else{
       lEnVerificacion = true;
       fGuardarProcesa();
     }
    }
    if(cId == "idCambiaEtapaPNC" && cError == ""){
      lEnVerificacion = true;
      lGuardar = false;
      fAbreRegistroPNC();
    }
    if(cId == "idRecibeN" && cError == "" && !lEnNotificacion){
       fBuscaDatosEtapa2();
    }
    if(cId == "idCveEtapa2"){
     if(aRes.length>1){
           frm.iCveOficina.value = aRes[0][2];
           frm.iCveDepartamento.value = aRes[0][3];
           frm.lAnexo.value = aRes[0][4];
           frm.iCveEtapa.value = frm.iNotificacion.value;
           fEtapaNotif();
     }
    }
    if(cId == "idCambiaNotif" && cError == ""){
     frm.cRecibeH.value = frm.cRecibeNotif.value;
     lEnNotificacion = true;
     fNavega();
    }    
    return true;
}


function fRecibeValores(){
  frm.iNumSolicitud.value = top.opener.fGetiNumSolicitud();
  frm.iEjercicio.value    = top.opener.fGetiEjercicio();
  frm.iEjercicioPNC.value = top.opener.fGetiEjercicio();
  frm.cDscTramite.value   = top.opener.fGetcDscTramite();
  frm.cDscModalidad.value = top.opener.fGetDscModalidad();
  fRecibioNotif();
}

//Buscar quien recibio la notificaci�n
function fRecibioNotif(){
 frm.hdLlave.value = "";
 frm.hdSelect.value = "SELECT CRECIBENOTIF, GRLREGISTROPNC.ICONSECUTIVOPNC " +
 " FROM TRAREGPNCETAPA " +
 "JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC " +
 "AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC " +
 " where TRAREGPNCETAPA.IEJERCICIO = " + frm.iEjercicioPNC.value +
 " AND TRAREGPNCETAPA.INUMSOLICITUD = " + frm.iNumSolicitud.value +
 " ORDER BY IORDEN DESC";
 fEngSubmite("pgConsulta.jsp","cRecibio");
}
function fGeneraCalifica(aResRequisito){
    aCalifica = new Array();
    for (var i=0; i<aResRequisito.length; i++){
      aCalifica[i] = new Array();
      aCalifica[i][0] = aResRequisito[i][9];
      aCalifica[i][1] = new Array();
    }
}
//Busca la �ltima etapa de la solicitud
function fBuscaEtapa(){
   frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
                        " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value + " "+
   			" And TRAREGPNCETAPA.ICONSECUTIVOPNC = ( SELECT max(r1.ICONSECUTIVOPNC) FROM TRAREGPNCETAPA r1 where r1.IEJERCICIO=TRAREGSOLICITUD.IEJERCICIO and r1.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD )";
   frm.hdOrden.value = "";
   fEngSubmite("pgTRARegEtapasXModTramA.jsp","idCveEtapa");
}
function fVerificaFechas(){

    if(frm.lValidoBOX.checked==false){
      if(lEsSolicitud && lTienePNC == false){
        frm.hdSelect.value = "SELECT " +
                             "CASE WHEN (DTLIMITEENTREGADOCS>DATE(CURRENT DATE)) THEN 1 " +
                             "ELSE 0 " +
                             "END AS iESVALIDO, " +
                             "DTLIMITEENTREGADOCS,DATE(CURRENT DATE) AS DTACTUAL " +
                             "FROM TRAREGSOLICITUD " +
                             "WHERE IEJERCICIO="+frm.iEjercicio.value+
                             " AND INUMSOLICITUD="+frm.iNumSolicitud.value;
        frm.hdLlave.value  = "";
        fEngSubmite("pgConsulta.jsp","cIdVerFecha");
      }
    }

}

//LLAMADO desde el Listado cada vez que se selecciona un rengl�n
    function fSelReg(aDato,iCol){
    frm.iCveTramite.value = aDato[2];
    frm.iCveModalidad.value = aDato[3];
    if (aDato[11]!="")
      frm.cDscTramite.value = aDato[11];
    if (aDato[12]!="")
      frm.cDscModalidad.value = aDato[12];
    frm.lValido.value = aDato[5];
    
    if(frm.lValido.value == 0)
       frm.lValidoBOX.checked = false;
    else
       frm.lValidoBOX.checked = true;
    frm.dtNotificacion.value = "";
    if(frm.cRecibeNotif.value == "")
       frm.cRecibeNotif.value = frm.cRecibeH.value; // Adelanta
    frm.dtNotificacion.value = aDato[6];
    if(aDato[6] != ""){
       frm.cRecibeNotif.value = frm.cRecibeH.value;
       frm.dtNotificacion.disabled = true;
    }else
       frm.cRecibeNotif.value = "";
    if(frm.dtNotificacion.value != "" && frm.lValidoBOX.checked == false){
       lNotificado = true;
       lEnNotificacion = true;
       frm.dtNotificacion.disabled = true;
       frm.cRecibeNotif.disabled = true;
    }else{
       lNotificado = false;
       if(frm.dtNotificacion.value != ""){
          lEnNotificacion = true;
          frm.dtNotificacion.disabled = true;
          frm.cRecibeNotif.disabled = true;
       }else{
          frm.dtNotificacion.disabled = false;
          frm.cRecibeNotif.disabled = false;
       }
    }
    frm.dtOficio.value = aDato[7];
    frm.cNumOficio.value = aDato[8];
    frm.iCveRequisito.value = aDato[9];
    numrequisito = frm.iCveRequisito.value;
    frm.hdDscRequisito.value = aDato[10];
    frm.dtRecepcion.value = aDato[4];
    if(aDato[4] == "")
       lEntregado = false;
    else
       lEntregado = true;
    if(aDato[5] == 0)
       lValido = false;
    else
       lValido = true;
    if(aDato[13] == 0)
       lTienePNC = false;
    else
       lTienePNC = true;
    if( frm.lValido.value == 1 ) frm.lValidoBOX.checked = true;
    else frm.lValidoBOX.checked = false;
    
    if(iCol==2){
      if(aDato[13] != "NO"){
         ClaveReqActual = aDato[9];
         DescReqActual  = aDato[10];
         fAbreValidaRequisito();
      }
    }
    if(iCol == 1 && aDato[14] != "NO"){
      cPosicionaReq = aDato[9];
      fRegDocumentos();
    }
    if(iCol == 5 && aDato[13] != "0"){
      fCargaPNCSolCom();
    }
    frm.lRecNotificado.value = aDato[15];
    fHabDeshab();
}

function fHabDeshab(){
    frm.lValidoBOX.disabled = true;
    frm.dtOficio.disabled = true;
    frm.cNumOficio.disabled = true;
}
function fGuardar(){
    lGuardado=true;
      if(frm.cNumOficio.value != "" && frm.lValidoBOX.checked == false && frm.cRecibeNotif.disabled == true){
        fAlert("No es pocible dar de alta un PNC sobre un requisito con una notificaci�n previa\nSe le recomienda cancelar el tr�mite");
      }else{
          if(fValidaRecibe()){
              FRMPanel.fSetTraStatus(",");
              frm.cRecibeNotif.disabled = true;
              frm.dtNotificacion.disabled = true;
            frm.hdBoton.value = "GRecibeN";
            fEngSubmite("pgGRLRegPNC.jsp","idRecibeN");
          }
        }
}
//Busca la oficina y el departamento de la Etapa 2
function fBuscaDatosEtapa2(){
   frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
                        " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value + " ";
   frm.hdOrden.value = "";
   fEngSubmite("pgTRARegEtapasXModTramA.jsp","idCveEtapa2");
}
//Guarda la etapa de notificaci�n
function fEtapaNotif(){
  frm.hdFiltro.value = " TRARegSolicitud.iEjercicio = " + frm.iEjercicio.value +
                       " AND TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitud.value + " ";
   frm.hdBoton.value = "CambiaEtapaA";
   fEngSubmite("pgTRARegEtapasXModTram.jsp","idCambiaNotif");

}
function fValidaRecibe(){
    valido = true;
    cMsg = "";
    if(frm.cRecibeNotif.value.length>99){
	cMsg = cMsg+"\n -Los datos de la persona no deben de pasar de 99 caracteres";
    }
    if(frm.cRecibeNotif.value == ""){
       cMsg = cMsg+"\n -Los datos de la persona que recibe son requeridos ";
    }
    if(frm.dtNotificacion.value == ""){
      cMsg = cMsg+"\n -La fecha de recepci�n es requerida ";
    }
    if(cMsg != ""){
       fAlert(cMsg);
       valido = false;
    }
    return valido;
}

function fSalir(){
  if(top.opener.fSetEvaluacion)
    top.opener.fSetEvaluacion(window, frm.iVerificacion.value, frm.iCveOficina.value, frm.iCveDepartamento.value, frm.lAnexo.value);
}
function fCargaPNCSolCom(){
    frm.hdLlave.value = "";
    frm.hdSelect.value = "SELECT PNC.iEjercicio, PNC.iConsecutivoPNC, PNC.iCveProducto, P.cDscProducto, "+
                         "PNC.dtRegistro, U.cNombre || ' ' || U.cApPaterno || ' ' || U.cApMaterno AS cDscUsuario, "+
                         "PNC.dtResolucion, PNC.iCveUsuRegistro, PNC.lResuelto, "+
                         "PNC.iCveOficinaAsignado, PNC.iCveDeptoAsignado, "+
                         "PS.iEjercicio AS iEjercicioSolicitud, PS.iNumSolicitud, PS.ICONSECUTIVOPNC "+
                         "FROM TRAREGPNCETAPA PS "+
                         "LEFT JOIN GRLRegistroPNC PNC ON PNC.iEjercicio = PS.iEjercicioPNC "+
                         "      AND PNC.iConsecutivoPNC = PS.iConsecutivoPNC "+
                         "LEFT JOIN GRLProducto P ON P.iCveProducto = PNC.iCveProducto "+
                         "LEFT JOIN SEGUSUARIO U ON U.iCveUsuario = PNC.iCveUsuRegistro "+
                         "WHERE PS.iEjercicio = " + frm.iEjercicio.value + " AND PS.iNumSolicitud = " + frm.iNumSolicitud.value + " "+ //iNumSolCom
                         "ORDER BY PS.iEjercicio, PS.iNumSolicitud, PS.iCONSECUTIVOPNC DESC";
    fEngSubmite("pgConsulta.jsp","CIDPNCSolCom");
  }



/*





function fConsultaPNC(){
  if(!aResPNC || !aResPNC.length || (aResPNC.length && aResPNC.length == 0))
    fAlert('\n - No existen registros de Productos No Conformes para esta solicitud.');
  else
    fAbreConsultaSeguimientoPNC();
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
function fNuevo(){

     FRMPanel.fSetTraStatus("UpdateBegin");
     fBlanked();
     fDisabled(false,"","--");
     FRMListado.fSetDisabled(true);

}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar




function fGuardarProcesa(){
   var valValida = frm.lValidoBOX.checked;
   if(lEsSolicitud && frm.dtRecepcion.value=="")
      fAlert("No es posible guardar el registro si el requisito no ha sido entregado");
   else{
     if( frm.lValidoBOX.checked == true ) frm.lValido.value = 1;
     else frm.lValido.value = 0;

     frm.hdBoton.value = "Guardar";
     if(fValidaTodo()==true && frm.lValidoBOX.checked == true){
        if(lEsSolicitud==false && frm.dtNotificacion.value != "")
           frm.dtRecepcion.value = "";
           frm.lGuardar.value=true;
        if(fNavega()==true){
           FRMPanel.fSetTraStatus("Sav,");
           FRMListado.fSetDisabled(false);
        }
     }
     fVerificaFechas();
   }
}

function fEtapaVerPNC(){
   frm.hdBoton.value = "CambiaEtapa";
   frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
                        " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value + " ";
   fEngSubmite("pgTRARegEtapasXModTram.jsp","idCambiaEtapaPNC");
}

function fReporte(){
  if(lNotificar)
     fSegOficios();
  else
     fAlert("Termine de evaluar todos los Requisitos de la Solicitud para generar la Notificaci�n");
}

function fSegOficios(){
   fAbreSubWindowPermisos("pg111020200", "750", "425");
}

function fRegresaDatosPNC(iEjer, iCons){
  frm.hdBoton.value = "ActReqXTram";
  fEngSubmite("pgVerificacion.jsp","ActReqXTram");
}




function fApuntadorPNC(winPNC){
  wPNC = winPNC;
}

// LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
function fGuardarA(){
   if( frm.lValidoBOX.checked ) frm.lValido.value = 1;
   else frm.lValido.value = 0;

   frm.hdBoton.value = "GuardarA";

   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("Sav,");
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
function fModificar(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
function fCancelar(){
   if(FRMListado.fGetLength() > 0)
     FRMPanel.fSetTraStatus("UpdateComplete");
   else
     FRMPanel.fSetTraStatus("AddOnly");
   fDisabled(true,"");
   FRMListado.fSetDisabled(false);
   frm.iNumSolicitud.disabled = false;
   frm.iEjercicio.disabled = false;
   frm.dtNotificacion.disabled = false;
   frm.lValidoBOX.disabled = false;
   frm.cObs.disabled = false;
   frm.lConcAprobadoBOX.disabled = false;
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Borrar
function fBorrar(){
    return false;
}

// Cambia el tr�mite a la etapa de verificaci�n
function fEtapaVerificacion(){
  if(parseInt(frm.iCveOficina.value,10)>0 && parseInt(frm.iCveDepartamento.value,10)>0){
    if(parseInt(frm.iEjercicio.value,10)>0 && parseInt(frm.iNumSolicitud.value,10)>0){
      frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
                           " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value + " ";
      frm.hdBoton.value = "CambiaEtapa";
      fEngSubmite("pgTRARegEtapasXModTram.jsp","idCambiaEtapa");
    }
  }else{
    fObtenOficDepto1();
  }
}

// Busca oficina y departamento de etapa recepci�n en el �rea
function fObtenOficDepto(){
  frm.hdLlave.value = "";
  frm.hdSelect.value = "SELECT ICVEOFICINA, ICVEDEPARTAMENTO, LANEXO "+
                       "FROM TRAREGETAPASXMODTRAM "+
                       "WHERE iEjercicio = " + frm.iEjercicio.value + " AND iNumSolicitud = " + frm.iNumSolicitud.value +
                       " AND iCveTramite = " + frm.iCveTramite.value + " AND iCveModalidad = " + frm.iCveModalidad.value +
                       " ORDER BY IORDEN DESC";
    fEngSubmite("pgConsulta.jsp","idOficeDepto");
}
// Busca oficina y departamento de etapa recepci�n en el �rea
function fObtenOficDepto1(){
  frm.hdLlave.value = "";
  frm.hdSelect.value = "SELECT ICVEOFICINA, ICVEDEPARTAMENTO, LANEXO "+
                       "FROM TRAREGETAPASXMODTRAM "+
                       "WHERE iEjercicio = " + frm.iEjercicio.value + " AND iNumSolicitud = " + frm.iNumSolicitud.value +
                       " AND iCveTramite = " + frm.iCveTramite.value + " AND iCveModalidad = " + frm.iCveModalidad.value +
                       " ORDER BY IORDEN DESC";
    fEngSubmite("pgConsulta.jsp","idOficeDepto1");
}
function fEnviaDatosPNC(objWnd){
  if(objWnd && objWnd.fSetRegistrosPNC)
    objWnd.fSetRegistrosPNC(aResPNC);
  if(objWnd.fSetRecepcion)
    objWnd.fSetRecepcion(frm.iRecepcion.value);
  if(objWnd.fSetVentanilla)
    objWnd.fSetVentanilla(frm.iCveVentanillaU.value);
  if(objWnd.fSetRequisito)
     objWnd.fSetRequisito(frm.iCveRequisito.value);
}



function fEnviaReqCalificar(targetForm){
  if (targetForm){
    if (targetForm.iCveRequisito && ClaveReqActual != ""){
      targetForm.iCveRequisito.value = ClaveReqActual;
      targetForm.cDscBreve.value     = DescReqActual;
      targetForm.lEnProceso.value = 1;
      targetForm.lMandatorio.value = 1;

    }
  }
}

function fValoresActuales(){
  for (var i=0; i<aCalifica.length; i++){
    if (aCalifica[i][0] == ClaveReqActual)
      return aCalifica[i][1];
  }
}

function fRecibeCalifica(iRequi, aCalifica, wVentana){
  iReq = iRequi;
  aCalif = fCopiaArregloBidim(aCalifica);
  lFaltanCarac = false;
  frm.cCveCarac.value = "";
  frm.hdBoton.value="Primero";
  wCaracteristicas = wVentana;
  wCaracteristicas.top.close();
  iPID = setInterval('fRecCalif();', 1500);
}

function fRecCalif(){
  var aDatos = new Array();
  clearInterval(iPID);
//  for(var iTime = 0; iTime < 80000; iTime++);

  for (var i=0; i<aCalif.length; i++){
    aDatos[i] = new Array();
    aDatos[i][0] = aCalif[i][0];
    aDatos[i][1] = aCalif[i][1];
    //LEL060906 Verifica si no existen carac. oblig. sin cumplir
    aDatos[i][2] = aCalif[i][2];
    if(aDatos[i][1] == true && aDatos[i][2] == 1){
       frm.cCveCarac.value = frm.cCveCarac.value + aCalif[i][0] + ",";
       lFaltanCarac = true;
    }
  }
  if(lFaltanCarac){
     lFaltanCarac = false;
     fGuardarPNC();
  }
}


// FUNCI�N donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   cMsg = fValElements("cNumOficio,");
   if(!fEvaluaCampo(frm.cNumOficio.value)==false)          //evalua los parametros que son aceptados en el
       cMsg = cMsg+"\n N�m. Oficio no permite comas, signos raros ni par�ntesis";
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

function fBuscar(){
   fAbreBuscaSolicitud();
   fNavega();
}

function fSetSolicitud(iEjercicio, iNumSolicitud){
  frm.iEjercicio.value = iEjercicio;
  frm.iNumSolicitud.value = iNumSolicitud;
  fNavega();
}


//Se manda a ejecutar de la pantalla emergente de registro de PNC
function fObtieneValoresTOP(forma){
  forma.fRecibeValoresTOP(frm.iCveTramite.value, frm.iCveRequisito.value,frm.iCveModalidad.value,frm.cDscTramite.value, frm.cDscModalidad.value,frm.hdDscRequisito.value, frm.iNumSolicitud.value, frm.iEjercicio.value);
}

function fAceptarDocumento(obj){
  fNavega();
  if(obj)
    obj.close();
}


function fGetParametros(){
   var aParametros = new Array();
   aParametros[0] = ""; // deprecated.
   aParametros[1] = "Documentos Adjuntos del Proceso Actual.";  //Descripcion del Proceso
   aParametros[2] = "";    // deprecated.
   aParametros[3] = ""; // deprecated.
   aParametros[4] = "TRADocXRequis"; // Nombre de la Tabla.
   aParametros[5] = ""; //deprecated.
   aParametros[6] = "2"; // No. de Modulo.
   aParametros[7] = "Consulta";   // Modo de la Pagina Escritura o Consulta.
   return aParametros;
}

function fGetArregloCampos(){
   var aCampos = new Array();
   aCampos[0] = "iEjercicioReq";
   aCampos[1] = "iNumSolicitud";
   aCampos[2] = "iCveTramite";
   aCampos[3] = "iCveModalidad";
   aCampos[4] = "iCveRequisito";//Nombres de los Campos.
   return aCampos;
}
function fGetArregloDatos(){
   var aDatos = new Array();
   aDatos[0] = frm.iEjercicio.value;
   aDatos[1] = frm.iNumSolicitud.value;
   aDatos[2] = frm.iCveTramite.value;
   aDatos[3] = frm.iCveModalidad.value;
   aDatos[4] = frm.iCveRequisito.value;//Nombres de los Valores de los Campos.
   return aDatos;
}


function fGetlFirmado(){
   return "1";
}
function fGetlTerminado(){
  return "0";
}        fGetlSustituido
function fGetlSustituido(){
  return "0";
}
function fGetlArchivado(){
  return "0";
}
function fGetTipoTitulo(){
  return "1";
}

function fGetiEjercicio(){
  return frm.iEjercicio.value;
}

function fGetiNumSolicitud(){
  return frm.iNumSolicitud.value;
}

function fGetcDscTramite(){
  return frm.cDscTramite.value;
}

function fGetcDscModalidad(){
  return frm.cDscModalidad.value;
}
function fGetCvePMDP(){
   var cPMDP = "";
   return cPMDP;
}

function fGetCveModulos(){
  var cModulos = "2,";
  return cModulos;
}
function fGetNumReportes(){
  var cNumReportes = "5,";
  return cNumReportes;
}

function fGetFiltroAdicional(){
  var cOtroFiltro = frm.iEjercicio.value + "," +
      frm.iNumSolicitud.value;
  return cOtroFiltro;
}

function fApOficio(apOficio){
   wOficio = apOficio;
}

function fObtenOficio(cNumOfic, dtOfic){
   frm.cNumOfReg.value = cNumOfic;
   frm.dtOfReg.value = dtOfic;
   wOficio.top.close();
//   frm.hdBoton.value = "ActReqXTram";
//  fEngSubmite("pgVerificacion.jsp","ActReqXTram");
   frm.hdBoton.value = "GOficio";
   frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = "+frm.iEjercicio.value+
                        " AND TRAREGSOLICITUD.iNumSolicitud = "+frm.iNumSolicitud.value +
                        " AND TRARegReqXTram.dtRecepcion IS NOT NULL";
   fEngSubmite("pgVerificacion.jsp","GOficio");
}


function fSoloAlfanumericos2(cVCadena){
    if ( fRaros(cVCadena)      || fPuntuacion(cVCadena) ||
         fComa(cVCadena)    || fSignos(cVCadena) || fArroba(cVCadena) ||
         fParentesis(cVCadena))
        return false;
    else
        return true;
}

function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if(fSoloAlfanumericos2(cVCadena))
        return false;
    else
        return true;
}

function fBuscaRequisito(){

}


function fReqTramMod(iTramite, iModalidad){
  var aReqTramMod = new Array();
  for (var i=0; i<aResCompleto.length; i++){
  //  if (aResCompleto[i][0] == iTramite && aResCompleto[i][1] == iModalidad)
      aReqTramMod[aReqTramMod.length] = aResCompleto[i];
  }
  return aReqTramMod;
}

// LLAMADO desde el Panel cada vez que se regresa de caracter�sticas, seleccionado bot�n Guardar
function fGuardarPNC(){
   var resp;
   cMsg = "";
   cMsg += "\nExisten requisitos obligatorios no seleccionados. Esto generar� PNC.";
   if(cMsg != "")
      resp = confirm("\n" + cMsg + "\n\n�Desea continuar con estos datos?");

   frm.iCveProducto.value = 2;
   frm.iCveCausaPNC.value = 2;
   frm.lResuelto.value = 0;
   lEtapaPNC = true;
   fEtapaVerificacion();

}

// FUNCI�N donde se generan las validaciones de los datos ingresados
function fValidaPNC(){
  cMsg = fValElements();
//  if (frm.iCveSolicitante.value == "")
//    cMsg += "\n-Debe seleccionar al solicitante del tr�mite.";
//  if (frm.iCveDomicilioSolicitante.value == "")
//    cMsg += "\n-Debe seleccionar el domicilio del solicitante del tr�mite.";
//  if (frm.iCveTipoPersona.value != "1"){
//    if (frm.iCveRepLegal.value == "")
//      cMsg += "\n-Debe seleccionar al representante legal en caso de personas NO f�sicas.";
//    if (frm.iCveDomicilioRepLegal.value == "")
//      cMsg += "\n-Debe seleccionar el domicilio del representante legal en caso de personas NO f�sicas.";
//  }

  var lPrincipalSel = false;
  var lRequisitoSel = false;
  var lRequisitoComp = true;
  aResReqPNC = new Array();
  for (var i=0; i<aCBoxReq.length; i++){
    if (aCBoxReq[i]){
      lRequisitoSel = true;
      for (var j=0; j<aResReqBase.length; j++){
        if (aResRequisito[i][2] == aResReqBase[j][2])
          lPrincipalSel = true;
      }
    }else{
      if(aResRequisito[i][9] == 1){
        lRequisitoComp = false;
        aResReqPNC[aResReqPNC.length] = aResRequisito[i];
      }
    }
  }
  if (lRequisitoSel == false)
    cMsg += "\n-Debe seleccionar como entregado al menos un requisito.";
  if (lRequisitoSel && !lPrincipalSel)
    cMsg += "\n-Debe seleccionar como entregado al menos un requisito del tr�mite principal.";

  if(cMsg != ""){
    fAlert(cMsg);
    return false;
  }
  cMsg = "";
  if(!lRequisitoComp)
    cMsg += "\nExisten requisitos obligatorios no seleccionados. Esto generar� PNC.";
  if(cMsg != "")
      return confirm(cAlertMsgGen + "\n" + cMsg + "\n\n�Desea continuar con estos datos?");
  return true;
}

function fNuevoPNC(){
   return lNuevoPNC;
}

*/
// MetaCD=1.0
 // Title: pg110020032
 // Description: JS "Consultas" de la entidad GRLRegistroPNC
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Levi Equihua López

 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var idUser = fGetIdUsrSesion();
 var aOficinaDeptoUsrAsg, aOficinaUsrAsg;
 var aRegistrosPNC = new Array();
 var aRegistrosSPNC = new Array();
 var iSeguimiento = 0;
 var iCausaSel = 0;
 var cResComent = "";
 var iResuelto = 0;
 var dtFechaActual = "";
 var iGuardar = 0;
 var aResListado1 = new Array();
 var cCausa = "";
 var cCausa2 = "";
 var iNuevo = 0;
 var iAsignado = 0;
 var iCorrecto = 0;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020032.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
     fSetWindowTitle();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"95%","","center","","0");
     ITRTD("ETablaST",0,"95%","","center","top");
        TextoSimple("CAUSAS DEL PRODUCTO NO CONFORME");
     FTDTR();
     ITRTD("",0,"","","center","top");
        IFrame("IListado23","100%","100","Listado.js","yes",true);
     FTDTR();
     ITRTD("ETablaST",0,"95%","","center","top");
        TextoSimple("SEGUIMIENTO DE PRODUCTO NO CONFORME");
     FTDTR();
     ITRTD("",0,"","","center","top");
        IFrame("IListado24","100%","130","Listado.js","yes",true);
     FTDTR();
     ITRTD("",0,"100%","","center","top");
       InicioTabla("ETablaInfo",0,"100%","","","",1);
       ITRTD("",0,"100%","","center","top");
       InicioTabla("",0,"","","","",1);
         ITR("EEtiqueta",0,"","","center");
            TDEtiAreaTexto(true,"EEtiqueta",0,"Actividades Realizadas y<br>Evidencias Registradas:",100,5,"cComentarios","","Comentarios relativos a este paso del seguiemiento","","fMayus(this);",'onkeydown="fMxTx(this,1000);"',false,true,true,"ECampo",5);
            //TDEtiAreaTexto(true, "EEtiqueta", 0, "EtiquetaDespliega:",                              50, 2,"cCampo",      "","TooTip",                                            "","fMayus(this);",'onkeydown="fMxTx(this,1000);"', true, true, true, "", 10);
         FTR();
         FTDTR();
         ITD();
         FinTabla();
         InicioTabla("",1,"","","","",1);
         ITRTD("",0,"70%","","center","top");
            InicioTabla("",0,"","","","",1);
               ITD();
                  ITRTD("EEtiquetaC",1,"","","center","top");
                     Radio(true,"iAccion",1,false,"","","",'onClick = "fAsignado();"');
                     TextoSimple("Asignar al departamento:");
                  FTDTR();
                  ITRTD("EEtiquetaC",1,"","","center","top");
                     fDefOficXUsr(true,true);
                  FTDTR();
               FTD();
            FinTabla();
            ITD();
            InicioTabla("",0,"","","","",1);
               ITD();
                  ITRTD("EEtiquetaC",2,"","","center","top");
                     Radio(true,"iAccion",2,true,"","","",'onClick = "fResuelto();"');
                     TextoSimple("Causa resuelta en:");
                  FTDTR();
                  ITRTD("EEtiquetaL",1,"","","left","top");
                     InicioTabla("",0,"","","","",1);
                     TDEtiCampo(false,"EEtiqueta",0,"Fecha:","dtFechaRes","",10,10,"Fecha","fMayus(this);","","","","ECampo",0);
                     FinTabla();
                  FTDTR();
                  ITRTD("EEtiquetaC",1,"10","20","center","top");

                  FTDTR();
               FTD();
            FinTabla();
            ITR();

            FTR();
          FinTabla();
         FTR();
         ITRTD("EEtiquetaC",2,"","","center","top");
            if(!top.opener)
               BtnImg("Guardar","guardar","fGuardar();");
         FTDTR();
         ITR();
       FinTabla();
     FTDTR();
     FTR();

   FinTabla();
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("iCveUsuario",idUser);
     Hidden("iEjercicio");
     Hidden("iConsecutivoPNC");
     Hidden("iCveCausaPNC","");
     Hidden("iCveSeguimiento");
     Hidden("iCveProducto");
     Hidden("iCveOficinaAsignado");
     Hidden("iCveDeptoAsignado");
     Hidden("tsMomentoSeguimiento");
     Hidden("iCveUsuRegistra");
     Hidden("iCveUsuCorrige");
     Hidden("lResuelto",0);

     Hidden("iOficina","");
     Hidden("iDepto","");
     Hidden("iOficinaEnvia","");
     Hidden("iDeptoEnvia","");

     Hidden("iEjercicioSel","");
     Hidden("iCveProductoSel","");
     Hidden("iConsecutivoSel","");
     Hidden("iCveCausaSel","");
     Hidden("groupRadio1");
     Hidden("aRegCausasPNC");
   fFinPagina();
 }
  // FUNCION para cargar listado con causas

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   frm.iDepto.value="";
   FRMListado = fBuscaFrame("IListado23");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Descripción de la Causa,Área que corrige,Fecha de resolución,");
   FRMListado.fSetAlinea("left,left,center,");
   FRMListado.fSetDespliega("texto,texto,center,");
   FRMListado.fSetCampos("1,9,3,");
   FRMListado.fSetSelReg(2);
   FRMListado1 = fBuscaFrame("IListado24");
   FRMListado1.fSetControl(self);
   FRMListado1.fSetTitulo("Fecha de Registro,Oficina,Departamento,Usuario que Registra,");
   FRMListado1.fSetAlinea("center,left,left,left,");
   FRMListado1.fSetDespliega("texto,texto,texto,texto,");
   FRMListado1.fSetCampos("1,4,6,2,");
   FRMListado1.fSetSelReg(1);
   frm.groupRadio1.value = 1;
   frm.hdBoton.value="Primero";
   if(top.opener){

     fDisabled(true);
   }else
     fSetRadioValue(frm.iAccion, "1");
 }

 // Función que asigna valores predeterminados a oficina,depto y  proc.
 function fRegresaDatosPNC(){
   if (top.opener)
      if(top.opener.fRegresaDatosPNC)
         top.opener.fRegresaDatosPNC(top,aRegistrosPNC,frm.aRegCausasPNC.value);
 }

  // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   FRMListado.fSetListado(window.parent.aRegCausas);
   FRMListado.fShow();
 }

 // LLAMADO a JSP para cargar las causas de Producto No Conforme
 function fNavega1(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = "IEJERCICIO,ICONSECUTIVOPNC,ICVEPRODUCTO,ICVECAUSAPNC,ICVESEGUIMIENTO";
   select2 = "SELECT GRLSEGUIMIENTOPNC.ICVESEGUIMIENTO, GRLSEGUIMIENTOPNC.TSMOMENTOSEGUIMIENTO, " +
      "SEGUSUARIO.CNOMBRE||' '|| SEGUSUARIO.CAPPATERNO||' '|| SEGUSUARIO.CAPMATERNO AS USUARIO, " +
      "GRLSEGUIMIENTOPNC.ICVEOFICINAASIGNADO, GRLOFICINA.CDSCBREVE AS cOficina, " +
      "GRLSEGUIMIENTOPNC.ICVEDEPTOASIGNADO, GRLDEPARTAMENTO.CDSCBREVE AS cDepto, " +
      "GRLSEGUIMIENTOPNC.CCOMENTARIOS, GRLREGCAUSAPNC.LRESUELTO, GRLREGCAUSAPNC.DTRESOLUCION " +
      "FROM GRLSEGUIMIENTOPNC " +
      "JOIN SEGUSUARIO ON GRLSEGUIMIENTOPNC.ICVEUSUREGISTRA = SEGUSUARIO.ICVEUSUARIO " +
      "JOIN GRLOFICINA ON GRLOFICINA.ICVEOFICINA = GRLSEGUIMIENTOPNC.ICVEOFICINAASIGNADO " +
      "JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = GRLSEGUIMIENTOPNC.ICVEDEPTOASIGNADO " +
      "JOIN GRLREGCAUSAPNC ON GRLREGCAUSAPNC.IEJERCICIO = GRLSEGUIMIENTOPNC.IEJERCICIO AND " +
      "GRLREGCAUSAPNC.ICONSECUTIVOPNC = GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC AND " +
      "GRLREGCAUSAPNC.ICVEPRODUCTO = GRLSEGUIMIENTOPNC.ICVEPRODUCTO AND " +
      "GRLREGCAUSAPNC.ICVECAUSAPNC = GRLSEGUIMIENTOPNC.ICVECAUSAPNC " +
      "WHERE GRLSEGUIMIENTOPNC.IEJERCICIO = " + window.parent.iEjercicioM +
      " AND GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC = " + window.parent.iConsecutivoPNCM +
      " AND GRLSEGUIMIENTOPNC.ICVEPRODUCTO = " + window.parent.iCveProductoM +
      " AND GRLSEGUIMIENTOPNC.ICVECAUSAPNC = " + iCausaSel +
      " ORDER BY GRLSEGUIMIENTOPNC.TSMOMENTOSEGUIMIENTO DESC";
   frm.hdSelect.value = select2;
   fEngSubmite("pgConsulta.jsp","ListadoA");
 }

  // LLAMADO a JSP para cargar las causas de Producto No Conforme
 function fActualizaCausas(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = "IEJERCICIO,ICONSECUTIVOPNC,ICVEPRODUCTO,ICVECAUSAPNC";
   select3 = "SELECT GRLREGCAUSAPNC.ICVECAUSAPNC, GRLCAUSAPNC.CDSCCAUSAPNC, " +
      "SEGUSUARIO.CNOMBRE||' '|| SEGUSUARIO.CAPPATERNO||' '|| SEGUSUARIO.CAPMATERNO AS USUARIO, " +
      "GRLREGCAUSAPNC.DTRESOLUCION, GRLREGCAUSAPNC.CDSCOTRACAUSA, " +
      "ET.IORDEN, (" +
      "SELECT MAX  (ET1.IORDEN) " +
      " FROM TRAREGPNCETAPA ET1 " +
      " WHERE ET1.IEJERCICIO = " + window.parent.iEjercicioM +
      " AND ET1.INUMSOLICITUD = ET.INUMSOLICITUD) " +
      " AS IORD, " +
      "GRLREGISTROPNC.ICVEOFICINAASIGNADO, " +
      "GRLREGISTROPNC.ICVEDEPTOASIGNADO, " +
      "O.CDSCBREVE ||' - '||D.CDSCBREVE AS CDSCAREA "+
      "FROM GRLREGCAUSAPNC " +
      "JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = " + window.parent.iEjercicioS +
      " AND GRLREGISTROPNC.ICONSECUTIVOPNC = " + window.parent.iConsecutivoPNCM +
      " JOIN SEGUSUARIO ON GRLREGCAUSAPNC.ICVEUSUCORRIGE = SEGUSUARIO.ICVEUSUARIO " +
      "JOIN GRLCAUSAPNC ON GRLCAUSAPNC.iCveProducto = GRLREGCAUSAPNC.iCveProducto " +
      " AND GRLCausaPNC.ICVECAUSAPNC = GRLREGCAUSAPNC.ICVECAUSAPNC "+
      "JOIN GRLPRODUCTO ON GRLPRODUCTO.ICVEPRODUCTO = GRLREGCAUSAPNC.ICVEPRODUCTO " +
      "LEFT JOIN TRAREGPNCETAPA ET ON ET.IEJERCICIO = " + window.parent.iEjercicioM +
      " AND ET.ICONSECUTIVOPNC = " + window.parent.iConsecutivoPNCM +
      "  JOIN GRLOFICINA O ON GRLREGISTROPNC.ICVEOFICINAASIGNADO = O.ICVEOFICINA " +
      "  JOIN GRLDEPARTAMENTO  D ON GRLREGISTROPNC.ICVEDEPTOASIGNADO = D.ICVEDEPARTAMENTO " +
      " WHERE GRLREGCAUSAPNC.IEJERCICIO = " + window.parent.iEjercicioM +
      " AND GRLREGCAUSAPNC.ICONSECUTIVOPNC = " + window.parent.iConsecutivoPNCM +
      " AND GRLREGCAUSAPNC.ICVEPRODUCTO = " + window.parent.iCveProductoM;
   frm.hdSelect.value = select3;
   fEngSubmite("pgConsulta.jsp","ListadoC");
 }

 function fRevisaCausas(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = "IEJERCICIO,ICONSECUTIVOPNC,ICVEPRODUCTO,ICVECAUSAPNC";
   select2 = "SELECT GRLREGCAUSAPNC.ICVECAUSAPNC, GRLREGCAUSAPNC.LRESUELTO, " +
      "GRLREGCAUSAPNC.DTRESOLUCION, GRLREGCAUSAPNC.CDSCOTRACAUSA FROM GRLREGCAUSAPNC " +
      "WHERE GRLREGCAUSAPNC.IEJERCICIO = " + window.parent.iEjercicioM +
      " AND GRLREGCAUSAPNC.ICONSECUTIVOPNC = " + window.parent.iConsecutivoPNCM +
      " AND GRLREGCAUSAPNC.ICVEPRODUCTO = " + window.parent.iCveProductoM +
      " AND LRESUELTO = 0"
   frm.hdSelect.value = select2;
   fEngSubmite("pgConsulta.jsp","CausasResueltas");
 }

 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iNConsecutivo){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   fResOficDeptoUsr(aRes,cId,cError,true);
   if(cId == "CIDOficinaDeptoXUsr" && cError==""){
     fNavega();
   }
   if(cId == "Guardar" && cError==""){
   //  fAlert("Seguimiento registrado con Consecutivo: " + iNConsecutivo + "\nResolviendo causa");
     fResolverCausa();
   }
   if(cId=="GuardarA" && cError==""){
   //  fAlert("Seguimiento registrado con Consecutivo: " + iNConsecutivo);
     fActualizaCausas();
   }
   if(cId=="GuardarB" && cError==""){
     fAlert("Registro Producto No Conforme Resuelto");
     fSelectSetIndexFromValue(frm.iCveOficinaUsr, 0);
     fOficinaUsrOnChange(true);
     fSelectSetIndexFromValue(frm.iCveDeptoUsr, 0);
     fDeptoUsrOnChange();
     //ELEL22082006
     fActualizaPNC();
     //fActualizaCausas();
     //Fin ELEL22082006
   }
   if(cId == "Resolver" && cError==""){
   //  fAlert("Causa de Producto No Conforme Resuelta \nSe buscaran causas resueltas");
     fRevisaCausas();
   }
   if(cId == "CausasResueltas" && cError==""){
     if(aRes.length == 0){
    //   fAlert("Todas las causas pertenecientes al producto no conforme estan resueltas\nSe registrará Fecha Resolucion");
       frm.lResuelto.value = 1;
       frm.hdBoton.value = "GuardarB";
       iGuardar = 0;
       if(fEngSubmite("pgGRLRegistroPNC.jsp","GuardarB")){ }
     }else{
     //  fAlert("Existen causas por resolver. Fin del proceso");
       fActualizaCausas();
   } }
   if(cId == "idFechaActual" && cError==""){
     dtFechaActual = aRes[0];
     if(iGuardar == 0)
        fCargaOficDeptoUsr(true);
     else if(iGuardar == 1){
        iGuardar = 0;
        frm.tsMomentoSeguimiento.value = aRes[1][2] + "-";
        if(aRes[1][1] < 10)
          frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + "0";
        frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + aRes[1][1] + "-";
        if(aRes[1][0] < 10) frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + "0";
        frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + aRes[1][0] + " " + aRes[3][0] + ":" + aRes[3][1] + ":00.1";
        frm.hdBoton.value = "Guardar";
        if(fEngSubmite("pgGRLSeguimientoPNC.jsp","GuardarA")){ }
     }else if(iGuardar == 2){
        iGuardar = 2;
        frm.tsMomentoSeguimiento.value = aRes[1][2] + "-";
        if(aRes[1][1] < 10) frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + "0";
        frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + aRes[1][1] + "-";
        if(aRes[1][0] < 10) frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + "0";
        frm.tsMomentoSeguimiento.value = frm.tsMomentoSeguimiento.value + aRes[1][0] + " " + aRes[3][0] + ":" + aRes[3][1] + ":00.1";
        frm.hdBoton.value = "Guardar: " + frm.tsMomentoSeguimiento.value;
        frm.hdBoton.value = "Guardar";
        if(fEngSubmite("pgGRLSeguimientoPNC.jsp","Guardar")){ }
     }
   }
   if(cId == "ListadoA" && cError==""){
     frm.hdRowPag.value = iRowPag;
     for(i=0; i<aRes.length; i++){
       cResComent = aRes[i][7];
       aRes[i][10] = cResComent.substring(0,50);
     }

     aResListado1 = fCopiaArregloBidim(aRes);
     FRMListado1.fSetListado(aResListado1);
     FRMListado1.fShow();
     FRMListado1.fSetLlave(cLlave);
     //lelcambio
     if(aRes.length > 0 && !top.opener && iResuelto == 0){
        if(aRes[0][3] == window.parent.iCveOficinaM &&
           aRes[0][5] == window.parent.iCveDeptoM){
           iAsignado = 1;
           fEnableRadio(frm.iAccion, true);
           fDisabled(false);
           fDisabled(true,"cComentarios,iCveOficinaUsr,iCveDeptoUsr,");
           fEnableRadio(frm.iAccion, true);
           fSetRadioValue(frm.iAccion, "1");
        }else{
           if(aRes.length == 0){

             iAsignado = 1;
           }else{
             iAsignado = 2;
             fEnableRadio(frm.iAccion, false);
             fDisabled(true);
           }
        }
     }else{
        if(iNuevo == 0){
      /*     fEnableRadio(frm.iAccion, false);
           fDisabled(true); */
        }else{
  /*           fDisabled(false);
           fDisabled(true,"iCveOficinaUsr,iCveDeptoUsr,cComentarios,");
           fEnableRadio(frm.iAccion, true); */
           iNuevo = 0;
        }
     }
     if(iGuardar == 2){
        iGuardar = 0;
        fRevisaCausas();
     }

   }
   if(cId == "ListadoC" && cError==""){
     frm.hdRowPag.value = iRowPag;
     aResListado1 = fCopiaArregloBidim(aRes);
     for(i=0; i < aResListado1.length; i++)
        if(aResListado1[i][0] == 0)
           aResListado1[i][1] = aResListado1[i][4];
           aRegCausasPNC = fCopiaArregloBidim(aResListado1);
     window.parent.aRegCausas = fCopiaArregloBidim(aResListado1);
     cCausa2 = cCausa;
     FRMListado.fSetListado(aResListado1);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     FRMListado.fSetDisabled(false);
   }
   if(cId == "NoTienePNC" && cError == ""){
     fActualizaCausas();
   }
 }

 function fAsignado(){
     frm.dtFechaRes.value = "";
    if(!top.opener && iResuelto == 0 && iNuevo == 1){
        fDisabled(false);
        fDisabled(true,"iCveOficinaUsr,iCveDeptoUsr,cComentarios,");
        fEnableRadio(frm.iAccion, true);
        frm.dtFechaRes.value = "";
     }
 }
 function fResuelto(){
   if(iResuelto == 0){
      frm.dtFechaRes.value = dtFechaActual;
      fDisabled(false);
      fDisabled(true,"iAccion,cComentarios,");
      fOficinaUsrOnChange(true);
      fDeptoUsrOnChange();
   }else{
     fDisabled(true);
   }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.cComentarios.value = aDato[7];
    if(cCausa2 != ""){
       fReposicionaListado(FRMListado,"1",cCausa2);
       cCausa2 = "";
    }
    if(window.parent.cCausaM != ""){
      fReposicionaListado(FRMListado,"1",window.parent.cCausaM);
      window.parent.cCausaRetM = cCausa;
      window.parent.cCausaM = "";
    }

    fSelectSetIndexFromValue(frm.iCveOficinaUsr,aDato[3]);
    fOficinaUsrOnChange(true);
    fSelectSetIndexFromValue(frm.iCveDeptoUsr, aDato[5]);

 }

 function fActualizaPNC(){
   frm.iEjercicioSel.value = window.parent.iEjercicioM;
   frm.iConsecutivoSel.value = window.parent.iConsecutivoPNCM;
   frm.hdBoton.value="NoTienePNC";
   fEngSubmite("pgTRARegReqXTram.jsp","NoTienePNC");
 }

 function fSelReg2(aDato){
    frm.cComentarios.value = "";
    fSelectSetIndexFromValue(frm.iCveOficinaUsr, 0);
    fOficinaUsrOnChange(true);
    fSelectSetIndexFromValue(frm.iCveDeptoUsr, 0);
    fDeptoUsrOnChange();
    fDisabled(false);
    iCausaSel = aDato[0];
    if(aDato[0] == ""){
      fEnableRadio(frm.iAccion, false);
      fDisabled(true);

    }else{
      cCausa = aDato[1];
      window.parent.cCausaRetM = cCausa;

      if(aDato[3] == ""){
        iResuelto = 0;
        if(lAsignado = false && aDato[6] == aDato[5] &&
          aDato[7] == window.parent.iCveOficinaM &&
          aDato[8] == window.parent.iCveDeptoM){
          fEnableRadio(frm.iAccion, true);
          fDisabled(false,"dtFechaRes");
        }else if(lAsignado == false && aDato[6] == aDato[5] &&
          (aDato[7] != window.parent.iCveOficinaM ||
          aDato[8] != window.parent.iCveDeptoM)){
          fEnableRadio(frm.iAccion, false);
          fDisabled(true);
        }else{
          fEnableRadio(frm.iAccion, true);
          fDisabled(false,"dtFechaRes");
          frm.dtFechaRes.disabled = true;
        }
      }else{
        iResuelto = 1;
        fEnableRadio(frm.iAccion, false);
        fDisabled(true);
      }
      //FinLEL281106
      fNavega1();
    }
 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 /*
 function fValidaTodo(){
    cMsg = fValElements();

    if(cMsg != ""){
       fAlert(cMsg);
       return false;
    }
    return true;
 }*/
 function fImprimir(){
    self.focus();
    window.print();
 }

  function fResolverCausa(){
     frm.hdBoton.value = "Resolver";
     if(fEngSubmite("pgGRLRegCausaPNC1.jsp","Resolver")){ }
  }

  function fValidaTodo(){
     cMensaje = "";
     if(iResuelto == 1){
       cMensaje = "La causa seleccionada ya esta resuelta";
 /*    } else if(iAsignado == 2){
       cMensaje = "La causa no esta asignada al departamento";
*/
     } else {
       if(frm.cComentarios.value == ""){
         cMensaje = "El campo de comentarios es obligatorio ";
         if(frm.iAccion[0].checked){
            if(frm.iCveOficinaUsr.value <= 0 || frm.iCveDeptoUsr.value <= 0)
               cMensaje = cMensaje + "e indique la oficina y el departamento";
         }
       }else if((frm.iCveOficinaUsr.value <= 0 || frm.iCveDeptoUsr.value <= 0) &&
            frm.iAccion[0].checked){
            cMensaje = "Indique la oficina y el departamento";
       }
     }
     if(cMensaje != ""){
       iCorrecto = 0;
       fAlert(cMensaje);
     }else
       iCorrecto = 1;
  }

  function fGuardar(){
     cCausa2 = cCausa;
     if(frm.cComentarios.disabled == true)
        fAlert("No se puede registrar dicho movimiento por no ser su área la última asignada");
     else{
       fValidaTodo();
       if(iCorrecto == 1){
         if(iResuelto == 0/* && iAsignado == 1*/){
           if(frm.cComentarios.value != ""){
             frm.iEjercicio.value = window.parent.iEjercicioM
             frm.iConsecutivoPNC.value = window.parent.iConsecutivoPNCM;
             frm.iCveProducto.value = window.parent.iCveProductoM;
             frm.iCveCausaPNC.value = iCausaSel;
             frm.iCveSeguimiento.value = iSeguimiento;
             frm.iCveUsuRegistra.value = window.parent.iCveUsuarioM;
             frm.iCveUsuCorrige.value = window.parent.iCveUsuarioM;
             frm.hdBoton.value="Guardar";
             if(frm.iAccion[1].checked){
               iGuardar = 2;
               frm.iCveOficinaAsignado.value=window.parent.iCveOficinaM;
               frm.iCveDeptoAsignado.value=window.parent.iCveDeptoM;
               fTraeFechaActual();
             }else if(frm.iAccion[0].checked){
               if(frm.iCveOficinaUsr.value > 0 || frm.iCveDeptoUsr.value > 0){
                 iGuardar = 1;
                 frm.iCveOficinaAsignado.value=frm.iCveOficinaUsr.value;
                 frm.iCveDeptoAsignado.value=frm.iCveDeptoUsr.value;
                 fTraeFechaActual();
               }
             }
          }
        }
      }
    }
  }
  function fEjecutafNavega(){
   fDisabled(true,"iAccion,");
   frm.iCveOficinaAsignado.value=0;
   frm.iCveDeptoAsignado.value=0;
   fTraeFechaActual();
 }


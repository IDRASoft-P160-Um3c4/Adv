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
   cPaginaWebJS = "pg110020020.js";
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
         ITR();
           TDEtiCampo(false,"EEtiqueta",0,"Usuario:","cNomUsuario","",80,50,"Usuario","fMayus(this);","","","","ECampo",5);
         FTR();
         ITRTD("",4);
           InicioTabla("",0);
             fDefOficXUsr();
           FinTabla();
         FTDTR();
         ITR();
//         TDEtiSelect(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cOnChange,cEstiloCM,iColExtiendeCM);
           TDEtiSelect(false,"EEtiqueta",0,"Proceso:","iCveProceso","fCargaProducto();");
           TDEtiSelect(false,"EEtiqueta",0,"Producto:","iCveProducto","fNavega();");
         FTR();
       FinTabla();

       if(!top.opener){
         ITRTD("",0,"","","center","top");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",10,"","","center");
             TextoSimple("Llenar esta información si el producto no conforme es sobre un tramite");
           FTDTR();
           ITR();
             TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",5,5,"Ejercicio del tramite","fMayus(this);","","","","ECampo",3);
             TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitud","",5,5,"Solicitud Relacionada","fMayus(this);","","","","ECampo",3);
           FTR();
         FinTabla();
       }else{
         Hidden("iNumSolicitud");
         Hidden("iEjercicio","1");
       }

       InicioTabla("",0,"","","center");
           ITR();
  //            TDEtiCheckBox(cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo,cEstiloCM,iColExtiendeCM);
              TDEtiCheckBox("EEtiqueta",0,"Ver Otros Procesos","lVerTodosBOX","0",true," Activo","","onClick=fSePresiono();"); //
           FTR();
       FinTabla();
     FTDTR();

     ITRTD("ETablaST",0,"95%","","center","top");
       TextoSimple("CAUSAS COMUNES REGISTRADAS PARA ESTE PRODUCTO");
     FTDTR();
     ITRTD("",0,"","","center","top");
       IFrame("IListado20","95%","170","Listado.js","yes",true);
     FTDTR();

     ITRTD("",0,"100%","","center","top");
       InicioTabla("ETablaInfo",0,"75%","","","",1);
         ITR("EEtiqueta",0,"","","center");
            TDEtiAreaTexto(false,"EEtiqueta",0,"Otra causa:",100,3,"cDscOtraCausa","","Descripción de otra causa ajena a las comunes","","fMayus(this);",'onkeydown="fMxTx(this,500);"',false,true,true,"ECampo",0);
         ITR("EEtiqueta",0,"","","center");
            //TDEtiAreaTexto(false,"EEtiqueta",0,"Articulos por los que se deben de cubrir:",50,6,"cObsLey1","","Descripción de otra causa ajena a las comunes","","fMayus(this);",'onkeydown="fMxTx(this,500);"',false,true,true,"ECampo",0);
         Hidden("cObsLey1");
         ITR("EEtiqueta",0,"","","center");
            //TDEtiAreaTexto(false,"EEtiqueta",0,"Articulos que determinan prevenir al Solicitante:",50,6,"cObsLey2","","Descripción de otra causa ajena a las comunes","","fMayus(this);",'onkeydown="fMxTx(this,200);"',false,true,true,"ECampo",0);
         Hidden("cObsLey2");
         ITR("EEtiqueta",0,"","","center");
            //TDEtiAreaTexto(false,"EEtiqueta",0,"Leyes y Reglamentos:",50,6,"cObsLey3","","Descripción de otra causa ajena a las comunes","","fMayus(this);",'onkeydown="fMxTx(this,500);"',false,true,true,"ECampo",0);
         Hidden("cObsLey3");
         FTR();
         ITRTD("ETablaST",2,"100%","","center","top");
           TextoSimple("ASIGNAR PRODUCTO A OFICINA Y DEPARTAMENTO");
         FTDTR();
         ITRTD("",2,"","","center","top");
           InicioTabla("",0);
             fDefOficXUsrAsg(true,true);
           FinTabla();
         FTDTR();
       FinTabla();
     FTDTR();
     ITR();
        InicioTabla("",0,"75%","","center");
          ITRTD("",0,"","40","center","bottom");
             IFrame("IPanel","95%","34","Paneles.js");
          FTDTR();
        FinTabla();
      FTR();
/*     ITRTD("",2,"","","center");
       BtnImg("Guardar","guardar","fGuardar();");
     FTDTR(); */

   FinTabla();
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("iCveUsuario",idUser);
     Hidden("iCveCausaPNC","");

     Hidden("iOficina","");
     Hidden("iDepto","");
     Hidden("iOficinaEnvia","");
     Hidden("iDeptoEnvia","");

     Hidden("iProceso","");
     Hidden("iConsec",0);

     Hidden("iEjercicioPNC");
     Hidden("iConsecutivoPNC");

     Hidden("iCveTramite");
     Hidden("iCveModalidad");
     Hidden("iCveEtapa");
     Hidden("iOrden");
     Hidden("iRecepcion");
     Hidden("iCveVentanillaU");
     Hidden("iCveRequisito");
     Hidden("cOtrasCausas");
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
//   FRMPanel.fShow("Tra,");
   FRMPanel.fSetTraStatus("Sav,");
   if (top.opener){
      if(top.opener.fEnviaDatosPNC)
         top.opener.fEnviaDatosPNC(window);
      if(top.opener.fApuntadorPNC)
         top.opener.fApuntadorPNC(window);
   }
   fDisabled(true,"iNumSolicitud,iEjercicio,iCveProceso,iCveProducto,cDscOtraCausa,iCveOficinaUsrAsg,iCveDeptoUsrAsg,iCveOficinaUsr,iCveDeptoUsr,");
   if(lHabTodas == true){
       frm.lVerTodosBOX.disabled = false;
       frm.cObsLey1.disabled = false;
       frm.cObsLey2.disabled = false;
       frm.cObsLey3.disabled = false;
   }else{
       frm.lVerTodosBOX.disabled = true;
       frm.cObsLey1.disabled = true;
       frm.cObsLey2.disabled = true;
       frm.cObsLey3.disabled = true;
   }
   fTraeFechaActual();
 }

 function fSePresiono(){
 //  fDefOficXUsr(frm.lVerTodosBOX.checked);  //frm.lVerTodosBOX.checked
// fResOficDeptoUsr(aRes,"","",frm.lVerTodosBOX.checked);
/*   FRMListado.fSetListado(new Array());
   FRMListado.fShow(); */

   fCargaOficDeptoUsr(frm.lVerTodosBOX.checked);
   fOficinaUsrOnChange(frm.lVerTodosBOX.checked,true);
   fDeptoUsrOnChangeLocal();

//   fSelectSetIndexFromValue(frm.iCveOficinaUsr, -1);

//   iCveOficinaUsr,iCveDeptoUsr
 }
 // Función que asigna valores predeterminados a oficina,depto y  proc.
 function fSetFiltrosPNC(iOficina, iDepartamento,iProcedimiento,iOficinaEnvia,iDeptoEnvia,lInactivaEnvio){
   frm.iOficina.value = iOficina;
   frm.iDepto.value = iDepartamento;
   frm.iProceso.value = iProcedimiento;
   if(iOficinaEnvia)
     frm.iOficinaEnvia.value = iOficinaEnvia;
   if(iDeptoEnvia)
     frm.iDeptoEnvia.value = iDeptoEnvia;
   if(!lInactivaEnvio)
     lInactivaEnvio = false;
   cDeshab = "iCveProducto,cDscOtraCausa,";
   if(lInactivaEnvio == false)
     cDeshab += "iCveOficinaUsrAsg,iCveDeptoUsrAsg,";
   fDisabled(true,cDeshab);
 }

 function fRegresaDatosPNC(){
   if (top.opener){
      if(top.opener.fRegresaDatosPNC){
         top.opener.fRegresaDatosPNC(top,frm.iEjercicio.value,frm.iConsec.value);
      }
   }
   //LEL03102006
   fRegReqXCausa();

 }

 function fRegReqXCausa(){
   frm.hdBoton.value = "GuardarMult";
   fEngSubmite("pgTRARegReqXCausa.jsp","idRegReqXCausa");
 }

 function fOficinaUsrOnChangeLocal(){
//   alert("On oficina on chage local");
  fFillSelect(frm.iCveProceso,new Array,"",0,0,0);
  fFillSelect(frm.iCveProducto,new Array,"",0,0,0);
  fSelectSetIndexFromValue(frm.iCveDeptoUsr, frm.iDepto.value);
  fDeptoUsrOnChange();
 }

 function fDeptoUsrOnChangeLocal(){
   fCargaProceso();
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
   if(parseInt(frm.iCveOficinaUsr.value,10) >= 0 &&
      parseInt(frm.iCveDeptoUsr.value,10) >= 0 &&
      parseInt(frm.iCveProceso.value,10) >= 0){
     frm.hdLlave.value = "ICVEOFICINA,ICVEDEPARTAMENTO";
     frm.hdSelect.value = "SELECT DISTINCT(GRLPRODXOFICDEPTO.ICVEPRODUCTO), " +
     "CDSCPRODUCTO FROM GRLPRODXOFICDEPTO " +
     "JOIN GRLPRODUCTO ON GRLPRODXOFICDEPTO.ICVEPRODUCTO = GRLPRODUCTO.ICVEPRODUCTO " +
     "WHERE ICVEOFICINA = " + frm.iCveOficinaUsr.value +
     " AND ICVEDEPARTAMENTO = " + frm.iCveDeptoUsr.value +
     " AND ICVEPROCESO = " + frm.iCveProceso.value;
     
     fEngSubmite("pgConsulta.jsp","cIdProducto");
   }
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
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   fResOficDeptoUsr(aRes,cId,cError,frm.lVerTodosBOX.checked);
   if((cId == "Guardar" || cId == "GuardarB") && cError == ""){
     if(frm.cOtrasCausas.value != "")
        frm.cDscOtraCausa.value = frm.cOtrasCausas.value;
     frm.iConsecutivoPNC.value = iNConsecutivo;
     fAlert("Registrado Producto No Conforme con Consecutivo: " +  iNConsecutivo);
     fSelectSetIndexFromValue(frm.iCveOficinaUsrAsg, 0);
     fOficinaUsrOnChangeAsg(true);
     fSelectSetIndexFromValue(frm.iCveDeptoUsrAsg, 0);
     if(top.opener){
        if(top.opener.frm.iEjercicio && top.opener.frm.iNumSolicitud &&
           top.opener.frm.iCveTramite && top.opener.frm.iCveModalidad){
           if(top.opener.fNuevoPNC){
              if(top.opener.fNuevoPNC() == true){
                 frm.iConsec.value = iNConsecutivo;
                 frm.iConsecutivoPNC.value = iNConsecutivo;
                 fRegistraTram(iNConsecutivo);
              }else{
                 frm.hdBoton.value = "";
                 fEngSubmite("pgTRARegPNCEtapa.jsp","idRegTramite");
              }
           }else{
              fRegistraTram(iNConsecutivo);
           }
        }else{
           fRegresaDatosPNC();
        }
     }else{
        if(frm.iNumSolicitud.value != ""){
           frm.iConsecutivoPNC.value = iNConsecutivo;
           frm.hdBoton.value = "Guardar";
           fEngSubmite("pgTRARegPNCEtapa.jsp","idEtapaPNC");
        }
     }
   }

   if(cId == "idEtapaPNC" && cError == ""){
//     alert("Registro etapa");
   }

   if(cId == "idExisteSol" && cError == ""){
     if(aRes.length > 0){
       lExisteSol = true;
       frm.iEjercicio.value = aRes[0][0];
       frm.iEjercicioPNC.value = aRes[0][0];
       frm.iNumSolicitud.value = aRes[0][1];
       frm.iCveTramite.value  = aRes[0][2];
       frm.iCveModalidad.value = aRes[0][3];
       frm.iCveEtapa.value = aRes[0][5];
       fGuardaDatos();
     }else{
       lExisteSol = false;
     }
   }

   if(cId == "idRegTramite" && cError==""){
     fRegresaDatosPNC();
   }

   if(cId == "idFechaActual" && cError==""){
     frm.iEjercicio.value = aRes[1][2];
     fCargaUsuario();
   }

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     aResListado = fCopiaArregloBidim(aRes);
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     FRMListado.fSetDisabled(false);
     if(top.opener){
        fBuscaOficeDepto();
     }
   }

   if(cId == "cNomUsuario" && cError==""){
     frm.cNomUsuario.value=aRes[0][0];
     frm.iCveUsuario.value = idUser;
     fCargaOficDeptoUsr(frm.lVerTodosBOX.checked);
   }
   if(cId == "cIdProceso" && cError==""){
     fFillSelect(frm.iCveProducto,new Array,"",0,0,0);//LEL171106
     FRMListado.fSetListado(new Array());
     FRMListado.fShow();
     fFillSelect(frm.iCveProceso,aRes,false,frm.iCveProceso.value,0,1);
     fCargaProducto();
   }
   if(cId == "cIdProducto" && cError==""){
     fFillSelect(frm.iCveProducto,aRes,false,frm.iCveProducto.value,0,1);
     fCargaOficDeptoUsrAsg();
   }

   fResOficDeptoUsrAsg(aRes,cId,cError,true);

   if(cId == "CIDOficinaDeptoXUsrAsg"){
     fNavega();
   }

   if(cId == "idUltimoPNC" && cError==""){
     if(aRes.length > 0){
       frm.iConsecutivoPNC.value = aRes[0][3];
       if(aRes[0][6] != ""){
          frm.cDscOtraCausa.value = aRes[0][6];
          frm.cOtrasCausas.value = aRes[0][6];
          
          
          
          lOtrasCausas = true;
       }
       if(aRes[0][8] != ""){
	   frm.cObsLey1.value = aRes[0][8];
	   frm.cObsLey1.disabled = true;
       }
       if(aRes[0][9] != ""){
	   frm.cObsLey2.value = aRes[0][9];
	   frm.cObsLey2.disabled = true;
       }
       if(aRes[0][10] != ""){
	   frm.cObsLey3.value = aRes[0][10];
	   frm.cObsLey3.disabled = true;
       }
       fSelectSetIndexFromValue(frm.iCveOficinaUsrAsg,aRes[0][4]);
       fOficinaUsrOnChangeAsg(true);
       fSelectSetIndexFromValue(frm.iCveDeptoUsrAsg, aRes[0][5]);
       frm.iCveOficinaUsrAsg.disabled = true;
       frm.iCveDeptoUsrAsg.disabled = true;
     }
   }
   if(cId == "idGuardaCausa" && cError == ""){
     frm.cOtrasCausas.value = frm.cDscOtraCausa.value;
     frm.cDscOtraCausa.value = "";
     fGuardar2();
   }
 }

 function fBuscaSolicitud(){
  if (frm.hdLlave && frm.hdSelect){
    frm.hdLlave.value = "";
    frm.hdSelect.value = "SELECT " +
                         "S.iEjercicio, " +
                         "S.iNumSolicitud, " +
                         "S.iCveTramite, " +
                         "S.iCveModalidad, " +
                         "ET.IORDEN, " +
                         "ET.iCveEtapa " +
                         "FROM TRARegSolicitud S " +
                      //   "JOIN TRAREGPNCETAPA ET ON ET.IEJERCICIO = S.iEjercicio " +
                         "JOIN TRAREGETAPASXMODTRAM ET ON ET.IEJERCICIO = S.iEjercicio " +
                         " AND ET.INUMSOLICITUD = S.INUMSOLICITUD " +
                         "WHERE S.IEJERCICIO = " + frm.iEjercicio.value +
                         " AND S.INUMSOLICITUD = " + frm.iNumSolicitud.value +
                         " ORDER BY S.IEJERCICIO DESC, S.INUMSOLICITUD DESC, ET.IORDEN DESC";
    if (frm.hdNumReg)
      frm.hdNumReg.value = 100000;
    fEngSubmite("pgConsulta.jsp","idExisteSol");
  }
 }

 function fBuscaUltimoPNC(){
  if (frm.hdLlave && frm.hdSelect){
    frm.hdLlave.value = "";
    frm.hdSelect.value = "SELECT " +
                         "ETA.IORDEN, " +
                         "RXT.ICVEREQUISITO, " +
                         "CSA.ICVECAUSAPNC, " +
                         "PNC.iConsecutivoPNC as iPNC, " +
                         "PNC.iCveOficinaAsignado as iCveOfiAsig, " +
                         "PNC.iCveDeptoAsignado as iCveDepAsig, " +
                         "CSA.cDscOtraCausa as cOtraCausa, " +
                         "RXT.dtNotificacion, "+
                         "COBSLEY1, "+
                         "COBSLEY2, "+
                         "COBSLEY3 "+                  
                         "FROM TRARegPNCEtapa ETA " +
                         "JOIN GRLREGISTROPNC PNC ON PNC.IEJERCICIO = ETA.IEJERCICIOPNC " +
                         "AND PNC.ICONSECUTIVOPNC = ETA.ICONSECUTIVOPNC " +
                         "AND PNC.DTRESOLUCION IS NULL " +
                         "JOIN GRLREGCAUSAPNC CSA ON CSA.IEJERCICIO = PNC.IEJERCICIO " +
                         "AND CSA.ICONSECUTIVOPNC = PNC.ICONSECUTIVOPNC " +
                         "JOIN TRAREGREQXTRAM RXT ON RXT.IEJERCICIO = ETA.IEJERCICIO " +
                         "AND RXT.INUMSOLICITUD = ETA.INUMSOLICITUD " +
                         "WHERE ETA.IEJERCICIO = " + frm.iEjercicio.value +
                         " AND ETA.INUMSOLICITUD = " + frm.iNumSolicitud.value +
                         " ORDER BY ETA.IEJERCICIO DESC, ETA.INUMSOLICITUD DESC, " +
                         "ETA.IORDEN DESC, CSA.ICVECAUSAPNC";
    if (frm.hdNumReg)
      frm.hdNumReg.value = 100000;
    fEngSubmite("pgConsulta.jsp","idUltimoPNC");
  }

 }

 function fBuscaOficeDepto(){
   if(top.opener.frm.iNumSolicitud){
      frm.iNumSolicitud.value = top.opener.frm.iNumSolicitud.value;
      fBuscaUltimoPNC();
   }
 }

 function fRegistraTram(iPNC){
   frm.iConsecutivoPNC.value = iPNC;
   frm.iEjercicioPNC.value = frm.iEjercicio.value;
   frm.iEjercicio.value = top.opener.frm.iEjercicio.value;
   frm.iNumSolicitud.value = top.opener.frm.iNumSolicitud.value;
   frm.iCveTramite.value = top.opener.frm.iCveTramite.value;
   frm.iCveModalidad.value = top.opener.frm.iCveModalidad.value;
   frm.iCveEtapa.value = 1;
   frm.iOrden.value = 1;
   frm.hdBoton.value = "Guardar";
   fEngSubmite("pgTRARegPNCEtapa.jsp","idRegTramite");
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
      if(frm.cDscOtraCausa.value != "")
         frm.cOtrasCausas.value = frm.cDscOtraCausa.value;
      if(fValidaTodo()){
        if(lOtrasCausas == true){
          fGuardaOtrasCausas();
        }else{
          fGuardar2();
        }
      }
 }

 function fGuardaOtrasCausas(){
   frm.hdBoton.value = "GuardarCausas";
   fEngSubmite("pgGRLRegCausaPNC1.jsp","idGuardaCausa");
 }

 function fGuardar2(){
     frm.hdFiltro.value = "";
     frm.iCveCausaPNC.value = "";
     iExisteCausa = 0;
     aCausas = FRMListado.fGetObjs(0);
     for(cont=0;cont < aCausas.length;cont++)
        if(aCausas[cont] == true)
           iExisteCausa = 1;
     if(frm.iCveDeptoUsrAsg.value > 0 && (  /*frm.cDscOtraCausa.value != ""*/
        frm.cOtrasCausas.value || iExisteCausa == 1)){
	 if(frm.iCveOficinaUsrAsg.disabled == false){
	     if(confirm(cAlertMsgGen + "\n\nSe generará un producto no conforme para\n"+
		                       "\nOficina:  "+frm.iCveOficinaUsrAsg.options[frm.iCveOficinaUsrAsg.selectedIndex].text+
		                       "\nDepartamento:  "+frm.iCveDeptoUsrAsg.options[frm.iCveDeptoUsrAsg.selectedIndex].text+
		                       "\n\n¿Esta seguro de generar el Producto No Conforme?")){
		 
	     }else{return false}
	 }
        	 
                for(cont=0;cont < aCausas.length;cont++)
                  if(aCausas[cont] == true)
                    frm.iCveCausaPNC.value = (frm.iCveCausaPNC.value == "")?aResListado[cont][0]:frm.iCveCausaPNC.value+","+aResListado[cont][0];
                //LEL25092006
                if(top.opener){
                  if(top.opener.cPaginaWebJS == "pgVerificacion.js"){
                    if(top.opener.frm.iNumSolicitud){
                      frm.iNumSolicitud.value = top.opener.frm.iNumSolicitud.value;
                      frm.hdBoton.value = "GuardarB";
                      lGuardar = true;
                      fEngSubmite("pgGRLRegPNC.jsp","GuardarB");
                    }else{
                      frm.hdBoton.value = "Guardar";
                      lGuardar = true;
                      fEngSubmite("pgGRLRegPNC.jsp","Guardar");
                    }
                  }else{
                    frm.hdBoton.value = "Guardar";
                    lGuardar = true;
                    fEngSubmite("pgGRLRegPNC.jsp","Guardar");
                  }
                }else{
                  frm.hdBoton.value = "Guardar";
                  lGuardar = true;
                  fEngSubmite("pgGRLRegPNC.jsp","Guardar");
                }
                //FinLEL25092006
     }else{
       lGuardar = false;
       fAlert("\n - Seleccione el departamento al que será asignado el PNC y seleccione las causas.");
     }
     return true;
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

function fReporte(){
   fAlert("\n-Función no permitida \n");
}

var aOficinaDeptoUsr, aOficinaUsr;
function fOficinaUsrOnChange(lTodas, lSeleccione){
  lTodas = frm.lVerTodosBOX.checked;
  if(!lSeleccione)
    lSeleccione = false;
  if (!lTodas)
    fLlenaSelectFromValueInSelect(aOficinaDeptoUsr, frm.iCveOficinaUsr.value, 1, frm.iCveDeptoUsr, 2, 8, lSeleccione);
  else
    fLlenaSelectFromValueInSelect(aOficinaDeptoUsr, frm.iCveOficinaUsr.value, 0, frm.iCveDeptoUsr, 1, 5, lSeleccione);
  if (window.fOficinaUsrOnChangeLocal)
    window.fOficinaUsrOnChangeLocal();
  else if (document.fOficinaUsrOnChangeLocal)
    document.fOficinaUsrOnChangeLocal();
}

function fDeptoUsrOnChange(){
  if (window.fDeptoUsrOnChangeLocal)
    window.fDeptoUsrOnChangeLocal();
  if (document.fDeptoUsrOnChangeLocal)
    document.fDeptoUsrOnChangeLocal();
}

 // FUNCIÖN para Asignar Oficina X Departamento
function fDefOficXUsrAsg(lTodas,lRenglonIntermedio,lSeleccione){
  if(!lTodas)
    lTodas = false;
  if(!lSeleccione)
    lSeleccione = false;
  var cTx;
  cTx = ITRTD("EEtiquetaC",2,"100%","20","center")+
      InicioTabla("",0,"","","center")+
        ITR()+
          ITD("EEtiqueta",2,"0","","center","middle")+
            TextoSimple("Oficina:")+
          FTD()+
          ITD("EEtiquetaL",2,"0","","center","middle");
  cTx += Select("iCveOficinaUsrAsg","fOficinaUsrOnChangeAsg(" + lTodas + "," + lSeleccione +");");
  cTx+=   FTD();
  if(lRenglonIntermedio)
    cTx += FITR();
  cTx +=  ITD("EEtiqueta",2,"0","","center","middle")+
            TextoSimple("Departamento:")+
          FTD()+
          ITD("EEtiquetaL",2,"0","","center","middle")+
            Select("iCveDeptoUsrAsg","if(fDeptoUsrOnChangeAsg)fDeptoUsrOnChangeAsg();")+
          FTD()+
        FTR()+
      FinTabla()+
    FTDTR();
  return cTx;
}


function fOficinaUsrOnChangeAsg(){
  fSelectSetIndexFromValue(frm.iCveOficinaUsrAsg, frm.iOficinaEnvia.value);
  fLlenaSelectFromValueInSelect(aOficinaDeptoUsrAsg, frm.iCveOficinaUsrAsg.value, 0, frm.iCveDeptoUsrAsg, 1, 5, false);
  fSelectSetIndexFromValue(frm.iCveDeptoUsrAsg, frm.iDeptoEnvia.value);
}

function fDeptoUsrOnChangeAsg(){
}

function fResOficDeptoUsrAsg(aRes,cId,cError){
  var lEncontrado;

  if(cId == "CIDOficinaDeptoXUsrAsg" && cError==""){
    aOficinaDeptoUsrAsg = new Array();
    aOficinaUsrAsg = new Array();
    for (var i=0; i<aRes.length; i++){
      aOficinaDeptoUsrAsg[aOficinaDeptoUsrAsg.length] = aRes[i];
      lEncontrado = false
      for (var j=0; j<aOficinaUsrAsg.length; j++){
          if (aOficinaUsrAsg[j][0] == aRes[i][0]){
            lEncontrado = true;
            break;
          }
      }
      if (!lEncontrado)
        aOficinaUsrAsg[aOficinaUsrAsg.length] = aRes[i];
    }
    fFillSelect(frm.iCveOficinaUsrAsg,aOficinaUsrAsg,false,frm.iCveOficinaUsrAsg.value,0,3);
    fOficinaUsrOnChangeAsg();
  }
}

function fCargaOficDeptoUsrAsg(){
  if (frm.hdLlave && frm.hdSelect){
    frm.hdLlave.value = "GRLDeptoXOfic.iCveOficina,GRLDeptoXOfic.iCveDepartamento";
    frm.hdSelect.value = "SELECT GRLDeptoXOfic.iCveOficina, GRLDeptoXOfic.iCveDepartamento, "+
                         "GRLOficina.cDscOficina, GRLOficina.cDscBreve as cDscBreveOfic, "+
                         "GRLDepartamento.cDscDepartamento, GRLDepartamento.cDscBreve as cDscBreveDepto "+
                         "FROM GRLDeptoXOfic "+
                         "JOIN GRLOficina ON GRLOficina.iCveOficina = GRLDeptoXOfic.iCveOficina "+
                         "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLDeptoXOfic.iCveDepartamento ";
    
    //Cambio hecho para obligar al usuario a colocar solo ventanilla única
    if(top.opener)
	if(top.opener.fVentanilla)
	    if(top.opener.fVentanilla()==true)
		frm.hdSelect.value += " where GRLDEPARTAMENTO.ICVEDEPARTAMENTO=95 ";
    
    frm.hdSelect.value += "ORDER BY cDscBreveOfic, cDscBreveDepto";
    
    if (frm.hdNumReg)
      frm.hdNumReg.value = 100000;
    fEngSubmite("pgConsulta.jsp","CIDOficinaDeptoXUsrAsg");
  }
}

function fSetRecepcion(iRecep){
  frm.iRecepcion.value = iRecep;
}

function fSetRequisito(iReq){
  frm.iCveRequisito.value = iReq;
}

function fSetVentanilla(iVentanillaU){
  frm.iCveVentanillaU.value = iVentanillaU;
}

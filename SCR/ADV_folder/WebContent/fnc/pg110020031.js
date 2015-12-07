// MetaCD=1.0
 // Title: pg110020031
 // Description: JS "Consultas" de la entidad GRLRegistroPNC
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Levi Equihua López

 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var idUser = fGetIdUsrSesion();
 var aOficinaDeptoUsrAsg, aOficinaUsrAsg;
 var aResPaso = new Array();
 var aRegistrosPNC = new Array();
 var aRegistrosSPNC = new Array();
 var aRegCausasPNC = new Array();
 var aRegAsignaPNC = new Array();
 var iSeguimiento = 0;
 var iEjercicioSel = 0;
 var iEjercicioSoli = 0;
 var iCveProductoSel = 0;
 var iCveConsecRes = 0;
 var iConsecutivoSel = 0;
 var iCveCausaSel = 0;
 var iCargado = 0;
 var iCount = 0;
 var lBandera = false;
 var aRegistro = new Array();
 var lRegistrado = false;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020031.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
     fSetWindowTitle();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"95%","","center","","0");


     ITRTD("",0,"","","center","top");
       InicioTabla("ETablaInfo",0,"100%","","","",1);
         ITR();
            ITD();
               TDEtiCampo(false,"EEtiqueta",0,"Usuario:","iNomUsuario","",80,50,"Usuario","fMayus(this);","","","","ECampo",3);
            FTD();
            if (!top.opener){
               ITD();
                  BtnImg("Buscar","lupa","fNavega();");
               FTD();
       	    }
         FTR();
         ITRTD("",6,"","","center","");
            InicioTabla("",0);
               fDefOficXUsr(false);
            FinTabla();
         FTDTR();
         ITR("","","","center","",2,"");
            TDEtiCampo(false,"EEtiqueta",0,"Ejercicio PNC:","iEjercicio","",4,4,"Ejercicio de la búsqueda","","","","","ECampo",0);
            TDEtiCampo(false,"EEtiqueta",0,"Consecutivo PNC:","iConsecutivoPNC","",10,10,"Consecutivo de Producto No Conforme","","","","","ECampo",0);
            TDEtiCampo(false,"EEtiqueta",0,"Ejercicio Solicitud:","iEjercicioSol","",4,4,"Ejercicio de la solicitud","","","","","ECampo",0);
            TDEtiCampo(false,"EEtiqueta",0,"Número de Solicitud:","iNumSolicitud","",10,10,"Número de Solicitud","","","","","ECampo",0);
         FTR();
       FinTabla();
     FTDTR();
     ITRTD("ETablaST",0,"95%","","center","top");
        TextoSimple("PRODUCTO NO CONFORME");
     FTDTR();
     ITRTD("",0,"","","center","top");
        IFrame("IListado20","100%","170","Listado.js","yes",true);
     FTDTR();

     ITRTD("ETablaST",0,"95%","","center","top");
        TextoSimple("CAUSAS DEL PRODUCTO NO CONFORME");
     FTDTR();
     ITRTD("",0,"","","center","top");
        IFrame("IListado21","100%","170","Listado.js","yes",true);
     FTDTR();
   FinTabla();
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("iCveUsuario",idUser);
     Hidden("iCveCausaPNC","");

     Hidden("iOficina","");
     Hidden("iDepto","");
     Hidden("iOficinaEnvia","");
     Hidden("iDeptoEnvia","");
   window.parent.iCveUsuarioM = idUser;
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   frm.iDepto.value="";
   FRMListado = fBuscaFrame("IListado20");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Origen,Ejercicio,Consecutivo,Ejercicio Sol., Núm Sol.,Producto,Registro,Usuario que Registró,Fecha de Resolución,");
   FRMListado.fSetAlinea("center,center,center,left,center,left,center,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,fecha,texto,fecha,");
   FRMListado.fSetCampos("9,0,1,10,11,3,4,5,6,");
   FRMListado.fSetSelReg(1);
   FRMListado1 = fBuscaFrame("IListado21");
   FRMListado1.fSetControl(self);
   FRMListado1.fSetTitulo("Descripción de la Causa,Área que corrige,Fecha de resolución,");
   FRMListado1.fSetAlinea("left,left,center,");
   FRMListado1.fSetDespliega("texto,texto,center,");
   FRMListado1.fSetCampos("1,9,3,");
   FRMListado1.fSetSelReg(2);
   frm.hdBoton.value="Primero";
   fDisabled(true,"iCveOficinaUsr,iCveDeptoUsr,iEjercicio,iConsecutivoPNC,iEjercicioSol,iNumSolicitud,");
   if (top.opener){
      if(top.opener.fEnviaDatosPNC)
         top.opener.fEnviaDatosPNC(window);
   }
   fTraeFechaActual();
   arreglo = new Array();
 }
 // Función que asigna valores predeterminados a oficina,depto y  proc.
 function fSetRegistrosPNC(aRegistros){
    frm.iEjercicio.value = "";
    frm.iConsecutivoPNC.value = "";
    fLlenaSelectFromValueInSelect(new Array, frm.iCveOficinaUsr.value, 0, frm.iCveDeptoUsr, 1, 5, false);
    fLlenaSelectFromValueInSelect(new Array, frm.iCveDeptoUsr.value, 0, frm.iCveDeptoUsr, 1, 5, false);
    aRegistrosPNC = aRegistros;  //Aguas
    FRMListado.fSetListado(aRegistros);
    FRMListado.fShow();
    fDisabled(true);
 }

 function fRegresaDatosPNC(){
   if (top.opener)
      if(top.opener.fRegresaDatosPNC)
         top.opener.fRegresaDatosPNC(top,aRegistrosPNC,aRegCausasPNC);
 }

 function fOficinaUsrOnChangeLocal(){
  if(!top.opener){
     fSelectSetIndexFromValue(frm.iCveDeptoUsr, frm.iDepto.value);
     fDeptoUsrOnChange();
  }
  window.parent.iCveOficinaM = frm.iCveOficinaUsr.value;
 }

 function fDeptoUsrOnChangeLocal(){
    window.parent.iCveDeptoM = frm.iCveDeptoUsr.value;
 }

 // LLAMADO al JSP específico para obtener datos del usuario
 function fCargaUsuario(){
   frm.hdLlave.value = "ICVEUSUARIO";
   frm.hdSelect.value = "SELECT CNOMBRE||' '|| CAPPATERNO||' '|| CAPMATERNO AS cNOMBREUSU FROM SEGUsuario" +
   " where ICVEUSUARIO="+idUser;
   fEngSubmite("pgConsulta.jsp","iNomUsuario");
 }

  // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   if(frm.iEjercicio.value==""){
     fAlert('El Campo "Ejercicio PNC" es necesario para la busqueda');
   }
   aRegistrosPNC = new Array();
   aRegistrosSPNC = new Array();
   aRegCausasPNC = new Array();
   aRegAsignaPNC = new Array();
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = "IEJERCICIO,ICONSECUTIVOPNC";
   select =
      "SELECT GRLREGISTROPNC.IEJERCICIO, "+
      "GRLREGISTROPNC.ICONSECUTIVOPNC, " +
      "GRLREGISTROPNC.ICVEPRODUCTO, " +
      "GRLPRODUCTO.CDSCPRODUCTO, " +
      "GRLREGISTROPNC.DTREGISTRO, "+
      "SEGUSUARIO.CNOMBRE||' '|| SEGUSUARIO.CAPPATERNO||' '|| SEGUSUARIO.CAPMATERNO AS USUARIO, " +//5
      "DTRESOLUCION, "+
      "TRAREGPNCETAPA.IEJERCICIO as iEjercicioSol, "+
      "TRAREGPNCETAPA.INUMSOLICITUD "+
      "FROM GRLREGISTROPNC " +
      "JOIN GRLPRODUCTO ON GRLREGISTROPNC.ICVEPRODUCTO = GRLPRODUCTO.ICVEPRODUCTO " +
      "JOIN SEGUSUARIO ON SEGUSUARIO.ICVEUSUARIO = GRLREGISTROPNC.ICVEUSUREGISTRO " +
      "LEFT JOIN TRAREGPNCETAPA ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC " +
      "AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC " +
      "WHERE ICVEOFICINA = " + frm.iCveOficinaUsr.value + " " +
      "AND ICVEDEPARTAMENTO = " + frm.iCveDeptoUsr.value;
   if(frm.iEjercicio.value != "")
      select = select + " AND GRLREGISTROPNC.IEJERCICIO = " + frm.iEjercicio.value;
   if(frm.iConsecutivoPNC.value != "")
      select = select + " AND GRLREGISTROPNC.ICONSECUTIVOPNC = " + frm.iConsecutivoPNC.value + " ";
   if(frm.iEjercicioSol.value != "")
      select = select + " AND TRAREGPNCETAPA.IEJERCICIO = " + frm.iEjercicioSol.value;
   if(frm.iNumSolicitud.value != "")
      select = select + " AND TRAREGPNCETAPA.INUMSOLICITUD = " + frm.iNumSolicitud.value + " ";
   select = select + " ORDER BY GRLREGISTROPNC.LRESUELTO, GRLREGISTROPNC.IEJERCICIO DESC, GRLREGISTROPNC.ICONSECUTIVOPNC DESC, " +
                           "GRLREGISTROPNC.DTREGISTRO DESC";
  frm.hdSelect.value = select;
  fEngSubmite("pgConsulta.jsp","Listado");
 }

 // LLAMADO a JSP para cargar las causas de Producto No Conforme
 function fNavega1(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   
   iEjercicioSel = frm.iEjercicio.value;
   frm.hdLlave.value = "IEJERCICIO,ICONSECUTIVOPNC,ICVEPRODUCTO,ICVECAUSAPNC";
   select2 = "SELECT GRLREGCAUSAPNC.ICVECAUSAPNC, GRLCAUSAPNC.CDSCCAUSAPNC, " +
      "SEGUSUARIO.CNOMBRE||' '|| SEGUSUARIO.CAPPATERNO||' '|| SEGUSUARIO.CAPMATERNO AS USUARIO, " +
      "GRLREGCAUSAPNC.DTRESOLUCION, GRLREGCAUSAPNC.CDSCOTRACAUSA, " +
      "ET.IORDEN, (" +
      "SELECT MAX  (ET1.IORDEN) " +
      " FROM TRAREGPNCETAPA ET1 " +
      " WHERE ET1.IEJERCICIO = " + iEjercicioSel +
      " AND ET1.INUMSOLICITUD = ET.INUMSOLICITUD) " +
      " AS IORD, " +
      "GRLREGISTROPNC.ICVEOFICINAASIGNADO, " +
      "GRLREGISTROPNC.ICVEDEPTOASIGNADO, " +
      "O.CDSCBREVE ||' - '||D.CDSCBREVE AS CDSCAREA "+
      "FROM GRLREGCAUSAPNC " +
      "JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = " + iEjercicioSel +
      " AND GRLREGISTROPNC.ICONSECUTIVOPNC = " + iConsecutivoSel +
      " JOIN SEGUSUARIO ON GRLREGCAUSAPNC.ICVEUSUCORRIGE = SEGUSUARIO.ICVEUSUARIO " +
      "JOIN GRLCAUSAPNC ON GRLCAUSAPNC.iCveProducto = GRLREGCAUSAPNC.iCveProducto " +
      " AND GRLCausaPNC.ICVECAUSAPNC = GRLREGCAUSAPNC.ICVECAUSAPNC "+
      "JOIN GRLPRODUCTO ON GRLPRODUCTO.ICVEPRODUCTO = GRLREGCAUSAPNC.ICVEPRODUCTO " +
      "LEFT JOIN TRAREGPNCETAPA ET ON ET.IEJERCICIOPNC = " + iEjercicioSel +
      " AND ET.ICONSECUTIVOPNC = " + iConsecutivoSel +
      "  JOIN GRLOFICINA O ON GRLREGISTROPNC.ICVEOFICINAASIGNADO = O.ICVEOFICINA " +
      "  JOIN GRLDEPARTAMENTO  D ON GRLREGISTROPNC.ICVEDEPTOASIGNADO = D.ICVEDEPARTAMENTO " +
      " WHERE GRLREGCAUSAPNC.IEJERCICIO = " + iEjercicioSel +
      " AND GRLREGCAUSAPNC.ICONSECUTIVOPNC = " + iConsecutivoSel +
      " AND GRLREGCAUSAPNC.ICVEPRODUCTO = " + iCveProductoSel;
   iSeguimiento = 0;
   frm.hdSelect.value = select2;
   fEngSubmite("pgConsulta.jsp","Listado1");
 }

 function fNavega2(){
   frm.hdLlave.value = "IEJERCICIO,ICONSECUTIVOPNC,ICVEPRODUCTO,ICVECAUSAPNC,ICVESEGUIMIENTO";
    select1 =
      "SELECT GRLSEGUIMIENTOPNC.IEJERCICIO, "+
      "GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC, " +
      "GRLSEGUIMIENTOPNC.ICVEPRODUCTO, "+
      "GRLPRODUCTO.CDSCPRODUCTO, " +
      "DATE(GRLSEGUIMIENTOPNC.TSMOMENTOSEGUIMIENTO) as dtMomento, " +
      "SEGUSUARIO.CNOMBRE||' '|| SEGUSUARIO.CAPPATERNO||' '|| SEGUSUARIO.CAPMATERNO AS USUARIO, " +
      "GRLREGISTROPNC.DTRESOLUCION, " +
      "GRLSEGUIMIENTOPNC.ICVESEGUIMIENTO, " +
      "GRLSEGUIMIENTOPNC.ICVECAUSAPNC, " +
      "TRAREGPNCETAPA.IEJERCICIO, "+
      "TRAREGPNCETAPA.INUMSOLICITUD "+
      "FROM GRLSEGUIMIENTOPNC " +
      "JOIN GRLPRODUCTO ON GRLSEGUIMIENTOPNC.ICVEPRODUCTO = GRLPRODUCTO.ICVEPRODUCTO " +
      "JOIN SEGUSUARIO ON SEGUSUARIO.ICVEUSUARIO = GRLSEGUIMIENTOPNC.ICVEUSUREGISTRA " +
      "JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = GRLSEGUIMIENTOPNC.IEJERCICIO " +
      "AND GRLREGISTROPNC.ICONSECUTIVOPNC = GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC " +
      "AND GRLREGISTROPNC.ICVEPRODUCTO = GRLSEGUIMIENTOPNC.ICVEPRODUCTO " +
      "LEFT JOIN TRAREGPNCETAPA ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC " +
      "AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC " +
      "WHERE " +
      " GRLSEGUIMIENTOPNC.ICVEOFICINAASIGNADO = " + frm.iCveOficinaUsr.value +
      " AND GRLSEGUIMIENTOPNC.ICVEDEPTOASIGNADO = " + frm.iCveDeptoUsr.value +
      " AND GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC NOT IN (SELECT GRLREGISTROPNC.ICONSECUTIVOPNC " +
      "FROM GRLREGISTROPNC " +
      "WHERE ICVEOFICINA = " + frm.iCveOficinaUsr.value + " " +
      "AND ICVEDEPARTAMENTO = " + frm.iCveDeptoUsr.value;
    if(frm.iEjercicio.value != "")
      select1 = select1 + " AND GRLSEGUIMIENTOPNC.IEJERCICIO = " + frm.iEjercicio.value;
    if(frm.iConsecutivoPNC.value != "")
      select1 = select1 + " AND GRLREGISTROPNC.ICONSECUTIVOPNC = " + frm.iConsecutivoPNC.value + " ";
    select1 = select1 + " ORDER BY GRLREGISTROPNC.LRESUELTO, GRLREGISTROPNC.IEJERCICIO DESC, " +
                        " GRLREGISTROPNC.ICONSECUTIVOPNC DESC, " +
                           " GRLREGISTROPNC.DTREGISTRO DESC) ";
    if(frm.iEjercicio.value != "")
         select1 = select1 + " AND GRLSEGUIMIENTOPNC.IEJERCICIO = " + frm.iEjercicio.value;
    if(frm.iConsecutivoPNC.value != "")
      select1 = select1 + " AND GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC = " + frm.iConsecutivoPNC.value;
   if(frm.iEjercicio.value != "")
      select1 = select1 + " AND TRAREGPNCETAPA.IEJERCICIO = " + frm.iEjercicio.value;
   if(frm.iNumSolicitud.value != "")
      select1 = select1 + " AND TRAREGPNCETAPA.INUMSOLICITUD = " + frm.iNumSolicitud.value + " ";
    select1 = select1 + " ORDER BY GRLREGISTROPNC.LRESUELTO, GRLSEGUIMIENTOPNC.IEJERCICIO DESC, " +
            " GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC DESC";
    frm.hdSelect.value = select1;
    fEngSubmite("pgConsulta.jsp","Listado2");
 }

 // Carga los registros de PNC asignados por primera vez a Ofic. Depto.
 function fNavega3(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = ""; //"IEJERCICIO,ICONSECUTIVOPNC";
   select3 =
      "SELECT "+
      "GRLREGISTROPNC.IEJERCICIO, "+
      "GRLREGISTROPNC.ICONSECUTIVOPNC, " +
      "GRLREGISTROPNC.ICVEPRODUCTO, " +
      "GRLPRODUCTO.CDSCPRODUCTO, " +
      "GRLREGISTROPNC.DTREGISTRO, "+
      "SEGUSUARIO.CNOMBRE||' '|| SEGUSUARIO.CAPPATERNO||' '|| SEGUSUARIO.CAPMATERNO AS USUARIO, " +
      "DTRESOLUCION, "+
      "TRAREGPNCETAPA.IEJERCICIO, "+
      "TRAREGPNCETAPA.INUMSOLICITUD "+
      "FROM GRLREGISTROPNC " +
      "JOIN GRLPRODUCTO ON GRLREGISTROPNC.ICVEPRODUCTO = GRLPRODUCTO.ICVEPRODUCTO " +
      "JOIN SEGUSUARIO ON SEGUSUARIO.ICVEUSUARIO = GRLREGISTROPNC.ICVEUSUREGISTRO " +
      "LEFT JOIN TRAREGPNCETAPA ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC " +
      "AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC " +
      "WHERE ICVEOFICINAASIGNADO = " + frm.iCveOficinaUsr.value + " " +
      " AND ICVEDEPTOASIGNADO = " + frm.iCveDeptoUsr.value +
      " AND ((ICVEOFICINA <> " + frm.iCveOficinaUsr.value + ") OR " +
      " (ICVEOFICINA = " + frm.iCveOficinaUsr.value + " AND ICVEDEPARTAMENTO <> " + frm.iCveDeptoUsr.value + ")) ";
   if(frm.iEjercicio.value != "")
      select3 = select3 + " AND GRLREGISTROPNC.IEJERCICIO = " + frm.iEjercicio.value;
   if(frm.iConsecutivoPNC.value != "")
      select3 = select3 + " AND GRLREGISTROPNC.ICONSECUTIVOPNC = " + frm.iConsecutivoPNC.value + " ";
   if(frm.iEjercicioSol.value != "")
      select3 = select3 + " AND TRAREGPNCETAPA.IEJERCICIO = " + frm.iEjercicioSol.value;
   if(frm.iNumSolicitud.value != "")
      select3 = select3 + " AND TRAREGPNCETAPA.INUMSOLICITUD = " + frm.iNumSolicitud.value + " ";

   select3 = select3 + " AND GRLREGISTROPNC.ICONSECUTIVOPNC NOT IN (SELECT SPNC.ICONSECUTIVOPNC " +
      " FROM GRLSEGUIMIENTOPNC SPNC WHERE SPNC.ICVEOFICINAASIGNADO = " + frm.iCveOficinaUsr.value +
      " AND SPNC.ICVEDEPTOASIGNADO = " + frm.iCveDeptoUsr.value +
      " AND SPNC.IEJERCICIO = " + frm.iEjercicio.value + ") ";
   select3 = select3 + " ORDER BY GRLREGISTROPNC.LRESUELTO, GRLREGISTROPNC.IEJERCICIO DESC, GRLREGISTROPNC.ICONSECUTIVOPNC DESC, " +
                           "GRLREGISTROPNC.DTREGISTRO DESC";
  frm.hdSelect.value = select3;
  fEngSubmite("pgConsulta.jsp","Listado3");
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
   fResOficDeptoUsr(aRes,cId,cError,false);
   window.parent.iCveOficinaM = frm.iCveOficinaUsr.value;
   window.parent.iCveDeptoM = frm.iCveDeptoUsr.value;
   if(cId == "Guardar"){
     frm.iConsec.value = iNConsecutivo;
     fRegresaDatosPNC();
   }
   if(cId == "idFechaActual" && cError==""){
     if(!top.opener){
        frm.iEjercicio.value = aRes[1][2];
        //frm.iEjercicioSol.value = aRes[1][2];
     }
     fCargaUsuario();
   }
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     aResPaso = new Array();
     for(i=0; i < aRes.length; i++){
        aResPaso[i] = new Array();
        aResPaso[i] = fCopiaArreglo(aRes[i]);
        aResPaso[i][7] = "";
        aResPaso[i][8] = "";
        aResPaso[i][9] = "Registrado"; // Registrado por el área
        aResPaso[i][10] = aRes[i][7];
        aResPaso[i][11]= aRes[i][8];
     }
     aResListado = fCopiaArregloBidim(aResPaso);
     aRegistrosPNC = fCopiaArregloBidim(aResPaso);
     fNavega3();
   }
   if(cId == "Listado3" && cError==""){
     frm.hdRowPag.value = iRowPag;
     var iReg = aRes.length;
     aResPaso = new Array();
     aResListado = new Array();
     aRegAsignaPNC = new Array();
     for(i=0; i < iReg; i++){
        aResPaso[i] = new Array();
        aResPaso[i][0] = aRes[i][0];
        aResPaso[i][1] = aRes[i][1];
        aResPaso[i][2] = aRes[i][2];
        aResPaso[i][3] = aRes[i][3];
        aResPaso[i][4] = aRes[i][4];
        aResPaso[i][5] = aRes[i][5];
        aResPaso[i][6] = aRes[i][6];
        aResPaso[i][7] = "";
        aResPaso[i][8] = "";
        aResPaso[i][9] = "Asignado";  // Asignado al área por otra área
        aResPaso[i][10] = aRes[i][7];
        aResPaso[i][11] = aRes[i][8];
     }
     aResListado = fCopiaArregloBidim(aResPaso);
    // aRegistrosPNC = fCopiaArregloBidim(aRes);
     aRegAsignaPNC = fCopiaArregloBidim(aResListado);
     fNavega2();
   }
   if(cId == "Listado1" && cError==""){
     frm.hdRowPag.value = iRowPag;
     aResListado1 = fCopiaArregloBidim(aRes);
     for(i=0; i < aResListado1.length; i++){
       if(aResListado1[i][0] == 0){
         aResListado1[i][1] = aResListado1[i][4];
       }
     }
     aRegCausasPNC = fCopiaArregloBidim(aResListado1);
     window.parent.aRegCausas = fCopiaArregloBidim(aResListado1);
     FRMListado1.fSetListado(aResListado1);
     FRMListado1.fShow();
     FRMListado1.fSetLlave(cLlave);
     FRMListado1.fSetDisabled(false);
     if(window.parent.cCausaRetM != ""){
        fReposicionaListado(FRMListado1,"1",window.parent.cCausaRetM);
     }
   }
   if(cId == "Listado2" && cError==""){
     iCargado = 1;
     aRegistrosSPNC = new Array();
     aRegistrosSPNC = fCopiaArregloBidim(aRes);

     iConAnt = 0;
     iCausaAnt = 0;
     iPosR = 0;
     aRegistrosSPNC1 = new Array();
     jPosA = 0;
     jPosS = 0;
     for(jPosS = 0; jPosS < aRegistrosSPNC.length; jPosS++){
         if(aRegistrosSPNC[jPosS][0] != iConAnt || aRegistrosSPNC[jPosS][1] != iCausaAnt){
           iConAnt = aRegistrosSPNC[jPosS][0];
           iCausaAnt = aRegistrosSPNC[jPosS][1];
           aRegistrosSPNC1[iPosR] = new Array();
           aRegistrosSPNC1[iPosR][0] = aRegistrosSPNC[jPosS][0];
           aRegistrosSPNC1[iPosR][1] = aRegistrosSPNC[jPosS][1];
           aRegistrosSPNC1[iPosR][2] = aRegistrosSPNC[jPosS][2];
           aRegistrosSPNC1[iPosR][3] = aRegistrosSPNC[jPosS][3];
           aRegistrosSPNC1[iPosR][4] = aRegistrosSPNC[jPosS][4];
           aRegistrosSPNC1[iPosR][5] = aRegistrosSPNC[jPosS][5];
           aRegistrosSPNC1[iPosR][6] = aRegistrosSPNC[jPosS][6];
           aRegistrosSPNC1[iPosR][7] = aRegistrosSPNC[jPosS][7];
           aRegistrosSPNC1[iPosR][8] = aRegistrosSPNC[jPosS][8];
           aRegistrosSPNC1[iPosR][9] = "Asignado";
           aRegistrosSPNC1[iPosR][10] = aRegistrosSPNC[jPosS][9];
           aRegistrosSPNC1[iPosR][11] = aRegistrosSPNC[jPosS][10];
           iPosR = iPosR + 1;
         }
     }

     jPosS = 0;
     jPosA = 0;
     aRegistrosSPNC = new Array();
     while(jPosA < aRegAsignaPNC.length || jPosS < aRegistrosSPNC1.length){
       if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] == "" && aRegistrosSPNC1[jPosS][6] == "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) < parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegistrosSPNC1[jPosS]);
          jPosS++;

       }else if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] == "" && aRegistrosSPNC1[jPosS][6] == "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) > parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegAsignaPNC[jPosA]);
          jPosA++;

       }else if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] == "" && aRegistrosSPNC1[jPosS][6] != "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) < parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegAsignaPNC[jPosA]);
          jPosA++;

       }else if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] != "" && aRegistrosSPNC1[jPosS][6] == "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) < parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegistrosSPNC1[jPosS]);
          jPosS++;

       }else if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] == "" && aRegistrosSPNC1[jPosS][6] != "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) > parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegistrosSPNC1[jPosS]);
          jPosS++;

       }else if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] != "" && aRegistrosSPNC1[jPosS][6] == "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) > parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegistrosSPNC1[jPosS]);
          jPosS++;


       }else if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] != "" && aRegistrosSPNC1[jPosS][6] != "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) < parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegistrosSPNC1[jPosS]);
          jPosS++;

       }else if((jPosA < aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length) &&
          aRegAsignaPNC[jPosA][6] != "" && aRegistrosSPNC1[jPosS][6] != "" &&
          parseInt(aRegAsignaPNC[jPosA][1],10) > parseInt(aRegistrosSPNC1[jPosS][1],10)){
          fAgregaReg(aRegAsignaPNC[jPosA]);
          jPosA++;

       }else if(jPosA >= aRegAsignaPNC.length && jPosS < aRegistrosSPNC1.length){
          fAgregaReg(aRegistrosSPNC1[jPosS]);
          jPosS++;

       }else if(jPosA < aRegAsignaPNC.length && jPosS >= aRegistrosSPNC1.length){
          fAgregaReg(aRegAsignaPNC[jPosA]);
          jPosA++;
       }
       else jPosA++;
     }

     iFin = aRegistrosSPNC.length;
     for(iPos = 0; iPos < aRegistrosPNC.length; iPos++)  aRegistrosSPNC[iFin + iPos] = aRegistrosPNC[iPos];
     FRMListado.fSetListado(aRegistrosSPNC);
     FRMListado.fShow();
     if(iCveConsecRes != 0){
        fReposicionaListado(FRMListado,"1",iCveConsecRes);
        iCveConsecRes = 0;
        fNavega1();
     }
   }
   if(cId == "iNomUsuario" && cError==""){
     frm.iNomUsuario.value=aRes[0][0];
     frm.iCveUsuario.value = idUser;
     fCargaOficDeptoUsr(false);
   }
   if(cId == "idDarSeguimiento" && cError==""){
     if(aRes.length > 0){
       if(aRes[0][1] == frm.iCveOficinaUsr.value &&
          aRes[0][2] == frm.iCveDeptoUsr.value){
          window.parent.iEditSeguimiento = 1;
       }else{
          window.parent.iEditSeguimiento = 0;
       }
     }else{
       window.parent.iEditSeguimiento = 1;
     }
     fCargaCausas(aRegistro);
   }
 }

 function fAgregaReg(aReg){
    ind = aRegistrosSPNC.length;
    aRegistrosSPNC[ind] = new Array();
    aRegistrosSPNC[ind][0] = aReg[0];
    aRegistrosSPNC[ind][1] = aReg[1];
    aRegistrosSPNC[ind][2] = aReg[2];
    aRegistrosSPNC[ind][3] = aReg[3];
    aRegistrosSPNC[ind][4] = aReg[4];
    aRegistrosSPNC[ind][5] = aReg[5];
    aRegistrosSPNC[ind][6] = aReg[6];
    aRegistrosSPNC[ind][7] = aReg[7];
    aRegistrosSPNC[ind][8] = aReg[8];
    aRegistrosSPNC[ind][9] = "Asignado";
    aRegistrosSPNC[ind][10] = aReg[10];
    aRegistrosSPNC[ind][11] = aReg[11];
 }

 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    aRegistro = new Array();
    aRegistro = aDato;// fCopiaArregloBidim(aDato);
    if(aDato[9] == "Asignado")
       window.parent.lAsignado = true;
    else
       window.parent.lAsignado = false;
    fBuscaSeg(aDato);
 }

 function fCargaCausas(aDato){
    if(aDato[0] != ""){
      if(iCveConsecRes != ""){
      }
      iEjercicioSel = aDato[0];
      iEjercicioSoli = aDato[10];
      window.parent.iEjercicioM = iEjercicioSel;
      window.parent.iEjercicioS = iEjercicioSoli;
      iConsecutivoSel = aDato[1];
      window.parent.iConsecutivoPNCM = iConsecutivoSel;
      iCveProductoSel = aDato[2];
      window.parent.iCveProductoM = iCveProductoSel;
      if(aDato.length > 7){
         iCveCausaSel = aDato[8];
         iSeguimiento = 1;
      }
      fNavega1();
   }
 }

 function fBuscaSeg(aDato){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   frm.hdLlave.value = "";
   if(aDato[0] && aDato[1]){
     selectS =  "select icveseguimiento, icveoficinaasignado, icvedeptoasignado " +
     "from GRLSEGUIMIENTOPNC " +
     "where iejercicio = " + aDato[0] +
     " and iconsecutivopnc = " + aDato[1] +
     " order by icveseguimiento desc ";
     frm.hdSelect.value = selectS;
     fEngSubmite("pgConsulta.jsp","idDarSeguimiento");
   }
 }

 function fSelReg2(aDato){
   if(aDato[1] != "")
      window.parent.cCausaM = aDato[1];
 }
 // Selecciona un registro de causas cuando se procede de otro folder
 function fEjecutaSel(){
     if(window.parent.cCausaRetM != ""){
        iCveConsecRes = iConsecutivoSel;
        if(!top.opener)
           fNavega();
        fReposicionaListado(FRMListado1,"1",window.parent.cCausaRetM);
       window.parent.cCausaRetM = "";
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



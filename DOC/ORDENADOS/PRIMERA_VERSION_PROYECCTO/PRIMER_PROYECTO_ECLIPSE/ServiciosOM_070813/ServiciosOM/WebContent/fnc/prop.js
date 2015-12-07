// Propiedades del Editor por Aplication Server
  var cRutaIniWebSrv = '/ServiciosOM/';  

// Propiedades del Editor comunes
  var iNDSADM       = 11;
  var iIDSistema    = 44;
  var cRutaADMSEGPWD = 'http://aplicaciones4.sct.gob.mx/sysadmin_ext/jsp/content/mx/sct/utilidades/cambio_contrasena.jsp';
  var cRutaSesionExpirada = 'about:blank';
  var cAlertMsgGen  = "Sistema Institucional de Gestión de Trámites:";
  var cTituloGen    = "SIPYMM - CGPMM - SCT - ";
  var cRutaImgLocal = 'ServiciosOM/wwwrooting/img/';
  var cRutaDAO      = "gob.sct.sipmm.dao"; 

  var lDesarrollo  = true;
  var lBtnDerecho  = true;
  var lBtnReporte  = false;
  var msgcder      = "Función del Mouse No Habilitada.";
  var cRutaFuncs     = cRutaIniWebSrv + 'fnc/';
  var cRutaEstaticos = cRutaIniWebSrv + 'wwwrooting/';
  var cRutaImgServer = cRutaEstaticos + 'img/';
  var cRutaEstilos   = cRutaEstaticos + 'estilos/';
  var cRutaAyuda     = cRutaEstaticos + 'ayuda/';
  var cRutaPlantilla = cRutaEstaticos + 'descargar/';
  var cRutaManualUsr = cRutaEstaticos + 'MUsuario/';
  var cEstilos     = 'estilos.css';
  var cPagGral     = 'CDPagGral.jsp';
  var cPagNva      = 'CDPagNva.jsp';
  var cPagIni      = 'CD/frmmi';
  var cPagFRMMI    = 'CDfrmmi.jsp';
  var cRutaProg    = '/ServiciosOM/';
  var iNumRegLista        = 10;
  var iMaxNumRegLista     = 150;
  var cFGColorSelList     = 'd0d0d0';
  var cColorBGPanel       = 'E9E9E9';
  var cBGColorSelList     = '8CC640';
  var cBGColorGrid        = "DEECEC";
  var EFolder             = "FFFFFF";
  var cColorPesFolder     = "949393";
  var EFolderDes          = "FFFFFF";
  var cFondoCeldaISO      = "#31619C";
  var iTiempoVerificacion = 10;
  var cExtensiónAyuda     = '.pdf';
  var cColorGenJS         = 'E9E9E9'; //'cdcdcc';//b0cb52,c5d882
  var cColorGenJSIni      = 'ffffff'; //'8CC640';
  //var cColorGenJSFolder   = '63A58C';
  var cColorGenJSFolder   = '63A58C'; //'018c45';
  var cColorLigasWiz      = '004000';
  var cColorBGWiz         = '420000'; //'420000';
  var cColorGenJSInicio   = 'ffffff';
  var cColorLetraArbol    = "BLACK";
  var cColorFondoArbol    = "WHITE";
  var cColorRamaArbol     = "GREEN";
  var cColorArbolSel      = 'DCE145';
  
  var lFormatoDMY         = true;
  var cClavesModulo; // separados y terminados por coma (,)
  var cNumerosRep;   // separados y terminados por coma (,)
  var cFiltrosRep;   // separados y terminados por el separador de variable cSeparadorRep
  var cSeparadorRep = "$";
  var iIntervaloDef = 45000;
  var iIntervaloRep = 100000;
  var iPaisDefault = 1;
  var iOficinaCentral = 1;
  var iCveCertifLiterales = 5;
  var iCveCertifDocs = 6;
  var iCveDeptoTripulantes = -2;
  var iCveTipoUniMedPotencia    = 2;
  var iCveTipoUniMedCombustible = 3;
  var iCveDeptoVentanillaUnica = 95;
  var iCveDeptoProgTrabajo = 30;
  var iTerminal = 2;
  var iCveTramiteSenalDist = 3;
  var lOmitePermiso = false;
  var iMaxPermisosServicios = 7;
  var iInspeccionGrupoCertMatriculadas  = 2;
  var iInspeccionAplicaCertificadoMatriculadas = 5;
  var cCapCondMet0 = "3,96,9,8,10,7,49,48,46,44,28,22,14,25,96,90,33,15,20";
  var cCapCondMet1 = "56,57,61,62,63,51,52,11,12,64,37,40,39";
  
  //Para el funcionamiento de elicencias
  var cRutaProgMM = 'http://localhost:7001/ServiciosOM/';
  var cRutaProgLic = 'http://aplicaciones1.sct.gob.mx/elic/';


function fSetIntervalo(lReporte, iInter){ // Solo se debe usar bajo condiciones específicas
  var iIntervalo = (lReporte)?iIntervaloRep:iIntervaloDef;
  if (iInter)
    iIntervalo = iInter;
  if (top.FRMSI)
    if (top.FRMSI.fVerAnswer)
      top.FRMSI.iPID = setInterval('top.FRMSI.fVerAnswer();', iIntervalo);
}

function fAbreSubWindowPermisos(cNomPag, cAncho, cAlto, lMenuBar){
  cMenuBar = 'no';
  if(lMenuBar)
    cMenuBar = (lMenuBar==true)?'yes':'no';
  if(!cAncho)
    cAncho = "800";
  if (!cAlto)
    cAlto = "600";
  if (fGetPermisos(cNomPag + ".js") != 2)
    fAbreSubWindow(true,cNomPag,cMenuBar,"yes","yes","yes",cAncho,cAlto,"","");
  else
    fAlert("No tiene permiso de ejecutar esta acción");
}


function fAbreSubWindowSinPermisos(cNomPag, cAncho, cAlto){
  if(!cAncho)
    cAncho = "800";
  if (!cAlto)
    cAlto = "600";
  fAbreSubWindow(true,cNomPag,"no","yes","yes","yes",cAncho,cAlto,"","");
}

function fAbreSolicitante(){
  fAbreSubWindowPermisos("pg111010011","800","585");
}

function fAbreSolicitanteSinPermisos(){
  fAbreSubWindowSinPermisos("pg111010011","800","585");
}

function fAbreImportaDatos(){
  fAbreSubWindowPermisos("pgImportaDatos","700","500");
}

function fAbreBuscaCitaCIS(){
  fAbreSubWindowPermisos("pgBuscaCitaCIS","800","585");
}

function fAbreSolicitudesRelacionadas(){
  fAbreSubWindowPermisos("pgMuestraSolRel","800","585");
}

function fAbreMuestraReq(){
  fAbreSubWindowPermisos("pgMuestraRequisitos","800","460");
}

function fAbreRegistroPNC(){
  fAbreSubWindowPermisos("pg110020020","800","550");
}

function fAbreConsultaSeguimientoPNC(){
  fAbreSubWindowPermisos("pg110020030","800","560");
}

function fAbreValidaRequisito(){
  fAbreSubWindowPermisos("pgCalificaRequisito","800","415");
}

function fAbreGeneraMovimientos(){
  fAbreSubWindowPermisos( "pg111020170","920","610");
}

function fAbreFichaIngresos(){
  fAbreSubWindowPermisos("pg111020171","800","615");
}

function fAbreRegistraPago(){
  fAbreSubWindowPermisos( "pgRegistraPago","800","515");
}

function fAbreBuscaSolicitud(){
  fAbreSubWindowPermisos("pg111020015","800","405");
}

function fRegDocumentos(){
  fAbreSubWindowPermisos("pg110000010","800","515");
}

function fAbreAdjuntaDocs(){
  fAbreSubWindowPermisos("pg110000011","400","95");
}

function fAbreConsultaDocs(){
  fAbreSubWindowPermisos("pg110000013","800","600",true);
}

function fAbreRegVehiculos(){
  fAbreSubWindowPermisos("pg110030040","800","515");
}

function fAbreBuscaEmbarcacionGral(){
  fAbreSubWindowPermisos("pgBuscaEmbarcacionGral","1100","515");
}

function fAbreBuscaEmbarcacion(){
  fAbreSubWindowPermisos("pgBuscaEmbarcacion","800","530");
}

function fAbreBuscaVehiculo(){
  fAbreSubWindowPermisos("pgBuscaVehiculo","800","520");
}

function fAbreBuscaConcesion(){
  fAbreSubWindowPermisos("pgBuscaConcesion","800","545");
}

function fAbreBuscaSenal(){
  fAbreSubWindowPermisos("pgBuscaSenal","810","470");
}

function fAbreBuscaArribo(){
  fAbreSubWindowPermisos("pgBuscaArribo","800","470");
}

function fAbreBuscaProgMaestroDesPort(){
  fAbreSubWindowPermisos("pgBuscaPMDP","800","600");
}

function fAbreConsultaReglasOpnPort(){
  fAbreSubWindowPermisos("pgConsRegOpPto","800","600");
}

function fSegOpiniones(){
  fAbreSubWindowPermisos("pg111020130","800","650");
}

function fAsociacionPlanos(){
  fAbreSubWindowPermisos("pg119220110","800","615");
}

function fAbreBuscaConcesionZF(){
  fAbreSubWindowPermisos("pgBuscaCZF","800","450");
}

function fAbreBuscaAutorizacionIA(){
  fAbreSubWindowPermisos("pgBuscaCZF","800","450");
}

function fAbreBuscaDictamenSEM(){
  fAbreSubWindowPermisos("pg116020140","800","600");
}

function fAbreBuscaContrato(){
  fAbreSubWindowPermisos("pgBuscaContrato","800","600");
}
function fReportes(){
  cMensaje = "";
  if (!cClavesModulo)
    cClavesModulo = "";
  if (!cNumerosRep)
    cNumerosRep = "";
  if (!cFiltrosRep)
    cFiltrosRep = "";
  if (cClavesModulo == "" || cNumerosRep == "")
    cMensaje += "\nNo esta configurado ningún reporte para esta pantalla";
  if ((cClavesModulo.length > 0) && (cNumerosRep.length > 0)){
    cNumEntMod = fNumEntries(cClavesModulo,",");
    cNumEntRep = fNumEntries(cNumerosRep,",");
    cNumEntFil = fNumEntries(cFiltrosRep,"$");
    if (cNumEntMod != cNumEntRep || cNumEntMod != cNumEntFil || cNumEntRep != cNumEntFil)
      cMensaje += "\nEl número de parámetros de módulos, reportes y filtros no concuerdan";
  }
  if (cMensaje != ""){
    fAlert(cMensaje);
    return;
  }
  fAbreSubWindowPermisos("pg110000020","580","425");
}

// Funcion que regresa una copia del arreglo proporcionado para evitar la pérdidad e apuntadores
function fCopiaArreglo(aCopiar){
  aTemp = new Array();
  for (var i=0; i<aCopiar.length; i++)
    aTemp[aTemp.length] = aCopiar[i];
  return aTemp;
}

function fGetDateSQL(cFecha, lOmiteSep){ // de: 02/03/2006 (dd/mm/aaaa)  a: 2006-03-02 (aaaa-mm-dd) ó 20060302(aaaammdd)
  cSep = (lOmiteSep)?"":"-";
  return cFecha.substring(6,10) + cSep + cFecha.substring(3,5) + cSep + cFecha.substring(0,2);
}

//Verifica q la fecha inicial no sea mayor a la fecha final
//La fechas se reciben en formato "dd/mm/aaaa"
function fComparaFecha(fechaIni, fechaFin, permitirIgual){
  return (eval("parseInt(fGetDateSQL(fechaIni,true),10)" +
               ((permitirIgual)?" <= ":" < ") +
               "parseInt(fGetDateSQL(fechaFin,true),10)"));
}

//Verifica q la fecha inicial no sea mayor a la fecha final
//La fechas se reciben en formato "dd/mm/aaaa"
//Las horas se reciben en formato "hh:mm"
function fComparaFechaHora(fechaIni, horaIni, fechaFin, horaFin, permitirIgual){
  return (eval("parseInt(fGetDateSQL(fechaIni,true)+horaIni.substring(0,2)+horaIni.substring(3,5),10)" +
               ((permitirIgual)?" <= ":" < ") +
               "parseInt(fGetDateSQL(fechaFin,true)+horaFin.substring(0,2)+horaFin.substring(3,5),10)"));
}

 //Regresa la fecha actual del servidor
 //Ej para capturar la fecha y ejercicio en el js que se ejecute esta función:
 //en el fResultado poner las sig. líneas
 //  if(cId == "idFechaActual" && cError==""){
 //    frm.fechaActual.value = aRes[0,0];
 //    frm.iEjercicioActual.value = aRes[1][2];
 //  }
 function fTraeFechaActual(){
   return fEngSubmite("pgGRLFechayHora.jsp","idFechaActual");
 }

function fLlenaSelectFromValueInSelect(aSource, aValues, aColsSource, selectTarget, colTarget, colDescTarget, lSeleccione){
// Ejemplos de llamado a la función:
//     fLlenaSelectFromValueInSelect(aOficinaDeptoUsr, new Array(top.fGetUsrID(),frm.iCveOficinaUsr.value), new Array("0","1"), frm.iCveDeptoUsr, 2, 8);
//     fLlenaSelectFromValueInSelect(aOficinaDeptoUsr, frm.iCveOficinaUsr.value, 1, frm.iCveDeptoUsr, 2, 8);
  if(!lSeleccione)
    lSeleccione = false;
  var aTemp = new Array();
  for (var i=0; i<aSource.length; i++){
    lLlaveIgual = true;
    if (aColsSource.length && aColsSource.length > 0 && aValues.length > 0){
      for (var x=0; x<aColsSource.length; x++){
        if (aSource[i][aColsSource[x]] != aValues[x]){
          lLlaveIgual = false;
          break;
        }
      }
    }else{
      if (aSource[i][aColsSource] != aValues)
        lLlaveIgual = false;
    }
    if (lLlaveIgual){
      lEncontrado = false;
      for (var j=0; j<aTemp.length; j++){
        if (aTemp[j][colTarget] == aSource[i][colTarget]){
          lEncontrado = true;
          break;
        }
      }
      if (!lEncontrado)
        aTemp[aTemp.length] = fCopiaArreglo(aSource[i]);
    }
  }
  fFillSelect(selectTarget,aTemp,lSeleccione,selectTarget.value,colTarget,colDescTarget);
}

// Creación de objetos para manejo de trámite y modalidad, requiere fTramiteOnChange, fModalidadOnChange, fCargaTramites, fResTramiteModalidad
function fRequisitoModalidad(lRenglonIntermedio){
  var cTx;
  cTx = ITRTD("EEtiquetaC",0,"100%","20","center")+
      InicioTabla("",0,"","","center")+
        ITR()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Trámite:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Text(false,"cCveTramite","",11,10,"Teclee la clave interna del trámite para ubicarlo","fTramiteOnChange();this.value='';"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramite, this);\"","",true,true)+
            Select("iCveTramite","fTramiteOnChange(true);")+
          FTD();
  if(lRenglonIntermedio)
    cTx += FITR();
  cTx += ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Modalidad:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveModalidad","if(fModalidadOnChange)fModalidadOnChange();")+
          FTD()+
        FTR()+
      FinTabla()+
    FTDTR();
  return cTx;
}

function fTramiteOnChange(lForzar) {
  if((frm.cCveTramite && frm.cCveTramite.value != "") || lForzar == true){
    frm.cCveTramite.value="";
    if (frm.iCveTramite && frm.iCveTramite.value && frm.iCveTramite.value !="" ){
      frm.hdBoton.value = "GetModalidadesTramite";
      frm.hdFiltro.value = " CT.iCveTramite = " + frm.iCveTramite.value + " AND lVigente = 1 AND CT.lActivo=1 ";
      frm.hdOrden.value = " cDscModalidad "
      fSelectSetIndexFromValue(frm.iCveModalidad, "-1");
      fEngSubmite("pgTRAModalidad.jsp","CIDModalidad");
    }
    if (window.fTramiteOnChangeLocal)
      window.fTramiteOnChangeLocal();
    else if (document.fTramiteOnChangeLocal)
      document.fTramiteOnChangeLocal();
  }
}

function fModalidadOnChange(){
  if (window.fModalidadOnChangeLocal)
    window.fModalidadOnChangeLocal();
  else if (document.fModalidadOnChangeLocal)
    document.fModalidadOnChangeLocal();
}

function fCargaTramites(){
  frm.hdBoton.value="GetTramites";
  fEngSubmite("pgTRACatTramite.jsp","CIDTramite");
}

function fResTramiteModalidad(aRes,cId,cError,lQuitarSeleccioneModalidad){
  lSeleccione = true;
  if(lQuitarSeleccioneModalidad)
    lSeleccione = false;
  if(cId == "CIDTramite" && cError==""){
    fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,6);
    fTramiteOnChange();
  }

  if(cId == "CIDModalidad" && cError==""){
    fFillSelect(frm.iCveModalidad,aRes,lSeleccione,frm.iCveModalidad.value,0,1);
    if(!aRes || aRes.length == 0 || !lSeleccione)
      fModalidadOnChange();
  }
}

// Creación de objetos para manejo de oficinas y departamentos del usuario, requiere fOficinaUsrOnChange, fDeptoUsrOnChange, fCargaOficDeptoUsr, fResOficDeptoUsr
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
  cTx += Select("iCveOficinaUsr","fOficinaUsrOnChange(" + lTodas + "," + lSeleccione +");");
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
      FinTabla()+
    FTDTR();
  return cTx;
}

var aOficinaDeptoUsr, aOficinaUsr;
function fOficinaUsrOnChange(lTodas, lSeleccione){
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

function fCargaOficDeptoUsr(lTodas){
  usrBuscar = "";
  usrBuscar = fGetIdUsrSesion();
  if (frm.hdLlave && frm.hdSelect){
    if (!lTodas){
      frm.hdLlave.value = "GRLUsuarioXOfic.iCveUsuario,GRLUsuarioXOfic.iCveOficina,GRLUsuarioXOfic.iCveDepartamento";
      frm.hdSelect.value = "SELECT GRLUsuarioXOfic.iCveUsuario, GRLUsuarioXOfic.iCveOficina, GRLUsuarioXOfic.iCveDepartamento, "+
                           "  GRLDeptoXOfic.cTitular AS cTitularDepOfic, "+
                           "  GRLOficina.cDscOficina, GRLOficina.cDscBreve AS cDscBreveOficina, GRLOficina.cTitular AS cTitularOficina, "+
                           "  GRLDepartamento.cDscDepartamento, GRLDepartamento.cDscBreve AS cDscBreveDepto "+
                           "FROM GRLUsuarioXOfic "+
                           "JOIN GRLDeptoXOfic ON  GRLDeptoXOfic.iCveOficina = GRLUsuarioXOfic.iCveOficina "+
                           "     AND GRLDeptoXOfic.iCveDepartamento = GRLUsuarioXOfic.iCveDepartamento "+
                           "JOIN GRLOficina ON GRLOficina.iCveOficina = GRLUsuarioXOfic.iCveOficina "+
                           "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLUsuarioXOfic.iCveDepartamento "+
                           "WHERE GRLUsuarioXOfic.iCveUsuario = "+ usrBuscar +
                           "  AND GRLUsuarioXOfic.iCveDepartamento >= 0 "+
                           "ORDER BY cDscBreveOficina, cDscBreveDepto";
    }
    if (lTodas){
      frm.hdLlave.value = "GRLDeptoXOfic.iCveOficina,GRLDeptoXOfic.iCveDepartamento";
      frm.hdSelect.value = "SELECT GRLDeptoXOfic.iCveOficina, GRLDeptoXOfic.iCveDepartamento, "+
                           "GRLOficina.cDscOficina, GRLOficina.cDscBreve as cDscBreveOfic, "+
                           "GRLDepartamento.cDscDepartamento, GRLDepartamento.cDscBreve as cDscBreveDepto "+
                           "FROM GRLDeptoXOfic "+
                           "JOIN GRLOficina ON GRLOficina.iCveOficina = GRLDeptoXOfic.iCveOficina "+
                           "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLDeptoXOfic.iCveDepartamento "+
                           "WHERE GRLDeptoXOfic.iCveDepartamento >= 0 "+
                           "ORDER BY cDscBreveOfic, cDscBreveDepto";
    }
    if (frm.hdNumReg)
      frm.hdNumReg.value = 100000;
    fEngSubmite("pgConsulta.jsp","CIDOficinaDeptoXUsr");
  }
}


function fResOficDeptoUsr(aRes,cId,cError,lTodas,lSeleccione){
  if(!lSeleccione)
    lSeleccione = false;
  var lEncontrado;
  if(cId == "CIDOficinaDeptoXUsr" && cError==""){
    aOficinaDeptoUsr = new Array();
    aOficinaUsr = new Array();
    for (var i=0; i<aRes.length; i++){
      aOficinaDeptoUsr[aOficinaDeptoUsr.length] = aRes[i];
      lEncontrado = false
      for (var j=0; j<aOficinaUsr.length; j++){
        if(!lTodas){
          if (aOficinaUsr[j][1] == aRes[i][1]){
            lEncontrado = true;
            break;
          }
        }else{
          if (aOficinaUsr[j][0] == aRes[i][0]){
            lEncontrado = true;
            break;
          }
        }
      }
      if (!lEncontrado)
        aOficinaUsr[aOficinaUsr.length] = aRes[i];
    }
    if (!lTodas)
      fFillSelect(frm.iCveOficinaUsr,aOficinaUsr,lSeleccione,frm.iCveOficinaUsr.value,1,5);
    else
      fFillSelect(frm.iCveOficinaUsr,aOficinaUsr,lSeleccione,frm.iCveOficinaUsr.value,0,3);
    fOficinaUsrOnChange(lTodas, lSeleccione);
  }
}

function fEnableRadio(theObject, theStatus, cValores){
  aValores = new Array();
  if(cValores)
    aValores = cValores.split(",");
  if(theObject && theObject.length)
    for (var i = 0; i < theObject.length; i++){
      if (!cValores)
        theObject[i].disabled = !theStatus;
      else{
        for (var j=0; j<aValores.length; j++)
          if (theObject[i].value == aValores[j])
            theObject[i].disabled = !theStatus;
      }
    }
}

function fValoresFecha(cFecha, cFormato, cSeparador, CampoAnio, CampoMes, CampoDia){
  cFmt = "dd/mm/aaaa";
  cSep = "/";
  cDia = "";
  cMes = "";
  cAnio = "";
  if (cFormato)
    if (cFormato.length > 0)
      cFmt = cFormato;
  aFormato = cFmt.split("/");
  if (cSeparador)
    if (cSeparador.length > 0)
      cSep = cSeparador;
  if (cFecha.length == 10){
    aFecha   = cFecha.split(cSep);
    for (var i=0; i< aFormato.length; i++){
      if (aFormato[i] == "dd")
        cDia = aFecha[i];
      else if (aFormato[i] == "mm")
        cMes = aFecha[i];
      else if (aFormato[i] == "aaaa")
        cAnio = aFecha[i];
    }
  }
  if (''+cAnio == "undefined")
    cAnio = "";
  if (''+cMes == "undefined")
    cMes = "";
  if (''+cDia == "undefined")
    cDia = "";
  if (CampoAnio)
    CampoAnio.value = cAnio;
  if (CampoMes)
    CampoMes.value = cMes;
  if (CampoDia)
    CampoDia.value = cDia;
}

function fCheckForEnter(theEvent, theObject, theWindow) {
  evt = (theEvent) ? theEvent : event;
  if(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 13) {
      if (theWindow.fEnterLocal)
        theWindow.fEnterLocal(theObject, evt, theWindow);
      return false;
    }
  }
  return true;
}

function fReposicionaListado(theObject, cColumnas, cValores){
  if (cValores != "" && cColumnas != ""){
    aValores = cValores.split(",");
    aColumnas = cColumnas.split(",");
    if (aValores.length != aColumnas.length)
      return;
    aResTemp = fCopiaArreglo(theObject.fGetARes());
    if (aResTemp)
      if (aResTemp.length > 0)
        for (var i=0; i<aResTemp.length; i++){
          lEncontrado = true;
          for (var j=0; j<aColumnas.length; j++){
            if (aResTemp[i][aColumnas[j]] != aValores[j]){
              lEncontrado = false;
              break;
            }
          }
          if (lEncontrado){
            theObject.fSelReg(i);
            break;
          }
        }
  }else
    return;
}

function fCheckBoxTodos(cNombre, cFuncion, cTexto){
  cCad = "Todos";
  if (cTexto)
    cCad = cTexto;
  return CheckBox(cNombre,"0",true,"",""," onClick='if(window.parent."+cFuncion+")window.parent."+cFuncion+"(this);else if(document."+cFuncion+")document."+cFuncion+"(this);' ","",false,true) + "<small>"+cCad+"</small>";
}

function fSeleccionaTodosEnListado(objListado, iColumna, theCheck, iValor){
  var aResSel = new Array();
  if(objListado && objListado.fGetARes && objListado.fGetARes() && objListado.fGetARes().length){
    for (var i=0; i<objListado.fGetARes().length; i++)
      aResSel[aResSel.length] = (!iValor)?theCheck.checked:iValor;
    objListado.fSetDefaultValues(iColumna, 1, aResSel);
  }
}

function fEncuentraLiga(objDoc, cNombre){
  var objLiga;
  for (var i=0; i<objDoc.links.length; i++)
    if (objDoc.links[i].name == cNombre)
      objLiga = objDoc.links[i];
  return objLiga;
}

function fFocusLiga(objDoc, cNombreLiga){
  var objLiga = fEncuentraLiga(objDoc, cNombreLiga);
  if (objLiga)
    objLiga.focus();
}

function LigaNombre(cNombreM,cHRefM,cEstatusM,cNombreLiga){ // 3000-Botón de tipo imagen
  cRef = cHRefM + '\" name=\"';
  cRef += (cNombreLiga)?cNombreLiga:cNombreM;
  return Liga(cNombreM, cRef, cEstatusM);
}

function BtnImgNombre(cNombreM,cNomImgM,cHRefM,cEstatusM,l4Status,cImgIni,cNombreLiga){ // 3000-botón de tipo imagen
  cRef = cHRefM + '\" name=\"';
  cRef += (cNombreLiga)?cNombreLiga:cNombreM;
  return BtnImg(cNombreM, cNomImgM, cRef, cEstatusM, l4Status, cImgIni);
}

var tCpoProcesoActual = "";
function fDefProcesoActual(lDefTabla, lNuevoRenglon, lNuevaCelda, cEstiloTabla, cEstiloCelda){
  cEstTabla = "";
  cEstCelda = "";
  if(cEstiloTabla)
    cEstTabla = cEstiloTabla;
  if(cEstiloCelda)
    cEstCelda = cEstiloCelda;
  cTx="";
  if(lDefTabla)
    cTx+=InicioTabla(cEstiloTabla,0,"100%","","","","0");
  if(lDefTabla || lNuevoRenglon)
    cTx+=ITR();
  if(lDefTabla || lNuevoRenglon || lNuevaCelda)
    cTx+=ITD(cEstiloCelda,0,"100%","","right","middle");
  cTx+=DinTabla("IDProcesoActual",cEstTabla,0,"100%","0","","","",0,"","");
  if(lDefTabla || lNuevoRenglon || lNuevaCelda)
    cTx+=FTD();
  if(lDefTabla || lNuevoRenglon)
    cTx+=FTR();
  if(lDefTabla)
    cTx+=FinTabla();
  return cTx;
}

function fProcesoActual(cMensaje){
  if(tCpoProcesoActual == ""){
    oProcActual = (document.all) ? document.all.IDProcesoActual:document.getElementById("IDProcesoActual");
    tCpoProcesoActual= oProcActual.tBodies[0];
  }
  for(i=0;tCpoProcesoActual.rows.length;i++)
    tCpoProcesoActual.deleteRow(0);
  if(cMensaje && cMensaje.length>0){
    newRow  = tCpoProcesoActual.insertRow(0);
    newCell = newRow.insertCell(0);
    newCell.innerHTML=Img("regla.gif");
    newCell = newRow.insertCell(1);
    newCell.className="EProceso";
    newCell.innerHTML=cMensaje;
  }
}

function getDatosFolio(cNumFolio){
  var cDatosFolio = cNumFolio.split("/");
  var cDigitosTemp = cDatosFolio[0].split(".");
  var aDatosFolio = new Array();
  aDatosFolio[0] = cDatosFolio[cDatosFolio.length-1];
  aDatosFolio[2] = cDigitosTemp[cDigitosTemp.length-1];
  Digitos = "";
  for(var i=0; i<cDigitosTemp.length-1; i++){
    Digitos += cDigitosTemp[i];
    if (i<cDigitosTemp.length-2)
      Digitos += ".";
  }
  aDatosFolio[1] = Digitos;
  return aDatosFolio;
}

function fPuntosCardinales(lObligatorio){
  aPtoCardinal=new Array();
  if(!lObligatorio)
    aPtoCardinal[aPtoCardinal.length]=["0","NO ESPECIFICADO"];
  aPtoCardinal[aPtoCardinal.length]=["1","(N) NORTE"];
  aPtoCardinal[aPtoCardinal.length]=["2","(S) SUR"];
  aPtoCardinal[aPtoCardinal.length]=["3","(E) ESTE"];
  aPtoCardinal[aPtoCardinal.length]=["4","(W) OESTE"];
  return aPtoCardinal;
}

function fPuntosCardinalesCompleto(lObligatorio){
  aPtoCardinal=new Array();
  if(!lObligatorio)
    aPtoCardinal[aPtoCardinal.length]=["0","NO ESPECIFICADO"];
  aPtoCardinal[aPtoCardinal.length]=["1","(N) NORTE"];
  aPtoCardinal[aPtoCardinal.length]=["2","(S) SUR"];
  aPtoCardinal[aPtoCardinal.length]=["3","(E) ESTE"];
  aPtoCardinal[aPtoCardinal.length]=["4","(W) OESTE"];
  aPtoCardinal[aPtoCardinal.length]=["5","(NE) NORESTE"];
  aPtoCardinal[aPtoCardinal.length]=["6","(SE) SURESTE"];
  aPtoCardinal[aPtoCardinal.length]=["7","(SW) SUROESTE"];
  aPtoCardinal[aPtoCardinal.length]=["8","(NW) NOROESTE"];
  return aPtoCardinal;
}

function fGetIdUsrSesion(){
/*  if(top.opener)
    if(top.opener.top.fGetIdUsrSesion)
      return top.opener.top.fGetIdUsrSesion();*/
  return top.fGetUsrID();  // solo dejar esta linea si aceptan cambios a Cliente Delgado
}

function fGetPermisos(cNomPag){ // 0=Solo Consulta  ; 1=Escritura  ; 2=No hay permiso ejecución
/*  if(top.opener)
    if(top.opener.top.fGetPermisos)
      return top.opener.top.fGetPermisos(cNomPag);*/
  return top.fGetPermiso(cNomPag);  // solo dejar esta linea si aceptan cambios a Cliente Delgado
}

function fSubirRegEnListado(FRMLstCambiar, iPosOrden, cPosLlave, objWindow, cId){
  var iPos = FRMLstCambiar.iPosAnt;
  iPos--;
  var aResTemp = FRMLstCambiar.fGetARes();
  var cValoresLlave = "";
  var aPosLlave = cPosLlave.split(",");

  if(iPos >= 1){
    if(aPosLlave.length > 1)
      for(var i=0; i< aPosLlave.lengh; i++)
        cValoresLlave += (cValoresLlave != "")?","+aResTemp[iPos][cValoresLlave]:aResTemp[iPos][cValoresLlave];
    else
      cValoresLlave = aResTemp[iPos][cPosLlave]
    aDatoTemp = fCopiaArreglo(aResTemp[iPos]);
    iOrdPos = aDatoTemp[iPosOrden];
    iOrdAnt = aResTemp[iPos-1][iPosOrden];
    aResTemp[iPos] = fCopiaArreglo(aResTemp[iPos-1]);
    aResTemp[iPos-1] = fCopiaArreglo(aDatoTemp);
    aResTemp[iPos][iPosOrden] = iOrdPos;
    aResTemp[iPos-1][iPosOrden] = iOrdAnt;

    if(objWindow)
      if(objWindow.fDatosRegCambiado)
        objWindow.fDatosRegCambiado(aResTemp[iPos], aResTemp[iPos-1], cId);
        // Definir la funcion de arriba en la página que requiere la funcionalidad

    FRMLstCambiar.fSetListado(aResTemp);
    FRMLstCambiar.fShow();
    fReposicionaListado(FRMLstCambiar, cPosLlave, cValoresLlave);
  }
}

function fBajarRegEnListado(FRMLstCambiar, iPosOrden, cPosLlave, objWindow, cId){
  var iPos = FRMLstCambiar.iPosAnt;
  iPos--;
  var aResTemp = FRMLstCambiar.fGetARes();
  var cValoresLlave = "";
  var aPosLlave = cPosLlave.split(",");
  if(iPos >= 0 && iPos<aResTemp.length-1){
    if(aPosLlave.length > 1)
      for(var i=0; i< aPosLlave.lengh; i++)
        cValoresLlave += (cValoresLlave != "")?","+aResTemp[iPos][cValoresLlave]:aResTemp[iPos][cValoresLlave];
    else
      cValoresLlave = aResTemp[iPos][cPosLlave]
    aDatoTemp = fCopiaArreglo(aResTemp[iPos]);
    iOrdPos = aDatoTemp[iPosOrden];
    iOrdAnt = aResTemp[iPos+1][iPosOrden];
    aResTemp[iPos] = fCopiaArreglo(aResTemp[iPos+1]);
    aResTemp[iPos+1] = fCopiaArreglo(aDatoTemp);
    aResTemp[iPos][iPosOrden] = iOrdPos;
    aResTemp[iPos+1][iPosOrden] = iOrdAnt;

    if(objWindow)
      if(objWindow.fDatosRegCambiado)
        objWindow.fDatosRegCambiado(aResTemp[iPos], aResTemp[iPos+1], cId);
        // Definir la funcion de arriba en la página que requiere la funcionalidad

    FRMLstCambiar.fSetListado(aResTemp);
    FRMLstCambiar.fShow();
    fReposicionaListado(FRMLstCambiar, cPosLlave, cValoresLlave);
  }
}

function fAgregarRegEnListado(FRMLstOrigen, FRMLstDestino, objWindow, cId){
  var iPos = FRMLstOrigen.iPosAnt;
  iPos--;
  var aResOrigen = FRMLstOrigen.fGetARes();
  var aResDestino = FRMLstDestino.fGetARes();
  if(!aResDestino || ''+aResDestino == 'undefined')
    aResDestino = new Array();
  var aResTemp = new Array();

  var cValoresLlave = "";
  if(iPos >= 0 && iPos<aResOrigen.length){
    if(aResDestino.length >= 0)
      aResDestino[aResDestino.length] = fCopiaArreglo(aResOrigen[iPos]);
    for(var i=0; i<aResOrigen.length; i++)
      if(i != iPos)
        aResTemp[aResTemp.length] = fCopiaArreglo(aResOrigen[i]);

    if(objWindow)
      if(objWindow.fDatosAgregados)
        objWindow.fDatosAgregados(aResDestino[aResDestino.length-1], cId);
        // Definir la funcion de arriba en la página que requiere la funcionalidad

    FRMLstOrigen.fSetListado(aResTemp);
    FRMLstOrigen.fShow();
    FRMLstDestino.fSetListado(aResDestino);
    FRMLstDestino.fShow();
  }
}

function fCargaAyuda(){
  cAyuda=top.fGetNombrePrograma();
  cAyuda=cAyuda.substring(0,cAyuda.length-3);
  cAyuda = (cAyuda!="")?cRutaAyuda + cAyuda + cExtensiónAyuda:"";
  if(cAyuda != "")
    fAbreWindowHTML(false,cAyuda,'yes','yes','yes','yes','no',750,580,"","");
  else
    fAlert('\n - La ayuda de esta página no está definida.');
}

function fCopiaArregloBidim(aResFuente){
  aTemporal = new Array();
  for (var i=0; i<aResFuente.length; i++)
    aTemporal[aTemporal.length] = fCopiaArreglo(aResFuente[i]);
  return aTemporal;
}

function fCopiaArregloMarcaInactivo(aResFuente, iColActivo, iColTexto, cIndicador){
  aTemporal = new Array();
  if(!cIndicador || cIndicador.length == 0)
    cIndicador = "*";
  for (var i=0; i<aResFuente.length; i++){
    aTemporal[aTemporal.length] = fCopiaArreglo(aResFuente[i]);
    if(aTemporal[aTemporal.length-1][iColActivo] == 0)
      aTemporal[aTemporal.length-1][iColTexto] = cIndicador + aTemporal[aTemporal.length-1][iColTexto];
  }
  return aTemporal;
}

function fMuestraAResInactivos(objSelect, aRes, iColValues, iColPos, lTodos, cIndicador){
  cInactivos = "";
  if(!cIndicador || cIndicador.length == 0)
    cIndicador = "*";
  if(!lTodos)
    for(var i=0; i<aRes.length; i++)
      if(aRes[i][iColPos] == 0)
        cInactivos += (cInactivos=="")?aRes[i][iColValues]:","+aRes[i][iColValues];
  fMuestraInactivos(objSelect, cInactivos, cIndicador);
}

function fMuestraInactivos(objSelect, cValores, cIndicador){
  var aValores;
  if(!cIndicador || cIndicador.length == 0)
    cIndicador = "*";
  if(cValores && cValores.length>0){
    aValores = cValores.split(",");
    for(var j=0; j<aValores.length; j++){
      for(var i=0; i<objSelect.options.length; i++){
        opc = objSelect.options[i];
        if(opc.value == aValores[j] && opc.text.substring(0, cIndicador.length) != cIndicador)
          opc.text = cIndicador + opc.text;
      }
    }
  }else{
    for(var i=0; i<objSelect.options.length; i++){
      opc = objSelect.options[i];
      if(opc.text.substring(0,cIndicador.length) == cIndicador)
        opc.text = opc.text.substring(cIndicador.length, opc.text.length);
    }
  }
  fValidaValorActivo(objSelect, false, cIndicador);
}

function fValidaValorActivo(objSelect, lMensaje, cIndicador){
  if(!cIndicador || cIndicador.length == 0)
    cIndicador = '*';
  if(objSelect && objSelect.options && objSelect.selectedIndex>=0 && objSelect.options[objSelect.selectedIndex].text.substring(0,cIndicador.length) == cIndicador){
    for(var i=0; i<objSelect.options.length; i++){
      opc = objSelect.options[i];
      if(opc.text.substring(0,cIndicador.length) != cIndicador){
        objSelect.selectedIndex = i;
        objSelect.value = opc.value;
        if(lMensaje)
          fAlert('\n - Esta opción esta inactiva, no puede ser seleccionada.');
        break;
      }
    }
  }
}

function fEliminaCaracteres(cCadena, cCaracteres, cCaracControl){
  if(cCaracteres+'' == 'undefined' || cCaracteres == "")
    cCaracteres = "$, ";
  if(cCaracControl+'' == 'undefined' || cCaracControl == '')
    cCaracControl = '+/-'
  for(var i=0; i<cCaracteres.length; i++){
    var regExp = eval("/" + cCaracteres.substring(i, (i+1)) + "/g");
    if(cCaracteres.substring(i, i+1) == "$" || cCaracteres.substring(i, i+1) == "^")
      regExp = eval("/\\" + cCaracteres.substring(i, i+1) +"/g");
    cCadena = cCadena.replace(regExp, "");
  }
  if(cCaracControl+'' != 'undefined')
    for(var i=0; i<cCaracControl.length; i++){
      var regExp = eval("/\\" + cCaracControl.substring(i, i+1) + "/g");
      cCadena = cCadena.replace(regExp, "");
    }
  return cCadena;
}

function fNumeroFormato(cValorEval,iDecimales,cSigno,lAgrupar,iNumDigitosGpo,cSepGrupo,cSepDecimal,iEnteros,lMsgEnteros,fLimInf,fLimSup,lMsgRango){
  iDecimales = (iDecimales+'' == 'undefined')?2:iDecimales;
  cSigno= (cSigno == null || cSigno+'' == 'undefined')?"$ ":cSigno;
  lAgrupar= (lAgrupar == null || lAgrupar+'' == 'undefined')?true:lAgrupar;
  iNumDigitosGpo= (iNumDigitosGpo == null || iNumDigitosGpo+'' == 'undefined')?3:iNumDigitosGpo;
  cSepGrupo= (cSepGrupo == null || cSepGrupo+'' == 'undefined')?",":cSepGrupo;
  cSepDecimal= (cSepDecimal == null || cSepDecimal+'' == 'undefined')?".":cSepDecimal;
  cValorEval = fTrim(cValorEval)+'';
  aValorEval = cValorEval.split(".");
  if(aValorEval.length > 2){
    cValorEval = "";
    for(var j=0; j<aValorEval.length-1; j++)
      cValorEval += aValorEval[j];
    cValorEval += "." + aValorEval[aValorEval.length-1];
  }
  var cMensaje = "";
  var cSignoEnt = (cValorEval.substring(0,1) == '-')?'-':'';
  var str = '' + Math.round(eval(fEliminaCaracteres(cValorEval)*1) * Math.pow(10,iDecimales));
  while (str.length <= iDecimales)
    str = '0' + str;
  var cEntero    = str.substring(0,(str.length - iDecimales));
  var cDecimales = str.substring((str.length - iDecimales),str.length);
  var cRegEval = cSignoEnt + cEntero + "." + cDecimales;
  var cTemp = "";
  if(iEnteros && cEntero.length > iEnteros){
    cEntero = cEntero.substring(0, iEnteros);
    if(lMsgEnteros)
      cMensaje += "\n - El número debe tener el formato máximo de "+ iEnteros + " enteros y " + iDecimales + " decimales.";
    cEntero= cSignoEnt + cEntero;
  }
  if(lAgrupar){
    var iInicio = cEntero.length % iNumDigitosGpo;
    var iGrupos = parseInt(cEntero.length/iNumDigitosGpo,10);
    cTemp = cEntero.substring(0, iInicio);
    cEntero= cEntero.substring(iInicio, cEntero.length);
    for(var i=0; i<iGrupos; i++){
      cTemp += (cTemp=="")?"":cSepGrupo;
      cTemp += cEntero.substring(0,iNumDigitosGpo);
      cEntero= cEntero.substring(iNumDigitosGpo,cEntero.length);
    }
  }else
    cTemp = cEntero;
  var cRegresa = cSignoEnt + cSigno + cTemp + cSepDecimal + cDecimales;
  if(fLimInf || fLimSup){
    if(fLimInf != null && fLimSup != null)
      if(parseFloat(cRegEval,10) < parseFloat(fLimInf,10) || parseFloat(cRegEval,10) > parseFloat(fLimSup,10))
        if(lMsgRango)
          cMensaje += "\n - El número no esta dentro de los rangos permitidos, el rango de valores es: [" + fLimInf + "..." + fLimSup + "].";
        else
          cRegresa = null;
    if(fLimInf != null && fLimSup == null)
      if(parseFloat(cRegEval,10) < parseFloat(fLimInf,10))
        if(lMsgRango)
          cMensaje += "\n - El número debe ser mayor o igual a " + fLimInf + ".";
        else
          cRegresa = null;
    if(fLimInf == null && fLimSup != null)
      if(parseFloat(cRegEval,10) > parseFloat(fLimSup,10))
        if(lMsgRango)
          cMensaje += "\n - El número debe ser menor o igual a " + fLimSup + ".";
        else
          cRegresa = null;
  }
  if(cMensaje != ""){
    alert(cMensaje);
    return null;
  }
  return cRegresa;
}

function fTituloOrden(cTitulo){ // Regresa el HTML para un título de listado que permita ordenamiento
  return '<a style="color: #FFFFFF; font-weight: bold; font-family: verdana; font-size: 8pt" href="JavaScript:if(window.parent.fAsignaOrdenTitulo)window.parent.fAsignaOrdenTitulo(\'' + cTitulo + '\');">' + cTitulo + '</a>';
}

function fTituloCodigo(cTitulo, cPrograma){
  InicioTabla("ESTitulo",0,"100%","","","",0);
    ITRTD("ESTitulo",0,"","","center");
      TextoSimple(cTitulo);
    FTD();
      fMuestraTDCodigo(cPrograma);
    FTR();
  FinTabla();
}

 function fMuestraTablaCodigo(cPrograma){
   var cCodigo = fGetCodigoPrograma(cPrograma);
   if(cCodigo != ""){
     InicioTabla("",0,"100%","","right","",0,"","");
       ITRTD("",0,"","","center");
       FTD();
         fMuestraTDCodigo(cPrograma);
       FTR();
     FinTabla();
   }
 }

 function fMuestraTDCodigo(cPrograma, cFondoCelda){
   if(!cFondoCelda)
     cFondoCelda = cFondoCeldaISO;
   var cCodigo = fGetCodigoPrograma(cPrograma);
   var cAncho = parseInt(cCodigo.length * 6.2,10) + '';
   if(cCodigo != ""){
     ITD("ECodigoISO",0,cAncho,"","right","top\" bgColor=\""+ cFondoCelda);
       TextoSimple(cCodigo);
     FTD();
   }
 }

 var iProcIDPausa;
 function fPausa(dSegundos, cFuncionEjecutar, lCiclaIntervalo){
   if(!dSegundos) dSegundos = 0.0;
   if(!cFuncionEjecutar) cFuncionEjecutar="";
   if(!lCiclaIntervalo) lCiclaIntervalo = false;
   iProcIDPausa = setInterval('fTerminaPausa("' + cFuncionEjecutar + '",' + lCiclaIntervalo + ');', dSegundos*1000);
 }
 function fTerminaPausa(cFuncion, lCiclaIntervalo){
   if(iProcIDPausa && !lCiclaIntervalo)
     clearInterval(iProcIDPausa);
   if(cFuncion){
     obj = eval(cFuncion + "();");
     if(obj) obj;
   }
 }

 function fGetArrayMeses(){
   var aMeses = new Array();
   aMeses[0] = ['1','ENERO'];
   aMeses[1] = ['2','FEBRERO'];
   aMeses[2] = ['3','MARZO'];
   aMeses[3] = ['4','ABRIL'];
   aMeses[4] = ['5','MAYO'];
   aMeses[5] = ['6','JUNIO'];
   aMeses[6] = ['7','JULIO'];
   aMeses[7] = ['8','AGOSTO'];
   aMeses[8] = ['9','SEPTIEMBRE'];
   aMeses[9] = ['10','OCTUBRE'];
   aMeses[10] = ['11','NOVIEMBRE'];
   aMeses[11] = ['12','DICIEMBRE'];
   return aMeses;
 }

 //Busca las oficinas que tiene asignadas el usuario del sistema
 function fTraeOficinasDeUsuario(){
   frm.hdLlave.value = "";
   frm.hdSelect.value =	 "SELECT DISTINCT(o.iCveOficina), o.CDSCOFICINA " +
                             "FROM  GRLOFICINA o " +
                               "JOIN GRLUSUARIOXOFIC uxo ON o.ICVEOFICINA = uxo.ICVEOFICINA " +
                               "WHERE o.ICVEOFICINA > 0 and uxo.ICVEUSUARIO = " + frm.iCveUsuario.value;
   fEngSubmite("pgConsulta.jsp","cIdOficinasDeUsuario");

 }

 //Recibe el aRes que regresa la función fTraeOficinasDeUsuario
 //El llamado a esta función se debe de poner en el fResultado
 function fEsUsuarioDeOficinaCentral(aRes,cId,cError){
   var lEsUsrDeOficCentral = false;
   if(cId == "cIdOficinasDeUsuario" && cError==""){
     if(aRes.length>0){
       //Recorre el aRes y si algunas de las oficinas  es igual a la clave de oficina central (iOficinaCentral)
       //asigna a lEsUsrDeOficCentral true
       for(var cont=0;cont<aRes.length;cont++){
         if(aRes[cont][0]==iOficinaCentral){
           lEsUsrDeOficCentral = true;
           break;
         }
       }
     }
     return lEsUsrDeOficCentral;
   }
   else
     return false;
 }


// Regresa la Oficina y Departamento que resuelve el trámite, dependiendo del Usuario que lo atiende.
// Recibe como parámetro la clave del  Usuario y la clave del trámite.
 function fObtieneOficinaDeptoResuelve(parCveUsuario, parCveTramite){
   frm.hdLlave.value = "";
   frm.hdSelect.value =	 "select CDIGITOSFOLIO, ICVEOFICINARESUELVE, ICVEDEPTORESUELVE " +
           "  from GRLDIGITOSFOLIOXDEPTO " +
           "  join GRLUSUARIOXOFIC on GRLUSUARIOXOFIC.icveoficina = GRLDIGITOSFOLIOXDEPTO.ICVEOFICINA " +
           "   and GRLUSUARIOXOFIC.ICVEDEPARTAMENTO = GRLDIGITOSFOLIOXDEPTO.ICVEDEPARTAMENTO " +
           "  join TRATRAMITEXOFIC on TRATRAMITEXOFIC.icveoficina = GRLDIGITOSFOLIOXDEPTO.ICVEOFICINA " +
           "   and TRATRAMITEXOFIC.icvedeptoresuelve = GRLDIGITOSFOLIOXDEPTO.ICVEDEPARTAMENTO " +
           " where GRLUSUARIOXOFIC.ICVEUSUARIO = " + parCveUsuario +
           "   and TRATRAMITEXOFIC.ICVETRAMITE = " + parCveTramite +
           " order by GRLDIGITOSFOLIOXDEPTO.DTASIGNACION desc ";
   fEngSubmite("pgConsulta.jsp","cIdOficinaDeptoResuelve");

 }

 // Asigna la oficina y el departamento que resuelve el trámite.
 // Declarar en el archivo .js los siguientes campos: hdDigitosFolio, hdOficResuelve y hdDeptoResuelve
 function fAsignaOficinaDeptoResuelve (aRes,cId,cError){
   if(cId == "cIdOficinaDeptoResuelve" && cError==""){
     if(aRes.length>0){
        frm.hdDigitosFolio.value = aRes[0][0];
        frm.hdOficResuelve.value = aRes[0][1];
        frm.hdDeptoResuelve.value = aRes[0][2];
     }
   }
 }

// Regresa el número de días entre dos fechas, no contempla años bisiestos.
function fDiasEntre(dtInicio, dtFin){
  if(fComparaFecha(dtInicio, dtFin, false)){
    iAnioInicio = parseInt(dtInicio.substring(6,10),10);
    iMesInicio  = parseInt(dtInicio.substring(3,5),10);
    iDiaInicio  = parseInt(dtInicio.substring(0,2),10);
    iAnioFin = parseInt(dtFin.substring(6,10),10);
    iMesFin  = parseInt(dtFin.substring(3,5),10);
    iDiaFin  = parseInt(dtFin.substring(0,2),10);
    iDiasEntre = 0;
    if((iAnioInicio == iAnioFin) && (iMesInicio == iMesFin))
      iDiasEntre = iDiaFin - iDiaInicio;
    else{
      //Calcual los dias entre la fecha de inicio y el fin de mes.
      switch (iMesInicio){
        case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                iDiasEntre = 31 - iDiaInicio; break;
        case 2: iDiasEntre = 28 - iDiaInicio; break;
        case 4: case 6: case 9: case 11:
                iDiasEntre = 30 - iDiaInicio; break;
      }
      iMesInicio++;
      while ((iMesInicio<iMesFin) || (iAnioInicio<iAnioFin)){
        switch (iMesInicio){
        case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                iDiasEntre += 31; break;
        case 2: iDiasEntre += 28; break;
        case 4: case 6: case 9: case 11:
                iDiasEntre += 30; break;
        }
        if(iMesInicio<12)
          iMesInicio++;
        else{
          iMesInicio=1;
          iAnioInicio++;
        }
      }
      iDiasEntre += iDiaFin;
    }
  }
  return iDiasEntre;
}

function fGetDateSPN(cFecha,cSeparador,lMayusculas){
  var aFecha = cFecha.split("/");
  var cNomMes = "", cSeparadorFinal = ((cSeparador)?cSeparador:"/");

  if(cFecha == "")
    return "";

  switch (aFecha[1]){
    case "01": cNomMes = "Ene";
               break;
    case "02": cNomMes = "Feb";
               break;
    case "03": cNomMes = "Mar";
               break;
    case "04": cNomMes = "Abr";
               break;
    case "05": cNomMes = "May";
               break;
    case "06": cNomMes = "Jun";
               break;
    case "07": cNomMes = "Jul";
               break;
    case "08": cNomMes = "Ago";
               break;
    case "09": cNomMes = "Sep";
               break;
    case "10": cNomMes = "Oct";
               break;
    case "11": cNomMes = "Nov";
               break;
    case "12": cNomMes = "Dic";
               break;
  }
  if(lMayusculas)
    cNomMes = cNomMes.toUpperCase();

  return aFecha[0] + cSeparadorFinal + cNomMes + cSeparadorFinal + aFecha[2];
}

//Obtiene la diferencia en dias entre dos fechas con formato dd/mm/aaaa
function fDiasEntreFechas(dtInicio,dtFin){
  iAnioI = parseInt(dtInicio.substring(6,10),10);
  iMesI  = parseInt(dtInicio.substring(3,5),10)-1;
  iDiaI  = parseInt(dtInicio.substring(0,2),10);
  iAnioF = parseInt(dtFin.substring(6,10),10);
  iMesF  = parseInt(dtFin.substring(3,5),10)-1;
  iDiaF  = parseInt(dtFin.substring(0,2),10);
  var FechaI=new Date(iAnioI,iMesI,iDiaI);
  var FechaF=new Date(iAnioF,iMesF,iDiaF);
  var mseg_dia=1000*60*60*24;
  var Dias = 0;
  Dias = Math.ceil((FechaF.getTime()-FechaI.getTime())/(mseg_dia))
  return Dias;
}

function fAbreWindowNoPermisos(lModalM,cNombreM,cMenubarM,cResizableM,cScrollbarsM,cStatusM,cAnchoM,cAltoM,iX,iY){ // 5000-Abre una Nueva Ventana (.js) Sin Control Transaccional
    if((wExp != null) && (!wExp.closed))
      wExp.focus();
    else{
      cParametrosM = 'dependent=yes,hotKeys=no,location=no,menubar='+cMenubarM+',personalbar=no,resizable='+cResizableM+',scrollbars='+cScrollbarsM+',status='+cStatusM+',titlebar=no,toolbar=no,width='+cAnchoM+',height='+cAltoM+',screenX=800,screenY=600';
      wExp = open(cPagGral+'?cPagina='+cNombreM+'.js','',cParametrosM);
      wExp.creator = self;
      if(iX); else iX =  (screen.availWidth - cAnchoM) / 2;
      if(iY); else iY =  (screen.availHeight - cAltoM) / 2;
      wExp.moveTo(iX, iY);
      if(lModalM){
         window.onclick=HandleFocus
         window.onfocus=HandleFocus
      }
    }
}

var aManuales = new Array();
aManuales[0] = ['Coordinación General', 'Común', 'Módulo de Administración General', 'Configuración y Procesos Generales.pdf'];
aManuales[1] = ['','','Generalidades del Sistema','Manual de Usuario SIPYMM - General.pdf'];
aManuales[2] = ['','Ventanilla Única','Configuración','Gestión de Trámites - Catálogos.pdf'];
aManuales[3] = ['','','Procesos','Gestión de Trámites - Procesos.pdf'];
aManuales[4] = ['','','Consultas','Gestión de Trámites - Consultas.pdf'];
aManuales[5] = ['','','Trámites a Distancia','Trámites a Distancia - Proceso.pdf'];
aManuales[6] = ['','Estadísticas','Procesos','Estadísticas - Procesos.pdf'];
aManuales[7] = ['Marina Mercante','Registro Público','Configuración','RPMN, Matrículas y Permisos - Catálogos.pdf'];
aManuales[8] = ['','','Procesos','RPMN, Matrículas y  Permisios - Procesos.pdf'];
aManuales[9] = ['','','Consultas','RPMN, Matrículas y Permisos - Consultas.pdf'];
aManuales[10] = ['','Navegación','Configuración','Navegación - Catálogos.pdf'];
aManuales[11] = ['','','Procesos','Navegación - Procesos.pdf'];
aManuales[12] = ['','','Consultas','Navegación - Consultas.pdf'];
aManuales[13] = ['','Inspección','Configuración','Inspección de Embarcaciones e Instalaciones - Catálogos.pdf'];
aManuales[14] = ['','','Procesos y Consultas','Inspección de Embarcaciones e Instalaciones - Procesos y Consultas.pdf'];
aManuales[15] = ['','Señalamiento Marítimo','Configuración','Señalamiento Marítimo - Catalogos.pdf'];
aManuales[16] = ['','','Procesos','Señalamiento Marítimo - Procesos.pdf'];
aManuales[17] = ['','Operación de Capitanías','Configuración','Operación de las Capitanías de Puerto - Catálogos.pdf'];
aManuales[18] = ['','','Procesos','Operación de las Capitanías de Puerto - Procesos.pdf'];
aManuales[19] = ['','Planeación de Programas','Configuración','Planeación de Programas - Catálogos.pdf'];
aManuales[20] = ['','','Procesos','Planeación de Programas - Procesos.pdf'];
aManuales[21] = ['Puertos','Concesiones y Permisos','Configuración','Concesiones y Permisos - Catálogos.pdf'];
aManuales[22] = ['','','Procesos','Concesiones y Permisos - Procesos.pdf'];
aManuales[23] = ['','Desarrollo Portuario','Configuración','Desarrollo Portuario - Catálogos.pdf'];
aManuales[24] = ['','','Procesos Centrales y API','Desarrollo Portuario - Procesos.pdf'];
aManuales[25] = ['','','Consultas','Desarrollo Portuario - Consultas.pdf'];
aManuales[26] = ['','','Procesos Centrales','Desarrollo Portuario - Procesos Central.pdf'];
aManuales[27] = ['','','Procesos API','Desarrollo Portuario - Procesos API.pdf'];
aManuales[28] = ['','Tarifas','Configuración','Tarifas - Catálogos.pdf'];
aManuales[29] = ['','','Procesos','Tarifas - Procesos.pdf'];
aManuales[30] = ['','Obras Marítimas','Configuración','Obra Marítima Portuaria e Inmuables - Catálogos.pdf'];
aManuales[31] = ['','','Procesos','Obra Marítima Portuaria e Inmuables - Procesos.pdf'];

var aReportesPublico = new Array();
// 0 = Grupo o Liga || 1 = Texto para encabezado o liga || 2 = Módulos para enviar reportes (separados y terminados por coma) || 3 = Números de Reporte (separados y terminados por coma)  ||  4 = filtros para cada reporte
aReportesPublico[0] = ['Grupo','AREAS QUE DEPENDEN DIRECTAMENTE DE LA COORDINACIÓN GENERAL DE PUERTOS Y MARINA MERCANTE','','',''];
aReportesPublico[1] = ['Liga','Ventanilla Única','2,','7,',' '+cSeparadorRep];
aReportesPublico[2] = ['Grupo','AREAS DE LA DIRECCIÓN GENERAL DE MARINA MERCANTE','','',''];
aReportesPublico[3] = ['Liga','Derrotero Meteorológico','22,','3,',' '+cSeparadorRep];

// En esta función se indican los códigos de formatos o procesos ISO que se deban desplegar en pantallas.
function fGetCodigoPrograma(cNomPagina){
  var cModulo = "";
  var iModulo = 0;
  var cCodigo = "";
  var cRev    = "";

  cModulo = cNomPagina.substring(0,6);
  if(cModulo.length == 6)
    iModulo = parseInt(cModulo.substring(cModulo.length - 2, cModulo.length),10);
  else return "";

  switch(iModulo){
    case 10:
            break;
    case 20:if(cNomPagina == 'pg112020080.js'){ cCodigo = 'DGMM-PE-02'; cRev = '5';}
            break;
    case 30:if(cNomPagina == 'pg113020011A01.js'){ cCodigo = 'DGP-PE-27'; cRev = '3';}
            if(cNomPagina == 'pg113020011A02.js'){ cCodigo = 'DGP-PE-27'; cRev = '3';}
            break;
    case 40:if(cNomPagina == 'pg114020060.js'){ cCodigo = 'DGMM-PE-01'; cRev = '4';}
            break;
    case 50:
            break;
    case 60: if(cNomPagina == 'pg116020020.js'){ cCodigo = 'DGMM-PE-04'; cRev = '0';}
             if(cNomPagina == 'pg116020020R1.js'){ cCodigo = 'DGMM-FE-04-01'; cRev = '';}
             if(cNomPagina == 'pg116020020R2.js'){ cCodigo = 'DGMM-FE-04-02'; cRev = '';}
             if(cNomPagina == 'pg116020022.js' || cNomPagina == 'pg116020025.js' || cNomPagina == 'pg116020026.js' || cNomPagina == 'pg116020027.js'){ cCodigo = 'DGMM-FE-04-03'; cRev = '';}
            break;
    case 70:
            break;
    case 80:
            break;
    case 90: if(cNomPagina == 'pg119020010.js'){ cCodigo = 'DGP-PE-29'; cRev = '4';}
            break;
    case 91:if(cNomPagina == 'pg119120010.js' || cNomPagina == 'pg119120020.js' || cNomPagina == 'pg119120030.js' || cNomPagina == 'pg119120040.js'){ cCodigo = 'DGMM-PE-07'; cRev = '';}
            break;
  }
  // para cualquier pagina que no tenga número de módulo y que requiera obtenerse un código ISO
  if(cNomPagina == 'pg110020020.js'){ cCodigo = 'SCT-FI-05-01'; cRev = ''; }
  if(cNomPagina == 'pg110020030.js'){ cCodigo = 'SCT-PI-05'; cRev = '3'; }
  if(cCodigo != "")
    return "CÓDIGO: " + cCodigo + ((cRev!='')?" REV. " + cRev:"");
  else
    return "";
}

function fGetClavesTramiteFiltrar(){
  var cNomPagina = "";
  var cModulo = "";
  var iModulo = 0;

  if(top.FRMCuerpo)
    if(top.FRMCuerpo.FRMDatos)
      if(top.FRMCuerpo.FRMDatos.FRMPagina)
        cNomPagina = top.FRMCuerpo.FRMDatos.FRMPagina.cPaginaWebJS;
      else return "";
    else return "";
  else return "";
  cModulo = cNomPagina.substring(0,6);
  if(cModulo.length == 6)
    iModulo = parseInt(cModulo.substring(cModulo.length - 2, cModulo.length),10);
  else return "";

  switch(iModulo){
    case 10:
            if(cNomPagina == "pg111020270.js") return "1,411"; // Certificación de Documentos.
            return "";
            break;
    case 20: //Navegación
            if(cNomPagina == "pg112020050.js") return "102"; // Amarre temporal.
            if(cNomPagina == "pg112020060.js") return "40,41,125,126,127,132,133,334"; // Autorización de arribo y despacho.
            if(cNomPagina == "pg112020010.js") return "42,128,129,130"; // Registro de  Actividades / Eventos que afecten el Tráfico Marítimo.
            if(cNomPagina == "pg112020020.js") return "103,104"; // Registro de Desguaces y Remociones.
            if(cNomPagina == "pg112020030.js") return "128,129,130"; // Registro Accidentes o Incidentes Marítimos.
            if(cNomPagina == "pg112020040.js") return "109"; // Registro de Institutos Privados.
            if(cNomPagina == "pg112020070.js") return "105"; // Autorizar Embarque Tècnico Extranjero.
            if(cNomPagina == "pg112020080.js") return "65,66,134,135"; // Permisos de Navegación.
            //if(cNomPagina == "pg112020090.js") return ""; // Informe de Operaciones en Navegación Interior.
            //if(cNomPagina == "pg112020100.js") return "138"; // Autorización de Instructores.
            if(cNomPagina == "pg112020120.js") return "110"; // Autorización de Cursos.
            if(cNomPagina == "pg112020130.js") return "98,99"; // Autorización de Instructores.
//            if(cNomPagina == "pg112020140.js") return ""; // Autorización de CCTM.
            if(cNomPagina == "pg112040010.js") return "65,66,134,135"; // Permisos de Navegación para consultas.
            return "";
            break;
    case 30: // Concesiones
             if(cNomPagina == "pg113020011A01.js") return "49"; //Formulación Proyecto Titulo de Concesion.
             if(cNomPagina == "pg113020011A02.js") return "121"; //Formulación Proyecto Titulo de Concesión de API.
             if(cNomPagina == "pg113020011A03.js") return "51"; //Permiso para Construir dentro de Zonas Lacustres.
             if(cNomPagina == "pg113020011A04.js") return "50"; //Permio para Construir fuera de Zonas Lacustres.
             if(cNomPagina == "pg113020011A05.js") return "52"; //Permiso para Prestar Servicios Portuarios.
             if(cNomPagina == "pg113020011A06.js") return "53"; //Autorizacion de Obras Marítimas y de Dragado.
             if(cNomPagina == "pg113020011A07.js") return "145"; //Autorización Tecnica de Obras a cargo de una API.
             if(cNomPagina == "pg113020011A08.js") return "136"; //Autorizacion de Cesión de las Obligaciones Derechos.
             if(cNomPagina == "pg113020011A09.js") return "56"; //Autorizacion para Inicio de Construccion.
             if(cNomPagina == "pg113020011A10.js") return "58"; //Autorización para Inicio de Operaciones.
             if(cNomPagina == "pg113020011A11.js") return "116"; //Autorización para Navegación de Altura Terninales fuera de Puerto.
             if(cNomPagina == "pg113020011A12.js") return "55"; //Autorización de Obras Marítimas y de Dragado API.
             if(cNomPagina == "pg113020011A13.js") return "57"; //Autorizacion para Inicio de Construcción API.
             if(cNomPagina == "pg113020011A14.js") return "59"; //Autorización para Inicio de Operaciones API.
             if(cNomPagina == "pg113020090.js")    return "115"; //Acuerdo de Delimitación de Recintos Portuarios.
             if(cNomPagina == "pg113020090A.js")   return "144"; //Delimitación de Recintos Portuarios (Revisión Técnica de Planos).
             if(cNomPagina == "pg113020100.js")    return "114"; //Decreto de Habilitación de Puertos y Terminales.
             if(cNomPagina == "pg113020080.js")    return "117"; //Declaratoria de Delimitación de Zonas de Desarrollo Portuario.
             if(cNomPagina == "pg113020070.js")    return "140"; //Regularizacion de Areas e Instalaciones Portuarias.
             if(cNomPagina == "pg113020060.js")    return "60"; //Registro de Contratos.
             break;
    case 40: // Inspección
            if(cNomPagina == "pg114020010.js") return "343,351,352,355,395";
            if(cNomPagina == "pg114020030.js") return "32,33,34,35,36,37,359"; // Autorización de documentos técnicos.
            if(cNomPagina == "pg114020040.js") return "39,131"; // Autorización para expedir certificados.
            if(cNomPagina == "pg114020050.js") return "107,108,285"; // Registro de estaciones de servicio.
            if(cNomPagina == "pg114020060.js") return "0,19,20,31,123,124,189,190,191,192,205,206,229,245,266,275,276,297,298,312,313,322,323,324,359,380"; //Certificados de Arqueo y Francobordo.
            if(cNomPagina == "pg114020070.js") return "0,15,21,22,23,24,25,26,27,28,29,30,147,148,149,"+
                                                      "150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,"+
                                                      "170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,"+
                                                      "193,194,195,196,197,198,199,200,201,202,203,204,207,208,209,"+
                                                      "210,211,212,213,214,215,216,217,218,219,220,226,227,228,"+
                                                      "230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,246,247,248,249,"+
                                                      "250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,267,268,269,"+
                                                      "270,271,288,289,290,291,292,293,294,295,296,299,300,301,302,303,304,305,306,307,308,309,"+
                                                      "310,311,314,315,316,317,318,319,320,321,325,326,327,328,329,"+
                                                      "330,331,332,333,335,336,337,338,339,340,341"; // Certificados de Seguridad.
            if(cNomPagina == "pg114020080.js") return "106"; // Registro de Astilleros, varaderos y talleres navales.
            if(cNomPagina == "pg114020090.js") return "38"; // Autorización de dispositivos y medios de salvamento.
            if(cNomPagina == "pg114020100.js") return "112,113"; // Certificados Código PBIP.
            if(cNomPagina == "pg114020120.js") return "389"; // Certificados Código PBIP.
            if(cNomPagina == "pg114020110.js") return "390"; // Certificados Código PBIP.

            return "";
            break;
    case 50: // RPMN, Matrícula y Abanderamiento
            if(cNomPagina == "pg115020010.js") return "2"; //Abanderamiento.
            if(cNomPagina == "pg115020020.js") return "43,44,63,279"; //Permiso para prestar servicio de Crucero Turistico, Transporte, Turismo Náutico.
            if(cNomPagina == "pg115020030.js") return "5"; //Cambio de Matrícula.
            if(cNomPagina == "pg115020040.js") return "8,9,10,11,12,17,18,122"; //Inscripción en RPMN, Anotación preventiva, Rectificación de Inscripciones.
            if(cNomPagina == "pg115020050.js") return "4,221,272,274"; //Matriculación.
            if(cNomPagina == "pg115020060.js") return "13,14"; //Reconocimiento de Capacidad Jurídica.
            if(cNomPagina == "pg115020070.js") return "15,16"; //Consulta y certificación.
            if(cNomPagina == "pg115020080.js") return "3,223"; //Señal Distintiva.
            if(cNomPagina == "pg115020090.js") return "101"; //Registrar y autorizar tarifas.
            if(cNomPagina == "pg115020100.js") return "138"; //Registrar y autorizar tarifas.
            if(cNomPagina == "pg115020140.js") return "7"; //Dimisión de Bandera.
            if(cNomPagina == "pg115020150.js") return "6"; //Cancelación de Matrícula.
            if(cNomPagina == "pg115020170.js") return "5"; //Modificación de Matrícula.
            if(cNomPagina == "pg115020180.js") return "43,44,63,64,279"; //Revalidación de Permisos.
            if(cNomPagina == "pg115040080.js") return "16"; //Consulta al RPMN.
            if(cNomPagina == "pg115040090.js") return "15"; //Certificacion al RPMN.
            return "";
            break;
    case 60:
            if(cNomPagina == "pg116020130.js") return "143"; //Autorización y Dictaminación de SM.
            return "";
            break;
    case 70:
             if(cNomPagina == "pg117020080.js") return "48"; //Reglas de Operación de los Puertos.
            return "";
            break;
    case 80:
             if(cNomPagina == "pg118020020.js") return "54";  //Programa Operativo Anual
             if(cNomPagina == "pg118020060.js") return "120"; //Programas Maestros de Desarrollo Portuario.
             if(cNomPagina == "pg118020070.js") return "119"; //Reglas de Operación de los Puertos.
            return "";
            break;
    case 90: if(cNomPagina == "pg119020010.js") return "45,146";  //Servicios Portuarios.
             if(cNomPagina == "pg119020020.js") return "47";  //Servicios por Uso de Infraestructura.
             if(cNomPagina == "pg119020040.js") return "118"; //Servicios Portuarios de Pilotaje.
            return "";
            break;
  }
  // para cualquier pagina que no tenga número de módulo y que requiera filtrado de trámites
//  if(cNomPagina == "pg110020100.js")
//    return "XX";
  return "";
}
function fCargaCatalogoPaises(){
  frm.hdBoton.value = "TraerTodoCatalogo";
  frm.hdOrden.value = "cNombre";
  if(fEngSubmite("pgGRLPais.jsp","CIDCatalogoPaises"));
}
function fCargaCatalogoEntidades(){
  frm.hdBoton.value = "TraerTodoCatalogo";
  frm.hdOrden.value = "cNombre";
  if(fEngSubmite("pgGRLEntidadFed.jsp","CIDCatalogoEntidades"));
}
function fResCatalogoPaisEntidad(aRes,cId,cError){
  if(cId == "CIDCatalogoPaises" && cError==""){
    if(aPais)
      aPais = fCopiaArregloBidim(aRes);
    if(window.fTerminoCargaPais){
      window.fTerminoCargaPais();
    }
  }
  if(cId == "CIDCatalogoEntidades" && cError==""){
    if(aEstados)
      aEstados = fCopiaArregloBidim(aRes);
    if(fTerminaCargaPaisEntidad)
      fTerminaCargaPaisEntidad();
  }
}
function clearClipBoardData(){
	// Llamar de la siguiente forma <body onload="clearClipBoardData();">
	setInterval("window.clipboardData.setData('text','')",20);
}
function reloadBlockOnError(){
	// Llamar de la siguiente forma:  window.onerror = reloadBlockOnError;
	window.location.reload(true);
	return true;
}
function fTerminoCargaPais(){
  fCargaCatalogoEntidades();
}

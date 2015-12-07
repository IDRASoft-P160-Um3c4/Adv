// MetaCD=1.0
// Title: pg114020062.js
// Description: JS "Catálogo" de la entidad INSAutorizaExpVer
// Company: Tecnología InRed S.A. de C.V.
// Author: Sandor Trujillo Q.  $$ Arturo López Peña.
var cTitulo = "";
var FRMListado = "";
var frm;
var cUser = fGetIdUsrSesion();
var cNumReportes=0;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg114020072.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
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
        ITR("",0,"","","center","top");
           TDEtiCampo(false,"EEtiqueta",0,"Numero OMI:","cNumOMI","",30,30,"Numero OMI","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"Matricula:","cMatricula","",15,15,"Matricula","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","10",8,8,"Ejercicio","fMayus(this);");
        FITR();
           TDEtiAreaTexto(false,"EEtiqueta",0,"Nombre Embarcación:",40,2,"cNomEmbarcacion","","Nombre Embarcacion","","fMayus(this);",'onkeydown="fMxTx(this,75);"');
           ITD("",1,"","","center");
             //BtnImg("Buscar","buscar","fBusqueda();","Buscar");
           FTD();
           ITD("",1,"","","center");
           FTD();
           TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitud","10",8,8,"Solicitud","fMayus(this);");
       FTR();
     FinTabla();
    FTDTR();
      ITRTD("",0,"","75","center","top");
        IFrame("IListado62","95%","70","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple("");
          FTDTR();
            TDEtiSelect(true,"EEtiqueta",0,"Oficina de Expedición:","iCveOficina","fGpoOnChange();");
          FTDTR();
            TDEtiSelect(true,"EEtiqueta",0,"Grupo Certificado:","iCveGrupoCertif","fGpoOnChange();");
            TDEtiSelect(true,"EEtiqueta",0,"Tipo de Certificado:","iTipoCertificado","iTipoOnChange();");
          FITR();
              TDEtiCheckBox("EEtiqueta",0,"Autorizado:","lAutorizadoBOX","1",true,"Autorizado");
              Hidden("lAutorizado","");
              TDEtiCheckBox("EEtiqueta",0,"Aprobado:","lAprobadoBOX","1",true," Aprobado...");
              Hidden("lAprobado","");
          FITR();
            TDEtiCampo(true,"EEtiqueta",0,"Fecha de Expedición:","dtExpedicionCert","",8,8,"ArqueoBruto","fMayus(this);");
            TDEtiCampo(true,"EEtiqueta",0,"Inicio de Vigencia:","dtIniVigencia","",8,8,"ArqueoBruto","fMayus(this);");
          FITR();
            TDEtiCampo(true,"EEtiqueta",0,"Certificado Expedido:","iConsecutivoCertifExp","",8,8,"ArqueoBruto","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0,"Fin de Vigencia:","dtFinVigencia","",8,8,"ArqueoBruto","fMayus(this);");
          FITR();
              TDEtiCheckBox("EEtiqueta",0,"Expedido:","lExpedicionBOX","1",true," Expedido...");
              Hidden("lExpedicion","");
              TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true," Vigente...");
              Hidden("lVigente","");
      FTDTR();
      FinTabla();
      ITRTD("",0,"","","center","bottom");
               Liga("Adjuntar Documentos","fRegistroDocumentos();","Adjuntar Documentos de los Títulos");
      Hidden("cPagina","pg114020062");
      Hidden("iCveVehiculo","");
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel62","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCveTipoUnidad");
    Hidden("iConsecutivoCertExp");
    Hidden("iCveUsuario");
    Hidden("cFechaActual");
    Hidden("hdPropEspecifica1","ReporteInspeccionSeguridad");
    Hidden("iNumReporte");
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel62");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(true);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado62");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Consecutivo,Tipo de Certificado,Inicio Vigencia,Fin Vigencia,Vigente,");
  FRMListado.fSetCampos("1,12,7,8,9,");
  FRMListado.fSetDespliega("texto,texto,center,center,logico,");
  FRMListado.fSetAlinea("center,left,center,center,center,");
  fDisabled(true,"cMatricula,cNEmbarcacion,");
  frm.hdBoton.value="Primero";
// fNavega();
  frm.iCveUsuario.value = cUser;
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdNumReg.value=10000;
  frm.hdOrden.value = "";
  frm.hdFiltro.value = "iNumSolicitud = "+frm.iNumSolicitud.value+" AND iEjercicio = " + frm.iEjercicio.value;
  return fEngSubmite("pgINSCertifExp.jsp","Listado");
}
function fEmbarcacion(){
  frm.hdFiltro.value = " pi.iNumSolicitud = " +frm.iNumSolicitud.value +
                       " AND pi.iEjercicio = " +frm.iEjercicio.value;
  frm.hdSelect.value =
  "SELECT rs.IEJERCICIO,rs.INUMSOLICITUD,EMB.CNOMEMBARCACION, EMB.ICVETIPOEMBARCACION, EMB.ICVEVEHICULO,EMB.CNUMOMI, VE.CMATRICULA " +
  "FROM  TRARegSolicitud RS " +
  "Join INSCERTXINSPECCION CI ON CI.IEJERCICIO = rs.IEJERCICIO and CI.INUMSOLICITUD = rs.INUMSOLICITUD " +
  "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = CI.ICVEINSPPROG " +
  "JOIN VEHEMBARCACION Emb ON PI.ICVEEMBARCACION = Emb.ICVEVEHICULO " +
  "JOIN VEHVEHICULO VE ON VE.ICVEVEHICULO= EMB.ICVEVEHICULO " +
  "Where rs.INUMSOLICITUD = "+frm.iNumSolicitud.value+" and rs.IEJERCICIO = "+frm.iEjercicio.value;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp","Listado2");
}
function fOficina(){
  frm.hdLlave.value = "";
  frm.hdSelect.value = "SELECT distinct UpO.iCveOficina, Of.CDSCBREVE FROM GRLUSUARIOXOFIC UpO "+
                       "JOIN GRLOFICINA Of ON  UpO.ICVEOFICINA = Of.ICVEOFICINA "+
                       "WHERE iCveUsuario = "+frm.iCveUsuario.value;
  fEngSubmite("pgConsulta.jsp","cIdOficina");
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,Reportes){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }
// if(cError!="")
// FRMFiltro.fSetNavStatus("Record");

  if(cId == "Listado2" && cError==""){
    frm.iCveTipoUnidad.value = aRes[0][3];
    frm.hdRowPag.value = iRowPag;
    frm.cNomEmbarcacion.value = aRes[0][2];
    frm.iCveVehiculo.value = aRes[0][4];
    frm.cNumOMI.value =aRes[0][5];
    frm.cMatricula.value = aRes[0][6];
    fOficina();
  }
  if(cId == "Listado" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fCancelar();
  }
  if(cId == "IDUnidad2" && cError==""){
    fFillSelect(frm.iUniMedArqueoBruto,aRes,false,frm.iUniMedArqueoBruto.value,0,2);
  }
  if(cId == "cIdOficina" && cError==""){
    fFillSelect(frm.iCveOficina,aRes,false,frm.iCveOficina.value,0,1);
    fTraeFechaActual();
  }
  if(cId == "Certif" && cError==""){
    if (aRes.length>0){
      cNumReportes = Reportes;
      fFillSelect(frm.iCveGrupoCertif,aRes,false,frm.iCveGrupoCertif.value,0,2);
      fFillSelect(frm.iTipoCertificado,aRes,false,frm.iTipoCertificado.value,1,3);
      if(aRes[0][4])aRes[0][4]==1?frm.lAprobadoBOX.checked = true:frm.lAprobadoBOX.checked=false;
      if(aRes[0][5])aRes[0][5]==1?frm.lAutorizadoBOX.checked = true:frm.lAutorizadoBOX.checked=false;
      fEmbarcacion();
    } else fAlert("No existe una inspeccion para esta Solicitud");
  }
  if(cId == "TipoCertif" && cError==""){
    fFillSelect(frm.iTipoCertificado,aRes,false,frm.iTipoCertificado.value,0,1);
    iTipoOnChange();
  }
  if(cId == "Folio" && cError==""){
    frm.iConsecutivoCertifExp.value = parseInt(aRes[0][0])+1;
  }
  if(cId == "idVerifica" && cError==""){
    if(aRes==0) fReporte1();
    else fAlert("Todos los certificados deben de estar autorizados para Inprimir un reporte.")
  }
  if(cId== "idFechaActual" && cError==""){
    frm.cFechaActual.value = aRes[0];
    fNavega();
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iCveVehiculo,iEjercicio,iNumSolicitud,cNumOMI,cMatricula,cNomEmbarcacion,lAprobadoBOX,lAutorizadoBOX,");
    frm.dtExpedicionCert.value = frm.cFechaActual.value;
    fDisabled(false,"iNumSolicitud,iEjercicio,cNumOMI,cMatricula,cNomEmbarcacion,iConsecutivoCertifExp,iCveVehiculo,dtExpedicionCert,lAutorizadoBOX,lAprobadoBOX,");
    FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  frm.lVigenteBOX.checked==true?frm.lVigente.value =1:frm.lVigente.value =0;
  frm.lExpedicionBOX.checked==true?frm.lExpedicion.value =1:frm.lExpedicion.value =0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("Mod,");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
}
// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
  frm.lVigenteBOX.checked==true?frm.lVigente.value =1:frm.lVigente.value =0;
  frm.lExpedicionBOX.checked==true?frm.lExpedicion.value =1:frm.lExpedicion.value =0;
   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("Mod,");
        fDisabled(true);
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
   //frm.hdFiltro.value = "UM.ICVETIPOUNIDAD = "+frm.iCveTipoUnidad.value;
   //frm.hdOrden.value = "";
   //fEngSubmite("pgVEHUnidadMedida.jsp","IDUnidad2");
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iNumSolicitud,iEjercicio,cNumOMI,iCveOficina,cMatricula,cNomEmbarcacion,iConsecutivoCertifExp,iTipoCertificado,iCveGrupoCertif,dtExpedicionCert,lAutorizadoBOX,lAprobadoBOX,");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
// FRMFiltro.fSetNavStatus("ReposRecord");
   if(FRMListado.fGetLength() > 0)
     FRMPanel.fSetTraStatus("Mod,");
   else
     FRMPanel.fSetTraStatus("AddOnly");
   fDisabled(true,"");
   FRMListado.fSetDisabled(false);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
  //frm.iCveVehiculo.value = aDato[0];
  frm.iConsecutivoCertExp.value=aDato[1];
  frm.dtExpedicionCert.value = aDato[6];
  frm.dtIniVigencia.value = aDato[7];
  frm.iConsecutivoCertifExp.value = aDato[5];
  frm.dtFinVigencia.value = aDato[8];
  frm.lExpedicion.value = aDato[10];
  frm.lExpedicion.value ==1?frm.lExpedicionBOX.checked=true:frm.lExpedicionBOX.checked=false;
  frm.lVigente.value = aDato[9];
  frm.lVigente.value ==1?frm.lVigenteBOX.checked=true:frm.lVigenteBOX.checked=false;
  frm.iNumReporte.value = aDato[13];
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   cMsg = fValElements();
   if(!fComparaFecha(frm.dtIniVigencia.value, frm.dtFinVigencia.value, true))cMsg+="\n-La fecha Final debe de ser mayor a la inicial.";
   if(frm.iTipoCertificado.value==NaN || frm.iTipoCertificado.value=="" )cMsg+="\n-El campo Tipo de Certificado es requerido";
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
function fSetValues(iEjercicio, iNumSolicitud ){
   frm.iNumSolicitud.value = iNumSolicitud;
   frm.iEjercicio.value = iEjercicio;
   frm.hdSelect.value =
   "SELECT  CI.ICVEGRUPOCERTIF,CI.ITIPOCERTIFICADO, GC.CDSCGRUPOCERTIF, TC.CDSCCERTIFICADO, CI.LAPROBADA,CI.LAUTORIZADO " +
   "FROM INSCERTXINSPECCION  CI " +
   "JOin INSGRUPOCERTIF GC ON CI.ICVEGRUPOCERTIF = GC.ICVEGRUPOCERTIF " +
   "JOIN INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO= CI.ITIPOCERTIFICADO AND TC.ICVEGRUPOCERTIF = CI.ICVEGRUPOCERTIF " +
   "WHERE CI.IEJERCICIO = "+frm.iEjercicio.value+" AND CI.INUMSOLICITUD = "+frm.iNumSolicitud.value;
   frm.hdLlave.value = "ICVEGRUPOCERTIF";
   fEngSubmite("pgConsulta.jsp","Certif");
}
function fBusqueda(){
   fNavega();
}
function fGetiEjercicio(){
   return frm.iEjercicio.value;
}
function fGetiNumSolicitud(){
   return frm.iNumSolicitud.value;
}
function fGpoOnChange(){
    frm.hdSelect.value = "SELECT ITIPOCERTIFICADO,CDSCCERTIFICADO FROM INSTIPOCERTIF   where ICVEGRUPOCERTIF = "+frm.iCveGrupoCertif.value+" AND LVIGENTE = 1 and lSeguridad = 0";
    frm.hdLlave.value = "ICVEGRUPOCERTIF,ITIPOCERTIFICADO";
    fEngSubmite("pgConsulta.jsp","TipoCertif");
}
function iTipoOnChange(){
  frm.hdSelect.value = "SELECT IFOLIO FROM INSTIPOCERTIF WHERE ICVEGRUPOCERTIF = "+frm.iCveGrupoCertif.value+" AND ITIPOCERTIFICADO = "+frm.iTipoCertificado.value;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp","Folio");
}
function fReporte(){
    fBuscaAutorizacion();
}
function fReporte1(){
  if(frm.cMatricula.value!=""){
    if(FRMListado.fGetLength() > 0){
      if(frm.iNumReporte.value > 0){
        aCBoxTra = FRMListado.fGetObjs(0);
        var formato = null;
        cClavesModulo="13,";
        cFiltrosRep=frm.iConsecutivoCertExp.value+","+frm.iCveVehiculo.value+","+frm.iCveUsuario.value+","+frm.iCveOficina.value+","+frm.iEjercicio.value+","+frm.iNumSolicitud.value+cSeparadorRep;
        cNumerosRep = frm.iNumReporte.value+",";
        fReportes();
      }else fAlert("Al reporte no se le ha seleccionado una plantilla.");
    }else fAlert("No existen datos para esta solicitud");
  }else fAlert("El Vehiculo debe de tener Matrícula");
}

function fBuscaAutorizacion(){
  frm.hdLlave.value = "";
  frm.hdSelect.value =
      "SELECT IEJERCICIO,INUMSOLICITUD FROM INSCERTXINSPECCION " +
      "Where (LAPROBADA = 0 or LAUTORIZADO = 0) and ICVEINSPPROG in " +
      "(SELECT ICVEINSPPROG FROM INSCERTXINSPECCION where INUMSOLICITUD = "+frm.iNumSolicitud.value+
      " and IEJERCICIO = "+ frm.iEjercicio.value +") ";
  fEngSubmite("pgConsulta.jsp","idVerifica");
}

function fGetParametros(){
   var aParametros = new Array();
   aParametros[0] = ""; // deprecated.
   aParametros[1] = "En este lugar puede adjuntar los Certificados de Seguridad Marítima";  //Descripcion del Proceso
   aParametros[2] = "";    // deprecated.
   aParametros[3] = ""; // deprecated.
   aParametros[4] = "INSDoctoXCertif"; // Nombre de la Tabla.
   aParametros[5] = ""; //deprecated.
   aParametros[6] = "13"; // No. de Modulo.
   aParametros[7] = "Escritura";   // Modo de la Pagina Escritura o Consulta.
   return aParametros;
}
function fGetArregloCampos(){
   var aCampos = new Array();
   aCampos[0] = "ICONSECUTIVOCERTEXP";
   aCampos[1] = "ICVEVEHICULO"; //Nombres de los Campos.
   //aCampos[2] = "iEjercicio";
   //aCampos[3] = "iCveTipoDocumento"
   return aCampos;
}
function fGetArregloDatos(){
   var aDatos = new Array();
   aDatos[0] = frm.iConsecutivoCertExp.value;
   aDatos[1] = frm.iCveVehiculo.value; //Nombres de los Valores de los Campos.
   return aDatos;
}
function fRegistroDocumentos(){
  if(FRMListado.fGetLength() > 0){
    fRegDocumentos();
  }else fAlert("No existen datos para esta solicitud");
}

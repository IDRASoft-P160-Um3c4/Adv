// MetaCD=1.0
// Title: pg114020062.js
// Description: JS "Catálogo" de la entidad INSAutorizaExpVer
// Company: Tecnología InRed S.A. de C.V.
// Author: Sandor Trujillo Q.  $$ Arturo López Peña.
var cTitulo = "";
var FRMListado = "";
var frm;
var cUser = fGetIdUsrSesion();
var aArqueo = new Array;
var aFranco = new Array;
var lRepor = false;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg114020062.js";
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
           TDEtiCampo(false,"EEtiqueta",0,"Número OMI:","cNumOMI","",30,30,"Numero OMI","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"Matrícula:","cMatricula","",15,15,"Matricula","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","10",8,8,"Ejercicio","fMayus(this);");
        FITR();
           TDEtiAreaTexto(false,"EEtiqueta",0,"Nombre Embarcación:",40,2,"cNomEmbarcacion","","Nombre Embarcacion","","fMayus(this);",'onkeydown="fMxTx(this,75);"');
           ITD("",1,"","","center");
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
            TDEtiSelect(true,"EEtiqueta",0,"Lugar Expedición:","iCvePuerto","");
          FTDTR();
              //TDEtiAreaTexto(false,"EEtiqueta",0,"Grupo y Tipo de Certificado:",100,2,"cTipoCertif","","Grupo y Tipo de Certificado","fMayus(this);","",'onkeydown="fMxTx(this,1000);"',"","","","","7");
            TDEtiSelect(true,"EEtiqueta",0,"Grupo Certificado:","iCveGrupoCertif","fGrupoOnChange();");
            TDEtiSelect(true,"EEtiqueta",0,"Tipo de Certificado:","iTipoCertificado","");//iTipoOnChange();
          FITR();
              Hidden("lAutorizado","");
              Hidden("lAprobado","");
          FITR();
            TDEtiCampo(true,"EEtiqueta",0,"Fecha de Expedición:","dtExpedicionCert","",8,8,"ArqueoBruto","fMayus(this);");
            TDEtiCampo(true,"EEtiqueta",0,"Inicio de Vigencia:","dtIniVigencia","",8,8,"ArqueoBruto","fMayus(this);");
          FITR();
            TDEtiCampo(true,"EEtiqueta",0,"Certificado Expedido:","iConsecutivoCertifExp","",8,8,"ArqueoBruto","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0,"Fin de Vigencia:","dtFinVigencia","",8,8,"ArqueoBruto","fMayus(this);");
            //Hidden("dtFinVigencia");
          FITR();
              TDEtiCheckBox("EEtiqueta",0,"Expedido:","lExpedicionBOX","1",true," Expedido...");
              Hidden("lExpedicion","");
              TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true," Vigente...");
              Hidden("lVigente","");
          FITR();
              TDEtiAreaTexto(false,"EEtiqueta",0,"Observaciones:",100,5,"cObses","","Observaciones al certificado","fMayus(this);","",'onkeydown="fMxTx(this,1000);"',"","","","","7");
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel62","95%","34","Paneles.js");
      FTDTR();
      ITRTD("",0,"","0","center");
        InicioTabla("",0,"100%","","","",1);
            ITRTD("",0,"","105","center","top");
              IFrame("ITablaDin","95%","100","tablaDin.js","yes",true);
            FTDTR();
        FinTabla();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanelA1","95%","34","Paneles.js");
       FTDTR();
      ITRTD("",0,"","","center","bottom");FTDTR();     
      ITRTD("",0,"","40","center","bottom");
        Liga("Autorizar y Finalizar","fAutorizar();");
      FITR();
        ITD();BR();  
      FTDTR();
      //FTDTR();
        //Liga("Adjuntar Documentos","fRegDocumentos();","Adjuntar Documentos de los Títulos");
      Hidden("cPagina","pg114020062");
      Hidden("iCveVehiculo","");
    FinTabla();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCveTipoUnidad");
    Hidden("iConsecutivoCertExp");
    Hidden("iCveUsuario");
    Hidden("cFechaActual");
    Hidden("iNumReporte");
    Hidden("hdPropEspecifica1","CertArqueo");
    Hidden("hdPropEspecifica2","CertFrancoBordo");
    Hidden("iCveTramite");
    Hidden("iCveModalidad");
    Hidden("iEjercicioSol");
    Hidden("iNumSolic3itudSol");
    Hidden("iCveInspeccion");
    Hidden("cCampos");
    Hidden("cConceptos");
    Hidden("cCaracteristicas");
    Hidden("cValores");
    Hidden("iCveInspProg");
    Hidden("iNumCertificado");
    Hidden("iNumSolicitudSol");
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel62");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(true);
  FRMPanel.fShow("Tra,");
   FRMPanelA = fBuscaFrame("IPanelA1");
   FRMPanelA.fSetControl(self,cPaginaWebJS);
   FRMPanelA.fShow("Tra,");
   FRMPanelA.fSetNombreFuncion("Tabla");
  FRMListado = fBuscaFrame("IListado62");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Consecutivo,Tipo de Certificado,Inicio Vigencia,Fin Vigencia,Vigente,");
  FRMListado.fSetCampos("1,12,7,8,9,");
  FRMListado.fSetDespliega("texto,texto,texto,texto,logico,");
  FRMListado.fSetAlinea("left,left,center,center,center,");

  FRMTabla = fBuscaFrame("ITablaDin");
  FRMTabla.fSetControl(self);
  FRMTabla.fSetInner(7,"Google");
  FRMTabla.fSetFormulario(7,6,4,9);
  FRMTabla.fSetAlinea("left,center,center,right,center,center");
  FRMTabla.fSetCampos("3,4");
  FRMTabla.fSetTitulo("Caracterísitca,Campo");

  fDisabled(true,"cMatricula,cNEmbarcacion,");
  frm.hdBoton.value="Primero";
  frm.iCveUsuario.value = cUser;
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdNumReg.value=10000;
  frm.hdOrden.value = "";
  frm.hdFiltro.value = "CE.iNumSolicitud = "+frm.iNumSolicitud.value+
                       " AND CE.iEjercicio = " + frm.iEjercicio.value;
  return fEngSubmite("pgINSCertifExp.jsp","Listado");
}
function fEmbarcacion(){
  frm.hdFiltro.value = " pi.iNumSolicitud = " +frm.iNumSolicitud.value +
                       " AND pi.iEjercicio = " +frm.iEjercicio.value;
  frm.hdSelect.value =  "SELECT rs.IEJERCICIO,rs.INUMSOLICITUD,EMB.CNOMEMBARCACION, EMB.ICVETIPOEMBARCACION, EMB.ICVEVEHICULO,EMB.CNUMOMI, VE.CMATRICULA, CI.LAPROBADA, CI.LAUTORIZADO " +
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
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iLongitud,iPeso){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }

  if(cId == "Listado2" && cError==""){
    if(aRes[0]){
      frm.iCveTipoUnidad.value = aRes[0][3];
      frm.hdRowPag.value = iRowPag;
      frm.cNomEmbarcacion.value = aRes[0][2];
      frm.iCveVehiculo.value = aRes[0][4];
      frm.cNumOMI.value =aRes[0][5];
      frm.cMatricula.value = aRes[0][6];
      fOficina();
    }
  }
  if(cId == "Listado" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fCancelar();
  }
  if(cId=="cIdConceptos" && cError==""){
    FRMTabla.fShow(aRes);
    if(frm.iCveGrupoCertif.disabled==true)FRMTabla.fSetDisabled(true);
    else FRMTabla.fSetDisabled(false);
    fCancelarTabla();
  }
  if(cId == "IDUnidad2" && cError==""){
    fFillSelect(frm.iUniMedArqueoBruto,aRes,false,frm.iUniMedArqueoBruto.value,0,2);
  }
  if(cId == "cIdOficina" && cError==""){
    fFillSelect(frm.iCveOficina,aRes,false,frm.iCveOficina.value,0,1);
    //frm.hdSelect.value = "SELECT ICVEPUERTO,CDSCPUERTO FROM grlpuerto where icvepais=1 order by CDSCPUERTO ";
    frm.hdSelect.value = "SELECT " +
                         "       ICVEPUERTO, " +
                         "       p.CNOMBRE ||' ' || ef.CNOMBRE ||', '||CDSCPUERTO as cpuerto " +
                         "FROM GRLPUERTO pu " +
                         "join GRLPAIS p on p.ICVEPAIS= pu.ICVEPAIS " +
                         "join GRLENTIDADFED ef on ef.ICVEPAIS=pu.ICVEPAIS and ef.ICVEENTIDADFED=pu.ICVEENTIDADFED " +
                         " where p.iCvePais > 0  "+
                         "  ORDER BY p.icvepais,ef.CNOMBRE,pu.CDSCPUERTO ";
    frm.hdLlave.value  = "ICVEPUERTO";
    fEngSubmite("pgConsulta.jsp","cIdPuerto");
  }
  if(cId=="cIdPuerto" && cError==""){
    fFillSelect(frm.iCvePuerto,aRes,false,frm.iCvePuerto.value,0,1);
      frm.hdSelect.value =
            "SELECT ICVEGRUPOCERTIF,CDSCGRUPOCERTIF FROM INSGRUPOCERTIF WHERE LACTIVO = 1 and ICVEGRUPOCERTIF in ( " +
            "SELECT ICVEGRUPOCERTIF FROM INSCERTIFICADOXMODTRAM "+
            "where ICVETRAMITE="+frm.iCveTramite.value+" and ICVEMODALIDAD="+frm.iCveModalidad.value+" ) ";
      frm.hdLlave.value  = "";
      fEngSubmite("pgConsulta.jsp", "cIdGrupoCertif");
  }
  if(cId == "cIdGrupoCertif" && cError==""){
    if(aRes.length>0){
      fFillSelect(frm.iCveGrupoCertif,aRes,false,frm.iCveGrupoCertif.value,0,1);
      frm.hdSelect.value =
               "SELECT ITIPOCERTIFICADO,CDSCCERTIFICADO FROM INSTIPOCERTIF WHERE LVIGENTE=1 "+
               "AND ICVEGRUPOCERTIF="+frm.iCveGrupoCertif.value+" AND ITIPOCERTIFICADO IN ( " +
               "SELECT ITIPOCERTIFICADO FROM INSCERTIFICADOXMODTRAM WHERE ICVETRAMITE="+frm.iCveTramite.value+
               " AND ICVEMODALIDAD="+frm.iCveModalidad.value+" AND ICVEGRUPOCERTIF="+frm.iCveGrupoCertif.value+") ";
      frm.hdLlave.value = "";
      fEngSubmite("pgConsulta.jsp","cIdTipoCertif");
    }
    else fAlert("No existen certificados configurados para este trámite y Modalidad");
    //fTraeFechaActual();
  }
  if(cId == "cIdTipoCertif" && cError==""){
    fFillSelect(frm.iTipoCertificado,aRes,false,frm.iTipoCertificado.value,0,1);
    FRMEmbarcacion = fBuscaFrame("PEM2");
    fTraeFechaActual();
    //iTipoOnChange();
  }
  if(cId== "idFechaActual" && cError==""){
    frm.cFechaActual.value = aRes[0];
    if(frm.iTipoCertificado.disabled==true) fNavega();
  }
  if(cId == "cIdSolicitud" && cError==""){
    //if (fGetPermisos(cNomPag + ".js") != 2)
    frm.iEjercicioSol.value = frm.iEjercicio.value;
    frm.iNumSolicitudSol.value = frm.iNumSolicitud.value;
    if(aRes.length>0){
      if(confirm(cAlertMsgGen + "\n\nya existe un certificado \nde tramite "+aRes[0][1]+" y modalidad "+aRes[0][3]+"\n¿Desea usted continuar con la impresión?")){
        aCBoxTra = FRMListado.fGetObjs(0);
        var formato = null;
        frm.hdBoton.value = "ConsultaTipoCertificado";
        frm.hdFiltro.value = "";
        frm.hdOrden.value = "";
        fEngSubmite("pgINSCertificadoXModTram.jsp","cIdTipoCer");
      }
      else{
        frm.hdBoton.value = "ConsultaTipoCertificado";
        frm.hdFiltro.value = "";
        frm.hdOrden.value = "";
        fEngSubmite("pgINSCertificadoXModTram.jsp","cIdTipoCer");
      }
    }
    else fAlert("No existe un trámite configurado para este reporte");
  }
  cClaveModulo="";
  cNumerosRep="";
  if(cId=="cIdTipoCer" && cError==""){
    cClavesModulo="";
    cNumeroRep="";
    cFiltrosRep="";
    for(rep=0;rep<aRes.length;rep++){
      cClavesModulo += "13,";
      cNumerosRep += aRes[rep][0]+",";
      cFiltrosRep += frm.iConsecutivoCertExp.value+","+frm.iCveVehiculo.value+","+frm.iCveUsuario.value+","+frm.iCveOficina.value+","+cSeparadorRep;
    }
    fReportes();
  }
  if(cId=="cIdAutorizar" && cError==""){
    if(aRes[0]){
      frm.lAprobado.value=aRes[0][7];
      fNavega();
    }
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("iCveVehiculo,iEjercicio,iNumSolicitud,cNumOMI,cMatricula,cNomEmbarcacion,");
    frm.dtExpedicionCert.value = frm.cFechaActual.value;
    fDisabled(false,"iNumSolicitud,iEjercicio,cNumOMI,cMatricula,cNomEmbarcacion,iConsecutivoCertifExp,iCveVehiculo,dtExpedicionCert,");
    FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  frm.lVigenteBOX.checked==true?frm.lVigente.value =1:frm.lVigente.value =0;
  frm.lExpedicionBOX.checked==true?frm.lExpedicion.value =1:frm.lExpedicion.value =0;
  frm.hdBoton.value = "GuardarCI";
    if(fValidaTodo()==true){
       if(fNavega()==true){
          if(FRMListado.fGetLength() > 0)
            FRMPanel.fSetTraStatus("Mod,");
          else FRMPanel.fSetTraStatus("Add,");
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
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iNumSolicitud,iEjercicio,cNumOMI,iCveOficina,cMatricula,cNomEmbarcacion,iConsecutivoCertifExp,iTipoCertificado,iCveGrupoCertif,dtExpedicionCert,");
    FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
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
  frm.iConsecutivoCertExp.value=aDato[1];
  frm.dtExpedicionCert.value = aDato[6];
  frm.dtIniVigencia.value = aDato[7];
  frm.iConsecutivoCertifExp.value = aDato[5];
  frm.dtFinVigencia.value = aDato[8];
  frm.lExpedicion.value = aDato[10];
  frm.lExpedicion.value ==1?frm.lExpedicionBOX.checked=true:frm.lExpedicionBOX.checked=false;
  frm.lVigente.value = aDato[9];
  frm.lVigente.value ==1?frm.lVigenteBOX.checked=true:frm.lVigenteBOX.checked=false;
  frm.iNumReporte.value=aDato[13];
  frm.cObses.value = aDato[14];
  frm.iCveInspeccion.value = aDato[15];
  frm.iCveInspProg.value = aDato[16];
  frm.iNumCertificado.value = aDato[17];
  frm.lAprobado.value = aDato[18];
  if(parseInt(frm.iConsecutivoCertExp.value,10) > 0){
	  fCargaTabla();
   frm.iCvePuerto.value = aDato[19];
  }
}
function fNuevoTabla(){
}
function fModificarTabla(){
   FRMPanelA.fSetTraStatus("UpdateBegin");
   FRMTabla.fSetDisabled(false);
}
function fGuardarTabla(){
}
function fSelRegT(){

}
function fGuardarATabla(){
  var aTabla = new Array();
  frm.cCampos.value = "";
  frm.cValores.value = "";
  frm.cCaracteristicas.value = "";
  frm.cConceptos.value = "";
  aTabla = FRMTabla.fGetARes();
  for (i=0;i<aTabla.length;i++){
    frm.cCampos.value += frm.cCampos.value == ""?aTabla[i][4]:","+aTabla[i][4];
    frm.cConceptos.value += frm.cConceptos.value == ""?aTabla[i][2]:","+aTabla[i][2];
    frm.cCaracteristicas.value += frm.cCaracteristicas.value == ""?aTabla[i][8]:","+aTabla[i][8];
    frm.cValores.value += frm.cValores.value == ""?FRMTabla.frm.elements[aTabla[i][4]].value:","+FRMTabla.frm.elements[aTabla[i][4]].value;
  }
  fGuardar1();
}
function fGuardar1(){
  frm.hdBoton.value = "GuardarCara";
   if(fValidaTodo()==true){
      if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
         FRMTabla.fSetDisabled(true);
      }
   }
}
function fBorrarTabla(){
}
function fCancelarTabla(){
   FRMTabla.fSetDisabled(true);
   if(FRMListado.fGetLength() > 0)
     FRMPanelA.fSetTraStatus("Mod,");
   else
     FRMPanelA.fSetTraStatus(",");
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){

   cMsg = fValElements();
   /*if(frm.cMatricula.value == "")
     if(fDiasEntre(frm.dtIniVigencia.value,frm.dtFinVigencia.value)>90)
       cMsg += "En el caso de un Certificado Provisional la fecha de Fin de Vigencia no puede \nser mayor de 90 dias apartir del Inicio de Vigencia";*/
   //if(!fComparaFecha(frm.dtIniVigencia.value, frm.dtFinVigencia.value, true))cMsg+="\n-La fecha Final debe de ser mayor a la inicial.";
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
function fSetValues(iEjercicio, iNumSolicitud,iCveVehiculo,iCveTramite,iCveModalidad,cNomEmbarcacion,cOMI,cMatricula){
   frm.iNumSolicitud.value = iNumSolicitud;
   frm.iEjercicio.value = iEjercicio;
   frm.iCveTramite.value = iCveTramite;
   frm.iCveModalidad.value = iCveModalidad;
   frm.iCveVehiculo.value = iCveVehiculo;
   frm.cNumOMI.value = cOMI;
   frm.cMatricula.value = cMatricula;
   frm.cNomEmbarcacion.value = cNomEmbarcacion;
   fOficina();
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
function fGetNomEmbarcacion(){
  return frm.cNomEmbarcacion.value;
}
function fGetCveEmbarcacion(){
  return frm.iCveVehiculo.value;
}
function fGetGrupoCertif(){
  return frm.iCveGrupoCertif.value;
}
function fGetCveTipoCertificado(){
  return frm.iTipoCertificado.value;
}
function fGetCertificadoExp(){
  return frm.iConsecutivoCertExp.value;
}
function fGetTipoCertificado(){
  return frm.iTipoCertificado.options[frm.iTipoCertificado.selectedIndex].text;
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
  if(frm.lAprobado.value==1){
    if(parseInt(frm.iCveVehiculo.value)>0 && parseInt(frm.iEjercicio.value)>0 && parseInt(frm.iNumSolicitud.value)>0){
        frm.iEjercicioSol.value = frm.iEjercicio.value;
        frm.iNumSolicitudSol.value = frm.iNumSolicitud.value;
        frm.hdBoton.value = "ConsultaTipoCertificado";
        frm.hdFiltro.value = "";
        frm.hdOrden.value = "";
        fEngSubmite("pgINSCertificadoXModTram.jsp","cIdTipoCer");
    }else fAlert("Es necesario contar tanto con Vehículo, Ejercicio y Solicitud.");
  }else fAlert("El certificado necesita estar Aprobado.");
}


function fGetParametros(){
   var aParametros = new Array();
   aParametros[0] = ""; // deprecated.
   aParametros[1] = "En este lugar puede adjuntar los Certificados de Francobordo";  //Descripcion del Proceso
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
   return aCampos;
}
function fGetArregloDatos(){
   var aDatos = new Array();
   aDatos[0] = frm.iConsecutivoCertExp.value;
   aDatos[1] = frm.iCveVehiculo.value; //Nombres de los Valores de los Campos.
   return aDatos;
}

function fGrupoOnChange(){
  frm.hdSelect.value  = "SELECT ITIPOCERTIFICADO,CDSCCERTIFICADO FROM INSTIPOCERTIF WHERE ICVEGRUPOCERTIF = "+frm.iCveGrupoCertif.value;
  frm.hdLlave.value   = "";
  fEngSubmite("pgConsulta.jsp","cIdTipoCertif");
}

function fSetEmbarcacion(iCveVehiculo,cNomEmbarcacion,cPropietario,cNumOMI,cMatricula,cNumSerie,cTipoServ,cTipoNavega,
                         hdPropOPoseedor,iFolioRPMN,cPaisAbanderamiento,cTipoEmbarcacion,cSenalDist,iCveTipoServ,
                         iCveTipoNavega,iCveTipoEmbarcacion,cPuertoMat){

      frm.iCveTipoUnidad.value = iCveTipoEmbarcacion;
      frm.cNomEmbarcacion.value = cNomEmbarcacion;
      frm.iCveVehiculo.value = iCveVehiculo;
      frm.cNumOMI.value = cNumOMI;
      frm.cMatricula.value = cMatricula;
    fOficina();
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
    var iDiasEntre = 0;
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

 function fCargaTabla(){
     frm.hdLlave.value = "";
     frm.hdSelect.value = "SELECT "+
                          "ct.ICVEGRUPOCERTIF,ct.ITIPOCERTIFICADO,ct.ICVECONCEPTOEVAL,cc.CDSCCARACTERISTICA,cc.CVARIABLE,"+
                          "cc.ITIPOCAMPO,15 as tamanio,'caja' as campo,cc.iCveCaracterisitic,";

                          if(frm.iCveInspeccion.value>0)frm.hdSelect.value +="ci.CVALOR ";
                          else frm.hdSelect.value +="''";
                          frm.hdSelect.value += "FROM INSCONCXTIPOCERT ct " +
                          "JOIN INSCARACTXCONC cc on ct.ICVECONCEPTOEVAL=cc.ICVECONCEPTOEVAL AND CC.LACTIVO=1 ";
                          if(frm.iCveInspeccion.value>0)
                            frm.hdSelect.value +="left join INSCARACTXINSP ci on ci.ICVECONCEPTOEVAL=ct.ICVECONCEPTOEVAL and ci.ICVECARACTERISITIC=cc.ICVECARACTERISITIC and ci.ICVEINSPECCION= "+frm.iCveInspeccion.value ;
                          frm.hdSelect.value +=" where CT.LaCTIVO=1 AND ct.ITIPOCERTIFICADO= "+frm.iTipoCertificado.value +
                          " and CT.ICVEGRUPOCERTIF="+frm.iCveGrupoCertif.value+
                          "  Order By ct.IORDEN,cc.IORDEN ";
     fEngSubmite("pgConsulta.jsp","cIdConceptos");
 }
 function fAutorizar(){
   if(frm.iCveInspProg.value>0 && frm.iConsecutivoCertExp.value > 0 && frm.iCveUsuario.value>0){
     frm.hdBoton.value = "Autorizar";
     fEngSubmite("pgINSCertxInspeccion.jsp","cIdAutorizar");
   } else fAlert("No se cuenta con todos los parámentros para la obtencion del reporte");
 }

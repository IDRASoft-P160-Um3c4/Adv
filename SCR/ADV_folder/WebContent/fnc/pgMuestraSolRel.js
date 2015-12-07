// MetaCD=1.0
// Title: pgVerificacion.js
// Description: JS Muestra el listado de las solicitudes Relacionadas a una solicitud (trámites dependientes)
// Company: Tecnología InRed S.A. de C.V.
// Author: Rafael Miranda Blumenkron.
var cTitulo = "";
var FRMListado = "", FRMListadoEtapa = "";
var frm;
var idUser = fGetIdUsrSesion();
var aResRel = new Array();
var aResTot = new Array();

function fBefLoad(){
  cPaginaWebJS = "pgMuestraSolRel.js";
  if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"SOLICITUDES RELACIONADAS":cTitulo;
  fSetWindowTitle();
}

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
              TDEtiCampo(false,"EEtiqueta",0," Número de Solicitud :","iNumSolicitud","",10,10," Solicitud ","fMayus(this);");
            FTR();
          FinTabla();
        FITR();
          TDEtiAreaTexto(false,"EEtiqueta",0," Trámite:",90,2,"cDscTramite",""," Trámite","fMayus(this);","","",true,true,false,"",0);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0," Modalidad:","cDscModalidad","",90,90," Modalidad","fMayus(this);");
        FTR();
        ITRTD("ESTitulo",4,"95%","","center");
          TextoSimple("SOLICITUDES RELACIONADAS A SU TRÁMITE");
        FTDTR();
        ITRTD("",4,"95%","150","center","top");
          IFrame("IListadoTRA","100%","145","Listado.js","yes",true);
        FTDTR();
        ITRTD("",4,"","20","center");
        FTDTR();
        ITRTD("ESTitulo",4,"95%","","center");
          TextoSimple("ETAPAS REGISTRADAS");
        FTDTR();
        ITRTD("",4,"95%","250","center","top");
          IFrame("IListadoETAPA","100%","245","Listado.js","yes",true);
        FTDTR();
      FinTabla();
    FTR();
  FinTabla();

  Hidden("iCveTramite","");
  Hidden("iCveModalidad","");
  Hidden("iNumSolPrinc","");
  Hidden("iNumSolSelec","");

  Hidden("hdSelect","");
  Hidden("hdLlave","");

  fFinPagina();
}


function fOnLoad(){
  frm = document.forms[0];

  FRMListado = fBuscaFrame("IListadoTRA");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Ejercicio,Solicitud,Tramite, Modalidad,Favorable,");
  FRMListado.fSetDespliega("texto,texto,texto,texto,logico,");
  FRMListado.fSetCampos("0,2,3,4,5,");
  FRMListado.fSetAlinea("center,center,left,left,center,");

  FRMListadoEtapa = fBuscaFrame("IListadoETAPA");
  FRMListadoEtapa.fSetControl(self);
  FRMListadoEtapa.fSetTitulo("Etapa,Oficina,Departamento,Usuario,Registro,Anexo,");
  FRMListadoEtapa.fSetCampos("7,0,1,2,5,6,");
  FRMListadoEtapa.fSetAlinea("left,center,left,center,left,center,");
  FRMListadoEtapa.fSetDespliega("texto,texto,texto,texto,texto,logico,");
  FRMListadoEtapa.fSetSelReg(2);
  fDisabled(true);

  if(top.opener && top.opener.fEnviaDatosMuestraSolRel)
    top.opener.fEnviaDatosMuestraSolRel(this);
}

function fTraeSolRel(){
  frm.hdLlave.value = "iEjercicio,iNumSolicitud,";
  frm.hdSelect.value = "SELECT SR.iEjercicio, SR.iNumSolicitudPrinc, SR.iNumSolicitudRel, TRA.cCveInterna || ' - ' || TRA.cDscTramite AS cTramite, "+
                       "MOD.cDscModalidad, RS.lResolucionPositiva " +
                       "FROM TRASolicitudRel SR " +
                       "JOIN TRARegSolicitud RS ON RS.iEjercicio = SR.iEjercicio AND RS.iNumSolicitud = SR.iNumSolicitudRel "+
                       "JOIN TRACatTramite TRA ON TRA.iCveTramite = RS.iCveTramite "+
                       "JOIN TRAModalidad MOD ON MOD.iCveModalidad = RS.iCveModalidad "+
                       "WHERE SR.iEjercicio = " + frm.iEjercicio.value + " " +
                       "  AND SR.iNumSolicitudPrinc = " + frm.iNumSolicitud.value + " " +
                       "UNION " +
                       "SELECT SR.iEjercicio, SR.iNumSolicitudPrinc, SR.iNumSolicitudRel, TRA.cCveInterna || ' - ' || TRA.cDscTramite AS cTramite, "+
                       "MOD.cDscModalidad, RS.lResolucionPositiva " +
                       "FROM TRASolicitudRel SR " +
                       "JOIN TRARegSolicitud RS ON RS.iEjercicio = SR.iEjercicio AND RS.iNumSolicitud = SR.iNumSolicitudRel "+
                       "JOIN TRACatTramite TRA ON TRA.iCveTramite = RS.iCveTramite "+
                       "JOIN TRAModalidad MOD ON MOD.iCveModalidad = RS.iCveModalidad "+
                       "WHERE SR.iEjercicio = " + frm.iEjercicio.value + " " +
                       "  AND SR.iNumSolicitudPrinc = (SELECT SR2.iNumSolicitudPrinc FROM TRASolicitudRel SR2 WHERE SR2.iEjercicio = SR.iEjercicio " +
                       "                                      AND SR2.iNumSolicitudRel = " + frm.iNumSolicitud.value + ") " +
                       "ORDER BY iEjercicio, iNumSolicitudPrinc, iNumSolicitudRel";
  if(frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "")
    fEngSubmite("pgConsulta.jsp","ListadoTRA");
}

function fTraeSolPrinc(){
  frm.hdLlave.value = "iEjercicio,iNumSolicitud,";
  frm.hdSelect.value = " SELECT RS.iEjercicio, RS.iNumSolicitud, RS.iNumSolicitud AS iNumSolicitudRel, TRA.cCveInterna || ' - ' || TRA.cDscTramite AS cTramite, "+
                       " MOD.cDscModalidad, RS.lResolucionPositiva "+
                       " FROM TRARegSolicitud RS "+
                       " JOIN TRACatTramite TRA ON TRA.iCveTramite = RS.iCveTramite "+
                       " JOIN TRAModalidad MOD ON MOD.iCveModalidad = RS.iCveModalidad "+
                       " WHERE RS.iEjercicio = " + frm.iEjercicio.value + " " +
                       "   AND RS.iNumSolicitud = " + frm.iNumSolPrinc.value + " ";
  if(frm.iEjercicio.value != "" && frm.iNumSolPrinc.value != "")
    fEngSubmite("pgConsulta.jsp","ListadoPrinc");
}

function fTraeEtapasSol(){
  frm.hdLlave.value = " iEjercicio,iNumSolicitud ";
  frm.hdSelect.value = " SELECT GRLOficina.cDscOficina, GRLDepartamento.cDscDepartamento, SEGUsuario.cNombre,  SEGUsuario.cApPaterno, SEGUsuario.cApMaterno, TRARegEtapasXmodTram.tsRegistro, TRARegEtapasXmodTram.lAnexo, cDscEtapa "+
                       " FROM  TRARegEtapasXmodTram "+
                       " JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = TRARegEtapasXmodTram.iCveDepartamento "+
                       " JOIN GRLOficina ON GRLOficina.iCveOficina = TRARegEtapasXmodTram.iCveOficina "+
                       " JOIN SEGUsuario ON SEGUsuario.iCveUsuario = TRARegEtapasXmodTram.iCveUsuario "+
                       " JOIN TRAEtapa TE ON TE.iCveEtapa = TRARegEtapasXmodTram.iCveEtapa "+
                       " WHERE  TRARegEtapasXmodTram.iEjercicio = "+ frm.iEjercicio.value + " AND TRARegEtapasXmodTram.iNumSolicitud = " +frm.iNumSolSelec.value +
                       " Order by iOrden ";
  if(frm.iEjercicio.value != "" && frm.iNumSolSelec.value != "")
    fEngSubmite("pgConsulta.jsp","ListadoEtapas");
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError != ""){
    fAlert("Ocurrió un error insesperado en la acción: " + cError);
    return;
  }else{
    if(cId == "ListadoTRA"){
      if(aRes && aRes.length && aRes.length > 0){
        aResRel = fCopiaArregloBidim(aRes);
        frm.iNumSolPrinc.value = aResRel[0][1];
      }else
        frm.iNumSolPrinc.value = frm.iNumSolicitud.value;
      fTraeSolPrinc();
    }
    if(cId == "ListadoPrinc"){
      aResTot = fCopiaArregloBidim(aRes);
      if(parseInt(aResTot[0][2],10) == parseInt(frm.iNumSolicitud.value,10)){
        frm.cDscTramite.value   = aResTot[0][3];
        frm.cDscModalidad.value = aResTot[0][4];
      }
      for(var i=0; i<aResRel.length;i++){
        aResTot[aResTot.length] = fCopiaArreglo(aResRel[i]);
        if(parseInt(aResRel[i][2],10) == parseInt(frm.iNumSolicitud.value,10)){
          frm.cDscTramite.value   = aResRel[i][3];
          frm.cDscModalidad.value = aResRel[i][4];
        }
      }
      FRMListado.fSetListado(aResTot);
      FRMListado.fShow();
      FRMListado.fSetLlave(cLlave);
    }
    if(cId == "ListadoEtapas"){
      FRMListadoEtapa.fSetListado(aRes);
      FRMListadoEtapa.fShow();
      FRMListadoEtapa.fSetLlave(cLlave);
    }
  }
}

function fCancelar(){
   fDisabled(true,"");
   FRMListado.fSetDisabled(false);
}

function fSelReg(aDato,iCol){
  frm.iNumSolSelec.value = aDato[2];
  fTraeEtapasSol();
}

function fSelReg2(aDato,iCol){
}

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

function fAsignaEjercicioSolicitud(iEjercicio, iSolicitud){
  frm.iEjercicio.value = iEjercicio;
  frm.iNumSolicitud.value = iSolicitud;
  fTraeSolRel();
}

// MetaCD=1.0
// Title: pgVerificacion.js
// Description: JS Muestra el listado de requisitos registrados de una solicitud
// Company: Tecnología InRed S.A. de C.V.
// Author: Rafael Miranda Blumenkron.
var cTitulo = "";
var FRMListado = "";
var frm;
var idUser = fGetIdUsrSesion();

function fBefLoad(){
  cPaginaWebJS = "pgMuestraRequisitos.js";
  if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"LISTADO DE REQUISITOS":cTitulo;
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
        ITRTD("",4,"","330","center","top");
          IFrame("IListado01A","95%","325","Listado.js","yes",true);
        FTDTR();
      FinTabla();
    FTR();
  FinTabla();

  Hidden("iCveTramite","");
  Hidden("iCveModalidad","");

  fFinPagina();
}


function fOnLoad(){
  frm = document.forms[0];

  FRMListado = fBuscaFrame("IListado01A");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Id,Requisito,Fecha<BR>Recepción,Válido,Recibió,");
  FRMListado.fSetDespliega("texto,texto,texto,logico,texto,");
  FRMListado.fSetCampos("4,11,5,10,12,");
  FRMListado.fSetAlinea("right,left,center,center,left,");

  fDisabled(true);

  if(top.opener && top.opener.fEnviaDatosMuestraRequisitos)
    top.opener.fEnviaDatosMuestraRequisitos(this);
}

function fNavega(){
  frm.hdNumReg.value = 10000;
  frm.hdFiltro.value = " TRAREGSOLICITUD.iEjercicio = " + frm.iEjercicio.value +
                       " AND TRAREGSOLICITUD.iNumSolicitud = " + frm.iNumSolicitud.value + " ";
  if(frm.iEjercicio.value != "" && frm.iNumSolicitud.value != "")
    fEngSubmite("pgTRARegReqXTramA.jsp","Listado01A");
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError != ""){
    fAlert("Ocurrió un error insesperado en la acción: " + cError);
    return;
  }else{
    if(cId == "Listado01A"){
      FRMListado.fSetListado(aRes);
      FRMListado.fShow();
      FRMListado.fSetLlave(cLlave);
    }
  }
}

function fCancelar(){
   fDisabled(true,"");
   FRMListado.fSetDisabled(false);
}

function fSelReg(aDato,iCol){
  frm.iCveTramite.value   = aDato[2];
  frm.iCveModalidad.value = aDato[3];
  frm.cDscTramite.value   = aDato[13] + " - " + aDato[14];
  frm.cDscModalidad.value = aDato[15];
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
  fNavega();
}

// MetaCD=1.0
// Title: pgVerificacion.js
// Company: Tecnología InRed S.A. de C.V.
// Author: iCaballero
var cTitulo = "";
var FRMListado = "";
var frm;
//var lModificacion = false; // Para ver si se hizo algun cambio

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111020194.js";
  if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"REQUISITOS":cTitulo;
  fSetWindowTitle();

}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",4,"100%","","center");
       fTituloEmergente(cTitulo);
     FTDTR();
     ITRTD("",2,"","","top");
     InicioTabla("ETablaInfo",1,"100%","","center");
      ITR();
        InicioTabla("ETablaInfo",0,"95%","","center");
          ITD("ESTitulo",4,"100%","","center");
            TextoSimple("SOLICITUD");
          ITR();
            TDEtiCampo(false,"EEtiqueta",0," Ejercicio :","iEjercicio","",4,4," Ejercicio ","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0," Número de Solicitud :","iNumSolicitud","",15,15," Solicitud ","fMayus(this);");
          FTR();
          ITD("ESTitulo",4,"100%","","center");
            TextoSimple("DESCRIPCIÓN DEL REGISTRO DEL TRÁMITE");
          ITR();
            TDEtiCampo(false,"EEtiqueta",0,"Solictante :","cSolicitante","",60,60," Solicitante ","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0,"RFC :","cRFC","",13,13,"RFC ","fMayus(this);");
          FTR();
          ITR();
            TDEtiCampo(false,"EEtiqueta",0,"Trámite :","cTramite","",60,60," Trámite ","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0,"Modalidad :","cModalidad","",30,30,"Modalidad ","fMayus(this);");
          FTR();
          ITR();
        FinTabla();
        ITR();
        ITD("ESTitulo",4,"95%","","center");
          TextoSimple("REQUISITOS QUE REQUIEREN DIGITALIZACIÓN");
        FTR();
        ITR();
        ITRTD("",4,"","145","center","top");
          IFrame("IListado01A","95%","140","Listado.js","yes",true);
        FTDTR();
        InicioTabla("",0,"45%","","center");
          ITD("",4,"100%","","center");
            BtnImg("Salir","aceptar","fAceptar();");
          FTDTR();
        FinTabla();
     FinTabla();
     FTDTR();

     Hidden("iCveTramite","");
     Hidden("iCveModalidad","");
     Hidden("iCveRequisito","");
     Hidden("hdLlave");
     Hidden("hdSelect");

   FinTabla();
   fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMListado = fBuscaFrame("IListado01A");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Requisito,Anexar Documento,¿Se Digitalizó el Documento?,");
  FRMListado.fSetDespliega("texto,texto,texto,");
  FRMListado.fSetObjs(1,"Boton");
  FRMListado.fSetCampos("1,2,");
  FRMListado.fSetAlinea("left,center,center,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  fRecibeValores();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdNumReg.value = 100000;
  frm.hdLlave.value = "iCveTramite,iCveModalidad,iCveRequisito,iEjercicioDocumento,iEjercicioReq,iCveTipoDocumento,iIdDocumento,iNumSolicitud";
  frm.hdSelect.value = "SELECT " +
			"REQ.ICVEREQUISITO, " +
			"REQ.CDSCREQUISITO AS cReq, " +
			"( " +
			"SELECT COUNT(DOC.IEJERCICIOREQ) AS ITRA " +
			"FROM TRADOCXREQUIS DOC " +
			"WHERE RRT.IEJERCICIO = DOC.IEJERCICIOREQ " +
			"AND RRT.INUMSOLICITUD = DOC.INUMSOLICITUD " +
			"AND RRT.ICVETRAMITE = DOC.ICVETRAMITE " +
			"AND RRT.ICVEMODALIDAD = DOC.ICVEMODALIDAD " +
			"AND RRT.ICVEREQUISITO = DOC.ICVEREQUISITO " +
			") AS lAdjunto " +
			"FROM TRAREGREQXTRAM RRT " +
			"JOIN TRAREQUISITO REQ ON RRT.ICVEREQUISITO = REQ.ICVEREQUISITO "+
			"WHERE RRT.IEJERCICIO = " +frm.iEjercicio.value+
			" AND RRT.INUMSOLICITUD = "+frm.iNumSolicitud.value+
                        " and REQ.LDIGITALIZA = 1";
  fEngSubmite("pgConsulta.jsp","Listado01A");
}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }
  if(cId == "Listado01A" && cError==""){
    aResListado = fCopiaArregloBidim(aRes);
    for(var i=0; i<aResListado.length; i++)
      if(aResListado[i][2] > 0)
        aResListado[i][2] = "<font color=blue>" + "SI" + "</font>";
      else
        aResListado[i][2] = "<font color=red>" + "NO" + "</font>";

    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aResListado);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);

  }

}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
}
// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
}
// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato,iCol){

  frm.iCveRequisito.value = aDato[0];

  if(iCol==1)
    fRegDocumentos();
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
  frm.iNumSolicitud.value = top.opener.fGetiNumSolicitud();
  frm.iEjercicio.value    = top.opener.fGetiEjercicio();
  frm.cTramite.value   = top.opener.fGetcDscTramite();
  frm.cModalidad.value = top.opener.fGetDscModalidad();
  frm.cRFC.value = top.opener.fGetcRFC();
  frm.cSolicitante.value = top.opener.fGetcSolicitante();
  frm.iCveTramite.value = top.opener.fGetiCveTramite();
  frm.iCveModalidad.value = top.opener.fGetiCveModalidad();
  fNavega();
}
function fAceptar(){
  var lRep = true;
  var lDigitalizado = true;
  for(var i=0;i<aResListado.length; i++)
    if(aResListado[i][2] == "<font color=red>" + "NO" + "</font>"){
      lDigitalizado = false;
      break;
    }

  if(!lDigitalizado){
    fAlert("\nFavor de digitalizar los documentos de cada requisito.");
    return;
  }

  if(top.opener.fDigitalizaEjecutado)
    top.opener.fDigitalizaEjecutado(lRep, frm.iNumSolicitud.value);

  top.close();
}
function fAceptarDocumento(obj){
  fNavega();
  if(obj)
    obj.close();
}
/*Funciones que necesita el Content Manager*/

function fGetParametros(){
   var aParametros = new Array();
   aParametros[0] = ""; // deprecated.
   aParametros[1] = "Docuemntos Adjuntos de Trámites a Distancia.";  //Descripcion del Proceso
   aParametros[2] = "";    // deprecated.
   aParametros[3] = ""; // deprecated.
   aParametros[4] = "TRADocXRequis"; // Nombre de la Tabla.
   aParametros[5] = ""; //deprecated.
   aParametros[6] = "2"; // No. de Modulo.
   aParametros[7] = "Escritura";   // Modo de la Pagina Escritura o Consulta.
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

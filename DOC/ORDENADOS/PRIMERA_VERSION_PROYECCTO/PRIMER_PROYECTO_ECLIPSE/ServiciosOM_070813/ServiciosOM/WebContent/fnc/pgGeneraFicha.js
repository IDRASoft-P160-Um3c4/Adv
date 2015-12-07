// MetaCD=1.0
 // Title: pg111020170.js
 // Description: JS "Catálogo" de la entidad TRAConceptoPago
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero

 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var calculados = false;
 var aResPaso = new Array();
 var aResDatosRef = new Array();
 var aResDatosRef2;
 var CveModalidad = 0;
 var CveTramite = 0;
 var BuscarSolicitante = true;
 var aResUsuIngresos = new Array();
 var lEjecutafBusquedaSolIng = false;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020170.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
   fSetWindowTitle();
 }

 // SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",0,"100%","","center");
       fTituloEmergente(cTitulo);
    FTDTR();
    ITRTD("",0,"","95","center","center");
      IFrame("IListado","95%","110","Listado.js","yes",true);
    FTDTR();
    ITRTD("",0,"95%","","center","top");
      InicioTabla("",0,"","","center");
        ITR();
          TDEtiCampo(false,"EEtiqueta",0,"R.F.C.:","cRFC","",16,16," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiCampo(false,"EEtiqueta",0,"Nombre o<br>Razón Social:","cNomRazonSocial","",75,95," Nombre o Razón Social","fMayus(this);","","",false,"EEtiquetaL",0);
        FITR();
          TDEtiAreaTexto(false,"EEtiqueta",0,"Domicilio:",107,2,"cDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",3);
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","40","center","top");
      InicioTabla("",0,"90%","","center");
        ITRTD("ENormalC",0,"","20","center","top");
          TextoSimple("Seleccione el Solicitante que corresponda en el listado de ingresos. <br> "+
                      "Si no existe en el listado de ingresos de clic en <b>registrar solicitante en ingresos</b> \n"+
                      "y selecciónelo");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","","center","center");
      InicioTabla("",0,"95%","","center");
        ITRTD("ESTitulo");
          TextoSimple("Listado de solicitantes registrados en ingresos");
        FTDTR();
      FinTabla();
    FTDTR();
    ITRTD("",0,"","","center","center");
      IFrame("IListadoUsuIng","95%","130","Listado.js","yes",true);
    FTDTR();

    ITRTD("",0,"","30","center","center");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"30%","","center");
          Liga("Registrar Solicitante en Ingresos","fRegistraSolIng();","Registrar solicitante en Ingresos...");
        FITD("",0,"30%","","center");
          Liga("Generar Fichas","fGeneraFichas();","Genera las fichas de pago");
        FITD("",0,"30%","","center");
          BtnImg("Aceptar","aceptar","fAceptar();","");
        FTDTR();
      FinTabla();
    FTDTR();
  FinTabla();

  Hidden("iCveGrupo","");
  Hidden("iCveConcepto","");
  Hidden("lPagoAnticipado","0");
  Hidden("iUnidades","");
  Hidden("lEsTarifa","");
  Hidden("lEsPorcentaje","");
  Hidden("dImporteSinAjuste","");
  Hidden("dImporteAjustado","");
  Hidden("cDscConcepto","");
  Hidden("iRefNumerica","");

  Hidden("iCvePersona","");
  Hidden("iCvePersonaIng","");
  Hidden("iTipoPersona","");
  Hidden("cNombre","");
  Hidden("cNombreIng","");
  Hidden("cApPaterno","");
  Hidden("cApMaterno","");
  Hidden("iCveDomicilio","");
  Hidden("iCveRepLegal","");
  Hidden("iCveDomRepLegal","");

  Hidden("iCveTramite");
  Hidden("iCveModalidad");
  Hidden("hdLlave");
  Hidden("hdSelect");
  Hidden("iEjercicio");
  Hidden("iNumSolicitud");
  Hidden("cRefAlfaNumerica");

  Hidden("hdaRes");
  Hidden("iCveUsuario");
  fFinPagina();
}

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];

   frm.iCveUsuario.value = fGetIdUsrSesion();

   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo(",Descripción,Unidades,Importe,");
   FRMListado.fSetCampos("5,6,");
   FRMListado.fSetObjs(0,"Caja")
   FRMListado.fSetObjs(2,"Campo");
   FRMListado.fSetAlinea("center,left,center,right,");
   FRMListado.fSetColTit( 3,"right",100)

   FRMListadoUsuIng = fBuscaFrame("IListadoUsuIng");
   FRMListadoUsuIng.fSetControl(self);
   FRMListadoUsuIng.fSetTitulo("Cve en Ingresos,RFC,Nombre,Domicilio,");
   FRMListadoUsuIng.fSetCampos("16,10,13,15,");
   FRMListadoUsuIng.fSetSelReg(2);

   frm.cNomRazonSocial.disabled = true;
   frm.cDomicilio.disabled = true;
   frm.cRFC.disabled = true;

   frm.hdBoton.value="Primero";

   //Busca si hay un opener para asignar datos del solicitante
   fBuscaSolEnTopOpener();
//   fNavega();
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdLlave.value = "";
   frm.hdSelect.value = "SELECT " +
		"0 AS ICOLCERO, " +
		"0 AS ICOLUNO, " +
		"0 AS ICOLDOS, " +
		"0 AS ICOLTRES, " +
		"RP.INUMUNIDADES AS INUMUNIDADES, " +
		"CP.CDSCCONCEPTO AS CDSCCONCEPTO, " +
		"RP.DIMPORTEPAGAR AS DIMPORTEPAGAR, " +
		"RP.IREFNUMERICA AS IREFNUMERICA, " +
		"RC.DIMPORTESINAJUSTE " +
		"FROM TRAREGREFPAGO RP " +
		"JOIN TRACONCEPTOPAGO CP ON RP.ICVECONCEPTO = CP.ICVECONCEPTO " +
		"JOIN TRAREFERENCIACONCEPTO RC ON CP.ICVECONCEPTO = RC.ICVECONCEPTO " +
		"where RP.IEJERCICIO = " +frm.iEjercicio.value +
		" and RP.INUMSOLICITUD = " + frm.iNumSolicitud.value +
                " and CREFALFANUM like ' '" +
                "order by iConsecutivo";

   frm.hdNumReg.value =  10000;

   if (frm.iCveTramite.value >0 && frm.iCveModalidad.value >0)
     fEngSubmite("pgConsulta.jsp","Listado2");

   FRMListado.fSetDisabled(false);
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

   if(cId == "Listado" && cError==""){
     calculados = false;
     frm.hdRowPag.value = iRowPag;
     aRes2 = fPintaText(aRes);
     aResultadoTemp = aRes;
     FRMListado.fSetCamposDespliega(aRes2);

     if(aRes.length>0){
       for(var cont=0;cont<aRes.length;cont++){
         if(parseInt(aRes[cont][11],10)>0){
           aRes[cont][5]=aRes[cont][11]+" - "+aRes[cont][5];
         }
       }
     }

     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fBusquedaSolIng();
   }

   if(cId == "Listado2" && cError==""){
     calculados = true;
     frm.hdRowPag.value = iRowPag;

     if(aRes.length>0){
       for(var cont=0;cont<aRes.length;cont++){
         if(parseInt(aRes[cont][7],10)>0){
           aRes[cont][5]=aRes[cont][7]+" - "+aRes[cont][5];
         }
       }
     }

     aRes2 = fCopiaArreglo(aRes);
     aResultado = new Array();
     aSeleccionado = new Array();
     for(x=0; x<aRes2.length; x++){
       aResultado[aResultado.length] = false;
       aSeleccionado[aSeleccionado.length] = 1;
     }

     FRMListado.fSetListado(aRes2);
     FRMListado.fSetCamposDespliega(aResultado);
     FRMListado.fShow();
     FRMListado.fSetDefaultValues(0, -1, aSeleccionado);
     FRMListado.fSetLlave(cLlave);
     FRMListado.fSetDisabled(true);
     fBusquedaSolIng();
   }

   if(cId == "UsuariosIng" && cError==""){
     aResUsuIngresos = fCopiaArreglo(aRes);
     frm.hdRowPag.value = iRowPag;
     FRMListadoUsuIng.fSetListado(aResUsuIngresos);
     FRMListadoUsuIng.fShow();
     FRMListadoUsuIng.fSetLlave(cLlave);
   }
}

function fTramiteOnChangeLocal(){
  if(CveTramite > 0){
    fSelectSetIndexFromValue(frm.iCveTramite,CveTramite);
  }
}

function fSelReg(aDato){
}

function fSelReg2(aDato){
  frm.iCvePersonaIng.value = aDato[16];
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

function fPintaText(aResTemp){
  var aResultado = new Array();
  for(x=0;x < aResTemp.length; x++){
    if(aResTemp[x][0]>0)
      aResultado[aResultado.length] = true;
    else
      aResultado[aResultado.length] = false;
  }
  return aResultado;
}

function fGeneraFichas(){
  if(FRMListado.fGetLength() > 0){
    if( frm.iCvePersonaIng.value == "" ){
      fAlert("\nSeleccione el solicitante de la lista de solicitantes registrados en ingresos si no esta regístrelo.");
      return;
    }

    frm.hdBoton.value="CalcularGenerar";
    aCBoxGenerar = FRMListado.fGetObjs(0);
    for(cont=0;cont < aCBoxGenerar.length;cont++){
      if(!aCBoxGenerar[cont]){
        if(cont==(aCBoxGenerar.length-1)){
          fAlert("Debe de seleccionar los conceptos de los que desea se generen en la ficha de pago");
          return;
        }
      }
    }
    if (!calculados){
      fAlert("Tiene que calcular los importes a pagar.");
      return;
    }
    if( frm.cRFC.value == "" ){
      fAlert("Debe de seleccionar un solicitante");
      return;
    }

    fAbreFichaIngresos();
  }else
    fAlert('\n - Debe de existir un concepto de pago para generar la referencia alfanumérica.');
}

function fBlurChangeCampo(iPos,iCol,objCampo,cEvento){
  if (cEvento == 'onBlur')
    if (!fSoloNumeros(objCampo.value)){
      fAlert("Favor de proporcionar un dato numérico");
      objCampo.focus();
      return;
    }
}

function fPagoAnticipado(){
  frm.lPagoAnticipado.value = (frm.lPagoAnticipadoBOX.checked)?1:0;
}

function fGetAResultadoTemp(){
  return FRMListado.fGetARes();
}

function fBusquedaSolIng(){
  if(frm.cRFC.value!=""){
    frm.hdBoton.value = "BuscaSolIng";
    frm.hdNumReg.value = 100000;
    fEngSubmite("pgTRAImporteConceptoXTramMod.jsp","UsuariosIng");
  }
}

function fRegistraSolIng(){
  if (confirm("La información se registrará en el Sistema de Ingresos \n ¿Desea continuar?")) {
  	if(frm.cRFC.value!=""){
    		frm.hdBoton.value = "RegistraSolIng";
    		frm.hdNumReg.value = 10000;
    		fEngSubmite("pgTRAImporteConceptoXTramMod.jsp","RegSolicitante");
    		fBusquedaSolIng();
      	}else
   	 fAlert("Debe de seleccionar un solicitante.")
    }
}

function fBuscaSolEnTopOpener(){
  if(top.opener){
    if(top.opener.fGetDatosPersona)
      top.opener.fGetDatosPersona(this);
  }
}

function fDatosReferencia(paraResDatos,frmPopup){
  aResDatosRef = new Array();
  frm.cRefAlfaNumerica.value = "";
  for (var i=0; i<paraResDatos.length; i++)
    frm.cRefAlfaNumerica.value = (frm.cRefAlfaNumerica.value == "")?paraResDatos[i][2]:frm.cRefAlfaNumerica.value + "," + paraResDatos[i][2];

  if(frmPopup)
     frmPopup.close();

  frm.hdBoton.value = "ActualizaAlfanumerico"
  fEngSubmite("pgTRAImporteConceptoXTramMod.jsp","ActualizaAlfanumerico");
}

function fAceptar(){
  if(top.opener)
    if(top.opener.fDatosReferenciaAlfanumerica)
      top.opener.fDatosReferenciaAlfanumerica(top);
}

function fSetDatosSolicitante(iCveSolicitante,cRFC,cNomRazonSocial,cDomicilio,iCveTramite,iCveModalidad,iEjercicio,iNumSolicitud){
   frm.iCvePersona.value = iCveSolicitante;
   frm.cRFC.value = cRFC;
   frm.cNomRazonSocial.value = cNomRazonSocial;
   frm.cDomicilio.value = cDomicilio;
   frm.iCveTramite.value = iCveTramite;
   frm.iCveModalidad.value = iCveModalidad;
   frm.iEjercicio.value = iEjercicio;
   frm.iNumSolicitud.value = iNumSolicitud;
   fNavega();
}

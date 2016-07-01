// MetaCD=1.0
 // Title: pg111020170.js
 // Description: JS "Catálogo" de la entidad TRAConceptoPago
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ABarrientos <dd> Rafael Miranda Blumenkron
 // Comentarios: Esta pantalla puede ser llamada por otra y asignarle a esta los valores del solicitante
 //              para esto la pantalla opener debe de tener las funciones fDatosReferencia(aResDatosRef,frmEmergente) y
 //              fGetDatosPersona(frmEmergente).
 //              a fGetDatosPersona(frmEmergente) se le manda el apuntador al frame de esta ventana, y en
 //              esa función deben de ejecutar la función
 //              frmEmergente.fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
 //                        iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
 //                        iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
 //                        cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio,iCveTramite,iCveModalidad,
 //                        lPagoAnticipado, lDesactivarAnticipado)
 //              En fDatosReferencia(aResDatosRef,frmEmergente) se manda el aRes con la información de las referencias
 //              generadas, el contenido es el siguiente: 0 - No de deposito, 1 - referencia númerica
 //              2 - referencia alfanumérica, 3 - Importe con formato, 4 - Area recaudadora, 5 - sucursal banamex,
 //              6 - cuenta banamex, 7 - importe sin formato, 8 - Cve del concepto, 9 - Cve del solicitante en ingresos
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
 var aResUsuIngresos = null;
 var lEjecutafBusquedaSolIng = false;
 var lInicioDeTramite = true;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020170.js";
   
   if(top.fGetTituloPagina){;
//     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = "GENERAR HOJA DE AYUDA";
   }
   
   fSetWindowTitle();
 }

 // SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",0,"100%","","center");
       fTituloEmergente(cTitulo);
      //TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"","","top");
      InicioTabla("",0,"95%","","center");
        ITRTD();
          InicioTabla("",0,"100%","","center");
            fRequisitoModalidad(true);
          FinTabla();
        FTDTR();
        ITRTD();
          InicioTabla("",0,"40%","","center");
            ITR();
              //TDEtiCheckBox("EEtiqueta",0,"Solo Pago Anticipado:","lPagoAnticipadoBOX","1",true,"Busca los que requieren pago anticipado","fPagoAnticipado();");
            //FITR();
              ITD("",0,"","","center","middle");
                BtnImg("Buscar","lupa","fNavega();");
              FTD();
            FTR();
          FinTabla();
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","95","center","center");
      IFrame("IListado","95%","110","Listado.js","yes",true);
    FTDTR();

    ITRTD("","","","35","center");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"","50%","center");
          Liga("Calcular","fCalcula();","Cálcula el importe  a pagar");
        FITD();
          //Liga("Búsqueda de Solicitante","fBuscarSolicitante();","Búsqueda de solicitante...");
        FTDTR();
      FinTabla();
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
  Hidden("lAplicaFactorDirecto","");

  Hidden("iCvePersona","");
  Hidden("iCvePersonaIng","");
  Hidden("iTipoPersona","");
  Hidden("cNombre","");
  //Hidden("cNombreIng","");
  Hidden("cApPaterno","");
  Hidden("cApMaterno","");
  Hidden("iCveDomicilio","");
  Hidden("iCveRepLegal","");
  Hidden("iCveDomRepLegal","");

  Hidden("cRPA","");
  Hidden("cCURP","");
  Hidden("cCalle","");
  Hidden("cTelefono","");
  Hidden("iCvePais","");
  Hidden("iCveEntidadFed","");
  Hidden("iCveMunicipio","");
  Hidden("cColonia","");
  Hidden("cCodPostal","");
  Hidden("cDscPais","");
  Hidden("cDscEntidadFed","");
  Hidden("cDscMunicipio","");
  Hidden("cNumExterior","");
  Hidden("cNumInterior","");


  Hidden("FILNombre","");
  Hidden("hdCampoClave","");
  Hidden("FILRFC","");
  Hidden("hdUrlIngresos","");
  //Hidden("SLSCentro","");
  Hidden("hdUnidadAdm","");
  Hidden("SLSArea","");
  Hidden("SLSOficina","");
  Hidden("hdURLRespuesta","");
  Hidden("hdDatosAdicionales","");
  Hidden("hdBien","");
  Hidden("hdUnidCalculo","");
  Hidden("hdIdBien","");

  Hidden("FILRefer1","");
  Hidden("FILRefer2","");
  Hidden("FILRefer3","");
  Hidden("FILRefer4","");
  Hidden("FILRefer5","");
  Hidden("FILRefer6","");
  Hidden("FILRefer7","");
  Hidden("FILRefer8","");
  Hidden("FILRefer9","");
  Hidden("FILRefer10","");

  Hidden("TBXEmite1","1");
  Hidden("TBXEmite2","1");
  Hidden("TBXEmite3","1");
  Hidden("TBXEmite4","1");
  Hidden("TBXEmite5","1");
  Hidden("TBXEmite6","1");
  Hidden("TBXEmite7","1");
  Hidden("TBXEmite8","1");
  Hidden("TBXEmite9","1");
  Hidden("TBXEmite10","1");

  Hidden("FILUnidad1","1");
  Hidden("FILUnidad2","1");
  Hidden("FILUnidad3","1");
  Hidden("FILUnidad4","1");
  Hidden("FILUnidad5","1");
  Hidden("FILUnidad6","1");
  Hidden("FILUnidad7","1");
  Hidden("FILUnidad8","1");
  Hidden("FILUnidad9","1");
  Hidden("FILUnidad10","1");

  Hidden("FILTotal1","");
  Hidden("FILTotal2","");
  Hidden("FILTotal3","");
  Hidden("FILTotal4","");
  Hidden("FILTotal5","");
  Hidden("FILTotal6","");
  Hidden("FILTotal7","");
  Hidden("FILTotal8","");
  Hidden("FILTotal9","");
  Hidden("FILTotal10","");

  Hidden("TXTObserva1","");
  Hidden("TXTObserva2","");
  Hidden("TXTObserva3","");
  Hidden("TXTObserva4","");
  Hidden("TXTObserva5","");
  Hidden("TXTObserva6","");
  Hidden("TXTObserva7","");
  Hidden("TXTObserva8","");
  Hidden("TXTObserva9","");
  Hidden("TXTObserva10","");

  Hidden("hdUsrIngresos","");
  Hidden("hdPassUsrIngresos","");

  Hidden("iEjercicio","");
  Hidden("iNumSolicitud","");

  Hidden("hdaRes");
  Hidden("iCveUsuario");

  Hidden("hdRutaJSSource",cRutaIniWebSrv);

  fFinPagina();
}

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];

   frm.iCveUsuario.value = fGetIdUsrSesion();

   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo(",Descripción,Unidades,Importe,");
   //FRMListado.fSetCampos("5,6,");
//   FRMListado.fSetObjs(0,"Caja");
//   FRMListado.fSetObjs(2,"Campo");
//   FRMListado.fSetAlinea("center,left,center,right,");
//   FRMListado.fSetColTit( 3,"right",100)

   FRMListadoUsuIng = fBuscaFrame("IListadoUsuIng");
   FRMListadoUsuIng.fSetControl(self);
   FRMListadoUsuIng.fSetTitulo("Cve en Ingresos,RFC,Nombre,Domicilio,");
   FRMListadoUsuIng.fSetCampos("16,10,13,15,");
   FRMListadoUsuIng.fSetSelReg(2);

   frm.cNomRazonSocial.disabled = true;
   frm.cDomicilio.disabled = true;
   frm.cRFC.disabled = true;

   frm.hdBoton.value="Primero";

   fCargaTramites();

   //Busca si hay un opener para asignar datos del solicitante
   fBuscaSolEnTopOpener();
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdBoton.value  = "";
   frm.hdFiltro.value = "";
   frm.hdNumReg.value =  10000;

   if (frm.iCveTramite.value >0 && frm.iCveModalidad.value >0)
     fEngSubmite("pgTRAImporteConceptoXTramMod.jsp","Listado");
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
	   
	   FRMListado.fSetTitulo(",Descripción,Unidades,Importe,");
	 FRMListado.fSetCampos("5,6,");
	 FRMListado.fSetObjs(0,"Caja");
	   FRMListado.fSetObjs(2,"Campo");
	   FRMListado.fSetAlinea("center,left,center,right,");
	   //FRMListado.fSetColTit( 3,"right",100);
	 
     calculados = false;
     frm.hdRowPag.value = iRowPag;
     aRes2 = fPintaText(aRes);
     //aResultadoTemp = aRes;
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
//     var vObjeto = null;
//     for(var i=0; i<aRes.length; i++){
//       vObjeto = eval("FRMListado.frm.OCampB2" + i);
//       if(vObjeto){
//         vObjeto.value = frm.hdUnidCalculo.value;
//         if(lInicioDeTramite || parseInt(frm.hdIdBien.value,10)>0)
//           vObjeto.disabled = true;
//       }
//     }
     aResultadoTemp = fCopiaArregloBidim(aRes); 
     if(aResUsuIngresos == null || (aResUsuIngresos.length && aResUsuIngresos.length == 0))
       fBusquedaSolIng();
   }

   if(cId == "Listado2" && cError==""){
     calculados = true;
     frm.hdRowPag.value = iRowPag;
     
//     if(aRes.length>0){
//       for(var cont=0;cont<aRes.length;cont++){
//         if(parseInt(aRes[cont][7],10)>0){
//           aRes[cont][5]=aRes[cont][7]+" - "+aRes[cont][5];
//           aRes[cont][5]=aRes[cont][7]+" - "+aRes[cont][5];
//         }
//       }
//     }
     FRMListado.fDelObjs();
     FRMListado.fSetTitulo("Descripción,Unidades,Importe,");
	 FRMListado.fSetCampos("5,4,6,");
	 FRMListado.fSetAlinea("left,right,center,");
     
     aRes2 = fCopiaArreglo(aRes);
     aResultado = new Array();
     aSeleccionado = new Array();
     for(x=0; x<aRes2.length; x++){
       aResultado[aResultado.length] = false;
       aSeleccionado[aSeleccionado.length] = 1;
     }

     FRMListado.fSetListado(aRes2);
     //FRMListado.fSetCamposDespliega(aResultado);
     FRMListado.fShow();
     FRMListado.fSetDefaultValues(0, -1, aSeleccionado);
     FRMListado.fSetLlave(cLlave);
     FRMListado.fSetDisabled(true);
   }

   if(cId == "UsuariosIng" && cError==""){
     aResUsuIngresos = fCopiaArregloBidim(aRes);
     frm.hdRowPag.value = iRowPag;
     FRMListadoUsuIng.fSetListado(aResUsuIngresos);
     FRMListadoUsuIng.fShow();
     FRMListadoUsuIng.fSetLlave(cLlave);
   }

  fResTramiteModalidad(aRes,cId,cError);

  if(cId == "CIDTramite" && cError==""){
    if(CveTramite > 0){
      fSelectSetIndexFromValue(frm.iCveTramite,CveTramite);
      frm.cCveTramite.disabled = true;
      frm.iCveTramite.disabled = true;
      fTramiteOnChange(true);
      if(frm.iCveTramite.value == "-1"){
        frm.iCveModalidad.disabled = true;
        fAlert("El trámite ya no está vigente");
      }
    }
  }

  if(cId == "CIDModalidad" && cError==""){
    if (frm.iCveTramite.value > 0 && CveModalidad > 0){
      fSelectSetIndexFromValue(frm.iCveModalidad,CveModalidad);
      frm.iCveModalidad.disabled = true;
      fNavega();
    }
  }

  if(cId == "cIdPagIngresos" && cError==""){
    var aResRefs = new Array();
    frm.hdBoton.value = "GeneraPorImporteTotal";
    frm.hdCampoClave.value = frm.iCvePersonaIng.value;
    
    frm.hdUrlIngresos.value = aRes[0];
    frm.SLSArea.value = aRes[3];
    frm.hdUsrIngresos.value  = aRes[5];
    frm.hdPassUsrIngresos.value = aRes[6];
    frm.hdURLRespuesta.value = aRes[7];
    
    aResRefs = fCopiaArregloBidim(FRMListado.fGetARes());

    var cConceptos="";
    for(var iRefs=0;iRefs<aResRefs.length;iRefs++){
       eval("frm.FILRefer"+(iRefs+1)+".value=aResRefs[iRefs][7];");
       eval("frm.FILTotal"+(iRefs+1)+".value=aResRefs[iRefs][8];");
       eval("frm.TXTObserva"+(iRefs+1)+".value='Sol. Trámite = '+frm.iNumSolicitud.value+'/'+frm.iEjercicio.value+'   Bien: '+frm.hdBien.value;");
       //alert("frm.hdBien.value: "+frm.hdBien.value);
       cConceptos += (iRefs==0)?aResRefs[iRefs][1]:"\\"+aResRefs[iRefs][1];
    }
    
    frm.hdDatosAdicionales.value = frm.iEjercicio.value+","+frm.iNumSolicitud.value+","+frm.hdRutaJSSource.value+","+frm.hdRutaJSSource.value +","+cConceptos;
    
    fGuardaEmergente();
  }

   if(cId == "UsuarioXOfic" && cError==""){
     frm.SLSOficina.value  = aRes[0][8];
     frm.hdUnidadAdm.value = aRes[0][9];
     fDatosPaginaIngresos();
   }

   if(cId == "cIdUsrYPwd" && cError==""){	    
	    frm.hdUsrIngresos.value=aRes[0][2];
	    frm.hdPassUsrIngresos.value=aRes[0][3];	    
	   fTraeUsuarioOfic();
   }

}

  function fGuardaEmergente(){
    var cConds = "alwaysRaised=yes,dependent=yes,width=800,height=600,location=no,menubar=no,resizable=no,scrollbars=yes,titlebar=yes,statusbar=yes,toolbar=no";
    hWinMovimiento = window.open("about:blank", "FRMIngGeneraPago", cConds);
    if(hWinMovimiento){
      hWinMovimiento.moveTo((screen.availWidth - 800) / 2, (screen.availHeight - 600) / 2)
      //fManejaFocus(hWinMovimiento);
    }
    form = frm;    
//    alert("1 "+frm.hdUsrIngresos.value); 
//    alert("2 "+frm.hdPassUsrIngresos.value);
//    alert("3 "+frm.hdUrlIngresos.value);
//    alert("4 "+frm.SLSArea.value);
//    alert("5 "+frm.hdURLRespuesta.value);
    form.action  = frm.hdUrlIngresos.value;
    form.target  = "FRMIngGeneraPago";
    form.submit();
  }

function fTramiteOnChangeLocal(){
  if(CveTramite > 0){
    fSelectSetIndexFromValue(frm.iCveTramite,CveTramite);
  }
}

function fSelReg(aDato){
}

function fSelReg2(aDato){
  frm.FILRFC.value = aDato[10];
  frm.FILNombre.value = aDato[13];
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
//    if(aResTemp[x][0]>0)
      aResultado[aResultado.length] = true;
//    else
//      aResultado[aResultado.length] = false;
  }
  return aResultado;
}

function fGeneraFichas(){
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

  //fAbreFichaIngresos();
  //fDatosPaginaIngresos();
  fBuscaUsrYPwd();
}

function validaSeleccion(aCBoxCalcular){// ya que Ingresos solo puede generar la hoja para un concepto se limita a que solo se pueda seleccionar uno
	var noSel = 0;	 
	for(var a=0; a<aCBoxCalcular.length; a++){	  
		if(aCBoxCalcular[a])
		  noSel++;	  
	  if(noSel>1){
		  return false;
	  }	  
  }
  return true;
}

function fCalcula(){

  if (calculados){
    fAlert("El cálculo ya se realizó, si quiere hacer otro seleccione nuevamente el trámite y de clic en la lupa");
    return;
  }

  frm.iCveGrupo.value         = "";
  frm.iCveConcepto.value      = "";
  frm.iUnidades.value         = "";
  frm.lEsTarifa.value         = "";
  frm.lEsPorcentaje.value     = "";
  frm.dImporteSinAjuste.value = "";
  frm.dImporteAjustado.value  = "";
  frm.cDscConcepto.value      = "";
  frm.iRefNumerica.value      = "";
  frm.lAplicaFactorDirecto.value = "";

  frm.hdBoton.value="Calcular";
 
  aCBoxCalcular = FRMListado.fGetObjs(0);
  
  var selValid = validaSeleccion(aCBoxCalcular);
    
  if(selValid==false){
	  fAlert("Para generar la hoja de ayuda debe seleccionar únicamente un concepto.");
	  return;
  }
  
  aCValoresText = FRMListado.fGetObjs(2);

  var lElegidoCalcular = false;
  for(cont=0;cont < aCBoxCalcular.length;cont++){
    if(aCBoxCalcular[cont]){
      lElegidoCalcular = true;
      if(frm.iCveGrupo.value == "") frm.iCveGrupo.value = aResultadoTemp[cont][0]; else frm.iCveGrupo.value += ","+aResultadoTemp[cont][0];
      if(frm.iCveConcepto.value == "") frm.iCveConcepto.value = aResultadoTemp[cont][1]; else frm.iCveConcepto.value += ","+aResultadoTemp[cont][1];

      if(frm.lEsTarifa.value == "") frm.lEsTarifa.value = aResultadoTemp[cont][6]; else frm.lEsTarifa.value += ","+aResultadoTemp[cont][6];
      if(frm.lEsPorcentaje.value == "") frm.lEsPorcentaje.value = aResultadoTemp[cont][7]; else frm.lEsPorcentaje.value += ","+aResultadoTemp[cont][7];
      if(frm.dImporteSinAjuste.value == "") frm.dImporteSinAjuste.value = aResultadoTemp[cont][8]; else frm.dImporteSinAjuste.value += ","+aResultadoTemp[cont][8];
      if(frm.dImporteAjustado.value == "") frm.dImporteAjustado.value = aResultadoTemp[cont][9]; else frm.dImporteAjustado.value += ","+aResultadoTemp[cont][9];
      if(frm.iRefNumerica.value == "") frm.iRefNumerica.value = aResultadoTemp[cont][11]; else frm.iRefNumerica.value += ","+aResultadoTemp[cont][11];
      if(frm.lAplicaFactorDirecto.value == "") frm.lAplicaFactorDirecto.value = aResultadoTemp[cont][12]; else frm.lAplicaFactorDirecto.value += ","+aResultadoTemp[cont][12];

      //if (parseInt(aResultadoTemp[cont][0],10)>0){ //Si es un grupo
        if (aCValoresText[cont]=="") {
          fAlert("Debe capturar valores para los conceptos seleccionados");
          return;
        }
        if(frm.iUnidades.value == "") frm.iUnidades.value = aCValoresText[cont]; else frm.iUnidades.value += ","+aCValoresText[cont];
        if(frm.cDscConcepto.value == "") frm.cDscConcepto.value = aResultadoTemp[cont][5]; else frm.cDscConcepto.value += "¨"+aResultadoTemp[cont][5]; 
        
//      }else{ // Si es un concepto individual
//         if(frm.iUnidades.value == "") frm.iUnidades.value = "1"; else frm.iUnidades.value += ",1";
//         if(frm.cDscConcepto.value == "") frm.cDscConcepto.value = aResultadoTemp[cont][5]; else frm.cDscConcepto.value += "¨"+aResultadoTemp[cont][5];
//      }
    }
  }
  if(!lElegidoCalcular){
    fAlert("Debe de seleccionar los conceptos a calcular");
    return;
  }
  if (aCBoxCalcular.length>0)
    fEngSubmite("pgTRAImporteConceptoXTramMod.jsp","Listado2");
  else{
    fAlert("Seleccione un trámite");
    return;
  }
  FRMListado.fSetDisabled(true);
}
/*
function fAceptar(){
  alert("Aceptar");
}
*/
function fBlurChangeCampo(iPos,iCol,objCampo,cEvento){
  if (cEvento == 'onBlur')
    if (!fSoloNumerosF(objCampo.value)){
      fAlert("Favor de proporcionar un dato numérico");
      objCampo.focus();
      return;
    }
}

//function fPagoAnticipado(){
//  frm.lPagoAnticipado.value = (frm.lPagoAnticipadoBOX.checked)?1:0;
//}

function fGetAResultadoTemp(){
  return FRMListado.fGetARes();
}

function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio, iCveTramite, iCveModalidad,
                         lPagoAnticipado, lDesactivarAnticipado, lBuscarSolicitante){
  frm.iCvePersona.value     = iCvePersona
  frm.cRFC.value            = cRFC;
  frm.cRPA.value            = cRPA;
  frm.iTipoPersona.value    = iTipoPersona;
  frm.cNomRazonSocial.value = cNomRazonSocial;
  frm.cNombre.value         = cNomRazonSocial;

  if (iTipoPersona == "1")
    frm.cNomRazonSocial.value += " " + cApPaterno + " " + cApMaterno;

  frm.cApPaterno.value      = cApPaterno;
  frm.cApMaterno.value      = cApMaterno;

  frm.iCveDomicilio.value   = iCveDomicilio;
  frm.cNomRazonSocial.disabled = true;
  frm.cDomicilio.disabled = true;
  frm.cRFC.disabled = true;
  frm.cCalle.value          = cCalle;
  frm.cNumExterior.value    = cNumExterior;
  frm.cNumInterior.value    = cNumInterior;
  frm.cColonia.value        = cColonia;
  frm.cCodPostal.value      = cCodPostal;
  frm.cTelefono.value       = cTelefono;
  frm.iCvePais.value        = iCvePais;
  frm.cDscPais.value        = cDscPais;
  frm.iCveEntidadFed.value  = iCveEntidadFed;
  frm.cDscEntidadFed.value  = cDscEntidadFed;
  frm.iCveMunicipio.value   = iCveMunicipio;
  frm.cDscMunicipio.value   = cDscMunicipio;
  frm.cDomicilio.value      = cCalle+", No. "+cNumExterior+" Int. "+cNumInterior+" Col. "+cColonia+", "+cDscMunicipio+", "+cDscEntidadFed+", "+cDscPais;

  if (iCveTramite && iCveModalidad){
    CveTramite   = iCveTramite;
    CveModalidad = iCveModalidad;
    BuscarSolicitante = lBuscarSolicitante;
  }

//  if (lPagoAnticipado){
//     frm.lPagoAnticipadoBOX.checked = true;
//     fPagoAnticipado();
//     if (lDesactivarAnticipado)
//       frm.lPagoAnticipadoBOX.disabled = true;
//  }

  //Si el opener q manda a ejecutar esta pantalla le manda la clave del trámite no busca aqui al solicitante en ingresos.
  if (CveTramite == 0)
    fBusquedaSolIng();

  //Si en esta pantalla le dieron clic en la liga de búsqueda de solicitante se le asigna a lEjecutafBusquedaSolIng  true
  if (lEjecutafBusquedaSolIng)
    fBusquedaSolIng();

}

function fValoresRepLegal(){
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetParametrosConsulta(frmDestino){
  var lShowPersona     = true;
  var lShowRepLegal    = false;
  var lEditPersona     = true;
  var lEditDomPersona  = true;
  var lEditRepLegal    = false;
  var lEditDomRepLegal = false;
  if (frmDestino){
    if (frmDestino.setShowValues)
      frmDestino.setShowValues(lShowPersona, lShowRepLegal, "");
    if (frmDestino.setEditValues)
      frmDestino.setEditValues(lEditPersona, lEditDomPersona, lEditRepLegal, lEditDomRepLegal);
  }
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetClaves(frmDestino){
  if (frmDestino.setClaves)
    frmDestino.setClaves(frm.iTipoPersona.value, frm.iCvePersona.value, frm.iCveDomicilio.value, frm.iCveRepLegal.value, frm.iCveDomRepLegal.value);
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
  fObtieneDatosOpener();
}

function fDatosReferencia(paraResDatos,frmPopup){
  aResDatosRef = new Array();
  for (var i=0; i<paraResDatos.length; i++)
    aResDatosRef[aResDatosRef.length] = fCopiaArreglo(paraResDatos[i]);
  if(frmPopup)
     frmPopup.close();
}

function fAceptar(){
  if(top.opener)
    if(top.opener.fDatosReferencia)
      top.opener.fDatosReferencia(aResDatosRef,top);
}

function fBuscarSolicitante(){
  //if(BuscarSolicitante)
    fAbreSolicitante();
    lEjecutafBusquedaSolIng = true;
}

function fDatosPaginaIngresos(){
  fEngSubmite("pgObtieneDatosPaginaIngresos.jsp", "cIdPagIngresos");
}

function fTraeUsuarioOfic(){
  frm.hdFiltro.value = " GRLUsuarioXOfic.ICVEUSUARIO = " + frm.iCveUsuario.value;
  fEngSubmite("pgGRLUsuarioXOfic.jsp","UsuarioXOfic");
}

function fBuscaUsrYPwd(){
   frm.hdBoton.value  = "";
   frm.hdFiltro.value = "iCveUsuario = " + frm.iCveUsuario.value;
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  10000;
   fEngSubmite("pgSEGUsuario.jsp","cIdUsrYPwd");
}

function fObtieneDatosOpener(){
   if(top.opener){
     if(top.opener.fGetIEjercicio)
       frm.iEjercicio.value    = top.opener.fGetIEjercicio();

     if(top.opener.fGetINumSolicitud)
       frm.iNumSolicitud.value = top.opener.fGetINumSolicitud();

     if(top.opener.fGetHDBien)
       frm.hdBien.value = top.opener.fGetHDBien();

     if(top.opener.fGetUnidCalculo)
       frm.hdUnidCalculo.value = top.opener.fGetUnidCalculo();
       //frm.hdUnidCalculo.value = parseInt(top.opener.fGetUnidCalculo(),10);

     if(top.opener.fGetIdBien)
       frm.hdIdBien.value = top.opener.fGetIdBien();

     if(top.opener.fGetInicioTramite)
       lInicioDeTramite = top.opener.fGetInicioTramite();

   }
}

function fSoloNumerosF(cVCadena){
	if (!fDecimal(cVCadena))
/*    if ( fRaros(cVCadena)       || fPuntuacion(cVCadena) ||
         fSignos(cVCadena)      || fArroba(cVCadena)     ||
         fParentesis(cVCadena)  || fLetras(cVCadena)     ||
         fDiagonal(cVCadena)    ||
         fEspacio(cVCadena)     || fGuionBajo(cVCadena)  ||
         fComa(cVCadena))*/
        return false;
    else
        return true;
}

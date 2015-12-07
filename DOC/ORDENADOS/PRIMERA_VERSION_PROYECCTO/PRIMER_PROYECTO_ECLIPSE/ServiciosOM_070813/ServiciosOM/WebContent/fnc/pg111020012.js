// MetaCD=1.0
// Title: pg111020012.js
// Description:
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda<dd>Rafael Miranda Blumenkron
var cTitulo = "";
var FRMLstTram = "", FRMLstReq = "";
var frm;
var iAuxMod = 0;
var iAuxTra = 0;
var i = 0;
var iCon = 0;
var iCon2 = 0;
var iCon3 = 0;
var iCon4 = 0;
var iBan = 0;
var iAux = 0;
var aResCompleto, aResTramite = new Array(), aResRequisito = new Array(),
    aResReqBase = new Array(), aResOficUsr = new Array(), aResTramiteOriginal = new Array(),
    aResReqPNC = new Array();
aRegistros = new Array();
var ClaveReqActual = "";
var DescReqActual  = "";
var aCalifica;
var lBuscarBien = true;
var lRequisitoComp = true;
var iReqObligatorio = 0;
var FRMArbol;
var FRMLstModalidades;
var cBienBuscar = "";


// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111020012.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  JSSource("jquery.js");
  JSSource("jquery-ui.js");
  JSSource("jquery.ui.draggable.js");
  JSSource("jquery.alerts.css");
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
  

  fDefOficXUsr();
  
  ITRTD("EEtiquetaC",0,"95%","0","center");
	InicioTabla("",1,"95%","","center");
	ITRTD("ETablaST",0,"49%","","center");
	  TextoSimple("Listado de Tramites:");
	FITD("ETablaST",0,"100%","","center");
	  TextoSimple("Listado de Modalidades:");
	FTDTR();
          ITRTD("",0,"","","center","middle");
	    IFrame("IArbol","100%","280","ArbolISM.js","yes",true);
	  FITD("",0,"","","center","middle");
	    InicioTabla("",0,"95%","","center");
    	        ITR();
                  IFrame("IListadoModalidades","100%","120","Listado.js","yes",true);
                FITR("ETablaST",0,"100%","","center");
                  //ITRTD("ETablaST",0,"100%","","center");
                    TextoSimple("Requisitos Entregados");
                  //FTDTR();
                FITR();
                  IFrame("IListado12B","100%","130","Listado.js","yes",true);
                FTR();
            FinTabla();
            FTD();
            
	FinTabla();
   FTDTR();
  /** cambio modalidad */
   FinTabla();
   InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"95%","0","center","top");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"49%","40","center","middle");
          InicioTabla("ETablaInfo",0,"100%","","","",1);
            ITR();
              TDEtiCampo(false,"EEtiqueta",0," Aplicar Trámite al Bien:","cDscBien","",35,150," Seleccione o registre el bien al cual se aplicará el trámite","fMayus(this);","","fCambiaBien();",false,"EEtiquetaL",2);
            FTR();
            ITR();
              TDEtiCampo(false,"EEtiqueta",0," Unidades para Cálculo de Referencias:","dUnidCalculo","",5,15," Proporcione las unidades para cálculo de derechos","fMayus(this);","","",false,"EEtiquetaL",0);
              ITD("",0,"","","center","middle");
              BtnImgNombre("vgbuscar","lupa","fBuscaBien();","Buscar bien al cual se aplicará el trámite", false, "", "");
              FTD();
            FTR();
          FinTabla();
        FITD("",0,"","","center","middle");

        FITD("",0,"49%","","center","middle");
          IFrame("IPanel12","100%","34","Paneles.js");
        FTDTR();
      FinTabla();
    FTDTR();
    FinTabla();

    InicioTabla("",0,"100%","","","","1");

    ITRTD("",0,"","1","center");
      InicioTabla("ETablaInfo",0,"95%","","","",1);
        ITRTD("ETablaST",4,"","","center");
          TextoSimple("Datos de la Solicitud");
        FTDTR();
        ITR();
          TDEtiCampo(true,"EEtiqueta",0," Solicitante:","Persona_cNomRazonSocial","",50,50," Solicitante","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiCampo(true,"EEtiqueta",0," Rep. Legal:","RepLegal_cNomRazonSocial","",50,50," Representante Legal","fMayus(this);","","",false,"EEtiquetaL",0);
        FITR();
          TDEtiAreaTexto(false,"EEtiqueta",0,"Dom. Solicitante:",49,3,"Persona_cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",0);
          TDEtiAreaTexto(false,"EEtiqueta",0,"Dom. Rep. Legal:",49,3,"RepLegal_cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",0);
        FITR();
          TDEtiCampo(true,"EEtiqueta",0," Autorizado a Recoger:","cNomAutorizaRecoger","",50,100," Persona Autorizada a Recoger Trámite","fMayus(this);","","",false,"EEtiquetaL",0);
          TDEtiAreaTexto(false,"EEtiqueta \" rowspan=\"3",0,"Observación:",49,4,"cObsTramite","","Observaciones","fMayus(this);","","",false,false,false,"EEtiquetaL\" rowspan=\"3",0);
        FITR();
          ITD("EEtiqueta",0,"0","","center","middle");
            TextoSimple("Enviar resolución a:");
          FITD("EEtiquetaL",0,"0","","center","middle");
            Select("iCveOficina","fChangeOficinaEnvio();");
          FTD();
        FITR();
          ITD("EEtiqueta",0,"0","","center","middle");
            TextoSimple("");
          FITD("EEtiquetaL",2,"0","","center","middle");
            Text(false,"cEnviarResolucionA","",40,40,"","fMayus(this);","","",false,true);
          FTD();
        FITR();
        ITD("EEtiqueta",0,"0","","center","middle");
          TextoSimple("");
        FTD("EEtiquetaL",2,"0","","center","middle");
        TDEtiCheckBox("EEtiqueta",0,"¿Desea que se le envien notificacines vía correo electrónico?","lNotifCorreo","1",true," Enviar notificación","","","",false,true,"EEtiquetaL",3);
      FTR();
      FinTabla();
    FTDTR();
    
    // *****    Deprecated    *****
    InicioTabla("",0,"95%","","center");
      ITRTD("",0,"","0","center","top");
        IFrame("IFiltro12","0","0","Filtros.js");
      FTDTR();
      IFrame("IListado12A","0","0","Listado.js","yes",true);
    FinTabla();

    Hidden("cCveTramiteTEMP");
    Hidden("iCveTipoPersona");
    Hidden("iCveSolicitante");
    Hidden("iCveDomicilioSolicitante");
    Hidden("iCveRepLegal");
    Hidden("iCveDomicilioRepLegal");
    Hidden("iIdBien");

    if (top.fGetUsrID())
      Hidden("iCveUsuario",top.fGetUsrID());
    else
      Hidden("iCveUsuario",0);

    Hidden("ClavesTramite");
    Hidden("ClavesModalidad");
    Hidden("iCveRequisito");
    Hidden("lNotificacion");
    Hidden("RequisitoPNC");
    Hidden("Caracteristicas");
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("cCveTramite");
    Hidden("iCveTramite");
    Hidden("cCveIntTramite");
    Hidden("iCveModalidad");
    cGPD += '<div id="INTdialog" style="text-align:justify;"></div>';
    cGPD += '<div id="ALMSol" style="text-align:justify;"></div>';
  FinTabla();
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];

  FRMArbol = fBuscaFrame("IArbol");
  FRMArbol.fSetControl(self);

  FRMLstModalidades = fBuscaFrame("IListadoModalidades");
  FRMLstModalidades.fSetControl(self);
  FRMLstModalidades.fSetTitulo("Modalidades del Trámite:,");
  FRMLstModalidades.fSetCampos("1,");
  FRMLstModalidades.fSetAlinea("center,");
  FRMLstModalidades.fSetSelReg(3);


  FRMFiltro = fBuscaFrame("IFiltro12");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow(",");

  FRMLstReq = fBuscaFrame("IListado12B");
  FRMLstReq.fSetControl(self);
  FRMLstReq.fSetSelReg(2);
  FRMLstReq.fSetTitulo(",Carac.,Oblig.,Requisito,");
  FRMLstReq.fSetObjs(0,"Caja");
  FRMLstReq.fSetObjs(1,"Boton");
  FRMLstReq.fSetCampos("9,7,");
  FRMLstReq.fSetAlinea("center,center,center,left,");
  FRMLstReq.fSetDespliega("texto,texto,logico,texto,");

  $("#INTdialog").dialog({
    resizable: true,
    autoOpen: false,
    width: 600,
    height: 400});

  $("#ALMSol").dialog({
    resizable: false,
    autoOpen: false,
    width: 600});
  
  FRMPanel = fBuscaFrame("IPanel12");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  //jAlert('Alerta1 ','Alert Dialog');
  fDisabled(true, "iCveOficinaUsr,iCveDeptoUsr,cDscBien,dUnidCalculo,");
  if(frm.cCveTramite && frm.cCveTramite.disabled == false)
    frm.cCveTramite.focus();

}

function fOnSelNodoArbol(arreglo){
	FRMArbol.fSetVALUE(arreglo[1]);
	FRMArbol.fShow();
	frm.iCveTramite.value = arreglo[1];
	frm.cCveTramiteTEMP.value = arreglo[2];
	frm.cCveIntTramite.value = arreglo[6];
	cBienBuscar = arreglo[7];
	fTramiteOnChange(true);
	//fTramiteOnChangeLocal();

}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  //frm.hdFiltro.value = "TRAReqXModTramite.iCveTramite = " + frm.iCveTramite.value + " and TRAReqXModTramite.iCveModalidad = " + frm.iCveModalidad.value;
  frm.hdBoton.value = "primero";
  frm.hdFiltro.value = "TRAReqXModTramite.iCveTramite IN (select icvetramitehijo from TRADependencia where iCvetramite = " + frm.iCveTramite.value + " and icvemodalidad = "+frm.iCveModalidad.value+") " + "and TRAReqXModTramite.iCveModalidad IN (select icvemodalidadhijo from TRADependencia where iCvetramite = " + frm.iCveTramite.value + " and icvemodalidad = "+frm.iCveModalidad.value+") " + "or TRAReqXModTramite.iCveTramite = "+frm.iCveTramite.value+" and TRAReqXModTramite.iCveModalidad = "+frm.iCveModalidad.value;
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  10000000;
  //FRMLstTra.fSetDisabled(true);
  FRMLstReq.fSetDisabled(true);
  if(frm.iCveModalidad.value >= 0 && frm.iCveTramite.value >= 0)
    fEngSubmite("pgTRAReqXModTramite2.jsp","Listado");
}
// RECEPCIÓN de Datos de provenientes del Servidor

function fOficinaUsrOnChangeLocal(){
	frm.hdSelect.value = "SELECT ICVEOFICINA,CDSCOFICINA FROM grloficina  ";
	frm.hdLlave.value = "ICVEOFICINA";
	fEngSubmite("pgConsulta.jsp","cIdOficina")
}
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert(cError);
    FRMFiltro.fSetNavStatus("Record");
    return;
  }

  if (cId == "Arbol" && cError == "") {
	aResTramiteOriginal = fCopiaArreglo(aRes);
        var cAlert="";
        FRMArbol.fSetArbol(aRes);
	FRMArbol.fShow();
  }

  if(cId == "CIDTramite" && cError==""){
    aResTramiteOriginal = fCopiaArreglo(aRes);
  }
  //fResTramiteModalidadLocal(aRes,cId,cError,true);
  if(cId == "CIDModalidad" && cError==""){
	  FRMLstModalidades.fSetListado(aRes);
	  FRMLstModalidades.fSetLlave(cLlave);
	  FRMLstModalidades.fShow();
  }
  fResOficDeptoUsr(aRes,cId,cError);

  if(cId == "cIdOficina" && cError==""){
    fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value,0,1);
    fcargaArbol();
  }

  if(cId == "Listado" && cError==""){
    aResCompleto = fCopiaArregloBidim(aRes);
    frm.hdRowPag.value = iRowPag;
    fCompactaListado();

    FRMLstReq.fSetListado(aResRequisito);
    FRMLstReq.fSetLlave(cLlave);
    FRMLstReq.fShow();

    fCancelar();
    FRMFiltro.fSetNavStatus(cNavStatus);
    if(frm.iCveModalidad.value != -1 && frm.iCveTramite.value != -1)
      FRMPanel.fSetTraStatus("AddOnly");
    //FRMLstTra.fSetDisabled(true);
    FRMLstReq.fSetDisabled(true);
    //LEL20092006
    if(frm.cDscBien.value != "")
    	fDisabled(true, "iCveOficinaUsr,iCveDeptoUsr,");
       //fDisabled(true, "cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,");
    else
    	fDisabled(true, "iCveOficinaUsr,iCveDeptoUsr,cDscBien,dUnidCalculo,");
       //fDisabled(true, "cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,cDscBien,dUnidCalculo,");
    //Fin LEL20092006

  }
  if(cId=="cIdTrámitesDepend" && cError == ""){
      var msg="";
      for(i=0;i<aRes.length;i++){
	  msg += "<br> - "+aRes[i][2]+"  Modalidad "+aRes[i][3];
      }
      if(msg!="") {
	  $("#INTdialog").html("La solicitud seleccionada imprimirá los siguientes trámites:<br>"+msg);
	  $("#INTdialog").dialog('option', 'title',"Síntesis");
	  $("#INTdialog").dialog('open');
      }
  }

}
function fcargaArbol(){
  fEngSubmite("pgARBOLTRAMITES.jsp","Arbol");
}

function fGeneraCalifica(){
  aCalifica = new Array();
  for (var i=0; i<aResRequisito.length; i++){
    aCalifica[i] = new Array();
    aCalifica[i][0] = aResRequisito[i][2];
    aCalifica[i][1] = new Array();
  }
}

function fCompletaRequisitos(){
  lEncontrado = false;
  aResRequisito = new Array();
  for (var i = 0; i < aResReqBase.length; i++)
    aResRequisito[aResRequisito.length] = fCopiaArreglo(aResReqBase[i]);
  FRMLstReq.fSetListado(aResRequisito);
  FRMLstReq.fShow();
}

function fCompactaListado(){
  var lEncontrado = false;
  aResTramite = new Array();
  aResReqBase = new Array();
  for (var i = 0; i < aResCompleto.length; i++){
    lEncontrado = false;
    for (var x = 0; x < aResTramite.length; x++){
      if (aResTramite[x][0] == aResCompleto[i][0] && aResTramite[x][1] == aResCompleto[i][1]){
        lEncontrado = true;
        break;
      }
    }
    if (!lEncontrado)
      if (!(aResCompleto[i][0] == frm.iCveTramite.value && aResCompleto[i][1] == frm.iCveModalidad.value))
        aResTramite[aResTramite.length] = fCopiaArreglo(aResCompleto[i]);
      else
        aResReqBase[aResReqBase.length] = fCopiaArreglo(aResCompleto[i]);
  }

  aResRequisito = new Array();
  for (var i = 0; i < aResReqBase.length; i++)
    aResRequisito[aResRequisito.length] = fCopiaArreglo(aResReqBase[i]);
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
  cMsg = "";
  lRequisitoComp= true;
  cMsg = fValElements();
  if(!frm.iCveOficinaUsr.value>0 || !frm.iCveDeptoUsr.value>0)
    cMsg += "\n-Debe de Seleccionar una Oficina y un Departamento valido";
  if (frm.iCveSolicitante.value == "")
    cMsg += "\n-Debe seleccionar al solicitante del trámite.";
  if (frm.iCveDomicilioSolicitante.value == "")
    cMsg += "\n-Debe seleccionar el domicilio del solicitante del trámite.";
  if (frm.iCveTipoPersona.value != "1"){
    if (frm.iCveRepLegal.value == "")
      cMsg += "\n-Debe seleccionar al representante legal en caso de personas NO físicas.";
    if (frm.iCveDomicilioRepLegal.value == "")
      cMsg += "\n-Debe seleccionar el domicilio del representante legal en caso de personas NO físicas.";
  }
  if(frm.cDscBien.value != "" && fRaros2(frm.cDscBien.value)) cMsg += "\n-El campo Aplicar Trámite al Bien no acepta comillas.";
  if(frm.dUnidCalculo.value != "" && !fDecimal(frm.dUnidCalculo.value)) cMsg += "\n-El campo Unidades para Cálculo solo acepta números decimales.";

  var lPrincipalSel = false;
  var lRequisitoSel = false;

  aResReqPNC = new Array();
  for (var i=0; i<aCBoxReq.length; i++){
    if (aCBoxReq[i]){
      lRequisitoSel = true;
      for (var j=0; j<aResReqBase.length; j++){
        if (aResRequisito[i][2] == aResReqBase[j][2])
          lPrincipalSel = true;
      }
    }else{
      if(aResRequisito[i][9] == 1){
        lRequisitoComp = false;
        aResReqPNC[aResReqPNC.length] = aResRequisito[i];
      }
    }
  }
  if (!lRequisitoSel)
    cMsg += "\n-Debe seleccionar como entregado al menos un requisito.";
  if (lRequisitoSel && !lPrincipalSel)
    cMsg += "\n-Debe seleccionar como entregado al menos un requisito del trámite principal.";

  if(cMsg != ""){
    fAlert(cMsg);
    return false;
  }
  cMsg = "";
  if(cMsg != "")
      return confirm(cAlertMsgGen + "\n" + cMsg + "\n\n¿Desea continuar con estos datos?");
  return true;
}

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
    if(cBienBuscar == "EMBARCACION" && !(parseInt("frm.iIdBien.value",10)>0)){
      if (frm.iCveTramite.value > 0 && frm.iCveModalidad.value > 0){
        lRequisitoComp = true;
        fGeneraCalifica();
        FRMPanel.fSetTraStatus("UpdateBegin");
        fBlanked("Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,cDscBien,dUnidCalculo,");
        fDisabled(true);
        //fDisabled(false,"cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,cDscBien,dUnidCalculo,","--");
        fDisabled(false,"iCveOficinaUsr,iCveDeptoUsr,Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,cDscBien,dUnidCalculo,","--");
        //FRMLstTra.fSetDisabled(false);
        FRMLstReq.fSetDisabled(false);
        lBuscarBien = false;
        fAsignaFuncionBuscarBien();
        frm.hdSelect.value = "SELECT t.CDSCTRAMITE,m.CDSCMODALIDAD,td.CDSCTRAMITE,md.CDSCMODALIDAD " +
                             "FROM TRADEPENDENCIA D " +
                              "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=D.ICVETRAMITE " +
                              "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=D.ICVEMODALIDAD " +
                              "JOIN TRACATTRAMITE TD ON TD.ICVETRAMITE=D.ICVETRAMITEHIJO " +
                              "JOIN TRAMODALIDAD MD ON MD.ICVEMODALIDAD=D.ICVEMODALIDADHIJO " +
                              "WHERE D.ICVETRAMITE=" +frm.iCveTramite.value+
                              " AND D.ICVEMODALIDAD="+frm.iCveModalidad.value;
        frm.hdLlave.value ="";
        fEngSubmite("pgConsulta.jsp","cIdTrámitesDepend")
      }else{
        fAlert("\n - Debe seleccionar un trámite y una modalidad");
        FRMPanel.fSetTraStatus(",")
        //FRMLstTra.fSetListado(new Array());
        //FRMLstTra.fShow();
        FRMLstReq.fSetListado(new Array());
        FRMLstReq.fShow();
      }
    }else {
	 if (frm.iCveTramite.value > 0 && frm.iCveModalidad.value > 0){
	    lRequisitoComp = true;
	    fGeneraCalifica();
	    FRMPanel.fSetTraStatus("UpdateBegin");
	    fBlanked("Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,cDscBien,dUnidCalculo,");
	    fDisabled(true);
	    fDisabled(false,"cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,Persona_cNomRazonSocial,RepLegal_cNomRazonSocial,Persona_cDscDomicilio,RepLegal_cDscDomicilio,cDscBien,dUnidCalculo,","--");
	    //FRMLstTra.fSetDisabled(false);
	    FRMLstReq.fSetDisabled(false);
	    lBuscarBien = false;
	    fAsignaFuncionBuscarBien();
	  }
    }
}

function fReqTramMod(iTramite, iModalidad){
  var aReqTramMod = new Array();
  for (var i=0; i<aResCompleto.length; i++){
    if (aResCompleto[i][0] == iTramite && aResCompleto[i][1] == iModalidad)
      aReqTramMod[aReqTramMod.length] = aResCompleto[i];
  }
  return aReqTramMod;
}

function fReqSelected(iRequisito, aResultado){
  for (var i=0; i<aResRequisito.length; i++){
    if (aResRequisito[i][2] == iRequisito)
      return aResultado[i];
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  if(!confirm(cAlertMsgGen + "\n - Ha elegido guardar el trámite con los datos de pantalla.\n¿Está seguro de continuar?"))
    return;
  //agregar PNC con Propiedades: VentanillaProductoRecepcion, VentanillaCausaRequisitoIncompleto, VentanillaCausaRequisitoNoConforme
  frm.hdFiltro.value = "";

  //aCBoxTra = FRMLstTra.fGetObjs(0);
  aCBoxReq = FRMLstReq.fGetObjs(0);

  var conCorreo = true;
  if(frm.lNotifCorreo.checked){
  	conCorreo = window.parent.validaCorreoElectronico();
  }

  if (fValidaTodo()&& conCorreo){
    var cTramites    = frm.iCveTramite.value;
    var cModalidades = frm.iCveModalidad.value;
    var cRequisitos  = "";

    var aReqTemp = fReqTramMod(frm.iCveTramite.value, frm.iCveModalidad.value);
    for (var i=0; i<aReqTemp.length; i++){
      var lSelected = fReqSelected(aReqTemp[i][2], aCBoxReq);
      cRequisitos += (cRequisitos=="")?"":",";
      cRequisitos += aReqTemp[i][2] + "=" + lSelected + "/" + aReqTemp[i][9];
    }
    var cCalifica = "", cCalifTemp = "";
    for (var k=0; k<aCalifica.length; k++){
      aReqTemp = aCalifica[k][1];
      cCalifTemp = "";
      for (var m=0; m<aReqTemp.length; m++){
        cCalifTemp += (m==0)?"":",";
        cCalifTemp += aReqTemp[m][0] + "=" + aReqTemp[m][1];
      }
      if(cCalifTemp != ""){
        cCalifica += (cCalifica == "")?"":":";
        cCalifica += aCalifica[k][0] + "/";
        cCalifica += cCalifTemp;
      }
    }

    var cReqPNC = "";
    for(var n=0; n<aResReqPNC.length; n++){
      cReqPNC += (cReqPNC == "")?"":",";
      cReqPNC += aResReqPNC[n][2] + "";
    }

    frm.ClavesTramite.value   = cTramites;
    frm.ClavesModalidad.value = cModalidades;
    frm.iCveRequisito.value   = cRequisitos;
    frm.Caracteristicas.value = cCalifica;
    frm.RequisitoPNC.value    = cReqPNC;
    if(frm.lNotifCorreo.checked){
    	frm.lNotificacion.value = 1;
    }else{
    	frm.lNotificacion.value = 0;
    }

    if(fEngSubmite("pgTRARegSolicitud.jsp","")== true){
      FRMPanel.fSetTraStatus("AddOnly");
      fDisabled(true,"cDscBien,dUnidCalculo,");
      frm.cDscBien.disabled = false;
      frm.dUnidCalculo.disabled = false;
      frm.iIdBien.value = "";
      frm.cDscBien.value = "";
      frm.dUnidCalculo.value = "";
      //FRMLstTra.fSetDisabled(false);
      FRMLstReq.fSetDisabled(false);
      frm.hdBoton.value = "primero";
      fNavega();
    }
  }
  lBuscarBien = true;
  fAsignaFuncionBuscarBien();

}

// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
}


// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
}

// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
  FRMFiltro.fSetNavStatus("ReposRecord");
  FRMPanel.fSetTraStatus("AddOnly");
  fDisabled(false);
  //fDisabled(true,"cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,cDscBien,dUnidCalculo,");
  fDisabled(true,"iCveOficinaUsr,iCveDeptoUsr,cDscBien,dUnidCalculo,");
  //FRMLstTra.fSetDisabled(true);
  FRMLstReq.fSetDisabled(true);
  //FRMLstTra.focus();
  FRMLstReq.focus();
  lBuscarBien = true;
  var aTemp = new Array();
  for(var i=0; i<aResRequisito.length; i++)
    aTemp[aTemp.length] = false;
  FRMLstReq.fSetDefaultValues(0, 0, aTemp);
  fAsignaFuncionBuscarBien();
}

// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
}

function fSelReg(aDato,iCol,lChecked){
  if (iCol == 0)
    fCompletaRequisitos();
}

function fSelReg2(aDato,iCol,lChecked){
  iReqObligatorio = aDato[9];
  if(iCol==1){
    ClaveReqActual = aDato[2];
    DescReqActual  = aDato[3];
    fAbreValidaRequisito();
  }
}
function fSelReg3(aDato, iCol, lChecked) {
	//fSelectSetIndexFromValue(frm.iCveModalidad, aDato[0]);
	frm.iCveModalidad.value=aDato[0];
	fModalidadOnChange();
}
function fEnviaReqCalificar(targetForm){
  if (targetForm){
    if (targetForm.iCveRequisito && ClaveReqActual != ""){
      targetForm.iCveRequisito.value = ClaveReqActual;
      targetForm.cDscBreve.value     = DescReqActual;
      targetForm.lEnRecepcion.value = 1;
    }
  }
}

function fValoresActuales(){
  for (var i=0; i<aCalifica.length; i++){
    if (aCalifica[i][0] == ClaveReqActual)
      return aCalifica[i][1];
  }
}

function fRecibeCalifica(iReq, aCalif){
  var aDatos = new Array();
  for (var i=0; i<aCalif.length; i++){
    aDatos[i] = new Array();
    aDatos[i][0] = aCalif[i][0];
    aDatos[i][1] = aCalif[i][1];
  }

  for (var i=0; i<aCalifica.length; i++){
    if (aCalifica[i][0] == iReq)
      aCalifica[i][1] = aDatos;
  }
}

function fImprimir(){
  self.focus();
  window.print();
}

function fAsignaFuncionBuscarBien(){
  var objLiga = fEncuentraLiga(document, "BuscarBien");
  var cFuncion = "fNoDefinidaParaTramite";
  if(objLiga){
    if (frm.iCveTramite && frm.iCveTramite.value && frm.iCveTramite.value !="" && frm.iCveTramite.value >= 0){
      for (var i=0; i<aResTramiteOriginal.length; i++){
        if (aResTramiteOriginal[i][0] == frm.iCveTramite.value){
          cFuncion = aResTramiteOriginal[i][8];
          break;
        }
      }
    }
    if(cFuncion=="")
      cFuncion = "fNoDefinidaParaTramite";
    if(!lBuscarBien || frm.iCveTramite.value <= 0)
      objLiga.href = "JavaScript:alert('No es posible buscar bien en modo de edición o sin seleccionar trámite');"
    else
      objLiga.href = "JavaScript:"+cFuncion + "();";
  }
}

function fNoDefinidaParaTramite(){
  frm.iIdBien.value = "";
  frm.cDscBien.value = "";
  frm.dUnidCalculo.value = "";
  alert("No existe búsqueda de bienes para este trámite");
}

function fTramiteOnChangeLocal(){
  frm.iIdBien.value = "";
  frm.cDscBien.value = "";
  frm.dUnidCalculo.value = "";
  fAsignaFuncionBuscarBien();
  if (frm.iCveTramite && frm.iCveTramite.value >= 0){
    FRMLstReq.fSetListado(new Array());
    FRMLstReq.fShow();
  }else{
	  FRMPanel.fSetTraStatus(",");
  }
}

function fModalidadOnChangeLocal() {

  if (frm.iCveTramite.value >= 0 && frm.iCveModalidad.value >= 0){
    fNavega();

  }else{

    FRMPanel.fSetTraStatus(",")
    //FRMLstTra.fSetListado(new Array());
    //FRMLstTra.fShow();
    FRMLstReq.fSetListado(new Array());
    FRMLstReq.fShow();
  }
  frm.cCveTramite.value = frm.cCveTramiteTEMP.value
}

function fValoresDisponibles(theContFrm){
  frm.Persona_cNomRazonSocial.value  = theContFrm.cNomRazonSocial.value;
  frm.RepLegal_cNomRazonSocial.value = theContFrm.cNomRazonSocial2.value;
  frm.Persona_cDscDomicilio.value    = theContFrm.cDscDomicilio.value;
  frm.RepLegal_cDscDomicilio.value   = theContFrm.cDscDomicilio2.value;

  frm.iCveTipoPersona.value          = theContFrm.iTipoPersona.value;
  frm.iCveSolicitante.value          = theContFrm.iCvePersona.value;
  frm.iCveDomicilioSolicitante.value = theContFrm.iCveDomicilio.value;
  frm.iCveRepLegal.value             = theContFrm.iCveRepLegal.value;
  frm.iCveDomicilioRepLegal.value    = theContFrm.iCveDomRepLegal.value;
}

function fChangeOficinaEnvio(){
  if (frm.iCveOficina.value > 0)
    frm.cEnviarResolucionA.value = frm.iCveOficina.options[frm.iCveOficina.selectedIndex].text;
  else
    frm.cEnviarResolucionA.value = "";
}

function fDatosBien(idBien, cDscBien, cUnidCalculo){
  frm.iIdBien.value = idBien;
  frm.cDscBien.value = cDscBien;
  frm.dUnidCalculo.value = cUnidCalculo;
  if(frm.cDscBien.value != "" && frm.dUnidCalculo.value != "")
     //fDisabled(true, "cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,");
	  fDisabled(true, "iCveOficinaUsr,iCveDeptoUsr,");
  else
     //fDisabled(true, "cCveTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,cDscBien,dUnidCalculo,");
	  fDisabled(true, "iCveOficinaUsr,iCveDeptoUsr,cDscBien,dUnidCalculo,");
  fMayus(frm.cDscBien);
  fMayus(frm.dUnidCalculo);
}

function fCambiaBien(){
  frm.iIdBien.value = "0";
}

function fGetCveSolicitante(){
  if(frm.iCveSolicitante){
    if(frm.iCveSolicitante.value != "")
    return frm.iCveSolicitante.value;
  }else
    return "";
}

function fGetCveTramite(){
  if(frm.iCveTramite){
    if(frm.iCveTramite.value > 0)
    return frm.iCveTramite.value;
  }else
    return "";
}

function fGetCveModalidad(){
  if(frm.iCveTramite && frm.iCveModalidad){
    if(frm.iCveTramite.value > 0 && frm.iCveModalidad.value > 0)
    return frm.iCveModalidad.value;
  }else
    return "";
}

function fGetPermiteBusqueda(){
  return false;
}

function fRaros2(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"`")  ||
       fEncCaract(cVCadena.toUpperCase(),"´")  ||
       fEncCaract(cVCadena.toUpperCase(),"º")  ||
       fEncCaract(cVCadena.toUpperCase(),"'"))
       return true;
   else
      return false;
}

function fBuscaBien(){
	if(cBienBuscar == "EMBARCACION"){
		fAbreBuscaEmbarcacion();
	}
	else if(cBienBuscar == "CONCESION"){
		fAbreBuscaConcesion();
	}
	else fAlert("No existe búsqueda de bienes para este trámite");
}
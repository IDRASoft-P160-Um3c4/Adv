// MetaCD=1.0
// Title: pg111010011A.js
// Description: JS "Catálogo" de la entidad GRLPersona
// Company: Tecnología InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm, FRMObj;
var lEditPersona = true;
var lEditando = false;
var aDato;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010011A.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","100%","","");
    ITRTD("EEtiquetaC",0,"","","","");
      InicioTabla("ETablaInfo",0,"0","","center","",1);
        ITRTD("ETablaST",12,"","","center");
          cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"DATOS GENERALES":cTitulo;
          TextoSimple(cTitulo);
        FTDTR();
          ITRTD("EEtiqueta",0,"","","","");
            TextoSimple("Tipo de Persona: ");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",1,false,"","","",'onClick="fOnChangeTipoPersona();"');
          FITD("EEtiquetaL",0);
            Etiqueta("Fisica","EEtiquetaL","Física");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",2,true,"","","",'onClick="fOnChangeTipoPersona();"');
          FITD("EEtiquetaL",0);
            Etiqueta("Moral","EEtiquetaL","Moral");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",3,false,"","","",'onClick="fOnChangeTipoPersona();"');
          FITD("EEtiquetaL",0);
            Etiqueta("Federal","EEtiquetaL","Federal");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",4,false,"","","",'onClick="fOnChangeTipoPersona();"');
          FITD("EEtiquetaL",3);
            Etiqueta("EstataL","EEtiquetaL","Estatal");
        FTDTR();
        ITR();
          TDEtiCampo(false,"EEtiqueta",0,"Clave:","iCvePersona","",14,13,"Clave de Persona","fMayus(this);","","",false,"EEtiquetaL",5);
          TDEtiCampo(false,"EEtiqueta",3,"Correo Electrónico:","cCorreoE","",33,50,"Correo Electrónico","","","",false,"EEtiquetaL",4);
        FITR();
          TDEtiCampo(true,"EEtiqueta",0,"R.F.C.:","cRFC","",14,13," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",7);
          TDEtiCampo(false,"EEtiqueta",0,"R.U.P.A.:","cRPA","",16,15," R.P.A.","fMayus(this);","","",false,"EEtiquetaL",4);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0,"C.U.R.P.:","cCURP","",18,18," C.U.R.P.","fMayus(this);","","",false,"EEtiquetaL",7);
          TDEtiCampo(false,"EEtiqueta",0,"Pseudónimo:","cPseudonimoEmp","",33,30,"Pseudónimo","fMayus(this);","","",false,"EEtiquetaL",4);
        FITR();
          TDEtiCampo(true,"EEtiqueta",0,"Nombre o Razón Social:","cNomRazonSocial","",91,80,"Nombre o Razón Social","fMayus(this);","","",false,"EEtiquetaL",11);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0,"Ap. Paterno:","cApPaterno","",33,30,"Apellido Paterno","fMayus(this);","","",false,"EEtiquetaL",7);
          TDEtiCampo(false,"EEtiqueta",0,"Ap. Materno:","cApMaterno","",33,30,"Apellido Materno","fMayus(this);","","",false,"EEtiquetaL",3);
    ITR();
      TDEtiAreaTexto(false,"EEtiqueta",0,"Domicilio Persona:",94,2,"cPersona_Domicilio","","Domicilio de la Persona","fMayus(this);","","",false,false,false,"EEtiquetaL",11);
    FITR();
      TDEtiCampo(false,"EEtiqueta",0," Nombre Rep. Legal:","cRepLegal_Nombre","",95,95," Nombre del Representante Legal","fMayus(this);","","",false,"EEtiquetaL",11);
    ITR();
      TDEtiAreaTexto(false,"EEtiqueta",0,"Domicilio Rep. Legal:",94,2,"cRepLegal_Domicilio","","Domicilio del Representante Legal","fMayus(this);","","",false,false,false,"EEtiquetaL",11);
    FTR();
      FinTabla();
    FTDTR();
    ITRTD("",0,"","40","center","bottom");
      IFrame("IPanel1A","95%","34","Paneles.js");
    FTDTR();
  FinTabla();
  Hidden("iTipoPersona");
  Hidden("hdFiltro11");
  Hidden("hdFiltro11A");
  Hidden("hdFiltro12");
  Hidden("hdFiltro13");
  Hidden("hdFiltro14");
  Hidden("cNombreFRM");
  Hidden("hdSelect");
  Hidden("hdLlave");
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel1A");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(true);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado1A");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  //fNavega();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  if(frm.iCvePersona.value==""){
    if (lEditPersona)
      FRMPanel.fSetTraStatus("AddOnly");
    return;
  }
  cCadTrans = (lEditPersona)?"UpdateComplete":"Disabled";
  FRMPanel.fSetTraStatus(cCadTrans);
}
var aResDomPersona;
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert (cError);
  }

    //refresca el listado del contenedor despues de guardar el registro
  if(cId=="RefreshPersona" && cError == ""){
    var cRfc = "";
    var iTipo = fGetRadioValue(frm.iTipo);
    cRfc = frm.cRFC.value;
    frm.hdBoton.value = "Primero";
    window.parent.fSetLlave(cLlave,true); //obtiene la llave primaria
    FRMPanel.fSetTraStatus("UpdateComplete");
    window.parent.fHabilita(false);
    fDisabled(true);
    lEditando = false;
    if(window.parent.fSetDatosBuscar){
      window.parent.fSetDatosBuscar(iTipo, cRfc, "", "", "", "", "");
    }
  }
  if(cId=="cIdCURP" && cError==""){
    if(aRes.length>0){
      if(confirm("El C.U.R.P. proporcionado ya existe en la base de datos.\n¿Desea usted Continuar con el Guardado?"))fGuardar2();
    }
    else{fGuardar2();}
  }
 }
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
  lEditando = true;
  FRMPanel.fSetTraStatus("UpdateBegin");
  fBlanked();
  fDisabled(false,"iCvePersona,","--");
  window.parent.fDeshabilita();
  fSetTipoPersona(window.parent.fGetTipoPersona());
  fOnChangeTipoPersona();
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
  frm.cNombreFRM.value = window.parent.frm.cNombreFRM.value;
  if(frm.cRFC.value!=""){
    frm.hdSelect.value = "SELECT * FROM grlpersona where crfc='"+frm.cRFC.value+"'";
    if(frm.cCURP.value!="")frm.hdSelect.value=frm.hdSelect.value+" or CCURP='"+frm.cCURP.value+"' ";
    frm.hdLlave.value = "";
    fEngSubmite("pgConsulta.jsp","cIdCURP");
  }else{

	  if(window.parent.fGetlRFCObligatorio()){
		  fAlert("El RFC es un dato requerido.");
	  }else{
		  frm.hdSelect.value = "SELECT * FROM grlpersona where crfc='"+frm.cRFC.value+"'";
		  if(frm.cCURP.value!="")frm.hdSelect.value=frm.hdSelect.value+" or CCURP='"+frm.cCURP.value+"' ";
		  frm.hdLlave.value = "";
		  fEngSubmite("pgConsulta.jsp","cIdCURP");  
	  }
	 
  }
}
function fGuardar2(){
  frm.iTipoPersona.value = fGetRadioValue(frm.iTipo);
  if(fValidaTodo()){
    frm.hdOrden.value = "";
    cRfc = frm.cRFC.value;
    frm.hdBoton.value = "Guardar";
    fEngSubmite("pgGRLPersonaA1.jsp","RefreshPersona");
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
  frm.iTipoPersona.value = fGetRadioValue(frm.iTipo);
  if(fValidaTodo()){
    frm.hdOrden.value = "";
    fEngSubmite("pgGRLPersonaA1.jsp","ListadoPer");
    FRMPanel.fSetTraStatus("UpdateComplete");
    lEditando = false;
    fDisabled(true);
    window.parent.fHabilita(false);
    window.parent.fRefreshData();
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
  if(frm.iCvePersona.value > 0){
      FRMPanel.fSetTraStatus("UpdateBegin");
      fDisabled(false,"iCvePersona,");
      lEditando = true;
      window.parent.fDeshabilita();
      fOnChangeTipoPersona();
  }
  else fAlert("No es posible modificar a esta persona.")
}

// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
  lEditando = false;
  FRMPanel.fSetTraStatus("UpdateComplete");
  window.parent.fHabilita();
  fDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
  var lLimpiar = false;
  if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
    frm.hdOrden.value = "";
    if(frm.iCvePersona.value == window.parent.PerOriginal)
      lLimpiar = true;
    fEngSubmite("pgGRLPersonaA1.jsp","ListadoPer");
    window.parent.fResultado(new Array(),"ListadoPer","");
    if(lLimpiar){
      if (top.opener.fValoresPersona)
        top.opener.fValoresPersona("","","","","","","","","","","","","","",
                                   "","","","","","","","","","","","","","");
      if (top.opener.fValoresRepLegal)
        top.opener.fValoresRepLegal("","","","","","","","","","","","","","",
                                    "","","","","","","","","","","","","","");
    }
  }
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(){
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
  cMsg = "";
  cMsg = fValElements("cCorreoE,cRFC,cNomRazonSocial,");
  var iTipo = fGetRadioValue(frm.iTipo);
  if (iTipo > 2)
    iTipo = 2;
  
  if(window.parent.fGetlRFCObligatorio()){
	  if (frm.cRFC.value == "")
	    cMsg += '\n-Favor de verificar el RFC, es campo obligatorio y no ha registrado valor alguno';
	  else if (!fValRFC (frm.cRFC.value,parseInt(""+iTipo,10)))
	    cMsg += '\n-Favor de verificar el RFC, no cumple con las reglas para el tipo de persona';		  
  }  
  
  if (!fEMail(frm.cCorreoE.value))
    cMsg += '\n-Favor de verificar el correo electrónico, tiene datos incorrectos';

  if(frm.iTipoPersona.value == 1){

	  if(window.parent.fGetlRFCObligatorio()){
	    if(((frm.cRFC.value == "") && (frm.cRPA.value == "")) || ((frm.cNomRazonSocial.value == "") || (!fSoloAlfanumericosNomRazonSoc(frm.cNomRazonSocial.value))) || ((frm.cApPaterno.value == "") && (frm.cApMaterno.value == "")))
	        cMsg += "\nFavor de proporcionar RFC ó RPA, y Nombre con un Apellido sin caracteres especiales";
	  }else{
	    if( ((frm.cNomRazonSocial.value == "") || (!fSoloAlfanumericosNomRazonSoc(frm.cNomRazonSocial.value))) || ((frm.cApPaterno.value == "") && (frm.cApMaterno.value == "")))
	        cMsg += "\nFavor de proporcionar Nombre con un Apellido sin caracteres especiales";
	  }

  }else{
	  
	  if(window.parent.fGetlRFCObligatorio()){
		    if(((frm.cRFC.value == "") && (frm.cRPA.value == "")) || ((frm.cNomRazonSocial.value == "")||(!fSoloAlfanumericosNomRazonSoc(frm.cNomRazonSocial.value))))
		        cMsg += "\nFavor de proporcionar RFC ó RPA, y Nombre o Pseudónimo sin caracteres especiales";		  
	  }else{
		    if( ((frm.cNomRazonSocial.value == "")||(!fSoloAlfanumericosNomRazonSoc(frm.cNomRazonSocial.value))) )
		        cMsg += "\nFavor de proporcionar Nombre o Pseudónimo sin caracteres especiales";
	  }

    
  }

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

function fGetCvePersona(){
  return frm.iCvePersona.value;
}

function fSetCvePersona(persona, locFRMObj, locADato){
  frm.iCvePersona.value= persona;
  aDato = locADato;
  FRMObj = locFRMObj;
  fNavega();
}

function fSetRFC(rfc){
  frm.cRFC.value= rfc;
}

function fSetRPA(rpa){
   frm.cRPA.value=rpa;
}

function fSetTipoPersona(cTipoPersona){
  frm.iTipoPersona.value=cTipoPersona;
  fSetRadioValue(frm.iTipo, cTipoPersona);
}

function fSetNombre(nombre){
  frm.cNomRazonSocial.value =nombre;
}

function fSetPaterno(paterno) {
  frm.cApPaterno.value = paterno;
}

function fSetMaterno(materno){
  frm.cApMaterno.value =  materno;
}

function fSetPseudo(pseudo){
  frm.cPseudonimoEmp.value =  pseudo;
}

function fSetCorreo(correo){
  frm.cCorreoE.value = correo;
}

function setEditValues(EditPersona){
  lEditPersona = EditPersona;
  if (!lEditPersona)
    FRMPanel.fSetTraStatus("Disabled");
  fNavega();
}

function fOnChangeTipoPersona(){
  if (window.parent)
    if (window.parent.cSoloTipoPersona != ""){
      fSetRadioValue(frm.iTipo, window.parent.cSoloTipoPersona);
    }else
      frm.iTipoPersona.value = fGetRadioValue(frm.iTipo);
  if(frm.iTipoPersona.value!=1){
    fDisabled(false);
    fDisabled(true,"iTipo,cRFC,cCURP,cRPA,cNomRazonSocial,cPseudonimoEmp,cCorreoE,");
  }else{
    fDisabled(false);
    fDisabled(true,"iTipo,cRFC,cCURP,cRPA,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,");
  }
}

function fReporte(){
  if(window.parent.frm.Persona_iTipoPersona.value == 1){
    if(window.parent.frm.Persona_iCvePersona.value != ""){
      cClavesModulo = "2,";
      cNumerosRep = "10,";
      cFiltrosRep = window.parent.frm.Persona_iCvePersona.value + cSeparadorRep;
    }
  }else{
    if(window.parent.frm.Persona_iCvePersona.value != ""){
      cClavesModulo = "2,";
      cNumerosRep = "9,";
      cFiltrosRep = window.parent.frm.Persona_iCvePersona.value + cSeparadorRep;
    }
    if(window.parent.frm.RepLegal_iCvePersona.value != ""){
      cClavesModulo += "2,";
      cNumerosRep += "10,";
      cFiltrosRep += window.parent.frm.RepLegal_iCvePersona.value + cSeparadorRep;
    }
  }
  if(window.parent.frm.Persona_iCvePersona.value != "")
    fReportes();
  else
    fAlert('Debe elegir una persona con o sin representantes legales para poder ejecutar esta acción');
}

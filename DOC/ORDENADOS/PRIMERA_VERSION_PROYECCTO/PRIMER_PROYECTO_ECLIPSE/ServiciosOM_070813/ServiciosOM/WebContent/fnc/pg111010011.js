// MetaCD=1.0
var cTitulo = "";
var FRMListado = "";
var frm;
var iPagAnt = 1;
var iPagAct = 1;
var desdeOtra = false;
var lShowPersona     = true;
var lShowRepLegal    = true;
var lEditPersona     = true;
var lEditDomPersona  = true;
var lEditRepLegal    = true;
var lEditDomRepLegal = true;
var cSoloTipoPersona = "";;
var FRMObj,FRMObj1, FRMObj2, FRMObj3, FRMObj4, FRMFiltro;
var lEditando = false;
var PerOriginal, DomOriginal, RepOriginal, DomRepOriginal;
var iTipoPersonaSeleccionado = "";
var lPermiteBusqueda = true;
var lCargado = false;
var lRFCObligatorio = true;

function setShowValues(ShowPersona, ShowRepLegal, SoloTipoPersona){
  lShowPersona     = ShowPersona;
  lShowRepLegal    = ShowRepLegal;
  if (SoloTipoPersona != "")
    cSoloTipoPersona = SoloTipoPersona;
}

function setEditValues(EditPersona, EditDomPersona, EditRepLegal, EditDomRepLegal){
  lEditPersona     = EditPersona;
  lEditDomPersona  = EditDomPersona;
  lEditRepLegal    = EditRepLegal;
  lEditDomRepLegal = EditDomRepLegal;
}

function setClaves(TipoPersona, CvePersona, DomPersona, CveRepLegal, DomRepLegal){
  FRMObj = document.forms[0];
  FRMObj.Persona_iTipoPersona.value = TipoPersona;
  PerOriginal = CvePersona;
  FRMObj.Persona_iCvePersona.value = CvePersona;
  DomOriginal = DomPersona;
  FRMObj.Persona_iCveDomicilio.value = DomPersona;
  RepOriginal = CveRepLegal;
  FRMObj.RepLegal_iCvePersona.value = CveRepLegal;
  DomRepOriginal = DomRepLegal;
  FRMObj.RepLegal_iCveDomicilio.value = DomRepLegal;
  fRefreshData(TipoPersona, CvePersona, DomPersona, CveRepLegal, DomRepLegal);
}

valores = new Array();
var cLlavePA1;

fWrite(JSSource("Carpetas.js"));

function fBefLoad(){
  desdeOtra=false;
  cPaginaWebJS = "pg111010011.js";
//  fPermisoEjecutar();
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
  fSetWindowTitle();
}
function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  if(top.opener){
    desdeOtra = true;
    if(top.opener.fGetParametrosConsulta)
      top.opener.fGetParametrosConsulta(this);

    if(top.opener.fGetlPermiteBusqueda)
      lPermiteBusqueda = top.opener.fGetlPermiteBusqueda();
    
    if(top.opener.fRFCObligatorio)
      lRFCObligatorio = top.opener.fRFCObligatorio();
    else
      lRFCObligatorio = true;
  }
  JSSource("pais.js");
  JSSource("estados.js");
  InicioTabla("",0,"100%","100%","","");
    ITRTD("ETablaST","","","","center");
     // fTituloEmergente(cTitulo);
     fTituloEmergente(cTitulo, false,"","fRestablece");
    FTDTR();
    ITRTD("EEtiquetaC",0,"","","","");
      InicioTabla("ETablaInfo",0,"0","","center","",1);
          ITRTD("EEtiqueta",0,"","","","");
            TextoSimple("Tipo de Persona: ");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",1,false,"","","",'onClick="fOnChange(false);"');
          FITD("EEtiquetaL",0);
            Etiqueta("Fisica","EEtiquetaL","Física");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",2,true,"","","",'onClick="fOnChange(false);"');
          FITD("EEtiquetaL",0);
            Etiqueta("Moral","EEtiquetaL","Moral");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",3,false,"","","",'onClick="fOnChange(false);"');
          FITD("EEtiquetaL",0);
            Etiqueta("Federal","EEtiquetaL","Federal");
          FITD("EEtiqueta",0);
            Radio(true,"iTipo",4,false,"","","",'onClick="fOnChange(false);"');
          FITD("EEtiquetaL",0);
            Etiqueta("EstataL","EEtiquetaL","Estatal");
          FITD("EEtiquetaC",3);
            BtnImg("vgbuscar","lupa","fBuscaDatos();","");
        FTDTR();
        ITR();
          TDEtiCampo(false,"EEtiqueta",0,"R.F.C.:","cRFC","",14,13," R.F.C.","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0,"R.U.P.A.:","cRPA","",16,15," R.P.A.","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0,"C.U.R.P.:","cCURP","",16,15," C.U.R.P.","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"EEtiquetaL",3);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0,"Pseudónimo:","cPseudonimoEmp","",30,30," Pseudónimo","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"EEtiquetaL",2);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0,"Nombre o Razón Social:","cNomRazonSocial","",87,80," Nombre o Razon Social","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"EEtiquetaL",11);
        FITR();
          TDEtiCampo(false,"EEtiqueta",0,"Ap. Paterno:","cApPaterno","",33,30," Apellido Paterno","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"EEtiquetaL",7);
          TDEtiCampo(false,"EEtiqueta",0,"Ap. Materno:","cApMaterno","",33,30," Apellido Materno","fMayus(this);"," onKeyPress='return fCheckForEnter(event, this, window);' ","",false,"EEtiquetaL",3);
        FTR();
      FinTabla(); //Datos de búsqueda
    FTDTR();
    ITRTD("EEtiquetaC",0,"","","","");
      InicioTabla("",0,"95%","100%","center");
        ITRTD("",0,"","15","center","top");
          IFrame("IListado20","100%","70","Listado.js","yes",true);
        FTDTR();
      FinTabla();  // Despliegue de búsqueda
    FTDTR();
    FTDTR();
    ITRTD("EEtiquetaC",0,"","327","","");
      InicioTabla("",0,"100%","100%","center");
        ITRTD("",0,"100%","100%","center","middle");
          var cCadTitulos = "", cCadPaginas = "";
          cCadTitulos += (lShowPersona)?"Datos Generales<br>de la Persona|Domicilio de<br>la Persona|":"";
          cCadTitulos += (lShowRepLegal)?"Representante<br>Legal|Domicilio del<br>Rep. Legal|":"";
          cCadPaginas += (lShowPersona)?"pg111010011A.js|pg111010012.js|":"";
          cCadPaginas += (lShowRepLegal)?"pg111010013.js|pg111010014.js|":"";
          fDefCarpeta(cCadTitulos,cCadPaginas+"false","PEM","99%","99%",true );
        FTDTR();
      FinTabla(); // Folder
    FTDTR();
    if (desdeOtra){
      ITRTD("EEtiquetaC",0,"100%","","center","");
        InicioTabla("",0,"100%","","center");
          ITRTD("",0,"","","center","center");
            BtnImgNombre("vgcerrar","aceptar","fRegresaDatos();","");
          FTDTR();
        FinTabla(); // Botón Aceptar
      FTDTR();
    }
  FinTabla();
  Hidden("Persona_iCvePersona");
  Hidden("Persona_cRFC");
  Hidden("Persona_cRPA");
  Hidden("Persona_iTipoPersona");
  Hidden("Persona_cNomRazonSocial");
  Hidden("Persona_cApPaterno");
  Hidden("Persona_cApMaterno");
  Hidden("Persona_cCorreoE");
  Hidden("Persona_cPseudonimoEmp");

  Hidden("Persona_iCveDomicilio");
  Hidden("Persona_iCveTipoDomicilio");
  Hidden("Persona_cCalle");
  Hidden("Persona_cNumExterior");
  Hidden("Persona_cNumInterior");
  Hidden("Persona_cColonia");
  Hidden("Persona_cCodPostal");
  Hidden("Persona_cTelefono");
  Hidden("Persona_iCvePais");
  Hidden("Persona_cDscPais");
  Hidden("Persona_iCveEntidadFed");
  Hidden("Persona_cDscEntidadFed");
  Hidden("Persona_iCveMunicipio");
  Hidden("Persona_cDscMunicipio");
  Hidden("Persona_iCveLocalidad");
  Hidden("Persona_cDscLocalidad");
  Hidden("Persona_lPredeterminado");
  Hidden("Persona_cDscTipoDomicilio");
  Hidden("Persona_cDscDomicilio");

  Hidden("RepLegal_iCvePersona");
  Hidden("RepLegal_cRFC");
  Hidden("RepLegal_cRPA");
  Hidden("RepLegal_iTipoPersona");
  Hidden("RepLegal_cNomRazonSocial");
  Hidden("RepLegal_cApPaterno");
  Hidden("RepLegal_cApMaterno");
  Hidden("RepLegal_cCorreoE");
  Hidden("RepLegal_cPseudonimoEmp");
  Hidden("RepLegal_lPrincipal");

  Hidden("RepLegal_iCveDomicilio");
  Hidden("RepLegal_iCveTipoDomicilio");
  Hidden("RepLegal_cCalle");
  Hidden("RepLegal_cNumExterior");
  Hidden("RepLegal_cNumInterior");
  Hidden("RepLegal_cColonia");
  Hidden("RepLegal_cCodPostal");
  Hidden("RepLegal_cTelefono");
  Hidden("RepLegal_iCvePais");
  Hidden("RepLegal_cDscPais");
  Hidden("RepLegal_iCveEntidadFed");
  Hidden("RepLegal_cDscEntidadFed");
  Hidden("RepLegal_iCveMunicipio");
  Hidden("RepLegal_cDscMunicipio");
  Hidden("RepLegal_iCveLocalidad");
  Hidden("RepLegal_cDscLocalidad");
  Hidden("RepLegal_lPredeterminado");
  Hidden("RepLegal_cDscTipoDomicilio");
  Hidden("RepLegal_cDscDomicilio");

  Hidden("hdFiltro11");
  Hidden("hdFiltro11A");
  Hidden("hdFiltro12");
  Hidden("hdFiltro13");
  Hidden("hdFiltro14");
  Hidden("cNombreFRM");
  fFinPagina();
}

function fOnLoad(){ // Carga información al mostrar la página.
  FRMListado = fBuscaFrame("IListado20");
  FRMListado.fSetControl(self);
  if (lShowRepLegal){
    FRMFiltro = fBuscaFrame("IFiltro13");
    if (FRMFiltro){
      if (desdeOtra){
        FRMFiltro.fSetControl(self);
        FRMFiltro.fShow(",");
      }
      FRMFiltro.fSetFiltra("Clave,P.iCvePersona,RFC,P.cRFC,RPA,P.cRPA,Nombre,P.cNomRazonSocial,Paterno,P.cPaterno,Materno,P.cMaterno,");
      FRMFiltro.fSetOrdena("Empresa,P.iCveEmpresa,Representante Legal,P.iCvePersona,");
    }
  }
  FRMObj = frm = document.forms[0];
  if(window.parent){
	  if(window.parent.fCargaPaices){}else  fCargaCatalogoPaises();
  }else  fCargaCatalogoPaises();
    FRMObj1= fBuscaFrame("PEM1");
    FRMObj2= fBuscaFrame("PEM2");
    FRMObj3= fBuscaFrame("PEM3");
    FRMObj4 = fBuscaFrame("PEM4");
  /*if (lShowPersona){
    FRMObj1= fBuscaFrame("PEM1");
    if(FRMObj1.setEditValues)
      FRMObj1.setEditValues(lEditPersona);
    FRMObj2= fBuscaFrame("PEM2");
    if(FRMObj2.setEditValues)
      FRMObj2.setEditValues(lEditDomPersona);
  }
  if (lShowRepLegal){
    FRMObj3= fBuscaFrame("PEM3");
    if(FRMObj3.setEditValues)
      FRMObj3.setEditValues(lEditRepLegal);
    FRMObj4 = fBuscaFrame("PEM4");
    if(FRMObj4.setEditValues)
      FRMObj4.setEditValues(lEditDomRepLegal);
  }*/
  fPagFolder(1);
  iPagAct = 1;
  if (!fGetRadioValue(FRMObj.iTipo))
    fSetRadioValue(FRMObj.iTipo, "2");
  FRMObj.Persona_iTipoPersona.value = fGetRadioValue(FRMObj.iTipo);
  fListado();
  fOnChange(false);
  if (FRMObj.cRFC)
    FRMObj.cRFC.focus();
 // if (top.opener)
 //   if(top.opener.fGetClaves)
 //     top.opener.fGetClaves(this);
  frm.cNomRazonSocial.focus();
}

var aResPersonas;
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  // Recibe el resultado en el Vector aRes
  fResCatalogoPaisEntidad(aRes,cId,cError);

  if(cId == "ListadoPer" && cError==""){
    for(i=0;i<aRes.length;i++){
      var iFolio=parseInt(aRes[i][12],10);
      var cFolio="";
      cFolio=iFolio>999?""+iFolio:iFolio>99?"0"+iFolio:iFolio>9?"00"+iFolio:"000"+iFolio;
      aRes[i][20]=cFolio+"-"+aRes[i][11]+"-"+aRes[i][10].substr(2,4);
      aRes[i][21]=aRes[i][9];
    }
    aResPersonas = fCopiaArregloBidim(aRes);
    fListado();
    FRMObj.hdRowPag.value = iRowPag;
    if (FRMListado){
      FRMListado.fSetListado(aResPersonas);
      FRMListado.fShow();
      if (FRMFiltro)
        FRMFiltro.fSetNavStatus(cNavStatus);
      fOnChange(false);
    }
  }
  FRMObj.iTipo.Disabled=true;
}

function fSetDatosBuscar(TipoPersona, RFC, RPA, Pseudonimo, Nombre, ApPaterno, ApMaterno, lNoBuscar){
  FRMObj.cRFC.value="";
  FRMObj.cRPA.value="";
  FRMObj.cPseudonimoEmp.value="";
  FRMObj.cNomRazonSocial.value="";
  FRMObj.cApPaterno.value="";
  FRMObj.cApMaterno.value="";
  if (TipoPersona)
    fSetTipoPersona(TipoPersona);
  if (RFC)
    FRMObj.cRFC.value = RFC;
  if (RPA)
    FRMObj.cRPA.value = RPA;
  if (Pseudonimo)
    FRMObj.cPseudonimoEmp.value = Pseudonimo;
  if (Nombre)
    FRMObj.cNomRazonSocial.value = Nombre;
  if (ApPaterno)
    FRMObj.cApPaterno.value = ApPaterno;
  if (ApMaterno)
    FRMObj.cApMaterno.value = ApMaterno;
  if(!lNoBuscar)
    fBuscaDatos();
}

function fBuscaDatos(){
  if(!lCargado)
    return;
   lLongitudVar = false;
   if(frm.cRFC.value.length > 2 || frm.cRPA.value.length > 2 || frm.cPseudonimoEmp.value.length > 2 ||
      frm.cNomRazonSocial.value.length > 2 || frm.cApPaterno.value.length > 2 ||
      frm.cApMaterno.value.length >2 || frm.cCURP.value.length > 2){
        lLongitudVar = true;

  }else{
        lLongitudVar = false;
  }
  if(lLongitudVar == false){
     fAlert("Teclee almenos 3 letras por opción de búsqueda");
     return;
  }else{
     if(!lPermiteBusqueda){
       fAlert("No tiene permitido realizar esta operación.");
       return;
     }
  }

  if(lEditando == false){
    if(FRMObj.cRFC.value.length > 3)
      if(!fNum(FRMObj.cRFC.value.substring(0,4)))
        fSetTipoPersona("1");
    if (iPagAct <= 2){
      DomOriginal = "";
      RepOriginal = "";
      fLimpiaSolicitante();
      fLimpiaRepLegal();
      fNavega();
      fFocusLiga(document, 'vgcerrar');
    }else{
      RepOriginal = "";
      DomRepOriginal = "";
      fLimpiaRepLegal();
      FRMObj3.fNavega3(window);
    }
  }
}

function fLimpiaSolicitante(){
  FRMObj.Persona_iCvePersona.value = "";
  FRMObj.Persona_cRFC.value = "";
  FRMObj.Persona_cRPA.value = "";
  FRMObj.Persona_iTipoPersona.value = "";
  FRMObj.Persona_cNomRazonSocial.value = "";
  FRMObj.Persona_cApPaterno.value = "";
  FRMObj.Persona_cApMaterno.value = "";
  FRMObj.Persona_cCorreoE.value = "";
  FRMObj.Persona_cPseudonimoEmp.value = "";

  FRMObj.Persona_iCveDomicilio.value= "";
  FRMObj.Persona_iCveTipoDomicilio.value= "";
  FRMObj.Persona_cCalle.value= "";
  FRMObj.Persona_cNumExterior.value= "";
  FRMObj.Persona_cNumInterior.value= "";
  FRMObj.Persona_cColonia.value= "";
  FRMObj.Persona_cCodPostal.value= "";
  FRMObj.Persona_cTelefono.value= "";
  FRMObj.Persona_iCvePais.value= "";
  FRMObj.Persona_cDscPais.value= "";
  FRMObj.Persona_iCveEntidadFed.value= "";
  FRMObj.Persona_cDscEntidadFed.value= "";
  FRMObj.Persona_iCveMunicipio.value= "";
  FRMObj.Persona_cDscMunicipio.value= "";
  FRMObj.Persona_iCveLocalidad.value= "";
  FRMObj.Persona_cDscLocalidad.value= "";
  FRMObj.Persona_lPredeterminado.value= "";
  FRMObj.Persona_cDscTipoDomicilio.value= "";
  FRMObj.Persona_cDscDomicilio.value= "";

  FRMObj1.document.forms[0].cPersona_Domicilio.value = "";
}

function fLimpiaRepLegal(){
  FRMObj.RepLegal_iCvePersona.value = "";
  FRMObj.RepLegal_cRFC.value = "";
  FRMObj.RepLegal_cRPA.value = "";
  FRMObj.RepLegal_iTipoPersona.value = "";
  FRMObj.RepLegal_cNomRazonSocial.value = "";
  FRMObj.RepLegal_cApPaterno.value = "";
  FRMObj.RepLegal_cApMaterno.value = "";
  FRMObj.RepLegal_cCorreoE.value = "";
  FRMObj.RepLegal_cPseudonimoEmp.value = "";

  FRMObj.RepLegal_iCveDomicilio.value= "";
  FRMObj.RepLegal_iCveTipoDomicilio.value= "";
  FRMObj.RepLegal_cCalle.value= "";
  FRMObj.RepLegal_cNumExterior.value= "";
  FRMObj.RepLegal_cNumInterior.value= "";
  FRMObj.RepLegal_cColonia.value= "";
  FRMObj.RepLegal_cCodPostal.value= "";
  FRMObj.RepLegal_cTelefono.value= "";
  FRMObj.RepLegal_iCvePais.value= "";
  FRMObj.RepLegal_cDscPais.value= "";
  FRMObj.RepLegal_iCveEntidadFed.value= "";
  FRMObj.RepLegal_cDscEntidadFed.value= "";
  FRMObj.RepLegal_iCveMunicipio.value= "";
  FRMObj.RepLegal_cDscMunicipio.value= "";
  FRMObj.RepLegal_iCveLocalidad.value= "";
  FRMObj.RepLegal_cDscLocalidad.value= "";
  FRMObj.RepLegal_lPredeterminado.value= "";
  FRMObj.RepLegal_cDscTipoDomicilio.value= "";
  FRMObj.RepLegal_cDscDomicilio.value= "";

  FRMObj1.document.forms[0].cRepLegal_Nombre.value = "";
  FRMObj1.document.forms[0].cRepLegal_Domicilio.value = "";
}

function fFolderOnChange( iPag ) { // iPag indica a la página que se desea cambiar
  if(""+iTipoPersonaSeleccionado != "") 
    fSetTipoPersona(iTipoPersonaSeleccionado);
  if (lShowRepLegal && parseInt(""+iTipoPersonaSeleccionado,10)==1 && iPag > 2){
    iPag = 1;
    fAlert('\n - Esta opción solo es válida para personas no físicas.');
    return false;
  }

  iPagAct = iPag;
  if(iPag > 2){
    iPagAnt = iPag;
    fSetRadioValue(FRMObj.iTipo, "1");
    fEnableRadio(FRMObj.iTipo, false);
  }else{
    fSetTipoPersona(FRMObj.Persona_iTipoPersona.value);
    fEnableRadio(FRMObj.iTipo, true);
  }
  fOnChange(false);
  if(iPag==1)frm.cNombreFRM.value="PEM1";
  if(iPag==2)frm.cNombreFRM.value="PEM2";
  if(iPag==3)frm.cNombreFRM.value="PEM3";
  if(iPag==4)frm.cNombreFRM.value="PEM4";
}

function fBuscaRepLegal(){
  if(FRMObj.Persona_iCvePersona.value!="") {
    if (lShowRepLegal && iTipoPersonaSeleccionado!="1"){
      if (RepOriginal != "")
        FRMObj.RepLegal_iCvePersona.value = RepOriginal;
      if (FRMObj3)
        if (FRMObj3.fSetClavePersona)
          FRMObj3.fSetClavePersona(FRMObj.Persona_iCvePersona.value, FRMObj.RepLegal_iCvePersona.value);
    }else{
      if (FRMObj3)
        if (FRMObj3.fResultado){
          FRMObj3.fResultado(new Array(),"ListadoRepLegal","","",0,"");
          FRMObj3.fResultado(new Array(),"Listado2","","",0,"");
        }
      fBuscaDomRepLegal();
    }
  }
}

function fBuscaDomRepLegal(){
  if(FRMObj.Persona_iCvePersona.value!="") {
    if (lShowRepLegal && iTipoPersonaSeleccionado!="1"){
      if(FRMObj.RepLegal_iCvePersona.value != ""){
        if (DomRepOriginal != "")
          FRMObj.RepLegal_iCveDomicilio.value = DomRepOriginal;
        if (FRMObj4)
          if (FRMObj4.fSetNombre)
            FRMObj4.fSetNombre(FRMObj.RepLegal_cNomRazonSocial.value,FRMObj.RepLegal_cApPaterno.value,FRMObj.RepLegal_cApMaterno.value,FRMObj.RepLegal_iCvePersona.value, FRMObj.RepLegal_iCveDomicilio.value);
      }
    }else{
      if (FRMObj4){
        if (FRMObj4.fResultado)
          FRMObj4.fResultado(new Array(),"ListadoDomRL","","",0,"");
        if (FRMObj4.fSetNombre)
          FRMObj4.fSetNombre("","","","","");
      }
    }
  }
}

function fCambioDato(){
  if(FRMObj.Persona_iCvePersona.value!="") {
    if (DomOriginal != "")
      FRMObj.Persona_iCveDomicilio.value = DomOriginal;
    if (FRMObj2)
      if (FRMObj2.fSetClavePersona)
        FRMObj2.fSetClavePersona(FRMObj.Persona_iCvePersona.value, FRMObj.Persona_iCveDomicilio.value);
  }
}

function fSelReg(aDato){
  // Asigna valores en campos ocultos para regreso
  FRMObj.Persona_iCvePersona.value = aDato[0];
  FRMObj.Persona_cRFC.value = aDato[1];
  FRMObj.Persona_cRPA.value = aDato[2];
  /*
  if(top.opener){
      if(top.opener.fValidaRUPA){
	  if(top.opener.fValidaRUPA()==true){
	      if((FRMObj.Persona_cRPA.value == "") || (FRMObj.Persona_cRFC.value=="") ){
		  fAlert("Es requerido que el solicitante cuente con R.F.C. y R.U.P.A. Le sugerimos ingresar los valores.");
	      }
	  }
      }	  
  }*/
      
  
  
  if (aDato)
    if (aDato[3] != ""){
      iTipoPersonaSeleccionado = aDato[3];
      fSetRadioValue(FRMObj.iTipo, aDato[3]);
    }
  FRMObj.Persona_iTipoPersona.value = aDato[3];
  FRMObj.Persona_cNomRazonSocial.value = aDato[4];
  FRMObj.Persona_cApPaterno.value = aDato[5];
  FRMObj.Persona_cApMaterno.value = aDato[6];
  FRMObj.Persona_cCorreoE.value = aDato[7];
  FRMObj.Persona_cPseudonimoEmp.value = aDato[8];
  // Asigna valores a campos de pestaña de detalle de persona
  FRMObj1.fSetCvePersona(FRMObj.Persona_iCvePersona.value, this, aDato);
  FRMObj1.fSetRFC(FRMObj.Persona_cRFC.value);
  FRMObj1.fSetRPA(FRMObj.Persona_cRPA.value);
  FRMObj1.fSetTipoPersona(FRMObj.Persona_iTipoPersona.value);
  FRMObj1.fSetNombre(FRMObj.Persona_cNomRazonSocial.value);
  FRMObj1.fSetPaterno(FRMObj.Persona_cApPaterno.value);
  FRMObj1.fSetMaterno(FRMObj.Persona_cApMaterno.value);
  FRMObj1.fSetPseudo(FRMObj.Persona_cPseudonimoEmp.value);
  FRMObj1.fSetCorreo(FRMObj.Persona_cCorreoE.value);
  fCambioDato();
}

function fNavega(){
  FRMObj.Persona_iTipoPersona.value = fGetRadioValue(FRMObj.iTipo);
  var cadRFC = (FRMObj.cRFC.value!="")?" and P.cRFC LIKE '" + FRMObj.cRFC.value + "%'":"";
  var cadRPA = (FRMObj.cRPA.value!="")?" and P.cRPA LIKE '" + FRMObj.cRPA.value + "%'":"";
  var cadNom = (FRMObj.cNomRazonSocial.value!="")?" and P.cNomRazonSocial LIKE '" + FRMObj.cNomRazonSocial.value + "%'":"";
  var cadApPat = (FRMObj.cApPaterno.value!="")?" and P.cApPaterno LIKE '" + FRMObj.cApPaterno.value + "%'":"";
  var cadApMat = (FRMObj.cApMaterno.value!="")?" and P.cApMaterno LIKE '" + FRMObj.cApMaterno.value + "%'":"";
  var cadPseud = (FRMObj.cPseudonimoEmp.value!="")?" and P.cPseudonimoEmp LIKE '" + FRMObj.cPseudonimoEmp.value + "%'":"";
  var cadCURP = (FRMObj.cCURP.value!="")?" and P.cCURP LIKE '" + FRMObj.cCURP.value + "%'":"";
  FRMObj.hdFiltro11.value = "P.iTipoPersona= " + FRMObj.Persona_iTipoPersona.value;
  if (FRMFiltro){
    FRMObj.hdOrden.value = FRMFiltro.fGetOrden();
    FRMObj.hdNumReg.value =  FRMFiltro.fGetNumReg();
  }
  var lSubmiteBusca = false;

  if(FRMObj.Persona_iTipoPersona.value == 1)
    if((cadRFC == "") && (cadRPA == "") && ((cadNom == "") || ((cadApPat == "") && (cadApMat == "") && (cadCURP == ""))))
      fAlert("Favor de proporcionar RFC, RPA, CURP o Nombre y Un Apellido");
    else{
      FRMObj.hdFiltro11.value += cadRFC + cadRPA + cadNom + cadApPat + cadApMat + cadCURP;
      lSubmiteBusca = true;
    }
  else
    if((cadRFC == "") && (cadRPA == "") && (cadNom == "") && (cadPseud == "") && (cadCURP == ""))
      fAlert("Favor de proporcionar RFC, RPA, Nombre o Pseudónimo");
    else{
      FRMObj.hdFiltro11.value += cadRFC + cadRPA + cadNom + cadPseud+cadCURP;
      lSubmiteBusca = true;
    }
  if (lSubmiteBusca && !lEditando){
	if(FRMObj.hdFiltro11.value!="")
      fEngSubmite("pgGRLPersonaA1.jsp","ListadoPer");
  }
  fOnChange(false);
}

function fRefreshData(cTipoPersona, cPersona, cDomPersona, cRepLegal, cDomRepLegal){
  if (cTipoPersona)
    fSetTipoPersona(cTipoPersona);
  if (cPersona)
    if(parseInt(cPersona,10)>0)
      FRMObj.Persona_iCvePersona.value = cPersona;
  if (cDomPersona){
    DomOriginal = cDomPersona;
    FRMObj.Persona_iCveDomicilio.value = cDomPersona;
  }
  if (cRepLegal){
    RepOriginal = cRepLegal;
    FRMObj.RepLegal_iCvePersona.value = cRepLegal;
  }
  if (cDomRepLegal){
    DomRepOriginal = cDomRepLegal;
    FRMObj.RepLegal_iCveDomicilio.value = cDomRepLegal;
  }
  if (FRMObj.Persona_iCvePersona.value != "" && parseInt(FRMObj.Persona_iCvePersona.value,10)>0){
    FRMObj.hdFiltro11.value = "P.iCvePersona= " +  FRMObj.Persona_iCvePersona.value;
    fEngSubmite("pgGRLPersonaA1.jsp","ListadoPer");
  }
}

function fListado(){
  FRMObj.Persona_iTipoPersona.value = fGetRadioValue(FRMObj.iTipo);
  if( FRMObj.Persona_iTipoPersona.value==1){
    FRMListado.fSetTitulo("Clave,R.F.C.,R.P.A.,Nombre o Razón Social,RPMN,");
    FRMListado.fSetCampos("0,1,2,21,20,");
    FRMListado.fSetAlinea("center,left,left,left,left,");
  }else{
    FRMListado.fSetTitulo("Clave,R.F.C.,R.P.A.,Nombre o Razón Social,Pseudónimo,RPMN,");
    FRMListado.fSetCampos("0,1,2,21,8,20,");
    FRMListado.fSetAlinea("center,left,left,left,left,left,");
  }
}

function fOnChange(lBlank){
  if(lBlank)
    fBlanked();
  if(iPagAct > 2)
    fSetRadioValue(FRMObj.iTipo, "1");
  if (cSoloTipoPersona)
    if (cSoloTipoPersona != ""){
      fSetTipoPersona(cSoloTipoPersona);
      FRMObj.iTipo.disabled = true;
    }
  if(fGetRadioValue(FRMObj.iTipo)!=1){
    fDisabled(false);
    FRMObj.cApPaterno.value = "";
    FRMObj.cApMaterno.value = "";
    fDisabled(true,"iTipo,cRFC,cRPA,cCURP,cNomRazonSocial,cPseudonimoEmp,");
  }else{
    fDisabled(false);
    FRMObj.cPseudonimoEmp.value = "";
    fDisabled(true,"iTipo,cRFC,cRPA,cCURP,cNomRazonSocial,cApPaterno,cApMaterno,");
  }
}

function fRegresaDatos(){
  lEditando = false;
  lEditandoOtros = false;
  if (lShowPersona){
    if(!lEditandoOtros && FRMObj1 && FRMObj1.lEditando) lEditandoOtros = true;
    if(!lEditandoOtros && FRMObj2 && FRMObj2.lEditando) lEditandoOtros = true;
  }
  if (lShowRepLegal){
    if(!lEditandoOtros && FRMObj3 && FRMObj3.lEditando) lEditandoOtros = true;
    if(!lEditandoOtros && FRMObj4 && FRMObj4.lEditando) lEditandoOtros = true;
  }
  if(lEditandoOtros){
    fAlert('\n - Esta acción no esta disponible en modo edición de cualquier pestaña.');
    return;
  }
  if (desdeOtra){
    if (top.opener){
      if (lShowPersona){
        if (FRMObj.Persona_iCveDomicilio.value == "")
          FRMObj2.fSetClavePersona(FRMObj.Persona_iCvePersona.value, "");
        if (FRMObj.Persona_iCvePersona.value == "" || FRMObj.Persona_iCveDomicilio.value == ""){
          fLimpiaSolicitante();
          fLimpiaRepLegal();
          if (top.opener.fValoresPersona)
            top.opener.fValoresPersona("","","","","","","","","","","","","","",
                                       "","","","","","","","","","","","","","");
          if (top.opener.fValoresRepLegal)
            top.opener.fValoresRepLegal("","","","","","","","","","","","","","",
                                        "","","","","","","","","","","","","","");
          fAlert("\nSeleccione los datos de persona");
          return;
        }
        if (top.opener.fValoresPersona)
          top.opener.fValoresPersona(FRMObj.Persona_iCvePersona.value,
                                     FRMObj.Persona_cRFC.value,
                                     FRMObj.Persona_cRPA.value,
                                     FRMObj.Persona_iTipoPersona.value,
                                     FRMObj.Persona_cNomRazonSocial.value,
                                     FRMObj.Persona_cApPaterno.value,
                                     FRMObj.Persona_cApMaterno.value,
                                     FRMObj.Persona_cCorreoE.value,
                                     FRMObj.Persona_cPseudonimoEmp.value,
                                     FRMObj.Persona_iCveDomicilio.value,
                                     FRMObj.Persona_iCveTipoDomicilio.value,
                                     FRMObj.Persona_cCalle.value,
                                     FRMObj.Persona_cNumExterior.value,
                                     FRMObj.Persona_cNumInterior.value,
                                     FRMObj.Persona_cColonia.value,
                                     FRMObj.Persona_cCodPostal.value,
                                     FRMObj.Persona_cTelefono.value,
                                     FRMObj.Persona_iCvePais.value,
                                     FRMObj.Persona_cDscPais.value,
                                     FRMObj.Persona_iCveEntidadFed.value,
                                     FRMObj.Persona_cDscEntidadFed.value,
                                     FRMObj.Persona_iCveMunicipio.value,
                                     FRMObj.Persona_cDscMunicipio.value,
                                     FRMObj.Persona_iCveLocalidad.value,
                                     FRMObj.Persona_cDscLocalidad.value,
                                     FRMObj.Persona_lPredeterminado.value,
                                     FRMObj.Persona_cDscTipoDomicilio.value,
                                     FRMObj.Persona_cDscDomicilio.value);
      }
      if (lShowRepLegal){
        if (FRMObj.Persona_iTipoPersona.value != "1"){
          if (FRMObj.RepLegal_iCvePersona.value == "" || FRMObj.RepLegal_iCveDomicilio.value == ""){
            fLimpiaRepLegal();
            if (top.opener.fValoresRepLegal)
              top.opener.fValoresRepLegal("","","","","","","","","","","","","","",
                                          "","","","","","","","","","","","","","");
            fAlert("\nSeleccione los datos de representante legal");
            return;
          }
        }
        if (top.opener.fValoresRepLegal)
          if (FRMObj.Persona_iTipoPersona.value == "1")
            top.opener.fValoresRepLegal("","","","","NO APLICA A PERSONAS FISICAS","","","","","","","","","",
                                        "","","","","","","","","","","","","","");
          else
            top.opener.fValoresRepLegal(FRMObj.RepLegal_iCvePersona.value,
                                        FRMObj.RepLegal_cRFC.value,
                                        FRMObj.RepLegal_cRPA.value,
                                        FRMObj.RepLegal_iTipoPersona.value,
                                        FRMObj.RepLegal_cNomRazonSocial.value,
                                        FRMObj.RepLegal_cApPaterno.value,
                                        FRMObj.RepLegal_cApMaterno.value,
                                        FRMObj.RepLegal_cCorreoE.value,
                                        FRMObj.RepLegal_cPseudonimoEmp.value,
                                        FRMObj.RepLegal_iCveDomicilio.value,
                                        FRMObj.RepLegal_iCveTipoDomicilio.value,
                                        FRMObj.RepLegal_cCalle.value,
                                        FRMObj.RepLegal_cNumExterior.value,
                                        FRMObj.RepLegal_cNumInterior.value,
                                        FRMObj.RepLegal_cColonia.value,
                                        FRMObj.RepLegal_cCodPostal.value,
                                        FRMObj.RepLegal_cTelefono.value,
                                        FRMObj.RepLegal_iCvePais.value,
                                        FRMObj.RepLegal_cDscPais.value,
                                        FRMObj.RepLegal_iCveEntidadFed.value,
                                        FRMObj.RepLegal_cDscEntidadFed.value,
                                        FRMObj.RepLegal_iCveMunicipio.value,
                                        FRMObj.RepLegal_cDscMunicipio.value,
                                        FRMObj.RepLegal_iCveLocalidad.value,
                                        FRMObj.RepLegal_cDscLocalidad.value,
                                        FRMObj.RepLegal_lPredeterminado.value,
                                        FRMObj.RepLegal_cDscTipoDomicilio.value,
                                        FRMObj.RepLegal_cDscDomicilio.value);
      }
    }
    var lCierra = true;
    if (FRMObj.Persona_iCveDomicilio.value == ""){
      fAlert('\nFavor de seleccionar el domicilio de la persona.');
      lCierra = false;
    }
    if (lShowRepLegal && FRMObj.Persona_iTipoPersona.value != 1 && (FRMObj.RepLegal_iCvePersona.value == "" || FRMObj.RepLegal_iCveDomicilio.value == "")){
      fAlert('\nFavor de seleccionar al representante legal y su domicilio.');
      lCierra = false;
    }
    if (lCierra == true)
      lEditando = false;
      lEditandoOtros = false;
      if (lShowPersona){
         FRMObj1.lEditando = false;
         FRMObj2.lEditando = false;
      }
      if (lShowRepLegal){
         FRMObj3.lEditando = false;
         FRMObj4.lEditando = false;
      }
      top.close();
  }
}

function fDeshabilita(){
  FRMListado.fSetDisabled(true);
  fDisabled(true);
  lEditando = true;
}
function fHabilita(lSelReg){
  if(!lSelReg)
    lSelReg = true;
  FRMListado.fSetDisabled(false, lSelReg);
  lEditando = false;
  fOnChange(false);
}

//obtiene la llave primaria del registro insertado
function fSetLlave(iCvePersona, lNavega){
  if(iCvePersona && iCvePersona+'' != 'undefined' && iCvePersona > 0){
    cLlavePA1 = iCvePersona;
    if (FRMObj.Llave)
      FRMObj.Llave.value=cLlavePA1;
    if (lNavega){
      FRMObj.hdFiltro11.value = "P.iCvePersona= " + iCvePersona;
      fEngSubmite("pgGRLPersonaA1.jsp","ListadoPer");
    }
  }
}

function fSetTipoPersona(cTipoPersona){
  if (cTipoPersona != "")
    FRMObj.Persona_iTipoPersona.value=cTipoPersona;
  else
    FRMObj.Persona_iTipoPersona.value="2";
  fSetRadioValue(FRMObj.iTipo, FRMObj.Persona_iTipoPersona.value);
}

function fGetTipoPersona(){
  return fGetRadioValue(FRMObj.iTipo);
}

function dispPersona_Domicilio(){
  FRMObj1= fBuscaFrame("PEM1");
  FRMObj1.document.forms[0].cPersona_Domicilio.value = FRMObj.Persona_cDscDomicilio.value;
}

function dispRepLegal_Nombre(){
  FRMObj1= fBuscaFrame("PEM1");
  if (lShowRepLegal){
    FRMObj1.document.forms[0].cRepLegal_Nombre.value = FRMObj.RepLegal_cNomRazonSocial.value;
    if (FRMObj.RepLegal_iTipoPersona.value == "1")
      FRMObj1.document.forms[0].cRepLegal_Nombre.value += " " + FRMObj.RepLegal_cApPaterno.value + " " + FRMObj.RepLegal_cApMaterno.value;
  }else
    FRMObj1.document.forms[0].cRepLegal_Nombre.value = "";
}

function dispRepLegal_Domicilio(){
  FRMObj1= fBuscaFrame("PEM1");
  if (lShowRepLegal){
    dispRepLegal_Nombre();
    FRMObj1.document.forms[0].cRepLegal_Domicilio.value = FRMObj.RepLegal_cDscDomicilio.value;
  }
  else
    FRMObj1.document.forms[0].cRepLegal_Domicilio.value = "";
}

function fEnterLocal(theObject, theEvent, theWindow){
  objName = theObject.name;
  if (objName == 'cRFC' || objName == 'cRPA' || objName == 'cPseudonimoEmp' ||
      objName == 'cNomRazonSocial' || objName == 'cApPaterno' || objName == 'cApMaterno'|| objName == 'cCURP'){
    fMayus(theObject);
    if(theObject.value.length > 2)
       fBuscaDatos();
    else{
       fAlert("Teclee almenos 3 letras por opción de búsqueda");
    }
  }
}

function fRestablece(){
  lEditando = false;
  lEditandoOtros = false;
  if (lShowPersona){
     FRMObj1.lEditando = false;
     FRMObj2.lEditando = false;
  }
  if(lShowRepLegal){
     FRMObj3.lEditando = false;
     FRMObj4.lEditando = false;
  }
  top.close();
}

function fTerminaCargaPaisEntidad(){
  lCargado = true;
  FRM2 = fBuscaFrame("PEM2");
  FRM2.fCargaTipoDom();
  if (top.opener)
    if(top.opener.fGetClaves)
      top.opener.fGetClaves(this);
}

function fSetCampo(campo,valor1,valor2){
    elCampo = null;
    elCampo = eval('frm.cRFC');
    elCampo.value = valor1;
    elCampo1 = null;
}


function fSetNombre(cNombre){
    FRMObj1= fBuscaFrame("PEM1");
    FRMObj1.frm.cNomRazonSocial.value = cNombre;    
}
function fSetCURP(cCURP){ FRMObj1= fBuscaFrame("PEM1"); FRMObj1.frm.cCURP.value = cCURP;}
function fSetApPaterno(cApPaterno){
    FRMObj1= fBuscaFrame("PEM1");
    FRMObj1.frm.cApPaterno.value = cApPaterno;    
}
function fSetApMaterno(cApMaterno){
    FRMObj1= fBuscaFrame("PEM1");
    FRMObj1.frm.cApMaterno.value = cApMaterno;    
}
function fGetlRFCObligatorio(){
	//regresa false para indicar que la captura de RFC no es obligatoria
	return lRFCObligatorio;
}

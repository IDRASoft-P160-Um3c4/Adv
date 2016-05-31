// MetaCD=1.0
// Title: pg111010011A.js
// Description: JS "Catálogo" de la entidad GRLPersona
// Company: Tecnología InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm, FRMObj;
var lEditPersona = true,FRMPanel;
var lEditando = false;
var aDato;
var personaBuscada = null; 

var buscarRepL = false;

var iniciaTramite = false;
var consultaSolicitud= false;
var arrConsulta=undefined;


var cMsg ="";

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg111010011A.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
  
  if(window.parent){
	  buscarRepL = window.parent.getBuscarRepL();
	  iniciaTramite = window.parent.getIniciaTramite();
	  consultaSolicitud = window.parent.getConsultaSol();
  }
  
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
      
    InicioTabla("ETablaInfo",0,"0","","center","",1);
        ITRTD("ETablaST",0,"","","center");
          cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"DATOS GENERALES":cTitulo;
          TextoSimple(cTitulo);
        FTDTR();
        ITRTD("EEtiquetaC",0,"","","","");
	        InicioTabla("ETablaInfo",0,"0","","center","",1);
		        ITRTD("EEtiqueta",0,"","","","");
			        	TextoSimple("Tipo de Persona: ");
			        FITD("EEtiqueta",0);
			        	Radio(true,"iTipo",1,true,"","","",'onClick="fOnChangeTipoPersona();"');
			        FITD("EEtiquetaL",0);
			        	Etiqueta("Fisica","EEtiquetaL","Física");
			        FITD("EEtiqueta",0);
			        	Radio(true,"iTipo",2,false,"","","",'onClick="fOnChangeTipoPersona();"');
			        FITD("EEtiquetaL",0);
			        	Etiqueta("Moral","EEtiquetaL","Moral");
			        FITD("EEtiquetaL",0);
		        FTR();
	        FinTabla();
        FTDTR();
        
        ITRTD("EEtiquetaC",0,"","","","");
	        InicioTabla("ETablaInfo",0,"0","","center","",1);
		        ITR("EEtiqueta",0,"","","","");
			        TDEtiCampo(true,"EEtiqueta",0,"R.F.C.:","cRFC","",30,13," R.F.C.","fMayus(this);","","",false,"EEtiquetaL",0);
		        	TDEtiCampo(true,"EEtiqueta",0,"Correo Electrónico:","cCorreoE","",30,50,"Correo Electrónico","fMayus(this);","","",false,"EEtiquetaL",0);
		        FTR();
		        TDEtiCampo(true,"EEtiqueta",0,"Nombre o Razón Social:","cNomRazonSocial","",60,80," Nombre o Razon Social","fMayus(this);","","",false,"EEtiquetaL",0);
		        FITR();
		          TDEtiCampo(false,"EEtiqueta",0,"Ap. Paterno:","cApPaterno","",30,30," Apellido Paterno","fMayus(this);","","",false,"EEtiquetaL",0);
		          TDEtiCampo(false,"EEtiqueta",0,"Ap. Materno:","cApMaterno","",30,30," Apellido Materno","fMayus(this);","","",false,"EEtiquetaL",0);
		        FTR();
	        FinTabla();
        FTDTR();
        
        ITRTD("EEtiquetaC",0,"","","","");
	        InicioTabla("ETablaInfo",0,"0","","center","",1);
		        ITRTD("ETablaST",8,"","","center");        
		          TextoSimple("DATOS DEL DOMICILIO");
		        FTDTR();
		        ITR();
		          TDEtiCampo(true,"EEtiqueta",0," Calle:","cCalle","",40,40," Calle","fMayus(this);","","",false,"EEtiquetaL",0);
		          TDEtiCampo(false,"EEtiqueta",0," Núm. Exterior:","cNumExterior","",10,10," Número Exterior","fMayus(this);","","",false,"EEtiquetaL",0);
		          TDEtiCampo(false,"EEtiqueta",0," Núm. Interior:","cNumInterior","",11,11," Número Interior","fMayus(this);","","",false,"EEtiquetaL",0);
		        FITR();
		          TDEtiCampo(true,"EEtiqueta",0," Colonia:","cColonia","",40,40," Colonia","fMayus(this);","","",false,"EEtiquetaL",0);
		          TDEtiCampo(false,"EEtiqueta",0," C.P.:","cCodPostal","",5,5," C.P.","fMayus(this);","","",false,"EEtiquetaL",0);
		          TDEtiCampo(true,"EEtiqueta",0," Teléfono:","cTelefono","",25,50," Teléfono","fMayus(this);","","",false,"EEtiquetaL",0);
		        FTR();
		        ITRTD("EEtiqueta",0,"","","left","");
		        	TextoSimple("Entidad | Municipio | Localidad:");
		        	FTD();
		        	ITD("EEtiquetaL",5,"","","left","");
		          Select("iCveEntidadFed","fChangeEntidadFed();");
		          Select("iCveMunicipio","fChangeMunicipio();");
		          Select("iCveLocalidad");
		        FTDTR();
		       
		        if(buscarRepL){
			        ITRTD("EEtiquetaC",8,"","","center");        
			        BtnImgNombre("vgcerrar","aceptar","fRegresaDatos();","");
			        FTDTR();
		        }else
		        if(iniciaTramite){
			        ITRTD("EEtiquetaC",8,"","","center");        
			        BtnImgNombre("vgcerrar","aceptar","fRegresaDatosTramite();","");
			        FTDTR();
		        }
		        else
		        if(consultaSolicitud){
			        ITRTD("EEtiquetaC",8,"","","center");        
			        BtnImgNombre("vgcerrar","aceptar","fRegresaDatosConsultaSol();","");
			        FTDTR();
		        }
		        
		    FinTabla();
	    FTDTR();
	    
	    if(buscarRepL!=true&&iniciaTramite!=true&&consultaSolicitud!=true){
		    ITRTD("",0,"","40","center","bottom");
		      IFrame("IPanel1A","95%","34","Paneles.js");
		    FTDTR();
	    }
	    
      FinTabla();
      Hidden("hdLlave",0);
  Hidden("iCvePersona",0);
  Hidden("iCveDomicilio",0);
  Hidden("hdiTipo",0);
      
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  
  if(buscarRepL!=true&&iniciaTramite!=true&&consultaSolicitud!=true){
	  FRMPanel = fBuscaFrame("IPanel1A");
	  FRMPanel.fSetControl(self,cPaginaWebJS);
	  FRMPanel.fShow("Tra,");
	  FRMPanel.fSetTraStatus("AddOnly");
  }
  
  fDisabled(true);
  //frm.hdBoton.value="Primero";
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
 
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
	
  if(cError!=""){
    fAlert (cError);
  }

    //refresca el listado del contenedor despues de guardar el registro
  if(cId=="RefreshPersona" && cError == ""){
    
  }
  
  if(cId=="cIdEntidades" && cError == ""){
	  fFillSelect(frm.iCveEntidadFed,aRes,false,frm.iCveEntidadFed.value,0,1);
	  if(personaBuscada!=null){
		  fSelectSetIndexFromValue(frm.iCveEntidadFed, personaBuscada.iCveEntidadFed);	   
	  }
	  fChangeEntidadFed();
  }
  
  if(cId=="cIdMunicipios" && cError == ""){
	  fFillSelect(frm.iCveMunicipio,aRes,false,frm.iCveMunicipio.value,0,1);
	  if(personaBuscada!=null){
		  fSelectSetIndexFromValue(frm.iCveMunicipio, personaBuscada.iCveMunicipio);	   
	  }
	  fChangeMunicipio();
  }
  
  if(cId=="cIdLocalidades" && cError == ""){
	  fFillSelect(frm.iCveLocalidad,aRes,false,frm.iCveLocalidad.value,0,1);
	  if(personaBuscada!=null){
		  fSelectSetIndexFromValue(frm.iCveLocalidad, personaBuscada.iCveLocalidad);	   
	  }
  }
  
  if( (cId=="cIdGuardar" || cId=="cIdUpdate") && cError == ""){
	  fAlert("Se han guardado los datos de persona con éxito.");
	  frm.iCvePersona.value=aRes[0][0];
	  frm.iCveDomicilio.value=aRes[0][1];
	  fDisabled(true,"iCvePersona,iCveDomicilio,hdiTipo,","--");
	  FRMPanel.fSetTraStatus("UpdateComplete");
	  if (window.parent){
			window.parent.setExistePersona(true);
			window.parent.setIcvePersona(frm.iCvePersona.value);
			window.parent.fBuscaPostGuardar(returnPersonaParams());
	  }
  }
  
  if(cId=="cIdBorrar" && cError == ""){
	  if(aRes.length>0){
		  fAlert("Esta persona tiene solicitudes asociadas. No es posible eliminar.");
	  }else{
		  fAlert("Se ha eliminado la persona con éxito.");
		  frm.iCvePersona.value=-1;
		  frm.iCveDomicilio.value=-1;
		  if (window.parent){
				window.parent.setExistePersona(false);
				window.parent.setIcvePersona(-1);
				window.parent.fBuscaPostGuardar(returnPersonaParams());
		  }
	  }
  }
  
  
  
 }

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
  lEditando = true;
  FRMPanel.fSetTraStatus("UpdateBegin");
  fBlanked();
  fDisabled(false,"iCvePersona,iCveDomicilio,hdiTipo,","--");
  fOnChangeTipoPersona();
  resetAllForm();
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
	
	cMsg = fValElements();
	
	if(cMsg!=""){
		fAlert(cMsg);
		return;
	}
	
	if(fValidaTodo()==false)
		return;
	
	frm.iTipo.value = fGetRadioValue(frm.iTipo);
	
	if(confirm("Se guardará la información en pantalla ¿Está seguro que desea continuar con la información en pantalla?")){
		frm.hdBoton.value = "Guardar";
		frm.hdLlave.value = "";
		fEngSubmite("pgGRLPersonaADV.jsp","cIdGuardar");
	}
}

function fGuardarA(){
	
	cMsg = fValElements();
	
	if(cMsg!=""){
		fAlert(cMsg);
		return;
	}
	
	if(fValidaTodo()==false)
		return;
	
	frm.iTipo.value = fGetRadioValue(frm.iTipo);
	
	if(confirm("Se guardará la información en pantalla ¿Está seguro que desea continuar con la información en pantalla?")){
		frm.hdBoton.value = "GuardarA";
		frm.hdLlave.value = "";
		fEngSubmite("pgGRLPersonaADV.jsp","cIdUpdate");
	}
}

// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
  if(frm.iCvePersona.value > 0){
      FRMPanel.fSetTraStatus("UpdateBegin");
      fDisabled(false,"iCvePersona,iCveDomicilio,hdiTipo,");
      fOnChangeTipoPersona();
      lEditando = true;
  }
}

// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
  lEditando = false;
  fDisabled(true);
  if(FRMPanel)
	  FRMPanel.fSetTraStatus("UpdateComplete");
}

// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
  if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
	  frm.hdBoton.value = "Borrar";
	  fEngSubmite("pgGRLPersonaADV.jsp","cIdBorrar");
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
  
	if(!fValRFC(frm.cRFC.value,parseInt(""+iTipo,10)))
	    cMsg += '\n-Favor de verificar el RFC, no cumple con las reglas para el tipo de persona';		    
  
  if (!fEMail(frm.cCorreoE.value))
    cMsg += '\n-Favor de verificar el correo electrónico, tiene datos incorrectos';

  
  if(parseInt(iTipo,10) == 1){
   if((frm.cRFC.value == "") || 
    		((frm.cNomRazonSocial.value == "") || (!fSoloAlfanumericosNomRazonSoc(frm.cNomRazonSocial.value))) || 
    		((frm.cApPaterno.value == "") && (frm.cApMaterno.value == "")))
        cMsg += "\nFavor de proporcionar RFC y Nombre con un Apellido sin caracteres especiales";
   }else{
	    if( (frm.cRFC.value == "") || (frm.cNomRazonSocial.value == "")||(!fSoloAlfanumericosNomRazonSoc(frm.cNomRazonSocial.value)))
	        cMsg += "\nFavor de proporcionar RFC y Razón social sin caracteres especiales";		  
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

function fOnChangeTipoPersona(){
  var iTipo = fGetRadioValue(frm.iTipo);
  frm.hdiTipo.value=iTipo;
  
  if(iTipo!=1){
	frm.cApPaterno.value="";
	frm.cApMaterno.value="";
    fDisabled(false);
    fDisabled(true,"iTipo,cRFC,cNomRazonSocial,cCorreoE,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCveEntidadFed,iCveMunicipio,iCveLocalidad,");
  }else{
    fDisabled(false);
    fDisabled(true,"iTipo,cRFC,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCveEntidadFed,iCveMunicipio,iCveLocalidad,");
  }
  
}

function fGetEntidades(){
	frm.hdBoton.value = "getEntidades";
	frm.hdLlave.value = "ICVEENTIDADFED";
	fEngSubmite("pgGRLPersonaADV.jsp","cIdEntidades");
}

function fGetMunicipios(){
	if(frm.iCveEntidadFed.value!=""){
		frm.hdBoton.value = "getMunicipios";
		fEngSubmite("pgGRLPersonaADV.jsp","cIdMunicipios");
	}
}

function fGetLocalidades(){
	if(frm.iCveMunicipio.value!=""){
		frm.hdBoton.value = "getLocalidades";
		fEngSubmite("pgGRLPersonaADV.jsp","cIdLocalidades");
	}
}

function fChangeEntidadFed(){
	fGetMunicipios();
}

function fChangeMunicipio(){
	fGetLocalidades();
}

function setPersonaSel(personaObj){
	
	if(personaObj!=undefined){
		fCancelar();
		personaBuscada = personaObj;
		frm.iCvePersona.value=personaObj.iCvePersona;
		frm.iCveDomicilio.value=personaObj.iCveDomicilio;
		frm.cRFC.value=personaObj.cRFC;
		fSetRadioValue(frm.iTipo, personaObj.iTipo);
		frm.cNomRazonSocial.value=personaObj.cNomRazonSocial; 
		frm.cApPaterno.value=personaObj.cApPaterno;
		frm.cCorreoE.value=personaObj.cCorreoE;
		frm.cApMaterno.value=personaObj.cApMaterno;
		frm.cCalle.value=personaObj.cCalle;
		frm.cNumExterior.value=personaObj.cNumExterior;
		frm.cNumInterior.value=personaObj.cNumInterior;
		frm.cColonia.value=personaObj.cColonia;
		frm.cCodPostal.value=personaObj.cCodPostal;
		frm.cTelefono.value=personaObj.cTelefono;
		if(window.parent){
				window.parent.setExistePersona(true);
				window.parent.setIcvePersona(frm.iCvePersona.value);
		}
			
		fGetEntidades();
		return;
	}
	personaSel=null;
}

function resetAllForm(){
	
	personaBuscada = null;
	frm.iCvePersona.value=undefined;
	frm.iCveDomicilio.value=undefined;
	frm.cRFC.value="";
	fSetRadioValue(frm.iTipo, 1);
	frm.cNomRazonSocial.value=""; 
	frm.cApPaterno.value="";
	frm.cCorreoE.value="";
	frm.cApMaterno.value="";
	frm.cCalle.value="";
	frm.cNumExterior.value="";
	frm.cNumInterior.value="";
	frm.cColonia.value="";
	frm.cCodPostal.value="";
	frm.cTelefono.value="";
	if(window.parent){
			window.parent.setExistePersona(false);
			window.parent.setIcvePersona(-1);
	}
		
	fSelectSetIndexFromValue(frm.iCveEntidadFed, 0);
	fGetEntidades();
}

function fRegresaDatos(){
//	var nuevoRepl = {};
	
//	nuevoRepl.iCvePersona= personaBuscada.iCvePersona;
//	nuevoRepl.iTipo=personaBuscada.iTipo;
//	nuevoRepl.cRFC=personaBuscada.cRFC; 
//	nuevoRepl.cCorreoE=personaBuscada.cCorreoE;
//	nuevoRepl.cNomRazonSocial=personaBuscada.cNomRazonSocial;
//	nuevoRepl.cApPaterno=personaBuscada.cApPaterno;
//	nuevoRepl.cApMaterno=personaBuscada.cApMaterno;
//	nuevoRepl.cCalle=personaBuscada.cCalle;
//	nuevoRepl.cNumExterior=personaBuscada.cNumExterior;
//	nuevoRepl.cNumInterior=personaBuscada.cNumInterior;
//	nuevoRepl.cColonia=personaBuscada.cColonia;
//	nuevoRepl.cCodPostal=personaBuscada.cCodPostal;	
//	nuevoRepl.cEntidad=frm.iCveEntidadFed.options[frm.iCveEntidadFed.selectedIndex].text;
//	nuevoRepl.cMunicipio=frm.iCveMunicipio.options[frm.iCveMunicipio.selectedIndex].text;
//	nuevoRepl.cLocalidad=frm.iCveLocalidad.options[frm.iCveLocalidad.selectedIndex].text;
//	
	if(top.opener.fSetRepresentante){
		top.opener.seleccionaReplegalEmergente(personaBuscada.iCvePersona);
		top.close();
	}
	
}

function fRegresaDatosTramite(){
	
	if(top.opener.fValoresPersona){
		
	var cDscEntidadFed=frm.iCveEntidadFed.options[frm.iCveEntidadFed.selectedIndex].text;
	var cDscMunicipio=frm.iCveMunicipio.options[frm.iCveMunicipio.selectedIndex].text;
	var cDscLocalidad=frm.iCveLocalidad.options[frm.iCveLocalidad.selectedIndex].text;
		
	var cDscDomicilio = "CALLE: " + arrConsulta[8] + ", EXT. "
	+ arrConsulta[9] + ", INT."
	+ arrConsulta[10] + ", COL. "
	+ arrConsulta[11] + ", C.P. "
	+arrConsulta[12] + ", "
	+ cDscLocalidad + ", " + cDscMunicipio + ", "
	+ cDscEntidadFed;
	
	top.opener.fValoresPersona(
			arrConsulta[0],
			arrConsulta[2],
			"",
			arrConsulta[3],
			arrConsulta[4],
			arrConsulta[5],
			arrConsulta[6],
			arrConsulta[7],
			"",
			arrConsulta[1],
			1,
			arrConsulta[8],
			arrConsulta[9],
			arrConsulta[10],
			arrConsulta[11],
			arrConsulta[12],
			arrConsulta[13],
			1,
			"MÉXICO",
			arrConsulta[14],
			cDscEntidadFed,
			arrConsulta[15],
			cDscMunicipio,
			arrConsulta[16],
			cDscLocalidad,
			1,
			"PRINCIPAL",
			cDscDomicilio);

	if(parseInt(arrConsulta[17])>0){ 
		cDscDomicilio = "CALLE: " + arrConsulta[25] + ", EXT. "
		+ arrConsulta[26] + ", INT."
		+ arrConsulta[27] + ", COL. "
		+ arrConsulta[28] + ", C.P. "
		+arrConsulta[29] + ", "
		+ arrConsulta[34] + ", " + arrConsulta[33] + ", "
		+ arrConsulta[32];
	}else{
		cDscDomicilio ="";
	}	
	
	top.opener.fValoresRepLegal(
			 	arrConsulta[17],
				arrConsulta[19],
				"",
				arrConsulta[20],
				arrConsulta[21],
				arrConsulta[22],
				arrConsulta[23],
				arrConsulta[24],
				"",
				arrConsulta[18],
				1,
				arrConsulta[25],
				arrConsulta[26],
				arrConsulta[27],
				arrConsulta[28],
				arrConsulta[29],
				arrConsulta[30],
				1,
				"MÉXICO",
				-1,
				arrConsulta[32],
				-1,
				arrConsulta[33],
				-1,
				arrConsulta[34],
				1,
				"PRINCIPAL",
				cDscDomicilio);
	
	top.close();
	}
}

function fRegresaDatosConsultaSol(){
	if(top.opener.setDatosPersona){
		var obj = {};
		obj.cRFC = frm.cRFC.value;
		obj.cNomRazonSocial = frm.cNomRazonSocial.value;
		top.opener.setDatosPersona(obj);
		top.close();
	}
}

function returnPersonaParams(){
	
	return obj;
}

function setEstausPanel(cve){
	  if(FRMPanel)
		  FRMPanel.fSetTraStatus(cve);
}

function setArrConsulta(aDato){
	if(aDato!=undefined){
		arrConsulta=[];
		for(var t = 0; t<aDato.length;t++){
			arrConsulta.push(aDato[t]);
		}
	}
}
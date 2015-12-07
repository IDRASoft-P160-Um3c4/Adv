 // MetaCD=1.0
 // Title: pg10053015000.js
 // Description: Carpeta de creacion de grupos de placas
 // Company: INFOTEC
 // Author: alopez

 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 var tBarraWizard;
 var tBarraConsecutivo;
 var guardado = 0;
 var entrar = 0;
 var CAR_ESCENCIAL = 22;
 var aIngresos = new Array();
 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pgINTSol01FIEL.js";
   if(top.fGetTituloPagina){ 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
 } 
 
 // SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){ 
    Estilo(".ETablaInfoPanel","height:30px;border:1px solid #6B96AD;background-color:#C0C0C0;text-align:center;font-family:Verdana;font-size:10pt;TEXT-DECORATION:none;font-weight:Bold;background-color: #C0C0C0;");	
    Estilo(".ESUP1","COLOR:800000;font-family:Verdana;font-size:9pt;font-weight: Normal;");
    Estilo(".ESUP2","COLOR:800000;font-family:Verdana;font-size:9pt;font-weight: Normal;");
    Estilo(".ECOR","COLOR:2B0082;font-family:Verdana;font-size:8pt;font-weight: Normal;text-align:center;");
    fInicioPagina("E4E4E4"); 
	   Estilo("A:Link","COLOR:00049E;font-family:Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
	   Estilo("A:Visited","COLOR:00049E;font-family: Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");     
	InicioTabla("",0,"100%","","","","1"); 
		ITRTD("",0,"","","top"); 
			InicioTabla("",0,"100%","","center"); 
				ITRTD("EEtiquetaC",0,"100%","25","center","top");
					TextoSimple("SI DESEAS BUSCAR UNA SOLICITUD ANTES CAPTURADA, POR FAVOR TECLEA LA CLAVE DEL TRÁMITE:");
				FTDTR(); 
				ITRTD("EEtiquetaC",0,"100%","25","center","top");
					Text(false,"cBusca","",10,10,"","fMayus(this);",'onKeyDown="return fCheckReturn(event);"'); 
					Liga("[BUSCAR]","fBuscar();","NUEVO TRÁMITE","lBusca"); 
				FTDTR(); 
				ITRTD("EEtiquetaC",0,"100%","25","center","top");
					cGPD += "<div id='panelNuevoTramite1' style='display:block;'>";
					TextoSimple("SI DESEAS INICIAR UNA NUEVA SOLICITUD DA CLIC EN LA SIGUIENTE LIGA:");
					cGPD += "</div>";
				FTDTR(); 
				ITRTD("ELIGA",0,"100%","25","center","top");
					cGPD += "<div id='panelNuevoTramite2' style='display:block;'>";
					Liga("[NUEVO TRÁMITE]","fNuevoTramite();","NUEVO TRÁMITE"); 
					cGPD += "</div>";
				FTDTR();
				ITRTD("ESUP1",0,"","","center",""); 
			
				   InicioTabla("",0,"100%","","center");
				   ITRTD("ESUP1",0,"70%","","center",""); 
				       TextoSimple('"En caso de Dudas o Aclaraciones enviar correo electrónico a:"');
				       cGPD+='<a href="mailto:cheredia@sct.gob.mx">cheredia@sct.gob.mx</a>';
				       //TextoSimple('<BR>"Si su trámite es del interior de la República enviar correo electrónico a:"');
				       //cGPD+='<a href="mailto:cheredia@sct.gob.mx">cheredia@sct.gob.mx</a>';
				   FTDTR();
				   ITRTD("ESUP2",0,"","","center","middle"); 
			           TextoSimple('<BR>Recuerde que sus documentos digitalizados deben ir en formato PDF no mayor a 2MB.');
				       TextoSimple('<BR>Deberá concluir la <B>Declaración de características</B> para que su solicitud sea procesada en ventanilla.');
				   FTDTR();
				   FinTabla();
				   
			    FTDTR();				
				ITRTD("",0,"","40","center","bottom"); 
					DinTabla("BarraWizard","",0,"","100%","center","top","",".1",".1");//IFrame("IPanelINT","95%","34","Paneles.js"); 
				FTDTR(); 
				ITRTD("",0,"","175","center","top"); 
					cGPD += "<div id='panelBusqueda' style='display:none;'>";
					IFrame("IListadoINT","95%","170","ListadoINT.js","yes",true); 
					cGPD += "</div>";
				FTDTR();
				ITRTD("",0,"","40","center","bottom"); 
					DinTabla("BarraConsecutivo","",0,"","100%","center","top","",".1",".1");
				FTDTR(); 
				
			FTDTR();			    
			FinTabla(); 
		FTDTR(); 
		ITRTD("",0,"","40","center","bottom"); 
			//IFrame("IPanelINTC","95%","34","Paneles.js"); 
		FTDTR(); 
	FinTabla();
	Hidden("ICONSECUTIVO","");
	Hidden("LFINALIZADO","0");
	Hidden("ICVEDEPTO","");
	Hidden("CPERMISIONA","");
	Hidden("LCONCLUIDO","");
	Hidden("iCveSolicitante",0);
	Hidden("iEjercicio",0);
	Hidden("iNumSolicitud",0);
	Hidden("iNumCita",0);
	Hidden("iCveTramiteTmp",0);
	Hidden("iCveModalidadTmp",0);
	Hidden("cRFCRep","");
	Hidden("cCveInterna","");
	Hidden("cModalidad","");
	fFinPagina(); 
} 
 
 function fOnLoad(){ 
   frm = document.forms[0];    
   FRMListado = fBuscaFrame("IListadoINT"); 

	
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Llenado,Ejercicio/Solicitud,Clave del Trámite,Tipo de trámite, Modalidad,");
   FRMListado.fSetCampos("1,0,5,6,");
   FRMListado.fSetObjs(0,"Liga",{label:"Declaración de características", toolTip: "", style: "color:#0000FF;text-decoration:underline;"});
   //FRMListado.fSetObjs(1,"Liga",{label:"Borrar", toolTip: "", style: "color:#0000FF;text-decoration:underline;"});
    
   fDisabled(false); 
   frm.hdBoton.value=""; 
   
   tBarraWizard = document.getElementById("BarraWizard");
   tBarraConsecutivo = document.getElementById("BarraConsecutivo");
 }

function fNuevoTramite(){
	fShowFormulario();
	fHideListado();
	frm.cBusca.value = "";
	frm.iNumCita.value = "0";
	frm.ICONSECUTIVO.value = "0";
	frm.cBusca.disabled = true;
	frm.hdOrden.value = "iCveOficina";
	frm.hdFiltro.value = " iCveOficina > 0";
	frm.hdNumReg.value = 10000;
        fEngSubmite("pgGRLOficina.jsp","cIdOficina");
	frm.hdOrden.value = "";   
}

function fBuscar(liga)
{
  var patron =/^([0-9]+)$/
  //if(!fGO("lBusca").disabled){
	if(frm.cBusca.value.match(patron))
	{
	  frm.iNumCita.value = frm.cBusca.value;
	  frm.hdNumReg.value = 10000;
	  frm.hdBoton.value = "Buscar";
	  frm.hdOrden.value  =  "";
	  frm.hdFiltro.value = "";
	  fEngSubmite("pgINTTram1A.jsp","IDTram");
	}
	else
	{
	  fAlert("\n - El campo solo acepta valores numericos");
	}
  //}
}

function fGuardarNuevo()
{
    
    if(window.parent){
	       if(window.parent.fGetRFCRep){
		   frm.cRFCRep.value = window.parent.fGetRFCRep();
		}
	   }
      frm.hdNumReg.value = 10000;
      frm.hdBoton.value = "GuardarSol";
      frm.hdOrden.value  =  "";
      frm.hdFiltro.value = "";
      fEngSubmite("pgINTTram1A.jsp","IDTram");
}

function fBorrar(){
	frm.hdNumReg.value = 10000;
	frm.hdBoton.value = "Borrar";
	frm.hdOrden.value  =  "";
	frm.hdFiltro.value = "";
	fEngSubmite("pgINTTram1A.jsp","IDTram"); 
}

function fFinalizar(){
  if(frm.LCONCLUIDO.value > 0)
  {
    frm.hdNumReg.value = 10000;
	frm.hdBoton.value = "Finalizar";
	frm.hdOrden.value  =  ""; 
	frm.hdFiltro.value = "";
	fEngSubmite("pgINTTram1A.jsp","IDTram");
  }
  else
  {
    fAlert("\n - No puedes FINALIZAR el Trámite hasta que realices el registro del vehículo");
  }
}

  
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,grd){ 
	if(cError=="Guardar") 
		fAlert("Existió un error en el Guardado!"); 
	if(cError=="Borrar") 
		fAlert("Existió un error en el Borrado!"); 
	if(cError=="Cascada"){ 
		fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!"); 
		return; 
	} 
	if(cId == "cIdTramite"){
	    for (i=0;i<aRes.length;i++){
		aRes[i][25]=aRes[i][1]+" - "+aRes[i][3];
	    }
		frm.hdOrden.value  =  "";
		frm.hdFiltro.value = "";
		fFillSelect(frm.iCveTramite,aRes,false,frm.iCveTramite.value,0,25);
		fLoadModalidad();
   }
   if(cId == "cIdOficina"){
			fFillSelect(frm.iCveOficina,aRes,false,frm.iCveOficina.value,0,1);
			frm.hdOrden.value  =  "T.cCveInterna";
			frm.hdFiltro.value = " lTramInt=1 AND lVigente=1 and LTRAMITEFINAL=1";
			if(top.fGetUA()==1) frm.hdFiltro.value += " AND T.cCveInterna not like 'PT%'";
			if(top.fGetUA()==2) frm.hdFiltro.value += " AND T.cCveInterna like 'PT%'";
			fEngSubmite("pgTRACatTramite1.jsp","cIdTramite");
   }
   if(cId == "cIdModalidad")
   {
	 fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,1,11);
	 var indice = frm.iCveModalidad.selectedIndex;
	 frm.CPERMISIONA.value = frm.iCveModalidad.options[indice].text;
   }
	if(cId == "IDTram" && cError==""){ 
		frm.hdBoton.value = "";
		if(aRes.length > 0){
			fCancelarNuevoTramite();
			fShowListado();
			frm.hdRowPag.value = iRowPag; 
			FRMListado.fSetListado(aRes); 
			FRMListado.fShow(); 
			FRMListado.fSetLlave(cLlave);
			guardado = grd;
			fConsultaIngresos();
		}else{
			fAlert("\n - El trámite " + frm.iNumCita.value + " no se encuetra registrado.");
			frm.iNumCita.value = "";
		}
		aIngresos= new Array();
	}   
	if(cId == "cIdIngresos" && cError==""){ 	
	    aIngresos = aRes;
	}
}

function fShowFormulario(){
	bGuardar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fGuardarNuevo();'>Guardar</div>";
	bCancelar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fCancelarNuevoTramite();'>Cancelar</div>";
	while(tBarraWizard.rows.length > 0) {
		tBarraWizard.deleteRow(tBarraWizard.rows.length-1);
	}

	tBarraWizard.className = "ETablaInfo";
	tBarraWizard.width = "100%";
	tRw = tBarraWizard.insertRow();
	
	tCell = tRw.insertCell();
	if(top.fGetUA()==1)tCell.innerHTML = "Seleccione la Oficina donde su solicitud será atendida:";
	if(top.fGetUA()==2)tCell.innerHTML = "Seleccione la Oficina donde su solicitud será entregada:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Select("iCveOficina");
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Tipo de Tramite:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Select("iCveTramite","fLoadModalidad();");
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.innerHTML = "Modalidad:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Select("iCveModalidad");
	
	tRw = tBarraWizard.insertRow();
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.align = "center";
	tCell.innerHTML = "<table border='0'><tr><td class='ETablaInfoPanel'>" + bGuardar + "</td><td class='ETablaInfoPanel'>" + bCancelar + "</td></tr></table>";
}

function fShowListado(){
	document.getElementById("panelBusqueda").style.display = "block";
	
	bAgregar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fGuardarNuevo();'>Nuevo Movimiento</div>";
	bFinalizar = "<div class style='padding: 2 15 2 15;cursor:pointer;' onclick='fFinalizar();'>Finalizar Trámite</div>";
	
	while(tBarraConsecutivo.rows.length > 0) {
		tBarraConsecutivo.deleteRow(tBarraConsecutivo.rows.length-1);
	}

	tBarraConsecutivo.className = "ETablaInfo";
	tBarraConsecutivo.width = "90%";
	tRw = tBarraConsecutivo.insertRow(0);
	tCell = tRw.insertCell();
	tCell.innerHTML = "Tipo Trámite:" +
	Hidden("ICVETIPOPERMISIONA","") + 
	Hidden("ICVETIPOTRAMITE","");
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(false,"CTIPOPERMISIONARIO","",100,100,"","","disabled='disabled'");
	tRw = tBarraConsecutivo.insertRow(1);
	tCell = tRw.insertCell();
	tCell.innerHTML = "Modalidad:";
	tCell.className = "EEtiqueta";
	tCell = tRw.insertCell();
	tCell.innerHTML = Text(false,"CDSCTIPOTRAMITE","",100,100,"","","disabled='disabled'");
	tRw = tBarraConsecutivo.insertRow(2);
	tCell = tRw.insertCell();
	tCell.colSpan = 2;
	tCell.id = "trTramiteButtons";
	tCell.align = "center";
	//tCell.innerHTML = "<table border='0'><tr><td class='ETablaInfoPanel'>" + bAgregar ;
}

function fHideListado(){
	document.getElementById("panelBusqueda").style.display = "none";
	while(tBarraConsecutivo.rows.length > 0) {
		tBarraConsecutivo.deleteRow(tBarraConsecutivo.rows.length-1);
	}
}

function fCancelarNuevoTramite(){
	while(tBarraWizard.rows.length > 0) {
		tBarraWizard.deleteRow(tBarraWizard.rows.length-1);
	}
	frm.cBusca.disabled = false;
	//fGO("lBusca").disabled = false;
}

function fCancelar(){
	fCancelarNuevoTramite();
}

function fLoadModalidad(){
    if(frm.iCveTramite.value>0){
        frm.hdNumReg.value = 10000;
        frm.hdOrden.value  =  "";
        frm.hdFiltro.value = "lActivo=1 AND TC.iCveTramite = " + frm.iCveTramite.value + " AND tc.LTRAMINT=1 " ;
        fEngSubmite("pgTRAConfiguraTramite.jsp","cIdModalidad");
    }
}

function fSelReg(aDato,col){
	frm.iNumCita.value = aDato[0];
	frm.cBusca.value = aDato[0];
	frm.ICONSECUTIVO.value = aDato[1];
	frm.CTIPOPERMISIONARIO.value = aDato[5];
	frm.CDSCTIPOTRAMITE.value = aDato[6];
	frm.iCveTramiteTmp.value = aDato[2];
	frm.iCveModalidadTmp.value = aDato[3];
	frm.ICVEDEPTO.value = aDato[8];
	frm.LCONCLUIDO.value = aDato[11];
	frm.iEjercicio.value = aDato[9];
	frm.iNumSolicitud.value = aDato[10];
	if(aDato[7] != ""){
		//fGO("trTramiteButtons").innerHTML = "<td></td>";
		frm.LFINALIZADO.value = 1;
	}else{
		frm.LFINALIZADO.value = 0;
	}
	if(col == 0){
		parent.setIREGISTRO(frm.ICONSECUTIVO.value);
		parent.fPagFolder(2);
	}
	/********
	else if(col == 1){
		if(getLFINALIZADO() == 0){
			fBorrar();
		}else{
			fAlert("\n - Trámite finalizado. No se pueden modificar ni borrar datos.");
		}
	}
	*/
}
 
function fCheckReturn(evt){
    evt=(evt) ? evt : window.event;
    var charCode=(evt.which)?evt.which:evt.keyCode;
    if(charCode==13){
      fBuscar();
      return false;
    }
    return true;
}

function getICVETRAMITE(){
	return frm.iNumCita.value;
}

function getICONSECUTIVO(){
	return frm.ICONSECUTIVO.value;
}

function getICVETIPOTRAMITE(){
	return frm.ICVETIPOTRAMITE.value;
}

function getLFINALIZADO(){
	return frm.LFINALIZADO.value;
}

function getICVETIPOPERMISIONA(){
	return frm.ICVETIPOPERMISIONA.value;
}

function getCTIPOPERMISIONARIO(){
	return frm.CTIPOPERMISIONARIO.value;
}

function getCDSCTIPOTRAMITE(){
	return frm.CDSCTIPOTRAMITE.value;
}

function setValues(guard)
{
  guardado = guard;
}
function fGetCveTramite(){
    return frm.iCveTramiteTmp.value;
}
function fGetModalidad(){
    return frm.iCveModalidadTmp.value;
}

function fGetEjercicio(){ return frm.iEjercicio.value;}
function fGetSolicitud(){ return frm.iNumSolicitud.value;}
function fGetTramiteDsc(){ return frm.iCveTramite.options[frm.iCveTramite.selectedIndex].text}
function fGetModalidadDsc(){ return frm.iCveModalidad.options[frm.iCveModalidad.selectedIndex].text}
function fConsultaIngresos(){
	frm.hdNumReg.value = 10000;
	fEngSubmite("pgObtieneIngresos.jsp","cIdIngresos");
}
function fGetIngresos(){
    return aIngresos;
}
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var idUser = fGetIdUsrSesion();
 var aOficinaDeptoUsrAsg, aOficinaUsrAsg;
 var lOtrasCausas = false;
 var lHabTodas = false;
 var lExisteSol = false;
 var lGuardar = false;
 var prefijoObservacion = 'idObs_';
 var tBarraWizard = undefined;
 var indiceAcuerdos=0;
 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020020A.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
     fSetWindowTitle();
   }
   if(fGetPermisos("pg110020020p.js") == 2)
     lHabTodas = false;
   else
     lHabTodas = true;

 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   
   InicioTabla("",0,"100%","","","","0");
     if (top.opener){
       ITRTD("ETablaST",0,"100%","","top");
         fTituloEmergente(cTitulo,false,cPaginaWebJS);
       FTDTR();
     }else{
        ITRTD("ESTitulo",0,"100%","20","center");
        fTituloCodigo(cTitulo,cPaginaWebJS);
        FTDTR();
     }
     ITRTD("", 9, "", "", "left");
		TextoSimple("Nota: Para agregar observaciones de click en la liga.");
	FTDTR();
 		
 		ITRTD("", 9, "", "", "center");
 			Liga("Agregar observación", "agregarComentario();","");
 		FTDTR();

		ITRTD("", 10, "", "", "center");
		DinTabla("BarraWizard", "", 0, "", "90%", "center", "top", "", ".1", ".1");
		FTDTR();
		ITRTD("", 10, "", "", "center");
		InicioTabla("", 0, "100%", "", "", "", "1");
		IFrame("IPanel", "95%", "34", "Paneles.js");
		FinTabla();
		FTDTR();
FinTabla();

     Hidden("hdLlave");
     Hidden("hdSelect");

    
     Hidden("iEjer","");
     Hidden("iNumSol","");
     Hidden("iCveRequi","");
     Hidden("cadenaObservaciones","");
     Hidden("iCveUser",idUser);
     Hidden("iCveCausaPNC","");
     
   fFinPagina();

 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   tBarraWizard = document.getElementById("BarraWizard");
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(false);
   FRMPanel.fShow("Tra,");
   FRMPanel.fSetTraStatus("Sav,");
   if (top.opener){
	   fRecibeValores();
   }

 }

 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iNConsecutivo){
	 
	 if (cError == "Guardar")
			fAlert("Existió un error en el Guardado!");
		if (cError == "Borrar")
			fAlert("Existió un error en el Borrado!");
		if (cError == "Cascada") {
			fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
			return;
		}
		if (cError != "") {
			fAlert(cError);
			return;
		} 	 
   
   if(cId == "GuardarMultXArea"){
	   if(top.opener){
		   fAlert("\nSe han guardado las causas correctamente.");
		   	top.opener.fNavega();
		   	top.close();
	   }
   }
     
 }

  // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
	 
      if(!top.opener){
         if(frm.iNumSolicitud.value != "")
            fBuscaSolicitud();
         else
            fGuardaDatos();
      }else{
        fGuardaDatos();
      }
 }

 function fGuardaDatos(){
	var cadena = generaCadenaObservaciones();
	
	if(cadena==""){
		fAlert("\nDebe agregar al menos una observación");
		return;
	}else if(confirm("\n¿Está seguro que desea guardar los datos en pantalla?")){
		frm.cadenaObservaciones.value=cadena;
		frm.hdBoton.value="GuardarMultXArea";
		fEngSubmite("pgVerificacionArea.jsp", "GuardarMultXArea");
	}
	
 }
 
 
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
//    frm.iEjercicio.value = aDato[0];
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
  frm.iEjer.value = top.opener.fGethdEjercicio();
  frm.iNumSol.value = top.opener.fGethdNumSolicitud();
  frm.iCveRequi.value = top.opener.fGetiCveRequisito();
}


function agregarComentario() {
		tRw = tBarraWizard.insertRow();
		tCell = tRw.insertCell();
		tCell.className = "EEtiqueta";
		tCell.align = "center";
		tCell.innerHTML = TDEtiAreaTextoID(
				prefijoObservacion + indiceAcuerdos,
				false,
				"EEtiqueta",
				1,
				"Observación:",
				75,
				4,
				"",
				"",
				"Observación:",
				"",
				"fMayus(this);",
				'onchange="fMxTx(this,500);" onkeydown="fMxTx(this,500);" onblur="fMxTx(this,500);"',
				true, true, true, "ECampo", 3);
		indiceAcuerdos++;
}

function generaCadenaObservaciones() {
	var observacionesCadena = "";
	for ( var a = 0; a < indiceAcuerdos; a++) {
		var el = document.getElementById(prefijoObservacion+ a);

		if (el != null && myTrim(el.value)) {
			// elimino el token separador de la cadena si existe
			var cad = el.value.replace(/~/g, "");
			// agrego el token separador
			observacionesCadena += cad + "~";
		}
	}
	return observacionesCadena;
}

function myTrim(x) {
	return x.replace(/^\s+|\s+$/gm, '');
}
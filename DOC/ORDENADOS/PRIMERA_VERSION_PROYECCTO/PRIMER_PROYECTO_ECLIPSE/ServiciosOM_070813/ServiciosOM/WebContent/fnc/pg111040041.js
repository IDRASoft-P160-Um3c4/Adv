// MetaCD=1.0
 // Title: pg111040041.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author:  mbeano
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040041.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     FTDTR();
     ITRTD("",0,"","","top");
       ITRTD("",0,"","40","center","top");
       InicioTabla("ETablaInfo",0,"0","","center","",1);
       ITRTD("",0,"","40","center","top");
       InicioTabla("",0,"0","","center","",1);
          ITR();
             TDEtiCampo(true,"EEtiqueta",0,"Inicio Periodo de Registro:","dtInicioPeriodo","",10,10,"Inicio Periodo de Registro","fMayus(this);","","",false,"EEtiquetaL",3);
             ITD();
             FITD();
             TDEtiCampo(true,"EEtiqueta",0,"Fin Periodo de Registro:","dtFinPeriodo","",10,10,"Fin Periodo de Registro","fMayus(this);","","",false,"EEtiquetaL",3);
             ITD("",0,"","","Right","Center");
                ITD("",0,"","","Right","Center");
                    BtnImg("vgbuscar","lupa","fBuscaDatos();","");
                FTD();
            FTD();
          FITR();
          ITR();
          FITR();
       FTR();
      FinTabla(); //Datos de búsqueda
/******************************************************************************/
      InicioTabla("",0,"0","","center","",1);
           TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficinaUsr","fOficinaOnChange();","",5);
           TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDeptoUsr","","",5);
         //fDefOficXUsr(true);
         FTDTR();
      FinTabla();
      InicioTabla("",0,"0","","center","",1);
         ITRTD("",0,"","","center");
           TDEtiSelect(true,"EEtiqueta",0,"Etapa:","iCveEtapa","","",5);
         FTDTR();
      FinTabla(); //Datos de búsqueda
      FinTabla();

      FTDTR();
      ITRTD("",0,"","175","center","top");
         IFrame("IListado11","95%","170","Listado.js","yes",true);
      FTDTR();
      ITRTD("",0,"","1","center");
      FTDTR();
       //FinTabla();
     FTDTR();
     ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel11","95%","34","Paneles.js");
     FTDTR();
     FinTabla();
    /**/
    Hidden("hdLlave","");
    Hidden("hdSelect","");
    Hidden("dtFecha1");
    Hidden("dtFecha2");
    Hidden("iCveEjercicio");
    Hidden("iNumSolicitud");
    Hidden("iCveTramite");
    Hidden("iCveModalidad");
    Hidden("cDscModalidad");
    Hidden("cDscTramite");
    Hidden("fechaActual");
   Hidden("iDeptoUsuario");
   Hidden("cUsuarioM");
   fFinPagina();

 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel11");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow(",");
   FRMListado = fBuscaFrame("IListado11");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo(" Ejercicio, Solicitud, Trámite, Modalidad, Registro, Respuesta, Entrega, RFC, Solicitante, Departamento,");
   FRMListado.fSetCampos("0,1,11,12,2,3,5,8,9,10,");
   FRMListado.fSetAlinea("center,center,left,center,left,left,left,left,");
   frm.hdBoton.value="Primero";
   fTraeFechaActual();
   frm.cUsuarioM.value  = "";
   frm.cUsuarioM.value = fGetIdUsrSesion();
 }
 // LLAMADO al JSP específico para la navegación de la página
function fNavega(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;

   if (frm.hdFiltro.value !='')
      frm.hdFiltro.value = frm.hdFiltro.value +
                            " AND TRARegEtapasXModTram.iCveDepartamento = "+ frm.iCveDeptoUsr.value+
                            " AND TRARegEtapasXModTram.iCveOficina = "+ frm.iCveOficinaUsr.value+
                            " AND TRARegEtapasXModTram.iCveEtapa = "+ frm.iCveEtapa.value;
   else
      frm.hdFiltro.value = frm.hdFiltro.value +
                            " TRARegEtapasXModTram.iCveDepartamento = "+ frm.iCveDeptoUsr.value+
                            " AND TRARegEtapasXModTram.iCveOficina = "+ frm.iCveOficinaUsr.value+
                            " AND TRARegEtapasXModTram.iCveEtapa = "+ frm.iCveEtapa.value;

   frm.dtFecha1.value = frm.dtInicioPeriodo.value;
   frm.dtFecha2.value = frm.dtFinPeriodo.value;

   return fEngSubmite("pgTRARegSolicitud3A.jsp","Listado");
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

  if(cId == "idFechaActual" && cError==""){
     frm.fechaActual.value = aRes[0,0];
     frm.dtInicioPeriodo.value = frm.fechaActual.value;
     frm.dtFinPeriodo.value = frm.fechaActual.value;
     fDatosUsuario();
  }

   if(cId =="idUsuario" && cError==""){
     if (aRes.length > 0)
	for (var i=0;i<aRes.length;i++){
	    frm.iDeptoUsuario.value = aRes [i][1];
	}
     fCargaEtapa();
   }

   if(cId =="idEtapa" && cError==""){
      if (frm.iDeptoUsuario.value!=95)
         fFillSelect(frm.iCveEtapa,aRes,false,2,0,1);
      else
         fFillSelect(frm.iCveEtapa,aRes,false,1,0,1);
      fCargaDepartamentos();
   }

   if(cId == "Listado" && cError==""){
     if (aRes.length > 0)
	for (var i=0;i<aRes.length;i++){
           if(aRes[i][3]==0)
               aRes[i][3]="NEGATIVO";
           if(aRes[i][3]==1)
              aRes[i][3]="POSITIVO";
        }
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
   }
   if(cId == "CIDOficinaDeptoXUsr")
     fResOficDeptoUsr(aRes,cId,cError,true);

   if(cId=="cIdOficina" && cError==""){
     fFillSelect(frm.iCveOficinaUsr,aRes,false);
     fOficinaOnChange();
   }
   if(cId=="cIdDepto" && cError==""){
     fFillSelect(frm.iCveDeptoUsr,aRes,false);
   }
/*
   if (frm.iDeptoUsuario.value!=95)
      fResOficDeptoUsr(aRes,cId,cError);
   else
      fResOficDeptoUsr(aRes,cId,cError,true);*/
}

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iEjercicio,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicio,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.iCveEjercicio.value = aDato[0];
    frm.iNumSolicitud.value = aDato[1];
    frm.iCveTramite.value = aDato[6];
    frm.cDscTramite.value = aDato[11];
    frm.iCveModalidad.value = aDato[7];
    frm.cDscModalidad.value = aDato[12];
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

 function fBuscaDatos(){
   var bandera = true;
   if (frm.iCveDeptoUsr.value == -1 || frm.iCveOficinaUsr.value == -1 || frm.iCveEtapa.value == -1){
       fAlert("Se debe seleccionar Oficina, Departamento y Etapa");
       bandera = false;
   }
   if(fComparaFecha(frm.dtInicioPeriodo.value,frm.dtFinPeriodo.value, true) == false && frm.dtInicioPeriodo.value != '' && frm.dtFinPeriodo.value != ''){
       fAlert("El Inicio del periodo es mayor al Fin del periodo");
       bandera = false;
   }

   if (fValidaTodo() == true && bandera == true)
      fNavega();
 }

 function fGetiCveEjercicio(){
  return  frm.iCveEjercicio.value;
}

function fGetiNumSolicitud(){
  return  frm.iNumSolicitud.value;
}

function fGetcTramite(){
  return frm.iCveTramite.value = frm.cDscTramite.value;
}

function fGetcModalidad(){
  return frm.iCveModalidad.value = frm.cDscModalidad.value;
}

function fGetcOficina(){
  return frm.iCveOficinaUsr.options[frm.iCveOficinaUsr.selectedIndex].text;
}

 function fCargaEtapa(){
     frm.hdFiltro.value =  "";
     frm.hdOrden.value =  "TRAEtapa.cDscEtapa";
     frm.hdNumReg.value =  "10000";
     fEngSubmite("pgTRAEtapa.jsp","idEtapa");
}

 function fDatosUsuario(){
     frm.hdFiltro.value =  "";
     frm.hdOrden.value =  "";
     frm.hdFiltro.value = "SEGUSUARIO.ICVEUSUARIO = " + frm.cUsuarioM.value;
     fEngSubmite("pgGRLUsuarioXOfic.jsp","idUsuario");
}

/*function fCargaDepartamentos(){
      if (frm.iDeptoUsuario.value == 95)
        fCargaOficDeptoUsr(true);
     else
        fCargaOficDeptoUsr();
}*/
function fCargaDepartamentos(){
   if (frm.iDeptoUsuario.value == 95)
     frm.hdSelect.value = "SELECT ICVEOFICINA,CDSCBREVE FROM GRLOFICINA WHERE LVIGENTE  = 1 ORDER BY CDSCBREVE ";
   else
     frm.hdSelect.value = " SELECT DISTINCT(O.ICVEOFICINA),CDSCBREVE FROM GRLOFICINA O " +
                          " JOIN GRLUSUARIOXOFIC UO ON UO.ICVEOFICINA = O.ICVEOFICINA " +
                          " WHERE O.LVIGENTE = 1 AND ICVEUSUARIO = " + fGetIdUsrSesion()+
                          " ORDER BY CDSCBREVE";
   frm.hdLlave.value    = "iCveOficina";
   fEngSubmite("pgConsulta.jsp","cIdOficina");

}
function fOficinaOnChange(){
 // if(frm.iCveOficinaUsr.value>=0 && frm.cUsuarioM.value>=0){
   if (frm.iDeptoUsuario.value == 95)
     frm.hdSelect.value = "SELECT DISTINCT(D.ICVEDEPARTAMENTO), D.CDSCBREVE FROM GRLUSUARIOXOFIC UO " +
                          "JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO = UO.ICVEDEPARTAMENTO " +
                          "WHERE UO.ICVEOFICINA = "+frm.iCveOficinaUsr.value+
                          " ORDER BY CDSCBREVE ";
   else
     frm.hdSelect.value = "SELECT DISTINCT(D.ICVEDEPARTAMENTO), D.CDSCBREVE FROM GRLUSUARIOXOFIC UO " +
                          "JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO = UO.ICVEDEPARTAMENTO " +
                          "WHERE UO.ICVEOFICINA = "+frm.iCveOficinaUsr.value+
                          " AND UO.ICVEUSUARIO = "+fGetIdUsrSesion()+
                          " ORDER BY CDSCBREVE ";
   frm.hdLlave.value  = "UO.ICVEOFICINA,UO.ICVEDEPARTAMENTO,UO.ICVEUSUARIO";
   fEngSubmite("pgConsulta.jsp","cIdDepto");
  //}
}

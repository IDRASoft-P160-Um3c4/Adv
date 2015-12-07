// MetaCD=1.0
 // Title: pg111040011.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ijimenez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040011.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     //ITRTD("ESTitulo",0,"100%","","center");
       //TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     //InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
        //IFrame("iFiltro11","0","0","Filtros.js");
/******************************************************************************/
      InicioTabla("ETablaInfo",0,"0","","center","",1);
        ITRTD("ETablaST",12,"","","center");
//          cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"BÚSQUEDA DE PERSONAS":cTitulo;
        TextoSimple(cTitulo);
        //FTDTR();
          ITRTD("EEtiqueta",0,"","","","");
            TextoSimple("*Estado Solicitud:");
          FITD("EEtiqueta",0);
            //Radio(false,"iEstadoSolicitud",1,true,"","","",'onClick="fOnChange(true);"');
            Radio(true,"iEstadoSolicitud","frm.groupRadio1.value=1",true,"","","",'onClick = "fHabilitar();frm.groupRadio1.value=1"');
          FITD("EEtiquetaL",0);
            Etiqueta("Pendientes","EEtiquetaL","Pendientes");
          FITD("EEtiqueta",0);
            Radio(true,"iEstadoSolicitud",2,false,"","","",'onClick = "fHabilitar();frm.groupRadio1.value=2"');
          FITD("EEtiquetaL",0);
            Etiqueta("Resueltas","EEtiquetaL","Resueltas");
          FITD("EEtiquetaL",0);
            Radio(true,"iEstadoSolicitud",3,false,"","","",'onClick = "fDeshabilitar();frm.groupRadio1.value=3"' );
          FITD("EEtiquetaL",0);
            Etiqueta("Canceladas","EEtiquetaL","Canceladas");
          FITD("EEtiquetaL",0);
            Radio(true,"iEstadoSolicitud",4,false,"","","",'onClick = "fDeshabilitar();frm.groupRadio1.value=4"' );
          FITD("EEtiquetaL",0);
            Etiqueta("Improcedentes","EEtiquetaL","Improcedentes");
          FITD("EEtiquetaR",2);
            BtnImg("vgbuscar","lupa","fBuscaDatos();","");
        FTDTR();
        /*************************************************************/
         FTDTR();
          ITRTD("EEtiqueta",0,"","","","")
          FITD("EEtiqueta",0);
            Radio(true,"iEstadoSolicitud2",1,true,"","","",'onClick = "frm.groupRadio2.value=1"');
          FITD("EEtiquetaL",0);
            Etiqueta("Fuera de tiempo","EEtiquetaL","Fuera de tiempo");
          FITD("EEtiqueta",0);
            Radio(true,"iEstadoSolicitud2",2,false,"","","",'onClick = "frm.groupRadio2.value=2"');
          FITD("EEtiquetaL",0);
            Etiqueta("En tiempo","EEtiquetaL","En tiempo");
        /*************************************************************/

        ITR();
          TDEtiCampo(true,"EEtiqueta",0,"Inicio Periodo de Registro:","dtInicioPeriodo","",10,10,"Inicio Periodo de Registro","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0,"<bt>R.F.C. Solicitante:","cRFCSolicitante","",14,13," R.F.C. Solicitante","fMayus(this);","","",false,"EEtiquetaL",3);
          FITR();
          TDEtiCampo(true,"EEtiqueta",0,"Fin Periodo de Registro:","dtFinPeriodo","",10,10,"Fin Periodo de Registro","fMayus(this);","","",false,"EEtiquetaL",3);
        FITR();

        FTR();
      FinTabla(); //Datos de búsqueda
/******************************************************************************/
        InicioTabla("ETablaInfo",0,"0","","center","",1);
             fDefOficXUsr();
             fRequisitoModalidad();
        FinTabla();
       FTDTR();
       ITRTD("",0,"","250","center","top");
         IFrame("IListado11","95%","250","Listado.js","yes",true);
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
    Hidden("iCveUsuario");
    Hidden("cCondicion");
    Hidden("iClaveDepartamento");
    Hidden("iClaveOficina");
    Hidden("iClaveTramite");
    Hidden("iClaveModalidad");
    Hidden("groupRadio1");
    Hidden("groupRadio2");
    Hidden("dtFecha1");
    Hidden("dtFecha2");
    Hidden("tipoFiltro");
    Hidden("iEjercicioCve");
    Hidden("iNumSolicitudCve");
    Hidden("iCveEjercicio");
    Hidden("iNumSolicitud");
    Hidden("cTramite");
    Hidden("cModalidad");
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
   FRMListado.fSetTitulo("Solicitud,Fecha Reg,Favorable,Fecha Compr.,Fecha Ent.,RFC,Solicitante,Departamento,Fecha Canc.,Motivo Canc.,Canceló,");
   FRMListado.fSetCampos("21,2,3,4,12,5,22,9,13,14,15,");
   FRMListado.fSetDespliega("texto,texto,logico,texto,texto,texto,texto,texto,texto,texto,texto,");
   FRMListado.fSetAlinea("left,center,center,center,center,left,left,left,center,left,left,");
   frm.hdBoton.value="Primero";
   fCargaOficDeptoUsr();
   fCargaTramites();
   frm.groupRadio1.value = 1;
   frm.groupRadio2.value = 1;
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "";
   frm.hdNumReg.value =  100000;
   if(frm.groupRadio1.value == 1 && frm.groupRadio2.value == 1){ // Pendientes Fuera de Tiempo
       frm.hdFiltro.value = " TRARegSolicitud.dtEntrega IS NULL "+
                            " AND TRARegTramXSol.dtCancelacion IS NULL ";
       frm.cCondicion.value = "PendFT";
   }
   if(frm.groupRadio1.value == 1 && frm.groupRadio2.value == 2){ // Pendientes En Tiempo
       frm.hdFiltro.value = " TRARegSolicitud.dtEntrega IS NULL "+
                            " AND TRARegTramXSol.dtCancelacion IS NULL ";
       frm.cCondicion.value = "PendT";
   }
   if(frm.groupRadio1.value == 2 && frm.groupRadio2.value == 1){ // Resueltas Fuera de Tiempo
       frm.hdFiltro.value = " TRARegSolicitud.dtEntrega IS NOT NULL "+
                            " AND TRARegTramXSol.dtCancelacion IS NULL ";
       frm.cCondicion.value = "ResFT";
   }
   if(frm.groupRadio1.value == 2 && frm.groupRadio2.value == 2){ // Resueltas En Tiempo
       frm.hdFiltro.value = " TRARegSolicitud.dtEntrega IS NOT NULL "+
                            " AND TRARegTramXSol.dtCancelacion IS NULL ";

       frm.cCondicion.value = "ResT";
   }
   if(frm.groupRadio1.value == 3 && frm.groupRadio2.value == 0){ // Canceladas
       frm.hdFiltro.value = " TRARegTramXSol.dtCancelacion IS NOT NULL ";
       frm.cCondicion.value = "Cancel";
   }
   if(frm.groupRadio1.value == 4 && frm.groupRadio2.value == 0){ // Canceladas
       frm.hdFiltro.value = " TRARegSolicitud.dtEntrega IS NOT NULL "+
                            " AND TRARegTramXSol.dtCancelacion IS NULL ";
       frm.cCondicion.value = "Improcedente";
   }
   if(frm.iCveOficinaUsr.value != -1 && frm.iCveOficinaUsr.value != 0)
     frm.hdFiltro.value += " AND TRARegEtapasXModTram.iCveOficina = "+frm.iCveOficinaUsr.value;
   if(frm.iCveDeptoUsr.value != -1 && frm.iCveDeptoUsr.value != 0)
     frm.hdFiltro.value += " AND TRARegEtapasXModTram.iCveDepartamento = "+frm.iCveDeptoUsr.value;
   if(frm.iCveTramite.value != "" && frm.iCveTramite.value != -1)
     frm.hdFiltro.value += " AND TRARegEtapasXModTram.iCveTramite = "+frm.iCveTramite.value;
   if(frm.iCveModalidad.value != "" && frm.iCveModalidad.value != -1)
     frm.hdFiltro.value += " AND TRARegEtapasXModTram.iCveModalidad = "+frm.iCveModalidad.value;

   if(frm.cRFCSolicitante.value != ''){
      frm.hdFiltro.value = frm.hdFiltro.value +
                            " AND GRLPersona.cRFC = '"+frm.cRFCSolicitante.value+"'";
   }
   frm.dtFecha1.value = frm.dtInicioPeriodo.value;
   frm.dtFecha2.value = frm.dtFinPeriodo.value;
  return fEngSubmite("pgTRARegSolicitud1A.jsp","Listado");
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   fResOficDeptoUsr(aRes,cId,cError);
   fResTramiteModalidad(aRes,cId,cError);


   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }

   if(cId == "Listado" && cError==""){
     var antiEjercicio = 0;
     var antiNumSolicitud = 0;
     var aLista = new Array();
     var iLista = 0;
     for (i=0;i<aRes.length;i++){
        if(aRes[i][0] != antiEjercicio || antiNumSolicitud != aRes[i][1]){
           antiEjercicio = aRes[i][0];
           antiNumSolicitud = aRes[i][1];
           aLista[iLista] = new Array();
           aLista[iLista][0] = aRes[i][0];
           aLista[iLista][1] = aRes[i][1];
           aLista[iLista][2] = aRes[i][2];
           aLista[iLista][3] = aRes[i][3];
           aLista[iLista][4] = aRes[i][4];
           aLista[iLista][5] = aRes[i][5];
           aLista[iLista][6] = aRes[i][6];
           aLista[iLista][7] = aRes[i][7];
           aLista[iLista][8] = aRes[i][8];
           aLista[iLista][9] = aRes[i][9];
           aLista[iLista][10] = aRes[i][10];
           aLista[iLista][11] = aRes[i][11];
           aLista[iLista][12] = aRes[i][12];
           aLista[iLista][13] = aRes[i][13];
           aLista[iLista][14] = aRes[i][14];
           aLista[iLista][15] = aRes[i][15];
           aLista[iLista][21] = aRes[i][1] + "/" + aRes[i][0];
           aLista[iLista][22] = aRes[i][6] + " " + aRes[i][7]+ " " + aRes[i][8];
           iLista++;
        }
     }
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aLista);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
   }
   if(cId == "CIDOficinaDeptoXUsrTodas" && cError == "")
     fFillSelect(frm.iCveDeptoUsr,aRes,false,frm.iCveDeptoUsr.value,0,2);

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
    frm.cTramite.value = aDato[10];
    frm.cModalidad.value = aDato[11];
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
   if(fComparaFecha(frm.dtInicioPeriodo.value,frm.dtFinPeriodo.value, true) == false && frm.dtInicioPeriodo.value != '' && frm.dtFinPeriodo.value != ''){
       fAlert("El Inicio del periodo es mayor al Fin del periodo");
       bandera = false;
   }

   if (fValidaTodo() == true && bandera == true)
      fNavega();

 }
 function fDeshabilitar(){
  frm.groupRadio2.value=0;
  fEnableRadio(frm.iEstadoSolicitud2, false);
  fSetRadioValue(frm.iEstadoSolicitud2, 0);
 }

 function fHabilitar(){
   fEnableRadio(frm.iEstadoSolicitud2, true);
   if((frm.groupRadio1.value == 3 || frm.groupRadio1.value == 4) && frm.groupRadio2.value == 0){
      fSetRadioValue(frm.iEstadoSolicitud2, 1);
      frm.groupRadio2.value = 1;
   }
 }
 function fGetiCveEjercicio(){
  return  frm.iCveEjercicio.value;
}
function fGetiNumSolicitud(){
  return  frm.iNumSolicitud.value;
}
function fGetcTramite(){
  return frm.cTramite.value;
}
function fGetcModalidad(){
  return frm.cModalidad.value;
}
function fGetcOficina(){
  return frm.iCveOficinaUsr.options[frm.iCveOficinaUsr.selectedIndex].text;
}

function fDefOficXUsr(lTodas,lRenglonIntermedio,lSeleccione){
  if(!lTodas)
    lTodas = false;
  if(!lSeleccione)
    lSeleccione = false;
  //Necesario definir los campos ocultos  Hidden("fResultado "); y Hidden("hdLlave");
  var cTx;
  cTx = ITRTD("EEtiquetaC",0,"100%","20","center")+
      InicioTabla("",0,"","","center")+
        ITR()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Oficina:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle");
  cTx += Select("iCveOficinaUsr","fOficinaUsrOnChangeValidaDeptos();");
  cTx+=   FTD();
  if(lRenglonIntermedio)
    cTx += FITR();
  cTx +=  ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Departamento:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveDeptoUsr","if(fDeptoUsrOnChange)fDeptoUsrOnChange();")+
          FTD()+
        FTR()+
      FinTabla();
     cTx += InicioTabla("",0,"","","center")+
        ITR()+
          TDEtiCheckBox("EEtiqueta",0," Mostrar todos los Departamentos:","lMuestraDeptoBOX","0",true,"Muestra todos los departamentos asignados a la oficina seleccionada","","onClick=fCargaDeptos();") +
        FTR()+
      FinTabla();
    cTx += FTDTR();
  return cTx;
}
function fCargaDeptos(){
  if(frm.lMuestraDeptoBOX.checked)
    fCargaTodosDeptos();
  else
    fOficinaUsrOnChange(false,false);
}
function fCargaTodosDeptos(){
  frm.hdLlave.value = "GRLDeptoXOfic.iCveOficina,GRLDeptoXOfic.iCveDepartamento";
  frm.hdSelect.value = "SELECT GRLDeptoXOfic.iCveDepartamento, "+
                           "GRLDepartamento.cDscDepartamento, GRLDepartamento.cDscBreve as cDscBreveDepto "+
                           "FROM GRLDeptoXOfic "+
                           "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLDeptoXOfic.iCveDepartamento "+
                           "WHERE GRLDeptoXOfic.iCveOficina = " + frm.iCveOficinaUsr.value +
                           " and  GRLDeptoXOfic.iCveDepartamento >= 0 "+
                           "ORDER BY cDscBreveDepto";
  frm.hdNumReg.value = 100000;
  fEngSubmite("pgConsulta.jsp","CIDOficinaDeptoXUsrTodas");
}

function fOficinaUsrOnChangeValidaDeptos(){
  fCargaDeptos();
}

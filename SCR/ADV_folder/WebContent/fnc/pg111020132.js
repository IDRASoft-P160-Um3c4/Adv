// MetaCD=1.0
 // Title: pg111020132.js
 // Description: JS "Catálogo" de la entidad TRAOpnEntTramite
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResReporte = new Array();
 var lBandera = false;
 var lNoVigente = false;
 var lBanderaLiga = false;
 var lBanderaDependencia = false;
 var lEjecuta = true;
 var lNuevoLisA = false;
 var lCargaPagina = true;
 var cPermisoPag = "";
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
   cPaginaWebJS = "pg" + iNDSADM + "1020132.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"SEGUIMIENTO A OPINIONES":cTitulo;
   fSetWindowTitle();
   cPermisoPag = fGetPermisos(cPaginaWebJS);
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro32","95%","34","Filtros.js");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicioSolicitud","",10,10,"Año","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitud","",20,20,"Número de Solicitud","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cTramite","",45,45,"Trámite","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cModalidad","",45,45,"Modalidad","fMayus(this);");
           FITR();
        FinTabla();
       FTDTR();
       ITRTD("",0,"","100","center","top");
         IFrame("IListado32","95%","100","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
       InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("",0,"","","center");
             IFrame("IListado32A","100%","60","Listado.js","yes",true);
           FTDTR();
         FinTabla();

         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);

           ITR();
              TDEtiSelect(false,"EEtiqueta",0,"Dependencia Externa:","iCveOpinionEntidad","fOnClickDependencia();","",5);
           FITR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Dirigido a:","cOpnDirigidoA","",50,75,"Número de Oficio","fMayus(this);","","","","",2);
           FITR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Puesto:","cPtoOpinion","",50,75,"Número de Oficio","fMayus(this);","","","","",2);
           FITR();
           ITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Observaciones / Objeto:",50,2,"cObsesSegto","","cObsesSegto","","fMayus(this);",'onkeydown="fMxTx(this,500);"',"","",true,"",3);
//              TDEtiAreaTexto(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo,lContador,cEstiloCM,iColExtiendeCM);
           FITR();
           //TDEtiCampo(false,"EEtiqueta",0,"No. de Oficio:","cNumOficio","",20,20,"Número de Oficio","fMayus(this);");
              Hidden("cNumOficio");
              TDEtiCampo(false,"EEtiqueta",0,"No. de Oficio:","cOficio","",20,20,"Número de Oficio","fMayus(this);");
              TDEtiCheckBox("EEtiqueta",0,"¿Es una Contestación?:","lEsContestacionBOX","1",true,"EsContestacion","","onClick=fActivaCampos();");//'onkeydown="fMxTx(this,500);"',"","",false,"",3);
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Siglas:","cSiglas","",30,30,"Siglas de los responsables","fMayus(this);");
           FITR();
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("CONTESTACIONES EXTERNAS");
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"No. Oficialía de Partes de la Contestación:","cNumOficialiaPartesCon","",15,15,"Número de Oficialía de Partes de la Contestación","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Fecha Recepción :","dtRecepcionCon","",10,10,"Fecha Recepción Oficialia","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"No. Oficio de la Contestación:","cNumOficioCon","",30,30,"Número de Oficio de la Contestación","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Fecha Oficio de la Contestación:","dtOficioCon","",10,10,"Fecha del Oficio de la Contestación","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Contestación Dirigida a:","cDirigidoACon","",30,75,"Contestación","fMayus(this);","","","","",2);
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Titular:","cTitularFirmaCon","",30,75,"Titular que Firma la Contestación","fMayus(this);","","","","",2);
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Asunto:","cAsuntoCon","",30,75,"Asunto de la Contestación","fMayus(this);","","","","",2);
           FITR();

           /*ITD("EEtiquetaC",2,"","","center");
              Liga("Oficios Referenciados","fOficios();","Oficios Referenciados");
           FTD();*/

           ITD("EEtiquetaC",2,"","","center");
              Liga("Adjuntar/Consultar Documentos","fDocumentos();","Abre página de Adjuntar Documentos");
           FTD();

         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel32","95%","34","Paneles.js");

       FTDTR();
       Hidden("lEsContestacion");
       Hidden("hdLlave");
       Hidden("hdSelect");
       Hidden("iCveTramite");
       Hidden("iCveModalidad");
       Hidden("iFolioReferenInterno");
       Hidden("iCveSegtoEntidad");
       Hidden("hdCveOpinionEntidad");
       Hidden("Temp");
       Hidden("hdCveModulo");
       Hidden("hdNumReporte");
       Hidden("Query");
       Hidden("cFiltroAdicional");


     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel32");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(true);
   if(window.parent.lConsulta)
     FRMPanel.fShow(",");
   else
     FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado32");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Num. Control, Dependencia Externa,Contestación,");
   FRMListado.fSetCampos("6,4,8,");
   FRMListado.fSetAlinea("center,left,center,");
   FRMListado.fSetDespliega("texto,texto,logico,");
   FRMListado.fSetSelReg(1);
   FRMListadoA = fBuscaFrame("IListado32A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Número de Oficio, Fecha del Oficio,");
   FRMListadoA.fSetCampos("5,7,");
   FRMListadoA.fSetAlinea("left,center,");
   FRMListadoA.fSetSelReg(2);
   FRMFiltro = fBuscaFrame("IFiltro32");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
   fDisabled();
   frm.hdBoton.value="Primero";

 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  " TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD = " +frm.iEjercicioSolicitud.value+ " AND TRAOPNENTTRAMITE.INUMSOLICITUD = " +frm.iNumSolicitud.value;
   frm.hdOrden.value =  " GRLSEGTOENTIDAD.ICVESEGTOENTIDAD";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgGRLSegtoEntidad.jsp","Listado");
   //fCargaListadoA();
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
   if(cError!="")
     FRMFiltro.fSetNavStatus("Record");

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;


     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     if(frm.Temp.value!="")
       fReposicionaListado(FRMListado,"7", frm.Temp.value);
     else
      fReposicionaListado(FRMListado,"7", 0);
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

   if(cId == "ListadoA" && cError==""){
     aResTemp = fCopiaArreglo(aRes);
     for(var i =0; i<aResTemp.length;i++){
       if(aResTemp[i][6]<10)
         aRes[i][6] = "00"+aResTemp[i][6];

       if(aRes[i][6]>=10 & aResTemp[i][6]<100)
         aRes[i][6] = "0"+aResTemp[i][6];

     aRes[i][5] = aResTemp[i][5]+"."+aResTemp[i][6] +"/"+ aResTemp[i][2];
     }

     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);

     if(frm.hdCveOpinionEntidad.value > 0)
       fDatosReporte();

    lEjecuta = true;
   }

   if(cId == "GRLOpinionEntidad" && cError==""){
     if(aRes.length > 0)
        lBandera = true;

     aResOpinion = fCopiaArreglo(aRes);
     aResValidos = fCopiaArregloBidim(aRes);
     fFillSelect(frm.iCveOpinionEntidad,aResOpinion,true,frm.iCveOpinionEntidad.value,0,2);
     fNavega();

   }

   if(cId == "Reporte" && cError=="")
     aResReporte = fCopiaArregloBidim(aRes);


 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
   if(lBandera == true){
       lNuevoLisA = true;
       frm.lEsContestacion.value = 0;
       fSelectSetIndexFromValue(frm.iCveOpinionEntidad, -1);
       FRMPanel.fSetTraStatus("UpdateBegin");
       fBlanked("iCveTramite,iCveModalidad,iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cOpnDirigidoA,cPtoOpinion,");
       fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,cNumOficialiaPartesCon,dtRecepcionCon,dtOficioCon,cNumOficioCon,cDirigidoACon,cTitularFirmaCon,cAsuntoCon,","--");
       FRMListado.fSetDisabled(true);
       lBanderaLiga = true;
   }else{
     fAlert("No existe una Dependencia Externa configurada");
     lBanderaLiga = false;
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
  for(var i=0;i<aResValidos.length;i++){
    if(aResValidos[i][0] == frm.iCveOpinionEntidad.value && aResValidos[i][5] == 0){
      lNoVigente = true;
      break;
    }
  }

  if(lNoVigente == false){
    lBanderaLiga = false;
    frm.Temp.value = frm.cObsesSegto.value;
    frm.lEsContestacionBOX.checked==true?frm.lEsContestacion.value=1:frm.lEsContestacion.value=0;
     if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
          lEjecuta = false;
          lNuevoLisA = false;
       }
    }
  }else{
    fAlert("La dependencia seleccionada no está vigente");
    lNoVigente = false;
    lBanderaLiga = true;
    return;
  }
}
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    frm.Temp.value = frm.cObsesSegto.value;
    frm.lEsContestacionBOX.checked==true?frm.lEsContestacion.value=1:frm.lEsContestacion.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
         lBanderaLiga = false;
         lBanderaDependencia = false;
         lEjecuta = false;
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
if (frm.lEsContestacion.value!=0){
    lBanderaLiga = true;
    lBanderaDependencia = true;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,iCveOpinionEntidad,","--");
    FRMListado.fSetDisabled(true);
    } else {
    lBanderaLiga = true;
    lBanderaDependencia = true;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveOpinionEntidad,iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,lEsContestacionBOX,cNumOficialiaPartesCon,dtRecepcionCon,dtOficioCon,cNumOficioCon,cDirigidoACon,cTitularFirmaCon,cAsuntoCon,","--");
    FRMListado.fSetDisabled(true);
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    lBanderaLiga = false;
    lBanderaDependencia = false;
    lNuevoLisA = false;
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Add,Mod,");
    else
      FRMPanel.fSetTraStatus("AddOnly");

    fDisabled(true);
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
 }
 //se llama la funcion para que sirvan los reportes
 function fReporte(){
   frm.hdCveModulo.value = "";
   frm.hdNumReporte.value = "";
   frm.Query.value = "";

 if(frm.cFiltroAdicional.value != "")
     cFiltrosRep=frm.iEjercicioSolicitud.value+","+
                 frm.iNumSolicitud.value+","+
                 frm.hdCveOpinionEntidad.value+","+
                 frm.iCveSegtoEntidad.value+","+
                 frm.cFiltroAdicional.value+
                 cSeparadorRep;
  else
     cFiltrosRep=frm.iEjercicioSolicitud.value+","+
                 frm.iNumSolicitud.value+","+
                 frm.hdCveOpinionEntidad.value+","+
                 frm.iCveSegtoEntidad.value+
                 cSeparadorRep;


   for(cont=0;cont < aResReporte.length;cont++){
    if(frm.hdCveModulo.value == ""){
    	frm.hdCveModulo.value = aResReporte[cont][2];
    }else{
  	frm.hdCveModulo.value = frm.hdCveModulo.value + "," + aResReporte[cont][2];
    }
    if(frm.hdNumReporte.value == ""){
    	frm.hdNumReporte.value = aResReporte[cont][3];
    }else{
  	frm.hdNumReporte.value = frm.hdNumReporte.value + "," + aResReporte[cont][3];
      }
    if(frm.Query.value == "" ){
        frm.Query.value = cFiltrosRep;
    }else{
        frm.Query.value = frm.Query.value + "" + cFiltrosRep;
    }
   }

  cClavesModulo=frm.hdCveModulo.value+",";
  cNumerosRep=frm.hdNumReporte.value+",";
  cFiltrosRep=frm.Query.value;

  if(frm.lEsContestacion.value == 1 || FRMListadoA.fGetLength()>0){
    fAlert("Ya existe un oficio asignado para éste registro y/o no se permite esta operación para Contestaciones");
  }else{
     fReportes();
  }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.iCveOpinionEntidad.value = aDato[5];
    frm.hdCveOpinionEntidad.value = aDato[5];
    frm.iCveSegtoEntidad.value = aDato[6];
    fCargaListadoA();
    frm.cObsesSegto.value = aDato[7];

    frm.lEsContestacion.value = aDato[8];
    if (frm.lEsContestacion.value==1) frm.lEsContestacionBOX.checked=true;
       else frm.lEsContestacionBOX.checked=false;


    frm.cNumOficialiaPartesCon.value = aDato[14];
    frm.dtRecepcionCon.value = aDato[13];
    frm.dtOficioCon.value = aDato[12];
    frm.cNumOficioCon.value = aDato[15];
    frm.cDirigidoACon.value = aDato[9];
    frm.cTitularFirmaCon.value = aDato[11];
    frm.cAsuntoCon.value = aDato[10];

    frm.cOpnDirigidoA.value = aDato[16];
    frm.cPtoOpinion.value = aDato[17];
    frm.cSiglas.value = aDato[20];
    frm.cOficio.value = aDato[21];

 }

  function fSelReg2(aDato){
    frm.cNumOficio.value = aDato[5];
  }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    cMsg = fValElements("lEsContestacion,cSiglas,");

    if(frm.iCveOpinionEntidad.value == -1)
      if(cMsg == '')
        cMsg = 'Favor de Seleccionar una Dependencia Externa';
      else
        cMsg += '\nFavor de Seleccionar una Dependencia Externa';

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
//Recibe los valores de la pagina anterior
function fRecibeValores(){
  if(!lCargaPagina)
     return;

  frm.iEjercicioSolicitud.value = top.opener.fGetiEjercicio();
  frm.iNumSolicitud.value = top.opener.fGetiNumSolicitud();
  frm.cTramite.value = top.opener.fGetcDscTramite();
  frm.cModalidad.value = top.opener.fGetcDscModalidad();
  frm.iCveTramite.value = top.opener.fGetCveTramite();
  frm.iCveModalidad.value = top.opener.fGetCveModalidad();
  frm.iFolioReferenInterno.value = 0;

  if(top.opener.fGetFiltroAdicional)
     frm.cFiltroAdicional.value = top.opener.fGetFiltroAdicional();
  else
     frm.cFiltroAdicional.value = "";

  fCargaDependencia();
  lCargaPagina = false;

}

//Liga
function fOficios(){
   if (FRMListado.fGetLength()>0 && lBanderaLiga == false){
     fAbreSubWindowPermisos("pg111020140","900","400");
   } else fAlert("Debe de existir algún registro y/o no se puede realizar esta operación con una transacción abierta");
}

//Funcion que activa campos dependiendo de la acción del checkbox
function fActivaCampos(){
   if(frm.lEsContestacion.value == 1){
     fBlanked("iCveTramite,iCveModalidad,iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,cObsesSegto,cOpnDirigidoA,cPtoOpinion,");
     fDisabled(true,"cObsesSegto,lEsContestacionBOX,iCvePersona,cOpnDirigidoA,cPtoOpinion,iCveOpinionEntidad,");
     FRMListado.fSetDisabled(true);
     frm.lEsContestacion.value = 0;
   }else{
     fBlanked("iCveTramite,iCveModalidad,iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cObsesSegto,lEsContestacionBOX,cNumOficio,cOpnDirigidoA,cPtoOpinion,");
     if(lBanderaDependencia = false)
        fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,","--");
     else
        fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,iCveOpinionEntidad,","--");
     FRMListado.fSetDisabled(true);
     frm.lEsContestacion.value = 1;
   }
}

//Función que llena el select de dependencia externa.
function fCargaDependencia(){
      frm.hdLlave.value =   "GRLOpinionEntidad.iCveOpinionEntidad";
      frm.hdSelect.value =  "SELECT GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD, GRLOPINIONENTIDAD.ICVEPERSONA, GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO as NOMBRE, " +
      			     " GRLOPINIONENTIDAD.COPINIONDIRIGIDOA, GRLOPINIONENTIDAD.CPUESTOOPINION, "+
                             "GRLOPINIONENTIDAD.lVigente "+
                            "FROM GRLOPINIONENTIDAD " +
                            "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEDOMICILIO = GRLOPINIONENTIDAD.ICVEDOMICILIO AND GRLDOMICILIO.ICVEPERSONA = GRLOPINIONENTIDAD.ICVEPERSONA " +
                            "JOIN GRLPERSONA ON GRLPERSONA.ICVEPERSONA = GRLDOMICILIO.ICVEPERSONA " +
                            "WHERE GRLOpinionEntidad.ICVETRAMITE = " +frm.iCveTramite.value + " AND GRLOPINIONENTIDAD.ICVEMODALIDAD = " +frm.iCveModalidad.value;
    frm.hdNumReg.value = 100000;
    fEngSubmite("pgConsulta.jsp","GRLOpinionEntidad");
}


function fCargaListadoA(){
  if(lEjecuta){

  if(lNuevoLisA)
     frm.iCveSegtoEntidad.value = -1;

  if(frm.iCveSegtoEntidad.value == null || frm.iCveSegtoEntidad.value == "")
     frm.iCveSegtoEntidad.value = 0;

      frm.hdLlave.value =   "GRLFolioXSegtoEnt.iCveSegtoEntidad, GRLFolioXSegtoEnt.iConsecutivoSegtoRef";
      frm.hdSelect.value =  "SELECT FOSEG.ICVESEGTOENTIDAD, FOSEG.ICONSECUTIVOSEGTOREF, FOSEG.IEJERCICIOFOLIO, FOSEG.ICVEOFICINA, FOSEG.ICVEDEPARTAMENTO, " +
      			     "FOSEG.CDIGITOSFOLIO, FOSEG.ICONSECUTIVO, FO.DTASIGNACION, FO.CDIRIGIDOA, FO.CASUNTO " +
                             "FROM GRLFOLIOXSEGTOENT FOSEG " +
                             "JOIN GRLFOLIO FO ON FO.IEJERCICIO = FOSEG.IEJERCICIOFOLIO " +
                             "AND FO.ICVEOFICINA = FOSEG.ICVEOFICINA " +
                             "AND FO.ICVEDEPARTAMENTO = FOSEG.ICVEDEPARTAMENTO " +
                             "AND FO.CDIGITOSFOLIO = FOSEG.CDIGITOSFOLIO " +
                             "AND FO.ICONSECUTIVO = FOSEG.ICONSECUTIVO "+
                             "WHERE FOSEG.ICVESEGTOENTIDAD = "+frm.iCveSegtoEntidad.value+
                             " and FOSEG.LFOLIOREFERENINTERNO = 0" +
                             " ORDER BY FO.DTASIGNACION DESC,FOSEG.ICONSECUTIVO ASC ";


      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","ListadoA");

  }
}

function fDatosReporte(){
      frm.hdLlave.value =   "GRLReporteXOpinion";
      frm.hdSelect.value =  "select ICVEOPINIONENTIDAD, ICVESISTEMA, ICVEMODULO, INUMREPORTE "+
                            "from GRLREPORTEXOPINION "+
                            "where ICVEOPINIONENTIDAD = "+frm.hdCveOpinionEntidad.value;


      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","Reporte");
}

function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError){
  fCargaListadoA();
}

function fGetCveSegtoEntidad(){
   return frm.iCveSegtoEntidad.value;
}

function fOnClickDependencia(){
  for(i=0;i < aResOpinion.length;i++)
    if(frm.iCveOpinionEntidad.value == aResOpinion[i][0]){
      frm.cOpnDirigidoA.value = aResOpinion[i][3];
      frm.cPtoOpinion.value = aResOpinion[i][4];
    }

  if(frm.iCveOpinionEntidad.value == -1){
    frm.cOpnDirigidoA.value = '';
    frm.cPtoOpinion.value = '';
  }
}

/*Funciones para adjuntar documentos*/
function fDocumentos(){
  if(lBanderaLiga){
   fAlert("No se puede realizar esta acción mientras exista una transacción abierta.");
   return;
  }

  if(FRMListado.fGetLength() > 0){
    fRegDocumentos();
  }else
    fAlert("Favor de dar de alta un nuevo registro para poder utilizar esta opción.");
}

/*Funciones que necesita el Content Manager*/
 function fGetParametros(){
   var aParametros = new Array();
   aParametros[0] = "unico de dueño doc"; // deprecated.
   aParametros[1] = "Módulo de Opiniones";  //Descripcion del Proceso
   aParametros[2] = "1";    // deprecated.
   aParametros[3] = "Tipo 1"; // deprecated.
   aParametros[4] = "TRADoctoXOpinion"; // Nombre de la Tabla.
   aParametros[5] = "iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad"; //Nombre el Campo.
   aParametros[6] = "25"; // No. de Modulo.

   if(cPermisoPag == 1)
     aParametros[7] = "Escritura";   // Modo de la Pagina Escritura o Consulta.
   else
     aParametros[7] = "Consulta";   // Modo de la Pagina Escritura o Consulta.

   if(window.parent.lConsulta)
     aParametros[7] = "Consulta";

   return aParametros;
 }

 function fGetArregloCampos(){
   var aCampos = new Array();
   aCampos[0] = "iEjercicioSolicitud";
   aCampos[1] = "iNumSolicitud";
   aCampos[2] = "iCveOpinionEntidad";
   aCampos[3] = "iCveSegtoEntidad";
   return aCampos;
 }

 function fGetArregloDatos(){
   var aDatos = new Array();
   aDatos[0] = frm.iEjercicioSolicitud.value; //Nombres de los Valores de los Campos.
   aDatos[1] = frm.iNumSolicitud.value; //Nombres de los Valores de los Campos.
   aDatos[2] = frm.hdCveOpinionEntidad.value; //Nombres de los Valores de los Campos.
   aDatos[3] = frm.iCveSegtoEntidad.value; //Nombres de los Valores de los Campos.
   return aDatos;
 }

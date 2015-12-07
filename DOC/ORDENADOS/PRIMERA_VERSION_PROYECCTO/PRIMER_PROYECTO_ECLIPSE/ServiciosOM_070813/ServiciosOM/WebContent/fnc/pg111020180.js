 // MetaCD=1.0
 // Title: pg111020131.js
 // Description: JS "Catálogo" de la entidad TRAOpnEntTramite para contestacion de opiniones
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aOpinionEntidad;
 var aResReporte = new Array();
 var aResVal = new Array();
 var lBandera = false;
 var iPID;
 var lEjecuta = true;
 var lNuevoLisA = false;

 var lExisteOficDep = false;
 var aResLisA = new Array();
 var cFiltroNavega = "";
 var lTransaccion = false; // para no dejar buscar mientras se encuentre una transaccion abierta
 var cPermisoPag;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg" + iNDSADM + "1020180.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"SEGUIMIENTO A OPINIONES":cTitulo;

   cPermisoPag = fGetPermisos(cPaginaWebJS);
   fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro31","95%","34","Filtros.js");
/*Tabla de Filtro*/
         InicioTabla("ETablaInfo",0,"50%","","","",1);
           FITR();
           ITR();
              ITD("EEtiqueta","","","","LEFT","LEFT");
              TextoSimple("Buscar por:");
              ITD("EEtiqueta","","","","RIGHT","RIGHT");
              Radio(true,"lBusqueda",1,true,"","","",'onClick = "fHabilitarBusqueda();"');
              ITD("EEtiquetaL","","","","LEFT","LEFT");
              TextoSimple("Número de Oficio");
              ITD("EEtiqueta",1,"","","CENTER","CENTER");
              Radio(true,"lBusqueda",1,false,"","","",'onClick = "fHabilitarBusqueda();"');
              ITD("EEtiquetaL","","","","LEFT","LEFT");
              TextoSimple("Solicitud");
           FITR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Número de Oficio:","cNumOficioFil","",60,60,"Número de Oficio","fMayus(this);","","","","",5);
           FITR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicioFil","",4,4,"Ejercicio","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","cSolicitudFil","",20,20,"Número de Solicitud","fMayus(this);","","","","",5);
              ITD("",1,"","","center");
              BtnImg("Buscar","lupa","fEjecutafNavega();","Buscar");
           FITR();
           FITR();
        FinTabla();
/*Fin Tabla Filtro*/
/*Inicio Tabla Campos Informativos*/
        InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicioSolicitudL","",10,10,"Año","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitudL","",20,20,"Número de Solicitud","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cTramite","",45,45,"Año","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cModalidad","",45,45,"Número de Solicitud","fMayus(this);");
           FITR();
        FinTabla();
       FTDTR();
       ITRTD("",0,"","100","center","top");
         IFrame("IListado31","95%","120","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
       InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           FTDTR();
           ITRTD("",0,"","","center");
             IFrame("IListado31A","100%","120","Listado.js","yes",true);
           FTDTR();
         FinTabla();
/*Fin Tabla Campos Informativos*/
         TextoSimple("");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           ITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Observaciones:",50,2,"cObsesSegto","","OpnDirigidoA","","fMayus(this);",'onkeydown="fMxTx(this,100);"');
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Número de Oficio:","cNumOficio","",20,20,"Número de Oficio","fMayus(this);");
           FITR();
           ITD("EEtiquetaC",2,"","","center");
              Liga("Oficios Referenciados","fOficios();","Oficios Referenciados");
           FTD();
           ITD("EEtiquetaC","1","","","left");
              Liga("Adjuntar/Consultar Documentos","fDocumentos();","Abre página de Adjuntar Documentos");
           FTD();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel31","95%","34","Paneles.js");

       FTDTR();
       Hidden("lEsContestacion");
       Hidden("hdLlave");
       Hidden("Temp");
       Hidden("hdSelect");
       Hidden("iCveTramite");
       Hidden("iCveModalidad");
       Hidden("iFolioReferenInterno");
       Hidden("iCveSegtoEntidad");
       Hidden("hdCveOpinionEntidad");
       Hidden("hdCveModulo");
       Hidden("hdNumReporte");
       Hidden("Query");
       Hidden("Select");
       Hidden("cFiltroAdicional","");
       Hidden("idUser",fGetIdUsrSesion());
       Hidden("hdCveOficina");//para USr*oficina
       Hidden("hdCveDepto");//para usr*depto
       Hidden("hdCveOficinaOficio"); // para oficio
       Hidden("hdCveDeptoOficio");//para oficio
       Hidden("hdEjercicioOficio");//para oficio
       Hidden("hdDigitosFolio");//para oficio
       Hidden("hdConsecutivoOficio");//para oficio
       Hidden("hdOpinionEntidad",""); //para agrupar
       Hidden("iEjercicioSolicitud");//para filtrar con el oficio
       Hidden("iNumSolicitud");//para filtrar con el oficio
       Hidden("hdCveSegtoEntidad");//para reposicionar
       Hidden("iCveOficinaUsr");//para asignar el valor en el sel reg por que asi estan definidas las variables en un JSP y DAO que se va a reutilizar que se usan para guardar y aztualizar
       Hidden("iCveDeptoUsr");//para asignar el valor en el sel reg por que asi estan definidas las variables en un JSP y DAO que se va a reutilizar que se usan para guardar y aztualizar
       Hidden("iCveSegtoContestacion");//Para saber quien es el destinatario del oficio en el reporte
       Hidden("iConsecutivoSegto");//Filtro
       Hidden("hdCveDeptoPadre","0");

     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel31");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(true);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado31");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Num.Control,Oficina,Departamento,Contestación,");
   FRMListado.fSetCampos("0,4,5,3,");
   FRMListado.fSetSelReg(1);
   FRMListado.fSetAlinea("center,left,left,center,");
   FRMListado.fSetDespliega("texto,texto,texto,logico,");
   FRMListadoA = fBuscaFrame("IListado31A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetSelReg(2);
   FRMListadoA.fSetTitulo("Número de Oficio, Fecha del Oficio,");
   FRMListadoA.fSetCampos("5,7,");
   FRMListadoA.fSetAlinea("left,center,");
   FRMFiltro = fBuscaFrame("IFiltro31");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
   fDisabled(true,"cNumOficioFil,lBusqueda,");
   frm.hdBoton.value="Primero";
   fUserxOfiDepto();

//   fRecibeValores();


 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   if(frm.lBusqueda[0].checked == true){
     cFiltro = " and TRA.IEJERCICIOSOLICITUD = "+frm.iEjercicioSolicitud.value+
		" and TRA.INUMSOLICITUD = "+frm.iNumSolicitud.value;
   }
   frm.hdFiltro.value = " FXS.LFOLIOREFERENINTERNO = 0" + cFiltro + " and OPE.ICVEOPINIONENTIDAD IN ("+frm.hdOpinionEntidad.value+")";
   frm.hdOrden.value =  " icveseg";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   fEngSubmite("pgTRAOpnEntTramiteA.jsp","Listado");
//   fCargaListadoA();

 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iCveDeptoPadre,iEjercicioOficio,iCveOficinaOficio,iCveDeptoOficio,cDigitosFolio,iConsecutivoOficio){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
//   if(cError!="")
//     FRMFiltro.fSetNavStatus("Record");

   if(cId == "DatosListado" && cError == ""){
     aResDesplegar = fCopiaArregloBidim(aRes);
     if(aRes.length > 0){
       frm.iEjercicioSolicitud.value = aRes[0][8];
       frm.iNumSolicitud.value = aRes[0][9];
       fEncuentraOficDepto(frm.hdCveOficina.value,frm.hdCveDepto.value);
     }else{
       fAlert("No existen Oficios con el filtro proporcionado.");
       var aResTemp = new Array();
       FRMListado.fSetListado(aResTemp);
       FRMListado.fShow();
       fCancelar();
     }
   }

   if(cId == "Listado" && cError==""){
     aResLisADesplegar = fCopiaArregloBidim(aRes);
/*
     for(var i = 0; i < aRes.length; i++)
       if(frm.hdOpinionEntidad.value == aRes[i][7])
        aResLisADesplegar[aResLisADesplegar.length] = aRes[i];
*/
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
     if(frm.hdCveSegtoEntidad.value != "" && frm.lBusqueda[0].checked == true)
       fReposicionaListado(FRMListado, "0", frm.hdCveSegtoEntidad.value);
     else
       fReposicionaListado(FRMListado, "0", frm.iCveSegtoEntidad.value);


     if(frm.Temp.value != "")
       fReposicionaListado(FRMListado, "6", frm.Temp.value);

     frm.Temp.value = "";
     frm.hdCveSegtoEntidad.value = "";

   }

   if(cId == "UserxOfi" && cError==""){
     if(aRes.length > 0){
       frm.hdCveOficina.value = aRes[0][0];
       frm.hdCveDepto.value = aRes[0][1];
       frm.hdCveDeptoPadre.value = aRes[0][1];
     }else{
       fAlert("El usuario no tiene Oficina y Departamento asignado. Favor de verificarlo con el administrador");
       fEngSubmite("pgDelUsrSes.jsp","CerrarSesion");
       fSalirTotal();
     }
   }

   if(cId == "DeptoPadre" && cError==""){
     if(frm.hdCveDeptoPadre.value != iCveDeptoPadre){
       frm.hdCveDeptoPadre.value = iCveDeptoPadre;
       fEncuentraOficDepto(frm.hdCveOficina.value,frm.hdCveDeptoPadre.value);
     }else{
       fAlert("No existen número de Oficio relacionados a la Dirección del Área.");
       var aResTemp = new Array();
       FRMListado.fSetListado(aResTemp);
       FRMListado.fShow();
       fCancelar();
     }
   }

   if(cId == "SeparaOficio" && cError == ""){
     frm.hdCveOficinaOficio.value = iCveOficinaOficio;
     frm.hdCveDeptoOficio.value = iCveDeptoOficio;
     frm.hdEjercicioOficio.value = iEjercicioOficio;
     frm.hdDigitosFolio.value = cDigitosFolio;
     frm.hdConsecutivoOficio.value = iConsecutivoOficio;
     cFiltro = " and FXS.IEJERCICIOFOLIO = "+frm.hdEjercicioOficio.value +
		" and FXS.ICVEOFICINA = "+frm.hdCveOficinaOficio.value+
		" and FXS.ICVEDEPARTAMENTO = "+frm.hdCveDeptoOficio.value+
		" and FXS.CDIGITOSFOLIO like '"+frm.hdDigitosFolio.value +"'"+
		" and FXS.ICONSECUTIVO = "+frm.hdConsecutivoOficio.value;
     fCargaDatosListado();
   }


   if(cId == "ListadoA" && cError==""){
     aResTemp = fCopiaArreglo(aRes);
     for(var i =0; i<aResTemp.length;i++){
       if(aResTemp[i][6]<10){
         aRes[i][6] = "00"+aResTemp[i][6];
       }
       if(aRes[i][6]>=10 & aResTemp[i][6]<100){
         aRes[i][6] = "0"+aResTemp[i][6];
       }
     aRes[i][5] = aResTemp[i][5]+"."+aResTemp[i][6] +"/"+ aResTemp[i][2];
     }

     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);

     if(frm.cNumOficioFil.value != "")
     fReposicionaListado(FRMListadoA, "5", frm.cNumOficioFil.value);

     if(frm.hdCveOpinionEntidad.value > 0)
       fDatosReporte();

     lEjecuta = true;
   }

   if(cId == "Reporte" && cError==""){
     aResReporte = aRes;
   }

 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
   lTransaccion = true;
   lBandera = true;
   lNuevoLisA = true;
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked("iCveTramite,iCveModalidad,iEjercicioSolicitud,iNumSolicitud,iEjercicioSolicitudL,iNumSolicitudL,cTramite,cModalidad,iCveOficinaUsr,iCveDeptoUsr,");
   fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,cNumOficioFil,iEjercicioFil,cSolicitudFil,iEjercicioSolicitudL,iNumSolicitudL,","--");
   FRMListado.fSetDisabled(true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
   frm.lEsContestacion.value = 1;

   frm.Temp.value = frm.cObsesSegto.value;
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
          lBandera = false;
          lEjecuta = false;
          lNuevoLisA = false;
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    frm.hdCveSegtoEntidad.value = "";
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
         lBandera = false;
         lEjecuta = false;
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
   if(frm.lEsContestacion.value == 0){
     fAlert("No se puede modifcar este registro ya que no es una contestación");
     return;
   }
   frm.cNumOficioFil.value = "";
   frm.iEjercicioFil.value = "";
   frm.cSolicitudFil.value = "";
   lTransaccion = true;
   lBandera = true;
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,iEjercicioSolicitudL,iNumSolicitudL,cNumOficioFil,iEjercicioFil,cSolicitudFil,","--");
   FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    lBandera = false;
    lNuevoLisA = false;
    lExisteOficDep = false;
    lTransaccion = false;
//    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Add,Mod,");
    else
      FRMPanel.fSetTraStatus(",");

    if(frm.lBusqueda[0].checked == true)
      fDisabled(true,"cNumOficioFil,lBusqueda,");
    else
      fDisabled(true,"cSolicitudFil,iEjercicioFil,lBusqueda,");
    FRMListado.fSetDisabled(false);

 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
 }
 //Para que funcione el boton reporte
 function fReporte(){

   if(FRMListado.fGetLength() == 0){
     fAlert("No se puede ejecutar ningún reporte debido a que no hay registros en el Listado.");
     return;
   }

// Para generar los parámetros que se envían dinámicamente
   frm.hdCveModulo.value = "";
   frm.hdNumReporte.value = "";
   frm.Query.value = "";

   for(var j = 0; j < aResLisADesplegar.length; j++)
     if(aResLisADesplegar[j][3] == 0)
       frm.iCveSegtoContestacion.value = aResLisADesplegar[j][0];



 if(frm.cFiltroAdicional.value != "")
     cFiltrosRep=frm.iEjercicioSolicitud.value+","+frm.iNumSolicitud.value+","+frm.hdCveOpinionEntidad.value+","+frm.iCveSegtoEntidad.value+","+frm.iCveSegtoContestacion.value+","+frm.iConsecutivoSegto.value+","+frm.cFiltroAdicional.value+cSeparadorRep;
  else
     cFiltrosRep=frm.iEjercicioSolicitud.value+","+frm.iNumSolicitud.value+","+frm.hdCveOpinionEntidad.value+","+frm.iCveSegtoEntidad.value+","+frm.iCveSegtoContestacion.value+","+frm.iConsecutivoSegto.value+cSeparadorRep;


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
       frm.Query.value = frm.Query.value + "" +cFiltrosRep;
     }
   }

  cClavesModulo=frm.hdCveModulo.value+",";
  cNumerosRep=frm.hdNumReporte.value+",";
  cFiltrosRep=frm.Query.value;

     fReportes();

 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.hdCveOpinionEntidad.value = aDato[7];
    frm.iCveSegtoEntidad.value = aDato[0];
    frm.cObsesSegto.value = aDato[6];
    frm.lEsContestacion.value = aDato[3];
    frm.iCveOficinaUsr.value = aDato[1];
    frm.iCveDeptoUsr.value = aDato[2];
    frm.iEjercicioSolicitudL.value = aDato[8];
    frm.iNumSolicitudL.value = aDato[9];
    frm.cTramite.value = aDato[10];
    frm.cModalidad.value = aDato[11];

    fCargaListadoA();

 }
 function fSelReg2(aDato){
   frm.cNumOficio.value = aDato[5];
   frm.iConsecutivoSegto.value = aDato[1];
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

//liga
function fOficios(){
   if(FRMListado.fGetLength()>0 && lBandera == false){
     fAbreSubWindowPermisos("pg111020140","900","400");
   } else fAlert("Debe de existir algún registro y/o no se puede realizar esta operación con una transacción abierta");
}

function fCargaListadoA(){



  if(lEjecuta){

  if(lNuevoLisA)
     frm.iCveSegtoEntidad.value = -1;

  if(frm.iCveSegtoEntidad.value == null || frm.iCveSegtoEntidad.value == "")
     frm.iCveSegtoEntidad.value = 0;

      frm.hdLlave.value =   "GRLFolioXSegtoEnt.iCveSegtoEntidad, GRLFolioXSegtoEnt.iConsecutivoSegtoRef";
      frm.hdSelect.value =  "SELECT FOSEG.ICVESEGTOENTIDAD as iCveSegtoEntidad, FOSEG.ICONSECUTIVOSEGTOREF as iConsecutivoSegto, "+
			     "FOSEG.IEJERCICIOFOLIO as iEjercicioFolio, FOSEG.ICVEOFICINA as iCveOficina, FOSEG.ICVEDEPARTAMENTO as iCveDepto, " +
      			     "FOSEG.CDIGITOSFOLIO as cDigitosFolio, FOSEG.ICONSECUTIVO as iConsecutivo, FO.DTASIGNACION as dtAsignacion, "+
                             "FO.CDIRIGIDOA as cDirigidoA, FO.CASUNTO as cAsunto " +
                             "FROM GRLFOLIOXSEGTOENT FOSEG " +
                             "JOIN GRLFOLIO FO ON FO.IEJERCICIO = FOSEG.IEJERCICIOFOLIO " +
                             "AND FO.ICVEOFICINA = FOSEG.ICVEOFICINA " +
                             "AND FO.ICVEDEPARTAMENTO = FOSEG.ICVEDEPARTAMENTO " +
                             "AND FO.CDIGITOSFOLIO = FOSEG.CDIGITOSFOLIO " +
                             "AND FO.ICONSECUTIVO = FOSEG.ICONSECUTIVO "+
                             "WHERE FOSEG.ICVESEGTOENTIDAD = "+frm.iCveSegtoEntidad.value+
                             " and FOSEG.LFOLIOREFERENINTERNO = 0" +
                             " ORDER BY FO.DTASIGNACION ASC, cDigitosFolio ASC , FOSEG.ICONSECUTIVO ASC ";


      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","ListadoA");

  }
}



function fDatosReporte(){
  if(frm.hdCveOpinionEntidad.value == null || frm.hdCveOpinionEntidad.value == "")
     frm.hdCveOpinionEntidad.value = 0;

      frm.hdLlave.value =   "ICVEOPINIONENTIDAD,ICVESISTEMA,ICVEMODULO,INUMREPORTE";
      frm.hdSelect.value =  "select RXO.ICVEOPINIONENTIDAD, RXO.ICVESISTEMA, RXO.ICVEMODULO, RXO.INUMREPORTE " +
      			"from GRLREPORTEXOPINION RXO " +
			"join GRLREPORTE REP on RXO.ICVESISTEMA = REP.ICVESISTEMA " +
			"and RXO.ICVEMODULO = REP.ICVEMODULO " +
			"and RXO.INUMREPORTE = REP.INUMREPORTE "+
                        "where ICVEOPINIONENTIDAD = "+frm.hdCveOpinionEntidad.value +
                        " and LESCONTESTACION = 1 ";
      if(cPermisoPag == 0)
        frm.hdSelect.value += " and REP.LREQUIEREFOLIO = 0";


      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","Reporte");
}


function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError){
   fCargaListadoA();
}

function fGetCveSegtoEntidad(){
   return frm.iCveSegtoEntidad.value;
}

function fHabilitarBusqueda(){
  fDisabled(false);
  if(frm.lBusqueda[0].checked == true){
    frm.iEjercicioFil.value = "";
    frm.cSolicitudFil.value = "";
    fDisabled(true,"cNumOficioFil,lBusqueda,","");
    frm.cNumOficioFil.focus();
  }else{
    frm.cNumOficioFil.value = "";
    fDisabled(true,"iEjercicioFil,cSolicitudFil,lBusqueda,","");
    frm.iEjercicioFil.focus();
  }
}

function fEjecutafNavega(){
  if(lTransaccion){
    fAlert("No se puede hacer una búsqueda con una transacción abierta.");
    return;
  }

  if(frm.lBusqueda[0].checked == true){
    if(frm.cNumOficioFil.value == ""){
      fAlert("Debe de escribir un número de oficio");
      return;
    }
    frm.hdCveDeptoPadre.value = frm.hdCveDepto.value;
    fSeparaOficio();
    frm.cNumOficioFil.focus();

  }else{
    if(frm.iEjercicioFil.value == "" || frm.cSolicitudFil.value == ""){
      fAlert("Debe de proporcionar Ejercicio y Solicitud.");
      return;
    }

    frm.hdCveDeptoPadre.value = frm.hdCveDepto.value;
    cFiltro = " and TRA.IEJERCICIOSOLICITUD = "+frm.iEjercicioFil.value+
		" and TRA.INUMSOLICITUD = "+frm.cSolicitudFil.value;

    fCargaDatosListado();
    frm.cNumOficioFil.disabled = false;
    frm.cNumOficioFil.focus();
  }
}
//Para obtener Oficina y Depto que esten asignados a un Usuario
function fUserxOfiDepto(){
      frm.hdLlave.value =   "ICVEOFICINA,ICVEDEPARTAMENTO,ICVEUSUARIO";
      frm.hdSelect.value =  "SELECT " +
			"ICVEOFICINA, " +
			"ICVEDEPARTAMENTO, " +
			"ICVEUSUARIO " +
			"FROM GRLUSUARIOXOFIC " +
			"where ICVEUSUARIO = "+frm.idUser.value;

      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","UserxOfi");

}

/*Separa el numero de oficio dado.*/
function fSeparaOficio(){
  frm.hdBoton.value = "SeparaOficio";
  fEngSubmite("pgTRAOpnEntTramiteA.jsp","SeparaOficio");
}

/*Busca la dependencia del depto padre*/
function fCargaDeptoPadre(){
  frm.hdBoton.value = "DeptoPadre";
  fEngSubmite("pgTRAOpnEntTramiteA.jsp","DeptoPadre");
}

/*Funcion para encontrar la oficina y el depto que el usuario tiene asignado.*/
function fEncuentraOficDepto(iCveOficina,iCveDepto){
  frm.hdOpinionEntidad.value = "";
  for(var i = 0; i < aResDesplegar.length; i++)
    if(iCveOficina == aResDesplegar[i][1] && iCveDepto == aResDesplegar[i][2]){
      lExisteOficDep = true;
      if(frm.hdOpinionEntidad.value == "")
        frm.hdOpinionEntidad.value = aResDesplegar[i][7];
      else
        frm.hdOpinionEntidad.value += ","+aResDesplegar[i][7];

      frm.hdCveSegtoEntidad.value = aResDesplegar[i][0];
    }

  if(!lExisteOficDep)
    fCargaDeptoPadre();
  else
    fNavega();
}

/*Carga Datos para ver si existen de acuerdo al filtro*/
function fCargaDatosListado(){
   frm.hdFiltro.value = " FXS.LFOLIOREFERENINTERNO = 0" + cFiltro;
   frm.hdOrden.value =  " icveseg";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   fEngSubmite("pgTRAOpnEntTramiteA.jsp","DatosListado");
}

/*Funciones para adjuntar documentos*/
function fDocumentos(){
  if(lBandera){
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
   aParametros[6] = "24"; // No. de Modulo.

   if(cPermisoPag == 1 && frm.lEsContestacion.value == 0)
     aParametros[7] = "Consulta";   // Modo de la Pagina Consulta.
   else if(cPermisoPag == 1 && frm.lEsContestacion.value == 1)
     aParametros[7] = "Escritura";   // Modo de la Pagina Escritura.
   else if(cPermisoPag == 0)
     aParametros[7] = "Consulta";   // Modo de la Pagina Escritura o Consulta.

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


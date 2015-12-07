   // MetaCD=1.0
 // Title: pg111020131.js
 // Description: JS "Catálogo" de la entidad TRAOpnEntTramite
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aOpinionEntidad;
 var cveSegto;
 var aResReporte = new Array();
 var aResVal = new Array();
 var lNoVigente = false;
 var lBandera = false;
 var iPID;
 var lEjecuta = true;
 var lNuevoLisA = false;
 var cPermisoPag = "";
 var lRespuesta = false;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg" + iNDSADM + "1020131.js";
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
         IFrame("IFiltro31","95%","34","Filtros.js");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicioSolicitud","",10,10,"Año","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitud","",20,20,"Número de Solicitud","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cTramite","",45,45,"Año","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cModalidad","",45,45,"Número de Solicitud","fMayus(this);");
 //          FITR();
 //             TDEtiCampo(false,"EEtiqueta",0,"","iEjercicioSolicitud","",0,0,"Año","fMayus(this);");

           FITR();
        FinTabla();
       FTDTR();
       ITRTD("",0,"","100","center","top");
         IFrame("IListado31","95%","120","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
       //InicioTabla("ETablaInfo",0,"95%","","","",1);

        //ITRTD("ETablaST",5,"","","center");
        FTDTR();
          ITRTD("",0,"","","center");
           IFrame("IListado31A","95%","70","Listado.js","yes",true);
        FTDTR();
       //FinTabla();

         TextoSimple("");

         InicioTabla("ETablaInfo",0,"75%","","center","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           InicioTabla("ETablaInfo",0,"75%","","center","",0);
               fDefOficXUsr(true);
           FinTabla();
           ITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Observaciones:",50,3,"cObsesSegto","","OpnDirigidoA","","fMayus(this);",'onkeydown="fMxTx(this,500);"');
           FITR();
              //TDEtiCampo(false,"EEtiqueta",0,"Número de Oficio:","cNumOficio","",20,20,"Número de Oficio","fMayus(this);");
           Hidden("cNumOficio");
           FITR();
           FITR();
           TDEtiCampo(false,"EEtiqueta",0,"Número de Oficio:","cOficio","",20,20,"Número de Oficio","fMayus(this);");
           FITR();

           //ITD("EEtiquetaC",2,"","","center");
           //   Liga("Oficios Referenciados","fOficios();","Oficios Referenciados");
           //FTD();
           ITD("EEtiquetaC",1,"","","center");
              Liga("Adjuntar/Consultar Documentos","fDocumentos();","Abre página de Adjuntar Documentos");
           FTD();
           
           if(top.opener){
               if(top.opener.fPermiteRespOp){
        	   if(top.opener.fPermiteRespOp()==true){

        	           ITD("EEtiquetaC",1,"","","center");
        	              Liga("Responder Opinion","fResponderOp();","Habilita la respuesta de opinion");
        	   }
               }
           }

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
       Hidden("hdOpinionEntidad");
       Hidden("hdCveModulo");
       Hidden("hdNumReporte");
       Hidden("Query");
       Hidden("Select");
       Hidden("Temp1");
       Hidden("cFiltroAdicional");
       Hidden("cSiglas");
       //Hidden("iCveOpinionEntidad");

     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel31");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(true);
   if(window.parent.lConsulta)
     FRMPanel.fShow(",");
   else
     FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado31");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Num.Control,Oficina,Departamento,Contestación,");
   FRMListado.fSetCampos("7,4,5,11,");
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
   fDisabled(true);
   frm.hdBoton.value="Primero";

   fRecibeValores();
   fCargaOficDeptoUsr(true);

 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = " TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD = " +frm.iEjercicioSolicitud.value+ " AND TRAOPNENTTRAMITE.INUMSOLICITUD = " +frm.iNumSolicitud.value;
   frm.hdOrden.value =  " GRLSEGTOENTIDAD.ICVESEGTOENTIDAD";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgTRAOpnEntTramite.jsp","Listado");
//   fCargaListadoA();

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
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);

     for(var i=0; i<aRes.length; i++)
       aRes[i][12] = aRes[i][9]+aRes[i][10]+aRes[i][8];


     if(frm.Temp.value!="")
        fReposicionaListado(FRMListado,"12", frm.Temp.value);
     else
        fReposicionaListado(FRMListado,"12", 0);


     if(frm.Temp1.value != "")
       fReposicionaListado(FRMListado, "8",frm.Temp1.value);
     else
       fReposicionaListado(FRMListado, "8", 0);

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

   if(cId == "Reporte" && cError==""){
     aResReporte = aRes;
   }

   if(cId == "CIDOficinaDeptoXUsr" && cError==""){

      aResValidos = fCopiaArregloBidim(aRes);
      fResOficDeptoUsr(aRes,cId,cError,true);
   }

 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
   if (frm.iCveOficinaUsr.value != ""){
       lBandera = true;
       lNuevoLisA = true;
//      fFillSelect(frm.iCveOficinaUsr,aResValido,false,frm.iCveOficinaUsr.value,1,5);
       fSelectSetIndexFromValue(frm.iCveOficinaUsr, 1);
       fSelectSetIndexFromValue(frm.iCveDeptoUsr, 1);
       fOficinaUsrOnChange(true);
       FRMPanel.fSetTraStatus("UpdateBegin");
       fBlanked("iCveTramite,iCveModalidad,iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,iCveOficinaUsr,iCveDeptoUsr,");
       fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,","--");
       FRMListado.fSetDisabled(true);
   }else{
     fAlert("No existe una Oficina y un Departamento configurados");
     lBandera = false;
   }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
     if(lRespuesta==true){
	 frm.hdBoton.value = "Guardar";
	 fEngSubmite("pgTRAOpnEntTramite.jsp","Listado");
	 return true;
     }
     else{
          for(var i=0;i<aResValidos.length;i++){
            if(aResValidos[i][0] == frm.iCveOficinaUsr.value && aResValidos[i][1] == frm.iCveDeptoUsr.value && aResValidos[i][7] == 0){
              lNoVigente = true;
              break;
            }
            if(aResValidos[i][0] == frm.iCveOficinaUsr.value && aResValidos[i][1] == frm.iCveDeptoUsr.value){
              //frm.iCveOpinionEntidad.value = aResValidos[i][6];
              frm.hdCveOpinionEntidad.value = aResValidos[i][6];
              frm.hdOpinionEntidad.value = aResValidos[i][6];
              break;
             }
          }
        
          if(lNoVigente == false){
          frm.Temp.value = frm.iCveOficinaUsr.value+frm.iCveDeptoUsr.value+frm.cObsesSegto.value;
          frm.lEsContestacion.value = 0;
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
          }else{
            fAlert("La oficina seleccionada no está vigente");
            lNoVigente = false;
            lBandera = true;
            return;
          }
     }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    frm.Temp1.value = frm.cObsesSegto.value;
    frm.lEsContestacion.value = 0;
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
   if(frm.lEsContestacion.value == 1){
     fAlert("No se puede modifcar este registro ya que es una contestación");
     return;
   }
   lBandera = true;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,iCveOficinaUsr,iCveDeptoUsr,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    lBandera = false;
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
 //Para que funcione el boton reporte
 function fReporte(){

   if(frm.lEsContestacion.value == 1){
     fAlert("No puede hacer uso de esta opción");
     return;
   }

// Para generar los parámetros que se envían dinámicamente
   frm.hdCveModulo.value = "";
   frm.hdNumReporte.value = "";
   frm.Query.value = "";

   if(frm.cFiltroAdicional.value != "")
     cFiltrosRep=frm.iEjercicioSolicitud.value+","+frm.iNumSolicitud.value+","+frm.hdCveOpinionEntidad.value+","+frm.iCveSegtoEntidad.value+","+frm.cFiltroAdicional.value+cSeparadorRep;
   else
     cFiltrosRep=frm.iEjercicioSolicitud.value+","+frm.iNumSolicitud.value+","+frm.hdCveOpinionEntidad.value+","+frm.iCveSegtoEntidad.value+cSeparadorRep;


   for(cont=0;cont < aResReporte.length;cont++){
     if(frm.hdCveModulo.value == "")
       frm.hdCveModulo.value = aResReporte[cont][2];
     else
  	   frm.hdCveModulo.value = frm.hdCveModulo.value + "," + aResReporte[cont][2];

     if(frm.hdNumReporte.value == "")
   	   frm.hdNumReporte.value = aResReporte[cont][3];
     else
 	   frm.hdNumReporte.value = frm.hdNumReporte.value + "," + aResReporte[cont][3];

     if(frm.Query.value == "" )
       frm.Query.value = cFiltrosRep;
     else
       frm.Query.value = frm.Query.value + "" +cFiltrosRep;

   }

  cClavesModulo=frm.hdCveModulo.value+",";
  cNumerosRep=frm.hdNumReporte.value+",";
  cFiltrosRep=frm.Query.value;

  if(FRMListadoA.fGetLength()>0){
    fAlert("Ya esxiste un oficio asignado para éste registro");
  }else{
     fReportes();
  }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
     lRespuesta = false;

    if (parseInt(aDato[9]) > 0)
      fSelectSetIndexFromValue(frm.iCveOficinaUsr, aDato[9]);
    else
      fSelectSetIndexFromValue(frm.iCveOficinaUsr, 0);

    fOficinaUsrOnChange(true);
    if (parseInt(aDato[10]) > 0)
      fSelectSetIndexFromValue(frm.iCveDeptoUsr, aDato[10]);
    else
      fSelectSetIndexFromValue(frm.iCveDeptoUsr, 0);

    frm.hdCveOpinionEntidad.value = aDato[6];
    frm.hdOpinionEntidad.value = aDato[6];
    frm.iCveSegtoEntidad.value = aDato[7];
    frm.cObsesSegto.value = aDato[8];
    frm.lEsContestacion.value = aDato[11];
    frm.cOficio.value = aDato[13];

    fCargaListadoA();

 }
 function fSelReg2(aDato){
   frm.cNumOficio.value = aDato[5];
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

//Recibe valores de la pagina anterior
function fRecibeValores(){
  frm.iEjercicioSolicitud.value = top.opener.fGetiEjercicio();
  frm.iNumSolicitud.value = top.opener.fGetiNumSolicitud();
  frm.cTramite.value = top.opener.fGetcDscTramite();
  frm.cModalidad.value = top.opener.fGetcDscModalidad();
  frm.iCveTramite.value = top.opener.fGetCveTramite();
  frm.iCveModalidad.value = top.opener.fGetCveModalidad();
  frm.iFolioReferenInterno.value = 0;
//  frm.lEsContestacion.value = 0;
  if(top.opener.fGetFiltroAdicional)
     frm.cFiltroAdicional.value = top.opener.fGetFiltroAdicional();
  else
     frm.cFiltroAdicional.value = "";
  fNavega();


}
//sobreescribo la funcion para que la pueda adecuar
function fCargaOficDeptoUsr(){

      frm.hdLlave.value =   "GRLDeptoXOfic.iCveOficina,GRLDeptoXOfic.iCveDepartamento";
      frm.hdSelect.value =  "SELECT GRLOPINIONENTIDAD.ICVEOFICINAOPN, GRLOPINIONENTIDAD.ICVEDEPARTAMENTOOPN, " +
                            "GRLOficina.cDscOficina, GRLOficina.cDscBreve as cDscBreveOfic, " +
                            "GRLDepartamento.cDscDepartamento, GRLDepartamento.cDscBreve as cDscBreveDepto, GRLOpinionEntidad.iCveOpinionEntidad, " +//6
                            "GRLOPINIONENTIDAD.lVigente "+//7
                            "FROM GRLOPINIONENTIDAD " +
                            "JOIN GRLOficina ON GRLOficina.iCveOficina = GRLOPINIONENTIDAD.ICVEOFICINAOPN " +
                            "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLOPINIONENTIDAD.ICVEDEPARTAMENTOOPN " +
                            "WHERE GRLOpinionEntidad.ICVETRAMITE = " +frm.iCveTramite.value + " AND GRLOPINIONENTIDAD.ICVEMODALIDAD = " +frm.iCveModalidad.value;

          aOficinaDeptoUsr = fCopiaArreglo(aRes);
          aOficinaUsr = fCopiaArreglo(aRes);

    if (frm.hdNumReg)
      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","CIDOficinaDeptoXUsr");
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
                             " ORDER BY FO.DTASIGNACION DESC,FOSEG.ICONSECUTIVO ASC ";


      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","ListadoA");

  }
}



function fDatosReporte(){

  if(frm.hdCveOpinionEntidad.value == null || frm.hdCveOpinionEntidad.value == "")
     frm.hdCveOpinionEntidad.value = 0;


      frm.hdLlave.value =   "ICVEOPINIONENTIDAD,ICVESISTEMA,ICVEMODULO,INUMREPORTE";
      frm.hdSelect.value =  "select ICVEOPINIONENTIDAD, ICVESISTEMA, ICVEMODULO, INUMREPORTE "+
                            "from GRLREPORTEXOPINION "+
                            "where ICVEOPINIONENTIDAD = "+frm.hdCveOpinionEntidad.value+
                            " and LESCONTESTACION = 0 ";


      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","Reporte");

}


function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError){
   fCargaListadoA();
}

function fGetCveSegtoEntidad(){
   return frm.iCveSegtoEntidad.value;
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
     aParametros[7] = "Escritura";   // Modo de la Pagina Consulta.
   else if(cPermisoPag == 1 && frm.lEsContestacion.value == 1)
     aParametros[7] = "Consulta";   // Modo de la Pagina Escritura.
   else if(cPermisoPag == 0)
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

 function fResponderOp(){
     if (frm.iCveOficinaUsr.value != ""){
	       lBandera = true;
	       lNuevoLisA = true;
	       lRespuesta = true;
	       frm.lEsContestacion.value = 1;
	       fSelectSetIndexFromValue(frm.iCveOficinaUsr, 1);
	       fSelectSetIndexFromValue(frm.iCveDeptoUsr, 1);
	       fOficinaUsrOnChange(true);
	       FRMPanel.fSetTraStatus("UpdateBegin");
	       fBlanked("iCveTramite,iCveModalidad,iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,iCveOficinaUsr,iCveDeptoUsr,");
	       fDisabled(false,"iEjercicioSolicitud,iNumSolicitud,cTramite,cModalidad,cNumOficio,iCveOficinaUsr,iCveDeptoUsr,","--");
	       FRMListado.fSetDisabled(true);
	   }else{
	     fAlert("No existe una Oficina y un Departamento configurados");
	     lBandera = false;
	   }
	 
     
 }
// MetaCD=1.0
 // Title: pg111020160.js
 // Description: JS "Catálogo" de la entidad GRLReporteXOpinion
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResTemp = new Array();
 var aResLis = new Array();
 var cPermisoPag;
 var lBandera = false;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg" + iNDSADM + "1020161.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"CONFIGURAR REPORTES":cTitulo;
   fSetWindowTitle();
   cPermisoPag = fGetPermisos(cPaginaWebJS);
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       fTituloEmergente(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro","0%","34","Filtros.js");
       InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           TextoSimple("Oficina, Departamento, Trámite y Modalidad");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Oficina:","cDscOficina","",45,45,"Oficina","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Departamento:","cDscDepartamento","",45,45,"Departamento","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cTramite","",45,45,"Trámite","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cModalidad","",45,45,"Modalidad","fMayus(this);");
           FITR();
           ITR();
              TDEtiSelect(false,"EEtiqueta",0,"Módulo:","iCveModulo","fCargaListadoA();");
           FITR();
        FinTabla();
          TextoSimple("<br>");


        InicioTabla("",0,"95%","","center");
        ITRTD("ETablaST",0,"49%","","center");
          TextoSimple("Documentos Disponibles");
        FITD("",0,"2%","","center");
        FITD("ETablaST",0,"49%","","center");
          TextoSimple("Documentos Seleccionados");
        FTDTR();
        ITRTD("",0,"49%","","center","middle");
          IFrame("IListadoA","100%","130","Listado.js","yes",true);
        FITD("",0,"2%","","center","middle");
        InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnquitar","fEliminar();");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
             ITRTD("",0,"","100%","center","");
             FTDTR();
           FinTabla();
        FITD("",0,"49%","","center","middle");
          IFrame("IListado","100%","130","Listado.js","yes",true);
        FTDTR();


         FITR();
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
             TDEtiCampo(false,"EEtiqueta",0,"Documento:","cNomReporte","",75,75,"Año","fMayus(this);");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"¿Es Documento de Contestación?","lEsContestacionBOX","1",true,"EsContestacion");
              Hidden("lEsContestacion","");
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Vigente:","lConVigenteBOX","1",true,"ConVigente");
              Hidden("lConVigente","");
              Hidden("iCveOpinionEntidad");
              Hidden("iCveSistema");
              Hidden("iCveTramite");
              Hidden("iCveModalidad");
              Hidden("iCveOficina");
              Hidden("iCveDepartamento");
              Hidden("iNumReporte");
              Hidden("hdLlave");
              Hidden("hdSelect");
              Hidden("hdCveModulo","");
              Hidden("hdCveOpinionEntidad","");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");

       FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo(",Documento,Es Contestación?,Vigente,");
   FRMListado.fSetCampos("6,4,5,");
   FRMListado.fSetObjs(0,"Caja");
   FRMListado.fSetDespliega(",texto,logico,logico,logico,");
   FRMListado.fSetAlinea("center,left,center,center,");
   FRMListado.fSetDisabled(false);
   FRMListado.fSetSelReg(1);
//   FRMListado.fSetCampos();
    FRMListadoA = fBuscaFrame("IListadoA");
    FRMListadoA.fSetControl(self);
    FRMListadoA.fSetTitulo(",Documento,");
    FRMListadoA.fSetCampos("3,");
    FRMListadoA.fSetObjs(0,"Caja");
    FRMListadoA.fSetSelReg(2);
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("");
   FRMFiltro.fSetOrdena("");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fCargaModulo();
   fRecibeValores();


//   fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){

   if (frm.iCveModulo.value == -1 || frm.iCveModulo.value == ""){
   frm.hdFiltro.value =  "GRLREPORTE.iCveModulo = -1";//+frm.iCveModulo.value; //+frm.iCveModulo.value;
   frm.hdOrden.value =  " GRLREPORTE.CNOMREPORTE";
   frm.hdNumReg.value =  10000;
   } else{
   frm.hdFiltro.value =  "GRLREPORTE.iCveModulo = "+frm.iCveModulo.value+ " AND GRLREPORTEXOPINION.ICVEOPINIONENTIDAD = "+frm.iCveOpinionEntidad.value+ " AND GRLREPORTEXOPINION.ICVESISTEMA = 44";
   frm.hdOrden.value =  " GRLREPORTE.CNOMREPORTE";
   frm.hdNumReg.value =  10000;
   }
   return fEngSubmite("pgGRLReporteXOpinion.jsp","Listado");
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
     //aResTemp = aRes;
     //alert("---------*aRes...Listado.."+aRes);
     aResLis = aRes;
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
     fNuevo();
   }

   if(cId == "GRLReporte" && cError==""){
     aResTemp = aRes;
     frm.hdRowPag.value = iRowPag;
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
     fNavega();

   }

   if(cId == "GRLModulo" && cError==""){
     fFillSelect(frm.iCveModulo,aRes,true,frm.iCveModulo.value,0,1);
     fNuevo();
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
  if(FRMListado.fGetLength() > 0){
    FRMPanel.fSetTraStatus("Mod,");
    fBlanked("iCveOpinionEntidad,iCveModulo,iCveTramite,iCveModalidad,iCveOficina,iCveDepartamento,cTramite,cModalidad,cDscDepartamento,cDscOficina,cNomReporte,");
    fDisabled(false,"iCveOpinionEntidad,cDscDepartamento,cDscOficina,cModalidad,cTramite,cNomReporte,lEsContestacionBOX,lConVigenteBOX,","--");
    FRMListado.fSetDisabled(false);
  }else{
    FRMPanel.fSetTraStatus(",");
    fBlanked("iCveOpinionEntidad,iCveModulo,iCveTramite,iCveModalidad,iCveOficina,iCveDepartamento,cTramite,cModalidad,cDscDepartamento,cDscOficina,cNomReporte,");
    fDisabled(false,"iCveOpinionEntidad,cDscDepartamento,cDscOficina,cModalidad,cTramite,cNomReporte,lEsContestacionBOX,lConVigenteBOX,","--");
    FRMListado.fSetDisabled(false);
  }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
    frm.lConVigente.value=1;
    frm.lEsContestacion.value=0;
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
    lBandera = false;
    frm.lConVigenteBOX.checked==true?frm.lConVigente.value=1:frm.lConVigente.value=0;
    frm.lEsContestacionBOX.checked==true?frm.lEsContestacion.value=1:frm.lEsContestacion.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
         FRMListadoA.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    lBandera = true;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(true);
    fDisabled(false,"iCveOpinionEntidad,cDscOficina,cDscDepartamento,cTramite,cModalidad,cNomReporte,iCveModulo,");
    FRMListado.fSetDisabled(true);
    FRMListadoA.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    lBandera = false;
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0){
      fDisabled(true);
      fNuevo();
      FRMListadoA.fSetDisabled(false);
    }
    else{
       fDisabled(true);
       fNuevo();
       FRMListadoA.fSetDisabled(false);
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
//    frm.iCveOpinionEntidad.value = aDato[0];
    frm.iCveSistema.value = aDato[1];
  //  frm.iCveModulo.value = aDato[2];
    frm.iNumReporte.value = aDato[3];
    frm.cNomReporte.value = aDato[6];
    frm.lEsContestacion.value = aDato[4];
     if (frm.lEsContestacion.value==1) frm.lEsContestacionBOX.checked=true;
       else frm.lEsContestacionBOX.checked=false;

    frm.lConVigente.value = aDato[5];
     if (frm.lConVigente.value==1) frm.lConVigenteBOX.checked=true;
       else frm.lConVigenteBOX.checked=false;
 }
 function fSelReg2(aDato){
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

 //Función para cargar el select del módulo
 function fCargaModulo(){
      frm.hdLlave.value =   "iCveSistema, iCveModulo";
      frm.hdSelect.value =  "SELECT ICVEMODULO, CDSCMODULO " +
                            "FROM GRLMODULO WHERE ICVESISTEMA = 44 "+
                            "order by CDSCMODULO";
      frm.hdNumReg.value = 100000;
      fEngSubmite("pgConsulta.jsp","GRLModulo");

 }

//Recibe valores de la pagina anterior
 function fRecibeValores(){
  frm.iCveTramite.value = top.opener.fGetClaveTramite();
  frm.iCveModalidad.value = top.opener.fGetClaveModalidad();
  frm.cTramite.value = top.opener.fGetDscTramite();
  frm.cModalidad.value = top.opener.fGetDscModalidad();
  frm.cDscOficina.value = top.opener.fGetDscOficina();
  frm.cDscDepartamento.value = top.opener.fGetDscDepto();
  frm.iCveOficina.value = top.opener.fGetClaveOficina();
  frm.iCveDepartamento.value = top.opener.fGetClaveDepto();
  frm.iCveOpinionEntidad.value = top.opener.fGetClaveOpinionEntidad();
  frm.iCveSistema.value = 44;


//  fNavega();
 }

 //Se carga el listado A
 function fCargaListadoA(){
   frm.hdFiltro.value =  "ICVEMODULO = " +frm.iCveModulo.value+ " AND INUMREPORTE NOT IN (SELECT INUMREPORTE FROM GRLREPORTEXOPINION WHERE ICVEOPINIONENTIDAD = "+frm.iCveOpinionEntidad.value+ " AND ICVESISTEMA = 44 AND ICVEMODULO = " +frm.iCveModulo.value+ " )";
   frm.hdOrden.value =  " CNOMREPORTE";
   frm.hdNumReg.value = 100000;
   fEngSubmite("pgGRLReporteA.jsp","GRLReporte");
 }

//Funcion que responde al boton agregar, se utiliza un arreglo temporal, y se concatenan los datos a insertar
 function fAgregar(){
   if (cPermisoPag != 1){
     fAlert("No tiene Permiso de ejecutar esta acción");
     return;
   }

   if(lBandera == true){
     fAlert("No puede efectuar esta operación mientras se encuentre realizando otra transacción");
     return;
   }
   frm.iCveSistema.value = "";
   frm.hdCveModulo.value = "";
   frm.iNumReporte.value = "";

   aDocDisp = FRMListadoA.fGetObjs(0);

   for(cont=0;cont < aDocDisp.length;cont++){
    if(aDocDisp[cont]){
      if (frm.iCveSistema.value=="") frm.iCveSistema.value=aResTemp[cont][0]; else frm.iCveSistema.value+=","+aResTemp[cont][0];
      if (frm.hdCveModulo.value=="") frm.hdCveModulo.value=aResTemp[cont][1]; else frm.hdCveModulo.value += "," + aResTemp[cont][1];
      if (frm.iNumReporte.value=="" ) frm.iNumReporte.value=aResTemp[cont][2]; else frm.iNumReporte.value+=","+aResTemp[cont][2];
      }
    }

   if (frm.iCveSistema.value == ""){
     fAlert ('\nSeleccione al menos un registro para hacer esta operación.');
     return;
   }


   frm.hdBoton.value = "Guardar";
   frm.hdFiltro.value = "";
   if(fEngSubmite("pgGRLReporteA.jsp","idAgrega")){
     FRMPanel.fSetTraStatus("UpdateComplete");
     fDisabled(true);
     FRMListado.fSetDisabled(false);
   }

   fCargaListadoA();
}

//Funcion que responde al boton Eliminar, se utiliza un arreglo temporal, y se concatenan los datos a eliminar
 function fEliminar(){
   if (cPermisoPag != 1){
     fAlert("No tiene Permiso de ejecutar esta acción");
     return;
   }

   if(lBandera == true){
     fAlert("No puede efectuar esta operación mientras se encuentre realizando otra transacción");
     return;
   }

   frm.hdCveOpinionEntidad.value = "";
   frm.iCveSistema.value = "";
   frm.hdCveModulo.value = "";
   frm.iNumReporte.value = "";

   aDoc = FRMListado.fGetObjs(0);

   for(cont=0;cont < aDoc.length;cont++){
     if(aDoc[cont]){
       if (frm.hdCveOpinionEntidad.value=="") frm.hdCveOpinionEntidad.value=aResLis[cont][0]; else frm.hdCveOpinionEntidad.value+=","+aResLis[cont][0];
       if (frm.iCveSistema.value=="") frm.iCveSistema.value=aResLis[cont][1]; else frm.iCveSistema.value+=","+aResLis[cont][1];
       if (frm.hdCveModulo.value=="") frm.hdCveModulo.value=aResLis[cont][2]; else frm.hdCveModulo.value += "," + aResLis[cont][2];
       if (frm.iNumReporte.value=="" ) frm.iNumReporte.value=aResLis[cont][3]; else frm.iNumReporte.value+=","+aResLis[cont][3];
     }
   }

   if (frm.iCveSistema.value == ""){
     fAlert ('\nSeleccione al menos un registro para realizar esta operación.');
     return;
   }

   frm.hdBoton.value = "Borrar";
   frm.hdFiltro.value = "";
   if(fEngSubmite("pgGRLReporteXOpinion.jsp","idElimina")){
     FRMPanel.fSetTraStatus("UpdateComplete");
     fDisabled(true);
     FRMListado.fSetDisabled(false);
   }

   fCargaListadoA();
 }

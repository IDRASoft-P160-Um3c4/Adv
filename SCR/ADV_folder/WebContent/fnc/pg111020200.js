// MetaCD=1.0
 // Title: pg111020131.js
 // Description: JS "Catálogo" de la entidad TRAOpnEntTramite
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var cModulos = "";
 var cRep = "";
 var cModulosVal = "";
 var cRepVal = "";
 var lDeVerif = false;
 var iRegistros = 0;
 var iRegistros1 = 0;
 var lSeInserto = false;
 var lPrimera = true;
 var wReporte;

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg" + iNDSADM + "1020200.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"SEGUIMIENTO A OFICIOS":cTitulo;
   fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",2,"100%","","center");
       fTituloEmergente(cTitulo);
     FTDTR();
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
          FITR();
       FinTabla();
       FTDTR();
       ITRTD("",0,"","190","center","top");
         IFrame("IListado31","95%","185","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
       FTDTR();
     FinTabla();
     FTDTR();
     ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel31","95%","34","Paneles.js");
     FTDTR();
     if(top.opener)
        if(top.opener.fApOficio){
           ITRTD("",0,"","40","center","bottom");
              BtnImg("Salir","aceptar","fSalir();");
           FTDTR();
        }

     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("cFiltroAdicional","");
     Hidden("cNumOficio");
     Hidden("cOficialiaPartes");
     Hidden("dtAsignacion");
     Hidden("dtOficio");
     Hidden("cNumOfPaso");
     Hidden("hdOficialiaPartes");
     Hidden("hdNumOficio");
     Hidden("hdAsignacion");
   FinTabla();
   fFinPagina();
 }
 function fTodos(theCheck){
  fSeleccionaTodosEnListado(FRMListado, 0, theCheck);
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel31");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(true);
   FRMPanel.fShow(",");
   FRMListado = fBuscaFrame("IListado31");
   FRMListado.fSetControl(self);
   if(top.opener.fGetCveModulosVal){
     //FRMListado.fSetTitulo(fCheckBoxTodos("cbTodos", "fTodos")+",Nombre del Reporte,Asunto,Número de Oficio,Num. Oficialía de Partes, Fecha del Oficio,Fec. Cancelación Oficio,");
     //FRMListado.fSetObjs(0,"Caja");
     //FRMListado.fSetCampos("0,1,5,9,7,8,");
     //FRMListado.fSetAlinea("center,left,left,center,center,center,");
	   FRMListado.fSetTitulo(fCheckBoxTodos("cbTodos", "fTodos")+",Nombre del Reporte,Asunto,Número de Oficio, Fecha del Oficio,");
	     FRMListado.fSetObjs(0,"Caja");
	     FRMListado.fSetCampos("0,1,5,7,");
	     FRMListado.fSetAlinea("center,left,left,center,");
	     
     
   }else{
     //FRMListado.fSetTitulo("Nombre del Reporte,Asunto,Número de Oficio,Num. Oficialía de Partes, Fecha del Oficio,Fec. Cancelación Oficio,");
     //FRMListado.fSetCampos("0,1,5,9,7,8,");
     //FRMListado.fSetAlinea("center,left,left,center,center,center,");
     
     FRMListado.fSetTitulo("Nombre del Reporte,Asunto,Número de Oficio, Fecha del Oficio,");
     FRMListado.fSetCampos("0,1,5,7,");
     FRMListado.fSetAlinea("center,left,left,center,");
     
     
   }
   FRMFiltro = fBuscaFrame("IFiltro31");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fRecibeValores();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = " REPF.iEjercicioSolicitud = "+frm.iEjercicioSolicitud.value +
  			 " and REPF.iNumSolicitud = "+frm.iNumSolicitud.value;
   frm.hdOrden.value =  " dtAsig desc";
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   iRegistros1 = iRegistros;
   return fEngSubmite("pgGRLReporteXFolio.jsp","Listado");
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
     iRegistros = aRes.length;
     if(lPrimera == false){
        if(iRegistros != iRegistros1){
           lSeInserto = true;
           wReporte.top.close();
        }else
           lSeInserto = false;
     }else{
       iRegistros1 = iRegistros;
       lPrimera = false;
     }
     for(var i=0; i<aRes.length; i++){
       if(aRes[i][6]<10)
         aRes[i][6] = "00"+aRes[i][6];

       if(aRes[i][6]>10 && aRes[i][6]<100)
         aRes[i][6] = "0"+aRes[i][6];

       aRes[i][5] = aRes[i][5] + "." + aRes[i][6] + "/" + aRes[i][2];
     }
     if(aRes.length > 0){
        frm.cNumOfPaso.value = aRes[aRes.length - 1][5];
        frm.dtOficio.value = aRes[aRes.length - 1][7];
     }else{
        frm.cNumOfPaso.value = "";
        frm.dtOficio.value = "";
        fReporte();
     }
     aResListado = fCopiaArregloBidim(aRes);
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

 }

 //Para que funcione el boton reporte
 function fReporte(){
   var aModulos = new Array();
   cFiltrosRep = "";
   cFiltrosRepTemp = "";
   cClavesModulo = "";
   cNumerosRep = "";
   frm.hdOficialiaPartes.value = "";
   frm.hdNumOficio.value = '';
   frm.hdAsignacion.value = '';

   /*En caso de que existan reportes con validacion aca se agregan*/
   if(cModulosVal != "" && cRepVal != "" && FRMListado.fGetLength() > 0){
     cClavesModulo=cModulos + cModulosVal;
     cNumerosRep=cRep + cRepVal;
   }else{
     cClavesModulo=cModulos;
     cNumerosRep=cRep;
   }


   /*Para ver cuales selecciono*/
   aSelec = FRMListado.fGetObjs(0);

   /*Se concatenan separados por una | , para no afectar a los programas ya hechos que hacen split con ,*/
   for(cont=0;cont < aSelec.length;cont++)
    if(aSelec[cont]){
      if(frm.hdOficialiaPartes.value==""){
        if(aResListado[cont][9] != "")
          frm.hdOficialiaPartes.value=aResListado[cont][5]+"("+aResListado[cont][9]+")";
        else
          frm.hdOficialiaPartes.value=aResListado[cont][5];
      }else{
        if(aResListado[cont][9] != "")
          frm.hdOficialiaPartes.value+="|"+aResListado[cont][5]+"("+aResListado[cont][9]+")";
        else
          frm.hdOficialiaPartes.value+="|"+aResListado[cont][5];
      }


      if(frm.hdNumOficio.value=="")
        frm.hdNumOficio.value=aResListado[cont][5];
      else
        frm.hdNumOficio.value+="|"+aResListado[cont][5];

      if(frm.hdAsignacion.value=="")
        frm.hdAsignacion.value=aResListado[cont][7];
      else
        frm.hdAsignacion.value+="|"+aResListado[cont][7];
    }

    if(top.opener.fGetCveModulosVal)
      if(FRMListado.fGetLength() > 0 && frm.hdAsignacion.value == ''){
        if(!confirm('No ha seleccionado ningun registro para hacer referencia.\nEsto puede ocasionar que no se genere correctamente el documento respectivo.\n ¿Desea Continuar?'))
        return;
      }

    /*Se asigna el filtro adicional si hay*/
    aModulos = cClavesModulo.split(",");
    if(frm.cFiltroAdicional.value != "")
      cFiltrosRepTemp=frm.hdOficialiaPartes.value+","+frm.hdNumOficio.value+","+frm.hdAsignacion.value+","+frm.cFiltroAdicional.value+cSeparadorRep;
    else
      cFiltrosRepTemp=frm.hdOficialiaPartes.value+","+frm.hdNumOficio.value+","+frm.hdAsignacion.value+cSeparadorRep;

    /*Se pone el mismo numero de filtros que el numero de reportes*/
    for(var i=0; i<aModulos.length-1; i++)
      if(cFiltrosRep == "")
        cFiltrosRep = cFiltrosRepTemp;
      else
        cFiltrosRep += cFiltrosRepTemp;

   fReportes();
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
   frm.cNumOficio.value = aDato[5];
   frm.dtAsignacion.value = aDato[7];
   frm.cOficialiaPartes.value = aDato[9];
   if(top.opener && lSeInserto)
     if(top.opener.fObtenOficio)
       top.opener.fObtenOficio(frm.cNumOfPaso.value, frm.dtOficio.value);
   lSeInserto = false;
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

  if(top.opener)
    if(top.opener.fApOficio){
      top.opener.fApOficio(window);
      lDeVerif = true;
    }

  cModulos = top.opener.fGetCveModulos();
  cRep = top.opener.fGetNumReportes();

  if(top.opener.fGetCveModulosVal)
    cModulosVal = top.opener.fGetCveModulosVal();

  if(top.opener.fGetCveReportesVal)
    cRepVal = top.opener.fGetCveReportesVal();

  if(top.opener.fGetFiltroAdicional)
     frm.cFiltroAdicional.value = top.opener.fGetFiltroAdicional();
  else
     frm.cFiltroAdicional.value = "";

  fNavega();
}

function fReporteEjecutado(theWindow, aRes, aDato, cFiltro, cId, cError){
   fNavega();
}

function fSalir(){
  //if(lSeInserto)
  //   top.opener.fObtenOficio(frm.cNumOfPaso.value, frm.dtOficio.value);
  //else
     top.close();
}
function fSetHandleReportes(wRep){
  wReporte = wRep;
}

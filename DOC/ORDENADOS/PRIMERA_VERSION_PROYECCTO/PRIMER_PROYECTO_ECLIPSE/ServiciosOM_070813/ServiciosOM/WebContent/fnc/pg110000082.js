// MetaCD=1.0
 // Title: pg110000082.js
 // Description: JS "Catálogo" de la entidad GRLUsuarioXOfic
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Levi Equihua
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110000082.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","","","1");
     InicioTabla("ETablaInfo",0,"95%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Modulo:","iCveModulo","fNavega();");
           FITR();
     FinTabla();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel31","0","0","Paneles.js");
     FTDTR();
   FinTabla();
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","","center","");
         IFrame("IFiltro31","0","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","1","center");
       FTDTR();
         ITRTD("",0,"","","center","");
           IFrame("IListado31A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               //Liga("     Agregar  >>> ","fAgregar();","Agrega un Registro");
               BtnImg("Buscar","btnagregar","fGuardar();");
             ITRTD("",0,"","100%","center","");
               //Liga(" <<< Eliminar     ","fEliminar();","Elimina un Registro");
               BtnImg("Buscar","btnquitar","fBorrar();");
             FTDTR();
           FinTabla();
         ITD("",0,"","","center","");
           IFrame("IListado31","95%","220","Listado.js","yes",true);
       FTDTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
     FinTabla();
   //Hidden ("iCveModulo1"); //Clave del modulo en ejecucion
   Hidden ("iCveTipoDocumento1");
   Hidden ("iCveTipoDocumento");
   Hidden ("iCveSistema");
   Hidden("hdLlave");
   Hidden("hdSelect");
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   frm.iCveSistema.value=iNDSADM;
   FRMPanel = fBuscaFrame("IPanel31");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado31");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Tipo Documento Asignado,");
   FRMListado.fSetCampos("3,");
   FRMListadoA = fBuscaFrame("IListado31A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Tipo Documento Disponible,");
   FRMListadoA.fSetCampos("1,");
   FRMFiltro = fBuscaFrame("IFiltro31");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Oficina,iCveOficina,Departamento,iCveDepartamento,");
   FRMFiltro.fSetOrdena("Oficina,iCveOficina,Departamento,iCveDepartamento,");
   FRMListadoA.fSetSelReg(2);
   frm.hdBoton.value="Primero";
 }

 function fFiltro(){
   frm.hdFiltro.value =  "";
   frm.hdOrden.value =  "cDscModulo";
   frm.hdNumReg.value = 10000;
   fEngSubmite("pgGRLModulo.jsp","CIDModulo");
 }
  // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value =  "iCveModulo = " + frm.iCveModulo.value +  " AND " +
   "ICVESISTEMA="+frm.iCveSistema.value;
   frm.hdOrden.value =  "cDscTipoDocumento";
   frm.hdNumReg.value = 10000;
   return fEngSubmite("pgGRLModTipoDocumento.jsp","Listado");
//  }
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
     if(frm.iCveModulo.value>=0)fNavega1();else{
       FRMListadoA.fSetListado(new Array);
       FRMListadoA.fShow();
     }
   }
    if(cId == "Listado2" && cError == ""){
     frm.hdRowPag.value = iRowPag;
     FRMListadoA.fSetListado(aRes);
     FRMListadoA.fShow();
     FRMListadoA.fSetLlave(cLlave);
   }
   if(cId == "CIDModulo" && cError == ""){
     fFillSelect(frm.iCveModulo,aRes,true,frm.iCveModulo.value,1,2);
     fNavega();
   }

 }

 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.iCveTipoDocumento.value = aDato[2];
 }
 function fSelReg2(aDato){
    frm.iCveTipoDocumento1.value=aDato[0];
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

 function fNavega1(){
   frm.hdLlave.value = "";
   frm.hdSelect.value =
   "SELECT ICVETIPODOCUMENTO, CDSCTIPODOCUMENTO FROM GRLTIPODOCUMENTO Where ICVETIPODOCUMENTO not in( " +
   "SELECT ICVETIPODOCUMENTO FROM  GRLMODTIPODOCUMENTO where ICVESISTEMA="+frm.iCveSistema.value+
   " and ICVEMODULO="+frm.iCveModulo.value+") and GRLTIPODOCUMENTO.LACTIVO=1 ";
   fEngSubmite("pgConsulta.jsp","Listado2");
 }

  function fEjecutafNavega(){
   fNavega();
 }
 function fGuardar(){
   if(FRMListadoA.fGetLength() > 0){
     frm.hdBoton.value = "Agregar";
     fNavega();
   }
 }
 function fBorrar(){
   if(FRMListado.fGetLength() > 0){
     frm.hdBoton.value = "Borrar";
     fNavega();
   }
 }

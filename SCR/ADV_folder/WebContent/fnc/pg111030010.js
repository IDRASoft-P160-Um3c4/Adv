// MetaCD=1.0
 // Title: pg111010101.js
 // Description: JS "Catálogo" de la entidad GRLOpinionEntidad
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var lTransaccion = false;
 var aResTemp = new Array();
 var lGuardar = "";
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
   cPaginaWebJS = "pg" + iNDSADM + "1010102.js";
   if(top.fGetTituloPagina)
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"CONFIGURAR OPINIONES":cTitulo;
   fSetWindowTitle();
 }

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
          InicioTabla("",0,"100%","","center");
            TDEtiCheckBox("EEtiqueta",0,"Detener?","lDetener","1",true,"Detener");
            ITD("",0,"0","","center","left");
              Liga("Comienza loop de entrega de trámites","fNavega();","Búsqueda de Dependencia Externa...");
            FTD();
          FinTabla();
          ITRTD("",0,"","","top");
          InicioTabla("",0,"100%","","center");
            TDEtiCheckBox("EEtiqueta",0,"Detener?","lDetener1","1",true,"Detener");
            ITD("",0,"0","","center","left");
              Liga("Comienza loop de trámites","fNavega1();","Búsqueda de Dependencia Externa...");
            FTD();
          FinTabla();
     FTDTR();
       FTDTR();
       Hidden("iEjercicio",2009);
       Hidden("iNumSolicitud",1);
       Hidden("cUsuario",1);
       Hidden("lOficinas",false);
     FinTabla();
   fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  if(frm.lDetener.checked==false){
    frm.hdFiltro.value="RS.iEjercicio=2009 AND RS.iNumSolicitud=1";
    frm.hdOrden.value="";
    frm.hdNumReg.value = 100000;
    fEngSubmite("pgTRARegSolicitud2B.jsp","Listado");
  }
}
function fNavega1(){
  if(frm.lDetener1.checked==false){
    frm.hdFiltro.value="TRARegSolicitud.iEjercicio=2009 AND TRARegSolicitud.iNumSolicitud=1";
    frm.hdOrden.value="";
    frm.hdNumReg.value = 100000;
    fEngSubmite("pgTRADatosSolicitud.jsp","Listado1");
  }
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
     fNavega();
   }
   if(cId == "Listado1" && cError==""){
     fNavega1();
   }
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
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

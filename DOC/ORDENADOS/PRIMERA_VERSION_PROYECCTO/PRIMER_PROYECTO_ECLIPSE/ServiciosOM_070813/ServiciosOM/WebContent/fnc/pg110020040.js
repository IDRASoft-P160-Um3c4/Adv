// MetaCD=1.0
 // Title: pg111020080.js
 // Description: JS "Catálogo" de la entidad TRARegReqXTram
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Hanksel Fierro Medina || ICaballero || LEquihua
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var lCancelado = false;
 var lModificando = false;
 var lEjecuta = true;
 var aArreglo = new Array();
 var aResReporte = new Array();
 var idUser = fGetIdUsrSesion();
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020040.js";
   if(top.fGetTituloPagina){
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
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
     ITRTD("",0,"","","center","top");
       InicioTabla("ETablaInfo",0,"75%","","","",1);
         ITR();
           TDEtiCampo(false,"EEtiqueta",0,"Usuario:","cNomUsuario","",80,50,"Usuario","fMayus(this);","","","","ECampo",3);
         FTR();
         ITRTD("",4);
           InicioTabla("",0);
             fDefOficXUsr(false);
           FinTabla();
         FTDTR();
         ITR();
           TDEtiSelect(false,"EEtiqueta",0,"Proceso:","iCveProceso",""); //fCargaProducto();
      //     TDEtiSelect(false,"EEtiqueta",0,"Producto:","iCveProducto","fNavega();");
         FTR();
         FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Mes:","iMes","",2,2,"Mes","fMayus(this);","","","","ECampo",0);
              TDEtiCampo(false,"EEtiqueta",0,"Año:","iAnio","",4,4,"Año","fMayus(this);","","","","ECampo",0);
         FTDTR();
       FinTabla();
     FTDTR();
     ITR();
        InicioTabla("",0,"75%","","center");
          ITRTD("",0,"","40","center","bottom");
             IFrame("IPanel01A","95%","34","Paneles.js");
          FTDTR();
        FinTabla();
      FTR();

//     ITRTD("",2,"","","center");
//       BtnImg("Guardar","guardar","fGuardar();");
//     FTDTR();

   FinTabla();
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("iCveUsuario",idUser);
     Hidden("iEjercicio","1");
     Hidden("iCveCausaPNC","");

     Hidden("iOficina","");
     Hidden("iDepto","");
     Hidden("iOficinaEnvia","");
     Hidden("iDeptoEnvia","");

     Hidden("iProceso","");
     Hidden("iConsec",0);
   fFinPagina();
 }

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
//  fDisabled(true,"cNomUsuario,");
  FRMPanel = fBuscaFrame("IPanel01A");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fHabilitaReporte(true);
  FRMPanel.fShow("Rep,");
//  FRMPanel.fSetTraStatus("Sav,");

   frm = document.forms[0];
   frm.hdBoton.value="Primero";
   fCargaUsuario();
 }
 // LLAMADO al JSP específico para la navegación de la página

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
   fResOficDeptoUsr(aRes,cId,cError,false);

   if(cId == "cNomUsuario" && cError==""){
     frm.cNomUsuario.value=aRes[0][0];
     frm.iCveUsuario.value = idUser;
     fCargaOficDeptoUsr(false);
   }
   if(cId == "cIdProceso" && cError==""){
     fFillSelect(frm.iCveProceso,aRes,false,frm.iCveProceso.value,0,1);
//     alert("Cargué proceso");
   //  fCargaProducto();
   }

   if(cId == "Reporte" && cError==""){
     aResReporte = aRes;
   }

 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
 }

 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fImprimir(){
    fAlert("La función Imprimir está deshabilitada");
 //   self.focus();
 //   window.print();
 }
 /*
 function fVerifica(){
 //  if (frm.cTramite.value != "") {
      fAbreSubWindowPermisos("pgVerificacion","800","400");
    //  fNavega();
 //  }
 //  else
  //   fAlert (" \nDebe seleccionar una solicitud.");
 }
*/

function fReporte(){
   if(frm.iCveDeptoUsr.value >= 0 && frm.iCveOficinaUsr.value >= 0 &&
       frm.iCveProceso.value > 0 && frm.iMes.value > 0 && frm.iMes.value < 12 &&
       frm.iAnio.value > 0){
       cFiltrosRep = frm.iCveOficinaUsr.value+","+frm.iCveDeptoUsr.value+","+
       frm.iCveProceso.value+","+frm.iMes.value+","+frm.iAnio.value+","+
       frm.iCveOficinaUsr.options[frm.iCveOficinaUsr.selectedIndex].text+","+
       frm.iCveDeptoUsr.options[frm.iCveDeptoUsr.selectedIndex].text+","+
       frm.iCveProceso.options[frm.iCveProceso.selectedIndex].text+","+
       cSeparadorRep;
       cClavesModulo = "0,"
       cNumerosRep = "3,"
       fReportes();
   }else{
      fAlert("\n-Introduzca todos los datos solicitados \n"+
      "y verifique que el mes proporcionado esté entre 1 y 12");
   }
}

 // LLAMADO al JSP específico para obtener datos del usuario
 function fCargaUsuario(){
   frm.hdLlave.value = "ICVEUSUARIO";
   frm.hdSelect.value = "SELECT CNOMBRE||' '|| CAPPATERNO||' '|| CAPMATERNO AS cNOMBREUSU FROM SEGUsuario" +
   " where ICVEUSUARIO="+idUser;
   fEngSubmite("pgConsulta.jsp","cNomUsuario");
 }

function fOficinaUsrOnChangeLocal(){
  fFillSelect(frm.iCveProceso,new Array,"",0,0,0);
//  fFillSelect(frm.iCveProducto,new Array,"",0,0,0);
  fSelectSetIndexFromValue(frm.iCveDeptoUsr, frm.iDepto.value);
  fDeptoUsrOnChange();
 }
 function fDeptoUsrOnChangeLocal(){
   fCargaProceso();
 }

 function fCargaProceso(){
   frm.hdLlave.value = "ICVEOFICINA,ICVEDEPARTAMENTO";
   frm.hdSelect.value = "SELECT DISTINCT(GRLPRODXOFICDEPTO.ICVEPROCESO), " +
   "CDSCPROCESO, ICVEOFICINA, ICVEDEPARTAMENTO FROM GRLPRODXOFICDEPTO " +
   "JOIN GRLPROCESO ON GRLPRODXOFICDEPTO.ICVEPROCESO = GRLPROCESO.ICVEPROCESO " +
   "WHERE ICVEOFICINA = " + frm.iCveOficinaUsr.value +
   " AND ICVEDEPARTAMENTO = " + frm.iCveDeptoUsr.value;

   fEngSubmite("pgConsulta.jsp","cIdProceso");
 }


function fGetiNumSolicitud(){
//  return frm.iNumSolicitud.value;
}
function fGetiEjercicio(){
//  return frm.iEjercicio.value;
}
function fGetcDscTramite(){
//  return frm.cDscTramite.value;
}
function fGetDscModalidad(){
//  return frm.cDscModalidad.value;
}

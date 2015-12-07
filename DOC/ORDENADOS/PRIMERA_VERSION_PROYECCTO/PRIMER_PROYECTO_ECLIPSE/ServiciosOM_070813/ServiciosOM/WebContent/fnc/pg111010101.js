// MetaCD=1.0
 // Title: pg111010101.js
 // Description: JS "Catálogo" de la entidad GRLOpinionEntidad
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResTemp = new Array();
 var lGuardar = "";
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg" + iNDSADM + "1010101.js";
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
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro01","95%","34","Filtros.js");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cDscTramite","",80,80,"Trámite","fMayus(this);");
      //     FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cDscModalidad","",18,18,"Modalidad","fMayus(this);");
           FITR();
         FinTabla();
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado01","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
             fDefOficXUsr(true);
           FTDTR();
            TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
           FTDTR();



             Hidden("lEsTramite","");
           FITR();
           FITR();
           FITR();
           FITR();
           FITR();
           FITR();
           FITR();
           FITR();


              Hidden("lEsOpinionInterna","");
              Hidden("lVigente","");
              Hidden("iCveOpinionEntidad","");
              Hidden("lEsTramiteBOX","");
              Hidden("iCveTramite","");
              Hidden("iCveModalidad","");
              Hidden("iCveSistema","");
              Hidden("iCveModulo","");
              Hidden("iCvePersona","");
              Hidden("iCveDomicilio","");
              Hidden("iCveOficinaOpn","");
              Hidden("iCveDepartamentoOpn","");
              Hidden("fResultado ");
              Hidden("hdLlave");
              Hidden("hdSelect");
              Hidden("cDscOficina");
              Hidden("cDscBreveo");
              Hidden("cDscDepartamento");
              Hidden("hdCveOficina","");
              Hidden("hdCveDepto","");
              Hidden("hdCveModalidad","");
              Hidden("hdCveTramite","");
              Hidden("hdOficina");
              Hidden("hdDepto");

           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
          IFrame("IPanel01","95%","34","Paneles.js");
     InicioTabla("",0,"95%","","","",1);
       ITRTD("100%",0,"","","center");
         Liga("Configurar Reportes","fConfigurarReportes();","Configurar Reportes");

     //FTD();
     FinTabla();
       FTDTR();

     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel01");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado01");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Oficina,Departamento,Vigente,");
   FRMListado.fSetCampos("12,13,11,");
   FRMListado.fSetAlinea("left,left,center,");
   FRMListado.fSetDespliega("texto,texto,logico,");
   FRMFiltro = fBuscaFrame("IFiltro01");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
   FRMFiltro.fSetFiltra("");
   FRMFiltro.fSetOrdena("");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fCargaOficDeptoUsr(true);
   fRecibeValores();
   fNavega();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = "GRLOpinionEntidad.iCveTramite = " + frm.iCveTramite.value + " And GRLOpinionEntidad.iCveModalidad = " +frm.iCveModalidad.value;// + "And GRLOpinionEntidad.iCveModalidad =" + frm.iCveModalidad;
   frm.hdOrden.value =  FRMFiltro.fGetOrden();
   frm.hdNumReg.value =  10000;
   return fEngSubmite("pgGRLOpinionEntidad.jsp","Listado");
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
     aResTemp = aRes;
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

     fResOficDeptoUsr(aRes,cId,cError,true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    fSelectSetIndexFromValue(frm.iCveOficinaUsr, -0);
    fOficinaUsrOnChange(true);
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("cDscModalidad,cDscTramite,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,");
    frm.lVigenteBOX.checked=true;
    fDisabled(false,"iCveOpinionEntidad,cDscModalidad,cDscTramite,lVigenteBOX,","--");
    FRMListado.fSetDisabled(true);

 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){

   lGuardar=0;
   for(cont=0;cont < aResTemp.length;cont++)
    if(aResTemp[cont][7] == frm.iCveOficinaUsr.value & aResTemp[cont][8] == frm.iCveDeptoUsr.value)
      lGuardar = 1;

  if(lGuardar == 1)
    alert("No se puede guardar el mismo registro");
  else{
    frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
          FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
   lGuardar=0;
   for(cont=0;cont < aResTemp.length;cont++)
    if(aResTemp[cont][7] == frm.iCveOficinaUsr.value & aResTemp[cont][8] == frm.iCveDeptoUsr.value)
      lGuardar = 1;

   if(lGuardar == 0 || frm.hdOficina.value == frm.iCveOficinaUsr.value & frm.hdDepto.value == frm.iCveDeptoUsr.value){
     frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
     if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }

   }else
     fAlert("No se puede guardar el mismo registro");
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
   frm.hdOficina.value = frm.iCveOficinaUsr.value;
   frm.hdDepto.value = frm.iCveDeptoUsr.value;
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iCveOpinionEntidad,cDscTramite,cDscModalidad,iCveOficinaUsr,iCveDeptoUsr,");
   FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");
    fDisabled(true);
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.iCveOpinionEntidad.value = aDato[0];
    frm.lEsTramite.value = aDato[1];

    if (parseInt(aDato[7]) > 0)
      fSelectSetIndexFromValue(frm.iCveOficinaUsr, aDato[7]);
    else
      fSelectSetIndexFromValue(frm.iCveOficinaUsr, 0);

    fOficinaUsrOnChange(true);
    if (parseInt(aDato[8]) > 0)
      fSelectSetIndexFromValue(frm.iCveDeptoUsr, aDato[8]);
    else
      fSelectSetIndexFromValue(frm.iCveDeptoUsr, 0);

    frm.cDscOficina.value = aDato[12];
    frm.cDscDepartamento.value = aDato[13];
    frm.iCveSistema.value = aDato[5];
    frm.iCveModulo.value = aDato[6];
    frm.iCvePersona.value = aDato[9];
    frm.iCveDomicilio.value = aDato[10];
    frm.lVigente.value = aDato[11];
    if (frm.lVigente.value==1) frm.lVigenteBOX.checked=true;
       else frm.lVigenteBOX.checked=false;

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


function fRecibeValores(){
  frm.iCveTramite.value = top.opener.fGetCveTramite();
  frm.iCveModalidad.value = top.opener.fGetCveModalidad();
  frm.cDscTramite.value = top.opener.fGetcDscTramite();
  frm.cDscModalidad.value = top.opener.fGetcDscModalidad();

 fNavega();

}


function fConfigurarReportes(){
  if (FRMListado.fGetLength()>0){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveOpinionEntidad,cDscTramite,cDscModalidad,");
    FRMListado.fSetDisabled(true);
   fAbreSubWindowPermisos("pg111020161","900","600");
   fNavega();
  } else alert("Debe de existir algún registro a configurar");

}

function fGetClaveOficina(){
 return frm.iCveOficinaUsr.value;
}

function fGetClaveDepto(){
 return frm.iCveDeptoUsr.value;
}

function fGetClaveTramite(){
 return frm.iCveTramite.value;
}

function fGetClaveModalidad(){
 return frm.iCveModalidad.value;
}

function fGetClaveOpinionEntidad(){
 return frm.iCveOpinionEntidad.value;
}

function fGetDscTramite(){
 return frm.cDscTramite.value;
}

function fGetDscModalidad(){
 return frm.cDscModalidad.value;
}

function fGetDscOficina(){
 return frm.cDscOficina.value;
}

function fGetDscDepto(){
 return frm.cDscDepartamento.value;
}

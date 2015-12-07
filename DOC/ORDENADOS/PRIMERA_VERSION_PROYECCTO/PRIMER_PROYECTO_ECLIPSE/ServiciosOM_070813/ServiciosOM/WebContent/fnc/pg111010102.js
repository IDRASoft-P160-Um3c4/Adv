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
       ITRTD("",0,"","40","center","top");
         IFrame("IFiltro102","95%","34","Filtros.js");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
 //          ITRTD("ETablaST",5,"","","center");
           FTDTR();
              TDEtiCampo(false,"EEtiqueta",0,"Trámite:","cDscTramite","",80,80,"Trámite","fMayus(this);");
      //     FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Modalidad:","cDscModalidad","",18,18,"Modalidad","fMayus(this);");
           FITR();
         FinTabla();
       FTDTR();
       ITRTD("",0,"","160","center","top");
         IFrame("IListado102","95%","160","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");

          InicioTabla("",0,"100%","","center");
            ITD("",0,"0","","center","left");
              Liga("Búsqueda de Dependencia Externa","fAbre();","Búsqueda de Dependencia Externa...");
            FTD();
          FinTabla();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"0","","","",1);
           ITRTD("ETablaST",6,"","","center");
             TextoSimple("Datos del Solicitante");
           FTDTR();
           ITR();
              TDEtiCampo(true,"EEtiqueta",0,"Dependencia Externa:","cNomRazonSocial","",95,95," Nombre o Razón Social","fMayus(this);","","",false,"EEtiquetaL",5);
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Representante Dependencia Externa:","cRepresentante","",95,95," Nombre Representante de la Dependencia Externa","fMayus(this);","","",false,"EEtiquetaL",5);
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Domicilio:",94,3,"cDscDomicilio","","Domicilio","fMayus(this);","","",false,false,false,"EEtiquetaL",5);
           FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Dirigido a:","cOpinionDirigidoA","",95,95,"OpinionDirigidoA","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Puesto:","cPuestoOpinion","",95,95,"PuestoOpinion","fMayus(this);");
           FITR();

           TDEtiCheckBox("EEtiqueta",0,"Vigente:","lVigenteBOX","1",true,"Vigente");
           FTR();
         FinTabla();
       FTDTR();
         ITRTD("",0,"","1","center");
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel11","95%","34","Paneles.js");
      InicioTabla("",0,"95%","","","",1);
        ITRTD("100%",0,"","","center");
     //ITD();
	 Liga("Configurar Reportes","fConfigurarReportes();","Configurar Reportes");

      FinTabla();
       FTDTR();
       Hidden("hdPersona");
       Hidden("iCveTramite");
       Hidden("iCveOficinaOpn");
       Hidden("iCveDepartamentoOpn");
       Hidden("iCveModalidad");
       Hidden("iCveOpinionEntidad");
       Hidden("lEsTramite");
       Hidden("lEsOpinionInterna");
       Hidden("iCveSistema");
       Hidden("iCveModulo");
       Hidden("iCvePersona");
       Hidden("lVigente");
       Hidden("iCveDomicilio");

     FinTabla();
   fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel11");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado102");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Dependencia Externa,Domicilio,Vigente,");
  FRMListado.fSetCampos("12,13,11,");
  FRMListado.fSetAlinea("left,left,center,");
  FRMListado.fSetDespliega("texto,texto,logico,");
  FRMFiltro = fBuscaFrame("IFiltro102");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow("Reg,Nav,");
  FRMFiltro.fSetFiltra("");
  FRMFiltro.fSetOrdena("");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  fRecibeValores();
  fNavega();
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdFiltro.value =  "GRLOpinionEntidad.iCveTramite = " + frm.iCveTramite.value + " And GRLOpinionEntidad.iCveModalidad = " +frm.iCveModalidad.value;
  frm.hdOrden.value =  FRMFiltro.fGetOrden();
  frm.hdNumReg.value =  10000;
  return fEngSubmite("pgGRLOpinionEntidadA.jsp","Listado");
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
     for (i=0;i<aRes.length;i++)
        aRes[i][13]= aRes[i][13] + " " + aRes[i][14]+ " " + aRes[i][15];

     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }



}

// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked("cDscModalidad,cDscTramite,iCveTramite,iCveModalidad,");
    lTransaccion = true;
    frm.iCvePersona.value = 0;


    frm.lVigenteBOX.checked=true;
    fDisabled(false,"iCveOpinionEntidad,cDscModalidad,cDscTramite,lVigenteBOX,cNomRazonSocial,cDscDomicilio,","--");
    FRMListado.fSetDisabled(true);
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
   lGuardar=0;
   for(cont=0;cont < aResTemp.length;cont++){
    if(aResTemp[cont][9] == frm.iCvePersona.value)lGuardar = 1;
    }

    if(lGuardar == 1)
      alert("No se puede guardar el mismo registro");
    else{
      if(frm.iCvePersona.value > 0){
        lTransaccion = false;
        frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;

        if(fValidaTodo()==true){
          if(fNavega()==true){
            FRMPanel.fSetTraStatus("UpdateComplete");
            fDisabled(true);
            FRMListado.fSetDisabled(false);
          }
        }
      }else alert("Favor de indicar una Dependencia Externa y su Domicilio");
    }
}

// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
   lGuardar=0;
   for(cont=0;cont < aResTemp.length;cont++){
    if(aResTemp[cont][9] == frm.iCvePersona.value)lGuardar = 1;
    }

    if(lGuardar == 0 || frm.hdPersona.value == frm.iCvePersona.value){
    lTransaccion = false;
    frm.lVigenteBOX.checked==true?frm.lVigente.value=1:frm.lVigente.value=0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
    }else{
    alert("No se puede guardar el mismo registro");
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
    frm.hdPersona.value = frm.iCvePersona.value;
    lTransaccion = false;
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveOpinionEntidad,cDscTramite,cDscModalidad,cNomRazonSocial,cDscDomicilio,");
    FRMListado.fSetDisabled(true);

}

// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
    lTransaccion = false;
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
   lTransaccion = false;
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
   }
}

// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
    frm.iCveOpinionEntidad.value = aDato[0];

    frm.cOpinionDirigidoA.value = aDato[20]
    frm.cPuestoOpinion.value = aDato[21]
    if(FRMListado.fGetLength()>0){
    frm.cDscDomicilio.value = aDato[13] + " " +aDato[16] + " C.P. " +aDato[17] + ", " +aDato[19] + ", " +aDato[18] + " " + " ";
    }
    frm.cNomRazonSocial.value = aDato[12];
    frm.iCvePersona.value = aDato[9];
    frm.iCveDomicilio.value = aDato[10];
    frm.lVigente.value = aDato[11];
    if (frm.lVigente.value==1) frm.lVigenteBOX.checked=true;
       else frm.lVigenteBOX.checked=false;
    frm.cRepresentante.value = aDato[22];


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

function fGetCveSol(){
  return frm.iCvePersona.value;
}

function fGetSol(){
  return frm.cNomRazonSocial.value;
}

function fGetCveRep(){
   return frm.iCveRepLegal.value;
}

function fGetRep(){
   return frm.cNomRazonSocial2.value;
}

function fGetRFC(){
   return frm.cRFC.value;
}

// Definir en paginas que requieran datos de persona o persona y representante legal
function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,
                         iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,
                         iCvePais,cDscPais,iCveEntidadFed,cDscEntidadFed,iCveMunicipio,cDscMunicipio,iCveLocalidad,
                         cDscLocalidad,lPredeterminado,cDscTipoDomicilio,cDscDomicilio){
  frm.iCvePersona.value     = iCvePersona
  frm.cNomRazonSocial.value = cNomRazonSocial;
  frm.cDscDomicilio.value   = cDscDomicilio;
  frm.iCveDomicilio.value = iCveDomicilio;



//  cDscDomicilio;
}


// Definir en paginas que requieran datos de persona o persona y representante legal
function fGetParametrosConsulta(frmDestino){
  var lShowPersona     = true;
  var lShowRepLegal    = false;
  var lEditPersona     = true;
  var lEditDomPersona  = true;
  var lEditRepLegal    = false;
  var lEditDomRepLegal = false;
  if (frmDestino){
    if (frmDestino.setShowValues)
      frmDestino.setShowValues(lShowPersona, lShowRepLegal, "");
    if (frmDestino.setEditValues)
      frmDestino.setEditValues(lEditPersona, lEditDomPersona, lEditRepLegal, lEditDomRepLegal);
  }
}

function fRecibeValores(){
  frm.iCveTramite.value = top.opener.fGetCveTramite();
  frm.iCveModalidad.value = top.opener.fGetCveModalidad();
  frm.cDscTramite.value = top.opener.fGetcDscTramite();
  frm.cDscModalidad.value = top.opener.fGetcDscModalidad();


 fNavega();

}

function fAbre(){
 if (lTransaccion == true){
    fAbreSolicitante();
 }else alert("Debe de oprimir \"Nuevo\" ");
}


function fConfigurarReportes(){
  if (FRMListado.fGetLength()>0){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"cDscTramite,cDscModalidad,");
    FRMListado.fSetDisabled(true);
   fAbreSubWindowPermisos("pg111020162","900","600");
   fNavega();
  } else alert("Debe de existir algún registro a configurar");

}

function fGetPersona(){
 return frm.cNomRazonSocial.value;
}

function fGetDirigido(){
 return frm.cOpinionDirigidoA.value;
}

function fGetClaveTramite(){
 return frm.iCveTramite.value;
}

function fGetClaveModalidad(){
 return frm.iCveModalidad.value;
}

function fGetDscTramite(){
 return frm.cDscTramite.value;
}

function fGetDscModalidad(){
 return frm.cDscModalidad.value;
}

function fGetPuesto(){
 return frm.cPuestoOpinion.value;
}

function fGetClaveOpinionEntidad(){
 return frm.iCveOpinionEntidad.value;
}



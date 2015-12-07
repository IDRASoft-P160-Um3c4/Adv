
// MetaCD=1.0
 // Title: pg111020260.js
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: iCaballero

 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResTemp = new Array();
 var iCveEtapa = 0;
 var iTramiteCertif = "";
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020260.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","10","center","top");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           fDefOficXUsr();
         FinTabla();
       FTDTR();
       ITRTD("",0,"","10","center","top");
         IFrame("IFiltro","0%","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","center",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("REASIGNAR TR�MITE");
           FTDTR();
           ITD("EEtiqueta",0,"0","","center","middle");
            TextoSimple("Departamento:");
           FTD();
           ITD("EEtiquetaL",0,"0","","center","middle");
             Select("iCveDepartamentoAsg");
           FTD();
         FinTabla();
           ITRTD("",0,"","30","center","top");
             Liga("Reasignar Tr�mite","fRecibeSolicitud();","Reasignar Tr�mite a otro Departamento");
           FTDTR();

       FTDTR();

       if (top.fGetUsrID())
         Hidden("hdCveUsuario",top.fGetUsrID());
       else
        Hidden("hdCveUsuario",1);

       Hidden("hdLlave","");
       Hidden("hdSelect","");
       Hidden("iEjercicio","");
       Hidden("iNumSolicitud","");
       Hidden("iCveTramite","");
       Hidden("iCveModalidad","");
       Hidden("lAnexo","");
       Hidden("hdEtapa","EtapaRegistro");
       Hidden("hdEtapaGuardar","EtapaRegistro");
       Hidden("hdPropEspecifica1", "TramiteCertificacionDoc");

       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow(",");
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Reasignar,Anexos,Ejercicio,N�m. Solicitud,Tr�mite,Modalidad,");
   FRMListado.fSetCampos("0,1,4,5,");
   FRMListado.fSetObjs(0,"Caja");
   FRMListado.fSetObjs(1,"Caja");
   FRMListado.fSetAlinea("center,center,center,left,left,left,");
   FRMFiltro = fBuscaFrame("IFiltro");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Ejercicio,iEjercicio,N�m. Solicitud,iNumSolicitud,");
   FRMFiltro.fSetOrdena("Ejercicio,iEjercicio,N�m. Solicitud,iNumSolicitud,");
   fDisabled(false);
   frm.hdBoton.value="Primero";

   fCargaOficDeptoUsr();
 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina

 function fNavega(){
//   frm.hdFiltro.value =  " and TRARegSolicitud.iCveTramite = " + iTramiteCertif;
   frm.hdFiltro.value = "";
   frm.hdOrden.value =  "iEjercicio,iNumSolicitud";
   frm.hdNumReg.value =  1000;
   return fEngSubmite("pgTRARecepcion.jsp","Listado");
 }
 // RECEPCI�N de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iTramiteCertif){
   if(cError=="Guardar")
     fAlert("Existi� un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existi� un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cError!=""){
     fAlert('n - '+cError);
     return;
   }

   if(cId == "Listado" && cError==""){
     aResTemp = aRes;
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);

     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
   }

   if(cId == "CIDOficinaDeptoXUsr" && cError==""){
     this.iTramiteCertif = iTramiteCertif;
     fResOficDeptoUsr(aRes,cId,cError);
     fCargaTodosDeptos();
   }

   if(cId == "CIDOficinaDeptoXUsrTodas" && cError == ""){
     fFillSelect(frm.iCveDepartamentoAsg,aRes,false,frm.iCveDepartamentoAsg.value,0,2);
   }
 }

 // LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");
    fDisabled(false);
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Listado cada vez que se selecciona un rengl�n
 function fSelReg(aDato){
 }
 // FUNCI�N donde se generan las validaciones de los datos ingresados
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

function fDefOficXUsr(){
  //Necesario definir los campos ocultos  Hidden("fResultado "); y Hidden("hdLlave");
  var cTx;
  cTx = ITRTD("EEtiquetaC",0,"100%","20","center")+
      InicioTabla("",0,"","","center")+
        ITR()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Oficina:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveOficinaUsr","fOficinaUsrOnChange();fCargaTodosDeptos();")+
          FTD()+
          ITD("EEtiqueta",0,"0","","center","middle")+
            TextoSimple("Departamento:")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
            Select("iCveDeptoUsr","if(fDeptoUsrOnChange)fDeptoUsrOnChange();")+
          FTD()+
          ITD("EEtiquetaL",0,"0","","center","middle")+
          BtnImg("Buscar","lupa","fBuscar();","Buscar")+
          FTD()+
        FTR()+
      FinTabla()+
    FTDTR();
  return cTx;
}


function fBuscar(){
  fNavega();
}

function fDeptoUsrOnChangeLocal(){
}

function fRecibeSolicitud(){
 frm.iEjercicio.value    = "";
 frm.iNumSolicitud.value = "";
 frm.iCveTramite.value   = "";
 frm.iCveModalidad.value = "";
 frm.lAnexo.value="";


 if(!confirm("�Esta seguro que desea reasignar el tr�mite?")){
   return;
 }

 aCBoxReq    = FRMListado.fGetObjs(0);
 aCBoxAnexo = FRMListado.fGetObjs(1);

 for(cont=0;cont < aCBoxReq.length;cont++){
  if(aCBoxReq[cont]){
    if (frm.iEjercicio.value=="") frm.iEjercicio.value=aResTemp[cont][0]; else frm.iEjercicio.value+=","+aResTemp[cont][0];
    if (frm.iNumSolicitud.value=="" ) frm.iNumSolicitud.value=aResTemp[cont][1]; else frm.iNumSolicitud.value+=","+aResTemp[cont][1];
    if (frm.iCveTramite.value=="" ) frm.iCveTramite.value=aResTemp[cont][2]; else frm.iCveTramite.value+=","+aResTemp[cont][2];
    if (frm.iCveModalidad.value=="" ) frm.iCveModalidad.value=aResTemp[cont][3]; else frm.iCveModalidad.value+=","+aResTemp[cont][3];

    if (frm.lAnexo.value==""){
      if (aCBoxAnexo[cont]) frm.lAnexo.value="1"; else frm.lAnexo.value="0";
    }
    else{
      if (aCBoxAnexo[cont]) frm.lAnexo.value+=",1"; else frm.lAnexo.value+=",0";
    }
  }
 }

 if (frm.iEjercicio.value == ""){
   fAlert ('\n - Seleccione al menos un registro para hacer esta operaci�n.');
   return;
 }

 frm.hdBoton.value = "RecibeTramite";
 //frm.hdFiltro.value =  " TRARegSolicitud.iCveTramite = " + iTramiteCertif;
 frm.hdOrden.value =  "iEjercicio,iNumSolicitud";
 frm.hdNumReg.value =  1000;
 fEngSubmite("pgTRARecepcion.jsp","Listado");
}

function fCargaTodosDeptos(){
  frm.hdLlave.value = "GRLDeptoXOfic.iCveOficina,GRLDeptoXOfic.iCveDepartamento";
  frm.hdSelect.value = "SELECT GRLDeptoXOfic.iCveDepartamento, "+
                           "GRLDepartamento.cDscDepartamento, GRLDepartamento.cDscBreve as cDscBreveDepto "+
                           "FROM GRLDeptoXOfic "+
                           "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLDeptoXOfic.iCveDepartamento "+
                           "WHERE GRLDeptoXOfic.iCveOficina = " + frm.iCveOficinaUsr.value +
                           " and  GRLDeptoXOfic.iCveDepartamento > 0 "+
                           "ORDER BY cDscBreveDepto";
  frm.hdNumReg.value = 100000;
  fEngSubmite("pgConsulta.jsp","CIDOficinaDeptoXUsrTodas");
}

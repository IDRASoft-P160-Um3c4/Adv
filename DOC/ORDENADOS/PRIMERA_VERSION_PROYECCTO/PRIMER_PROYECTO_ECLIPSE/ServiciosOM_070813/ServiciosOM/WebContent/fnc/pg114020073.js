// MetaCD=1.0
// Title: pg114020063.js
// Description: JS "Cat�logo" de la entidad INSAutorizaExpVer
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Sandor Trujillo Q.
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg114020073.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",5,"100%","","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"","","top");
      InicioTabla("",0,"80%","","center");
        ITRTD("",5,"","0","center","top");
          IFrame("IListado3","80%","0","Listado.js","yes",true);
        FTDTR();
        FITR();
           TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",25,25,"Ejercicio","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitud","",25,25,"Solicitud","fMayus(this);");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Embarcaci�n:","cNomEmbarcacion","",25,25,"Embarcaci�n...","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"Puerto de Matricula:","cPtoMatricula","",25,25,"Matricula...","fMayus(this);");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"OMI:","cOMI","",25,25,"OMI...","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"Matricula:","cMatricula","",25,25,"Matricula...","fMayus(this);");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Tipo Navegaci�n:","cDscTipoNavega","",25,25,"Ceritifcado N�m.","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"Tipo Servicio:","cDscTipoServicio","",25,25,"Inicio de Vigencia","fMayus(this);");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Se�al Distintiva","iSenDistintiva","",25,25,"Se�al Distintiva","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"A�o Construcci�n","iAnio","",25,25,"A�o Construcci�n","fMayus(this);");
        FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Propietario:","cNomPropietario","",80,80,"Propietario..","fMayus(this);","","","","EEtiquetaL",7);
      FinTabla();
      ITRTD("",0,"","1","center");
      InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple("Medidas de la Embarcaci�n");
          FTDTR();
           TDEtiCampo(true,"EEtiqueta",0,"Eslora:","dEslora","",25,25,"Eslora","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Eslora:","iUniMedEslora","");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Manga:","dManga","",25,25,"Manga","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Manga:","iUniMedManga","");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Puntal:","dPuntal","",25,25,"Puntal","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Puntal:","iUniMedPuntal","");
        FTR();
      FinTabla();
      FTDTR();

      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
              TDEtiCampo(true,"EEtiqueta",0,"Arqueo Bruto:","dArqueoBruto","",8,8,"ArqueoBruto","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Uni Med Arqueo Bruto:","iUniMedArqueoBruto","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Arqueo Neto:","dArqueoNeto","",8,8,"ArqueoNeto","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Uni Med Arqueo Neto:","iUniMedArqueoNeto","");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Numero de Tripulantes:","cNumTripulantes","",8,8,"Fecha Expedici�n","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"No. de Pasajeros:","cNumPasajeros","",8,8,"ArqueoNeto","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Ultimo Dique Seco:","dtUltDiqueSeco","",30,30,"Fecha Expedici�n","fMayus(this);");
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
      Hidden("cPagina","pg114020063");
      Hidden("iCveVehiculo","0");
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel3","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
    Hidden("iPeso");
    Hidden("iLongitud");
    Hidden("hdPropEspecifica2");
    Hidden("hdPropEspecifica1");
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCveInspeccion");
    Hidden("iCveInspProg");
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel3");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado3");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("No OMI.,Nombre, Matricula,UAB,");
  FRMListado.fSetCampos("32,1,2,3,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
}
// LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
function fNavega(){
  frm.hdFiltro.value = " rs.iEjercicio= "+ frm.iEjercicio.value + " AND rs.iNumSolicitud = " +frm.iNumSolicitud.value;
  return fEngSubmite("pgVEHEmbarcacion.jsp","Listado3");
}
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iLong, iPeso){
  if(frm.iLongitud.value==0&&iLong>=0)frm.iPeso.value = iPeso;
  if(frm.iLongitud.value==0&&iLong>=0)frm.iLongitud.value = iLong;
  //alert(frm.iLongitud.value+" ..... "+frm.iPeso.value);

  if(cError=="Guardar")
    fAlert("Existi� un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existi� un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }
// if(cError!="")
// FRMFiltro.fSetNavStatus("Record");

  if(cId == "Listado3" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListado.fSetListado(aRes);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fCancelar();
  }

  if(cId == "IDUnidadL" && cError == ""){
    fFillSelect(frm.iUniMedEslora  ,aRes,false,frm.iUniMedEslora  .value,0,2);
    fFillSelect(frm.iUniMedManga,aRes,false,frm.iUniMedManga.value,0,2);
    fFillSelect(frm.iUniMedPuntal,aRes,false,frm.iUniMedPuntal.value,0,2);
   frm.hdFiltro.value = "UM.iCveTipoUnidad = " + frm.iPeso.value;
   frm.hdOrden.value = "";
   fEngSubmite("pgVEHUnidadMedida.jsp","IDUnidadP");
  }
  if(cId == "IDUnidadP" && cError == ""){
    fFillSelect(frm.iUniMedArqueoBruto,aRes,false,frm.iUniMedArqueoBruto.value,0,2);
    fFillSelect(frm.iUniMedArqueoNeto,aRes,false,frm.iUniMedArqueoNeto.value,0,2);
    fNavega();
  }
  if(cId == "SoloMedidas" && cError == ""){
     frm.hdFiltro.value = "UM.iCveTipoUnidad = " + frm.iLongitud.value;
     frm.hdOrden.value = "";
     fEngSubmite("pgVEHUnidadMedida.jsp","IDUnidadL")
  }
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Nuevo
function fNuevo(){
   alert("Acci�n no permitida");
   return false;
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Guardar
function fGuardar(){
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n GuardarA "Actualizar"
function fGuardarA(){
  frm.hdBoton.value = "GuardarF";
   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("Mod,");
        fDisabled(true);
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
function fModificar(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iNumSolicitud,iEjercicio,iCert,dtIniVig,cNomEmbarcacion,dtFinVig,cPtoMatricula,cMatricula,iSenDistintiva,iAnio,dtExpedicion,cOMI,cDscTipoNavega,cDscTipoServicio,cNomPropietario,");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
function fCancelar(){
   if(FRMListado.fGetLength() > 0)
     FRMPanel.fSetTraStatus("Mod,");
   else
     FRMPanel.fSetTraStatus("Mod,");
   fDisabled(true,"iNumSolicitud,iEjercicio,iCert,cNomEmbarcacion,cPtoMatricula,cMatricula,iSenDistintiva,iAnio,dtIniVig,dtFinVig,dtExpedicion,");
   FRMListado.fSetDisabled(false);
}
// LLAMADO desde el Panel cada vez que se presiona al bot�n Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un rengl�n
function fSelReg(aDato){
   frm.cNomEmbarcacion.value = aDato[2];
   frm.cPtoMatricula.value = aDato[20];
   frm.iSenDistintiva.value = aDato[25];
   frm.iAnio.value = aDato[19];
   frm.dEslora.value = aDato[3];
   frm.dManga.value = aDato[4];
   frm.dPuntal.value = aDato[5];
   frm.dArqueoBruto.value = aDato[6];
   frm.dArqueoNeto.value = aDato[7];
   frm.iCveVehiculo.value = aDato[26];
   frm.cDscTipoNavega.value=aDato[27];
   frm.cDscTipoServicio.value=aDato[28];
   frm.cOMI.value = aDato[29];
   frm.iCveInspProg.value = aDato[30];
   frm.cNumTripulantes.value = aDato[31];
   frm.cNumPasajeros.value = aDato[32];
   frm.dtUltDiqueSeco.value=aDato[33];
   frm.iCveInspeccion.value=aDato[34];
   frm.cMatricula.value = aDato[18];
   frm.cNomPropietario.value = aDato[35];
   fSelectSetIndexFromValue(frm.iUniMedEslora,aDato[8]);
   fSelectSetIndexFromValue(frm.iUniMedManga,aDato[9]);
   fSelectSetIndexFromValue(frm.iUniMedPuntal,aDato[10]);
   fSelectSetIndexFromValue(frm.iUniMedArqueoBruto,aDato[11]);
   fSelectSetIndexFromValue(frm.iUniMedArqueoNeto,aDato[12]);
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
function fBusqueda(){
   fNavega();
}
function fSetValues(iEjercicio, iNumSolicitud){
   frm.iNumSolicitud.value = iNumSolicitud;
   frm.iEjercicio.value = iEjercicio;
   frm.hdLlave.value = "";
   frm.hdSelect.value= "SELECT ICVEUNIDADMEDIDA FROM VEHUnidadMedida";
   frm.hdPropEspecifica2.value = "ValPeso";
   frm.hdPropEspecifica1.value = "ValLongitud";
   fEngSubmite("pgConsulta.jsp","SoloMedidas");
}
function fGetiEjercicio(){
   return frm.iEjercicio.value;
}
function fGetiNumSolicitud(){
   return frm.iNumSolicitud.value;
}
function fSetEmbarcacion(iCveVehiculo,cNomEmbarcacion,cPropietario,cNumOMI,cMatricula,cNumSerie,cTipoServ,cTipoNavega,hdPropOPoseedor,iFolioRPMN,cPaisAbanderamiento,cTipoEmbarcacion,cSenalDist ){
  frm.iCveVehiculo.value = iCveVehiculo;
  frm.cNomEmbarcacion.value = cNomEmbarcacion;
  frm.cMatricula.value = cMatricula;
  frm.iSenDistintiva.value = cSenalDist;
  frm.cOMI.value = cNumOMI;
  frm.cDscTipoNavega.value = cTipoNavega;
  frm.cDscTipoServicio.value = cTipoServ;
}
function fBuscaEmb(){
  if(frm.dEslora.enabled == true){
    fAbreBuscaEmbarcacion();
  }
}

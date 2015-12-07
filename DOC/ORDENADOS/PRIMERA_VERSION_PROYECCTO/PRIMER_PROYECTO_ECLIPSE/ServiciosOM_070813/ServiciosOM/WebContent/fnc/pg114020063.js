// MetaCD=1.0
// Title: pg114020063.js
// Description: JS "Catálogo" de la entidad INSAutorizaExpVer
// Company: Tecnología InRed S.A. de C.V.
// Author: Sandor Trujillo Q.
var cTitulo = "";
var FRMListado = "";
var frm;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg114020063.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",5,"100%","","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"","","top");
      InicioTabla("ETablaInfo",0,"75%","","center");
        ITRTD("",7,"","0","center","top");
          IFrame("IListado3","0","0","Listado.js","yes",true);
        FTDTR();
        ITRTD("ETablaST",7,"","","center");
            TextoSimple("Solicitud");
        FITR();
           TDEtiCampo(false,"EEtiqueta",0,"Solicitud:","iNumSolicitud","",25,25,"Solicitud","fMayus(this);");
           TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",25,25,"Ejercicio","fMayus(this);");
        FITR();
          ITRTD("ETablaST",7,"","","center");
            TextoSimple("Embarcación");
          FTDTR();
           TDEtiCampo(true,"EEtiqueta",0,"Embarcación:","cNomEmbarcacion","",25,25,"Embarcación...","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"Puerto de Matrícula:","cPtoMatricula","",25,25,"Matricula...","fMayus(this);");//cPtoMatricula
           TDEtiCampo(true,"EEtiqueta",0,"Matrícula:","cMatricula","",25,25,"OMI...","fMayus(this);");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"OMI:","cOMI","",25,25,"OMI...","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"Tipo Navegación:","cDscTipoNavega","",25,25,"Ceritifcado Núm.","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"Tipo Servicio:","cDscTipoServicio","",25,25,"Inicio de Vigencia","fMayus(this);");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Señal Distintiva","iSenDistintiva","",25,25,"Señal Distintiva","fMayus(this);");
           TDEtiCampo(true,"EEtiqueta",0,"Año Construcción","iAnio","",25,25,"Año Construcción","fMayus(this);");
           ITD();
           FTD();
        FTR();
      FinTabla();
      ITRTD("",0,"","1","center");
      InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple("Medidas de la Embarcación");
          FTDTR();
           TDEtiCampo(true,"EEtiqueta",0,"Eslora:","dEslora","",25,25,"Eslora","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Eslora:","iUniMedEslora","");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Manga:","dManga","",25,25,"Manga","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Manga:","iUniMedManga","");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Puntal:","dPuntal","",25,25,"Puntal","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Puntal:","iUniMedPuntal","");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Calado Proa:","dCaladoProa","",25,25,"Puntal","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Calado:","iUniMedCaladoProa","");
        FITR();
           TDEtiCampo(true,"EEtiqueta",0,"Calado Popa:","dCaladoPopa","",25,25,"Puntal","fMayus(this);");
           TDEtiSelect(true,"EEtiqueta",0,"Uni Med Calado:","iUniMedCaladoPopa","");
        FITR();
           Hidden("dCalado");
           Hidden("iUniMedCalado");
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
           FTR();
        FinTabla();
      FTDTR();
      FinTabla();
      Hidden("cPagina","pg114020063");
      Hidden("iCveVehiculo","0");
      ITRTD("",0,"","1","center");
        InicioTabla("",0,"100%","","","",1);
             ITD("",0,"","","center");
               Liga("Buscar Embarcacion","fBuscaEmb();");
             FTD();
        FinTabla();
      FTDTR();
    FinTabla();
    Hidden("iPeso");
    Hidden("iLongitud");
    Hidden("hdPropEspecifica2");
    Hidden("hdPropEspecifica1");
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCvePuertoMat");
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMListado = fBuscaFrame("IListado3");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("No OMI.,Nombre, Matricula,UAB,");
  FRMListado.fSetCampos("32,1,2,3,");

  fDisabled(true);

  frm.hdBoton.value="Primero";
// fNavega();
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){

  //if(parseInt(frm.iCveVehiculo.value,10)>0) frm.hdFiltro.value = " V.iCveVehiculo = "+frm.iCveVehiculo.value;
  //else 
  frm.hdFiltro.value = " s.iEjercicio= "+ frm.iEjercicio.value + " AND s.iNumSolicitud = " +frm.iNumSolicitud.value;
  return fEngSubmite("pgVEHEmbarcacion.jsp","Listado3");
}
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,iLong, iPeso){
  if(frm.iLongitud.value==0&&iLong>=0)frm.iPeso.value = iPeso;
  if(frm.iLongitud.value==0&&iLong>=0)frm.iLongitud.value = iLong;

  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
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
    fFillSelect(frm.iUniMedCaladoProa,aRes,false,frm.iUniMedPuntal.value,0,2);
    fFillSelect(frm.iUniMedCaladoPopa,aRes,false,frm.iUniMedPuntal.value,0,2);
   frm.hdFiltro.value = "UM.iCveTipoUnidad = 20 "; //+ frm.iPeso.value;
   frm.hdOrden.value = "";
   fEngSubmite("pgVEHUnidadMedida.jsp","IDUnidadP");
  }
  if(cId == "IDUnidadP" && cError == ""){
    fFillSelect(frm.iUniMedArqueoBruto,aRes,false,frm.iUniMedArqueoBruto.value,0,2);
    frm.hdFiltro.value = "UM.iCveTipoUnidad = 21 "; //+ frm.iPeso.value;
    frm.hdOrden.value = "";
    fEngSubmite("pgVEHUnidadMedida.jsp","IDUnidadNeto");
    
    //fFillSelect(frm.iUniMedArqueoNeto,aRes,false,frm.iUniMedArqueoNeto.value,0,2);
    //fNavega();
  }
  
  if(cId == "IDUnidadNeto" && cError == ""){	   
	    
	    fFillSelect(frm.iUniMedArqueoNeto,aRes,false,frm.iUniMedArqueoNeto.value,0,2);
	    fNavega();
  }  
  
  if(cId == "SoloMedidas" && cError == ""){
     frm.hdFiltro.value = "UM.iCveTipoUnidad = " + frm.iLongitud.value;
     frm.hdOrden.value = "";
     fEngSubmite("pgVEHUnidadMedida.jsp","IDUnidadL")
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   alert("Acción no permitida");
   return false;
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
}
// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
  frm.hdBoton.value = "GuardarB";
   if(fValidaTodo()==true){
      if(fNavega()==true){
        fDisabled(true);
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
   fDisabled(false,"iNumSolicitud,iEjercicio,iCert,dtIniVig,cNomEmbarcacion,dtFinVig,cPtoMatricula,cMatricula,iSenDistintiva,dtExpedicion,cDscTipoNavega,cDscTipoServicio,");
   FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
   fDisabled(true,"iNumSolicitud,iEjercicio,iCert,cNomEmbarcacion,cPtoMatricula,cMatricula,iSenDistintiva,iAnio,dtIniVig,dtFinVig,dtExpedicion,");
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
   frm.iCveVehiculo.value =       aDato[0];
   frm.cNomEmbarcacion.value =    aDato[1];
   frm.cPtoMatricula.value =      aDato[2];
   frm.iSenDistintiva.value =     aDato[3];
   frm.iAnio.value =              aDato[4];
   frm.dEslora.value =            aDato[5];
   frm.dManga.value =             aDato[6];
   frm.dPuntal.value =            aDato[7];
   frm.dArqueoBruto.value =       aDato[8];
   frm.dArqueoNeto.value =        aDato[9];
   frm.cDscTipoNavega.value=      aDato[10];
   frm.cDscTipoServicio.value=    aDato[11];
   frm.cOMI.value =               aDato[12];
   fSelectSetIndexFromValue(frm.iUniMedEslora,aDato[13]);
   fSelectSetIndexFromValue(frm.iUniMedManga,aDato[14]);
   fSelectSetIndexFromValue(frm.iUniMedPuntal,aDato[15]);
   fSelectSetIndexFromValue(frm.iUniMedArqueoBruto,aDato[16]);
   fSelectSetIndexFromValue(frm.iUniMedArqueoNeto,aDato[17]);
   frm.dCaladoPopa.value = aDato[18];
   frm.dCaladoProa.value = aDato[19];
   frm.dCalado.value = aDato[20];
   frm.cMatricula.value = aDato[21];
   fSelectSetIndexFromValue(frm.iUniMedCalado,aDato[37]);
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

function fValoresEmbarcacion(
              iCveVehiculo,cNomEmbarcacion,iCvePropietario,cPropietario,cMatricula,
              cNumOMI,cDscTipoEmbarcacion,iCveTipoNavega,cDscTipoNavega,iCveTipoServ,
              cDscTipoServ,lArtefacto,dEslora,dManga,dPuntal,
              dArqueoBruto,dArqueoNeto,dPesoMuerto,cDscUnidadMedidaEslora,cDscUnidadMedidaManga,
              cDscUnidadMedidaPuntal,cDscUnidadMedidaArqueoBruto,cDscUnidadMedidaArqueoNeto,cDscUnidadMedidaPesoMuerto,iUniMedEslora,
              iUniMedManga,iUniMedPuntal,iUniMedArqueoBruto,iUniMedArqueoNeto,iUniMedPesoMuerto){
  frm.iCveVehiculo.value = iCveVehiculo;
  frm.cNomEmbarcacion.value = cNomEmbarcacion;
  frm.cMatricula.value = cMatricula;
  frm.iSenDistintiva.value = "";
  frm.cOMI.value = cNumOMI;
  frm.cDscTipoNavega.value = cDscTipoNavega;
  frm.cDscTipoServicio.value = cDscTipoServ;

  frm.dEslora.value=dEslora;
  frm.dManga.value=dManga;
  frm.dPuntal.value=dPuntal;
  frm.dCaladoProa.value=0;
  frm.dCaladoPopa.value=0;
  frm.dArqueoBruto.value=dArqueoBruto;
  frm.dArqueoNeto.value=dArqueoNeto;
  
  frm.iUniMedArqueoBruto.value=iUniMedArqueoBruto;
  frm.iUniMedArqueoNeto.value=iUniMedArqueoNeto;
  
  frm.iUniMedEslora.value=iUniMedEslora;
  frm.iUniMedManga.value=iUniMedManga;
  frm.iUniMedPuntal.value=iUniMedPuntal;
}

/*function fValoresEmbarcacion(
	iCveVehiculo,cNomEmbarcacion,iCvePropietario,cPropietario,cMatricula,
	cNumOMI,cDscTipoEmbarcacion,iCveTipoNavega,cDscTipoNavega,iCveTipoServ,
	cDscTipoServ,lArtefacto,dEslora,dManga,dPuntal,
	dArqueoBruto,dArqueoNeto,dPesoMuerto,cDscUnidadMedidaEslora,cDscUnidadMedidaManga,
	cDscUnidadMedidaPuntal,cDscUnidadMedidaArqueoBruto,cDscUnidadMedidaArqueoNeto,cDscUnidadMedidaPesoMuerto,
	iUniMedEslora,iUniMedManga,iUniMedPuntal,iUniMedArqueoBruto,iUniMedArqueoNeto,
	iUniMedPesoMuerto,cBandera,iNumSolMatricula){
    
}*/

function fSetEmbarcacion(iCveVehiculo,cNomEmbarcacion,cPropietario,cNumOMI,cMatricula,cNumSerie,cTipoServ,cTipoNavega,hdPropOPoseedor,iFolioRPMN,cPaisAbanderamiento,cTipoEmbarcacion,cSenalDist ){
  frm.iCveVehiculo.value = iCveVehiculo;
  frm.cNomEmbarcacion.value = cNomEmbarcacion;
  frm.cMatricula.value = cMatricula;
  frm.iSenDistintiva.value = cSenalDist;
  frm.cOMI.value = cNumOMI;
  frm.cDscTipoNavega.value = cTipoNavega;
  frm.cDscTipoServicio.value = cTipoServ;
}
function fSetVarios(dArqueoBruto,dArqueoNeto,dPesoMuerto,dEslora,dManga,
           dPuntal,cPuertoAband,hdlArtefacto,iTripulacionMax,dtConstruccion,
           hdUnidMedManga,hdUnidMedArqueoNeto,hdUniMedArqueoBruto,iCveEntFedMatricula,iCveMunicMatricula,
           cDscEntFedMatricula,cDscMunicMatricula,iUniMedEslora,iUniMedManga,iUniMedPuntal,iUniMedCaladoPopa,
           iUniMedCaladoProa,iUniMedCaladoMax,iUniMedArqueoBruto,iUniMedArqueoNeto,iUniMedPesoMuerto,iUniMedVelocidadMax,
           dCaladoProa,dCaladoPopa,cMaterial){
  frm.dEslora.value=dEslora;
  frm.dManga.value=dManga;
  frm.dPuntal.value=dPuntal;
  frm.dCaladoProa.value=dCaladoProa;
  frm.dCaladoPopa.value=dCaladoPopa;
  frm.dArqueoBruto.value=dArqueoBruto;
  frm.dArqueoNeto.value=dArqueoNeto;
  frm.iUniMedEslora.value=iUniMedEslora;
  frm.iUniMedManga.value=iUniMedManga;
  frm.iUniMedPuntal.value=iUniMedPuntal;


   fSelectSetIndexFromValue(frm.iUniMedEslora,iUniMedEslora);
   fSelectSetIndexFromValue(frm.iUniMedManga,iUniMedManga);
   fSelectSetIndexFromValue(frm.iUniMedPuntal,iUniMedPuntal);
   fSelectSetIndexFromValue(frm.iUniMedArqueoBruto,iUniMedArqueoBruto);
   fSelectSetIndexFromValue(frm.iUniMedArqueoNeto,iUniMedArqueoNeto);
   fSelectSetIndexFromValue(frm.iUniMedCaladoProa,iUniMedCaladoProa);
   fSelectSetIndexFromValue(frm.iUniMedCaladoPopa,iUniMedCaladoPopa);
}

function fBuscaEmb(){
   fAbreBuscaEmbarcacionGral();
//    fAbreBuscaEmbarcacion();
}
function fGetVehiculo(){return frm.iCveVehiculo.value;}
function fGetNomEmbarcacion(){return frm.cNomEmbarcacion.value;}
function fGetOMI(){return frm.cOMI.value;}
function fGetMatricula(){return frm.cMatricula.value;}
function fGetPermisoEscritura(){return true;}
function fGetPermisoEmbarcacionesNacionales(){return true;}
function fSetEmbarcacionBuscar(window){window.fSetEmbID(frm.iCveVehiculo.value);}
function fGetEmbarcacion(){return frm.iCveVehiculo.value;}


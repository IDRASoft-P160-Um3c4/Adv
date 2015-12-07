// MetaCD=1.0
// Title: pg116010014.js
// Description: JS Consulta General de Embarcación
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Arturo López Peña
var cTitulo = "";
var FRMListado = "";
var frm;
var cUser = fGetIdUsrSesion();
var iDestinoOrigen=0;
var lRefrescando=false;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110020054.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","100%","center");
        ITRTD("",0,"","","center","top");
      InicioTabla("",0,"100%","100%","center");
        ITRTD("",0,"","","left","top");
        InicioTabla("",0,"","","left");
          ITRTD("",0,"","","center","top");
          InicioTabla("",1,"","","left");
            ITRTD("",0,"","","","top");
            InicioTabla("",0,"100%","100%","left");
              ITRTD("ETablaST",3,"","","center");
                TextoSimple("EMBARCACION DESTINO");
                FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              FITR();
                Liga("Buscar Embarcacion","fAbreBuscaEmbarcacionDestino();");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Embarcación:","cNomEmbarcacion","",70,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL",9);
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Tipo de Embarcación:","cTipoEmbarcacion","",70,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL",9);
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Matricula:","cMatricula","",25,0,"ClaveEspecialidad","fMayus(this);");
                TDEtiCampo(true,"EEtiqueta",0,"Abanderamiento:","cBandera","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Numero OMI:","cNumOMI","",25,0,"ClaveEspecialidad","fMayus(this);");
                TDEtiCampo(true,"EEtiqueta",0,"Folio RPMN:","iFolioRPMN","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Tipo de Navegacion:","cTipoNavega","",25,0,"ClaveEspecialidad","fMayus(this);");
                TDEtiCampo(true,"EEtiqueta",0,"Tipo de Servicio:","cTipoServ","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Se�al Distintiva:","cSenalDist","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Eslora:","cEslora","",25,0,"Eslora","fMayus(this);");
                TDEtiCampo(true,"EEtiqueta",0,"Manga:","cManga","",25,0,"Manga","fMayus(this);");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Puntal:","cPuntal","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Calado Proa:","cCaladoProa","",25,0,"ClaveEspecialidad","fMayus(this);");
                TDEtiCampo(true,"EEtiqueta",0,"Calado Popa:","cCaladoPopa","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Arqueo Bruto:","cUnidMedArqueoBruto","",25,0,"ClaveEspecialidad","fMayus(this);");
                TDEtiCampo(true,"EEtiqueta",0,"Arqueo Neto:","cUnidMedArqueoNeto","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
            FinTabla();
          FinTabla();
          ITRTD("",0,"","","","top");
          InicioTabla("",0,"","","center");
            ITRTD("",0,"","","","top");
              Liga("Integrar Motores","fIntegrarMotor();");
            ITRTD();
              IFrame("IListadoMotD","500","100","Listado.js","yes",true);
            FTD();
        FinTabla();
        ITRTD("",0,"","","","top");
        InicioTabla("",0,"","","center");
          ITRTD("",0,"","","","top");
            Liga("Integrar Documentos","fIntegrarDocto();");
          ITRTD();
            IFrame("IListadoDocD","500","100","Listado.js","yes",true);
          FTD();
      FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("",0,"","","","top");
                Liga("Integrar Solicitudes","fIntegrarSolicitud();");
              ITRTD();
                IFrame("IListadoSolD","500","100","Listado.js","yes",true);
              FTD();
          FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("",0,"","","","top");
                Liga("Integrar Matriculas","fIntegrarMatricula();");
              ITRTD();
                IFrame("IListadoMatD","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("",0,"","","","top");
                Liga("Integrar RPMN","fIntegrarRPMN();");
              ITRTD();
                IFrame("IListadoRPMND","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();

          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("",0,"","","","top");
                Liga("Integrar Inspecciones","fIntegrarInsp();");
              ITRTD();
                IFrame("IListadoInsp","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("",0,"","","","top");
                Liga("Integrar Certificados","fIntegrarCert();");
              ITRTD();
                IFrame("IListadoCert","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("",0,"","","","top");
                Liga("Integrar Arribos/Despachos","fIntegrarArribo();");
              ITRTD();
                IFrame("IListadoArribosD","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
        FinTabla();

        FITD("",0,"","","right","top");
        //ITRTD("",0,"","","left","top");
          InicioTabla("",0,"","","top");
            ITRTD("",0,"","","","top");
            InicioTabla("",1,"100%","100%","right");
              ITRTD("",0,"","","","top");
              InicioTabla("",0,"100%","100%","left");
                ITRTD("ETablaST",3,"","","center");
                  TextoSimple("EMBARCACION DESTINO");
                ITD();
                  Liga("Borrar Embarcacion","fBorrarEmbarcacion();");
                FTD();
              FinTabla();
              ITRTD("",3,"","","center");
              //ITRTD("",0,"","","","top");
              InicioTabla("",0,"","","center");
                FITR();
                  Liga("Buscar Origen","fAbreBuscaEmbarcacionOrigen();");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Embarcación:","cNomEmbarcacionO","",70,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL",9);
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Tipo de Embarcación:","cTipoEmbarcacionO","",70,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL",9);
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Matricula:","cMatriculaO","",25,0,"ClaveEspecialidad","fMayus(this);");
                  TDEtiCampo(true,"EEtiqueta",0,"Abanderamiento:","cBanderaO","",25,0,"ClaveEspecialidad","fMayus(this);");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Numero OMI:","cNumOMIO","",25,0,"ClaveEspecialidad","fMayus(this);");
                  TDEtiCampo(true,"EEtiqueta",0,"Folio RPMN:","iFolioRPMNO","",25,0,"ClaveEspecialidad","fMayus(this);");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Tipo de Navegacion:","cTipoNavegaO","",25,0,"ClaveEspecialidad","fMayus(this);");
                  TDEtiCampo(true,"EEtiqueta",0,"Tipo de Servicio:","cTipoServO","",25,0,"ClaveEspecialidad","fMayus(this);");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Se�al Distintiva:","cSenalDistO","",25,0,"ClaveEspecialidad","fMayus(this);");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Eslora:","cEsloraO","",25,0,"Eslora","fMayus(this);");
                  TDEtiCampo(true,"EEtiqueta",0,"Manga:","cMangaO","",25,0,"Manga","fMayus(this);");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Puntal:","cPuntalO","",25,0,"ClaveEspecialidad","fMayus(this);");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Calado Proa:","cCaladoProaO","",25,0,"ClaveEspecialidad","fMayus(this);");
                  TDEtiCampo(true,"EEtiqueta",0,"Calado Popa:","cCaladoPopaO","",25,0,"ClaveEspecialidad","fMayus(this);");
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Arqueo Bruto:","cUnidMedArqueoBrutoO","",25,0,"ClaveEspecialidad","fMayus(this);");
                  TDEtiCampo(true,"EEtiqueta",0,"Arqueo Neto:","cUnidMedArqueoNetoO","",25,0,"ClaveEspecialidad","fMayus(this);");
                FITR();
              FinTabla();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("Motores a Integrar");
              ITRTD();
                IFrame("IListadoMotO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("Documentos a Integrar");
              ITRTD();
                IFrame("IListadoDocO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("Solicitudes a Integrar");
              ITRTD();
                IFrame("IListadoSolO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("Matriculas a Integrar");
              ITRTD();
                IFrame("IListadoMatO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("RPMN a Integrar");
              ITRTD();
                IFrame("IListadoRPMNO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();

          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("Inspecciones a Integrar");
              ITRTD();
                IFrame("IListadoInsO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("Certificados a Integrar");
              ITRTD();
                IFrame("IListadoCertO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();
          ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              ITRTD("EEtiquetaC",0,"","","","top");
                TextoSimple("Arribos / Despachos a Integrar");
              ITRTD();
                IFrame("IListadoArribosO","500","100","Listado.js","yes",true);
              FTD();
            FinTabla();

          FinTabla();
          ITRTD();
        FinTabla()
      FinTabla();
    FinTabla();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCveVehiculo");
    Hidden("iCveVehiculoO");
    Hidden("lNacional");
    Hidden("iConsecutivoCertExp");
    Hidden("iCveOficina");
    Hidden("iCveArriboA");
    Hidden("iCveArriboE");
    Hidden("iCveTramite");
    Hidden("cSintesis");
    Hidden("iCveUsuario",top.fGetUsrID());
    Hidden("iEjerArribo");
    Hidden("iNumArribo");
    Hidden("iEjercicioOri");
    Hidden("iNumSolicitudOri");
    Hidden("iEjercicioMatOri");
    Hidden("iCveMatriculaOri");
    Hidden("iEjercicioInsOri");
    Hidden("iCveOficinaOri");
    Hidden("iFolioOri");
    Hidden("iPartidaOri");
    Hidden("iCveInspProg");
    Hidden("iCveMotor");
    Hidden("iEjercicio");
    Hidden("iCveTipoDocumento");
    Hidden("iIdDocumento");
  FinTabla();
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];

  FRMListadoMotD = fBuscaFrame("IListadoMotD");
  FRMListadoMotD.fSetControl(self);
  FRMListadoMotD.fSetTitulo("Consecutivo,Marca,Potencia,Unidad Potencia,");
  FRMListadoMotD.fSetCampos("0,1,2,3,");
  FRMListadoMotD.fSetAlinea("center,");
  FRMListadoMotD.fSetDespliega("texto,");
  FRMListadoMotO = fBuscaFrame("IListadoMotO");
  FRMListadoMotO.fSetControl(self);
  FRMListadoMotO.fSetTitulo("Ejercicio,Solicitud,Tramite,Modalidad,");
  FRMListadoMotO.fSetCampos("0,1,2,3,");
  FRMListadoMotO.fSetAlinea("center,");
  FRMListadoMotO.fSetDespliega("texto,");
  FRMListadoMotO.fSetSelReg(8);

  FRMListadoDocD = fBuscaFrame("IListadoDocD");
  FRMListadoDocD.fSetControl(self);
  FRMListadoDocD.fSetTitulo("Fecha de Registro,Tipo Documento,Descripcion,");
  FRMListadoDocD.fSetCampos("3,4,5,");
  FRMListadoDocD.fSetAlinea("center,");
  FRMListadoDocD.fSetDespliega("texto,");
  FRMListadoDocO = fBuscaFrame("IListadoDocO");
  FRMListadoDocO.fSetControl(self);
  FRMListadoDocO.fSetTitulo("Fecha de Registro,Tipo Documento,Descripcion,");
  FRMListadoDocO.fSetCampos("3,4,5,");
  FRMListadoDocO.fSetAlinea("center,");
  FRMListadoDocO.fSetDespliega("texto,");
  FRMListadoDocO.fSetSelReg(9);
  
  FRMListadoSolD = fBuscaFrame("IListadoSolD");
  FRMListadoSolD.fSetControl(self);
  FRMListadoSolD.fSetTitulo("Ejercicio,Solicitud,Tramite,Modalidad,");
  FRMListadoSolD.fSetCampos("0,1,2,3,");
  FRMListadoSolD.fSetAlinea("center,");
  FRMListadoSolD.fSetDespliega("texto,");
  FRMListadoSolO = fBuscaFrame("IListadoSolO");
  FRMListadoSolO.fSetControl(self);
  FRMListadoSolO.fSetTitulo("Ejercicio,Solicitud,Tramite,Modalidad,");
  FRMListadoSolO.fSetCampos("0,1,2,3,");
  FRMListadoSolO.fSetAlinea("center,");
  FRMListadoSolO.fSetDespliega("texto,");
  FRMListadoSolO.fSetSelReg(2);


  FRMListadoMatD = fBuscaFrame("IListadoMatD");
  FRMListadoMatD.fSetControl(self);
  FRMListadoMatD.fSetTitulo("Matricula,Fecha Alta,Puerto,Navegaci�n,Servicio,Propietario,");
  FRMListadoMatD.fSetCampos("20,7,8,9,10,11,");
  FRMListadoMatO = fBuscaFrame("IListadoMatO");
  FRMListadoMatO.fSetControl(self);
  FRMListadoMatO.fSetTitulo("Matricula,Fecha Alta,Puerto,Navegaci�n,Servicio,Propietario,");
  FRMListadoMatO.fSetCampos("20,7,8,9,10,11,");
  FRMListadoMatO.fSetSelReg(3);


  IListadoRPMND = fBuscaFrame("IListadoRPMND");
  IListadoRPMND.fSetControl(self);
  IListadoRPMND.fSetTitulo("Folio,Secci�n,Tipo Acto,Fecha Alta,Fecha Baja,");
  IListadoRPMND.fSetCampos("10,6,7,4,5,");
  IListadoRPMND.fSetDespliega("texto,");
  IListadoRPMNO = fBuscaFrame("IListadoRPMN");
  IListadoRPMNO.fSetControl(self);
  IListadoRPMNO.fSetTitulo("Folio,Secci�n,Tipo Acto,Fecha Alta,Fecha Baja,");
  IListadoRPMNO.fSetCampos("10,6,7,4,5,");
  IListadoRPMNO.fSetDespliega("texto,");
  IListadoRPMNO.fSetSelReg(4);

  IListadoInspD = fBuscaFrame("IListadoInsp");
  IListadoInspD.fSetControl(self);
  IListadoInspD.fSetTitulo("Inicio Inspecci�n,Fin Inspecci�n,Puerto,Observaciones,");
  IListadoInspD.fSetCampos("0,1,2,3,");
  IListadoInspD.fSetDespliega("texto,");
  IListadoInspO = fBuscaFrame("IListadoInsO");
  IListadoInspO.fSetControl(self);
  IListadoInspO.fSetTitulo("Inicio Inspecci�n,Fin Inspecci�n,Puerto,Observaciones,");
  IListadoInspO.fSetCampos("0,1,2,3,");
  IListadoInspO.fSetDespliega("texto,");
  IListadoInspO.fSetSelReg(5);

  IListadoCertD = fBuscaFrame("IListadoCert");
  IListadoCertD.fSetControl(self);
  IListadoCertD.fSetTitulo("Inicio Vig,Fin Vig,Tipo  Certificado,Usuario,");
  IListadoCertD.fSetCampos("0,1,2,6,");
  IListadoCertO = fBuscaFrame("IListadoCertO");
  IListadoCertO.fSetControl(self);
  IListadoCertO.fSetTitulo("Inicio Vig,Fin Vig,Tipo  Certificado,Usuario,");
  IListadoCertO.fSetCampos("0,1,2,6,");
  IListadoCertO.fSetSelReg(6);


  IListadoArriboD = fBuscaFrame("IListadoArribosD");
  IListadoArriboD.fSetControl(self);
  IListadoArriboD.fSetTitulo("Hora Arribo,Lugar Procede,Hora Zarpe,Lugar Destino,");
  IListadoArriboD.fSetCampos("6,8,7,9,");
  IListadoArriboO = fBuscaFrame("IListadoArribosO");
  IListadoArriboO.fSetControl(self);
  IListadoArriboO.fSetTitulo("Hora Arribo,Lugar Procede,Hora Zarpe,Lugar Destino,");
  IListadoArriboO.fSetCampos("6,8,7,9,");
  IListadoArriboO.fSetSelReg(7);

  if (top.opener){
    if(top.opener.fEnviaDatosConsultaVehiculo)
      top.opener.fEnviaDatosConsultaVehiculo(window);
  }
  fDisabled(true);
  frm.hdBoton.value="Primero";
}

function fEmbarcacion(){
   frm.hdNumReg.value =  10000;
  frm.hdSelect.value = "SELECT EM.CNOMEMBARCACION,P.CNOMBRE,TE.CDSCTIPOEMBARCACION,EM.CNUMOMI FROM VEHEMBARCACION Em " +
                       "join GRLPAIS P on EM.ICVEPAISABANDERAMIENTO = P.ICVEPAIS " +
                       "JOIN VEHTIPOEMBARCACION TE ON TE.ICVETIPOEMBARCACION = EM.ICVETIPOEMBARCACION ";
  frm.hdLlave.value  = "iCveVehiculo";
  fEngSubmite("pgConsulta.jsp","Listado");
}
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existi� un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existi� un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }

   // Monitoreo de Errores realacionados a SQL y la Base
   if(cError=="Datos")
     fAlert("Existe un conflicto de Datos.");


   if(cId == "Listado" && cError==""){
     if(aRes.length>0){
       frm.cNomEmbarcacion.value = aRes[0][0];
       frm.cTipoEmbarcacion.value = aRes[0][2];
       frm.cBandera.value = aRes[0][1];
     }
   }
   if(cId == "ListMotores" && cError==""){
       frm.hdRowPag.value = iRowPag;
       if(iDestinoOrigen==1){
         IListadoMotD.fSetListado(aRes);
         IListadoMotD.fShow();
         IListadoMotD.fSetLlave(cLlave);
         if(lRefrescando==true) {
           iDestinoOrigen=0;
           fMotores();
         }
       }
       if(iDestinoOrigen==0){
         IListadoMotO.fSetListado(aRes);
         IListadoMotO.fShow();
         IListadoMotO.fSetLlave(cLlave);
         lRefrescando=false;
       }
       if(lRefrescando==false) {
	   fDocumentos();
       }
   }
   if(cId == "ListDoctos" && cError==""){
       frm.hdRowPag.value = iRowPag;
       if(iDestinoOrigen==1){
         IListadoDocD.fSetListado(aRes);
         IListadoDocD.fShow();
         IListadoDocD.fSetLlave(cLlave);
         if(lRefrescando==true) {
           iDestinoOrigen=0;
           fDocumentos();
         }
       }
       if(iDestinoOrigen==0){
         IListadoDocO.fSetListado(aRes);
         IListadoDocO.fShow();
         IListadoDocO.fSetLlave(cLlave);
         lRefrescando=false;
       }
       if(lRefrescando==false) {
	   fSolicitud();
       }
       
   }
   if(cId == "ListInspeccion" && cError==""){
     frm.hdRowPag.value = iRowPag;
     if(iDestinoOrigen==1){
       IListadoInspD.fSetListado(aRes);
       IListadoInspD.fShow();
       IListadoInspD.fSetLlave(cLlave);
       if(lRefrescando==true) {
         iDestinoOrigen=0;
         fInspecciones();
       }
     }
     if(iDestinoOrigen==0){
       IListadoInspO.fSetListado(aRes);
       IListadoInspO.fShow();
       IListadoInspO.fSetLlave(cLlave);
       lRefrescando=false;
     }
     if(lRefrescando==false) fCertificados();
   }
   if(cId == "ListCertificados" && cError==""){
     if(iDestinoOrigen==1){
       IListadoCertD.fSetListado(aRes);
       IListadoCertD.fShow();
       IListadoCertD.fSetLlave(cLlave);
       if(lRefrescando==true) {
         iDestinoOrigen=0;
         fCertificados();
       }
     }
     else if(iDestinoOrigen==0){
       IListadoCertO.fSetListado(aRes);
       IListadoCertO.fShow();
       IListadoCertO.fSetLlave(cLlave);
       lRefrescando=false;
     }
     if(lRefrescando==false) fArribos();
   }
   if(cId == "ListSolicitud" && cError==""){
     frm.hdRowPag.value = iRowPag;
     if(iDestinoOrigen==1){
       FRMListadoSolD.fSetListado(aRes);
       FRMListadoSolD.fShow();
       FRMListadoSolD.fSetLlave(cLlave);
       if(lRefrescando==true) {
         iDestinoOrigen=0;
         fSolicitud();
       }
     }
     if(iDestinoOrigen==0){
       FRMListadoSolO.fSetListado(aRes);
       FRMListadoSolO.fShow();
       FRMListadoSolO.fSetLlave(cLlave);
       lRefrescando=false;
     }
     if(lRefrescando==false) fMatricula();
   }
   if(cId == "cIdRPMN" && cError==""){
     frm.hdRowPag.value = iRowPag;
     if(aRes.length>0){
       for(i=0;i<aRes.length;i++){
         var iFolio = parseInt(aRes[i][2]);
         var cFolio="";
         cFolio = iFolio<10?"000"+iFolio:(iFolio<100?"00"+iFolio:(iFolio<1000?"0"+iFolio:""+iFolio));
         aRes[i][10]=cFolio+"-"+aRes[i][0]+"-"+aRes[i][1].substring(2,4);
       }
     }
     if(iDestinoOrigen==0){
       IListadoRPMNO.fSetListado(aRes);
       IListadoRPMNO.fShow();
       IListadoRPMNO.fSetLlave(cLlave);
     }
     if(iDestinoOrigen==1){
       IListadoRPMND.fSetListado(aRes);
       IListadoRPMND.fShow();
       IListadoRPMND.fSetLlave(cLlave);
       if(lRefrescando==true){
         iDestinoOrigen=0;
         fRPMN();
       }
     }
     if(lRefrescando==false) fInspecciones();
   }
   if(cId == "cIdMatricula" && cError==""){
     if(aRes.length>0){
       for(i=0;i<aRes.length;i++){
         var cEstado = aRes[i][0]>9?aRes[i][0]:"0"+aRes[i][0];
         var cCapitania =aRes[i][1]>9?aRes[i][1]:"0"+aRes[i][1];
         var cConsecutivo = aRes[i][3]>99?aRes[i][3]:aRes[i][3]>9?"0"+aRes[i][3]:"00"+aRes[i][3];
         aRes[i][20]= cEstado+cCapitania+aRes[i][2]+cConsecutivo+aRes[i][4]+aRes[i][5]+aRes[i][6];
       }
     }
     if(iDestinoOrigen==1){
       FRMListadoMatD.fSetListado(aRes);
       FRMListadoMatD.fShow();
       FRMListadoMatD.fSetLlave(cLlave);
       if(lRefrescando==true){
         iDestinoOrigen=0;
         fMatricula();
       }
     }
     if(iDestinoOrigen==0){
       FRMListadoMatO.fSetListado(aRes);
       FRMListadoMatO.fShow();
       FRMListadoMatO.fSetLlave(cLlave);
     }
     if(lRefrescando==false)fRPMN();
   }
   if(cId=="ListArribos" && cError == ""){
     if(iDestinoOrigen==1){
       IListadoArriboD.fSetListado(aRes);
       IListadoArriboD.fShow();
       IListadoArriboD.fSetLlave(cLlave);
       if(lRefrescando==true) {
         iDestinoOrigen=0;
         fArribos();
       }
     }
     else if(iDestinoOrigen==0){
       IListadoArriboO.fSetListado(aRes);
       IListadoArriboO.fShow();
       IListadoArriboO.fSetLlave(cLlave);
       lRefrescando=false;
     }
   }
   if(cId=="cIdBorraEmbarcacion" && cError==""){
       iDestinoOrigen=0; 
      fSetEmbarcacion("","","","","",
	              "","","","","",
	              "","","","","",
	              "");
      fSetVarios("","","","","",
	         "","","","","",
	         "","","","","",
	         "","","","","",
	         "","","","","",
	         "","","","","",
	         "","");
   }
   if(cId=="cIdCertif" && cError == ""){}
   if(cId=="cIdIntegraMot")fCargaMotor();
   if(cId=="cIdIntegraSol")fCargaSolicitudes();
   if(cId=="cIdIntegraMat")fCargaMat();
   if(cId=="cIdIntegraRPMN")fCargaRPMN();
   if(cId=="cIdIntegraInsp")fCargaInsp();
   if(cId=="cIdIntegraCert")fCargaCert();
   if(cId=="cIdIntegraArribo")fCargaArribo();
   if(cId=="cIdIntegraDoc")fCargaDoc();
 }

function fSetEmbarcacion
  (iCveVehiculo,cNomEmbarcacion,cPropietario,cNumOMI,cMatricula,cNumSerie,cTipoServ,cTipoNavega,hdPropOPoseedor,
  iFolioRPMN,cPaisAbanderamiento,cTipoEmbarcacion,cSenalDist,iCveTipoServ,iCveTipoNavega,iCveTipoEmbarcacion){
    if(iDestinoOrigen==1){
      frm.iCveVehiculo.value    = iCveVehiculo;
      frm.cNomEmbarcacion.value = cNomEmbarcacion;
      frm.cMatricula.value      = cMatricula;
      frm.cTipoNavega.value     = cTipoNavega;
      frm.cTipoServ.value       = cTipoServ;
      frm.cTipoEmbarcacion.value= cTipoEmbarcacion;
      frm.cBandera.value        = cPaisAbanderamiento;
      frm.cNumOMI.Value         = cNumOMI;
      frm.iFolioRPMN.value      = iFolioRPMN;
      frm.cSenalDist.value      = cSenalDist;
    }
    if(iDestinoOrigen==0){
      frm.iCveVehiculoO.value    = iCveVehiculo;
      frm.cNomEmbarcacionO.value = cNomEmbarcacion;
      frm.cMatriculaO.value      = cMatricula;
      frm.cTipoNavegaO.value     = cTipoNavega;
      frm.cTipoServO.value       = cTipoServ;
      frm.cTipoEmbarcacionO.value= cTipoEmbarcacion;
      frm.cBanderaO.value        = cPaisAbanderamiento;
      frm.cNumOMIO.Value         = cNumOMI;
      frm.iFolioRPMNO.value      = iFolioRPMN;
      frm.cSenalDistO.value      = cSenalDist;
    }
  }
function fSetVarios
  (dArqueoBruto,dArqueoNeto,dPesoMuerto,dEslora,dManga,dPuntal,cPuertoAband,hdlArtefacto,iTripulacionMax,dtConstruccion,iNumBodegas
  ,iNumTanques,dCaladoMax,hdUnidMedManga,hdUnidMedArqueoNeto,hdUniMedArqueoBruto,iCveEntFedMatricula,iCveMunicMatricula,
  cDscEntFedMatricula,cDscMunicMatricula,cUnidMedEslora,cUnidMedManga,cUnidMedPuntal,cUnidMedCaladoPopa,cUnidMedCaladoProa
  ,cUnidMedCaladoMax,cUnidMedArqueoBruto,cUnidMedArqueoNeto,iUniMedPesoMuerto,iUniMedVelocidadMax,dCaladoProa,dCaladoPopa){
    if(iDestinoOrigen==1){
      frm.cUnidMedArqueoBruto.value   = dArqueoBruto+" "+cUnidMedArqueoBruto;
      frm.cUnidMedArqueoNeto.value    = dArqueoNeto+" "+cUnidMedArqueoNeto;
      frm.cCaladoProa.value = dCaladoProa+" "+cUnidMedCaladoProa;
      frm.cCaladoPopa.value = dCaladoPopa+" "+cUnidMedCaladoPopa;
      frm.cEslora.value = dEslora+" "+cUnidMedEslora;
      frm.cManga.value = dManga+" "+cUnidMedManga;
      frm.cPuntal.value = dPuntal+" "+cUnidMedPuntal;
      lRefrescando=false;
      fMotores();
      //fSolicitud();
    }
    if(iDestinoOrigen==0){
      frm.cUnidMedArqueoBrutoO.value   = dArqueoBruto+" "+cUnidMedArqueoBruto;
      frm.cUnidMedArqueoNetoO.value    = dArqueoNeto+" "+cUnidMedArqueoNeto;
      frm.cCaladoProaO.value = dCaladoProa+" "+cUnidMedCaladoProa;
      frm.cCaladoPopaO.value = dCaladoPopa+" "+cUnidMedCaladoPopa;
      frm.cEsloraO.value = dEslora+" "+cUnidMedEslora;
      frm.cMangaO.value = dManga+" "+cUnidMedManga;
      frm.cPuntalO.value = dPuntal+" "+cUnidMedPuntal;
      lRefrescando=false;
      fMotores();
      
      //fSolicitud();
    }
  }

function fCertificados(){
  var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;
  frm.hdNumReg.value =  10000;
  frm.hdSelect.value = "SELECT CE.DTINIVIGENCIA, CE.DTFINVIGENCIA, TC.CDSCCERTIFICADO,ICONSECUTIVOCERTEXP, U.ICVEUSUARIO, ce.ICVEOFICINA, " +
    "U.CNOMBRE||' '|| U.CAPPATERNO||' '|| U.CAPMATERNO AS CUSUARIO, O.CDSCBREVE AS COFICINA, S.ICVETRAMITE,CE.iConsecutivoCertExp " +
    "FROM INSCERTIFEXPEDIDOS CE " +
    "join INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CE.ITIPOCERTIFICADO AND TC.ICVEGRUPOCERTIF = CE.ICVEGRUPOCERTIF " +
    "join TRAREGSOLICITUD S ON CE.INUMSOLICITUD = S.INUMSOLICITUD AND CE.IEJERCICIO = S.IEJERCICIO " +
    "join SEGUSUARIO U ON U.ICVEUSUARIO = CE.ICVEUSUARIO " +
    "JOIN GRLOFICINA O ON O.ICVEOFICINA = CE.ICVEOFICINA " +
    "where CE.ICVEVEHICULO = " + cVehiculo;
  frm.hdLlave.value  = "CE.ICVEVEHICULO, CE.ICONSECUTIVOCERTEXP,";
  fEngSubmite("pgConsulta.jsp","ListCertificados");
}
function fInspecciones(){
  var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;
  frm.hdSelect.value = "SELECT I.DTINIINSP, I.DTFININSP,P.CDSCPUERTO,COBSES,I.iCveInspProg "+
  		       "FROM INSINSPECCION I " +
                       "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
                       "JOIN GRLPUERTO P ON P.ICVEPUERTO = I.ICVEPUERTO " +
                       "WHERE PI.ICVEEMBARCACION = "+cVehiculo;
  frm.hdLlave.value = "iCveInspeccion";
  fEngSubmite("pgConsulta.jsp","ListInspeccion");
}

function fMotores(){
    var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;
    frm.hdSelect.value = "SELECT vm.ICONSECUTIVO,mm.CDSCBREVE, vm.IPOTENCIA,um.CABREVIATURA " +
                         "FROM VEHMOTOR vm " +
                         "join VEHMARCAMOTOR mm on mm.ICVEMARCAMOTOR=vm.ICVEMARCAMOTOR " +
                         "join VEHUNIDADMEDIDA um on um.ICVEUNIDADMEDIDA=vm.ICVEUNIMEDPOTENCIA " +
                         "WHERE VM.ICVEVEHICULO= "+cVehiculo;
    frm.hdLlave.value  = "";
    fEngSubmite("pgConsulta.jsp","ListMotores");
}

function fDocumentos(){
    var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;
    frm.hdSelect.value = "SELECT VD.IEJERCICIO,VD.ICVETIPODOCUMENTO,VD.IIDDOCUMENTO,Doc.DTRegistro,TD.CDSCTIPODOCUMENTO,Doc.CDSCDOCUMENTO " +
                         "FROM VEHDOCUMENTO VD " +
                         "JOIN GRLDOCUMENTO Doc ON VD.IEJERCICIO=Doc.IEJERCICIO AND VD.ICVETIPODOCUMENTO=Doc.ICVETIPODOCUMENTO AND VD.IIDDOCUMENTO=Doc.IIDDOCUMENTO " +
                         "JOIN GRLTIPODOCUMENTO TD ON TD.ICVETIPODOCUMENTO=VD.ICVETIPODOCUMENTO " +
                         "WHERE VD.ICVEVEHICULO= "+cVehiculo;
    frm.hdLlave.value  = "";
    fEngSubmite("pgConsulta.jsp","ListDoctos");
}
function fSolicitud(){
  var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;
  frm.hdSelect.value="SELECT S.IEJERCICIO,S.INUMSOLICITUD,CT.CDSCBREVE,MO.CDSCMODALIDAD,E.CDSCETAPA, "+
                     "P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CSOLICITANTE, " +
                     "O.CDSCBREVE,D.CDSCBREVE " +
                     "FROM TRAREGSOLICITUD S " +
                     "JOIN TRACATTRAMITE CT ON CT.ICVETRAMITE = S.ICVETRAMITE " +
                     "JOIN TRAMODALIDAD MO ON MO.ICVEMODALIDAD = S.ICVEMODALIDAD " +
                     "JOIN TRAREGETAPASXMODTRAM EM ON EM.IEJERCICIO = S.IEJERCICIO AND S.INUMSOLICITUD= EM.INUMSOLICITUD " +
                     "  AND EM.ICVEMODALIDAD = S.ICVEMODALIDAD AND EM.ICVETRAMITE = S.ICVETRAMITE   and ICVEETAPA = 1 " +
                     "JOIN TRAETAPA E ON E.ICVEETAPA = EM.ICVEETAPA " +
                     "JOIN GRLPERSONA P ON P.ICVEPERSONA = S.ICVESOLICITANTE " +
                     "JOIN GRLOFICINA O ON O.ICVEOFICINA = S.ICVEOFICINA " +
                     "JOIN GRLDEPARTAMENTO D ON D.ICVEDEPARTAMENTO = S.ICVEDEPARTAMENTO " +
                     "WHERE IIDBIEN = "+cVehiculo;
  frm.hdLlave.value ="";
  fEngSubmite("pgConsulta.jsp","ListSolicitud");
}
function fPermisos(){
  if(frm.lNacional.value == 1){
    frm.hdSelect.value = "SELECT TP.CDSCTIPOPERMISO,P.CDSCPUERTO ,PS.DTFINVIGENCIA,lAprobado FROM MYRPERMISOSSERV PS " +
    "join MYREMBARCACIONESXPERM EP ON PS.ICONSECPERMISO = EP.ICONSECPERMISO AND PS.IEJERCICIOPERMISO = EP.IEJERCICIOPERMISO " +
    "JOIN MYRTIPOPERMISO TP ON TP.ICVETIPOPERMISO = PS.ICVETIPOPERMISO " +
    "join GRLPUERTO P ON P.ICVEPUERTO = PS.ICVEPUERTO " +
    "WHERE EP.ICVEVEHICULO = "+frm.iCveVehiculo.value;
    frm.hdLlave.value = "";
    fEngSubmite("pgConsulta.jsp","ListPermisos");
  }
  else{
    frm.hdSelect.value = "SELECT CPRESTASERVAEMP,CMOTIVOCONTRATO,DTFINVIGENCIA FROM NAVPERMISOS " +
                         "where ICVEVEHICULO = "+frm.iCveVehiculo.value;
    frm.hdLlave.value  = "";
    fEngSubmite("pgConsulta.jsp","ListPermisos");
  }
}
function fArribos(){
  var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;

  frm.hdSelect.value="SELECT A.IEJERCICIO,A.INUMARRIBO,E.ICVEVEHICULO,E.CNOMEMBARCACION,"+
                     "       A.TSARRIBOPROGRAMADO,A.TSZARPEPROGRAMADO,A.TSARRIBOREAL, " +
                     "       A.TSZARPEREAL,PP.CDSCPUERTO AS CPUERTOPROCEDE,PD.CDSCPUERTO AS CPUERTODESTINO " +
                     "FROM NAVARRIBOS A " +
                     "JOIN VEHEMBARCACION E ON E.ICVEVEHICULO=A.ICVEEMBARCACION " +
                     "JOIN GRLPUERTO PP ON PP.ICVEPUERTO=A.ICVEPUERTOPROCED " +
                     "JOIN GRLPUERTO PD ON PD.ICVEPUERTO=A.ICVEPUERTODESTINO "+
                     "WHERE E.ICVEVEHICULO = "+cVehiculo;
  frm.hdLlave.value ="";
  fEngSubmite("pgConsulta.jsp","ListArribos");
}
function fDeficiencias(){
  frm.hdSelect.value="SELECT D.CDSCCODDEFICIENCIAESP, M.CDSCMEDIDAESP,MD.DTCUMPLIMIENTO FROM INSMEDXDEFICIENCIA MD " +
                     "join INSMEDADOPTADAS M ON M.ICODIGOMEDIDA = MD.ICODIGOMEDIDA " +
                     "JOIN INSCODDEFICIENCIA D ON MD.ICVECODDEFICIENCIA = D.ICVECODDEFICIENCIA " +
                     "JOIN INSINSPECCION I ON I.ICVEINSPECCION = MD.ICVEINSPECCION " +
                     "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
                     "JOIN VEHEMBARCACION V ON V.ICVEVEHICULO = PI.ICVEEMBARCACION " +
                     "WHERE V.ICVEVEHICULO = "+frm.iCveVehiculo.value+" AND M.ICODIGOMEDIDA > 3 AND I.ICVEINSPECCION IN ( " +
                     "SELECT MAX(INSP.ICVEINSPECCION) FROM INSINSPECCION INSP " +
                     "JOIN INSPROGINSP PROGI ON INSP.ICVEINSPPROG = PROGI.ICVEINSPPROG " +
                     "WHERE PROGI.ICVEEMBARCACION = "+frm.iCveVehiculo.value+" ) ";
  frm.hdLlave.value ="";
  fEngSubmite("pgConsulta.jsp","cIdDefi");
}


function fMostrarInspecciones(){
  if(frm.iCveVehiculo.value != "")
    fAbreSubWindow(true,"pg110030053","no","yes","yes","yes","800","350","","");
}
function fMostrarCertificados(){
  if(frm.iCveVehiculo.value != "")
    fAbreSubWindow(true,"pg110030054","no","yes","yes","yes","800","250","","");
}
function fMostrarSoli(){
  if(frm.iCveVehiculo.value != "")
    fAbreSubWindow(true,"pg110030051","no","yes","yes","yes","800","350","","");
}
function fMostrarArri(){
  if(frm.iCveVehiculo.value != "")
    fAbreSubWindow(true,"pg110030055","no","yes","yes","yes","800","350","","");
}
function fGetEmbarcacion(){
  return frm.iCveVehiculo.value;
}
function fSetNacionales(){
  return 1;
}
function fMostrarPermisos(){
  if(frm.iCveVehiculo.value != ""){
    if(frm.lNacional.value==1)
      fAbreSubWindow(true,"pg110030052","no","yes","yes","yes","800","350","","");
    else fAbreSubWindow(true,"pg112020082","no","yes","yes","yes","800","250","","");
  }
}

function fGetArregloCampos(){
   var aCampos = new Array();
   aCampos[0] = "ICONSECUTIVOCERTEXP";
   aCampos[1] = "ICVEVEHICULO";
   return aCampos;
}
function fGetArregloDatos(){
   var aDatos = new Array();
   aDatos[0] = frm.iConsecutivoCertExp.value;
   aDatos[1] = frm.iCveVehiculo.value; //Nombres de los Valores de los Campos.
   return aDatos;
}
function fReporte(){
    if(frm.cMatricula.value!=""){
      if(FRMListado3.fGetLength() > 0){
        //aCBoxTra = FRMListado.fGetObjs(0);
        var formato = null;
        cClavesModulo = "13,";
        cNumerosRep = "7,";
        cFiltrosRep = frm.iConsecutivoCertExp.value+","+frm.iCveVehiculo.value+","+frm.iCveUsuario.value+","+frm.iCveOficina.value+cSeparadorRep;
        fReportes();
      }else fAlert("No existen datos para esta solicitud");
    }else fAlert("El Vehiculo debe de tener Matr�cula");
}

function fSelReg(aDato){
}
function fSelReg2(aDato){
  if(aDato.length>0){
    frm.iEjercicioOri.value=aDato[0];
    frm.iNumSolicitudOri.value = aDato[1];
  }
}
function fSelReg3(aDato){
  if(aDato.length>0){
    frm.iEjercicioMatOri.value = aDato[12];
    frm.iCveMatriculaOri.value = aDato[13];
  }
}
function fSelReg4(aDato){
  if(aDato.length>0){
    frm.iEjercicioInsOri.value = aDato[1];
    frm.iCveOficinaOri.value = aDato[9];
    frm.iFolioOri.value = aDato[2];
    frm.iPartidaOri.value = aDato[3];
  }
}
function fSelReg5(aDato){
  if(aDato.length>0){
    frm.iCveInspProg.value = aDato[4];
  }
}
function fSelReg6(aDato){
  if(aDato.length>0){
    frm.iConsecutivoCertExp.value = aDato[9];
  }
}
function fSelReg7(aDato){
  if(aDato.length>0){
    frm.iEjerArribo.value = aDato[0];
    frm.iNumArribo.value = aDato[1];
  }
}

function fSelReg8(aDato){
  if(aDato.length>0){
    frm.iCveMotor.value = aDato[0];
  }
}


function fSelReg9(aDato){
  if(aDato.length>0){
      frm.iEjercicio.value = aDato[0];
      frm.iCveTipoDocumento.value = aDato[1];
      frm.iIdDocumento.value = aDato[2];
  }
}

function fCertificado(){
  if (fGetPermisos("pg114020060.js") != 2){
    cPaginaWebJS = "pg114020060.js";
    var cTramites = fGetClavesTramiteFiltrar();
    var aTram = cTramites.split(",");
    for(i=0;i<aTram.length;i++)
      if(frm.iCveTramite.value == aTram[i]) {
        fReporte();
      }
  }
  if (fGetPermisos("pg114020070.js") != 2){
    cPaginaWebJS = "pg114020070.js";
    var cTramites = fGetClavesTramiteFiltrar();
    var aTram = cTramites.split(",");
    for(i=0;i<aTram.length;i++)
      if(frm.iCveTramite.value == aTram[i]) {
        fReporte();
      }
  }
  cNomPagina = "pg110030050.js";
}

function fRPMN(){
  var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;
  frm.hdSelect.value = "SELECT C.CSIGLASOFICINA,RP.IEJERCICIOINS,RP.IFOLIORPMN,RP.IPARTIDA,RP.DTREGISTRO,RP.DTCANCELAFOLIO,S.CDSCSECCION,TA.CDSCTIPOACTO,O.CDSCOBSLARGA,RP.iCveOficina " +
                       "FROM MYRRPMN RP " +
                       "LEFT JOIN MYRCAPITANIA C ON C.ICVEOFICINA=RP.ICVEOFICINA " +
                       "LEFT JOIN MYRSECCION S ON S.ICVERAMO=RP.ICVERAMO AND S.ICVESECCION=RP.ICVESECCION " +
                       "LEFT JOIN MYRTIPOACTO TA ON TA.ICVERAMO=RP.ICVERAMO AND TA.ICVESECCION=RP.ICVESECCION AND TA.ICVETIPOACTO=RP.ICVETIPOACTO " +
                       "LEFT JOIN MYREMBARCACION E ON E.IEJERCICIOINS=RP.IEJERCICIOINS AND E.ICVEOFICINA=RP.ICVEOFICINA AND E.IFOLIORPMN=RP.IFOLIORPMN AND E.IPARTIDA=RP.IPARTIDA " +
                       "LEFT JOIN GRLOBSERVALARGA O ON O.IEJERCICIOOBSLARGA=RP.IEJERCICIOOBSLARGA AND O.ICVEOBSLARGA=RP.ICVEOBSLARGA " +
                       "WHERE E.ICVEVEHICULO= "+cVehiculo;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp","cIdRPMN");
}
function fMatricula(){
  var cVehiculo = iDestinoOrigen==1?frm.iCveVehiculo.value:frm.iCveVehiculoO.value;
  frm.hdSelect.value = "SELECT " +
                       "M.ICVEENTIDADFED,M.ICVECAPITANIAPARAMAT,M.CSERIE, " +
                       "M.ICONSECUTIVOMATRICULA,M.ICVETIPONAVEGA,M.ICVETIPOSERV,M.IDIGVERIFICADOR, " +
                       "M.DTASIGNACION,O.CDSCOFICINA,TN.CDSCTIPONAVEGA,TS.CDSCTIPOSERV,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CPROPIETARIO, " +
                       "M.IEJERCICIO,M.ICVEMATRICULA "+
                       "FROM MYRMATRICULA M " +
                       "JOIN GRLOFICINA O ON O.ICVEOFICINA=M.ICVEPUERTOMAT " +
                       "JOIN VEHTIPOSERVICIO TS ON TS.ICVETIPOSERV=M.ICVETIPOSERV " +
                       "JOIN VEHTIPONAVEGACION TN ON TN.ICVETIPONAVEGA=M.ICVETIPONAVEGA " +
                       "JOIN GRLPERSONA P ON P.ICVEPERSONA=M.ICVEPROPIETARIO "+
                       "WHERE ICVEEMBARCACION= "+cVehiculo;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp","cIdMatricula");
}
function fSintesis(){
  alert('\nSintesis:\n'+frm.cSintesis.value);
}


function fAbreBuscaEmbarcacionDestino(){
  iDestinoOrigen = 1;
  fAbreBuscaEmbarcacion();
}
function fAbreBuscaEmbarcacionOrigen(){
  iDestinoOrigen = 0;
  fAbreBuscaEmbarcacion();
}
function fBorrarEmbarcacion(){
    if(frm.iCveVehiculoO.value > 0){
      frm.hdBoton.value = "BorraEmbarcacion";
      fEngSubmite("pgVEHEmbarcacion.jsp","cIdBorraEmbarcacion");
    }
}
function fIntegrarMotor(){
    if(confirm("�Esta seguro de que desea integrar est� Motor?")){
	if(frm.iCveMotor.value>0 && frm.iCveVehiculoO.value>0 && frm.iCveVehiculo.value>0){
	    frm.hdBoton.value = "IntegraMot";      
	    fEngSubmite("pgVEHMotor.jsp","cIdIntegraMot");
	}
    }
  }

function fCargaMotor(){
  lRefrescando=true;
  iDestinoOrigen=1;
  fMotores();
}

function fIntegrarDocto(){
    if(confirm("�Esta seguro de que desea integrar est� Documento?")){
	if(frm.iEjercicio.value>=0 && frm.iCveVehiculoO.value>0 && frm.iCveVehiculo.value>0){
	    frm.hdBoton.value = "IntegrarDocumento";      
	    fEngSubmite("pgVEHVehiculo.jsp","cIdIntegraDoc");
	}
    }    
}
function fCargaDoc(){
    lRefrescando=true;
    iDestinoOrigen=1;
    fDocumentos();
  }
function fIntegrarSolicitud(){
  if(confirm("�Esta seguro de que desea integrar esta Solicitud?")){
    frm.hdBoton.value = "IntegraSol";
    fEngSubmite("pgTRARegSolicitud.jsp","cIdIntegraSol");
  }
}
function fCargaSolicitudes(){
  lRefrescando=true;
  iDestinoOrigen=1;
  fSolicitud();
}
function fIntegrarMatricula(){
  if(confirm("�Esta seguro de que desea integrar esta Matricula?")){
    frm.hdBoton.value = "IntegraMat";
    fEngSubmite("pgMYRMatricula.jsp","cIdIntegraMat");
  }
}
function fCargaMat(){
  lRefrescando=true;
  iDestinoOrigen=1;
  fMatricula();
}
function fIntegrarRPMN(){
  if(confirm("�Esta seguro de que desea integrar est� Registro?")){
    frm.hdBoton.value = "IntegraRPMN";
    fEngSubmite("pgMYREmbarcacion.jsp","cIdIntegraRPMN");
  }
}

function fCargaInsp(){
  lRefrescando=true;
  iDestinoOrigen=1;
  fInspecciones();
}
function fIntegrarInsp(){
  if(confirm("�Esta seguro de que desea integrar est� Registro?")){
    frm.hdBoton.value = "IntegraInsp";
    fEngSubmite("pgINSProgInsp.jsp","cIdIntegraInsp");
  }
}
function fCargaRPMN(){
  lRefrescando=true;
  iDestinoOrigen=1;
  fRPMN();
}

function fCargaCert(){
  lRefrescando=true;
  iDestinoOrigen=1;
  fCertificados();
}
function fIntegrarCert(){
  if(confirm("�Esta seguro de que desea integrar est� Registro?")){
    frm.hdBoton.value = "IntegraCert";
    fEngSubmite("pgINSCertifExpedidos.jsp","cIdIntegraCert");
  }
}

function fCargaArribo(){
  lRefrescando=true;
  iDestinoOrigen=1;
  fArribos();
}
function fIntegrarArribo(){
  if(confirm("�Esta seguro de que desea integrar est� Arribo / Despacho?")){
    frm.hdBoton.value = "IntegrarArribo";
    fEngSubmite("pgNAVArribos.jsp","cIdIntegraArribo");
  }
}

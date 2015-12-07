// MetaCD=1.0
// Title: pg116010014.js
// Description: JS Consulta General de Embarcaci�n
// Company: Tecnolog�a InRed S.A. de C.V.
// Author: Arturo L�pez Pe�a
var cTitulo = "";
var FRMListado = "";
var frm;
var cUser = fGetIdUsrSesion();
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110030050.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
function fDefPag(){



  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","100%","center");
        ITRTD("",0,"","","center","top");
      InicioTabla("",0,"100%","100%","center");
        ITRTD("",0,"","","center","top");
        InicioTabla("",1,"","","center");
          ITRTD("",0,"","","center","top");
          InicioTabla("",0,"","","center");
            ITRTD("",0,"","","","top");
            InicioTabla("",0,"100%","100%","center");
              ITRTD("ETablaST",3,"","","center");
                TextoSimple(cTitulo);
                FTD();
              FinTabla();
            ITRTD("",0,"","","","top");
            InicioTabla("",0,"","","center");
              FITR();
                Liga("Buscar Embarcacion","fAbreBuscaEmbarcacion();");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Embarcaci�n:","cNomEmbarcacion","",70,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL",9);
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Tipo de Embarcaci�n:","cTipoEmbarcacion","",70,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL",9);
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
                TDEtiCampo(true,"EEtiqueta",0,"Arqueo Bruto:","cUnidMedArqueoBruto","",25,0,"ClaveEspecialidad","fMayus(this);");
                TDEtiCampo(true,"EEtiqueta",0,"Arqueo Neto:","cUnidMedArqueoNeto","",25,0,"ClaveEspecialidad","fMayus(this);");
              FITR();
            FinTabla();
          FinTabla();
        FinTabla()
          ITRTD();
          InicioTabla("",0,"","","center");
              Liga("Mostrar Detalle de Solicitud","fMostrarSoli();");
            ITRTD();
              IFrame("IListado4","500","100","Listado.js","yes",true);
          FinTabla();
          ITRTD();
          InicioTabla("",0,"","","center");
              Liga("Mostrar Detalle de Permisos","fMostrarPermisos();");
            ITRTD();
              IFrame("IListado5","500","100","Listado.js","yes",true);
          FinTabla();
          ITRTD();
          InicioTabla("",0,"","","center");
              //Liga("Mostrar Detalle de Documentos de las Embarcaciones","fMostrarPermisos();");
              TextoSimple("Documentos de las Embarcaciones")
            ITRTD();
              IFrame("IListado8","500","100","Listado.js","yes",true);
          FinTabla();
        FinTabla();
      ITD("",0,"100%","100%");
      InicioTabla("",0,"100%","","center");
        ITRTD();
          InicioTabla("",0,"100%","","center");
              Liga("Mostrar Detalle de Inspecciones","fMostrarInspecciones();");
            ITRTD();
              IFrame("IListado2","100%","150","Listado.js","yes",true);
          FinTabla();
          ITRTD();
          InicioTabla("",0,"100%","","center");
              Liga("Mostrar Detalle de Certificados","fMostrarCertificados();");
            ITD();
              Liga("Certificados","fCertificado();");
            ITRTD();
              IFrame("IListado3","100%","150","Listado.js","yes",true);
          FinTabla();
          ITRTD();
          InicioTabla("",0,"100%","","center");
              Liga("Mostrar Detalle de Arribos","fMostrarArri();");
            ITRTD();
              IFrame("IListado6","100%","150","Listado.js","yes",true);
          FinTabla();
      FinTabla();
    FinTabla();
      InicioTabla("",0,"90%","","center");
        ITRTD();
         IFrame("IListado7","100%","150","Listado.js","yes",true);
      FinTabla();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCveVehiculo");
    Hidden("lNacional");
    Hidden("iConsecutivoCertExp");
    Hidden("iCveUsuario");
    Hidden("iCveOficina");
    Hidden("iCveArriboA");
    Hidden("iCveArriboE");
    Hidden("iCveTramite");
  FinTabla();
  fFinPagina();
}
// SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
function fOnLoad(){
  frm = document.forms[0];

  FRMListado2 = fBuscaFrame("IListado2");
  FRMListado2.fSetControl(self);
  FRMListado2.fSetTitulo("Inicio, Fin,Puerto,Observaciones,");
  FRMListado2.fSetAlinea("center,");
  FRMListado2.fSetDespliega("texto,");
  FRMListado2.fSetSelReg(2);

  FRMListado3 = fBuscaFrame("IListado3");
  FRMListado3.fSetControl(self);
  FRMListado3.fSetTitulo("Inicio Vigencia,Fin Vigencia, Certificados Expedidos,Expidio,Oficina,");
  FRMListado3.fSetCampos("0,1,2,6,7,");
  FRMListado3.fSetAlinea("center,");
  FRMListado3.fSetDespliega("texto,");
  FRMListado3.fSetSelReg(3);

  FRMListado4 = fBuscaFrame("IListado4");
  FRMListado4.fSetControl(self);
  FRMListado4.fSetTitulo("Ejercicio,Solicitud,Tramite,Modalidad,");
  FRMListado4.fSetCampos("0,1,2,3,");
  FRMListado4.fSetAlinea("center,");
  FRMListado4.fSetDespliega("texto,");
  FRMListado4.fSetSelReg(4);

  FRMListado5 = fBuscaFrame("IListado5");
  FRMListado5.fSetControl(self);
  FRMListado5.fSetTitulo("Listado5,");
  FRMListado5.fSetAlinea("center,");
  FRMListado5.fSetDespliega("texto,");
  FRMListado5.fSetSelReg(5);

  FRMListado6 = fBuscaFrame("IListado6");
  FRMListado6.fSetControl(self);
  FRMListado6.fSetTitulo("Oficina,Puerto Procedencia, Puerto Destino,Arribo,Zarpe,");
  FRMListado6.fSetCampos("4,0,1,2,3,");
  FRMListado6.fSetAlinea("");
  FRMListado6.fSetDespliega("texto,");
  FRMListado6.fSetSelReg(6);

  FRMListado7 = fBuscaFrame("IListado7");
  FRMListado7.fSetControl(self);
  FRMListado7.fSetTitulo("Deficiencia,Medida adoptada,Fecha a Subsanar,");
  FRMListado7.fSetCampos("0,1,2,");
  FRMListado7.fSetAlinea("");
  FRMListado7.fSetDespliega("texto,");
  FRMListado7.fSetSelReg(7);

  FRMListado8 = fBuscaFrame("IListado8");
  FRMListado8.fSetControl(self);
  FRMListado8.fSetTitulo("Documento T�cnico,Fecha de Autorizaci�n,inicio Vigencia,Fin de Vigencia,");
  FRMListado8.fSetCampos("1,2,5,6,");
  FRMListado8.fSetAlinea("");
  FRMListado8.fSetDespliega("texto,");
  FRMListado8.fSetSelReg(8);
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
function fBuscaEmbarcacion(){
  alert("En Construccion...\nNo te Emociones, no soy tan rapido.");
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
   if(cId == "ListInspeccion" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado2.fSetListado(aRes);
     FRMListado2.fShow();
     FRMListado2.fSetLlave(cLlave);
     fCertificados();
   }
   if(cId == "ListCertificados" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado3.fSetListado(aRes);
     FRMListado3.fShow();
     FRMListado3.fSetLlave(cLlave);
     fSolicitud();
   }
   if(cId == "ListSolicitud" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado4.fSetListado(aRes);
     FRMListado4.fShow();
     FRMListado4.fSetLlave(cLlave);
     if(frm.cBandera.value=="MEXICO")frm.lNacional.value=1;else frm.lNacional.value=0;
     fPermisos();
   }
   if(cId == "ListPermisos" && cError==""){
     frm.hdRowPag.value = iRowPag;
     if(frm.lNacional.value==1){
       FRMListado5.fSetTitulo("Permiso,Puerto,Vigencia,");
     }
     else{
       FRMListado5.fSetTitulo("Empresa,Motivo de Contrato,Vigencia,Aprobado,");
     }
     FRMListado5.fSetListado(aRes);
     FRMListado5.fShow();
     FRMListado5.fSetLlave(cLlave);
     fArribos();
   }
   if(cId == "ListArribos" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado6.fSetListado(aRes);
     FRMListado6.fShow();
     FRMListado6.fSetLlave(cLlave);
     FRMListado6.fSetSelReg(6);//
     fDeficiencias();
   }
   if(cId == "cIdDefi" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado7.fSetListado(aRes);
     FRMListado7.fShow();
     FRMListado7.fSetLlave(cLlave);
     FRMListado7.fSetSelReg(7);//cIdDefi
     fDocumentos();
   }
   if(cId == "cIdDoctos" && cError==""){
     FRMListado8.fSetListado(aRes);
     FRMListado8.fShow();
     FRMListado8.fSetLlave(cLlave);
     FRMListado8.fSetSelReg(8);//cIdDefi
   }
 }

function fSetEmbarcacion
  (iCveVehiculo,cNomEmbarcacion,cPropietario,cNumOMI,cMatricula,cNumSerie,cTipoServ,cTipoNavega,hdPropOPoseedor,
  iFolioRPMN,cPaisAbanderamiento,cTipoEmbarcacion,cSenalDist,iCveTipoServ,iCveTipoNavega,iCveTipoEmbarcacion){
    frm.iCveVehiculo.value    = iCveVehiculo;
    frm.cNomEmbarcacion.value = cNomEmbarcacion;
    frm.cMatricula.value      = cMatricula;
    frm.cTipoNavega.value     = cTipoNavega;
    frm.cTipoServ.value       = cTipoServ;
    frm.cTipoEmbarcacion.value= cTipoEmbarcacion;
    frm.cBandera.value        = cPaisAbanderamiento;
    frm.cNumOMI.value         = cNumOMI;
    frm.iFolioRPMN.value      = iFolioRPMN;
    frm.cSenalDist.value      = cSenalDist;
    fInspecciones();
  }
function fSetVarios
  (dArqueoBruto,dArqueoNeto,dPesoMuerto,dEslora,dManga,dPuntal,cPuertoAband,hdlArtefacto,iTripulacionMax,dtConstruccion,iNumBodegas
  ,iNumTanques,dCaladoMax,hdUnidMedManga,hdUnidMedArqueoNeto,hdUniMedArqueoBruto,iCveEntFedMatricula,iCveMunicMatricula,
  cDscEntFedMatricula,cDscMunicMatricula,cUnidMedEslora,cUnidMedManga,cUnidMedPuntal,cUnidMedCaladoPopa,cUnidMedCaladoProa
  ,cUnidMedCaladoMax,cUnidMedArqueoBruto,cUnidMedArqueoNeto){
    frm.cUnidMedArqueoBruto.value   = dArqueoBruto+" "+cUnidMedArqueoBruto;
    frm.cUnidMedArqueoNeto.value    = dArqueoNeto+" "+cUnidMedArqueoNeto;
    fInspecciones();
  }

function fCertificados(){
  frm.hdNumReg.value =  10000;
  frm.hdSelect.value = "SELECT CE.DTINIVIGENCIA, CE.DTFINVIGENCIA, TC.CDSCCERTIFICADO,ICONSECUTIVOCERTEXP, U.ICVEUSUARIO, ce.ICVEOFICINA, " +
    "U.CNOMBRE||' '|| U.CAPPATERNO||' '|| U.CAPMATERNO AS CUSUARIO, O.CDSCBREVE AS COFICINA, S.ICVETRAMITE " +
    "FROM INSCERTIFEXPEDIDOS CE " +
    "join INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CE.ITIPOCERTIFICADO AND TC.ICVEGRUPOCERTIF = CE.ICVEGRUPOCERTIF " +
    "join TRAREGSOLICITUD S ON CE.INUMSOLICITUD = S.INUMSOLICITUD AND CE.IEJERCICIO = S.IEJERCICIO " +
    "join SEGUSUARIO U ON U.ICVEUSUARIO = CE.ICVEUSUARIO " +
    "JOIN GRLOFICINA O ON O.ICVEOFICINA = CE.ICVEOFICINA " +
    "where CE.ICVEVEHICULO = " + frm.iCveVehiculo.value;
  frm.hdLlave.value  = "CE.ICVEVEHICULO, CE.ICONSECUTIVOCERTEXP,";
  fEngSubmite("pgConsulta.jsp","ListCertificados");
}
function fInspecciones(){
  frm.hdSelect.value = "SELECT I.DTINIINSP, I.DTFININSP,P.CDSCPUERTO,COBSES FROM INSINSPECCION I " +
                       "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
                       "JOIN GRLPUERTO P ON P.ICVEPUERTO = I.ICVEPUERTO " +
                       "WHERE PI.ICVEEMBARCACION = "+frm.iCveVehiculo.value;
  frm.hdLlave.value = "iCveInspeccion";
  fEngSubmite("pgConsulta.jsp","ListInspeccion");
}

function fSolicitud(){
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
                     "WHERE IIDBIEN = "+frm.iCveVehiculo.value;
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
  frm.hdSelect.value="SELECT P1.CDSCPUERTO AS CPUPROCEDENCIA, P2.CDSCPUERTO AS CPUDESTINO, " +
                     "TSARRIBOPROGRAMADO, TSZARPEPROGRAMADO,O.CDSCBREVE,A.INUMARRIBO " +
                     "FROM NAVARRIBOS A " +
                     "JOIN GRLPUERTO P1 ON P1.ICVEPUERTO = A.ICVEPUERTOPROCED " +
                     "JOIN GRLPUERTO P2 ON P2.ICVEPUERTO = A.ICVEPUERTODESTINO " +
                     "JOIN GRLOFICINA O ON O.ICVEOFICINA = A.ICVEOFICINA " +
                     "WHERE A.ICVEEMBARCACION = "+frm.iCveVehiculo.value;
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
function fDocumentos(){
  frm.hdSelect.value = "SELECT " +
                       "              ED.ICONSECUTIVODOCTECEMB, " +
                       "              DT.CDSCDOCTECNICO, " +
                       "              ED.DTAUTORIZACION AS , " +
                       "              ED.LVIGENTE, " +
                       "              ED.ICVEDOCTECNICO, " +
                       "              ED.DTINIVIGENCIA AS DTINIVIGENCIA, " +
                       "              ED.DTFINVIGENCIA AS DTFINVIGENCIA, " +
                       "              DT.LTIENEVIGENCIA, " +
                       "              ED.ICVEVEHICULO, " +
                       "              VE.CNOMEMBARCACION, " +
                       "              ED.IEJERCICIO, " +
                       "              ED.INUMSOLICITUD " +
                       "FROM INSEMBARCXDOCTEC ED " +
                       "  JOIN INSDOCTECNICO DT ON DT.ICVEDOCTECNICO = ED.ICVEDOCTECNICO " +
                       "  JOIN VEHEMBARCACION VE ON VE.ICVEVEHICULO = ED.ICVEVEHICULO " +
                       "  WHERE VE.ICVEVEHICULO= " +frm.iCveVehiculo.value+
                       "  ORDER BY ED.ICONSECUTIVODOCTECEMB ";
  frm.hdLlave.value ="";
  fEngSubmite("pgConsulta.jsp","cIdDoctos");
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
function fGetiCveVehiculo(){
	return frm.iCveVehiculo.value;
}
function fMostrarNo(){
  alert("Muestra inspecciones\nNo ahu no esta hecha la pagina\nPasiencia...");
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

function fSelReg3(aDato){
  frm.iConsecutivoCertExp.value = aDato[3];
  frm.iCveUsuario.value = aDato[4];
  frm.iCveOficina.value = aDato[5];
  frm.iCveTramite.value = aDato[8];
}

function fSelReg6(aDato){
  frm.iCveArriboA.value = aDato[5];
}
function fGetArriboE(){
  return frm.iCveVehiculo.value;
}

function fGetArriboA(){
  return frm.iCveArriboA.value;
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

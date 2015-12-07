// MetaCD=1.0
// Title: pg114020061.js
// Description: JS "Catálogo" de la entidad INSInspeccion
// Company: Tecnología InRed S.A. de C.V.
// Author: Arturo López Peña
var cTitulo = "Arqueo Embarcaciones Matriculadas";
var frm;
var lUsuOficinaCentral = false;
var iCveUsr = top.fGetUsrID();
var iGrupo=0;
var iTipo=0;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg114020061.js";
  if(top.fGetTituloPagina)
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",0,"100%","","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","","center");
      ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple("Datos Generales de la Solicitud");
          FTDTR();
          ITR();
             TDEtiCampo(true,"EEtiqueta",0," Ejercicio: ","iEjercicio","",3,3," Comentario: ","fMayus(this);");
             TDEtiCampo(true,"EEtiqueta",0," Solicitud: ","iNumSolicitud","",3,3," Comentario: ","fMayus(this);");
           FITR();
             TDEtiCampo(true,"EEtiqueta",0," Tramite: ","cTramite","",100,100," Comentario: ","fMayus(this);","","","","EETiquetaL",7);
           FITR();
             TDEtiCampo(true,"EEtiqueta",0," Modalidad: ","cModalidad","",100,100," Comentario: ","fMayus(this);","","","","EETiquetaL",7);
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Embarcacion:","cNomEmbarcacion","",100,100," Comentario: ","fMayus(this);","","","","EETiquetaL",7);
           FITR();
             TDEtiCampo(true,"EEtiqueta",0," Matricula: ","cMatricula","",20,20," Comentario: ","fMayus(this);","","","","EETiquetaL",7);
           FTDTR();
             TDEtiSelect(true,"EEtiqueta",0,"Grupo Certificado:","iCveGrupoCertif","");
             TDEtiSelect(true,"EEtiqueta",0,"Tipo de Certificado:","iTipoCertificado","");
          ITRTD("ETablaST",5,"","","center");
            TextoSimple("Evaluación");
          FITR();
            TDEtiCheckBox("EEtiqueta",0,"Aprobado:","lAprovadoBOX","1",true,"Vigente","","onClick=fApruebaOnChange();");
            Hidden("lAprovado","");
          ITD();
            TDEtiCheckBox("EEtiqueta",0,"Autorizado:","lAutorizadoBOX","1",true,"Vigente","","onClick=fAcutorizaOnChange();");
            Hidden("lAutorizado","");
          FITR();
         FinTabla();
      FTDTR();
      FinTabla();
      ITRTD("",0,"","1","center");
        InicioTabla("",0,"75%","","","",1);
          ITD("","","","40","center","bottom");
          BtnImg("guardar","guardar","fGuardar();");
        FinTabla();
    FinTabla();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCveVehiculo");
    Hidden("iCveUsuario");
    Hidden("iCveInspector");
    Hidden("iCveOficina",iOficinaCentral);
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  fDisabled(true);
  frm.iCveUsuario.value = iCveUsr;
  frm.hdBoton.value="Primero";
  frm.lAprovadoBOX.disabled=false;
  frm.lAutorizadoBOX.disabled=false;
  frm.iCveGrupoCertif.disabled=true;
  frm.iTipoCertificado.disabled=true;
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
    alert("Error: "+cError);
  else{
    if(cId == "cIdData"){
      if(aRes.length>0){
        if(aRes[0][4]!=""){
          frm.cTramite.value=aRes[0][0];
          frm.cModalidad.value=aRes[0][1];
          frm.iCveVehiculo.value=aRes[0][2];
          frm.cNomEmbarcacion.value=aRes[0][3];
          frm.cMatricula.value=aRes[0][4];
          frm.lAprovadoBOX.checked=aRes[0][7]==1?true:false;
          frm.lAprovado.value=aRes[0][7];
          frm.lAutorizadoBOX.checked=aRes[0][8]==1?true:false;
          frm.lAutorizado.value=aRes[0][8];
          //fAsignaSelect(frm.iCveGrupoCertif,aRes[0][9],aRes[0][10]);
          iGrupo=aRes[0][9];
          //fAsignaSelect(frm.iTipoCertificado,aRes[0][11],aRes[0][12]);
          iTipo=aRes[0][11];
          frm.iCveGrupoCertif.disabled=true;
          frm.iTipoCertificado.disabled=true;
          /*if(frm.lAprovado.value==1 || frm.lAutorizado.value==1){
            frm.iCveGrupoCertif.disabled=true;
            frm.iTipoCertificado.disabled=true;
          }else{
            frm.iCveGrupoCertif.disabled=false;
            frm.iTipoCertificado.disabled=false;
          }*/
          fTraeGrupos();
        }
        else
          fAlert("La embarcacion asignada a esta solicitud\n no tiene una matricula registrada.");
      }
      else
        fAlert("no se encontraron registros");
    }
    if(cId == "cIdOficinasDeUsuario")
      lUsuOficinaCentral = fEsUsuarioDeOficinaCentral(aRes,cId,cError);
    if(cId=="cIdGrupo"){
      fFillSelect(frm.iCveGrupoCertif,aRes,false,0,0,1);
      fSelectSetIndexFromValue(frm.iCveGrupoCertif, iGrupo);
      fTraeCertificados();
    }
    if(cId=="cIdCertificado"){
      fFillSelect(frm.iTipoCertificado,aRes,false,0,0,1);
      fSelectSetIndexFromValue(frm.iTipoCertificado, iTipo);
      fTraeOficinasDeUsuario();
    }
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fGuardar(){
  if(lUsuOficinaCentral){
    frm.lAprovado.value = frm.lAprovadoBOX.checked==true?1:0;
    frm.lAutorizado.value = frm.lAutorizadoBOX.checked==true?1:0;
    frm.iCveInspector.value = frm.iCveUsuario.value;
    frm.hdBoton.value = "GuardarMat";
    fEngSubmite("pgINSCertxInspeccion.jsp","cIdGuardar");
  }else fAlert("Esta opcion solo es disponible para usuarios pertenecientes a la Oficina Central");
}
function fValidaTodo(){
   cMsg = fValElements();
   if(cMsg != ""){
      fAlert(cMsg);
      return false;
   }
   return true;
}
function fInicio(iEjercicio,iNumSolicitud){
  frm.iEjercicio.value = iEjercicio;
  frm.iNumSolicitud.value = iNumSolicitud;
  frm.hdSelect.value ="SELECT " +
                      "       T.CCVEINTERNA||' - '||T.CDSCBREVE as cTramite, " +
                      "       M.CDSCMODALIDAD, " +
                      "       E.ICVEVEHICULO, " +
                      "       E.CNOMEMBARCACION, " +
                      "       V.CMATRICULA, " +
                      "       S.ICVEOFICINA, " +
                      "       O.CDSCOFICINA, " +
                      "       CI.LAPROBADA, " +
                      "       CI.LAUTORIZADO, " +
                      "       TC.ICVEGRUPOCERTIF, " +
                      "       GC.CDSCGRUPOCERTIF, " +
                      "       TC.ITIPOCERTIFICADO, " +
                      "       TC.CDSCCERTIFICADO " +
                      "FROM TRAREGSOLICITUD S " +
                      "  JOIN VEHEMBARCACION E ON E.ICVEVEHICULO=S.IIDBIEN " +
                      "  JOIN VEHVEHICULO V ON V.ICVEVEHICULO=S.IIDBIEN " +
                      "  JOIN TRACATTRAMITE T ON T.ICVETRAMITE=S.ICVETRAMITE " +
                      "  JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=S.ICVEMODALIDAD " +
                      "  JOIN GRLOFICINA O ON O.ICVEOFICINA=S.ICVEOFICINA " +
                      "  LEFT JOIN INSPROGINSP PI ON PI.IEJERCICIO=S.IEJERCICIO AND PI.INUMSOLICITUD=S.INUMSOLICITUD AND PI.LARQMATRICULDA=1 " +
                      "  LEFT JOIN INSCERTXINSPECCION CI ON CI.ICVEINSPPROG=CI.ICVEINSPPROG AND CI.IEJERCICIO=S.IEJERCICIO AND CI.INUMSOLICITUD=S.INUMSOLICITUD " +
                      "  LEFT JOIN INSCERTIFICADOXMODTRAM CTM ON CTM.ICVETRAMITE=S.ICVETRAMITE AND CTM.ICVEMODALIDAD=S.ICVEMODALIDAD " +
                      "  LEFT JOIN INSTIPOCERTIF TC ON TC.ICVEGRUPOCERTIF=CTM.ICVEGRUPOCERTIF AND TC.ITIPOCERTIFICADO=CTM.ITIPOCERTIFICADO " +
                      "  LEFT JOIN INSGRUPOCERTIF GC ON GC.ICVEGRUPOCERTIF=TC.ICVEGRUPOCERTIF "+
                      " WHERE S.IEJERCICIO = "+frm.iEjercicio.value+
                      "   AND S.INUMSOLICITUD="+frm.iNumSolicitud.value;
  frm.hdLlave.value = "";
  fEngSubmite("pgConsulta.jsp", "cIdData");
}
function fAcutorizaOnChange(){
  if(frm.lAutorizadoBOX.checked==false && frm.lAutorizado.value == 1){
    frm.lAutorizadoBOX.checked=true;
    fAlert("No es posible desautorizar este registro");
  }
}
function fApruebaOnChange(){
  if(frm.lAprovadoBOX.checked==false && frm.lAprovado.value == 1){
    frm.lAprovadoBOX.checked=true;
    fAlert("No es posible desaprovar este registro");
  }
}
function fTraeGrupos(){
  frm.hdLlave.value = "";
  frm.hdSelect.value = "SELECT ICVEGRUPOCERTIF,CDSCGRUPOCERTIF FROM INSGRUPOCERTIF " +
                       "WHERE LACTIVO=1 ";
  //if (iInspeccionGrupoCertMatriculadas>0)
    //frm.hdSelect.value += "AND ICVEGRUPOCERTIF="+iInspeccionGrupoCertMatriculadas;
  fEngSubmite("pgConsulta.jsp","cIdGrupo");
}
function fTraeCertificados(){
  frm.hdLlave.value = "";
  frm.hdSelect.value = "SELECT ITIPOCERTIFICADO,CDSCCERTIFICADO FROM INSTIPOCERTIF " +
                       "WHERE ICVEGRUPOCERTIF = "+frm.iCveGrupoCertif.value+
                       " AND ICVEAPLICACERTIF = "+iInspeccionAplicaCertificadoMatriculadas;
  fEngSubmite("pgConsulta.jsp","cIdCertificado");
}
//Exportacion de valores
function fGetIdEmbarcacion(){
  return frm.iCveVehiculo.value;
}
function fGetLAutorizadas(){
  return frm.lAutorizado.value;
}
function fGetLAprovadas(){
  return frm.lAprovado.value;
}

// MetaCD=1.0
// Title: pg115020052.js
// Description: JS "Catálogo" de la entidad MYRMatricula
// Company: S.C.T.
// Author: Arturo López Peña
var cTitulo = "";
var FRMListado = "";
var frm;
var aEstatus = new Array();
var lFiltro=true;
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110020053.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

// SEGMENT Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",6,"100%","","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","","center");
      ITRTD("",6,"","1","center");
        InicioTabla("ETablaInfo",0,"85%","","","",1);
          ITR();
            TDEtiCampo(false,"EEtiqueta",0,"Embarcacion:","cNomEmbarcacion","",60,60,"Embarcacion a filtrar","fMayus(this);");
          FITD();
            BtnImgNombre("vgbuscar","lupa","fBuscaEmbFiltro();","Buscar la embarcación", false, "", "BuscaEmb");
          FTR();
        FinTabla();
      ITRTD("",6,"","1","center");
        InicioTabla("ETablaInfo",0,"85%","","","",1);
          ITR();
            TDEtiSelect(true,"EEtiqueta",0,"Entidad Federativa:","iCveEntidadFedF","fEntidadOnChangeF();");
            TDEtiSelect(true,"EEtiqueta",0,"Capitania:","iCveCapitaniaF","");
          FITR();
            TDEtiCampo(false,"EEtiqueta",0,"Serie:","cSerieF","",1,1,"Serie de la matricula","fMayus(this);");
            TDEtiCampo(false,"EEtiqueta",0,"Consecutivo:","iConsecutivoF","",3,3,"Consecutivo de la Matricula","fMayus(this);");
          FITR();
            TDEtiSelect(true,"EEtiqueta",0,"T. Servicio:","iCveTipoServicioF","");
            TDEtiSelect(true,"EEtiqueta",0,"T. Navegación:","iCveTipoNavegaF","");
          FITR();
            TDEtiCampo(false,"EEtiqueta",0,"Digito verificador:","iDigVerificadorF","",1,1,"Digito verificador","fMayus(this);");
            ITD();
              BtnImgNombre("vgbuscar","lupa","fBuscaMatricula();","Buscar la Matricula", false, "", "buscaMat");
          FTR();
        FinTabla();
          ITRTD("",6,"","1","center");
            InicioTabla("",0,"100%","","","",1);
              ITRTD("",0,"","175","center","top");
                IFrame("IListado53","95%","170","Listado.js","yes",true);
              FTDTR();
            FinTabla();
            InicioTabla("ETablaInfo",0,"85%","","","",1);
              ITR();
                TDEtiSelect(true,"EEtiqueta",0,"Entidad Federativa:","iCveEntidadFed","fEntidadOnChange();");
                TDEtiSelect(true,"EEtiqueta",0,"Capitania:","iCveCapitania","");
              FITR();
                TDEtiCampo(false,"EEtiqueta",0,"Serie:","cSerie","",1,1,"Serie de la matricula","fMayus(this);");
                TDEtiCampo(false,"EEtiqueta",0,"Consecutivo:","iConsecutivo","",3,3,"Consecutivo de la Matricula","fMayus(this);");
              FITR();
                TDEtiSelect(true,"EEtiqueta",0,"T. Servicio:","iCveTipoServicio","");
                TDEtiSelect(true,"EEtiqueta",0,"T. Navegación:","iCveTipoNavega","");
              FITR();
                TDEtiCampo(false,"EEtiqueta",0,"Digito verificador:","iDigVerificador","",1,1,"Digito verificador","fMayus(this);");
                TDEtiSelect(true,"EEtiqueta",0,"Estatus Actual:","iEstatus","");
              FITR();
                TDEtiCampo(false,"EEtiqueta",0,"Ejercicio Solicitud:","iEjercicioSolicitud","",4,4,"Ejercicio de la solicitud de Asignacion","fMayus(this);");
                TDEtiCampo(false,"EEtiqueta",0,"Numero Solicitud:","iNumSolicitud","",6,6,"Consecutivo de la solicitud de Asignacion","fMayus(this);");
              FITR();
                TDEtiCampo(false,"EEtiqueta",0,"Embarcacion:","cEmbarcacion","",60,60,"Embarcacion asignada a la matricula","fMayus(this);");
              FITD();
                BtnImgNombre("vgbuscar","lupa","fBuscaEmb();","Buscar la embarcación", false, "", "BuscaEmb");
              FITR();
                TDEtiCampo(false,"EEtiqueta",0,"Propietario:","cPropietario","",60,60,"Propietario de la embarcacion al momento de matricular","fMayus(this);");
              
              FITD();
                BtnImgNombre("vgbuscar","lupa","fAbrePropietario();","Buscar el propietario", false, "", "BuscaEmb");
              FITR();
                TDEtiCampo(false,"EEtiqueta",0,"Fecha de Cancelación:","dtBaja","",10,10,"Propietario de la embarcacion al momento de matricular","fMayus(this);");
              FITR(); 
                TDEtiSelect(true,"EEtiqueta",0,"Puerto Matricula:","iCvePtoMatricula","");
              FTR();
           FITR();
              TDEtiCheckBox("EEtiqueta",0,"Vigente","lVigenteBOX","1",true,"¿Matricula vigente?");
              Hidden("lVigente","");
              TDEtiCheckBox("EEtiqueta",0,"Aprobada","lAprobadaBOX","1",true,"¿Matricula vigente?");
              Hidden("lAprobada","");
      FinTabla();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel53","95%","34","Paneles.js");
       FTDTR();
    FTDTR();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iCveMatricula");
    Hidden("iCveEmbarcacion");
    Hidden("iCvePropietario");
    Hidden("iEjercicio");
    Hidden("");
    FinTabla();
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel53");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado53");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo("Matricula,ID Embarcacion,Nombre Embarcación,Propietario,Estatus,");
  FRMListado.fSetCampos("40,12,21,25,41,");
  fDisabled(true,"iCveEntidadFedF,iCveCapitaniaF,cSerieF,iConsecutivoF,iCveTipoServicioF,iCveTipoNavegaF,iDigVerificadorF,");
  frm.hdBoton.value="Primero";
  aEstatus[0]=[1,"Generada"];
  aEstatus[1]=[2,"Recibida por Capitania"];
  aEstatus[2]=[3,"Asignada a Embarcacion"];
  aEstatus[3]=[4,"Cancelada"];
  fFillSelect(frm.iEstatus,aEstatus,true,-1,0,1);
}
 function fSetEmbarcacion(iCveVehiculo,cNomEmbarcacion){
   frm.iCveEmbarcacion.value=iCveVehiculo;
   frm.cNomEmbarcacion.value = cNomEmbarcacion;
   frm.cEmbarcacion.value = cNomEmbarcacion;
   if(lFiltro==true){
     frm.hdFiltro.value = "mat.iCveEmbarcacion="+frm.iCveEmbarcacion.value;
     fNavega();
   }
 }
function fBuscaMatricula(){
  frm.hdFiltro.value = "";
  if(frm.iCveEntidadFedF.value > 0) frm.hdFiltro.value+=frm.hdFiltro.value==""?"mat.iCveEntidadFed="+frm.iCveEntidadFedF.value:" AND mat.iCveEntidadFed="+frm.iCveEntidadFedF.value;
  if(frm.iCveCapitaniaF.value > 0) frm.hdFiltro.value+=frm.hdFiltro.value==""?"mat.iCveCapitaniaParaMat="+frm.iCveCapitaniaF.value:" AND mat.iCveCapitaniaParaMat="+frm.iCveCapitaniaF.value;
  if(frm.cSerieF.value!="") frm.hdFiltro.value+=frm.hdFiltro.value==""?"mat.cSerie='"+frm.cSerieF.value+"'":" AND mat.cSerie='"+frm.cSerieF.value+"'";
  if(frm.iConsecutivoF.value>0) frm.hdFiltro.value+=frm.hdFiltro.value==""?"mat.iConsecutivoMatricula=?"+frm.iConsecutivoF.value:" AND mat.iConsecutivoMatricula="+frm.iConsecutivoF.value;
  if(frm.iCveTipoServicioF.value > 0) frm.hdFiltro.value+=frm.hdFiltro.value==""?"mat.ICVETIPOSERV="+frm.iCveTipoServicioF.value:" AND mat.ICVETIPOSERV="+frm.iCveTipoServicioF.value;
  if(frm.iCveTipoNavegaF.value > 0) frm.hdFiltro.value+=frm.hdFiltro.value==""?"mat.ICVETIPONAVEGA="+frm.iCveTipoNavegaF.value:" AND mat.ICVETIPONAVEGA="+frm.iCveTipoNavegaF.value;
  if(frm.iDigVerificadorF.value>0) frm.hdFiltro.value+=frm.hdFiltro.value==""?"mat.iDigVerificador="+frm.iDigVerificadorF.value:" AND mat.iDigVerificador="+frm.iDigVerificadorF.value;
  fNavega();
}

// LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
    if(frm.hdFiltro.value!=""){
        frm.hdNumReg.value = 10000;
        frm.hdOrden.value  = "";
        fEngSubmite("pgMYRMatricula.jsp","Listado");
    }else fAlert("Seleccione un filtro valido.");
 }
// RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave, cCapitania, iOficina, iCapitania){
   if(cError!=""){
     fAlert("Existio un Error al Comunicarse con el Servidor o en la operación Indicada");
   }
   else{
     if(cId=="cIdTipoServicio"){
       fFillSelect(frm.iCveTipoServicio,aRes,true,-1,0,1);
       fFillSelect(frm.iCveTipoServicioF,aRes,true,-1,0,1);
       frm.hdNumReg.value = 10000;
       frm.hdFiltro.value = "";
       frm.hdOrden.value  = "";
       fEngSubmite("pgVEHTipoNavegacion.jsp","cIdTipoNavega");
     }
     if(cId=="cIDPtoMat"){
	 fFillSelect(frm.iCvePtoMatricula,aRes,false,-1,0,1);	 
     }
     if(cId=="cIdTipoNavega"){
       fFillSelect(frm.iCveTipoNavega,aRes,true,-1,0,1);
       fFillSelect(frm.iCveTipoNavegaF,aRes,true,-1,0,1);
       fCargaEntidad();
     }
     if(cId=="cIdEntidadFed"){
       fFillSelect(frm.iCveEntidadFed,aRes,true,-1,1,2);
       fFillSelect(frm.iCveEntidadFedF,aRes,true,-1,1,2);
       frm.hdFiltro.value = "iCvePais = 1";
       frm.hdOrden.value  = "cDscBreve";
       fEngSubmite("pgGRLOficinaA1.jsp","cIDPtoMat")
     }
     if(cId=="cIdCapitanias"){
       fFillSelect(frm.iCveCapitania,aRes,true,-1,0,1);
     }
     if(cId=="cIdCapitaniasF"){
       fFillSelect(frm.iCveCapitaniaF,aRes,true,-1,0,1);
     }
     if(cId=="Listado"){
       frm.hdRowPag.value = iRowPag;
       for(i=0;i<aRes.length;i++){
         aRes[i][40]=aRes[i][3]>9?aRes[i][3]:"0"+aRes[i][3];
         aRes[i][40]+=aRes[i][5]>9?aRes[i][5]:"0"+aRes[i][5];
         aRes[i][40]+=aRes[i][6];
         aRes[i][40]+=(aRes[i][7]>99?""+aRes[i][7]:(aRes[i][7]>9?"0"+aRes[i][7]:"00"+aRes[i][7]));
         aRes[i][40]+=aRes[i][9];
         aRes[i][40]+=aRes[i][10];
         aRes[i][40]+=aRes[i][8];
         aRes[i][41]=aRes[i][16]==1?"Generada":(aRes[i][16]==2?"En Capitania":(aRes[i][16]==3?"Asignada a Embarcación":(aRes[i][16]==4?"Cancelada":"")));
       }
       FRMListado.fSetListado(aRes);
       FRMListado.fShow();
       FRMListado.fSetLlave(cLlave);
       fCancelar();
     }
   }
 }
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(true);
    fDisabled(false,"iCveEntidadFedF,iCveCapitaniaF,cSerieF,iConsecutivoF,iCveTipoServicioF,iCveTipoNavegaF,iDigVerificadorF,cPropietario,cEmbarcacion,cNomEmbarcacion,");
    FRMListado.fSetDisabled(true);
 }
 function fGuardarA(){
   if(fValidaTodo()==true){
     frm.lVigente.value=frm.lVigenteBOX.checked == true?1:0;
     frm.lAprobada.value=frm.lAprobadaBOX.checked == true?1:0;
     frm.hdBoton.value = "ActualizaAdmin";
     if(fNavega()==true){
       FRMPanel.fSetTraStatus("UpdateComplete");
       fDisabled(false);
       fDisabled(true,"iCveEntidadFedF,iCveCapitaniaF,cSerieF,iConsecutivoF,iCveTipoServicioF,iCveTipoNavegaF,iDigVerificadorF,cPropietario,cEmbarcacion,cNomEmbarcacion,");
       FRMListado.fSetDisabled(false);
     }
   }
 }
 function fCancelar(){
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Mod,");
    else
      FRMPanel.fSetTraStatus(",");
     (false);
    fDisabled(true,"iCveEntidadFedF,iCveCapitaniaF,cSerieF,iConsecutivoF,iCveTipoServicioF,iCveTipoNavegaF,iDigVerificadorF,");
    FRMListado.fSetDisabled(false);
 }

 function fSelReg(aDato){
   frm.iEjercicio.value = aDato[0];
   frm.iCveMatricula.value = aDato[1];
   fSelectSetIndexFromValue(frm.iCveEntidadFed,aDato[3]);
   fAsignaSelect(frm.iCveCapitania,aDato[5],aDato[33]);
   frm.cSerie.value = aDato[6];
   frm.iConsecutivo.value = aDato[7];
   fSelectSetIndexFromValue(frm.iCveTipoServicio,aDato[10]);
   fSelectSetIndexFromValue(frm.iCveTipoNavega,aDato[9]);
   frm.iDigVerificador.value = aDato[8];
   frm.iEstatus.value = aDato[16];
   frm.iEjercicioSolicitud.value = aDato[14];
   frm.iNumSolicitud.value = aDato[13];
   frm.cEmbarcacion.value = aDato[21];
   frm.iCveEmbarcacion.value = aDato[12];
   frm.cPropietario.value = aDato[25];
   frm.iCvePropietario.value = aDato[32];
   frm.lVigente.value = aDato[11];
   frm.lVigenteBOX.checked = frm.lVigente.value==1?true:false;
   frm.lAprobada.value = aDato[15];
   frm.lAprobadaBOX.checked = frm.lAprobada.value==1?true:false;
   frm.dtBaja.value = aDato[19];
   frm.iCvePtoMatricula.value = aDato[20];
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
 function fInicia(){
   frm.hdNumReg.value = 10000;
   frm.hdFiltro.value = " lActivo = 1 ";
   frm.hdOrden.value  = " cDscTipoServ ";
   fEngSubmite("pgVEHTipoServicio.jsp","cIdTipoServicio");
 }
 function fEntidadOnChangeF(){
   if(frm.iCveEntidadFedF.value > 0){
     frm.hdSelect.value = "SELECT C.ICVECAPITANIAPARAMAT,C.CDSCCAPITANIA FROM MYRCAPITANIA C " +
                          "JOIN GRLOFICINA O ON O.ICVEOFICINA=C.ICVEOFICINA " +
                          "WHERE O.ICVEPAIS= 1 AND O.ICVEENTIDADFED= "+frm.iCveEntidadFedF.value+
                          " ORDER BY CDSCCAPITANIA ";
     frm.hdLlave.value  = "";
     fEngSubmite("pgConsulta.jsp","cIdCapitaniasF");
   }
 }
 function fEntidadOnChange(){
   if(frm.iCveEntidadFed.value > 0){
     frm.hdSelect.value = "SELECT C.ICVECAPITANIAPARAMAT,C.CDSCCAPITANIA FROM MYRCAPITANIA C " +
                          "JOIN GRLOFICINA O ON O.ICVEOFICINA=C.ICVEOFICINA " +
                          "WHERE O.ICVEPAIS= 1 AND O.ICVEENTIDADFED= "+frm.iCveEntidadFed.value+
                          " ORDER BY CDSCCAPITANIA ";
     frm.hdLlave.value  = "";
     fEngSubmite("pgConsulta.jsp","cIdCapitanias");
   }
 }
 function fCargaEntidad(){
   frm.hdFiltro.value = "iCvePais=1";
   frm.hdOrden.value  = "cNombre";
   fEngSubmite("pgGRLEntidadFed.jsp","cIdEntidadFed");
 }

 function fBuscaEmb(){
   if(frm.cSerie.disabled==false){
     lFiltro=false;
     fAbreBuscaEmbarcacion();
   }
 }
 function fBuscaEmbFiltro(){
   if(frm.cSerie.disabled==true){
     lFiltro=true;
     fAbreBuscaEmbarcacion();
   }
 }
function fAbrePropietario(){
  if(frm.cSerie.disabled==false){
    fAbreSolicitante();
  }
}
function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno){
  frm.iCvePropietario.value = iCvePersona;
  alert("Propietario:  "+iCvePersona);
  frm.cPropietario.value = cNomRazonSocial+" "+cApPaterno+" "+cApMaterno;
}

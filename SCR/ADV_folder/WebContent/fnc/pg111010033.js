// MetaCD=1.0
// Title: pg111010033.js
// Description: JS "Catálogo" de la entidad TRAConfiguraTramite
// Company: Tecnología InRed S.A. de C.V.
// Author: Jorge Arturo Wong Mozqueda
var cTitulo = "";
var FRMListado = "";
var frm;
var lDelFiltro=true;
var lDelListado=false;
var lGuardando=false;
var lNuevo=false;
var lActualizaListado=false;
var lBorrado=false;

// SEGMENTO antes de cargar la página (Definición Mandatoria)

function fBefLoad(){
  cPaginaWebJS = "pg111010033.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"","","top");
    InicioTabla("",0,"100%","","center");
      ITRTD("",3,"","40","center","top");
        IFrame("IFiltro33","95%","34","Filtros.js");
      FTDTR();
      ITRTD("",0,"45%","","center","");
        IFrame("IListado33A","100%","80%","Listado.js","yes",true);
      FTD();
      ITD("",0,"10%","","center","center");

      FTD();
      ITD("",0,"45%","","center","");
        IFrame("IListado33","100%","80%","Listado.js","yes",true);
      ITD("",0,"","");
      FTDTR();
      ITRTD("",3,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
          ITRTD("ETablaST",10,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
             TDEtiCampo(false,"EEtiqueta",0," Trámite:","cDscTramite","",100,100," Trámite","fMayus(this);","","","","EEtiquetaL","5");
             Hidden("iCveTramite");
          FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Modalidad:","iCveModalidad","","",2);
          FITR();
              TDEtiCampo(true,"EEtiqueta",0," Inicio Vigencia:","dtIniVigencia","",10,10," Inicio Vigencia","fMayus(this);");
//           FITR();
//              TDEtiCampo(true,"EEtiqueta",0," Núm. días Resolución:","iNumDiasResolV","",3,3," Núm. días Resolución","fMayus(this);");
              Hidden("iNumDiasResol",-1);
//              TDEtiCheckBox("EEtiqueta",0," Días Naturales:","lDiasNaturalesResolBOX","1",true," Días Naturales");
              Hidden("lDiasNaturalesResol",0);
//              TDEtiCheckBox("EEtiqueta",0," No Aplica:","lRequiereDiasBOX","0",true," No Aplica número de días de Resolución","","onClick=fRequiereDias();");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0," Núm. de días para cubrir requisitos:","iNumDiasCubrirReq","",3,3," Núm. de días para cubrir requisitos","fMayus(this);");
              ITD();
              	TextoSimple("(Nota: Tiempo de atención de requisitos en días naturales.)");
              FTD();
//              TDEtiCheckBox("EEtiqueta",0," Días Naturales:","lDiasNaturalesReqBOX","1",true," Días Naturales");
              Hidden("lDiasNaturalesReq",0);
           FITR();
//              TDEtiCheckBox("EEtiqueta",0," Requiere Pago:","lRequierePagoBOX","1",true," Requiere Pago");
              Hidden("lRequierePago",1);
              Hidden("flag1");
              Hidden("flag2");
           FITR();
              //TDEtiSelect(true,"EEtiqueta",0," Formato Captura:","iCveFormato","","EEtiquetaL",7);
           	  Hidden("iCveFormato",0);
           //    ITD();
              //Liga("Configurar Opiniones","fVerifica();","Verifica los datos");
           FTD();
           FITR();
              //TDEtiCampo(true,"EEtiqueta",0,"Tramite CIS:","iCveTramiteCIS","",10,10,"Clave del tramite en el Sistema CIS");
           FITR();
              //TDEtiAreaTexto(false,"EEtiqueta",0,"Notas:",80,3,"cNotas","","Notas","","fMayus(this);",'onkeydown="fMxTx(this,1200);"',true,true,true,"",9);
          FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Días despues de notifición PNC:","iDiasDespuesNotif","",5,5,"Dias con los que cuenta un usuario para cumplir con los documentos apartir de la notificacion.");
              ITD();
            	TextoSimple("(Nota: Tiempo de atención de requisitos como respuesta a la notificación en días naturales.)");
             FTD();
//              TDEtiCheckBox("EEtiqueta",0,"Activo:","lActivoBOX","1",true," Días Naturales");
//              TDEtiCheckBox("EEtiqueta",0,"Trámite por Internet:","lTramIntBOX","1",true," Días Naturales");
              Hidden("lActivo",1);
              Hidden("lTramInt",1);
          FTR();
        FinTabla();
      FTDTR();
      FinTabla();
    FTDTR();
      ITRTD("",0,"","40","center","bottom");
        IFrame("IPanel33","95%","34","Paneles.js");
      FTDTR();
    FinTabla();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("cNotas","");
    Hidden("iCveTramiteCfg");
    Hidden("iCveModalidadCfg");
    Hidden("cDscTramiteCfg");
    Hidden("cDscModalidadCfg");
    Hidden("iCveUsuario",top.fGetUsrID());
    
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel33");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  FRMPanel.fShow("Tra,");
  FRMListado = fBuscaFrame("IListado33");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(" Inicio Vig., Núm. días Resolución,");
  FRMListado.fSetCampos("2,20,");
  FRMListado.fSetAlinea("center,center,");
  FRMListadoA = fBuscaFrame("IListado33A");
  FRMListadoA.fSetControl(self);
  FRMListadoA.fSetTitulo("Modalidades Configuradas,");
  FRMListadoA.fSetAlinea("left,");
  FRMListadoA.fSetCampos("1,");
  FRMListadoA.fSetSelReg(2);
  FRMFiltro = fBuscaFrame("IFiltro33");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow("Nav,Reg,");
  fDisabled(true);
  frm.hdBoton.value="Primero";
  frm.iCveModalidad.value = -1;
  frm.iCveTramite.value = -1;
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  if(frm.iCveTramite.value != "" && frm.iCveModalidad.value != ""){
    frm.hdFiltro.value =  "TC.iCveTramite = " + frm.iCveTramite.value +
                          " and TC.iCveModalidad = " + frm.iCveModalidad.value;
    frm.hdOrden.value =  " TC.dtIniVigencia DESC";
    frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
    return fEngSubmite("pgTRAConfiguraTramite.jsp","Listado");
  } else {
    frm.hdFiltro.value =  "TC.iCveTramite = 0" +
                          " and TC.iCveModalidad = 0";
    frm.hdOrden.value  =  " TC.dtIniVigencia DESC";
    frm.hdNumReg.value =  FRMFiltro.document.forms[0].iNumReg.value;
    return fEngSubmite("pgTRAConfiguraTramite.jsp","Listado");

  }
}

function fNavega2(){
  if (frm.iCveTramite.value != ""){
    frm.hdSelect.value = "select distinct(TRAConfiguraTramite.iCveModalidad), " +
                         "TRAModalidad.cDscModalidad, " +
                         "TRACatTramite.iCveTramite, " +
                         "TRACatTramite.cDscBreve " +
                         "from TRAConfiguraTramite " +
                         "join TRAModalidad on TRAModalidad.iCveModalidad = TRAConfiguraTramite.iCveModalidad " +
                         "join TRACatTramite on TRACatTramite.iCveTramite = TRAConfiguraTramite.iCveTramite " +
                         "where TRAConfiguraTramite.iCveTramite = " + frm.iCveTramite.value + " " +
                         "order by TRAModalidad.cDscModalidad ";
    frm.hdLlave.value = "TRAConfiguraTramite.iCveTramite,TRAConfiguraTramite.iCveModadlidad,";
    frm.hdNumReg.value = 10000;
    frm.hdOrden.value = "";
    frm.hdFiltro.value = "";
    fEngSubmite("pgConsulta.jsp","IDSoloModalidades");
  }
}

function fActualiza(){
  if (frm.iCveTramite.value != ""){
    frm.hdSelect.value = "select distinct(TRAConfiguraTramite.iCveModalidad), " +
                         "TRAModalidad.cDscModalidad, " +
                         "TRACatTramite.iCveTramite, " +
                         "TRACatTramite.cDscBreve " +
                         "from TRAConfiguraTramite " +
                         "join TRAModalidad on TRAModalidad.iCveModalidad = TRAConfiguraTramite.iCveModalidad " +
                         "join TRACatTramite on TRACatTramite.iCveTramite = TRAConfiguraTramite.iCveTramite " +
                         "where TRAConfiguraTramite.iCveTramite = " + frm.iCveTramite.value + " " +
                         "order by TRAModalidad.cDscModalidad ";
    frm.hdLlave.value = "TRAConfiguraTramite.iCveTramite,TRAConfiguraTramite.iCveModadlidad,";
    frm.hdNumReg.value = 10000;
    frm.hdOrden.value = "";
    frm.hdFiltro.value = "";
    fEngSubmite("pgConsulta.jsp","IDActualiza");
  }
}


// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError=="Guardar")
    fAlert("Existió un error en el Guardado!");
  if(cError=="Duplicado")
    fAlert("Registro Existente!");
  if(cError=="Borrar")
    fAlert("Existió un error en el Borrado!");
  if(cError=="Cascada"){
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
    return;
  }
  if(cError!="")
    FRMFiltro.fSetNavStatus("Record");
  if(cId == "Listado" && cError==""){
    var aListado = new Array();
    var aReng;
    frm.hdRowPag.value = iRowPag;
    for(i=0; i<aRes.length;i++){
      aReng = new Array();
      aReng[0] = aRes[i][0];
      aReng[1] = aRes[i][1];
      aReng[2] = aRes[i][2];
      aReng[3] = aRes[i][3];
      aReng[4] = aRes[i][4];
      aReng[5] = aRes[i][5];
      aReng[6] = aRes[i][6];
      aReng[7] = aRes[i][7];
      aReng[8] = aRes[i][8];
      aReng[9] = aRes[i][9];
      aReng[10] = aRes[i][10];
      aReng[11] = aRes[i][11];
      aReng[12] = aRes[i][12];
      aReng[13] = aRes[i][13];
      aReng[14] = aRes[i][14];
      aReng[15] = aRes[i][15];
      aReng[16] = aRes[i][16];
      if(aRes[i][3] == -1)
         aReng[20] = "No Aplica";
      else
         aReng[20] = aRes[i][3];
      aListado[i] = aReng;
    }
    FRMListado.fSetListado(aListado);
    FRMListado.fShow();
    FRMListado.fSetLlave(cLlave);
    fCancelar();
    FRMFiltro.fSetNavStatus(cNavStatus);

    if (lActualizaListado)
      fActualiza();
    if(lBorrado){
       lBorrado = false;
       fNavega2();
    }
  }

  if(cId == "IDSoloModalidades" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListadoA.fSetListado(aRes);
    FRMListadoA.fShow();
    FRMListadoA.fSetLlave(cLlave);
    fReposicionaListado(FRMListadoA,"2,0",frm.iCveTramite.value + "," + frm.iCveModalidad.value + "");
    fNavega();
  }

  if(cId == "IDActualiza" && cError==""){
    frm.hdRowPag.value = iRowPag;
    FRMListadoA.fSetListado(aRes);
    FRMListadoA.fShow();
    FRMListadoA.fSetLlave(cLlave);
    fReposicionaListado(FRMListadoA,"2,0",frm.iCveTramite.value + "," + frm.iCveModalidad.value + "");
    lActualizaListado=false;
  }

  if(cId == "CIDFormato" && cError==""){
    for(i=0;i<aRes.length;i++)
      aRes[i][10] = aRes[i][0]+" - "+aRes[i][1];
    //if (lNuevo)
    //  fFillSelect(frm.iCveFormato,aRes,true,frm.iCveFormato.value,0,10);
    //else
    //  fFillSelect(frm.iCveFormato,aRes,false,frm.iCveFormato.value,0,10);
  }
  if(cId == "cIdModalidad" && cError==""){
    fFillSelect(frm.iCveModalidad,aRes,true,frm.iCveModalidad.value,0,1);
    fNavega2();
  }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
   frm.hdFiltro.value = "";
   frm.hdOrden.value =  "";
   //frm.iCveFormato.value = "";
   frm.hdNumReg.value = 10000;
   //lNuevo=true;
   //if(fEngSubmite("pgGRLFormato.jsp","CIDFormato") == true ){
     FRMPanel.fSetTraStatus("UpdateBegin");
     fBlanked("iCveTramite,cDscTramite,");
     fDisabled(false,"iCveTramite,cDscTramite,","--");
     FRMListado.fSetDisabled(true);
  // }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){
//   frm.lDiasNaturalesResolBOX.checked==true?frm.lDiasNaturalesResol.value=1:frm.lDiasNaturalesResol.value=0;
//   frm.lDiasNaturalesReqBOX.checked==true?frm.lDiasNaturalesReq.value=1:frm.lDiasNaturalesReq.value=0;
//   frm.lRequierePagoBOX.checked==true?frm.lRequierePago.value=1:frm.lRequierePago.value=0;
//   frm.lTramIntBOX.checked==true?frm.lTramInt.value=1:frm.lTramInt.value=0;
//   if(frm.lRequiereDiasBOX.checked == true)
//     frm.iNumDiasResol.value = -1;
//   else
//     frm.iNumDiasResol.value = frm.iNumDiasResolV.value;
   lNuevo=false;
   lGuardando=true;
//   frm.lActivo.value = frm.lActivoBOX.checked==true?1:0;
   if (FRMListado.length == 0)
     lActualizaListado=true;
   if(fValidaTodo()==true){
      if(fNavega()==true){
         FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA(){
//   frm.lDiasNaturalesResolBOX.checked==true?frm.lDiasNaturalesResol.value=1:frm.lDiasNaturalesResol.value=0;
//   frm.lDiasNaturalesReqBOX.checked==true?frm.lDiasNaturalesReq.value=1:frm.lDiasNaturalesReq.value=0;
//   frm.lRequierePagoBOX.checked==true?frm.lRequierePago.value=1:frm.lRequierePago.value=0;
//   frm.lTramIntBOX.checked==true?frm.lTramInt.value=1:frm.lTramInt.value=0;
//   if(frm.lRequiereDiasBOX.checked == true)
//     frm.iNumDiasResol.value = -1;
//   else
//     frm.iNumDiasResol.value = frm.iNumDiasResolV.value;
//   frm.lActivo.value = frm.lActivoBOX.checked==true?1:0;

   lGuardando=true;
   lActualizaListado=false;
   if(fValidaTodo()==true){
      if(fNavega()==true){
        FRMPanel.fSetTraStatus("UpdateComplete");
        fDisabled(true);
        FRMListado.fSetDisabled(false);
      }
   }
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar(){
    frm.hdFiltro.value =  "";
    frm.hdOrden.value =  "";
    frm.hdNumReg.value = 10000;
    lGuardando=false;
    //fEngSubmite("pgGRLFormato.jsp","CIDFormato")
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"dtIniVigencia,iCveTramite,cDscTramite,iCveModalidad,");
//    if(frm.lRequiereDiasBOX.checked == true){
//       frm.lDiasNaturalesResolBOX.disabled = true;
////       frm.iNumDiasResolV.disabled = true;
//       frm.iNumDiasResolV.value = "";
//    }else{
//       frm.lDiasNaturalesResolBOX.disabled = false;
//       frm.iNumDiasResolV.disabled = false;
//    }
    FRMListado.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
   FRMFiltro.fSetNavStatus("ReposRecord");
   lNuevo=false;
   lGuardando=false;
   if(FRMListado.fGetLength() > 0)
     FRMPanel.fSetTraStatus("UpdateComplete");
   else
     FRMPanel.fSetTraStatus("AddOnly");
   fDisabled(true);
   FRMListado.fSetDisabled(false);
   FRMListado.focus();
}
// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar(){
   if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
      if (FRMListado.length == 1)
         lActualizaListado=true;
      lBorrado=true;
      fNavega();
   }
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato){
   frm.dtIniVigencia.value = aDato[2];
   frm.iNumDiasResol.value = aDato[3];
//   if(aDato[3] != -1){
//      frm.iNumDiasResolV.value = aDato[3];
//      frm.lRequiereDiasBOX.checked = false;
//   }else{
//      frm.iNumDiasResolV.value = "";
//      frm.lRequiereDiasBOX.checked = true;
//   }
//   fAsignaCheckBox(frm.lDiasNaturalesResolBOX,aDato[4]);
   frm.iNumDiasCubrirReq.value = aDato[5];
//   fAsignaCheckBox(frm.lDiasNaturalesReqBOX,aDato[6]);
//   fAsignaCheckBox(frm.lRequierePagoBOX,aDato[7]);
   //fAsignaSelect(frm.iCveFormato,aDato[8],aDato[8]+" - "+aDato[9]);
   fSelectSetIndexFromValue(frm.iCveTramite,aDato[1]);
   //frm.iCveTramiteCIS.value=aDato[12];
   //frm.cNotas.value = aDato[13];
   frm.iDiasDespuesNotif.value = aDato[14];
   frm.lActivo.value = aDato[15];
//   frm.lActivoBOX.checked = frm.lActivo.value == 1 ? true: false;
   frm.lTramInt.value = aDato[16];
//   frm.lTramIntBOX.checked = frm.lTramInt.value==1?true:false;
}
function fSelReg2(aDato){
  if(aDato && aDato[0]){
    frm.iCveModalidadCfg.value = aDato[0];
    frm.iCveModalidad.value = aDato[0];
    frm.cDscModalidadCfg.value = aDato[1];
    frm.iCveTramiteCfg.value  = aDato[2];
    frm.cDscTramiteCfg.value = aDato[3];

    if (frm.iCveTramiteCfg.value != "" && frm.iCveModalidadCfg.value != ""){
      if (!lDelFiltro)
        fActListado(frm.iCveTramiteCfg.value,frm.cDscTramiteCfg.value,
                    frm.iCveModalidadCfg.value,frm.cDscModalidadCfg.value);
    } else
      fNavega();
    lDelFiltro=false;
  }else{
    fResultado(new Array(),"Listado","");
    frm.cDscTramite.value = window.parent.frm.iCveTramite.options[window.parent.frm.iCveTramite.selectedIndex].text;
  }
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){
   cMsg = fValElements("cNotas,");
   //if (frm.iCveFormato.value < 0 ){
   //  cMsg = cMsg + "\n - El Campo Formato de Captura, es Obligatorio, Favor de introducir su valor.";
   //}      // cComprobante como son parentesis
   //if(frm.cNotas.value!="")
   //  if(fEvaluaCampo(frm.cNotas.value)==false)          //evalua los parametros que son aceptados en
   //    cMsg = cMsg+"\n Parametro incorrecto Notas";
   if(cMsg != ""){
      fAlert(cMsg);
      return false;
   }
   return true;
}
function fEvaluaCampo(cVCadena){
   if(cVCadena == "")
      return false;
    if (fRaros2(cVCadena))
         return false;  //Este regresa a la funcion fValidaTodo en donde está la sig instrucción if(fEvaluaCampo(frm.cDscTramite.value)==false)
    else
      return true;
 }

function fRaros2(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"^") ||
       fEncCaract(cVCadena.toUpperCase(),"`") ||
       fEncCaract(cVCadena.toUpperCase(),"´") ||
       fEncCaract(cVCadena.toUpperCase(),"¨") )
       return true;
   else
      return false;
}
function fImprimir(){
   self.focus();
   window.print();
}
function fSetMod(iCveModalidad,cDscMod){
   frm.iCveModalidad.value = iCveModalidad;
   if (frm.iCveTramite.value != "" && frm.iCveModalidad.value != "" )
     fNavega2();
}
function fSetTra(iCveTramite,cDscTra){
   frm.iCveTramite.value = iCveTramite;
   frm.cDscTramite.value = cDscTra;
}
function fSetValores(iCveTramite,cDscTramite,iCveModalidad,cDscModalidad,bandera){
    frm.iCveModalidad.value = iCveModalidad;
    frm.iCveTramite.value = iCveTramite;
    frm.cDscTramite.value = cDscTramite;
    frm.flag1.value = 0;
    lDelFiltro=true;
    if(frm.flag2.value == 1 && bandera == undefined)
      fNavega2();
    else if(frm.flag1.value == 0)
      frm.flag2.value=1;
}
function fActListado(iCveTramite,cDscTramite,iCveModalidad,cDscModalidad){
  if (iCveTramite != "")
    frm.iCveTramite.value = iCveTramite;
  if (iCveModalidad != "")
     frm.iCveModalidad.value = iCveModalidad;
  if (cDscTramite != "")
    frm.cDscTramite.value = cDscTramite;
  fNavega();
}
function fVerifica(){
  if (FRMListado.fGetLength()>0){
    fAbreSubWindowPermisos("pg111010100","900","600");
    fNavega();
  } else alert("Debe de existir algún registro a configurar");
}
function fGetCveTramite(){
 return frm.iCveTramite.value;
}
function fGetCveModalidad(){
 return frm.iCveModalidad.value;
}
function fGetcDscTramite(){
 return frm.cDscTramite.value;
}
function fSetValues(iTramite){
  frm.hdNumReg.value = 10000;
  frm.iCveTramite.value = iTramite;
  frm.hdSelect.value =
      "SELECT ICVEMODALIDAD,CDSCMODALIDAD FROM TRAMODALIDAD Where LVIGENTE = 1 order by CDSCMODALIDAD ";
  frm.hdLlave.value="";
  fEngSubmite("pgConsulta.jsp","cIdModalidad");
}
function fSetValues2(iTramite){
  frm.iCveTramite.value = iTramite;
  fNavega2();
}
function fGetcDscModalidad(){
  return frm.cDscModalidadCfg.value;
}
function fRequiereDias(){
//  if(frm.lRequiereDiasBOX.checked == true){
//     frm.lDiasNaturalesResolBOX.disabled = true;
//     frm.iNumDiasResolV.disabled = true;
//  }else{
//     frm.lDiasNaturalesResolBOX.disabled = false;
//     frm.iNumDiasResolV.disabled = false;
//  }
}

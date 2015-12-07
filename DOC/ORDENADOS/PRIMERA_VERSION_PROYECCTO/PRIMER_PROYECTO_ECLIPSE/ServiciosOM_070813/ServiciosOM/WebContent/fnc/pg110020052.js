// MetaCD=1.0
 // Title: pg110020052.js
 // Description: JS "Proceso" de la entidad MYRRPMN
 // Company: S.C.T.
 // Author: ALopez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aRamo = new Array(), aSeccion = new Array(), aSeccionCveSecc=new Array();
 var aOfic = new Array(), aTipoAsiento = new Array(), aTipoActo=new Array(), aTipoActoCveTA=new Array(), aTodasOfic=new Array();
 var iUser = fGetIdUsrSesion();
 var cFechaActual;
 var lGuardar = false;
 var lEmbarcOEmpresa; //0 Para Embarcaciones , 1 ParaEmpresas
 var iCveCancelaEmb, iCveCancelaEmp;
 var lPasoCan = false;
 var iEjercicioSolListado=0, iNumSolicitudListado=0;
 var lYaEstaReg = false;
 var aVacio = new Array();
 var lCancelacion = false;
 var lUsrficCent = false;
 var lCambiaProp=false;
 var lConsultaEmb=false;
 var lBuscaXNom = false;
 var lBuscaXRFC = false;
 var iTipoActo = 0;
 var lTodosFinalizados=true;
 var lValidaActivo = false;
 var lModificando = false;
 var aTipoAsiento1 = new Array();
 var iCveSeccion1 = -1;
 var lRamo=0;
 var aTipoAsiento = new Array();
 var lBusaEmbEmp = false;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110020052.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center","","","1");
       ITRTD("",0,"","0","center","top");
         IFrame("IFiltro41","0%","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
       ITRTD("",0,"","1","center");
         InicioTabla("",0,"75%","","","",1);
            //ITD("",1,"","","center","center");Liga("Buscar registros de solicitud","fBuscaSolicitudReg();","");
         FinTabla();
       ITRTD("",0,"","1","center");
        InicioTabla("",0,"100%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITRTD("",0,"","1","center");
            InicioTabla("",0,"75%","","","",1);
              ITR();
                TDEtiSelect(true,"EEtiqueta",0,"Ramo:","iCveRamo","fRamoOnChange();","EEtiquetaL",1);//
                TDEtiCampo(true,"EEtiqueta",0,"Folio:","iFolio","",4,4,"","fMayus(this);","","");
                TDEtiSelect(false,"EEtiqueta",0,"Capitania:","iCveCapitania");
                TDEtiCampo(false,"EEtiqueta",0,"Ejercicio:","iEjercicio","",4,4,"","fMayus(this);","","fValidaEjercicio();");
              FITR();
              	TDEtiCampo(true,"EEtiqueta",0,"Empresa / Embarcación:","cFiltroObjeto","",80,80,"ToolTip...","fMayus(this);","","","","",5);

              ITD();
                Liga("Empresa","fBuscaEmpresa();")
              FITD();
                Liga("Embarcación","fBuscaEmbarca();");
              FITD();
              FTD();
            FinTabla();
            ITD();

            InicioTabla("",0,"75%","","","",1);              
              ITD();
                BtnImg("Buscar","lupa","fBuscaFolio();");
              FTD();
            FinTabla();
          FTDTR();
          FinTabla();
        FinTabla();
         FinTabla();
       FTDTR();

       ITRTD("",0,"","80","center","top");
         IFrame("IListado41","90%","150","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",7,"","","center");
             TextoSimple("Registro Público Marítimo Nacional");
           FTDTR();
           ITRTD("",0,"","1","center");
           InicioTabla("",0,"75%","","","",1);
             ITR();
               TDEtiCampo(true,"EEtiqueta",0,"Empresa o Embarcaión:","cNomObjeto","",70,70,"","fMayus(this);","","","","EEtiquetaL",3);
               ITD(); Liga("Cambiar Embarcación/Empresa","fBuscaObjeto();","Busqueda de Embarcaciones/Empresas.");
               FTD();
           FinTabla();
           
           ITRTD("",0,"","1","center");
           InicioTabla("",0,"75%","","center","center",1);

           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Folio RPMN:","iFolioRPMN","",10,10,"FolioRPMN","fMayus(this);","","",true,"");
              TDEtiCampo(true,"EEtiqueta",0,"Partida:","iPartida","",5,5,"Partida","fMayus(this);","","",true,"",4);
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Oficina de Registro:","iCveOficinaReg","fCambiaOfic();");
              TDEtiCampo(false,"EEtiqueta",0,"Titular:","cTitular","",43,43,"Titular","","","",false,"");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Ejercicio Sol. :","iEjercSolReg","",4,4,"Ejercicio de la solicitud registrada","fMayus(this);","","",true,"");
              TDEtiCampo(true,"EEtiqueta",0,"Num. Solicitud:","iNumSolReg","",5,5,"Número de solicitud registrada","fMayus(this);","","",true,"");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Sección:","iCveSeccion","fLlenaTipoActo();","");
              TDEtiSelect(true,"EEtiqueta",0,"Tipo de Acto:","iCveTipoActo","","",4);
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Tipo de Asiento:","iCveTipoAsiento","","",6);//
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Fecha de Ingreso:","dtIngreso","",12,12,"Fecha de Ingreso","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"Fecha de Registro:","dtRegistro","",12,12,"Fecha de Registro","fMayus(this);","","",true,"",4);
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Descripción del documento a inscribir:",80,3,"cDscDocumento","","Descripción del documento a inscribir","","",'onkeydown="fMxTx(this,200);"',false,false,false,"",5);
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Sintesis:",80,10,"cSintesis","","Sintesis","","",'onkeydown="fMxTx(this,4000);"',false,false,true,"",5);
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"% Participación Extranjera:","dPartExtranjera","",4,4,"Porcentaje de Participación Extranjera","fMayus(this);","","",false,"");
              TDEtiCampo(false,"EEtiqueta",0,"Partida Cancelación:","iPartidaCancela","",5,5,"Partida de Cancelación","fMayus(this);");
              TDEtiCheckBox("EEtiqueta",0,"Registro Finalizado:","lRegFinalizadoBOX","",true,"Finaliza el registro de la inscripción","","","",true,true,"",0);
              Hidden("lRegFinalizado","");
           FITR();
             ITD("",6,"","","center");
               Liga("Documentos","fEjecutaDocumentos();","Adjuntar Documentos");
             FTD();
           FTR();
           ITR("","","25","center","bottom");
            ITD("EPieC",6,"","");
             TextoSimple("Los dos siguientes campos deberán ser llenados por el registrador en caso de existir<br>antecendetes registrales previos (folio anterior)");
            FTD();
           FITR();
              TDEtiSelect(false,"EEtiqueta",0,"Oficina de RPMN de <BR>un folio anterior","iCveOficinaFolioAnt","","",3);
              TDEtiCampo(false,"EEtiqueta",0,"Folio RPMN Anterior:","cFolioRPMNAnt","",10,10,"Folio RPMN que la empresa o embarcación tenia asignado","fMayus(this);","","",true,"");
           FTR();
           FinTabla();
         FinTabla();
         InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",6,"","","center");
             TextoSimple("Referencia de Pago");
           FTDTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel41","95%","34","Paneles.js");
       FTDTR();
     FTDTR();
     FinTabla();

     Hidden("dtCancelaFolio","");
     Hidden("hdSelect");
     Hidden("hdLlave");
     Hidden("iCveObsLarga");
     Hidden("iEjercicioObs");
     Hidden("lRegEmbarcacion","");
     Hidden("iCveOficina","");
     Hidden("iEjercicioIns","");
     Hidden("iCveVehiculo");
     Hidden("iCvePersona")
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel41");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fHabilitaReporte(true);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado41");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("RPMN,Partida,Sección,Tipo de Acto,Tipo de Asiento,Embarcacion/Empresa,Finalizado,");
   FRMListado.fSetCampos("17,2,15,28,16,70,30,");
   FRMListado.fSetAlinea("center,left,left,left,left,left,center,");
   FRMListado.fSetDespliega("texto,texto,texto,texto,texto,texto,logico,");
   FRMFiltro = fBuscaFrame("IFiltro41");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow("Reg,Nav,");
   FRMFiltro.fSetFiltra("Clave,iCveOficina,FolioRPMN,iFolioRPMN,");
   FRMFiltro.fSetOrdena("Clave,iCveOficina,FolioRPMN,iFolioRPMN,");
   fDisabled(true,"iCveRamo,iFolio,iCveCapitania,iEjercicio,");
   frm.cSintesis.disabled = false;
   frm.cSintesis.readOnly = true;
   frm.hdBoton.value="Primero";
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
     frm.hdFiltro.value = "";
     frm.hdOrden.value  = "";
     frm.hdNumReg.value = "100000";
     fEngSubmite("pgMYRRPMN.jsp","idGraba");
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,dtFecha){
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

   if(cId == "idFechaActual" && cError==""){
     cFechaActual = aRes[0,0];
     fTraeOficina(true);
   }
   if(cId=="cIdRamo" && cError==""){
     aRamo=fCopiaArregloBidim(aRes);
     fFillSelect(frm.iCveRamo,aRes,false,0,0,1);
     fRamoOnChange();
   }
   if(cId=="cIdSeccion" && cError==""){
     fFillSelect(frm.iCveSeccion,aRes,true,0,0,1);
     fCargaAsiento();
   }
   if(cId=="cIdCapitania" && cError==""){
     fFillSelect(frm.iCveOficinaReg,aRes,true,0,0,1);
     fFillSelect(frm.iCveCapitania,aRes,true,0,0,1);
     fFillSelect(frm.iCveOficinaFolioAnt,aRes,true,0,0,1);
     fGetRamo();
   }
   if(cId=="idGraba" && cError==""){
     fBuscaFolio();
   }
   if(cId=="cIdTipoActo" && cError==""){
     fFillSelect(frm.iCveTipoActo,aRes,true,0,2,3);
     //fCargaAsiento();
   }
   if(cId=="cIdTipoAsiento" && cError==""){
     aTipoAsiento = fCopiaArregloBidim(aRes);
     //fFillSelect(frm.iCveTipoAsiento,aRes,true,0,0,1);
   }
   if(cId == "Listado" && cError==""){
     lBuscaXNom = false;
	 lBusaEmbEmp == false;
     if(aRes.length>0){
       for(var z=0; z<aRes.length; z++){
         aRes[z][70] = aRes[z][3]+"  ("+aRes[z][4]+")<br>Mat: "+aRes[z][35];
         //Concatena el Folio RPMN
         if(aRes[z][1]>999) aRes[z][17] = aRes[z][1];
         else{
           if(aRes[z][1]>99 && aRes[z][1]<1000)aRes[z][17] = "0"+aRes[z][1];
           else{
             if(aRes[z][1]>9 && aRes[z][1]<100)aRes[z][17] = "00"+aRes[z][1];
             else{
               if(aRes[z][1]<10) aRes[z][17] = "000"+aRes[z][1];
             }
           }
         }
         //Concatena las siglas de la oficina
         aRes[z][17] += "-"+aRes[z][24];
         //Concatena dos digitos del año
         if(aRes[z][43]){
           if(parseInt(aRes[z][43],10)==0)aRes[z][17] += "-" + "00";
           else aRes[z][17] += "-" + aRes[z][43].substring(2,4);
         }
         if(aRes[z][30]!=1) lTodosFinalizados = false;
       }
     }
     frm.hdFiltro.value = "";
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     FRMPanel.fSetTraStatus("Add,Mod,");
     fCancelar();
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
    var cMesSel="";
    if(frm.iCveSeccion.value <= 0 || frm.iCveTipoActo.value <= 0 || frm.iCveTipoAsiento.value <= 0)
      cMesSel="Seleccione\n";
    if(frm.iCveSeccion.value <= 0)
      cMesSel+="- Sección\n";
    if(frm.iCveTipoActo.value <= 0)
      cMesSel+="- Tipo de Acto\n";
    if (frm.iCveTipoAsiento.value <= 0)
      cMesSel+="- Tipo de Asiento\n";
    //if( parseInt(frm.dPartExtranjera.value,10) > 49 )
      //cMesSel+="- No se puede inscribir una empresa con participación extranjera del más del 49%\n";
    if(parseInt(frm.dtIngreso.value.substring(6,10),10)<1980)
      cMesSel+="- El ejercicio de la fecha de ingreso no puede ser menor a 1980\n";
    if(cMesSel!=""){
      fAlert(cMesSel);
      return;
    }
    lGuardar = true;
    if(frm.iCveOficinaFolioAnt.value < 0)
      frm.iCveOficinaFolioAnt.value = "0";
    if(frm.lRegFinalizadoBOX.checked) frm.lRegFinalizado.value="1"; else frm.lRegFinalizado.value="0";
    frm.hdBoton.value = "GuardarAdmin";
    if(fValidaTodo()==true){
      frm.cDscDocumento.value = fReemplazar(frm.cDscDocumento.value);
      frm.cSintesis.value = fReemplazar(frm.cSintesis.value);
      fNavega();
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    if (FRMListado.fGetLength()<=0){
      fAlert("\nNo hay partidas a modificar.");
      return false;
    }
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(true);
    fDisabled(false,"iFolioRPMN,iPartida,cNomObjeto,iCveRamo,iFolio,iCveCapitania,iEjercicio,");
    frm.cSintesis.readOnly = false;
    FRMListado.fSetDisabled(true);
    fFillSelect(frm.iCveTipoAsiento,aTipoAsiento,false,0,0,1);
    lModificando=true;
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
      if(FRMListado.fGetLength() > 0)
        FRMPanel.fSetTraStatus("Mod,");
      else
        FRMPanel.fSetTraStatus("");
    FRMListado.fSetDisabled(false,false);
    fDisabled(false);
    fDisabled(true,"iCveRamo,iFolio,iCveCapitania,iEjercicio,");
    frm.cSintesis.disabled = false;
    frm.cSintesis.readOnly = true;
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
   frm.iEjercSolReg.value = aDato[44];
   frm.iNumSolReg.value = aDato[45];
   frm.iCveOficinaReg.value = aDato[63];
   frm.iCveOficina.value = aDato[67];
   frm.cTitular.value = aDato[20];
   fSelectSetIndexFromValue(frm.iCveSeccion,aDato[22]);
   fAsignaSelect(frm.iCveTipoActo,aDato[27],aDato[28]);
   fAsignaSelect(frm.iCveTipoAsiento,aDato[18],aDato[16]);
   //frm.iCveTipoActo.value = aDato[];
   frm.iFolioRPMN.value = aDato[1];
   frm.iPartida.value = aDato[2];
   frm.dtIngreso.value=aDato[29];
   frm.dtRegistro.value=aDato[26];

   frm.lRegEmbarcacion.value = aRamo[frm.iCveRamo.value-1][4]; //abm
   frm.cDscDocumento.value=aDato[36];
   frm.cSintesis.value=aDato[53];
   frm.cNomObjeto.value = aDato[3];
   
   
   if(aDato[23]!="" && aDato[23]!="0")
     frm.iPartidaCancela.value = aDato[23];
   else
     frm.iPartidaCancela.value = "";
   frm.lRegFinalizado.value=aDato[30];
   if(frm.lRegFinalizado.value=="1")
     frm.lRegFinalizadoBOX.checked = true;
   else
     frm.lRegFinalizadoBOX.checked = false;
   
   if (frm.lRegEmbarcacion.value == "0"){
	   if (parseInt(aDato[31],10)>0)
		   frm.dPartExtranjera.value = aDato[31];
	   else
	       frm.dPartExtranjera.value = "";
   }else
	   frm.dPartExtranjera.value = "";
   frm.iEjercicioObs.value = aDato[51];
   frm.iCveObsLarga.value = aDato[52];
   frm.iEjercicioIns.value = aDato[68];
 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
   frm.cDscDocumento.value = fReemplazar(frm.cDscDocumento.value);
   frm.cSintesis.value = fReemplazar(frm.cSintesis.value);
    cMsg = fValElements("cDscDocumento,cSintesis,cEmbarcacion,");
    if(frm.iNumSolReg.value == "" && frm.iEjercicio.value == "" && (!frm.iCveOficinaFolioAnt.value > 0 || frm.cFolioRPMNAnt.value ==""))cMsg+="\n - En el caso de historicos son obligatorios los campos de \n"+
                                                                                                                                                 "   Oficina de RPMN de un folio anterior y Folio RPMN Anterior"    ;
    if(!fEvaluaCampo(frm.cDscDocumento.value))cMsg+="\n - El campo de descripción del documento tiene caracteres no validos";
    if(!fEvaluaCampo(frm.cSintesis.value))cMsg+="\n - El campo de plantilla tiene caracteres no validos";
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
function fInicia(){
  frm.hdBoton.value="Primero";
  frm.hdNumReg.value = 10000;
  frm.hdSelect.value = "SELECT iCveOficina,cDscOficina FROM GRLOFICINA order by cDscOficina";
  frm.hdLlave.value  = "";
  fEngSubmite("pgConsulta.jsp","cIdCapitania");
}
function fGetRamo(){
  frm.hdNumReg.value = 10000;
  frm.hdFiltro.value = "";
  frm.hdOrden.value  = "";
  fEngSubmite("pgMYRRamo.jsp","cIdRamo");
}
function fRamoOnChange(){
  var iRamo = 0;
  for(i=0;i<aRamo.length;i++){
    if(aRamo[i][0]==frm.iCveRamo.value){
      if(aRamo[i][3]==1){lRamo=1;i=aRamo.length;break}//Empresa
      if(aRamo[i][4]==1){lRamo=2;i=aRamo.length;break}//Embarcacion      
    }
  }
  iRamo = lRamo==1?2:1;
  frm.hdSelect.value = "SELECT ICVESECCION,CDSCSECCION FROM MYRSeccion Where iCveRamo = "+iRamo+" order by CDSCSECCION";
  frm.hdLlave.value  = "";
  fEngSubmite("pgConsulta.jsp","cIdSeccion");
}
function fValidaEjercicio(){
  var lValido=true;
  if(!fSoloNumeros(frm.iEjercicio.value)) lValido=false;
  if(frm.iEjercicio.value.length!=4) lValido=false;
  if(lValido==false){
    fAlert("El formato del ejercicio no corresponde.");
    frm.iEjercicio.value = "";
  }
}
function fBuscaFolio(){//iCveRamo,iFolio,iCveCapitania,iEjercicio,
  if(parseInt(frm.iCveRamo.value)>0){
    if(parseInt(lRamo)==1){//Empresa
      frm.hdFiltro.value = "";
      frm.hdNumReg.value = 10000;
      frm.hdBoton.value  = "";
      frm.hdOrden.value  = "MYRRPMN.iEjercicioIns,MYRRPMN.iCveOficina,MYRRPMN.iFolioRPMN";
      if(frm.iEjercicio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iEjercicioIns="+frm.iEjercicio.value:frm.hdFiltro.value+=" AND MYRRPMN.iEjercicioIns="+frm.iEjercicio.value;
      if(frm.iCveCapitania.value>0)frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iCveOficina="+frm.iCveCapitania.value:frm.hdFiltro.value+=" AND MYRRPMN.iCveOficina="+frm.iCveCapitania.value;
      if(frm.iFolio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.IFOLIORPMN="+frm.iFolio.value:frm.hdFiltro.value+=" AND MYRRPMN.IFOLIORPMN="+frm.iFolio.value;
      fEngSubmite("pgMYREmpresa.jsp","Listado");
    }
    if(parseInt(lRamo)==2){//Embarcacion
      frm.hdFiltro.value = "";
      frm.hdNumReg.value = 10000;
      frm.hdBoton.value  = "";
      frm.hdOrden.value  = "MYRRPMN.iEjercicioIns,MYRRPMN.iCveOficina,MYRRPMN.iFolioRPMN";
      if(frm.iEjercicio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iEjercicioIns="+frm.iEjercicio.value:frm.hdFiltro.value+=" AND MYRRPMN.iEjercicioIns="+frm.iEjercicio.value;
      if(frm.iCveCapitania.value>0)frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iCveOficina="+frm.iCveCapitania.value:frm.hdFiltro.value+=" AND MYRRPMN.iCveOficina="+frm.iCveCapitania.value;
      if(frm.iFolio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.IFOLIORPMN="+frm.iFolio.value:frm.hdFiltro.value+=" AND MYRRPMN.IFOLIORPMN="+frm.iFolio.value;
      fEngSubmite("pgMYREmbarcacion.jsp","Listado");
    }
  }else fAlert("Es necesario seleccionar un Ramo para la Busqueda.");
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
    if (fEncCaract(cVCadena.toUpperCase(),"º")||
       fEncCaract(cVCadena.toUpperCase(),"ª") ||
       fEncCaract(cVCadena.toUpperCase(),"%") ||
       fEncCaract(cVCadena.toUpperCase(),"ç") ||
       fEncCaract(cVCadena.toUpperCase(),"|") ||
       fEncCaract(cVCadena.toUpperCase(),"{") ||
       fEncCaract(cVCadena.toUpperCase(),"}") ||
       fEncCaract(cVCadena.toUpperCase(),"<") ||
       fEncCaract(cVCadena.toUpperCase(),">") ||
       fEncCaract(cVCadena.toUpperCase(),"~") ||
       fEncCaract(cVCadena.toUpperCase(),"æ")||
       fEncCaract(cVCadena.toUpperCase(),"Æ") ||
       fEncCaract(cVCadena.toUpperCase(),"¢") ||
       fEncCaract(cVCadena.toUpperCase(),"£") ||
       fEncCaract(cVCadena.toUpperCase(),"¥") ||
       fEncCaract(cVCadena.toUpperCase(),"ƒ") ||
       fEncCaract(cVCadena.toUpperCase(),"«") ||
       fEncCaract(cVCadena.toUpperCase(),"»") ||
       fEncCaract(cVCadena.toUpperCase(),"^") ||
       fEncCaract(cVCadena.toUpperCase(),"`") ||
       fEncCaract(cVCadena.toUpperCase(),"Ç") ||
       fEncCaract(cVCadena.toUpperCase(),"´") ||
       fEncCaract(cVCadena.toUpperCase(),"¨") ||
       fEncCaract(cVCadena.toUpperCase(),String.fromCharCode(39)) ||
       fEncCaract(cVCadena.toUpperCase(),String.fromCharCode(34)) )
        return true;
    else
       return false;
 }
 function fLlenaTipoActo(){
   frm.hdFiltro.value = "MYRTipoActo.iCveRamo="+frm.iCveRamo.value+" AND MYRTipoActo.iCveSeccion="+frm.iCveSeccion.value;
   frm.hdOrden.value  = "";
   frm.hdLlave.value  = "";
   fEngSubmite("pgMYRTipoActo.jsp","cIdTipoActo");
 }
 function fCargaAsiento(){
   frm.hdFiltro.value = "";
   frm.hdOrden.value  = "";
   frm.hdLlave.value  = "";
   fEngSubmite("pgMYRTipoAsiento.jsp","cIdTipoAsiento");
 }


 function fBuscaObjeto(){
     if(parseInt(frm.iCveRamo.value)>0){
	if(parseInt(lRamo)==1){//Empresa
	    fAbreSolicitante();
	}
	if(parseInt(lRamo)==2){//Embarcacion
	    fAbreBuscaEmbarcacionGral();
	}
     }     
 }
 
 
 function fValoresEmbarcacion(iCveVehiculo,cNomEmbarcacion){
     if(lBusaEmbEmp == true){
	 frm.cFiltroObjeto.value = "Embarcación: " +cNomEmbarcacion + " ("+iCveVehiculo+")";
	 frm.hdFiltro.value = " MYREmbarcacion.iCveVehiculo = "+iCveVehiculo;
	 fEngSubmite("pgMYREmbarcacion.jsp","Listado");
     }
     else{
         frm.iCveVehiculo.value = iCveVehiculo;
         frm.cNomObjeto.value = cNomEmbarcacion;
         frm.hdFiltro.value = "";
         frm.hdNumReg.value = 10000;
         frm.hdOrden.value  = "MYRRPMN.iEjercicioIns,MYRRPMN.iCveOficina,MYRRPMN.iFolioRPMN";
         if(frm.iEjercicio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iEjercicioIns="+frm.iEjercicio.value:frm.hdFiltro.value+=" AND MYRRPMN.iEjercicioIns="+frm.iEjercicio.value;
         if(frm.iCveCapitania.value>0)frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iCveOficina="+frm.iCveCapitania.value:frm.hdFiltro.value+=" AND MYRRPMN.iCveOficina="+frm.iCveCapitania.value;
         if(frm.iFolio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.IFOLIORPMN="+frm.iFolio.value:frm.hdFiltro.value+=" AND MYRRPMN.IFOLIORPMN="+frm.iFolio.value;
         frm.hdBoton.value = "ActEmbarcacion";
         fEngSubmite("pgMYREmbarcacion.jsp","Listado");
     }
 }
 
 
 function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial){
     if(lBusaEmbEmp == true){
	 frm.cFiltroObjeto.value = "Empresa: " +cNomRazonSocial;
	 frm.hdFiltro.value = " MYREmpresa.iCvePersona = "+iCvePersona;
	 fEngSubmite("pgMYREmpresa.jsp","Listado");
     }
     else{
	 alert(2);
         frm.iCvePersona.value = iCvePersona;
         frm.cNomObjeto.value = cRFC;
         frm.hdFiltro.value = "";
         frm.hdNumReg.value = 10000;
         frm.hdBoton.value  = "ActEmpresa";
         frm.hdOrden.value  = "MYRRPMN.iEjercicioIns,MYRRPMN.iCveOficina,MYRRPMN.iFolioRPMN";
         if(frm.iEjercicio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iEjercicioIns="+frm.iEjercicio.value:frm.hdFiltro.value+=" AND MYRRPMN.iEjercicioIns="+frm.iEjercicio.value;
         if(frm.iCveCapitania.value>0)frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.iCveOficina="+frm.iCveCapitania.value:frm.hdFiltro.value+=" AND MYRRPMN.iCveOficina="+frm.iCveCapitania.value;
         if(frm.iFolio.value!="")frm.hdFiltro.value==""?frm.hdFiltro.value="MYRRPMN.IFOLIORPMN="+frm.iFolio.value:frm.hdFiltro.value+=" AND MYRRPMN.IFOLIORPMN="+frm.iFolio.value;
         fEngSubmite("pgMYREmpresa.jsp","Listado");
     }
 }
 
 function fBuscaEmpresa(){
     lBusaEmbEmp = true;
     fAbreSolicitante();
 }
 
 function fBuscaEmbarca(){
     lBusaEmbEmp = true;
     fAbreBuscaEmbarcacion();
 }
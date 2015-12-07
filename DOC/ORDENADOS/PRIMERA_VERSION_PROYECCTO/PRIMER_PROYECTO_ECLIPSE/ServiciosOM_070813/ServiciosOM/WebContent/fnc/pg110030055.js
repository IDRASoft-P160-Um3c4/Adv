// MetaCD=1.0
 // Title: pg119420025.js
 // Description: JS "Catálogo" de la entidad NAVArribos
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Melina Aline Rodriguez Angeles

 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aResNavArribo = "";
 var aResNavZarpe = "";
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110030055.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     //ITRTD("ESTitulo",0,"100%","","center");
      // TextoSimple(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       //ITRTD("",0,"","40","center","top");
         //IFrame("IFiltro","95%","34","Filtros.js");
       //FTDTR();
       ITRTD("",0,"","55","center","top");
         IFrame("IListado","95%","50","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
        InicioTabla("ETablaInfo",0,"75%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple(cTitulo);
           FTDTR();
           ITR();
           FITR();
              //TDEtiCampo(true,"EEtiqueta",0,"Ejercicio:","iEjercicio","",4,4,"Ejercicio","fMayus(this);");
              Hidden("iEjercicio");
              //TDEtiCampo(true,"EEtiqueta",0,"iNumArribo:","iNumArribo","",3,3,"iNumArribo","fMayus(this);");
              Hidden("iNumArribo");
              FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Ejercicio Solicitud:","iEjercicioSol","",3,3,"EjercicioSol","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"Núm. Solicitud:","iNumSolicitud","",8,8,"Núm. Solicitud","fMayus(this);");
           FITR();
              //TDEtiCampo(true,"EEtiqueta",0,"Embarcación:","iCveEmbarcacion","",8,8,"Embarcación","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Embarcación:","iCveEmbarcacion","");
              //TDEtiCampo(true,"EEtiqueta",0,"Puerto de Procedencia:","iCvePuertoProced","",8,8,"Puerto de Procedencia","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Puerto de Procedencia:","iCvePuertoProced","");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Fecha Arribo Programado:","dtArriboProgramado","",10,10,"Fecha Arribo Prev","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"Hora Arribo Programado:","hArriboProgramado","",5,5,"Hora Arribo Prev","fMayus(this);");
              Hidden("tsArriboProgramado");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Fecha Zarpe Programado:","dtZarpeProgramado","",10,10,"Fecha Arribo Prev","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"Hora Zarpe Programado:","hZarpeProgramado","",5,5,"Hora Arribo Prev","fMayus(this);");
              Hidden("tsZarpeProgramado");
           FITR();
              //TDEtiCampo(true,"EEtiqueta",0,"Puerto Destino:","iCvePuertoDestino","",8,8,"Puerto Destino","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Puerto Destino:","iCvePuertoDestino","");
              TDEtiCampo(true,"EEtiqueta",0,"Dias en Puerto:","iDiasEnPuerto","",3,3,"DiasEnPuerto","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Fecha Fondeo:","dtFondeo","",10,10,"Fecha Arribo Prev","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"Hora Fondeo:","hFondeo","",5,5,"Hora Arribo Prev","fMayus(this);");
              Hidden("tsFondeo");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Puertos Operacion:","iPuertosOperacion","",3,3,"PuertosOperacion","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Solicitud de Remolcador:","dtSolicRemolcador","",10,10,"Fecha Arribo Prev","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Hora Solicitud de Remolcador:","hSolicRemolcador","",5,5,"Hora Arribo Prev","fMayus(this);");
              Hidden("tsSolicRemolcador");
           FITR();
               //TDEtiCampo(false,"EEtiqueta",0,"Clave de la Oficina:","iCveOficina","",5,5,"Clave de la Oficina","fMayus(this);");
               TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","");
           FITR();
              TDEtiAreaTexto(true,"EEtiqueta",0,"Ultimos Puertos:",80,2,"cUltimosPuertos","","UltimosPuertos","","fMayus(this);",'onkeydown="fMxTx(this,200);"',"","","","EEtiquetaL","5");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Capitan de la Embarcación:","cCapitan","",80,100,"Capitan","fMayus(this);","","","","EEtiquetaL","5");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Numero de Tripulantes:","iNumTripulantes","",5,5,"NumeroTripulantes","fMayus(this);");
             // TDEtiCampo(true,"EEtiqueta",0,"Causa de Arribo:","iCveCausaArribo","",5,5,"Causa de Arribo","fMayus(this);");
              TDEtiSelect(true,"EEtiqueta",0,"Causa de Arribo:","iCveCausaArribo","");
           FITR();
            ITRTD("ETablaST",5,"","","center");
             TextoSimple("DATOS MANIFESTADOS");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Pasajeros Embarcados:","iNumPasajerosEmb","",3,3,"Pasajeros Embarcados");
              TDEtiCampo(false,"EEtiqueta",0,"Pasajeros Transito:","iNumPasajerosTransito","",5,5,"Pasajeros Transito");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Contenedores Imp:","iContenedoresImp","",5,5,"ContenedoresImp","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Contenedores Exp:","iContenedoresExp","",5,5,"ContenedoresExp","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Carga Embarcada:","iNumTonCargaEmb","",5,5,"Carga Embarcada","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Carga Transito:","iNumTonCargaTransito","",5,5,"Carga Transito","fMayus(this);");
           FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Calado de Popa:","dCaladoPopa","",8,8,"CaladoPopa","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Calado de Proa:","dCaladoProa","",8,8,"CaladoProa","fMayus(this);");
           FITR();
              //TDEtiCampo(false,"EEtiqueta",0,"Unidad de Medida del Calado:","iCveUnidadMedidaCalado","",8,8,"Unidad de Medida del Calado","fMayus(this);");
              TDEtiSelect(false,"EEtiqueta",0,"Unidad de Medida del Calado:","iCveUnidadMedidaCalado","");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Fecha de Arribo Real:","dtArriboReal","",10,10,"Fecha de Arribo Real","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Hora de Arribo:","hArriboReal","",5,5,"Hora Arribo","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Fecha de Zarpe Real:","dtZarpeReal","",10,10,"Fecha de Zarpe Real","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Hora de Zarpe:","hZarpeReal","",5,5,"Hora de Zarpe","fMayus(this);");
           FITR();
              TDEtiSelect(false,"EEtiqueta",0,"Tipo Navegacion Arribo:","iCveTipoNavArribo","");
              TDEtiSelect(false,"EEtiqueta",0,"Tipo Navegacion Zarpe:","iCveTipoNavZarpe","");
           FTR();
         FinTabla();
       FTDTR();
       FinTabla();
       Hidden("tsArriboReal");
       Hidden("tsZarpeReal");

       Hidden("hdLlave","");
       Hidden("hdSelect","");
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,Mod");//Tra
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Ejercicio,Numero Arribo,Ejercicio Sol,Núm. Solicitud,Embarcacion,Puerto Procedencia,Puerto Destino,");
   FRMListado.fSetCampos("0,1,2,3,29,30,31,");
   FRMListado.fSetAlinea("center,center,center,,center,left,left,left,");
   //FRMFiltro = fBuscaFrame("IFiltro");
   //FRMFiltro.fSetControl(self);
   //FRMFiltro.fShow();
   //FRMFiltro.fSetFiltra("NumeroArribo,iNumArribo,");
   //FRMFiltro.fSetOrdena("NumeroArribo,iNumArribo,");
   fDisabled(true);
   frm.hdBoton.value="Primero";
   fNavega();
//   fCargaListado();

 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
    frm.hdFiltro.value = "";
   // alert("frm.iEjercicio.value" + frm.iEjercicio.value);
   // alert("frm.iNumArribo.value"+frm.iNumArribo.value);
   if(top.opener){
     if(top.opener.fGetArriboE() > 0)
       frm.hdFiltro.value = "NAVArribos.iCveEmbarcacion = " + top.opener.fGetArriboE();
   }
   else{
     if(frm.hdFiltro.value == "" || frm.hdFiltro.value == null){
       if((frm.iEjercicio.value != "" && frm.iEjercicio.value != null && frm.iNumArribo.value!=  "" && frm.iNumArribo.value != null)){       //&&(frm.iNumArribo.value != "" && frm.iNumArribo.value != null)
          frm.hdFiltro.value = "NAVARRIBOS.iEjercicio = " + frm.iEjercicio.value + " and NAVARRIBOS.iNumArribo = " + frm.iNumArribo.value;
       }
     }
   }
  // frm.hdFiltro.value =  FRMFiltro.fGetFiltro();
  // frm.hdOrden.value =  FRMFiltro.fGetOrden();
   //frm.hdNumReg.value =  FRMFiltro.fGetNumReg();
   return fEngSubmite("pgNAVArribos2.jsp","Listado");
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
     //FRMFiltro.fSetNavStatus("Record");

   if(cId == "TipoNavArribo" && cError==""){
       aResNavArribo = fCopiaArreglo(aRes);
       fFillSelect(frm.iCveTipoNavArribo,aResNavArribo,false,frm.iCveTipoNavArribo.value,0,1);

   }

    if(cId == "TipoNavZarpe" && cError==""){
      aResNavZarpe = fCopiaArreglo(aRes);
       fFillSelect(frm.iCveTipoNavZarpe,aResNavZarpe,false,frm.iCveTipoNavZarpe.value,0,1);
   }
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     //FRMFiltro.fSetNavStatus(cNavStatus);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iNumArribo,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
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
    //fFillSelect(frm.iCveTipoNavArribo,aResNavArribo,false,frm.iCveTipoNavArribo.value,0,1);
   // fFillSelect(frm.iCveTipoNavZarpe,aResNavZarpe,false,frm.iCveTipoNavZarpe.value,0,1);
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false);
    fDisabled(true,"dtZarpeReal,hZarpeReal,dtArriboReal,hArriboReal,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    //fFillSelect(frm.iCveTipoNavArribo,aResNavArribo,false,frm.iCveTipoNavArribo.value,0,1);
   // fFillSelect(frm.iCveTipoNavZarpe,aResNavZarpe,false,frm.iCveTipoNavZarpe.value,0,1);
    //FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Mod,");
    else
      FRMPanel.fSetTraStatus("Mod,");
    fDisabled(true);
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
    frm.iEjercicioSol.value = aDato[2];
    frm.iNumSolicitud.value = aDato[3];
    //frm.iCveEmbarcacion.value = aDato[4];//***
    fAsignaSelect(frm.iCveEmbarcacion,aDato[4],aDato[29]);
    //frm.iCvePuertoProced.value = aDato[5];//***
    fAsignaSelect(frm.iCvePuertoProced,aDato[5],aDato[30]);
    fAsignaTimeStamp(frm.dtArriboProgramado,frm.hArriboProgramado,aDato[6]);   //***
    fAsignaTimeStamp(frm.dtZarpeProgramado,frm.hZarpeProgramado,aDato[7]);//***
    //frm.iCvePuertoDestino.value = aDato[8]; //***
    fAsignaSelect(frm.iCvePuertoDestino,aDato[8],aDato[31]);
    frm.iDiasEnPuerto.value = aDato[9]; //***
    fAsignaTimeStamp(frm.dtFondeo,frm.hFondeo,aDato[10]);//***
    frm.iPuertosOperacion.value = aDato[11];//***
    fAsignaTimeStamp(frm.dtSolicRemolcador,frm.hSolicRemolcador,aDato[12]);//***
    frm.cUltimosPuertos.value = aDato[13];           //***
    //frm.iCveOficina.value = aDato[14];          //***
    if (aDato[14]!="")fAsignaSelect(frm.iCveOficina,aDato[14],aDato[32]);
    frm.cCapitan.value = aDato[15];        //***
    frm.iNumTripulantes.value = aDato[16]; //***
    //frm.iCveCausaArribo.value = aDato[17];   //***
    fAsignaSelect(frm.iCveCausaArribo,aDato[17],aDato[33]);
    frm.iContenedoresImp.value = aDato[18];  //***
    frm.iContenedoresExp.value = aDato[19];  //***
    frm.dCaladoPopa.value = aDato[20];    //***
    frm.dCaladoProa.value = aDato[21];    //***
    //frm.iCveUnidadMedidaCalado.value = aDato[22];  //***
    fAsignaSelect(frm.iCveUnidadMedidaCalado,aDato[22],aDato[34]);
    fAsignaTimeStamp(frm.dtArriboReal,frm.hArriboReal,aDato[23]); //***
    fAsignaTimeStamp(frm.dtZarpeReal,frm.hZarpeReal,aDato[24]);  //***
    frm.iNumPasajerosEmb.value=aDato[25]; //***
    frm.iNumPasajerosTransito.value=aDato[26]; //***
    frm.iNumTonCargaEmb.value=aDato[27];        //***
    frm.iNumTonCargaTransito.value=aDato[28];    //***
    fAsignaSelect(frm.iCveTipoNavArribo,aDato[35],aDato[37]);
    fAsignaSelect(frm.iCveTipoNavZarpe,aDato[36],aDato[38]);
 }
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    cMsg = fValElements();
    cMsg += fFormatTimeStamp(frm.dtArriboProgramado.value,frm.hArriboProgramado.value,frm.tsArriboProgramado," ArriboProgramado ");
    cMsg += fFormatTimeStamp(frm.dtZarpeProgramado.value,frm.hZarpeProgramado.value,frm.tsZarpeProgramado," ZarpeProgramado ");
    cMsg += fFormatTimeStamp(frm.dtFondeo.value,frm.hFondeo.value,frm.tsFondeo," Fondeo ");
    cMsg += fFormatTimeStamp(frm.dtSolicRemolcador.value,frm.hSolicRemolcador.value,frm.tsSolicRemolcador," SolicRemolcador ");
    cMsg += fFormatTimeStamp(frm.dtArriboReal.value,frm.hArriboReal.value,frm.tsArriboReal," FechadeArriboReal ");
    cMsg += fFormatTimeStamp(frm.dtZarpeReal.value,frm.hZarpeReal.value,frm.tsZarpeReal," FechadeZarpeReal ");


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

 function fSetDatos(ejercicio,numArriboo){
 frm.iNumArribo.value =  "";
 frm.iEjercicio.value = ejercicio;
 frm.iNumArribo.value = numArriboo;
 }

function fCargaListado(){
   frm.hdLlave.value = "iCveTipoNavArribo";
   frm.hdSelect.value = "SELECT ICVETIPONAVEGA,CDSCTIPONAVEGA FROM VEHTipoNavegacion ORDER BY CDSCTIPONAVEGA ";
   frm.hdNumReg.value = 1000;
   fEngSubmite("pgConsulta.jsp","TipoNavArribo");
   fCargaListadoDos();
 }

 function fCargaListadoDos(){
   frm.hdLlave.value = "iCveTipoNavZarpe";
   frm.hdSelect.value = "SELECT ICVETIPONAVEGA,CDSCTIPONAVEGA FROM VEHTipoNavegacion ORDER BY CDSCTIPONAVEGA ";
   frm.hdNumReg.value = 1000;
   fEngSubmite("pgConsulta.jsp","TipoNavZarpe");
 }

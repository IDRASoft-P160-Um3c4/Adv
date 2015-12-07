// MetaCD=1.0
 // Title: pg111040012.js
 // Description: JS "Catalogo" de la entidad TRARegSolicitud
 // Company: Tecnologia InRed S.A. de C.V.
 // Author: IJimenez
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040012.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","50","","","1");  //1
     ITRTD("ESTitulo",0,"100%","","center");
       //TextoSimple(cTitulo);
       TextoSimple("");
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");      //2
       ITRTD("",0,"","0","center","top");
         IFrame("IListadoSol","0","0","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"80%","","","",1);//3
           ITRTD("ETablaST",7,"","","center");
             TextoSimple("TRÁMITE");
           ITRTD();
           FTDTR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",0," Ejercicio :","iEjercicio","",4,4," Ejercicio ","fMayus(this);");
//           FITR();
//               ITD();
                 TDEtiCampo(false,"EEtiqueta",0," Número de Solicitud :","iNumSolicitud","",4,4," Solicitud ","fMayus(this);");
//               FITD();
          FITR();
          ITR();
              TDEtiCampo(false,"EEtiqueta",0," Recibida en :","cOficina","",70,70," Oficina ","fMayus(this);");
              /*ITD("",0,"","","Right","Center");
                 BtnImg("Buscar","lupa","fValidaCampos();");
              FTD();*/
           FTR();

           ITR();
              TDEtiCampo(false,"EEtiqueta",0," Trámite :","cTramite","",100,100," Trámite ","fMayus(this);","","","","EEtiquetaL", "5");
           FITR();
//           ITR();
              TDEtiCampo(false,"EEtiqueta",0," Modalidad :","cModalidad","",70,70," Modalidad ","fMayus(this);","","","", "EEtiquetaL", "2");
           FTR();
         FinTabla();                                           //1
         InicioTabla("ETablaInfo",0,"80%","","","",1);   //4

           //ITRTD("ETablaST",5,"","","center");
            // TextoSimple("DATOS DEL SOLICITANTE");
           FTDTR();

           ITR();
              TDEtiCampo(false,"EEtiqueta",2," Fecha Impresión Acuse de Recibo:","cFechaImpresion","",15,15," RFC ","fMayus(this);", "", "", "","EEtiquetaL","0");
           FITR();
           ITR();
              TDEtiCampo(false,"EEtiqueta",2," Fecha y Hora de Recepción:","cHoraRecepcion","",20,20," RFC ","fMayus(this);", "", "", "","EEtiquetaL","0");
              TDEtiCampo(false,"EEtiqueta",2," Usuario que recibe","cUsuarioRecibe","",50,50," RFC ","fMayus(this);", "", "", "","EEtiquetaL","0");
           FITR();
//              TDEtiCampo(false,"EEtiqueta",2," Fecha y Hora de Entrega:","cHoraEntrega","",15,15," RFC ","fMayus(this);", "", "", "","EEtiquetaL","0");         
//              TDEtiCampo(false,"EEtiqueta",2," Usuario que entrega:","cUsuarioEntrega","",50,50," RFC ","fMayus(this);");
           Hidden("cHoraEntrega","");
           Hidden("cUsuarioEntrega","");
//           FITR();
              TDEtiCampo(false,"EEtiqueta",2," Fecha estimada de entrega:","cFechaEntrega","",15,15," RFC ","fMayus(this);", "", "", "","EEtiquetaL","0");
              TDEtiCheckBox("EEtiqueta",2,"Resolución positiva: ","lPositivaBOX","1",true,"Cumple con los Requisitos");
//              TDEtiCampo(false,"EEtiqueta",2," Fecha límite de entrega de documentos:","cFechaLimite","",15,15," RFC ","fMayus(this);", "", "", "","EEtiquetaL","0");
              Hidden("cFechaLimite","");
           FITR();
             // TDEtiCheckBox("EEtiqueta",2,"Pagado: ","lPagadoBOX","1",true,"Cumple con los Requisitos");
              Hidden("lPagadoBOX");
              
           FTR();

         FinTabla();                                              //2
         InicioTabla("ETablaInfo",0,"80%","","","",1);      //5
           //ITRTD("ETablaST",5,"","","center");
             //TextoSimple("DATOS DEL SOLICITANTE");
           FTDTR();

           ITR();
              TDEtiCampo(false,"EEtiqueta",2," Promovente:","cSolicitante","",50,50," RFC ","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",2," R.F.C.:","cRFCSolicitante","",51,50," RFC ","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",2," Domicilio:","cDomicilioSolicitante","",111,111," Correo Electrónico ","fMayus(this);", "", "", "","EEtiquetaL","4");
           FITR();
              TDEtiCampo(false,"EEtiqueta",2," Representante Legal:","cRepLegal","",50,50," RFC ","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",2," R.F.C.:","cRFCRepLegal","",51,50," RFC ","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",2," Domicilio:","cDomicilioRepLegal","",111,111," Nombre o Razón Social ","fMayus(this);","","","","EEtiquetaL","4");
           FTR();
         FinTabla();                                                 //3
       FinTabla();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel12","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   fDisabled(true);
   FRMPanel = fBuscaFrame("IPanel12");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow(",");
 }

 function fNavega(){
   //frm.hdFiltro.value = FRMFiltro.fGetFiltro();

   frm.hdFiltro.value =  " TRARegSolicitud.iEjercicio = " + frm.iEjercicio.value +
                         " AND TRARegSolicitud.iNumSolicitud = " + frm.iNumSolicitud.value + " " ;

   fEngSubmite("pgTRARegSolicitud2C.jsp","DatosGenerales");
 }

 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if( cId == "DatosGenerales" && cError == "" ) {

        if(aRes.length >0){
        for (i=0;i<aRes.length;i++){
        aRes[i][32]= aRes[i][1] + " " + aRes[i][2]+ " " + aRes[i][3];
        aRes[i][33]= aRes[i][5] + " " + aRes[i][6]+ " " + aRes[i][7];
        aRes[i][16]= aRes[i][16] + " " + aRes[i][17]+ ", Int." + aRes[i][18]+
        ", " + aRes[i][19]+ ", " + aRes[i][21]+ " " + aRes[i][20];
        /* Acabar de corregir los numeros de los arreglos por que cambie el query*/
        aRes[i][22]= aRes[i][22] + " " + aRes[i][23] + " " + aRes[i][24];
        aRes[i][26]= aRes[i][26] + " " + aRes[i][27]+ ", Int." + aRes[i][28]+
        ", " + aRes[i][29]+ ", " + aRes[i][31]+ " " + aRes[i][30];

        }
       /*frm.iEjercicio.value = 2005;
       frm.iNumSolicitud.value = 6;*/
//       frm.cOficina.value =   aRes [0][0];
//       frm.cTramite.value =   aRes [0][1];
//       frm.cModalidad.value =   aRes [0][2];
       frm.cFechaImpresion.value = aRes [0][35];
       frm.cHoraRecepcion.value = aRes [0][0];
       frm.cUsuarioRecibe.value = aRes [0][32];
       frm.cHoraEntrega.value = aRes [0][4];
       frm.cUsuarioEntrega.value = aRes [0][5];
       frm.cFechaEntrega.value = aRes [0][8];
       frm.cFechaLimite.value = aRes [0][9];
       frm.lPagadoBOX.value = aRes [0][10];
        if(frm.lPagadoBOX.value == 1)
          frm.lPagadoBOX.checked=true;
       else
          frm.lPagadoBOX.checked=false;

       frm.lPositivaBOX.value = aRes [0][11];
       if(frm.lPositivaBOX.value == 1)
          frm.lPositivaBOX.checked=true;
       else
          frm.lPositivaBOX.checked=false;

        frm.cSolicitante.value = aRes [0][12]+" "+aRes[0][13]+" "+aRes[0][14];
        frm.cRFCSolicitante.value = aRes [0][15];
        frm.cDomicilioSolicitante.value = aRes [0][16];

        if (aRes[0][29] != null && aRes[0][29]!="")
            frm.cRepLegal.value = aRes [0][22];
        else
            frm.cRepLegal.value = "No aplica";

        frm.cRFCRepLegal.value = aRes [0][25];
        if(aRes[0][29] != null && aRes[0][29]!= "")
           frm.cDomicilioRepLegal.value = aRes [0][26];
        else
           frm.cDomicilioRepLegal.value = "";

     }
     else {
        fBlanked();
        alert("No existen datos....");
      }

   }

   //ggfNavega();


 }
 // LLAMADO desde el Panel cada vez que se presiona al boton Nuevo
 function fNuevo(){
    //FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iEjercicio,","--");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al boton Guardar
 function fGuardar(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
          //FRMPanel.fSetTraStatus("UpdateComplete");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al boton GuardarA "Actualizar"
 function fGuardarA(){
    if(fValidaTodo()==true){
       if(fNavega()==true){
         //FRMPanel.fSetTraStatus("UpdateComplete");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al boton Modificar
 function fModificar(){
    //FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iEjercicio,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al boton Cancelar
 function fCancelar(){
    fDisabled(true,"iEjercicio,iNumSolicitud,hdFiltroUsrXDepto,");
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al boton Borrar
 function fBorrar(){
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){
       fNavega();
    }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglon
 function fSelReg(aDato){
 }
 // FUNCION donde se generan las validaciones de los datos ingresados
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

function fSetSolicitud(iEjercicio,iNumSolicitud, cTramite, cModalidad, cOficina){
   frm.iEjercicio.value = iEjercicio;
   frm.iNumSolicitud.value = iNumSolicitud;
   frm.cTramite.value = cTramite;
   frm.cModalidad.value = cModalidad;
   frm.cOficina.value = cOficina;
   fNavega();

 }


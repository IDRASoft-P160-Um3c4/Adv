// MetaCD=1.0
 // Title: pgBuscaEmbaracion.js
 // Description: JS "Catálogo" de la entidad VEHEmbarcacion
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ABarrientos<dd>Rafael Miranda Blumenkron
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aRes71;
 var aCBoxCalcular;
 var lModoReimpresion = false;
 var aResFichasReimpresion = new Array();
 var aResDatos2 = new Array();

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111020171.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
   fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       fTituloEmergente(cTitulo);
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",1,"95%","","","",1,0,1);
           ITR();
              ITD("ETablaST",2);TextoSimple("SECRETARIA DE COMUNICACIONES Y TRANSPORTES"); FTD();
              ITD("ETablaST");TextoSimple("Movimientos Generados"); FTD();
           FITR();
              ITD("",0,"","","center"); Img("logoSCT01.gif"); FTD();
              ITD("ENormalA"); TextoSimple("R.F.C.: SCT 850101 8I9"); FTD();
              ITD();
                InicioTabla("ETablaInfo",1,"100%","","","",1,0,1);
                  ITR();
                    ITD("EEtiqueta"); TextoSimple("Area Rec:"); FTD();
                    ITD("EEtiquetaL",3); Text(false,"areaRec","",50); FTD();
                  FTR();
                  ITR();
                    ITD("EEtiqueta"); TextoSimple("Oficina:"); FTD();
                    ITD("EEtiquetaL",3); Text(false,"oficina","",50); FTD();
                  FTR();
                  ITR();
                    ITD("EEtiqueta"); TextoSimple("Nombre:"); FTD();
                    ITD("EEtiquetaL",3); Text(false,"nombre","",50); FTD();
                  FTR();
                  ITR();
                    ITD("EEtiqueta"); TextoSimple("RFC:"); FTD();
                    ITD("EEtiquetaL"); Text(false,"cRfc",""); FTD();
                    ITD("EEtiqueta"); TextoSimple("Fecha:"); FTD();
                    ITD("EEtiquetaL"); Text(false,"fecha",""); FTD();
                  FTR();
                FinTabla();
              FTD();

           FTR();
         FinTabla();
       FTDTR();
       ITRTD();
         SP();
       FTDTR();
       ITRTD("",0,"","1","center");
       InicioTabla("",0,"100%","","center");
           ITR();
             InicioTabla("ETablaInfo",1,"40%","","","",1,0,1);
               ITRTD("ETablaST",5,"","","center");
                 TextoSimple("CONCENTRACIÓN DE FONDOS");
               FTDTR();
               ITR();
                 ITD("",0,"","","center"); Img("logoBanamex.gif"); FTD();
                 ITD();
                   InicioTabla("ETablaInfo",1,"100%","","","",1);
                     ITR();
                     ITD("EEtiqueta"); TextoSimple("Sucursal:"); FTD();
                     ITD("EEtiquetaL"); Text(false,"sucursal","",6); FTD();
                     FTR();
                     ITR();
                     ITD("EEtiqueta"); TextoSimple("Cuenta:"); FTD();
                     ITD("EEtiquetaL"); Text(false,"cuenta","",6); FTD();
                     FTR();
                   FinTabla();
                 FTD();
               FTR();

             FinTabla();

           FTDTR();
         FinTabla();
       FTDTR();
       ITRTD("",0,"","175","center","center");
         IFrame("IListadoBE","80%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("ESTituloFolder",0,"","20","center","center");
          //TextoSimple('<font size="6" color="red" face="verdana">NO SELLAR</font>')
          TextoSimple("NO SELLAR")
       FTDTR();
       ITRTD("ENormalAC",0,"","","center","center");
          TextoSimple("NOTA: ESTA FICHA NO TIENE VALIDEZ OFICIAL, SOLICITE SU COMPROBANTE DE PAGO EN SUCURSAL.")
       FTDTR();
       ITRTD("ENormalAC",2,"","20","center","center");
          TextoSimple("Sólo se deberá recibir: Efectivo, Cheques Banamex y Cheques de Caja o Certificados de otros bancos.")
       FTDTR();
       ITRTD("",0,"","50","center","center");
         InicioTabla("",0,"180","","","",1);
          ITRTD("",0,"","","center");
           BtnImg("Imprimir","imprimir","fImprimir();","");
          FITD();
           BtnImg("Aceptar","aceptar","fAceptar();","");
          FTDTR();
         FinTabla();
       FTDTR();
       Hidden("iCveUsuario","");
       Hidden("iCveUsuarioIng","");
       Hidden("iRefNumerica","");
       Hidden("iUnidades","");
       Hidden("hdaRes","");
       Hidden("iCveTramite","0");
       Hidden("iCveModalidad","0");
       Hidden("lPagoAnticipado","0");
       Hidden("iCveOficIngresos","");
       Hidden("iCveUnidAdmva","");


       FinTabla();
     FTDTR();
       ITRTD("",0,"","0","center","center");
         IFrame("IPanelBE","0%","0","Paneles.js");
       FTDTR();
     FinTabla();
   fFinPagina();
 }

 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanelBE");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListadoBE");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Depósito No.,Referencia Numérica,Referencia Alfanumérica,Importe a Depositar,");
   FRMListado.fSetCampos("0,1,2,3,");
   FRMListado.fSetAlinea("center,center,center,right,");

   fDisabled(true);
   frm.cRfc.disabled = false;
   frm.cRfc.readOnly = true;
   frm.sucursal.disabled = false;
   frm.sucursal.readOnly = true;
   frm.cuenta.disabled = false;
   frm.cuenta.readOnly = true;

   frm.areaRec.disabled = false;
   frm.areaRec.readOnly = true;
   frm.oficina.disabled = false;
   frm.oficina.readOnly = true;
   frm.nombre.disabled = false;
   frm.nombre.readOnly = true;
   frm.fecha.disabled = false;
   frm.fecha.readOnly = true;

   if(top.opener){
     if(top.opener.fGetModoReimpresion)
       lModoReimpresion = top.opener.fGetModoReimpresion();
     if(lModoReimpresion)
       if(top.opener.fGetAResReimpresion)
         aResFichasReimpresion = fCopiaArreglo(top.opener.fGetAResReimpresion());
   }

   fTraeFechaActual();
   frm.hdBoton.value="Primero";
 }

 function fNavega(){
 }

 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);

     frm.areaRec.value = aRes[0][4];
     frm.sucursal.value = aRes[0][5];
     frm.cuenta.value = aRes[0][6];
     aResDatos = aRes;
     fCancelar();

   }

   if(cId == "UsuarioXOfic" && cError==""){
     frm.oficina.value = aRes[0][5];
     frm.iCveUnidAdmva.value = aRes[0][9];
     if (lModoReimpresion == false)
       fGeneraMov(aRes71);
     else
       fTraeAreaSucursalCuenta();
   }

   if(cId == "AreaSucursalCuenta" && cError ==""){
     frm.areaRec.value = aRes[0];
     frm.sucursal.value = aRes[1];
     frm.cuenta.value = aRes[2];
     fDespliegaListadoReimpresion();
   }

   if(cId == "idFechaActual" && cError==""){
     frm.fecha.value = aRes[0,0];
     if (lModoReimpresion == false)
       fObtieneValores();
     else
       fGetDatosGenerales();
   }


 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("UpdateComplete");
    else
      FRMPanel.fSetTraStatus("AddOnly");

       fDisabled(true);
   frm.cRfc.disabled = false;
   frm.cRfc.readOnly = true;
   frm.sucursal.disabled = false;
   frm.sucursal.readOnly = true;
   frm.cuenta.disabled = false;
   frm.cuenta.readOnly = true;

   frm.areaRec.disabled = false;
   frm.areaRec.readOnly = true;
   frm.oficina.disabled = false;
   frm.oficina.readOnly = true;
   frm.nombre.disabled = false;
   frm.nombre.readOnly = true;
   frm.fecha.disabled = false;
   frm.fecha.readOnly = true;
    FRMListado.fSetDisabled(false);
 }

 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
 }

 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
 }

 function fImprimir(){
    self.focus();
    window.print();
 }

 function fObtieneValores(){
   if(top.opener){
     aRes71 = new Array();
     var aTemp = new Array();
     aTemp = top.opener.fGetAResultadoTemp();
     for (var i=0; i<aTemp.length; i++)
       aRes71[aRes71.length] = fCopiaArreglo(aTemp[i]);

     aCBoxCalcular            = top.opener.FRMListado.fGetObjs(0);
     frm.cRfc.value           = top.opener.frm.cRFC.value;
     frm.nombre.value         = top.opener.frm.cNomRazonSocial.value;
     frm.iCveUsuarioIng.value = top.opener.frm.iCvePersonaIng.value;
     frm.iCveUsuario.value    = top.opener.frm.iCveUsuario.value;
     fTraeUsuarioOfic();

   }
   else{
     fAlert("Cierre la ventana y vuelva a generar la ficha");
   }
 }

 function fGeneraMov(paraResTemp){
   aResTemp = new Array();
   aResTemp = paraResTemp;
   frm.hdBoton.value = "CalcularGenerar";

   for(cont=0;cont < aCBoxCalcular.length;cont++){
     if(aCBoxCalcular[cont]){
       if(frm.hdaRes.value=="")
         frm.hdaRes.value = aResTemp[cont];
       else
         frm.hdaRes.value += "'"+aResTemp[cont];
     }
   }

   if(frm.hdaRes.value!="")
     return fEngSubmite("pgTRAImporteConceptoXTramMod.jsp","Listado");

 }

function fTraeUsuarioOfic(){
  frm.hdFiltro.value = " GRLUsuarioXOfic.ICVEUSUARIO = " + frm.iCveUsuario.value;
  fEngSubmite("pgGRLUsuarioXOfic.jsp","UsuarioXOfic");
}

function fAceptar(){
  if(lModoReimpresion == false){
    if(top.opener)
      if(top.opener.fDatosReferencia){
        aResDatos2 = new Array();
        for(i=0; i < aResDatos.length; i++){
          aResDatos2[i] = new Array();
          aResDatos2[i][0] = aResDatos[i][0];
          aResDatos2[i][1] = aResDatos[i][1];
          aResDatos2[i][2] = aResDatos[i][2];
          aResDatos2[i][3] = aResDatos[i][3];
          aResDatos2[i][4] = aResDatos[i][4];
          aResDatos2[i][5] = aResDatos[i][5];
          aResDatos2[i][6] = aResDatos[i][6];
          aResDatos2[i][7] = aResDatos[i][7];
          aResDatos2[i][8] = aResDatos[i][8];
          aResDatos2[i][9] = aResDatos[i][9];
          aResDatos2[i][10] = frm.fecha.value;
        }
        aResDatos = fCopiaArregloBidim(aResDatos2);
        top.opener.fDatosReferencia(aResDatos,top);
      }
  }else
    top.close();
}

function fGetaResDatos(){
  return aResDatos;
}

function fGetDatosGenerales(){
  if(lModoReimpresion == true){
    frm.cRfc.value           = top.opener.frm.cRFCSolicitante.value;
    frm.nombre.value         = top.opener.frm.Persona_cNomRazonSocial.value;
    frm.iCveUsuario.value    = fGetIdUsrSesion();
    fTraeUsuarioOfic();
  }
}

function fDespliegaListadoReimpresion(){
  FRMListado.fSetListado(aResFichasReimpresion);
  FRMListado.fShow();
}

function fTraeAreaSucursalCuenta(){
  fEngSubmite("pgGRLAreaSucursalCuenta.jsp","AreaSucursalCuenta");
}

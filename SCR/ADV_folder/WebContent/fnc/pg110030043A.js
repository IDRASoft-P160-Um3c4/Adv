


// MetaCD=1.0
 // Title: pg110030043.js
 // Description: JS "Catálogo" de la entidad VEHEmbarcacion
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Enrique Moreno Belmares
 var cTitulo = "";
 var FRMListado = "";
 var FRMListado2 = "";
 var frm;
 var aUnidades = new Array();
 var aLongitud = new Array();
 var aPotencia = new Array();
 var aVolumen = new Array();
 var aCantidad = new Array();
 var aPeso = new Array();
 var aVelocidad = new Array();
 var aMoneda = new Array();
 
 var aUnArqBruto = new Array();
 var aUnArqNeto = new Array();

 var aParUAB = new Array();
 var aPais = new Array();

 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg110030043A.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }

 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
       IFrame("IFiltro43A","0","0","Filtros.js");
       IFrame("IListado43A","0","0","Listado.js","yes",true);
     ITRTD("",0,"","","top");
     	InicioTabla("",0,"100%","","center");
     		ITRTD("",0,"","1","center");
     			InicioTabla("", 0, "100%", "", "center");
 			ITRTD("", 0, "", "0", "center", "top");
 				IFrame("IFiltro1", "0", "0", "Filtros.js");
			FTDTR();
			ITRTD("", 0, "", "175", "center", "top");
				IFrame("IListado1", "95%", "170", "Listado.js", "yes", true);
			FTDTR();
		FinTabla();	
      FITR();
       	InicioTabla("ETablaInfo",0,"90%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Embarcación");
           FTDTR();
           ITR();
              //TDEtiCampo(true,"EEtiqueta",0,"Clave:","iCveVehiculo","",5,5,"ClaveVehiculo","fMayus(this);");
              TDEtiCampo(true,"EEtiqueta",0,"Embarcación:","cNomEmbarcacion","",75,75,"Nombre de la Embarcación","fMayus(this);","","",true,"",9)
              //TDEtiCampo(true,"EEtiqueta",0,"Embarcacion:","cNomEmbarcacion","",75,75,"NombreEmbarcacion","fMayus(this);");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Abanderamiento:","iCvePaisAbanderamiento","");
              TDEtiCheckBox("EEtiqueta",0,"Artefacto:","lArtefactoBOX","1",true,"Artefacto");
              Hidden("lArtefacto","");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Numeral:","cNumeral","",10,10,"Numeral","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Número OMI:","cNumOMI","",30,30,"Número OMI","fMayus(this);");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Navegación:","iCveTipoNavega","");
              TDEtiSelect(true,"EEtiqueta",0,"Servicio:","iCveTipoServ","");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Tipo:","iCveTipoEmbarcacion","","",9);
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Material del casco:","iCveMaterial","","",9);
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Sociedad Clasif.:","iCveSocClasificacion","","",9);
          FITR();
              TDEtiCampo(true,"EEtiqueta",0,"Lugar Construcción:","cLugarConstruccion","",100,100,"Lugar de Construcción","fMayus(this);","","",true,"",9)
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Construcción:","dtConstruccion","",10,10,"Fecha de Construcción","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Clase:","cClase","",15,15,"Clase","fMayus(this);");
           FITR();
              ITD("EEtiqueta"); TextoSimple("Valor Embarcación:"); FTD();
              ITD("ECampo");
              fInput("text","dValorEmbarcacion","\"\"",8,8,"Valor de la Embarcación","","","",true,false);
              Select("iCveUnidMedValorEmb",""); FTD();
              TDEtiSelect(true,"EEtiqueta",0,"Tipo de carga:","iCveTipoCarga","");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Bodegas:","iNumBodegas","",5,5,"Número de Bodegas","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Tanques:","iNumTanques","",5,5,"Número de Tanques","fMayus(this);");
           FITR();
              TDEtiCampo(false,"EEtiqueta",0,"Tripulación Min.:","iTripulacionMin","",5,5,"Tripulación Mínima","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"Tripulación Max.:","iTripulacionMax","",5,5,"Tripulación Máxima","fMayus(this);");
           FTR();
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Varios");
           FTDTR();
           ITR();
             InicioTabla("ETablaInfo",0,"90%","","","",1);
               ITR();
                 ITD("ETablaST"); TextoSimple("Dimensiones"); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Eslora:","dEslora","",8,8,"Eslora","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedEslora",""); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Manga:","dManga","",8,8,"Manga","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedManga",""); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Puntal:","dPuntal","",8,8,"Puntal","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedPuntal",""); FTD();
               FITR();
                 ITD("ETablaST"); TextoSimple("Calado"); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Popa:","dCaladoPopa","",8,8,"Calado Popa","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedCaladoPopa",""); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Proa:","dCaladoProa","",8,8,"Calado Proa","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedCaladoProa",""); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Max:","dCaladoMax","",8,8,"Calado Máximo","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedCaladoMax",""); FTD();
               FITR();
                 ITD("ETablaST"); TextoSimple(""); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Medio:","dCaladoMedio","",8,8,"Calado Medio","fMayus(this);"); ITD("EEtiquetaL");  FTD();
               FITR();
                 ITD("ETablaST"); TextoSimple("Arqueo"); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Bruto:","dArqueoBruto","",8,8,"Arqueo Bruto","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedArqueoBruto",""); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Neto:","dArqueoNeto","",8,8,"Arqueo Neto","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedArqueoNeto",""); FTD();
               FITR();
                 ITD("ETablaST"); TextoSimple("Peso y Velocidad"); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Muerto:","dPesoMuerto","",8,8,"Peso Muerto","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedPesoMuerto",""); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Velocidad Máxima:","dVelocidadMaxima","",8,8,"Velocidad Máxima","fMayus(this);"); ITD("EEtiquetaL"); Select("iUniMedVelocidadMax",""); FTD();
                 TDEtiCheckBox("EEtiqueta",0,"Gran velocidad:","lGranVelocidadBOX","1",true,"Gran velocidad");
                 Hidden("lGranVelocidad","");
               FITR();
                 ITD("ETablaST"); TextoSimple(""); FTD();
                 TDEtiCampo(false,"EEtiqueta",0,"Calculo Velocidad:","cCalculoVelocidad","",50,50,"Calculo de Velocidad","fMayus(this);","","",true,"",5)
               FITR();
                 ITD("ETablaST"); TextoSimple("Potencia"); FTD();
                 TDEtiCampo(true,"EEtiqueta",0,"Total:","iPotenciaTotal","",8,8,"Potencia Total","fMayus(this);"); ITD("EEtiquetaL"); Select("iUnidadMedPotencia",""); FTD();
               FTR();
             FinTabla();
           FTDTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel43A","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
     Hidden("iCveVehiculo","");
     Hidden("cParLongitud","");
     Hidden("cParPotencia","");
     Hidden("cParVolumen","");
     Hidden("cParCantidad","");
     Hidden("cParPeso","");
     Hidden("cParVelocidad","");
     Hidden("cParMoneda","");
     
     Hidden("cParArqBruto","");
     Hidden("cParArqNeto","");
     
     Hidden("cParUAB","");
     Hidden("iCvePais","");
     Hidden("iCvePaisDef","");
     Hidden("pais","");

     //Valores de Salida de la Pagina.
     Hidden("cDscTipoServ","");
     Hidden("cDscTipoEmbarcacion","");
     Hidden("cDscTipoNavega","");
     Hidden("cDscUnidadMedidaArqueoBruto","");
     Hidden("cDscUnidadMedidaArqueoNeto","");
     Hidden("cDscUnidadMedidaPesoMuerto","");
     Hidden("cDscUnidadMedidaManga","");
     Hidden("cDscUnidadMedidaPuntal","");
   fFinPagina();
 }

 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel43A");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");

   FRMListado = fBuscaFrame("IListado43A");
    FRMListado.fSetControl(self);
    FRMListado.fSetTitulo("Clave,Matricula,Fecha de cambio,Usuario realizo,");
	FRMListado.fSetCampos("0,60,58,59,");	
	FRMListado.fSetAlinea("left,left,left,left,");
	FRMListado.fSetDespliega("text,text,text,text,");
	
    FRMFiltro = fBuscaFrame("IFiltro43A");
    FRMFiltro.fSetControl(self);
    FRMFiltro.fShow(',');
    FRMFiltro.fSetFiltra("Clave,VEHVehiculo.iCveVehiculo,Matrícula,VEHVehiculo.cMatricula,Nombre,VEHEmbarcacion.cNomEmbarcacion,");
    FRMFiltro.fSetOrdena("Clave,VEHVehiculo.iCveVehiculo,Matrícula,VEHVehiculo.cMatricula,Nombre,VEHEmbarcacion.cNomEmbarcacion,");
   
	FRMListado2 = fBuscaFrame("IListado1");
	FRMListado2.fSetControl(self);
    FRMListado2.fSetTitulo("Clave,Matricula,Fecha de cambio,Usuario realizo,");
	FRMListado2.fSetCampos("0,60,58,59,");	
	FRMListado2.fSetAlinea("left,left,left,left,");
	FRMListado2.fSetDespliega("text,text,text,text,");
	
	FRMFiltro2 = fBuscaFrame("IFiltro1");
	FRMFiltro2.fSetControl(self);
	FRMFiltro2.fShow();
	FRMFiltro2.fSetFiltra("Clave,VEHVehiculo.iCveVehiculo,Matrícula,VEHVehiculo.cMatricula,Nombre,VEHEmbarcacion.cNomEmbarcacion,");
	FRMFiltro2.fSetOrdena("Clave,VEHVehiculo.iCveVehiculo,Matrícula,VEHVehiculo.cMatricula,Nombre,VEHEmbarcacion.cNomEmbarcacion,");
	
	    
   fDisabled(true);
   if(top.opener)if(top.opener.fGetEmbarcacion){
     frm.iCveVehiculo.value = top.opener.fGetEmbarcacion();
     frm.hdFiltro.value = "";
     frm.hdOrden.value = "";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgSEGPropiedad.jsp","idPropiedad");
   }
   frm.hdBoton.value="Primero";
 }

 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   frm.hdFiltro.value = " VEHEmbarcacion.iCveVehiculo = "+ frm.iCveVehiculo.value;
   frm.hdOrden.value = "";
   frm.hdNumReg.value =  1;
   return fEngSubmite("pgVEHEmbarcacionA6.jsp","Listado");
 }
 
 function fBuscaHistorico(){
	   frm.hdBoton.value="";	 
	   frm.hdFiltro.value = " VEHEmbarcacion.iCveVehiculo = "+ frm.iCveVehiculo.value;
	   frm.hdOrden.value = "";
	   frm.hdNumReg.value =  100;
	   return fEngSubmite("pgVEHEmbarcacionHist.jsp","Listado2");
 }

 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,cParLongitud,cParPotencia,cParPeso,cParVelocidad,cParMoneda,cParVolumen){
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

   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
     fCancelar();
     FRMFiltro.fSetNavStatus(cNavStatus);
 //Para realizar la consulta al historico de la embarcación
     fBuscaHistorico();
   }
 
   if(cId == "Listado2" && cError==""){
	   	 FRMListado2.fSetSelReg(2);
	     FRMListado2.fSetListado(aRes);
	     FRMListado2.fShow();
	     FRMListado2.fSetLlave(cLlave);
   }
  

   if( cId == "idPropiedad" && cError == "" ) {
     frm.cParLongitud.value = cParLongitud;
     frm.cParPotencia.value = cParPotencia;
     frm.cParPeso.value = cParPeso;
     frm.cParVelocidad.value = cParVelocidad;
     frm.cParMoneda.value = cParMoneda;
     
     var volBrutoNetoArray = new Array() 
     volBrutoNetoArray = cParVolumen.split(',');     

     frm.cParVolumen.value = volBrutoNetoArray[0];          
     frm.cParArqBruto.value = volBrutoNetoArray[1];     
     frm.cParArqNeto.value = volBrutoNetoArray[2];   
          
     frm.hdFiltro.value = "";
     frm.hdOrden.value = "";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgSEGPropiedadA1.jsp","idPropiedad2");
   }

   if( cId == "idPropiedad2" && cError == "" ) {
     frm.iCvePaisDef.value = cParLongitud;

     frm.hdFiltro.value = "";
     frm.hdOrden.value = "UM.CDSCUNIDADMEDIDA";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgVEHUnidadMedida.jsp","idUnidades");
   }

   if( cId == "idUnidades" && cError == "" ) {
			aUnidades = new Array();
 			aLongitud = new Array();
 			aPotencia = new Array();
 			aVolumen = new Array();
 			aPeso = new Array();
 			aVelocidad = new Array();
 			aMoneda = new Array();
 			
			 aUnArqBruto = new Array();
 			 aUnArqNeto = new Array();
 			

     aUnidades = aRes;

     for(var i=0;i< aUnidades.length; i++){

       if (aUnidades[i][1] == frm.cParLongitud.value)
         aLongitud[aLongitud.length] = aUnidades[i];
       if (aUnidades[i][1] == frm.cParPotencia.value)
         aPotencia[aPotencia.length] = aUnidades[i];
       if (aUnidades[i][1] == frm.cParPeso.value)
         aPeso[aPeso.length] = aUnidades[i];
       if (aUnidades[i][1] == frm.cParVelocidad.value)
         aVelocidad[aVelocidad.length] = aUnidades[i];
       if (aUnidades[i][1] == frm.cParMoneda.value)
         aMoneda[aMoneda.length] = aUnidades[i];
       if (aUnidades[i][1] == frm.cParVolumen.value)
         aVolumen[aVolumen.length] = aUnidades[i];
       
       if (aUnidades[i][1] == frm.cParArqBruto.value)
    	   aUnArqBruto[aUnArqBruto.length] = aUnidades[i];    	          	       	                 
       if (aUnidades[i][1] == frm.cParArqNeto.value)
    	   aUnArqNeto[aUnArqNeto.length] = aUnidades[i];    	                 
     }

     fFillSelect(frm.iUniMedEslora,aLongitud,false,0,0,2);
     fFillSelect(frm.iUniMedManga,aLongitud,false,0,0,2);
     fFillSelect(frm.iUniMedPuntal,aLongitud,false,0,0,2);
     fFillSelect(frm.iUniMedCaladoPopa,aLongitud,false,0,0,2);
     fFillSelect(frm.iUniMedCaladoProa,aLongitud,false,0,0,2);
     fFillSelect(frm.iUniMedCaladoMax,aLongitud,false,0,0,2);
     
     fFillSelect(frm.iUniMedArqueoBruto,aUnArqBruto,false,0,0,2);   
     fFillSelect(frm.iUniMedArqueoNeto,aUnArqNeto,false,0,0,2);
     
     fFillSelect(frm.iUniMedPesoMuerto,aPeso,false,0,0,2);
     fFillSelect(frm.iUniMedVelocidadMax,aVelocidad,false,0,0,2);
     fFillSelect(frm.iUnidadMedPotencia,aPotencia,false,0,0,2);
     fFillSelect(frm.iCveUnidMedValorEmb,aMoneda,false,0,0,2);



     if(frm.pais.value == 0){
       frm.hdFiltro.value = "";
     }
     if(top.opener)
       if(top.opener.fSetNacionales)
           if(top.opener.fSetNacionales()==1){
             frm.hdFiltro.value = "";
           } else frm.hdFiltro.value = " iCvePais != 1";
     frm.hdOrden.value = "";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgGRLPais.jsp","idPais");
   }

   if( cId == "idPais" && cError == "" ) {

     if (frm.iCvePais.value != "" && frm.iCvePais.value == frm.iCvePaisDef.value){
        for(var i=0;i< aRes.length; i++){
           if (aRes[i][0] == frm.iCvePaisDef.value)
              aPais[aPais.length] = aRes[i];
        }
     } else
       aPais = aRes;
     fFillSelect(frm.iCvePaisAbanderamiento,aPais,false);
     frm.hdFiltro.value = "";
     frm.hdOrden.value = "CDSCTIPOSERV";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgVEHTipoServicio.jsp","idTipoServicio");
   }

   if( cId == "idTipoServicio" && cError == "" ) {
     fFillSelect(frm.iCveTipoServ,aRes,false,0,0,1);
     frm.hdFiltro.value = "";
     frm.hdOrden.value = "CDSCTIPOEMBARCACION";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgVEHTipoEmbarcacion.jsp","idTipoEmbarcacion");
   }

   if( cId == "idTipoEmbarcacion" && cError == "" ) {
     fFillSelect(frm.iCveTipoEmbarcacion,aRes,false);
     frm.hdFiltro.value = "";
     frm.hdOrden.value = "CDSCTIPONAVEGA";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgVEHTipoNavegacion.jsp","idTipoNavegacion");
   }

   if( cId == "idTipoNavegacion" && cError == "" ) {
     fFillSelect(frm.iCveTipoNavega,aRes,false);
     frm.hdFiltro.value = "";
     frm.hdOrden.value = "CDSCTIPOCARGA";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgVEHTipoCarga.jsp","idTipoCarga");
   }

   if( cId == "idTipoCarga" && cError == "" ) {
     fFillSelect(frm.iCveTipoCarga,aRes,false);
     frm.hdFiltro.value = "";
     frm.hdOrden.value = "CDSCMATERIAL";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgVEHMaterialCasco.jsp","idMaterialCasco");
   }

   if( cId == "idMaterialCasco" && cError == "" ) {
     fFillSelect(frm.iCveMaterial,aRes,false);
     frm.hdFiltro.value = "";
     frm.hdOrden.value = " CDSCSOCCLASIFICACION";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgVEHSocClasificacion.jsp","idSocClasificacion");
   }

   if( cId == "idSocClasificacion" && cError == "" ) {
     fFillSelect(frm.iCveSocClasificacion,aRes,false);
     fNavega();
   }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fBlanked("iCveVehiculo,cParLongitud,cParPotencia,cParVolumen,cParCantidad,cParPeso,");
   fDisabled(false,"iCveVehiculo,");
   FRMListado.fSetDisabled(true);

 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
   if(fValidaTodo("")==true){
     if( frm.lGranVelocidadBOX.checked ) frm.lGranVelocidad.value = 1;
     else frm.lGranVelocidad.value = 0;
     if( frm.lArtefactoBOX.checked ) frm.lArtefacto.value = 1;
     else frm.lArtefacto.value = 0;
      if(fNavega()==true){
          FRMPanel.fSetTraStatus("Mod,");
          fDisabled(true);
          FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
   if(fValidaTodo()==true){
     if( frm.lGranVelocidadBOX.checked ) frm.lGranVelocidad.value = 1;
     else frm.lGranVelocidad.value = 0;
     if( frm.lArtefactoBOX.checked ) frm.lArtefacto.value = 1;
     else frm.lArtefacto.value = 0;
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("Mod,");
         fDisabled(true);
         FRMListado.fSetDisabled(false);
       }
    }
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
   FRMPanel.fSetTraStatus("UpdateBegin");
   fDisabled(false,"iCveVehiculo,");
   FRMListado.fSetDisabled(true);
 }

 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
   FRMFiltro.fSetNavStatus("ReposRecord");
//   if(FRMListado.fGetLength() > 0)
//      FRMPanel.fSetTraStatus("Mod,");
//    else

     if(frm.cNomEmbarcacion.value != "" || FRMListado.fGetLength() > 0)
        FRMPanel.fSetTraStatus("Mod,")
     else
        FRMPanel.fSetTraStatus("AddOnly");
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
    frm.cNomEmbarcacion.value = aDato[1];
    fSelectSetIndexFromValue(frm.iCvePaisAbanderamiento, aDato[2]);
    fSelectSetIndexFromValue(frm.iCveTipoEmbarcacion, aDato[4]);
    frm.cDscTipoEmbarcacion.value = fDameCadena(frm.iCveTipoEmbarcacion, aDato[4]);
    fSelectSetIndexFromValue(frm.iCveTipoNavega, aDato[6]);
    frm.cDscTipoNavega.value = fDameCadena(frm.iCveTipoNavega, aDato[6]);

    fSelectSetIndexFromValue(frm.iCveTipoServ, aDato[8]);
    frm.cDscTipoServ.value = fDameCadena(frm.iCveTipoServ, aDato[8]);

    frm.lGranVelocidad.value = aDato[10];
    if( frm.lGranVelocidad.value == 1 ) frm.lGranVelocidadBOX.checked = true;
    else frm.lGranVelocidadBOX.checked = false;
    fSelectSetIndexFromValue(frm.iCveMaterial, aDato[15]);
    frm.dEslora.value = aDato[17];
    fSelectSetIndexFromValue(frm.iUniMedEslora, aDato[18]);
    frm.dManga.value = aDato[19];
    fSelectSetIndexFromValue(frm.iUniMedManga, aDato[20]);
    frm.cDscUnidadMedidaManga.value = fDameCadena(frm.iUniMedManga, aDato[20]);
    frm.dPuntal.value = aDato[21];
    fSelectSetIndexFromValue(frm.iUniMedPuntal, aDato[22]);
    frm.cDscUnidadMedidaPuntal.value = fDameCadena(frm.iUniMedPuntal, aDato[22]);
    frm.dCaladoPopa.value = aDato[23];
    fSelectSetIndexFromValue(frm.iUniMedCaladoPopa, aDato[24]);
    frm.dCaladoProa.value = aDato[25];
    fSelectSetIndexFromValue(frm.iUniMedCaladoProa, aDato[26]);
    frm.dCaladoMax.value = aDato[27];
    fSelectSetIndexFromValue(frm.iUniMedCaladoMax, aDato[28]);
    frm.dArqueoBruto.value = aDato[29];
    fSelectSetIndexFromValue(frm.iUniMedArqueoBruto, aDato[30]);
    frm.cDscUnidadMedidaArqueoBruto.value = fDameCadena(frm.iUniMedArqueoBruto, aDato[30]);
    frm.dArqueoNeto.value = aDato[31];
    fSelectSetIndexFromValue(frm.iUniMedArqueoNeto, aDato[32]);
    frm.cDscUnidadMedidaArqueoNeto.value = fDameCadena(frm.iUniMedArqueoNeto, aDato[32]);
    frm.dPesoMuerto.value = aDato[33];
    fSelectSetIndexFromValue(frm.iUniMedPesoMuerto, aDato[34]);
    frm.cDscUnidadMedidaPesoMuerto.value = fDameCadena(frm.iUniMedPesoMuerto, aDato[34]);
    frm.dVelocidadMaxima.value = aDato[35];
    fSelectSetIndexFromValue(frm.iUniMedVelocidadMax, aDato[36]);
    fSelectSetIndexFromValue(frm.iCveTipoCarga, aDato[37]);
    frm.iNumBodegas.value = aDato[39];
    frm.iNumTanques.value = aDato[40];
    frm.cNumOMI.value = aDato[41];
    frm.cNumeral.value = aDato[42];
    frm.cClase.value = aDato[43];
    frm.iPotenciaTotal.value = aDato[46];
    fSelectSetIndexFromValue(frm.iUnidadMedPotencia, aDato[47]);
    frm.lArtefacto.value = aDato[48];
    if( frm.lArtefacto.value == 1 ) frm.lArtefactoBOX.checked = true;
    else frm.lArtefactoBOX.checked = false;
    if (aDato[49] != undefined)
      frm.iTripulacionMin.value = aDato[49];
    else
      frm.iTripulacionMin.value = "";
    if (aDato[50] != undefined)
      frm.iTripulacionMax.value = aDato[50];
    else
    frm.iTripulacionMax.value = "";
    if (aDato[51] != undefined)
      frm.dValorEmbarcacion.value = aDato[51];
    else
      frm.dValorEmbarcacion.value = "";
    fSelectSetIndexFromValue(frm.iCveUnidMedValorEmb, aDato[52]);
    if (aDato[53] != undefined)
      frm.cLugarConstruccion.value = aDato[53];
    else
      frm.cLugarConstruccion.value = "";
    fSelectSetIndexFromValue(frm.iCveSocClasificacion, aDato[54]);
    if (aDato[55] != undefined)
      frm.cCalculoVelocidad.value = aDato[55];
    else
      frm.cCalculoVelocidad.value = "";
    if (aDato[56] != undefined)
      frm.dCaladoMedio.value = aDato[56];
    else
      frm.dCaladoMedio.value = "";
    if (aDato[57] != undefined)
      frm.dtConstruccion.value = aDato[57];
    else
      frm.dtConstruccion.value = "";


 }

 // LLAMADO desde el Listado que si se ve en la pantalla cada vez que se selecciona un renglón
 function fSelReg2(aDato){
    frm.cNomEmbarcacion.value = aDato[1];
    fSelectSetIndexFromValue(frm.iCvePaisAbanderamiento, aDato[2]);
    fSelectSetIndexFromValue(frm.iCveTipoEmbarcacion, aDato[4]);
    frm.cDscTipoEmbarcacion.value = fDameCadena(frm.iCveTipoEmbarcacion, aDato[4]);
    fSelectSetIndexFromValue(frm.iCveTipoNavega, aDato[6]);
    frm.cDscTipoNavega.value = fDameCadena(frm.iCveTipoNavega, aDato[6]);

    fSelectSetIndexFromValue(frm.iCveTipoServ, aDato[8]);
    frm.cDscTipoServ.value = fDameCadena(frm.iCveTipoServ, aDato[8]);

    frm.lGranVelocidad.value = aDato[10];
    if( frm.lGranVelocidad.value == 1 ) frm.lGranVelocidadBOX.checked = true;
    else frm.lGranVelocidadBOX.checked = false;
    fSelectSetIndexFromValue(frm.iCveMaterial, aDato[15]);
    frm.dEslora.value = aDato[17];
    fSelectSetIndexFromValue(frm.iUniMedEslora, aDato[18]);
    frm.dManga.value = aDato[19];
    fSelectSetIndexFromValue(frm.iUniMedManga, aDato[20]);
    frm.cDscUnidadMedidaManga.value = fDameCadena(frm.iUniMedManga, aDato[20]);
    frm.dPuntal.value = aDato[21];
    fSelectSetIndexFromValue(frm.iUniMedPuntal, aDato[22]);
    frm.cDscUnidadMedidaPuntal.value = fDameCadena(frm.iUniMedPuntal, aDato[22]);
    frm.dCaladoPopa.value = aDato[23];
    fSelectSetIndexFromValue(frm.iUniMedCaladoPopa, aDato[24]);
    frm.dCaladoProa.value = aDato[25];
    fSelectSetIndexFromValue(frm.iUniMedCaladoProa, aDato[26]);
    frm.dCaladoMax.value = aDato[27];
    fSelectSetIndexFromValue(frm.iUniMedCaladoMax, aDato[28]);
    frm.dArqueoBruto.value = aDato[29];
    fSelectSetIndexFromValue(frm.iUniMedArqueoBruto, aDato[30]);
    frm.cDscUnidadMedidaArqueoBruto.value = fDameCadena(frm.iUniMedArqueoBruto, aDato[30]);
    frm.dArqueoNeto.value = aDato[31];
    fSelectSetIndexFromValue(frm.iUniMedArqueoNeto, aDato[32]);
    frm.cDscUnidadMedidaArqueoNeto.value = fDameCadena(frm.iUniMedArqueoNeto, aDato[32]);
    frm.dPesoMuerto.value = aDato[33];
    fSelectSetIndexFromValue(frm.iUniMedPesoMuerto, aDato[34]);
    frm.cDscUnidadMedidaPesoMuerto.value = fDameCadena(frm.iUniMedPesoMuerto, aDato[34]);
    frm.dVelocidadMaxima.value = aDato[35];
    fSelectSetIndexFromValue(frm.iUniMedVelocidadMax, aDato[36]);
    fSelectSetIndexFromValue(frm.iCveTipoCarga, aDato[37]);
    frm.iNumBodegas.value = aDato[39];
    frm.iNumTanques.value = aDato[40];
    frm.cNumOMI.value = aDato[41];
    frm.cNumeral.value = aDato[42];
    frm.cClase.value = aDato[43];
    frm.iPotenciaTotal.value = aDato[46];
    fSelectSetIndexFromValue(frm.iUnidadMedPotencia, aDato[47]);
    frm.lArtefacto.value = aDato[48];
    if( frm.lArtefacto.value == 1 ) frm.lArtefactoBOX.checked = true;
    else frm.lArtefactoBOX.checked = false;
    if (aDato[49] != undefined)
      frm.iTripulacionMin.value = aDato[49];
    else
      frm.iTripulacionMin.value = "";
    if (aDato[50] != undefined)
      frm.iTripulacionMax.value = aDato[50];
    else
    frm.iTripulacionMax.value = "";
    if (aDato[51] != undefined)
      frm.dValorEmbarcacion.value = aDato[51];
    else
      frm.dValorEmbarcacion.value = "";
    fSelectSetIndexFromValue(frm.iCveUnidMedValorEmb, aDato[52]);
    if (aDato[53] != undefined)
      frm.cLugarConstruccion.value = aDato[53];
    else
      frm.cLugarConstruccion.value = "";
    fSelectSetIndexFromValue(frm.iCveSocClasificacion, aDato[54]);
    if (aDato[55] != undefined)
      frm.cCalculoVelocidad.value = aDato[55];
    else
      frm.cCalculoVelocidad.value = "";
    if (aDato[56] != undefined)
      frm.dCaladoMedio.value = aDato[56];
    else
      frm.dCaladoMedio.value = "";
    if (aDato[57] != undefined)
      frm.dtConstruccion.value = aDato[57];
    else
      frm.dtConstruccion.value = "";


 }
 
 
 
function fValNomEmbarcacion(cVCadena){
    if ( fRaros(cVCadena)      || //fPuntuacion(cVCadena) ||
         //fSignos(cVCadena)     || //fArroba(cVCadena)     ||
         fParentesis(cVCadena) || //fPunto(cVCadena)      ||
         fDiagonal(cVCadena)   || //fComa(cVCadena)
         fEncCaract(cVCadena,"+")
         )
        return false;
    else
        return true;
}

 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){
    cMsg = fValElements("cCalculoVelocidad,cNomEmbarcacion,");
    if(!fValNomEmbarcacion(frm.cNomEmbarcacion.value)) cMsg += 'El campo nombre de embarcación solo puede tener letras, números y guión medio.';
    if(parseInt(frm.dManga.value,10)>9999.99) cMsg+='\n - El campo de "Manga" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dPuntal.value,10)>9999.99) cMsg+='\n - El campo de "Puntal" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dCaladoPopa.value,10)>9999.99) cMsg+='\n - El campo de "Calado en popa" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dCaladoProa.value,10)>9999.99) cMsg+='\n - El campo de "Calado en proa" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dCaladoMedio.value,10)>9999.99) cMsg+='\n - El campo de "Calado medio" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dVelocidadMaxima.value,10)>9999.99) cMsg+='\n - El campo de "Velocidad maxima" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.iPotenciaTotal.value,10)>99999999) cMsg+='\n - El campo de "Potencia total" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dCaladoMax.value,10)>9999.99) cMsg+='\n - El campo de "Calado Maximo" no puede exceder de 9999.99 Unidades';

    if(parseInt(frm.dEslora.value,10)>999999.99) cMsg+='\n - El campo de "Eslora" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dArqueoBruto.value,10)>999999.99) cMsg+='\n - El campo de "Arqueo bruto" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dArqueoNeto.value,10)>999999.99) cMsg+='\n - El campo de "Arqueo neto" no puede exceder de 9999.99 Unidades';
    if(parseInt(frm.dPesoMuerto.value,10)>999999.99) cMsg+='\n - El campo de "Peso muerto" no puede exceder de 9999.99 Unidades';

    if (frm.iTripulacionMin.value != "" && frm.iTripulacionMax.value != ""){
      if (parseInt(frm.iTripulacionMin.value) > parseInt(frm.iTripulacionMax.value)){
        cMsg += "\n - La Tripulación Mínima debe ser Menor que la Tripulación Máxima";
        frm.iTripulacionMin.focus();
      }
    }

    if(frm.dValorEmbarcacion.value = ""){
       cMsg += "\n - Favor de introducir el valor de la Embarcación";
       frm.dValorEmbarcacion.focus();
    }

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

 function fGetiCveVehiculo() {
   return frm.iCveVehiculo.value;
 }

 function fSetiCveVehiculo(valor) {
   frm.iCveVehiculo.value = valor;
   frm.hdFiltro.value = "";
   frm.hdOrden.value = "";
   frm.hdNumReg.value = 10000;
   //fNavega();
   fEngSubmite("pgSEGPropiedad.jsp","idPropiedad");
 }


 function fAsgUnidades(valor) {
   if( valor == "" ) {
     return "";
   }
   else {
     return aUnidades[valor][2];
   }
 }

 function fSetICvePais(iCvePais){
   frm.iCvePais.value = iCvePais;
 }

/* function fGetPaisAbandera(paisabandera){
   pais = paisabandera;
 }*/

 function fGetcNomEmbarcacion(){
   return frm.cNomEmbarcacion.value;
 }

 function fGetiCveTipoServ(){
   return frm.iCveTipoServ.value;
 }

 function fGetcDscTipoServ(){
   return frm.cDscTipoServ.value;
 }

 function fGetcDscTipoEmbarcacion(){
   return frm.cDscTipoEmbarcacion.value;
 }

 function fGetiCveTipoNavega(){
   return frm.iCveTipoNavega.value;
 }

 function fGetcDscTipoNavega(){
   return frm.cDscTipoNavega.value;
 }

 function fGetdArqueoBruto(){
   return frm.dArqueoBruto.value;
 }

 function fGetcDscUnidadMedidaArqueoBruto(){
   return frm.cDscUnidadMedidaArqueoBruto.value;
 }

 function fGetdArqueoNeto(){
   return frm.dArqueoNeto.value;
 }

 function fGetcDscUnidadMedidaArqueoNeto(){
   return frm.cDscUnidadMedidaArqueoNeto.value;
 }

 function fGetdPesoMuerto(){
   return frm.dPesoMuerto.value;
 }

 function fGetcDscUnidadMedidaPesoMuerto(){
   return frm.cDscUnidadMedidaPesoMuerto.value;
 }

 function fGetdEslora(){
   return frm.dEslora.value;
 }

 function fGetdManga(){
   return frm.dManga.value;
 }

 function fGetcDscUnidadMedidaManga(){
   return frm.cDscUnidadMedidaManga.value;
 }

 function fGetdPuntal(){
   return frm.dPuntal.value;
 }

 function fGetcDscUnidadMedidaPuntal(){
   return frm.cDscUnidadMedidaPuntal.value;
 }

 function fGetlArtefacto(){
   return frm.lArtefacto.value;
 }

function fDameCadena(theObject, theValue){
  var cCadena = "";
  if (theObject.options)
    if (theObject.options.length > 0)
      for (var i = 0; i < theObject.options.length; i++)
        if (theObject.options[i].value == theValue){
          cCadena = theObject.options[i].text;
          break;
        }
  return cCadena;
}


function fSetPaisAbandera(lvalor) {
  frm.pais.value = lvalor;

 }







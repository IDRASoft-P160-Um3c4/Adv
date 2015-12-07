 // MetaCD=1.0
 // Title: pg10053015000.js
 // Description: Folder de solicitud de tramite por internet
 // Company: INFOTEC
 // Author: jperez
 
var frm;
var FRMTramites;
var FRMAsignacion;
var IREGISTRO = 0;
var entrar = 0;

fWrite(JSSource("IntCarpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pgINTSolicitud.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

function fDefPag(){ // Define la p�gina a ser mostrada
  fInicioPagina("F5F5F5"); 
  Estilo("A:Link","COLOR:#800000;font-family:Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
  Estilo("A:Visited","COLOR:#800000;;font-family: Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
  Estilo(".ETablaSTE","COLOR:WHITE;font-family:Verdana;font-size:8pt;font-weight:bold;");
  Estilo(".ETablaSTG","COLOR:WHITE;font-family:Verdana;font-size:8pt;font-weight:bold;background-color: #808080;text-align:center;");
  Estilo(".EEtiquetaCE","COLOR:#808080;font-family:Verdana;font-size:8pt;font-weight:normal;text-align:justify;");
  Estilo(".ESUP","COLOR:FFFFFF;font-family:Verdana;font-size:8pt;font-weight: Bold;");      
  InicioTabla("",0,"100%","100%","","",".1",".1");
     ITRTD("",0,"","45",""); 
     FTDTR();
     ITRTD("",0,"100%","118","center"); 
       InicioTabla("",0,"976","118","center","sctinternet.jpg",".1",".1","WHITE");
       ITRTD("EEtiquetaC",0,"500");
          SP();
         FITD("ESUP",0,"350");
          TextoSimple("AUTOTRANSPORTE FEDERAL <BR> SOLICITUD DE TR�MITES POR INTERNET");
         FITD("",0,"126","","","");
          Liga("<BR><BR>[Gu�a R�pida]","fOpenHelp();","Gu�a R�pida");
       FTDTR();
       ITRTD("",3,"100%","38","center");
         InicioTabla("",0,"100%","38","center","sctgris.jpg",".1",".1","WHITE");
         ITRTD("ETablaSTE",0,"","38","center");
           TextoSimple("Solicitud de Tr�mites por Internet"); 
         FTDTR();
         FinTabla();     
       FTDTR();
       FinTabla();
     FTDTR();  
     ITRTD("",0,"100%","100%","center","middle");
        InicioTabla("",0,"976","100%","center","",".1",".1","WHITE");
        ITRTD("EEtiquetaC",0,"100%","100%");
           fDefCarpeta( "Tipo de Tr�mite|Registro|" , 
                     "pgINTSol01FIEL.js|pgINTSol02FIEL.js|" , "INT" , "99%" , "99%", false);
        FTDTR();
        FinTabla();
     FTDTR();
     FinTabla(); 
     Hidden("ICVETRAMITE","");
     Hidden("ICONSECUTIVO","");
  fFinPagina();  
}

function fOnLoad(){ // Carga informaci�n al mostrar la p�gina.
  frm = document.forms[0];
  fPagFolder(1);
}

function fFolderOnChange( iPag ){ // iPag indica a la p�gina que se desea cambiar
	FRMTramites = fBuscaFrame("INT1");
   FRMAsignacion = fBuscaFrame("INT2");
   if(iPag == 2){
       entrar = 1;
		if(IREGISTRO == 0){
			fPagFolder(1);
			//alert("@i = " + getIREGISTRO());
			return false;
		}else{
			setIREGISTRO(0);
			FRMAsignacion.setValues({
				ICVETRAMITE: FRMTramites.getICVETRAMITE(), 
				ICONSECUTIVO: FRMTramites.getICONSECUTIVO(),
				ICVETIPOTRAMITE: FRMTramites.getICVETIPOTRAMITE(),
				LFINALIZADO: FRMTramites.getLFINALIZADO(),
				ICVETIPOPERMISIONA: FRMTramites.getICVETIPOPERMISIONA(),
				CTIPOPERMISIONARIO: FRMTramites.getCTIPOPERMISIONARIO()
			});
			FRMAsignacion.fNewTram();
			return true;
		}
	}
  else
  {
    //FRMTramites.setValues(FRMAsignacion.fGetGuardado());
    if(entrar == 1)
      FRMTramites.fBuscar();
  }
}

function getIREGISTRO(){
	return IREGISTRO;
}

function setIREGISTRO(iCons){
	IREGISTRO = iCons;
}

function fOpenHelp(){ //
	fAbreWindowHTML(false,cRutaAyuda + "TRAMXINTERNET.pdf",'no','no','yes','no','no',700,500,50,50);
}

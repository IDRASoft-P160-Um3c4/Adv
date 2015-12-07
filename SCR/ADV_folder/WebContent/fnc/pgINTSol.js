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
var cRFCRep = "";
var existeSolicitante=false;

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pINTSolicitud.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }else{
	    cTitulo = "Solicitud de trámites por internet";
  }
}

function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina("F5F5F5"); 
  Estilo("A:Link","COLOR:#800000;font-family:Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
  Estilo("A:Visited","COLOR:#800000;;font-family: Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
  Estilo(".ETablaSTE","COLOR:WHITE;font-family:Verdana;font-size:8pt;font-weight:bold;");
  Estilo(".ETablaSTG","COLOR:WHITE;font-family:Verdana;font-size:8pt;font-weight:bold;background-color: #808080;text-align:center;");
  Estilo(".EEtiquetaCE","COLOR:#808080;font-family:Verdana;font-size:8pt;font-weight:normal;text-align:justify;");
  Estilo(".ESUP","COLOR:808080;font-family:times new roman;font-size:8pt;font-weight: Bold;");      
  InicioTabla("",0,"100%","100%","","",".1",".1");
     ITRTD("",0,"","45",""); 
     FTDTR();
     ITRTD("",0,"100%","118","center"); 
       InicioTabla("",0,"976","118","center","sctinternet.jpg",".1",".1","WHITE");
       ITRTD("EEtiquetaC",0,"500");
          SP();
         FITD("ESUP",0,"350");
          TextoSimple("DIRECCIÓN GENERAL DE DESARROLLO CARRETERO <BR> SOLICITUD DE TRÁMITES POR INTERNET");
         FITD("ESUP",0,"126");
          //Liga("<BR><BR>[Guía Rápida]","fOpenHelp();","Guía Rápida");
       FTDTR();
       ITRTD("",3,"100%","38","center");
         InicioTabla("",0,"100%","38","center","sctgris.jpg",".1",".1","WHITE");
         ITRTD("ETablaSTE",0,"","38","center");
           TextoSimple("Solicitud de Trámites por Internet"); 
         FTDTR();
         FinTabla();     
       FTDTR();
       FinTabla();
     FTDTR();  
     ITRTD("",0,"100%","100%","center","middle");
        InicioTabla("",0,"976","100%","center","",".1",".1","WHITE");
        ITRTD("EEtiquetaC",0,"100%","100%");
           //fDefCarpeta("Tipo de Trámite|Registro|" ,"pgINTSol01FIEL.js|pgINTSol02FIEL.js|" , "INT" , "99%" , "99%", false);
           fDefCarpeta( "Tipo de Trámite|Registro|",
        	   "pgINTSol01FIEL.js|pgINTSol02FIEL.js|",
        	   "INT" , "99%" , "99%", false);
        FTDTR();
        FinTabla();
     FTDTR();
     FinTabla(); 
     Hidden("ICVETRAMITE","");
     Hidden("ICONSECUTIVO","");
  fFinPagina();  
}

function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  cRFCRep = window.parent.fGetRFCRep();
  fPagFolder(1);

}

function fFolderOnChange( iPag ){ // iPag indica a la página que se desea cambiar
    FRMTramites = fBuscaFrame("INT1");
    FRMAsignacion = fBuscaFrame("INT2");
    if(iPag == 2){
	entrar = 1;
	if(IREGISTRO == 0){
	    fPagFolder(1);
	    return false;
	}else{
	    setIREGISTRO(0);
	    
	    FRMAsignacion.setValues({
		ICONSECUTIVO: FRMTramites.getICVETRAMITE(), 
		ICVETRAMITE: FRMTramites.fGetCveTramite(),
		ICVEMODALIDAD: FRMTramites.fGetModalidad()
	    });
	    
	    //FRMAsignacion.setValues(FRMTramites.getICVETRAMITE(),FRMTramites.fGetCveTramite(),FRMTramites.fGetModalidad());
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
    return true;
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

function fGetRFCRep (){
    return cRFCRep;
}

function getExisteSolicitante(){
	return existeSolicitante;
}

function setExisteSolicitante(estado){
	existeSolicitante=estado;
}
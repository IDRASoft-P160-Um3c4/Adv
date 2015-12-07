// MetaCD=1.0
var frm;
var iPagAnt = 1;
var objTramiteInternet = null;
fWrite(JSSource("IntCarpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg111020111.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
 var objINTTramite = null;
 /*objINTTramite = {
	     //ICVETRAMITE: 1,
	     //ICVETIPOPERMISIONA: 1,
	     //ICVETIPOTRAMITE: 1,
	     //TSREGISTRO: "2012-08-21",
	     //TSFIN: "2012-09-01",
	     //ICVEDEPARTAMENTO: 70,
	     //CDSCTIPOTRAMITE: "Tramite de prueba",
	     //ICVETIPOTRAPROD: 0
 };
*/
function fDefPag(){
  JSSource("jquery.js");
  JSSource("jquery-ui.js");
  Estilo("A:Link","COLOR:black;font-family:Verdana;font-size:9pt;font-weight:normal;TEXT-DECORATION:none");
  Estilo("A:Visited","COLOR:black;font-family: Verdana;font-size:9pt;font-weight:normal;TEXT-DECORATION:none");
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple("Recepción de solicitudes por Internet");
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta( "Recepcion solicitudes|Relacionar solicitantes|",
                    "pg111020112.js|pg111020113.js|",
                    "PAG" , "99%" , "99%", true);
    FTDTR();
  FinTabla();
  Hidden("lLiga",false);
	cGPD += '<div id="INTdialog"></div>';
	cGPD += '<div id="INHABILITACION"></div>';
  fFinPagina();
}

function fOnLoad(){ 
  frm = document.forms[0];
  fPagFolder(1);
	$("#INTdialog").dialog(
		{
		    autoOpen:false,
		    resizable:false,
		    width: 600,
		    close: function(event, ui){
		    	FRMTit = fBuscaFrame("FRMTitulo");
        		FRMTit.fShowInternet();
		    }
	        }
	)
}

function fResultado(aRes, cId,cError){
    fListadoTramInt(cId,cError,aRes);
}

function fFolderOnChange( iPag ){
    Pag2 = fBuscaFrame("PAG2");
    if(iPag==2){
        if(frm.lLiga.value == "true" || frm.lLiga.value == true){
            frm.lLiga.value = false;
            //return true;
        }else 
    	return false;
    }
    return true;    
}

function fIntegraCampo(cCampoCop,cVal1,cVal2){
    FRM1 = fBuscaFrame("PAG1");
    FRM2 = fBuscaFrame("PAG2");
    frm.lLiga.value = true;
    FRM2.fSetCampo(cCampoCop,cVal1,cVal2);
    fPagFolder(2);
}

//function fLINKBUTTON(obj){}


// MetaCD=1.0
// Title: pg111010011A.js
// Description: JS "Catálogo" de la entidad GRLPersona
// Company: Tecnología InRed S.A. de C.V.
// Author: ahernandez<dd>LSC. Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado = "";
var frm, FRMObj;
var lEditPersona = true;
var lEditando = false;
var aDato;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
	cTitulo = "AGREGAR APROVECHAMIENTO IRREGULAR";
	 fSetWindowTitle();
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","100%","","");
    ITRTD("EEtiquetaC",0,"","","","");
      InicioTabla("ETablaInfo",0,"0","","center","",1);
        ITRTD("ETablaST",12,"","","center");
          cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"AGREGAR APROVECHAMIENTO IRREGULAR":cTitulo;
          TextoSimple(cTitulo);
        FTDTR();
        
        ITR();
        
        TDEtiCampo(false,"EEtiqueta",0," Autopista :","cAutopista","",70,250," autopista ","fMayus(this);");
        FTR();
        ITR();
        TDEtiCampo(false,"EEtiqueta",0," Tipo :","cTipo","",70,250," tipo ","fMayus(this);");
        FTR();
        ITR();
        TDEtiCampo(false,"EEtiqueta",0," Cadenamiento :","cCadenamiento","",15,20," cadenamiento ","fMayus(this);");
        FTR();
        ITR();
        TDEtiTexto("EEtiqueta",0,"","ej. Latitud: 20.734377 Longitud: -103.581310","ECampo",0);
        FTR();
        ITR("","","","left");
      
        TDEtiCampo(false,"EEtiqueta",0," Latitud :","cLatitud","",15,20," cadenamiento ","fMayus(this);");
        ITR("","","","left");
        FTR();
        TDEtiCampo(false,"EEtiqueta",0," Longitud :","cLongitud","",15,20," cadenamiento ","fMayus(this);");

        FTR();
        ITR();
        TDEtiAreaTexto(false,"EEtiqueta",1,"Comentario:",60,10,"cComentario","","Comentario:","","fMayus(this);",'onkeydown="fMxTx(this,1000);"',true,true,true,"ECampo",0);
        FTR();
        
      FinTabla();
      
      
      

      ITRTD("",0,"","40","center","bottom");
       IFrame("IPanel1A","95%","34","Paneles.js");
      FTDTR();
      
   FTDTR();
  FinTabla();
  Hidden("hdSelect");
  Hidden("hdLlave");
  Hidden("iCveUsuario",fGetIdUsrSesion());
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  FRMPanel = fBuscaFrame("IPanel1A");
  FRMPanel.fSetControl(self,cPaginaWebJS);
  //FRMPanel.fHabilitaReporte(true);
  FRMPanel.fShow("Tra,");
  fDisabled(true);
  FRMPanel.fSetTraStatus("AddOnly");
  frm.hdBoton.value="Primero";
}
// LLAMADO al JSP específico para la navegación de la página
function fNavega(){


}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert (cError);
  }

  if(cId=="Guardar" && cError == ""){
	  
	  if(top.opener){
		  top.opener.fNavega();
	  }
	  
	  if(top.opener)
	    	 top.close();
  }
 }
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo(){
  lEditando = true;
  FRMPanel.fSetTraStatus("UpdateBegin");
  fDisabled(false);
  fBlanked();
}

// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar(){	
	msg = fValidaCoords(frm.cLatitud.value,frm.cLongitud.value);
	
	if(msg!="")
		return fAlert(msg);
	
	fEngSubmite("pg117010050.jsp","Guardar");  
}


// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar(){
  FRMPanel.fSetTraStatus("AddOnly");
  fDisabled(true);
}


function fValidaCoords(lati,longi){
	var latiExpr =/^-?(\d{1,3})\.{1}\d{1,6}$/;
	
	if((lati!=""&&longi=="")||(longi!=""&&lati==""))
		return "\n -Debe proporcionar ambas coordenadas.";
	else
		if(lati!=""&&longi!="")
		{	
			if(!(lati.match(latiExpr)&&longi.match(latiExpr))){
					return "\n -Las coordenadas deben tener el formato '20.734377'.";
			}
		}
 
	return "";
}
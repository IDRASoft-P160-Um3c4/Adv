// MetaCD=1.0 
// Title: IntConfirmaAlta.js
// Description: JS de confirmaci�n
// Company: SCT 
// Author: JESR
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
var iRow = 0;
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){ 
	
} 
// SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
function fDefPag(){ 
	  fInicioPagina("WHITE"); 
	  Estilo("A:Link","COLOR:#800000;font-family:Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
	  Estilo("A:Visited","COLOR:#800000;;font-family: Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
	  Estilo(".ETablaSTE","COLOR:WHITE;font-family:Verdana;font-size:8pt;font-weight:bold;");
	  Estilo(".ETablaSTG","COLOR:WHITE;font-family:Verdana;font-size:8pt;font-weight:bold;background-color: #808080;text-align:center;");
	  Estilo(".EEtiquetaCE","COLOR:#808080;font-family:Verdana;font-size:8pt;font-weight:normal;text-align:justify;");
	  Estilo(".ESUP","COLOR:FFFFFF;font-family:Verdana;font-size:8pt;font-weight: Bold;");   
	InicioTabla("",0,"976","","","","1");//InicioTabla("",0,"100%","100%","center","",".1",".1");
	  ITRTD("EEtiquetaC",7,"","","center");//ITRTD("ESTitulo",2,"","1","","center");
	       InicioTabla("",0,"976","118","center","sctinternet.jpg",".1",".1","WHITE");
	       ITRTD("EEtiquetaC",0,"500");
	          SP();
	         FITD("ESUP",0,"350");
	          TextoSimple("PUERTOS Y MARINA MERCANTE <BR> SOLICITUD DE TR�MITES POR INTERNET");
	         FITD("",0,"126","","","");
	       FTDTR();
	       ITRTD("",3,"100%","38","center");
	         InicioTabla("",0,"100%","38","center","sctgris.jpg",".1",".1","WHITE");
	         ITRTD("ETablaSTE",0,"","38","center");
	         TextoSimple("Solicitud de Tr�mite por Internet ante la D.G.P.M.M."); 
	         FTDTR();
	         FinTabla();     
	       FTDTR();
	       FinTabla();
	  FTDTR();
	  ITRTD("EEtiquetaC",0,"100%","","center"); 
	    cCadFir = top.opener.fGetCCADFIRMADA();    
		InicioTabla("",0,"900","100%","center","",".1",".1"); 
			ITRTD("EEtiquetaL",0,"80%","100%","Left","top");
			cTxt = '<BR><BR>Ciudadano o Representante Legal: ' + top.opener.fGetCNOMPERSONA() +
			'.<BR>N�mero de Tr�mite: ' + top.opener.fGetICVETRAMITEINT() + 
			'.<BR>Consecutivo: ' + top.opener.fGetICONSECUTIVO() +
			'.<BR>Tipo de Tr�mite: '+ top.opener.getCTIPOPERMISIONARIO() +" - " + top.opener.getCDSCTIPOTRAMITE() + 
			'.<BR><P Align="JUSTIFY">Declaro bajo protesta de decir la verdad, que la documentaci�n presentada para acreditar el cumplimiento de todos y cada uno de los requisitos son aut�nticos y ciertas las car�cteristicas del veh�culo que en su caso se describe, dejando a salvo la facultad de la Secretar�a de Comunicaciones y Transportes para constatar lo manifestado, aceptando que de comprobarse lo contrario dar� motivo a la revocaci�n del permiso que en su caso se genere. </P>'+
			'<BR>Cadena Original:<BR><BR><P Align="JUSTIFY">' + top.opener.fGetCCADORIGINAL() +
			'</p><BR> Firmado Digital del Ciudadano o Representante Legal:<BR><BR><P Align="JUSTIFY">' + cCadFir.substring(0,100) + "<BR>" + cCadFir.substring(100,200) + "<BR>" + cCadFir.substring(200) + "</p>";
			TextoSimple(cTxt);
			FTDTR();

		FinTabla();
	  FTDTR();		
    FinTabla();
  fFinPagina(); 
} 
function fOnLoad(){ 
  frm = document.forms[0];
  top.document.title = 'SCT :: Generaci�n de Recibo de Tr�mite'; 
  self.focus();
  window.print();   
} 

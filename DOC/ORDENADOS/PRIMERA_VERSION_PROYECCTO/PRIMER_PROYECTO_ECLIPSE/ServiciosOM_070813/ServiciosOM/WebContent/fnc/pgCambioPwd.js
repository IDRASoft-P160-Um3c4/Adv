// MetaCD=1.0 
 // Title: pgCambioPwd.js
 // Description: JS "Catálogo" de la entidad SEGUSUARIO
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: JESR
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm;
 var iVOportunidad = 0; 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pgCambioPwd.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
 } 
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){ 
   fInicioPagina(cColorGenJS); 
     Estilo("A:Link","COLOR:WHITE;font-family:Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
     Estilo("A:Visited","COLOR:WHITE;font-family: Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");   
   InicioTabla("",0,"100%","100%","","","1"); 
     ITRTD("ESTitulo",0,"100%","1","center"); 
       TextoSimple("CAMBIO DE CONTRASEÑA"); 
     FTDTR(); 
     ITRTD("",0,"","100%","top"); 
     InicioTabla("",0,"100%","","center"); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"60%","","","",1); 
           ITRTD("ETablaST",5,"","25","center"); 
             Liga("[Para Realizar el cambio de Contraseña de Click Aquí]","fCambio();","Cambio de Contraseña");             
           FTDTR(); 
         FinTabla(); 
       FTDTR();
       FinTabla(); 
     FTDTR(); 
     FinTabla(); 
   fFinPagina(); 
 } 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   frm.hdBoton.value="Primero";
 } 

 function fCambio(){
	    cUsr = top.fGetUsrName();
	    if(cUsr.indexOf(",") > -1)
	       cUsr = cUsr.substring(0,cUsr.indexOf(","));
        open(cRutaADMSEGPWD+'?iCveUsuario='+top.fGetUsrID()+'&cUsuario='+cUsr,'','dependent=yes,hotKeys=no,location=no,menubar=no,personalbar=no,resizable=no,scrollbars=no,status=yes,titlebar=no,toolbar=no,width=900,height=590,screenX=50,screenY=50');
 }
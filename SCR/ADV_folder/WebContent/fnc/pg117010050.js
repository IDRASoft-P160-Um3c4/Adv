// MetaCD=1.0
 // Title: pg111020020.js
 // Description: JS "Cat�logo" de la entidad TRARegEtapasXModTram
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: ocastrejon; lequihua; iCaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 

 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
 function fBefLoad(){
	 cTitulo= "APROVECHAMIENTOS IRREGULARES";
   fSetWindowTitle();
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);

  
   InicioTabla("",0,"100%","","","","1");
     ITRTD("ESTitulo",0,"100%","","center");
       TextoSimple("Aprovechamientos Irregulares");
     FTDTR();
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","175","center","top");
         IFrame("IListado","95%","170","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");
       LigaNombre("Aregar Aprovechamiento Irregular","fShowIrregulares();","","");
       FTDTR();
       FinTabla();
     FTDTR();
     ITRTD("",0,"","","top");
     
     FTDTR();
     
     FinTabla();
     Hidden("iCveUsuario",fGetIdUsrSesion());
     Hidden("hdLlave");
     Hidden("hdSelect");
     Hidden("hdCveIrr");
    // Hidden("hdBoton","Borrar");
     
   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   
   FRMListado = fBuscaFrame("IListado");
   FRMListado.fSetControl(self);
   
   FRMListado.fSetTitulo("Autopista, Tipo, Cadenamiento,      Comentario     , Georeferencia, Acci�n,");
   FRMListado.fSetCampos("1,2,3,4,8,9,");
   FRMListado.fSetAlinea("center,center,center,center,center,center,center,");
   fDisabled(false);
   frm.hdBoton.value="Primero";
   fTraeOficDepUsrLocal();
   fNavega();
 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
	 fEngSubmite("pg117010050.jsp","Listado");
 }
 
 
 // RECEPCI�N de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave,cEtapas){
   if(cError=="Guardar")
     fAlert("Existi� un error en el Guardado!\n");
   else if(cError=="Borrar")
     fAlert("Existi� un error en el Borrado!");
   else if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   else if (cError != "")fAlert(cError);
   
   if(cId == "Listado" && cError==""){

	   alert(aRes[0]);
	   
	   for(var i =0;i<aRes.length;i++){
		   
		   if(aRes[i][6]!=""&&aRes[i][7]!="")
			   aRes[i].push("<font color=blue>Ir...</font>");
		   else
			   aRes[i].push("No proporcionado.");
		   
		   aRes[i].push("<font color=blue>Eliminar</font>");
	   }
	   
	   //alert(aRes);
	   
	   	FRMListado.fSetListado(aRes);
		FRMListado.fShow();
		FRMListado.fSetLlave(cLlave);
   }
   
   if(cId == "Borrar" && cError==""){
	   fNavega();
   }
   
 }

 function fBorrar(){

	 if(top.opener.top.usrGpoId!=5)//grupoUsr dgdc
		 fAlert("\nNo tiene permiso para eliminar.");
	 else{
		 frm.hdBoton.value="Borrar";
		 fEngSubmite("pg117010050.jsp","Borrar");
	 }	 
 }
 // LLAMADO desde el Listado cada vez que se selecciona un rengl�n
 function fSelReg(aDato, iCol){
	 
   if(iCol==4&&aDato[6]!=""&&aDato[7]!="")
	 window.open("https://maps.google.com/maps?q="+aDato[6]+","+aDato[7]); 
 
   if(iCol==5){
	 frm.hdCveIrr.value=aDato[0];
	 fBorrar();
   }
	   
   

 }
 // FUNCI�N donde se generan las validaciones de los datos ingresados
 
 function fTraeOficDepUsrLocal(){
   frm.hdLlave.value = "";
   frm.hdSelect.value = "SELECT " +
				"ICVEOFICINA,ICVEDEPARTAMENTO " +
				"FROM GRLUSUARIOXOFIC " +
				"where ICVEUSUARIO = " + frm.iCveUsuario.value;

   fEngSubmite("pgConsulta.jsp","OficDepUsrLocal");
 }
 
 function fShowIrregulares(){
		fAbreSubWindowSinPermisos("pg117010060", "750", "425");
 }
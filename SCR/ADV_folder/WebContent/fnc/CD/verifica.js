 // MetaCD=1.0
var frm;
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
}
function fDefPag(){ // Define la página a ser mostrada
  //fInicioPagina("BLACK","Sistema Institucional de Puertos y Marina Mercante", true);
	fInicioPagina("BLACK","Aprovechamiento del Derecho de Vía", true);
     InicioTabla("",0,"100%","100%","center","verifica.gif",".1","1","");
        ITRTD("ESTitulo",2,"","20");
           TextoSimple('ACCESO AL SISTEMA');
        FTDTR();
        ITRTD();
        	InicioTabla("",0,"100%","60","center","",".1",".1","");
        		ITRTD();SP();FTDTR();
        		ITRTD("ESUsrPwd",2,"","","right");
           		TextoSimple("Usuario:");
        		FITD("ECampo",2);
           		Text(true,"cUsuario","",12,30,"Nombre de Usuario...","","","",true);
        		FTDTR();
        		ITRTD("ESUsrPwd",2,"","","right");
        	   	TextoSimple("Contraseña");
        		FITD("ECampo",2);
           		Pwd(true,"cContrasena","",12,30,"Introduzca la contraseña","",'onKeyPress="fCheckReturn(event);"',"",true);
        		FTDTR();
        		ITRTD();SP();FTDTR();
        	FinTabla();
        FTDTR();
        ITRTD();
        	InicioTabla("",0,"100%","35","center");
		        ITRTD("",0,"40%");SP();
    		    //FITD("",0,"56","","center");
        	  	
        		FITD("",0,"");SP();
        		FITD("",0,"40%","","center");
           		//BtnImg("BtnLimpiar","restaurar","document.forms[0].reset();","Restaura los campos...");
        		BtnImg("BtnAceptar","aceptarb","fAceptar();","Acceso al Sistema...");
        		//FITD("",0,"56");SP();
        		FTDTR();
     			FinTabla();
     		FTDTR();
     	FinTabla();
  fFinPagina2();
}
function fOnLoad(){
}
function fValidaTodo(){
   cMsg = fValElements();
   if(cMsg != ''){
      fAlert(cMsg);
      return false;
   }
   return true;
}
function fCheckReturn(evt){
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if(charCode == 13){
        fAceptar();
    }
}
function fAceptar() {
    if (fValidaTodo() == true){
      top.opener.fUsrPwdAceptar(frm.cUsuario.value,frm.cContrasena.value, top.fGetUsrID());
      top.close();
    }
}
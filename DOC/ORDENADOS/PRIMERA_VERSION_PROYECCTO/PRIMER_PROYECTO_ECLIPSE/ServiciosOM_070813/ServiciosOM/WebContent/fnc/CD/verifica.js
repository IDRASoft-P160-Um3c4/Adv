 // MetaCD=1.0
var frm;
function fBefLoad(){ // Carga informaci�n antes de que la p�gina sea mostrada.
}
function fDefPag(){ // Define la p�gina a ser mostrada
  fInicioPagina("BLACK","Sistema Institucional de Puertos y Marina Mercante", true);
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
           		Text(true,"cUsuario","",12,12,"Nombre de Usuario...","","","",true);
        		FTDTR();
        		ITRTD("ESUsrPwd",2,"","","right");
        	   	TextoSimple("Contrase�a");
        		FITD("ECampo",2);
           		Pwd(true,"cContrasena","",12,12,"Introduzca la contrase�a","",'onKeyPress="fCheckReturn(event);"',"",true);
        		FTDTR();
        		ITRTD();SP();FTDTR();
        	FinTabla();
        FTDTR();
        ITRTD();
        	InicioTabla("",0,"100%","35","center");
		        ITRTD("",0,"40%");SP();
    		    //FITD("",0,"56","","center");
        	  	BtnImg("BtnAceptar","aceptarb","fAceptar();","Acceso al Sistema...");
        		FITD("",0,"");SP();
        		FITD("",0,"40%","","center");
           		BtnImg("BtnLimpiar","restaurar","document.forms[0].reset();","Restaura los campos...");
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
      top.opener.fUsrPwdAceptar(frm.cUsuario.value,frm.cContrasena.value);
      top.close();
    }
}

// MetaCD=1.0
fWrite(JSSource("pg"+iNDSADM+"00004.js"));
fWrite(JSSource("pg"+iNDSADM+"00006.js"));
function fBefLoad(){ // Carga informaci�n antes de que la p�gina sea mostrada.
}

function fDefPag(){ // Define la p�gina a ser mostrada
   fInicioPagina("D6D6CE");
  // Definici�n del �rea de trabajo
   InicioTabla("",1,"100%","100%","center");
     ITRTD("",3,"100%","30");
        fDefBarra();
     FTDTR();
     ITRTD("",0,"0","100%","","top");
       fDefMenu();
     FITD("","","100%","","center","top");
  // Definici�n de la p�gina
       InicioTabla("",0,"100%","100%","","","","","FFFFFF");
         ITRTD("",0,"","","center");
           InicioTabla("",1);
             ITRTD();
               Img("inicio.gif","P�gina de Inicio del Sistema");
             FTDTR();
           FinTabla();
           Marquee("Mensajes del Sistema","MRQ","center","10","up","350","100");
         FTDTR();
       FinTabla();
     FTDTR();
   FinTabla();
   fFinPagina();
}

function fOnLoad(){ // Carga informaci�n al mostrar la p�gina.
   fLoadMenu();
//   fShowMenu();
}

// MetaCD=1.0
fWrite(JSSource("pg"+iNDSADM+"00004.js"));
fWrite(JSSource("pg"+iNDSADM+"00006.js"));
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
}

function fDefPag(){ // Define la página a ser mostrada
   fInicioPagina("D6D6CE");
  // Definición del área de trabajo
   InicioTabla("",1,"100%","100%","center");
     ITRTD("",3,"100%","30");
        fDefBarra();
     FTDTR();
     ITRTD("",0,"0","100%","","top");
       fDefMenu();
     FITD("","","100%","","center","top");
  // Definición de la página
       InicioTabla("",0,"100%","100%","","","","","FFFFFF");
         ITRTD("",0,"","","center");
           InicioTabla("",1);
             ITRTD();
               Img("inicio.gif","Página de Inicio del Sistema");
             FTDTR();
           FinTabla();
           Marquee("Mensajes del Sistema","MRQ","center","10","up","350","100");
         FTDTR();
       FinTabla();
     FTDTR();
   FinTabla();
   fFinPagina();
}

function fOnLoad(){ // Carga información al mostrar la página.
   fLoadMenu();
//   fShowMenu();
}

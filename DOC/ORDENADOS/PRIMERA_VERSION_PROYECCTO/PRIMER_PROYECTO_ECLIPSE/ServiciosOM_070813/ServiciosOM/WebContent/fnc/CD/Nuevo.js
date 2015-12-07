// MetaCD=1.0
var frm;
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
}

function fDefPag(){ // Define la página a ser mostrada
   fInicioPagina("D6D6CE");
   // Coloque aquí la definición de la página
   fFinPagina();
}

function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
//   fEngSubmite('Archivo','Identificador'); 
}

function fResultado(aRes, cId){ // Recibe el resultado en el Vector aRes.
//  if(cId == 'Identificador'){      
//  }
}

function fValidaTodo(){
   cMsg = fValElements();

   if(cMsg != ''){
      fAlert(cMsg);
   }
}
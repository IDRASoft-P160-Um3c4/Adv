// MetaCD=1.0
var frm;
function fBefLoad(){ // Carga informaci�n antes de que la p�gina sea mostrada.
}

function fDefPag(){ // Define la p�gina a ser mostrada
   fInicioPagina("D6D6CE");
   // Coloque aqu� la definici�n de la p�gina
   fFinPagina();
}

function fOnLoad(){ // Carga informaci�n al mostrar la p�gina.
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
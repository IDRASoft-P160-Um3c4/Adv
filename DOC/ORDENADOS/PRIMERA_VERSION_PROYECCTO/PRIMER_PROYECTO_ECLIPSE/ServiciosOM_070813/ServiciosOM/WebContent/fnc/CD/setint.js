var iPID;
var iCheca;
function fPag(){
  document.write('<html><body onLoad="fInicia();"><form method="POST"></FORM></body></HTML>');
}
function fInicia(){
  if(lDesarrollo)
     iPID = setInterval('fVerAnswer();',15000);
  else
     iPID = setInterval('fVerAnswer();',60000);
}
function fLetCopy(){
  clearInterval(iPID);
  top.fLoadIneng();
  iPID = setInterval('fVerAnswer();',1000);
}
function fVerAnswer(){
    if(""+top.FRMMI.fPag == "undefined"){
      iCheca++;
      if(iCheca > 2){
         iCheca = 0;
         clearInterval(iPID);
         top.document.body.rows="97%,0,3%";
         try{
            top.fEndThreat();
         }catch(e){}
         top.fDelThreats();
         //top.fLoadIneng();
         alert('  '+
            ' \n \n Existi� una inconsistencia en la obtenci�n de datos del servidor.'+
            ' \n La referencia se muestra en la p�gina desplegada atr�s de este mensaje.'+
            ' \n \n Al cerrar este mensaje (dar click en el bot�n ACEPTAR) el sistema '+
            ' \n "REESTABLECER� LA CONEXI�N" en 5 segundos, despu�s de ello, '+
            ' \n \n "CONTIN�E" con la �ltima acci�n que realiz�.');
        iPID = setInterval('fLetCopy();',5000);
      }
    }else{
      iCheca = 0;
      clearInterval(iPID);
      if(lDesarrollo)
         top.document.body.rows="2,0,100%";
      else
         top.document.body.rows="0,0,100%";
      fInicia();
    }
}
function fGetRutaProg(){
  return cRutaProg;
}

/*function fVerAnswer(){
    if(""+top.FRMMI.fPag == "undefined"){
      clearInterval(iPID);
      top.document.body.rows="97%,0,3%";
      top.fEndThreat();
      top.fDelThreats();
      top.fLoadIneng();
      alert(' Existe un Error en la obtenci�n de datos del servidor (Comunicaciones).'+
            ' \n El error se muestra en la p�gina desplegada atr�s de este mensaje.'+
            ' \n \n IMPORTANTE: Favor de entrar nuevamente al proceso para verificar '+
            ' \n \n que su transacci�n se haya efectuado, en caso de no haberse aplicado'+
            ' \n \n deber� realizar nuevamente su acci�n..'+
            ' \n \n Al cerrar este mensaje el sistema "INTENTAR� REESTABLECER LA CONEXI�N"'+
            ' \n en aproximadamente 3 segundos, le agradecemos su paciencia.');
      iPID = setInterval('fVerAnswer();',3000);
    }else{
      if(lDesarrollo)
         top.document.body.rows="5,2,100%";
      else
         top.document.body.rows="0,0,100%";
      clearInterval(iPID);
      fInicia();
    }
}
function fGetRutaProg(){
  return cRutaProg;
}*/

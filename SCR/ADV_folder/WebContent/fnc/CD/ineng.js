var aRes = new Array();
var cQryStr;
// Utilitarios
function fPag(){
  document.write(fDefPag(""));
}
// Funciones hacia el servidor
function fEngSubmite(cPaginaM, cIdM, cParametro){
  lContinua=false;
  if(cIdM == "CerrarSesion"){
     lContinua=true;
  }else{
    lContinua=fVerTpoLocal();
  }
  if(lContinua==true){
   form = document.forms[0];
   cQryStr = Hidden("cId",cIdM);
   cQryStr += Hidden("cNombreFRM",self.name);
   if(cParametro)
      cQryStr += Hidden("cParametro",cParametro);
   else{
      for (i=0; i<form.elements.length;i++){
        obj = form.elements[i];
          if (obj.type == 'file' ||
          obj.type == 'checkbox' ||
          obj.type == 'password' ||
          obj.type == 'radio' ||
          obj.type == 'text' ||
          obj.type == 'hidden' ||
          obj.type == 'textarea' ||
          obj.type == 'select-one' ||
          obj.type == 'select-multiple'){
             cQryStr += Hidden(obj.name,obj.value);
          }
      }
   }
   if(form.hdBoton)
     form.hdBoton.value = "";
   if(!top.fAddThreat(cPaginaM,fDefPag(cQryStr))){
     alert('Mensaje del CD:\n El Threat: ' + cPaginaM + ' Id: ' + cIdM + ' del frame: ' + self.name + ' no pudo ser afregado a la pila');
   }
   top.fRunIniThreat();
  }
  return lContinua;
}
// Función que realiza la verificación
function fVerTpoLocal(){
  iTpoLocalIneng = top.fGetTiempoActualLocal();
  if(iTpoLocalIneng == 0){
     return true;
  }else{
    iTpoVerLocal = top.fGetTiempoVerificacion() * 1000 * 60;
    dtHoraLocalIneng = new Date();
    if((iTpoVerLocal + iTpoLocalIneng) < dtHoraLocalIneng.getTime()){
    	//fSetNuevoTpoLocal();
      if((wVerifica != null) && (!wVerifica.closed))
      	wVerifica.focus();
      else
      	fAbreSubVerifica(true,"CD/verifica","no","no","no","no",175,150,100,100,'wndVerifica');
      return false;
    }
    fSetNuevoTpoLocal();
    return true;
  }
}
function fAbreSubVerifica(lModalM,cNombreM,cMenubarM,cResizableM,cScrollbarsM,cStatusM,cAnchoM,cAltoM,iX,iY,cNomVentana){ // 5000-Abre una Nueva Ventana (.js) con un FRMMI propio (Ventana Transaccional)
    if((wVerifica != null) && (!wVerifica.closed))
      wVerifica.focus();
    else{
      cParametrosM = 'dependent=yes,hotKeys=no,location=no,menubar='+cMenubarM+',personalbar=no,resizable='+cResizableM+',scrollbars='+cScrollbarsM+',status='+cStatusM+',titlebar=no,toolbar=no,width='+cAnchoM+',height='+cAltoM;
      wVerifica = window.open(cPagNva+'?cPagina='+cPagIni+'.js&cPagNva='+cNombreM+'.js',cNomVentana,cParametrosM);
      wVerifica.creator = self;
      if(iX); else iX =  (screen.availWidth - cAnchoM) / 2;
      if(iY); else iY =  (screen.availHeight - cAltoM) / 2;
      wVerifica.moveTo(iX, iY);
    }
}
function fSetNuevoTpoLocal(){
   dtHoraLocalIneng = new Date();
   top.fSetTiempoActualLocal(dtHoraLocalIneng.getTime());
}

function fUsrPwdAceptar(cUsr,cPwd, lastUsrId) {
      fSetNuevoTpoLocal();
      fEngSubmite('pgUsrPwdVer.jsp','inengUsrPwdVer',cUsr+"/"+cPwd+"/"+lastUsrId);
}

function fSalirTotal(){
    frm = document.forms[0];
    if(!frm)
      if(top && top.FRMCuerpo && top.FRMCuerpo.FRMTitulo)
        frm = top.FRMCuerpo.FRMTitulo.document.forms[0];
    if(frm){
      frm.action = 'index.jsp';
      frm.target = '_top';
      frm.submit();
    }else
      top.location = top.cRutaSesionExpirada;
}
// Funciones desde el servidor
function fEngResultado(cFrame,cId,cP1,cP2,cP3,cP4,cP5,cP6,cP7,cP8,cP9,cP10){
//   fPag();
   top.fEndThreat();
   frmcpo = window.parent.FRMCuerpo;
   if(cFrame != ''){
      frmcpo = fBuscaFrame(cFrame, top);
   }
   if(frmcpo != ''){
     if(frmcpo.fResultado){
      if(cId=="inengUsrPwdVer"){
         if(aRes[0] == "ineng-1"){
           alert("Mensaje de Seguridad: \n\n - Favor de realizar nuevamente la acción...");
           fSetNuevoTpoLocal();
         }else{
            alert("Mensaje de Seguridad: \n\n - Acceso Incorrecto. Será redireccionado fuera del sistema!");
            fSalirTotal();
         }
      }else{
         frmcpo.fResultado(aRes,cId,cP1,cP2,cP3,cP4,cP5,cP6,cP7,cP8,cP9,cP10);
      }
      if(cP1){
        if(cP1=="ErrorSesion"){
          alert("Usted solo puede tener UNA SESIÓN abierta, por \nsu seguridad se REINICIARÁN todas las sesiones \nabiertas con su usuario.");
          fSalirTotal();
        }
      }
     }
   }else{
     alert('Mensaje del CD:\n El destino de datos: ' + cFrame + ' no ha podido ser localizado!');
   }
   top.fRunThreat();
}
function fArrayTester(iRengs,iCols){
  for(iAT=0;iAT<iRengs;iAT++){
     aCols = new Array();
     for(iCT=0;iCT<iCols;iCT++){
        aCols[iCT]=iCT;
     }
     aRes[iAT]=aCols;
  }
  return aRes;
}

function fBuscaFrame(cFrameM){
  try{
  aFramesM = new Array();
  lCont = true; iArray = 0;
  iPosMax = fVerFrames(aFramesM, top, 0);
  while(lCont){
     aTmp = aFramesM[iArray];
     if(aTmp[1] == cFrameM){
       return aTmp[0];
     }
     if(aTmp[2] == true){
        iPosMax = fVerFrames(aFramesM, aTmp[0], iPosMax);
     }
     iArray++;
     if(iArray >= iPosMax){
       lCont = false;
     }
  }
  }catch(e){}
  return '';
}

function fArrayFrame(){
  aFramesM = new Array();
  lCont = true; iArray = 0;
  iPosMax = fVerFrames(aFramesM, top, 0);
  while(lCont){
     aTmp = aFramesM[iArray];
     if(aTmp[2] == true){
        iPosMax = fVerFrames(aFramesM, aTmp[0], iPosMax);
     }
     iArray++;
     if(iArray >= iPosMax){
       lCont = false;
     }
  }
  return aFramesM;
}

function fVerFrames(aFramesM, frmM, iPosM){
   for(i=0; i<frmM.frames.length; i++){
      if(frmM.frames[i].frames.length > 0){
         aFramesM[iPosM] = [frmM.frames[i],frmM.frames[i].name,true];
      }else{
         aFramesM[iPosM] = [frmM.frames[i],frmM.frames[i].name,false];
      }
      iPosM = iPosM + 1;
   }
   return iPosM;
}

// Focus sobre el primer elemento de la pantalla
function fLoading(){
    fOnFocus();
    if ("captureEvents" in document) {
      document.captureEvents(Event);
    }
    if(navigator.userAgent.indexOf('MSIE') != -1)
       document.onkeypress = fKeyPressIE
    fOnLoad();
}
function fKeyPressIE(){
  lSelf=true;
  try{
    charCode = event.keyCode;
    if(charCode==11){ // Nuevo Ctrl K
      if(cPaginaWebJS=='Paneles.js'){
        self.fActual(frm.Nuevo,'Nuevo');
        FRMCtrl.fOnFocus();
        lSelf=false;
      }
      if(cPaginaWebJS=='Filtros.js' || cPaginaWebJS=='Listado.js'){
        if((FRMCtrl) && (FRMCtrl.FRMPanel)){
          FRMCtrl.FRMPanel.fActual(FRMCtrl.FRMPanel.document.forms[0].Nuevo,'Nuevo');
          FRMCtrl.fOnFocus();
        }
        lSelf=false;
      }
      if(lSelf==true){
        if(FRMPanel){
          FRMPanel.fActual(FRMPanel.document.forms[0].Nuevo,'Nuevo');
          self.fOnFocus();
        }
      }
    }
    if(charCode==7){ // Guardar Ctrl G
      if(cPaginaWebJS=='Paneles.js'){
        FRMCtrl.fOnFocus();
        self.fActual(frm.Guardar,'Guardar');
        FRMCtrl.fOnFocus();
        lSelf=false;
      }7
      if(cPaginaWebJS=='Filtros.js' || cPaginaWebJS=='Listado.js'){
        if((FRMCtrl) && (FRMCtrl.FRMPanel)){
          FRMCtrl.fOnFocus();
          FRMCtrl.FRMPanel.fActual(FRMCtrl.FRMPanel.document.forms[0].Guardar,'Guardar');
          FRMCtrl.fOnFocus();
        }
        lSelf=false;
      }
      if(lSelf==true){
        if(FRMPanel){
          self.fOnFocus();
          FRMPanel.fActual(FRMPanel.document.forms[0].Guardar,'Guardar');
          self.fOnFocus();
        }
      }
    }
    if(charCode==20){ // Modificar Ctrl t
      if(cPaginaWebJS=='Paneles.js'){
        self.fActual(frm.Actualizar,'Modificar');
        FRMCtrl.fOnFocus();
        lSelf=false;
      }
      if(cPaginaWebJS=='Filtros.js' || cPaginaWebJS=='Listado.js'){
        if((FRMCtrl) && (FRMCtrl.FRMPanel)){
          FRMCtrl.FRMPanel.fActual(FRMCtrl.FRMPanel.document.forms[0].Actualizar,'Modificar');
          FRMCtrl.fOnFocus();
        }
        lSelf=false;
      }
      if(lSelf==true){
        if(FRMPanel){
          FRMPanel.fActual(FRMPanel.document.forms[0].Actualizar,'Modificar');
          self.fOnFocus();
        }
      }
    }
    if(charCode==27){ // Cancelar Esc
      if(cPaginaWebJS=='Paneles.js'){
        self.fActual(frm.Cancelar,'Cancelar');
        FRMCtrl.fOnFocus();
        lSelf=false;
      }
      if(cPaginaWebJS=='Filtros.js' || cPaginaWebJS=='Listado.js'){
        if((FRMCtrl) && (FRMCtrl.FRMPanel)){
          FRMCtrl.FRMPanel.fActual(FRMCtrl.FRMPanel.document.forms[0].Cancelar,'Cancelar');
          FRMCtrl.fOnFocus();
        }
        lSelf=false;
      }
      if(lSelf==true){
        if(FRMPanel){
          FRMPanel.fActual(FRMPanel.document.forms[0].Cancelar,'Cancelar');
          self.fOnFocus();
        }
      }
    }
    if(charCode==10){ // Borrar
      if(cPaginaWebJS=='Paneles.js'){
        self.fActual(frm.Borrar,'Borrar');
        FRMCtrl.fOnFocus();
        lSelf=false;
      }
      if(cPaginaWebJS=='Filtros.js' || cPaginaWebJS=='Listado.js'){
        if((FRMCtrl) && (FRMCtrl.FRMPanel)){
          FRMCtrl.FRMPanel.fActual(FRMCtrl.FRMPanel.document.forms[0].Borrar,'Borrar');
          FRMCtrl.fOnFocus();
        }
        lSelf=false;
      }
      if(lSelf==true){
        if(FRMPanel){
          FRMPanel.fActual(FRMPanel.document.forms[0].Borrar,'Borrar');
          self.fOnFocus();
        }
      }
    }
  }catch(e){}
}
function fOnFocus(){
  try{
    lFocus = false;
    form = document.forms[0];
    j = 0;
    while(!lFocus && j<10){
       for(i=0;i<form.elements.length;i++){
         if((form.elements[i].type == "text" ||
         form.elements[i].type == "file" ||
         form.elements[i].type == "select-one" ||
         form.elements[i].type == "checkbox" ||
         form.elements[i].type == "textarea" ||
         form.elements[i].type == "select-multiple")
         && form.elements[i].disabled == false){
             form.elements[i].focus();
             lFocus = true;
             break;
          }
       }
       j++;
    }
  }catch(e){}
}
// Manejo Específico de Modal para frames que NO lanzan al window
  var wEOpen;
  function setwExp(wOpen){
    wEOpen = wOpen;
    window.onclick=HandleThisFocus
    window.onfocus=HandleThisFocus
  }

  function HandleThisFocus(){
    if (wEOpen){
      if (!wEOpen.closed){
        wEOpen.focus()
      }
      else{
        window.onclick="";
        window.onfocus="";
      }
    }
    return false
  }

// Manejo Específico de Modal para frames que SI lanzan al window

  function HandleFocus(){
    if (wExp){
      if (!wExp.closed){
        wExp.focus()
      }
      else{
        window.onclick="";
        window.onfocus="";
      }
    }
    return false
  }

// manejo del botón Derecho
 function click(e) {
   try{
     if(!lBtnDerecho){
          if (document.all) {
             if (event.button == 2 || event.button == 3 || event.button == 6 || event.button == 7) {
                alert(msgcder);
                return false;
             }
          }
          if (document.layers) {
             if (e.which == 3) {
                alert(msgcder);
                return false;
             }
          }
     }
   }catch(w){}
  }
  document.onmousedown=click;

function Hidden(cNombreM,cValorM){
  return '<input type="hidden" name="'+cNombreM+'" value="'+cValorM+'">';
}

function fDefPag(cBody){
  return '<HTML>'+
         '<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />'+
         '<body><form method="POST">'+cBody+'</FORM></body></HTML>'+
         '<script language="JavaScript">function fPag(){}</script>';
}

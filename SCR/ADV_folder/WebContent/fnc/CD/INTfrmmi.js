  var aThreats = new Array();
  var lOnProcess = false;
  var cUsrID;
  var cUsrName; 
  var aMenu;
  var aPermisos = new Array();
  var cPrograma;
  var iTiempoActualLocal=0;
  var cGeneral;
  var FRMTitulo='';
  var cAyuda='';
  // Definición del manejo de Frames (Cuerpo y Motor de Intercambio)
  function fBefLoad(){
  }
  function fDefPag(){
    aPags = new Array();
    aFrame = new Array();

    aFrame =  [false,'CDfrmmi.jsp','no','FRMMI','0',0,'margin: 0px; padding: 0px'];
    aPags[0] = aFrame;
    aFrame =  [false,'CDsetint.jsp','no','FRMSI','0',0,'margin: 0px; padding: 0px'];
    aPags[1] = aFrame;
    aFrame =  [true,cPagNva,'yes','FRMCuerpo','0',0,'margin: 0px; padding: 0px'];
    aPags[2] = aFrame;

    if(lDesarrollo)
      DefFrameRow(aPags,"5,2,*",false,0,0);
    else
      DefFrameRow(aPags,"0,0,*",false,0,0);
  }
  function fOnLoad(){
  }
  function fLoadIneng(){
    top.FRMMI.location.href = top.FRMSI.fGetRutaProg() +"CDfrmmi.jsp";
  }
  function fSetTiempoActualLocal(iTpo){
    iTiempoActualLocal = iTpo;
    if(top.opener)
      if(top.opener.top.fSetTiempoActualLocal)
        top.opener.top.fSetTiempoActualLocal(iTpo);
  }
  function fGetTiempoActualLocal(){
    if(top.opener)
	try{
	    if(top.opener.top.fGetTiempoActualLocal)
	        return top.opener.top.fGetTiempoActualLocal();
	}
      catch(e){}
    return iTiempoActualLocal;
  }
  function fGetTiempoVerificacion(){
    return iTiempoVerificacion;
  }
  function fSetUsrName(cUsr){
    cUsrName = cUsr;
  }
  function fGetUsrName(){
    return cUsrName;
  }  
  function fSetUsrID(cUsr){
    cUsrID = cUsr;
  }
  function fGetUsrID(){
    if(top.opener)
      if(top.opener.top.fGetUsrID)
        return top.opener.top.fGetUsrID();
    return cUsrID;
  }
  function fSetMenu(xMenu){
    aMenu = xMenu;
  }
  function fGetMenu(){
    return aMenu;
  }
  function fSetPermisos(xPermisos){
    aPermisos = xPermisos;
  }
  function fGetPermiso(cNombreM){
    if(top.opener){
      try{
      if(top.opener.top.fGetPermiso)
        return top.opener.top.fGetPermiso(cNombreM);
      }catch(E){}
    }  
    for(xper=0;xper<aPermisos.length;xper++){
       aPerTmp = aPermisos[xper];
       if(cNombreM == (''+aPerTmp[0]+'.js')){
         return aPerTmp[2];
       }
    }
    return 2;
  }
  function fGetTituloPagina(cNombreM, lRecursivo){
    if(!lRecursivo && cNombreM)
      cAyuda=cNombreM;
    if (top.opener)
      if(top.opener.top.fGetTituloPagina())
        return top.opener.top.fGetTituloPagina(cNombreM,true);
    for(xper=0;xper<aPermisos.length;xper++){
       aPerTmp = aPermisos[xper];
       if(cNombreM == (''+aPerTmp[0]+'.js')){
         return aPerTmp[1];
       }
    }
    return "TÍTULO NO ENCONTRADO";
  }
  function fSetPrograma(xPrograma){
    cPrograma = xPrograma;
  }
  function fGetPrograma(){
    return cPrograma;
  }

  function fAddThreat(cPagina,cThreat){
     aThreats[aThreats.length] = [cPagina,cThreat];
     return true;
  }
  function fRunThreat(){
        if(""+top.FRMMI.fPag == "undefined"){
          alert("Mensaje del CD:\n"+
                "\n El sistema no ha podido encontrar al Motor de Intercambio."+
                "\n - Favor de presionar F5."+
                "\n - Gracias.");
        }else{
          if(aThreats.length > 0){
             fEnProceso(true);
             lOnProcess = true;
             //top.FRMMI.document.clear();
             top.FRMMI.document.write(aThreats[0][1]);
             try{
               top.FRMMI.document.charset = "utf-8";
             }catch(e){//alert(".."+e);
             }
             frmmi = top.FRMMI.document.forms[0];
             frmmi.action = aThreats[0][0];
             aTmp = new Array();
             for(i=1;i<aThreats.length;i++){
                aTmp[i-1] = aThreats[i];
             }
             aThreats = aTmp;
             frmmi.submit();
           }
        }
  }
  function fDelThreats(){
    aThreats = new Array();
  }
  function fRunIniThreat(){
    if(!lOnProcess){
      lOnProcess = true;
      fRunThreat();
    }
  }
  function fEndThreat(){
    lOnProcess = false;
    fEnProceso(false);
  }
  function fGetGeneral(){
    return cGeneral;
  }
  function fSetGeneral(cVar){
    cGeneral = cVar;
  }
  function fEnProceso(lEnProc){
    if(top.opener){
	try{
	    if(top.opener.top)
		if (top.opener.top.fEnProceso)
		    top.opener.top.fEnProceso(lEnProc);
	}catch(e){}
    }else{
      if(FRMTitulo==''){
        FRMTitulo=fBuscaFrame('FRMTitulo');
      }else
        FRMTitulo.fEnProceso(lEnProc);
    }
  }
  function fGetNombrePrograma(){
    return cAyuda;
  }

function fPermisoEjecutar(cPag){
  if(cPag)
    cPaginaWebJS = cPag;
}

function fGetRFCRep(){
    return cRFC;
}


 function fGetTituloPagina(cNombreM){
     cAyuda=cNombreM;
     for(xper=0;xper<aPermisos.length;xper++){
        aPerTmp = aPermisos[xper];
        if(cNombreM == (''+aPerTmp[0]+'.js')){
          return aPerTmp[1];
        }
     }
     if (top.opener){
       try{
       if(top.opener.top.fGetTituloPagina)
         return top.opener.top.fGetTituloPagina(cNombreM);
       }catch(e){}
     }
     return "TÍTULO NO ENCONTRADO";
   }
function fGetUA(){
    //1 = Marina
    //2 = Puertos
    return cUA;
}
// MetaCD=1.0
var frm;
var tCPnl;
var aSignos = new Array();
var aOrdena = new Array();
var aFiltra = new Array();
var FRMCtrl;
var lDeshabTra=false;
var cNavega = '';
var iInicie = 8;
var cFiltrado = '';
var cOrdenado = '';
var cRegistros = '';

function fBefLoad(){ // Carga información antes de que la página sea mostrada.
   aSignos[0] = ['Todos','Todos'];
   aSignos[1] = ['=','='];
   aSignos[2] = ['<>','<>'];
   aSignos[3] = ['<','<'];
   aSignos[4] = ['<=','<='];
   aSignos[5] = ['>','>'];
   aSignos[6] = ['>=','>='];
   aSignos[7] = ['like','Inicie'];
   cPaginaWebJS = "Filtros.js";
}
function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJS);
   Estilo("input","COLOR:BLACK;font-family:Verdana;font-size:7pt;font-weight:normal");
   Estilo("select","COLOR:BLACK;font-family:Verdana;font-size:7pt;font-weight:normal");
   Estilo(".EEtiquetaF","COLOR:BLACK;font-family:Verdana;font-size:7pt;font-weight:bold");
   InicioTabla("",0,"100%","32","left","",".1",".1");
   ITRTD();
     //DinTabla("TPnl","",0,"","","center","","panel.gif",".1",".1");
     DinTabla("TPnl","",0,"","","center","","filtro3.jpg",".1",".1");
   FTDTR();
   FinTabla
  fFinPagina2();
}
function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];
  theTable=(document.all) ? document.all.TPnl:document.getElementById("TPnl");
  tCPnl=theTable.tBodies[0];
}
function fShow(cEstatus){
   for(i=0;tCPnl.rows.length;i++){
     tCPnl.deleteRow(0);
   }
   if(cEstatus){
     iNumEst=fNumEntries(cEstatus,',');
     if(iNumEst > 0){
       lPOrd=false;lPNav=false;lPReg=false;lPFil=false;
       for(i=0;i<iNumEst;i++){
          if(fEntry(i+1,cEstatus,",")=="Ord") lPOrd=true;
          if(fEntry(i+1,cEstatus,",")=="Nav") lPNav=true;
          if(fEntry(i+1,cEstatus,",")=="Reg") lPReg=true;
          if(fEntry(i+1,cEstatus,",")=="Fil") lPFil=true;
       }
     }
   }else{
     lPOrd=true;lPNav=true;lPReg=true;lPFil=true;
   }
   i=0;
   Rw=tCPnl.insertRow(0);

   if((lPOrd==true && lDeshabTra==false) ||
      (lPReg==true) ||
      (lPNav==true) ||
      (lPFil==true && lDeshabTra==false)){
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Img("esq01.gif");
   }
   if(lPOrd==true && lDeshabTra==false){
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=TextoSimple("ORDENAR:");
     Cll.className="EEtiquetaF";
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Select("SLSOrden","fAsigna('Orden');");
   }
   if(lPReg==true){
     Cll=Rw.insertCell(i++);
     Cll.className="EEtiquetaF";
     Cll.innerHTML=TDEtiCampo(false,"EEtiquetaF",0,SP()+"REGISTROS:","iNumReg",iNumRegLista,2,3,"Registros a desplegar...","fMayus(this);",'onKeyDown="return fCheckReturn(event);"');
   }
   if(lPNav==true){
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=BtnImg("Primero","pprimero","fAsigna('Filtro','Primero',document.forms[0].Primero)","Primer Grupo de Registros...",true);
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=BtnImg("Anterior","panterior","fAsigna('Filtro','Anterior',document.forms[0].Anterior)","Grupo Anterior de Registros...",true);
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=BtnImg("Siguiente","psiguiente","fAsigna('Filtro','Siguiente',document.forms[0].Siguiente)","Siguiente Grupo de Registros...",true);
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=BtnImg("Ultimo","pultimo","fAsigna('Filtro','Ultimo',document.forms[0].Ultimo)","Ultimo Grupo de Registros...",true);
   }
   if(lPFil==true && lDeshabTra==false){
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=TextoSimple(SP()+"BUSCAR:");
     Cll.className="EEtiquetaF";
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Select("SLSFiltro","fFiltra();");
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Select("SLSSignos","if(document.forms[0].SLSSignos.value=='Todos') document.forms[0].cFiltro.value='';");
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Text(false,"cFiltro","",13,25,"Cadena de filtrado...","fMayus(this);fTipo();",'onKeyDown="return fCheckReturn2(event);"');
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=BtnImg("Registros","lupa","fAsigna('Filtro')","Muestra el número de registros seleccionados");
   }
   if((lPOrd==true && lDeshabTra==false) ||
      (lPReg==true) ||
      (lPNav==true) ||
      (lPFil==true && lDeshabTra==false)){
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Img("esq02.gif");
   }

   if(frm.SLSSignos){
     fFillSelect(frm.SLSSignos,aSignos);
   }
   if(lPNav==true){
     fSetNavStatus('Disabled');
   }
}
function fValidaTodo(){
   cMsg = fValElements();

   if(cMsg != ''){
      fAlert(cMsg);
   }
}
function fSetFiltra(cFiltro){
 if(frm.SLSFiltro){
   iNum=fNumEntries(cFiltro,',');
   i=1;j=0;
   while (i<(iNum + 1)){
      aDato = [fEntry(i+1,cFiltro,","),fEntry(i,cFiltro,",")];
      aFiltra[j] = aDato;
      i+=2;j++;
   }
   fFillSelect(frm.SLSFiltro,aFiltra);
   fFiltra();
 }
}
function fSetOrdena(cOrden){
  if(frm.SLSOrden){
    iNum=fNumEntries(cOrden,',');
    i=1;j=0;
    while (i<(iNum + 1)){
      aDato = [fEntry(i+1,cOrden,","),fEntry(i,cOrden,",")];
      aOrdena[j] = aDato;
      i+=2;j++;
    }
    fFillSelect(frm.SLSOrden,aOrdena);
    cOrdenado=frm.SLSOrden.value;
  }
}
function fFiltra(){
 if(frm.SLSSignos){
   frm.cFiltro.value='';
   cCampo=frm.SLSFiltro.value;
   if(cCampo.indexOf(".") != -1)
     cCampo=cCampo.substring(cCampo.indexOf(".")+1);
   aTipo = aSignos[aSignos.length-1];
   if(fTipoDato(cCampo) != 2){
     if(aTipo[0] == 'like'){
       fFillSelect(frm.SLSSignos,aSignos);
       frm.SLSSignos.length = (iInicie - 1);
     }else{
       fFillSelect(frm.SLSSignos,aSignos);
       frm.SLSSignos.length = iInicie;
     }
   }else{
     fFillSelect(frm.SLSSignos,aSignos);
     frm.SLSSignos.length = iInicie;
   }
   frm.SLSSignos.selectedIndex = 0;
 }
}
function fEspecial(aSignosEsp){
   iInicie = parseInt(aSignosEsp.length,10);
   aSignos = aSignosEsp;
   fFillSelect(frm.SLSSignos,aSignos);
   fFiltra();
}
function fTipo(){
  if(frm.cFiltro){
    iTipo=fTipoDato(frm.SLSFiltro.value);
    if(fValTipo(frm.cFiltro,iTipo,"Filtrar por",false,true) != ''){
      frm.cFiltro.value='';
      return false;
    }
  }
}
function fAsigna(cTipo,cNavega,obj){
  if((frm.cFiltro) && (frm.SLSSignos)){
    if(cTipo == 'Filtro' && frm.cFiltro.value == '' && frm.SLSSignos.value != 'Todos'){
      fAlert('El campo "Filtrar" no puede encontrarse vacío.');
      return;
    }
  }
  if(FRMCtrl){
    if(FRMCtrl.fNavega){
      FRMCtrl.document.forms[0].hdBoton.value='Primero';
      if(frm.SLSOrden){
        FRMCtrl.document.forms[0].hdOrden.value=frm.SLSOrden.value;
        cOrdenado = frm.SLSOrden.value;
      }
      if((frm.SLSFiltro) && cTipo=='Filtro'){
         cAux=frm.SLSFiltro.value+' '+frm.SLSSignos.value+' ';
         if(frm.SLSSignos.value=='Todos')
           cAux='';
         else{
           cCampo=frm.SLSFiltro.value;
           if(cCampo.indexOf(".") != -1)
             cCampo=cCampo.substring(cCampo.indexOf(".")+1);
           if(fTipoDato(cCampo)==2){
             if(frm.SLSSignos.value != 'like')
                cAux=' UCASE('+frm.SLSFiltro.value+') '+frm.SLSSignos.value+" '"+frm.cFiltro.value.toUpperCase()+"' ";
             else
                cAux=' UCASE('+frm.SLSFiltro.value+') '+frm.SLSSignos.value+" '"+frm.cFiltro.value.toUpperCase()+"%' ";
           }else
             if(fTipoDato(cCampo)==3)
               cAux=cAux+fFormatDate(frm.cFiltro.value);
             else
               cAux=cAux+frm.cFiltro.value;
         }
         FRMCtrl.document.forms[0].hdFiltro.value=cAux;
         cFiltrado = cAux;
      }
      if(cNavega)
        fNavega(cNavega,obj);
      else
        fNavega('Primero',obj);
      FRMCtrl.document.forms[0].hdBoton.value='';
      FRMCtrl.document.forms[0].hdNumReg.value = 100000;
      FRMCtrl.document.forms[0].hdOrden.value = "";
      FRMCtrl.document.forms[0].hdFiltro.value = "";
    }else
       fAlert('FRMCtrl-fNavega');
  }else
    fAlert('FRMCtrl-fSetControl');
}
function fNavega(cNavega,obj){
  iEstatus=0;
  if(obj)
    iEstatus=parseInt(obj.src.substring(obj.src.length-5,obj.src.length-4),10);
  if(iEstatus < 3){
    if(frm.iNumReg){
      if((''+parseInt(frm.iNumReg.value)) == 'NaN')
        frm.iNumReg.value=iNumRegLista
      if(parseInt(frm.iNumReg.value) > iMaxNumRegLista)
        frm.iNumReg.value=iMaxNumRegLista
      if(parseInt(frm.iNumReg.value) <=0)
        frm.iNumReg.value=iNumRegLista
    }
    if(FRMCtrl){
      if(FRMCtrl.fNavega){
        FRMCtrl.document.forms[0].hdBoton.value=cNavega;
        if(frm.iNumReg)
          cRegistros=frm.iNumReg.value;
        else
          cRegistros=iNumRegLista;
        FRMCtrl.document.forms[0].hdNumReg.value = cRegistros;
        FRMCtrl.fNavega();
      }else
        fAlert('FRMCtrl-fNavega');
    }else
      fAlert('FRMCtrl-fSetControl');
  }
}
function fSetControl(oControl){
   FRMCtrl=oControl;
}
function fMouseOver(objDoc, cImagen, cEstatus){
    frm=objDoc.forms[0];
    var objImg;
    if (cImagen=='pprimero') objImg=frm.Primero;
    if (cImagen=='panterior') objImg=frm.Anterior;
    if (cImagen=='psiguiente') objImg=frm.Siguiente;
    if (cImagen=='pultimo') objImg=frm.Ultimo;
    cEdoIni=objImg.src.substr(objImg.src.length - 5, 1);
    if (cEdoIni=='1') fSrc(objImg, '2');
    if (cEdoIni=='2') fSrc(objImg, '1');
    if (cEdoIni=='3') fSrc(objImg, '3');
    if (cEdoIni=='4') fSrc(objImg, '4');
}
function fMouseOut(objDoc, cImagen){
    fMouseOver(objDoc, cImagen);
}
function fSetNavStatus(cNavStatus){
  if(frm.Primero){
    if(cNavStatus=='ReposRecord' && cNavega!='') cNavStatus=cNavega;
    if(cNavStatus=='FirstRecord'){
      fSrc(frm.Primero,'3');fSrc(frm.Anterior,'3');fSrc(frm.Siguiente,'1');fSrc(frm.Ultimo,'1');}
    if(cNavStatus=='LastRecord'){
      fSrc(frm.Primero,'1');fSrc(frm.Anterior,'1');fSrc(frm.Siguiente,'3');fSrc(frm.Ultimo,'3');}
    if(cNavStatus=='Disabled'){
      fSrc(frm.Primero,'3');fSrc(frm.Anterior,'3');fSrc(frm.Siguiente,'3');fSrc(frm.Ultimo,'3');}
    if(cNavStatus=='Record'){
      fSrc(frm.Primero,'1');fSrc(frm.Anterior,'1');fSrc(frm.Siguiente,'1');fSrc(frm.Ultimo,'1');}
    if(cNavStatus!='Disabled')
      cNavega = cNavStatus;
  }
}
function fCheckReturn(evt){
    evt=(evt) ? evt : window.event;
    var charCode=(evt.which)?evt.which:evt.keyCode;
    if(charCode==13){
      frm.iNumReg.select();
      fAsigna('Filtro');
      return false;
    }
    return true;
}
function fCheckReturn2(evt){
    evt=(evt) ? evt : window.event;
    var charCode=(evt.which)?evt.which:evt.keyCode;
    if(charCode==13){
      frm.cFiltro.value = frm.cFiltro.value.toUpperCase();
      //frm.cFiltro.select();
      if(fTipo() != false)
         fAsigna('Filtro');
      return false;
    }
    return true;
}
function fSetFiltro(cCad){
   frm.cFiltro.value = cCad;
   cCampo=frm.SLSFiltro.value;
   if(cCampo.indexOf(".") != -1)
      cCampo=cCampo.substring(cCampo.indexOf(".")+1);
   if(fTipoDato(cCampo)==2){
      if(frm.SLSSignos.value != 'like')
         cAux=' UCASE('+frm.SLSFiltro.value+') '+frm.SLSSignos.value+" '"+frm.cFiltro.value.toUpperCase()+"' ";
      else
         cAux=' UCASE('+frm.SLSFiltro.value+') '+frm.SLSSignos.value+" '"+frm.cFiltro.value.toUpperCase()+"%' ";
   }else
     if(fTipoDato(cCampo)==3)
        cAux=cAux+fFormatDate(frm.cFiltro.value);
     else
        cAux=cAux+frm.cFiltro.value;
   cFiltrado = cAux;
}
function fGetFiltro(){
  return cFiltrado;
}
function fGetOrden(){
  return cOrdenado;
}
function fGetNumReg(){
  return cRegistros;
}

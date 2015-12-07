// MetaCD=1.0
var FRMCtrl;
var tCPnl;
var frm;
var cNavega = '';
var lModificar=false;
var lDeshabTra=false;
function fBefLoad(){
}
function fDefPag(){ // Define la página a ser mostrada
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","1","center","top",".1",".1");
   ITRTD("",0,"","","center");
     InicioTabla("",0,"","","","",".1",".1");
       ITRTD();
         DinTabla("TPnl","",0,"100%","32","center","middle","panel.gif",".1",".1");
       FTDTR();
     FinTabla
   FTDTR();
   FinTabla();
   fFinPagina
}
function fOnLoad(){
   frm=document.forms[0];
   theTable=(document.all) ? document.all.TPnl:
   document.getElementById("TPnl");
   tCPnl=theTable.tBodies[0];
}
function fShow(cEstatus){
   for(i=0;tCPnl.rows.length;i++){
     tCPnl.deleteRow(0);
   }
   if(cEstatus){
     iNumEst=fNumEntries(cEstatus,',');
     if(iNumEst > 0){
       lPTra=false;lPNav=false;lPReg=false;lPRep=false;
       for(i=0;i<iNumEst;i++){
          if(fEntry(i+1,cEstatus,",")=="Tra") lPTra=true;
          if(fEntry(i+1,cEstatus,",")=="Nav") lPNav=true;
          if(fEntry(i+1,cEstatus,",")=="Reg") lPReg=true;
          if(fEntry(i+1,cEstatus,",")=="Rep") lPRep=true;
       }
     }
   }else{
     lPTra=true;lPNav=true;lPReg=true;lPRep=true;
   }
   i=0;
   Rw=tCPnl.insertRow(0);
   Cll=Rw.insertCell(i++);
   Cll.innerHTML=Img("esq01.gif");

   if(lPTra==true && lDeshabTra==false){
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Nuevo","nuevo","fActual(document.forms[0].Nuevo,'Nuevo')","Nuevo...",true);
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Guardar","guardar","fActual(document.forms[0].Guardar,'Guardar')","Guardar...",true);
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Actualizar","modificar","fActual(document.forms[0].Actualizar,'Modificar')","Modificar...",true);
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Cancelar","cancelar","fActual(document.forms[0].Cancelar,'Cancelar')","Cancelar...",true);
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Borrar","borrar","fActual(document.forms[0].Borrar,'Borrar')","Borrar...",true);
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Img("regla.gif");
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=SP();
   }
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Imprimir","imprimir","fActual(document.forms[0].Imprimir,'Imprimir')","Imprimir...");
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Reporte","reporte","fReportes();","Reporte...");
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=SP();
    Cll=Rw.insertCell(i++);
    Cll.innerHTML=Img("regla.gif");
    Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
    Cll.innerHTML=SP();


   if(lPNav==true){
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Primero","pprimero","fNavega('Primero',document.forms[0].Primero)","Primero...",true);
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Anterior","panterior","fNavega('Anterior',document.forms[0].Anterior)","Anterior...",true);
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Siguiente","psiguiente","fNavega('Siguiente',document.forms[0].Siguiente)","Siguiente...",true);
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=BtnImg("Ultimo","pultimo","fNavega('Ultimo',document.forms[0].Ultimo)","Ultimo...",true);
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Img("regla.gif");;
     Cll=Rw.insertCell(i++);Cll.vAlign="bottom";
     Cll.innerHTML=SP();
   }
   if(lPReg==true){
     Cll=Rw.insertCell(i++);
     Cll.className="ECampo";
     Cll.innerHTML=TDEtiCampo(false,"ECampo",0,"Registros:","iNumReg",iNumRegLista,3,3,"Registros a desplegar...","fMayus(this);",'onKeyDown="return fCheckReturn(event);"');
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=SP();
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=BtnImg("Registros","ira","fNavega('Primero')","Muestra el número de registros seleccionados");
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=SP();
     Cll=Rw.insertCell(i++);
     Cll.innerHTML=Img("regla.gif");
   }
   Cll=Rw.insertCell(i++);Cll.vAlign="top";
   Cll.innerHTML=Img("esq02.gif");
   fSetNavStatus('Disabled');
   fSetTraStatus('Disabled');
}
function fCheckReturn(evt){
    evt=(evt) ? evt : window.event;
    var charCode=(evt.which)?evt.which:evt.keyCode;
    if(charCode==13){
      frm.iNumReg.select();
      fNavega('Primero');
      return false;
    }
    return true;
}
function fSetControl(oControlM,cPW){
   FRMCtrl=oControlM;
   if(cPW && top.fGetPermiso){
     if(top.fGetPermiso(cPW) == 0) lDeshabTra=true;
   }
}
function fActual(obj,cTipo){
  iEstatus=parseInt(obj.src.substring(obj.src.length-5,obj.src.length-4));
  if(iEstatus < 3){
    if(FRMCtrl){
        FRMCtrl.document.forms[0].hdBoton.value=cTipo;
        if(cTipo=='Nuevo'){
          lModificar==false;
          (FRMCtrl.fNuevo)?FRMCtrl.fNuevo():fAlert('FRMCtrl-fNuevo');
        }
        if(cTipo=='Guardar'){
            if(lModificar==false)
              (FRMCtrl.fGuardar)?FRMCtrl.fGuardar():fAlert('FRMCtrl-fGuardar');
            else{
              FRMCtrl.document.forms[0].hdBoton.value="GuardarA";
              (FRMCtrl.fGuardarA)?FRMCtrl.fGuardarA():fAlert('FRMCtrl-fGuardarA');
            }
            lModificar==false;
        }
        if(cTipo=='Modificar'){
          if(FRMCtrl.fModificar){
             lModificar=true;
             FRMCtrl.fModificar();
          }else fAlert('FRMCtrl-fModificar');
        }
        if(cTipo=='Cancelar'){
          if(FRMCtrl.fCancelar){
             lModificar=false;
             FRMCtrl.fCancelar();
          }else fAlert('FRMCtrl-fCancelar');
        }
        if(cTipo=='Borrar'){
          lModificar==false;
          (FRMCtrl.fBorrar)?FRMCtrl.fBorrar():fAlert('FRMCtrl-fBorrar');
        }
        if(cTipo=='Imprimir'){
          if(FRMCtrl.fImprimir){
             FRMCtrl.fImprimir();
          }else fAlert('FRMCtrl-fImprimir');
        }
        if(cTipo=='Reporte'){
          if(FRMCtrl.fReporte){
             FRMCtrl.fReporte();
          }else fAlert('FRMCtrl-fReporte');
        }
    }else
      fAlert('FRMCtrl-fSetControl');
  }
}
function fNavega(cNavega,obj){
  iEstatus=0;
  if(obj)
    iEstatus=parseInt(obj.src.substring(obj.src.length-5,obj.src.length-4));
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
          FRMCtrl.document.forms[0].hdNumReg.value=frm.iNumReg.value;
        else
          FRMCtrl.document.forms[0].hdNumReg.value=iNumRegLista;
        FRMCtrl.fNavega();
      }else
        fAlert('FRMCtrl-fNavega');
    }else
      fAlert('FRMCtrl-fSetControl');
  }
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

function fSetTraStatus(cTraStatus){
  lNuevo=false;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=false;
  if(frm.Nuevo){
    if(cTraStatus=='UpdateBegin'){
      lNuevo=false;lGuardar=true;lActualizar=false;lCancelar=true;lBorrar=false;
      fSetNavStatus('Disabled');
    }
    if (cTraStatus=='UpdateComplete'){
      lNuevo=true;lGuardar=false;lActualizar=true;lCancelar=false;lBorrar=true;}
    if (cTraStatus=='AddOnly'){
      lNuevo=true;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=false;
      fSetNavStatus('Disabled');
    }
    if (cTraStatus=='Disabled'){
      lNuevo=false;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=false;}
    iNumEst=fNumEntries(cTraStatus,',');
    if(iNumEst > 0){
       for(i=0;i<iNumEst;i++){
          if(fEntry(i+1,cTraStatus,",")=="Add")
            lNuevo=true;
          if(fEntry(i+1,cTraStatus,",")=="Sav")
            lGuardar=true;
          if(fEntry(i+1,cTraStatus,",")=="Mod")
            lActualizar=true;
          if(fEntry(i+1,cTraStatus,",")=="Can")
            lCancelar=true;
          if(fEntry(i+1,cTraStatus,",")=="Del")
            lBorrar=true;
       }
    }
    if(lNuevo==true) fSrc(frm.Nuevo,'1'); else fSrc(frm.Nuevo,'3');
    if(lGuardar==true) fSrc(frm.Guardar,'1'); else fSrc(frm.Guardar,'3');
    if(lActualizar==true) fSrc(frm.Actualizar,'1'); else fSrc(frm.Actualizar,'3');
    if(lCancelar==true) fSrc(frm.Cancelar,'1'); else fSrc(frm.Cancelar,'3');
    if(lBorrar==true) fSrc(frm.Borrar, '1'); else fSrc(frm.Borrar, '3');
  }
}
function fMouseOver(objDoc, cImagen, cEstatus){
    frm=objDoc.forms[0];
    var objImg;
    if (cImagen=='pprimero') objImg=frm.Primero;
    if (cImagen=='panterior') objImg=frm.Anterior;
    if (cImagen=='psiguiente') objImg=frm.Siguiente;
    if (cImagen=='pultimo') objImg=frm.Ultimo;
    if (cImagen=='nuevo') objImg=frm.Nuevo;
    if (cImagen=='guardar') objImg=frm.Guardar;
    if (cImagen=='modificar') objImg=frm.Actualizar;
    if (cImagen=='cancelar') objImg=frm.Cancelar;
    if (cImagen=='borrar') objImg=frm.Borrar;
    cEdoIni=objImg.src.substr(objImg.src.length - 5, 1);
    if (cEdoIni=='1') fSrc(objImg, '2');
    if (cEdoIni=='2') fSrc(objImg, '1');
    if (cEdoIni=='3') fSrc(objImg, '3');
    if (cEdoIni=='4') fSrc(objImg, '4');
}
function fMouseOut(objDoc, cImagen){
    fMouseOver(objDoc, cImagen);
}

// MetaCD=1.0
var FRMCtrl;
var tCPnl;
var frm;
var cNavega = '';
var lModificar=false;
var lDeshabTra=false;
var lNuevo;
var lGuardar;
var lActualizar;
var lCancelar;
var lBorrar;
var cEstatusGral;
var cEstadoActual;
var lHabReporte=false;
var cNombreFuncion='';

function fBefLoad(){
  cPaginaWebJS = "Paneles.js";
}
function fDefPag(){ // Define la página a ser mostrada
   Estilo("A:Link","COLOR:FFFFFF;font-family:Verdana;font-size:10pt;TEXT-DECORATION:none;font-weight:Bold;background-color: #"+cColorBGPanel +";");
   Estilo("A:Visited","COLOR:FFFFFF;font-family: Verdana;font-size:10pt;TEXT-DECORATION:none;font-weight:Bold;background-color: #"+cColorBGPanel +";");
   Estilo(".EDisPanel","COLOR:5C2D2D;font-family:Verdana;font-size:10pt;text-align:center;border:0px solid #6B96AD;padding:0px;spacing:0px;background-color: #"+cColorGenJS+";");
   Estilo(".ERegla","border: 2px solid #193AF1;padding: 0px;spacing: 0px;");
   Estilo(".ETablaInfoPanel","border:1px solid #"+cColorGenJS+";background-color:#"+cColorGenJS+";text-align:center;");
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","25","center","",".1",".1");
   ITRTD("",0,"","100%","center","top");
     InicioTabla("",0,"","100%","","",".1",".1");
       ITRTD();
         DinTabla("TPnl","",0,"","100%","center","top","",".1",".1");
       FTDTR();
     FinTabla();
   FTDTR();
   FinTabla();
   fFinPagina2();
}
function fOnLoad(){
   frm=document.forms[0];
   theTable=(document.all) ? document.all.TPnl:
   document.getElementById("TPnl");
   tCPnl=theTable.tBodies[0];
}
function fShow(cEstatus,lHabDes){
  if(lHabDes+'' == 'undefined')
    lHabDes = true;
   cEstatusGral = cEstatus;
   if(lHabDes != true){
      lNuevo=false;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=false;
   }
   if(tCPnl && tCPnl.rows && tCPnl.rows.length)
     for(i=0;tCPnl.rows.length;i++)
       tCPnl.deleteRow(0);
   if(cEstatus){
     iNumEst=fNumEntries(cEstatus,',');
     if(iNumEst > 0){
       lPTra=false;lPRep=false;
       for(i=0;i<iNumEst;i++){
          if(fEntry(i+1,cEstatus,",")=="Tra") lPTra=true;
          if(fEntry(i+1,cEstatus,",")=="Rep") lPRep=true;
          if(fEntry(i+1,cEstatus,",")=="Imp") lPRep=false;
       }
     }
   }else{
     lPTra=true;lPRep=true;
   }
   i=0;
   if(tCPnl){
     Rw=tCPnl.insertRow(0);
     if(lPTra==true && lDeshabTra==false){
       Cll=Rw.insertCell(i++);Cll.width="80";
       if(lNuevo == true){
	   Cll.className="ETablaInfoPanel";
	   Cll.innerHTML=BtnImg("Nuevo","pan-nuevo","fActual(document.forms[0].Nuevo,'Nuevo')");}//Liga("Nuevo","fActual(document.forms[0].Nuevo,'Nuevo')","Nuevo...");}
       else{Cll.className="EDisPanel";Cll.innerHTML=TextoSimple("Nuevo");}
       Cll=Rw.insertCell(i++);Cll.width="80";
       if(lGuardar == true){Cll.className = "ETablaInfoPanel"; Cll.innerHTML=BtnImg("Guardar","pan-guardar","fActual(document.forms[0].Guardar,'Guardar')");}//Liga("Guardar","fActual(document.forms[0].Guardar,'Guardar')","Guardar...");}
       else{Cll.className="EDisPanel";Cll.innerHTML=TextoSimple("Guardar");}
       Cll=Rw.insertCell(i++);Cll.width="80";
       if(lActualizar == true){Cll.className = "ETablaInfoPanel"; Cll.innerHTML=BtnImg("Modificar","pan-modificar","fActual(document.forms[0].Actualizar,'Modificar')");}//Liga("Modificar","fActual(document.forms[0].Actualizar,'Modificar')","Modificar...");}
       else{Cll.className="EDisPanel";Cll.innerHTML=TextoSimple("Modificar");}
       Cll=Rw.insertCell(i++);Cll.width="80";
       if(lCancelar == true){Cll.className = "ETablaInfoPanel"; Cll.innerHTML=BtnImg("Cancelar","pan-cancelar","fActual(document.forms[0].Cancelar,'Cancelar')");}//Liga("Cancelar","fActual(document.forms[0].Cancelar,'Cancelar')","Cancelar...");}
       else{Cll.className="EDisPanel";Cll.innerHTML=TextoSimple("Cancelar");}
       Cll=Rw.insertCell(i++);Cll.width="80";
       if(lBorrar == true){Cll.className = "ETablaInfoPanel"; Cll.innerHTML=BtnImg("Borrar","pan-borrar","fActual(document.forms[0].Borrar,'Borrar')");}//Liga("Borrar","fActual(document.forms[0].Borrar,'Borrar')","Borrar...");}
       else{Cll.className="EDisPanel";Cll.innerHTML=TextoSimple("Borrar");}
       Cll=Rw.insertCell(i++);
       Cll.innerHTML=InicioTabla("ERegla",0,"0","100%","","",".1",".1")+ITRTD()+FTDTR();
     }
     //Cll=Rw.insertCell(i++);Cll.className = "ETablaInfoPanel";Cll.width="100";
     //Cll.innerHTML=InicioTabla("",0)+ITRTD()+BtnImg("Imprimir","pan-imprimir","fActual(document.forms[0].Imprimir,'Imprimir')")/*Liga("-Imprimir-","fActual(document.forms[0].Imprimir,'Imprimir')","Imprimir...")*/+FTDTR();
     if((lPRep==true && lDeshabTra==false) || lHabReporte==true){
       Cll=Rw.insertCell(i++);
       Cll.innerHTML=InicioTabla("ERegla",0,"0","100%","","",".1",".1")+ITRTD()+FTDTR();
       Cll=Rw.insertCell(i++);Cll.className = "ETablaInfoPanel";Cll.width="100";
       Cll.innerHTML=InicioTabla("",0)+ITRTD()+BtnImg("Imprimir","pan-reporte","fActual(document.forms[0].Reporte,'Reporte')")/*Liga("-Reporte-","fActual(document.forms[0].Reporte,'Reporte')","Reporte...")*/+FTDTR();
     }
   }
}
function fSetControl(oControlM,cPW){
   FRMCtrl=oControlM;
   if(cPW && top.fGetPermiso){
     if(top.fGetPermiso(cPW) == 0) lDeshabTra=true;
   }
}
function fSetNombreFuncion(cNomAdicional){
  if(cNomAdicional)
    cNombreFuncion = cNomAdicional;
}
function fActual(obj,cTipo){
  iEstatus=0;//parseInt(obj.src.substring(obj.src.length-5,obj.src.length-4));
  if(iEstatus < 3){
    if(FRMCtrl){
        FRMCtrl.document.forms[0].hdBoton.value=cTipo;
        if(cTipo=='Nuevo'){
          lModificar=false;
          (eval("FRMCtrl.fNuevo"+cNombreFuncion))?eval("FRMCtrl.fNuevo"+cNombreFuncion+"()"):fAlert('FRMCtrl-fNuevo'+cNombreFuncion);
          FRMCtrl.fOnFocus();
        }
        if(cTipo=='Guardar'){
            if(lModificar==false){
              FRMCtrl.document.forms[0].hdBoton.value="Guardar";
              (eval("FRMCtrl.fGuardar"+cNombreFuncion))?eval("FRMCtrl.fGuardar"+cNombreFuncion+"()"):fAlert('FRMCtrl-fGuardar'+cNombreFuncion);
            }else{
              FRMCtrl.document.forms[0].hdBoton.value="GuardarA";
              (eval("FRMCtrl.fGuardarA"+cNombreFuncion))?eval("FRMCtrl.fGuardarA"+cNombreFuncion+"()"):fAlert('FRMCtrl-fGuardarA'+cNombreFuncion);
            }
            window.focus();
        }
        if(cTipo=='Modificar'){
          if(eval("FRMCtrl.fModificar"+cNombreFuncion)){
             lModificar=true;
             eval("FRMCtrl.fModificar"+cNombreFuncion+"()");
             FRMCtrl.fOnFocus();
          }else fAlert('FRMCtrl-fModificar'+cNombreFuncion);
        }
        if(cTipo=='Cancelar'){
          if(eval("FRMCtrl.fCancelar"+cNombreFuncion)){
             lModificar=false;
             eval("FRMCtrl.fCancelar"+cNombreFuncion+"()");
             window.focus();
          }else fAlert('FRMCtrl-fCancelar'+cNombreFuncion);
        }
        if(cTipo=='Borrar'){
          lModificar=false;
          (eval("FRMCtrl.fBorrar"+cNombreFuncion))?eval("FRMCtrl.fBorrar"+cNombreFuncion+"()"):fAlert('FRMCtrl-fBorrar'+cNombreFuncion);
        }
        if(cTipo=='Imprimir'){
          if(eval("FRMCtrl.fImprimir"+cNombreFuncion)){
             eval("FRMCtrl.fImprimir"+cNombreFuncion+"()");
          }else fAlert('FRMCtrl-fImprimir');
        }
        if(cTipo=='Reporte'){
          if(eval("FRMCtrl.fReporte"+cNombreFuncion)){
             eval("FRMCtrl.fReporte"+cNombreFuncion+"()");
          }else fAlert('FRMCtrl-fReporte');
        }
        FRMCtrl.document.forms[0].hdBoton.value="";
    }else
      fAlert('FRMCtrl-fSetControl');
  }
}
function fSetTraStatus(cTraStatus){
if(cEstadoActual != cTraStatus){
  cEstadoActual = cTraStatus;
  lNuevo=false;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=false;
    if(cTraStatus=='UpdateBegin'){
      lNuevo=false;lGuardar=true;lActualizar=false;lCancelar=true;lBorrar=false;
    }
    if (cTraStatus=='UpdateComplete'){
      lNuevo=true;lGuardar=false;lActualizar=true;lCancelar=false;lBorrar=true;lModificar=false;
    }
    if (cTraStatus=='UpdateCompleteRepL'){
        lNuevo=true;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=true;lModificar=false;
      }
    if (cTraStatus=='UpdateOnly'){
      lNuevo=false;lGuardar=false;lActualizar=true;lCancelar=true;lBorrar=false;lModificar=false;
    }
    if (cTraStatus=='AddOnly'){
      lNuevo=true;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=false;lModificar=false;
    }
    if (cTraStatus=='Disabled'){
      lNuevo=false;lGuardar=false;lActualizar=false;lCancelar=false;lBorrar=false;lModificar=false;
    }
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
    fShow(cEstatusGral,true);
  }
}
function fHabilitaReporte(lEstado){
  lHabReporte = lEstado;
}


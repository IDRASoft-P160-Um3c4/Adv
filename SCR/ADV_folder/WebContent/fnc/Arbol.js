// MetaCD=1.0
var tCpoArbol;
var frm;
var FRMCtrl;
var aArbol;
var aAbiertos;
var aCerrados;
var cImgCerrado;
var cImgAbierto;
var cImgOpcion;
var lDisabled = false;
var iSelNodoVal=1;
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
}
function fDefPag(){ // Define la página a ser mostrada
   Estilo("A:Link","COLOR:"+cColorLetraArbol+";font-family:Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
   Estilo("A:Visited","COLOR:"+cColorLetraArbol+";font-family: Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
   fInicioPagina(cColorFondoArbol);
     DinTabla("TArbol","",0,"100%","1","left","top");
   fFinPagina();
}
function fOnLoad(){
  theTree = (document.all) ? document.all.TArbol:document.getElementById("TArbol");
  tCpoArbol = theTree.tBodies[0];
  frm = document.forms[0];
}
function fSetArbol(aArbolPar){
   aArbol = aArbolPar;
}
function fShow(lNodoRama){
   lNR = false
   if(lNodoRama == true)
     lNR = true;
   for(i=0;tCpoArbol.rows.length;i++){
     tCpoArbol.deleteRow(0);
   }
   j=0;k=0;
   aAbiertos = new Array();
   for(i=0;i<aArbol.length;i++){
      aRow=aArbol[i];
      if(aRow[0]==0){
        newRow  = tCpoArbol.insertRow(j++);
        newCell = newRow.insertCell(0);
        iDenta = 0;
        if(aRow[4]==2){
           newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD()+
                               fFill(iDenta,SP())+Img(cImgOpcion)+FITD()+
                               Liga(aRow[2],"fSelNodo("+i+");","...")+FTDTR()+
                               FinTabla();
        }else{
           cImg = cImgCerrado;
           if(aRow[4]==1)
             cImg = cImgAbierto;
           cNR = "fSelRama("+i+","+lNR+");";
           if(lNR==true)
             cNR = "fSelNodo("+i+");" + cNR;

           newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD()+
                               fFill(iDenta,SP())+Img(cImg)+FITD()+
                               Liga(aRow[2],cNR,"...")+FTDTR()+
                               FinTabla();
        }
      }
      if(aRow[4]==1){
        aAbiertos[k++]=[aRow[1],aRow[5]];
      }
      if(fAbrirNodo(aRow[0],aRow)==true){
        newRow  = tCpoArbol.insertRow(j++);
        newCell = newRow.insertCell(0);
        iDenta = aRow[5];
        if(aRow[4]==2){
           newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD()+
                               fFill(iDenta,SP())+Img(cImgOpcion)+FITD()+
                               Liga(aRow[2],"fSelNodo("+i+");","...")+FTDTR()+
                               FinTabla();
        }else{
           cImg = cImgCerrado;
           if(aRow[4]==1)
             cImg = cImgAbierto;

           cNR = "fSelRama("+i+","+lNR+");";
           if(lNR==true)
             cNR = "fSelNodo("+i+");" + cNR;

           newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD()+
                               fFill(iDenta,SP())+Img(cImg)+FITD()+
                               Liga(aRow[2],cNR,"...")+FTDTR()+
                               FinTabla();
        }
      }
   }
}
function fAbrirNodo(iPadre,aRow){
  for(ghj=0;ghj<aAbiertos.length;ghj++){
     aTmpAb = aAbiertos[ghj];
     if(aTmpAb[0]==iPadre){
       aRow[5]=parseInt(aTmpAb[1],10)+2;
       return true;
     }
  }
  return false;
}
function fSelRama(iPos,lNodoRama){
  if(lDisabled==false){
     aRow = aArbol[iPos]
     if(aRow[4]==0){ //abre
        aRow[4]=1;
        aArbol[iPos] = aRow;
     }else{ //cierra
        aRow[4]=0;
        aRow[5]=0;
        aArbol[iPos] = aRow;
        fCierraDescendencia(aRow[1]);
     }
     fShow(lNodoRama);
  }
}
function fCierraDescendencia(iPadre){
   aCerrados = new Array();
   aCerrados[0]=iPadre;
   for(i=0;i<aArbol.length;i++){
     aRow = aArbol[i]
     if(fEsHijoCerrarNodo(aRow[0],aRow[1])==true){
       if(aRow[4]!=2){
         aRow[4]=0;
         aArbol[i] = aRow;
       }
     }
   }
}
function fEsHijoCerrarNodo(iPadre,iHijo){
   lSiEs = false;
   lCont = true;
   kl=0;
   while(lCont==true){
     if(aCerrados[kl++]==iPadre){
       lSiEs = true;
       aCerrados[aCerrados.length]=iHijo;
     }
     if(kl>=aCerrados.length){
       lCont = false;
     }
   }
   return lSiEs ;
}
function fSetControl(FRMControl){
   FRMCtrl = FRMControl;
}
function fSelNodo(iPos){
  if(lDisabled==false){
    if(iSelNodoVal == 1)
       FRMCtrl.fOnSelNodoArbol(aArbol[iPos]);
    if(iSelNodoVal == 2)
       FRMCtrl.fOnSelNodoArbol2(aArbol[iPos]);
    if(iSelNodoVal == 3)
       FRMCtrl.fOnSelNodoArbol3(aArbol[iPos]);
  }
}
function fFill(iNoChar,cChar){
   cTmp = '';
   for(iVer=0;iVer<iNoChar;iVer++){
      cTmp+=cChar;
   }
   return cTmp;
}
function fSetImgCerrado(cNomCerrado){
   cImgCerrado = cNomCerrado;
}
function fSetImgAbierto(cNomAbierto){
   cImgAbierto = cNomAbierto;
}
function fSetImgOpcion(cNomOpcion){
   cImgOpcion = cNomOpcion;
}
function fDisabled(lValor){
  lDisabled = lValor;
}
function fSetSelNodo(iCveNodo){
  iSelNodoVal = iCveNodo;
}


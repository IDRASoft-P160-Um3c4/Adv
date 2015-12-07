// MetaCD=1.0
var aArbol;
var tCpoArbol;
var FRMPagina;
var iRowAnt=-1;
var cBGAnt;
var frm;
var FRMCtrl;
var aArbol;
var aAbiertos;
var aCerrados;
var lDisabled = false;
var iSelNodoVal=1;
var iCveSel;


var cColorSel="FFFFFF";
var cColorFondoArbol = "GRAY";
var cColorArbolSel = "058A48";
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
   aArbol = top.fGetMenu();
}



function fDefPag(){ // Define la página a ser mostrada
   fInicioPagina(cColorFondoArbol);
   Estilo("A:Link","COLOR:white;font-family:Verdana;font-size:9pt;font-weight:normal;TEXT-DECORATION:none");
   Estilo("A:Visited","COLOR:white;font-family: Verdana;font-size:9pt;font-weight:normal;TEXT-DECORATION:none");

//   Estilo("A:Link","COLOR:white;font-family:Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
   //Estilo("A:Visited","COLOR:white;font-family: Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
   Estilo(".EM","COLOR:DDDF07;font-family: Verdana;font-size:13pt;font-weight:bold;TEXT-DECORATION:none");
   InicioTabla("",0,"100%","100%","center","",".1",".1");
     ITRTD("",0,"","","","top");
       DinTabla("TArbol","",0, "100%", "", "", "top", "");
     FTDTR();
   FinTabla();
   fFinPagina();
}

function fOnLoad(){
  theTree = (document.all) ? document.all.TArbol:document.getElementById("TArbol");
  tCpoArbol = theTree.tBodies[0];
  frm = document.forms[0];
  fShow();
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
      if(aRow[2]==0){
        newRow  = tCpoArbol.insertRow(j++);
        newRow.style.backgroundColor = cColorFondoArbol;
        if(aRow[3] == iCveSel)
           newRow.style.backgroundColor = cColorArbolSel;
        newCell = newRow.insertCell(0);
        iDenta = 0;
        if(aRow[4]==2){
          //Accion:: Se agrego IF para eliminar un nodo de Estadisticas "Control de Operacion"
         // if(i != 362 && j != 23)
          //{
             newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD("EM")+
                                 fFill(iDenta,SP())+":"+FITD()+
                                 Liga(aRow[0],"fSelNodo("+i+","+j+");","...")+FTDTR()+
                                 FinTabla();
          //}*/
        }else{
           cImg = "+";
           if(aRow[4]==1)
             cImg = FITD("EM")+"-"+FITD()+SP();
           cNR = "fSelRama("+i+","+lNR+");";
           if(lNR==true)
             cNR = "fSelNodo("+i+","+j+");" + cNR;
           //Accion:: Se agrego IF para eliminar un nodo de Estadisticas "Control de Operacion"
           //if(i != 362 && j != 23)
           //{
             newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD("EM")+
                                 fFill(iDenta,SP())+cImg+FITD()+
                                 Liga(aRow[0],cNR,"...")+FTDTR()+
                                 FinTabla();
           //}
        }
      }
      if(aRow[4]==1){
        aAbiertos[k++]=[aRow[3],aRow[5]];
      }
      if(fAbrirNodo(aRow[2],aRow)==true){
        newRow  = tCpoArbol.insertRow(j++);
        newRow.style.backgroundColor = cColorFondoArbol;
        if(aRow[3] == iCveSel)
           newRow.style.backgroundColor = cColorArbolSel;
        newCell = newRow.insertCell(0);
        iDenta = aRow[5];
        if(aRow[4]==2){
          //Accion:: Se agrego IF para eliminar un nodo de Estadisticas "Control de Operacion"
          //if(i != 362 && j != 23)
          //{
             newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD("EM")+
                                 fFill(iDenta,SP())+":"+FITD()+
                                 Liga(aRow[0],"fSelNodo("+i+","+j+");","...")+FTDTR()+
                                 FinTabla();
          //}
        }else{
           cImg = "+";
           if(aRow[4]==1)
             cImg = FITD("EM")+"-"+FITD()+SP();

           cNR = "fSelRama("+i+","+lNR+");";
           if(lNR==true)
             cNR = "fSelNodo("+i+","+j+");" + cNR;
           //Accion:: Se agrego IF para eliminar un nodo de Estadisticas "Control de Operacion"
           //if(i != 362 && j != 23)
           //{
             newCell.innerHTML = newCell.innerHTML = InicioTabla("",0)+ITRTD("EM")+
                                 fFill(iDenta,SP())+cImg+FITD()+
                                 Liga(aRow[0],cNR,"...")+FTDTR()+
                                 FinTabla();
           //}
        }
      }
   }//Aqui acaba FOR
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
        fCierraDescendencia(aRow[3]);
     }
     fShow(lNodoRama);
  }
}
function fCierraDescendencia(iPadre){
   aCerrados = new Array();
   aCerrados[0]=iPadre;
   for(i=0;i<aArbol.length;i++){
     aRow = aArbol[i]
     if(fEsHijoCerrarNodo(aRow[2],aRow[3])==true){
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
function fSelNodo(iPos,iArray){
  if(lDisabled==false)
  {
    iCveSel = aArbol[iPos][1];
    for(hjlk=0;hjlk<tCpoArbol.rows.length;hjlk++)
      tCpoArbol.rows[hjlk].style.backgroundColor = cColorFondoArbol;
    tCpoArbol.rows[iArray-1].style.backgroundColor = cColorArbolSel;
    fSelProgram(aArbol[iPos][1],aArbol[iPos][0],iPos);
  }
}
function fFill(iNoChar,cChar){
   cTmp = '';
   for(iVer=0;iVer<iNoChar;iVer++){
      cTmp+=cChar;
   }
   return cTmp;
}

function fSelProgram(cNom,cTit,iRow){
   FRMPagina = fBuscaFrame("FRMPagina");
   FRMTitulo = fBuscaFrame('FRMTitulo');
   if(FRMPagina.document.forms[0]){
      fAbrePagina(cNom,FRMPagina.document.forms[0]);
      if(FRMTitulo && cTit.toUpperCase() != 'INICIO')
        FRMTitulo.fEsconder();
   }else{
      fAlert("No es posible llamar al programa solicitado.");
      FRMTitulo.fSalir();
   }
}

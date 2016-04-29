// MetaCD=1.0
var lDisable = false;
var tCpoListado;
var cTitulo = ',';
var cAlinea = '';
var cDespliega = '';
var aListado;
var FRMCtrl;
var iPosAnt = 0;
var cBGColor = '';
var cColor = '';
var aDat = new Array();
var cCual = '';
var aOCL = new Array();
var aColTit = new Array();
var aCols = new Array();
var iOCL = 0;
var iColTit = 0;
var iCols = 0;
var ifSelReg=1;
var cTab='';
var aSelect;
var cTextosLog = "SI|NO|";
var aCamposDespliega = new Array();
var aAnchosCampo = new Array();
function fBefLoad(){
   cPaginaWebJS = "Listado.js";
}
function fDefPag(){
   fInicioPagina("FFFFFF");
     Estilo(".ETablaSTL","COLOR:800000;background-color:#C0C0C0;font-family:Verdana;font-size:9pt;font-weight: normal;");
     Estilo("A:Link","COLOR:BLACK;font-family:Verdana;font-size:8pt;font-weight:normal;TEXT-DECORATION:none");
     Estilo("A:Visited","COLOR:BLACK;font-family: Verdana;font-size:8pt;font-weight:normal;TEXT-DECORATION:none");
     DinTabla("TListado","ETablaInfoLST",0,"100%","","center","","","1",".1");
   fFinPagina();
}
function fOnLoad(){ // Carga información al mostrar la página.
   theTable = (document.all) ? document.all.TListado:
   document.getElementById("TListado");
   tCpoListado= theTable.tBodies[0];
}
function fSetTitulo(cTit){
  cTitulo = cTit;
  for(i=0;tCpoListado.rows.length;i++){
    tCpoListado.deleteRow(0);
  }
  iTits = fNumEntries(cTitulo,',')+1;
  newRow  = tCpoListado.insertRow(0);
  if(iTits > 0){
    for(i=0;i<=iTits;i++){
       newCell = newRow.insertCell(i);
       newCell.innerHTML = fEntry(i+1,cTitulo,",");
       newCell.className = "ETablaSTL";
       newCell.align = "center";
    }
  }
}
function fSetCampos(cCuales){
  cCual=cCuales;
}
function fSetAlinea(cAlineas){
  cAlinea=cAlineas;
}
function fSetAnchosCampo(aAnchos){
  aAnchosCampo=aAnchos;
}
function fSetDespliega(cDespliegues, cTextosLogicos){
  if (cTextosLogicos)
    if (cTextosLogicos.length>0)
      cTextosLog = cTextosLogicos;
  cDespliega=cDespliegues;
}
function fSetObjs(iCol,cObj,vars){
	if(vars)
  		aOCL[iOCL] = [iCol,cObj,vars];
  	else{
  		aOCL[iOCL] = [iCol,cObj];
  	}
  iOCL++;
}
function fDelObjs(){
  aOCL = new Array();
  iOCL = 0;
}
function fSetColTit(iColM,cAlineacionM,cTamano){
  aColTit[iColTit]=[iColM,cAlineacionM,cTamano];
  iColTit++;
}
function fSetCol(iColM,cAlineacionM){
  aCols[iCols]=[iColM,cAlineacionM];
  iCols++;
}
function fSetListado(aRes){
  aListado = aRes;
}
function fShow(){
  aTextosLogicos = cTextosLog.split("|");
  if (aTextosLogicos.length != 3){
    aTextosLogicos = new Array();
    aTextosLogicos[0] = "SI";
    aTextosLogicos[1] = "NO";
    aTextosLogicos[2] = "";
  }
   for(i=0;tCpoListado.rows.length;i++){
     tCpoListado.deleteRow(0);
   }
   iTits = fNumEntries(cTitulo,',')+1;
   newRow  = tCpoListado.insertRow(0);
   if(iTits > 0){
     for(i=0;i<=iTits;i++){
        newCell = newRow.insertCell(i);
        newCell.innerHTML = fEntry(i+1,cTitulo,",");
        newCell.className = "ETablaSTL";
        newCell.align = "center";
        for(iCT=0;iCT<aColTit.length;iCT++){
           aCTTmp = aColTit[iCT];
           if(i==aCTTmp[0]){
              newCell.align=aCTTmp[1];
              if(""+aCTTmp[2] != 'undefined')
                newCell.width=aCTTmp[2];
           }
        }
     }
   }
   if(aListado.length > 0){
     if(cCual!='')
       iNoCual=fNumEntries(cCual,',');
     for(i=0;i<aListado.length;i++){
        aDato = aListado[i];
        newRow  = tCpoListado.insertRow(i+1);
        if((i%2) == 0)
          newRow.style.backgroundColor=cBGColorGrid;
        h = 0;j = 0;
        if(cCual==''){
          lKeep=true;
          while(lKeep==true){
            lObj = false;
            for(r=0;r<aOCL.length;r++){ //Objetos Columna
               aTOCL = aOCL[r];
               if(aTOCL[0] == h){
                 newCell = newRow.insertCell(h);
                 newCell.className = "EEtiquetaLST";
                 newCell.align = fEntry(h+1,cAlinea,",");
                 newCell.innerHTML = fDrawObj(aTOCL,h,i);
                 lObj = true;
               }
            }
            if(lObj==false && aDato.length > j){
               newCell = newRow.insertCell(h);
               newCell.className = "EEtiquetaLST";
               newCell.align = fEntry(h+1,cAlinea,",");
               cDatoRef = aDato[j]==''?SP():aDato[j];
               if (fEntry(h+1,cDespliega,",") == "logico")
                 cDatoRef = (cDatoRef == "1")?aTextosLogicos[0]:(cDatoRef == "0")?aTextosLogicos[1]:aTextosLogicos[2];
               newCell.innerHTML = Liga(cDatoRef,"fSelReg("+i+","+h+");",cDatoRef);
               for(iCT=0;iCT<aCols.length;iCT++){
                  aCTTmp = aCols[iCT];
                  if(j==aCTTmp[0]){
                     newCell.align=aCTTmp[1];
                  }
               }
               j++;
               lObj = true;
            }
            if(lObj==false)
              lKeep = false;
            if(lObj==true)
              h++;
          }
        }else{
          for(j=0;j<iNoCual;j++){
            for(r=0;r<aOCL.length;r++){ //Objetos Columna
               aTOCL = aOCL[r];
               if(aTOCL[0] == h){
                 newCell = newRow.insertCell(h);
                 newCell.className = "EEtiquetaLST";
                 newCell.align = fEntry(h+1,cAlinea,",");
                 newCell.innerHTML = fDrawObj(aTOCL,h,i);
                 h++;
               }
            }
            newCell = newRow.insertCell(h);
            newCell.className = "EEtiquetaLST";
            newCell.align = fEntry(h+1,cAlinea,",");
            iCol=parseInt(fEntry(j+1,cCual,","),10);
            cDatoRef = aDato[iCol]==''?SP():aDato[iCol];
            cFormaDespliega = fEntry(h+1,cDespliega,",");
               if (fEntry(h+1,cDespliega,",") == "logico")
                 cDatoRef = (cDatoRef == "1")?aTextosLogicos[0]:(cDatoRef == "0")?aTextosLogicos[1]:aTextosLogicos[2];
            newCell.innerHTML = Liga(cDatoRef,"fSelReg("+i+","+h+");",cDatoRef);
            for(iCT=0;iCT<aCols.length;iCT++){
              aCTTmp = aCols[iCT];
              if(j==aCTTmp[0]){
                 newCell.align=aCTTmp[1];
              }
            }
            h++;
          }
        }
     }
   }else{
     newRow  = tCpoListado.insertRow(1);
     newCell = newRow.insertCell(0);
     newCell.colSpan = iTits-1;
     newCell.className = "EEtiquetaC";
     newCell.innerHTML = "No se encontraron datos con el filtro proporcionado.";
     aDat=new Array();
     fSetDisabled(false);
   }
   
   fSelReg(aListado[0]);
}
function fDrawObj(aTOCL,h,iRw){
  if(aTOCL[1]=="Boton")
     return BtnImg("OBoton"+h+iRw,"bto_lista","fSelReg("+iRw+","+h+");");
  if(aTOCL[1]=="Liga"){
  	cTexto = "Ir a...";
  	cTooltip = "...";
  	cStyle = "";
  	if(aTOCL.length == 3){
  		if(aTOCL[2].label) 	cTexto = aTOCL[2].label;
  		if(aTOCL[2].toolTip) cTooltip = aTOCL[2].toolTip;
  		if(aTOCL[2].style) cStyle = aTOCL[2].style;
  	}
   return "<a href='JavaScript:fSelReg("+iRw+","+h+");' style='" + cStyle + "' alt='" + cTooltip + "'>" + cTexto + "</a>";
  }
  if(aTOCL[1]=="Radio")
     return Radio(false,"ORadio","",false,"...","",true,'onClick="fSelReg('+iRw+","+h+');"');
  if(aTOCL[1]=="RadioH")
     return Radio(false,"ORadiH"+iRw,h,false,"...","",true,'onClick="fSelReg('+iRw+","+h+');"');
  if(aTOCL[1]=="Caja")
     return CheckBox("OCajaB"+h+iRw,"",false,"...",".",'onClick="fSelReg('+iRw+","+h+',document.forms[0].OCajaB'+h+iRw+'.checked);"');
  if(aTOCL[1]=="Select"){
     cOnBlur ='onBlur=\'fBlurSelect('+iRw+','+h+',document.forms[0].OSeleB'+h+iRw+');\'';
     cOnChange = 'fSelReg(' + iRw + "," + h +
                   ',document.forms[0].OSeleB' + h + iRw +'.value);'
     return SelectList("OSeleB"+h+iRw,1,
                       cOnChange,false,cOnBlur);
  }
  if(aTOCL[1]=="Campo"){
    cAncho = "10";
    if(aAnchosCampo && aAnchosCampo.length)
      for(var x=0; x<aAnchosCampo.length; x++)
        if(aAnchosCampo[x][0] == h)
          cAncho = aAnchosCampo[x][1];
    cOnBlur = 'fBlurChangeCampo('+iRw+","+h+',document.forms[0].OCampB'+h+iRw+',\'onBlur\');';
    cOnChange = 'fBlurChangeCampo('+iRw+","+h+',document.forms[0].OCampB'+h+iRw+',\'onChange\');';
    if (aCamposDespliega){
      if(aCamposDespliega[iRw] == false)
        return Liga("","fSelReg("+i+","+h+");","");
      else
        return Text(false,"OCampB"+h+iRw,"",cAncho,"","",cOnBlur,"",cOnChange,false,true);
    }else
        return Text(false,"OCampB"+h+iRw,"",cAncho,"","",cOnBlur,"",cOnChange,false,true);
  }
  return "---";
}
function fSelReg(iPos,iCol,lChecked){
  if(lDisable == false){

    	   try{
    	     if(aListado.length > 0){
    	        aDat=aListado[iPos];
    	     }else{
    	     for(iDatB=0;iDatB<50;iDatB++)
    	        aDat[iDatB]='';
    	     }
    	   }catch(e){
    	     for(iDatB=0;iDatB<50;iDatB++)
    	       aDat[iDatB]='';
    	   }
    fSelRow(++iPos);
    if(FRMCtrl){
      if(ifSelReg == 1 && FRMCtrl.fSelReg){
        FRMCtrl.fSelReg(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 2 && FRMCtrl.fSelReg2){
        FRMCtrl.fSelReg2(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 3 && FRMCtrl.fSelReg3){
        FRMCtrl.fSelReg3(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 4 && FRMCtrl.fSelReg4){
        FRMCtrl.fSelReg4(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 5 && FRMCtrl.fSelReg5){
        FRMCtrl.fSelReg5(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 6 && FRMCtrl.fSelReg6){
        FRMCtrl.fSelReg6(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 7 && FRMCtrl.fSelReg7){
        FRMCtrl.fSelReg7(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 8 && FRMCtrl.fSelReg8){
        FRMCtrl.fSelReg8(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 10 && FRMCtrl.fSelReg10){
        FRMCtrl.fSelReg10(aDat,iCol,lChecked,iPos);
      }
      if(ifSelReg == 9 && FRMCtrl.fSelReg9){
        FRMCtrl.fSelReg9(aDat,iCol,lChecked,iPos);
      }
    }
  }
}
function fSelRow(iPos){
  if (iPosAnt == 1 && cBGColor == "")
    cBGColor = cBGColorGrid;
  if(iPosAnt > 0 && tCpoListado.rows.length > iPosAnt){
    tCpoListado.rows[iPosAnt].style.backgroundColor=cBGColor;
    tCpoListado.rows[iPosAnt].style.color=cColor;
  }
  iPosAnt  = iPos;
  try{
    cBGColor = tCpoListado.rows[iPos].style.backgroundColor;
    cColor   = tCpoListado.rows[iPos].style.color;
    tCpoListado.rows[iPos].style.backgroundColor = cBGColorSelList;
    tCpoListado.rows[iPos].style.color = cFGColorSelList;
  }catch(e){}
}
function fSetControl(oControlM){
   FRMCtrl = oControlM;
}
function fSetDisabled(lDis,lSelReg){
   lDisable = lDis;
   if(lSelReg == false);
   else
     fSelReg(iPosAnt-1,0);
   frm=document.forms[0];
   for(qwe=0;qwe<frm.elements.length;qwe++){
     obj=frm.elements[qwe];
     if (obj.type == 'checkbox' ||  obj.type == 'radio' || obj.type == 'text')
        obj.disabled=lDis;
   }
}
function fSetLlave(cLlave){
  if(cLlave){
    iNum=fNumEntries(cLlave+',',',');
    if(iNum>0&&aListado.length>0){
      for(i=0;i<aListado.length;i++){
        aTmp=aListado[i];
        lSelected=true;
        for(j=0;j<iNum;j++){
          if((""+aTmp[j])!=fEntry(j+1,cLlave,","))
            lSelected=false;
        }
        if(lSelected==true){
          fSelReg(i,""+aTmp);
          break;
        }
      }
    }
  }
}

function fGetLength(){
	 try{
	  if(aListado.length >= 0){
	   return aListado.length;
	  }
	 }catch(e){
	   return 0;
	 } 
}
function fSetSelReg(iSelReg){
  ifSelReg = iSelReg;
}
function fShowTableRows(cTablaJSP){
   for(i=0;tCpoListado.rows.length;i++){
     tCpoListado.deleteRow(0);
   }
   newRow  = tCpoListado.insertRow(0);
   newCell = newRow.insertCell(0);
   iTits = fNumEntries(cTitulo,',');
   newRow  = tCpoListado.insertRow(0);
   if(iTits > 0){
     cTitRows='<TR CLASS="ETablaSTL">';
     for(i=0;i<=iTits;i++){
       cTitRows += "<TD>" + fEntry(i+1,cTitulo,",") + "</TD>";
     }
     cTitRows+="</TR>";
   }
   cTable = '<TABLE WIDTH="100%" CLASS="EEtiquetaC" BORDER=1 cellspacing=".1" cellpadding=".1">'+
            cTitRows+
            cTablaJSP+
            '</TABLE>';
   newCell.innerHTML = cTable;
}
function fGetARes(){
   return aListado;
}
function fGetObjs(iCol){
   aValores = new Array();
   cCol=""+iCol;
   iLCol=cCol.length;
   iRenObj = 0;
   frm = document.forms[0];
   for(i=0;i<frm.elements.length;i++){
     objN = frm.elements[i].name;
     if(objN.length >= (6+iLCol) && objN.substring(6,6+iLCol) == cCol){
       if(objN.substring(0,6) == "OCajaB"){
         aValores[iRenObj++]=frm.elements[i].checked;
       }
       if(objN.substring(0,6) == "OCampB"){
          aValores[iRenObj++]=frm.elements[i].value;
       }
       if(objN.substring(0,6) == "OSeleB"){
          aValores[iRenObj++]=frm.elements[i].value;
       }
     }
     else
     {
       if(objN.substring(0,6) == "ORadio")
       {
         aValores[iRenObj++]=frm.elements[i].checked;
       }
     }
   }
   return aValores;
}

function fGetFilesValues(){
	   var valFiles="";
	   frm = document.forms[0];
	   for(i=0;i<frm.elements.length;i++){
		   var objCamp = frm.elements[i];
		   if (objCamp.type == 'file')
			   valFiles+=objCamp.value +",";
	   }
	   return valFiles;
	}


function fGetRadioValuesH(){
  aValores = new Array();
  if(fGetARes() && fGetARes().length){
    for(var i=0; i<fGetARes().length; i++){
      aObj = eval("document.forms[0].ORadiH" + i);
      if(aObj && aObj.length){
        for (var j=0; j<aObj.length; j++)
          if (aObj[j].checked){
            aValores[aValores.length]=aObj[j].value;
            break;
          }
        if(aValores.length-1 != i)
          aValores[aValores.length] = null;
      }else
        aValores[aValores.length] = (aObj && aObj.checked)?aObj.value:null;
    }
  }
  return aValores;
}

function fSetDefaultRadioValuesH(aValores){
  for (var i=0; i<aValores.length; i++){
    aObj = eval("document.forms[0].ORadiH" + i);
    if(aObj){
      if(aObj.length){
        for (var j=0; j<aObj.length; j++)
          aObj[j].checked = (aObj[j].value == aValores[i])?true:false;
      }else
        aObj.checked = (aObj.value == aValores[i])?true:false;
    }
  }
}

function fSetDefaultValues(iColObj, iColValues, aValues){
   cCol=""+iColObj;
   iLCol=cCol.length;
   iRenObj = 0;
   frm = document.forms[0];
   for(i=0;i<frm.elements.length;i++){
     objN = frm.elements[i].name;
     if(objN.length >= (6+iLCol) && objN.substring(6,6+iLCol) == cCol)
       if(objN.substring(0,6) == "OCajaB"){
         if (!aValues)
           frm.elements[i].checked = (aListado[iRenObj++][iColValues]==1)?true:false;
         else{
           frm.elements[i].checked = (aValues[iRenObj]==1)?true:false;
           iRenObj++;
         }
       }
       if(objN.substring(0,6+iLCol) == "OCampB"+iColObj){
         if (!aValues){
           if(aListado && aListado.length)
             frm.elements[i].value = aListado[iRenObj++][iColValues];
         }else{
           frm.elements[i].value = aValues[iRenObj];
           iRenObj++;
         }
       }

       if(objN.substring(0,6+iLCol) == "OSeleB"+iColObj){
         if (!aValues){
           if(aListado && aListado.length)
             fSelectSetIndexFromValue(frm.elements[i],aListado[iRenObj++][iColValues]);
         }else{
           fSelectSetIndexFromValue(frm.elements[i],0);
           iRenObj++;
         }
       }
   }
}

function fSetSelValues(aSlct,iColC,iCveSel,iCvePos,iTextPos){
  frm = document.forms[0];
  aSelect = aSlct;
  cSelObjName = "OSeleB"+iColC;
  iLength = cSelObjName.length;
  for(imc=0;imc<frm.elements.length;imc++){
    if(frm.elements[imc].name.substring(0,iLength) == cSelObjName && frm.elements[imc].type == 'select-one'){
      fFillSelect(frm.elements[imc],aSlct,false,iCveSel,iCvePos,iTextPos);
    }
  }
}


function fSetCamposDespliega(aCampos){
  aCamposDespliega = aCampos;
}

function fBlurChangeCampo(iPos,iCol,objCampo,cEvento){
  if(lDisable == false){
    if(''+aListado=='undefined' || ''+aListado==''){
       for(iDatB=0;iDatB<50;iDatB++)
          aDat[iDatB]='';
    }else
       aDat=aListado[iPos];
    fSelRow(++iPos);
    if(FRMCtrl){
      if(ifSelReg == 1 && FRMCtrl.fBlurChangeCampo){
        FRMCtrl.fBlurChangeCampo(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 2 && FRMCtrl.fBlurChangeCampo2){
        FRMCtrl.fBlurChangeCampo2(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 3 && FRMCtrl.fBlurChangeCampo3){
        FRMCtrl.fBlurChangeCampo3(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 4 && FRMCtrl.fBlurChangeCampo4){
        FRMCtrl.fBlurChangeCampo4(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 5 && FRMCtrl.fBlurChangeCampo5){
        FRMCtrl.fBlurChangeCampo5(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 6 && FRMCtrl.fBlurChangeCampo6){
        FRMCtrl.fBlurChangeCampo6(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 7 && FRMCtrl.fBlurChangeCampo7){
        FRMCtrl.fBlurChangeCampo7(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 8 && FRMCtrl.fBlurChangeCampo8){
        FRMCtrl.fBlurChangeCampo8(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 9 && FRMCtrl.fBlurChangeCampo9){
        FRMCtrl.fBlurChangeCampo9(aDat,iCol,objCampo,cEvento);
      }
      if(ifSelReg == 10 && FRMCtrl.fBlurChangeCampo10){
        FRMCtrl.fBlurChangeCampo10(aDat,iCol,objCampo,cEvento);
      }
    }
  }
}


function fBlurSelect(iPos,iCol,objCampo){
  if(lDisable == false){
    if(''+aListado=='undefined' || ''+aListado==''){
       for(iDatB=0;iDatB<50;iDatB++)
          aDat[iDatB]='';
    }else
       aDat=aListado[iPos];
    fSelRow(++iPos);
    if(FRMCtrl){
      if(ifSelReg == 1 && FRMCtrl.fBlurSelect){
        FRMCtrl.fBlurSelect(aDat,iPos, iCol,objCampo);
      }
      if(ifSelReg == 2 && FRMCtrl.fBlurSelect2){
        FRMCtrl.fBlurSelect2(aDat,iPos, iCol,objCampo);
      }
      if(ifSelReg == 3 && FRMCtrl.fBlurSelect3){
        FRMCtrl.fBlurSelect3(aDat,iPos, iCol,objCampo);
      }
      if(ifSelReg == 4 && FRMCtrl.fBlurSelect4){
        FRMCtrl.fBlurSelect4(aDat,iPos, iCol,objCampo);
      }
    }
  }
}

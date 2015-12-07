var cGPD = "";
var aImgs = new Array();
var iImgs = -1;
var oFechaLocal;
var wExp;
var cPaginaWebJS="";

//7000 Objetos Compuestos
function TDEtiCampo(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,Campo
   cMan = '0-'; if(lMandatorioM == true){cMan = '1-';cEtiquetaEM="*"+cEtiquetaEM;}
   Hidden('HDMF-'+cMan+cNombreM,cEtiquetaEM);
   iTipo = fTipoDato(cNombreM); cHora = "";if(cNombreM.substring(0,1).toUpperCase() == "H") cHora = "(HH:MM)";
   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM+cHora)+FITD(cEstiloCM, iColExtiendeCM)+
          Text(0,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus)+FTD();
}
function TDEtiPwd(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,Pwd
   cMan = '0-'; if(lMandatorioM == true){cMan = '1-';cEtiquetaEM="*"+cEtiquetaEM;}
   Hidden('HDMF-'+cMan+cNombreM,cEtiquetaEM);
   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
          Pwd(0,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus)+FTD();
}
function TDEtiAreaTexto(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo,lContador,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,AreaTexto
   cMan = '0-'; if(lMandatorioM == true){cMan='1-';cEtiquetaEM="*"+cEtiquetaEM;}
   Hidden('HDMF-'+cMan+cNombreM,cEtiquetaEM);
   cATComp = ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
          AreaTexto(0,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo);
   if(lContador!=false)
      cATComp += SP()+Text(0,"AuxTxt"+cNombreM,"",4,4);
   cATComp += FTD();
   return cATComp;
}
function TDEtiSelect(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cOnChange,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,Select
   cMan = '0-'; if(lMandatorioM == true){cMan='1-';cEtiquetaEM="*"+cEtiquetaEM;}
   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
          Select(cNombreM,cOnChange)+FTD();
}
function TDEtiSelectList(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,iSizeM,cOnChange,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,SelectList
   cMan = '0-'; if(lMandatorioM == true){cMan='1-';cEtiquetaEM="*"+cEtiquetaEM;}
   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
          SelectList(cNombreM,iSizeM,cOnChange)+FTD();
}
function TDEtiTexto(cEstiloEM,iColExtiendeEM,cEtiquetaEM,cTextoM,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,TextoSimple
   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+TextoSimple(cTextoM)+FTD();
}
function TDEtiCheckBox(cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,CheckBox
   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
          CheckBox(cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo)+FTD();
}
function TDEtiFile(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,Campo
   cMan = '0-';
   if(lMandatorioM == true){
     cMan = '1-';
     cEtiquetaEM= "*" + cEtiquetaEM;
   }
   Hidden('HDMF-'+cMan+cNombreM,cEtiquetaEM);
   iTipo = fTipoDato(cNombreM);
   cHora = "";
   if(cNombreM.substring(0,1).toUpperCase() == "H")
     cHora = "(HH:MM)";
   return ITD(cEstiloEM,iColExtiendeEM)  + TextoSimple(cEtiquetaEM + cHora)  +
          FITD(cEstiloCM, iColExtiendeCM)+ fInput("file",cNombreM,cValorM) + FTD();
}

//3000 Objetos de Edición
function Img(cImagenM,cEstatus){ // 3000-Objetos de Imagen
  iImgs++;
  aImgs[iImgs] = cRutaImgServer+cImagenM;
  cTx='<img SRC="'+cRutaImgServer+cImagenM+'"';
  if(cEstatus){
    if(cEstatus != "")
       cTx+=' alt="' + cEstatus + '"';
  }
  cTx+='>';
  cGPD = cGPD + cTx + "\n";
  return cTx;
}
function ImgNom(cNombreM,cImagenM,cEstatus){ // 3000-Objetos de Imagen con un nombre
  iImgs++;
  aImgs[iImgs] = cRutaImgServer+cImagenM;
  cTx='<img border="0" name="'+cNombreM+'" src="'+cRutaImgServer+cImagenM+'"';
  if(cEstatus){
    if(cEstatus != "")
       cTx+=' alt="' + cEstatus + '"';
  }
  cTx+='>';
  cGPD+=cTx+"\n";
  return cTx;
}
function BtnImg(cNombreM,cNomImgM,cHRefM,cEstatusM,l4Status,cImgIni){ // 3000-botón de tipo imagen
   iImgs++;
   aImgs[iImgs] = cRutaImgServer+cNomImgM;
   var imgIni = "01";
   cTx='<a href="JavaScript:'+cHRefM+'"'+"\n";
   if(l4Status == true){
     cTx+=' onMouseOut="'+"if(fMouseOut)fMouseOut(document, '"+cNomImgM+"');self.status='';"+'return true;"'+"\n";
     cTx+=' onMouseOver="'+"if(fMouseOver)fMouseOver(document, '"+cNomImgM+"');self.status='"+cEstatusM+"';"+'return true;">'+"\n";
   }else{
     cTx+=' onMouseOut="'+"if(fCambiaImagen)fCambiaImagen(document,'"+cNombreM+"','','"+cRutaImgServer+cNomImgM+"01.gif',1);self.status='';" + 'return true;"'+"\n";
     cTx+=' onMouseOver="'+"if(fCambiaImagen)fCambiaImagen(document,'"+cNombreM+"','','"+cRutaImgServer+cNomImgM+"02.gif',1);self.status='"+cEstatusM+"';" + 'return true;">'+"\n";
   }
   if (cImgIni && cImgIni != "")
     imgIni = cImgIni;
   cTx+='<img border="0" name="'+cNombreM+'" src="'+cRutaImgServer+cNomImgM+ imgIni+'.gif" alt="' + cEstatusM + '">';
   cTx+='</a>'+"\n";
   cGPD+=cTx;
   return cTx;
}
function Etiqueta(cIDM,cEstiloM,cValor){ // 3000-Etiqueta editable
   cTx='<P class="'+cEstiloM+'" ID="'+cIDM+'">';
   if(cValor){
     cTx+=cValor;
   }
   cTx+='</P>'+"\n";
   cGPD+=cTx;
   return cTx;
}
function Hidden(cNombreM,cValorM){ // 3000-Campos Ocultos
  if(!cValorM)
    cValorM = '';
  cTx='<input type="hidden" name="'+cNombreM+'" value="'+cValorM+'">';
  cGPD+=cTx+"\n";
  return cTx;
}
function Text(lMandatorioM,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo){ // 3000-Campo de Texto
   cMan = '0-';
   if(lMandatorioM == true)
      cMan = '1-';
   if(lMandatorioM != 0)
      Hidden('HDMF-'+cMan+cNombreM,''+cToolTip);
   return fInput("text", cNombreM, '"'+cValorM+'"', iLargoM, iMaxCaracteresM, cToolTip, cOnBlur, cOnAnyEvent, cOnChange, lSelectOnFocus, lActivo)
}
function Pwd(lMandatorioM,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo){ // 3000-Campo de Contraseña
   cMan = '0-';
   if(lMandatorioM == true)
      cMan = '1-';
   if(lMandatorioM != 0)
      Hidden('HDMF-'+cMan+cNombreM,''+cToolTip);
   return fInput("password", cNombreM, '"'+cValorM+'"', iLargoM, iMaxCaracteresM, cToolTip, cOnBlur, cOnAnyEvent, cOnChange, lSelectOnFocus, lActivo)
}
function fInput(cTipo,cNombreM,cValorM,iLargo,iMaxCaracteres,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo){ // no
      cTx='<input type="'+cTipo+'" name="'+cNombreM+'" value='+cValorM+' ';
      if(iLargo){
         if(iLargo != ""){
            if(cNombreM.substring(0,2).toLowerCase() == 'dt')
               cTx+=' size="12"';
            else
               cTx+=' size="'+iLargo+'"';
         }
      }
      if(iMaxCaracteres){
         if(iMaxCaracteres != ""){
            if(cNombreM.substring(0,2).toLowerCase() == 'dt')
               cTx+=' maxlength="10"';
            else
               cTx+=' maxlength="'+iMaxCaracteres+'"';
         }
      }
      if(lActivo){
         if(lActivo == false)
            cTx+=" disabled";
      }
      if(lSelectOnFocus){
         if(lSelectOnFocus == true)
            cTx+=' onfocus="this.select();"';
      }
      if(cOnChange){
         if(cOnChange != "")
           cTx+=' onChange="' + cOnChange + '"';
      }
      if(cOnBlur){
        if(cOnBlur != "")
           cTx+=' onBlur="'+cOnBlur+'"';
      }
      if(cOnAnyEvent){
         if(cOnAnyEvent != "")
           cTx+=" " + cOnAnyEvent;
      }
      if(cToolTip){
         if(cToolTip != "")
            cTx+=' onMouseOut="' + "top.status='';" + '" onMouseOver="' + "top.status='" + cToolTip + "...';" + '"';
      }
      cTx+='>';
      cGPD+=cTx+"\n";
      if(cNombreM.substring(0,2).toLowerCase() == 'dt'){
        cTx+=Liga("dd/mm/aaaa","fLoadCal(document.forms[0]."+cNombreM+");","Selección de Fechas...");
      }
      return cTx;
}
function Liga(cNombreM,cHRefM,cEstatusM){ // 3000-Botón de tipo imagen
   cTx='<a href="JavaScript:'+cHRefM+'"'+"\n";
   cTx+=' onMouseOut="' + "self.status='';" + 'return true;"'+"\n";
   cTx+=' onMouseOver="' + "self.status='"+cEstatusM+"';" + 'return true;">'+"\n";
   cTx+=cNombreM+"\n";
   cTx+='</a>'+"\n";
   cGPD+=cTx;
   return cTx;
}
function CheckBox(cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,lActivo){ // 3000-Caja de Selección
      cTx='<input type="checkbox" name="'+cNombreM+'" value='+cValorM+' ';
      if(lSeleccion){
         if(lSeleccion == false)
            cTx+=" checked";
      }
      if(lActivo){
         if(lActivo == false)
            cTx+=" disabled";
      }
      if(lSelectOnFocus){
         if(lSelectOnFocus == true)
            cTx+=' onfocus="this.select();"';
      }
      if(cOnChange){
         if(cOnChange != "")
           cTx+=' onChange="' + cOnChange + '"';
      }
      if(cOnBlur){
         if(cOnBlur != "")
           cTx+=' onBlur="'+cOnBlur+'"';
      }
      if(cOnAnyEvent){
         if(cOnAnyEvent != "")
           cTx+=" " + cOnAnyEvent;
      }
      if(cToolTip){
         if(cToolTip != "")
            cTx+=' onMouseOut="' + "top.status='';" + '" onMouseOver="' + "top.status='" + cToolTip + "...';" + '"';
      }
      cTx+='>';
      cGPD+=cTx+"\n";
      return cTx;
}
function Radio(lMandatorioM, cNombreM, cValorM, lChecked, cToolTip, cOnSelected, lActivo, cOnAnyEvent, cOnChange, lSelectOnFocus){ // 3000-Campo tipo radio
   cMan = '0-';
   if(lMandatorioM == true)
      cMan = '1-';
   Hidden('HDMF-'+cMan+cNombreM,cToolTip);
      cTx='<input type="radio" name="'+cNombreM+'" value='+cValorM+' ';
      if(lChecked){
         if(lChecked == true)
            cTx+=" checked";
      }
      if(cToolTip){
         if(cToolTip != "")
            cTx+=' onMouseOut="' + "top.status='';" + '" onMouseOver="' + "top.status='" + cToolTip + "...';" + '"';
      }
      if(cOnSelected){
         if(cOnSelected != "")
           cTx+=' onSelected="'+cOnSelected+'"';
      }
      if(lActivo){
         if(lActivo == false)
            cTx+=" disabled";
      }
      if(cOnAnyEvent){
         if(cOnAnyEvent != "")
           cTx+=" " + cOnAnyEvent;
      }
      if(cOnChange){
         if(cOnChange != "")
           cTx+=' onChange="' + cOnChange + '"';
      }
      if(lSelectOnFocus){
         if(lSelectOnFocus == true)
            cTx+=' onfocus="this.select();"';
      }
      cTx+='>';
      cGPD+=cTx+"\n";
      return cTx;
}
function TextoSimple(cTextoM){ // 3000-Establece un texto simple
   cTx = cTextoM;
   cGPD+=cTx+"\n";
   return cTx;
}
function AreaTexto(lMandatorioM,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo){ // 3000-Componente de tipo Area Texto
   cMan = '0-';
   if(lMandatorioM == true)
      cMan = '1-';
   if(lMandatorioM != 0)
      Hidden('HDMF-'+cMan+cNombreM,''+cToolTip);
   cTx='<textarea cols="'+iColM+'" rows="'+iRengM+'" name="'+cNombreM+'" value="'+cValueM+'" ';
   if(lActivo){
      if(lActivo == false)
         cTx+=' disabled ';
   }
   if(lSelectonFocus) {
      if(lSelectonFocus == true)
         cTx+=' onfocus="this.select();" ';
   }
   if(cOnChange){
      if(cOnChange != "")
         cTx+=' onChange="'+cOnChange+'" ';
   }
   if(cOnBlur){
      if(cOnBlur != "")
         cTx+=' onBlur="'+cOnBlur+'" ';
   }
   if(cOnAnyEvent){
      if(cOnAnyEvent != "")
         cTx+=" " + cOnAnyEvent;
   }
   if(cToolTip){
      if(cToolTip != ""){
         cTx+=' onMouseOut="' + "self.status='';" + 'return true;"';
         cTx+=' onMouseOver="' + "self.status='"+cToolTip+"';" + 'return true;"';
      }
   }
   cTx+='></textarea>';
   cGPD+=cTx+"\n";
   return cTx;
}
function Select(cNombreM,cOnChange){ // 3000-Lista Desplegable simple
   cTx='<SELECT NAME="'+cNombreM+'" SIZE="1" ';
   if(cOnChange){
      if(cOnChange != "")
         cTx+=' onChange="'+cOnChange+'" ';
   }
   cTx+='></SELECT>';
   cGPD+=cTx+"\n";
   return cTx;
}
function SelectList(cNombreM,iSizeM,cOnChange){ // 3000-Lista simple
   cTx='<SELECT NAME="'+cNombreM+'" SIZE="'+iSizeM+'" ';
   if(cOnChange){
      if(cOnChange != "")
         cTx+=' onChange="'+cOnChange+'" ';
   }
   cTx+='></SELECT>';
   cGPD+=cTx+"\n";
   return cTx;
}
function fAsignaSelect(oSelectM,iCveM,cCharM){// 3000-Llena al select con un solo valor caracter
  oSelectM.length=1;
  oSelectM[0].text  = cCharM;
  oSelectM[0].value = iCveM;
}
function fAsignaCheckBox(oSelectM,iDatoM){// 3000-Llena al checkbox con true si Dato es 1 y False se Dato es 0
  if(parseInt(iDatoM,10)==1)
    oSelectM.checked=true;
  else
    oSelectM.checked=false;
}
function fFillSelect(oSelectM,aVarM,lSelMsg,iCveSel,iCvePos,iTxtPos){ // 3000-Llena un select o Select-list con los datos del vector
   sIndex = 0;
   i=0; largo = aVarM.length; j=0;
   oSelectM.length = largo + 1;
   if(lSelMsg){
     if(lSelMsg == true){
       i=1;
       largo = largo + 1;
       oSelectM[0].text  = 'Seleccione...';
       oSelectM[0].value = -1;
     }
   }
   for(;i<largo;i++){
     aTmp = aVarM[j];
     if((''+aTmp[0]) != 'undefined'){
       if(iCvePos >= 0)
         oSelectM[i].value = aTmp[iCvePos];
       else
        oSelectM[i].value = aTmp[0];

       if(iTxtPos >= 0)
         oSelectM[i].text  = aTmp[iTxtPos];
       else
         oSelectM[i].text  = aTmp[1];

       if(iCveSel){
         if(iCvePos){
           if((''+iCveSel) == (''+aTmp[iCvePos]))
             sIndex = j;
         }else{
           if((''+iCveSel) == (''+aTmp[0]))
             sIndex = j;
         }
       }
     }else{
       oSelectM[i].value = aTmp;
       oSelectM[i].text  = aTmp;
       if(iCveSel){
         if((''+iCveSel) == (''+aTmp))
             sIndex = j-1;
       }
     }j++;
   }
   if((j == 0) || (j == 0 && lSelMsg == true))
     oSelectM[0].text  = "No existen datos";
   else
     oSelectM.selectedIndex = sIndex;
   oSelectM.selectedIndex = sIndex;
   oSelectM.length = largo;
}
function fFilterSelect(oSelectM,aVarM,cFiltro,lSelMsg,iCveSel,iCvePos,iTxtPos){ // 3000-Llena un select o Select-list con los datos del vector y un campo para filtrado
   sIndex = 0; iFilterSel = 0;
   largo = aVarM.length;
   oSelectM.length = largo + 1;
   if(lSelMsg){
     if(lSelMsg == true){
       iFilterSel=1;
       oSelectM[0].text  = 'Seleccione...';
       oSelectM[0].value = -1;
     }
   }
   for(i=0;i<largo;i++){
     aTmp = aVarM[i];
     if((''+aTmp[0]) == (''+cFiltro)){
          if(iCvePos >= 0)
            oSelectM[iFilterSel].value = aTmp[iCvePos];
          else
            oSelectM[iFilterSel].value = aTmp[0];

          if(iTxtPos >= 0)
            oSelectM[iFilterSel].text  = aTmp[iTxtPos];
          else
            oSelectM[iFilterSel].text  = aTmp[1];
        if(iCveSel){
          if(iCvePos){
            if((''+iCveSel) == (''+aTmp[iCvePos]))
              sIndex = iFilterSel;
           }else{
              if((''+iCveSel) == (''+aTmp[0]))
                sIndex = iFilterSel;
           }
        }
        iFilterSel++;
     }
   }
   if((iFilterSel == 0) || (iFilterSel == 1 && lSelMsg == true))
     oSelectM[0].text  = "No existen datos";
   else
     oSelectM.selectedIndex = sIndex;
   oSelectM.length = iFilterSel;
}
function SP(){ // 3000-Espacio En Blanco en lugar de &nbsp;
   cTx='&nbsp;';
   cGPD+=cTx;
   return cTx;
}
function BR(){// no
   cTx='<BR>';
   cGPD+=cTx;
   return cTx;
}
function Estilo(cNombreM,cDefineM){ // 3000-Definición de algún estilo de Texto de forma local
   cTx='<style>'+cNombreM+'{'+cDefineM+'}</style>';
   cGPD+=cTx+"\n";
   return cTx;
}
function Marquee(cMsgM,cClassM,cAlignM,cDelayM,cDireccionM,cAnchoM,cAltoM){ // 3000-Generación de un Marquee
   cTx='<marquee class="'+cClassM+'" align="'+cAlignM+'" scrollamount="1" scrolldelay="'+cDelayM+'" direction="';
   cTx+=cDireccionM+'" width="'+cAnchoM+'" height="'+cAltoM+'">'+cMsgM+'</marquee>';
   cGPD+=cTx+"\n";
   return cTx;
}
//4000 Tablas
function InicioTabla(cEstiloM,iBordeM,cAncho,cAlto,cAlign,cImgFondo,iEspacioCel,iRellenoCel,cBGColor){ // 4000-Genera la especificación de una tabla dinámica
   cTx='<table border="'+iBordeM+'"';
   if(cEstiloM){
     if(cEstiloM != "")
       cTx+=' class="'+cEstiloM+'"';
   }
   if(cAncho){
     if(cAncho != ""){
       if(cAncho.substring(0,1).toUpperCase() == "P")
         cAncho = cAncho.substring(1) + "%";
       cTx+=' width="'+cAncho+'"';
     }
   }
   if(cAlto){
     if(cAlto != ""){
       if(cAlto.substring(0,1).toUpperCase() == "P")
         cAlto = cAlto.substring(1) + "%";
       cTx+=' height="'+cAlto+'"';
     }
   }
   if(iEspacioCel){
     if(iEspacioCel != 0)
       cTx+=' cellspacing="'+iEspacioCel+'"';
   }
   if(iRellenoCel){
     if(iRellenoCel != 0)
       cTx+=' cellpadding="'+iRellenoCel+'"';
   }
   if(cImgFondo){
     if(cImgFondo != '')
       cTx+=' background="'+cRutaImgServer+cImgFondo+'"';
   }
   if(cBGColor){
     if(cBGColor != '')
       cTx+=' bgcolor="'+cBGColor+'"';
   }
   if(cAlign){
     if(cAlign != '')
       cTx+=' align="'+cAlign+'"';
   }
   cTx+='>';
   cGPD+=cTx+"\n";
   return cTx;
}
function DinTabla(cIDM,cEstiloM,iBordeM,cAncho,cAlto,cAlign,cValign,cImgFondo,iEspacioCel,iRellenoCel,cBGColor){ // 4000-Genera la especificación de una tabla
   cTx='<table border="'+iBordeM+'"';
   if(cIDM){
     if(cIDM != "")
       cTx+=' ID="'+cIDM+'"';
   }
   if(cEstiloM){
     if(cEstiloM != "")
       cTx+=' class="'+cEstiloM+'"';
   }
   if(cAncho){
     if(cAncho != ""){
       if(cAncho.substring(0,1).toUpperCase() == "P")
         cAncho = cAncho.substring(1) + "%";
       cTx+=' width="'+cAncho+'"';
     }
   }
   if(cAlto){
     if(cAlto != ""){
       if(cAlto.substring(0,1).toUpperCase() == "P")
         cAlto = cAlto.substring(1) + "%";
       cTx+=' height="'+cAlto+'"';
     }
   }
   if(iEspacioCel){
     if(iEspacioCel != 0)
       cTx+=' cellspacing="'+iEspacioCel+'"';
   }
   if(iRellenoCel){
     if(iRellenoCel != 0)
       cTx+=' cellpadding="'+iRellenoCel+'"';
   }
   if(cImgFondo){
     if(cImgFondo != '')
       cTx+=' background="'+cRutaImgServer+cImgFondo+'"';
   }
   if(cAlign){
     if(cAlign != '')
       cTx+=' align="'+cAlign+'"';
   }
   if(cValign){
     if(cAlign != '')
       cTx+=' valign="'+cValign+'"';
   }
   if(cBGColor){
     if(cBGColor != '')
       cTx+=' bgcolor="'+cBGColor+'"';
   }
   cTx+='><tr><td>&nbsp;</td></tr></table>';
   cGPD+=cTx+"\n";
   return cTx;
}
function ITR(cEstilo,cAncho,cAlto,cAlign,cValign,iRenExtiende,cAnyEvent){ // 4000-Genera la definición de un nuevo renglón de una tabla
   cTx='<tr';
   if(cEstilo){
     if(cEstilo != "")
       cTx+=' class="'+cEstilo+'"';
   }
   if(cAncho){
     if(cAncho != ""){
       if(cAncho.substring(0,1).toUpperCase() == "P")
         cAncho = cAncho.substring(1) + "%";
       cTx+=' width="'+cAncho+'"';
     }
   }
   if(cAlto){
     if(cAlto != ""){
       if(cAlto.substring(0,1).toUpperCase() == "P")
         cAlto = cAlto.substring(1) + "%";
       cTx+=' height="'+cAlto+'"';
     }
   }
   if(cAlign){
     if(cAlign != '')
       cTx+=' align="'+cAlign+'"';
   }
   if(cValign){
     if(cValign != '')
       cTx+=' valign="'+cValign+'"';
   }
   if(iRenExtiende){
     if(iRenExtiende != 0)
       cTx+=' rowspan="'+iRenExtiende+'"';
   }
   if(cAnyEvent){
     if(cAnyEvent != '')
       cTx+=cAnyEvent;
   }
   cTx+=">";
   cGPD+=cTx+"\n";
   return cTx;
}
function ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign){ // 4000-Genera la definición de una columna
   cTx='<td';
   if(cEstilo){
     if(cEstilo != "")
       cTx+=' class="'+cEstilo+'"';
   }
   if(cAncho){
     if(cAncho != ""){
       if(cAncho.substring(0,1).toUpperCase() == "P")
         cAncho = cAncho.substring(1) + "%";
       cTx+=' width="'+cAncho+'"';
     }
   }
   if(iColExtiende){
     if(iColExtiende != 0)
       cTx+=' colspan="'+iColExtiende+'"';
   }
   if(cAlto){
     if(cAlto != ""){
       if(cAlto.substring(0,1).toUpperCase() == "P")
         cAlto = cAlto.substring(1) + "%";
       cTx+=' height="'+cAlto+'"';
     }
   }
   if(cAlign){
     if(cAlign != '')
       cTx+=' align="'+cAlign+'"';
   }
   if(cValign){
     if(cValign != '')
       cTx+=' valign="'+cValign+'"';
   }
   cTx+=">";
   cGPD+=cTx+"\n";
   return cTx;
}
function ITRTD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign){ // 4000-Genera la definición de un renglón y una columna
   return (ITR() + ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign));
}
function FTD(){ // 4000-Genera el fin de una columna
   cTx="</td>";
   cGPD+=cTx+"\n";
   return cTx;
}
function FITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign){ // 4000-Genera la definición del fin de una columna y el inicio de Otra
   cTx = FTD();
   cTx = ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign);
   return cTx;
}
function FTDTR(){ // 4000-Genera la finalización de una columna y de un renglón de una tabla
   cTx="</td></tr>";
   cGPD+=cTx+"\n";
   return cTx;
}
function FITR(){ // 4000-Genera la finalización del renglón de una tabla e inserta uno nuevo
   cTx="</tr><tr>";
   cGPD+=cTx+"\n";
   return cTx;
}
function FTR(){ // 4000-Genera la finalización del renglón de una tabla
   cTx="</tr>";
   cGPD+=cTx+"\n";
   return cTx;
}
function FinTabla(){ // 4000-Genera la finalización de una tabla
   cTx="</table>";
   cGPD+=cTx+"\n";
   return cTx;
}
//5000 Control de Páginas
function fAbrePagina(cNombreM,oFrm,lSinVerPermiso){ // 5000-Abre una nueva página de tipo Cliente Delgado (.js) sobre el frame indicado y verifica sus permisos de acceso
   iTpoPermiso='';
   if(lSinVerPermiso == true) ; else iTpoPermiso = top.fGetPermiso(cNombreM+'.js');
   if(iTpoPermiso == '2' && lDesarrollo==false)
     fAlert('\n- Acceso no Permitido.');
   else{
     if(oFrm){
        frm = oFrm;
     }else{
        frm = document.forms[0];
     }
     frm.action = cPagGral+'?cPagina='+cNombreM+'.js';
     frm.submit();
   }
}
function fAbrePaginaHTML(cNombreM){ // 5000-Abre una nueva página de cualquier tipo (htm,asp,jsp,etc) sobre el frame indicado
   frm = document.forms[0];
   frm.action = cNombreM;
   frm.submit();
}
function fAbreSubWindow(lModalM,cNombreM,cMenubarM,cResizableM,cScrollbarsM,cStatusM,cAnchoM,cAltoM,iX,iY){ // 5000-Abre una Nueva Ventana (.js) con un FRMMI propio (Ventana Transaccional)
    if((wExp != null) && (!wExp.closed))
      wExp.focus();
    else{
      cParametrosM = 'dependent=yes,hotKeys=no,location=no,menubar='+cMenubarM+',personalbar=no,resizable='+cResizableM+',scrollbars='+cScrollbarsM+',status='+cStatusM+',titlebar=no,toolbar=no,width='+cAnchoM+',height='+cAltoM+',screenX=800,screenY=600';
      wExp = open(cPagNva+'?cPagina='+cPagIni+'.js&cPagNva='+cNombreM+'.js','',cParametrosM);
      wExp.creator = self;
      if(iX); else iX =  (screen.availWidth - cAnchoM) / 2;
      if(iY); else iY =  (screen.availHeight - cAltoM) / 2;
      wExp.moveTo(iX, iY);
      if(lModalM){
         window.onclick=HandleFocus
         window.onfocus=HandleFocus
         aFramesM = fArrayFrame();
         for(i=0;i<aFramesM.length;i++){
            objFrame = aFramesM[i];
            objFrame = objFrame[0];
           if(objFrame.setwExp){
              objFrame.setwExp(wExp);
           }
         }
      }
    }
}
function fAbreWindow(lModalM,cNombreM,cMenubarM,cResizableM,cScrollbarsM,cStatusM,cAnchoM,cAltoM,iX,iY){ // 5000-Abre una Nueva Ventana (.js) Sin Control Transaccional
    if((wExp != null) && (!wExp.closed))
      wExp.focus();
    else{
      cParametrosM = 'dependent=yes,hotKeys=no,location=no,menubar='+cMenubarM+',personalbar=no,resizable='+cResizableM+',scrollbars='+cScrollbarsM+',status='+cStatusM+',titlebar=no,toolbar=no,width='+cAnchoM+',height='+cAltoM+',screenX=800,screenY=600';
      wExp = open(cPagGral+'?cPagina='+cNombreM+'.js','',cParametrosM);
      wExp.creator = self;
      if(iX); else iX =  (screen.availWidth - cAnchoM) / 2;
      if(iY); else iY =  (screen.availHeight - cAltoM) / 2;
      wExp.moveTo(iX, iY);
      if(lModalM){
         window.onclick=HandleFocus
         window.onfocus=HandleFocus
         aFramesM = fArrayFrame();
         for(i=0;i<aFramesM.length;i++){
            objFrame = aFramesM[i];
            objFrame = objFrame[0];
           if(objFrame.setwExp){
              objFrame.setwExp(wExp);
           }
         }
      }
    }
}
function fAbreWindowHTML(lModalM,cRutaNombreM,cLocationM,cMenubarM,cResizableM,cScrollbarsM,cStatusM,cAnchoM,cAltoM,iX,iY){ // 5000-Abre una Nueva Ventana HTML
if((wExp != null) && (!wExp.closed))
      wExp.focus();
    else{
      cParametrosM = 'dependent=yes,hotKeys=no,location='+cLocationM+',menubar='+cMenubarM+',personalbar=no,resizable='+cResizableM+',scrollbars='+cScrollbarsM+',status='+cStatusM+',titlebar=no,toolbar=no,width='+cAnchoM+',height='+cAltoM+',screenX=800,screenY=600';
      wExp = open(cRutaNombreM,'',cParametrosM);
      wExp.creator = self;
      if(iX); else iX =  (screen.availWidth - cAnchoM) / 2;
      if(iY); else iY =  (screen.availHeight - cAltoM) / 2;
      wExp.moveTo(iX, iY);
      if(lModalM){
         window.onclick=HandleFocus
         window.onfocus=HandleFocus
         aFramesM = fArrayFrame();
         for(i=0;i<aFramesM.length;i++){
            objFrame = aFramesM[i];
            objFrame = objFrame[0];
           if(objFrame.setwExp){
              objFrame.setwExp(wExp);
           }
         }
      }
    }
}
function fInicioPagina(cColorM,cTitulo,lTop,cBody,lHdBoton,cTipoForm){ // 5000-Define el inicio de cualquier página a desplegar
    cTmp = '';
    JSSource("CD/valida_nt.js");
    JSSource("CD/ineng.js");
    JSSource("CD/imagenes.js");
    cTmp = cTmp + '<html>'+"\n";
    if(lTop == true){
      top.document.title = cTitulo;
    }
    if(cTitulo){
      cTmp = cTmp + '<title>'+cTitulo+'</title>'+"\n";
    }
    cGPD+=cTmp;
    cTx+=cTmp;
    cTx+=LinkSource();
    cTmp = '<body bgcolor="'+cColorM+'" ';
    if(cBody){
      cTmp = cTmp + ' ' + cBody + ' ';
    }
    if (cTipoForm)
      cTmp = cTmp + 'topmargin="0" leftmargin="0" onLoad="fLoading();"><form enctype="' + cTipoForm + '" method="POST">'+"\n";
    else
      cTmp = cTmp + 'topmargin="0" leftmargin="0" onLoad="fLoading();"><form method="POST">'+"\n";
    cGPD+=cTmp;
    cTx+=cTmp;
    if(lHdBoton == false);
    else cTx+=Hidden("hdBoton","");
    cTx+=Hidden("hdRowPag","-1");
    cTx+=Hidden("hdNumReg","");
    cTx+=Hidden("hdOrden","");
    cTx+=Hidden("hdFiltro","");
    return cTx;
}
function fFinPagina(){ // 5000-Define el fin de una página a desplegar
    cTx='</form>'+"\n";
    cTx+='</body>'+"\n";
    cTx+='</html>'+"\n";
    cGPD+=cTx;
    //fCargarImagen();
    return cTx;
}
//6000 Utilerías
function fAlert(cMsgM){ // 6000-Define el formato de mensajes que van a ser mostrados en el CD
   alert(cAlertMsgGen + '\n ' + cMsgM);
}
function fDebug(){ // 6000-Muestra una pantalla con el código HTML Generado hasta el llamado de la función
       cPg='\n<HTML><title>HTML Debug</title><body bgcolor="" topmargin="0" leftmargin="0" >'+
       '<textarea cols="85" rows="30" wrap="off" value="'+cGPD+'" </body><HTML>';
       wExp = window.open("", "Debug","width=700,height=500,status=no,resizable=yes,top=200,left=200");
       wExp.opener = window;
       this.wExp.document.write(cPg);
       wExp.moveTo(50, 50);
}
function fNumEntries(cCadenaM,cSepM){ // 6000-Define el número de elementos que existen en una cadena separados por un caracter
   iLi1 = 0;
   for (jLj1 = 0; jLj1 < cCadenaM.length; jLj1++) {
      if(cCadenaM.substring(jLj1,jLj1+1) == cSepM){
        iLi1++;
      }
   }
   return iLi1;
}
function fEntry(iEntradaM,cCadenaM,cSepM){ // 6000-Regresa la cadena encontrada en la posición indicada
   iLi2 = 0; cCad2 = '';
   for (jLj2 = 0; jLj2 < cCadenaM.length; jLj2++) {
      cCad2 = cCad2 + cCadenaM.substring(jLj2,jLj2+1);
      if(cCadenaM.substring(jLj2,jLj2+1) == cSepM){
        iLi2++;
        if(iLi2 == iEntradaM){
           cCad2 = cCad2.substring(0,cCad2.length - 1);
           break;
        }
        cCad2 = '';
      }
   }
   return cCad2;
}
function fTipoDato(cDatoM){ // 6000-Regresa el tipo de dato (I=1,C=2,DT=3,D=4,L=5) de una cadena
  iTipo = -1;
  cTmp = cDatoM.substring(0,1).toUpperCase();
  if(cTmp == "T")
    iTipo = 0;
  if(cTmp == "I")
    iTipo = 1;
  if(cTmp == "C")
    iTipo = 2;
  if(cTmp == "D"){
    if(cDatoM.substring(0,2).toLowerCase() == 'dt')
      iTipo = 3;
    else
      iTipo = 4;
  }
  if(cTmp == "L")
    iTipo =  5;
  if(cTmp == "H")
    iTipo =  6;
  return iTipo;
}
function fValTipo(ObjTxtM, iTipoM, cNombreM, lMandatoM, lMsg){ // 6000-Valida que un elemento tenga los datos correctos
     cVMsg1 = '';
     if(ObjTxtM.value != ''){
        if (iTipoM == 0){
        }
        if (iTipoM == 1){
           if(!fSoloNumeros(ObjTxtM.value))
             cVMsg1 = "\n - El campo '" + cNombreM + "' solo permite caracteres numéricos.";
        }
        if (iTipoM == 2){
           if(!fSoloAlfanumericos(ObjTxtM.value))
             cVMsg1 = "\n - El campo '" + cNombreM + "' solo permite caracteres alfanuméricos (Números, Letras y Punto '.').";
        }
        if (iTipoM == 3){
           if(!fValFecha(ObjTxtM.value,false))
             cVMsg1 = "\n - El campo '" + cNombreM + "' solo permite valores de tipo fecha válidos (dd/mm/aaaa).";
        }
        if (iTipoM == 4){
           if(!fDecimal(ObjTxtM.value))
             cVMsg1 = "\n - El campo '" + cNombreM + "' solo permite valores decimales.";
        }
        if (iTipoM == 5){
           if(ObjTxtM.value.toUpperCase() != "TRUE" && ObjTxtM.value.toUpperCase() != "FALSE")
             cVMsg1 = "\n - El campo '" + cNombreM + "' solo permite valores lógicos (true/false).";
        }
        if (iTipoM == 6){
           if(!fValHora(ObjTxtM.value))
             cVMsg1 = "\n - El campo '" + cNombreM + "' solo permite horas válidas formato(23:59).";
        }
     }
     else{
       if(lMandatoM){
         cVMsg1 = "\n - El campo '" + cNombreM + "' es Obligatorio, favor de introducir su valor.";
       }
     }
     if(cVMsg1 != ''){
       ObjTxtM.focus();
       ObjTxtM.focus();
       if(lMsg)
         fAlert(cVMsg1);
     }
     return cVMsg1;
}
function fDisabled(lDisM,cExcepto,cChars){ // 6000-Habilita o Deshabilita todos los inputs, excepto (Lista separada por comas)
  if(cExcepto)
    iNum=fNumEntries(cExcepto,',');
  for(i=0;i<frm.elements.length;i++){
     lAccess=true;
     obj=frm.elements[i];
     if (obj.type == 'file' ||
     obj.type == 'checkbox' ||
     obj.type == 'password' ||
     obj.type == 'radio' ||
     obj.type == 'text' ||
     obj.type == 'hidden' ||
     obj.type == 'textarea' ||
     obj.type == 'select-one' ||
     obj.type == 'select-multiple'){
       cNombre = obj.name;
       if(cExcepto){
         for(iExc=0;iExc<iNum;iExc++){
           if(fEntry(iExc+1,cExcepto,",")==cNombre){
             lAccess=false;
             if((cChars)&&lDisM==true) obj.value=cChars;
           }
         }
       }
       if(lAccess==true)
         obj.disabled=lDisM;
     }
  }
}
function fDisabledEsp(lDisM,cListaM){ // 6000-Habilita o Deshabilita los inputs Seleccionados (Lista separada por comas)
  iNum=fNumEntries(cListaM,',');
  for(i=0;i<frm.elements.length;i++){
     cNombre = frm.elements[i].name;
     if(cListaM == "Blanco" && frm.elements[i].value=="---"){
       frm.elements[i].value="";
     }else{
       for(iExc=0;iExc<iNum;iExc++){
         if(fEntry(iExc+1,cListaM,",")==cNombre){
           if(lDisM==true)
              frm.elements[i].value="---";
           if(lDisM==false && frm.elements[i].value=="---")
              frm.elements[i].value="";
           frm.elements[i].disabled = lDisM;
         }
       }
     }
  }
}
function fBlanked(cExcepto){ // 6000-Cambia el valor a blanco de todos los inputs, excepto (Lista separada por comas)
  lPrimero=true;
  if(cExcepto)
    iNum=fNumEntries(cExcepto,',');
  for(i=0;i<frm.elements.length;i++){
     lAccess=true;
     obj=frm.elements[i];
     if (obj.type == 'file' ||
     obj.type == 'checkbox' ||
     obj.type == 'password' ||
     obj.type == 'text' ||
     obj.type == 'textarea'){
       cNombre = obj.name;
       if(cExcepto){
         for(iExc=0;iExc<iNum;iExc++){
           if(fEntry(iExc+1,cExcepto,",")==cNombre)
             lAccess=false;
         }
       }
       if(lAccess==true){
         obj.value = '';
         obj.checked = false;
         if(lPrimero==true && obj.disabled==false){
            obj.focus();
            lPrimero=false;
         }
       }
    }
  }
}
function fFormatDate(cFechaM){ // 6000-Entrega en formato dd/mm/aaaa ó mm/dd/aaaa de acuerdo a lFormatoDMY
  cDia = cFechaM.substring(0,2);
  cMes = cFechaM.substring(3,5);
  cAnio = cFechaM.substring(6,10);
  return "{ D '"+cMes+"-"+cDia+"-"+cAnio+"' }";
}
function fFormatTimeStamp(cFechaM,cHoraM,oTS,cMsg){ // 6000-Entrega en formato TimeStamp la suma de una fecha y una hora
  cRegresa = "";
  if(cFechaM != "" && cHoraM != ""){
     cDia = cFechaM.substring(0,2);
     cMes = cFechaM.substring(3,5);
     cAnio = cFechaM.substring(6,10);
     cHora = cHoraM.substring(0,2);
     cMinuto = cHoraM.substring(3,5);
     cRegresa = cAnio+"/"+cMes+"/"+cDia+"/"+cHora+"/"+cMinuto;
  }else{
     if(cFechaM == "" && cHoraM == "") ;
     else{
        if(oTS){
          oTS.value = "";
          return '\n - Tanto la fecha y hora de "'+cMsg+'" deben ser introducidas.';
        }
     }
  }
  if(oTS){
    oTS.value = cRegresa;
    cRegresa = "";
  }
  return cRegresa;
}
function fAsignaTimeStamp(oFechaM,oHoraM,cTStampM){
  if(cTStampM != ""){
    oFechaM.value = fEntry(1,cTStampM+"-","-");
    oHoraM.value = fEntry(2,cTStampM+"-","-");
  }else{
    oFechaM.value = "";
    oHoraM.value = "";
  }
}
function fGetToday(){ // 6000-Entrega en formato String la fecha de Hoy
  dtToday = new Date();
  cDia = dtToday.getDate()<10?("0"+dtToday.getDate()):dtToday.getDate();
  cMes = (dtToday.getMonth()+1)<10?("0"+(dtToday.getMonth()+1)):(dtToday.getMonth()+1);
  if(lFormatoDMY==true)
     return cDia + "/" + cMes + "/" + dtToday.getFullYear();
  else
     return cMes + "/" + cDia + "/" + dtToday.getFullYear();
}
function fLoadCal(oObjM){// 3000-Llama al Calendario
  if(oObjM.disabled == false){
    oFechaLocal = oObjM;
    if(fValFecha(oObjM.value,false)==false){
      oObjM.value = "";
    }
    fAbreSubWindow(true,"CD/calendario","no","no","no","no",200,200,375,290);
  }
}
function fGetFechaLocal(){
  return oFechaLocal;
}
function fWrite(cTagsM){ // 6000-Escribe sobre la cadena con los tags o JavaScript.
  document.write(cTagsM);
}
function fValElements(cExcepto){ // 6000-Valida todos los objetos habilitados en la forma.
   frm = document.forms[0];
   cMsgTmp = '';
   for(i=0;i<frm.elements.length;i++){
      obj= frm.elements[i];
      lMan = false;
      cNombre = '';
      if(obj.name.substring(0,6)=="AuxTxt") obj.disabled=true;
      if (!obj.disabled){
          if (obj.type == 'file' || obj.type == 'password' ||
          obj.type == 'text' || obj.type == 'textarea'){
             objTmp = frm.elements[i-1];
             if(objTmp.name.substring(0,5) == 'HDMF-'){
               if(objTmp.name.substring(5,6) == '1'){
                 lMan = true;
               }
               cNombre = objTmp.value;
             }
             lValObj = true;
             if(cExcepto){
               iNoEntExc = fNumEntries(cExcepto,",");
               for(jklm=1;jklm<=iNoEntExc;jklm++){
                 if(obj.name == fEntry(jklm,cExcepto,","))
                   lValObj = false;
               }
             }
             if(lValObj == true){
               iTipo = fTipoDato(obj.name);
               if(iTipo != -1){
                 cMsgTmp += fValTipo(obj,iTipo,cNombre,lMan,false);
               }else{
                 cMsgTmp += "\n CD: El objeto '" + obj.name + "' no contiene un formato de nombre válido";
               }
             }
          }
      }
   }
   return cMsgTmp;
}
//1000 Frames
function DefFrameCol(aPagsM,cColDefM,lBorderM,iFrmSpacingM,iFrmBorderM){ // 1000-Definición de FrameSets en columnas.
   cTx='<html>'+"\n";
   cTx+='<frameset framespacing="'+iFrmSpacingM+'" border="'+lBorderM+'" cols="'+cColDefM+'" frameborder="'+iFrmBorderM+'">'+"\n";
   for(i=0;i<aPagsM.length;i++){
     aTmp = aPagsM[i];
     if(aTmp[0] == true)
       cTx+='<frame src="'+cRutaProg+cPagGral+'?cPagina='+aTmp[1]+'" scrolling="'+aTmp[2]+'" name="'+aTmp[3]+'" marginwidth="'+aTmp[4]+'" marginheight="'+aTmp[5]+'" style="'+aTmp[6]+'">'+"\n";
     else
       cTx+='<frame src="'+cRutaProg+aTmp[1]+'" scrolling="'+aTmp[2]+'" name="'+aTmp[3]+'" marginwidth="'+aTmp[4]+'" marginheight="'+aTmp[5]+'" style="'+aTmp[6]+'">'+"\n";
   }
   cTx+='<noframes>'+"\n";
   cTx+='<body topmargin="0" leftmargin="0">'+"\n";
   cTx+='<p>Su navegador no soporta el uso de frames, favor de obtener una versión mas reciente</p>'+"\n";
   cTx+='</body>'+"\n";
   cTx+='</noframes>'+"\n";
   cTx+='</frameset>'+"\n";
   cTx+='</html>'+"\n";
   cGPD+=cTx;
}
function DefFrameRow(aPagsM,cRowDefM,lBorderM,iFrmSpacingM,iFrmBorderM){ // 1000-Definición de FrameSets de renglones.
   cTx='<html>'+"\n";
   cTx+='<frameset framespacing="'+iFrmSpacingM+'" border="'+lBorderM+'" rows="'+cRowDefM+'" frameborder="'+iFrmBorderM+'">'+"\n";
   for(i=0;i<aPagsM.length;i++){
     aTmp = aPagsM[i];
     if(aTmp[0] == true)
       cTx+='<frame src="'+cRutaProg+cPagGral+'?cPagina='+aTmp[1]+'" scrolling="'+aTmp[2]+'" name="'+aTmp[3]+'" marginwidth="'+aTmp[4]+'" marginheight="'+aTmp[5]+'" style="'+aTmp[6]+'">'+"\n";
     else
       cTx+='<frame src="'+cRutaProg+aTmp[1]+'" scrolling="'+aTmp[2]+'" name="'+aTmp[3]+'" marginwidth="'+aTmp[4]+'" marginheight="'+aTmp[5]+'" style="'+aTmp[6]+'">'+"\n";
   }
   cTx+='<noframes>'+"\n";
   cTx+='<body topmargin="0" leftmargin="0">'+"\n";
   cTx+='<p>Su navegador no soporta el uso de frames, favor de obtener una versión mas reciente</p>'+"\n";
   cTx+='</body>'+"\n";
   cTx+='</noframes>'+"\n";
   cTx+='</frameset>'+"\n";
   cTx+='</html>'+"\n";
   cGPD+=cTx;
}
function IFrame(cIDM,cAnchoM,cLargoM,cSrc,cScroll,lBorde){ // 1000-Definición de un IFrame.
   cTx='<iframe name="'+cIDM+'" width="'+cAnchoM+'" ID="'+cIDM+'" height="'+cLargoM+'" ';
   if(cSrc){
     if(cSrc != '')
        cTx+=' SRC="'+cRutaProg + cPagGral + "?cPagina=" + cSrc+'" ';
   }
   if(lBorde){
     if(lBorde == true)
        cTx+=' frameborder=1 ';
   }else
     cTx+=' frameborder=0 ';
   if(cScroll){
     if(cScroll)
        cTx+=' scrolling='+cScroll+' ';
   }else
     cTx+=' scrolling=no ';
   cTx+='></iframe>'+"\n";
   cGPD+=cTx;
   return cTx;
}
function IFrameHTM(cIDM,cAnchoM,cLargoM,cSrc,cScroll,lBorde){ // 1000-IFrame para HTML.
   cTx='<iframe name="'+cIDM+'" width="'+cAnchoM+'" ID="'+cIDM+'" height="'+cLargoM+'" ';
   if(cSrc){
     if(cSrc != '')
        cTx+=' SRC="'+cRutaProg + cSrc+'" ';
   }
   if(lBorde){
     if(lBorde == true)
        cTx+=' frameborder=1 ';
   }else
     cTx+=' frameborder=0 ';

   if(cScroll){
     if(cScroll)
        cTx+=' scrolling='+cScroll+' ';
   }else
     cTx+=' scrolling=no ';
   cTx+='></iframe>';
   cGPD+=cTx+"\n";
   return cTx;
}
//2000 Inclusión de Archivos
function JSSource(cNombreM){ // 2000-Llamado a archivos JavaScript
  cTx= '<SCRIPT LANGUAGE="JavaScript" SRC="' + cRutaFuncs + cNombreM + '"></SCRIPT>';
  cGPD+=cTx+"\n";
  return cTx;
}
function LinkSource(){ // 2000-Llamado a página de estilos
  cTx='<link rel="stylesheet" href="'+ cRutaEstilos + cEstilos  + '" TYPE="text/css">';
  cGPD+=cTx+"\n";
  return cTx;
}
function fCargarImagen(){ // no
   cTx='';
   if(aImgs.length > 0){
       iImgs = -1;
       cTx='<SCRIPT LANGUAGE="JavaScript">'+"\n";
       cTx=cTx+'if (fPreCargarImagen){';
       for(i=0;i<aImgs.length;i++){
          cTx=cTx+'fPreCargarImagen(document,"'+aImgs[i]+'");'+"\n";
          aImgs[i] = '';
       }
       cTx=cTx+'}'+"\n";
       cTx=cTx+'</SCRIPT>'+"\n";
       cGPD+=cTx;
   }
   return cTx;
}
function fPag(){ // no
  if(fBefLoad()){
     fBefLoad();
  }
  if(fPermisoEjecutar)
    fPermisoEjecutar();
  if(fDefPag()){
     fDefPag();
  }
  if(fPagExe()){
     fPagExe();
  }
}
function fPagExe(){ // no
    document.write(cGPD);
    cGPD="";
}
function fInicioJS(){ // no
    cTx='<script language="JavaScript">'+"\n";
    cGPD+=cTx;
    return cTx;
}
function fFinJS(){ // no
    cTx='</script>'+"\n";
    cGPD+=cTx;
    return cTx;
}
function fBuscaFrame(cFrameM){
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
  return '';
}
function fArrayFrame(){// no
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
function fVerFrames(aFramesM, frmM, iPosM){// no
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

function fSelectSetIndexFromValue(theObject, theValue){
  if (theObject.options)
    if (theObject.options.length > 0)
      for (var i = 0; i < theObject.options.length; i++)
        if (theObject.options[i].value == theValue){
          theObject.selectedIndex = i;
          theObject.value = theObject.options[i].value;
          break;
        }
}

function fSelectSetIndexFromText(theObject, theText){
  if (theObject.options)
    if (theObject.options.length > 0)
      for (var i = 0; i < theObject.options.length; i++)
        if (theObject.options[i].text == theText){
          theObject.selectedIndex = i;
          theObject.value = theObject.options[i].value;
          break;
        }
}

function fSelectReposFromValue(theObject, theText){
  if (theObject.options)
    if (theObject.options.length > 0)
      for (var i = 0; i < theObject.options.length; i++)
        if (theObject.options[i].text.substring(0,theText.length) == theText){
          theObject.selectedIndex = i;
          theObject.value = theObject.options[i].value;
          break;
        }
}

function fReposSelectFromField(theEvent, lMayus, theSelectObject, theTextObject){
  var charCode = (theEvent.which) ? theEvent.which : event.keyCode;
  theTextObject.value += String.fromCharCode(charCode);
  if (lMayus)
    fMayus(theTextObject);
  fSelectReposFromValue(theSelectObject, theTextObject.value);
  return false;
}

function fSetRadioValue(theObject, theValue){
  for (var i = 0; i < theObject.length; i++)
    if (theObject[i].value == theValue)
      theObject[i].checked = true;
    else
      theObject[i].checked = false;
}

function fGetRadioValue(theObject){
  for (var i = 0; i < theObject.length; i++)
    if (theObject[i].checked)
      return theObject[i].value;
}

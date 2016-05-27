var cGPD = "";
var aImgs = new Array();
var iImgs = -1;
var oFechaLocal;
var wExp;
var wVerifica;
var cPaginaWebJS="";
var lLoadedPag=false;

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
function TDEtiAreaTextoID(ID,lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo,lContador,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,AreaTexto
	   cMan = '0-'; if(lMandatorioM == true){cMan='1-';cEtiquetaEM="*"+cEtiquetaEM;}
	   Hidden('HDMF-'+cMan+cNombreM,cEtiquetaEM);
	   cATComp = ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
	          AreaTextoID(ID,0,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo);
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
function TDEtiRadio(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,lChecked, cToolTip, cOnSelected, lActivo, cOnAnyEvent, cOnChange, lSelectOnFocus){ // 3000-Campo tipo radio
   cMan = '0-'; if(lMandatorioM == true){cMan='1-';cEtiquetaEM="*"+cEtiquetaEM;}
   return ITD(cEstiloEM,iColExtiendeEM)+
	      Radio(lMandatorioM, cNombreM, cValorM, lChecked, cToolTip, cOnSelected, lActivo, cOnAnyEvent, cOnChange, lSelectOnFocus)+
  	      TextoSimple(cEtiquetaEM)+
	      FTD();
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


function TDEtiCheckBoxOnClick(cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnClick,lSelectOnFocus,lActivo,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,CheckBox
	   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
	          CheckBoxOnClick(cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnClick,lSelectOnFocus,lActivo)+FTD();
	}



function TDLigaCampo(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus,cEstiloCM,iColExtiendeCM,cFunctionLiga){ // 7000-Etiqueta,Campo
   cMan = '0-'; if(lMandatorioM == true){cMan = '1-';cEtiquetaEM="*"+cEtiquetaEM;}
   Hidden('HDMF-'+cMan+cNombreM,cEtiquetaEM);
   iTipo = fTipoDato(cNombreM); cHora = "";if(cNombreM.substring(0,1).toUpperCase() == "H") cHora = "(HH:MM)";
   return ITD(cEstiloEM,iColExtiendeEM)+
             Liga(cEtiquetaEM,cFunctionLiga,cToolTip)+
          FITD(cEstiloCM, iColExtiendeCM)+
             Text(0,cNombreM,cValorM,iLargoM,iMaxCaracteresM,cToolTip,cOnBlur,cOnAnyEvent,cOnChange,lSelectOnFocus)+FTD();
}
function TDLigaSelect(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,cOnChange,cEstiloCM,iColExtiendeCM,cFunctionLiga){ // 7000-Etiqueta,Select
   cMan = '0-'; if(lMandatorioM == true){cMan='1-';cEtiquetaEM="*"+cEtiquetaEM;}
   return ITD(cEstiloEM,iColExtiendeEM)+
            Liga(cEtiquetaEM,cFunctionLiga,"")+
          FITD(cEstiloCM,iColExtiendeCM)+
            Select(cNombreM,cOnChange)+FTD();
}
function TDEtiSelectList(lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,cNombreM,iSizeM,cOnChange,cEstiloCM,iColExtiendeCM){ // 7000-Etiqueta,SelectList
   cMan = '0-'; if(lMandatorioM == true){cMan='1-';cEtiquetaEM="*"+cEtiquetaEM;}
   return ITD(cEstiloEM,iColExtiendeEM)+TextoSimple(cEtiquetaEM)+FITD(cEstiloCM,iColExtiendeCM)+
          SelectList(cNombreM,iSizeM,cOnChange)+FTD();
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

function FileButton(cNombreM, cNom, irW, h){
	var cTx = '<div style="text-align: center; "><input type="button" value="Subir archivo"  onclick="window.open(\'http://localhost\', \'\', \'width=200,height=100\')" /></div>';
	return cTx;	
}

function Examinar(cNombreM, cNom, irW, h){
	var cTx = '<div style="text-align: center; "><input type="file" name="'+cNombreM+'" size="25"></div>';
	return cTx;	
}

function fButtonRes(cNombreM, cNom, irW, h){
	var cTx = '<input type="button" name="btnArchRes" value="Subir Archivo" >';
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
   cVal = "";
   if(lMandatorioM == true)
      cMan = '1-';
   if(lMandatorioM != 0)
      cVal = Hidden('HDMF-'+cMan+cNombreM,''+cToolTip);
   return cVal + fInput("text", cNombreM, '"'+cValorM+'"', iLargoM, iMaxCaracteresM, cToolTip, cOnBlur, cOnAnyEvent, cOnChange, lSelectOnFocus, lActivo);
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
function Liga(cNombreM,cHRefM,cEstatusM,oLig){ // 3000-Botón de tipo imagen
    cTx='<a ID="'+oLig+'"; href="JavaScript:'+cHRefM+'"'+"\n";
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

function CheckBoxOnClick(cNombreM,cValorM,lSeleccion,cToolTip,cOnBlur,cOnAnyEvent,cOnClick,lSelectOnFocus,lActivo){ // 3000-Caja de Selección
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
    if(cOnClick){
       if(cOnClick != "")
         cTx+=' onClick="' + cOnClick+ '"';
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

function AreaTextoID(ID,lMandatorioM,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo){ // 3000-Componente de tipo Area Texto
	   cMan = '0-';
	   if(lMandatorioM == true)
	      cMan = '1-';
	   if(lMandatorioM != 0)
	      Hidden('HDMF-'+cMan+cNombreM,''+cToolTip);
	   cTx='<textarea id="'+ID+'"cols="'+iColM+'" rows="'+iRengM+'" name="'+cNombreM+'" value="'+cValueM+'" ';
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

function Select(cNombreM,cOnChange,lActivo){ // 3000-Lista Desplegable simple
   cTx='<SELECT NAME="'+cNombreM+'" SIZE="1" ';
   if(cOnChange){
      if(cOnChange != "")
         cTx+=' onChange="'+cOnChange+'" ';
   }
   if(lActivo == false)
      cTx+=' disabled ';
   cTx+='></SELECT>';
   cGPD+=cTx+"\n";
   return cTx;
}
function SelectList(cNombreM,iSizeM,cOnChange, lMultiple, cOnAnyEvent){ // 3000-Lista simple
   cTx='<SELECT NAME="'+cNombreM+'" SIZE="'+iSizeM+'" ';
   if(cOnChange){
      if(cOnChange != "")
         cTx+=' onChange="'+cOnChange+'" ';
   }
   if(lMultiple)
     cTx += ' multiple ';
   if(cOnAnyEvent){
      if(cOnAnyEvent != "")
      cTx+=" " + cOnAnyEvent;
   }
   cTx+='></SELECT>';
   cGPD+=cTx+"\n";
   return cTx;
}
function fAsignaSelect(oSelectM,iCveM,cCharM){// 3000-Llena al select con un solo valor caracter
  oSelectM.length=1;
  oSelectM[0].text  = cCharM;
  oSelectM[0].value = iCveM;
  oSelectM.value = iCveM;
}
function fAsignaCheckBox(oSelectM,iDatoM){// 3000-Llena al checkbox con true si Dato es 1 y False se Dato es 0
  if(parseInt(iDatoM,10)==1)
    oSelectM.checked=true;
  else
    oSelectM.checked=false;
}
function fAsignaRadio(oSelectM,cValor){
  for(var i = 0; i < oSelectM.length; i++){
    if(cValor == oSelectM[i].value){
       oSelectM[i].checked = true;
	   break;
	}
  }
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
/*   else{
	 if(lSelMsg)
       oSelectM.selectedIndex = sIndex + 1;
	 else
       oSelectM.selectedIndex = sIndex;
   }*/
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
   if(iEspacioCel >= 0)
     cTx+=' cellspacing="'+iEspacioCel+'"';
   if(iRellenoCel >= 0)
     cTx+=' cellpadding="'+iRellenoCel+'"';
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
   if(iEspacioCel >= 0)
     cTx+=' cellspacing="'+iEspacioCel+'"';
   if(iRellenoCel >= 0)
     cTx+=' cellpadding="'+iRellenoCel+'"';
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
function fAbreSubWindow(lModalM,cNombreM,cMenubarM,cResizableM,cScrollbarsM,cStatusM,cAnchoM,cAltoM,iX,iY,cNomVentana){ // 5000-Abre una Nueva Ventana (.js) con un FRMMI propio (Ventana Transaccional)
    if((wExp != null) && (!wExp.closed))
      wExp.focus();
    else{
      cParametrosM = 'dependent=yes,hotKeys=no,location=no,menubar='+cMenubarM+',personalbar=no,resizable='+cResizableM+',scrollbars='+cScrollbarsM+',status='+cStatusM+',titlebar=no,toolbar=no,width='+cAnchoM+',height='+cAltoM+',screenX=800,screenY=600';
      wExp = open(cPagNva+'?cPagina='+cPagIni+'.js&cPagNva='+cNombreM+'.js',(!cNomVentana?'':cNomVentana),cParametrosM);
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
    //JSSource("CD/valida_nt.js");
    //JSSource("CD/imagenes.js");
    cGPD+='<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="es"><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /><meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />'+"\n";
    JSSource("CD/ineng.js");
    if(lTop == true){
      top.document.title = cTitulo;
    }
    if(cTitulo){
      cTmp = cTmp + '<title>'+cTitulo+'</title>'+"\n";
    }
    cGPD+=cTmp;
    cTx+=cTmp;
    cTmp +="<head>";
    cTx+=LinkSource();
    cTmp+= "</head>";
    cGPD+=cTmp;
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
function fInicioPaginaFiles(cColorM,cTitulo,lTop,cBody,lHdBoton,idForm,cTipoForm,formAction){ // 5000-Define el inicio de cualquier página a desplegar
    cTmp = '';
    //JSSource("CD/valida_nt.js");
    //JSSource("CD/imagenes.js");
    cGPD+='<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="es"><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /><meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />'+"\n";
    JSSource("CD/ineng.js");
    if(lTop == true){
      top.document.title = cTitulo;
    }
    if(cTitulo){
      cTmp = cTmp + '<title>'+cTitulo+'</title>'+"\n";
    }
    cGPD+=cTmp;
    cTx+=cTmp;
    cTmp +="<head>";
    cTx+=LinkSource();
    cTmp+= "</head>";
    cGPD+=cTmp;
    cTmp = '<body bgcolor="'+cColorM+'" ';
    if(cBody){
      cTmp = cTmp + ' ' + cBody + ' ';
    }
    if (cTipoForm)  
      cTmp = cTmp + 'topmargin="0" leftmargin="0" onLoad="fLoading();"><form id="'+idForm+'" enctype="' + cTipoForm + '" method="POST" action="'+formAction+'">'+ "\n";
    else
      cTmp = cTmp + 'topmargin="0" leftmargin="0" onLoad="fLoading();"><form method="POST">'+"\n";
    cGPD+=cTmp;
    cTx+=cTmp;
    /*if(lHdBoton == false);
    else cTx+=Hidden("hdBoton","");
    cTx+=Hidden("hdRowPag","-1");
    cTx+=Hidden("hdNumReg","");
    cTx+=Hidden("hdOrden","");
    cTx+=Hidden("hdFiltro","");
    cTx+=Examinar("DocXReqPRUEBA");*/
    //cTx+=Hidden("hdNumReguevos","");
    cTx+='<input type="file" name="PUTAAAAA" size="25" value="55">';
    cGPD+=cTx+"\n";
    
    return cTx;
}

function fFinPagina2(){ // 5000-Define el fin de una página a desplegar
    cTx='</form>'+"\n";
    cTx+='</body>'+"\n";
    cTx+='</html>'+"\n";
    cGPD+=cTx;
    return cTx;
}
/*function fFinPagina(){ // 5000-Define el fin de una página a desplegar
    cTx='<DIV ID="PRC" STYLE="position:absolute;top:'+((screen.availHeight - 200)/2)+';left:'+
         ((screen.availWidth - 360)/2)+';width:0;height:0;background-color:white';
    cTx+='clip:rect(0px 0px 0px 0px);">';
    cTx+='<FORM>';
    cTx+='<table border="4" width="100%" height="100%" cellspacing="3">';
    cTx+='<tr><td align="left" class="ESTitulo" valign="middle"><img SRC="'+cRutaImgServer+'clock.jpg"></td><td class="ESTitulo">PROCESANDO LA PETICIÓN <br> AL SERVIDOR...';
    cTx+='</td></tr>';
    cTx+='</FORM></DIV>';
    cTx+='</form>'+"\n";
    cTx+='</body>'+"\n";
    cTx+='</html>'+"\n";
    cGPD+=cTx;
    //fCargarImagen();
    return cTx;
}*/
/*function fEnProceso(lOpen){
try{
  oProc = document.getElementById("PRC").style;
  oProc.border = "#FFFFFF solid 1px";
  if(lOpen == true){
    oProc.clip = "rect(0px,360px,180px,0px)";
    oProc.width = 360;
    oProc.height = 180;
  }else{
    oProc.width = 0;
    oProc.height = 0;
    oProc.clip = "rect(0px,0px,0px,0px)";
  }
}catch(e){}
}*/
//6000 UtilerÍas
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
function fFormatDate(cFechaM){ // 6000-Entrega en formato dd/mm/aaaa o mm/dd/aaaa de acuerdo a lFormatoDMY
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
    fAbreSubWindow(true,"CD/calendario","no","no","no","no",220,205,375,290);
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
  fEnProceso(false);
}

function fPagADV(){ // no
	  if(fBefLoad){
	     fBefLoad();
	  }

	  if(fDefPag()){
	     fDefPag();
	  }
	  if(fPagExe){
	     fPagExe();
	  }
	  	  
	  fEnProceso(false);
}

function fPagADVError(cError){
	  if(fBefLoad){
	     fBefLoad(cError);
	  }

	  if(fDefPag){
	     fDefPag(cError);
	  }
	  if(fPagExe){
	     fPagExe();
	  }
	  	  
	  fEnProceso(false);
}


function fGetLoadedPag(){
    return lLoadedPag;
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
/*function fBuscaFrame(cFrameM){
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
}*/

/*function fArrayFrame(){// no
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
*/
/*function fVerFrames(aFramesM, frmM, iPosM){// no
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
*/
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
function fPermisoEjecutar(){
  if(top.opener && top.opener.lOmitePermiso == true)
    return;
  top.fPermisoEjecutar(cPaginaWebJS);
}
function fSetWindowTitle(){
  top.document.title = cTituloGen + cTitulo;
}
function fTituloEmergente(cTitulo, lSoloAyuda,cPrograma,cAccionSalir){
  InicioTabla("ESTitulo",0,"100%","23","","",0);
    ITRTD("ESTitulo",0,"","","center");
      if(cPrograma)
        fTituloCodigo(cTitulo, cPrograma)
      else
        TextoSimple(cTitulo);
    if(top.opener){
      FITD("ESTitulo",0,"70");
     //   BtnImg("BtnAyuda","ayuda","fCargaAyuda();","Muestra la Ayuda de la Pantalla...");
      if(!lSoloAyuda){
        FITD("ESTitulo",0,"70");
          if(cAccionSalir)
            BtnImg("BtnSalir","salir","if(window." + cAccionSalir + ")window." + cAccionSalir + "();","Salir de la ventana emergente...");
          else
            BtnImg("BtnSalir","salir","if(top.opener)top.close();","Cierra la ventana sin ejecutar acción alguna...");
      }
    }
    FTDTR();
  FinTabla();
}
function fTituloImprimirSalir(cTitulo, cPrograma, cAccionSalir){
  InicioTabla("ESTitulo",0,"100%","23","","",0);
    ITRTD("ESTitulo",0,"100","","center");
      ImgNom("ImgLogo","LogoSCT_0096_0064.jpg","");
    FITD("ESTitulo",0,"");
      if(cPrograma)
        fTituloCodigo(cTitulo, cPrograma)
      else
        TextoSimple(cTitulo);
      FITD("ESTitulo",0,"70");
          BtnImg("BtnImprimir","imprime","window.print();","Imprime la pantalla correspondiente...");
      FITD("ESTitulo",0,"70");
        if(cAccionSalir)
          BtnImg("BtnSalir","salir","if(window." + cAccionSalir + ")window." + cAccionSalir + "();","Salir de la ventana emergente...");
        else
          BtnImg("BtnSalir","salir","if(top.opener)top.close();","Cierra la ventana sin ejecutar acción alguna...");
    FTDTR();
  FinTabla();
}

  function fReemplazar(Valor){
    var cComillaS = String.fromCharCode(39),
        cComillaD = String.fromCharCode(34),
        cRetorno1 = String.fromCharCode(10),
        cRetorno2 = String.fromCharCode(13),
        cRetorno3 = String.fromCharCode(12),
        cTabula   = String.fromCharCode(9),
        cCaracter = "",
        cFinal    = "";
    for (var i = 0; i <= Valor.length; i++){
      cCaracter = Valor.substring(i, i+1);
      if (cCaracter != cComillaD &&
          cCaracter != cRetorno1 && cCaracter != cRetorno2 && cCaracter != cRetorno3 &&
          cCaracter != cTabula)
        cFinal+= Valor.substring(i, i+1);
      //if (cCaracter == cComillaD)
      //  cFinal+=cComillaS;
    }
  return cFinal;
  }

/* Función para convertir a mayúsculas el valor de un campo
   Parámetros: campoFil -- nombre del campo a convertir(this) */
function fMayus(campoFil){
	//DGDC SOLICITO QUITAR ESTA FUNCIONALIDAD EN TODAS LAS PANTALLAS
//  campoFil.value = fReemplazar(fTrim(campoFil.value.toUpperCase())); //ORIGINAL
	campoFil.value = fTrim(campoFil.value); 
}
function fMayusNoReplace(campoFil){
  campoFil.value = fTrim(campoFil.value.toUpperCase());
}
/* Función para localizar un caracter específico dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el caracter
               cVCaract -- Caracter a buscar
   Valor que regresa:   true  -- Si lo localiza
                        false -- Si no lo encuentra */
function fEncCaract(cVCadena, cVCaract){
   if (cVCadena.indexOf(cVCaract) != -1)
      return true;
   else
      return false;
}
/* Función para localizar una coma
   Parámetros: cVCadena -- Cadena en la cual se va a localizar la coma
   Valor que regresa:   true  -- Si lo localiza
                        false -- Si no lo encuentra */
function fComa(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),","))
      return true;
   else
      return false;
}
/* Función para localizar un guión bajo
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el guión bajo
   Valor que regresa:   true  -- Si lo localiza
                        false -- Si no lo encuentra */
function fGuionBajo(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"_"))
      return true;
   else
      return false;
}
/* Función para localizar un espacio
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el espacio
               cVCaract -- Caracter a buscar
   Valor que regresa:   true  -- Si lo localiza
                        false -- Si no lo encuentra */
function fEspacio(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase()," "))
      return true;
   else
      return false;
}
/* Función para localizar números dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar que exista un número
   Valor que Regresa: true  -- Si localiza por lo menos un número
                      false -- Si no localiza ningún número  */
function fNum(cVCadena){
   if ( fEncCaract(cVCadena.toUpperCase(),"1") ||
        fEncCaract(cVCadena.toUpperCase(),"2") ||
        fEncCaract(cVCadena.toUpperCase(),"3") ||
        fEncCaract(cVCadena.toUpperCase(),"4") ||
        fEncCaract(cVCadena.toUpperCase(),"5") ||
        fEncCaract(cVCadena.toUpperCase(),"6") ||
        fEncCaract(cVCadena.toUpperCase(),"7") ||
        fEncCaract(cVCadena.toUpperCase(),"8") ||
        fEncCaract(cVCadena.toUpperCase(),"9") ||
        fEncCaract(cVCadena.toUpperCase(),"0") )
       return true;
   else
       return false;
}
/* Función para localizar letras dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar una letra
   Valor que Regresa: true  -- Si localiza por lo menos una letra
                      false -- Si no localiza ninguna letra  */
function fLetras(cVCadena){
   if ( fEncCaract(cVCadena.toUpperCase(),"A") ||
        fEncCaract(cVCadena.toUpperCase(),"B") ||
        fEncCaract(cVCadena.toUpperCase(),"C") ||
        fEncCaract(cVCadena.toUpperCase(),"D") ||
        fEncCaract(cVCadena.toUpperCase(),"E") ||
        fEncCaract(cVCadena.toUpperCase(),"F") ||
        fEncCaract(cVCadena.toUpperCase(),"G") ||
        fEncCaract(cVCadena.toUpperCase(),"H") ||
        fEncCaract(cVCadena.toUpperCase(),"I")  ||
        fEncCaract(cVCadena.toUpperCase(),"J")  ||
        fEncCaract(cVCadena.toUpperCase(),"K")  ||
        fEncCaract(cVCadena.toUpperCase(),"L")  ||
        fEncCaract(cVCadena.toUpperCase(),"M")  ||
        fEncCaract(cVCadena.toUpperCase(),"N")  ||
        fEncCaract(cVCadena.toUpperCase(),"Ñ")  ||
        fEncCaract(cVCadena.toUpperCase(),"O")  ||
        fEncCaract(cVCadena.toUpperCase(),"P")  ||
        fEncCaract(cVCadena.toUpperCase(),"Q") ||
        fEncCaract(cVCadena.toUpperCase(),"R")  ||
        fEncCaract(cVCadena.toUpperCase(),"S")  ||
        fEncCaract(cVCadena.toUpperCase(),"T")  ||
        fEncCaract(cVCadena.toUpperCase(),"U")  ||
        fEncCaract(cVCadena.toUpperCase(),"V")  ||
        fEncCaract(cVCadena.toUpperCase(),"W") ||
        fEncCaract(cVCadena.toUpperCase(),"X")  ||
        fEncCaract(cVCadena.toUpperCase(),"Y")  ||
        fEncCaract(cVCadena.toUpperCase(),"Z"))
       return true;
   else
       return false;
}
/* Función para localizar caracteres raros dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar un caracter raro
   Valor que Regresa: true  -- Si localiza por lo menos un caracter raro
                      false -- Si no localiza ningún caracter raro  */
function fRaros(cVCadena){
	   if (fEncCaract(cVCadena.toUpperCase(),"º")    ||
	       fEncCaract(cVCadena.toUpperCase(),"ª")    ||
	       fEncCaract(cVCadena.toUpperCase(),"·")    ||
	       fEncCaract(cVCadena.toUpperCase(),"%")    ||
	       fEncCaract(cVCadena.toUpperCase(),"&")    ||
	       fEncCaract(cVCadena.toUpperCase(),"ç")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¿")    ||
	       fEncCaract(cVCadena.toUpperCase(),"?")    ||
	       fEncCaract(cVCadena.toUpperCase(),"|")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¬")    ||
	       fEncCaract(cVCadena.toUpperCase(),"#")    ||
	       fEncCaract(cVCadena.toUpperCase(),"$")    ||
	       fEncCaract(cVCadena.toUpperCase(),"=")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¡")    ||
	       fEncCaract(cVCadena.toUpperCase(),"!")    ||
	       fEncCaract(cVCadena.toUpperCase(),"*")   ||
	       fEncCaract(cVCadena.toUpperCase(),"{")    ||
	       fEncCaract(cVCadena.toUpperCase(),"}")   ||
	       fEncCaract(cVCadena.toUpperCase(),"[")    ||
	       fEncCaract(cVCadena.toUpperCase(),"]")   ||
	       fEncCaract(cVCadena.toUpperCase(),"<")    ||
	       fEncCaract(cVCadena.toUpperCase(),">")    ||
	       fEncCaract(cVCadena.toUpperCase(),"~")    ||
	       fEncCaract(cVCadena.toUpperCase(),"æ ")   ||
	       fEncCaract(cVCadena.toUpperCase(),"'")  ||
	       fEncCaract(cVCadena.toUpperCase(),"Æ")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¢")    ||
	       fEncCaract(cVCadena.toUpperCase(),"£")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¥")   ||
	       fEncCaract(cVCadena.toUpperCase(),"ƒ")    ||
	       fEncCaract(cVCadena.toUpperCase(),"«")    ||
	       fEncCaract(cVCadena.toUpperCase(),"»") )
	       return true;
	   else
	      return false;
	}
/* Función para localizar los caracteres + y - dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el signo
   Valor que Regresa: true  -- Si localiza por lo menos un signo
                      false -- Si no localiza ningún signo  */
function fSignos(cVCadena){
   if (fEncCaract(cVCadena,"+") || fEncCaract(cVCadena,"-"))
       return true;
   else
       return false;
}
/* Función para localizar : y ; en una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el signo
   Valor que Regresa: true  -- Si localiza por lo menos un signo
                      false -- Si no localiza ningún signo  */
function fPuntuacion(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),":") ||
       fEncCaract(cVCadena.toUpperCase(),";") )
      return true;
   else
      return false;
}
/* Función para localizar la Diagonal /
   Parámetros: cVCadena -- Cadena en la cual se va a localizar la diagonal
   Valor que Regresa: true  -- Si localiza por lo menos una diagonal
                      false -- Si no localiza ningún signo  */
function fDiagonal(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"/") )
      return true;
   else
      return false;
}
/* Función para localizar arrobas dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar la arrobas
   Valor que Regresa: true  -- Si localiza por lo menos una arroba
                      false -- Si no localiza ninguna arroba  */
function fArroba(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"@"))
     return true;
   else
     return false;
}
/* Función para localizar paréntesis tanto de apertura como de cierre dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el paréntesis
   Valor que Regresa: true  -- Si localiza por lo menos un paréntesis
                      false -- Si no localiza ningún paréntesis  */
function fParentesis(cVCadena){
   if (fEncCaract(cVCadena.toUpperCase(),"(") &&
       fEncCaract(cVCadena.toUpperCase(),")") )
      return true;
   else
      return false;
}
/* Función para localizar apóstrofe dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el apóstrofe
   Valor que Regresa: true  -- Si localiza por lo menos un apóstrofe
                      false -- Si no localiza ningún apóstrofe  */
function fComilla(cVCadena){
   if (fEncCaract(cVCadena,"'"))
       return true;
   else
       return false;
}
/* Función para localizar comas dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar la coma
   Valor que Regresa: true  -- Si localiza por lo menos una coma
                      false -- Si no localiza ninguna coma */
function fComa(cVCadena){
   if (fEncCaract(cVCadena,","))
       return true;
   else
       return false;
}
/* Función para localizar puntos dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar un punto
   Valor que Regresa: true  -- Si localiza por lo menos un punto
                      false -- Si no localiza ningún punto */
function fPunto(cVCadena){
   if (fEncCaract(cVCadena,"."))
       return true;
   else
       return false;
}
/* Función para validar que una cadena solo contenga letras
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fSoloLetras(cVCadena){
   if(cVCadena == "")
      return false;
    if ( fNum(cVCadena)          || fRaros(cVCadena)       ||
         fPuntuacion(cVCadena)   || fSignos(cVCadena)      ||
         fArroba(cVCadena)       || fParentesis(cVCadena)  ||
         fPunto(cVCadena)        || fDiagonal(cVCadena)    ||
         fGuionBajo(cVCadena)    || fComa(cVCadena))
        return false;
    else
        return true;
 }
/* Función para validar que una cadena solo contenga caracteres alfanuméricos
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fSoloAlfanumericos(cVCadena){
    if ( fRaros(cVCadena)      || //fPuntuacion(cVCadena) ||
         fSignos(cVCadena)     || //fArroba(cVCadena)     ||
         fParentesis(cVCadena) || //fPunto(cVCadena)      ||
         fDiagonal(cVCadena)   //|| fComa(cVCadena)
         )
        return false;
    else
        return true;
}
/* Función para validar que una cadena solo contenga números
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fSoloNumeros(cVCadena){
    if ( fRaros(cVCadena)       || fPuntuacion(cVCadena) ||
         fSignos(cVCadena)      || fArroba(cVCadena)     ||
         fParentesis(cVCadena)  || fLetras(cVCadena)     ||
         fDiagonal(cVCadena)    || fPunto(cVCadena)      ||
         fEspacio(cVCadena)     || fGuionBajo(cVCadena)  ||
         fComa(cVCadena))
        return false;
    else
        return true;
}
/* Función para validar que una cadena solo contenga números con Comas
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fSoloNumerosC(cVCadena){
    if ( fRaros(cVCadena)       || fPuntuacion(cVCadena) ||
         fSignos(cVCadena)      || fArroba(cVCadena)     ||
         fParentesis(cVCadena)  || fLetras(cVCadena)     ||
         fDiagonal(cVCadena)    || fPunto(cVCadena)      ||
         fEspacio(cVCadena)     || fGuionBajo(cVCadena)  )
        return false;
    else
        return true;
}
/* Función para validar que un campo tenga un decimal
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fDecimal(cVCadena){
   // Encontrar el punto
   iVPosP = cVCadena.indexOf('.');
   iVPosI = 0;
   // Encontrar el signo
   if (fSignos(cVCadena.substring(0,1))) iVPosI = 1;
   if (iVPosP > -1) {
      if (fSoloNumerosC(cVCadena.substring(iVPosI,iVPosP)) && fSoloNumeros(cVCadena.substring(iVPosP+1,cVCadena.length)))
         return true;
      else
         return false;
   }
   if (fSoloNumerosC(cVCadena.substring(iVPosI,cVCadena.length)))
      return true;
   else
     return false;
}
/* Función para localizar caracteres raros dentro de una cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar un caracter raro
   Valor que Regresa: true  -- Si localiza por lo menos un caracter raro
                      false -- Si no localiza ningún caracter raro  */
function fRarosRFC(cVCadena){
	   if (fEncCaract(cVCadena.toUpperCase(),"º")    ||
	       fEncCaract(cVCadena.toUpperCase(),"ª")    ||
	       fEncCaract(cVCadena.toUpperCase(),"·")    ||
	       fEncCaract(cVCadena.toUpperCase(),"%")    ||
	       fEncCaract(cVCadena.toUpperCase(),"ç")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¿")    ||
	       fEncCaract(cVCadena.toUpperCase(),"?")    ||
	       fEncCaract(cVCadena.toUpperCase(),"|")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¬")    ||
	       fEncCaract(cVCadena.toUpperCase(),"#")    ||
	       fEncCaract(cVCadena.toUpperCase(),"$")    ||
	       fEncCaract(cVCadena.toUpperCase(),"=")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¡")    ||
	       fEncCaract(cVCadena.toUpperCase(),"!")    ||
	       fEncCaract(cVCadena.toUpperCase(),"*")   ||
	       fEncCaract(cVCadena.toUpperCase(),"{")    ||
	       fEncCaract(cVCadena.toUpperCase(),"}")   ||
	       fEncCaract(cVCadena.toUpperCase(),"[")    ||
	       fEncCaract(cVCadena.toUpperCase(),"]")   ||
	       fEncCaract(cVCadena.toUpperCase(),"<")    ||
	       fEncCaract(cVCadena.toUpperCase(),">")    ||
	       fEncCaract(cVCadena.toUpperCase(),"~")    ||
	       fEncCaract(cVCadena.toUpperCase(),"æ ")   ||
	       fEncCaract(cVCadena.toUpperCase(),"'")  ||
	       fEncCaract(cVCadena.toUpperCase(),"Æ")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¢")    ||
	       fEncCaract(cVCadena.toUpperCase(),"£")    ||
	       fEncCaract(cVCadena.toUpperCase(),"¥")   ||
	       fEncCaract(cVCadena.toUpperCase(),"ƒ")    ||
	       fEncCaract(cVCadena.toUpperCase(),"«")    ||
	       fEncCaract(cVCadena.toUpperCase(),"»") )
	       return true;
	   else
	      return false;
	}

/* Función para validar que una cadena solo contenga caracteres alfanuméricos
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fSoloAlfanumericosNomRazonSoc(cVCadena){
    if ( fRarosRFC(cVCadena)      || //fPuntuacion(cVCadena) ||
         fSignos(cVCadena)     || //fArroba(cVCadena)     ||
         fParentesis(cVCadena) || //fPunto(cVCadena)      ||
         fDiagonal(cVCadena)   //|| fComa(cVCadena)
         )
        return false;
    else
        return true;
}
/* Función para validar que una cadena solo contenga letras
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fSoloLetrasRFC(cVCadena){
   if(cVCadena == "")
      return false;
    if ( fNum(cVCadena)          || fRarosRFC(cVCadena)       ||
         fPuntuacion(cVCadena)   || fSignos(cVCadena)      ||
         fArroba(cVCadena)       || fParentesis(cVCadena)  ||
         fPunto(cVCadena)        || fDiagonal(cVCadena)    ||
         fGuionBajo(cVCadena)    || fComa(cVCadena))
        return false;
    else
        return true;
 }
/* Función para validar el RFC
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fValRFC (cVRFC,iVTipo){
   switch(iVTipo){
   case 1: iVSiglas = 4; iVMin = 10; iVTam = 13; break;
   case 2: iVSiglas = 3; iVMin = 12; iVTam = 12; break;
   }
   if (cVRFC.length < iVMin || cVRFC.length > iVTam)
      return false;
   if (cVRFC.length > iVMin && cVRFC.length < iVTam) {
         return false;
   }
   if(fSoloLetrasRFC(cVRFC.substring(0,iVSiglas))    &&
      fSoloNumeros(cVRFC.substring(iVSiglas,iVSiglas + 6))  &&
      fSoloAlfanumericos(cVRFC.substring(iVSiglas + 6, iVTam)) && !fEspacio(cVRFC)  )
     return true;
   else
     return false;
}
/* Función para validar la Fecha
   Parámetros: cVCadena -- Cadena a verificar
   Valor que Regresa: true
                      false  */
function fValFecha(cVCadena,lMsg){
  if(lMsg != false) lMsg=true;
  if (   (fRaros(cVCadena)        || fPuntuacion(cVCadena) ||
          fSignos(cVCadena)       || fArroba(cVCadena)     ||
          fParentesis(cVCadena)   || fLetras(cVCadena)     ||
          fEspacio(cVCadena)      || fComa(cVCadena))      ||
          fGuionBajo(cVCadena)   ) {
     lMsg==true?alert('La información no es correcta.\nFavor de verificar el formato de la fecha: dd/mm/aaaa'):lMsg=false;
     return false;
  }
  if (cVCadena.length != 10) {
     lMsg==true?alert('Favor de verificar el formato de la fecha: dd/mm/aaaa.\n'):lMsg=false;
     return false;
  }
  if (!fDiagonal(cVCadena)){
     lMsg==true?alert('Favor de verificar el formato de la fecha: dd/mm/aaaa.\n' + cVCompM):lMsg=false;
     return false;
  }
   // Barrer la cadena
  iVPos = 0;
  var iVPDiag = new Array();
  for(var iVCont = 0; iVCont < cVCadena.length; iVCont ++){
     if(cVCadena.charAt(iVCont) == "/")
        iVPDiag[iVPos++] = iVCont;
  }
  if (iVPos != 2) {
    lMsg==true?alert('Favor de verificar el formato de la fecha: dd/mm/aaaa\n' + cVCompM):lMsg=false;
     return false;
  }
  cVDia   = cVCadena.substring(0,iVPDiag[0]);
  cVMes  = cVCadena.substring(iVPDiag[0] + 1,iVPDiag[1]);
  cVAnio  = cVCadena.substring(iVPDiag[1] + 1,cVCadena.length);
  if(parseInt(cVMes,10) < 1 || parseInt(cVMes,10) > 12) {
     lMsg==true?alert('El Mes no es válido.\nDebe ser un número entre 1 y 12'):lMsg=false;
     return false;
  }
  switch (parseInt(cVMes,10)){
  case  1:
  case  3:
  case  5:
  case  7:
  case  8:
  case 10:
  case 12:
           if(parseInt(cVDia,10) < 1 || parseInt(cVDia,10) > 31) {
                     lMsg==true?alert('El Día no es válido.\nDebe ser un número entre 1 y 31'):lMsg=false;
                     return false;
                          }
                break;
    case 2:	if(parseInt(cVDia,10) < 1 || parseInt(cVDia,10) > 29) {
                     lMsg==true?alert('El Día no es válido.\nDebe ser un número entre 1 y 29'):lMsg=false;
                     return false;
                          }
                break;
   default :            if(parseInt(cVDia,10) < 1 || parseInt(cVDia,10) > 30) {
                     lMsg==true?alert('El Día no es válido.\nDebe ser un número entre 1 y 30'):lMsg=false;
                     return false;
                          }
   }
  return true;
}
function fTelefono(cVCampo){
  return true;
}
function fEMail(cVCampo){
  return true;
}
/* Función para localizar un Espacio en Blanco al Inicio de la Cadena
   Parámetros: cVCadena -- Cadena en la cual se va a localizar el espacio en Blanco
   Valor que regresa:   true  -- Si lo localiza
                        false -- Si no lo encuentra */
function fEspBlanco(cVCadena){
   if (cVCadena.indexOf(' ') == 0)
      return true;
   else
      return false;
}
/* Función para mostrar los caracteres restantes de un campo de captura en otro campo con una longitud maxima el exceso es truncado */
  function fDespRestantes(fTextArea,fCampoDesp,iMaxLen){
    cText = fTextArea.value;
    if(cText.length > iMaxLen)
      fTextArea.value = cText = cText.substring(0,iMaxLen);
    fCampoDesp.value = iMaxLen - cText.length;
  }
/* Función que calcula el restante de una área texto */
  function fMxTx(oTexto,iMax){
    iMax = iMax - 1;
    if(oTexto.value.length <= iMax){
      for(i=0;i<frm.elements.length;i++){
        if(frm.elements[i].name=="AuxTxt"+oTexto.name)
           frm.elements[i].value=iMax-oTexto.value.length;
        if(frm.elements[i].name.substring(0,6)=="AuxTxt")
           frm.elements[i].disabled=true;
      }
    }else{
      oTexto.value=oTexto.value.substring(0,iMax);
    }
  }
/* Función que valida el formato de hora */
function fValHora(cTexto){
  if(cTexto.indexOf(":") != -1){
    cHora = cTexto.substring(0,cTexto.indexOf(":"));
    cMin =  cTexto.substring(cTexto.indexOf(":")+1);
    if(cMin.length < 2 || !fSoloNumeros(cMin))
      return false;
    if(cHora.length < 2 || !fSoloNumeros(cHora))
      return false;
    if(parseInt(cHora,10) > 23)
      return false;
    if(parseInt(cMin,10) > 59)
      return false;
    return true;
  }
  return false;
}
/* Función de Entero a formato de hora */
function fIntToHora(cTexto){
   iTmp = parseInt(cTexto,10);
   cHora = "";
   cMin = "";
   if(iTmp < 2400){
     cTxt = ""+iTmp;
     if(iTmp == 0)
       return "0:00";
     if(iTmp < 1000){
       cHora = cTxt.substring(0,1);
       cMin = cTxt.substring(1);
     }else{
       cHora = cTxt.substring(0,2);
       cMin = cTxt.substring(2);
     }
     if(parseInt(cMin,10) > 59){
       cMin = "";
       cHora = "";
     }else
       return cHora + ":" + cMin;
   }
   return "";
}
/* Función de Hora a Entero */
function fHoraToInt(cTexto){
  if(cTexto.indexOf(":") != -1){
    iHora = parseInt(cTexto.substring(0,cTexto.indexOf(":")),10);
    iMin =  parseInt(cTexto.substring(cTexto.indexOf(":")+1),10);
    if(iHora > 23)
      return 0;
    if(iMin > 59)
      return 0;
    return parseInt(""+iHora+""+cTexto.substring(cTexto.indexOf(":")+1),10);
  }
  return 0;
}
/* Función que regresa la hora (int) de una cadena en formato HH:MM */
function fGetHora(cTexto){
  return parseInt(cTexto.substring(0,cTexto.indexOf(":")),10);
}
/* Función que regresa los minutos (int) de una cadena en formato HH:MM */
function fGetMinutos(cTexto){
  return parseInt(cTexto.substring(cTexto.indexOf(":")+1),10);
} 

/* Función que elimina espacios del principio de una cadena */
function fLTrim(cadena){
  for(i=0; i<cadena.length; ){
    if(cadena.charAt(i)==" ")
      cadena=cadena.substring(i+1, cadena.length);
    else
      break;
  }
  return cadena;
}

/* Función que elimina espacios del fin de una cadena */
function fRTrim(cadena){
  for(i=cadena.length-1; i>=0; i=cadena.length-1){
    if(cadena.charAt(i)==" ")
      cadena=cadena.substring(0,i);
    else
      break;
  }
  return cadena;
}

/* Función que elimina espacios del principio y fin de una cadena */
function fTrim(cadena){
  return fLTrim(fRTrim(cadena));
}

  // Definición del manejo de Imágenes (Locales o Remotas)
  var SiAcvx = true;

  function getIEVersion(){
    var ua = navigator.userAgent
    var IEOffset = ua.indexOf('MSIE ')
    return parseFloat(ua.substring(IEOffset + 5, ua.indexOf(';', IEOffset)))
  }

  function fVerLocalImages(cLocal) {
      try{
        fsObj = new ActiveXObject("Scripting.FileSystemObject")
        SiAcvx = true;
        return fsObj.FolderExists(cLocal);
      }catch(e){
        if(SiAcvx == true){
          SiAcvx = false;
        }
        return false;
      }
  }

  function fVerRutaImg(cValor,cLocal) {
    cSuccess = cValor;
    if(navigator.appName == 'Microsoft Internet Explorer' && parseInt(getIEVersion()) > 4 && fVerLocalImages(cLocal)){
      cSuccess = cLocal;
    }else{
      cSuccess = cValor;
    }
    return cSuccess;
  }

  function fEncuentraObjeto(n, d) {
    var p,i,x;
    if(!d)
      d=vDocument;
    if((p=n.indexOf("?"))>0&&parent.frames.length) {
      d=parent.frames[n.substring(p+1)].document;
      n=n.substring(0,p);
    }
    if(!(x=d[n])&&d.all)
      x=d.all[n];
    if (d.forms){
      for (i=0;!x&&i<d.forms.length;i++)
        x=d.forms[i][n];
    }
    for(i=0;!x&&d.layers&&i<d.layers.length;i++)
      x=fEncuentraObjeto(n,d.layers[i].document);
    return x;
  }

  function fCambiaImagen() {
    var l,z=0,x,a=fCambiaImagen.arguments;
    var doc = a[0];
    vDocument = doc;
    doc.MM_sr=new Array;
    for(l=1;l<(a.length-2);l+=3)
    if ((x=fEncuentraObjeto(a[l]))!=null){
        doc.MM_sr[z++]=x;
        if(!x.oSrc)
          x.oSrc=x.src;
        cImg = a[l+2]+'/';
        cImg = fEntry(fNumEntries(cImg,"/"),cImg,"/");
        x.src=fVerRutaImg(cRutaImgServer,cRutaImgLocal) + cImg;
    }
  }

  function fPreCargarImagen(doc) {
     var d=doc;
     if(d.images){
       if(!d.MM_p)
         d.MM_p=new Array();
       var i,j=d.MM_p.length,a=fPreCargarImagen.arguments;
       for(i=1; i<a.length; i++)
         if (a[i].indexOf("#")!=0){
           d.MM_p[j]=new Image;
           d.MM_p[j++].src=a[i];
         }
     }
  }

  function fSrc(objImagen, cEstado){
    var cSourceTemp = objImagen.src;
    var cImageTemp  = cSourceTemp.substr(0, cSourceTemp.length - 5);
    var cExtTemp    = cSourceTemp.substr(cSourceTemp.length - 3, cSourceTemp.length);
    cImg = cImageTemp +'/';
    cImg = fEntry(fNumEntries(cImg,"/"),cImg,"/");
    objImagen.src   = fVerRutaImg(cRutaImgServer,cRutaImgLocal) + cImg + cEstado + '.' + cExtTemp;
  }
  function fGO(cNombre){
      return document.getElementById(cNombre);
  }

       
       




      
      function fBuscaFrame(cFrameM){
	   aFramesM = new Array();
	   lCont = true; iArray = 0;
	   iPosMax = fVerFrames(aFramesM, top, 0);
	   while(lCont){
	      aTmp = aFramesM[iArray];
	      try{
	        if(aTmp[1] == cFrameM){
	          return aTmp[0];
	        }
	      }catch(e){}
	      try{
	        if(aTmp[2] == true){
	          iPosMax = fVerFrames(aFramesM, aTmp[0], iPosMax);
	        }
	      }catch(e){}
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
	       try{ aFramesM[iPosM] = [frmM.frames[i],frmM.frames[i].name,true]; }catch(e){}
	      }else{
	       try{ aFramesM[iPosM] = [frmM.frames[i],frmM.frames[i].name,false]; }catch(e){}
	      }
	      iPosM = iPosM + 1;
	   }
	   return iPosM;
	} 
function fShowIntDocDig(iINTDocDigTmp){
    iINTDocDigFIEL = iINTDocDigTmp;
    fAbreSubWindow(false,"pgDownINTDOCDIG","no","yes","yes","yes","800","600",50,50);
}

function fShowConsultaExcel(){//exportar consultas ADV
    fAbreSubWindow(false,"pgConsultaExcel","no","yes","yes","yes","800","600",50,50);
}

function fShowHistoricoExcel(){//descargar hisotrico ADV
    fAbreSubWindow(false,"pgHistoricoExcel","no","yes","yes","yes","800","600",50,50);
}


function fGetINTDOCDIG(){
	  return iINTDocDigFIEL;
}

function fFinPagina(){ // 5000-Define el fin de una página a desplegar
    if(navigator.userAgent.indexOf("MSIE") > -1 ){ 
      cTx='<DIV ID="PRC" STYLE="position:absolute;top:'+((screen.availHeight - 200)/2)+';left:'+
           ((screen.availWidth - 360)/2)+';width:0;height:0;background-color:white';
      cTx+='clip:rect(0px 325px 140px 0px);">';
      cTx+='<FORM>';
      cTx+='<table border="0">';
      cTx+='<tr><td align="left" valign="middle"><img SRC="'+cRutaImgServer+'clock.jpg">';
      cTx+='</td></tr>';
      cTx+='</FORM></DIV>';
    }else{
   cTx='<DIV ID="PRC" STYLE="position:absolute;top:'+((screen.availHeight - 200)/2)+';left:'+
          ((screen.availWidth - 360)/2)+';width:0;height:0;background-color:white';
      cTx+='clip:rect(0px 325px 140px 0px);">';
      cTx+='<FORM>';
      cTx+='<table border="0">';
      cTx+='<tr><td align="left" valign="middle" class="EETiquetaC"><SPAN class="EEtiquetaC" ID="oWaitProcess" ></SPAN>';
      cTx+='</td></tr>';
      cTx+='</FORM></DIV>';
    } 
      cTx+='</form>'+"\n";
      cTx+='</body>'+"\n";
      cTx+='</html>'+"\n";
      cGPD+=cTx;
      return cTx;
  }

  function fEnProceso(lOpen){
  try{
    oProc = document.getElementById("PRC").style;
    if(navigator.userAgent.indexOf("MSIE") == -1 )
      oWaitProcess = document.getElementById("oWaitProcess");
    if(lOpen == true){
      oProc.clip = "rect(0px,360px,180px,0px)";
      oProc.style.focus();
      if(navigator.userAgent.indexOf("MSIE") == -1 )
         oWaitProcess.innertHTML = ".: Procesando :.";
    }else{
      oProc.clip = "rect(0px,0px,0px,0px)";
      if(navigator.userAgent.indexOf("MSIE") == -1 )
         oWaitProcess.innertHTML = "";
    }
  }catch(e){}
  }

  
  function fReserva(cData){
      cData = cData.toUpperCase();
      var unionReg = /(\-\-)|((\s*|\w*|^)(UNION|INSERT|DELETE|UPDATE|INTO|FROM|GRANT|VALUES|JOIN|WHERE|SELECT|REVOKE|AND|OR|CURRENT|LIKE|DROP)(((\s+|\W+)(\w+))|(w*)|$))/g;
      if (unionReg.test(cData)) {
         cTmp = cData; 
            //Si esta en medio.
         cTmp = cTmp.replace(/\ UNION /g,' ');
         cTmp = cTmp.replace(/\ INSERT /g,' ');
         cTmp = cTmp.replace(/\ DELETE /g,' ');
         cTmp = cTmp.replace(/\ UPDATE /g,' ');
         cTmp = cTmp.replace(/\ INTO /g,' ');
         cTmp = cTmp.replace(/\ FROM /g,' ');
         cTmp = cTmp.replace(/\ GRANT /g,' ');
         cTmp = cTmp.replace(/\ VALUES /g,' ');
         cTmp = cTmp.replace(/\ JOIN /g,' ');
         cTmp = cTmp.replace(/\ WHERE /g,' ');
         cTmp = cTmp.replace(/\ SELECT /g,' ');
         cTmp = cTmp.replace(/\ REVOKE /g,' ');
         cTmp = cTmp.replace(/\ AND /g,' ');
         cTmp = cTmp.replace(/\ OR /g,' ');
         cTmp = cTmp.replace(/\ CURRENT /g,' ');
         cTmp = cTmp.replace(/\ LIKE /g,' ');
         cTmp = cTmp.replace(/\ DROP /g,' ');
         //Si es la primera palabra
         try{
           cPrimera = cTmp.substring(0,cTmp.indexOf(' '));
           unionReg.test(cPrimera);
           if (unionReg.test(cPrimera)){
            cTmp = cTmp.substring(cTmp.indexOf(' ')+1);
           }
         }catch(e){}
         //Si es la última palabra
         try{
        cUltima = cTmp.substring(cTmp.lastIndexOf(' '));
        if (unionReg.test(cUltima)){
            cTmp = cTmp.substring(0,cTmp.lastIndexOf(' '));
        }
         }catch(e){}    
         cData = cTmp;
      }
      return cData;
     }

  /* Función para convertir a mayúsculas el valor de un campo
     Parámetros: campoFil -- nombre del campo a convertir(this) 
  function fMayus(campoFil){
    campoFil.value = fReemplazar(fTrim(campoFil.value.toUpperCase()));
    campoFil.value = fReserva(campoFil.value);
  }
  function fSinMayus(campoFil){
    campoFil.value = fTrim(fReemplazar(campoFil.value));
    campoFil.value = fReserva(campoFil.value);
  }
  function fMayusNoReplace(campoFil){
    campoFil.value = fTrim(campoFil.value.toUpperCase());
    campoFil.value = fReserva(campoFil.value);
  }
  */
  
  function fGetFilesValues(FRMObj){
	  frm = FRMObj;
	   var valFiles=new Array();
	   
	   for(var i=0;i<frm.elements.length;i++){
		   var objCamp = frm.elements[i];
		   if (objCamp.type == 'file'){
			   var arT = new Array();
		   		arT.push(objCamp.value);
		   		arT.push(objCamp.id);
			   valFiles.push(arT);
		   }
	   }
	   return valFiles;
	}
  
  function fGetNumFiles(FRMObj){
	   frm = FRMObj;
	   var numFiles=0;
	   
	   for(var i=0;i<frm.elements.length;i++){
		   var objCamp = frm.elements[i];
		   if (objCamp.type == 'file')
			   numFiles++;
	   }
	   return numFiles;
	}

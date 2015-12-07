// MetaCD=1.0
var cBarra = "33";
var tCpoMenu;
function fBefLoad(){ }

function fDefPag(){
   JSSource("jquery.js");
   JSSource("jquery-ui.js");
   fInicioPagina('63A580','',false);
   InicioTabla("",0,"100%","45","white","tituloNew03.gif",".1",".1");
      ITRTD("",0,"1","","left");
         BtnImg("BtnAcerca","Sipymm_plecaA","fAcerca();","Acerca de...");
      FITD("",0,"100%","","center","middle")  
       
      FITD("",0,"1");
      	cGPD+= "<div id='divInternet' style='display:none;'>";
         	BtnImg("BtnMenu","internet","fShowInternetDialog();","Muestra la ventana de ayuda del trámite por internet...");
         cGPD+= "</div>";
      FITD("",0,"1");
         BtnImg("BtnMenu","menu","fEsconder();","Muestra al Menú del Sistema...");//ITD();TextoSimple("Menu");FTD();
      FITD("",0,"1");
         BtnImg("BtnAyuda","ayuda","fAyuda();","Muestra la Ayuda de la Pantalla...");//ITD();TextoSimple("Ayuda");FTD();
      FITD("",0,"1");
         BtnImg("BtnSalir","salir","fSalir();","Cierra al Sistema...");//ITD();TextoSimple("Salir");FTD();
      FITD("",0,"0","","right","middle");
         DinTabla("IDProceso","",0,"0","0");
      FTDTR();
    cGPD += '<div id="INTdialog" style="text-align:justify;"></div>';
    cGPD += '<div id="AlertGen" style="text-align:justify;"></div>';
    FinTabla();
    FITR()
    FTR();
    cGPD+='</form></body></html>';
   fFinPagina();
}
function fOnLoad(){ // Carga información al mostrar la página.
    frm = document.forms[0];
    oProceso = (document.all) ? document.all.IDProceso:document.getElementById("IDProceso");
    tCpoMenu= oProceso.tBodies[0];

    $("#INTdialog").dialog({
      resizable: true,
      autoOpen: false,
      width: 600,
      height: 400});

    $("#AlertGen").dialog({
      resizable: false,
      autoOpen: false,
      width: 600});
}
function fEsconder(){
   FRMDatos = fBuscaFrame('FRMDatos', top);
   if(FRMDatos.document.body.cols != "0,100%"){
      cBarra = FRMDatos.document.body.cols;
      FRMDatos.document.body.cols="0,100%";
   }else{
      FRMDatos.document.body.cols=cBarra;
   }
}
function fSalir(){
  if(confirm(cAlertMsgGen + "\n\n¿Desea Salir de la aplicación?")){
    fEngSubmite("pgDelUsrSes.jsp","CerrarSesion");
    fSalirTotal();
  }
}
function fResultado(aRes,cId){
  if(cId == "CerrarSesion"){
    frm.action = 'index.jsp';
    frm.target = '_top';
    frm.submit();
  }
}
function fEnProceso(lEnProc){
  if(tCpoMenu && tCpoMenu.rows)
    for(i=0;tCpoMenu.rows.length;i++)
      tCpoMenu.deleteRow(0);
    FRMPagina = fBuscaFrame("FRMPagina");
    if(lEnProc==true){
      newRow  = tCpoMenu.insertRow(0);
      newCell = newRow.insertCell(0);
      newCell.className="EProceso";
      newCell.innerHTML="Procesando..."+SP()+SP()+SP();
	  if(FRMPagina.fEnProceso)
         FRMPagina.fEnProceso(true);
    }else{
  	  if(FRMPagina.fEnProceso)
        FRMPagina.fEnProceso(false);
	}
}
function fAyuda(){
  cAyuda=top.fGetNombrePrograma();
  cAyuda=cAyuda.substring(0,cAyuda.length-3)+cExtensiónAyuda;
  fAbreWindowHTML(false,cRutaAyuda+cAyuda,'yes','yes','yes','yes','no',750,580,10,10);
}
function fAcerca(){
  fAbreSubWindow(true,"pgAcerca","yes","yes","yes","yes",600,400);
}



function BtnImgJPG(cNombreM,cNomImgM,cHRefM,cEstatusM,l4Status,cImgIni){ // 3000-botón de tipo imagen
   iImgs++;
   aImgs[iImgs] = cRutaImgServer+cNomImgM;
   var imgIni = "01";
   cTx='<a href="JavaScript:'+cHRefM+'"'+"\n";
   if(l4Status == true){
     cTx+=' onMouseOut="'+"if(fMouseOut)fMouseOut(document, '"+cNomImgM+"');self.status='';"+'return true;"'+"\n";
     cTx+=' onMouseOver="'+"if(fMouseOver)fMouseOver(document, '"+cNomImgM+"');self.status='"+cEstatusM+"';"+'return true;">'+"\n";
   }else{
     cTx+=' onMouseOut="'+"if(fCambiaImagen)fCambiaImagen(document,'"+cNombreM+"','','"+cRutaImgServer+cNomImgM+"01.jpg',1);self.status='';" + 'return true;"'+"\n";
     cTx+=' onMouseOver="'+"if(fCambiaImagen)fCambiaImagen(document,'"+cNombreM+"','','"+cRutaImgServer+cNomImgM+"02.jpg',1);self.status='"+cEstatusM+"';" + 'return true;">'+"\n";
   }
   if (cImgIni && cImgIni != "")
     imgIni = cImgIni;
   cTx+='<img border="0" name="'+cNombreM+'" src="'+cRutaImgServer+cNomImgM+imgIni+'.jpg" alt="' + cEstatusM + '">';
   cTx+='</a>'+"\n";
   cGPD+=cTx;
   return cTx;
}

function fShowInternetDialog(){
	try{
		FRMPagina = fBuscaFrame("FRMPagina");
		FRMPagina.fOpenINTDialog(true);
	}catch(e){
		fHideInternet();
	}
}

function fShowInternet(){
	fGO("divInternet").style.display = "block";
}

function fHideInternet(){
	fGO("divInternet").style.display = "none";	
}
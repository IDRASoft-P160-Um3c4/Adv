// MetaCD=1.0
var iNoPags;
var cNomPags;
var tCpoTabs;
var cPags;
var cNoFoldsAct;
var cFolDes="";
var aNomPags;
var iPagAct=0;
var aINTSol = new Array();
objINTTramite = {
	     //ICVETRAMITE: 1,
	     //ICVETIPOPERMISIONA: 1,
	     //ICVETIPOTRAMITE: 1,
	     //TSREGISTRO: "2012-08-21",
	     //TSFIN: "2012-09-01",
	     //ICVEDEPARTAMENTO: 70,
	     //CDSCTIPOTRAMITE: "Tramite de prueba",
	     //ICVETIPOTRAPROD: 0
};
function fDefCarpeta(cPaginas,cTitulos,cNomPag,cWidth,cHeight,lPermisos){ // Define la página a ser mostrada
    cNomPags = cNomPag;
    cPags = cPaginas;
    //Estilo("A:Link","COLOR:FFFFFF;font-family:Verdana;font-size:10pt;font-weight:normal;TEXT-DECORATION:none");
    Estilo("A:Link","COLOR:000000;font-family:Verdana;font-size:10pt;font-weight:normal;TEXT-DECORATION:none");
    Estilo("A:Visited","COLOR:000000;font-family: Verdana;font-size:10pt;font-weight:normal;TEXT-DECORATION:none");
    //Estilo("A:Visited","COLOR:FFFFFF;font-family: Verdana;font-size:10pt;font-weight:normal;TEXT-DECORATION:none");
    Estilo(".EColFol","border:1px solid #6B96AD;padding:0px;spacing:0px;");
    InicioTabla("",0,cWidth,cHeight,"center","",".1",".1");
      ITRTD("",0,"100%","1");
          DinTabla("TFolTabs","EFolder",0,"","","","","",".1",".1");
          iNoPags=fNumEntries(cPaginas,"|");
      FTDTR();
      ITRTD("",0,"100%","100%","center","top");
        InicioTabla("",0,"100%","100%","","","","","E4E4E4");
          ITD("",10,"100%","100%","center","top");
            cNoFoldsAct='';
            aNomPags=new Array();
            for(dc=1;dc<=iNoPags;dc++){
              cPW = fEntry(dc,cTitulos,"|");
              aNomPags[parseInt(dc,10)-1]=cPW;
              if(cPW && top.fGetPermiso){
                if(top.fGetPermiso(cPW) != 2 || lPermisos == false){
                  IFrame(cNomPag+dc,"0","0",cPW,"yes");
                  cNoFoldsAct=cNoFoldsAct+dc+'|';
                }
              }
            }
          FTDTR();
        FinTabla();
      FTDTR();
    FinTabla();
}

function fLoadFolder(iPagSelectedM){
   theTable = (document.all) ? document.all.TFolTabs:
   document.getElementById("TFolTabs");
   tCpoTabs= theTable.tBodies[0];
   for(dc=0;tCpoTabs.rows.length;dc++){
     tCpoTabs.deleteRow(0);
   }
   newRow  = tCpoTabs.insertRow(0);
   iNoDCReal=1;
   for(dc=1;dc<=iNoPags;dc++){
      if(fVerPagEsp(dc)==true){
        newCell = newRow.insertCell(iNoDCReal-1);
        iNoDCReal++;
        newCell.width="121";
        newCell.height="24";
        newCell.align="center";
        cImgFondo = "C0C0C0";
        cEstilo = "EColFol";
        if(dc==iNoPags)
          iPagAct = "C0C0C0";        
        if(iPagSelectedM==dc){
          cImgFondo = "E4E4E4";
          cEstilo = "";
        }
        cPagNomPes = fEntry(dc,cPags,"|");
        if(cPagNomPes.length > 16)
          cPagNomPes = cPagNomPes.substring(0,15)+"<br>"+cPagNomPes.substring(15);

        newCell.innerHTML = InicioTabla("",0,"100%","100%","","",".1",".1",cImgFondo)+ITRTD(cEstilo,0,"100%","100%","left")+
                            Liga(SP()+SP()+fEntry(dc,cPags,"|"),"fPagFolder("+dc+")",fEntry(dc,cPags,"|"))+
                            FinTabla();
      }
   }
}

function fVerPagEsp(iDC){
  iNoPagsReal=fNumEntries(cNoFoldsAct,"|");
  for(ijkl=1;ijkl<=iNoPagsReal;ijkl++){
    if(fEntry(ijkl,cNoFoldsAct,"|")==''+iDC)
      return true;
  }
  return false;
}

function fPagFolder(iPag){
   if(fFolderOnChange(iPag,iPagAct) != false){
      fLoadFolder(iPag);
      for(iklmn=1;iklmn<=iNoPags;iklmn++){
        if(fVerPagEsp(iklmn)==true){
           iFrm = document.getElementById(cNomPags+iklmn);
           if(iklmn==iPag){
             iFrm.style.width = '100%';
             iFrm.style.height = '100%';
             top.fGetTituloPagina(aNomPags[iklmn-1]);
             FRMTmp=fBuscaFrame(cNomPags+iklmn);
             FRMTmp.focus();
             FRMTmp.fOnFocus();
           }else{
             iFrm.style.width = '0';
             iFrm.style.height = '0';
           }
        }
      }
   }
}


function fOpenINTDialog(lopen){
	if(lopen){
		$("#INTdialog").dialog('open');
		FRMTit = fBuscaFrame("FRMTitulo")
		FRMTit.fHideInternet();
	}else if($("#INTdialog").dialog('isOpen')){
		$("#INTdialog").dialog('close');
		FRMTit = fBuscaFrame("FRMTitulo")
		FRMTit.fShowInternet();
	}
}

function fSetINTDialogContent(cContent){
	$("#INTdialog").html(cContent);
}

function fSetINTDialogTitle(cTitle){
	$("#INTdialog").dialog('option', 'title',cTitle);
}

function fGetCampos(iEjercicio,iNumSolicitud){
    frm.hdFiltro.value = "td.IEJERCICIO = "+iEjercicio+
                         " and td.INUMSOLICITUD = "+iNumSolicitud+
                         " and tc.ccarpeta = '"+cPaginaWebJS+"'";
    frm.hdOrden.value  = "tc.IORDEN";
    fEngSubmite("pgINTCampoXCarp.jsp","cIdCampos");
}
function fSetObjTramiteInternet(obj){
	objTramiteInternet = obj;
	fIntegraCampos();
}

function fIntegraCampos(){
		obj = objTramiteInternet;
		cContenido = "<table cellspacing='0' width='100%'>";
		cContenido += "<tr><td class='ETablaSTInt'>Etiqueta</td>";
		cContenido += "<td class='ETablaSTInt'>Valor</td>";
		cContenido += "<td class='ETablaSTInt'>&nbsp;</td>";
		j = 0;
		for(v = 0; v < obj.campos.length; v++){
			// if(obj.campos[v].CCARPETA == "SOL" && obj.campos[v].CCAMPO != "cRFCREP"){
				aINTSol[j++] = obj.campos[v];
				cContenido += "<tr class='ETablaInfo' style='height:25px'><td class='EPieInt'>" + obj.campos[v].CETIQUETA + "</td>";
				cContenido += "<td class='Eliga'>" + (obj.campos[v].CVALOR2 != "" ? obj.campos[v].CVALOR2 : obj.campos[v].CVALOR1) + "</td>";
				cContenido += " <td style='padding-top:8px;padding-bottom:5px;text-align:right;'>" + BtnImg("Nombre", "aceptarc", "fIntegraCampo('" + obj.campos[v].CCAMPO + "','" + obj.campos[v].CVALOR1 + "','" + obj.campos[v].CVALOR2 + "');"); 
				+ "</td></tr>";
		}
		cContenido += "</table><br />";
		fSetINTDialogTitle("Campos a integrar");
		fSetINTDialogContent(cContenido);
		fAbreDialogo();
}


function Liga1(cNombreM,cHRefM,cEstatusM){ // 3000-Botón de tipo imagen		    
    cTx='<a href="JavaScript:'+cHRefM+'"'+"\n";
    cTx+=' onMouseOut="' + "self.status='';" + 'return true;"'+"\n";
    cTx+=' onMouseOver="' + "self.status='"+cEstatusM+"';" + 'return true;">'+"\n";
    cTx+=cNombreM+"\n";
    cTx+='</a>'+"\n";
    cGPD+=cTx;
    return cTx;
}
function fAbreDialogo(){
    FRMTit = fBuscaFrame("FRMTitulo");
    FRMTit.fShowInternetDialog();
}

function fListadoTramInt(cId,cError,aRes){
    if(cId=="cIdCampos" && cError==""){
	frm.hdFiltro.value = "";
	frm.hdOrden.value  = "";
	aCampos = new Array();
	for(u = 0; u < aRes.length; u ++){
	    
		obj = { ICONSECUTIVO: aRes[u][1], 
			IORDEN: aRes[u][2], 
			CCAMPO: aRes[u][3], 
			CVALOR1: aRes[u][4], 
			CVALOR2: aRes[u][5], 
			CCARPETA: aRes[u][6], 
			CETIQUETA: aRes[u][7], 
			CCAMPOCOP: aRes[u][8]};
		aCampos[u] = obj;
	}
	fSetObjTramiteInternet({campos: aCampos, tramite: objINTTramite});
    }
}

objINTTramite = {
	     //ICVETRAMITE: 1,
	     //ICVETIPOPERMISIONA: 1,
	     //ICVETIPOTRAMITE: 1,
	     //TSREGISTRO: "2012-08-21",
	     //TSFIN: "2012-09-01",
	     //ICVEDEPARTAMENTO: 70,
	     //CDSCTIPOTRAMITE: "Tramite de prueba",
	     //ICVETIPOTRAPROD: 0
};
// MetaCD=1.0
var iNoPags;
var cNomPags;
var tCpoTabs;
var cPags;
var cNoFoldsAct;
var cFolDes="";
var aNomPags;
var iPagAct=0;
function fDefCarpeta(cPaginas,cTitulos,cNomPag,cWidth,cHeight,lPermisos){ // Define la página a ser mostrada
    cNomPags = cNomPag;
    cPags = cPaginas;
    Estilo("A:Link","COLOR:FFFFFF;font-family:Verdana;font-size:10pt;font-weight:normal;TEXT-DECORATION:none");
    Estilo("A:Visited","COLOR:FFFFFF;font-family: Verdana;font-size:10pt;font-weight:normal;TEXT-DECORATION:none");
    Estilo(".EColFol","border:1px solid #6B96AD;padding:0px;spacing:0px;");
    InicioTabla("",0,cWidth,cHeight,"center","",".1",".1");
      ITRTD("",0,"100%","1");
          DinTabla("TFolTabs","EFolder",0,"","","","","",".1",".1");
          iNoPags=fNumEntries(cPaginas,"|");
      FTDTR();
      ITRTD("",0,"100%","100%","center","top");
        InicioTabla("",0,"100%","100%","","","","",EFolder);
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
        cImgFondo = cColorPesFolder;
        cEstilo = "EColFol";
        if(iPagSelectedM==dc){
          cImgFondo = cColorGenJS;
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


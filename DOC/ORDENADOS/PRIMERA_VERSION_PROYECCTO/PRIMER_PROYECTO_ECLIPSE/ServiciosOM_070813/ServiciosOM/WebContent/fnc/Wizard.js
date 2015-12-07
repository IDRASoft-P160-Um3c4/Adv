// MetaCD=1.0
var iNoPags;
var cNomPags;
var tCpoTabs;
var tCpoWiz;
var cPags;
var cNoFoldsAct;
var cFolDes="";
var aNomPags;
var iPagAct=0;
function fDefWizard(cPaginas,cTitulos,cNomPag,cWidth,cHeight,lPermisos){ // Define la página a ser mostrada
    cNomPags = cNomPag;
    cPags = cPaginas.toLowerCase();
    Estilo("A:Link","COLOR:"+cColorLigasWiz+";background-color:"+cColorBGWiz+";font-family:Verdana;font-size:11pt;font-weight:bold;TEXT-DECORATION:none");
    Estilo("A:Visited","COLOR:"+cColorLigasWiz+";background-color:"+cColorBGWiz+";font-family: Verdana;font-size:11pt;font-weight:bold;TEXT-DECORATION:none");
    Estilo(".EDisWiz","COLOR:GRAY;font-family:Verdana;font-size:10pt;font-weight:bold;");
    Estilo(".EColWiz","background-color:"+cColorBGWiz+";text-align: center ;");      
    Estilo(".EColFol","background-color:"+cColorGenJS+";");
    InicioTabla("",1,cWidth,cHeight,"center","",".1",".1");
      ITRTD("",2,"100%","100%","center","top");
        iNoPags=fNumEntries(cPaginas,"|");
        InicioTabla("",0,"100%","100%","","",".1",".1",cColorGenJS);
          ITRTD("EEtiquetaC",0,"100%");
            DinTabla("TFolTabs","EFolder",0,"100%","","","","",".1",".1");             
          FTDTR();                  
          ITRTD("",10,"100%","100%","center","top");
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
          ITRTD("",2,"100%","25","right");
            DinTabla("TFolWiz","EColWiz",1,"100%","","","","",".1",".1");          
          FTDTR();
        FinTabla();
      FTDTR();
    FinTabla();
}
function fTitWizard(iPagAct){ 
   cEstado = ""; 
   theTable = (document.all) ? document.all.TFolTabs:document.getElementById("TFolTabs");
   tCpoTabs = theTable.tBodies[0];   
   for(dc=0;tCpoTabs.rows.length;dc++){
     tCpoTabs.deleteRow(0);
   }
   newRow = tCpoTabs.insertRow(0);
   newRow.className="ESTitulo";  
   newCell = newRow.insertCell(0);   
   for(ixy=0;ixy<fNumEntries(cPags,'|');ixy++){
      if(iPagAct-1 == ixy)   
         cEstado += "/ -- " + fEntry(ixy+1,cPags.toUpperCase(),"|") + " -- ";
      else
         cEstado += "/" + fEntry(ixy+1,cPags,"|");      
   } 
   newCell.align="left"; 
   newCell.innerText=cEstado;          
}
function fBtnWizard(cBtnPanel){
   lInicio = false; lAnterior = false; lSiguiente = false; lFinal = false;  
   if(cBtnPanel != "" && cBtnPanel != "Disabled"){     
      iNumEst=fNumEntries(cBtnPanel,',');
      if(iNumEst > 0){
       for(i=0;i<iNumEst;i++){
          if(fEntry(i+1,cBtnPanel,",")=="Inicio") lInicio=true;
          if(fEntry(i+1,cBtnPanel,",")=="Anterior") lAnterior=true;
          if(fEntry(i+1,cBtnPanel,",")=="Siguiente") lSiguiente=true;
          if(fEntry(i+1,cBtnPanel,",")=="Finalizar") lFinal=true;                    
       }
     }
   }else{
      if(cBtnPanel != "Disabled"){   
         lInicio = true; lAnterior = true; lSiguiente = true; lFinal = true;
      }   
   }

   theTable = (document.all) ? document.all.TFolWiz:document.getElementById("TFolWiz");
   tCpoWiz = theTable.tBodies[0];   
   for(dc=0;tCpoWiz.rows.length;dc++){
     tCpoWiz.deleteRow(0);
   }      
   newRow = tCpoWiz.insertRow(0);   
   i=0;            
   newCell = newRow.insertCell(i++);
   newCell.innerHTML=SP();

   newCell = newRow.insertCell(i++);
   newCell.width="130";      
   if(lInicio == true)      
     newCell.innerHTML = Liga("INICIO","fAccion('Inicio');","Inicio...");
   else{
     newCell.className = "EDisWiz";
     newCell.innerHTML = TextoSimple("INICIO");
   }
   newCell = newRow.insertCell(i++);
   newCell.width="130";           
   if(lAnterior == true)   
     newCell.innerHTML = Liga("<< ANTERIOR","fAccion('Anterior');","Anterior...");
   else{
     newCell.className = "EDisWiz";
     newCell.innerHTML = TextoSimple("<< ANTERIOR");   
   }
   newCell = newRow.insertCell(i++);
   newCell.width="130";             
   if(lSiguiente == true)
     newCell.innerHTML = Liga("SIGUIENTE >>","fAccion('Siguiente');","Siguiente...");
   else{
     newCell.className = "EDisWiz";   
     newCell.innerHTML = TextoSimple("SIGUIENTE >>");   
   }
   newCell = newRow.insertCell(i++);
   newCell.width="130";   
   if(lFinal == true)   
     newCell.innerHTML = Liga("FINALIZAR","fAccion('Finalizar');","Finalizar...");
   else{
     newCell.className = "EDisWiz";   
     newCell.innerHTML = TextoSimple("FINALIZAR");   
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

function fAccion(cAccion){
  fWizardOnChange(iPagAct, cAccion);
}
function fSelPag(iPag,cBtns){
      iPagAct = iPag;
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
      fTitWizard(iPagAct);
      cBtnGral = "";
      if(cBtns)
        cBtnGral = cBtns;               
      fBtnWizard(cBtnGral);      
}


// MetaCD=1.0
var tCpoMarquee;
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
   cPaginaWebJS = "pg"+iNDSADM+"00009.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
}
function fDefPag(){ // Define la página a ser mostrada
   fInicioPagina("FFFFFF");
   InicioTabla("",0,"100%","100%","center","",".1",".1");
     ITRTD("","","","","center");
       InicioTabla("",0,"","","center");
         ITRTD("","","100","","center","bottom");
          Img("SCT-SEMAR2.gif","Página de Inicio del Sistema");
         FTDTR
       FinTabla();
       DinTabla("TDMarquee","",0,"95%","","center");
     FTDTR
   FinTabla();
   fFinPagina();
}

function fOnLoad(){ // Carga información al mostrar la página.
   theTable = (document.all) ? document.all.TDMarquee:document.getElementById("TDMarquee");
   tCpoMarquee= theTable.tBodies[0];
   fEngSubmite("pgGRLMensajes.jsp","IDMen");
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cId == "IDMen" && cError == ""){
      for(i=0;tCpoMarquee.rows.length;i++){
         tCpoMarquee.deleteRow(0);
      }
      cMrq = "";
      for(i=0;i<aRes.length;i++){
         aTmp = aRes[i];
         cMrq += aTmp[2] + " - " + aTmp[3] + " \n<br>";
      }
      newRow  = tCpoMarquee.insertRow(0);
      newCell = newRow.insertCell(0);
      newCell.innerHTML = Marquee(cMrq,"MRQ","center","10","up","100%","100");
   }
}

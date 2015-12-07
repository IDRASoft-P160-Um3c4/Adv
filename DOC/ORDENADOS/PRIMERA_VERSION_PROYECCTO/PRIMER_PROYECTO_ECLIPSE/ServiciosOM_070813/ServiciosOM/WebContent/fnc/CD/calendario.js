// MetaCD=1.0
  var frm;

  var iAnio;
  var iMes;
  var iDia;
  var iRef;
  var iDiaUno;

  var theTable;
  var theTableBody;
  var newCell;
  var newRow;
  var aMeses;
  var cMeses = new Array();
  var oDTLocal;

function fBefLoad(){
     dtActualCal = new Date()

     cFechaActual = "";
     if(top.opener.fGetFechaLocal)
       oDTLocal = top.opener.fGetFechaLocal();
     if(oDTLocal)
       cFechaActual = oDTLocal.value;

     iAnioTmpLocal = dtActualCal.getYear();

     if(navigator.appName == "Netscape")
       iAnioTmpLocal = iAnioTmpLocal + 1900;

     if(cFechaActual == "")
       cFechaActual = ''+dtActualCal.getDate()+'/'+(dtActualCal.getMonth()+1)+'/'+iAnioTmpLocal;

     cFechaActual += "/";
     iDia = parseInt(fEntry(1,cFechaActual,"/"),10);
     iRef = iDia;
     iMes = parseInt(fEntry(2,cFechaActual,"/"),10) - 1;
     iAnio = parseInt(fEntry(3,cFechaActual,"/"),10);

     iDiaUno = fFirstDay(iAnio,iMes);
     cMeses[0]="Enero";
     cMeses[1]="Febrero";
     cMeses[2]="Marzo";
     cMeses[3]="Abril";
     cMeses[4]="Mayo";
     cMeses[5]="Junio";
     cMeses[6]="Julio";
     cMeses[7]="Agosto";
     cMeses[8]="Septiembre";
     cMeses[9]="Octubre";
     cMeses[10]="Noviembre";
     cMeses[11]="Diciembre";
}
function fDefPag(){
   fInicioPagina("FFFFFF","Calendario",true);
   Estilo("A:Link","COLOR:000000;font-family:Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
   Estilo("A:Visited","COLOR:000000;font-family: Verdana;font-size:8pt;font-weight:bold;TEXT-DECORATION:none");
   InicioTabla("ETablaInfo",1,"200","1","","",".1",".1","");
     ITRTD("",7,"","","center");
     cGPD+='<'+'SELECT NAME="SLSMes" SIZE="1" onChange="fSLS();" >';
     for(x=0;x<cMeses.length;x++){
        if(iMes == x)
           cGPD+='<'+'OPTION SELECTED value='+x+'>'+cMeses[x];
        else
           cGPD+='<'+'OPTION value='+x+'>'+cMeses[x];
     }
     FTDTR();
     ITRTD("",0,"","","center");Liga('|<<','fCambio(1);','Año Anterior...');
     FITD("",5,"","","center");Text(true,"FILAnio",iAnio,"4","4","Anio...","fSLS();",'onKeyDown="return fCheckReturn(event);"');
     FITD("",0,"","","center");Liga('>>|','fCambio(4);','Año Siguiente...');
     FTDTR();
   FinTabla();
   DinTabla("myTABLE","",1,"200","150","","","",".1",".1","");
   fFinPagina();
}
function fOnLoad(){
     frm = document.forms[0];
     if(navigator.appName != "Netscape")
       frm.SLSMes.focus();
     init();
     fDelTable();
     fGenCalendar();
}
function fFirstDay(iA,iM){
     var firstDate = new Date(iA,iM,1);
     return firstDate.getDay();
}
function init() {
    theTable = (document.all) ? document.all.myTABLE:document.getElementById("myTABLE")
    theTableBody = theTable.tBodies[0]
    if((iAnio % 4) == 0)
      aMeses = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    else
      aMeses = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
}
function fDelTable(){
    for(i=0;theTableBody.rows.length;i++){
      theTableBody.deleteRow(0);
    }
}
function fGenCalendar(){
    var iRow = 0; var iCell = 0; var iDia = 1, iColor;
    if((iAnio % 4) == 0)
      aMeses = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    else
      aMeses = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    fInsRow(iRow);
    fInsCell(0,0,"Dom",1);
    fInsCell(1,0,"Lun",1);
    fInsCell(2,0,"Mar",1);
    fInsCell(3,0,"Mie",1);
    fInsCell(4,0,"Jue",1);
    fInsCell(5,0,"Vie",1);
    fInsCell(6,0,"Sab",1);
    iRow++;
    fInsRow(iRow);
    for(i=0;i<42;i++){
      if(i<iDiaUno || iDia>aMeses[iMes]){
        fInsCell(iCell,0,SP());
      }
      if(i>=iDiaUno && iDia<=aMeses[iMes]){
        iColor = 5;
        if(iRef == iDia)
          iColor = 2;
        fInsCell(iCell,0,Liga(iDia,"fSetDate("+iDia+","+iMes+","+iAnio+");",iDia),iColor);
        iDia++;
      }
      iCell++;
      if(iCell == 7){
        iCell = 0;iRow++;
        fInsRow(iRow);
      }
    }
  }
  function fInsRow(iR){
     newRow = theTableBody.insertRow(iR);
  }
  function fInsCell(iC, iCol, cVal, cColor ){
      newCell = newRow.insertCell(iC);
      newCell.innerHTML = cVal;
      newCell.align = "center";
      if(iCol!=0)
        newCell.colSpan = iCol;
      if(cColor == 1){
        newCell.bgColor = "#6BA6D6";
        newCell.className = "EEtiquetaC";
      }
      if(cColor == 2)
        newCell.bgColor = "#FA9696";
      if(cColor == 3)
        newCell.bgColor = "#6BA6D6";
      if(cColor == 4)
        newCell.bgColor = "#6BA6D6";
      if(cColor == 5)
        newCell.bgColor = "#BDD3EF";
  }
  function fCambio(cValor){
    if(cValor == 1){
      iAnio = parseInt(iAnio,10) - 1;
      iDiaUno = fFirstDay(iAnio,iMes);
    }
    if(cValor == 2){
      iMes = iMes - 1;
      if(iMes < 0)
        iMes = 0;
      iDiaUno = fFirstDay(iAnio,iMes);
    }
    if(cValor == 3){
      iMes = iMes + 1;
      if(iMes > 11)
        iMes = 11;
      iDiaUno = fFirstDay(iAnio,iMes);
    }
    if(cValor == 4){
      iAnio = parseInt(iAnio,10) + 1;
      iDiaUno = fFirstDay(iAnio,iMes);
    }
    frm.FILAnio.value = iAnio;
    fDelTable();
    fGenCalendar();
  }
  function fSLS(cValor){
      form=document.forms[0];
      iAux = ''+parseInt(form.FILAnio.value,10);
      if(iAux != 'NaN')
        iAnio = iAux;
      iMes = parseInt(form.SLSMes.value,10);
      iDiaUno = fFirstDay(iAnio,iMes);
      fDelTable();
      fGenCalendar();
  }
  function fSetDate(dia,mes,anio){
    mes = mes+1;
    cVMes = '' + mes;
    if(mes < 10)
      cVMes = "0" + mes;
    cVDia = '' + dia;
    if(dia < 10)
      cVDia = "0" + dia;

    if(oDTLocal)
       oDTLocal.value = '' + cVDia + '/' + cVMes + '/' + anio;

    if(top.opener)
      if(top.opener.fCambioFecha)
        top.opener.fCambioFecha(oDTLocal);

    top.close();
  }
  function fCheckReturn(evt){
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if(charCode == 13){
        fSLS();
        return false;
    }
  }

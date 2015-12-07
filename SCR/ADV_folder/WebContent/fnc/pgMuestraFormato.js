var cTx;
var theMainTable;
var theMainBodie;
var theTable;
var theBodie;
var iNumFormatos = 1;
var cTituloGrlFormato = "";
var aResTotal = new Array();
var cBGColorGrid = "dddddd";

function fOnLoad(){
  fInitMainTable();
  fDrawMainTable();
}

function fInitMainTable(){
  theMainTable = (document.all) ? document.all.mainTable:
  document.getElementById("mainTable");
  theMainBodie = theMainTable.tBodies[0];
}

function fInitDinamicTable(iNumTable){
  theTable = (document.all) ? eval("document.all.theTable"+iNumTable):document.getElementById("theTable"+iNumTable);
  theBodie = theTable.tBodies[0];
}

function fDrawMainTable(){
  cGPD = "";
  fTituloImprimirSalir("COORDINACIÓN GENERAL DE PUERTOS Y MARINA MERCANTE<BR>"+cTituloGrlFormato+'<BR>Consulte nuestra página <a href="http://e-mar.sct.gob.mx" target="_new" style="color:#FFFFFF">http://e-mar.sct.gob.mx</a>', "pgMuestraFormato.js");
  newRow  = theMainBodie.insertRow(0);
  newRow.style.backgroundColor="FFFFFF";
  newCell = newRow.insertCell(0);
  newCell.className = "ESTitulo";
  newCell.align = "center";
  newCell.innerHTML = cGPD;
  cGPD = "";

  theMainBodie.deleteRow(1)

  for(var i=1; i<=iNumFormatos; i++){
    cGPD="";
    DinTabla("theTable"+i,"ETabla",0,"100%","","center","top","",0,0,"");
    if(i > 3)
      newRow  = theMainBodie.insertRow(i+1);
    else
      newRow  = theMainBodie.insertRow(i);
    newRow.style.backgroundColor=cBGColorGrid;
    newCell = newRow.insertCell(0);
    newCell.className = "ETabla";
    newCell.align = "center";
    newCell.innerHTML = cGPD;
    fInitDinamicTable(i);
    cGPD = "";
    fDrawTables(i-1);
  }
  newRow  = theMainBodie.insertRow(2);
  newRow.style.backgroundColor=cBGColorGrid;
  newCell = newRow.insertCell(0);
  newCell.className = "EEtiquetaL";
  newCell.innerHTML = "Nota. Los datos marcados con (*) son obligatorios.";
}

function fDrawTables(iARes){
  var theRow, theCell;
  var i;
  var iRengAct = 0;
  var aResUso = fCopiaArregloBidim(aResTotal[iARes]);

  if(iARes == 2){
    theRow = theMainBodie.insertRow(1);
    theCell = theRow.insertCell(0);
    theCell.className = "ESTitulo";
    theCell.innerHTML = aResUso[0][2];
  }
  if(aResUso && aResUso.length && aResUso.length > 0){
    var iRenglones = 1, iColumnas = 1;
    for(i=0; i<aResUso.length; i++){
      if(iRenglones < aResUso[i][23]) iRenglones = aResUso[i][23];
      if(iColumnas < aResUso[i][24]) iColumnas = aResUso[i][24];
    }

    var iColAct = 0;
    for(i=0; i<aResUso.length; i++){
      cGPD = "";
      if(aResUso[i][24] && aResUso[i][24] == 1 && i<aResUso.length){
        iRengAct++;
        theRow = theBodie.insertRow(iRengAct-1);
        iColAct = 0;
      }else
        iColAct++;
      theCell = theRow.insertCell(iColAct);
      theCell.className = "ECampoL";
      theCell.vAlign = "top";

      if(aResUso[i][13] != 10){
        InicioTabla("",0,"","","left","",0,0,"");
        ITRTD("EEtiqueta",0,"","","","");
        if(aResUso[i][14] == 1)
          cGPD += "* ";

        // Textos en Area de Texto
        if(aResUso[i][13] == 1){
          cGPD += (aResUso[i][10]!="")?aResUso[i][10] + ":":"";
          FITD("ECampo",0,"","","","");
          AreaTexto(((aResUso[i][14]==1)?true:false),aResUso[i][17],3,aResUso[i][18],"","Proporcione el siguiente dato: " + aResUso[i][11],"","fMayus(this);","",false,true);
          FITD("EEtiquetaL",0,"","","","");
          cGPD += aResUso[i][12];
        }

        // Lógico como CheckBox
        if(aResUso[i][13] == 2){
          cGPD += (aResUso[i][10]!="")?aResUso[i][10] + ":":"";
          FITD("ECampo",0,"","","","");
          CheckBox(aResUso[i][18],"",false,"Proporcione el siguiente dato: " + aResUso[i][11],"","","",false,true);
          FITD("EEtiquetaL",0,"","","","");
          cGPD += aResUso[i][12];
        }

        // Textos en Campo de Texto Activo o Inactivo
        if(aResUso[i][13] == 3 || aResUso[i][13] == 11){
          cGPD += (aResUso[i][10]!="")?aResUso[i][10] + ":":"";
          FITD("ECampo",0,"","","","");
          var iAnchoCampo = parseInt(aResUso[i][17],10);
          iAnchoCampo += 2;
          cGPD += Text(((aResUso[i][14]==1)?true:false),aResUso[i][18],"",iAnchoCampo,aResUso[i][17],"Proporcione el siguiente dato: " + aResUso[i][11],"fMayus(this);","","",false,((aResUso[i][13] == 3)?true:false));
          FITD("EEtiquetaL",0,"","","","");
          cGPD += aResUso[i][12];
          if(aResUso[i][13] == 11)
            eval("document.forms[0]."+aResUso[i][18]+".disabled = true;");
        }

        // Lógico como Radio Button
        if(aResUso[i][13] == 7){
          cGPD += (aResUso[i][10]!="")?aResUso[i][10] + ":":"";
          FITD("ECampo",0,"","","","");
          Radio(((aResUso[i][14]==1)?true:false), aResUso[i][18], "1", true, "Proporcione el siguiente dato: " + aResUso[i][11],"",true," onClick='if(fEstadoCampos"+aResUso[0][0]+")fEstadoCampos"+aResUso[0][0]+"();';","",false);
          cGPD +="Sí";
          Radio(((aResUso[i][14]==1)?true:false), aResUso[i][18], "0", false, "Proporcione el siguiente dato: " + aResUso[i][11],"",true," onClick='if(fEstadoCampos"+aResUso[0][0]+")fEstadoCampos"+aResUso[0][0]+"();';","",false);
          cGPD +="No";
          FITD("EEtiquetaL",0,"","","","");
          cGPD += aResUso[i][12];
        }

        // Campos de tipo Radio Button
        if(aResUso[i][13] == 12){
          cGPD += (aResUso[i][10]!="")?aResUso[i][10] + ":":"";
          FITD("ECampo",0,"","","","");
          Radio(((aResUso[i][14]==1)?true:false), aResUso[i][18], aResUso[i][15], false, "Proporcione el siguiente dato: " + aResUso[i][11],"",true," onClick='if(fEstadoCampos"+aResUso[0][0]+")fEstadoCampos"+aResUso[0][0]+"();';","",false);
          cGPD +=aResUso[i][12];
        }

        // Liga
//        if(aResUso[i][13] == 12 || aResUso[i][13] == 11){
//          Liga(aResUso[i][11], aResUso[i][18]+";", "Oprima esta liga para " + aResUso[i][11]);
//        }

        FTDTR();
        FinTabla();
        theCell.innerHTML = cGPD;
        if(aResUso[i][13] == 11)
          eval("document.forms[0]."+aResUso[i][18]+".disabled = true;");
      }

      // Despliegue para Etiquetas de Grupo de Datos
      if(aResUso[i][13] == 10){
        iRengAct++;
        theCell.colSpan = iColumnas;
        theCell.className = "ESTitulo";
        theCell.innerHTML = aResUso[i][11];
        iRengAct--;
      }

      // Despliegue para Notas
      if(aResUso[i][13] == 13){
        iRengAct++;
        theCell.colSpan = iColumnas;
        theCell.className = "EEtiquetaL";
        theCell.innerHTML = aResUso[i][11];
        iRengAct--;
      }

      // Amplia ultima columna de acuerdo al máximo número de columnas registradas
      if(aResUso && aResUso[i] && aResUso[i+1]){
        if(iColAct < iColumnas && aResUso[i][23] != aResUso[i+1][23])
          theCell.colSpan = (iColumnas - iColAct);
      }else if(i == (aResUso.length-1) && iColAct < iColumnas)
        theCell.colSpan = (iColumnas - iColAct);
    }
    if(iRengAct > 1)
      theBodie.deleteRow(iRengAct);
  }else
    theCell.innerHTML = "FORMATO NO CONFIGURADO";
    eval("if(fEstadoCampos"+aResUso[0][0]+" != null)fEstadoCampos"+aResUso[0][0]+"();");
}

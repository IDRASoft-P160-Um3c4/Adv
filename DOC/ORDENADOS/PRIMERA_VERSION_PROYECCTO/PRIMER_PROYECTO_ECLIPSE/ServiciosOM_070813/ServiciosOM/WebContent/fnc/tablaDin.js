// MetaCD=1.0
// Title: pg111020012.js
// Description:
// Company: INFOTEC
// Author: TGC<dd>Tip General de Computo

var aResT = new Array();
var FRMCtrl;
var cCual="";
var aAlinea= new Array;
aAlinea[0]="";
var aCual = new Array;
var iCampo=null;
var iFac=null;
var iVar=null;
var iInner="";
var cInnerMostrar = "";



var aConjunto = new Array();
var nR;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg114020072.js";
  if(top.fGetTituloPagina){
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina("ffffff");
  InicioTabla("",0,"100%","","","","1");
    ITRTD("",0,"95%","0","center","top");
      InicioTabla("",0,"100%","","center");
        ITRTD("EEtiquetaL",0,"100%","100%","center","top");
        DinTabla("TRequisit","EEtiquetaC",0,'100%','','','','','.1','.1');
        FTDTR();
      FinTabla();
    ITRTD("",0,"95%","0","center","top");
    FTDTR();
  FinTabla();
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];
  theTable = (document.all) ? document.all.TRequisit:document.getElementById("TRequisit");
  tCpoRequi = theTable.tBodies[0];
  //fArregloPrueba();
}
/**
 * Dentro de esta funcion se define si uno de los campos representa una liga directa
 */
function fSetInner(iCol,cMostrar){
  iInner = iCol;
  cInnerMostrar = cMostrar;
}
/**
 * Esta funcion es la encargada de definir cuales son los campos en el arreglo que se van a desplegar.
 * Los valores contenidos en el parametro de entrada deben de ser nuemericos separados por comas.
 */
function fSetCampos(cCuales){
  cCual=cCuales;
  aCual = cCual.split(",");
}
/**
 * Esta funcion es la encargada de definir si las columnas de la tabla estarán a la izquierda, derecha o centro.
 * Recibe como parámetro (center,left,right)
 */
function fSetAlinea(cAlinea){
  aAlinea = cAlinea.split(",");
}
function fSetDisabled(lDis,lSelReg){
   lDisable = lDis;
   frm=document.forms[0];
   for(qwe=0;qwe<frm.elements.length;qwe++){
     obj=frm.elements[qwe];
     if (obj.type == 'checkbox' ||  obj.type == 'radio' || obj.type == 'text')
        obj.disabled=lDis;
   }
}
/**
 * Esta funcion es la encargada de colocarle tos titulos a cada columna en la tabla.
 * Los valores que debe de contener el parámetro de entrada deben de estar separados por comas.
 */
function fSetTitulo(cTitulos){
  var aTitulos = new Array();
  if(cTitulos!=""){
    aTitulos=cTitulos.split(",");
    fInsRow(0);
    for(i=0;i<aCual.length;i++){
      fInsCell(i,0,aTitulos[i],3,"center");
     /* if(aAlinea!="")
        fInsCell(i,0,aTitulos[i],3,aAlinea[i]);
      else fInsCell(i,0,aTitulos[i],3,"left");*/
    }
  }
  else{
    fAlert("No se introducieron los titulos de la tabla");
    return false;
  }
  return true;
}
/**
 * Se indica cuales son las columnas en el arreglo que tienen el tipo de campo, el Factor, y el nombre de la variable.
 */
function fSetFormulario(iCampTipo,iFactor,iVariable,iValor){
  iCampo=iCampTipo;
  iFac=iFactor;
  iVar=iVariable;
  iVal=iValor;
}
/**
 * Esta funcion se encarga de indicar si un campo es de tipo capturable.
 * Hay que indicarle el tipo de campo que se agrega
 * los parametros que recibe son:
 * TipoCampo: caja,list,area.
 * fac: En el caso de caja y area nos determina el tamaño de datos a capturar,
 * en el caso de una lista hay que agrupar los renglones separados por - y valor y descripcion separado por comas
 * de la siguiente manera: 1,desc1/2,desc2/3,desc3/n,etc
 * Var: Nombre de la variable en la cual se va a capturar el valor.
 */
function fFormulario(TipoCampo,Fac,Var){
  Var = Var.trim().toUpperCase();
  var cVar = "";
  if(TipoCampo=="caja"){
    cVar = ITR()+TDEtiCampo(false,'EEtiqueta',0,Var+":",Var,"",50,50,"Valor que se almacenara en la variable: "+Var,"fMayus(this);")+FTR();
  }
  else if(TipoCampo=="list"){
    cVar=ITR()+TDEtiSelect(true,"EEtiqueta",0,Var+":",Var,"","",0)+FTR();
  }
  else ITR(); FTR();
  return cVar;
}
function fLlenaCombos(TipoCampo,Fac,Var){
  var cVar = "";
  if(TipoCampo=="list"){
    var aLlena=Fac.split("/");
    for(iL=0;iL<aLlena.length;iL++){
      aLlena[iL]=aLlena[iL].split(":");
    }
    if(frm.elements[Var]){
      fFillSelect(frm.elements[Var],aLlena,true,0,0,1);
    }
  }
}
function fArregloPrueba(){
  aConjunto[0]=["1","1","1","a","caja",15,"cCaja1",""];
  aConjunto[1]=["1","1","2","b","caja",15,"cCaja2",""];
  aConjunto[2]=["1","1","3","c","list",'1:campo1/2:campo2/3:campo3',"cCaja3",""];
  aConjunto[3]=["1","1","4","d","caja",55,"cCaja4",""];
  aConjunto[4]=["1","2","1","e","caja",15,"cCaja5",""];
  aConjunto[5]=["1","2","2","f","caja",15,"cCaja6",""];
  aConjunto[6]=["1","2","3","g","list",'1:text1/2:text2',"cCaja7",""];
  aConjunto[7]=["1","2","4","h","caja",15,"cCaja8",""];
  aConjunto[8]=["1","2","5","i","caja",5,"cCaja9",""];
  aConjunto[9]=["1","3","1","j","caja",15,"cCaja10",""];
  aConjunto[10]=["1","3","2","k","caja",30,"cCaja11",""];
  aConjunto[11]=["1","3","3","l","caja",15,"cCaja12",""];
  aConjunto[12]=["1","3","4","m","caja",15,"cCaja13",""];
  fSetInner(7,"Google");
  fSetFormulario(4,5,6);
  fSetAlinea("left,center,center,right,center,center");
  fSetCampos("0,1,2,3,6,7");
  fSetTitulo("Titulo1,Titulo 2,Titulo 3,Titulo 4,Campo,Liga");
  fShow(aConjunto);
}
function fSetControl(oControlM){
   FRMCtrl = oControlM;
}
/**
 * En esta funcion se despliega en la tabla el listado proporcionado.
 * Previo a esta funcion se deben de proporcionar las siguientes funciones (fSetAlinea,fSetCampos,fSetTitulos)
 */
function fShow(aRes){
  for(i=0;(tCpoRequi.rows.length-1);i++){
    tCpoRequi.deleteRow(1);
  }
  frm = document.forms[0];
  aResT = fCopiaArregloBidim(aRes);
  if(aRes.length==0) {
    fInsRow(1);
    var cCad = "no hay registros";
    fInsCell(0,4,cCad,3);
    return false;
  }
  else{
    for(iRen=0;iRen<aResT.length;iRen++){
      var cMostrar="";
      fInsRow(1+iRen);
      for(jCol=0;jCol<aCual.length;jCol++){
        if(aCual[jCol]==iVar)
          cMostrar = (fFormulario(aResT[iRen][iCampo],aResT[iRen][iFac],aResT[iRen][iVar]));
        else if(aCual[jCol]==iInner){
          cMostrar = Liga(cInnerMostrar,aResT[iRen][aCual[jCol]]);
        }
        else
          cMostrar = Liga(aResT[iRen][aCual[jCol]],"fSelReg1("+iRen+","+jCol+");",aResT[iRen][aCual[jCol]]);
        fInsCell(jCol,0,cMostrar,4,aAlinea[jCol]);
        frm = document.forms[0];
        if(aCual[jCol]  && aCual[jCol]==iVar) frm.elements[aResT[iRen][iVar]].value = aResT[iRen][iVal];
        if(jCol==iCampo)fLlenaCombos(aResT[iRen][iCampo],aResT[iRen][iFac],aResT[iRen][iVar]);

      }
    }
  }
}

function fInsCell(iC, iCol, cVal, cColor,cAlinea){
   //deleteRow( 0);
    nC = nR.insertCell(iC);
    nC.innerHTML = cVal;
    nC.style.borderColor = "black";
    nC.style.borderWidth = "1px";
    if(cAlinea=="center") nC.align="center";
    else if(cAlinea=="right") nC.align="right";
    else nC.align = "left";
    if(iCol!=0){
      nC.colSpan = iCol;
    }
    if(cColor == 1){
      nC.bgColor = "#6BA6D6";
      nC.className = "ETablaST";
    }
    if(cColor == 2){
    	nC.bgColor = "#FFFFFF";
      //nC.className = "EEtiquetaLST";
    }
    if(cColor == 3){
      //nC.bgColor = "#F4F703";
      nC.className ="ETablaSTL";
    }
    if(cColor == 4){
    	nC.bgColor = "#BDD3E8";
      //nC.className = "EEtiquetaLST1"
    }
    if(cColor == 5){
      nC.bgColor = "#E0E0E0";
    }
	if(cColor == 6){
	   nC.bgColor = "#8A2BE2";
	}
}

function fInsRow(iR){
  nR = tCpoRequi.insertRow(iR);
}

function fSelReg1(iRen,iCol){
  alert("Arreglo: "+aResT[iRen]+
        "\nRenglon: "+iRen+
        "\nColumna: "+iCol+
        "\nValor Obj: "+aResT[iRen][iVar]+" --> "+frm.elements[aResT[iRen][iVar]].value);
  if(FRMCtrl)
    FRMCtrl.fSelRegT(aResT[iRen],iRen,iCol);
}
function fGetARes(){
  return aResT;
}

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}

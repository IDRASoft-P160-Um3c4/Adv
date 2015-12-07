// MetaCD=1.0
// Title: pgCalificaRequisito.js
// Description:
// Company: Tecnología InRed S.A. de C.V.
// Author: Rafael Miranda Blumenkron
var cTitulo = "";
var FRMListado="", FRMFiltro="";
var frm;
var aResultado;
var aCalifActual;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pgCalificaRequisito.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
  fSetWindowTitle();
}

// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("EEtiquetaC",0,"100%","20","center");
      InicioTabla("",0,"","","center");
        ITR();
          ITD("EEtiqueta",0,"0","","center","middle");
            TextoSimple("Requisito:");
          FTD();
          ITD("EEtiquetaL",0,"0","","center","middle");
            Text(false,"iCveRequisito","",4,4,"Requisito a validar","","","",true,false);
            Text(false,"cDscBreve","",100,100,"Requisito a validar","","","",true,false);
          FTD();
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("EEtiquetaC",0,"95%","0","center");
      InicioTabla("",0,"95%","","center");
        ITRTD("",0,"","0","center","top");
          IFrame("IFiltro12","0","0","Filtros.js");
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"95%","0","center","top");
      InicioTabla("",0,"95%","","center");
        ITRTD("ETablaST",0,"100%","","center");
          TextoSimple("Caracteríticas NO Cumplidas");
        FTDTR();

        ITRTD("",0,"100%","","center","middle");
          IFrame("IListado12","100%","230","Listado.js","yes",true);
        FTDTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","1","center");
      InicioTabla("ETablaInfo",0,"95%","","","",1);
        ITRTD("ETablaST",2,"","","center");
          TextoSimple("Detalle de Características");
        FTDTR();
        ITR();
          TDEtiAreaTexto(false,"EEtiqueta",0,"Descripción:",110,3,"cDscCaracteristica","","Característica","fMayus(this);","","",false,false,false,"EEtiquetaL",0);
        FTR();
      FinTabla();
    FTDTR();

    ITRTD("",0,"","","center","center");
      BtnImg("vgcerrar","aceptar","fRegresaDatos();","");
    FTDTR();
    Hidden("lVigente","");
    Hidden("lEnRecepcion","");
    Hidden("lEnProceso","");
    Hidden("lMandatorio","");
  FinTabla();
  fFinPagina();
}

// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
  frm = document.forms[0];

  FRMFiltro = fBuscaFrame("IFiltro12");
  FRMFiltro.fSetControl(self);
  FRMFiltro.fShow(",");

  FRMListado = fBuscaFrame("IListado12");
  FRMListado.fSetControl(self);
  FRMListado.fSetTitulo(",Caracteristica,Obligatoria,");
  FRMListado.fSetDespliega("texto,texto,logico,");
  FRMListado.fSetCampos("3,7,");
  FRMListado.fSetObjs(0,"Caja");
  FRMListado.fSetAlinea("center,left,center,");

  fDisabled(true,"");
  if (top.opener){
    if (top.opener.fEnviaReqCalificar)
      top.opener.fEnviaReqCalificar(frm);
    if (top.opener.fValoresActuales){
      aCalifActual = top.opener.fValoresActuales();
    }
  }
  if (frm.iCveRequisito.value != ""){
    frm.hdFiltro.value = " iCveRequisito = " + frm.iCveRequisito.value + " ";
    if (frm.lVigente.value != "")
      frm.hdFiltro.value += " and lVigente = " + frm.lVigente.value + " ";
    if (frm.lEnRecepcion.value != "")
      frm.hdFiltro.value += " and lEnRecepcion = " + frm.lEnRecepcion.value + " ";
    if (frm.lEnProceso.value != "")
      frm.hdFiltro.value += " and lEnProceso = " + frm.lEnProceso.value + " ";
    if (frm.lMandatorio.value != "")
      frm.hdFiltro.value += " and lMandatorio = " + frm.lMandatorio.value + " ";
    frm.hdOrden.value = " lMandatorio DESC, cDscBreve ";
    fEngSubmite("pgTRACaracXRequisito.jsp","Listado");
  }else{
    fAlert("No se proporcionó requisito a validar");
    window.close();
  }
}

// LLAMADO al JSP específico para la navegación de la página
function fNavega(){
  frm.hdNumReg.value =  10000000;
  FRMListado.fSetDisabled(false);
}

// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  aResultado = aRes;
  if(cError!=""){
    fAlert(cError);
    FRMFiltro.fSetNavStatus("Record");
    return;
  }

  if(cId == "Listado" && cError==""){
    frm.hdRowPag.value = iRowPag;

    FRMListado.fSetListado(aRes);
    FRMListado.fSetLlave(cLlave);
    FRMListado.fShow();

    aDatos = new Array();
    for (var i=0; i<aCalifActual.length; i++)
      aDatos[i] = aCalifActual[i][1];
    FRMListado.fSetDefaultValues(0, 0, aDatos);
  }
}

function fSelReg(aDato,iCol,lChecked){
  frm.cDscCaracteristica.value = aDato[2];
}

function fRegresaDatos(){
  var aDatos = new Array();
  var aCalificado = FRMListado.fGetObjs(0);
  for (var i=0; i<aResultado.length; i++){
    aDatos[i] = new Array();
    aDatos[i][0] = aResultado[i][1];
    aDatos[i][1] = aCalificado[i];
    /**LEL060906 se agrega columna de requerido*/
    aDatos[i][2] = aResultado[i][7];
    /*FinELEL060906*/
  }

  if (top.opener){
    if(top.opener.lRequisitoComp)
      for(var j= 0; j<aCalificado.length; j++)
        if(aCalificado[j])
          if(aResultado[j][7] == 1)
            if(top.opener.iReqObligatorio == 1 || top.opener.iReqObligatorio == "1"){
              top.opener.lRequisitoComp = false;
              break;
            }

    if (top.opener.fRecibeCalifica){
      if(top.opener.frm.wCaracteristicas){
//         alert("Encuentra wCaracteristica");
//         top.close();
         frm.hdBoton.value = "primero";
         top.opener.fRecibeCalifica(frm.iCveRequisito.value, aDatos, window);
      }else{
         top.opener.fRecibeCalifica(frm.iCveRequisito.value, aDatos);
         top.close();
      }
    }
  }
}

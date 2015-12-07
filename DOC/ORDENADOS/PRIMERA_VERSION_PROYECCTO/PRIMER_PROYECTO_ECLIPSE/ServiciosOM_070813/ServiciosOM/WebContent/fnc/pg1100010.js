// MetaCD=1.0
 // Title: pg1100010.js
 // Description: JS Actualizaci�n de Propiedades de tabla SEGPropiedad
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: Rafael Miranda Blumenkron
 var cTitulo = "";
 var frm;

function fBefLoad(){
  cPaginaWebJS = "pg1100010.js";
  if(top.fGetTituloPagina)
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
}

function fDefPag(){
  cEstiloDesc = "";
  fInicioPagina(cColorGenJS);
  InicioTabla("",0,"100%","","","","1");
    ITRTD("ESTitulo",0,"100%","","center");
      TextoSimple(cTitulo);
    FTDTR();

    ITRTD("",0,"","","center","center");
      InicioTabla("ETablaInfo",0,"90%","100%","center","",1);
        ITRTD("EEtiquetaC");
          Liga("Actualizar Propiedades Generales","fActualizaProp();","Actualizar Propiedades Generales");
          FITD(cEstiloDesc);
            TextoSimple('Esta opci�n actualizar� los par�metros de configuraci�n del sistema.');
        FTDTR();

        ITRTD("EEtiquetaC");
          Liga("Actualizar Pa�ses","fActualizaPais();","Actualizar Pa�ses");
          FITD(cEstiloDesc);
            TextoSimple('Esta opci�n actualizar� los pa�ses del sistema de seguridad a este sistema.');
        FTDTR();

        ITRTD("EEtiquetaC");
          Liga("Actualizar Usuarios","fActualizaUsuarios();","Actualizar Pa�ses");
          FITD(cEstiloDesc);
            TextoSimple('Esta opci�n actualizar� los usuarios del sistema de seguridad a este sistema.');
        FTDTR();
      FinTabla();
    FTDTR();

  FinTabla();
  fFinPagina();
}

function fOnLoad(){
  frm = document.forms[0];
}

function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
  if(cError!=""){
    fAlert(cError);
    return false;
  }else{
    if(cId == "idActualizaProp"){
      fAlert('\n - Las propiedades se actualizaron exitosamente.\nFavor de salir del sistema y reingresar para que apliquen de forma correcta.');
    }
    if(cId == "cIdUsr"){
      fAlert('\n - Los usuarios se actualizaron exitosamente.\nFavor de salir del sistema y reingresar para que apliquen de forma correcta.');
    }
  }
}

function fActualizaProp(){
  frm.hdBoton.value = "ActualizaProp";
  fEngSubmite("pgActualizaProp.jsp","idActualizaProp");
}

function fActualizaPais(){
  frm.hdBoton.value = "Actualiza";
  fEngSubmite("pgActualizaPaises","idPaises");
}
function fActualizaUsuarios(){
  frm.hdFiltro.value = "";
  frm.hdOrden.value =  "";
  frm.hdNumReg.value =  100000;
  frm.hdBoton.value = "Actualiza";
  fEngSubmite("pgActualizaUsr.jsp","cIdUsr");
}

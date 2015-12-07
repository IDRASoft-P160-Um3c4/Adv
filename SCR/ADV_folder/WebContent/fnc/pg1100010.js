// MetaCD=1.0
 // Title: pg1100010.js
 // Description: JS Actualización de Propiedades de tabla SEGPropiedad
 // Company: Tecnología InRed S.A. de C.V.
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
            TextoSimple('Esta opción actualizará los parámetros de configuración del sistema.');
        FTDTR();

        ITRTD("EEtiquetaC");
          Liga("Actualizar Países","fActualizaPais();","Actualizar Países");
          FITD(cEstiloDesc);
            TextoSimple('Esta opción actualizará los países del sistema de seguridad a este sistema.');
        FTDTR();

        ITRTD("EEtiquetaC");
          Liga("Actualizar Usuarios","fActualizaUsuarios();","Actualizar Países");
          FITD(cEstiloDesc);
            TextoSimple('Esta opción actualizará los usuarios del sistema de seguridad a este sistema.');
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

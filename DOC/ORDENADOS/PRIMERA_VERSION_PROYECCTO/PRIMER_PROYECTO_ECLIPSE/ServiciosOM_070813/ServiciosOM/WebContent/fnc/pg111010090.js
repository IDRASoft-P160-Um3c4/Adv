// MetaCD=1.0
// Title: pg111010090.js
// Description: JS "Catálogo" de la entidad INSDocTecnico
// Company: Tecnología InRed S.A. de C.V.
// Author: Leopoldo Beristain González
var frm;
var iPagAnt = 1;
fWrite(JSSource("Carpetas.js"));

function fBefLoad(){
  cPaginaWebJS = "pg111010090.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}

// Define la página a ser mostrada
function fDefPag(){ // Define la página a ser mostrada
  fInicioPagina(cColorGenJSFolder);
  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("EFolderMSG",0,"100%","20","center");
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       fDefCarpeta("Registro de Formatos|Atributos de Formatos|Atributos Tipo Catálogo|Configuración de Búsquedas|Tipo de Respuesta|" ,
                   "pg111010091.js|pg111010092.js|pg111010093.js|pg111010094.js|pg111010095.js|" ,
                   "FORMAT" , "99%" , "99%",true);
    FTDTR();
  FinTabla();
  fFinPagina();
}

// Carga información al mostrar la página.
function fOnLoad(){
  frm = document.forms[0];
  fPagFolder(1);
}

// Recibe el resultado en el Vector aRes.
function fResultado(aRes, cId){
}

// iPag indica a la página que se desea cambiar
function fFolderOnChange( iPag ) {
  var cMensaje = "!Debe Seleccionar un Formato!";

  // Pagina Inicial
  if(iPag == 1)
    iPagAnt = 1;

  // Si la Pagina es distinta de la Primera
  if( iPag > 1){
    // Segunda Pestaña Atributos
    if( iPag == 2){
      FRMAtributo = fBuscaFrame("FORMAT2");

      if(iPagAnt == 1){
        FRMFormato = fBuscaFrame("FORMAT1");
        if(FRMFormato.fGetCveFormato()!="" && FRMFormato.fGetTitulo()!=""){
          FRMAtributo.fSetVarIni(FRMFormato.fGetCveFormato(),FRMFormato.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }


      if(iPagAnt == 4){
        FRMBusqueda = fBuscaFrame("FORMAT4");
        if(FRMBusqueda.fGetCveFormatoAux()!="" && FRMBusqueda.fGetTitulo()!=""){
          FRMAtributo.fSetVarIni(FRMBusqueda.fGetCveFormatoAux(),FRMBusqueda.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }

     if(iPagAnt == 5){
        FRMTipoRespuesta = fBuscaFrame("FORMAT5");
        if(FRMTipoRespuesta.fGetCveFormatoAux()!="" && FRMTipoRespuesta.fGetTituloAux()!=""){
          FRMAtributo.fSetVarIni(FRMTipoRespuesta.fGetCveFormatoAux(),FRMTipoRespuesta.fGetTituloAux());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }
      iPagAnt = 2;
    }

    // Tercera Pestaña Atributos por Catalogo
    if( iPag == 3 && iPagAnt == 2){
      FRMAtributo = fBuscaFrame("FORMAT2");
      FRMCatalago = fBuscaFrame("FORMAT3");
      if(FRMAtributo.fGetTipoRespuestaValida()==true){
        if(FRMAtributo.fGetCveAtributo()!="") {
          FRMCatalago.fSetVarIni(FRMAtributo.fGetCveFormatoAux(),FRMAtributo.fGetTitulo(),FRMAtributo.fGetCveAtributo(),FRMAtributo.fGetDscAtributo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n ¡Debe Seleccionar un Atributo asociado a un Formato!");
          return false;
        }
      }
      else{
        fAlert("\n ¡Este Tipo de Respuesta no es Válida!");
        return false;
      }

    }



    // Cuarta Pestaña
    if( iPag == 4){
      FRMBusqueda = fBuscaFrame("FORMAT4");

      if(iPagAnt == 1){
        FRMFormato = fBuscaFrame("FORMAT1");
        if(FRMFormato.fGetCveFormato()!="" && FRMFormato.fGetTitulo()!=""){
          FRMBusqueda.fSetVarIni(FRMFormato.fGetCveFormato(),FRMFormato.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }

      if(iPagAnt == 2 ){
        FRMAtributo = fBuscaFrame("FORMAT2");
        if(FRMAtributo.fGetCveFormatoAux()!="" && FRMAtributo.fGetTitulo()!=""){
          FRMBusqueda.fSetVarIni(FRMAtributo.fGetCveFormatoAux(),FRMAtributo.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }

      if(iPagAnt == 3 ){
        FRMCatalago = fBuscaFrame("FORMAT3");
        if(FRMCatalago.fGetCveFormato()!="" && FRMCatalago.fGetTitulo()!=""){
          FRMBusqueda.fSetVarIni(FRMCatalago.fGetCveFormato(),FRMCatalago.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }

     if(iPagAnt == 5){
        FRMTipoRespuesta = fBuscaFrame("FORMAT5");
        if(FRMTipoRespuesta.fGetCveFormatoAux()!="" && FRMTipoRespuesta.fGetTituloAux()!=""){
          FRMBusqueda.fSetVarIni(FRMTipoRespuesta.fGetCveFormatoAux(),FRMTipoRespuesta.fGetTituloAux());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }
    }

    // Quinta Pestaña
    if( iPag == 5){
      FRMTipoRespuesta = fBuscaFrame("FORMAT5");

      if(iPagAnt == 1){
        FRMFormato = fBuscaFrame("FORMAT1");
          FRMTipoRespuesta.fSetVarIni(FRMFormato.fGetCveFormato(),FRMFormato.fGetTitulo());
          iPagAnt = iPag;
      }

      if(iPagAnt == 2){
        FRMAtributo = fBuscaFrame("FORMAT2");
        if(FRMAtributo.fGetCveFormatoAux()!="" && FRMAtributo.fGetTitulo()!=""){
          FRMTipoRespuesta.fSetVarIni(FRMAtributo.fGetCveFormatoAux(),FRMAtributo.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }

      if(iPagAnt == 3){
        FRMCatalago = fBuscaFrame("FORMAT3");
        if(FRMCatalago.fGetCveFormato()!="" && FRMCatalago.fGetTitulo()!=""){
          FRMTipoRespuesta.fSetVarIni(FRMCatalago.fGetCveFormato(),FRMCatalago.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }

      if(iPagAnt == 4){
        FRMBusqueda = fBuscaFrame("FORMAT4");
        if(FRMBusqueda.fGetCveFormatoAux()!="" && FRMBusqueda.fGetTitulo()!=""){
          FRMTipoRespuesta.fSetVarIni(FRMBusqueda.fGetCveFormatoAux(),FRMBusqueda.fGetTitulo());
          iPagAnt = iPag;
        }
        else{
          fAlert("\n "+ cMensaje);
          return false;
        }
      }
    }

    // Violación de Integridad
    if( iPag == 2 && iPagAnt == 4) {
        fAlert("\n "+ cMensaje);
      return false;
    }


    // Violación de Integridad
    if( iPag == 3 && (iPagAnt == 1 || iPagAnt == 4 || iPagAnt == 5)) {
      fAlert("\n ¡Debe Seleccionar un Atributo asociado a un Formato!");
      return false;
    }

    // Pagina Final
    if(iPag == 5)
      iPagAnt = 5;
  }  // if Pestaña Mayor que 1
}

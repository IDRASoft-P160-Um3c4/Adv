// MetaCD=1.0
var frm;
var iPagAnt = 1;
//Para Idenficar si se tiene que mostrar las pestañas siguientes.
var lShowPersona       = true;
var iCveVehiculoFiltro = "";
var lPropioSCTFiltro   = "";
var iCveTipoVehFiltro  = "";
var cMatriculaFiltro   = "";
var cCvPaisFiltro      = "";

function fSetParValores(iCveVehiculo,lPropioSCT,iCveTipoVeh,cMatricula,lCvPais){

  iCveVehiculoFiltro  = iCveVehiculo;
 lPropioSCTFiltro    = lPropioSCT;
 iCveTipoVehFiltro   = iCveTipoVeh;
 cMatriculaFiltro    = cMatricula;
 cCvPaisFiltro       = lCvPais;

}

fWrite(JSSource("Carpetas.js"));
function fBefLoad(){
  cPaginaWebJS = "pg110030050.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}


function fDefPag(){
  fInicioPagina(cColorGenJSFolder);

  //Parametros de Entrada para el Filtro de la Pagina.
  Hidden("iCveVehiculoFiltro",""); // Vacio -- No Aplica, No Vacio -- el Vehiculo que tenga esa Clave.
  Hidden("lPropioSCTFiltro","");   // Vacio -- Todos, 1 -- Propios de la SCT, 0 -- Particulares.
  Hidden("iCveTipoVehFiltro","");  // Vacio -- Todos, 1 -- Embarcaciones y Artefactos Navales, 2 -- Vehiulos Terrestres.
  Hidden("cMatriculaFiltro","");   // Vacio -- Todos, No Vacio -- el Vehiculo que tenga esa Matricula.
  Hidden("cCvPaisFiltro","");      // 1 -- Nacional(incluye a México), 0 -- Extranjero.

  if(top.opener){

    if (top.opener.fGetParametros)
      top.opener.fGetParametros(this);
  }

  InicioTabla("",0,"100%","100%");
    ITRTD("ESTitulo",0,"100%","20","center");
      TextoSimple(cTitulo);
    FTDTR();
    ITRTD("",0,"100%","100%","center","middle");
       var cCadTitulos = "Consulta de Embarcaciones|Consulta de historicos|", cCadPaginas = "pg110030050A.js|pg110030043A.js|";
          
       
       fDefCarpeta(cCadTitulos,cCadPaginas,"PEM","99%","99%",false );
       //fDefCarpeta("Datos|Motor|Personas|Embarcacion|Senal Dist|",
       //            "pg110030041.js|pg110030042.js|pg110030042A.js|pg110030043.js|pg110030045.js|",
       //            "PEM" , "99%" , "99%", false);
       
      // pg110030040
    FTDTR();
    ITRTD("EEtiquetaC",0,"100%","","center","");
      InicioTabla("",0,"100%","","center");
        ITRTD("",0,"","","center","center");
          if(top.opener)BtnImg("vgcerrar","aceptar","fRegresaDatos();","");
        FTDTR();
      FinTabla(); // Botón Aceptar
    FTDTR();
  FinTabla();



  Hidden("iCveVehiculo","");
  //Parametros de Salida de Vehiculos Artefactos Navales y Marítimos.
  Hidden("cNomEmbarcacion","");
  Hidden("iCveTipoServ","");
  Hidden("cDscTipoServ","");
  Hidden("cDscTipoEmbarcacion","");
  Hidden("iCveTipoNavega","");
  Hidden("cDscTipoNavega","");
  Hidden("dArqueoBruto","");
  Hidden("cDscUnidadMedidaArqueoBruto","");
  Hidden("dArqueoNeto","");
  Hidden("cDscUnidadMedidaArqueoNeto","");
  Hidden("dPesoMuerto","");
  Hidden("cDscUnidadMedidaPesoMuerto","");
  Hidden("dEslora","");
  Hidden("dManga","");
  Hidden("cDscUnidadMedidaManga","");
  Hidden("dPuntal","");
  Hidden("cDscUnidadMedidaPuntal","");
  Hidden("iCvePropietario","");
  Hidden("cNomRazonSocial","");
  Hidden("lArtefacto","");


  //Parametros de Salida de Vehiculos Terrestres.
  Hidden("cMatricula","");
  Hidden("iCveMarcaMotor","");
  Hidden("cDscMarcaMotor","");
  Hidden("cNumSerie","");
  fFinPagina();
}

function fOnLoad(){ // Carga información al mostrar la página.
  frm = document.forms[0];

  frm.iCveVehiculoFiltro.value= iCveVehiculoFiltro;
  frm.lPropioSCTFiltro.value  = lPropioSCTFiltro;
  frm.iCveTipoVehFiltro.value = iCveTipoVehFiltro;
  frm.cMatriculaFiltro.value  = cMatriculaFiltro;
  frm.cCvPaisFiltro.value     = cCvPaisFiltro;


  fPagFolder(1);
}

function fResultado(aRes, cId){ // Recibe el resultado en el Vector aRes.
}

function fFolderOnChange(iPag) {
	if (iPag >= 1) {
		FRMDatos = fBuscaFrame("PEM1");
		if (iPag == 1) {
		}
		if (iPag == 2) {
			FRMEmbarcacion = fBuscaFrame("PEM2");
			if (parseInt(FRMDatos.fGetiCveVehiculo(), 10) > 0) {
				FRMEmbarcacion.fSetiCveVehiculo(FRMDatos.fGetiCveVehiculo());
				//FRMEmbarcacion.fSetPaisAbandera(frm.cCvPaisFiltro.value);
			} else {
				alert("Debe de Seleccionar una Embarcación");
				return false;
			}
		}
	}
}

function fSetEmbarcacion(iCveVehiculo,cNomEmbarcacion,
                         cDscTipoEmbarcacion,
                         iCveTipoNavega,cDscTipoNavega,
                         dArqueoBruto,cDscUnidadMedidaArqueoBruto,
                         dArqueoNeto,cDscUnidadMedidaArqueoNeto,
                         dPesoMuerto,cDscUnidadMedidaPesoMuerto,
                         dEslora,
                         dManga,cDscUnidadMedidaManga,
                         dPuntal,cDscUnidadMedidaPuntal,
                         iCveTipoServ,cDscTipoServ,lArtefacto){
  frm.iCveVehiculo.value = iCveVehiculo;
  frm.cNomEmbarcacion.value = cNomEmbarcacion;
  frm.cDscTipoEmbarcacion.value = cDscTipoEmbarcacion;
  frm.iCveTipoNavega.value = iCveTipoNavega;
  frm.cDscTipoNavega.value = cDscTipoNavega;
  frm.dArqueoBruto.value = dArqueoBruto;
  frm.cDscUnidadMedidaArqueoBruto.value = cDscUnidadMedidaArqueoBruto;
  frm.dArqueoNeto.value = dArqueoNeto;
  frm.cDscUnidadMedidaArqueoNeto.value = cDscUnidadMedidaArqueoNeto;
  frm.dPesoMuerto.value = dPesoMuerto;
  frm.cDscUnidadMedidaPesoMuerto.value = cDscUnidadMedidaPesoMuerto;
  frm.dEslora.value = dEslora;
  frm.dManga.value = dManga;
  frm.cDscUnidadMedidaManga.value = cDscUnidadMedidaManga;
  frm.dPuntal.value = dPuntal;
  frm.cDscUnidadMedidaPuntal.value = cDscUnidadMedidaPuntal;
  frm.iCveTipoServ.value = iCveTipoServ;
  frm.cDscTipoServ.value = cDscTipoServ;
  frm.lArtefacto.value = lArtefacto;
}

function fSetPropietario(iCvePropietario,cNomRazonSocial){
  frm.iCvePropietario.value = iCvePropietario;
  frm.cNomRazonSocial.value = cNomRazonSocial;
}

function fSetMotor(iCveMarcaMotor,cDscMarcaMotor,cNumSerie){
  frm.iCveMarcaMotor.value = iCveMarcaMotor;
  frm.cDscMarcaMotor.value = cDscMarcaMotor;
  frm.cNumSerie.value = cNumSerie;
}

function fSetVehiculo(iCveVehiculo,cMatricula){
  frm.iCveVehiculo.value = iCveVehiculo;
  frm.cMatricula.value = cMatricula;
}


function fGetEmbarcacion(){
  if (top.opener)
    if(top.opener.fValoresEmbarcacion){

      top.opener.fValoresEmbarcacion(frm.iCveVehiculo.value,
                                     frm.cNomEmbarcacion.value,
                                     frm.cDscTipoEmbarcacion.value,
                                     frm.iCveTipoNavega.value,
                                     frm.cDscTipoNavega.value,
                                     frm.dArqueoBruto.value,
                                     frm.cDscUnidadMedidaArqueoBruto.value,
                                     frm.dArqueoNeto.value,
                                     frm.cDscUnidadMedidaArqueoNeto.value,
                                     frm.dPesoMuerto.value,
                                     frm.cDscUnidadMedidaPesoMuerto.value,
                                     frm.dEslora.value,
                                     frm.dManga.value,
                                     frm.cDscUnidadMedidaManga.value,
                                     frm.dPuntal.value,
                                     frm.cDscUnidadMedidaPuntal.value,
                                     frm.iCveTipoServ.value,
                                     frm.cDscTipoServ.value,
                                     frm.lArtefacto.value);
    }
}

function fGetPropietario(){
  if (top.opener)
    if(top.opener.fValoresPropietario()){
      top.opener.fValoresPropietario(frm.iCvePropietario.value,
                                     frm.cNomRazonSocial.value);
    }
}

function fGetMotor(){
  if (top.opener)
    if (top.opener.fValoresMotor()){
      top.opener.fValoresMotor(frm.iCveMotor.value,
                               frm.cDscMarcaMotor.value,
                               frm.cNumSerie.value);
    }
}

function fGetVehiculo(){
  if (top.opener)
    if (top.opener.fValoresVehiculo()){
      top.opener.fValoresVehiculo(frm.iCveVehiculo.value,
                                  frm.cMatricula.value);
    }
}

function fRegresaDatos(){
  FRMDatos = fBuscaFrame("PEM1");
  FRMPersonas = fBuscaFrame("PEM3");
  FRMEmbarcacion = fBuscaFrame("PEM4");
  FRMMotor = fBuscaFrame("PEM2");
  var iCveTipoVehiculo = "";

  iCveTipoVehiculo = FRMDatos.fGetiCveTipoVehiculo();

  if (iCveTipoVehiculo == 1){

    if (FRMPersonas){
      fSetPropietario(FRMPersonas.fGetiCvePropietario(),FRMPersonas.fGetcNomRazonSocial());
    }

    if (FRMEmbarcacion){
      fSetEmbarcacion(FRMEmbarcacion.fGetiCveVehiculo(),
                      FRMEmbarcacion.fGetcNomEmbarcacion(),
                      FRMEmbarcacion.fGetcDscTipoEmbarcacion(),
                      FRMEmbarcacion.fGetiCveTipoNavega(),
                      FRMEmbarcacion.fGetcDscTipoNavega(),
                      FRMEmbarcacion.fGetdArqueoBruto(),
                      FRMEmbarcacion.fGetcDscUnidadMedidaArqueoBruto(),
                      FRMEmbarcacion.fGetdArqueoNeto(),
                      FRMEmbarcacion.fGetcDscUnidadMedidaArqueoNeto(),
                      FRMEmbarcacion.fGetdPesoMuerto(),
                      FRMEmbarcacion.fGetcDscUnidadMedidaPesoMuerto(),
                      FRMEmbarcacion.fGetdEslora(),
                      FRMEmbarcacion.fGetdManga(),
                      FRMEmbarcacion.fGetcDscUnidadMedidaManga(),
                      FRMEmbarcacion.fGetdPuntal(),
                      FRMEmbarcacion.fGetcDscUnidadMedidaPuntal(),
                      FRMEmbarcacion.fGetiCveTipoServ(),
                      FRMEmbarcacion.fGetcDscTipoServ(),
                      FRMEmbarcacion.fGetlArtefacto())
    }

    if (frm.cNomEmbarcacion.value == ""){
      alert("Debe Seleccionar la Embarcación");
      return;
    }

    if (frm.iCvePropietario.value == ""){
      alert("Debe Seleccionar el Propietario del Vehiculo");
      return;
     }
  }


  if (iCveTipoVehiculo == 2){
    if (FRMDatos){
       fSetVehiculo(FRMDatos.fGetiCveVehiculo(),
                    FRMDatos.fGetcMatricula());
    }

    if (FRMMotor){
      fSetMotor(FRMMotor.fGetiCveMarcaMotor(),
                FRMMotor.fGetcDscMarcaMotor(),
                FRMMotor.fGetcNumSerie());
    }

    if (frm.cMatricula.value == ""){
      alert("Debe Seleccionar el Vehiculo");
      return;
    }

    if (frm.cDscMarcaMotor.value == ""){
      alert("Debe Seleccionar el Motor del Vehiculo");
      return;
    }
  }

  if(confirm("¿ Esta Seguro de la Seleccion del Vehiculo ? ")){
    if (iCveTipoVehiculo == 1){

      fGetEmbarcacion();
      fGetPropietario();
    }

    if (iCveTipoVehiculo == 2){
      fGetMotor();
      fGetVehiculo();
    }

    if (top.opener)
      top.close();
  }
}

function fAbreTodo(){
  FRMDatos = fBuscaFrame("PEM1");
  FRMPersonas = fBuscaFrame("PEM3");
  FRMEmbarcacion = fBuscaFrame("PEM4");

    if (FRMPersonas){
      if( parseInt(FRMDatos.fGetiCveVehiculo(),10) > 0 ) {
        FRMPersonas.fSetiCveVehiculo(FRMDatos.fGetiCveVehiculo());
        FRMPersonas.fSetiCveTipoVehiculo(FRMDatos.fGetiCveTipoVehiculo());
      }
    }

    if (FRMEmbarcacion){
      if( parseInt(FRMDatos.fGetiCveVehiculo(),10) > 0 ) {
        FRMEmbarcacion.fSetiCveVehiculo(FRMDatos.fGetiCveVehiculo());
      }
    }
}

function fAbreMotor(){
  FRMDatos = fBuscaFrame("PEM1");
  FRMMotor = fBuscaFrame("PEM2");

    if (FRMMotor){
      if( parseInt(FRMDatos.fGetiCveVehiculo(),10) > 0 ) {
        FRMMotor.fSetiCveVehiculo(FRMDatos.fGetiCveVehiculo());
        FRMMotor.fSetiCveTipoVehiculo(FRMDatos.fGetiCveTipoVehiculo());
      }
    }
}

function fGetPaisAbandera(iPais){
 iPaisAbanderamiento = iPais;

}
function fPaisAbandera(){

  }


function fGetPaisAbandera(){
  if (top.opener)
    if (top.opener.fValoresVehiculo()){
      top.opener.fValoresVehiculo(frm.iCveVehiculo.value,
                                  frm.cMatricula.value);
    }
}



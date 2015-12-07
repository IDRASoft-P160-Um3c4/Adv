// MetaCD=1.0 
// Title: pg....js
// Description: JS "Catálogo/Proceso" de la entidad MODTabla
// Company: S.C.T. 
// Author: 
var cTitulo = "";
var FRMListado = "";
var frm;
var form;
var aNuevo = new Array();
var aCampos = new Array();
var aAgregados = new Array();
// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad() {
    //Nombre de la página.
    cPaginaWebJS = "p....js";
    if (top.fGetTituloPagina) {
	;
	cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
    }
}

function fDefPag() {
    fInicioPagina(cColorGenJS);
    InicioTabla("", 0, "100%", "", "", "", "1"); 
    ITRTD("", 0, "", "40", "center", "top");
        ITD("EEtiqueta",0,"0","","center","middle");
            TextoSimple("Trámite:");
        FTD();
        ITD("EEtiquetaL",0,"0","","center","middle");
            Text(false,"cCveTramite","",7,6,"Teclee la clave interna del trámite para ubicarlo","fGetModalidad();this.value='';"," onKeyPress=\"return fReposSelectFromField(event, true, this.form.iCveTramite, this);\"","",true,true);
            Select("iCveTramite","fGetModalidad();");
        FTD();
    FITR();
    	ITD();TDEtiSelect(true, "EEtiqueta", 0, "Modalidad:", "iCveModalidad", "fGetRequisito();");
    FITR();
    	ITD();TDEtiSelect(true, "EEtiqueta", 0, "Requisito:", "iCveRequisito", "fNavega();");
    FinTabla();
    //Tabla Reservada para Listado.
    InicioTabla("", 0, "100%", "", "", "", "1");
    ITRTD("", 0, "", "175", "center", "top");
    ITD();IFrame("IListadoI", "95%", "170", "Listado.js", "yes", true);
    FITD();
    	InicioTabla("", 0, "100%", "", "", "", "1");
    		ITR();BtnImg("Agregar", "flechad", "fGuardar();");
    		FITR();BtnImg("Borrar", "flechaI", "fBorrar();");
    		FITR();
    		FITR();BtnImg("Arriba", "flechaArr", "fSubir();");
    		FITR();BtnImg("Abajo", "flechaAbj", "fBajar();");
    	FinTabla();
    FITD();IFrame("IListadoD", "95%", "170", "Listado.js", "yes", true);
    FTDTR();
    FinTabla();
    //Tabla reservada para despliegue de campos
    InicioTabla("ETablaInfo", 0, "75%", "", "center", "", 1);
    ITRTD("ETablaST", 5, "75%", "", "center");
    TextoSimple(cTitulo);
    FTDTR();
    ITR();
    //TDEtiCampo(true, "EEtiqueta", 0, " Orden: :", "iOrden", "", 4,4,"Orden que llevara el campo al momento de presentarse ", "fMayus(this);");
    Hidden("iOrden");
    FITR();
    TDEtiCampo(true, "EEtiqueta", 0, " Carpeta: :", "cCarpeta", "", 50, 50," Comentario: ", "");
    FITR();
    FTR();
    FinTabla();
    //Tabla recerbada para panel y botones.
    InicioTabla("", 0, "100%", "", "center", "", 1);
    ITRTD("", 0, "", "40", "center", "bottom");
    IFrame("IPanel52", "95%", "34", "Paneles.js");
    FTDTR();
    FinTabla();
    Hidden("hdSelect");
    Hidden("hdLlave");
    Hidden("iOrden2");
    Hidden("iCveCampo");
    Hidden("iCveCampoI");
    FinTabla();
    fFinPagina();

}

function fOnLoad() {
    form = document.forms[0];
    frm = document.forms[0];
    FRMPanel = fBuscaFrame("IPanel52");
    FRMPanel.fSetControl(self, cPaginaWebJS);
    FRMPanel.fShow("Tra,");
    FRMListado52 = fBuscaFrame("IListadoD");
    FRMListado52.fSetControl(self);
    FRMListado52.fSetTitulo("Consecutivo,Campo Asignado,");
    FRMListado52.fSetCampos("0,1,");
    FRMListado52.fSetAlinea("left,left,");
    FRMListado52.fSetSelReg(1);
    FRMListadoI = fBuscaFrame("IListadoI");
    FRMListadoI.fSetControl(self);
    FRMListadoI.fSetTitulo("Consecutivo,Campo Disponible,");
    FRMListadoI.fSetCampos("0,1,");
    FRMListadoI.fSetAlinea("left,left,");
    FRMListadoI.fSetSelReg(2);
    frm.cCveTramite.onFocus = false;
    fDisabled(false);
    fDisabled(true,"iCveTramite,cCveTramite,iCveModalidad,iCveRequisito,");
    frm.hdBoton.value = "Primero";
    
}
function fNavega() {
    if(frm.iCveTramite.value>0 && frm.iCveModalidad.value>0 && frm.iCveRequisito.value){
        frm.hdFiltro.value = "iCveTramite="+frm.iCveTramite.value+
        			 " AND iCveModalidad="+frm.iCveModalidad.value+
        			 " AND iCveRequisito= "+frm.iCveRequisito.value;
        frm.hdOrden.value  = "TC.iOrden";
        frm.hdNumReg.value = 1000;
        fEngSubmite("pgINTTramXCampo.jsp", "Listado");
    }
}
// RECEPCIÓN de Datos de provenientes del Servidor
function fResultado(aRes, cId, cError, cNavStatus, iRowPag, cLlave) {
    if (cError == "Guardar")
	fAlert("Existió un error en el Guardado!");
    if (cError == "Borrar")
	fAlert("Existió un error en el Borrado!");
    if (cError == "Cascada") {
	fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
	return;
    }

    if (cId == "Listado" && cError == "") {
	frm.hdRowPag.value = iRowPag;
	FRMListado52.fSetListado(aRes);
	FRMListado52.fShow();
	FRMListado52.fSetLlave(cLlave);
	aAgregados = fCopiaArreglo(aRes);
	if(frm.iOrden2.value>0) {
	    
	    fReposicionaListado(FRMListado52,"2", ""+frm.iOrden2.value);
	}
	fFiltraCampos();
	fCancelar();
    }
    if(cId == "cIdTramite" && cError==""){
	for(i=0;i<aRes.length;i++){
	    aRes[i][1]=aRes[i][2]+" - "+aRes[i][1];
	}
	fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,1);
    }
    if(cId=="cIdModalidad" && cError==""){
	fFillSelect(frm.iCveModalidad,aRes,false,frm.iCveModalidad.value,0,1);
	fGetRequisito();
    }
    if(cId=="cIdRequisito" && cError==""){
	fFillSelect(frm.iCveRequisito,aRes,false,frm.iCveRequisito.value,0,1);
	fNavega();
    }
    if(cId=="cIdCamposT" && cError==""){
	aCampos = fCopiaArreglo(aRes);
	fTramite();
    }
	
}
// LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
function fNuevo() {
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false, ",", "--");
    FRMListado52.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Guardar
function fGuardar() {
    frm.hdBoton.value = "Guardar";
	if (fNavega() == true) {
	    FRMPanel.fSetTraStatus("UpdateComplete");
	    fDisabled(true);
	    FRMListado52.fSetDisabled(false);
	}
}
// LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
function fGuardarA() {
	if (fNavega() == true) {
	    FRMPanel.fSetTraStatus("UpdateComplete");
	    fDisabled(true);
	    FRMListado52.fSetDisabled(false);
	}
}
// LLAMADO desde el Panel cada vez que se presiona al botón Modificar
function fModificar() {
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(true);
    fDisabled(false, "cCveTramite,iCveTramite,iCveModalidad,iCveRequisito,iOrden,");
    FRMListado52.fSetDisabled(true);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
function fCancelar() {
    if (FRMListado52.fGetLength() > 0)
	FRMPanel.fSetTraStatus("Mod,");
    else
	FRMPanel.fSetTraStatus("");
    fDisabled(false);
    fDisabled(true,"iCveTramite,cCveTramite,iCveModalidad,iCveRequisito,");
    FRMListado52.fSetDisabled(false);
}
// LLAMADO desde el Panel cada vez que se presiona al botón Borrar
function fBorrar() {
    frm.hdBoton.value = "Borrar";
    if (confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")) {
	fNavega();
    }
}
// LLAMADO desde el Listado cada vez que se selecciona un renglón
function fSelReg(aDato) {
    frm.iCveCampo.value = aDato[0];
    frm.iCveCampo.value = aDato[0];
    frm.iOrden.value = aDato[2];
    frm.cCarpeta.value=aDato[3];
}
function fSelReg2(aDato1) {
    frm.iCveCampoI.value = aDato1[0];
}
// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo() {
    cMsg = fValElements();
    if (cMsg != "") {
	fAlert(cMsg);
	return false;
    }
    return true;
}
function fImprimir() {
    self.focus();
    window.print();
}

function fTramite(){
    frm.hdSelect.value = "SELECT ICVETRAMITE, CDSCBREVE, CCVEINTERNA FROM TRACATTRAMITE WHERE LVIGENTE=1 AND LTRAMITEFINAL=1 ORDER BY CCVEINTERNA ";
    frm.hdLlave.value = "ICVETRAMITE";
    return fEngSubmite("pgConsulta.jsp","cIdTramite");
}
function fGetModalidad(){
    fFillSelect(frm.iCveModalidad,aNuevo,false,frm.iCveModalidad.value,0,1);
    if(frm.iCveTramite.value > 0){
            frm.hdSelect.value = 
        	"SELECT CT.ICVEMODALIDAD,M.CDSCMODALIDAD " +
        	"FROM TRACONFIGURATRAMITE CT " +
        	"JOIN TRAMODALIDAD M ON CT.ICVEMODALIDAD=M.ICVEMODALIDAD " +
        	"WHERE M.LVIGENTE=1 AND CT.LACTIVO=1 AND CT.ICVETRAMITE= "+frm.iCveTramite.value;
            frm.hdLlave.value = "iCveTramite,iCveModalidad,";
            fEngSubmite("pgConsulta.jsp","cIdModalidad");
    }
}
function fGetRequisito(){
    if(frm.iCveTramite.value > 0 && frm.iCveModalidad.value > 0){
        frm.hdSelect.value = 
            "SELECT R.ICVEREQUISITO,R.CDSCREQUISITO " +
            "FROM TRAREQXMODTRAMITE RM " +
            "JOIN TRAREQUISITO R ON R.ICVEREQUISITO=RM.ICVEREQUISITO " +
            "WHERE LVIGENTE=1 "+
            " AND RM.ICVETRAMITE="+frm.iCveTramite.value+
            " AND RM.ICVEMODALIDAD="+frm.iCveModalidad.value +
            " ORDER BY R.CDSCREQUISITO ";
        frm.hdLlave.value  = "";
        fEngSubmite("pgConsulta.jsp","cIdRequisito");
    }
}

function fGetCampos(){
    frm.hdSelect.value = "SELECT iCveCampo,cCampo FROM INTCAMPOS";
    frm.hdLlave.value = "iCveCampo";
    fEngSubmite("pgConsulta.jsp","cIdCamposT");
}

function fFiltraCampos(){
    var lEncontrado=false;
    aNuevo = new Array();
    for(i=0;i<aCampos.length;i++){
	lEncontrado=false;
	for (j=0;j<aAgregados.length;j++){
	    if(aCampos[i][0]==aAgregados[j][0]) lEncontrado=true;
	}
	if(lEncontrado==false) aNuevo[aNuevo.length] = fCopiaArreglo(aCampos[i]); 
    }
    FRMListadoI.fSetListado(aNuevo);
    FRMListadoI.fShow();
}

function fSubir(){
    var iMenor = 0;
    for(i=0;i<aAgregados.length;i++){
	if(aAgregados[i][2]<frm.iOrden.value && aAgregados[i][2]>=iMenor)
	    iMenor=aAgregados[i][2];
    }
    if(iMenor > 0){
	frm.iOrden2.value = iMenor;
	frm.hdBoton.value = "Subir";
	fNavega();
    }
}
function fBajar(){
    var iMayor = 1000;
    for(i=0;i<aAgregados.length;i++){
	if(aAgregados[i][2]>frm.iOrden.value && aAgregados[i][2]<iMayor)
	    iMayor=aAgregados[i][2]
    }
    if(iMayor<1000){
        frm.iOrden2.value = iMayor;
        frm.hdBoton.value = "Subir";
        fNavega();
    }
}
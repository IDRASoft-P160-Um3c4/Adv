// MetaCD=1.0
var form;
function fBefLoad() {
	// cPaginaWebJS = "pg110000050.js";
	// if(top.fGetTituloPagina){;
	cTitulo = "ACCESO SGPADV";
	fSetWindowTitle();
	// }
	return true;
}

function fDefPag() {
	fInicioPagina(cColorGenJSInicio);
	InicioTabla("", 0, "805", "693", "center", "SIPYMM_inicio.jpg", "", "", "");
	ITRTD("", 0, "", "", "top");
	InicioTabla("", 0, "", "120", "left", "");
	TextoSimple("<br>");
	FinTabla();
	ITRTD("", 0, "", "left", "top");
	InicioTabla("", 0, "", "100", "left", "Top");
	ITR("", "50", "50", "left", "top", "", "");
	ITD("", 0, "330");
	TextoSimple("<br>");
	FTD();
	ITD("", "", "190", "", "", "");
	Text(true, "cUsuario", "", "14", "25", "", "",
			'onKeyPress="fCheckReturn(event,this);"');
	FTD();
	ITD("", "", "", "", "", "");
	Pwd(0, "cContrasena", "", "14", "25", "Introdusca la contraseña", "",
			'onKeyPress="fCheckReturn(event,this);"');
	FTD();
	ITD("", "", "25", "27", "", "");
	BtnImg("Btn-IniAceptar", "btn-IniAceptar", "fAceptar();", "");
	FTD();
	ITD("", "", "25", "27", "", "");
	BtnImg("Btn-IniRestaurar", "btn-IniRestaurar", "fAceptar();", "");
	FTD();
	FTR();
	FinTabla();
	ITRTD("", 0, "", "left", "top");
	InicioTabla("", 0, "", "415", "left", "Top");
	TextoSimple("<br>");
	FinTabla();

	ITR("", "100%", "100%", "left", "top", "", "");
	FTR();
	FinTabla();
	FinTabla();
	fFinPagina();
}

function fOnLoad() {
	form = document.forms[0];
}

function fValidaTodo() {
	cMsg = fValElements();

	if (cMsg != '') {
		fAlert(cMsg);
		return false;
	}
	return true;
}

function fCheckReturn(evt, obj) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode == 13) {
		if (obj.name == "cUsuario")
			if (obj.value == "")
				obj.focus();
			else if (form.cContrasena.value == "")
				form.cContrasena.focus();
			else
				fAceptar();
		if (obj.name == "cContrasena")
			if (form.cUsuario.value == "")
				form.cUsuario.focus();
			else if (obj.value == "")
				obj.focus();
			else
				fAceptar();
	}
}

function fAceptar() {
	if (fValidaTodo() == true) {
		form.hdBoton.value = "Aceptar";
		fEngSubmite('pgUsrPwd.jsp', '');
	}
}

// function fResultado(aRes){
// if(aRes[0] == '1')
// fAlert("El usuario no tiene configurados permisos de acceso.");
// if(aRes[0] == '2')
// fAlert("El usuario o contraseña no son válidos.");
// if(aRes[0] == '3')
// fAlert("No es posible realizar la conexión con el sistema.");
//
// cTmp = aRes[0];
// if(cTmp.substring(0,1) == '.'){
// aMenu = new Array();
// aPermisos = new Array();
// j = 0;lMenu = true;
// for(i=1;i<aRes.length-1;i++){
// aDato = aRes[i];
// if(aDato[0] == 'Permisos'){
// lMenu = false;
// j = 0;
// }
// if(lMenu == true){
// aMenu[j] = aRes[i];
// j++;
// }
// if(lMenu == false){
// aPermisos[j] = aRes[i+1];
// j++;
// }
// }
// top.fSetUsrName(form.cUsuario.value);
// top.fSetUsrID(cTmp.substring(1));
// top.fSetMenu(aMenu);
//      
// top.fSetPermisos(aPermisos);
// oneDate = new Date();
// top.fSetTiempoActualLocal(''+oneDate.getHours()+'/'+oneDate.getMinutes()+'/');
// fAbrePagina('pg'+iNDSADM+'00003');
// }
// }

function fResultado(aRes, cError, cError2, lChgPwd) {
	if (aRes[0] == '1')
		fAlert("El usuario no tiene configurados permisos de acceso.");
	if (aRes[0] == '2')
		fAlert("El usuario o contraseña no son válidos.");
	if (aRes[0] == '3')
		fAlert("No es posible realizar la conexión con el sistema.");
	
	cTmp = aRes[0];
	if (cTmp.substring(0, 1) == '.') {
		aMenu = new Array();
		aPermisos = new Array();
		j = 0;
		lMenu = true;
		for (i = 1; i < aRes.length - 1; i++) {
			aDato = aRes[i];
			if (aDato[0] == 'Permisos') {
				lMenu = false;
				j = 0;
			}
			if (lMenu == true) {
				aMenu[j] = aRes[i];
				j++;
			}
			if (lMenu == false) {
				aPermisos[j] = aRes[i + 1];
				j++;
			}
		}

		cUsrID = fEntry(1, cTmp.substring(1), "|");
		top.fSetUsrID(fEntry(1, cTmp.substring(1), "|"));
		top.fSetUsrName(fEntry(2, cTmp.substring(1), "|"));
		top.fSetMenu(aMenu);
		top.fSetPermisos(aPermisos);
		oneDate = new Date();
		top.fSetTiempoActualLocal('' + oneDate.getHours() + '/'
				+ oneDate.getMinutes() + '/');
		if (lChgPwd == 'true') {
			cUsr = cNombre;
			if (cUsr.indexOf(",") > -1) {
				cUsr = cUsr.substring(0, cUsr.indexOf(","));
			}
			fAlert("\nSu contraseña ha caducado o es un usuario nuevo. Debe cambiar su contraseña para poder acceder al sistema.");
			open(
					cRutaADMSEGPWD + '?iCveUsuario=' + cUsrID + '&cUsuario='
							+ cUsr,
					'',
					'dependent=yes,hotKeys=no,location=no,menubar=no,personalbar=no,resizable=no,scrollbars=no,status=yes,titlebar=no,toolbar=no,width=900,height=590,screenX=50,screenY=50');
			return;
		} else {
			fAbrePagina('pg' + iNDSADM + '00003');
			// fAbreSubWindow(false,'pg'+iNDSADM+'00003',"no","yes","no","yes",screen.availWidth,screen.availHeight,0,0);
			// wExp.focus();
		}
		// fAbrePagina('pg'+iNDSADM+'00003');
	}
}

function ITDROW(cEstilo, iColExtiende, cAncho, cAlto, cAlign, cValign) { // 4000-Genera
																			// la
																			// definición
																			// de
																			// una
																			// columna
	cTx = '<td';
	if (cEstilo) {
		if (cEstilo != "")
			cTx += ' class="' + cEstilo + '"';
	}
	if (cAncho) {
		if (cAncho != "") {
			if (cAncho.substring(0, 1).toUpperCase() == "P")
				cAncho = cAncho.substring(1) + "%";
			cTx += ' width="' + cAncho + '"';
		}
	}
	if (iColExtiende) {
		if (iColExtiende != 0)
			cTx += ' rowspan="' + iColExtiende + '"';
	}
	if (cAlto) {
		if (cAlto != "") {
			if (cAlto.substring(0, 1).toUpperCase() == "P")
				cAlto = cAlto.substring(1) + "%";
			cTx += ' height="' + cAlto + '"';
		}
	}
	if (cAlign) {
		if (cAlign != '')
			cTx += ' align="' + cAlign + '"';
	}
	if (cValign) {
		if (cValign != '')
			cTx += ' valign="' + cValign + '"';
	}
	cTx += ">";
	cGPD += cTx + "\n";
	return cTx;
}

function BtnImgJPG(cNombreM, cNomImgM, cHRefM, cEstatusM, l4Status, cImgIni) { // 3000-botón
																				// de
																				// tipo
																				// imagen
	iImgs++;
	aImgs[iImgs] = cRutaImgServer + cNomImgM;
	var imgIni = "01";
	cTx = '<a href="JavaScript:' + cHRefM + '"' + "\n";
	if (l4Status == true) {
		cTx += ' onMouseOut="' + "if(fMouseOut)fMouseOut(document, '"
				+ cNomImgM + "');self.status='';" + 'return true;"' + "\n";
		cTx += ' onMouseOver="' + "if(fMouseOver)fMouseOver(document, '"
				+ cNomImgM + "');self.status='" + cEstatusM + "';"
				+ 'return true;">' + "\n";
	} else {
		cTx += ' onMouseOut="' + "if(fCambiaImagen)fCambiaImagen(document,'"
				+ cNombreM + "','','" + cRutaImgServer + cNomImgM
				+ "01.jpg',1);self.status='';" + 'return true;"' + "\n";
		cTx += ' onMouseOver="' + "if(fCambiaImagen)fCambiaImagen(document,'"
				+ cNombreM + "','','" + cRutaImgServer + cNomImgM
				+ "02.jpg',1);self.status='" + cEstatusM + "';"
				+ 'return true;">' + "\n";
	}
	if (cImgIni && cImgIni != "")
		imgIni = cImgIni;
	cTx += '<img border="0" name="' + cNombreM + '" src="' + cRutaImgServer
			+ cNomImgM + imgIni + '.jpg" alt="' + cEstatusM + '">';
	cTx += '</a>' + "\n";
	cGPD += cTx;
	return cTx;
}

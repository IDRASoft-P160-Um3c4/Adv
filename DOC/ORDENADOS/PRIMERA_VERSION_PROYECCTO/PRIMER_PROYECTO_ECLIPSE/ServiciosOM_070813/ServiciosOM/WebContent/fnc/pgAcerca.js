// MetaCD=1.0
 // Title: pgAcerca.js
 // Description: JS "Cat�logo" de la entidad CPAClasificacion
 // Company: Tecnolog�a InRed S.A. de C.V.
 // Author: ICaballero
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)

 function fBefLoad(){
 cPaginaWebJS = "pgAcerca.js";
 if(top.fGetTituloPagina)
 cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
 cTitulo = (cTitulo == "" || cTitulo == "T�TULO NO ENCONTRADO")?"ACERCA DE":cTitulo;
 fSetWindowTitle();
 }
 // SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     fTituloEmergente("Sistema Institucional de Puertos y Marina Mercante");
     ITRTD("",0,"","","top");

     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","175","center","top");
       InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Acerca del Sistema: SIPYMM");
           FTDTR();
           ITR();
           ITD("EEtiqueta");//ITD(cEstilo,iColExtiende,cAncho,cAlto,cAlign,cValign);
             TextoSimple("Versi�n");
           ITD("ENormal");
             TextoSimple("Beta");
           FTDTR();
           ITR();
           ITD("EEtiqueta");
             TextoSimple("Ayuda de Operaci�n:");
           ITD("ENormal");
             TextoSimple("Mesa de Ayuda");
           FTDTR();
           ITR();
           ITD("EEtiqueta");
             TextoSimple(" ");
           ITD("ENormal");
             TextoSimple("cat@sct.gob.mx");
           FTDTR();
           ITR();
           ITD("EEtiqueta");
             TextoSimple(" ");
           ITD("ENormal");
             TextoSimple("(0155) 5723-9300 ext. 46481 / (01 800) 900-0012");
           FTDTR();
           ITR();
           ITD("EEtiqueta");
             TextoSimple("Soporte T�cnico:");
           ITD("ENormal");
             TextoSimple("Area de Soporte T�cnico (Ing. Allan Roberto Mireles Calder�n)");
           FTDTR();
           ITR();
           ITD("EEtiqueta");
             TextoSimple(" ");
           ITD("ENormal");
             TextoSimple("amireles@sct.gob.mx");
           FTDTR();
           ITR();
           ITD("EEtiqueta");
             TextoSimple(" ");
           ITD("ENormal");
             TextoSimple("(0155) 5723-9300 ext. 16480");
           FTDTR();
           ITR();
          ITD("EEtiqueta");
            TextoSimple("Usuario Actual:");
           ITD();
             Text(false,"cUser","---", 50,50,"Nombre de Usuario en el Sistema");
         FinTabla();
       FTDTR();
       ITRTD("",0,"","1","center");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Temas de Ayuda para la Operaci�n del Sistema");
           FTDTR();
           ITR();
           ITD("ENormal");
             TextoSimple("Es importante resaltar que en aplicaciones que trabajan con navegador de internet, solo debe dar un click sobre botones, ligas y cualquier otro elemento, la acci�n doble click podr�a generar resultados inesperados.");
           FTDTR();
           ITRTD();
             TextoSimple("<br>");
           FTDTR();
           ITRTD();
             Liga("Como configurar el navegador para un correcto funcionamiento","fConfigura();","Configuracion del Navegador");
           FTDTR();
           ITRTD();
             Liga("Como configurar el navegador para el funcionamiento de Reportes","fConfiguraReportes();","Configuracion del Navegador");
           FTDTR();
           ITRTD();
             Liga("Manuales de Usuario","fManualesUsuario();","Manuales de Usuario");
           FTDTR();
         FinTabla();
       FTDTR();
       FinTabla();
     FTDTR();
     Hidden("idUser","");
     Hidden("hdLlave","");
     Hidden("hdSelect","");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Despu�s de Cargar la p�gina (Definici�n Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   fDisabled(true);
   frm.hdBoton.value="Primero";
   frm.idUser.value = fGetIdUsrSesion();
   fNavega();
 }
 // LLAMADO al JSP espec�fico para la navegaci�n de la p�gina
 function fNavega(){
      frm.hdLlave.value =   "iCveUsuario";
      frm.hdSelect.value =  "select iCveUsuario,cUsuario,cNombre,cApPaterno,cApMaterno " +
                            "from SEGUSUARIO " +
                            "where ICVEUSUARIO = "+fGetIdUsrSesion();
      frm.hdNumReg.value = 100;
      fEngSubmite("pgConsulta.jsp","Usuario");
 }
 // RECEPCI�N de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existi� un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existi� un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cError!="")
     FRMFiltro.fSetNavStatus("Record");

   if(cId == "Usuario" && cError==""){
     frm.cUser.value = (aRes[0])?aRes[0][2]+' '+aRes[0][3]+' '+aRes[0][4]:"";
   }
 }
 function fConfigura(){
   fAbreSubWindow(true,"pgConfigura","no","no","no","yes",800,400);
 }
 function fConfiguraReportes(){
   fAbreSubWindow(true,"pgConfiguraReportes","no","no","no","yes",800,400);
 }
 function fManualesUsuario(){
   fAbreSubWindow(true,"pgManualesUsr","no","no","no","yes",800,600);
 }

// MetaCD=1.0 PgConfigura.js
 // Description: JS "Catálogo" de la entidad CPAClasificacion
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Rafael Miranda
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
 cPaginaWebJS = "pgManualesUsr.js";
 if(top.fGetTituloPagina)
 cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
 cTitulo = (cTitulo == "" || cTitulo == "TÍTULO NO ENCONTRADO")?"ACERCA DE: MANUALES DE USUARIO":cTitulo;
 fSetWindowTitle();
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     fTituloEmergente("Sistema Institucional de Puertos y Marina Mercante");
     ITRTD("",0,"","","top");
     InicioTabla("",0,"","","top");
       ITRTD("",0,"","","center","top");
         InicioTabla("ETablaInfo",0,"85%","","","",1);
           ITRTD("ETablaST",5,"","","center");
             TextoSimple("Manuales de Usuario del Sistema");
           FTDTR();
           ITRTD("ENormal");
             TextoSimple("A continuación se despliega una lista de los manuales de usuario del sistema agrupados por módulo, haga click en el que desea descargar.");
           FTDTR();
         FinTabla();
       FTDTR();
       ITRTD("",0,"","","center","top");
         TextoSimple("<br>");
       FTDTR();
       ITRTD("",0,"","","center","top");
         InicioTabla("ETablaInfo",0,"95%","","","",1);
           for(var i=0; i<aManuales.length; i++){
             ITR();
               ITD((aManuales[i][0]!='')?"ETablaSTL":"ENormal");
                 TextoSimple(aManuales[i][0]);
               FTD();
               ITD((aManuales[i][1]!='')?"ETablaSTL":"ENormal");
                 TextoSimple(aManuales[i][1]);
               FTD();
               ITD("ENormal");
                 Liga(aManuales[i][2],"fManual('"+aManuales[i][3]+"');","Abrir el documento seleccionado");
               FTD();
             FTR();
           }
         FinTabla();
       FTDTR();
       ITRTD("",0,"","1","center");
       FTDTR();
       FinTabla();
     FTDTR();
       Hidden("idUser");
       Hidden("hdLlave");
       Hidden("hdSelect");
     FinTabla();
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   fDisabled(true);
   frm.hdBoton.value="Primero";
   frm.idUser.value = fGetIdUsrSesion();
 }
function fManual(cNomArch){
  fAbreWindowHTML(false,cRutaManualUsr + escape(cNomArch),true,true,true,true,true,800,600,0,0);
}

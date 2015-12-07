// MetaCD=1.0
 // Title: pg111040011.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ijimenez
 var cTitulo = "";
 var FRMListado = "";
 var aMes = new Array();
 var frm;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "pg111040011.js";
   if(top.fGetTituloPagina){;
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
   }
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   fInicioPagina(cColorGenJS);
   InicioTabla("",0,"100%","","","","1");
     FTDTR();
     ITRTD("",0,"","","top");
       ITRTD("",0,"","40","center","top");
      InicioTabla("ETablaInfo",0,"0","","center","",1);
        ITRTD("ETablaST",12,"","","center");
          TextoSimple(cTitulo);
          FTDTR();
            ITRTD("",0,"","40","center","top");
              InicioTabla("ETablaInfo",0,"0","","center","",1);
                  TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","fOficinaOnChange();");
                FITR();
                  TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDepartamento","");
                FTR();
              FinTabla();
            ITRTD("",0,"","40","center","top");
              InicioTabla("ETablaInfo",0,"0","","center","",1);
                  TDEtiCampo(true,"EEtiqueta",0,"Ejercicio:","iEjercicio","",10,10,"Ejercicio en que se realizaron las Solicitudes","fMayus(this);","","",false,"EEtiquetaL",3);
                ITD();
                  TDEtiSelect(true,"EEtiqueta",0,"Periodo de Ingreso:","iPeriodo","");
                FTD();
                FTR();
              FinTabla();
            BtnImg("vgbuscar","lupa","fBuscaDatos();","");
      FinTabla();
       FTDTR();
       ITRTD("",0,"","250","center","top");
         IFrame("IListado11","95%","250","Listado.js","yes",true);
       FTDTR();
       ITRTD("",0,"","1","center");

       FTDTR();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
        InicioTabla("ETablaInfo",0,"0","","center","",1);
          TDEtiCampo(false,"EEtiqueta",0,"Solicitudes Emitidas:","iTotal","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0,"Recibidas:","iRecibidas","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0,"Atendidas:","iAtendidas","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0,"Canceladas:","iCanceladas","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          TDEtiCampo(false,"EEtiqueta",0,"Vencidas:","iVencidas","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
        FinTabla();
       FTDTR();
     FinTabla();
    Hidden("hdLlave","");
    Hidden("hdSelect","");
    Hidden("iCveUsuario");
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMListado = fBuscaFrame("IListado11");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Solicitud,Tramite,Modalidad,Registro\nV.U.,Recepción\nÁrea,Termino\nÁrea,Días Efectivos,Entrega\nProgramada,Cancelacion,Estatus,");
   FRMListado.fSetCampos("1,2,3,4,5,6,10,7,8,9,");
   FRMListado.fSetAlinea("left,left,left,center,center,center,center,center,");
   frm.hdBoton.value="Primero";
   aMes[0] = [1,"Enero"];
   aMes[1] = [2,"Febrero"];
   aMes[2] = [3,"Marzo"];
   aMes[3] = [4,"Abril"];
   aMes[4] = [5,"Mayo"];
   aMes[5] = [6,"Junio"];
   aMes[6] = [7,"Julio"];
   aMes[7] = [8,"Agosto"];
   aMes[8] = [9,"Septiembre"];
   aMes[9] = [10,"Octubre"];
   aMes[10] = [11,"Noviembre"];
   aMes[11] = [12,"Diciembre"];
   fFillSelect(frm.iPeriodo,aMes,false,0,0,1);
   frm.hdFiltro.value = "";
   frm.hdNumReg.value=100000;
   frm.hdOrden.value  = "cDscBreve";
   fEngSubmite("pgGRLOficina.jsp","cIdOficina");
 }
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   fResOficDeptoUsr(aRes,cId,cError);
   fResTramiteModalidad(aRes,cId,cError);


   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cId == "Listado" && cError==""){
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     frm.iTotal.value=aRes.length;
     var iRecibidas=0,iAtendidas=0,iCanceladas=0,iVencidas=0;
     var cMes=frm.iPeriodo.value<10?"0"+frm.iPeriodo.value:frm.iPeriodo.value;
     for(i=0;i<aRes.length;i++){
       if(aRes[i][5]!="") iRecibidas++;
       if(aRes[i][6]!="") iAtendidas++;
       if(aRes[i][8]!="") iCanceladas++;
       if(aRes[i][7]!="" && aRes[i][6] && fComparaFecha(aRes[i][7],aRes[i][6],true)){
         iVencidas++;
         aRes[i][7] = "<font color=red>"+aRes[i][7]+"</font>"
       }
     }
     frm.iRecibidas.value = iRecibidas;
     frm.iAtendidas.value = iAtendidas;
     frm.iCanceladas.value = iCanceladas;
     frm.iVencidas.value = iVencidas;
     FRMListado.fShow();
     FRMListado.fSetLlave(cLlave);
   }
   if(cId == "cIdOficina" && cError == "")
     fFillSelect(frm.iCveOficina,aRes,true,0,0,2);
   if(cId == "cIdDepto" && cError == "")
     fFillSelect(frm.iCveDepartamento,aRes,true,0,1,7);
   if(cId=="cIdTramites" && cError==""){
     fFillSelect(frm.iCveTramite,aRes,false,frm.iCveTramite.value,1,6);
     fTramiteOnChange();
   }
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
 }

 function fImprimir(){
    self.focus();
    window.print();
 }

function fOficinaOnChange(){
  frm.hdFiltro.value = "GRLDeptoXOfic.iCveOficina="+frm.iCveOficina.value;
  frm.hdOrden,value  = "cDscBreve";
  fEngSubmite("pgGRLDeptoXOfic.jsp","cIdDepto");
}
function fBuscaDatos(){
  var msg="";
  if(frm.iEjercicio.value=="") msg+="\nEl campo de Ejercicio es obligatorio.";
  if(fSoloNumeros(frm.iEjercicio.value)==false) msg+="\nEl campo de Ejercicio solo acepta caracteres numericos.";
  if(frm.iCveOficina.value<=0) msg+="\nEl campo de Oficina es obligatorio.";
  if(frm.iCveDepartamento.value<=0) msg+="\nEl campo de Departamento es obligatorio.";
  if(msg!="") {
    fAlert(msg);
    return;
  }
  frm.hdLlave.value = "";
  frm.hdSelect.value = "SELECT " +
                       "R.IEJERCICIO,R.INUMSOLICITUD,T.CCVEINTERNA||' '|| T.CDSCBREVE AS CDSCTRAMITE,M.CDSCMODALIDAD, " +
                       "DATE(R.TSREGISTRO) AS DTREGSITRO,DATE(ET.TSREGISTRO) AS DTRECIBEAREA, DATE(ET1.TSREGISTRO) AS DTREGFIN,R.DTESTIMADAENTREGA, " +
                       "DATE(TS.DTCANCELACION) AS DTCANCELA,E.CDSCETAPA,(DATE(ET1.TSREGISTRO)-DATE(R.TSREGISTRO)) AS IDIASRESOLUCION " +
                       "FROM TRAREGSOLICITUD R " +
                       "LEFT JOIN TRAREGETAPASXMODTRAM ET2 ON ET2.IEJERCICIO=R.IEJERCICIO AND ET2.INUMSOLICITUD=R.INUMSOLICITUD AND ET2.ICVEETAPA=1 " +
                       "LEFT JOIN TRAREGETAPASXMODTRAM ET ON ET.IEJERCICIO=R.IEJERCICIO AND ET.INUMSOLICITUD=R.INUMSOLICITUD AND ET.ICVEETAPA=2 " +
                       "LEFT JOIN TRAREGETAPASXMODTRAM ET1 ON ET1.IEJERCICIO=R.IEJERCICIO AND ET1.INUMSOLICITUD=R.INUMSOLICITUD AND ET1.ICVEETAPA=7 " +
                       "LEFT JOIN TRAREGTRAMXSOL TS ON TS.IEJERCICIO=R.IEJERCICIO AND TS.INUMSOLICITUD=R.INUMSOLICITUD " +
                       "LEFT JOIN TRACATTRAMITE T ON T.ICVETRAMITE=R.ICVETRAMITE " +
                       "LEFT JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=R.ICVEMODALIDAD " +
                       "LEFT JOIN TRAREGETAPASXMODTRAM EF ON EF.IEJERCICIO=R.IEJERCICIO AND EF.INUMSOLICITUD=R.INUMSOLICITUD " +
                       "LEFT JOIN TRAETAPA E ON EF.ICVEETAPA=E.ICVEETAPA " +
                       "WHERE R.iEjercicio = " + frm.iEjercicio.value +
                       "   AND MONTH(DATE(R.TSREGISTRO))<=" +frm.iPeriodo.value+
                       "   AND ET2.ICVEOFICINA= " +frm.iCveOficina.value+
                       "   AND ET2.ICVEDEPARTAMENTO= " +frm.iCveDepartamento.value+
                       "   AND EF.ICVEETAPA= " +
                       "(SELECT MAX(ICVEETAPA) FROM TRAREGETAPASXMODTRAM EF1 WHERE EF1.IEJERCICIO=R.IEJERCICIO AND EF1.INUMSOLICITUD=R.INUMSOLICITUD) " +
                       " ORDER BY INUMSOLICITUD ";
  fEngSubmite("pgConsulta.jsp","Listado");
}

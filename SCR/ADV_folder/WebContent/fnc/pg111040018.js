// MetaCD=1.0
 // Title: pg111040011.js
 // Description: JS "Catálogo" de la entidad TRARegSolicitud
 // Company: Tecnología InRed S.A. de C.V.
 // Author: ijimenez
 var cTitulo = "";
 var FRMListado = "";
 var aMes = new Array();
 var aResTotal = new Array();
 var aFiltro = new Array();
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
              InicioTabla("",0,"0","","center","",1);
                  TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","fOficinaOnChange();");
                FITR();
                  TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDepartamento","");
                FTR();
              FinTabla();
            ITRTD("",0,"","40","center","top");
              InicioTabla("",0,"0","","center","",1);
                  //TDEtiCampo(true,"EEtiqueta",0,"Ejercicio:","iEjercicio","",10,10,"Ejercicio en que se realizaron las Solicitudes","fMayus(this);","","",false,"EEtiquetaL",3);
                FITR();
                  TDEtiCampo(true,"EEtiqueta",0,"Fecha Inicio:","dtInicio","",10,10,"Fecha de inicio del periodo","fMayus(this);","","",false,"EEtiquetaL",3);
                  TDEtiCampo(true,"EEtiqueta",0,"Fecha Fin:","dtFin","",10,10,"Fecha de fin del periodo","fMayus(this);","","",false,"EEtiquetaL",3);
                FITR();
                  TDEtiSelect("true","EEtiqueta",0,"Filtro por estado:","iCveEstado","fOnChangeEstado()");
                  //TDEtiSelect(true,"EEtiqueta",0,"Periodo de Ingreso:","iPeriodo","");
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
          ITR();
            TDEtiCampo(false,"EEtiqueta",0,"Solicitudes Emitidas:","iTotal","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
            TDEtiCampo(false,"EEtiqueta",0,"Recibidas por el Área:","iRecibidas","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          FITR();
            TDEtiCampo(false,"EEtiqueta",0,"Atendidas Dentro de Tiempo:","iAtendidasDT","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
            TDEtiCampo(false,"EEtiqueta",0,"Atendidas Fuera de Tiempo:","iAtendidasFT","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          FITR();
            TDEtiCampo(false,"EEtiqueta",0,"Pendientes Dentro de Tiempo:","iPendientesDT","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
            TDEtiCampo(false,"EEtiqueta",0,"Pendientes Fuera de Tiempo:","iPendientesFT","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          FITR();
            TDEtiCampo(false,"EEtiqueta",0,"Canceladas Dentro de Tiempo:","iCanceladasDT","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
            TDEtiCampo(false,"EEtiqueta",0,"Canceladas Fuera de Tiempo:","iCanceladasFT","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          FITR();
            TDEtiCampo(false,"EEtiqueta",0,"Improcedentes:","iImprocedentes","",10,10,"Suma de el numero de operaciones que arrojo la consulta","fMayus(this);","","",false,"EEtiquetaL",3);
          FTR();
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
   FRMListado.fSetTitulo("Solicitud,Aplica al Bien,Tramite,Modalidad,Registro\nV.U.,Recepción\nÁrea,Termino\nÁrea,Días Efectivos,Entrega\nProgramada,Cancelacion,Estatus,");
   FRMListado.fSetCampos("0,12,2,3,4,5,6,10,7,8,9,");
   FRMListado.fSetAlinea("left,left,left,center,center,center,center,center,");
   frm.hdBoton.value="Primero";
   aFiltro[0] = [0,"Solicitudes Emitidas (Todas)"];   aFiltro[1] = [1,"Recibidas por el Área"];
   aFiltro[2] = [2,"Pendientes Dentro de Tiempo"];    aFiltro[3] = [3,"Pendientes Despues de Tiempo"];
   aFiltro[4] = [4,"Atendidas Dentro de Tiempo"];     aFiltro[5] = [5,"Atendidas Despues de Tiempo"];
   aFiltro[6] = [6,"Canceladas Dentro de Tiempo"];    aFiltro[7] = [7,"Canceladas Despues de Tiempo"];
   aFiltro[8] = [8,"Improcedentes"];
   fFillSelect(frm.iCveEstado,aFiltro,false,0,0,1);
   frm.iCveEstado.disabled=true;
   var cWhere = "";
   if (fGetPermisos("pg111040018A.js") != 2){
     cWhere = "";
   }
   if (fGetPermisos("pg111040018O.js") != 2){
     cWhere = " GRLOficina.iCveOficina in (select iCveOficina from GRLUSUARIOXOFIC where iCveUsuario = "+top.fGetUsrID()+") ";
   }
   if (fGetPermisos("pg111040018D.js") != 2){
     cWhere = " GRLOficina.iCveOficina in (select iCveOficina from GRLUSUARIOXOFIC where iCveUsuario = "+top.fGetUsrID()+") ";
   }
   if((fGetPermisos("pg111040018D.js") != 2) || (fGetPermisos("pg111040018O.js") != 2) || (fGetPermisos("pg111040018A.js") != 2) ){
     frm.hdFiltro.value = cWhere;
     frm.hdNumReg.value=100000;
     frm.hdOrden.value  = "cDscBreve";
     fEngSubmite("pgGRLOficina.jsp","cIdOficina");
   }
   else fAlert("El usuario ingresado no tiene permisos para realizar la consulta.");
   fDisabled(true,"iCveOficina,iCveDepartamento,dtInicio,dtFin,iCveEstado,")
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
     var iRecibidas=0,iAtendidasDT=0,iAtendidasFT=0,iCanceladasDT=0,iCanceladasFT=0,iVencidas=0,iPendientesDT=0,iPendientesFT=0,iImprocedentes=0;
     //var cMes=frm.iPeriodo.value<10?"0"+frm.iPeriodo.value:frm.iPeriodo.value;
     frm.iTotal.value = aRes.length;
     aResTotal = new Array();
     aResTotal[0] = fCopiaArregloBidim(aRes);
     aResTotal[1]=new Array();
     aResTotal[2]=new Array();
     aResTotal[3]=new Array();
     aResTotal[4]=new Array();
     aResTotal[5]=new Array();
     aResTotal[6]=new Array();
     aResTotal[7]=new Array();
     aResTotal[8]=new Array();
     for(i=0;i<aRes.length;i++){
       // 4.-Generacion V.U.
       // 5.-Recepción Área.
       // 6.-Termino Área.
       // 7.-Entrega Programada.
       // 8.-Cancelación.
       // 11.-Fecha Actual.

       /*
       aFiltro[0] = [0,"Solicitudes emitidas (Todas)"];   aFiltro[1] = [1,"Recibidas por el área"];
       aFiltro[2] = [2,"Pendientes en tiempo"];           aFiltro[3] = [3,"Pendientes despues de tiempo"];
       aFiltro[4] = [4,"concluidas en tiempo"];           aFiltro[5] = [5,"Concluidas despues de tiempo"];
       aFiltro[6] = [6,"Canceladas en tiempo"];           aFiltro[7] = [7,"Canceladas despues de tiempo"];
       aFiltro[8] = [8,"Improcedentes"];*/
       if(aRes[i][5]!="") {
         iRecibidas++;
         aResTotal[1][aResTotal[1].length]=aRes[i];
       }

       if(aRes[i][7]!="" && aRes[i][6]!="" && fComparaFecha(aRes[i][6],aRes[i][7],true)) {
         iAtendidasDT++;
         aResTotal[4][aResTotal[4].length]=aRes[i];
       }
       if(aRes[i][7]!="" && aRes[i][6]!="" && fComparaFecha(aRes[i][7],aRes[i][6],false)) {
         iAtendidasFT++;
         aResTotal[5][aResTotal[5].length]=aRes[i];
       }
       if(aRes[i][7]!="" && aRes[i][4]!="" && aRes[i][6]=="" && aRes[i][8]=="" && fComparaFecha(aRes[i][11],aRes[i][7],true)) {
         iPendientesDT++;
         aResTotal[2][aResTotal[2].length]=aRes[i];
       }
       if(aRes[i][7]!="" && aRes[i][4]!="" && aRes[i][6]=="" && aRes[i][8]=="" && fComparaFecha(aRes[i][7],aRes[i][11],false)) {
         iPendientesFT++;
         aResTotal[3][aResTotal[3].length]=aRes[i];
       }
       //if(aRes[i][7]!="" && aRes[i][5]!="" && fComparaFecha(aRes[i][7],aRes[i][6],false)) iCanceladasFT++;
       if(aRes[i][7]!="" && aRes[i][8]!="" && fComparaFecha(aRes[i][8],aRes[i][7],true)) {
         iCanceladasDT++;
         aResTotal[6][aResTotal[6].length]=aRes[i];
       }
       if(aRes[i][7]!="" && aRes[i][8]!="" && fComparaFecha(aRes[i][7],aRes[i][8],false)) {
         iCanceladasFT++;
         aResTotal[7][aResTotal[7].length]=aRes[i];
       }
       if(aRes[i][7]=="") {
         iImprocedentes++;
         aResTotal[8][aResTotal[8].length]=aRes[i];
       }

       /*{
         iVencidas++;
         aRes[i][7] = "<font color=red>"+aRes[i][7]+"</font>"
       }*/
     }
     frm.iRecibidas.value = iRecibidas;
     frm.iAtendidasFT.value = iAtendidasFT;
     frm.iAtendidasDT.value = iAtendidasDT;
     frm.iCanceladasFT.value = iCanceladasFT;
     frm.iCanceladasDT.value = iCanceladasDT;
     frm.iPendientesDT.value = iPendientesDT;
     frm.iPendientesFT.value = iPendientesFT;
     frm.iImprocedentes.value = iImprocedentes;
     frm.hdRowPag.value = iRowPag;
     frm.iCveEstado.disabled=false;
     FRMListado.fSetListado(aResTotal[frm.iCveEstado.value]);
     frm.iTotal.value=aRes.length;
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
   //fComparaFecha1(aDato[7],aDato[11],false);
 }

 function fImprimir(){
    self.focus();
    window.print();
 }

function fOficinaOnChange(){
  frm.hdFiltro.value = "GRLDeptoXOfic.iCveOficina="+frm.iCveOficina.value;
  var cWhere = "";
  if (fGetPermisos("pg111040018D.js") != 2){
    cWhere = " AND GRLDeptoXOfic.iCveDepartamento in (select iCveDepartamento from GRLUSUARIOXOFIC where iCveUsuario = "+top.fGetUsrID()+") AND GRLDeptoXOfic.iCveOficina = "+frm.iCveOficina.value;
  }
  if (fGetPermisos("pg111040018O.js") != 2){
    cWhere = "";
  }
  if (fGetPermisos("pg111040018A.js") != 2){
    cWhere = "";
  }
  frm.hdFiltro.value += cWhere;
  frm.hdOrden,value  = "cDscBreve";
  fEngSubmite("pgGRLDeptoXOfic.jsp","cIdDepto");
}
function fBuscaDatos(){
  var msg="";
  //if(frm.iEjercicio.value=="") msg+="\nEl campo de Ejercicio es obligatorio.";
  //if(fSoloNumeros(frm.iEjercicio.value)==false) msg+="\nEl campo de Ejercicio solo acepta caracteres numericos.";
  if(frm.iCveOficina.value<=0) msg+="\nEl campo de Oficina es obligatorio.";
  if(frm.iCveDepartamento.value<=0) msg+="\nEl campo de Departamento es obligatorio.";
  if(frm.dtInicio.value=="") msg+="\nEl campo Fecha Inicio es obligatorio.";
  if(frm.dtFin.value=="") msg+="\nEl campo Fecha Fin es obligatorio.";
  if(frm.dtInicio.value.substring(6,10)!=frm.dtFin.value.substring(6,10))
    msg+="La consulta solo permite realizar la busqueda si los parametros corresponden a un solo ejercicio";
  if(msg!="") {
    fAlert(msg);
    return;
  }
  frm.hdLlave.value = "";
  frm.hdSelect.value = "SELECT " +
                       "DISTINCT(R.INUMSOLICITUD),R.IEJERCICIO,T.CCVEINTERNA||' - '|| T.CDSCBREVE AS CDSCTRAMITE,M.CDSCMODALIDAD, " +
                       "DATE(R.TSREGISTRO) AS DTREGSITRO,DATE(ET.TSREGISTRO) AS DTRECIBEAREA, DATE(ET1.TSREGISTRO) AS DTREGFIN,R.DTESTIMADAENTREGA, " +
                       "DATE(TS.DTCANCELACION) AS DTCANCELA,E.CDSCETAPA,(DAYS(DATE(ET1.TSREGISTRO))-DAYS(DATE(R.TSREGISTRO))) AS IDIASRESOLUCION, " +
                       "DATE(CURRENT DATE) as dtActual,r.CDSCBIEN "+
                       "FROM TRAREGSOLICITUD R " +
                       "JOIN TRATRAMITEXOFIC TRO ON TRO.ICVETRAMITE=R.ICVETRAMITE AND TRO.ICVEOFICINA=R.ICVEOFICINA "+
                       "LEFT JOIN TRAREGETAPASXMODTRAM ET2 ON ET2.IEJERCICIO=R.IEJERCICIO AND ET2.INUMSOLICITUD=R.INUMSOLICITUD AND ET2.ICVEETAPA=1 " +
                       "LEFT JOIN TRAREGETAPASXMODTRAM ET ON ET.IEJERCICIO=R.IEJERCICIO AND ET.INUMSOLICITUD=R.INUMSOLICITUD AND ET.ICVEETAPA=2 " +
                       "LEFT JOIN TRAREGETAPASXMODTRAM ET1 ON ET1.IEJERCICIO=R.IEJERCICIO AND ET1.INUMSOLICITUD=R.INUMSOLICITUD AND ET1.ICVEETAPA=7 " +
                       "LEFT JOIN TRAREGTRAMXSOL TS ON TS.IEJERCICIO=R.IEJERCICIO AND TS.INUMSOLICITUD=R.INUMSOLICITUD " +
                       "LEFT JOIN TRACATTRAMITE T ON T.ICVETRAMITE=R.ICVETRAMITE " +
                       "LEFT JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=R.ICVEMODALIDAD " +
                       "LEFT JOIN TRAREGETAPASXMODTRAM EF ON EF.IEJERCICIO=R.IEJERCICIO AND EF.INUMSOLICITUD=R.INUMSOLICITUD " +
                       "LEFT JOIN TRAETAPA E ON EF.ICVEETAPA=E.ICVEETAPA " +
                       "WHERE DATE(R.TSREGISTRO) between '" +frm.dtInicio.value.substring(6,10)+"-"+frm.dtInicio.value.substring(3,5)+"-"+frm.dtInicio.value.substring(0,2)+
                       "'     AND '"+frm.dtFin.value.substring(6,10)+"-"+frm.dtFin.value.substring(3,5)+"-"+frm.dtFin.value.substring(0,2)+"'"+
                       "   AND TRO.ICVEOFICINARESUELVE = " +frm.iCveOficina.value+
                       "   AND TRO.ICVEDEPTORESUELVE = " +frm.iCveDepartamento.value+
                       "   AND EF.IORDEN= " +
                       "(SELECT MAX(IORDEN) FROM TRAREGETAPASXMODTRAM EF1 WHERE EF1.IEJERCICIO=R.IEJERCICIO AND EF1.INUMSOLICITUD=R.INUMSOLICITUD) " +
                       " ORDER BY INUMSOLICITUD ";
  fEngSubmite("pgConsulta.jsp","Listado");
}
function fOnChangeEstado(){
     FRMListado.fSetListado(aResTotal[frm.iCveEstado.value]);
     FRMListado.fShow();
}

function fComparaFecha1(fechaIni, fechaFin, permitirIgual){
  return (eval("parseInt(fGetDateSQL(fechaIni,true),10)" +
               ((permitirIgual)?" <= ":" < ") +
               "parseInt(fGetDateSQL(fechaFin,true),10)"));
}

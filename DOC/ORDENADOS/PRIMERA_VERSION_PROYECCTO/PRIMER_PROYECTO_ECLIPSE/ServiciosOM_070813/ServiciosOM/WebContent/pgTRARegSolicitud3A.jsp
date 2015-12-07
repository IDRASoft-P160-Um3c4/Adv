<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.util.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARegSolicitud3A.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitud1A dTDConsulta = new TDTRARegSolicitud1A();
  TFechas dtFechas = new TFechas();
  Date dFechaActual;
  String cCondicion="";
  String cFecha1="";
  String cFecha2="";
  String cError = "";
  StringBuffer cConsulta = new StringBuffer ("");
  StringBuffer cAnd = new StringBuffer ("");
  StringBuffer cJoin = new StringBuffer ("");
  String cEtapa = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TVDinRep vParametro = oAccion.setInputs("dtFecha1,dtFecha2");
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

  //cCondicion = request.getParameter("cCondicion");
  cFecha1 = vParametro.getString("dtFecha1");
  cFecha2 = vParametro.getString("dtFecha2");
  java.sql.Date dtFechaUno = dtFechas.getDateSQL(cFecha1);
  java.sql.Date dtFechaDos = dtFechas.getDateSQL(cFecha2);
  dFechaActual = dtFechas.TodaySQL();
//  cEtapa = vParametros.getPropEspecifica("EtapaRegistro");
/*  if (cCondicion.equals("PendFT")){
	cAnd.append(" AND TRARegEtapasXModTram.iCveEtapa = "+ cEtapa);
        cAnd.append(" AND        TRARegSolicitud.tsRegistro BETWEEN "+"'"+dtFechaUno.toString()+" 00:00:00' "+" AND "+"'"+dtFechaDos.toString()+" 23:59:00' ");
        cAnd.append(" AND         TRARegSolicitud.dtEstimadaEntrega < "+" '"+dFechaActual.toString()+"'" );
        cJoin.append(" LEFT ");
    }
  if (cCondicion.equals("PendT")){
    	cAnd.append(" AND TRARegEtapasXModTram.iCveEtapa = "+ cEtapa);
        cAnd.append(" AND        TRARegSolicitud.tsRegistro BETWEEN "+"'"+dtFechaUno.toString()+" 00:00:00' "+" AND "+"'"+dtFechaDos.toString()+" 23:59:00' ");
        cAnd.append(" AND         TRARegSolicitud.dtEstimadaEntrega >= "+" '"+dFechaActual.toString()+"'" );
        cJoin.append(" LEFT ");
    }
  if (cCondicion.equals("ResFT")){
    	cAnd.append(" AND TRARegEtapasXModTram.iCveEtapa = "+ cEtapa);
        cAnd.append(" AND        TRARegSolicitud.tsRegistro BETWEEN "+"'"+dtFechaUno.toString()+" 00:00:00' "+" AND "+"'"+dtFechaDos.toString()+" 23:59:00' ");
        cAnd.append(" AND         TRARegSolicitud.dtEstimadaEntrega < "+" TRARegSolicitud.dtEntrega " );
        cJoin.append(" LEFT ");
    }
  if (cCondicion.equals("ResT")){
    	cAnd.append(" AND TRARegEtapasXModTram.iCveEtapa = "+ cEtapa);
  	cAnd.append(" AND        TRARegSolicitud.tsRegistro BETWEEN "+"'"+dtFechaUno.toString()+" 00:00:00' "+" AND "+"'"+dtFechaDos.toString()+" 23:59:00' ");
        cAnd.append(" AND         TRARegSolicitud.dtEstimadaEntrega >= "+" TRARegSolicitud.dtEntrega " );
    	cJoin.append(" LEFT ");
    }
  if (cCondicion.equals("Cancel")){
    	cAnd.append(" AND TRARegEtapasXModTram.iCveEtapa = "+cEtapa); //
       cJoin.append(" ");

    }*/
//  cAnd.append(" AND TRARegSolicitud.tsRegistro BETWEEN "+"'"+dtFechaUno.toString()+" 00:00:00' "+" AND "+"'"+dtFechaDos.toString()+" 23:59:00' ");
//  cAnd.append(" AND TRARegSolicitud.dtEstimadaEntrega >= "+" TRARegSolicitud.dtEntrega " );
//  cJoin.append(" LEFT ");


  cConsulta.append(" SELECT  TRARegSolicitud.iEjercicio,");
  cConsulta.append(" TRARegSolicitud.iNumSolicitud,");
  cConsulta.append(" TRARegSolicitud.tsRegistro,");
  cConsulta.append(" TRARegSolicitud.lResolucionPositiva,");
  cConsulta.append(" TRARegSolicitud.dtEstimadaEntrega,");
  cConsulta.append(" TRARegSolicitud.dtEntrega,");
  cConsulta.append(" TRARegSolicitud.iCveTramite,");
  cConsulta.append(" TRARegSolicitud.iCveModalidad,");
  cConsulta.append(" GRLPersona.crfc,");
  cConsulta.append(" GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno,");
  cConsulta.append(" GRLDepartamento.cDscDepartamento,");
  cConsulta.append(" TRACatTramite.cDscBreve as cDscBreveTramite, ");
  cConsulta.append(" TRAModalidad.cDscModalidad ");
  cConsulta.append(" FROM TRARegSolicitud ");
  cConsulta.append(" LEFT ");
//  cConsulta.append(" "+cJoin.toString()+" ");
  cConsulta.append(" JOIN TRARegTramXSol ON TRARegSolicitud.iEjercicio = TRARegTramXSol.iEjercicio AND TRARegSolicitud.iNumSolicitud = TRARegTramXSol.iNumSolicitud ");
  cConsulta.append(" JOIN GRLPersona ON GRLPersona.iCvePersona = TRARegSolicitud.iCveSolicitante ");
  cConsulta.append(" JOIN TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite ");
  cConsulta.append(" JOIN TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad ");
  cConsulta.append(" JOIN TRARegEtapasXModTram 	ON TRARegEtapasXModTram.iEjercicio = TRARegSolicitud.iEjercicio AND TRARegEtapasXModTram.iNumSolicitud = TRARegSolicitud.iNumSolicitud ");
  cConsulta.append(" JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = TRARegEtapasXModTram.iCveDepartamento ");
  cConsulta.append(oAccion.getCFiltro());
//  cConsulta.append(" "+cAnd.toString());
  cConsulta.append(" AND TRARegEtapasXModTram.iOrden = (select max(RE.iorden) from TRARegEtapasXModTram RE ");
  cConsulta.append("                                   WHERE RE.iEjercicio =  TRARegSolicitud.iEjercicio AND ");
  cConsulta.append("                                   RE.iNumSolicitud = TRARegSolicitud.iNumSolicitud AND RE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE AND ");
  cConsulta.append("                                   RE.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD) ");
  cConsulta.append(" AND TRARegSolicitud.tsRegistro BETWEEN "+"'"+dtFechaUno.toString()+" 00:00:00' "+" AND "+"'"+dtFechaDos.toString()+" 23:59:00' ");
  cConsulta.append(" ORDER BY TRARegSolicitud.iEjercicio, TRARegSolicitud.iNumSolicitud ");

//  System.out.println(cConsulta);
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTDConsulta.findByCustom("iEjercicio,iNumSolicitud", cConsulta.toString());
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
%>
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>');
</script>
<%}%>

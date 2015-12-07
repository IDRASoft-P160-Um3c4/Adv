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
 * <p>Title: pgTRARegSolicitud1A.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ijimenez
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
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

  cCondicion = request.getParameter("cCondicion");
  cFecha1 = request.getParameter("dtFecha1");
  cFecha2 = request.getParameter("dtFecha2");
  java.sql.Date dtFechaUno = dtFechas.getDateSQL(cFecha1);
  java.sql.Date dtFechaDos = dtFechas.getDateSQL(cFecha2);
  dFechaActual = dtFechas.TodaySQL();
  cEtapa = vParametros.getPropEspecifica("EtapaRegistro");
  if (cCondicion.equals("PendFT")){
        cAnd.append(" AND TRARegSolicitud.tsRegistro BETWEEN '"+dtFechaUno.toString()+" 00:00:00' AND '"+dtFechaDos.toString()+" 23:59:59' ")
            .append(" AND TRARegSolicitud.dtEstimadaEntrega < '" + dFechaActual.toString() + "'");
    }
  if (cCondicion.equals("PendT")){
        cAnd.append(" AND TRARegSolicitud.tsRegistro BETWEEN '"+dtFechaUno.toString()+" 00:00:00' AND '"+dtFechaDos.toString()+" 23:59:59' ");
        cAnd.append(" AND TRARegSolicitud.dtEstimadaEntrega >= '"+dFechaActual.toString()+"'" );
    }
  if (cCondicion.equals("ResFT")){
        cAnd.append(" AND TRARegSolicitud.tsRegistro BETWEEN '"+dtFechaUno.toString()+" 00:00:00' AND '"+dtFechaDos.toString()+" 23:59:59' ");
        cAnd.append(" AND TRARegSolicitud.dtEstimadaEntrega < TRARegSolicitud.dtEntrega " );
    }
  if (cCondicion.equals("ResT")){
  	cAnd.append(" AND TRARegSolicitud.tsRegistro BETWEEN '"+dtFechaUno.toString()+" 00:00:00' AND '"+dtFechaDos.toString()+" 23:59:59' ");
        cAnd.append(" AND TRARegSolicitud.dtEstimadaEntrega >= "+" TRARegSolicitud.dtEntrega " );
    }
  if (cCondicion.equals("Cancel")){
  	cAnd.append(" AND TRARegSolicitud.tsRegistro BETWEEN '"+dtFechaUno.toString()+" 00:00:00' AND '"+dtFechaDos.toString()+" 23:59:59' ");
    }
  if (cCondicion.equals("Improcedente")){
  	cAnd.append(" AND TRARegSolicitud.tsRegistro BETWEEN '"+dtFechaUno.toString()+" 00:00:00' AND '"+dtFechaDos.toString()+" 23:59:59' ");
        cAnd.append(" AND (SELECT COUNT(1) FROM TRARegEtapasXModTram REMT WHERE REMT.iEjercicio = TRARegSolicitud.iEjercicio AND REMT.iNumSolicitud = TRARegSolicitud.iNumSolicitud AND REMT.iCveEtapa = " + vParametros.getPropEspecifica("EtapaRecepcion") + ") = 0");
  }



  cConsulta.append(" SELECT TRARegSolicitud.iEjercicio, TRARegSolicitud.iNumSolicitud, TRARegSolicitud.tsRegistro, ")
           .append("        TRARegSolicitud.lResolucionPositiva, TRARegSolicitud.dtEstimadaEntrega, GRLPersona.cRFC, ")
           .append("        GRLPersona.cNomRazonSocial, GRLPersona.cApPaterno, GRLPersona.cApMaterno, ")
           .append("        GRLDepartamento.cDscDepartamento, TRACATTRAMITE.CDSCBREVE, TRAMODALIDAD.CDSCMODALIDAD, ")
           .append("        TRARegsolicitud.dtEntrega, TRARegTramXSol.dtCancelacion, GRLMotivoCancela.cDscMotivo, ")
           .append("        SEGUsuario.cNombre || ' ' || SEGUsuario.cApPaterno || ' ' || SEGUsuario.cApMaterno AS cNomCancela ")
           .append(" FROM TRARegSolicitud ")
           .append("   LEFT JOIN TRARegTramXSol ON TRARegSolicitud.iEjercicio = TRARegTramXSol.iEjercicio ")
           .append(" 	                         AND TRARegSolicitud.iNumSolicitud= TRARegTramXSol.iNumSolicitud ")
           .append("   LEFT JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE ")
           .append("   LEFT JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD ")
           .append("   JOIN GRLPersona ON GRLPersona.iCvePersona = TRARegSolicitud.iCveSolicitante ")
           .append("   JOIN TRARegEtapasXModTram ON TRARegEtapasXModTram.iEjercicio = TRARegSolicitud.iEjercicio ")
           .append("                             AND TRARegEtapasXModTram.iNumSolicitud = TRARegSolicitud.iNumSolicitud ")
           .append("   JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = TRARegEtapasXModTram.iCveDepartamento ")
           .append("   LEFT JOIN SEGUsuario ON SEGUsuario.iCveUsuario = TRARegTramXSol.iCveUsuario ")
           .append("   LEFT JOIN GRLMotivoCancela ON GRLMotivoCancela.iCveMotivoCancela = TRARegTramXSol.iCveMotivoCancela ")
           .append(oAccion.getCFiltro())
           .append(" ").append(cAnd.toString())
           .append(" ORDER BY TRARegSolicitud.iEjercicio, TRARegSolicitud.iNumSolicitud ");


 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTDConsulta.findByCustom("iEjercicio,iNumSolicitud", cConsulta.toString());
  oAccion.setINumReg(vcListado.size());
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

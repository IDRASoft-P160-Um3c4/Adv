<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLDEPTOS.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLDEPTOS</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Enrique Moreno Belmares
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLPais dGRLDEPTOS = new TDGRLPais();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
 
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSQL = "SELECT iEjercicio,iNumSolicitud, ICVEDOCDIG, CDOCUMENTO, CNOMARCHIVO, CCAMPO  FROM INTTRAMITEDOCS where iEjercicio = " + request.getParameter("iEjercicio") +  " AND iNumSolicitud = "+ request.getParameter("iNumSolicitud");
 
  Vector vcListado = dGRLDEPTOS.findByCustom("",cSQL);
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
%>

<%@page import="gob.sct.sipmm.dao.TDGRLPais"%><SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
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

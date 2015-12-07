<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLEntidadFedXLitoral.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad GRLEntidadFedXLitoral</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Abarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLEntidadFedXLitoral dGRLEntidadFedXLitoral = new TDGRLEntidadFedXLitoral();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = dGRLEntidadFedXLitoral.findByCustom("iCvePais,iCveEntidadFed,iCveLitoral",
                     "SELECT GRLEntidadFedXLitoral.iCvePais, " +
                     "GRLPais.cNombre, " +
                     "GRLEntidadFedXLitoral.iCveEntidadFed, " +
                     "GRLEntidadFed.cNombre, " +
                     "GRLEntidadFedXLitoral.iCveLitoral, " +
                     "GRLLitoral.cDscLitoral, " +
                     "GRLEntidadFedXLitoral.iOrden "+
                     "FROM GRLEntidadFedXLitoral " +
                     "join GRLPais on GRLPais.iCvePais = GRLEntidadFedXLitoral.iCvePais " +
                     "JOIN GRLEntidadFed ON GRLEntidadFedXLitoral.iCvePais = GRLEntidadFed.iCvePais " +
                     "AND GRLEntidadFedXLitoral.iCveEntidadFed = GRLEntidadFed.iCveEntidadFed " +
                     "join GRLLitoral on GRLLitoral.iCveLitoral = GRLEntidadFedXLitoral.iCveLitoral " +
                     oAccion.getCFiltro() + oAccion.getCOrden());
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

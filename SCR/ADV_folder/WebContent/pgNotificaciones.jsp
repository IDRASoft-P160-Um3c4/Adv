<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgIMPIMPRESORA.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad IMPIMPRESORA</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: SCT </p>
 * @author JESR
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLPais dPais = new TDGRLPais();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSQL = "SELECT " +
  "IEJERCICIO, " +
  "INUMSOLICITUD, " +
  "ICVEDOCDIG, " +
  "TSREGISTRO, " +
  "CDOCUMENTO, " +
  "CNOMARCHIVO, " +
  "CCAMPO, " +
  "ICVEUSUNOTIFICA, " +
  "TSREGNOTIFICA, " +
  "CTIPO, " +
  "COBSERVACION, " +
  "SEGUSUARIO.CUSUARIO || ' - ' || SEGUSUARIO.CNOMBRE || ' ' || SEGUSUARIO.CAPPATERNO || ' ' || SEGUSUARIO.CAPMATERNO as cUsuario, " +
  "INTTRAMITEDOCS.ICVEESTATUS, cDscEstatus " +  
  "FROM INTTRAMITEDOCS " +
  "LEFT JOIN SEGUSUARIO ON INTTRAMITEDOCS.ICVEUSUNOTIFICA = SEGUSUARIO.ICVEUSUARIO " +
  "LEFT JOIN INTESTATUS on INTTRAMITEDOCS.ICVEESTATUS = INTESTATUS.ICVEESTATUS " +
  oAccion.getCFiltro()+
  oAccion.getCOrden();
 
  Vector vcListado = dPais.findByCustom("",cSQL);
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

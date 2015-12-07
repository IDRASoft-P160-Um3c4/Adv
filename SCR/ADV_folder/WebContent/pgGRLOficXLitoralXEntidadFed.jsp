<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLOficXLitoralXEntidadFed.jsp</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLEntidadFedXLitoral dGRLEntidadFedXLitoral = new TDGRLEntidadFedXLitoral();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLEntidadFedXLitoral.findByCustom("iCveLitoral",
"SELECT lit.iCveLitoral, lit.cDscLitoral, lit.lVigente, OFI.cDscBreve, " +
" OFI.iCveOficina, EF.CNOMBRE " +
" FROM GRLLitoral lit " +
" JOIN GRLEntidadFedXLitoral efxl ON efxl.iCveLitoral = lit.iCveLitoral " +
" JOIN GRLOficina OFI ON OFI.iCvePais = efxl.iCvePais " +
"   AND OFI.iCveEntidadFed = efxl.iCveEntidadFed " +
" JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = OFI.ICVEPAIS AND OFI.ICVEENTIDADFED = EF.ICVEENTIDADFED " +
" JOIN MYRCAPITANIA CAP ON CAP.ICVEOFICINA = OFI.ICVEOFICINA "+
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLMUNICIPIO.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLMUNICIPIO</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author JESR
 * @version 1.0
 */
  //TLogger.setSistema("10");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLMUNICIPIO dGRLMUNICIPIO = new TDGRLMUNICIPIO();
  Vector vcKeys = new Vector();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */

  TVDinRep vParametro = oAccion.setInputs("hdFiltroMun,hdOrdenMun");
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSQL = "Select ICVEPAIS,ICVEENTIDADFED,ICVEMUNICIPIO,CNOMBRE,CABREVIATURA from GRLMUNICIPIO where " + vParametro.getString("hdFiltroMun") + " order by " + vParametro.getString("hdOrdenMun");

  Vector vcListado = dGRLMUNICIPIO.findByCustom("ICVEPAIS",cSQL);
  oAccion.setINumReg(500);
  oAccion.navega(vcListado);
  PageBeanScroller bs = oAccion.getBs();
  String cNavStatus = oAccion.getCNavStatus();
  String cDato;
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

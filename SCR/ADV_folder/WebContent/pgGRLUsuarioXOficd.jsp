<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLUsuarioXOfic.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLUsuarioXOfic</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLUsuarioXOfic dGRLUsuarioXOfic = new TDGRLUsuarioXOfic();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{


 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  vDinRep = oAccion.setInputs("iCveEntidad");
  String iCveEntidad = vDinRep.getString("iCveEntidad") ;
  //System.out.println("iCveEntidad: "+iCveEntidad);
  Vector vcListado = dGRLUsuarioXOfic.findByCustom("GRLUsuarioXOfic.iCveOficina,GRLUsuarioXOfic.iCveDepartamento,GRLUsuarioXOfic.iCveUsuario","Select " +
  " GRLUsuarioXOfic.iCveOficina, GRLOficina.cDscBreve" +
  " from GRLUsuarioXOfic " +
  " join SEGUsuario ON SEGUsuario.iCveUsuario = 1 " +
  " join GRLOficina ON GRLOficina.iCveOficina = GRLUsuarioXOfic.iCveOficina " +
    " AND GRLOficina.iCveEntidadFed = " + iCveEntidad + " "+ oAccion.getCFiltro() + oAccion.getCOrden());
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











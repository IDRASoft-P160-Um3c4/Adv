<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLOficeXUser.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLOficina</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLOficina dGRLOficina = new TDGRLOficina();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  String cUsuario = "";
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
   /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

    String cSQL =
    " SELECT of.iCveOficina, of.cDscOficina "+
    " FROM GRLOficina of ";

    String cFiltro = "";
    if(!(new TDGRLUsuarioXOfic().checkUserByOffice(vUsuario.getICveusuario()))){
       cFiltro =
       " JOIN GRLUsuarioXOfic uxo ON uxo.iCveOficina = of.iCveOficina "+
                               " AND uxo.iCveUsuario = "+vUsuario.getICveusuario();
    }
    else{
      cUsuario = "oficina 1";
    }

    Vector vcListado = dGRLOficina.findByCustom("iCveOficina", cSQL + cFiltro);
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
                '<%=oAccion.getBPK()%>',
                '<%=cUsuario%>');
</script>
<%}%>

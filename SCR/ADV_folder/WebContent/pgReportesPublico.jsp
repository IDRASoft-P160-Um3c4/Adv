<%@ page import="java.util.*" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.util.*" %>

<%@ page import="java.lang.*" %>

<%
/**
 * <p>Title: pgMuestraFormato.jsp</p>
 * <p>Description: JSP que se encarga de mostrar los reportes al público en general</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Rafael Miranda Blumenkron
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;

  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  try{
%>
<html>
  <head>
    <title>REPORTES DISPONIBLES AL PÚBLICO EN GENERAL</title>
  </head>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/componentes.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/valida_nt.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/imagenes.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>pgReportesPublico.js"></SCRIPT>
  <body bgcolor='DEEFF7' onLoad="fOnLoad();">
    <link rel='stylesheet' href='<%=vParametros.getPropEspecifica("URLEstaticos")%>estilos/estilos.css' TYPE='text/css'>

    <FORM name="frmDespliega" enctype="text/html" method="POST" action="" target="">
      <table ID="mainTable" width="100%" class="ETablaInfo">
        <tr><td>&nbsp;</td></tr>
      </table>
    </form>
  </body>
</html>
<%
  } catch(Exception e){
    e.printStackTrace();
  }
%>

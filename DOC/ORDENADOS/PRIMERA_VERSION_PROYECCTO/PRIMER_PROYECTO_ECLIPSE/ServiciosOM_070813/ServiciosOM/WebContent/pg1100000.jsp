<%@ page import="com.micper.ingsw.*"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.text.*" %>
<%
  try{
      request.getSession(true).removeAttribute("UsrID");
      request.getSession(true).removeAttribute("MenuUsuario");
      request.getSession(true).removeAttribute("PermisosUsuario");
  }catch (Exception exc){}
  TParametro  vParametros = new TParametro("44");
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/componentes.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/frmmi.js"></SCRIPT>
<script language="JavaScript">
  var cPagNva='pg1100002.js';
  fPag();
</script>


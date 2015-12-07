<%@ page import="com.micper.ingsw.*"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.text.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>

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


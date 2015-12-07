<%@ page import="com.micper.ingsw.*"%><%TParametro  vParametros = new TParametro("44");%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT><SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/componentes.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")+""+request.getParameter("cPagina")%>"></SCRIPT><script language="JavaScript">var cPagNva='<%=request.getParameter("cPagNva")%>'; var cRFC='<%=request.getParameter("cRFC")%>';var cUA='<%=request.getParameter("cUA")%>';fPag();</script>


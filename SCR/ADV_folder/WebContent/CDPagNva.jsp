<%@ page import="com.micper.ingsw.*"%><%TParametro  vParametros = new TParametro("44");%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT><SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/componentes.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")+""+request.getParameter("cPagina")%>"></SCRIPT><script language="JavaScript">var cPagNva='<%=request.getParameter("cPagNva")%>';fPag();</script>


<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>

<%@ page import="gob.sct.sipmm.dao.*" %>

<%

  TLogger.setSistema("44");
  TParametro vParametros = new TParametro("44");

  TVDinRep vDinRep;
  TDTRARegSolicitudA1 dTRARegSolicitud = new TDTRARegSolicitudA1();
  Vector vcListado = new Vector();
  
  String cRFCparam = request.getParameter("cRFC");
  String modo="error";
  
  if(cRFCparam!=null){
	  vcListado = dTRARegSolicitud.findByCustom("", "SELECT COUNT (ICVEPERSONA) ICUENTA FROM GRLPERSONA WHERE CRFC ='"+cRFCparam+"'");
	  vDinRep = (TVDinRep) vcListado.get(0);
	  
	  if(vDinRep.getInt("ICUENTA")>1)
		modo="rfcRepetido";
	  else
		  modo="";
  }    
  
 %>
 
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/componentes.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")+""+request.getParameter("cPagina")%>"></SCRIPT>

<script language="JavaScript">
	var cPagNva='<%=request.getParameter("cPagNva")%>'; 
	var cRFC='<%=request.getParameter("cRFC")%>';
	var cUA='<%=request.getParameter("cUA")%>';
	var modoInt = '<%=modo%>';
	
	fPag();
</script>




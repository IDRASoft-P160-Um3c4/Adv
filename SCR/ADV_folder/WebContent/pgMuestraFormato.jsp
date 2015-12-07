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
 * <p>Description: JSP que se encarga de recibir valores y armar tabla dinámica con campos definidos en el formato</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Rafael Miranda Blumenkron
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;

  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  Vector vDatos = new Vector();
  TDGRLConfigFormato dConfigFormato = new TDGRLConfigFormato();
  String cFormatos = request.getParameter("iCveFormato");
  String cTituloPantalla = request.getParameter("cDscTituloFormato");
  if(cTituloPantalla == null)
    cTituloPantalla = "";
  if(cFormatos == null)
    cFormatos = "";
  String[] aFormatos = cFormatos.split(",");
  try{
    String cSQL = "SELECT F.ICVEFORMATO, F.CDSCFORMATO, F.CTITULO, F.LVIGENTE, F.DTINIVIGENCIA, "+
                  "F.CTABLABUSCA, F.CCAMPOLLAVEBUSCA, F.INUMCOLUMNAS, CF.ICVEATRIBUTO, CF.IORDEN, "+
                  "CF.CETIQUETAANTERIOR, CF.CDSCATRIBUTO, CF.CETIQUETAPOSTERIOR, CF.ICVETIPORESPUESTA, CF.LOBLIGATORIO, "+
                  "CF.CVALORINIOMISION, CF.CVALORFINOMISION, CF.IMAXLONGITUD, CF.CNOMCAMPOPANTALLA, CF.CTABLAMAPEO, "+
                  "CF.CCAMPOMAPEO, CF.CJSVALIDACIONES, CF.LVIGENTE, CF.IRENGLON, CF.ICOLUMNA, "+
                  "TR.CDSCTIPORESPUESTA, CAT.ICVEATRIBUTONIVELANTERIOR, CAT.CTABLA, CAT.CCAMPOLLAVE, CAT.CCAMPOORDEN, "+
                  "CAT.CJSPCONTROLADOR "+
                  "FROM GRLFORMATO F "+
                  "JOIN GRLCONFIGFORMATO CF ON CF.ICVEFORMATO = F.ICVEFORMATO "+
                  "JOIN GRLTIPORESPUESTA TR ON TR.ICVETIPORESPUESTA = CF.ICVETIPORESPUESTA "+
                  "LEFT JOIN GRLCONFIGCATFORMATO CAT ON CAT.ICVEFORMATO = F.ICVEFORMATO AND CAT.ICVEATRIBUTO = CF.ICVEATRIBUTO "+
                  "WHERE F.ICVEFORMATO = ";
    String cSQL2 = "  AND CF.ICVEATRIBUTO > 0 "+
                   "  AND CF.LVIGENTE = 1 "+
                  "ORDER BY CF.IRENGLON, CF.ICOLUMNA, CF.IORDEN";
%>
<html>
  <head>
    <title><%=cTituloPantalla%></title>
  </head>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/componentes.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/valida_nt.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/imagenes.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>pgMuestraFormato.js"></SCRIPT>
<%
    for(int i=0; i<aFormatos.length; i++){
      vDatos = (Vector)dConfigFormato.findByCustom("iCveFormato", cSQL + aFormatos[i] + cSQL2);
      oAccion.setINumReg(vDatos.size());
      oAccion.navega(vDatos);
%>
    <script language="JavaScript">
<%    if(i==0){ %>
      iNumFormatos = <%= aFormatos.length %>;
      cTituloGrlFormato = "<%= cTituloPantalla%>";
<%    }%>
      aRes = new Array();
      <% out.println(oAccion.getArrayCD()); %>
      aResTotal[<%=i%>] = new Array();
      aResTotal[<%=i%>] = fCopiaArregloBidim(aRes);
      function fEstadoCampos<%=aFormatos[i]%>(){
      }
    </script>
    <script LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>pgMuestraFormato<%=aFormatos[i]%>.js"></SCRIPT>
<%  }%>
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

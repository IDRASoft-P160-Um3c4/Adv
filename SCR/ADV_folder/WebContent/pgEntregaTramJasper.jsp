<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.util.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>

<%
/**
 * <p>Title: pgCBBRuta.jsp</p>
 * <p>Description: abre el reporte de entrega de trámites</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: U.T.I.C. S.C.T.  </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  String dataSourceName = vParametros.getPropEspecifica("ConDBModulo");
  DbConnection dbConn =  new DbConnection(dataSourceName);
  Connection conn = null;
  TReportes Reportes = new TReportes("44");
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
    if(oAccion.getCAccion().equals("ReporteEntrega")){
      TVDinRep vDinRep = oAccion.setInputs("cSolicitudesEnviar,iEjercicio,");
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);

      Map parameters = new HashMap();

      parameters.put("cFiltroSol", vDinRep.getString("cSolicitudesEnviar"));
      if(!vDinRep.getString("cSolicitudesEnviar").equals(null) && !vDinRep.getString("cSolicitudesEnviar").equals("")){
        File reportFile = new File(application.getRealPath("Reportes\\AcuseEntrega.Jasper"));

        byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath (), parameters, conn);
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream ouputStream = response.getOutputStream();
        ouputStream.write(bytes, 0, bytes.length);

        ouputStream.flush();
        ouputStream.close();
        //StringBuffer sbObjeto = Reportes.creaPDFObject();
        //out.println(sbObjeto);
      }
    }
    conn.close();
  }
  %>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
    <script language="JavaScript">
    <%
      Vector vcListado = new Vector();
      TVDinRep vListado = new TVDinRep();
      vListado.put("Respuesta","OK");
      out.print(oAccion.getArrayCD());
    %>
      fEngResultado('<%=request.getParameter("cNombreFRM")%>','<%=request.getParameter("cId")%>','<%=""%>');
      <%//System.out.print("*****     "+request.getParameter("cNombreFRM"));%>
      <%//System.out.print("*****     "+request.getParameter("cId"));%>
    </script>

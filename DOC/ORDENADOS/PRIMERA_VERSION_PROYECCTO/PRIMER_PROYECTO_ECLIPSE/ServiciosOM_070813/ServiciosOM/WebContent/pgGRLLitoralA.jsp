<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLLitoral.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLLitoral</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Adriana Hernandez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLLitoral dGRLLitoral = new TDGRLLitoral();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  int iCveOf = 0;
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{


  if(request.getParameter("iCveOficina") != null && Integer.parseInt(""+request.getParameter("iCveOficina")) > 0 )
	     iCveOf =  Integer.parseInt(""+request.getParameter("iCveOficina"));
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String selectDatos =
      " SELECT LIT.ICVELITORAL, LIT.CDSCLITORAL "+
      " FROM GRLOFICINA OFI "+
      " INNER JOIN GRLENTIDADFEDXLITORAL EFL ON EFL.ICVEENTIDADFED = OFI.ICVEENTIDADFED "+
      " INNER JOIN GRLLITORAL LIT ON LIT.ICVELITORAL = EFL.ICVELITORAL "+
      " WHERE OFI.ICVEOFICINA = " +iCveOf;
   System.out.println("selectDatosLITORALA===="+selectDatos+"---");
//      " Select iCveLitoral,cDscLitoral,lVigente from GRLLitoral "
 //     + oAccion.getCFiltro() + oAccion.getCOrden() ;
  Vector vcListado = dGRLLitoral.findByCustom("ICVELITORAL",selectDatos);
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

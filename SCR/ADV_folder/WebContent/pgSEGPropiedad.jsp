<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgVEHEmbarcacionA6.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad VEHEmbarcacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Enrique Moreno Belmares
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  /*String tresValores[] = new String [3];  
  tresValores[0]=vParametros.getPropEspecifica("ValVolumen");
  tresValores[1]="20";
  tresValores[2]="21";*/
	  
  String cParLongitud = vParametros.getPropEspecifica("ValLongitud");
  String cParPotencia = vParametros.getPropEspecifica("ValPotencia");
  String cParVolumen  = vParametros.getPropEspecifica("ValVolumen") + "," + vParametros.getPropEspecifica("ValArqBruto") + "," + vParametros.getPropEspecifica("ValArqNeto");
  String cParCantidad = vParametros.getPropEspecifica("ValCantidad");
  String cParPeso     = vParametros.getPropEspecifica("ValPeso");
  String cParVelocidad = vParametros.getPropEspecifica("ValVelocidad");
  String cParMoneda = vParametros.getPropEspecifica("ValMoneda");
  String cParUAB = vParametros.getPropEspecifica("ValUAB");
  String cPaisDef = vParametros.getPropEspecifica("PaisDef");

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = null;
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
                '<%=cParLongitud%>',
                '<%=cParPotencia%>',
                '<%=cParPeso%>',
                '<%=cParVelocidad%>',
                '<%=cParMoneda%>',              
                '<%=cParVolumen%>');
</script>
<%}%>

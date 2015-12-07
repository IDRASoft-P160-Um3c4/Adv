<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="gob.sct.sipmm.dao.reporte.*" %>

<%
/**
 * <p>Title: pgGRLSegtoEntidad.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLReporteXFolio</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDDPOPMDP dGRLReporteXFolio = new TDDPOPMDP();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 String cSQL = "SELECT " +
		"REP.CNOMREPORTE as cNomRep, " +//0
		"FOL.CASUNTO as cAsun, " +//1
		"REPF.IEJERCICIOFOLIO as iEjerFolio, " +//2
		"REPF.ICVEOFICINA as iOficina, " +//3
		"REPF.ICVEDEPARTAMENTO as iDepto, " +//4
		"REPF.CDIGITOSFOLIO as cDigFolio, " +//5
		"REPF.ICONSECUTIVO as iCons, " +//6
		"FOL.DTASIGNACION as dtAsig, " +//7
		"FOL.DTCANCELACION as dtCancel, " +//8
                "FOL.CNUMOFICIALIAPARTES as cNumOficPartes " +//9
		"FROM GRLREPORTEXFOLIO REPF " +
		"JOIN GRLREPORTE REP on REPF.ICVESISTEMA = rep.ICVESISTEMA " +
		"AND REPF.ICVEMODULO = REP.ICVEMODULO " +
		"AND REPF.INUMREPORTE = REP.INUMREPORTE " +
		"JOIN GRLFOLIO FOL ON REPF.IEJERCICIOFOLIO  = FOL.IEJERCICIO " +
		"AND REPF.ICVEOFICINA = FOL.ICVEOFICINA " +
		"AND REPF.ICVEDEPARTAMENTO = FOL.ICVEDEPARTAMENTO " +
		"AND REPF.CDIGITOSFOLIO = FOL.CDIGITOSFOLIO " +
		"AND REPF.ICONSECUTIVO = FOL.ICONSECUTIVO ";

  Vector vcListado = dGRLReporteXFolio.findByCustom("IEJERCICIOFOLIO,ICVEOFICINA,ICVEDEPARTAMENTO,CDIGITOSFOLIO,ICONSECUTIVO",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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

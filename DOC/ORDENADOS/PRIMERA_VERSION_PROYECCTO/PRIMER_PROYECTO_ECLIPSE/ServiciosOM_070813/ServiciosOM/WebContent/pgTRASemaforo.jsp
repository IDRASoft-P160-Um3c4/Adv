<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgVEHMotor.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad VEHMotor</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Enrique Moreno Belmares
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRASemaforo dSemaforo = new TDTRASemaforo();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

  vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,dtInicioTra,dtFinTra");
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cQuery1 = "SELECT " +
                   "	S.IEJERCICIO,S.INUMSOLICITUD,T.CCVEINTERNA||' - '||T.CDSCBREVE as cTramite ,M.CDSCMODALIDAD, " +
                   "	E.CDSCETAPA,S.TSREGISTRO AS TSREGSOLICITUD,ET.TSREGISTRO AS TSULTIMAETAPA,S.DTESTIMADAENTREGA " +
                   "FROM TRAREGSOLICITUD S " +
                   "JOIN TRAREGETAPASXMODTRAM ET ON ET.IEJERCICIO=S.IEJERCICIO AND ET.INUMSOLICITUD=S.INUMSOLICITUD AND ET.IORDEN = ( " +
                	 "	SELECT MAX(IORDEN) FROM TRAREGETAPASXMODTRAM WHERE IEJERCICIO=ET.IEJERCICIO AND INUMSOLICITUD=ET.INUMSOLICITUD) " +
                	 "JOIN TRAETAPA E ON E.ICVEETAPA=ET.ICVEETAPA " +
                	 "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=ET.ICVETRAMITE " +
                	 "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=ET.ICVEMODALIDAD " +
                	 "LEFT join TRAREGETAPASXMODTRAM E1 ON E1.IEJERCICIO=S.IEJERCICIO AND E1.INUMSOLICITUD=S.INUMSOLICITUD AND E1.ICVEETAPA=1 " +
                	 "LEFT join TRAREGETAPASXMODTRAM E8 ON E8.IEJERCICIO=S.IEJERCICIO AND E8.INUMSOLICITUD=S.INUMSOLICITUD AND E8.ICVEETAPA=8 "+
                	 "  LEFT JOIN TRAREGETAPASXMODTRAM E2 ON E2.IEJERCICIO=S.IEJERCICIO AND E2.INUMSOLICITUD=S.INUMSOLICITUD AND E2.ICVEETAPA=2 ";
  TDGRLPais pais = new TDGRLPais();
  Vector vcListado = pais.findByCustom("iCveVehiculo,iCveMarcaMotor",cQuery1 + oAccion.getCFiltro() + oAccion.getCOrden());
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

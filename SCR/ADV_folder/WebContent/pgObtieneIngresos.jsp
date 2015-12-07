<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgACCCAUSA.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad ACCCAUSA</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Daniel Ramos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("10");
  TVDinRep vDinRep;
  TDGRLPais dACCCAUSA = new TDGRLPais();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

 
 
  String cSQL  = "SELECT " +
          "	RC.IEJERCICIO,RC.IREFNUMERICAINGRESOS,RC.DTINIVIGENCIA, " +
          "	RC.DTFINVIGENCIA,CTM.LPAGOANTICIPADO,CTM.ICVECONCEPTO, " +
          "	RC.DIMPORTESINAJUSTE,RC.DIMPORTEAJUSTADO,C.CDSCCONCEPTO, " +
          "	CTM.ICVETRAMITE,CTM.ICVEMODALIDAD,T.CDSCBREVE, " +
          "	M.CDSCMODALIDAD " +
          "FROM TRAREFERENCIACONCEPTO RC " +
          "JOIN TRACONCEPTOPAGO C ON RC.ICVECONCEPTO = C.ICVECONCEPTO " +
          "JOIN TRACONCEPTOXTRAMMOD CTM ON CTM.IEJERCICIO=RC.IEJERCICIO AND CTM.ICVECONCEPTO=RC.ICVECONCEPTO " +
          "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=CTM.ICVETRAMITE " +
          "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=CTM.ICVEMODALIDAD " +
          "WHERE RC.IEJERCICIO=  YEAR(current date) " +
          " AND CTM.ICVETRAMITE=" + request.getParameter("iCveTramiteTmp") +
          " AND CTM.ICVEMODALIDAD=" + request.getParameter("iCveModalidadTmp") +
          " AND RC.DTINIVIGENCIA <= CURRENT DATE " +
          " AND RC.DTFINVIGENCIA > CURRENT DATE " +
          " ORDER BY RC.DIMPORTESINAJUSTE ";
  Vector vcListado = dACCCAUSA.findByCustom("",cSQL);
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

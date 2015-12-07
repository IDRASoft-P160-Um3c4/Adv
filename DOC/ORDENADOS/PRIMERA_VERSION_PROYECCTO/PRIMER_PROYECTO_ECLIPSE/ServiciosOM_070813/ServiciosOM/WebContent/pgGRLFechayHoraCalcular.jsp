<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.util.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgCISAgenda.jsp</p>
 * <p>Description: JSP "QUERY" de la entidad CISCitas para la construcción de la AGENDA</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author JESR
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TFechas tfSol = new TFechas("44");
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  java.sql.Date dtHoy = new java.sql.Date(new java.util.Date().getTime());
  java.sql.Date dtUnAnio = new java.sql.Date(new java.util.Date().getTime());
  String cDmy = tfSol.getFechaDDMMYYYY(dtHoy,"/");
  TFechas f = new TFechas();
  dtUnAnio = f.aumentaDisminuyeDias(dtHoy,364);
  String cUnAnio   = tfSol.getFechaDDMMYYYY(dtUnAnio,"/");

  GregorianCalendar gTime = new GregorianCalendar();
  Date dtHora = gTime.getTime();
  String cHora = ""+dtHora;

  String [] cConjunto = cHora.split(" ");
  // 0 = "dd/mm/aaaa"; 1 =  [dd,mm,aaaa]; 2 = 'Formato SPN With Day'
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
   aRes[0]='<%=cDmy%>';
   aRes[1]=[<%=cDmy.substring(0,2)%>,<%=cDmy.substring(3,5)%>,<%=cDmy.substring(6)%>];
   aRes[2]='<%=tfSol.getDateSPNWithDay(dtHoy)%>';
   aRes[3]=[<%=cConjunto[3].substring(0,2)%>,<%=cConjunto[3].substring(3,5)%>];
   aRes[4]='<%=cUnAnio%>';
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>');
</script>



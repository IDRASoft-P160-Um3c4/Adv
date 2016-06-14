<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.util.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="gob.sct.ingresos.TEncriptaDatos" %>

<%
/**
 * <p>Title: pgObtieneDatosPaginaIngresos.jsp</p>
 * <p>Description: JSP Para la obtención de datos para la integración de la página insertable de ingresos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TEncriptaDatos eDatos = new TEncriptaDatos();

  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));

  String cTraIngresosURL      = vParametros.getPropEspecifica("TRAIngresosURL");
  String cTRAIngresosNumBytes = vParametros.getPropEspecifica("TRAIngresosNumBytes");
  String cTRAIngresosCifrado  = vParametros.getPropEspecifica("TRAIngresosCifrado");
  String cAreaRecaudadora     = vParametros.getPropEspecifica("WSIngRecauda");
  String cURLRespuesta        = vParametros.getPropEspecifica("TRAURLRespuesta");

  System.out.println("****** VALORES PARA INVOCAR LA HOJA DE PAGO - INGRESOS ************");
  
  System.out.println("cTraIngresosURL ->"+cTraIngresosURL);
  System.out.println("cTRAIngresosNumBytes ->"+cTRAIngresosNumBytes);
  System.out.println("cTRAIngresosCifrado ->"+cTRAIngresosCifrado);
  System.out.println("cAreaRecaudadora ->"+cAreaRecaudadora);
  System.out.println("cURLRespuesta ->"+cURLRespuesta);
  System.out.println("iCveUsuario ->"+request.getParameter("iCveUsuario"));
  System.out.println("hdUsrIngresos ->"+request.getParameter("hdUsrIngresos"));
  System.out.println("hdPassUsrIngresos ->"+request.getParameter("hdPassUsrIngresos"));
  
  eDatos.setLengthAndKey(Integer.parseInt(cTRAIngresosNumBytes),cTRAIngresosCifrado);
  String cIdUsrIngresos = eDatos.getCifrado(request.getParameter("iCveUsuario"));  
  String chdUsrIngresos = eDatos.getCifrado(request.getParameter("hdUsrIngresos"));
  String chdPassUsrIngresos = eDatos.getCifrado(request.getParameter("hdPassUsrIngresos"));

%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
   aRes[0]='<%=cTraIngresosURL%>';
   aRes[1]='<%=cTRAIngresosNumBytes%>';
   aRes[2]='<%=cTRAIngresosCifrado%>';
   aRes[3]='<%=cAreaRecaudadora%>';
   aRes[4]='<%=cIdUsrIngresos%>';
   aRes[5]='<%=chdUsrIngresos%>';
   aRes[6]='<%=chdPassUsrIngresos%>';
   aRes[7]='<%=cURLRespuesta%>';
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>');
</script>



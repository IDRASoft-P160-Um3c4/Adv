<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.util.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.reporte.*" %>
<%@ page import="com.micper.sql.*" %>
<body onLoad="JavaScript:fDocCargado();">
<%
/**
 * <p>Title: pgReporteGeneral.jsp</p>
 * <p>Description: JSP para generar un reporte</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Rafael Miranda Blumenkron
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;

  TReportes Reportes = new TReportes("44");
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  StringBuffer sbResultado = new StringBuffer("Reporte PDF"),
               sbObjeto    = new StringBuffer("");
  Object oDatos;
  HashMap hParametros = new HashMap();
  //Si esta configurado que necesita folio par2 se le asigna el folio, par3 se le asigna la cve de oficina  y par 4 se le asigna la cve de depto
  try{
    if(request.getParameter("iNumParametros") != null){
      String cTemp = "";
      for(int i=0; i<=Integer.parseInt(request.getParameter("iNumParametros"),10); i++){
        cTemp = request.getParameter("Param" + i);
        if(cTemp != null){
          String[] aTemp = cTemp.replace('|',',').split(",");
          if(aTemp[0].equalsIgnoreCase("Integer")) hParametros.put(aTemp[1], new Integer(aTemp[2]));
          else if(aTemp[0].equalsIgnoreCase("String")) hParametros.put(aTemp[1], aTemp[2]);
          else hParametros.put(aTemp[1], aTemp[2]);
        }
      }
    }
  }catch(Exception ex){ex.printStackTrace();  cError = ex.getMessage(); }
  if (sbResultado == null || sbResultado.toString().equals(""))
    cError = "No hay datos para desplegar en el reporte";

  if (cError.equals("")){
    request.getSession().setAttribute("cNombreReporte",request.getParameter("cNombreReporte"));
    request.getSession().setAttribute("hashParametros", hParametros);
    sbObjeto = Reportes.creaPDFObject();
  }
  Vector vcListado = new Vector();
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();

%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
%>
  function fDocCargado(){
//    top.FRMCuerpo.fProcesoActual("");
//    fEngResultado('<%=request.getParameter("cNombreFRM")%>','CIdDocCargado','','','','');
//    top.FRMCuerpo.fSetIntervalo(false);
  }

//  top.FRMCuerpo.fProcesoActual("Resultado recibido");
//  top.FRMCuerpo.fSetIntervalo(true);

//  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
//                '<%=request.getParameter("cId")%>',
//                '<%=cError%>',
//                '<%=cNavStatus%>',
//                '<%=oAccion.getIRowPag()%>',
//                '<%=oAccion.getBPK()%>');
//  top.FRMCuerpo.fProcesoActual("Enviando datos a la aplicación");
</script>
</body>

<%
  out.println(sbObjeto);
}%>



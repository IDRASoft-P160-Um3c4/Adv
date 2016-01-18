<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgPERTRAMITE.jsp</p> 
 * <p>Description: JSP "Proceso" de la entidad INTTRAMXCAMPO</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: S.C.T.</p>
 * @author aLopez
 * @version 1.0
 */
  TLogger.setSistema("11");
  TParametro  vParametros = new TParametro("11");
  TVDinRep vDinRep;
  TDINTTRAMITES dCampo = new TDINTTRAMITES();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String cFiltro;

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
String cSQL =  " SELECT " +
               "	  TD.IEJERCICIO, " +
               "	  TD.INUMSOLICITUD, " +
               "	  tc.IORDEN, " +
               "	  TD.CCAMPO, " +
               "	  TD.CVALOR1, " +
               "	  TD.CVALOR2, " +
               "	  tc.CCARPETA, " +
               "	  C.CETIQUETA, " +
               "  	C.CCAMPOCOP " +
               " FROM INTTRAMITEDETALLE TD " +
               "  join TRAREGSOLICITUD S ON S.IEJERCICIO=TD.IEJERCICIO AND TD.INUMSOLICITUD=S.INUMSOLICITUD "+
               " JOIN INTCAMPOS C ON TD.CCAMPO=C.CCAMPO " +
               " JOIN INTTRAMXCAMPO TC ON TC.ICVECAMPO=C.ICVECAMPO AND S.ICVETRAMITE=TC.ICVETRAMITE AND S.ICVEMODALIDAD=TC.ICVEMODALIDAD" +
               oAccion.getCFiltro() + 
               oAccion.getCOrden();
  //System.out.print("*****      INTCampoXCarp:     *****\n\n"+cSQL+"\n");
  Vector vcListado = dCampo.findByCustom("ICVETRAMITE",cSQL);
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

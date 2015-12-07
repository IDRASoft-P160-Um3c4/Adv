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
  TVDinRep vDinRep2;
  TDINTTRAMITES dPERTRAMITE = new TDINTTRAMITES();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String cFiltro;

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  cFiltro = oAccion.getCFiltro().trim();

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
String cSQL =  " SELECT "+
                              "iCveTramite, "+ //0
                              "INTTRAMITES.iCveTipoPermisiona,  "+ //1
                              "INTTRAMITES.iCveTipoTramite,  "+ //2
                              "TSRegistro,  "+ //3
                              "TSFin,  "+ //4
                              "iCveDepartamento,  "+ //5
                              "iCveDeptoIntegra,  "+ //6
                              "TRATIPOTRAMITE.cDscTipoTramite, "+ //7
                              "INTTIPOTRAMPER.iCveTipoTraProd "+ //8
                              "FROM INTTRAMITES "+
                              "JOIN INTTIPOTRAMPER ON INTTRAMITES.iCveTipoPermisiona = INTTIPOTRAMPER.iCveTipoPermisiona "+
                              "AND INTTRAMITES.iCveTipoTramite    = INTTIPOTRAMPER.iCveTipoTramite "+
                              "JOIN TRATIPOTRAMITE ON INTTIPOTRAMPER.iCveTipoTraProd = TRATIPOTRAMITE.iCveTipoTramite "+
                              oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dPERTRAMITE.findByCustom("ICVETRAMITE",cSQL);
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLReporte.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLReporte</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Rafael Miranda Blumenkron
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLReporte dGRLReporte = new TDGRLReporte();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  Vector vcListado = null;
  try{
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
    vcListado = dGRLReporte.findByCustom("iCveSistema,iCveModulo,iNumReporte",
    "SELECT " +
    "  GRLReporte.iCveSistema, " + //0
    "  GRLReporte.iCveModulo AS iModulo, " +
    "  GRLReporte.iNumReporte, " +
    "  GRLReporte.cNomReporte, " +
    "  GRLReporte.lExcel, " +
    "  GRLReporte.lWord, " + //5
    "  GRLReporte.lPDF, " +
    "  GRLReporte.lMultiPart, " +
    "  GRLReporte.cPlantillaExcel, " +
    "  GRLReporte.cPlantillaWord, " +
    "  GRLReporte.cPlantillaPDF, " +   //10
    "  GRLReporte.cDAOEjecutar, " +
    "  GRLReporte.cMetodoExcel, " +
    "  GRLReporte.cMetodoWord, " +
    "  GRLReporte.cMetodoPDF, " +
    "  GRLReporte.iCveFormatoFiltro, " +   //15
    "  GRLReporte.lAutoImprimir, " +
    "  GRLReporte.iNumCopias, " +
    "  GRLReporte.lMostrarAplicacion, " +
    "  GRLReporte.lParametroModificable, " +
    "  GRLModulo.iCveModulo AS iMModulo, " +   //20
    "  GRLModulo.cDscModulo, " +
    "  GRLFormato.cDscFormato, " +
    "  GRLFormato.cTitulo, " +
    "  GRLFormato.lVigente, " +
    "  GRLReporte.lRequiereFolio " + //25
    "FROM GRLReporte " +
    "  JOIN GRLModulo ON GRLReporte.iCveSistema = GRLModulo.iCveSistema AND GRLReporte.iCveModulo = GRLModulo.iCveModulo " +
    "  LEFT JOIN GRLFormato ON GRLReporte.iCveFormatoFiltro = GRLFormato.iCveFormato "
     + oAccion.getCFiltro() + oAccion.getCOrden()
    );
    oAccion.setINumReg(vcListado.size());
    oAccion.navega(vcListado);
  }catch(Exception e){
  }
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT>
<script language="JavaScript">
  var aRes = new Array();
<%
   out.print(oAccion.getArrayCD());
%>
  window.parent.aResRep = fCopiaArregloBidim(aRes);
  window.parent.fResultado(aRes,"Listado","");
</script>

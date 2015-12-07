<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRASolicitudRel.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRASolicitudRel</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLIngresos dIngresos = new TDGRLIngresos();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  vDinRep = oAccion.setInputs("iEjercicio,iCategoriaIngresos");
  if(oAccion.getCAccion().equals("Guardar")){
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSQL = "SELECT T.DTVIGENCIA,C.ICVECONCEPTO,CA.IREFERENCIA,T.DTARIFACAJUSTE AS DCAJUSTE,T.DTARIFASAJUSTE AS DSAJUSTE, " +
                "C.CDSCCONCEPTO,C.ICVECATEGORIA,C.LPORCENTAJE,C.LTARIFA " +
                "FROM INGCONCEPTO C " +
                "JOIN INGTARIFA T ON T.IEJERCICIO=C.IEJERCICIO AND T.ICVECATEGORIA=C.ICVECATEGORIA AND T.ICVECONCEPTO=C.ICVECONCEPTO " +
                "JOIN INGCONCEPTOXAREA CA ON CA.IEJERCICIO=C.IEJERCICIO AND CA.ICVECATEGORIA=C.ICVECATEGORIA AND CA.ICVECONCEPTO=C.ICVECONCEPTO " +
                "JOIN INGAREAREC A ON A.ICVEAREAREC=CA.ICVEAREAREC " +
                "WHERE C.IEJERCICIO="+vDinRep.getInt("iEjercicio")+" AND CA.ICVEAREAREC=10 ";
  Vector vcListado = dIngresos.findByCustom("",cSQL);
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

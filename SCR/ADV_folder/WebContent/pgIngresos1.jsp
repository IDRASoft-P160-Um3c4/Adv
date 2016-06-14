
<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.ws.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgIngresos1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad CPATitulo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep = new TVDinRep();
  TDIngresos dIngresos = new TDIngresos();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 // Vector vcListado = new Vector();
    vDinRep = oAccion.setInputs("iEjercicio,iCategoriaIngresos,iCveConcepto1,iTipoRef");
 System.out.println(vDinRep.getInt("iEjercicio")+"  "+vDinRep.getInt("iNumSolicitud")+"  "+vDinRep.getInt("iCveConcepto")+"  "+vDinRep.getInt("iTipoRef"));
    Vector vcListado = dIngresos.findTarifaConcepto(vDinRep.getInt("iEjercicio"),vDinRep.getInt("iCategoriaIngresos"),vDinRep.getInt("iCveConcepto1"),vDinRep.getInt("iTipoRef"));
    System.out.println("*****     Ejercicio: "+vDinRep.getInt("iEjercicio")+"\n          Categoria: "+vDinRep.getInt("iCategoriaIngresos")+"\n          Concepto: "+vDinRep.getInt("iCveConcepto1")+"\n          TipoRef: "+vDinRep.getInt("iTipoRef"));

  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   System.out.println("**++++++++oAccion.getArrayCD()..."+oAccion.getArrayCD());
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.excepciones.*" %>
<%
/**
 * <p>Title: pgTRARegEtapasXModTram.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegEtapasXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String cError = "";
  TDGRLIngresos objIng = new TDGRLIngresos();
  TDConsulta objConsulta = new TDConsulta();
  TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID"))){
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  }else{

  String cSQL = "SELECT CUSUARIO, CPASSWORD FROM SEGUSUARIO WHERE ICVEUSUARIO = "+ vUsuario.getICveusuario();
 
  Vector vcListado = objConsulta.findByCustom("", cSQL);
  Vector vcListIng = new Vector();
  
  try{
  if(vcListado.size()>0){
	  String cSQLIng = "SELECT ICVEUSUARIO FROM SEGUSUARIO WHERE CUSUARIO = "+ vUsuario.getCPassword() +" AND CPASSWORD="+ vUsuario.getCPassword();
	  vcListIng = objIng.findByCustom("", cSQLIng);
	  
	  if(vcListIng!=null && vcListIng.size()>0){
	  	vDinRep = (TVDinRep) vcListIng.get(0);
	  	//System.out.println(vDinRep.getInt("ICVEUSUARIO"));
	  }
  }
  }catch(Exception e){
	  cError="validaUsrIngADVErr";
  }
  
  oAccion.navega(vcListIng);
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

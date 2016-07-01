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
 Vector vcListIng = null;
 String cNavStatus = "";
 String cError = "";
 CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
 TParametro  vParametros = new TParametro("44");

 try{
  TLogger.setSistema("44");
  TVDinRep vDinRep;

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID"))){
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  }else{

  TDConsulta objConsulta = new TDConsulta();
  TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  
  String cSQL = "SELECT CUSUARIO, CPASSWORD FROM SEGUSUARIO WHERE ICVEUSUARIO = "+ vUsuario.getICveusuario(); 
  
  Vector vcListado = objConsulta.findByCustom("", cSQL);
  
  if(vcListado.size()>0){
    TDIngresosResult tdInResult = TDIngresos.ConsultaUsuarioIngresos(vUsuario.getCUsuario(), vUsuario.getCPassword());      
    vcListIng = tdInResult.getResult();
    cError = tdInResult.getCError();
  }  
  }
    oAccion.navega(vcListIng);
    cNavStatus = oAccion.getCNavStatus();
  } catch(Exception e){
    cError = "Se sucitó el siguiente error: " + e.getMessage();
    oAccion.navega(null);
    cNavStatus = oAccion.getCNavStatus();   
  }
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
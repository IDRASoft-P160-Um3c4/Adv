<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgSEGUSUARIO.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad SEGUSUARIO</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author JESR
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDSEGUSUARIOCHG dSEGUSUARIO = new TDSEGUSUARIOCHG();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("ICVEUSUARIO,CPASSWORD,CVERPASSWORD");
    try{
      Vector vcSegUsu = dSEGUSUARIO.findByCustom("","Select ICVEUSUARIO from "+
                                                     "SEGUSUARIO WHERE ICVEUSUARIO = "+vDinRep.getInt("ICVEUSUARIO") +
                                                     " AND CPASSWORD = '"+vDinRep.getString("CPASSWORD")+"'");
      if(vcSegUsu.size() > 0)
         vDinRep = dSEGUSUARIO.update(vDinRep,null);
      else
         cError = "NoUser";
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '',
                '',
                '');
</script>
<%}%>

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAModalidad.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAModalidad</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAModalidad dTRAModalidad = new TDTRAModalidad();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDscModalidad,lVigente");
    try{
      vDinRep = dTRAModalidad.insert(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveModalidad,cDscModalidad,lVigente");
    try{
      vDinRep = dTRAModalidad.update(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveModalidad");
    try{
       dTRAModalidad.delete(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = new Vector();
  if (oAccion.getCAccion().equals("GetModalidadesTramite")){
    try{
      vcListado = dTRAModalidad.findByCustom("iCveModalidad","SELECT DISTINCT(M.iCveModalidad), M.cDscModalidad, M.lVigente FROM TRAModalidad M JOIN TRAConfiguraTramite CT ON CT.iCveModalidad = M.iCveModalidad " + oAccion.getCFiltro() + oAccion.getCOrden());
      if (vcListado != null)
        oAccion.setINumReg(vcListado.size());
    }catch(Exception e){
      cError=e.getMessage();
    }
  }else{
    try{
      vcListado = dTRAModalidad.findByCustom("iCveModalidad","Select iCveModalidad,cDscModalidad,lVigente from TRAModalidad " + oAccion.getCFiltro() + oAccion.getCOrden());
    }catch(Exception e){
      cError=e.getMessage();
    }
  }
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

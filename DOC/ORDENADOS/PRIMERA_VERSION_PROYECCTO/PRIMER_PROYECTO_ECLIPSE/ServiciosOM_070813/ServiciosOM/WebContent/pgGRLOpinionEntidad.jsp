<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLOpinionEntidad.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLOpinionEntidad</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLOpinionEntidad dGRLOpinionEntidad = new TDGRLOpinionEntidad();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("lEsTramite,lEsOpinionInterna,iCveTramite,iCveModalidad,iCveOficinaUsr,iCveDeptoUsr,lVigente");
    try{
      vDinRep = dGRLOpinionEntidad.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOpinionEntidad,iCveOficinaUsr,iCveDeptoUsr,lVigente");
    try{
      vDinRep = dGRLOpinionEntidad.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOpinionEntidad");
    try{
       dGRLOpinionEntidad.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

String cSQL = "Select iCveOpinionEntidad,lEsTramite,lEsOpinionInterna,iCveTramite,iCveModalidad, "+
              "iCveSistema,iCveModulo,GRLOpinionEntidad.iCveOficinaOpn,GRLOpinionEntidad.iCveDepartamentoOpn, "+
              "iCvePersona,iCveDomicilio,GRLOpinionEntidad.lVigente,cDscOficina,cDscDepartamento "+
              "from GRLOpinionEntidad "+
              "JOIN GRLOficina ON GRLOficina.iCveOficina = GRLOpinionEntidad.iCveOficinaOpn "+
              "JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLOpinionEntidad.iCveDepartamentoOpn ";
  Vector vcListado = dGRLOpinionEntidad.findByCustom("iCveOpinionEntidad",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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

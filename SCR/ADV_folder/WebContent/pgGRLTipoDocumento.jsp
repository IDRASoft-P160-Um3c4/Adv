<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLTipoDocumento.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLTipoDocumento</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLTipoDocumento dGRLTipoDocumento = new TDGRLTipoDocumento();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  int iCveSistema = 0;
  int iCveModulo = 0;
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
    if (request.getParameter("iCveSistema") != null)
      if (request.getParameter("iCveSistema").toString().compareTo("") != 0)
       iCveSistema = Integer.parseInt(request.getParameter("iCveSistema"));
    if (request.getParameter("iCveModulo") != null)
      if (request.getParameter("iCveModulo").toString().compareTo("") != 0)
       iCveModulo = Integer.parseInt(request.getParameter("iCveModulo"));

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDscTipoDocumento,lActivo");
    try{
      vDinRep = dGRLTipoDocumento.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTipoDocumento,cDscTipoDocumento,lActivo");
    try{
      vDinRep = dGRLTipoDocumento.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTipoDocumento");
    try{
       dGRLTipoDocumento.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLTipoDocumento.findByCustom("iCveTipoDocumento",
  " Select GRLTipoDocumento.iCveTipoDocumento, " +
  " GRLTipoDocumento.cDscTipoDocumento, " +
  " GRLTipoDocumento.lActivo " +
  " from GRLTipoDocumento " +
  " join GRLModTipoDocumento on GRLModTipoDocumento.iCveSistema = " + iCveSistema +
  " and GRLModTipoDocumento.iCveModulo  = " + iCveModulo +
  " and GRLModTipoDocumento.iCveTipoDocumento = GRLTipoDocumento.iCveTipoDocumento " +
  oAccion.getCFiltro() + oAccion.getCOrden());
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

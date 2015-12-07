<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="com.micper.excepciones.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLConfigDespBusca.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLConfigDespBusca</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Leopoldo Beristain González
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLConfigDespBusca dGRLConfigDespBusca = new TDGRLConfigDespBusca();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveFormatoAux,iOrden,cNomTabla,cNomCampo,cEtiquetaAnterior,iCveTipoRespuesta,cFormato,cAlineacion,cJoinTabla,cJoinCampo");
    try{
      vDinRep = dGRLConfigDespBusca.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveFormatoAux,iCveAtributo,iOrden,cNomTabla,cNomCampo,cEtiquetaAnterior,iCveTipoRespuesta,cFormato,cAlineacion,cJoinTabla,cJoinCampo");
    try{
      vDinRep = dGRLConfigDespBusca.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveAtributo");
    try{
       dGRLConfigDespBusca.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = null;
  try{
     vcListado = dGRLConfigDespBusca.findByCustom("iCveAtributo",
     						  "Select GRLCDB.iCveFormato,"+
                                                         "GRLFMT.cTitulo,"+
                                                         "GRLCDB.iCveAtributo,"+
                                                         "GRLCDB.iOrden,"+
                                                         "GRLCDB.cNomTabla,"+
                                                         "GRLCDB.cNomCampo,"+
                                                         "GRLCDB.cEtiquetaAnterior,"+
                                                         "GRLCDB.iCveTipoRespuesta,"+
                                                         "GRLTR.cDscTipoRespuesta,"+
                                                         "GRLCDB.cFormato,"+
                                                         "GRLCDB.cAlineacion,"+
                                                         "GRLCDB.cJoinTabla,"+
                                                         "GRLCDB.cJoinCampo "+
                                                    "from GRLConfigDespBusca GRLCDB "+
                                                    "join GRLFormato GRLFMT on GRLCDB.iCveFormato = GRLFMT.iCveFormato "+
                                                    "join GRLTipoRespuesta GRLTR on GRLCDB.iCveTipoRespuesta = GRLTR.iCveTipoRespuesta "+
                                                    oAccion.getCFiltro() + oAccion.getCOrden());
  }catch(DAOException ex){
    if(!ex.getMessage().equals(""))
         cError="Datos";
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

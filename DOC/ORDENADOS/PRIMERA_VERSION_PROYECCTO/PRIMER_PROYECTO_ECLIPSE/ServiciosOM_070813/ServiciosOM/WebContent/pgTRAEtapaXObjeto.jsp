<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAEtapaXObjeto.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAEtapaXObjeto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAEtapaXObjeto dTRAEtapaXObjeto = new TDTRAEtapaXObjeto();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveSistema,iCvePrograma,cObjeto,iOrden,iCveTramite,iCveModalidad,iCveEtapa,lVigente");
    try{
      vDinRep = dTRAEtapaXObjeto.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveSistema,iCvePrograma,cObjeto,iConsecutivo,iOrden,iCveTramite,iCveModalidad,iCveEtapa,lVigente");
    try{
      vDinRep = dTRAEtapaXObjeto.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveSistema,iCvePrograma,cObjeto,iConsecutivo");
    try{
       dTRAEtapaXObjeto.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSql = "Select TRAEtapaXObjeto.iCveSistema,TRAEtapaXObjeto.iCvePrograma,cObjeto,iConsecutivo,iOrden," +
                "TRAEtapaXObjeto.iCveTramite,TRAEtapaXObjeto.iCveModalidad,TRAEtapaXObjeto.iCveEtapa,TRAEtapaXObjeto.lVigente,cDscBreve,cDscModalidad," +
                "cDscSistema,cDscPrograma,cDscEtapa " +
                "from TRAEtapaXObjeto " +
                "join TRACatTramite on TRAEtapaXObjeto.iCveTramite = TRACatTramite.iCveTramite " +
                "join TRAModalidad on TRAEtapaXObjeto.iCveModalidad = TRAModalidad.iCveModalidad " +
                "join SEGSistema on TRAEtapaXObjeto.iCveSistema = SEGSistema.iCveSistema " +
                "join SEGPrograma on TRAEtapaXObjeto.iCvePrograma = SEGPrograma.iCvePrograma and TRAEtapaXObjeto.iCveSistema = SEGPrograma.iCveSistema " +
                "join TRAEtapa on TRAEtapaXObjeto.iCveEtapa = TRAEtapa.iCveEtapa " +
                 oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dTRAEtapaXObjeto.findByCustom("iCveSistema,iCvePrograma,cObjeto,iConsecutivo",cSql);
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

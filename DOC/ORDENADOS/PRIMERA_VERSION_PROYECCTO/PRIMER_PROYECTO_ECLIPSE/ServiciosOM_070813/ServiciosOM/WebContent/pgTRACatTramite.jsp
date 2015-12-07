<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>

<%
/**
 * <p>Title: pgTRACatTramite.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRACatTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRACatTramite dTRACatTramite = new TDTRACatTramite();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cCveInterna,cDscTramite,cDscBreve,lReqFirmaDigital,lVigente");
    try{
      vDinRep = dTRACatTramite.insert(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,cCveInterna,cDscTramite,cDscBreve,lReqFirmaDigital,lVigente");
    try{
      vDinRep = dTRACatTramite.update(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite");
    try{
       dTRACatTramite.delete(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = new Vector();
  if (oAccion.getCAccion().equals("GetTramites")){
    try{
      vcListado = dTRACatTramite.findByCustom("iCveTramite","SELECT iCveTramite, cCveInterna, cDscTramite, cDscBreve, lReqFirmaDigital, lVigente, cCveInterna || ' - ' || cDscBreve AS cCveDesc, cBienBuscar, '' AS cFuncionEjecutar, SUBSTR(ccveinterna,1,3) AS cInicioCve FROM TRACatTramite WHERE lVigente = 1 ORDER BY cInicioCve, cDscBreve ");
      if (vcListado != null)
        oAccion.setINumReg(vcListado.size());
    }catch(Exception e){
      cError=e.getMessage();
    }
  }else{
    try{
      vcListado = dTRACatTramite.findByCustom("iCveTramite","Select distinct(TRACatTramite.iCveTramite),TRACatTramite.cCveInterna,TRACatTramite.cDscTramite,TRACatTramite.cDscBreve,TRACatTramite.lReqFirmaDigital,TRACatTramite.lVigente, TRACatTramite.cBienBuscar, '' AS cFuncionEjecutar from TRACatTramite,TRAConfiguraTramite " + oAccion.getCFiltro() + oAccion.getCOrden());
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

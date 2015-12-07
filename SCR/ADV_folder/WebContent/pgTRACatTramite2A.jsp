<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>

<%
/**
 * <p>Title: pgTRACatTramite2A.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRACatTramite incluyendo los campos para generación de arbol</p>
 * <p>Copyright: Copyright (c) 2006 </p>
 * <p>Company: INFOTEC </p>
 * @author ISM
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRACatTramite2A dTRACatTramite = new TDTRACatTramite2A();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cCveInterna,cDscTramite,cDscBreve,lReqFirmaDigital,lVigente,cBienBuscar,cObjTramite,cComprobante,cNotas,iCveTramite2,lTramiteFinal,lImprimeManual,iCveUsuario");	
    try{
      vDinRep = dTRACatTramite.insert(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,cCveInterna,cDscTramite,cDscBreve,lReqFirmaDigital,lVigente,cBienBuscar,cObjTramite,cComprobante,cNotas,iCveTramite2,lTramiteFinal,lImprimeManual,iCveUsuario");
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
      vcListado = dTRACatTramite.findByCustom("iCveTramite","SELECT iCveTramite, cCveInterna, cDscTramite, cDscBreve, lReqFirmaDigital, lVigente, cCveInterna || ' - ' || cDscBreve AS cCveDesc,cBienBuscar, ICVETRAMITEPADRE,  LTRAMITEFINAL FROM TRACatTramite WHERE lVigente = 1 ");
      if (vcListado != null)
        oAccion.setINumReg(vcListado.size());
    }catch(Exception e){
      cError=e.getMessage();
    }
  }else{
    try{
      vcListado = dTRACatTramite.findByCustom("iCveTramite","Select " +
      "TRACatTramite.iCveTramite," + //0
      "TRACatTramite.cCveInterna," + //1
      "TRACatTramite.cDscTramite," + //2
      "TRACatTramite.cDscBreve," +   //3
      "TRACatTramite.lReqFirmaDigital," + //4
      "TRACatTramite.lVigente," +    //5
      "TRACatTramite.CCVEINTERNA,"+  //6
      "TRACatTramite.cBienBuscar,"+  //7
      "TRACatTramite.cObjetivoTramite, "+  //8
      "TRACatTramite.cComprobante, "+//9
      "TRACatTramite.cNotas,       "+//10 cComprobante,cNotas
      "TRACatTramite.ICVETRAMITEPADRE, "+//11 id del padre de este tramite
      "TRACatTramite.LTRAMITEFINAL, "+//12 si es un tramite final
      "TRACatTramite.LIMPRIMEMANUAL "+
      "from TRACatTramite " + oAccion.getCFiltro() + oAccion.getCOrden());
    }catch(Exception e){
      cError=e.getMessage();
    }
  }
  oAccion.navega(vcListado);
  oAccion.setINumReg(vcListado.size());
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

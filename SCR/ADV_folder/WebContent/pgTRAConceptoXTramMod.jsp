<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAConceptoXTramMod.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAConceptoXTramMod</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAConceptoXTramMod dTRAConceptoXTramMod = new TDTRAConceptoXTramMod();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto1");
    try{
      vDinRep = dTRAConceptoXTramMod.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto,lPagoAnticipado");
    try{
      vDinRep = dTRAConceptoXTramMod.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Cambia")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto,cConjunto");
    try{
      vDinRep = dTRAConceptoXTramMod.Cambia(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto");
    try{
       dTRAConceptoXTramMod.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSql = "Select TRAConceptoXTramMod.iEjercicio,TRAConceptoXTramMod.iCveTramite,TRAConceptoXTramMod.iCveModalidad,TRAConceptoXTramMod.iCveConcepto,lPagoAnticipado," +
                "cDscBreve,cDscModalidad,cDscConcepto,TRAREFERENCIACONCEPTO.IREFNUMERICAINGRESOS, max(TRAREFERENCIACONCEPTO.DTINIVIGENCIA) " +
                "from TRAConceptoXTramMod " +
                "join TRACatTramite on TRAConceptoXTramMod.iCveTramite = TRACatTramite.iCveTramite " +
                "join TRAModalidad on TRAConceptoXTramMod.iCveModalidad = TRAModalidad.iCveModalidad " +
                "join TRAConceptoPago on TRAConceptoXTramMod.iCveConcepto = TRAConceptoPago.iCveConcepto " +
                "join TRAREFERENCIACONCEPTO on TRAREFERENCIACONCEPTO.IEJERCICIO = TRACONCEPTOXTRAMMOD.IEJERCICIO and TRAREFERENCIACONCEPTO.ICVECONCEPTO = TRACONCEPTOXTRAMMOD.ICVECONCEPTO "+
                oAccion.getCFiltro() + oAccion.getCOrden()+
                " GROUP BY TRAREFERENCIACONCEPTO.IEJERCICIO, TRAREFERENCIACONCEPTO.ICVECONCEPTO,TRACONCEPTOXTRAMMOD.iEjercicio,TRAConceptoXTramMod.iCveTramite,"+
                " TRAConceptoXTramMod.iCveModalidad,TRAConceptoXTramMod.iCveConcepto,lPagoAnticipado,"+
                " cDscBreve,cDscModalidad,cDscConcepto,TRAREFERENCIACONCEPTO.IREFNUMERICAINGRESOS";
  Vector vcListado = dTRAConceptoXTramMod.findByCustom("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto",cSql);
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

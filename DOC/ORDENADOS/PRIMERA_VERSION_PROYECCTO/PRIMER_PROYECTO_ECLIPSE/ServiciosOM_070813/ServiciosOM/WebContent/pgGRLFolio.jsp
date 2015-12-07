<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLFolio.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLFolio</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLFolio dGRLFolio = new TDGRLFolio();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDigitosFolio,dtAsignacion,iCveUsuAsigna,cDirigidoA,cAsunto,cTitularFirma,dtEnvio,dtRecepcion,cNumOficialiaPartes,cNumControlGestion,dtCancelacion,iCveUsuCancela,cMotivoCancela,iCveOficina,iCveDepartamento");
    try{
      vDinRep = dGRLFolio.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,cDigitosFolio,iConsecutivo,dtAsignacion,iCveUsuAsigna,cDirigidoA,cAsunto,cTitularFirma,dtEnvio,dtRecepcion,cNumOficialiaPartes,cNumControlGestion,dtCancelacion,iCveUsuCancela,cMotivoCancela,iCveOficina,iCveDepartamento");
    try{
      vDinRep = dGRLFolio.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,cDigitosFolio,iConsecutivo");
    try{
       dGRLFolio.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }


 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLFolio.findByCustom("iEjercicio,cDigitosFolio,iConsecutivo",
  "SELECT " +
  "  GRLFolio.iEjercicio, " + //0
  "  GRLFolio.cDigitosFolio, " +
  "  GRLFolio.iConsecutivo, " +
  "  GRLFolio.dtAsignacion, " +
  "  GRLFolio.iCveUsuAsigna, " +
  "  GRLFolio.cDirigidoA, " + //5
  "  GRLFolio.cAsunto, " +
  "  GRLFolio.cTitularFirma, " +
  "  GRLFolio.dtEnvio, " +
  "  GRLFolio.dtRecepcion, " +
  "  GRLFolio.cNumOficialiaPartes, " + //10
  "  GRLFolio.cNumControlGestion, " +
  "  GRLFolio.dtCancelacion, " +
  "  GRLFolio.iCveUsuCancela, " +
  "  GRLFolio.cMotivoCancela, " +
  "  GRLFolio.iCveOficina, " + //15
  "  GRLFolio.iCveDepartamento, " +
  "  GRLOficina.cDscOficina, " +
  "  GRLOficina.cDscBreve, " +
  "  GRLDepartamento.cDscDepartamento, " +
  "  GRLDepartamento.cDscBreve " + //20
  "FROM GRLFolio " +
  "  JOIN GRLOficina ON GRLFolio.iCveOficina = GRLOficina.iCveOficina " +
  "  JOIN GRLDepartamento ON GRLFolio.iCveDepartamento = GRLDepartamento.iCveDepartamento " +
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

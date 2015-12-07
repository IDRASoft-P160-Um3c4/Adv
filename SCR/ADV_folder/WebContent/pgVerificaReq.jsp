<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgVerificaReq.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Abarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegReqXTram dTRARegReqXTram = new TDTRARegReqXTram();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String cEtapasRestringidas = vParametros.getPropEspecifica("EtapaConcluidoArea") + "," +
  vParametros.getPropEspecifica("EtapaVerificaRequisito") + "," + vParametros.getPropEspecifica("EtapaEntregarVU") + "," +
  vParametros.getPropEspecifica("EtapaEntregarOficialia") + "," + vParametros.getPropEspecifica("EtapaRecibeResolucion") + "," +
  vParametros.getPropEspecifica("EtapaEntregaResol") + "," + vParametros.getPropEspecifica("EtapaDocRetorno") + "," +
  vParametros.getPropEspecifica("EtapaConclusionTramite") + "," + vParametros.getPropEspecifica("EtapaPNCNotificado") + "," +
  vParametros.getPropEspecifica("EtapaResEnviadaOficialia") + "," + vParametros.getPropEspecifica("EtapaTramiteCancelado");
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,iIdDocDigitalizado");
    try{
      vDinRep = dTRARegReqXTram.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,iIdDocDigitalizado");
    try{
      vDinRep = dTRARegReqXTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito");
    try{
       dTRARegReqXTram.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRARegReqXTram.findByCustom("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito ",
  "SELECT TRAREGREQXTRAM.iEjercicio,TRAREGREQXTRAM.iNumSolicitud,TRAREGREQXTRAM.iCveTramite,TRAREGREQXTRAM.iCveModalidad, "+
  "TRAREGREQXTRAM.iCveRequisito,TRAREGREQXTRAM.iEjercicioFormato,TRAREGREQXTRAM.iCveFormatoReg,TRAREGREQXTRAM.dtRecepcion,TRAREGREQXTRAM.iCveUsuRecibe  " +
  " FROM  TRAREGREQXTRAM " +
  "  JOIN TRAREQXMODTRAMITE ON TRAREGREQXTRAM.ICVETRAMITE = TRAREQXMODTRAMITE.ICVETRAMITE " +
  "  AND TRAREGREQXTRAM.ICVEMODALIDAD = TRAREQXMODTRAMITE.ICVEMODALIDAD " +
  "  AND TRAREGREQXTRAM.ICVEREQUISITO = TRAREQXMODTRAMITE.ICVEREQUISITO " +
  "  AND TRAREQXMODTRAMITE.LREQUERIDO = 1 " + oAccion.getCFiltro() + oAccion.getCOrden());
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
                '<%=oAccion.getBPK()%>',
                '<%=cEtapasRestringidas%>');
</script>
<%}%>

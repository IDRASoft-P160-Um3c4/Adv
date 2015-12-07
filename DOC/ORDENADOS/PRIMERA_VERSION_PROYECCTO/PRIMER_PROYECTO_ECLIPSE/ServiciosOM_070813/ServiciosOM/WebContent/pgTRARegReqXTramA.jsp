    <%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARegReqXTramA.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegReqXTram dTRARegReqXTram = new TDTRARegReqXTram();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,dtNotificacion,cNumOficio,dtOficio,lValido");
    try{
      vDinRep = dTRARegReqXTram.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,dtNotificacion,cNumOficio,dtOficio,lValido");
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
    vDinRep = oAccion.setInputs("iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito");
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
 String cSQL = "Select TRAREGREQXTRAM.INUMSOLICITUD, TRAREGREQXTRAM.IEJERCICIO, TRAREGREQXTRAM.ICVETRAMITE, TRAREGREQXTRAM.ICVEMODALIDAD, "+
		"TRAREGREQXTRAM.ICVEREQUISITO, DTRECEPCION, SEGUSUARIO.CUSUARIO as usuario, DTNOTIFICACION, CNUMOFICIO, DTOFICIO, LVALIDO,TRAREQUISITO.CDSCBREVE, "+
		"SEGUSUARIO.CNOMBRE || ' ' || SEGUSUARIO.CAPPATERNO || ' ' || SEGUSUARIO.CAPMATERNO AS NOMBRE, "+
                "TRACatTramite.cCveInterna, TRACatTramite.cDscBreve AS cDscTramite, TRAModalidad.cDscModalidad "+
                " from TRARegReqXTram "+
		"join SEGUSUARIO on SEGUSUARIO.ICVEUSUARIO = TRAREGREQXTRAM.ICVEUSURECIBE "+
		"join TRAREGSOLICITUD ON TRAREGSOLICITUD.IEJERCICIO = TRAREGREQXTRAM.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD "+
		"join TRAREQXMODTRAMITE on TRAREQXMODTRAMITE.ICVETRAMITE = TRAREGREQXTRAM.ICVETRAMITE and TRAREQXMODTRAMITE.ICVEMODALIDAD = TRAREGREQXTRAM.ICVEMODALIDAD and TRAREQXMODTRAMITE.ICVEREQUISITO = TRAREGREQXTRAM.ICVEREQUISITO "+
		"join TRAREQUISITO on TRAREQUISITO.ICVEREQUISITO = TRAREQXMODTRAMITE.ICVEREQUISITO "+
                "join TRACatTramite ON TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite "+
                "join TRAModalidad ON TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad ";

  Vector vcListado = dTRARegReqXTram.findByCustom("iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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

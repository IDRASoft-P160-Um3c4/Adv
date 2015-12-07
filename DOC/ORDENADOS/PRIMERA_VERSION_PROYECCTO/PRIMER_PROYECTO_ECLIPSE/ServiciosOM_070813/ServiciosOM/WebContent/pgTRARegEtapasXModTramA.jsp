<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARegEtapasXModTram.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegEtapasXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TVDinRep vDinRep2;
  TDTRARegEtapasXModTram dTRARegEtapasXModTram = new TDTRARegEtapasXModTram();
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  String cFiltroUsr = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  int iCveUsuario = 0;
  int iEtapaModifTram,iEtapaVerifica, iEtapaRecepcion, iEtapaNotificacion = 0;
  int iDeptoVentanillaUnica = 95;

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID"))){

    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  }else{

   if (vUsuario != null){
      iCveUsuario = vUsuario.getICveusuario();

    }

    cFiltroUsr = " and GRLUsuarioXOfic.iCveUsuario = " + request.getParameter("iCveUsuario");

  iEtapaVerifica = Integer.parseInt(vParametros.getPropEspecifica("EtapaVerificaRequisito"));
  iEtapaRecepcion = Integer.parseInt(vParametros.getPropEspecifica("EtapaRecepcion"));//
  iEtapaNotificacion = Integer.parseInt(vParametros.getPropEspecifica("EtapaPNCNotificado"));
  iDeptoVentanillaUnica = Integer.parseInt(vParametros.getPropEspecifica("DeptoVentanillaUnica"));
  iEtapaModifTram = Integer.parseInt(vParametros.getPropEspecifica("EtapaModificarTramite"));
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){

    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iCveUsuario,lAnexo");
    vDinRep2 = oAccion.setInputs("iEjercicio,iNumSolicitud,lResolucion");
    try{
      vDinRep = dTRARegEtapasXModTram.insert(vDinRep,null);

      vDinRep2 = dTRARegSolicitud.updEtapas(vDinRep2,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCveOficina,iCveDepartamento,iCveUsuario,tsRegistro");
    try{
      vDinRep = dTRARegEtapasXModTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden");
    try{
       dTRARegEtapasXModTram.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

  Vector vcListado = dTRARegEtapasXModTram.findByCustom("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden",
        "SELECT ICVEETAPA,IORDEN,ICVEOFICINA,ICVEDEPARTAMENTO,LANEXO FROM TRAREGETAPASXMODTRAM " +
   "WHERE IEJERCICIO = " + request.getParameter("iEjercicio") +
   " AND INUMSOLICITUD = " + request.getParameter("iNumSolicitud") +
   " ORDER BY IORDEN DESC");

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
                '<%=iEtapaVerifica%>',
                '<%=iEtapaRecepcion%>',
                '<%=0%>',
                '<%=iEtapaNotificacion%>',
                '<%=iDeptoVentanillaUnica%>',
                '<%=iEtapaModifTram%>');
</script>
<%}%>

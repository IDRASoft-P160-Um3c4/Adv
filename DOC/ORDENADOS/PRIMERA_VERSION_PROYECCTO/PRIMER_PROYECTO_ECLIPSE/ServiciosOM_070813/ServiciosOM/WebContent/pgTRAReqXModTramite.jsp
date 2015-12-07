<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAReqXModTramite.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAReqXModTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo López Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAReqXModTramite dTRAReqXModTramite = new TDTRAReqXModTramite();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iOrden,lRequerido");
    try{
      vDinRep = dTRAReqXModTramite.insert(vDinRep,null);
    }catch(Exception e){
      if(e.getMessage().equals("")==false){
        cError="Duplicado";
       }else{
          cError="Guardar";
       }
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iOrden,lRequerido");
    try{
      vDinRep = dTRAReqXModTramite.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Cambiar" (Actualizar) */
  if(oAccion.getCAccion().equals("Cambia")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,cConjunto");
    try{
      vDinRep = dTRAReqXModTramite.Cambia(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
/*  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito");
    try{
       dTRAReqXModTramite.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  */
  if(oAccion.getCAccion().equals("Bajar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iOrden");

    try{
      vDinRep = dTRAReqXModTramite.Bajar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Agregar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito1");
    try{
      vDinRep = dTRAReqXModTramite.Agregar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("AgregarT")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito1,iCveGrupo,var1");
    try{
      vDinRep = dTRAReqXModTramite.AgregarT(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  /*if(oAccion.getCAccion().equals("Eliminar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito");
    try{
      vDinRep = dTRAReqXModTramite.delete(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }*/
    if(oAccion.getCAccion().equals("Eliminar")){
      oAccion.setCAccion("Actual");
      vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iOrden,iCveRequisito");
      try{
         dTRAReqXModTramite.delete(vDinRep,null);
      }catch(Exception ex){
        if(ex.getMessage().equals("")==false){
          cError="Cascada";
        }else
          cError="Borrar";
      }
  }
  if(oAccion.getCAccion().equals("Subir")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iOrden");
    try{
      vDinRep = dTRAReqXModTramite.Subir(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSql = "Select TRAReqXModTramite.iCveTramite,TRAReqXModTramite.iCveModalidad,TRAReqXModTramite.iCveRequisito,iOrden,lRequerido," +
                "TRACATTramite.cDscBreve as Tram,cDscModalidad,TRARequisito.cDscBreve " +
                "from TRAReqXModTramite " +
                "join TRACATTramite on TRAReqXModTramite.iCveTramite = TRACATTramite.iCveTramite " +
                "join TRAModalidad on  TRAReqXModTramite.iCveModalidad = TRAModalidad.iCveModalidad " +
                "join TRARequisito on  TRAReqXModTramite.iCveRequisito = TRARequisito.iCveRequisito " +
                oAccion.getCFiltro() + oAccion.getCOrden();

  Vector vcListado = dTRAReqXModTramite.findByCustom("iCveTramite,iCveModalidad,iCveRequisito",cSql);
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

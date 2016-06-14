<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAEtapaXModTram.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAEtapaXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAEtapaXModTram dTRAEtapaXModTram = new TDTRAEtapaXModTram();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa,iOrden,lObligatorio,lVigente");
    try{
      vDinRep = dTRAEtapaXModTram.insert(vDinRep,null);
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
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa,iOrden,lObligatorio,lVigente");
    try{
      vDinRep = dTRAEtapaXModTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarC")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa,iCveEtapaCIS,lEnviarFechaCIS");
    try{
      vDinRep = dTRAEtapaXModTram.updateCIS(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("GuardarB")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa1,lObligatorio");
    try{
      vDinRep = dTRAEtapaXModTram.Agregar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa");
    try{
       dTRAEtapaXModTram.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  if(oAccion.getCAccion().equals("Eliminar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa,iOrden");
    try{
       dTRAEtapaXModTram.Eliminar(vDinRep,null);
    }catch(Exception ex){
      System.out.println("*****    Error en jsp   =   "+ex.toString());
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
        System.out.println("*****    cError = "+cError);
      }else
        cError="Borrar";
    }
  }
  if(oAccion.getCAccion().equals("Arriba")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa,iOrden,iOrdenI,iOrdenF,iEtapaTemp");
    try{
       dTRAEtapaXModTram.Arriba(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  if(oAccion.getCAccion().equals("Abajo")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa,iOrden");
    try{
       dTRAEtapaXModTram.Abajo(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  if(oAccion.getCAccion().equals("Cambia")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveEtapa,iOrden,cConjunto");
    try{
       dTRAEtapaXModTram.Cambia(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSql = "Select TRAEtapaXModTram.iCveTramite as iCveTramite,TRAEtapaXModTram.iCveModalidad as iCveModalidad,TRAEtapaXModTram.iCveEtapa as iCveEtapa,iOrden,lObligatorio,TRAEtapaXModTram.lVigente as lVigente," +
                "cDscBreve,cDscModalidad, cDscEtapa,iCveEtapaCIS,lEnviarFechaCIS " +
                "from TRAEtapaXModTram " +
                "join TRACatTramite  on TRAEtapaXModTram.iCveTramite = TRACatTramite.iCveTramite " +
                "join TraModalidad on TRAEtapaXModTram.iCveModalidad = TraModalidad.iCveModalidad " +
                "join TRAEtapa on TraEtapa.iCveEtapa = TRAEtapaXModTram.iCveEtapa "+
                oAccion.getCFiltro() + oAccion.getCOrden();

  Vector vcListado = dTRAEtapaXModTram.findByCustom("iCveTramite,iCveModalidad,iCveEtapa",cSql);
  oAccion.setINumReg(vcListado.size());
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

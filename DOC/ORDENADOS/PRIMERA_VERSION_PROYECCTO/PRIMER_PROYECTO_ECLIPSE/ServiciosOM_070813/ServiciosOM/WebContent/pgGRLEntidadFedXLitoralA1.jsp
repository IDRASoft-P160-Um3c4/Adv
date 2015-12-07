<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLEntidadFedXLitoralA1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLEntidadFedXLitoralA1</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLEntidadFedXLitoralA1 dGRLEntidadFedXLitoralA1 = new TDGRLEntidadFedXLitoralA1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCvePais,iCveEntidadFed,iCveLitoral");
    try{
      vDinRep = dGRLEntidadFedXLitoralA1.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePais,iCveEntidadFed,iCveLitoral,iOrden");
    try{
      vDinRep = dGRLEntidadFedXLitoralA1.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Subir")){
    vDinRep = oAccion.setInputs("iOrden,iCveLitoral");
    try{
      vDinRep = dGRLEntidadFedXLitoralA1.update1(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Bajar")){
    vDinRep = oAccion.setInputs("iOrden,iCveLitoral");
    try{
      vDinRep = dGRLEntidadFedXLitoralA1.update2(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCvePais1,iCveEntidadFed1,iOrden,iCveLitoral");
    try{
       dGRLEntidadFedXLitoralA1.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  /** Verifica si la Acción a través de hdBotón es igual a "Agregar" */
  if(oAccion.getCAccion().equals("Agregar")){
    vDinRep = oAccion.setInputs("CvePais,CveEntidadFed,iOrden");
    try{
      vDinRep = dGRLEntidadFedXLitoralA1.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLEntidadFedXLitoralA1.findByCustom("iCvePais,iCveEntidadFed","Select GRLEntidadFedXLitoral.iCvePais,GRLEntidadFedXLitoral.iCveEntidadFed,iCveLitoral,iOrden,cNombre from GRLEntidadFedXLitoral "+
                                                                                     " Join GRLEntidadFed ON GRLEntidadFedXLitoral.iCvePais = GRLEntidadFed.iCvePais AND GRLEntidadFedXLitoral.iCveEntidadFed = GRLEntidadFed.iCveEntidadFed " + oAccion.getCFiltro() + oAccion.getCOrden());
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

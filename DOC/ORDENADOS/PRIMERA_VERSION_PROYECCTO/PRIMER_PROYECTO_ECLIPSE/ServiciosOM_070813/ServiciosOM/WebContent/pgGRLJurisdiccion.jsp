<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLJurisdiccion.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLJurisdiccion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Artur López Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLJurisdiccion dGRLJurisdiccion = new TDGRLJurisdiccion();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCvePais,iCveEntidadFed,iCveMunicipio,cDscJurisdiccion,lVigente,iCveOficinaAtencion");
    try{
      vDinRep = dGRLJurisdiccion.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePais,iCveEntidadFed,iCveMunicipio,iCveJurisdiccion,cDscJurisdiccion,lVigente,iCveOficinaAtencion");
    try{
      vDinRep = dGRLJurisdiccion.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCvePais,iCveEntidadFed,iCveMunicipio,iCveJurisdiccion");
    try{
       dGRLJurisdiccion.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 String cSql = " Select GRLJurisdiccion.iCvePais,GRLJurisdiccion.iCveEntidadFed,GRLJurisdiccion.iCveMunicipio,iCveJurisdiccion,cDscJurisdiccion,GRLJurisdiccion.lVigente,GRLJurisdiccion.iCveOficinaAtencion, " +
               " GRLPais.cNombre as cDscPais, GRLEntidadFed.cNombre as cDscEntidadFed, " +
               " GRLMunicipio.cNombre as cDscMunicipio, " +
               " GRLOficina.cDscOficina " +
               " from GRLJurisdiccion " +
               " join  GRLPais on GRLJurisdiccion.iCvePais = GRLPais.iCvePais " +
               " join  GRLEntidadFed on  GRLJurisdiccion.iCvePais = GRLEntidadFed.iCvePais " +
               " and GRLJurisdiccion.iCveEntidadFed = GRLEntidadFed.iCveEntidadFed " +
               " join  GRLMunicipio  on  GRLJurisdiccion.iCvePais = GRLMunicipio.iCvePais  " +
               " and GRLJurisdiccion.iCveEntidadFed = GRLMunicipio.iCveEntidadFed " +
               " and GRLJurisdiccion.iCveMunicipio = GRLMunicipio.iCveMunicipio " +
               " Join GRLOficina ON GRLOficina.iCveOficina = GRLJurisdiccion.iCveOficinaAtencion " +
               oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dGRLJurisdiccion.findByCustom("iCvePais,iCveEntidadFed,iCveMunicipio,iCveJurisdiccion",cSql);
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

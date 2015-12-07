<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLOficina.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLOficina</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLOficina dGRLOficina = new TDGRLOficina();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDscOficina,cDscBreve,iCveCategoria,iCvePais,iCveEntidadFed,iCveMunicipio,cCalleYNo,cColonia,cCodPostal,cTitular,cTelefono,cCorreoE,lVigente,iCveOficIngresos,iCveUnidadMVA,iCveOficinaCIS");
    try{
      vDinRep = dGRLOficina.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOficina,cDscOficina,cDscBreve,iCveCategoria,iCvePais,iCveEntidadFed,iCveMunicipio,cCalleYNo,cColonia,cCodPostal,cTitular,cTelefono,cCorreoE,lVigente,iCveOficIngresos,iCveUnidadMVA,iCveOficinaCIS");
    try{
      vDinRep = dGRLOficina.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOficina");
    try{
       dGRLOficina.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLOficina.findByCustom("iCveOficina","Select iCveOficina,cDscOficina,cDscBreve,GRLOficina.iCveCategoria,GRLOficina.iCvePais,GRLOficina.iCveEntidadFed,GRLOficina.iCveMunicipio,cCalleYNo,cColonia,cCodPostal,cTitular,cTelefono,cCorreoE,GRLOficina.lVigente,GRLCategoriaOfic.cDscCategoria, GRLPais.cNombre As cPais, GRLEntidadFed.cNombre As cEntidadFed, GRLMunicipio.cNombre As cMunicipio, iCveOficIngresos, iCveUnidadMVA, iCveOficinaCIS "+
                                              "from GRLOficina "+
                                              "Join GRLCategoriaOfic ON GRLCategoriaOfic.iCveCategoria = GRLOficina.iCveCategoria "+
                                              "Join GRLPais On GRLOficina.iCvePais = GRLPais.iCvePais  "+
                                              "Join GRLEntidadFed ON GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed AND GRLEntidadFed.iCvePais = GRLOficina.iCvePais " +
                                              "Join GRLMunicipio ON GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed AND GRLMunicipio.iCvePais = GRLOficina.iCvePais AND GRLMunicipio.iCveMunicipio = GRLOficina.iCveMunicipio  "
                                              + oAccion.getCFiltro() + oAccion.getCOrden());
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

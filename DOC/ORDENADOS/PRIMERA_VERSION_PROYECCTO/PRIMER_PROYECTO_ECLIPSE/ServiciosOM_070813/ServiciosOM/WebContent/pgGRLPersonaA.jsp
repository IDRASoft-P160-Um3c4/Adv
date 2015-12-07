<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLPersonaA.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLPersona</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLPersonaA dGRLPersona = new TDGRLPersonaA();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad");
    try{
      vDinRep = dGRLPersona.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE");
    try{
      vDinRep = dGRLPersona.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCvePersona");
    try{
       dGRLPersona.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 String cSQL= "Select GRLPersona.iCvePersona,cRFC,cRPA,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,GRLDomicilio.iCvePais,GRLDomicilio.iCveMunicipio,GRLPAIS.CNOMBRE,GRLDomicilio.iCveentidadFed,GRLENTIDADFED.CNOMBRE,GRLMUNICIPIO.CNOMBRE,GRLLocalidad.cNombre " +
               "from GRLPersona " +
               //"join GRLRepLegal on GRLPersona.iCvePersona=GRLRepLegal.iCvePersona " +
               "join GRLDomicilio on GRLPersona.iCvePersona=GRLDomicilio.iCvePersona " +
               "join GRLPAIS ON GRLDomicilio.ICVEPAIS = GRLPAIS.ICVEPAIS  " +
               "join GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLPAIS.ICVEPAIS AND GRLENTIDADFED.ICVEENTIDADFED = GRLDomicilio.ICVEENTIDADFED " +
               "join GRLMUNICIPIO  ON GRLMUNICIPIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDomicilio.ICVEMUNICIPIO " +
               "join GRLLocalidad  ON  grldomicilio.icvepais=grllocalidad.icvepais and grllocalidad.icvepais=grldomicilio.icvepais and grllocalidad.icveentidadfed=grldomicilio.icveentidadfed and grldomicilio.icvemunicipio=grllocalidad.icvemunicipio and GRLDOMICILIO.iCveLocalidad = GRLLocalidad.iCveLocalidad " +
//               "join GRLLocalidad  ON  grllocalidad.icvepais=grldomicilio.icvepais and grllocalidad.icveentidadfed=grldomicilio.icveentidadfed and grldomicilio.icvemunicipio=grllocalidad.icvemunicipio and GRLDOMICILIO.iCveLocalidad = GRLLocalidad.iCveLocalidad  " +
                oAccion.getCFiltro() + oAccion.getCOrden();
 Vector vcListado = dGRLPersona.findByCustom("iCvePersona",cSQL);
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

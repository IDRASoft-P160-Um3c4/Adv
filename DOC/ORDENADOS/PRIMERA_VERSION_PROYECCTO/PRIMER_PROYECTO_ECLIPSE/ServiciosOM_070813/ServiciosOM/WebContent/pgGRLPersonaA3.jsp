<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLPersonaA1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLPersona</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLPersonaA1 dGRLPersona = new TDGRLPersonaA1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp,");
    try{
      vDinRep = dGRLPersona.insert(vDinRep,null);
    }catch(Exception e){
      e.printStackTrace();
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp");
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
  String cFiltro = oAccion.getCFiltro();
  String cFrame = "";
  TVDinRep vFiltro = oAccion.setInputs("hdFiltro11,hdFiltro11A,hdFiltro12,hdFiltro13,hdFiltro14,");
  cFrame = request.getParameter("cNombreFRM");
  if (cFrame.equals("FRMCuerpo") || cFrame.equals(""))
    cFiltro = vFiltro.getString("hdFiltro11");
  if (cFrame.equals("PEM1"))
    cFiltro = vFiltro.getString("hdFiltro11A");
  if (cFrame.equals("PEM2"))
    cFiltro = vFiltro.getString("hdFiltro12");
  if (cFrame.equals("PEM3"))
    cFiltro = vFiltro.getString("hdFiltro13");
  if (cFrame.equals("PEM4"))
    cFiltro = vFiltro.getString("hdFiltro14");
  if (!cFiltro.equals(""))
    cFiltro = " where " + cFiltro + " ";
  String cSql="Select GRLPersona.iCvePersona, " +
              "GRLPersona.cRFC, " +
              "GRLPersona.cRPA, " +
              "GRLPersona.iTipoPersona, " +
              "GRLPersona.cNomRazonSocial, " +
              "GRLPersona.cApPaterno, " +
              "GRLPersona.cApMaterno, " +
              "GRLPersona.cCorreoE, " +
              "GRLPersona.cPseudonimoEmp, " +
              "GRLPersona.cNomRazonSocial||' '||GRLPersona.cApPaterno||' '||GRLPersona.cApMaterno, " +
              "cCalle || ' No. ' || cNumExterior || ' Int. ' || cNumInterior || ' COL. ' || cColonia || ', ' || GRLMunicipio.cNombre || ' (' || GRLEntidadFed.cAbreviatura ||'.)' AS cDomicilio " +
              "from GRLPersona " +
              "JOIN GRLDomicilio on GRLDomicilio.ICVEPERSONA = GRLPersona.iCvePersona " +
              "AND GRLDomicilio.lPredeterminado = 1 " +
              "join GRLPAIS ON GRLDomicilio.ICVEPAIS = GRLPAIS.ICVEPAIS " +
              "join GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLPAIS.ICVEPAIS " +
              "AND GRLENTIDADFED.ICVEENTIDADFED = GRLDomicilio.ICVEENTIDADFED " +
              "join GRLMUNICIPIO ON GRLMunicipio.iCvePais = GRLDomicilio.iCvePais "+
              "AND GRLMUNICIPIO.ICVEENTIDADFED = GRLDomicilio.ICVEENTIDADFED " +
              "AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDomicilio.ICVEMUNICIPIO " +
              "join GRLLocalidad ON grldomicilio.icvepais=grllocalidad.icvepais " +
              "and grllocalidad.icveentidadfed=grldomicilio.icveentidadfed " +
              "and grldomicilio.icvemunicipio=grllocalidad.icvemunicipio " +
              "and GRLDOMICILIO.iCveLocalidad = GRLLocalidad.iCveLocalidad " +
              cFiltro +
              " order by cRFC, cNomRazonSocial, cApPaterno ";
  Vector vcListado = new Vector();
  try{
    if (!cFiltro.equals(""))
      vcListado = dGRLPersona.findByCustom("iCvePersona",cSql);
    oAccion.setINumReg(vcListado.size());
  }catch(Exception e){
    cError=e.getMessage();
  }
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

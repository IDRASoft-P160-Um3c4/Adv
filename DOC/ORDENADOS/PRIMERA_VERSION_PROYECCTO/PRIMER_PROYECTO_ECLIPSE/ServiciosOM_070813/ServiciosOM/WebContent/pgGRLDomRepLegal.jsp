<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLDomRepLegal.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLRepLegal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLRepLegal dGRLRepLegal = new TDGRLRepLegal();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCvePersona,dtAsignacion,lPrincipal");
    try{
      vDinRep = dGRLRepLegal.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveEmpresa,iCvePersona,dtAsignacion,lPrincipal");
    try{
      vDinRep = dGRLRepLegal.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveEmpresa");
    try{
       dGRLRepLegal.delete(vDinRep,null);
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
  TVDinRep vValores = oAccion.setInputs("cNombreFRM,hdFiltro11,hdFiltro11A,hdFiltro12,hdFiltro13,hdFiltro14");
  cFrame = vValores.getString("cNombreFRM");//request.getParameter("cNombreFRM");
  if (cFrame == null)
    cFrame = "";
  if (cFrame.equals("FRMCuerpo") || cFrame.equals(""))
    cFiltro = vValores.getString("hdFiltro11");//request.getParameter("hdFiltro11");
  if (cFrame.equals("PEM1"))
    cFiltro = vValores.getString("hdFiltro11A");//request.getParameter("hdFiltro11A");
  if (cFrame.equals("PEM2"))
    cFiltro = vValores.getString("hdFiltro12");//request.getParameter("hdFiltro12");
  if (cFrame.equals("PEM3"))
    cFiltro = vValores.getString("hdFiltro13");//request.getParameter("hdFiltro13");
  if (cFrame.equals("PEM4"))
    cFiltro = vValores.getString("hdFiltro14");//request.getParameter("hdFiltro14");
  if(cFiltro == null)
    cFiltro = "";
  if (!cFiltro.equals(""))
    cFiltro = " where " + cFiltro + " ";
 String cSql= "Select distinct grlreplegal.iCvePersona,iCveDomicilio,GRLDomicilio.iCveTipoDomicilio,cCalle,cNumExterior"+
              ",cNumInterior,cColonia,cCodPostal,cTelefono,GRLDomicilio.iCvePais"+
              ",GRLLocalidad.cNombre,GRLpais.cNombre as pais,GRLDomicilio.iCveentidadFed,GRLENTIDADFED.CNOMBRE as estado"+
              ",GRLMUNICIPIO.CNOMBRE as municipio,GRLDomicilio.iCveMunicipio,lPredeterminado,grltipodomicilio.cdsctipodomicilio " +
              ",cCalle || ' No. ' || cNumExterior || ' Int. ' || cNumInterior || ' COL. ' || cColonia || ', ' || GRLMunicipio.cNombre || ' (' || GRLEntidadFed.cAbreviatura ||'.)' || ' C.P. ' || cCodPostal AS cDomicilio"+
              ",GRLDomicilio.iCveLocalidad " +
              " from GRLDomicilio " +
              "join grlreplegal on grldomicilio.icvepersona=grlreplegal.icvepersona " +
              "join GRLPAIS ON GRLDomicilio.ICVEPAIS = GRLPAIS.ICVEPAIS " +
              "join GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLPAIS.ICVEPAIS AND GRLENTIDADFED.ICVEENTIDADFED = GRLDomicilio.ICVEENTIDADFED " +
              "join GRLMUNICIPIO  ON GRLMUNICIPIO.ICVEPAIS=GRLDomicilio.ICVEPAIS AND GRLMUNICIPIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDomicilio.ICVEMUNICIPIO " +
              "join GRLLocalidad  ON  grldomicilio.icvepais=grllocalidad.icvepais and grllocalidad.icvepais=grldomicilio.icvepais and grllocalidad.icveentidadfed=grldomicilio.icveentidadfed and grldomicilio.icvemunicipio=grllocalidad.icvemunicipio and GRLDOMICILIO.iCveLocalidad = GRLLocalidad.iCveLocalidad " +
              "join grltipodomicilio ON GRLDomicilio.iCveTipoDomicilio = grltipodomicilio.iCveTipoDomicilio " +
              cFiltro +
              " ORDER BY lPredeterminado desc ";
  Vector vcListado = new Vector();
  try{
    if (!cFiltro.equals(""))
      vcListado = dGRLRepLegal.findByCustom("iCveEmpresa",cSql);
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

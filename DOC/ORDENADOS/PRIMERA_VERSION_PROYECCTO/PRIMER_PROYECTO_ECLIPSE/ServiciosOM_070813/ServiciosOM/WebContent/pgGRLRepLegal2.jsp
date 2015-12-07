<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLRepLegal2.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLRepLegal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
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
    vDinRep = oAccion.setInputs("dtAsignacion,lPrincipal");
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
    vDinRep = oAccion.setInputs("iCveEmpresa,iCvePersona");
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
  String cSql = "SELECT distinct CNOMRAZONSOCIAL || ' ' || CAPPATERNO || ' ' || CAPMATERNO as cNom,CRFC, ccalle, "+
                "CNUMEXTERIOR,CNUMINTERIOR,CCOLONIA,CCODPOSTAL,CCORREOE,CTELEFONO,GRLENTIDADFED.cnombre as estado,grlmunicipio.cnombre, " +
                "grldomicilio.icvedomicilio " +
		"FROM GRLPERSONA " +
		"join GRLDOMICILIO on grlpersona.ICVEPERSONA = grldomicilio.ICVEPERSONA " +
		"join GRLENTIDADFED on grlentidadfed.ICVEENTIDADFED = grldomicilio.ICVEENTIDADFED " +
		"join GRLMUNICIPIO on grldomicilio.ICVEMUNICIPIO = grlmunicipio.ICVEMUNICIPIO  " +
		"AND grlentidadfed.ICVEENTIDADFED = grlmunicipio.ICVEENTIDADFED " +
                oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dGRLRepLegal.findByCustom("iCveEmpresa,iCvePersona",cSql);
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

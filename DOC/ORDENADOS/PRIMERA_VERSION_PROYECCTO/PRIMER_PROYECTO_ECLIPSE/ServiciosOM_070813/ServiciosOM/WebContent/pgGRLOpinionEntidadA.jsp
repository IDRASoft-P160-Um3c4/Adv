<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLOpinionEntidadA.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad GRLOpinionEntidad</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLOpinionEntidadA dGRLOpinionEntidadA = new TDGRLOpinionEntidadA();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("lEsTramite,lEsOpinionInterna,iCveTramite,iCveModalidad,iCvePersona,iCveDomicilio,lVigente,cOpinionDirigidoA,cPuestoOpinion,cRepresentante");
    try{
      vDinRep = dGRLOpinionEntidadA.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOpinionEntidad,iCvePersona,iCveDomicilio,lVigente,cOpinionDirigidoA,cPuestoOpinion,cRepresentante");
    try{
      vDinRep = dGRLOpinionEntidadA.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOpinionEntidad");
    try{
       dGRLOpinionEntidadA.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
 String cSQL = "Select iCveOpinionEntidad,lEsTramite,lEsOpinionInterna,iCveTramite,iCveModalidad, "+
              "iCveSistema,iCveModulo,GRLOpinionEntidad.iCveOficinaOpn,GRLOpinionEntidad.iCveDepartamentoOpn, "+
              "GRLOpinionEntidad.iCvePersona,GRLOpinionEntidad.iCveDomicilio,GRLOpinionEntidad.lVigente,cNomRazonSocial,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal, "+
              "GRLMunicipio.cNombre as Municipio, GRLEntidadFed.cNombre as Entidad, cOpinionDirigidoA, cPuestoOpinion, cRepresentante "+
              "from GRLOpinionEntidad "+
              "JOIN GRLDomicilio ON GRLDomicilio.iCveDomicilio = GRLOpinionEntidad.iCveDomicilio AND GRLDomicilio.iCvePersona = GRLOpinionEntidad.iCvePersona "+
              "JOIN GRLPersona ON GRLPersona.iCvePersona = GRLOpinionEntidad.iCvePersona "+
              "JOIN GRLMunicipio ON GRLMunicipio.iCvePais = GRLDomicilio.iCvepais AND GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed AND GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio "+
              "JOIN GRLEntidadFed ON GRLEntidadFed.iCvepais = GRLMUNICIPIO.iCvepais AND GRLEntidadFed.iCveEntidadFed = GRLMUNICIPIO.iCveEntidadFed ";

  Vector vcListado = dGRLOpinionEntidadA.findByCustom("iCveOpinionEntidad",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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
<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLDigitosFolioXDepto.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLDigitosFolioXDepto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLDigitosFolioXDepto dGRLDigitosFolioXDepto = new TDGRLDigitosFolioXDepto();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDigitosFolio,iCveOficina,iCveDepartamento");
    try{
      vDinRep = dGRLDigitosFolioXDepto.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("cDigitosFolio,dtAsignacion,iCveOficina,iCveDepartamento");
    try{
      vDinRep = dGRLDigitosFolioXDepto.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("cDigitosFolio,dtAsignacion");
    try{
       dGRLDigitosFolioXDepto.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLDigitosFolioXDepto.findByCustom("cDigitosFolio,dtAsignacion",
	"SELECT " +
	"  GRLDigitosFolioXDepto.cDigitosFolio," +
	"  GRLDigitosFolioXDepto.dtAsignacion," +
	"  GRLDigitosFolioXDepto.iCveOficina," +
	"  GRLDigitosFolioXDepto.iCveDepartamento," +
	"  GRLOficina.cDscOficina," +
	"  GRLOficina.cDscBreve," +
	"  GRLDepartamento.cDscDepartamento," +
	"  GRLDepartamento.cDscBreve, " +
        "  GRLDEPTOXOFIC.CTITULAR  " +
	"FROM GRLDigitosFolioXDepto" +
	"  JOIN GRLOficina ON GRLDigitosFolioXDepto.iCveOficina = GRLOficina.iCveOficina" +
	"  JOIN GRLDepartamento ON GRLDigitosFolioXDepto.iCveDepartamento = GRLDepartamento.iCveDepartamento " +
       "   JOIN GRLDEPTOXOFIC ON GRLDEPTOXOFIC.ICVEOFICINA = GRLDigitosFolioXDepto.ICVEOFICINA AND GRLDEPTOXOFIC.ICVEDEPARTAMENTO = GRLDigitosFolioXDepto.ICVEDEPARTAMENTO " +
	  oAccion.getCFiltro() + oAccion.getCOrden());
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

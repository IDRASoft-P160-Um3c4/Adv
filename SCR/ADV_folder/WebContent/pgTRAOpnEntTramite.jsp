<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAOpnEntTramite.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAOpnEntTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAOpnEntTramite dTRAOpnEntTramite = new TDTRAOpnEntTramite();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("lEsContestacion,cObsesSegto,iEjercicioSolicitud,iNumSolicitud,iCveDeptoUsr,iCveOficinaUsr,hdCveOpinionEntidad,cOficio");
    try{
      vDinRep = dTRAOpnEntTramite.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("hdCveOpinionEntidad,iEjercicioSolicitud,iNumSolicitud,iCveModalidad,iCveTramite,iCveSegtoEntidad,cObsesSegto,cOficio");
    try{
      vDinRep = dTRAOpnEntTramite.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad");
    try{
       dTRAOpnEntTramite.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }


	/** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
	String cSQL = "SELECT TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD, TRAOPNENTTRAMITE.INUMSOLICITUD, TRACATTRAMITE.CDSCTRAMITE, TRAMODALIDAD.CDSCMODALIDAD, " +
		"GRLOFICINA.CDSCOFICINA, GRLDEPARTAMENTO.CDSCDEPARTAMENTO, GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD, GRLSEGTOENTIDAD.ICVESEGTOENTIDAD, GRLSEGTOENTIDAD.COBSESSEGTO, " +
                "GRLOFICINA.ICVEOFICINA, GRLDEPARTAMENTO.ICVEDEPARTAMENTO, GRLSegtoEntidad.lEsContestacion, "+
                "GRLSegtoEntidad.CDIRIGIDOACON,GRLSEGTOENTIDAD.cOficio " +
		"FROM TRAOPNENTTRAMITE " +
		"JOIN GRLOPINIONENTIDAD ON GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD = TRAOPNENTTRAMITE.ICVEOPINIONENTIDAD " +
		"JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = GRLOPINIONENTIDAD.ICVETRAMITE " +
		"JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD = GRLOPINIONENTIDAD.ICVEMODALIDAD " +
		"JOIN GRLDEPTOXOFIC ON GRLDEPTOXOFIC.ICVEOFICINA = GRLOPINIONENTIDAD.ICVEOFICINAOPN AND GRLDEPTOXOFIC.ICVEDEPARTAMENTO = GRLOPINIONENTIDAD.ICVEDEPARTAMENTOOPN " +
		"JOIN GRLOFICINA ON GRLOFICINA.ICVEOFICINA = GRLDEPTOXOFIC.ICVEOFICINA " +
		"JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = GRLDEPTOXOFIC.ICVEDEPARTAMENTO " +
              	"JOIN GRLSEGTOENTIDAD ON GRLSEGTOENTIDAD.ICVESEGTOENTIDAD = TRAOPNENTTRAMITE.ICVESEGTOENTIDAD ";
	Vector vcListado = dTRAOpnEntTramite.findByCustom("iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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

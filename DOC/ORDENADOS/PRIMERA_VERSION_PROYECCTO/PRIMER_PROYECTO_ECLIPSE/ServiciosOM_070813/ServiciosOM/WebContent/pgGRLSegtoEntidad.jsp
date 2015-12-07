<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLSegtoEntidad.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLSegtoEntidad</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLSegtoEntidad dGRLSegtoEntidad = new TDGRLSegtoEntidad();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("lEsContestacion,cObsesSegto,iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,cDirigidoACon,cAsuntoCon,cTitularFirmaCon,dtOficioCon,dtRecepcionCon,cNumOficialiaPartesCon,cNumOficioCon,cOpnDirigidoA,cPtoOpinion,cSiglas,cOficio");
    try{
      vDinRep = dGRLSegtoEntidad.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad,lEsContestacion,cDirigidoACon,cAsuntoCon,cTitularFirmaCon,dtOficioCon,dtRecepcionCon,cNumOficialiaPartesCon,cNumOficioCon,cObsesSegto,cOpnDirigidoA,cPtoOpinion,cSiglas,cOficio");
    try{
      vDinRep = dGRLSegtoEntidad.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveSegtoEntidad");
    try{
       dGRLSegtoEntidad.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 String cSQL = "SELECT TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD, TRAOPNENTTRAMITE.INUMSOLICITUD, TRACATTRAMITE.CDSCTRAMITE, TRAMODALIDAD.CDSCMODALIDAD, " +
		"GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO as NOMBRE, "+
                "GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD, GRLSEGTOENTIDAD.ICVESEGTOENTIDAD, GRLSEGTOENTIDAD.COBSESSEGTO, " +
		"GRLSEGTOENTIDAD.LESCONTESTACION, GRLSEGTOENTIDAD.CDIRIGIDOACON, GRLSEGTOENTIDAD.CASUNTOCON, GRLSEGTOENTIDAD.CTITULARFIRMACON, " +
		"GRLSEGTOENTIDAD.DTOFICIOCON, GRLSEGTOENTIDAD.DTRECEPCIONCON, GRLSEGTOENTIDAD.CNUMOFICIALIAPARTESCON, GRLSEGTOENTIDAD.CNUMOFICIOCON, " +
                "TRAOPNENTTRAMITE.COPNDIRIGIDOA, TRAOPNENTTRAMITE.CPTOOPINION,GRLOPINIONENTIDAD.COPINIONDIRIGIDOA, GRLOPINIONENTIDAD.CPUESTOOPINION,GRLSEGTOENTIDAD.CSIGLAS,GRLSEGTOENTIDAD.cOficio " +
              	"FROM TRAOPNENTTRAMITE " +
		"JOIN GRLOPINIONENTIDAD ON GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD = TRAOPNENTTRAMITE.ICVEOPINIONENTIDAD " +
		"JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = GRLOPINIONENTIDAD.ICVETRAMITE " +
		"JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD = GRLOPINIONENTIDAD.ICVEMODALIDAD " +
		"JOIN GRLSEGTOENTIDAD ON GRLSEGTOENTIDAD.ICVESEGTOENTIDAD = TRAOPNENTTRAMITE.ICVESEGTOENTIDAD " +
		"JOIN GRLPERSONA ON GRLPERSONA.ICVEPERSONA = GRLOPINIONENTIDAD.ICVEPERSONA ";

  Vector vcListado = dGRLSegtoEntidad.findByCustom("iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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

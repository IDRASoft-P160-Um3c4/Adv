<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.util.*" %>
<%
/**
 * <p>Title: pgTRARegSolMatricula.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  int iAux = 0;
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
//  " SELECT iEjercicio, iNumSolicitud, iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma "
  Vector vcListado = null;
  String cSQL = "",
         cFiltroCertificado = "";

  if(oAccion.getCAccion().equals("CERTIFICADOS")){
  cSQL =
	"SELECT " +
	"  VEHEmbarcacion.cNomEmbarcacion, " +  //0
	"  INSCERTIFEXPEDIDOS.DTEXPEDICIONCERT, " +
	"  INSCERTIFEXPEDIDOS.DTINIVIGENCIA, " +
	"  INSCERTIFEXPEDIDOS.DTFINVIGENCIA, " +
	"  INSCERTIFEXPEDIDOS.LVIGENTE, " +
	"  INSTIPOCERTIF.CDSCCERTIFICADO, " +  //5
	"  INSCERTPBUQ.ITIPOCERTIFICADO, " +
	"  INSINSPECCION.DTFININSP, " +
	"  INSCertXInspeccion.lAprobada, " +
        "  INSInspeccion.dtIniInsp " +
	"FROM VEHEmbarcacion " +
	"  JOIN INSCERTPBUQ ON VEHEmbarcacion.ICVETIPOSERV = INSCERTPBUQ.ICVETIPOSERV  AND VEHEmbarcacion.iCveTipoNavega = INSCERTPBUQ.ICVETIPONAVEGA " +
	"                   AND (VEHEmbarcacion.DARQUEOBRUTO BETWEEN INSCERTPBUQ.IUABMINIMA AND INSCERTPBUQ.IUABMAXIMA " +
        "                       OR VEHEmbarcacion.DARQUEOBRUTO >= INSCERTPBUQ.IUABMINIMA AND INSCERTPBUQ.IUABMAXIMA = 0)   " +
	"  JOIN INSTIPOCERTIF ON INSCERTPBUQ.ICVEGRUPOCERTIF = INSTIPOCERTIF.ICVEGRUPOCERTIF  and INSCERTPBUQ.ITIPOCERTIFICADO = INSTIPOCERTIF.ITIPOCERTIFICADO " +
	"  left JOIN INSCERTIFEXPEDIDOS ON VEHEmbarcacion.iCveVehiculo = INSCERTIFEXPEDIDOS.ICVEVEHICULO " +
	"                         AND INSCERTPBUQ.ICVEGRUPOCERTIF = INSCERTIFEXPEDIDOS.ICVEGRUPOCERTIF AND INSCERTPBUQ.ITIPOCERTIFICADO = INSCERTIFEXPEDIDOS.ITIPOCERTIFICADO " +
	"                         AND INSCERTIFEXPEDIDOS.LVIGENTE = 1 " +
	"  left join INSPROGINSP ON VEHEMBARCACION.ICVEVEHICULO = INSPROGINSP.ICVEEMBARCACION " +
 	"  left join INSCertXInspeccion ON  INSPROGINSP.iCveInspProg = INSCertXInspeccion.iCveInspProg " +
	"                     AND INSCertXInspeccion.ICVEGRUPOCERTIF = INSPROGINSP.ICVEGRUPOCERTIF AND INSCertXInspeccion.ITIPOCERTIFICADO = INSPROGINSP.ITIPOCERTIFICADO " +
	"  left join INSINSPECCION ON INSPROGINSP.ICVEINSPPROG = INSINSPECCION.ICVEINSPPROG ";
        cFiltroCertificado = " AND VEHEMBARCACION.ICVEPAISABANDERAMIENTO = " + vParametros.getPropEspecifica("PaisDef") + " ";
  }
  else if(oAccion.getCAccion().equals("INSPECCIONES")){
  cSQL =
	"SELECT " +
	"  INSProgInsp.iEjercicio, " +
	"  INSProgInsp.iNumSolicitud, " +
	"  INSInspeccion.iCveInspeccion, " +
	"  INSInspeccion.iCveInspProg, " +
	"  INSInspeccion.dtFinInsp, " +
	"  INSTipoCertif.cDscCertificado, " +
	"  INSTipoInsp.cDscInspeccion, " +
	"  INSCertXInspeccion.lAprobada " +
	"FROM INSProgInsp " +
 	"  left join INSCertXInspeccion ON  INSPROGINSP.iCveInspProg = INSCertXInspeccion.iCveInspProg " +
	"  left JOIN INSInspeccion ON INSProgInsp.iCveInspProg = INSInspeccion.iCveInspProg " +
	"  JOIN INSTipoCertif ON INSProgInsp.ICVEGRUPOCERTIF = INSTIPOCERTIF.ICVEGRUPOCERTIF and INSProgInsp.iTipoCertificado = INSTipoCertif.iTipoCertificado " +
	"  JOIN INSTipoInsp ON INSProgInsp.iCveTipoInsp = INSTipoInsp.iCveTipoInsp ";
  }

  //System.out.println("-----:-- "+cSQL + oAccion.getCFiltro() + cFiltroCertificado + oAccion.getCOrden());
  vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud", cSQL + oAccion.getCFiltro() + cFiltroCertificado + oAccion.getCOrden());
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pg111020015A1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon //mbeano && iCaballero
 * @version 1.0
 */
  TLogger.setSistema("11");
  TParametro  vParametros = new TParametro("11");
  TVDinRep vDinRep;
  TDTRARegSolicitudA1 dTRARegSolicitud = new TDTRARegSolicitudA1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String cEtapas = vParametros.getPropEspecifica("EtapaConcluidoArea") + "," + vParametros.getPropEspecifica("EtapaEntregarVU") + "," +
  vParametros.getPropEspecifica("EtapaEntregarOficialia") + "," + vParametros.getPropEspecifica("EtapaRecibeResolucion") + "," +
  vParametros.getPropEspecifica("EtapaResEnviadaOficialia")+","+vParametros.getPropEspecifica("EtapaEntregaResol")+","+
  vParametros.getPropEspecifica("EtapaConclusionTramite");
  //TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
    try{
      vDinRep = dTRARegSolicitud.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
    try{
      vDinRep = dTRARegSolicitud.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
    try{
       dTRARegSolicitud.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",
         "SELECT " +
         "       TRAREGSOLICITUD.IEJERCICIO, " +
         "       TRAREGSOLICITUD.INUMSOLICITUD, " +
         "       TRAREGSOLICITUD.ICVETRAMITE, " +
         "       TRACATTRAMITE.CDSCTRAMITE, " +
         "       TRACATTRAMITE.CDSCBREVE AS CDSCBREVETRAMITE, " +
         "       TRAREGSOLICITUD.ICVEMODALIDAD, " +
         "       TRAMODALIDAD.CDSCMODALIDAD, " +
         "       TRAREGSOLICITUD.ICVESOLICITANTE, " +
         "       GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS CNOMBRECOMPLETO, " +
         "       GRLPERSONA.CRFC, " +
         "       TRAREGSOLICITUD.TSREGISTRO, " +
         "       TRAREGSOLICITUD.DTESTIMADAENTREGA, " +
         "       TRAREGSOLICITUD.LPAGADO, " +
         "       TRAREGSOLICITUD.DTENTREGA, " +
         "       TRAREGSOLICITUD.LRESOLUCIONPOSITIVA, " +
         "       TRAREGSOLICITUD.ICVEOFICINA, " +
         "       GRLOFICINA.CDSCBREVE AS CDSCBREVEOFICINA, " +
         "       TRAREGSOLICITUD.ICVEDEPARTAMENTO, " +
         "       GRLDEPARTAMENTO.CDSCBREVE AS CDSCBREVEDEPTO, " +
         "       TRAREGSOLICITUD.CDSCBIEN, " +
         "       TREXMT.ICVEETAPA AS ICVEETA, " +
         "       E.CDSCETAPA, " +
         "       TRAREGSOLICITUD.DTENTREGA, " +
         "       O1.CDSCBREVE as COFIRESUELVE, " +
         "       D1.CDSCBREVE AS CDPTORESUELVE, " +
         "       TRAREGSOLICITUD.CENVIARRESOLUCIONA "+
         "FROM TRAREGSOLICITUD " +
         "  JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE " +
         "  JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD " +
         "  JOIN GRLPERSONA ON GRLPERSONA.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE " +
         "  JOIN GRLOFICINA ON GRLOFICINA.ICVEOFICINA = TRAREGSOLICITUD.ICVEOFICINA " +
         "  JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = TRAREGSOLICITUD.ICVEDEPARTAMENTO " +
         "  LEFT JOIN TRATRAMITEXOFIC TXO ON TXO.ICVEOFICINA=TRAREGSOLICITUD.ICVEOFICINA " +
         "    AND TXO.ICVETRAMITE= TRAREGSOLICITUD.ICVETRAMITE " +
         "  JOIN GRLOFICINA O1 ON O1.ICVEOFICINA = txo.ICVEOFICINARESUELVE " +
         "  JOIN GRLDEPARTAMENTO D1 ON D1.ICVEDEPARTAMENTO = TXO.ICVEDEPTORESUELVE " +
         "  LEFT JOIN TRAREGETAPASXMODTRAM TREXMT ON TRAREGSOLICITUD.IEJERCICIO = TREXMT.IEJERCICIO " +
         "    AND TRAREGSOLICITUD.INUMSOLICITUD = TREXMT.INUMSOLICITUD " +
         "  LEFT JOIN TRAETAPA E ON E.ICVEETAPA = TREXMT.ICVEETAPA " +
         oAccion.getCFiltro() +
         " AND TRAREGSOLICITUD.LIMPRESO = 1 " +
         //" AND TRAREGSOLICITUD.DTENTREGA IS NULL " +
         //" AND TRARegSolicitud.iNumSolicitud NOT IN (SELECT TRAREGTRAMXSOL.INUMSOLICITUD "+
         //"FROM TRAREGTRAMXSOL WHERE TRAREGTRAMXSOL.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO " +
         //"AND TRAREGTRAMXSOL.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) " +
         "AND (SELECT COUNT(TRAREGETAPASXMODTRAM.IORDEN) FROM TRAREGETAPASXMODTRAM WHERE " +
         "TRAREGETAPASXMODTRAM.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND " +
         "TRAREGETAPASXMODTRAM.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD AND " +
         "TRAREGETAPASXMODTRAM.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE AND " +
         "TRAREGETAPASXMODTRAM.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD) >= 1 " +
         " AND TREXMT.iOrden = " +
         "( " +
         "   SELECT " +
         "   MAX(EMT.iOrden) " +
         "   FROM TRARegEtapasXModTram EMT " +
         "   WHERE TRARegSolicitud.IEJERCICIO = EMT.IEJERCICIO " +
         "   AND TRARegSolicitud.INUMSOLICITUD = EMT.INUMSOLICITUD " +
         "   AND TRARegSolicitud.iCveTramite = EMT.iCveTramite " +
         "   AND TRARegSolicitud.iCveModalidad = EMT.iCveModalidad " +
         ") " +
         "    AND ( SELECT COUNT (1) FROM TRAREGTRAMXSOL WHERE IEJERCICIO=TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD=TRAREGSOLICITUD.INUMSOLICITUD ) = 0 " +
         "    AND ( SELECT COUNT (1) FROM TRAREGETAPASXMODTRAM EM1 WHERE EM1.IEJERCICIO=TRAREGSOLICITUD.IEJERCICIO AND EM1.INUMSOLICITUD=TRAREGSOLICITUD.INUMSOLICITUD AND EM1.ICVEETAPA=8 ) = 0 " +
         oAccion.getCOrden());
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
                '<%=oAccion.getBPK()%>',
                '<%=cEtapas%>');


</script>
<%}%>

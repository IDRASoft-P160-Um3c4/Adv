<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pg111020020A1.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
    try{
      vDinRep = dTRARegSolicitud.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
    try{
      vDinRep = dTRARegSolicitud.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
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
 
  if(oAccion.getCAccion().equals("compFolEnv")){
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");

	    try{
	    	TDTRARecepcion dTRARecepcion= new TDTRARecepcion();
	       dTRARecepcion.comprobarFolioEnv(vDinRep,null);
	      }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
}


 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",
         " Select TRARegSolicitud.iEjercicio, " +
         "        TRARegSolicitud.iNumSolicitud, " +
         "        TRARegSolicitud.iCveTramite, " +
         "        TRACatTramite.cDscBreve,  " +
         "        TRARegSolicitud.iCveModalidad, " +
         "        TRAModalidad.cDscModalidad, " +
         "        TRARegSolicitud.iCveSolicitante, " +
         "        GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cNombreCompleto, " +
         "        TRACatTramite.cCveInterna, " +
         "        TRARegSolicitud.dtEntrega, " +
         "		  CARR.CNOMTITULO, "+
         "        TRARegSolicitud.lpermiso " +
         " from TRARegSolicitud "+
         " left join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite " +
         " left join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad " +
         " left join GRLPersona on GRLPersona.iCvePersona = TRARegSolicitud.iCveSolicitante " +
         " JOIN TRAREGDATOSADVXSOL ADV ON ADV.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO "+
         "		 AND ADV.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD "+
         "		 JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA "+
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

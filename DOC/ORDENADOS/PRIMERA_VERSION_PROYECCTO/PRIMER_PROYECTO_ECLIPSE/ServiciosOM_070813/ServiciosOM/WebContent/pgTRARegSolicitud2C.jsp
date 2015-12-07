<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARegSolicitud2A.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ijimenez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,lImpreso,iCveDomicilioSolicitante,iCveDomicilioRepLegal");
    try{
      vDinRep = dTRARegSolicitud.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,lImpreso,iCveDomicilioSolicitante,iCveDomicilioRepLegal");
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
 StringBuffer cSQL = new StringBuffer("");
		cSQL.append("SELECT ");
		cSQL.append("DISTINCT ");
		cSQL.append("TRARegSolicitud.tsRegistro, ");
		cSQL.append("UR.cNombre as NombreUR, ");
		cSQL.append("UR.cApPaterno as ApellidoPatUR, ");
		cSQL.append("UR.cApMaterno as ApellidoMatUR, ");
		cSQL.append("TRARegSolicitud.dtEntrega, ");
		cSQL.append("UE.cNombre as NombreUE, ");
		cSQL.append("UE.cApPaterno as ApellidoEU, ");
		cSQL.append("UE.cApMaterno as ApellidoUE, ");
		cSQL.append("TRARegSolicitud.dtEstimadaEntrega, ");
		cSQL.append("TRARegSolicitud.dtLimiteEntregaDocs, ");
		cSQL.append("TRARegSolicitud.lPagado, ");
		cSQL.append("TRARegSolicitud.lResolucionPositiva, ");
		cSQL.append("Persol.cNomRazonSocial as Solicitante, ");
		cSQL.append("Persol.cApPaterno as APSolicitante, ");
		cSQL.append("Persol.cApMaterno as AMSolicitante, ");
		cSQL.append("Persol.cRFC as RFCSolicitante, ");
		cSQL.append("DomPer.CCALLE as CallSol, ");
		cSQL.append("DomPer.CNUMEXTERIOR as NumSol, ");
		cSQL.append("DomPer.cNUMINTERIOR AS numintsol, ");
		cSQL.append("DomPer.cCOLONIA as ColSol, ");
		cSQL.append("ESTADOSOL.CNOMBRE as CiudadPer, ");
		cSQL.append("MUNSOL.CNOMBRE as MunPer, ");
		cSQL.append("PerRepLegal.cNomRazonSocial as Representante, ");
		cSQL.append("PerRepLegal.cApPaterno as APRepresentante, ");
		cSQL.append("PerRepLegal.cApMaterno as AMRepresentante, ");
		cSQL.append("PerRepLegal.cRFC as RFCRepresentante, ");
		cSQL.append("DomRep.cCalle as CallRep, ");
		cSQL.append("DomRep.cNumExterior as NumRep, ");
		cSQL.append("DomRep.cNumInterior as NumIntRep, ");
		cSQL.append("DomRep.cColONia as ColRep, ");
		cSQL.append("ESTADOREP.CNOMBRE as CiudadRep, ");
		cSQL.append("MUNREP.CNOMBRE as MunRep, ");
		cSQL.append("TRARegSolicitud.cNomAutorizaRecoger, ");
		cSQL.append("TRARegSolicitud.cEnviarResolucionA, ");
		cSQL.append("TRARegSolicitud.COBSTRAMITE, ");
		cSQL.append("TRARegSolicitud.dtImpresion ");
		cSQL.append("FROM TRARegSolicitud ");
		cSQL.append("LEFT JOIN TRACONfiguraTramite ON TRARegSolicitud.iCveTramite = TRACONfiguraTramite.iCveTramite ");
		cSQL.append("AND TRARegSolicitud.iCveModalidad = TRACONfiguraTramite.iCveModalidad ");
		cSQL.append("JOIN GRLPersONa Persol ON TRARegSolicitud.iCveSolicitante = Persol.iCvePersONa ");
		cSQL.append("JOIN GRLDomicilio DomPer ON TRARegSolicitud.iCveSolicitante = DomPer.ICVEPERSONA ");
		cSQL.append("AND DomPer.ICVEDOMICILIO = TRARegSolicitud.ICVEDOMICILIOSOLICITANTE ");
		cSQL.append("LEFT JOIN GRLENTIDADFED ESTADOSOL ON DOMPER.ICVEPAIS = ESTADOSOL.ICVEPAIS and DomPer.iCveEntidadFed = ESTADOSOL.ICVEENTIDADFED ");
		cSQL.append("LEFT JOIN GRLMUNICIPIO MUNSOL ON DOMPER.ICVEPAIS = MUNSOL.ICVEPAIS and DomPer.iCveEntidadFed = MUNSOL.ICVEENTIDADFED ");
		cSQL.append("AND DOMPER.ICVEMUNICIPIO = MUNSOL.ICVEMUNICIPIO ");
		cSQL.append("JOIN GRLPERSONA PerRepLegal ON TRARegSolicitud.icvereplegal = PerRepLegal.ICVEPERSONA ");
		cSQL.append("JOIN GRLDomicilio DomRep ON TRARegSolicitud.icvereplegal = DomRep.ICVEPERSONA ");
		cSQL.append("AND DomRep.ICVEDOMICILIO = TRARegSolicitud.ICVEDOMICILIOREPLEGAL ");
		cSQL.append("LEFT JOIN GRLENTIDADFED ESTADOREP ON DOMREP.ICVEPAIS = ESTADOREP.ICVEPAIS and DomREP.iCveEntidadFed = ESTADOREP.ICVEENTIDADFED ");
		cSQL.append("LEFT JOIN GRLMUNICIPIO MUNREP ON DOMREP.ICVEPAIS = MUNREP.ICVEPAIS and DomREP.iCveEntidadFed = MUNREP.ICVEENTIDADFED ");
		cSQL.append("AND DOMREP.ICVEMUNICIPIO = MUNREP.ICVEMUNICIPIO ");
//		cSQL.append("JOIN TRAREGETAPASXMODTRAM trexmt ON TRAREGSOLICITUD.IEJERCICIO = trexmt.IEJERCICIO ");
//		cSQL.append("AND TRAREGSOLICITUD.INUMSOLICITUD = trexmt.INUMSOLICITUD AND trexmt.ICVEETAPA = 1 ");
//		cSQL.append("AND TRAREGSOLICITUD.INUMSOLICITUD = trexmt.INUMSOLICITUD AND trexmt.ICVEETAPA = " + vParametros.getPropEspecifica("EtapaRegistro") + " ");
		cSQL.append("JOIN TRAREGETAPASXMODTRAM ON TRARegSolicitud.IEJERCICIO = TRAREGETAPASXMODTRAM.IEJERCICIO ");
		cSQL.append("AND TRARegSolicitud.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD ");
		cSQL.append("LEFT JOIN SEGUsuario UR ON UR.iCveUsuario=TRARegSolicitud.iCveUsuRegistra ");
		cSQL.append("LEFT JOIN SEGUsuario UE ON UE.iCveUsuario=TRARegSolicitud.iCveUsuEntrega ");
		cSQL.append(oAccion.getCFiltro());
//		cSQL.append("AND TRAREGETAPASXMODTRAM.ICVEETAPA=1 ");
  Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",cSQL.toString() );

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

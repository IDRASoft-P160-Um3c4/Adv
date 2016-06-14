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
 * <p>Title: pgTRADatosSolicitud.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TFechas fecha = new TFechas();


  System.out.println("*****     "+fecha.getThisTime());
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
    try{
      vDinRep = dTRARegSolicitud.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
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
    vDinRep = oAccion.setInputs("iEjercicio,iCveTramite");
    try{
       dTRARegSolicitud.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 int iEjercicio    = new Integer(request.getParameter("iEjercicio")).intValue(),
     iNumSolicitud = new Integer(request.getParameter("iNumSolicitud")).intValue();
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 
 /*	 Vector tienePNC = dTRARegSolicitud.findByCustom("",;
 
      TVDinRep vVerPNC = (TVDinRep) tienePNC.get(0);
      boolean lTienePNC = vVerPNC.getInt("iCuenta")>0; */
 
 
 String cSQL =
  "SELECT "+
  "	TRARegSolicitud.iEjercicio, "+
  "	TRARegSolicitud.iNumSolicitud, "+
  "	TRACatTramite.iCveTramite, "+
  "	TRACatTramite.cDscBreve, "+
  "	TRAModalidad.iCveModalidad, "+
  "	TRAModalidad.cDscModalidad, "+//5
  "	PerSol.CRFC as CRFCSOL, "+
  "	PerSol.CNOMRAZONSOCIAL || ' ' || PerSol.CAPPATERNO || ' ' || PerSol.CAPMATERNO AS NomSolicitante, "+
  "	PerRepLegal.CRFC as cRFCRL, "+
  "	PerRepLegal.CNOMRAZONSOCIAL || ' ' || PerRepLegal.CAPPATERNO || ' ' || PerRepLegal.CAPMATERNO as NomRepLegal, "+
  "	PerSol.ICVEPERSONA, "+//10
  "	TRARegSolicitud.iIdBien, "+
  "     DomPer.CCALLE as CallSol, DomPer.CNUMEXTERIOR as NumSol, "+
  "     DomPer.cNUMINTERIOR AS numintsol, "+
  "     DomPer.cCOLONIA as ColSol, "+//15
  "     ESTADOSOL.CNOMBRE as CiudadPer, "+
  "     MUNSOL.CNOMBRE as MunPer, "+
  "     DomRep.cCalle as CallRep, DomRep.cNumExterior as NumRep, "+
  "     DomRep.cNumInterior as NumIntRep, "+//20
  "     DomRep.cColonia as ColRep, "+
  "     ESTADOREP.CNOMBRE as CiudadRep, "+
  "     MUNREP.CNOMBRE as MunRep, "+
  "     DomRep.iCveDomicilio, "+
  "     Persol.CCORREOE as cElectSol, " + //25
  "     PerRepLegal.CCORREOE as cElecRL, " +
  "     PerRepLegal.iCvePersona as Representante, " +
  "     TraRegSolicitud.TSREGISTRO, " +
  "     DomPer.iCvePais, "+
  "     DomPer.iCveEntidadFed, "+ //30
  "     DomPer.iCveMunicipio, " +
  "     DomPer.iCveLocalidad, "+
  "     DomPer.cCodPostal, "+
  "     Persol.ITIPOPERSONA, "+
  "     DomPer.ICVEDOMICILIO as iDomSolicitante, " +//35
  "     TRARegSolicitud.cDscBien, " +
  "     (SELECT count(1) as iCuenta FROM TRAREGPNCETAPA "+
  "		where IEJERCICIO="+iEjercicio+
  " 	and INUMSOLICITUD="+iNumSolicitud+") as existePNC"+
  "     FROM TRARegSolicitud "+
  "     LEFT JOIN TRAConfiguraTramite ON TRAConfiguraTramite.iCveTramite=TRARegSolicitud.iCveTramite AND TRAConfiguraTramite.iCveModalidad=TRARegSolicitud.iCveModalidad "+
  "     JOIN GRLPersona Persol ON Persol.iCvePersona=TRARegSolicitud.iCveSolicitante  "+
  "     JOIN GRLDomicilio DomPer ON DomPer.ICVEPERSONA=TRARegSolicitud.iCveSolicitante AND DomPer.ICVEDOMICILIO = TRARegSolicitud.ICVEDOMICILIOSOLICITANTE "+
  "     JOIN GRLENTIDADFED ESTADOSOL ON ESTADOSOL.ICVEPAIS=DOMPER.ICVEPAIS AND ESTADOSOL.ICVEENTIDADFED=DomPer.iCveEntidadFed "+
  "     JOIN GRLMUNICIPIO MUNSOL ON MUNSOL.ICVEPAIS=DOMPER.ICVEPAIS AND MUNSOL.ICVEENTIDADFED = DomPer.iCveEntidadFed AND MUNSOL.ICVEMUNICIPIO=DOMPER.ICVEMUNICIPIO "+
  "     LEFT JOIN GRLPERSONA PerRepLegal ON PerRepLegal.ICVEPERSONA = TRARegSolicitud.icvereplegal " +
  "     LEFT JOIN GRLDomicilio DomRep ON DomRep.ICVEPERSONA=TRARegSolicitud.icvereplegal and DomRep.ICVEDOMICILIO = TRARegSolicitud.ICVEDOMICILIOREPLEGAL "+
  "     LEFT JOIN GRLENTIDADFED ESTADOREP ON ESTADOREP.ICVEPAIS=DOMREP.ICVEPAIS AND ESTADOREP.ICVEENTIDADFED=DomREP.iCveEntidadFed "+
  "     LEFT JOIN GRLMUNICIPIO MUNREP ON MUNREP.ICVEPAIS=DOMREP.ICVEPAIS AND MUNREP.ICVEENTIDADFED=DomREP.iCveEntidadFed AND MUNREP.ICVEMUNICIPIO=DOMREP.ICVEMUNICIPIO "+
  "     JOIN TRAREGETAPASXMODTRAM trexmt ON trexmt.IEJERCICIO=TRAREGSOLICITUD.IEJERCICIO AND trexmt.INUMSOLICITUD=TRAREGSOLICITUD.INUMSOLICITUD and trexmt.ICVEETAPA = " + vParametros.getPropEspecifica("EtapaRecepcion") + " " +
  "     JOIN TRACatTramite ON TRAConfiguraTramite.iCveTramite = TRACatTramite.iCveTramite " +
  "     JOIN TRAModalidad ON TRAConfiguraTramite.iCveModalidad = TRAModalidad.iCveModalidad " +
  " 	JOIN TRAREGRESOLVTECXSOL ON TRAREGSOLICITUD.IEJERCICIO= TRAREGRESOLVTECXSOL.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD= TRAREGRESOLVTECXSOL.INUMSOLICITUD "+	
  oAccion.getCFiltro() +
  " AND (select count(TRAREGTRAMXSOL.INUMSOLICITUD) FROM TRAREGTRAMXSOL WHERE TRAREGTRAMXSOL.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND TRAREGTRAMXSOL.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) = 0  "+
  " and TRARegSolicitud.dtRecepcion is not null"+
  " and TRARegSolicitud.LABANDONADA = 0";
  /*
  if(request.getParameter("lOficinas").toString().equals("true")){
    cSQL += " AND "+request.getParameter("cUsuario")+" in ( SELECT U.ICVEUSUARIO FROM TRATRAMITEXOFIC TXO ";
    cSQL += "JOIN GRLUSUARIOXOFIC DXO ON TXO.ICVEOFICINARESUELVE = DXO.ICVEOFICINA ";
    if(!oAccion.getCAccion().equals("Certificacion"))
      cSQL += " AND TXO.ICVEDEPTORESUELVE = DXO.ICVEDEPARTAMENTO ";
    cSQL += " JOIN SEGUSUARIO U ON U.ICVEUSUARIO = DXO.ICVEUSUARIO "+
            "WHERE TXO.ICVETRAMITE = TRACatTramite.iCveTramite )";
  }*/
  cSQL +=oAccion.getCOrden();
  Vector vcListado = dTRARegSolicitud.findSolicitud("iEjercicio,iCveTramite", cSQL,iEjercicio,iNumSolicitud);
  System.out.println("*****     "+fecha.getThisTime());
  System.out.println("*****     ");
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

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
 * <p>Title: pgMYREmpresa.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad MYREmpresa</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDMYREmpresa dMYREmpresa = new TDMYREmpresa();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TFechas Fecha = new TFechas("44");
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveOficina,cRFC,iCvePersona,cNombre,iEjercicio,iNumSolicitud,dtRegistro,iRamo,cSintesis,iCveSeccion,iCveTipoAsiento,cDscCalificacion,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCvePais");
    try{
      vDinRep = dMYREmpresa.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOficina,iFolioRPMN,iPartida,cRFC,iCvePersona,cNombre,iEjercicio,iNumSolicitud,dtRegistro,iRamo,cSintesis,iCveSeccion,iCveTipoAsiento,cDscCalificacion,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCvePais");
    try{
      vDinRep = dMYREmpresa.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("ActEmpresa")){
	    vDinRep = oAccion.setInputs("iEjercicioIns,iCveOficina,iFolioRPMN,iPartida,iCvePersona");
	    try{
	      vDinRep = dMYREmpresa.updEmpresa(vDinRep,null);
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }
 /** Verifica si la Acción a través de hdBotón es igual a "IntegraEmpresa" (Actualizar) */
  if(oAccion.getCAccion().equals("IntegraEmpresa")){
    vDinRep = oAccion.setInputs("iCvePersonaD,iCvePersonaO,iEjercicioIns,iCveOficina,iFolioRPMN,iPartida");
    try{
      vDinRep = dMYREmpresa.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOficina,iFolioRPMN,iPartida");
    try{
       dMYREmpresa.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  String cHistorico = "";
  if(oAccion.getCAccion().equals("ConsultaRPMN"))
    cHistorico = "";
  else
    cHistorico = "";
  //  cHistorico = "  AND MYRRPMN.lHistorico=0  ";

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSQL = " Select MYREmpresa.iCveOficina, " + //0
         " MYREmpresa.iFolioRPMN, " +
         " MYREmpresa.iPartida, " +
         " MYREmpresa.cRFC, " +
         " MYREmpresa.iCvePersona, " +
         " cCalle, " + //5
         " cNumExterior, " +
         " MYREmpresa.cNomRazonSocial, " +
         " cNumInterior, " +
         " MYREmpresa.cColonia, " +
         " MYREmpresa.cCodPostal, " + //10
         " MYREmpresa.iCveEntidadFed, " +
         " MYREmpresa.iCveMunicipio, " +
         " iCveLocalidad, " +
         " MYREmpresa.iCvePais, " +
         " cDscSeccion, " + //15
         " cDscTipoAsiento, " +
         " '' || rtrim(char(MYREmpresa.iCveOficina)) || ' - ' || rtrim(char(MYREmpresa.iFolioRPMN)) as cfolio, " +
         " MYRRPMN.iCveTipoAsiento, " +
         " '' as cSintesis, " +
         " GRLOficina.cTitular, " + //20
         " GRLOficina.cDscBreve, " +
         " MYRRPMN.iCveSeccion, " +
         " MYRRPMN.iPartidaCancela, " +
         " MYRCapitania.cSiglasOficina, " +//24
         " MYRCapitania.cDscCapitania, " + //25
         " MYRRPMN.DTREGISTRO, " +
         " MYRTIPOACTO.ICVETIPOACTO,"+
         " MYRTIPOACTO.CDSCTIPOACTO, " +
         " MYRRPMN.dtIngreso, " + //29
         " MYRRPMN.lRegFinalizado, " + //30
         " MYREmpresa.dPartExtranjera, " +
         " GRLPersona.iTipoPersona, " +
         " MYRSeccion.cReferencia, " +
         " GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno AS cNomRazSoc, " +
         " 0 as cRefAlfaNum, " + //35
         " MYRRPMN.cDscDocumento, " +
         //" TRAConceptoPago.cDscConcepto, "+ //Estos campo se reemplazaro por 0 para no afectar la captura de los datos en el js
         " 0 as cDscConcepto, "+
         " MYRRAMO.cDscRamo, " +
         " GRLOficina.iCveEntidadFed as iEntidadO, " +
         " GRLOficina.iCveMunicipio as iMunicipioO, " + //40
         " GRLEntidadFed.cNombre AS cEntidad, " +
         " GRLMunicipio.cNombre AS cMunicipio, " +
         " MYRRPMN.iEjercicioIns, " +
         " TRARegSolicitud.iEjercicio, " +
         " TRARegSolicitud.iNumSolicitud, " + //45
         " 0 as cTemp, " +
         " 0 as cTemp2, " +
         " MYRRPMN.iCveOficinaFolioAnt, " +
         " MYRRPMN.cFolioRPMNAnt, " +
         " '' as cPropietario, " +  //50
         " MYRRPMN.IEJERCICIOOBSLARGA, " +
         " MYRRPMN.ICVEOBSLARGA, " +
         " ol.CDSCOBSLARGA, " +
         " MYRRPMN.lHistorico, " +
         " MYRRPMN.CTITULAR, "+//55
         " OFI.CDSCOFICINA, "+
         " EFReg.cNombre as cEfReg, "+
         " MUNReg.cNombre as cMunReg, "+
         " TRAREGSOLICITUD.ICVETRAMITE, "+//59
         " EFREG.ICVEENTIDADFED as iEntFedReg, "+//60
         " MUNREG.ICVEMUNICIPIO AS iMunReg, "+
         " '',"+
         " MYRRPMN.iCveOficinaReg, "+//63
         " OFIREG.CDSCBREVE AS COFICINAREG, "+
         " OFFOLIOANTE.CDSCBREVE AS COFFOLIOANTE, "+
         " MYRRPMN.dtCancelaFolio, " +
         " MYRRPMN.ICVEOFICINA, " +
         " MYRRPMN.iEJERCICIOINS "+
         " FROM MYREmpresa " +
         " LEFT JOIN MYRRPMN ON MYRRPMN.iEjercicioIns = MYREmpresa.iEjercicioIns AND MYRRPMN.iCveOficina = MYREmpresa.iCveOficina " +
         "       AND MYRRPMN.iFolioRPMN  = MYREmpresa.iFolioRPMN " +
         "       AND MYRRPMN.iPartida    = MYREmpresa.iPartida " + cHistorico +
         " left JOIN GRLObservaLarga ol ON ol.IEJERCICIOOBSLARGA = MYRRPMN.IEJERCICIOOBSLARGA AND ol.ICVEOBSLARGA = MYRRPMN.ICVEOBSLARGA " +
         " LEFT JOIN GRLPersona ON GRLPersona.iCvePersona = MYREmpresa.iCvePersona " +
         " LEFT JOIN GRLOficina on GRLOficina.iCveOficina = MYREmpresa.iCveOficina " +
         " LEFT JOIN GRLEntidadFed on GRLEntidadFed.iCvePais = " + vParametros.getPropEspecifica("PaisDef") + " AND GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
         " LEFT JOIN GRLMunicipio ON GRLMunicipio.iCvePais = "+vParametros.getPropEspecifica("PaisDef")+" AND GRLMunicipio.iCveEntidadFed = GRLEntidadFed.iCveEntidadFed AND GRLMunicipio.iCveMunicipio = GRLOficina.iCveMunicipio " +
         " LEFT JOIN MYRCapitania ON MYRCapitania.iCveOficina = MYRRPMN.iCveOficina " +
         " LEFT JOIN MYRRAMO on MYRRAMO.ICVERAMO = MYRRPMN.ICVERAMO "+
         " LEFT JOIN MYRSeccion on MYRSECCION.ICVERAMO = MYRRAMO.ICVERAMO AND  MYRSeccion.iCveSeccion = MYRRPMN.iCveSeccion "+
         " LEFT JOIN MYRTipoActo on MYRTipoActo.ICVERAMO = MYRRPMN.ICVERAMO and MYRTipoActo.ICVESECCION = MYRRPMN.ICVESECCION AND  MYRTipoActo.ICVETIPOACTO = MYRRPMN.ICVETIPOACTO " +
         " LEFT JOIN MYRTipoAsiento on MYRTipoAsiento.iCveTipoAsiento = MYRRPMN.iCveTipoAsiento " +
         " LEFT JOIN TRARegSolicitud on TRARegSolicitud.iEjercicio = MYRRPMN.iEjercicio AND TRARegSolicitud.iNumSolicitud = MYRRPMN.iNumSolicitud "+
         " LEFT JOIN GRLOFICINA OFI ON OFI.ICVEOFICINA = MYRRPMN.iCveOficinaReg "+
         " LEFT JOIN GRLEntidadFed EFReg on EFReg.iCvePais = " + vParametros.getPropEspecifica("PaisDef") + " AND EFReg.iCveEntidadFed = OFI.iCveEntidadFed " +
         " LEFT JOIN GRLMunicipio MUNReg ON MUNReg.iCvePais = " + vParametros.getPropEspecifica("PaisDef") + " AND MUNReg.iCveEntidadFed = OFI.iCveEntidadFed AND MUNReg.iCveMunicipio = OFI.iCveMunicipio "+
         " LEFT JOIN GRLOFICINA OFIREG ON OFIREG.ICVEOFICINA = MYRRPMN.iCveOficinaReg "+
         " LEFT JOIN GRLOFICINA OFFOLIOANTE ON OFFOLIOANTE.ICVEOFICINA = MYRRPMN.iCveOficinaFolioAnt ";
         //System.out.println(cSQL + oAccion.getCFiltro() + oAccion.getCOrden());

  Vector vcListado = dMYREmpresa.findByCustom("iCveOficina,iFolioRPMN,iPartida", cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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
                '<%=Fecha.getFechaDDMMYYYY(Fecha.TodaySQL(),"/")%>');
</script>
<%}%>

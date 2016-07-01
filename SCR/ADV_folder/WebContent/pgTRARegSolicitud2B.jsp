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
 * <p>Title: pgTRARegSolicitud2.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */

  TFechas fecha = new TFechas();
  //System.out.println("*****     "+fecha.getThisTime());
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
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma");
    try{
      vDinRep = dTRARegSolicitud.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
    try{
      vDinRep = dTRARegSolicitud.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  /** Verifica si la Acción a través de hdBotón es igual a "GuardarCambios" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarCambios")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cNomAutorizaRecoger");
    try{
      vDinRep = dTRARegSolicitud.updateAutorizaRec(vDinRep,null);
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

  if(oAccion.getCAccion().equals("UpdateImpresoB")){
    vDinRep = oAccion.setInputs("cFiltroImpreso,idUser");
    try{
      vDinRep = dTRARegSolicitud.updateImpresoB(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
StringBuffer sb = new StringBuffer();
sb.append("SELECT ");
sb.append("RS.iEjercicio, ");//0
sb.append("RS.iNumSolicitud, ");
sb.append("RS.iCveTramite, ");
sb.append("RS.iCveModalidad , ");
sb.append("RS.iCveSolicitante, ");
sb.append("RS.iCveRepLegal, ");//5
sb.append("RS.cNomAutorizaRecoger, ");
sb.append("RS.tsRegistro , ");
sb.append("RS.dtLimiteEntregaDocs, ");
sb.append("RS.dtEstimadaEntrega, ");
sb.append("RS.lPagado, ");//10
sb.append("RS.lPrincipal , ");
sb.append("RS.lImpreso, ");
sb.append("RS.cEnviarResolucionA, ");
sb.append("RS.cObsTramite , ");
sb.append("T.cCveInterna || ' - ' || T.cDscBreve AS cCveDscTramite , ");//15
sb.append("D.iCveDomicilio, ");
sb.append("D.iCveTipoDomicilio, ");
sb.append("D.cCalle, ");
sb.append("D.cNumExterior , ");
sb.append("D.cNumInterior, ");//20
sb.append("D.cColonia, ");
sb.append("D.cCodPostal, ");
sb.append("D.cTelefono, ");
sb.append("D.iCvePais , ");
sb.append("GRLLocalidad.cNombre, ");//25
sb.append("GRLpais.cNombre as pais, ");
sb.append("D.iCveEntidadFed, ");
sb.append("GRLENTIDADFED.CNOMBRE as estado, ");
sb.append("GRLMUNICIPIO.CNOMBRE as municipio , ");
sb.append("D.iCveMunicipio, "); //30
sb.append("GRLLocalidad.iCveLocalidad, ");
sb.append("grltipodomicilio.cdsctipodomicilio, ");
sb.append("D.lPredeterminado , ");
sb.append("D.cCalle || ' No. ' || D.cNumExterior || ' Int. ' || D.cNumInterior || ' COL. ' || D.cColonia || ', ' || GRLMunicipio.cNombre || ' (' || GRLEntidadFed.cAbreviatura ||'.)' || ' C.P. ' || D.cCodPostal AS cDomicilio , ");
sb.append("P.cRFC AS cRFCRepLegal, ");//35
sb.append("P.iTipoPersona AS iTipoPersonaRepLegal, ");
sb.append("P.cNomRazonSocial AS cNomRazonSocialRepLegal, ");
sb.append("P.cApPaterno AS cApPaternoRepLegal, ");
sb.append("P.cApMaterno AS cApMaternoRepLegal, ");
sb.append("PS.CNOMRAZONSOCIAL || ' ' || PS.CAPPATERNO || ' ' || PS.CAPMATERNO AS CNOMBREPRSONA, "); //40
sb.append("DP.CCALLE || ' NO. ' || DP.CNUMEXTERIOR || ' INT. ' || DP.CNUMINTERIOR || ' COL. ' || DP.CCOLONIA || ',' || MUNP.CNOMBRE || ' (' || EFP.CABREVIATURA ||'.)' || ' C.P. ' || DP.CCODPOSTAL AS CDOMICILIOPERSONA , ");
sb.append("PS.CRFC, ");
sb.append("DP.CCALLE AS CCALLEPER, ");
sb.append("DP.CNUMEXTERIOR AS CNUMENTERIORPER , ");
sb.append("DP.CNUMINTERIOR AS CNUMINTERIORPER , "); //45
sb.append("DP.CCOLONIA AS CCOLONIAPER , ");
sb.append("MUNP.CNOMBRE AS CNOMBREMUNPER , ");
sb.append("EFP.CABREVIATURA AS CABREVMUNPER , ");
sb.append("DP.CCODPOSTAL AS CCODPOSTALPER, ");
sb.append("PS.CNOMRAZONSOCIAL AS CNOMPERSOLIC, "); //50
sb.append("PS.CAPPATERNO AS CAPPATERNOSOLIC, ");
sb.append("PS.CAPMATERNO AS CAPMATERNOSOLIC, ");
sb.append("DP.ICVEPAIS AS ICVEPAISSOLIC, ");
sb.append("DP.ICVEENTIDADFED AS ICVEENTIDADSOLIC, ");
sb.append("DP.ICVEMUNICIPIO AS ICVEMUNICSOLIC, "); //55
sb.append("(SELECT COUNT(1) FROM TRAREGREFPAGO RP WHERE RP.IEJERCICIO = RS.IEJERCICIO AND RP.INUMSOLICITUD = RS.INUMSOLICITUD AND RP.LPAGADO = 0) AS INUMPENDPAGO, ");
sb.append("RS.LRESOLUCIONPOSITIVA, ");
sb.append("RS.DUNIDADESCALCULO, ");
sb.append("RS.iIdBien "); //59
sb.append("FROM TRARegSolicitud RS ");
sb.append("LEFT JOIN TRARegTramXSol ON TRARegTramXSol.iEjercicio = RS.iEjercicio ");
sb.append("AND TRARegTramXSol.iNumSolicitud = RS.iNumSolicitud ");
sb.append("JOIN TRACatTramite T ON RS.iCveTramite = T.iCveTramite ");
sb.append("JOIN TRARegEtapasXModTram ET ON ET.iEjercicio = RS.iEjercicio ");
sb.append("AND ET.iNumSolicitud = RS.iNumSolicitud ");
sb.append("AND ET.ICVETRAMITE = RS.ICVETRAMITE ");
sb.append("AND ET.ICVEMODALIDAD = RS.ICVEMODALIDAD ");
sb.append("AND ET.iCveEtapa = " +vParametros.getPropEspecifica("EtapaRecibeResolucion"));
sb.append(" join GRLDomicilio D ON D.iCvePersona = RS.iCveRepLegal ");
sb.append("AND D.iCveDomicilio = RS.iCveDomicilioRepLegal ");
sb.append("join GRLPais ON GRLPais.iCvePais = D.iCvePais ");
sb.append("join GRLEntidadFed ON GRLEntidadFed.iCvePais = D.iCvePais ");
sb.append("AND GRLEntidadFed.iCveEntidadFed = D.iCveEntidadFed ");
sb.append("join GRLMunicipio ON GRLMunicipio.iCvePais = D.iCvePais ");
sb.append("AND GRLMunicipio.iCveEntidadFed = D.iCveEntidadFed ");
sb.append("AND GRLMunicipio.iCveMunicipio = D.iCveMunicipio ");
sb.append("left join GRLLocalidad ON D.iCvePais = GRLLocalidad.iCvePais ");
sb.append("AND GRLLocalidad.iCveEntidadFed = D.iCveEntidadFed ");
sb.append("AND GRLLocalidad.iCveMunicipio = D.iCveMunicipio ");
sb.append("AND GRLLocalidad.iCveLocalidad = D.iCveLocalidad ");
sb.append("join grlTipoDomicilio ON GRLTipoDomicilio.iCveTipoDomicilio = D.iCveTipoDomicilio ");
sb.append("join GRLPersona P ON P.iCvePersona = RS.iCveRepLegal ");
sb.append("JOIN GRLDOMICILIO DP ON DP.ICVEPERSONA = RS.ICVESOLICITANTE ");
sb.append("AND DP.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE ");
sb.append("JOIN GRLPAIS PAISP ON PAISP.ICVEPAIS = DP.ICVEPAIS ");
sb.append("JOIN GRLENTIDADFED EFP ON EFP.ICVEPAIS = DP.ICVEPAIS ");
sb.append("AND EFP.ICVEENTIDADFED = DP.ICVEENTIDADFED ");
sb.append("JOIN GRLMUNICIPIO MUNP ON MUNP.ICVEPAIS = DP.ICVEPAIS ");
sb.append("AND MUNP.ICVEENTIDADFED = DP.ICVEENTIDADFED ");
sb.append("AND MUNP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO ");
sb.append("left JOIN GRLLOCALIDAD LOCP ON DP.ICVEPAIS = LOCP.ICVEPAIS ");
sb.append("AND LOCP.ICVEENTIDADFED = DP.ICVEENTIDADFED ");
sb.append("AND LOCP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO ");
sb.append("AND LOCP.ICVELOCALIDAD = DP.ICVELOCALIDAD ");
sb.append("JOIN GRLPERSONA PS ON PS.ICVEPERSONA = RS.ICVESOLICITANTE ");
sb.append(oAccion.getCFiltro());
sb.append(" and TRARegTramXSol.dtCancelacion IS NULL ");
sb.append("AND ET.iOrden = ");
sb.append("( ");
sb.append("   SELECT ");
sb.append("   MAX(ET3.iOrden) ");
sb.append("   FROM TRARegEtapasXModTram ET3 ");
sb.append("   WHERE ET3.iEjercicio = RS.iEjercicio ");
sb.append("   AND ET3.iNumSolicitud = RS.iNumSolicitud ");
sb.append("   AND ET3.iCveTramite = RS.iCveTramite ");
sb.append("   AND ET3.iCveModalidad = RS.iCveModalidad ");
sb.append(") ");
sb.append("UNION ");
sb.append("SELECT ");
sb.append("RS.iEjercicio, ");//0
sb.append("RS.iNumSolicitud, ");
sb.append("RS.iCveTramite, ");
sb.append("RS.iCveModalidad , ");
sb.append("RS.iCveSolicitante, ");
sb.append("RS.iCveRepLegal, ");//5
sb.append("RS.cNomAutorizaRecoger, ");
sb.append("RS.tsRegistro , ");
sb.append("RS.dtLimiteEntregaDocs, ");
sb.append("RS.dtEstimadaEntrega, ");
sb.append("RS.lPagado, ");//10
sb.append("RS.lPrincipal , ");
sb.append("RS.lImpreso, ");
sb.append("RS.cEnviarResolucionA, ");
sb.append("RS.cObsTramite , ");
sb.append("T.cCveInterna || ' - ' || T.cDscBreve AS cCveDscTramite , ");//15
sb.append("D.iCveDomicilio, ");
sb.append("D.iCveTipoDomicilio, ");
sb.append("D.cCalle, ");
sb.append("D.cNumExterior , ");
sb.append("D.cNumInterior, ");//20
sb.append("D.cColonia, ");
sb.append("D.cCodPostal, ");
sb.append("D.cTelefono, ");
sb.append("D.iCvePais , ");
sb.append("GRLLocalidad.cNombre, ");//25
sb.append("GRLpais.cNombre as pais, ");
sb.append("D.iCveEntidadFed, ");
sb.append("GRLENTIDADFED.CNOMBRE as estado, ");
sb.append("GRLMUNICIPIO.CNOMBRE as municipio , ");
sb.append("D.iCveMunicipio, "); //30
sb.append("GRLLocalidad.iCveLocalidad, ");
sb.append("grltipodomicilio.cdsctipodomicilio, ");
sb.append("D.lPredeterminado , ");
sb.append("D.cCalle || ' No. ' || D.cNumExterior || ' Int. ' || D.cNumInterior || ' COL. ' || D.cColonia || ', ' || GRLMunicipio.cNombre || ' (' || GRLEntidadFed.cAbreviatura ||'.)' || ' C.P. ' || D.cCodPostal AS cDomicilio , ");
sb.append("P.cRFC AS cRFCRepLegal, ");//35
sb.append("P.iTipoPersona AS iTipoPersonaRepLegal, ");
sb.append("P.cNomRazonSocial AS cNomRazonSocialRepLegal, ");
sb.append("P.cApPaterno AS cApPaternoRepLegal, ");
sb.append("P.cApMaterno AS cApMaternoRepLegal, ");
sb.append("PS.CNOMRAZONSOCIAL || ' ' || PS.CAPPATERNO || ' ' || PS.CAPMATERNO AS CNOMBREPRSONA, "); //40
sb.append("DP.CCALLE || ' NO. ' || DP.CNUMEXTERIOR || ' INT. ' || DP.CNUMINTERIOR || ' COL. ' || DP.CCOLONIA || ',' || MUNP.CNOMBRE || ' (' || EFP.CABREVIATURA ||'.)' || ' C.P. ' || DP.CCODPOSTAL AS CDOMICILIOPERSONA , ");
sb.append("PS.CRFC, ");
sb.append("DP.CCALLE AS CCALLEPER, ");
sb.append("DP.CNUMEXTERIOR AS CNUMENTERIORPER , ");
sb.append("DP.CNUMINTERIOR AS CNUMINTERIORPER , "); //45
sb.append("DP.CCOLONIA AS CCOLONIAPER , ");
sb.append("MUNP.CNOMBRE AS CNOMBREMUNPER , ");
sb.append("EFP.CABREVIATURA AS CABREVMUNPER , ");
sb.append("DP.CCODPOSTAL AS CCODPOSTALPER, ");
sb.append("PS.CNOMRAZONSOCIAL AS CNOMPERSOLIC, "); //50
sb.append("PS.CAPPATERNO AS CAPPATERNOSOLIC, ");
sb.append("PS.CAPMATERNO AS CAPMATERNOSOLIC, ");
sb.append("DP.ICVEPAIS AS ICVEPAISSOLIC, ");
sb.append("DP.ICVEENTIDADFED AS ICVEENTIDADSOLIC, ");
sb.append("DP.ICVEMUNICIPIO AS ICVEMUNICSOLIC, "); //55
sb.append("(SELECT COUNT(1) FROM TRAREGREFPAGO RP WHERE RP.IEJERCICIO = RS.IEJERCICIO AND RP.INUMSOLICITUD = RS.INUMSOLICITUD AND RP.LPAGADO = 0) AS INUMPENDPAGO, ");
sb.append("RS.LRESOLUCIONPOSITIVA, ");
sb.append("RS.DUNIDADESCALCULO, ");
sb.append("RS.iIdBien "); //59
sb.append("FROM TRARegSolicitud RS ");
sb.append("LEFT JOIN TRARegTramXSol ON TRARegTramXSol.iEjercicio = RS.iEjercicio ");
sb.append("AND TRARegTramXSol.iNumSolicitud = RS.iNumSolicitud ");
sb.append("JOIN TRACatTramite T ON RS.iCveTramite = T.iCveTramite ");
sb.append("JOIN TRARegEtapasXModTram ET2 ON ET2.iEjercicio = RS.iEjercicio ");
sb.append("AND ET2.iNumSolicitud = RS.iNumSolicitud ");
sb.append("AND ET2.ICVETRAMITE = RS.ICVETRAMITE ");
sb.append("AND ET2.ICVEMODALIDAD = RS.ICVEMODALIDAD ");
sb.append("AND ET2.iCveEtapa = " +vParametros.getPropEspecifica("EtapaResEnviadaOficialia"));
sb.append(" join GRLDomicilio D ON D.iCvePersona = RS.iCveRepLegal ");
sb.append("AND D.iCveDomicilio = RS.iCveDomicilioRepLegal ");
sb.append("join GRLPais ON GRLPais.iCvePais = D.iCvePais ");
sb.append("join GRLEntidadFed ON GRLEntidadFed.iCvePais = D.iCvePais ");
sb.append("AND GRLEntidadFed.iCveEntidadFed = D.iCveEntidadFed ");
sb.append("join GRLMunicipio ON GRLMunicipio.iCvePais = D.iCvePais ");
sb.append("AND GRLMunicipio.iCveEntidadFed = D.iCveEntidadFed ");
sb.append("AND GRLMunicipio.iCveMunicipio = D.iCveMunicipio ");
sb.append("left join GRLLocalidad ON D.iCvePais = GRLLocalidad.iCvePais ");
sb.append("AND GRLLocalidad.iCveEntidadFed = D.iCveEntidadFed ");
sb.append("AND GRLLocalidad.iCveMunicipio = D.iCveMunicipio ");
sb.append("AND GRLLocalidad.iCveLocalidad = D.iCveLocalidad ");
sb.append("join grlTipoDomicilio ON GRLTipoDomicilio.iCveTipoDomicilio = D.iCveTipoDomicilio ");
sb.append("join GRLPersona P ON P.iCvePersona = RS.iCveRepLegal ");
sb.append("JOIN GRLDOMICILIO DP ON DP.ICVEPERSONA = RS.ICVESOLICITANTE ");
sb.append("AND DP.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE ");
sb.append("JOIN GRLPAIS PAISP ON PAISP.ICVEPAIS = DP.ICVEPAIS ");
sb.append("JOIN GRLENTIDADFED EFP ON EFP.ICVEPAIS = DP.ICVEPAIS ");
sb.append("AND EFP.ICVEENTIDADFED = DP.ICVEENTIDADFED ");
sb.append("JOIN GRLMUNICIPIO MUNP ON MUNP.ICVEPAIS = DP.ICVEPAIS ");
sb.append("AND MUNP.ICVEENTIDADFED = DP.ICVEENTIDADFED ");
sb.append("AND MUNP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO ");
sb.append("left JOIN GRLLOCALIDAD LOCP ON DP.ICVEPAIS = LOCP.ICVEPAIS ");
sb.append("AND LOCP.ICVEENTIDADFED = DP.ICVEENTIDADFED ");
sb.append("AND LOCP.ICVEMUNICIPIO = DP.ICVEMUNICIPIO ");
sb.append("AND LOCP.ICVELOCALIDAD = DP.ICVELOCALIDAD ");
sb.append("JOIN GRLPERSONA PS ON PS.ICVEPERSONA = RS.ICVESOLICITANTE ");
sb.append(oAccion.getCFiltro());
sb.append(" and TRARegTramXSol.dtCancelacion IS NULL ");
sb.append("AND ET2.iOrden = ");
sb.append("( ");
sb.append("   SELECT ");
sb.append("   MAX(ET3.iOrden) ");
sb.append("   FROM TRARegEtapasXModTram ET3 ");
sb.append("   WHERE ET3.iEjercicio = RS.iEjercicio ");
sb.append("   AND ET3.iNumSolicitud = RS.iNumSolicitud ");
sb.append("   AND ET3.iCveTramite = RS.iCveTramite ");
sb.append("   AND ET3.iCveModalidad = RS.iCveModalidad ");
sb.append(") ");
sb.append("ORDER BY IEJERCICIO, INUMSOLICITUD");

  Vector vcListado = new Vector(), vcTemp = new Vector(), vcResultado = new Vector();
  String cSQL2 = "", cSQL3="";

  try{
    vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",sb.toString());
    if (vcListado != null && vcListado.size() > 0){
      TVDinRep vDataSol = new TVDinRep();
      for (int i=0; i<vcListado.size(); i++){
        vDataSol = (TVDinRep)vcListado.get(i);
        cSQL2 = "SELECT CT.lRequierePago "+
                " FROM TRAConfiguraTramite CT "+
                " WHERE CT.iCveTramite = " + vDataSol.getString("iCveTramite") +
                "   AND CT.iCvemodalidad = " + vDataSol.getString("iCveModalidad") +
                " ORDER BY CT.dtIniVigencia DESC";
        try{
          vcTemp = dTRARegSolicitud.findByCustom("iCveTramite,iCveModalidad,dtIniVigencia",cSQL2);
          if (vcTemp != null && vcTemp.size()>0 && ((TVDinRep)vcTemp.get(0)).getInt("lRequierePago")==1){
            cSQL3 = "SELECT RP.iRefNumerica, RP.cRefAlfaNum, RP.lPagado, RP.dtPago "+
                    " FROM TRARegRefPago RP "+
                    " WHERE RP.iEjercicio = " + vDataSol.getString("iEjercicio") +
                    "   AND RP.iNumSolicitud = " + vDataSol.getString("iNumSolicitud")+
                    "   AND RP.lPagado = 0 ";
            try{
              vcTemp = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud,iConsecutivo", cSQL3);
              if(vcTemp != null){
                if (vcTemp.size()>0)
                  vDataSol.put("lPagado", 0);
                else
                  vDataSol.put("lPagado", 1);
              }else
                vDataSol.put("lPagado", 1);
            }catch(Exception e){
              vDataSol.put("lPagado", 0);
            }
          }else
            vDataSol.put("lPagado", 1);
        }catch(Exception e){
          vDataSol.put("lPagado", 0);
        }
        vcResultado.add(vDataSol);
      }
      vcListado = vcResultado;
    }
  //System.out.println("*****     "+fecha.getThisTime());
  //System.out.println("*****     ");
  }catch(Exception e){
    cError = e.getMessage();
  }
  oAccion.setINumReg(vcListado.size());
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

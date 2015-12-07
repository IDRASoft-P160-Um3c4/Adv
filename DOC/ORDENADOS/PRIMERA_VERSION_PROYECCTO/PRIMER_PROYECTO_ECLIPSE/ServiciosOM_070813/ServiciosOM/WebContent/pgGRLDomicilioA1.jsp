<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLDomicilioA1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLDomicilio</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLDomicilioA1 dGRLDomicilio = new TDGRLDomicilioA1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCvePersona,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,lPredeterminado");

      try{
      vDinRep = dGRLDomicilio.insert(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePersona,iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,lPredeterminado");
    try{
      vDinRep = dGRLDomicilio.update(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCvePersona,iCveDomicilio");
    try{
       dGRLDomicilio.delete(vDinRep,null);

       String cSql2="select iCvePersona,iCveDomicilio, lPredeterminado from GRLDomicilio " +
                    "where iCvePersona=" +  vDinRep.getInt("iCvePersona") + " and lPredeterminado=1 ";
      Vector vcData2 = dGRLDomicilio.findByCustom("",cSql2);
      if (vcData2.size() == 0){
        String cSql3="select iCvePersona,iCveDomicilio, lPredeterminado from GRLDomicilio " +
                     "where iCvePersona=" +  vDinRep.getInt("iCvePersona");
        Vector vcData3 = dGRLDomicilio.findByCustom("",cSql3);
        if (vcData3.size()>0){
          TVDinRep dinRepTemp = (TVDinRep) vcData3.get(0);
          dinRepTemp.put("lPredeterminado", 1);
          dGRLDomicilio.updatePredeterminado(dinRepTemp, null);
        }
      }
    }catch(Exception e){
      cError=e.getMessage();
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cFiltro = oAccion.getCFiltro();
  String cFrame = "";
  TVDinRep vFiltro = oAccion.setInputs("cNombreFRM,hdFiltro11,hdFiltro11A,hdFiltro12,hdFiltro13,hdFiltro14");
  cFrame = vFiltro.getString("cNombreFRM");//request.getParameter("cNombreFRM");
  if (cFrame.equals("FRMCuerpo") || cFrame.equals(""))
    cFiltro = vFiltro.getString("hdFiltro11");//request.getParameter("hdFiltro11");
  if (cFrame.equals("PEM1"))
    cFiltro = vFiltro.getString("hdFiltro11A");//request.getParameter("hdFiltro11A");
  if (cFrame.equals("PEM2"))
    cFiltro = vFiltro.getString("hdFiltro12");//request.getParameter("hdFiltro12");
  if (cFrame.equals("PEM3"))
    cFiltro = vFiltro.getString("hdFiltro13");//request.getParameter("hdFiltro13");
  if (cFrame.equals("PEM4"))
    cFiltro = vFiltro.getString("hdFiltro14");//request.getParameter("hdFiltro14");
  if (!cFiltro.equals(""))
    cFiltro = " where " + cFiltro + " ";
String cSql= "Select iCvePersona,iCveDomicilio,GRLDomicilio.iCveTipoDomicilio,cCalle,cNumExterior"+
               ",cNumInterior,cColonia,cCodPostal,cTelefono,GRLDomicilio.iCvePais"+
               ",GRLLocalidad.cNombre,GRLpais.cNombre as pais,GRLDomicilio.iCveentidadFed,GRLENTIDADFED.CNOMBRE as estado,GRLMUNICIPIO.CNOMBRE as municipio"+
               ",GRLDomicilio.iCveMunicipio,GRLLocalidad.iCveLocalidad,grltipodomicilio.cdsctipodomicilio,lPredeterminado" +
               ",cCalle || ' No. ' || cNumExterior || ' Int. ' || cNumInterior || ' COL. ' || cColonia || ', ' || GRLMunicipio.cNombre || ' (' || GRLEntidadFed.cAbreviatura ||'.)' || ' C.P. ' || cCodPostal AS cDomicilio"+
               " from GRLDomicilio " +
               "join GRLPAIS ON GRLDomicilio.ICVEPAIS = GRLPAIS.ICVEPAIS  " +
               "join GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLPAIS.ICVEPAIS AND GRLENTIDADFED.ICVEENTIDADFED = GRLDomicilio.ICVEENTIDADFED " +
               "join GRLMUNICIPIO ON  GRLMUNICIPIO.icvepais = grlpais.icvepais and GRLMUNICIPIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDomicilio.ICVEMUNICIPIO " +
               "join GRLLocalidad  ON  grldomicilio.icvepais=grllocalidad.icvepais and grllocalidad.icvepais=grldomicilio.icvepais and grllocalidad.icveentidadfed=grldomicilio.icveentidadfed and grldomicilio.icvemunicipio=grllocalidad.icvemunicipio and GRLDOMICILIO.iCveLocalidad = GRLLocalidad.iCveLocalidad " +
               "join grltipodomicilio ON GRLDomicilio.iCveTipoDomicilio = grltipodomicilio.iCveTipoDomicilio " +
               cFiltro +
               " ORDER BY lPredeterminado desc ";
  Vector vcListado = new Vector();
  try{
    if (!cFiltro.equals(""))
      vcListado = dGRLDomicilio.findByCustom("iCvePersona,iCveDomicilio",cSql);
    oAccion.setINumReg(vcListado.size());
  }catch(Exception e){
    e.printStackTrace();
    cError=e.getMessage();
  }
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

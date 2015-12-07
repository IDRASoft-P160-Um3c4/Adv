<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLRepLegalA1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLRepLegal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLRepLegalA1 dGRLRepLegal = new TDGRLRepLegalA1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveEmpresa,iCvePersona,lPrincipal");
    try{
      vDinRep = dGRLRepLegal.insert(vDinRep,null);
    //}catch(Exception e){
     // cError="Guardar";
    }catch(Exception e){
       if(e.getMessage().equals("")==false){
        cError="Duplicado";
       }else{
          cError="Guardar";
       }}
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveEmpresa,iCvePersona,lPrincipal");
    try{
      vDinRep = dGRLRepLegal.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveEmpresa,iCvePersona");
    try{
       dGRLRepLegal.delete(vDinRep,null);

       String cSql2="select iCvePersona,iCveEmpresa,lPrincipal from GRLRepLegal " +
              "where iCveEmpresa=" + vDinRep.getInt("iCveEmpresa") + " and lPrincipal = 1 ";

      Vector vcData2 = dGRLRepLegal.findByCustom("",cSql2);
      if (vcData2.size() == 0){
        String cSql3="select iCvePersona,iCveEmpresa, lPrincipal from GRLRepLegal " +
                     "where iCveEmpresa=" +  vDinRep.getInt("iCveEmpresa");
        Vector vcData3 = dGRLRepLegal.findByCustom("",cSql3);
        if (vcData3.size()>0){
          TVDinRep dinRepTemp = (TVDinRep) vcData3.get(0);
          dinRepTemp.put("lPrincipal", 1);
          dGRLRepLegal.updatePredeterminado(dinRepTemp, null);
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
  String cSql="Select GRLRepLegal.iCvePersona,cRFC,cRPA,cNomRazonSocial,cApPaterno,cApMaterno,"+
              "cCorreoE,cPseudonimoEmp,lPrincipal,"+
              "cNomRazonSocial || ' ' || cApPaterno || ' ' || cApMaterno AS cNombreCompleto, " +
//              "cCalle || ' No. ' || cNumExterior || ' Int. ' || cNumInterior || ' COL. ' || cColonia || ', ' || GRLMunicipio.cNombre || ' (' || GRLEntidadFed.cAbreviatura ||'.)' || ' C.P. ' || cCodPostal AS cDomicilio " +
              "cCalle || ' No. ' || cNumExterior || ' Int. ' || cNumInterior || ' COL. ' || cColonia || ', ' || GRLMunicipio.cNombre || ' (' || GRLEntidadFed.cAbreviatura ||'.)' AS cDomicilio " +
              "from grlreplegal " +
              "join grlpersona on grlreplegal.icvepersona=grlpersona.icvepersona " +
              //LEL19092006
              "JOIN GRLDomicilio on GRLDomicilio.ICVEPERSONA = GRLRepLegal.iCvePersona " +
              "AND GRLDomicilio.lPredeterminado = 1 " +
              "join GRLPAIS ON GRLDomicilio.ICVEPAIS = GRLPAIS.ICVEPAIS " +
              "join GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLPAIS.ICVEPAIS " +
              "AND GRLENTIDADFED.ICVEENTIDADFED = GRLDomicilio.ICVEENTIDADFED " +
              "join GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS=GRLDOMICILIO.ICVEPAIS "+
              "AND GRLMUNICIPIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED " +
              "AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDomicilio.ICVEMUNICIPIO " +
              "join GRLLocalidad ON grldomicilio.icvepais=grllocalidad.icvepais " +
              "and grllocalidad.icvepais=grldomicilio.icvepais " +
              "and grllocalidad.icveentidadfed=grldomicilio.icveentidadfed " +
              "and grldomicilio.icvemunicipio=grllocalidad.icvemunicipio " +
              "and GRLDOMICILIO.iCveLocalidad = GRLLocalidad.iCveLocalidad " +
              //Fin LEL19092006
              cFiltro +
              " ORDER BY lPrincipal desc, cRFC ";
  Vector vcListado = new Vector();
  try{
    if (!cFiltro.equals(""))
      vcListado = dGRLRepLegal.findByCustom("iCveEmpresa",cSql);
    oAccion.setINumReg(vcListado.size());
  }catch(Exception e){
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

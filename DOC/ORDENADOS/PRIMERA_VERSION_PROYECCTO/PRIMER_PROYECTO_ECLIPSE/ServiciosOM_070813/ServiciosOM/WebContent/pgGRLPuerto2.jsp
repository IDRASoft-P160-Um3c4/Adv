<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLPuerto.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLPuerto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Melina Aline Rodriguez Angeles
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLPuerto dGRLPuerto = new TDGRLPuerto();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad");
    String lSQL =
       "select * from GRLPuerto where cDscPuerto = '"+vDinRep.getString("cDscPuerto") +"'";
    try{
       Vector vcLista=dGRLPuerto.findByCustom("",lSQL);
       if(vcLista.size() > 0)
       {cError="Duplicado";
       }else {
      vDinRep = dGRLPuerto.insert(vDinRep,null);
       }
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad");
    String lSQL2 =
      "select * from GRLPuerto where cDscPuerto = '"+vDinRep.getString("cDscPuerto") +"' "+
      " AND iCvePuerto NOT IN ("+vDinRep.getInt("iCvePuerto")+") ";
    try{
      Vector vcLista2=dGRLPuerto.findByCustom("",lSQL2);
      if(vcLista2.size() > 0)
       {cError="Duplicado";
       } else{
      vDinRep = dGRLPuerto.update(vDinRep,null);
       }
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCvePuerto");
    try{
       dGRLPuerto.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 Vector vcListado = new Vector();
 try{
   String lSQL = " Select iCvePuerto, GRLPuerto.cDscPuerto ||', '|| GRLEntidadFed.cNombre as cPuerto, " +
                 " GRLPuerto.iCveTipoPuerto,GRLPuerto.iCvePais, " +
                 " GRLPuerto.iCveEntidadFed,GRLPuerto.iCveMunicipio, " +
                 " GRLPuerto.iCveLocalidad, cDscTipoPuerto, " +
                 " GRLPais.cNombre as cDscPais, GRLEntidadFed.cNombre as cDscEntidadFed, " +
                 " GRLMunicipio.cNombre as cDscMunicipio, GRLLocalidad.cNombre as cDscLocalidad " +
                 " from GRLPuerto  " +
                 " join  GRLTipoPuerto on  GRLPuerto.iCveTipoPuerto = GRLTipoPuerto.iCveTipoPuerto " +
                 " join  GRLPais       on  GRLPuerto.iCvePais = GRLPais.iCvePais " +
                 " join  GRLEntidadFed on  GRLPuerto.iCvePais = GRLEntidadFed.iCvePais " +
                 " and GRLPuerto.iCveEntidadFed = GRLEntidadFed.iCveEntidadFed " +
                 " join  GRLMunicipio  on  GRLPuerto.iCvePais = GRLMunicipio.iCvePais  " +
                 " and GRLPuerto.iCveEntidadFed = GRLMunicipio.iCveEntidadFed " +
                 " and GRLPuerto.iCveMunicipio = GRLMunicipio.iCveMunicipio " +
                 " join  GRLLocalidad  on  GRLPuerto.iCvePais = GRLLocalidad.iCvePais " +
                 " and GRLPuerto.iCveEntidadFed = GRLLocalidad.iCveEntidadFed " +
                 " and GRLPuerto.iCveMunicipio = GRLLocalidad.iCveMunicipio " +
                 " and GRLPuerto.iCveLocalidad = GRLLocalidad.ICveLocalidad " +  oAccion.getCFiltro() + oAccion.getCOrden();
   if (request.getParameter("hdFiltro") != null && request.getParameter("hdFiltro").trim().startsWith("GRLPuerto"))
     vcListado = dGRLPuerto.findByCustom("iCvePuerto",lSQL);
 }catch(Exception e){
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

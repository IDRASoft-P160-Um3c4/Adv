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
 * @author Melina Aline Rodriguez Angeles || ICaballero || mbeano
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
    vDinRep = oAccion.setInputs("cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCveLitoral,iCveOficinaAdscrita,iPortId,lEstadisticaDGP,lEstadisticaRutaCarga,lEstadisticaRutaPasaje");
    String lSQL =
       "select * from GRLPuerto where cDscPuerto = '"+vDinRep.getString("cDscPuerto") +"'";
    try{
       vDinRep = dGRLPuerto.insert(vDinRep,null);
       /*
       Vector vcLista=dGRLPuerto.findByCustom("",lSQL);
       if(vcLista.size() > 0)
       {cError="Duplicado";
       }else {
      vDinRep = dGRLPuerto.insert(vDinRep,null);
       }*/
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePuerto,cDscPuerto,iCveTipoPuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCveLitoral,iCveOficinaAdscrita,hdiCveEntidad,hdiOrden,iPortId,lEstadisticaDGP,lEstadisticaRutaCarga,lEstadisticaRutaPasaje,lHabilitado");
    String lSQL2 =
      "select * from GRLPuerto where cDscPuerto = '"+vDinRep.getString("cDscPuerto") +"' "+
      " AND iCvePuerto NOT IN ("+vDinRep.getInt("iCvePuerto")+") ";
    try{
      vDinRep = dGRLPuerto.update(vDinRep,null);
      /*
      Vector vcLista2=dGRLPuerto.findByCustom("",lSQL2);
      if(vcLista2.size() > 0)
       {cError="Duplicado";
       } else{
      vDinRep = dGRLPuerto.update(vDinRep,null);
       }*/
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCvePuerto,iOrden,iCveEntidadFed,iCveLitoral");
    try{
       dGRLPuerto.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  if(oAccion.getCAccion().equals("ActualizaOrden")){
    vDinRep = oAccion.setInputs("iCvePuertoOrden,iOrdenPuerto");
    try{
      vDinRep = dGRLPuerto.DatosOrden(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String lSQL = "";
  if(oAccion.getCAccion().equals("ConsultaPto")){
     lSQL = "Select iCvePuerto, CDSCPUERTO from GRLPUERTO " + oAccion.getCOrden();
  }
  else{
  lSQL = " Select iCvePuerto,cDscPuerto || ' (' || GRLEntidadFed.cAbreviatura || ')' as cDscPuertoEntidad, " +  //0,1
                " GRLPuerto.iCveTipoPuerto,GRLPuerto.iCvePais, " +                 //2,3
                " GRLPuerto.iCveEntidadFed,GRLPuerto.iCveMunicipio, " +            //4,5
                " GRLPuerto.iCveLocalidad, cDscTipoPuerto, " +                 //6,7
                " GRLPais.cNombre as cDscPais, GRLEntidadFed.cNombre as cDscEntidadFed, " +        //8,9
                " GRLMunicipio.cNombre as cDscMunicipio, GRLLocalidad.cNombre as cDscLocalidad, " +    //10,11
                " GRLPuerto.iCveLitoral,  GRLLitoral.cDscLitoral as cDscLitoral, " +            //12,13
                " GRLOficina.iCveOficina, GRLOficina.cDscOficina," +          //14,15
                " GRLPuerto.iOrden, GRLPuerto.lHabilitado, GRLPuerto.lConRecinto, cDscPuerto, " +        //16,17,18,19
                " GRLPuerto.iPortId, " +                    //20
                " GRLPuerto.lEstadisticaDGP, GRLPuerto.lEstadisticaRutaCarga, GRLPuerto.lEstadisticaRutaPasaje " +//21,22,23
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
                " and GRLPuerto.iCveLocalidad = GRLLocalidad.ICveLocalidad " +
                " join GRLLitoral on GRLPuerto.iCveLitoral = GRLLitoral.iCveLitoral " +
                " left join GRLOficina on GRLPuerto.iCveOficinaAdscrita = GRLOficina.iCveOficina " +
                oAccion.getCFiltro() + oAccion.getCOrden();
  }
  
 Vector vcListado = dGRLPuerto.findByCustom("iCvePuerto",lSQL);
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

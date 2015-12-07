<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLTipoPuerto.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLTipoPuerto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Melina Aline Rodriguez Angeles
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLTipoPuerto dGRLTipoPuerto = new TDGRLTipoPuerto();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDscTipoPuerto");
     String lSQL =
       "select * from GRLTipoPuerto where cDscTipoPuerto = '"+vDinRep.getString("cDscTipoPuerto") +"'";
    try{
      Vector vcLista=dGRLTipoPuerto.findByCustom("",lSQL);
      if(vcLista.size() > 0)
       {cError="Duplicado";
       } else {
      vDinRep = dGRLTipoPuerto.insert(vDinRep,null);
        }
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTipoPuerto,cDscTipoPuerto");
    String lSQL2 =
      "select * from GRLTipoPuerto where cDscTipoPuerto = '"+vDinRep.getString("cDscTipoPuerto") +"' "+
      " AND iCveTipoPuerto NOT IN ("+ vDinRep.getInt("iCveTipoPuerto") +") ";
    try{
      Vector vcLista2=dGRLTipoPuerto.findByCustom("",lSQL2);
      if(vcLista2.size() > 0)
       {cError="Duplicado";
       } else{
      vDinRep = dGRLTipoPuerto.update(vDinRep,null);
        }
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTipoPuerto");
    try{
       dGRLTipoPuerto.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLTipoPuerto.findByCustom("iCveTipoPuerto","Select iCveTipoPuerto,cDscTipoPuerto from GRLTipoPuerto "+oAccion.getCFiltro() + oAccion.getCOrden());
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

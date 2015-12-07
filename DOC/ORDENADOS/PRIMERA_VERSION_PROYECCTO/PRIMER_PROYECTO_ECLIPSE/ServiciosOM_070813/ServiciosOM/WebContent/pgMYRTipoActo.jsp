<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgMYRTipoActo.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad MYRTipoActo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDMYRTipoActo dMYRTipoActo = new TDMYRTipoActo();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveRamo,iCveSeccion,cDscTipoActo,lActivo,cPlantillaSintesis");
    try{
      vDinRep = dMYRTipoActo.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveRamo,iCveSeccion,iCveTipoActo,cDscTipoActo,lActivo,cPlantillaSintesis");
    try{
      vDinRep = dMYRTipoActo.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveRamo,iCveSeccion,iCveTipoActo");
    try{
       dMYRTipoActo.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  String cSQL = "Select " +
		"  MYRTipoActo.iCveRamo, " +
		"  MYRTipoActo.iCveSeccion, " +
		"  MYRTipoActo.iCveTipoActo, " +
		"  MYRTipoActo.cDscTipoActo, " +
		"  MYRTipoActo.lActivo, " +
		"  MYRRAMO.CDSCRAMO, " +
		"  MYRSECCION.CDSCSECCION, " +
		"  MYRTipoActo.cPlantillaSintesis " +
		"from MYRTipoActo " +
		"JOIN MYRRAMO on MYRRAMO.ICVERAMO = MYRTipoActo.iCveRamo " +
		"JOIN MYRSECCION on MYRSECCION.ICVERAMO = MYRTipoActo.iCveRamo and MYRSECCION.ICVESECCION = MYRTipoActo.iCveSeccion " +
               oAccion.getCFiltro() + oAccion.getCOrden();
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dMYRTipoActo.findByCustom("iCveRamo,iCveSeccion,iCveTipoActo",cSQL);
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLProdXOficDepto.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLProdXOficDepto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLProdXOficDepto dGRLProdXOficDepto = new TDGRLProdXOficDepto();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,iCveFormatoSalida,dtIniAsignacion");
    try{
      vDinRep = dGRLProdXOficDepto.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,dtIniAsignacion,iCveFormatoSalida");
    try{
      vDinRep = dGRLProdXOficDepto.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,iCveFormatoSalida");
    try{
       dGRLProdXOficDepto.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLProdXOficDepto.findByCustom("iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,iCveFormatoSalida","Select " +
  " GRLProdXOficDepto.iCveOficina,GRLProdXOficDepto.iCveDepartamento,GRLProdXOficDepto.iCveProceso,GRLProdXOficDepto.iCveProducto,GRLProdXOficDepto.dtIniAsignacion,GRLProdXOficDepto.iCveFormatoSalida, "+
  " GRLOficina.cDscBreve AS cDscOficBreve, GRLDepartamento.cDscBreve AS cDscDeptoBreve, GRLProceso.cDscProceso, GRLProducto.cDscBreve AS cDscProdBreve , GRLFormatoSalida.cDscFormatoSalida "+
  " from GRLProdXOficDepto " +
  " join GRLOficina ON GRLProdXOficDepto.iCveOficina = GRLOficina.iCveOficina"+
  " join GRLDepartamento ON GRLProdXOficDepto.iCveDepartamento = GRLDepartamento.iCveDepartamento"+
  " join GRLProceso ON GRLProdXOficDepto.iCveProceso = GRLProceso.iCveProceso"+
  " join GRLProducto ON GRLProdXOficDepto.iCveProducto = GRLProducto.iCveProducto"+
  " join GRLFormatoSalida ON GRLProdXOficDepto.iCveFormatoSalida = GRLFormatoSalida.iCveFormatoSalida"+
   oAccion.getCFiltro() + oAccion.getCOrden());
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

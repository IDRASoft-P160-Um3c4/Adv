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
 * <p>Description: JSP "Cat�logo" de la entidad GRLProdXOficDepto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLProdXOficDepto dGRLProdXOficDepto = new TDGRLProdXOficDepto();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,iCveFormatoSalida,dtIniAsignacion");
    try{
      vDinRep = dGRLProdXOficDepto.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveProceso,iCveProducto,dtIniAsignacion,iCveFormatoSalida");
    try{
      vDinRep = dGRLProdXOficDepto.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
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

  String cSQL = "";
  if(oAccion.getCAccion().equals("Procesos")){
        cSQL = "SELECT DISTINCT(GRLPRODXOFICDEPTO.ICVEPROCESO),  GRLPROCESO.CDSCPROCESO " +
		"FROM GRLPRODXOFICDEPTO " +
		"JOIN GRLPROCESO ON GRLPRODXOFICDEPTO.ICVEPROCESO = GRLPROCESO.ICVEPROCESO ";
  }

  if(oAccion.getCAccion().equals("Productos")){
  	cSQL = "SELECT " +
		"  GRLProdXOficDepto.iCveOficina, " +
		"  GRLProdXOficDepto.iCveDepartamento, " +
		"  GRLProdXOficDepto.iCveProceso, " +
		"  GRLProdXOficDepto.iCveProducto, " +
		"  GRLProducto.cDscProducto, " +
		"  GRLProducto.cDscBreve " +
		"FROM GRLProdXOficDepto " +
		"  JOIN GRLProducto ON GRLProdXOficDepto.iCveProducto = GRLProducto.iCveProducto ";
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = dGRLProdXOficDepto.findByCustom("iCveOficina,iCveDepartamento,iCveProceso,iCveProducto", cSQL +
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

<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%
/**
 * <p>Title: pgGRLDocRegistrado.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLDocRegistrado</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLDocRegistrado dGRLDocRegistrado = new TDGRLDocRegistrado();
  String cError = "";


  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());


  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){

    //System.out.print("Dentro del Guardar");

    vDinRep = oAccion.setInputs("iEjercicio,bDocAlmacenado,lActivo");
    try{
      vDinRep = dGRLDocRegistrado.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){

    //System.out.print("Dentro del GuardarA");

    vDinRep = oAccion.setInputs("iEjercicio,iIdGestorDocumento,bDocAlmacenado,lActivo");
    try{
      vDinRep = dGRLDocRegistrado.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");

    //System.out.print("Dentro del Borrar");

    vDinRep = oAccion.setInputs("iEjercicio,iIdGestorDocumento");
    try{
       dGRLDocRegistrado.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  //Vector vcListado = dGRLDocRegistrado.findByCustom("iEjercicio,iIdGestorDocumento","Select iEjercicio,iIdGestorDocumento,bDocAlmacenado,lActivo from GRLDocRegistrado " + oAccion.getCFiltro() + oAccion.getCOrden());
  //oAccion.navega(vcListado);
  //String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
  function fDesHabilita(){}
  function fHabilita(){}
</script>
<form name="form1" method="post" action="">
<input type="hidden" name="hdBoton" value="">
<input type="hidden" name="hdOrden" value="">
<input type="hidden" name="hdFiltro" value="">
<input type="hidden" name="hdNumReg" value="">
<input type="hidden" name="hdRowPag" value="">
<input type="hidden" name="iEjercicio" value="">
<input type="hidden" name="iIdGestorDocumento" value="">
    Archivo Adjuntado con Exito!!!
</form>
<%}%>

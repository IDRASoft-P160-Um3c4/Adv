<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAReferenciaConcepto.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad TRAReferenciaConcepto</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAReferenciaConcepto dTRAReferenciaConcepto = new TDTRAReferenciaConcepto();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveConcepto,dtIniVigenciaIzq,iCategoriaIngresos,iConceptoIngresos,iRefNumericaIngresos,lEsTarifaIzq,lEsPorcentajeIzq,dImporteSinAjusteIzq,dImporteAjustadoIzq,dtFinVigenciaIzq");
    //System.out.println("****** Fecha inicio = "+vDinRep.getString("dtIniVigencia"));
    try{
      vDinRep = dTRAReferenciaConcepto.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveConcepto,dtIniVigencia,iCategoriaIngresos,iConceptoIngresos,iRefNumericaIngresos,lEsTarifa,lEsPorcentaje,dImporteSinAjuste,dImporteAjustado,dtFinVigenciaIzq");
    try{
      vDinRep = dTRAReferenciaConcepto.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iCveConcepto,dtIniVigencia");
    try{
       dTRAReferenciaConcepto.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  String cSql ="Select iEjercicio,TRAReferenciaConcepto.iCveConcepto,dtIniVigencia,iCategoriaIngresos,iConceptoIngresos,iRefNumericaIngresos,lEsTarifa,lEsPorcentaje,dImporteSinAjuste,dImporteAjustado,cDscConcepto,dtFinVigencia " +
               "from TRAReferenciaConcepto " +
               "join TRAConceptoPago on TRAReferenciaConcepto.iCveConcepto =  TRAConceptoPago.iCveConcepto " +
               oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dTRAReferenciaConcepto.findByCustom("iEjercicio,iCveConcepto,dtIniVigencia",cSql);
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLReporte.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLReporte</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLReporte dGRLReporte = new TDGRLReporte();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cNomReporte,lExcel,lWord,lPDF,lMultiPart,cPlantillaExcel,cPlantillaWord,cPlantillaPDF,cDAOEjecutar,cMetodoExcel,cMetodoWord,cMetodoPDF,iCveFormatoFiltro,lAutoImprimir,iNumCopias,lMostrarAplicacion,lParametroModificable,lRequiereFolio");
    try{
      vDinRep = dGRLReporte.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveSistema,iCveModulo,iNumReporte,cNomReporte,lExcel,lWord,lPDF,lMultiPart,cPlantillaExcel,cPlantillaWord,cPlantillaPDF,cDAOEjecutar,cMetodoExcel,cMetodoWord,cMetodoPDF,iCveFormatoFiltro,lAutoImprimir,iNumCopias,lMostrarAplicacion,lParametroModificable,lRequiereFolio");
    try{
      vDinRep = dGRLReporte.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveSistema,iCveModulo,iNumReporte");
    try{
       dGRLReporte.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  //System.out.print(oAccion.getCFiltro());
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLReporte.findByCustom("iCveSistema,iCveModulo,iNumReporte",
  "SELECT " +
  "  GRLReporte.iCveSistema, " + //0
  "  GRLReporte.iCveModulo AS iModulo, " +
  "  GRLReporte.iNumReporte, " +
  "  GRLReporte.cNomReporte, " +
  "  GRLReporte.lExcel, " +
  "  GRLReporte.lWord, " + //5
  "  GRLReporte.lPDF, " +
  "  GRLReporte.lMultiPart, " +
  "  GRLReporte.cPlantillaExcel, " +
  "  GRLReporte.cPlantillaWord, " +
  "  GRLReporte.cPlantillaPDF, " +   //10
  "  GRLReporte.cDAOEjecutar, " +
  "  GRLReporte.cMetodoExcel, " +
  "  GRLReporte.cMetodoWord, " +
  "  GRLReporte.cMetodoPDF, " +
  "  GRLReporte.iCveFormatoFiltro, " +   //15
  "  GRLReporte.lAutoImprimir, " +
  "  GRLReporte.iNumCopias, " +
  "  GRLReporte.lMostrarAplicacion, " +
  "  GRLReporte.lParametroModificable, " +
  "  GRLModulo.iCveModulo AS iMModulo, " +   //20
  "  GRLModulo.cDscModulo, " +
  "  GRLFormato.cDscFormato, " +
  "  GRLFormato.cTitulo, " +
  "  GRLFormato.lVigente, " +
  "  GRLReporte.lRequiereFolio " + //25
  "FROM GRLReporte " +
  "  JOIN GRLModulo ON GRLReporte.iCveSistema = GRLModulo.iCveSistema AND GRLReporte.iCveModulo = GRLModulo.iCveModulo " +
  "  LEFT JOIN GRLFormato ON GRLReporte.iCveFormatoFiltro = GRLFormato.iCveFormato " +
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLReporte1A.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad GRLReporte</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author ijimenez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  StringBuffer cSql = new StringBuffer(" ");
  TDGRLReporte1A dGRLReporte = new TDGRLReporte1A();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    System.out.print("Entro al jsp en guardar");
    vDinRep = oAccion.setInputs("iCveModulo,cNomReporte,lExcel,lWord,lPDF,lMultiPart,cPlantillaExcel,cPlantillaWord,cPlantillaPDF,cDAOEjecutar,cMetodoExcel,cMetodoWord,cMetodoPDF,iCveFormatoFiltro,lAutoImprimir,iNumCopias,lMostrarAplicacion,lParametroModificable,lRequiereFolio");
    try{
      vDinRep = dGRLReporte.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveSistema,iCveModulo,iNumReporte,cNomReporte,lExcel,lWord,lPDF,lMultiPart,cPlantillaExcel,cPlantillaWord,cPlantillaPDF,cDAOEjecutar,cMetodoExcel,cMetodoWord,cMetodoPDF,iCveFormatoFiltro,lAutoImprimir,iNumCopias,lMostrarAplicacion,lParametroModificable,lRequiereFolio");
    try{
      vDinRep = dGRLReporte.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
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
  /*Se relacionan las tablas GRLModulo y GRLReporte para obtener las descripciones de los m�dulos*/
  cSql.append("SELECT  DISTINCT GRLReporte.iCveSistema,GRLReporte.iCveModulo,GRLReporte.iNumReporte,GRLReporte.cNomReporte,GRLReporte.lExcel,GRLReporte.lWord,GRLReporte.lPDF,GRLReporte.lMultiPart,GRLReporte.cPlantillaExcel,GRLReporte.cPlantillaWord,GRLReporte.cPlantillaPDF,GRLReporte.cDAOEjecutar,GRLReporte.cMetodoExcel,GRLReporte.cMetodoWord,GRLReporte.cMetodoPDF,GRLReporte.iCveFormatoFiltro,GRLReporte.lAutoImprimir,GRLReporte.iNumCopias,GRLReporte.lMostrarAplicacion,GRLReporte.lParametroModificable,GRLReporte.lRequiereFolio, GRLModulo.cDscModulo ");
  cSql.append("FROM    GRLReporte ");
  cSql.append("JOIN    GRLModulo   ON  GRLModulo.iCveSistema = GRLReporte.iCveSistema AND GRLModulo.iCveModulo = GRLReporte.iCveModulo ");
  cSql.append(oAccion.getCFiltro());
  cSql.append(oAccion.getCOrden());
 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = dGRLReporte.findByCustom("iCveSistema,iCveModulo,iNumReporte",cSql.toString());
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

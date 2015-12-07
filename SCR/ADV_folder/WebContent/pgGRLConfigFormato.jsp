<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="com.micper.excepciones.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLConfigFormato.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad GRLConfigFormato</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Leopoldo Beristain Gonz�lez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLConfigFormato dGRLConfigFormato = new TDGRLConfigFormato();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveFormatoAux,iOrden,cDscAtributo,cEtiquetaAnterior,iCveTipoRespuesta,cEtiquetaPosterior,lObligatorio,cValorIniOmision,cValorFinOmision,iMaxLongitud,cNomCampoPantalla,cTablaMapeo,cCampoMapeo,cJSValidaciones,lVigente,iRenglon,iColumna");
    try{
      vDinRep = dGRLConfigFormato.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveFormatoAux,iCveAtributo,iOrden,cDscAtributo,cEtiquetaAnterior,iCveTipoRespuesta,cEtiquetaPosterior,lObligatorio,cValorIniOmision,cValorFinOmision,iMaxLongitud,cNomCampoPantalla,cTablaMapeo,cCampoMapeo,cJSValidaciones,lVigente,iRenglon,iColumna");
    try{
      vDinRep = dGRLConfigFormato.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveFormatoAux,iCveAtributo");
    try{
       dGRLConfigFormato.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = null;
  try{
     vcListado = dGRLConfigFormato.findByCustom("iCveFormato,iCveAtributo",
     						"Select GRLCFMT.iCveFormato,"+
                                                       "GRLFMT.cTitulo,"+
                                                       "GRLCFMT.iCveAtributo,"+
                                                       "GRLCFMT.iOrden,"+
                                                       "GRLCFMT.cDscAtributo,"+
                                                       "GRLCFMT.cEtiquetaAnterior,"+
                                                       "GRLCFMT.cEtiquetaPosterior,"+
                                                       "GRLCFMT.iCveTipoRespuesta,"+
                                                       "GRLTR.cDscTipoRespuesta,"+
                                                       "GRLCFMT.lObligatorio,"+
                                                       "GRLCFMT.cValorIniOmision,"+
                                                       "GRLCFMT.cValorFinOmision,"+
                                                       "GRLCFMT.iMaxLongitud,"+
                                                       "GRLCFMT.cNomCampoPantalla,"+
                                                       "GRLCFMT.cTablaMapeo,"+
                                                       "GRLCFMT.cCampoMapeo,"+
                                                       "GRLCFMT.cJSValidaciones,"+
                                                       "GRLCFMT.lVigente, "+
                                                       "GRLCFMT.iRenglon, "+
                                                       "GRLCFMT.iColumna "+
                                                "from GRLConfigFormato GRLCFMT "+
                                                "join GRLFormato GRLFMT on GRLCFMT.iCveFormato = GRLFMT.iCveFormato "+
                                                "join GRLTipoRespuesta GRLTR on GRLCFMT.iCveTipoRespuesta = GRLTR.iCveTipoRespuesta "+
                                                oAccion.getCFiltro() + oAccion.getCOrden());
  }catch(DAOException ex){
    if(!ex.getMessage().equals(""))
         cError="Datos";
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
                '<%=oAccion.getBPK()%>',
                '<%=vParametros.getPropEspecifica("TipoRespuestaFormato")%>');   // Validar Tipo Respuesta != 5
</script>
<%}%>

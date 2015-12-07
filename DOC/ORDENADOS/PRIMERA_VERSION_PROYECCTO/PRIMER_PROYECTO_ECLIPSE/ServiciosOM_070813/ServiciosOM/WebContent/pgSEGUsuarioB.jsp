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
 * <p>Title: pgSEGUsuarioB.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad SEGUsuario</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Leopoldo Beristain González
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDSEGUsuario dSEGUsuario = new TDSEGUsuario();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("dtRegistro,cUsuario,cPassword,cNombre,cApPaterno,cApMaterno,cCalle,cColonia,iCvePais,iCveEntidadFed,iCveMunicipio,iCodigoPostal,cTelefono,iCveUnidadOrg,lBloqueado");
    try{
      vDinRep = dSEGUsuario.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveUsuario,dtRegistro,cUsuario,cPassword,cNombre,cApPaterno,cApMaterno,cCalle,cColonia,iCvePais,iCveEntidadFed,iCveMunicipio,iCodigoPostal,cTelefono,iCveUnidadOrg,lBloqueado");
    try{
      vDinRep = dSEGUsuario.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveUsuario");
    try{
       dSEGUsuario.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 Vector vcListado = null;
 try{
    vcListado = dSEGUsuario.findByCustom("iCveUsuario",
    " SELECT "+
    " SEGU.iCveUsuario,"+    //0
    " SEGU.DtRegistro,"+
    " SEGU.CUsuario,"+
    " SEGU.CPassword,"+
    " SEGU.CNombre,"+
    " SEGU.CApPaterno,"+     //5
    " SEGU.CApMaterno,"+
    " grldepartamento.icvedepartamento, " +
    " grldepartamento.cdscdepartamento, " +
    " grloficina.icveoficina, " +
    " grloficina.cdscoficina,  " +    //10
    " SEGU.CNombre || ' ' || SEGU.CApPaterno || ' ' || SEGU.CApMaterno as cNom " +
    " FROM SegUsuario SEGU "+
    " JOIN GrlUsuarioXOfic GRLUXOFIC ON SEGU.iCveUsuario = GRLUXOFIC.iCveUsuario " +
    " join grloficina      on GRLUXOFIC.icveoficina      = grloficina.icveoficina " +
    " join grldepartamento on GRLUXOFIC.icvedepartamento = grldepartamento.icvedepartamento " +
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
                '<%=oAccion.getBPK()%>');
</script>
<%}%>

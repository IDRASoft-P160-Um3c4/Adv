<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLUsuarioXOficB1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLUsuarioXOfic</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLUsuarioXOficB1 dGRLUsuarioXOficB1 = new TDGRLUsuarioXOficB1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    System.out.print("Entro Guardar");
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveUsuario1,lFirmante");
    try{
      vDinRep = dGRLUsuarioXOficB1.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveUsuario,cTelefono,cCorreoE,lFirmante");
    try{
      vDinRep = dGRLUsuarioXOficB1.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOficina,iCveDepartamento,iCveUsuario");
    try{
       dGRLUsuarioXOficB1.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 String cSql =" Select iCveOficina,iCveDepartamento,GRLUsuarioXOfic.iCveUsuario,GRLUsuarioXOfic.cTelefono,GRLUsuarioXOfic.cCorreoE, " +
              " cApPaterno||' '||cApMaterno||' '||SEGUsuario.cNombre As cNom,lFirmante " +
              " from GRLUsuarioXOfic " +
              " JOIN SEGUsuario ON SEGUsuario.iCveUsuario = GRLUsuarioXOfic.iCveUsuario " +
              oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dGRLUsuarioXOficB1.findByCustom("iCveOficina,iCveDepartamento,iCveUsuario",cSql);
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

<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLUsrXModulo.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLUsrXModulo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author csen
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLUsrXModulo dGRLUsrXModulo = new TDGRLUsrXModulo();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("");
    try{
      vDinRep = dGRLUsrXModulo.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveSistema,iCveModulo,iCveUsuario");
    try{
      vDinRep = dGRLUsrXModulo.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveSistema,iCveModulo,iCveUsuario");
    try{
       dGRLUsrXModulo.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  if(oAccion.getCAccion().equals("GuardarT")){
      vDinRep = oAccion.setInputs("iCveSistema,iCveModulo,cConjunto,");
    if(request.getParameter("cConjunto") != ""){

       try{
         vDinRep = dGRLUsrXModulo.insertT(vDinRep,null);
       }catch(Exception e){
         cError="Guardar";
    }
    }else

    oAccion.setBeanPK(vDinRep.getPK());
  }

  if(oAccion.getCAccion().equals("BorrarT")){

     oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveSistema,iCveModulo,cConjunto");
    try{
       dGRLUsrXModulo.BorrarT(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }



 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dGRLUsrXModulo.findByCustom("iCveSistema,iCveModulo,iCveUsuario",
      "SELECT ICVESISTEMA,ICVEMODULO, um.ICVEUSUARIO, U.CAPPATERNO||' '|| U.CAPMATERNO||' '|| U.CNOMBRE AS CUSUARIO1 FROM GRLUSRXMODULO um " +
      "JOIN SEGUSUARIO U ON UM.ICVEUSUARIO = U.ICVEUSUARIO "+ oAccion.getCFiltro() + oAccion.getCOrden());
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

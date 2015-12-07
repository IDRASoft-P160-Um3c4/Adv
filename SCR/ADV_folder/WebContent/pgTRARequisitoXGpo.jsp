
<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARequisitoXGpo.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARequisitoXGpo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARequisitoXGpo dTRARequisitoXGpo = new TDTRARequisitoXGpo();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveGrupo,iCveRequisito,iCveModalidad,iOrden");
    try{
      vDinRep = dTRARequisitoXGpo.insert(vDinRep,null);
    }catch(Exception e){
      if(e.getMessage().equals("")==false){
        cError="Duplicado";
       }else{
          cError="Guardar";
       }
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveGrupo,iCveRequisito,iCveModalidad,iOrden");
    try{
      vDinRep = dTRARequisitoXGpo.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveGrupo,iCveRequisito");
    try{
       dTRARequisitoXGpo.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("Agregar")){
    vDinRep = oAccion.setInputs("iCveGrupo,iCveRequisito1");
    try{
      vDinRep = dTRARequisitoXGpo.Agregar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("Eliminar")){

    vDinRep = oAccion.setInputs("iCveGrupo,iCveRequisito,iOrden");
    try{
      vDinRep = dTRARequisitoXGpo.Eliminar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Subir")){
    vDinRep = oAccion.setInputs("iOrden,iCveGrupo");
    try{
      vDinRep = dTRARequisitoXGpo.update1(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Bajar")){
    vDinRep = oAccion.setInputs("iOrden,iCveGrupo");
    try{
      vDinRep = dTRARequisitoXGpo.update2(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSql = "Select TRARequisitoXGpo.iCveGrupo,TRARequisitoXGpo.iCveRequisito,iOrden,cDscBreve,cDscGrupo,cDscRequisito " +
                "from TRARequisitoXGpo " +
                //"join TRAModalidad on TRARequisitoXGpo.iCveModalidad = TRAModalidad.iCveModalidad " +
                "join TRARequisito on TRARequisitoXGpo.iCveRequisito = TRARequisito.iCveRequisito " +
                "join TRAGpoRequisito on TRARequisitoXGpo.iCveGrupo = TRAGpoRequisito.iCveGrupo " +
                oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dTRARequisitoXGpo.findByCustom("iCveGrupo,iCveRequisito",cSql);
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

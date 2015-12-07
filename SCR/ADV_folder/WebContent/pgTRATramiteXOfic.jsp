<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRATramiteXOfic.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRATramiteXOfic</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRATramiteXOfic dTRATramiteXOfic = new TDTRATramiteXOfic();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    //vDinRep = oAccion.setInputs("iCveOficina,iCveTramite,iCveOficinaResuelve,iCveDeptoResuelve");
    vDinRep = oAccion.setInputs("iCveOficina,iCveTramite,");
    try{
      vDinRep = dTRATramiteXOfic.insert(vDinRep,null);
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
    //vDinRep = oAccion.setInputs("iCveOficina,iCveTramite,iCveOficinaResuelve,iCveDeptoResuelve");
    vDinRep = oAccion.setInputs("iCveOficina,iCveTramite,");
    try{
      vDinRep = dTRATramiteXOfic.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Agregar")){
    vDinRep = oAccion.setInputs("iCveOficina1,iCveTramite");
    try{
      vDinRep = dTRATramiteXOfic.Agregar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOficina,iCveTramite");
    try{
       dTRATramiteXOfic.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSql = "Select TT.iCveOficina,TT.iCveTramite,iCveOficinaResuelve,iCveDeptoResuelve,GO.cDscBreve as oficina1,GO2.cDscBreve as oficina2,TC.cDscBreve as tra1,GRLDepartamento.cDscBreve, GO.CDSCBREVE||' - '||GRLENTIDADFED.CNOMBRE AS COFI " +
"from TRATramiteXOfic TT " +
"join GRLOficina GO on TT.iCveOficina = GO.iCveOficina " +
"left join GRLOficina GO2 on TT.iCveOficinaResuelve = GO2.iCveOficina " +
"left join GRLDepartamento on TT.iCveDeptoResuelve = GRLDepartamento.iCveDepartamento " +
"LEFT JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS= GO.ICVEPAIS AND GRLENTIDADFED.ICVEENTIDADFED = GO.ICVEENTIDADFED " +
"join TRACatTramite TC on TT.iCveTramite = TC.iCveTramite "+
               oAccion.getCFiltro() + oAccion.getCOrden();

  Vector vcListado = dTRATramiteXOfic.findByCustom("iCveOficina,iCveTramite",cSql);
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

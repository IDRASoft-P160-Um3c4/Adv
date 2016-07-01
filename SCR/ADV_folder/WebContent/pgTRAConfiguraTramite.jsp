<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAConfiguraTramite.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAConfiguraTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAConfiguraTramite dTRAConfiguraTramite = new TDTRAConfiguraTramite();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,dtIniVigencia,iNumDiasResol,lDiasNaturalesResol,iNumDiasCubrirReq,lDiasNaturalesReq,lRequierePago,iCveFormato,iCveTramiteCIS,cNotas,iDiasDespuesNotif,lActivo,iCveUsuario,lTramInt");
    try{
    	//System.out.println("**********************"+vDinRep.getInt("iCveUsuario"));
      vDinRep = dTRAConfiguraTramite.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,dtIniVigencia,iNumDiasResol,lDiasNaturalesResol,iNumDiasCubrirReq,lDiasNaturalesReq,lRequierePago,iCveFormato,iCveTramiteCIS,cNotas,iDiasDespuesNotif,lActivo,iCveUsuario,lTramInt");
    try{
      vDinRep = dTRAConfiguraTramite.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,dtIniVigencia");
    try{
       dTRAConfiguraTramite.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  String cSql = "Select TC.iCveTramite,TM.iCveModalidad,TC.dtIniVigencia,iNumDiasResol,lDiasNaturalesResol," +//4
                "iNumDiasCubrirReq,lDiasNaturalesReq,lRequierePago,TC.iCveFormato,cDscFormato,cDscBreve,cDscModalidad, " +//11
                "iCveTramiteCIS,TC.cNotas,iDiasDespuesNotif,lActivo,lTramInt "+//16
                "from TRAConfiguraTramite TC " +
                "join GRLFormato GF on TC.iCveFormato = GF.iCveFormato " +
                "join TRACatTramite CT on TC.iCveTramite = CT.iCveTramite " +
                "join TRAModalidad TM on TC.iCveModalidad = TM.iCveModalidad " +
                 oAccion.getCFiltro() + oAccion.getCOrden();
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRAConfiguraTramite.findByCustom("iCveTramite,iCveModalidad,dtIniVigencia",cSql);
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

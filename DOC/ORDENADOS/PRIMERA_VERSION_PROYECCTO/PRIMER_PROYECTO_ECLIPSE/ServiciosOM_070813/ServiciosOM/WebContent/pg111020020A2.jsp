<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pg111020020A2.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAEtapa</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon; iCaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAEtapa dTRAEtapa = new TDTRAEtapa();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  int iCveEtapaConcluidoArea = Integer.parseInt(vParametros.getPropEspecifica("EtapaConcluidoArea"));
  int iCveEtapaVerificacion = Integer.parseInt(vParametros.getPropEspecifica("EtapaVerificaRequisito"));
  int iCveEtapaEntregarVU = Integer.parseInt(vParametros.getPropEspecifica("EtapaEntregarVU"));
  int iCveEtapaEntregarOfic = Integer.parseInt(vParametros.getPropEspecifica("EtapaEntregarOficialia"));
  int iCveEtapaRecibeResol = Integer.parseInt(vParametros.getPropEspecifica("EtapaRecibeResolucion"));
  int iCveEtapaEntregaResol = Integer.parseInt(vParametros.getPropEspecifica("EtapaEntregaResol"));
  int iCveEtapaDocRetorno = Integer.parseInt(vParametros.getPropEspecifica("EtapaDocRetorno"));
  int iCveEtapaConclusionTramite = Integer.parseInt(vParametros.getPropEspecifica("EtapaConclusionTramite"));
  int iCveEtapaNotificado = Integer.parseInt(vParametros.getPropEspecifica("EtapaPNCNotificado"));
  String cEtapas = iCveEtapaConcluidoArea + "," + iCveEtapaVerificacion + "," + iCveEtapaEntregarVU + "," +
    iCveEtapaEntregarOfic + "," + iCveEtapaRecibeResol + "," + iCveEtapaEntregaResol + "," + iCveEtapaDocRetorno + "," +
    iCveEtapaConclusionTramite + "," + iCveEtapaNotificado + "," + vParametros.getPropEspecifica("EtapaResEnviadaOficialia") + "," +
    vParametros.getPropEspecifica("EtapaTramiteCancelado") + "," + vParametros.getPropEspecifica("TramiteCertificacionDoc");

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cDscEtapa,lVigente");
    try{
      vDinRep = dTRAEtapa.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveEtapa,cDscEtapa,lVigente");
    try{
      vDinRep = dTRAEtapa.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveEtapa");
    try{
       dTRAEtapa.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

  Vector vcListado = dTRAEtapa.findByCustom("iCveEtapa",
         " Select TRAEtapa.iCveEtapa, " +
         "        cDscEtapa, " +
         "        TRAEtapa.lVigente as lVigente, " +
         "        lObligatorio " +
         "   from TRAEtapa " +
	 "   join traEtapaxmodtram on traEtapaxmodtram.iCveEtapa = TRAEtapa.iCveEtapa "  +
        oAccion.getCFiltro() +
        " and TRAEtapa.iCveEtapa not in ( " + iCveEtapaNotificado + ","  + 
        vParametros.getPropEspecifica("EtapaRegistro") + ") " +
        oAccion.getCOrden());
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
                '<%=cEtapas%>');
</script>
<%}%>

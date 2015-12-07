<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.util.*" %>
<%
/**
 * <p>Title: pgGRLRegistroPNC.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLRegistroPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos; Levi Equihua
 * @version 1.1
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLRegistroPNC dGRLRegistroPNC = new TDGRLRegistroPNC();
  TDTRARegReqXCausa dTRARegReqXCausa = new TDTRARegReqXCausa();
  TDVerificacion dVerificacion = new TDVerificacion();
  String cError = "";
  int iConsPNC = 0;
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveUsuRegistro,iCveProducto,iCveOficinaAsignado,iCveDeptoAsignado");
    try{
      vDinRep = dGRLRegistroPNC.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("GPNCCarac")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveUsuRegistro,iCveProducto,lResuelto,iCveRequisito,cCveCarac,iNumSolicitud,iCveTramite,iCveModalidad,iEjercicioPNC");
    try{
      vDinRep = dGRLRegistroPNC.insertCarac(vDinRep,null);
      iConsPNC = dGRLRegistroPNC.getConsecutivoPNC();
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }


  if(oAccion.getCAccion().equals("RegistraPNC")){
    boolean actualizaTraRegReqxTram =true;
    vDinRep = oAccion.setInputs("iCveUsuRegistro,iCveProducto,iCveOficinaAsignado,iCveDeptoAsignado,iCveCausaPNC,iCveUsuCorrige,lResuelto,cDscOtraCausa,iCveRequisito,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad");

    vDinRep.put("lTienePNC",1);
    if(vDinRep.getInt("iEjercicio")==0){
      TFechas Fecha = new TFechas();
      int year = Fecha.getIntYear(Fecha.TodaySQL());
      vDinRep.put("iEjercicio",year);
      actualizaTraRegReqxTram = false;
    }
    try{
      vDinRep = dGRLRegistroPNC.insert(vDinRep,null);
      vDinRep = dTRARegReqXCausa.insert(vDinRep,null);
      if(actualizaTraRegReqxTram)
        vDinRep = dVerificacion.updateTienePNC(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado");
    try{
      vDinRep = dGRLRegistroPNC.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
/** Verifica la acción a través de hdBoton es igual a "BuardarB" (Resolver) */
  if(oAccion.getCAccion().equals("GuardarB")){
    vDinRep = oAccion.setInputs("iEjercicio,iConsecutivoPNC,lResuelto,dtFechaRes");
    try{
      vDinRep = dGRLRegistroPNC.update2(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iConsecutivoPNC");
    try{
       dGRLRegistroPNC.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  //Vector vcListado = dGRLRegistroPNC.findByCustom("iEjercicio,iConsecutivoPNC","Select iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado from GRLRegistroPNC " + oAccion.getCFiltro() + oAccion.getCOrden());
  Vector vcListado = new Vector();
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
                '<%=oAccion.getBPK()%>','<%=0%>','<%=0%>',
                '<%=iConsPNC%>');
</script>
<%}%>

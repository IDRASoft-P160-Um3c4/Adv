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
 * <p>Title: pgGRLRegPNC.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLRegistroPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Levi Equihua
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLRegPNC dGRLRegistroPNC = new TDGRLRegPNC();
  TDTRARegReqXCausa dTRARegReqXCausa = new TDTRARegReqXCausa();
  TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();
  TDVerificacion dVerificacion = new TDVerificacion();
  String cError = "";
  int iConsecutivoPNC = 0;

  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveUsuario,iCveProducto,iCveOficinaUsrAsg,iCveDeptoUsrAsg,cDscOtraCausa,iCveCausaPNC,cDscOtraCausa,iCveOficinaUsr,iCveDeptoUsr,iCveProceso,cObsLey1,cObsLey2,cObsLey3");

    try{
      vDinRep = dGRLRegistroPNC.insert(vDinRep,null);
      iConsecutivoPNC = dGRLRegistroPNC.getConsecutivoPNC();
    }catch(Exception e){
      cError="Guardar";
    }    
    oAccion.setBeanPK(vDinRep.getPK());
  }
  /** Verifica si la Acción a través de hdBotón es igual a "GuardarB" para agregar causas a un PNC existente*/
  if(oAccion.getCAccion().equals("GuardarB")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveUsuario,iCveProducto,iCveOficinaUsrAsg,iCveDeptoUsrAsg,cDscOtraCausa,iCveCausaPNC,cDscOtraCausa,iCveOficinaUsr,iCveDeptoUsr,iCveProceso,iNumSolicitud,iCveRequisito,cObsLey1,cObsLey2,cObsLey3");

    try{
      vDinRep = dGRLRegistroPNC.insertExiste(vDinRep,null);
      //System.out.println(">>>jsp  1:"+vDinRep.getString("cObsLey1"));
      //System.out.println(">>>jsp  2:"+vDinRep.getString("cObsLey2"));
      //System.out.println(">>>jsp  3:"+vDinRep.getString("cObsLey3"));
      iConsecutivoPNC = dGRLRegistroPNC.getConsecutivoPNC();
    }catch(Exception e){
      cError="Guardar";
    }
    
                                                                                             // Id Estado CIS 23 REQUISITO(S) DEL TRAMITE NO VALIDO(S).
    regEtapasXModTram.upDateEstadoInformativoCIS(vDinRep.getInt("iEjercicio"),vDinRep.getInt("iNumSolicitud"),Integer.parseInt(vParametros.getPropEspecifica("estadoCIS_PNC")),null);
    
    oAccion.setBeanPK(vDinRep.getPK());
  }
  
 
  if(oAccion.getCAccion().equals("GuardarTodos")){
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveUsuario,lResuelto,iCveOficina,iCveDepartamento");
	   try{
	      vDinRep = dGRLRegistroPNC.insertExiste(vDinRep,null);
	    }catch(Exception e){
	      cError="Guardar";
	    }
	   // regEtapasXModTram.upDateEstadoInformativoCIS(vDinRep.getInt("iEjercicio"),vDinRep.getInt("iNumSolicitud"),Integer.parseInt(vParametros.getPropEspecifica("estadoCIS_PNC")),null);
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

 /** Verifica si la Acción a través de hdBotón es igual a "GRecibeN" (Actualizar) */
  if(oAccion.getCAccion().equals("GRecibeN")){
    vDinRep = oAccion.setInputs("iEjercicio,dtNotificacion,iNumSolicitud,cRecibeNotif");    
    try{
      vDinRep = dGRLRegistroPNC.updateRecibe(vDinRep,null);
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
                '<%=oAccion.getBPK()%>',
                '<%=iConsecutivoPNC%>');
</script>
<%}%>

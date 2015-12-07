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
 * <p>Title: pgTRARegSolicitud.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  int iAux = 0;
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
     TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    int iIntentos = 0;
    int iMaxIntentos = 10;
    boolean lInsertado = false;
    vDinRep = oAccion.setInputs("ClavesTramite,ClavesModalidad,iCveRequisito,RequisitoPNC,Caracteristicas,iCveTipoPersona,"+
    "iCveSolicitante,iCveDomicilioSolicitante,iCveRepLegal,iCveDomicilioRepLegal,cNomAutorizaRecoger,iCveOficinaUsr,"+
    "iCveDeptoUsr,cObsTramite,cEnviarResolucionA,iCveUsuario,iIdBien,cDscBien,iCveOficina,dUnidCalculo");
    while (!lInsertado && iIntentos < iMaxIntentos){
      iIntentos++;
      try{
        vDinRep = dTRARegSolicitud.fGenSolicitudRel(vDinRep,null);
        lInsertado = true;
        cError = "\\n - Se han insertado las solicitudes con los números:\\n" +
                 vDinRep.getString("cNumSolicitudes")+
                 "\\ny podrá consultarlos en la siguiente pestaña";
      }catch(Exception e){
        if (e.getMessage().indexOf("(-803)") != -1){
          lInsertado = false;
          if (iIntentos == iMaxIntentos - 1 && !lInsertado)
            cError=e.getMessage();
        }else{
          iIntentos = iMaxIntentos + 1;
          cError = e.getMessage();
        }
      }
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  if(oAccion.getCAccion().equals("RecibeTramite")){
    int iEtapa = Integer.parseInt(vParametros.getPropEspecifica("EtapaRecepcion"));
    int iCveOficina = Integer.parseInt(request.getParameter("iCveOficinaUsr"));
    int iCveDepartamento = Integer.parseInt(request.getParameter("iCveDeptoUsr"));
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,lAnexo");
    try{
      vDinRep = dTRARegSolicitud.insertRegEtapasxModTram(vDinRep,null,iEtapa,iCveOficina,iCveDepartamento,Integer.parseInt(request.getParameter("hdCveUsuario")));
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }


 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma");
    try{
      vDinRep = dTRARegSolicitud.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("IntegraSol")){
    //vDinRep = oAccion.setInputs("iCveVehiculo,iCveVehiculoO,cNomEmbarcacion");
    vDinRep = oAccion.setInputs("iCveVehiculo,cNomEmbarcacion,iEjercicioOri,iNumSolicitudOri");
    try{
      vDinRep = dTRARegSolicitud.integraSol(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("IntegraSolicitante")){
	    //vDinRep = oAccion.setInputs("iCveVehiculo,iCveVehiculoO,cNomEmbarcacion");
	    vDinRep = oAccion.setInputs("iCvePersonaD,iEjercicioOri,iNumSolicitudOri");
	    try{
	      vDinRep = dTRARegSolicitud.integraSolicitante(vDinRep,null);
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }
  if(oAccion.getCAccion().equals("CambiarBien")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveVehiculo,cNomEmbarcacion,iCveUsuario");
    try{
      vDinRep = dTRARegSolicitud.cambiaBien(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  if(oAccion.getCAccion().equals("CambiarResolucion")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,lPositiva,iCveUsuario");
    try{
      vDinRep = dTRARegSolicitud.cambiaResolucion(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("CambiarSolicitante")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveSolicitante,iCveRepLegal,iCveUsuario");
    try{
      vDinRep = dTRARegSolicitud.cambiaSolicitante(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  if(oAccion.getCAccion().equals("agregaSolicitante")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCvePersona,iCveDomPersona,iCveRepLegal,iCveDomRepLegal,");
    try{
      vDinRep = dTRARegSolicitud.agregaSolicitante(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  
  if(oAccion.getCAccion().equals("cIdFechaImp")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
    try{
    	dTRARegSolicitud.fechaImpresion(vDinRep.getInt("iEjercicio"),vDinRep.getInt("iNumSolicitud"),null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
    try{
       dTRARegSolicitud.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  String cSQL =   "SELECT " +
  "  TRARegSolicitud.iEjercicio, " + //0
  "  TRARegSolicitud.iNumSolicitud, " +
  "  TRARegSolicitud.iCveTramite, " +
  "  TRARegSolicitud.iCveModalidad, " +
  "  TRARegSolicitud.iCveSolicitante, " +
  "  TRARegSolicitud.iCveRepLegal, " + //5
  "  TRARegSolicitud.cNomAutorizaRecoger, " +
  "  TRARegSolicitud.iCveUsuRegistra, " +
  "  TRARegSolicitud.tsRegistro, " +
  "  TRARegSolicitud.dtLimiteEntregaDocs, " +
  "  TRARegSolicitud.dtEstimadaEntrega, " +  //10
  "  TRARegSolicitud.lPagado, " +
  "  TRARegSolicitud.dtEntrega, " +
  "  TRARegSolicitud.iCveUsuEntrega, " +
  "  TRARegSolicitud.lResolucionPositiva, " +
  "  TRARegSolicitud.lDatosPreregistro, " +  //15
  "  TRARegSolicitud.iIdBien, " +
  "  TRARegSolicitud.iCveOficina, " +
  "  TRARegSolicitud.iCveDepartamento, " +
  "  TRARegSolicitud.iEjercicioCita, " +
  "  TRARegSolicitud.iIdCita, " + //20
  "  TRARegSolicitud.iCveForma, " +
  "  TRARegSolicitud.lPrincipal, " +
  "  TRACatTramite.cDscTramite, " +
  "  TRACatTramite.cDscBreve, " +
  "  TRAModalidad.cDscModalidad, " + //25
  "  TRARegSolicitud.cDscBien, "+
  "  TRARegSolicitud.dUnidadesCalculo, "+
  "  TRARegSolicitud.LTRAMINERNET "+
  "FROM TRARegSolicitud " +
  "  JOIN TRACatTramite ON TRARegSolicitud.iCveTramite = TRACatTramite.iCveTramite " +
  "  JOIN TRAModalidad ON TRARegSolicitud.iCveModalidad = TRAModalidad.iCveModalidad " +
  "  JOIN TRAREGETAPASXMODTRAM ON TRARegSolicitud.IEJERCICIO = TRAREGETAPASXMODTRAM.IEJERCICIO AND TRARegSolicitud.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD AND TRAREGETAPASXMODTRAM.ICVEETAPA = " + vParametros.getPropEspecifica("EtapaRegistro") + " ";
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

  if(!oAccion.getCAccion().equals("IntegraSolicitante") && !oAccion.getCAccion().equals("IntegraSol") && !oAccion.getCAccion().equals("Guardar") && !oAccion.getCAccion().equals("CambiarBien") && !oAccion.getCAccion().equals("CambiarSolicitante") && !oAccion.getCAccion().equals("cIdFechaImp")  && !oAccion.getCAccion().equals("CambiarResolucion") && !oAccion.getCAccion().equals("agregaSolicitante")){
    Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",
    cSQL +
    oAccion.getCFiltro() + oAccion.getCOrden());
    oAccion.navega(vcListado);
  }
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
                '',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>');
</script>
<%}%>

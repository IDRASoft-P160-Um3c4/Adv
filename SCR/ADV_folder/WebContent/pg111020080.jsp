<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.micper.util.TFechas" %>
<%
/**
 * <p>Title: pg111020080.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegReqXTramb dTRARegReqXTram = new TDTRARegReqXTramb();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  //TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  //System.out.print(">>> llega aqui sin error"); 
  int iCveUsuario = Integer.parseInt(request.getParameter("iCveUsuario"));
  //System.out.print(">>> llega aqui sin error");
  int lValido = 1;

  out.write("Clave del usuario"+iCveUsuario);
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito");
    try{
      vDinRep.put("iCveUsuRecibe",iCveUsuario);
      vDinRep = dTRARegReqXTram.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
//    out.write("paso por el upadte en el jsp");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito");
    try{
      vDinRep.put("iCveUsuRecibe",iCveUsuario);
      vDinRep = dTRARegReqXTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Cambia" (UpDate) */
  if(oAccion.getCAccion().equals("Cambia")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cConjunto,iCveModalidad,iCveTramite,iCveRequisito,iCveUsuario");
    try{
      vDinRep.put("iCveUsuRecibe",iCveUsuario);
      vDinRep = dTRARegReqXTram.Cambia(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  /** Verifica si la Acción a través de hdBotón es igual a "Cambia" (UpDate) */
  if(oAccion.getCAccion().equals("GuardarCambios")){
    TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cNomAutorizaRecoger,iCveOficina,cEnviarResolucionA");
    try{
      vDinRep = dTRARegSolicitud.updateAutorizaRec(vDinRep,null);
      if(Integer.parseInt(request.getParameter("iCveOficina")) != -1)
        vDinRep = dTRARegSolicitud.updateEnviarResolucionA(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito");
    try{
       dTRARegReqXTram.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  if(oAccion.getCAccion().equals("ValidaNotif")){
    TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();
    TFechas dFecha = new TFechas();
    Vector vNotif = new Vector();
    TVDinRep vDinNotif = new TVDinRep();
    java.sql.Date dtFechaFinal, dtFechaHoy;
    TVDinRep vDtNotificacion = oAccion.setInputs("dtNotificacion");
    boolean lNaturales = false;
    String cQuery = "SELECT " +
		"INUMDIASCUBRIRREQ,LDIASNATURALESREQ " +
		"FROM TRACONFIGURATRAMITE " +
		"where ICVETRAMITE = " + request.getParameter("iCveTramite") +
		" and ICVEMODALIDAD = " + request.getParameter("iCveModalidad") +
		" order by DTINIVIGENCIA desc ";

    vNotif = dTRARegReqXTram.findByCustom("ICVETRAMITE,iCveModalidad,dtIniVigencia", cQuery);
    vDinNotif = (TVDinRep)vNotif.get(0);
    if(vDinNotif.getInt("LDIASNATURALESREQ") == 1)
      lNaturales = true;
    dtFechaHoy = dFecha.TodaySQL();
    vDinNotif.put("dtNotificacion",vDtNotificacion.getString("dtNotificacion"));
    dtFechaFinal = dDiaNoHabil.getFechaFinal(vDinNotif.getDate("dtNotificacion"),Integer.parseInt(vParametros.getPropEspecifica("cDiasRecReqNotificados"),10),false);
    if(Integer.parseInt(dFecha.getFechaYYYYMMDDSinSep(dtFechaHoy),10) > Integer.parseInt(dFecha.getFechaYYYYMMDDSinSep(dtFechaFinal),10))
      lValido = 0;

  }

 vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 Vector vcListado = new Vector();
 try{
  vcListado = dTRARegReqXTram.findByCustom("iEjercicio,iNumSolicitud",
	"SELECT " +
	" TRAReqXModTramite.iCveTramite, " +
	" TRAReqXModTramite.iCveModalidad, " +
	" TRAReqXModTramite.iCveRequisito, " +
	" TRAReqXModTramite.iOrden, " +
	" COALESCE (TRARegReqXTram.dtNotificacion,rp.DTNOTIFICACION) as DTNOTIFICACION, " +
	" TRARegReqXTram.dtRecepcion, " +
	" TRARequisito.cDscRequisito, " +
	" TRARegReqXTram.lValido, " +
        " ' ' as cCa, " +
        " cNomAutorizaRecoger, " +
        " TRAREGSOLICITUD.iCveOficinaEnviaRes as iCveOficinaEnviaRes, " +
        " TRAREGSOLICITUD.cEnviarResolucionA as cEnviarResolucionA " +
//        " TRAREGREQXTRAM.dtCotejo "+       		
	" FROM TRAREGSOLICITUD " +
	"JOIN TRAReqXModTramite  ON TRAReqXModTramite.iCveTramite = TRAREGSOLICITUD.iCveTramite " +
	"                        AND TRAReqXModTramite.iCveModalidad = TRAREGSOLICITUD.ICVEMODALIDAD " +
	"join TRARegReqxTram  ON TRAREGSOLICITUD.iEjercicio = TRARegReqxTram.iEjercicio " +
	"                     AND TRAREGSOLICITUD.iNumSolicitud = TRAREGREQXTRAM.iNumSolicitud " +
	"                     and TRARegReqxTram.ICVETRAMITE = TRAReqXModTramite.ICVETRAMITE and TRARegReqxTram.ICVEMODALIDAD = TRAReqXModTramite.ICVEMODALIDAD " +
	"                     and TRARegReqxTram.ICVEREQUISITO = TRAReqXModTramite.ICVEREQUISITO " +
	"JOIN TRARequisito  ON TRAREQUISITO.ICVEREQUISITO = TRAReqXModTramite.ICVEREQUISITO " +
	"JOIN TRAREGPNCETAPA PE ON PE.IEJERCICIO=TRAREGSOLICITUD.IEJERCICIO AND PE.INUMSOLICITUD=TRAREGSOLICITUD.INUMSOLICITUD " +
	"                and pe.IORDEN = (select max (pe1.iorden) from TRAREGPNCETAPA pe1 where pe1.IEJERCICIO=pe.IEJERCICIO and pe1.INUMSOLICITUD=pe.INUMSOLICITUD) "+
	"  LEFT JOIN GRLREGISTROPNC RP ON RP.IEJERCICIO=PE.IEJERCICIOPNC AND RP.ICONSECUTIVOPNC=PE.ICONSECUTIVOPNC " +
  oAccion.getCFiltro() + oAccion.getCOrden());
 }catch(Exception ex){ex.printStackTrace();  cError = ex.getMessage(); }
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
                '<%=lValido%>');
</script>
<%}%>

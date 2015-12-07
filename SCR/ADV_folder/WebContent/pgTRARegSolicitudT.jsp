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
    vDinRep = oAccion.setInputs("ClavesTramite,ClavesModalidad,iCveRequisito,RequisitoPNC,Caracteristicas,iCveTipoPersona,iCveSolicitante,iCveDomicilioSolicitante,iCveRepLegal,iCveDomicilioRepLegal,cNomAutorizaRecoger,iCveOficinaUsr,iCveDeptoUsr,cObsTramite,cEnviarResolucionA,iCveUsuario,iIdBien,cDscBien");
    while (!lInsertado && iIntentos < iMaxIntentos){
      iIntentos++;
      try{
        vDinRep = dTRARegSolicitud.insertBatch(vDinRep,null);
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
  TVDinRep vFiltro = oAccion.setInputs("hdFiltro");


 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",
  "SELECT " +
  "TRAREGSOLICITUD.IEJERCICIO, " +
  "TRAREGSOLICITUD.INUMSOLICITUD, " +
  "GRLPersona.cNomRazonSocial || ' '||GRLPersona.cAPPaterno||' '|| GRLPersona.cAPMaterno AS cSolicitante, " +
  "TRACATTRAMITE.CDSCBREVE, " +
  "TRAMODALIDAD.CDSCMODALIDAD, " +
  " COALESCE   (TRAREGREQXTRAM.DTOFICIO,GRLREGISTROPNC.DTOFICIO) as dtoficio, " +
  " case TRAREGREQXTRAM.CNUMOFICIO " +
  "      when '' then GRLREGISTROPNC.CNUMOFICIO " +
  "      else TRAREGREQXTRAM.CNUMOFICIO " +
  "      end as CNUMOFICIO, " +
  "COALESCE ( TRAREGREQXTRAM.DTNOTIFICACION,GRLREGISTROPNC.DTNOTIFICACION) as DTNOTIFICACION, " +
  "GRLREGISTROPNC.CRECIBENOTIF, " +
  "GRLREGISTROPNC.ICONSECUTIVOPNC, "+
  "LTRAMINERNET "+
  "FROM TRAREGSOLICITUD " +
  "join GRLPersona ON GRLPersona.iCvePersona = TRARegSolicitud.iCveSolicitante " +
  "JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD " +
  "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE " +
  "JOIN TRAREGREQXTRAM ON TRAREGREQXTRAM.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO " +
  "AND TRAREGREQXTRAM.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD " +
  "AND TRAREGREQXTRAM.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE " +
  "AND TRAREGREQXTRAM.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD " +
  "LEFT JOIN TRAREGPNCETAPA ON TRAREGPNCETAPA.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO " +
  "AND TRAREGPNCETAPA.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD " +
  "LEFT JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC " +
  "AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC " +
  "WHERE  TRAREGSOLICITUD.INUMSOLICITUD NOT IN(SELECT TRAREGTRAMXSOL.INUMSOLICITUD " +
  "FROM TRAREGTRAMXSOL WHERE TRAREGTRAMXSOL.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND " +
  "TRAREGTRAMXSOL.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) " +
  " AND (TRAREGREQXTRAM.DTOFICIO IS NOT NULL  OR GRLREGISTROPNC.DTOFICIO is not null) " +
  " and TRAREGSOLICITUD.LABANDONADA = 0" +
  vFiltro.getString("hdFiltro") + oAccion.getCOrden());

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

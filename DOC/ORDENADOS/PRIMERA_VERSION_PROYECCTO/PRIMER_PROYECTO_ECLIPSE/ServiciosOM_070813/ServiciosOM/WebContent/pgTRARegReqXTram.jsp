<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>

<%
/**
 * <p>Title: pgTRARegReqXTram.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegReqXTram dTRARegReqXTram = new TDTRARegReqXTram();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

    TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    //vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iCveUsuRecibe,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,");
vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iCveUsuRecibe");
      //  vDinRep.put("iCveUsuRegistra",vUsuario.getICveusuario());
      //  vDinRep.put("iCveUsuRecibe",vUsuario.getICveusuario());
    try{

      //Obtengo fechas maximas para tablas TRATiempoTraslado y TRAConfiguraTramite
      /*String cSql = "Select MAX(dtIniVigencia) " +
                     "FROM GRLUsuarioXOfic " +
                     "JOIN TRATramiteXOfic on  GRLUsuarioXOfic.iCveOficina = TRATramiteXOfic.iCveOficina " +
                     "JOIN TRATiempoTraslado on TRATramiteXOfic.iCveOficina = TRATiempoTraslado.iCveOficinaOrigen " +
                     "AND  TRATramiteXOfic.iCveOficinaResuelve = TRATiempoTraslado.iCveOficinaDestino " +
                     "WHERE iCveUsuario = 1 " +
                     "AND TRATramiteXOfic.iCveTramite = 1 " +
                     "AND TRATiempoTraslado.iCveModalidad = 1 ";

     String cSql2 =  "Select MAX(dtIniVigencia)  " +
                     "FROM TRAConfiguraTramite  " +
                     "where TRAConfiguraTramite.iCveModalidad = 1  " +
                     "AND TRAConfiguraTramite.iCveTramite = 1 ";


      Vector vcFecha = dTRARegReqXTram.findByCustom("",cSql);
      Vector vcFecha2 = dTRARegReqXTram.findByCustom("",cSql2);*/

      /*String cSql1 = "Select GRLUsuarioXOfic.iCveOficina,iCveDepartamento,iNumDiasTraslado,lDiasNaturalesTraslado " +
                    "FROM GRLUsuarioXOfic " +
                    "JOIN TRATramiteXOfic on  GRLUsuarioXOfic.iCveOficina = TRATramiteXOfic.iCveOficina " +
                    "JOIN TRATiempoTraslado on TRATramiteXOfic.iCveOficina = TRATiempoTraslado.iCveOficinaOrigen " +
                    "AND  TRATramiteXOfic.iCveOficinaResuelve = TRATiempoTraslado.iCveOficinaDestino " +
                    "WHERE iCveUsuario = 1 " +
                    "AND TRATramiteXOfic.iCveTramite = 1 " +
                    "AND TRATiempoTraslado.iCveModalidad = 1 " +
                    "AND dtiniVigencia in (select max(dtinivigencia) from TraTiempoTraslado) ";

       String cSql2 = "SELECT iNumDiasResol,lDiasNaturalesResol,iNumDiasCubrirReq " +
                      "FROM TRAConfiguraTramite " +
                      "WHERE iCveTramite = 1 " +
                      "AND iCveModalidad = 1 " +
                      "AND dtiniVigencia in (select max(dtinivigencia) from TraConfiguraTramite)";
       Vector vcOfic = dTRARegReqXTram.findByCustom("",cSql1);
       Vector vcOfic2 = dTRARegReqXTram.findByCustom("",cSql2);


      // Sumo iNumDiasTraslado y iNumDiasResol a la fecha actual viendo si son naturales o no para obtener dtEstimadaEntrega
      // Sumo iNumDiasCubrirReq a la fecha actual tomando si son naturales o no para obtener dtLimiteEntregaDocs
      String cSql3 = "Select distinct (CURRENT DATE) + DAY('" + ((TVDinRep)vcOfic.get(2)).getInt("iNumDiasTraslado") + "') DAYS + DAY('"+((TVDinRep)vcOfic2.get(0)).getInt("iNumDiasResol")+"') as dtEstimadaEntrega from TRAConfiguraTramite ";
      String cSql4 = "Select distinct (CURRENT DATE) + DAY('"+((TVDinRep)vcOfic2.get(2)).getInt("iNumDiasCubrirReq")+"') DAYS as dtLimiteEntregaDocs from TRAConfiguraTramite ";

      Vector vcFecha1 = dTRARegReqXTram.findByCustom("",cSql3);
      Vector vcFecha2 = dTRARegReqXTram.findByCustom("",cSql4);*/

      /*if(vcFecha1.size()>0 && vcFecha2.size() >0 && vcOfic.size() > 0 && vcOfic2.size() >0){
            vDinRep.put("dtEstimadaEntrega",((TVDinRep)vcFecha1.get(0)).getInt("dtEstimadaEntrega"));
            vDinRep.put("dtLimiteEntregaDocs",((TVDinRep)vcFecha2.get(0)).getInt("dtLimiteEntregaDocs"));
            vDinRep.put("iCveOficina",((TVDinRep)vcOfic.get(0)).getInt("iCveOficina"));
            vDinRep.put("iCveDepartamento",((TVDinRep)vcOfic.get(1)).getInt("iCveDepartamento"));
            vDinRep = dTRARegReqXTram.insert(vDinRep,null);
        }else
           cError="Oficina";*/
        vDinRep = dTRARegReqXTram.insert(vDinRep,null);
     //*********************************************  Insertar  ************************************************


    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,dtRecepcion,iCveUsuRecibe");
    try{
      vDinRep = dTRARegReqXTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

   /** Verifica si la Acción a través de hdBotón es igual a "NoTienePNC" (Actualizar) */
  if(oAccion.getCAccion().equals("NoTienePNC")){
    vDinRep = oAccion.setInputs("iEjercicioSel,iConsecutivoSel");
    try{
      vDinRep = dTRARegReqXTram.updateNoTienePNC(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  /** Verifica si la Acción a través de hdBotón es igual a "TienePNC" (Actualizar) */
  if(oAccion.getCAccion().equals("TienePNC")){
    vDinRep = oAccion.setInputs("iEjercicio,iConsecutivo");
    try{
      vDinRep = dTRARegReqXTram.updateTienePNC(vDinRep,null);
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
  String cWhere = " AND ";
  if(oAccion.getCFiltro().equals("")==true)
  	cWhere = " where ";

  String cSql = "Select iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,dtRecepcion,iCveUsuRecibe " +
                "from TRARegReqXTram " +
                 oAccion.getCFiltro() + cWhere + "TRARegReqXTram.iCveUsuRecibe = 1"; //+ vUsuario.getICveusuario() + oAccion.getCOrden();
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRARegReqXTram.findByCustom("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito",cSql);
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

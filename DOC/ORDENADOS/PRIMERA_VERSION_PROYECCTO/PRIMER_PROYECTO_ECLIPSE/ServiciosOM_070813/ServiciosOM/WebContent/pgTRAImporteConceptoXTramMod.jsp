<%@page import="java.util.*"%>
<%@page import="com.micper.ingsw.*"%>
<%@page import="com.micper.util.logging.*"%>
<%@page import="com.micper.seguridad.vo.*"%>
<%@page import="com.micper.seguridad.dao.*"%>
<%@page import="gob.sct.sipmm.dao.*"%>
<%@page import="com.micper.sql.*"%>
<%
  /**
   * <p>Title: pgTRAConceptoXTramMod.jsp</p>
   * <p>Description: JSP "Catálogo" de la entidad TRAConceptoXTramMod</p>
   * <p>Copyright: Copyright (c) 2005 </p>
   * <p>Company: Tecnología InRed S.A. de C.V. </p>
   * @author ABarrientos
   * @version 1.0
   */
  TLogger.setSistema("44");
  TParametro vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAConceptoXTramMod dTRAConceptoXTramMod = new TDTRAConceptoXTramMod();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if (!oAccion.unaSesion(vParametros, (CFGSesiones) application.getAttribute("Sesiones"), (TVUsuario) request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else {
    /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
    if (oAccion.getCAccion().equals("Guardar")) {
      vDinRep = oAccion.setInputs("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto1");
      try {
        vDinRep = dTRAConceptoXTramMod.insert(vDinRep, null);
      }
      catch (Exception e) {
        cError = "Guardar";
      }
      oAccion.setBeanPK(vDinRep.getPK());
    }
    /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
    if (oAccion.getCAccion().equals("GuardarA")) {
      vDinRep = oAccion.setInputs("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto,lPagoAnticipado");
      try {
        vDinRep = dTRAConceptoXTramMod.update(vDinRep, null);
      }
      catch (Exception e) {
        cError = "Guardar";
      }
      oAccion.setBeanPK(vDinRep.getPK());
    }
    if (oAccion.getCAccion().equals("Cambia")) {
      vDinRep = oAccion.setInputs("iEjercicio,iCveTramite,iCveModalidad,iCveConcepto,cConjunto");
      try {
        vDinRep = dTRAConceptoXTramMod.Cambia(vDinRep, null);
      }
      catch (Exception e) {
        cError = "Guardar";
      }
      oAccion.setBeanPK(vDinRep.getPK());
    }
    /**
     * Ejecuta la acción ActualizaAlfanumerico que actualiza al concepto que existe una referencia alfanumérica
     * 15.Nov.2006
     */
    if (oAccion.getCAccion().equals("ActualizaAlfanumerico")) {
      vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cRefAlfaNumerica");
      try {
        vDinRep = dTRAConceptoXTramMod.actualizaRefAlfaNum(vDinRep, null);
      }
      catch (Exception e) {
        cError = "Guardar";
      }
      oAccion.setBeanPK(vDinRep.getPK());
    }
    /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
    if (oAccion.getCAccion().equals("Borrar")) {
      oAccion.setCAccion("Actual");
      vDinRep = oAccion.setInputs("iCveGrupo,iCveConcepto,iCveTramite,iCveModalidad");
      try {
        dTRAConceptoXTramMod.delete(vDinRep, null);
      }
      catch (Exception ex) {
        if (ex.getMessage().equals("") == false) {
          cError = "Cascada";
        }
        else
          cError = "Borrar";
      }
    }
    Vector vImportesCalculados = new Vector();
    boolean lPasoProceso = false;
    if (oAccion.getCAccion().equals("Calcular")) {
      vDinRep = oAccion.setInputs("iCveGrupo,iCveConcepto,iCveTramite,iCveModalidad,iUnidades,lEsTarifa,lEsPorcentaje,dImporteSinAjuste,dImporteAjustado,cDscConcepto,iRefNumerica,lAplicaFactorDirecto");
      vImportesCalculados = dTRAConceptoXTramMod.calculaConceptosDeTramYMod(vDinRep);
      lPasoProceso = true;
    }
    if (oAccion.getCAccion().equals("CalcularGenerar")) {
      vDinRep = oAccion.setInputs("hdaRes,iCveUsuarioIng,iCveOficIngresos,iCveUnidAdmva");
      vImportesCalculados = dTRAConceptoXTramMod.generaMov(vDinRep);
      lPasoProceso = true;
    }
    if (oAccion.getCAccion().equals("BuscaSolIng")) {
      vDinRep = oAccion.setInputs("cRFC");
      vImportesCalculados = dTRAConceptoXTramMod.buscaUsuIng(vDinRep);
      lPasoProceso = true;
    }
    if (oAccion.getCAccion().equals("RegistraSolIng")) {
      vDinRep = oAccion.setInputs("iCvePersona,cRPA,cRFC,cNombre,cApPaterno,cApMaterno,iTipoPersona,cCalle,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,cCURP,cNumExterior,cNumInterior");
      vImportesCalculados = dTRAConceptoXTramMod.registraUsuIng(vDinRep);
      lPasoProceso = true;
    }
    /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
    String cSql = "";
    int iCveTramite = new Integer(request.getParameter("iCveTramite")).intValue();
    int iCveModalidad = new Integer(request.getParameter("iCveModalidad")).intValue();
    int lPagoAnticipado = new Integer(request.getParameter("lPagoAnticipado")).intValue();
    int iEjercicio = new Integer(request.getParameter("iEjercicio")).intValue();
    int iNumSolicitud = new Integer(request.getParameter("iNumSolicitud")).intValue();

    Vector vcListado = new Vector();
    if (vImportesCalculados.size()>0 || lPasoProceso )
      vcListado = vImportesCalculados;
    else{
        vcListado = dTRAConceptoXTramMod.regresaConceptosDeTramYMod(iCveTramite, iCveModalidad, lPagoAnticipado, vImportesCalculados,iEjercicio,iNumSolicitud);
    }
    oAccion.navega(vcListado);
    String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js" type="" ></SCRIPT>
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

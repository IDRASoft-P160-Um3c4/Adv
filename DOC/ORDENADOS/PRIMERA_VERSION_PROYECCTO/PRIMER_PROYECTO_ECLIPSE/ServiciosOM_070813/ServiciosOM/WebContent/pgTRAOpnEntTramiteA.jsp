<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="gob.sct.sipmm.dao.reporte.*;" %>
<%
/**
 * <p>Title: pgTRAOpnEntTramite.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAOpnEntTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAOpnEntTramite dTRAOpnEntTramite = new TDTRAOpnEntTramite();
  TDObtenDatos dObtenDatos = new TDObtenDatos();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  int iCveDeptoPadre = 0;
  int iEjercicioOficio = 0;
  int iCveOficinaOficio = 0;
  int iCveDeptoOficio = 0;
  String cDigitosFolio = "";
  int iConsecutivoOficio = 0;
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("hdCveOpinionEntidad,lEsContestacion,cObsesSegto,iEjercicioSolicitud,iNumSolicitud,iCveDeptoUsr,iCveOficinaUsr");
    try{
      vDinRep = dTRAOpnEntTramite.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("hdCveOpinionEntidad,iEjercicioSolicitud,iNumSolicitud,iCveModalidad,iCveTramite,iCveSegtoEntidad,cObsesSegto");
    try{
      vDinRep = dTRAOpnEntTramite.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad");
    try{
       dTRAOpnEntTramite.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
  if(oAccion.getCAccion().equals("DeptoPadre")){
    dObtenDatos.dPadre.setOficinaDeptoHijo(Integer.parseInt(request.getParameter("hdCveOficina")),Integer.parseInt(request.getParameter("hdCveDeptoPadre")));
    iCveDeptoPadre = dObtenDatos.dPadre.getCveDepartamento();

  }
  if(oAccion.getCAccion().equals("SeparaOficio")){
    dObtenDatos.dFolio.setDatosFolio(request.getParameter("cNumOficioFil"));
    iEjercicioOficio = dObtenDatos.dFolio.getCveEjercicio();
    iCveOficinaOficio = dObtenDatos.dFolio.getCveOficina();
    iCveDeptoOficio = dObtenDatos.dFolio.getCveDepartamento();
    cDigitosFolio = dObtenDatos.dFolio.getCveDigitosFolio();
    iConsecutivoOficio = dObtenDatos.dFolio.getCveConsecutivo();

  }


	/** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
	String cSQL = "SELECT " +
		"distinct(TRA.ICVESEGTOENTIDAD) as icveseg, " +
		"OPE.ICVEOFICINAOPN as iCveOfi, " +
		"OPE.ICVEDEPARTAMENTOOPN as iCveDep, " +
		"SEG.LESCONTESTACION as lContes, " +
		"OFI.CDSCBREVE as cBreveOf, " +
		"DEPTO.CDSCBREVE as cBreveDep, " +
		"SEG.COBSESSEGTO as cObs, " +
		"TRA.ICVEOPINIONENTIDAD as iCveOpnE, " +
		"TRA.IEJERCICIOSOLICITUD as iEjerSol, " +
		"TRA.INUMSOLICITUD as iNumSol, " +
		"TRAM.CDSCBREVE as cTra, " +
		"MOD.CDSCMODALIDAD as cMod " +
		"FROM GRLFOLIOXSEGTOENT FXS " +
		"join GRLSEGTOENTIDAD SEG on FXS.ICVESEGTOENTIDAD = SEG.ICVESEGTOENTIDAD " +
		"join TRAOPNENTTRAMITE TRA on SEG.ICVESEGTOENTIDAD = TRA.ICVESEGTOENTIDAD " +
		"join GRLOPINIONENTIDAD OPE on TRA.ICVEOPINIONENTIDAD = OPE.ICVEOPINIONENTIDAD and OPE.LESOPINIONINTERNA = 1 " +
		"join GRLOFICINA OFI on OPE.ICVEOFICINAOPN = OFI.ICVEOFICINA " +
		"join GRLDEPARTAMENTO DEPTO on OPE.ICVEDEPARTAMENTOOPN = DEPTO.ICVEDEPARTAMENTO " +
		"join TRAREGSOLICITUD REGS on TRA.IEJERCICIOSOLICITUD = REGS.IEJERCICIO " +
		"and TRA.INUMSOLICITUD = REGS.INUMSOLICITUD " +
		"join TRACATTRAMITE TRAM on REGS.ICVETRAMITE = TRAM.ICVETRAMITE " +
		"join TRAMODALIDAD MOD on REGS.ICVEMODALIDAD = MOD.ICVEMODALIDAD ";

	Vector vcListado = dTRAOpnEntTramite.findByCustom("iEjercicioSolicitud,iNumSolicitud,iCveOpinionEntidad,iCveSegtoEntidad",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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
                '<%=iCveDeptoPadre%>',
                '<%=iEjercicioOficio%>',
                '<%=iCveOficinaOficio%>',
                '<%=iCveDeptoOficio%>',
                '<%=cDigitosFolio%>',
                '<%=iConsecutivoOficio%>');
</script>
<%}%>

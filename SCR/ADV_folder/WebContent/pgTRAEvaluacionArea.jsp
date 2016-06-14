<%@ page import="java.util.*"%>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*"%>
<%@ page import="com.micper.seguridad.vo.*"%>
<%@ page import="com.micper.seguridad.dao.*"%>
<%@ page import="gob.sct.sipmm.dao.*"%>
<%@ page import="com.micper.sql.*"%>
<%
	
	TLogger.setSistema("44");
	TParametro vParametros = new TParametro("44");
	TVDinRep vDinRep;
	
	TDTRARecepcion dTRARecepcion = new TDTRARecepcion();
	TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();
	
	String cError = "";
	CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
	
	/** Verifica si existe una o más sesiones */
	if (!oAccion.unaSesion(vParametros,
			(CFGSesiones) application.getAttribute("Sesiones"),
			(TVUsuario) request.getSession(true).getAttribute("UsrID")))
		out.print(oAccion.getErrorSesion(vParametros
				.getPropEspecifica("RutaFuncs")));
	else {

		if (oAccion.getCAccion().equals("EtapaRecepVisita")) {
			vDinRep = oAccion
					.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,lAnexo,iCveOficinaUsr,iCveDeptoUsr,hdCveUsuario");

			try {
				vDinRep = dTRARecepcion.recepcionVisita(vDinRep, null);
			} catch (Exception e) {
				cError = "Guardar";
			}
			oAccion.setBeanPK(vDinRep.getPK());
		}

		if (oAccion.getCAccion().equals("guardaDatosEnvios")) {
			
			vDinRep = oAccion
					.setInputs("iEjercicio,iNumSolicitud,cFolDGAJL,cDirDGAJL,cFolDGST,cDirDGST,cDirGen,dtDGST,cRefDGST");
			try {
				dTRARecepcion.guardaDatosEnvios(vDinRep, null);
			} catch (Exception e) {
				cError = "Guardar";
			}

			oAccion.setBeanPK(vDinRep.getPK());
		}
		
		if (oAccion.getCAccion().equals("ResolucionNegativaPNC")) {
			
			vDinRep = oAccion
					.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveUsuario,iCveOficina,iCveDepartamento");
			try {
				regEtapasXModTram.resolucionNegativaPNC(vDinRep, null);
			} catch (Exception e) {
				cError = "Guardar";
			}

			oAccion.setBeanPK(vDinRep.getPK());
		}


		StringBuffer sb = new StringBuffer();

		String CID = (String) request.getParameter("cId");
		String cFiltro = "";
		
		if(CID.equals("ListadoTecnico")){

			sb.append("SELECT ");
			sb.append("TRARegSolicitud.iEjercicio as iEjercicio, ");
			sb.append("TRARegSolicitud.iNumSolicitud as iNumSolicitud, ");
			sb.append("P.cNomRazonSocial||' '|| P.cApPaterno ||' '|| P.cApMaterno as cSolicitante,");
			sb.append("TRACatTramite.cDscBreve as cTramite, ");
			sb.append("O.CDSCBREVE, ");
			sb.append("(SELECT MAX(ICONSECUTIVOPNC) FROM GRLREGISTROPNC where IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD AND LRESUELTO = 0) as icvepnc, ");
			sb.append("(SELECT MAX(ICONSECUTIVOPNC) FROM GRLREGISTROPNC where IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) as icvepnca ");
			sb.append("FROM TRARegsolicitud ");
			sb.append("JOIN TRAREGETAPASXMODTRAM ON TRARegSolicitud.IEJERCICIO = TRAREGETAPASXMODTRAM.IEJERCICIO ");
			sb.append("AND TRARegSolicitud.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD ");
			sb.append("AND TRARegSolicitud.iCveTramite = TRARegEtapasXModTram.iCveTramite ");
			sb.append("AND TRARegSolicitud.iCveModalidad = TRARegEtapasXModTram.iCveModalidad ");
			sb.append("JOIN TRACatTramite ON TRARegSolicitud.iCveTramite = TRACatTramite.iCveTramite ");
			sb.append("AND TRARegSolicitud.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD ");
			sb.append("AND TRARegSolicitud.iCveTramite = TRARegEtapasXModTram.iCveTramite ");
			sb.append("AND TRARegSolicitud.iCveModalidad = TRARegEtapasXModTram.iCveModalidad ");
			sb.append("LEFT JOIN TRARegTramXSol ON TRARegTramXSol.iEjercicio = TRARegsolicitud.iEjercicio ");
			sb.append("AND TRARegTramXSol.iNumSolicitud = TRARegsolicitud.iNumSolicitud ");
			sb.append("LEFT JOIN GRLPersona P ON P.iCvePersona = TRARegsolicitud.iCveSolicitante ");
			sb.append("left join GRLOFICINA O ON O.ICVEOFICINA = TRAREGSOLICITUD.ICVEOFICINA ");
			sb.append("where TRARegSolicitud.lImpreso = 1 ");
			sb.append(" AND TRARegSolicitud.LABANDONADA = 0 ");
			sb.append(" AND TRARegTramXSol.dtCancelacion IS NULL ");
			sb.append(" AND TRARegSolicitud.dtResolTram IS NULL ");
			sb.append(" AND TRARegSolicitud.lTecnico = 0 ");
			sb.append(" AND TRAREGETAPASXMODTRAM.ICVEETAPA in (2)"); //2 etapa de recpcion de tramites
			cFiltro = oAccion.getCFiltro().toUpperCase()
					.replaceAll("WHERE", " AND ");
		}else if(CID.equals("ListadoJuridico")){
			sb.append("SELECT ");
			sb.append("TRARegSolicitud.iEjercicio as iEjercicio, ");
			sb.append("TRARegSolicitud.iNumSolicitud as iNumSolicitud, ");
			sb.append("P.cNomRazonSocial||' '|| P.cApPaterno ||' '|| P.cApMaterno as cSolicitante,");
			sb.append("TRACatTramite.cDscBreve as cTramite, ");
			sb.append("O.CDSCBREVE, ");
			sb.append("(SELECT MAX(ICONSECUTIVOPNC) FROM GRLREGISTROPNC where IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD AND LRESUELTO = 0) as icvepnc, ") ;
			sb.append("(SELECT MAX(ICONSECUTIVOPNC) FROM GRLREGISTROPNC where IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) as icvepnca ");
			sb.append("FROM TRARegsolicitud ");
			sb.append("JOIN TRAREGETAPASXMODTRAM ON TRARegSolicitud.IEJERCICIO = TRAREGETAPASXMODTRAM.IEJERCICIO ");
			sb.append("AND TRARegSolicitud.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD ");
			sb.append("AND TRARegSolicitud.iCveTramite = TRARegEtapasXModTram.iCveTramite ");
			sb.append("AND TRARegSolicitud.iCveModalidad = TRARegEtapasXModTram.iCveModalidad ");
			sb.append("JOIN TRACatTramite ON TRARegSolicitud.iCveTramite = TRACatTramite.iCveTramite ");
			sb.append("AND TRARegSolicitud.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD ");
			sb.append("AND TRARegSolicitud.iCveTramite = TRARegEtapasXModTram.iCveTramite ");
			sb.append("AND TRARegSolicitud.iCveModalidad = TRARegEtapasXModTram.iCveModalidad ");
			sb.append("LEFT JOIN TRARegTramXSol ON TRARegTramXSol.iEjercicio = TRARegsolicitud.iEjercicio ");
			sb.append("AND TRARegTramXSol.iNumSolicitud = TRARegsolicitud.iNumSolicitud ");
			sb.append("LEFT JOIN GRLPersona P ON P.iCvePersona = TRARegsolicitud.iCveSolicitante ");
			sb.append("left join GRLOFICINA O ON O.ICVEOFICINA = TRAREGSOLICITUD.ICVEOFICINA ");
			sb.append("where TRARegSolicitud.lImpreso = 1 ");
			sb.append(" AND TRARegSolicitud.LABANDONADA = 0 ");
			sb.append(" AND TRARegTramXSol.dtCancelacion IS NULL ");
			sb.append(" AND TRARegSolicitud.dtResolTram IS NULL ");
			sb.append(" AND TRARegSolicitud.lJuridico = 0 ");
			sb.append(" AND TRAREGETAPASXMODTRAM.ICVEETAPA in (2)"); //2 etapa de recpcion de tramites
			cFiltro = oAccion.getCFiltro().toUpperCase()
					.replaceAll("WHERE", " AND ");
		}
		else if(CID.equals("BuscaPNCExistente")){
			sb.append("SELECT count(IEJERCICIO) as ccuenta FROM GRLREGISTROPNC "); //2 etapa de recpcion de tramites
			cFiltro = oAccion.getCFiltro().toUpperCase();
		}else if(CID.equals("BuscaResolucion")){
			sb.append("SELECT count(DTRESOLTRAM) as ccuenta FROM TRAREGSOLICITUD "); //2 etapa de recpcion de tramites
			cFiltro = oAccion.getCFiltro().toUpperCase();
		}

		Vector vcListado = null;
		if(!sb.toString().equals("")){
			 vcListado = dTRARecepcion.findByCustom(
				"iEjercicio,iNumSolicitud", sb.toString() + cFiltro
						+ oAccion.getCOrden());
		}
		oAccion.navega(vcListado);
		String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript"
	SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%out.print(oAccion.getArrayCD());%>

fEngResultado('<%=request.getParameter("cNombreFRM")%>',
        '<%=request.getParameter("cId")%>',
        '<%=cError%>',
        '<%=cNavStatus%>',
        '<%=oAccion.getIRowPag()%>',
        '<%=oAccion.getBPK()%>');
</script>
<%
	}
%>

<%@ page import="java.util.*"%>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*"%>
<%@ page import="com.micper.seguridad.vo.*"%>
<%@ page import="com.micper.seguridad.dao.*"%>
<%@ page import="gob.sct.sipmm.dao.*"%>
<%@ page import="com.micper.sql.*"%>
<%
	/**
	 * <p>Title: pgTRARecepcion.jsp</p>
	 * <p>Copyright: Copyright (c) 2005 </p>
	 * <p>Company: Tecnología InRed S.A. de C.V. </p>
	 * @author ICaballero
	 * @version 1.0
	 */
	TLogger.setSistema("44");
	TParametro vParametros = new TParametro("44");
	TVDinRep vDinRep;
	TDTRARecepcion dTRARecepcion = new TDTRARecepcion();
	TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();
	String cError = "";
	CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
	String iCveEtapa = "";
	int iCveEtapaGuardar = 0;
	if (!request.getParameter("hdEtapa").equalsIgnoreCase("null")
			&& !request.getParameter("hdEtapa").equalsIgnoreCase(""))
		iCveEtapa = vParametros.getPropEspecifica(request
				.getParameter("hdEtapa"));
	else
		cError = "Los datos no fueron recibidos correctamente, favor de efectuar la búsqueda nuevamente.";

	if (!request.getParameter("hdEtapaGuardar")
			.equalsIgnoreCase("null")
			&& !request.getParameter("hdEtapaGuardar")
					.equalsIgnoreCase(""))
		iCveEtapaGuardar = Integer.parseInt(vParametros
				.getPropEspecifica(request
						.getParameter("hdEtapaGuardar")));
	else
		cError = "Los datos no fueron recibidos correctamente, favor de efectuar la búsqueda nuevamente.";

	/** Verifica si existe una o más sesiones */
	if (!oAccion.unaSesion(vParametros,
			(CFGSesiones) application.getAttribute("Sesiones"),
			(TVUsuario) request.getSession(true).getAttribute("UsrID")))
		out.print(oAccion.getErrorSesion(vParametros
				.getPropEspecifica("RutaFuncs")));
	else {

		/*mioooooo etapa recepcion en area y visita tecnica*/
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

		if (oAccion.getCAccion().equals("compFol")) {
			vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");

			try {
				dTRARecepcion.comprobarFolio(vDinRep, null);
			} catch (Exception e) {
				cError = "Guardar";
			}
			oAccion.setBeanPK(vDinRep.getPK());
		}

		/* etapa recepcion en area y visita tecnica*/

		if (oAccion.getCAccion().equals("RecibeTramite")) {
			vDinRep = oAccion
					.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,lAnexo,iCveOficinaUsr,iCveDeptoUsr,hdCveUsuario");

			try {
				if (iCveEtapaGuardar != 0) {
					vDinRep.put("iCveEtapa", iCveEtapaGuardar);
					vDinRep.put("iEtapaOficialia", iCveEtapa);
					if (iCveEtapaGuardar == Integer
							.parseInt(vParametros
									.getPropEspecifica("EtapaRegistro")))
						vDinRep.put("iCveDepartamentoAsg", request
								.getParameter("iCveDepartamentoAsg"));

					vDinRep = dTRARecepcion.recepcionTramite(vDinRep,
							null);
				} else
					cError = "Guardar";
			} catch (Exception e) {
				cError = "Guardar";
			}

			if (!cError.equals("Guardar"))
				regEtapasXModTram.cambiaEtapaWSCIS(vDinRep, null);

			oAccion.setBeanPK(vDinRep.getPK());
		}
		if (oAccion.getCAccion().equals("RecibeTramiteInt")) {
			System.out
					.println("Dentro del jsp recibe trámite internet...");
			vDinRep = oAccion
					.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,lAnexo,iCveOficinaUsr,iCveDeptoUsr,hdCveUsuario");

			try {
				if (iCveEtapaGuardar != 0) {
					vDinRep.put("iCveEtapa", iCveEtapaGuardar);
					vDinRep.put("iEtapaOficialia", iCveEtapa);
					if (iCveEtapaGuardar == Integer
							.parseInt(vParametros
									.getPropEspecifica("EtapaRegistro")))
						vDinRep.put("iCveDepartamentoAsg", request
								.getParameter("iCveDepartamentoAsg"));

					TDINTSolicitud intSol = new TDINTSolicitud();
					System.out
							.println("Antes de hacer la integracion de la fecha de integracion...");
					intSol.updateFechaIntegra(
							vDinRep.getInt("iEjercicio"),
							vDinRep.getInt("iNumSolicitud"),
							vDinRep.getInt("iCveDeptoUsr"),
							vDinRep.getInt("hdCveUsuario"), null);
					vDinRep = dTRARecepcion.recepcionTramite(vDinRep,
							null);
				} else
					cError = "Guardar";
			} catch (Exception e) {
				cError = "Guardar";
			}

			if (!cError.equals("Guardar"))
				regEtapasXModTram.cambiaEtapaWSCIS(vDinRep, null);

			oAccion.setBeanPK(vDinRep.getPK());
		}
		/** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

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

		StringBuffer sb = new StringBuffer();

		String CID = (String) request.getParameter("cId");

		String cFiltro = "";

		if (CID.equals("datosEnvios")) {
			sb.append("SELECT DE.iejercicio,DE.inumsolicitud,DE.cfolmemo,DE.cfoldgst,DE.cdirdgajl,DE.cdirdgst,DE.cdirgen, DE.dtDGST,DE.CREFDGST ");
			sb.append("FROM  tradatosenvios DE ");
			cFiltro = oAccion.getCFiltro().toUpperCase();
		} else {
			sb.append("SELECT ");
			sb.append("TRARegSolicitud.iEjercicio as iEjercicio, ");
			sb.append("TRARegSolicitud.iNumSolicitud as iNumSolicitud, ");
			sb.append("TRARegSolicitud.iCveTramite as iCveTramite, ");
			sb.append("TRARegSolicitud.iCveModalidad as iCveModalidad, ");
			sb.append("TRACatTramite.cDscBreve as cTramite, ");
			sb.append("TRAModalidad.cDscModalidad as cModalidad, ");
			sb.append("P.cNomRazonSocial||' '|| P.cApPaterno ||' '|| P.cApMaterno as cSolicitante,TRARegsolicitud.iCveSolicitante,O.CDSCBREVE ");
			sb.append("FROM TRARegsolicitud ");
			sb.append("JOIN TRACatTramite ON TRARegSolicitud.iCveTramite = TRACatTramite.iCveTramite ");
			sb.append("JOIN TRAModalidad ON TRARegSolicitud.iCveModalidad = TRAModalidad.iCveModalidad ");
			sb.append("JOIN TRAREGETAPASXMODTRAM ON TRARegSolicitud.IEJERCICIO = TRAREGETAPASXMODTRAM.IEJERCICIO ");
			sb.append("AND TRARegSolicitud.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD ");
			sb.append("AND TRARegSolicitud.iCveTramite = TRARegEtapasXModTram.iCveTramite ");
			sb.append("AND TRARegSolicitud.iCveModalidad = TRARegEtapasXModTram.iCveModalidad ");
			sb.append("LEFT JOIN TRARegTramXSol ON TRARegTramXSol.iEjercicio = TRARegsolicitud.iEjercicio ");
			sb.append("AND TRARegTramXSol.iNumSolicitud = TRARegsolicitud.iNumSolicitud ");
			sb.append("LEFT JOIN GRLPersona P ON P.iCvePersona = TRARegsolicitud.iCveSolicitante ");
			sb.append("left join GRLOFICINA O ON O.ICVEOFICINA = TRAREGSOLICITUD.ICVEOFICINA ");
			sb.append("where TRARegSolicitud.lImpreso = 1 ");
			sb.append("AND TRARegSolicitud.dtRecepcion is null");
			sb.append(" AND TRARegSolicitud.LABANDONADA = 0 ");
			sb.append(" AND TRARegTramXSol.dtCancelacion IS NULL ");
			sb.append(" AND TRAREGETAPASXMODTRAM.ICVEETAPA in ( "
					+ iCveEtapa + ")");
			sb.append(" AND TRARegEtapasXModTram.iOrden = ");
			sb.append("( ");
			sb.append("   SELECT ");
			sb.append("   MAX(EMT.iOrden) ");
			sb.append("   FROM TRARegEtapasXModTram EMT ");
			sb.append("   WHERE TRARegSolicitud.IEJERCICIO = EMT.IEJERCICIO ");
			sb.append("   AND TRARegSolicitud.INUMSOLICITUD = EMT.INUMSOLICITUD ");
			sb.append("   AND TRARegSolicitud.iCveTramite = EMT.iCveTramite ");
			sb.append("   AND TRARegSolicitud.iCveModalidad = EMT.iCveModalidad ");
			sb.append(") ");
			cFiltro = oAccion.getCFiltro().toUpperCase()
					.replaceAll("WHERE", " AND ");
		}

		//System.out.print(sb.toString() + cFiltro);
		Vector vcListado = dTRARecepcion.findByCustom(
				"iEjercicio,iNumSolicitud", sb.toString() + cFiltro
						+ oAccion.getCOrden());
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

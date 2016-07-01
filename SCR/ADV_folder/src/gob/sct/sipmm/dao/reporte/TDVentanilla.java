package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import gob.sct.sipmm.dao.*;
import com.micper.excepciones.DAOException;
import java.util.HashMap;

import org.apache.axis.types.Time;

/**
 * <p>
 * Title: TDVentanilla.java
 * </p>
 * <p>
 * Description: DAO con métodos para reportes de Ventanilla Única
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Tecnología InRed S.A. de C.V.
 * </p>
 * 
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDVentanilla extends DAOBase {
	private String cSistema = "44";
	private TParametro VParametros = new TParametro(cSistema);
	private TFechas tFecha = new TFechas(cSistema);
	private TDTramite tTramite = new TDTramite();

	private String cNombreReporte = "";
	private int iNumeroReporte = 0;
	private int iNumeroCopias = 1;
	private int iTiempoEsperaAcuses = 1500;
	private boolean lAutoImprimir = false;
	TExcel rep = new TExcel();

	private String dataSourceName = VParametros
			.getPropEspecifica("ConDBModulo");
	private String cLeyendaBien;
	private String cLeyendaPago;
	private String cLeyendaEval;
	private String cNotaCapitania;

	public TDVentanilla() {
	}

	public Vector findByCustom(String cKey, String cWhere) throws DAOException {
		Vector vcRecords = new Vector();
		cError = "";
		try {
			String cSQL = cWhere;
			vcRecords = this.FindByGeneric(cKey, cSQL, dataSourceName);
		} catch (Exception e) {
			e.printStackTrace();
			cError = e.toString();
		} finally {
			if (!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	public String Logico(int cCampo) {
		String dato = "";
		cError = "";
		try {
			if (cCampo == 0)
				dato = "NO";
			else
				dato = "SI";

		} catch (Exception e) {
			cError = e.toString();
		}
		return dato;
	}

	public void inicializaDatos(String cNumReporte) {
		String cNumModuloVentanilla = VParametros
				.getPropEspecifica("VentanillaModulo");

		String cSQL = "SELECT " + "  GRLReporte.iCveSistema, "
				+ "  GRLReporte.iCveModulo, " + "  GRLReporte.iNumReporte, "
				+ "  GRLReporte.cNomReporte, " + "  GRLReporte.iNumCopias, "
				+ "  GRLReporte.lAutoImprimir " + "FROM GRLReporte "
				+ "WHERE iCveSistema = " + cSistema + "  AND iCveModulo  = "
				+ cNumModuloVentanilla + "  AND iNumReporte = " + cNumReporte;
		try {
			TDGRLReporte dReporte = new TDGRLReporte();
			Vector vDatosReporte = dReporte.findByCustom(
					"iCveSistema,iCveModulo,iNumReporte", cSQL);
			if (vDatosReporte != null) {
				TVDinRep dRegistro = (TVDinRep) vDatosReporte.get(0);
				cNombreReporte = dRegistro.getString("cNomReporte");
				iNumeroReporte = dRegistro.getInt("iNumReporte");
				iNumeroCopias = dRegistro.getInt("iNumCopias");
				lAutoImprimir = (dRegistro.getInt("lAutoImprimir") == 0) ? false
						: true;
			}
		} catch (Exception e) {
		}
	}

	public StringBuffer acuseReciboTramite(String cSolicitud) throws Exception {
		String cNumReporteAcuseRecibo = VParametros
				.getPropEspecifica("VentanillaReporteAcuseRecibo");
		StringBuffer sbRetorno = new StringBuffer("");
		try {
			this.inicializaDatos(cNumReporteAcuseRecibo);
			sbRetorno = this.acuseReciboModificacionTramiteA(cSolicitud);
		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	public StringBuffer acuseModificaTramite(String cSolicitud)
			throws Exception {
		String cNumReporteAcuseRecibo = VParametros
				.getPropEspecifica("VentanillaReporteAcuseModifica");
		StringBuffer sbRetorno = new StringBuffer("");
		try {
			this.inicializaDatos(cNumReporteAcuseRecibo);
			sbRetorno = this.acuseReciboModificacionTramite(cSolicitud);
		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	public StringBuffer acuseComplementoDoc(String cSolicitud) throws Exception {
		String cNumReporteAcuseRecibo = VParametros
				.getPropEspecifica("VentanillaReporteAcuseModifica");
		StringBuffer sbRetorno = new StringBuffer("");
		try {
			this.inicializaDatos(cNumReporteAcuseRecibo);
			sbRetorno = this.acuseComplementoDocs(cSolicitud);
		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	public StringBuffer acuseReciboModificacionTramite(String cSolicitud)
			throws Exception {
		Vector vGenerales = new Vector(), vDerechos = new Vector(), vRequisitos = new Vector(), vOficResuelve = new Vector();
		cError = "";
		TDTRARegSolicitud regSolicitud = new TDTRARegSolicitud();
		int iUltimoSalto = 8, iNumRengXPag = 71, iTemp;
		int ilongBien = 0;
		int iRengBien = 0;
		int iRengConc = 0;
		int iRengGrupo = 0;
		int iLongReng = 137; // Número de caracteres por renglón
		int iRengBienPos = 0;
		int iRengConcPos = 0;
		int iRengGrpoPos = 0;
		int iLongConc = 0;
		int iLongGrupo = 0;

		String cSaltosPag = "";
		cLeyendaBien = VParametros.getPropEspecifica("LeyendaBien");
		cLeyendaPago = VParametros.getPropEspecifica("LeyendaPago");
		cLeyendaEval = VParametros.getPropEspecifica("LeyendaEvaluacion1");
		cLeyendaEval += " "
				+ VParametros.getPropEspecifica("LeyendaEvaluacion2");
		cNotaCapitania = VParametros.getPropEspecifica("NotaCapitania1");
		cNotaCapitania += " " + VParametros.getPropEspecifica("NotaCapitania2");
		int iRengIni = 10, iReng, iRengTemp, iRengImprocedente, iBorde = 1, iLineaFirma = 2, iFondoCeldaPrin = 37, iFondoEsp = 33, iFondoCeldaSec = -1, iLetrasXRenglonReq = 100, iLetrasXRenglonDer = 40;
		float fAltoRengIntermedio = 3.0f;
		String cFechaNula = "'- - - - - - - - - -", cTemp = "", cWhere, cOrder;
		StringBuffer sbRetorno = new StringBuffer("");
		TExcel rep = new TExcel();
		String cNomUsuRegistra, cDscDeptoRecibe, cDscOficinaRecibe;
		boolean lSolicRepLegal = true, lOficinaResolDiferente = false, lImprocedente = false, lRelacionadoImprocedente = false;
		String cRenglonesImprocedentes = "", cSolicitudesImprocedentes = "", cTramiteImproc = "", cModalidadImproc = "";

		String[] aSolicitudes = cSolicitud.split("/");
		if (cMensaje.equals("")) {
			cNomUsuRegistra = cDscDeptoRecibe = cDscOficinaRecibe = "";
			try {
				rep.iniciaReporte();
				rep.comDespliega("A", 8, cNombreReporte);
				iReng = iRengIni;

				// Procesa todas las solicitudes
				for (int j = 0; j < aSolicitudes.length; j++) {
					String[] aSolicitud = aSolicitudes[j].split(",");
					if (aSolicitud.length != 2)
						cMensaje = "No se proporcionó ejercicio o solicitud";
					int iEjercicio = Integer.parseInt(aSolicitud[0], 10);
					int iNumSolicitud = Integer.parseInt(aSolicitud[1], 10);

					cWhere = "WHERE RS.iEjercicio = " + iEjercicio
							+ " AND RS.iNumSolicitud = " + iNumSolicitud;
					cOrder = "ORDER BY RS.iEjercicio, RS.iNumSolicitud";

					lSolicRepLegal = false;
					// Datos Generales
					TVDinRep regGenerales = tTramite.getDatosGenSolicitud(
							cWhere, cOrder, lSolicRepLegal);

					if (regGenerales.getString("DTIMPRESION").equals("null"))
						try {
							regSolicitud.fechaImpresion(iEjercicio,
									iNumSolicitud, conn);
						} catch (Exception e) {
							e.printStackTrace();
						}

					// Registra la Fecha de compromiso de la solicitud.
					java.sql.Date dtTemp1 = regSolicitud.getFechaCompromiso(
							iEjercicio, iNumSolicitud, conn);

					int iOficinaReg = regGenerales.getInt("iCveOficina");
					int iDeptoReg = regGenerales.getInt("iCveDepartamento");
					int iTramite = regGenerales.getInt("iCveTramite");
					int iModalidad = regGenerales.getInt("iCveModalidad");
					if (regGenerales.getInt("lPrincipal") == 1) {
						lRelacionadoImprocedente = false;
						cRenglonesImprocedentes = "";
						cSolicitudesImprocedentes = "";
						cTramiteImproc = "";
						cModalidadImproc = "";
					}

					String cOficResuelve = regGenerales
							.getString("cDscBreveOfic");
					String cDeptoResuelve = regGenerales
							.getString("cDscBreveDepto");
					double dImporte = 0d;
					java.sql.Date dtTemp;
					if (j == 0) {
						cNomUsuRegistra = regGenerales
								.getString("cNomUsuRegistra");
						cDscDeptoRecibe = regGenerales
								.getString("cDscDepartamento");
						cDscOficinaRecibe = regGenerales
								.getString("cDscOficina");

						// Datos del solicitante
						rep.comDespliegaCombinado("DATOS DEL SOLICITANTE", "B",
								iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						iReng++;
						iRengTemp = iReng;
						rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								regGenerales.getString("cNomSolicitante"),
								false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "R.F.C.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cRFCSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("S", iReng, "Teléfono:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cTelefonoSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						cTemp = regGenerales.getString("cCalleSol") + " No. "
								+ regGenerales.getString("cNumExteriorSol");
						if (!regGenerales.getString("cNumInteriorSol").equals(
								""))
							cTemp += " Int. "
									+ regGenerales.getString("cNumInteriorSol");
						rep.comDespliegaAlineado("E", iReng, "Domicilio:",
								true, rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								cTemp, false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "Correo-E:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng + 1,
								regGenerales.getString("cCorreoESol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("T", iReng, "W", iReng + 1,
								rep.getAT_HJUSTIFICA());
						iReng += 2;
						rep.comDespliegaAlineado("E", iReng, "Colonia:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng,
								regGenerales.getString("cColoniaSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cCodPostalSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						iReng++;
						rep.comDespliegaAlineado("F", iReng,
								"Ciudad/Municipio:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("G", iReng, "O", iReng,
								regGenerales.getString("cNomMunicipioSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("Q", iReng, "Entidad:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("R", iReng, "W", iReng,
								regGenerales.getString("cNomEntidadSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comBordeRededor("B", iRengTemp, "X", iReng, iBorde,
								1);

						// Datos del representante legal si es que existe
						cTemp = regGenerales.getString("iCveRepLegal");

						if (cTemp != null && cTemp != ""
								&& !cTemp.equals("null")
								&& Integer.parseInt(cTemp, 10) > 0) {// agregado
																		// no se
																		// validaba
																		// cTemp
							iReng++;
							rep.comAltoReng("A", iReng, fAltoRengIntermedio);
							iReng++;
							rep.comDespliegaCombinado(
									"DATOS DEL REPRESENTANTE LEGAL", "B",
									iReng, "X", iReng,
									rep.getAT_COMBINA_CENTRO(), "", true,
									iFondoCeldaPrin, true, true, iBorde, 1);
							iReng++;
							iRengTemp = iReng;
							rep.comDespliegaAlineado("E", iReng, "Nombre:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1,
									regGenerales.getString("cNomRepLegal"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "R.F.C.:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cRFCRep"), false,
									rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							rep.comDespliegaAlineado("S", iReng, "Teléfono:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cTelefonoRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							cTemp = regGenerales.getString("cCalleRep")
									+ " No. "
									+ regGenerales.getString("cNumExteriorRep");
							if (!regGenerales.getString("cNumInteriorRep")
									.equals(""))
								cTemp += " Int. "
										+ regGenerales
												.getString("cNumInteriorRep");
							rep.comDespliegaAlineado("E", iReng, "Domicilio:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1, cTemp, false,
									rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "Correo-E:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W",
									iReng + 1,
									regGenerales.getString("cCorreoERep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("T", iReng, "W", iReng + 1,
									rep.getAT_HJUSTIFICA());
							iReng += 2;
							rep.comDespliegaAlineado("E", iReng, "Colonia:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q", iReng,
									regGenerales.getString("cColoniaRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cCodPostalRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							iReng++;
							rep.comDespliegaAlineado("F", iReng,
									"Ciudad/Municipio:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("G", iReng, "O", iReng,
									regGenerales.getString("cNomMunicipioRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("Q", iReng, "Entidad:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("R", iReng, "W", iReng,
									regGenerales.getString("cNomEntidadRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comBordeRededor("B", iRengTemp, "X", iReng,
									iBorde, 1);
						}
					}

					if (!lOficinaResolDiferente) {
						cWhere = "WHERE TXO.iCveOficina = " + iOficinaReg
								+ "  AND TXO.iCveTramite = " + iTramite;
						cOrder = "ORDER BY TXO.iCveOficina, TXO.iCveTramite";
						vOficResuelve = tTramite.getDatosOficinaResolucion(
								cWhere, cOrder);
						if (vOficResuelve != null && vOficResuelve.size() > 0) {
							TVDinRep regOficResuelve = (TVDinRep) vOficResuelve
									.get(0);
							if (iOficinaReg != regOficResuelve
									.getInt("iCveOficinaResuelve")) {
								cOficResuelve = regOficResuelve
										.getString("cDscBreveOfic");
								lOficinaResolDiferente = true;
							}
							if (iDeptoReg != regOficResuelve
									.getInt("iCveDepartamento"))
								cDeptoResuelve = regOficResuelve
										.getString("cDscBreveDepto");
						}
					}

					// Datos del trámite
					iReng++;
					rep.comAltoReng("A", iReng, fAltoRengIntermedio);
					iReng++;
					if (regGenerales.getInt("iIdCita") == 0)
						rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B",
								iReng, "O", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
					else {
						rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B",
								iReng, "J", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						rep.comDespliegaCombinado("No. CIS:", "K", iReng, "L",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaPrin, true, true, iBorde, 1);
						rep.comDespliegaCombinado(
								regGenerales.getString("iIdCita"), "M", iReng,
								"O", iReng, rep.getAT_COMBINA_DERECHA(), "",
								false, iFondoEsp, true, true, iBorde, 2);
					}
					rep.comDespliegaCombinado("Ejercicio:", "P", iReng, "Q",
							iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);

					rep.comFontBold("R", iReng, "X", iReng);
					rep.comFontColor("R", iReng, "R", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iEjercicio"), "R", iReng,
							"R", iReng, rep.getAT_COMBINA_DERECHA(), "", false,
							iFondoEsp, true, true, iBorde, 2);
					rep.comDespliegaCombinado("Solicitud No.:", "S", iReng,
							"U", iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);
					rep.comFontColor("V", iReng, "X", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iNumSolicitud"), "V",
							iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(), "",
							false, iFondoEsp, true, true, iBorde, 2);
					iReng++;
					iRengTemp = iReng;
					rep.comDespliegaAlineado("E", iReng, "Clave:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "H", iReng,
							regGenerales.getString("cCveInterna"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iRengImprocedente = iReng;
					cRenglonesImprocedentes += iReng + ",";
					cSolicitudesImprocedentes += regGenerales
							.getString("iNumSolicitud") + ",";
					cTramiteImproc += regGenerales.getString("iCveTramite")
							+ ",";
					cModalidadImproc += regGenerales.getString("iCveModalidad")
							+ ",";
					iReng++;
					rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "P", iReng + 3,
							regGenerales.getString("cDscTramite"), false,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR());
					rep.comAlineaRango("F", iReng, "P", iReng + 3,
							rep.getAT_HJUSTIFICA());

					/*
					 * rep.comDespliegaAlineado("F",iReng,"P",iReng,regGenerales.
					 * getString
					 * ("cDscBreve"),false,rep.getAT_COMBINA_IZQUIERDA(),"");
					 */
					rep.comDespliegaAlineado("R", iReng, "Modalidad:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("S", iReng, "X", iReng,
							regGenerales.getString("cDscModalidad"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					/*
					 * rep.comDespliegaAlineado("F",iReng,"P",iReng +
					 * 3,regGenerales
					 * .getString("cDscTramite"),false,rep.getAT_COMBINA_IZQUIERDA
					 * (),rep.getAT_VSUPERIOR());
					 */

					rep.comDespliegaAlineado("T", iReng, "Fecha de Solicitud:",
							true, rep.getAT_HDERECHA(), "");
					java.sql.Timestamp tsTemp = regGenerales
							.getTimeStamp("tsRegistro");
					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(
									tFecha.getDateSQL(tsTemp), "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng += 1;
					rep.comDespliegaAlineado("T", iReng, "Hora de Solicitud:",
							true, rep.getAT_HDERECHA(), "");

					/*
					 * cTemp = (tsTemp == null) ? cFechaNula : "'" +
					 * tFecha.getFechaDDMMMYYYY(tFecha.getDateSQL(tsTemp),"/");
					 */
					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getStringHoraHHMM_24(tsTemp, ":");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");

					iReng += 1;
					int iRengLimCubDocs = iReng;
					rep.comDespliegaAlineado("T", iReng,
							"Fecha Lím. Cubrir Doc.:", true,
							rep.getAT_HDERECHA(), "");
					dtTemp = regGenerales.getDate("dtLimiteEntregaDocs");
					cTemp = (dtTemp == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					rep.comDespliegaAlineado("T", iReng, "Fecha de Respuesta:",
							true, rep.getAT_HDERECHA(), "");
					// dtTemp1 = regGenerales.getDate("dtEstimadaEntrega");
					cTemp = (dtTemp1 == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(dtTemp1, "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					if (!cTemp.equals(cFechaNula)) {
						rep.comDespliega("T", iRengLimCubDocs, "");
						rep.comDespliega("U", iRengLimCubDocs, "");
					}
					iReng++;
					rep.comDespliegaAlineado("F", iReng,
							"Oficina de Resolución:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("G", iReng, "W", iReng,
							cOficResuelve, false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					rep.comDespliegaAlineado("F", iReng, "Departamento:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("G", iReng, "W", iReng,
							cDeptoResuelve, false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					/*
					 * rep.comDespliegaAlineado("B",iReng,"G",iReng,
					 * "Aplicar Trámite al Bien Núm.:"
					 * ,true,rep.getAT_COMBINA_DERECHA(),""); iTemp =
					 * regGenerales.getInt("iIdBien");
					 * rep.comDespliegaAlineado("H",iReng,"I",iReng, (iTemp > 0)
					 * ? iTemp + "" :
					 * "'- - -",false,rep.getAT_COMBINA_CENTRO(),""); cTemp =
					 * regGenerales.getString("cDscBien"); if(cTemp == null ||
					 * cTemp.equals("") || cTemp.equalsIgnoreCase("null")) cTemp
					 * = "NO SE ESPECIFICÓ ESTA INFORMACIÓN";
					 * rep.comDespliegaAlineado
					 * ("J",iReng,"W",iReng,cTemp,false,rep
					 * .getAT_COMBINA_IZQUIERDA(),"");
					 * rep.comAlineaRangoVer("B",
					 * iReng,"X",iReng,rep.getAT_VSUPERIOR());
					 * rep.comBordeRededor("B",iRengTemp,"X",iReng,iBorde,1);
					 */
					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 2;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}
					/*
					 * // Datos de Derechos del Trámite iReng++;
					 * rep.comAltoReng("A",iReng,fAltoRengIntermedio); iReng++;
					 * rep
					 * .comDespliegaCombinado("DERECHOS DEL TRÁMITE","B",iReng
					 * ,"X"
					 * ,iReng,rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaSec
					 * ,true,true,iBorde,1); iReng++; cWhere =
					 * "WHERE RRP.iEjercicio = " + iEjercicio +
					 * "  AND RRP.iNumSolicitud = " + iNumSolicitud; cOrder =
					 * "ORDER BY RRP.iEjercicio, RRP.iNumSolicitud, RRP.dtPago, RRP.iRefNumerica"
					 * ; vDerechos =
					 * tTramite.getDatosDerechosSolicitud(cWhere,cOrder);
					 * if(vDerechos != null && vDerechos.size() > 0){ // Datos
					 * de derechos TLetterNumberFormat numLetras = new
					 * TLetterNumberFormat(); double dImportePagado = 0d;
					 * rep.comDespliegaCombinado
					 * ("Unid.","B",iReng,"C",iReng,rep.
					 * getAT_COMBINA_CENTRO(),""
					 * ,true,iFondoCeldaSec,true,true,iBorde,1);
					 * rep.comDespliegaCombinado
					 * ("Ref. Núm.","D",iReng,"E",iReng,
					 * rep.getAT_COMBINA_CENTRO(
					 * ),"",true,iFondoCeldaSec,true,true,iBorde,1);
					 * rep.comDespliegaCombinado
					 * ("Imp. Pagar","F",iReng,"G",iReng
					 * ,rep.getAT_COMBINA_CENTRO
					 * (),"",true,iFondoCeldaSec,true,true,iBorde,1);
					 * rep.comDespliegaCombinado
					 * ("Fecha Pago","H",iReng,"I",iReng
					 * ,rep.getAT_COMBINA_CENTRO
					 * (),"",true,iFondoCeldaSec,true,true,iBorde,1);
					 * rep.comDespliegaCombinado
					 * ("Imp. Pagado","J",iReng,"K",iReng
					 * ,rep.getAT_COMBINA_CENTRO
					 * (),"",true,iFondoCeldaSec,true,true,iBorde,1);
					 * rep.comDespliegaCombinado
					 * ("Ref. AlfaNúm.","L",iReng,"N",iReng
					 * ,rep.getAT_COMBINA_CENTRO
					 * (),"",true,iFondoCeldaSec,true,true,iBorde,1);
					 * rep.comDespliegaCombinado
					 * ("Banco","O",iReng,"P",iReng,rep.
					 * getAT_COMBINA_CENTRO(),""
					 * ,true,iFondoCeldaSec,true,true,iBorde,1); //
					 * rep.comDespliegaCombinado
					 * ("Concepto","Q",iReng,"X",iReng,rep
					 * .getAT_COMBINA_CENTRO()
					 * ,"",true,iFondoCeldaSec,true,true,iBorde,1);
					 * rep.comDespliegaCombinado
					 * ("Total","Q",iReng,"X",iReng,rep.
					 * getAT_COMBINA_CENTRO(),""
					 * ,true,iFondoCeldaSec,true,true,iBorde,1);
					 * 
					 * TVDinRep regPago; iRengTemp = iReng + 1; boolean lPagado;
					 * int iRenglonesDer; for(int i = 0;i <
					 * vDerechos.size();i++){ iReng++; regPago = (TVDinRep)
					 * vDerechos.get(i); cTemp =
					 * regPago.getString("cDscConcepto"); // iRenglonesDer =
					 * (int) (cTemp.length() / iLetrasXRenglonDer); LEL
					 * iRenglonesDer = 2; //LEL101106 // if(cTemp.length() %
					 * iLetrasXRenglonDer > 0) // iRenglonesDer++; //LEL271106
					 * lPagado = (regPago.getInt("lPagado") == 1) ? true :
					 * false; cTemp = (regPago.getInt("iNumUnidades") == 0) ?
					 * "'- - -" : regPago.getString("iNumUnidades");
					 * rep.comDespliegaAlineado("B",iReng,"C",iReng +
					 * iRenglonesDer -
					 * 1,cTemp,false,rep.getAT_COMBINA_CENTRO(),"");
					 * rep.comDespliegaAlineado("D",iReng,"E",iReng +
					 * iRenglonesDer -
					 * 1,regPago.getString("iRefNumerica"),false,
					 * rep.getAT_COMBINA_CENTRO(),""); dImporte =
					 * regPago.getDouble("dImportePagar"); cTemp = (dImporte ==
					 * 0d) ? cFechaNula : "" + dImporte;
					 * rep.comDespliegaAlineado("F",iReng,"G",iReng +
					 * iRenglonesDer -
					 * 1,cTemp,false,rep.getAT_COMBINA_DERECHA(),""); dtTemp =
					 * regPago.getDate("dtPago"); cTemp = (dtTemp == null ||
					 * !lPagado) ? cFechaNula : "'" +
					 * tFecha.getFechaDDMMMYYYY(dtTemp,"/");
					 * rep.comDespliegaAlineado("H",iReng,"I",iReng +
					 * iRenglonesDer -
					 * 1,cTemp,false,rep.getAT_COMBINA_CENTRO(),""); dImporte =
					 * regPago.getDouble("dImportePagado"); dImportePagado +=
					 * dImporte; cTemp = (dImporte == 0d || !lPagado) ?
					 * cFechaNula : "" + dImporte;
					 * rep.comDespliegaAlineado("J",iReng,"K",iReng +
					 * iRenglonesDer -
					 * 1,cTemp,false,rep.getAT_COMBINA_DERECHA(),""); cTemp =
					 * regPago.getString("cRefAlfaNum"); if(cTemp == null) cTemp
					 * = regPago.getString("cFormatoSAT"); cTemp = (cTemp ==
					 * null || !lPagado) ? cFechaNula : "'" + cTemp;
					 * rep.comDespliegaAlineado("L",iReng,"N",iReng +
					 * iRenglonesDer -
					 * 1,cTemp,false,rep.getAT_COMBINA_CENTRO(),""); cTemp =
					 * regPago.getString("cDscBanco"); cTemp = (cTemp == null ||
					 * !lPagado) ? cFechaNula : cTemp;
					 * rep.comDespliegaAlineado("O",iReng,"P",iReng +
					 * iRenglonesDer -
					 * 1,cTemp,false,rep.getAT_COMBINA_CENTRO(),"");
					 * //rep.comDespliegaAlineado("Q",iReng,"X",iReng +
					 * iRenglonesDer -
					 * 1,regPago.getString("cDscConcepto"),false,
					 * rep.getAT_COMBINA_CENTRO(),"");
					 * rep.comCellFormat("D",iReng,"E",iReng + iRenglonesDer -
					 * 1,"00000000"); rep.comCellFormat("F",iReng,"G",iReng +
					 * iRenglonesDer - 1,"$ #,##0.00");
					 * rep.comCellFormat("J",iReng,"K",iReng + iRenglonesDer -
					 * 1,"$ #,##0.00"); rep.comAlineaRango("O",iReng,"P",iReng +
					 * iRenglonesDer - 1,rep.getAT_HIZQUIERDA());
					 * rep.comAlineaRango("Q",iReng,"X",iReng + iRenglonesDer -
					 * 1,rep.getAT_HJUSTIFICA());
					 * rep.comAlineaRangoVer("B",iReng,"X",iReng + iRenglonesDer
					 * - 1,rep.getAT_VSUPERIOR());
					 * rep.comBordeRededor("B",iReng,"C",iReng + iRenglonesDer -
					 * 1,iBorde,1); rep.comBordeRededor("D",iReng,"E",iReng +
					 * iRenglonesDer - 1,iBorde,1);
					 * rep.comBordeRededor("F",iReng,"G",iReng + iRenglonesDer -
					 * 1,iBorde,1); rep.comBordeRededor("H",iReng,"I",iReng +
					 * iRenglonesDer - 1,iBorde,1);
					 * rep.comBordeRededor("J",iReng,"K",iReng + iRenglonesDer -
					 * 1,iBorde,1); rep.comBordeRededor("L",iReng,"N",iReng +
					 * iRenglonesDer - 1,iBorde,1);
					 * rep.comBordeRededor("O",iReng,"P",iReng + iRenglonesDer -
					 * 1,iBorde,1); rep.comBordeRededor("Q",iReng,"X",iReng +
					 * iRenglonesDer - 1,iBorde,1); iReng += iRenglonesDer - 1;
					 * } rep.comDespliegaCombinado("","S",iReng,"U",iReng,rep.
					 * getAT_COMBINA_DERECHA
					 * (),"",true,iFondoCeldaSec,true,false,iBorde,0);
					 * rep.comAlineaRangoVer("R",iReng,"X",iReng +
					 * 1,rep.getAT_VSUPERIOR());
					 * rep.comCreaFormula("S",iReng,"=SUM(F" + iRengTemp + ":G"
					 * + (iReng - 1) + ")");
					 * 
					 * iReng++; }
					 * 
					 * /* ilongBien = cLeyendaBien.length(); iRengBien = 0;
					 * while(ilongBien > 0){ ilongBien = ilongBien - iLongReng;
					 * iRengBien += 1; } iRengBienPos = iReng;
					 * rep.comDespliegaCombinado
					 * (cLeyendaBien,"B",iReng,"X",iReng + iRengBien -
					 * 1,rep.getAT_COMBINA_IZQUIERDA
					 * (),rep.getAT_VSUPERIOR(),false
					 * ,iFondoCeldaSec,false,false,iBorde,1);
					 * rep.comAlineaRango("B",iReng,"X",iReng + iRengBien -
					 * 1,rep.getAT_HJUSTIFICA()); iReng += iRengBien - 1;
					 * iReng++; rep.comAltoReng("A",iReng,fAltoRengIntermedio);
					 * iReng++;
					 * rep.comDespliegaCombinado("CONCEPTOS DE PAGO","B"
					 * ,iReng,"X"
					 * ,iReng,rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaSec
					 * ,true,true,iBorde,1); iReng++; String cConceptos =
					 * fBuscaConceptos(iEjercicio,iTramite,iModalidad);
					 * iLongConc = cLeyendaBien.length(); iRengConc = 0;
					 * while(iLongConc > 0){ iLongConc = iLongConc - iLongReng;
					 * iRengConc += 1; } iRengConcPos = iReng;
					 * rep.comDespliegaCombinado(cConceptos,"B",iReng,"X",iReng
					 * + iRengConc -
					 * 1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR
					 * (),false,iFondoCeldaSec,false,false,iBorde,1);
					 * rep.comAlineaRango("B",iReng,"X",iReng + iRengConc -
					 * 1,rep.getAT_HJUSTIFICA()); iReng += iRengConc - 1;
					 * 
					 * iReng++; Vector vGrpCobro = new Vector(); vGrpCobro =
					 * fBuscaGrupos(iEjercicio,iTramite,iModalidad); for(int k =
					 * 0;k < vGrpCobro.size();k++){ iLongGrupo =
					 * (String.valueOf(vGrpCobro.elementAt(k))).length();
					 * iRengGrupo = 0; while(iLongGrupo > 0){ iLongGrupo =
					 * iLongGrupo - iLongReng; iRengGrupo += 1; } iRengGrpoPos =
					 * iReng; rep.comDespliegaCombinado(String.valueOf(k + 1) +
					 * ". " +
					 * String.valueOf(vGrpCobro.elementAt(k)),"B",iReng,"X"
					 * ,iReng
					 * ,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR()
					 * ,false,iFondoCeldaSec,false,false,iBorde,1);
					 * rep.comAlineaRango
					 * ("B",iReng,"X",iReng,rep.getAT_HJUSTIFICA()); iReng++;
					 * 
					 * } iReng--;
					 */
					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 3;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					// Datos de Requisitos del Trámite
					cWhere = "WHERE RRT.iEjercicio = " + iEjercicio
							+ "  AND RRT.iNumSolicitud = " + iNumSolicitud
							+ "  AND RRT.iCveTramite = " + iTramite
							+ "  AND RRT.iCveModalidad = " + iModalidad;
					cOrder = "ORDER BY RRT.dtRecepcion DESC, RMT.lRequerido DESC, RMT.iOrden, R.iCveRequisito";
					int iRenglones;
					vRequisitos = tTramite.getDatosRequisitosSolicitud(cWhere,
							cOrder);
					if (vRequisitos != null && vRequisitos.size() > 0) { // Datos
																			// de
																			// Requisitos
						iReng++;
						rep.comAltoReng("A", iReng, fAltoRengIntermedio);
						iReng++;
						rep.comDespliegaCombinado("REQUISITOS", "B", iReng,
								"X", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						iReng++;
						rep.comDespliegaCombinado("Recibido", "B", iReng, "E",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Obl.", "F", iReng, "F",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Descripción", "G", iReng,
								"X", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						/*
						 * rep.comDespliegaCombinado("Cve.","G",iReng,"G",iReng,
						 * rep.getAT_COMBINA_CENTRO(),"",true,
						 * iFondoCeldaSec,true,true,iBorde,1);
						 * rep.comDespliegaCombinado
						 * ("Descripción","H",iReng,"X",iReng,
						 * rep.getAT_COMBINA_CENTRO(),"",true,
						 * iFondoCeldaSec,true,true,iBorde,1);
						 */
						iRengTemp = iReng + 1;
						TVDinRep regRequisito;
						lImprocedente = false;
						boolean lUsuEncontrado = false;
						for (int i = 0; i < vRequisitos.size(); i++) {
							iReng++;
							regRequisito = (TVDinRep) vRequisitos.get(i);
							cTemp = regRequisito.getString("cDscRequisito");
							iRenglones = (int) (cTemp.length() / iLetrasXRenglonReq);
							if (cTemp.length() % iLetrasXRenglonReq > 0)
								iRenglones++;
							dtTemp = regRequisito.getDate("dtRecepcion");
							if (regRequisito.getDate("dtRecepcion") != null
									&& !lUsuEncontrado) {
								cNomUsuRegistra = regRequisito
										.getString("cNomUsuRecibe");
								lUsuEncontrado = true;
							}
							cTemp = (dtTemp == null) ? "NO ENTREGADO" : "'"
									+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
							rep.comDespliegaAlineado("B", iReng, "E", iReng
									+ iRenglones - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(),
									rep.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("F", iReng, "F", iReng
									+ iRenglones - 1, (regRequisito
									.getInt("lRequerido") == 1) ? "SI" : "NO",
									false, rep.getAT_COMBINA_CENTRO(), rep
											.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("G", iReng, "X", iReng
									+ iRenglones - 1,
									regRequisito.getString("cDscRequisito"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("H", iReng, "X", iReng
									+ iRenglones - 1, rep.getAT_HJUSTIFICA());
							rep.comBordeRededor("B", iReng, "E", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("F", iReng, "F", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("G", iReng, "G", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("H", iReng, "X", iReng
									+ iRenglones - 1, iBorde, 1);
							iReng += iRenglones - 1;
						}

						// Valida que todos los conceptos que requieren pago
						// anticipado esten pagados
						String cSQL4 = "SELECT "
								+ "       RS.IEJERCICIO, "
								+ "       RS.INUMSOLICITUD, "
								+ "       RS.ICVETRAMITE AS ICVETRAM, "
								+ "       RS.ICVEMODALIDAD, "
								+ "       RS.LPAGADO, "
								+ "       CTM.ICVECONCEPTO AS ICONCEPTO, "
								+ "       CTM.LPAGOANTICIPADO, "
								+ "       RP.LPAGADO AS CONCEPTOPAGADO, "
								+ "       CG.ICVEGRUPO "
								+ "FROM TRAREGSOLICITUD RS "
								+ "  JOIN TRACONCEPTOXTRAMMOD CTM ON CTM.IEJERCICIO = RS.IEJERCICIO "
								+ "    AND CTM.ICVETRAMITE = RS.ICVETRAMITE "
								+ "    AND CTM.ICVEMODALIDAD = RS.ICVEMODALIDAD "
								+ "  LEFT JOIN TRAREGREFPAGO RP ON RP.IEJERCICIO = RS.IEJERCICIO "
								+ "    AND RP.INUMSOLICITUD = RS.INUMSOLICITUD "
								+ "    AND RP.ICVECONCEPTO = CTM.ICVECONCEPTO "
								+ "  LEFT join TRACONCEPTOXGRUPO cg on ctm.ICVECONCEPTO = cg.ICVECONCEPTO "
								+ "WHERE RS.iEjercicio = " + iEjercicio
								+ "  AND RS.iNumSolicitud = " + iNumSolicitud
								+ "  AND CTM.lPagoAnticipado = 1 "
								+ " ORDER BY CG.ICVEGRUPO,CONCEPTOPAGADO";
						// try{
						Vector vAnticipadosPorPagar = this.findByCustom("",
								cSQL4);
						TVDinRep vConceptos = new TVDinRep();
						int iGpoPagado = 0;
						if (vAnticipadosPorPagar.size() > 0) {
							for (int inc1 = 0; inc1 < vAnticipadosPorPagar
									.size(); inc1++) {
								vConceptos = (TVDinRep) vAnticipadosPorPagar
										.get(inc1);
								if (vConceptos.getInt("ICVEGRUPO") != 0
										&& vConceptos.getInt("CONCEPTOPAGADO") == 1) {
									iGpoPagado = vConceptos.getInt("ICVEGRUPO");
								}
							}
							for (int inc = 0; inc < vAnticipadosPorPagar.size(); inc++) {
								vConceptos = (TVDinRep) vAnticipadosPorPagar
										.get(inc);
								if (vConceptos.getInt("ICVEGRUPO") == 0
										&& vConceptos.getInt("CONCEPTOPAGADO") == 0) {
									lImprocedente = true;
								}
								if (iGpoPagado >= 0) {
									if (vConceptos.getInt("ICVEGRUPO") != 0
											&& vConceptos
													.getInt("CONCEPTOPAGADO") != 1
											&& vConceptos.getInt("ICVEGRUPO") != iGpoPagado) {
										lImprocedente = true;
									}
								}
							}
						}
						if (iNumeroReporte == 1) {
							String cSQLPNC = "SELECT " + "ICONSECUTIVOPNC "
									+ "FROM TRAREGPNCETAPA "
									+ "where IEJERCICIO = " + iEjercicio
									+ " and INUMSOLICITUD = " + iNumSolicitud;

							Vector vSQLPNC = this.findByCustom("", cSQLPNC);
							if (vSQLPNC.size() > 0) {
								lImprocedente = true;
								lRelacionadoImprocedente = true;
							}
						}
						if (lImprocedente == true
								|| lRelacionadoImprocedente == true) {
							cSolicitudesImprocedentes = cSolicitudesImprocedentes
									.substring(
											0,
											cSolicitudesImprocedentes.length() - 1);
							cRenglonesImprocedentes = cRenglonesImprocedentes
									.substring(
											0,
											cRenglonesImprocedentes.length() - 1);
							String[] cRengImpro = cRenglonesImprocedentes
									.split(",");
							String[] cSolImproc = cSolicitudesImprocedentes
									.split(",");
							String[] cTramImproc = cTramiteImproc.split(",");
							String[] cModaImproc = cModalidadImproc.split(",");
							int iRengTempImp = 0;

							for (int x = 0; x < cRengImpro.length; x++) {

								iRengTempImp = Integer.parseInt(cRengImpro[x],
										10);
								rep.comFontColor("R", iRengTempImp, "X",
										iRengTempImp, 2);
								rep.comDespliegaCombinado(
										"TRÁMITE IMPROCEDENTE", "R",
										iRengTempImp, "X", iRengTempImp,
										rep.getAT_COMBINA_CENTRO(), "", true,
										iFondoEsp, true, false, iBorde, 1);
								rep.comDespliegaAlineado("T", iRengTempImp + 4,
										"T", iRengTempImp + 4, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
								rep.comDespliegaAlineado("U", iRengTempImp + 4,
										"W", iRengTempImp + 4, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
								rep.comDespliegaAlineado("T", iRengTempImp + 5,
										"T", iRengTempImp + 5, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
								rep.comDespliegaAlineado("U", iRengTempImp + 5,
										"W", iRengTempImp + 5, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
							}
							cRenglonesImprocedentes = "";
							// Actualiza etapa en que se encuentra el trámite y
							// lo concluye

							// LEL Elimina la leyenda de bien si el trámite es
							// improcedente
							if (regGenerales.getInt("iIdBien") == 0) {
								rep.comDespliegaCombinado("", "B",
										iRengBienPos, "X", iRengBienPos
												+ iRengBien - 1,
										rep.getAT_COMBINA_IZQUIERDA(),
										rep.getAT_VSUPERIOR(), false,
										iFondoCeldaSec, false, false, iBorde, 1);
								rep.comAlineaRango("B", iRengBienPos, "X",
										iRengBienPos + iRengBien - 1,
										rep.getAT_HJUSTIFICA());
							}
							try {
								for (int x = 0; x < cSolImproc.length; x++) {

									String cRelacionados = "SELECT INUMSOLICITUDREL,icvetramite,icvemodalidad "
											+ "FROM TRASOLICITUDREL SR "
											+ "JOIN TRAREGSOLICITUD S ON SR.IEJERCICIO=S.IEJERCICIO AND SR.INUMSOLICITUDREL=S.INUMSOLICITUD "
											+ "where SR.IEJERCICIO="
											+ regGenerales.getInt("iEjercicio")
											+ " and SR.INUMSOLICITUDREL <> "
											+ cSolImproc[x]
											+ " and SR.INUMSOLICITUDPRINC = "
											+ "(select INUMSOLICITUDPRINC from TRASOLICITUDREL where iejercicio="
											+ regGenerales.getInt("iEjercicio")
											+ " and INUMSOLICITUDREL="
											+ cSolImproc[x] + ") ";
									TVDinRep vDatos = new TVDinRep();
									vDatos.put("iEjercicio",
											regGenerales.getInt("iEjercicio"));
									vDatos.put("iNumSolicitud", cSolImproc[x]);
									vDatos.put("iCveTramite", cTramImproc[x]);
									vDatos.put("iCveModalidad", cModaImproc[x]);
									vDatos.put("iCveOficina",
											regGenerales.getInt("iCveOficina"));
									vDatos.put("iCveDepartamento", regGenerales
											.getInt("iCveDepartamento"));
									vDatos.put("iCveUsuario", regGenerales
											.getInt("iCveUsuRegistra"));
									vDatos.put("iCveUsuRegistra", regGenerales
											.getInt("iCveUsuRegistra"));
									vDatos.put("iCveUsuEntrega", regGenerales
											.getInt("iCveUsuRegistra"));

									tTramite.updateImprocedenteFaltaPago(vDatos);

								}
								cSolicitudesImprocedentes = "";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						if (iReng >= (iUltimoSalto + iNumRengXPag)) {
							iUltimoSalto = iRengTemp - 2;
							cSaltosPag += (cSaltosPag.equals("")) ? ""
									+ iUltimoSalto : "," + iUltimoSalto;
						}
					}
				}
				iReng++;
				rep.comAltoReng("A", iReng, fAltoRengIntermedio);
				iReng++;
				rep.comDespliegaCombinado("RECIBE", "B", iReng, "L", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				rep.comDespliegaCombinado("SELLO", "R", iReng, "X", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				iReng++;
				iRengTemp = iReng;
				rep.comBordeRededor("B", iReng, "L", iReng + 2, iLineaFirma, 1);
				rep.comBordeRededor("B", iReng, "L", iReng + 5, iBorde, 1);
				rep.comBordeRededor("R", iReng, "X", iReng + 10, iBorde, 1);
				iReng += 3;
				rep.comDespliegaCombinado(cNomUsuRegistra, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				iReng++;
				rep.comDespliegaCombinado(cDscDeptoRecibe, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				iReng++;
				rep.comDespliegaCombinado(cDscOficinaRecibe, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				/*
				 * if(lOficinaResolDiferente){ iReng += 2;
				 * rep.comDespliegaCombinado(cNotaCapitania,"B",iReng,"P",iReng
				 * +
				 * 3,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,
				 * -1,false,false,0,0); rep.comAlineaRango("B",iReng,"P",iReng +
				 * 3,rep.getAT_HJUSTIFICA()); iReng += 3; }
				 */
				// LEL12102006
				iReng += 4;
				/*
				 * int ilongPago = cLeyendaPago.length(); int iRengPago = 0;
				 * while(ilongPago > 0){ ilongPago = ilongPago - iLongReng;
				 * iRengPago += 1; }
				 * rep.comDespliegaCombinado(cLeyendaPago,"B",iReng,"X",iReng +
				 * iRengPago -
				 * 1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR
				 * (),false,iFondoCeldaSec,false,false,iBorde,1);
				 * rep.comAlineaRango("B",iReng,"X",iReng + iRengPago -
				 * 1,rep.getAT_HJUSTIFICA()); iReng += iRengPago + 1;
				 */
				int ilongEvalua = cLeyendaEval.length();
				int iRengEvalua = 0;
				while (ilongEvalua > 0) {
					ilongEvalua = ilongEvalua - iLongReng;
					iRengEvalua += 1;
				}
				rep.comDespliegaCombinado(cLeyendaEval, "B", iReng, "X", iReng
						+ iRengEvalua - 1, rep.getAT_COMBINA_IZQUIERDA(),
						rep.getAT_VSUPERIOR(), false, iFondoCeldaSec, false,
						false, iBorde, 1);
				rep.comAlineaRango("B", iReng, "X", iReng + iRengEvalua - 1,
						rep.getAT_HJUSTIFICA());
				iReng += iRengEvalua - 1;
				// FinLEL12102006

				if (iReng >= (iUltimoSalto + iNumRengXPag)) {
					iUltimoSalto = iRengTemp - 2;
					cSaltosPag += (cSaltosPag.equals("")) ? "" + iUltimoSalto
							: "," + iUltimoSalto;
				}

				if (!cSaltosPag.equals(""))
					rep.comSaltosPagina(cSaltosPag);
				sbRetorno = rep.getSbDatos();
			} catch (Exception e) {
				e.printStackTrace();
				cMensaje += e.getMessage();
			}
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	public StringBuffer acuseComplementoDocs(String cSolicitud)
			throws Exception {
		Vector vGenerales = new Vector(), vDerechos = new Vector(), vRequisitos = new Vector(), vOficResuelve = new Vector();
		cError = "";
		TDTRARegSolicitud regSolicitud = new TDTRARegSolicitud();
		int iUltimoSalto = 8, iNumRengXPag = 71, iTemp;
		int ilongBien = 0;
		int iRengBien = 0;
		int iRengConc = 0;
		int iRengGrupo = 0;
		int iLongReng = 137; // Número de caracteres por renglón
		int iRengBienPos = 0;
		int iRengConcPos = 0;
		int iRengGrpoPos = 0;
		int iLongConc = 0;
		int iLongGrupo = 0;

		String cSaltosPag = "";
		cLeyendaBien = VParametros.getPropEspecifica("LeyendaBien");
		cLeyendaPago = VParametros.getPropEspecifica("LeyendaPago");
		cLeyendaEval = VParametros.getPropEspecifica("LeyendaEvaluacion1");
		cLeyendaEval += " "
				+ VParametros.getPropEspecifica("LeyendaEvaluacion2");
		cNotaCapitania = VParametros.getPropEspecifica("NotaCapitania1");
		cNotaCapitania += " " + VParametros.getPropEspecifica("NotaCapitania2");
		int iRengIni = 10, iReng, iRengTemp, iRengImprocedente, iBorde = 1, iLineaFirma = 2, iFondoCeldaPrin = 37, iFondoEsp = 33, iFondoCeldaSec = -1, iLetrasXRenglonReq = 100, iLetrasXRenglonDer = 40;
		float fAltoRengIntermedio = 3.0f;
		String cFechaNula = "'- - - - - - - - - -", cTemp = "", cWhere, cOrder;
		StringBuffer sbRetorno = new StringBuffer("");
		TExcel rep = new TExcel();
		String cNomUsuRegistra, cDscDeptoRecibe, cDscOficinaRecibe;
		boolean lSolicRepLegal = true, lOficinaResolDiferente = false, lImprocedente = false, lRelacionadoImprocedente = false;
		String cRenglonesImprocedentes = "", cSolicitudesImprocedentes = "", cTramiteImproc = "", cModalidadImproc = "";

		String[] aSolicitudes = cSolicitud.split("/");
		if (cMensaje.equals("")) {
			cNomUsuRegistra = cDscDeptoRecibe = cDscOficinaRecibe = "";
			try {
				rep.iniciaReporte();
				rep.comDespliega("A", 8, cNombreReporte);
				iReng = iRengIni;

				// Procesa todas las solicitudes
				for (int j = 0; j < aSolicitudes.length; j++) {
					String[] aSolicitud = aSolicitudes[j].split(",");
					if (aSolicitud.length != 2)
						cMensaje = "No se proporcionó ejercicio o solicitud";
					int iEjercicio = Integer.parseInt(aSolicitud[0], 10);
					int iNumSolicitud = Integer.parseInt(aSolicitud[1], 10);

					cWhere = "WHERE RS.iEjercicio = " + iEjercicio
							+ " AND RS.iNumSolicitud = " + iNumSolicitud;
					cOrder = "ORDER BY RS.iEjercicio, RS.iNumSolicitud";

					lSolicRepLegal = false;
					// Datos Generales
					TVDinRep regGenerales = tTramite.getDatosGenSolicitud(
							cWhere, cOrder, lSolicRepLegal);

					if (regGenerales.getString("DTIMPRESION").equals("null"))
						try {
							regSolicitud.fechaImpresion(iEjercicio,
									iNumSolicitud, conn);
						} catch (Exception e) {
							e.printStackTrace();
						}

					// Registra la Fecha de compromiso de la solicitud.
					java.sql.Date dtTemp1 = regSolicitud.getFechaCompromiso(
							iEjercicio, iNumSolicitud, conn);

					int iOficinaReg = regGenerales.getInt("iCveOficina");
					int iDeptoReg = regGenerales.getInt("iCveDepartamento");
					int iTramite = regGenerales.getInt("iCveTramite");
					int iModalidad = regGenerales.getInt("iCveModalidad");
					if (regGenerales.getInt("lPrincipal") == 1) {
						lRelacionadoImprocedente = false;
						cRenglonesImprocedentes = "";
						cSolicitudesImprocedentes = "";
						cTramiteImproc = "";
						cModalidadImproc = "";
					}

					String cOficResuelve = regGenerales
							.getString("cDscBreveOfic");
					String cDeptoResuelve = regGenerales
							.getString("cDscBreveDepto");
					double dImporte = 0d;
					java.sql.Date dtTemp;
					if (j == 0) {
						cNomUsuRegistra = regGenerales
								.getString("cNomUsuRegistra");
						cDscDeptoRecibe = regGenerales
								.getString("cDscDepartamento");
						cDscOficinaRecibe = regGenerales
								.getString("cDscOficina");

						// Datos del solicitante
						rep.comDespliegaCombinado("DATOS DEL SOLICITANTE", "B",
								iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						iReng++;
						iRengTemp = iReng;
						rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								regGenerales.getString("cNomSolicitante"),
								false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "R.F.C.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cRFCSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("S", iReng, "Teléfono:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cTelefonoSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						cTemp = regGenerales.getString("cCalleSol") + " No. "
								+ regGenerales.getString("cNumExteriorSol");
						if (!regGenerales.getString("cNumInteriorSol").equals(
								""))
							cTemp += " Int. "
									+ regGenerales.getString("cNumInteriorSol");
						rep.comDespliegaAlineado("E", iReng, "Domicilio:",
								true, rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								cTemp, false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "Correo-E:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng + 1,
								regGenerales.getString("cCorreoESol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("T", iReng, "W", iReng + 1,
								rep.getAT_HJUSTIFICA());
						iReng += 2;
						rep.comDespliegaAlineado("E", iReng, "Colonia:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng,
								regGenerales.getString("cColoniaSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cCodPostalSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						iReng++;
						rep.comDespliegaAlineado("F", iReng,
								"Ciudad/Municipio:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("G", iReng, "O", iReng,
								regGenerales.getString("cNomMunicipioSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("Q", iReng, "Entidad:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("R", iReng, "W", iReng,
								regGenerales.getString("cNomEntidadSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comBordeRededor("B", iRengTemp, "X", iReng, iBorde,
								1);

						// Datos del representante legal si es que existe
						cTemp = regGenerales.getString("iCveRepLegal");

						if (cTemp != null && cTemp != ""
								&& !cTemp.equals("null")
								&& Integer.parseInt(cTemp, 10) > 0) {// agregado
																		// no se
																		// validaba
																		// cTemp
							iReng++;
							rep.comAltoReng("A", iReng, fAltoRengIntermedio);
							iReng++;
							rep.comDespliegaCombinado(
									"DATOS DEL REPRESENTANTE LEGAL", "B",
									iReng, "X", iReng,
									rep.getAT_COMBINA_CENTRO(), "", true,
									iFondoCeldaPrin, true, true, iBorde, 1);
							iReng++;
							iRengTemp = iReng;
							rep.comDespliegaAlineado("E", iReng, "Nombre:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1,
									regGenerales.getString("cNomRepLegal"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "R.F.C.:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cRFCRep"), false,
									rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							rep.comDespliegaAlineado("S", iReng, "Teléfono:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cTelefonoRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							cTemp = regGenerales.getString("cCalleRep")
									+ " No. "
									+ regGenerales.getString("cNumExteriorRep");
							if (!regGenerales.getString("cNumInteriorRep")
									.equals(""))
								cTemp += " Int. "
										+ regGenerales
												.getString("cNumInteriorRep");
							rep.comDespliegaAlineado("E", iReng, "Domicilio:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1, cTemp, false,
									rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "Correo-E:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W",
									iReng + 1,
									regGenerales.getString("cCorreoERep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("T", iReng, "W", iReng + 1,
									rep.getAT_HJUSTIFICA());
							iReng += 2;
							rep.comDespliegaAlineado("E", iReng, "Colonia:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q", iReng,
									regGenerales.getString("cColoniaRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cCodPostalRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							iReng++;
							rep.comDespliegaAlineado("F", iReng,
									"Ciudad/Municipio:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("G", iReng, "O", iReng,
									regGenerales.getString("cNomMunicipioRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("Q", iReng, "Entidad:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("R", iReng, "W", iReng,
									regGenerales.getString("cNomEntidadRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comBordeRededor("B", iRengTemp, "X", iReng,
									iBorde, 1);
						}
					}

					if (!lOficinaResolDiferente) {
						cWhere = "WHERE TXO.iCveOficina = " + iOficinaReg
								+ "  AND TXO.iCveTramite = " + iTramite;
						cOrder = "ORDER BY TXO.iCveOficina, TXO.iCveTramite";
						vOficResuelve = tTramite.getDatosOficinaResolucion(
								cWhere, cOrder);
						if (vOficResuelve != null && vOficResuelve.size() > 0) {
							TVDinRep regOficResuelve = (TVDinRep) vOficResuelve
									.get(0);
							if (iOficinaReg != regOficResuelve
									.getInt("iCveOficinaResuelve")) {
								cOficResuelve = regOficResuelve
										.getString("cDscBreveOfic");
								lOficinaResolDiferente = true;
							}
							if (iDeptoReg != regOficResuelve
									.getInt("iCveDepartamento"))
								cDeptoResuelve = regOficResuelve
										.getString("cDscBreveDepto");
						}
					}

					// Datos del trámite
					iReng++;
					rep.comAltoReng("A", iReng, fAltoRengIntermedio);
					iReng++;
					if (regGenerales.getInt("iIdCita") == 0)
						rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B",
								iReng, "O", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
					else {
						rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B",
								iReng, "J", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						rep.comDespliegaCombinado("No. CIS:", "K", iReng, "L",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaPrin, true, true, iBorde, 1);
						rep.comDespliegaCombinado(
								regGenerales.getString("iIdCita"), "M", iReng,
								"O", iReng, rep.getAT_COMBINA_DERECHA(), "",
								false, iFondoEsp, true, true, iBorde, 2);
					}
					rep.comDespliegaCombinado("Ejercicio:", "P", iReng, "Q",
							iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);

					rep.comFontBold("R", iReng, "X", iReng);
					rep.comFontColor("R", iReng, "R", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iEjercicio"), "R", iReng,
							"R", iReng, rep.getAT_COMBINA_DERECHA(), "", false,
							iFondoEsp, true, true, iBorde, 2);
					rep.comDespliegaCombinado("Solicitud No.:", "S", iReng,
							"U", iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);
					rep.comFontColor("V", iReng, "X", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iNumSolicitud"), "V",
							iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(), "",
							false, iFondoEsp, true, true, iBorde, 2);
					iReng++;
					iRengTemp = iReng;
					rep.comDespliegaAlineado("E", iReng, "Clave:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "H", iReng,
							regGenerales.getString("cCveInterna"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iRengImprocedente = iReng;
					cRenglonesImprocedentes += iReng + ",";
					cSolicitudesImprocedentes += regGenerales
							.getString("iNumSolicitud") + ",";
					cTramiteImproc += regGenerales.getString("iCveTramite")
							+ ",";
					cModalidadImproc += regGenerales.getString("iCveModalidad")
							+ ",";
					iReng++;
					rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "P", iReng + 3,
							regGenerales.getString("cDscTramite"), false,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR());
					rep.comAlineaRango("F", iReng, "P", iReng + 3,
							rep.getAT_HJUSTIFICA());

					rep.comDespliegaAlineado("R", iReng, "Modalidad:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("S", iReng, "X", iReng,
							regGenerales.getString("cDscModalidad"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;

					rep.comDespliegaAlineado("T", iReng, "Fecha de Solicitud:",
							true, rep.getAT_HDERECHA(), "");
					java.sql.Timestamp tsTemp = regGenerales
							.getTimeStamp("tsRegistro");
					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(
									tFecha.getDateSQL(tsTemp), "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng += 1;
					rep.comDespliegaAlineado("T", iReng, "Hora de Solicitud:",
							true, rep.getAT_HDERECHA(), "");

					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getStringHoraHHMM_24(tsTemp, ":");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");

					iReng += 1;
					int iRengLimCubDocs = iReng;
					rep.comDespliegaAlineado("T", iReng,
							"Fecha Lím. Cubrir Doc.:", true,
							rep.getAT_HDERECHA(), "");
					dtTemp = regGenerales.getDate("dtLimiteEntregaDocs");
					cTemp = (dtTemp == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					rep.comDespliegaAlineado("T", iReng, "Fecha de Respuesta:",
							true, rep.getAT_HDERECHA(), "");

					cTemp = (dtTemp1 == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(dtTemp1, "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					if (!cTemp.equals(cFechaNula)) {
						rep.comDespliega("T", iRengLimCubDocs, "");
						rep.comDespliega("U", iRengLimCubDocs, "");
					}
					iReng++;
					rep.comDespliegaAlineado("F", iReng,
							"Oficina de Resolución:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("G", iReng, "W", iReng,
							cOficResuelve, false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					rep.comDespliegaAlineado("F", iReng, "Departamento:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("G", iReng, "W", iReng,
							cDeptoResuelve, false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;

					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 2;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 3;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					// Datos de Requisitos del Trámite
					cWhere = "WHERE RRT.iEjercicio = " + iEjercicio
							+ "  AND RRT.iNumSolicitud = " + iNumSolicitud
							+ "  AND RRT.iCveTramite = " + iTramite
							+ "  AND RRT.iCveModalidad = " + iModalidad
							+ "  AND DTEVALUACION IS NULL ";
					cOrder = "ORDER BY RRT.dtRecepcion DESC, RMT.lRequerido DESC, RMT.iOrden, R.iCveRequisito";
					int iRenglones;
					vRequisitos = tTramite.getDatosRequisitosSolicitud(cWhere,
							cOrder);
					if (vRequisitos != null && vRequisitos.size() > 0) { // Datos
																			// de
																			// Requisitos
						iReng++;
						rep.comAltoReng("A", iReng, fAltoRengIntermedio);
						iReng++;
						rep.comDespliegaCombinado("REQUISITOS", "B", iReng,
								"X", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						iReng++;
						rep.comDespliegaCombinado("Recibido", "B", iReng, "E",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Obl.", "F", iReng, "F",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Descripción", "G", iReng,
								"X", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);

						iRengTemp = iReng + 1;
						TVDinRep regRequisito;
						lImprocedente = false;
						boolean lUsuEncontrado = false;
						for (int i = 0; i < vRequisitos.size(); i++) {
							iReng++;
							regRequisito = (TVDinRep) vRequisitos.get(i);
							cTemp = regRequisito.getString("cDscRequisito");
							iRenglones = (int) (cTemp.length() / iLetrasXRenglonReq);
							if (cTemp.length() % iLetrasXRenglonReq > 0)
								iRenglones++;
							dtTemp = regRequisito.getDate("dtRecepcion");
							if (regRequisito.getDate("dtRecepcion") != null
									&& !lUsuEncontrado) {
								cNomUsuRegistra = regRequisito
										.getString("cNomUsuRecibe");
								lUsuEncontrado = true;
							}
							cTemp = (dtTemp == null) ? "NO ENTREGADO" : "'"
									+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
							rep.comDespliegaAlineado("B", iReng, "E", iReng
									+ iRenglones - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(),
									rep.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("F", iReng, "F", iReng
									+ iRenglones - 1, (regRequisito
									.getInt("lRequerido") == 1) ? "SI" : "NO",
									false, rep.getAT_COMBINA_CENTRO(), rep
											.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("G", iReng, "X", iReng
									+ iRenglones - 1,
									regRequisito.getString("cDscRequisito"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("H", iReng, "X", iReng
									+ iRenglones - 1, rep.getAT_HJUSTIFICA());
							rep.comBordeRededor("B", iReng, "E", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("F", iReng, "F", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("G", iReng, "G", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("H", iReng, "X", iReng
									+ iRenglones - 1, iBorde, 1);
							iReng += iRenglones - 1;
						}

						if (iReng >= (iUltimoSalto + iNumRengXPag)) {
							iUltimoSalto = iRengTemp - 2;
							cSaltosPag += (cSaltosPag.equals("")) ? ""
									+ iUltimoSalto : "," + iUltimoSalto;
						}
					}
				}
				iReng++;
				rep.comAltoReng("A", iReng, fAltoRengIntermedio);
				iReng++;
				rep.comDespliegaCombinado("RECIBE", "B", iReng, "L", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				rep.comDespliegaCombinado("SELLO", "R", iReng, "X", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				iReng++;
				iRengTemp = iReng;
				rep.comBordeRededor("B", iReng, "L", iReng + 2, iLineaFirma, 1);
				rep.comBordeRededor("B", iReng, "L", iReng + 5, iBorde, 1);
				rep.comBordeRededor("R", iReng, "X", iReng + 10, iBorde, 1);
				iReng += 3;
				rep.comDespliegaCombinado(cNomUsuRegistra, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				iReng++;
				rep.comDespliegaCombinado(cDscDeptoRecibe, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				iReng++;
				rep.comDespliegaCombinado(cDscOficinaRecibe, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				iReng += 4;
				int ilongEvalua = cLeyendaEval.length();
				int iRengEvalua = 0;
				while (ilongEvalua > 0) {
					ilongEvalua = ilongEvalua - iLongReng;
					iRengEvalua += 1;
				}
				rep.comDespliegaCombinado(cLeyendaEval, "B", iReng, "X", iReng
						+ iRengEvalua - 1, rep.getAT_COMBINA_IZQUIERDA(),
						rep.getAT_VSUPERIOR(), false, iFondoCeldaSec, false,
						false, iBorde, 1);
				rep.comAlineaRango("B", iReng, "X", iReng + iRengEvalua - 1,
						rep.getAT_HJUSTIFICA());
				iReng += iRengEvalua - 1;
				// FinLEL12102006

				if (iReng >= (iUltimoSalto + iNumRengXPag)) {
					iUltimoSalto = iRengTemp - 2;
					cSaltosPag += (cSaltosPag.equals("")) ? "" + iUltimoSalto
							: "," + iUltimoSalto;
				}

				if (!cSaltosPag.equals(""))
					rep.comSaltosPagina(cSaltosPag);
				sbRetorno = rep.getSbDatos();
			} catch (Exception e) {
				e.printStackTrace();
				cMensaje += e.getMessage();
			}
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	private Vector fRevisarGrupos(Vector vAnticipadosPorPagar) {
		TVDinRep regConcPag;
		Vector vGrupos = null;
		regConcPag = (TVDinRep) vAnticipadosPorPagar.get(0);
		String cSQL5 = "SELECT  distinct CXG.ICVEGRUPO as iGrupo "
				+ "FROM TRACONCEPTOXTRAMMOD CTM "
				+ "JOIN TRACONCEPTOXGRUPO CXG ON CXG.ICVECONCEPTO = CTM.ICVECONCEPTO "
				+ "WHERE ICVETRAMITE = " + regConcPag.getInt("iCveTram");
		try {
			vGrupos = this.findByCustom("", cSQL5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vGrupos;
	}

	private Vector fConceptosXGrupo(Vector vAnticipadosPorPagar) {
		TVDinRep regConcPag;
		Vector vConceptos = null;
		String cTramites = "";
		for (int inc = 0; inc < vAnticipadosPorPagar.size(); inc++) {
			regConcPag = (TVDinRep) vAnticipadosPorPagar.get(inc);
			cTramites = cTramites.equals("") ? ""
					+ regConcPag.getInt("iCveTram") : ","
					+ regConcPag.getInt("iCveTram");

			String cSQL6 = "SELECT CTM.ICVECONCEPTO as iConcepto, CXG.ICVEGRUPO as iGrupo "
					+ "FROM TRACONCEPTOXTRAMMOD CTM "
					+ "JOIN TRACONCEPTOXGRUPO CXG ON CXG.ICVECONCEPTO = CTM.ICVECONCEPTO "
					+ "WHERE ICVETRAMITE = "
					+ regConcPag.getInt("iCveTram")
					+ " iCveModalidad = " + regConcPag.getInt("iCveTram");
			try {
				vConceptos = this.findByCustom("", cSQL6);
				TVDinRep regConceptos;
				for (int i = 0; i < vConceptos.size(); i++) {
					regConceptos = (TVDinRep) vConceptos.get(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vConceptos;
	}

	private int fElminaConcXGrupo(Vector vAnticipadosPorPagar, Vector vGrupos,
			Vector vConceptos) {
		// Elimina conceptos
		int iGruposBorrados = 0;
		TVDinRep regAntPag;
		TVDinRep regConceptos;
		TVDinRep regMuestra;
		TVDinRep regGrupos;
		for (int i = 0; i < vAnticipadosPorPagar.size(); i++) {
			regAntPag = (TVDinRep) vAnticipadosPorPagar.get(i);
			regAntPag.getInt("iConcepto");
			int j = 0;
			while (j < vConceptos.size()) {
				regConceptos = (TVDinRep) vConceptos.get(j);
				if (regConceptos.getInt("iConcepto") == regAntPag
						.getInt("iConcepto")) {
					vConceptos.removeElementAt(j);
					j = 0;
					for (int z = 0; z < vConceptos.size(); z++) {
						regMuestra = (TVDinRep) vConceptos.get(z);
					}
				} else {
					j++;
				}
			}
		}
		if (vConceptos.size() == 0 || vConceptos.size() != vGrupos.size())
			return -1;
		int l;
		iGruposBorrados = 0;
		for (int k = 0; k < vGrupos.size(); k++) {
			regGrupos = (TVDinRep) vGrupos.get(k);
			l = 0;
			while (l < vConceptos.size()) {
				regConceptos = (TVDinRep) vConceptos.get(l);
				if (regGrupos.getInt("iGrupo") == regConceptos.getInt("iGrupo")) {
					vConceptos.removeElementAt(l);
					iGruposBorrados++;
					l = vConceptos.size();
				} else {
					l++;
				}
			}
		}
		for (int z = 0; z < vConceptos.size(); z++) {
			regMuestra = (TVDinRep) vConceptos.get(z);

			if (vGrupos.size() == iGruposBorrados && vConceptos.size() == 0) {
				return 0;
			} else {
				return -1;
			}
		}
		return vConceptos.size();
	}

	public StringBuffer acuseEntrega(String cSolicitud) throws Exception {

		Vector vGenerales = new Vector();
		cError = "";
		int iUltimoSalto = 8, iNumRengXPag = 71, iTemp;
		String cSaltosPag = "";
		int iRengIni = 10, iReng, iRengTemp, iBorde = 1, iLineaFirma = 2, iFondoCeldaPrin = 16;
		float fAltoRengIntermedio = 3.0f;
		String cFechaNula = "'- - - - - - - - - -", cTemp = "", cWhere, cOrder;
		StringBuffer sbRetorno = new StringBuffer("");
		TExcel rep = new TExcel();
		String cNumReporteAcuseEntrega = VParametros
				.getPropEspecifica("VentanillaReporteAcuseEntrega");
		String cNomSolicitante = "", cNomRepLegal = "", cNomAutorizaRecoger = "";

		this.inicializaDatos(cNumReporteAcuseEntrega);

		String[] aSolicitudes = cSolicitud.split("/");
		if (cMensaje.equals("")) {
			try {
				rep.iniciaReporte();

				// Procesa todas las solicitudes
				for (int j = 0; j < aSolicitudes.length; j++) {
					rep.comDespliega("A", 8, cNombreReporte.toString());
					iReng = iRengIni;
					String[] aSolicitud = aSolicitudes[j].split(",");
					if (aSolicitud.length > 4)
						cMensaje = "El número de parametros no coincide";
					int iEjercicio = Integer.parseInt(aSolicitud[0], 10);
					int iNumSolicitud = Integer.parseInt(aSolicitud[1], 10);
					rep.comCopiaHoja(1, rep.getAT_POSFINAL(), iEjercicio + "_"
							+ iNumSolicitud);
					rep.comEligeHoja(2 + j);

					cWhere = "WHERE RS.iEjercicio = " + iEjercicio
							+ " AND RS.iNumSolicitud = " + iNumSolicitud;
					cOrder = "ORDER BY RS.iEjercicio, RS.iNumSolicitud";
					vGenerales = tTramite.getDatosGeneralesSolicitud(cWhere,
							cOrder, true);
					// Datos Generales
					if (vGenerales != null && vGenerales.size() > 0) { // Datos
																		// Generales
						TVDinRep regGenerales = (TVDinRep) vGenerales.get(0);
						cNomRepLegal = regGenerales.getString("cNomRepLegal");
						if (cNomRepLegal == null
								|| cNomRepLegal.equalsIgnoreCase("null"))
							cNomRepLegal = "";
						cNomAutorizaRecoger = regGenerales
								.getString("cNomAutorizaRecoger");
						if (cNomAutorizaRecoger == null
								|| cNomAutorizaRecoger.equalsIgnoreCase("null"))
							cNomAutorizaRecoger = "";
						cNomSolicitante = regGenerales
								.getString("cNomSolicitante");
						if (cNomSolicitante == null
								|| cNomSolicitante.equalsIgnoreCase("null"))
							cNomSolicitante = "";
						java.sql.Date dtTemp;
						// Datos del solicitante
						rep.comDespliegaCombinado("DATOS DEL SOLICITANTE", "B",
								iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						iReng++;
						iRengTemp = iReng;
						rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								regGenerales.getString("cNomSolicitante"),
								false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "R.F.C.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cRFCSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("S", iReng, "Teléfono:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cTelefonoSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						cTemp = regGenerales.getString("cCalleSol") + " No. "
								+ regGenerales.getString("cNumExteriorSol");
						if (!regGenerales.getString("cNumInteriorSol").equals(
								""))
							cTemp += " Int. "
									+ regGenerales.getString("cNumInteriorSol");
						rep.comDespliegaAlineado("E", iReng, "Domicilio:",
								true, rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								cTemp, false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "Correo-E:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng + 1,
								regGenerales.getString("cCorreoESol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("T", iReng, "W", iReng + 1,
								rep.getAT_HJUSTIFICA());
						iReng += 2;
						rep.comDespliegaAlineado("E", iReng, "Colonia:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng,
								regGenerales.getString("cColoniaSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cCodPostalSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						iReng++;
						rep.comDespliegaAlineado("F", iReng,
								"Ciudad/Municipio:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("G", iReng, "O", iReng,
								regGenerales.getString("cNomMunicipioSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("Q", iReng, "Entidad:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("R", iReng, "W", iReng,
								regGenerales.getString("cNomEntidadSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comBordeRededor("B", iRengTemp, "X", iReng, iBorde,
								1);

						// Datos del representante legal si es que existe
						cTemp = regGenerales.getString("iCveRepLegal");
						if (Integer.parseInt(cTemp, 10) > 0) {
							iReng++;
							rep.comAltoReng("A", iReng, fAltoRengIntermedio);
							iReng++;
							rep.comDespliegaCombinado(
									"DATOS DEL REPRESENTANTE LEGAL", "B",
									iReng, "X", iReng,
									rep.getAT_COMBINA_CENTRO(), "", true,
									iFondoCeldaPrin, true, true, iBorde, 1);
							iReng++;
							iRengTemp = iReng;
							rep.comDespliegaAlineado("E", iReng, "Nombre:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1,
									regGenerales.getString("cNomRepLegal"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "R.F.C.:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cRFCRep"), false,
									rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							rep.comDespliegaAlineado("S", iReng, "Teléfono:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cTelefonoRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							cTemp = regGenerales.getString("cCalleRep")
									+ " No. "
									+ regGenerales.getString("cNumExteriorRep");
							if (!regGenerales.getString("cNumInteriorRep")
									.equals(""))
								cTemp += " Int. "
										+ regGenerales
												.getString("cNumInteriorRep");
							rep.comDespliegaAlineado("E", iReng, "Domicilio:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1, cTemp, false,
									rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "Correo-E:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W",
									iReng + 1,
									regGenerales.getString("cCorreoERep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("T", iReng, "W", iReng + 1,
									rep.getAT_HJUSTIFICA());
							iReng += 2;
							rep.comDespliegaAlineado("E", iReng, "Colonia:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q", iReng,
									regGenerales.getString("cColoniaRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cCodPostalRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							iReng++;
							rep.comDespliegaAlineado("F", iReng,
									"Ciudad/Municipio:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("G", iReng, "O", iReng,
									regGenerales.getString("cNomMunicipioRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("Q", iReng, "Entidad:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("R", iReng, "W", iReng,
									regGenerales.getString("cNomEntidadRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comBordeRededor("B", iRengTemp, "X", iReng,
									iBorde, 1);
						}

						// Datos del trámite
						iReng++;
						rep.comAltoReng("A", iReng, fAltoRengIntermedio);
						iReng++;
						rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B",
								iReng, "O", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						rep.comDespliegaCombinado("Ejercicio:", "P", iReng,
								"Q", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaPrin, true, true, iBorde, 1);
						rep.comDespliegaCombinado(
								regGenerales.getString("iEjercicio"), "R",
								iReng, "R", iReng, rep.getAT_COMBINA_DERECHA(),
								"", false, iFondoCeldaPrin, true, true, iBorde,
								1);
						rep.comDespliegaCombinado("Solicitud No.:", "S", iReng,
								"U", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaPrin, true, true, iBorde, 1);
						rep.comDespliegaCombinado(
								regGenerales.getString("iNumSolicitud"), "V",
								iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(),
								"", false, iFondoCeldaPrin, true, true, iBorde,
								1);
						iReng++;
						iRengTemp = iReng;
						rep.comDespliegaAlineado("E", iReng, "Clave:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "H", iReng,
								regGenerales.getString("cCveInterna"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "P", iReng + 3,
								regGenerales.getString("cDscTramite"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "P", iReng + 3,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("R", iReng, "Modalidad:",
								true, rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("S", iReng, "X", iReng,
								regGenerales.getString("cDscModalidad"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("T", iReng,
								"Fecha de Solicitud:", true,
								rep.getAT_HDERECHA(), "");
						java.sql.Timestamp tsTemp = regGenerales
								.getTimeStamp("tsRegistro");
						cTemp = (tsTemp == null) ? cFechaNula : "'"
								+ tFecha.getFechaDDMMMYYYY(
										tFecha.getDateSQL(tsTemp), "/");
						rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("T", iReng,
								"Fecha de Respuesta:", true,
								rep.getAT_HDERECHA(), "");
						dtTemp = regGenerales.getDate("dtEstimadaEntrega");

						cTemp = (dtTemp == null) ? cFechaNula : "'"
								+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
						rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						dtTemp = regGenerales.getDate("dtCancelacion");
						cTemp = (dtTemp == null) ? null : "'"
								+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
						if (cTemp != null && !cTemp.equalsIgnoreCase("null")) {
							rep.comDespliegaAlineado("T", iReng,
									"Fecha Cancelación:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("U", iReng, "W", iReng,
									cTemp, false,
									rep.getAT_COMBINA_IZQUIERDA(), "");
						}
						iReng++;
						rep.comDespliegaAlineado("T", iReng, "Resolución:",
								true, rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado(
								"U",
								iReng,
								"W",
								iReng,
								(regGenerales.getInt("lResolucionPositiva") == 1) ? "POSITIVA"
										: "NEGATIVA", false, rep
										.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						if (cNomAutorizaRecoger != null
								&& !cNomAutorizaRecoger.equals("")) {
							rep.comDespliegaCombinado("Autorizado a recoger:",
									"B", iReng, "E", iReng,
									rep.getAT_COMBINA_DERECHA(), "", true, -1,
									false, false, 0, 0);
							rep.comDespliegaCombinado(cNomAutorizaRecoger, "F",
									iReng, "P", iReng,
									rep.getAT_COMBINA_IZQUIERDA(), "", false,
									-1, false, false, 0, 0);
						}
						String cAnexos = regGenerales.getString("lAnexo");
						if (cAnexos != null
								&& !cAnexos.equalsIgnoreCase("null")) {
							cTemp = (cAnexos.equalsIgnoreCase("0")) ? "NO"
									: "SI";
							rep.comDespliegaAlineado("T", iReng, "Anexos:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("U", iReng, "W", iReng,
									cTemp, false,
									rep.getAT_COMBINA_IZQUIERDA(), "");
						}

						rep.comBordeRededor("B", iRengTemp, "X", iReng, iBorde,
								1);
						iReng++;
						if (iReng >= (iUltimoSalto + iNumRengXPag)) {
							iUltimoSalto = iRengTemp - 2;
							cSaltosPag += (cSaltosPag.equals("")) ? ""
									+ iUltimoSalto : "," + iUltimoSalto;
						}
					}

					// Datos para firma
					iReng++;
					rep.comAltoReng("A", iReng, fAltoRengIntermedio);
					iReng++;
					rep.comDespliegaCombinado(
							"RECIBE EL DÍA: "
									+ tFecha.getFechaDDMMMMMYYYY(
											tFecha.TodaySQL(), "/")
											.toUpperCase(), "B", iReng, "L",
							iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);
					iReng++;
					iRengTemp = iReng;
					rep.comBordeRededor("B", iReng, "L", iReng + 2,
							iLineaFirma, 1);
					rep.comBordeRededor("B", iReng, "L", iReng + 5, iBorde, 1);
					iReng += 3;

					if (cNomAutorizaRecoger != null
							&& !cNomAutorizaRecoger.equals("")
							&& !cNomAutorizaRecoger.equalsIgnoreCase("null"))
						rep.comDespliegaCombinado(cNomAutorizaRecoger, "B",
								iReng, "L", iReng, rep.getAT_COMBINA_CENTRO(),
								"", false, -1, false, false, 0, 0);
					else
						rep.comDespliegaCombinado(cNomRepLegal, "B", iReng,
								"L", iReng, rep.getAT_COMBINA_CENTRO(), "",
								false, -1, false, false, 0, 0);

					iReng++;
					rep.comDespliegaCombinado(cNomSolicitante, "B", iReng, "L",
							iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
							false, false, 0, 0);
					iReng++;
					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 2;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					// En caso de existir saltos de página los despliega.
					if (!cSaltosPag.equals(""))
						rep.comSaltosPagina(cSaltosPag);

					// En caso de estar configurado para impresión automática lo
					// imprime.
					if (lAutoImprimir && j < aSolicitudes.length - 1)
						rep.comImprimir(iNumeroCopias, iTiempoEsperaAcuses);
				}
				sbRetorno = rep.getSbDatos();
			} catch (Exception e) {
				e.printStackTrace();
				cMensaje += e.getMessage();
			}
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	/********************************** Reprte de Configuracion de trámites *****************/

	public StringBuffer confTramite(String cDatos) throws Exception {

		cError = "";
		String Firma = "";
		String Vigente = "";
		String Anticipado = "";
		String Obligatorio = "";
		String VigenteE = "";
		String Digitaliza = "";
		String Requerido = "";
		String CDigitaliza = "";
		String CRequerido = "";
		String CMandatorio = "";
		String DiasNatReq = "";
		String DiasCubrir = "";
		String DiasNat = "";
		String Tarifa = "";
		String Porcentaje = "";
		String ReqPago = "";

		int iReng = 0, iRengE = 0, iRengR = 0, iRengC = 0, iRengEt = 0, iRengM = 0, iRengOf = 0;

		StringBuffer sbRetorno = new StringBuffer("");
		String[] aParam = cDatos.split(",");

		try {

			// Obras de Protección
			rep.iniciaReporte();
			rep.comEligeHoja(1);

			// Query para sacar los datos a reemplazar de Trámite y Oficina
			String cSql = " Select distinct ct.iCveTramite,cCveInterna,ct.cDscTramite,ct.cDscBreve,lReqFirmaDigital,ct.lVigente,cBienBuscar, "
					+ " conf.iCveModalidad,tm.cdscmodalidad,dtinivigencia,inumdiasresol,ldiasnaturalesresol,inumdiascubrirreq, "
					+ " ldiasnaturalesreq,lrequierepago,ccvecofemer "
					+ " from TRACatTramite ct "
					+ " join Traconfiguratramite conf on  ct.iCveTramite=conf.iCveTramite "
					+ " join tramodalidad tm on conf.iCveModalidad=tm.iCveModalidad "
					+ " left join tracofemer cof on conf.icvemodalidad=cof.icvemodalidad and conf.icvetramite=cof.icvetramite "
					+ " where ct.iCveTramite ="
					+ aParam[0]
					+ " and conf.iCveModalidad = " + aParam[1];

			String cSqlOfic = " Select distinct ct.iCveTramite,ct.cDscTramite, "
					+ " iCveOficinaResuelve,iCveDeptoResuelve,GO.cDscBreve as oficina1, "
					+ " GO2.cDscBreve as oficina2,ct.cDscBreve as tra1,GRLDepartamento.cDscBreve "
					+ " from TRACatTramite ct "
					+ " join Traconfiguratramite conf on  ct.iCveTramite=conf.iCveTramite "
					+ " join tratramitexofic TT on ct.icvetramite=tt.icvetramite "
					+ " left join GRLOficina GO on TT.iCveOficina = GO.iCveOficina  "
					+ " left join GRLOficina GO2 on TT.iCveOficinaResuelve = GO2.iCveOficina  "
					+ " left join GRLDepartamento on TT.iCveDeptoResuelve = GRLDepartamento.iCveDepartamento "
					+ " where ct.iCveTramite =" + aParam[0];

			// Query para sacar los datos a reemplazar de Conceptos de Pago
			String cSqlPago = "Select TRAConceptoXTramMod.iEjercicio,TRAConceptoXTramMod.iCveTramite,TRAConceptoXTramMod.iCveModalidad,lPagoAnticipado,TRAConceptoXTramMod.iCveConcepto,cDscConcepto,iCategoriaIngresos,iConceptoIngresos,iRefNumericaIngresos,lEsTarifa,lEsPorcentaje,dImporteSinAjuste,dImporteAjustado "
					+ " from TRAConceptoXTramMod  "
					+ " join TRAConceptoPago on TRAConceptoXTramMod.iCveConcepto=TRAConceptoPago.iCveConcepto "
					+ " join TRAREFERENCIACONCEPTO ON TRACONCEPTOXTRAMMOD.IEJERCICIO=TRAREFERENCIACONCEPTO.IEJERCICIO "
					+ " and TRACONCEPTOXTRAMMOD.iCveConcepto=TRAREFERENCIACONCEPTO.iCveConcepto  "
					+ " where TRAConceptoXTramMod.iEjercicio= "
					+ aParam[4]
					+ " and TRAConceptoXTramMod.iCveTramite= "
					+ aParam[0]
					+ " and TRAConceptoXTramMod.iCveModalidad= " + aParam[1];
			// Query para sacar los datos a reemplazar de Etapas
			String cSqlEtapas = "Select icvetramite,icvemodalidad,traetapaxmodtram.icveetapa,lobligatorio,traetapaxmodtram.lvigente,cdscetapa "
					+ " from traetapaxmodtram "
					+ " join traetapa on traetapaxmodtram.icveetapa=traetapa.icveetapa "
					+ " where iCveTramite= "
					+ aParam[0]
					+ " and iCveModalidad = " + aParam[1] + " Order By iOrden";

			// Query para sacar los datos a reemplazar de Dependecnias
			String cSqlD = "Select D.iCveTramite,D.iCveModalidad,D.iCveTramiteHijo,D.iCveModalidadHijo, "
					+ " T.cDscBreve As cTram, M.cDscModalidad AS cMod, TH.cDscBreve AS cTH, MH.cDscModalidad AS cMH "
					+ " from TRADependencia D "
					+ " Join TraCatTramite T ON T.iCveTramite = D.iCveTramite "
					+ " Join TRAModalidad M ON M.iCveModalidad = D.iCveModalidad "
					+ " Join TraCatTramite TH ON TH.iCveTramite = D.iCveTramiteHijo "
					+ " Join TRAModalidad MH ON MH.iCveModalidad = D.iCveModalidadHijo "
					+ " where D.iCveTramite= "
					+ aParam[0]
					+ " and D.iCveModalidad = " + aParam[1];

			// Query para sacar los datos a reemplazar de Requermientos
			String cSqlReq = "Select iCveTramite,iCveModalidad,r.iCveRequisito,iOrden,lRequerido,cDscRequisito,cDscBreve,cFundLegal,lDigitaliza "
					+ " from  TraReqXModTramite rxm  "
					+ " join TRARequisito r  on rxm.iCveRequisito=r.iCveRequisito "
					+ " where iCveTramite= "
					+ aParam[0]
					+ " and iCveModalidad = " + aParam[1];

			Vector vcData = findByCustom("", cSql);
			Vector vcData2 = findByCustom("", cSqlPago);
			Vector vcData3 = findByCustom("", cSqlEtapas);
			Vector vcData4 = findByCustom("", cSqlReq);
			Vector vcDataOf = findByCustom("", cSqlOfic);
			Vector vcDataDep = findByCustom("", cSqlD);

			// Obtiene los Datos de Trámite y Oficina

			TVDinRep vCatastroOP = (TVDinRep) vcData.get(0); // Obtiene los
																// datos de
																// Tramite

			Firma = Logico(vCatastroOP.getInt("lReqFirmaDigital")); // Obtien
																	// SI=1 y
																	// NO=0
			Vigente = Logico(vCatastroOP.getInt("lVigente"));

			rep.comDespliegaAlineado("B", 10,
					vCatastroOP.getString("cCveInterna"), false,
					rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO());
			rep.comDespliegaAlineado("D", 10,
					vCatastroOP.getString("cDscBreve"), false,
					rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO());
			rep.comDespliegaAlineado("A", 12,
					vCatastroOP.getString("cDscTramite"), false,
					rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("B", 17, Firma, false,
					rep.getAT_HCENTRO(), rep.getAT_VCENTRO());
			rep.comDespliegaAlineado("D", 17, Vigente, false,
					rep.getAT_HCENTRO(), rep.getAT_VCENTRO());
			rep.comDespliegaAlineado("F", 17,
					vCatastroOP.getString("cBienBuscar"), false,
					rep.getAT_HIZQUIERDA(), rep.getAT_VCENTRO());

			/************* modalidades ***********/
			iRengR = 20;
			// Encabezados 1Modalidades
			iRengM = iRengR;
			rep.comBordeTotal("A", iRengR, "F", iRengR, 1);
			// rep.comFontColor("A",iRengR,"F",iRengR,1);
			rep.comDespliegaCombinado("Modalidad", "A", iRengR, "D", iRengR,
					rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR(), true, 0,
					false, false, 1, 5);
			rep.comDespliegaAlineado("E", iRengR, "Fecha Vigencia", true,
					rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("F", iRengR, "Requiere Pago", true,
					rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());

			iRengR++;

			for (int i = 0; i < vcData.size(); i++) {
				TVDinRep vModal = (TVDinRep) vcData.get(i);

				ReqPago = Logico(vModal.getInt("lrequierepago"));
				// Datos 1 Modalidades
				rep.comFontColor("A", iRengR, "F", iRengR, 1);
				rep.comDespliegaCombinado(vModal.getString("cdscmodalidad"),
						"A", iRengR, "D", iRengR,
						rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR(),
						false, 0, false, false, 1, 5);
				rep.comDespliegaAlineado("E", iRengR,
						vModal.getString("dtinivigencia"), false,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
				rep.comDespliegaAlineado("F", iRengR, ReqPago, false,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
				iRengR++;
			} // iRengR++;
				// Encabezados 2 Modalidades
			rep.comDespliegaCombinado("Días Resolución", "A", iRengR, "A",
					iRengR, rep.getAT_COMBINA_CENTRO(), rep.getAT_HCENTRO(),
					true, 0, false, false, 1, 5);
			rep.comDespliegaAlineado("B", iRengR,
					"Días Naturales de Resolución", true, rep.getAT_HCENTRO(),
					rep.getAT_VCENTRO());
			rep.comDespliegaAlineado("C", iRengR, "Días Cubrir Requisitos",
					true, rep.getAT_HCENTRO(), rep.getAT_VCENTRO());
			rep.comDespliegaAlineado("D", iRengR,
					"Días Naturales Cubrir Requisitos", true,
					rep.getAT_HCENTRO(), rep.getAT_VCENTRO());
			rep.comDespliegaCombinado("COFEMER", "E", iRengR, "F", iRengR,
					rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO(), true, 0,
					false, false, 1, 5);

			iRengR++;
			for (int i = 0; i < vcData.size(); i++) {
				TVDinRep vModal = (TVDinRep) vcData.get(i);
				DiasNatReq = Logico(vModal.getInt("ldiasnaturalesreq"));
				DiasNat = Logico(vModal.getInt("ldiasnaturalesresol"));
				// Datos 2 Modalidades
				rep.comDespliegaAlineado("A", iRengR,
						vModal.getString("inumdiasresol"), false,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
				rep.comDespliegaAlineado("B", iRengR, DiasNat, false,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
				rep.comDespliegaAlineado("C", iRengR,
						vModal.getString("inumdiascubrirreq"), false,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
				rep.comDespliegaAlineado("D", iRengR, DiasNatReq, false,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
				if (vModal.getString("ccvecofemer").equals("")
						|| vModal.getString("ccvecofemer").equals("null"))
					rep.comDespliegaCombinado("", "E", iRengR, "F", iRengR,
							rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR(),
							true, 0, false, false, 1, 5);
				else
					rep.comDespliegaCombinado(vModal.getString("ccvecofemer"),
							"E", iRengR, "F", iRengR,
							rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR(),
							true, 0, false, false, 1, 5);

				iRengR++;
			}
			rep.comBordeTotal("A", iRengM, "F", iRengR - 1, 1);

			/************ oficinas ****************/
			// Encabezados
			iRengR++;
			if (vcDataOf.size() > 0) {
				iRengOf = iRengR;
				rep.comBordeTotal("A", iRengR, "F", iRengR, 1);
				rep.comDespliegaCombinado("Oficinas", "A", iRengR, "F", iRengR,
						rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR(),
						true, 15, false, false, 1, 5);
				iRengR++;
				rep.comDespliegaCombinado("Oficina", "A", iRengR, "B", iRengR,
						rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR(),
						true, 0, false, false, 1, 5);
				rep.comDespliegaCombinado("Oficina que Resuelve", "C", iRengR,
						"D", iRengR, rep.getAT_COMBINA_CENTRO(),
						rep.getAT_VSUPERIOR(), true, 0, false, false, 1, 5);
				rep.comDespliegaCombinado("Departamento que Resuelve", "E",
						iRengR, "F", iRengR, rep.getAT_COMBINA_CENTRO(),
						rep.getAT_VSUPERIOR(), true, 0, false, false, 1, 5);
				iRengR++;
				for (int i = 0; i < vcDataOf.size(); i++) {
					TVDinRep vOfic = (TVDinRep) vcDataOf.get(i);
					// iRengR++;
					// Datos Oficinas

					rep.comDespliegaCombinado(vOfic.getString("oficina1"), "A",
							iRengR, "B", iRengR + 1,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VAJUSTAR(), false, 0, false, false, 1, 5);
					if (vOfic.getString("oficina2").equals("")
							|| vOfic.getString("oficina2").equals("null"))
						rep.comDespliegaCombinado("", "C", iRengR, "D",
								iRengR + 1, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VAJUSTAR(), false, 0, false, false,
								1, 5);
					else
						rep.comDespliegaCombinado(vOfic.getString("oficina2"),
								"C", iRengR, "D", iRengR + 1,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VAJUSTAR(), false, 0, false, false,
								1, 5);
					if (vOfic.getString("cDscBreve").equals("")
							|| vOfic.getString("cDscBreve").equals("null"))
						rep.comDespliegaCombinado("", "E", iRengR, "F",
								iRengR + 1, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VAJUSTAR(), false, 0, false, false,
								1, 5);
					else
						rep.comDespliegaCombinado(vOfic.getString("cDscBreve"),
								"E", iRengR, "F", iRengR + 1,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VAJUSTAR(), false, 0, false, false,
								1, 5);
					iRengR += 2;

				}
				rep.comBordeTotal("A", iRengOf, "F", iRengR - 1, 1);

			}

			/***** Obtiene datos de Conceptos de Pago ****/
			// Encabezados
			iRengR++;

			if (vcData2.size() > 0) {
				// iRengC=iRengR;
				rep.comBordeTotal("A", iRengR, "F", iRengR, 1);
				// rep.comFontColor("A",iRengR,"F",iRengR,1);
				rep.comDespliegaCombinado("Conceptos", "A", iRengR, "F",
						iRengR, rep.getAT_COMBINA_IZQUIERDA(),
						rep.getAT_VSUPERIOR(), true, 15, false, false, 1, 5);
				/*
				 * iRengR++;
				 * rep.comDespliegaCombinado("Concepto","A",iRengR,"D",
				 * iRengR,rep.getAT_COMBINA_CENTRO(),
				 * rep.getAT_VCENTRO(),true,0,false,false,1,5);
				 * rep.comDespliegaAlineado
				 * ("E",iRengR,"Categoría Ingresos",true,
				 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR());
				 * rep.comDespliegaAlineado("F",iRengR,"Concepto Ingresos",true,
				 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR());
				 */
				iRengR++;
				for (int i = 0; i < vcData2.size(); i++) {
					iRengC = iRengR;
					TVDinRep vConc = (TVDinRep) vcData2.get(i);
					// iRengR++;
					rep.comDespliegaCombinado("Concepto", "A", iRengR, "D",
							iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO(), true, 0, false, false, 1, 5);
					rep.comDespliegaAlineado("E", iRengR, "Categoría Ingresos",
							true, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("F", iRengR, "Concepto Ingresos",
							true, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR());

					iRengR++;

					int longitud = vConc.getString("cDscConcepto").length();
					int rengs = 0;

					if (longitud >= 0 && longitud <= 100)
						rengs = 1;

					else if (longitud >= 100 && longitud < 150)
						rengs = 2;

					else if (longitud >= 150 && longitud < 200)
						rengs = 3;
					else if (longitud >= 200 && longitud < 230)
						rengs = 4;
					else if (longitud >= 230 && longitud < 350)
						rengs = 5;

					else
						rengs = 6;
					rep.comDespliegaCombinado(vConc.getString("cDscConcepto"),
							"A", iRengR, "D", iRengR + rengs,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VJUSTIFICAR(), false, 0, false, false, 1,
							5);

					rep.comDespliegaCombinado(
							vConc.getString("iCategoriaIngresos"), "E", iRengR,
							"E", iRengR + rengs, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO(), false, 0, false, false, 1, 5);

					rep.comDespliegaCombinado(
							vConc.getString("iConceptoIngresos"), "F", iRengR,
							"F", iRengR + rengs, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO(), false, 0, false, false, 1, 5);

					// Datos 1 Conceptos
					if (longitud >= 0 && longitud <= 150)
						iRengR += (rengs + 1);
					else if (longitud >= 150 && longitud <= 200)
						iRengR += (rengs + 1); // 3;
					else if (longitud >= 200 && longitud <= 230)
						iRengR += (rengs + 1); // 4;
					else if (longitud >= 230 && longitud <= 350)
						iRengR += (rengs + 1); // 5;
					else
						iRengR += (rengs + 1); // 7;
					rep.comDespliegaAlineado("A", iRengR,
							"Ref. Numérica Ingresos", true,
							rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("B", iRengR, "Es Tarifa", true,
							rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO());
					rep.comDespliegaAlineado("C", iRengR, "Es Factor", true,
							rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO());
					rep.comDespliegaAlineado("D", iRengR, "Importe Sin Ajuste",
							true, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO());
					rep.comDespliegaAlineado("E", iRengR, "Importe Ajustado",
							true, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO());
					rep.comDespliegaAlineado("F", iRengR, "Pago Anticipado",
							true, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO());
					iRengR++;
					// Datos Conceptos PAgo 2

					// for(int k = 0;k < vcData2.size();k++){
					TVDinRep vConcPago = (TVDinRep) vcData2.get(i);
					Anticipado = Logico(vConcPago.getInt("lPagoAnticipado"));
					Tarifa = Logico(vConcPago.getInt("lEsTarifa"));
					Porcentaje = Logico(vConcPago.getInt("lEsPorcentaje"));

					rep.comDespliegaAlineado("A", iRengR,
							vConcPago.getString("iRefNumericaIngresos"), false,
							rep.getAT_HIZQUIERDA(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("B", iRengR, Tarifa, false,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("C", iRengR, Porcentaje, false,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("D", iRengR,
							vConcPago.getString("dImporteSinAjuste"), false,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("E", iRengR,
							vConcPago.getString("dImporteAjustado"), false,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("F", iRengR, Anticipado, false,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

					iRengR += 2;
					// }
					rep.comBordeTotal("A", iRengC, "F", iRengR - 2, 1);

				} // iRengR++;
					// rep.comBordeTotal("A",iRengC,"F",iRengR-1,1);

				// Encabezado 2 Conc PAgo
				/*
				 * rep.comDespliegaAlineado("A",iRengR,"Ref. Numérica Ingresos",true
				 * , rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR());
				 * rep.comDespliegaAlineado("B",iRengR,"Es Tarifa",true,
				 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
				 * rep.comDespliegaAlineado("C",iRengR,"Es Factor",true,
				 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
				 * rep.comDespliegaAlineado
				 * ("D",iRengR,"Importe Sin Ajuste",true,
				 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
				 * rep.comDespliegaAlineado("E",iRengR,"Importe Ajustado",true,
				 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
				 * rep.comDespliegaAlineado("F",iRengR,"Pago Anticipado",true,
				 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO()); iRengR++;
				 * //Datos Conceptos PAgo 2
				 * 
				 * for(int k = 0;k < vcData2.size();k++){ TVDinRep vConcPago =
				 * (TVDinRep) vcData2.get(k); Anticipado =
				 * Logico(vConcPago.getInt("lPagoAnticipado")); Tarifa =
				 * Logico(vConcPago.getInt("lEsTarifa")); Porcentaje =
				 * Logico(vConcPago.getInt("lEsPorcentaje"));
				 * 
				 * rep.comDespliegaAlineado("A",iRengR,vConcPago.getString(
				 * "iRefNumericaIngresos"),false,
				 * rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				 * rep.comDespliegaAlineado("B",iRengR,Tarifa,false,
				 * rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
				 * rep.comDespliegaAlineado("C",iRengR,Porcentaje,false,
				 * rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
				 * rep.comDespliegaAlineado
				 * ("D",iRengR,vConcPago.getString("dImporteSinAjuste"),false,
				 * rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
				 * rep.comDespliegaAlineado
				 * ("E",iRengR,vConcPago.getString("dImporteAjustado"),false,
				 * rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
				 * rep.comDespliegaAlineado("F",iRengR,Anticipado,false,
				 * rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
				 * 
				 * iRengR++; }rep.comBordeTotal("A",iRengC,"F",iRengR-1,1);
				 */
			}

			/******* Obtiene datos de Etapas *******/
			// Encabezados
			// iRengR++;
			if (vcData3.size() > 0) {
				rep.comFontColor("A", iRengR, "C", iRengR, 1);
				rep.comBordeTotal("A", iRengR, "F", iRengR, 1);

				rep.comDespliegaCombinado("Etapas", "A", iRengR, "F", iRengR,
						rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR(),
						true, 15, false, false, 1, 5);
				iRengR++;
				rep.comBordeTotal("A", iRengR, "F", iRengR, 1);
				rep.comDespliegaCombinado("Etapa", "A", iRengR, "D", iRengR,
						rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR(),
						true, 0, false, false, 1, 5);
				rep.comDespliegaAlineado("E", iRengR, "Obligatorio", true,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
				rep.comDespliegaAlineado("F", iRengR, "Vigente", true,
						rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

				iRengR++;
				rep.comBordeTotal("A", iRengR, "F", iRengR, 1);
				// Datos Etapas
				iRengEt = iRengR;
				for (int l = 0; l < vcData3.size(); l++) {
					TVDinRep vEtapaDep = (TVDinRep) vcData3.get(l);
					Obligatorio = Logico(vEtapaDep.getInt("lobligatorio"));
					VigenteE = Logico(vEtapaDep.getInt("lvigente"));
					rep.comDespliegaCombinado(vEtapaDep.getString("cdscetapa"),
							"A", iRengR, "D", iRengR,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR(), false, 0, false, false, 1, 5);
					rep.comDespliegaAlineado("E", iRengR, Obligatorio, false,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("F", iRengR, VigenteE, false,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					iRengR++;

				}
				rep.comBordeTotal("A", iRengEt, "F", iRengR - 1, 1);
			}
			iRengR++;
			/************ Dependencias *********/

			if (vcDataDep.size() > 0) {
				// rep.comFontColor("A",iRengR,"C",iRengR,1);
				rep.comBordeTotal("A", iRengR, "F", iRengR, 1);

				rep.comDespliegaCombinado("Dependencias", "A", iRengR, "F",
						iRengR, rep.getAT_COMBINA_IZQUIERDA(),
						rep.getAT_VSUPERIOR(), true, 15, false, false, 1, 5);
				iRengR++;

				// rep.comBordeTotal("A",iRengR,"F",iRengR,1);

				iRengR++;
				int irengD = iRengR;
				// rep.comBordeTotal("A",iRengR,"F",iRengR,1);
				for (int k = 0; k < vcDataDep.size(); k++) {
					TVDinRep vDep = (TVDinRep) vcDataDep.get(k);
					rep.comDespliegaCombinado("Trámite", "A", iRengR, "C",
							iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR(), true, 0, false, false, 1, 5);
					rep.comDespliegaCombinado("Modalidad", "D", iRengR, "F",
							iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR(), true, 0, false, false, 1, 5);

					iRengR++;
					rep.comDespliegaCombinado(vDep.getString("cTram"), "A",
							iRengR, "C", iRengR, rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR(), false, 0, false, false, 1, 5);
					rep.comDespliegaCombinado(vDep.getString("cMod"), "D",
							iRengR, "F", iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR(), false, 0, false, false, 1, 5);
					iRengR++;
					rep.comDespliegaCombinado("Trámite Dependiente", "A",
							iRengR, "C", iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR(), true, 0, false, false, 1, 5);
					rep.comDespliegaCombinado("Modalidad Dependiente", "D",
							iRengR, "F", iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR(), true, 0, false, false, 1, 5);
					iRengR++;
					rep.comDespliegaCombinado(vDep.getString("cTH"), "A",
							iRengR, "C", iRengR, rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR(), false, 0, false, false, 1, 5);
					rep.comDespliegaCombinado(vDep.getString("cMH"), "D",
							iRengR, "F", iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR(), false, 0, false, false, 1, 5);
					iRengR++;

				}
				rep.comBordeTotal("A", irengD, "F", iRengR - 1, 1);

			}
			iRengR++;

			/******** Obtiene lso dartos de Requisitos *************/
			if (vcData4.size() > 0) {

				// iRengR=13;
				// rep.comEligeHoja(2);
				for (int i = 0; i < vcData4.size(); i++) {
					TVDinRep vConcReq = (TVDinRep) vcData4.get(i);

					int longitud2 = vConcReq.getString("cDscRequisito")
							.length();
					int reng2 = 0;

					if (longitud2 >= 0 && longitud2 <= 100)
						reng2 = 1;

					else if (longitud2 >= 100 && longitud2 < 150)
						reng2 = 2;
					else if (longitud2 >= 150 && longitud2 < 250)
						reng2 = 3;
					else
						reng2 = 5;

					Digitaliza = Logico(vConcReq.getInt("lRequerido"));
					Requerido = Logico(vConcReq.getInt("lDigitaliza"));
					// Encabezados
					iReng = iRengR;

					// rep.comFontColor("A",iRengR,"E",iRengR,1);

					rep.comDespliegaCombinado("Requisito", "A", iRengR, "F",
							iRengR, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VSUPERIOR(), true, 15, false, false, 1, 5);
					iRengR++;
					/*
					 * rep.comDespliegaCombinado("Descripción Breve Requsito","A"
					 * ,iRengR,"C",iRengR,rep.getAT_HCENTRO_SELECCION(),
					 * rep.getAT_VSUPERIOR(),true,0,false,false,1,5);
					 */
					rep.comDespliegaCombinado("Descripción Completa", "A",
							iRengR, "D", iRengR, rep.getAT_HCENTRO_SELECCION(),
							rep.getAT_VSUPERIOR(), true, 0, false, false, 1, 5);

					rep.comDespliegaAlineado("E", iRengR, "Digitaliza", true,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("F", iRengR, "Requerido", true,
							rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

					iRengR++;

					// Datos Requisitos

					/*
					 * rep.comDespliegaCombinado(vConcReq.getString("cDscBreve"),
					 * "A",iRengR,"C",iRengR,rep.getAT_COMBINA_IZQUIERDA(),
					 * rep.getAT_VSUPERIOR(),false,0,false,false,1,5);
					 */
					rep.comDespliegaCombinado(
							vConcReq.getString("cDscRequisito"), "A", iRengR,
							"D", iRengR + reng2, rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR(), false, 0, false, false, 1, 5);
					// iRengR+=3;
					rep.comDespliegaCombinado(Requerido, "E", iRengR, "E",
							iRengR + reng2, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO(), false, 0, false, false, 1, 5);
					rep.comDespliegaCombinado(Digitaliza, "F", iRengR, "F",
							iRengR + reng2, rep.getAT_COMBINA_CENTRO(),
							rep.getAT_VCENTRO(), false, 0, false, false, 1, 5);
					/*
					 * rep.comDespliegaAlineado("D",iRengR,Requerido,false,
					 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR());
					 * rep.comDespliegaAlineado("E",iRengR,Digitaliza,false,
					 * rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR()); /*
					 * iRengR++;
					 * rep.comDespliegaCombinado("Descripción Completa"
					 * ,"A",iRengR,"E",iRengR,rep.getAT_HCENTRO_SELECCION(),
					 * rep.getAT_VSUPERIOR(),true,0,false,false,1,5);
					 * 
					 * iRengR++;
					 * rep.comDespliegaCombinado(vConcReq.getString("cDscRequisito"
					 * ),"A",iRengR,"E",iRengR+2,rep.getAT_COMBINA_IZQUIERDA(),
					 * rep.getAT_VAJUSTAR(),false,0,false,false,1,5); iRengR+=3;
					 */
					iRengR += (reng2 + 1);
					if (!vConcReq.getString("cFundLegal").equals("")) {

						rep.comDespliegaCombinado("Fundamento Legal", "A",
								iRengR, "F", iRengR,
								rep.getAT_HCENTRO_SELECCION(),
								rep.getAT_VSUPERIOR(), true, 0, false, false,
								1, 5);
						iRengR++;
						rep.comDespliegaCombinado(
								vConcReq.getString("cFundLegal"), "A", iRengR,
								"F", iRengR + 1, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR(), false, 0, false, false,
								1, 5);
						// iRengR+=3;
						iRengR += 2;
					}

					/******* Obtiene datos de Carcaterísrticas ***********/
					String cSqlCarac = "Select TRACaracXRequisito.iCveRequisito,iCveCaracteristica,cDscCaracteristica,TRACaracXRequisito.cDscBreve,TRACaracXRequisito.lVigente,lEnRecepcion,lEnProceso,lMandatorio,TRARequisito.cDscBreve As Requisito "
							+ " from TRACaracXRequisito "
							+ " Join TRARequisito ON TRARequisito.iCveRequisito = TRACaracXRequisito.iCveRequisito "
							+ " where TRARequisito.iCveRequisito= "
							+ vConcReq.getInt("iCveRequisito");

					iRengR++;

					Vector vcData5 = findByCustom("", cSqlCarac);
					if (vcData5.size() > 0) {
						// Encabezados
						// rep.comFontColor("A",iRengR,"E",iRengR-1,1);
						// rep.comBordeTotal("A",iRengR,"E",iRengR,1);

						rep.comDespliegaCombinado("Características", "A",
								iRengR, "F", iRengR,
								rep.getAT_HCENTRO_SELECCION(),
								rep.getAT_VSUPERIOR(), true, 15, false, false,
								1, 1);
						iRengR++;
						rep.comDespliegaCombinado("Descripción Breve", "A",
								iRengR, "C", iRengR,
								rep.getAT_HCENTRO_SELECCION(),
								rep.getAT_VSUPERIOR(), true, 0, false, false,
								1, 5);
						rep.comDespliegaAlineado("D", iRengR, "En Recepcion",
								true, rep.getAT_HCENTRO(),
								rep.getAT_VSUPERIOR());
						rep.comDespliegaAlineado("E", iRengR, "En Proceso",
								true, rep.getAT_HCENTRO(),
								rep.getAT_VSUPERIOR());
						rep.comDespliegaAlineado("F", iRengR, "Mandatorio",
								true, rep.getAT_HCENTRO(),
								rep.getAT_VSUPERIOR());

						iRengR++;

						// Datos Características
						int iRengCa = iRengR;
						int carac = 0;
						carac = vcData5.size();
						for (int j = 0; j < vcData5.size(); j++) {
							TVDinRep vCarac = (TVDinRep) vcData5.get(j);

							CDigitaliza = Logico(vCarac.getInt("lEnRecepcion"));
							CRequerido = Logico(vCarac.getInt("lEnProceso"));
							CMandatorio = Logico(vCarac.getInt("lMandatorio"));

							rep.comDespliegaCombinado(
									(j + 1) + ".- "
											+ vCarac.getString("cDscBreve"),
									"A", iRengR, "C", iRengR,
									rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR(), false, 0, false,
									true, 1, 5);
							rep.comDespliegaAlineado("D", iRengR, CDigitaliza,
									false, rep.getAT_HCENTRO(),
									rep.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("E", iRengR, CRequerido,
									false, rep.getAT_HCENTRO(),
									rep.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("F", iRengR, CMandatorio,
									false, rep.getAT_HCENTRO(),
									rep.getAT_VSUPERIOR());

							iRengR++;
							/*
							 * rep.comDespliegaCombinado(
							 * "Descripción Características"
							 * ,"A",iRengR,"E",iRengR
							 * ,rep.getAT_COMBINA_IZQUIERDA(),
							 * rep.getAT_VSUPERIOR(),true,0,false,false,1,1);
							 * iRengR++;
							 */
							rep.comDespliegaCombinado(
									vCarac.getString("cDscCaracteristica"),
									"A", iRengR, "F", iRengR + 1,
									rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VAJUSTAR(), false, 0, false,
									false, 1, 5);
							iRengR += 2;
						}

						rep.comBordeTotal("A", iRengCa - 2, "F", iRengR - 1, 1);
						iRengR++;
						carac = (carac * 3) + 5;

						rep.comBordeTotal("A", iReng, "F", iRengR - carac, 1);

					} else {

						iRengE = iRengR;
						rep.comBordeTotal("A", iReng, "F", iRengE - 2, 1);

					}

				}
			}

			sbRetorno = rep.getSbDatos();

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	public Vector GenNotificacion(String cFiltro, String cFolio,
			String cCveOfic, String cCveDepto) throws Exception {
		int iCveOficina, iCveDepto, iEjercicioSolicitud, iNumSolicitud, iCveReglaOperacion;
		int iCveSolicitante, iCveRepLegal, iCveDomicilio;
		int iEjercicioPNC = 0;
		int iConsecutivoPNC = 0;
		int iContReq = 1;
		int iDiasDespNotificado = 0;
		String cNumOficioSol, cdtOficioSol, cDscBien, cRequisito = "";
		String cCalle, cNumExt, cNumInt, cColonia, cCodPostal, cPais, cEntidad, cMunicipio = "";
		String cTramite, cSolicitante = "";
		String cModalidad = "", cOfic = "", cDpto = "";// nuevos ADV
		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		String cCausa = "";
		String cRegistro = "";
		java.sql.Date dtOficioSol, dtToday;
		int iCvePMDP;
		java.sql.Timestamp tsRegistro;
		TWord rep1 = new TWord();
		TWord rep2 = new TWord();
		TDObtenDatos dObtenDatos = new TDObtenDatos();
		TVDinRep vData = new TVDinRep();
		Vector vctOficio = new Vector();

		String[] aFiltros = cFiltro.split(",");
		iEjercicioSolicitud = Integer.parseInt(aFiltros[3], 10);
		iNumSolicitud = Integer.parseInt(aFiltros[4], 10);

		// dtOficioSolROP = tFecha.getDateSQL(cdtOficioSol);
		Vector vDatos = new Vector();

		try {
			rep1.iniciaReporte();
			Vector vcData3 = findByCustom(
					"",
					"SELECT "
							+ "ICVESOLICITANTE, "
							+ "ICVEREPLEGAL, "
							+ "TSREGISTRO, "
							+ "CDSCBIEN, "
							+ "CCALLE, "
							+ "CNUMEXTERIOR, "
							+ "CNUMINTERIOR, "
							+ "CCOLONIA, "
							+ "CCODPOSTAL, "
							+ "GRLMUNICIPIO.CNOMBRE AS cMunicipio, "
							+ "GRLENTIDADFED.CNOMBRE as cEntidad, "
							+ "GRLPAIS.CNOMBRE as cPais, "
							+ "GRLDOMICILIO.ICVEDOMICILIO as iCveDom, "
							+ "TRACATTRAMITE.CDSCBREVE as cTram, "
							+ "TRAMODALIDAD.CDSCMODALIDAD as cModal, "
							+ "GRLPERSONA.CNOMRAZONSOCIAL || GRLPERSONA.CAPPATERNO || GRLPERSONA.CAPMATERNO as cSolicitante "
							+ "FROM TRAREGSOLICITUD "
							+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
							+ " AND GRLDOMICILIO.ICVEDOMICILIO = TRAREGSOLICITUD.ICVEDOMICILIOSOLICITANTE "
							+ "JOIN GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
							+ " AND GRLMUNICIPIO.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
							+ " AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDOMICILIO.ICVEMUNICIPIO "
							+ "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
							+ " AND GRLENTIDADFED.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
							+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
							+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
							+ "JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD= TRAREGSOLICITUD.ICVEMODALIDAD "
							+ "JOIN GRLPERSONA ON GRLPERSONA.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
							+ "where IEJERCICIO = " + iEjercicioSolicitud
							+ " and INUMSOLICITUD = " + iNumSolicitud);
			TVDinRep vSoli = (TVDinRep) vcData3.get(0);
			iCveSolicitante = vSoli.getInt("ICVESOLICITANTE");
			iCveDomicilio = vSoli.getInt("iCveDom");
			iCveRepLegal = vSoli.getInt("ICVEREPLEGAL");
			tsRegistro = vSoli.getTimeStamp("TSREGISTRO");
			cDscBien = vSoli.getString("CDSCBIEN");
			cCalle = vSoli.getString("CCALLE");

			cNumExt = vSoli.getString("CNUMEXTERIOR");
			cNumInt = vSoli.getString("CNUMINTERIOR");
			cColonia = vSoli.getString("CCOLONIA");
			cCodPostal = vSoli.getString("CCODPOSTAL");
			cPais = vSoli.getString("cPais");
			cEntidad = vSoli.getString("cEntidad");
			cMunicipio = vSoli.getString("cMunicipio");

			cTramite = vSoli.getString("cTram");
			cModalidad = vSoli.getString("cModal");
			cSolicitante = vSoli.getString("cSolicitante");

			vData.put("iEjercicioSolicitud", iEjercicioSolicitud);
			vData.put("iNumSolicitud", iNumSolicitud);
			vData.put("iCveSistema",
					VParametros.getPropEspecifica("iIDSistema"));
			vData.put("iCveModulo", 2);
			vData.put("iNumReporte", 5);
			vData.put("cFolio", cFolio);
			vData.put("cAsunto", "Notificación de PNC");

			try {
				Vector vcOfi = this.findByCustom("",
						"SELECT CDSCBREVE FROM GRLOFICINA WHERE ICVEOFICINA="
								+ cCveOfic);

				if (vcOfi.size() > 0)
					cOfic = ((TVDinRep) vcOfi.get(0)).getString("CDSCBREVE");

				Vector vcDpto = this.findByCustom("",
						"SELECT CDSCBREVE FROM GRLDEPARTAMENTO WHERE ICVEDEPARTAMENTO="
								+ cCveDepto);

				if (vcDpto.size() > 0)
					cDpto = ((TVDinRep) vcDpto.get(0)).getString("CDSCBREVE");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Vector vcDias = this
						.findByCustom(
								"",
								"SELECT IDIASDESPUESNOTIF FROM TRACONFIGURATRAMITE CT "
										+ "JOIN TRAREGSOLICITUD S ON S.ICVETRAMITE=CT.ICVETRAMITE AND S.ICVEMODALIDAD=CT.ICVEMODALIDAD "
										+ "where S.IEJERCICIO="
										+ iEjercicioSolicitud
										+ " AND INUMSOLICITUD=" + iNumSolicitud);
				if (vcDias.size() > 0)
					iDiasDespNotificado = ((TVDinRep) vcDias.get(0))
							.getInt("IDIASDESPUESNOTIF");
			} catch (Exception e) {
				e.printStackTrace();
			}

			TDDPOPMDP rFolio = new TDDPOPMDP();
			rFolio.insertReportexFolio(vData, conn);

			dtToday = tFecha.TodaySQL();
			dtOficioSol = tFecha.getDateSQL(tsRegistro);
			if (tFecha.getIntYear(dtToday) != tFecha.getIntYear(dtOficioSol))
				cdtOficioSol = tFecha.getFechaDDMMMMMYYYY(dtOficioSol, " de ");
			else
				cdtOficioSol = tFecha.getStringDay(dtOficioSol) + " de "
						+ tFecha.getMonthName(dtOficioSol)
						+ " del año en curso";

			rep1.comEligeTabla("cReqNotificacion");

			Vector vcPNC = findByCustom("", "SELECT "
					+ "ICONSECUTIVOPNC, IEJERCICIOPNC FROM TRAREGPNCETAPA "
					+ "WHERE IEJERCICIO = " + iEjercicioSolicitud
					+ " AND INUMSOLICITUD = " + iNumSolicitud
					+ " ORDER BY IORDEN DESC");
			if (vcPNC.size() > 0) {
				TVDinRep vRegEtapa = (TVDinRep) vcPNC.get(0);
				iEjercicioPNC = vRegEtapa.getInt("IEJERCICIOPNC");
				iConsecutivoPNC = vRegEtapa.getInt("ICONSECUTIVOPNC");

				String sqlDatosADV = "SELECT PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO as cSolicitanteADV, "
						+ "REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO as cRepL, "
						+ "TRACATTRAMITE.CDSCBREVE as cTramiteADV, "
						+ "TRACATTRAMITE.CCVEINTERNA as cClaveT, "
						+ "ADV.CKMSENTIDO as CKMSENTIDO, "
						+ "ADV.CHECHOS as cHechos, "
						+ "ADV.CORGANO as cOrgano, "
						+ "UPPER(CARR.CDSCARRETERA) as cAutopista, "
						+ "ENTOF.CNOMBRE as cNomEnt, "
						+ "GRLOFICINA.CTITULARTEC as cTecnico "
						+ "FROM TRAREGSOLICITUD "
						+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
						+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
						+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
						+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
						+ "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
						+ "LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
						+ "JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD "
						+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA "
						+ "JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
						+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
						+ "where TRAREGSOLICITUD.IEJERCICIO= "
						+ iEjercicioSolicitud
						+ " and TRAREGSOLICITUD.INUMSOLICITUD= "
						+ iNumSolicitud;

				Vector vADV = new Vector();

				vADV = this.findByCustom("", sqlDatosADV);

				TVDinRep datosADV = new TVDinRep();

				if (vADV.size() > 0)
					datosADV = (TVDinRep) vADV.get(0);

				sbBuscaAdic
						.append("[iEjercicioSol]|[iNumSolicitud]|[dtSolicitud]|[cTramite]|[cDscBien]|")
						.append("[cSolicitante]|[cAsunto]|[cCalle]|[cNumExterior]|")
						.append("[cNumInterior]|[cColonia]|[cCodigoPostal]|[cPais]|")
						.append("[cEntidad]|[cMunicipio]|[numDiasNot]|[iConsecutivoPNC]|[iEjercicioPNC]|")
						.append("[cModalidad]|[cOficina]|[cDepto]|[cSolicitanteADV]|[cTramiteADV]|")
						.append("[cClaveT]|[cKmSentido]|[cHechos]|[cAutopista]|[cNomEnt]|[cRepL]|");

				sbReemplazaAdic
						.append(String.valueOf(iEjercicioSolicitud) + "|"
								+ (String.valueOf(iNumSolicitud)) + "|")
						.append(cdtOficioSol + "|" + cTramite + "|" + cDscBien
								+ "|" + cSolicitante + "|" + "Notificación"
								+ "|" + cCalle + "|" + cNumExt + "|" + cNumInt
								+ "|" + cColonia + "|")
						.append(cCodPostal + "|" + cPais + "|" + cEntidad + "|"
								+ cMunicipio + "|" + iDiasDespNotificado + "|"
								+ iConsecutivoPNC + "|" + iEjercicioPNC + "|"
								+ cModalidad + "|" + cOfic + "|" + cDpto + "|"
								+ datosADV.getString("cSolicitanteADV") + "|"
								+ datosADV.getString("cTramiteADV") + "|"
								+ datosADV.getString("cClaveT") + "|"
								+ datosADV.getString("CKMSENTIDO") + "|"
								+ datosADV.getString("cHechos") + "|"
								+ datosADV.getString("cAutopista") + "|"
								+ datosADV.getString("cNomEnt") + "|"
								+ datosADV.getString("cRepL") + "|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				String cadReqs = "SELECT TRAREQUISITO.CDSCBREVE as cRequisito, GRLCAUSAPNC.CDSCCAUSAPNC as cCausa, GRLREGCAUSAPNC.CDSCOTRACAUSA as cOtra "
						+ "FROM GRLREGCAUSAPNC "
						+ "inner join TRAREQUISITO on GRLREGCAUSAPNC.ICVEREQUISITO = TRAREQUISITO.ICVEREQUISITO "
						+ "inner join GRLCAUSAPNC on GRLCAUSAPNC.ICVEPRODUCTO = GRLREGCAUSAPNC.ICVEPRODUCTO "
						+ "and GRLCAUSAPNC.ICVECAUSAPNC = GRLREGCAUSAPNC.ICVECAUSAPNC "
						+ "where GRLREGCAUSAPNC.IEJERCICIO ="
						+ iEjercicioPNC
						+ " and GRLREGCAUSAPNC.ICONSECUTIVOPNC="
						+ iConsecutivoPNC
						+ " and GRLREGCAUSAPNC.ICVECAUSAPNC > 0 "
						+ "order by TRAREQUISITO.CDSCBREVE";

				Vector vcDataReq = findByCustom("", cadReqs);

				List<String> listaNomReq = new ArrayList<String>();
				List<TVDinRep> listaReq = new ArrayList<TVDinRep>();

				for (int ind = 0; ind < vcDataReq.size(); ind++) {

					TVDinRep vReq = (TVDinRep) vcDataReq.get(ind);
					listaReq.add(vReq);

					String reqAct = vReq.getString("cRequisito");
					if (!listaNomReq.contains(reqAct)) {
						listaNomReq.add(reqAct);
					}
				}

				int ultimoIndice = 0;
				boolean paro = false;

				for (int cont = 0; cont < listaNomReq.size(); cont++) {

					String nomAct = listaNomReq.get(cont);
					String celda = (cont + 1) + ". " + nomAct + ":";
					String otraCausa = listaReq.get(ultimoIndice).getString(
							"cOtra");
					rep1.comDespliegaCelda(celda);

					for (int cont2 = ultimoIndice; cont2 < listaReq.size(); cont2++) {

						if (nomAct.equals(listaReq.get(cont2).getString(
								"cRequisito"))) {
//							//System.out.print(listaReq.get(cont2).getString(
//									"cCausa"));
							rep1.comDespliegaCelda("    -"
									+ listaReq.get(cont2).getString("cCausa"));

						} else {
							ultimoIndice = cont2;
							paro = true;
						}

						if (paro == true) {
							//System.out.print(otraCausa);
							rep1.comDespliegaCelda("    -" + otraCausa);
							rep1.comDespliegaCelda("");
							paro = false;
							break;
						}

						if ((cont2 + 1) >= listaReq.size() && paro == false) {
							//System.out.print(otraCausa);
							rep1.comDespliegaCelda("    -" + otraCausa);
							rep1.comDespliegaCelda("");
						}

					}
				}

			}

			// Cargando otras causas

			// Falta sacar las referencias y Numeros Internos

			vDatos = new TDGeneral().generaOficioWord(cFolio,
					Integer.parseInt(cCveOfic, 10),
					Integer.parseInt(cCveDepto, 10), 0, 0, iCveSolicitante,
					iCveDomicilio, iCveRepLegal, "", "", "", "", false, "",
					new Vector(), false, new Vector(),
					rep1.getEtiquetasBuscar(), rep1.getEtiquetasRemplazar());

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		// Vector vDatosTemp = new Vector();
		Vector vDatosTemp = (Vector) vDatos.get(1);
		Vector vDatosTablas = rep1.getDatosTablas();
		for (int iCont = 0; iCont < vDatosTablas.size(); iCont++) {
			vDatosTemp.add(vDatosTablas.get(iCont));
		}
		vDatos.add(1, vDatosTemp);

		return vDatos;

		/*
		 * if(!cMensaje.equals("")) throw new Exception(cMensaje); return
		 * rep1.getVectorDatos(true);
		 */

	}

	/**************** PERMISOS WORD ADV ******************/

	public Vector PermisoF1(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");
		Vector vecDat = new Vector();
		TVDinRep datosPermiso = new TVDinRep();

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlDatosPermiso = "SELECT PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO as cSolicitante, "
				+ "REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO as cRepLegal, "
				+ "GRLDOMICILIO.CCALLE || ' Int. ' || GRLDOMICILIO.CNUMINTERIOR || ', Ext. ' || GRLDOMICILIO.CNUMEXTERIOR || ', Col. ' || GRLDOMICILIO.CCOLONIA || ', C.P. ' || GRLDOMICILIO.CCODPOSTAL || ', ' || GRLMUNICIPIO.CNOMBRE || ', ' ||  GRLENTIDADFED.CNOMBRE || ', ' || GRLPAIS.CNOMBRE as cDomicilio, "
				+ "TRACATTRAMITE.CDSCBREVE as cTramite, "
				+ "ADV.CKMSENTIDO as CKMSENTIDO, "
				+ "ADV.CHECHOS as cHechos, "
				+ "UPPER(CARR.CDSCARRETERA) as cAutopista, "
				+ "ENTOF.CNOMBRE as cNomEnt, "
				+ "FOL.CNUMPERMISO as cFolioPermiso "
				+ "FROM TRAREGSOLICITUD "
				+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "AND GRLDOMICILIO.ICVEDOMICILIO = TRAREGSOLICITUD.ICVEDOMICILIOSOLICITANTE "
				+ "JOIN GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "AND GRLMUNICIPIO.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
				+ "AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDOMICILIO.ICVEMUNICIPIO "
				+ "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "AND GRLENTIDADFED.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
				+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
				+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD "
				+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA "
				+ "JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
				+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRAFOLIOSADVXSOL FOL ON TRAREGSOLICITUD.IEJERCICIO = FOL.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD=FOL.INUMSOLICITUD "
				+ "where TRAREGSOLICITUD.IEJERCICIO= "
				+ iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		Vector vDatos = new Vector();

		// //System.out.print("el parametrooooooo-"+cFiltro);

		try {

			vecDat = this.findByCustom("", sqlDatosPermiso);
			if (vecDat.size() > 0) {
				datosPermiso = (TVDinRep) vecDat.get(0);

				rep1.iniciaReporte();

				sbBuscaAdic
						.append("[cFolioPermiso]|[cTramite]|[cAutopista]|[cKmSentido]|[cSolicitante]|[cRepLegal]|[cHechos]|[cNomEnt]|[cDomicilio]|");

				sbReemplazaAdic.append(datosPermiso.getString("cFolioPermiso")
						+ "|" + datosPermiso.getString("cTramite") + "|"
						+ datosPermiso.getString("cAutopista") + "|"
						+ datosPermiso.getString("cKmSentido") + "|"
						+ datosPermiso.getString("cSolicitante") + "|"
						+ datosPermiso.getString("cRepLegal") + "|"
						+ datosPermiso.getString("cHechos") + "|"
						+ datosPermiso.getString("cNomEnt") + "|"
						+ datosPermiso.getString("cDomicilio") + "|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}
	
	public Vector PermisoUnicoADV(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");
		Vector vecDat = new Vector();
		TVDinRep datosPermiso = new TVDinRep();

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlDatosPermiso = "SELECT " +
					"PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO as cSolicitante,  "+
					"case (REP.CNOMRAZONSOCIAL) "+
					"when '' THEN '' "+
					"ELSE ' REPRESENTADO LEGALMENTE POR '||REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO "+ 
					"END as cRepL, "+
					"TRACATTRAMITE.CDSCBREVE as cTramite, "+
					"TRACATTRAMITE.CCVEINTERNA as cClaveT, "+
					"ADV.CKMSENTIDO, "+
					"ADV.CHECHOS as cHechos,   "+
					"UPPER(CARR.CDSCARRETERA) as cCARRETERA,  "+
					"UPPER(CARR.CCONSECIONARIO) as CCONSECIONARIO,  "+
					"ENTOF.CNOMBRE as cNomEnt, "+
					"VARCHAR_FORMAT(TRAREGSOLICITUD.TSREGISTRO, 'DD/MM/YYYY') AS cDTSOL, "+
					"VARCHAR_FORMAT(CURRENT_DATE, 'DD/MM/YYYY') AS cDTREP, "+
					"TRADATOSPERM.CFOLPERMISO AS CFOLPERM, "+
					"TRADATOSPERM.CFOLVOLANTE, "+
					"TRADATOSPERM.CFOLPERMISO, "+
					"TRADATOSPERM.CDGDC, "+
					"TRADATOSPERM.CNUMERAL, "+
					"TRADATOSPERM.CPARRAF_1, "+
					"TRADATOSPERM.CPARRAF_2, "+
					"TRADATOSPERM.CPARRAF_3, "+
					"TRADATOSPERM.CPARRAF_4, "+
					"TRADATOSPERM.CPARRAF_5, "+
					"TRADATOSPERM.CPARRAF_6, "+
					"TRADATOSPERM.CARTICULOS, "+
					"TRADATOSPERM.CREVDGDC, "+
					"TRADATOSPERM.CPLAZO, "+
					"TRADATOSPERM.CCALMAT, "+
					"GRLDOMICILIO.CCALLE || ' Int. ' || GRLDOMICILIO.CNUMINTERIOR || ', Ext. ' || GRLDOMICILIO.CNUMEXTERIOR || ', Col. ' || GRLDOMICILIO.CCOLONIA || ', C.P. ' || GRLDOMICILIO.CCODPOSTAL || ', ' || GRLMUNICIPIO.CNOMBRE || ', ' ||  GRLENTIDADFED.CNOMBRE || ', ' || GRLPAIS.CNOMBRE as cDomicilio " +
					"FROM TRAREGSOLICITUD  "+
					"JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
					+ "AND GRLDOMICILIO.ICVEDOMICILIO = TRAREGSOLICITUD.ICVEDOMICILIOSOLICITANTE "
					+ "JOIN GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
					+ "AND GRLMUNICIPIO.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
					+ "AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDOMICILIO.ICVEMUNICIPIO "
					+ "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
					+ "AND GRLENTIDADFED.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
					+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "+
					"JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE   "+
					"JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE  "+
					"LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL   "+
					"LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL  "+
					"JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD   "+
					"JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA  "+
					"JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA   "+
					"JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS  "+
					"JOIN TRADATOSNOAFEC ON TRAREGSOLICITUD.IEJERCICIO = TRADATOSNOAFEC.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = TRADATOSNOAFEC.INUMSOL  "+
					"JOIN TRAFOLENVIOS ON TRAREGSOLICITUD.IEJERCICIO = TRAFOLENVIOS.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = TRAFOLENVIOS.INUMSOLICITUD "+
					"JOIN TRADATOSPERM ON TRAREGSOLICITUD.IEJERCICIO = TRADATOSPERM.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = TRADATOSPERM.INUMSOLICITUD "
				+ "where TRAREGSOLICITUD.IEJERCICIO= "
				+ iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		Vector vDatos = new Vector();

		try {

			vecDat = this.findByCustom("", sqlDatosPermiso);
			if (vecDat.size() > 0) {
				datosPermiso = (TVDinRep) vecDat.get(0);

				rep1.iniciaReporte();
				
				
				sbBuscaAdic.append("[CFOLVOLANTE]|[CFOLPERMISO]|[CTRAMITE]|[CCARRETERA]|[CKMSENTIDO]|[CDGDC]|[CSOLICITANTE]|[CREPL]|[CPARRAF_1]|[CPARRAF_2]|[CPARRAF_3]|[CPARRAF_4]|[CPARRAF_5]|[CPARRAF_6]|[CNUMERAL]|[CARTICULOS]"+
								   "|[CHECHOS]|[CREVDGDC]|[cNOMENT]|[CPLAZO]|[CCALMAT]|[CDOMICILIO]|[cDTREP]|");

				sbReemplazaAdic.append(datosPermiso.getString("CFOLVOLANTE")+"|"+datosPermiso.getString("CFOLPERMISO")+"|"+datosPermiso.getString("CTRAMITE")+"|"+datosPermiso.getString("CCARRETERA")+"|"+datosPermiso.getString("CKMSENTIDO")+"|"+datosPermiso.getString("CDGDC")+"|"+datosPermiso.getString("CSOLICITANTE")+"|"+datosPermiso.getString("CREPL")+"|"+datosPermiso.getString("CPARRAF_1")+"|"+datosPermiso.getString("CPARRAF_2")+"|"+datosPermiso.getString("CPARRAF_3")+"|"+datosPermiso.getString("CPARRAF_4")+"|"+datosPermiso.getString("CPARRAF_5")+"|"+datosPermiso.getString("CPARRAF_6")+"|"+datosPermiso.getString("CNUMERAL")+"|"+datosPermiso.getString("CARTICULOS")+"|"+datosPermiso.getString("CHECHOS")+"|"+datosPermiso.getString("CREVDGDC")+"|"+datosPermiso.getString("cNOMENT")+"|"+datosPermiso.getString("CPLAZO")+"|"+datosPermiso.getString("CCALMAT")+"|"+datosPermiso.getString("CDOMICILIO")+"|"+datosPermiso.getString("cDTREP")+"|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}

	/**************** PERMISOS WORD ADV ******************/

	/**************** FORMATOS WORD ADV ******************/
	
	public Vector minutaVisitaT(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");
		Vector vecDat = new Vector();
		TVDinRep datosPermiso = new TVDinRep();
		
		Vector vecAsis = new Vector();
		TVDinRep datosAsist = new TVDinRep();

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlDatosVT = "SELECT VARCHAR_FORMAT(CURRENT_DATE, 'DD/MM/YYYY') AS CDTREP, "+
					"TRAM.CDSCBREVE AS CTRAMITE,"+
					"ADV.CHECHOS,"+
					"AUTO.CDSCARRETERA AS CAUTOPISTA,"+
					"ADV.CKMSENTIDO,"+
					"VT.COBSERVACION,"+
					"VT.CCONCLUSION,"+
					"VT.CACUERDO,"+
					"ENTOF.CNOMBRE as CNOMENT "+
				"FROM TRAREGSOLICITUD SOL "+
				"INNER JOIN TRACATTRAMITE TRAM ON SOL.ICVETRAMITE = TRAM.ICVETRAMITE "+
				"INNER JOIN TRAREGRESOLVTECXSOL  VT ON VT.IEJERCICIO=SOL.IEJERCICIO AND VT.INUMSOLICITUD = SOL.INUMSOLICITUD "+
				"INNER JOIN TRAREGDATOSADVXSOL ADV ON ADV.IEJERCICIO = SOL.IEJERCICIO AND ADV.INUMSOLICITUD = SOL.INUMSOLICITUD "+
				"INNER JOIN TRACATCARRETERA AUTO ON AUTO.ICVECARRETERA = ADV.ICVECARRETERA "+
				"INNER JOIN GRLOFICINA ON SOL.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "+
				"INNER JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = 1 "+
				"WHERE SOL.IEJERCICIO ="+ iEjer
				+ " AND SOL.INUMSOLICITUD= " + iNumSol;
		
		String asisVT = "SELECT CASIS1,CREP1,CCARGO1,CASIS2,CREP2,CCARGO2,CASIS3,CREP3,CCARGO3,CASIS4,CREP4,CCARGO4,CASIS5,CREP5,CCARGO5,CASIS6,CREP6,CCARGO6,CASIS7,CREP7,CCARGO7,CASIS8,CREP8,CCARGO8,CASIS9,CREP9,CCARGO9 FROM TRAASISVTADV WHERE IEJERCICIO ="+iEjer+" AND INUMSOLICITUD ="+iNumSol;

		Vector vDatos = new Vector();

		try {

			vecDat = this.findByCustom("", sqlDatosVT);
			vecAsis = this.findByCustom("", asisVT);
			if (vecDat.size() > 0 && vecAsis.size()>0) {
				
				datosPermiso = (TVDinRep) vecDat.get(0);
				datosAsist = (TVDinRep) vecAsis.get(0);

				rep1.iniciaReporte();
				
				sbBuscaAdic.append("[CDTREP]|[CTRAMITE]|[CHECHOS]|[CAUTOPISTA]|[CKMSENTIDO]|[COBS]|[CCONCLUSION]|[CACUERDO]|[CNOMENT]|[CASIS1]|[CREP1]|[CCARGO1]|[CASIS2]|[CREP2]|[CCARGO2]|[CASIS3]|[CREP3]|[CCARGO3]|[CASIS4]|[CREP4]|[CCARGO4]|[CASIS5]|[CREP5]|[CCARGO5]|[CASIS6]|[CREP6]|[CCARGO6]|[CASIS7]|[CREP7]|[CCARGO7]|[CASIS8]|[CREP8]|[CCARGO8]|[CASIS9]|[CREP9]|[CCARGO9]|");

				sbReemplazaAdic.append(datosPermiso.getString("CDTREP")+"|"+datosPermiso.getString("CTRAMITE")+"|"+datosPermiso.getString("CHECHOS")+"|"+
				datosPermiso.getString("CAUTOPISTA")+"|"+datosPermiso.getString("CKMSENTIDO")+"|"+datosPermiso.getString("COBSERVACION")+"|"
						+datosPermiso.getString("CCONCLUSION")+"|"+datosPermiso.getString("CACUERDO")+"|"+datosPermiso.getString("CNOMENT")+"|"+
						datosAsist.getString("CASIS1")+"|"+	datosAsist.getString("CREP1")+"|"+	datosAsist.getString("CCARGO1")+"|"+
						datosAsist.getString("CASIS2")+"|"+	datosAsist.getString("CREP2")+"|"+	datosAsist.getString("CCARGO2")+"|"+
						datosAsist.getString("CASIS3")+"|"+	datosAsist.getString("CREP3")+"|"+	datosAsist.getString("CCARGO3")+"|"+
						datosAsist.getString("CASIS4")+"|"+	datosAsist.getString("CREP4")+"|"+	datosAsist.getString("CCARGO4")+"|"+
						datosAsist.getString("CASIS5")+"|"+	datosAsist.getString("CREP5")+"|"+	datosAsist.getString("CCARGO5")+"|"+
						datosAsist.getString("CASIS6")+"|"+	datosAsist.getString("CREP6")+"|"+	datosAsist.getString("CCARGO6")+"|"+
						datosAsist.getString("CASIS7")+"|"+	datosAsist.getString("CREP7")+"|"+	datosAsist.getString("CCARGO7")+"|"+
						datosAsist.getString("CASIS8")+"|"+	datosAsist.getString("CREP8")+"|"+	datosAsist.getString("CCARGO8")+"|"+
						datosAsist.getString("CASIS9")+"|"+	datosAsist.getString("CREP9")+"|"+	datosAsist.getString("CCARGO9")+"|");
				
				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}

	public Vector dictamenFact(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");
		Vector vecDat = new Vector();
		TVDinRep datosPermiso = new TVDinRep();

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlDatosFactibilidad = "SELECT PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO as cSolicitante, "
				+ "REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO as cRepL, "
				+ "TRACATTRAMITE.CDSCBREVE as cTramite, "
				+ "TRACATTRAMITE.CCVEINTERNA as cClaveT, "
				+ "VARCHAR_FORMAT(DTVISITA, 'DD/MM/YYYY') AS cVisitaT, "
				+ "ADV.CKMSENTIDO as CKMSENTIDO, "
				+ "ADV.CHECHOS as cHechos, "
				+ "UPPER(ADV.CORGANO) as cOrgano, "
				+ "UPPER(CARR.CDSCARRETERA) as cAutopista, "
				+ "ENTOF.CNOMBRE as cNomEnt, "
				+ "UPPER(GRLOFICINA.CTITULARTEC) as cTecnico "
				+ "FROM TRAREGSOLICITUD "
				+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
				+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD "
				+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA "
				+ "JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
				+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "where TRAREGSOLICITUD.IEJERCICIO= "
				+ iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		Vector vDatos = new Vector();

		// //System.out.print("el parametrooooooo-"+cFiltro);

		try {

			vecDat = this.findByCustom("", sqlDatosFactibilidad);
			if (vecDat.size() > 0) {
				datosPermiso = (TVDinRep) vecDat.get(0);

				rep1.iniciaReporte();

				sbBuscaAdic
						.append("[cSolicitante]|[cTramite]|[cClaveT]|[cVisitaT]|[cKmSentido]|[cHechos]|[cOrgano]|[cAutopista]|[cNomEnt]|[cRepL]|[cTecnico]|");

				sbReemplazaAdic.append(datosPermiso.getString("cSolicitante")
						+ "|" + datosPermiso.getString("cTramite") + "|"
						+ datosPermiso.getString("cClaveT") + "|"
						+ datosPermiso.getString("cVisitaT") + "|"
						+ datosPermiso.getString("cKmSentido") + "|"
						+ datosPermiso.getString("cHechos") + "|"
						+ datosPermiso.getString("cOrgano") + "|"
						+ datosPermiso.getString("cAutopista") + "|"
						+ datosPermiso.getString("cNomEnt") + "|"
						+ datosPermiso.getString("cRepL") + "|"
						+ datosPermiso.getString("cTecnico") + "|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}

	public Vector noAfectacion(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");
		Vector vecDat = new Vector();
		TVDinRep datosPermiso = new TVDinRep();

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlDatosFactibilidad = "SELECT PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO as cSolicitante, "
				+ "REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO as cRepL, "
				+ "TRACATTRAMITE.CDSCBREVE as cTramite, "
				+ "TRACATTRAMITE.CCVEINTERNA as cClaveT, "
				+ "VARCHAR_FORMAT(DTVISITA, 'DD/MM/YYYY') AS cVisitaT, "
				+ "ADV.CKMSENTIDO as CKMSENTIDO, "
				+ "ADV.CHECHOS as cHechos, "
				+ "UPPER(ADV.CORGANO) as cOrgano, "
				+ "UPPER(CARR.CDSCARRETERA) as cAutopista, "
				+ "ENTOF.CNOMBRE as cNomEnt, "
				+ "UPPER(GRLOFICINA.CTITULARTEC) as cTecnico "
				+ "FROM TRAREGSOLICITUD "
				+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
				+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD "
				+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA "
				+ "JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
				+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "where TRAREGSOLICITUD.IEJERCICIO= "
				+ iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		Vector vDatos = new Vector();

		// //System.out.print("el parametrooooooo-"+cFiltro);

		try {

			vecDat = this.findByCustom("", sqlDatosFactibilidad);
			if (vecDat.size() > 0) {
				datosPermiso = (TVDinRep) vecDat.get(0);

				rep1.iniciaReporte();

				sbBuscaAdic
						.append("[cSolicitante]|[cTramite]|[cClaveT]|[cVisitaT]|[cKmSentido]|[cHechos]|[cOrgano]|[cAutopista]|[cNomEnt]|[cRepL]|[cTecnico]|");

				sbReemplazaAdic.append(datosPermiso.getString("cSolicitante")
						+ "|" + datosPermiso.getString("cTramite") + "|"
						+ datosPermiso.getString("cClaveT") + "|"
						+ datosPermiso.getString("cVisitaT") + "|"
						+ datosPermiso.getString("cKmSentido") + "|"
						+ datosPermiso.getString("cHechos") + "|"
						+ datosPermiso.getString("cOrgano") + "|"
						+ datosPermiso.getString("cAutopista") + "|"
						+ datosPermiso.getString("cNomEnt") + "|"
						+ datosPermiso.getString("cRepL") + "|"
						+ datosPermiso.getString("cTecnico") + "|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}

	public Vector envioST_DAJL(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");
		Vector vecDat = new Vector();
		TVDinRep datosPermiso = new TVDinRep();

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlDatosFactibilidad = "SELECT PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO as cSolicitante, "
				+ "REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO as cRepL, "
				+ "TRACATTRAMITE.CDSCBREVE as cTramite, "
				+ "TRACATTRAMITE.CCVEINTERNA as cClaveT, "
				+ "VARCHAR_FORMAT(DTVISITA, 'DD/MM/YYYY') AS cVisitaT, "
				+ "ADV.CKMSENTIDO as CKMSENTIDO, "
				+ "ADV.CHECHOS as cHechos, "
				+ "UPPER(ADV.CORGANO) as cOrgano, "
				+ "UPPER(CARR.CDSCARRETERA) as cAutopista, "
				+ "ENTOF.CNOMBRE as cNomEnt "
				+ "FROM TRAREGSOLICITUD "
				+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
				+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD "
				+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA "
				+ "JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
				+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "where TRAREGSOLICITUD.IEJERCICIO= "
				+ iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		Vector vDatos = new Vector();

		// //System.out.print("el parametrooooooo-"+cFiltro);

		try {

			vecDat = this.findByCustom("", sqlDatosFactibilidad);
			if (vecDat.size() > 0) {
				datosPermiso = (TVDinRep) vecDat.get(0);

				rep1.iniciaReporte();

				sbBuscaAdic
						.append("[cSolicitante]|[cTramite]|[cClaveT]|[cVisitaT]|[cKmSentido]|[cHechos]|[cOrgano]|[cAutopista]|[cNomEnt]|[cRepL]|");

				sbReemplazaAdic.append(datosPermiso.getString("cSolicitante")
						+ "|" + datosPermiso.getString("cTramite") + "|"
						+ datosPermiso.getString("cClaveT") + "|"
						+ datosPermiso.getString("cVisitaT") + "|"
						+ datosPermiso.getString("cKmSentido") + "|"
						+ datosPermiso.getString("cHechos") + "|"
						+ datosPermiso.getString("cOrgano") + "|"
						+ datosPermiso.getString("cAutopista") + "|"
						+ datosPermiso.getString("cNomEnt") + "|"
						+ datosPermiso.getString("cRepL") + "|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}

	public Vector entregaResol(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlDatosSolRep = "SELECT DISTINCT(PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO) as cSolicitante, "
				+ "GRLDOMICILIO.CCALLE || ' Int. ' || GRLDOMICILIO.CNUMINTERIOR || ' Ext. ' || GRLDOMICILIO.CNUMEXTERIOR as cdomicilio, "
				+ "GRLDOMICILIO.CCOLONIA, "
				+ "GRLDOMICILIO.CCODPOSTAL, "
				+ "GRLMUNICIPIO.CNOMBRE AS cNOMMUN, "
				+ "GRLENTIDADFED.CNOMBRE AS cNOMENT, "
				+ "GRLDOMICILIO.CTELEFONO, "
				+ "per.CCORREOE AS cMAILSOL, "
				+ "REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO as cRepLeg, "
				+ "REP.CCORREOE cMAILREP, "
				+ "DREP.CTELEFONO cTELREP, "
				+ "PER.CRFC "
				+ "FROM TRAREGSOLICITUD "
				+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "AND GRLDOMICILIO.ICVEDOMICILIO = TRAREGSOLICITUD.ICVEDOMICILIOSOLICITANTE "
				+ "JOIN GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "AND GRLMUNICIPIO.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
				+ "AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLDOMICILIO.ICVEMUNICIPIO "
				+ "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "AND GRLENTIDADFED.ICVEENTIDADFED = GRLDOMICILIO.ICVEENTIDADFED "
				+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
				+ "JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD= TRAREGSOLICITUD.ICVEMODALIDAD "
				+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "JOIN GRLDOMICILIO DREP ON DREP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "AND DREP.ICVEDOMICILIO = TRAREGSOLICITUD.ICVEDOMICILIOREPLEGAL "
				+ "where TRAREGSOLICITUD.IEJERCICIO= "
				+ iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		String sqlDatosSolicitud = "SELECT "
				+ "TRACATTRAMITE.CDSCBREVE as cTram,  "
				+ "TRAMODALIDAD.CDSCMODALIDAD as cModal, "
				+ "TRACATTRAMITE.CCVEINTERNA AS cHOMOCLAVE, "
				+ "DAY(CURRENT_DATE) || '/' || MONTH(CURRENT_DATE) || '/' || YEAR(CURRENT_DATE)AS cDTREP, "
				+ "CARR.CDSCARRETERA, "
				+ "DAY(DATE(TRAREGSOLICITUD.TSREGISTRO)) || '/' || MONTH(DATE(TRAREGSOLICITUD.TSREGISTRO)) || '/' || YEAR(DATE(TRAREGSOLICITUD.TSREGISTRO)) AS cDTSOL, "
				+ "ENTOF.CNOMBRE AS cNOMENT, "
				+ "ADV.CHECHOS, "
				+ "ADV.CKMSENTIDO as CKMSENTIDO, "
				+ "TRAREGSOLICITUD.CNOMAUTORIZARECOGER, "
				+ "case "
				+ "when TRAREGSOLICITUD.LRESOLUCIONPOSITIVA > 0 then 'X' "
				+ "else ' ' end as cpositiva, "
				+ "case "
				+ "when TRAREGSOLICITUD.LRESOLUCIONPOSITIVA <> 1 then 'X' "
				+ "else ' ' end as cnegativa, "
				+ "TRAREGETAPASXMODTRAM.COBSENVIADACIS AS COBS "
				+ "FROM TRAREGSOLICITUD  "
				+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
				+ "JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD= TRAREGSOLICITUD.ICVEMODALIDAD  "
				+ "JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD "
				+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA  "
				+ "JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA  "
				+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED  AND ENTOF.ICVEPAIS = 1 "
				+ "JOIN TRAREGETAPASXMODTRAM ON TRAREGETAPASXMODTRAM.IEJERCICIO=TRAREGSOLICITUD.IEJERCICIO "
				+ "AND TRAREGETAPASXMODTRAM.INUMSOLICITUD= TRAREGSOLICITUD.INUMSOLICITUD AND TRAREGETAPASXMODTRAM.ICVEETAPA = 7 "
				+ "where TRAREGSOLICITUD.IEJERCICIO=  " + iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		Vector vDatos = new Vector();

		Vector vecDat = new Vector();
		TVDinRep datosSolRep = new TVDinRep();
		TVDinRep datosSolicitud = new TVDinRep();

		try {

			vecDat = this.findByCustom("", sqlDatosSolRep);

			if (vecDat.size() > 0) {
				datosSolRep = (TVDinRep) vecDat.get(0);

				vecDat = null;
				vecDat = this.findByCustom("", sqlDatosSolicitud);

				if (vecDat.size() > 0)
					datosSolicitud = (TVDinRep) vecDat.get(0);

				rep1.iniciaReporte();

				sbBuscaAdic
						.append("[cSolicitante]|[cDomicilio]|[cColonia]|[cMun]|[cMail]|[cTel]")
						.append("|[cRFC]|[cCodP]|[cNomEnt]|[cRepL]|[cMailRep]|[cTelRep]|[cClaveT]")
						.append("|[cTram]|[cModal]|[cHechos]|[cAutopista]|[cKmSentido]")
						.append("|[iEjer]|[iNumSol]|[cDtSol]|[cPos]|[cNeg]|[cObs]|[dtRep]|[cRecoge]|");

				sbReemplazaAdic
						.append(datosSolRep.getString("CSOLICITANTE")
								+ "|"
								+ datosSolRep.getString("CDOMICILIO")
								+ "|"
								+ datosSolRep.getString("CCOLONIA")
								+ "|"
								+ datosSolRep.getString("CNOMMUN")
								+ "|"
								+ datosSolRep.getString("CMAILSOL")
								+ "|"
								+ datosSolRep.getString("CTELEFONO")
								+ "|"
								+ datosSolRep.getString("CRFC")
								+ "|"
								+ datosSolRep.getString("CCODPOSTAL")
								+ "|"
								+ datosSolRep.getString("CNOMENT")
								+ "|"
								+ datosSolRep.getString("CREPLEG")
								+ "|"
								+ datosSolRep.getString("CMAILREP")
								+ "|"
								+ datosSolRep.getString("CTELREP")
								+ "|"
								+

								datosSolicitud.getString("CHOMOCLAVE")
								+ "|"
								+ datosSolicitud.getString("CTRAM")
								+ "|"
								+ datosSolicitud.getString("CMODAL")
								+ "|"
								+ datosSolicitud.getString("CHECHOS")
								+ "|"
								+ datosSolicitud.getString("CDSCARRETERA")
								+ "|"
								+ datosSolicitud.getString("CKMSENTIDO")
								+ "|"
								+ "|"
								+ iEjer
								+ "|"
								+ iNumSol
								+ "|"
								+ datosSolicitud.getString("CDTSOL")
								+ "|"
								+ datosSolicitud.getString("CPOSITIVA")
								+ "|"
								+ datosSolicitud.getString("CNEGATIVA")
								+ "|"
								+ datosSolicitud.getString("COBS")
								+ "|"
								+ datosSolicitud.getString("CDTREP")
								+ "|"
								+ datosSolicitud
										.getString("CNOMAUTORIZARECOGER") + "|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}

	public Vector oficiosEnv(String cFiltro) throws Exception {

		StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
		TWord rep1 = new TWord();
		TVDinRep vData = new TVDinRep();
		int iEjer = 0, iNumSol = 0;

		String[] aFiltros = cFiltro.split(",");
		Vector vecDat = new Vector();
		TVDinRep datosOficio = new TVDinRep();

		iEjer = Integer.parseInt(aFiltros[0]);
		iNumSol = Integer.parseInt(aFiltros[1]);

		String sqlOficio = "SELECT PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO as cSolicitante, "
				+ "REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO as cRepL, "
				+ "TRACATTRAMITE.CDSCBREVE as cTramite, "
				+ "TRACATTRAMITE.CCVEINTERNA as cClaveT, "
				+ "VARCHAR_FORMAT(DTVISITA, 'DD/MM/YYYY') AS cVisitaT, "
				+ "ADV.CKMSENTIDO as CKMSENTIDO, "
				+ "ADV.CHECHOS as cHechos, "
				+ "UPPER(CARR.CDSCARRETERA) as cAutopista, "
				+ "ENTOF.CNOMBRE as cNomEnt, "
				+ "UPPER(ADV.CORGANO) as cOrgano, "
				+ "FOL.CNUMPERMISO "
				+ "FROM TRAREGSOLICITUD "
				+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "JOIN GRLPAIS ON GRLPAIS.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "
				+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE "
				+ "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA = TRAREGSOLICITUD.ICVEREPLEGAL "
				+ "JOIN TRAREGDATOSADVXSOL ADV ON TRAREGSOLICITUD.IEJERCICIO = ADV.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = ADV.INUMSOLICITUD "
				+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA "
				+ "JOIN GRLOFICINA ON TRAREGSOLICITUD.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
				+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
				+ "JOIN TRAFOLIOSADVXSOL FOL ON TRAREGSOLICITUD.IEJERCICIO = FOL.IEJERCICIO "
				+ "AND TRAREGSOLICITUD.INUMSOLICITUD=FOL.INUMSOLICITUD "
				+ "where TRAREGSOLICITUD.IEJERCICIO= "
				+ iEjer
				+ " and TRAREGSOLICITUD.INUMSOLICITUD= " + iNumSol;

		Vector vDatos = new Vector();

		try {

			vecDat = this.findByCustom("", sqlOficio);
			if (vecDat.size() > 0) {
				datosOficio = (TVDinRep) vecDat.get(0);

				rep1.iniciaReporte();

				sbBuscaAdic
						.append("[cSolicitante]|[cTramite]|[cClaveT]|[cVisitaT]|[cKmSentido]|[cHechos]|[cOrgano]|[cAutopista]|[cNomEnt]|[cRepL]|[cNumPermiso]|");

				sbReemplazaAdic.append(datosOficio.getString("cSolicitante")
						+ "|" + datosOficio.getString("cTramite") + "|"
						+ datosOficio.getString("cClaveT") + "|"
						+ datosOficio.getString("cVisitaT") + "|"
						+ datosOficio.getString("cKmSentido") + "|"
						+ datosOficio.getString("cHechos") + "|"
						+ datosOficio.getString("cOrgano") + "|"
						+ datosOficio.getString("cAutopista") + "|"
						+ datosOficio.getString("cNomEnt") + "|"
						+ datosOficio.getString("cRepL") + "|"
						+ datosOficio.getString("CNUMPERMISO") + "|");

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

				rep1.anexaEtiquetas(sbBuscaAdic, sbReemplazaAdic);

				vDatos = new TDGeneral()
						.generaFormato(rep1.getEtiquetasBuscar(),
								rep1.getEtiquetasRemplazar());

			} else {
				vDatos = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		return vDatos;
	}

	/*******************************************************************/

	public StringBuffer acuseEntregaImprocedente(String cSolicitud)
			throws Exception {
		StringBuffer sbRetorno = new StringBuffer();
		StringBuffer sbImproc = new StringBuffer();
		TExcel rep = new TExcel();
		TDTRARegTramXSol dTraCancelado = new TDTRARegTramXSol();
		TVDinRep vDinCan = new TVDinRep();
		String aSolicitud[] = cSolicitud.split(",");
		String aTraMod[] = aSolicitud[3].replace('.', ',').split(",");
		TDTRARegEtapasXModTram dEtapas = new TDTRARegEtapasXModTram();
		int iCveOficina = 0;

		int iFondoEsp = 33, iBorde = 1, iReng = 27;
		try {
			vDinCan.put("iEjercicio", aSolicitud[0]);
			vDinCan.put("iNumSolicitud", aSolicitud[1]);
			vDinCan.put("iCveMotivoCancela", 4);
			vDinCan.put("iCveUsuario", aSolicitud[2]);
			vDinCan.put("cObs", " ");
			// vDinCan = dTraCancelado.insert(vDinCan,null);

			String cUser = "SELECT " + "iCveOficina "
					+ "FROM TRAREGETAPASXMODTRAM " + "where IEJERCICIO = "
					+ aSolicitud[0] + " and INUMSOLICITUD = " + aSolicitud[1]
					+ " and iCveEtapa = "
					+ VParametros.getPropEspecifica("EtapaRegistro");

			Vector vOfic = this.findByCustom("", cUser);

			TVDinRep vDatosUser = (TVDinRep) vOfic.get(0);
			iCveOficina = vDatosUser.getInt("iCveOficina");

			TVDinRep vCambiaEtapa = new TVDinRep();
			vCambiaEtapa.put("iEjercicio", aSolicitud[0]);
			vCambiaEtapa.put("iNumSolicitud", aSolicitud[1]);
			vCambiaEtapa.put("iCveTramite", aTraMod[0]);
			vCambiaEtapa.put("iCveModalidad", aTraMod[1]);
			vCambiaEtapa.put("iCveEtapa",
					VParametros.getPropEspecifica("EtapaTramiteCancelado"));
			vCambiaEtapa.put("iCveOficina", iCveOficina);
			vCambiaEtapa.put("iCveDepartamento",
					VParametros.getPropEspecifica("DeptoVentanillaUnica"));
			vCambiaEtapa.put("iCveUsuario", aSolicitud[2]);
			vCambiaEtapa.put("lAnexo", 0);
			dEtapas.cambiarEtapa(vCambiaEtapa, false, " ");

			sbRetorno = this.acuseEntrega(cSolicitud);
			rep.comDespliega("A", 8, "ACUSE DE CANCELACIÓN DE TRÁMITE");
			rep.comFontColor("R", iReng, "X", iReng, 2);
			rep.comDespliegaCombinado("TRÁMITE IMPROCEDENTE", "R", iReng, "X",
					iReng, rep.getAT_COMBINA_CENTRO(), "", true, iFondoEsp,
					true, false, iBorde, 1);

			Vector vcNotif = findByCustom(
					"",
					"SELECT "
							+ "DTNOTIFICACION, "
							+ " UReg.cNombre || ' ' || UReg.cApPaterno || ' ' || UReg.cApMaterno AS cNomUsuRecibe "
							+ " FROM TRARegReqXTram "
							+ " JOIN TRARegTramXSol RTS ON RTS.iEjercicio = "
							+ aSolicitud[0]
							+ " AND RTS.iNumSolicitud = "
							+ aSolicitud[1]
							+ " "
							+ " JOIN SEGUsuario UReg ON UReg.iCveUsuario = RTS.iCveUsuario "
							+ " where " + " TRARegReqXTram.IEJERCICIO = "
							+ aSolicitud[0]
							+ " and TRARegReqXTram.INUMSOLICITUD = "
							+ aSolicitud[1] + " ORDER BY dtNotificacion ");
			if (vcNotif.size() > 0) {
				TVDinRep vNotif = (TVDinRep) vcNotif.get(0);
				rep.comDespliegaAlineado("T", 33, "Fecha de Notificación:",
						true, rep.getAT_HDERECHA(), "");
				if (vNotif != null && vNotif.getDate("DTNOTIFICACION") != null)
					rep.comDespliegaAlineado(
							"U",
							33,
							"W",
							33,
							"'"
									+ tFecha.getFechaDDMMMYYYY(
											vNotif.getDate("DTNOTIFICACION"),
											"/"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");

				rep.comDespliegaCombinado("ENTREGADO POR: ", "N", 36, "X", 36,
						rep.getAT_COMBINA_CENTRO(), "", true, 16, true, true,
						iBorde, 1);
				rep.comDespliegaAlineado("N", 40, "X", 40,
						vNotif.getString("cNomUsuRecibe"), false,
						rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO());
				rep.comBordeTotal("N", 36, "X", 40, 0);
			}

			sbImproc = rep.getSbDatos();
			sbRetorno.append(sbImproc);
		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}

		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	public String fBuscaConceptos(int iEjer, int iCveTram, int iCveMod) {
		String cConceptos = "";
		String cSQL = "select REF.IREFNUMERICAINGRESOS AS iReferencia "
				+ "FROM TRACONCEPTOXTRAMMOD CON "
				+ "JOIN TRAREFERENCIACONCEPTO REF ON REF.IEJERCICIO = CON.IEJERCICIO "
				+ "AND REF.ICVECONCEPTO = CON.ICVECONCEPTO "
				+ "WHERE CON.IEJERCICIO = "
				+ iEjer
				+ " AND CON.ICVETRAMITE = "
				+ iCveTram
				+ " AND CON.ICVEMODALIDAD = "
				+ iCveMod
				+ " AND CON.ICVECONCEPTO NOT IN (SELECT CXG.ICVECONCEPTO "
				+ "FROM TRACONCEPTOXGRUPO CXG WHERE CXG.ICVECONCEPTO = CON.ICVECONCEPTO)";

		try {
			TDGRLReporte dConceptos = new TDGRLReporte();
			Vector vConceptos = dConceptos.findByCustom(
					"iCveConcepto,iCveModalidad", cSQL);
			if (vConceptos != null) {
				for (int i = 0; i < vConceptos.size(); i++) {
					if (cConceptos.compareTo("") != 0)
						cConceptos += ", ";
					TVDinRep dRegConcepto = (TVDinRep) vConceptos.get(i);
					cConceptos += dRegConcepto.getString("iReferencia");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}
		return cConceptos;
	}

	public Vector fBuscaGrupos(int iEjer, int iCveTram, int iCveMod) {
		Vector vcGrupos = new Vector();
		String cSQL = "SELECT DISTINCT(GRP.CDSCGRUPO) as cGrupo FROM TRACONCEPTOXTRAMMOD CON "
				+ "JOIN TRACONCEPTOXGRUPO CXG ON CXG.ICVECONCEPTO = CON.ICVECONCEPTO "
				+ "JOIN TRAGRUPOCONCEPTO GRP ON GRP.ICVEGRUPO = CXG.ICVEGRUPO "
				+ "WHERE CON.IEJERCICIO = "
				+ iEjer
				+ " AND CON.ICVETRAMITE = "
				+ iCveTram + " AND CON.ICVEMODALIDAD = " + iCveMod;
		try {
			TDGRLReporte dGrupos = new TDGRLReporte();
			Vector vGrupos = dGrupos.findByCustom("iCveModalidad", cSQL);
			TVDinRep dRegGrupo;
			if (vGrupos != null) {
				for (int i = 0; i < vGrupos.size(); i++) {
					dRegGrupo = (TVDinRep) vGrupos.get(i);
					vcGrupos.addElement(dRegGrupo.getString("cGrupo"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			cMensaje += e.getMessage();
		}
		return vcGrupos;
	}

	public HashMap entregaTramite(String cFiltro) throws Exception {
		HashMap hParametros = new HashMap();
		String[] aFiltros = cFiltro.split(",");
		String cSolicitudes = "";
		for (int i = 0; i < aFiltros.length; i++) {
			if (i == 0)
				hParametros.put("iEjercicio", aFiltros[i]);
			else
				cSolicitudes += cSolicitudes == "" ? aFiltros[i] : ","
						+ aFiltros[i];
		}
		hParametros.put("cFiltroSol", cSolicitudes);
		return hParametros;
	}

	public HashMap manualServiciosPublicoJasper(String cFiltro)
			throws Exception {
		HashMap hParametros = new HashMap();
		hParametros.put("URLFormatosEMar",
				VParametros.getPropEspecifica("URLFormatosEMar"));
		hParametros.put("URLDespliegaFormato",
				VParametros.getPropEspecifica("URLDespliegaFormato"));
		hParametros.put("cTramiteFiltro", "false");
		return hParametros;
	}

	public HashMap manualServiciosPublicoJasperF(String cFiltro)
			throws Exception {
		HashMap hParametros = new HashMap();
		hParametros.put("URLFormatosEMar",
				VParametros.getPropEspecifica("URLFormatosEMar"));
		hParametros.put("URLDespliegaFormato",
				VParametros.getPropEspecifica("URLDespliegaFormato"));
		hParametros.put("cCveTramiteFiltro", cFiltro);
		hParametros.put("cTramiteFiltro", "true");
		return hParametros;
	}

	public HashMap etapasTramiteJasper(String cFiltro) throws Exception {
		HashMap hParametros = new HashMap();
		return hParametros;
	}

	public HashMap empresasRepJasper(String cFiltro) throws Exception {
		HashMap hParametros = new HashMap();
		hParametros.put("iCvePersona", new Double(cFiltro));
		return hParametros;
	}

	public HashMap repEmpresaJasper(String cFiltro) throws Exception {
		HashMap hParametros = new HashMap();
		hParametros.put("iCveEmpresa", new Double(cFiltro));
		return hParametros;
	}

	public HashMap tramiteCanceladoJasper(String cFiltro) throws Exception {
		TDTRARegTramXSol dTRARegTramXSol = new TDTRARegTramXSol();
		HashMap hParametros = new HashMap();
		String[] cDatosFiltro = cFiltro.split(",");
		String cSolicitudes = cDatosFiltro[1];
		if (cDatosFiltro.length == 2) {
			String cSQLSearchRel = "SELECT SR.iEjercicio, SR.iNumSolicitudPrinc, SR.iNumSolicitudRel "
					+ "FROM TRASolicitudRel SR "
					+ "WHERE SR.iEjercicio = "
					+ cDatosFiltro[0]
					+ " "
					+ "  AND SR.iNumSolicitudPrinc = "
					+ cDatosFiltro[1]
					+ " "
					+ "UNION "
					+ "SELECT SR.iEjercicio, SR.iNumSolicitudPrinc, SR.iNumSolicitudRel "
					+ "FROM TRASolicitudRel SR "
					+ "WHERE SR.iEjercicio = "
					+ cDatosFiltro[0]
					+ " "
					+ "  AND SR.iNumSolicitudPrinc = (SELECT SR2.iNumSolicitudPrinc FROM TRASolicitudRel SR2 WHERE SR2.iEjercicio = SR.iEjercicio "
					+ "                                      AND SR2.iNumSolicitudRel = "
					+ cDatosFiltro[1]
					+ ") "
					+ "ORDER BY iEjercicio, iNumSolicitudPrinc, iNumSolicitudRel ";
			try {
				Vector vRelacionadas = dTRARegTramXSol.findByCustom("",
						cSQLSearchRel);
				if (vRelacionadas != null && vRelacionadas.size() > 0)
					for (int i = 0; i < vRelacionadas.size(); i++) {
						if (i == 0) {
							cSolicitudes = ((TVDinRep) vRelacionadas.get(i))
									.getString("iNumSolicitudPrinc");
						}
						cSolicitudes += ","
								+ ((TVDinRep) vRelacionadas.get(i))
										.getString("iNumSolicitudRel");
					}
				//System.out.print("+++++++cSolicitudes:" + cSolicitudes);
				/*
				 * if(!cSolicitudes.equals("")) cSolicitudes =
				 * cSolicitudes.substring(0,cSolicitudes.length() - 1);
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		hParametros.put("cEjercicioSolicitudes", cDatosFiltro[0]);
		// hParametros.put("cSolicitudesCanceladas",""+cSolicitudes);
		// hParametros.put("cSolicitudesCance",""+cSolicitudes);
		hParametros.put("cSolicitudesCanceladas", "" + cDatosFiltro[1]);
		hParametros.put("cSolicitudesCance", "" + cDatosFiltro[1]);

		return hParametros;
	}

	public StringBuffer reimpresionSolicitud(String cSolicitud)
			throws Exception {
		Vector vGenerales = new Vector(), vDerechos = new Vector(), vRequisitos = new Vector(), vOficResuelve = new Vector();
		cError = "";
		TDTRARegSolicitud regSolicitud = new TDTRARegSolicitud();
		int iUltimoSalto = 8, iNumRengXPag = 71, iTemp;
		int ilongBien = 0;
		int iRengBien = 0;
		int iRengConc = 0;
		int iRengGrupo = 0;
		int iLongReng = 137; // Número de caracteres por renglón
		int iRengBienPos = 0;
		int iRengConcPos = 0;
		int iRengGrpoPos = 0;
		int iLongConc = 0;
		int iLongGrupo = 0;
		String cSaltosPag = "";
		cLeyendaBien = VParametros.getPropEspecifica("LeyendaBien");
		cLeyendaPago = VParametros.getPropEspecifica("LeyendaPago");
		cLeyendaEval = VParametros.getPropEspecifica("LeyendaEvaluacion1");
		cLeyendaEval += " "
				+ VParametros.getPropEspecifica("LeyendaEvaluacion2");
		cNotaCapitania = VParametros.getPropEspecifica("NotaCapitania1");
		cNotaCapitania += " " + VParametros.getPropEspecifica("NotaCapitania2");
		int iRengIni = 10, iReng, iRengTemp, iRengImprocedente, iBorde = 1, iLineaFirma = 2, iFondoCeldaPrin = 37, iFondoEsp = 33, iFondoCeldaSec = -1, iLetrasXRenglonReq = 100, iLetrasXRenglonDer = 40;
		float fAltoRengIntermedio = 3.0f;
		String cFechaNula = "'- - - - - - - - - -", cTemp = "", cWhere, cOrder;
		StringBuffer sbRetorno = new StringBuffer("");
		TExcel rep = new TExcel();
		String cNomUsuRegistra, cDscDeptoRecibe, cDscOficinaRecibe;
		boolean lSolicRepLegal = true, lOficinaResolDiferente = false, lImprocedente = false, lRelacionadoImprocedente = false;
		String cRenglonesImprocedentes = "", cSolicitudesImprocedentes = "", cTramiteImproc = "", cModalidadImproc = "";

		String[] aSolicitudes = cSolicitud.split("/");
		if (cMensaje.equals("")) {
			cNomUsuRegistra = cDscDeptoRecibe = cDscOficinaRecibe = "";
			try {
				rep.iniciaReporte();
				rep.comDespliega("A", 8, cNombreReporte);
				iReng = iRengIni;

				// Procesa todas las solicitudes
				for (int j = 0; j < aSolicitudes.length; j++) {
					String[] aSolicitud = aSolicitudes[j].split(",");
					if (aSolicitud.length != 2)
						cMensaje = "No se proporcionó ejercicio o solicitud";
					int iEjercicio = Integer.parseInt(aSolicitud[0], 10);
					int iNumSolicitud = Integer.parseInt(aSolicitud[1], 10);

					cWhere = "WHERE RS.iEjercicio = " + iEjercicio
							+ " AND RS.iNumSolicitud = " + iNumSolicitud;
					cOrder = "ORDER BY RS.iEjercicio, RS.iNumSolicitud";

					lSolicRepLegal = false;
					// Datos Generales
					TVDinRep regGenerales = tTramite.getDatosGenSolicitud(
							cWhere, cOrder, lSolicRepLegal);

					int iOficinaReg = regGenerales.getInt("iCveOficina");
					int iDeptoReg = regGenerales.getInt("iCveDepartamento");
					int iTramite = regGenerales.getInt("iCveTramite");
					int iModalidad = regGenerales.getInt("iCveModalidad");

					String cOficResuelve = regGenerales
							.getString("cDscBreveOfic");
					String cDeptoResuelve = regGenerales
							.getString("cDscBreveDepto");
					double dImporte = 0d;
					java.sql.Date dtTemp;
					if (j == 0) {
						cNomUsuRegistra = regGenerales
								.getString("cNomUsuRegistra");
						cDscDeptoRecibe = regGenerales
								.getString("cDscDepartamento");
						cDscOficinaRecibe = regGenerales
								.getString("cDscOficina");

						// Datos del solicitante
						rep.comDespliegaCombinado("DATOS DEL SOLICITANTE", "B",
								iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						iReng++;
						iRengTemp = iReng;
						rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								regGenerales.getString("cNomSolicitante"),
								false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "R.F.C.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cRFCSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("S", iReng, "Teléfono:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cTelefonoSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						cTemp = regGenerales.getString("cCalleSol") + " No. "
								+ regGenerales.getString("cNumExteriorSol");
						if (!regGenerales.getString("cNumInteriorSol").equals(
								""))
							cTemp += " Int. "
									+ regGenerales.getString("cNumInteriorSol");
						rep.comDespliegaAlineado("E", iReng, "Domicilio:",
								true, rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								cTemp, false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "Correo-E:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng + 1,
								regGenerales.getString("cCorreoESol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("T", iReng, "W", iReng + 1,
								rep.getAT_HJUSTIFICA());
						iReng += 2;
						rep.comDespliegaAlineado("E", iReng, "Colonia:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng,
								regGenerales.getString("cColoniaSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cCodPostalSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						iReng++;
						rep.comDespliegaAlineado("F", iReng,
								"Ciudad/Municipio:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("G", iReng, "O", iReng,
								regGenerales.getString("cNomMunicipioSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("Q", iReng, "Entidad:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("R", iReng, "W", iReng,
								regGenerales.getString("cNomEntidadSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comBordeRededor("B", iRengTemp, "X", iReng, iBorde,
								1);

						// Datos del representante legal si es que existe
						cTemp = regGenerales.getString("iCveRepLegal");
						if (Integer.parseInt(cTemp, 10) > 0) {
							iReng++;
							rep.comAltoReng("A", iReng, fAltoRengIntermedio);
							iReng++;
							rep.comDespliegaCombinado(
									"DATOS DEL REPRESENTANTE LEGAL", "B",
									iReng, "X", iReng,
									rep.getAT_COMBINA_CENTRO(), "", true,
									iFondoCeldaPrin, true, true, iBorde, 1);
							iReng++;
							iRengTemp = iReng;
							rep.comDespliegaAlineado("E", iReng, "Nombre:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1,
									regGenerales.getString("cNomRepLegal"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "R.F.C.:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cRFCRep"), false,
									rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							rep.comDespliegaAlineado("S", iReng, "Teléfono:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cTelefonoRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							cTemp = regGenerales.getString("cCalleRep")
									+ " No. "
									+ regGenerales.getString("cNumExteriorRep");
							if (!regGenerales.getString("cNumInteriorRep")
									.equals(""))
								cTemp += " Int. "
										+ regGenerales
												.getString("cNumInteriorRep");
							rep.comDespliegaAlineado("E", iReng, "Domicilio:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1, cTemp, false,
									rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "Correo-E:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W",
									iReng + 1,
									regGenerales.getString("cCorreoERep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("T", iReng, "W", iReng + 1,
									rep.getAT_HJUSTIFICA());
							iReng += 2;
							rep.comDespliegaAlineado("E", iReng, "Colonia:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q", iReng,
									regGenerales.getString("cColoniaRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cCodPostalRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							iReng++;
							rep.comDespliegaAlineado("F", iReng,
									"Ciudad/Municipio:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("G", iReng, "O", iReng,
									regGenerales.getString("cNomMunicipioRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("Q", iReng, "Entidad:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("R", iReng, "W", iReng,
									regGenerales.getString("cNomEntidadRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comBordeRededor("B", iRengTemp, "X", iReng,
									iBorde, 1);
						}
					}

					if (!lOficinaResolDiferente) {
						cWhere = "WHERE TXO.iCveOficina = " + iOficinaReg
								+ "  AND TXO.iCveTramite = " + iTramite;
						cOrder = "ORDER BY TXO.iCveOficina, TXO.iCveTramite";
						vOficResuelve = tTramite.getDatosOficinaResolucion(
								cWhere, cOrder);
						if (vOficResuelve != null && vOficResuelve.size() > 0) {
							TVDinRep regOficResuelve = (TVDinRep) vOficResuelve
									.get(0);
							if (iOficinaReg != regOficResuelve
									.getInt("iCveOficinaResuelve")) {
								cOficResuelve = regOficResuelve
										.getString("cDscBreveOfic");
								lOficinaResolDiferente = true;
							}
							if (iDeptoReg != regOficResuelve
									.getInt("iCveDepartamento"))
								cDeptoResuelve = regOficResuelve
										.getString("cDscBreveDepto");
						}
					}

					// Datos del trámite
					iReng++;
					rep.comAltoReng("A", iReng, fAltoRengIntermedio);
					iReng++;
					if (regGenerales.getInt("iIdCita") == 0)
						rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B",
								iReng, "O", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
					else {
						rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B",
								iReng, "J", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						rep.comDespliegaCombinado("No. CIS:", "K", iReng, "L",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaPrin, true, true, iBorde, 1);
						rep.comDespliegaCombinado(
								regGenerales.getString("iIdCita"), "M", iReng,
								"O", iReng, rep.getAT_COMBINA_DERECHA(), "",
								false, iFondoEsp, true, true, iBorde, 2);
					}
					rep.comDespliegaCombinado("Ejercicio:", "P", iReng, "Q",
							iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);

					rep.comFontBold("R", iReng, "X", iReng);
					rep.comFontColor("R", iReng, "R", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iEjercicio"), "R", iReng,
							"R", iReng, rep.getAT_COMBINA_DERECHA(), "", false,
							iFondoEsp, true, true, iBorde, 2);
					rep.comDespliegaCombinado("Solicitud No.:", "S", iReng,
							"U", iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);
					rep.comFontColor("V", iReng, "X", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iNumSolicitud"), "V",
							iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(), "",
							false, iFondoEsp, true, true, iBorde, 2);
					iReng++;
					iRengTemp = iReng;
					rep.comDespliegaAlineado("E", iReng, "Clave:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "H", iReng,
							regGenerales.getString("cCveInterna"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iRengImprocedente = iReng;
					cRenglonesImprocedentes += iReng + ",";
					cSolicitudesImprocedentes += regGenerales
							.getString("iNumSolicitud") + ",";
					cTramiteImproc += regGenerales.getString("iCveTramite")
							+ ",";
					cModalidadImproc += regGenerales.getString("iCveModalidad")
							+ ",";
					iReng++;
					rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "P", iReng + 3,
							regGenerales.getString("cDscTramite"), false,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR());
					rep.comAlineaRango("F", iReng, "P", iReng + 3,
							rep.getAT_HJUSTIFICA());

					rep.comDespliegaAlineado("R", iReng, "Modalidad:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("S", iReng, "X", iReng,
							regGenerales.getString("cDscModalidad"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;

					rep.comDespliegaAlineado("T", iReng, "Fecha de Solicitud:",
							true, rep.getAT_HDERECHA(), "");
					java.sql.Timestamp tsTemp = regGenerales
							.getTimeStamp("tsRegistro");
					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(
									tFecha.getDateSQL(tsTemp), "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng += 1;
					rep.comDespliegaAlineado("T", iReng, "Hora de Solicitud:",
							true, rep.getAT_HDERECHA(), "");

					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getStringHoraHHMM_24(tsTemp, ":");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");

					iReng += 1;
					int iRengLimCubDocs = iReng;
					rep.comDespliegaAlineado("T", iReng,
							"Fecha Lím. Cubrir Doc.:", true,
							rep.getAT_HDERECHA(), "");
					dtTemp = regGenerales.getDate("dtLimiteEntregaDocs");
					cTemp = (dtTemp == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					rep.comDespliegaAlineado("T", iReng, "Fecha de Respuesta:",
							true, rep.getAT_HDERECHA(), "");
					cTemp = (regGenerales.getDate("DTESTIMADAENTREGA") == null) ? cFechaNula
							: "'"
									+ tFecha.getFechaDDMMMYYYY(regGenerales
											.getDate("DTESTIMADAENTREGA"), "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					if (!cTemp.equals(cFechaNula)) {
						rep.comDespliega("T", iRengLimCubDocs, "");
						rep.comDespliega("U", iRengLimCubDocs, "");
					}
					iReng++;
					rep.comDespliegaAlineado("F", iReng,
							"Oficina de Resolución:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("G", iReng, "W", iReng,
							cOficResuelve, false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					rep.comDespliegaAlineado("F", iReng, "Departamento:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("G", iReng, "W", iReng,
							cDeptoResuelve, false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					rep.comDespliegaAlineado("B", iReng, "G", iReng,
							"Aplicar Trámite al Bien Núm.:", true,
							rep.getAT_COMBINA_DERECHA(), "");
					iTemp = regGenerales.getInt("iIdBien");
					rep.comDespliegaAlineado("H", iReng, "I", iReng,
							(iTemp > 0) ? iTemp + "" : "'- - -", false,
							rep.getAT_COMBINA_CENTRO(), "");
					cTemp = regGenerales.getString("cDscBien");
					if (cTemp == null || cTemp.equals("")
							|| cTemp.equalsIgnoreCase("null"))
						cTemp = "NO SE ESPECIFICÓ ESTA INFORMACIÓN";
					rep.comDespliegaAlineado("J", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					rep.comAlineaRangoVer("B", iReng, "X", iReng,
							rep.getAT_VSUPERIOR());
					rep.comBordeRededor("B", iRengTemp, "X", iReng, iBorde, 1);
					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 2;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					// Datos de Derechos del Trámite
					iReng++;
					rep.comAltoReng("A", iReng, fAltoRengIntermedio);
					iReng++;
					rep.comDespliegaCombinado("DERECHOS DEL TRÁMITE", "B",
							iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(), "",
							true, iFondoCeldaSec, true, true, iBorde, 1);
					iReng++;
					cWhere = "WHERE RRP.iEjercicio = " + iEjercicio
							+ "  AND RRP.iNumSolicitud = " + iNumSolicitud;
					cOrder = "ORDER BY RRP.iEjercicio, RRP.iNumSolicitud, RRP.dtPago, RRP.iRefNumerica";
					vDerechos = tTramite.getDatosDerechosSolicitud(cWhere,
							cOrder);
					if (vDerechos != null && vDerechos.size() > 0) { // Datos de
																		// derechos
						TLetterNumberFormat numLetras = new TLetterNumberFormat();
						double dImportePagado = 0d;
						rep.comDespliegaCombinado("Unid.", "B", iReng, "C",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Ref. Núm.", "D", iReng, "E",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Imp. Pagar", "F", iReng,
								"G", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Fecha Pago", "H", iReng,
								"I", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Imp. Pagado", "J", iReng,
								"K", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Ref. AlfaNúm.", "L", iReng,
								"N", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Banco", "O", iReng, "P",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Total", "Q", iReng, "X",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);

						TVDinRep regPago;
						iRengTemp = iReng + 1;
						boolean lPagado;
						int iRenglonesDer;
						for (int i = 0; i < vDerechos.size(); i++) {
							iReng++;
							regPago = (TVDinRep) vDerechos.get(i);
							cTemp = regPago.getString("cDscConcepto");
							iRenglonesDer = 2;
							lPagado = (regPago.getInt("lPagado") == 1) ? true
									: false;
							cTemp = (regPago.getInt("iNumUnidades") == 0) ? "'- - -"
									: regPago.getString("iNumUnidades");
							rep.comDespliegaAlineado("B", iReng, "C", iReng
									+ iRenglonesDer - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(), "");
							rep.comDespliegaAlineado("D", iReng, "E", iReng
									+ iRenglonesDer - 1,
									regPago.getString("iRefNumerica"), false,
									rep.getAT_COMBINA_CENTRO(), "");
							dImporte = regPago.getDouble("dImportePagar");
							cTemp = (dImporte == 0d) ? cFechaNula : ""
									+ dImporte;
							rep.comDespliegaAlineado("F", iReng, "G", iReng
									+ iRenglonesDer - 1, cTemp, false,
									rep.getAT_COMBINA_DERECHA(), "");
							dtTemp = regPago.getDate("dtPago");
							cTemp = (dtTemp == null || !lPagado) ? cFechaNula
									: "'"
											+ tFecha.getFechaDDMMMYYYY(dtTemp,
													"/");
							rep.comDespliegaAlineado("H", iReng, "I", iReng
									+ iRenglonesDer - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(), "");
							dImporte = regPago.getDouble("dImportePagado");
							dImportePagado += dImporte;
							cTemp = (dImporte == 0d || !lPagado) ? cFechaNula
									: "" + dImporte;
							rep.comDespliegaAlineado("J", iReng, "K", iReng
									+ iRenglonesDer - 1, cTemp, false,
									rep.getAT_COMBINA_DERECHA(), "");
							cTemp = regPago.getString("cRefAlfaNum");
							if (cTemp == null)
								cTemp = regPago.getString("cFormatoSAT");
							cTemp = (cTemp == null || !lPagado) ? cFechaNula
									: "'" + cTemp;
							rep.comDespliegaAlineado("L", iReng, "N", iReng
									+ iRenglonesDer - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(), "");
							cTemp = regPago.getString("cDscBanco");
							cTemp = (cTemp == null || !lPagado) ? cFechaNula
									: cTemp;
							rep.comDespliegaAlineado("O", iReng, "P", iReng
									+ iRenglonesDer - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(), "");
							rep.comCellFormat("D", iReng, "E", iReng
									+ iRenglonesDer - 1, "00000000");
							rep.comCellFormat("F", iReng, "G", iReng
									+ iRenglonesDer - 1, "$ #,##0.00");
							rep.comCellFormat("J", iReng, "K", iReng
									+ iRenglonesDer - 1, "$ #,##0.00");
							rep.comAlineaRango("O", iReng, "P", iReng
									+ iRenglonesDer - 1, rep.getAT_HIZQUIERDA());
							rep.comAlineaRango("Q", iReng, "X", iReng
									+ iRenglonesDer - 1, rep.getAT_HJUSTIFICA());
							rep.comAlineaRangoVer("B", iReng, "X", iReng
									+ iRenglonesDer - 1, rep.getAT_VSUPERIOR());
							rep.comBordeRededor("B", iReng, "C", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							rep.comBordeRededor("D", iReng, "E", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							rep.comBordeRededor("F", iReng, "G", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							rep.comBordeRededor("H", iReng, "I", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							rep.comBordeRededor("J", iReng, "K", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							rep.comBordeRededor("L", iReng, "N", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							rep.comBordeRededor("O", iReng, "P", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							rep.comBordeRededor("Q", iReng, "X", iReng
									+ iRenglonesDer - 1, iBorde, 1);
							iReng += iRenglonesDer - 1;
						}
						rep.comDespliegaCombinado("", "S", iReng, "U", iReng,
								rep.getAT_COMBINA_DERECHA(), "", true,
								iFondoCeldaSec, true, false, iBorde, 0);
						rep.comAlineaRangoVer("R", iReng, "X", iReng + 1,
								rep.getAT_VSUPERIOR());
						rep.comCreaFormula("S", iReng, "=SUM(F" + iRengTemp
								+ ":G" + (iReng - 1) + ")");

						iReng++;
					}

					ilongBien = cLeyendaBien.length();
					iRengBien = 0;
					while (ilongBien > 0) {
						ilongBien = ilongBien - iLongReng;
						iRengBien += 1;
					}
					iRengBienPos = iReng;
					rep.comDespliegaCombinado(cLeyendaBien, "B", iReng, "X",
							iReng + iRengBien - 1,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR(), false, iFondoCeldaSec,
							false, false, iBorde, 1);
					rep.comAlineaRango("B", iReng, "X", iReng + iRengBien - 1,
							rep.getAT_HJUSTIFICA());
					iReng += iRengBien - 1;
					iReng++;
					rep.comAltoReng("A", iReng, fAltoRengIntermedio);
					iReng++;
					rep.comDespliegaCombinado("CONCEPTOS DE PAGO", "B", iReng,
							"X", iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaSec, true, true, iBorde, 1);
					iReng++;
					String cConceptos = fBuscaConceptos(iEjercicio, iTramite,
							iModalidad);
					iLongConc = cLeyendaBien.length();
					iRengConc = 0;
					while (iLongConc > 0) {
						iLongConc = iLongConc - iLongReng;
						iRengConc += 1;
					}
					iRengConcPos = iReng;
					rep.comDespliegaCombinado(cConceptos, "B", iReng, "X",
							iReng + iRengConc - 1,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR(), false, iFondoCeldaSec,
							false, false, iBorde, 1);
					rep.comAlineaRango("B", iReng, "X", iReng + iRengConc - 1,
							rep.getAT_HJUSTIFICA());
					iReng += iRengConc - 1;

					iReng++;
					Vector vGrpCobro = new Vector();
					vGrpCobro = fBuscaGrupos(iEjercicio, iTramite, iModalidad);
					for (int k = 0; k < vGrpCobro.size(); k++) {
						iLongGrupo = (String.valueOf(vGrpCobro.elementAt(k)))
								.length();
						iRengGrupo = 0;
						while (iLongGrupo > 0) {
							iLongGrupo = iLongGrupo - iLongReng;
							iRengGrupo += 1;
						}
						iRengGrpoPos = iReng;
						rep.comDespliegaCombinado(String.valueOf(k + 1) + ". "
								+ String.valueOf(vGrpCobro.elementAt(k)), "B",
								iReng, "X", iReng,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR(), false, iFondoCeldaSec,
								false, false, iBorde, 1);
						rep.comAlineaRango("B", iReng, "X", iReng,
								rep.getAT_HJUSTIFICA());
						iReng++;

					}
					iReng--;
					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 3;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					// Datos de Requisitos del Trámite
					cWhere = "WHERE RRT.iEjercicio = " + iEjercicio
							+ "  AND RRT.iNumSolicitud = " + iNumSolicitud
							+ "  AND RRT.iCveTramite = " + iTramite
							+ "  AND RRT.iCveModalidad = " + iModalidad;
					cOrder = "ORDER BY RRT.dtRecepcion DESC, RMT.lRequerido DESC, RMT.iOrden, R.iCveRequisito";
					int iRenglones;
					vRequisitos = tTramite.getDatosRequisitosSolicitud(cWhere,
							cOrder);
					if (vRequisitos != null && vRequisitos.size() > 0) { // Datos
																			// de
																			// Requisitos
						iReng++;
						rep.comAltoReng("A", iReng, fAltoRengIntermedio);
						iReng++;
						rep.comDespliegaCombinado("REQUISITOS", "B", iReng,
								"X", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						iReng++;
						rep.comDespliegaCombinado("Recibido", "B", iReng, "E",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Obl.", "F", iReng, "F",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Descripción", "G", iReng,
								"X", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);

						iRengTemp = iReng + 1;
						TVDinRep regRequisito;
						lImprocedente = false;
						boolean lUsuEncontrado = false;
						for (int i = 0; i < vRequisitos.size(); i++) {
							iReng++;
							regRequisito = (TVDinRep) vRequisitos.get(i);
							cTemp = regRequisito.getString("cDscRequisito");
							iRenglones = (int) (cTemp.length() / iLetrasXRenglonReq);
							if (cTemp.length() % iLetrasXRenglonReq > 0)
								iRenglones++;
							dtTemp = regRequisito.getDate("dtRecepcion");
							if (regRequisito.getDate("dtRecepcion") != null
									&& !lUsuEncontrado) {
								cNomUsuRegistra = regRequisito
										.getString("cNomUsuRecibe");
								lUsuEncontrado = true;
							}
							cTemp = (dtTemp == null) ? "NO ENTREGADO" : "'"
									+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
							rep.comDespliegaAlineado("B", iReng, "E", iReng
									+ iRenglones - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(),
									rep.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("F", iReng, "F", iReng
									+ iRenglones - 1, (regRequisito
									.getInt("lRequerido") == 1) ? "SI" : "NO",
									false, rep.getAT_COMBINA_CENTRO(), rep
											.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("G", iReng, "X", iReng
									+ iRenglones - 1,
									regRequisito.getString("cDscRequisito"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("H", iReng, "X", iReng
									+ iRenglones - 1, rep.getAT_HJUSTIFICA());
							rep.comBordeRededor("B", iReng, "E", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("F", iReng, "F", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("G", iReng, "G", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("H", iReng, "X", iReng
									+ iRenglones - 1, iBorde, 1);
							iReng += iRenglones - 1;
						}

						if (iReng >= (iUltimoSalto + iNumRengXPag)) {
							iUltimoSalto = iRengTemp - 2;
							cSaltosPag += (cSaltosPag.equals("")) ? ""
									+ iUltimoSalto : "," + iUltimoSalto;
						}
					}
				}
				iReng++;
				rep.comAltoReng("A", iReng, fAltoRengIntermedio);
				iReng++;
				rep.comDespliegaCombinado("RECIBE", "B", iReng, "L", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				rep.comDespliegaCombinado("SELLO", "R", iReng, "X", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				iReng++;
				iRengTemp = iReng;
				rep.comBordeRededor("B", iReng, "L", iReng + 2, iLineaFirma, 1);
				rep.comBordeRededor("B", iReng, "L", iReng + 5, iBorde, 1);
				rep.comBordeRededor("R", iReng, "X", iReng + 10, iBorde, 1);
				iReng += 3;
				rep.comDespliegaCombinado(cNomUsuRegistra, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				iReng++;
				rep.comDespliegaCombinado(cDscDeptoRecibe, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				iReng++;
				rep.comDespliegaCombinado(cDscOficinaRecibe, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				if (lOficinaResolDiferente) {
					iReng += 2;
					rep.comDespliegaCombinado(cNotaCapitania, "B", iReng, "P",
							iReng + 3, rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR(), false, -1, false, false, 0,
							0);
					rep.comAlineaRango("B", iReng, "P", iReng + 3,
							rep.getAT_HJUSTIFICA());
					iReng += 3;
				}
				// LEL12102006
				iReng += 7;
				int ilongPago = cLeyendaPago.length();
				int iRengPago = 0;
				while (ilongPago > 0) {
					ilongPago = ilongPago - iLongReng;
					iRengPago += 1;
				}
				rep.comDespliegaCombinado(cLeyendaPago, "B", iReng, "X", iReng
						+ iRengPago - 1, rep.getAT_COMBINA_IZQUIERDA(),
						rep.getAT_VSUPERIOR(), false, iFondoCeldaSec, false,
						false, iBorde, 1);
				rep.comAlineaRango("B", iReng, "X", iReng + iRengPago - 1,
						rep.getAT_HJUSTIFICA());
				iReng += iRengPago + 1;

				int ilongEvalua = cLeyendaEval.length();
				int iRengEvalua = 0;
				while (ilongEvalua > 0) {
					ilongEvalua = ilongEvalua - iLongReng;
					iRengEvalua += 1;
				}
				rep.comDespliegaCombinado(cLeyendaEval, "B", iReng, "X", iReng
						+ iRengEvalua - 1, rep.getAT_COMBINA_IZQUIERDA(),
						rep.getAT_VSUPERIOR(), false, iFondoCeldaSec, false,
						false, iBorde, 1);
				rep.comAlineaRango("B", iReng, "X", iReng + iRengEvalua - 1,
						rep.getAT_HJUSTIFICA());
				iReng += iRengEvalua - 1;
				// FinLEL12102006

				if (iReng >= (iUltimoSalto + iNumRengXPag)) {
					iUltimoSalto = iRengTemp - 2;
					cSaltosPag += (cSaltosPag.equals("")) ? "" + iUltimoSalto
							: "," + iUltimoSalto;
				}

				if (!cSaltosPag.equals(""))
					rep.comSaltosPagina(cSaltosPag);
				sbRetorno = rep.getSbDatos();
			} catch (Exception e) {
				e.printStackTrace();
				cMensaje += e.getMessage();
			}
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

	// ///////////////////////// PARAMETROS REPORTES ADV ///////////////////////

	public HashMap paramReporte() throws Exception {

		HashMap hParametros = new HashMap();
		// String[] cDatosFiltro = cFiltro.split(",");
		//
		// hParametros.put("cEjercicio", cDatosFiltro[0]);
		// hParametros.put("cSolicitud", cDatosFiltro[1]);

		return hParametros;
	}

	public HashMap paramConstanciaNoAfectacion(String cFiltro) throws Exception {

		HashMap hParametros = new HashMap();
		String[] cDatosFiltro = cFiltro.split(",");
		hParametros.put("cEjercicio", cDatosFiltro[0]);
		hParametros.put("cSolicitud", cDatosFiltro[1]);
		hParametros.put("cLeyendaEnc", VParametros.getPropEspecifica("LeyendaEnc"));

		return hParametros;
	}
	
	public HashMap paramAcuseSol_Impreso(String cFiltro) throws Exception {

		HashMap hParametros = new HashMap();
		String[] cDatosFiltro = cFiltro.split(",");
		hParametros.put("cEjercicio", cDatosFiltro[0]);
		hParametros.put("cSolicitud", cDatosFiltro[1]);
		hParametros.put("cLeyendaEnc", VParametros.getPropEspecifica("LeyendaEnc"));
		
//		TDTRARegSolicitud regSolicitud = new TDTRARegSolicitud();	
//		try {
//			regSolicitud.fechaImpresion(Integer.parseInt(cDatosFiltro[0]),
//					Integer.parseInt(cDatosFiltro[1]), conn);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}

		return hParametros;
	}
	
	
	public HashMap paramEstadistico(String cFiltro) throws Exception {
		
		HashMap hParametros = new HashMap();				
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		String[] cDatosFiltro = cFiltro.split(",");
		String ini="",end="";
		
		String strIni = null, strFin = null; 
		Date tsDtIni = null, tsDtFin = null; 
		Timestamp tsIni=null,tsFin = null;
		try{ 	
			
			strIni = cDatosFiltro[0] + " 00:00:00";
			strFin = cDatosFiltro[1] +" 23:59:59";
			
			tsDtIni = sdf.parse(strIni);
			tsDtFin = sdf.parse(strFin);
			
			tsIni = new Timestamp(tsDtIni.getTime());
			tsFin = new Timestamp(tsDtFin.getTime());
		
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("Error, parametros incorrectos.");
		}
				
		hParametros.put("sDtIniO", cDatosFiltro[0]);
		hParametros.put("sDtFinO", cDatosFiltro[1]);
		
		hParametros.put("dtIni", tsDtIni);
		hParametros.put("dtFin", tsDtFin);
		
		hParametros.put("tsIni", tsIni);
		hParametros.put("tsFin", tsFin);
				
		return hParametros;
	}

	public HashMap paraMemo2(String cFiltro) throws Exception {

		HashMap hParametros = new HashMap();
		String[] cDatosFiltro = cFiltro.split(",");

		hParametros.put("cEjercicio", cDatosFiltro[0]);
		hParametros.put("cSolicitud", cDatosFiltro[1]);
		hParametros.put("cLeyendaEnc", VParametros.getPropEspecifica("LeyendaEnc"));

		return hParametros;
	}

	public HashMap paramNotifPNC2015(String cFiltro) throws Exception {

		HashMap hParametros = new HashMap();
		String[] cDatosFiltro = cFiltro.split(",");

		hParametros.put("cEjercicio", cDatosFiltro[0]);
		hParametros.put("cSolicitud", cDatosFiltro[1]);
		hParametros.put("cLeyendaEnc", VParametros.getPropEspecifica("LeyendaEnc"));
		hParametros.put("SUBREPORT_DIR",
				VParametros.getPropEspecifica("URLReportes"));
		return hParametros;
	}

	// ACUSE DE SOLICITUD ADV

	public StringBuffer acuseReciboModificacionTramiteA(String cSolicitud)
			throws Exception {
		Vector vGenerales = new Vector(), vDerechos = new Vector(), vRequisitos = new Vector(), vOficResuelve = new Vector();
		cError = "";
		TDTRARegSolicitud regSolicitud = new TDTRARegSolicitud();
		int iUltimoSalto = 8, iNumRengXPag = 71, iTemp;
		int ilongBien = 0;
		int iRengBien = 0;
		int iRengConc = 0;
		int iRengGrupo = 0;
		int iLongReng = 137; // Número de caracteres por renglón
		int iRengBienPos = 0;
		int iRengConcPos = 0;
		int iRengGrpoPos = 0;
		int iLongConc = 0;
		int iLongGrupo = 0;

		String cSaltosPag = "";
		cLeyendaBien = VParametros.getPropEspecifica("LeyendaBien");
		cLeyendaPago = VParametros.getPropEspecifica("LeyendaPago");

		cLeyendaEval = VParametros.getPropEspecifica("LeyendaEvaluacionADV");

		cNotaCapitania = VParametros.getPropEspecifica("NotaCapitania1");
		cNotaCapitania += " " + VParametros.getPropEspecifica("NotaCapitania2");
		int iRengIni = 10, iReng, iRengTemp, iRengImprocedente, iBorde = 1, iLineaFirma = 2, iFondoCeldaPrin = 37, iFondoEsp = 33, iFondoCeldaSec = -1, iLetrasXRenglonReq = 100, iLetrasXRenglonDer = 40;
		float fAltoRengIntermedio = 3.0f;
		String cFechaNula = "'- - - - - - - - - -", cTemp = "", cWhere, cOrder;
		StringBuffer sbRetorno = new StringBuffer("");
		TExcel rep = new TExcel();
		String cNomUsuRegistra, cDscDeptoRecibe, cDscOficinaRecibe;
		boolean lSolicRepLegal = true, lOficinaResolDiferente = false, lImprocedente = false, lRelacionadoImprocedente = false;
		String cRenglonesImprocedentes = "", cSolicitudesImprocedentes = "", cTramiteImproc = "", cModalidadImproc = "";

		String[] aSolicitudes = cSolicitud.split("/");
		if (cMensaje.equals("")) {
			cNomUsuRegistra = cDscDeptoRecibe = cDscOficinaRecibe = "";
			try {
				rep.iniciaReporte();

				rep.comDespliega("A", 8, cNombreReporte);

				String nomEntQueryA = "SELECT ENTOF.CNOMBRE FROM TRAREGSOLICITUD RS "
						+ "JOIN GRLOFICINA ON RS.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
						+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = 1 ";

				iReng = iRengIni;

				// Procesa todas las solicitudes
				for (int j = 0; j < aSolicitudes.length; j++) {
					String[] aSolicitud = aSolicitudes[j].split(",");
					if (aSolicitud.length != 2)
						cMensaje = "No se proporcionó ejercicio o solicitud";
					int iEjercicio = Integer.parseInt(aSolicitud[0], 10);
					int iNumSolicitud = Integer.parseInt(aSolicitud[1], 10);

					cWhere = "WHERE RS.iEjercicio = " + iEjercicio
							+ " AND RS.iNumSolicitud = " + iNumSolicitud;
					cOrder = "ORDER BY RS.iEjercicio, RS.iNumSolicitud";

					String nomEntQuery = "SELECT "
							+ "ADV.CKMSENTIDO as COLA, "
							+ "ADV.CHECHOS as COLB, "
							+ "CARR.CDSCARRETERA as COLC, "
							+ "ENTOF.CNOMBRE AS COLD "
							+ "FROM TRAREGSOLICITUD RS "
							+ "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = RS.ICVESOLICITANTE "
							+ "JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = RS.ICVESOLICITANTE AND GRLDOMICILIO.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE "
							+ "JOIN TRAREGDATOSADVXSOL ADV ON RS.IEJERCICIO = ADV.IEJERCICIO AND RS.INUMSOLICITUD = ADV.INUMSOLICITUD "
							+ "JOIN GRLOFICINA ON RS.ICVEOFICINA = GRLOFICINA.ICVEOFICINA "
							+ "JOIN GRLENTIDADFED ENTOF ON GRLOFICINA.ICVEENTIDADFED = ENTOF.ICVEENTIDADFED AND ENTOF.ICVEPAIS = GRLDOMICILIO.ICVEPAIS "
							+ "JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = ADV.ICVECARRETERA ";

					Vector vNomEnt = new Vector();
					vNomEnt = this.findByCustom("", nomEntQuery + cWhere);

					TVDinRep vdNomEnt = new TVDinRep();

					if (vNomEnt.size() > 0)
						vdNomEnt = (TVDinRep) vNomEnt.get(0);

					rep.comDespliegaAlineado("L", 1,
							vdNomEnt.getString("COLD"), true,
							rep.getAT_HIZQUIERDA(), "");

					lSolicRepLegal = false;
					// Datos Generales
					TVDinRep regGenerales = tTramite.getDatosGenSolicitud(
							cWhere, cOrder, lSolicRepLegal);

					if (regGenerales.getString("DTIMPRESION").equals("null"))
						try {
							regSolicitud.fechaImpresion(iEjercicio,
									iNumSolicitud, conn);
						} catch (Exception e) {
							e.printStackTrace();
						}

					// Registra la Fecha de compromiso de la solicitud.
					java.sql.Date dtTemp1 = regSolicitud.getFechaCompromiso(
							iEjercicio, iNumSolicitud, conn);

					int iOficinaReg = regGenerales.getInt("iCveOficina");
					int iDeptoReg = regGenerales.getInt("iCveDepartamento");
					int iTramite = regGenerales.getInt("iCveTramite");
					int iModalidad = regGenerales.getInt("iCveModalidad");
					if (regGenerales.getInt("lPrincipal") == 1) {
						lRelacionadoImprocedente = false;
						cRenglonesImprocedentes = "";
						cSolicitudesImprocedentes = "";
						cTramiteImproc = "";
						cModalidadImproc = "";
					}

					String cOficResuelve = regGenerales
							.getString("cDscBreveOfic");
					String cDeptoResuelve = regGenerales
							.getString("cDscBreveDepto");
					double dImporte = 0d;
					java.sql.Date dtTemp;
					if (j == 0) {
						cNomUsuRegistra = regGenerales
								.getString("cNomUsuRegistra");
						cDscDeptoRecibe = regGenerales
								.getString("cDscDepartamento");
						cDscOficinaRecibe = regGenerales
								.getString("cDscOficina");

						// Datos del solicitante
						rep.comDespliegaCombinado("DATOS DEL SOLICITANTE", "B",
								iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaPrin, true, true, iBorde,
								1);
						iReng++;
						iRengTemp = iReng;
						rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								regGenerales.getString("cNomSolicitante"),
								false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "R.F.C.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cRFCSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						rep.comDespliegaAlineado("S", iReng, "Teléfono:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cTelefonoSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						iReng++;
						cTemp = regGenerales.getString("cCalleSol") + " No. "
								+ regGenerales.getString("cNumExteriorSol");
						if (!regGenerales.getString("cNumInteriorSol").equals(
								""))
							cTemp += " Int. "
									+ regGenerales.getString("cNumInteriorSol");
						rep.comDespliegaAlineado("E", iReng, "Domicilio:",
								true, rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng + 1,
								cTemp, false, rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("F", iReng, "Q", iReng + 1,
								rep.getAT_HJUSTIFICA());
						rep.comDespliegaAlineado("S", iReng, "Correo-E:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng + 1,
								regGenerales.getString("cCorreoESol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						rep.comAlineaRango("T", iReng, "W", iReng + 1,
								rep.getAT_HJUSTIFICA());
						iReng += 2;
						rep.comDespliegaAlineado("E", iReng, "Colonia:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("F", iReng, "Q", iReng,
								regGenerales.getString("cColoniaSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("T", iReng, "W", iReng,
								regGenerales.getString("cCodPostalSol"), false,
								rep.getAT_COMBINA_IZQUIERDA(),
								rep.getAT_VSUPERIOR());
						iReng++;
						rep.comDespliegaAlineado("F", iReng,
								"Ciudad/Municipio:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("G", iReng, "O", iReng,
								regGenerales.getString("cNomMunicipioSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comDespliegaAlineado("Q", iReng, "Entidad:", true,
								rep.getAT_HDERECHA(), "");
						rep.comDespliegaAlineado("R", iReng, "W", iReng,
								regGenerales.getString("cNomEntidadSol"),
								false, rep.getAT_COMBINA_IZQUIERDA(), "");
						rep.comBordeRededor("B", iRengTemp, "X", iReng, iBorde,
								1);

						// Datos del representante legal si es que existe
						cTemp = regGenerales.getString("iCveRepLegal");

						if (cTemp != null && cTemp != ""
								&& !cTemp.equals("null")
								&& Integer.parseInt(cTemp, 10) > 0) {// agregado
																		// no se
																		// validaba
																		// cTemp
							iReng++;
							rep.comAltoReng("A", iReng, fAltoRengIntermedio);
							iReng++;
							rep.comDespliegaCombinado(
									"DATOS DEL REPRESENTANTE LEGAL", "B",
									iReng, "X", iReng,
									rep.getAT_COMBINA_CENTRO(), "", true,
									iFondoCeldaPrin, true, true, iBorde, 1);
							iReng++;
							iRengTemp = iReng;
							rep.comDespliegaAlineado("E", iReng, "Nombre:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1,
									regGenerales.getString("cNomRepLegal"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "R.F.C.:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cRFCRep"), false,
									rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							rep.comDespliegaAlineado("S", iReng, "Teléfono:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cTelefonoRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							iReng++;
							cTemp = regGenerales.getString("cCalleRep")
									+ " No. "
									+ regGenerales.getString("cNumExteriorRep");
							if (!regGenerales.getString("cNumInteriorRep")
									.equals(""))
								cTemp += " Int. "
										+ regGenerales
												.getString("cNumInteriorRep");
							rep.comDespliegaAlineado("E", iReng, "Domicilio:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q",
									iReng + 1, cTemp, false,
									rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("F", iReng, "Q", iReng + 1,
									rep.getAT_HJUSTIFICA());
							rep.comDespliegaAlineado("S", iReng, "Correo-E:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W",
									iReng + 1,
									regGenerales.getString("cCorreoERep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("T", iReng, "W", iReng + 1,
									rep.getAT_HJUSTIFICA());
							iReng += 2;
							rep.comDespliegaAlineado("E", iReng, "Colonia:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("F", iReng, "Q", iReng,
									regGenerales.getString("cColoniaRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("S", iReng, "C.P.:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("T", iReng, "W", iReng,
									regGenerales.getString("cCodPostalRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							iReng++;
							rep.comDespliegaAlineado("F", iReng,
									"Ciudad/Municipio:", true,
									rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("G", iReng, "O", iReng,
									regGenerales.getString("cNomMunicipioRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comDespliegaAlineado("Q", iReng, "Entidad:",
									true, rep.getAT_HDERECHA(), "");
							rep.comDespliegaAlineado("R", iReng, "W", iReng,
									regGenerales.getString("cNomEntidadRep"),
									false, rep.getAT_COMBINA_IZQUIERDA(), "");
							rep.comBordeRededor("B", iRengTemp, "X", iReng,
									iBorde, 1);
						}
					}

					if (!lOficinaResolDiferente) {
						cWhere = "WHERE TXO.iCveOficina = " + iOficinaReg
								+ "  AND TXO.iCveTramite = " + iTramite;
						cOrder = "ORDER BY TXO.iCveOficina, TXO.iCveTramite";
						vOficResuelve = tTramite.getDatosOficinaResolucion(
								cWhere, cOrder);
						if (vOficResuelve != null && vOficResuelve.size() > 0) {
							TVDinRep regOficResuelve = (TVDinRep) vOficResuelve
									.get(0);
							if (iOficinaReg != regOficResuelve
									.getInt("iCveOficinaResuelve")) {
								cOficResuelve = regOficResuelve
										.getString("cDscBreveOfic");
								lOficinaResolDiferente = true;
							}
							if (iDeptoReg != regOficResuelve
									.getInt("iCveDepartamento"))
								cDeptoResuelve = regOficResuelve
										.getString("cDscBreveDepto");
						}
					}

					// Datos del trámite
					iReng++;
					rep.comAltoReng("A", iReng, fAltoRengIntermedio);
					iReng++;
					// if (regGenerales.getInt("iIdCita") == 0)
					rep.comDespliegaCombinado("DATOS DEL TRÁMITE", "B", iReng,
							"O", iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);
					/*
					 * else { rep.comDespliegaCombinado("DATOS DEL TRÁMITE",
					 * "B", iReng, "J", iReng, rep.getAT_COMBINA_CENTRO(), "",
					 * true, iFondoCeldaPrin, true, true, iBorde, 1);
					 * rep.comDespliegaCombinado("No. CIS:", "K", iReng, "L",
					 * iReng, rep.getAT_COMBINA_CENTRO(), "", true,
					 * iFondoCeldaPrin, true, true, iBorde, 1);
					 * rep.comDespliegaCombinado(
					 * regGenerales.getString("iIdCita"), "M", iReng, "O",
					 * iReng, rep.getAT_COMBINA_DERECHA(), "", false, iFondoEsp,
					 * true, true, iBorde, 2); }
					 */
					rep.comDespliegaCombinado("Ejercicio:", "P", iReng, "Q",
							iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);

					rep.comFontBold("R", iReng, "X", iReng);
					rep.comFontColor("R", iReng, "R", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iEjercicio"), "R", iReng,
							"R", iReng, rep.getAT_COMBINA_DERECHA(), "", false,
							iFondoEsp, true, true, iBorde, 2);
					rep.comDespliegaCombinado("Solicitud No.:", "S", iReng,
							"U", iReng, rep.getAT_COMBINA_CENTRO(), "", true,
							iFondoCeldaPrin, true, true, iBorde, 1);
					rep.comFontColor("V", iReng, "X", iReng, 2);
					rep.comDespliegaCombinado(
							regGenerales.getString("iNumSolicitud"), "V",
							iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(), "",
							false, iFondoEsp, true, true, iBorde, 2);
					iReng++;
					iRengTemp = iReng;
					rep.comDespliegaAlineado("E", iReng, "Clave:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "H", iReng,
							regGenerales.getString("cCveInterna"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iRengImprocedente = iReng;
					cRenglonesImprocedentes += iReng + ",";
					cSolicitudesImprocedentes += regGenerales
							.getString("iNumSolicitud") + ",";
					cTramiteImproc += regGenerales.getString("iCveTramite")
							+ ",";
					cModalidadImproc += regGenerales.getString("iCveModalidad")
							+ ",";
					iReng++;
					rep.comDespliegaAlineado("E", iReng, "Nombre:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("F", iReng, "P", iReng + 3,
							regGenerales.getString("cDscTramite"), false,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR());
					rep.comAlineaRango("F", iReng, "P", iReng + 3,
							rep.getAT_HJUSTIFICA());

					/*
					 * rep.comDespliegaAlineado("F",iReng,"P",iReng,regGenerales.
					 * getString
					 * ("cDscBreve"),false,rep.getAT_COMBINA_IZQUIERDA(),"");
					 */
					rep.comDespliegaAlineado("R", iReng, "Tipo:", true,
							rep.getAT_HDERECHA(), "");
					rep.comDespliegaAlineado("S", iReng, "X", iReng,
							regGenerales.getString("cDscModalidad"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng++;
					/*
					 * rep.comDespliegaAlineado("F",iReng,"P",iReng +
					 * 3,regGenerales
					 * .getString("cDscTramite"),false,rep.getAT_COMBINA_IZQUIERDA
					 * (),rep.getAT_VSUPERIOR());
					 */

					rep.comDespliegaAlineado("T", iReng, "Fecha de Solicitud:",
							true, rep.getAT_HDERECHA(), "");
					java.sql.Timestamp tsTemp = regGenerales
							.getTimeStamp("tsRegistro");
					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getFechaDDMMMYYYY(
									tFecha.getDateSQL(tsTemp), "/");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");
					iReng += 1;
					rep.comDespliegaAlineado("T", iReng, "Hora de Solicitud:",
							true, rep.getAT_HDERECHA(), "");

					/*
					 * cTemp = (tsTemp == null) ? cFechaNula : "'" +
					 * tFecha.getFechaDDMMMYYYY(tFecha.getDateSQL(tsTemp),"/");
					 */
					cTemp = (tsTemp == null) ? cFechaNula : "'"
							+ tFecha.getStringHoraHHMM_24(tsTemp, ":");
					rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
							false, rep.getAT_COMBINA_IZQUIERDA(), "");

					iReng += 2;

					rep.comDespliegaAlineado("E", iReng, "Autopista:", true,
							rep.getAT_HDERECHA(), "");

					rep.comDespliegaAlineado("F", iReng, "J", iReng,
							vdNomEnt.getString("COLC"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");

					iReng++;

					rep.comDespliegaAlineado("E", iReng,
							"Cadenamientos y Sentidos:", true,
							rep.getAT_HDERECHA(), "");

					rep.comDespliegaAlineado("F", iReng, "G", iReng,
							vdNomEnt.getString("COLA"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");

					iReng++;

					// rep.comDespliegaAlineado("E", iReng,
					// "Sentido:", true,
					// rep.getAT_HDERECHA(), "");
					//
					// rep.comDespliegaAlineado("F", iReng, "G", iReng,
					// vdNomEnt.getString("COLD"), false,
					// rep.getAT_COMBINA_IZQUIERDA(), "");
					//
					// iReng++;

					rep.comDespliegaAlineado("E", iReng, "Descripción ADV:",
							true, rep.getAT_HDERECHA(), "");

					rep.comDespliegaAlineado("F", iReng, "J", iReng + 2,
							vdNomEnt.getString("COLB"), false,
							rep.getAT_COMBINA_IZQUIERDA(),
							rep.getAT_VSUPERIOR());
					rep.comAlineaRango("F", iReng, "J", iReng + 2,
							rep.getAT_HJUSTIFICA());

					iReng += 3;

					rep.comDespliegaAlineado("E", iReng, "Oficina de Ingreso:",
							true, rep.getAT_HDERECHA(), "");

					rep.comDespliegaAlineado("F", iReng, "H", iReng,
							vdNomEnt.getString("COLD"), false,
							rep.getAT_COMBINA_IZQUIERDA(), "");

					iReng++;
					/*
					 * int iRengLimCubDocs = iReng;
					 * rep.comDespliegaAlineado("T", iReng,
					 * "Fecha Lím. Cubrir Doc.:", true, rep.getAT_HDERECHA(),
					 * ""); dtTemp =
					 * regGenerales.getDate("dtLimiteEntregaDocs"); cTemp =
					 * (dtTemp == null) ? cFechaNula : "'" +
					 * tFecha.getFechaDDMMMYYYY(dtTemp, "/");
					 * rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
					 * false, rep.getAT_COMBINA_IZQUIERDA(), ""); iReng++;
					 * rep.comDespliegaAlineado("T", iReng,
					 * "Fecha de Respuesta:", true, rep.getAT_HDERECHA(), "");
					 * // dtTemp1 = regGenerales.getDate("dtEstimadaEntrega");
					 * cTemp = (dtTemp1 == null) ? cFechaNula : "'" +
					 * tFecha.getFechaDDMMMYYYY(dtTemp1, "/");
					 * rep.comDespliegaAlineado("U", iReng, "W", iReng, cTemp,
					 * false, rep.getAT_COMBINA_IZQUIERDA(), ""); if
					 * (!cTemp.equals(cFechaNula)) { rep.comDespliega("T",
					 * iRengLimCubDocs, ""); rep.comDespliega("U",
					 * iRengLimCubDocs, ""); } iReng++;
					 * rep.comDespliegaAlineado("F", iReng,
					 * "Oficina de Resolución:", true, rep.getAT_HDERECHA(),
					 * ""); rep.comDespliegaAlineado("G", iReng, "W", iReng,
					 * cOficResuelve, false, rep.getAT_COMBINA_IZQUIERDA(), "");
					 * iReng++; rep.comDespliegaAlineado("F", iReng,
					 * "Departamento:", true, rep.getAT_HDERECHA(), "");
					 * rep.comDespliegaAlineado("G", iReng, "W", iReng,
					 * cDeptoResuelve, false, rep.getAT_COMBINA_IZQUIERDA(),
					 * ""); iReng++; /*
					 * rep.comDespliegaAlineado("B",iReng,"G",iReng,
					 * "Aplicar Trámite al Bien Núm.:"
					 * ,true,rep.getAT_COMBINA_DERECHA(),""); iTemp =
					 * regGenerales.getInt("iIdBien");
					 * rep.comDespliegaAlineado("H",iReng,"I",iReng, (iTemp > 0)
					 * ? iTemp + "" :
					 * "'- - -",false,rep.getAT_COMBINA_CENTRO(),""); cTemp =
					 * regGenerales.getString("cDscBien"); if(cTemp == null ||
					 * cTemp.equals("") || cTemp.equalsIgnoreCase("null")) cTemp
					 * = "NO SE ESPECIFICÓ ESTA INFORMACIÓN";
					 * rep.comDespliegaAlineado
					 * ("J",iReng,"W",iReng,cTemp,false,rep
					 * .getAT_COMBINA_IZQUIERDA(),"");
					 * rep.comAlineaRangoVer("B",
					 * iReng,"X",iReng,rep.getAT_VSUPERIOR());
					 * rep.comBordeRededor("B",iRengTemp,"X",iReng,iBorde,1);
					 */

					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 2;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					if (iReng >= (iUltimoSalto + iNumRengXPag)) {
						iUltimoSalto = iRengTemp - 3;
						cSaltosPag += (cSaltosPag.equals("")) ? ""
								+ iUltimoSalto : "," + iUltimoSalto;
					}

					// Datos de Requisitos del Trámite
					cWhere = "WHERE RRT.iEjercicio = " + iEjercicio
							+ "  AND RRT.iNumSolicitud = " + iNumSolicitud
							+ "  AND RRT.iCveTramite = " + iTramite
							+ "  AND RRT.iCveModalidad = " + iModalidad;
					cOrder = "ORDER BY RRT.dtRecepcion DESC, RMT.lRequerido DESC, RMT.iOrden, R.iCveRequisito";
					int iRenglones;
					vRequisitos = tTramite.getDatosRequisitosSolicitud(cWhere,
							cOrder);
					if (vRequisitos != null && vRequisitos.size() > 0) { // Datos
																			// de
																			// Requisitos
						iReng++;
						rep.comAltoReng("A", iReng, fAltoRengIntermedio);
						iReng++;
						rep.comDespliegaCombinado("REQUISITOS INGRESADOS", "B",
								iReng, "X", iReng, rep.getAT_COMBINA_CENTRO(),
								"", true, iFondoCeldaSec, true, true, iBorde, 1);
						iReng++;
						rep.comDespliegaCombinado("Recibido", "B", iReng, "E",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Obl.", "F", iReng, "F",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Fis.", "G", iReng, "G",
								iReng, rep.getAT_COMBINA_CENTRO(), "", true,
								iFondoCeldaSec, true, true, iBorde, 1);
						rep.comDespliegaCombinado("Descripción", "H", iReng,
								"X", iReng, rep.getAT_COMBINA_CENTRO(), "",
								true, iFondoCeldaSec, true, true, iBorde, 1);
						/*
						 * rep.comDespliegaCombinado("Cve.","G",iReng,"G",iReng,
						 * rep.getAT_COMBINA_CENTRO(),"",true,
						 * iFondoCeldaSec,true,true,iBorde,1);
						 * rep.comDespliegaCombinado
						 * ("Descripción","H",iReng,"X",iReng,
						 * rep.getAT_COMBINA_CENTRO(),"",true,
						 * iFondoCeldaSec,true,true,iBorde,1);
						 */
						iRengTemp = iReng + 1;
						TVDinRep regRequisito;
						lImprocedente = false;
						boolean lUsuEncontrado = false;
						for (int i = 0; i < vRequisitos.size(); i++) {
							iReng++;
							regRequisito = (TVDinRep) vRequisitos.get(i);
							cTemp = regRequisito.getString("cDscRequisito");
							iRenglones = (int) (cTemp.length() / iLetrasXRenglonReq);
							if (cTemp.length() % iLetrasXRenglonReq > 0)
								iRenglones++;
							dtTemp = regRequisito.getDate("dtRecepcion");
							if (regRequisito.getDate("dtRecepcion") != null
									&& !lUsuEncontrado) {
								cNomUsuRegistra = regRequisito
										.getString("cNomUsuRecibe");
								lUsuEncontrado = true;
							}
							cTemp = (dtTemp == null) ? "NO ENTREGADO" : "'"
									+ tFecha.getFechaDDMMMYYYY(dtTemp, "/");
							rep.comDespliegaAlineado("B", iReng, "E", iReng
									+ iRenglones - 1, cTemp, false,
									rep.getAT_COMBINA_CENTRO(),
									rep.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("F", iReng, "F", iReng
									+ iRenglones - 1, (regRequisito
									.getInt("lRequerido") == 1) ? "SI" : "NO",
									false, rep.getAT_COMBINA_CENTRO(), rep
											.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("G", iReng, "G", iReng
									+ iRenglones - 1, (regRequisito
									.getInt("lFisico") == 1) ? "SI" : "NO",
									false, rep.getAT_COMBINA_CENTRO(), rep
											.getAT_VSUPERIOR());
							rep.comDespliegaAlineado("H", iReng, "X", iReng
									+ iRenglones - 1,
									regRequisito.getString("cDscRequisito"),
									false, rep.getAT_COMBINA_IZQUIERDA(),
									rep.getAT_VSUPERIOR());
							rep.comAlineaRango("H", iReng, "X", iReng
									+ iRenglones - 1, rep.getAT_HJUSTIFICA());
							rep.comBordeRededor("B", iReng, "E", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("F", iReng, "F", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("G", iReng, "G", iReng
									+ iRenglones - 1, iBorde, 1);
							rep.comBordeRededor("H", iReng, "X", iReng
									+ iRenglones - 1, iBorde, 1);
							iReng += iRenglones - 1;
						}

						// Valida que todos los conceptos que requieren pago
						// anticipado esten pagados
						String cSQL4 = "SELECT "
								+ "       RS.IEJERCICIO, "
								+ "       RS.INUMSOLICITUD, "
								+ "       RS.ICVETRAMITE AS ICVETRAM, "
								+ "       RS.ICVEMODALIDAD, "
								+ "       RS.LPAGADO, "
								+ "       CTM.ICVECONCEPTO AS ICONCEPTO, "
								+ "       CTM.LPAGOANTICIPADO, "
								+ "       RP.LPAGADO AS CONCEPTOPAGADO, "
								+ "       CG.ICVEGRUPO "
								+ "FROM TRAREGSOLICITUD RS "
								+ "  JOIN TRACONCEPTOXTRAMMOD CTM ON CTM.IEJERCICIO = RS.IEJERCICIO "
								+ "    AND CTM.ICVETRAMITE = RS.ICVETRAMITE "
								+ "    AND CTM.ICVEMODALIDAD = RS.ICVEMODALIDAD "
								+ "  LEFT JOIN TRAREGREFPAGO RP ON RP.IEJERCICIO = RS.IEJERCICIO "
								+ "    AND RP.INUMSOLICITUD = RS.INUMSOLICITUD "
								+ "    AND RP.ICVECONCEPTO = CTM.ICVECONCEPTO "
								+ "  LEFT join TRACONCEPTOXGRUPO cg on ctm.ICVECONCEPTO = cg.ICVECONCEPTO "
								+ "WHERE RS.iEjercicio = " + iEjercicio
								+ "  AND RS.iNumSolicitud = " + iNumSolicitud
								+ "  AND CTM.lPagoAnticipado = 1 "
								+ " ORDER BY CG.ICVEGRUPO,CONCEPTOPAGADO";
						// try{
						Vector vAnticipadosPorPagar = this.findByCustom("",
								cSQL4);
						TVDinRep vConceptos = new TVDinRep();
						int iGpoPagado = 0;
						if (vAnticipadosPorPagar.size() > 0) {
							for (int inc1 = 0; inc1 < vAnticipadosPorPagar
									.size(); inc1++) {
								vConceptos = (TVDinRep) vAnticipadosPorPagar
										.get(inc1);
								if (vConceptos.getInt("ICVEGRUPO") != 0
										&& vConceptos.getInt("CONCEPTOPAGADO") == 1) {
									iGpoPagado = vConceptos.getInt("ICVEGRUPO");
								}
							}
							for (int inc = 0; inc < vAnticipadosPorPagar.size(); inc++) {
								vConceptos = (TVDinRep) vAnticipadosPorPagar
										.get(inc);
								if (vConceptos.getInt("ICVEGRUPO") == 0
										&& vConceptos.getInt("CONCEPTOPAGADO") == 0) {
									lImprocedente = true;
								}
								if (iGpoPagado >= 0) {
									if (vConceptos.getInt("ICVEGRUPO") != 0
											&& vConceptos
													.getInt("CONCEPTOPAGADO") != 1
											&& vConceptos.getInt("ICVEGRUPO") != iGpoPagado) {
										lImprocedente = true;
									}
								}
							}
						}
						if (iNumeroReporte == 1) {
							String cSQLPNC = "SELECT " + "ICONSECUTIVOPNC "
									+ "FROM TRAREGPNCETAPA "
									+ "where IEJERCICIO = " + iEjercicio
									+ " and INUMSOLICITUD = " + iNumSolicitud;

							Vector vSQLPNC = this.findByCustom("", cSQLPNC);
							if (vSQLPNC.size() > 0) {
								lImprocedente = true;
								lRelacionadoImprocedente = true;
							}
						}
						if (lImprocedente == true
								|| lRelacionadoImprocedente == true) {
							cSolicitudesImprocedentes = cSolicitudesImprocedentes
									.substring(
											0,
											cSolicitudesImprocedentes.length() - 1);
							cRenglonesImprocedentes = cRenglonesImprocedentes
									.substring(
											0,
											cRenglonesImprocedentes.length() - 1);
							String[] cRengImpro = cRenglonesImprocedentes
									.split(",");
							String[] cSolImproc = cSolicitudesImprocedentes
									.split(",");
							String[] cTramImproc = cTramiteImproc.split(",");
							String[] cModaImproc = cModalidadImproc.split(",");
							int iRengTempImp = 0;

							for (int x = 0; x < cRengImpro.length; x++) {

								iRengTempImp = Integer.parseInt(cRengImpro[x],
										10);
								rep.comFontColor("R", iRengTempImp, "X",
										iRengTempImp, 2);
								rep.comDespliegaCombinado(
										"TRÁMITE IMPROCEDENTE", "R",
										iRengTempImp, "X", iRengTempImp,
										rep.getAT_COMBINA_CENTRO(), "", true,
										iFondoEsp, true, false, iBorde, 1);
								rep.comDespliegaAlineado("T", iRengTempImp + 4,
										"T", iRengTempImp + 4, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
								rep.comDespliegaAlineado("U", iRengTempImp + 4,
										"W", iRengTempImp + 4, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
								rep.comDespliegaAlineado("T", iRengTempImp + 5,
										"T", iRengTempImp + 5, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
								rep.comDespliegaAlineado("U", iRengTempImp + 5,
										"W", iRengTempImp + 5, "", false,
										rep.getAT_COMBINA_IZQUIERDA(), "");
							}
							cRenglonesImprocedentes = "";
							// Actualiza etapa en que se encuentra el trámite y
							// lo concluye

							// LEL Elimina la leyenda de bien si el trámite es
							// improcedente
							if (regGenerales.getInt("iIdBien") == 0) {
								rep.comDespliegaCombinado("", "B",
										iRengBienPos, "X", iRengBienPos
												+ iRengBien - 1,
										rep.getAT_COMBINA_IZQUIERDA(),
										rep.getAT_VSUPERIOR(), false,
										iFondoCeldaSec, false, false, iBorde, 1);
								rep.comAlineaRango("B", iRengBienPos, "X",
										iRengBienPos + iRengBien - 1,
										rep.getAT_HJUSTIFICA());
							}
							try {
								for (int x = 0; x < cSolImproc.length; x++) {

									String cRelacionados = "SELECT INUMSOLICITUDREL,icvetramite,icvemodalidad "
											+ "FROM TRASOLICITUDREL SR "
											+ "JOIN TRAREGSOLICITUD S ON SR.IEJERCICIO=S.IEJERCICIO AND SR.INUMSOLICITUDREL=S.INUMSOLICITUD "
											+ "where SR.IEJERCICIO="
											+ regGenerales.getInt("iEjercicio")
											+ " and SR.INUMSOLICITUDREL <> "
											+ cSolImproc[x]
											+ " and SR.INUMSOLICITUDPRINC = "
											+ "(select INUMSOLICITUDPRINC from TRASOLICITUDREL where iejercicio="
											+ regGenerales.getInt("iEjercicio")
											+ " and INUMSOLICITUDREL="
											+ cSolImproc[x] + ") ";
									TVDinRep vDatos = new TVDinRep();
									vDatos.put("iEjercicio",
											regGenerales.getInt("iEjercicio"));
									vDatos.put("iNumSolicitud", cSolImproc[x]);
									vDatos.put("iCveTramite", cTramImproc[x]);
									vDatos.put("iCveModalidad", cModaImproc[x]);
									vDatos.put("iCveOficina",
											regGenerales.getInt("iCveOficina"));
									vDatos.put("iCveDepartamento", regGenerales
											.getInt("iCveDepartamento"));
									vDatos.put("iCveUsuario", regGenerales
											.getInt("iCveUsuRegistra"));
									vDatos.put("iCveUsuRegistra", regGenerales
											.getInt("iCveUsuRegistra"));
									vDatos.put("iCveUsuEntrega", regGenerales
											.getInt("iCveUsuRegistra"));

									tTramite.updateImprocedenteFaltaPago(vDatos);

								}
								cSolicitudesImprocedentes = "";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						if (iReng >= (iUltimoSalto + iNumRengXPag)) {
							iUltimoSalto = iRengTemp - 2;
							cSaltosPag += (cSaltosPag.equals("")) ? ""
									+ iUltimoSalto : "," + iUltimoSalto;
						}
					}
				}
				iReng++;
				rep.comAltoReng("A", iReng, fAltoRengIntermedio);
				iReng++;
				rep.comDespliegaCombinado("RECIBE", "B", iReng, "L", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				rep.comDespliegaCombinado("SELLO", "R", iReng, "X", iReng,
						rep.getAT_COMBINA_CENTRO(), "", true, iFondoCeldaPrin,
						true, true, iBorde, 1);
				iReng++;
				iRengTemp = iReng;
				rep.comBordeRededor("B", iReng, "L", iReng + 2, iLineaFirma, 1);
				rep.comBordeRededor("B", iReng, "L", iReng + 5, iBorde, 1);
				rep.comBordeRededor("R", iReng, "X", iReng + 10, iBorde, 1);
				iReng += 3;
				/*
				 * rep.comDespliegaCombinado(cNomUsuRegistra, "B", iReng, "L",
				 * iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1, false,
				 * false, 0, 0);
				 */
				rep.comDespliegaCombinado("VENTANILLA ÚNICA", "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);

				iReng++;
				/*
				 * rep.comDespliegaCombinado(cDscDeptoRecibe, "B", iReng, "L",
				 * iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1, false,
				 * false, 0, 0); iReng++;
				 */
				rep.comDespliegaCombinado(cDscOficinaRecibe, "B", iReng, "L",
						iReng, rep.getAT_COMBINA_CENTRO(), "", false, -1,
						false, false, 0, 0);
				/*
				 * if(lOficinaResolDiferente){ iReng += 2;
				 * rep.comDespliegaCombinado(cNotaCapitania,"B",iReng,"P",iReng
				 * +
				 * 3,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,
				 * -1,false,false,0,0); rep.comAlineaRango("B",iReng,"P",iReng +
				 * 3,rep.getAT_HJUSTIFICA()); iReng += 3; }
				 */
				// LEL12102006
				iReng += 4;
				/*
				 * int ilongPago = cLeyendaPago.length(); int iRengPago = 0;
				 * while(ilongPago > 0){ ilongPago = ilongPago - iLongReng;
				 * iRengPago += 1; }
				 * rep.comDespliegaCombinado(cLeyendaPago,"B",iReng,"X",iReng +
				 * iRengPago -
				 * 1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR
				 * (),false,iFondoCeldaSec,false,false,iBorde,1);
				 * rep.comAlineaRango("B",iReng,"X",iReng + iRengPago -
				 * 1,rep.getAT_HJUSTIFICA()); iReng += iRengPago + 1;
				 */
				int ilongEvalua = cLeyendaEval.length();
				int iRengEvalua = 0;
				while (ilongEvalua > 0) {
					ilongEvalua = ilongEvalua - iLongReng;
					iRengEvalua += 1;
				}
				rep.comDespliegaCombinado(cLeyendaEval, "B", iReng, "X", iReng
						+ iRengEvalua - 1, rep.getAT_COMBINA_IZQUIERDA(),
						rep.getAT_VSUPERIOR(), false, iFondoCeldaSec, false,
						false, iBorde, 1);
				rep.comAlineaRango("B", iReng, "X", iReng + iRengEvalua - 1,
						rep.getAT_HJUSTIFICA());
				iReng += iRengEvalua - 1;
				// FinLEL12102006

				if (iReng >= (iUltimoSalto + iNumRengXPag)) {
					iUltimoSalto = iRengTemp - 2;
					cSaltosPag += (cSaltosPag.equals("")) ? "" + iUltimoSalto
							: "," + iUltimoSalto;
				}

				if (!cSaltosPag.equals(""))
					rep.comSaltosPagina(cSaltosPag);
				sbRetorno = rep.getSbDatos();
			} catch (Exception e) {
				e.printStackTrace();
				cMensaje += e.getMessage();
			}
		}
		if (!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return sbRetorno;
	}

}

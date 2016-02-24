package gob.sct.sipmm.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
import gob.sct.sipmm.dao.ws.TDCis;
import cisws.generated.WSInsertaCita;
import cisws.generated.WSInsertaCitaPort;
import cisws.generated.WSInsertaCita_Impl;

/**
 * <p>
 * Title: TDTRARegSolicitud.java
 * </p>
 * <p>
 * Description: DAO de la entidad TRARegSolicitud
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Tecnolog�a InRed S.A. de C.V.
 * </p>
 * 
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRARegSolicitud extends DAOBase {
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros
			.getPropEspecifica("ConDBModulo");
	private TFechas tFecha = new TFechas("44");

	/**
	 * Ejecuta cualquier query enviado en cWhere y regresa a las entidades
	 * encontradas.
	 * 
	 * @param cKey
	 *            String - Cadena de Campos que definen la llave de cada entidad
	 *            encontrada.
	 * @param cWhere
	 *            String - Cadena que contiene al query a ejecutar.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return Vector - Arreglo que contiene a las entidades (VOs) encontrados
	 *         por el query.
	 */
	public Vector findByCustom(String cKey, String cWhere) throws DAOException {
		Vector vcRecords = new Vector();
		cError = "";
		try {
			String cSQL = cWhere;
			vcRecords = this.FindByGeneric(cKey, cSQL, dataSourceName);
		} catch (Exception e) {
			cError = e.toString();
		} finally {
			if (!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	/**
	 * Ejecuta cualquier query enviado en cWhere y regresa a las entidades
	 * encontradas.
	 * 
	 * @param cKey
	 *            String - Cadena de Campos que definen la llave de cada entidad
	 *            encontrada.
	 * @param cWhere
	 *            String - Cadena que contiene al query a ejecutar.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return Vector - Arreglo que contiene a las entidades (VOs) encontrados
	 *         por el query.
	 */
	public Vector findSolicitud(String cKey, String cWhere, int iEjercicio,
			int iNumSolicitud) throws DAOException {
		Vector vcRecords = new Vector();
		cError = "";
		try {
			// boolean lTienePNC = true; LEL050906
			boolean lTienePNC = false;
			String cSQL = cWhere;
			// Query q va a buscar primero si la solicitud solicitada tiene PNC
			// no resueltos
			String cSQLPNC = "SELECT TRAREGPNCETAPA.IEJERCICIO, TRAREGPNCETAPA.INUMSOLICITUD, GRLREGISTROPNC.LRESUELTO "
					+ "FROM TRAREGPNCETAPA "
					+ "JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC "
					+ "                    AND GRLREGISTROPNC.ICONSECUTIVOPNC = TRAREGPNCETAPA.ICONSECUTIVOPNC "
					+ "WHERE TRAREGPNCETAPA.iEjercicio = "
					+ iEjercicio
					+ " AND TRAREGPNCETAPA.INUMSOLICITUD = "
					+ iNumSolicitud
					+ " ";

			/*
			 * LEL050906 Se elimina para que se valide PNC para cada caso
			 * vcRecords = this.FindByGeneric(cKey,cSQLPNC,dataSourceName);
			 * TVDinRep vDatos = new TVDinRep(); if (vcRecords.size()>0){ for
			 * (int i=0; i<vcRecords.size();i++){ vDatos = (TVDinRep)
			 * vcRecords.get(i); if ( vDatos.getInt("lResuelto") == 1 ){
			 * lTienePNC = false; break; } else lTienePNC = true;
			 * 
			 * } } else lTienePNC = false;
			 * 
			 * FINLEL050906
			 */
			if (!lTienePNC)
				vcRecords = this.FindByGeneric(cKey, cSQL, dataSourceName);
			else
				vcRecords = new Vector();
		} catch (Exception e) {
			cError = e.toString();
		} finally {
			if (!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	public TVDinRep insertRegEtapasxModTram(TVDinRep vData,
			Connection cnNested, int iEtapa, int iCveOficina,
			int iCveDepartamento, int iCveUsuario) throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		Vector vcRegs = null;
		String[] aEjercicio, aNumSolicitud, aCveTramite, aCveModalidad, alAnexo;
		int iOrden = 0;
		TParametro parametro = null;
		TVDinRep vDatos = null;
		TFechas tsThisTime = new TFechas();
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			aEjercicio = vData.getString("iEjercicio").split(",");
			aNumSolicitud = vData.getString("iNumSolicitud").split(",");
			aCveTramite = vData.getString("iCveTramite").split(",");
			aCveModalidad = vData.getString("iCveModalidad").split(",");
			alAnexo = vData.getString("lAnexo").split(",");

			// Inserta todas las solicitudes recibidas
			for (int i = 0; i < aEjercicio.length; i++) {
				String cSQLTRaEtapaXModTram = "SELECT iOrden FROM TRaEtapaXModTram "
						+ " WHERE iCveTramite = "
						+ aCveTramite[i]
						+ " AND iCveModalidad = "
						+ aCveModalidad[i]
						+ " AND iCveEtapa = " + iEtapa;

				vcRegs = this.FindByGeneric("", cSQLTRaEtapaXModTram,
						dataSourceName);

				if (vcRegs.size() > 0) {
					vDatos = (TVDinRep) vcRegs.get(0);
					iOrden = vDatos.getInt("iOrden");
				}

				String lSQL = "INSERT INTO TRARegEtapasXModTram(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden, iCveOficina, iCveDepartamento, iCveUsuario, tsRegistro, lAnexo) values (?,?,?,?,?,?,?,?,?,?,?)";

				vData.addPK(vData.getString("iEjercicio"));
				vData.addPK(vData.getString("iNumSolicitud"));
				vData.addPK(vData.getString("iCveTramite"));
				vData.addPK(vData.getString("iCveModalidad"));
				vData.addPK(vData.getString("iCveEtapa"));
				vData.addPK(vData.getString("iOrden"));

				lPStmt = conn.prepareStatement(lSQL);
				lPStmt.setInt(1, Integer.parseInt(aEjercicio[i]));
				lPStmt.setInt(2, Integer.parseInt(aNumSolicitud[i]));
				lPStmt.setInt(3, Integer.parseInt(aCveTramite[i]));
				lPStmt.setInt(4, Integer.parseInt(aCveModalidad[i]));
				lPStmt.setInt(5, iEtapa);
				lPStmt.setInt(6, iOrden);
				lPStmt.setInt(7, iCveOficina);
				lPStmt.setInt(8, iCveDepartamento);
				lPStmt.setInt(9, iCveUsuario);
				lPStmt.setTimestamp(10, tsThisTime.getThisTime());
				lPStmt.setInt(11, Integer.parseInt(alAnexo[i]));

				lPStmt.executeUpdate();

				if (lPStmt != null) {
					lPStmt.close();
				}

			}

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("insertRegEtapasxModTram", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insertRegEtapasxModTram.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
			return vData;
		}
	}

	/**
	 * Inserta el registro dado por la entidad vData.
	 * <p>
	 * <b> insert into
	 * TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad
	 * ,iCveSolicitante
	 * ,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro
	 * ,dtLimiteEntregaDocs
	 * ,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva
	 * ,lDatosPreregistro
	 * ,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma)
	 * values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return TVDinRep - VO Din�mico que contiene a la entidad a Insertada, as�
	 *         como a la llave de esta entidad.
	 */
	public TVDinRep insert(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		int iAux = 0;
		int i = 0;
		int iSolicitud = 0;
		int lPrincipal;
		String[] aTramite;
		String[] cRegistro;
		String cTramite;
		String cModalidad;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			String cSql = "Select MAX(iNumSolicitud) as iNumSolicitud "
					+ "from TRARegSolicitud "
					+ "where iEjercicio =  Year(Current date)";

			aTramite = vData.getString("iCveRequisito").split(";");
			cRegistro = vData.getString("iAuxiliar").split(",");

			String lSQL = "insert into TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,lImpreso) values (YEAR(CURRENT DATE),?,?,?,?,?,?,?,(CURRENT TIMESTAMP),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			lPStmt = conn.prepareStatement(lSQL);

			String cSql1 = "SELECT iCveTramiteHijo,iCveModalidadHijo "
					+ "FROM tradependencia " + "WHERE iCveTramite = "
					+ vData.getInt("iCveTramite") + " "
					+ "AND iCveModalidad = " + vData.getInt("iCveModalidad");

			Vector vcHijos = findByCustom("", cSql1);

			for (iAux = 0; iAux <= (aTramite.length - 1); iAux++) {
				// debug("Longitud" + cRegistro.length + " Tramite " +
				// aTramite.length);

				if (Integer.parseInt(cRegistro[iAux]) != 0) {
					cTramite = ""
							+ ((TVDinRep) vcHijos.get(Integer
									.parseInt(cRegistro[iAux]) - 1))
									.getInt("iCveTramiteHijo");
					cModalidad = ""
							+ ((TVDinRep) vcHijos.get(Integer
									.parseInt(cRegistro[iAux]) - 1))
									.getInt("iCveModalidadHijo");
					lPrincipal = 0;
				} else {
					cTramite = "" + vData.getString("iCveTramite");
					cModalidad = "" + vData.getString("iCveModalidad");
					lPrincipal = 1;
				}
				// debug("COSA" + Integer.parseInt(cRegistro[iAux]));

				if (iAux == 0) {
					Vector vcData = findByCustom("", cSql);
					if (vcData.size() > 0) {
						TVDinRep vUltimo = (TVDinRep) vcData.get(0);
						iSolicitud = vUltimo.getInt("iNumSolicitud") + 1;
					} else
						iSolicitud = 1;

				} else
					iSolicitud++;

				// vData.put("iNumSolicitud",iSolicitud);
				/*
				 * String lSQL =
				 * "insert into TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma) values (YEAR(CURRENT DATE),?,?,?,?,?,?,?,(CURRENT TIMESTAMP),?,?,?,?,?,?,?,?,?,?,?,?,?)"
				 * ;
				 */

				vData.addPK(vData.getString("iEjercicio"));
				vData.addPK(vData.getString("iNumSolicitud"));

				// ...

				// lPStmt = conn.prepareStatement(lSQL);
				// debug();
				lPStmt.setInt(1, iSolicitud);
				lPStmt.setInt(2, Integer.parseInt(cTramite));
				lPStmt.setInt(3, Integer.parseInt(cModalidad));
				lPStmt.setInt(4, vData.getInt("iCveSolicitante"));
				lPStmt.setInt(5, vData.getInt("iCveRepLegal"));
				lPStmt.setString(6, vData.getString("cNomAutorizaRecoger"));
				lPStmt.setInt(7, vData.getInt("iCveUsuRegistra"));
				lPStmt.setDate(8, vData.getDate("dtLimiteEntregaDocs"));
				lPStmt.setDate(9, vData.getDate("dtEstimadaEntrega"));
				lPStmt.setInt(10, vData.getInt("lPagado"));
				lPStmt.setDate(11, vData.getDate("dtEntrega"));
				lPStmt.setInt(12, vData.getInt("iCveUsuEntrega"));
				lPStmt.setInt(13, vData.getInt("lResolucionPositiva"));
				lPStmt.setInt(14, vData.getInt("lDatosPreregistro"));
				lPStmt.setInt(15, vData.getInt("iIdBien"));
				lPStmt.setInt(16, vData.getInt("iCveOficina"));
				lPStmt.setInt(17, vData.getInt("iCveDepartamento"));
				lPStmt.setInt(18, vData.getInt("iEjercicioCita"));
				lPStmt.setInt(19, vData.getInt("iIdCita"));
				lPStmt.setInt(20, vData.getInt("iCveForma"));
				lPStmt.setInt(21, lPrincipal);
				lPStmt.setInt(22, 0);
				lPStmt.executeUpdate();
			}
			vData.put("iNumSolicitud", (iSolicitud - (aTramite.length - 1)));
			try {
				new TDTRARegReqXTram().insert(vData, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("insert", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
			return vData;
		}
	}

	public synchronized Vector FindNextSolicitud(String cKey, String cSQL,
			String dataSourceName) throws DAOException {
		this.dataSourceName = dataSourceName;
		DbConnection dbConn = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Vector vcDinRep = new Vector();
		try {
			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			// conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
			conn.setTransactionIsolation(2);

			pstmt = conn.prepareStatement(cSQL);
			rset = pstmt.executeQuery();
			vcDinRep = this.getVO(cKey, rset);

		} catch (SQLException ex) {
			cError = cMensaje = getSQLMessages(ex);
		} catch (Exception ex) {
			cError = ex.getMessage();
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				dbConn.closeConnection();
			} catch (Exception ex2) {
				warn("FindByGeneric.close", ex2);
			}
			if (!cError.equals(""))
				throw new DAOException(cError);
			return vcDinRep;
		}
	}

	/**
	 * Inserta el registro dado por la entidad vData.
	 * <p>
	 * <b> insert into
	 * TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad
	 * ,iCveSolicitante
	 * ,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro
	 * ,dtLimiteEntregaDocs
	 * ,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva
	 * ,lDatosPreregistro
	 * ,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma)
	 * values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return TVDinRep - VO Din�mico que contiene a la entidad a Insertada, as�
	 *         como a la llave de esta entidad.
	 */
	public synchronized TVDinRep insertBatch(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null, lPStmt2 = null;
		boolean lSuccess = true;
		String cNumSolicitudes = "";

		TFechas tFecha = new TFechas();
		Vector vcDataMax = new Vector();
		int iNumSolicitud = 1;

		// LEL06122006 C�lculo de Fecha Estimada de Entrega para tr�mites
		// relacionados
		java.sql.Date dtIniCalculo;
		java.sql.Date dtFinCalculo;
		java.sql.Date dtTramitePrincipal = null;
		int iEjerPrincipal = 0;
		int iNumSolPrincipal = 0;
		int iMaxDias = 0;
		int iNumDias = 0;
		int iMaxTramite = 0;
		int iMaxModalidad = 0;
		Vector vcSolicitudes = new Vector();
		Vector vcSolicitud = null;
		// Fin LEL06122006
		java.sql.Timestamp tsRegistro = tFecha.getThisTime(false);
		java.sql.Date dtRegistro = tFecha.getDateSQL(tsRegistro);
		TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();
		TDTRARegEtapasXModTram dEtapaIni = new TDTRARegEtapasXModTram();
		TDTRARegPNCEtapa dRegPNCEtapa = new TDTRARegPNCEtapa();
		java.sql.Date dtInicioCalculos = dDiaNoHabil.getFechaFinal(dtRegistro,
				0, false);
		int iEjercicio = tFecha.getIntYear(dtRegistro);
		int iCveTramite = 0, iCveModalidad = 0, iCveOficinaOrigen, iCveDeptoOrigen;
		String cOficinaResuelve, cDeptoResuelve, cSolicitudPNC = "", cTramitePNC = "", cModalidadPNC = "", cOrdenPNC = "";
		boolean lReqCompletos;
		int iCveEtapa = Integer.parseInt(
				VParametros.getPropEspecifica("EtapaRegistro"), 10);
		int iPNC = Integer.parseInt(
				VParametros.getPropEspecifica("VentanillaProductoRecepcion"),
				10);
		int iCausaReqInc = Integer.parseInt(VParametros
				.getPropEspecifica("VentanillaCausaRequisitoIncompleto"), 10);
		int iCausaReqNC = Integer.parseInt(VParametros
				.getPropEspecifica("VentanillaCausaRequisitoNoConforme"), 10);

		String[] aTramites, aModalidades, aRequisitos, aReqTemp, aReqValor, aEnvObl, aCarac, aReqCarac, aCaracTemp, aCarValor, aReqPNC;
		Vector vcCaracPNC = new Vector();
		String cSQLMax = "SELECT MAX(iNumSolicitud) AS iNumSolicitud FROM TRARegSolicitud WHERE iEjercicio = "
				+ iEjercicio;
		String cSQLTra = "INSERT INTO TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,"
				+ "iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,"
				+ "dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,"
				+ "iEjercicioCita,iIdCita,iCveForma,lPrincipal,lImpreso,iCveDomicilioSolicitante,iCveDomicilioRepLegal,"
				+ "cEnviarResolucionA,cObsTramite,cDscBien,iCveOficinaEnviaRes,dUnidadesCalculo)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String cSQLReq = "INSERT INTO TRARegReqXTram (iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,dtNotificacion,cNumOficio,dtOficio,lValido)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String cSQLdtEstPrin = " UPDATE TRARegSolicitud SET dtEstimadaEntrega=? "
				+ " WHERE iEjercicio=? AND iNumSolicitud=? ";
		Vector vcOficXDepto = this.findByCustom(
				"",
				"SELECT iCveOficina,iCveDepartamento FROM GRLDEPTOXOFIC where ICVEOFICINA = "
						+ vData.getInt("iCveOficinaUsr")
						+ " AND ICVEDEPARTAMENTO = "
						+ vData.getInt("iCveDeptoUsr"));

		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				// conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
				conn.setTransactionIsolation(2);
			}
			iCveOficinaOrigen = vData.getInt("iCveOficinaUsr");
			if (vcOficXDepto.size() > 0)
				iCveDeptoOrigen = vData.getInt("iCveDeptoUsr");
			else
				iCveDeptoOrigen = Integer.parseInt(VParametros
						.getPropEspecifica("DeptoVentanillaUnica"));
			TVDinRep vDataPNC = new TVDinRep();
			TDGRLRegistroPNC dRegistroPNC = new TDGRLRegistroPNC();
			TVDinRep vDataReqCausa = new TVDinRep();
			TDTRARegReqXCausa dRegReqXCausa = new TDTRARegReqXCausa();

			if (!vData.getString("RequisitoPNC").equals(""))
				aReqPNC = vData.getString("RequisitoPNC").split(",");
			else
				aReqPNC = null;
			aTramites = vData.getString("ClavesTramite").split(",");
			aModalidades = vData.getString("ClavesModalidad").split(",");
			aRequisitos = vData.getString("iCveRequisito").split(":");
			if (!vData.getString("Caracteristicas").equals(""))
				aCarac = vData.getString("Caracteristicas").split(":");
			else
				aCarac = null;
			Vector vcTemp;

			if (aCarac != null) {
				for (int i = 0; i < aCarac.length; i++) {
					aReqCarac = aCarac[i].split("/");
					aCaracTemp = aReqCarac[1].split(",");
					vcTemp = new Vector();
					for (int j = 0; j < aCaracTemp.length; j++) {
						aCarValor = aCaracTemp[j].split("=");
						if (aCarValor[1].equalsIgnoreCase("true"))
							vcTemp.add(aCarValor[0]);
					}
					if (vcTemp.size() > 0) {
						Vector vcAgregar = new Vector();
						vcAgregar.add(aReqCarac[0]);
						vcAgregar.add(vcTemp);
						vcCaracPNC.add(vcAgregar);
					}
				}
			}

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			for (int i = 0; i < aTramites.length; i++) {
				aReqTemp = aRequisitos[i].split(",");
				lReqCompletos = true;
				for (int j = 0; j < aReqTemp.length; j++) {
					aReqValor = aReqTemp[j].split("=");
					aEnvObl = aReqValor[1].replace('/', ',').split(",");
					if (aEnvObl[0].equalsIgnoreCase("false")
							&& aEnvObl[1].equalsIgnoreCase("1")) {
						lReqCompletos = false;
					}
				}

				// Prepara Statement de creaci�n de solicitud.
				iCveTramite = Integer.parseInt(aTramites[i], 10);
				iCveModalidad = Integer.parseInt(aModalidades[i], 10);
				String cOficDestino = this.getOficinaDeptoResuelve(
						iCveOficinaOrigen, iCveTramite);
				String[] aOficDepto = cOficDestino.split(",");
				if (aOficDepto.length == 2) {
					cOficinaResuelve = aOficDepto[0];
					cDeptoResuelve = aOficDepto[1];
				} else {
					cOficinaResuelve = iCveOficinaOrigen + "";
					cDeptoResuelve = iCveDeptoOrigen + "";
				}

				if (vcOficXDepto.size() <= 0)
					cDeptoResuelve = VParametros
							.getPropEspecifica("DeptoVentanillaUnica");

				if (i == 0) {
					vcDataMax = this.FindNextSolicitud("", cSQLMax,
							dataSourceName);
					if (vcDataMax.size() > 0)
						iNumSolicitud = ((TVDinRep) vcDataMax.get(0))
								.getInt("iNumSolicitud") + 1;
					else
						iNumSolicitud = 1;
				} else
					iNumSolicitud++;

				cNumSolicitudes += (cNumSolicitudes.equals("")) ? ""
						+ iNumSolicitud : ", " + iNumSolicitud;
				lPStmt = conn.prepareStatement(cSQLTra);
				lPStmt.setInt(1, iEjercicio); // iEjercicio
				lPStmt.setInt(2, iNumSolicitud); // iNumSolicitud
				lPStmt.setInt(3, iCveTramite); // iCveTramite
				lPStmt.setInt(4, iCveModalidad); // iCveModalidad
				lPStmt.setInt(5, vData.getInt("iCveSolicitante")); // iCveSolicitante
				lPStmt.setInt(6, vData.getInt("iCveRepLegal")); // iCveRepLegal
				lPStmt.setString(7, vData.getString("cNomAutorizaRecoger")); // cNomAutorizaRecoger
				lPStmt.setInt(8, vData.getInt("iCveUsuario")); // iCveUsuRegistra
				lPStmt.setTimestamp(9, tsRegistro); // tsRegistro

				java.sql.Date dtLimEntregaDocs = this
						.getFechaLimiteEntregaDocs(iCveTramite, iCveModalidad,
								dtInicioCalculos);
				if (dtLimEntregaDocs == null)
					lPStmt.setNull(10, Types.DATE); // dtLimiteEntregaDocs
				else
					lPStmt.setDate(10, dtLimEntregaDocs); // dtLimiteEntregaDocs
				lPStmt.setNull(11, Types.DATE);

				java.sql.Date dtEstimadaEntrega = null;

				if (i == 0) {
					dtTramitePrincipal = dtEstimadaEntrega; // Registro de Fecha
															// Entrega Principal
				} else {
					iNumDias = getNumDiasResol(iCveTramite, iCveModalidad);
					if (iNumDias > iMaxDias) {
						iMaxDias = iNumDias;
						iMaxTramite = iCveTramite;
						iMaxModalidad = iCveModalidad;
					}
				}
				vcSolicitud = new Vector();
				vcSolicitud.addElement(String.valueOf(iEjercicio));
				vcSolicitud.addElement(String.valueOf(iNumSolicitud));
				vcSolicitudes.addElement(vcSolicitud);

				lPStmt.setInt(12, 0); // lPagado
				lPStmt.setNull(13, Types.DATE); // dtEntrega
				lPStmt.setInt(14, 0); // iCveUsuEntrega
				lPStmt.setInt(15, 0); // lResolucionPositiva

				// modificar cuando se defina el preregistro
				lPStmt.setInt(16, 0); // lDatosPreregistro
				lPStmt.setInt(17, vData.getInt("iIdBien")); // iIdBien

				lPStmt.setInt(18, iCveOficinaOrigen); // iCveOficina
				lPStmt.setInt(19, iCveDeptoOrigen); // iCveDepartamento

				// modificar cuando se defina la forma de localizar la cita o de
				// registrarla
				lPStmt.setInt(20, 0); // iEjercicioCita
				lPStmt.setInt(21, 0); // iIdCita

				lPStmt.setInt(22, 0); // iCveForma

				lPStmt.setInt(23, (i == 0) ? 1 : 0); // lPrincipal
				lPStmt.setInt(24, 0); // lImpreso
				lPStmt.setInt(25, vData.getInt("iCveDomicilioSolicitante")); // iCveDomicilioSolicitante
				lPStmt.setInt(26, vData.getInt("iCveDomicilioRepLegal")); // iCveDomicilioRepLegal
				lPStmt.setString(27, vData.getString("cEnviarResolucionA")); // cEnviaResolucionA
				lPStmt.setString(28, vData.getString("cObsTramite")); // cObsTramite
				lPStmt.setString(29, vData.getString("cDscBien")); // cDscBien

				if (vData.getInt("iCveOficina") == -1)
					lPStmt.setNull(30, Types.INTEGER);
				else
					lPStmt.setInt(30, vData.getInt("iCveOficina")); // Enviar
																	// resoluci�n
																	// a oficina

				lPStmt.setDouble(31, vData.getDouble("dUnidCalculo"));

				try {
					lPStmt.executeUpdate();
					lPStmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (cnNested == null)
					conn.commit();
				// Inserta etapa inicial del tr�mite para el seguimiento

				TVDinRep vEtapa = new TVDinRep();
				vEtapa.put("iEjercicio", iEjercicio);
				vEtapa.put("iNumSolicitud", iNumSolicitud);
				vEtapa.put("iCveTramite", iCveTramite);
				vEtapa.put("iCveModalidad", iCveModalidad);
				vEtapa.put("iCveEtapa", iCveEtapa);
				vEtapa.put("iCveOficina",
						Integer.parseInt(cOficinaResuelve, 10));
				vEtapa.put("iCveDepartamento",
						Integer.parseInt(cDeptoResuelve, 10));
				vEtapa.put("iCveUsuario", vData.getInt("iCveUsuario"));
				vEtapa.put("tsRegistro", tsRegistro);
				vEtapa.put("lAnexo", 0);
				vEtapa.put(
						"cObservaciones",
						"Solicitud" + iEjercicio + "/" + iNumSolicitud
								+ " registrada con fecha:"
								+ tsRegistro.toString());
				try {
					vEtapa = dEtapaIni.insertEtapa(vEtapa, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}

				/************** Incerta al CIS **************/
				TVDinRep vCIS = new TVDinRep();
				vCIS.put("iEjercicio", iEjercicio);
				vCIS.put("iNumSolicitud", iNumSolicitud);
				vCIS.put("iCveTramite", iCveTramite);
				vCIS.put("iCveModalidad", iCveModalidad);
				vCIS.put("iCveOficina", Integer.parseInt(cOficinaResuelve, 10));
				vCIS.put("iCveSolicitante", 0);
				vCIS.put("dtCita", tFecha.getDateSQL(tsRegistro));

				try {
					this.updateEtapasCIS(vCIS, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (cnNested == null)
					conn.commit();
				// Inicia creaci�n de requisitos del tr�mite.
				int iRequisito;

				if (vcCaracPNC.size() > 0) {
					// Crear GRLRegistroPNC, y GRLRegCausaPNC
					cSolicitudPNC += (cSolicitudPNC.equals("")) ? ""
							+ iNumSolicitud : "," + iNumSolicitud;
					cTramitePNC += (cTramitePNC.equals("")) ? "" + iCveTramite
							: "," + iCveTramite;
					cModalidadPNC += (cModalidadPNC.equals("")) ? ""
							+ iCveModalidad : "," + iCveModalidad;
					cOrdenPNC += (cOrdenPNC.equals("")) ? vEtapa
							.getString("iOrden") : ","
							+ vEtapa.getString("iOrden");
					TVDinRep vDataPNCTmp = new TVDinRep();
					vDataPNCTmp.put("iEjercicio", iEjercicio);
					vDataPNCTmp.put("iCveUsuRegistro",
							vData.getInt("iCveUsuario"));
					vDataPNCTmp.put("iCveProducto", iPNC);
					vDataPNCTmp.put("lResuelto", 1);
					vDataPNCTmp.put("dtResolucion", tFecha.TodaySQL());
					vDataPNCTmp.put("iCveOficinaAsignado", iCveOficinaOrigen);
					vDataPNCTmp.put("iCveDeptoAsignado", iCveDeptoOrigen);
					vDataPNCTmp.put("iCveCausaPNC", iCausaReqNC);
					vDataPNCTmp.put("iCveUsuCorrige",
							vData.getInt("iCveUsuario"));
					vDataPNCTmp.put("cDscOtraCausa", "");
					vDataPNCTmp.put("lResuelto", 1);
					vDataPNCTmp.put("dtResolucion", tFecha.TodaySQL());

					try {
						vDataPNC = dRegistroPNC.insert(vDataPNCTmp, null);
					} catch (Exception ex) {
						ex.printStackTrace();
						vDataPNC = null;
					}
				}
				for (int j = 0; j < aReqTemp.length; j++) {
					aReqValor = aReqTemp[j].split("=");
					iRequisito = Integer.parseInt(aReqValor[0], 10);
					lPStmt2 = conn.prepareStatement(cSQLReq);
					lPStmt2.setInt(1, iEjercicio); // iEjercicio
					lPStmt2.setInt(2, iNumSolicitud); // iNumSolicitud
					lPStmt2.setInt(3, Integer.parseInt(aTramites[i], 10)); // iCveTramite
					lPStmt2.setInt(4, Integer.parseInt(aModalidades[i], 10)); // iCveModalidad
					lPStmt2.setInt(5, iRequisito); // iCveRequisito
					lPStmt2.setInt(6, 0); // iEjercicioFormato
					lPStmt2.setInt(7, 0); // iCveFormatoReg
					if ((aReqValor[1].replace('/', ',').split(","))[0]
							.equalsIgnoreCase("true")) {
						lPStmt2.setDate(8, tFecha.TodaySQL()); // dtRecepcion
						lPStmt2.setInt(9, vData.getInt("iCveUsuario")); // iCveUsuRecibe
					} else {
						lPStmt2.setNull(8, Types.DATE); // dtRecepcion
						lPStmt2.setInt(9, 0); // iCveUsuRecibe
					}
					lPStmt2.setNull(10, Types.DATE); // dtNotificacion
					lPStmt2.setString(11, ""); // cNumOficio
					lPStmt2.setNull(12, Types.DATE); // dtOficio
					lPStmt2.setInt(13, 0); // lValido
					try {
						lPStmt2.executeUpdate();
						lPStmt2.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					// Verifica si existen caracter�sticas para generar PNC
					if (vcCaracPNC.size() > 0) {
						if (vDataPNC != null && j == 0) {
							TVDinRep vPNCEtapa = new TVDinRep();
							vPNCEtapa.put("iEjercicio", iEjercicio);
							vPNCEtapa.put("iNumSolicitud", iNumSolicitud);
							vPNCEtapa.put("iCveTramite", iCveTramite);
							vPNCEtapa.put("iCveModalidad", iCveModalidad);
							vPNCEtapa.put("iCveEtapa", iCveEtapa);
							int iOrden = 1;
							if (vEtapa != null)
								iOrden = vEtapa.getInt("iOrden");
							vPNCEtapa.put("iOrden", iOrden);
							vPNCEtapa.put("iEjercicioPNC",
									vDataPNC.getInt("iEjercicio"));
							vPNCEtapa.put("iConsecutivoPNC",
									vDataPNC.getInt("iConsecutivoPNC"));
							try {
								if (aReqPNC == null)
									vPNCEtapa = dRegPNCEtapa.insert(vPNCEtapa,
											null);
							} catch (Exception ex) {
								vPNCEtapa = null;
							}
						}

						for (int m = 0; m < vcCaracPNC.size(); m++) {
							Vector vcReqCarac = (Vector) vcCaracPNC.get(m);
							Vector vcCarac = (Vector) vcReqCarac.get(1);
							if (Integer.parseInt(vcReqCarac.get(0).toString(),
									10) == iRequisito) {
								if (vDataPNC != null) {
									vDataReqCausa = new TVDinRep();
									vDataReqCausa.put("iEjercicio",
											vDataPNC.getInt("iEjercicio"));
									vDataReqCausa.put("iConsecutivoPNC",
											vDataPNC.getInt("iConsecutivoPNC"));
									vDataReqCausa.put("iCveProducto",
											vDataPNC.getInt("iCveProducto"));
									vDataReqCausa.put("iCveCausaPNC",
											vDataPNC.getInt("iCveCausaPNC"));
									vDataReqCausa.put("iCveRequisito", Integer
											.parseInt(vcReqCarac.get(0)
													.toString(), 10));
									try {
										vDataReqCausa = dRegReqXCausa.insert(
												vDataReqCausa, null);
									} catch (Exception ex) {
										vDataReqCausa = null;
									}
									// Insertar en TRARegReqXCausa con PNC
									// creado arriba
									TDTRARegCaracXReqPNC dRegCaracXReqPNC = new TDTRARegCaracXReqPNC();
									for (int n = 0; n < vcCarac.size(); n++) {
										if (vDataReqCausa != null) {
											vDataReqCausa.put(
													"iCveCaracteristica",
													Integer.parseInt(vcCarac
															.get(n).toString(),
															10));
											vDataReqCausa.put("cObs", "");
											try {
												dRegCaracXReqPNC.insert(
														vDataReqCausa, null);
											} catch (Exception ex) {
												vDataReqCausa = null;
											}
										}
									}
								}
								break;
							}
						}
					}// Termina generacion de PNC por caracter�sticas no
						// conformes.
				}
				if (cnNested == null)
					conn.commit();
			}
			// modificar fecha de entrega
			if (aTramites.length > 0) {
				TDGRLDiaNoHabil dnhObjeto = new TDGRLDiaNoHabil();
				java.sql.Date dtEstimado = (dtTramitePrincipal != null) ? dnhObjeto
						.getFechaFinal(dtTramitePrincipal, iMaxDias, false)
						: null;
			}
			// Registro de PNC por requisitos obligatorios incompletos
			if (aReqPNC != null) {
				TVDinRep vDataPNCTmp = new TVDinRep();
				vDataPNCTmp.put("iEjercicio", iEjercicio);
				vDataPNCTmp.put("iCveUsuRegistro", vData.getInt("iCveUsuario"));
				vDataPNCTmp.put("iCveProducto", iPNC);
				vDataPNCTmp.put("lResuelto", 1);
				vDataPNCTmp.put("dtResolucion", tFecha.TodaySQL());
				vDataPNCTmp.put("iCveOficinaAsignado", iCveOficinaOrigen);
				vDataPNCTmp.put("iCveDeptoAsignado", iCveDeptoOrigen);
				vDataPNCTmp.put("iCveCausaPNC", iCausaReqInc);
				vDataPNCTmp.put("iCveUsuCorrige", vData.getInt("iCveUsuario"));
				vDataPNCTmp.put("cDscOtraCausa", "");
				vDataPNCTmp.put("lResuelto", 1);
				vDataPNCTmp.put("dtResolucion", tFecha.TodaySQL());
				try {
					vDataPNC = dRegistroPNC.insert(vDataPNCTmp, null);
				} catch (Exception ex) {
					vDataPNC = null;
				}
				if (vDataPNC != null) {
					TVDinRep vPNCEtapa = new TVDinRep();
					vPNCEtapa.put("iEjercicio", iEjercicio);
					vPNCEtapa.put("iCveEtapa", iCveEtapa);
					vPNCEtapa.put("iEjercicioPNC",
							vDataPNC.getInt("iEjercicio"));
					vPNCEtapa.put("iConsecutivoPNC",
							vDataPNC.getInt("iConsecutivoPNC"));

					try {
						vPNCEtapa.put("iNumSolicitud", iNumSolicitud);
						vPNCEtapa.put("iCveTramite", iCveTramite);
						vPNCEtapa.put("iCveModalidad", iCveModalidad);
						vPNCEtapa = dRegPNCEtapa.insert(vPNCEtapa, null);
					} catch (Exception ex) {

					}

					for (int i = 0; i < aReqPNC.length; i++) {
						vDataPNC.put("iCveRequisito",
								Integer.parseInt(aReqPNC[i], 10));
						try {
							dRegReqXCausa.insert(vDataPNC, null);
						} catch (Exception ex) {
							vDataReqCausa = null;
						}
					}
				}
				// aca voy a meter el codigo para insertar en etapaspncreg o
				// algo asi
			}

			if (cnNested == null)
				conn.commit();

			vData.put("cNumSolicitudes", cNumSolicitudes);
			try {
				this.insertSolicitudesRel(vData, null);
			} catch (Exception ex) {
				cMensaje = ex.getMessage();
			}

		} catch (SQLException ex) {
			cMensaje = getSQLMessages(ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} catch (Exception ex) {
			cMensaje = ex.getMessage();
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null)
					lPStmt.close();
				if (lPStmt2 != null)
					lPStmt2.close();
				if (cnNested == null) {
					if (conn != null)
						conn.close();
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			if (!lSuccess)
				throw new DAOException(cMensaje);

			return vData;
		}
	}

	public java.sql.Date getFechaLimiteEntregaDocs(int iCveTramite,
			int iCveModalidad, java.sql.Date dtIni) {
		java.sql.Date dtLimiteEntregaDocs = null;
		Vector vData = new Vector();
		String cSQL = "SELECT iNumDiasCubrirReq,lDiasNaturalesReq "
				+ "FROM TRAConfiguraTramite " + "WHERE iCveTramite = "
				+ iCveTramite + "  AND iCveModalidad = " + iCveModalidad
				+ "  AND dtiniVigencia in ("
				+ "select max(dtIniVigencia) from TraConfiguraTramite "
				+ "WHERE iCveTramite = " + iCveTramite
				+ " AND iCveModalidad = " + iCveModalidad + ")";
		TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();

		try {
			vData = this.findByCustom("", cSQL);
			if (vData.size() > 0) {
				
				
				TVDinRep dDato = (TVDinRep) vData.get(0);
				
				cSQL="select DISTINCT (current_date + "+dDato.getInt("iNumDiasCubrirReq")+" days) as dtLim from GRLCAUSAPNC" ;
				
				vData = this.findByCustom("", cSQL);
				
				TVDinRep dDato2 = (TVDinRep) vData.get(0);
				
				dtLimiteEntregaDocs = dDato2.getDate("dtLim"); //CALCULO DE LIMITE DE ENTREGA ADV
				
				/*
				dtLimiteEntregaDocs = dDiaNoHabil
						.getFechaFinal(
								dtIni,
								dDato.getInt("iNumDiasCubrirReq"),
								(dDato.getInt("lDiasNaturalesReq") == 0) ? false
										: true);*/
			}
		} catch (Exception e) {
			cMensaje = e.getMessage();
		}
		return dtLimiteEntregaDocs;
	}

	public int getNumDiasResol(int iCveTramite, int iCveModalidad) {
		Vector vData = new Vector();
		int iDiasRes = 0;
		String cSQL = "SELECT iNumDiasResol " + "FROM TRAConfiguraTramite "
				+ "WHERE iCveTramite = " + iCveTramite
				+ "  AND iCveModalidad = " + iCveModalidad
				+ "  AND dtiniVigencia in ("
				+ "select max(dtIniVigencia) from TraConfiguraTramite "
				+ "WHERE iCveTramite = " + iCveTramite
				+ " AND iCveModalidad = " + iCveModalidad + ")";
		try {
			vData = this.findByCustom("", cSQL);
			if (vData.size() > 0) {
				TVDinRep dDato = (TVDinRep) vData.get(0);
				iDiasRes = dDato.getInt("iNumDiasResol");

			}
		} catch (Exception e) {
			cMensaje = e.getMessage();
		}
		return iDiasRes;
	}

	public java.sql.Date getFechaLimiteResolucion(int iCveTramite,
			int iCveModalidad, java.sql.Date dtIni) {
		java.sql.Date dtLimiteEntregaResol = null;
		Vector vData = new Vector();
		String cSQL = "SELECT iNumDiasResol,lDiasNaturalesResol "
				+ "FROM TRAConfiguraTramite " + "WHERE iCveTramite = "
				+ iCveTramite + "  AND iCveModalidad = " + iCveModalidad
				+ "  AND dtiniVigencia in ("
				+ "select max(dtIniVigencia) from TraConfiguraTramite "
				+ "WHERE iCveTramite = " + iCveTramite
				+ " AND iCveModalidad = " + iCveModalidad + ")";
		TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();

		try {
			vData = this.findByCustom("", cSQL);
			if (vData.size() > 0) {
				TVDinRep dDato = (TVDinRep) vData.get(0);
				dtLimiteEntregaResol = dDiaNoHabil.getFechaFinal(dtIni, dDato
						.getInt("iNumDiasResol"), (dDato
						.getInt("lDiasNaturalesResol") == 0) ? false : true);
			}
		} catch (Exception e) {
			cMensaje = e.getMessage();
		}
		return dtLimiteEntregaResol;
	}

	public String getOficinaDeptoResuelve(int iCveOficinaOrigen, int iCveTramite) {
		String cOficinaDepto = "";
		Vector vData = new Vector();
		String cSQL = "SELECT iCveOficinaResuelve,iCveDeptoResuelve "
				+ "FROM TRATramiteXOfic " + "WHERE iCveOficina = "
				+ iCveOficinaOrigen + "  AND iCveTramite = " + iCveTramite;
		try {
			vData = this.findByCustom("", cSQL);
			if (vData.size() > 0) {
				TVDinRep dDato = (TVDinRep) vData.get(0);
				cOficinaDepto = dDato.getString("iCveOficinaResuelve");
				cOficinaDepto += "," + dDato.getString("iCveDeptoResuelve");
			} else
				cOficinaDepto = iCveOficinaOrigen + ","
						+ VParametros.getPropEspecifica("DeptoVentanillaUnica");
		} catch (Exception e) {
			cMensaje = e.getMessage();
		}
		return cOficinaDepto;
	}

	public java.sql.Date getFechaLimiteTraslado(int iCveOficinaOrigen,
			int iCveTramite, int iCveModalidad, java.sql.Date dtIni) {
		java.sql.Date dtLimiteTraslado = null;
		Vector vData = new Vector();
		String cOficDestino = this.getOficinaDeptoResuelve(iCveOficinaOrigen,
				iCveTramite);
		String[] aOficDepto = cOficDestino.split(",");
		if (aOficDepto.length == 2)
			cOficDestino = aOficDepto[0];
		else
			cOficDestino = iCveOficinaOrigen + "";
		String cSQL = "SELECT iNumDiasTraslado,lDiasNaturalesTraslado "
				+ "FROM TRATiempoTraslado " + "WHERE iCveTramite = "
				+ iCveTramite + "  AND iCveModalidad = " + iCveModalidad
				+ "  AND iCveOficinaOrigen = " + iCveOficinaOrigen
				+ "  AND iCveOficinaDestino = " + cOficDestino
				+ "  AND dtiniVigencia IN ("
				+ "select max(dtIniVigencia) from TraTiempoTraslado "
				+ "WHERE iCveTramite = " + iCveTramite
				+ "  AND iCveModalidad = " + iCveModalidad
				+ "  AND iCveOficinaOrigen = " + iCveOficinaOrigen
				+ "  AND iCveOficinaDestino = " + cOficDestino + ")";
		TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();

		try {
			vData = this.findByCustom("", cSQL);
			if (vData.size() > 0) {
				TVDinRep dDato = (TVDinRep) vData.get(0);
				dtLimiteTraslado = dDiaNoHabil.getFechaFinal(dtIni, dDato
						.getInt("iNumDiasTraslado"), (dDato
						.getInt("lDiasNaturalesTraslado") == 0) ? false : true);
			}
		} catch (Exception e) {
			cMensaje = e.getMessage();
		}
		return dtLimiteTraslado;
	}

	/**
	 * Elimina al registro a trav�s de la entidad dada por vData.
	 * <p>
	 * <b> delete from TRARegSolicitud where iEjercicio = ? AND iNumSolicitud =
	 * ? </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return boolean - En caso de ser o no eliminado el registro.
	 */
	public boolean delete(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		String cMsg = "";
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			// Ajustar Where de acuerdo a requerimientos...
			String lSQL = "delete from TRARegSolicitud where iEjercicio = ? AND iNumSolicitud = ?  ";
			// ...

			lPStmt = conn.prepareStatement(lSQL);

			lPStmt.setInt(1, vData.getInt("iEjercicio"));
			lPStmt.setInt(2, vData.getInt("iNumSolicitud"));

			lPStmt.executeUpdate();
			if (cnNested == null) {
				conn.commit();
			}
		} catch (SQLException sqle) {
			lSuccess = false;
			cMsg = "" + sqle.getErrorCode();
		} catch (Exception ex) {
			warn("delete", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("delete.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("delete.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException(cMsg);
			return lSuccess;
		}
	}

	/**
	 * Actualiza al registro con las modificaciones enviadas sobre la entidad
	 * (vData).
	 * <p>
	 * <b> update TRARegSolicitud set iCveTramite=?, iCveModalidad=?,
	 * iCveSolicitante=?, iCveRepLegal=?, cNomAutorizaRecoger=?,
	 * iCveUsuRegistra=?, tsRegistro=?, dtLimiteEntregaDocs=?,
	 * dtEstimadaEntrega=?, lPagado=?, dtEntrega=?, iCveUsuEntrega=?,
	 * lResolucionPositiva=?, lDatosPreregistro=?, iIdBien=?, iCveOficina=?,
	 * iCveDepartamento=?, iEjercicioCita=?, iIdCita=?, iCveForma=? where
	 * iEjercicio = ? AND iNumSolicitud = ? </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return TVDinRep - Entidad Modificada.
	 */
	public TVDinRep update(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		String[] aEjercicio;
		String[] aSolicitud;
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set lImpreso = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);
			aEjercicio = vData.getString("iEjercicio").split(",");
			aSolicitud = vData.getString("iNumSolicitud").split(",");

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			for (i = 0; i <= aEjercicio.length - 1; i++) {
				lPStmt.setInt(1, 1);
				lPStmt.setInt(2, Integer.parseInt(aEjercicio[i], 10));
				lPStmt.setInt(3, Integer.parseInt(aSolicitud[i], 10));
				lPStmt.executeUpdate();
				debug(" Ejercicio " + aEjercicio[i] + " Solicitud "
						+ aSolicitud[i]);
			}
			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep cambiaBien(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		TDGRLBitacora bitacora = new TDGRLBitacora();
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set iIdBien = ?,cDscBien = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));
			lPStmt.setInt(1, vData.getInt("iCveVehiculo"));
			lPStmt.setString(2, vData.getString("cNomEmbarcacion"));
			lPStmt.setInt(3, vData.getInt("iEjercicio"));
			lPStmt.setInt(4, vData.getInt("iNumSolicitud"));
			lPStmt.executeUpdate();

			String cLigadas = "SELECT "
					+ "INUMSOLICITUDPRINC,INUMSOLICITUDREL FROM TRASOLICITUDREL "
					+ "where iejercicio="
					+ vData.getInt("iEjercicio")
					+ " and INUMSOLICITUDPRINC=( "
					+ "SELECT INUMSOLICITUDPRINC FROM TRASOLICITUDREL where iejercicio="
					+ vData.getInt("iEjercicio") + " and INUMSOLICITUDREL="
					+ vData.getInt("iNumSolicitud") + ") ";
			Vector vcLigadas = this.findByNessted("", cLigadas, conn);
			for (int iL = 0; iL < vcLigadas.size(); iL++) {
				TVDinRep vLigadas = new TVDinRep();
				vLigadas = (TVDinRep) vcLigadas.get(iL);
				PreparedStatement lPStmt1 = null;
				String lSQL1 = "update TRARegSolicitud set iIdBien = ?,cDscBien = ? where iEjercicio = ? AND iNumSolicitud = ? ";
				lPStmt1 = conn.prepareStatement(lSQL1);
				lPStmt1.setInt(1, vData.getInt("iCveVehiculo"));
				lPStmt1.setString(2, vData.getString("cNomEmbarcacion"));
				lPStmt1.setInt(3, vData.getInt("iEjercicio"));
				lPStmt1.setInt(4, vLigadas.getInt("INUMSOLICITUDREL"));
				lPStmt1.executeUpdate();
				conn.commit();
			}

			TVDinRep Bita = new TVDinRep();
			Bita.put("iCveUsuario", vData.getInt("iCveUsuario"));
			Bita.put("cTablaAfecta", "TRARegSolicitud");
			Bita.put(
					"cLlaveReg",
					vData.getString("iEjercicio") + "/"
							+ vData.getString("iNumSolicitud"));
			Bita.put("iTipoMov", 2);
			bitacora.insert(Bita, conn);

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep cambiaResolucion(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		TDGRLBitacora bitacora = new TDGRLBitacora();
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set LRESOLUCIONPOSITIVA = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));
			lPStmt.setInt(1, vData.getInt("lPositiva"));
			lPStmt.setInt(2, vData.getInt("iEjercicio"));
			lPStmt.setInt(3, vData.getInt("iNumSolicitud"));
			lPStmt.executeUpdate();

			TVDinRep Bita = new TVDinRep();
			Bita.put("iCveUsuario", vData.getInt("iCveUsuario"));
			Bita.put("cTablaAfecta", "TRARegSolicitud");
			Bita.put(
					"cLlaveReg",
					vData.getString("iEjercicio") + "/"
							+ vData.getString("iNumSolicitud"));
			Bita.put("iTipoMov", 2);
			bitacora.insert(Bita, conn);

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep integraSol(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			// String cSQL =
			// "Select traregsolicitud where idbien="+vData.getInt("iCveVehiculoO");
			String lSQL = "update TRARegSolicitud set iIdBien = ?,cDscBien = ? where iEjercicio = ? and iNumSolicitud=?";
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1, vData.getInt("iCveVehiculo"));
			lPStmt.setString(2, vData.getString("cNomEmbarcacion"));
			lPStmt.setInt(3, vData.getInt("iEjercicioOri"));
			lPStmt.setInt(4, vData.getInt("iNumSolicitudOri"));

			lPStmt.executeUpdate();
			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep integraSolicitante(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			String cSql = "";

			// String cSQL =
			// "Select traregsolicitud where idbien="+vData.getInt("iCveVehiculoO");
			String lSQL = "update TRARegSolicitud set iCveSolicitante = ?,iCveRepLegal=null,iCveDomicilioSolicitante=1 where iEjercicio = ? and iNumSolicitud=?";
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1, vData.getInt("iCvePersonaD"));
			lPStmt.setInt(2, vData.getInt("iEjercicioOri"));
			lPStmt.setInt(3, vData.getInt("iNumSolicitudOri"));

			lPStmt.executeUpdate();
			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep cambiaSolicitante(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		TDGRLBitacora bitacora = new TDGRLBitacora();
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set iCveSolicitante=?,iCveRepLegal=? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));
			lPStmt.setInt(1, vData.getInt("iCveSolicitante"));
			lPStmt.setInt(2, vData.getInt("iCveRepLegal"));
			lPStmt.setInt(3, vData.getInt("iEjercicio"));
			lPStmt.setInt(4, vData.getInt("iNumSolicitud"));

			lPStmt.executeUpdate();

			TVDinRep Bita = new TVDinRep();
			Bita.put("iCveUsuario", vData.getInt("iCveUsuario"));
			Bita.put("cTablaAfecta", "TRARegSolicitud");
			Bita.put(
					"cLlaveReg",
					vData.getString("iEjercicio") + ","
							+ vData.getString("iNumSolicitud"));
			Bita.put("iTipoMov", 2);
			bitacora.insert(Bita, conn);

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep agregaSolicitante(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		TDGRLBitacora bitacora = new TDGRLBitacora();
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set iCveSolicitante=?,iCveRepLegal=?,ICVEDOMICILIOSOLICITANTE=?,ICVEDOMICILIOREPLEGAL=? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));
			lPStmt.setInt(1, vData.getInt("iCvePersona"));
			lPStmt.setInt(2, vData.getInt("iCveRepLegal"));
			lPStmt.setInt(3, vData.getInt("iCveDomPersona"));
			lPStmt.setInt(4, vData.getInt("iCveDomRepLegal"));
			lPStmt.setInt(5, vData.getInt("iEjercicio"));
			lPStmt.setInt(6, vData.getInt("iNumSolicitud"));
			lPStmt.executeUpdate();

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep updateImpreso(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		String[] aSolicitudes;
		int i;
		boolean lSuccess = true;
		TFechas tFecha = new TFechas();

		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set lImpreso = ?, dtImpresion = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);
			aSolicitudes = vData.getString("cFiltroImpreso").split("/");
			String[] aActual;

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			for (i = 0; i <= aSolicitudes.length - 1; i++) {
				aActual = aSolicitudes[i].split(",");
				if (aActual.length == 2) {
					lPStmt.setInt(1, vData.getInt("lImpreso"));
					lPStmt.setDate(2, tFecha.TodaySQL());
					lPStmt.setInt(3, Integer.parseInt(aActual[0], 10));
					lPStmt.setInt(4, Integer.parseInt(aActual[1], 10));
					lPStmt.executeUpdate();
				} else
					cMensaje = "Error en datos para actualizar campo impreso y fecha impresion";
			}

			if (cnNested == null) {
				conn.commit();
			}
		} catch (SQLException ex) {
			cMensaje = getSQLMessages(ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException(cMensaje);

			return vData;
		}
	}

	public TVDinRep updEtapas(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			if (vData.getInt("lResolucion") < 0) {
				String lSQL = "update TRARegSolicitud set lResolucionPositiva = ? where iEjercicio = ? AND iNumSolicitud = ? ";
				lPStmt = conn.prepareStatement(lSQL);

				vData.addPK(vData.getString("iEjercicio"));
				vData.addPK(vData.getString("iNumSolicitud"));

				lPStmt.setInt(1, vData.getInt("lResolucion"));
				lPStmt.setInt(2, vData.getInt("iEjercicio"));
				lPStmt.setInt(3, vData.getInt("iNumSolicitud"));
				lPStmt.executeUpdate();
			} else {
				String lSQL = "update TRARegSolicitud set lResolucionPositiva = ? , lpermiso = ? where iEjercicio = ? AND iNumSolicitud = ? ";
				lPStmt = conn.prepareStatement(lSQL);

				vData.addPK(vData.getString("iEjercicio"));
				vData.addPK(vData.getString("iNumSolicitud"));

				lPStmt.setInt(1, vData.getInt("lResolucion"));
				lPStmt.setInt(2, vData.getInt("lResolucion"));
				lPStmt.setInt(3, vData.getInt("iEjercicio"));
				lPStmt.setInt(4, vData.getInt("iNumSolicitud"));
				lPStmt.executeUpdate();

			}

			if (cnNested == null)
				conn.commit();

		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep updateImpresoB(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		String[] aSolicitudes;
		TFechas tFecha = new TFechas();
		Vector vEtapasXTra = new Vector();
		Vector vOficDepto = new Vector();
		TDTRARegEtapasXModTram dEtapas = new TDTRARegEtapasXModTram();
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set dtEntrega = ?, iCveUsuEntrega = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);
			aSolicitudes = vData.getString("cFiltroImpreso").split("/");
			String[] aActual;

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			for (i = 0; i <= aSolicitudes.length - 1; i++) {
				aActual = aSolicitudes[i].split(",");
				if (aActual.length == 4) {
					lPStmt.setDate(1, tFecha.TodaySQL());
					lPStmt.setInt(2, vData.getInt("idUser"));
					lPStmt.setInt(3, Integer.parseInt(aActual[0], 10));
					lPStmt.setInt(4, Integer.parseInt(aActual[1], 10));
					lPStmt.executeUpdate();
					if (cnNested == null)
						conn.commit();

					String cSQL = "SELECT * FROM TRAETAPAXMODTRAM "
							+ "where iCveTramite = " + aActual[2]
							+ " and ICVEMODALIDAD = " + aActual[3]
							+ " and icveetapa = "
							+ VParametros.getPropEspecifica("EtapaDocRetorno");

					vEtapasXTra = this.findByCustom("", cSQL);

					if (vEtapasXTra.size() > 0) {
						String cOficDep = "SELECT "
								+ "ICVEOFICINA,ICVEDEPARTAMENTO "
								+ "FROM TRAREGETAPASXMODTRAM "
								+ "where IEJERCICIO = "
								+ aActual[0]
								+ " and INUMSOLICITUD = "
								+ aActual[1]
								+ " and icveetapa = "
								+ VParametros
										.getPropEspecifica("EtapaRecepcion");
						vOficDepto = this.findByCustom("", cOficDep);
						TVDinRep vOficDep = (TVDinRep) vOficDepto.get(0);

						TVDinRep vCambiaEtapa = new TVDinRep();
						vCambiaEtapa.put("iEjercicio",
								Integer.parseInt(aActual[0], 10));
						vCambiaEtapa.put("iNumSolicitud",
								Integer.parseInt(aActual[1], 10));
						vCambiaEtapa.put("iCveTramite",
								Integer.parseInt(aActual[2], 10));
						vCambiaEtapa.put("iCveModalidad",
								Integer.parseInt(aActual[3], 10));
						vCambiaEtapa
								.put("iCveEtapa",
										Integer.parseInt(VParametros
												.getPropEspecifica("EtapaEntregaResol")));
						vCambiaEtapa.put("iCveOficina",
								vOficDep.getInt("ICVEOFICINA"));
						vCambiaEtapa.put("iCveDepartamento",
								vOficDep.getInt("ICVEDEPARTAMENTO"));
						vCambiaEtapa.put("iCveUsuario", vData.getInt("idUser"));
						vCambiaEtapa.put("lAnexo", 1);
						dEtapas.cambiarEtapa(vCambiaEtapa, false, " ");

					} else {
						for (int j = 0; j < 2; j++) {
							TVDinRep vCambiaEtapa = new TVDinRep();
							int iCveEtapa = 0;
							if (j == 0)
								iCveEtapa = Integer
										.parseInt(VParametros
												.getPropEspecifica("EtapaEntregaResol"));
							else
								iCveEtapa = Integer
										.parseInt(VParametros
												.getPropEspecifica("EtapaConclusionTramite"));

							vCambiaEtapa.put("iEjercicio",
									Integer.parseInt(aActual[0], 10));
							vCambiaEtapa.put("iNumSolicitud",
									Integer.parseInt(aActual[1], 10));
							vCambiaEtapa.put("iCveTramite",
									Integer.parseInt(aActual[2], 10));
							vCambiaEtapa.put("iCveModalidad",
									Integer.parseInt(aActual[3], 10));
							vCambiaEtapa.put("iCveEtapa", iCveEtapa);
							vCambiaEtapa
									.put("iCveOficina",
											Integer.parseInt(VParametros
													.getPropEspecifica("OficinaCentral")));
							vCambiaEtapa
									.put("iCveDepartamento",
											Integer.parseInt(VParametros
													.getPropEspecifica("DeptoVentanillaUnica")));
							vCambiaEtapa.put("iCveUsuario",
									vData.getInt("idUser"));
							vCambiaEtapa.put("lAnexo", 0);
							dEtapas.cambiarEtapa(vCambiaEtapa, false, " ");

						}
					}

				} else
					cMensaje = "Error en datos para actualizar campo impreso";
			}

		} catch (SQLException ex) {
			cMensaje = getSQLMessages(ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException(cMensaje);

			return vData;
		}
	}

	public TVDinRep updSolImprocedenteFaltaPago(TVDinRep vData,
			Connection cnNested) throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set dtEstimadaEntrega = ?, dtEntrega = ?, iCveUsuEntrega = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			lPStmt.setNull(1, Types.DATE);
			lPStmt.setDate(2, tFecha.TodaySQL());
			lPStmt.setInt(3, vData.getInt("iCveUsuEntrega"));
			lPStmt.setInt(4, vData.getInt("iEjercicio"));
			lPStmt.setInt(5, vData.getInt("iNumSolicitud"));
			lPStmt.executeUpdate();

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null)
					lPStmt.close();
				if (cnNested == null) {
					if (conn != null)
						conn.close();
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	/**
	 * Actualiza al registro con las modificaciones enviadas sobre la entidad
	 * (vData).
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return TVDinRep - Entidad Modificada. Autor: iCaballero Fecha:
	 *         2006.11.03
	 */
	public TVDinRep updateAutorizaRec(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set cObsTramite = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			lPStmt.setString(1, vData.getString("cObsTramite"));
			lPStmt.setInt(2, vData.getInt("iEjercicio"));
			lPStmt.setInt(3, vData.getInt("iNumSolicitud"));
			lPStmt.executeUpdate();

			if (cnNested == null)
				conn.commit();

		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	/**
	 * Actualiza al registro con las modificaciones enviadas sobre la entidad
	 * (vData).
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return TVDinRep - Entidad Modificada. Autor: iCaballero Fecha:
	 *         2006.11.08
	 */
	public TVDinRep updateEnviarResolucionA(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set iCveOficinaEnviaRes = ?, cEnviarResolucionA = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			lPStmt.setInt(1, vData.getInt("iCveOficina"));
			lPStmt.setString(2, vData.getString("cEnviarResolucionA"));
			lPStmt.setInt(3, vData.getInt("iEjercicio"));
			lPStmt.setInt(4, vData.getInt("iNumSolicitud"));
			lPStmt.executeUpdate();

			if (cnNested == null)
				conn.commit();

		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	/**
	 * Actualiza al registro con las modificaciones enviadas sobre la entidad
	 * (vData).
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return TVDinRep - Entidad Modificada. Autor: iCaballero Fecha:
	 *         2006.11.09
	 */
	public TVDinRep insertSolicitudesRel(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		String[] aSolicitudes = vData.getString("cNumSolicitudes").split(",");
		String aSolicitudesRel[] = new String[(aSolicitudes.length - 1)];
		int iNumSolicitudPrin = Integer.parseInt(aSolicitudes[0], 10);
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			for (int j = 0; j < aSolicitudes.length; j++)
				if (j >= 1)
					aSolicitudesRel[j - 1] = aSolicitudes[j];

			String lSQL = "insert into TRASolicitudRel(iEjercicio,iNumSolicitudPrinc,iNumSolicitudRel) values(YEAR(CURRENT DATE), ?,?)";
			for (int i = 0; i < aSolicitudesRel.length; i++) {
				lPStmt = conn.prepareStatement(lSQL);
				lPStmt.setInt(1, vData.getInt("iSolicitudPadre"));
				lPStmt.setInt(2,
						Integer.parseInt(aSolicitudesRel[i].trim(), 10));
				lPStmt.executeUpdate();
				lPStmt.close();
			}

			if (cnNested == null)
				conn.commit();

		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep insertSolicitudesRel2(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			String lSQL = "insert into TRASolicitudRel(iEjercicio,iNumSolicitudPrinc,iNumSolicitudRel) values(YEAR(CURRENT DATE), ?,?)";
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1, vData.getInt("iSolicitudPadre"));
			lPStmt.setInt(2, vData.getInt("iNumSolicitud"));
			lPStmt.executeUpdate();
			lPStmt.close();

			if (cnNested == null)
				conn.commit();

		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public int insertaCitaCIS(int oficinaUsr, int tramite, int modalidad,
			int solicitante, java.sql.Date dtCita) {
		TDCis dCis = new TDCis();
		int iCveCitaCis = 0;
		boolean lError = false, lEjecutaCIS = false, lInteresadoEncontrado = false;
		int iCveTramiteCis = 0, iCveOficinaCis = 0, iCvePersonaCIS = 0, iCveInteresadoCIS = 0;
		String cRFC = "", cApPaterno = "", cApMaterno = "", cNomPersona = "", cDomicilio = "";
		try {
			TVDinRep vOficinaCIS = new TVDinRep();
			TVDinRep vTramiteCIS = new TVDinRep();
			TVDinRep vPersonaCIS = new TVDinRep();
			TVDinRep vInteresadoCIS = new TVDinRep();
			Vector vcInteresado = new Vector();

			String cOficinaCIS = "SELECT iCveOficinaCIS FROM grloficina where iCveOficina = "
					+ oficinaUsr;
			String cTramiteCIS = "SELECT iCveTramiteCIS FROM TRACONFIGURATRAMITE where iCveTramite = "
					+ tramite + " AND iCVEModalidad =" + modalidad;
			String cPersonaCIS = "SELECT "
					+ "P.ICVEPERSONA,CNOMRAZONSOCIAL,cApPaterno,cApMaterno,cRFC,"
					+ "D.CCALLE,D.CNUMEXTERIOR,D.CNUMINTERIOR,D.ICVEPAIS,D.ICVEENTIDADFED,"
					+ "D.ICVEMUNICIPIO,D.CCODPOSTAL,D.CTELEFONO,P.CCORREOE "
					+ "FROM GRLPersona P "
					+ "LEFT JOIN GRLDOMICILIO D ON D.ICVEPERSONA = P.ICVEPERSONA "
					+ " Where P.iCvePersona = " + solicitante
					+ " ORDER BY D.LPREDETERMINADO DESC ";
			Vector vcTramiteCIS = this.findByCustom("", cTramiteCIS);
			Vector vcOficinaCIS = this.findByCustom("", cOficinaCIS);
			Vector vcPersonaCIS = this.findByCustom("", cPersonaCIS);

			if (vcOficinaCIS.size() > 0) {
				vOficinaCIS = (TVDinRep) vcOficinaCIS.get(0);
				iCveOficinaCis = vOficinaCIS.getInt("iCveOficinaCIS");
			}
			if (vcTramiteCIS.size() > 0) {
				vTramiteCIS = (TVDinRep) vcTramiteCIS.get(0);
				iCveTramiteCis = vTramiteCIS.getInt("iCveTramiteCIS");
			}
			if (vcPersonaCIS.size() > 0) {
				vPersonaCIS = (TVDinRep) vcPersonaCIS.get(0);
				iCvePersonaCIS = vPersonaCIS.getInt("iCveTramiteCIS");
				cRFC = vPersonaCIS.getString("cRFC");
				cApPaterno = vPersonaCIS.getString("cApPaterno");
				cApMaterno = vPersonaCIS.getString("cApMaterno");
				cNomPersona = vPersonaCIS.getString("CNOMRAZONSOCIAL");
				cDomicilio = vPersonaCIS.getString("cDomicilio") + " "
						+ vPersonaCIS.getString("cNumExterior");
				if (!vPersonaCIS.getString("cNumInterior").equals("")
						&& !vPersonaCIS.getString("cNumInterior")
								.equals("null"))
					cDomicilio += " " + vPersonaCIS.getString("cNumInterior");
			}
			if (lEjecutaCIS) {
				vcInteresado = dCis.consultaInteresadoRFC(cRFC);
				if (vcInteresado.size() > 0) {
					for (int in = 0; in < vcInteresado.size(); in++) {
						vInteresadoCIS = (TVDinRep) vcInteresado.get(in);
						if (cNomPersona == vInteresadoCIS
								.getString("cNomRazonSocial")
								&& cApPaterno == vInteresadoCIS
										.getString("cApPaterno")
								&& cApMaterno == vInteresadoCIS
										.getString("cApMaterno")) {
							iCveInteresadoCIS = vInteresadoCIS
									.getInt("iCveInteresado");
							lInteresadoEncontrado = true;
							break;
						}
					}
					if (!lInteresadoEncontrado) {
						for (int in = 0; in < vcInteresado.size(); in++) {
							vInteresadoCIS = (TVDinRep) vcInteresado.get(in);
							if (cNomPersona == vInteresadoCIS
									.getString("cNomRazonSocial")
									&& cApPaterno == vInteresadoCIS
											.getString("cApPaterno")) {
								iCveInteresadoCIS = vInteresadoCIS
										.getInt("iCveInteresado");
								lInteresadoEncontrado = true;
								break;
							}
						}
					}
					if (!lInteresadoEncontrado) {
						for (int in = 0; in < vcInteresado.size(); in++) {
							vInteresadoCIS = (TVDinRep) vcInteresado.get(in);
							if (cNomPersona == vInteresadoCIS
									.getString("cNomRazonSocial")) {
								iCveInteresadoCIS = vInteresadoCIS
										.getInt("iCveInteresado");
								lInteresadoEncontrado = true;
								break;
							}
						}
					}
				} else
					iCveInteresadoCIS = dCis.insertaInteresado(
							vPersonaCIS.getString("cRFC"), "",
							vPersonaCIS.getString("CNOMRAZONSOCIAL"),
							vPersonaCIS.getString("cApPaterno"),
							vPersonaCIS.getString("cApMaterno"),
							vPersonaCIS.getInt("iCvePais"),
							vPersonaCIS.getInt("iCveEntidadFed"),
							vPersonaCIS.getInt("iCveMunicipio"),
							vPersonaCIS.getString("cColonia"), cDomicilio,
							vPersonaCIS.getString("CCODPOSTAL"),
							vPersonaCIS.getString("CTELEFONO"),
							vPersonaCIS.getString("CCORREOE"));

			}

			if (iCveTramiteCis > 0 && iCveOficinaCis > 0) {

				// Invocacion WS CIS
				String wsdlUrl = VParametros.getPropEspecifica("URLWSCis")
						+ "InsertaCita?WSDL";
				// String wsdlUrl =
				// "http://10.33.141.136:7010/cisws/gob.sct.cisws.ws." +
				// "InsertaCita?WSDL";

				WSInsertaCita service = new WSInsertaCita_Impl(wsdlUrl);
				WSInsertaCitaPort port = service.getWSInsertaCitaPort();

				iCveCitaCis = port.insertaCita(
						VParametros.getPropEspecifica("ConWSCisUsr"),
						VParametros.getPropEspecifica("ConWSCisPwd"),
						iCveTramiteCis, 0, 1, cApPaterno + " " + cApMaterno
								+ " " + cNomPersona, iCveOficinaCis);
			}

		} catch (Exception ex) {
			lError = true;
			iCveCitaCis = 0;

		} finally {
		}
		return iCveCitaCis;
	}

	public TVDinRep updateEtapasCIS(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		PreparedStatement lPStmt0 = null;
		int idCita = 0;
		try {
			// Cita Insertada WS CIS
			idCita = this.insertaCitaCIS(vData.getInt("iCveOficina"),
					vData.getInt("iCveTramite"), vData.getInt("iCveModalidad"),
					vData.getInt("iCveSolicitante"), vData.getDate("dtCita"));
		} catch (Exception e) {
			e.printStackTrace();
			idCita = 0;
		}

		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String cInsertCita = "insert into TRADATOSCITA (iEjercicio,iIdCita,dtCita) values(?,?,?)";
			lPStmt0 = conn.prepareStatement(cInsertCita);
			lPStmt0.setInt(1, vData.getInt("iEjercicio"));
			lPStmt0.setInt(2, idCita);
			lPStmt0.setDate(3, vData.getDate("dtCita"));
			if (idCita > 0) {
				lPStmt0.executeUpdate();
				conn.commit();
				lPStmt0.close();
			}

			String lSQL = "update TRARegSolicitud set iEjercicioCita = ?, iIDCita = ? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			lPStmt.setInt(1, vData.getInt("iEjercicio"));
			lPStmt.setInt(2, idCita);
			lPStmt.setInt(3, vData.getInt("iEjercicio"));
			lPStmt.setInt(4, vData.getInt("iNumSolicitud"));
			if (idCita > 0) {
				lPStmt.executeUpdate();
				conn.commit();
				lPStmt.close();
			}

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null)
					lPStmt.close();
				if (cnNested == null) {
					if (conn != null)
						conn.close();
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");

			return vData;
		}

	}

	public java.sql.Date actualizaFechaCompromiso(int iEjercicio,
			int iNumSolicitud, int iCveTramite, int iCveModalidad,
			int iCveOficinaOrigen) throws Exception {
		java.sql.Timestamp tsRegistro = tFecha.getThisTime(false);
		java.sql.Date dtRegistro = tFecha.getDateSQL(tFecha.getThisTime());
		java.sql.Date dtEstimadaEntrega = null;
		String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
		String iEtapaDespNotif = VParametros
				.getPropEspecifica("EtapaRecepcionDespuesNotificacion");
		String iEtapaModificacion = VParametros
				.getPropEspecifica("EtapaModificarTramite");
		DbConnection dbConn = null;
		Connection cnNested = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		boolean lUpdateEtapa = false;

		String cSQLdtEstPrin = "";
		String cRevEtapaNotif = "";
		dtEstimadaEntrega = this.getFechaLimiteTraslado(iCveOficinaOrigen,
				iCveTramite, iCveModalidad, dtRegistro);
		if (dtEstimadaEntrega == null) {
			dtEstimadaEntrega = dtRegistro;
		}

		cSQLdtEstPrin = " UPDATE TRARegSolicitud SET dtEstimadaEntrega= ? "
				+ " WHERE iEjercicio=? AND iNumSolicitud= ? ";
		cRevEtapaNotif = "SELECT EM.ICVEETAPA,S.LIMPRESO,S.dtEstimadaEntrega FROM TRAREGETAPASXMODTRAM EM "
				+ "JOIN TRAREGSOLICITUD S ON S.IEJERCICIO = EM.IEJERCICIO "
				+ " AND S.INUMSOLICITUD=EM.INUMSOLICITUD "
				+ " where S.IEJERCICIO="
				+ iEjercicio
				+ " and S.INUMSOLICITUD="
				+ iNumSolicitud + " ORDER BY EM.iORDEN DESC";
		Vector vcEtapaNotif = this.findByCustom("", cRevEtapaNotif);
		if (vcEtapaNotif.size() > 0) {
			TVDinRep vEtapaNotif = (TVDinRep) vcEtapaNotif.get(0);
			if (vEtapaNotif.getInt("LIMPRESO") == 0
					|| vEtapaNotif.getInt("iCveEtapa") == Integer
							.parseInt(iEtapaDespNotif)
					|| vEtapaNotif.getInt("iCveEtapa") == Integer
							.parseInt(iEtapaModificacion)) {
				lUpdateEtapa = true;
				dtEstimadaEntrega = this.getFechaLimiteResolucion(iCveTramite,
						iCveModalidad, dtEstimadaEntrega);
			} else {
				dtEstimadaEntrega = vEtapaNotif.getDate("dtEstimadaEntrega");
			}
		}

		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			lPStmt = conn.prepareStatement(cSQLdtEstPrin);
			lPStmt.setDate(1, dtEstimadaEntrega);
			lPStmt.setInt(2, iEjercicio);
			lPStmt.setInt(3, iNumSolicitud);
			if (lUpdateEtapa == true) {
				lPStmt.executeUpdate();
				lPStmt.close();
				if (cnNested == null) {
					conn.commit();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			warn("insert", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
				warn("insert.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
		}
		return dtEstimadaEntrega;
	}

	/**
	 * Inserta el registro dado por la entidad vData.
	 * <p>
	 * <b> insert into
	 * TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad
	 * ,iCveSolicitante
	 * ,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro
	 * ,dtLimiteEntregaDocs
	 * ,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva
	 * ,lDatosPreregistro
	 * ,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma)
	 * values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iEjercicio,iNumSolicitud, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Din�mico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexi�n anidada que permite que el m�todo se
	 *            encuentre dentro de una transacci�n mayor.
	 * @throws DAOException
	 *             - Excepci�n de tipo DAO
	 * @return TVDinRep - VO Din�mico que contiene a la entidad a Insertada, as�
	 *         como a la llave de esta entidad.
	 */
	public synchronized TVDinRep insertBatch2(TVDinRep vData,
			Connection cnNested) throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null, lPStmt2 = null;
		boolean lSuccess = true;
		String cNumSolicitudes = "";

		TFechas tFecha = new TFechas();
		Vector vcDataMax = new Vector();
		int iNumSolicitud = 1;

		// LEL06122006 C�lculo de Fecha Estimada de Entrega para tr�mites
		// relacionados
		java.sql.Date dtIniCalculo;
		java.sql.Date dtFinCalculo;
		java.sql.Date dtTramitePrincipal = null;
		int iEjerPrincipal = 0;
		int iNumSolPrincipal = 0;
		int iMaxDias = 0;
		int iNumDias = 0;
		int iMaxTramite = 0;
		int iMaxModalidad = 0;
		Vector vcSolicitudes = new Vector();
		Vector vcSolicitud = null;
		// Fin LEL06122006
		java.sql.Timestamp tsRegistro = tFecha.getThisTime(false);
		java.sql.Date dtRegistro = tFecha.getDateSQL(tsRegistro);
		TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();
		TDTRARegEtapasXModTram dEtapaIni = new TDTRARegEtapasXModTram();
		TDTRARegPNCEtapa dRegPNCEtapa = new TDTRARegPNCEtapa();
		java.sql.Date dtInicioCalculos = dDiaNoHabil.getFechaFinal(dtRegistro,
				0, false);
		int iEjercicio = tFecha.getIntYear(dtRegistro);
		int iCveTramite = 0, iCveModalidad = 0, iCveOficinaOrigen, iCveDeptoOrigen;
		String cOficinaResuelve, cDeptoResuelve, cSolicitudPNC = "", cTramitePNC = "", cModalidadPNC = "", cOrdenPNC = "";
		boolean lReqCompletos;
		int iCveEtapa = Integer.parseInt(
				VParametros.getPropEspecifica("EtapaRegistro"), 10);
		int iPNC = Integer.parseInt(
				VParametros.getPropEspecifica("VentanillaProductoRecepcion"),
				10);
		int iCausaReqInc = Integer.parseInt(VParametros
				.getPropEspecifica("VentanillaCausaRequisitoIncompleto"), 10);
		int iCausaReqNC = Integer.parseInt(VParametros
				.getPropEspecifica("VentanillaCausaRequisitoNoConforme"), 10);


		String[] aTramites, aModalidades, aRequisitos, aReqTemp, aReqValor, aEnvObl, aCarac, aReqCarac, aCaracTemp, aCarValor;//,aFisico;
		Vector vcCaracPNC = new Vector();
		// String cSQLMax =
		// "SELECT MAX(iNumSolicitud) AS iNumSolicitud FROM TRARegSolicitud WHERE iEjercicio = "
		// + iEjercicio;
		String cSQLTra = "INSERT INTO TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,"
				+ "iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,"
				+ "dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,"
				+ "iEjercicioCita,iIdCita,iCveForma,lPrincipal,lImpreso,iCveDomicilioSolicitante,iCveDomicilioRepLegal,"
				+ "cEnviarResolucionA,cObsTramite,cDscBien,iCveOficinaEnviaRes,dUnidadesCalculo)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String cSQLReq = "INSERT INTO TRARegReqXTram (iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,dtNotificacion,cNumOficio,dtOficio,lValido,lFisico)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String cSQLdtEstPrin = " UPDATE TRARegSolicitud SET dtEstimadaEntrega=? "
				+ " WHERE iEjercicio=? AND iNumSolicitud=? ";
		Vector vcOficXDepto = this.findByNessted(
				"",
				"SELECT iCveOficina,iCveDepartamento FROM GRLDEPTOXOFIC where ICVEOFICINA = "
						+ vData.getInt("iCveOficinaUsr")
						+ " AND ICVEDEPARTAMENTO = "
						+ vData.getInt("iCveDeptoUsr"), conn);

		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				// conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
				conn.setTransactionIsolation(2);
			}
			iCveOficinaOrigen = vData.getInt("iCveOficinaUsr");
			if (vcOficXDepto.size() > 0)
				iCveDeptoOrigen = vData.getInt("iCveDeptoUsr");
			else
				iCveDeptoOrigen = Integer.parseInt(VParametros
						.getPropEspecifica("DeptoVentanillaUnica"));
			TVDinRep vDataPNC = new TVDinRep();
			TDGRLRegistroPNC dRegistroPNC = new TDGRLRegistroPNC();
			TVDinRep vDataReqCausa = new TVDinRep();
			TDTRARegReqXCausa dRegReqXCausa = new TDTRARegReqXCausa();

			aTramites = vData.getString("ClavesTramite").split(",");
			aModalidades = vData.getString("ClavesModalidad").split(",");

			aRequisitos = vData.getString("iCveRequisito").split(":");
			
			//aFisico = vData.getString("cFisico").split(",");
			
			if (!vData.getString("Caracteristicas").equals(""))
				aCarac = vData.getString("Caracteristicas").split(":");
			else
				aCarac = null;
			Vector vcTemp;

			if (aCarac != null) {
				for (int i = 0; i < aCarac.length; i++) {
					aReqCarac = aCarac[i].split("/");
					aCaracTemp = aReqCarac[1].split(",");
					vcTemp = new Vector();
					for (int j = 0; j < aCaracTemp.length; j++) {
						aCarValor = aCaracTemp[j].split("=");
						if (aCarValor[1].equalsIgnoreCase("true"))
							vcTemp.add(aCarValor[0]);
					}
					if (vcTemp.size() > 0) {
						Vector vcAgregar = new Vector();
						vcAgregar.add(aReqCarac[0]);
						vcAgregar.add(vcTemp);
						vcCaracPNC.add(vcAgregar);
					}
				}
			}

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iNumSolicitud"));

			for (int i = 0; i < aTramites.length; i++) {
				aReqTemp = aRequisitos[i].split(",");

				lReqCompletos = true;
				for (int j = 0; j < aReqTemp.length; j++) {
					aReqValor = aReqTemp[j].split("=");
					aEnvObl = aReqValor[1].replace('/', ',').split(",");
				}

				/********* Prepara Statement de creaci�n de solicitud. */
				iCveTramite = Integer.parseInt(aTramites[i], 10);
				iCveModalidad = Integer.parseInt(aModalidades[i], 10);
				String cOficDestino = this.getOficinaDeptoResuelve(
						iCveOficinaOrigen, iCveTramite);
				String[] aOficDepto = cOficDestino.split(",");
				if (aOficDepto.length == 2) {
					cOficinaResuelve = aOficDepto[0];
					cDeptoResuelve = aOficDepto[1];
				} else {
					cOficinaResuelve = iCveOficinaOrigen + "";
					cDeptoResuelve = iCveDeptoOrigen + "";
				}

				if (vcOficXDepto.size() <= 0)
					cDeptoResuelve = VParametros
							.getPropEspecifica("DeptoVentanillaUnica");

				/*
				 * if(i == 0){ vcDataMax = this.FindNextSolicitud("", cSQLMax,
				 * dataSourceName); if(vcDataMax.size() > 0) iNumSolicitud = (
				 * (TVDinRep) vcDataMax.get(0)).getInt("iNumSolicitud") + 1;
				 * else iNumSolicitud = 1; }else iNumSolicitud++;
				 */

				iNumSolicitud = TablaSecuenciaXAnio.getSecuencia(conn,
						"SOLICITUD", iEjercicio);

				cNumSolicitudes += (cNumSolicitudes.equals("")) ? ""
						+ iNumSolicitud : ", " + iNumSolicitud;

				/***************** INSERT SOLICITUD **********/
				lPStmt = conn.prepareStatement(cSQLTra);
				lPStmt.setInt(1, iEjercicio); // iEjercicio

				lPStmt.setInt(2, iNumSolicitud); // iNumSolicitud
				vData.put("iNumSolicitud", iNumSolicitud);

				// ejercicio para adv
				vData.put("iEjer", iEjercicio);
				//
				lPStmt.setInt(3, iCveTramite); // iCveTramite
				lPStmt.setInt(4, iCveModalidad); // iCveModalidad
				lPStmt.setInt(5, vData.getInt("iCveSolicitante")); // iCveSolicitante
				lPStmt.setInt(6, vData.getInt("iCveRepLegal")); // iCveRepLegal
				lPStmt.setString(7, vData.getString("cNomAutorizaRecoger")); // cNomAutorizaRecoger
				lPStmt.setInt(8, vData.getInt("iCveUsuario")); // iCveUsuRegistra
				lPStmt.setTimestamp(9, tsRegistro); // tsRegistro
				lPStmt.setDate(10, vData.getDate("dtLimEntregaDoctos")); // dtLimiteEntregaDocs
				lPStmt.setNull(11, Types.DATE);

				java.sql.Date dtEstimadaEntrega = null;

				if (i == 0) {
					dtTramitePrincipal = dtEstimadaEntrega; // Registro de Fecha
															// Entrega Principal
				} else {
					iNumDias = getNumDiasResol(iCveTramite, iCveModalidad);
					if (iNumDias > iMaxDias) {
						iMaxDias = iNumDias;
						iMaxTramite = iCveTramite;
						iMaxModalidad = iCveModalidad;
					}
				}
				vcSolicitud = new Vector();
				vcSolicitud.addElement(String.valueOf(iEjercicio));
				vcSolicitud.addElement(String.valueOf(iNumSolicitud));
				vcSolicitudes.addElement(vcSolicitud);

				lPStmt.setInt(12, 0); // lPagado
				lPStmt.setNull(13, Types.DATE); // dtEntrega
				lPStmt.setInt(14, 0); // iCveUsuEntrega
				lPStmt.setInt(15, 0); // lResolucionPositiva

				// modificar cuando se defina el preregistro
				lPStmt.setInt(16, 0); // lDatosPreregistro
				lPStmt.setInt(17, vData.getInt("iIdBien")); // iIdBien

				// lPStmt.setInt(18,iCveOficinaOrigen); // iCveOficina
				// lPStmt.setInt(19,iCveDeptoOrigen); // iCveDepartamento

				lPStmt.setInt(18, vData.getInt("iCveOficinaUsr")); // iCveOficina
																	// //para
																	// que
																	// guarde la
																	// ofic y
																	// dpto del
																	// user que
																	// registra
																	// la
																	// solicitud
				lPStmt.setInt(19, vData.getInt("iCveDeptoUsr")); // iCveDepartamento

				// modificar cuando se defina la forma de localizar la cita o de
				// registrarla
				// ya no se encontro, pero se crea la cita en una fase posterior
				// de este mismo proceso.
				lPStmt.setInt(20, 0); // iEjercicioCita
				lPStmt.setInt(21, 0); // iIdCita

				lPStmt.setInt(22, 0); // iCveForma

				lPStmt.setInt(23, vData.getInt("lPrinsipal")); // lPrincipal
				lPStmt.setInt(24, 0); // lImpreso
				lPStmt.setInt(25, vData.getInt("iCveDomicilioSolicitante")); // iCveDomicilioSolicitante
				lPStmt.setInt(26, vData.getInt("iCveDomicilioRepLegal")); // iCveDomicilioRepLegal
				lPStmt.setString(27, vData.getString("cEnviarResolucionA")); // cEnviaResolucionA
				lPStmt.setString(28, vData.getString("cObsTramite")); // cObsTramite
				lPStmt.setString(29, vData.getString("cDscBien")); // cDscBien

				if (vData.getInt("iCveOficina") == -1)
					lPStmt.setNull(30, Types.INTEGER);
				else
					lPStmt.setInt(30, vData.getInt("iCveOficina")); // Enviar
																	// resoluci�n
																	// a oficina

				lPStmt.setDouble(31, vData.getDouble("dUnidCalculo"));// dUnidCalculo

				try {
					lPStmt.executeUpdate();
					lPStmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (cnNested == null)
					conn.commit();
				
				/******** Inserta etapa inicial del tr�mite para el seguimiento */

				TVDinRep vEtapa = new TVDinRep();
				vEtapa.put("iEjercicio", iEjercicio);
				vEtapa.put("iNumSolicitud", iNumSolicitud);
				vEtapa.put("iCveTramite", iCveTramite);
				vEtapa.put("iCveModalidad", iCveModalidad);
				vEtapa.put("iCveEtapa", iCveEtapa);

				// vEtapa.put("iCveOficina",
				// Integer.parseInt(cOficinaResuelve,10));
				// vEtapa.put("iCveDepartamento",
				// Integer.parseInt(cDeptoResuelve,10));

				vEtapa.put("iCveOficina", vData.getInt("iCveOficinaUsr")); // para
																			// que
																			// guarde
																			// la
																			// etapa
																			// con
																			// la
																			// ofic
																			// y
																			// el
																			// dpto
																			// de
																			// quien
																			// genera
																			// la
																			// etapa
				vEtapa.put("iCveDepartamento", vData.getInt("iCveDeptoUsr"));

				vEtapa.put("iCveUsuario", vData.getInt("iCveUsuario"));
				vEtapa.put("tsRegistro", tsRegistro);
				vEtapa.put("lAnexo", 0);
				vEtapa.put(
						"cObservaciones",
						"Solicitud" + iEjercicio + "/" + iNumSolicitud
								+ " registrada con fecha:"
								+ tsRegistro.toString());
				try {
					vEtapa = dEtapaIni.insertEtapa(vEtapa, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// SE AGREGA LA ETAPA DE VISITA TECNICA AL INICIAR LA SOLICITUD
				vEtapa.put("iCveEtapa", Integer.parseInt(
						VParametros.getPropEspecifica("EtapaVisita"), 10));

				try {
					vEtapa = dEtapaIni.insertEtapa(vEtapa, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}

				/************** Incerta al CIS **************/
				TVDinRep vCIS = new TVDinRep();
				vCIS.put("iEjercicio", iEjercicio);
				vCIS.put("iNumSolicitud", iNumSolicitud);
				vCIS.put("iCveTramite", iCveTramite);
				vCIS.put("iCveModalidad", iCveModalidad);
				vCIS.put("iCveOficina", Integer.parseInt(cOficinaResuelve, 10));
				vCIS.put("iCveSolicitante", vData.getInt("iCveSolicitante"));
				vCIS.put("dtCita", tFecha.getDateSQL(tsRegistro));

				try {
					this.updateEtapasCIS(vCIS, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					dEtapaIni.incertaEstadoCita(iEjercicio, iNumSolicitud,
							iCveEtapa, conn);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (cnNested == null)
					conn.commit();

				/*************** REGISTRO DE REQUISITOS EN EL TR�MITE ***************/
				int iRequisito;

				String sRequisitos = "";
				

				for (int k = 0; k < aReqTemp.length; k++) {
					aReqValor = aReqTemp[k].split("=");
					iRequisito = Integer.parseInt(aReqValor[0], 10);
					sRequisitos += sRequisitos == "" ? "" + iRequisito : ","
							+ iRequisito;
				}

				String sReq = "SELECT ICVEREQUISITO,LREQUERIDO FROM TRAREQXMODTRAMITE "
						+ "where ICVETRAMITE="
						+ Integer.parseInt(aTramites[i], 10)
						+ " and icvemodalidad ="
						+ Integer.parseInt(aModalidades[i], 10)
						+ " and iCveRequisito in (" + sRequisitos + ")";
				Vector vRequisitos = this.findByNessted("", sReq, conn);

				for (int j = 0; j < vRequisitos.size(); j++) {
					
					TVDinRep vDRequisitos = (TVDinRep) vRequisitos.get(j);
					
					 Integer iCveReq = vDRequisitos.getInt("ICVEREQUISITO");
					
					//int iFisico=0;
					
//					for(int h=0;h<aFisico.length;h++){
//						String[] objFis = aFisico[h].split(":"); 
//						
//						if(Integer.parseInt(objFis[0])==iCveReq){
//							if(objFis[1].equals("true"))
//								iFisico = 1;
//							break;
//						}
//					}

					lPStmt2 = conn.prepareStatement(cSQLReq);
					lPStmt2.setInt(1, iEjercicio); // iEjercicio
					lPStmt2.setInt(2, iNumSolicitud); // iNumSolicitud
					lPStmt2.setInt(3, Integer.parseInt(aTramites[i], 10)); // iCveTramite
					lPStmt2.setInt(4, Integer.parseInt(aModalidades[i], 10)); // iCveModalidad
					lPStmt2.setInt(5,iCveReq); // iCveRequisito
					lPStmt2.setInt(6, 0); // iEjercicioFormato
					lPStmt2.setInt(7, 0); // iCveFormatoReg

					String aReq[] = vData.getString("iCveRequisito").split(",");
					boolean lReq = false;
					for (int iR = 0; iR < aReq.length; iR++) {
						String aReq2[] = aReq[iR].split("=");
						int iReq = Integer.parseInt(aReq2[0], 10);

						if (vDRequisitos.getInt("ICVEREQUISITO") == iReq) {
							lReq = aReq2[1].split("/")[0].equals("true");
						}
					}
					
					if (lReq == true) {
						lPStmt2.setDate(8, tFecha.TodaySQL()); // dtRecepcion
						lPStmt2.setInt(9, vData.getInt("iCveUsuario")); // iCveUsuRecibe
						//lPStmt2.setInt(14, iFisico); // lFisico
						lPStmt2.setInt(14, 0); // lFisico
					} else {
						lPStmt2.setNull(8, Types.DATE); // dtRecepcion
						lPStmt2.setInt(9, 0); // iCveUsuRecibe
						lPStmt2.setInt(14, 0); // lFisico
					}
					
					lPStmt2.setNull(10, Types.DATE); // dtNotificacion
					lPStmt2.setString(11, ""); // cNumOficio
					lPStmt2.setNull(12, Types.DATE); // dtOficio
					lPStmt2.setInt(13, 0); // lValido
					
					try {
						lPStmt2.executeUpdate();
						lPStmt2.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					String sqlMaxEv = "SELECT MAX(ICVEEVALXAREA) AS ICVEEVALXAREA FROM TRAREGEVAREQXAREA";
					
					Vector vMaxEv = this.findByCustom("", sqlMaxEv);
					
					int consEv = ((TVDinRep)vMaxEv.get(0)).getInt("ICVEEVALXAREA");
					
					if(!(consEv>0))
						consEv=1;
					else
						consEv+=1;
					
					String sqlInsEv = "INSERT INTO TRAREGEVAREQXAREA (ICVEEVALXAREA,IEJERCICIO,INUMSOLICITUD,ICVETRAMITE,"+
									  "ICVEMODALIDAD,ICVEREQUISITO,ICVEUSUARIO,LVALIDO,ICONSECUTIVOPNC,DTEVALUACION) "+
									  "VALUES (?,?,?,?,?,?,?,?,?,?)";
					
					lPStmt2 = conn.prepareStatement(sqlInsEv);
					
					lPStmt2.setInt(1,consEv);
					lPStmt2.setInt(2,iEjercicio);
					lPStmt2.setInt(3,iNumSolicitud);					
					lPStmt2.setInt(4,Integer.parseInt(aTramites[i], 10));
					lPStmt2.setInt(5,Integer.parseInt(aModalidades[i], 10));
					lPStmt2.setInt(6,vDRequisitos.getInt("ICVEREQUISITO"));
					lPStmt2.setInt(7,0);
					lPStmt2.setInt(8,0);
					lPStmt2.setNull(9, Types.INTEGER);
					lPStmt2.setNull(10, Types.DATE);
					
					try {
						lPStmt2.executeUpdate();
						lPStmt2.close();
						conn.commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (cnNested == null)
					conn.commit();
			}

			if (cnNested == null)
				conn.commit();

			vData.put("cNumSolicitudes", cNumSolicitudes);

			try {

				if (vData.getInt("iSolicitudPadre") > 0)
					this.insertSolicitudesRel2(vData, null);
			} catch (Exception ex) {
				cMensaje = ex.getMessage();
			}

		} catch (SQLException ex) {
			cMensaje = getSQLMessages(ex);
			ex.printStackTrace();
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
					e.printStackTrace();
				}
			}
			lSuccess = false;
		} catch (Exception ex) {
			cMensaje = ex.getMessage();
			ex.printStackTrace();
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
					e.printStackTrace();
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null)
					lPStmt.close();
				if (lPStmt2 != null)
					lPStmt2.close();
				if (cnNested == null) {
					if (conn != null)
						conn.close();
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
				ex2.printStackTrace();
			}
			if (!lSuccess)
				throw new DAOException(cMensaje);

			return vData;
		}
	}

	public TVDinRep fGenSolicitudRel(TVDinRep vData, Connection cnNested) {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		String cSol = "";
		PreparedStatement lPStmt = null;

		TVDinRep vDinRep = new TVDinRep();
		Vector vcRes = new Vector();
		TFechas fecha = new TFechas();
		java.sql.Date dtActual = fecha.getDateSQL(fecha.getThisTime());
		java.sql.Date dtLimEntregaDoctos = this.getFechaLimiteEntregaDocs(
				Integer.parseInt(vData.getString("ClavesTramite")),
				Integer.parseInt(vData.getString("ClavesModalidad")), dtActual);
		String cSQL = "SELECT ICVETRAMITEHIJO,ICVEMODALIDADHIJO FROM TRADependencia "
				+ "where ICVETRAMITE="
				+ vData.getString("ClavesTramite")
				+ " and ICVEMODALIDAD= " + vData.getString("ClavesModalidad");

		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			Vector vcTramitesRel = this.findByCustom("", cSQL);
			vData.put("lPrinsipal", 1);
			vData.put("dtLimEntregaDoctos", dtLimEntregaDoctos);
			vData.put("iSolicitudPadre", 0);
			TVDinRep vData1 = this.insertBatch2(vData, conn);

			// INSERTA EN LA TABLA TRAREGDATOSADVXSOL

			String cEtapa = "SELECT MAX(ICVEDATOSADV) AS ICVEDATOSADV FROM TRAREGDATOSADVXSOL";
			

			Vector vcData = this.findByCustom("", cEtapa);

			int consec;

			if (vcData.size() > 0) {
				TVDinRep vUltimo = (TVDinRep) vcData.get(0);
				consec = vUltimo.getInt("ICVEDATOSADV") + 1;
			} else {
				consec = 1;
			}

			String SqlADV = "INSERT INTO TRAREGDATOSADVXSOL (ICVEDATOSADV, IEJERCICIO, INUMSOLICITUD, CHECHOS,CORGANO, ICVECARRETERA, DTVISITA, CLATITUD, CLONGITUD,CKMSENTIDO) VALUES (?,?,?,?,?,?,?,?,?,?)";

			lPStmt = conn.prepareStatement(SqlADV);
		
			System.out.println(SqlADV);
			System.out.println(consec);
			System.out.println(vData1.getInt("iEjer"));
			System.out.println(vData1.getInt("iNumSolicitud"));
			System.out.println(vData.getString("cHechosTramite"));
			System.out.println(vData.getString("cOrganoAdmin"));
			System.out.println(vData.getInt("iCveCarretera"));
			System.out.println(vData.getDate("dtVisita"));
			System.out.println(vData.getString("tLatitud"));
			System.out.println(vData.getString("tLongitud"));
			System.out.println(vData.getString("tKmSentido"));

			lPStmt.setInt(1, consec);
			lPStmt.setInt(2, vData1.getInt("iEjer"));
			lPStmt.setInt(3, vData1.getInt("iNumSolicitud"));
			lPStmt.setString(4, vData.getString("cHechosTramite"));
			lPStmt.setString(5, vData.getString("cOrganoAdmin"));
			lPStmt.setInt(6, vData.getInt("iCveCarretera"));
			lPStmt.setDate(7, vData.getDate("dtVisita"));
			lPStmt.setString(8, vData.getString("tLatitud"));
			lPStmt.setString(9, vData.getString("tLongitud"));
			lPStmt.setString(10, vData.getString("tKmSentido"));
			
			lPStmt.executeUpdate();

			lPStmt = null;
			
			try {
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			int iSolPadre = vData1.getInt("iNumSolicitud");
			cSol = "\\n     " + iSolPadre; 
			cSol = ""+iSolPadre;
			for (int i = 0; i < vcTramitesRel.size(); i++) {
				vDinRep = (TVDinRep) vcTramitesRel.get(i);
				vData.remove("ClavesTramite");
				vData.remove("ClavesModalidad");
				vData.remove("lPrinsipal");
				vData.remove("iSolicitudPadre");
				vData.put("ClavesTramite", vDinRep.getString("ICVETRAMITEHIJO"));
				vData.put("ClavesModalidad",
						vDinRep.getString("ICVEMODALIDADHIJO"));
				vData.put("lPrinsipal", 0);
				vData.put("iSolicitudPadre", iSolPadre);
				TVDinRep vSol = this.insertBatch2(vData, conn);
				try {
					conn.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				cSol += "\\n     " + vSol.getString("cNumSolicitudes");
			}
			vData.remove("cNumSolicitudes");
			//vData.put("cNumSolicitudes", cSol); //original
			vData.put("cNumSolicitudes", vData1.getInt("iNumSolicitud"));
		} catch (Exception ex) {
			ex.printStackTrace();
			cMensaje = ex.getMessage();
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}

		}
		return vData;
	}

	public java.sql.Date getFechaCompromiso(int iEjercicio, int iNumSolicitud,
			Connection cnNested) throws Exception {
		java.sql.Date dtRetorno = null;
		TDGRLDiaNoHabil diaNoHabil = new TDGRLDiaNoHabil();
		Connection conn = cnNested;
		TFechas fecha = new TFechas();
		if (cnNested == null) {
			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(2);
		}

		String cPadre = "SELECT INUMSOLICITUDPRINC " + "FROM TRASOLICITUDREL "
				+ "where IEJERCICIO=" + iEjercicio + " and INUMSOLICITUDREL="
				+ iNumSolicitud;
		Vector vcPadre = this.findByCustom("", cPadre);
		String cSql = "";
		if (vcPadre.size() > 0) {
			TVDinRep vPadre = (TVDinRep) vcPadre.get(0);
			cSql = "SELECT "
					+ "DATE(S.DTIMPRESION) AS DTREG, "
					+ "TT.INUMDIASTRASLADO AS IDIASIDA,TT.LDIASNATURALESTRASLADO, "
					+ "T1.INUMDIASTRASLADO AS IDIASRETORNO,T1.LDIASNATURALESTRASLADO as LDIASNATURALESRET, "
					+ "CT.INUMDIASRESOL,CT.LDIASNATURALESRESOL,date(emt1.TSREGISTRO) as dtNotif,date(emt2.TSREGISTRO) as dtRecNotif,date(emt2.TSREGISTRO)-date(emt1.TSREGISTRO) as iDiasNot "
					+ "FROM TRAREGSOLICITUD S "
					+ "JOIN TRACONFIGURATRAMITE CT ON S.ICVETRAMITE=ct.ICVETRAMITE AND CT.ICVEMODALIDAD=S.ICVEMODALIDAD "
					+ "JOIN TRATRAMITEXOFIC OT ON OT.ICVEOFICINA=S.ICVEOFICINA AND OT.ICVETRAMITE=S.ICVETRAMITE "
					+ "LEFT JOIN TRATIEMPOTRASLADO TT ON TT.ICVETRAMITE=S.ICVETRAMITE AND TT.ICVEMODALIDAD=S.ICVEMODALIDAD "
					+ "AND TT.ICVEOFICINAORIGEN=S.ICVEOFICINA AND TT.ICVEOFICINADESTINO=OT.ICVEOFICINARESUELVE "
					+ "LEFT JOIN TRATIEMPOTRASLADO T1 ON T1.ICVETRAMITE=S.ICVETRAMITE AND T1.ICVEMODALIDAD=S.ICVEMODALIDAD "
					+ "AND T1.ICVEOFICINAORIGEN=OT.ICVEOFICINARESUELVE AND T1.ICVEOFICINADESTINO=S.ICVEOFICINAENVIARES "
					+ "LEFT JOIN TRAREGETAPASXMODTRAM EMT1 on EMT1.IEJERCICIO=S.IEJERCICIO AND EMT1.INUMSOLICITUD=S.INUMSOLICITUD AND EMT1.ICVEETAPA=20 "
					+ "LEFT JOIN TRAREGETAPASXMODTRAM EMT2 on EMT2.IEJERCICIO=S.IEJERCICIO AND EMT2.INUMSOLICITUD=S.INUMSOLICITUD AND EMT2.ICVEETAPA=23 "
					+
					// "left join traregsolicitud s1 on s1.iejercicio=s.iejercicio "
					// +
					"where S.IEJERCICIO =" + iEjercicio
					+ " AND S.INUMSOLICITUD= "
					+ vPadre.getInt("INUMSOLICITUDPRINC");
			/*
			 * " AND s1.inumsolicitud in ( " +
			 * "SELECT INUMSOLICITUDREL FROM TRASOLICITUDREL where INUMSOLICITUDPRINC= "
			 * +
			 * "(SELECT INUMSOLICITUDPRINC FROM TRASOLICITUDREL where iejercicio=s.iejercicio and INUMSOLICITUDREL=s.INUMSOLICITUD)"
			 * + ") ";
			 */
		} else {
			cSql = "SELECT "
					+ "DATE(S.DTIMPRESION) AS DTREG, "
					+ "TT.INUMDIASTRASLADO AS IDIASIDA,TT.LDIASNATURALESTRASLADO, "
					+ "T1.INUMDIASTRASLADO AS IDIASRETORNO,T1.LDIASNATURALESTRASLADO as LDIASNATURALESRET, "
					+ "CT.INUMDIASRESOL,CT.LDIASNATURALESRESOL,date(emt1.TSREGISTRO) as dtNotif,date(emt2.TSREGISTRO) as dtRecNotif,date(emt2.TSREGISTRO)-date(emt1.TSREGISTRO) as iDiasNot "
					+ "FROM TRAREGSOLICITUD S "
					+ "JOIN TRACONFIGURATRAMITE CT ON S.ICVETRAMITE=ct.ICVETRAMITE AND CT.ICVEMODALIDAD=S.ICVEMODALIDAD "
					+ "JOIN TRATRAMITEXOFIC OT ON OT.ICVEOFICINA=S.ICVEOFICINA AND OT.ICVETRAMITE=S.ICVETRAMITE "
					+ "LEFT JOIN TRATIEMPOTRASLADO TT ON TT.ICVETRAMITE=S.ICVETRAMITE AND TT.ICVEMODALIDAD=S.ICVEMODALIDAD "
					+ "AND TT.ICVEOFICINAORIGEN=S.ICVEOFICINA AND TT.ICVEOFICINADESTINO=OT.ICVEOFICINARESUELVE "
					+ "LEFT JOIN TRATIEMPOTRASLADO T1 ON T1.ICVETRAMITE=S.ICVETRAMITE AND T1.ICVEMODALIDAD=S.ICVEMODALIDAD "
					+ "AND T1.ICVEOFICINAORIGEN=OT.ICVEOFICINARESUELVE AND T1.ICVEOFICINADESTINO=S.ICVEOFICINAENVIARES "
					+ "LEFT JOIN TRAREGETAPASXMODTRAM EMT1 on EMT1.IEJERCICIO=S.IEJERCICIO AND EMT1.INUMSOLICITUD=S.INUMSOLICITUD AND EMT1.ICVEETAPA=20 "
					+ "LEFT JOIN TRAREGETAPASXMODTRAM EMT2 on EMT2.IEJERCICIO=S.IEJERCICIO AND EMT2.INUMSOLICITUD=S.INUMSOLICITUD AND EMT2.ICVEETAPA=23 "
					+ "where S.IEJERCICIO =" + iEjercicio
					+ " AND S.INUMSOLICITUD= " + iNumSolicitud;
		}

		String cNotif = "SELECT "
				+ "	S.IEJERCICIO, "
				+ "	S.INUMSOLICITUD, "
				+ "	RP.ICONSECUTIVOPNC, "
				+ "	RP.DTREGISTRO, "
				+ "	RP.DTNOTIFICACION, "
				+ "	COALESCE(RP.DTRESOLUCION,(current date)) as DTRESOLUCION "
				+ "FROM TRAREGSOLICITUD S "
				+ "JOIN TRAREGPNCETAPA P ON P.IEJERCICIO=S.IEJERCICIO AND P.INUMSOLICITUD=S.INUMSOLICITUD "
				+ "JOIN GRLREGISTROPNC RP ON RP.IEJERCICIO=P.IEJERCICIOPNC AND RP.ICONSECUTIVOPNC = P.ICONSECUTIVOPNC "
				+ "WHERE S.IEJERCICIO=" + iEjercicio + " AND S.INUMSOLICITUD="
				+ iNumSolicitud;

		Vector vcTrami = this.findByNessted("", cSql, conn);

		Vector vcNotif = this.findByNessted("", cNotif, conn);
		int iNotif = 0;
		for (int iN = 0; iN < vcNotif.size(); iN++) {
			TVDinRep vNotif = (TVDinRep) vcNotif.get(iN);
			iNotif += fecha.getDaysBetweenDates(vNotif.getDate("DTREGISTRO"),
					vNotif.getDate("DTRESOLUCION"));
		}

		if (vcTrami.size() > 0) {
			TVDinRep vTrami = (TVDinRep) vcTrami.get(0);
			int diasReso = 0;
			int diasIda = 0;
			int diasRetorno = 0;
			int diasNot = 0;
			dtRetorno = vTrami.getDate("DTREG");
			for (int i = 0; i < vcTrami.size(); i++) {
				TVDinRep vTramiTemp = (TVDinRep) vcTrami.get(i);

				diasReso = diasReso > vTramiTemp.getInt("INUMDIASRESOL") ? diasReso
						: vTramiTemp.getInt("INUMDIASRESOL");
				diasIda = diasIda > vTramiTemp.getInt("IDIASIDA") ? diasIda
						: vTramiTemp.getInt("IDIASIDA");
				diasRetorno = diasRetorno > vTramiTemp.getInt("IDIASRETORNO") ? diasRetorno
						: vTramiTemp.getInt("IDIASRETORNO");
				diasNot = diasNot > vTramiTemp.getInt("dtNotif") ? diasNot
						: vTramiTemp.getInt("dtNotif");
			}
			if (diasReso > 0) {
				dtRetorno = diaNoHabil.getFechaFinal(dtRetorno, diasReso,
						(vTrami.getInt("LDIASNATURALESRESOL") == 1 ? true
								: false));
			}
			if (diasIda > 0) {
				dtRetorno = diaNoHabil.getFechaFinal(dtRetorno, diasIda,
						(vTrami.getInt("LDIASNATURALESTRASLADO") == 1 ? true
								: false));
			}
			if (diasRetorno > 0) {
				dtRetorno = diaNoHabil
						.getFechaFinal(dtRetorno, diasRetorno, (vTrami
								.getInt("LDIASNATURALESRET") == 1 ? true
								: false));
			}
			if (diasNot > 0) {
				dtRetorno = diaNoHabil.getFechaFinal(dtRetorno, diasNot, true);
			}

			if (!dtRetorno.equals(null)) {
				PreparedStatement lPStmt = null;

				String cSql1 = "update traregsolicitud set DTESTIMADAENTREGA=? where iejercicio=? and INUMSOLICITUD=? ";
				lPStmt = conn.prepareStatement(cSql1);
				lPStmt.setDate(1, dtRetorno);
				lPStmt.setInt(2, iEjercicio);
				lPStmt.setInt(3, iNumSolicitud);

				lPStmt.executeUpdate();
				if (cnNested == null)
					conn.commit();
			}
		}
		return dtRetorno;
	}

	public void updateFechaCompromiso(int iEjercicio, int iNumSolicitud,
			Connection cnNested) throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		TDGRLBitacora bitacora = new TDGRLBitacora();
		int i;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update TRARegSolicitud set DTESTIMADAENTREGA=? where iEjercicio = ? AND iNumSolicitud = ? ";
			lPStmt = conn.prepareStatement(lSQL);

			lPStmt.setDate(1,
					this.getFechaCompromiso(iEjercicio, iNumSolicitud, conn));
			lPStmt.setInt(2, iEjercicio);
			lPStmt.setInt(3, iNumSolicitud);

			lPStmt.executeUpdate();

			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
		}
	}

	public void fechaImpresion(int iEjercicio, int iNumSolicitud,
			Connection cnNested) throws DAOException {

		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		TFechas fecha = new TFechas();
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(1);
			}
			String lSQL = "UPDATE TRAREGSOLICITUD SET DTIMPRESION=?,LIMPRESO=1 WHERE IEJERCICIO=? AND INUMSOLICITUD=?";

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setDate(1, fecha.getDateSQL(fecha.getThisTime()));
			lPStmt.setInt(2, iEjercicio);
			lPStmt.setInt(3, iNumSolicitud);
			lPStmt.executeUpdate();
			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
		}

	}

	public void updateAbandono(TVDinRep vData, Connection cnNested)
			throws DAOException {

		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		TFechas fecha = new TFechas();
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(1);
			}
			String lSQL = "UPDATE TRAREGSOLICITUD SET LABANDONADA=1 WHERE IEJERCICIO=? AND INUMSOLICITUD=?";

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1, vData.getInt("hdEjer"));
			lPStmt.setInt(2, vData.getInt("hdNumSol"));

			lPStmt.executeUpdate();
			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("update", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("update.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("update.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
		}

	}

	public int obtenSolicitanteByRFC(String rfcSol, Connection cnNested) {

		DbConnection dbConn = null;
		Connection conn = cnNested;
		String cSol = "";
		PreparedStatement lPStmt = null;
		int cve = -1;
		try {

			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			String cSQL = "SELECT ICVEPERSONA FROM GRLPERSONA WHERE CRFC='" + rfcSol + "'";

			Vector vcResult = this.findByCustom("", cSQL);

			if(vcResult.size()>0){
				TVDinRep vDatos = (TVDinRep) vcResult.get(0);
				cve=vDatos.getInt("ICVEPERSONA");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			cMensaje = ex.getMessage();
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}

		}
		return cve;
	}
	
	
	public void updateFRecep(int iEjercicio, int iNumSolicitud, Connection cnNested){
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		try {

			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			String cSQL = "UPDATE TRAREGREQXTRAM SET DTRECEPCION = CURRENT DATE WHERE INUMSOLICITUD="+iNumSolicitud+" AND IEJERCICIO="+iEjercicio+" AND DTRECEPCION IS NULL";

			lPStmt = conn.prepareStatement(cSQL);
			lPStmt.executeUpdate();
			
			if(cnNested==null)
				conn.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			cMensaje = ex.getMessage();
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}

		}
	}
	
	public boolean deleteIrrelgular(String idIrr,Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		String cMsg = "";
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			String lSQL = "delete from TraIrregular where icveirregular="+idIrr;

			lPStmt = conn.prepareStatement(lSQL);

			lPStmt.executeUpdate();
			if (cnNested == null) {
				conn.commit();
			}
		} catch (SQLException sqle) {
			lSuccess = false;
			cMsg = "" + sqle.getErrorCode();
		} catch (Exception ex) {
			warn("delete", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("delete.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("delete.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException(cMsg);
			return lSuccess;
		}
	}
	
	public TVDinRep insertIrr(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			
			Vector dtoVec = new Vector();
			TVDinRep dtoConsec= new TVDinRep();
			
			dtoVec= this.findByCustom("icveirregular", "select max(icveirregular)+1 as consec from trairregular");
			
			if(!dtoVec.isEmpty())
				dtoConsec=(TVDinRep)dtoVec.get(0);
			
			int consec;
			
			consec=dtoConsec.getInt("consec");
			
			if(!(consec>1)){
				consec=1;
			}

			String lSQL="INSERT INTO TRAIRREGULAR (ICVEIRREGULAR,CAUTOPISTA,CTIPO,CCADENAMIENTO,CCOMENTARIO,CICVEUSUARIO,CLATITUD,CLONGITUD) VALUES (?,?,?,?,?,?,?,?)";
			
			lPStmt = conn.prepareStatement(lSQL);
			
			lPStmt.setInt(1, consec);
			lPStmt.setString(2, vData.getString("cAutopista"));
			lPStmt.setString(3, vData.getString("cTipo"));
			lPStmt.setString(4, vData.getString("cCadenamiento"));
			lPStmt.setString(5, vData.getString("cComentario"));
			lPStmt.setInt(6, vData.getInt("iCveUsuario"));
			lPStmt.setString(7, vData.getString("cLatitud"));
			lPStmt.setString(8, vData.getString("cLongitud"));
			
			lPStmt.executeUpdate();			
			
			if (cnNested == null) {
				conn.commit();
			}
		} catch (Exception ex) {
			warn("insert", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
			return vData;
		}
	}
	
	
	public TVDinRep insertDatosPermiso(TVDinRep vData, Connection cnNested)
			throws DAOException {
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try {
			if (cnNested == null) {
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
				
				String lSQL="SELECT INUMSOLICITUD FROM TRADATOSPERM where IEJERCICIO ="+vData.getInt("IEJERCICIO")+" and INUMSOLICITUD ="+vData.getInt("INUMSOLICITUD");
				String lSQLA="";
				String lSQLB="";
				
			if(findByCustom("INUMSOLICITUD", lSQL).size()>0){
				lSQLA="UPDATE TRAFOLENVIOS SET CFOLCONCE=?, CFOLSCT=?,CFOLPERM=?,CFOLSCTPERM=?,CNOMDGSCT=?,CNOMDGSCTPERM=? WHERE IEJERCICIO=? AND INUMSOLICITUD=?";
				lSQLB = "UPDATE TRADATOSPERM SET CFOLVOLANTE=?, CFOLPERMISO=?, CDGDC=?, CNUMERAL=?,CPARRAF_1=?,CPARRAF_2=?,CPARRAF_3=?,CPARRAF_4=?,CPARRAF_5=?,CPARRAF_6=?,CARTICULOS=?,CREVDGDC=?,CPLAZO=?,CCALMAT=? WHERE IEJERCICIO=? AND INUMSOLICITUD=?";
			}else {

				lSQLA="INSERT INTO TRAFOLENVIOS (CFOLCONCE, CFOLSCT,CFOLPERM,CFOLSCTPERM,CNOMDGSCT,CNOMDGSCTPERM,IEJERCICIO,INUMSOLICITUD) VALUES (?,?,?,?,?,?,?,?)";	
				lSQLB = "INSERT INTO TRADATOSPERM (CFOLVOLANTE , CFOLPERMISO , CDGDC , CNUMERAL ,CPARRAF_1 ,CPARRAF_2 ,CPARRAF_3 ,CPARRAF_4 ,CPARRAF_5 ,CPARRAF_6 ,CARTICULOS ,CREVDGDC ,CPLAZO ,CCALMAT,IEJERCICIO ,INUMSOLICITUD, DTPERMISO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_DATE)";
			}
				
				lPStmt = conn.prepareStatement(lSQLA);
	
				
				lPStmt.setString(1, vData.getString("CFOLCONCE"));
				lPStmt.setString(2, vData.getString("CFOLSCT"));
				lPStmt.setString(3, vData.getString("CFOLPERM"));
				lPStmt.setString(4, vData.getString("CFOLSCTPERM"));
				lPStmt.setString(5, vData.getString("CNOMDGSCT"));
				lPStmt.setString(6, vData.getString("CNOMDGSCTPERM"));
				lPStmt.setInt(7, vData.getInt("IEJERCICIO"));
				lPStmt.setInt(8, vData.getInt("INUMSOLICITUD"));
				
				lPStmt.executeUpdate();			
				
				lPStmt = conn.prepareStatement(lSQLB);
							
				lPStmt.setString(1, vData.getString("CVOLANTE"));
				lPStmt.setString(2, vData.getString("CFOLPERMISO"));
				lPStmt.setString(3, vData.getString("CDGDC"));
				lPStmt.setString(4, vData.getString("CNUMERAL"));
				lPStmt.setString(5, vData.getString("CPARRAF1"));
				lPStmt.setString(6, vData.getString("CPARRAF2"));
				lPStmt.setString(7, vData.getString("CPARRAF3"));
				lPStmt.setString(8, vData.getString("CPARRAF4"));
				lPStmt.setString(9, vData.getString("CPARRAF5"));
				lPStmt.setString(10, vData.getString("CPARRAF6"));
				lPStmt.setString(11, vData.getString("CARTICULOS"));
				lPStmt.setString(12, vData.getString("CREVDGDC"));
				lPStmt.setString(13, vData.getString("CPLAZO"));
				lPStmt.setString(14, vData.getString("CCALMAT"));
				lPStmt.setInt(15, vData.getInt("IEJERCICIO"));
				lPStmt.setInt(16, vData.getInt("INUMSOLICITUD"));
	
				lPStmt.executeUpdate();			
				
				if (cnNested == null) {
					conn.commit();
				}
		} catch (Exception ex) {
			warn("insert", ex);
			if (cnNested == null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;
		} finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (cnNested == null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			if (lSuccess == false)
				throw new DAOException("");
			return vData;
		}
	}

}



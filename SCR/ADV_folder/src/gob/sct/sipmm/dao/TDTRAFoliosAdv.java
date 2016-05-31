package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;
import gob.sct.sipmm.dao.ws.TDCis;

/**
 * <p>
 * Title: TDTRARegEtapasXModTram.java
 * </p>
 * <p>
 * Description: DAO de la entidad TRARegEtapasXModTram
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Tecnología InRed S.A. de C.V.
 * </p>
 * 
 * @author ocastrejon
 * @version 1.0
 */
public class TDTRAFoliosAdv extends DAOBaseSerializable {
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros
			.getPropEspecifica("ConDBModulo");
	private String errorExc = "";

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
	 *             - Excepción de tipo DAO
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
			e.printStackTrace();
			cError = e.toString();
		} finally {
			if (!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	public void insertFolioxSol(String cCampo, int iEjercicio, int iNumSolicitud)
			throws Exception {
		DbConnection dbConn = null;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		String cMsgError = "";
		int consec=0;
		
		try {

			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(2); //serializable;
			
			
			Vector vcMaxFolio = new Vector();
			
			String sqlMaxFolio= "";

			String cvePerm="";
			String cColConsec="";
			
			if(cCampo.equals("INUMPERMISO")){
				TVDinRep datosTramPer = this.datosTramitePermiso(iEjercicio, iNumSolicitud);
				
				if(datosTramPer!=null){
					cvePerm=datosTramPer.getString("CCVEPERMISO");
					cColConsec=datosTramPer.getString("CCOLCONSEC");
					sqlMaxFolio="SELECT MAX("+cCampo+cColConsec+") as consec FROM TRAFOLIOSADV WHERE IEJERCICIO="+iEjercicio;
				}
			}else{
				sqlMaxFolio="SELECT MAX("+cCampo+") as consec FROM TRAFOLIOSADV WHERE IEJERCICIO="+iEjercicio;
			}
			
			vcMaxFolio = this.findByCustom("",sqlMaxFolio);

			if (vcMaxFolio.size() > 0) {
				TVDinRep vMax = (TVDinRep) vcMaxFolio.get(0);
				consec = vMax.getInt("consec")+1;
			} else {
				consec=1;
			}
			
			String sqluUpdDFol ="";

			if(cCampo.equals("INUMPERMISO"))
					sqluUpdDFol="update TRAFOLIOSADV set "+cCampo+cColConsec+" = "+consec+" where iejercicio="+iEjercicio;
			else
				sqluUpdDFol="update TRAFOLIOSADV set "+cCampo+" = "+consec+" where iejercicio="+iEjercicio;
			
			lPStmt = conn.prepareStatement(sqluUpdDFol);
			
			lPStmt.executeUpdate();
			
			String sqlUpdFolxSol="";
			
			if(cCampo.equals("INUMPERMISO")){
				//sqlUpdFolxSol = "update TRAFOLIOSADVXSOL set "+cCampo+" = '"+cvePerm+consec+"' , dtpermiso=current_date where iejercicio="+iEjercicio+" and inumsolicitud="+iNumSolicitud;
				sqlUpdFolxSol = "update TRAFOLIOSADVXSOL set CNUMPERMISO = '"+cvePerm+consec+"' , dtpermiso=current_date where iejercicio="+iEjercicio+" and inumsolicitud="+iNumSolicitud;
			}else{
				sqlUpdFolxSol = "update TRAFOLIOSADVXSOL set "+cCampo+" = "+consec+" where iejercicio="+iEjercicio+" and inumsolicitud="+iNumSolicitud;
			}
			
			lPStmt=null;
			
			lPStmt=conn.prepareStatement(sqlUpdFolxSol);
			lPStmt.executeUpdate();

			
			conn.commit();

		} catch (DAOException ex) {
			errorExc = "" + ex.getMessage() + "";
			warn(ex.getMessage(), ex);
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;

		} catch (Exception ex) {
			errorExc = "" + ex.getMessage() + "";
			warn(ex.getMessage(), ex);
			if (conn != null) {
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
				if (conn != null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			// throw new DAOException(errorExc+"s");
			if (lSuccess == false) {
				throw new Exception(errorExc);
			}
		}
	}
		
	public TVDinRep datosTramitePermiso(Integer iEjercicio, Integer iNumSolicitud){
		Vector vcDatosPerm = new Vector();
		TVDinRep vdDatosPerm= null;
		
		String sqlCvePermiso = "SELECT CCVEPERMISO,CCOLCONSEC FROM TRAREGSOLICITUD SOL"
								+" JOIN TRACATTRAMITE TRA ON SOL.ICVETRAMITE = TRA.ICVETRAMITE"
								+" WHERE SOL.INUMSOLICITUD="+iNumSolicitud+" AND SOL.IEJERCICIO="+iEjercicio;
		
		try{
			vcDatosPerm= this.findByCustom("", sqlCvePermiso);
			if(vcDatosPerm.size()>0)
				vdDatosPerm = (TVDinRep) vcDatosPerm.get(0);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return vdDatosPerm;
		}
		
	}
	
	public TVDinRep insertPersona(int modo,TVDinRep vData)
			throws Exception {
		DbConnection dbConn = null;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		String cMsgError = "";
		int consec=0;
		TFechas fecha = new TFechas();
		int iEjercicio = fecha.getIntYear(fecha.getDateSQL(fecha.getThisTime(false)));
		
		try {

			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(2); //serializable;

			String cSQL = "INSERT INTO GRLPERSONA (ICVEPERSONA,CRFC,CRPA,ITIPOPERSONA,CNOMRAZONSOCIAL,"+
						  "CAPPATERNO,CAPMATERNO,CCORREOE,CPSEUDONIMOEMP,CCURP) VALUES (?,?,'',?,?,?,?,?,'','')";

			String cSQLDom ="INSERT INTO GRLDOMICILIO (ICVEPERSONA,ICVEDOMICILIO,ICVETIPODOMICILIO,CCALLE,"+
						    "CNUMEXTERIOR,CNUMINTERIOR,CCOLONIA,CCODPOSTAL,CTELEFONO,ICVEPAIS,ICVEENTIDADFED,ICVEMUNICIPIO,ICVELOCALIDAD,LPREDETERMINADO) VALUES "+ 
						    "(?,1,1,?,?,?,?,?,?,1,?,?,?,1)";

			String cMax = "SELECT MAX(ICVEPERSONA)+1 as ICVEPERSONA FROM GRLPERSONA";
			
			Vector vcMax = this.findByCustom("", cMax);
			
			if(vcMax.size()>0){
				TVDinRep vMax= (TVDinRep) vcMax.get(0);
				consec = vMax.getInt("ICVEPERSONA");
			}
						
			if(modo==1 && consec>0){ //replegal
				
				lPStmt = conn.prepareStatement(cSQL);
			
				lPStmt.setInt(1, consec);
				lPStmt.setString(2,vData.getString("hdcRFC_RepL"));
				lPStmt.setInt(3, modo);
				lPStmt.setString(4, vData.getString("hdcNomRaz_RepL"));
				lPStmt.setString(5, vData.getString("hdcApPat_RepL"));
				lPStmt.setString(6, vData.getString("hdcApMat_RepL"));
				lPStmt.setString(7, vData.getString("hdcMail_RepL"));
				
				lPStmt.executeUpdate();

				lPStmt = null;
				
				lPStmt = conn.prepareStatement(cSQLDom);
				
				lPStmt.setInt(1, consec);
				lPStmt.setString(2, vData.getString("hdcCalle_RepL"));
				lPStmt.setString(3, vData.getString("hdcNumExt_RepL"));
				lPStmt.setString(4, vData.getString("hdcNumInt_RepL"));
				lPStmt.setString(5, vData.getString("hdcCol_RepL"));
				lPStmt.setString(6, vData.getString("hdcCP_RepL"));
				lPStmt.setString(7, vData.getString("hdcTel_RepL"));
				lPStmt.setInt(8, vData.getInt("iCveEntidadFedA"));
				lPStmt.setInt(9, vData.getInt("iCveMunicipioA"));
				lPStmt.setNull(10, Types.INTEGER);
				
				lPStmt.executeUpdate();
				
			}else if(modo==2 && consec>0){//empresa
				lPStmt = conn.prepareStatement(cSQL);
				
				lPStmt.setInt(1, consec);
				lPStmt.setString(2,vData.getString("hdcRFC_Sol"));
				lPStmt.setInt(3, modo);
				lPStmt.setString(4, vData.getString("hdcNomRaz_Sol"));
				lPStmt.setString(5, vData.getString("hdcApPat_Sol"));
				lPStmt.setString(6, vData.getString("hdcApMat_Sol"));
				lPStmt.setString(7, vData.getString("hdcMail_Sol"));
				
				lPStmt.executeUpdate();

				lPStmt = null;
				
				lPStmt = conn.prepareStatement(cSQLDom);
				
				lPStmt.setInt(1, consec);
				lPStmt.setString(2, vData.getString("hdcCalle_Sol"));
				lPStmt.setString(3, vData.getString("hdcNumExt_Sol"));
				lPStmt.setString(4, vData.getString("hdcNumInt_Sol"));
				lPStmt.setString(5, vData.getString("hdcCol_Sol"));
				lPStmt.setString(6, vData.getString("hdcCP_Sol"));
				lPStmt.setString(7, vData.getString("hdcTel_Sol"));
				lPStmt.setInt(8, vData.getInt("iCveEntidadFedB"));
				lPStmt.setInt(9, vData.getInt("iCveMunicipioB"));
				lPStmt.setNull(10, Types.INTEGER);
				
				lPStmt.executeUpdate();
								
			}
			
			vData.put("ICVEPERSONA", consec);
			
			conn.commit();

		} catch (DAOException ex) {
			errorExc = "" + ex.getMessage() + "";
			warn(ex.getMessage(), ex);
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;

		} catch (Exception ex) {
			errorExc = "" + ex.getMessage() + "";
			warn(ex.getMessage(), ex);
			if (conn != null) {
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
				if (conn != null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			// throw new DAOException(errorExc+"s");
			if (lSuccess == false) {
				throw new Exception(errorExc);
			}
		}
		return vData;
	}
	
	public void executeQuery(String query)
			throws Exception {
		DbConnection dbConn = null;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		String cMsgError = "";

		try {

			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(2); //serializable;

			if(!query.trim().equals("")){
		    	lPStmt = conn.prepareStatement(query);
				lPStmt.executeUpdate();
			}
			
			conn.commit();

		} catch (DAOException ex) {
			errorExc = "" + ex.getMessage() + "";
			warn(ex.getMessage(), ex);
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e) {
					fatal("insert.rollback", e);
				}
			}
			lSuccess = false;

		} catch (Exception ex) {
			errorExc = "" + ex.getMessage() + "";
			warn(ex.getMessage(), ex);
			if (conn != null) {
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
				if (conn != null) {
					if (conn != null) {
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch (Exception ex2) {
				warn("insert.close", ex2);
			}
			// throw new DAOException(errorExc+"s");
			if (lSuccess == false) {
				throw new Exception(errorExc);
			}
		}
	}
}

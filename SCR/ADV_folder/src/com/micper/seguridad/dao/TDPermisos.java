package com.micper.seguridad.dao;

//Java imports
import java.sql.*;
import java.util.*;

import com.micper.ingsw.*;
import com.micper.seguridad.PasswordHash;
import com.micper.seguridad.dao.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

public class TDPermisos extends DAOBase {
	private String dataSrcName = "";
	private Vector vcMenuUsuario = new Vector();
	private HashMap hmPUsuario;

	private TParametro vParametros;

	public static final int PBKDF2_ITERATIONS = 1000;
	public static final int SALT_INDEX = 1;
	public static final int PBKDF2_INDEX = 2;
	private StringBuilder builder;
	private TVDinRep usrData;
	private boolean lChgPwd=false;

	public TDPermisos() {
	}

	public void menuUsuario(String cNumModulo, int iCveUsuario) {
		vParametros = new TParametro(cNumModulo);
		Vector vcMenu = TDMenu.getVSystemMenu(cNumModulo);
		hmPUsuario = this.permisosUsuario(iCveUsuario,
				Integer.parseInt(cNumModulo, 10));
		HashMap hmAuxPadres = new HashMap();

		TVMenu vMenu;

		for (int i = 0; i < vcMenu.size(); i++) {
			vMenu = (TVMenu) vcMenu.get(i);
			if (hmPUsuario.containsKey(vMenu.getCNomPagina())) {
				if (vMenu.getIOpcPadre() == 0
						|| hmAuxPadres.containsKey("" + vMenu.getIOpcPadre())) {
					StringTokenizer stActualizacion = new StringTokenizer(
							(String) hmPUsuario.get(vMenu.getCNomPagina()), "|");
					if (stActualizacion.countTokens() == 2) {
						stActualizacion.nextElement();
						vMenu.setLActualizacion(Integer.parseInt(""
								+ stActualizacion.nextElement()));
					} else {
						vMenu.setLActualizacion(0);
					}
					vcMenuUsuario.add(vMenu);
					hmAuxPadres.put("" + vMenu.getIOrden(),
							"" + vMenu.getIOrden());
				}
			}
		}
	}

	private HashMap permisosUsuario(int iCveUsuario, int iCveSistema) {
		DbConnection dbConn = null;
		Connection conn = null;
		PreparedStatement lPStmt = null;
		ResultSet lRSet = null;
		HashMap vPermisos = new HashMap();

		try {
			dataSrcName = vParametros.getPropEspecifica("ConDB");
			dbConn = new DbConnection(dataSrcName);
			conn = dbConn.getConnection();
			conn.setTransactionIsolation(2);

			String lSQL = "select segprograma.cNombre, segprograma.cdscprograma, segpermisoxgpo.lactualizacion "
					+ "from segusuario  "
					+ "join seggpoxusr on segusuario.icveusuario = seggpoxusr.icveusuario "
					+ "and  seggpoxusr.icvesistema = ? "
					+ "join seggrupo on seggpoxusr.icvesistema = seggrupo.icvesistema "
					+ "and  seggpoxusr.icvegrupo = seggrupo.icvegrupo  "
					+ "and  seggrupo.lbloqueado = 0 "
					+ "join segpermisoxgpo on seggpoxusr.icvesistema = segpermisoxgpo.icvesistema "
					+ "and  seggpoxusr.icvegrupo = segpermisoxgpo.icvegrupo "
					+ "and  segpermisoxgpo.lejecucion = 1 "
					+ "join segprograma on segpermisoxgpo.icvesistema = segprograma.icvesistema "
					+ "and  segpermisoxgpo.icveprograma = segprograma.icveprograma  "
					+ "and  segprograma.lbloqueado = 0 "
					+ "where segusuario.icveusuario = ? "
					+ "and   segusuario.lbloqueado = 0 "
					+ "order by segprograma.icveprograma, segpermisoxgpo.lactualizacion ";

			lPStmt = conn.prepareStatement(lSQL);

			lPStmt.setInt(1, iCveSistema);
			lPStmt.setInt(2, iCveUsuario);
			lRSet = lPStmt.executeQuery();

			while (lRSet.next()) {
				vPermisos.put(lRSet.getString(1), lRSet.getString(2) + "|"
						+ lRSet.getInt(3));
			}
		} catch (Exception ex) {
			//System.out.print("permisosUsuario");
			ex.printStackTrace();
		} finally {
			try {
				if (lRSet != null) {
					lRSet.close();
				}
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				dbConn.closeConnection();
			} catch (Exception ex2) {
				//System.out.print("permisosUsuario.Close");
			}
			return vPermisos;
		}
	}

	public HashMap getHmPUsuario() {
		return hmPUsuario;
	}

	public Vector getVcMenuUsuario() {
		return vcMenuUsuario;
	}

	public TVUsuario accesoUsuario(String cUsuario, String cContrasenia,
			String cNumModulo) {

		vParametros = new TParametro(cNumModulo);
		DbConnection dbConn = null;
		Connection conn = null;
		PreparedStatement lPStmt = null;
		ResultSet lRSet = null;
		TVUsuario vUsuario = null;
		try {

			if (checkUserPass(cUsuario, cContrasenia)) {
				dataSrcName = vParametros.getPropEspecifica("ConDB");
				dbConn = new DbConnection(dataSrcName);
				conn = dbConn.getConnection();
				conn.setTransactionIsolation(2);

				String lSQL = "select segusuario.*, grlpais.cnombre as cdscpais, grlentidadfed.cnombre as cdscentidadfed, grlmunicipio.cnombre as cdscmunicipio, seggpoxusr.icvegrupo as icvegrupo "
						+ "from segusuario "
						+ "join grlpais on segusuario.icvepais = grlpais.icvepais "
						+ "join grlentidadfed on segusuario.icvepais = grlentidadfed.icvepais "
						+ "and segusuario.icveentidadfed = grlentidadfed.icveentidadfed "
						+ "join grlmunicipio on segusuario.icvepais = grlmunicipio.icvepais "
						+ "and segusuario.icveentidadfed = grlmunicipio.icveentidadfed "
						+ "and segusuario.icvemunicipio = grlmunicipio.icvemunicipio "
						+ "join seggpoxusr on segusuario.icveusuario = seggpoxusr.icveusuario "
						+ "where cUsuario = ? "
						// + "and   cPassword = ? "
						+ "and   lBloqueado = 0";

				lPStmt = conn.prepareStatement(lSQL);

				lPStmt.setString(1, cUsuario);
				// lPStmt.setString(2, cContrasenia);
				lRSet = lPStmt.executeQuery();

				while (lRSet.next()) {
					vUsuario = new TVUsuario();
					vUsuario.setICveusuario(lRSet.getInt("iCveUsuario"));
					vUsuario.setDtRegistro(lRSet.getDate("dtRegistro"));
					vUsuario.setCUsuario(lRSet.getString("cUsuario"));
					vUsuario.setCPassword(lRSet.getString("cPassword"));
					vUsuario.setCNombre(lRSet.getString("cNombre"));
					vUsuario.setCApPaterno(lRSet.getString("cApPaterno"));
					vUsuario.setCApMaterno(lRSet.getString("cApMaterno"));
					vUsuario.setCCalle(lRSet.getString("cCalle"));
					vUsuario.setCColonia(lRSet.getString("cColonia"));
					vUsuario.setICvePais(lRSet.getInt("iCvePais"));
					vUsuario.setICveEntidadFed(lRSet.getInt("iCveEntidadFed"));
					vUsuario.setICveMunicipio(lRSet.getInt("iCveMunicipio"));
					vUsuario.setICodigoPostal(lRSet.getInt("iCodigoPostal"));
					vUsuario.setCTelefono(lRSet.getString("cTelefono"));
					vUsuario.setICveUnidadOrg(lRSet.getInt("iCveUnidadOrg"));
					vUsuario.setLBloqueado(lRSet.getInt("lBloqueado"));
					vUsuario.setCDscPais(lRSet.getString("cdscpais"));
					vUsuario.setCDscEntidadFed(lRSet
							.getString("cdscentidadfed"));
					vUsuario.setCDscMunicipio(lRSet.getString("cdscmunicipio"));
					vUsuario.setID("" + new java.util.Date().getTime());
					vUsuario.setiCveGrupo(lRSet.getInt("icvegrupo"));

				}

			}
		} catch (Exception ex) {
			//System.out.print("accesoUsuario");
			ex.printStackTrace();
		} finally {
			try {
				if (lRSet != null) {
					lRSet.close();
				}
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				dbConn.closeConnection();
			} catch (Exception ex2) {
				//System.out.print("accesoUsuario.Close");
			}
			return vUsuario;
		}
	}

	public boolean cambioContrasenia(String cUsuario, String cNvaContrasenia,
			String cNumModulo) {
		vParametros = new TParametro(cNumModulo);
		DbConnection dbConn = null;
		Connection conn = null;
		PreparedStatement lPStmt = null;
		ResultSet lRSet = null;
		boolean lSuccess = true;

		try {
			dataSrcName = vParametros.getPropEspecifica("ConDB");
			dbConn = new DbConnection(dataSrcName);
			conn = dbConn.getConnection();
			conn.setTransactionIsolation(2);

			String lSQL = "update segusuario " + "set cPassword = '"
					+ cNvaContrasenia + "'" + "where cUsuario = ? ";

			lPStmt = conn.prepareStatement(lSQL);

			lPStmt.setString(1, cUsuario);
			lPStmt.executeUpdate();
		} catch (Exception ex) {
			lSuccess = false;
			//System.out.print("cambioContrasenia");
			ex.printStackTrace();
		} finally {
			try {
				if (lRSet != null) {
					lRSet.close();
				}
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				dbConn.closeConnection();
			} catch (Exception ex2) {
				//System.out.print("accesoUsuario.Close");
			}
			return lSuccess;
		}
	}

	// public boolean checkUserPass(String cUsuario, String cPassword) {
	// Vector data = null;
	// try {
	// String lSQL = "select CPWDSLT,CPWDHSH from segusuario where cUsuario = '"
	// + cUsuario + "'";
	//
	// data = FindByGeneric("", lSQL,
	// vParametros.getPropEspecifica("ConDB"));
	// if (data.size() > 0) {
	// builder = new StringBuilder();
	// usrData = (TVDinRep) data.get(0);
	// builder.append(PBKDF2_ITERATIONS).append(":")
	// .append(usrData.get("CPWDSLT")).append(":")
	// .append(usrData.get("CPWDHSH"));
	// return PasswordHash.validatePassword(cPassword,
	// builder.toString());
	// } else {
	// return false;
	// }
	//
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// throw new RuntimeException(ex.getMessage());
	// }
	// }
	
	
	///mi funk 
	/*
	public boolean checkUserPass(String cUsuario, String cPassword) {
		Vector data = null;
		Vector dt = null;

		PreparedStatement lPStmt = null;
		
		boolean result = false;
		
		try {
			
			dataSrcName = vParametros.getPropEspecifica("ConDB");
			dbConn = new DbConnection(dataSrcName);
			conn = dbConn.getConnection();
			conn.setTransactionIsolation(2);

//			String[] arrStr = {}; //para obtener el salt y el hash e insertarlos antes de comprobar
//			
//			arrStr = PasswordHash.createHash(cPassword).split(":");
//			
//			String lSQL = "update segusuario " + "set CPWDSLT = '"
//					+ arrStr[1] + "', CPWDHSH='"+ arrStr[2] +"' where CPASSWORD = ? ";
//
//			lPStmt = conn.prepareStatement(lSQL);
//			lPStmt.setString(1, cPassword);
//			lPStmt.executeUpdate();
//
//			if(conn!=null)
//				conn.commit();
//			
			String lSQL = "select CPWDSLT,CPWDHSH, case when DTCAMBIOCONTRA < current date - "
					+ vParametros.getPropEspecifica("EXPDAYS")
					+ " days then 1 when DTCAMBIOCONTRA is null then 1 else 0 end case from segusuario where cUsuario = '"
					+ cUsuario + "'";

			data = FindByGeneric("", lSQL,
					vParametros.getPropEspecifica("ConDB"));
			if (data.size() > 0) {
				builder = new StringBuilder();
				usrData = (TVDinRep) data.get(0);
				if (usrData.getString("CASE").equals("1")) {
					lChgPwd = true;
				}
				builder.append(PBKDF2_ITERATIONS).append(":")
						.append(usrData.get("CPWDSLT")).append(":")
						.append(usrData.get("CPWDHSH"));
				
				result = PasswordHash.validatePassword(cPassword,
						builder.toString());
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}finally {
			try {
				
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				dbConn.closeConnection();
			} catch (Exception ex2) {
				//System.out.print("accesoUsuario.Close");
			}
			return result;
		}
	}*/

	public boolean checkUserPass(String cUsuario, String cPassword) {
		Vector data = null;
		try {
			String lSQL = "select CPWDSLT,CPWDHSH, case when DTCAMBIOCONTRA < current date - "
					+ vParametros.getPropEspecifica("EXPDAYS")
					+ " days then 1 when DTCAMBIOCONTRA is null then 1 else 0 end case from segusuario where cUsuario = '"
					+ cUsuario + "'";
	
			data = FindByGeneric("", lSQL,
					vParametros.getPropEspecifica("ConDB"));
			if (data.size() > 0) {
				builder = new StringBuilder();
				usrData = (TVDinRep) data.get(0);
				if (usrData.getString("CASE").equals("1")) {
					lChgPwd = true;
				}
				builder.append(PBKDF2_ITERATIONS).append(":")
						.append(usrData.get("CPWDSLT")).append(":")
						.append(usrData.get("CPWDHSH"));
				
				return PasswordHash.validatePassword(cPassword,
						builder.toString());
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	public boolean islChgPwd() {
		return lChgPwd;
	}

	public void setlChgPwd(boolean lChgPwd) {
		this.lChgPwd = lChgPwd;
	}
	
}

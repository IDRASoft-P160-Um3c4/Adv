package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>
 * Title: TDGRLPersona.java
 * </p>
 * <p>
 * Description: DAO de la entidad GRLPersona
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Tecnología InRed S.A. de C.V.
 * </p>
 * 
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDGRLPersona extends DAOBase {
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros
			.getPropEspecifica("ConDBModulo");

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
			cError = e.toString();
		} finally {
			if (!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	/**
	 * Inserta el registro dado por la entidad vData.
	 * <p>
	 * <b> insert into
	 * GRLPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial
	 * ,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp) values
	 * (?,?,?,?,?,?,?,?,?) </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iCvePersona, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexión anidada que permite que el método se
	 *            encuentre dentro de una transacción mayor.
	 * @throws DAOException
	 *             - Excepción de tipo DAO
	 * @return TVDinRep - VO Dinámico que contiene a la entidad a Insertada, así
	 *         como a la llave de esta entidad.
	 */
	public TVDinRep insert(TVDinRep vData, Connection cnNested)
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
			String lSQL = "insert into GRLPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp) values (?,?,?,?,?,?,?,?,?)";

			// AGREGAR AL ULTIMO ...
			Vector vcData = findByCustom("",
					"select MAX(iCvePersona) AS iCvePersona from GRLPersona");
			if (vcData.size() > 0) {
				TVDinRep vUltimo = (TVDinRep) vcData.get(0);
				vData.put("iCvePersona", vUltimo.getInt("iCvePersona") + 1);
			} else
				vData.put("iCvePersona", 1);
			vData.addPK(vData.getString("iCvePersona"));
			// ...

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1, vData.getInt("iCvePersona"));
			lPStmt.setString(2, vData.getString("cRFC"));
			lPStmt.setString(3, vData.getString("cRPA"));
			lPStmt.setInt(4, vData.getInt("iTipoPersona"));
			lPStmt.setString(5, vData.getString("cNomRazonSocial"));
			lPStmt.setString(6, vData.getString("cApPaterno"));
			lPStmt.setString(7, vData.getString("cApMaterno"));
			lPStmt.setString(8, vData.getString("cCorreoE"));
			lPStmt.setString(9, vData.getString("cPseudonimoEmp"));
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

	/**
	 * Elimina al registro a través de la entidad dada por vData.
	 * <p>
	 * <b> delete from GRLPersona where iCvePersona = ? </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iCvePersona, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexión anidada que permite que el método se
	 *            encuentre dentro de una transacción mayor.
	 * @throws DAOException
	 *             - Excepción de tipo DAO
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
			String lSQL = "delete from GRLPersona where iCvePersona = ?  ";
			// ...

			lPStmt = conn.prepareStatement(lSQL);

			lPStmt.setInt(1, vData.getInt("iCvePersona"));

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
	 * <b> update GRLPersona set cRFC=?, cRPA=?, iTipoPersona=?,
	 * cNomRazonSocial=?, cApPaterno=?, cApMaterno=?, cCorreoE=?,
	 * cPseudonimoEmp=? where iCvePersona = ? </b>
	 * </p>
	 * <p>
	 * <b> Campos Llave: iCvePersona, </b>
	 * </p>
	 * 
	 * @param vData
	 *            TVDinRep - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested
	 *            Connection - Conexión anidada que permite que el método se
	 *            encuentre dentro de una transacción mayor.
	 * @throws DAOException
	 *             - Excepción de tipo DAO
	 * @return TVDinRep - Entidad Modificada.
	 */
	public TVDinRep update(TVDinRep vData, Connection cnNested)
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
			String lSQL = "update GRLPersona set cRFC=?, cRPA=?, iTipoPersona=?, cNomRazonSocial=?, cApPaterno=?, cApMaterno=?, cCorreoE=?, cPseudonimoEmp=? where iCvePersona = ? ";

			vData.addPK(vData.getString("iCvePersona"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setString(1, vData.getString("cRFC"));
			lPStmt.setString(2, vData.getString("cRPA"));
			lPStmt.setInt(3, vData.getInt("iTipoPersona"));
			lPStmt.setString(4, vData.getString("cNomRazonSocial"));
			lPStmt.setString(5, vData.getString("cApPaterno"));
			lPStmt.setString(6, vData.getString("cApMaterno"));
			lPStmt.setString(7, vData.getString("cCorreoE"));
			lPStmt.setString(8, vData.getString("cPseudonimoEmp"));
			lPStmt.setInt(9, vData.getInt("iCvePersona"));
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

	public TVDinRep insertPersonaDomicilioADV(TVDinRep vData,
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
			
			String lSQL = "insert into GRLPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cPseudonimoEmp) values (?,?,?,?,?,?,?,?,?)";

			// AGREGAR AL ULTIMO ...
			Vector vcData = findByCustom("",
					"select MAX(iCvePersona) AS iCvePersona from GRLPersona");
			if (vcData.size() > 0) {
				TVDinRep vUltimo = (TVDinRep) vcData.get(0);
				vData.put("iCvePersona", vUltimo.getInt("iCvePersona") + 1);
			} else
				vData.put("iCvePersona", 1);
			vData.addPK(vData.getString("iCvePersona"));

			lPStmt = conn.prepareStatement(lSQL);
			
			lPStmt.setInt(1, vData.getInt("iCvePersona"));
			lPStmt.setString(2, vData.getString("cRFC"));
			lPStmt.setString(3, "");//vData.getString("cRPA"));
			lPStmt.setInt(4, vData.getInt("hdiTipo"));
			lPStmt.setString(5, vData.getString("cNomRazonSocial"));
			lPStmt.setString(6, vData.getString("cApPaterno"));
			lPStmt.setString(7, vData.getString("cApMaterno"));
			lPStmt.setString(8, vData.getString("cCorreoE"));
			lPStmt.setString(9, "");//vData.getString("cPseudonimoEmp"));
			lPStmt.executeUpdate();
			
			if (cnNested == null) {
				conn.commit();
			}
			
			String lSQLDom =
			          "insert into GRLDomicilio(iCvePersona,iCveDomicilio,iCveTipoDomicilio,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad,lPredeterminado) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			      // Buscar clave de domicilio mayor y agregar uno para el registro a crear.
			      Vector vcDataDom = findByCustom("",
			                                   "select MAX(iCveDomicilio) AS iCveDomicilio from GRLDomicilio where iCvePersona = " +
			                                   vData.getInt("iCvePersona"));
			      vData.put("iCveDomicilio",(vcData.size() > 0) ? ((TVDinRep) vcData.get(0)).getInt("iCveDomicilio") + 1 : 1);

			      vData.addPK(vData.getString("iCvePersona"));
			      vData.addPK(vData.getString("iCveDomicilio"));

			      lPStmt = conn.prepareStatement(lSQLDom);
			      lPStmt.setInt(1,vData.getInt("iCvePersona"));
			      lPStmt.setInt(2,vData.getInt("iCveDomicilio"));
			      lPStmt.setInt(3,1);//vData.getInt("iCveTipoDomicilio"));
			      lPStmt.setString(4,vData.getString("cCalle"));
			      lPStmt.setString(5,vData.getString("cNumExterior"));
			      lPStmt.setString(6,vData.getString("cNumInterior"));
			      lPStmt.setString(7,vData.getString("cColonia"));
			      lPStmt.setString(8,vData.getString("cCodPostal"));
			      lPStmt.setString(9,vData.getString("cTelefono"));
			      lPStmt.setInt(10,1);//1=>mexico vData.getInt("iCvePais"));
			      lPStmt.setInt(11,vData.getInt("iCveEntidadFed"));
			      lPStmt.setInt(12,vData.getInt("iCveMunicipio"));
			      lPStmt.setInt(13,vData.getInt("iCveLocalidad"));
			      lPStmt.setInt(14,1);//vData.getInt("lPredeterminado"));
			      
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
	
	public TVDinRep guardaAsociacionPersonaRepL(TVDinRep vData,
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
			
			//se elimina la relacion existente
			String lSQL = "DELETE FROM GRLREPLEGAL WHERE ICVEEMPRESA = " +vData.getInt("iCveRepresentado");
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.executeUpdate();
			
			if (cnNested == null) {
				conn.commit();
			}
			
			//se inserta la nueva relacion
			lSQL = "INSERT INTO GRLREPLEGAL VALUES (?,?,CURRENT_DATE,1)";	
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iCveRepresentado"));
			lPStmt.setInt(2,vData.getInt("iCveRepL"));
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
	
	public TVDinRep updatePersonaDomicilioADV(TVDinRep vData, Connection cnNested)
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
			String lSQL = "update GRLPersona set cRFC=?, iTipoPersona=?, cNomRazonSocial=?, cApPaterno=?, cApMaterno=?, cCorreoE=? where iCvePersona = ? ";

			vData.addPK(vData.getString("iCvePersona"));

			//System.out.print(vData.getInt("hdiTipo"));
			
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setString(1, vData.getString("cRFC"));
			lPStmt.setInt(2, vData.getInt("hdiTipo"));
			lPStmt.setString(3, vData.getString("cNomRazonSocial"));
			lPStmt.setString(4, vData.getString("cApPaterno"));
			lPStmt.setString(5, vData.getString("cApMaterno"));
			lPStmt.setString(6, vData.getString("cCorreoE"));
			lPStmt.setInt(7, vData.getInt("iCvePersona"));
			lPStmt.executeUpdate();
			
			String lSQLDom ="update GRLDomicilio set cCalle=?, cNumExterior=?, cNumInterior=?, cColonia=?, cCodPostal=? , cTelefono= ? ,iCveEntidadFed=? ,iCveMunicipio=? ,iCveLocalidad =? where iCvePersona=? and iCveDomicilio=?";
			
		      lPStmt = conn.prepareStatement(lSQLDom);
		      lPStmt.setString(1,vData.getString("cCalle"));
		      lPStmt.setString(2,vData.getString("cNumExterior"));
		      lPStmt.setString(3,vData.getString("cNumInterior"));
		      lPStmt.setString(4,vData.getString("cColonia"));
		      lPStmt.setString(5,vData.getString("cCodPostal"));
		      lPStmt.setString(6,vData.getString("cTelefono"));
		      lPStmt.setString(7,vData.getString("iCveEntidadFed"));
		      lPStmt.setInt(8,vData.getInt("iCveMunicipio"));
		      lPStmt.setInt(9,vData.getInt("iCveLocalidad"));
		      lPStmt.setInt(10,vData.getInt("iCvePersona"));
		      lPStmt.setInt(11,vData.getInt("iCveDomicilio"));
		      		      
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

	
	public TVDinRep borraAsociacionPersonaRepL(TVDinRep vData,
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
			
			//se elimina la relacion existente
			String lSQL = "DELETE FROM GRLREPLEGAL WHERE ICVEEMPRESA = " +vData.getInt("iCveRepresentado");
			lPStmt = conn.prepareStatement(lSQL);
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
	
	public TVDinRep borrarPersonaDomicilio(TVDinRep vData,
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
			
			String lSQL = "DELETE FROM GRLDOMICILIO WHERE ICVEPERSONA = " + vData.getString("iCvePersona");
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.executeUpdate();
			
			lSQL = "DELETE FROM GRLREPLEGAL WHERE ICVEEMPRESA = " + vData.getString("iCvePersona");
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.executeUpdate();
			
			lSQL = "DELETE FROM GRLPERSONA WHERE ICVEPERSONA = " + vData.getString("iCvePersona");
			lPStmt = conn.prepareStatement(lSQL);
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

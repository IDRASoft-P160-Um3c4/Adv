package gob.sct.sipmm.dao.reporte;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDCYSCitaNotificacion.java</p>
 * <p>Description: DAO de la entidad CYSCitaNotificacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */

public class TDRAINotOficio extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	/**
	 * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
	 * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
	 * @param cWhere String  - Cadena que contiene al query a ejecutar.
	 * @throws DAOException  - Excepción de tipo DAO
	 * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
	 */

	public TDRAINotOficio(){
	}

	public Vector findByCustom(String cKey,String cWhere) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			String cSQL = cWhere;
			vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
		} catch(Exception e){
			cError = e.toString();
		} finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}
	/**
	 * Inserta el registro dado por la entidad vData.
	 * <p><b> insert into CYSCitaNotificacion(iMovCitaNotificacion,dtRegistro,iCvePersona,iCveDomicilio,lEsDomicilio,tsCitatorio,lEstaPresenteCitado,lCitatorioEntregado,cRecibeCitatorio,cTestigoNotCitatorio,cTestigoActCitatorio,tsFechaNotificacion,cNotificador,cIdentificacionNoficador,cNoIdentificacionNotificador,cTestigoNotificador,cIdentificacionTesNot,cNoIdentificacionTesNot,cTestigoActuante,cIdentificacionTesAct,cNoIdentificacionTesAct,cObsNotificacion,iCveOficina) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
	 * <p><b> Campos Llave: iMovCitaNotificacion, </b></p>
	 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
	 * @throws DAOException       - Excepción de tipo DAO
	 * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
	 */
	public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		PreparedStatement lPStmt1 = null;
		boolean lSuccess = true;
		int iConsecutivo=0;
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL =
				"insert into CYSCitaNotificacion(iMovCitaNotificacion,iCvePersona,iCveDomicilio,iCveOficina) " +
				"values(?,?,?,?)";

			//AGREGAR AL ULTIMO ...
			Vector vcData = findByCustom("","select MAX(iMovCitaNotificacion) AS iMovCitaNotificacion from CYSCitaNotificacion");
			if(vcData.size() > 0){
				TVDinRep vUltimo = (TVDinRep) vcData.get(0);
				vData.put("iMovCitaNotificacion",vUltimo.getInt("iMovCitaNotificacion") + 1);
			}else
				vData.put("iMovCitaNotificacion",1);
			vData.addPK(vData.getString("iMovCitaNotificacion"));
			//...

			iConsecutivo = vData.getInt("iMovCitaNotificacion");

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,iConsecutivo);
			lPStmt.setInt(2,vData.getInt("iCvePersona"));
			lPStmt.setInt(3,vData.getInt("iCveDomicilio"));
			lPStmt.setInt(4,vData.getInt("iCveOficina"));
			lPStmt.executeUpdate();
			lPStmt.close();

			String cAgregar = "insert into RAINotOficio(iCveRegularizacion,iConsecutivo,iMovCitaNotificacion) values(?,?,?)";
			lPStmt1 = conn.prepareStatement(cAgregar);
			lPStmt1.setInt(1,vData.getInt("iCveRegularizacion"));
			lPStmt1.setInt(2,vData.getInt("iConsecutivo"));
			lPStmt1.setInt(3,iConsecutivo);
			lPStmt1.executeUpdate();
			lPStmt1.close();

			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			warn("insert",ex);
			if(cnNested == null){
				try{
					conn.rollback();
				} catch(Exception e){
					fatal("insert.rollback",e);
				}
			}
			lSuccess = false;
		} finally{
			try{
				if(lPStmt != null){
					lPStmt.close();
				}
				if(lPStmt1 != null){
					lPStmt1.close();
				}
				if(cnNested == null){
					if(conn != null){
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch(Exception ex2){
				warn("insert.close",ex2);
			}
			if(lSuccess == false)
				throw new DAOException("");
			return vData;
		}
	}
	/**
	 * Elimina al registro a través de la entidad dada por vData.
	 * <p><b> delete from CYSCitaNotificacion where iMovCitaNotificacion = ?  </b></p>
	 * <p><b> Campos Llave: iMovCitaNotificacion, </b></p>
	 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
	 * @throws DAOException       - Excepción de tipo DAO
	 * @return boolean            - En caso de ser o no eliminado el registro.
	 */
	public boolean delete(TVDinRep vData,Connection cnNested)throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		PreparedStatement lPStmt1 = null;
		boolean lSuccess = true;
		String cMsg = "";
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			String cAgregar = "delete from RAINotOficio where iCveRegularizacion = ? and iConsecutivo = ? and iMovCitaNotificacion = ?";
			lPStmt1 = conn.prepareStatement(cAgregar);

			lPStmt1.setInt(1,vData.getInt("iCveRegularizacion"));
			lPStmt1.setInt(2,vData.getInt("iConsecutivo"));
			lPStmt1.setInt(3,vData.getInt("iMovCitaNotificacion"));
			lPStmt1.executeUpdate();

			//Ajustar Where de acuerdo a requerimientos...
			String lSQL = "delete from CYSCitaNotificacion where iMovCitaNotificacion = ?  ";
			//...

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iMovCitaNotificacion"));
			lPStmt.executeUpdate();

			if(cnNested == null){
				conn.commit();
			}
		} catch(SQLException sqle){
			lSuccess = false;
			cMsg = ""+sqle.getErrorCode();
		} catch(Exception ex){
			warn("delete",ex);
			if(cnNested == null){
				try{
					conn.rollback();
				} catch(Exception e){
					fatal("delete.rollback",e);
				}
			}
			lSuccess = false;
		} finally{
			try{
				if(lPStmt != null){
					lPStmt.close();
				}
				if(lPStmt1 != null){
					lPStmt1.close();
				}
				if(cnNested == null){
					if(conn != null){
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch(Exception ex2){
				warn("delete.close",ex2);
			}
			if(lSuccess == false)
				throw new DAOException(cMsg);
			return lSuccess;
		}
	}
	/**
	 * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
	 * <p><b> update CYSCitaNotificacion set dtRegistro=?, iCvePersona=?, iCveDomicilio=?, lEsDomicilio=?, tsCitatorio=?, lEstaPresenteCitado=?, lCitatorioEntregado=?, cRecibeCitatorio=?, cTestigoNotCitatorio=?, cTestigoActCitatorio=?, tsFechaNotificacion=?, cNotificador=?, cIdentificacionNoficador=?, cNoIdentificacionNotificador=?, cTestigoNotificador=?, cIdentificacionTesNot=?, cNoIdentificacionTesNot=?, cTestigoActuante=?, cIdentificacionTesAct=?, cNoIdentificacionTesAct=?, cObsNotificacion=?, iCveOficina=?, iCveDepartamento=? where iMovCitaNotificacion = ?  </b></p>
	 * <p><b> Campos Llave: iMovCitaNotificacion, </b></p>
	 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
	 * @throws DAOException       - Excepción de tipo DAO
	 * @return TVDinRep           - Entidad Modificada.
	 */
	public TVDinRep update(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update CYSCitaNotificacion set dtRegistro=?, iCvePersona=?, iCveDomicilio=?, lEsDomicilio=?, tsCitatorio=?, " +
			"lEstaPresenteCitado=?, lCitatorioEntregado=?, cRecibeCitatorio=?, cTestigoNotCitatorio=?, " +
			"cTestigoActCitatorio=?, tsFechaNotificacion=?, cNotificador=?, cIdentificacionNoficador=?, " +
			"cNoIdentificacionNotificador=?, cTestigoNotificador=?, cIdentificacionTesNot=?, cNoIdentificacionTesNot=?, " +
			"cTestigoActuante=?, cIdentificacionTesAct=?, cNoIdentificacionTesAct=?, cObsNotificacion=?, " +
			"iCveOficina=? where iMovCitaNotificacion = ? ";

			vData.addPK(vData.getString("iMovCitaNotificacion"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setDate(1,vData.getDate("dtRegistro"));
			lPStmt.setInt(2,vData.getInt("iCvePersona"));
			lPStmt.setInt(3,vData.getInt("iCveDomicilio"));
			lPStmt.setInt(4,vData.getInt("lEsDomicilio"));
			lPStmt.setTimestamp(5,vData.getTimeStamp("tsCitatorio"));
			lPStmt.setInt(6,vData.getInt("lEstaPresenteCitado"));
			lPStmt.setInt(7,vData.getInt("lCitatorioEntregado"));
			lPStmt.setString(8,vData.getString("cRecibeCitatorio"));
			lPStmt.setString(9,vData.getString("cTestigoNotCitatorio"));
			lPStmt.setString(10,vData.getString("cTestigoActCitatorio"));
			lPStmt.setTimestamp(11,vData.getTimeStamp("tsFechaNotificacion"));
			lPStmt.setString(12,vData.getString("cNotificador"));
			lPStmt.setString(13,vData.getString("cIdentificacionNoficador"));
			lPStmt.setString(14,vData.getString("cNoIdentificacionNotificador"));
			lPStmt.setString(15,vData.getString("cTestigoNotificador"));
			lPStmt.setString(16,vData.getString("cIdentificacionTesNot"));
			lPStmt.setString(17,vData.getString("cNoIdentificacionTesNot"));
			lPStmt.setString(18,vData.getString("cTestigoActuante"));
			lPStmt.setString(19,vData.getString("cIdentificacionTesAct"));
			lPStmt.setString(20,vData.getString("cNoIdentificacionTesAct"));
			lPStmt.setString(21,vData.getString("cObsNotificacion"));
			lPStmt.setInt(22,vData.getInt("iCveOficina"));
			lPStmt.setInt(23,vData.getInt("iMovCitaNotificacion"));
			lPStmt.executeUpdate();
			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			warn("update",ex);
			if(cnNested == null){
				try{
					conn.rollback();
				} catch(Exception e){
					fatal("update.rollback",e);
				}
			}
			lSuccess = false;
		} finally{
			try{
				if(lPStmt != null){
					lPStmt.close();
				}
				if(cnNested == null){
					if(conn != null){
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch(Exception ex2){
				warn("update.close",ex2);
			}
			if(lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep updNotificacion(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update RAINotOficio " +
			"set iCveRegularizacionNot=?," +
			"iConsecutivoNot=? " +
			"where iCveRegularizacion=? " +
			"and iConsecutivo=? " +
			"and iMovCitaNotificacion=? ";

			vData.addPK(vData.getString("iCveRegularizacion"));
			vData.addPK(vData.getString("iConsecutivo"));
			vData.addPK(vData.getString("iMovCitaNotificacion"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iCveRegularizacionNot"));
			lPStmt.setInt(2,vData.getInt("iConsecutivoNot"));
			lPStmt.setInt(3,vData.getInt("iCveRegularizacion"));
			lPStmt.setInt(4,vData.getInt("iConsecutivo"));
			lPStmt.setInt(5,vData.getInt("iMovCitaNotificacion"));
			lPStmt.executeUpdate();
			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			warn("update",ex);
			if(cnNested == null){
				try{
					conn.rollback();
				} catch(Exception e){
					fatal("update.rollback",e);
				}
			}
			lSuccess = false;
		} finally{
			try{
				if(lPStmt != null){
					lPStmt.close();
				}
				if(cnNested == null){
					if(conn != null){
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch(Exception ex2){
				warn("update.close",ex2);
			}
			if(lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

	public TVDinRep updRecordatorio(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "update RAINotOficio " +
			"set iCveRegularizacionRec=?," +
			"iConsecutivoRec=? " +
			"where iCveRegularizacion=? " +
			"and iConsecutivo=? " +
			"and iMovCitaNotificacion=? ";

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iMovProcedimiento"));
			vData.addPK(vData.getString("iMovFolioProc"));
			vData.addPK(vData.getString("iMovCitaNotificacion"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iCveRegularizacionRec"));
			lPStmt.setInt(2,vData.getInt("iConsecutivoRec"));
			lPStmt.setInt(3,vData.getInt("iCveRegularizacion"));
			lPStmt.setInt(4,vData.getInt("iConsecutivo"));
			lPStmt.setInt(5,vData.getInt("iMovCitaNotificacion"));
			lPStmt.executeUpdate();
			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			warn("update",ex);
			if(cnNested == null){
				try{
					conn.rollback();
				} catch(Exception e){
					fatal("update.rollback",e);
				}
			}
			lSuccess = false;
		} finally{
			try{
				if(lPStmt != null){
					lPStmt.close();
				}
				if(cnNested == null){
					if(conn != null){
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch(Exception ex2){
				warn("update.close",ex2);
			}
			if(lSuccess == false)
				throw new DAOException("");

			return vData;
		}
	}

}

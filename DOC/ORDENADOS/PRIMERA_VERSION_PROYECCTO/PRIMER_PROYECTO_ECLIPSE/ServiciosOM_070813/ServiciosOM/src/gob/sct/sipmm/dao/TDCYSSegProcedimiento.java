package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCYSSegProcedimiento.java</p>
 * <p>Description: DAO de la entidad CYSSegProcedimiento</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDCYSSegProcedimiento extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

	/**
	 * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
	 * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
	 * @param cWhere String  - Cadena que contiene al query a ejecutar.
	 * @throws DAOException  - Excepción de tipo DAO
	 * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
	 */
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
        public Vector findByCustomInconformidades(String cKey,String cWhere, int proceso) throws DAOException{
                Vector vcRecords = new Vector();
                TFechas fecha = new TFechas();
                cError = "";
                int iDiasTrans=0;
                String cDiasTrans1="";
                try{
                        String cSQL = cWhere;
                        vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
                        if(vcRecords.size()>0)  {
                          System.out.println("*****     "+vcRecords.size());
                          for(int i=0; i<vcRecords.size(); i++){
                            TVDinRep vUltimo = (TVDinRep) vcRecords.get(i);
                            System.out.println("*****     \n"+vUltimo.toHashMap().toString());
                            if(proceso !=  6){
                              String cInc = "";
                              String cInconformidades =
                                  "SELECT I.ICVEINCONFORMIDAD,IR.LENFIRME,IR.ICVERESPUESTA,JR.CDSCJUICIO " +
                                  "FROM CYSRECURSOJUICIO RJ " +
                                  "join RININCONFORMIDAD I ON RJ.ICVEINCONFORMIDAD = I.ICVEINCONFORMIDAD " +
                                  "JOIN RINJUICIOREC JR ON JR.ICVEJUICIOREC = I.ICVEJUICIOREC " +
                                  "LEFT JOIN RININCONFORMIDADRESOLUCION IR ON I.ICVEINCONFORMIDAD = IR.ICVEINCONFORMIDAD " +
                                  "WHERE RJ.IEJERCICIO = " + vUltimo.getInt("iEjercicio") +
                                  " AND RJ.IMOVPROCEDIMIENTO =  " + vUltimo.getInt("iMovProcedimiento") +
                                  " ORDER BY IR.IMOVINCONFORMIDAD DESC ";
                              String cFechas =
                                  "select " +
                                  "ccn.tsfechanotificacion, " +
                                  "cpa.IDIASOTORGADOS as iDiasProrroga, " +
                                  "cpaAl.IDIASOTORGADOS as iDiasAlegato " +
                                  "from CYSSegProcedimiento " +
                                  "join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio " +
                                  "and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento " +
                                  "and CYSFolioProc.lEsProrroga = 0 " +
                                  "and CYSFolioProc.lEsAlegato = 0 " +
                                  "and CYSFolioProc.lEsSinEfecto = 0 " +
                                  "and CYSFolioProc.lEsNotificacion = 0 " +
                                  "and CYSFolioProc.lEsRecordatorio = 0 " +
                                  "and CYSFolioProc.lEsResolucion = 0 " +
                                  "left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio " +
                                  "and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento " +
                                  "and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc " +
                                  "left join CYSFolioProc cfp on cfp.iEjercicio = CYSSegProcedimiento.iEjercicio " +
                                  "and cfp.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento " +
                                  "and cfp.lEsProrroga = 1 " +
                                  "and cfp.lEsAlegato = 0 " +
                                  "and cfp.lEsSinEfecto = 0 " +
                                  "and cfp.lEsNotificacion = 0 " +
                                  "and cfp.lEsRecordatorio = 0 " +
                                  "and cfp.lEsResolucion = 0 " +
                                  "left join CYSPRORROGAALEGATO cpa on cfp.IEJERCICIO = cpa.IEJERCICIO " +
                                  "and cfp.IMOVPROCEDIMIENTO = cpa.IMOVPROCEDIMIENTO " +
                                  "and cfp.IMOVFOLIOPROC = cpa.IMOVFOLIOPROC " +
                                  "left join CYSFolioProc cfpAl on cfpAl.iEjercicio = CYSSegProcedimiento.iEjercicio " +
                                  "and cfpAl.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento " +
                                  "and cfpAl.lEsProrroga = 0 " +
                                  "and cfpAl.lEsAlegato = 1 " +
                                  "and cfpAl.lEsSinEfecto = 0 " +
                                  "and cfpAl.lEsNotificacion = 0 " +
                                  "and cfpAl.lEsRecordatorio = 0 " +
                                  "and cfpAl.lEsResolucion = 0 " +
                                  "left join CYSPRORROGAALEGATO cpaAl on cfpAl.IEJERCICIO = cpaAl.IEJERCICIO " +
                                  "and cfpAl.IMOVPROCEDIMIENTO = cpaAl.IMOVPROCEDIMIENTO " +
                                  "and cfpAl.IMOVFOLIOPROC = cpaAl.IMOVFOLIOPROC " +
                                  "left join CYSCITANOTIFICACION ccn on CYSNOTOFICIO.IMOVCITANOTIFICACION = CCN.IMOVCITANOTIFICACION " +
                                  "where CYSSegProcedimiento.iEjercicio = " +
                                  vUltimo.getInt("iEjercicio") + " " +
                                  "and CYSSegProcedimiento.iMovProcedimiento = " +
                                  vUltimo.getInt("iMovProcedimiento") + " " +
                                  "and CYSFolioProc.iMovFolioProc = (select MAX(CYSFolioProc.iMovFolioProc) " +
                                  "from CYSFolioProc " +
                                  "where CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio " +
                                  "and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento " +
                                  "and CYSFolioProc.lEsProrroga = 0 " +
                                  "and CYSFolioProc.lEsAlegato = 0 " +
                                  "and CYSFolioProc.lEsSinEfecto = 0 " +
                                  "and CYSFolioProc.lEsNotificacion = 0 " +
                                  "and CYSFolioProc.lEsRecordatorio = 0 " +
                                  "and CYSFolioProc.lEsResolucion = 0) ";
                              Vector vcInc = this.findByCustom("",cInconformidades);
                              Vector vcFechas = this.findByCustom("",cFechas);
                              String cFechaLimite = "";
                              String cDiasFaltantes = "";
                              TDGRLDiaNoHabil dh = new TDGRLDiaNoHabil();
                              java.sql.Date dtFechaLimite = null;
                              TVDinRep vFechas = new TVDinRep();
                              int diasProrroga = 0,diasAlegato = 0;
                              if(vcFechas.size() > 0){
                                vFechas = (TVDinRep) vcFechas.get(0);
                                diasProrroga = vFechas.getInt("iDiasProrroga");
                                diasAlegato = vFechas.getInt("iDiasAlegato");
                                if(! (vFechas.getTimeStamp("tsfechanotificacion") +
                                      "").equals("null")){
                                  int iDiasLimite = 0;
                                  if(proceso == 1) iDiasLimite = 10;
                                  if(proceso == 2) iDiasLimite = 15;
                                  if(proceso == 4) iDiasLimite = 15;
                                  dtFechaLimite = dh.getFechaFinal(fecha.getDateSQL(vFechas.
                                      getTimeStamp("tsfechanotificacion")),
                                                                   (diasProrroga + diasAlegato +
                                                                    iDiasLimite),false);
                                  int iDiasEntre = getDaysBetweenDatesHabiles(fecha.getDateSQL(
                                      fecha.getThisTime()),dh.getFechaFinal(dtFechaLimite,30,false));
                                  if(iDiasEntre < 6){
                                    cDiasFaltantes = "<font color=red>" + iDiasEntre +
                                        (iDiasEntre == 1 ? " Dia</font>" : " Dias</font>");
                                  } else cDiasFaltantes = "";

                                  if(comparaFechas(dtFechaLimite,
                                                   fecha.getDateSQL(fecha.getThisTime())))
                                    cFechaLimite = "<font color=blue>" +
                                        fecha.getDateSPN(dtFechaLimite) + "</font>";
                                  else
                                    cFechaLimite = "<font color=red>" +
                                        fecha.getDateSPN(dtFechaLimite) + "</font>";
                                }
                              }

                                if(vcInc.size()>0){
                                  TVDinRep vInconformidad = (TVDinRep) vcInc.get(0);
                                  String cColor = "#8E35EF";
                                  if(vInconformidad.getInt("LENFIRME")==1) cColor = "#0000A0";
                                  vUltimo.put("cInconformidad","<font color="+cColor+">Suspensión<br>Inconformidad # "+vInconformidad.getInt("ICVEINCONFORMIDAD")+"</font>");
                                  vUltimo.put("iCveInconformidad",vInconformidad.getInt("ICVEINCONFORMIDAD"));
                                  vUltimo.put("lEnfirme",vInconformidad.getInt("LENFIRME"));
                                }
                                else{
                                  vUltimo.put("cInconformidad","");
                                  vUltimo.put("iCveInconformidad",0);
                                  vUltimo.put("lEnfirme",0);
                                }

                              vUltimo.put("cFechaLimite",cFechaLimite);
                              vUltimo.put("iDiasFaltantes",cDiasFaltantes);
                            }


                            //Envio de Datos al listado, estos ultimos 3 datos se agregaran al final del aRes dado en el JSP
                            if(proceso == 6){
                              String cQry2 =
                                  "SELECT SP5.IEJERCICIOANT,SP5.IMOVPROCEDIMIENTOANT FROM CYSSEGPROCEDIMIENTO SP6 " +
                                  "JOIN CYSSEGPROCEDIMIENTO SP5 ON SP6.IEJERCICIOANT = SP5.IEJERCICIO AND SP6.IMOVPROCEDIMIENTOANT = SP5.IMOVPROCEDIMIENTO " +
                                  "WHERE SP6.IEJERCICIO = "+vUltimo.getInt("iEjercicio")+" AND SP6.IMOVPROCEDIMIENTO = "+vUltimo.getInt("iMovProcedimiento");
                              Vector vcQry2 = this.findByCustom("",cQry2);

                              if(vcQry2.size()>0){
                                TVDinRep vQry2 = (TVDinRep) vcQry2.get(0);
                                String cQry1 =
                                    "SELECT " +
                                    "notif.TSFECHANOTIFICACION " +
                                    "FROM CYSSEGPROCEDIMIENTO csp " +
                                    "left join CYSFOLIOPROC cfp on csp.IEJERCICIO = cfp.IEJERCICIO " +
                                    "and csp.IMOVPROCEDIMIENTO = cfp.IMOVPROCEDIMIENTO " +
                                    "and cfp.lEsProrroga <> 1 " +
                                    "and cfp.lEsAlegato <> 1 " +
                                    "and cfp.lEsSinEfecto <> 1 " +
                                    "and cfp.lEsNotificacion <> 1 " +
                                    "and cfp.lEsRecordatorio <> 1 " +
                                    "and cfp.lEsResolucion = 1 " +
                                    "and cfp.LESREVERSION <> 1 " +
                                    "and cfp.LESACTAADMVA <> 1 " +
                                    "left join CYSNOTOFICIO notof on cfp.IEJERCICIO = notof.IEJERCICIO " +
                                    "and cfp.IMOVPROCEDIMIENTO = notof.IMOVPROCEDIMIENTO " +
                                    "and cfp.IMOVFOLIOPROC = notof.IMOVFOLIOPROC " +
                                    "left join CYSCITANOTIFICACION notif on notof.IMOVCITANOTIFICACION = notif.IMOVCITANOTIFICACION " +
                                    "where csp.IEJERCICIO = " + vQry2.getInt("IEJERCICIOANT") +
                                    " and csp.ICVEPROCEDIMIENTO = 4 " +
                                    " and csp.IMOVPROCEDIMIENTO = " +
                                    vQry2.getInt("IMOVPROCEDIMIENTOANT")+
                                    " order by TSFECHANOTIFICACION";
                                Vector vcQry1 = this.findByCustom("",cQry1);
                                if(vcQry1.size() > 0){
                                  TVDinRep vFechaNotif = (TVDinRep) vcQry1.get(0);

                                  if(vFechaNotif.getTimeStamp("TSFECHANOTIFICACION") != null){
                                    iDiasTrans = fecha.getDaysBetweenDates(fecha.getDateSQL(
                                        vFechaNotif.getTimeStamp("TSFECHANOTIFICACION")),
                                        fecha.getDateSQL(fecha.getThisTime()));

                                    if(iDiasTrans >= 0) cDiasTrans1 =
                                        "<font color=#F52887> Menos de 30 Dias</font>";
                                    if(iDiasTrans > 30) cDiasTrans1 =
                                        "<font color=#F52887>Más de 30 Días</font>";
                                    if(iDiasTrans > 60) cDiasTrans1 =
                                        "<font color=#F52887>Más de 60 Días</font>";
                                    if(iDiasTrans > 90) cDiasTrans1 =
                                        "<font color=#F52887>Más de 90 Días</font>";
                                    if(iDiasTrans > 120) cDiasTrans1 =
                                        "<font color=#F52887>Más de 120 Días</font>";
                                    if(iDiasTrans > 150) cDiasTrans1 = "<font color=red>" +
                                        iDiasTrans + " Dias </font>";
                                    if(iDiasTrans < 0) cDiasTrans1="";
                                  } else cDiasTrans1 = "";
                                } else cDiasTrans1 = "";
                                vUltimo.put("iDiasTranscurridos",cDiasTrans1);
                              }
                              else
                            	  vUltimo.put("iDiasTranscurridos","");
                            }
                          }
                        }

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
	 * <p><b> insert into CYSSegProcedimiento(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
	 * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
		boolean lSuccess = true;
		int iConsecutivo = this.findMax(vData.getInt("iEjercicio"));
		vData.put("iMovProcedimiento",iConsecutivo);
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "insert into CYSSegProcedimiento(iEjercicio, " +
			"iMovProcedimiento, " +
			"iCveTitulo, " +
			"dtSeguimiento, " +
			"cNumOficio, " +
			"iCveProcedimiento, " +
			"cObsProcedimiento, " +
			"iCveUsuario, " +
			"iEjercicioAnt, " +
			"iMovProcedimientoAnt, " +
			"lActivo ) " +
			"values (?,?,?,?,?,?,?,?,?,?,?)";

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iMovProcedimiento"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iEjercicio"));
			lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
			lPStmt.setInt(3,vData.getInt("iCveTitulo"));
			lPStmt.setDate(4,vData.getDate("dtSeguimiento"));
			lPStmt.setString(5,vData.getString("cNumOficio"));
			lPStmt.setInt(6,vData.getInt("iCveProcedimiento"));
			lPStmt.setString(7,vData.getString("cObsProcedimiento"));
			lPStmt.setInt(8,vData.getInt("iCveUsuario"));
			lPStmt.setInt(9,vData.getInt("iEjercicioAnt"));
			lPStmt.setInt(10,vData.getInt("iMovProcedimientoAnt"));
			lPStmt.setInt(11,1);
			lPStmt.executeUpdate();
			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			//ex.printStackTrace();
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
	 * <p><b> delete from CYSSegProcedimiento where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
	 * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
	 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
	 * @throws DAOException       - Excepción de tipo DAO
	 * @return boolean            - En caso de ser o no eliminado el registro.
	 */
	public boolean delete(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		String cMsg = "";
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			//Ajustar Where de acuerdo a requerimientos...
			String lSQL = "delete from CYSSegProcedimiento where iEjercicio=? " +
			"and iMovProcedimiento=? ";

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iEjercicio"));
			lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
			lPStmt.executeUpdate();
			if(cnNested == null){
				conn.commit();
			}
		} catch(SQLException sqle){
			lSuccess = false;
			cMsg = "" + sqle.getErrorCode();
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

	public boolean Eliminar(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		String cMsg = "";
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}

			//Ajustar Where de acuerdo a requerimientos...
			String lSQL = "delete from CYSRecursoJuicio where iEjercicio=? " +
			"and iMovProcedimiento=? and iCveInconformidad=?" ;

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iEjercicio"));
			lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
			lPStmt.setInt(3,vData.getInt("iConsecutivo"));
			lPStmt.executeUpdate();
			if(cnNested == null){
				conn.commit();
			}
		} catch(SQLException sqle){
			lSuccess = false;
			cMsg = "" + sqle.getErrorCode();
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
	 * <p><b> update CYSSegProcedimiento set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
	 * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
			String lSQL = "update CYSSegProcedimiento " +
			"set iCveTitulo=?, " +
			"dtSeguimiento=?, " +
			"cNumOficio=?, " +
			"iCveProcedimiento=?, " +
			"cObsProcedimiento=?, " +
			"iCveUsuario=?, " +
			"iEjercicioAnt=?, " +
			"iMovProcedimientoAnt=? " +
			"where iEjercicio=? " +
			"and iMovProcedimiento=? ";

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iMovProcedimiento"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iCveTitulo"));
			lPStmt.setDate(2,vData.getDate("dtSeguimiento"));
			lPStmt.setString(3,vData.getString("cNumOficio"));
			lPStmt.setInt(4,vData.getInt("iCveProcedimiento"));
			lPStmt.setString(5,vData.getString("cObsProcedimiento"));
			lPStmt.setInt(6,vData.getInt("iCveUsuario"));
			lPStmt.setInt(7,vData.getInt("iEjercicioAnt"));
			lPStmt.setInt(8,vData.getInt("iMovProcedimientoAnt"));
			lPStmt.setInt(9,vData.getInt("iEjercicio"));
			lPStmt.setInt(10,vData.getInt("iMovProcedimiento"));
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

	public int findMax(int iEjercicio) throws DAOException{
		DbConnection dbConn = null;
		Connection conn = null;
		Statement lStmt = null;
		ResultSet rs = null;
		int iConsecutivo = 0;
		try{

			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			lStmt = conn.createStatement();
			String lSQL =
				" SELECT MAX(iMovProcedimiento) AS iMovProcedimiento " +
				" FROM CYSSegProcedimiento " +
				" WHERE iEjercicio = " + iEjercicio;

			rs = lStmt.executeQuery(lSQL);
			while(rs.next()){
				iConsecutivo = rs.getInt("iMovProcedimiento");
			}
			iConsecutivo++;
		} catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				if(lStmt != null){
					lStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
				dbConn.closeConnection();
			} catch(Exception ex2){
			}
			return iConsecutivo;
		}
	}

	public synchronized TVDinRep Cambio(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
                TFechas Fechas = new TFechas();
		boolean lSuccess = true;
		int iConsecutivo = this.findMax(vData.getInt("iEjercicio"));
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
                        String cActivo = (vData.getInt("iCveProcedimiento")==2 || vData.getInt("iCveProcedimiento")==4)?"0 ":"1 ";
			String lSQL = "insert into CYSSegProcedimiento(CYSSegProcedimiento.iEjercicio, " +
			"CYSSegProcedimiento.iMovProcedimiento, " +
			"CYSSegProcedimiento.iCveTitulo, " +
			"CYSSegProcedimiento.iCveProcedimiento, " +
			"CYSSegProcedimiento.cObsProcedimiento, " +
			"CYSSegProcedimiento.iCveUsuario, " +
			"CYSSegProcedimiento.iEjercicioAnt, " +
			"CYSSegProcedimiento.iMovProcedimientoAnt, " +
			"CYSSegProcedimiento.lActivo )" +
			"select "+
                        Fechas.getIntYear(Fechas.getDateSQL(Fechas.getThisTime()))+
                        ", "+iConsecutivo+" ," +
			"CYSSegProAnt.iCveTitulo, " +
			"CYSSegProAnt.iCveProcedimiento + 1, " +
			"CYSSegProAnt.cObsProcedimiento, " +
			"CYSSegProAnt.iCveUsuario, " +
			"CYSSegProAnt.iEjercicio, " +
			"CYSSegProAnt.iMovProcedimiento,"+
                        cActivo +
			"from CYSSegProcedimiento CYSSegProAnt " +
			"where CYSSegProAnt.iEjercicio = ? " +
			"and CYSSegProAnt.iMovProcedimiento = ? ";

                       lPStmt = conn.prepareStatement(lSQL);
                       lPStmt.setInt(1,vData.getInt("iEjercicio"));
                       lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
                       lPStmt.executeUpdate();
                       lPStmt.close();
                       conn.commit();

                        if(vData.getInt("iCveProcedimiento")==2 || vData.getInt("iCveProcedimiento")==4){
                          lSQL = "insert into CYSSegProcedimiento(CYSSegProcedimiento.iEjercicio, " +
                              "CYSSegProcedimiento.iMovProcedimiento, " +
                              "CYSSegProcedimiento.iCveTitulo, " +
                              "CYSSegProcedimiento.iCveProcedimiento, " +
                              "CYSSegProcedimiento.cObsProcedimiento, " +
                              "CYSSegProcedimiento.iCveUsuario, " +
                              "CYSSegProcedimiento.iEjercicioAnt, " +
                              "CYSSegProcedimiento.iMovProcedimientoAnt, " +
                              "CYSSegProcedimiento.lActivo )" +
                              "select "+
                              Fechas.getIntYear(Fechas.getDateSQL(Fechas.getThisTime()))+
                              ", "+(iConsecutivo+1)+" ," +
                              "CYSSegProAnt.iCveTitulo, " +
                              "CYSSegProAnt.iCveProcedimiento + 1, " +
                              "CYSSegProAnt.cObsProcedimiento, " +
                              "CYSSegProAnt.iCveUsuario, " +
                              "CYSSegProAnt.iEjercicio, " +
                              "CYSSegProAnt.iMovProcedimiento,1 " +
                              "from CYSSegProcedimiento CYSSegProAnt " +
                              "where CYSSegProAnt.iEjercicio = ? " +
                              "and CYSSegProAnt.iMovProcedimiento = ? ";

                          lPStmt = conn.prepareStatement(lSQL);
                          lPStmt.setInt(1,vData.getInt("iEjercicio"));
                          lPStmt.setInt(2,iConsecutivo);
                          lPStmt.executeUpdate();
                          conn.commit();
                          lPStmt.close();

                        }

			lSQL = "update CYSSegProcedimiento " +
			"set lActivo=? " +
			"where iEjercicio=? " +
			"and iMovProcedimiento=? ";
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,0);
			lPStmt.setInt(2,vData.getInt("iEjercicio"));
			lPStmt.setInt(3,vData.getInt("iMovProcedimiento"));
		        lPStmt.executeUpdate();
                        conn.commit();
                        lPStmt.close();

			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			//ex.printStackTrace();
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

	public TVDinRep Terminar(TVDinRep vData,Connection cnNested) throws
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

			String lSQL = "update CYSSegProcedimiento " +
			"set lActivo=0 " +
			"where iEjercicio=? " +
			"and iMovProcedimiento=? ";

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iEjercicio"));
			lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
			int i = lPStmt.executeUpdate();

			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			//ex.printStackTrace();
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


//	/ Método que funciona para Requerimiento y Sanciones
	public Vector findByOficioNotRec(int iEjercicio,int iMovProcedimiento, int iTipoOficio) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer cSQL = new StringBuffer();

			cSQL.append("select CYSFolioProc.iEjercicio, ");
			cSQL.append("CYSFolioProc.iMovProcedimiento, ");
			cSQL.append("CYSFolioProc.iMovFolioProc, ");
			cSQL.append("CYSFolioProc.cDigitosFolio, ");
			cSQL.append("CYSFolioProc.iConsecutivo, ");
			cSQL.append("CYSFolioProc.iEjercicioFol, ");
			cSQL.append("GRLFolio.dtAsignacion, ");
			cSQL.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
			cSQL.append("Notif.iConsecutivo as NotiConsecutivo, ");
			cSQL.append("Notif.iEjercicioFol as NotiEjercicioFol, ");
			cSQL.append("FolioNotif.dtAsignacion as dtNotAsignacion, ");
			cSQL.append("Record.cDigitosFolio as ReccDigitosFolio, ");
			cSQL.append("Record.iConsecutivo as ReciConsecutivo, ");
			cSQL.append("Record.iEjercicioFol as ReciEjercicioFol, ");
			cSQL.append("FolioRecord.dtAsignacion as dtRecAsignacion, ");
			cSQL.append("ccn.TSFECHANOTIFICACION as tsFechaRealNotif, ");
			cSQL.append("CYSSegProcedimiento.ICVEPROCEDIMIENTO as iCveProcedimiento ");
			cSQL.append("from CYSSegProcedimiento ");
			cSQL.append("join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append(" and CYSFolioProc.LESRESOLUCION = " + (iTipoOficio == 1 ? 1 : 0) );
			cSQL.append(" and CYSFolioProc.LESPRORROGA = " + (iTipoOficio == 2 ? 1 : 0) );
			cSQL.append(" and CYSFolioProc.LESALEGATO = " + (iTipoOficio == 3 ? 1 : 0));
			cSQL.append(" and CYSFolioProc.LESSINEFECTO = " + (iTipoOficio == 4 ? 1 : 0));
			cSQL.append(" and CYSFolioProc.LESNOTIFICACION = " + (iTipoOficio == 5 ? 1 : 0));
			cSQL.append(" and CYSFolioProc.LESRECORDATORIO = " + (iTipoOficio == 6 ? 1 : 0));
			cSQL.append(" join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");
			cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");
			cSQL.append("left join CYSFolioProc Notif on Notif.iEjercicio = CYSNotOficio.iEjercicioNot ");
			cSQL.append("and Notif.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			cSQL.append("and Notif.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			cSQL.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicioFol ");
			cSQL.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
			cSQL.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
			cSQL.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
			cSQL.append("and FolioNotif.iConsecutivo = Notif.iConsecutivo ");
			cSQL.append("left join CYSFolioProc Record on Record.iEjercicio = CYSNotOficio.iEjercicioRec ");
			cSQL.append("and Record.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
			cSQL.append("and Record.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
			cSQL.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicioFol ");
			cSQL.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
			cSQL.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
			cSQL.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
			cSQL.append("and FolioRecord.iConsecutivo = Record.iConsecutivo ");
			cSQL.append("left join CYSCITANOTIFICACION ccn on CYSNotOficio.IMOVCITANOTIFICACION = ccn.IMOVCITANOTIFICACION ");
			cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
			cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");
			cSQL.append("and CYSFolioProc.iMovFolioProc = (select max(CYSFolioProc.iMovFolioProc)  ");
			cSQL.append("from CYSFolioProc  ");
			cSQL.append("where CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append(" and CYSFolioProc.LESRESOLUCION = " + (iTipoOficio == 1 ? 1 : 0) );
			cSQL.append(" and CYSFolioProc.LESPRORROGA = " + (iTipoOficio == 2 ? 1 : 0) );
			cSQL.append(" and CYSFolioProc.LESALEGATO = " + (iTipoOficio == 3 ? 1 : 0));
			cSQL.append(" and CYSFolioProc.LESSINEFECTO = " + (iTipoOficio == 4 ? 1 : 0));
			cSQL.append(" and CYSFolioProc.LESNOTIFICACION = " + (iTipoOficio == 5 ? 1 : 0));
			cSQL.append(" and CYSFolioProc.LESRECORDATORIO = " + (iTipoOficio == 6 ? 1 : 0) + ")");

			vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);
		} catch(Exception e){
			cError = e.toString();
		} finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	public Vector findByProAntOficioNotRec(int iEjercicio,int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer cSQL = new StringBuffer();

			cSQL.append("select CYSFolioProc.iEjercicio, ");
			cSQL.append("CYSFolioProc.iMovProcedimiento, ");
			cSQL.append("CYSFolioProc.iMovFolioProc, ");
			cSQL.append("CYSFolioProc.cDigitosFolio, ");
			cSQL.append("CYSFolioProc.iConsecutivo, ");
			cSQL.append("CYSFolioProc.iEjercicioFol, ");
			cSQL.append("GRLFolio.dtAsignacion, ");
			cSQL.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
			cSQL.append("Notif.iConsecutivo as NotiConsecutivo, ");
			cSQL.append("Notif.iEjercicioFol as NotiEjercicioFol, ");
			cSQL.append("FolioNotif.dtAsignacion as dtNorAsignacion, ");
			cSQL.append("Record.cDigitosFolio as ReccDigitosFolio, ");
			cSQL.append("Record.iConsecutivo as ReciConsecutivo, ");
			cSQL.append("Record.iEjercicioFol as ReciEjercicioFol, ");
			cSQL.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
			cSQL.append("from CYSSegProcedimiento ");
			cSQL.append("left join CYSSegProcedimiento ProcAnterior on ProcAnterior.iEjercicio = CYSSegProcedimiento.iEjercicioAnt ");
			cSQL.append("and ProcAnterior.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimientoAnt ");
			cSQL.append("join CYSFolioProc on CYSFolioProc.iEjercicio = ProcAnterior.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = ProcAnterior.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 0 ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 0 ");
			cSQL.append("and CYSFolioProc.lEsSinEfecto = 0 ");
			cSQL.append("and CYSFolioProc.lEsNotificacion = 0 ");
			cSQL.append("and CYSFolioProc.lEsRecordatorio = 0 ");
			cSQL.append("join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");
			cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");
			cSQL.append("left join CYSFolioProc Notif on Notif.iEjercicio = CYSNotOficio.iEjercicioNot ");
			cSQL.append("and Notif.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			cSQL.append("and Notif.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			cSQL.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicioFol ");
			cSQL.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
			cSQL.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
			cSQL.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
			cSQL.append("and FolioNotif.iConsecutivo = Notif.iConsecutivo ");
			cSQL.append("left join CYSFolioProc Record on Record.iEjercicio = CYSNotOficio.iEjercicioRec ");
			cSQL.append("and Record.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
			cSQL.append("and Record.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
			cSQL.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicioFol ");
			cSQL.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
			cSQL.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
			cSQL.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
			cSQL.append("and FolioRecord.iConsecutivo = Record.iConsecutivo ");
			cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
			cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");
			cSQL.append("and CYSFolioProc.iMovFolioProc = (select max(CYSFolioProc.iMovFolioProc)  ");
			cSQL.append("from CYSFolioProc  ");
			cSQL.append("where CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 0 ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 0 ");
			cSQL.append("and CYSFolioProc.lEsSinEfecto = 0 ");
			cSQL.append("and CYSFolioProc.lEsNotificacion = 0 ");
			cSQL.append("and CYSFolioProc.lEsRecordatorio = 0) ");

			vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);
		} catch(Exception e){
			cError = e.toString();
		} finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	public Vector findByProAntAntOficioNotRec(int iEjercicio,int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer cSQL = new StringBuffer();

			cSQL.append("select CYSFolioProc.iEjercicio, ");
			cSQL.append("CYSFolioProc.iMovProcedimiento, ");
			cSQL.append("CYSFolioProc.iMovFolioProc, ");
			cSQL.append("CYSFolioProc.cDigitosFolio, ");
			cSQL.append("CYSFolioProc.iConsecutivo, ");
			cSQL.append("CYSFolioProc.iEjercicioFol, ");
			cSQL.append("GRLFolio.dtAsignacion, ");
			cSQL.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
			cSQL.append("Notif.iConsecutivo as NotiConsecutivo, ");
			cSQL.append("Notif.iEjercicioFol as NotiEjercicioFol, ");
			cSQL.append("FolioNotif.dtAsignacion as dtNorAsignacion, ");
			cSQL.append("Record.cDigitosFolio as ReccDigitosFolio, ");
			cSQL.append("Record.iConsecutivo as ReciConsecutivo, ");
			cSQL.append("Record.iEjercicioFol as ReciEjercicioFol, ");
			cSQL.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
			cSQL.append("from CYSSegProcedimiento ");
			cSQL.append("left join CYSSegProcedimiento ProcAnterior on ProcAnterior.iEjercicio = CYSSegProcedimiento.iEjercicioAnt ");
			cSQL.append("and ProcAnterior.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimientoAnt ");

			cSQL.append("left join CYSSegProcedimiento ProcAntAnt on ProcAntAnt.iEjercicio = ProcAnterior.iEjercicioAnt ");
			cSQL.append("and ProcAntAnt.iMovProcedimiento = ProcAnterior.iMovProcedimientoAnt ");

			cSQL.append("join CYSFolioProc on CYSFolioProc.iEjercicio = ProcAntAnt.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = ProcAntAnt.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 0 ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 0 ");
			cSQL.append("and CYSFolioProc.lEsSinEfecto = 0 ");
			cSQL.append("and CYSFolioProc.lEsNotificacion = 0 ");
			cSQL.append("and CYSFolioProc.lEsRecordatorio = 0 ");
			cSQL.append("join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");
			cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");
			cSQL.append("left join CYSFolioProc Notif on Notif.iEjercicio = CYSNotOficio.iEjercicioNot ");
			cSQL.append("and Notif.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			cSQL.append("and Notif.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			cSQL.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicioFol ");
			cSQL.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
			cSQL.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
			cSQL.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
			cSQL.append("and FolioNotif.iConsecutivo = Notif.iConsecutivo ");
			cSQL.append("left join CYSFolioProc Record on Record.iEjercicio = CYSNotOficio.iEjercicioRec ");
			cSQL.append("and Record.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
			cSQL.append("and Record.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
			cSQL.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicioFol ");
			cSQL.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
			cSQL.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
			cSQL.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
			cSQL.append("and FolioRecord.iConsecutivo = Record.iConsecutivo ");
			cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
			cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");
			cSQL.append("and CYSFolioProc.iMovFolioProc = (select MIN(CYSFolioProc.iMovFolioProc)  ");
			cSQL.append("from CYSFolioProc  ");
			cSQL.append("where CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 0 ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 0 ");
			cSQL.append("and CYSFolioProc.lEsSinEfecto = 0 ");
			cSQL.append("and CYSFolioProc.lEsNotificacion = 0 ");
			cSQL.append("and CYSFolioProc.lEsRecordatorio = 0) ");

			vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);
		} catch(Exception e){
			cError = e.toString();
		} finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	public Vector findByProAntAntOficioResolSancion(int iEjercicio,int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer cSQL = new StringBuffer();

			cSQL.append("select CYSFolioProc.iEjercicio, ");
			cSQL.append("CYSFolioProc.iMovProcedimiento, ");
			cSQL.append("CYSFolioProc.iMovFolioProc, ");
			cSQL.append("CYSFolioProc.cDigitosFolio, ");
			cSQL.append("CYSFolioProc.iConsecutivo, ");
			cSQL.append("CYSFolioProc.iEjercicioFol, ");
			cSQL.append("GRLFolio.dtAsignacion, ");
			cSQL.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
			cSQL.append("Notif.iConsecutivo as NotiConsecutivo, ");
			cSQL.append("Notif.iEjercicioFol as NotiEjercicioFol, ");
			cSQL.append("FolioNotif.dtAsignacion as dtNorAsignacion, ");
			cSQL.append("Record.cDigitosFolio as ReccDigitosFolio, ");
			cSQL.append("Record.iConsecutivo as ReciConsecutivo, ");
			cSQL.append("Record.iEjercicioFol as ReciEjercicioFol, ");
			cSQL.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
			cSQL.append("from CYSSegProcedimiento ");
			cSQL.append("left join CYSSegProcedimiento ProcAnterior on ProcAnterior.iEjercicio = CYSSegProcedimiento.iEjercicioAnt ");
			cSQL.append("and ProcAnterior.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimientoAnt ");

			cSQL.append("left join CYSSegProcedimiento ProcAntAnt on ProcAntAnt.iEjercicio = ProcAnterior.iEjercicioAnt ");
			cSQL.append("and ProcAntAnt.iMovProcedimiento = ProcAnterior.iMovProcedimientoAnt ");

			cSQL.append("join CYSFolioProc on CYSFolioProc.iEjercicio = ProcAntAnt.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = ProcAntAnt.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 0 ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 0 ");
			cSQL.append("and CYSFolioProc.lEsSinEfecto = 0 ");
			cSQL.append("and CYSFolioProc.lEsNotificacion = 0 ");
			cSQL.append("and CYSFolioProc.lEsRecordatorio = 0 ");
			cSQL.append("and CYSFolioProc.lEsResolucion = 1 ");
			cSQL.append("join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");
			cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");
			cSQL.append("left join CYSFolioProc Notif on Notif.iEjercicio = CYSNotOficio.iEjercicioNot ");
			cSQL.append("and Notif.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			cSQL.append("and Notif.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			cSQL.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicioFol ");
			cSQL.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
			cSQL.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
			cSQL.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
			cSQL.append("and FolioNotif.iConsecutivo = Notif.iConsecutivo ");
			cSQL.append("left join CYSFolioProc Record on Record.iEjercicio = CYSNotOficio.iEjercicioRec ");
			cSQL.append("and Record.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
			cSQL.append("and Record.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
			cSQL.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicioFol ");
			cSQL.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
			cSQL.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
			cSQL.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
			cSQL.append("and FolioRecord.iConsecutivo = Record.iConsecutivo ");
			cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
			cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");

			vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);
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
	 * <p><b> insert into CYSSegProcedimiento(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
	 * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
	 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
	 * @throws DAOException       - Excepción de tipo DAO
	 * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
	 */
	public TVDinRep insertContrato(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		int iConsecutivo = this.findMax(vData.getInt("iEjercicio"));
		vData.put("iMovProcedimiento",iConsecutivo);
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}
			String lSQL = "insert into CYSSegProcedimiento(iEjercicio, " +
			"iMovProcedimiento, " +
			"iCveContrato, " +
			"dtSeguimiento, " +
			"cNumOficio, " +
			"iCveProcedimiento, " +
			"cObsProcedimiento, " +
			"iCveUsuario, " +
			"iEjercicioAnt, " +
			"iMovProcedimientoAnt, " +
			"lActivo ) " +
			"values (?,?,?,?,?,?,?,?,?,?,?)";

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iMovProcedimiento"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iEjercicio"));
			lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
			lPStmt.setInt(3,vData.getInt("iCveContrato"));
			lPStmt.setDate(4,vData.getDate("dtSeguimiento"));
			lPStmt.setString(5,vData.getString("cNumOficio"));
			lPStmt.setInt(6,vData.getInt("iCveProcedimiento"));
			lPStmt.setString(7,vData.getString("cObsProcedimiento"));
			lPStmt.setInt(8,vData.getInt("iCveUsuario"));
			lPStmt.setInt(9,vData.getInt("iEjercicioAnt"));
			lPStmt.setInt(10,vData.getInt("iMovProcedimientoAnt"));
			lPStmt.setInt(11,1);
			lPStmt.executeUpdate();
			if(cnNested == null)
				conn.commit();

		} catch(Exception ex){
			//ex.printStackTrace();
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
				if(lPStmt != null)
					lPStmt.close();

				if(cnNested == null){
					if(conn != null)
						conn.close();

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
	 * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
	 * <p><b> update CYSSegProcedimiento set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
	 * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
	 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
	 * @throws DAOException       - Excepción de tipo DAO
	 * @return TVDinRep           - Entidad Modificada.
	 */
	public TVDinRep updateContrato(TVDinRep vData,Connection cnNested) throws
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
			String lSQL = "update CYSSegProcedimiento " +
			"set iCveContrato=?, " +
			"dtSeguimiento=?, " +
			"cNumOficio=?, " +
			"iCveProcedimiento=?, " +
			"cObsProcedimiento=?, " +
			"iCveUsuario=?, " +
			"iEjercicioAnt=?, " +
			"iMovProcedimientoAnt=? " +
			"where iEjercicio=? " +
			"and iMovProcedimiento=? ";

			vData.addPK(vData.getString("iEjercicio"));
			vData.addPK(vData.getString("iMovProcedimiento"));

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iCveContrato"));
			lPStmt.setDate(2,vData.getDate("dtSeguimiento"));
			lPStmt.setString(3,vData.getString("cNumOficio"));
			lPStmt.setInt(4,vData.getInt("iCveProcedimiento"));
			lPStmt.setString(5,vData.getString("cObsProcedimiento"));
			lPStmt.setInt(6,vData.getInt("iCveUsuario"));
			lPStmt.setInt(7,vData.getInt("iEjercicioAnt"));
			lPStmt.setInt(8,vData.getInt("iMovProcedimientoAnt"));
			lPStmt.setInt(9,vData.getInt("iEjercicio"));
			lPStmt.setInt(10,vData.getInt("iMovProcedimiento"));
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
	/**
	 * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
	 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
	 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
	 * @throws DAOException       - Excepción de tipo DAO
	 * @return TVDinRep           - Entidad Modificada.
	 */
	public synchronized TVDinRep cambioContrato(TVDinRep vData,Connection cnNested) throws
	DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
                TFechas Fechas = new TFechas();
		boolean lSuccess = true;
		int iConsecutivo = this.findMax(vData.getInt("iEjercicio"));
//		vData.put("iMovProcedimiento",iConsecutivo);
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
                        }
                        String cActivo = (vData.getInt("iCveProcedimiento")==2 || vData.getInt("iCveProcedimiento")==4)?"0 ":"1 ";
			String lSQL = "insert into CYSSegProcedimiento(CYSSegProcedimiento.iEjercicio, " +
			"CYSSegProcedimiento.iMovProcedimiento, " +
			"CYSSegProcedimiento.iCveContrato, " +
			"CYSSegProcedimiento.iCveProcedimiento, " +
			"CYSSegProcedimiento.cObsProcedimiento, " +
			"CYSSegProcedimiento.iCveUsuario, " +
			"CYSSegProcedimiento.iEjercicioAnt, " +
			"CYSSegProcedimiento.iMovProcedimientoAnt, " +
			"CYSSegProcedimiento.lActivo )" +
			"select "+Fechas.getIntYear(Fechas.getDateSQL(Fechas.getThisTime()))+", " +
			iConsecutivo + ", " +
			"CYSSegProAnt.iCveContrato, " +
			"CYSSegProAnt.iCveProcedimiento + 1, " +
			"CYSSegProAnt.cObsProcedimiento, " +
			"CYSSegProAnt.iCveUsuario, " +
			"CYSSegProAnt.iEjercicio, " +
			"CYSSegProAnt.iMovProcedimiento, " + cActivo + " " +
			"from CYSSegProcedimiento CYSSegProAnt " +
			"where CYSSegProAnt.iEjercicio = ? " +
			"and CYSSegProAnt.iMovProcedimiento = ? ";


                        lPStmt = conn.prepareStatement(lSQL);
                        lPStmt.setInt(1,vData.getInt("iEjercicio"));
                        lPStmt.setInt(2,vData.getInt("iMovProcedimiento"));
                        int i = lPStmt.executeUpdate();
                        conn.commit();
                        lPStmt.close();

                        if(vData.getInt("iCveProcedimiento")==2 || vData.getInt("iCveProcedimiento")==4){
                          lSQL = "insert into CYSSegProcedimiento(CYSSegProcedimiento.iEjercicio, " +
                                 "CYSSegProcedimiento.iMovProcedimiento, " +
                                 "CYSSegProcedimiento.iCveContrato, " +
                                 "CYSSegProcedimiento.iCveProcedimiento, " +
                                 "CYSSegProcedimiento.cObsProcedimiento, " +
                                 "CYSSegProcedimiento.iCveUsuario, " +
                                 "CYSSegProcedimiento.iEjercicioAnt, " +
                                 "CYSSegProcedimiento.iMovProcedimientoAnt, " +
                                 "CYSSegProcedimiento.lActivo )" +
                                 "select "+Fechas.getIntYear(Fechas.getDateSQL(Fechas.getThisTime()))+
                                 ", "+(iConsecutivo+1)+" ," +
                                 "CYSSegProAnt.iCveContrato, " +
                                 "CYSSegProAnt.iCveProcedimiento + 1, " +
                                 "CYSSegProAnt.cObsProcedimiento, " +
                                 "CYSSegProAnt.iCveUsuario, " +
                                 "CYSSegProAnt.iEjercicio, " +
                                 "CYSSegProAnt.iMovProcedimiento,1 " +
                                 "from CYSSegProcedimiento CYSSegProAnt " +
                                 "where CYSSegProAnt.iEjercicio = ? " +
                                 "and CYSSegProAnt.iMovProcedimiento = ? ";

                          lPStmt = conn.prepareStatement(lSQL);
                          lPStmt.setInt(1,vData.getInt("iEjercicio"));
                          lPStmt.setInt(2,iConsecutivo);
                          lPStmt.executeUpdate();
                          conn.commit();
                          lPStmt.close();

                        }


			lSQL = "update CYSSegProcedimiento " +
			"set lActivo=? " +
			"where iEjercicio=? " +
			"and iMovProcedimiento=? ";

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,0);
			lPStmt.setInt(2,vData.getInt("iEjercicio"));
			lPStmt.setInt(3,vData.getInt("iMovProcedimiento"));
			lPStmt.executeUpdate();

			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			//ex.printStackTrace();
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

//	Método que funciona para la Resolución de Sanción y Revocación
	public Vector findByOfiRevNotRec(int iEjercicio,int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer cSQL = new StringBuffer();

			cSQL.append("select CYSFolioProc.iEjercicio, ");
			cSQL.append("CYSFolioProc.iMovProcedimiento, ");
			cSQL.append("CYSFolioProc.iMovFolioProc, ");
			cSQL.append("CYSFolioProc.cDigitosFolio, ");
			cSQL.append("CYSFolioProc.iConsecutivo, ");
			cSQL.append("CYSFolioProc.iEjercicioFol, ");
			cSQL.append("GRLFolio.dtAsignacion, ");
			cSQL.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
			cSQL.append("Notif.iConsecutivo as NotiConsecutivo, ");
			cSQL.append("Notif.iEjercicioFol as NotiEjercicioFol, ");
			cSQL.append("FolioNotif.dtAsignacion as dtNotAsignacion, ");
			cSQL.append("Record.cDigitosFolio as ReccDigitosFolio, ");
			cSQL.append("Record.iConsecutivo as ReciConsecutivo, ");
			cSQL.append("Record.iEjercicioFol as ReciEjercicioFol, ");
			cSQL.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
			cSQL.append("from CYSSegProcedimiento ");
			cSQL.append("join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 0 ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 0 ");
			cSQL.append("and CYSFolioProc.lEsSinEfecto = 0 ");
			cSQL.append("and CYSFolioProc.lEsNotificacion = 0 ");
			cSQL.append("and CYSFolioProc.lEsRecordatorio = 0 ");
			cSQL.append("and CYSFolioProc.lEsResolucion = 0 ");
			cSQL.append("join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");
			cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");
			cSQL.append("left join CYSFolioProc Notif on Notif.iEjercicio = CYSNotOficio.iEjercicioNot ");
			cSQL.append("and Notif.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			cSQL.append("and Notif.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			cSQL.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicioFol ");
			cSQL.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
			cSQL.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
			cSQL.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
			cSQL.append("and FolioNotif.iConsecutivo = Notif.iConsecutivo ");
			cSQL.append("left join CYSFolioProc Record on Record.iEjercicio = CYSNotOficio.iEjercicioRec ");
			cSQL.append("and Record.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
			cSQL.append("and Record.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
			cSQL.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicioFol ");
			cSQL.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
			cSQL.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
			cSQL.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
			cSQL.append("and FolioRecord.iConsecutivo = Record.iConsecutivo ");
			cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
			cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");
			cSQL.append("and CYSFolioProc.iMovFolioProc = (select min(CYSFolioProc.iMovFolioProc)  ");
			cSQL.append("from CYSFolioProc  ");
			cSQL.append("where CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 0 ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 0 ");
			cSQL.append("and CYSFolioProc.lEsSinEfecto = 0 ");
			cSQL.append("and CYSFolioProc.lEsNotificacion = 0 ");
			cSQL.append("and CYSFolioProc.lEsRecordatorio = 0 ");
			cSQL.append("and CYSFolioProc.lEsResolucion = 0) ");

			/*      cSQL.append("select CYSFolioProc.iEjercicio, ");
      cSQL.append("CYSFolioProc.iMovProcedimiento, ");
      cSQL.append("CYSFolioProc.iMovFolioProc, ");
      cSQL.append("CYSFolioProc.cDigitosFolio, ");
      cSQL.append("CYSFolioProc.iConsecutivo, ");
      cSQL.append("CYSFolioProc.iEjercicioFol, ");
      cSQL.append("GRLFolio.dtAsignacion, ");
      cSQL.append("FolProcNot.cDigitosFolio as NotcDigitosFolio, ");
      cSQL.append("FolProcNot.iConsecutivo as NotiConsecutivo, ");
      cSQL.append("FolProcNot.iEjercicioFol as NotiEjercicioFol, ");
      cSQL.append("FolNot.dtAsignacion as dtNorAsignacion, ");
      cSQL.append("FolProcRec.cDigitosFolio as ReccDigitosFolio, ");
      cSQL.append("FolProcRec.iConsecutivo as ReciConsecutivo, ");
      cSQL.append("FolProcRec.iEjercicioFol as ReciEjercicioFol, ");
      cSQL.append("FolRec.dtAsignacion as dtRecAsignacion ");
      cSQL.append("from CYSSegProcedimiento ");

      cSQL.append("left join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
      cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
      cSQL.append("and CYSFolioProc.lEsResolucion = 1 ");
      cSQL.append("left join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
      cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
      cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
      cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
      cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");

      cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
      cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
      cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");

      cSQL.append("left join CYSFolioProc FolProcNot on FolProcNot.iEjercicio = CYSNotOficio.iEjercicioNot ");
      cSQL.append("and FolProcNot.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
      cSQL.append("and FolProcNot.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
      cSQL.append("and FolProcNot.lEsNotificacion = 1 ");
      cSQL.append("left join GRLFolio FolNot on FolNot.iEjercicio = FolProcNot.iEjercicioFol ");
      cSQL.append("and FolNot.iCveOficina = FolProcNot.iCveOficina ");
      cSQL.append("and FolNot.iCveDepartamento = FolProcNot.iCveDepartamento ");
      cSQL.append("and FolNot.cDigitosFolio = FolProcNot.cDigitosFolio ");
      cSQL.append("and FolNot.iConsecutivo = FolProcNot.iConsecutivo ");

      cSQL.append("left join CYSFolioProc FolProcRec on FolProcRec.iEjercicio = CYSNotOficio.iEjercicioRec ");
      cSQL.append("and FolProcRec.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
      cSQL.append("and FolProcRec.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
      cSQL.append("and FolProcRec.lEsRecordatorio = 1 ");
      cSQL.append("left join GRLFolio FolRec on FolRec.iEjercicio = FolProcRec.iEjercicioFol ");
      cSQL.append("and FolRec.iCveOficina = FolProcRec.iCveOficina ");
      cSQL.append("and FolRec.iCveDepartamento = FolProcRec.iCveDepartamento ");
      cSQL.append("and FolRec.cDigitosFolio = FolProcRec.cDigitosFolio ");
      cSQL.append("and FolRec.iConsecutivo = FolProcRec.iConsecutivo ");

      cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
      cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");*/

			vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);
		} catch(Exception e){
			cError = e.toString();
		} finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

//	Método que funciona para obtener la prorroga, notificación y recordatorio
	public Vector findByProrrogaNotRec(int iEjercicio,int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer cSQL = new StringBuffer();

			cSQL.append("select CYSFolioProc.iEjercicio, ");
			cSQL.append("CYSFolioProc.iMovProcedimiento, ");
			cSQL.append("CYSFolioProc.iMovFolioProc, ");
			cSQL.append("CYSFolioProc.cDigitosFolio, ");
			cSQL.append("CYSFolioProc.iConsecutivo, ");
			cSQL.append("CYSFolioProc.iEjercicioFol, ");
			cSQL.append("GRLFolio.dtAsignacion, ");

			cSQL.append("CYSProrrogaAlegato.iDiasOtorgados, ");
			cSQL.append("CYSProrrogaAlegato.lSeConcede, ");
			cSQL.append("CYSProrrogaAlegato.cNumEscrito, ");
			cSQL.append("CYSProrrogaAlegato.dtEscrito, ");

			cSQL.append("FolProcNot.cDigitosFolio as NotcDigitosFolio, ");
			cSQL.append("FolProcNot.iConsecutivo as NotiConsecutivo, ");
			cSQL.append("FolProcNot.iEjercicioFol as NotiEjercicioFol, ");
			cSQL.append("FolNot.dtAsignacion as dtNorAsignacion, ");
			cSQL.append("FolProcRec.cDigitosFolio as ReccDigitosFolio, ");
			cSQL.append("FolProcRec.iConsecutivo as ReciConsecutivo, ");
			cSQL.append("FolProcRec.iEjercicioFol as ReciEjercicioFol, ");
			cSQL.append("FolRec.dtAsignacion as dtRecAsignacion ");
			cSQL.append("from CYSSegProcedimiento ");

			cSQL.append("left join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsProrroga = 1 ");
			cSQL.append("left join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");

			cSQL.append("left join CYSProrrogaAlegato on CYSProrrogaAlegato.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSProrrogaAlegato.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSProrrogaAlegato.iMovFolioProc = CYSFolioProc.iMovFolioProc ");

			cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");

			cSQL.append("left join CYSFolioProc FolProcNot on FolProcNot.iEjercicio = CYSNotOficio.iEjercicioNot ");
			cSQL.append("and FolProcNot.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			cSQL.append("and FolProcNot.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			cSQL.append("and FolProcNot.lEsNotificacion = 1 ");
			cSQL.append("left join GRLFolio FolNot on FolNot.iEjercicio = FolProcNot.iEjercicioFol ");
			cSQL.append("and FolNot.iCveOficina = FolProcNot.iCveOficina ");
			cSQL.append("and FolNot.iCveDepartamento = FolProcNot.iCveDepartamento ");
			cSQL.append("and FolNot.cDigitosFolio = FolProcNot.cDigitosFolio ");
			cSQL.append("and FolNot.iConsecutivo = FolProcNot.iConsecutivo ");

			cSQL.append("left join CYSFolioProc FolProcRec on FolProcRec.iEjercicio = CYSNotOficio.iEjercicioRec ");
			cSQL.append("and FolProcRec.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
			cSQL.append("and FolProcRec.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
			cSQL.append("and FolProcRec.lEsRecordatorio = 1 ");
			cSQL.append("left join GRLFolio FolRec on FolRec.iEjercicio = FolProcRec.iEjercicioFol ");
			cSQL.append("and FolRec.iCveOficina = FolProcRec.iCveOficina ");
			cSQL.append("and FolRec.iCveDepartamento = FolProcRec.iCveDepartamento ");
			cSQL.append("and FolRec.cDigitosFolio = FolProcRec.cDigitosFolio ");
			cSQL.append("and FolRec.iConsecutivo = FolProcRec.iConsecutivo ");

			cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
			cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");

			vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);


		} catch(Exception e){
			cError = e.toString();
		} finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}

	public Vector findByResolucionSancion(int iEjercicio, int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer sb = new StringBuffer() ;

			sb.append("select ");
			sb.append("GRLFolio.dtAsignacion, ");
			sb.append("FolNot.dtAsignacion as dtNotResolucionSan ");
			sb.append("from CYSSegProcedimiento ");
			sb.append("left join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			sb.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			sb.append("and CYSFolioProc.LESRESOLUCION = 1 ");
			sb.append("left join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			sb.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			sb.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			sb.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			sb.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");
			sb.append("left join CYSProrrogaAlegato on CYSProrrogaAlegato.iEjercicio = CYSFolioProc.iEjercicio ");
			sb.append("and CYSProrrogaAlegato.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			sb.append("and CYSProrrogaAlegato.iMovFolioProc = CYSFolioProc.iMovFolioProc ");
			sb.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			sb.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			sb.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");
			sb.append("left join CYSFolioProc FolProcNot on FolProcNot.iEjercicio = CYSNotOficio.iEjercicioNot ");
			sb.append("and FolProcNot.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			sb.append("and FolProcNot.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			sb.append("and FolProcNot.lEsNotificacion = 1 ");
			sb.append("left join GRLFolio FolNot on FolNot.iEjercicio = FolProcNot.iEjercicioFol ");
			sb.append("and FolNot.iCveOficina = FolProcNot.iCveOficina ");
			sb.append("and FolNot.iCveDepartamento = FolProcNot.iCveDepartamento ");
			sb.append("and FolNot.cDigitosFolio = FolProcNot.cDigitosFolio ");
			sb.append("and FolNot.iConsecutivo = FolProcNot.iConsecutivo ");
			sb.append("where CYSSegProcedimiento.iEjercicio =  " + iEjercicio);
			sb.append(" and CYSSegProcedimiento.iMovProcedimiento =  " + iMovProcedimiento);
			sb.append(" order by CYSFolioProc.iMovFolioProc");

			vcRecords = this.FindByGeneric("",sb.toString(),dataSourceName);

		}catch(Exception ex){
			cError = ex.toString();
		}finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}
/**
 *
 * @param iEjercicio
 * @param iMovProcedimiento
 * @return Vector
 * @throws DAOException
 */
	public Vector findByDerechosFiscales(int iEjercicio, int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer sb = new StringBuffer() ;

			sb.append("select ");
			sb.append("GRLFolio.dtAsignacion as dtOficioSHCP, ");
			sb.append("GRLFolio.CDIGITOSFOLIO  as cDigitosFolio, ");
			sb.append("GRLFOLIO.ICONSECUTIVO as iConsecutivo, ");
			sb.append("GRLFOLIO.IEJERCICIO as iEjercicioFolio ");
			sb.append("from CYSSegProcedimiento ");
			sb.append("left join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			sb.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			sb.append("and CYSFolioProc.LESRESOLUCION = 1 ");
			sb.append("and CYSFolioProc.LESPRORROGA = 1 ");
			sb.append("and CYSFolioProc.LESALEGATO = 1 ");
			sb.append("and CYSFolioProc.LESSINEFECTO = 1 ");
			sb.append("and CYSFolioProc.LESNOTIFICACION = 1 ");
			sb.append("and CYSFolioProc.LESRECORDATORIO = 1 ");
			sb.append("left join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			sb.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			sb.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			sb.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			sb.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");
			sb.append("where CYSSegProcedimiento.iEjercicio =  " + iEjercicio);
			sb.append(" and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento);
			sb.append(" order by CYSFolioProc.iMovFolioProc ");

			vcRecords = this.FindByGeneric("",sb.toString(),dataSourceName);

		}catch(Exception ex){
			cError = ex.toString();
		}finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}


//	Método que funciona para los alegatos, notificaciones y recordatorio
	public Vector findByAlegatoNotRec(int iEjercicio,int iMovProcedimiento) throws DAOException{
		Vector vcRecords = new Vector();
		cError = "";
		try{
			StringBuffer cSQL = new StringBuffer();

			cSQL.append("select CYSFolioProc.iEjercicio, ");
			cSQL.append("CYSFolioProc.iMovProcedimiento, ");
			cSQL.append("CYSFolioProc.iMovFolioProc, ");
			cSQL.append("CYSFolioProc.cDigitosFolio, ");
			cSQL.append("CYSFolioProc.iConsecutivo, ");
			cSQL.append("CYSFolioProc.iEjercicioFol, ");
			cSQL.append("GRLFolio.dtAsignacion, ");

			cSQL.append("CYSProrrogaAlegato.iDiasOtorgados, ");
			cSQL.append("CYSProrrogaAlegato.lSeConcede, ");
			cSQL.append("CYSProrrogaAlegato.cNumEscrito, ");
			cSQL.append("CYSProrrogaAlegato.dtEscrito, ");

			cSQL.append("FolProcNot.cDigitosFolio as NotcDigitosFolio, ");
			cSQL.append("FolProcNot.iConsecutivo as NotiConsecutivo, ");
			cSQL.append("FolProcNot.iEjercicioFol as NotiEjercicioFol, ");
			cSQL.append("FolNot.dtAsignacion as dtNorAsignacion, ");
			cSQL.append("FolProcRec.cDigitosFolio as ReccDigitosFolio, ");
			cSQL.append("FolProcRec.iConsecutivo as ReciConsecutivo, ");
			cSQL.append("FolProcRec.iEjercicioFol as ReciEjercicioFol, ");
			cSQL.append("FolRec.dtAsignacion as dtRecAsignacion ");
			cSQL.append("from CYSSegProcedimiento ");

			cSQL.append("left join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio ");
			cSQL.append("and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento ");
			cSQL.append("and CYSFolioProc.lEsAlegato = 1 ");
			cSQL.append("left join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol ");
			cSQL.append("and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina ");
			cSQL.append("and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento ");
			cSQL.append("and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio ");
			cSQL.append("and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo ");

			cSQL.append("left join CYSProrrogaAlegato on CYSProrrogaAlegato.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSProrrogaAlegato.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSProrrogaAlegato.iMovFolioProc = CYSFolioProc.iMovFolioProc ");

			cSQL.append("left join CYSNotOficio on CYSNotOficio.iEjercicio = CYSFolioProc.iEjercicio ");
			cSQL.append("and CYSNotOficio.iMovProcedimiento = CYSFolioProc.iMovProcedimiento ");
			cSQL.append("and CYSNotOficio.iMovFolioProc = CYSFolioProc.iMovFolioProc ");

			cSQL.append("left join CYSFolioProc FolProcNot on FolProcNot.iEjercicio = CYSNotOficio.iEjercicioNot ");
			cSQL.append("and FolProcNot.iMovProcedimiento = CYSNotOficio.iMovProcedimientoNot ");
			cSQL.append("and FolProcNot.iMovFolioProc = CYSNotOficio.iMovFolioProcNot ");
			cSQL.append("and FolProcNot.lEsNotificacion = 1 ");
			cSQL.append("left join GRLFolio FolNot on FolNot.iEjercicio = FolProcNot.iEjercicioFol ");
			cSQL.append("and FolNot.iCveOficina = FolProcNot.iCveOficina ");
			cSQL.append("and FolNot.iCveDepartamento = FolProcNot.iCveDepartamento ");
			cSQL.append("and FolNot.cDigitosFolio = FolProcNot.cDigitosFolio ");
			cSQL.append("and FolNot.iConsecutivo = FolProcNot.iConsecutivo ");

			cSQL.append("left join CYSFolioProc FolProcRec on FolProcRec.iEjercicio = CYSNotOficio.iEjercicioRec ");
			cSQL.append("and FolProcRec.iMovProcedimiento = CYSNotOficio.iMovProcedimientoRec ");
			cSQL.append("and FolProcRec.iMovFolioProc = CYSNotOficio.iMovFolioProcRec ");
			cSQL.append("and FolProcRec.lEsRecordatorio = 1 ");
			cSQL.append("left join GRLFolio FolRec on FolRec.iEjercicio = FolProcRec.iEjercicioFol ");
			cSQL.append("and FolRec.iCveOficina = FolProcRec.iCveOficina ");
			cSQL.append("and FolRec.iCveDepartamento = FolProcRec.iCveDepartamento ");
			cSQL.append("and FolRec.cDigitosFolio = FolProcRec.cDigitosFolio ");
			cSQL.append("and FolRec.iConsecutivo = FolProcRec.iConsecutivo ");

			cSQL.append("where CYSSegProcedimiento.iEjercicio = " + iEjercicio + " ");
			cSQL.append("and CYSSegProcedimiento.iMovProcedimiento = " + iMovProcedimiento + " ");

			vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);
		} catch(Exception e){
			cError = e.toString();
		} finally{
			if(!cError.equals(""))
				throw new DAOException(cError);
			return vcRecords;
		}
	}
        public boolean comparaFechas(java.sql.Date Fecha1, java.sql.Date Fecha2){
          String cFecha1 = ("" + Fecha1).substring(0,4)+("" + Fecha1).substring(5,7)+("" + Fecha1).substring(8,10);
          String cFecha2 = ("" + Fecha2).substring(0,4)+("" + Fecha2).substring(5,7)+("" + Fecha2).substring(8,10);
          double dFecha1 = Double.parseDouble(cFecha1);
          double dFecha2 = Double.parseDouble(cFecha2);
          if(dFecha1>dFecha2) return true;
          else return false;
        }
        public int getDaysBetweenDatesHabiles(java.sql.Date dtInicial, java.sql.Date dtFinal){
          int iNumDias = 0;
          TDGRLDiaNoHabil dh = new TDGRLDiaNoHabil();
          while(dtInicial.compareTo(dtFinal) < 0){
            dtInicial = dh.getFechaFinal(dtInicial,1,false);
            iNumDias++;
          }
          return iNumDias;
        }

}


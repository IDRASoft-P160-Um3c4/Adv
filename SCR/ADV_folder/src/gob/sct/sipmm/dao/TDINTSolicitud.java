package gob.sct.sipmm.dao;

import gob.sct.sipmm.dao.ws.TDCis;

import java.sql.*;
import java.util.*;
import java.util.Date;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;

/**
 * <p>
 * Title: TDCCAModalidad.java
 * </p>
 * <p>
 * Description: DAO de la entidad CCAModalidad
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Tecnologï¿½a InRed S.A. de C.V.
 * </p>
 * 
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDINTSolicitud extends DAOBase {
    private TParametro VParametros = new TParametro("44");
    private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
    //private String tramiteDGAF = VParametros.getPropEspecifica("tramiteDGAF");
    //private String etapaCIS = VParametros.getPropEspecifica("etapaCISTramxInt");
    //private String areaCIS = VParametros.getPropEspecifica("areaCIS");
    private String RFCRepresentante = VParametros.getPropEspecifica("RFCRepLegal");

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
     *             - Excepciï¿½n de tipo DAO
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
        }
        return vcRecords;
    }

    /**
     * Inserta el registro dado por la entidad vData.
     * <p>
     * <b> insert into
     * CCAModalidad(iCveModalidad,cDscModalidad,iUltimoFolio,lActivo) values
     * (?,?,?,?) </b>
     * </p>
     * <p>
     * <b> Campos Llave: iCveModalidad, </b>
     * </p>
     * 
     * @param vData
     *            TVDinRep - VO Dinï¿½mico que contiene a la entidad a Insertar.
     * @param cnNested
     *            Connection - Conexiï¿½n anidada que permite que el mï¿½todo se
     *            encuentre dentro de una transacciï¿½n mayor.
     * @throws DAOException
     *             - Excepciï¿½n de tipo DAO
     * @return TVDinRep - VO Dinï¿½mico que contiene a la entidad a Insertada, asï¿½
     *         como a la llave de esta entidad.
     */
    public TVDinRep insert(TVDinRep vData, Connection cnNested)
            throws DAOException {
        DbConnection dbConn = null;
        Connection conn = cnNested;
        PreparedStatement lPStmt = null;
        boolean lSuccess = true;
        TDCis dCis = new TDCis();
        TDTRARegReqXTram dReq = new TDTRARegReqXTram();
        TDTRARegEtapasXModTram dEtapas = new TDTRARegEtapasXModTram();
        TVDinRep vEtapas = new TVDinRep();
        TFechas fecha = new TFechas();

        int tramite  = 0;
        int etapa = 0; // Inicio de trï¿½mite por ventanilla
        int area  = 0; //DGAF
        
        String cObservacion = "";
        String RFCRepLegal = "";
            
        try {
            if (cnNested == null) {
                dbConn = new DbConnection(dataSourceName);
                conn = dbConn.getConnection();
                conn.setAutoCommit(false);
                conn.setTransactionIsolation(2);
            }
            String lSQL = "INSERT INTO INTTRAMITEDETALLE(iEjercicio,iNumSolicitud,IORDEN,cCampo,cValor1,cValor2) values (?,?,?,?,?,?)";

            // ...
            lPStmt = conn.prepareStatement(lSQL);
            StringTokenizer stSol = new StringTokenizer(vData.getString("cCamSol"), "^");
            
            String cSolicitud = "SELECT IEJERCICIO,INUMSOLICITUD,S.ICVETRAMITE,S.ICVEMODALIDAD,CT.ICVETRAMITECIS FROM TRAREGSOLICITUD S " +
                                "JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=S.ICVETRAMITE AND CT.ICVEMODALIDAD=S.ICVEMODALIDAD " +
                                "WHERE S.IIDCITA="+vData.getInt("iNumCita");
            Vector vcSolicitud = this.findByNessted("", cSolicitud, conn);
            int iEjercicio = 0;
            int iNumSolicitud = 0;
            
            if(vcSolicitud.size()>0){ 
            	TVDinRep vSolicitud  = (TVDinRep) vcSolicitud.get(0);
            	iEjercicio = vSolicitud.getInt("iEjercicio");
            	iNumSolicitud = vSolicitud.getInt("iNumSolicitud");
            	tramite = vSolicitud.getInt("ICVETRAMITECIS");
            	
            	vSolicitud.put("cRequisitos", vData.getString("cRequisitos"));
            	dReq.insertInt(vSolicitud, conn);
            	
            	String cArea = "SELECT ICVEOFICINARESUELVE,ICVEDEPTORESUELVE FROM TRATRAMITEXOFIC " +
            			       "where icveoficina=  and ICVETRAMITE="+vSolicitud.getInt("iCveTramite");
            	cArea = "SELECT ICVEOFICINARESUELVE,ICVEDEPTORESUELVE FROM TRAREGSOLICITUD s " +
            	        "join TRATRAMITEXOFIC txo on txo.ICVETRAMITE=s.ICVETRAMITE and txo.ICVEOFICINA=s.ICVEOFICINA " +
            	        "where s.IEJERCICIO="+vSolicitud.getInt("iEjercicio")+
            	        " and  s.INUMSOLICITUD="+vSolicitud.getInt("iNumSolicitud");
            	
            	
            	cArea = "SELECT ICVEOFICINA,ICVEDEPARTAMENTO FROM TRAREGSOLICITUD WHERE IEJERCICIO ="+vSolicitud.getInt("iEjercicio")+
            			" AND INUMSOLICITUD="+vSolicitud.getInt("iNumSolicitud");

            	
            	Vector vcArea = this.findByNessted("",cArea,conn);
            	
            	

            	if(vcArea.size()>0){
            		TVDinRep vArea = (TVDinRep) vcArea.get(0); 
	                vEtapas.put("iEjercicio",vSolicitud.getInt("iEjercicio"));
	                vEtapas.put("iNumSolicitud",vSolicitud.getInt("iNumSolicitud"));
	                vEtapas.put("iCveTramite",vSolicitud.getInt("iCveTramite"));
	                vEtapas.put("iCveModalidad",vSolicitud.getInt("iCveModalidad"));
	                
	                vEtapas.put("iCveOficina",vArea.getInt("ICVEOFICINA"));
	                vEtapas.put("iCveDepartamento",vArea.getInt("ICVEDEPARTAMENTO"));
	                
	                vEtapas.put("iCveUsuario",0);
	                vEtapas.put("tsRegistro", fecha.getThisTime());
	                vEtapas.put("iCveEtapa",1);
	                vEtapas.put("iOrden",1);
	                dEtapas.insertEtapa(vEtapas,conn, false);
	                
	                TVDinRep vDatosEtapa = new TVDinRep();
	                vDatosEtapa.put("iEjercicio", vSolicitud.getInt("iEjercicio"));
	                vDatosEtapa.put("iNumSolicitud",vSolicitud.getInt("iNumSolicitud"));
	                vDatosEtapa.put("iCveTram", vSolicitud.getInt("iCveTramite"));
	                vDatosEtapa.put("iCveMod", vSolicitud.getInt("iCveModalidad"));
	                vDatosEtapa.put("iCveEtapa", VParametros.getPropEspecifica("EtapaVisita"));
	                vDatosEtapa.put("iCveOfic",vArea.getInt("ICVEOFICINA"));
	                vDatosEtapa.put("iCveDpto", vArea.getInt("ICVEDEPARTAMENTO"));
	                vDatosEtapa.put("iCveUsuario", 0);
	                vDatosEtapa.put("lAnexo", 0);
	                vDatosEtapa.put("Observacion", "");
	                
	                dEtapas.cambiaEtapaADV(vDatosEtapa, conn);
	                
            	}
            	
            	//this.updateFechaEntrega(vSolicitud.getInt("iEjercicio"),vSolicitud.getInt("iNumSolicitud"),null);
            	
            }
            
            while (stSol.hasMoreTokens() == true) {
                String cCampo = stSol.nextToken();
                StringTokenizer stCam = new StringTokenizer(cCampo, "=");
                String cC1 = stCam.nextToken().trim();
                String cC2 = stCam.nextToken().trim();
                String cC3 = stCam.nextToken().trim();
                String cC4 = stCam.nextToken().trim();
                
                if(cC2.length() > 0){
                    lPStmt.setInt(1, iEjercicio);
                    lPStmt.setInt(2, iNumSolicitud);
                    lPStmt.setInt(3, Integer.parseInt(cC4));
                    lPStmt.setString(4, cC1);
                    lPStmt.setString(5, cC2);
                    lPStmt.setString(6, cC3);

                    lPStmt.executeUpdate();
                    if(cC1.equalsIgnoreCase(RFCRepresentante)) {
                        RFCRepLegal = cC2;
                    }
                }else{
                }
            }
            
            
            RFCRepLegal = vData.getString("cRFCRep");
            cObservacion =  "RFC ";
            cObservacion += RFCRepLegal;
            cObservacion += " Clave del Tramite ";
            cObservacion += vData.getInt("iNumCita");
            cObservacion += "-" ;
            cObservacion += vData.getInt("ICONSECUTIVO");
            
            dEtapas.upDateEstadoInformativoCISADV(iEjercicio, iNumSolicitud, Integer.parseInt(VParametros.getPropEspecifica("EtapaRegInt")),cObservacion,conn);
            
            Vector vcFec = this.findByCustom("", "SELECT DTVISITA FROM TRAREGDATOSADVXSOL WHERE IEJERCICIO="+iEjercicio+" and INUMSOLICITUD="+iNumSolicitud);
            
            TVDinRep vFec= (TVDinRep) vcFec.get(0);
            
            String fVT= vFec.getDate("DTVISITA").toString();
            
            String comentario="Se realizará una visita técnica en el lugar donde pretende realizar la obra el día "+ fVT +".";
            
            //Thread.sleep(1000);
            
            dEtapas.upDateEstadoInformativoCISADV(iEjercicio, iNumSolicitud, Integer.parseInt(VParametros.getPropEspecifica("EtapaVTecInt")),comentario,conn);
            //
            
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
        }
        return vData;
    }

       public TVDinRep insertOneRecord(TVDinRep vData, Connection cnNested)
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
       String lSQL = "INSERT INTO INTTRAMITEDETALLE(iEjercicio,iNumSolicitud,IORDEN,cCampo,cValor1,cValor2) values (?,?,?,?,?,?)";

       // ...
       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.setInt(1, vData.getInt("iEjercicio"));
       lPStmt.setInt(2, vData.getInt("iNumSolicitud"));
       lPStmt.setInt(3, vData.getInt("IORDEN"));
       lPStmt.setString(4, vData.getString("CCAMPO"));
       lPStmt.setString(5, vData.getString("cValor1"));
       lPStmt.setString(6, vData.getString("cValor2"));
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
   }
   return vData;
}

    
    /**
     * Inserta el registro dado por la entidad vData.
     * <p>
     * <b> insert into
     * CCAModalidad(iCveModalidad,cDscModalidad,iUltimoFolio,lActivo) values
     * (?,?,?,?) </b>
     * </p>
     * <p>
     * <b> Campos Llave: iCveModalidad, </b>
     * </p>
     * 
     * @param vData
     *            TVDinRep - VO Dinï¿½mico que contiene a la entidad a Insertar.
     * @param cnNested
     *            Connection - Conexiï¿½n anidada que permite que el mï¿½todo se
     *            encuentre dentro de una transacciï¿½n mayor.
     * @throws DAOException
     *             - Excepciï¿½n de tipo DAO
     * @return TVDinRep - VO Dinï¿½mico que contiene a la entidad a Insertada, asï¿½
     *         como a la llave de esta entidad.
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

            String lSQL = "Select ICVETRAMITE,ICONSECUTIVO,IORDEN from INTTRAMITEDETALLE where ICVETRAMITE = " + 
            vData.getInt("ICVETRAMITE") + " AND ICONSECUTIVO = " + vData.getInt("ICONSECUTIVO");

            Vector vcTTram = findByCustom("", lSQL);

            if (vcTTram.size() > 0) {
                delete(vData, conn);
                insert(vData, conn);
            }
        } catch (Exception ex) {
            warn("update", ex);
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
        }
        return vData;
    }

    /**
     * Elimina al registro a travï¿½s de la entidad dada por vData.
     * <p>
     * <b> delete from CCAModalidad where iCveModalidad = ? </b>
     * </p>
     * <p>
     * <b> Campos Llave: iCveModalidad, </b>
     * </p>
     * 
     * @param vData
     *            TVDinRep - VO Dinï¿½mico que contiene a la entidad a Insertar.
     * @param cnNested
     *            Connection - Conexiï¿½n anidada que permite que el mï¿½todo se
     *            encuentre dentro de una transacciï¿½n mayor.
     * @throws DAOException
     *             - Excepciï¿½n de tipo DAO
     * @return boolean - En caso de ser o no eliminado el registro.
     */
    public boolean delete(TVDinRep vData, Connection cnNested) {
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

            // Ajustar Where de acuerdo a requerimientos...
            String lSQL = "delete from INTTRAMITEDETALLE where ICVETRAMITE = ? AND ICONSECUTIVO = ?";
            // ...

            lPStmt = conn.prepareStatement(lSQL);

            lPStmt.setInt(1, vData.getInt("ICVETRAMITE"));
            lPStmt.setInt(2, vData.getInt("ICONSECUTIVO"));
            lPStmt.executeUpdate();
            if (cnNested == null) {
                conn.commit();
            }
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
        }
        return lSuccess;
    }
    
	public TVDinRep insertDocto(TVDinRep vData, Connection cnNested)
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
	        conn.setTransactionIsolation(2);
	    }
	    String lSQL = "INSERT INTO INTTRAMITEDOCS (ICVEDOCDIG,TSREGISTRO,CDOCUMENTO,CNOMARCHIVO,CCAMPO,IEJERCICIO,INUMSOLICITUD,ICVEREQUISITO) VALUES (?,?,?,?,?,?,?,?)";
	
	// ...
	lPStmt = conn.prepareStatement(lSQL);
			 
		lPStmt.setInt(1,vData.getInt("IIDGESTORDOCUMENTO"));
		lPStmt.setTimestamp(2, fecha.getThisTime());
		lPStmt.setString(3, (vData.getString("CDOCUMENTO").length()>=20?vData.getString("CDOCUMENTO").substring(0,19):vData.getString("CDOCUMENTO")));
		lPStmt.setString(4, vData.getString("CNOMARCHIVO").length()>=100?vData.getString("CNOMARCHIVO").substring(0,99):vData.getString("CNOMARCHIVO"));
		lPStmt.setString(5, vData.getString("CCAMPO"));
		lPStmt.setInt(6, vData.getInt("iEjercicio"));
		lPStmt.setInt(7, vData.getInt("iNumSolicitud"));
				
		lPStmt.setInt(8, vData.getInt("ICVEREQUISITO"));

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
	}
	return vData;
	}
	
	public TVDinRep insertDoctoADV(TVDinRep vData,boolean isRequisito, Connection cnNested)
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
		        conn.setTransactionIsolation(2);
		    }
		    
		    String lSQL = "INSERT INTO INTTRAMITEDOCS (ICVEDOCDIG,TSREGISTRO,CDOCUMENTO,CNOMARCHIVO,IEJERCICIO,INUMSOLICITUD,ICVEREQUISITO,ICVEOFICIO,COBSERVACION,ICVEESTATUS) " +
		    			  "VALUES (?,current_timestamp,?,?,?,?,?,?,?,?)"; 
		
		    
		    lPStmt = conn.prepareStatement(lSQL);
		    
			lPStmt.setString(1,vData.getString("ICVEDOCDIG").trim());
			lPStmt.setString(2, (vData.getString("CDOCUMENTO").length()>=20?vData.getString("CDOCUMENTO").substring(0,19):vData.getString("CDOCUMENTO")));
			lPStmt.setString(3, vData.getString("CNOMARCHIVO").length()>=100?vData.getString("CNOMARCHIVO").substring(0,99):vData.getString("CNOMARCHIVO"));
			lPStmt.setInt(4, vData.getInt("iEjercicio"));
			lPStmt.setInt(5, vData.getInt("iNumSolicitud"));
			
			
			if(isRequisito==true){				
				lPStmt.setInt(6, vData.getInt("ICVEREQUISITO"));
				lPStmt.setNull(7, Types.INTEGER);
			}else{
				lPStmt.setNull(6, Types.INTEGER);
				lPStmt.setInt(7, vData.getInt("ICVEOFICIO"));
			}
		
			lPStmt.setString(8, vData.getString("COBSERVACION"));
			
			if(vData.getInt("tienePNC")>0){
				lPStmt.setInt(9, 38);
			}
			else{
				lPStmt.setNull(9, Types.INTEGER);
			}
			
			lPStmt.executeUpdate();
		
		    if (cnNested == null) {
		        conn.commit(); 
		    }
		} catch (Exception ex) {
			
		    warn("insert", ex);
		    ex.printStackTrace();
		    
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
		}
		return vData;
		}
	
	
	public TVDinRep insertDoctoHistoricoADV(TVDinRep vData, Connection cnNested)
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
		        conn.setTransactionIsolation(2);
		    }
		    
	
		    String lSQL = "INSERT INTO TRADOCHIST (ICVEDOCHIST,DTREGISTRO,ICVEUSUARIO,CNOMARCH,ICVEHISTADV) VALUES (?,CURRENT_DATE,?,?,?)";
		    
		    lPStmt = conn.prepareStatement(lSQL);
		    
		    System.out.print(vData.getInt("ICVEDOCHIST"));
		    System.out.print(vData.getInt("ICVEUSUARIO"));
		    System.out.print(vData.getString("fName"));
		    System.out.print(vData.getInt("ICVEHISTADV"));
		    
		    
			lPStmt.setInt(1,vData.getInt("ICVEDOCHIST"));
			lPStmt.setInt(2,vData.getInt("ICVEUSUARIO"));
			lPStmt.setString(3,vData.getString("fName"));
			lPStmt.setInt(4,vData.getInt("ICVEHISTADV"));
			
		    lPStmt.executeUpdate();
		
		    if (cnNested == null) {
		        conn.commit(); 
		    }
		} catch (Exception ex) {
			
		    warn("insert", ex);
		    ex.printStackTrace();
		    
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
		}
		return vData;
		}

	
	
	public void updateFechaEntrega(int iEjercicio, int iNumSolicitud, Connection cnNested) throws DAOException{
		TDTRARegSolicitud solicitud = new TDTRARegSolicitud ();
        DbConnection dbConn = null;
        Connection conn = cnNested;
        PreparedStatement lPStmt = null;
        boolean lSuccess = true;
        java.sql.Date fecha = null;
		try{
			fecha = solicitud.getFechaCompromiso(iEjercicio, iNumSolicitud, null);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	    try{
	    	if(cnNested == null){
	           dbConn = new DbConnection(dataSourceName);
	           conn = dbConn.getConnection();
	           conn.setAutoCommit(false);
	           conn.setTransactionIsolation(2);
	         }
	         String lSQL = "update TRARegSolicitud set DTESTIMADAENTREGA=? where iEjercicio = ? AND iNumSolicitud = ? ";
	         lPStmt = conn.prepareStatement(lSQL);

	         lPStmt.setDate(1,fecha);
	         lPStmt.setInt (2,iEjercicio);
	         lPStmt.setInt (3,iNumSolicitud);
	         lPStmt.executeUpdate();


	         if(cnNested == null)
	           conn.commit();

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
	    } 
	    finally{
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
	    }
	}
	public void updateFechaIntegra(int iEjercicio, int iNumSolicitud,int iCveDepartamento,int iCveUsuario, Connection cnNested) throws DAOException{
		TDTRARegSolicitud solicitud = new TDTRARegSolicitud ();
        DbConnection dbConn = null;
        Connection conn = cnNested;
        PreparedStatement lPStmt = null;
        boolean lSuccess = true;
        TFechas fecha = new TFechas();
	    try{
	    	if(cnNested == null){
	           dbConn = new DbConnection(dataSourceName);
	           conn = dbConn.getConnection();
	           conn.setAutoCommit(false);
	           conn.setTransactionIsolation(2);
	         }
	         String lSQL = "update INTTRAMITES set tsIntegra=?,iCveDeptoIntegra=?,iCveUsuIntegra=? where iEjercicio = ? AND iNumSolicitud = ? ";
	         lPStmt = conn.prepareStatement(lSQL);

	         lPStmt.setTimestamp(1,fecha.getThisTime());
	         lPStmt.setInt (2,iCveDepartamento);
	         lPStmt.setInt (3,iCveUsuario);
	         lPStmt.setInt (4,iEjercicio);
	         lPStmt.setInt (5,iNumSolicitud);
	         lPStmt.executeUpdate();


	         if(cnNested == null)
	           conn.commit();

	    } catch(Exception ex){
	    	ex.printStackTrace();
	         warn("update",ex);
	         if(cnNested == null){
	           try{
	             conn.rollback();
	           } catch(Exception e){
	             fatal("update.rollback",e);
	           }
	         }
	         lSuccess = false;
	    } 
	    finally{
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
	    }
	}
}

package gob.sct.sipmm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import com.micper.excepciones.DAOException;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.DAOBase;
import com.micper.sql.DbConnection;
import com.micper.util.TFechas;
import com.micper.sql.TablaSecuenciaXAnio; 

import cisws.generated.*;

public class TDINTTRAMITES 
    extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros
      .getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas("44");
  

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
    }
    return vcRecords;
  }

  public TVDinRep finaliza(TVDinRep vData,Connection cnNested) throws
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
      String lSQL =
          "UPDATE INTTRAMITES SET TSFIN = {FN NOW()} WHERE ICVETRAMITE = ?";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("ICVETRAMITE"));

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
    }
    return vData;
  }

  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    int ICVETRAMITE = 0;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into INTTRAMITES "
          + "(ICVETRAMITE, ICONSECUTIVO, ICVETIPOPERMISIONA, ICVETIPOTRAMITE, TSREGISTRO, ICVEDEPARTAMENTO) "
          + "values (?,?,?,?,{FN NOW()},?)";

      // Si el tr�mite no existe, solicitar al CIS un n�mero de tr�mite
      if(vData.getInt("ICVETRAMITE") <= 0){

        String wsdlUrl = VParametros.getPropEspecifica("WSCISURI") +
            "InsertaCita?WSDL";
        WSInsertaCita service = new WSInsertaCita_Impl(wsdlUrl);
        WSInsertaCitaPort port = service.getWSInsertaCitaPort();

        ICVETRAMITE = port.insertaCita(VParametros.getPropEspecifica("WSCISUSR"),
                                       VParametros.getPropEspecifica("WSCISPWD"),
                                       vData.getInt("ICVETRAMITECIS"),
                                       0,
                                       1,
                                       vData.getString("CNOMBRECIS"),
                                       vData.getInt("ICVEAREACIS"));

        if(ICVETRAMITE == 0){
          lSuccess = false;
          throw new DAOException("Sin conexi�n al CIS");
        }

        vData.put("ID", (int) ICVETRAMITE);
      } else{
        vData.put("ID",vData.getInt("ICVETRAMITE"));
      }

      Vector vcData = findByCustom("",
                                   "SELECT MAX(ICONSECUTIVO) AS ICONSECUTIVO from INTTRAMITES where ICVETRAMITE="
                                   + vData.getString("ID"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("ICONSECUTIVO",vUltimo.getInt("ICONSECUTIVO") + 1);
      } else{
        vData.put("ICONSECUTIVO",1);
      }
      vData.addPK(vData.getString("ID"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("ID"));
      lPStmt.setInt(2,vData.getInt("ICONSECUTIVO"));
      lPStmt.setInt(3,vData.getInt("ICVETIPOPERMISIONA"));
      lPStmt.setInt(4,vData.getInt("ICVETIPOTRAMITE"));
      lPStmt.setInt(5,vData.getInt("ICVEDEPARTAMENTO"));
      lPStmt.executeUpdate();
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
    }
    return vData;
  }

  public boolean delete(TVDinRep vData,Connection cnNested) throws DAOException{
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

      // Ajustar Where de acuerdo a requerimientos...
      String lSQL =
          "delete from INTTRAMITES where ICVETRAMITE = ? AND ICONSECUTIVO = ?";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("ICVETRAMITE"));
      lPStmt.setInt(2,vData.getInt("ICONSECUTIVO"));

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
    }
    return lSuccess;
  }

  public TVDinRep integra(TVDinRep vData,Connection cnNested) throws
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
      String lSQL =
          "UPDATE INTTRAMITES SET TSINTEGRA = {FN NOW()}, iCveDeptoIntegra = ?, iCveUsuIntegra = ?  WHERE ICVETRAMITE = ?";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("ICVEDEPARTAMENTOA"));
      lPStmt.setInt(2,vData.getInt("ICVEUSUARIO"));
      lPStmt.setInt(3,vData.getInt("ICVETRAMITE"));

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
    }
    return vData;
  }
  
  public TVDinRep insertCaracterEsencial(TVDinRep vData,Connection cnNested) throws DAOException
  {
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    int ICVETRAMITE = 0;
    
    try{
         if(cnNested == null)
         {
           dbConn = new DbConnection(dataSourceName);
           conn = dbConn.getConnection();
           conn.setAutoCommit(false);
           conn.setTransactionIsolation(2); 
         }
         String lSQL = "insert into INTTRAMITES (ICVETRAMITE,ICONSECUTIVO,ICVETIPOPERMISIONA,ICVETIPOSERVICIO," + 
                       "TSREGISTRO,ICVEDEPARTAMENTO,ICVETIPOTRAMITE) values (?,?,?,?,{FN NOW()},?,?)";

         // Si el tr�mite no existe, solicitar al CIS un n�mero de tr�mite
         if(vData.getInt("ICVETRAMITE") <= 0)
         {
           String wsdlUrl = VParametros.getPropEspecifica("WSCISURI") + "InsertaCita?WSDL";
           WSInsertaCita service = new WSInsertaCita_Impl(wsdlUrl);
           WSInsertaCitaPort port = service.getWSInsertaCitaPort();
           ICVETRAMITE = port.insertaCita(VParametros.getPropEspecifica("WSCISUSR"),
                                          VParametros.getPropEspecifica("WSCISPWD"),
                                          vData.getInt("ICVETRAMITECIS"),
                                          0,
                                          1,
                                          vData.getString("CNOMBRECIS"),
                                          vData.getInt("ICVEAREACIS"));

           if(ICVETRAMITE == 0)
           {
             lSuccess = false;
             throw new DAOException("Sin conexi�n al CIS");
           }

           vData.put("ID", (int) ICVETRAMITE);
         }
         else{ vData.put("ID",vData.getInt("ICVETRAMITE")); }
         Vector vcData = findByCustom("", "SELECT MAX(ICONSECUTIVO) AS ICONSECUTIVO from INTTRAMITES where ICVETRAMITE="
                                          + vData.getString("ID"));
         if(vcData.size() > 0)
         {
           TVDinRep vUltimo = (TVDinRep) vcData.get(0);
           vData.put("ICONSECUTIVO",vUltimo.getInt("ICONSECUTIVO") + 1);
         }
         else{ vData.put("ICONSECUTIVO",1); }
         vData.addPK(vData.getString("ID"));
         
         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.setInt(1,vData.getInt("ID"));
         lPStmt.setInt(2,vData.getInt("ICONSECUTIVO"));
         lPStmt.setInt(3,vData.getInt("ICVETIPOPERMISIONA"));
         lPStmt.setInt(4,vData.getInt("ICVETIPOSERVICIO"));
         lPStmt.setInt(5,vData.getInt("ICVEDEPARTAMENTO"));
         lPStmt.setInt(6,0);
         lPStmt.executeUpdate();
         
         if(cnNested == null){ conn.commit(); }
       } 
    catch(Exception ex)
    {
      ex.printStackTrace();
      warn("insert",ex);
      if(cnNested == null)
      {
        try{ conn.rollback(); }
        catch(Exception e){ fatal("insert.rollback",e); }
      }
      lSuccess = false;
    }
    finally{
             try{
                  if(lPStmt != null){ lPStmt.close(); }
                  if(cnNested == null)
                  {
                    if(conn != null){ conn.close(); }
                    dbConn.closeConnection();
                  }
                }
             catch(Exception ex2){ warn("insert.close",ex2); }
             if(lSuccess == false)
               throw new DAOException("");
           }
    return vData;
  }
  
  public TVDinRep insertCEPersona(TVDinRep vData,Connection cnNested) throws DAOException
  {
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    try{
         if(cnNested == null)
         {
           dbConn = new DbConnection(dataSourceName);
           conn = dbConn.getConnection();
           conn.setAutoCommit(false);
           conn.setTransactionIsolation(2);
         }
         String lSQL = "UPDATE INTTRAMITES SET ICVEPERSONA = ? WHERE ICVETRAMITE = ?";

         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.setInt(1,vData.getInt("ICVEPERSONA"));
         lPStmt.setInt(2,vData.getInt("ICVETRAMITE"));
         lPStmt.executeUpdate();
         
         if(cnNested == null){ conn.commit(); }
       }
    catch(Exception ex)
    {
      ex.printStackTrace();
      warn("update",ex);
      if(cnNested == null)
      {
        try{ conn.rollback(); }
        catch(Exception e){ fatal("insert.rollback",e); }
      }
      lSuccess = false;
    }
    finally{
             try{
                  if(lPStmt != null){ lPStmt.close(); }
                  if(cnNested == null)
                  {
                    if(conn != null){ conn.close(); }
                    dbConn.closeConnection();
                  }
                }
             catch(Exception ex2){ warn("insert.close",ex2); }
             if(lSuccess == false)
               throw new DAOException("");
           }
    return vData;
  }
  
  public TVDinRep insertCEEmpresa(TVDinRep vData,Connection cnNested) throws DAOException
  {
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    try{
         if(cnNested == null)
         {
           dbConn = new DbConnection(dataSourceName);
           conn = dbConn.getConnection();
           conn.setAutoCommit(false);
           conn.setTransactionIsolation(2);
         }
         String lSQL = "UPDATE INTTRAMITES SET ICVEEMPRESA = ? WHERE ICVETRAMITE = ?";

         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.setInt(1,vData.getInt("ICVEEMPRESA"));
         lPStmt.setInt(2,vData.getInt("ICVETRAMITE"));
         lPStmt.executeUpdate();
         
         if(cnNested == null){ conn.commit(); }
       }
    catch(Exception ex)
    {
      ex.printStackTrace();
      warn("update",ex);
      if(cnNested == null)
      {
        try{ conn.rollback(); }
        catch(Exception e){ fatal("insert.rollback",e); }
      }
      lSuccess = false;
    }
    finally{
             try{
                  if(lPStmt != null){ lPStmt.close(); }
                  if(cnNested == null)
                  {
                    if(conn != null){ conn.close(); }
                    dbConn.closeConnection();
                  }
                }
             catch(Exception ex2){ warn("insert.close",ex2); }
             if(lSuccess == false)
               throw new DAOException("");
           }
    return vData;
  }

  public TVDinRep insertSol(TVDinRep vData,Connection cnNested) throws
	  DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		PreparedStatement lPStmt2 = null;
		boolean lSuccess = true;
		int ICVETRAMITE = 0;
	    String cCveInt = "";
	    String cModalidad = "";
	    java.sql.Date dtRegistro = tFecha.getDateSQL(tFecha.getThisTime(false));
	    int iEjercicio = tFecha.getIntYear(dtRegistro);
	    int iNumSolicitud = 1;
	    
		try{
		  if(cnNested == null){
		    dbConn = new DbConnection(dataSourceName);
		    conn = dbConn.getConnection();
		    conn.setAutoCommit(false);
		    conn.setTransactionIsolation(2);
		  }
		  String lSQL = "INSERT INTO INTTRAMITES (IEJERCICIO,INUMSOLICITUD,ICVETRAMITE,ICVEMODALIDAD,TSREGISTRO) VALUES (year({FN NOW()}),?,?,?,{FN NOW()})";
		  
		  String lInsSol = "INSERT INTO TRAREGSOLICITUD (IEJERCICIO,INUMSOLICITUD,ICVETRAMITE,ICVEMODALIDAD,ICVESOLICITANTE,ICVEUSUREGISTRA,ICVEOFICINA,ICVEDEPARTAMENTO,IIDCITA,LPRINCIPAL,ICVEOFICINAENVIARES,lImpreso,lTramInernet,tsRegistro,DTIMPRESION) VALUES (year({FN NOW()}),?,?,?,?,1,?,95,?,1,?,1,1,{FN NOW()},date({FN NOW()}))";
		
		  // Si el tr�mite no existe, solicitar al CIS un n�mero de tr�mite
		  
		  Vector vcTramCis = this.findByNessted("","Select iCveTramiteCis FROM TRACONFIGURATRAMITE where icvetramite="+vData.getInt("iCveTramite")+" and iCveModalidad = "+vData.getInt("iCveModalidad"), conn);
		  
		  if(vcTramCis.size()>0){
		    TVDinRep vTramCis = (TVDinRep) vcTramCis.get(0);
		    
		    String wsdlUrl = VParametros.getPropEspecifica("URLWSCis") + "InsertaCita?WSDL";
		    WSInsertaCita service = new WSInsertaCita_Impl(wsdlUrl);
		    WSInsertaCitaPort port = service.getWSInsertaCitaPort();
		    
		    Vector vcTram = this.findByCustom("", "SELECT T.CCVEINTERNA,m.CDSCMODALIDAD FROM TRACATTRAMITE T,TRAMODALIDAD M where t.ICVETRAMITE="+vData.getString("iCveTramite")+" and ICVEMODALIDAD="+vData.getString("iCveModalidad"));
		    if(vcTram.size()>0){
		    	TVDinRep vTram = (TVDinRep) vcTram.get(0);
		    	cCveInt = vTram.getString("CCVEINTERNA");
		    	cModalidad = vTram.getString("CDSCMODALIDAD");
		    }

		
		    String cObservacion = "";
		    
		    cObservacion += "RFC "; 
		    cObservacion += vData.getString("cRFCRep");
		    cObservacion += " Clave del Tramite ";
		    cObservacion += vData.getInt("iCveTramite");
		    cObservacion += "-"; 
		    cObservacion += vData.getInt("iCveModalidad");
		    cObservacion += " " ;
		    cObservacion +=cCveInt;
		    cObservacion += " - ";
		    
		    cObservacion += cModalidad;
		    	          
		    ICVETRAMITE = port.insertaCita(VParametros.getPropEspecifica("ConWSCisUsr"),
                    VParametros.getPropEspecifica("ConWSCisPwd"),
                    vTramCis.getInt("iCveTramiteCis"),
                    0,
                    1,
                    cObservacion,
                    93);
		
		    if(ICVETRAMITE == 0){
		      lSuccess = false;
		      throw new DAOException("Sin conexi�n al CIS");
		    }
		
		    vData.put("ID", (int) ICVETRAMITE);
		  } else{
		    vData.put("ID",vData.getInt("ICVETRAMITE"));
		  }
		  
		  /*
		  Vector vcData = findByCustom("",
		                               "SELECT max(iNumSolicitud) as iNumSolicitud FROM TRAREGSOLICITUD where iejercicio= year({FN NOW()})");
		  if(vcData.size() > 0){
		    TVDinRep vUltimo = (TVDinRep) vcData.get(0);
		    vData.put("iNumSolicitud",vUltimo.getInt("iNumSolicitud") + 1);
		  } else{
		    vData.put("iNumSolicitud",1);
		  }
          */	  
		  iNumSolicitud = TablaSecuenciaXAnio.getSecuencia(conn, "SOLICITUD", iEjercicio);
		  vData.put("iNumSolicitud",iNumSolicitud);
		  //System.out.print(">>>>>>>>>>iNumSolicitud:"+vData.getInt("iNumSolicitud"));
		  
		  vData.addPK(vData.getString("iNumSolicitud"));

		  lPStmt = conn.prepareStatement(lSQL);
		  lPStmt.setInt(1,vData.getInt("iNumSolicitud"));
		  lPStmt.setInt(2,vData.getInt("iCveTramite"));
		  lPStmt.setInt(3,vData.getInt("iCveModalidad"));
		  lPStmt.executeUpdate();
		  
		  lPStmt2 = conn.prepareStatement(lInsSol);
		  lPStmt2.setInt(1,vData.getInt("iNumSolicitud"));
		  lPStmt2.setInt(2,vData.getInt("iCveTramite"));
		  lPStmt2.setInt(3,vData.getInt("iCveModalidad"));
		  lPStmt2.setInt(4,vData.getInt("iCveSolicitante"));
		  if( cCveInt.substring(0,2).equals("PT")){lPStmt2.setInt(5,1); }
		  else{ lPStmt2.setInt(5,vData.getInt("iCveOficina")); }
		  lPStmt2.setInt(6,ICVETRAMITE);
		  lPStmt2.setInt(7,vData.getInt("iCveOficina"));
		  lPStmt2.executeUpdate();
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
		}
		return vData;
		}


  public TVDinRep insertSolADV(TVDinRep vData,Connection cnNested) throws
  DAOException{
	DbConnection dbConn = null;
	Connection conn = cnNested;
	PreparedStatement lPStmt = null;
	PreparedStatement lPStmt2 = null;
	boolean lSuccess = true;
	int ICVETRAMITE = 0;
    String cCveInt = "";
    String cModalidad = "";
    java.sql.Date dtRegistro = tFecha.getDateSQL(tFecha.getThisTime(false));
    java.sql.Timestamp tsRegistro = tFecha.getThisTime(false);
    int iEjercicio = tFecha.getIntYear(dtRegistro);
    int iNumSolicitud = 1;
    int cveDptoVU = Integer.parseInt(VParametros.getPropEspecifica("DeptoVentanillaUnica"));
    
	try{
	  if(cnNested == null){
	    dbConn = new DbConnection(dataSourceName);
	    conn = dbConn.getConnection();
	    conn.setAutoCommit(false);
	    conn.setTransactionIsolation(2);
	  }
	  
	  
	  Vector vcTramCis = this.findByNessted("","Select iCveTramiteCis FROM TRACONFIGURATRAMITE where icvetramite="+vData.getInt("iCveTramite")+" and iCveModalidad = "+vData.getInt("iCveModalidad"), conn);
	  
	  if(vcTramCis.size()>0){
	    TVDinRep vTramCis = (TVDinRep) vcTramCis.get(0);
	    
	    String wsdlUrl = VParametros.getPropEspecifica("URLWSCis") + "InsertaCita?WSDL";
	    WSInsertaCita service = new WSInsertaCita_Impl(wsdlUrl);
	    WSInsertaCitaPort port = service.getWSInsertaCitaPort();
	    
	    Vector vcTram = this.findByCustom("", "SELECT T.CCVEINTERNA,m.CDSCMODALIDAD FROM TRACATTRAMITE T,TRAMODALIDAD M where t.ICVETRAMITE="+vData.getString("iCveTramite")+" and ICVEMODALIDAD="+vData.getString("iCveModalidad"));
	    if(vcTram.size()>0){
	    	TVDinRep vTram = (TVDinRep) vcTram.get(0);
	    	cCveInt = vTram.getString("CCVEINTERNA");
	    	cModalidad = vTram.getString("CDSCMODALIDAD");
	    }

	
	    String cObservacion = "";
	    
	    cObservacion += "RFC "; 
	    cObservacion += vData.getString("cRFCRep");
	    cObservacion += " Clave del Tramite ";
	    cObservacion += vData.getInt("iCveTramite");
	    cObservacion += "-"; 
	    cObservacion += vData.getInt("iCveModalidad");
	    cObservacion += " " ;
	    cObservacion +=cCveInt;
	    cObservacion += " - ";
	    
	    cObservacion += cModalidad;
	    	          
	    ICVETRAMITE = port.insertaCita(VParametros.getPropEspecifica("ConWSCisUsr"),
                VParametros.getPropEspecifica("ConWSCisPwd"),
                vTramCis.getInt("iCveTramiteCis"),
                0,
                1,
                cObservacion,
                93);
	
	    if(ICVETRAMITE == 0){
	      lSuccess = false;
	      throw new DAOException("Sin conexi�n al CIS");
	    }
	

	    vData.put("ID", (int) ICVETRAMITE);
	  } else{
	    vData.put("ID",vData.getInt("ICVETRAMITE"));
	  }
	  
	  
      /******************* INSERT INTDATOSCITA ***************/
      
      
      
      String cInsertCita = "insert into TRADATOSCITA (iEjercicio,iIdCita,dtCita) values(?,?,current_date)";
      
      lPStmt=null;
      lPStmt = conn.prepareStatement(cInsertCita);
      
      lPStmt.setInt(1,iEjercicio);
      lPStmt.setInt(2, ICVETRAMITE);

      lPStmt.executeUpdate();
      
      
      /********INSERT INTTRAMITE***********/
	  iNumSolicitud = TablaSecuenciaXAnio.getSecuencia(conn, "SOLICITUD", iEjercicio);
      
  	  String lSQLintTram = "INSERT INTO INTTRAMITES (IEJERCICIO,INUMSOLICITUD,ICVETRAMITE,ICVEMODALIDAD,TSREGISTRO) VALUES (?,?,?,?,{FN NOW()})";

      lPStmt = null; 
	  lPStmt = conn.prepareStatement(lSQLintTram);
	  
	  lPStmt.setInt(1,iEjercicio);
	  lPStmt.setInt(2,iNumSolicitud);
	  lPStmt.setInt(3,vData.getInt("iCveTramite"));
	  lPStmt.setInt(4,vData.getInt("iCveModalidad"));

      lPStmt.executeUpdate();
      
      /*****************  INSERT SOLICITUD  **********/	  
	  
	  
	   String cSQLTra = "INSERT INTO TRARegSolicitud"+
	   "(IEJERCICIO,INUMSOLICITUD,ICVETRAMITE,ICVEMODALIDAD,ICVESOLICITANTE,ICVEREPLEGAL," +
	   "CNOMAUTORIZARECOGER,ICVEUSUREGISTRA,TSREGISTRO,DTLIMITEENTREGADOCS,DTESTIMADAENTREGA," +
	   "LPAGADO,DTENTREGA,ICVEUSUENTREGA,LRESOLUCIONPOSITIVA,LDATOSPREREGISTRO,IIDBIEN,ICVEOFICINA," +
	   "ICVEDEPARTAMENTO,IEJERCICIOCITA,IIDCITA,ICVEFORMA,LPRINCIPAL,LIMPRESO,ICVEDOMICILIOSOLICITANTE," +
	   "ICVEDOMICILIOREPLEGAL,CENVIARRESOLUCIONA,COBSTRAMITE,ICVEFORMATOREG,CDSCBIEN,DTIMPRESION," +
	   "ICVEOFICINAENVIARES,DUNIDADESCALCULO,LNOTIFICACION,LTRAMINERNET,DTRECEPCION,LABANDONADA,LTECNICO,LJURIDICO,DTRESOLTRAM,LPERMISO)"+
	   " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		   
		TFechas fecha = new TFechas();
		java.sql.Date dtActual = fecha.getDateSQL(fecha.getThisTime());
	   
	   java.sql.Date dtLimEntregaDoctos = this.getFechaLimiteEntregaDocs(
			   vData.getInt("iCveTramite"),
			   vData.getInt("iCveModalidad"), dtActual);       

		       
       lPStmt = conn.prepareStatement(cSQLTra);
       lPStmt.setInt(1,iEjercicio); // iEjercicio
       lPStmt.setInt(2,iNumSolicitud); // iNumSolicitud
       lPStmt.setInt(3,vData.getInt("iCveTramite")); // iCveTramite
       lPStmt.setInt(4,vData.getInt("iCveModalidad")); // iCveModalidad
       lPStmt.setInt(5,vData.getInt("iCveSolicitante")); // iCveSolicitante
       lPStmt.setInt(6,vData.getInt("iCveRepLegal")); // iCveRepLegal
       lPStmt.setString(7,vData.getString("cAutorizado")); // cNomAutorizaRecoger
       lPStmt.setInt(8,0); // iCveUsuRegistra
       lPStmt.setTimestamp(9,tsRegistro); // tsRegistro
       lPStmt.setDate(10,dtLimEntregaDoctos); // dtLimiteEntregaDocs
       lPStmt.setNull(11,Types.DATE); // dtEstamidoEntrega
       lPStmt.setInt(12,0); // lpagado
       lPStmt.setNull(13,Types.DATE); // dtEntrega
       lPStmt.setInt(14,0); // iCveUsuEntrega
       lPStmt.setInt(15,0); // lResolucionPositiva
       lPStmt.setInt(16,0); // lDatosPreregistro
       lPStmt.setNull(17,Types.INTEGER); // iIdBien
       lPStmt.setInt(18,vData.getInt("iCveOficina")); // iCveOficina
       lPStmt.setInt(19,cveDptoVU); // iCveDepartamento
       lPStmt.setInt(20,iEjercicio); // iEjercicioCita
       lPStmt.setInt(21,ICVETRAMITE); // iIdCita
       lPStmt.setNull(22,Types.INTEGER);// iCveForma
       lPStmt.setInt(23,1); // lPrincipal
       lPStmt.setInt(24,1); // lImpreso
       lPStmt.setInt(25,vData.getInt("iCveDomicilioSolicitante")); // iCveDomicilioSolicitante
       lPStmt.setInt(26,vData.getInt("iCveDomicilioRepLegal")); // iCveDomicilioRepLegal
       lPStmt.setNull(27,Types.VARCHAR); //cEnviaResolucionA
       lPStmt.setNull(28,Types.VARCHAR); // cObsTramite
       lPStmt.setNull(29,Types.INTEGER); // iCveFormatoReg
       lPStmt.setNull(30,Types.VARCHAR); // cDscBien
       //lPStmt.setNull(31,Types.DATE); //dtImpresion
       lPStmt.setDate(31,dtRegistro); //dtImpresion
       lPStmt.setInt(32,vData.getInt("iCveOficina")); // oficinaEnviaREs
       lPStmt.setNull(33,Types.INTEGER); // unidadesCalculo
       lPStmt.setNull(34,Types.INTEGER); // lnotificacion
       lPStmt.setInt(35,1); // lTramiteInternet
       lPStmt.setNull(36,Types.DATE); // dtRecepcion
       lPStmt.setInt(37,0); //lAbandonada
       lPStmt.setInt(38,0); // ltecnico
       lPStmt.setInt(39,0); // ljuridico
       lPStmt.setNull(40,Types.DATE); // dtResolTram
       lPStmt.setNull(41,Types.INTEGER); // lpermiso       
       

         lPStmt.executeUpdate();
      
      /**************DATOS ADV****************/
      // INSERTA EN LA TABLA TRAREGDATOSADVXSOL  
      
         
      String SqlADV= "INSERT INTO TRAREGDATOSADVXSOL (IEJERCICIO,INUMSOLICITUD,CORGANO,CHECHOS,ICVECARRETERA,DTVISITA,CKMSENTIDO) VALUES (?,?,?,?,?,(CURRENT_DATE + 10 DAYS),?)";
      
      String sqlCveCarr = "SELECT ICVECARRETERA FROM TRACATCARRETERA WHERE CDSCARRETERA ='"+vData.getString("hdNomAuto")+"'"+
    		  			  " AND ICVEOFICINA ="+vData.getString("iCveOficina");

      Vector vcDa = this.findByCustom("", sqlCveCarr);
      
      int cveAuto=0;

      if(vcDa.size() > 0){
   	   TVDinRep vUltimo = (TVDinRep) vcDa.get(0);
   	   cveAuto = vUltimo.getInt("ICVECARRETERA");
      }
      
      String sqlOrg="SELECT CTITULAR FROM GRLOFICINA WHERE ICVEOFICINA=" + vData.getString("iCveOficina");
      
      Vector vcOrg = this.findByCustom("", sqlOrg);
      
      String orga="";

      if(vcDa.size() > 0){
   	   TVDinRep vOrg = (TVDinRep) vcOrg.get(0);
   	   orga = vOrg.getString("CTITULAR");
      }
      
      
      lPStmt = null; 
      lPStmt = conn.prepareStatement(SqlADV);
             
      lPStmt.setInt(1, iEjercicio);
      lPStmt.setInt(2 ,iNumSolicitud);
      lPStmt.setString(3, orga);
      lPStmt.setString(4, vData.getString("cHechos"));
      lPStmt.setInt(5, cveAuto);
      lPStmt.setString(6, vData.getString("cKmSentido"));
      

      lPStmt.executeUpdate();
             

       /***********inserta el registro para la asignacion de folios********/
//       String sqlInsFolxSol = "insert into trafoliosadvxsol (iejercicio, inumsolicitud) values (?,?)"; 
//       
//       lPStmt=null;
//       lPStmt=conn.prepareStatement(sqlInsFolxSol);
//       lPStmt.setInt(1, iEjercicio);
//       lPStmt.setInt(2, iNumSolicitud);
//       
//       try{
//           lPStmt.executeUpdate();
//         }catch(Exception e){
//           e.printStackTrace();
//         }
       /************/
       
       
		String sReq = "SELECT ICVEREQUISITO,LREQUERIDO FROM TRAREQXMODTRAMITE "
				+ "where ICVETRAMITE="
				+ vData.getInt("iCveTramite")
				+ " and icvemodalidad ="
				+ vData.getInt("iCveModalidad")
				+ " and LREQUERIDO = 1";
		Vector vRequisitos = this.findByNessted("", sReq, conn);
		
		

		for (int j = 0; j < vRequisitos.size(); j++) {
			
				String cSQLReq = "INSERT INTO TRARegReqXTram (iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,iEjercicioFormato,iCveFormatoReg,dtRecepcion,iCveUsuRecibe,dtNotificacion,cNumOficio,dtOficio,lValido,lFisico)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				TVDinRep vDRequisitos = new TVDinRep();
				vDRequisitos=(TVDinRep)vRequisitos.get(j);
				
				lPStmt2 = conn.prepareStatement(cSQLReq);
				lPStmt2.setInt(1, iEjercicio); // iEjercicio
				lPStmt2.setInt(2, iNumSolicitud); // iNumSolicitud
				lPStmt2.setInt(3,vData.getInt("iCveTramite"));
				lPStmt2.setInt(4,vData.getInt("iCveModalidad"));
				lPStmt2.setInt(5,vDRequisitos.getInt("ICVEREQUISITO")); // iCveRequisito
				lPStmt2.setInt(6, 0); // iEjercicioFormato
				lPStmt2.setInt(7, 0); // iCveFormatoReg
				lPStmt2.setNull(8, Types.DATE); // dtRecepcion
				lPStmt2.setInt(9, 0); // iCveUsuRecibe
				lPStmt2.setNull(10, Types.DATE); // dtNotificacion
				lPStmt2.setString(11, ""); // cNumOficio
				lPStmt2.setNull(12, Types.DATE); // dtOficio
				lPStmt2.setInt(13, 0); // lValido
				lPStmt2.setInt(14, 0); // lFisico
				
				lPStmt2.executeUpdate();

				
				String sqlInsEv = "INSERT INTO TRAREGEVAREQXAREA (IEJERCICIO,INUMSOLICITUD,ICVETRAMITE,"+
								  "ICVEMODALIDAD,ICVEREQUISITO,ICVEUSUARIO,LVALIDO,ICONSECUTIVOPNC,DTEVALUACION) "+
								  "VALUES (?,?,?,?,?,?,?,?,?)";
				lPStmt2=null;
				lPStmt2 = conn.prepareStatement(sqlInsEv);
				
				lPStmt2.setInt(1,iEjercicio);
				lPStmt2.setInt(2,iNumSolicitud);					
				lPStmt2.setInt(3,vData.getInt("iCveTramite"));
				lPStmt2.setInt(4,vData.getInt("iCveModalidad"));
				lPStmt2.setInt(5,vDRequisitos.getInt("ICVEREQUISITO"));
				lPStmt2.setInt(6,0);
				lPStmt2.setInt(7,0);
				lPStmt2.setNull(8, Types.INTEGER);
				lPStmt2.setNull(9, Types.DATE);
				

				lPStmt2.executeUpdate();
			
		}
		
		/**PARA SABER QUE REQUISITOS SE ENTREGARAN COMO FISICOS PARA SOLICITARLOS EN LA SEGUNDA PESTANA DE TRAMITE POR INTERNET**/
		
		//System.out.print(vData.getString("cCadReqs"));
		
		String[] arrReqsFisicos = vData.getString("cCadReqs").split(",");
		
		if(arrReqsFisicos.length>0){
			String sqlInsReqFis = "INSERT INTO TRAREQDIGINT (IEJERCICIO,INUMSOLICITUD,ICVEREQUISITO) VALUES (?,?,?)";
			lPStmt2=null;
			lPStmt2 = conn.prepareStatement(sqlInsReqFis);
			for (int j = 0; j < arrReqsFisicos.length; j++) {
	
					
					lPStmt2.setInt(1,iEjercicio);
					lPStmt2.setInt(2,iNumSolicitud);					
					lPStmt2.setInt(3,Integer.parseInt(arrReqsFisicos[j]));
					
				
						lPStmt2.executeUpdate();
				
			}
		}
       
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
	}
	return vData;
	}
  
  public java.sql.Date getFechaLimiteEntregaDocs(int iCveTramite, int iCveModalidad, java.sql.Date dtIni){
	  java.sql.Date dtLimiteEntregaDocs = null;
	  Vector vData = new Vector();
	  String cSQL = "SELECT iNumDiasCubrirReq,lDiasNaturalesReq " +
	                "FROM TRAConfiguraTramite " +
	                "WHERE iCveTramite = " + iCveTramite +
	                "  AND iCveModalidad = " + iCveModalidad +
	                "  AND dtiniVigencia in ("+
	                "select max(dtIniVigencia) from TraConfiguraTramite " +
	                "WHERE iCveTramite = " + iCveTramite +
	                " AND iCveModalidad = " + iCveModalidad +
	                ")";
	  TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();

	  try{
	    vData = this.findByCustom("", cSQL);
	    if (vData.size() > 0){
	      TVDinRep dDato = (TVDinRep)vData.get(0);
	      dtLimiteEntregaDocs = dDiaNoHabil.getFechaFinal(dtIni, dDato.getInt("iNumDiasCubrirReq"),(dDato.getInt("lDiasNaturalesReq") == 0)?false:true);
	    }
	  }catch(Exception e){
	    cMensaje = e.getMessage();
	  }
	  return dtLimiteEntregaDocs;
	}

}

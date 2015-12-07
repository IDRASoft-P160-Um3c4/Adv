package gob.sct.sipmm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
  private TParametro VParametros = new TParametro("11");
  private String dataSourceName = VParametros
      .getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas("11");
  

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

      // Si el trámite no existe, solicitar al CIS un número de trámite
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
          throw new DAOException("Sin conexión al CIS");
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

         // Si el trámite no existe, solicitar al CIS un número de trámite
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
             throw new DAOException("Sin conexión al CIS");
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
		
		  // Si el trámite no existe, solicitar al CIS un número de trámite
		  
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
		      throw new DAOException("Sin conexión al CIS");
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
		  System.out.println(">>>>>>>>>>iNumSolicitud:"+vData.getInt("iNumSolicitud"));
		  
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
		  System.out.println(">>>  "+cCveInt.substring(0,2)+"==PT?  "+cCveInt.substring(0,2).equals("PT"));
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

}

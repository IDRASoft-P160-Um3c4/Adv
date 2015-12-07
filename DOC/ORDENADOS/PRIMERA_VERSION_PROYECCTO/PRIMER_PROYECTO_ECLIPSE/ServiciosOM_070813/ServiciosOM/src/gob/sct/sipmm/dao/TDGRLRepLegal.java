package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>Title: TDGRLRepLegal.java</p>
 * <p>Description: DAO de la entidad GRLRepLegal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
public class TDGRLRepLegal
    extends DAOBase{
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
    } catch(SQLException ex){
      cMensaje = getSQLMessages(ex);
    } finally{
      if(!cMensaje.equals(""))
        throw new DAOException(cMensaje);
      return vcRecords;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into GRLRepLegal(iCveEmpresa,iCvePersona,dtAsignacion,lPrincipal) values (?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveEmpresa,iCvePersona, </b></p>
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
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLRepLegal(iCveEmpresa,iCvePersona,dtAsignacion,lPrincipal) values (?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("",
          "select MAX(iCvePersona) AS iCvePersona from GRLRepLegal");
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iCvePersona",vUltimo.getInt("iCvePersona") + 1);
      } else
        vData.put("iCvePersona",1);
      vData.addPK(vData.getString("iCvePersona"));
      vData.addPK(vData.getString("iCveEmpresa"));

      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveEmpresa"));
      lPStmt.setInt(2,vData.getInt("iCvePersona"));
      lPStmt.setDate(3,vData.getDate("dtAsignacion"));
      lPStmt.setInt(4,vData.getInt("lPrincipal"));
      lPStmt.executeUpdate();

      new TDGRLDom().insert(vData,conn);

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
      return vData;
    }
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from GRLRepLegal where iCveEmpresa = ? AND iCvePersona = ?  </b></p>
   * <p><b> Campos Llave: iCveEmpresa,iCvePersona, </b></p>
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
      String lSQL =
          "delete from GRLRepLegal where iCveEmpresa = ? AND iCvePersona = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveEmpresa"));
      lPStmt.setInt(2,vData.getInt("iCvePersona"));

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
   * <p><b> update GRLRepLegal set dtAsignacion=?, lPrincipal=? where iCveEmpresa = ? AND iCvePersona = ?  </b></p>
   * <p><b> Campos Llave: iCveEmpresa,iCvePersona, </b></p>
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
      String lSQL = "update GRLRepLegal set dtAsignacion=?, lPrincipal=? where iCveEmpresa = ? AND iCvePersona = ? ";

      vData.addPK(vData.getString("iCveEmpresa"));
      vData.addPK(vData.getString("iCvePersona"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtAsignacion"));
      lPStmt.setInt(2,vData.getInt("lPrincipal"));
      lPStmt.setInt(3,vData.getInt("iCveEmpresa"));
      lPStmt.setInt(4,vData.getInt("iCvePersona"));
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
	      String cSql = "SELECT ICVEEMPRESA,ICVEPERSONA,DTASIGNACION,LPRINCIPAL FROM GRLREPLEGAL WHERE ICVEEMPRESA= "+vData.getInt("iCvePersonaO")+" AND ICVEPERSONA="+vData.getInt("iCveRepLegal");
	      Vector vcRepresentante = this.findByNessted("", cSql, conn);
	      if(vcRepresentante.size()>0){
	    	  TVDinRep vRepresentante = (TVDinRep) vcRepresentante.get(0);
	    	  TVDinRep vBorrar = new TVDinRep();
	    	  vBorrar.put("iCveEmpresa", vData.getInt("iCvePersonaO"));
	    	  vBorrar.put("iCvePersona", vData.getInt("iCveRepLegal"));
	    	  
	    	  System.out.println(">>>  Empresa: "+vBorrar.getInt("iCveEmpresa"));
	    	  System.out.println(">>>  Persona: "+vBorrar.getInt("iCvePersona"));
	    	  
	    	  this.delete(vBorrar,conn);
	    	  TVDinRep vAgrega = new TVDinRep();
	    	  vAgrega.put("iCveEmpresa",vData.getInt("iCvePersonaD"));
	    	  vAgrega.put("iCvePersona",vData.getInt("iCveRepLegal"));
	    	  vAgrega.put("dtAsignacion",vRepresentante.getDate("DTASIGNACION"));
	    	  vAgrega.put("lPrincipal",vRepresentante.getInt("LPRINCIPAL"));
	    	  System.out.println(">>>  Empresa: "+vAgrega.getInt("iCveEmpresa"));
	    	  System.out.println(">>>  iCvePersona: "+vAgrega.getInt("iCvePersona"));
	    	  System.out.println(">>>  dtAsignacion: "+vAgrega.getDate("dtAsignacion"));
	    	  this.insert2(vAgrega,conn);
	    	  conn.commit();
	      }
	    }	
	    catch(Exception ex){
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
	public TVDinRep insert2(TVDinRep vData,Connection cnNested) throws
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
		      "insert into GRLRepLegal(iCveEmpresa,iCvePersona,dtAsignacion,lPrincipal) values (?,?,?,?)";
		
		  lPStmt = conn.prepareStatement(lSQL);
		  lPStmt.setInt(1,vData.getInt("iCveEmpresa"));
		  lPStmt.setInt(2,vData.getInt("iCvePersona"));
		  lPStmt.setDate(3,vData.getDate("dtAsignacion"));
		  lPStmt.setInt(4,vData.getInt("lPrincipal"));
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
		  return vData;
		}
	}


}

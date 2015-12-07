package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*; 
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRAGpoRequisito.java</p>
 * <p>Description: DAO de la entidad TRAGpoRequisito</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDINTTramXCampo extends DAOBase{
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
  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into TRAGpoRequisito(iCveGrupo,cDscGrupo,cFundLegal) values (?,?,?) </b></p>
   * <p><b> Campos Llave: iCveGrupo, </b></p>
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
          "insert into INTTramXCampo(iCveTramite,iCveModalidad,iCveRequisito,iCveCampo,iOrden) values (?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iOrden) AS iOrden from INTTramXCampo " +
      								  "Where iCveTramite = "  + vData.getInt("iCveTramite") +
      								  " AND iCveModalidad = " + vData.getInt("iCveModalidad") +
      								  " AND iCveRequisito = " + vData.getInt("iCveRequisito")); 
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iOrden",vUltimo.getInt("iOrden") + 1);
      }else
         vData.put("iCveCampo",1);
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));
      vData.addPK(vData.getString("iCveCampoI"));
      //...
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iCveRequisito"));
      lPStmt.setInt(4,vData.getInt("iCveCampoI"));
      lPStmt.setInt(5,vData.getInt("iOrden"));
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
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRAGpoRequisito where iCveGrupo = ?  </b></p>
   * <p><b> Campos Llave: iCveGrupo, </b></p>
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
      String lSQL = "delete from INTTramXCampo where iCveTramite = ? and iCveModalidad=? and iCveRequisito=? AND iCveCampo = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iCveRequisito"));
      lPStmt.setInt(4,vData.getInt("iCveCampo"));

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
   * <p><b> update TRAGpoRequisito set cDscGrupo=?, cFundLegal=? where iCveGrupo = ?  </b></p>
   * <p><b> Campos Llave: iCveGrupo, </b></p>
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
		  String lSQL = "update INTTramXCampo set cCarpeta=? where iCveTramite =? and iCveModalidad=? and iCveRequisito=? and iCveCampo = ? "; 

		  vData.addPK(vData.getString("iCveTramite"));
		  vData.addPK(vData.getString("iCveModalidad"));
		  vData.addPK(vData.getString("iCveRequisito"));
		  vData.addPK(vData.getString("iCveCampo"));
		
		  lPStmt = conn.prepareStatement(lSQL);
		  lPStmt.setString(1,vData.getString("cCarpeta"));
		  lPStmt.setInt(2,vData.getInt("iCveTramite"));
		  lPStmt.setInt(3,vData.getInt("iCveModalidad"));
		  lPStmt.setInt(4,vData.getInt("iCveRequisito"));
		  lPStmt.setInt(5,vData.getInt("iCveCampo"));
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
  public TVDinRep Posicion(TVDinRep vData,Connection cnNested) throws
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
	  String lSQL = "update INTTramXCampo set iOrden=? where iCveTramite =? and iCveModalidad=? and iCveRequisito=? and iOrden = ? "; 

	  vData.addPK(vData.getString("iCveTramite"));
	  vData.addPK(vData.getString("iCveModalidad"));
	  vData.addPK(vData.getString("iCveRequisito"));
	  vData.addPK(vData.getString("iCveCampo"));
	
	  lPStmt = conn.prepareStatement(lSQL);
	  lPStmt.setInt(1,vData.getInt("iLugar2"));
	  lPStmt.setInt(2,vData.getInt("iCveTramite"));
	  lPStmt.setInt(3,vData.getInt("iCveModalidad"));
	  lPStmt.setInt(4,vData.getInt("iCveRequisito"));
	  lPStmt.setInt(5,vData.getInt("iLugar1"));
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
  public TVDinRep Bajar(TVDinRep vData,Connection cnNested) throws
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
	  String lSQL = "update INTCAMPOS set cCampo=?,cEtiqueta=?,iLargo=?,iCveTipoCampo=?,cTabla=?,cCve=?,cDsc=?,lMandatorio=?,lFijo=?,lEncabezado=?,lSeleccionar=?,cLigado=?,cCampoCop=?,cScript=?,cCarpeta=? where iCveCampo = ? "; 
	
	  vData.addPK(vData.getString("iCveCampo"));
	
	  lPStmt = conn.prepareStatement(lSQL);
	  lPStmt.setString(1,vData.getString("cCampo"));
	  lPStmt.setString(2,vData.getString("cEtiqueta"));
	  lPStmt.setInt(3,vData.getInt("iLargo"));
	  lPStmt.setInt(4,vData.getInt("iCveTipoCampo"));
	  lPStmt.setString(5,vData.getString("cTabla"));
	  lPStmt.setString(6,vData.getString("cCve"));
	  lPStmt.setString(7,vData.getString("cDsc"));
	  lPStmt.setInt(8,vData.getInt("lMandatorio"));
	  lPStmt.setInt(9,vData.getInt("lFijo"));
	  lPStmt.setInt(10,vData.getInt("lEncabezado"));
	  lPStmt.setInt(11,vData.getInt("lSeleccionar"));
	  lPStmt.setString(12,vData.getString("cLigado"));
	  lPStmt.setString(13,vData.getString("cCampoCop"));
	  lPStmt.setString(14,vData.getString("cScript"));
	  lPStmt.setString(15,vData.getString("cCarpeta"));
	  lPStmt.setInt(16,vData.getInt("iCveCampo"));
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


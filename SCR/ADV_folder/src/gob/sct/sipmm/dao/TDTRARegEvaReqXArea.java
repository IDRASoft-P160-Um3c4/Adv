package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDTRARegReqXTram.java</p>
 * <p>Description: DAO de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRARegEvaReqXArea extends DAOBase{
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
   * <p><b> insert into TRARegReqXTram(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,dtRecepcion,iCveUsuRecibe) values (?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
    String[] aRequisitos;
    String[] aTramite;
    String[] cRegistro;
    String cTramite;
    String cModalidad;
    int iSolicitud = 0;
    int iAux;
    int i;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }


      /*String cSql1 = "Select MAX(iNumSolicitud) as iNumSolicitud " +
            "from TRARegReqXTram " +
            "where iEjercicio =  Year(Current date)";*/

      aTramite = vData.getString("iCveRequisito").split(";");
      cRegistro = vData.getString("iAuxiliar").split(",");

      String lSQL =
             "insert into TRARegReqXTram(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,dtRecepcion,iCveUsuRecibe) values (YEAR(CURRENT DATE),?,?,?,?,(CURRENT DATE),?)";

       lPStmt = conn.prepareStatement(lSQL);


       String cSql = "SELECT iCveTramiteHijo,iCveModalidadHijo " +
                                   "FROM tradependencia " +
                                   "WHERE iCveTramite = " + vData.getInt("iCveTramite") + " " +
                                   "AND iCveModalidad = " + vData.getInt("iCveModalidad");

      Vector vcHijos = findByCustom("",cSql);


      for(iAux = 0; iAux <= (aTramite.length -1); iAux++){

        aRequisitos = aTramite[iAux].split(",");

        if(Integer.parseInt(cRegistro[iAux]) != 0){
              cTramite = ""  + ((TVDinRep)vcHijos.get(Integer.parseInt(cRegistro[iAux])-1)).getInt("iCveTramiteHijo");
              cModalidad = "" + ((TVDinRep)vcHijos.get(Integer.parseInt(cRegistro[iAux])-1)).getInt("iCveModalidadHijo");

           }else{

              cTramite = "" + vData.getString("iCveTramite");
              cModalidad = "" + vData.getString("iCveModalidad") ;

           }






            if(iAux == 0)
              iSolicitud = vData.getInt("iNumSolicitud");
            else
               iSolicitud++;





        for(i = 0;i < aRequisitos.length;i++){ //<---insertar todos los requisitos


          vData.addPK(vData.getString("iEjercicio"));
          vData.addPK(vData.getString("iNumSolicitud"));
          vData.addPK(vData.getString("iCveTramite"));
          vData.addPK(vData.getString("iCveModalidad"));
          vData.addPK(vData.getString("iCveRequisito"));
          //...

          //lPStmt = conn.prepareStatement(lSQL);

          lPStmt.setInt(1,iSolicitud);
          lPStmt.setInt(2,Integer.parseInt(cTramite));
          lPStmt.setInt(3,Integer.parseInt(cModalidad));
          lPStmt.setInt(4,Integer.parseInt(aRequisitos[i]));
          lPStmt.setInt(5,vData.getInt("iCveUsuRecibe"));
          lPStmt.executeUpdate();

        }
      }
      new TDTRASolicitudRel().insert(vData,conn);
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
   * <p><b> delete from TRARegReqXTram where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
      String lSQL = "delete from TRARegReqXTram where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveRequisito"));

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
   * <p><b> update TRARegReqXTram set dtRecepcion=?, iCveUsuRecibe=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito, </b></p>
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
      String lSQL = "update TRARegReqXTram set dtRecepcion=?, iCveUsuRecibe=? where iEjercicio = ? AND iNumSolicitud = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveRequisito = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRecepcion"));
      lPStmt.setInt(2,vData.getInt("iCveUsuRecibe"));
      lPStmt.setInt(3,vData.getInt("iEjercicio"));
      lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(5,vData.getInt("iCveTramite"));
      lPStmt.setInt(6,vData.getInt("iCveModalidad"));
      lPStmt.setInt(7,vData.getInt("iCveRequisito"));
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

 public TVDinRep updateNoTienePNC(TVDinRep vData,Connection cnNested) throws
    DAOException{
  int iEjercicio, iNumSolicitud = 0;
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

    String cSql = "SELECT iEjercicio,iNumSolicitud " +
                            "FROM TRARegPNCEtapa " +
                            "WHERE iEjercicioPNC = " + vData.getInt("iEjercicioSel") + " " +
                            "AND iConsecutivoPNC = " + vData.getInt("iConsecutivoSel");
    Vector vcEjeSol = findByCustom("",cSql);
    if(vcEjeSol.size() > 0){
      TVDinRep vEjeSol = (TVDinRep) vcEjeSol.get(0);
      iEjercicio = vEjeSol.getInt("IEJERCICIO");
      iNumSolicitud = vEjeSol.getInt("INUMSOLICITUD");
      String lSQL =
          " UPDATE TRARegReqXTram SET " +
          " lTienePNC = 0 " +
          " WHERE iNumSolicitud = ? " +
          " AND iEjercicio = ? ";
      /*   " AND iCveTramite = ? " +
         " AND iCveModalidad = ? " +
         " AND iCveRequisito = ? "; */

      lPStmt = conn.prepareStatement(lSQL);
      /*     vData.addPK(vData.getString("iNumSolicitud"));
             vData.addPK(vData.getString("iEjercicio"));
             vData.addPK(vData.getString("iCveTramite"));
             vData.addPK(vData.getString("iCveModalidad"));
             vData.addPK(vData.getString("iCveRequisito")); */

//     lPStmt.setInt(1,vData.getInt("lTienePNC"));
      lPStmt.setInt(1,iNumSolicitud);
      lPStmt.setInt(2,iEjercicio);
      int iUpdate = lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
    }
  } catch(Exception ex){
    ex.printStackTrace();
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

public TVDinRep updateTienePNC(TVDinRep vData,Connection cnNested) throws
   DAOException{
 int iEjercicio, iNumSolicitud = 0;
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

   String cSql = "SELECT iEjercicio,iNumSolicitud " +
                           "FROM TRARegPNCEtapa " +
                           "WHERE iEjercicioPNC = " + vData.getInt("iEjercicio") + " " +
                           "AND iConsecutivoPNC = " + vData.getInt("iConsecutivo");
   Vector vcEjeSol = findByCustom("",cSql);
   if(vcEjeSol.size() > 0){
     TVDinRep vEjeSol = (TVDinRep) vcEjeSol.get(0);
     iEjercicio = vEjeSol.getInt("IEJERCICIO");
     iNumSolicitud = vEjeSol.getInt("INUMSOLICITUD");
     String lSQL =
         " UPDATE TRARegReqXTram SET " +
         " lTienePNC = 0 " +
         " WHERE iNumSolicitud = ? " +
         " AND iEjercicio = ? ";
     /*   " AND iCveTramite = ? " +
        " AND iCveModalidad = ? " +
        " AND iCveRequisito = ? "; */

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,iNumSolicitud);
     lPStmt.setInt(2,iEjercicio);
     int iUpdate = lPStmt.executeUpdate();

     if(cnNested == null){
       conn.commit();
     }
   }
 } catch(Exception ex){
   ex.printStackTrace();
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
	public TVDinRep insertInt(TVDinRep vData,Connection cnNested) throws
	DAOException{
	DbConnection dbConn = null;
	Connection conn = cnNested;
	PreparedStatement lPStmt = null;
	boolean lSuccess = true;
	String[] aRequisitos;
	String[] cRegistro;
	String cTramite;
	String cModalidad;
	TFechas fecha = new TFechas();
	int iSolicitud = 0;
	int iAux;
	int i;
	try{
	if(cnNested == null){
	  dbConn = new DbConnection(dataSourceName);
	  conn = dbConn.getConnection();
	  conn.setAutoCommit(false);
	  conn.setTransactionIsolation(2);
	}
	
	
	cRegistro = vData.getString("iAuxiliar").split(",");
	
	String lSQL =
	       "insert into TRARegReqXTram(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,dtRecepcion,iCveUsuRecibe) values (?,?,?,?,?,?,?)";
	
	 lPStmt = conn.prepareStatement(lSQL);
	 
	 Vector vcRequisitos = new Vector();
	 vcRequisitos = this.findByCustom(""," SELECT ICVEREQUISITO FROM TRAREQXMODTRAMITE " +
	 									" where ICVETRAMITE="+vData.getInt("iCveTramite")+
	 									" and icvemodalidad="+vData.getInt("iCveModalidad"));
	 
	 aRequisitos = vData.getString("cRequisitos").split(",");
	 for(i = 0;i < vcRequisitos.size();i++){
		 
		TVDinRep vReq = (TVDinRep) vcRequisitos.get(i); 
	    lPStmt.setInt(1,vData.getInt("iEjercicio"));
	    lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
	    lPStmt.setInt(3,vData.getInt("iCveTramite"));
	    lPStmt.setInt(4,vData.getInt("iCveModalidad"));
	    lPStmt.setInt(5,vReq.getInt("ICVEREQUISITO"));
	    lPStmt.setTimestamp(6,fecha.getThisTime());
	    lPStmt.setInt(7,vData.getInt("iCveUsuRecibe"));
	    lPStmt.executeUpdate();
	
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
	return vData;
	}
	}

}

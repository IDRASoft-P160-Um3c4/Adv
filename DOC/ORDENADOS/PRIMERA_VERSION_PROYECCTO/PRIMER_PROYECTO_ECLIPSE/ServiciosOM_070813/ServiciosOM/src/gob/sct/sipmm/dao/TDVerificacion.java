package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDTRARegSolicitud.java</p>
 * <p>Description: DAO de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDVerificacion extends DAOBase{
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
   * <p><b> insert into TRARegSolicitud(iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    java.sql.Date dtNulo = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null, lPStmt2 = null;;
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      
      String lSQL =
          " UPDATE TRARegReqXTram SET " +
          " lValido = ?, " +
          " dtNotificacion = ?, " +
          " dtOficio = ?, " +
          " cNumOficio = ?, " +
          " dtRecepcion = ?, " +
          " LRECNOTIFICADO = 0 " + 
          " WHERE iNumSolicitud = ? " +
          " AND iEjercicio = ? " +
          " AND iCveTramite = ? " +
          " AND iCveModalidad = ? ";
      if((vData.getString("dtNotificacion")).compareTo("") == 0 ||
         (vData.getString("dtNotificacion")).compareTo("") != 0 && vData.getInt("lValido") == 1){
        lSQL += " AND iCveRequisito = ? ";
      }else{
        lSQL += " AND lTienePNC = ? ";
      }
      //    " AND dtNotificacion = ?";

      lPStmt = conn.prepareStatement(lSQL);
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      lPStmt.setInt(1,vData.getInt("lValido"));
      lPStmt.setDate(2,vData.getDate("dtNotificacion"));
      lPStmt.setDate(3,vData.getDate("dtOficio"));
      lPStmt.setString(4,vData.getString("cNumOficio"));
      if((vData.getString("dtNotificacion")).compareTo("") != 0 && vData.getInt("lValido") == 1){
        lPStmt.setDate(2,dtNulo);
        lPStmt.setDate(3,dtNulo);
        lPStmt.setString(4,"");
      }
      if(vData.getString("dtRecepcion").equals("")){
        lPStmt.setDate(5,dtNulo);
      }else{
        lPStmt.setDate(5,vData.getDate("dtRecepcion"));
      }
      lPStmt.setInt(6,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(7,vData.getInt("iEjercicio"));
      lPStmt.setInt(8,vData.getInt("iCveTramite"));
      lPStmt.setInt(9,vData.getInt("iCveModalidad"));
      if((vData.getString("dtNotificacion")).compareTo("") == 0 ||
         (vData.getString("dtNotificacion")).compareTo("") != 0 && vData.getInt("lValido") == 1)
         lPStmt.setInt(10,vData.getInt("iCveRequisito"));
      else
         lPStmt.setInt(10,1);

      int iUpdate = lPStmt.executeUpdate();


      if(cnNested == null){
        conn.commit();

        Vector vcArea = this.findByCustom("","SELECT ICVEOFICINA,ICVEDEPARTAMENTO FROM TRAREGETAPASXMODTRAM where iejercicio="+vData.getString("iEjercicio")+" and INUMSOLICITUD="+vData.getString("iNumSolicitud")+" and ICVEETAPA=3");
        if(vcArea.size()>0){
      	  TVDinRep vArea = (TVDinRep) vcArea.get(0);
      	  vData.put("iCveOficina",vArea.getInt("ICVEOFICINA"));
      	  vData.put("iCveDepartamento",vArea.getInt("ICVEDEPARTAMENTO"));
      	  vData.put("iCveEtapa",3);
      	  vData.put("tsRegistro", fecha.getThisTime());
      	  vData.put("lAnexo",1);
      	  TDTRARegEtapasXModTram etapas = new TDTRARegEtapasXModTram();
      	  etapas.insertEtapa(vData, null, false);
      	  
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

  public TVDinRep validaNotificados(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    java.sql.Date dtNulo = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null, lPStmt2 = null;;
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL =
          " UPDATE TRARegReqXTram SET " +
          " lValido = 1 " +
          " WHERE iNumSolicitud = ? " +
          " AND iEjercicio = ? " +
          " AND iCveTramite = ? " +
          " AND iCveModalidad = ? "+
          " AND iCveRequisito = ? ";

      lPStmt = conn.prepareStatement(lSQL);
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      System.out.println("*****   iNumSolicitud    "+vData.getInt("iNumSolicitud"));
      System.out.println("*****   iEjercicio       "+vData.getInt("iEjercicio"));
      System.out.println("*****   iCveTramite      "+vData.getInt("iCveTramite"));
      System.out.println("*****   iCveModalidad    "+vData.getInt("iCveModalidad"));
      System.out.println("*****   iCveRequisito    "+vData.getInt("iCveRequisito"));

      lPStmt.setInt(1,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveRequisito"));
      lPStmt.executeUpdate();
      
      
      Vector vcOfic = this.findByCustom("", "SELECT ICVEOFICINA,ICVEDEPARTAMENTO FROM TRAREGETAPASXMODTRAM " +
      			                            "where iejercicio="+vData.getInt("iEjercicio")+
      			                            " and INUMSOLICITUD="+vData.getInt("iNumSolicitud")+
      			                            " and ICVEETAPA=3");
      TVDinRep vOfic = (TVDinRep) vcOfic.get(0);
      vData.put("iCveEtapa", 3);
      vData.put("iCveOficina", vOfic.getInt("ICVEOFICINA"));
      vData.put("iCveDepartamento", vOfic.getInt("ICVEDEPARTAMENTO"));
      vData.put("tsRegistro", fecha.getThisTime());
      vData.put("lAnexo", 1);
      TDTRARegEtapasXModTram etapa = new TDTRARegEtapasXModTram();
      etapa.insertEtapa(vData,conn,false);
      
      
      if(cnNested == null){
        conn.commit();
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
          " UPDATE TRARegReqXTram SET " +
          " lTienePNC = ? " +
          " WHERE iNumSolicitud = ? " +
          " AND iEjercicio = ? " +
          " AND iCveTramite = ? " +
          " AND iCveModalidad = ? " +
          " AND iCveRequisito = ? ";

      lPStmt = conn.prepareStatement(lSQL);
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveRequisito"));

      lPStmt.setInt(1,vData.getInt("lTienePNC"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iEjercicio"));
      lPStmt.setInt(4,vData.getInt("iCveTramite"));
      lPStmt.setInt(5,vData.getInt("iCveModalidad"));
      lPStmt.setInt(6,vData.getInt("iCveRequisito"));
      int iUpdate = lPStmt.executeUpdate();

      if(cnNested == null){
        conn.commit();
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

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update TRARegSolicitud set iCveTramite=?, iCveModalidad=?, iCveSolicitante=?, iCveRepLegal=?, cNomAutorizaRecoger=?, iCveUsuRegistra=?, tsRegistro=?, dtLimiteEntregaDocs=?, dtEstimadaEntrega=?, lPagado=?, dtEntrega=?, iCveUsuEntrega=?, lResolucionPositiva=?, lDatosPreregistro=?, iIdBien=?, iCveOficina=?, iCveDepartamento=?, iEjercicioCita=?, iIdCita=?, iCveForma=? where iEjercicio = ? AND iNumSolicitud = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
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
    PreparedStatement lPStmt2 = null;
    boolean lSuccess = true;
    TFechas tFechas = new TFechas();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      vData.addPK(vData.getString("iHDEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));

      String lSQL =
          " UPDATE GRLConcXVerProd SET " +
          " lConcAprobado = ?, " +
          " cObs = ? " +
          " WHERE iEjercicio = ? " +
          " AND iNumVerifica = ? " +
          " AND iCveProceso = ? " +
          " AND iCveProducto = ? " +
          " AND iCveConcVerifica = ? ";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lConcAprobado"));
      lPStmt.setInt(2,vData.getInt("cObs"));
      lPStmt.setInt(3,vData.getInt("iHDEjercicio"));
      lPStmt.setInt(4,vData.getInt("iNumVerifica"));
      lPStmt.setInt(5,vData.getInt("iCveProceso"));
      lPStmt.setInt(6,vData.getInt("iCveProducto"));
      lPStmt.setInt(7,vData.getInt("iCveConcVerifica"));
      int iUpdate = lPStmt.executeUpdate();

      lSQL =
          " UPDATE GRLVerifProduc SET " +
          " dtVerifica = ?, " +
          " lAprobada = ? " +
          " WHERE iEjercicio = ? " +
          " AND iNumVerifica = ? ";

      lPStmt2 = conn.prepareStatement(lSQL);
      lPStmt2.setDate(1,tFechas.TodaySQL());
      lPStmt2.setInt(2,vData.getInt("lAprobada"));
      lPStmt2.setInt(3,vData.getInt("iHDEjercicio"));
      lPStmt2.setInt(4,vData.getInt("iNumVerifica"));
      int iUpdate2 = lPStmt2.executeUpdate();

      if(cnNested == null){
        conn.commit();
      }
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
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(lPStmt2 != null){
          lPStmt2.close();
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
   * Actualiza al registro de TRARegReqXTram cuando encuentra que algún registro tiene PNC.
   * <p><b> update TRARegSolicitud set iCveTramite=?, iCveModalidad=?, iCveSolicitante=?, iCveRepLegal=?, cNomAutorizaRecoger=?, iCveUsuRegistra=?, tsRegistro=?, dtLimiteEntregaDocs=?, dtEstimadaEntrega=?, lPagado=?, dtEntrega=?, iCveUsuEntrega=?, lResolucionPositiva=?, lDatosPreregistro=?, iIdBien=?, iCveOficina=?, iCveDepartamento=?, iEjercicioCita=?, iIdCita=?, iCveForma=? where iEjercicio = ? AND iNumSolicitud = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep updateReq(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    TFechas fecha = new TFechas();
    boolean lSuccess = true;
    PreparedStatement lPStmt = null;

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      vData.addPK(vData.getString("iHDEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));

      String lSQL =
          " UPDATE TRARegReqXTram SET " +
          " lTienePNC = ?,LRECNOTIFICADO=0 " +
          " WHERE iEjercicio = ? " +
          " AND iNumSolicitud = ? " +
          " AND iCveTramite = ? " +
          " AND iCveModalidad = ? " +
          " AND iCveRequisito = ? ";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,1);
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(4,vData.getInt("iCveTramite"));
      lPStmt.setInt(5,vData.getInt("iCveModalidad"));
      lPStmt.setInt(6,vData.getInt("iCveRequisito"));
      lPStmt.executeUpdate();
      
      Vector vcArea = this.findByCustom("","SELECT ICVEOFICINA,ICVEDEPARTAMENTO FROM TRAREGETAPASXMODTRAM where iejercicio="+vData.getString("iEjercicio")+" and INUMSOLICITUD="+vData.getString("iNumSolicitud")+" and ICVEETAPA=3");
      if(vcArea.size()>0){
    	  TVDinRep vArea = (TVDinRep) vcArea.get(0);
    	  vData.put("iCveOficina",vArea.getInt("ICVEOFICINA"));
    	  vData.put("iCveDepartamento",vArea.getInt("ICVEDEPARTAMENTO"));
    	  vData.put("iCveEtapa",3);
    	  vData.put("tsRegistro", fecha.getThisTime());
    	  vData.put("lAnexo",1);
    	  TDTRARegEtapasXModTram etapas = new TDTRARegEtapasXModTram();
    	  etapas.insertEtapa(vData, null, false);
    	  
      }
      
      if(cnNested == null){
        conn.commit();
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

  /**
   * Actualiza al registro de TRARegReqXTram cuando encuentra que algún registro tiene PNC.
   * <p><b> update TRARegSolicitud set iCveTramite=?, iCveModalidad=?, iCveSolicitante=?, iCveRepLegal=?, cNomAutorizaRecoger=?, iCveUsuRegistra=?, tsRegistro=?, dtLimiteEntregaDocs=?, dtEstimadaEntrega=?, lPagado=?, dtEntrega=?, iCveUsuEntrega=?, lResolucionPositiva=?, lDatosPreregistro=?, iIdBien=?, iCveOficina=?, iCveDepartamento=?, iEjercicioCita=?, iIdCita=?, iCveForma=? where iEjercicio = ? AND iNumSolicitud = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep upOficio(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    boolean lSuccess = true;
    PreparedStatement lPStmt = null;

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "UPDATE GRLREGISTROPNC SET CNUMOFICIO=?, DTOFICIO=? " +
      "WHERE IEJERCICIO= ( SELECT MAX(IEJERCICIOPNC) as IEJERCICIOPNC FROM TRAREGPNCETAPA WHERE IEJERCICIO=? AND INUMSOLICITUD=?) " +
      "AND ICONSECUTIVOPNC= ( SELECT MAX(ICONSECUTIVOPNC) AS ICONSECUTIVOPNC FROM TRAREGPNCETAPA WHERE IEJERCICIO=? AND INUMSOLICITUD=?) ";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cNumOfReg"));
      lPStmt.setDate(2,vData.getDate("dtOfReg"));
      lPStmt.setInt(3,vData.getInt("iEjercicio"));
      lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(5,vData.getInt("iEjercicio"));
      lPStmt.setInt(6,vData.getInt("iNumSolicitud"));
      
      
      System.out.println(">>>  lSQL:  "+lSQL);
      System.out.println(">>>  cNumOfReg:       "+vData.getString("cNumOfReg"));
      System.out.println(">>>  dtOfReg:         "+vData.getDate("dtOfReg"));
      System.out.println(">>>  iEjercicio:      "+vData.getString("iEjercicio"));
      System.out.println(">>>  iNumSolicitud:   "+vData.getString("iNumSolicitud"));
      
      
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
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


  /**
 * Actualiza al registro de TRARegReqXTram cuando se Notifica al cliente.
 * <p><b> update TRARegReqXTram set dtNotificacion=? where iEjercicio = ? AND iNumSolicitud = ?  </b></p>
 * <p><b> Campos Llave: iEjercicio,iNumSolicitud, </b></p>
 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
 * @throws DAOException       - Excepción de tipo DAO
 * @return TVDinRep           - Entidad Modificada.
 */
public TVDinRep upNotificacion(TVDinRep vData,Connection cnNested) throws
  DAOException{
  DbConnection dbConn = null;
  Connection conn = cnNested;
  boolean lSuccess = true;
  PreparedStatement lPStmt = null;

  try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }
    vData.addPK(vData.getString("iEjercicio"));
    vData.addPK(vData.getString("iNumSolicitud"));
    String lSQL =
        " UPDATE TRARegReqXTram SET " +
        " dtNotificacion = ? " +
        " WHERE iEjercicio = ? " +
        " AND iNumSolicitud = ? " +
        " AND lTienePNC = 1 ";

    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setDate(1,vData.getDate("dtNotificacion"));
    lPStmt.setInt(2,vData.getInt("iEjercicio"));
    lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
    int iUpdate = lPStmt.executeUpdate();
    if(cnNested == null){
      conn.commit();
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

}


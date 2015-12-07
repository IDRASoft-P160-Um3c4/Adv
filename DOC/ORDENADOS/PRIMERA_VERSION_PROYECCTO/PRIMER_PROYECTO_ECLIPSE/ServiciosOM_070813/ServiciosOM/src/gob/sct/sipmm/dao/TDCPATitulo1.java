package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDCPATitulo.java</p>
 * <p>Description: DAO de la entidad CPATitulo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author lmorales
 * @version 1.0
 */
public class TDCPATitulo1 extends DAOBase{
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
   * <p><b> insert into CPATitulo(iCveTitulo,iCveTipoTitulo,cNumTitulo,cNumTituloAnterior,dtVigenciaTitulo,dtIniVigenciaTitulo,cZonaFedAfectadaTerrestre,cZonaFedAfectadaAgua,cObjetoTitulo,iCveUsoTitulo,cMontoInversion,lFirmado) values (?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTipoTitulo,iCveTitulo,iCveUsoTitulo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt1 = null;
    PreparedStatement lPStmt2 = null;
    PreparedStatement lPStmt3 = null;
    boolean lSuccess = true;
    int iCveTitulo = this.getiCveTitulo();
    int iOrden = this.getiOrden(iCveTitulo, vData.getInt("iCvePersona"));
    TFechas tFechas = new TFechas();
    int[] iUpdate = null;
    try{

      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL =" INSERT INTO CPATitulo(iCveTitulo, " +
                   "iCveTipoTitulo, " +
                   "cNumTitulo, " +
                   "cNumTituloAnterior, "+
                   "dtVigenciaTitulo, " +
                   "dtIniVigenciaTitulo, " +
                   "cZonaFedAfectadaTerrestre, "+
                   "cZonaFedAfectadaAgua, " +
                   "cObjetoTitulo, " +
                   "iCveUsoTitulo, " +
                   "cMontoInversion, " +
                   "dtPublicacionDOF, " +
                   "lFirmado, " +
                   "cZonaOpNoExcTerrestre, " +
                   "cZonaOpNoExcAgua, " +
                   "cZonaFedTotalTerrestre, " +
                   "cZonaFedTotalAgua, " +
                   "iCveClasificacion, " +
                   "iPropiedad, " +
                   "lApi, " +
                   "iCveTituloAnterior, "+
                   "iCveTipoPartAcc ) " +
                   " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(""+iCveTitulo);

      vData.put("iCveTitulo",iCveTitulo);

      lPStmt1 = conn.prepareStatement(lSQL);
      lPStmt1.setInt(1,iCveTitulo);
      lPStmt1.setInt(2,vData.getInt("iCveTipoTitulo"));
      lPStmt1.setString(3,vData.getString("cNumTitulo"));
      lPStmt1.setString(4,"");
      lPStmt1.setDate(5,vData.getDate("dtVigenciaTitulo"));
      lPStmt1.setDate(6,vData.getDate("dtIniVigenciaTitulo"));
      lPStmt1.setString(7,vData.getString("cZonaFedAfectadaTerrestre"));
      lPStmt1.setString(8,vData.getString("cZonaFedAfectadaAgua"));
      lPStmt1.setString(9,vData.getString("cObjetoTitulo"));
      lPStmt1.setInt(10,vData.getInt("iCveUsoTitulo"));
      lPStmt1.setString(11,vData.getString("cMontoInversion"));
      lPStmt1.setDate(12,vData.getDate("dtPublicacionDOF"));
      lPStmt1.setInt(13,1);
      lPStmt1.setString(14,vData.getString("cZonaOpNoExcTerrestre"));
      lPStmt1.setString(15,vData.getString("cZonaOpNoExcAgua"));
      lPStmt1.setString(16,vData.getString("cZonaFedTotalTerrestre"));
      lPStmt1.setString(17,vData.getString("cZonaFedTotalAgua"));
      lPStmt1.setInt(18,vData.getInt("iCveClasificacion"));
      lPStmt1.setInt(19,vData.getInt("iPropiedad"));
      lPStmt1.setInt(20,vData.getInt("lApi"));
      lPStmt1.setInt(21,vData.getInt("iCveTituloAnterior"));
      lPStmt1.setInt(22,vData.getInt("iCveTipoPartAcc"));

      lPStmt1.executeUpdate();
      lPStmt1.close();

      lSQL = "INSERT INTO CPASolTitulo(iCveTitulo, " +
             "iEjercicio, " +
             "iNumSolicitud, " +
             "iCveConceptoSolicitud ) " +
             "VALUES(?,?,?,?) ";

      lPStmt2 = conn.prepareStatement(lSQL);

      lPStmt2.setInt(1,iCveTitulo);
      lPStmt2.setInt(2,vData.getInt("iEjercicio"));
      lPStmt2.setInt(3,vData.getInt("iNumSolicitud"));
      lPStmt2.setInt(4,vData.getInt("iCveConceptoSolicitud"));

      lPStmt2.executeUpdate();
      lPStmt2.close();

      lSQL = "INSERT INTO CPATitular(iCveTitulo, " +
             "iCveTitular, " +
             "iOrden ) " +
             "VALUES (?,?,?) ";

      lPStmt3 = conn.prepareStatement(lSQL);
      lPStmt3.setInt(1,iCveTitulo);
      lPStmt3.setInt(2,vData.getInt("iCvePersona"));
      lPStmt3.setInt(3,iOrden);

      lPStmt3.executeUpdate();
      lPStmt3.close();


      if(cnNested == null){
        conn.commit();
      }

    }
    catch(Exception ex){
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
        if(lPStmt1 != null){
          lPStmt1.close();
        }
        if(lPStmt2 != null){
          lPStmt2.close();
        }
        if(lPStmt3 != null){
          lPStmt3.close();
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
   * <p><b> delete from CPATitulo where iCveTipoTitulo = ? AND iCveTitulo = ? AND iCveUsoTitulo = ?  </b></p>
   * <p><b> Campos Llave: iCveTipoTitulo,iCveTitulo,iCveUsoTitulo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested)throws
    DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    Statement lStmt1 = null;
    Statement lStmt2 = null;
    Statement lStmt3 = null;
    boolean lSuccess = true;
    String cMsg = "";
    try{
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
      lStmt1 = conn.createStatement();
      lStmt2 = conn.createStatement();
      lStmt3 = conn.createStatement();

      String lSQL =
          " DELETE FROM CPATitular "+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo")+
          " AND iCveTitular = "+vData.getInt("iCvePersona");
      lStmt1.executeUpdate(lSQL);

      lSQL =
          " DELETE FROM CPASolTitulo "+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo")+
          " AND iEjercicio = "+vData.getInt("iEjercicio")+
          " AND iNumSolicitud = "+vData.getInt("iNumSolicitud");
      lStmt2.executeUpdate(lSQL);

      lSQL =
          " DELETE FROM CPATitulo "+
          " WHERE iCveTitulo = "+vData.getInt("iCveTitulo");
      lStmt3.executeUpdate(lSQL);

      conn.commit();

    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = ""+sqle.getErrorCode();
    } catch(Exception ex){
      warn("delete",ex);
      try{
        conn.rollback();
      } catch(Exception e){
        fatal("delete.rollback",e);
      }
      lSuccess = false;
    } finally{
      try{
        if(lStmt1 != null){
          lStmt1.close();
        }
        if(lStmt2 != null){
          lStmt2.close();
        }
        if(lStmt3 != null){
          lStmt3.close();
        }

          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();

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
   * <p><b> update CPATitulo set cNumTituloAnterior=?, dtVigenciaTitulo=?, dtIniVigenciaTitulo=?, cZonaFedAfectadaTerrestre=?, cZonaFedAfectadaAgua=?, cObjetoTitulo=?, iCveUsoTitulo=?, cMontoInversion=?, lFirmado=? where iCveTipoTitulo = ? AND iCveTitulo = ? AND iCveUsoTitulo = ?  </b></p>
   * <p><b> Campos Llave: iCveTipoTitulo,iCveTitulo,iCveUsoTitulo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    boolean lSuccess = true;
    PreparedStatement lPstmt1 = null;
    PreparedStatement lPstmt2 = null;
    TFechas Fecha = new TFechas("44");
    try{

      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL1 = "update CPATitulo set " +
                     "iCveTipoTitulo = ? ," +
                     "cNumTitulo = ? ," +
                     "dtVigenciaTitulo = ?, " +
                     "dtIniVigenciaTitulo = ? ," +
                     "cZonaFedAfectadaTerrestre = ?, " +
                     "cZonaFedAfectadaAgua = ?, " +
                     "cObjetoTitulo = ?, " +
                     "iCveUsoTitulo = ?, " +
                     "cMontoInversion = ? ," +
                     "dtPublicacionDOF = ?, " +
                     "cZonaOpNoExcTerrestre = ?, " +
                     "cZonaOpNoExcAgua = ?, " +
                     "cZonaFedTotalTerrestre = ?, " +
                     "cZonaFedTotalAgua = ?, " +
                     "iCveClasificacion = ?, " +
                     "iPropiedad = ?, " +
                     "lApi = ?, " +
                     "iCveTituloAnterior = ?, " +
                     "iCveTipoPartAcc = ? " +
                     "where iCveTitulo = ? ";

      vData.addPK(vData.getString("iCveTitulo"));

      lPstmt1 = conn.prepareStatement(lSQL1);
      lPstmt1.setInt(1,vData.getInt("iCveTipoTitulo"));
      lPstmt1.setString(2,vData.getString("cNumTitulo"));
      lPstmt1.setDate(3,vData.getDate("dtVigenciaTitulo"));
      lPstmt1.setDate(4,vData.getDate("dtIniVigenciaTitulo"));
      lPstmt1.setString(5,vData.getString("cZonaFedAfectadaTerrestre"));
      lPstmt1.setString(6,vData.getString("cZonaFedAfectadaAgua"));
      lPstmt1.setString(7,vData.getString("cObjetoTitulo"));
      lPstmt1.setInt(8,vData.getInt("iCveUsoTitulo"));
      lPstmt1.setString(9,vData.getString("cMontoInversion"));
      lPstmt1.setDate(10,vData.getDate("dtPublicacionDOF"));
      lPstmt1.setString(11,vData.getString("cZonaOpNoExcTerrestre"));
      lPstmt1.setString(12,vData.getString("cZonaOpNoExcAgua"));
      lPstmt1.setString(13,vData.getString("cZonaFedTotalTerrestre"));
      lPstmt1.setString(14,vData.getString("cZonaFedTotalAgua"));
      lPstmt1.setInt(15,vData.getInt("iCveClasificacion"));
      lPstmt1.setInt(16,vData.getInt("iPropiedad"));
      lPstmt1.setInt(17,vData.getInt("lApi"));
      lPstmt1.setInt(18,vData.getInt("iCveTituloAnterior"));
      lPstmt1.setInt(19,vData.getInt("iCveTipoPartAcc"));
      lPstmt1.setInt(20,vData.getInt("iCveTitulo"));

      lPstmt1.executeUpdate();
      lPstmt1.close();

      String lSQL2 = "UPDATE CPASolTitulo SET "+
             "iCveConceptoSolicitud = ? " +
             "WHERE iCveTitulo = ? " +
             "AND iEjercicio = ? " +
             "AND iNumSolicitud = ? ";

      lPstmt2 = conn.prepareStatement(lSQL2);
      lPstmt2.setInt(1,vData.getInt("iCveConceptoSolicitud"));
      lPstmt2.setInt(2,vData.getInt("iCveTitulo"));
      lPstmt2.setInt(3,vData.getInt("iEjercicio"));
      lPstmt2.setInt(4,vData.getInt("iNumSolicitud"));

      lPstmt2.executeUpdate();
      lPstmt2.close();

      if(cnNested == null){
        conn.commit();
      }

    } catch(Exception ex){
      ex.printStackTrace();
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
        if(lPstmt1 != null){
          lPstmt1.close();
        }
        if(lPstmt2 != null){
          lPstmt2.close();
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

 public int getiCveTitulo()throws DAOException{
   DbConnection dbConn = null;
   Connection conn = null;
   Statement lStmt = null;
   ResultSet rs = null;
   int iCveTitulo = 0;
   try{
     dbConn = new DbConnection(dataSourceName);
     conn = dbConn.getConnection();
     lStmt = conn.createStatement();

     String lSQL = " select MAX(iCveTitulo) AS iCveTitulo from CPATitulo ";
     rs = lStmt.executeQuery(lSQL);
     while(rs.next()){
       iCveTitulo = rs.getInt("iCveTitulo");
     }
     iCveTitulo++;

   }
   catch(SQLException sqle){
     sqle.getErrorCode();
   }
   catch(Exception ex){
     ex.printStackTrace();
   }
   finally{
     try{
       if(lStmt != null){
         lStmt.close();
       }

         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();

     } catch(Exception ex2){
       warn("delete.close",ex2);
     }

     return iCveTitulo;
   }
 }

 public int getiOrden(int iCveTitulo, int iCveTitular)throws DAOException{
   DbConnection dbConn = null;
   Connection conn = null;
   Statement lStmt = null;
   ResultSet rs = null;
   int iOrden = 0;
   try{
     dbConn = new DbConnection(dataSourceName);
     conn = dbConn.getConnection();
     lStmt = conn.createStatement();

     String lSQL = " select MAX(iOrden) AS iOrden from CPATitular "+
                   " WHERE iCveTitulo = "+iCveTitulo+
                   " AND iCveTitular = "+iCveTitular;

     rs = lStmt.executeQuery(lSQL);
     while(rs.next()){
       iOrden = rs.getInt("iOrden");
     }
     iOrden++;

   }
   catch(SQLException sqle){
     sqle.getErrorCode();
   }
   catch(Exception ex){
     ex.printStackTrace();
   }
   finally{
     try{
       if(lStmt != null){
         lStmt.close();
       }

         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();

     } catch(Exception ex2){
       warn("delete.close",ex2);
     }

     return iOrden;
   }
 }

 public TVDinRep insertFrom(TVDinRep vData,Connection cnNested) throws DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt1 = null;
   PreparedStatement lPStmt2 = null;
   PreparedStatement lPStmt3 = null;
   boolean lSuccess = true;
   int iCveTitulo = this.getiCveTitulo();
   int iOrden = this.getiOrden(iCveTitulo, vData.getInt("iCvePersona"));
   TFechas tFechas = new TFechas();
   int[] iUpdate = null;
   try{

     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }

     String lSQL =" INSERT INTO CPATitulo(iCveTitulo, " +
                  "iCveTipoTitulo, " +
                  "cNumTitulo, " +
                  "cNumTituloAnterior, "+
                  "dtVigenciaTitulo, " +
                  "dtIniVigenciaTitulo, " +
                  "cZonaFedAfectadaTerrestre, "+
                  "cZonaFedAfectadaAgua, " +
                  "cObjetoTitulo, " +
                  "iCveUsoTitulo, " +
                  "cMontoInversion, " +
                  "dtPublicacionDOF, " +
                  "lFirmado, " +
                  "cZonaOpNoExcTerrestre, " +
                  "cZonaOpNoExcAgua, " +
                  "cZonaFedTotalTerrestre, " +
                  "cZonaFedTotalAgua, " +
                  "iCveClasificacion, " +
                  "iPropiedad, " +
                  "lApi, " +
                  "iCveTituloAnterior ) " +
                  "select " + iCveTitulo + "," +
                  "CPATituloAnt.iCveTipoTitulo, " +
                  "CPATituloAnt.cNumTitulo, " +
                  "CPATituloAnt.cNumTituloAnterior, "+
                  "CPATituloAnt.dtVigenciaTitulo, " +
                  "CPATituloAnt.dtIniVigenciaTitulo, " +
                  "CPATituloAnt.cZonaFedAfectadaTerrestre, "+
                  "CPATituloAnt.cZonaFedAfectadaAgua, " +
                  "CPATituloAnt.cObjetoTitulo, " +
                  "CPATituloAnt.iCveUsoTitulo, " +
                  "CPATituloAnt.cMontoInversion, " +
                  "CPATituloAnt.dtPublicacionDOF, " +
                  "CPATituloAnt.lFirmado, " +
                  "CPATituloAnt.cZonaOpNoExcTerrestre, " +
                  "CPATituloAnt.cZonaOpNoExcAgua, " +
                  "CPATituloAnt.cZonaFedTotalTerrestre, " +
                  "CPATituloAnt.cZonaFedTotalAgua, " +
                  "CPATituloAnt.iCveClasificacion, " +
                  "CPATituloAnt.iPropiedad, " +
                  "CPATituloAnt.lApi, " +
                  "CPATituloAnt.iCveTituloAnterior " +
                  "from CPATitulo CPATituloAnt " +
                  "where CPATituloAnt.iCveTitulo = ? " +
                  " ";

     lPStmt1 = conn.prepareStatement(lSQL);
     lPStmt1.setInt(1,vData.getInt("iCveTitulo"));

     lPStmt1.executeUpdate();
     lPStmt1.close();

     vData.addPK(""+iCveTitulo);

     lSQL = "INSERT INTO CPASolTitulo(iCveTitulo, " +
            "iEjercicio, " +
            "iNumSolicitud, " +
            "iCveConceptoSolicitud ) " +
            "VALUES(?,?,?,?) ";

     lPStmt2 = conn.prepareStatement(lSQL);

     lPStmt2.setInt(1,iCveTitulo);
     lPStmt2.setInt(2,vData.getInt("iEjercicio"));
     lPStmt2.setInt(3,vData.getInt("iNumSolicitud"));
     lPStmt2.setInt(4,vData.getInt("iCveConceptoSolicitud"));

     lPStmt2.executeUpdate();
     lPStmt2.close();

     lSQL = "INSERT INTO CPATitular(iCveTitulo, " +
            "iCveTitular, " +
            "iOrden ) " +
            "VALUES (?,?,?) ";

     lPStmt3 = conn.prepareStatement(lSQL);
     lPStmt3.setInt(1,iCveTitulo);
     lPStmt3.setInt(2,vData.getInt("iCvePersona"));
     lPStmt3.setInt(3,iOrden);

     lPStmt3.executeUpdate();
     lPStmt3.close();


     if(cnNested == null){
       conn.commit();
     }

   }
   catch(Exception ex){
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
       if(lPStmt1 != null){
         lPStmt1.close();
       }
       if(lPStmt2 != null){
         lPStmt2.close();
       }
       if(lPStmt3 != null){
         lPStmt3.close();
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

 public TVDinRep updArchivado(TVDinRep vData,Connection cnNested) throws DAOException{
   DbConnection dbConn = null;
   Connection conn = null;
   boolean lSuccess = true;
   PreparedStatement lPstmt1 = null;
   TFechas Fecha = new TFechas("44");
   try{

     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }

     String lSQL1 = "update CPATitulo set " +
                    "lArchivado = 1 " +
                    "where iCveTitulo = ? ";

     vData.addPK(vData.getString("iCveTitulo"));

     lPstmt1 = conn.prepareStatement(lSQL1);
     lPstmt1.setInt(1,vData.getInt("iCveTituloAnterior"));

     lPstmt1.executeUpdate();
     lPstmt1.close();

     if(cnNested == null){
       conn.commit();
     }

   } catch(Exception ex){
     ex.printStackTrace();
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
       if(lPstmt1 != null){
         lPstmt1.close();
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

 public TVDinRep updRevocado(TVDinRep vData,Connection cnNested) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    boolean lSuccess = true;
    PreparedStatement lPstmt1 = null;
    TFechas Fecha = new TFechas("44");
    try{

      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      String lSQL1 = "update CPATitulo set " +
                     "lRevocado = 1 " +
                     "where iCveTitulo = ? ";

      vData.addPK(vData.getString("iCveTitulo"));

      lPstmt1 = conn.prepareStatement(lSQL1);
      lPstmt1.setInt(1,vData.getInt("iCveTitulo"));
      lPstmt1.executeUpdate();
      lPstmt1.close();

      if(cnNested == null){
        conn.commit();
      }

    } catch(Exception ex){
      ex.printStackTrace();
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
        if(lPstmt1 != null){
          lPstmt1.close();
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

  public Vector findByInfTitulo(int iCveTitulo) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      StringBuffer cSQL = new StringBuffer();
      cSQL.append("select CPATitulo.iCveTitulo, ");
      cSQL.append("GRLPersona.iTipoPersona, ");
      cSQL.append("GRLPersona.cNomRazonSocial, ");
      cSQL.append("GRLPersona.cApPaterno, ");
      cSQL.append("GRLPersona.cApMaterno, ");
      cSQL.append("GRLPersona.cRFC, ");
      cSQL.append("CPAActaNacimiento.cNumActaNacimiento, ");
      cSQL.append("CPAActaNacimiento.dtNacimiento, ");
      cSQL.append("CPAActaNacimiento.cNumJuzgado, ");
      cSQL.append("Juez.cNomRazonSocial cNomJuez, ");
      cSQL.append("Juez.cApPaterno cApPatJuez, ");
      cSQL.append("Juez.cApMaterno cApMatJuez, ");
      cSQL.append("CPAActaNacimiento.cNumLibroActa, ");
      cSQL.append("CPAActaNacimiento.cNumFojaActa, ");

      cSQL.append("GRLDomicilio.iCveDomicilio, ");
      cSQL.append("GRLDomicilio.cCalle, ");
      cSQL.append("GRLDomicilio.cNumExterior, ");
      cSQL.append("GRLDomicilio.cNumInterior, ");
      cSQL.append("GRLDomicilio.cColonia, ");
      cSQL.append("GRLDomicilio.cCodPostal, ");
      cSQL.append("GRLDomicilio.cTelefono, ");
      cSQL.append("GRLPais.cNombre as cNomPais, ");
      cSQL.append("GRLEntidadFed.cNombre as cNomEntidadFed, ");
      cSQL.append("GRLMunicipio.cNombre as cNomMunicipio, ");
      cSQL.append("GRLLocalidad.cNombre as cNomLocalidad, ");
      cSQL.append("CPATitulo.cNumTitulo, ");
      cSQL.append("CPATitulo.cNumTituloAnterior, ");
      cSQL.append("CPATitulo.dtIniVigenciaTitulo, ");
      cSQL.append("CPATitulo.cZonaFedAfectadaTerrestre, ");
      cSQL.append("CPATitulo.cZonaFedAfectadaAgua, ");
      cSQL.append("CPATitulo.cObjetoTitulo, ");
      cSQL.append("CPATitulo.cMontoInversion, ");
      cSQL.append("CPATitulo.dtPublicacionDOF, ");
      cSQL.append("CPATitulo.iCveClasificacion, ");
      cSQL.append("CPAUsoTitulo.iCveUsoTitulo, ");
      cSQL.append("CPATitulo.iCveTituloAnterior, ");
      cSQL.append("CPATitulo.dtVigenciaTitulo, ");
      cSQL.append("CPAUsoTitulo.cDscUsoTitulo, ");
      cSQL.append("CPATitular.iCveTitular, ");
      cSQL.append("GRLDomicilio.ICVEDOMICILIO, ");
      cSQL.append("CPATipoTitulo.cDscTipoTitulo, ");
      cSQL.append("CPATitulo.iPropiedad ");
      cSQL.append("from CPATitulo ");
      cSQL.append("join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo ");
      cSQL.append("join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo ");
      cSQL.append("join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo ");
      cSQL.append("join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular ");
      cSQL.append("left join CPAActaNacimiento on CPAActaNacimiento.iCvePersona = GRLPersona.iCvePersona ");
      cSQL.append("left join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona ");
      cSQL.append("and GRLDomicilio.lPredeterminado = 1 ");
      cSQL.append("left join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais ");
      cSQL.append("left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais ");
      cSQL.append("and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ");
      cSQL.append("left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais ");
      cSQL.append("and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ");
      cSQL.append("and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio ");
      cSQL.append("left join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais ");
      cSQL.append("and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ");
      cSQL.append("and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio ");
      cSQL.append("and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad ");
      cSQL.append("left join GRLPersona Juez on Juez.iCvePersona = CPAActaNacimiento.iCveJuez ");
      cSQL.append("where CPATitulo.iCveTitulo = " + iCveTitulo);

      vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }

 public Vector findByInfTituloUbicacion(int iCveTitulo) throws DAOException{
   Vector vcRecords = new Vector();
   cError = "";
   try{
     StringBuffer cSQL = new StringBuffer();
     cSQL.append("select CPATituloUbicacion.lDentroRecintoPort, ");
     cSQL.append("CPATituloUbicacion.cCalleTitulo, ");
     cSQL.append("CPATituloUbicacion.cKm, ");
     cSQL.append("CPATituloUbicacion.cColoniaTitulo, ");
     cSQL.append("GRLPuerto.cDscPuerto, ");
     cSQL.append("GRLPais.cNombre as cDscPais, ");
     cSQL.append("GRLEntidadFed.cNombre as cDscEntidadFed, ");
     cSQL.append("GRLMunicipio.cNombre as cDscMunicipio, ");
     cSQL.append("GRLLocalidad.cNombre as cDscLocalidad, ");
     cSQL.append("GRLPuerto.iCvePuerto ");
     cSQL.append("from CPATitulo ");
     cSQL.append("join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo ");
     cSQL.append("left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto ");
     cSQL.append("left join GRLPais on GRLPais.iCvePais = CPATituloUbicacion.iCvePais ");
     cSQL.append("left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloUbicacion.iCvePais ");
     cSQL.append("and GRLEntidadFed.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed ");
     cSQL.append("left join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloUbicacion.iCvePais ");
     cSQL.append("and GRLMunicipio.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed ");
     cSQL.append("and GRLMunicipio.iCveMunicipio = CPATituloUbicacion.iCveMunicipio ");
     cSQL.append("left join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloUbicacion.iCvePais ");
     cSQL.append("and GRLLocalidad.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed ");
     cSQL.append("and GRLLocalidad.iCveMunicipio = CPATituloUbicacion.iCveMunicipio ");
     cSQL.append("and GRLLocalidad.iCveLocalidad = CPATituloUbicacion.iCveLocalidad ");
     cSQL.append("where CPATitulo.iCveTitulo = " + iCveTitulo);

     vcRecords = this.FindByGeneric("",cSQL.toString(),dataSourceName);

   } catch(Exception e){
     cError = e.toString();
   } finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vcRecords;
   }
  }
}

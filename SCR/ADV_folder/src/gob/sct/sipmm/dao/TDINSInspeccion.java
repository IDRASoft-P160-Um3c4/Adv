package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDINSInspeccion.java</p>
 * <p>Description: DAO de la entidad INSInspeccion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author cabrito
 * @version 1.0
 */
public class TDINSInspeccion extends DAOBase{
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
   * <p><b> insert into INSInspeccion(iCveInspeccion,iCveInspProg,tsIniInsp,tsFinInsp,iCveOficina,iCvePuerto,cObses,lRegistroFinalizado) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveInspeccion, </b></p>
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
    int iInspeccion=0;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      }
      String lSQL =
          "insert into INSInspeccion(iCveInspeccion,iCveInspProg,dtIniInsp,dtFinInsp,iCveOficina,iCvePuerto,cObses,lRegistroFinalizado) values (?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveInspeccion) AS iCveInspeccion from INSInspeccion");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveInspeccion",vUltimo.getInt("iCveInspeccion") + 1);
      }else
         vData.put("iCveInspeccion",1);
      vData.addPK(vData.getString("iCveInspeccion"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveInspeccion"));
      iInspeccion = vData.getInt("iCveInspeccion");
      lPStmt.setInt(2,vData.getInt("iCveInspProg"));
      lPStmt.setDate(3,vData.getDate("dtIniInsp"));
      lPStmt.setDate(4,vData.getDate("dtFinInsp"));
      lPStmt.setInt(5,vData.getInt("iCveOficina1"));
      lPStmt.setInt(6,vData.getInt("iCvePuerto"));
      lPStmt.setString(7,vData.getString("cObses"));
      lPStmt.setInt(8,vData.getInt("lRegistroFinalizado"));
      lPStmt.executeUpdate();
      lPStmt.close();
      conn.commit();


      String cPendientes =
          "SELECT distinct(cd.ICVECODDEFICIENCIA) " +
          "FROM INSDEFICXINSP DI " +
          "JOIN INSCODDEFICIENCIA CD ON CD.ICVECODDEFICIENCIA = DI.ICVECODDEFICIENCIA " +
          "JOIN INSMEDXDEFICIENCIA MD ON MD.ICVEINSPECCION = DI.ICVEINSPECCION AND MD.ICVECODDEFICIENCIA = DI.ICVECODDEFICIENCIA " +
          "JOIN INSMEDADOPTADAS MA ON MA.ICODIGOMEDIDA = MD.ICODIGOMEDIDA " +
          "JOIN INSINSPECCION I ON I.ICVEINSPECCION = DI.ICVEINSPECCION " +
          "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
          "JOIN VEHEMBARCACION E ON E.ICVEVEHICULO = PI.ICVEEMBARCACION " +
          "WHERE E.ICVEVEHICULO = "+vData.getInt("iCveEmbarcacion")+" AND MA.ICODIGOMEDIDA > 3 AND I.ICVEINSPECCION IN ( " +
          "SELECT MAX(ICVEINSPECCION) FROM INSINSPECCION I " +
          "JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
          "wHERE PI.ICVEEMBARCACION = "+vData.getInt("iCveEmbarcacion")+" and i.LREGISTROFINALIZADO = 1)  ";
      Vector vcPendientes = findByCustom("",cPendientes);
      if(vcPendientes.size() > 0){
        for(int i=0;i<vcPendientes.size();i++){
          String cAgregar =
              "insert into INSDEFICXINSP (ICVEINSPECCION,ICVECODDEFICIENCIA) values (?,?)";
          TVDinRep vPendientes = (TVDinRep) vcPendientes.get(i);
          lPStmt1 = conn.prepareStatement(cAgregar);
          lPStmt1.setInt(1,iInspeccion);
          lPStmt1.setInt(2,vPendientes.getInt("ICVECODDEFICIENCIA"));
          lPStmt1.executeUpdate();
          lPStmt1.close();
          conn.commit();

        }
      }

      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveInspeccion",vUltimo.getInt("iCveInspeccion") + 1);
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
   * <p><b> delete from INSInspeccion where iCveInspeccion = ?  </b></p>
   * <p><b> Campos Llave: iCveInspeccion, </b></p>
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
      String lSQL = "delete from INSInspeccion where iCveInspeccion = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveInspeccion"));

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
   * <p><b> update INSInspeccion set iCveInspProg=?, tsIniInsp=?, tsFinInsp=?, iCveOficina=?, iCvePuerto=?, cObses=?, lRegistroFinalizado=? where iCveInspeccion = ?  </b></p>
   * <p><b> Campos Llave: iCveInspeccion, </b></p>
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
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update INSInspeccion set iCveInspProg=?, dtIniInsp=?, dtFinInsp=?, iCveOficina=?, iCvePuerto=?, cObses=?, lRegistroFinalizado=? where iCveInspeccion = ? ";

      vData.addPK(vData.getString("iCveInspeccion"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveInspProg"));
      lPStmt.setDate(2,vData.getDate("dtIniInsp"));
      lPStmt.setDate(3,vData.getDate("dtFinInsp"));
      lPStmt.setInt(4,vData.getInt("iCveOficina1"));
      lPStmt.setInt(5,vData.getInt("iCvePuerto"));
      lPStmt.setString(6,vData.getString("cObses"));
      lPStmt.setInt(7,vData.getInt("lRegistroFinalizado"));
      lPStmt.setInt(8,vData.getInt("iCveInspeccion"));
      lPStmt.executeUpdate();

      String lSQL2 = "UPDATE INSPROGINSP SET ICVEEMBARCACION=? WHERE ICVEINSPPROG=?";
      if(vData.getInt("iCveEmbarcacion")>0 ){
    	  lPStmt2 = conn.prepareStatement(lSQL2);
    	  lPStmt2.setInt(1, vData.getInt("iCveEmbarcacion"));
    	  lPStmt2.setInt(2, vData.getInt("iCveInspProg"));
    	  lPStmt2.executeUpdate();
      }
      
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
   * <p><b> update INSInspeccion set iCveInspProg=?, tsIniInsp=?, tsFinInsp=?, iCveOficina=?, iCvePuerto=?, cObses=?, lRegistroFinalizado=? where iCveInspeccion = ?  </b></p>
   * <p><b> Campos Llave: iCveInspeccion, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep updDetInspeccion(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = " update INSInspeccion set cObses=?,               " +
                    "                          dtUltDiqueSeco=?,       " +
                    "                          iNumTripInspec=?,       " +
                    "                          iNumPasInspec=?,        " +
                    "                          lRegistroFinalizado=?   " +
                    " where iCveInspeccion = ? ";

      vData.addPK(vData.getString("iCveInspeccion"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cObses"));
      lPStmt.setDate(2,vData.getDate("dtUltDiqueSeco"));
      lPStmt.setInt(3,vData.getInt("iNumTripInspec"));
      lPStmt.setInt(4,vData.getInt("iNumPasInspec"));
      lPStmt.setInt(5,0);
      lPStmt.setInt(6,vData.getInt("iCveInspeccion"));
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

 public TVDinRep updCertificado(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String lSQL2=
         "UPDATE VEHEMBARCACION SET " +
         "CNOMEMBARCACION=?,CNUMOMI=?, ICVETIPONAVEGA=?, ICVETIPOSERV=?,DTCONSTRUCCION=?, " +
         "DESLORA=?, IUNIMEDESLORA=?, DMANGA=?, IUNIMEDESLORA=?, DPUNTAL=?, IUNIMEDPUNTAL=?, " +
         "DARQUEOBRUTO=?, IUNIMEDARQUEOBRUTO=?, DARQUEONETO=?, IUNIMEDARQUEONETO=? " +
         "WHERE ICVEVEHICULO=? ";

     String lSQL = " update INSInspeccion     " +
                   " set iNumTripInspec = ?,  " +
                   "     iNumPasInspec = ?,   " +
                   "     dtUltDiqueSeco = ?   " +
                   " where iCveInspeccion = ? ";

     vData.addPK(vData.getString("iCveInspeccion"));

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("cNumTripulantes"));
     lPStmt.setInt(2,vData.getInt("cNumPasajeros"));
     lPStmt.setDate(3,vData.getDate("dtUltDiqueSeco"));
     lPStmt.setInt(4,vData.getInt("iCveInspeccion"));
     lPStmt1 = conn.prepareStatement(lSQL2);
     lPStmt1.setString(1,vData.getString("cNomEmbarcacion"));
     lPStmt1.setString(2,vData.getString("cOMI"));
     lPStmt1.setInt(3,vData.getInt("iCvetipoNavega"));
     lPStmt1.setInt(4,vData.getInt("iCveTipoServicio"));
     lPStmt1.setDate(5,vData.getDate("dtConstruccion"));
     lPStmt1.setDouble(6,vData.getDouble("dEslora"));
     lPStmt1.setInt(7,vData.getInt("iUniMedEslora"));
     lPStmt1.setDouble(8,vData.getDouble("dManga"));
     lPStmt1.setInt(9,vData.getInt("iUniMedManga"));
     lPStmt1.setDouble(10,vData.getDouble("dPuntal"));
     lPStmt1.setInt(11,vData.getInt("iUniMedPuntal"));
     lPStmt1.setDouble(12,vData.getDouble("dArqueoBruto"));
     lPStmt1.setInt(13,vData.getInt("iUniMedArqueoBruto"));
     lPStmt1.setDouble(14,vData.getDouble("ArqueoNeto"));
     lPStmt1.setInt(15,vData.getInt("iUniMedArqueoNeto"));





     lPStmt.executeUpdate();

     lPStmt1 = conn.prepareStatement(lSQL2);
     lPStmt.setString(1,vData.getString("cNomEmbarcacion"));
     lPStmt.setString(1,vData.getString("cOMI"));

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
  public TVDinRep finInspeccion(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    int iInspeccion = 0;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      Vector vcInspeccion = this.findByCustom("","SELECT ICVEINSPECCION FROM INSINSPECCION I WHERE I.ICVEINSPPROG = "+vData.getInt("iCveInspProg"));
      if(vcInspeccion.size()>0){
        TVDinRep vInspeccion = (TVDinRep) vcInspeccion.get(0);
        iInspeccion = vInspeccion.getInt("ICVEINSPECCION");
      }
      String lSQL = " update INSInspeccion set lRegistroFinalizado = 1 " +
                    " where iCveInspeccion = ? ";

      vData.addPK(vData.getString("iCveInspeccion"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,iInspeccion);
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

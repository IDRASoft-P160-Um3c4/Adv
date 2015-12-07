package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLReporte1A.java</p>
 * <p>Description: DAO de la entidad GRLReporte</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ijimenez
 * @version 1.0
 */
public class TDGRLReporte1A extends DAOBase{
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
   * <p><b> insert into GRLReporte(iCveSistema,iCveModulo,iNumReporte,cNomReporte,lExcel,lWord,lPDF,lMultiPart,cPlantillaExcel,cPlantillaWord,cPlantillaPDF,cDAOEjecutar,cMetodoExcel,cMetodoWord,cMetodoPDF,iCveFormatoFiltro,lAutoImprimir,iNumCopias,lMostrarAplicacion,lParametroModificable,lRequiereFolio) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveSistema,iCveModulo,iNumReporte, </b></p>
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
    final int   ICVESISTEMA = 44;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLReporte(iCveSistema,iCveModulo,iNumReporte,cNomReporte,lExcel,lWord,lPDF,lMultiPart,cPlantillaExcel,cPlantillaWord,cPlantillaPDF,cDAOEjecutar,cMetodoExcel,cMetodoWord,cMetodoPDF,iCveFormatoFiltro,lAutoImprimir,iNumCopias,lMostrarAplicacion,lParametroModificable,lRequiereFolio) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//System.out.print("hellooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
      //AGREGAR AL ULTIMO ...
      vData.put("iCveSistema",ICVESISTEMA);
      Vector vcData = findByCustom("","select MAX(iNumReporte) AS iNumReporte from GRLReporte where iCveSistema = "+vData.getInt("iCveSistema")+ " AND iCveModulo = "+vData.getInt("iCveModulo"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iNumReporte",vUltimo.getInt("iNumReporte") + 1);
      }else
         vData.put("iNumReporte",1);
      vData.addPK(vData.getString("iNumReporte"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveSistema"));
      lPStmt.setInt(2,vData.getInt("iCveModulo"));
      lPStmt.setInt(3,vData.getInt("iNumReporte"));
      lPStmt.setString(4,vData.getString("cNomReporte"));
      lPStmt.setInt(5,vData.getInt("lExcel"));
      lPStmt.setInt(6,vData.getInt("lWord"));
      lPStmt.setInt(7,vData.getInt("lPDF"));
      lPStmt.setInt(8,vData.getInt("lMultiPart"));
      lPStmt.setString(9,vData.getString("cPlantillaExcel"));
      lPStmt.setString(10,vData.getString("cPlantillaWord"));
      lPStmt.setString(11,vData.getString("cPlantillaPDF"));
      lPStmt.setString(12,vData.getString("cDAOEjecutar"));
      lPStmt.setString(13,vData.getString("cMetodoExcel"));
      lPStmt.setString(14,vData.getString("cMetodoWord"));
      lPStmt.setString(15,vData.getString("cMetodoPDF"));
      lPStmt.setInt(16,vData.getInt("iCveFormatoFiltro"));
      lPStmt.setInt(17,vData.getInt("lAutoImprimir"));
      lPStmt.setInt(18,vData.getInt("iNumCopias"));
      lPStmt.setInt(19,vData.getInt("lMostrarAplicacion"));
      lPStmt.setInt(20,vData.getInt("lParametroModificable"));
      lPStmt.setInt(21,vData.getInt("lRequiereFolio"));
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
   * <p><b> delete from GRLReporte where iCveSistema = ? AND iCveModulo = ? AND iNumReporte = ?  </b></p>
   * <p><b> Campos Llave: iCveSistema,iCveModulo,iNumReporte, </b></p>
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
      String lSQL = "delete from GRLReporte where iCveSistema = ? AND iCveModulo = ? AND iNumReporte = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveSistema"));
      lPStmt.setInt(2,vData.getInt("iCveModulo"));
      lPStmt.setInt(3,vData.getInt("iNumReporte"));

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
   * <p><b> update GRLReporte set cNomReporte=?, lExcel=?, lWord=?, lPDF=?, lMultiPart=?, cPlantillaExcel=?, cPlantillaWord=?, cPlantillaPDF=?, cDAOEjecutar=?, cMetodoExcel=?, cMetodoWord=?, cMetodoPDF=?, iCveFormatoFiltro=?, lAutoImprimir=?, iNumCopias=?, lMostrarAplicacion=?, lParametroModificable=?, lRequiereFolio=? where iCveSistema = ? AND iCveModulo = ? AND iNumReporte = ?  </b></p>
   * <p><b> Campos Llave: iCveSistema,iCveModulo,iNumReporte, </b></p>
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
      String lSQL = "update GRLReporte set cNomReporte=?, lExcel=?, lWord=?, lPDF=?, lMultiPart=?, cPlantillaExcel=?, cPlantillaWord=?, cPlantillaPDF=?, cDAOEjecutar=?, cMetodoExcel=?, cMetodoWord=?, cMetodoPDF=?, iCveFormatoFiltro=?, lAutoImprimir=?, iNumCopias=?, lMostrarAplicacion=?, lParametroModificable=?, lRequiereFolio=? where iCveSistema = ? AND iCveModulo = ? AND iNumReporte = ? ";

      vData.addPK(vData.getString("iCveSistema"));
      vData.addPK(vData.getString("iCveModulo"));
      vData.addPK(vData.getString("iNumReporte"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cNomReporte"));
      lPStmt.setInt(2,vData.getInt("lExcel"));
      lPStmt.setInt(3,vData.getInt("lWord"));
      lPStmt.setInt(4,vData.getInt("lPDF"));
      lPStmt.setInt(5,vData.getInt("lMultiPart"));
      lPStmt.setString(6,vData.getString("cPlantillaExcel"));
      lPStmt.setString(7,vData.getString("cPlantillaWord"));
      lPStmt.setString(8,vData.getString("cPlantillaPDF"));
      lPStmt.setString(9,vData.getString("cDAOEjecutar"));
      lPStmt.setString(10,vData.getString("cMetodoExcel"));
      lPStmt.setString(11,vData.getString("cMetodoWord"));
      lPStmt.setString(12,vData.getString("cMetodoPDF"));
      lPStmt.setInt(13,vData.getInt("iCveFormatoFiltro"));
      lPStmt.setInt(14,vData.getInt("lAutoImprimir"));
      lPStmt.setInt(15,vData.getInt("iNumCopias"));
      lPStmt.setInt(16,vData.getInt("lMostrarAplicacion"));
      lPStmt.setInt(17,vData.getInt("lParametroModificable"));
      lPStmt.setInt(18,vData.getInt("lRequiereFolio"));
      lPStmt.setInt(19,vData.getInt("iCveSistema"));
      lPStmt.setInt(20,vData.getInt("iCveModulo"));
      lPStmt.setInt(21,vData.getInt("iNumReporte"));
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

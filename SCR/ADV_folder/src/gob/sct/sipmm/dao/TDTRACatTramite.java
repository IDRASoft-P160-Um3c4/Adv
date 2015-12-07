package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDTRACatTramite.java</p>
 * <p>Description: DAO de la entidad TRACatTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDTRACatTramite extends DAOBase{
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
    Vector vcRecords = new Vector(), vcResultado = new Vector();
    String cTipoBienesBuscar = VParametros.getPropEspecifica("TipoBienesBuscar"),
           cTipoBienesBuscarFunciones = VParametros.getPropEspecifica("TipoBienesBuscarFunciones");
    TVDinRep dActual;
    String[] aTipoBienesBuscar = cTipoBienesBuscar.split(","),
             aTipoBienesFunciones = cTipoBienesBuscarFunciones.split(",");
    String cActual;
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      if (vcRecords != null && vcRecords.size()>0){
        for (int i=0; i< vcRecords.size(); i++){
          dActual = (TVDinRep) vcRecords.get(i);
          cActual = dActual.getString("cBienBuscar");
          if(cActual!=null){
            if(aTipoBienesBuscar != null && aTipoBienesBuscar.length>0){
              for(int j = 0;j < aTipoBienesBuscar.length; j++){
                if (aTipoBienesBuscar[j].equalsIgnoreCase(cActual)){
                  dActual.put("cFuncionEjecutar", (aTipoBienesFunciones[j] != null)?aTipoBienesFunciones[j]:"");
                  break;
                }
              }
            }else
              dActual.put("cFuncionEjecutar", "");
          }else
            dActual.put("cFuncionEjecutar", "");
          vcResultado.addElement(dActual);
        }
      }
      return vcResultado;
    }
 }
  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into TRACatTramite(iCveTramite,cCveInterna,cDscTramite,cDscBreve,lReqFirmaDigital,lVigente) values (?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTramite, </b></p>
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
          "insert into TRACatTramite(iCveTramite,cCveInterna,cDscTramite,cDscBreve,lReqFirmaDigital,lVigente,cBienBuscar,cObjetivoTramite,cComprobante,cNotas,lImprimeManual,iCveModulo) values (?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveTramite) AS iCveTramite from TRACatTramite");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveTramite",vUltimo.getInt("iCveTramite") + 1);
      }else
         vData.put("iCveTramite",1);
      vData.addPK(vData.getString("iCveTramite"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setString(2,vData.getString("cCveInterna"));
      lPStmt.setString(3,vData.getString("cDscTramite"));
      lPStmt.setString(4,vData.getString("cDscBreve"));
      lPStmt.setInt(5,vData.getInt("lReqFirmaDigital"));
      lPStmt.setInt(6,vData.getInt("lVigente"));//cBienBuscar
      lPStmt.setString(7,vData.getString("cBienBuscar"));
      lPStmt.setString(8,vData.getString("cObjTramite"));
      lPStmt.setString(9,vData.getString("cComprobante"));
      lPStmt.setString(10,vData.getString("cNotas"));
      lPStmt.setInt(11,vData.getInt("lImprimeManual"));
      lPStmt.setInt(12,vData.getInt("iCveModulo"));
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
   * <p><b> delete from TRACatTramite where iCveTramite = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite, </b></p>
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
      String lSQL = "delete from TRACatTramite where iCveTramite = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTramite"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = super.getSQLMessages(sqle);
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
   * <p><b> update TRACatTramite set cCveInterna=?, cDscTramite=?, cDscBreve=?, lReqFirmaDigital=?, lVigente=? where iCveTramite = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite, </b></p>
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
      String lSQL = "update TRACatTramite set cCveInterna=?, cDscTramite=?, cDscBreve=?, lReqFirmaDigital=?, lVigente=?, cBienBuscar=?, cObjetivoTramite=?, cComprobante=?, cNotas=?,lImprimeManual=?,iCveModulo=? where iCveTramite = ? ";

      vData.addPK(vData.getString("iCveTramite"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cCveInterna"));
      lPStmt.setString(2,vData.getString("cDscTramite"));
      lPStmt.setString(3,vData.getString("cDscBreve"));
      lPStmt.setInt(4,vData.getInt("lReqFirmaDigital"));
      lPStmt.setInt(5,vData.getInt("lVigente"));
      lPStmt.setString(6,vData.getString("cBienBuscar"));
      lPStmt.setString(7,vData.getString("cObjTramite"));
      lPStmt.setString(8,vData.getString("cComprobante"));
      lPStmt.setString(9,vData.getString("cNotas"));
      lPStmt.setInt(10,vData.getInt("lImprimeManual"));
      lPStmt.setInt(11,vData.getInt("iCveModulo"));
      lPStmt.setInt(12,vData.getInt("iCveTramite"));
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

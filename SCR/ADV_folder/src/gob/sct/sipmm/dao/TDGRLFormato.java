package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLFormato.java</p>
 * <p>Description: DAO de la entidad GRLFormato</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Leopoldo Beristain González
 * @version 1.0
 */
public class TDGRLFormato extends DAOBase{
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
   * <p><b> insert into GRLFormato(iCveFormato,cDscFormato,cTitulo,lVigente,dtIniVigencia,cTablaBusca,cCampoLlaveBusca,iNumColumnas) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveFormato, </b></p>
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
          "insert into GRLFormato(iCveFormato,cDscFormato,cTitulo,lVigente,dtIniVigencia,cTablaBusca,cCampoLlaveBusca,iNumColumnas) values (?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveFormato) AS iCveFormato from GRLFormato");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveFormato",vUltimo.getInt("iCveFormato") + 1);
      }else
         vData.put("iCveFormato",1);
      vData.addPK(vData.getString("iCveFormato"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveFormato"));
      lPStmt.setString(2,vData.getString("cDscFormato"));
      lPStmt.setString(3,vData.getString("cTitulo"));
      lPStmt.setInt(4,vData.getInt("lVigente"));
      lPStmt.setDate(5,vData.getDate("dtIniVigencia"));
      lPStmt.setString(6,vData.getString("cTablaBusca"));
      lPStmt.setString(7,vData.getString("cCampoLlaveBusca"));
      lPStmt.setInt(8,vData.getInt("iNumColumnas"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

      TVDinRep vDataAtrib = new TVDinRep();
      TDGRLConfigFormato dConfigFormato = new TDGRLConfigFormato();
      vDataAtrib.put("iCveFormatoAux", vData.getInt("iCveFormato"));
      vDataAtrib.put("iCveAtributo", 0);
      vDataAtrib.put("iOrden",0);
      vDataAtrib.put("cDscAtributo","NO ESPECIFICADO");
      vDataAtrib.put("cEtiquetaAnterior","");
      vDataAtrib.put("iCveTipoRespuesta",10);
      vDataAtrib.put("cEtiquetaPosterior","");
      vDataAtrib.put("lObligatorio",0);
      vDataAtrib.put("cValorIniOmision","");
      vDataAtrib.put("cValorFinOmision","");
      vDataAtrib.put("iMaxLongitud",2);
      vDataAtrib.put("cNomCampoPantalla","");
      vDataAtrib.put("cTablaMapeo","");
      vDataAtrib.put("cCampoMapeo","");
      vDataAtrib.put("cJSValidaciones","");
      vDataAtrib.put("lVigente",1);
      vDataAtrib = dConfigFormato.insert(vDataAtrib, null);
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
   * <p><b> delete from GRLFormato where iCveFormato = ?  </b></p>
   * <p><b> Campos Llave: iCveFormato, </b></p>
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
      String lSQL = "delete from GRLFormato where iCveFormato = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveFormato"));

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
   * <p><b> update GRLFormato set cDscFormato=?, cTitulo=?, lVigente=?, dtIniVigencia=?, cTablaBusca=?, cCampoLlaveBusca=?, iNumColumnas=? where iCveFormato = ?  </b></p>
   * <p><b> Campos Llave: iCveFormato, </b></p>
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
      String lSQL = "update GRLFormato set cDscFormato=?, cTitulo=?, lVigente=?, dtIniVigencia=?, cTablaBusca=?, cCampoLlaveBusca=?, iNumColumnas=? where iCveFormato = ? ";

      vData.addPK(vData.getString("iCveFormato"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cDscFormato"));
      lPStmt.setString(2,vData.getString("cTitulo"));
      lPStmt.setInt(3,vData.getInt("lVigente"));
      lPStmt.setDate(4,vData.getDate("dtIniVigencia"));
      lPStmt.setString(5,vData.getString("cTablaBusca"));
      lPStmt.setString(6,vData.getString("cCampoLlaveBusca"));
      lPStmt.setInt(7,vData.getInt("iNumColumnas"));
      lPStmt.setInt(8,vData.getInt("iCveFormato"));
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

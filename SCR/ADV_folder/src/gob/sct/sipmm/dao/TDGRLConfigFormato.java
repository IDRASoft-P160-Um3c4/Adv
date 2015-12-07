package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLConfigFormato.java</p>
 * <p>Description: DAO de la entidad GRLConfigFormato</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Leopoldo Beristain Gonz�lez
 * @version 1.0
 */
public class TDGRLConfigFormato extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepci�n de tipo DAO
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
   * <p><b> insert into GRLConfigFormato(iCveFormato,iCveAtributo,iOrden,cDscAtributo,cEtiquetaAnterior,iCveTipoRespuesta,cEtiquetaPosterior,lObligatorio,cValorIniOmision,cValorFinOmision,iMaxLongitud,cNomCampoPantalla,cTablaMapeo,cCampoMapeo,cJSValidaciones,lVigente) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveAtributo, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - VO Din�mico que contiene a la entidad a Insertada, as� como a la llave de esta entidad.
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
          "insert into GRLConfigFormato(iCveFormato,iCveAtributo,iOrden,cDscAtributo,cEtiquetaAnterior,iCveTipoRespuesta,cEtiquetaPosterior,lObligatorio,cValorIniOmision,cValorFinOmision,iMaxLongitud,cNomCampoPantalla,cTablaMapeo,cCampoMapeo,cJSValidaciones,lVigente,iRenglon,iColumna) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iCveAtributo) AS iCveAtributo from GRLConfigFormato where iCveFormato = " + vData.getInt("iCveFormatoAux"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iCveAtributo",vUltimo.getInt("iCveAtributo") + 1);
      }else
         vData.put("iCveAtributo",1);

      vData.addPK(vData.getString("iCveAtributo"));
      vData.addPK(vData.getString("iCveFormatoAux"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveFormatoAux"));
      lPStmt.setInt(2,vData.getInt("iCveAtributo"));
      lPStmt.setInt(3,vData.getInt("iOrden"));
      lPStmt.setString(4,vData.getString("cDscAtributo"));
      lPStmt.setString(5,vData.getString("cEtiquetaAnterior"));
      lPStmt.setInt(6,vData.getInt("iCveTipoRespuesta"));
      lPStmt.setString(7,vData.getString("cEtiquetaPosterior"));
      lPStmt.setInt(8,vData.getInt("lObligatorio"));
      lPStmt.setString(9,vData.getString("cValorIniOmision"));
      lPStmt.setString(10,vData.getString("cValorFinOmision"));
      lPStmt.setInt(11,vData.getInt("iMaxLongitud"));
      lPStmt.setString(12,vData.getString("cNomCampoPantalla"));
      lPStmt.setString(13,vData.getString("cTablaMapeo"));
      lPStmt.setString(14,vData.getString("cCampoMapeo"));
      lPStmt.setString(15,vData.getString("cJSValidaciones"));
      lPStmt.setInt(16,vData.getInt("lVigente"));
      lPStmt.setInt(17,vData.getInt("iRenglon"));
      lPStmt.setInt(18,vData.getInt("iColumna"));
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
   * Elimina al registro a trav�s de la entidad dada por vData.
   * <p><b> delete from GRLConfigFormato where iCveAtributo = ?  </b></p>
   * <p><b> Campos Llave: iCveAtributo, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
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
      String lSQL = "delete from GRLConfigFormato where iCveFormato = ? AND iCveAtributo = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveFormatoAux"));
      lPStmt.setInt(2,vData.getInt("iCveAtributo"));

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
   * <p><b> update GRLConfigFormato set iCveAtributo=?, iOrden=?, cDscAtributo=?, cEtiquetaAnterior=?, iCveTipoRespuesta=?, cEtiquetaPosterior=?, lObligatorio=?, cValorIniOmision=?, cValorFinOmision=?, iMaxLongitud=?, cNomCampoPantalla=?, cTablaMapeo=?, cCampoMapeo=?, cJSValidaciones=?, lVigente=? where iCveAtributo = ?  </b></p>
   * <p><b> Campos Llave: iCveAtributo, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
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

      String lSQL = "update GRLConfigFormato set iOrden=?, "+
                                                "cDscAtributo=?, "+
                                                "cEtiquetaAnterior=?, "+
                                                "iCveTipoRespuesta=?, "+
                                                "cEtiquetaPosterior=?, "+
                                                "lObligatorio=?, "+
                                                "cValorIniOmision=?, "+
                                                "cValorFinOmision=?, "+
                                                "iMaxLongitud=?,  "+
                                                "cNomCampoPantalla=?, "+
                                                "cTablaMapeo=?, "+
                                                "cCampoMapeo=?, "+
                                                "cJSValidaciones=?, "+
                                                "lVigente=?,  "+
                                                "iRenglon=?, "+
                                                "iColumna=? "+
                                            "where iCveAtributo=? and iCveFormato=?";

      vData.addPK(vData.getString("iCveAtributo"));
      vData.addPK(vData.getString("iCveFormatoAux"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iOrden"));
      lPStmt.setString(2,vData.getString("cDscAtributo"));
      lPStmt.setString(3,vData.getString("cEtiquetaAnterior"));
      lPStmt.setInt(4,vData.getInt("iCveTipoRespuesta"));
      lPStmt.setString(5,vData.getString("cEtiquetaPosterior"));
      lPStmt.setInt(6,vData.getInt("lObligatorio"));
      lPStmt.setString(7,vData.getString("cValorIniOmision"));
      lPStmt.setString(8,vData.getString("cValorFinOmision"));
      lPStmt.setInt(9,vData.getInt("iMaxLongitud"));
      lPStmt.setString(10,vData.getString("cNomCampoPantalla"));
      lPStmt.setString(11,vData.getString("cTablaMapeo"));
      lPStmt.setString(12,vData.getString("cCampoMapeo"));
      lPStmt.setString(13,vData.getString("cJSValidaciones"));
      lPStmt.setInt(14,vData.getInt("lVigente"));
      lPStmt.setInt(15,vData.getInt("iRenglon"));
      lPStmt.setInt(16,vData.getInt("iColumna"));
      lPStmt.setInt(17,vData.getInt("iCveAtributo"));
      lPStmt.setInt(18,vData.getInt("iCveFormatoAux"));
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

package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDINSCertificadoXModTram.java</p>
 * <p>Description: DAO de la entidad INSCertificadoXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author undefined
 * @version 1.0
 */
public class TDINSCertificadoXModTram extends DAOBase{
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
   * <p><b> insert into INSCertificadoXModTram(iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado,iCveGrupoCertif) values (?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado,iCveGrupoCertif, </b></p>
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
          "insert into INSCertificadoXModTram(iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado,iCveGrupoCertif) values (?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select max(DTINIVIGENCIA) as  dtIniVigencia from TRACONFIGURATRAMITE "+
                                   " where ICVETRAMITE = "+vData.getInt("iCveTramite")+
                                   " and ICVEMODALIDAD = "+vData.getInt("iCveModalidad"));
      TVDinRep vUltimo = (TVDinRep) vcData.get(0);
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setDate(3,vUltimo.getDate("dtIniVigencia"));
      lPStmt.setInt(4,vData.getInt("iTipoCertificado"));
      lPStmt.setInt(5,vData.getInt("iCveGrupoCertif"));
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
   * <p><b> delete from INSCertificadoXModTram where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ? AND iTipoCertificado = ? AND iCveGrupoCertif = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado,iCveGrupoCertif, </b></p>
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
      String lSQL = "delete from INSCertificadoXModTram where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ? AND iTipoCertificado = ? AND iCveGrupoCertif = ?  ";
      //...
      Vector vcData = findByCustom("","select max(DTINIVIGENCIA) as  dtIniVigencia from TRACONFIGURATRAMITE "+
                                   " where ICVETRAMITE = "+vData.getInt("iCveTramite")+
                                   " and ICVEMODALIDAD = "+vData.getInt("iCveModalidad"));
      TVDinRep vUltimo = (TVDinRep) vcData.get(0);


      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setDate(3,vUltimo.getDate("dtIniVigencia"));
      lPStmt.setInt(4,vData.getInt("iTipoCertificado1"));
      lPStmt.setInt(5,vData.getInt("iCveGrupoCertif1"));
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
   * <p><b> update INSCertificadoXModTram set undefined=? where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ? AND iTipoCertificado = ? AND iCveGrupoCertif = ?  </b></p>
   * <p><b> Campos Llave: iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado,iCveGrupoCertif, </b></p>
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
    PreparedStatement lPStmt1 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select max(DTINIVIGENCIA) as  dtIniVigencia from TRACONFIGURATRAMITE "+
                                   " where ICVETRAMITE = "+vData.getInt("iCveTramite")+
                                   " and ICVEMODALIDAD = "+vData.getInt("iCveModalidad"));
      TVDinRep vUltimo = (TVDinRep) vcData.get(0);
      //...
      String lSQL1 = "delete from INSCertificadoXModTram where iCveTramite = ? AND iCveModalidad = ? AND dtIniVigencia = ? AND iTipoCertificado = ? AND iCveGrupoCertif = ?  ";
      //...


      lPStmt = conn.prepareStatement(lSQL1);

      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setDate(3,vUltimo.getDate("dtIniVigencia"));
      lPStmt.setInt(4,vData.getInt("iTipoCertificado1"));
      lPStmt.setInt(5,vData.getInt("iCveGrupoCertif1"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

      Vector vcData1 = findByCustom("","select max(DTINIVIGENCIA) as  dtIniVigencia from TRACONFIGURATRAMITE "+
                                   " where ICVETRAMITE = "+vData.getInt("iCveTramite")+
                                   " and ICVEMODALIDAD = "+vData.getInt("iCveModalidad"));
      TVDinRep vUltimo1 = (TVDinRep) vcData1.get(0);

      String lSQL =
          "insert into INSCertificadoXModTram(iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado,iCveGrupoCertif) values (?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...

      lPStmt1 = conn.prepareStatement(lSQL);

      lPStmt1.setInt(1,vData.getInt("iCveTramite"));
      lPStmt1.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt1.setInt(5,vData.getInt("iCveGrupoCertif"));
      lPStmt1.setDate(3,vUltimo1.getDate("dtIniVigencia"));
      lPStmt1.setInt(4,vData.getInt("iTipoCertificado"));

      lPStmt1.executeUpdate();
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

package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDCYSProgramaMaestroDP.java</p>
 * <p>Description: DAO de la entidad CYSProgramaMaestroDP</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDCYSProgramaMaestroDP extends DAOBase{
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
   * <p><b> insert into CYSProgramaMaestroDP(iCveTitulo,iCveTitular,iNumTituloPropiedad,cSuperficiePropiedad,cLotePropiedad,cSeccionPropiedad,cManzanaPropiedad,cNumOficialPropiedad,cCallePropiedad,cKmPropiedad,cColoniaPropiedad,iCvePuerto,iCvePais,iCveEntidadFed,iCveMunicipio,iCveLocalidad) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
    int iConsecutivo = this.findMax(vData.getInt("iCveTitulo"));
    vData.put("iMovProgramaMaestroDP",iConsecutivo);
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "insert into CYSProgramaMaestroDP(iCveTitulo, " +
          "iMovProgramaMaestroDP, " +
          "dtRegistroPMDP, " +
          "cNumEscritoPMDP, " +
          "dtEscritoPMDP, " +
          "dtPresentacionPMDP, " +
          "dtIniVigenciaPMDP, " +
          "dtFinVigenciaPMDP, " +
          "cObsPMDP, " +
          "iCveTituloPMDP, " +
          "iCvePMDP ) " +
          "values (?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovProgramaMaestroDP"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovProgramaMaestroDP"));
      lPStmt.setDate(3,vData.getDate("dtRegistroPMDP"));
      lPStmt.setString(4,vData.getString("cNumEscritoPMDP"));
      lPStmt.setDate(5,vData.getDate("dtEscritoPMDP"));
      lPStmt.setDate(6,vData.getDate("dtPresentacionPMDP"));
      lPStmt.setDate(7,vData.getDate("dtIniVigenciaPMDP"));
      lPStmt.setDate(8,vData.getDate("dtFinVigenciaPMDP"));
      lPStmt.setString(9,vData.getString("cObsPMDP"));
      lPStmt.setInt(10,vData.getInt("iCveTituloPMDP"));
      lPStmt.setInt(11,vData.getInt("iCvePMDP"));
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
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from CYSProgramaMaestroDP where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "delete from CYSProgramaMaestroDP where iCveTitulo = ? " +
          "and iMovProgramaMaestroDP=? ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveTitulo"));
      lPStmt.setInt(2,vData.getInt("iMovProgramaMaestroDP"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = "" + sqle.getErrorCode();
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
   * <p><b> update CYSProgramaMaestroDP set cSuperficiePropiedad=?, cLotePropiedad=?, cSeccionPropiedad=?, cManzanaPropiedad=?, cNumOficialPropiedad=?, cCallePropiedad=?, cKmPropiedad=?, cColoniaPropiedad=?, iCvePuerto=?, iCvePais=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=? where iCveTitulo = ? AND iCveTitular = ? AND iNumTituloPropiedad = ?  </b></p>
   * <p><b> Campos Llave: iCveTitulo,iCveTitular,iNumTituloPropiedad, </b></p>
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
      String lSQL = "update CYSProgramaMaestroDP " +
          "set dtRegistroPMDP=?, " +
          "cNumEscritoPMDP=?, " +
          "dtEscritoPMDP=?, " +
          "dtPresentacionPMDP=?, " +
          "dtIniVigenciaPMDP=?, " +
          "dtFinVigenciaPMDP=?, " +
          "cObsPMDP=? " +
          "where iCveTitulo=? " +
          "and iMovProgramaMaestroDP=?";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovProgramaMaestroDP"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistroPMDP"));
      lPStmt.setString(2,vData.getString("cNumEscritoPMDP"));
      lPStmt.setDate(3,vData.getDate("dtEscritoPMDP"));
      lPStmt.setDate(4,vData.getDate("dtPresentacionPMDP"));
      lPStmt.setDate(5,vData.getDate("dtIniVigenciaPMDP"));
      lPStmt.setDate(6,vData.getDate("dtFinVigenciaPMDP"));
      lPStmt.setString(7,vData.getString("cObsPMDP"));
      lPStmt.setInt(8,vData.getInt("iCveTitulo"));
      lPStmt.setInt(9,vData.getInt("iMovProgramaMaestroDP"));
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

  public int findMax(int iCveTitulo) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    Statement lStmt = null;
    ResultSet rs = null;
    int iMovProgramaMaestro = 0;
    try{

      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      lStmt = conn.createStatement();
      String lSQL =
          " SELECT MAX(iMovProgramaMaestroDP) AS iMovProgramaMaestroDP " +
          " FROM CYSProgramaMaestroDP " +
          " WHERE iCveTitulo = " + iCveTitulo;

      rs = lStmt.executeQuery(lSQL);
      while(rs.next()){
        iMovProgramaMaestro = rs.getInt("iMovProgramaMaestroDP");
      }
      iMovProgramaMaestro++;
    } catch(Exception ex){
      ex.printStackTrace();
    } finally{
      try{
        if(lStmt != null){
          lStmt.close();
        }
        if(rs != null){
          rs.close();
        }
        if(conn != null){
          conn.close();
        }
        dbConn.closeConnection();
      } catch(Exception ex2){
      }
      return iMovProgramaMaestro;
    }
  }

  public TVDinRep updateEPMDP(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update CYSProgramaMaestroDP " +
          "set dtRegistroEPMDP=?, " +
          "cNumEscritoEPMDP=?, " +
          "dtEscritoEPMDP=?, " +
          "dtPresentacioEPMDP=?, " +
          "dtPubEPMDP=?, " +
          "cObsEPMDP=? " +
          "where iCveTitulo=? " +
          "and iMovProgramaMaestroDP=?";

      vData.addPK(vData.getString("iCveTitulo"));
      vData.addPK(vData.getString("iMovProgramaMaestroDP"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistroEPMDP"));
      lPStmt.setString(2,vData.getString("cNumEscritoEPMDP"));
      lPStmt.setDate(3,vData.getDate("dtEscritoEPMDP"));
      lPStmt.setDate(4,vData.getDate("dtPresentacioEPMDP"));
      lPStmt.setDate(5,vData.getDate("dtPubEPMDP"));
      lPStmt.setString(6,vData.getString("cObsEPMDP"));
      lPStmt.setInt(7,vData.getInt("iCveTitulo"));
      lPStmt.setInt(8,vData.getInt("iMovProgramaMaestroDP"));
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

  public Vector findProgramaMDP(TVDinRep vData) throws DAOException{
   Vector vcPMDP = new Vector();
   int iCveTitulo=0, iEjercicioI=0,iEjercicioF=0;
   java.sql.Date dtConcesion =null;
   TFechas dtFecha = new TFechas();
   TVDinRep vDatos, vNoExisten;
   boolean lExiste = false;

   try{
      iCveTitulo = vData.getInt("iCveTitulo");
      dtConcesion = vData.getDate("dtIniVigencia");
      iEjercicioI = (dtFecha.getIntYear(dtConcesion)+1);
      iEjercicioF = dtFecha.getIntYear(dtFecha.TodaySQL());
      String cSQL = " SELECT DISTINCT year(DTPRESENTACIONPMDP) AS IEJERCICIO FROM  CYSPROGRAMAMAESTRODP  " +
                    " WHERE ICVETITULO = " + iCveTitulo + " ORDER BY year(DTPRESENTACIONPMDP) ";
      Vector vcData = findByCustom("",cSQL);
      if(vcData.size()>0){
         while(iEjercicioI<=iEjercicioF){
            lExiste = false;
            for(int i=0;i<vcData.size();i++){
              vDatos = (TVDinRep) vcData.get(i);
              if(iEjercicioI==vDatos.getInt("iEjercicio")){
                 vcData.removeElementAt(i);
                 lExiste = true;
                 break;
              }
            }

            if(!lExiste){
               vNoExisten = new TVDinRep();
               vNoExisten.put("iEjercicio",iEjercicioI);
               vNoExisten.put("cMotivo","NO PRESENTADO");
               vcPMDP.add(vNoExisten);
            }
            iEjercicioI++;
         }
         if (vcPMDP.isEmpty()){
           vNoExisten = new TVDinRep();
           vNoExisten.put("iEjercicio",iEjercicioI);
           vNoExisten.put("cMotivo","NO PRESENTADO");
           vcPMDP.add(vNoExisten);
         }
      } else {
        vNoExisten = new TVDinRep();
        vNoExisten.put("iEjercicio",iEjercicioI);
        vNoExisten.put("cMotivo","NO PRESENTADO");
        vcPMDP.add(vNoExisten);
      }

   } catch(Exception e){
   cError = e.toString();
   e.printStackTrace();
 } finally{
   if(!cError.equals(""))
     throw new DAOException(cError);
   return vcPMDP;
   }
  }

  public Vector findExtractoDP(int iCveTitulo) throws DAOException{
   Vector vcEPMDP = new Vector();
   TVDinRep vDatos,vExiste;

   try{
      String cSQL = " SELECT DTPRESENTACIONPMDP,DTPRESENTACIOEPMDP,DTPUBEPMDP " +
                    " FROM CYSPROGRAMAMAESTRODP WHERE ICVETITULO = " + iCveTitulo +
                    "  AND IMOVPROGRAMAMAESTRODP = (SELECT MAX(M.IMOVPROGRAMAMAESTRODP) FROM CYSPROGRAMAMAESTRODP M " +
                                                    " WHERE M.ICVETITULO = " + iCveTitulo + ") ";

      Vector vcData = findByCustom("",cSQL);
      if(vcData.size()>0){
        vDatos = (TVDinRep) vcData.get(0);
        if(vDatos.getDate("dtPresentacionPMDP") != null && vDatos.getDate("dtPresentacioEPMDP") != null &&
           vDatos.getDate("dtPubEPMDP") != null){
           vExiste = new TVDinRep();
           vExiste.put("lPresentacion",1);
           vcEPMDP.add(vExiste);
        }
        else{
           vExiste = new TVDinRep();
           vExiste.put("lPresentacion",0);
           vcEPMDP.add(vExiste);
        }
      }

   } catch(Exception e){
   cError = e.toString();
   e.printStackTrace();
 } finally{
   if(!cError.equals(""))
     throw new DAOException(cError);
   return vcEPMDP;
   }
  }


}


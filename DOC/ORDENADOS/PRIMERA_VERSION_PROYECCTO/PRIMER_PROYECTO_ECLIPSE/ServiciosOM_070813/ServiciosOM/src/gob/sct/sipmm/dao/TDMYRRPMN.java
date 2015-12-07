package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDMYRRPMN.java</p>
 * <p>Description: DAO de la entidad MYRRPMN</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
public class TDMYRRPMN extends DAOBase{
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
   * <p><b> insert into MYRRPMN(iCveOficina,iFolioRPMN,iPartida,iEjercicio,iNumSolicitud,dtRegistro,iRamo,cDscDocumento,iCvePersona,cInscripcion,cSintesis,iCveSeccion,iCveTipoAsiento,cTitular,iCalificacion,cDscCalificacion,iPartidaCancela) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
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
    int lRegistraEmb = vData.getInt("lRegEmbarcacion");
    vData.put("iCveOficIns",vData.getInt("iCveOficina"));
    TFechas fecha = new TFechas();
    vData.put("iEjercicioIns",fecha.getIntYear(fecha.TodaySQL()));
    TDGRLObservaLarga DGRLObservaLarga = new TDGRLObservaLarga();
    TVDinRep VObs = new TVDinRep();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String cSQLEmboEmp="";
        if(lRegistraEmb == 1){
          cSQLEmboEmp = "SELECT " +
              "  MYRRPMN.IEJERCICIOINS, " +
              "  MYRRPMN.ICVEOFICINA, " +
              "  MYRRPMN.IFOLIORPMN, " +
              "  MYRRPMN.IPARTIDA " +
              "FROM MYREMBARCACION " +
              "JOIN MYRRPMN ON MYREMBARCACION.IEJERCICIOINS = MYRRPMN.IEJERCICIOINS AND  MYREMBARCACION.ICVEOFICINA = MYRRPMN.ICVEOFICINA AND MYREMBARCACION.IFOLIORPMN = MYRRPMN.IFOLIORPMN AND  MYREMBARCACION.IPARTIDA = MYRRPMN.IPARTIDA " +
              "WHERE MYREMBARCACION.ICVEVEHICULO = " +
              vData.getInt("iCveVehiculo") +
              " and lhistorico <> 1 " +
              " ORDER BY MYRRPMN.IPARTIDA DESC, MYRRPMN.IFOLIORPMN  DESC";
        }
        if(lRegistraEmb == 0){
          cSQLEmboEmp = "SELECT " +
              "  MYREMPRESA.IEJERCICIOINS, " +
              "  MYREMPRESA.ICVEOFICINA, " +
              "  MYREMPRESA.IFOLIORPMN, " +
              "  MYREMPRESA.IPARTIDA " +
              "FROM  MYREMPRESA " +
              "JOIN MYRRPMN ON MYREMPRESA.IEJERCICIOINS = MYRRPMN.IEJERCICIOINS AND MYREMPRESA.ICVEOFICINA = MYRRPMN.ICVEOFICINA AND MYREMPRESA.IFOLIORPMN = MYRRPMN.IFOLIORPMN AND  MYREMPRESA.IPARTIDA = MYRRPMN.IPARTIDA " +
              "WHERE MYREMPRESA.ICVEPERSONA = " + vData.getInt("iCvePersona") +
              " and lhistorico <> 1 " +
              " ORDER BY MYRRPMN.IPARTIDA DESC, MYRRPMN.IFOLIORPMN  DESC";
        }
        Vector vcData = findByCustom("",cSQLEmboEmp);
        if(vcData.size() > 0){
          TVDinRep vUltimo = (TVDinRep) vcData.get(0);
          int ultimoMasUno = vUltimo.getInt("iPartida") + 1;
          vData.put("iEjercicioIns",vUltimo.getInt("iEjercicioIns"));
          vData.put("iFolioRPMN",vUltimo.getInt("iFolioRPMN"));
          //if(vData.getInt("lHistorico")!=1)
          vData.put("iConsecPartida",ultimoMasUno);
          vData.put("iOficina",vUltimo.getInt("iCveOficina"));
        }else{
          //if(vData.getInt("lHistorico")!=1)
          vData.put("iConsecPartida",1);
          cSQLEmboEmp = "SELECT " +
              "  MYRRPMN. ICVEOFICINA, " +
              "  MYRRPMN. IFOLIORPMN " +
              "FROM MYRRPMN " +
              "WHERE  MYRRPMN.ICVEOFICINA = " + vData.getInt("iCveOficina") +
              " AND MYRRPMN.iEjercicioIns = " + fecha.getIntYear(fecha.TodaySQL()) +
              //" AND LHISTORICO <> 1 "+
              " ORDER BY MYRRPMN.IFOLIORPMN  DESC";

          vcData = findByCustom("",cSQLEmboEmp);

          vData.put("iOficina",vData.getInt("iCveOficina"));
          if(vcData.size() > 0){
            TVDinRep vUltimo2 = (TVDinRep) vcData.get(0);
            vData.put("iFolioRPMN",vUltimo2.getInt("iFolioRPMN") + 1);
          } else
            vData.put("iFolioRPMN",1);

        }

      VObs.put("cDscObsLarga",vData.getString("cSintesis"));
      VObs = DGRLObservaLarga.insert(VObs,null);

      String lSQL = "INSERT INTO MYRRPMN ( "+
          "ICVEOFICINA, " + //1
          "IFOLIORPMN, " +
          "IPARTIDA, " +
          "IEJERCICIO, " +
          "INUMSOLICITUD, " + //5
          "DTREGISTRO, " +
          "ICVEPERSONA, " +
          //"CSINTESIS, " +
          "ICVERAMO, " +
          "ICVESECCION, " +
          "ICVETIPOASIENTO, " +//10
          "CTITULAR, " +
          "ICALIFICACION, " +
          "CDSCCALIFICACION, " +
          "ICVEUSUARIO, " +
          "ICVETIPOACTO, "+//15
          "dtIngreso, "+
          "lRegFinalizado, "+
          "cDscDocumento, "+
          "iEjercicioIns, "+
          "iCveOficinaFolioAnt, "+//20
          "cFolioRPMNAnt, " +
          "iCveObsLarga, " +
          "IEJERCICIOOBSLARGA,"+
          "ICVEOCICINAINS,"+
          "iCveOficinaReg ) "+
          "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iEjercicioIns"));
      vData.addPK(vData.getString("iOficina"));
      vData.addPK(vData.getString("iFolioRPMN"));
      vData.addPK(vData.getString("iConsecPartida"));
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iOficina"));
      lPStmt.setInt(2,vData.getInt("iFolioRPMN"));
      lPStmt.setInt(3,vData.getInt("iConsecPartida"));
      lPStmt.setInt(4,vData.getInt("iEjercicio"));
      lPStmt.setInt(5,vData.getInt("iNumSolicitud"));
      lPStmt.setDate(6,vData.getDate("dtRegistro"));
      lPStmt.setInt(7,vData.getInt("iCvePersona"));
      //lPStmt.setString(8,vData.getString("cSintesis"));
      lPStmt.setInt(8,vData.getInt("iCveRamo"));
      lPStmt.setInt(9,vData.getInt("iCveSeccion"));
      lPStmt.setInt(10,vData.getInt("iCveTipoAsiento"));
      lPStmt.setString(11,vData.getString("cTitular"));
      lPStmt.setInt(12,vData.getInt("iCalificacion"));
      lPStmt.setString(13,vData.getString("cDscCalificacion"));
      lPStmt.setInt(14,vData.getInt("iCveUsuario"));
      lPStmt.setInt(15,vData.getInt("iCveTipoActo"));
      lPStmt.setDate(16,vData.getDate("dtIngreso"));
      lPStmt.setInt(17,vData.getInt("lRegFinalizado"));
      lPStmt.setString(18,vData.getString("cDscDocumento"));
      lPStmt.setInt(19,vData.getInt("iEjercicioIns"));
      lPStmt.setInt(20,vData.getInt("iCveOficinaFolioAnt"));
      lPStmt.setString(21,vData.getString("cFolioRPMNAnt"));
      lPStmt.setInt(22,VObs.getInt("iCveObsLarga"));
      lPStmt.setInt(23,VObs.getInt("iEjercicioObsLarga"));
      lPStmt.setInt(24,vData.getInt("iCveOficIns"));
      lPStmt.setInt(25,vData.getInt("iCveOficinaReg"));

      try{
        lPStmt.executeUpdate();
      }catch (Exception ex){
        ex.printStackTrace();
      }
      finally{
        if(lPStmt != null){
          lPStmt.close();
        }
       /* if(cnNested == null){
          conn.commit();
        }*/
      }


      //Si es una cancelación actualiza la partida de cancelación del q se cancela
      if(vData.getString("hdCancelar").equals("1")){
        lSQL = "UPDATE MYRRPMN SET iPartidaCancela = " + vData.getInt("iConsecPartida") +
               " WHERE iEjercicioIns="+vData.getInt("iEjercicioReg")+
               " AND iCveOficina="+vData.getInt("iOficinaReg")+
               " AND iFolioRPMN="+vData.getString("hdFolioRPMNCancelar")+
               " AND iPartida="+vData.getString("hdPartida");
        lPStmt = conn.prepareStatement(lSQL);

        lPStmt.executeUpdate();
        if(lPStmt != null){
          lPStmt.close();
        }

      }

      //Se ejecuta si la inscripción es de una embarcación
      if(lRegistraEmb == 1){
        lSQL = "INSERT INTO MYREmbarcacion (" +
            "iCveOficina, " +
            "iFolioRPMN, " +
            "iPartida, " +
            "cNomEmbarcacion, " +
            "iCveVehiculo, " +
            "iCvePropietario, " +
            "iCveTipoEmbarcacion, " +
            "dEslora, " +
            "iUniMedEslora, " +
            "dManga, " +
            "iUniMedManga, " +
            "dPuntal, " +
            "iUniMedPuntal, " +
            "dArqueoBruto, " +
            "dArqueoNeto, " +
            "iEjercicioIns, "+
            "iCveMunicipioMatric, "+
            "iCveEntidadMatric, " +
            "cMatricula, " +
            "cPropietario " +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iOficina"));
        lPStmt.setInt(2,vData.getInt("iFolioRPMN"));
        lPStmt.setInt(3,vData.getInt("iConsecPartida"));
        lPStmt.setString(4,vData.getString("hdEmbarcacion"));
        lPStmt.setInt(5,vData.getInt("iCveVehiculo"));
        lPStmt.setInt(6,vData.getInt("iCvePropietario"));
        lPStmt.setInt(7,vData.getInt("iCveTipoEmbarcacion"));
        lPStmt.setDouble(8,vData.getDouble("dEslora"));
        lPStmt.setInt(9,vData.getInt("iUniMedEslora"));
        lPStmt.setDouble(10,vData.getDouble("dManga"));
        lPStmt.setInt(11,vData.getInt("iUniMedManga"));
        lPStmt.setDouble(12,vData.getDouble("dPuntal"));
        lPStmt.setInt(13,vData.getInt("iUniMedPuntal"));
        lPStmt.setDouble(14,vData.getDouble("dArqueoBruto"));
        lPStmt.setDouble(15,vData.getDouble("dArqueoNeto"));
        lPStmt.setInt(16,vData.getInt("iEjercicioIns"));
        lPStmt.setInt(17,vData.getInt("iCveMunicMatricula"));
        lPStmt.setInt(18,vData.getInt("iCveEntFedMatricula"));
        lPStmt.setString(19,vData.getString("cMatricula"));
        lPStmt.setString(20,vData.getString("cPropietario"));

        lPStmt.executeUpdate();
      }

      //Se ejecuta si la inscripción es de una empresa
      if(lRegistraEmb != 1){
        lSQL = "INSERT INTO MYREmpresa("+
            "iCveOficina, " +
            "iFolioRPMN, " +
            "iPartida, " +
            "cRFC, "+
            "iCvePersona, "+
            "cCalle, "+
            "cNumExterior, "+
            "cNomRazonSocial, "+
            "cNumInterior, "+
            "cColonia, "+
            "cCodPostal, "+
            "iCveEntidadFed, "+
            "iCveMunicipio, "+
            "iCveLocalidad, "+
            "iCvePais, "+
            "dPartExtranjera, "+
            "iEjercicioIns "+
            ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        lPStmt = conn.prepareStatement(lSQL);

        lPStmt.setInt(1,vData.getInt("iOficina"));
        lPStmt.setInt(2,vData.getInt("iFolioRPMN"));
        lPStmt.setInt(3,vData.getInt("iConsecPartida"));
        lPStmt.setString(4,vData.getString("cRFC"));
        lPStmt.setInt(5,vData.getInt("iCvePersona"));
        lPStmt.setString(6,vData.getString("cCalle"));
        lPStmt.setString(7,vData.getString("cNumExterior"));
        lPStmt.setString(8,vData.getString("cNomRazonSocial"));
        lPStmt.setString(9,vData.getString("cNumInterior"));
        lPStmt.setString(10,vData.getString("cColonia"));
        lPStmt.setString(11,vData.getString("cCodPostal"));
        lPStmt.setInt(12,vData.getInt("iCveEntidadFed"));
        lPStmt.setInt(13,vData.getInt("iCveMunicipio"));
        lPStmt.setInt(14,vData.getInt("iCveLocalidad"));
        lPStmt.setInt(15,vData.getInt("iCvePais"));
        lPStmt.setDouble(16,vData.getDouble("dPartExtranjera"));
        lPStmt.setInt(17,vData.getInt("iEjercicioIns"));
        lPStmt.executeUpdate();
      }

      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      ex.printStackTrace();
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          e.printStackTrace();
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
   * <p><b> delete from MYRRPMN where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
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
      String lSQL = "delete from MYRRPMN where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iFolioRPMN"));
      lPStmt.setInt(3,vData.getInt("iPartida"));

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
   * <p><b> update MYRRPMN set iEjercicio=?, iNumSolicitud=?, dtRegistro=?, iRamo=?, cDscDocumento=?, iCvePersona=?, cInscripcion=?, cSintesis=?, iCveSeccion=?, iCveTipoAsiento=?, cTitular=?, iCalificacion=?, cDscCalificacion=?, iPartidaCancela=? where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
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

      String lSQL = " update MYRRPMN "+
                    " set dtRegistro=? , " +
                    //"   cSintesis=?, "+
                    "   lRegFinalizado=?," +
                    "   cDscDocumento=?, " +
                    "   iCveOficinaFolioAnt=?, "+
                    "   cFolioRPMNAnt=?, " +

                    "   iCveSeccion=?, " +
                    "   iCveTipoAsiento=?, " +
                    "   iCveTipoActo=?, " +
                    "   dtIngreso=? " +

                    " WHERE iEjercicioIns = ?"+
                    "   AND iCveOficina = ? " +
                    "   AND iFolioRPMN = ? " +
                    "   AND iPartida = ? ";

      vData.addPK(vData.getString("iEjercicioIns"));
      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iFolioRPMN"));
      vData.addPK(vData.getString("iPartida"));


      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      //lPStmt.setString(2,vData.getString("cSintesis"));
      lPStmt.setInt(2,vData.getInt("lRegFinalizado"));
      lPStmt.setString(3,vData.getString("cDscDocumento"));
      lPStmt.setInt(4,vData.getInt("iCveOficinaFolioAnt"));
      lPStmt.setString(5,vData.getString("cFolioRPMNAnt"));

      lPStmt.setInt(6,vData.getInt("iCveSeccion"));
      lPStmt.setInt(7,vData.getInt("iCveTipoAsiento"));
      lPStmt.setInt(8,vData.getInt("iCveTipoActo"));
      lPStmt.setDate(9,vData.getDate("dtIngreso"));

      lPStmt.setInt(10,vData.getInt("iEjercicioIns"));
      lPStmt.setInt(11,vData.getInt("hdOficReg"));
      //hdFolioRPMNCancelar tiene el valor del folio seleccionado y que se esta actualizando (no confundirse con el nombre)
      lPStmt.setInt(12,vData.getInt("hdFolioRPMNCancelar"));
      lPStmt.setInt(13,vData.getInt("hdPartReg"));

      lPStmt.executeUpdate();

      if(lPStmt != null){
        lPStmt.close();
      }

      lSQL = "UPDATE GRLObservaLarga "+
          "SET CDSCOBSLARGA = ? " +
          "WHERE IEJERCICIOOBSLARGA = ? " +
          "AND ICVEOBSLARGA = ? "
          ;

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cSintesis"));
      lPStmt.setInt(2,vData.getInt("iEjercicioObsLarga"));
      lPStmt.setInt(3,vData.getInt("iCveObsLarga"));
      lPStmt.executeUpdate();

      if(lPStmt != null){
        lPStmt.close();
      }

      int lRegistraEmb = vData.getInt("lRegEmbarcacion");
      if(lRegistraEmb==0){
        lSQL = "UPDATE MYREmpresa " +
            "SET dPartExtranjera = ? " +
            " WHERE iEjercicioIns = ?" +
            "   AND iCveOficina = ? " +
            "   AND iFolioRPMN = ? " +
            "   AND iPartida = ? ";

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setDouble(1,vData.getDouble("dPartExtranjera"));
        lPStmt.setInt(2,vData.getInt("iEjercicioIns"));
        lPStmt.setInt(3,vData.getInt("hdOficReg"));
        //hdFolioRPMNCancelar tiene el valor del folio seleccionado y que se esta actualizando (no confundirse con el nombre)
        lPStmt.setInt(4,vData.getInt("hdFolioRPMNCancelar"));
        lPStmt.setInt(5,vData.getInt("hdPartReg"));
        lPStmt.executeUpdate();
      }else{
        lSQL = "UPDATE MYREMBARCACION SET ICVEPROPIETARIO = ?, CPROPIETARIO=? " +
            "where IEJERCICIOINS=? and ICVEOFICINA=? and IFOLIORPMN=? and IPARTIDA=? ";

        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iCvePropietario"));
        lPStmt.setString(2,vData.getString("cPropietario"));
        lPStmt.setInt(3,vData.getInt("iEjercicioIns"));
        lPStmt.setInt(4,vData.getInt("hdOficReg"));
        //hdFolioRPMNCancelar tiene el valor del folio seleccionado y que se esta actualizando (no confundirse con el nombre)
        lPStmt.setInt(5,vData.getInt("hdFolioRPMNCancelar"));
        lPStmt.setInt(6,vData.getInt("hdPartReg"));
        lPStmt.executeUpdate();
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


 public TVDinRep updateAdmin(TVDinRep vData,Connection cnNested) throws
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
     
     String lSQL = "UPDATE GRLObservaLarga "+
                   "SET CDSCOBSLARGA = ? " +
                   "WHERE IEJERCICIOOBSLARGA = ? " +
                   "AND ICVEOBSLARGA = ? ";
     
     if(vData.getInt("iEjercicioObs")>0 && vData.getInt("iCveObsLarga")>0){
    	 lPStmt1 = conn.prepareStatement(lSQL);
    	 lPStmt1.setString(1,vData.getString("cSintesis"));
    	 lPStmt1.setInt(2,vData.getInt("iEjercicioObs"));
    	 lPStmt1.setInt(3,vData.getInt("iCveObsLarga"));
    	 lPStmt1.executeUpdate();
     }
     else {
    	 TDGRLObservaLarga obs = new TDGRLObservaLarga();
    	 TVDinRep vObs = new TVDinRep();
    	 vObs.put("cDscObsLarga", vData.getString("cSintesis"));
    	 vObs = obs.insert(vObs,conn);
    	 vData.remove("iEjercicioObs");
    	 vData.remove("iCveObsLarga");
    	 TFechas tfSol = new TFechas("44");
    	 
    	 java.sql.Date dtHoy = new java.sql.Date(new java.util.Date().getTime());
    	 String cDmy = tfSol.getFechaDDMMYYYY(dtHoy,"/");
    	 String cEjercicio = cDmy.substring(6);
    	 vData.put("iEjercicioObs", cEjercicio);
    	 vData.put("iCveObsLarga", vObs.getInt("iCveObsLarga"));
     }
     
     if(lPStmt1 != null){
         lPStmt1.close();
      } 
      lSQL =
               "UPDATE MYRRPMN SET " +
               "ICVERAMO=?, " +
               "ICVEOFICINAREG=?, " +
               "CTITULAR=?, " +
               "IEJERCICIO=?, " +
               "INUMSOLICITUD=?, " +
               "ICVESECCION=?, " +
               "ICVETIPOACTO=?, " +
               "ICVETIPOASIENTO=?, " +
               "DTINGRESO=?, " +
               "DTRegistro=?,"+
               "CDSCDOCUMENTO=?, " +
               "IPARTIDACANCELA=?, " +
               "LREGFINALIZADO=?, " +
               "ICVEOFICINAFOLIOANT=?, " +
               "CFOLIORPMNANT=?, " +
               "IEJERCICIOOBSLARGA=?, "+
               "ICVEOBSLARGA=? "+
               " WHERE IEJERCICIOINS = ? " +
               "   AND ICVEOFICINA = ? " +
               "   AND IFOLIORPMN = ? " +
               "   AND IPARTIDA = ? ";
     vData.addPK(vData.getString("iEjercicioIns"));
     vData.addPK(vData.getString("iCveOficina"));
     vData.addPK(vData.getString("iFolioRPMN"));
     vData.addPK(vData.getString("iPartida"));
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iCveRamo"));
     lPStmt.setInt(2,vData.getInt("iCveOficinaReg"));
     lPStmt.setString(3,vData.getString("cTitular"));
     lPStmt.setInt(4,vData.getInt("iEjercSolReg"));
     lPStmt.setInt(5,vData.getInt("iNumSolReg"));
     lPStmt.setInt(6,vData.getInt("iCveSeccion"));
     lPStmt.setInt(7,vData.getInt("iCveTipoActo"));
     lPStmt.setInt(8,vData.getInt("iCveTipoAsiento"));
     lPStmt.setDate(9,vData.getDate("dtIngreso"));
     lPStmt.setDate(10,vData.getDate("dtRegistro"));
     lPStmt.setString(11,vData.getString("cDscDocumento"));
     lPStmt.setInt(12,vData.getInt("dPartExtranjera"));
     lPStmt.setInt(13,vData.getInt("lRegFinalizado"));
     lPStmt.setInt(14,vData.getInt("iCveOficinaFolioAnt"));
     lPStmt.setString(15,vData.getString("cFolioRPMNAnt"));
     lPStmt.setInt(16,vData.getInt("iEjercicioObs"));
     lPStmt.setInt(17,vData.getInt("iCveObsLarga"));
     lPStmt.setInt(18,vData.getInt("iEjercicioIns"));
     lPStmt.setInt(19,vData.getInt("iCveOficina"));
     lPStmt.setInt(20,vData.getInt("iFolioRPMN"));
     lPStmt.setInt(21,vData.getInt("iPartida"));

     lPStmt.executeUpdate();

     if(lPStmt != null){
       lPStmt.close();
     }



     if(lPStmt != null){
       lPStmt.close();
     }

     if(cnNested == null){
       conn.commit();
     }
   } catch(Exception ex){
     warn("update",ex);
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


 public TVDinRep cancelaFolio(TVDinRep vData,Connection cnNested) throws
   DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   TFechas fecha = new TFechas();
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }

     String lSQL = " UPDATE MYRRPMN SET dtCancelaFolio = ? WHERE iEjercicioIns=? AND iCveOficina=? AND iFolioRPMN=? ";

     vData.addPK(vData.getString("iEjercicioIns"));
     vData.addPK(vData.getString("iOficinaReg"));
     vData.addPK(vData.getString("iFolioRPMN"));


     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setDate(1,fecha.getDateSQL(fecha.getThisTime()));
     lPStmt.setInt(2,vData.getInt("iEjercicioIns"));
     lPStmt.setInt(3,vData.getInt("iOficinaReg"));
     lPStmt.setInt(4,vData.getInt("iFolioReg"));

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

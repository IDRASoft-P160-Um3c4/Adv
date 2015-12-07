package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDTRARegRefPago.java</p>
 * <p>Description: DAO de la entidad TRARegRefPago</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @author iCaballero
 * @version 1.0
 */
public class TDTRARegRefPago
    extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  public TDDatosRefPago dRefPago;
  String cSistema = "44";

  public TDTRARegRefPago(){
    dRefPago = new TDDatosRefPago();
  }

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
   * <p><b> insert into TRARegRefPago(iEjercicio,iNumSolicitud,iConsecutivo,cRefNumerica,cRefAlfaNum,dImportePagar,dImportePagado,dtPago,iCveBanco,cFormatoSAT) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iConsecutivo, </b></p>
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
    TFechas tFecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into TRARegRefPago(iEjercicio,iNumSolicitud,iConsecutivo,iRefNumerica,cRefAlfaNum,dImportePagar,dImportePagado,dtPago,iCveBanco,cFormatoSAT,lPagado,iCveConcepto,iNumUnidades,iCveSolicitanteIngresos,dtMovimiento) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("",
          "SELECT MAX(iConsecutivo) AS iConsecutivo FROM TRARegRefPago " +
                                   "WHERE iEjercicio = " +
                                   vData.getString("iEjercicio") +
                                   "  AND iNumSolicitud = " +
                                   vData.getString("iNumSolicitud"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivo",vUltimo.getInt("iConsecutivo") + 1);
      } else
        vData.put("iConsecutivo",1);
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iConsecutivo"));
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iConsecutivo"));
      lPStmt.setString(4,vData.getString("cRefNumerica"));
      lPStmt.setString(5,vData.getString("cRefAlfaNum"));
      lPStmt.setFloat(6,vData.getFloat("dImportePagar"));
      lPStmt.setFloat(7,vData.getFloat("dImportePagado"));
      lPStmt.setDate(8,vData.getDate("dtPago"));
      lPStmt.setInt(9,vData.getInt("iCveBanco"));
      lPStmt.setString(10,vData.getString("cFormatoSAT"));
      lPStmt.setInt(11,vData.getInt("lPagado"));
      lPStmt.setInt(12,vData.getInt("iCveConcepto"));
      lPStmt.setInt(13,vData.getInt("iNumUnidades"));
      lPStmt.setInt(14,vData.getInt("iCveSolicitanteIngresos"));
      lPStmt.setDate(15,tFecha.getDateSQL(vData.getString("dtMovimiento")));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException e){
      lSuccess = false;
      cMensaje = super.getSQLMessages(e);
    } catch(Exception ex){
      cMensaje = "";
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMensaje);
      return vData;
    }
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRARegRefPago where iEjercicio = ? AND iNumSolicitud = ? AND iConsecutivo = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iConsecutivo, </b></p>
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
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from TRARegRefPago where iEjercicio = ? AND iNumSolicitud = ? AND iConsecutivo = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iConsecutivo"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException e){
      lSuccess = false;
      cMensaje = super.getSQLMessages(e);
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("delete.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMensaje);
      return lSuccess;
    }
  }

  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update TRARegRefPago set cRefNumerica=?, cRefAlfaNum=?, dImportePagar=?, dImportePagado=?, dtPago=?, iCveBanco=?, cFormatoSAT=? where iEjercicio = ? AND iNumSolicitud = ? AND iConsecutivo = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iConsecutivo, </b></p>
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
      String lSQL = "update TRARegRefPago set iRefNumerica=?, cRefAlfaNum=?, dImportePagar=?, dImportePagado=?, dtPago=?, iCveBanco=?, cFormatoSAT=?, lPagado=?, iCveConcepto=?, iNumUnidades=? where iEjercicio = ? AND iNumSolicitud = ? AND iConsecutivo = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iConsecutivo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("iRefNumerica"));
      lPStmt.setString(2,vData.getString("cRefAlfaNum"));
      lPStmt.setDouble(3,vData.getDouble("dImportePagar"));
      lPStmt.setDouble(4,vData.getDouble("dImportePagado"));
      lPStmt.setDate(5,vData.getDate("dtPago"));
      lPStmt.setInt(6,vData.getInt("iCveBanco"));
      lPStmt.setString(7,vData.getString("cFormatoSAT"));
      lPStmt.setInt(8,vData.getInt("lPagado"));
      lPStmt.setInt(9,vData.getInt("iCveConcepto"));
      lPStmt.setInt(10,vData.getInt("iNumUnidades"));

      lPStmt.setInt(11,vData.getInt("iEjercicio"));
      lPStmt.setInt(12,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(13,vData.getInt("iConsecutivo"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException e){
      lSuccess = false;
      cMensaje = super.getSQLMessages(e);
    } catch(Exception ex){
      cMensaje = "";
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMensaje);
      return vData;
    }
  }

  public TVDinRep updateDatosPago(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    cMensaje = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update TRARegRefPago set cRefAlfaNum=?, dImportePagado=?, dtPago=?, iCveBanco=?, cFormatoSAT=?, lPagado=? where iEjercicio = ? AND iNumSolicitud = ? AND iConsecutivo = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iConsecutivo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cRefAlfaNum"));
      lPStmt.setDouble(2,vData.getDouble("dImportePagado"));
      lPStmt.setDate(3,vData.getDate("dtPago"));
      lPStmt.setInt(4,vData.getInt("iCveBanco"));
      lPStmt.setString(5,vData.getString("cFormatoSAT"));
      lPStmt.setInt(6,vData.getInt("lPagado"));

      lPStmt.setInt(7,vData.getInt("iEjercicio"));
      lPStmt.setInt(8,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(9,vData.getInt("iConsecutivo"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException e){
      cMensaje += super.getSQLMessages(e);
    } catch(Exception ex){
      cMensaje += ex.getMessage();
      warn("update",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback",e);
        }
      }
    } finally{
      try{
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(!cMensaje.equals(""))
        throw new DAOException(cMensaje);
      return vData;
    }
  }

  public TVDinRep insertBatch(TVDinRep vData,Connection cnNested) throws
      DAOException{
    boolean lSuccess = true;
    TVDinRep vDataEnvia = new TVDinRep();
    try{
      vDataEnvia.put("iEjercicio",vData.getInt("iEjercicio"));
      vDataEnvia.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
      vDataEnvia.put("dtPago", (String)null);
      vDataEnvia.put("dImportePagado",0.0d);
      vDataEnvia.put("iCveBanco",0);
      vDataEnvia.put("cFormatoSAT","");
      vDataEnvia.put("lPagado",0);

      String[] cRefNum = null,cNumUnid = null,cRefAlfaNum = null,cImpPagar = null,
          cConcepto = null,cSolicIng = null,cFechaRef = null;
      if(!vData.getString("Mov_iRefNumerica").equals("") &&
         !vData.getString("Mov_iNumUnidades").equals("") &&
         !vData.getString("Mov_cRefAlfaNum").equals("") &&
         !vData.getString("Mov_dImportePagar").equals("") &&
         !vData.getString("Mov_iCveConcepto").equals("") &&
         !vData.getString("Mov_iCveSolicitanteIngresos").equals("") &&
         !vData.getString("Mov_dFechaRef").equals("")
         ){
        cRefNum = vData.getString("Mov_iRefNumerica").split(",");
        cNumUnid = vData.getString("Mov_iNumUnidades").split(",");
        cRefAlfaNum = vData.getString("Mov_cRefAlfaNum").split(",");
        cImpPagar = vData.getString("Mov_dImportePagar").split(",");
        cConcepto = vData.getString("Mov_iCveConcepto").split(",");
        cSolicIng = vData.getString("Mov_iCveSolicitanteIngresos").split(",");
        cFechaRef = vData.getString("Mov_dFechaRef").split(",");
      }

      if(cRefNum != null && cNumUnid != null && cRefAlfaNum != null &&
         cImpPagar != null && cConcepto != null){
        if(cRefNum.length != cNumUnid.length &&
           cRefNum.length != cRefAlfaNum.length &&
           cRefNum.length != cImpPagar.length &&
           cRefNum.length != cConcepto.length)
          cMensaje = "Las referencias numéricas a registrar no tienen correspondiente en unidades, alfanuméricas, importes o conceptos";
        else{
          for(int i = 0;i < cRefNum.length;i++){
            vDataEnvia.put("cRefNumerica",cRefNum[i]);
            vDataEnvia.put("iNumUnidades",cNumUnid[i]);
            vDataEnvia.put("cRefAlfaNum",cRefAlfaNum[i]);
            vDataEnvia.put("dImportePagar",cImpPagar[i]);
            vDataEnvia.put("iCveConcepto",cConcepto[i]);
            vDataEnvia.put("iCveSolicitanteIngresos",cSolicIng[i]);
            vDataEnvia.put("dtMovimiento",cFechaRef[i]);
            this.insert(vDataEnvia,null);
          }
        }
      } else
        cMensaje = "No se proporcionaron referencias a registrar";

    } catch(Exception e){
      e.printStackTrace();
      lSuccess = false;
      cMensaje = e.getMessage();
    } finally{
      if(lSuccess == false)
        throw new DAOException(cMensaje);
      return vData;
    }
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into TRARegRefPago(iEjercicio,iNumSolicitud,iConsecutivo,cRefNumerica,cRefAlfaNum,dImportePagar,dImportePagado,dtPago,iCveBanco,cFormatoSAT) values (?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iNumSolicitud,iConsecutivo, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   * Autor: iCaballero
   * 23.11.2006
   */
  public TVDinRep insertPagoSimple(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    TFechas tFecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into TRARegRefPago(iEjercicio,iNumSolicitud,iConsecutivo,iRefNumerica,cRefAlfaNum,dImportePagar," +
          "dImportePagado,dtPago,iCveBanco,cFormatoSAT,lPagado,iCveConcepto,iNumUnidades,dtMovimiento) " +
          "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("",
                                   "SELECT MAX(iConsecutivo) AS iConsecutivo FROM TRARegRefPago " +
                                   "WHERE iEjercicio = " +
                                   vData.getString("iEjercicio") +
                                   "  AND iNumSolicitud = " +
                                   vData.getString("iNumSolicitud"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivo",vUltimo.getInt("iConsecutivo") + 1);
      } else
        vData.put("iConsecutivo",1);
      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iNumSolicitud"));
      vData.addPK(vData.getString("iConsecutivo"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iConsecutivo"));
      lPStmt.setString(4,vData.getString("iRefNumerica"));
      lPStmt.setString(5,vData.getString("cRefAlfaNum"));
      lPStmt.setDouble(6,vData.getDouble("cImportePagar"));
      lPStmt.setDouble(7,vData.getDouble("dImportePagado"));
      lPStmt.setDate(8,vData.getDate("dtPago"));
      lPStmt.setInt(9,vData.getInt("iCveBanco"));
      lPStmt.setString(10,vData.getString("cFormatoSAT"));
      lPStmt.setInt(11,vData.getInt("lPagado"));
      lPStmt.setInt(12,vData.getInt("iCveConcepto"));
      lPStmt.setInt(13,vData.getInt("iNumUnidades"));
      lPStmt.setDate(14,tFecha.getDateSQL(vData.getString("dtPago")));
      lPStmt.executeUpdate();
      if(cnNested == null)
        conn.commit();

    } catch(SQLException e){
      lSuccess = false;
      cMensaje = super.getSQLMessages(e);
    } catch(Exception ex){
      cMensaje = "";
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
        if(lPStmt != null)
          lPStmt.close();
        if(cnNested == null){
          if(conn != null)
            conn.close();
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMensaje);
      return vData;
    }
  }


  /**
   * Métodos encargados de proporcionar los datos necesarios para obtener una referencia númerica,
   * registrar una referencia de pago y obtener los importes a pagar.
   * Autor: ICaballero
   * Fecha: 11-Oct-2006
   */

  public class TDDatosRefPago
      extends DAOBase{
    private TVDinRep vDato;
    private int iEjercicio,iNumSolicitud,iNumUnidades,iCveConcepto;

    public TDDatosRefPago(){
      super.setSistema(cSistema);
    }

    public void setDatosRefPago(TVDinRep vData){
      iEjercicio = vData.getInt("iEjercicio");
      iNumSolicitud = vData.getInt("iNumSolicitud");
      iNumUnidades = vData.getInt("iNumUnidades");
      iCveConcepto = vData.getInt("iCveConcepto");
      vDato = this.getDatosRefPago();
    }

    private TVDinRep getDatosRefPago(){
      Vector vRegsDatos = new Vector();
      StringBuffer sb = new StringBuffer();
      sb.append("SELECT ");
      sb.append("DTINIVIGENCIA, ");
      sb.append("ICATEGORIAINGRESOS, ");
      sb.append("ICONCEPTOINGRESOS, ");
      sb.append("IREFNUMERICAINGRESOS, ");
      sb.append("LESTARIFA, ");
      sb.append("LESPORCENTAJE, ");
      sb.append("DIMPORTESINAJUSTE, ");
      sb.append("DIMPORTEAJUSTADO, ");
      sb.append("DTFINVIGENCIA ");
      sb.append("FROM TRAREFERENCIACONCEPTO ");
      sb.append("where IEJERCICIO = " + iEjercicio);
      sb.append(" AND ICVECONCEPTO = " + iCveConcepto);
      sb.append(" ORDER BY DTINIVIGENCIA DESC ");

      try{
        vRegsDatos = super.FindByGeneric("",sb.toString(),super.dataSourceName);
      } catch(SQLException ex){
        cMensaje = ex.getMessage();
      } catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if(vRegsDatos.size() > 0)
        return(TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }

    public double getImportePagar(){
      double dImporteSinAjuste = vDato.getDouble("DIMPORTESINAJUSTE");
      double dImportePagar;
      String temp;
      String decTemp;
      double decimales = 0.0;
      double enteros = 0.0;
      double permitido = 51;

      String[] aEnterosDecimales;

      temp = String.valueOf(dImporteSinAjuste * iNumUnidades);

      aEnterosDecimales = temp.replace('.',',').split(",");
      enteros = Double.parseDouble(aEnterosDecimales[0]);

      decTemp = aEnterosDecimales[1];
      if(decTemp.length() >= 0 && decTemp.length() < 2)
        decTemp += "00";

      decimales = Double.parseDouble(decTemp.substring(0,2));

      if(decimales >= permitido)
        decimales = 1;
      else
        decimales = 0;

      dImportePagar = enteros + decimales;
      return dImportePagar;
    }

    public int getRefNumerica(){
      return vDato.getInt("IREFNUMERICAINGRESOS");
    }

    public void insertRefPago() throws  DAOException{
      DbConnection dbConn = null;
      Connection conn = null;
      TVDinRep vData = new TVDinRep();
      PreparedStatement lPStmt = null;
      boolean lSuccess = true;
      try{
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);

        String lSQL =
            "insert into TRARegRefPago(iEjercicio,iNumSolicitud,iConsecutivo,iRefNumerica,iNumUnidades,cRefAlfaNum,dImportePagar,lPagado,iCveConcepto) values (?,?,?,?,?,?,?,?,?)";

        Vector vcData = findByCustom("",
                                     "SELECT MAX(iConsecutivo) AS iConsecutivo FROM TRARegRefPago " +
                                     "WHERE iEjercicio = " + iEjercicio +
                                     "  AND iNumSolicitud = " + iNumSolicitud);
        if(vcData.size() > 0){
          TVDinRep vUltimo = (TVDinRep) vcData.get(0);
          vData.put("iConsecutivo",vUltimo.getInt("iConsecutivo") + 1);
        } else
          vData.put("iConsecutivo",1);

        vData.addPK(String.valueOf(iEjercicio));
        vData.addPK(String.valueOf(iNumSolicitud));
        vData.addPK(vData.getString("iConsecutivo"));
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,iEjercicio);
        lPStmt.setInt(2,iNumSolicitud);
        lPStmt.setInt(3,vData.getInt("iConsecutivo"));
        lPStmt.setInt(4,vDato.getInt("IREFNUMERICAINGRESOS"));
        lPStmt.setInt(5,iNumUnidades);
        lPStmt.setString(6," ");
        lPStmt.setDouble(7,this.getImportePagar());
        lPStmt.setInt(8,0);
        lPStmt.setInt(9,iCveConcepto);
        lPStmt.executeUpdate();

        conn.commit();

      } catch(SQLException e){
        lSuccess = false;
        cMensaje = super.getSQLMessages(e);
      } catch(Exception ex){
        cMensaje = "";
        warn("insert",ex);
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }

        lSuccess = false;
      } finally{
        try{
          if(lPStmt != null)
            lPStmt.close();

          if(conn != null)
            conn.close();
          dbConn.closeConnection();

        } catch(Exception ex2){
          warn("insert.close",ex2);
        }
        if(lSuccess == false)
          throw new DAOException(cMensaje);
      }
    }

  }


}

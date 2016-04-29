package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDGRLRegCausaPNC.java</p>
 * <p>Description: DAO de la entidad GRLRegCausaPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
public class TDGRLRegCausaPNC1 extends DAOBase{
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
   * <p><b> insert into GRLRegCausaPNC(iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveUsuCorrige,cDscOtraCausa,lResuelto,dtResolucion) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested, int iConsecutivo) throws
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
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      }
      String lSQL =
          "insert into GRLRegCausaPNC(iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveUsuCorrige,cDscOtraCausa,lResuelto,dtResolucion,cObsLey1,cObsLey2,cObsLey3) values (?,?,?,?,?,?,?,?,?,?,?)";
      String [] strCveCausaPNC = vData.getString("iCveCausaPNC").split(",");
      String [] strCveUsuCorrige = vData.getString("iCveUsuCorrige").split(",");
      for(int i=0;i< strCveCausaPNC.length && vData.getString("iCveCausaPNC").compareTo("")!=0;i++){
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEjercicio"));
        lPStmt.setInt(2,iConsecutivo);
        lPStmt.setInt(3,vData.getInt("iCveProducto"));
        lPStmt.setInt(4,Integer.parseInt(strCveCausaPNC[i]));
        lPStmt.setInt(5,Integer.parseInt("0"));
        lPStmt.setString(6,"");
        lPStmt.setInt(7,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(8,Types.DATE);  
        else
          lPStmt.setDate(8,vData.getDate("dtResolucion"));
        lPStmt.setString(9,vData.getString("cObsLey1"));
        lPStmt.setString(10,vData.getString("cObsLey2"));
        lPStmt.setString(11,vData.getString("cObsLey3"));
        System.out.print(">>>TDGRLRegCausaPNC1  1:"+vData.getString("cObsLey1"));
        System.out.print(">>>TDGRLRegCausaPNC1  2:"+vData.getString("cObsLey2"));
        System.out.print(">>>TDGRLRegCausaPNC1  3:"+vData.getString("cObsLey3"));
        lPStmt.executeUpdate();
        lPStmt.close();
      }
      if(vData.getString("cDscOtraCausa").compareTo("") != 0){
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEjercicio"));
        lPStmt.setInt(2,iConsecutivo);
      //  lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
        lPStmt.setInt(3,vData.getInt("iCveProducto"));
        lPStmt.setInt(4,0);
        lPStmt.setInt(5,Integer.parseInt("0"));
        lPStmt.setString(6,vData.getString("cDscOtraCausa"));
        lPStmt.setInt(7,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(8,Types.DATE);
        else
          lPStmt.setDate(8,vData.getDate("dtResolucion"));
        
        lPStmt.setString(9,vData.getString("cObsLey1"));
        lPStmt.setString(10,vData.getString("cObsLey2"));
        lPStmt.setString(11,vData.getString("cObsLey3"));
        
        lPStmt.executeUpdate();
        lPStmt.close();
      }
      System.out.print("\n\n\n-------------  Por llamar a ReqCausa en insert");
      TDTRARegReqXCausa trReqCausa = new TDTRARegReqXCausa();
      trReqCausa.insertMult(vData,conn);

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
   * Inserta el registro dado buscando su existencia primero por la entidad vData.
   * <p><b> insert into GRLRegCausaPNC(iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveUsuCorrige,cDscOtraCausa,lResuelto,dtResolucion) values (?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insertA(TVDinRep vData,Connection cnNested, int iConsecutivo) throws
      DAOException{
  //  System.out.print("\n\n\n>>>>>>>>>>>>>>>>>>>>>   En insert A");
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
  //  PreparedStatement lPStmt2 = null;
    boolean lSuccess = true;
    boolean lInsertaCausa = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
//        conn.setTransactionIsolation(2);
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      }
      String lSQL =
          "insert into GRLRegCausaPNC(iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC,iCveUsuCorrige,cDscOtraCausa,lResuelto,dtResolucion,cObsLey1,cObsLey2,cObsLey3) values (?,?,?,?,?,?,?,?,?,?,?)";
    /*  String lSQL2 =
          "insert into TRARegReqXCausa(iEjercicio,iConsecutovoPNC,iCveProducto,iCveCausaPNC,iCveRequisito) values (?,?,?,?,?)";
*/
      String [] strCveCausaPNC = vData.getString("iCveCausaPNC").split(",");
      String [] strCveUsuCorrige = vData.getString("iCveUsuCorrige").split(",");
      Vector vcDataA;
      for(int i=0;i< strCveCausaPNC.length && (vData.getString("iCveCausaPNC")).compareTo("")!=0 ;i++){
         vcDataA = findByCustom("","SELECT " +
            "GRLREGCAUSAPNC.ICVECAUSAPNC " +
            "FROM GRLREGCAUSAPNC " +
            "where GRLREGCAUSAPNC.IEJERCICIO = " + vData.getInt("iEjercicio") +
            " and GRLREGCAUSAPNC.ICONSECUTIVOPNC = " + iConsecutivo +
            " and GRLREGCAUSAPNC.ICVEPRODUCTO = " + vData.getInt("iCveProducto") +
            " and GRLREGCAUSAPNC.ICVECAUSAPNC = " + Integer.parseInt(strCveCausaPNC[i]));

            if(vcDataA.size() > 0)
               lInsertaCausa = false;
            else
               lInsertaCausa = true;
        lPStmt = conn.prepareStatement(lSQL);
  //      lPStmt2 = conn.prepareStatement(lSQL2);
        lPStmt.setInt(1,vData.getInt("iEjercicio"));
    //    lPStmt2.setInt(1,vData.getInt("iEjercicio"));
        lPStmt.setInt(2,iConsecutivo);
   //     lPStmt2.setInt(2,iConsecutivo);
        lPStmt.setInt(3,vData.getInt("iCveProducto"));
   //     lPStmt2.setInt(3,vData.getInt("iCveProducto"));
        lPStmt.setInt(4,Integer.parseInt(strCveCausaPNC[i]));
  //      lPStmt2.setInt(4,Integer.parseInt(strCveCausaPNC[i]));
        lPStmt.setInt(5,Integer.parseInt("0"));
  //      lPStmt2.setInt(5,vData.getInt("iCveRequisito"));
        lPStmt.setString(6,"");
        lPStmt.setInt(7,vData.getInt("lResuelto"));
   //     System.out.print("A lResuelto: " + vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(8,Types.DATE);
        else
          lPStmt.setDate(8,vData.getDate("dtResolucion"));

        lPStmt.setString(9,vData.getString("cObsLey1"));
        lPStmt.setString(10,vData.getString("cObsLey2"));
        lPStmt.setString(11,vData.getString("cObsLey3"));
        if(lInsertaCausa == true){
          lPStmt.executeUpdate();
    //      lPStmt2.executeUpdate();
        }
        lPStmt.close();
      }
      if(vData.getString("cDscOtraCausa").compareTo("") != 0 &&
         vData.getString("cDscOtraCausa").compareTo("null") != 0){
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,vData.getInt("iEjercicio"));
        lPStmt.setInt(2,iConsecutivo);
      //  lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
        lPStmt.setInt(3,vData.getInt("iCveProducto"));
        lPStmt.setInt(4,0);
        lPStmt.setInt(5,Integer.parseInt("0"));
        lPStmt.setString(6,vData.getString("cDscOtraCausa"));
        lPStmt.setInt(7,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(8,Types.DATE);
        else
          lPStmt.setDate(8,vData.getDate("dtResolucion"));
        lPStmt.setString(9,vData.getString("cObsLey1"));
        lPStmt.setString(10,vData.getString("cObsLey2"));
        lPStmt.setString(11,vData.getString("cObsLey3"));
        lPStmt.executeUpdate();
        lPStmt.close();
      }

      if(cnNested == null){
        conn.commit();
    //    System.out.print("\n\n\n ----------------------Por llamar en insertA a ReqCausa");
   /*     TDTRARegReqXCausa trReqCausa = new TDTRARegReqXCausa();
        trReqCausa.insertMult(vData,conn,iConsecutivo); */
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
   * <p><b> delete from GRLRegCausaPNC where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
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
      String lSQL = "delete from GRLRegCausaPNC where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(3,vData.getInt("iCveProducto"));
      lPStmt.setInt(4,vData.getInt("iCveCausaPNC"));

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
   * <p><b> update GRLRegCausaPNC set iCveUsuCorrige=?, cDscOtraCausa=?, lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
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
      String lSQL = "update GRLRegCausaPNC set iCveUsuCorrige=?, cDscOtraCausa=?, lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iConsecutivoPNC"));
      vData.addPK(vData.getString("iCveProducto"));
      vData.addPK(vData.getString("iCveCausaPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveUsuCorrige"));
      lPStmt.setString(2,vData.getString("cDscOtraCausa"));
      lPStmt.setInt(3,vData.getInt("lResuelto"));
      lPStmt.setDate(4,vData.getDate("dtResolucion"));
      lPStmt.setInt(5,vData.getInt("iEjercicio"));
      lPStmt.setInt(6,vData.getInt("iConsecutivoPNC"));
      lPStmt.setInt(7,vData.getInt("iCveProducto"));
      lPStmt.setInt(8,vData.getInt("iCveCausaPNC"));
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
 /**
 * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
 * <p><b> update GRLRegCausaPNC set iCveUsuCorrige=?, cDscOtraCausa=?, lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  </b></p>
 * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
 * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
 * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
 * @throws DAOException       - Excepción de tipo DAO
 * @return TVDinRep           - Entidad Modificada.
 */
 public TVDinRep resolver(TVDinRep vData,Connection cnNested) throws
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
    String lSQL = "update GRLRegCausaPNC set iCveUsuCorrige=?, lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ?";
    TFechas tFecha = new TFechas();
    vData.addPK(vData.getString("iEjercicio"));
    vData.addPK(vData.getString("iConsecutivoPNC"));
    vData.addPK(vData.getString("iCveProducto"));
    vData.addPK(vData.getString("iCveCausaPNC"));

    lPStmt = conn.prepareStatement(lSQL);
    lPStmt.setInt(1,vData.getInt("iCveUsuCorrige"));
    lPStmt.setInt(2,1);
    lPStmt.setDate(3,tFecha.TodaySQL());
    lPStmt.setInt(4,vData.getInt("iEjercicio"));
    lPStmt.setInt(5,vData.getInt("iConsecutivoPNC"));
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

/**
* Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
* <p><b> update GRLRegCausaPNC set iCveUsuCorrige=?, cDscOtraCausa=?, lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveProducto = ? AND iCveCausaPNC = ?  </b></p>
* <p><b> Campos Llave: iEjercicio,iConsecutivoPNC,iCveProducto,iCveCausaPNC, </b></p>
* @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
* @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
* @throws DAOException       - Excepción de tipo DAO
* @return TVDinRep           - Entidad Modificada.
*/
public TVDinRep updateCausas(TVDinRep vData,Connection cnNested) throws
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
   String lSQL = "update GRLRegCausaPNC set cDscOtraCausa=? where iEjercicio = ? AND iConsecutivoPNC = ? AND iCveCausaPNC = 0 ";
   TFechas tFecha = new TFechas();
   vData.addPK(vData.getString("iEjercicio"));
   vData.addPK(vData.getString("iConsecutivoPNC"));
  // vData.addPK(vData.getString("iCveProducto"));
   vData.addPK(vData.getString("iCveCausaPNC"));

   lPStmt = conn.prepareStatement(lSQL);
   lPStmt.setString(1,vData.getString("cDscOtraCAusa"));
   lPStmt.setInt(2,vData.getInt("iEjercicio"));
   lPStmt.setInt(3,vData.getInt("iConsecutivoPNC"));
//   lPStmt.setInt(6,vData.getInt("iCveProducto"));
//   lPStmt.setInt(7,vData.getInt("iCveCausaPNC"));
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

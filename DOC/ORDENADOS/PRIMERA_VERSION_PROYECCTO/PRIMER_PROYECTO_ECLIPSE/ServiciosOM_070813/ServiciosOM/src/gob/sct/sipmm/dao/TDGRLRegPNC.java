package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDGRLRegPNC.java</p>
 * <p>Description: DAO de la entidad GRLRegistroPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Levi Equihua
 * @version 1.0
 */
public class TDGRLRegPNC extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas tFecha = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private int iConsecutivo;
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
   * <p><b> insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
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
    TFechas dtFechaActual = new TFechas();

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado,iCveOficina,iCveDepartamento,iCveProceso) values (?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " + vData.getInt("iEjercicio"));
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
      }else
         vData.put("iConsecutivoPNC",1);
      vData.addPK(vData.getString("iConsecutivoPNC"));
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,dtFechaActual.getIntYear(dtFechaActual.TodaySQL()));
        lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
        iConsecutivo = vData.getInt("iConsecutivoPNC");
        lPStmt.setDate(3,tFecha.TodaySQL());
        lPStmt.setInt(4,vData.getInt("iCveUsuario"));
        lPStmt.setInt(5,vData.getInt("iCveProducto"));
        lPStmt.setInt(6,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(7,Types.DATE);
        else
          lPStmt.setDate(7,vData.getDate("dtResolucion"));
        lPStmt.setInt(8,vData.getInt("iCveOficinaUsrAsg"));
        lPStmt.setInt(9,vData.getInt("iCveDeptoUsrAsg"));
        lPStmt.setInt(10,vData.getInt("iCveOficinaUsr"));
        lPStmt.setInt(11,vData.getInt("iCveDeptoUsr"));
        lPStmt.setInt(12,vData.getInt("iCveProceso"));

        lPStmt.executeUpdate();
        lPStmt.close();
        try{
          TDGRLRegCausaPNC1 dGRLRegCausaPNC = new TDGRLRegCausaPNC1();
          dGRLRegCausaPNC.insert(vData,conn,iConsecutivo);
        }catch(Exception exCausa){
          exCausa.printStackTrace();
          lSuccess = false;
          throw new Exception("Error Causa");
        }
        if(cnNested == null && lSuccess)
          conn.commit();
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
        throw new DAOException("");
      return vData;
    }
  }


  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
   * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
   * @throws DAOException       - Excepci�n de tipo DAO
   * @return TVDinRep           - VO Din�mico que contiene a la entidad a Insertada, as� como a la llave de esta entidad.
   */
  public TVDinRep insertExiste(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    boolean lAgregarAPNC = false;
    boolean lInsertaCausa = false;
    TFechas dtFechaActual = new TFechas();
    int iNumSolicitud, iCveTramite, iCveModalidad = 0;
    int iConsecutivoPNC = 0;
    int iEjercicio = 0;
    try{
      //LEL26092006
      Vector vcDataA = findByCustom("","SELECT " +
         "TRAREGPNCETAPA.IEJERCICIOPNC, " +
         "TRAREGPNCETAPA.INUMSOLICITUD, " +
         "TRAREGPNCETAPA.ICONSECUTIVOPNC, " +
         "TRAREGPNCETAPA.ICVETRAMITE, " +
         "TRAREGPNCETAPA.ICVEMODALIDAD " +
         "FROM TRAREGPNCETAPA " +
         "where TRAREGPNCETAPA.IEJERCICIO = " + vData.getInt("iEjercicio") +
         " and TRAREGPNCETAPA.INUMSOLICITUD = "+ vData.getInt("iNumSolicitud") +
         " ORDER BY ICONSECUTIVOPNC DESC ");
      TVDinRep vSoli;
      if(vcDataA.size() > 0){
        vSoli = (TVDinRep) vcDataA.get(0);
        iEjercicio = vSoli.getInt("IEJERCICIOPNC");
        iNumSolicitud = vSoli.getInt("INUMSOLICITUD");
        iConsecutivoPNC = vSoli.getInt("ICONSECUTIVOPNC");
        iCveTramite = vSoli.getInt("ICVETRAMITE");
        iCveModalidad = vSoli.getInt("ICVEMODALIDAD");
        vcDataA = null;
        vcDataA = findByCustom("","SELECT " +
                  "DTNOTIFICACION FROM TRAREGREQXTRAM " +
                  "WHERE IEJERCICIO = " + iEjercicio +
                  " AND INUMSOLICITUD = " + iNumSolicitud +
                  " AND ICVETRAMITE = " + iCveTramite +
                  " AND ICVEMODALIDAD = " + iCveModalidad +
                  " AND LTIENEPNC = 1");
        lAgregarAPNC = true;
        for(int i=0; i < vcDataA.size() && lAgregarAPNC == false; i++){
          vSoli = (TVDinRep) vcDataA.get(i);
          if(vSoli.getDate("DTNOTIFICACION") == null)
            lAgregarAPNC = true;
          else
            lAgregarAPNC = false;
        }
      }
      if(lAgregarAPNC){
        TVDinRep vResuelto;
        Vector vcDataR = findByCustom("","SELECT lResuelto " +
                                      "FROM GRLREGISTROPNC WHERE " +
                                      "IEJERCICIO = " + iEjercicio +
                                      " AND ICONSECUTIVOPNC = " + iConsecutivoPNC);
        if(vcDataR.size() > 0){
          vResuelto = (TVDinRep) vcDataR.get(0);
          if(vResuelto.getInt("lResuelto") != 0)
            lAgregarAPNC = false;
        }
      }
      //FinLEL26092006
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado,iCveOficina,iCveDepartamento,iCveProceso) values (?,?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      if(lAgregarAPNC == false){
        Vector vcData = findByCustom("",
                                     "select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " +
                                     vData.getInt("iEjercicio"));
        if(vcData.size() > 0){
          TVDinRep vUltimo = (TVDinRep) vcData.get(0);
          vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
        } else
          vData.put("iConsecutivoPNC",1);
      }else{
        vData.put("iConsecutivoPNC",iConsecutivoPNC);
      }
      vData.addPK(vData.getString("iConsecutivoPNC"));
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1,dtFechaActual.getIntYear(dtFechaActual.TodaySQL()));
        lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
        iConsecutivo = vData.getInt("iConsecutivoPNC");
        lPStmt.setDate(3,tFecha.TodaySQL());
        lPStmt.setInt(4,vData.getInt("iCveUsuario"));
        lPStmt.setInt(5,vData.getInt("iCveProducto"));
        lPStmt.setInt(6,vData.getInt("lResuelto"));
        if(vData.getDate("dtResolucion") == null)
          lPStmt.setNull(7,Types.DATE);
        else
          lPStmt.setDate(7,vData.getDate("dtResolucion"));
        lPStmt.setInt(8,vData.getInt("iCveOficinaUsrAsg"));
        lPStmt.setInt(9,vData.getInt("iCveDeptoUsrAsg"));
        lPStmt.setInt(10,vData.getInt("iCveOficinaUsr"));
        lPStmt.setInt(11,vData.getInt("iCveDeptoUsr"));
        lPStmt.setInt(12,vData.getInt("iCveProceso"));
        

        System.out.println(">>>TDRELRegPNC  1:"+vData.getString("cObsLey1"));
        System.out.println(">>>TDRELRegPNC  2:"+vData.getString("cObsLey2"));
        System.out.println(">>>TDRELRegPNC  3:"+vData.getString("cObsLey3"));

        if(lAgregarAPNC == false)
           lPStmt.executeUpdate();
        lPStmt.close();
        try{
          TDGRLRegCausaPNC1 dGRLRegCausaPNC = new TDGRLRegCausaPNC1();
      //    TDGRLRegCausaPNC dGRLRegCausaPNC = new TDGRLRegCausaPNC();
          if(lAgregarAPNC == false){
            dGRLRegCausaPNC.insert(vData,conn,iConsecutivo);
          //   dGRLRegCausaPNC.insert(vData,conn);
          }else{
              dGRLRegCausaPNC.insertA(vData,conn,iConsecutivo);
            //   dGRLRegCausaPNC.insertA(vData,conn);
          }
        }catch(Exception exCausa){
          exCausa.printStackTrace();
          lSuccess = false;
        }
        if(cnNested == null && lSuccess)
          conn.commit();
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
        throw new DAOException("");
      return vData;
    }
  }



  /**
   * Elimina al registro a trav�s de la entidad dada por vData.
   * <p><b> delete from GRLRegistroPNC where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
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
      String lSQL = "delete from GRLRegistroPNC where iEjercicio = ? AND iConsecutivoPNC = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));

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
   * <p><b> update GRLRegistroPNC set dtRegistro=?, iCveUsuRegistro=?, iCveProducto=?, lResuelto=?, dtResolucion=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
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
      String lSQL = "update GRLRegistroPNC set dtRegistro=?, iCveUsuRegistro=?, iCveProducto=?, lResuelto=?, dtResolucion=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iConsecutivoPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtRegistro"));
      lPStmt.setInt(2,vData.getInt("iCveUsuRegistro"));
      lPStmt.setInt(3,vData.getInt("iCveProducto"));
      lPStmt.setInt(4,vData.getInt("lResuelto"));
      lPStmt.setDate(5,vData.getDate("dtResolucion"));
      lPStmt.setInt(6,vData.getInt("iCveOficinaAsignado"));
      lPStmt.setInt(7,vData.getInt("iCveDeptoAsignado"));
      lPStmt.setInt(8,vData.getInt("iEjercicio"));
      lPStmt.setInt(9,vData.getInt("iConsecutivoPNC"));
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
  * Actualiza el campo de Producto No Conforme de la persona que recibe la notificaci�n.
  * <p><b> update GRLRegistroPNC set cRecibeNotif=? where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
  * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
  * @param vData TVDinRep      - VO Din�mico que contiene a la entidad a Insertar.
  * @param cnNested Connection - Conexi�n anidada que permite que el m�todo se encuentre dentro de una transacci�n mayor.
  * @throws DAOException       - Excepci�n de tipo DAO
  * @return TVDinRep           - Entidad Modificada.
  */
 public TVDinRep updateRecibe(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   int iConsecutivoPNC = 0,iEjercicioPNC=0;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     Vector vcPNC = findByCustom("","SELECT " +
                              "iEjercicioPNC,ICONSECUTIVOPNC " +
                              "FROM TRAREGPNCETAPA " +
                              "where IEJERCICIO = " + vData.getInt("iEjercicio")+
                              " and INUMSOLICITUD = "+ vData.getInt("iNumSolicitud") +
                              " order by iorden desc");
     if(vcPNC.size() > 0){
       TVDinRep vRegPNC = (TVDinRep) vcPNC.get(0);
       iConsecutivoPNC = vRegPNC.getInt("ICONSECUTIVOPNC");
       iEjercicioPNC = vRegPNC.getInt("iEjercicioPNC");
     }

     String lSQL = "update GRLRegistroPNC set cRecibeNotif=?,dtNotificacion=? where iEjercicio = ? AND iConsecutivoPNC = ? ";

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setString(1,vData.getString("cRecibeNotif"));
     lPStmt.setDate(2,vData.getDate("dtNotificacion"));
     lPStmt.setInt(3,iEjercicioPNC);
     lPStmt.setInt(4,iConsecutivoPNC);
     lPStmt.executeUpdate();
     /*if(cnNested == null){
       conn.commit();
       TDVerificacion tdVer = new TDVerificacion();
       tdVer.upNotificacion(vData, cnNested);
     }*/
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


 //FUNCION Obtiene el valor del consecutivo:
  public int getConsecutivoPNC(){
     return iConsecutivo;
  }
}



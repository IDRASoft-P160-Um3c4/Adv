package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDGRLRegistroPNC.java</p>
 * <p>Description: DAO de la entidad GRLRegistroPNC</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina; Levi Equihua
 * @version 1.1
 */
public class TDGRLRegistroPNC extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas tFecha = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private int iConsecutivo;
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
   * <p><b> insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
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
          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("",
                                   "select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " +
                                   vData.getInt("iEjercicio"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
      } else
        vData.put("iConsecutivoPNC",1);
      vData.addPK(vData.getString("iConsecutivoPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setDate(3,tFecha.TodaySQL());
      lPStmt.setInt(4,vData.getInt("iCveUsuRegistro"));
      lPStmt.setInt(5,vData.getInt("iCveProducto"));
      lPStmt.setInt(6,vData.getInt("lResuelto"));
      if(vData.getDate("dtResolucion") == null)
        lPStmt.setNull(7,Types.DATE);
      else
        lPStmt.setDate(7,vData.getDate("dtResolucion"));
      lPStmt.setInt(8,vData.getInt("iCveOficinaAsignado"));
      lPStmt.setInt(9,vData.getInt("iCveDeptoAsignado"));

      lPStmt.executeUpdate();

      try{
        TDGRLRegCausaPNC dGRLRegCausaPNC = new TDGRLRegCausaPNC();
        dGRLRegCausaPNC.insert(vData,conn);
      } catch(Exception exCausa){
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
  
  
  
  
  /*  RESPALDO DE LA FUNCION ORIGINAL DE INSERTAR EN GRLREGISTROPNC 
   * 
   *  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
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
          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("",
                                   "select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " +
                                   vData.getInt("iEjercicio"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
      } else
        vData.put("iConsecutivoPNC",1);
      vData.addPK(vData.getString("iConsecutivoPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setDate(3,tFecha.TodaySQL());
      lPStmt.setInt(4,vData.getInt("iCveUsuRegistro"));
      lPStmt.setInt(5,vData.getInt("iCveProducto"));
      lPStmt.setInt(6,vData.getInt("lResuelto"));
      if(vData.getDate("dtResolucion") == null)
        lPStmt.setNull(7,Types.DATE);
      else
        lPStmt.setDate(7,vData.getDate("dtResolucion"));
      lPStmt.setInt(8,vData.getInt("iCveOficinaAsignado"));
      lPStmt.setInt(9,vData.getInt("iCveDeptoAsignado"));

      lPStmt.executeUpdate();

      try{
        TDGRLRegCausaPNC dGRLRegCausaPNC = new TDGRLRegCausaPNC();
        dGRLRegCausaPNC.insert(vData,conn);
      } catch(Exception exCausa){
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
   * 
   * 
   * 
   */

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion,iCveOficinaAsignado,iCveDeptoAsignado) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insertCaracA(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    TVDinRep vData1 = new TVDinRep();
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    TFechas tFecha = new TFechas();
    int iEjercicio = 0;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into GRLRegistroPNC(iEjercicio,iConsecutivoPNC,dtRegistro,iCveUsuRegistro,iCveProducto,lResuelto,dtResolucion) values (?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("",
                                   "select MAX(iConsecutivoPNC) AS iConsecutivoPNC from GRLRegistroPNC WHERE iEjercicio = " +
                                   vData.getInt("iEjercicio"));
      if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iConsecutivoPNC",vUltimo.getInt("iConsecutivoPNC") + 1);
      } else
        vData.put("iConsecutivoPNC",1);
      vData.addPK(vData.getString("iConsecutivoPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iConsecutivoPNC"));
      lPStmt.setDate(3,tFecha.TodaySQL());
      lPStmt.setInt(4,vData.getInt("iCveUsuRegistro"));
      lPStmt.setInt(5,vData.getInt("iCveProducto"));
      lPStmt.setInt(6,vData.getInt("lResuelto"));
      lPStmt.setDate(7,tFecha.TodaySQL());

      lPStmt.executeUpdate();
      //Agrega Causas de PNC
      try{
//        vData1 = vData;
        vData.put("iCveCausaPNC","2,1,");
        vData.put("iCveUsuCorrige",vData.getInt("iCveUsuRegistro")+","+vData.getInt("iCveUsuRegistro")+",");
        vData.put("dtResolucion",tFecha.TodaySQL());
        TDGRLRegCausaPNC dGRLRegCausaPNC = new TDGRLRegCausaPNC();
        dGRLRegCausaPNC.insert(vData,conn);
     //   if(lPStmt != null)
           lPStmt.close();
      } catch(Exception exCausa){
        exCausa.printStackTrace();
        lSuccess = false;
      }
      // Agrega Requisito en PNC
      try{
         TDTRARegReqXCausa dGRLReqCausa = new TDTRARegReqXCausa();
         dGRLReqCausa.insertMulti(vData,conn);
       //  if(lPStmt != null)
            lPStmt.close();

      } catch(Exception exCausa){
         exCausa.printStackTrace();
         lSuccess = false;
      }

      // Agrega Características por Requisito en PNC
      try{
         TDTRARegCaracXReqPNC dGRLCaracReqPNC = new TDTRARegCaracXReqPNC();
         dGRLCaracReqPNC.insertMulti(vData,conn);
        // if(lPStmt != null)
            lPStmt.close();
      } catch(Exception exCausa){
         exCausa.printStackTrace();
         lSuccess = false;
      }

      //Agregar Etapas
      try{
        TDTRARegPNCEtapa dTraRegPNCEtapa = new TDTRARegPNCEtapa();
        dTraRegPNCEtapa.insert(vData,conn);
       // if(lPStmt != null)
           lPStmt.close();

      } catch(Exception exCausa){
        exCausa.printStackTrace();
        lSuccess = false;
      }

      if(cnNested == null && lSuccess){
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


  public TVDinRep insertCarac(TVDinRep vData,Connection cnNested) throws
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
         System.out.print("*****    lAgregarAPNC:  "+lAgregarAPNC);
        if(lAgregarAPNC == false)
           lPStmt.executeUpdate();
        lPStmt.close();

        //Agrega Causas de PNC
        try{
          if(vData.getInt("lResuelto")==0){
            System.out.print("*****   0   " + vData.getString("cCveCarac"));
            vData.put("iCveCausaPNC",vData.getString("cCveCarac"));
            vData.put("iCveUsuCorrige","0,0,");
            vData.put("dtResolucion","null");
          }
          else{
            //        vData1 = vData;
            System.out.print("*****   1   " + vData.getString("cCveCarac"));
            vData.put("iCveCausaPNC",vData.getString("cCveCarac"));
            vData.put("iCveUsuCorrige",
                      vData.getInt("iCveUsuRegistro") + "," +
                      vData.getInt("iCveUsuRegistro") + ",");
            vData.put("dtResolucion",tFecha.TodaySQL());
          }
          TDGRLRegCausaPNC1 dGRLRegCausaPNC = new TDGRLRegCausaPNC1();
          dGRLRegCausaPNC.insertA(vData,conn,iConsecutivo);
       //   if(lPStmt != null)
             lPStmt.close();
        } catch(Exception exCausa){
          exCausa.printStackTrace();
          lSuccess = false;
        }
        // Agrega Requisito en PNC
        try{
           TDTRARegReqXCausa dGRLReqCausa = new TDTRARegReqXCausa();
           dGRLReqCausa.insertCarac(vData,conn);
         //  if(lPStmt != null)
              lPStmt.close();

        } catch(Exception exCausa){
           exCausa.printStackTrace();
           lSuccess = false;
        }

        // Agrega Características por Requisito en PNC
        try{
           TDTRARegCaracXReqPNC dGRLCaracReqPNC = new TDTRARegCaracXReqPNC();
           dGRLCaracReqPNC.insertMulti(vData,conn);
          // if(lPStmt != null)
              lPStmt.close();
        } catch(Exception exCausa){
           exCausa.printStackTrace();
           lSuccess = false;
        }

        //Agregar Etapas
        try{
          if(lAgregarAPNC == false){
             TDTRARegPNCEtapa dTraRegPNCEtapa = new TDTRARegPNCEtapa();
             dTraRegPNCEtapa.insert(vData,conn);
         // if(lPStmt != null)
               lPStmt.close();
          }else{
          }
        } catch(Exception exCausa){
          exCausa.printStackTrace();
          lSuccess = false;
        }

        //Agrega lTienePNC
        TDVerificacion dTDVerifica = new TDVerificacion();
        dTDVerifica.updateReq(vData,conn);

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
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from GRLRegistroPNC where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
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
      String lSQL =
          "delete from GRLRegistroPNC where iEjercicio = ? AND iConsecutivoPNC = ?  ";
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
   * <p><b> update GRLRegistroPNC set dtRegistro=?, iCveUsuRegistro=?, iCveProducto=?, lResuelto=?, dtResolucion=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
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
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update GRLRegistroPNC set dtRegistro=?, iCveUsuRegistro=?, iCveProducto=?, lResuelto=?, dtResolucion=?, iCveOficinaAsignado=?, iCveDeptoAsignado=? where iEjercicio = ? AND iConsecutivoPNC = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iConsecutivoPNC, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update2(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update GRLRegistroPNC set lResuelto=?, dtResolucion=? where iEjercicio = ? AND iConsecutivoPNC = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iConsecutivoPNC"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lResuelto"));
      lPStmt.setDate(2,vData.getDate("dtFechaRes"));
      lPStmt.setInt(3,vData.getInt("iEjercicio"));
      lPStmt.setInt(4,vData.getInt("iConsecutivoPNC"));
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

  //FUNCION Obtiene el valor del consecutivo:
 public int getConsecutivoPNC(){
    return iConsecutivo;
 }
}

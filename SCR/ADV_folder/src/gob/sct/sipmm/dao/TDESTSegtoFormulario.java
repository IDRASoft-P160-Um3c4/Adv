package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*; import com.micper.util.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDESTSegtoFormulario.java</p>
 * <p>Description: DAO de la entidad ESTSegtoFormulario</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Angel Zamora P.
 * @version 1.0
 */
public class TDESTSegtoFormulario extends DAOBase{
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
 public Vector findByGenericLocal(String cKey,String cSQL,
                                 String dataSourceName, Connection cnNested) throws SQLException{
  dataSourceName = dataSourceName;
  DbConnection dbConn = null;
  Connection conn = cnNested;
  PreparedStatement pstmt = null;
  ResultSet rset = null;
  Vector vcDinRep = new Vector();
  try{
    dbConn = new DbConnection(dataSourceName);
    if(conn == null){
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }

    pstmt = conn.prepareStatement(cSQL);
    rset = pstmt.executeQuery();

    vcDinRep = this.getVO(cKey,rset);

  } catch(SQLException ex){
    cError = cMensaje = getSQLMessages(ex);
  } catch(Exception ex){
    cError = ex.toString();
    ex.printStackTrace();
  } finally{
    try{
      if(rset != null)
        rset.close();

      if(pstmt != null)
        pstmt.close();

      dbConn.closeConnection();
    } catch(Exception ex2){
      warn("FindByGenericLocal.close",ex2);
    }
    if(!cError.equals(""))
      throw new SQLException(cError);
    return vcDinRep;
  }

}

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into ESTSegtoFormulario(iEjercicioSeguimiento,iNumMovtoSegto,iCveTitulo,iCveTitular,iCveFormulario,iEjercicioReportado,iPeriodoReportado,dtLimitePresentacion,dtEnvioDGFYDP) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto, </b></p>
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
    PreparedStatement lPStmt1 = null;
    TFechas tFecha = new TFechas();
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
      }

      // Se obtiene el ejercicio(año) del servidor
      TFechas tfSol = new TFechas("44");
      java.sql.Date dtHoy = new java.sql.Date(new java.util.Date().getTime());
      String cDmy = tfSol.getFechaDDMMYYYY(dtHoy,"/");
      String cEjercicio = cDmy.substring(6);

      String lSQL =
          "insert into ESTSegtoFormulario(iEjercicioSeguimiento,iNumMovtoSegto,iCveTitulo,iCveTitular,iCveFormulario,iEjercicioReportado,iPeriodoReportado,dtLimitePresentacion,dtEnvioDGFYDP,iEstatusFormulario,lBloqueado) values (?,?,?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = this.findByGenericLocal("","select MAX(iNumMovtoSegto) AS iNumMovtoSegto from ESTSegtoFormulario where iEjercicioSeguimiento = " + cEjercicio,dataSourceName,conn);
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iNumMovtoSegto",vUltimo.getInt("iNumMovtoSegto") + 1);
      }else
         vData.put("iNumMovtoSegto",1);

      vData.addPK(cEjercicio);
      vData.addPK(vData.getString("iNumMovtoSegto"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,cEjercicio);
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.setInt(3,vData.getInt("iCveTitulo"));
      lPStmt.setInt(4,vData.getInt("iCveTitular"));
      lPStmt.setInt(5,vData.getInt("iCveFormulario"));
      lPStmt.setInt(6,vData.getInt("iEjercicioReportado"));
      lPStmt.setInt(7,vData.getInt("iPeriodoReportado"));
      lPStmt.setDate(8,vData.getDate("dtLimitePresentacion"));
      lPStmt.setDate(9,vData.getDate("dtEnvioDGFYDP"));
      //lPStmt.setDate(10,vData.getDate("dtAprobacionInformacion"));
      lPStmt.setInt(10,vData.getInt("iEstatusFormulario"));
      lPStmt.setInt(11,vData.getInt("lBloqueado"));

      lPStmt.executeUpdate();

      String cProgramaFor=vData.getString("cPrograFor");
      if(vData.getString("cPrograFor")!=null && (vData.getString("cPrograFor").equalsIgnoreCase("pg119420160") || vData.getString("cPrograFor").equalsIgnoreCase("pg119420300"))){

        Vector vcConceptoCont = this.findByGenericLocal("","SELECT ICVECONCEPTOCONTABLE,CDSCCONCEPTOCONTABLE,ICVECONCEPTOCONTABLEPADRE FROM ESTCONCEPTOCONTABLE where ICVECONCEPTOCONTABLE >0 and LVIGENTE = 1",dataSourceName,conn);
        for(int i=0;i<vcConceptoCont.size();i++){
          TVDinRep vConceptoCont = (TVDinRep) vcConceptoCont.get(i);
          double dEstimado = 0.0;

          String cPlanFinanciera =
              "INSERT INTO ESTPLANFINANCIERA (IEJERCICIOSEGUIMIENTO,INUMMOVTOSEGTO,ICVECONCEPTOCONTABLE,ICVECONCEPTOCONTABLEPADRE, " +
              "DPROGRAMADOACUMULADO,DREALACUMULADO,DPROGRAMADOMES, drealmes, destimadocierreanual) " +
              "values(?,?,?,?,0,0,0,0,?) ";
          lPStmt1 = conn.prepareStatement(cPlanFinanciera);
          lPStmt1.setString(1,cEjercicio);
          lPStmt1.setInt(2,vData.getInt("iNumMovtoSegto"));
          lPStmt1.setInt(3,vConceptoCont.getInt("ICVECONCEPTOCONTABLE"));
          lPStmt1.setInt(4,vConceptoCont.getInt("ICVECONCEPTOCONTABLEPADRE"));
          lPStmt1.setDouble(5,dEstimado);
          lPStmt1.executeUpdate();
          conn.commit();
          lPStmt1.close();

        }
      }else{
        if(vData.getString("cPrograFor")!=null && (cProgramaFor.equalsIgnoreCase("pg119420240")
                                                 || cProgramaFor.equalsIgnoreCase("pg119420250")
                                                 || cProgramaFor.equalsIgnoreCase("pg119420260")
                                                 || cProgramaFor.equalsIgnoreCase("pg119420270")
                                                 || cProgramaFor.equalsIgnoreCase("pg119420280")
                                                   )){
           TDCPATitulo dCPATitulo =  new TDCPATitulo();

           Vector vDatosTit = dCPATitulo.datosTitulo(vData.getInt("iCveTitulo"),0,1,1, new Vector(),2);

             TVDinRep vDinPrgPuertos = null, vDinTemp = new TVDinRep();
             TDESTPrgConcesionPto dESTPrgConcesionPto = new TDESTPrgConcesionPto();
             Vector vDatosPuertos = new Vector();
             vDinTemp = (TVDinRep) vDatosTit.get(vDatosTit.size()-1);
             vDatosPuertos = (Vector) vDinTemp.get("Ubicaciones");

             for(int iCont=0;iCont<vDatosPuertos.size();iCont++){
               vDinPrgPuertos = new TVDinRep();
               vDinPrgPuertos = (TVDinRep) vDatosPuertos.get(iCont);
               vDinPrgPuertos.put("iEjercicioSeguimiento",cEjercicio);
               vDinPrgPuertos.put("iNumMovtoSegto",vData.getInt("iNumMovtoSegto"));
               vDinPrgPuertos.put("dProgramado1",0);
               vDinPrgPuertos.put("dProgramado2",0);

               dESTPrgConcesionPto.insert(vDinPrgPuertos,conn);
             }

        }
      }

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
   * <p><b> delete from ESTSegtoFormulario where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto, </b></p>
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
      String lSQL = "delete from ESTSegtoFormulario where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));

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
   * <p><b> update ESTSegtoFormulario set iCveTitulo=?, iCveTitular=?, iCveFormulario=?, iEjercicioReportado=?, iPeriodoReportado=?, dtLimitePresentacion=?, dtEnvioDGFYDP=? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto, </b></p>
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
      String lSQL = "update ESTSegtoFormulario set dtLimitePresentacion=?, dtEnvioDGFYDP=?, DTAPROBACIONINFORMACION = ?, IESTATUSFORMULARIO = ?, LBLOQUEADO = ? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? ";

      vData.addPK(vData.getString("iEjercicioSeguimiento"));
      vData.addPK(vData.getString("iNumMovtoSegto"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setDate(1,vData.getDate("dtLimitePresentacion"));
      lPStmt.setDate(2,vData.getDate("dtEnvioDGFYDP"));
      lPStmt.setDate(3,vData.getDate("DTAPROBACIONINFORMACION"));
      lPStmt.setInt(4,vData.getInt("IESTATUSFORMULARIO"));
      lPStmt.setInt(5,vData.getInt("LBLOQUEADO"));
      lPStmt.setInt(6,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(7,vData.getInt("iNumMovtoSegto"));
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
 public TVDinRep insertAnual(TVDinRep vData,Connection cnNested) throws
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
       conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
     }

     // Se obtiene el ejercicio(año) del servidor
     TFechas tfSol = new TFechas("44");
     java.sql.Date dtHoy = new java.sql.Date(new java.util.Date().getTime());
     String cDmy = tfSol.getFechaDDMMYYYY(dtHoy,"/");
     String cEjercicio = cDmy.substring(6);

     String lSQL =
         "insert into ESTSegtoFormulario(iEjercicioSeguimiento,iNumMovtoSegto,iCveTitulo,iCveTitular,iCveFormulario,iEjercicioReportado,iPeriodoReportado,dtLimitePresentacion,dtEnvioDGFYDP,dtAprobacionInformacion,iEstatusFormulario,lBloqueado) values (?,?,?,?,?,?,?,?,?,?,?,?)";

     //AGREGAR AL ULTIMO ...
     Vector vcData = this.findByGenericLocal("","select MAX(iNumMovtoSegto) AS iNumMovtoSegto from ESTSegtoFormulario where iEjercicioSeguimiento = " + cEjercicio,dataSourceName,conn);
     if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iNumMovtoSegto",vUltimo.getInt("iNumMovtoSegto") + 1);
     }else
        vData.put("iNumMovtoSegto",1);

     vData.addPK(cEjercicio);
     vData.addPK(vData.getString("iNumMovtoSegto"));

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setString(1,cEjercicio);
     lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
     lPStmt.setInt(3,vData.getInt("iCveTitulo"));
     lPStmt.setInt(4,vData.getInt("iCveTitular"));
     lPStmt.setInt(5,vData.getInt("iCveFormulario"));
     lPStmt.setInt(6,vData.getInt("iEjercicioReportado"));
     lPStmt.setInt(7,vData.getInt("iPeriodoReportado"));
     lPStmt.setDate(8,vData.getDate("dtLimitePresentacion"));
     lPStmt.setDate(9,vData.getDate("dtEnvioDGFYDP"));
     lPStmt.setDate(10,vData.getDate("dtAprobacionInformacion"));
     lPStmt.setInt(11,vData.getInt("iEstatusFormulario"));
     lPStmt.setInt(12,vData.getInt("lBloqueado"));

     lPStmt.executeUpdate();

     String cProgramaFor=vData.getString("cPrograFor");
     if(vData.getString("cPrograFor")!=null && vData.getString("cPrograFor").equalsIgnoreCase("pg119420160")){
       Vector vcConceptoCont = this.findByGenericLocal("","SELECT ICVECONCEPTOCONTABLE,CDSCCONCEPTOCONTABLE,ICVECONCEPTOCONTABLEPADRE FROM ESTCONCEPTOCONTABLE where ICVECONCEPTOCONTABLE >0 and LVIGENTE = 1",dataSourceName,conn);
       for(int i=0;i<vcConceptoCont.size();i++){
         TVDinRep vConceptoCont = (TVDinRep) vcConceptoCont.get(i);
         double dEstimado = 0.0;

         if(vData.getInt("iPeriodoReportado")>1){

           String cEstimado =
               "SELECT P.DESTIMADOCIERREANUAL FROM ESTSEGTOFORMULARIO S " +
               "JOIN ESTPLANFINANCIERA P ON S.IEJERCICIOSEGUIMIENTO = P.IEJERCICIOSEGUIMIENTO AND S.INUMMOVTOSEGTO = P.INUMMOVTOSEGTO " +
               " AND   P.ICVECONCEPTOCONTABLE = " + vConceptoCont.getInt("ICVECONCEPTOCONTABLE") +
               " WHERE S.IEJERCICIOREPORTADO = " + vData.getInt("iEjercicioReportado")+
               " AND   S.IPERIODOREPORTADO =  " + (vData.getInt("iPeriodoReportado") -1) +
               " AND   S.ICVETITULO = " + vData.getInt("iCveTitulo") +
               " AND   S.ICVETITULAR = " + vData.getInt("iCveTitular") +
               " AND   S.ICVEFORMULARIO = " + vData.getInt("iCveFormulario");

           Vector vcEstimado = this.findByGenericLocal("",cEstimado,dataSourceName,conn);
           TVDinRep vEstimado = new TVDinRep();
           if(vcEstimado.size()>0)
             vEstimado = (TVDinRep) vcEstimado.get(0);
           else  {
             vEstimado.put("DESTIMADOCIERREANUAL",0);
           }
           dEstimado = vEstimado.getDouble("DESTIMADOCIERREANUAL");
         }

         String cPlanFinanciera =
             "INSERT INTO ESTPLANFINANCIERA (IEJERCICIOSEGUIMIENTO,INUMMOVTOSEGTO,ICVECONCEPTOCONTABLE,ICVECONCEPTOCONTABLEPADRE, " +
             "DPROGRAMADOACUMULADO,DREALACUMULADO,DPROGRAMADOMES, drealmes, destimadocierreanual) " +
             "values(?,?,?,?,0,0,0,0,?) ";
         lPStmt1 = conn.prepareStatement(cPlanFinanciera);
         lPStmt1.setString(1,cEjercicio);
         lPStmt1.setInt(2,vData.getInt("iNumMovtoSegto"));
         lPStmt1.setInt(3,vConceptoCont.getInt("ICVECONCEPTOCONTABLE"));
         lPStmt1.setInt(4,vConceptoCont.getInt("ICVECONCEPTOCONTABLEPADRE"));
         lPStmt1.setDouble(5,vData.getInt("iPeriodoReportado")==1?0:dEstimado);
         lPStmt1.executeUpdate();
         conn.commit();
         lPStmt1.close();

       }
     }else{
       if(vData.getString("cPrograFor")!=null && (cProgramaFor.equalsIgnoreCase("pg119420240")
                                                || cProgramaFor.equalsIgnoreCase("pg119420250")
                                                || cProgramaFor.equalsIgnoreCase("pg119420260")
                                                || cProgramaFor.equalsIgnoreCase("pg119420270")
                                                || cProgramaFor.equalsIgnoreCase("pg119420280")
                                                  )){
          TDCPATitulo dCPATitulo =  new TDCPATitulo();

          Vector vDatosTit = dCPATitulo.datosTitulo(vData.getInt("iCveTitulo"),0,1,1, new Vector(),2);

            TVDinRep vDinPrgPuertos = null, vDinTemp = new TVDinRep();
            TDESTPrgConcesionPto dESTPrgConcesionPto = new TDESTPrgConcesionPto();
            Vector vDatosPuertos = new Vector();
            vDinTemp = (TVDinRep) vDatosTit.get(vDatosTit.size()-1);
            vDatosPuertos = (Vector) vDinTemp.get("Ubicaciones");

            for(int iCont=0;iCont<vDatosPuertos.size();iCont++){
              vDinPrgPuertos = new TVDinRep();
              vDinPrgPuertos = (TVDinRep) vDatosPuertos.get(iCont);
              vDinPrgPuertos.put("iEjercicioSeguimiento",cEjercicio);
              vDinPrgPuertos.put("iNumMovtoSegto",vData.getInt("iNumMovtoSegto"));
              vDinPrgPuertos.put("dProgramado1",0);
              vDinPrgPuertos.put("dProgramado2",0);

              dESTPrgConcesionPto.insert(vDinPrgPuertos,conn);
            }

       }
     }

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
  public TVDinRep update1(TVDinRep vData,int i,Connection cnNested) throws
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
        String cQrySegtoForm =
            "SELECT iEjercicioSeguimiento,iNumMovtoSegto FROM ESTSEGTOFORMULARIO " +
            "where IEJERCICIOREPORTADO = "+vData.getInt("iEjercicioReportado") +" and iPeriodoreportado = "+i+
            " and iCveTitulo = "+vData.getInt("iCveTitulo")+" and iCveTitular = "+vData.getInt("iCveTitular")+
            " and iCveFormulario = 19 ";
        Vector vcSegtoForm = this.findByCustom("",cQrySegtoForm);
        String cUpdate = "";
        if (vcSegtoForm.size()>0){
          TVDinRep vSegtoForm = (TVDinRep) vcSegtoForm.get(0);
          cUpdate =
              "update estplanfinanciera set DPROGRAMADOMODIFICADO = DPROGRAMADOMES "+
              "  where IEJERCICIOSEGUIMIENTO = "+vSegtoForm.getInt("iEjercicioSeguimiento")+
              "  and INUMMOVTOSEGTO = "+vSegtoForm.getInt("iNumMovtoSegto");
          lPStmt = conn.prepareStatement(cUpdate);
          lPStmt.executeUpdate();

          vData.put("iEjercicioSeguimiento",vSegtoForm.getInt("iEjercicioSeguimiento"));
          vData.put("iNumMovtoSegto",vSegtoForm.getInt("iNumMovtoSegto"));

          lPStmt.close();
          this.update2(vData,conn);

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

 /**
  * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
  * <p><b> update ESTSegtoFormulario set iCveTitulo=?, iCveTitular=?, iCveFormulario=?, iEjercicioReportado=?, iPeriodoReportado=?, dtLimitePresentacion=?, dtEnvioDGFYDP=? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ?  </b></p>
  * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto, </b></p>
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
     String lSQL = "update ESTSegtoFormulario set DTAPROBACIONINFORMACION = ?, IESTATUSFORMULARIO = ?, LBLOQUEADO = ? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? ";

     vData.addPK(vData.getString("iEjercicioSeguimiento"));
     vData.addPK(vData.getString("iNumMovtoSegto"));

     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setDate(1,vData.getDate("DTAPROBACIONINFORMACION"));
     lPStmt.setInt(2,vData.getInt("IESTATUSFORMULARIO"));
     lPStmt.setInt(3,vData.getInt("LBLOQUEADO"));
     lPStmt.setInt(4,vData.getInt("iEjercicioSeguimiento"));
     lPStmt.setInt(5,vData.getInt("iNumMovtoSegto"));
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

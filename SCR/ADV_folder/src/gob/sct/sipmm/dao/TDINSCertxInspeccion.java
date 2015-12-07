package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDINSCertxInspeccion.java</p>
 * <p>Description: DAO de la entidad INSCertxInspeccion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
public class TDINSCertxInspeccion extends DAOBase{
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
   * <p><b> insert into INSCertxInspeccion(iCveInspProg,iNumCertificado,iEjercicio,iNumSolicitud,iCveGrupoCertif,iTipoCertificado,iCveTipoInsp,lAprobada,lAutorizado) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveInspProg,iNumCertificado, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    TDINSCaractXInsp Caract = new TDINSCaractXInsp();
    TDINSCertifExp dINSCertifExpedidos = new TDINSCertifExp();
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      TVDinRep vProg = (TVDinRep) this.findByNessted("","SELECT IEJERCICIO,INUMSOLICITUD FROM INSPROGINSP WHERE ICVEINSPPROG="+vData.getInt("iCveInspProg"),conn).get(0);
      String lSQL =
          "insert into INSCertxInspeccion(iCveInspProg,iNumCertificado,iEjercicio,iNumSolicitud,iCveGrupoCertif,iTipoCertificado,iCveTipoInsp,lAprobada,lAutorizado) values (?,?,?,?,?,?,?,?,?)";


      //AGREGAR AL ULTIMO ...
      Vector vcData = findByNessted("","select MAX(iNumCertificado) AS iNumCertificado from INSCertxInspeccion Where iCveInspProg = "+vData.getInt("iCveInspProg"),conn);
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iNumCertificado",vUltimo.getInt("iNumCertificado") + 1);
      }else
         vData.put("iNumCertificado",1);
      vData.addPK(vData.getString("iNumCertificado"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveInspProg"));
      lPStmt.setInt(2,vData.getInt("iNumCertificado"));
      lPStmt.setInt(3,vProg.getInt("iEjercicio"));
      lPStmt.setInt(4,vProg.getInt("iNumSolicitud"));
      lPStmt.setInt(5,vData.getInt("iCveGrupoCertif"));
      lPStmt.setInt(6,vData.getInt("iTipoCertificado"));
      lPStmt.setInt(7,1);
      lPStmt.setInt(8,vData.getInt("lAprobada"));
      lPStmt.setInt(9,1);
      //lPStmt.setString(10,vData.getString("cObses"));
      lPStmt.executeUpdate();
      lPStmt.close();


      String certExp = "";

      String [] aCampos = vData.getString("cCampos").split(",");
      String [] aValores = vData.getString("cValores").split(",");
      String [] aConceptos = vData.getString("cConceptos").split(",");
      String [] aCaracteristicas = vData.getString("cCaracteristicas").split(",");

//oAccion.setInputs("iCveVehiculo,iNumSolicitud,iEjercicio,iTipoCertificado,dtIniVigencia,dtFinVigencia,iCveGrupoCertif,iCveUsuario,iCveInspProg,iNumCertificado");
      TVDinRep vCertif = new TVDinRep();
      vCertif.put("iCveVehiculo",vData.getInt("iCveVehiculo"));
      vCertif.put("iTipoCertificado",vData.getInt("iTipoCertificado"));
      vCertif.put("dtIniVigencia",vData.getDate("dtIniVigencia"));
      vCertif.put("dtFinVigencia",vData.getDate("dtFinVigencia"));

      vCertif.put("iCveGrupoCertif",vData.getInt("iCveGrupoCertif"));
      vCertif.put("iCveUsuario",vData.getInt("iCveUsuario"));
      vCertif.put("iCveInspProg",vData.getInt("iCveInspProg"));
      vCertif.put("iNumCertificado",vData.getInt("iNumCertificado"));
      vCertif.put("cObses",vData.getString("cObses"));

      if(vData.getInt("iCveVehiculo")>0)
        dINSCertifExpedidos.insertCert(vCertif,conn);
      
      StringTokenizer stCampos         = new StringTokenizer(vData.getString("cCampos"),",");
      StringTokenizer stValores        = new StringTokenizer(vData.getString("cValores"),",");
      StringTokenizer stConcepto       = new StringTokenizer(vData.getString("cConceptos"),",");
      StringTokenizer stCaracterisitca = new StringTokenizer(vData.getString("cCaracteristicas"),",");
      
      if(vData.getInt("iCveInspeccion")>0)
      while(stCampos.hasMoreElements()){
    	  
          String sCampos = stCampos.nextToken();  
          String sValores = stValores.nextToken();  
          int sConceptos = Integer.parseInt(stConcepto.nextToken());  
          int sCaracteristicas = Integer.parseInt(stCaracterisitca.nextToken());
          TVDinRep vCaract = new TVDinRep();
          Vector vcExistente = this.findByNessted("","SELECT TSMODIFICACION FROM INSCARACTXINSP "+
                                                     "Where ICVEINSPECCION = "+vData.getString("iCveInspeccion")+
                                                     " and ICVECONCEPTOEVAL="+sConceptos+
                                                     " and ICVECARACTERISITIC="+sCaracteristicas,conn);

          vCaract.put("iCveInspeccion",vData.getString("iCveInspeccion"));
          vCaract.put("iCveConceptoEval",sConceptos);
          vCaract.put("iCveCaracteristic",sCaracteristicas);
          vCaract.put("iCveUsuario",vData.getString("iCveUsuario"));
          vCaract.put("cVariable",sCampos);
          vCaract.put("cValor",sValores);
          vCaract.put("lAprobado",1);

          if(vcExistente.size()>0) Caract.update(vCaract,conn);
          else Caract.insert(vCaract,conn);
          conn.commit();    	  
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
   * <p><b> delete from INSCertxInspeccion where iCveInspProg = ? AND iNumCertificado = ?  </b></p>
   * <p><b> Campos Llave: iCveInspProg,iNumCertificado, </b></p>
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
    PreparedStatement lPStmt1 = null;
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
//      TVDinRep vCertif = (TVDinRep) this.findByNessted("","select ICVEVEHICULO,ICONSECUTIVOCERTEXP from INSCertxInspeccion where iCveInspProg = "+vData.getInt("iCveInspProg")+" AND iNumCertificado = "+vData.getInt("iNumCertificado"),conn).get(0);
      TVDinRep vCertif = (TVDinRep) this.findByCustom("","select ICVEVEHICULO,ICONSECUTIVOCERTEXP from INSCertxInspeccion where iCveInspProg = "+vData.getInt("iCveInspProg")+" AND iNumCertificado = "+vData.getInt("iNumCertificado")).get(0);

      String lSQL = "delete from INSCertxInspeccion where iCveInspProg = ? AND iNumCertificado = ?  ";
      String lSQL1 = "DELETE FROM INSCERTIFEXPEDIDOS where iCveVehiculo = ? and iConsecutivoCertExp = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt1 = conn.prepareStatement(lSQL1);
      lPStmt1.setInt(1,vCertif.getInt("ICVEVEHICULO"));
      lPStmt1.setInt(2,vCertif.getInt("ICONSECUTIVOCERTEXP"));
      lPStmt1.executeUpdate();

      lPStmt.setInt(1,vData.getInt("iCveInspProg"));
      lPStmt.setInt(2,vData.getInt("iNumCertificado"));
      lPStmt.executeUpdate();


      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = ""+sqle.getErrorCode();
    } catch(Exception ex){
      warn("delete",ex);
      ex.printStackTrace();
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
   * <p><b> update INSCertxInspeccion set iEjercicio=?, iNumSolicitud=?, iCveGrupoCertif=?, iTipoCertificado=?, iCveTipoInsp=?, lAprobada=?, lAutorizado=? where iCveInspProg = ? AND iNumCertificado = ?  </b></p>
   * <p><b> Campos Llave: iCveInspProg,iNumCertificado, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep autorizar(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update INSCertxInspeccion set lAprobada=1,iCveUsuAutoriza=? where iCveInspProg = ? AND iNumCertificado = ? ";
      vData.addPK(vData.getString("iCveInspProg"));
      vData.addPK(vData.getString("iNumCertificado"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveUsuario"));
      lPStmt.setInt(2,vData.getInt("iCveInspProg"));
      lPStmt.setInt(3,vData.getInt("iNumCertificado"));
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

  public TVDinRep update(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    TDINSCaractXInsp Caract = new TDINSCaractXInsp();
    PreparedStatement lPStmt = null;
    PreparedStatement lPStmt2 = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL = "update INSCertxInspeccion set iEjercicio=?, iNumSolicitud=?, iCveGrupoCertif=?, iTipoCertificado=?, iCveTipoInsp=?, lAprobada=?, lAutorizado=? where iCveInspProg = ? AND iNumCertificado = ? ";

      vData.addPK(vData.getString("iCveInspProg"));
      vData.addPK(vData.getString("iNumCertificado"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
      lPStmt.setInt(3,vData.getInt("iCveGrupoCertif"));
      lPStmt.setInt(4,vData.getInt("iTipoCertificado"));
      lPStmt.setInt(5,vData.getInt("iCveTipoInsp"));
      lPStmt.setInt(6,vData.getInt("lAprobada"));
      lPStmt.setInt(7,vData.getInt("lAutorizado"));
      lPStmt.setInt(8,vData.getInt("iCveInspProg"));
      lPStmt.setInt(9,vData.getInt("iNumCertificado"));
      lPStmt.executeUpdate();

      String lSQL2 = "UPDATE INSCERTIFEXPEDIDOS SET DTINIVIGENCIA=?, DTFINVIGENCIA=?,cObses=? WHERE ICVEVEHICULO=? AND ICONSECUTIVOCERTEXP=?";
      lPStmt2 = conn.prepareStatement(lSQL2); 
      lPStmt2.setDate(1,vData.getDate("dtIniVigencia"));
      lPStmt2.setDate(2,vData.getDate("dtFinVigencia"));
      lPStmt2.setString(3,vData.getString("cObses"));
      lPStmt2.setInt(4,vData.getInt("iCveVehiculo"));
      lPStmt2.setInt(5,vData.getInt("iConsecutivoCertExp"));
       
      
      lPStmt2.executeUpdate();
      
      StringTokenizer stCampos         = new StringTokenizer(vData.getString("cCampos"),",");
      StringTokenizer stValores        = new StringTokenizer(vData.getString("cValores"),",");
      StringTokenizer stConcepto       = new StringTokenizer(vData.getString("cConceptos"),",");
      StringTokenizer stCaracterisitca = new StringTokenizer(vData.getString("cCaracteristicas"),",");
      

      while(stCampos.hasMoreElements()){
          String sCampos = stCampos.nextToken();  
          String sValores = stValores.nextToken();  
          int sConceptos = Integer.parseInt(stConcepto.nextToken());  
          int sCaracteristicas = Integer.parseInt(stCaracterisitca.nextToken());
          TVDinRep vCaract = new TVDinRep();
          Vector vcExistente = this.findByNessted("","SELECT TSMODIFICACION FROM INSCARACTXINSP "+
                                                     "Where ICVEINSPECCION = "+vData.getString("iCveInspeccion")+
                                                     " and ICVECONCEPTOEVAL="+sConceptos+
                                                     " and ICVECARACTERISITIC="+sCaracteristicas,conn);

          vCaract.put("iCveInspeccion",vData.getString("iCveInspeccion"));
          vCaract.put("iCveConceptoEval",sConceptos);
          vCaract.put("iCveCaracteristic",sCaracteristicas);
          vCaract.put("iCveUsuario",vData.getString("iCveUsuario"));
          vCaract.put("cVariable",sCampos);
          vCaract.put("cValor",sValores);
          vCaract.put("lAprobado",1);

          if(vcExistente.size()>0) Caract.update(vCaract,conn);
          else Caract.insert(vCaract,conn);
          conn.commit();    	  
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
          lPStmt2.close();
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
     String lSQL = "update INSInspeccion set lRegistroFinalizado = 1 where iCveInspeccion =  "+vData.getInt("iCveInspeccion");

     vData.addPK(vData.getString("iCveInspeccion"));
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.executeUpdate();
         lPStmt.close();
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

 public TVDinRep Autoriza(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   PreparedStatement lPStmt2 = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String [] cAutorizada = vData.getString("cConjunto1").split(",");
     String [] cAprobada = vData.getString("cConjunto").split(",");
     String lSQL = "update INSCertxInspeccion set lAprobada=1 where iCveInspProg = ? AND iNumCertificado = ? ";
     String lSQL1 = "update INSCertxInspeccion set lAutorizado=1 where iCveInspProg = ? AND iNumCertificado = ? ";
     String lSQL2 = "update INSCertxInspeccion set lAprobada=0,lAutorizado=0 where iCveInspProg = ? ";

     vData.addPK(vData.getString("iCveInspProg"));
     vData.addPK(vData.getString("iNumCertificado"));

     lPStmt2 = conn.prepareStatement(lSQL2);
     lPStmt2.setInt(1,vData.getInt("iCveInspProg"));
     lPStmt2.executeUpdate();
     lPStmt2.close();
     for(int tm=0;tm<cAprobada.length;tm++){
       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.setInt(1,vData.getInt("iCveInspProg"));
       lPStmt.setInt(2,Integer.parseInt(cAprobada[tm]));
       lPStmt.executeUpdate();
       lPStmt.close();
     }
     for(int tm1=0;tm1<cAutorizada.length;tm1++){
       lPStmt1 = conn.prepareStatement(lSQL1);
       lPStmt1.setInt(1,vData.getInt("iCveInspProg"));
       lPStmt1.setInt(2,Integer.parseInt(cAutorizada[tm1]));
       lPStmt1.executeUpdate();
       lPStmt1.close();
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
       if(lPStmt != null){
         lPStmt1.close();
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
 public TVDinRep Modificar(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   PreparedStatement lPStmt1 = null;
   PreparedStatement lPStmt2 = null;
   boolean lSuccess = true;
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String [] cAutorizada = vData.getString("cConjunto1").split(",");
     String [] cAprobada = vData.getString("cConjunto").split(",");
     String lSQL = "update INSCERTXINSPECCION set ICVEGRUPOCERTIF = ?, ITIPOCERTIFICADO = ? where ICVEINSPPROG =? and INUMCERTIFICADO = ? ";

     vData.addPK(vData.getString("iCveInspProg"));
     vData.addPK(vData.getString("iNumCertificado"));
       lPStmt = conn.prepareStatement(lSQL);
       lPStmt.setInt(1,vData.getInt("iCveGrupoCertif"));
       lPStmt.setInt(2,vData.getInt("iTipoCertificado"));
       lPStmt.setInt(3,vData.getInt("iCveInspeccionProg"));
       lPStmt.setInt(4,vData.getInt("iNumCertificado"));

       lPStmt.executeUpdate();
       lPStmt.close();

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
       if(lPStmt != null){
         lPStmt1.close();
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
 public TVDinRep insertMat(TVDinRep vData,Connection cnNested) throws
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
     TFechas fecha = new TFechas();
     TVDinRep vProgInsp = new TVDinRep();
     vProgInsp.put("iNumSolicitud",vData.getInt("iNumSolicitud"));
     vProgInsp.put("iCveInspector",vData.getInt("iCveInspector"));
     vProgInsp.put("iCveTipoInsp",1);
     vProgInsp.put("iTipoCertificado",1);
     vProgInsp.put("iCveEmbarcacion",vData.getInt("iCveVehiculo"));
     vProgInsp.put("iCvePuerto",0);
     vProgInsp.put("iCveOficina",vData.getInt("iCveOficina"));
     vProgInsp.put("tsRegistro",fecha.getThisTime());
     vProgInsp.put("tsInsp",fecha.getThisTime());
     vProgInsp.put("iEjercicio",vData.getInt("iEjercicio"));
     vProgInsp.put("lCancelada",0);
     vProgInsp.put("cMotivoCancelacion","");
     vProgInsp.put("dtCancelacion",vData.getDate("dtCancelacion"));
     TDINSProgInsp dProgInsp = new TDINSProgInsp();
     vProgInsp = dProgInsp.insertNuevo(vProgInsp,conn);
     String lSQL =
         "insert into INSCertxInspeccion(iCveInspProg,iNumCertificado,iEjercicio,iNumSolicitud,iCveTipoInsp,lAprobada,lAutorizado,iCveGrupoCertif,iTipoCertificado) values (?,?,?,?,?,?,?,?,?)";
     //AGREGAR AL ULTIMO ...
     Vector vcData = findByCustom("","select MAX(iNumCertificado) AS iNumCertificado from INSCertxInspeccion Where iCveInspProg = "+vProgInsp.getInt("iCveInspProg"));
     if(vcData.size() > 0){
        TVDinRep vUltimo = (TVDinRep) vcData.get(0);
        vData.put("iNumCertificado",vUltimo.getInt("iNumCertificado") + 1);
     }else
        vData.put("iNumCertificado",1);
     vData.addPK(vData.getString("iNumCertificado"));
     //...
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vProgInsp.getInt("iCveInspProg"));
     lPStmt.setInt(2,vData.getInt("iNumCertificado"));
     lPStmt.setInt(3,vData.getInt("iEjercicio"));
     lPStmt.setInt(4,vData.getInt("iNumSolicitud"));
     lPStmt.setInt(5,1);
     lPStmt.setInt(6,vData.getInt("lAprovado"));
     lPStmt.setInt(7,vData.getInt("lAutorizado"));
     lPStmt.setInt(8,vData.getInt("iCveGrupoCertif"));
     lPStmt.setInt(9,vData.getInt("iTipoCertificado"));
     lPStmt.executeUpdate();
     lPStmt.close();
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
 public TVDinRep ModificarMat(TVDinRep vData,Connection cnNested) throws
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
     String lSQL = "update INSCERTXINSPECCION set lAprobada=?,lAutorizado=?,iCveGrupoCertif=?,iTipoCertificado=? where ICVEINSPPROG =? and INUMCERTIFICADO = ? ";
     vData.addPK(vData.getString("iCveInspProg"));
     vData.addPK(vData.getString("iNumCertificado"));
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("lAprovado"));
     lPStmt.setInt(2,vData.getInt("lAutorizado"));
     lPStmt.setInt(3,vData.getInt("iCveGrupoCertif"));
     lPStmt.setInt(4,vData.getInt("iTipoCertificado"));
     lPStmt.setInt(5,vData.getInt("iCveInspeccionProg"));
     lPStmt.setInt(6,vData.getInt("iNumCertificado"));

     lPStmt.executeUpdate();
     lPStmt.close();
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
 public TVDinRep modSolicitud(TVDinRep vData,Connection cnNested) throws
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
     String lSQL = "update INSCERTXINSPECCION set iEjercicio=?,iNumSolicitud=? where ICVEINSPPROG =? and INUMCERTIFICADO = ? ";
     vData.addPK(vData.getString("iCveInspProg"));
     vData.addPK(vData.getString("iNumCertificado"));
     lPStmt = conn.prepareStatement(lSQL);
     lPStmt.setInt(1,vData.getInt("iEjercicio"));
     lPStmt.setInt(2,vData.getInt("iNumSolicitud"));
     lPStmt.setInt(3,vData.getInt("iCveInspProg"));
     lPStmt.setInt(4,vData.getInt("iNumCertificado"));
     lPStmt.executeUpdate();
     lPStmt.close();
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

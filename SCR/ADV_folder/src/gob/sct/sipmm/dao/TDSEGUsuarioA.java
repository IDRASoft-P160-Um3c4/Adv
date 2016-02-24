package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.sql.*;
import com.micper.seguridad.vo.*;

/**
 * <p>Title: Data Acces Object de SEGUsuario DAO </p>
 * <p>Description: DAO de la entidad SEGUsuario que es replica de ADMSEG (Solo Lectura) </p>
 * <p>Copyright: Copyright (c) 2004 </p>
 * <p>Company: Micros Personales S.A. de C.V. </p>
 * @author Jaime Enrique Suárez Romero && ARTURO LOPEZ PEÑA
 * @version 1.0
 */

public class TDSEGUsuarioA extends DAOBase {
   private TParametro VParametros = new TParametro("44");
   public TDSEGUsuarioA() {
   }
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

  public Vector findByCustom2(String cKey,String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    String dataSourceName2 = VParametros.getPropEspecifica("ConDBModulo");
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName2);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }


 public TVDinRep upDateA(TVDinRep vData,Connection cnNested) throws DAOException{
    String dataSourceName = VParametros.getPropEspecifica("ConDB"), dataSourceName2 = VParametros.getPropEspecifica("ConDBModulo");
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lError = false;
    String cSQL = "",cSQL1="";
    String cSql="",cErrores="";
    boolean lSuccess = true;
    Vector vcData = new Vector();
    try{
    if(cnNested == null){
      dbConn = new DbConnection(dataSourceName2);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    }
      cSQL = "select " +
             "distinct(segusuario.iCveUsuario), " +
             "segusuario.dtRegistro, " +
             "segusuario.cUsuario, " +
             "segusuario.cPassword," +
             "segusuario.cNombre, " +
             "segusuario.cApPaterno, " +
             "segusuario.cApMaterno, " +
             "segusuario.cCalle, " +
             "segusuario.cColonia, " +
             "segusuario.iCvePais, " +
             "segusuario.iCveEntidadFed, " +
             "segusuario.iCveMunicipio, " +
             "segusuario.iCodigoPostal, " +
             "segusuario.cTelefono, " +
             "segusuario.iCveUnidadOrg," +
             "segusuario.lBloqueado " +
             "from seggrupo " +
             "join seggpoxusr on seggrupo.icvesistema = seggpoxusr.icvesistema " +
             "and  seggrupo.icvegrupo = seggpoxusr.icvegrupo " +
             "join segusuario on seggpoxusr.icveusuario = segusuario.icveusuario " +
             "where seggrupo.icvesistema = 44";
      vcData = this.FindByGeneric("",cSQL,dataSourceName);
    }catch(Exception ex1){
      lError = true;
      ex1.printStackTrace();
    }
    if(vcData.size()>0 && !lError){
      System.out.print("*****    Tamaño del vector: "+vcData.size());
        for(int i=0;i<vcData.size();i++){
            TVDinRep vD = (TVDinRep) vcData.get(i);
            cSQL1 = "SELECT ICVEUSUARIO FROM SEGUSUARIO WHERE ICVEUSUARIO = " +
                vD.getString("iCveUsuario");
            Vector vcDataLoc = this.findByCustom2("",cSQL1);
            TVDinRep vDL = (TVDinRep) vcData.get(0);
            if(vcDataLoc.size() > 0){
              //UPDATE
              cErrores+=usrUpdate(vD, null);
            } else{
              //INSERT
              cErrores+=usrInsert(vD, null);
            }
       }
       //f(cErrores!="")
         System.out.print("Errores presentados\n"+cErrores);
    }
      return vData;
  }
  public String usrUpdate(TVDinRep vD, Connection cnNested){
    Connection conn = cnNested;
    String respuesta="";
    PreparedStatement lPStmt = null;
    String dataSourceName2 = VParametros.getPropEspecifica("ConDBModulo");
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName2);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String cSql = "UPDATE SEGUSUARIO SET DTREGISTRO=?,CUSUARIO=?,CPASSWORD=?,CNOMBRE=?,CAPPATERNO=?,CAPMATERNO=?,CCALLE=?," +
                    "CCOLONIA=?,ICVEPAIS=?,ICVEENTIDADFED=?, " +
                    "ICVEMUNICIPIO=?,ICODIGOPOSTAL=?,CTELEFONO=?,ICVEUNIDADORG=?,LBLOQUEADO=? WHERE ICVEUSUARIO = ? ";
      lPStmt = conn.prepareStatement(cSql);
      lPStmt.setDate(1,vD.getDate("DTREGISTRO"));
      lPStmt.setString(2,vD.getString("CUSUARIO"));
      lPStmt.setString(3,vD.getString("CPASSWORD"));
      lPStmt.setString(4,vD.getString("CNOMBRE"));
      lPStmt.setString(5,vD.getString("CAPPATERNO"));
      lPStmt.setString(6,vD.getString("CAPMATERNO"));
      lPStmt.setString(7,vD.getString("CCALLE"));
      lPStmt.setString(8,vD.getString("CCOLONIA"));
      lPStmt.setInt(9,vD.getInt("ICVEPAIS"));
      lPStmt.setInt(10,vD.getInt("ICVEENTIDADFED"));
      lPStmt.setInt(11,vD.getInt("ICVEMUNICIPIO"));
      lPStmt.setInt(12,vD.getInt("ICODIGOPOSTAL"));
      lPStmt.setString(13,vD.getString("CTELEFONO"));
      lPStmt.setInt(14,vD.getInt("ICVEUNIDADORG"));
      lPStmt.setInt(15,vD.getInt("LBLOQUEADO"));
      lPStmt.setInt(16,vD.getInt("ICVEUSUARIO"));
      System.out.print("*****     Actualizando usuario: "+vD.getInt("ICVEUSUARIO")+": "+vD.getString("CUSUARIO"));
      lPStmt.executeUpdate();
      lPStmt.close();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      ex.printStackTrace();
      respuesta = "Acctualizar: "+vD.getString("ICVEUSUARIO")+" - "+vD.getString("CUSUARIO")+"\n";
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }
      }
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
        ex2.printStackTrace();
      }
    }
    return respuesta;
  }
  public String usrInsert(TVDinRep vD, Connection cnNested){
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    String respuesta="";
    String cSql="",dataSourceName2 = VParametros.getPropEspecifica("ConDBModulo");
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName2);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      cSql = "INSERT INTO SEGUSUARIO (ICVEUSUARIO,DTREGISTRO,CUSUARIO,CPASSWORD,CNOMBRE,CAPPATERNO,CAPMATERNO,CCALLE,CCOLONIA,ICVEPAIS,ICVEENTIDADFED, " +
             "ICVEMUNICIPIO,ICODIGOPOSTAL,CTELEFONO,ICVEUNIDADORG,LBLOQUEADO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
      lPStmt = conn.prepareStatement(cSql);
      lPStmt.setInt(1,vD.getInt("ICVEUSUARIO"));
      lPStmt.setDate(2,vD.getDate("DTREGISTRO"));
      lPStmt.setString(3,vD.getString("CUSUARIO"));
      lPStmt.setString(4,vD.getString("CPASSWORD"));
      lPStmt.setString(5,vD.getString("CNOMBRE"));
      lPStmt.setString(6,vD.getString("CAPPATERNO"));
      lPStmt.setString(7,vD.getString("CAPMATERNO"));
      lPStmt.setString(8,vD.getString("CCALLE"));
      lPStmt.setString(9,vD.getString("CCOLONIA"));
      lPStmt.setInt(10,vD.getInt("ICVEPAIS"));
      lPStmt.setInt(11,vD.getInt("ICVEENTIDADFED"));
      lPStmt.setInt(12,vD.getInt("ICVEMUNICIPIO"));
      lPStmt.setInt(13,vD.getInt("ICODIGOPOSTAL"));
      lPStmt.setString(14,vD.getString("CTELEFONO"));
      lPStmt.setInt(15,vD.getInt("ICVEUNIDADORG"));
      lPStmt.setInt(16,vD.getInt("LBLOQUEADO"));
      System.out.print("*****     Insertando usuario: "+vD.getInt("ICVEUSUARIO")+": "+vD.getString("CUSUARIO"));
      lPStmt.executeUpdate();
      lPStmt.close();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      ex.printStackTrace();
      respuesta = "Insertar: "+vD.getString("ICVEUSUARIO")+" - "+vD.getString("CUSUARIO")+"\n";
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
          e.printStackTrace();
        }
      }
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
    }
    return respuesta;
  }
}



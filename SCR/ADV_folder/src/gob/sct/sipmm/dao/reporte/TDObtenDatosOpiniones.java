package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.excepciones.*;
import java.sql.SQLException;
import java.util.Vector;
import gob.sct.sipmm.dao.reporte.*;
import java.sql.*;
import java.util.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;


/**
 * <p>Title: TDObtenDatos.java</p>
 * <p>Description: DAO para la obtención de datos geéricos de opiniones</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */

public class TDObtenDatosOpiniones{
  String cSistema = "44";
  public TDDatosOpnTra dDatosOpnTra;
  public TDDatosFolioOpn dDatosFolioOpn;
  public TDDatosOficPer dDatosOficPer;
  public TDDatosOpnCont dDatosOpnCon;
  public TDDatosOpnSolicitante dDatosOpnSol;
  public TDDatosSegtoEntidad dDatosSegtoEntidad;


  public TDObtenDatosOpiniones(){
    dDatosOpnTra = new TDDatosOpnTra();
    dDatosFolioOpn = new TDDatosFolioOpn();
    dDatosOficPer = new TDDatosOficPer();
    dDatosOpnCon = new TDDatosOpnCont();
    dDatosOpnSol = new TDDatosOpnSolicitante();
    dDatosSegtoEntidad = new TDDatosSegtoEntidad();
  }


  public class TDDatosOpnTra extends DAOBase{
    private TVDinRep vDato;
    private int iEjercicio, iNumSolicitud, iCveOpinionEntidad,iCveSegtoEntidad;

    public TDDatosOpnTra(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDatosTraOpn(){
      Vector vRegsDatos = new Vector();
      String cSql = "select * " +
                "from TRAOPNENTTRAMITE " +
                "where IEJERCICIOSOLICITUD = "+iEjercicio+
                " and INUMSOLICITUD = " +iNumSolicitud+
                " and ICVEOPINIONENTIDAD = "+iCveOpinionEntidad+
                " and ICVESEGTOENTIDAD = "+iCveSegtoEntidad;

      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }

    public void setFiltrosOpn(String Filtros){
      String[] aFiltros = Filtros.split(",");

      iEjercicio = Integer.parseInt(aFiltros[0],10);
      iNumSolicitud = Integer.parseInt(aFiltros[1],10);
      iCveOpinionEntidad = Integer.parseInt(aFiltros[2],10);
      iCveSegtoEntidad = Integer.parseInt(aFiltros[3],10);

      vDato = this.getDatosTraOpn();
    }
    public int getiEjercicio(){
      return vDato.getInt("IEJERCICIOSOLICITUD");
    }
    public int getiNumSolicitud(){
      return vDato.getInt("INUMSOLICITUD");
    }
    public int getiCveOpinionEntidad(){
      return vDato.getInt("ICVEOPINIONENTIDAD");
    }
    public int getiCveSegtoEntidad(){
      return vDato.getInt("ICVESEGTOENTIDAD");
    }
    public String getOpnDirigidoA(){
      return vDato.getString("COPNDIRIGIDOA");
    }
    public String getPtoOpn(){
      return vDato.getString("CPTOOPINION");
    }

  }

  public class TDDatosFolioOpn extends DAOBase{
      private TVDinRep vDato;
      private TParametro VParametros = new TParametro("44");
      private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
      private String cFolio;
      private int iEjercicio, iNumSolicitud, iCveOpinionEntidad,iCveSegtoEntidad,iConsecutivo;
      TDObtenDatos obtenerDatos = new TDObtenDatos();

      public TDDatosFolioOpn(){
        super.setSistema(cSistema);
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


      private TVDinRep getDatosTraOpn() throws Exception{
        Vector vRegsDatos = new Vector();
        String cSql = "select * " +
            "from GRLFOLIOXSEGTOENT " +
            "where ICVESEGTOENTIDAD = " + iCveSegtoEntidad + " and lFolioReferenInterno = 0 and ICVEOFICINA is null";


        try{
           vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
        }catch(SQLException ex){
          cMensaje = ex.getMessage();
        }catch(Exception ex2){
          cMensaje += ex2.getMessage();
        }
        if (vRegsDatos.size() > 0){
          TVDinRep vDatosFolio = (TVDinRep) vRegsDatos.get(0);
          iConsecutivo = vDatosFolio.getInt("ICONSECUTIVOSEGTOREF");
          this.Update(conn);
          return(TVDinRep) vRegsDatos.get(0);
        }else{
          this.insert(conn);
          return new TVDinRep();
        }
      }


      public void setFolio(int SegtoEntidad, String Folio) throws Exception{
        cFolio = Folio;
        iCveSegtoEntidad = SegtoEntidad;
        vDato = this.getDatosTraOpn();
      }

      public TVDinRep Update(Connection cnNested) throws
      Exception{
         DbConnection dbConn = null;
         Connection conn = cnNested;
         PreparedStatement lPStmt = null;
         boolean lSuccess = true;
         TVDinRep vData=null;


         obtenerDatos.dFolio.setDatosFolio(cFolio);
         try{
           if(cnNested == null){
             dbConn = new DbConnection(dataSourceName);
             conn = dbConn.getConnection();
             conn.setAutoCommit(false);
             conn.setTransactionIsolation(2);
           }

           //Update GRLFolioXSegtoEn
           String lSQL = "update GRLFOLIOXSEGTOENT set IEJERCICIOFOLIO = " +obtenerDatos.dFolio.getCveEjercicio() + "," +
               " ICVEOFICINA = " + obtenerDatos.dFolio.getCveOficina() + "," +
               " ICVEDEPARTAMENTO = " + obtenerDatos.dFolio.getCveDepartamento() +"," +
               " CDIGITOSFOLIO = '" + obtenerDatos.dFolio.getCveDigitosFolio()+ "' ," +
               " ICONSECUTIVO = " + obtenerDatos.dFolio.getCveConsecutivo() +
               " where ICVESEGTOENTIDAD = " +iCveSegtoEntidad+ " and ICONSECUTIVOSEGTOREF = "+iConsecutivo;

           lPStmt = conn.prepareStatement(lSQL);
           lPStmt.executeUpdate();
           if(cnNested == null){
             conn.commit();
           }

         } catch(Exception ex){
           ex.printStackTrace();
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

       public TVDinRep insert(Connection cnNested) throws
           DAOException{
         DbConnection dbConn = null;
         Connection conn = cnNested;
         PreparedStatement lPStmt = null;
         boolean lSuccess = true;
         TVDinRep vData=null;
         obtenerDatos.dFolio.setDatosFolio(cFolio);
         try{
           if(cnNested == null){
             dbConn = new DbConnection(dataSourceName);
             conn = dbConn.getConnection();
             conn.setAutoCommit(false);
             conn.setTransactionIsolation(2);
           }

         //Insertar Registro en GRLFolioXSegtoEnt

         Vector vcData = findByCustom("","select MAX(iConsecutivoSegtoRef) AS iConsecutivoSegtoRef from GRLFolioXSegtoEnt where iCveSegtoEntidad = "+iCveSegtoEntidad);
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         int iConsecutivoSegto = vUltimo.getInt("iConsecutivoSegtoRef");
         iConsecutivoSegto+=1;

         String lSQL =
             "insert into GRLFolioXSegtoEnt(iCveSegtoEntidad,iConsecutivoSegtoRef,LFOLIOREFERENINTERNO,iEjercicioFolio,iCveOficina,iCveDepartamento, "+
             "cDigitosFolio,iConsecutivo) "+
             "values ("+iCveSegtoEntidad+","+iConsecutivoSegto+",0,"+obtenerDatos.dFolio.getCveEjercicio()+","+
             obtenerDatos.dFolio.getCveOficina()+","+obtenerDatos.dFolio.getCveDepartamento()+","+" '"+
             obtenerDatos.dFolio.getCveDigitosFolio()+"'"+" ,"+obtenerDatos.dFolio.getCveConsecutivo()+" )";




         lPStmt = conn.prepareStatement(lSQL);
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

   }

  public class TDDatosOficPer extends DAOBase{
    private TVDinRep vDato;
    private int iCveOpinionEntidad;

    public TDDatosOficPer(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDatosTraOpn(){
      Vector vRegsDatos = new Vector();
      String cSql = " SELECT ICVEOFICINAOPN,ICVEDEPARTAMENTOOPN,ICVEPERSONA,ICVEDOMICILIO " +
          " FROM GRLOpinionEntidad " +
          " WHERE GRLOpinionEntidad.iCveOpinionEntidad = "+iCveOpinionEntidad;

      try{
        vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }

    public void setFiltroOpnEnt(int cveOpinionEntidad){
      iCveOpinionEntidad = cveOpinionEntidad;
      vDato = this.getDatosTraOpn();
    }
    public int getiCvePersona(){
      return vDato.getInt("ICVEPERSONA");
    }
    public int getiCveOficina(){
      return vDato.getInt("ICVEOFICINAOPN");
    }
    public int getiCveDepto(){
      return vDato.getInt("ICVEDEPARTAMENTOOPN");
    }
    public int getiCveDomicilio(){
      return vDato.getInt("ICVEDOMICILIO");
    }
  }

  public class TDDatosOpnCont extends DAOBase{
    private TVDinRep vDato;
    private int iCveSegtoContestacion;

    public TDDatosOpnCont(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDatosOpnCont(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT " +
          "ICVEOFICINA, " +
          "ICVEDEPARTAMENTO " +
          "FROM GRLFOLIOXSEGTOENT " +
          "where ICVESEGTOENTIDAD  = " +iCveSegtoContestacion +
          " and LFOLIOREFERENINTERNO = 0";


      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }

    public void setFiltro(int iCveSegtoCon){
      iCveSegtoContestacion = iCveSegtoCon;
      vDato = this.getDatosOpnCont();
    }
    public int getiOficinaCon(){
      return vDato.getInt("ICVEOFICINA");
    }
    public int getiDeptoCon(){
      return vDato.getInt("ICVEDEPARTAMENTO");
    }
  }

  public class TDDatosOpnSolicitante extends DAOBase{
    private TVDinRep vDato;
    private int iEjercicioSol, iNumSolicitud;

    public TDDatosOpnSolicitante(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDatosOpnSol(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT " +
          "P.cNomRazonSocial || ' ' || P.cApPaterno  || ' ' || P.cApMaterno AS cNomCompleto " +
          "FROM " +
          "TRAREGSOLICITUD TRA " +
          "join GRLPERSONA P on TRA.ICVESOLICITANTE = P.ICVEPERSONA " +
          "where TRA.IEJERCICIO = " +iEjercicioSol+
          " and TRA.INUMSOLICITUD = "+iNumSolicitud;

      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }

    public void setFiltro(int Ejercicio, int iSolicitud){
      iEjercicioSol = Ejercicio;
      iNumSolicitud = iSolicitud;
      vDato = this.getDatosOpnSol();
    }
    public String getcNombre(){
      return vDato.getString("cNomCompleto");
    }
  }
  public class TDDatosSegtoEntidad extends DAOBase{
    private TVDinRep vDato;
    private int iCveSegtoEntidad;

    public TDDatosSegtoEntidad(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDatosOpnSol(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT ICVESEGTOENTIDAD, CSIGLAS FROM GRLSegtoEntidad Where ICVESEGTOENTIDAD = "+iCveSegtoEntidad;

      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }

    public void setFiltroSegtoEnt(String Filtros){
      System.out.print("*****    Filtros en setFiltro TDDatosSegtoEntidad  ");
      String[] aFiltros = Filtros.split(",");
      iCveSegtoEntidad = Integer.parseInt(aFiltros[3],10);

      vDato = this.getDatosOpnSol();
    }
    public String getcSiglas(){
      return vDato.getString("cSiglas");
    }
  }

}

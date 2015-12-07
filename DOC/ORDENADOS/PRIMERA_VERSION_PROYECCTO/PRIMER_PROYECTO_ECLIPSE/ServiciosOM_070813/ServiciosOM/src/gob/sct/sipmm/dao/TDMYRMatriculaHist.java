package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDMYRMatricula.java</p>
 * <p>Description: DAO de la entidad MYRMatricula</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
public class TDMYRMatriculaHist extends DAOBase{
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
     * <p><b> insert into MYRMatricula(iEjercicio,iCveMatricula,iCvePais,iCveEntidadFed,iCveOficina,iCveCapitaniaParaMat,cConsecutivoMatricula,iDigVerificador,lVigente,iCveEmbarcacion,iNumSolicitud,iEjercicioSolicitud,lAprobada,iCvePropietario,cNomEmbarcacion,iEstatus,iCveTipoServ,iCveTipoNavega,iCveTipoRespons) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
     * <p><b> Campos Llave: iEjercicio,iCveMatricula, </b></p>
     * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
     *                            - Para este caso en particular contiene iEjercicio,iCveMatricula,iCveEmbarcacion.
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
            "insert into MYRMatriculaHist("+
            "IEJERCICIO,ICVEMATRICULA,DTMOVIMIENTO,CNOMEMBARCACION,CNOMPROPIETARIO,"+
            "ICVEPROPIETARIO,ICVETIPOEMBARCACION,ICVETIPOSERV,ICVETIPONAVEGA,DARQUEOBRUTO,"+
            "IUNIDADMEDARQUEOBRUTO,DARQUEONETO,IUNIDADMEDARQUEONETO,DPESOMUERTO,IUNIDADMEDPESOMUERTO,"+
            "DESLORA,IUNIDADMEDESLORA,DMANGA,IUNIDADMEDMANGA,DPUNTAL,"+
            "IUNIDADMEDPUNTAL,IPOTENCIATOTAL,IUNIDADMEDPOTENCIATOTAL,ICVEUSUARIO) "+
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String cEmbarcacion = 
        	"SELECT " +
        	"  E.CNOMEMBARCACION,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CNOMPROPIETARIO,E.ICVEPROPIETARIO,E.ICVETIPOEMBARCACION,E.ICVETIPOSERV, " +
        	"  E.ICVETIPONAVEGA,E.DARQUEOBRUTO,E.IUNIMEDARQUEOBRUTO,E.DARQUEONETO,E.IUNIMEDARQUEONETO, " +
        	"  E.DPESOMUERTO,E.IUNIMEDPESOMUERTO,E.DESLORA,E.IUNIMEDESLORA,E.DMANGA, " +
        	"  E.IUNIMEDMANGA,E.DPUNTAL,E.IUNIMEDPUNTAL,E.IPOTENCIATOTAL,E.IUNIDADMEDPOTENCIA " +
        	"FROM VEHEMBARCACION E " +
        	"LEFT JOIN GRLPERSONA P ON P.ICVEPERSONA = E.ICVEPROPIETARIO " +
        	"WHERE E.ICVEVEHICULO= "+vData.getInt("iCveEmbarcacion");
        

        //AGREGAR AL ULTIMO ...
        Vector vcEmbarcacion = findByCustom("",cEmbarcacion);
        TVDinRep vEmbarcacion = new TVDinRep();
        if(vcEmbarcacion.size() > 0){
          vEmbarcacion = (TVDinRep) vcEmbarcacion.get(0);
          TFechas fecha = new TFechas();
          lPStmt = conn.prepareStatement(lSQL);
          lPStmt.setInt(1,vData.getInt("iEjercicio"));
          lPStmt.setInt(2,vData.getInt("iCveMatricula"));
          lPStmt.setDate(3,fecha.getDateSQL(fecha.getThisTime()));
          lPStmt.setString(4,vEmbarcacion.getString("CNOMEMBARCACION"));
          lPStmt.setString(5,vEmbarcacion.getString("CNOMPROPIETARIO"));
          lPStmt.setInt(6,vEmbarcacion.getInt("ICVEPROPIETARIO"));
          lPStmt.setInt(7,vEmbarcacion.getInt("ICVETIPOEMBARCACION"));
          lPStmt.setInt(8,vEmbarcacion.getInt("ICVETIPOSERV"));
          lPStmt.setInt(9,vEmbarcacion.getInt("ICVETIPONAVEGA"));
          lPStmt.setDouble(10,vEmbarcacion.getDouble("DARQUEOBRUTO"));
          lPStmt.setInt(11,vEmbarcacion.getInt("IUNIMEDARQUEOBRUTO"));
          lPStmt.setDouble(12,vEmbarcacion.getDouble("DARQUEONETO"));
          lPStmt.setInt(13,vEmbarcacion.getInt("IUNIMEDARQUEONETO"));
          lPStmt.setDouble(14,vEmbarcacion.getDouble("DPESOMUERTO"));
          lPStmt.setInt(15,vEmbarcacion.getInt("IUNIMEDPESOMUERTO"));
          lPStmt.setDouble(16,vEmbarcacion.getDouble("DESLORA"));
          lPStmt.setInt(17,vEmbarcacion.getInt("IUNIMEDESLORA"));
          lPStmt.setDouble(18,vEmbarcacion.getDouble("DMANGA"));
          lPStmt.setInt(19,vEmbarcacion.getInt("IUNIMEDMANGA"));
          lPStmt.setDouble(20,vEmbarcacion.getDouble("DPUNTAL"));
          lPStmt.setInt(21,vEmbarcacion.getInt("IUNIMEDPUNTAL"));
          lPStmt.setDouble(22,vEmbarcacion.getDouble("IPOTENCIATOTAL"));
          lPStmt.setInt(23,vEmbarcacion.getInt("IUNIDADMEDPOTENCIA"));
          lPStmt.setInt(24,vData.getInt("iCveusuario"));
          lPStmt.executeUpdate();
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
    public TVDinRep insertNew(TVDinRep vData,Connection cnNested) throws
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
            "INSERT INTO MYRMATRICULAHIST ( " +
            "    IEJERCICIO,ICVEMATRICULA,DTMOVIMIENTO, " +//3
            "    CNOMEMBARCACION, " +//4
            "    CNOMPROPIETARIO,ICVEPROPIETARIO, " +//6
            "    ICVETIPOEMBARCACION,ICVETIPOSERV,ICVETIPONAVEGA, " +//9
            "    DARQUEOBRUTO,IUNIDADMEDARQUEOBRUTO, " +//11
            "    DARQUEONETO,IUNIDADMEDARQUEONETO, " +//13
            "    DPESOMUERTO,IUNIDADMEDPESOMUERTO, " +//15
            "    DESLORA,IUNIDADMEDESLORA, " +//17
            "    DMANGA,IUNIDADMEDMANGA, " +//19
            "    DPUNTAL,IUNIDADMEDPUNTAL, " +//21
            "    IPOTENCIATOTAL,IUNIDADMEDPOTENCIATOTAL, " +//23
            "    ICVEUSUARIO) " +//24
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

        vData.addPK(vData.getString("iEjercicio"));
        vData.addPK(vData.getString("iCveMatricula"));
        lPStmt = conn.prepareStatement(lSQL);
        lPStmt.setInt(1, vData.getInt("iEjercicio"));
        lPStmt.setInt(2,vData.getInt("iCveMatricula"));
        lPStmt.setDate(3,vData.getDate("dtMovimiento"));
        lPStmt.setString(4,vData.getString("cNomEmbarcacion"));
        lPStmt.setString(5,vData.getString("cPropietario"));
        lPStmt.setInt(6,vData.getInt("iCvePropietario"));
        lPStmt.setInt(7,vData.getInt("iCveTipoEmbarcacion"));
        lPStmt.setInt(8,vData.getInt("iCveTipoServ"));
        lPStmt.setInt(9,vData.getInt("iCveTipoNavega"));
        lPStmt.setDouble(10,vData.getDouble("dArqueoBruto"));
        lPStmt.setInt(11,vData.getInt("iUnidadMedArqueoBruto"));
        lPStmt.setDouble(12,vData.getDouble("dArqueoNeto"));
        lPStmt.setInt(13,vData.getInt("iUnidadMedArqueoNeto"));
        lPStmt.setDouble(14,vData.getDouble("dPesoMuerto"));
        lPStmt.setInt(15,vData.getInt("iUnidadMedPesoMuerto"));
        lPStmt.setDouble(16,vData.getDouble("dEslora"));
        lPStmt.setInt(17,vData.getInt("iUnidadMedEslora"));
        lPStmt.setDouble(18,vData.getDouble("dManga"));
        lPStmt.setInt(19,vData.getInt("iUnidadMedManga"));
        lPStmt.setDouble(20,vData.getDouble("dPuntal"));
        lPStmt.setInt(21,vData.getInt("iUnidadMedPuntal"));
        lPStmt.setDouble(22,vData.getInt("dPotenciaTotal"));
        lPStmt.setInt(23,vData.getInt("iUnidadMedPotenciaTotal"));
        lPStmt.setInt(24,vData.getInt("iCveUsuario"));

        lPStmt.executeUpdate();
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


}

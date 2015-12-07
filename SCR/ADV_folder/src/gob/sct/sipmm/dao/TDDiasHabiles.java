package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;
/**
 * <p>Title: TDTARContraptacion.java</p>
 * <p>Description: DAO de la entidad TARContraptacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */
public class TDDiasHabiles extends DAOBase{
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
 public Vector diashab(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   TFechas tFecha = new TFechas();
   boolean lSuccess = true, lEncontro = false;
   int iEjercicio =0;
   java.sql.Date dtOrigen = null, dtFinal = null;
   java.sql.Date dtFechaActual = null;
   Vector vFechaHabil = new Vector();

   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
     }

       iEjercicio = vData.getInt("iEjercicio");
       dtOrigen = tFecha.getDateSQL("01","01",""+iEjercicio);
       dtFinal = tFecha.getDateSQL("31","12",""+iEjercicio);
       dtFechaActual = dtOrigen;

       // Traer los días no habiles dado un ejercicio
       Vector vcData = findByCustom("","SELECT DTNOHABIL as DTNOHABIL FROM GRLDIANOHABIL " +
                                       " WHERE IEJERCICIO = " + iEjercicio + "  ORDER BY DTNOHABIL ASC");
       if(vcData.size()>0){
//         for(int i=1;i<365;i++){
         for(int i=0;dtFechaActual.compareTo(dtFinal)<0;i++){
            lEncontro = false;
            dtFechaActual = tFecha.aumentaDisminuyeDias(dtOrigen,i);
            for(int j=0;j<vcData.size();j++){
               TVDinRep vUltimo = (TVDinRep) vcData.get(j);
               if(dtFechaActual.equals(vUltimo.getDate("DTNOHABIL"))){
                  vcData.removeElementAt(j);
                  lEncontro = true;
                  break;
               }
            }

            if(!lEncontro){
              TVDinRep vDiaHabil = new TVDinRep();
              vDiaHabil.put("dtDiaHabil",dtFechaActual);
              vFechaHabil.add(vDiaHabil);
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
     return vFechaHabil;
   }
 }


}

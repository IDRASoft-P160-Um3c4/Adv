package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDCBBRuta.java</p>
 * <p>Description: DAO de la entidad CBBRuta</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
public class TDGRLConsultaTabla extends DAOBase{
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
 public boolean ExisteDato(String cTabla,String cWhere) throws DAOException{
   Vector vcRecords = new Vector();
   cError = "";
   try{
     vcRecords = this.FindByGeneric("",
                                    "Select * from " + cTabla + " Where " + cWhere,
                                    dataSourceName);
   } catch(Exception e){
     cError = e.toString();
   } finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vcRecords.size() > 0 ? true : false;
   }
 }

 public boolean ExisteDatoReadUncommitted(String cTabla,String cWhere) throws DAOException{
   Vector vcRecords = new Vector();
   cError = "";
   try{
     vcRecords = this.FindByGenericReadUncommitted("",
                                    "Select * from " + cTabla + " Where " + cWhere,
                                    dataSourceName);
   } catch(Exception e){
     cError = e.toString();
   } finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vcRecords.size() > 0 ? true : false;
   }
 }


}

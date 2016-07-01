package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.sql.*;
import com.micper.seguridad.vo.*;

/**
 * <p>Title: TDGRLIngresos.java</p>
 * <p>Description: DAO de Consult
 * as de Ingresos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: U.T.I.C., S.C.T.</p>
 * @author ALOPEZ
 * @version 1.0
 */
public class TDGRLIngresos extends DAOBase{
   private TParametro VParametros = new TParametro("44");
  //private String dataSourceName = "java:/IngresosDS";
  private String dataSourceName = VParametros.getPropEspecifica("IngresosDS");
  public Vector findByCustom(String cKey,String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey, cSQL, dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }
}

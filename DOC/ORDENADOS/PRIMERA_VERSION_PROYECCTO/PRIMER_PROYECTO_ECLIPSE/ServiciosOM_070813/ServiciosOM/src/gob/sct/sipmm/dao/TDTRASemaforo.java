package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
import com.micper.util.TFechas;
/**
 * <p>Title: TDVEHMotor.java</p>
 * <p>Description: DAO de la entidad VEHMotor</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Enrique Moreno Belmares
 * @version 1.0
 */
public class TDTRASemaforo extends DAOBase{
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
    TFechas fecha = new TFechas();
    TDGRLDiaNoHabil diaNoHabil = new TDGRLDiaNoHabil();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
      for(int i=0;i<vcRecords.size();i++){
    	TVDinRep vRecords = (TVDinRep) vcRecords.get(i);
    	java.sql.Date dtFinal =diaNoHabil.getFechaFinal(fecha.getDateSQL(vRecords.getTimeStamp("TSREGISTRO")), 5, false);
    	
    	vRecords.put("dtFinNotifica", dtFinal);
      }
    } catch(Exception e){
      e.printStackTrace();    
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }
}

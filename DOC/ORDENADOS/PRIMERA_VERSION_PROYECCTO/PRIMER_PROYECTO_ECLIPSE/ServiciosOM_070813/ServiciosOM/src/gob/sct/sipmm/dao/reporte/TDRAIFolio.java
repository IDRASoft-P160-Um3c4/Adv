package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.*;
import com.micper.sql.*;
import com.micper.util.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDRAIRequerimiento.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author iCaballero
 * @version 1.0
 */

public class TDRAIFolio extends DAOBase{
	private TParametro VParametros = new TParametro("44");

	public TDRAIFolio(){
	}

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
				"insert into RAIFolio(iCveRegularizacion,iConsecutivo,iEjercicio,iCveOficina,iCveDepartamento," +
				"cDigitosFolio,iConsecutivoFolio,iCveTipoDocto) values (?,?,?,?,?,?,?,?)";
			String cSQLMax = "select MAX(iConsecutivo) AS iConsecutivo from RAIFolio where iCveRegularizacion = " + vData.getInt("iCveRegularizacion");
//			AGREGAR AL ULTIMO ...
		      Vector vcData = super.FindByGeneric("",cSQLMax,dataSourceName);
		      if(vcData.size() > 0){
		         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
		         vData.put("iConsecutivo",vUltimo.getInt("iConsecutivo") + 1);
		      }else
		         vData.put("iConsecutivo",1);

			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iCveRegularizacion"));
			lPStmt.setInt(2,vData.getInt("iConsecutivo"));
			lPStmt.setInt(3,vData.getInt("iEjercicio"));
			lPStmt.setInt(4,vData.getInt("iCveOficina"));
			lPStmt.setInt(5,vData.getInt("iCveDepartamento"));
			lPStmt.setString(6,vData.getString("cDigitosFolio"));
			lPStmt.setInt(7,vData.getInt("iConsecutivoFolio"));
			lPStmt.setInt(8,vData.getInt("iCveTipoDocto"));
			lPStmt.executeUpdate();
			
			if(cnNested == null)
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


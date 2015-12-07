package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;

public class TDRAISancion
extends DAOBase{
	private int iEjercicioOficio = 0;
	private String cDigitosFolio = "";
	private int iConsecutivo = 0;

	public TDRAISancion(){
	}

	public Vector RepGeneral(String cQuery,String cNumFolio,
			String cCveOficinaOrigen,String cCveDeptoOrigen){

		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");
		int iCveOficinaDest=0,iCveDeptoDest=0,iCveTitular=0,iCveDomicilio=1,iCveRepLegal=0;
		String cParametros [] = cQuery.split(",");
		int iCveRegularizacion = Integer.parseInt(cParametros[0]);
		iCveTitular = Integer.parseInt(cParametros[1]);

		//System.out.println("*****  "+cParametros[2]);
		//if(cParametros[2]!="")iCveDomicilio = Integer.parseInt(cParametros[2]);else iCveDomicilio = 1;
		String cAsunto = "";
		Vector vcData = new Vector();
		TDRAIFolio dFolio = new TDRAIFolio();
		TDObtenDatos dObten = new TDObtenDatos();
		dObten.dFolio.setDatosFolio(cNumFolio);
                String cQrySancion =
                    "SELECT S.DTSANCION,R.DTCOMISION,R.CHORACOMISION,P.CDSCPUERTO FROM RAIREGULARIZACION R " +
                    "JOIN RAISANCION S ON R.ICVEREGULARIZACION = S.ICVEREGULARIZACION " +
                    "LEFT JOIN RAIUBICACION U ON R.ICVEREGULARIZACION = U.ICVEREGULARIZACION " +
                    "LEFT JOIN GRLPUERTO P ON U.ICVEPUERTO = P.ICVEPUERTO " +
                    "WHERE S.ICVEREGULARIZACION = "+iCveRegularizacion;


		/***/
                try{
                  vcData = this.FindByGeneric("",cQrySancion,dataSourceName);
                } catch(Exception e){
                  e.printStackTrace();
                }
                /***/
                rep.iniciaReporte();
                if(vcData.size()>0){
                  TVDinRep vSancion = (TVDinRep) vcData.get(0);
                  rep.comRemplaza("[cPuerto]",vSancion.getString("CDSCPUERTO"));
                  rep.comRemplaza("[cFechaComision]",Fechas.getDateSPN(vSancion.getDate("DTCOMISION")));
                  rep.comRemplaza("[cHoraComision]",vSancion.getString("CHORACOMISION"));
                }

		//return ;
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				iCveTitular,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"","",
				true,"cCuerpo",vcCuerpo,
				true,vcCopiasPara,
				rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());

		TVDinRep vInsertaFolio = new TVDinRep();
		vInsertaFolio.put("iCveRegularizacion", iCveRegularizacion);
		vInsertaFolio.put("iEjercicio", dObten.dFolio.getCveEjercicio());
		vInsertaFolio.put("iCveOficina", dObten.dFolio.getCveOficina());
		vInsertaFolio.put("iCveDepartamento", dObten.dFolio.getCveDepartamento());
		vInsertaFolio.put("cDigitosFolio", dObten.dFolio.getCveDigitosFolio());
		vInsertaFolio.put("iConsecutivoFolio", dObten.dFolio.getCveConsecutivo());
		vInsertaFolio.put("iCveTipoDocto", 1);

		try {
			dFolio.insert(vInsertaFolio, null);
		} catch (DAOException e) {
			e.printStackTrace();
		}


		return vRetorno;

	}

}

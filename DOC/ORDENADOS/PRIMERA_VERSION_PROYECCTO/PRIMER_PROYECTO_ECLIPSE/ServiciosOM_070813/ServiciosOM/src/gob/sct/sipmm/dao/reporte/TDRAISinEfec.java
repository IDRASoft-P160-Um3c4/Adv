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

public class TDRAISinEfec
extends DAOBase{
	private int iEjercicioOficio = 0;
	private String cDigitosFolio = "";
	private int iConsecutivo = 0;

	public TDRAISinEfec(){
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

		String cAsunto = "";

		String cSql =
                    "SELECT SE.CNUMESCRITO,F1.CDIGITOSFOLIO, F1.ICONSECUTIVOFOLIO, F1.IEJERCICIO ,R.DTDOCUMENTO, " +
                    "U.LDENTRORECINTOPORTUARIO, U.CUBICACION, P.CDSCPUERTO " +
                    "FROM RAIREGULARIZACION R " +
                    "JOIN RAIFOLIO F1 ON R.ICVEREGULARIZACION = F1.ICVEREGULARIZACION AND F1.ICVETIPODOCTO = 1 " +
                    "JOIN RAIFOLIO F2 ON R.ICVEREGULARIZACION = F2.ICVEREGULARIZACION AND F2.ICVETIPODOCTO = 4 " +
                    "LEFT JOIN RAISINEFECTO SE ON F2.ICVEREGULARIZACION = SE.ICVEREGULARIZACIONREF AND F2.ICONSECUTIVO = SE.ICONSECUTIVOREF " +
                    "LEFT JOIN RAIUBICACION U ON R.ICVEREGULARIZACION = U.ICVEREGULARIZACION " +
                    "LEFT JOIN GRLPUERTO P ON U.ICVEPUERTO = P.ICVEPUERTO " +
                    "WHERE R.ICVEREGULARIZACION = "+iCveRegularizacion;
		Vector vcData = new Vector();
		TDRAIFolio dFolio = new TDRAIFolio();
		TDObtenDatos dObten = new TDObtenDatos();
		dObten.dFolio.setDatosFolio(cNumFolio);
		/***/
                try{
                  vcData = this.FindByGeneric("",cSql,dataSourceName);
                } catch(Exception e){
                  e.printStackTrace();
                }
                /***/
                rep.iniciaReporte();
                if(vcData.size()>0){
                  TVDinRep vSinEfectos = (TVDinRep) vcData.get(0);
                  rep.comRemplaza("[cNumEscrito]",vSinEfectos.getString("CNUMESCRITO"));
                  rep.comRemplaza("[NoOficio]",vSinEfectos.getString("CDIGITOSFOLIO")+"."+vSinEfectos.getInt("ICONSECUTIVOFOLIO")+"/"+vSinEfectos.getInt("iEjercicio"));
                  rep.comRemplaza("[fechaOficioConLetra]",Fechas.getDateSPN(vSinEfectos.getDate("DTDOCUMENTO")));
                  rep.comRemplaza("[cUbicacion]",vSinEfectos.getInt("LDENTRORECINTOPORTUARIO")==1?vSinEfectos.getString("CDSCPUERTO"):vSinEfectos.getString("CUBICACION"));
                }

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
		vInsertaFolio.put("iCveTipoDocto", 4);

		try {
			dFolio.insert(vInsertaFolio, null);
		} catch (DAOException e) {
			e.printStackTrace();
		}


		return vRetorno;

	}

}

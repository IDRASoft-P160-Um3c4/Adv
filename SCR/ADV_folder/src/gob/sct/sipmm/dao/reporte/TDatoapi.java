package gob.sct.sipmm.dao.reporte;

import java.sql.SQLException;
import java.util.Vector;

import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.DAOBase;
import com.micper.util.TFechas;
import com.micper.util.TWord;

public class TDatoapi extends DAOBase {

	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros
			.getPropEspecifica("ConDBModulo");

	public Vector fReporte(String cQuery, String cNumFolio,
			String cCveOficinaOrigen, String cCveDeptoOrigen) {
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector Observaciones = new Vector();
		
		TWord rep = new TWord();
		
		int iCveInconforme = 0, iCveDomicilio = 0;
		String ICVEOPINIONENTIDAD = "",COPINIONDIRIGIDOA = "",CPUESTOOPINION = "",PERSONA= "",ASUNTOLA="",OBJTITULO ="";
		String FECHASOL ="", NUMPLANOS= "";
		String[] cParametros = cQuery.split(",");
		TFechas fecha = new TFechas();
		OBJTITULO = cParametros[3];
		
		rep.iniciaReporte();
		if(OBJTITULO != ""){
			rep.comRemplaza("[ObjetoAutTec]", OBJTITULO);
		}else{
			rep.comRemplaza("[ObjetoAutTec]", "");
			//System.out.print("[ObjetoAutTec]" + OBJTITULO);
		}
		try {
			vRegs = super
					.FindByGeneric(
							"",
							"SELECT " +
							"iCveOpinionEntidad,COPINIONDIRIGIDOA,CPUESTOOPINION,GRLPERSONA.CNOMRAZONSOCIAL||' " +
							"'|| grlpersona.CAPPATERNO||' '|| grlpersona.CAPMATERNO AS CDEPENDENCIAEXT " +
							"FROM GRLOPINIONENTIDAD O " +
							"JOIN GRLPERSONA on grlpersona.icvepersona= O.ICVEPERSONA " +
							"JOIN TRAREGSOLICITUD T ON T.ICVETRAMITE=O.ICVETRAMITE AND T.ICVEMODALIDAD=O.ICVEMODALIDAD " +
							"WHERE T.IEJERCICIO ="+cParametros[2]+" AND T.INUMSOLICITUD="+cParametros[1]+
							" AND LESOPINIONINTERNA=0 "
							, dataSourceName);
		} catch (SQLException ex) {
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		//rep.comRemplaza("[dtFechaActual]", tFecha.getDateSPN(tFecha.getDateSQL(tFecha.getThisTime())));
		if (vRegs.size() > 0) {
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);
			ICVEOPINIONENTIDAD = vDatos.getString("iCveOpinionEntidad");
			COPINIONDIRIGIDOA = vDatos.getString("COPINIONDIRIGIDOA");
			CPUESTOOPINION = vDatos.getString("CPUESTOOPINION");
			PERSONA = vDatos.getString("CDEPENDENCIAEXT");
			
			//System.out.print("ICVEOPINIONENTIDAD" + ICVEOPINIONENTIDAD);
			//System.out.print("COPINIONDIRIGIDOA" + COPINIONDIRIGIDOA);
			//System.out.print("CPUESTOOPINION" + CPUESTOOPINION);
			//System.out.print("PERSONA" + PERSONA);
			if (COPINIONDIRIGIDOA != "")
				rep.comRemplaza("[cDirigidoa]", COPINIONDIRIGIDOA);
			else
				rep.comRemplaza("[cDirigidoa]", "");
			if (PERSONA != "")
				rep.comRemplaza("[cDependenciaExterna]", PERSONA);
			else
				rep.comRemplaza("[cDependenciaExterna]", "");
		}
		
		try {
			Observaciones = super
					.FindByGeneric(
							"",
							"SELECT GRLSEGTOENTIDAD.COBSESSEGTO, T.TSREGISTRO from TRAOPNENTTRAMITE OT " +
							"JOIN GRLSEGTOENTIDAD ON GRLSEGTOENTIDAD.ICVESEGTOENTIDAD = OT.ICVESEGTOENTIDAD " +
							"JOIN TRAREGSOLICITUD AS T ON T.IEJERCICIO=OT.IEJERCICIOSOLICITUD AND T.INUMSOLICITUD=OT.INUMSOLICITUD " +
							" WHERE  OT.IEJERCICIOSOLICITUD = "+cParametros[2] +
							" AND OT.INUMSOLICITUD = "+cParametros[1]+
							" ORDER BY  GRLSEGTOENTIDAD.ICVESEGTOENTIDAD"
							, dataSourceName);
		} catch (SQLException ex) {
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		if(Observaciones.size()>0){
			TVDinRep vDatos = (TVDinRep) Observaciones.get(0);
			ASUNTOLA = vDatos.getString("COBSESSEGTO");
			if (ASUNTOLA != "")
				rep.comRemplaza("[cAsuntoLargo]", ASUNTOLA);
			else
				rep.comRemplaza("[cAsuntoLargo]", "");
			//System.out.print("ASUNTOLA" + ASUNTOLA);
			
			//FECHASOL = vDatos.getString("TSREGISTRO");
			FECHASOL = fecha.getDateSPN(fecha.getDateSQL(vDatos.getTimeStamp("TSREGISTRO")));
			if (FECHASOL != "")
				rep.comRemplaza("[cFechaSolicitud]", FECHASOL);
			else
				rep.comRemplaza("[cFechaSolicitud]", "");
			//System.out.print("FECHASOL" + FECHASOL);
		}
		
		try {
			Observaciones = super
					.FindByGeneric(
							"",
							"SELECT " +
							"count(distinct(TRAPLANOXTRAMITE.ICVEPLANO)) AS PLANOS " +
							"FROM TRAPLANOXTRAMITE " +
							"JOIN OMPPLANO ON TRAPLANOXTRAMITE.ICVEPLANO = OMPPLANO.ICVEPLANO" +
							" WHERE  IEJERCICIO = "+cParametros[2] +
							" AND INUMSOLICITUD = "+cParametros[1]
							, dataSourceName);
		} catch (SQLException ex) {
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		if(Observaciones.size()>0){
			TVDinRep vDatos = (TVDinRep) Observaciones.get(0);
			NUMPLANOS = vDatos.getString("PLANOS");
			if (NUMPLANOS != "")
				rep.comRemplaza("[NumeroDePlanos]", NUMPLANOS);
			else
				rep.comRemplaza("[NumeroDePlanos]", "");
			//System.out.print("NUMPLANOS" + NUMPLANOS);

		}
		
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0, 0,
				iCveInconforme, iCveDomicilio, 0,
				"", "", "", "", true, "cCuerpo", vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		return vRetorno;

	}
}

package gob.sct.sipmm.dao.reporte;

import java.sql.SQLException;
import java.util.Vector;

import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.util.*;

public class TDRGCConsultas extends DAOBase{
	TFechas fecha = new TFechas();

	public TDRGCConsultas() {
	}

	public StringBuffer genPadron(String cFiltro){
		TExcel rep = new TExcel();
		String aFiltro[] = cFiltro.split(",");
		int iEjercicio = Integer.parseInt(aFiltro[0],10);
		int iCveMes = Integer.parseInt(aFiltro[1],10);
		int iCvePuerto = Integer.parseInt(aFiltro[2],10);
		Vector vData = new Vector();
		Vector vMeses = new Vector();
		TVDinRep vDinRep = new TVDinRep();
		int iReng = 9;

		String cFiltroQry = " where PTO.ICVEPUERTO = " +iCvePuerto;
		int iMesFinal = 0;
		int iEjercicioFinal = 0;
		if(iCveMes == 12){
			iMesFinal = 1;
			iEjercicioFinal = iEjercicio + 1;
		}else{
			iMesFinal = iCveMes + 1;
			iEjercicioFinal = iEjercicio;
		}

		if(iCveMes != -1)
			cFiltroQry += " and CON.DTREGISTRO BETWEEN '" +iEjercicio +"-" + iCveMes +"-01 00:00:00'"+
			" AND '"+iEjercicioFinal+"-"+iMesFinal+ "-01 00:00:00' ";
		else
			cFiltroQry += " and CON.DTREGISTRO BETWEEN '" +iEjercicio +"-01-01 00:00:00'"+
			" AND '"+iEjercicio+"-12-31 23:59:59' ";

		String cSQL = "SELECT " +
		"CON.CNUMCONTRATO AS CNUMCONTRATO, " +
		"PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CNOMBRE, " +
		"TPO.CDSCTIPOCONTRATO AS CTIPOCONTRATO, " +
		"CON.DTREGISTRO AS DTREGISTRO, " +
		"CON.CUSOINSTALPORTUARIA AS COBJETO, " +
		"CON.DTCONTRATO AS DTCONTRATO, " +
		"(YEAR(CON.DTVENCIMIENTO) - YEAR(CON.DTINIVIGENCIA)) AS CVIGENCIA, " +
		"CON.COBSCONTRATO AS COBSERVACION, " +
		"CON.CCONTRAPRESTACION as CCONTRAPRESTACION, " +
		"CON.CNUMCONVOCATORIA AS CNUMCONVOCATORIA, " +
		"CON.DTVENCIMIENTO AS DTVENCIMIENTO, " +
		"CON.DTINIVIGENCIA AS DTINIVIGENCIA, " +
		"PTO.CDSCPUERTO || ', '  || EFED.CNOMBRE AS CUBICACION " +
		"FROM RGCCONTRATO CON " +
		"JOIN RGCCONTRATOUBICACION UBI ON CON.ICVECONTRATO = UBI.ICVECONTRATO " +
		"JOIN GRLPUERTO PTO ON UBI.ICVEPUERTO = PTO.ICVEPUERTO " +
		"JOIN GRLENTIDADFED EFED ON PTO.ICVEPAIS = EFED.ICVEPAIS " +
		"AND PTO.ICVEENTIDADFED = EFED.ICVEENTIDADFED " +
		"JOIN RGCTIPOCONTRATO TPO ON CON.ICVETIPOCONTRATO = TPO.ICVETIPOCONTRATO " +
		"JOIN GRLPERSONA PER ON CON.ICVECESIONARIO = PER.ICVEPERSONA " + cFiltroQry;

		try {
			vData = super.FindByGeneric("", cSQL, dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rep.iniciaReporte();
		vMeses =  fecha.getVNombresMes();
		String cMeses = "";
		if(iCveMes == -1)
			cMeses = vMeses.toString();
		else
			cMeses = (String) vMeses.get(iCveMes-1);
		rep.comDespliegaCombinado("Registro de contratos del mes de " + cMeses + " del " + iEjercicio, "A", 5, "K", 5, rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO(), true, 0, false, false, 0, 0);
		for(int i = 0; i < vData.size(); i++){
			vDinRep = (TVDinRep) vData.get(i);
			rep.comDespliegaAlineado("A", iReng, vDinRep.getString("CUBICACION"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("B", iReng, vDinRep.getString("CNUMCONTRATO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("C", iReng, vDinRep.getString("CNOMBRE"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("D", iReng, vDinRep.getString("CTIPOCONTRATO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("E", iReng, vDinRep.getDate("DTREGISTRO") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTREGISTRO"),"."):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("F", iReng, vDinRep.getString("COBJETO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("G", iReng, vDinRep.getDate("DTCONTRATO") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTCONTRATO"),"."):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

			String cVigencia = vDinRep.getDate("DTINIVIGENCIA") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTINIVIGENCIA"),"."):"---";
			cVigencia += vDinRep.getDate("DTVENCIMIENTO") != null?" - " + fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTVENCIMIENTO"),"."):"- ---";

			rep.comDespliegaAlineado("H", iReng, vDinRep.getString("CVIGENCIA") + "  " + cVigencia , false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("I", iReng, !vDinRep.getString("CCONTRAPRESTACION").equalsIgnoreCase("null")?vDinRep.getString("CCONTRAPRESTACION"):"----", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("J", iReng, vDinRep.getString("CNUMCONVOCATORIA") != null && vDinRep.getString("CNUMCONVOCATORIA").equalsIgnoreCase("")? "Por convocatoria número " +vDinRep.getString("CNUMCONVOCATORIA"): "Directa", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
			rep.comDespliegaAlineado("K", iReng, vDinRep.getString("COBSERVACION"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

			rep.comBordeRededor("A", iReng, "A", iReng, 1, 1);
			rep.comBordeRededor("B", iReng, "B", iReng, 1, 1);
			rep.comBordeRededor("C", iReng, "C", iReng, 1, 1);
			rep.comBordeRededor("D", iReng, "D", iReng, 1, 1);
			rep.comBordeRededor("E", iReng, "E", iReng, 1, 1);
			rep.comBordeRededor("F", iReng, "F", iReng, 1, 1);
			rep.comBordeRededor("G", iReng, "G", iReng, 1, 1);
			rep.comBordeRededor("H", iReng, "H", iReng, 1, 1);
			rep.comBordeRededor("I", iReng, "I", iReng, 1, 1);
			rep.comBordeRededor("J", iReng, "J", iReng, 1, 1);
			rep.comBordeRededor("K", iReng, "K", iReng, 1, 1);
			iReng++;
		}

		rep.comDespliegaAlineado("C", iReng+2, "Total registrados ", false, rep.getAT_HIZQUIERDA(), rep.getAT_VSUPERIOR());
		rep.comDespliegaAlineado("D", iReng+2, vData.size() > 0?"" + vData.size():"0", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());



		return rep.getSbDatos();
	}

}

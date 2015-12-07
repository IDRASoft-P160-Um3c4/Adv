package gob.sct.sipmm.dao.reporte;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;

import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.util.*;

public class TDConsultaConcesiones extends DAOBase{
	TFechas fecha = new TFechas();

	public TDConsultaConcesiones() {
	}

	public StringBuffer generaPadronContratos(String cFiltro){
		TExcel rep = new TExcel();
		String aFiltro[] = cFiltro.split(",");
		String cIniPeriodo  = !aFiltro[0].equalsIgnoreCase("")?aFiltro[0]:null;
		String cFinPeriodo  = !aFiltro[1].equalsIgnoreCase("")?aFiltro[1]:null;
		int iCvePuerto = Integer.parseInt(aFiltro[2],10);
		String cObjeto = !aFiltro[3].equalsIgnoreCase("")?aFiltro[3]:null;
		int iCveModalidad = Integer.parseInt(aFiltro[4],10);
		boolean lVigente = aFiltro[5].equalsIgnoreCase("true")?true:false;
		String[] aEntidades = aFiltro[6].replace('|',',').split(",");
		Vector vData = new Vector();
		Vector vMeses = new Vector();
		TVDinRep vDinRep = new TVDinRep(), vDinPto = new TVDinRep();
		int iReng = 9, iContador=0;

		String cFiltroQry = "where 1 = 1 ";

		if(cIniPeriodo != null && cFinPeriodo != null)
			cFiltroQry +=" and CON.DTREGISTRO BETWEEN '" + fecha.getDateSQL(cIniPeriodo) + " 00:00:00' " +
			" and '" + fecha.getDateSQL(cFinPeriodo) + " 00:00:00' ";

		if(iCveModalidad != -1)
			cFiltroQry += " and CON.iCveTipoContrato = " + iCveModalidad;

		if(cObjeto != null)
			cFiltroQry += " and CON.CUSOINSTALPORTUARIA like '" + cObjeto + "&'";

		if(lVigente)
			cFiltroQry += " and CON.DTVENCIMIENTO > '" + fecha.TodaySQL() + " 00:00:00'";

		rep.iniciaReporte();
		if(cIniPeriodo != null && cFinPeriodo != null)
			rep.comDespliegaCombinado("Registro de contratos del período del " + fecha.getDateSPN(fecha.getDateSQL(cIniPeriodo)) +
					" al  " +  fecha.getDateSPN(fecha.getDateSQL(cFinPeriodo)),"A", 5, "K", 5, rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO(),
					true, 0, false, false, 0, 0);
		else
			rep.comDespliegaCombinado("Registro de contratos de todos los años.","A", 5, "K", 5, rep.getAT_COMBINA_CENTRO(), rep.getAT_VCENTRO(),
					true, 0, false, false, 0, 0);

		for(int i=0; i<aEntidades.length;i++){
			StringBuffer sbPto = new StringBuffer();
			sbPto.append("SELECT ")
			.append("iCvePuerto ")
			.append("FROM GRLPUERTO ")
			.append("where icvepais = 1 ")
			.append("and ICVEENTIDADFED =  " + aEntidades[i]);
			if(iCvePuerto != -1)
				sbPto.append(" and iCvePuerto = " + iCvePuerto);

			sbPto.append(" order by CDSCPUERTO ");

			Vector vDataPtos = new Vector();

			try {
				vDataPtos = super.FindByGeneric("", sbPto.toString(), dataSourceName);
			} catch (SQLException e) {
				e.printStackTrace();
			}


			for(int j = 0; j<vDataPtos.size(); j++){
				vDinPto = (TVDinRep) vDataPtos.get(j);
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT ");
				sb.append("CON.CNUMCONTRATO AS CNUMCONTRATO, ");
				sb.append("PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CNOMBRE, ");
				sb.append("TPO.CDSCTIPOCONTRATO AS CTIPOCONTRATO, ");
				sb.append("CON.DTREGISTRO AS DTREGISTRO, ");
				sb.append("CON.CUSOINSTALPORTUARIA AS COBJETO, ");
				sb.append("CON.DTCONTRATO AS DTCONTRATO, ");
				sb.append("(YEAR(CON.DTVENCIMIENTO) - YEAR(CON.DTINIVIGENCIA)) AS CVIGENCIA, ");
				sb.append("CON.COBSCONTRATO AS COBSERVACION, ");
				sb.append("CON.CCONTRAPRESTACION as CCONTRAPRESTACION, ");
				sb.append("CON.CNUMCONVOCATORIA AS CNUMCONVOCATORIA, ");
				sb.append("CON.DTVENCIMIENTO AS DTVENCIMIENTO, ");
				sb.append("CON.DTINIVIGENCIA AS DTINIVIGENCIA, ");
				sb.append("PTO.CDSCPUERTO || ', '  || EFED.CNOMBRE AS CUBICACION ");
				sb.append("FROM RGCCONTRATO CON ");
				sb.append("JOIN RGCCONTRATOUBICACION UBI ON CON.ICVECONTRATO = UBI.ICVECONTRATO ");
				sb.append("JOIN GRLPUERTO PTO ON UBI.ICVEPUERTO = PTO.ICVEPUERTO ");
				sb.append("JOIN GRLENTIDADFED EFED ON PTO.ICVEPAIS = EFED.ICVEPAIS ");
				sb.append("AND PTO.ICVEENTIDADFED = EFED.ICVEENTIDADFED ");
				sb.append("JOIN RGCTIPOCONTRATO TPO ON CON.ICVETIPOCONTRATO = TPO.ICVETIPOCONTRATO ");
				sb.append("JOIN GRLPERSONA PER ON CON.ICVECESIONARIO = PER.ICVEPERSONA ");
				sb.append(cFiltroQry);
				sb.append(" and PTO.iCvePuerto = " + vDinPto.getInt("iCvePuerto"));

				try {
					vData = super.FindByGeneric("", sb.toString(), dataSourceName);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				for(int y = 0; y < vData.size(); y++){
					vDinRep = (TVDinRep) vData.get(y);
					rep.comDespliegaAlineado("A", iReng, vDinRep.getString("CUBICACION"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("B", iReng, vDinRep.getString("CNUMCONTRATO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("C", iReng, vDinRep.getString("CNOMBRE"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("D", iReng, vDinRep.getString("CTIPOCONTRATO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("E", iReng, vDinRep.getDate("DTREGISTRO") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTREGISTRO"),".",true):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("F", iReng, vDinRep.getString("COBJETO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("G", iReng, vDinRep.getDate("DTCONTRATO") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTCONTRATO"),".",true):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

					String cVigencia = vDinRep.getDate("DTINIVIGENCIA") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTINIVIGENCIA"),".",true):"---";
					cVigencia += vDinRep.getDate("DTVENCIMIENTO") != null?" - " + fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTVENCIMIENTO"),".",true):"- ---";

					rep.comDespliegaAlineado("H", iReng, vDinRep.getString("CVIGENCIA") + "  " + cVigencia , false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("I", iReng, !vDinRep.getString("CCONTRAPRESTACION").equalsIgnoreCase("null")?vDinRep.getString("CCONTRAPRESTACION"):"----", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("J", iReng, vDinRep.getString("CNUMCONVOCATORIA") != null && !vDinRep.getString("CNUMCONVOCATORIA").equalsIgnoreCase("")? "Por convocatoria número " +vDinRep.getString("CNUMCONVOCATORIA"): "Directa", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("K", iReng, !vDinRep.getString("COBSERVACION").equalsIgnoreCase("null")?vDinRep.getString("COBSERVACION"):"", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

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
					iContador++;
				}

			}

		}

		rep.comDespliegaAlineado("C", iReng+2, "Total registrados ", false, rep.getAT_HIZQUIERDA(), rep.getAT_VSUPERIOR());
		rep.comDespliegaAlineado("D", iReng+2, "" + iContador, false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

		return rep.getSbDatos();
	}

	public StringBuffer generaPadronConcesiones(String cFiltro){
		TExcel rep = new TExcel();
		String aFiltro[] = cFiltro.split(",");
		String[] aEntidades = aFiltro[0].replace('|',',').split(",");
		int iCvePuerto = Integer.parseInt(aFiltro[1],10);
		int iReng = 9;
		String cSuperficie="";
		Vector vData = new Vector();
		TVDinRep vDinRep = new TVDinRep(), vDinPto = new TVDinRep(), vDinRepTemp = new TVDinRep();

		rep.iniciaReporte();
		for(int i=0; i<aEntidades.length;i++){
			StringBuffer sbPto = new StringBuffer();
			sbPto.append("SELECT ")
			.append("iCvePuerto ")
			.append("FROM GRLPUERTO ")
			.append("where icvepais = 1 ")
			.append("and ICVEENTIDADFED =  " + aEntidades[i]);
			if(iCvePuerto != -1)
				sbPto.append(" and iCvePuerto = " + iCvePuerto);

			sbPto.append(" order by CDSCPUERTO ");

			Vector vDataPtos = new Vector();

			try {
				vDataPtos = super.FindByGeneric("", sbPto.toString(), dataSourceName);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			for(int j = 0; j<vDataPtos.size(); j++){
				vDinPto = (TVDinRep) vDataPtos.get(j);
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT ");
				sb.append("PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CTITULAR, ");
				sb.append("TIT.COBJETOTITULO AS COBJETO, ");
				sb.append("USO.CDSCUSOTITULO AS CUSO, ");
				sb.append("UBI.CCALLETITULO || ' ' || UBI.CKM || ' ' || UBI.CCOLONIATITULO AS CUBICACION, ");
				sb.append("TIT.CZONAFEDAFECTADATERRESTRE as CZONAFEDTERR, ");
				sb.append("TIT.CZONAOPNOEXCTERRESTRE as CZONANOESTERR, ");
				sb.append("TIT.CZONAFEDTOTALTERRESTRE AS CZONATOTTERR, ");
				sb.append("TIT.CZONAFEDAFECTADAAGUA AS CZONAFEDAGUA, ");
				sb.append("TIT.CZONAOPNOEXCAGUA AS CZONANOEXAGUA, ");
				sb.append("TIT.CZONAFEDTOTALAGUA AS CZONATOTAGUA, ");
				sb.append("TIT.DTINIVIGENCIATITULO AS DTINIVIGENCIA, ");
				sb.append("TIT.DTVIGENCIATITULO as DTFINVIGENCIA, ");
				sb.append("TIT.CNUMTITULO AS CNUMTITULO, ");
				sb.append("PTO.CDSCPUERTO || ', '  || EFED.CABREVIATURA AS CNOMPTO, ");
				sb.append("PTO.iCvePuerto as iCvePuerto, ");
				sb.append("(YEAR(TIT.DTVIGENCIATITULO) - YEAR(TIT.DTINIVIGENCIATITULO))  AS CVIGENCIA ");
				sb.append("FROM CPATITULO TIT ");
				sb.append("JOIN CPAUSOTITULO USO ON TIT.ICVEUSOTITULO = USO.ICVEUSOTITULO ");
				sb.append("JOIN CPATITULAR TITULAR ON TIT.ICVETITULO = TITULAR.ICVETITULO ");
				sb.append("JOIN GRLPERSONA PER ON TITULAR.ICVETITULAR = PER.ICVEPERSONA ");
				sb.append("JOIN CPATITULOUBICACION UBI ON TIT.ICVETITULO = UBI.ICVETITULO ");
				sb.append("JOIN GRLPUERTO PTO ON UBI.ICVEPUERTO = PTO.ICVEPUERTO ");
				sb.append("JOIN GRLENTIDADFED EFED ON PTO.ICVEPAIS = EFED.ICVEPAIS ");
				sb.append("AND PTO.ICVEENTIDADFED = EFED.ICVEENTIDADFED ");
				sb.append("WHERE TIT.lAPI = 1 and TIT.iCveTipoTitulo = 1 and EFED.ICVEENTIDADFED =  " + aEntidades[i]);
				sb.append(" AND PTO.ICVEPUERTO = " +vDinPto.getInt("iCvePuerto") );

				try {
					vData = super.FindByGeneric("", sb.toString(), dataSourceName);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				for(int y=0; y<vData.size(); y++){
					vDinRep = (TVDinRep) vData.get(y);
					//Para ver cuando cambie de un puerto a otro.
					if(y>0)
						vDinRepTemp = (TVDinRep) vData.get(y-1);
					else
						vDinRepTemp = (TVDinRep) vData.get(y);

					//Para poner los títulos de puerto, Entidad
					if(vDinRep.getInt("iCvePuerto") != vDinRepTemp.getInt("iCvePuerto")){
						rep.comDespliegaAlineado("B", iReng, vDinRep.getString("CNOMPTO"),true, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
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
						rep.comBordeRededor("L", iReng, "L", iReng, 1, 1);
						iReng++;
					}else if(y==0){
						rep.comDespliegaAlineado("B", iReng, vDinRep.getString("CNOMPTO"),true, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
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
						rep.comBordeRededor("L", iReng, "L", iReng, 1, 1);
						iReng++;
					}
					cSuperficie = (vDinRep.getString("CZONATOTTERR") != null && !vDinRep.getString("CZONATOTTERR").equalsIgnoreCase(""))?"ZFMT"+vDinRep.getString("CZONATOTTERR"):"";

					if(!cSuperficie.equalsIgnoreCase(""))
						cSuperficie += (vDinRep.getString("CZONATOTAGUA") != null && !vDinRep.getString("CZONATOTAGUA").equalsIgnoreCase(""))?", ZFMA"+vDinRep.getString("CZONATOTAGUA"):"";

					rep.comDespliegaAlineado("A", iReng, "" + (y+1), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("B", iReng, vDinRep.getString("CTITULAR"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("C", iReng, vDinRep.getString("COBJETO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("D", iReng, vDinRep.getString("CUSO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("E", iReng, vDinRep.getString("CUBICACION"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("F", iReng, cSuperficie, false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("G", iReng, vDinRep.getDate("DTINIVIGENCIA") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTINIVIGENCIA"),"."):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("H", iReng, (vDinRep.getString("CVIGENCIA")!= null && !vDinRep.getString("CVIGENCIA").equalsIgnoreCase("null"))?vDinRep.getString("CVIGENCIA"):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("I", iReng, vDinRep.getDate("DTINIVIGENCIA") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTINIVIGENCIA"),"."):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("J", iReng, vDinRep.getDate("DTFINVIGENCIA") != null?fecha.getFechaDDMMMYYYY(vDinRep.getDate("DTFINVIGENCIA"),"."):"---", false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
					rep.comDespliegaAlineado("K", iReng, vDinRep.getString("CNUMTITULO"), false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());

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
					rep.comBordeRededor("L", iReng, "L", iReng, 1, 1);
					iReng++;
				}
			}
		}

		return rep.getSbDatos();
	}

	/**
	 * Método que regresa en un vector de TVDInRep de modificaciones a un título de concesión
	 * @param iCveTitulo
	 * @return
	 * Autor: iCaballero
	 */
	public Vector regresaTitulosConcesion(int iCveTitulo){
		TVDinRep vData = new TVDinRep();
		Vector vRegresa = new Vector();

		vData = this.consultaTitulosConAnt(iCveTitulo);
		vRegresa.add(vData);
		while(vData.getInt("ICVETITULOANTERIOR") > 0){
			vData = this.consultaTitulosConAnt(vData.getInt("ICVETITULOANTERIOR"));
			vRegresa.add(vData);
		}

		return vRegresa;
	}

	public TVDinRep consultaTitulosConAnt(int iCveTitulo){
		TVDinRep vRegresa = new TVDinRep();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CTITULAR, ");
		sb.append("TIT.COBJETOTITULO AS COBJETO, ");
		sb.append("USO.CDSCUSOTITULO AS CUSO, ");
		sb.append("TIT.DTINIVIGENCIATITULO AS DTINIVIGENCIA, ");
		sb.append("TIT.DTVIGENCIATITULO as DTFINVIGENCIA, ");
		sb.append("TIT.CNUMTITULO AS CNUMTITULO, "); //5
		sb.append("PTO.CDSCPUERTO || ', '  || EFED.CABREVIATURA AS CNOMPTO, ");
		sb.append("(YEAR(TIT.DTVIGENCIATITULO) - YEAR(TIT.DTINIVIGENCIATITULO))  AS CVIGENCIA, ");
		sb.append("TIT.ICVETITULOANTERIOR AS ICVETITULOANTERIOR, ");
		sb.append("PA.CDSCTIPOPARTACC AS CDSCPARTACC, ");
		sb.append("TIT.DTPUBLICACIONDOF AS DTPUBLICACIONDOF, ");//10
		sb.append("omp.DTINIPROPUESTO as dtiniciooperaciones, ");
		sb.append("EC.DTESCRITURAMOV AS DTCONSTITUCION ");
		sb.append("FROM CPATITULO TIT ");
		sb.append("left JOIN CPAUSOTITULO USO ON TIT.ICVEUSOTITULO = USO.ICVEUSOTITULO ");
		sb.append("left JOIN CPATITULAR TITULAR ON TIT.ICVETITULO = TITULAR.ICVETITULO ");
		sb.append("left JOIN CPATIPOPARTACC PA ON TIT.ICVETIPOPARTACC = PA.ICVETIPOPARTACC ");
		sb.append("LEFT JOIN OMPPRODOBRASERV OMP ON TIT.ICVETITULO = OMP.ICVETITULO ");
		sb.append("AND OMP.LOPERACION = 1 ");
		sb.append("LEFT JOIN CPAESCCONSMOV EC ON TIT.ICVETITULO = EC.ICVETITULO ");
		sb.append("AND EC.ICVETIPOMOVESCRITURA = 1 ");
		sb.append("left JOIN GRLPERSONA PER ON TITULAR.ICVETITULAR = PER.ICVEPERSONA ");
		sb.append("left JOIN CPATITULOUBICACION UBI ON TIT.ICVETITULO = UBI.ICVETITULO ");
		sb.append("left JOIN GRLPUERTO PTO ON UBI.ICVEPUERTO = PTO.ICVEPUERTO ");
		sb.append("left JOIN GRLENTIDADFED EFED ON PTO.ICVEPAIS = EFED.ICVEPAIS ");
		sb.append("and PTO.ICVEENTIDADFED = EFED.ICVEENTIDADFED ");
		sb.append("where TIT.ICVETITULO =  " + iCveTitulo);
		Vector vData = new Vector();
		try {
			vData = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(vData.size() > 0)
			vRegresa = (TVDinRep) vData.get(0);

		return vRegresa;
	}

}

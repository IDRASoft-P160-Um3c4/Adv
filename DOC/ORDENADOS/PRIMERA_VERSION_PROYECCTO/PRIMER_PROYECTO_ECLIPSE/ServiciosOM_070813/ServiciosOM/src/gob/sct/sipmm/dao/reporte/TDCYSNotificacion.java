package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;

import gob.sct.sipmm.dao.TDCYSFolioProc;
import gob.sct.sipmm.dao.TDCYSNotOficio;
import gob.sct.sipmm.dao.TDCYSSegProcedimiento;
import gob.sct.sipmm.dao.TDGRLDiaNoHabil;
import gob.sct.sipmm.dao.reporte.*;
import java.util.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.excepciones.DAOException;
import com.micper.excepciones.TFormatException;
import com.micper.ingsw.*;

public class TDCYSNotificacion  extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	private TFechas Fecha = new TFechas();

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


	public TDCYSNotificacion(){

	}

	public StringBuffer genNotificacion(String cFiltro) throws Exception{
		TFechas Fecha = new TFechas("44");
		String [] cParametros = cFiltro.split(",");
		String iCveTitulo = cParametros[0];
		String iEjercicio = cParametros[1];
		String iMovProcedimiento = cParametros[2];
		String iMovFolioProc = cParametros[3];
		String iMovCitaNotificacion = cParametros[4];
		String cNombre = cParametros[5];
		String iCveOficina = cParametros[6];
		int iCveTitular=0;
		int iTipoOficio = Integer.parseInt(cParametros[7]);
		String cAsunto = "";
		TWord rep = new TWord();
		rep.iniciaReporte();


		String cQryNotificacion =
			"SELECT " +
			"P.iCvePersona, p.ITIPOPERSONA, " +
			"P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CNOMBRE, " +
			"REP.CNOMRAZONSOCIAL||' '|| REP.CAPPATERNO||' '|| REP.CAPMATERNO AS CREPRESENTANTE, " +
			"D.CCALLE ||' '|| D.CNUMEXTERIOR AS CDIRECCION, D.CNUMINTERIOR, D.CCOLONIA, EF.CNOMBRE AS CENTIDAD, MUN.CNOMBRE AS CMUNICIPIO, " +
			"CN.DTREGISTRO, CN.TSCITATORIO, CN.CRECIBECITATORIO, CN.CTESTIGONOTCITATORIO,CN.CTESTIGOACTCITATORIO, CN.TSFECHANOTIFICACION, " +
			"CN.CNOTIFICADOR, CN.CIDENTIFICACIONNOFICADOR, CN.CNOIDENTIFICACIONNOTIFICADOR, " +
			"CN.CTESTIGONOTIFICADOR, CN.CIDENTIFICACIONTESNOT, CN.CNOIDENTIFICACIONTESNOT, " +
			"CN.CTESTIGOACTUANTE, CN.CIDENTIFICACIONTESACT, CN.CNOIDENTIFICACIONTESACT, " +
			"CN.COBSNOTIFICACION, EF1.CNOMBRE AS CENFEDNOTIF, MUN1.CNOMBRE AS CMUNNOTIF, CN.ICVEOFICINA " +
			"FROM CYSCitaNotificacion CN " +
			"LEFT JOIN GRLPERSONA P ON CN.ICVEPERSONA = P.ICVEPERSONA " +
			"LEFT JOIN GRLREPLEGAL RL ON P.ICVEPERSONA = RL.ICVEEMPRESA AND RL.LPRINCIPAL = 1 " +
			"LEFT JOIN GRLPERSONA REP ON RL.ICVEPERSONA = REP.ICVEPERSONA " +
			"LEFT JOIN GRLDOMICILIO D ON CN.ICVEPERSONA = D.ICVEPERSONA AND CN.ICVEDOMICILIO = D.ICVEDOMICILIO " +
			"LEFT JOIN GRLENTIDADFED EF ON D.ICVEENTIDADFED = EF.ICVEENTIDADFED AND D.ICVEPAIS = EF.ICVEPAIS " +
			"LEFT JOIN GRLMUNICIPIO MUN ON D.ICVEMUNICIPIO = MUN.ICVEMUNICIPIO AND D.ICVEENTIDADFED = MUN.ICVEENTIDADFED AND D.ICVEPAIS = MUN.ICVEPAIS " +
			"lEFT JOIN GRLOFICINA O ON CN.ICVEOFICINA = O.ICVEOFICINA " +
			"LEFT JOIN GRLENTIDADFED EF1 ON O.ICVEENTIDADFED = EF1.ICVEENTIDADFED AND O.ICVEPAIS = EF1.ICVEPAIS " +
			"LEFT JOIN GRLMUNICIPIO MUN1 ON O.ICVEPAIS=MUN1.ICVEPAIS AND O.ICVEENTIDADFED = MUN1.ICVEENTIDADFED AND O.ICVEMUNICIPIO = MUN1.ICVEMUNICIPIO " +
			"where cn.IMOVCITANOTIFICACION = " + iMovCitaNotificacion;
		String cQryTitulo =
			"SELECT T.ICVETITULO,TT.CDSCTIPOTITULO,T.CNUMTITULO, " +
			"T.DTVIGENCIATITULO,T.DTINIVIGENCIATITULO, " +
			"T.COBJETOTITULO,UT.CDSCUSOTITULO, " +
			"P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS TITULAR, TIT.IORDEN " +
			"FROM CPATITULO T " +
			"join CPATIPOTITULO TT ON T.ICVETIPOTITULO = TT.ICVETIPOTITULO " +
			"JOIN CPAUSOTITULO UT ON T.ICVEUSOTITULO = UT.ICVEUSOTITULO " +
			"JOIN CPATITULAR TIT ON T.ICVETITULO = TIT.ICVETITULO " +
			"JOIN GRLPERSONA P ON TIT.ICVETITULAR = P.ICVEPERSONA " +
			"WHERE T.ICVETITULO = "+iCveTitulo;
		String cQryFolio =
			" select " +
			" GRLFolio.dtAsignacion as dtOficio, " +
			" GRLFolio.CDIGITOSFOLIO  as cDigitosFolio, " +
			" GRLFOLIO.ICONSECUTIVO as iConsecutivo, " +
			" GRLFOLIO.IEJERCICIO as iEjercicioFolio, " +
			" GRLFOLIO.ICVEOFICINA, EF.CNOMBRE AS CENTFED, M.CNOMBRE AS CMUNICIPIO, "+
			" DEO.CTITULAR, DEO.CPUESTOTITULAR "+
			" from CYSSegProcedimiento " +
			" left join CYSFolioProc on CYSFolioProc.iEjercicio = CYSSegProcedimiento.iEjercicio " +
			" and CYSFolioProc.iMovProcedimiento = CYSSegProcedimiento.iMovProcedimiento " +
			" and CYSFolioProc.LESRESOLUCION = " + (iTipoOficio == 1 ? 1 : 0) +
			" and CYSFolioProc.LESPRORROGA = " + (iTipoOficio == 2 ? 1 : 0) +
			" and CYSFolioProc.LESALEGATO = " + (iTipoOficio == 3 ? 1 : 0) +
			" and CYSFolioProc.LESSINEFECTO = " + (iTipoOficio == 4 ? 1 : 0) +
			" and CYSFolioProc.LESNOTIFICACION = " + (iTipoOficio == 5 ? 1 : 0) +
			" and CYSFolioProc.LESRECORDATORIO = " + (iTipoOficio == 6 ? 1 : 0) +
			" left join GRLFolio on GRLFolio.iEjercicio = CYSFolioProc.iEjercicioFol " +
			" and GRLFolio.iCveOficina = CYSFolioProc.iCveOficina " +
			" and GRLFolio.iCveDepartamento = CYSFolioProc.iCveDepartamento " +
			" and GRLFolio.cDigitosFolio = CYSFolioProc.cDigitosFolio " +
			" and GRLFolio.iConsecutivo = CYSFolioProc.iConsecutivo " +
			"  LEFT JOIN GRLOFICINA O ON GRLFOLIO.ICVEOFICINA = O.ICVEOFICINA " +
			"  LEFT JOIN GRLENTIDADFED EF ON O.ICVEPAIS = EF.ICVEPAIS AND O.ICVEENTIDADFED = EF.ICVEENTIDADFED " +
			"  LEFT JOIN GRLMUNICIPIO M ON O.ICVEPAIS = M.ICVEPAIS AND O.ICVEENTIDADFED = M.ICVEENTIDADFED AND O.ICVEMUNICIPIO = M.ICVEMUNICIPIO "+
			" LEFT JOIN GRLDEPTOXOFIC DEO ON GRLFOLIO.ICVEDEPARTAMENTO = DEO.ICVEDEPARTAMENTO AND GRLFOLIO.ICVEOFICINA = DEO.ICVEOFICINA "+
			" where CYSSegProcedimiento.iEjercicio = " + iEjercicio +
			" and CYSSegProcedimiento.iMovProcedimiento =  " + iMovProcedimiento +
			" order by CYSFolioProc.iMovFolioProc ";


		Vector vcNotificacion = findByCustom("",cQryNotificacion);
		Vector vcTitulo = findByCustom("",cQryTitulo);
		Vector vcFolio = findByCustom("",cQryFolio);

		if(vcNotificacion.size()>0){
			TVDinRep vNotificacion = (TVDinRep) vcNotificacion.get(0);
			if(!vNotificacion.getString("TSFECHANOTIFICACION").equals("null")){
				rep.comRemplaza("[cHorasNotificacion]",Fecha.getHora12H(vNotificacion.getTimeStamp("TSFECHANOTIFICACION"))+":"+Fecha.getMinuto(vNotificacion.getTimeStamp("TSFECHANOTIFICACION")));
				rep.comRemplaza("[cFechaNotificacion]",Fecha.getDateSPNWithDay(Fecha.getDateSQL(vNotificacion.getTimeStamp("TSFECHANOTIFICACION"))));
			}
			else{
				rep.comRemplaza("[cHorasNotificacion]","____:____");
				rep.comRemplaza("[cFechaNotificacion]","____/_______/_____");
			}
			if(!vNotificacion.getString("CENFEDNOTIF").equals("null")) rep.comRemplaza("[cEntidadFed]",vNotificacion.getString("CENFEDNOTIF"));else rep.comRemplaza("[cEntidadFed]","________________________________");
			if(!vNotificacion.getString("CMUNNOTIF").equals("null")) rep.comRemplaza("[cMunicipio]",vNotificacion.getString("CMUNNOTIF"));else rep.comRemplaza("[cMunicipio]","________________________________");
			if(!vNotificacion.getString("CNOTIFICADOR").equals("null")) rep.comRemplaza("[cNomNotificador]",vNotificacion.getString("CNOTIFICADOR"));else rep.comRemplaza("[cNomNotificador]","NOMBRE Y FIRMA");
			if(!vNotificacion.getString("CTESTIGONOTIFICADOR").equals("null")) rep.comRemplaza("[cTestigoNotificador]",vNotificacion.getString("CTESTIGONOTIFICADOR"));else rep.comRemplaza("[cTestigoNotificador]","NOMBRE Y FIRMA");
			if(!vNotificacion.getString("CIDENTIFICACIONTESNOT").equals("null")) rep.comRemplaza("[cIdentificaConNotificador]",vNotificacion.getString("CIDENTIFICACIONTESNOT")+" # "+vNotificacion.getString("CNOIDENTIFICACIONTESNOT"));else rep.comRemplaza("[cIdentificaConNotificador]","____________________________________");//
			if(!vNotificacion.getString("CRECIBECITATORIO").equals("null")) rep.comRemplaza("[cRecibeCitatorio]",vNotificacion.getString("CRECIBECITATORIO"));else rep.comRemplaza("[cRecibeCitatorio]","NOMBRE Y FIRMA");
			if(!vNotificacion.getString("CTESTIGONOTCITATORIO").equals("null")) rep.comRemplaza("[cTestigoNotifCitatorio]",vNotificacion.getString("CTESTIGONOTCITATORIO"));else rep.comRemplaza("[cTestigoNotifCitatorio]","NOMBRE Y FIRMA");
			if(!vNotificacion.getString("CTESTIGOACTCITATORIO").equals("null")) rep.comRemplaza("[cTestigoActCitatorio]",vNotificacion.getString("CTESTIGOACTCITATORIO"));else rep.comRemplaza("[cTestigoActCitatorio]","NOMBRE Y FIRMA");
			if(!vNotificacion.getString("CTESTIGOACTUANTE").equals("null")) rep.comRemplaza("[cTestigoActuante]",vNotificacion.getString("CTESTIGOACTUANTE"));else rep.comRemplaza("[cTestigoActuante]","NOMBRE Y FIRMA");
			if(!vNotificacion.getString("CIDENTIFICACIONTESACT").equals("null")) rep.comRemplaza("[cIdentificaConActuante]",vNotificacion.getString("CIDENTIFICACIONTESACT")+" # "+vNotificacion.getString("CNOIDENTIFICACIONTESACT"));else rep.comRemplaza("[cIdentificaConActuante]","____________________________________");//


			if(!vNotificacion.getString("TSCITATORIO").equals("null")){
				rep.comRemplaza("[cHorasCitatorio]",Fecha.getHora12H(vNotificacion.getTimeStamp("TSCITATORIO"))+":"+Fecha.getMinuto(vNotificacion.getTimeStamp("TSCITATORIO")));
				rep.comRemplaza("[cFechaCitatorio]",Fecha.getDateSPNWithDay(Fecha.getDateSQL(vNotificacion.getTimeStamp("TSCITATORIO"))));

			}else{
				rep.comRemplaza("[cHorasCitatorio]","____:____");
				rep.comRemplaza("[cFechaCitatorio]","____/_______/_____");
			}

			if(!vNotificacion.getString("CNOMBRE").equals("null")){
				String cTitular = "";
				if(!(vNotificacion.getInt("ITIPOPERSONA")==1)){
					cTitular = vNotificacion.getString("CREPRESENTANTE") + " Representante legal de "+vNotificacion.getString("CNOMBRE");
				}
				else cTitular = vNotificacion.getString("CNOMBRE");
				rep.comRemplaza("[cTitular]",cTitular);
				iCveTitular = vNotificacion.getInt("iCvePersona");
			}
			else rep.comRemplaza("[cTitular]","_____________________________________");
		}
		if(vcTitulo.size()>0){
			TVDinRep vTitulo = (TVDinRep) vcTitulo.get(0);
			if(!vTitulo.getString("CNUMTITULO").equals("null"))rep.comRemplaza("[cNumTitulo]", vTitulo.getString("CNUMTITULO"));else rep.comRemplaza("[cNumTitulo]", "___________________");
			if(!vTitulo.getString("CDSCTIPOTITULO").equals("null"))rep.comRemplaza("[cTipoTitulo]", vTitulo.getString("CDSCTIPOTITULO"));else rep.comRemplaza("[cTipoTitulo]", "___________________");
			if(!vTitulo.getString("DTINIVIGENCIATITULO").equals("null"))rep.comRemplaza("[cFechaIniTitulo]",Fecha.getDateSPN(vTitulo.getDate("DTINIVIGENCIATITULO")));else rep.comRemplaza("[cFechaIniTitulo]", "___________________");

		}
		if(vcFolio.size()>0){
			TVDinRep vFolio = (TVDinRep) vcFolio.get(0);
			if(!vFolio.getString("dtOficio").equals("null") && !vFolio.getString("dtOficio").equals("")){
				rep.comRemplaza("[cFechaOficioNotificado]",Fecha.getDateSPNWithDay(vFolio.getDate("dtOficio")));
			} else rep.comRemplaza("[cFechaOficioNotificado]","____/_______/_____");
			if(!vFolio.getString("cDigitosFolio").equals("null") &&
					!vFolio.getString("cDigitosFolio").equals("null") &&
					!vFolio.getString("cDigitosFolio").equals("null"))
				rep.comRemplaza("[cNumOficioNotificado]",vFolio.getString("cDigitosFolio")+"."+vFolio.getString("iConsecutivo")+"/"+vFolio.getString("iEjercicioFolio"));
			else rep.comRemplaza("[cNumOficioNotificado]","________________________________________");

			if(!vFolio.getString("CTITULAR").equals("null")) rep.comRemplaza("[cNomRepOficina]",vFolio.getString("CTITULAR"));else rep.comRemplaza("[cNomRepOficina]","________________________________");
			if(!vFolio.getString("CPUESTOTITULAR").equals("null")) rep.comRemplaza("[cPuestoRepOficina]",vFolio.getString("CPUESTOTITULAR"));else rep.comRemplaza("[cPuestoRepOficina]","____________________________");


		}

		StringBuffer  sbRetorno = rep.getEtiquetas(true);

		//   this.setDatosFolio(cNumFolio);

		return sbRetorno;
	}


	public Vector genCertificado(String cFiltro,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vRegs = new Vector();
		Vector vTitulo = new Vector();
		TWord rep = new TWord();
		TDCYSSegProcedimiento dCYSSegProcedimiento = new TDCYSSegProcedimiento();
		String[] aFiltros = cFiltro.split(",");
		String cCveTitulo = aFiltros[0];
		int iEjercicio = Integer.parseInt(aFiltros[1]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
		int iTipoOficio = Integer.parseInt(aFiltros[7]), iCveProcedimiento = 0;
		String cNumOficioReq="";
		java.sql.Date dtFechaNotif = null, dtFechaFinal =null, dtFechaInicial = null;
		TDGRLDiaNoHabil dFechaNoHabil = new TDGRLDiaNoHabil();
		TLetterNumberFormat dNumeros = new TLetterNumberFormat();

		// Query de Consulta del Reporte.
		try{
			vRegs = dCYSSegProcedimiento.findByOficioNotRec(iEjercicio,iMovProcedimiento,iTipoOficio);

		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("TIT.CNUMTITULO AS CNUMTITULO, ");
		sb.append("CASE CHAR(TIT.ICVETIPOTITULO) ");
		sb.append("WHEN '1' THEN 'CONCESIONARIO' ");
		sb.append("WHEN '2' THEN 'PERMISIONARIO' ");
		sb.append("WHEN '3' THEN 'AUTORIZADO' ");
		sb.append("END CPSEUDONIMOTIPOTITULO, ");
		sb.append("TIT.DTINIVIGENCIATITULO AS DTINIVIGENCIATITULO, ");
		sb.append("TIT.DTVIGENCIATITULO AS DTVIGENCIATITULO, ");
		sb.append("(YEAR(TIT.DTVIGENCIATITULO) - YEAR(TIT.DTINIVIGENCIATITULO)) AS CANIOSVIGENCIATIT, ");
		sb.append("TIT.COBJETOTITULO AS COBJETOTITULO, ");
		sb.append("PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CTITULAR, ");
		sb.append("CASE CHAR(TITUB.LDENTRORECINTOPORT) ");
		sb.append("WHEN '1' THEN PTO.CDSCPUERTO || ', ' || EFED.CNOMBRE ");
		sb.append("WHEN '0' THEN TITUB.CCALLETITULO || ' Col. ' || TITUB.CCOLONIATITULO || ' ' || MUN.CNOMBRE || ', ' || EFEDF.CNOMBRE ");
		sb.append("ELSE ' ' ");
		sb.append("END CUBICACION ");
		sb.append("FROM CPATITULO TIT ");
		sb.append("left JOIN CPATITULAR TITULAR ON TIT.ICVETITULO = TITULAR.ICVETITULO ");
		sb.append("left JOIN GRLPERSONA PER ON TITULAR.ICVETITULAR = PER.ICVEPERSONA ");
		sb.append("LEFT JOIN CPATITULOUBICACION TITUB ON TIT.ICVETITULO = TITUB.ICVETITULO ");
		sb.append("left JOIN GRLPUERTO PTO ON TITUB.ICVEPUERTO = PTO.ICVEPUERTO ");
		sb.append("left JOIN GRLENTIDADFED EFED ON PTO.ICVEPAIS = EFED.ICVEPAIS ");
		sb.append("AND PTO.ICVEENTIDADFED = EFED.ICVEENTIDADFED ");
		sb.append("LEFT JOIN GRLENTIDADFED EFEDF ON TITUB.ICVEPAIS = EFEDF.ICVEPAIS ");
		sb.append("AND TITUB.ICVEENTIDADFED = EFEDF.ICVEENTIDADFED ");
		sb.append("LEFT JOIN GRLMUNICIPIO MUN ON TITUB.ICVEPAIS = MUN.ICVEPAIS ");
		sb.append("AND TITUB.ICVEENTIDADFED = MUN.ICVEENTIDADFED ");
		sb.append("AND TITUB.ICVEMUNICIPIO = MUN.ICVEMUNICIPIO ");
		sb.append("WHERE TIT.ICVETITULO =  " + cCveTitulo);

		try {
			vTitulo = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		String cFechaAsignacion = "";
		if(vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);
			if(vDatos.getDate("dtAsignacion") != null)
				cFechaAsignacion  = Fecha.getFechaDDMMMMMYYYY(vDatos.getDate("dtAsignacion"), " de ");

			if(vDatos.getTimeStamp("tsFechaRealNotif") != null && !vDatos.getTimeStamp("tsFechaRealNotif").equals(""))
				dtFechaNotif = Fecha.getDateSQL(vDatos.getTimeStamp("tsFechaRealNotif"));

			cNumOficioReq = vDatos.getString("CDIGITOSFOLIO") + "." + vDatos.getString("ICONSECUTIVO") + "/" + vDatos.getString("IEJERCICIOFOL");
			iCveProcedimiento = vDatos.getInt("iCveProcedimiento");

		}

		String cNumTitulo = "";
		String cPseudoTitulo = "";
		String cVigenciaTitulo = "";
		String cObjetoTitulo = "";
		String cTitular = "";
		String cUbicacion = "";
		if(vTitulo.size() > 0){
			TVDinRep vDatosTitulo = (TVDinRep) vTitulo.get(0);
			cNumTitulo  = vDatosTitulo.getString("CNUMTITULO");
			cPseudoTitulo = vDatosTitulo.getString("CPSEUDONIMOTIPOTITULO");
			cVigenciaTitulo = vDatosTitulo.getString("CANIOSVIGENCIATIT");
			cObjetoTitulo = vDatosTitulo.getString("COBJETOTITULO");
			cTitular = vDatosTitulo.getString("CTITULAR");
			cUbicacion = vDatosTitulo.getString("CUBICACION");
		}

		rep.comRemplaza("[cNumTitulo]", cNumTitulo);
		rep.comRemplaza("[cPseudonimoTipoTitulo]", cPseudoTitulo);
		rep.comRemplaza("[cVigenciaTitulo]", cVigenciaTitulo);
		rep.comRemplaza("[cObjetoTitulo]", cObjetoTitulo);
		rep.comRemplaza("[cTitular]", cTitular);
		rep.comRemplaza("[cPuertoTitulo]", cUbicacion);
		rep.comRemplaza("[cNumOficioNotificado]", cNumOficioReq);
		rep.comRemplaza("[cFechaOficioNotificado]", cFechaAsignacion);
		rep.comRemplaza("[cFechaEmision]",Fecha.getFechaDDMMMMMYYYY(Fecha.TodaySQL()," de "));
		if(dtFechaNotif != null)
			rep.comRemplaza("[cFechaNotificacionOficio]",Fecha.getFechaDDMMMMMYYYY(dtFechaNotif, " de "));
		else
			rep.comRemplaza("[cFechaNotificacionOficio]","_________________");

		int iNumDias  =0;

		if(iTipoOficio == 2)
			iNumDias = 7;
		else if(iTipoOficio == 3)
			iNumDias = 10;
		else if(iTipoOficio != 1 && iTipoOficio != 2 && iTipoOficio != 3 && iTipoOficio != 4 && iTipoOficio != 5 && iTipoOficio != 6){
			if(iCveProcedimiento == 1)
				iNumDias = 10;
			else if((iCveProcedimiento == 2))
				iNumDias = 15;
		}
		if(dtFechaNotif != null){
			dtFechaFinal = dFechaNoHabil.getFechaFinal(dtFechaNotif, iNumDias, false);
			dtFechaInicial = dFechaNoHabil.getFechaFinal(dtFechaNotif, 1, false);
			rep.comRemplaza("[cFechaFinPeriodo]",Fecha.getFechaDDMMMMMYYYY(dtFechaFinal, " de "));
			rep.comRemplaza("[cFechaIniPeriodo]",Fecha.getFechaDDMMMMMYYYY(dtFechaInicial, " de "));
		}else{
			rep.comRemplaza("[cFechaFinPeriodo]","_____________");
			rep.comRemplaza("[cFechaIniPeriodo]","_____________");
		}


		try {
			rep.comRemplaza("[iNumDias]",dNumeros.getCantidad(iNumDias));
		} catch (TFormatException e) {
			e.printStackTrace();
		}

		TDObtenDatos dObten3 = new TDObtenDatos();
		Vector vRutaRemitente = new Vector();
		vRutaRemitente = dObten3.getOrganigrama(Integer.parseInt(cCveOficinaOrigen), Integer.parseInt(cCveDeptoOrigen), vRutaRemitente);
		rep.comEligeTabla("cRutaRemitente");
		for(int i=0; i<vRutaRemitente.size(); i++)
			rep.comDespliegaCelda((String)vRutaRemitente.get(i));

		return rep.getVectorDatos(true) ;

	}
}

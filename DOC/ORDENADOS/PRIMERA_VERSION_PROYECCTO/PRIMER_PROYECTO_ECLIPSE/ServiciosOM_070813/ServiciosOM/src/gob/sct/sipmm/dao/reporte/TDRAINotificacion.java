package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import gob.sct.sipmm.dao.TDGRLDiaNoHabil;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import com.micper.excepciones.DAOException;
import com.micper.excepciones.TFormatException;


public class TDRAINotificacion  extends DAOBase{
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


	public TDRAINotificacion(){

	}

	public StringBuffer genNotificacion(String cFiltro) throws Exception{
		TFechas Fecha = new TFechas("44");
		String [] cParametros = cFiltro.split(",");
		String iCveRegularizacion = cParametros[0];

		String iMovCitaNotificacion = cParametros[2];

		int iTipoOficio = Integer.parseInt(cParametros[5]);
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

		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("FOL.dtAsignacion as dtOficio, ");
		sb.append("FOL.CDIGITOSFOLIO as cDigitosFolio, ");
		sb.append("FOL.ICONSECUTIVO as iConsecutivo, ");
		sb.append("FOL.IEJERCICIO as iEjercicioFolio, ");
		sb.append("FOL.ICVEOFICINA, ");
		sb.append("EF.CNOMBRE AS CENTFED, ");
		sb.append("M.CNOMBRE AS CMUNICIPIO, ");
		sb.append("DEO.CTITULAR, ");
		sb.append("DEO.CPUESTOTITULAR ");
		sb.append("from RAIREGULARIZACION RR ");
		sb.append("LEFT JOIN RAIFOLIO RF ON RR.ICVEREGULARIZACION = RF.ICVEREGULARIZACION ");
		sb.append("AND RF.ICVETIPODOCTO =  " + iTipoOficio);
		sb.append(" left join GRLFolio FOL on FOL.iEjercicio = RF.IEJERCICIO ");
		sb.append("and FOL.iCveOficina = RF.iCveOficina ");
		sb.append("and FOL.iCveDepartamento = RF.iCveDepartamento ");
		sb.append("and FOL.cDigitosFolio = RF.cDigitosFolio ");
		sb.append("and FOL.iConsecutivo = RF.iConsecutivoFolio ");
		sb.append("LEFT JOIN GRLOFICINA O ON FOL.ICVEOFICINA = O.ICVEOFICINA ");
		sb.append("LEFT JOIN GRLENTIDADFED EF ON O.ICVEPAIS = EF.ICVEPAIS ");
		sb.append("AND O.ICVEENTIDADFED = EF.ICVEENTIDADFED ");
		sb.append("LEFT JOIN GRLMUNICIPIO M ON O.ICVEPAIS = M.ICVEPAIS ");
		sb.append("AND O.ICVEENTIDADFED = M.ICVEENTIDADFED ");
		sb.append("AND O.ICVEMUNICIPIO = M.ICVEMUNICIPIO ");
		sb.append("LEFT JOIN GRLDEPTOXOFIC DEO ON FOL.ICVEDEPARTAMENTO = DEO.ICVEDEPARTAMENTO ");
		sb.append("AND FOL.ICVEOFICINA = DEO.ICVEOFICINA ");
		sb.append("where RR.ICVEREGULARIZACION =  " + iCveRegularizacion);
		sb.append(" order by RF.ICONSECUTIVO ");


		Vector vcNotificacion = findByCustom("",cQryNotificacion);

		Vector vcFolio = findByCustom("",sb.toString());

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

			}
			else rep.comRemplaza("[cTitular]","_____________________________________");
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

	public Vector genSolicitudNotificacion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vDirigido = new Vector();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");

		TDRAINotOficio dNotOficio = new TDRAINotOficio();
		TDRAIFolio  dRAIFol = new TDRAIFolio();

		TDObtenDatos dObten = new TDObtenDatos();

		String cNombreAPI="",cDirigidoA="",cAsunto="";
		String[] aFiltros = cQuery.split(",");
		int iCveRegularizacion = Integer.parseInt(aFiltros[0]);
		int iConsecutivo = Integer.parseInt(aFiltros[1]);
		int iMovCitaNotificacion = Integer.parseInt(aFiltros[2]);
		cNombreAPI = aFiltros[3];
		int iCveOficDest = Integer.parseInt(aFiltros[4]);
		int iTipoOficio = Integer.parseInt(aFiltros[5]);
		java.sql.Date dtAsignacionReq = null;
		String cFechaLetras = "",cNumOficioReq="";

		cAsunto = "Se solicita realizar Notificación";
		dObten.dFolio.setDatosFolio(cNumFolio);

		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("RAIFOLIO.ICVEREGULARIZACION, ");
		sb.append("RAIFOLIO.ICONSECUTIVO, ");
		sb.append("RAIFOLIO.cDigitosFolio, ");
		sb.append("RAIFOLIO.iConsecutivoFolio, ");
		sb.append("RAIFOLIO.iEjercicio, ");
		sb.append("GRLFolio.dtAsignacion, ");
		sb.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
		sb.append("Notif.iConsecutivoFolio as NotiConsecutivo, ");
		sb.append("Notif.iEjercicio as NotiEjercicioFol, ");
		sb.append("FolioNotif.dtAsignacion as dtNotAsignacion, ");
		sb.append("Record.cDigitosFolio as ReccDigitosFolio, ");
		sb.append("Record.iConsecutivoFolio as ReciConsecutivo, ");
		sb.append("Record.iEjercicio as ReciEjercicioFol, ");
		sb.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
		sb.append("from RAIREGULARIZACION RR ");
		sb.append("join RAIFOLIO on RAIFOLIO.ICVEREGULARIZACION = RR.ICVEREGULARIZACION ");
		sb.append("AND RAIFOLIO.ICVETIPODOCTO =  " + iTipoOficio);
		sb.append(" join GRLFolio on GRLFolio.iEjercicio = RAIFOLIO.iEjercicio ");
		sb.append("and GRLFolio.iCveOficina = RAIFOLIO.iCveOficina ");
		sb.append("and GRLFolio.iCveDepartamento = RAIFOLIO.iCveDepartamento ");
		sb.append("and GRLFolio.cDigitosFolio = RAIFOLIO.cDigitosFolio ");
		sb.append("and GRLFolio.iConsecutivo = RAIFOLIO.iConsecutivoFolio ");
		sb.append("left join RAINOTOFICIO on RAINOTOFICIO.ICVEREGULARIZACION = RAIFOLIO.ICVEREGULARIZACION ");
		sb.append("and RAINOTOFICIO.ICONSECUTIVO = RAIFOLIO.ICONSECUTIVO ");
		sb.append("left join RAIFOLIO Notif on Notif.ICVEREGULARIZACION = RAINOTOFICIO.ICVEREGULARIZACIONNOT ");
		sb.append("and Notif.ICONSECUTIVO = RAINOTOFICIO.ICONSECUTIVONOT ");
		sb.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicio ");
		sb.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
		sb.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
		sb.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
		sb.append("and FolioNotif.iConsecutivo = Notif.iConsecutivoFolio ");
		sb.append("left join RAIFOLIO Record on Record.ICVEREGULARIZACION = RAINOTOFICIO.ICVEREGULARIZACIONREC ");
		sb.append("and Record.ICONSECUTIVO = RAINOTOFICIO.ICONSECUTIVOREC ");
		sb.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicio ");
		sb.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
		sb.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
		sb.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
		sb.append("and FolioRecord.iConsecutivo = Record.iConsecutivoFolio ");
		sb.append("where RR.ICVEREGULARIZACION = " + iCveRegularizacion);
		sb.append(" and RAIFOLIO.ICONSECUTIVO = (select max(RAIFOLIO.ICONSECUTIVO) ");
		sb.append("from RAIFOLIO ");
		sb.append("where RAIFOLIO.ICVEREGULARIZACION = RR.ICVEREGULARIZACION ");
		sb.append("AND RAIFOLIO.ICVETIPODOCTO = " + iTipoOficio);
		sb.append(" ) ");

		try {
			vRegs = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//Servicios Portuarios del Registro de Contratos.
		try{
			vDirigido = super.FindByGeneric("", "SELECT cTitular FROM GRLOFICINA where icveoficina  = " + iCveOficDest,dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		if(vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);
			if(vDatos.getDate("dtAsignacion") != null){
				dtAsignacionReq = vDatos.getDate("dtAsignacion");
				cFechaLetras = Fechas.getFechaTexto(dtAsignacionReq,"/");
				cNumOficioReq = vDatos.getString("CDIGITOSFOLIO") + "." + vDatos.getString("ICONSECUTIVOFolio") + "/" + vDatos.getString("IEJERCICIO");
			}
		}

		if(vDirigido.size() > 0){
			TVDinRep vDatosD = (TVDinRep) vDirigido.get(0);
			cDirigidoA = vDatosD.getString("cTitular");
		}
		else
			cDirigidoA = "";

		StringTokenizer st = new StringTokenizer(cNombreAPI);
		cNombreAPI = "";
		while (st.hasMoreTokens()) {
			String cToken = st.nextToken();
			cNombreAPI = cNombreAPI  + ' ' +
			cToken.charAt(0) +
			cToken.substring(1,cToken.length()).toLowerCase();
		}

		rep.comRemplaza("[cDirigidoA]",cDirigidoA);
		rep.comRemplaza("[cNumOficioReq]",cNumOficioReq);
		rep.comRemplaza("[dtOficioConLetra]",cFechaLetras);
		rep.comRemplaza("[cGiradoA]",cNombreAPI);

		//return ;
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficDest,0,
				0,0,0,
				"",cAsunto,
				"","",
				true,"",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());


		TVDinRep vInsertaFolio = new TVDinRep();
		vInsertaFolio.put("iCveRegularizacion", iCveRegularizacion);
		vInsertaFolio.put("iEjercicio", dObten.dFolio.getCveEjercicio());
		vInsertaFolio.put("iCveOficina", dObten.dFolio.getCveOficina());
		vInsertaFolio.put("iCveDepartamento", dObten.dFolio.getCveDepartamento());
		vInsertaFolio.put("cDigitosFolio", dObten.dFolio.getCveDigitosFolio());
		vInsertaFolio.put("iConsecutivoFolio", dObten.dFolio.getCveConsecutivo());
		vInsertaFolio.put("iCveTipoDocto", 5);

		try{

			TVDinRep vDinRep2 = dRAIFol.insert(vInsertaFolio,null);
			TVDinRep vDinRep3 = new TVDinRep();

			vDinRep3.put("iCveRegularizacion",   iCveRegularizacion);
			vDinRep3.put("iConsecutivo",       iConsecutivo);
			vDinRep3.put("iMovCitaNotificacion",iMovCitaNotificacion);
			vDinRep3.put("iCveRegularizacionNot",vDinRep2.getInt("iCveRegularizacion"));
			vDinRep3.put("iConsecutivoNot",    vDinRep2.getInt("iConsecutivo"));

			vDinRep3 = dNotOficio.updNotificacion(vDinRep3,null);

		} catch(Exception ex){
			ex.printStackTrace();
		}

		return vRetorno;

	}

	public Vector genRecordatorioNot(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vDirigido = new Vector();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");

		TDRAINotOficio dNotOficio = new TDRAINotOficio();
		TDRAIFolio  dRAIFol = new TDRAIFolio();

		String cNombreAPI="",cDirigidoA="",cAsunto="";
		String[] aFiltros = cQuery.split(",");
		int iCveRegularizacion = Integer.parseInt(aFiltros[0]);
		int iConsecutivo = Integer.parseInt(aFiltros[1]);
		int iMovCitaNotificacion = Integer.parseInt(aFiltros[2]);
		cNombreAPI = aFiltros[3];
		int iCveOficDest = Integer.parseInt(aFiltros[4]);
		int iTipoOficio = Integer.parseInt(aFiltros[5]);
		java.sql.Date dtAsignacionReq = null;
		String cFechaLetras = "",cNumOficioReq="",cNumOficioNot = "";

		cAsunto = "Se solicita realizar Recordatorio de Notificación";
		TDObtenDatos dObten = new TDObtenDatos();
		dObten.dFolio.setDatosFolio(cNumFolio);

		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("RAIFOLIO.ICVEREGULARIZACION, ");
		sb.append("RAIFOLIO.ICONSECUTIVO, ");
		sb.append("RAIFOLIO.cDigitosFolio, ");
		sb.append("RAIFOLIO.iConsecutivoFolio, ");
		sb.append("RAIFOLIO.iEjercicio, ");
		sb.append("GRLFolio.dtAsignacion, ");
		sb.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
		sb.append("Notif.iConsecutivoFolio as NotiConsecutivo, ");
		sb.append("Notif.iEjercicio as NotiEjercicioFol, ");
		sb.append("FolioNotif.dtAsignacion as dtNotAsignacion, ");
		sb.append("Record.cDigitosFolio as ReccDigitosFolio, ");
		sb.append("Record.iConsecutivoFolio as ReciConsecutivo, ");
		sb.append("Record.iEjercicio as ReciEjercicioFol, ");
		sb.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
		sb.append("from RAIREGULARIZACION RR ");
		sb.append("join RAIFOLIO on RAIFOLIO.ICVEREGULARIZACION = RR.ICVEREGULARIZACION ");
		sb.append("AND RAIFOLIO.ICVETIPODOCTO = " + iTipoOficio);
		sb.append(" join GRLFolio on GRLFolio.iEjercicio = RAIFOLIO.iEjercicio ");
		sb.append("and GRLFolio.iCveOficina = RAIFOLIO.iCveOficina ");
		sb.append("and GRLFolio.iCveDepartamento = RAIFOLIO.iCveDepartamento ");
		sb.append("and GRLFolio.cDigitosFolio = RAIFOLIO.cDigitosFolio ");
		sb.append("and GRLFolio.iConsecutivo = RAIFOLIO.iConsecutivoFolio ");
		sb.append("left join RAINOTOFICIO on RAINOTOFICIO.ICVEREGULARIZACION = RAIFOLIO.ICVEREGULARIZACION ");
		sb.append("and RAINOTOFICIO.ICONSECUTIVO = RAIFOLIO.ICONSECUTIVO ");
		sb.append("left join RAIFOLIO Notif on Notif.ICVEREGULARIZACION = RAINOTOFICIO.ICVEREGULARIZACIONNOT ");
		sb.append("and Notif.ICONSECUTIVO = RAINOTOFICIO.ICONSECUTIVONOT ");
		sb.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicio ");
		sb.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
		sb.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
		sb.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
		sb.append("and FolioNotif.iConsecutivo = Notif.iConsecutivoFolio ");
		sb.append("left join RAIFOLIO Record on Record.ICVEREGULARIZACION = RAINOTOFICIO.ICVEREGULARIZACIONREC ");
		sb.append("and Record.ICONSECUTIVO = RAINOTOFICIO.ICONSECUTIVOREC ");
		sb.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicio ");
		sb.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
		sb.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
		sb.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
		sb.append("and FolioRecord.iConsecutivo = Record.iConsecutivoFolio ");
		sb.append("where RR.ICVEREGULARIZACION = " + iCveRegularizacion);
		sb.append(" and RAIFOLIO.ICONSECUTIVO = (select max(RAIFOLIO.ICONSECUTIVO) ");
		sb.append("from RAIFOLIO ");
		sb.append("where RAIFOLIO.ICVEREGULARIZACION = RR.ICVEREGULARIZACION ");
		sb.append("AND RAIFOLIO.ICVETIPODOCTO = " + iTipoOficio);
		sb.append(") ");

		try {
			vRegs = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//Servicios Portuarios del Registro de Contratos.
		try{
			vDirigido = super.FindByGeneric("", "SELECT cTitular FROM GRLOFICINA where icveoficina  = " + iCveOficDest,dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		if(vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);
			if(vDatos.getDate("dtAsignacion") != null){
				dtAsignacionReq = vDatos.getDate("dtAsignacion");
				cFechaLetras = Fechas.getFechaTexto(dtAsignacionReq,"/");
				cNumOficioReq = vDatos.getString("CDIGITOSFOLIO") + "." + vDatos.getString("ICONSECUTIVOFolio") + "/" + vDatos.getString("IEJERCICIO");
			}
			cNumOficioNot = vDatos.getString("NOTCDIGITOSFOLIO") + "." + vDatos.getString("NOTICONSECUTIVO") + "/" + vDatos.getString("NOTIEJERCICIOFOL");
		}

		if(vDirigido.size() > 0){
			TVDinRep vDatosD = (TVDinRep) vDirigido.get(0);
			cDirigidoA = vDatosD.getString("cTitular");
		}
		else
			cDirigidoA = "";

		StringTokenizer st = new StringTokenizer(cNombreAPI);
		cNombreAPI = "";
		while (st.hasMoreTokens()) {
			String cToken = st.nextToken();
			cNombreAPI = cNombreAPI  + ' ' +
			cToken.charAt(0) +
			cToken.substring(1,cToken.length()).toLowerCase();
		}

		rep.comRemplaza("[cDirigidoA]",cDirigidoA);
		rep.comRemplaza("[cNumOficioReq]",cNumOficioReq);
		rep.comRemplaza("[dtOficioReqConLetra]",cFechaLetras);
		rep.comRemplaza("[cNumOficioNot]",cNumOficioNot);
		rep.comRemplaza("[cTitular]",cNombreAPI);

		//return ;
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficDest,0,
				0,0,0,
				"",cAsunto,
				"","",
				true,"",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());


		TVDinRep vInsertaFolio = new TVDinRep();
		vInsertaFolio.put("iCveRegularizacion", iCveRegularizacion);
		vInsertaFolio.put("iEjercicio", dObten.dFolio.getCveEjercicio());
		vInsertaFolio.put("iCveOficina", dObten.dFolio.getCveOficina());
		vInsertaFolio.put("iCveDepartamento", dObten.dFolio.getCveDepartamento());
		vInsertaFolio.put("cDigitosFolio", dObten.dFolio.getCveDigitosFolio());
		vInsertaFolio.put("iConsecutivoFolio", dObten.dFolio.getCveConsecutivo());
		vInsertaFolio.put("iCveTipoDocto", 6);


		try{

			TVDinRep vDinRep2 = dRAIFol.insert(vInsertaFolio,null);
			TVDinRep vDinRep3 = new TVDinRep();
			vDinRep3.put("iCveRegularizacion",   iCveRegularizacion);
			vDinRep3.put("iConsecutivo",       iConsecutivo);
			vDinRep3.put("iMovCitaNotificacion",iMovCitaNotificacion);
			vDinRep3.put("iCveRegularizacionRec",vDinRep2.getInt("iCveRegularizacion"));
			vDinRep3.put("iConsecutivoRec",    vDinRep2.getInt("iConsecutivo"));
			vDinRep3 = dNotOficio.updRecordatorio(vDinRep3,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return vRetorno;
	}


	public Vector genCertificacion(String cFiltro,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vRegs = new Vector();

		TWord rep = new TWord();
		String[] aFiltros = cFiltro.split(",");
		String cNombreAPI="";
		int iCveRegularizacion = Integer.parseInt(aFiltros[0]);
		cNombreAPI = aFiltros[3];
		int iTipoOficio = Integer.parseInt(aFiltros[5]);
		String cNumOficioReq="";
		java.sql.Date dtFechaNotif = null, dtFechaFinal =null, dtFechaInicial = null;
		TDGRLDiaNoHabil dFechaNoHabil = new TDGRLDiaNoHabil();
		TLetterNumberFormat dNumeros = new TLetterNumberFormat();


		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("RF.CDIGITOSFOLIO AS CDIGITOSFOLIO, ");
		sb.append("RF.ICONSECUTIVOFOLIO AS ICONSECUTIVOFOLIO, ");
		sb.append("RF.IEJERCICIO AS IEJERCICIOFOL, ");
		sb.append("FOL.DTASIGNACION AS DTASIGNACION, ");
		sb.append("CCN.TSFECHANOTIFICACION AS TSFECHANOTIFICACION, ");
		sb.append("CASE CHAR(RU.LDENTRORECINTOPORTUARIO) ");
		sb.append("WHEN '1' THEN RU.COBRAENCONTRADA || '. DENTRO DEL RECINTO EN EL PUERTO DE ' || PTO.CDSCPUERTO || ', ' || EFED.CNOMBRE ");
		sb.append("WHEN '2' THEN RU.COBRAENCONTRADA || '. FUERA DELETE RECINTO EN ' || RU.CUBICACION ");
		sb.append("END CUBICACION ");
		sb.append("FROM RAIREGULARIZACION RR ");
		sb.append("JOIN RAIFOLIO RF ON RR.ICVEREGULARIZACION = RF.ICVEREGULARIZACION ");
		sb.append("AND RF.ICVETIPODOCTO = " + iTipoOficio);
		sb.append(" JOIN GRLFOLIO FOL ON RF.IEJERCICIO = FOL.IEJERCICIO ");
		sb.append("AND RF.ICVEOFICINA = FOL.ICVEOFICINA ");
		sb.append("AND RF.ICVEDEPARTAMENTO = FOL.ICVEDEPARTAMENTO ");
		sb.append("AND RF.CDIGITOSFOLIO = FOL.CDIGITOSFOLIO ");
		sb.append("AND RF.ICONSECUTIVOFOLIO = FOL.ICONSECUTIVO ");
		sb.append("JOIN RAINOTOFICIO RNO ON RF.ICVEREGULARIZACION = RNO.ICVEREGULARIZACION ");
		sb.append("AND RF.ICONSECUTIVO = RNO.ICONSECUTIVO ");
		sb.append("JOIN CYSCITANOTIFICACION CCN ON RNO.IMOVCITANOTIFICACION = CCN.IMOVCITANOTIFICACION ");
		sb.append("LEFT JOIN RAIUBICACION RU ON RR.ICVEREGULARIZACION = RU.ICVEREGULARIZACION ");
		sb.append("LEFT JOIN GRLPUERTO PTO ON RU.ICVEPUERTO = PTO.ICVEPUERTO ");
		sb.append("LEFT JOIN GRLENTIDADFED EFED ON PTO.ICVEPAIS = EFED.ICVEPAIS ");
		sb.append("AND PTO.ICVEENTIDADFED = EFED.ICVEENTIDADFED ");
		sb.append("where RR.ICVEREGULARIZACION =  " + iCveRegularizacion);
		sb.append(" ORDER BY RF.ICONSECUTIVO ");
		try {
			vRegs = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		String cFechaAsignacion = "";
		String cUbicacion="";
		if(vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);
			if(vDatos.getDate("DTASIGNACION") != null)
				cFechaAsignacion  = Fecha.getFechaDDMMMMMYYYY(vDatos.getDate("DTASIGNACION"), " de ");

			if(vDatos.getTimeStamp("TSFECHANOTIFICACION") != null && !vDatos.getTimeStamp("TSFECHANOTIFICACION").equals(""))
				dtFechaNotif = Fecha.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"));

			cNumOficioReq = vDatos.getString("CDIGITOSFOLIO") + "." + vDatos.getString("ICONSECUTIVOFOLIO") + "/" + vDatos.getString("IEJERCICIOFOL");
			cUbicacion = vDatos.getString("CUBICACION");
		}



		rep.comRemplaza("[cTitular]", cNombreAPI);
		rep.comRemplaza("[cNumOficioNotificado]", cNumOficioReq);
		rep.comRemplaza("[cFechaOficioNotificado]", cFechaAsignacion);
		rep.comRemplaza("[cFechaEmision]",Fecha.getFechaDDMMMMMYYYY(Fecha.TodaySQL()," de "));
		rep.comRemplaza("[cUbicacion]", cUbicacion);

		if(dtFechaNotif != null)
			rep.comRemplaza("[cFechaNotificacionOficio]",Fecha.getFechaDDMMMMMYYYY(dtFechaNotif, " de "));
		else
			rep.comRemplaza("[cFechaNotificacionOficio]","_________________");

		int iNumDias  =15;

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

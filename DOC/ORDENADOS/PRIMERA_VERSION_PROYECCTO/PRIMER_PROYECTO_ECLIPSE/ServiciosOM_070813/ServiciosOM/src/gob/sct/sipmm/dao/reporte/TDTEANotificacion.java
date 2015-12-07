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


public class TDTEANotificacion  extends DAOBase{
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


	public TDTEANotificacion(){

	}

	public StringBuffer genNotificacion(String cFiltro) throws Exception{
		TFechas Fecha = new TFechas("44");
		String [] cParametros = cFiltro.split(",");
		String iCveTermAnticipada = cParametros[0];

		String iMovCitaNotificacion = cParametros[2];

		int iTipoOficio = Integer.parseInt(cParametros[5]);
		int iCveTitulo =  Integer.parseInt(cParametros[6]);
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
		sb.append("from TEATERMINACIONANTICIPADA TTA ");
		sb.append("LEFT JOIN TEAFOLIO TF ON TTA.ICVETERMANTICIPADA = TF.ICVETERMANTICIPADA ");
		sb.append("AND TF.ICVETIPODOCTO =  " + iTipoOficio);
		sb.append(" left join GRLFolio FOL on FOL.iEjercicio = TF.IEJERCICIO ");
		sb.append("and FOL.iCveOficina = TF.iCveOficina ");
		sb.append("and FOL.iCveDepartamento = TF.iCveDepartamento ");
		sb.append("and FOL.cDigitosFolio = TF.cDigitosFolio ");
		sb.append("and FOL.iConsecutivo = TF.ICONSECUTIVOFOLIO ");
		sb.append("LEFT JOIN GRLOFICINA O ON FOL.ICVEOFICINA = O.ICVEOFICINA ");
		sb.append("LEFT JOIN GRLENTIDADFED EF ON O.ICVEPAIS = EF.ICVEPAIS ");
		sb.append("AND O.ICVEENTIDADFED = EF.ICVEENTIDADFED ");
		sb.append("LEFT JOIN GRLMUNICIPIO M ON O.ICVEPAIS = M.ICVEPAIS ");
		sb.append("AND O.ICVEENTIDADFED = M.ICVEENTIDADFED ");
		sb.append("AND O.ICVEMUNICIPIO = M.ICVEMUNICIPIO ");
		sb.append("LEFT JOIN GRLDEPTOXOFIC DEO ON FOL.ICVEDEPARTAMENTO = DEO.ICVEDEPARTAMENTO ");
		sb.append("AND FOL.ICVEOFICINA = DEO.ICVEOFICINA ");
		sb.append("where TTA.ICVETERMANTICIPADA = " + iCveTermAnticipada);
		sb.append(" order by TF.ICONSECUTIVO ");


		Vector vcNotificacion = findByCustom("",cQryNotificacion);
		Vector vcTitulo = findByCustom("",cQryTitulo);
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
		int iCveTermAnticipada = Integer.parseInt(aFiltros[0]);
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
		sb.append("TEAFOLIO.ICVETERMANTICIPADA, ");
		sb.append("TEAFOLIO.ICONSECUTIVO, ");
		sb.append("TEAFOLIO.cDigitosFolio, ");
		sb.append("TEAFOLIO.iConsecutivoFolio, ");
		sb.append("TEAFOLIO.iEjercicio, ");
		sb.append("GRLFolio.dtAsignacion, ");
		sb.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
		sb.append("Notif.iConsecutivoFolio as NotiConsecutivo, ");
		sb.append("Notif.iEjercicio as NotiEjercicioFol, ");
		sb.append("FolioNotif.dtAsignacion as dtNotAsignacion, ");
		sb.append("Record.cDigitosFolio as ReccDigitosFolio, ");
		sb.append("Record.iConsecutivoFolio as ReciConsecutivo, ");
		sb.append("Record.iEjercicio as ReciEjercicioFol, ");
		sb.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
		sb.append("from TEATERMINACIONANTICIPADA TA ");
		sb.append("join TEAFOLIO on TEAFOLIO.ICVETERMANTICIPADA = TA.ICVETERMANTICIPADA ");
		sb.append("AND TEAFOLIO.ICVETIPODOCTO = " + iTipoOficio);
		sb.append(" join GRLFolio on GRLFolio.iEjercicio = TEAFOLIO.iEjercicio ");
		sb.append("and GRLFolio.iCveOficina = TEAFOLIO.iCveOficina ");
		sb.append("and GRLFolio.iCveDepartamento = TEAFOLIO.iCveDepartamento ");
		sb.append("and GRLFolio.cDigitosFolio = TEAFOLIO.cDigitosFolio ");
		sb.append("and GRLFolio.iConsecutivo = TEAFOLIO.ICONSECUTIVOFOLIO ");
		sb.append("left join TEANOTOFICIO on TEANOTOFICIO.ICVETERMANTICIPADA = TEAFOLIO.ICVETERMANTICIPADA ");
		sb.append("and TEANOTOFICIO.ICONSECUTIVO = TEAFOLIO.ICONSECUTIVO ");
		sb.append("left join TEAFOLIO Notif on Notif.ICVETERMANTICIPADA = TEANOTOFICIO.ICVETERMANTICIPADANOT ");
		sb.append("and Notif.ICONSECUTIVO = TEANOTOFICIO.ICONSECUTIVONOT ");
		sb.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicio ");
		sb.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
		sb.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
		sb.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
		sb.append("and FolioNotif.iConsecutivo = Notif.ICONSECUTIVOFOLIO ");
		sb.append("left join TEAFOLIO Record on Record.ICVETERMANTICIPADA = TEANOTOFICIO.ICVETERMANTICIPADA ");
		sb.append("and Record.ICONSECUTIVO = TEANOTOFICIO.ICONSECUTIVOREC ");
		sb.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicio ");
		sb.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
		sb.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
		sb.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
		sb.append("and FolioRecord.iConsecutivo = Record.ICONSECUTIVOFOLIO ");
		sb.append("where TA.ICVETERMANTICIPADA = " + iCveTermAnticipada);
		sb.append(" and TEAFOLIO.ICONSECUTIVO = (select max(TEAFOLIO.ICONSECUTIVO) ");
		sb.append("from TEAFOLIO ");
		sb.append("where TEAFOLIO.ICVETERMANTICIPADA = TA.ICVETERMANTICIPADA ");
		sb.append("AND TEAFOLIO.ICVETIPODOCTO = " + iTipoOficio);
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
		vInsertaFolio.put("iCveTermAnticipada", iCveTermAnticipada);
		vInsertaFolio.put("iEjercicio", dObten.dFolio.getCveEjercicio());
		vInsertaFolio.put("iCveOficina", dObten.dFolio.getCveOficina());
		vInsertaFolio.put("iCveDepartamento", dObten.dFolio.getCveDepartamento());
		vInsertaFolio.put("cDigitosFolio", dObten.dFolio.getCveDigitosFolio());
		vInsertaFolio.put("iConsecutivoFolio", dObten.dFolio.getCveConsecutivo());
		vInsertaFolio.put("iCveTipoDocto", 2);

		try{

			TVDinRep vDinRep2 = dRAIFol.insert(vInsertaFolio,null);
			TVDinRep vDinRep3 = new TVDinRep();

			vDinRep3.put("iCveTermAnticipada",   iCveTermAnticipada);
			vDinRep3.put("iConsecutivo",       iConsecutivo);
			vDinRep3.put("iMovCitaNotificacion",iMovCitaNotificacion);
			vDinRep3.put("iCveTermAnticipadaNot",vDinRep2.getInt("iCveTermAnticipada"));
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
		int iCveTermAnticipada = Integer.parseInt(aFiltros[0]);
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
		sb.append("TEAFOLIO.ICVETERMANTICIPADA, ");
		sb.append("TEAFOLIO.ICONSECUTIVO, ");
		sb.append("TEAFOLIO.cDigitosFolio, ");
		sb.append("TEAFOLIO.iConsecutivoFolio, ");
		sb.append("TEAFOLIO.iEjercicio, ");
		sb.append("GRLFolio.dtAsignacion, ");
		sb.append("Notif.cDigitosFolio as NotcDigitosFolio, ");
		sb.append("Notif.iConsecutivoFolio as NotiConsecutivo, ");
		sb.append("Notif.iEjercicio as NotiEjercicioFol, ");
		sb.append("FolioNotif.dtAsignacion as dtNotAsignacion, ");
		sb.append("Record.cDigitosFolio as ReccDigitosFolio, ");
		sb.append("Record.iConsecutivoFolio as ReciConsecutivo, ");
		sb.append("Record.iEjercicio as ReciEjercicioFol, ");
		sb.append("FolioRecord.dtAsignacion as dtRecAsignacion ");
		sb.append("from TEATERMINACIONANTICIPADA TA ");
		sb.append("join TEAFOLIO on TEAFOLIO.ICVETERMANTICIPADA = TA.ICVETERMANTICIPADA ");
		sb.append("AND TEAFOLIO.ICVETIPODOCTO = " + iTipoOficio);
		sb.append(" join GRLFolio on GRLFolio.iEjercicio = TEAFOLIO.iEjercicio ");
		sb.append("and GRLFolio.iCveOficina = TEAFOLIO.iCveOficina ");
		sb.append("and GRLFolio.iCveDepartamento = TEAFOLIO.iCveDepartamento ");
		sb.append("and GRLFolio.cDigitosFolio = TEAFOLIO.cDigitosFolio ");
		sb.append("and GRLFolio.iConsecutivo = TEAFOLIO.ICONSECUTIVOFOLIO ");
		sb.append("left join TEANOTOFICIO on TEANOTOFICIO.ICVETERMANTICIPADA = TEAFOLIO.ICVETERMANTICIPADA ");
		sb.append("and TEANOTOFICIO.ICONSECUTIVO = TEAFOLIO.ICONSECUTIVO ");
		sb.append("left join TEAFOLIO Notif on Notif.ICVETERMANTICIPADA = TEANOTOFICIO.ICVETERMANTICIPADANOT ");
		sb.append("and Notif.ICONSECUTIVO = TEANOTOFICIO.ICONSECUTIVONOT ");
		sb.append("left join GRLFolio FolioNotif on FolioNotif.iEjercicio = Notif.iEjercicio ");
		sb.append("and FolioNotif.iCveOficina = Notif.iCveOficina ");
		sb.append("and FolioNotif.iCveDepartamento = Notif.iCveDepartamento ");
		sb.append("and FolioNotif.cDigitosFolio = Notif.cDigitosFolio ");
		sb.append("and FolioNotif.iConsecutivo = Notif.ICONSECUTIVOFOLIO ");
		sb.append("left join TEAFOLIO Record on Record.ICVETERMANTICIPADA = TEANOTOFICIO.ICVETERMANTICIPADA ");
		sb.append("and Record.ICONSECUTIVO = TEANOTOFICIO.ICONSECUTIVOREC ");
		sb.append("left join GRLFolio FolioRecord on FolioRecord.iEjercicio = Record.iEjercicio ");
		sb.append("and FolioRecord.iCveOficina = Record.iCveOficina ");
		sb.append("and FolioRecord.iCveDepartamento = Record.iCveDepartamento ");
		sb.append("and FolioRecord.cDigitosFolio = Record.cDigitosFolio ");
		sb.append("and FolioRecord.iConsecutivo = Record.ICONSECUTIVOFOLIO ");
		sb.append("where TA.ICVETERMANTICIPADA = " + iCveTermAnticipada);
		sb.append(" and TEAFOLIO.ICONSECUTIVO = (select max(TEAFOLIO.ICONSECUTIVO) ");
		sb.append("from TEAFOLIO ");
		sb.append("where TEAFOLIO.ICVETERMANTICIPADA = TA.ICVETERMANTICIPADA ");
		sb.append("AND TEAFOLIO.ICVETIPODOCTO = " + iTipoOficio);
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
		vInsertaFolio.put("iCveTermAnticipada", iCveTermAnticipada);
		vInsertaFolio.put("iEjercicio", dObten.dFolio.getCveEjercicio());
		vInsertaFolio.put("iCveOficina", dObten.dFolio.getCveOficina());
		vInsertaFolio.put("iCveDepartamento", dObten.dFolio.getCveDepartamento());
		vInsertaFolio.put("cDigitosFolio", dObten.dFolio.getCveDigitosFolio());
		vInsertaFolio.put("iConsecutivoFolio", dObten.dFolio.getCveConsecutivo());
		vInsertaFolio.put("iCveTipoDocto", 3);


		try{

			TVDinRep vDinRep2 = dRAIFol.insert(vInsertaFolio,null);
			TVDinRep vDinRep3 = new TVDinRep();
			vDinRep3.put("iCveTermAnticipada",   iCveTermAnticipada);
			vDinRep3.put("iConsecutivo",       iConsecutivo);
			vDinRep3.put("iMovCitaNotificacion",iMovCitaNotificacion);
			vDinRep3.put("iCveTermAnticipadaRec",vDinRep2.getInt("iCveTermAnticipada"));
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
		int iCveTermAnticipada = Integer.parseInt(aFiltros[0]);
		cNombreAPI = aFiltros[3];
		int iTipoOficio = Integer.parseInt(aFiltros[5]);
		String cCveTitulo = aFiltros[6];
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
		sb.append("FROM TEATERMINACIONANTICIPADA RR ");
		sb.append("JOIN TEAFOLIO RF ON RR.ICVETERMANTICIPADA = RF.ICVETERMANTICIPADA ");
		sb.append("AND RF.ICVETIPODOCTO = " + iTipoOficio);
		sb.append(" JOIN GRLFOLIO FOL ON RF.IEJERCICIO = FOL.IEJERCICIO ");
		sb.append("AND RF.ICVEOFICINA = FOL.ICVEOFICINA ");
		sb.append("AND RF.ICVEDEPARTAMENTO = FOL.ICVEDEPARTAMENTO ");
		sb.append("AND RF.CDIGITOSFOLIO = FOL.CDIGITOSFOLIO ");
		sb.append("AND RF.ICONSECUTIVOFOLIO = FOL.ICONSECUTIVO ");
		sb.append("JOIN TEANOTOFICIO RNO ON RF.ICVETERMANTICIPADA = RNO.ICVETERMANTICIPADA ");
		sb.append("AND RF.ICONSECUTIVO = RNO.ICONSECUTIVO ");
		sb.append("JOIN CYSCITANOTIFICACION CCN ON RNO.IMOVCITANOTIFICACION = CCN.IMOVCITANOTIFICACION ");
		sb.append("JOIN CPATITULO TIT ON RR.ICVETITULO = TIT.ICVETITULO ");
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
		sb.append("WHERE RR.ICVETERMANTICIPADA = " + iCveTermAnticipada);
		try {
			vRegs = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		String cFechaAsignacion = "";
		String cUbicacion="";
		String cNumTitulo = "";
		String cPseudoTitulo = "";
		String cVigenciaTitulo = "";
		String cObjetoTitulo = "";
		String cTitular = "";

		if(vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);
			if(vDatos.getDate("DTASIGNACION") != null)
				cFechaAsignacion  = Fecha.getFechaDDMMMMMYYYY(vDatos.getDate("DTASIGNACION"), " de ");

			if(vDatos.getTimeStamp("TSFECHANOTIFICACION") != null && !vDatos.getTimeStamp("TSFECHANOTIFICACION").equals(""))
				dtFechaNotif = Fecha.getDateSQL(vDatos.getTimeStamp("TSFECHANOTIFICACION"));

			cNumOficioReq = vDatos.getString("CDIGITOSFOLIO") + "." + vDatos.getString("ICONSECUTIVOFOLIO") + "/" + vDatos.getString("IEJERCICIOFOL");
			cUbicacion = vDatos.getString("CUBICACION");
			cNumTitulo = vDatos.getString("CNUMTITULO");
			cPseudoTitulo = vDatos.getString("CPSEUDONIMOTIPOTITULO");
			cVigenciaTitulo = vDatos.getString("CANIOSVIGENCIATIT");
			cObjetoTitulo = vDatos.getString("COBJETOTITULO");
			cTitular = vDatos.getString("CTITULAR");
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

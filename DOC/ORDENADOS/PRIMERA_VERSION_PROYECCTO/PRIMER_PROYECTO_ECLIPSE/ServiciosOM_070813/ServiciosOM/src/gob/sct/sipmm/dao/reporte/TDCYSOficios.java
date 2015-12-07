package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.sql.SQLException;
import java.util.Vector;
import gob.sct.sipmm.dao.reporte.*;
import java.sql.*;
import java.util.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.*;
import gob.sct.sipmm.dao.*;





/**
 * <p>Title: TDZDPDeclaratoria.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame
 * @version 1.0
 */

public class TDCYSOficios extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
//	private String cDirSEMARNAT = VParametros.getPropEspecifica("ZPDCveSEMARNAT");
	String Folio;
	private int iCveSegtoEntidad=0;
	private int iCveOpinionEntidad = 0;
	private int iEjercicioSolicitud = 0;
	private int iNumSolicitud = 0;
	private String cOpnDirigidoA;
	private String cPtoOpinion;
	TDObtenDatos obtenerDatos = new TDObtenDatos();
	private int iCveDepto;
	private int iCveOficina;
	private int iEjercicioOficio = 0;
	private String cDigitosFolio = "";
	private int iConsecutivo=0;



	public TDCYSOficios(){

	}

	public Vector genReversionTitular(String cFiltro,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen) throws Exception{
		//  Vector vRetorno = new Vector();
		TFechas Fecha = new TFechas("44");
		int iEjercicio, iMovProcedimiento;
		int iCveOficinaDest=0;
		int iCveDeptoDest=0;
		int iCveTitular = 0;
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		String cAsunto="Se solicita su intervención en la diligencia de reversión ";
		String cArea = "";
		java.sql.Date dtAsignacion;
		java.sql.Date dtPublicacionDOF = null;
		java.sql.Date dtAsignacionAntAnt = null;
		java.sql.Date dtNotificacion = null;

		TLetterNumberFormat tLetterNumberFormat = new TLetterNumberFormat();
		TDCYSSegProcedimiento spOficio = new TDCYSSegProcedimiento();
		TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
		TDCPATitulo1 titTitulo = new TDCPATitulo1();

		String cQuery1 = "";
		String[] aFiltros = cFiltro.split(",");

		TWord rep = new TWord();  //Reporte Principal.

		rep.iniciaReporte(); //Reporte Principal.
		rep.comRemplaza("[dtEmision]",
				"" + tLetterNumberFormat.getCantidad((long) Fecha.getIntDay(Fecha.TodaySQL())) + " de " +
				"" + Fecha.getMonthName(Fecha.TodaySQL()) + " del año " +
				"" + tLetterNumberFormat.getCantidad((long) Fecha.getIntYear(Fecha.TodaySQL())) + ".");

		iEjercicio = Integer.parseInt(aFiltros[1],10);
		iMovProcedimiento = Integer.parseInt(aFiltros[2],10);

		Vector vOficioAntAnt = spOficio.findByProAntAntOficioNotRec(iEjercicio,iMovProcedimiento);
		System.out.println("\n\n vOficioAntAnt size  " + vOficioAntAnt.size());
		if(vOficioAntAnt.size() > 0){
			TVDinRep vDinOficAntAnt = (TVDinRep)vOficioAntAnt.get(0);
			if(vDinOficAntAnt.getDate("dtAsignacion")!=null){
				dtAsignacionAntAnt = vDinOficAntAnt.getDate("dtAsignacion");
				rep.comRemplaza("[cFechaFolioAntAnt]",
						String.valueOf(Fecha.getIntDay(dtAsignacionAntAnt)) +
						" de " +
						Fecha.getMonthName(dtAsignacionAntAnt) + " de " +
						String.valueOf(Fecha.getIntYear(dtAsignacionAntAnt)));
			}else
				rep.comRemplaza("[cFechaFolioAntAnt]", "");

			rep.comRemplaza("[cNumOficioAntAnt]", vDinOficAntAnt.getString("iMovFolioProc")+"."+
					vDinOficAntAnt.getString("cDigitosFolio") + "." +
					vDinOficAntAnt.getString("iConsecutivo") + "/" +
					vDinOficAntAnt.getString("iEjercicioFol"));

		}else{
			rep.comRemplaza("[cNumOficioAntAnt]", "");
			rep.comRemplaza("[cFechaFolioAntAnt]", "");
		}

		Vector vOficio = spOficio.findByOficioNotRec(iEjercicio, iMovProcedimiento,0);
		if(vOficio.size() > 0){
			TVDinRep vdinRep = (TVDinRep)vOficio.get(0);
			rep.comRemplaza("[cNumOficioA]", vdinRep.getString("iMovFolioProc")+"."+
					vdinRep.getString("cDigitosFolio") + "." +
					vdinRep.getString("iConsecutivo") + "/" +
					vdinRep.getString("iEjercicioFol"));

			if(vdinRep.getDate ("dtAsignacion") != null){
				dtAsignacion = vdinRep.getDate("dtAsignacion");

				rep.comRemplaza("[cFechaFolio]",
						String.valueOf(Fecha.getIntDay(dtAsignacion)) + " de " +
						Fecha.getMonthName(dtAsignacion) + " de " +
						String.valueOf(Fecha.getIntYear(dtAsignacion)));
			} else{
				rep.comRemplaza("[cFechaFolio]"," ");
			}
			System.out.println("NotAsigna" + vdinRep.getDate("dtNotAsignacion"));
			if(vdinRep.getDate("dtNotAsignacion") != null){

				dtNotificacion = vdinRep.getDate("dtNotAsignacion");
				rep.comRemplaza("[cFechaNotificacion]",
						String.valueOf(Fecha.getIntDay(dtNotificacion)) + " de " +
						Fecha.getMonthName(dtNotificacion) + " de " +
						String.valueOf(Fecha.getIntYear(dtNotificacion)));

			}else{
				rep.comRemplaza("[cFechaNotificacion]"," ");
			}
		} else {
			rep.comRemplaza("[cNumOficioA]"," ");
			rep.comRemplaza("[cFechaFolio]"," ");
		}
		Vector vUbicacion = titTitulo.findByInfTituloUbicacion(Integer.parseInt(aFiltros[0],10));

		if(vUbicacion.size() > 0){
			TVDinRep vdinUbicacion = (TVDinRep)vUbicacion.get(0);
			cArea = vdinUbicacion.getString("cCalleTitulo") + " ";
			if(vdinUbicacion.getString("cKm") != "") cArea += "Km. " + vdinUbicacion.getString("cKm");
			if(vdinUbicacion.getString("cColoniaTitulo") != "") cArea += " " + vdinUbicacion.getString("cColoniaTitulo");
			rep.comRemplaza("[cAreaSolic]",cArea);
		}
		Vector vTitulo = titTitulo.findByInfTitulo(Integer.parseInt(aFiltros[0],10));
		System.out.println("\n\n\n vTitulo size" + vTitulo.size());
		if(vTitulo.size() > 0){
			TVDinRep vdinTit = (TVDinRep)vTitulo.get(0);
			// rep.comRemplaza("[]");
			rep.comRemplaza("[cTipoTitulo]",vdinTit.getString("cDscTipoTitulo"));
			rep.comRemplaza("[cNumTitulo]",vdinTit.getString("cNumTitulo"));
			dtPublicacionDOF = vdinTit.getDate("dtPublicacionDOF");
			System.out.println("FEchaPublicacion "  + dtPublicacionDOF);
			if(vdinTit.getDate("dtPublicacionDOF")!=null){
				rep.comRemplaza("[dtPublicacionDOF]",
						String.valueOf(Fecha.getIntDay(dtPublicacionDOF)) +
						" de " +
						Fecha.getMonthName(dtPublicacionDOF) + " de " +
						String.valueOf(Fecha.getIntYear(dtPublicacionDOF)));
			}else{
				rep.comRemplaza("[dtPublicacionDOF]"," ");
			}
			iCveTitular = vdinTit.getInt("iCveTitular");
			rep.comRemplaza("[cDirigidoA]",vdinTit.getString("cNomRazonSocial") + " " +
					vdinTit.getString("cApPaterno") + " " +
					vdinTit.getString("cApMaterno"));
			rep.comRemplaza("[cObjetoTitulo]",vdinTit.getString("cObjetoTitulo"));
			rep.comRemplaza("[cUsoTitulo]",vdinTit.getString("cDscUsoTitulo"));
			if(vdinTit.getInt("iPropiedad")== 1)
				rep.comRemplaza("[cPropiedad]","Nacional");
			else if(vdinTit.getInt("iPropiedad")== 2)
				rep.comRemplaza("[cPropiedad]","Particular");
			else
				rep.comRemplaza("[cPropiedad]"," ");
			rep.comRemplaza("[cEntidadFed]",vdinTit.getString("cNomEntidadFed"));

			int iAniosDuracion = 1;
			if (vdinTit.get("dtIniVigenciaTitulo")!=null && vdinTit.get("dtVigenciaTitulo")!= null)
				iAniosDuracion = Fecha.getDaysBetweenDates(vdinTit.getDate("dtIniVigenciaTitulo"),vdinTit.getDate("dtVigenciaTitulo"));
			iAniosDuracion = iAniosDuracion/365;
			rep.comRemplaza("[cAniosDuracion]",""+iAniosDuracion);

		}else{
			rep.comRemplaza("[cTipoTitulo]"," ");
			rep.comRemplaza("[cNumTitulo]"," ");
			rep.comRemplaza("[dtPublicacionDOF]"," ");
			rep.comRemplaza("[cDirigidoA]", " ");
			rep.comRemplaza("[cObjetoTitulo]"," ");
			rep.comRemplaza("[cUsoTitulo]"," ");
			rep.comRemplaza("[cPropiedad]"," ");
			rep.comRemplaza("[cEntidadFed]"," ");
			rep.comRemplaza("[cAniosDuracion]"," ");
		}
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				iCveTitular,1,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		this.setDatosFolio(cNumFolio);
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iEjercicio",iEjercicio);
			vDinRep.put("iMovProcedimiento",iMovProcedimiento);
			vDinRep.put("iEjercicioFol",iEjercicioOficio);
			vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
			vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
			vDinRep.put("cDigitosFolio",cDigitosFolio);
			vDinRep.put("iConsecutivo",iConsecutivo);
			vDinRep.put("lEsProrroga",0);
			vDinRep.put("lEsAlegato",0);
			vDinRep.put("lEsSinEfecto",0);
			vDinRep.put("lEsNotificacion",0);
			vDinRep.put("lEsRecordatorio",0);
                        vDinRep.put("lEsResolucion",0);
			vDinRep.put("lEsReversion",1);
			vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return vRetorno;

	}

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


	public Vector GenOpnInterna(String cFiltro,String cFolio,String cCveOfic,
			String cCveDepto) throws Exception{
		Vector vcRegsRep = new Vector();
		Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector();
		String cPuerto="",cEntidad="",cMunicipio="", cGobernador="";


		TWord rep = new TWord();
		TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();
		rep.iniciaReporte();

		datosOpinion.dDatosOpnTra.setFiltrosOpn(cFiltro);
		iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
		iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();

		datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cFolio);
		datosOpinion.dDatosOficPer.setFiltroOpnEnt(iCveOpinionEntidad);
		iCveDepto = datosOpinion.dDatosOficPer.getiCveDepto();
		iCveOficina = datosOpinion.dDatosOficPer.getiCveOficina();

		String[] aValores = cFiltro.split(",");
		String cQuery1 = "SELECT P.CDSCPUERTO, EF.CNOMBRE AS CENTIDAD, M.CNOMBRE AS CMUNICIPIO " +
		"FROM GRLPUERTO P " +
		"JOIN GRLPAIS PA ON PA.ICVEPAIS = P.ICVEPAIS " +
		"JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = P.ICVEPAIS AND EF.ICVEENTIDADFED = P.ICVEENTIDADFED " +
		"JOIN GRLMUNICIPIO M ON M.ICVEPAIS = P.ICVEPAIS AND M.ICVEENTIDADFED = P.ICVEENTIDADFED AND " +
		"M.ICVEMUNICIPIO = P.ICVEMUNICIPIO " +
		"WHERE ICVEPUERTO = "+ aValores[4] ;


		vcRegsRep = GenSQLGenerico(vcRegsRep,cQuery1);

		if(vcRegsRep.size() > 0){
			TVDinRep vDatosRep = (TVDinRep) vcRegsRep.get(0);
			cPuerto = vDatosRep.getString("cDscPuerto").toLowerCase();
			cEntidad = vDatosRep.getString("cEntidad").toLowerCase();
			cMunicipio = vDatosRep.getString("cMunicipio").toLowerCase();

			rep.comRemplaza("[cPuerto]",cPuerto);
			rep.comRemplaza("[cMunicipioPto]",cMunicipio);
			rep.comRemplaza("[cEstado]",cEntidad);

			// Llamado 2 empleado para oficina y depto fijos destino externo
			return new TDGeneral().generaOficioWord(cFolio,
					Integer.parseInt(cCveOfic,10),
					Integer.parseInt(cCveDepto,10),
					iCveOficina,
					iCveDepto,
					0,0,0,
					"","","","",
					true,"",vcCuerpo,
					true, vcCopiasPara,
					rep.getEtiquetasBuscar(),
					rep.getEtiquetasRemplazar());

		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getVectorDatos(true);
	}

	public Vector GenOpnExterna(String cFiltro,String cFolio,String cCveOfic,
			String cCveDepto) throws Exception{

		Vector vcRegsRep = new Vector();
		Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector();
		String cPuerto="",cEntidad="",cMunicipio="";
		int iCvePersona, iCveDomicilio;

		TWord rep = new TWord();
		TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
		//Integridad con el Modulo de Opiniones.
		dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cFiltro);
		dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());


		iCvePersona = dObtenDatosOpiniones.dDatosOficPer.getiCvePersona();
		iCveDomicilio = dObtenDatosOpiniones.dDatosOficPer.getiCveDomicilio();
		String cDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
		String cPuestoDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

		try{
			dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cFolio);
		} catch (Exception e){
			e.printStackTrace();
			cMensaje = e.getMessage();
		}

		String[] aValores = cFiltro.split(",");
		String cQuery1 = "SELECT P.CDSCPUERTO, EF.CNOMBRE AS CENTIDAD, M.CNOMBRE AS CMUNICIPIO " +
		"FROM GRLPUERTO P " +
		"JOIN GRLPAIS PA ON PA.ICVEPAIS = P.ICVEPAIS " +
		"JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = P.ICVEPAIS AND EF.ICVEENTIDADFED = P.ICVEENTIDADFED " +
		"JOIN GRLMUNICIPIO M ON M.ICVEPAIS = P.ICVEPAIS AND M.ICVEENTIDADFED = P.ICVEENTIDADFED AND " +
		"M.ICVEMUNICIPIO = P.ICVEMUNICIPIO " +
		"WHERE ICVEPUERTO = "+ aValores[4] ;


		vcRegsRep = GenSQLGenerico(vcRegsRep,cQuery1);

		if(vcRegsRep.size() > 0){
			TVDinRep vDatosRep = (TVDinRep) vcRegsRep.get(0);
			cPuerto = vDatosRep.getString("cDscPuerto").toLowerCase();
			cEntidad = vDatosRep.getString("cEntidad").toLowerCase();
			cMunicipio = vDatosRep.getString("cMunicipio").toLowerCase();

			rep.comRemplaza("[cPuerto]",cPuerto);
			rep.comRemplaza("[cMunicipioPto]",cMunicipio);
			rep.comRemplaza("[cEstado]",cEntidad);
			rep.comRemplaza("[cDirigidoA]",cDirigidoA);
			rep.comRemplaza("[cPuestoDirigidoA]",cPuestoDirigidoA);

			// Llamado 2 empleado para oficina y depto fijos destino externo
			return new TDGeneral().generaOficioWord(cFolio,
					Integer.parseInt(cCveOfic,10),
					Integer.parseInt(cCveDepto,10),
					0,0,
					iCvePersona,iCveDomicilio,0,
					"","","","",
					true,"",vcCuerpo,
					true, vcCopiasPara,
					rep.getEtiquetasBuscar(),
					rep.getEtiquetasRemplazar());

		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getVectorDatos(true);
	}

	public Vector GenSQLGenerico(Vector vRegistros,String SentenciaSQL) throws
	Exception{
		try{
			vRegistros = super.FindByGeneric("",SentenciaSQL,dataSourceName);
		} catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		} catch(Exception ex2){
			ex2.printStackTrace();
			cMensaje += ex2.getMessage();
		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);

		return vRegistros;
	}

	private String minusculas(String cNom){
		StringTokenizer st = new StringTokenizer(cNom);
		cNom = "";
		while (st.hasMoreTokens()) {
			String cToken = st.nextToken();
			cNom = cNom  + ' ' +
			cToken.charAt(0) +
			cToken.substring(1,cToken.length()).toLowerCase();
		}
		return cNom;
	}

	private void setDatosFolio(String cNumFolio){
		String[] cDatosFolio = cNumFolio.split("/");
		String cTemp = cDatosFolio[0].replace('.', '/');
		String[] cDigitosTemp = cTemp.split("/");
		int Ejercicio = Integer.parseInt(cDatosFolio[cDatosFolio.length-1], 10);
		int Consecutivo = Integer.parseInt(cDigitosTemp[cDigitosTemp.length-1], 10);
		String Digitos = "";
		for(int i=0; i<cDigitosTemp.length-1; i++){
			Digitos += cDigitosTemp[i];
			if (i<cDigitosTemp.length-2)
				Digitos += ".";
		}
		iEjercicioOficio = Ejercicio;
		cDigitosFolio = Digitos;
		iConsecutivo = Consecutivo;
	}


}



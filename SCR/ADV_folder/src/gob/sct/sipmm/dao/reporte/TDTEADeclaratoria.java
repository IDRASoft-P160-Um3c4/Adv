package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.*;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDTERDeclaratoria.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame.
 * @version 1.0
 */

public class TDTEADeclaratoria extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	public TDTEADeclaratoria(){
	}

	public Vector genDeclaratoria(String cFiltro, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TWord rep = new TWord();
		TFechas Fecha = new TFechas("44");
		int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0;
		String cAsunto="";
		TLetterNumberFormat tLetterNumberFormat = new TLetterNumberFormat();
		TDObtenDatos dObten = new TDObtenDatos();
		TDTEAFolio dFolio = new TDTEAFolio();
		dObten.dFolio.setDatosFolio(cNumFolio);
		

		//Informacion de la Solicitud, del Registro de Contratos.
		try{
			vRegs = super.FindByGeneric("", "select TEATerminacionAnticipada.iCveTermAnticipada, " +
					"TEATerminacionAnticipada.iCveTipoTerminacion, " +
					"TEATerminacionAnticipada.dtDocumento, " +
					"TEATerminacionAnticipada.cDocumento, " +
					"TEATerminacionAnticipada.cNumTermAnticipada, " +
					"TEATerminacionAnticipada.dtTermAnticipada, " +
					"CPATitulo.iCveTitulo, " +
					"CPATitulo.iCveTipoTitulo, " +
					"CPATitulo.cNumTitulo, " +
					"CPATitulo.dtIniVigenciaTitulo, " +
					"CPATitulo.dtVigenciaTitulo, " +
					"CPATitulo.cObjetoTitulo, " +
					"CPATitulo.iCveUsoTitulo, " +
					"CPATitulo.cMontoInversion, " +
					"CPATitulo.dtPublicacionDOF, " +
					"CPATitulo.iCveClasificacion, " +
					"CPATitulo.cZonaFedAfectadaTerrestre, " +
					"CPATitulo.cZonaFedAfectadaAgua, " +

					"CPATitular.iCveTitular, " +
					"Titular.cNomRazonSocial || ' ' || Titular.cApPaterno || ' ' || Titular.cApMaterno as cNomTitular, " +
					"DomTitular.iCveDomicilio as iCveDomicilio, " +
					"CPAAcreditacionRepLegal.iCveRepLegal," +
					"RepLegal.cNomRazonSocial || ' ' || RepLegal.cApPaterno || ' ' || RepLegal.cApMaterno as cNomRepLegal, " +

					"CPATituloUbicacion.cCalleTitulo, " +
					"CPATituloUbicacion.cKm, " +
					"CPATituloUbicacion.cColoniaTitulo, " +
					"CPATituloUbicacion.iCvePais, " +
					"GRLPais.cNombre as cNomPais, " +
					"CPATituloUbicacion.iCveEntidadFed, " +
					"GRLEntidadFed.cNombre as cNomEntidadFed, " +
					"GRLEntidadFed.cAbreviatura, " +
					"CPATituloUbicacion.iCveMunicipio, " +
					"GRLMunicipio.cNombre as cNomMunicipio, " +
					"CPATituloUbicacion.iCveLocalidad, " +
					"GRLLocalidad.cNombre as cNomLocalidad, " +

					"TEALiquidacion.dtDocumento as dtLiquidacion, " +
					"TEALiquidacion.cDocumento, " +
					"TEALiquidacion.cNumDocumento, " +
					"TEALiquidacion.cLiquidador, " +
					"TEALiquidacion.cTestigo, " +
					"TEAActaDefuncion.cNumActaDefuncion, " +
					"TEAActaDefuncion.dtDefuncion, " +
					"TEAActaDefuncion.iCveJuez, " +
					"TEAActaDefuncion.cNumLibroActa, " +
					"TEAActaDefuncion.cNumLibroActa, " +
					"TEAActaDefuncion.cNumJuzgado " +
					"from TEATerminacionAnticipada " +
					"join CPATitulo on CPATitulo.iCveTitulo = TEATerminacionAnticipada.iCveTitulo " +
					"join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo " +
					"join GRLPersona Titular on Titular.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio DomTitular on DomTitular.iCvePersona = Titular.iCvePersona " +
					"and DomTitular.lPredeterminado = 1 " +
					"left join CPAAcreditacionRepLegal on CPAAcreditacionRepLegal.iCveTitulo = CPATitulo.iCveTitulo " +
					"left join GRLPersona RepLegal on RepLegal.iCvePersona = CPAAcreditacionRepLegal.iCveRepLegal " +
					"left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
					"and CPATituloUbicacion.iNumMovUbicacion = 1 " +
					"left join GRLPais on GRLPais.iCvePais = CPATituloUbicacion.iCvePais " +
					"left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloUbicacion.iCvePais " +
					"and GRLEntidadFed.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
					"left join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloUbicacion.iCvePais " +
					"and GRLMunicipio.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
					"and GRLMunicipio.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
					"left join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloUbicacion.iCvePais " +
					"and GRLLocalidad.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
					"and GRLLocalidad.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
					"and GRLLocalidad.iCveLocalidad = CPATituloUbicacion.iCveLocalidad " +

					"left join TEALiquidacion on TEALiquidacion.iCveTitulo = TEATerminacionAnticipada.iCveTitulo " +
					"left join TEAActaDefuncion on TEAActaDefuncion.iCvePersona = Titular.iCvePersona " +
					"where TEATerminacionAnticipada.iCveTermAnticipada = " + cFiltro,
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Asunto del Oficio.
		cAsunto = "Declaratoria de Terminación Anticipada";
		rep.iniciaReporte();
		if (!vRegs.isEmpty()){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);


			iCvePersona = vDatos.getInt("iCveTitular");
			iCveDomicilio = vDatos.getInt("iCveDomicilio");
			iCveRepLegal = vDatos.getInt("iCveRepLegal");

			try{
				rep.comRemplaza("[dtEscritoLetra]","" +
						tLetterNumberFormat.getCantidad( (long) Fecha.getIntDay(vDatos.getDate("dtDocumento")))
						+ " de " +
						"" + Fecha.getMonthName(vDatos.getDate("dtDocumento")) + " del año " +
						"" +
						tLetterNumberFormat.getCantidad( (long) Fecha.getIntYear(vDatos.getDate("dtDocumento"))));
				rep.comRemplaza("[dtTituloLetra]","" +
						tLetterNumberFormat.getCantidad( (long) Fecha.getIntDay(vDatos.getDate("dtPublicacionDOF")))
						+ " de " +
						"" + Fecha.getMonthName(vDatos.getDate("dtPublicacionDOF")) + " del año " +
						"" +
						tLetterNumberFormat.getCantidad( (long) Fecha.getIntYear(vDatos.getDate("dtPublicacionDOF"))));

			} catch (Exception ex){
				ex.printStackTrace();
			}

			rep.comRemplaza("[cObjetoTitulo]",vDatos.getString("cObjetoTitulo").toLowerCase());
			rep.comRemplaza("[cSupTierra]",vDatos.getString("cZonaFedAfectadaTerrestre").toLowerCase());
			rep.comRemplaza("[cSupAgua]",vDatos.getString("cZonaFedAfectadaAgua").toLowerCase());

			rep.comRemplaza("[cLocalidad]",vDatos.getString("cNomLocalidad").toLowerCase());
			rep.comRemplaza("[cDscMunicipio]",vDatos.getString("cNomMunicipio").toLowerCase());
			rep.comRemplaza("[cDscEntidadFed]",vDatos.getString("cNomEntidadFed").toLowerCase());

		}



		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
				0,0,
				iCvePersona,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		TVDinRep vInsertaFolio = new TVDinRep();
		vInsertaFolio.put("iCveTermAnticipada", cFiltro);
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


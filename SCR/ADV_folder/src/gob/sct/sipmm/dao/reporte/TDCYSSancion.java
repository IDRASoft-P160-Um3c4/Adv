package gob.sct.sipmm.dao.reporte;

import gob.sct.sipmm.dao.TDCPATitulo1;
import gob.sct.sipmm.dao.TDCYSAutImpactoAmbiental;
import gob.sct.sipmm.dao.TDCYSAutInicioConstruccion;
import gob.sct.sipmm.dao.TDCYSAutInicioOperacion;
import gob.sct.sipmm.dao.TDCYSAutSenalamientoMaritimo;
import gob.sct.sipmm.dao.TDCYSComiteOperacion;
import gob.sct.sipmm.dao.TDCYSCompromisosCalidad;
import gob.sct.sipmm.dao.TDCYSConcesionZonaFederal;
import gob.sct.sipmm.dao.TDCYSConservacionMantto;
import gob.sct.sipmm.dao.TDCYSDerechoExplotacion;
import gob.sct.sipmm.dao.TDCYSDerechoSenalamiento;
import gob.sct.sipmm.dao.TDCYSEdoFinanciero;
import gob.sct.sipmm.dao.TDCYSFianza;
import gob.sct.sipmm.dao.TDCYSFolioProc;
import gob.sct.sipmm.dao.TDCYSOficinaQuejas;
import gob.sct.sipmm.dao.TDCYSOtorgamiento;
import gob.sct.sipmm.dao.TDCYSPolizaSeguro;
import gob.sct.sipmm.dao.TDCYSProgramaMaestroDP;
import gob.sct.sipmm.dao.TDCYSProgramaModernizacion;
import gob.sct.sipmm.dao.TDCYSProgramaOperativoAnual;
import gob.sct.sipmm.dao.TDCYSProgramaSustTitulos;
import gob.sct.sipmm.dao.TDCYSPubConcesion;
import gob.sct.sipmm.dao.TDCYSRegPubPropiedadEstatal;
import gob.sct.sipmm.dao.TDCYSRegistroPublicoPropiedadFederal;
import gob.sct.sipmm.dao.TDCYSReglasOperacion;
import gob.sct.sipmm.dao.TDCYSSistemaComputo;
import gob.sct.sipmm.dao.ws.TDActualizaRecargo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.DAOBase;
import com.micper.util.TExcel;
import com.micper.util.TFechas;
import com.micper.util.TWord;

/**
 * <p>Title: TDCYSSancion.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame.
 * @author iCaballero
 * @version 1.0
 */

public class TDCYSSancion extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private TFechas    tFecha      = new TFechas("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
	private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
	private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
	private int iEjercicioOficio = 0;
	private String cDigitosFolio = "";
	private int iConsecutivo=0;

	public TDCYSSancion(){
	}

	public StringBuffer genInicioSancion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		StringBuffer sbRetorno = new StringBuffer();
		String[] aFiltros = cQuery.split(",");
		String cOtorgado="";
		int iCveTitulo = Integer.parseInt(aFiltros[0]);
		int iEjercicio = Integer.parseInt(aFiltros[1]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
		int iCveTitular = Integer.parseInt(aFiltros[3]);
		int iCveDomicilio = 0;
		int iCveRepLegal = 0;
		int iReng = 1;

		Vector vRegs = new Vector();
		Vector vRegsTituloProp = new Vector();
		Vector vRegsTituloDocumentos = new Vector();
		Vector vRegsTitPoliza = new Vector();
		Vector vRegsEscrituraConst = new Vector();
		Vector vRegsAcredRepLegal = new Vector();
		Vector vRegsTituloUbicacion = new Vector();
		Vector vRegsTituloSolicitud = new Vector();
		Vector vRegsTituloObras = new Vector();
		Vector vRegsTituloServPortuarios = new Vector();
		Vector vRegsTituloAnexos = new Vector();
		Vector vRegsTituloAnterior = new Vector();
		Vector vConZonFed = new Vector();
		Vector vAutImpAmbOp = new Vector();
		Vector vAutImpAmbConst = new Vector();
		Vector vAutIniConst = new Vector();
		Vector vAutIniOpe = new Vector();
		Vector vAutSenMar = new Vector();
		Vector vCompCal = new Vector();
		Vector vProMod = new Vector();
		Vector vConMantto = new Vector();
		Vector vPolSegObrasInstPort = new Vector();
		Vector vPolSegRespCiv = new Vector();
		Vector vPolSegEqu = new Vector();
		Vector vFianza = new Vector();
		Vector vPubConcesion = new Vector();
		Vector vRegPubProFed = new Vector();
		Vector vRegPubProEst = new Vector();
		Vector vSustTit = new Vector();
		Vector vProMaeDesPor = new Vector();
		Vector vExtProMaeDesPor = new Vector();
		Vector vProOpeAnual = new Vector();
		Vector vRegOpe = new Vector();
		Vector vComOpe = new Vector();
		Vector vOfiQue = new Vector();
		Vector vSisCom = new Vector();
		Vector vEdofin = new Vector();
		Vector vOtorgamiento = new Vector();
		Vector vDerSenalamiento = new Vector();
		Vector vDerExplotacion = new Vector();
		Vector vInfTitulo = new Vector();

		TVDinRep vDatosTitProp = new TVDinRep();

		TExcel rep = new TExcel();
		TFechas Fechas = new TFechas("44");
		TFechas Fecha = new TFechas("44");
		TDCPATitulo1 dCPATitulo1 = new TDCPATitulo1();
		TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
		//TDActualizaRecargo dActualizaRecargo = new TDActualizaRecargo();
		TDCYSConcesionZonaFederal dConZonFed = new TDCYSConcesionZonaFederal();
		TDCYSAutImpactoAmbiental dCYSAutImpactoAmbiental = new TDCYSAutImpactoAmbiental();
		TDCYSAutInicioConstruccion dCYSAutInicioConstruccion = new TDCYSAutInicioConstruccion();
		TDCYSAutInicioOperacion dCYSAutInicioOperacion = new TDCYSAutInicioOperacion();
		TDCYSAutSenalamientoMaritimo dCYSAutSenalamientoMaritimo = new TDCYSAutSenalamientoMaritimo();
		TDCYSCompromisosCalidad dCYSCompromisosCalidad = new TDCYSCompromisosCalidad();
		TDCYSProgramaModernizacion dCYSProgramaModernizacion = new TDCYSProgramaModernizacion();
		TDCYSConservacionMantto dCYSConservacionMantto = new TDCYSConservacionMantto();
		TDCYSPolizaSeguro dCYSPolizaSeguro = new TDCYSPolizaSeguro();
		TDCYSFianza dCYSFianza = new TDCYSFianza();
		TDCYSPubConcesion dCYSPubConcesion = new TDCYSPubConcesion();
		TDCYSRegistroPublicoPropiedadFederal dCYSRegistroPublicoPropiedadFederal = new TDCYSRegistroPublicoPropiedadFederal();
		TDCYSRegPubPropiedadEstatal dCYSRegPubPropiedadEstatal = new TDCYSRegPubPropiedadEstatal();
		TDCYSProgramaSustTitulos dCYSProgramaSustTitulos = new TDCYSProgramaSustTitulos();
		TDCYSProgramaMaestroDP dCYSProgramaMaestroDP = new TDCYSProgramaMaestroDP();
		TDCYSProgramaOperativoAnual dCYSProgramaOperativoAnual = new TDCYSProgramaOperativoAnual();
		TDCYSReglasOperacion dCYSReglasOperacion = new TDCYSReglasOperacion();
		TDCYSComiteOperacion dCYSComiteOperacion = new TDCYSComiteOperacion();
		TDCYSOficinaQuejas dCYSOficinaQuejas = new TDCYSOficinaQuejas();
		TDCYSSistemaComputo dCYSSistemaComputo = new TDCYSSistemaComputo();
		TDCYSEdoFinanciero dCYSEdoFinanciero = new TDCYSEdoFinanciero();
		TDCYSOtorgamiento dCYSOtorgamiento = new TDCYSOtorgamiento();
		TDCYSDerechoSenalamiento dCYSDerechoSenalamiento = new TDCYSDerechoSenalamiento();
		TDCYSDerechoExplotacion dCYSDerechoExplotacion = new TDCYSDerechoExplotacion();

		java.sql.Date dtConcesion = null;
		java.sql.Date dtHoy = Fechas.TodaySQL();

		this.setDatosFolio(cNumFolio);

		try{
			vInfTitulo = dCPATitulo1.findByInfTitulo(iCveTitulo);

			if (!vInfTitulo.isEmpty()){
				TVDinRep vDinRep = (TVDinRep) vInfTitulo.get(0);
				iCveDomicilio = vDinRep.getInt("iCveDomicilio");
			}


		} catch (Exception ex){
			ex.printStackTrace();
		}

		StringBuffer sbQuery = new StringBuffer();

		sbQuery.append("select CPATitulo.iCveTitulo, ")
		.append("GRLPersona.iTipoPersona, ")
		.append("GRLPersona.cNomRazonSocial, ")
		.append("GRLPersona.cApPaterno, ")
		.append("GRLPersona.cApMaterno, ")
		.append("GRLPersona.cRFC, ")
		.append("CPAActaNacimiento.cNumActaNacimiento, ")
		.append("CPAActaNacimiento.dtNacimiento, ")
		.append("CPAActaNacimiento.cNumJuzgado, ")
		.append("Juez.cNomRazonSocial cNomJuez, ")
		.append("Juez.cApPaterno cApPatJuez, ")
		.append("Juez.cApMaterno cApMatJuez, ")
		.append("CPAActaNacimiento.cNumLibroActa, ")
		.append("CPAActaNacimiento.cNumFojaActa, ")
		.append("GRLDomicilio.cCalle, ")
		.append("GRLDomicilio.cNumExterior, ")
		.append("GRLDomicilio.cNumInterior, ")
		.append("GRLDomicilio.cColonia, ")
		.append("GRLDomicilio.cCodPostal, ")
		.append("GRLDomicilio.cTelefono, ")
		.append("GRLPais.cNombre as cNomPais, ")
		.append("GRLEntidadFed.cNombre as cNomEntidadFed, ")
		.append("GRLMunicipio.cNombre as cNomMunicipio, ")
		.append("GRLLocalidad.cNombre as cNomLocalidad, ")
		.append("CPATitulo.cNumTitulo, ")
		.append("CPATitulo.cNumTituloAnterior, ")
		.append("CPATitulo.dtIniVigenciaTitulo, ")
		.append("CPATitulo.cZonaFedAfectadaTerrestre, ")
		.append("CPATitulo.cZonaFedAfectadaAgua, ")
		.append("CPATitulo.cObjetoTitulo, ")
		.append("CPATitulo.cMontoInversion, ")
		.append("CPATitulo.dtPublicacionDOF, ")
		.append("CPATitulo.iCveClasificacion, ")
		.append("CPAUsoTitulo.iCveUsoTitulo, ")
		.append("CPATitulo.iCveTituloAnterior, ")
		.append("CPATitulo.dtVigenciaTitulo ")
		.append("from CPATitulo ")
		.append("join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo ")
		.append("join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo ")
		.append("join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo ")
		.append("join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular ")
		.append("left join CPAActaNacimiento on CPAActaNacimiento.iCvePersona = GRLPersona.iCvePersona ")
		.append("left join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona ")
		.append("and GRLDomicilio.lPredeterminado = 1 ")
		.append("left join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais ")
		.append("left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais ")
		.append("and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ")
		.append("left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais ")
		.append("and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ")
		.append("and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio ")
		.append("left join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais ")
		.append("and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ")
		.append("and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio ")
		.append("and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad ")
		.append("left join GRLPersona Juez on Juez.iCvePersona = CPAActaNacimiento.iCveJuez ")
		.append("where CPATitulo.iCveTitulo = " + iCveTitulo);

		try{
			vRegs = super.FindByGeneric("", sbQuery.toString(), dataSourceName);
		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
			cMensaje += ex2.getMessage();
		}

		if (vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			//int iCveTitulo = Integer.parseInt(vDatos.get("iCveTitulo").toString());
			int iCveTituloAnt = vDatos.getInt("iCveTituloAnterior");


			// Escritura Constitutiva de las Personas Morales.
			StringBuffer sb = new StringBuffer();
			sb.append(" select CPAEscConsMov.iNumMovimiento, ");
			sb.append(" CPAEscConsMov.cNumEscrituraConst, ");
			sb.append(" CPATipoMovEscritura.cDscTipoMovEscritura, ");
			sb.append(" CPAEscConsMov.dtEscrituraMov, ");
			sb.append(" CPAEscConsMov.cNumNotariaPub, ");
			sb.append(" GRLPersona.cNomRazonSocial, ");
			sb.append(" GRLPersona.cApPaterno, ");
			sb.append(" GRLPersona.cApMaterno, ");
			sb.append(" CPARegPubComercio.dtRegPubComercio, ");
			sb.append(" CPARegPubComercio.cNumRegPubComercio, ");
			sb.append(" CPARegPubComercio.cNumFoja, ");
			sb.append(" CPARegPubComercio.cNumTomo, ");
			sb.append(" CPARegPubComercio.cNumSeccion, ");
			sb.append(" CPARegPubComercio.cNumLibro, ");
			sb.append(" CPARegPubComercio.cNumVolumen, ");
			sb.append(" CPARegPubComercio.cNumSeccion, ");
			sb.append(" GRLPais.cNombre as cDscPais, ");
			sb.append(" GRLEntidadFed.cNombre as cDscEntidadFed, ");
			sb.append(" GRLMunicipio.cNombre as cDscMunicipio, ");
			sb.append(" GRLLocalidad.cNombre as cDscLocalidad ");
			sb.append(" from CPATitulo ");
			sb.append(" join CPAEscConsMov on CPAEscConsMov.iCveTitulo = CPATitulo.iCveTitulo ");
			sb.append(" join CPATipoMovEscritura on CPATipoMovEscritura.iCveTipoMovEscritura = CPAEscConsMov.iCveTipoMovEscritura ");
			sb.append(" left join CPARegPubComercio on CPARegPubComercio.iCveTitulo = CPAEscConsMov.iCveTitulo ");
			sb.append(" and CPARegPubComercio.iNumMovimiento = CPAEscConsMov.iNumMovimiento ");
			sb.append(" left join GRLPersona on GRLPersona.iCvePersona = CPAEscConsMov.iCveNotarioPublico ");
			sb.append(" left join GRLPais on GRLPais.iCvePais = CPARegPubComercio.iCvePais ");
			sb.append(" left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPARegPubComercio.iCvePais ");
			sb.append(" and GRLEntidadFed.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed ");
			sb.append(" left join GRLMunicipio on GRLMunicipio.iCvePais = CPARegPubComercio.iCvePais ");
			sb.append(" and GRLMunicipio.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed ");
			sb.append(" and GRLMunicipio.iCveMunicipio = CPARegPubComercio.iCveMunicipio ");
			sb.append(" left join GRLLocalidad on GRLLocalidad.iCvePais = CPARegPubComercio.iCvePais ");
			sb.append(" and GRLLocalidad.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed ");
			sb.append(" and GRLLocalidad.iCveMunicipio = CPARegPubComercio.iCveMunicipio ");
			sb.append(" and GRLLocalidad.iCveLocalidad = CPARegPubComercio.iCveLocalidad ");
			sb.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsEscrituraConst = super.FindByGeneric("",sb.toString()	,dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Acreditacion del Representante Legal.
			StringBuffer sbAcredRL = new StringBuffer();
			sbAcredRL.append(" select CPAAcreditacionRepLegal.dtAcredRepLegal, ")
			.append(" CPAAcreditacionRepLegal.cNumNotPubAcred, ")
			.append(" CPAAcreditacionRepLegal.cDocumento, ")
			.append(" GRLPersona.iCvePersona iCveRepLegal, ")
			.append(" GRLPersona.cNomRazonSocial, ")
			.append(" GRLPersona.cApPaterno, ")
			.append(" GRLPersona.cApMaterno, ")
			.append(" Notario.cNomRazonSocial as cNomNotario, ")
			.append(" Notario.cApPaterno as cApPatNotario, ")
			.append(" Notario.cApMaterno as cApMatNotario, ")
			.append(" GRLPais.cNombre as cNomPais, ")
			.append(" GRLEntidadFed.cNombre as cNomEntidadFed, ")
			.append(" GRLMunicipio.cNombre as cNomMunicipio, ")
			.append(" GRLLocalidad.cNombre as cNomLocalidad ")
			.append(" from CPATitulo ")
			.append(" join CPAAcreditacionRepLegal on CPAAcreditacionRepLegal.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" join GRLPersona on GRLPersona.iCvePersona = CPAAcreditacionRepLegal.iCveRepLegal ")
			.append(" join GRLPersona Notario on Notario.iCvePersona = CPAAcreditacionRepLegal.iCveNotarioPubAcred ")
			.append(" join GRLDomicilio on GRLDomicilio.iCvePersona = Notario.iCvePersona ")
			.append(" and GRLDomicilio.lPredeterminado = 1 ")
			.append(" join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais ")
			.append(" join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais ")
			.append(" and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ")
			.append(" join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais ")
			.append(" and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ")
			.append(" and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio ")
			.append(" join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais ")
			.append(" and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed ")
			.append(" and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio ")
			.append(" and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsAcredRepLegal = super.FindByGeneric("",sbAcredRL.toString(),
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Infomacion del Titulo de Propiedad
			StringBuffer sbTituloProp = new StringBuffer();
			sbTituloProp.append(" select CPATituloPropiedad.cSuperficiePropiedad, ")
			.append(" CPATituloPropiedad.cLotePropiedad, ")
			.append(" CPATituloPropiedad.cSeccionPropiedad, ")
			.append(" CPATituloPropiedad.cManzanaPropiedad, ")
			.append(" CPATituloPropiedad.cNumOficialPropiedad, ")
			.append(" CPATituloPropiedad.cCallePropiedad, ")
			.append(" CPATituloPropiedad.cKmPropiedad, ")
			.append(" CPATituloPropiedad.cColoniaPropiedad, ")
			.append(" GRLPuerto.cDscPuerto, ")
			.append(" GRLPais.cNombre as cDscPais, ")
			.append(" GRLEntidadFed.cNombre as cDscEntidadFed, ")
			.append(" GRLMunicipio.cNombre as cDscMunicipio, ")
			.append(" GRLLocalidad.cNombre as cDscLocalidad, ")
			.append(" CPATitProEscrituraPublica.cNumTitProEscPublica, ")
			.append(" CPATitProEscrituraPublica.dtEscPublica, ")
			.append(" CPATitProEscrituraPublica.cNumNotariaPublica, ")
			.append(" Notario.cNomRazonSocial, ")
			.append(" Notario.cApPaterno, ")
			.append(" Notario.cApMaterno, ")
			.append(" PaisEscPub.cNombre as cDscPaisEscPub, ")
			.append(" EntidadEscPub.cNombre as cDscEntidadFedEscPub, ")
			.append(" MunicipioEscPub.cNombre as cDscMunicipioEscPub, ")
			.append(" LocalidadEscPub.cNombre as cDscLocalidadEscPub, ")
			.append(" CPATitProRegPubCom.dtTitProRegPubCom, ")
			.append(" CPATitProRegPubCom.cNumTitProRegPubCom, ")
			.append(" CPATitProRegPubCom.cNumRegPubComFoja, ")
			.append(" CPATitProRegPubCom.cNumRegPubComTomo, ")
			.append(" CPATitProRegPubCom.cNumRegPubComSeccion, ")
			.append(" CPATitProRegPubCom.cNumRegPubComLibro, ")
			.append(" CPATitProRegPubCom.cNumRegPubComVolumen, ")
			.append(" PaisRegPubCom.cNombre as cDscPaisRegPubCom, ")
			.append(" EntidadRegPubCom.cNombre as cDscEntidadFedRegPubCom, ")
			.append(" MunicipioRegPubCom.cNombre as cDscMunicipioRegPubCom, ")
			.append(" LocalidadRegPubCom.cNombre as cDscLocalidadRegPubCom ")
			.append(" from CPATitulo ")
			.append(" join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" join CPATituloPropiedad on CPATituloPropiedad.iCveTitulo = CPATitular.iCveTitulo ")
			.append(" and CPATituloPropiedad.iCveTitular = CPATitular.iCveTitular ")
			.append(" left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloPropiedad.iCvePuerto ")
			.append(" join GRLPais on GRLPais.iCvePais = CPATituloPropiedad.iCvePais ")
			.append(" join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloPropiedad.iCvePais ")
			.append(" and GRLEntidadFed.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed ")
			.append(" join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloPropiedad.iCvePais ")
			.append(" and GRLMunicipio.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed ")
			.append(" and GRLMunicipio.iCveMunicipio = CPATituloPropiedad.iCveMunicipio ")
			.append(" join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloPropiedad.iCvePais ")
			.append(" and GRLLocalidad.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed ")
			.append(" and GRLLocalidad.iCveMunicipio = CPATituloPropiedad.iCveMunicipio ")
			.append(" and GRLLocalidad.iCveLocalidad = CPATituloPropiedad.iCveLocalidad ")
			.append(" left join CPATitProEscrituraPublica on CPATitProEscrituraPublica.iCveTitulo = CPATituloPropiedad.iCveTitulo ")
			.append(" and CPATitProEscrituraPublica.iCveTitular = CPATituloPropiedad.iCveTitular ")
			.append(" and CPATitProEscrituraPublica.iNumTituloPropiedad = CPATituloPropiedad.iNumTituloPropiedad ")
			.append(" left join GRLPersona Notario on Notario.iCvePersona = CPATitProEscrituraPublica.iCveNotarioPub ")
			.append(" left join GRLPais PaisEscPub on PaisEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais ")
			.append(" left join GRLEntidadFed EntidadEscPub on EntidadEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais ")
			.append(" and EntidadEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed ")
			.append(" left join GRLMunicipio MunicipioEscPub on MunicipioEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais ")
			.append(" and MunicipioEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed ")
			.append(" and MunicipioEscPub.iCveMunicipio = CPATitProEscrituraPublica.iCveMunicipio ")
			.append(" left join GRLLocalidad LocalidadEscPub on LocalidadEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais ")
			.append(" and LocalidadEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed ")
			.append(" and LocalidadEscPub.iCveMunicipio = CPATitProEscrituraPublica.iCveMunicipio ")
			.append(" and LocalidadEscPub.iCveLocalidad = CPATitProEscrituraPublica.iCveLocalidad ")
			.append(" left join CPATitProRegPubCom on CPATitProRegPubCom.iCveTitulo = CPATitProEscrituraPublica.iCveTitulo ")
			.append(" and CPATitProRegPubCom.iCveTitular = CPATitProEscrituraPublica.iCveTitular ")
			.append(" and CPATitProRegPubCom.iNumTituloPropiedad = CPATitProEscrituraPublica.iNumTituloPropiedad ")
			.append(" left join GRLPais PaisRegPubCom on PaisRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais ")
			.append(" left join GRLEntidadFed EntidadRegPubCom on EntidadRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais ")
			.append(" and EntidadRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed ")
			.append(" left join GRLMunicipio MunicipioRegPubCom on MunicipioRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais ")
			.append(" and MunicipioRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed ")
			.append(" and MunicipioRegPubCom.iCveMunicipio = CPATitProRegPubCom.iCveMunicipio ")
			.append(" left join GRLLocalidad LocalidadRegPubCom on LocalidadRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais ")
			.append(" and LocalidadRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed ")
			.append(" and LocalidadRegPubCom.iCveMunicipio = CPATitProRegPubCom.iCveMunicipio ")
			.append(" and LocalidadRegPubCom.iCveLocalidad = CPATitProRegPubCom.iCveLocalidad ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsTituloProp = super.FindByGeneric("",sbTituloProp.toString(),	dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de la Ubicacion del Area Solicitada
			StringBuffer sbTituloUbicacion = new StringBuffer();
			sbTituloUbicacion.append(" select CPATituloUbicacion.lDentroRecintoPort, ")
			.append(" CPATituloUbicacion.cCalleTitulo, ")
			.append(" CPATituloUbicacion.cKm, ")
			.append(" CPATituloUbicacion.cColoniaTitulo, ")
			.append(" GRLPuerto.cDscPuerto, ")
			.append(" GRLPais.cNombre as cDscPais, ")
			.append(" GRLEntidadFed.cNombre as cDscEntidadFed, ")
			.append(" GRLMunicipio.cNombre as cDscMunicipio, ")
			.append(" GRLLocalidad.cNombre as cDscLocalidad ")
			.append(" from CPATitulo ")
			.append(" join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto ")
			.append(" left join GRLPais on GRLPais.iCvePais = CPATituloUbicacion.iCvePais ")
			.append(" left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloUbicacion.iCvePais ")
			.append(" and GRLEntidadFed.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed ")
			.append(" left join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloUbicacion.iCvePais ")
			.append(" and GRLMunicipio.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed ")
			.append(" and GRLMunicipio.iCveMunicipio = CPATituloUbicacion.iCveMunicipio ")
			.append(" left join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloUbicacion.iCvePais ")
			.append(" and GRLLocalidad.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed ")
			.append(" and GRLLocalidad.iCveMunicipio = CPATituloUbicacion.iCveMunicipio ")
			.append(" and GRLLocalidad.iCveLocalidad = CPATituloUbicacion.iCveLocalidad ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsTituloUbicacion = super.FindByGeneric("",sbTituloUbicacion.toString(),dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de la Solicitud
			StringBuffer sbTituloSolicitud = new StringBuffer();
			sbTituloSolicitud.append(" select CPASolTitulo.iEjercicio, ")
			.append(" CPASolTitulo.iNumSolicitud, ")
			.append(" TRACatTramite.cDscBreve, ")
			.append(" TRAModalidad.cDscModalidad, ")
			.append(" date(TRARegSolicitud.tsRegistro) as dtRegistro, ")
			.append(" GRLOficina.cCalleyNo, ")
			.append(" GRLOficina.cColonia, ")
			.append(" GRLOficina.cCodPostal, ")
			.append(" GRLOficina.cTelefono, ")
			.append(" GRLOficina.cCorreoE, ")
			.append(" GRLPais.cNombre as cDscPais, ")
			.append(" GRLEntidadFed.cNombre as cDscEntidadFed, ")
			.append(" GRLMunicipio.cNombre as cDscMunicipio, ")
			.append(" TRACatTramite.iCveTramite, ")
			.append(" TRAModalidad.iCveModalidad ")
			.append(" from CPATitulo ")
			.append(" join CPASolTitulo on CPASolTitulo.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" join TRARegSolicitud on TRARegSolicitud.iEjercicio = CPASolTitulo.iEjercicio ")
			.append(" and TRARegSolicitud.iNumSolicitud = CPASolTitulo.iNumSolicitud ")
			.append(" join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite ")
			.append(" join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad ")
			.append(" join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina ")
			.append(" join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais ")
			.append(" join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais ")
			.append(" and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed ")
			.append(" join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais ")
			.append(" and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed ")
			.append(" and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsTituloSolicitud = super.FindByGeneric("",sbTituloSolicitud.toString(),dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de las Obras del Título.
			StringBuffer sbTituloObra = new StringBuffer();
			sbTituloObra.append(" select CPATituloObra.cDscObra, ")
			.append(" CPATituloObra.lPropiedadNacional, ")
			.append(" CPATituloObra.lConstruccion, ")
			.append(" CPATituloObra.lOperacion, ")
			.append(" CPATituloObra.cTiempoEstEjecucion, ")
			.append(" CPATituloObra.dtIniObra ")
			.append(" from CPATitulo ")
			.append(" join CPATituloObra on CPATituloObra.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsTituloObras = super.FindByGeneric("",sbTituloObra.toString(),dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de Servicios Portuarios Relacionados con los Títulos.
			StringBuffer sbTituloSerPort = new StringBuffer();
			sbTituloSerPort.append(" select CPAServicioPortuario.cDscServicioPortuario ")
			.append(" from CPATitulo ")
			.append(" join CPATituloServicioPortuario on CPATituloServicioPortuario.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" join CPAServicioPortuario on CPAServicioPortuario.iCveServicioPortuario = CPATituloServicioPortuario.iCveServicioPortuario ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsTituloServPortuarios = super.FindByGeneric("",sbTituloSerPort.toString(),dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de Documentos Relacionados con el Título.
			StringBuffer sbTituloDocto = new StringBuffer();
			sbTituloDocto.append(" select CPATituloDocumento.iCveTipoDocumento, ")
			.append(" CPATipoDocumento.cDscTipoDocumento, ")
			.append(" CPATituloDocumento.cNumDocumento, ")
			.append(" CPATituloDocumento.dtDocumento, ")
			.append(" CPATituloDocumento.dtVigenciaDocumento, ")
			.append(" GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cDepEmite, ")
			.append(" CPATituloDocumento.cObjetoDocumento, ")
			.append(" CPATituloDocumento.cSuperficieDocumento, ")
			.append(" CPATituloDocumento.lMaritimoTerrestre, ")
			.append(" CPAClasificacionDocumento.cDscClasificacionDocto ")
			.append(" from CPATitulo ")
			.append(" join CPATituloDocumento on CPATituloDocumento.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" join CPATipoDocumento on CPATipoDocumento.iCveTipoDocumento = CPATituloDocumento.iCveTipoDocumento ")
			.append(" left join GRLPersona on GRLPersona.iCvePersona = CPATituloDocumento.iCveDepEmiteDoc ")
			.append(" left join CPAClasificacionDocumento on CPAClasificacionDocumento.iCveClasificacionDocto = CPATituloDocumento.iCveClasificacionDocto ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsTituloDocumentos = super.FindByGeneric("",sbTituloDocto.toString(),dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de los Anexos del Título.
			StringBuffer sbTituloAnexo = new StringBuffer();
			sbTituloAnexo.append(" select CPAAnexo.cDscAnexo, ")
			.append(" CPAAnexoTramite.iOrden, ")
			.append(" CPAAnexo.iNivel, ")
			.append(" CPAAnexo.iCveAnexo ")
			.append(" from CPATitulo ")
			.append(" join CPATituloAnexo on CPATituloAnexo.iCveTitulo = CPATitulo.iCveTitulo ")
			.append(" join CPAAnexoTramite on CPAAnexoTramite.iCveAnexo = CPATituloAnexo.iCveAnexo ")
			.append(" and CPAAnexoTramite.iCveTramite = CPATituloAnexo.iCveTramite ")
			.append(" and CPAAnexoTramite.iCveModalidad = CPATituloAnexo.iCveModalidad ")
			.append(" and CPAAnexoTramite.dtIniVigencia = CPATituloAnexo.dtIniVigencia ")
			.append(" join CPAAnexo on CPAAnexo.iCveAnexo = CPATituloAnexo.iCveAnexo ")
			.append(" where CPATitulo.iCveTitulo = " + iCveTitulo);
			try{
				vRegsTituloAnexos = super.FindByGeneric("",sbTituloAnexo.toString(),dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion del Titulo Anterior.
			if (iCveTituloAnt>0){
				StringBuffer sbTituloAnterior = new StringBuffer();
				sbTituloAnterior.append(" select CPATitulo.iCveTipoTitulo, ")
				.append(" CPATipoTitulo.cDscTipoTitulo, ")
				.append(" CPATitulo.cNumTitulo, ")
				.append(" CPATitulo.dtIniVigenciaTitulo, ")
				.append(" CPATitulo.dtVigenciaTitulo, ")
				.append(" CPATitulo.dtPublicacionDOF ")
				.append(" from CPATitulo ")
				.append(" join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo ")
				.append(" where CPATitulo.iCveTitulo = " + iCveTituloAnt);
				try{
					vRegsTituloAnterior = super.FindByGeneric("",sbTituloAnterior.toString(),dataSourceName);
				} catch(Exception ex){
					ex.printStackTrace();
					cMensaje += ex.getMessage();
				}
			}

			rep.iniciaReporte();


			//Encabezado
			rep.comDespliegaCombinado(cLeyendaEnc, "A", iReng, "K", iReng, rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR(), false, 0, false, false, 0, 0);
			iReng++;

			TDObtenDatos dObten = new TDObtenDatos();
			Vector vRutaRemitente = new Vector();
			vRutaRemitente = dObten.getOrganigrama(Integer.parseInt(cCveOficinaOrigen), Integer.parseInt(cCveDeptoOrigen), vRutaRemitente);
			for(int i=0; i<vRutaRemitente.size(); i++){
				rep.comDespliegaAlineado("F",iReng,"K",iReng,(String)vRutaRemitente.get(i),true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng++;
			}

			TDObtenDatos dObten2 = new TDObtenDatos();
			TDObtenDatos dObtenFolio = new TDObtenDatos();
			dObtenFolio.dFolio.setDatosFolio(cNumFolio);
			dObten2.dOficDepto.setOficinaDepto(Integer.parseInt(cCveOficinaOrigen), Integer.parseInt(cCveDeptoOrigen));
			String cMunicipioEmision = dObten2.dOficDepto.vDatoOfic.getNomMunicipio();
			String[] aEntidadRemplaza = cEntidadRemplazaTexto.split("/");
			String[] aDatos;
			for (int i=0; i<aEntidadRemplaza.length; i++){
				aDatos = aEntidadRemplaza[i].split(",");
				if (Integer.parseInt(aDatos[0],10) == dObten2.dOficDepto.vDatoOfic.getCvePais() &&
						Integer.parseInt(aDatos[1],10) == dObten2.dOficDepto.vDatoOfic.getCveEntidadFed()){
					cMunicipioEmision = aDatos[2];
					break;
				}
			}

			String cDatosEmision = cMunicipioEmision + "," + dObten2.dOficDepto.vDatoOfic.getAbrevEntidad() + ", a " +
			tFecha.getFechaDDMMMMMYYYY(dObtenFolio.dFolio.getAsignacion()," de ");
			rep.comDespliegaAlineado("F",iReng,"K",iReng,cDatosEmision,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;

			rep.comDespliegaAlineado("F",iReng,"K",iReng,"Asunto: Se Inicia Procedimiento Administrativo de Sanción.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;

			//Datos Destinatario
			TDObtenDatos dObten3 = new TDObtenDatos();
			String cNombrePersonaDest,cPuestoDest,cNombreEmpresaDest,cCalle,cNumExterior,
			cNumInterior,cColonia,cCodigoPostal,cEntidad,cMunicipio,cPais;
			cNombrePersonaDest = cPuestoDest = cNombreEmpresaDest = cCalle = cNumExterior = cNumInterior = cColonia = cCodigoPostal = cEntidad = cMunicipio = cPais = "";

			dObten3.dPersona.setPersona(iCveTitular,iCveDomicilio);
			cNombrePersonaDest = dObten3.dPersona.getNomCompleto();
			if (iCveRepLegal > 0 && dObten3.dPersona.getTipoPersona() > 1){
				TDObtenDatos dObten4 = new TDObtenDatos();
				dObten4.dPersona.setPersona(iCveRepLegal,0);
				cNombreEmpresaDest = cNombrePersonaDest;
				cPuestoDest = "REPRESENTANTE LEGAL DE";
				cNombrePersonaDest = dObten4.dPersona.getNomCompleto();
			}
			cCalle = dObten.dPersona.getCalle();
			cNumExterior = dObten3.dPersona.getNumExterior();
			if (!cNumExterior.equals(""))
				cNumExterior = "No. " + cNumExterior;
			cNumInterior = dObten3.dPersona.getNumInterior();
			if (!cNumInterior.equals(""))
				cNumInterior = " - " + cNumInterior;
			cColonia = dObten3.dPersona.getColonia();
			cCodigoPostal = dObten3.dPersona.getCodPostal();
			cMunicipio = dObten3.dPersona.getNomMunicipio();
			cEntidad = dObten3.dPersona.getAbrevEntidad() + ".";
			cPais = dObten3.dPersona.getNomPais();

			rep.comDespliegaAlineado("B",iReng,"G",iReng,cNombrePersonaDest,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("B",iReng,"G",iReng,cPuestoDest,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("B",iReng,"G",iReng,cNombreEmpresaDest,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("B",iReng,"G",iReng,cCalle + cNumExterior + cNumInterior,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("B",iReng,"G",iReng,"Col. " + cColonia + " C.P. " + cCodigoPostal,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("B",iReng,"G",iReng,cMunicipio + "," + cEntidad + "," + cPais,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("B",iReng,"G",iReng,"PRESENTE",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

			iReng+=2;
			if (vRegsTituloProp.size()>0)
				vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);

			rep.comDespliegaAlineado("B", iReng,"C",iReng,"TÍTULO",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng +=2;
			//Tipo de Concesion, Permiso o Autorización.
			int lConcesion=0,lPermiso=0,lAutorizacion=0;
			switch (vDatos.getInt("iCveClasificacion")){

			case(1):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Concesión: Puerto.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lConcesion=1;
				break;
			}
			//Terminal.
			case(2):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Concesión: Terminal.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lConcesion=1;
				break;
			}
			//Marina.
			case(3):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Concesión: Marina.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lConcesion=1;
				break;
			}
			//Instalación Portuaria.
			case(4):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Concesión: Instalación.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lConcesion=1;
				break;
			}
			//Permisos.
			//Embarcadero.
			case(5):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Permiso: Embarcadero.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lPermiso=1;
				break;
			}
			//Atracadero.
			case(6):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Permiso: Atracadero.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lPermiso=1;
				break;
			}
			//Botadero.
			case(7):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Permiso: Botadero.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lPermiso=1;
				break;
			}
			//Similar.
			case(8):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Permiso: Similar.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lPermiso=1;
				break;
			}
			//Servicio Portuario.
			case(9):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Permiso: Servicio Portuario.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lPermiso=1;
				break;
			}
			//Servicio Conexo.
			case(10):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Permiso: Servicio Conexo.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lPermiso=1;
				break;
			}
			//Autorizaciones.
			//Obra Marítima.
			case(11):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Autorización: Obra Marítima.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lAutorizacion=1;
				break;
			}
			//Dragado de Construcción.
			case(12):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Autorización: Dragado de Construcción.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lAutorizacion=1;
				break;
			}
			//Dragado de Mantenimiento.
			case(13):{
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"Autorización: Dragado de Mantenimiento.",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
				lAutorizacion=1;
				break;
			}
			}

			// Datos del Título

			rep.comDespliegaAlineado("C", iReng, "I", iReng,"No. de Título: " +vDatos.getString("cNumTitulo"),false,
					rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;

			// Titulo Anterior.
			TVDinRep vTituloAnt = new TVDinRep();
			if (vRegsTituloAnterior.size()>0)
				for(int i=0;i<vRegsTituloAnterior.size();i++)
					vTituloAnt = (TVDinRep) vRegsTituloAnterior.get(i);


			if(vTituloAnt.getInt("iCveTipoTitulo") == 1)
				cOtorgado = "Secretario de Comunicaciones y Transportes";
			else if (vTituloAnt.getInt("iCveTipoTitulo") == 2 || vTituloAnt.getInt("iCveTipoTitulo") == 3)
				cOtorgado = "Director General de Puertos.";

			if(vTituloAnt.getDate("dtPublicacionDOF")!=null){
				rep.comDespliegaAlineado("C", iReng, "I", iReng,"Fecha: " + Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtPublicacionDOF"),"/"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng++;
			}

			if(vTituloAnt.getDate("dtVigenciaTitulo")!=null){
				rep.comDespliegaAlineado("C", iReng, "I", iReng,"Vigencia: " + Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtVigenciaTitulo"),"/") +
						" a partir de: " + vTituloAnt.getDate("dtIniVigenciaTitulo")!=null?Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtIniVigenciaTitulo"),"/"):"",false,
								rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng++;
			}

			rep.comDespliegaAlineado("C", iReng, "I", iReng,"Para: " +vDatos.getString("cObjetoTitulo"),false,
					rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;

//			Ubicación del Area Solicitada.
			if (vRegsTituloUbicacion.size()>0){
				TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);

				rep.comDespliegaAlineado("B", iReng,"C",iReng,"Área: ",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng++;
				rep.comDespliegaAlineado("C", iReng,"H",iReng,"Ubicación del Área Solicitada: ",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng++;
				if(vDatos.get("cZonaFedAfectadaTerrestre")!= null)
					rep.comDespliegaAlineado("D", iReng,"E",iReng,vDatos.getString("cZonaFedAfectadaTerrestre"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				if(vDatos.get("cZonaFedAfectadaAgua") != null)
					rep.comDespliegaAlineado("F", iReng,"G",iReng,vDatos.getString("cZonaFedAfectadaAgua"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				iReng++;
				if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1)
					rep.comDespliegaAlineado("D", iReng,"G",iReng,"Dentro del Recinto Portuario",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				else
					rep.comDespliegaAlineado("D", iReng,"G",iReng,"Fuera del Recinto Portuario",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				iReng++;

			}

//			Construcciones e INstalaciones de Porpiedad
			int lPropNacional= 0,lConst=0,lOper=0;

			if (vRegsTituloObras.size()>0){
				rep.comDespliegaAlineado("B", iReng,"E",iReng,"Construcciones e instalaciones de propiedad: ",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng++;
				for(int i=0;i<vRegsTituloObras.size();i++){
					TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(i);

					if (vDatosTitObras.getInt("lPropiedadNacional") == 1 && lPropNacional == 0){
						rep.comDespliegaAlineado("C", iReng,"G",iReng,"Propiedad Nacional de acuerdo con los Artículos " +
								"36 y 67 de la Ley de Puertos.",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
						iReng++;
						lPropNacional = 1;
					}
					if (vDatosTitObras.getInt("lConstruccion") == 1 && lConst == 0){
						rep.comDespliegaAlineado("C", iReng,"G",iReng,"Construcción ",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
						iReng++;
						lConst = 1;
					}
					if (vDatosTitObras.getInt("lOperacion") == 1 && lOper == 0){
						rep.comDespliegaAlineado("C", iReng,"G",iReng,"Operación ",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
						iReng++;
						lOper = 1;
					}
				}
			}

//			MaritimoTerrestre
			if (vRegsTituloProp.size()>0){

				rep.comDespliega("C", iReng, "Frente a la zona federal maritimoterrestre, contigua al ");
				iReng++;

				rep.comDespliegaAlineado("C", iReng,"D",iReng,"Superficie: " + vDatosTitProp.get("cSuperficiePropiedad") + " m2",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("E", iReng, "F", iReng,"Lote: " +vDatosTitProp.get("cLotePropiedad"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("G", iReng, "H", iReng,"Sección: " +vDatosTitProp.get("cSeccionPropiedad"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("I", iReng, "J", iReng,"Manzana: " +vDatosTitProp.get("cManzanaPropiedad"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				iReng++;

				rep.comDespliegaAlineado("C", iReng,"D",iReng,"Número Oficial: " + vDatosTitProp.getString("cNumOficialPropiedad"),
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("E", iReng, "F", iReng,"Calle: " +vDatosTitProp.getString("cCallePropiedad"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("G", iReng, "H", iReng,"Km: " +vDatosTitProp.getString("cKmPropiedad"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("I", iReng, "J", iReng,"Colonia: " +vDatosTitProp.getString("cColoniaPropiedad"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				iReng++;

				rep.comDespliegaAlineado("C", iReng,"D",iReng,"Puerto: " + vDatosTitProp.getString("cDscPuerto"),
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("E", iReng, "F", iReng,"Municipio: " +vDatosTitProp.getString("cDscMunicipio"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				rep.comDespliegaAlineado("G", iReng, "J", iReng,"Entidad Federativa: " +vDatosTitProp.getString("cDscEntidadFed"),false,
						rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

				iReng++;

			}

			dtConcesion = vDatos.getDate("dtIniVigenciaTitulo");


		} else
			System.err.println("Sin valores en la base de Datos");

		//Concesion de Zona Federal Maritimo Terrestre.
		try{
			vConZonFed = dConZonFed.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		iReng++;
		rep.comDespliegaAlineado("B", iReng,"C",iReng,"Me refiero al título antes mencionado otorgado por el " + cOtorgado,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng+=2;
		String cTexto = "Sobre el particular, me permito manifestar a usted que, de acuerdo con las constancias que " +
				"obran en el expediente administrativo relativo al control y seguimiento del cumplimiento de las obligaciones " +
				"establecidas en el título antes señalado, aparece que no se encuentran cumplidas las siguientes condiciones.";

		rep.comDespliegaAlineado("B",iReng,"K",iReng + 3,cTexto,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
		rep.comAlineaRango("B",iReng,"K",iReng + 3,rep.getAT_HJUSTIFICA());

		iReng+=5;

		rep.comDespliegaAlineado("B", iReng,"C",iReng,"FALTA DE CUMPLIMIENTO DE OBLIGACIONES ",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng+=2;
		String cTraSMRT = "Trámite ante SEMARNAT: ", cNoSol = "No. Solicitud: ", cFechaSol = "Fecha de la Solicitud: ",
		cMotivo = "Motivo: ";
		if(!vConZonFed.isEmpty()){
			Object lValor = vConZonFed.get(0);
			if (vConZonFed.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia del Título de Concesión de la Zona Federal Marítimo Terrestre o Zona Federal Terrestre. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			} else {
				Object cNumSolZoFeMat = vConZonFed.get(1);
				Object dtSolZoFeMat = vConZonFed.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia del Título de Concesión de la Zona Federal Marítimo Terrestre o Zona Federal Terrestre. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + cNumSolZoFeMat.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + dtSolZoFeMat.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia del Título de Concesión de la Zona Federal Marítimo Terrestre o Zona Federal Terrestre. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Autorizacion de Impacto Ambiental para Operar la Obra.
		try{
			vAutImpAmbOp = dCYSAutImpactoAmbiental.findBySitObligacionOp("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutImpAmbOp.isEmpty()){
			Object lValor = vAutImpAmbOp.get(0);
			if (vAutImpAmbOp.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización en Materia de Impacto Ambiental para Operar. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			} else {
				Object cNumSolSemarnat = vAutImpAmbOp.get(1);
				Object dtSolSemarnat = vAutImpAmbOp.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización en Materia de Impacto Ambiental para Operar. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + cNumSolSemarnat.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + dtSolSemarnat.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización en Materia de Impacto Ambiental para Operar. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Autorizacion de Impacto Ambiental para Construir la Obra.
		try{
			vAutImpAmbConst = dCYSAutImpactoAmbiental.findBySitObligacionConst("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutImpAmbConst.isEmpty()){
			Object lValor = vAutImpAmbConst.get(0);
			if (vAutImpAmbConst.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización en Materia de Impacto Ambiental para Ejecutar la Obra. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			} else {
				Object cNumSolSemarnat = vAutImpAmbConst.get(1);
				Object dtSolSemarnat = vAutImpAmbConst.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización en Materia de Impacto Ambiental para Ejecutar la Obra. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + cNumSolSemarnat.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + dtSolSemarnat.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización en Materia de Impacto Ambiental para Ejecutar la Obra. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("C", iReng,"I",iReng,cTraSMRT,	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Autorizacion de Inicio de Construccion de la Obra.
		try{
			vAutIniConst = dCYSAutInicioConstruccion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutIniConst.isEmpty()){
			Object lValor = vAutIniConst.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización para Inicio de Construcción. ",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización para Inicio de Construcción. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Autorizacion de Inicio de Operación de la Obra.
		try{
			vAutIniOpe = dCYSAutInicioOperacion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutIniOpe.isEmpty()){
			Object lValor = vAutIniOpe.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización para Inicio de Operación. ",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}

		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización para Inicio de Operación. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Autorizacion de Senalamiento Marítimo.
		try{
			vAutSenMar = dCYSAutSenalamientoMaritimo.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutSenMar.isEmpty()){
			Object lValor = vAutSenMar.get(0);
			if (vAutSenMar.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización del Señalamiento Marítimo. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"Trámite ante la SCT - DGMM: ",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			} else {
				Object iNumSolTramite = vAutSenMar.get(1);
				Object dtRegistro = vAutSenMar.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización del Señalamiento Marítimo. ",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"Trámite ante la SCT - DGMM: ",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + iNumSolTramite.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + dtRegistro.toString(),	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Autorización del Señalamiento Marítimo. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"Trámite ante la SCT - DGMM: ",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cNoSol + "______",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cFechaSol + "______________________",	false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Compromisos de Calidad.
		try{
			vCompCal = dCYSCompromisosCalidad.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vCompCal.isEmpty()){
			Object lValor = vCompCal.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Compromisos de Calidad y Metas de Productividad. ",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Compromisos de Calidad y Metas de Productividad. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Programa de Modernización.
		try{
			vProMod = dCYSProgramaModernizacion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vProMod.isEmpty()){
			Object lValor = vProMod.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Programa de Modernización del Equipo. ",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Programa de Modernización del Equipo. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Conservación y Mantenimiento.
		try{
			vConMantto = dCYSConservacionMantto.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vConMantto.isEmpty()){
			Object lValor = vConMantto.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Reporte Anual de Conservación y Mantenimiento, con fotografías. ",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Reporte Anual de COnservación y Mantenimiento, con fotografías. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Poliza de Seguro.
		int iRengTemp = iReng;
		iReng++;
		boolean lMarcar = false;
		try{
			List lTipPolObras = new ArrayList();
			List lTipPolInst = new ArrayList();
			List lTipPolEqu = new ArrayList();
			lTipPolObras.add(new Integer("1"));
			lTipPolInst.add(new Integer("2"));
			lTipPolEqu.add(new Integer("3"));


			vPolSegObrasInstPort = dCYSPolizaSeguro.findBySitObligacion("",iCveTitulo,dtHoy,lTipPolObras);
			vPolSegRespCiv = dCYSPolizaSeguro.findBySitObligacion("",iCveTitulo,dtHoy,lTipPolInst);
			vPolSegEqu = dCYSPolizaSeguro.findBySitObligacion("",iCveTitulo,dtHoy,lTipPolEqu);

		} catch(Exception ex){
			ex.printStackTrace();
		}

		//Poliza de Seguro de Obras e Instalaciones Portuarias.
		if(!vPolSegObrasInstPort.isEmpty()){
			Object lValor = vPolSegObrasInstPort.get(1);
			if (vPolSegObrasInstPort.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Obras e Instalaciones Portuarias",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
					lMarcar = true;
				}
			} else {
				Object cMotivoDespliega = vPolSegObrasInstPort.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Obras e Instalaciones Portuarias",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + cMotivoDespliega.toString(),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
					lMarcar = true;
				}
			}
		} else {
			rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Obras e Instalaciones Portuarias",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
			lMarcar = true;
		}

		//Póliza de Seguro de Responsabilidad Civil.
		if(!vPolSegRespCiv.isEmpty()){
			Object lValor = vPolSegRespCiv.get(1);
			if (vPolSegRespCiv.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Responsabilidad Civil",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
					lMarcar = true;
				}
			} else {
				Object cMotivoDes = vPolSegRespCiv.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Responsabilidad Civil",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + cMotivoDes.toString(),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
					lMarcar = true;
				}
			}
		} else {
			rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Responsabilidad Civil",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
			lMarcar = true;
		}

		//Póliza de Seguro de los Equipos.
		if(!vPolSegEqu.isEmpty()){
			Object lValor = vPolSegEqu.get(1);
			if (vPolSegEqu.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Seguro de los Equipos",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
					lMarcar = true;
				}
			} else {
				Object cMotivoDes = vPolSegEqu.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Seguro de los Equipos",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + cMotivoDes.toString(),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
					lMarcar = true;
				}
			}
		} else {
			rep.comDespliegaAlineado("D", iReng,"I",iReng,"- Seguro de los Equipos",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("D", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
			lMarcar = true;
		}

		if (lMarcar)
			rep.comDespliegaAlineado("C", iRengTemp,"I",iRengTemp,"- Copia de la Póliza de Seguro. ",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

		//Fianza.
		try{
			vFianza = dCYSFianza.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vFianza.isEmpty()){
			Object lValor = vFianza.get(1);
			if (vFianza.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Original de la Póliza de la Fianza.",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			} else {
				Object cMotivoDes = vFianza.get(1);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Original de la Póliza de la Fianza.",
							false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng++;
					rep.comDespliegaAlineado("C", iReng,"I",iReng,cMotivo + cMotivoDes.toString(),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
					iReng+=2;
				}
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Original de la Póliza de la Fianza.",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
			rep.comDespliegaAlineado("C", iReng,"I",iReng,cMotivo + "_________________________",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Publicaciòn de la Concesión.
		try{
			vPubConcesion = dCYSPubConcesion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vPubConcesion.isEmpty()){
			Object lValor = vPubConcesion.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Publicación del Título de Concesión en el Diario Oficial de la Federación.",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia de la Publicación del Título de Concesión en el Diario Oficial de la Federación.",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Registro Publico de la Propiedad Federal.
		try{
			vRegPubProFed = dCYSRegistroPublicoPropiedadFederal.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vRegPubProFed.isEmpty()){
			Object lValor = vRegPubProFed.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia del Registro Público de la Propiedad Federal.",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia del Registro Público de la Propiedad Federal.",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Textos final doc.

		String cPrimerParrafoFin = "En virtud de lo anterior, se inicia procedimiento administrativo de sanción, " +
				"para cuyo efecto se le concede un plazo de 15 días hábiles contados a partir de la notificación del presente, " +
				"para que manifieste lo que a su derecho convenga y, en su caso, aporte pruebas en su defensa, pero se le " +
				"apercibe de que con o sin ellas, se pronunciará la resolución que en derecho proceda dentro del término legal.";

		iReng++;
		rep.comDespliegaAlineado("B", iReng,"K",iReng+3,cPrimerParrafoFin,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
		rep.comAlineaRango("B",iReng,"K",iReng + 3,rep.getAT_HJUSTIFICA());
		iReng+=5;
		String cSegundoParrafoFin = "Lo anterior, con fundamento en los artículos 14 y 16 de la Constitución Política de los " +
				"Estados Unidos Mexicanos; 36 fracciones XIX y XX de la Ley Orgánica de la Administración Pública Federal; " +
				"16 fracciones IV y X, 20, 63, 64, 65 y 69 de la Ley de Puertos; 3, 13, 32 y 35 fracción II de la Ley Federal de " +
				"Procedimiento Administrativo; 10 fracción IV, 27 fracciones I, IV, XI, XX, XXI y XXIII del Reglamento Interior " +
				"de la Secretaría de Comunicaciones y Transportes.";

		rep.comDespliegaAlineado("B",iReng,"K",iReng + 3,cSegundoParrafoFin,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
		rep.comAlineaRango("B",iReng,"K",iReng + 3,rep.getAT_HJUSTIFICA());
		iReng+=5;

//		Remitente y Pie de Página
		rep.comDespliegaAlineado("B", iReng,"E",iReng,"A T E N T A M E N T E.",
				true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B", iReng,"I",iReng,cLeyendaPie,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;

		TDObtenDatos dObtenPieP = new TDObtenDatos();
		dObtenPieP.dOficDepto.setOficinaDepto(Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen));
		rep.comDespliegaAlineado("B", iReng,"I",iReng,dObtenPieP.dOficDepto.getPuestoTitular(),true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng+=2;
		rep.comDespliegaAlineado("B", iReng,"I",iReng,dObtenPieP.dOficDepto.getTitular(),true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng+=3;
		rep.comDespliegaAlineado("B", iReng,"I",iReng,"ACN/AECHR",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B", iReng,"I",iReng,"Inicio sanción(nombre o razon).",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B", iReng,"I",iReng,"Exp: ________ ",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		float fTam = 8.0f;
		rep.comFontSize("B", iReng-3, "I", iReng, fTam);

		//Registro Publico de la Propiedad Estatal.
		try{
			vRegPubProEst = dCYSRegPubPropiedadEstatal.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vRegPubProEst.isEmpty()){
			Object lValor = vRegPubProEst.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
				rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia del Registro Público de la Propiedad Estatal.",
						false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
				iReng+=2;
			}
		} else {
			rep.comDespliegaAlineado("C", iReng,"I",iReng,"- Copia del Registro Público de la Propiedad Estatal.",
					false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng+=2;
		}

		//Programa de Sustitucion de Titulos.
		try{
			vSustTit = dCYSProgramaSustTitulos.findTituloPresentado(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vSustTit.isEmpty()){
			for(int i=0;i<vSustTit.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vSustTit.get(i);
				//System.out.print("Programa de Sustitucion de Titulos: " + vDinRep.getInt("lExiste"));
			}
		}

		//Programa Maestro de Desarrollo Portuario.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vProMaeDesPor = dCYSProgramaMaestroDP.findProgramaMDP(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vProMaeDesPor.isEmpty()){
			for(int i=0;i<vProMaeDesPor.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vProMaeDesPor.get(i);
				//System.out.print("Programa Maestro de Desarrollo Portuario: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Programa Maestro de Desarrollo Portuario: " + vDinRep.getString("cMotivo"));
			}
		}

		//Extracto del Programa Maestro de Desarrollo Portuario.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vExtProMaeDesPor = dCYSProgramaMaestroDP.findExtractoDP(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vExtProMaeDesPor.isEmpty()){
			for(int i=0;i<vExtProMaeDesPor.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vExtProMaeDesPor.get(i);
				//System.out.print("Extracto del Programa Maestro de Desarrollo Portuario: " + vDinRep.getInt("lPresentacion"));
			}
		}

		//Programa Operativo Anual.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vProOpeAnual = dCYSProgramaOperativoAnual.findProgramaOA(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vProOpeAnual.isEmpty()){
			for(int i=0;i<vProOpeAnual.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vProOpeAnual.get(i);
				//System.out.print("Programa Operativo Anual: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Programa Operativo Anual: " + vDinRep.getString("cMotivo"));
			}
		}

		//Falta el Programa Minimo de Mantenimiento.
		//
		//

		//Reglas de Operacion.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vRegOpe = dCYSReglasOperacion.findReglasOperacion(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vRegOpe.isEmpty()){
			for(int i=0;i<vRegOpe.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Reglas de Operacion: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Reglas de Operacion: " + vDinRep.getString("cMotivo"));
			}
		}

		//Comite de Operacion.
		try{
			vComOpe = dCYSComiteOperacion.findComiteOperacion(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vComOpe.isEmpty()){
			for(int i=0;i<vComOpe.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Comite de Operacion: " + vDinRep.getString("cMotivo"));
			}
		}

		//Oficina de Quejas.
		try{
			vOfiQue = dCYSOficinaQuejas.findOficinaQuejas(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vOfiQue.isEmpty()){
			for(int i=0;i<vOfiQue.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Oficina de Quejas: " + vDinRep.getString("cMotivo"));
			}
		}

		//Sistema de Computo.
		try{
			vSisCom = dCYSSistemaComputo.findSistemaComputo(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vSisCom.isEmpty()){
			for(int i=0;i<vSisCom.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Sistema de Computo: " + vDinRep.getString("cMotivo"));
			}
		}

		//Estados Financieros.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vEdofin = dCYSEdoFinanciero.findEdoFinanciero(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vEdofin.isEmpty()){
			for(int i=0;i<vEdofin.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Estados Financieros: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Estados Financieros: " + vDinRep.getString("cMotivo"));
			}
		}

		//Otorgamiento.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vOtorgamiento = dCYSOtorgamiento.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vOtorgamiento.isEmpty()){
			for(int i=0;i<vOtorgamiento.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vOtorgamiento.get(i);
				//System.out.print("Otorgamiento: " + vDinRep.getInt("iEjercicioNoPagado"));
				//System.out.print("Otorgamiento: " + vDinRep.getString("cPeriodoNoPagado"));
			}
		}

		//Derecho del Señalamiento Marítimo.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vDerSenalamiento = dCYSDerechoSenalamiento.SitObligacion(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vDerSenalamiento.isEmpty()){
			for(int i=0;i<vDerSenalamiento.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vDerSenalamiento.get(i);
				//System.out.print("Derecho del Señalamiento Marítimo: " + vDinRep.getInt("iExiste"));
			}
		}

		//Derecho y Explotacion 232 - A LFD.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vDerExplotacion = dCYSDerechoExplotacion.findDerechoExplota(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vDerExplotacion.isEmpty()){
			for(int i=0;i<vDerExplotacion.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vDerExplotacion.get(i);
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dIngresos"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dImporte"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iMesI"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iEjercicioI"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iMesF"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iEjercicioF"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDate("dtPago"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDate("dtPresentacion"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getString("cClave"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dImpPagado"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dImpCorrecto"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dDiferencia"));

			}
		}

		sbRetorno = rep.getSbDatos();

		//TFechas Fecha = new TFechas("44");
		Vector vcDatos = new Vector();
		TVDinRep vDinRepActRec = new TVDinRep();
		vDinRepActRec.put("dtAdeudo",Fecha.getDateSQL(new Integer("15"),new Integer("01"),new Integer("2003")));
		vDinRepActRec.put("dtPago",Fecha.TodaySQL());
		vDinRepActRec.put("dImporte",15000.00);
		vcDatos.add(vDinRepActRec);
		vcDatos.add(vDinRepActRec);
		vcDatos.add(vDinRepActRec);
		//dActualizaRecargo.findActRecargoMultiple(vcDatos);


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

		try{
			vDinRep = dCYSFolioProc.insert(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return sbRetorno;
	}

	public Vector genInicioSancionWord(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsTituloProp = new Vector();
		Vector vRegsTituloDocumentos = new Vector();
		Vector vRegsTitPoliza = new Vector();
		Vector vRegsSerPor = new Vector();
		Vector vRegsSerCon = new Vector();
		Vector vRegsEscrituraConst = new Vector();
		Vector vRegsAcredRepLegal = new Vector();
		Vector vRegsTituloUbicacion = new Vector();
		Vector vRegsTituloSolicitud = new Vector();
		Vector vRegsTituloObras = new Vector();
		Vector vRegsTituloServPortuarios = new Vector();
		Vector vRegsTituloAnexos = new Vector();
		Vector vRegsTituloAnterior = new Vector();
		Vector vRegsTipoTitulo = new Vector();

		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");
		TFechas Fecha = new TFechas("44");
		TDCPATitulo1 dCPATitulo1 = new TDCPATitulo1();
		TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
		TDCYSConcesionZonaFederal dConZonFed = new TDCYSConcesionZonaFederal();
		TDCYSAutImpactoAmbiental dCYSAutImpactoAmbiental = new TDCYSAutImpactoAmbiental();
		TDCYSAutInicioConstruccion dCYSAutInicioConstruccion = new TDCYSAutInicioConstruccion();
		TDCYSAutInicioOperacion dCYSAutInicioOperacion = new TDCYSAutInicioOperacion();
		TDCYSAutSenalamientoMaritimo dCYSAutSenalamientoMaritimo = new TDCYSAutSenalamientoMaritimo();
		TDCYSCompromisosCalidad dCYSCompromisosCalidad = new TDCYSCompromisosCalidad();
		TDCYSProgramaModernizacion dCYSProgramaModernizacion = new TDCYSProgramaModernizacion();
		TDCYSConservacionMantto dCYSConservacionMantto = new TDCYSConservacionMantto();
		TDCYSPolizaSeguro dCYSPolizaSeguro = new TDCYSPolizaSeguro();
		TDCYSFianza dCYSFianza = new TDCYSFianza();
		TDCYSPubConcesion dCYSPubConcesion = new TDCYSPubConcesion();
		TDCYSRegistroPublicoPropiedadFederal dCYSRegistroPublicoPropiedadFederal = new TDCYSRegistroPublicoPropiedadFederal();
		TDCYSRegPubPropiedadEstatal dCYSRegPubPropiedadEstatal = new TDCYSRegPubPropiedadEstatal();
		TDCYSProgramaSustTitulos dCYSProgramaSustTitulos = new TDCYSProgramaSustTitulos();
		TDCYSProgramaMaestroDP dCYSProgramaMaestroDP = new TDCYSProgramaMaestroDP();
		TDCYSProgramaOperativoAnual dCYSProgramaOperativoAnual = new TDCYSProgramaOperativoAnual();
		TDCYSReglasOperacion dCYSReglasOperacion = new TDCYSReglasOperacion();
		TDCYSComiteOperacion dCYSComiteOperacion = new TDCYSComiteOperacion();
		TDCYSOficinaQuejas dCYSOficinaQuejas = new TDCYSOficinaQuejas();
		TDCYSSistemaComputo dCYSSistemaComputo = new TDCYSSistemaComputo();
		TDCYSEdoFinanciero dCYSEdoFinanciero = new TDCYSEdoFinanciero();
		TDCYSOtorgamiento dCYSOtorgamiento = new TDCYSOtorgamiento();
		TDCYSDerechoSenalamiento dCYSDerechoSenalamiento = new TDCYSDerechoSenalamiento();
		TDCYSDerechoExplotacion dCYSDerechoExplotacion = new TDCYSDerechoExplotacion();

		int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
		int iCveTipoTitulo=0;
		String cNombreAPI="",cNomCesionario="",cNumTitulo="";
		String cAsunto="Se Inicia Procedimiento Administrativo de Sanción.";
		String cTipoContrato="";
		String[] aFiltros = cQuery.split(",");
		int iCveTitulo = Integer.parseInt(aFiltros[0]);
		int iEjercicio = Integer.parseInt(aFiltros[1]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
		int iCveTitular = Integer.parseInt(aFiltros[3]);
		int iCveDomicilio = 0;
		int iCveRepLegal = 0;
		java.sql.Date dtConcesion = null;
		java.sql.Date dtHoy = Fechas.TodaySQL();
		String cRepLegalCompleto = "";
		int lEntroZOFEMAT = 0,lEntroImpAmb = 0,lEntroPEMEX = 0,lEntroCONAGUA = 0,lEntroSEMARNAT = 0;

		Vector vConZonFed = new Vector();
		Vector vAutImpAmbOp = new Vector();
		Vector vAutImpAmbConst = new Vector();
		Vector vAutIniConst = new Vector();
		Vector vAutIniOpe = new Vector();
		Vector vAutSenMar = new Vector();
		Vector vCompCal = new Vector();
		Vector vProMod = new Vector();
		Vector vConMantto = new Vector();
		Vector vPolSegObrasInstPort = new Vector();
		Vector vPolSegRespCiv = new Vector();
		Vector vPolSegEqu = new Vector();
		Vector vFianza = new Vector();
		Vector vPubConcesion = new Vector();
		Vector vRegPubProFed = new Vector();
		Vector vRegPubProEst = new Vector();
		Vector vSustTit = new Vector();
		Vector vProMaeDesPor = new Vector();
		Vector vExtProMaeDesPor = new Vector();
		Vector vProOpeAnual = new Vector();
		Vector vRegOpe = new Vector();
		Vector vComOpe = new Vector();
		Vector vOfiQue = new Vector();
		Vector vSisCom = new Vector();
		Vector vEdofin = new Vector();
		Vector vOtorgamiento = new Vector();
		Vector vDerSenalamiento = new Vector();
		Vector vDerExplotacion = new Vector();
		Vector vInfTitulo = new Vector();

		StringBuffer sbAsunto = new StringBuffer();
		sbAsunto.append("Requerimiento");

		this.setDatosFolio(cNumFolio);

		try{
			vInfTitulo = dCPATitulo1.findByInfTitulo(iCveTitulo);

			if (!vInfTitulo.isEmpty()){
				TVDinRep vDinRep = (TVDinRep) vInfTitulo.get(0);
				iCveDomicilio = vDinRep.getInt("iCveDomicilio");
			}


		} catch (Exception ex){
			ex.printStackTrace();
		}

		cQuery = "select CPATitulo.iCveTitulo, " +
		"GRLPersona.iTipoPersona, " +
		"GRLPersona.cNomRazonSocial, " +
		"GRLPersona.cApPaterno, " +
		"GRLPersona.cApMaterno, " +
		"GRLPersona.cRFC, " +
		"CPAActaNacimiento.cNumActaNacimiento, " +
		"CPAActaNacimiento.dtNacimiento, " +
		"CPAActaNacimiento.cNumJuzgado, " +
		"Juez.cNomRazonSocial cNomJuez, " +
		"Juez.cApPaterno cApPatJuez, " +
		"Juez.cApMaterno cApMatJuez, " +
		"CPAActaNacimiento.cNumLibroActa, " +
		"CPAActaNacimiento.cNumFojaActa, " +
		"GRLDomicilio.cCalle, " +
		"GRLDomicilio.cNumExterior, " +
		"GRLDomicilio.cNumInterior, " +
		"GRLDomicilio.cColonia, " +
		"GRLDomicilio.cCodPostal, " +
		"GRLDomicilio.cTelefono, " +
		"GRLPais.cNombre as cNomPais, " +
		"GRLEntidadFed.cNombre as cNomEntidadFed, " +
		"GRLMunicipio.cNombre as cNomMunicipio, " +
		"GRLLocalidad.cNombre as cNomLocalidad, " +
		"CPATitulo.cNumTitulo, " +
		"CPATitulo.cNumTituloAnterior, " +
		"CPATitulo.dtIniVigenciaTitulo, " +
		"CPATitulo.cZonaFedAfectadaTerrestre, " +
		"CPATitulo.cZonaFedAfectadaAgua, " +
		"CPATitulo.cObjetoTitulo, " +
		"CPATitulo.cMontoInversion, " +
		"CPATitulo.dtPublicacionDOF, " +
		"CPATitulo.iCveClasificacion, " +
		"CPAUsoTitulo.iCveUsoTitulo, " +
		"CPATitulo.iCveTituloAnterior, " +
		"CPATitulo.dtVigenciaTitulo " +
		"from CPATitulo " +
		"join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
		"join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
		"join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo " +
		"join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
		"left join CPAActaNacimiento on CPAActaNacimiento.iCvePersona = GRLPersona.iCvePersona " +
		"left join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
		"and GRLDomicilio.lPredeterminado = 1 " +
		"left join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
		"left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
		"and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
		"left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
		"and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
		"and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
		"left join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
		"and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
		"and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
		"and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
		"left join GRLPersona Juez on Juez.iCvePersona = CPAActaNacimiento.iCveJuez " +
		"where CPATitulo.iCveTitulo = " + iCveTitulo;

		try{
			vRegs = super.FindByGeneric("", cQuery, dataSourceName);
		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
			cMensaje += ex2.getMessage();
		}

		if (vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			//int iCveTitulo = Integer.parseInt(vDatos.get("iCveTitulo").toString());
			int iCveTituloAnt = vDatos.getInt("iCveTituloAnterior");


			// Escritura Constitutiva de las Personas Morales.
			try{
				vRegsEscrituraConst = super.FindByGeneric("",
						" select CPAEscConsMov.iNumMovimiento, " +
						" CPAEscConsMov.cNumEscrituraConst, " +
						" CPATipoMovEscritura.cDscTipoMovEscritura, " +
						" CPAEscConsMov.dtEscrituraMov, " +
						" CPAEscConsMov.cNumNotariaPub, " +
						" GRLPersona.cNomRazonSocial, " +
						" GRLPersona.cApPaterno, " +
						" GRLPersona.cApMaterno, " +
						" CPARegPubComercio.dtRegPubComercio, " +
						" CPARegPubComercio.cNumRegPubComercio, " +
						" CPARegPubComercio.cNumFoja, " +
						" CPARegPubComercio.cNumTomo, " +
						" CPARegPubComercio.cNumSeccion, " +
						" CPARegPubComercio.cNumLibro, " +
						" CPARegPubComercio.cNumVolumen, " +
						" CPARegPubComercio.cNumSeccion, " +
						" GRLPais.cNombre as cDscPais, " +
						" GRLEntidadFed.cNombre as cDscEntidadFed, " +
						" GRLMunicipio.cNombre as cDscMunicipio, " +
						" GRLLocalidad.cNombre as cDscLocalidad " +
						" from CPATitulo " +
						" join CPAEscConsMov on CPAEscConsMov.iCveTitulo = CPATitulo.iCveTitulo " +
						" join CPATipoMovEscritura on CPATipoMovEscritura.iCveTipoMovEscritura = CPAEscConsMov.iCveTipoMovEscritura " +
						" left join CPARegPubComercio on CPARegPubComercio.iCveTitulo = CPAEscConsMov.iCveTitulo " +
						" and CPARegPubComercio.iNumMovimiento = CPAEscConsMov.iNumMovimiento " +
						" left join GRLPersona on GRLPersona.iCvePersona = CPAEscConsMov.iCveNotarioPublico " +
						" left join GRLPais on GRLPais.iCvePais = CPARegPubComercio.iCvePais " +
						" left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPARegPubComercio.iCvePais " +
						" and GRLEntidadFed.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
						" left join GRLMunicipio on GRLMunicipio.iCvePais = CPARegPubComercio.iCvePais " +
						" and GRLMunicipio.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
						" and GRLMunicipio.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
						" left join GRLLocalidad on GRLLocalidad.iCvePais = CPARegPubComercio.iCvePais " +
						" and GRLLocalidad.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
						" and GRLLocalidad.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
						" and GRLLocalidad.iCveLocalidad = CPARegPubComercio.iCveLocalidad " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Acreditacion del Representante Legal.
			try{
				vRegsAcredRepLegal = super.FindByGeneric("",
						" select CPAAcreditacionRepLegal.dtAcredRepLegal, " +
						" CPAAcreditacionRepLegal.cNumNotPubAcred, " +
						" CPAAcreditacionRepLegal.cDocumento, " +
						" GRLPersona.iCvePersona iCveRepLegal, " +
						" GRLPersona.cNomRazonSocial, " +
						" GRLPersona.cApPaterno, " +
						" GRLPersona.cApMaterno, " +
						" Notario.cNomRazonSocial as cNomNotario, " +
						" Notario.cApPaterno as cApPatNotario, " +
						" Notario.cApMaterno as cApMatNotario, " +
						" GRLPais.cNombre as cNomPais, " +
						" GRLEntidadFed.cNombre as cNomEntidadFed, " +
						" GRLMunicipio.cNombre as cNomMunicipio, " +
						" GRLLocalidad.cNombre as cNomLocalidad " +
						" from CPATitulo " +
						" join CPAAcreditacionRepLegal on CPAAcreditacionRepLegal.iCveTitulo = CPATitulo.iCveTitulo " +
						" join GRLPersona on GRLPersona.iCvePersona = CPAAcreditacionRepLegal.iCveRepLegal " +
						" join GRLPersona Notario on Notario.iCvePersona = CPAAcreditacionRepLegal.iCveNotarioPubAcred " +
						" join GRLDomicilio on GRLDomicilio.iCvePersona = Notario.iCvePersona " +
						" and GRLDomicilio.lPredeterminado = 1 " +
						" join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
						" join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
						" and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
						" join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
						" and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
						" and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
						" join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
						" and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
						" and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
						" and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Infomacion del Titulo de Propiedad
			try{
				vRegsTituloProp = super.FindByGeneric("",
						" select CPATituloPropiedad.cSuperficiePropiedad, " +
						" CPATituloPropiedad.cLotePropiedad, " +
						" CPATituloPropiedad.cSeccionPropiedad, " +
						" CPATituloPropiedad.cManzanaPropiedad, " +
						" CPATituloPropiedad.cNumOficialPropiedad, " +
						" CPATituloPropiedad.cCallePropiedad, " +
						" CPATituloPropiedad.cKmPropiedad, " +
						" CPATituloPropiedad.cColoniaPropiedad, " +
						" GRLPuerto.cDscPuerto, " +
						" GRLPais.cNombre as cDscPais, " +
						" GRLEntidadFed.cNombre as cDscEntidadFed, " +
						" GRLMunicipio.cNombre as cDscMunicipio, " +
						" GRLLocalidad.cNombre as cDscLocalidad, " +
						" CPATitProEscrituraPublica.cNumTitProEscPublica, " +
						" CPATitProEscrituraPublica.dtEscPublica, " +
						" CPATitProEscrituraPublica.cNumNotariaPublica, " +
						" Notario.cNomRazonSocial, " +
						" Notario.cApPaterno, "+
						" Notario.cApMaterno, " +
						" PaisEscPub.cNombre as cDscPaisEscPub, " +
						" EntidadEscPub.cNombre as cDscEntidadFedEscPub, " +
						" MunicipioEscPub.cNombre as cDscMunicipioEscPub, " +
						" LocalidadEscPub.cNombre as cDscLocalidadEscPub, " +
						" CPATitProRegPubCom.dtTitProRegPubCom, " +
						" CPATitProRegPubCom.cNumTitProRegPubCom, " +
						" CPATitProRegPubCom.cNumRegPubComFoja, " +
						" CPATitProRegPubCom.cNumRegPubComTomo, " +
						" CPATitProRegPubCom.cNumRegPubComSeccion, " +
						" CPATitProRegPubCom.cNumRegPubComLibro, " +
						" CPATitProRegPubCom.cNumRegPubComVolumen, " +
						" PaisRegPubCom.cNombre as cDscPaisRegPubCom, " +
						" EntidadRegPubCom.cNombre as cDscEntidadFedRegPubCom, " +
						" MunicipioRegPubCom.cNombre as cDscMunicipioRegPubCom, " +
						" LocalidadRegPubCom.cNombre as cDscLocalidadRegPubCom " +
						" from CPATitulo " +
						" join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo " +
						" join CPATituloPropiedad on CPATituloPropiedad.iCveTitulo = CPATitular.iCveTitulo " +
						" and CPATituloPropiedad.iCveTitular = CPATitular.iCveTitular " +
						" left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloPropiedad.iCvePuerto " +
						" join GRLPais on GRLPais.iCvePais = CPATituloPropiedad.iCvePais " +
						" join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloPropiedad.iCvePais " +
						" and GRLEntidadFed.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed " +
						" join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloPropiedad.iCvePais " +
						" and GRLMunicipio.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed " +
						" and GRLMunicipio.iCveMunicipio = CPATituloPropiedad.iCveMunicipio " +
						" join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloPropiedad.iCvePais " +
						" and GRLLocalidad.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed " +
						" and GRLLocalidad.iCveMunicipio = CPATituloPropiedad.iCveMunicipio " +
						" and GRLLocalidad.iCveLocalidad = CPATituloPropiedad.iCveLocalidad " +
						" left join CPATitProEscrituraPublica on CPATitProEscrituraPublica.iCveTitulo = CPATituloPropiedad.iCveTitulo " +
						" and CPATitProEscrituraPublica.iCveTitular = CPATituloPropiedad.iCveTitular " +
						" and CPATitProEscrituraPublica.iNumTituloPropiedad = CPATituloPropiedad.iNumTituloPropiedad " +
						" left join GRLPersona Notario on Notario.iCvePersona = CPATitProEscrituraPublica.iCveNotarioPub " +
						" left join GRLPais PaisEscPub on PaisEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
						" left join GRLEntidadFed EntidadEscPub on EntidadEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
						" and EntidadEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed " +
						" left join GRLMunicipio MunicipioEscPub on MunicipioEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
						" and MunicipioEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed " +
						" and MunicipioEscPub.iCveMunicipio = CPATitProEscrituraPublica.iCveMunicipio " +
						" left join GRLLocalidad LocalidadEscPub on LocalidadEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
						" and LocalidadEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed " +
						" and LocalidadEscPub.iCveMunicipio = CPATitProEscrituraPublica.iCveMunicipio " +
						" and LocalidadEscPub.iCveLocalidad = CPATitProEscrituraPublica.iCveLocalidad " +
						" left join CPATitProRegPubCom on CPATitProRegPubCom.iCveTitulo = CPATitProEscrituraPublica.iCveTitulo " +
						" and CPATitProRegPubCom.iCveTitular = CPATitProEscrituraPublica.iCveTitular " +
						" and CPATitProRegPubCom.iNumTituloPropiedad = CPATitProEscrituraPublica.iNumTituloPropiedad " +
						" left join GRLPais PaisRegPubCom on PaisRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
						" left join GRLEntidadFed EntidadRegPubCom on EntidadRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
						" and EntidadRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed " +
						" left join GRLMunicipio MunicipioRegPubCom on MunicipioRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
						" and MunicipioRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed " +
						" and MunicipioRegPubCom.iCveMunicipio = CPATitProRegPubCom.iCveMunicipio " +
						" left join GRLLocalidad LocalidadRegPubCom on LocalidadRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
						" and LocalidadRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed " +
						" and LocalidadRegPubCom.iCveMunicipio = CPATitProRegPubCom.iCveMunicipio " +
						" and LocalidadRegPubCom.iCveLocalidad = CPATitProRegPubCom.iCveLocalidad " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de la Ubicacion del Area Solicitada
			try{
				vRegsTituloUbicacion = super.FindByGeneric("",
						" select CPATituloUbicacion.lDentroRecintoPort, " +
						" CPATituloUbicacion.cCalleTitulo, " +
						" CPATituloUbicacion.cKm, " +
						" CPATituloUbicacion.cColoniaTitulo, " +
						" GRLPuerto.cDscPuerto, " +
						" GRLPais.cNombre as cDscPais, " +
						" GRLEntidadFed.cNombre as cDscEntidadFed, " +
						" GRLMunicipio.cNombre as cDscMunicipio, " +
						" GRLLocalidad.cNombre as cDscLocalidad " +
						" from CPATitulo " +
						" join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
						" left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
						" left join GRLPais on GRLPais.iCvePais = CPATituloUbicacion.iCvePais " +
						" left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloUbicacion.iCvePais " +
						" and GRLEntidadFed.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
						" left join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloUbicacion.iCvePais " +
						" and GRLMunicipio.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
						" and GRLMunicipio.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
						" left join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloUbicacion.iCvePais " +
						" and GRLLocalidad.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
						" and GRLLocalidad.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
						" and GRLLocalidad.iCveLocalidad = CPATituloUbicacion.iCveLocalidad " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de la Solicitud
			try{
				vRegsTituloSolicitud = super.FindByGeneric("",
						" select CPASolTitulo.iEjercicio, " +
						" CPASolTitulo.iNumSolicitud, " +
						" TRACatTramite.cDscBreve, " +
						" TRAModalidad.cDscModalidad, " +
						" date(TRARegSolicitud.tsRegistro) as dtRegistro, " +
						" GRLOficina.cCalleyNo, " +
						" GRLOficina.cColonia, " +
						" GRLOficina.cCodPostal, " +
						" GRLOficina.cTelefono, " +
						" GRLOficina.cCorreoE, " +
						" GRLPais.cNombre as cDscPais, " +
						" GRLEntidadFed.cNombre as cDscEntidadFed, " +
						" GRLMunicipio.cNombre as cDscMunicipio, " +
						" TRACatTramite.iCveTramite, " +
						" TRAModalidad.iCveModalidad " +
						" from CPATitulo " +
						" join CPASolTitulo on CPASolTitulo.iCveTitulo = CPATitulo.iCveTitulo " +
						" join TRARegSolicitud on TRARegSolicitud.iEjercicio = CPASolTitulo.iEjercicio " +
						" and TRARegSolicitud.iNumSolicitud = CPASolTitulo.iNumSolicitud " +
						" join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite " +
						" join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad " +
						" join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina " +
						" join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais "+
						" join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais " +
						" and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
						" join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais " +
						" and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
						" and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de las Obras del Título.
			try{
				vRegsTituloObras = super.FindByGeneric("",
						" select CPATituloObra.cDscObra, " +
						" CPATituloObra.lPropiedadNacional, " +
						" CPATituloObra.lConstruccion, " +
						" CPATituloObra.lOperacion, " +
						" CPATituloObra.cTiempoEstEjecucion, " +
						" CPATituloObra.dtIniObra " +
						" from CPATitulo " +
						" join CPATituloObra on CPATituloObra.iCveTitulo = CPATitulo.iCveTitulo " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de Servicios Portuarios Relacionados con los Títulos.
			try{
				vRegsTituloServPortuarios = super.FindByGeneric("",
						" select CPAServicioPortuario.cDscServicioPortuario " +
						" from CPATitulo " +
						" join CPATituloServicioPortuario on CPATituloServicioPortuario.iCveTitulo = CPATitulo.iCveTitulo " +
						" join CPAServicioPortuario on CPAServicioPortuario.iCveServicioPortuario = CPATituloServicioPortuario.iCveServicioPortuario " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de Documentos Relacionados con el Título.
			try{
				vRegsTituloDocumentos = super.FindByGeneric("",
						" select CPATituloDocumento.iCveTipoDocumento, " +
						" CPATipoDocumento.cDscTipoDocumento, " +
						" CPATituloDocumento.cNumDocumento, " +
						" CPATituloDocumento.dtDocumento, " +
						" CPATituloDocumento.dtVigenciaDocumento, " +
						" GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cDepEmite, " +
						" CPATituloDocumento.cObjetoDocumento, " +
						" CPATituloDocumento.cSuperficieDocumento, " +
						" CPATituloDocumento.lMaritimoTerrestre, " +
						" CPAClasificacionDocumento.cDscClasificacionDocto " +
						" from CPATitulo " +
						" join CPATituloDocumento on CPATituloDocumento.iCveTitulo = CPATitulo.iCveTitulo " +
						" join CPATipoDocumento on CPATipoDocumento.iCveTipoDocumento = CPATituloDocumento.iCveTipoDocumento " +
						" left join GRLPersona on GRLPersona.iCvePersona = CPATituloDocumento.iCveDepEmiteDoc " +
						" left join CPAClasificacionDocumento on CPAClasificacionDocumento.iCveClasificacionDocto = CPATituloDocumento.iCveClasificacionDocto " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion de los Anexos del Título.
			try{
				vRegsTituloAnexos = super.FindByGeneric("",
						" select CPAAnexo.cDscAnexo, " +
						" CPAAnexoTramite.iOrden, " +
						" CPAAnexo.iNivel, " +
						" CPAAnexo.iCveAnexo " +
						" from CPATitulo " +
						" join CPATituloAnexo on CPATituloAnexo.iCveTitulo = CPATitulo.iCveTitulo " +
						" join CPAAnexoTramite on CPAAnexoTramite.iCveAnexo = CPATituloAnexo.iCveAnexo " +
						" and CPAAnexoTramite.iCveTramite = CPATituloAnexo.iCveTramite " +
						" and CPAAnexoTramite.iCveModalidad = CPATituloAnexo.iCveModalidad " +
						" and CPAAnexoTramite.dtIniVigencia = CPATituloAnexo.dtIniVigencia " +
						" join CPAAnexo on CPAAnexo.iCveAnexo = CPATituloAnexo.iCveAnexo " +
						" where CPATitulo.iCveTitulo = " + iCveTitulo,
						dataSourceName);
			}catch(SQLException ex){
				ex.printStackTrace();
				cMensaje = ex.getMessage();
			}catch(Exception ex2){
				ex2.printStackTrace();
				cMensaje += ex2.getMessage();
			}

			//Informacion del Titulo Anterior.
			if (iCveTituloAnt>0){
				try{
					vRegsTituloAnterior = super.FindByGeneric("",
							" select CPATitulo.iCveTipoTitulo, " +
							" CPATipoTitulo.cDscTipoTitulo, " +
							" CPATitulo.cNumTitulo, " +
							" CPATitulo.dtIniVigenciaTitulo, " +
							" CPATitulo.dtVigenciaTitulo, " +
							" CPATitulo.dtPublicacionDOF " +
							" from CPATitulo " +
							" join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
							" where CPATitulo.iCveTitulo = " + iCveTituloAnt,
							dataSourceName);
				} catch(Exception ex){
					ex.printStackTrace();
					cMensaje += ex.getMessage();
				}
			}


			if (iCveTitulo>0){
				try{
					vRegsTipoTitulo = super.FindByGeneric("",
							" select CPATitulo.iCveTipoTitulo " +
							" from CPATitulo " +
							" join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
							" where CPATitulo.iCveTitulo = " + iCveTitulo,dataSourceName);
				} catch(Exception ex){
					ex.printStackTrace();
					cMensaje += ex.getMessage();
				}
			}


			rep.iniciaReporte();

			if(vRegsTipoTitulo.size() > 0){
				TVDinRep vTipoTitulo = (TVDinRep) vRegsTipoTitulo.get(0);
				iCveTipoTitulo = vTipoTitulo.getInt("iCveTipoTitulo");

				if(iCveTipoTitulo== 1)
					rep.comRemplaza("[cDscOtorgado]","El Presidente de la República a través de la Dirección General de Puertos");
				else if((iCveTipoTitulo== 2) || (iCveTipoTitulo== 3))
					rep.comRemplaza("[cDscOtorgado]","La Dirección General de Puertos");
			}
			else
				rep.comRemplaza("[cDscOtorgado]"," ");


			if (vDatos.getInt("iTipoPersona")== 1){
				//Valores para las Personas Físicas.
				rep.comRemplaza("[cNombre]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString():"");
				rep.comRemplaza("[cApPaterno]",(vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString():"");
				rep.comRemplaza("[cApMaterno]",(vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():"");
				rep.comRemplaza("[cNoActa]",(vDatos.get("cNumActaNacimiento")!= null)?vDatos.get("cNumActaNacimiento").toString():"");
				rep.comRemplaza("[cFechaActa]",(vDatos.get("dtNacimiento")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtNacimiento"),"/"):"");
				rep.comRemplaza("[cNoJuzgado]",(vDatos.get("cNumJuzgado")!= null)?vDatos.get("cNumJuzgado").toString():"");
				rep.comRemplaza("[cNomJuez]",(vDatos.get("cNomJuez")!= null)?vDatos.get("cNomJuez").toString():"");
				rep.comRemplaza("[cApPatJuez]",(vDatos.get("cApPatJuez")!= null)?vDatos.get("cApPatJuez").toString():"");
				rep.comRemplaza("[cApMatJuez]",(vDatos.get("cApMatJuez")!= null)?vDatos.get("cApMatJuez").toString():"");
				rep.comRemplaza("[cLibroActa]",(vDatos.get("cNumLibroActa")!= null)?vDatos.get("cNumLibroActa").toString():"");
				rep.comRemplaza("[cFojaActa]",(vDatos.get("cNumFojaActa")!= null)?vDatos.get("cNumFojaActa").toString():"");
				//Para quien es la Concesion.
				rep.comRemplaza("[cPara]",((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
						((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
						((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():""));
				cRepLegalCompleto = ((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
				((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
				((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():"");

			} else {
				rep.comRemplaza("[cNombre]","");
				rep.comRemplaza("[cApPaterno]","");
				rep.comRemplaza("[cApMaterno]","");
				rep.comRemplaza("[cNoActa]","");
				rep.comRemplaza("[cFechaActa]","");
				rep.comRemplaza("[cNoJuzgado]","");
				rep.comRemplaza("[cNomJuez]","");
				rep.comRemplaza("[cApPatJuez]","");
				rep.comRemplaza("[cApMatJuez]","");
				rep.comRemplaza("[cLibroActa]","");
				rep.comRemplaza("[cFojaActa]","");
			}


			if (vDatos.getInt("iTipoPersona")== 2){

				if(vRegsEscrituraConst.size()>0){
					TVDinRep vDatosEscConst = (TVDinRep) vRegsEscrituraConst.get(0);

					//Para quien es la Concesion.
					rep.comRemplaza("[cPara]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"");

					//Valores de las Personas Morales con su Escritura Constitutiva.
					rep.comRemplaza("[cRazonSocial]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString():"");
					rep.comRemplaza("[NoEscConstitutiva]",(vDatosEscConst.get("cNumEscrituraConst")!= null)?vDatosEscConst.get("cNumEscrituraConst").toString():"");
					rep.comRemplaza("[cFechaEscConstitutiva]",(vDatosEscConst.get("dtEscrituraMov")!= null)?Fecha.getFechaDDMMYYYY(vDatosEscConst.getDate("dtEscrituraMov"),"/"):"");
					rep.comRemplaza("[cNoNotariaPub]",(vDatosEscConst.get("cNumNotariaPub")!= null)?vDatosEscConst.get("cNumNotariaPub").toString():"");
					rep.comRemplaza("[cCiudad]",((vDatosEscConst.get("cDscLocalidad")!= null)?vDatosEscConst.get("cDscLocalidad").toString():"")+
							((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
					rep.comRemplaza("[cEntidadFed]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");
					rep.comRemplaza("[[cNomNotPub]]",(vDatosEscConst.get("cNomRazonSocial")!= null)?vDatosEscConst.get("cNomRazonSocial").toString():"");
					rep.comRemplaza("[cApPatNotPub]",(vDatosEscConst.get("cApPaterno")!= null)?vDatosEscConst.get("cApPaterno").toString():"");
					rep.comRemplaza("[cApMatNotPub]",(vDatosEscConst.get("cApMaterno")!= null)?vDatosEscConst.get("cApMaterno").toString():"");
					rep.comRemplaza("[cRFC]",(vDatos.get("cRFC")!= null)?vDatos.get("cRFC").toString():"");

					//Valores para el Registro Público de la Comercio de la Escritura Publica.
					rep.comRemplaza("[cFechaRP]",(vDatosEscConst.get("dtRegPubComercio")!= null)?Fecha.getFechaDDMMYYYY(vDatosEscConst.getDate("dtRegPubComercio"),"/"):"");
					rep.comRemplaza("[FolioRealRP]",(vDatosEscConst.get("cNumRegPubComercio")!= null)?vDatosEscConst.get("cNumRegPubComercio").toString():"");
					rep.comRemplaza("[FojaRP]",(vDatosEscConst.get("cNumFoja")!= null)?vDatosEscConst.get("cNumFoja").toString():"");
					rep.comRemplaza("[cToRP]",(vDatosEscConst.get("cNumTomo")!= null)?vDatosEscConst.get("cNumTomo").toString():"");
					rep.comRemplaza("[cSecRP]",(vDatosEscConst.get("cNumSeccion")!= null)?vDatosEscConst.get("cNumSeccion").toString():"");
					rep.comRemplaza("[cLibRP]",(vDatosEscConst.get("cNumLibro")!= null)?vDatosEscConst.get("cNumLibro").toString():"");
					rep.comRemplaza("[cVolRP]",(vDatosEscConst.get("cNumVolumen")!= null)?vDatosEscConst.get("cNumVolumen").toString():"");
					rep.comRemplaza("[cCiudadRP]",((vDatosEscConst.get("cDscLocalidad")!= null)?vDatosEscConst.get("cDscLocalidad").toString():"")+
							((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
					rep.comRemplaza("[cEntidadFedRP]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");

					if (vRegsEscrituraConst.size() > 1){
						TVDinRep vDatosModEscConst = (TVDinRep) vRegsEscrituraConst.get(1);

						//Modificaciones a la Escritura Constitutiva.
						rep.comRemplaza("[cNoEscMRP]",(vDatosModEscConst.get("cNumEscrituraConst")!= null)?vDatosModEscConst.get("cNumEscrituraConst").toString():"");
						rep.comRemplaza("[cFechaMRP]",(vDatosModEscConst.get("dtEscrituraMov")!= null)?Fecha.getFechaDDMMYYYY(vDatosModEscConst.getDate("dtEscrituraMov"),"/"):"");
						rep.comRemplaza("[cNoNotPubMRP]",(vDatosModEscConst.get("cNumNotariaPub")!= null)?vDatosModEscConst.get("cNumNotariaPub").toString():"");
						rep.comRemplaza("[cCiudadMRP]",((vDatosModEscConst.get("cDscLocalidad")!= null)?vDatosModEscConst.get("cDscLocalidad").toString():"")+
								((vDatosModEscConst.get("cDscMunicipio")!= null)?vDatosModEscConst.get("cDscMunicipio").toString():""));
						rep.comRemplaza("[cEntFedMRP]",(vDatosModEscConst.get("cDscEntidadFed")!= null)?vDatosModEscConst.get("cDscEntidadFed").toString():"");
						rep.comRemplaza("[cNotPubMRP]",((vDatosModEscConst.get("cNomRazonSocial")!= null)?vDatosModEscConst.get("cNomRazonSocial").toString():"")+
								((vDatosModEscConst.get("cApPaterno")!= null)?vDatosModEscConst.get("cApPaterno").toString():"")+
								((vDatosModEscConst.get("cApMaterno")!= null)?vDatosModEscConst.get("cApMaterno").toString():""));

						//Registro Publico de Comercio de las Modificaciones a la Escritura Constitutiva.
						rep.comRemplaza("[cFecRPC]",(vDatosModEscConst.get("dtRegPubComercio")!= null)?Fecha.getFechaDDMMYYYY(vDatosModEscConst.getDate("dtRegPubComercio"),"/"):"");
						rep.comRemplaza("[cFolRPC]",(vDatosModEscConst.get("cNumRegPubComercio")!= null)?vDatosModEscConst.get("cNumRegPubComercio").toString():"");
						rep.comRemplaza("[cFojRPC]",(vDatosModEscConst.get("cNumFoja")!= null)?vDatosModEscConst.get("cNumFoja").toString():"");
						rep.comRemplaza("[cTomRPC]",(vDatosModEscConst.get("cNumTomo")!= null)?vDatosModEscConst.get("cNumTomo").toString():"");
						rep.comRemplaza("[cSecRPC]",(vDatosModEscConst.get("cNumSeccion")!= null)?vDatosModEscConst.get("cNumSeccion").toString():"");
						rep.comRemplaza("[cLibRPC]",(vDatosModEscConst.get("cNumLibro")!= null)?vDatosModEscConst.get("cNumLibro").toString():"");
						rep.comRemplaza("[cVolRPC]",(vDatosModEscConst.get("cNumVolumen")!= null)?vDatosModEscConst.get("cNumVolumen").toString():"");
						rep.comRemplaza("[cCiudadRPC]",((vDatosModEscConst.get("cDscLocalidad")!= null)?vDatosModEscConst.get("cDscLocalidad").toString():"")+
								((vDatosModEscConst.get("cDscMunicipio")!= null)?vDatosModEscConst.get("cDscMunicipio").toString():""));
						rep.comRemplaza("[cEntFedRPC]",(vDatosModEscConst.get("cDscEntidadFed")!= null)?vDatosModEscConst.get("cDscEntidadFed").toString():"");
					}
				}

				if (vRegsAcredRepLegal.size()> 0){
					TVDinRep vDatosRepLegal = (TVDinRep) vRegsAcredRepLegal.get(0);

					//Información del Representante Legal.
					iCveRepLegal = vDatosRepLegal.getInt("iCveRepLegal");
					rep.comRemplaza("[cNombreRL]",(vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString():"");
					rep.comRemplaza("[cApPatRL]",(vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString():"");
					rep.comRemplaza("[cApMatRL]",(vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");
					rep.comRemplaza("[cFechaRL]",(vDatosRepLegal.get("dtAcredRepLegal")!= null)?Fecha.getFechaDDMMYYYY(vDatosRepLegal.getDate("dtAcredRepLegal"),"/"):"");
					rep.comRemplaza("[cNoNotPubRL]",(vDatosRepLegal.get("cNumNotPubAcred")!= null)?vDatosRepLegal.get("cNumNotPubAcred").toString():"");
					rep.comRemplaza("[cCiudadRL]",((vDatosRepLegal.get("cNomLocalidad")!= null)?vDatosRepLegal.get("cNomLocalidad").toString():"")+
							((vDatosRepLegal.get("cNomMunicipio")!= null)?vDatosRepLegal.get("cNomMunicipio").toString():""));
					rep.comRemplaza("[cEntFedRL]",(vDatosRepLegal.get("cNomEntidadFed")!= null)?vDatosRepLegal.get("cNomEntidadFed").toString():"");
					rep.comRemplaza("[cNotPubRL]",((vDatosRepLegal.get("cNomNotario")!= null)?vDatosRepLegal.get("cNomNotario").toString():"")+
							((vDatosRepLegal.get("cApPatNotario")!= null)?vDatosRepLegal.get("cApPatNotario").toString():"")+
							((vDatosRepLegal.get("cApMatNotario")!= null)?vDatosRepLegal.get("cApMatNotario").toString():""));

					cRepLegalCompleto = ((vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString() + " ":"") +
					((vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString() + " ":"") +
					((vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");


				} else {
					rep.comRemplaza("[cNombreRL]","");
					rep.comRemplaza("[cApPatRL]","");
					rep.comRemplaza("[cApMatRL]","");
					rep.comRemplaza("[cFechaRL]","");
					rep.comRemplaza("[cNoNotPubRL]","");
					rep.comRemplaza("[cCiudadRL]","");
					rep.comRemplaza("[cEntFedRL]","");
					rep.comRemplaza("[cNotPubRL]","");
				}
			} else {

				//Valores de las Personas Morales con su Escritura Constitutiva.
				rep.comRemplaza("[cRazonSocial]","");
				rep.comRemplaza("[NoEscConstitutiva]","");
				rep.comRemplaza("[cFechaEscConstitutiva]","");
				rep.comRemplaza("[cNoNotariaPub]","");
				rep.comRemplaza("[cCiudad]","");
				rep.comRemplaza("[cEntidadFed]","");
				rep.comRemplaza("[cNomNotPub]","");
				rep.comRemplaza("[cApPatNotPub]","");
				rep.comRemplaza("[cApMatNotPub]","");
				rep.comRemplaza("[cRFC]","");

				//Valores para el Registro Público de la Comercio de la Escritura Publica.
				rep.comRemplaza("[cFechaRP]","");
				rep.comRemplaza("[FolioRealRP]","");
				rep.comRemplaza("[FojaRP]","");
				rep.comRemplaza("[cToRP]","");
				rep.comRemplaza("[cSecRP]","");
				rep.comRemplaza("[cLibRP]","");
				rep.comRemplaza("[cVolRP]","");
				rep.comRemplaza("[cCiudadRP]","");
				rep.comRemplaza("[cEntidadFedRP]","");

				//Modificaciones a la Escritura Constitutiva.
				rep.comRemplaza("[cNoEscMRP]","");
				rep.comRemplaza("[cFechaMRP]","");
				rep.comRemplaza("[cNoNotPubMRP]","");
				rep.comRemplaza("[cCiudadMRP]","");
				rep.comRemplaza("[cEntFedMRP]","");
				rep.comRemplaza("[cNotPubMRP]","");

				//Registro Publico de Comercio de las Modificaciones a la Escritura Constitutiva.
				rep.comRemplaza("[cFecRPC]","");
				rep.comRemplaza("[cFolRPC]","");
				rep.comRemplaza("[cFojRPC]","");
				rep.comRemplaza("[cTomRPC]","");
				rep.comRemplaza("[cSecRPC]","");
				rep.comRemplaza("[cLibRPC]","");
				rep.comRemplaza("[cVolRPC]","");
				rep.comRemplaza("[cCiudadRPC]","");
				rep.comRemplaza("[cEntFedRPC]","");

				//Información del Representante Legal.
				rep.comRemplaza("[cNombreRL]","");
				rep.comRemplaza("[cApPatRL]","");
				rep.comRemplaza("[cApMatRL]","");
				rep.comRemplaza("[cFechaRL]","");
				rep.comRemplaza("[cNoNotPubRL]","");
				rep.comRemplaza("[cCiudadRL]","");
				rep.comRemplaza("[cEntFedRL]","");
				rep.comRemplaza("[cNotPubRL]","");
			}

			//Informacion del Domicilio para Recibir Notificaciones.
			rep.comRemplaza("[cCalleDN]",(vDatos.get("cCalle")!= null)?vDatos.get("cCalle").toString():"");
			rep.comRemplaza("[cNoExtDN]",(vDatos.get("cNumExterior")!= null)?vDatos.get("cNumExterior").toString():"");
			rep.comRemplaza("[cNoIntDN]",(vDatos.get("cNumInterior")!= null)?vDatos.get("cNumInterior").toString():"");
			rep.comRemplaza("[cColDN]",(vDatos.get("cColonia")!= null)?vDatos.get("cColonia").toString():"");
			rep.comRemplaza("[cDelDN]",(vDatos.get("cNomMunicipio")!= null)?vDatos.get("cNomMunicipio").toString():"");
			rep.comRemplaza("[cCiudadDN]",(vDatos.get("cNomLocalidad")!= null)?vDatos.get("cNomLocalidad").toString():"");
			rep.comRemplaza("[cCodPosDN]",(vDatos.get("cCodPostal")!= null)?vDatos.get("cCodPostal").toString():"");
			rep.comRemplaza("[cEntFedDN]",(vDatos.get("cNomEntidadFed")!= null)?vDatos.get("cNomEntidadFed").toString():"");

			if (vRegsTituloProp.size()>0){
				TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);

				//Informacion del Título de Propiedad.
				rep.comRemplaza("[cSupZF]",(vDatosTitProp.get("cSuperficiePropiedad")!= null)?vDatosTitProp.get("cSuperficiePropiedad").toString():"");
				rep.comRemplaza("[cLoteZF]",(vDatosTitProp.get("cLotePropiedad")!= null)?vDatosTitProp.get("cLotePropiedad").toString():"");
				rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cSeccionPropiedad")!= null)?vDatosTitProp.get("cSeccionPropiedad").toString():"");
				rep.comRemplaza("[cManZF]",(vDatosTitProp.get("cManzanaPropiedad")!= null)?vDatosTitProp.get("cManzanaPropiedad").toString():"");
				rep.comRemplaza("[cNoZF]",(vDatosTitProp.get("cNumOficialPropiedad")!= null)?vDatosTitProp.get("cNumOficialPropiedad").toString():"");
				rep.comRemplaza("[cCalZF]",(vDatosTitProp.get("cCallePropiedad")!= null)?vDatosTitProp.get("cCallePropiedad").toString():"");
				rep.comRemplaza("[cKMZF]",(vDatosTitProp.get("cKmPropiedad")!= null)?vDatosTitProp.get("cKmPropiedad").toString():"");
				rep.comRemplaza("[cColZF]",(vDatosTitProp.get("cColoniaPropiedad")!= null)?vDatosTitProp.get("cColoniaPropiedad").toString():"");
				rep.comRemplaza("[cPuertoZF]",(vDatosTitProp.get("cDscPuerto")!= null)?vDatosTitProp.get("cDscPuerto").toString():"");
				rep.comRemplaza("[cMunZF]",(vDatosTitProp.get("cDscMunicipio")!= null)?vDatosTitProp.get("cDscMunicipio").toString():"");
				rep.comRemplaza("[cEntFedZF]",(vDatosTitProp.get("cDscEntidadFed")!= null)?vDatosTitProp.get("cDscEntidadFed").toString():"");

				//Informacion de la Escritura Publica del Título de Propiedad.
				rep.comRemplaza("[cNoEscZF]",(vDatosTitProp.get("cNumTitProEscPublica") != null && vDatosTitProp.get("cNumTitProEscPublica")!= null)?vDatosTitProp.get("cNumTitProEscPublica").toString():"");
				rep.comRemplaza("[cFechaZF]",(vDatosTitProp.get("dtEscPublica") != null)?Fecha.getFechaDDMMYYYY(vDatosTitProp.getDate("dtEscPublica"),"/"):"");
				rep.comRemplaza("[cNoNotPubZF]",(vDatosTitProp.get("cNumNotariaPublica") != null && vDatosTitProp.get("cNumNotariaPublica")!= null)?vDatosTitProp.get("cNumNotariaPublica").toString():"");
				rep.comRemplaza("[cCiuZF]",((vDatosTitProp.get("cDscLocalidadEscPub") != null && vDatosTitProp.get("cDscLocalidadEscPub")!= null)?vDatosTitProp.get("cDscLocalidadEscPub").toString():"")+
						((vDatosTitProp.get("cDscMunicipioEscPub") != null && vDatosTitProp.get("cDscMunicipioEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():""));
				rep.comRemplaza("[cEntFedEPZF]",(vDatosTitProp.get("cDscEntidadFedEscPub") != null && vDatosTitProp.get("cDscEntidadFedEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():"");
				rep.comRemplaza("[cNotarioPublicoZF]",((vDatosTitProp.get("cNomRazonSocial") != null && vDatosTitProp.get("cNomRazonSocial")!= null)?vDatosTitProp.get("cNomRazonSocial").toString():"")+
						((vDatosTitProp.get("cApPaterno") != null && vDatosTitProp.get("cApPaterno")!= null)?vDatosTitProp.get("cApPaterno").toString():"")+
						((vDatosTitProp.get("cApMaterno") != null && vDatosTitProp.get("cApMaterno")!= null)?vDatosTitProp.get("cApMaterno").toString():""));

				//Informacion del Registro Publico de Comercio del Título de Propiedad.
				rep.comRemplaza("[cFecZF]",(vDatosTitProp.get("dtTitProRegPubCom") != null)?Fecha.getFechaDDMMYYYY(vDatosTitProp.getDate("dtTitProRegPubCom"),"/"):"");
				rep.comRemplaza("[cFolioZF]",(vDatosTitProp.get("cNumTitProRegPubCom") != null && vDatosTitProp.get("cNumTitProRegPubCom")!= null)?vDatosTitProp.get("cNumTitProRegPubCom").toString():"");
				rep.comRemplaza("[cFojaZF]",(vDatosTitProp.get("cNumRegPubComFoja") != null && vDatosTitProp.get("cNumRegPubComFoja")!= null)?vDatosTitProp.get("cNumRegPubComFoja").toString():"");
				rep.comRemplaza("[cToZF]",(vDatosTitProp.get("cNumRegPubComTomo") != null && vDatosTitProp.get("cNumRegPubComTomo")!= null)?vDatosTitProp.get("cNumRegPubComTomo").toString():"");
				rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cNumRegPubComSeccion") != null && vDatosTitProp.get("cNumRegPubComSeccion")!= null)?vDatosTitProp.get("cNumRegPubComSeccion").toString():"");
				rep.comRemplaza("[cLibroZF]",(vDatosTitProp.get("cNumRegPubComLibro") != null && vDatosTitProp.get("cNumRegPubComLibro")!= null)?vDatosTitProp.get("cNumRegPubComLibro").toString():"");
				rep.comRemplaza("[cVolZF]",(vDatosTitProp.get("cNumRegPubComVolumen") != null && vDatosTitProp.get("cNumRegPubComVolumen")!= null)?vDatosTitProp.get("cNumRegPubComVolumen").toString():"");
				rep.comRemplaza("[cCiudadZF]",((vDatosTitProp.get("cDscLocalidadRegPubCom") != null && vDatosTitProp.get("cDscLocalidadRegPubCom")!= null)?vDatosTitProp.get("cDscLocalidadRegPubCom").toString():"")+
						((vDatosTitProp.get("cDscMunicipioRegPubCom") != null && vDatosTitProp.get("cDscMunicipioRegPubCom")!= null)?vDatosTitProp.get("cDscMunicipioRegPubCom").toString():""));
				rep.comRemplaza("[cEntFedRPCZF]",(vDatosTitProp.get("cDscEntidadFedRegPubCom") != null && vDatosTitProp.get("cDscEntidadFedRegPubCom")!= null)?vDatosTitProp.get("cDscEntidadFedRegPubCom").toString():"");
			} else {
				//Informacion del Título de Propiedad.
				rep.comRemplaza("[cSupZF]","");
				rep.comRemplaza("[cLoteZF]","");
				rep.comRemplaza("[cSecZF]","");
				rep.comRemplaza("[cManZF]","");
				rep.comRemplaza("[cNoZF]","");
				rep.comRemplaza("[cCalZF]","");
				rep.comRemplaza("[cKMZF]","");
				rep.comRemplaza("[cColZF]","");
				rep.comRemplaza("[cPuertoZF]","");
				rep.comRemplaza("[cMunZF]","");
				rep.comRemplaza("[cEntFedZF]","");

				//Informacion de la Escritura Publica del Título de Propiedad.
				rep.comRemplaza("[cNoEscZF]","");
				rep.comRemplaza("[cFechaZF]","");
				rep.comRemplaza("[cNoNotPubZF]","");
				rep.comRemplaza("[cCiuZF]","");
				rep.comRemplaza("[cEntFedEPZF]","");
				rep.comRemplaza("[cNotarioPublicoZF]","");

				//Informacion del Registro Publico de Comercio del Título de Propiedad.
				rep.comRemplaza("[cFecZF]","");
				rep.comRemplaza("[cFolioZF]","");
				rep.comRemplaza("[cFojaZF]","");
				rep.comRemplaza("[cToZF]","");
				rep.comRemplaza("[cSecZF]","");
				rep.comRemplaza("[cLibroZF]","");
				rep.comRemplaza("[cVolZF]","");
				rep.comRemplaza("[cCiudadZF]","");
				rep.comRemplaza("[cEntFedRPCZF]","");
			}

			if (vRegsTituloDocumentos.size()>0){
				for(int i=0;i<vRegsTituloDocumentos.size();i++){
					TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
					switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

					//Titulo de Concesion de la Zona Federal Marítimo Terrestre.
					case 1:case 2:case 3:case 4:{
						if (vDatosTitDoctos.get("lMaritimoTerrestre") != null &&
								Integer.parseInt(vDatosTitDoctos.get("lMaritimoTerrestre").toString()) == 1){
							rep.comRemplaza("[lMarTitConZF]","X");
							rep.comRemplaza("[lFluvialTitConZF]","");
						} else {
							rep.comRemplaza("[lMarTitConZF]","");
							rep.comRemplaza("[lFluvialTitConZF]","X");
						}
						rep.comRemplaza("[cNoTitConZF]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[cFecTitConZF]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cDepEmiteTitConZF]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
						rep.comRemplaza("[cObjetoTitConZF]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
						rep.comRemplaza("[cVigenciaTitConZF]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						rep.comRemplaza("[cSupTitConZF]",(vDatosTitDoctos.get("cSuperficieDocumento")!= null)?vDatosTitDoctos.get("cSuperficieDocumento").toString():"");
						lEntroZOFEMAT = 1;
						break;
					}

					//Autorizacion en Materia de Impacto Ambiental.
					case 5:case 6:{
						rep.comRemplaza("[cNoAutIA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[cFechaAutIA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cDepEmiteAutIA]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
						rep.comRemplaza("[cObjetoAutIA]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
						rep.comRemplaza("[cVigenciaAutIA]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						lEntroImpAmb = 1;
						break;
					}

					//Franquicia de PEMEX.
					case 7:{
						rep.comRemplaza("[cNoFPEMEX]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[cFechaFPEMEX]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cVigenciaFPEMEX]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						lEntroPEMEX = 1;
						break;
					}

					//Autorizacion de la CONAGUA.
					case 8:{
						rep.comRemplaza("[cNoAutCONAGUA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[cFechaAutCONAGUA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cVigenciaAutCONAGUA]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						lEntroCONAGUA = 1;
						break;
					}
					//Autorizacion de la SEMARNAT.
					case 9:{
						rep.comRemplaza("[cNoAutSEMARNAT]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[cFechaAutSEMARNAT]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cVigenciaAutSEMARNAT]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						lEntroSEMARNAT = 1;
						break;
					}
					}
				}
			}

			if (lEntroZOFEMAT == 0){
				rep.comRemplaza("[cNoTitConZF]","");
				rep.comRemplaza("[cFecTitConZF]","");
				rep.comRemplaza("[cDepEmiteTitConZF]","");
				rep.comRemplaza("[cObjetoTitConZF]","");
				rep.comRemplaza("[cVigenciaTitConZF]","");
				rep.comRemplaza("[cSupTitConZF]","");
			}

			if (lEntroImpAmb == 0){
				rep.comRemplaza("[cNoAutIA]","");
				rep.comRemplaza("[cFechaAutIA]","");
				rep.comRemplaza("[cDepEmiteAutIA]","");
				rep.comRemplaza("[cObjetoAutIA]","");
				rep.comRemplaza("[cVigenciaAutIA]","");
			}

			if (lEntroPEMEX == 0){
				rep.comRemplaza("[cNoFPEMEX]","");
				rep.comRemplaza("[cFechaFPEMEX]","");
				rep.comRemplaza("[cVigenciaFPEMEX]","");
			}

			if (lEntroCONAGUA == 0){
				rep.comRemplaza("[cNoAutCONAGUA]","");
				rep.comRemplaza("[cFechaAutCONAGUA]","");
				rep.comRemplaza("[cVigenciaAutCONAGUA]","");

			}

			if (lEntroSEMARNAT == 0){
				rep.comRemplaza("[cNoAutSEMARNAT]","");
				rep.comRemplaza("[cFechaAutSEMARNAT]","");
				rep.comRemplaza("[cVigenciaAutSEMARNAT]","");
			}

			//Informacion de la Solicitud
			if (vRegsTituloSolicitud.size()>0){
				TVDinRep vDatosTitSolicutud = (TVDinRep) vRegsTituloSolicitud.get(0);
				rep.comRemplaza("[cNoSolicitud]",((vDatosTitSolicutud.get("iEjercicio")!= null)?vDatosTitSolicutud.get("iEjercicio").toString():"")+
						((vDatosTitSolicutud.get("iNumSolicitud")!= null)?vDatosTitSolicutud.get("iNumSolicitud").toString():""));
				rep.comRemplaza("[cLugarSolicitud]",((vDatosTitSolicutud.get("cCalleyNo")!= null)?vDatosTitSolicutud.get("cCalleyNo").toString():"")+
						((vDatosTitSolicutud.get("cColonia")!= null)?vDatosTitSolicutud.get("cColonia").toString():"")+
						((vDatosTitSolicutud.get("cCodPostal")!= null)?vDatosTitSolicutud.get("cCodPostal").toString():"")+
						((vDatosTitSolicutud.get("cDscMunicipio")!= null)?vDatosTitSolicutud.get("cDscMunicipio").toString():"")+
						((vDatosTitSolicutud.get("cDscEntidadFed")!= null)?vDatosTitSolicutud.get("cDscEntidadFed").toString():""));
				rep.comRemplaza("[cFechaSolicitud]",(vDatosTitSolicutud.get("dtRegistro")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitSolicutud.getDate("dtRegistro"),"/"):"");

				//Modalidad del Tramite.
				int lRegistro=0,lModificacion=0,lAmpliacion=0,lProrroga=0,lRenovacion=0;
				switch (vDatosTitSolicutud.getInt("iCveModalidad")){
				//Registro.
				case 1:{
					rep.comRemplaza("[no]"," ");
					rep.comRemplaza("[na]"," ");
					rep.comRemplaza("[np]"," ");
					rep.comRemplaza("[nr]"," ");
					lRegistro=1;
					break;
				}
				//Modificacion.
				case 2:{
					rep.comRemplaza("[no]","X");
					rep.comRemplaza("[na]"," ");
					rep.comRemplaza("[np]"," ");
					rep.comRemplaza("[nr]"," ");
					lModificacion=1;
					break;
				}
				//Ampliación.
				case 3:{
					rep.comRemplaza("[no]"," ");
					rep.comRemplaza("[na]","X");
					rep.comRemplaza("[np]"," ");
					rep.comRemplaza("[nr]"," ");
					lAmpliacion=1;
					break;
				}
				//Prorroga.
				case 4:{
					rep.comRemplaza("[no]"," ");
					rep.comRemplaza("[na]"," ");
					rep.comRemplaza("[np]","X");
					rep.comRemplaza("[nr]"," ");
					lProrroga=1;
					break;
				}
				//Renovacion.
				case 5:{
					rep.comRemplaza("[no]"," ");
					rep.comRemplaza("[na]"," ");
					rep.comRemplaza("[np]"," ");
					rep.comRemplaza("[nr]","X");
					lRenovacion=1;
					break;
				}
				}


			} else {
				rep.comRemplaza("[cNoSolicitud]","");
				rep.comRemplaza("[cLugarSolicitud]","");
				rep.comRemplaza("[cFechaSolicitud]","");
			}

			//Titulo Anterior.
			if (vRegsTituloAnterior.size()>0){
				for(int i=0;i<vRegsTituloAnterior.size();i++){
					TVDinRep vTituloAnt = (TVDinRep) vRegsTituloAnterior.get(i);
					//Titulo Anterior de Tipo Concesión.
					if(vTituloAnt.getInt("iCveTipoTitulo") == 1){
//						rep.comRemplaza("[cDscOtorgado]","El Presidente de la República a travéz de la Dirección General de Puertos");
						rep.comRemplaza("[l10]","X");
						rep.comRemplaza("[l11]"," ");
						rep.comRemplaza("[l12]"," ");
					}
					//Titulo Anterior de Tipo Permiso.
					if(vTituloAnt.getInt("iCveTipoTitulo") == 2){
//						rep.comRemplaza("[cDscOtorgado]","La Dirección General de Puertos");
						rep.comRemplaza("[l10]"," ");
						rep.comRemplaza("[l11]","X");
						rep.comRemplaza("[l12]"," ");
					}
					//Titulo Anterior de Tipo Autorización.
					if(vTituloAnt.getInt("iCveTipoTitulo") == 3){
//						rep.comRemplaza("[cDscOtorgado]","La Dirección General de Puertos");
						rep.comRemplaza("[l10]"," ");
						rep.comRemplaza("[l11]"," ");
						rep.comRemplaza("[l12]","X");
					}
					rep.comRemplaza("[cNumTitAnt]", vTituloAnt.getString("cNumTitulo")!=null?vTituloAnt.getString("cNumTitulo"):"");
					rep.comRemplaza("[cFecTitAnt]", vTituloAnt.getDate("dtPublicacionDOF")!=null?Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtPublicacionDOF"),"/"):"");
					rep.comRemplaza("[cDepEmiTitAnt]","Dirección General de Puertos");
					rep.comRemplaza("[cFVigTitAnt]", vTituloAnt.getDate("dtVigenciaTitulo")!=null?Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtVigenciaTitulo"),"/"):"");
					rep.comRemplaza("[cIVigTitAnt]", vTituloAnt.getDate("dtIniVigenciaTitulo")!=null?Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtIniVigenciaTitulo"),"/"):"");
				}
			} else {
				rep.comRemplaza("[l10]", " ");
				rep.comRemplaza("[l11]"," ");
				rep.comRemplaza("[l12]"," ");
				rep.comRemplaza("[cNumTitAnt]","");
				rep.comRemplaza("[cFecTitAnt]","");
				rep.comRemplaza("[cDepEmiTitAnt]","");
				rep.comRemplaza("[cFVigTitAnt]","");
				rep.comRemplaza("[cIVigTitAnt]","");
			}

			//Uso Publico o Particular.
			if(vDatos.getInt("iCveUsoTitulo") == 1){
				rep.comRemplaza("[U1]","X");
				rep.comRemplaza("[U2]"," ");
			}
			if(vDatos.getInt("iCveUsoTitulo") == 2){
				rep.comRemplaza("[U1]"," ");
				rep.comRemplaza("[U2]","X");
			}

			//Tipo de Concesion, Permiso o Autorización.
			int lConcesion=0,lPermiso=0,lAutorizacion=0;
			switch (vDatos.getInt("iCveClasificacion")){
			//Concesiones.
			//Puerto.
			case(1):{
				rep.comRemplaza("[cp]","X");
				rep.comRemplaza("[ct]"," ");
				rep.comRemplaza("[cn]"," ");
				rep.comRemplaza("[ci]"," ");
				rep.comRemplaza("[cp]"," ");
				rep.comRemplaza("[cv]"," ");
				lConcesion=1;
				break;
			}
			//Terminal.
			case(2):{
				rep.comRemplaza("[cp]"," ");
				rep.comRemplaza("[ct]","X");
				rep.comRemplaza("[cn]"," ");
				rep.comRemplaza("[ci]"," ");
				rep.comRemplaza("[cv]"," ");
				lConcesion=1;
				break;
			}
			//Marina.
			case(3):{
				rep.comRemplaza("[cp]"," ");
				rep.comRemplaza("[ct]"," ");
				rep.comRemplaza("[cn]","X");
				rep.comRemplaza("[ci]"," ");
				rep.comRemplaza("[cv]"," ");
				lConcesion=1;
				break;
			}
			//Instalación Portuaria.
			case(4):{
				rep.comRemplaza("[cp]"," ");
				rep.comRemplaza("[ct]"," ");
				rep.comRemplaza("[cn]"," ");
				rep.comRemplaza("[ci]","X");
				rep.comRemplaza("[cv]"," ");
				lConcesion=1;
				break;
			}
			//Permisos.
			//Embarcadero.
			case(5):{
				rep.comRemplaza("[pe]","X");
				rep.comRemplaza("[pa]"," ");
				rep.comRemplaza("[pb]"," ");
				rep.comRemplaza("[ps]"," ");
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[pc]"," ");
				lPermiso=1;
				break;
			}
			//Atracadero.
			case(6):{
				rep.comRemplaza("[pe]"," ");
				rep.comRemplaza("[pa]","X");
				rep.comRemplaza("[pb]"," ");
				rep.comRemplaza("[ps]"," ");
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[pc]"," ");
				lPermiso=1;
				break;
			}
			//Botadero.
			case(7):{
				rep.comRemplaza("[pe]"," ");
				rep.comRemplaza("[pa]"," ");
				rep.comRemplaza("[pb]","X");
				rep.comRemplaza("[ps]"," ");
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[pc]"," ");
				lPermiso=1;
				break;
			}
			//Similar.
			case(8):{
				rep.comRemplaza("[pe]"," ");
				rep.comRemplaza("[pa]"," ");
				rep.comRemplaza("[pb]"," ");
				rep.comRemplaza("[ps]","X");
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[pc]"," ");
				lPermiso=1;
				break;
			}
			//Servicio Portuario.
			case(9):{
				rep.comRemplaza("[pe]"," ");
				rep.comRemplaza("[pa]"," ");
				rep.comRemplaza("[pb]"," ");
				rep.comRemplaza("[ps]"," ");
				rep.comRemplaza("[pp]","X");
				rep.comRemplaza("[pc]"," ");
				lPermiso=1;
				break;
			}
			//Servicio Conexo.
			case(10):{
				rep.comRemplaza("[pe]"," ");
				rep.comRemplaza("[pa]"," ");
				rep.comRemplaza("[pb]"," ");
				rep.comRemplaza("[ps]"," ");
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[pc]","X");
				lPermiso=1;
				break;
			}
			//Autorizaciones.
			//Obra Marítima.
			case(11):{
				rep.comRemplaza("[ao]","X");
				rep.comRemplaza("[ad]"," ");
				rep.comRemplaza("[am]"," ");
				rep.comRemplaza("[aa]"," ");
				rep.comRemplaza("[ac]"," ");
				rep.comRemplaza("[ap]"," ");
				rep.comRemplaza("[ae]"," ");
				lAutorizacion=1;
				break;
			}
			//Dragado de Construcción.
			case(12):{
				rep.comRemplaza("[ao]"," ");
				rep.comRemplaza("[ad]","X");
				rep.comRemplaza("[am]"," ");
				rep.comRemplaza("[aa]"," ");
				rep.comRemplaza("[ac]"," ");
				rep.comRemplaza("[ap]"," ");
				rep.comRemplaza("[ae]"," ");
				lAutorizacion=1;
				break;
			}
			//Dragado de Mantenimiento.
			case(13):{
				rep.comRemplaza("[ao]"," ");
				rep.comRemplaza("[ad]"," ");
				rep.comRemplaza("[am]","X");
				rep.comRemplaza("[aa]"," ");
				rep.comRemplaza("[ac]"," ");
				rep.comRemplaza("[ap]"," ");
				rep.comRemplaza("[ae]"," ");
				lAutorizacion=1;
				break;
			}
			//Obra a Cargo de un AP.
			case(14):{
				rep.comRemplaza("[ao]"," ");
				rep.comRemplaza("[ad]"," ");
				rep.comRemplaza("[am]"," ");
				rep.comRemplaza("[aa]","X");
				rep.comRemplaza("[ac]"," ");
				rep.comRemplaza("[ap]"," ");
				rep.comRemplaza("[ae]"," ");
				lAutorizacion=1;
				break;
			}
			//Inicio de Construcción.
			case(15):{
				rep.comRemplaza("[ao]"," ");
				rep.comRemplaza("[ad]"," ");
				rep.comRemplaza("[am]"," ");
				rep.comRemplaza("[aa]"," ");
				rep.comRemplaza("[ac]","X");
				rep.comRemplaza("[ap]"," ");
				rep.comRemplaza("[ae]"," ");
				lAutorizacion=1;
				break;
			}
			//Inicio de Operación.
			case(16):{
				rep.comRemplaza("[ao]"," ");
				rep.comRemplaza("[ad]"," ");
				rep.comRemplaza("[am]"," ");
				rep.comRemplaza("[aa]"," ");
				rep.comRemplaza("[ac]"," ");
				rep.comRemplaza("[ap]","X");
				rep.comRemplaza("[ae]"," ");
				lAutorizacion=1;
				break;
			}
			//Cesión de Derechos.
			case(17):{
				rep.comRemplaza("[ao]"," ");
				rep.comRemplaza("[ad]"," ");
				rep.comRemplaza("[am]"," ");
				rep.comRemplaza("[aa]"," ");
				rep.comRemplaza("[ac]"," ");
				rep.comRemplaza("[ap]"," ");
				rep.comRemplaza("[ae]","X");
				lAutorizacion=1;
				break;
			}
			}

			if (lConcesion==0 ){
				rep.comRemplaza("[cp]"," ");
				rep.comRemplaza("[ct]"," ");
				rep.comRemplaza("[cn]"," ");
				rep.comRemplaza("[ci]"," ");
				rep.comRemplaza("[cp]"," ");
				rep.comRemplaza("[cv]"," ");
			}
			if (lPermiso==0){
				rep.comRemplaza("[pe]"," ");
				rep.comRemplaza("[pa]"," ");
				rep.comRemplaza("[pb]"," ");
				rep.comRemplaza("[ps]"," ");
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[pc]"," ");
			}
			if (lAutorizacion==0){
				rep.comRemplaza("[ao]"," ");
				rep.comRemplaza("[ad]"," ");
				rep.comRemplaza("[am]"," ");
				rep.comRemplaza("[aa]"," ");
				rep.comRemplaza("[ac]"," ");
				rep.comRemplaza("[ap]"," ");
				rep.comRemplaza("[ae]"," ");
			}

			//Objeto del Título y Ubicacion del Area Solicitada.
			rep.comRemplaza("[cObjetoTitulo]",(vDatos.get("cObjetoTitulo")!= null)?vDatos.get("cObjetoTitulo").toString():"");
			rep.comRemplaza("[cUbiZonFedTerrestre]",(vDatos.get("cZonaFedAfectadaTerrestre")!= null)?vDatos.get("cZonaFedAfectadaTerrestre").toString():"");
			rep.comRemplaza("[cUbiZonFedAgua]",(vDatos.get("cZonaFedAfectadaAgua")!= null)?vDatos.get("cZonaFedAfectadaAgua").toString():"");

			if (vRegsTituloUbicacion.size()>0){
				TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);
				rep.comRemplaza("[cCalleFRP]",(vDatosTitUbi.get("cCalleTitulo")!= null)?vDatosTitUbi.get("cCalleTitulo").toString():"");
				rep.comRemplaza("[cKmFRP]",(vDatosTitUbi.get("cKm")!= null)?vDatosTitUbi.get("cKm").toString():"");
				rep.comRemplaza("[cColFRP]",(vDatosTitUbi.get("cColoniaTitulo")!= null)?vDatosTitUbi.get("cColoniaTitulo").toString():"");
				rep.comRemplaza("[cPuertoFRP]",(vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
				rep.comRemplaza("[cMunFRP]",(vDatosTitUbi.get("cDscMunicipio")!= null)?vDatosTitUbi.get("cDscMunicipio").toString():"");
				rep.comRemplaza("[cEntFedFRP]",(vDatosTitUbi.get("cDscEntidadFed")!= null)?vDatosTitUbi.get("cDscEntidadFed").toString():"");

				//Ubicación del Area Solicitada.
				rep.comRemplaza("[U3]","X");

				if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1){
					rep.comRemplaza("[U4]","X");
					rep.comRemplaza("[U5]"," ");
				} else {
					rep.comRemplaza("[U4]"," ");
					rep.comRemplaza("[U5]","X");
				}
			} else {
				rep.comRemplaza("[cCalleFRP]","");
				rep.comRemplaza("[cKmFRP]","");
				rep.comRemplaza("[cColFRP]","");
				rep.comRemplaza("[cPuertoFRP]","");
				rep.comRemplaza("[cMunFRP]","");
				rep.comRemplaza("[cEntFedFRP]","");

				//Ubicación del Area Solicitada.
				rep.comRemplaza("[U3]"," ");
				rep.comRemplaza("[U4]"," ");
				rep.comRemplaza("[U5]"," ");
			}

			//Obras del Título.
			int lPropNacional= 0,lConst=0,lOper=0;
			String cObras = "";
			if (vRegsTituloObras.size()>0){
				for(int i=0;i<vRegsTituloObras.size();i++){
					TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(i);
					if (cObras.compareTo("") != 0)
						cObras = cObras  + ", " + vDatosTitObras.getString("cDscObra");
					else
						cObras = vDatosTitObras.getString("cDscObra");

					if (vDatosTitObras.getInt("lPropiedadNacional") == 1 && lPropNacional == 0){
						rep.comRemplaza("[o1]","X");
						lPropNacional = 1;
					}
					if (vDatosTitObras.getInt("lConstruccion") == 1 && lConst == 0){
						rep.comRemplaza("[o2]","X");
						lConst = 1;
					}
					if (vDatosTitObras.getInt("lOperacion") == 1 && lOper == 0){
						rep.comRemplaza("[o3]","X");
						lOper = 1;
					}
				}
				if (cObras.compareTo("")!= 0)
					cObras = cObras + ".";
				rep.comRemplaza("[cObras]",cObras);

				TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(0);
				rep.comRemplaza("[cTiempoEstimadoObra]",(vDatosTitObras.get("cTiempoEstEjecucion")!= null)?vDatosTitObras.get("cTiempoEstEjecucion").toString():"");
			}
			if (cObras.compareTo("")==0){
				rep.comRemplaza("[cObras]","");
				rep.comRemplaza("[cTiempoEstimadoObra]","");
			}
			if (lPropNacional == 0)
				rep.comRemplaza("[o1]"," ");
			if (lConst == 0)
				rep.comRemplaza("[o2]"," ");
			if (lOper == 0)
				rep.comRemplaza("[o3]"," ");

			//Servicios Portuarios Relacionados al Título.
			if (vRegsTituloServPortuarios.size()>0){
				String cServicios = "";
				for(int i=0;i<vRegsTituloServPortuarios.size();i++){
					TVDinRep vDatosTitServ = (TVDinRep) vRegsTituloServPortuarios.get(i);
					if (cServicios.compareTo("") != 0)
						cServicios = cServicios  + ", " + vDatosTitServ.getString("cDscServicioPortuario");
					else
						cServicios = vDatosTitServ.getString("cDscServicioPortuario");
				}
				if (cServicios.compareTo("")!= 0)
					cServicios = cServicios + ".";
				rep.comRemplaza("[cSerPortuarios1]",cServicios);
			} else
				rep.comRemplaza("[cSerPortuarios1]"," ");

			//Monto de Inversion.
			rep.comRemplaza("[cMontoInversion]",(vDatos.get("cMontoInversion")!= null)?vDatos.get("cMontoInversion").toString():"");

			int i1=0,i2=0,i3=0,i4=0,i5=0,i6=0,i7=0,i8=0,i9=0,i10=0;
			int i11=0,i12=0,i13=0,i14=0,i15=0,i16=0,i17=0,i18=0,i19=0,i20=0;
			int i21=0,i22=0,i23=0,i24=0,i25=0,i26=0,i27=0,i28=0,i29=0;

			//Anexos del Título.
			if (vRegsTituloAnexos.size()>0){
				for(int i=0;i<vRegsTituloAnexos.size();i++){
					TVDinRep vDatosTitAnexo = (TVDinRep) vRegsTituloAnexos.get(i);
					switch (Integer.parseInt(vDatosTitAnexo.get("iCveAnexo").toString())){
					//Personalidad.
					case 1:{
						rep.comRemplaza("[a1]","X");
						i1 = 1;
						break;
					}
					//Persona Física.
					case 2:{
						rep.comRemplaza("[a2]","X");
						rep.comRemplaza("[a3]"," ");
						i2 = 1;
						break;
					}
					//Persona Moral.
					case 3:{
						rep.comRemplaza("[a2]"," ");
						rep.comRemplaza("[a3]","X");
						i3 = 1;
						break;
					}
					//Copia de la Cedula de RFC.
					case 4:{
						rep.comRemplaza("[b1]","X");
						i4 = 1;
						break;
					}
					//Copia Certificada del Poder del Representante Legal.
					case 5:{
						rep.comRemplaza("[c1]","X");
						i5 = 1;
						break;
					}
					//Proyecto Ejecutivo.
					case 6:{
						rep.comRemplaza("[d1]","X");
						i6 = 1;
						break;
					}
					//Copia Certificada del Título de Propiedad del Terreno Colindante.
					case 7:{
						rep.comRemplaza("[e1]","X");
						i7 = 1;
						break;
					}
					//Copia del Título de Concesión de la Zona Federal Marítimoterrestre
					case 8:{
						rep.comRemplaza("[f1]","X");
						i8 = 1;
						break;
					}
					//Copia de la Autorización en Materia de Impacto Ambiental.
					case 9:{
						rep.comRemplaza("[g1]","X");
						i9 = 1;
						break;
					}
					//Copia del oficio por el que la Secretaría de Hacienda y Crédito Público.
					case 10:{
						rep.comRemplaza("[h1]","X");
						i10 = 1;
						break;
					}
					//Documentación con la cual se acredite contar con los Recursos Financieros Materiales y Humanos.
					case 11:{
						rep.comRemplaza("[i1]","X");
						i11 = 1;
						break;
					}
					//Carta(s) de institución(es) bancaria(s)
					case 12:{
						rep.comRemplaza("[i2]","X");
						i12 = 1;
						break;
					}
					//Línea(s) de Crédito Abiertas con Disposición Inmediata.
					case 13:{
						rep.comRemplaza("[i3]","X");
						i13 = 1;
						break;
					}
					//Estados Financieros Auditados del último ejercicio.
					case 14:{
						rep.comRemplaza("[i4]","X");
						i14 = 1;
						break;
					}
					//Contrato de Obra.
					case 15:{
						rep.comRemplaza("[i5]","X");
						i15 = 1;
						break;
					}
					//Precontrato con Costo y Materiales a Ocupar.
					case 16:{
						rep.comRemplaza("[i6]","X");
						i16 = 1;
						break;
					}
					//Especificación de los Materiales de Construcción con que cuentan.
					case 17:{
						rep.comRemplaza("[i7]","X");
						i17 = 1;
						break;
					}
					//De ser embarcaciones, se deberá anexar Copia del Certificado de Matrícula y de Seguridad Marítima vigentes.
					case 18:{
						rep.comRemplaza("[i8]","X");
						i18 = 1;
						break;
					}
					//Fotografías del equipo.
					case 19:{
						rep.comRemplaza("[i9]","X");
						i19 = 1;
						break;
					}
					//Comprobante de pago al Instituto Mexicano del Seguro Social.
					case 20:{
						rep.comRemplaza("[ii1]","X");
						i20 = 1;
						break;
					}
					//Ultima Nómina.
					case 21:{
						rep.comRemplaza("[ii2]","X");
						i21 = 1;
						break;
					}
					//Precontrato o Carta de Intención de Obra.
					case 22:{
						rep.comRemplaza("[ii3]","X");
						i22 = 1;
						break;
					}
					//En caso de Servicios Portuarios, propuesta de Plantilla de Personal.
					case 23:{
						rep.comRemplaza("[ii4]","X");
						i23 = 1;
						break;
					}
					//Compromisos de Calidad.
					case 24:{
						rep.comRemplaza("[j1]","X");
						i24 = 1;
						break;
					}
					//Metas de Productividad Calendarizadas.
					case 25:{
						rep.comRemplaza("[k1]","X");
						i25 = 1;
						break;
					}
					//Características Técnicas.
					case 26:{
						rep.comRemplaza("[l1]","X");
						i26 = 1;
						break;
					}
					//Plano(s) aprobado(s) y/o croquis del área donde se prestara el servicio(s).
					case 27:{
						rep.comRemplaza("[n1]","X");
						i27 = 1;
						break;
					}
					//Dentro del Recinto Portuario.
					case 28:{
						rep.comRemplaza("[n2]","X");
						rep.comRemplaza("[n3]"," ");
						i28 = 1;
						break;
					}
					//Fuera del Recinto Portuario.
					case 29:{
						rep.comRemplaza("[n2]"," ");
						rep.comRemplaza("[n3]","X");
						i29 = 1;
						break;
					}
					}
				}
			}
			if (i1==0) rep.comRemplaza("[a1]"," ");
			if (i2==0) rep.comRemplaza("[a2]"," ");
			if (i3==0) rep.comRemplaza("[a3]"," ");
			if (i4==0) rep.comRemplaza("[b1]"," ");
			if (i5==0) rep.comRemplaza("[c1]"," ");
			if (i6==0) rep.comRemplaza("[d1]"," ");
			if (i7==0) rep.comRemplaza("[e1]"," ");
			if (i8==0) rep.comRemplaza("[f1]"," ");
			if (i9==0) rep.comRemplaza("[g1]"," ");
			if (i10==0) rep.comRemplaza("[h1]"," ");
			if (i11==0) rep.comRemplaza("[i1]"," ");
			if (i12==0) rep.comRemplaza("[i2]"," ");
			if (i13==0) rep.comRemplaza("[i3]"," ");
			if (i14==0) rep.comRemplaza("[i4]"," ");
			if (i15==0) rep.comRemplaza("[i5]"," ");
			if (i16==0) rep.comRemplaza("[i6]"," ");
			if (i17==0) rep.comRemplaza("[i7]"," ");
			if (i18==0) rep.comRemplaza("[i8]"," ");
			if (i19==0) rep.comRemplaza("[i9]"," ");
			if (i20==0) rep.comRemplaza("[ii1]"," ");
			if (i21==0) rep.comRemplaza("[ii2]"," ");
			if (i22==0) rep.comRemplaza("[ii3]"," ");
			if (i23==0) rep.comRemplaza("[ii4]"," ");
			if (i24==0) rep.comRemplaza("[j1]"," ");
			if (i25==0) rep.comRemplaza("[k1]"," ");
			if (i26==0) rep.comRemplaza("[l1]"," ");
			if (i27==0) rep.comRemplaza("[n1]"," ");
			if (i28==0) rep.comRemplaza("[n2]"," ");
			if (i29==0) rep.comRemplaza("[n3]"," ");

			//Fecha de Expedición.
			rep.comRemplaza("[cFecExpedicion]",Fecha.getFechaDDMMYYYY(Fecha.TodaySQL(),"/"));
			rep.comRemplaza("[cFecVigenciaTit]",vDatos.getDate("dtVigenciaTitulo")!=null?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"):"");
			rep.comRemplaza("[cRepLegalCompleto]",cRepLegalCompleto);


			rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
			rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
			rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
			rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

			dtConcesion = vDatos.getDate("dtIniVigenciaTitulo");

			//System.out.print("Valor de la Fecha de Concesión: " + dtConcesion);


			//Fuera de Recinto Portuario
			if (vDatos.getInt("lDentroRecintoPort") == 0){
				rep.comRemplaza("[ir]"," ");
				rep.comRemplaza("[if]","X");
				rep.comRemplaza("[it]","X");
				rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
				rep.comRemplaza("[ia]","X");
				//Maritima
				rep.comRemplaza("[im]","X");
				rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
				//Fluvial
				rep.comRemplaza("[iu]","");
				rep.comRemplaza("[flu]","");
				//Lacustre
				rep.comRemplaza("[il]","");
				rep.comRemplaza("[lac]","");
				//Total
				rep.comRemplaza("[tot]","");
			}
			//Dentro de Recinto Portuario.
			if (vDatos.getInt("lDentroRecintoPort") == 1){
				rep.comRemplaza("[ir]","X");
				rep.comRemplaza("[if]"," ");
				rep.comRemplaza("[it]"," ");
				rep.comRemplaza("[cTerrestre]","");
				rep.comRemplaza("[ia]"," ");
				//Maritima
				rep.comRemplaza("[im]"," ");
				rep.comRemplaza("[mar]"," ");
				//Fluvial
				rep.comRemplaza("[iu]","");
				rep.comRemplaza("[flu]","");
				//Lacustre
				rep.comRemplaza("[il]","");
				rep.comRemplaza("[lac]","");
				//Total
				rep.comRemplaza("[tot]","");
			}

			//Propiedad Nacional.
			if (vDatos.getInt("[iPropiedad]") == 0){
				rep.comRemplaza("[pn]","X");
				rep.comRemplaza("[pr]"," ");
			}
			//Propiedad Particular.
			if (vDatos.getInt("[iPropiedad]") == 1){
				rep.comRemplaza("[pn]"," ");
				rep.comRemplaza("[pr]","X");
			}

			//Frente a la Zona Federal Marítimo Terrestre.
			if (vRegsTituloProp.size()>0){
				TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
				String cZonaFederal = "";
				if (vDatosTitProp.get("cLotePropiedad")!= null)
					cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
				if (vDatosTitProp.get("cSeccionPropiedad")!= null)
					cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
				if (vDatosTitProp.get("cManzanaPropiedad")!= null)
					cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
				if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
					cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
				if (vDatosTitProp.get("cCallePropiedad")!= null)
					cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
				if (vDatosTitProp.get("cKmPropiedad")!= null)
					cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
				if (vDatosTitProp.get("cColoniaPropiedad")!= null)
					cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
				if (vDatosTitProp.get("cDscMunicipio")!= null)
					cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
				if (vDatosTitProp.get("cDscEntidadFed")!= null)
					cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
				if (vDatosTitProp.get("cDscPuerto")!= null)
					cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
				rep.comRemplaza("[cFrenteZona]",cZonaFederal);
			}

			if (vRegsTituloDocumentos.size()>0){
				for(int i=0;i<vRegsTituloDocumentos.size();i++){
					TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
					switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

					//Titulo de Concesion de la Zona Federal Marítimo Terrestre.
					case 1:case 2:{
						rep.comRemplaza("[tc]","X");
						rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
						rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
						rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
						rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
						break;
					}

					//Autorizacion en Materia de Impacto Ambiental.
					case 5:{
						rep.comRemplaza("[ai]","X");
						rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
						rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
						rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
						rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
						break;
					}
					}
				}
			}

			if (vRegsTitPoliza.size()>0){
				for(int i=0;i<vRegsTitPoliza.size();i++){
					TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
					//Pólizas de Seguro.
					if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
						rep.comRemplaza("[pg]","");
						rep.comRemplaza("[re]","");
						//Responsabilidad Civil.
						if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
							rep.comRemplaza("[in]","X");
							rep.comRemplaza("[rc]"," ");
						}
						//Instalaciones Portuarias.
						if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
							rep.comRemplaza("[in]"," ");
							rep.comRemplaza("[rc]","X");
						}
						rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
						rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
						rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
						rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
						rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
						rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
						rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
					}

					//Polizas de Fianzas
					if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
						rep.comRemplaza("[pf]","X");
						rep.comRemplaza("[ed]","X");
						rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
						rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
						rep.comRemplaza("[ImpEndoso]","");
						rep.comRemplaza("[ActEndoso]","");
						rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
						rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
						rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
						rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
					}
				}
			}

		} else {
			//Sin Valores en la Base.



		}

		//Concesion de Zona Federal Maritimo Terrestre.
		try{
			vConZonFed = dConZonFed.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch (Exception ex){
			ex.printStackTrace();
		}

		if(!vConZonFed.isEmpty()){
			Object lValor = vConZonFed.get(0);
			if (vConZonFed.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[CZF]","X");
					rep.comRemplaza("[NoSolCZFMT]"," ");
					rep.comRemplaza("[dtSolCZFMT]"," ");
				} else {
					rep.comRemplaza("[CZF]"," ");
					rep.comRemplaza("[NoSolCZFMT]"," ");
					rep.comRemplaza("[dtSolCZFMT]"," ");
				}
			} else {
				Object cNumSolZoFeMat = vConZonFed.get(1);
				Object dtSolZoFeMat = vConZonFed.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[CZF]","X");
					rep.comRemplaza("[NoSolCZFMT]",cNumSolZoFeMat.toString());
					rep.comRemplaza("[dtSolCZFMT]",dtSolZoFeMat.toString());
				} else {
					rep.comRemplaza("[CZF]"," ");
					rep.comRemplaza("[NoSolCZFMT]"," ");
					rep.comRemplaza("[dtSolCZFMT]"," ");
				}
			}
		} else {
			rep.comRemplaza("[CZF]","X");
			rep.comRemplaza("[NoSolCZFMT]"," ");
			rep.comRemplaza("[dtSolCZFMT]"," ");
		}

		//Autorizacion de Impacto Ambiental para Operar la Obra.
		try{
			vAutImpAmbOp = dCYSAutImpactoAmbiental.findBySitObligacionOp("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutImpAmbOp.isEmpty()){
			Object lValor = vAutImpAmbOp.get(0);
			if (vAutImpAmbOp.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[AIAP]","X");
					rep.comRemplaza("[NoSolAIAOperar]"," ");
					rep.comRemplaza("[dtSolAIAOperar]"," ");
				} else {
					rep.comRemplaza("[AIAP]"," ");
					rep.comRemplaza("[NoSolAIAOperar]"," ");
					rep.comRemplaza("[dtSolAIAOperar]"," ");
				}
			} else {
				Object cNumSolSemarnat = vAutImpAmbOp.get(1);
				Object dtSolSemarnat = vAutImpAmbOp.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[AIAP]","X");
					rep.comRemplaza("[NoSolAIAOperar]",cNumSolSemarnat.toString());
					rep.comRemplaza("[dtSolAIAOperar]",dtSolSemarnat.toString());
				} else {
					rep.comRemplaza("[AIAP]"," ");
					rep.comRemplaza("[NoSolAIAOperar]"," ");
					rep.comRemplaza("[dtSolAIAOperar]"," ");
				}
			}
		} else {
			rep.comRemplaza("[AIAP]","X");
			rep.comRemplaza("[NoSolAIAOperar]"," ");
			rep.comRemplaza("[dtSolAIAOperar]"," ");
		}

		//Autorizacion de Impacto Ambiental para Construir la Obra.
		try{
			vAutImpAmbConst = dCYSAutImpactoAmbiental.findBySitObligacionConst("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutImpAmbConst.isEmpty()){
			Object lValor = vAutImpAmbConst.get(0);
			if (vAutImpAmbConst.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[AIAO]","X");
					rep.comRemplaza("[NoSolAIAObra]"," ");
					rep.comRemplaza("[dtSolAIAObra]"," ");
				} else {
					rep.comRemplaza("[AIAO]"," ");
					rep.comRemplaza("[NoSolAIAObra]"," ");
					rep.comRemplaza("[dtSolAIAObra]"," ");
				}
			} else {
				Object cNumSolSemarnat = vAutImpAmbConst.get(1);
				Object dtSolSemarnat = vAutImpAmbConst.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[AIAO]","X");
					rep.comRemplaza("[NoSolAIAObra]",cNumSolSemarnat.toString());
					rep.comRemplaza("[dtSolAIAObra]",dtSolSemarnat.toString());
				} else {
					rep.comRemplaza("[AIAO]"," ");
					rep.comRemplaza("[NoSolAIAObra]"," ");
					rep.comRemplaza("[dtSolAIAObra]"," ");
				}
			}
		} else {
			rep.comRemplaza("[AIAO]","X");
			rep.comRemplaza("[NoSolAIAObra]"," ");
			rep.comRemplaza("[dtSolAIAObra]"," ");
		}

		//Autorizacion de Inicio de Construccion de la Obra.
		try{
			vAutIniConst = dCYSAutInicioConstruccion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutIniConst.isEmpty()){
			Object lValor = vAutIniConst.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[AIC]","X");
			else
				rep.comRemplaza("[AIC]"," ");
		} else {
			rep.comRemplaza("[AIC]","X");
		}

		//Autorizacion de Inicio de Operación de la Obra.
		try{
			vAutIniOpe = dCYSAutInicioOperacion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutIniOpe.isEmpty()){
			Object lValor = vAutIniOpe.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[AIO]","X");
			else
				rep.comRemplaza("[AIO]"," ");
		} else {
			rep.comRemplaza("[AIO]","X");
		}

		//Autorizacion de Senalamiento Marítimo.
		try{
			vAutSenMar = dCYSAutSenalamientoMaritimo.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vAutSenMar.isEmpty()){
			Object lValor = vAutSenMar.get(0);
			if (vAutSenMar.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[ASM]","X");
					rep.comRemplaza("[NoSolASM]"," ");
					rep.comRemplaza("[dtSolASM]"," ");
				} else {
					rep.comRemplaza("[ASM]"," ");
					rep.comRemplaza("[NoSolASM]"," ");
					rep.comRemplaza("[dtSolASM]"," ");
				}
			} else {
				Object iNumSolTramite = vAutSenMar.get(1);
				Object dtRegistro = vAutSenMar.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[ASM]","X");
					rep.comRemplaza("[NoSolASM]",iNumSolTramite.toString());
					rep.comRemplaza("[dtSolASM]",dtRegistro.toString());
				} else {
					rep.comRemplaza("[ASM]"," ");
					rep.comRemplaza("[NoSolASM]"," ");
					rep.comRemplaza("[dtSolASM]"," ");
				}
			}
		} else {
			rep.comRemplaza("[ASM]","X");
			rep.comRemplaza("[NoSolASM]"," ");
			rep.comRemplaza("[dtSolASM]"," ");
		}

		//Compromisos de Calidad.
		try{
			vCompCal = dCYSCompromisosCalidad.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vCompCal.isEmpty()){
			Object lValor = vCompCal.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[CC]","X");
			else
				rep.comRemplaza("[CC]"," ");
		} else {
			rep.comRemplaza("[CC]","X");
		}

		//Programa de Modernización.
		try{
			vProMod = dCYSProgramaModernizacion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vProMod.isEmpty()){
			Object lValor = vProMod.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[PME]","X");
			else
				rep.comRemplaza("[PME]"," ");
		} else {
			rep.comRemplaza("[PME]","X");
		}

		//Conservación y Mantenimiento.
		try{
			vConMantto = dCYSConservacionMantto.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vConMantto.isEmpty()){
			Object lValor = vConMantto.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[RAC]","X");
			else
				rep.comRemplaza("[RAC]"," ");
		} else {
			rep.comRemplaza("[RAC]","X");
		}

		//Poliza de Seguro.
		boolean lMarcar = false;
		try{
			List lTipPolObras = new ArrayList();
			List lTipPolInst = new ArrayList();
			List lTipPolEqu = new ArrayList();
			lTipPolObras.add(new Integer("1"));
			lTipPolInst.add(new Integer("2"));
			lTipPolEqu.add(new Integer("3"));


			vPolSegObrasInstPort = dCYSPolizaSeguro.findBySitObligacion("",iCveTitulo,dtHoy,lTipPolObras);
			vPolSegRespCiv = dCYSPolizaSeguro.findBySitObligacion("",iCveTitulo,dtHoy,lTipPolInst);
			vPolSegEqu = dCYSPolizaSeguro.findBySitObligacion("",iCveTitulo,dtHoy,lTipPolEqu);

		} catch(Exception ex){
			ex.printStackTrace();
		}

		//Poliza de Seguro de Obras e Instalaciones Portuarias.
		if(!vPolSegObrasInstPort.isEmpty()){
			Object lValor = vPolSegObrasInstPort.get(1);
			if (vPolSegObrasInstPort.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[OIP]","X");
					rep.comRemplaza("[cMotPSOIP]"," ");
					lMarcar = true;
				} else {
					rep.comRemplaza("[OIP]"," ");
					rep.comRemplaza("[cMotPSOIP]"," ");
				}
			} else {
				Object cMotivo = vPolSegObrasInstPort.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[OIP]","X");
					rep.comRemplaza("[cMotPSOIP]",cMotivo.toString());
					lMarcar = true;
				} else {
					rep.comRemplaza("[OIP]"," ");
					rep.comRemplaza("[cMotPSOIP]"," ");
				}
			}
		} else {
			rep.comRemplaza("[OIP]","X");
			rep.comRemplaza("[cMotPSOIP]"," ");
			lMarcar = true;
		}

		//Póliza de Seguro de Responsabilidad Civil.
		if(!vPolSegRespCiv.isEmpty()){
			Object lValor = vPolSegRespCiv.get(1);
			if (vPolSegRespCiv.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[RC]","X");
					rep.comRemplaza("[cMotPSRC]"," ");
					lMarcar = true;
				} else {
					rep.comRemplaza("[RC]"," ");
					rep.comRemplaza("[cMotPSRC]"," ");
				}
			} else {
				Object cMotivo = vPolSegRespCiv.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[RC]","X");
					rep.comRemplaza("[cMotPSRC]",cMotivo.toString());
					lMarcar = true;
				} else {
					rep.comRemplaza("[RC]"," ");
					rep.comRemplaza("[cMotPSRC]"," ");
				}
			}
		} else {
			rep.comRemplaza("[RC]","X");
			rep.comRemplaza("[cMotPSRC]"," ");
			lMarcar = true;
		}

		//Póliza de Seguro de los Equipos.
		if(!vPolSegEqu.isEmpty()){
			Object lValor = vPolSegEqu.get(1);
			if (vPolSegEqu.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[SE]","X");
					rep.comRemplaza("[cMotPSSE]"," ");
					lMarcar = true;
				} else {
					rep.comRemplaza("[SE]"," ");
					rep.comRemplaza("[cMotPSSE]"," ");
				}
			} else {
				Object cMotivo = vPolSegEqu.get(2);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[SE]","X");
					rep.comRemplaza("[cMotPSSE]",cMotivo.toString());
					lMarcar = true;
				} else {
					rep.comRemplaza("[SE]"," ");
					rep.comRemplaza("[cMotPSSE]"," ");
				}
			}
		} else {
			rep.comRemplaza("[SE]","X");
			rep.comRemplaza("[cMotPSSE]"," ");
			lMarcar = true;
		}

		if (lMarcar)
			rep.comRemplaza("[PS]","X");
		else
			rep.comRemplaza("[PS]"," ");

		//Fianza.
		try{
			vFianza = dCYSFianza.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vFianza.isEmpty()){
			Object lValor = vFianza.get(1);
			if (vFianza.size() == 1){
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[PF]","X");
					rep.comRemplaza("[cMotPF]"," ");
					lMarcar = true;
				} else {
					rep.comRemplaza("[PF]"," ");
					rep.comRemplaza("[cMotPF]"," ");
				}
			} else {
				Object cMotivo = vFianza.get(1);
				if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0){
					rep.comRemplaza("[PF]","X");
					rep.comRemplaza("[cMotPF]",cMotivo.toString());
					lMarcar = true;
				} else {
					rep.comRemplaza("[PF]"," ");
					rep.comRemplaza("[cMotPF]"," ");
				}
			}
		} else {
			rep.comRemplaza("[PF]","X");
			rep.comRemplaza("[cMotPF]"," ");
			lMarcar = true;
		}

		//Publicaciòn de la Concesión.
		try{
			vPubConcesion = dCYSPubConcesion.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vPubConcesion.isEmpty()){
			Object lValor = vPubConcesion.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[CPC]","X");
			else
				rep.comRemplaza("[CPC]"," ");
		} else {
			rep.comRemplaza("[CPC]","X");
		}

		//Registro Publico de la Propiedad Federal.
		try{
			vRegPubProFed = dCYSRegistroPublicoPropiedadFederal.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vRegPubProFed.isEmpty()){
			Object lValor = vRegPubProFed.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[RPF]","X");
			else
				rep.comRemplaza("[RPF]"," ");
		} else {
			rep.comRemplaza("[RPF]","X");
		}

		//Registro Publico de la Propiedad Estatal.
		try{
			vRegPubProEst = dCYSRegPubPropiedadEstatal.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vRegPubProEst.isEmpty()){
			Object lValor = vRegPubProEst.get(0);
			if(lValor.toString().compareTo("false")==0 || lValor.toString().compareTo("FALSE")==0)
				rep.comRemplaza("[RPE]","X");
			else
				rep.comRemplaza("[RPE]"," ");
		} else {
			rep.comRemplaza("[RPE]","X");
		}

		//Programa de Sustitucion de Titulos.
		try{
			vSustTit = dCYSProgramaSustTitulos.findTituloPresentado(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vSustTit.isEmpty()){
			for(int i=0;i<vSustTit.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vSustTit.get(i);
				//System.out.print("Programa de Sustitucion de Titulos: " + vDinRep.getInt("lExiste"));
			}
		}

		//Programa Maestro de Desarrollo Portuario.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vProMaeDesPor = dCYSProgramaMaestroDP.findProgramaMDP(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vProMaeDesPor.isEmpty()){
			for(int i=0;i<vProMaeDesPor.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vProMaeDesPor.get(i);
				//System.out.print("Programa Maestro de Desarrollo Portuario: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Programa Maestro de Desarrollo Portuario: " + vDinRep.getString("cMotivo"));
			}
		}

		//Extracto del Programa Maestro de Desarrollo Portuario.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vExtProMaeDesPor = dCYSProgramaMaestroDP.findExtractoDP(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vExtProMaeDesPor.isEmpty()){
			for(int i=0;i<vExtProMaeDesPor.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vExtProMaeDesPor.get(i);
				//System.out.print("Extracto del Programa Maestro de Desarrollo Portuario: " + vDinRep.getInt("lPresentacion"));
			}
		}

		//Programa Operativo Anual.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vProOpeAnual = dCYSProgramaOperativoAnual.findProgramaOA(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vProOpeAnual.isEmpty()){
			for(int i=0;i<vProOpeAnual.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vProOpeAnual.get(i);
				//System.out.print("Programa Operativo Anual: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Programa Operativo Anual: " + vDinRep.getString("cMotivo"));
			}
		}

		//Falta el Programa Minimo de Mantenimiento.
		//
		//

		//Reglas de Operacion.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vRegOpe = dCYSReglasOperacion.findReglasOperacion(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vRegOpe.isEmpty()){
			for(int i=0;i<vRegOpe.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Reglas de Operacion: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Reglas de Operacion: " + vDinRep.getString("cMotivo"));
			}
		}

		//Comite de Operacion.
		try{
			vComOpe = dCYSComiteOperacion.findComiteOperacion(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vComOpe.isEmpty()){
			for(int i=0;i<vComOpe.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Comite de Operacion: " + vDinRep.getString("cMotivo"));
			}
		}

		//Oficina de Quejas.
		try{
			vOfiQue = dCYSOficinaQuejas.findOficinaQuejas(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vOfiQue.isEmpty()){
			for(int i=0;i<vOfiQue.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Oficina de Quejas: " + vDinRep.getString("cMotivo"));
			}
		}

		//Sistema de Computo.
		try{
			vSisCom = dCYSSistemaComputo.findSistemaComputo(iCveTitulo);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vSisCom.isEmpty()){
			for(int i=0;i<vSisCom.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Sistema de Computo: " + vDinRep.getString("cMotivo"));
			}
		}

		//Estados Financieros.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vEdofin = dCYSEdoFinanciero.findEdoFinanciero(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vEdofin.isEmpty()){
			for(int i=0;i<vEdofin.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vRegOpe.get(i);
				//System.out.print("Estados Financieros: " + vDinRep.getInt("iEjercicio"));
				//System.out.print("Estados Financieros: " + vDinRep.getString("cMotivo"));
			}
		}

		//Otorgamiento.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vOtorgamiento = dCYSOtorgamiento.findBySitObligacion("",iCveTitulo,dtConcesion);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vOtorgamiento.isEmpty()){
			for(int i=0;i<vOtorgamiento.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vOtorgamiento.get(i);
				//System.out.print("Otorgamiento: " + vDinRep.getInt("iEjercicioNoPagado"));
				//System.out.print("Otorgamiento: " + vDinRep.getString("cPeriodoNoPagado"));
			}
		}

		//Derecho del Señalamiento Marítimo.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vDerSenalamiento = dCYSDerechoSenalamiento.SitObligacion(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vDerSenalamiento.isEmpty()){
			for(int i=0;i<vDerSenalamiento.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vDerSenalamiento.get(i);
				//System.out.print("Derecho del Señalamiento Marítimo: " + vDinRep.getInt("iExiste"));
			}
		}

		//Derecho y Explotacion 232 - A LFD.
		try{
			TVDinRep vDinRep = new TVDinRep();
			vDinRep.put("iCveTitulo",iCveTitulo);
			vDinRep.put("dtIniVigencia",dtConcesion);
			vDerExplotacion = dCYSDerechoExplotacion.findDerechoExplota(vDinRep);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		if(!vDerExplotacion.isEmpty()){
			for(int i=0;i<vDerExplotacion.size();i++){
				TVDinRep vDinRep = new TVDinRep();
				vDinRep = (TVDinRep) vDerExplotacion.get(i);
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dIngresos"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dImporte"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iMesI"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iEjercicioI"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iMesF"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getInt("iEjercicioF"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDate("dtPago"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDate("dtPresentacion"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getString("cClave"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dImpPagado"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dImpCorrecto"));
				//System.out.print("Derecho y Explotacion 232 - A LFD: " + vDinRep.getDouble("dDiferencia"));

			}
		}

		//TFechas Fecha = new TFechas("44");
		Vector vcDatos = new Vector();
		TVDinRep vDinRepActRec = new TVDinRep();
		vDinRepActRec.put("dtAdeudo",Fecha.getDateSQL(new Integer("15"),new Integer("01"),new Integer("2003")));
		vDinRepActRec.put("dtPago",Fecha.TodaySQL());
		vDinRepActRec.put("dImporte",15000.00);
		vcDatos.add(vDinRepActRec);
		vcDatos.add(vDinRepActRec);
		vcDatos.add(vDinRepActRec);

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				iCveTitular,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

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

		try{
			vDinRep = dCYSFolioProc.insert(vDinRep,null);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return vRetorno;
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

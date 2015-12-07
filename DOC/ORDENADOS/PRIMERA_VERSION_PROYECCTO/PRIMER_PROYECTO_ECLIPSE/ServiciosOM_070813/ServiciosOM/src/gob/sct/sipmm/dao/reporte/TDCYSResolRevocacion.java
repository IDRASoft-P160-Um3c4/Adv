package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;

/**
 * <p>Title: TDCYSReversion.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */

public class TDCYSResolRevocacion extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private TFechas tFecha = new TFechas("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
	private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
	private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
	private int iEjercicioOficio = 0;
	private String cDigitosFolio = "";
	private int iConsecutivo=0;

	public TDCYSResolRevocacion(){
	}


	//public Vector genReversionPFP(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
	public Vector genResolucionRevocacion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vNotifRec = new Vector();
		Vector vOfiAnterior = new Vector();
		Vector vProrroga = new Vector();
		Vector vAlegato = new Vector();
		Vector vResolSancion = new Vector();
		Vector vResolNoti = new Vector(), vSHCP =  new Vector();
		TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");
		TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
		TDCYSProrrogaAlegato dCYSProrrogaAlegato = new TDCYSProrrogaAlegato();



		int iCveOficinaDest=0,iCveDeptoDest=0,iEjercicio=0,iMovProcedimiento=0,iAniosDuracion=0;
		String cTitular="", cFechaNotif="",cIniVigencia="",cUsoTitulo="",cEntidadFed="",cFolioNotif="";
		String cTipoTitulo="",cPseudonimoTitular="", cDscOtorgado="",cObjetoTitulo="",cAreaSolic="",cEntidad="",cFechaEscrito="",cFechaOfProrroga="";
		String cOfProrroga="",cFeNotPro="",cFechaOfAlegato="",cOfAlegato="",cFeNotAle="",cFeIniAle="",cFecFinAle="";
		String cMunicipioEnt="", cPuerto="",cAsunto="Resolución de Revocación. ",cOfSancion="",cFechaSancion="";
		String cFechaConcesion="",cOfResolSancion="",cFecResolSancion="", cDscRevoca = "";
		String[] aFiltros = cQuery.split(",");
		StringTokenizer st = null;
		java.sql.Date dtFin = null;

		int iCveTitulo = Integer.parseInt(aFiltros[0]);
		iEjercicio = Integer.parseInt(aFiltros[1]);
		iMovProcedimiento = Integer.parseInt(aFiltros[2]);
		String cDtResolucion =  aFiltros[3];
		this.setDatosFolio(cNumFolio);
		try{
//			vNotifRec = dSegPRoc.findByOficioNotRec(iEjercicio,iMovProcedimiento);
//			vProrroga = dCYSProrrogaAlegato.fGeneraProrroga(iEjercicio,iMovProcedimiento);
//			vAlegato = dCYSProrrogaAlegato.fGeneraAlegato(iEjercicio,iMovProcedimiento);

			vNotifRec = dSegPRoc.findByOfiRevNotRec(iEjercicio,iMovProcedimiento);
			vProrroga = dSegPRoc.findByProrrogaNotRec(iEjercicio,iMovProcedimiento);
			vResolNoti = dSegPRoc.findByResolucionSancion(iEjercicio, iMovProcedimiento);
			vAlegato = dSegPRoc.findByAlegatoNotRec(iEjercicio,iMovProcedimiento);
			vOfiAnterior = dSegPRoc.findByProAntAntOficioNotRec(iEjercicio,iMovProcedimiento);
			vResolSancion = dSegPRoc.findByProAntAntOficioResolSancion(iEjercicio,iMovProcedimiento);
			vSHCP = dSegPRoc.findByDerechosFiscales(iEjercicio,iMovProcedimiento);

		}catch(Exception eNotifRec){
			eNotifRec.printStackTrace();
		}
		String cSQL = "select CPATitulo.iCveTitulo, " +
		"GRLPersona.iCvePersona, " +
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
		"CPAUsoTitulo.cDscUsoTitulo, " +
		"CPATitulo.iCveTituloAnterior, " +
		"CPATitulo.dtVigenciaTitulo, " +
		"CPATitulo.iCveTipoTitulo, "+
		"CPATitulo.iPropiedad, "+
		"CPATituloUbicacion.cCalleTitulo, " +
		"CPATituloUbicacion.cKm, " +
		"CPATituloUbicacion.cColoniaTitulo, " +
		"entTitUbic.cNombre as cEntidadTitUbic, " +
		"munTitUbi.cNombre as cMunicipioTitUbic, "+
		"PaisTitUbic.cNombre AS cPaisTitUbi, "+
		"GRLPuerto.cDscPuerto " +
		"FROM CPATitulo " +
		"join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
		"join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
		"left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
		"left join GRLPais PaisTitUbic on PaisTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
		"left join GRLEntidadFed entTitUbic on entTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
		"and entTitUbic.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
		"left join GRLMunicipio munTitUbi on munTitUbi.iCvePais = CPATituloUbicacion.iCvePais " + //--
		"and munTitUbi.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " + //--
		"and munTitUbi.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " + //--
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
		"left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
		"where CPATitulo.iCveTitulo = " + iCveTitulo;


		// Query de Consulta del Reporte.
		try{
			vRegs = super.FindByGeneric("", cSQL, dataSourceName);

		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		if (vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			cTitular = vDatos.getString("cNomRazonSocial")+" "+vDatos.getString("cApPaterno")+" "+vDatos.getString("cApMaterno");

			rep.comRemplaza("[cDirigidoA]",cTitular);

			rep.comRemplaza("[cCalle1]",vDatos.getString("cCalle"));
			rep.comRemplaza("[cNumExterior1]",vDatos.getString("cNumExterior"));
			if(vDatos.getString("cNumInterior") != "" || vDatos.getString("cNumInterior") != null)
				rep.comRemplaza("[cNumInterior1]",vDatos.getString("cNumInterior"));
			else
				rep.comRemplaza("[cNumInterior1]"," ");
			rep.comRemplaza("[cColonia1]",vDatos.getString("cColonia"));
			rep.comRemplaza("[cCodigoPostal1]",vDatos.getString("cCodPostal"));
			rep.comRemplaza("[cMunicipio1]",vDatos.getString("cNomMunicipio"));
			rep.comRemplaza("[cEntidad1]",vDatos.getString("cNomEntidadFed"));
			rep.comRemplaza("[cPais1]",vDatos.getString("cNomPais"));

			st = new StringTokenizer(cTitular);
			cTitular="";

			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				if(cToken.indexOf("S.A.")==-1 && cToken.indexOf("C.V.")==-1)
					cTitular += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
				else
					cTitular += " " + cToken;
			}

			rep.comRemplaza("[cTitular]",cTitular);
			//Identificación que se trata de Concesión.

			String cPseudoTitulo = "";
			String cPseudoTitulo2 = "";
			String cRevocaConDireccion="";
			String cDerechosTitulo = "";
			String cTipoArticulos = "";
			String cTipoArticulosFinal = "";
			if (vDatos.getInt("iCveTipoTitulo") == 1){
				cTipoTitulo = "la Concesión";
				cDscRevoca = "Secretario de Comunicaciones y Transportes";
				cPseudoTitulo = "concesionadas";
				cPseudoTitulo2 = "concesionados";
				cDscOtorgado = "El Presidente de la República a través de la Dirección General de Puertos";
				cPseudonimoTitular = "EL CONCESIONARIO";
				cRevocaConDireccion = cDscRevoca + " en Centro SCT ";
				cDerechosTitulo  = "derechos conforme a lo dispuesto en  el artículo 167 y aprovechamientos conforme al artículo 37 de la Ley de Puertos";
				cTipoArticulos  = "XIX, XX y XXVII de la Ley Orgánica de la Administración Pública Federal, 16, fracción IV de la Ley de Puertos y 5º, " +
						"fracción XI ";
				cTipoArticulosFinal  = "XIX, XX y XXVII de la Ley Orgánica de la Administración Pública Federal, 3º, 6º, 7º, 9º, " +
						"13, 16, 18, 28 fracción VI y 107 de la Ley General de Bienes Nacionales; 1º, 2º fracción VI, 16, fracción IV, " +
						"20, fracción II, inciso a), 32, fracción III y último párrafo, 33, 34, 36, 37 de la Ley de Puertos; 1, 2, 3, 11, " +
						"fracción VI, 16 fracción X, 35, 36, 39 y 57, fracción I de la Ley Federal de Procedimiento Administrativo; 4º " +
						"y 5º, fracción XI del Reglamento Interior de la Secretaría de Comunicaciones y Transportes, en relación con " +
						"la condición ______ del título de referencia, procede a dictar administrativamente la siguiente: ";
			}
			//Identificación que se trata de Permiso.
			if (vDatos.getInt("iCveTipoTitulo") == 2){
				cTipoTitulo = "el Permiso";
				cDscRevoca = "Director General de Puertos";
				cPseudoTitulo = "permisionadas";
				cPseudoTitulo2 = "permisionados";
				cDscOtorgado = "La Dirección General de Puertos";
				cPseudonimoTitular = "EL PERMISIONARIO";
				cRevocaConDireccion = cDscRevoca + " de la Secretaria de Comunicaciones y Transportes, en Avenida Nuevo Leon" +
						" 210, piso 15, Colonia Hipódromo Condesa, Delegación Cuauhtemoc, México, 06100, D.F., para que sea resuelto " +
						"por el superior jerárquico, o sea el Coordinador General de Puertos y Marina Mercante de la misma Secretaría. ";
				cDerechosTitulo = "derechos conforme a lo dispuesto en los artículos 167 y 232 fracción I de la Ley Federal de Derechos";
				cTipoArticulos = "XIX y XX de la Ley Orgánica de la Administración Pública Federal; 1°, 2° fracción VI, 16 fracción IV, " +
						"32 fracción III de la Ley de Puertos; 27 fracciones I, XX y XXIII ";
				cTipoArticulosFinal = "XIX y XX de la Ley Orgánica de la Administración Pública Federal; 3°, 6°, 7°, 9°, 13, 16, 28 " +
						"fracción VI y 107 de la Ley General de Bienes Nacionales; 1°, 2° fracción VI, 16 fracción IV, 20, 32 " +
						"fracción III y último párrafo, 33 fracciones I, IX, XI, XII y XIV, 34, 36, 37,  de la Ley de Puertos; " +
						"1, 2, 3, 11 fracción VI, 16 fracción X, 35, 36, 39 y 57 fracción I de la Ley Federal de Procedimiento " +
						"Administrativo; así como 27 fracciones I, IV y XX del Reglamento Interior de la Secretaría de " +
						"Comunicaciones y Transportes en relación con la condición _______ del título de referencia, " +
						"procede dictar administrativamente la siguiente";
			}

			if (vDatos.getInt("iCveTipoTitulo") == 3){
				cTipoTitulo = "la Autorización";
				cPseudoTitulo = "autorizadas";
				cPseudoTitulo2 = "autorizados";
				cDscRevoca = "Director General de Puertos";
				cDscOtorgado = "La Dirección General de Puertos";
				cPseudonimoTitular = "EL AUTORIZADO";
				cRevocaConDireccion = cDscRevoca + " de la Secretaria de Comunicaciones y Transportes, en Avenida Nuevo Leon" +
				" 210, piso 15, Colonia Hipódromo Condesa, Delegación Cuauhtemoc, México, 06100, D.F., para que sea resuelto " +
				"por el superior jerárquico, o sea el Coordinador General de Puertos y Marina Mercante de la misma Secretaría. ";
				cDerechosTitulo = "derechos conforme a lo dispuesto en los artículos 167 y 232 fracción I de la Ley Federal de Derechos";
				cTipoArticulos = "XIX y XX de la Ley Orgánica de la Administración Pública Federal; 1°, 2° fracción VI, 16 fracción IV, " +
				"32 fracción III de la Ley de Puertos; 27 fracciones I, XX y XXIII ";
				cTipoArticulosFinal = "XIX y XX de la Ley Orgánica de la Administración Pública Federal; 3°, 6°, 7°, 9°, 13, 16, 28 " +
				"fracción VI y 107 de la Ley General de Bienes Nacionales; 1°, 2° fracción VI, 16 fracción IV, 20, 32 " +
				"fracción III y último párrafo, 33 fracciones I, IX, XI, XII y XIV, 34, 36, 37,  de la Ley de Puertos; " +
				"1, 2, 3, 11 fracción VI, 16 fracción X, 35, 36, 39 y 57 fracción I de la Ley Federal de Procedimiento " +
				"Administrativo; así como 27 fracciones I, IV y XX del Reglamento Interior de la Secretaría de " +
				"Comunicaciones y Transportes en relación con la condición _______ del título de referencia, " +
				"procede dictar administrativamente la siguiente";
			}

			rep.comRemplaza("[cPseudonimoTitular]",cPseudonimoTitular);
			rep.comRemplaza("[cPseudonimoTitulo]",cPseudoTitulo);
			rep.comRemplaza("[cPseudonimoTitulo2]",cPseudoTitulo2);
			rep.comRemplaza("[cRevocaTipoTitulo]",cDscRevoca);
			rep.comRemplaza("[cEntregaTipoTitulo]",cDscRevoca);
			rep.comRemplaza("[cRevocayDireccion]",cRevocaConDireccion);
			rep.comRemplaza("[cDerechosTipoTitulo]", cDerechosTitulo);
			
			
			rep.comRemplaza("[cTipoTitulo]",cTipoTitulo);
			rep.comRemplaza("[cDscOtorgado]",cDscOtorgado);
			rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
			rep.comRemplaza("[cTipoArticulosTitulo]",cTipoArticulos);
			rep.comRemplaza("[cArticulosTitulo]",cTipoArticulosFinal);

			if(vDatos.getDate("dtIniVigenciaTitulo") != null){
				cFechaConcesion = Fechas.getDateSPN(vDatos.getDate("dtIniVigenciaTitulo"));
			}
			else
				cFechaConcesion = " ";
			rep.comRemplaza("[cFechaConcesion]",cFechaConcesion);

			if(vDatos.getDate("dtVigenciaTitulo")!=null)
				cIniVigencia = Fechas.getDateSPN(vDatos.getDate("dtVigenciaTitulo"));
			else
				cIniVigencia ="";
			rep.comRemplaza("[cIniVigencia]",cIniVigencia);

			cObjetoTitulo = vDatos.getString("cObjetoTitulo");
			st = new StringTokenizer(cObjetoTitulo);
			cObjetoTitulo="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cObjetoTitulo += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}
			rep.comRemplaza("[cObjetoTitulo]",cObjetoTitulo);

			if(vDatos.getString("cDscUsoTitulo").indexOf("USO")==-1)
				cUsoTitulo = "uso "+vDatos.getString("cDscUsoTitulo").toLowerCase();
			else
				cUsoTitulo = vDatos.getString("cDscUsoTitulo").toLowerCase();
			rep.comRemplaza("[cUsoTitulo]",cUsoTitulo);

			cAreaSolic = vDatos.getString("cCalleTitulo")+" "+vDatos.getString("cKm")+" "+vDatos.getString("cColoniaTitulo");
			st = new StringTokenizer(cAreaSolic);
			cAreaSolic="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cAreaSolic += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}
			rep.comRemplaza("[cAreaSolic]",""+cAreaSolic);

			iAniosDuracion = Fechas.getDaysBetweenDates(vDatos.getDate("dtIniVigenciaTitulo"),vDatos.getDate("dtVigenciaTitulo"));
			iAniosDuracion = iAniosDuracion/365;
			rep.comRemplaza("[cAniosDuracion]",""+iAniosDuracion);

			st = new StringTokenizer(vDatos.getString("cEntidadTitUbic"));
			cEntidadFed="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cEntidadFed += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}
			rep.comRemplaza("[cEntidadFed]",cEntidadFed);

			st = new StringTokenizer(vDatos.getString("cDscPuerto"));
			cPuerto="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cPuerto += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}
			rep.comRemplaza("[cPuerto]",cPuerto);

			st = new StringTokenizer(vDatos.getString("cNomEntidadFed"));
			cEntidad="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cEntidad += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}
			rep.comRemplaza("[cEntidadPto]",cEntidad);

			st = new StringTokenizer(vDatos.getString("cNomMunicipio"));
			cMunicipioEnt="";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cMunicipioEnt += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
			}
			rep.comRemplaza("[cMunicipioEnt]",cMunicipioEnt);

			if(vNotifRec.size()>0){
				TVDinRep vDinNotifRec = new TVDinRep();
				vDinNotifRec = (TVDinRep) vNotifRec.get(0);
				rep.comRemplaza("[cFolio]",vDinNotifRec.getString("cDigitosFolio") + "." +
						vDinNotifRec.getString("iConsecutivo") + "/" +
						vDinNotifRec.getString("iEjercicioFol"));

				if(vDinNotifRec.get("dtAsignacion")!=null)
					rep.comRemplaza("[cFechaFolio]",Fechas.getDateSPN(vDinNotifRec.getDate("dtAsignacion")));
				else
					rep.comRemplaza("[cFechaFolio]"," ");

				if(vDinNotifRec.get("NotcDigitosFolio") != null){
					cFolioNotif =   vDinNotifRec.getString("NotcDigitosFolio") + "." +
					vDinNotifRec.getString("NotiConsecutivo") + "/" +
					vDinNotifRec.getString("NotiEjercicioFol");
				}
				else
					cFolioNotif = " ";

				rep.comRemplaza("[cFolioNotif]", cFolioNotif);

				if(vDatos.get("dtRecAsignacion")!=null)
					cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtRecAsignacion"));
				else if(vDinNotifRec.get("dtNotAsignacion")!=null)
					cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtNotAsignacion"));
				else
					cFechaNotif = " ";
				rep.comRemplaza("[cFechaNotif]",cFechaNotif);

			}
			else{
				rep.comRemplaza("[cFolio]"," ");
				rep.comRemplaza("[cFechaFolio]"," ");
				rep.comRemplaza("[cFolioNotif]"," ");
				rep.comRemplaza("[cFechaNotif]"," ");
			}

			if(vProrroga.size()>0){
				TVDinRep vDinProrroga = new TVDinRep();
				vDinProrroga = (TVDinRep) vProrroga.get(0);
				if(vDinProrroga.get("dtEscrito") != null)
					cFechaEscrito = Fechas.getDateSPN(vDinProrroga.getDate("dtEscrito"));
				else
					cFechaEscrito = " ";

				String cParrafoUno = "IV.- El " + cFechaEscrito + " , \"La Concesionaria\" acudió al procedimiento instaurado, solicitando una prórroga para poder dar cumplimiento a lo requerido en el Oficio anteriormente señalado. ";
				rep.comRemplaza("[cPrimerParrafoProrroga]",cParrafoUno);

				if(vDinProrroga.get("dtAsignacion") != null)
					cFechaOfProrroga = Fechas.getDateSPN(vDinProrroga.getDate("dtAsignacion"));
				else
					cFechaOfProrroga = " ";

				if(vDinProrroga.get("cDigitosFolio") != null){
					cOfProrroga = vDinProrroga.getString("cDigitosFolio") + "." +
					vDinProrroga.getString("iConsecutivo") + "/" +
					vDinProrroga.getString("iEjercicioFol");
				}
				else
					cOfProrroga = " ";

				if(vDinProrroga.get("dtRecAsignacion") != null)
					cFeNotPro = Fechas.getDateSPN(vDinProrroga.getDate("dtRecAsignacion"));
				else if (vDinProrroga.get("dtNorAsignacion") != null)
					cFeNotPro = Fechas.getDateSPN(vDinProrroga.getDate("dtNorAsignacion"));
				else
					cFeNotPro = " ";

				String cParrafoDos = "IV.- El " + cFechaOfProrroga + " ésta Dirección General con oficio " + cOfProrroga + " notificado el "+ cFeNotPro +
				", con fundamento en el artículo 31 de la Ley Federal de Procedimiento Administrativo, le concedió a \"La Concesionaria\" un plazo de siete días hábiles" +
				"contados a partir de su notificación, a fin que acreditara el cumplimiento de las obligaciones que fueron requeridas.";

				rep.comRemplaza("[cSegundoParrafoProrroga]",cParrafoDos);

			}
			else{
				rep.comRemplaza("[cPrimerParrafoProrroga]"," ");
				rep.comRemplaza("[cSegundoParrafoProrroga]"," ");
			}

			if(vResolNoti.size() > 0){
				TVDinRep vDinResolNotif = (TVDinRep) vResolNoti.get(0);
				if(vDinResolNotif.getDate("dtNotResolucionSan") != null)
					rep.comRemplaza("[dtNotificacionSan]", Fechas.getFechaDDMMMMMYYYY(vDinResolNotif.getDate("dtNotResolucionSan"), " de "));
				else
					rep.comRemplaza("[dtNotificacionSan]"," ");
			}else
				rep.comRemplaza("[dtNotificacionSan]"," ");

			if(vSHCP.size() > 0){
				TVDinRep vDinSHCP = (TVDinRep) vSHCP.get(0);
				if(vDinSHCP.getDate("dtOficioSHCP") != null)
					rep.comRemplaza("[cFechaOficio_SHCP]", Fechas.getFechaDDMMMMMYYYY(vDinSHCP.getDate("dtOficioSHCP"), " de "));
				else
					rep.comRemplaza("[cFechaOficio_SHCP]", "________________");

				rep.comRemplaza("[cNumOficio_SHCP]", vDinSHCP.getString("cDigitosFolio") +"." +  vDinSHCP.getString("iConsecutivo") +
						"/" + vDinSHCP.getString("iEjercicioFolio"));
			}else{
				rep.comRemplaza("[cFechaOficio_SHCP]", "____________");
				rep.comRemplaza("[cNumOficio_SHCP]", "____________");
			}

			if(vAlegato.size()>0){
				TVDinRep vDinAlegato = new TVDinRep();
				vDinAlegato = (TVDinRep) vAlegato.get(0);
				if(vDinAlegato.get("dtAsignacion") != null)
					cFechaOfAlegato = Fechas.getDateSPN(vDinAlegato.getDate("dtAsignacion"));
				else
					cFechaOfAlegato = " ";

				if(vDinAlegato.get("cDigitosFolio") != null){
					cOfAlegato = vDinAlegato.getString("cDigitosFolio") + "." +
					vDinAlegato.getString("iConsecutivo") + "/" +
					vDinAlegato.getString("iEjercicioFol");
				}
				else
					cOfAlegato = " ";

				String cParrafoUno = "VII.- El " + cFechaOfAlegato + " con el oficio " + cOfAlegato + ", se le comunicó a \"La Concesionaria\" la " +
				"conclusión de la tramitación del procedimiento administrativo de revocación y se le otorgó un plazo de diez días hábiles " +
				"contados a partir de su notificación para que previamente a dictar resolución, conf fundamento en el artículo 56 de la " +
				"Ley Federal de Procedimiento Administrativo formulara sus alegatos por escrito y acreditara el cumplimiento de las " +
				"condiciones faltantes";
				rep.comRemplaza("[cParrafoUnoAlegato]",cParrafoUno);

				if(vDinAlegato.get("dtRecAsignacion") != null)
					cFeNotAle = Fechas.getDateSPN(vDinAlegato.getDate("dtRecAsignacion"));
				else if (vDinAlegato.get("dtNorAsignacion") != null)
					cFeNotAle = Fechas.getDateSPN(vDinAlegato.getDate("dtNorAsignacion"));
				else
					cFeNotAle = " ";

				if(vDinAlegato.get("dtAsignacion") != null){
					cFeIniAle = Fechas.getDateSPN(vDinAlegato.getDate("dtAsignacion"));
					dtFin = Fechas.aumentaDisminuyeDias(vDinAlegato.getDate("dtAsignacion"),
							vDinAlegato.getInt("iDiasOtorgados"));
					cFecFinAle = Fechas.getDateSPN(dtFin);
				}
				else{
					cFeIniAle = " ";
					cFecFinAle = " ";
				}

				String cParrafoDos = "VIII.- El oficio señalado en el resultando que antecede, fue legalmente notificado a \"La Concesionaria\" " +
				"el " + cFeNotAle + " y dentro del término legal de diez días que señala el artículo 56 de la Ley Federal de Procedimiento " +
				"Administrativo, que se computó del " + cFeIniAle + " al " + cFecFinAle + ". Según certificación de la Subdirección de Control " +
				"y Seguimiento, \"La Concesionaria \". no formuló alegato alguno en defensa de sus intereses, por lo que se procede " +
				"a pronunciar la presente Resolución.";

				rep.comRemplaza("[cParrafoDosAlegato]",cParrafoDos);
				
				String cParrafoTres = "VII(A).- El ___________________, con escrito de ____________, “" + cPseudonimoTitular +
						" presentó ante esta Dirección General algunos documentos relativos _____________ por el período de" +
						" ____ a _____, así como ________________, que no fueron suficientes para acreditar el cumplimiento de " +
						"las obligaciones que " +cPseudonimoTitular+ " asumió en el título de concesión, así como en las " +
						"disposiciones legales y administrativas por lo que se procede a pronunciar la presente resolución.";
				
				rep.comRemplaza("[cParrafoTresAlegato]",cParrafoTres);
			}
			else{
				rep.comRemplaza("[cParrafoUno]"," ");
				rep.comRemplaza("[cParrafoDosAlegato]","");
			}


			if(vOfiAnterior.size()>0){
				TVDinRep vDatosAnt = (TVDinRep) vOfiAnterior.get(0);
				if(vDatosAnt.get("cDigitosFolio") != null){
					cOfSancion = vDatosAnt.getString("cDigitosFolio") + "." +
					vDatosAnt.getString("iConsecutivo") + "/" +
					vDatosAnt.getString("iEjercicioFol");
				}
				else
					cOfSancion = " ";
				rep.comRemplaza("[cOfSancion]",cOfSancion);

				if(vDatosAnt.get("dtAsignacion") != null)
					cFechaSancion = Fechas.getDateSPN(vDatosAnt.getDate("dtAsignacion"));
				else
					cFechaSancion = " ";
				rep.comRemplaza("[cFechaSancion]",cFechaSancion);
			}
			else{
				rep.comRemplaza("[cOfSancion]"," ");
				rep.comRemplaza("[cFechaSancion]"," ");
			}

			if(vResolSancion.size()>0){
				TVDinRep vResolSan = (TVDinRep) vResolSancion.get(0);
				if(vResolSan.get("cDigitosFolio") != null){
					cOfResolSancion = vResolSan.getString("cDigitosFolio") + "." +
					vResolSan.getString("iConsecutivo") + "/" +
					vResolSan.getString("iEjercicioFol");
				}
				else
					cOfResolSancion = " ";
				rep.comRemplaza("[cOfResolSancion]",cOfResolSancion);

				if(vResolSan.get("dtAsignacion") != null){
					cFecResolSancion = Fechas.getDateSPN(vResolSan.getDate("dtAsignacion"));
				}
				else
					cFecResolSancion = " ";
				rep.comRemplaza("[cFecResolSancion]",cFecResolSancion);
			}
			else{
				rep.comRemplaza("[cOfResolSancion]"," ");
				rep.comRemplaza("[cFecResolSancion]"," ");
			}

		} else {
			//Sin Valores en la Base.
		}

		//return ;
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				0,0,0,
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
		vDinRep.put("lEsResolucion",1);

		try{
			vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
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

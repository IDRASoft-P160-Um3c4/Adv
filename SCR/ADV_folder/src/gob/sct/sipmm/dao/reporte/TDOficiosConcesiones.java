package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDOficiosConcesiones.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @author LSC. Armando Barrientos Martínez
 * @author LCI. Oscar Castrejón Adame.
 * @author icaballero
 * @version 1.0
 */

public class TDOficiosConcesiones extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private TFechas    tFecha      = new TFechas("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
	private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
	private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
	public TDOficiosConcesiones(){
	}
	
	
	public Vector WordOficio(String cQuery){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsSerPor = new Vector();
		Vector vRegsSerCon = new Vector();
		TWord rep = new TWord();
		int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0,iCveTipoContrato=0;
		String cNombreAPI="",cNumContrato= "",cNomCesionario="",cNumTitulo="",cAsunto="";
		String cSerPor="";
		String cSerCon="";
		String cRefOficio="";
		String cIntOficio="";
		String cNumControl="";
		StringBuffer sbBuscaAdicional = new StringBuffer();
		StringBuffer sbRemplazaAdicional = new StringBuffer();

		try{
			System.out.print("*******=========== 1 ===========**********");
			vRegs = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"RGCContrato.iCveTipoContrato, " +
					"CPATitulo.cNumTitulo, " +
					"CPATitular.iCveTitular, " +
					"GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cNombreAPI, " +
					"GRLDomicilio.iCveDomicilio, " +
					"GRLRepLegal.iCvePersona, " +
					"Cesionaria.cNomRazonSocial || ' ' || Cesionaria.cApPaterno || ' ' || Cesionaria.cApMaterno as cNomCesionaria, " +
					"RGCContrato.cRefOficio, " +
					"RGCContrato.cIntOficio, " +
					"RGCContrato.cNumControl, " +
					"iCveModalidad " +
					"from RGCContrato " +
					"join CPATitulo on CPATitulo.iCveTitulo = RGCContrato.iCveContrato " +
					"join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo " +
					"join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
					"and GRLDomicilio.lPredeterminado = 1 " +
					"join GRLRepLegal on GRLRepLegal.iCveEmpresa = GRLPersona.iCvePersona " +
					"and GRLRepLegal.lPrincipal = 1 " +
					"join GRLPersona Cesionaria on Cesionaria.iCvePersona = RGCContrato.iCveCesionario " +
					"join RGCSolContrato on RGCSolContrato.iCveContrato = RGCContrato.iCveContrato  " +
					"join TRARegSolicitud on TRARegSolicitud.iEjercicio = RGCSolContrato.iEjercicio " +
					"and TRARegSolicitud.iNumSolicitud = RGCSolContrato.iNumSolicitud " +
					"where RGCContrato.iCveContrato = " + cQuery, dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Servicios Portuarios del Registro de Contratos.
		try{
			vRegsSerPor = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"CPAServicioPortuario.iCveServicioPortuario, " +
					"CPAServicioPortuario.cDscServicioPortuario " +
					"from RGCContrato " +
					"join RGCContratoSerPor on RGCContratoSerPor.iCveContrato = RGCContrato.iCveContrato " +
					"join CPAServicioPortuario on CPAServicioPortuario.iCveServicioPortuario = RGCContratoSerPor.iCveServicioPortuario " +
					"where RGCContrato.iCveContrato = " + cQuery, dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Servicios Portuarios del Registro de Contratos.
		try{
			System.out.print("*******=========== 2 ===========**********");
			vRegsSerCon = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"TARServicioConexo.iCveServicioConexo, " +
					"TARServicioConexo.cDscServConexo " +
					"from RGCContrato " +
					"join RGCContratoSerConexo on RGCContratoSerConexo.iCveContrato = RGCContrato.iCveContrato " +
					"join TARServicioConexo on TARServicioConexo.iCveServicioConexo = RGCContratoSerConexo.iCveServicioConexo " +
					"where RGCContrato.iCveContrato = " + cQuery, dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		rep.iniciaReporte();

		if (vRegs.size() > 0){
			System.out.print("*******=========== 3 ===========**********");
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			//Datos de la API.
			iCvePersona = vDatos.getInt("iCveTitular");
			cNombreAPI = vDatos.getString("cNombreAPI");
			iCveDomicilio = vDatos.getInt("iCveDomicilio");
			iCveRepLegal = vDatos.getInt("iCvePersona");
			cNumContrato = vDatos.getString("cNumContrato");
			cNumTitulo = vDatos.getString("cNumTitulo");
			cNomCesionario= vDatos.getString("cNomCesionaria");
			iCveTipoContrato = vDatos.getInt("iCveTipoContrato");
			cRefOficio = vDatos.getString("cRefOficio");
			cIntOficio = vDatos.getString("cIntOficio");
			cNumControl = vDatos.getString("cNumControl");


			//Modalidad Registro.
			if (vDatos.getInt("iCveModalidad") == 7){
				//Asunto del Oficio.
				if(iCveTipoContrato == 1 || iCveTipoContrato == 2){
					cAsunto = "Se Registra Contrato";
				}

				if(iCveTipoContrato == 3){
					cAsunto = "Se Toma Nota del Contrato";
				}

			}

			//Modalidad Prorroga.
			if (vDatos.getInt("iCveModalidad") == 4){
				//Asunto del Oficio.
				if(iCveTipoContrato == 1 || iCveTipoContrato == 2){
					cAsunto = "Se Registra Convenio de Prorroga";
				}

				if(iCveTipoContrato == 3){
					cAsunto = "Se Toma Nota del Convenio de Prorroga";
				}

			}

			//Modalidad Modificación.
			if (vDatos.getInt("iCveModalidad") == 2){
				//Asunto del Oficio.
				if(iCveTipoContrato == 1 || iCveTipoContrato == 2){
					cAsunto = "Se Registra Convenio Modificatorio";
				}

				if(iCveTipoContrato == 3){
					cAsunto = "Se Toma Nota del Convenio Modificatorio";
				}
			}



			StringTokenizer st = new StringTokenizer(cNomCesionario);
			cNomCesionario = "";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cNomCesionario = cNomCesionario  + ' ' +
				cToken.charAt(0) +
				cToken.substring(1,cToken.length()).toLowerCase();
			}

			rep.comRemplaza("[cNomCesionaria]",cNomCesionario);
			rep.comRemplaza("[cNumContrato]",cNumContrato);

			if (vRegsSerPor.size()>0){
				for(int i=0;i<vRegsSerPor.size();i++){
					TVDinRep vDatosSerPor = (TVDinRep) vRegsSerPor.get(i);
					if (i==0)
						cSerPor = vDatosSerPor.getString("cDscServicioPortuario");
					else
						cSerPor = cSerPor + ", " + vDatosSerPor.getString("cDscServicioPortuario");
				}
				rep.comRemplaza("[cSerPor]",cSerPor.toLowerCase());
			} else
				rep.comRemplaza("[cSerPor]","");

			if (vRegsSerCon.size()>0){
				for(int i=0;i<vRegsSerCon.size();i++){
					TVDinRep vDatosSerCon = (TVDinRep) vRegsSerCon.get(i);
					if (i==0)
						cSerCon = vDatosSerCon.getString("cDscServConexo");
					else
						cSerCon = cSerCon + ", " + vDatosSerCon.getString("cDscServConexo");
				}
				rep.comRemplaza("[cSerCon]",cSerCon.toLowerCase());
			} else
				rep.comRemplaza("[cSerCon]","");

			rep.comRemplaza("[cNumControl]",cNumControl != null && !cNumControl.equalsIgnoreCase("null")? cNumControl:" ");

		} else {
			System.out.print("*******=========== 4 ===========**********");
			//Sin Valores en la Base.
			rep.comRemplaza("[cNomCesionaria]","");
			rep.comRemplaza("[cNumContrato]","");
			rep.comRemplaza("[cSerPor]","");
			rep.comRemplaza("[cSerCon]","");
			rep.comRemplaza("[cNumControl]","");
		}
		System.out.print("*******=========== 5 ===========**********");
//		System.out.print("==============cNumFolio:"+cNumFolio);
//		System.out.print("==============iCveOficinaOrigen:"+iCveOficinaOrigen);
//		System.out.print("==============iCveDeptoOrigen:"+iCveDeptoOrigen);
		System.out.print("==============iCvePersona:"+iCvePersona);
		System.out.print("==============iCveDomicilio:"+iCveDomicilio);
		System.out.print("==============iCveRepLegal:"+iCveRepLegal);
		
		
		return rep.getVectorDatos(true); 
		
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
//		return new TDGeneral().generaOficioWord(cNumFolio,
//				Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
//				0,0,
//				iCvePersona,iCveDomicilio,iCveRepLegal,
//				"",cAsunto,
//				"REF: " + cRefOficio, "INT: " + cIntOficio,
//				true,"cCuerpo",vcCuerpo,
//				true, vcCopiasPara,
//				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}
	
	public Vector WordOficio(String cQuery, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsSerPor = new Vector();
		Vector vRegsSerCon = new Vector();
		TWord rep = new TWord();
		int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0,iCveTipoContrato=0;
		String cNombreAPI="",cNumContrato= "",cNomCesionario="",cNumTitulo="",cAsunto="";
		String cSerPor="";
		String cSerCon="";
		String cRefOficio="";
		String cIntOficio="";
		String cNumControl="";
		StringBuffer sbBuscaAdicional = new StringBuffer();
		StringBuffer sbRemplazaAdicional = new StringBuffer();

		try{
			System.out.print("*******=========== 1 ===========**********");
			vRegs = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"RGCContrato.iCveTipoContrato, " +
					"CPATitulo.cNumTitulo, " +
					"CPATitular.iCveTitular, " +
					"GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cNombreAPI, " +
					"GRLDomicilio.iCveDomicilio, " +
					"GRLRepLegal.iCvePersona, " +
					"Cesionaria.cNomRazonSocial || ' ' || Cesionaria.cApPaterno || ' ' || Cesionaria.cApMaterno as cNomCesionaria, " +
					"RGCContrato.cRefOficio, " +
					"RGCContrato.cIntOficio, " +
					"RGCContrato.cNumControl, " +
					"iCveModalidad " +
					"from RGCContrato " +
					"join CPATitulo on CPATitulo.iCveTitulo = RGCContrato.iCveContrato " +
					"join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo " +
					"join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
					"and GRLDomicilio.lPredeterminado = 1 " +
					"join GRLRepLegal on GRLRepLegal.iCveEmpresa = GRLPersona.iCvePersona " +
					"and GRLRepLegal.lPrincipal = 1 " +
					"join GRLPersona Cesionaria on Cesionaria.iCvePersona = RGCContrato.iCveCesionario " +
					"join RGCSolContrato on RGCSolContrato.iCveContrato = RGCContrato.iCveContrato  " +
					"join TRARegSolicitud on TRARegSolicitud.iEjercicio = RGCSolContrato.iEjercicio " +
					"and TRARegSolicitud.iNumSolicitud = RGCSolContrato.iNumSolicitud " +
					"where RGCContrato.iCveContrato = " + cQuery, dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Servicios Portuarios del Registro de Contratos.
		try{
			vRegsSerPor = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"CPAServicioPortuario.iCveServicioPortuario, " +
					"CPAServicioPortuario.cDscServicioPortuario " +
					"from RGCContrato " +
					"join RGCContratoSerPor on RGCContratoSerPor.iCveContrato = RGCContrato.iCveContrato " +
					"join CPAServicioPortuario on CPAServicioPortuario.iCveServicioPortuario = RGCContratoSerPor.iCveServicioPortuario " +
					"where RGCContrato.iCveContrato = " + cQuery, dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Servicios Portuarios del Registro de Contratos.
		try{
			System.out.print("*******=========== 2 ===========**********");
			vRegsSerCon = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"TARServicioConexo.iCveServicioConexo, " +
					"TARServicioConexo.cDscServConexo " +
					"from RGCContrato " +
					"join RGCContratoSerConexo on RGCContratoSerConexo.iCveContrato = RGCContrato.iCveContrato " +
					"join TARServicioConexo on TARServicioConexo.iCveServicioConexo = RGCContratoSerConexo.iCveServicioConexo " +
					"where RGCContrato.iCveContrato = " + cQuery, dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		rep.iniciaReporte();

		if (vRegs.size() > 0){
			System.out.print("*******=========== 3 ===========**********");
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			//Datos de la API.
			iCvePersona = vDatos.getInt("iCveTitular");
			cNombreAPI = vDatos.getString("cNombreAPI");
			iCveDomicilio = vDatos.getInt("iCveDomicilio");
			iCveRepLegal = vDatos.getInt("iCvePersona");
			cNumContrato = vDatos.getString("cNumContrato");
			cNumTitulo = vDatos.getString("cNumTitulo");
			cNomCesionario= vDatos.getString("cNomCesionaria");
			iCveTipoContrato = vDatos.getInt("iCveTipoContrato");
			cRefOficio = vDatos.getString("cRefOficio");
			cIntOficio = vDatos.getString("cIntOficio");
			cNumControl = vDatos.getString("cNumControl");


			//Modalidad Registro.
			if (vDatos.getInt("iCveModalidad") == 7){
				//Asunto del Oficio.
				if(iCveTipoContrato == 1 || iCveTipoContrato == 2){
					cAsunto = "Se Registra Contrato";
				}

				if(iCveTipoContrato == 3){
					cAsunto = "Se Toma Nota del Contrato";
				}

			}

			//Modalidad Prorroga.
			if (vDatos.getInt("iCveModalidad") == 4){
				//Asunto del Oficio.
				if(iCveTipoContrato == 1 || iCveTipoContrato == 2){
					cAsunto = "Se Registra Convenio de Prorroga";
				}

				if(iCveTipoContrato == 3){
					cAsunto = "Se Toma Nota del Convenio de Prorroga";
				}

			}

			//Modalidad Modificación.
			if (vDatos.getInt("iCveModalidad") == 2){
				//Asunto del Oficio.
				if(iCveTipoContrato == 1 || iCveTipoContrato == 2){
					cAsunto = "Se Registra Convenio Modificatorio";
				}

				if(iCveTipoContrato == 3){
					cAsunto = "Se Toma Nota del Convenio Modificatorio";
				}
			}



			StringTokenizer st = new StringTokenizer(cNomCesionario);
			cNomCesionario = "";
			while (st.hasMoreTokens()) {
				String cToken = st.nextToken();
				cNomCesionario = cNomCesionario  + ' ' +
				cToken.charAt(0) +
				cToken.substring(1,cToken.length()).toLowerCase();
			}

			rep.comRemplaza("[cNomCesionaria]",cNomCesionario);
			rep.comRemplaza("[cNumContrato]",cNumContrato);

			if (vRegsSerPor.size()>0){
				for(int i=0;i<vRegsSerPor.size();i++){
					TVDinRep vDatosSerPor = (TVDinRep) vRegsSerPor.get(i);
					if (i==0)
						cSerPor = vDatosSerPor.getString("cDscServicioPortuario");
					else
						cSerPor = cSerPor + ", " + vDatosSerPor.getString("cDscServicioPortuario");
				}
				rep.comRemplaza("[cSerPor]",cSerPor.toLowerCase());
			} else
				rep.comRemplaza("[cSerPor]","");

			if (vRegsSerCon.size()>0){
				for(int i=0;i<vRegsSerCon.size();i++){
					TVDinRep vDatosSerCon = (TVDinRep) vRegsSerCon.get(i);
					if (i==0)
						cSerCon = vDatosSerCon.getString("cDscServConexo");
					else
						cSerCon = cSerCon + ", " + vDatosSerCon.getString("cDscServConexo");
				}
				rep.comRemplaza("[cSerCon]",cSerCon.toLowerCase());
			} else
				rep.comRemplaza("[cSerCon]","");

			rep.comRemplaza("[cNumControl]",cNumControl != null && !cNumControl.equalsIgnoreCase("null")? cNumControl:" ");

		} else {
			System.out.print("*******=========== 4 ===========**********");
			//Sin Valores en la Base.
			rep.comRemplaza("[cNomCesionaria]","");
			rep.comRemplaza("[cNumContrato]","");
			rep.comRemplaza("[cSerPor]","");
			rep.comRemplaza("[cSerCon]","");
			rep.comRemplaza("[cNumControl]","");
		}
		System.out.print("*******=========== 5 ===========**********");
		System.out.print("==============cNumFolio:"+cNumFolio);
		System.out.print("==============iCveOficinaOrigen:"+iCveOficinaOrigen);
		System.out.print("==============iCveDeptoOrigen:"+iCveDeptoOrigen);
		System.out.print("==============iCvePersona:"+iCvePersona);
		System.out.print("==============iCveDomicilio:"+iCveDomicilio);
		System.out.print("==============iCveRepLegal:"+iCveRepLegal);
		
		
		return rep.getVectorDatos(true);
		
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
//		return new TDGeneral().generaOficioWord(cNumFolio,
//				Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
//				0,0,
//				iCvePersona,iCveDomicilio,iCveRepLegal,
//				"",cAsunto,
//				"REF: " + cRefOficio, "INT: " + cIntOficio,
//				true,"cCuerpo",vcCuerpo,
//				true, vcCopiasPara,
//				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

	public Vector GenOpnInterna(String cQuery,
			String cNumFolio,
			String cCveOficinaOrigen,
			String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsSerPor = new Vector();
		Vector vRegsSerCon = new Vector();
		TWord rep = new TWord();
		int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
		String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="";
		StringBuffer sbBuscaAdicional = new StringBuffer();
		StringBuffer sbRemplazaAdicional = new StringBuffer();
		String cTipoContrato="";
		String[] aFiltros = cQuery.split(",");
		int iCveContrato = Integer.parseInt(aFiltros[4],10);

		TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
		//Integridad con el Modulo de Opiniones.
		dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cQuery);
		dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());
		iCveOficinaDest = dObtenDatosOpiniones.dDatosOficPer.getiCveOficina();
		iCveDeptoDest = dObtenDatosOpiniones.dDatosOficPer.getiCveDepto();

		//String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
		//String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();


		try{
			dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
		} catch (Exception e){
			e.printStackTrace();
			cMensaje = e.getMessage();
		}

		// Query de Consulta del Reporte.
		try{

			vRegs = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"RGCContrato.iCveTipoContrato, " +
					"CPATitulo.cNumTitulo, " +
					"CPATitular.iCveTitular, " +
					"GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cNombreAPI, " +
					"GRLDomicilio.iCveDomicilio, " +
					"GRLRepLegal.iCvePersona, " +
					"Cesionaria.cNomRazonSocial || ' ' || Cesionaria.cApPaterno || ' ' || Cesionaria.cApMaterno as cNomCesionaria " +
					"from RGCContrato " +
					"join CPATitulo on CPATitulo.iCveTitulo = RGCContrato.iCveContrato " +
					"join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo " +
					"join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
					"and GRLDomicilio.lPredeterminado = 1 " +
					"join GRLRepLegal on GRLRepLegal.iCveEmpresa = GRLPersona.iCvePersona " +
					"and GRLRepLegal.lPrincipal = 1 " +
					"join GRLPersona Cesionaria on Cesionaria.iCvePersona = RGCContrato.iCveCesionario " +
					"where RGCContrato.iCveContrato = " + iCveContrato, dataSourceName);

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

			//Datos de la API.
			cNombreAPI = vDatos.getString("cNombreAPI");
			cNomCesionario= vDatos.getString("cNomCesionaria");
			iCveTipoContrato = vDatos.getInt("iCveTipoContrato");

			//Asunto del Oficio.
			if(iCveTipoContrato == 1 ){
				cAsunto ="Se envía copia del contrato de cesión parcial de derechos para su opinión";
				cTipoContrato="Contrato de Cesion Parcial de Derechos";
			}

			if (iCveTipoContrato == 2){
				cAsunto = "Se envía copia del contrato de prestación de servicios portuarios para su opinión";
				cTipoContrato="Contrato de Prestación de Servicios Portuarios";
			}
			if(iCveTipoContrato == 3){
				cAsunto = "Se envía copia del contrato de prestación de servicios conexos para su opinión";
				cTipoContrato="Contrato de Prestación de Servicios Conexos";
			}

			rep.comRemplaza("[cNomCesionaria]",cNomCesionario);
			rep.comRemplaza("[cNomAPI]",cNombreAPI);
			rep.comRemplaza("[cTipoContrato]",cTipoContrato);

		} else {
			//Sin Valores en la Base.
			rep.comRemplaza("[cNomCesionaria]","");
			rep.comRemplaza("[cNomAPI]","");
			rep.comRemplaza("[cTipoContrato]","");
		}

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				0,0,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

	public Vector GenOpnExterna(String cQuery,
			String cNumFolio,
			String cCveOficinaOrigen,
			String cCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsSerPor = new Vector();
		Vector vRegsSerCon = new Vector();
		TWord rep = new TWord();
		int iCveTipoContrato=0,iCvePersona=0,iCveRepLegal=0,iCveDomicilio=0;
		String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="";
		StringBuffer sbBuscaAdicional = new StringBuffer();
		StringBuffer sbRemplazaAdicional = new StringBuffer();
		String cTipoContrato="";
		String[] aFiltros = cQuery.split(",");
		int iCveContrato = Integer.parseInt(aFiltros[4],10);

		TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
		//Integridad con el Modulo de Opiniones.
		dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cQuery);
		dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());


		iCvePersona = dObtenDatosOpiniones.dDatosOficPer.getiCvePersona();
		iCveDomicilio = dObtenDatosOpiniones.dDatosOficPer.getiCveDomicilio();
		String cDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
		String cPuestoDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

		try{
			dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
		} catch (Exception e){
			e.printStackTrace();
			cMensaje = e.getMessage();
		}

		// Query de Consulta del Reporte.
		try{

			vRegs = super.FindByGeneric("", "select RGCContrato.cNumContrato, " +
					"RGCContrato.iCveTipoContrato, " +
					"CPATitulo.cNumTitulo, " +
					"CPATitular.iCveTitular, " +
					"GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cNombreAPI, " +
					"GRLDomicilio.iCveDomicilio, " +
					"GRLRepLegal.iCvePersona, " +
					"Cesionaria.cNomRazonSocial || ' ' || Cesionaria.cApPaterno || ' ' || Cesionaria.cApMaterno as cNomCesionaria " +
					"from RGCContrato " +
					"join CPATitulo on CPATitulo.iCveTitulo = RGCContrato.iCveContrato " +
					"join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo " +
					"join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
					"and GRLDomicilio.lPredeterminado = 1 " +
					"join GRLRepLegal on GRLRepLegal.iCveEmpresa = GRLPersona.iCvePersona " +
					"and GRLRepLegal.lPrincipal = 1 " +
					"join GRLPersona Cesionaria on Cesionaria.iCvePersona = RGCContrato.iCveCesionario " +
					"where RGCContrato.iCveContrato = " + iCveContrato, dataSourceName);

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

			//Datos de la API.
			cNombreAPI = vDatos.getString("cNombreAPI");
			cNomCesionario= vDatos.getString("cNomCesionaria");
			iCveTipoContrato = vDatos.getInt("iCveTipoContrato");

			//Asunto del Oficio.
			if(iCveTipoContrato == 1 ){
				cAsunto ="Se envía copia del contrato de cesión parcial de derechos para su opinión";
				cTipoContrato="Contrato de Cesion Parcial de Derechos";
			}

			if (iCveTipoContrato == 2){
				cAsunto = "Se envía copia del contrato de prestación de servicios portuarios para su opinión";
				cTipoContrato="Contrato de Prestación de Servicios Portuarios";
			}
			if(iCveTipoContrato == 3){
				cAsunto = "Se envía copia del contrato de prestación de servicios conexos para su opinión";
				cTipoContrato="Contrato de Prestación de Servicios Conexos";
			}

			rep.comRemplaza("[cNomDestino]", cDirigidoA);
			rep.comRemplaza("[cPtoDestino]", cPuestoDirigidoA);
			rep.comRemplaza("[cNomCesionaria]",cNomCesionario);
			rep.comRemplaza("[cNomAPI]",cNombreAPI);
			rep.comRemplaza("[cTipoContrato]",cTipoContrato);

		} else {
			//Sin Valores en la Base.
			rep.comRemplaza("[cNomDestino]", "");
			rep.comRemplaza("[cPtoDestino]", "");
			rep.comRemplaza("[cNomCesionaria]","");
			rep.comRemplaza("[cNomAPI]","");
			rep.comRemplaza("[cTipoContrato]","");
		}

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				iCvePersona,iCveDomicilio,0,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar(),cPuestoDirigidoA);

	}


	public Vector GenAutICAPI(String cQuery, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsPlanos = new Vector();
		Vector vRegsTituloDocumentos = new Vector();
		Vector vRegsTituloUbicacion = new Vector();
		Vector vRegsTituloObras = new Vector();
		TWord rep = new TWord();
		TFechas Fecha = new TFechas("44");
		int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0,iCveTipoContrato=0;
		String cNombreAPI="",cNumContrato= "",cNomCesionario="",cNumTitulo="",cAsunto="";
		String cSerPor="";
		String cSerCon="";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio    = Integer.parseInt(aFiltros[0],10),
		iNumSolicitud = Integer.parseInt(aFiltros[1],10),
		iCveTitulo    = Integer.parseInt(aFiltros[2],10),
		iCveContrato  = 0; //Integer.parseInt(aFiltros[3],10);

		//Informacion de la Solicitud, del Registro de Contratos.
		try{
			vRegs = super.FindByGeneric("", "select " +
					"date(TRARegSolicitud.tsRegistro) as dtSolRegistro, " +
					"TRARegSolicitud.iCveOficina, " +
					"GRLOficina.cCalleyNo, " +
					"GRLOficina.cColonia, " +
					"GRLOficina.cCodPostal, " +
					"GRLOficina.cTelefono, " +
					"GRLOficina.cCorreoE, " +
					"GRLPais.cNombre as cDscPais, " +
					"GRLEntidadFed.cNombre as cDscEntidadFed, " +
					"GRLMunicipio.cNombre as cDscMunicipio, " +
					"RGCContrato.iCveContrato, " +
					"RGCContrato.cNumContrato, " +
					"Cesionario.cNomRazonSocial || ' ' || Cesionario.cApPaterno || ' ' || Cesionario.cApMaterno as cNomCesionario, " +
					"RGCContrato.dtRegistro, " +
					"RGCContrato.dtContrato, " +
					"CPATitular.iCveTitular, " +
					"DomTitular.iCveDomicilio, " +
					"RepLegal.iCvePersona as iCveRepLegal " +
					"from TRARegSolicitud " +
					"join CPASolTitulo on CPASolTitulo.iEjercicio = TRARegSolicitud.iEjercicio " +
					"and CPASolTitulo.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
					"join CPATitulo on CPATitulo.iCveTitulo = CPASolTitulo.iCveTitulo " +
					"join CPATitular on CPATitular.iCveTitulo = CPASolTitulo.iCveTitulo " +
					"join GRLPersona Titular on Titular.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio DomTitular on DomTitular.iCvePersona = Titular.iCvePersona " +
					"and DomTitular.lPredeterminado = 1 " +
					"left join GRLRepLegal RepLegal on RepLegal.iCveEmpresa = Titular.iCvePersona " +
					"and RepLegal.lPrincipal = 1 " +
					"left join CPATituloContrato on CPATituloContrato.iCveTitulo = CPATitulo.iCveTitulo " +
					"left join RGCContrato on RGCContrato.iCveContrato = CPATituloContrato.iCveContrato " +
					"left join GRLPersona Cesionario on Cesionario.iCvePersona = RGCContrato.iCveCesionario " +
					"left join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina  " +
					"left join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais  " +
					"left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais  " +
					"and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed  " +
					"left join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais  " +
					"and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed  " +
					"and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Información de los Planos.
		try{
			vRegsPlanos = super.FindByGeneric("", "select OMPPlano.cPlanoNum, " +
					"OMPPlano.cDenominacion, " +
					"OMPPlano.dtAprobacionPlano, " +
					"OMPPlano.cExpedienteNum " +
					"from TRARegSolicitud " +
					"join TRAPlanoXTramite on TRAPlanoXTramite.iEjercicio = TRARegSolicitud.iEjercicio " +
					"and TRAPlanoXTramite.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
					"join OMPPlano on OMPPlano.iCvePlano = TRAPlanoXTramite.iCvePlano " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
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


		//Asunto del Oficio.
		cAsunto = "Autorización de Inicio de Construcción";
		rep.iniciaReporte();
		if (vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			//Datos de la API.
			iCvePersona = vDatos.getInt("iCveTitular");
			//cNombreAPI = vDatos.getString("cNombreAPI");
			iCveDomicilio = vDatos.getInt("iCveDomicilio");
			iCveRepLegal = vDatos.getInt("iCveRepLegal");
			//cNumContrato = vDatos.getString("cNumContrato");
			//cNumTitulo = vDatos.getString("cNumTitulo");
			//cNomCesionario= vDatos.getString("cNomCesionaria");
			//iCveTipoContrato = vDatos.getInt("iCveTipoContrato");

			//Información de la Solicitud.
			rep.comRemplaza("[cNoSolicitud]","" + iEjercicio + " - " + iNumSolicitud);
			rep.comRemplaza("[cLugarSolicitud]",vDatos.getString("cCalleyNo") + ",Col. " +
					vDatos.getString("cColonia") + ",C.P. " +
					vDatos.getString("cCodPostal") + "," +
					vDatos.getString("cDscMunicipio") + "," +
					vDatos.getString("cDscEntidadFed"));
			rep.comRemplaza("[dtSolicitud]",Fecha.getFechaDDMMYYYY(vDatos.getDate("dtSolRegistro"),"/"));

			//Información de la Autorización.
			rep.comRemplaza("[ao]"," ");
			rep.comRemplaza("[ad]"," ");
			rep.comRemplaza("[aa]"," ");
			rep.comRemplaza("[ac]","X");
			rep.comRemplaza("[ap]"," ");

			//Información del Registro de Contratos.
			rep.comRemplaza("[cNomCesionario]",(vDatos.getString("cNomCesionario")!=null&&vDatos.getString("cNomCesionario")!="")?vDatos.getString("cNomCesionario"):"");
			rep.comRemplaza("[dtCelebracion]",(vDatos.getDate("dtContrato")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtContrato"),"/"):"");
			rep.comRemplaza("[dtRegistro]",(vDatos.getDate("dtRegistro")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtRegistro"),"/"):"");
			rep.comRemplaza("[cNumContrato]",(vDatos.getString("cNumContrato")!=null&&vDatos.getString("cNumContrato")!="")?vDatos.getString("cNumContrato"):"");

			//Uso del Titulo.
			rep.comRemplaza("[uu]","");
			rep.comRemplaza("[up]","");


			//Fuera de Recinto Portuario.
			if (vRegsTituloUbicacion.size()>0){
				TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);
				rep.comRemplaza("[cCalle]",(vDatosTitUbi.get("cCalleTitulo")!= null)?vDatosTitUbi.get("cCalleTitulo").toString():"");
				rep.comRemplaza("[cKm]",(vDatosTitUbi.get("cKm")!= null)?vDatosTitUbi.get("cKm").toString():"");
				rep.comRemplaza("[cColonia]",(vDatosTitUbi.get("cColoniaTitulo")!= null)?vDatosTitUbi.get("cColoniaTitulo").toString():"");
				rep.comRemplaza("[cPuerto]",(vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
				rep.comRemplaza("[cMunicipio]",(vDatosTitUbi.get("cDscMunicipio")!= null)?vDatosTitUbi.get("cDscMunicipio").toString():"");
				rep.comRemplaza("[cEntFed]",(vDatosTitUbi.get("cDscEntidadFed")!= null)?vDatosTitUbi.get("cDscEntidadFed").toString():"");

				//Ubicación de la Obra.
				rep.comRemplaza("[uo]","X");
				if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1){
					rep.comRemplaza("[rp]","X");
					rep.comRemplaza("[fr]","");
				} else {
					rep.comRemplaza("[rp]"," ");
					rep.comRemplaza("[fr]","X");
				}
			} else {
				rep.comRemplaza("[uo]"," ");
				rep.comRemplaza("[rp]"," ");
				rep.comRemplaza("[fr]"," ");

				rep.comRemplaza("[cCalle]","");
				rep.comRemplaza("[cKm]","");
				rep.comRemplaza("[cColonia]","");
				rep.comRemplaza("[cPuerto]","");
				rep.comRemplaza("[cMunicipio]","");
				rep.comRemplaza("[cEntFed]","");
			}

			//Obras.
			if (vRegsTituloObras.size()>0){
				String cObras="";
				int lPropNacional= 0,lConst=0,lOper=0;
				for(int i=0;i<vRegsTituloObras.size();i++){
					TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(i);
					if (cObras.compareTo("") != 0)
						cObras = cObras  + ", " + vDatosTitObras.getString("cDscObra");
					else
						cObras = vDatosTitObras.getString("cDscObra");

					if (vDatosTitObras.getInt("lPropiedadNacional") == 1 && lPropNacional == 0){
						rep.comRemplaza("[pp]","X");
						lPropNacional = 1;
					}
					if (vDatosTitObras.getInt("lConstruccion") == 1 && lConst == 0){
						rep.comRemplaza("[cn]","X");
						lConst = 1;
					}
					if (vDatosTitObras.getInt("lOperacion") == 1 && lOper == 0){
						rep.comRemplaza("[op]","X");
						lOper = 1;
					}
				}
				if (cObras.compareTo("")!= 0)
					cObras = cObras + ".";
				rep.comRemplaza("[cObras]",cObras);

				TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(0);
				rep.comRemplaza("[cTiempoEstEjecucion]",(vDatosTitObras.get("cTiempoEstEjecucion")!= null)?vDatosTitObras.get("cTiempoEstEjecucion").toString():"");
			} else {
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[cn]"," ");
				rep.comRemplaza("[op]"," ");
				rep.comRemplaza("[cObras]"," ");
				rep.comRemplaza("[cTiempoEstEjecucion]"," ");
			}

			//Planos Aprobados.
			if (vRegsPlanos.size()>0){
				TVDinRep vDatosPlanos = (TVDinRep) vRegsPlanos.get(0);
				rep.comRemplaza("[cNumPlano]",vDatosPlanos.getString("cPlanoNum"));
				rep.comRemplaza("[cDenominacion]",vDatosPlanos.getString("cDenominacion"));
				rep.comRemplaza("[dtPlano]",Fecha.getFechaDDMMYYYY(vDatosPlanos.getDate("dtAprobacionPlano"),"/"));
				rep.comRemplaza("[cExpediente]",vDatosPlanos.getString("cExpedienteNum"));
			} else {
				rep.comRemplaza("[cNumPlano]"," ");
				rep.comRemplaza("[cDenominacion]"," ");
				rep.comRemplaza("[dtPlano]"," ");
				rep.comRemplaza("[cExpediente]"," ");
			}

			//Autorización de Impacto Ambiental para el Inicio de Construcción.
			if (vRegsTituloDocumentos.size()>0){
				for(int i=0;i<vRegsTituloDocumentos.size();i++){
					TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
					switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

					//Titulo de Concesion de la Zona Federal Marítimo Terrestre.
					case 1:case 2:case 3:case 4:{
						break;
					}

					//Autorizacion en Materia de Impacto Ambiental.
					case 5:case 6:{
						rep.comRemplaza("[cNumAutImpAmb]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[dtAutImpAmb]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cDepEmite]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
						rep.comRemplaza("[cObjAutImpAmb]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
						rep.comRemplaza("[dtVigAutImpAmb]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						break;
					}

					//Franquicia de PEMEX.
					case 7:{
						break;
					}

					//Autorizacion de la CONAGUA.
					case 8:{
						break;
					}
					//Autorizacion de la SEMARNAT.
					case 9:{
						break;
					}
					}
				}
			} else {
				rep.comRemplaza("[cNumAutImpAmb]","");
				rep.comRemplaza("[dtAutImpAmb]","");
				rep.comRemplaza("[cDepEmite]","");
				rep.comRemplaza("[cObjAutImpAmb]","");
				rep.comRemplaza("[dtVigAutImpAmb]","");
			}



		} else {

			//Información de la Solicitud.
			rep.comRemplaza("[cNoSolicitud]"," ");
			rep.comRemplaza("[cLugarSolicitud]"," ");
			rep.comRemplaza("[dtSolicitud]"," ");

			//Información de la Autorización.
			rep.comRemplaza("[ao]"," ");
			rep.comRemplaza("[ad]"," ");
			rep.comRemplaza("[aa]"," ");
			rep.comRemplaza("[ac]","X");
			rep.comRemplaza("[ap]"," ");

			//Información del Registro de Contratos.
			rep.comRemplaza("[cNomCesionario]"," ");
			rep.comRemplaza("[dtCelebracion]"," ");
			rep.comRemplaza("[dtRegistro]"," ");
			rep.comRemplaza("[cNumContrato]"," ");

			//Uso del Titulo.
			rep.comRemplaza("[uu]"," ");
			rep.comRemplaza("[up]"," ");

			//Ubicación de la Obra.
			rep.comRemplaza("[uo]"," ");
			rep.comRemplaza("[rp]"," ");
			rep.comRemplaza("[fr]"," ");

			//Fuera de Recinto Portuario.
			rep.comRemplaza("[cCalle]"," ");
			rep.comRemplaza("[cKm]"," ");
			rep.comRemplaza("[cColonia]"," ");
			rep.comRemplaza("[cPuerto]"," ");
			rep.comRemplaza("[cMunicipio]"," ");
			rep.comRemplaza("[cEntFed]"," ");

			//Obras.
			rep.comRemplaza("[pp]"," ");
			rep.comRemplaza("[cn]"," ");
			rep.comRemplaza("[op]"," ");
			rep.comRemplaza("[cObras]"," ");
			rep.comRemplaza("[cTiempoEstEjecucion]"," ");

			//Planos Aprobados.
			rep.comRemplaza("[cNumPlano]"," ");
			rep.comRemplaza("[cDenominacion]"," ");
			rep.comRemplaza("[dtPlano]"," ");
			rep.comRemplaza("[cExpediente]"," ");

			//Autorización de Impacto Ambiental para el Inicio de Construcción.
			rep.comRemplaza("[cNumAutImpAmb]"," ");
			rep.comRemplaza("[dtAutImpAmb]"," ");
			rep.comRemplaza("[cDepEmite]"," ");
			rep.comRemplaza("[cObjAutImpAmb]"," ");
			rep.comRemplaza("[dtVigAutImpAmb]"," ");

		}

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
				0,0,
				iCvePersona,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

	public Vector GenAutIOAPI(String cQuery, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsPlanos = new Vector();
		Vector vRegsTituloDocumentos = new Vector();
		Vector vRegsTituloUbicacion = new Vector();
		Vector vRegsTituloObras = new Vector();
		TWord rep = new TWord();
		TFechas Fecha = new TFechas("44");
		int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0,iCveTipoContrato=0;
		String cNombreAPI="",cNumContrato= "",cNomCesionario="",cNumTitulo="",cAsunto="";
		String cSerPor="";
		String cSerCon="";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio    = Integer.parseInt(aFiltros[0],10),
		iNumSolicitud = Integer.parseInt(aFiltros[1],10),
		iCveTitulo    = Integer.parseInt(aFiltros[2],10),
		iCveContrato  = 0; //Integer.parseInt(aFiltros[3],10);

		//Informacion de la Solicitud, del Registro de Contratos.
		try{
			vRegs = super.FindByGeneric("", "select " +
					"date(TRARegSolicitud.tsRegistro) as dtSolRegistro, " +
					"TRARegSolicitud.iCveOficina, " +
					"GRLOficina.cCalleyNo, " +
					"GRLOficina.cColonia, " +
					"GRLOficina.cCodPostal, " +
					"GRLOficina.cTelefono, " +
					"GRLOficina.cCorreoE, " +
					"GRLPais.cNombre as cDscPais, " +
					"GRLEntidadFed.cNombre as cDscEntidadFed, " +
					"GRLMunicipio.cNombre as cDscMunicipio, " +
					"RGCContrato.iCveContrato, " +
					"RGCContrato.cNumContrato, " +
					"Cesionario.cNomRazonSocial || ' ' || Cesionario.cApPaterno || ' ' || Cesionario.cApMaterno as cNomCesionario, " +
					"RGCContrato.dtRegistro, " +
					"RGCContrato.dtContrato, " +
					"CPATitular.iCveTitular, " +
					"DomTitular.iCveDomicilio, " +
					"RepLegal.iCvePersona as iCveRepLegal " +
					"from TRARegSolicitud " +
					"join CPASolTitulo on CPASolTitulo.iEjercicio = TRARegSolicitud.iEjercicio " +
					"and CPASolTitulo.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
					"join CPATitulo on CPATitulo.iCveTitulo = CPASolTitulo.iCveTitulo " +
					"join CPATitular on CPATitular.iCveTitulo = CPASolTitulo.iCveTitulo " +
					"join GRLPersona Titular on Titular.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio DomTitular on DomTitular.iCvePersona = Titular.iCvePersona " +
					"and DomTitular.lPredeterminado = 1 " +
					"left join GRLRepLegal RepLegal on RepLegal.iCveEmpresa = Titular.iCvePersona " +
					"and RepLegal.lPrincipal = 1 " +
					"left join CPATituloContrato on CPATituloContrato.iCveTitulo = CPATitulo.iCveTitulo " +
					"left join RGCContrato on RGCContrato.iCveContrato = CPATituloContrato.iCveContrato " +
					"left join GRLPersona Cesionario on Cesionario.iCvePersona = RGCContrato.iCveCesionario " +
					"left join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina  " +
					"left join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais  " +
					"left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais  " +
					"and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed  " +
					"left join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais  " +
					"and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed  " +
					"and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Información de los Planos.
		try{
			vRegsPlanos = super.FindByGeneric("", "select OMPPlano.cPlanoNum, " +
					"OMPPlano.cDenominacion, " +
					"OMPPlano.dtAprobacionPlano, " +
					"OMPPlano.cExpedienteNum " +
					"from TRARegSolicitud " +
					"join TRAPlanoXTramite on TRAPlanoXTramite.iEjercicio = TRARegSolicitud.iEjercicio " +
					"and TRAPlanoXTramite.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
					"join OMPPlano on OMPPlano.iCvePlano = TRAPlanoXTramite.iCvePlano " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
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


		//Asunto del Oficio.
		cAsunto = "Autorización de Inicio de Operación";
		rep.iniciaReporte();
		if (vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			//Datos de la API.
			iCvePersona = vDatos.getInt("iCveTitular");
			//cNombreAPI = vDatos.getString("cNombreAPI");
			iCveDomicilio = vDatos.getInt("iCveDomicilio");
			iCveRepLegal = vDatos.getInt("iCveRepLegal");
			//cNumContrato = vDatos.getString("cNumContrato");
			//cNumTitulo = vDatos.getString("cNumTitulo");
			//cNomCesionario= vDatos.getString("cNomCesionaria");
			//iCveTipoContrato = vDatos.getInt("iCveTipoContrato");

			//Información de la Solicitud.
			rep.comRemplaza("[cNoSolicitud]","" + iEjercicio + " - " + iNumSolicitud);
			rep.comRemplaza("[cLugarSolicitud]",vDatos.getString("cCalleyNo") + ",Col. " +
					vDatos.getString("cColonia") + ",C.P. " +
					vDatos.getString("cCodPostal") + "," +
					vDatos.getString("cDscMunicipio") + "," +
					vDatos.getString("cDscEntidadFed"));
			rep.comRemplaza("[dtSolicitud]",Fecha.getFechaDDMMYYYY(vDatos.getDate("dtSolRegistro"),"/"));

			//Información de la Autorización.
			rep.comRemplaza("[ao]"," ");
			rep.comRemplaza("[ad]"," ");
			rep.comRemplaza("[aa]"," ");
			rep.comRemplaza("[ac]"," ");
			rep.comRemplaza("[ap]","X");

			//Información del Registro de Contratos.
			rep.comRemplaza("[cNomCesionario]",vDatos.getString("cNomCesionario") != null && !vDatos.getString("cNomCesionario").equalsIgnoreCase("null") ? vDatos.getString("cNomCesionario"): " " );
			rep.comRemplaza("[dtCelebracion]",(vDatos.getDate("dtContrato")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtContrato"),"/"):"");
			rep.comRemplaza("[dtRegistro]",(vDatos.getDate("dtRegistro")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtRegistro"),"/"):"");
			rep.comRemplaza("[cNumContrato]",vDatos.getString("cNumContrato") != null && !vDatos.getString("cNumContrato").equalsIgnoreCase("null") ? vDatos.getString("cNumContrato"): " " );

			//Uso del Titulo.
			rep.comRemplaza("[uu]","");
			rep.comRemplaza("[up]","");


			//Fuera de Recinto Portuario.
			if (vRegsTituloUbicacion.size()>0){
				TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);
				rep.comRemplaza("[cCalle]",(vDatosTitUbi.get("cCalleTitulo")!= null)?vDatosTitUbi.get("cCalleTitulo").toString():"");
				rep.comRemplaza("[cKm]",(vDatosTitUbi.get("cKm")!= null)?vDatosTitUbi.get("cKm").toString():"");
				rep.comRemplaza("[cColonia]",(vDatosTitUbi.get("cColoniaTitulo")!= null)?vDatosTitUbi.get("cColoniaTitulo").toString():"");
				rep.comRemplaza("[cPuerto]",(vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
				rep.comRemplaza("[cMunicipio]",(vDatosTitUbi.get("cDscMunicipio")!= null)?vDatosTitUbi.get("cDscMunicipio").toString():"");
				rep.comRemplaza("[cEntFed]",(vDatosTitUbi.get("cDscEntidadFed")!= null)?vDatosTitUbi.get("cDscEntidadFed").toString():"");

				//Ubicación de la Obra.
				rep.comRemplaza("[uo]","X");
				System.out.print("\n\n\n Dentro del Recinto portuario ??? :: --->  " + vDatosTitUbi.getInt("lDentroRecintoPort"));
				if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1){
					rep.comRemplaza("[rp]","X");
					rep.comRemplaza("[fr]","");
				} else {
					rep.comRemplaza("[rp]"," ");
					rep.comRemplaza("[fr]","X");
				}
			} else {
				rep.comRemplaza("[uo]"," ");
				rep.comRemplaza("[rp]"," ");
				rep.comRemplaza("[fr]"," ");

				rep.comRemplaza("[cCalle]","");
				rep.comRemplaza("[cKm]","");
				rep.comRemplaza("[cColonia]","");
				rep.comRemplaza("[cPuerto]","");
				rep.comRemplaza("[cMunicipio]","");
				rep.comRemplaza("[cEntFed]","");
			}

			//Obras.
			if (vRegsTituloObras.size()>0){
				String cObras="";
				int lPropNacional= 0,lConst=0,lOper=0;
				for(int i=0;i<vRegsTituloObras.size();i++){
					TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(i);
					if (cObras.compareTo("") != 0)
						cObras = cObras  + ", " + vDatosTitObras.getString("cDscObra");
					else
						cObras = vDatosTitObras.getString("cDscObra");

					if (vDatosTitObras.getInt("lPropiedadNacional") == 1 && lPropNacional == 0){
						rep.comRemplaza("[pp]","X");
						lPropNacional = 1;
					}
					if (vDatosTitObras.getInt("lConstruccion") == 1 && lConst == 0){
						rep.comRemplaza("[cn]","X");
						lConst = 1;
					}
					if (vDatosTitObras.getInt("lOperacion") == 1 && lOper == 0){
						rep.comRemplaza("[op]","X");
						lOper = 1;
					}
				}
				if (cObras.compareTo("")!= 0)
					cObras = cObras + ".";
				rep.comRemplaza("[cObras]",cObras);

				TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(0);
				rep.comRemplaza("[cTiempoEstEjecucion]",(vDatosTitObras.get("cTiempoEstEjecucion")!= null)?vDatosTitObras.get("cTiempoEstEjecucion").toString():"");
			} else {
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[cn]"," ");
				rep.comRemplaza("[op]"," ");
				rep.comRemplaza("[cObras]"," ");
				rep.comRemplaza("[cTiempoEstEjecucion]"," ");
			}

			//Planos Aprobados.
			if (vRegsPlanos.size()>0){
				TVDinRep vDatosPlanos = (TVDinRep) vRegsPlanos.get(0);
				rep.comRemplaza("[cNumPlano]",vDatosPlanos.getString("cPlanoNum"));
				rep.comRemplaza("[cDenominacion]",vDatosPlanos.getString("cDenominacion"));
				rep.comRemplaza("[dtPlano]",Fecha.getFechaDDMMYYYY(vDatosPlanos.getDate("dtAprobacionPlano"),"/"));
				rep.comRemplaza("[cExpediente]",vDatosPlanos.getString("cExpedienteNum"));
			} else {
				rep.comRemplaza("[cNumPlano]"," ");
				rep.comRemplaza("[cDenominacion]"," ");
				rep.comRemplaza("[dtPlano]"," ");
				rep.comRemplaza("[cExpediente]"," ");
			}

			//Autorización de Impacto Ambiental para el Inicio de Construcción.
			if (vRegsTituloDocumentos.size()>0){
				for(int i=0;i<vRegsTituloDocumentos.size();i++){
					TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
					switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

					//Titulo de Concesion de la Zona Federal Marítimo Terrestre.
					case 1:case 2:case 3:case 4:{
						break;
					}

					//Autorizacion en Materia de Impacto Ambiental.
					case 5:case 6:{
						rep.comRemplaza("[cNumAutImpAmb]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[dtAutImpAmb]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cDepEmite]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
						rep.comRemplaza("[cObjAutImpAmb]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
						rep.comRemplaza("[dtVigAutImpAmb]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						break;
					}

					//Franquicia de PEMEX.
					case 7:{
						break;
					}

					//Autorizacion de la CONAGUA.
					case 8:{
						break;
					}
					//Autorizacion de la SEMARNAT.
					case 9:{
						break;
					}
					}
				}
			} else {
				rep.comRemplaza("[cNumAutImpAmb]","");
				rep.comRemplaza("[dtAutImpAmb]","");
				rep.comRemplaza("[cDepEmite]","");
				rep.comRemplaza("[cObjAutImpAmb]","");
				rep.comRemplaza("[dtVigAutImpAmb]","");
			}



		} else {

			//Información de la Solicitud.
			rep.comRemplaza("[cNoSolicitud]"," ");
			rep.comRemplaza("[cLugarSolicitud]"," ");
			rep.comRemplaza("[dtSolicitud]"," ");

			//Información de la Autorización.
			rep.comRemplaza("[ao]"," ");
			rep.comRemplaza("[ad]"," ");
			rep.comRemplaza("[aa]"," ");
			rep.comRemplaza("[ac]","X");
			rep.comRemplaza("[ap]"," ");

			//Información del Registro de Contratos.
			rep.comRemplaza("[cNomCesionario]"," ");
			rep.comRemplaza("[dtCelebracion]"," ");
			rep.comRemplaza("[dtRegistro]"," ");
			rep.comRemplaza("[cNumContrato]"," ");

			//Uso del Titulo.
			rep.comRemplaza("[uu]"," ");
			rep.comRemplaza("[up]"," ");

			//Ubicación de la Obra.
			rep.comRemplaza("[uo]"," ");
			rep.comRemplaza("[rp]"," ");
			rep.comRemplaza("[fr]"," ");

			//Fuera de Recinto Portuario.
			rep.comRemplaza("[cCalle]"," ");
			rep.comRemplaza("[cKm]"," ");
			rep.comRemplaza("[cColonia]"," ");
			rep.comRemplaza("[cPuerto]"," ");
			rep.comRemplaza("[cMunicipio]"," ");
			rep.comRemplaza("[cEntFed]"," ");

			//Obras.
			rep.comRemplaza("[pp]"," ");
			rep.comRemplaza("[cn]"," ");
			rep.comRemplaza("[op]"," ");
			rep.comRemplaza("[cTiempoEstEjecucion]"," ");

			//Planos Aprobados.
			rep.comRemplaza("[cNumPlano]"," ");
			rep.comRemplaza("[cDenominacion]"," ");
			rep.comRemplaza("[dtPlano]"," ");
			rep.comRemplaza("[cExpediente]"," ");

			//Autorización de Impacto Ambiental para el Inicio de Construcción.
			rep.comRemplaza("[cNumAutImpAmb]"," ");
			rep.comRemplaza("[dtAutImpAmb]"," ");
			rep.comRemplaza("[cDepEmite]"," ");
			rep.comRemplaza("[cObjAutImpAmb]"," ");
			rep.comRemplaza("[dtVigAutImpAmb]"," ");

		}

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
				0,0,
				iCvePersona,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

	public Vector GenAutTecObrasAPI(String cQuery, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
		TWord rep = new TWord();
		TFechas Fecha = new TFechas("44");
		TDConsultaConcesiones dTituloAnt = new TDConsultaConcesiones();

		Vector vRegs = new Vector(), vRegsPlanos = new Vector(), vRegsTituloUbicacion = new Vector(),
		vRegsTituloObras = new Vector(), vDataTitulosAnteriores = new Vector();

		TVDinRep dinRegs = new TVDinRep(), dinRegPlanos = new TVDinRep(), 	dinRegTituloUbi = new TVDinRep(),
		dinRegsTituloObra = new TVDinRep(), dinDataTitulosAnt = new TVDinRep();

		int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0;
		String cTiempoEjecucion="",cAsunto="", cObras = "", cExpedientesNum = "", cFechaAprobPlanos = "", cFechaUltModif = "",
		cFechaUlt = "", cRecinto = "__________";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio    = Integer.parseInt(aFiltros[0],10),
		iNumSolicitud = Integer.parseInt(aFiltros[1],10),
		iCveTitulo    = Integer.parseInt(aFiltros[2],10);


		vDataTitulosAnteriores = dTituloAnt.regresaTitulosConcesion(iCveTitulo);

		//Informacion de la Solicitud, del Registro de Contratos.
		try{
			vRegs = super.FindByGeneric("", "SELECT " +
					"DATE(TRAREGSOLICITUD.TSREGISTRO) AS DTSOLREGISTRO, " +
					"TITULAR.CNOMRAZONSOCIAL || ' ' || TITULAR.CAPPATERNO || ' ' || TITULAR.CAPMATERNO AS CTITULAR, " +
					"CPATITULAR.ICVETITULAR AS ICVETITULAR, " +
					"DOMTITULAR.ICVEDOMICILIO AS ICVEDOMTITULAR, " +
					"REPLEGAL.ICVEPERSONA AS ICVEREPLEGAL " +
					"FROM TRAREGSOLICITUD " +
					"JOIN CPASOLTITULO ON CPASOLTITULO.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO " +
					"AND CPASOLTITULO.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD " +
					"JOIN CPATITULO ON CPATITULO.ICVETITULO = CPASOLTITULO.ICVETITULO " +
					"JOIN CPATITULAR ON CPATITULAR.ICVETITULO = CPASOLTITULO.ICVETITULO " +
					"JOIN GRLPERSONA TITULAR ON TITULAR.ICVEPERSONA = CPATITULAR.ICVETITULAR " +
					"JOIN GRLDOMICILIO DOMTITULAR ON DOMTITULAR.ICVEPERSONA = TITULAR.ICVEPERSONA " +
					"AND DOMTITULAR.LPREDETERMINADO = 1 " +
					"LEFT JOIN GRLREPLEGAL REPLEGAL ON REPLEGAL.ICVEEMPRESA = TITULAR.ICVEPERSONA " +
					"AND REPLEGAL.LPRINCIPAL = 1 " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Información de los Planos.
		try{
			vRegsPlanos = super.FindByGeneric("", "select OMPPlano.cPlanoNum as cPlanoNum, " +
					"OMPPlano.cDenominacion as cDenominacion, " +
					"OMPPlano.dtAprobacionPlano  as dtAprobacionPlano, " +
					"OMPPlano.cExpedienteNum " +
					"from TRARegSolicitud " +
					"join TRAPlanoXTramite on TRAPlanoXTramite.iEjercicio = TRARegSolicitud.iEjercicio " +
					"and TRAPlanoXTramite.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
					"join OMPPlano on OMPPlano.iCvePlano = TRAPlanoXTramite.iCvePlano " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
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

		//Informacion de las Obras del Título.
		try{
			vRegsTituloObras = super.FindByGeneric("",
					"SELECT " +
					"CDSCPRODUCTO, " +
					"CTIEMPOESTEJECUCION " +
					"FROM OMPPRODOBRASERV " +
					"where ICVETITULO =  " + iCveTitulo,
					dataSourceName);
		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
			cMensaje += ex2.getMessage();
		}


		//Asunto del Oficio.
		cAsunto = "Autorización de Técnica de Obra a Cargo de una API";

		if(vRegs.size() > 0)
			dinRegs = (TVDinRep) vRegs.get(0);

		iCvePersona = dinRegs.getInt("iCveTitular");
		iCveDomicilio = dinRegs.getInt("iCveDomTitular");
		iCveRepLegal = dinRegs.getInt("iCveRepLegal");

		if(vRegsTituloUbicacion.size() > 0){
			dinRegTituloUbi = (TVDinRep) vRegsTituloUbicacion.get(0);
			cRecinto = dinRegTituloUbi.getString("cDscEntidadFed");
		}

		for(int i = 0; i<vRegsTituloObras.size(); i++){
			dinRegsTituloObra = (TVDinRep) vRegsTituloObras.get(i);
			cObras = !cObras.equalsIgnoreCase("")?cObras + ", " + dinRegsTituloObra.getString("cDscObra"):
				dinRegsTituloObra.getString("CDSCPRODUCTO");

			cTiempoEjecucion = !cTiempoEjecucion.equalsIgnoreCase("")?cTiempoEjecucion + ", " + dinRegsTituloObra.getString("cTiempoEstEjecucion"):
				dinRegsTituloObra.getString("cTiempoEstEjecucion");
		}

		for(int i = 0; i<vRegsPlanos.size(); i++){
			dinRegPlanos = (TVDinRep) vRegsPlanos.get(i);
			cExpedientesNum = !cExpedientesNum.equalsIgnoreCase("")?cExpedientesNum + ", " + dinRegPlanos.getString("cExpedienteNum"):
				dinRegPlanos.getString("cExpedienteNum");
			cFechaAprobPlanos = !cFechaAprobPlanos.equalsIgnoreCase("")?cFechaAprobPlanos + ", " + Fecha.getDateSPN(dinRegPlanos.getDate("dtAprobacionPlano")):
				Fecha.getDateSPN(dinRegPlanos.getDate("dtAprobacionPlano"));
		}


		String cFechaTemp = "";
		for(int i = 0; i<vDataTitulosAnteriores.size(); i++){
			dinDataTitulosAnt = (TVDinRep) vDataTitulosAnteriores.get(i);
			if(i == 0)
				cFechaUlt = dinDataTitulosAnt.getDate("dtIniVigencia") != null && !dinDataTitulosAnt.getDate("dtIniVigencia").equals("")?
						Fecha.getDateSPN(dinDataTitulosAnt.getDate("dtIniVigencia")): "___/____/____";

			if(i == (vDataTitulosAnteriores.size())){
				cFechaTemp = dinDataTitulosAnt.getDate("dtIniVigencia") != null && !dinDataTitulosAnt.getDate("dtIniVigencia").equals("")?
						Fecha.getDateSPN(dinDataTitulosAnt.getDate("dtIniVigencia")): "___/____/____";
				cFechaUltModif = " y su Modificación de " + cFechaTemp;
			}
		}


		rep.iniciaReporte();

		TWord rep2 = new TWord();
		rep2.iniciaReporte();

		rep2.comEligeTabla("cMarcadorPlano");
		for(int i = 0; i < vRegsPlanos.size() ; i++){
			dinRegPlanos = (TVDinRep) vRegsPlanos.get(i);
			rep2.comDespliegaCelda(dinRegPlanos.getString("cPlanoNum"));
			rep2.comDespliegaCelda(dinRegPlanos.getString("cDenominacion"));
		}

		rep.comRemplaza("[dtSolicitudLetra]", dinRegs.getDate("dtSolRegistro") != null && !dinRegs.getDate("dtSolRegistro").equals("") ?
				Fecha.getDateSPN(dinRegs.getDate("dtSolRegistro")): "_____/_____/____");

		rep.comRemplaza("[cDenObra]", !cObras.equalsIgnoreCase("")?cObras:"______");
		rep.comRemplaza("[cNomEmpresa]", dinRegs.getString("cTitular"));
		rep.comRemplaza("[cDscRecintoPortuario]", cRecinto);
		rep.comRemplaza("[cNumOfiOM]", cExpedientesNum);
		rep.comRemplaza("[cFecLetra]", cFechaAprobPlanos);
		rep.comRemplaza("[cNumDiasInicio]", !cTiempoEjecucion.equalsIgnoreCase("")?cTiempoEjecucion:"________");
		rep.comRemplaza("[dtConcesion]", cFechaUlt);
		rep.comRemplaza("[cModificaciones]", cFechaUltModif);

		Vector vRegresa = new Vector();
		Vector vMarcadores = new Vector();

		vMarcadores = rep2.getVectorDatos(true);

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		vRegresa =  new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
				0,0,
				iCvePersona,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"", "",
				true,"cCuerpo",new Vector(),
				true, new Vector(),
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

		Vector vTemp = ((Vector)vRegresa.elementAt(1));
		Vector vTemp1 = ((Vector)vMarcadores.elementAt(1));
		for(int i=0; i<vTemp1.size(); i++)
			vTemp.add(vTemp1.get(i));
		vRegresa.setElementAt(vTemp,1);
		//vRegresa.addElement(vMarcadores);

		return vRegresa;

	}

	public Vector GenAutObrMarDraAPI(String cQuery, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		Vector vRegsPlanos = new Vector();
		Vector vRegsTituloDocumentos = new Vector();
		Vector vRegsTituloUbicacion = new Vector();
		Vector vRegsTituloObras = new Vector();
		TWord rep = new TWord();
		TFechas Fecha = new TFechas("44");
		int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0,iCveTipoContrato=0;
		String cNombreAPI="",cNumContrato= "",cNomCesionario="",cNumTitulo="",cAsunto="";
		String cSerPor="";
		String cSerCon="";
		String[] aFiltros = cQuery.split(",");
		int iEjercicio    = Integer.parseInt(aFiltros[0],10),
		iNumSolicitud = Integer.parseInt(aFiltros[1],10),
		iCveTitulo    = Integer.parseInt(aFiltros[2],10),
		iCveContrato  = 0; //Integer.parseInt(aFiltros[3],10);

		//Informacion de la Solicitud, del Registro de Contratos.
		try{
			vRegs = super.FindByGeneric("", "select " +
					"date(TRARegSolicitud.tsRegistro) as dtSolRegistro, " +
					"TRARegSolicitud.iCveOficina, " +
					"GRLOficina.cCalleyNo, " +
					"GRLOficina.cColonia, " +
					"GRLOficina.cCodPostal, " +
					"GRLOficina.cTelefono, " +
					"GRLOficina.cCorreoE, " +
					"GRLPais.cNombre as cDscPais, " +
					"GRLEntidadFed.cNombre as cDscEntidadFed, " +
					"GRLMunicipio.cNombre as cDscMunicipio, " +
					"RGCContrato.iCveContrato, " +
					"RGCContrato.cNumContrato, " +
					"Cesionario.cNomRazonSocial || ' ' || Cesionario.cApPaterno || ' ' || Cesionario.cApMaterno as cNomCesionario, " +
					"RGCContrato.dtRegistro, " +
					"RGCContrato.dtContrato, " +
					"CPATitular.iCveTitular, " +
					"DomTitular.iCveDomicilio, " +
					"RepLegal.iCvePersona as iCveRepLegal " +
					"from TRARegSolicitud " +
					"join CPASolTitulo on CPASolTitulo.iEjercicio = TRARegSolicitud.iEjercicio " +
					"and CPASolTitulo.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
					"join CPATitulo on CPATitulo.iCveTitulo = CPASolTitulo.iCveTitulo " +
					"join CPATitular on CPATitular.iCveTitulo = CPASolTitulo.iCveTitulo " +
					"join GRLPersona Titular on Titular.iCvePersona = CPATitular.iCveTitular " +
					"join GRLDomicilio DomTitular on DomTitular.iCvePersona = Titular.iCvePersona " +
					"and DomTitular.lPredeterminado = 1 " +
					"left join GRLRepLegal RepLegal on RepLegal.iCveEmpresa = Titular.iCvePersona " +
					"and RepLegal.lPrincipal = 1 " +
					"join CPATituloContrato on CPATituloContrato.iCveTitulo = CPATitulo.iCveTitulo " +
					"join RGCContrato on RGCContrato.iCveContrato = CPATituloContrato.iCveContrato " +
					"join GRLPersona Cesionario on Cesionario.iCvePersona = RGCContrato.iCveCesionario " +
					"join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina  " +
					"join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais  " +
					"join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais  " +
					"and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed  " +
					"join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais  " +
					"and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed  " +
					"and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
		}

		//Información de los Planos.
		try{
			vRegsPlanos = super.FindByGeneric("", "select OMPPlano.cPlanoNum, " +
					"OMPPlano.cDenominacion, " +
					"OMPPlano.dtAprobacionPlano, " +
					"OMPPlano.cExpedienteNum " +
					"from TRARegSolicitud " +
					"join TRAPlanoXTramite on TRAPlanoXTramite.iEjercicio = TRARegSolicitud.iEjercicio " +
					"and TRAPlanoXTramite.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
					"join OMPPlano on OMPPlano.iCvePlano = TRAPlanoXTramite.iCvePlano " +
					"where TRARegSolicitud.iEjercicio = " + iEjercicio + " " +
					"and TRARegSolicitud.iNumSolicitud = " + iNumSolicitud + " ",
					dataSourceName);
		}catch(SQLException ex){
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
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


		//Asunto del Oficio.
		cAsunto = "Autorización de Obras Maritimas y de Dragado de una API";
		rep.iniciaReporte();
		if (vRegs.size() > 0){
			TVDinRep vDatos = (TVDinRep) vRegs.get(0);

			//Datos de la API.
			iCvePersona = vDatos.getInt("iCveTitular");
			//cNombreAPI = vDatos.getString("cNombreAPI");
			iCveDomicilio = vDatos.getInt("iCveDomicilio");
			iCveRepLegal = vDatos.getInt("iCveRepLegal");
			//cNumContrato = vDatos.getString("cNumContrato");
			//cNumTitulo = vDatos.getString("cNumTitulo");
			//cNomCesionario= vDatos.getString("cNomCesionaria");
			//iCveTipoContrato = vDatos.getInt("iCveTipoContrato");

			//Información de la Solicitud.
			rep.comRemplaza("[cNoSolicitud]","" + iEjercicio + " - " + iNumSolicitud);
			rep.comRemplaza("[cLugarSolicitud]",vDatos.getString("cCalleyNo") + ",Col. " +
					vDatos.getString("cColonia") + ",C.P. " +
					vDatos.getString("cCodPostal") + "," +
					vDatos.getString("cDscMunicipio") + "," +
					vDatos.getString("cDscEntidadFed"));
			rep.comRemplaza("[dtSolicitud]",Fecha.getFechaDDMMYYYY(vDatos.getDate("dtSolRegistro"),"/"));

			//Información de la Autorización.
			rep.comRemplaza("[ao]"," ");
			rep.comRemplaza("[ad]"," ");
			rep.comRemplaza("[aa]"," ");
			rep.comRemplaza("[ac]","X");
			rep.comRemplaza("[ap]"," ");

			//Información del Registro de Contratos.
			rep.comRemplaza("[cNomCesionario]",vDatos.getString("cNomCesionario"));
			rep.comRemplaza("[dtCelebracion]",(vDatos.getDate("dtContrato")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtContrato"),"/"):"");
			rep.comRemplaza("[dtRegistro]",(vDatos.getDate("dtRegistro")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtRegistro"),"/"):"");
			rep.comRemplaza("[cNumContrato]",vDatos.getString("cNumContrato"));

			//Uso del Titulo.
			rep.comRemplaza("[uu]","");
			rep.comRemplaza("[up]","");


			//Fuera de Recinto Portuario.
			if (vRegsTituloUbicacion.size()>0){
				TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);
				rep.comRemplaza("[cCalle]",(vDatosTitUbi.get("cCalleTitulo")!= null)?vDatosTitUbi.get("cCalleTitulo").toString():"");
				rep.comRemplaza("[cKm]",(vDatosTitUbi.get("cKm")!= null)?vDatosTitUbi.get("cKm").toString():"");
				rep.comRemplaza("[cColonia]",(vDatosTitUbi.get("cColoniaTitulo")!= null)?vDatosTitUbi.get("cColoniaTitulo").toString():"");
				rep.comRemplaza("[cPuerto]",(vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
				rep.comRemplaza("[cMunicipio]",(vDatosTitUbi.get("cDscMunicipio")!= null)?vDatosTitUbi.get("cDscMunicipio").toString():"");
				rep.comRemplaza("[cEntFed]",(vDatosTitUbi.get("cDscEntidadFed")!= null)?vDatosTitUbi.get("cDscEntidadFed").toString():"");

				//Ubicación de la Obra.
				rep.comRemplaza("[uo]","X");
				if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1){
					rep.comRemplaza("[rp]","X");
					rep.comRemplaza("[fr]","");
				} else {
					rep.comRemplaza("[rp]"," ");
					rep.comRemplaza("[fr]","X");
				}
			} else {
				rep.comRemplaza("[uo]"," ");
				rep.comRemplaza("[rp]"," ");
				rep.comRemplaza("[fr]"," ");

				rep.comRemplaza("[cCalle]","");
				rep.comRemplaza("[cKm]","");
				rep.comRemplaza("[cColonia]","");
				rep.comRemplaza("[cPuerto]","");
				rep.comRemplaza("[cMunicipio]","");
				rep.comRemplaza("[cEntFed]","");
			}

			//Obras.
			if (vRegsTituloObras.size()>0){
				String cObras="";
				int lPropNacional= 0,lConst=0,lOper=0;
				for(int i=0;i<vRegsTituloObras.size();i++){
					TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(i);
					if (cObras.compareTo("") != 0)
						cObras = cObras  + ", " + vDatosTitObras.getString("cDscObra");
					else
						cObras = vDatosTitObras.getString("cDscObra");

					if (vDatosTitObras.getInt("lPropiedadNacional") == 1 && lPropNacional == 0){
						rep.comRemplaza("[pp]","X");
						lPropNacional = 1;
					}
					if (vDatosTitObras.getInt("lConstruccion") == 1 && lConst == 0){
						rep.comRemplaza("[cn]","X");
						lConst = 1;
					}
					if (vDatosTitObras.getInt("lOperacion") == 1 && lOper == 0){
						rep.comRemplaza("[op]","X");
						lOper = 1;
					}
				}
				if (cObras.compareTo("")!= 0)
					cObras = cObras + ".";
				rep.comRemplaza("[cObras]",cObras);

				TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(0);
				rep.comRemplaza("[cTiempoEstEjecucion]",(vDatosTitObras.get("cTiempoEstEjecucion")!= null)?vDatosTitObras.get("cTiempoEstEjecucion").toString():"");
			} else {
				rep.comRemplaza("[pp]"," ");
				rep.comRemplaza("[cn]"," ");
				rep.comRemplaza("[op]"," ");
				rep.comRemplaza("[cObras]"," ");
				rep.comRemplaza("[cTiempoEstEjecucion]"," ");
			}

			//Planos Aprobados.
			if (vRegsPlanos.size()>0){
				TVDinRep vDatosPlanos = (TVDinRep) vRegsPlanos.get(0);
				rep.comRemplaza("[cNumPlano]",vDatosPlanos.getString("cPlanoNum"));
				rep.comRemplaza("[cDenominacion]",vDatosPlanos.getString("cDenominacion"));
				rep.comRemplaza("[dtPlano]",Fecha.getFechaDDMMYYYY(vDatosPlanos.getDate("dtAprobacionPlano"),"/"));
				rep.comRemplaza("[cExpediente]",vDatosPlanos.getString("cExpedienteNum"));
			} else {
				rep.comRemplaza("[cNumPlano]"," ");
				rep.comRemplaza("[cDenominacion]"," ");
				rep.comRemplaza("[dtPlano]"," ");
				rep.comRemplaza("[cExpediente]"," ");
			}

			//Autorización de Impacto Ambiental para el Inicio de Construcción.
			if (vRegsTituloDocumentos.size()>0){
				for(int i=0;i<vRegsTituloDocumentos.size();i++){
					TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
					switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

					//Titulo de Concesion de la Zona Federal Marítimo Terrestre.
					case 1:case 2:case 3:case 4:{
						break;
					}

					//Autorizacion en Materia de Impacto Ambiental.
					case 5:case 6:{
						rep.comRemplaza("[cNumAutImpAmb]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
						rep.comRemplaza("[dtAutImpAmb]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
						rep.comRemplaza("[cDepEmite]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
						rep.comRemplaza("[cObjAutImpAmb]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
						rep.comRemplaza("[dtVigAutImpAmb]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
						break;
					}

					//Franquicia de PEMEX.
					case 7:{
						break;
					}

					//Autorizacion de la CONAGUA.
					case 8:{
						break;
					}
					//Autorizacion de la SEMARNAT.
					case 9:{
						break;
					}
					}
				}
			} else {
				rep.comRemplaza("[cNumAutImpAmb]","");
				rep.comRemplaza("[dtAutImpAmb]","");
				rep.comRemplaza("[cDepEmite]","");
				rep.comRemplaza("[cObjAutImpAmb]","");
				rep.comRemplaza("[dtVigAutImpAmb]","");
			}



		} else {

			//Información de la Solicitud.
			rep.comRemplaza("[cNoSolicitud]"," ");
			rep.comRemplaza("[cLugarSolicitud]"," ");
			rep.comRemplaza("[dtSolicitud]"," ");

			//Información de la Autorización.
			rep.comRemplaza("[ao]"," ");
			rep.comRemplaza("[ad]"," ");
			rep.comRemplaza("[aa]"," ");
			rep.comRemplaza("[ac]","X");
			rep.comRemplaza("[ap]"," ");

			//Información del Registro de Contratos.
			rep.comRemplaza("[cNomCesionario]"," ");
			rep.comRemplaza("[dtCelebracion]"," ");
			rep.comRemplaza("[dtRegistro]"," ");
			rep.comRemplaza("[cNumContrato]"," ");

			//Uso del Titulo.
			rep.comRemplaza("[uu]"," ");
			rep.comRemplaza("[up]"," ");

			//Ubicación de la Obra.
			rep.comRemplaza("[uo]"," ");
			rep.comRemplaza("[rp]"," ");
			rep.comRemplaza("[fr]"," ");

			//Fuera de Recinto Portuario.
			rep.comRemplaza("[cCalle]"," ");
			rep.comRemplaza("[cKm]"," ");
			rep.comRemplaza("[cColonia]"," ");
			rep.comRemplaza("[cPuerto]"," ");
			rep.comRemplaza("[cMunicipio]"," ");
			rep.comRemplaza("[cEntFed]"," ");

			//Obras.
			rep.comRemplaza("[pp]"," ");
			rep.comRemplaza("[cn]"," ");
			rep.comRemplaza("[op]"," ");
			rep.comRemplaza("[cObras]"," ");
			rep.comRemplaza("[cTiempoEstEjecucion]"," ");

			//Planos Aprobados.
			rep.comRemplaza("[cNumPlano]"," ");
			rep.comRemplaza("[cDenominacion]"," ");
			rep.comRemplaza("[dtPlano]"," ");
			rep.comRemplaza("[cExpediente]"," ");

			//Autorización de Impacto Ambiental para el Inicio de Construcción.
			rep.comRemplaza("[cNumAutImpAmb]"," ");
			rep.comRemplaza("[dtAutImpAmb]"," ");
			rep.comRemplaza("[cDepEmite]"," ");
			rep.comRemplaza("[cObjAutImpAmb]"," ");
			rep.comRemplaza("[dtVigAutImpAmb]"," ");

		}

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
				0,0,
				iCvePersona,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"", "",
				true,"cCuerpo",vcCuerpo,
				true, vcCopiasPara,
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

	public Vector generaPrestacionServ(String cFiltro){
		TWord rep = new TWord();
		Vector vDataContratos = new Vector(), vDataTitular = new Vector(), vDataSolicitud = new Vector();
		TVDinRep dinDataContratos = new TVDinRep(), dinDataTitular = new TVDinRep(), dinDataSolicitud = new TVDinRep();
		String[] aFiltro = cFiltro.split(",");
		int iCveContrato = Integer.parseInt(aFiltro[0],10);
		int iCveTitular = Integer.parseInt(aFiltro[1],10);

		vDataContratos = this.consultaContratos(iCveContrato);
		vDataTitular = this.consultaTitular(iCveTitular);
		vDataSolicitud = this.consultaContratoSolicitud(iCveContrato);

		if(vDataContratos.size() > 0)
			dinDataContratos = (TVDinRep) vDataContratos.get(0);

		if(vDataTitular.size() > 0)
			dinDataTitular = (TVDinRep) vDataTitular.get(0);

		if(vDataSolicitud.size() > 0)
			dinDataSolicitud = (TVDinRep) vDataSolicitud.get(0);

		rep.iniciaReporte();
		rep.comRemplaza("[cAPI]", dinDataTitular.getString("CTITULAR"));
		rep.comRemplaza("[cPersona]", dinDataContratos.getString("CNOMBRE"));
		rep.comRemplaza("[cObjeto]", dinDataContratos.getString("COBJETO"));
		rep.comRemplaza("[cPersona]", dinDataContratos.getString("CNOMBRE"));
		if(!dinDataContratos.getString("CNUMCONVOCATORIA").equalsIgnoreCase("")
				&& !dinDataContratos.getString("CNUMCONVOCATORIA").equalsIgnoreCase("null")){
			rep.comRemplaza("[cDir]", " ");
			rep.comRemplaza("[cCon]", "X");
		}else{
			rep.comRemplaza("[cDir]", "X");
			rep.comRemplaza("[cCon]", " ");
		}
		if(dinDataContratos.getInt("IPMDP") == 1){
			rep.comRemplaza("[cSI]", "X");
			rep.comRemplaza("[cNo]", " ");
		}else{
			rep.comRemplaza("[cSI]", " ");
			rep.comRemplaza("[cNo]", "X");
		}
		rep.comRemplaza("[cFechaCelebrado]", dinDataContratos.getDate("DTCONTRATO") != null &&
				!dinDataContratos.getDate("DTCONTRATO").equals("")? tFecha.getDateSPN(dinDataContratos.getDate("DTCONTRATO")):
		"---");
		rep.comRemplaza("[cFechaRecibido]", dinDataSolicitud.getDate("DTREGISTRO") != null &&
				!dinDataSolicitud.getDate("DTREGISTRO").equals("")? tFecha.getDateSPN(dinDataSolicitud.getDate("DTREGISTRO")):
		"---");
		rep.comRemplaza("[cVigencia]", dinDataContratos.getString("CVIGENCIA") + " Años");
		rep.comRemplaza("[cNumOriginales]", ""+dinDataContratos.getInt("INUMORIGINALES"));
		rep.comRemplaza("[cImporteFianza]", ""+dinDataSolicitud.getDouble("DIMPORTEPAGADO"));



		return rep.getVectorDatos(true);
	}

	public Vector generaCesionDerechos(String cFiltro){
		System.out.print("-------------------TDOficiosConcessiones-->cFiltro:"+cFiltro+"<-----");
		TWord rep = new TWord();
		Vector vDataContratos = new Vector(), vDataTitular = new Vector(), vDataUbicacion = new Vector(),
		vDataSolicitud = new Vector();
		TVDinRep dinDataContratos = new TVDinRep(), dinDataTitular = new TVDinRep(), dinDataUbicacion = new TVDinRep(),
		dinDataSolicitud = new TVDinRep();
		String[] aFiltro = cFiltro.split("=");
		String cSuperficie = "", cSeparador = "";
		int iCveContrato = Integer.parseInt(aFiltro[0],10);
		int iCveTitular = Integer.parseInt(aFiltro[1],10);
		String cFechaReg = "";

		vDataContratos = this.consultaContratos(iCveContrato);
		vDataTitular = this.consultaTitular(iCveTitular);


		if(vDataContratos.size() > 0)
			dinDataContratos = (TVDinRep) vDataContratos.get(0);

		if(vDataTitular.size() > 0)
			dinDataTitular = (TVDinRep) vDataTitular.get(0);

		rep.iniciaReporte();
		
		rep.comRemplaza("[cREFAPI]", aFiltro[2]);
		rep.comRemplaza("[cControlIntDGP]", aFiltro[3]);
		rep.comRemplaza("[iSolicitud]", aFiltro[4]);
		cFechaReg = tFecha.getDateSPN(tFecha.getDateSQL(aFiltro[5]));
		rep.comRemplaza("[cFechaRegistro]", cFechaReg);
		
		rep.comRemplaza("[cRepLegalAPI]", aFiltro[6]);
		rep.comRemplaza("[cAPI]", aFiltro[7]);
		rep.comRemplaza("[cTipoContrato]", aFiltro[8]);
		rep.comRemplaza("[cCesionario]", aFiltro[9]);
		rep.comRemplaza("[cNumContrato]", aFiltro[10]);
		rep.comRemplaza("[cTipoServicio]", aFiltro[11]);
		
		

		return rep.getVectorDatos(true);
	}
	
	
	public Vector generaModificacionPrestacionServ(String cFiltro){
		TWord rep = new TWord();
		TDCYSRRContrato dContratoAnt = new TDCYSRRContrato();
		Vector vDataContratos = new Vector(), vDataTitular = new Vector(), vDataContratosAnt = new Vector(),
		vDataSolicitud = new Vector();
		TVDinRep dinDataContratos = new TVDinRep(), dinDataTitular = new TVDinRep(),
		dinDataContratosAnt = new TVDinRep(), dinDataSolicitud = new TVDinRep();
		String[] aFiltro = cFiltro.split(",");
		String cObjetoPenUlt = "",cCelebradoPenUlt = "", cObjetoOrig = "", cModoAdjudicOrig = "", cCelebradoOrig = "",
		cVigenciaOrig = "";
		int iCveContrato = Integer.parseInt(aFiltro[0],10);
		int iCveTitular = Integer.parseInt(aFiltro[1],10);

		vDataContratos = this.consultaContratos(iCveContrato);
		vDataTitular = this.consultaTitular(iCveTitular);
		vDataContratosAnt = dContratoAnt.regresaContratosAnt(iCveContrato);
		vDataSolicitud = this.consultaContratoSolicitud(iCveContrato);

		if(vDataContratos.size() > 0)
			dinDataContratos = (TVDinRep) vDataContratos.get(0);

		if(vDataTitular.size() > 0)
			dinDataTitular = (TVDinRep) vDataTitular.get(0);

		if(vDataSolicitud.size() > 0)
			dinDataSolicitud = (TVDinRep) vDataSolicitud.get(0);

		for(int i =0; i < vDataContratosAnt.size(); i++){
			dinDataContratosAnt = (TVDinRep) vDataContratosAnt.get(i);

			if(i == 1 && vDataContratosAnt.size() > 1){
				cObjetoPenUlt = !dinDataContratosAnt.getString("COBJETO").equalsIgnoreCase("null")?
						dinDataContratosAnt.getString("COBJETO"): "---- ";
						cCelebradoPenUlt = dinDataContratosAnt.getDate("DTCONTRATO") != null &&
						!dinDataContratosAnt.getDate("DTCONTRATO").equals("")?tFecha.getDateSPN(dinDataContratosAnt.getDate("DTCONTRATO")): "---- ";
			}

			if(i == (vDataContratosAnt.size() - 1) && vDataContratosAnt.size() > 1){
				cObjetoOrig = !dinDataContratosAnt.getString("COBJETO").equalsIgnoreCase("null")?
						dinDataContratosAnt.getString("COBJETO"): "---- ";
						if(dinDataContratosAnt.getString("CNUMCONVOCATORIA") != null &&
								!dinDataContratosAnt.getString("CNUMCONVOCATORIA").equalsIgnoreCase("null") &&
								!dinDataContratosAnt.getString("CNUMCONVOCATORIA").equalsIgnoreCase(""))
							cModoAdjudicOrig = " Por Concurso número: " + dinDataContratosAnt.getString("CNUMCONVOCATORIA");
						else
							cModoAdjudicOrig = " Directa ";

						cCelebradoOrig = dinDataContratosAnt.getDate("DTCONTRATO") != null &&
						!dinDataContratosAnt.getDate("DTCONTRATO").equals("")?tFecha.getDateSPN(dinDataContratosAnt.getDate("DTCONTRATO")): "---- ";

						cVigenciaOrig = dinDataContratosAnt.getString("CVIGENCIA") + " (Años)";

			}
		}

		rep.iniciaReporte();
		rep.comRemplaza("[cAPI]", dinDataTitular.getString("CTITULAR"));
		rep.comRemplaza("[cPersona]", dinDataContratos.getString("CNOMBRE"));
		rep.comRemplaza("[cObjeto]", dinDataContratos.getString("COBJETO"));
		if(!dinDataContratos.getString("CNUMCONVOCATORIA").equalsIgnoreCase("")
				&& !dinDataContratos.getString("CNUMCONVOCATORIA").equalsIgnoreCase("null")){
			rep.comRemplaza("[cDir]", " ");
			rep.comRemplaza("[cCon]", "X");
		}else{
			rep.comRemplaza("[cDir]", "X");
			rep.comRemplaza("[cCon]", " ");
		}
		if(dinDataContratos.getInt("IPMDP") == 1){
			rep.comRemplaza("[cSI]", "X");
			rep.comRemplaza("[cNo]", " ");
		}else{
			rep.comRemplaza("[cSI]", " ");
			rep.comRemplaza("[cNo]", "X");
		}
		rep.comRemplaza("[cFechaCelebrado]", dinDataContratos.getDate("DTCONTRATO") != null &&
				!dinDataContratos.getDate("DTCONTRATO").equals("")? tFecha.getDateSPN(dinDataContratos.getDate("DTCONTRATO")):
		"---");
		rep.comRemplaza("[cFechaRecibido]", dinDataSolicitud.getDate("DTREGISTRO") != null &&
				!dinDataSolicitud.getDate("DTREGISTRO").equals("")? tFecha.getDateSPN(dinDataSolicitud.getDate("DTREGISTRO")):
		"---");
		rep.comRemplaza("[cVigencia]", dinDataContratos.getString("CVIGENCIA") + " Años");
		rep.comRemplaza("[cNumOriginales]", ""+dinDataContratos.getInt("INUMORIGINALES"));
		rep.comRemplaza("[cImporteFianza]", ""+dinDataContratos.getDouble("DFIANZA"));
		rep.comRemplaza("[cObjetoOrig]",cObjetoOrig);
		rep.comRemplaza("[cModoAdjudicacionOrig]",cModoAdjudicOrig);
		rep.comRemplaza("[cFechaContratoOrig]",cCelebradoOrig);
		rep.comRemplaza("[cVigenciaOrig]",cVigenciaOrig);
		rep.comRemplaza("[cObjetoPenUlt]",cObjetoPenUlt);
		rep.comRemplaza("[cFechaContratoPenUlt]",cCelebradoPenUlt);

		return rep.getVectorDatos(true);
	}

	public Vector generaModificacionCesionDerechos(String cFiltro){
		TWord rep = new TWord();
		TDCYSRRContrato dContratoAnt = new TDCYSRRContrato();
		Vector vDataContratos = new Vector(), vDataTitular = new Vector(), vDataContratosAnt = new Vector(),
		vDataUbicacion = new Vector(), vDataSolicitud = new Vector();
		TVDinRep dinDataContratos = new TVDinRep(), dinDataTitular = new TVDinRep(),
		dinDataContratosAnt = new TVDinRep(), dinDataUbicacion = new TVDinRep(), dinDataSolicitud = new TVDinRep();
		String[] aFiltro = cFiltro.split(",");
		String cObjetoPenUlt = "",cCelebradoPenUlt = "", cObjetoOrig = "", cModoAdjudicOrig = "", cCelebradoOrig = "",
		cVigenciaOrig = "", cVigenciaPenUlt = "", cSuperficieOrig = "",  cSeparador = "";
		int iCveContrato = Integer.parseInt(aFiltro[0],10);
		int iCveTitular = Integer.parseInt(aFiltro[1],10);

		vDataContratos = this.consultaContratos(iCveContrato);
		vDataTitular = this.consultaTitular(iCveTitular);
		vDataContratosAnt = dContratoAnt.regresaContratosAnt(iCveContrato);
		vDataSolicitud = this.consultaContratoSolicitud(iCveContrato);

		if(vDataContratos.size() > 0)
			dinDataContratos = (TVDinRep) vDataContratos.get(0);

		if(vDataTitular.size() > 0)
			dinDataTitular = (TVDinRep) vDataTitular.get(0);

		if(vDataSolicitud.size() > 0)
			dinDataSolicitud = (TVDinRep) vDataSolicitud.get(0);

		for(int i =0; i < vDataContratosAnt.size(); i++){
			dinDataContratosAnt = (TVDinRep) vDataContratosAnt.get(i);

			if(i == 1 && vDataContratosAnt.size() > 1){
				cObjetoPenUlt = !dinDataContratosAnt.getString("COBJETO").equalsIgnoreCase("null")?
						dinDataContratosAnt.getString("COBJETO"): "---- ";

				cCelebradoPenUlt = dinDataContratosAnt.getDate("DTCONTRATO") != null &&
				!dinDataContratosAnt.getDate("DTCONTRATO").equals("")?tFecha.getDateSPN(dinDataContratosAnt.getDate("DTCONTRATO")): "---- ";
				cVigenciaPenUlt = dinDataContratosAnt.getString("CVIGENCIA") + " (Años)";
			}

			if(i == (vDataContratosAnt.size() - 1) && vDataContratosAnt.size() > 1){
				cObjetoOrig = !dinDataContratosAnt.getString("COBJETO").equalsIgnoreCase("null")?
						dinDataContratosAnt.getString("COBJETO"): "---- ";


				vDataUbicacion = this.consultaSuperficieContrato(dinDataContratosAnt.getInt("ICVECONTRATO"));

				for(int j = 0; j < vDataUbicacion.size(); j++){
					dinDataUbicacion = (TVDinRep) vDataUbicacion.get(j);
					if(j>0 && !cSuperficieOrig.equalsIgnoreCase(""))
						cSeparador = ", ";

						cSuperficieOrig += !dinDataUbicacion.getString("CSUPERFICIE").equalsIgnoreCase("null")?
							cSeparador + dinDataUbicacion.getString("CSUPERFICIE") + " en el puerto de " + dinDataUbicacion.getString("CPTO"):"";
				}

				if(dinDataContratosAnt.getString("CNUMCONVOCATORIA") != null &&
						!dinDataContratosAnt.getString("CNUMCONVOCATORIA").equalsIgnoreCase("null") &&
						!dinDataContratosAnt.getString("CNUMCONVOCATORIA").equalsIgnoreCase(""))
					cModoAdjudicOrig = " Por Concurso número: " + dinDataContratosAnt.getString("CNUMCONVOCATORIA");
				else
					cModoAdjudicOrig = " Directa ";

				cCelebradoOrig = dinDataContratosAnt.getDate("DTCONTRATO") != null &&
				!dinDataContratosAnt.getDate("DTCONTRATO").equals("")?tFecha.getDateSPN(dinDataContratosAnt.getDate("DTCONTRATO")): "---- ";

				cVigenciaOrig = dinDataContratosAnt.getString("CVIGENCIA") + " (Años)";

			}
		}

		rep.iniciaReporte();
		rep.comRemplaza("[cAPI]", dinDataTitular.getString("CTITULAR"));
		rep.comRemplaza("[cPersona]", dinDataContratos.getString("CNOMBRE"));
		rep.comRemplaza("[cObjeto]", dinDataContratos.getString("COBJETO"));

		if(dinDataContratos.getInt("IPMDP") == 1){
			rep.comRemplaza("[cSI]", "X");
			rep.comRemplaza("[cNo]", " ");
		}else{
			rep.comRemplaza("[cSI]", " ");
			rep.comRemplaza("[cNo]", "X");
		}
		rep.comRemplaza("[cFechaCelebrado]", dinDataContratos.getDate("DTCONTRATO") != null &&
				!dinDataContratos.getDate("DTCONTRATO").equals("")? tFecha.getDateSPN(dinDataContratos.getDate("DTCONTRATO")):
		"---");
		rep.comRemplaza("[cFechaRecibido]", dinDataSolicitud.getDate("DTREGISTRO") != null &&
				!dinDataSolicitud.getDate("DTREGISTRO").equals("")? tFecha.getDateSPN(dinDataSolicitud.getDate("DTREGISTRO")):
		"---");
		rep.comRemplaza("[cVigencia]", dinDataContratos.getString("CVIGENCIA") + " Años");
		rep.comRemplaza("[cNumOriginales]", ""+dinDataContratos.getInt("INUMORIGINALES"));

		rep.comRemplaza("[cObjetoOrig]",cObjetoOrig);
		rep.comRemplaza("[cSuperficieOrig]",cSuperficieOrig);
		rep.comRemplaza("[cModoAdjudicacionOrig]",cModoAdjudicOrig);
		rep.comRemplaza("[cFechaContratoOrig]",cCelebradoOrig);
		rep.comRemplaza("[cVigenciaOrig]",cVigenciaOrig);
		rep.comRemplaza("[cObjetoPenUlt]",cObjetoPenUlt);
		rep.comRemplaza("[cFechaContratoPenUlt]",cCelebradoPenUlt);
		rep.comRemplaza("[cVigenciaPenUlt]",cVigenciaPenUlt);

		return rep.getVectorDatos(true);
	}
	public Vector consultaContratos(int iCveContrato){
		StringBuffer sb = new StringBuffer();
		Vector vRegresa = new Vector();
		sb.append("SELECT ");
		sb.append("PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO AS CNOMBRE, ");
		sb.append("CON.CUSOINSTALPORTUARIA AS COBJETO, ");
		sb.append("CON.LPREVISTOPMDP AS IPMDP, ");
		sb.append("CON.DTCONTRATO AS DTCONTRATO, ");
		sb.append("CON.DTREGISTRO AS DTREGISTRO, ");
		sb.append("(YEAR(CON.DTVENCIMIENTO) - YEAR(CON.DTINIVIGENCIA)) AS CVIGENCIA, ");
		sb.append("CON.INUMORIGINALES AS INUMORIGINALES, ");
		sb.append("CON.DIMPORTEFIANZA AS DFIANZA, ");
		sb.append("CON.CNUMCONVOCATORIA as CNUMCONVOCATORIA ");
		sb.append("FROM RGCCONTRATO CON ");
		sb.append("JOIN GRLPERSONA PER ON CON.ICVECESIONARIO = PER.ICVEPERSONA ");
		sb.append("WHERE CON.ICVECONTRATO =  " + iCveContrato);

		try {
			vRegresa = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vRegresa;
	}

	public Vector consultaTitular(int iCveTitular){
		StringBuffer sb = new StringBuffer();
		Vector vRegresa = new Vector();

		sb.append("SELECT ");
		sb.append("CNOMRAZONSOCIAL || ' ' || CAPPATERNO || ' ' ||  CAPMATERNO as CTITULAR ");
		sb.append("FROM GRLPERSONA ");
		sb.append("where ICVEPERSONA = " + iCveTitular);

		try {
			vRegresa = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vRegresa;
	}

	public Vector consultaSuperficieContrato(int iCveContrato){
		StringBuffer sb = new StringBuffer();
		Vector vRegresa = new Vector();

		sb.append("SELECT ");
		sb.append("CSUPERFICIE, ");
		sb.append("CSUPTERRENO, ");
		sb.append("CSUPAGUA, ");
		sb.append("CFRENTEAGUA, ");
		sb.append("CDSCPUERTO || ', ' || CABREVIATURA AS CPTO ");
		sb.append("FROM RGCCONTRATOUBICACION UBI ");
		sb.append("JOIN GRLPUERTO PTO ON UBI.ICVEPUERTO = PTO.ICVEPUERTO ");
		sb.append("JOIN GRLENTIDADFED EFED ON PTO.ICVEPAIS = EFED.ICVEPAIS ");
		sb.append("AND PTO.ICVEENTIDADFED = EFED.ICVEENTIDADFED ");
		sb.append("WHERE ICVECONTRATO = " + iCveContrato);

		try {
			vRegresa = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vRegresa;
	}

	public Vector consultaContratoSolicitud(int iCveContrato){
		StringBuffer sb = new StringBuffer();
		Vector vRegresa = new Vector();

		sb.append("SELECT ");
		sb.append("DATE(SOL.TSREGISTRO) AS DTREGISTRO, ");
		sb.append("PAGO.DIMPORTEPAGADO AS DIMPORTEPAGADO ");
		sb.append("FROM RGCSOLCONTRATO SOLCON ");
		sb.append("JOIN TRAREGSOLICITUD SOL ON SOLCON.IEJERCICIO = SOL.IEJERCICIO ");
		sb.append("AND SOLCON.INUMSOLICITUD = SOL.INUMSOLICITUD ");
		sb.append("LEFT JOIN TRAREGREFPAGO PAGO ON SOL.IEJERCICIO = PAGO.IEJERCICIO ");
		sb.append("AND SOL.INUMSOLICITUD = PAGO.INUMSOLICITUD ");
		sb.append("where SOLCON.ICVECONTRATO = " + iCveContrato);

		try {
			vRegresa = super.FindByGeneric("", sb.toString(), dataSourceName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vRegresa;
	}

        public Vector GenOpnAutTecObrasAPI(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

                TWord rep = new TWord();
                Vector vRegs = new Vector();
                Vector vRegs2 = new Vector();
                int iCveOficinaDest=0,iCveDeptoDest=0;
                String[] aFiltros = cQuery.split(",");
                int iCveTitulo = Integer.parseInt(aFiltros[4],10);

                TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
                dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cQuery);
                dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());
                iCveOficinaDest = dObtenDatosOpiniones.dDatosOficPer.getiCveOficina();
                iCveDeptoDest = dObtenDatosOpiniones.dDatosOficPer.getiCveDepto();

                try{
                        dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
                } catch (Exception e){
                        e.printStackTrace();
                        cMensaje = e.getMessage();
                }

                // Query de Consulta del Reporte.
                try{

                        vRegs = super.FindByGeneric("",
                                                    "select " +
                                                    "GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' ||GRLPersona.cApMaterno as cNombre, " +
                                                    "CPATitulo.cObjetoTitulo " +
                                                    "from CPATitulo " +
                                                    "join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo " +
                                                    "join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
                                                    "where CPATitulo.iCveTitulo = " + iCveTitulo
                                                    , dataSourceName);
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
                        rep.comRemplaza("[cConcesionario]",vDatos.getString("cNombre"));
                        rep.comRemplaza("[cObjetoAutorizacion]",vDatos.getString("cObjetoTitulo"));
                } else {
                        //Sin Valores en la Base.
                        rep.comRemplaza("[cConcesionario]","");
                        rep.comRemplaza("[cObjetoAutorizacion]","");
                }

                // Query de Consulta del Reporte.
                try{

                        vRegs2 = super.FindByGeneric("",
                                                     " SELECT CNUMOFICIOREFEXTERNA, CNUMOFPARTESREFEXTERNA " +
                                                     " FROM GRLFOLIOXSEGTOENT WHERE ICVESEGTOENTIDAD = " + dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad() +
                                                     " AND LFOLIOREFERENINTERNO = 1 AND LEXTERNO = 1 "
                                                     , dataSourceName);
                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                }

                String cReferencias = "";
                String cNumerosInternos = "";

                if (vRegs2.size() > 0){
                  TVDinRep vDatos2 = (TVDinRep) vRegs2.get(0);
                  cReferencias = vDatos2.getString("CNUMOFICIOREFEXTERNA");
                  cNumerosInternos = vDatos2.getString("CNUMOFPARTESREFEXTERNA");
                }

                // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
                return new TDGeneral().generaOficioWord(cNumFolio,
                                Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                                iCveOficinaDest,iCveDeptoDest,
                                0,0,0,
                                "","",
                                cReferencias,cNumerosInternos,
                                true,"cCuerpo",new Vector(),
                                true, new Vector(),
                                rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

        }




}


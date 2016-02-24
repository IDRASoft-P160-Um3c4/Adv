package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.excepciones.*;
import java.sql.SQLException;
import java.util.Vector;

/**
 * <p>Title: TDRPODoctos.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Angel Zamora Portugal
 * @author icaballero
 * @version 1.0
 */

public class TDRPODoctos extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	private TDOficio dOficio = new TDOficio();

	public TDRPODoctos(){
	}

	public StringBuffer GenOpiniones(String cQuery, String cFolio, String cCveOfic, String cCveDepto) throws Exception{ // Select
		Vector vRegs = new Vector();
		StringBuffer sbResBuscar = new StringBuffer("");
		StringBuffer sbResReemplazar = new StringBuffer("");

		TWord rep = new TWord();
		rep.iniciaReporte();

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

			int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());

			sbResBuscar.append("[cNumeroOficio]|");
			sbResReemplazar.append((cFolio.toString().compareTo("") != 0)?cFolio.toString():"").append("|");

			if (lEsOpinionInterna == 1) { //Es una Opinión Interna

			} else {

			}

		}else {
			System.out.print("No Encontro Datos");
		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getEtiquetas(true);

	}

	public StringBuffer GenOpinionDGAJZFMTAcuerdo(String cQuery, String cFolio, String cCveOfic, String cCveDepto) throws Exception{ // Select
		Vector vRegs = new Vector();
		Vector vRegsPtoTerminal = new Vector();
		String cDscRecintos = new String("");

		TWord rep = new TWord();
		rep.iniciaReporte();

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

			int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());
			int lEsContestacion = Integer.parseInt(vDatos.get("lEsContestacion").toString());

			int iEjercicio = Integer.parseInt(vDatos.get("iEjercicioSolicitud").toString());
			int iNumSolicitud = Integer.parseInt(vDatos.get("iNumSolicitud").toString());

			vRegsPtoTerminal = GetVectorPtos(iEjercicio, iNumSolicitud);
			cDscRecintos = GetDscRecintos(vRegsPtoTerminal);
			rep.comRemplaza("[cRecintos]",(cDscRecintos.compareTo("") != 0)?cDscRecintos:"");

			if (lEsOpinionInterna == 1) { //Es una Opinión Interna
				if (lEsContestacion == 0)
					;
			}else{
				if (lEsContestacion == 0)
					;
			}

		}else {
			System.out.print("No Encontro Datos");
		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getEtiquetas(true);

	}

	public StringBuffer GenOpinionDGAJDOF(String cQuery, String cFolio, String cCveOfic, String cCveDepto) throws Exception{ // Select
		Vector vRegs = new Vector();
		Vector vRegsPtoTerminal = new Vector();
		String cDscRecintos = new String("");

		TWord rep = new TWord();
		rep.iniciaReporte();

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

			int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());
			int lEsContestacion = Integer.parseInt(vDatos.get("lEsContestacion").toString());

			int iEjercicio = Integer.parseInt(vDatos.get("iEjercicioSolicitud").toString());
			int iNumSolicitud = Integer.parseInt(vDatos.get("iNumSolicitud").toString());

			vRegsPtoTerminal = GetVectorPtos(iEjercicio, iNumSolicitud);

			if (vRegsPtoTerminal.size()>0){
				for(int i=0;i<vRegsPtoTerminal.size();i++){
					TVDinRep vDatosPtoTerminal = (TVDinRep) vRegsPtoTerminal.get(i);

					if (Integer.parseInt(vDatosPtoTerminal.get("iCveTipoPuerto").toString()) == 1) //Tipo de Puerto = Puerto
						cDscRecintos = " al ";
					else
						cDscRecintos = " a la ";

					cDscRecintos = cDscRecintos + ((vDatosPtoTerminal.get("cDscTipoPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscTipoPuerto").toString():"") + " de " +
					((vDatosPtoTerminal.get("cDscPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscPuerto").toString():"") + ", ";

					cDscRecintos = cDscRecintos + " Municipio de " + ((vDatosPtoTerminal.get("GRLMunicipio.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLMunicipio.cNombre").toString():"") + ", ";
					cDscRecintos = cDscRecintos + " ubicado en el Estado de " + ((vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString():"") + ",";
				}
			}

			rep.comRemplaza("[cRecintos]",(cDscRecintos.compareTo("") != 0)?cDscRecintos:"");

			if (lEsOpinionInterna == 1) { //Es una Opinión Interna
				if (lEsContestacion == 0)
					;
			}else{
				if (lEsContestacion == 0)
					;
			}

		}else {
			System.out.print("No Encontro Datos");
		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getEtiquetas(true);

	}

	public StringBuffer GenOpinionSEMARNATAcuerdo(String cQuery, String cFolio, String cCveOfic, String cCveDepto) throws Exception{ // Select
		Vector vRegs = new Vector();
		Vector vRegsPtoTerminal = new Vector();
		String cDscRecintos = new String("");
		String cDscPlanos = new String("");

		TFechas dFechas = new TFechas();
		TWord rep = new TWord();
		rep.iniciaReporte();

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

			int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());
			int lEsContestacion = Integer.parseInt(vDatos.get("lEsContestacion").toString());

			int iEjercicio = Integer.parseInt(vDatos.get("iEjercicioSolicitud").toString());
			int iNumSolicitud = Integer.parseInt(vDatos.get("iNumSolicitud").toString());

			vRegsPtoTerminal = GetVectorPtos(iEjercicio, iNumSolicitud);

			if (vRegsPtoTerminal.size()>0){
				for(int i=0;i<vRegsPtoTerminal.size();i++){
					TVDinRep vDatosPtoTerminal = (TVDinRep) vRegsPtoTerminal.get(i);

					cDscRecintos = ((vDatosPtoTerminal.get("cDscPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscPuerto").toString():"") + ", ";
					cDscRecintos = cDscRecintos + ((vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString():"") + ",";

					cDscPlanos = "el plano oficial " + ((vDatosPtoTerminal.get("cPlanoOficial").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cPlanoOficial").toString():"");
					cDscPlanos = " de " + dFechas.getMonthName(vDatosPtoTerminal.getDate(((vDatosPtoTerminal.get("dtPlanoOficial").toString().compareTo("") != 0)?vDatosPtoTerminal.get("dtPlanoOficial").toString():""))) +
					" de " + dFechas.getIntYear(vDatosPtoTerminal.getDate(((vDatosPtoTerminal.get("dtPlanoOficial").toString().compareTo("") != 0)?vDatosPtoTerminal.get("dtPlanoOficial").toString():""))) + ", ";
					cDscPlanos = "denominado " + "'" + ((vDatosPtoTerminal.get("cDscRecintoPortuario").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscRecintoPortuario").toString():"") + "', ";

				}
			}

			rep.comRemplaza("[cRecintos]",(cDscRecintos.compareTo("") != 0)?cDscRecintos:"");
			rep.comRemplaza("[cPlanos]",(cDscPlanos.compareTo("") != 0)?cDscPlanos:"");

			if (lEsOpinionInterna == 1) { //Es una Opinión Interna
				if (lEsContestacion == 0)
					;
			}else{
				if (lEsContestacion == 0)
					;
			}

		}else {
			System.out.print("No Encontro Datos");
		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getEtiquetas(true);

	}

	public StringBuffer GenDoctoSimple(String cQuery) throws Exception{
		TWord rep = new TWord();
		rep.iniciaReporte();

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getEtiquetas(true);

	}

	public StringBuffer GenTarjeta(String cQuery) throws Exception{
		Vector vRegs = new Vector();
		Vector vRegsPtoTerminal = new Vector();
		String cDscRecintos = new String("");

		TWord rep = new TWord();
		rep.iniciaReporte();

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

			int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());
			int lEsContestacion = Integer.parseInt(vDatos.get("lEsContestacion").toString());

			int iEjercicio = Integer.parseInt(vDatos.get("iEjercicioSolicitud").toString());
			int iNumSolicitud = Integer.parseInt(vDatos.get("iNumSolicitud").toString());

			vRegsPtoTerminal = GetVectorPtos(iEjercicio, iNumSolicitud);

			if (vRegsPtoTerminal.size()>0){
				for(int i=0;i<vRegsPtoTerminal.size();i++){
					TVDinRep vDatosPtoTerminal = (TVDinRep) vRegsPtoTerminal.get(i);

					cDscRecintos = ((vDatosPtoTerminal.get("cDscTipoPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscTipoPuerto").toString():"") + " " +
					((vDatosPtoTerminal.get("cDscPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscPuerto").toString():"") + ", ";

					if (Integer.parseInt(vDatosPtoTerminal.get("iCveTipoPuerto").toString()) == 1) //Tipo de Puerto = Puerto
						cDscRecintos = cDscRecintos + "ubicado";
					else
						cDscRecintos = cDscRecintos + "ubicada";

					cDscRecintos = cDscRecintos + " en el Estado de " + ((vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString():"") + ", ";
				}
			}

			rep.comRemplaza("[cRecintos]",(cDscRecintos.compareTo("") != 0)?cDscRecintos:"");

			if (lEsOpinionInterna == 1) { //Es una Opinión Interna
				if (lEsContestacion == 0)
					;
			}else{
				if (lEsContestacion == 0)
					;
			}

		}else {
			System.out.print("No Encontro Datos");
		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getEtiquetas(true);

	}

	public StringBuffer GenAcuerdo(String cQuery, String cFolio, String cCveOfic, String cCveDepto) throws Exception{ // Select
		Vector vRegs = new Vector();
		Vector vRegsHabPtos = new Vector();
		Vector vRegsPtoTerminal = new Vector();

		String cDscRecintos = new String("");
		String cDscHabPtos = new String("");
		String cDscAcuerdo = new String("");

		TWord rep = new TWord();
		rep.iniciaReporte();

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

			int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());
			int lEsContestacion = Integer.parseInt(vDatos.get("lEsContestacion").toString());

			int iEjercicio = Integer.parseInt(vDatos.get("iEjercicioSolicitud").toString());
			int iNumSolicitud = Integer.parseInt(vDatos.get("iNumSolicitud").toString());
			int iCvePuerto = Integer.parseInt(vDatos.get("iCvePuerto").toString());

			vRegsPtoTerminal = GetVectorPtos(iEjercicio, iNumSolicitud);

			if (vRegsPtoTerminal.size()>0){
				for(int i=0;i<vRegsPtoTerminal.size();i++){
					TVDinRep vDatosPtoTerminal = (TVDinRep) vRegsPtoTerminal.get(i);

					if (Integer.parseInt(vDatosPtoTerminal.get("iCveTipoPuerto").toString()) == 1) //Tipo de Puerto = Puerto
						cDscRecintos = " el ";
					else
						cDscRecintos = " la ";

					cDscRecintos = cDscRecintos + ((vDatosPtoTerminal.get("cDscTipoPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscTipoPuerto").toString():"") + " de " +
					((vDatosPtoTerminal.get("cDscPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscPuerto").toString():"") + ", ";

					cDscAcuerdo = cDscAcuerdo +
					((vDatosPtoTerminal.get("cDscTipoPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscTipoPuerto").toString():"") + " de " +
					((vDatosPtoTerminal.get("cDscPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscPuerto").toString():"") + ", " +
					" Municipio de " + ((vDatosPtoTerminal.get("GRLMunicipio.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLMunicipio.cNombre").toString():"") + ", " +
					" en el Estado de " + ((vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString():"") + "," +
					" conforme al plano oficial que se identifica en el último considerando del presente Acuerdo, para quedar con una " +
					" superficie total de " + ((vDatosPtoTerminal.get("cSuperficieTotal").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cSuperficieTotal").toString():"") +
					" m2, integrada por " + ((vDatosPtoTerminal.get("cTerrenoFederal").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cTerrenoFederal").toString():"") +
					" m2 de terrenos de dominio público de la Federación y " + ((vDatosPtoTerminal.get("cMarTerritorial").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cMarTerritorial").toString():"") +
					" m2 de agua de mar territorial, ";

					try{
						vRegsHabPtos = super.FindByGeneric("",
								" select GRLMunicipio.cNombre, " +
								" GRLEntidadFed.cNombre, " +
								" VEHTipoNavegacion.cDsctipoNavega " +
								" from HTPPuertoTerminal " +
								" join HTPHabilitacion on HTPHabilitacion.iCveHabilitacion = HTPPuertoTerminal.iCveHabilitacion " +
								" and HTPHabilitacion.dtPublicacionDOF is not null " +
								" join GRLPuerto on GRLPuerto.iCvePuerto = HTPPuertoTerminal.iCvePuerto " +
								" where HTPPuertoTerminal.iCvePuerto = " + iCvePuerto,
								dataSourceName);

					}catch(SQLException ex){
						ex.printStackTrace();
						cMensaje = ex.getMessage();
					}catch(Exception ex2){
						ex2.printStackTrace();
						cMensaje += ex2.getMessage();
					}

					if (vRegsHabPtos.size()>0){
						TVDinRep vDatosHabPtos = (TVDinRep) vRegsHabPtos.get(i);

						cDscHabPtos = cDscHabPtos + " Municipio de " + ((vDatosPtoTerminal.get("GRLMunicipio.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLMunicipio.cNombre").toString():"") + ", ";
						cDscHabPtos = cDscHabPtos + " en el Estado de " + ((vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString().compareTo("") != 0)?vDatosPtoTerminal.get("GRLEntidadFed.cNombre").toString():"") + ",";
						cDscHabPtos = cDscHabPtos + " para navegación de " + ((vDatosHabPtos.get("cDsctipoNavega").toString().compareTo("") != 0)?vDatosHabPtos.get("cDsctipoNavega").toString():"");
					}
				}
			}

			rep.comRemplaza("[cHabilitacion]",(cDscHabPtos.compareTo("") != 0)?cDscHabPtos:"");
			rep.comRemplaza("[cRecintos]",(cDscRecintos.compareTo("") != 0)?cDscRecintos:"");
			rep.comRemplaza("[cDscAcuerdo]",(cDscAcuerdo.compareTo("") != 0)?cDscAcuerdo:"");

			if (lEsOpinionInterna == 1) { //Es una Opinión Interna
				if (lEsContestacion == 0)
					;
			}else{
				if (lEsContestacion == 0)
					;
			}

		}else {
			System.out.print("No Encontro Datos");
		}

		if(!cMensaje.equals(""))
			throw new Exception(cMensaje);
		return rep.getEtiquetas(true);

	}

	public Vector GetVectorPtos(int iEjercicio, int iNumSolicitud) throws Exception{
		Vector vRegsPtos = new Vector();
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("GRLTIPOPUERTO.ICVETIPOPUERTO as iCveTipoPuerto, ");
		sb.append("GRLTIPOPUERTO.CDSCTIPOPUERTO as cDscTipoPuerto, ");
		sb.append("GRLPUERTO.CDSCPUERTO as cDscPuerto, ");
		sb.append("GRLPUERTO.ICVEPUERTO as iCvePuerto, ");
		sb.append("GRLENTIDADFED.CNOMBRE as cEntidadFed, ");
		sb.append("GRLMUNICIPIO.CNOMBRE as cMunicipio, ");
		sb.append("RPORECINTOPUERTO.CDSCMOVIMIENTO as cDscMovimientoRP ");
		sb.append("FROM RPOSOLRECINTOPTO ");
		sb.append("JOIN RPORECINTOPUERTO ON RPORECINTOPUERTO.ICVEMOVRECINTOPORTUARIO = RPOSOLRECINTOPTO.ICVEMOVRECINTOPORTUARIO ");
		sb.append("JOIN GRLPUERTO ON GRLPUERTO.ICVEPUERTO = RPORECINTOPUERTO.ICVEPUERTO ");
		sb.append("JOIN GRLTIPOPUERTO ON GRLTIPOPUERTO.ICVETIPOPUERTO = GRLPUERTO.ICVETIPOPUERTO ");
		sb.append("JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEPAIS = GRLPUERTO.ICVEPAIS ");
		sb.append("AND GRLENTIDADFED.ICVEENTIDADFED = GRLPUERTO.ICVEENTIDADFED ");
		sb.append("JOIN GRLMUNICIPIO ON GRLMUNICIPIO.ICVEPAIS = GRLPUERTO.ICVEPAIS ");
		sb.append("AND GRLMUNICIPIO.ICVEENTIDADFED = GRLPUERTO.ICVEENTIDADFED ");
		sb.append("AND GRLMUNICIPIO.ICVEMUNICIPIO = GRLPUERTO.ICVEMUNICIPIO ");
		sb.append("WHERE RPOSOLRECINTOPTO.IEJERCICIO =  " + iEjercicio);
		sb.append(" AND RPOSOLRECINTOPTO.INUMSOLICITUD = " + iNumSolicitud);

		try{
			vRegsPtos = super.FindByGeneric("",sb.toString(),dataSourceName);

		}catch(SQLException ex){
			ex.printStackTrace();
			cMensaje = ex.getMessage();
		}catch(Exception ex2){
			ex2.printStackTrace();
			cMensaje += ex2.getMessage();
		}

		return vRegsPtos;
	}

	public String GetDscRecintos(Vector vRegsPtoTerminal){
		String cDscRecintos = new String("");
		debug("\n\n: Entra hasta aca bien");
		if (vRegsPtoTerminal.size()>0){
			for(int i=0;i<vRegsPtoTerminal.size();i++){
				TVDinRep vDatosPtoTerminal = (TVDinRep) vRegsPtoTerminal.get(i);

				if (Integer.parseInt(vDatosPtoTerminal.get("iCveTipoPuerto").toString()) == 1) //Tipo de Puerto = Puerto
					cDscRecintos = " del ";
				else
					cDscRecintos = " de la ";

				cDscRecintos = cDscRecintos + ((vDatosPtoTerminal.get("cDscTipoPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscTipoPuerto").toString():"") + " de " +
				((vDatosPtoTerminal.get("cDscPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscPuerto").toString():"") + ", ";

				cDscRecintos = cDscRecintos + " en el Municipio de " + ((vDatosPtoTerminal.get("cMunicipio").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cMunicipio").toString():"") + ", ";
				cDscRecintos = cDscRecintos + " Estado de " + ((vDatosPtoTerminal.get("cEntidadFed").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cEntidadFed").toString():"") + ",";
			}
		}

		return cDscRecintos;
	}

	public Vector generaOpinionIntObras(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
                Vector vRegsPlanos = new Vector();
                Vector vRegsFolios = new Vector();
                Vector vRegsUbicacion = new Vector();

                TFechas Fecha = new TFechas("44");
                TWord rep = new TWord();
                int iCveOficinaDest=0,iCveDeptoDest=0;

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

                try{
                        vRegsPlanos = super.FindByGeneric("",
                                        " SELECT PLNO.CPLANONUM,PLNO.DTPLANO,PLNO.CDENOMINACION,RPTO.CDSCMOVIMIENTO FROM RPOSOLRECINTOPTO RSPTO " +
                                        " JOIN RPORECINTOPUERTO RPTO ON RPTO.ICVEMOVRECINTOPORTUARIO = RSPTO.ICVEMOVRECINTOPORTUARIO AND RPTO.LRECINTOMOVPRINCIPAL = 1 " +
                                        " JOIN RPOPLANOXRECINTO PRPTO ON PRPTO.ICVEMOVRECINTOPORTUARIO = RPTO.ICVEMOVRECINTOPORTUARIO " +
                                        " JOIN OMPPLANO PLNO ON PLNO.ICVEPLANO = PRPTO.ICVEPLANO AND PLNO.LAPROBADOXOBRAS = 1 " +
                                        " WHERE RSPTO.IEJERCICIO = " + dObtenDatosOpiniones.dDatosOpnTra.getiEjercicio() +
                                        " AND RSPTO.INUMSOLICITUD = " + dObtenDatosOpiniones.dDatosOpnTra.getiNumSolicitud(),
                                       dataSourceName);

                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                        cMensaje += ex2.getMessage();
                }

                String cPlanos = "", cAcuerdo = "";
                for(int i=0;i<vRegsPlanos.size();i++){
                   TVDinRep vDatosPlanos = (TVDinRep) vRegsPlanos.get(i);

                   cPlanos = cPlanos + ((vDatosPlanos.get("CPLANONUM")!= null)?vDatosPlanos.get("CPLANONUM").toString():"") + " de " +
                             Fecha.getMonthName(vDatosPlanos.getDate("DTPLANO")) +  " de " + Fecha.getIntYear(vDatosPlanos.getDate("DTPLANO")) + ", denominado " +
                             ((vDatosPlanos.get("CDENOMINACION")!= null)?vDatosPlanos.get("CDENOMINACION").toString().toLowerCase():"") + ", ";

                   cAcuerdo = ((vDatosPlanos.get("CDSCMOVIMIENTO")!= null)?vDatosPlanos.get("CDSCMOVIMIENTO").toString().toLowerCase():"");
                }

                // Query de Consulta del Reporte.
                try{

                   vRegsFolios = super.FindByGeneric("",
                                                     " Select GRLFolioXSegtoEnt.lFolioReferenInterno,GRLFOLIO.IEJERCICIO,GRLFOLIO.CDIGITOSFOLIO, " +
                                                     " GRLFOLIO.ICONSECUTIVO,GRLFOLIO.CNUMOFICIALIAPARTES, " +
                                                     " GRLFolioXSegtoEnt.lExterno, GRLFolioXSegtoEnt.CNUMOFICIOREFEXTERNA,GRLFOLIOXSEGTOENT.CNUMOFPARTESREFEXTERNA " +
                                                     " from GRLFolioXSegtoEnt " +
                                                     " left join GRLFOLIO on GRLFOLIO.IEJERCICIO = GRLFOLIOXSEGTOENT.IEJERCICIOFOLIO " +
                                                     " and GRLFOLIo.ICVEOFICINA = GRLFOLIOXSEGTOENT.ICVEOFICINA " +
                                                     " and grlfolio.ICVEDEPARTAMENTO = GRLFOLIOXSEGTOENT.ICVEDEPARTAMENTO " +
                                                     " and GRLFOLIO.CDIGITOSFOLIO = GRLFOLIOXSEGTOENT.CDIGITOSFOLIO " +
                                                     " and grlfolio.ICONSECUTIVO = GRLFOLIOXSEGTOENT.ICONSECUTIVO " +
                                                     " WHERE ICVESEGTOENTIDAD = " + dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad() + " AND LFOLIOREFERENINTERNO = 1 "
                                                     , dataSourceName);
                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                }

                String cConsecutivoPaso = "";
                String cNumerosFolio = "";
                String cNumOficialia = "";
                for(int i=0;i<vRegsFolios.size();i++){
                   TVDinRep vDatosFolios = (TVDinRep) vRegsFolios.get(i);

                   if(vDatosFolios.getInt("lExterno")==0){
                     if(vDatosFolios.getInt("ICONSECUTIVO")<10){
                       cConsecutivoPaso = "00" + vDatosFolios.getString("ICONSECUTIVO");
                     }else if(vDatosFolios.getInt("ICONSECUTIVO")>=10 && vDatosFolios.getInt("ICONSECUTIVO")<100){
                       cConsecutivoPaso = "0" + vDatosFolios.getString("ICONSECUTIVO");
                     }else
                       cConsecutivoPaso = vDatosFolios.getString("ICONSECUTIVO");

                     cNumerosFolio = cNumerosFolio + vDatosFolios.getString("cDigitosFolio") + "." + cConsecutivoPaso + "/" + vDatosFolios.getString("IEJERCICIO");
                     cNumOficialia = cNumOficialia + vDatosFolios.getString("CNUMOFICIALIAPARTES");
                   }else{
                     cNumerosFolio = cNumerosFolio + vDatosFolios.getString("CNUMOFICIOREFEXTERNA");
                     cNumOficialia = cNumOficialia + vDatosFolios.getString("CNUMOFPARTESREFEXTERNA");
                   }
                }

                // Query de Consulta del Reporte.
                try{

                   vRegsUbicacion = super.FindByGeneric("",
                                                     " SELECT PEntFed.CNOMBRE AS cEntFedPTO, PMun.CNOMBRE AS cMunicipioPTO FROM RPOSOLRECINTOPTO SRPTO " +
                                                     " JOIN RPORECINTOPUERTO RPTO ON RPTO.ICVEMOVRECINTOPORTUARIO = SRPTO.ICVEMOVRECINTOPORTUARIO " +
                                                     " AND RPTO.LRECINTOMOVPRINCIPAL = 1 " +
                                                     " JOIN GRLPUERTO PTO ON PTO.ICVEPUERTO = RPTO.ICVEPUERTO " +
                                                     " left join GRLPais PPais on PPais.iCvePais = PTO.iCvePais " +
                                                     " left join GRLEntidadFed PEntFed on PEntFed.iCvePais = PTO.iCvePais " +
                                                     " and PEntFed.iCveEntidadFed = PTO.iCveEntidadFed " +
                                                     " left join GRLMunicipio PMun on PMun.iCvePais = PTO.iCvePais " +
                                                     " and PMun.iCveEntidadFed = PTO.iCveEntidadFed " +
                                                     " and PMun.iCveMunicipio = PTO.iCveMunicipio " +
                                                     " WHERE SRPTO.IEJERCICIO = " + dObtenDatosOpiniones.dDatosOpnTra.getiEjercicio() +
                                                     " AND SRPTO.INUMSOLICITUD = " + dObtenDatosOpiniones.dDatosOpnTra.getiNumSolicitud(),
                                                     dataSourceName);
                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                }

                // Elaboración del Reporte.
		rep.iniciaReporte();

                rep.comRemplaza("[cPlanos]",cPlanos.toString());
                rep.comRemplaza("[cAcuerdo]",cAcuerdo.toString());

                if(vRegsUbicacion.size()>0){
                  TVDinRep vDatosUbicacion = (TVDinRep) vRegsUbicacion.get(0);
                  rep.comRemplaza("[cMunicipioPTO]",vDatosUbicacion.getString("cMunicipioPTO").toLowerCase());
                  rep.comRemplaza("[cEntFedPTO]",vDatosUbicacion.getString("cEntFedPTO").toLowerCase());
                }else{
                  rep.comRemplaza("[cMunicipioPTO]","");
                  rep.comRemplaza("[cEntFedPTO]","");
                }

                if(vRegsPlanos.size()>1)
                  rep.comRemplaza("[cNoPlanos]"," a los planos anteriormente señalados");
                else
                  rep.comRemplaza("[cNoPlanos]"," al plano anteriormente señalado");

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				0,0,0,
				"","",
				cNumerosFolio, cNumOficialia,
				true,"cCuerpo",new Vector(),
				true, new Vector(),
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}
	public Vector generaOpinionExtDGAJ(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

		TWord rep = new TWord();
		Vector vRegsPtoTerminal = new Vector();

		TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
		dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cQuery);
		dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());

		String cDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
		String cPuestoDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();
		String cDscRecintos = "";

		try {
			vRegsPtoTerminal = GetVectorPtos(dObtenDatosOpiniones.dDatosOpnTra.getiEjercicio(), dObtenDatosOpiniones.dDatosOpnTra.getiNumSolicitud());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		cDscRecintos = GetDscRecintos(vRegsPtoTerminal);

		try{
			dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
		} catch (Exception e){
			e.printStackTrace();
			cMensaje = e.getMessage();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();
		rep.comRemplaza("[cNomDestino]", cDirigidoA);
		rep.comRemplaza("[cPtoDestino]", cPuestoDirigidoA);
		rep.comRemplaza("[cRecintos]",(cDscRecintos.compareTo("") != 0)?cDscRecintos:"");

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				0,0,0,
				"","",
				"", "",
				true,"cCuerpo",new Vector(),
				true, new Vector(),
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

	public Vector generaOpinionExtZFMT(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

		TWord rep = new TWord();
		Vector vRegsPtoTerminal = new Vector();
                Vector vRegsRecinto = new Vector();

		TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
		dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cQuery);
		dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());

		String cDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
		String cPuestoDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();
		String cDscRecintos = "";
		int iCveDomicilio = dObtenDatosOpiniones.dDatosOficPer.getiCveDomicilio();
		int iCvePersona =  dObtenDatosOpiniones.dDatosOficPer.getiCvePersona();

		try {
			vRegsPtoTerminal = GetVectorPtos(dObtenDatosOpiniones.dDatosOpnTra.getiEjercicio(), dObtenDatosOpiniones.dDatosOpnTra.getiNumSolicitud());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		cDscRecintos = GetDscRecintos(vRegsPtoTerminal);

		try{
			dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
		} catch (Exception e){
			e.printStackTrace();
			cMensaje = e.getMessage();
		}

                try{
                        vRegsRecinto = super.FindByGeneric("",
                                        " SELECT PEntFed.CNOMBRE AS cEntFedPTO, PMun.CNOMBRE AS cMunicipioPTO, RPTO.CDSCMOVIMIENTO " +
                                        " FROM RPOSOLRECINTOPTO RSPTO " +
                                        " JOIN RPORECINTOPUERTO RPTO ON RPTO.ICVEMOVRECINTOPORTUARIO = RSPTO.ICVEMOVRECINTOPORTUARIO AND RPTO.LRECINTOMOVPRINCIPAL = 1 " +
                                        " JOIN GRLPUERTO PTO ON PTO.ICVEPUERTO = RPTO.ICVEPUERTO " +
                                        " left join GRLPais PPais on PPais.iCvePais = PTO.iCvePais " +
                                        " left join GRLEntidadFed PEntFed on PEntFed.iCvePais = PTO.iCvePais " +
                                        " and PEntFed.iCveEntidadFed = PTO.iCveEntidadFed " +
                                        " left join GRLMunicipio PMun on PMun.iCvePais = PTO.iCvePais " +
                                        " and PMun.iCveEntidadFed = PTO.iCveEntidadFed " +
                                        " and PMun.iCveMunicipio = PTO.iCveMunicipio " +
                                        " WHERE RSPTO.IEJERCICIO = " + dObtenDatosOpiniones.dDatosOpnTra.getiEjercicio() +
                                        " AND RSPTO.INUMSOLICITUD = " + dObtenDatosOpiniones.dDatosOpnTra.getiNumSolicitud(),
                                       dataSourceName);

                }catch(SQLException ex){
                        ex.printStackTrace();
                        cMensaje = ex.getMessage();
                }catch(Exception ex2){
                        ex2.printStackTrace();
                        cMensaje += ex2.getMessage();
                }

                // Elaboración del Reporte.
                rep.iniciaReporte();

                String cAcuerdo = "";
                if(vRegsRecinto.size()>0){
                  TVDinRep vDatosRecinto = (TVDinRep) vRegsRecinto.get(0);
                  cAcuerdo = ((vDatosRecinto.get("CDSCMOVIMIENTO") != null)?vDatosRecinto.get("CDSCMOVIMIENTO").toString().toLowerCase():"");
                  rep.comRemplaza("[cMunicipioPTO]",vDatosRecinto.getString("cMunicipioPTO").toLowerCase());
                  rep.comRemplaza("[cEntFedPTO]",vDatosRecinto.getString("cEntFedPTO").toLowerCase());
                }else{
                  rep.comRemplaza("[cMunicipioPTO]","");
                  rep.comRemplaza("[cEntFedPTO]","");
                }

		rep.comRemplaza("[cNomDestino]", cDirigidoA);
		rep.comRemplaza("[cPtoDestino]", cPuestoDirigidoA);
		//rep.comRemplaza("[cRecintos]",(cDscRecintos.compareTo("") != 0)?cDscRecintos:"");
                rep.comRemplaza("[cAcuerdo]", cAcuerdo);

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				iCvePersona,iCveDomicilio,0,
				"","",
				"", "",
				true,"cCuerpo",new Vector(),
				true, new Vector(),
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

}

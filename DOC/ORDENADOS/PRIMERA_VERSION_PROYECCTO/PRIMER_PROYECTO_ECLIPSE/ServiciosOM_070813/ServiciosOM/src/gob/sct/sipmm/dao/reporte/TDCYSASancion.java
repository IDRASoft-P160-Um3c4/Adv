package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;

import javax.swing.text.StyleContext.SmallAttributeSet;

import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;

public class TDCYSASancion
extends DAOBase{
	private TParametro VParametros = new TParametro("44");
	private TFechas    tFecha      = new TFechas("44");
	private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
	private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
	private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");

	public TDCYSASancion(){
	}

	public StringBuffer inicioSancion(String cQuery,String cNumFolio,
			String cCveOficinaOrigen,String cCveDeptoOrigen){
		StringBuffer sbRetorno = new StringBuffer();
		String[] aFiltros = cQuery.split(",");
		int iCveTitulo = Integer.parseInt(aFiltros[0]);
		int iEjercicio = Integer.parseInt(aFiltros[1]);
		int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
		int iCveTitular = Integer.parseInt(aFiltros[3]);
		int iCveDomicilio = 0;
		int iCveRepLegal = 0;
		int iReng = 1;
		TExcel rep = new TExcel();
		TDObtenDatos dObten = new TDObtenDatos();
		TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
		TDObtenDatos dObten2 = new TDObtenDatos();
		TDObtenDatos dObtenFolio = new TDObtenDatos();
		rep.iniciaReporte();

		//		Encabezado
		rep.comDespliegaCombinado(cLeyendaEnc, "A", iReng, "N", iReng, rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR(), false, 0, false, false, 0, 0);
		iReng++;

		
		Vector vRutaRemitente = new Vector();
		vRutaRemitente = dObten.getOrganigrama(Integer.parseInt(cCveOficinaOrigen), Integer.parseInt(cCveDeptoOrigen), vRutaRemitente);
		for(int i=0; i<vRutaRemitente.size(); i++){
			rep.comDespliegaAlineado("I",iReng,"N",iReng,(String)vRutaRemitente.get(i),true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
			iReng++;
		}

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
		rep.comDespliegaAlineado("I",iReng,"N",iReng,cNumFolio,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng+=2;
		String cDatosEmision = cMunicipioEmision + "," + dObten2.dOficDepto.vDatoOfic.getAbrevEntidad() + ", a " + 
		tFecha.getFechaDDMMMMMYYYY(dObtenFolio.dFolio.getAsignacion()," de ");
		rep.comDespliegaAlineado("I",iReng,"N",iReng,cDatosEmision,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;

		rep.comDespliegaAlineado("I",iReng,"N",iReng,"Asunto: Inicio de Sanción",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
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
		cCalle = dObten3.dPersona.getCalle();
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

		rep.comDespliegaAlineado("B",iReng,"I",iReng,cNombrePersonaDest,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B",iReng,"I",iReng,cPuestoDest,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B",iReng,"I",iReng,cNombreEmpresaDest,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B",iReng,"I",iReng,cCalle + cNumExterior + cNumInterior,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B",iReng,"I",iReng,"Col. " + cColonia + " C.P. " + cCodigoPostal,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B",iReng,"I",iReng,cMunicipio + "," + cEntidad + "," + cPais,true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		iReng++;
		rep.comDespliegaAlineado("B",iReng,"I",iReng,"PRESENTE",true,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

		TVDinRep vDinRep = new TVDinRep();
		vDinRep.put("iEjercicio",iEjercicio);
		vDinRep.put("iMovProcedimiento",iMovProcedimiento);
		vDinRep.put("iEjercicioFol",dObtenFolio.dFolio.getCveEjercicio());
		vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
		vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
		vDinRep.put("cDigitosFolio",dObtenFolio.dFolio.getCveDigitosFolio());
		vDinRep.put("iConsecutivo",dObtenFolio.dFolio.getCveConsecutivo());
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
		
		sbRetorno = rep.getSbDatos();

		return sbRetorno;


	}

}

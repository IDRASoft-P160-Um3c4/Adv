package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;

import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDInconformidadesW.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Ing. Arturo López Peña
 * @version 1.0
 */

public class TDTitulos extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  public TDTitulos(){}
  public StringBuffer fTituloW(String cQuery){
       TWord rep = new TWord();
       rep.iniciaReporte();
       Vector vcCuerpo = new Vector();
       Vector vcCopiasPara = new Vector();
       TFechas tFecha = new TFechas();
       int IFOLIO;
       int iCveInconforme = 0, iCveDomicilio = 0;
       String CINCONFORMIDAD="",CFECHAREG="",CEXPEDIENTE= "",CSOL="",CACTORECLAMADO="",CAUTORIDADRESOLUTORIA="",
              CSOLICITASUSPENCION="",COBSINCONFORMIDAD="",CJUICIO="",CENTIDADFEDERAL="",CPAIS="",CMUNICIPIO="";
       String[] cParametros = cQuery.split(",");
       //System.out.print("*****  "+cQuery);
       java.sql.Date dtFechaApr=null,dtFechaVal=null;
       
       String cTitulos =   "SELECT " +
				    	   "	T.CNUMTITULO, " +
				    	   "	T.CDSCOBRA, " +
				    	   "	T.DTOTORGAMIENTO, " +
				    	   "	T.COBJETOTITULO, " +
				    	   "	T.DZONAAFECTADA, " +
				    	   "	T.DZONAOPEXC, " +
				    	   "	T.DZONANOEXC, " +
				    	   "	T.DZONATERRESTRE, " +
				    	   "	(T.DZONAAFECTADA+T.DZONAOPEXC+T.DZONANOEXC+T.DZONATERRESTRE) AS DSUMA, " +
				    	   "	T.CMONTOINVERSION, " +
				    	   "	TU.LDENTRORECINTOPORT, " +
				    	   "	TU.CCALLETITULO, " +
				    	   "	P1.CNOMBRE AS CPAISFR, " +
				    	   "	EF1.CNOMBRE AS CENTIDADFR, " +
				    	   "	M1.CNOMBRE AS CMUNICIPIOFR, " +
				    	   "	L1.CNOMBRE AS CLOCALIDADFR, " +
				    	   "	PU.CDSCPUERTO, " +
				    	   "	P2.CNOMBRE AS CPAISP, " +
				    	   "	EF2.CNOMBRE AS CENTIDADP, " +
				    	   "	M2.CNOMBRE AS CMUNICIPIOP, " +
				    	   "	L2.CNOMBRE AS CLOCALIDADP, " +
				    	   "	YEAR (date(t.DTVIGENCIATITULO)-date(t.DTINIVIGENCIATITULO))  as caniosvig, " +
				    	   "	MONTH(date(t.DTVIGENCIATITULO)-date(t.DTINIVIGENCIATITULO)) as cmesesvig, " +
				    	   "	T.LPROPIEDADNACIONAL, " +
				    	   "	O.CTIEMPOESTEJECUCION, " +
				    	   "	T.ICVETITULOANTERIOR, " +
				    	   "	P1.CNOMRAZONSOCIAL|| ' ' || P1.CAPPATERNO || ' ' || P1.CAPMATERNO AS CSOLICITANTE, " +
				    	   "	P2.CNOMRAZONSOCIAL|| ' ' || P2.CAPPATERNO || ' ' || P2.CAPMATERNO AS CREPLEGAL, " +
				    	   "	DP.CCALLE, " +
				    	   "	DP.CNUMEXTERIOR, " +
				    	   "	DP.CNUMINTERIOR, " +
				    	   "	DP.CCOLONIA, " +
				    	   "	DP.CCODPOSTAL, " +
				    	   "	PP.CNOMBRE AS CPAISSOL, " +
				    	   "	EFP.CNOMBRE AS CENTIDADSOL, " +
				    	   "	MP.CNOMBRE AS CMUNICIPIOSOL," +
				    	   "    S.IEJERCICIO," +
				    	   "    t.DTINIVIGENCIATITULO " +
				    	   "FROM CPATITULO T " +
				    	   "LEFT JOIN CPATITULOUBICACION TU ON TU.ICVETITULO=T.ICVETITULO " +
				    	   "LEFT JOIN GRLPAIS P1 ON P1.ICVEPAIS=TU.ICVEPAIS " +
				    	   "LEFT JOIN GRLENTIDADFED EF1 ON EF1.ICVEPAIS=TU.ICVEPAIS AND EF1.ICVEENTIDADFED=TU.ICVEENTIDADFED " +
				    	   "LEFT JOIN GRLMUNICIPIO M1 ON M1.ICVEPAIS=TU.ICVEPAIS AND M1.ICVEENTIDADFED=TU.ICVEENTIDADFED AND M1.ICVEMUNICIPIO=TU.ICVEMUNICIPIO " +
				    	   "LEFT JOIN GRLLOCALIDAD L1 ON L1.ICVEPAIS=TU.ICVEPAIS AND L1.ICVEENTIDADFED=TU.ICVEENTIDADFED AND L1.ICVEMUNICIPIO=TU.ICVEMUNICIPIO AND L1.ICVELOCALIDAD=TU.ICVELOCALIDAD " +
				    	   "LEFT JOIN GRLPUERTO PU ON PU.ICVEPUERTO=TU.ICVEPUERTO " +
				    	   "LEFT JOIN GRLPAIS P2 ON P2.ICVEPAIS=PU.ICVEPAIS " +
				    	   "LEFT JOIN GRLENTIDADFED EF2 ON EF2.ICVEPAIS=PU.ICVEPAIS AND EF2.ICVEENTIDADFED=PU.ICVEENTIDADFED " +
				    	   "LEFT JOIN GRLMUNICIPIO M2 ON M2.ICVEPAIS=PU.ICVEPAIS AND M2.ICVEENTIDADFED=PU.ICVEENTIDADFED AND M2.ICVEMUNICIPIO=PU.ICVEMUNICIPIO " +
				    	   "LEFT JOIN GRLLOCALIDAD L2 ON L2.ICVEPAIS=PU.ICVEPAIS AND L2.ICVEENTIDADFED=PU.ICVEENTIDADFED AND L2.ICVEMUNICIPIO=PU.ICVEMUNICIPIO AND L2.ICVELOCALIDAD=PU.ICVELOCALIDAD " +
				    	   "LEFT JOIN OMPPRODOBRASERV O ON O.ICVETITULO=T.ICVETITULO " +
				    	   "LEFT JOIN CPASOLTITULO ST ON ST.ICVETITULO=T.ICVETITULO " +
				    	   "LEFT JOIN TRAREGSOLICITUD S ON S.IEJERCICIO=ST.IEJERCICIO AND S.INUMSOLICITUD=ST.INUMSOLICITUD " +
				    	   "LEFT JOIN GRLPERSONA P1 ON P1.ICVEPERSONA=S.ICVESOLICITANTE " +
				    	   "LEFT JOIN GRLPERSONA P2 ON P2.ICVEPERSONA=S.ICVEREPLEGAL " +
				    	   "LEFT JOIN GRLDOMICILIO DP ON DP.ICVEPERSONA=S.ICVESOLICITANTE AND DP.ICVEDOMICILIO = S.ICVEDOMICILIOSOLICITANTE " +
				    	   "LEFT JOIN GRLPAIS PP ON PP.ICVEPAIS=dp.ICVEPAIS " +
				    	   "left join GRLENTIDADFED EFP ON EFP.ICVEPAIS=DP.ICVEPAIS AND EFP.ICVEENTIDADFED=DP.ICVEENTIDADFED " +
				    	   "LEFT JOIN GRLMUNICIPIO MP ON MP.ICVEPAIS=DP.ICVEPAIS AND MP.ICVEENTIDADFED=DP.ICVEENTIDADFED AND MP.ICVEMUNICIPIO=DP.ICVEMUNICIPIO " +
				    	   "WHERE T.ICVETITULO = ";
       
       try{
    	   Vector vcTitulo = this.FindByGeneric("",cTitulos+cParametros[0],dataSourceName); 
		
		
		   rep.iniciaReporte();
		   if(vcTitulo.size()>0){
			   TVDinRep vTitulo = (TVDinRep)vcTitulo.get(0);
			   
			   int iMeses = vTitulo.getInt("caniosvig")*12 + vTitulo.getInt("cmesesvig");
			   
			   String cVigencia  = vTitulo.getInt("caniosvig")>0?vTitulo.getInt("caniosvig")+" Años":"";
			   if(!cVigencia.equals("") && vTitulo.getInt("cmesesvig")>0)cVigencia += ", ";
			          cVigencia += vTitulo.getInt("cmesesvig")>0?vTitulo.getInt("cmesesvig")+" Meses ":"";
			   rep.comRemplaza("[cVigenciaTitulo]", cVigencia);
			   
			   if(!vTitulo.getString("CNUMTITULO").equals("") && !vTitulo.getString("CNUMTITULO").equals("null")) rep.comRemplaza("[cNoTitulo]",vTitulo.getString("CNUMTITULO")); else rep.comRemplaza("[cNoTitulo]","_______________________________");
			   if(!vTitulo.getString("DTOTORGAMIENTO").equals("") && !vTitulo.getString("DTOTORGAMIENTO").equals("null")) rep.comRemplaza("[cFechaEmisionTitulo]",vTitulo.getString("DTOTORGAMIENTO")); else rep.comRemplaza("[cFechaEmisionTitulo]","_______________");
			   if(!vTitulo.getString("COBJETOTITULO").equals("") && !vTitulo.getString("COBJETOTITULO").equals("null")) rep.comRemplaza("[cObjetoTitulo]",vTitulo.getString("COBJETOTITULO")); else rep.comRemplaza("[cObjetoTitulo]","_______________________________");
			   if(!vTitulo.getString("CTIEMPOESTEJECUCION").equals("") && !vTitulo.getString("CTIEMPOESTEJECUCION").equals("null")) rep.comRemplaza("[cTiempoEstimadoEjecucionObra]",vTitulo.getString("CTIEMPOESTEJECUCION")); else rep.comRemplaza("[cTiempoEstimadoEjecucionObra]","_______________________________");
			   rep.comRemplaza("[cVigenciaTituloMeses]",iMeses+"");
			   if(!vTitulo.getString("DSUMA").equals("") && !vTitulo.getString("DSUMA").equals("null")) rep.comRemplaza("[iTotalTitulo]",vTitulo.getString("DSUMA")); else rep.comRemplaza("[iTotalTitulo]","_______________________________");
			   if(!vTitulo.getString("CDSCOBRA").equals("") && !vTitulo.getString("CDSCOBRA").equals("null")) rep.comRemplaza("[cDescripcionTitulo]",vTitulo.getString("CDSCOBRA")); else rep.comRemplaza("[cDescripcionTitulo]","_______________________________");
			   rep.comRemplaza("[cVigenciaTituloAnios]",vTitulo.getInt("caniosvig")+"");
			   if(vTitulo.getInt("LPROPIEDADNACIONAL") == 1) rep.comRemplaza("[cIndicadorPropiedadNacional]","Propiedad Nacional, "); else rep.comRemplaza("[cIndicadorPropiedadNacional]","");
			   if(!vTitulo.getString("CMONTOINVERSION").equals("") && !vTitulo.getString("CMONTOINVERSION").equals("null")) rep.comRemplaza("[iInversionTitulo]",vTitulo.getString("CMONTOINVERSION")); else rep.comRemplaza("[iInversionTitulo]","_______________________________");
			   if(!vTitulo.getString("DZONAOPEXC").equals("") && !vTitulo.getString("DZONAOPEXC").equals("null")) rep.comRemplaza("[iZonaOperacionalExclusiva]",vTitulo.getString("DZONAOPEXC")); else rep.comRemplaza("[iZonaOperacionalExclusiva]","_______________________________");
			   if(!vTitulo.getString("DZONAAFECTADA").equals("") && !vTitulo.getString("DZONAAFECTADA").equals("null")) rep.comRemplaza("[iZonaAfectada]",vTitulo.getString("DZONAAFECTADA")); else rep.comRemplaza("[iZonaAfectada]","_______________________________");
			   if(!vTitulo.getString("DTINIVIGENCIATITULO").equals("") && !vTitulo.getString("DTINIVIGENCIATITULO").equals("null")) rep.comRemplaza("[cFechaInicioVigencia]",vTitulo.getString("DTINIVIGENCIATITULO")); else rep.comRemplaza("[cFechaInicioVigencia]","_______________________________");
			   
		   
			   if(vTitulo.getInt("IEJERCICIO")>0){
				   String cDireccion = vTitulo.getString("CCALLE");
				   if(!vTitulo.getString("CNUMEXTERIOR").equals("")) cDireccion += " #"+vTitulo.getInt("CNUMEXTERIOR");
				   if(!vTitulo.getString("CNUMINTERIOR").equals("")) cDireccion += " Int. "+vTitulo.getInt("CNUMINTERIOR");
				   if(!vTitulo.getString("CCOLONIA").equals("")) cDireccion += ", Col. "+vTitulo.getInt("CCOLONIA");
				   if(!vTitulo.getString("CCODPOSTAL").equals("")) cDireccion += ", C.P. "+vTitulo.getInt("CCODPOSTAL");
				   if(!vTitulo.getString("CMUNICIPIOSOL").equals("") && !vTitulo.getString("CMUNICIPIOSOL").equals("null")) cDireccion += ", "+vTitulo.getString("CMUNICIPIOSOL");
				   if(!vTitulo.getString("CENTIDADSOL").equals("") && !vTitulo.getString("CENTIDADSOL").equals("null")) cDireccion += ", "+vTitulo.getString("CENTIDADSOL");
				   
				   if(!vTitulo.getString("CCALLE").equals("") && !vTitulo.getString("CCALLE").equals("null")) rep.comRemplaza("[cCalleSol]",vTitulo.getString("CCALLE")); else rep.comRemplaza("[cCalleSol]","_______________________________");
				   if(!vTitulo.getString("CCOLONIA").equals("") && !vTitulo.getString("CCOLONIA").equals("null")) rep.comRemplaza("[cColoniaSol]",vTitulo.getString("CCOLONIA")); else rep.comRemplaza("[cColoniaSol]","_______________________________");
				   if(!vTitulo.getString("CCODPOSTAL").equals("") && !vTitulo.getString("CCODPOSTAL").equals("null")) rep.comRemplaza("[cCPSol]",vTitulo.getString("CCODPOSTAL")); else rep.comRemplaza("[cCPSol]","_______________________________");
				   if(!vTitulo.getString("CMUNICIPIOSOL").equals("") && !vTitulo.getString("CMUNICIPIOSOL").equals("null")) rep.comRemplaza("[cMunicipioSol]",vTitulo.getString("CMUNICIPIOSOL")); else rep.comRemplaza("[cMunicipioSol]","_______________________________");
				   if(!vTitulo.getString("CENTIDADSOL").equals("") && !vTitulo.getString("CENTIDADSOL").equals("null")) rep.comRemplaza("[cEntidadSol]",vTitulo.getString("CENTIDADSOL")); else rep.comRemplaza("[cEntidadSol]","_______________________________");
				   
				   if(!vTitulo.getString("CSOLICITANTE").equals("") && !vTitulo.getString("CSOLICITANTE").equals("null")) rep.comRemplaza("[cSolicitante]",vTitulo.getString("CSOLICITANTE")); else rep.comRemplaza("[cSolicitante]","_______________________________");
				   if(!vTitulo.getString("CREPLEGAL").equals("") && !vTitulo.getString("CREPLEGAL").equals("null")) rep.comRemplaza("[cRepresentanteLegal]",vTitulo.getString("CREPLEGAL")); else rep.comRemplaza("[cRepresentanteLegal]","_______________________________");
				   if(!cDireccion.equals("") && !cDireccion.equals("null")) rep.comRemplaza("[cDomicilioSolicitante]",cDireccion); else rep.comRemplaza("[cDomicilioSolicitante]","_______________________________");
				   if(vTitulo.getInt("IEJERCICIO")>0) rep.comRemplaza("[cAnioEjercicio]",vTitulo.getString("IEJERCICIO")); else rep.comRemplaza("[cAnioEjercicio]","______________");
			   }
			   else{
				   rep.comRemplaza("[cSolicitante]","_______________________________");
				   rep.comRemplaza("[cRepresentanteLegal]","_______________________________");
				   rep.comRemplaza("[cDomicilioSolicitante]","_______________________________");
				   rep.comRemplaza("[cAñoEjercicio]","___________");
			   }
			   
			   
			   if(vTitulo.getInt("LDENTRORECINTOPORT")==1){
				   if(!vTitulo.getString("CDSCPUERTO").equals("") && !vTitulo.getString("CDSCPUERTO").equals("null")) rep.comRemplaza("[cPuerto]",vTitulo.getString("CDSCPUERTO")); else rep.comRemplaza("[cPuerto]","_______________________________");
				   if(!vTitulo.getString("CMUNICIPIOP").equals("") && !vTitulo.getString("CMUNICIPIOP").equals("null")) rep.comRemplaza("[cMunicipio]",vTitulo.getString("CMUNICIPIOP")); else rep.comRemplaza("[cMunicipio]","_______________________________");
				   if(!vTitulo.getString("CENTIDADP").equals("") && !vTitulo.getString("CENTIDADP").equals("null")) rep.comRemplaza("[cEstado]",vTitulo.getString("CENTIDADP")); else rep.comRemplaza("[cEstado]","_______________________________");
				   if(!vTitulo.getString("CLOCALIDADP").equals("") && !vTitulo.getString("CLOCALIDADP").equals("null")) rep.comRemplaza("[cCiudad]",vTitulo.getString("CLOCALIDADP")); else rep.comRemplaza("[cCiudad]","_______________________________");
			   }
			   else{
				   rep.comRemplaza("[cPuerto]","");
				   if(!vTitulo.getString("CMUNICIPIOFR").equals("") && !vTitulo.getString("CMUNICIPIOFR").equals("null")) rep.comRemplaza("[cMunicipio]",vTitulo.getString("CMUNICIPIOFR")); else rep.comRemplaza("[cMunicipio]","_______________________________");
				   if(!vTitulo.getString("CENTIDADFR").equals("") && !vTitulo.getString("CENTIDADFR").equals("null")) rep.comRemplaza("[cEstado]",vTitulo.getString("CENTIDADFR")); else rep.comRemplaza("[cEstado]","_______________________________");  
				   if(!vTitulo.getString("CLOCALIDADFR").equals("") && !vTitulo.getString("CLOCALIDADFR").equals("null")) rep.comRemplaza("[cCiudad]",vTitulo.getString("CLOCALIDADFR")); else rep.comRemplaza("[cCiudad]","_______________________________");  	
			   }
			   if(vTitulo.getInt("ICVETITULOANTERIOR")>0){
		    	   Vector vcTituloAnte = this.FindByGeneric("",cTitulos+vTitulo.getInt("ICVETITULOANTERIOR"),dataSourceName);
		    	   if(vcTituloAnte.size()>0){		
					   
					   
		    		   TVDinRep vTituloAnte = (TVDinRep)vcTituloAnte.get(0);
					   int iMeses2 = vTitulo.getInt("caniosvig")*12 + vTituloAnte.getInt("cmesesvig");

					   String cVigenciaA  = vTituloAnte.getInt("caniosvig")>0?vTituloAnte.getInt("caniosvig")+" Años":"";
					   if(!cVigencia.equals("") && vTituloAnte.getInt("cmesesvig")>0)cVigenciaA += ", ";
					          cVigenciaA += vTituloAnte.getInt("cmesesvig")>0?vTituloAnte.getInt("cmesesvig")+" Meses ":"";
					   rep.comRemplaza("[cVigenciaTituloAnterior]", cVigenciaA);
		    		   
					   if(!vTituloAnte.getString("CNUMTITULO").equals("") && !vTituloAnte.getString("CNUMTITULO").equals("null")) rep.comRemplaza("[cNoTituloAnterior]",vTituloAnte.getString("CNUMTITULO")); else rep.comRemplaza("[cNoTituloAnterior]","_______________________________");
					   if(!vTituloAnte.getString("DTOTORGAMIENTO").equals("") && !vTituloAnte.getString("DTOTORGAMIENTO").equals("null")) rep.comRemplaza("[cFechaEmisionTituloAnterior]",vTituloAnte.getString("DTOTORGAMIENTO")); else rep.comRemplaza("[cFechaEmisionTituloAnterior]","_______________________________");
					   if(!vTituloAnte.getString("COBJETOTITULO").equals("") && !vTituloAnte.getString("COBJETOTITULO").equals("null")) rep.comRemplaza("[cObjetoTituloAnterior]",vTituloAnte.getString("COBJETOTITULO")); else rep.comRemplaza("[cObjetoTituloAnterior]","_______________________________");
					   if(!vTituloAnte.getString("CTIEMPOESTEJECUCION").equals("") && !vTituloAnte.getString("CTIEMPOESTEJECUCION").equals("null")) rep.comRemplaza("[cTiempoEstimadoEjecucionObraAnterior]",vTituloAnte.getString("CTIEMPOESTEJECUCION")); else rep.comRemplaza("[cTiempoEstimadoEjecucionObraAnterior]","_______________________________");
					   //if(!vTituloAnte.getString("cmesesvig").equals("") && !vTituloAnte.getString("cmesesvig").equals("null")) rep.comRemplaza("[cVigenciaTituloMesesAnterior]",vTituloAnte.getString("cmesesvig")); else rep.comRemplaza("[cVigenciaTituloMesesAnterior]","_______________________________");
					   rep.comRemplaza("[cVigenciaTituloMesesAnterior]",iMeses+"");
					   if(!vTituloAnte.getString("DSUMA").equals("") && !vTituloAnte.getString("DSUMA").equals("null")) rep.comRemplaza("[iTotalTituloAnterior]",vTituloAnte.getString("DSUMA")); else rep.comRemplaza("[iTotalTituloAnterior]","_______________________________");
					   if(!vTituloAnte.getString("CDSCOBRA").equals("") && !vTituloAnte.getString("CDSCOBRA").equals("null")) rep.comRemplaza("[cDescripcionTituloAnterior]",vTituloAnte.getString("CDSCOBRA")); else rep.comRemplaza("[cDescripcionTituloAnterior]","_______________________________");
					   if(!vTituloAnte.getString("caniosvig").equals("") && !vTituloAnte.getString("caniosvig").equals("null")) rep.comRemplaza("[cVigenciaTituloAniosAnterior]",vTituloAnte.getString("caniosvig")); else rep.comRemplaza("[cVigenciaTituloAniosAnterior]","_______________________________");
					   if(vTituloAnte.getInt("LPROPIEDADNACIONAL") == 1) rep.comRemplaza("[cIndicadorPropiedadNacionalAnterior]","Propiedad Nacional,"); else rep.comRemplaza("[cIndicadorPropiedadNacionalAnterior]","");
					   if(!vTituloAnte.getString("CMONTOINVERSION").equals("") && !vTituloAnte.getString("CMONTOINVERSION").equals("null")) rep.comRemplaza("[iInversionTituloAnterior]",vTituloAnte.getString("CMONTOINVERSION")); else rep.comRemplaza("[iInversionTituloAnterior]","_______________________________");
					   if(!vTituloAnte.getString("DZONAOPEXC").equals("") && !vTituloAnte.getString("DZONAOPEXC").equals("null")) rep.comRemplaza("[iZonaOperacionalExclusivaAnterior]",vTituloAnte.getString("DZONAOPEXC")); else rep.comRemplaza("[iZonaOperacionalExclusivaAnterior]","_______________________________");
					   if(!vTituloAnte.getString("DZONAAFECTADA").equals("") && !vTituloAnte.getString("DZONAAFECTADA").equals("null")) rep.comRemplaza("[iZonaAfectadaAnterior]",vTituloAnte.getString("DZONAAFECTADA")); else rep.comRemplaza("[iZonaAfectadaAnterior]","_______________________________");
					   
					   if(vTituloAnte.getInt("LDENTRORECINTOPORT")==1){
						   if(!vTituloAnte.getString("CDSCPUERTO").equals("") && !vTituloAnte.getString("CDSCPUERTO").equals("null")) rep.comRemplaza("[cPuertoAnterior]",vTituloAnte.getString("CDSCPUERTO")); else rep.comRemplaza("[cPuertoAnterior]","_______________________________");
						   if(!vTituloAnte.getString("CMUNICIPIOP").equals("") && !vTituloAnte.getString("CMUNICIPIOP").equals("null")) rep.comRemplaza("[cMunicipioTituloAnterior]",vTituloAnte.getString("CMUNICIPIOP")); else rep.comRemplaza("[cMunicipioTituloAnterior]","_______________________________");
						   if(!vTituloAnte.getString("CENTIDADP").equals("") && !vTituloAnte.getString("CENTIDADP").equals("null")) rep.comRemplaza("[cEstadoTituloAnterior]",vTituloAnte.getString("CENTIDADP")); else rep.comRemplaza("[cEstadoTituloAnterior]","_______________________________");
						   if(!vTituloAnte.getString("CLOCALIDADP").equals("") && !vTituloAnte.getString("CLOCALIDADP").equals("null")) rep.comRemplaza("[cCiudadAnterior]",vTituloAnte.getString("CLOCALIDADP")); else rep.comRemplaza("[cCiudadAnterior]","_______________________________");
					   }
					   else{
						   if(!vTituloAnte.getString("CDSCPUERTO").equals("") && !vTituloAnte.getString("CDSCPUERTO").equals("null")) rep.comRemplaza("[cPuertoAnterior]",vTituloAnte.getString("CDSCPUERTO")); else rep.comRemplaza("[cPuertoAnterior]","_______________________________");
						   if(!vTituloAnte.getString("CMUNICIPIOFR").equals("") && !vTituloAnte.getString("CMUNICIPIOFR").equals("null")) rep.comRemplaza("[cMunicipioTituloAnterior]",vTituloAnte.getString("CMUNICIPIOFR")); else rep.comRemplaza("[cMunicipioTituloAnterior]","_______________________________");
						   if(!vTituloAnte.getString("CENTIDADFR").equals("") && !vTituloAnte.getString("CENTIDADFR").equals("null")) rep.comRemplaza("[cEstadoTituloAnterior]",vTituloAnte.getString("CENTIDADFR")); else rep.comRemplaza("[cEstadoTituloAnterior]","_______________________________");  
						   if(!vTituloAnte.getString("CLOCALIDADFR").equals("") && !vTituloAnte.getString("CLOCALIDADFR").equals("null")) rep.comRemplaza("[cCiudadAnterior]",vTituloAnte.getString("CLOCALIDADFR")); else rep.comRemplaza("[cCiudadAnterior]","_______________________________");  	
					   }		    		   
		    	   }	
				   if(vTitulo.getInt("IEJERCICIO")>0){
					   String cDireccion = vTitulo.getString("CCALLE");
					   if(!vTitulo.getString("CNUMEXTERIOR").equals("")) cDireccion += " #"+vTitulo.getInt("CNUMEXTERIOR");
					   if(!vTitulo.getString("CNUMINTERIOR").equals("")) cDireccion += " Int. "+vTitulo.getInt("CNUMINTERIOR");
					   if(!vTitulo.getString("CCOLONIA").equals("")) cDireccion += ", Col. "+vTitulo.getInt("CCOLONIA");
					   if(!vTitulo.getString("CCODPOSTAL").equals("")) cDireccion += ", C.P. "+vTitulo.getInt("CCODPOSTAL");
					   if(!vTitulo.getString("CMUNICIPIOSOL").equals("") && !vTitulo.getString("CMUNICIPIOSOL").equals("null")) cDireccion += ", "+vTitulo.getString("CMUNICIPIOSOL");
					   if(!vTitulo.getString("CENTIDADSOL").equals("") && !vTitulo.getString("CENTIDADSOL").equals("null")) cDireccion += ", "+vTitulo.getString("CENTIDADSOL");
					   
					   if(!vTitulo.getString("CCALLE").equals("") && !vTitulo.getString("CCALLE").equals("null")) rep.comRemplaza("[cCalleSolAnte]",vTitulo.getString("CCALLE")); else rep.comRemplaza("[cCalleSolAnte]","_______________________________");
					   if(!vTitulo.getString("CCOLONIA").equals("") && !vTitulo.getString("CCOLONIA").equals("null")) rep.comRemplaza("[cColoniaSolAnte]",vTitulo.getString("CCOLONIA")); else rep.comRemplaza("[cColoniaSolAnte]","_______________________________");
					   if(!vTitulo.getString("CCODPOSTAL").equals("") && !vTitulo.getString("CCODPOSTAL").equals("null")) rep.comRemplaza("[cCPSolAnte]",vTitulo.getString("CCODPOSTAL")); else rep.comRemplaza("[cCPSolAnte]","_______________________________");
					   if(!vTitulo.getString("CMUNICIPIOSOL").equals("") && !vTitulo.getString("CMUNICIPIOSOL").equals("null")) rep.comRemplaza("[cMunicipioSolAnte]",vTitulo.getString("CMUNICIPIOSOL")); else rep.comRemplaza("[cMunicipioSolAnte]","_______________________________");
					   if(!vTitulo.getString("CENTIDADSOL").equals("") && !vTitulo.getString("CENTIDADSOL").equals("null")) rep.comRemplaza("[cEntidadSolAnte]",vTitulo.getString("CENTIDADSOL")); else rep.comRemplaza("[cEntidadSolAnte]","_______________________________");
					   
					   if(!vTitulo.getString("CSOLICITANTE").equals("") && !vTitulo.getString("CSOLICITANTE").equals("null")) rep.comRemplaza("[cSolicitanteAnte]",vTitulo.getString("CSOLICITANTE")); else rep.comRemplaza("[cSolicitanteAnte]","_______________________________");
					   if(!vTitulo.getString("CREPLEGAL").equals("") && !vTitulo.getString("CREPLEGAL").equals("null")) rep.comRemplaza("[cRepresentanteLegalAnte]",vTitulo.getString("CREPLEGAL")); else rep.comRemplaza("[cRepresentanteLegalAnte]","_______________________________");
					   if(!cDireccion.equals("") && !cDireccion.equals("null")) rep.comRemplaza("[cDomicilioSolicitanteAnte]",cDireccion); else rep.comRemplaza("[cDomicilioSolicitanteAnte]","_______________________________");
					   if(vTitulo.getInt("IEJERCICIO")>0) rep.comRemplaza("[cAñoEjercicioAnte]",vTitulo.getString("IEJERCICIO")); else rep.comRemplaza("[cAñoEjercicioAnte]","______________");
				   }
				   else{
					   rep.comRemplaza("[cSolicitanteAnte]","_______________________________");
					   rep.comRemplaza("[cRepresentanteLegalAnte]","_______________________________");
					   rep.comRemplaza("[cDomicilioSolicitanteAnte]","_______________________________");
					   rep.comRemplaza("[cAñoEjercicioAnte]","___________");
				   }
			   }
			   else{
				   rep.comRemplaza("[cNoTituloAnterior]","_______________________________");
				   rep.comRemplaza("[cFechaEmisionTituloAnterior]","_______________________________");
				   rep.comRemplaza("[cObjetoTituloAnterior]","_______________________________");
				   rep.comRemplaza("[cTiempoEstimadoEjecucionObraAnterior]","_______________________________");
				   rep.comRemplaza("[cVigenciaTituloMesesAnterior]","_______________________________");
				   rep.comRemplaza("[iTotalTituloAnterior]","_______________________________");
				   rep.comRemplaza("[cDescripcionTituloAnterior]","_______________________________");
				   rep.comRemplaza("[cVigenciaTituloAniosAnterior]","_______________________________");
				   rep.comRemplaza("[cIndicadorPropiedadNacionalAnterior]","");
				   rep.comRemplaza("[iInversionTituloAnterior]","_______________________________");
				   rep.comRemplaza("[iZonaOperacionalExclusivaAnterior]","_______________________________");
				   rep.comRemplaza("[iZonaAfectadaAnterior]","_______________________________");
				   rep.comRemplaza("[cPuertoAnterior]","_______________________________");
				   rep.comRemplaza("[cMunicipioTituloAnterior]","_______________________________");
				   rep.comRemplaza("[cEstadoTituloAnterior]","_______________________________");
				   rep.comRemplaza("[cCiudadAnterior]","_______________________________");
				   
			   }
		  }
       }catch(SQLException ex){
    	    cMensaje = ex.getMessage();
       }catch(Exception ex2){
         ex2.printStackTrace();
       }
       return rep.getEtiquetas(true);

 }
}

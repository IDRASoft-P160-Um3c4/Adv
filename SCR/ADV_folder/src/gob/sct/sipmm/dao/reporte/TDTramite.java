package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import java.sql.SQLException;
import gob.sct.sipmm.dao.*;

/**
 * <p>Title: TDTramite.java</p>
 * <p>Description: DAO con métodos para consultas a Tablas de trámites</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDTramite extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas("44");

  public TDTramite(){
  }

  public Vector getDatosGeneralesSolicitud(String cWhere, String cOrder, boolean lSolicRepLegal) throws Exception{
    Vector vGenerales = new Vector();
    try{  // Datos generales del trámite
      String cSQL = "SELECT "+
                    "   RS.iEjercicio, RS.iNumSolicitud, RS.iCveTramite, RS.iCveModalidad, RS.tsRegistro,"+
                    "   RS.dtLimiteEntregaDocs, RS.dtEstimadaEntrega as dtEstimadaEntrega, RS.cNomAutorizaRecoger, RS.iCveUsuRegistra,"+
                    "   RS.lPagado, RS.dtEntrega, RS.iCveUsuEntrega, RS.lResolucionPositiva, RS.lDatosPreregistro,"+
                    "   RS.iIdBien, RS.iCveOficina, RS.iCveDepartamento, RS.iEjercicioCita, RS.iIdCita, RS.iCveForma,"+
                    "   RS.lPrincipal, RS.lImpreso, RS.cEnviarResolucionA, RS.cObsTramite, RS.cDscBien,"+
                    "   CT.cCveInterna, CT.cDscBreve, CT.cDscTramite, CT.lReqFirmaDigital, M.cDscModalidad,"+
                    "   RS.iCveSolicitante, RS.iCveRepLegal, RS.iCveDomicilioSolicitante, RS.iCveDomicilioRepLegal,"+
                    "   FA.cDscForma, FA.cDocumentoMostrar,";
      if (lSolicRepLegal)
        cSQL +=     "   SOL.cNomRazonsocial || ' ' || SOL.cApPaterno || ' ' || SOL.cApMaterno AS cNomSolicitante, "+
                    "   SOL.cRFC AS cRFCSol, SOL.cCorreoE AS cCorreoESol,"+
                    "   REP.cNomRazonsocial || ' ' || REP.cApPaterno || ' ' || REP.cApMaterno AS cNomRepLegal, "+
                    "   REP.cRFC AS cRFCRep, REP.cCorreoE AS cCorreoERep,"+
                    "   DSOL.cCalle AS cCalleSol, DSOL.cNumExterior AS cNumExteriorSol, DSOL.cNumInterior AS cNumInteriorSol, DSOL.cColonia AS cColoniaSol, DSOL.cCodPostal AS cCodPostalSol, DSOL.cTelefono AS cTelefonoSol,"+
                    "   DREP.cCalle AS cCalleRep, DREP.cNumExterior AS cNumExteriorRep, DREP.cNumInterior AS cNumInteriorRep, DREP.cColonia AS cColoniaRep, DREP.cCodPostal AS cCodPostalRep, DREP.cTelefono AS cTelefonoRep,"+
                    "   PSOL.cNombre AS cNomPaisSol, PSOL.cAbreviatura AS cAbrevPaisSol,"+
                    "   EFSOL.cNombre AS cNomEntidadSol, EFSOL.cAbreviatura AS cAbrevEntidadSol, "+
                    "   MSOL.cNombre AS cNomMunicipioSol, MSOL.cAbreviatura AS cAbrevMunicipioSol,"+
                    "   LSOL.cNombre AS cNomLocalidadSol, LSOL.cAbreviatura AS cAbrevLocalidadSol,"+
                    "   PREP.cNombre AS cNomPaisRep, PREP.cAbreviatura AS cAbrevPaisRep,"+
                    "   EFREP.cNombre AS cNomEntidadRep, EFREP.cAbreviatura AS cAbrevEntidadRep, "+
                    "   MREP.cNombre AS cNomMunicipioRep, MREP.cAbreviatura AS cAbrevMunicipioRep,"+
                    "   LREP.cNombre AS cNomLocalidadRep, LREP.cAbreviatura AS cAbrevLocalidadRep,";
      cSQL+=        "   UReg.cNombre || ' ' || UReg.cApPaterno || ' ' || UReg.cApMaterno AS cNomUsuRegistra,"+
                    "   UEnt.cNombre || ' ' || UEnt.cApPaterno || ' ' || UEnt.cApMaterno AS cNomUsuEntrega,"+
                    "   DXO.cTitular AS cTitularDeptoOfic, OF.cDscOficina, OF.cDscBreve AS cDscBreveOfic, DEP.cDscDepartamento, DEP.cDscBreve AS cDscBreveDepto,"+
                    "   DC.dtCita, CAN.dtCancelacion, CAN.iCveMotivoCancela, CAN.cObs, ET.lAnexo"+
                    " FROM TRARegSolicitud RS"+
                    " JOIN TRACatTramite CT ON CT.iCveTramite = RS.iCveTramite"+
                    " JOIN TRAModalidad M ON M.iCveModalidad = RS.iCveModalidad"+
                    " JOIN GRLFormaAcreditar FA ON FA.iCveForma = RS.iCveForma";
      if (lSolicRepLegal)
        cSQL+=      " JOIN GRLPersona SOL ON SOL.iCvePersona = RS.iCveSolicitante"+
                    " JOIN GRLPersona REP ON REP.iCvePersona = RS.iCveRepLegal"+
                    " JOIN GRLDomicilio DSOL ON DSOL.iCvePersona = RS.iCveSolicitante AND DSOL.iCveDomicilio = RS.iCveDomicilioSolicitante"+
                    " JOIN GRLDomicilio DREP ON DREP.iCvePersona = RS.iCveRepLegal AND DREP.iCveDomicilio = RS.iCveDomicilioRepLegal"+
                    " JOIN GRLPais PSOL ON PSOL.iCvePais = DSOL.iCvePais"+
                    " JOIN GRLEntidadFed EFSOL ON EFSOL.iCvePais = DSOL.iCvePais AND EFSOL.iCveEntidadFed = DSOL.iCveEntidadFed"+
                    " JOIN GRLMunicipio MSOL ON MSOL.iCvePais = DSOL.iCvePais AND MSOL.iCveEntidadFed = DSOL.iCveEntidadFed AND MSOL.iCveMunicipio = DSOL.iCveMunicipio"+
                    " JOIN GRLLocalidad LSOL ON LSOL.iCvePais = DSOL.iCvePais AND LSOL.iCveEntidadFed = DSOL.iCveEntidadFed AND LSOL.iCveMunicipio = DSOL.iCveMunicipio AND LSOL.iCveLocalidad = DSOL.iCveLocalidad"+
                    " JOIN GRLPais PREP ON PREP.iCvePais = DREP.iCvePais"+
                    " JOIN GRLEntidadFed EFREP ON EFREP.iCvePais = DREP.iCvePais AND EFREP.iCveEntidadFed = DREP.iCveEntidadFed"+
                    " JOIN GRLMunicipio MREP ON MREP.iCvePais = DREP.iCvePais AND MREP.iCveEntidadFed = DREP.iCveEntidadFed AND MREP.iCveMunicipio = DREP.iCveMunicipio"+
                    " JOIN GRLLocalidad LREP ON LREP.iCvePais = DREP.iCvePais AND LREP.iCveEntidadFed = DREP.iCveEntidadFed AND LREP.iCveMunicipio = DREP.iCveMunicipio AND LREP.iCveLocalidad = DREP.iCveLocalidad";
      cSQL+=        " JOIN SEGUsuario UReg ON UReg.iCveUsuario = RS.iCveUsuRegistra"+
                    " LEFT JOIN SEGUsuario UEnt ON UEnt.iCveUsuario = RS.iCveUsuEntrega"+
                    " JOIN GRLDeptoXOfic DXO ON DXO.iCveOficina = RS.iCveOficina AND DXO.iCveDepartamento = RS.iCveDepartamento"+
                    " JOIN GRLOficina OF ON OF.iCveOficina = RS.iCveOficina"+
                    " JOIN GRLDepartamento DEP ON DEP.iCveDepartamento = RS.iCveDepartamento"+
                    " JOIN TRADatosCita DC ON DC.iEjercicio = RS.iEjercicioCita ANd DC.iIdCita = RS.iIdCita"+
                    " LEFT JOIN TRARegTramXSol CAN ON CAN.iEjercicio = RS.iEjercicio AND CAN.iNumSolicitud = RS.iNumSolicitud"+
                    " LEFT JOIN TRARegEtapasXModTram ET ON ET.iEjercicio = RS.iEjercicio AND ET.iNumSolicitud = RS.iNumSolicitud AND ET.iCveTramite = RS.iCveTramite AND ET.iCveModalidad = RS.iCveModalidad AND ET.iCveEtapa = " + VParametros.getPropEspecifica("EtapaConcluidoArea") +
                    " " + cWhere +
//                    " AND ET.iOrden = (SELECT MAX(iOrden) FROM TRARegEtapasXModTram ET2 WHERE ET2.iEjercicio = RS.iEjercicio AND ET2.iNumSolicitud = RS.iNumSolicitud AND ET2.iCveTramite = RS.iCveTramite AND ET2.iCveModalidad = RS.iCveModalidad AND ET2.iCveEtapa = " + VParametros.getPropEspecifica("EtapaEntregaResol") +")"+
                    " " + cOrder;
      vGenerales = super.FindByGeneric("",cSQL,dataSourceName);
    } catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return vGenerales;
  }

  
  public TVDinRep getDatosGenSolicitud(String cWhere, String cOrder, boolean lSolicRepLegal) throws Exception{
	    Vector vSolicitud = new Vector();
	    Vector vPersonas  = new Vector();
	    Vector vUsuarios  = new Vector();
	    Vector vGenerales = new Vector();
	    TVDinRep vGen = new TVDinRep();
	    try{  // Datos generales del trámite

	      String cSol = "SELECT " +
	                  "       RS.IEJERCICIO,RS.INUMSOLICITUD,RS.ICVETRAMITE,RS.ICVEMODALIDAD, " +
	                  "       RS.TSREGISTRO,RS.DTLIMITEENTREGADOCS,RS.DTESTIMADAENTREGA AS DTESTIMADAENTREGA, " +
	                  "       RS.CNOMAUTORIZARECOGER,RS.ICVEUSUREGISTRA,RS.LPAGADO,RS.DTENTREGA,RS.ICVEUSUENTREGA, " +
	                  "       RS.LRESOLUCIONPOSITIVA,RS.LDATOSPREREGISTRO,RS.IIDBIEN,RS.ICVEOFICINA,RS.ICVEDEPARTAMENTO, " +
	                  "       RS.IEJERCICIOCITA,RS.IIDCITA,RS.ICVEFORMA,RS.LPRINCIPAL,RS.LIMPRESO,RS.CENVIARRESOLUCIONA,RS.COBSTRAMITE, " +
	                  "       RS.CDSCBIEN,CT.CCVEINTERNA,CT.CDSCBREVE,CT.CDSCTRAMITE,CT.LREQFIRMADIGITAL,M.CDSCMODALIDAD,RS.ICVESOLICITANTE, " +
	                  "       RS.ICVEREPLEGAL,RS.ICVEDOMICILIOSOLICITANTE,RS.ICVEDOMICILIOREPLEGAL,FA.CDSCFORMA, " +
	                  "       FA.CDOCUMENTOMOSTRAR " +
	                  "FROM TRAREGSOLICITUD RS " +
	                  "  JOIN TRACATTRAMITE CT ON CT.ICVETRAMITE = RS.ICVETRAMITE " +
	                  "  JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD = RS.ICVEMODALIDAD " +
	                  "  LEFT JOIN GRLFORMAACREDITAR FA ON FA.ICVEFORMA = RS.ICVEFORMA " +
	                  "  LEFT JOIN TRAREGTRAMXSOL CAN ON CAN.IEJERCICIO = RS.IEJERCICIO AND CAN.INUMSOLICITUD = RS.INUMSOLICITUD " +
	                  "  LEFT JOIN TRAREGETAPASXMODTRAM ET ON ET.IEJERCICIO = RS.IEJERCICIO " +
	                  "    AND ET.INUMSOLICITUD = RS.INUMSOLICITUD AND ET.ICVETRAMITE = RS.ICVETRAMITE AND ET.ICVEMODALIDAD = RS.ICVEMODALIDAD " +
	                  "    AND ET.ICVEETAPA = "+ VParametros.getPropEspecifica("EtapaConcluidoArea") +
	                  " " + cWhere + " " + cOrder; 	
	      String cPer = "SELECT " +
	                  "       SOL.CNOMRAZONSOCIAL || ' ' || SOL.CAPPATERNO || ' ' || SOL.CAPMATERNO AS CNOMSOLICITANTE, " +
	                  "       SOL.CRFC AS CRFCSOL,SOL.CCORREOE AS CCORREOESOL, " +
	                  "       REP.CNOMRAZONSOCIAL || ' ' || REP.CAPPATERNO || ' ' || REP.CAPMATERNO AS CNOMREPLEGAL,REP.CRFC AS CRFCREP, " +
	                  "       REP.CCORREOE AS CCORREOEREP,DSOL.CCALLE AS CCALLESOL,DSOL.CNUMEXTERIOR AS CNUMEXTERIORSOL, " +
	                  "       DSOL.CNUMINTERIOR AS CNUMINTERIORSOL,DSOL.CCOLONIA AS CCOLONIASOL,DSOL.CCODPOSTAL AS CCODPOSTALSOL, " +
	                  "       DSOL.CTELEFONO AS CTELEFONOSOL,DREP.CCALLE AS CCALLEREP,DREP.CNUMEXTERIOR AS CNUMEXTERIORREP, " +
	                  "       DREP.CNUMINTERIOR AS CNUMINTERIORREP,DREP.CCOLONIA AS CCOLONIAREP,DREP.CCODPOSTAL AS CCODPOSTALREP, " +
	                  "       DREP.CTELEFONO AS CTELEFONOREP,PSOL.CNOMBRE AS CNOMPAISSOL,PSOL.CABREVIATURA AS CABREVPAISSOL, " +
	                  "       EFSOL.CNOMBRE AS CNOMENTIDADSOL,EFSOL.CABREVIATURA AS CABREVENTIDADSOL,MSOL.CNOMBRE AS CNOMMUNICIPIOSOL, " +
	                  "       MSOL.CABREVIATURA AS CABREVMUNICIPIOSOL,LSOL.CNOMBRE AS CNOMLOCALIDADSOL,LSOL.CABREVIATURA AS CABREVLOCALIDADSOL, " +
	                  "       PREP.CNOMBRE AS CNOMPAISREP,PREP.CABREVIATURA AS CABREVPAISREP,EFREP.CNOMBRE AS CNOMENTIDADREP, " +
	                  "       EFREP.CABREVIATURA AS CABREVENTIDADREP,MREP.CNOMBRE AS CNOMMUNICIPIOREP,MREP.CABREVIATURA AS CABREVMUNICIPIOREP, " +
	                  "       LREP.CNOMBRE AS CNOMLOCALIDADREP,LREP.CABREVIATURA AS CABREVLOCALIDADREP " +
	                  "FROM TRAREGSOLICITUD RS " +
	                  "  JOIN GRLPERSONA SOL ON SOL.ICVEPERSONA = RS.ICVESOLICITANTE " +
	                  "  left JOIN GRLPERSONA REP ON REP.ICVEPERSONA = RS.ICVEREPLEGAL " +
	                  "  JOIN GRLDOMICILIO DSOL ON DSOL.ICVEPERSONA = RS.ICVESOLICITANTE AND DSOL.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE " +
	                  "  left JOIN GRLDOMICILIO DREP ON DREP.ICVEPERSONA = RS.ICVEREPLEGAL AND DREP.ICVEDOMICILIO = RS.ICVEDOMICILIOREPLEGAL " +
	                  "  JOIN GRLPAIS PSOL ON PSOL.ICVEPAIS = DSOL.ICVEPAIS " +
	                  "  JOIN GRLENTIDADFED EFSOL ON EFSOL.ICVEPAIS = DSOL.ICVEPAIS AND EFSOL.ICVEENTIDADFED = DSOL.ICVEENTIDADFED " +
	                  "  JOIN GRLMUNICIPIO MSOL ON MSOL.ICVEPAIS = DSOL.ICVEPAIS AND MSOL.ICVEENTIDADFED = DSOL.ICVEENTIDADFED AND MSOL.ICVEMUNICIPIO = DSOL.ICVEMUNICIPIO " +
	                  "  LEFT JOIN GRLLOCALIDAD LSOL ON LSOL.ICVEPAIS = DSOL.ICVEPAIS AND LSOL.ICVEENTIDADFED = DSOL.ICVEENTIDADFED AND LSOL.ICVEMUNICIPIO = DSOL.ICVEMUNICIPIO " +
	                  "    AND LSOL.ICVELOCALIDAD = DSOL.ICVELOCALIDAD " +
	                  "  left JOIN GRLPAIS PREP ON PREP.ICVEPAIS = DREP.ICVEPAIS " +
	                  "  left JOIN GRLENTIDADFED EFREP ON EFREP.ICVEPAIS = DREP.ICVEPAIS AND EFREP.ICVEENTIDADFED = DREP.ICVEENTIDADFED " +
	                  "  left JOIN GRLMUNICIPIO MREP ON MREP.ICVEPAIS = DREP.ICVEPAIS AND MREP.ICVEENTIDADFED = DREP.ICVEENTIDADFED AND MREP.ICVEMUNICIPIO = DREP.ICVEMUNICIPIO " +
	                  "  left JOIN GRLLOCALIDAD LREP ON LREP.ICVEPAIS = DREP.ICVEPAIS AND LREP.ICVEENTIDADFED = DREP.ICVEENTIDADFED AND LREP.ICVEMUNICIPIO = DREP.ICVEMUNICIPIO " +
	                  "    AND LREP.ICVELOCALIDAD = DREP.ICVELOCALIDAD "+
	                  " " + cWhere + " " + cOrder;
	      
	      String cUsu = "SELECT " +
	                  "      UREG.CNOMBRE || ' ' || UREG.CAPPATERNO || ' ' || UREG.CAPMATERNO AS CNOMUSUREGISTRA, " +
	                  "      UENT.CNOMBRE || ' ' || UENT.CAPPATERNO || ' ' || UENT.CAPMATERNO AS CNOMUSUENTREGA, " +
	                  "      DXO.CTITULAR AS CTITULARDEPTOOFIC,OF.CDSCOFICINA,OF.CDSCBREVE AS CDSCBREVEOFIC,DEP.CDSCDEPARTAMENTO, " +
	                  "      DEP.CDSCBREVE AS CDSCBREVEDEPTO,DC.DTCITA " +
	                  "FROM TRAREGSOLICITUD RS " +
	                  "  LEFT JOIN SEGUSUARIO UREG ON UREG.ICVEUSUARIO = RS.ICVEUSUREGISTRA " +
	                  "  LEFT JOIN SEGUSUARIO UENT ON UENT.ICVEUSUARIO = RS.ICVEUSUENTREGA " +
	                  "  JOIN GRLDEPTOXOFIC DXO ON DXO.ICVEOFICINA = RS.ICVEOFICINA AND DXO.ICVEDEPARTAMENTO = RS.ICVEDEPARTAMENTO " +
	                  "  JOIN GRLOFICINA OF ON OF.ICVEOFICINA = RS.ICVEOFICINA " +
	                  "  JOIN GRLDEPARTAMENTO DEP ON DEP.ICVEDEPARTAMENTO = RS.ICVEDEPARTAMENTO " +
	                  "  JOIN TRADATOSCITA DC ON DC.IEJERCICIO = RS.IEJERCICIOCITA AND DC.IIDCITA = RS.IIDCITA "+
	                  " " + cWhere + " " + cOrder; 

	      vSolicitud = super.FindByGeneric("",cSol,dataSourceName);
	      vPersonas = super.FindByGeneric("",cPer,dataSourceName);
	      vUsuarios = super.FindByGeneric("",cUsu,dataSourceName);
	      //vGenerales.addAll(vSolicitud);
	      vGenerales.addAll(vPersonas);
	      vGenerales.addAll(vUsuarios);
	      
	      if(vSolicitud.size()>0){
	    	  TVDinRep vTemp = (TVDinRep) vSolicitud.get(0);
	    	  vGen.put("IEJERCICIO", vTemp.getInt("iEjercicio"));
	    	  vGen.put("INUMSOLICITUD", vTemp.getInt("INUMSOLICITUD"));
	    	  vGen.put("ICVETRAMITE", vTemp.getInt("ICVETRAMITE"));
	    	  vGen.put("ICVEMODALIDAD", vTemp.getInt("ICVEMODALIDAD"));
	    	  vGen.put("TSREGISTRO", vTemp.getTimeStamp("TSREGISTRO"));
	    	  vGen.put("DTLIMITEENTREGADOCS", vTemp.getDate("DTLIMITEENTREGADOCS"));
	    	  vGen.put("DTESTIMADAENTREGA", vTemp.getDate("DTESTIMADAENTREGA"));
	    	  vGen.put("CNOMAUTORIZARECOGER", vTemp.getString("CNOMAUTORIZARECOGER"));
	    	  vGen.put("ICVEUSUREGISTRA", vTemp.getInt("ICVEUSUREGISTRA"));
	    	  vGen.put("LPAGADO", vTemp.getInt("LPAGADO"));
	    	  vGen.put("DTENTREGA", vTemp.getDate("DTENTREGA"));
	    	  vGen.put("ICVEUSUREGISTRA", vTemp.getInt("ICVEUSUREGISTRA"));
	    	  vGen.put("LPAGADO", vTemp.getInt("LPAGADO"));
	    	  vGen.put("DTENTREGA", vTemp.getDate("DTENTREGA"));
	    	  vGen.put("ICVEUSUENTREGA", vTemp.getInt("ICVEUSUENTREGA"));
	    	  vGen.put("LRESOLUCIONPOSITIVA", vTemp.getInt("LRESOLUCIONPOSITIVA"));
	    	  vGen.put("LDATOSPREREGISTRO", vTemp.getInt("LDATOSPREREGISTRO"));
	    	  vGen.put("IIDBIEN", vTemp.getInt("IIDBIEN"));
	    	  vGen.put("ICVEOFICINA", vTemp.getInt("ICVEOFICINA"));
	    	  vGen.put("ICVEDEPARTAMENTO", vTemp.getInt("ICVEDEPARTAMENTO"));
	    	  vGen.put("IEJERCICIOCITA", vTemp.getInt("IEJERCICIOCITA"));
	    	  vGen.put("IIDCITA", vTemp.getInt("IIDCITA"));
	    	  vGen.put("ICVEFORMA", vTemp.getInt("ICVEFORMA"));
	    	  vGen.put("LPRINCIPAL", vTemp.getInt("LPRINCIPAL"));
	    	  vGen.put("LIMPRESO", vTemp.getInt("LIMPRESO"));
	    	  vGen.put("CENVIARRESOLUCIONA", vTemp.getString("CENVIARRESOLUCIONA"));
	    	  vGen.put("COBSTRAMITE", vTemp.getString("COBSTRAMITE"));
	    	  vGen.put("CDSCBIEN", vTemp.getString("CDSCBIEN"));
	    	  vGen.put("CCVEINTERNA", vTemp.getString("CCVEINTERNA"));
	    	  vGen.put("CDSCBREVE", vTemp.getString("CDSCBREVE"));
	    	  vGen.put("CDSCTRAMITE", vTemp.getString("CDSCTRAMITE"));
	    	  vGen.put("LREQFIRMADIGITAL", vTemp.getInt("LREQFIRMADIGITAL"));
	    	  vGen.put("CDSCTRAMITE", vTemp.getString("CDSCTRAMITE"));
	    	  vGen.put("CDSCMODALIDAD", vTemp.getString("CDSCMODALIDAD"));
	    	  vGen.put("ICVESOLICITANTE", vTemp.getInt("ICVESOLICITANTE"));
	    	  vGen.put("ICVEREPLEGAL", vTemp.getInt("ICVEREPLEGAL"));
	    	  vGen.put("ICVEDOMICILIOSOLICITANTE", vTemp.getInt("ICVEDOMICILIOSOLICITANTE"));
	    	  vGen.put("ICVEDOMICILIOREPLEGAL", vTemp.getInt("ICVEDOMICILIOREPLEGAL"));
	    	  vGen.put("CDSCFORMA", vTemp.getString("CDSCFORMA"));
	    	  vGen.put("CDOCUMENTOMOSTRAR", vTemp.getString("CDOCUMENTOMOSTRAR"));
	      }
	      if(vPersonas.size()>0){
	    	  TVDinRep vTemp = (TVDinRep) vPersonas.get(0);
	    	  vGen.put("CNOMSOLICITANTE", vTemp.getString("CNOMSOLICITANTE"));
	    	  vGen.put("CRFCSOL", vTemp.getString("CRFCSOL"));
	    	  vGen.put("CCORREOESOL", vTemp.getString("CCORREOESOL"));
	    	  vGen.put("CNOMREPLEGAL", vTemp.getString("CNOMREPLEGAL"));
	    	  vGen.put("CRFCREP", vTemp.getString("CRFCREP"));
	    	  vGen.put("CCORREOEREP", vTemp.getString("CCORREOEREP"));
	    	  vGen.put("CCALLESOL", vTemp.getString("CCALLESOL"));
	    	  vGen.put("CNUMEXTERIORSOL", vTemp.getString("CNUMEXTERIORSOL"));
	    	  vGen.put("CNUMINTERIORSOL", vTemp.getString("CNUMINTERIORSOL"));
	    	  vGen.put("CCOLONIASOL", vTemp.getString("CCOLONIASOL"));
	    	  vGen.put("CCODPOSTALSOL", vTemp.getString("CCODPOSTALSOL"));
	    	  vGen.put("CTELEFONOSOL", vTemp.getString("CTELEFONOSOL"));
	    	  vGen.put("CCALLEREP", vTemp.getString("CCALLEREP"));
	    	  vGen.put("CNUMEXTERIORREP", vTemp.getString("CNUMEXTERIORREP"));
	    	  vGen.put("CNUMINTERIORREP", vTemp.getString("CNUMINTERIORREP"));
	    	  vGen.put("CCOLONIAREP", vTemp.getString("CCOLONIAREP"));
	    	  vGen.put("CCODPOSTALREP", vTemp.getString("CCODPOSTALREP"));
	    	  vGen.put("CTELEFONOREP", vTemp.getString("CTELEFONOREP"));
	    	  vGen.put("CNOMPAISSOL", vTemp.getString("CNOMPAISSOL"));
	    	  vGen.put("CABREVPAISSOL", vTemp.getString("CABREVPAISSOL"));
	    	  vGen.put("CNOMENTIDADSOL", vTemp.getString("CNOMENTIDADSOL"));
	    	  vGen.put("CABREVENTIDADSOL", vTemp.getString("CABREVENTIDADSOL"));
	    	  vGen.put("CNOMMUNICIPIOSOL", vTemp.getString("CNOMMUNICIPIOSOL"));
	    	  vGen.put("CABREVMUNICIPIOSOL", vTemp.getString("CABREVMUNICIPIOSOL"));
	    	  vGen.put("CNOMLOCALIDADSOL", vTemp.getString("CNOMLOCALIDADSOL"));
	    	  vGen.put("CABREVLOCALIDADSOL", vTemp.getString("CABREVLOCALIDADSOL"));
	    	  vGen.put("CNOMPAISREP", vTemp.getString("CNOMPAISREP"));
	    	  vGen.put("CABREVPAISREP", vTemp.getString("CABREVPAISREP"));
	    	  vGen.put("CNOMENTIDADREP", vTemp.getString("CNOMENTIDADREP"));
	    	  vGen.put("CABREVENTIDADREP", vTemp.getString("CABREVENTIDADREP"));
	    	  vGen.put("CNOMMUNICIPIOREP", vTemp.getString("CNOMMUNICIPIOREP"));
	    	  vGen.put("CABREVMUNICIPIOREP", vTemp.getString("CABREVMUNICIPIOREP"));
	    	  vGen.put("CNOMLOCALIDADREP", vTemp.getString("CNOMLOCALIDADREP"));
	    	  vGen.put("CABREVLOCALIDADREP", vTemp.getString("CABREVLOCALIDADREP"));
	    	  
	      }

	      if(vUsuarios.size()>0){
	    	  TVDinRep vTemp = (TVDinRep) vUsuarios.get(0);
	    	  vGen.put("CNOMUSUREGISTRA", vTemp.getString("CNOMUSUREGISTRA"));
	    	  vGen.put("CNOMUSUENTREGA", vTemp.getString("CNOMUSUENTREGA"));
	    	  vGen.put("CTITULARDEPTOOFIC", vTemp.getString("CTITULARDEPTOOFIC"));
	    	  vGen.put("CDSCOFICINA", vTemp.getString("CDSCOFICINA"));
	    	  vGen.put("CDSCBREVEOFIC", vTemp.getString("CDSCBREVEOFIC"));
	    	  vGen.put("CDSCDEPARTAMENTO", vTemp.getString("CDSCDEPARTAMENTO"));
	    	  vGen.put("CDSCBREVEDEPTO", vTemp.getString("CDSCBREVEDEPTO"));
	    	  vGen.put("DTCITA", vTemp.getDate("DTCITA"));
	      }
	    } catch(SQLException ex){
	      ex.printStackTrace();
	      cMensaje = ex.getMessage();
	    } catch(Exception ex2){
	      ex2.printStackTrace();
	      cMensaje += ex2.getMessage();
	    }
	    if(!cMensaje.equals(""))
	      throw new Exception(cMensaje);
	    return vGen;
	  }

  
  
  
  public Vector getDatosOficinaResolucion(String cWhere, String cOrder) throws Exception{
    Vector vDerechos = new Vector();
    try{ // Datos de Oficina de Resolucion para un trámite
      String cSQL = "SELECT "+
                    " TXO.iCveOficina, TXO.iCveTramite, TXO.iCveOficinaResuelve, TXO.iCveDeptoResuelve,"+
                    " OFI.cDscOficina, OFI.cDscBreve AS cDscBreveOfic, OFI.cTitular AS cTitularOficina,"+
                    " DEP.cDscDepartamento, DEP.cDscBreve AS cDscBreveDepto,"+
                    " DXO.cTitular AS cTitularOficDepto, DXO.cPuestoTitular AS cPuestoTitularOficDepto"+
                    " FROM TRATramiteXOfic TXO"+
                    " JOIN GRLOficina OFI ON OFI.iCveOficina = TXO.iCveOficinaResuelve"+
                    " JOIN GRLDepartamento DEP ON DEP.iCveDepartamento = TXO.iCveDeptoResuelve"+
                    " JOIN GRLDeptoXOfic DXO ON DXO.iCveOficina = TXO.iCveOficinaResuelve ANd DXO.iCveDepartamento = TXO.iCveDeptoResuelve"+
                    " " + cWhere + " " + cOrder;
      vDerechos = super.FindByGeneric("",cSQL,dataSourceName);
    }catch(SQLException ex){
      cMensaje += ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return vDerechos;
  }

  public Vector getDatosDerechosSolicitud(String cWhere, String cOrder) throws Exception{
    Vector vDerechos = new Vector();
    try{ // Datos de derechos del trámite
      String cSQL = "SELECT "+
                    "   RRP.iEjercicio, RRP.iNumSolicitud,"+
                    "   RRP.iConsecutivo, RRP.iNumUnidades, RRP.iRefNumerica, RRP.dImportePagar,"+
                    "   RRP.lPagado, RRP.dtPago,  RRP.cRefAlfaNum, RRP.dImportePagado, RRP.iCveBanco, RRP.cFormatoSAT,"+
                    "   B.cDscBanco, P.cDscConcepto"+
                    " FROM TRARegRefPago RRP"+
                    " JOIN GRLBanco B ON B.iCveBanco = RRP.iCveBanco"+
                    " JOIN TRAConceptoPago P ON P.iCveConcepto = RRP.iCveConcepto"+
                    " " + cWhere + " " + cOrder;
      vDerechos = super.FindByGeneric("",cSQL,dataSourceName);
    }catch(SQLException ex){
      cMensaje += ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return vDerechos;
  }

  public Vector getDatosRequisitosSolicitud(String cWhere, String cOrder) throws Exception{
    Vector vRequisitos = new Vector();
    try{ // Datos de requisitos del trámite
      String cSQL = "SELECT "+
                    "   RRT.iCveRequisito, RRT.iEjercicioFormato, RRT.iCveFormatoReg, RRT.dtRecepcion, RRT.iCveUsuRecibe,"+
                    "   RRT.dtNotificacion, RRT.cNumOficio, RRT.dtOficio, RRT.lValido,"+
                    "   R.cDscRequisito, R.cDscBreve AS cDscBreveRequisito, R.cFundLegal, R.lDigitaliza, R.iCveFormato,"+
                    "   RMT.iOrden, RMT.lRequerido, RRT.lFisico, "+
                    "   UReg.cNombre || ' ' || UReg.cApPaterno || ' ' || UReg.cApMaterno AS cNomUsuRecibe"+
                    " FROM TRARegReqXTram RRT"+
                    " JOIN TRARequisito R ON R.iCveRequisito = RRT.iCveRequisito"+
                    " JOIN TRAReqXModTramite RMT ON RMT.iCveTramite = RRT.iCveTramite AND RMT.iCveModalidad = RRT.iCveModalidad AND RMT.iCveRequisito = RRT.iCveRequisito"+
                    " LEFT JOIN SEGUsuario UReg ON UReg.iCveUsuario = RRT.iCveUsuRecibe"+
                    " " + cWhere + " " + cOrder ;
      vRequisitos = super.FindByGeneric("",cSQL,dataSourceName);
    }catch(SQLException ex){
      cMensaje += ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return vRequisitos;
  }

  public boolean updateImprocedenteFaltaPago(TVDinRep vDatosActuales){
    boolean lSuccess = false;
    int iEtapaEntregado = Integer.parseInt(VParametros.getPropEspecifica("EtapaEntregaResol"),10);
    TDTRARegEtapasXModTram dRegEtapa = new TDTRARegEtapasXModTram();
    TDTRARegSolicitud dRegSolicitud = new TDTRARegSolicitud();
    TVDinRep vData = new TVDinRep();
    vData.put("iEjercicio", vDatosActuales.getInt("iEjercicio"));
    vData.put("iNumSolicitud", vDatosActuales.getInt("iNumSolicitud"));
    vData.put("iCveTramite", vDatosActuales.getInt("iCveTramite"));
    vData.put("iCveModalidad", vDatosActuales.getInt("iCveModalidad"));
    vData.put("iCveEtapa", iEtapaEntregado);
    vData.put("iCveOficina", vDatosActuales.getInt("iCveOficina"));
    vData.put("iCveDepartamento", vDatosActuales.getInt("iCveDepartamento"));
    vData.put("iCveUsuario", vDatosActuales.getInt("iCveUsuRegistra"));
    vData.put("tsRegistro", tFecha.getThisTime());
    vData.put("lAnexo", 0);
    vData.put("iCveUsuEntrega", vDatosActuales.getInt("iCveUsuRegistra"));
    try{
      dRegEtapa.insertEtapa(vData, null);
      dRegSolicitud.updSolImprocedenteFaltaPago(vData, null);
    }catch(Exception e){
    }
    return lSuccess;
  }

}

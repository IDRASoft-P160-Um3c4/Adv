package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.util.*;
import java.sql.SQLException;
import java.util.Vector;
import java.util.StringTokenizer;
import gob.sct.sipmm.dao.TDMYRRPMN;
import java.util.HashMap;
import com.micper.util.TFechas;


/**
 * <p>Title: TDTramite.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Armando Barrientos Martínez
 * @version 1.0
 */

public class TDMatriculasRPMN extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  public TDMatriculasRPMN(){
  }

  public StringBuffer generaCertifMatricula(String cValoresCertificado) throws Exception{
    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TParametro  vParametros = new TParametro("44");
    String cArticuloFraccion = "",
           cDscCapitania = "",
           cTitular = "";

    TWord rep = new TWord();
    String[] aValoresCertificado;
    aValoresCertificado = cValoresCertificado.split("=");

    /*for(int i=0; i<aValoresCertificado.length;i++)
        //System.out.print("*****    Valores "+i+" =  "+aValoresCertificado[i]);*/

    int iCveUsuario = Integer.parseInt(aValoresCertificado[11]);
    String cMatricula = aValoresCertificado[12];
    TFechas dtHoy = new TFechas();
    Vector vRegs = new Vector();
    Vector vcMotor = new Vector();
    Vector vcSolicitud = new Vector();
    Vector vcSenial = new Vector();
    int iSolicitud = 0;
    String cPotenciaMotor = "";

    try{
      vRegs = super.FindByGeneric("",
    		  "SELECT " +
    		  "	o.ICVEOFICINA, " +
    		  "	o.ICVECATEGORIA, " +
    		  "	o.CTITULAR, " +
    		  "	o.CDSCBREVE, " +
    		  "	c.CDSCCAPITANIA " +
    		  "FROM MYRMATRICULA m " +
    		  "join GRLOFICINA o on o.ICVEOFICINA = m.ICVEPUERTOMAT " +
    		  "join MYRCAPITANIA c on c.ICVEOFICINA = o.ICVEOFICINA " +
    		  "where ICVEEMBARCACION="+aValoresCertificado[13]
    		  +" and DTBAJA is null "
                                   , dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
      ex.printStackTrace();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
      ex2.printStackTrace();
    }
    if (vRegs.size() > 0){
      TVDinRep vDatos = (TVDinRep) vRegs.get(0);
      if ( vDatos.getInt("iCveCategoria") == 0  )
         cArticuloFraccion = vParametros.getPropEspecifica("ArtCertifMatCentrales");
      else
         cArticuloFraccion = vParametros.getPropEspecifica("ArtCertifMatCapitania");

      cDscCapitania = vDatos.getString("cDscCapitania");
      cTitular = vDatos.getString("cTitular");
    }
    try{
      vcMotor = super.FindByGeneric("","SELECT IPOTENCIA FROM VEHMOTOR WHERE LVIGENTE=1 AND ICVEVEHICULO="+aValoresCertificado[13]
                                   , dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
      ex.printStackTrace();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
      ex2.printStackTrace();
    }
    

    try{
      vcSenial = super.FindByGeneric("","SELECT SD.CGRUPOSENALFIJO,SD.CGRUPOSENALCONSEC FROM MYRREGSENALDIST R " +
    		                           "JOIN MYRSENALDISTINT SD ON SD.ICONSECUTIVSENAL=R.ICONSECUTIVSENAL " +
    		                           "WHERE R.ICVEVEHICULO = "+aValoresCertificado[13]
                                       , dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
      ex.printStackTrace();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
      ex2.printStackTrace();
    }
    
    String cSenial = "";
    if(vcSenial.size()>0){
    	TVDinRep vSenial = (TVDinRep) vcSenial.get(0);
    	cSenial = vSenial.getString("CGRUPOSENALFIJO")+"-"+vSenial.getString("CGRUPOSENALCONSEC");
    }
    
    int iPotTotal = 0;
    if (vcMotor.size() > 0){
      for(int iM=0;iM<vcMotor.size();iM++){
        TVDinRep vDatos = (TVDinRep) vcMotor.get(iM);
        cPotenciaMotor += vDatos.getString("IPOTENCIA");
        iPotTotal += vDatos.getInt("IPOTENCIA");
      }
    }
    try{
      vcSolicitud = super.FindByGeneric("","SELECT iNumSolicitud FROM MYRMATRICULA WHERE DTBAJA IS NULL AND ICVEEMBARCACION="+aValoresCertificado[13]
                                   , dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }
      if(vcSolicitud.size()>0){
        TVDinRep vDatos = (TVDinRep) vcSolicitud.get(0);
        iSolicitud = vDatos.getInt("iNumSolicitud");
      }

      Vector vcMatricula = new Vector();
      vcMatricula = this.findByNessted("", "SELECT DTASIGNACION FROM MYRMATRICULA where ICVEEMBARCACION="+aValoresCertificado[13]+" and DTBAJA is null order by DTASIGNACION desc ", conn);
      TVDinRep vMatricula = new TVDinRep();
      if(vcMatricula.size()>0){
    	  vMatricula = (TVDinRep) vcMatricula.get(0);
      }


    rep.iniciaReporte();
    if(iSolicitud>0){
      rep.comRemplaza("[cNombrePropietario]",aValoresCertificado[0]);
      rep.comRemplaza("[cNombreEmbarcacion]",aValoresCertificado[1]);
      rep.comRemplaza("[cTipoEmbarcacion]",aValoresCertificado[2]);
      rep.comRemplaza("[cServicio]",aValoresCertificado[3]);
      rep.comRemplaza("[cTipoNavegacion]",aValoresCertificado[4]);
      rep.comRemplaza("[cUAB]",aValoresCertificado[5]);
      rep.comRemplaza("[cEslora]",aValoresCertificado[6]);
      rep.comRemplaza("[cUAN]",aValoresCertificado[7]);
      rep.comRemplaza("[cManga]",aValoresCertificado[8]);
      rep.comRemplaza("[cPesoMuerto]",aValoresCertificado[9]);
      rep.comRemplaza("[cPuntal]",aValoresCertificado[10]);
      rep.comRemplaza("[cArticuloFraccion]",cArticuloFraccion);
      rep.comRemplaza("[cOficina]",cDscCapitania);
      rep.comRemplaza("[DTASIGNACION]",vMatricula.getString("DTASIGNACION"));
      rep.comRemplaza("[cFechaExpedicion]",
                      dtHoy.getFechaDDMMYYYY(dtHoy.TodaySQL(),"/"));
      rep.comRemplaza("[cTitularOficina]",cTitular);
      rep.comRemplaza("[cMatricula]",cMatricula);
      rep.comRemplaza("[cPotenciaMotor]",cPotenciaMotor);
      rep.comRemplaza("[iPotTotal]",""+iPotTotal);
      rep.comRemplaza("[cMotor]",vcMotor.size() + "");
      rep.comRemplaza("[cSenial]",cSenial);
      
    }
    else{
      rep.comRemplaza("[cNombrePropietario]","");
      rep.comRemplaza("[cNombreEmbarcacion]","");
      rep.comRemplaza("[cTipoEmbarcacion]","");
      rep.comRemplaza("[cServicio]","");
      rep.comRemplaza("[cTipoNavegacion]","");
      rep.comRemplaza("[cUAB]","");
      rep.comRemplaza("[cEslora]","");
      rep.comRemplaza("[cUAN]","");
      rep.comRemplaza("[cManga]","");
      rep.comRemplaza("[cPesoMuerto]","");
      rep.comRemplaza("[cPuntal]","");
      rep.comRemplaza("[cArticuloFraccion]","");
      rep.comRemplaza("[cOficina]","");
      rep.comRemplaza("[cFechaExpedicion]","");
      rep.comRemplaza("[cTitularOficina]","");
      rep.comRemplaza("[cMatricula]","");
      rep.comRemplaza("[cPotenciaMotor]","");
      rep.comRemplaza("[iPotTotal]","");
      rep.comRemplaza("[cMotor]","");
      rep.comRemplaza("[cSenial]","");
    }

    return rep.getEtiquetas(true);
  }

  public StringBuffer generaCertMatricula(String cValoresCertificado) throws Exception{
    TDMYRRPMN dMyrRPMN = new TDMYRRPMN();
    TWord rep = new TWord();
    String[] aDato = cValoresCertificado.split("=");
    String cEjercicioIns = aDato[0];
    String cCveOficina = aDato[1];
    String cFolio = aDato[2];
    String cPartida = aDato[3];
    String ejercicioIns="",cveOficina="",siglasOficina="",folioRPMN="",partida="",nomRegistrador="",
           fechaRegistro="",dscDocumento="",ramo="",seccion="",tipoActo="",tipoAsiento="",usuRegistra="",objeto="";

    String cQuery =
        "SELECT R.IEJERCICIOINS,R.ICVEOFICINA,C.CSIGLASOFICINA,R.IFOLIORPMN,R.IPARTIDA,C.CNOMREGISTRADOR, " +
        "R.DTREGISTRO,R.CDSCDOCUMENTO,MR.CDSCRAMO,MS.CDSCSECCION,MTA.CDSCTIPOACTO,TA.CDSCTIPOASIENTO, " +
        "USU.CNOMBRE||' '||USU.CAPPATERNO||' '||USU.CAPMATERNO as CUSUARIOREG,OL.CDSCOBSLARGA, " +
        "ME.CNOMEMBARCACION,ME.CMATRICULA,ME.CPROPIETARIO, " +
        "ME.DESLORA,ME.DMANGA,ME.DPUNTAL,ME.DARQUEOBRUTO,ME.DARQUEONETO,E.DPESOMUERTO, " +
        "UM1.CDSCUNIDADMEDIDA AS UMESLORA,UM2.CDSCUNIDADMEDIDA AS UMMANGA,UM3.CDSCUNIDADMEDIDA AS UMPUNTAL,"+
        "UM4.CDSCUNIDADMEDIDA AS UMAB,UM5.CDSCUNIDADMEDIDA AS UMAN,UM6.CDSCUNIDADMEDIDA AS UMPM " +
        "FROM MYRRPMN R " +
        "JOIN MYRCAPITANIA C ON C.ICVEOFICINA=R.ICVEOFICINA " +
        "JOIN MYRRAMO MR ON MR.ICVERAMO=R.ICVERAMO " +
        "JOIN MYRSECCION MS ON MS.ICVERAMO=R.ICVERAMO AND MS.ICVESECCION=R.ICVESECCION " +
        "JOIN MYRTIPOACTO MTA ON MTA.ICVERAMO=R.ICVERAMO AND MTA.ICVESECCION=R.ICVESECCION AND MTA.ICVETIPOACTO=R.ICVETIPOACTO " +
        "JOIN MYRTIPOASIENTO TA ON TA.ICVETIPOASIENTO=R.ICVETIPOASIENTO " +
        "JOIN SEGUSUARIO USU ON USU.ICVEUSUARIO=R.ICVEUSUARIO " +
        "LEFT JOIN GRLOBSERVALARGA OL ON OL.ICVEOBSLARGA=R.ICVEOBSLARGA AND OL.IEJERCICIOOBSLARGA=R.IEJERCICIOOBSLARGA " +
        "LEFT JOIN MYREMBARCACION ME ON ME.IEJERCICIOINS=R.IEJERCICIOINS AND ME.ICVEOFICINA=R.ICVEOFICINA " +
        "     AND ME.IFOLIORPMN=R.IFOLIORPMN AND ME.IPARTIDA=R.IPARTIDA " +
        "LEFT JOIN VEHTIPOEMBARCACION TE ON TE.ICVETIPOEMBARCACION=ME.ICVETIPOEMBARCACION " +
        "LEFT JOIN VEHEMBARCACION E ON E.ICVEVEHICULO = ME.ICVEVEHICULO " +
        "LEFT JOIN VEHTIPOSERVICIO TS ON TS.ICVETIPOSERV=E.ICVETIPOSERV " +
        "LEFT JOIN VEHTIPONAVEGACION TN ON TN.ICVETIPONAVEGA=E.ICVETIPONAVEGA " +
        "LEFT JOIN VEHUNIDADMEDIDA UM1 ON UM1.ICVEUNIDADMEDIDA=ME.IUNIMEDESLORA " +
        "LEFT JOIN VEHUNIDADMEDIDA UM2 ON UM2.ICVEUNIDADMEDIDA=ME.IUNIMEDMANGA " +
        "LEFT JOIN VEHUNIDADMEDIDA UM3 ON UM3.ICVEUNIDADMEDIDA=ME.IUNIMEDPUNTAL " +
        "LEFT JOIN VEHUNIDADMEDIDA UM4 ON UM4.ICVEUNIDADMEDIDA=ME.IUNIMEDARQUEOBRUTO " +
        "LEFT JOIN VEHUNIDADMEDIDA UM5 ON UM5.ICVEUNIDADMEDIDA=ME.IUNIMEDARQUEONETO " +
        "LEFT JOIN VEHUNIDADMEDIDA UM6 ON UM6.ICVEUNIDADMEDIDA=E.IUNIMEDPESOMUERTO "+
        "WHERE R.IEJERCICIOINS= " + cEjercicioIns +
        "     AND R.ICVEOFICINA= " + cCveOficina +
        "     AND R.IFOLIORPMN= " + cFolio +
        "     AND R.IPARTIDA= " + cPartida;

    Vector vcRPMN = dMyrRPMN.findByCustom("",cQuery);

    TVDinRep vRPMN = new TVDinRep();
    rep.iniciaReporte();
    if(vcRPMN.size()>0){
      vRPMN = (TVDinRep) vcRPMN.get(0);
      rep.comRemplaza("[IEJERCICIOINS]",vRPMN.getString("IEJERCICIOINS"));
      rep.comRemplaza("[ICVEOFICINA]",vRPMN.getString("ICVEOFICINA"));
      rep.comRemplaza("[CSIGLASOFICINA]",vRPMN.getString("CSIGLASOFICINA"));
      rep.comRemplaza("[IFOLIORPMN]",vRPMN.getString("IFOLIORPMN"));
      rep.comRemplaza("[IPARTIDA]",vRPMN.getString("IPARTIDA"));
      rep.comRemplaza("[CTITULAROFICINA]",vRPMN.getString("CNOMREGISTRADOR"));
      rep.comRemplaza("[DTREGISTRO]",vRPMN.getString("DTREGISTRO"));
      rep.comRemplaza("[CDSCDOCUMENTO]",vRPMN.getString("CDSCDOCUMENTO"));
      rep.comRemplaza("[CDSCRAMO]",vRPMN.getString("CDSCRAMO"));
      rep.comRemplaza("[CDSCSECCION]",vRPMN.getString("CDSCSECCION"));
      rep.comRemplaza("[CDSCTIPOACTO]",vRPMN.getString("CDSCTIPOACTO"));
      rep.comRemplaza("[CDSCTIPOASIENTO]",vRPMN.getString("CDSCTIPOASIENTO"));
      rep.comRemplaza("[CUSUARIOREG]",vRPMN.getString("CUSUARIOREG"));
      rep.comRemplaza("[CDSCOBSLARGA]",vRPMN.getString("CDSCOBSLARGA"));
      rep.comRemplaza("[CNOMEMBARCACION]",vRPMN.getString("CNOMEMBARCACION"));
      rep.comRemplaza("[CMATRICULA]",vRPMN.getString("CMATRICULA"));
      rep.comRemplaza("[CPROPIETARIO]",vRPMN.getString("CPROPIETARIO"));
      rep.comRemplaza("[DESLORA]",vRPMN.getString("DESLORA"));
      rep.comRemplaza("[DMANGA]",vRPMN.getString("DMANGA"));
      rep.comRemplaza("[DPUNTAL]",vRPMN.getString("DPUNTAL"));
      rep.comRemplaza("[DARQUEOBRUTO]",vRPMN.getString("DARQUEOBRUTO"));
      rep.comRemplaza("[DARQUEONETO]",vRPMN.getString("DARQUEONETO"));
      rep.comRemplaza("[DPESOMUERTO]",vRPMN.getString("DPESOMUERTO"));
      rep.comRemplaza("[UMESLORA]",vRPMN.getString("UMESLORA"));
      rep.comRemplaza("[UMMANGA]",vRPMN.getString("UMMANGA"));
      rep.comRemplaza("[UMPUNTAL]",vRPMN.getString("UMPUNTAL"));
      rep.comRemplaza("[UMAB]",vRPMN.getString("UMAB"));
      rep.comRemplaza("[UMAN]",vRPMN.getString("UMAN"));
      rep.comRemplaza("[UMPM]",vRPMN.getString("UMPM"));


      String cMotores = "";
      Vector vcMotores = dMyrRPMN.findByCustom("",cMotores);
      if(vcMotores.size()>0){
        rep.comRemplaza("[cNumMotores]",vcMotores.size()+"");
        double dPotMotor = 0.0;
        for(int i=0;i<vcMotores.size();i++){
          TVDinRep vMotores = (TVDinRep) vcMotores.get(0);
          dPotMotor = vMotores.getDouble("")+dPotMotor;
        }
        rep.comRemplaza("[cPotenciaMotor]",dPotMotor+"");
      }
      else{
        rep.comRemplaza("[cNumMoteores]","0");
        rep.comRemplaza("[cPotenciaMotor]","0");
      }

      rep.comRemplaza("[xxx]",vRPMN.getString(""));
      rep.comRemplaza("[xxx]",vRPMN.getString(""));
      rep.comRemplaza("[xxx]",vRPMN.getString(""));



    }
    return null;
  }

  public StringBuffer genCancMatYDimisBand(String cValores)  throws Exception{
    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    TFechas tFecha = new TFechas();
    String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
    TWord rep = new TWord();

    //cValores trae los siguientes valores
    //0-Propietario, 1-NomEmbarcacion, 2-Tipo Embarcacion, 3-Tipo Servicio, 4-TipoNavega, 5-Arqueo Bruto
    //6-Eslore, 7-Arqueo Neto, 8- Manga, 9-Peso muerto, 10-Puntal, 11-Cve Usuario
    //12-cMatricula, 13-Num Solicitud, 14-Cve PErsona, 15-Cve Domicilio, 16-Cve Rep Legal
    //17-Señal distintiva, 18-Fecha solicitud, 19-Cve Ofic Resuelve Trámite,20-CveDepto Resuelve trámite
    String[] aValoresRecibidos = cValores.split("==");
    //int iCveEmbarcacion = Integer.parseInt(aValoresRecibidos[0]);

    String cProp = aValoresRecibidos[0];
    String cNomEmb = aValoresRecibidos[1];
    String cTipoEmb = aValoresRecibidos[2];
    String cUAB = aValoresRecibidos[5];
    String cMatricula = aValoresRecibidos[12];
    String cNumSol = aValoresRecibidos[13];
    /*int iCvePersona = Integer.parseInt(aValoresRecibidos[14]);
    int iCveDom = Integer.parseInt(aValoresRecibidos[15]);
    int iCveRepLegal = Integer.parseInt(aValoresRecibidos[16]);*/
    String cSD = aValoresRecibidos[17];
    String fechaSolicitud = aValoresRecibidos[18];
    int iCveOficResuelve = Integer.parseInt(aValoresRecibidos[19]);
    int iCveDeptoResuelve = Integer.parseInt(aValoresRecibidos[20]);

    TDObtenDatos dObten2 = new TDObtenDatos();

    dObten2.dOficDepto.setOficinaDepto(iCveOficResuelve, iCveDeptoResuelve);
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

    String cLetra1="",cLetra2="",cLetra3="",cLetra4="",cLetra5="";

    Vector vRegs = new Vector();
    String cQuery, cFechaInsp="";

    TNombreLetraONumero nl = new TNombreLetraONumero();
    rep.iniciaReporte();
    rep.comRemplaza("[cMunicipioEmision]", cMunicipioEmision);
    rep.comRemplaza("[cEntidadEmision]", dObten2.dOficDepto.vDatoOfic.getAbrevEntidad() + ".");
    rep.comRemplaza("[cFechaEmision]",tFecha.getFechaDDMMMMMYYYY(tFecha.TodaySQL()," de "));
    rep.comRemplaza("[dtFechaSol]",fechaSolicitud);
    rep.comRemplaza("[iNumSolicitud]",cNumSol);
    rep.comRemplaza("[cPropietarioOPoseedor]",cProp);
    rep.comRemplaza("[cNombreEmbarcacion]",cNomEmb);
    rep.comRemplaza("[cMatricula]",cMatricula);

    //Separar por letra la SD y buscar el nombre por letra y número
    if(!cSD.equals("")  && cSD.length()==5){
      cLetra1 = cSD.substring(0,1);
      cLetra2 = cSD.substring(1,2);
      cLetra3 = cSD.substring(2,3);
      cLetra4 = cSD.substring(3,4);
      cLetra5 = cSD.substring(4,5);
    }
    rep.comRemplaza("[cLetra1]",cLetra1);
    rep.comRemplaza("[cLetra2]",cLetra2);
    rep.comRemplaza("[cLetra3]",cLetra3);
    rep.comRemplaza("[cLetra4]",cLetra4);
    rep.comRemplaza("[cLetra5]",cLetra5);
    rep.comRemplaza("[cTipoEmbarcacion]",cTipoEmb);
    rep.comRemplaza("[cUAB]",cUAB);

    dObten2.dOficina.setOficina(iCveOficResuelve);
    //System.out.print("dObten2.dOficina.getTitular(): "+dObten2.dOficina.getTitular());
    rep.comRemplaza("[cNombreRemitente]",dObten2.dOficina.getTitular());
    return rep.getEtiquetas(true);
  }

  public Vector genOficResAban(String cValores) throws Exception{
    Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    TFechas dtInsp = new TFechas();
    TWord tWord = new TWord();
    //cValores trae los siguientes valores 0-CveEmbarcacion, 1-fechaSolicitud,2-NumSolicitud,3-EjercicioSol,4-nombre de la embarcación,
   //5-nombre del propietari o poseedor, 6-tipo de servicio, 7-señal distintiva, 8-arqueo bruto, 9-arqueo neto, 10-pero muerto, 11-esloqa
   //12-puntal, 13-Tipo de Navegación, 14-Manga, 15-Puerto, 16-Tipo de Embarcación, 17-Autoridad que ejecuta, 18-Matrícula

    String[] aValoresRecibidos = cValores.split("=");
    int iCveEmbarcacion = Integer.parseInt(aValoresRecibidos[0]);
    String fechaSolicitud = aValoresRecibidos[1];
    String cNumSol = aValoresRecibidos[2];
    String cEjercicioSol = aValoresRecibidos[3];
    String cNomEmb = aValoresRecibidos[4];
    String cProp = aValoresRecibidos[5];
    String cTipoServ = aValoresRecibidos[6];
    String cSD = aValoresRecibidos[7];
    String cUAB = aValoresRecibidos[8];
    String cUAN = aValoresRecibidos[9];
    String cPesoMuerto = aValoresRecibidos[10];
    String cEslora = aValoresRecibidos[11];
    String cPuntal = aValoresRecibidos[12];
    String cTipoNaveg = aValoresRecibidos[13];
    String cManga  = aValoresRecibidos[14];
    String cPuerto = aValoresRecibidos[15];
    String cTipoEmb = aValoresRecibidos[16];
    String cAutEjec = aValoresRecibidos[17];
    String cMatricula = aValoresRecibidos[18];
    String cCveOficina  = aValoresRecibidos[20];
    String cCveDepto  = aValoresRecibidos[21];
    String  CNOMCONSUL="",CDIRCONSUL="",CDSCPUERTO="",cPaisAband="",cPtoDesp="",cPaisDesp="",tsacta="",CCAPITANEMB="",CJEFEMAQUINAS="",CPARTICIPANTES="";
    String dtActa=null,hActa=null;
    String COFICIOAUTORIZACION="",cSolicitante="",cOficinaMatricula="",CCALLEYNO="",CCOLONIA="",CCODPOSTAL="",CTELEFONO="";
    String cPaisMatricula="",cEntidadMatricula="",cRepresentante="",cSenialdist="",CNUMOMI="",CENTIDFED="",CMUNICIPIO="";

    String cFechaAbanDimi = dtInsp.getDateSPN(dtInsp.getDateSQL(aValoresRecibidos[22]));
    String cNomL1 = "", cNomL2 = "",cNomL3 = "",cNomL4 = "",cNomL5 = "", cLetra1="",cLetra2="",cLetra3="",cLetra4="",cLetra5="";

    Vector vRegs = new Vector();
    String cQuery, cFechaInsp="";


    //****Busca Inspección
     cQuery = "SELECT " +
         "  INSPROGINSP.ICVEINSPPROG, " +
         "  INSINSPECCION.DTFININSP " +
         "FROM INSPROGINSP " +
         "JOIN INSINSPECCION ON INSPROGINSP.ICVEINSPPROG = INSINSPECCION.ICVEINSPPROG " +
         "WHERE INSPROGINSP.ICVEEMBARCACION = " + iCveEmbarcacion + "  ORDER BY INSINSPECCION.DTFININSP DESC";

    vRegs = this.buscaYRegresaDato(cQuery);
    //
    String cExtras = "SELECT " +
                     "         CNOMCONSUL, " +
                     "         CDIRCONSUL, " +
                     "         P1.CDSCPUERTO, " +
                     "         PA1.CNOMBRE AS CPAISABAND, " +
                     "         P2.CDSCPUERTO AS CPTODESP, " +
                     "         PA1.CNOMBRE AS CPAISDESP, " +
                     "         TSACTA, " +
                     "         CCAPITANEMB, " +
                     "         CJEFEMAQUINAS, " +
                     "         CPARTICIPANTES, " +
                     "         A.COFICIOAUTORIZACION, " +
                     "         SOL.CNOMRAZONSOCIAL||' '|| SOL.CAPPATERNO ||' '|| SOL.CAPMATERNO AS CSOLICITANTE, " +
                     "         OM.CDSCOFICINA AS COFICINAMATRICULA, " +
				     "         OM.CCALLEYNO, " +
				     "         OM.CCOLONIA, " +
        			 "         OM.CCODPOSTAL, " +
				     "         OM.CTELEFONO, " +
				     "         PM.CNOMBRE AS CPAISMATRICULA, " +
				     "         EM.CNOMBRE AS CENTIDADMATRICULA, " +
				     "         REP.CNOMRAZONSOCIAL||' '|| REP.CAPPATERNO||' '|| REP.CAPMATERNO AS CREPRESENTANTE, " +
				     "         SD.CGRUPOSENALFIJO||'-'|| SD.CGRUPOSENALCONSEC AS CSENIALDIST, " +
				     "         E.CNUMOMI, " +
				     "         EF.CNOMBRE AS CENTIDFED, " +
				     "         MU.CNOMBRE AS CMUNICIPIO " +
				     "FROM MYRABANDERAMIENTO A LEFT " +
				     "  JOIN GRLPUERTO AS P1 ON P1.ICVEPUERTO=A.ICVEPTOABAND LEFT " +
				     "  JOIN GRLPAIS AS PA1 ON PA1.ICVEPAIS=P1.ICVEPAIS " +
				     "  LEFT JOIN GRLPUERTO AS P2 ON P2.ICVEPUERTO=A.ICVEPTODESPACHO " +
				     "  LEFT JOIN GRLPAIS AS PA2 ON PA2.ICVEPAIS=P2.ICVEPAIS " +
				     "  LEFT JOIN GRLENTIDADFED EF ON P2.ICVEPAIS= EF.ICVEPAIS AND P2.ICVEENTIDADFED=EF.ICVEENTIDADFED " +
				     "  LEFT JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS=P2.ICVEMUNICIPIO AND MU.ICVEENTIDADFED=P2.ICVEENTIDADFED AND MU.ICVEMUNICIPIO=P2.ICVEMUNICIPIO " +
				     "  LEFT JOIN TRAREGSOLICITUD S ON S.IEJERCICIO=A.IEJERCICIOSOLICITUD " +
				     "    AND S.INUMSOLICITUD=A.INUMSOLICITUD " +
				     "  LEFT JOIN GRLPERSONA SOL ON SOL.ICVEPERSONA=S.ICVESOLICITANTE " +
				     "  LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION=A.ICVEEMBARCACION " +
				     "    AND MA.DTBAJA IS NULL " +
				     "  LEFT JOIN GRLOFICINA OM ON OM.ICVEOFICINA=MA.ICVEPUERTOMAT " +
				     "  LEFT JOIN GRLPAIS PM ON PM.ICVEPAIS=OM.ICVEPAIS " +
				     "  LEFT JOIN GRLPERSONA REP ON REP.ICVEPERSONA=S.ICVEREPLEGAL " +
				     "  LEFT JOIN GRLENTIDADFED EM ON EM.ICVEPAIS=OM.ICVEPAIS " +
				     "    AND EM.ICVEENTIDADFED=OM.ICVEENTIDADFED LEFT " +
				     "  JOIN MYRREGSENALDIST RSD ON RSD.ICVEVEHICULO=A.ICVEEMBARCACION " +
				     "    AND DTBAJASENAL IS NULL " +
				     "  LEFT JOIN MYRSENALDISTINT SD ON SD.ICONSECUTIVSENAL=RSD.ICONSECUTIVSENAL " +
				     "  JOIN VEHEMBARCACION E ON E.ICVEVEHICULO=A.ICVEEMBARCACION " +
                     "where  a.IEJERCICIOSOLICITUD="+cEjercicioSol+" and a.inumsolicitud="+cNumSol;
    Vector vcExtras = this.findByNessted("", cExtras, conn);
    TVDinRep vExtras = new TVDinRep();
    boolean lCamposExtra = false;
    if(vcExtras.size()>0){
      vExtras = (TVDinRep) vcExtras.get(0);
      lCamposExtra = true;
    }
    if(lCamposExtra==true){
    	CNOMCONSUL=vExtras.getString("CNOMCONSUL");
    	CDIRCONSUL=vExtras.getString("CDIRCONSUL");
    	CDSCPUERTO=vExtras.getString("CDSCPUERTO");
    	cPaisAband=vExtras.getString("cPaisAband");
    	cPtoDesp=vExtras.getString("cPtoDesp");
    	cPaisDesp=vExtras.getString("cPaisDesp");
    	//System.out.print(">>>  hora acta: "+vExtras.getTimeStamp("tsacta"));
    	//System.out.print(">>>  hora acta: "+(""+vExtras.getTimeStamp("tsacta")).equals("null"));
    	if (!(""+vExtras.getTimeStamp("tsacta")).equals("null")){
	    	tsacta=""+vExtras.getTimeStamp("tsacta");
	    	dtActa = ""+dtInsp.getDateSQL(vExtras.getTimeStamp("tsacta"));
	    	hActa = ""+dtInsp.getStringHora24H(vExtras.getTimeStamp("tsacta"));
    	}
    	CCAPITANEMB=vExtras.getString("CCAPITANEMB");
    	CJEFEMAQUINAS=vExtras.getString("CJEFEMAQUINAS");
    	CPARTICIPANTES=vExtras.getString("CPARTICIPANTES");
    	COFICIOAUTORIZACION=vExtras.getString("COFICIOAUTORIZACION");
    	cSolicitante=vExtras.getString("cSolicitante");
    	cOficinaMatricula=vExtras.getString("cOficinaMatricula");
    	CCALLEYNO=vExtras.getString("CCALLEYNO");
    	CCOLONIA=vExtras.getString("CCOLONIA");
    	CCODPOSTAL=vExtras.getString("CCODPOSTAL");
    	CTELEFONO=vExtras.getString("CTELEFONO");
    	cPaisMatricula=vExtras.getString("cPaisMatricula");
    	cEntidadMatricula=vExtras.getString("cEntidadMatricula");
    	cRepresentante=vExtras.getString("cRepresentante");
    	cSenialdist=vExtras.getString("cSenialdist");
    	CNUMOMI = vExtras.getString("CNUMOMI");
    	CENTIDFED = vExtras.getString("CENTIDFED");
    	CMUNICIPIO = vExtras.getString("CMUNICIPIO");
    	
    }


    if(vRegs.size() > 0){
      TVDinRep vDatos = (TVDinRep) vRegs.get(0);
      if(!(vDatos.getString("DTFININSP").equals("null")))
        cFechaInsp = dtInsp.getDateSPN(vDatos.getDate("DTFININSP"));
      else cFechaInsp = "";
    }
    // Termina Inspeccion

   TNombreLetraONumero nl = new TNombreLetraONumero();
   //Separar por letra la SD y buscar el nombre por letra y número
   if(!cSD.equals("")){
     cLetra1 = cSD.substring(0,1);
     cNomL1  = nl.getNombreLetraONumero(cSD.substring(0,1));
     cLetra2 = cSD.substring(1,2);
     cNomL2  = nl.getNombreLetraONumero(cSD.substring(1,2));
     cLetra3 = cSD.substring(2,3);
     cNomL3  = nl.getNombreLetraONumero(cSD.substring(2,3));
     cLetra4 = cSD.substring(3,4);
     cNomL4  = nl.getNombreLetraONumero(cSD.substring(3,4));
     cLetra5 = cSD.substring(4,5);
     cNomL5  = nl.getNombreLetraONumero(cSD.substring(4,5));
   }

   sbBuscaAdic.append("[dtFechasol]|[iNumSolicitud]|[cPropietarioOPoseedor]|[dtFechaReporteInspeccion]|[cNombreEmbarcacion]|")
              .append("[cTipoServicio]|[cUAB]|[cUAN]|[cPesoMuerto]|[cEslora]|[cPuntal]|[cNavegacion]|[cManga]|")
              .append("[cLetra1]|[cLetra2]|[cLetra3]|[cLetra4]|[cLetra5]|")
              .append("[cNomL1]|[cNomL2]|[cNomL3]|[cNomL4]|[cNomL5]|")
              .append("[cNombrePuerto]|[cTipoEmbarcacion]|[cAutoridadEjec]|[cMatricula]|[cFechaEmision]|")
              .append("[CNOMCONSUL]|[CDIRCONSUL]|[CDSCPUERTO]|[cPaisAband]|[cPtoDesp]|")
              .append("[cPaisDesp]|[tsacta]|[dtActa]|[hActa]|[CCAPITANEMB]|[CJEFEMAQUINAS]|[CPARTICIPANTES]|")
              .append("[COFICIOAUTORIZACION]|[cSolicitante]|[cOficinaMatricula]|[CCALLEYNO]|[CCOLONIA]|[CCODPOSTAL]|[CTELEFONO]|")
              .append("[cPaisMatricula]|[cEntidadMatricula]|[cRepresentante]|[cSenialdist]|[CNUMOMI]|[CENTIDFED]|[CMUNICIPIO]|");
   sbReemplazaAdic.append(fechaSolicitud+"|"+cNumSol+"|"+cProp+"|"+cFechaInsp+"|"+cNomEmb+"|")
                  .append(cTipoServ+"|"+cUAB+"|"+cUAN+"|"+cPesoMuerto+"|"+cEslora+"|")
                  .append(cPuntal+"|"+cTipoNaveg+"|"+cManga+"|")
                  .append(cLetra1+"|"+cLetra2+"|"+cLetra3+"|"+cLetra4+"|"+cLetra5+"|")
                  .append(cNomL1+"|"+cNomL2+"|"+cNomL3+"|"+cNomL4+"|"+cNomL5+"|")
                  .append(cPuerto+"|"+cTipoEmb+"|"+cAutEjec+"|"+cMatricula+"|"+cFechaAbanDimi+"|")
                  .append(CNOMCONSUL+"|"+CDIRCONSUL+"|"+CDSCPUERTO+"|"+cPaisAband+"|"+cPtoDesp+"|")
                  .append(cPaisDesp+"|"+tsacta+"|"+dtActa+"|"+hActa+"|"+CCAPITANEMB+"|"+CJEFEMAQUINAS+"|"+CPARTICIPANTES+"|")
                  .append(COFICIOAUTORIZACION+"|"+cSolicitante+"|"+cOficinaMatricula+"|"+CCALLEYNO+"|"+CCOLONIA+"|"+CCODPOSTAL+"|"+CTELEFONO+"|")
                  .append(cPaisMatricula+"|"+cEntidadMatricula+"|"+cRepresentante+"|"+cSenialdist+"|"+CNUMOMI+"|"+CENTIDFED+"|"+CMUNICIPIO+"|");



    return new TDGeneral().generaOficioWord("0",
                                    Integer.parseInt(cCveOficina),Integer.parseInt(cCveDepto),
                                    0,0,
                                    1,1,1,
                                    "","",
                                    "", "",
                                    true,"cCuerpo",vcCuerpo,
                                    true, vcCopiasPara,
                                    sbBuscaAdic, sbReemplazaAdic);

  }


  public Vector genOficioSD(String cValores) throws Exception{
    Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    TFechas tFecha = new TFechas();
    //cValores trae los siguientes valores: 0-No. de Solicitud, 1-Nombre Embarcación, 2-Matrícula, 3-Señal Distintiva,
    //4-UAB, 5-UAN, 6-Puerto abanderamiento, 7-Propietario, 8-Embarcación o Artefacto,
    //9-Fecha de la solicitud
    String[] aValoresRecibidos = cValores.split("¬");

    String cNoSolicitud = aValoresRecibidos[0];
    String cNomEmb = aValoresRecibidos[1];
    String cMat = aValoresRecibidos[2];
    String cSD = aValoresRecibidos[3];
    String cUAB = aValoresRecibidos[4];
    String cUAN = aValoresRecibidos[5];
    String cPtoAban = aValoresRecibidos[6];
    String cProp = aValoresRecibidos[7];
    String cEmbOArt = aValoresRecibidos[8];
    String cFechaSol = aValoresRecibidos[9];
    String cCveOficina  = aValoresRecibidos[11];
    String cCveDepto  = aValoresRecibidos[12];
    String cFechaAsign = tFecha.getDateSPN(tFecha.getDateSQL(aValoresRecibidos[13]));
    String cNomL1 = "", cNomL2 = "",cNomL3 = "",cNomL4 = "",cNomL5 = "", cLetra1="",cLetra2="",cLetra3="",cLetra4="",cLetra5="";

    TNombreLetraONumero nl = new TNombreLetraONumero();
    //Separar por letra la SD y buscar el nombre por letra y número
    if(!cSD.equals("")){
      cLetra1 = cSD.substring(0,1);
      cNomL1  = nl.getNombreLetraONumero(cSD.substring(0,1));
      cLetra2 = cSD.substring(1,2);
      cNomL2  = nl.getNombreLetraONumero(cSD.substring(1,2));
      if(cSD.length()>3){
        cLetra3 = cSD.substring(2,3);
        cNomL3 = nl.getNombreLetraONumero(cSD.substring(2,3));
      }
      else{
        cLetra3 = "";
        cNomL3 = "";
      }
      if(cSD.length()>3){
        cLetra4 = cSD.substring(3,4);
        cNomL4 = nl.getNombreLetraONumero(cSD.substring(3,4));
      }
      else{
        cLetra4 = "";
        cNomL4 = "";
      }

      if(cSD.length()>4){
        cLetra5 = cSD.substring(4,5);
        cNomL5 = nl.getNombreLetraONumero(cSD.substring(4,5));
      }
      else{
        cLetra5 = "";
        cNomL5 = "";
      }
    }

    sbBuscaAdic.append("[cNumSolicitud]|[cFechaSol]|[cEmbOPlataf]|[cNombreEmb]|[cUAB]|[cUAN]|")
               .append("[cMatricula]|[cPuertoAband]|[cSolicitante]|")
               .append("[cLetra1]|[cLetra2]|[cLetra3]|[cLetra4]|[cLetra5]|")
               .append("[cNomL1]|[cNomL2]|[cNomL3]|[cNomL4]|[cNomL5]|[cFechaEmision]|");
    sbReemplazaAdic.append(cNoSolicitud+"|"+cFechaSol+"|"+cEmbOArt+"|"+cNomEmb+"|"+cUAB+"|"+cUAN+"|")
                   .append(cMat+"|"+cPtoAban+"|"+cProp+"|")
                   .append(cLetra1+"|"+cLetra2+"|"+cLetra3+"|"+cLetra4+"|"+cLetra5+"|")
                   .append(cNomL1+"|"+cNomL2+"|"+cNomL3+"|"+cNomL4+"|"+cNomL5+"|"+cFechaAsign+"|");

     return new TDGeneral().generaOficioWord("0",
                                     Integer.parseInt(cCveOficina),Integer.parseInt(cCveDepto),
                                     0,0,
                                     1,1,1,
                                     "","",
                                     "", "",
                                     true,"cCuerpo",vcCuerpo,
                                     true, vcCopiasPara,
                                     sbBuscaAdic, sbReemplazaAdic);

  }

  private Vector buscaYRegresaDato(String cQuery)  throws Exception{
    Vector vRegs = new Vector();

    try{
      vRegs = super.FindByGeneric("", cQuery , dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }


    return vRegs;
  }

  public StringBuffer genSelloInscripcion(String cValores)  throws Exception{
    Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    TWord tWord = new TWord();
    TFechas tFecha = new TFechas();
    //cValores trae los siguientes valores 0-dtRegistro, 1-cDscDocumento,2-iFolioRPMN,3-iPartida,4-iImporte,
    //5-crefAlfaNumerica, 6-dtPago, 7-hdDscConcepto, 8-Ramo, 9-Registrador, 10-Municipio Oficina, 11-Entidad Oficina
    //12-Cve Municipio Oficina, 13-Cve Entidad Oficina
    String[] aValoresRecibidos = cValores.split("=");

    String cFechaRegistro = tFecha.getDateSPN(tFecha.getDateSQL(aValoresRecibidos[0]));
    String cDscDocumento  = aValoresRecibidos[1];
    String cFolioRPMN     = aValoresRecibidos[2];
    String cPartida       = aValoresRecibidos[3];
    String cImporte       = aValoresRecibidos[4];
    String cRefAlfanum    = aValoresRecibidos[5];
    String cFechaPago     = aValoresRecibidos[6];
    String cDscConcepto   = aValoresRecibidos[7];
    String cRamo          = aValoresRecibidos[8];
    String cRegistrador   = aValoresRecibidos[9];
    String cMunicipio     = aValoresRecibidos[10];
    String cEntidad       = aValoresRecibidos[11];
    int    iCveMunOfic    = Integer.parseInt(aValoresRecibidos[12]);
    int    iCveEntOfic    = Integer.parseInt(aValoresRecibidos[13]);
    String cOficinaReg    = aValoresRecibidos[46];
    String cUsuRegistro   = aValoresRecibidos[47];
    String cEntidadReg    = aValoresRecibidos[48];
    String cMunicipioReg  = aValoresRecibidos[49];
    int iCveEntFedReg     = Integer.parseInt(aValoresRecibidos[51],10);
    int iCveMunReg     = Integer.parseInt(aValoresRecibidos[52],10);

    String[] aEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto").split("/");
    String[] aDatosEntidad;

    for (int i=0; i<aEntidadRemplazaTexto.length; i++){
      aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
      if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntOfic ){
        cMunicipio = aDatosEntidad[2];
        break;
      }
    }
    for (int i=0; i<aEntidadRemplazaTexto.length; i++){
      aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
      if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntFedReg ){
        cMunicipioReg = aDatosEntidad[2];
        break;
      }
    }


    tWord.iniciaReporte();
    tWord.comRemplaza("[dtFechaRegistro]",cFechaRegistro);
    tWord.comRemplaza("[cDscDocumento]",cDscDocumento);
    tWord.comRemplaza("[cFolioMaritimo]",cFolioRPMN);
    tWord.comRemplaza("[cRamo]",cRamo);
    tWord.comRemplaza("[iPartida]",cPartida);
    tWord.comRemplaza("[cImporte]",cImporte);
    tWord.comRemplaza("[cRefAlfaNum]",cRefAlfanum);
    //System.out.print("............................cFechaPAgo......:--"+cFechaPago+"--");
    if(cFechaPago!="" && cFechaPago!=null && cFechaPago.length()==10)
      tWord.comRemplaza("[cFechaPago]", tFecha.getDateSPN(tFecha.getDateSQL(cFechaPago)));
    else
      tWord.comRemplaza("[cFechaPago]", "");
    //tWord.comRemplaza("[cDscConcepto]",cDscConcepto);
    tWord.comRemplaza("[cMunicipio]",cMunicipio);
    tWord.comRemplaza("[cEntidad]", cEntidad);
    tWord.comRemplaza("[cRegistrador]",cRegistrador);
    tWord.comRemplaza("[cOfcinaReg]",cOficinaReg);
    tWord.comRemplaza("[cUsuRegistro]",cUsuRegistro);
    tWord.comRemplaza("[cEntidadReg]",cEntidadReg);
    tWord.comRemplaza("[cMunicipioReg]",cMunicipioReg);


     return tWord.getEtiquetas(true);

  }

  public StringBuffer genFolioMaritimoEmb(String cValores)  throws Exception{
        Vector vcCuerpo = new Vector();
        Vector vcCopiasPara = new Vector();
        StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
        TWord tWord = new TWord();
        TFechas tFecha = new TFechas();
        //0-Fecha Registro Incripción actual,1-DscDocumento,2-FolioRPMN,3-Partida
        //4-Importe,5-ref Alfanumerica,6-Fecha de pago,7-Dsc concepto
        //8-Ramo,9-Titular,10-Municipio de la oficina,11-Entidad de la ofic
        //12-Cve municipio de la ofic,13-Cve Entidad de la ofic
        //14-Nombre o Razón Social, 15-Cve Entidad persona, 16-DescEntidad persona
        //17-Cve Municipio persona, 18-DescMunicipio persona, 19-RFC,
        //20-Dsc Municipio inscripción, 21-Dsc Entidad Inscripción, 22-Cve Municipio Ins.
        //23-Cve entidad inscripcion,24-Síntesis
        //25-Propietario emb,26-Eslora,27-Arqueo Bruto,28-Arqueo Neto
        //29-Peso Muerto,30-Manga,31-Puntal,32-Matrícula,33-cEmbarcacion,34-Tipo Persona,35-Fecha de Primer Registro,
        //36-cReferencia,37-cTipoActo,38-Puerto de abanderamiento,
        //39-cve Entidad fed de matrícula, 40-cve Municipio Matrícula, 41-Dsc Ent Matricula ,42-Dsc Munic MAtrícula , 43-Puerto de Matrícula
        //44-lHistorico, 45-Fecha de ingreso, 46-Valor para q reconozca todo el arreglo



        //System.out.print("--------------------------------------------------------------------");
        String[] aValoresRecibidos = cValores.split("=");

        //for(int j = 0; j<aValoresRecibidos.length; j++)

        //System.out.print(cValores);

        /*String cNombre= aValoresRecibidos[14];
        String iCveEntidadFedPersona= aValoresRecibidos[15];
        String cDscEntidadPersona= aValoresRecibidos[16];
        String iCveMunicipioPersona= aValoresRecibidos[17];
        String cDscMunicipioPersona= aValoresRecibidos[18];
        String cRFC= aValoresRecibidos[19];*/
        String cMunicipioIns= aValoresRecibidos[20];
        String cEntidadIns= aValoresRecibidos[21];

        String dtRegistro= aValoresRecibidos[0];
        String iPartida= aValoresRecibidos[3];
        String cSintesis= aValoresRecibidos[24];
        String cPropietario= aValoresRecibidos[25];
        String dEslora= aValoresRecibidos[26];
        String dArqueoBruto= aValoresRecibidos[27];
        String dArqueoNeto= aValoresRecibidos[28];
        String dPesoMuerto= aValoresRecibidos[29];
        String dManga= aValoresRecibidos[30];
        String dPuntal= aValoresRecibidos[31];
        String cMatricula= aValoresRecibidos[32];
        String cEmbarcacion = aValoresRecibidos[33];
        String cFolioRPMN = aValoresRecibidos[2];
        String cFechaPrimerRegistro = tFecha.getDateSPN(tFecha.getDateSQL(aValoresRecibidos[35]));
        String cReferencia = aValoresRecibidos[36];
        String cPuertoAband = aValoresRecibidos[38];
        String cOficinaReg    = aValoresRecibidos[46];
        String cUsuRegistro   = aValoresRecibidos[47];
        String cEntidadReg    = aValoresRecibidos[48];
        String cMunicipioReg  = aValoresRecibidos[49];
        String cTramiteSol    = aValoresRecibidos[50];
        int iCveEntFedReg     = Integer.parseInt(aValoresRecibidos[51],10);
        int iCveMunReg     = Integer.parseInt(aValoresRecibidos[52],10);
        String cPuertoMat  = aValoresRecibidos[53];
        String cDscPuerto  = aValoresRecibidos[54];


        int iCveEntFedMatric = 0, iCveEntIns = 0;
        if(!aValoresRecibidos[39].equals(""))
          iCveEntFedMatric = Integer.parseInt(aValoresRecibidos[39]);

        if(!aValoresRecibidos[23].equals(""))
          iCveEntIns = Integer.parseInt(aValoresRecibidos[23]);

        String cDscEntFedMatric = aValoresRecibidos[41];
        String cDscMunicMatric = aValoresRecibidos[42];
        int iHistorico = Integer.parseInt(aValoresRecibidos[44]);

        String[] aEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto").split("/");
        String[] aDatosEntidad;

        for (int i=0; i<aEntidadRemplazaTexto.length; i++){
          aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
          if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntFedReg ){
            cMunicipioReg = aDatosEntidad[2];
            break;
          }
        }


        for (int i=0; i<aEntidadRemplazaTexto.length; i++){
          aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
          if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntFedMatric ){
            cDscMunicMatric = aDatosEntidad[2];
            break;
          }
        }

        for (int i=0; i<aEntidadRemplazaTexto.length; i++){
          aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
          if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntIns ){
            cMunicipioIns = aDatosEntidad[2];
            break;
          }
        }


        String cMunEnt="";
        StringTokenizer st = new StringTokenizer(cMunicipioIns);
        while (st.hasMoreTokens()) {
           String cToken = st.nextToken();
           if(this.fEncuentraPalabra(cToken))
             cMunEnt += " " + cToken.toLowerCase();
           else
             cMunEnt += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }
        if(cEntidadIns.length()>2){
          st = new StringTokenizer(cEntidadIns);
          cMunEnt += ", ";
          while (st.hasMoreTokens()) {
             String cToken = st.nextToken();
             cMunEnt += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
          }

        }

        String cMunReg="";
        StringTokenizer stMunReg = new StringTokenizer(cMunicipioReg);
        while (stMunReg.hasMoreTokens()) {
           String cToken = stMunReg.nextToken();
           if(this.fEncuentraPalabra(cToken))
             cMunReg += " " + cToken.toLowerCase();
           else
             cMunReg += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
         }
         String cEFReg="";
         StringTokenizer stEFReg = new StringTokenizer(cEntidadReg);
         while (stEFReg.hasMoreTokens()) {
            String cToken = stEFReg.nextToken();
            if(this.fEncuentraPalabra(cToken))
              cEFReg += " " + cToken.toLowerCase();
            else
              cEFReg += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
         }




        String cMunEntReg="";
        String cDescPuerto2="";
        StringTokenizer stReg = new StringTokenizer(cOficinaReg);
        StringTokenizer stReg2 = new StringTokenizer(cDscPuerto);
        while (stReg.hasMoreTokens()) {
           String cToken = stReg.nextToken();
           cMunEntReg += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();           
        }
        
        while (stReg2.hasMoreTokens()) {
            String cToken = stReg2.nextToken();
            cDescPuerto2 += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();           
         }
        
        //cFechaPrimerRegistro = cMunEnt + ", " + cFechaPrimerRegistro;
        cFechaPrimerRegistro = cDescPuerto2 + ", " + cFechaPrimerRegistro;

        tWord.iniciaReporte();
        tWord.comRemplaza("[cNombre]",cEmbarcacion);
        tWord.comRemplaza("[cPropietario]",cPropietario);
        tWord.comRemplaza("[cEslora]",dEslora);
        tWord.comRemplaza("[cManga]",dManga);
        tWord.comRemplaza("[cPuntal]",dPuntal);
        tWord.comRemplaza("[cTRN]",dArqueoNeto);
        tWord.comRemplaza("[cTRB]",dArqueoBruto);
        tWord.comRemplaza("[cMatricula]",cMatricula);
        tWord.comRemplaza("[cFechaReg]",aValoresRecibidos[45].replaceAll("/","-"));
        tWord.comRemplaza("[iPartida]",iPartida);
        tWord.comRemplaza("[cFol]",cFolioRPMN);
        tWord.comRemplaza("[cFechaPrimeraIns]",cFechaPrimerRegistro);
        //tWord.comRemplaza("[cPuertoMatricula]",aValoresRecibidos[42]);
        tWord.comRemplaza("[cPuertoMatricula]",cPuertoMat);//cDscMunicMatric+", "+cDscEntFedMatric);

        String cFechaPago = aValoresRecibidos[6];
        if(cFechaPago!="" && cFechaPago!=null && cFechaPago.length()==10){
          cFechaPago = tFecha.getDateSPN(tFecha.getDateSQL(cFechaPago));
          cSintesis += "  Derechos ·" + aValoresRecibidos[4] +" Registro de caja No. " + aValoresRecibidos[5] + " de fecha " +cFechaPago;
        }else{
          if(cTramiteSol.equals("12"))
            cSintesis += " Exento del pago de derechos conforme al artículo 163 de la Ley Federal de Derechos";
        }

        cSintesis +=". "+cMunReg+", "+cEFReg+" a "+tFecha.getFechaLetras(tFecha.getDateSQL(dtRegistro)).toLowerCase();
        tWord.comRemplazaVerifica("[cSintesis]",cSintesis.replaceAll("##","·").replace('·','$'),true);
        tWord.comRemplaza("[cRefer]",cReferencia);

         return tWord.getEtiquetas(true);

  }


  public StringBuffer genFolioMaritimoEmp(String cValores)  throws Exception{
        TWord tWord = new TWord();
        TFechas tFecha = new TFechas();
        //0-Fecha Registro Incripción actual,1-DscDocumento,2-FolioRPMN,3-Partida
        //4-Importe,5-ref Alfanumerica,6-Fecha de pago,7-Dsc concepto
        //8-Ramo,9-Titular,10-Municipio de la oficina,11-Entidad de la ofic
        //12-Cve municipio de la ofic,13-Cve Entidad de la ofic
        //14-Nombre o Razón Social, 15-Cve Entidad persona, 16-DescEntidad persona
        //17-Cve Municipio persona, 18-DescMunicipio persona, 19-RFC,
        //20-Dsc Municipio inscripción, 21-Dsc Entidad Inscripción, 22-Cve Municipio Ins.
        //23-Cve entidad inscripcion,24-Síntesis
        //25-Propietario emb,26-Eslora,27-Arqueo Bruto,28-Arqueo Neto
        //29-Peso Muerto,30-Manga,31-Puntal,32-Matrícula,33-cEmbarcacion,34-Tipo Persona,35-Fecha de Primer Registro,
        //36-cReferencia,37-cTipoActo,38-Puerto de abanderamiento,,
        //39-cve Entidad fed de matrícula, 40-cve Municipio Matrícula, 41-Dsc Ent Matricula ,42-Dsc Munic MAtrícula , 43-Puerto de Matrícula
        //44-lHistorico, 45-Fecha de ingreso, 46-Valor para q reconozca todo el arreglo

        String[] aValoresRecibidos = cValores.split("=");
        String cNombre= aValoresRecibidos[14];
        int iCveEntidadPersona= Integer.parseInt(aValoresRecibidos[15]);
        String cDscEntidadPersona= aValoresRecibidos[16];
        int iCveMunicipioPersona= Integer.parseInt(aValoresRecibidos[17]);
        String cDscMunicipioPersona= aValoresRecibidos[18];
        String cRFC= aValoresRecibidos[19];
        String cMunicipioIns= aValoresRecibidos[20];
        String cEntidadIns= aValoresRecibidos[21];
        int iCveMunIns = Integer.parseInt(aValoresRecibidos[22]);
        int iCveEntIns = Integer.parseInt(aValoresRecibidos[23]);
        String dtRegistro= aValoresRecibidos[0];
        String iPartida= aValoresRecibidos[3];
        String cSintesis= aValoresRecibidos[24];

        String cFolioRPMN = aValoresRecibidos[2];
        String cTipoPersona = aValoresRecibidos[34];
        String cFechaPrimerIns = tFecha.getDateSPN(tFecha.getDateSQL(aValoresRecibidos[35]));
        String cReferencia = aValoresRecibidos[36];
        String cTipoActo = aValoresRecibidos[37];
        String cOficinaReg    = aValoresRecibidos[46];
        String cUsuRegistro   = aValoresRecibidos[47];
        String cEntidadReg    = aValoresRecibidos[48];
        String cMunicipioReg  = aValoresRecibidos[49];
        String cTramiteSol    = aValoresRecibidos[50];
        int iCveEntFedReg     = Integer.parseInt(aValoresRecibidos[51],10);
        int iCveMunReg     = Integer.parseInt(aValoresRecibidos[52],10);


        int iHistorico = Integer.parseInt(aValoresRecibidos[44]);

        String[] aEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto").split("/");
        String[] aDatosEntidad;

        for (int i=0; i<aEntidadRemplazaTexto.length; i++){
          aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
          if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntidadPersona ){
            cDscMunicipioPersona = aDatosEntidad[2];
            break;
          }
        }

        for (int i=0; i<aEntidadRemplazaTexto.length; i++){
          aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
          if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntFedReg ){
            cMunicipioReg = aDatosEntidad[2];
            break;
          }
        }



        for (int i=0; i<aEntidadRemplazaTexto.length; i++){
          aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
          if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntIns ){
            cMunicipioIns = aDatosEntidad[2];
            break;
          }
        }

        String cMunEnt="";
        StringTokenizer st = new StringTokenizer(cMunicipioIns);
        while (st.hasMoreTokens()) {
           String cToken = st.nextToken();
           cMunEnt = " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }
        if(cEntidadIns.length()>2){
          st = new StringTokenizer(cEntidadIns);
          cMunEnt += ", ";
          while (st.hasMoreTokens()) {
             String cToken = st.nextToken();
             cMunEnt += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
          }

        }
        String cMunReg="";
        StringTokenizer stMunReg = new StringTokenizer(cMunicipioReg);
        while (stMunReg.hasMoreTokens()) {
          String cToken = stMunReg.nextToken();
          if(this.fEncuentraPalabra(cToken))
            cMunReg += " " + cToken.toLowerCase();
          else
            cMunReg += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }
        String cEFReg="";
        StringTokenizer stEFReg = new StringTokenizer(cEntidadReg);
        while (stEFReg.hasMoreTokens()) {
          String cToken = stEFReg.nextToken();
          if(this.fEncuentraPalabra(cToken))
            cEFReg += " " + cToken.toLowerCase();
          else
            cEFReg += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }
        String cMunEntReg="";
        StringTokenizer stReg = new StringTokenizer(cOficinaReg);
        while (stReg.hasMoreTokens()) {
           String cToken = stReg.nextToken();
           cMunEntReg += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }


        tWord.iniciaReporte();
          tWord.comRemplaza("[cNombre]",cNombre);
        tWord.comRemplaza("[cDomicilio]",cDscMunicipioPersona+", "+cDscEntidadPersona);
        tWord.comRemplaza("[cRFC]",cRFC);
        tWord.comRemplaza("[cTipoAsiento]","[cTipoAsiento]");
        tWord.comRemplaza("[cLugarIns]",cMunEnt);
        tWord.comRemplaza("[cFechaIns]",cFechaPrimerIns);
        tWord.comRemplaza("[cFechaReg]",aValoresRecibidos[45].replaceAll("/","-"));
        tWord.comRemplaza("[cRefer]","[cRefer]");
        tWord.comRemplaza("[iPartida]",iPartida);
        tWord.comRemplaza("[cFol]",cFolioRPMN);
        String cFechaPago = aValoresRecibidos[6];
        if(cFechaPago!="" && cFechaPago.length()==10){
          cFechaPago = tFecha.getDateSPN(tFecha.getDateSQL(cFechaPago));
          cSintesis += "  Derechos ·" + aValoresRecibidos[4] +" Registro de caja No. " + aValoresRecibidos[5] + " de fecha " + cFechaPago;
        }else{
          if(cTramiteSol.equals("12"))
            cSintesis += " Exento del pago de derechos conforme al artículo 163 de la Ley Federal de Derechos";
        }
        cSintesis +=". "+cMunReg+", "+cEFReg+" a "+tFecha.getFechaLetras(tFecha.getDateSQL(dtRegistro)).toLowerCase();
        tWord.comRemplazaVerifica("[cSintesis]",cSintesis.replaceAll("##","·").replace('·','$'),true);
        tWord.comRemplaza("[cRefer]",cReferencia);
        tWord.comRemplaza("[cTipoActo]",cTipoActo);

         return tWord.getEtiquetas(true);

  }


  public Vector genCertificadoRPMN(String cValores) throws Exception{
    TWord tWord = new TWord();
    TFechas fecha = new TFechas();
    //0-Fecha Registro Incripción actual,1-DscDocumento,2-FolioRPMN,3-Partida
    //4-Importe,5-ref Alfanumerica,6-Fecha de pago,7-Dsc concepto
    //8-Ramo,9-Titular,10-Municipio de la oficina,11-Entidad de la ofic
    //12-Cve municipio de la ofic,13-Cve Entidad de la ofic
    //14-Nombre o Razón Social, 15-Cve Entidad persona, 16-DescEntidad persona
    //17-Cve Municipio persona, 18-DescMunicipio persona, 19-RFC,
    //20-Dsc Municipio inscripción, 21-Dsc Entidad Inscripción, 22-Cve Municipio Ins.
    //23-Cve entidad inscripcion,24-Síntesis
    //25-Propietario emb,26-Eslora,27-Arqueo Bruto,28-Arqueo Neto
    //29-Peso Muerto,30-Manga,31-Puntal,32-Matrícula,
    //33-cEmbarcacion,34-TipoPersona,35-Fecha de Primer Registro
    //36-cReferencia,37-cTipoActo,38-Puerto de abanderamiento,
    //39-Entidad fed de matrícula, 40-Municipio Matrícula, 41-Dsc Ent Matricula ,42-Dsc Munic MAtrícula ,
    //43-Texto certificado,
    //44-Importe Pagado, 45-fecha de pago, 46-ref alfanumerica, 47-Dsc Banco
    //48-Cve Entidad Usuario sistema, 49-Dsc Entidad Usuario sistema, 50-DscMunicipio Usuario sistema
    //51-Num certificado, 52-Ejercicio certificado, 53-nom registrador
    //54-Cve Ofic expide, 55-CveDepto expide, 56-cObjeto, 57-cNomSolicitante
    //58 a 61 llave del registro RPMN, 62-Tipo de docuimento seleccionado
    //58-Valor para q reconozca todo el arreglo por si hay espacios en blanco antes de este

    //System.out.print("--------------------------------------------------------------------");
    String[] aValoresRecibidos = cValores.split("=");

    //for(int j = 0; j<aValoresRecibidos.length; j++)

    //System.out.print(cValores);
    String[] aFechaInsPartida = aValoresRecibidos[0].split(",");
    String[] aPartida = aValoresRecibidos[3].split(",");
    String[] aSintesis = aValoresRecibidos[24].split("rrrrr");
    String cFechaIns = "";
    String[] aEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto").split("/");
    String[] aDatosEntidad;
    int iEjercicioRep = 0;
    int iOficinaReg = 0;
    int iFolioReg = 0;
    int iPartidaReg = 0;

    if(!aValoresRecibidos[58].equals("")){
      iEjercicioRep = Integer.parseInt(aValoresRecibidos[58]);
      iOficinaReg = Integer.parseInt(aValoresRecibidos[59]);
      iFolioReg = Integer.parseInt(aValoresRecibidos[60]);
      iPartidaReg = Integer.parseInt(aValoresRecibidos[61]);
    }

    int iCveEntidadIns = aValoresRecibidos[13].equals("")?0:Integer.parseInt(aValoresRecibidos[13]);
    int iCveEntidadUsr = Integer.parseInt(aValoresRecibidos[48]);
    String cDscMunicipio = aValoresRecibidos[11].equals("")?"--------------":aValoresRecibidos[11];
    String cDscMunicipioUsr = aValoresRecibidos[50];
    String cNumOfic = "";
    if(!aValoresRecibidos[51].equals("") && Integer.parseInt(aValoresRecibidos[51])>0){
      int iNumOfic = Integer.parseInt(aValoresRecibidos[51]);
      if(iNumOfic < 10)
        cNumOfic = "000" + iNumOfic;
      else if(iNumOfic < 100)
        cNumOfic = "00" + iNumOfic;
      else if(iNumOfic < 1000)
        cNumOfic = "0" + iNumOfic;
      else
        cNumOfic = "" + iNumOfic;
      cNumOfic += "/" + aValoresRecibidos[52].substring(2);
    } else cNumOfic = "S/N";
    for (int i=0; i<aEntidadRemplazaTexto.length; i++){
      aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
      if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntidadIns ){
        cDscMunicipio = aDatosEntidad[2];
        break;
      }
    }
    for (int i=0; i<aEntidadRemplazaTexto.length; i++){
      aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
      if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntidadUsr ){
        cDscMunicipioUsr = aDatosEntidad[2];
        break;
      }
    }


    tWord.iniciaReporte();

    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    String cFechaPago = "";
    sbBuscaAdic.append("[cFolio]|[cNombre]|[cSolicitante]|[cEmboEmpresa]|[cFraseTipoCertif]|")
               .append("[cImporte]|[cFechaPago]|[cBanco]|[cRefAlfanumerica]|[cMunicipioEmision]|")
               .append("[cEntidadEmision]|[cFechaEmision]|[iNumCertificado]|[cRegistrador]|[cObjeto]|");
    if(!aValoresRecibidos[45].equals(""))
        cFechaPago = fecha.getDateSPN(fecha.getDateSQL(aValoresRecibidos[45]));
    sbReemplazaAdic.append(aValoresRecibidos[2]+"|"+aValoresRecibidos[14]+"|"+aValoresRecibidos[57]+"|"+aValoresRecibidos[8]+"|"+aValoresRecibidos[43]+"|")
                   .append(aValoresRecibidos[44]+"|"+cFechaPago+"|"+aValoresRecibidos[47]+"|"+aValoresRecibidos[46]+"|"+cDscMunicipioUsr+"|")
                   .append(aValoresRecibidos[49]+"|"+fecha.getDateSPN(fecha.TodaySQL())+"|"+cNumOfic+"|"+aValoresRecibidos[53]+"|"+aValoresRecibidos[56]+"|");


    tWord.comEligeTabla("partidas");
    tWord.comAgregaRenglonTabla(aPartida.length+1);
    tWord.comEligeTabla("partidas");
    if(iEjercicioRep>0){
      String cPartidas =
          "SELECT IPARTIDA,DTREGISTRO,OL.CDSCOBSLARGA FROM MYRRPMN RP " +
          "LEFT JOIN GRLOBSERVALARGA OL " +
          "   ON OL.IEJERCICIOOBSLARGA=RP.IEJERCICIOOBSLARGA " +
          "   AND OL.ICVEOBSLARGA=RP.ICVEOBSLARGA " +
          "WHERE RP.IEJERCICIOINS= " + iEjercicioRep +
          "  AND RP.ICVEOFICINA= " + iOficinaReg +
          "  AND RP.IFOLIORPMN= " + iFolioReg +
          "  AND RP.IPARTIDA= " + iPartidaReg;
      Vector vPartidas = this.FindByGeneric("",cPartidas,dataSourceName);

      if(aValoresRecibidos[0] != "" && aValoresRecibidos[0] != null &&
         aValoresRecibidos[0].length() > 0)
        for(int conPart = 0;conPart < aPartida.length;conPart++){
          cFechaIns = fecha.getDateSPN(fecha.getDateSQL(aFechaInsPartida[
              conPart]));
          tWord.comDespliegaCelda("");
          tWord.comDespliegaCelda(aPartida[conPart] + ",o de fecha " +
                                  cFechaIns + "   " + aSintesis[conPart]);
          tWord.comDespliegaCelda("");
          tWord.comDespliegaCelda("");
        }
      tWord.comEligeTabla("ofic");
      tWord.comDespliegaCelda("");
      tWord.comDespliegaCelda(cDscMunicipio + ", " + aValoresRecibidos[11]);
    }

    Vector vDatos = new TDGeneral().generaOficioWord("0",
                                    Integer.parseInt(aValoresRecibidos[54]),Integer.parseInt(aValoresRecibidos[55]),
                                    0,0,
                                    1,1,1,
                                    "","",
                                    "", "",
                                    true,"cCuerpo",new Vector(),
                                    true, new Vector(),
                                    sbBuscaAdic, sbReemplazaAdic);
    Vector vDatosTemp = (Vector)vDatos.get(1);
    Vector vDatosTablas = tWord.getDatosTablas();
    for(int iCont=0;iCont<vDatosTablas.size();iCont++){
      vDatosTemp.add(vDatosTablas.get(iCont));
    }
    vDatos.add(1, vDatosTemp);

    return vDatos;

  }

/*------------------------------------------------------*/
  public StringBuffer genCertificadoLiteralesDocs(String cValores) throws Exception{
    TWord tWord = new TWord();
    TFechas fecha = new TFechas();
    //0-Fecha Registro Incripción actual,1-DscDocumento,2-FolioRPMN,3-Partida
    //4-Importe,5-ref Alfanumerica,6-Fecha de pago,7-Dsc concepto
    //8-Ramo,9-Titular,10-Municipio de la oficina,11-Entidad de la ofic
    //12-Cve municipio de la ofic,13-Cve Entidad de la ofic
    //14-Nombre o Razón Social, 15-Cve Entidad persona, 16-DescEntidad persona
    //17-Cve Municipio persona, 18-DescMunicipio persona, 19-RFC,
    //20-Dsc Municipio inscripción, 21-Dsc Entidad Inscripción, 22-Cve Municipio Ins.
    //23-Cve entidad inscripcion,24-Síntesis
    //25-Propietario emb,26-Eslora,27-Arqueo Bruto,28-Arqueo Neto
    //29-Peso Muerto,30-Manga,31-Puntal,32-Matrícula,
    //33-cEmbarcacion,34-TipoPersona,35-Fecha de Primer Registro
    //36-cReferencia,37-cTipoActo,38-Puerto de abanderamiento,
    //39-Entidad fed de matrícula, 40-Municipio Matrícula, 41-Dsc Ent Matricula ,42-Dsc Munic MAtrícula ,
    //43-Texto certificado,
    //44-Importe Pagado, 45-fecha de pago, 46-ref alfanumerica, 47-Dsc Banco
    //48-Cve Entidad Usuario sistema, 49-Dsc Entidad Usuario sistema, 50-DscMunicipio Usuario sistema
    //51-Num certificado, 52-Ejercicio certificado, 53-nom registrador
    //54-Cve Ofic expide, 55-CveDepto expide,  56-Num Solicitud
    //57-Valor para q reconozca todo el arreglo por si hay espacios en blanco antes de este


    String[] aValoresRecibidos = cValores.split("=");
    //for(int j = 0; j<aValoresRecibidos.length; j++)
    //System.out.print("aValoresRecibidos.length........"+aValoresRecibidos.length);
    //System.out.print(cValores);
    String[] aFechaInsPartida = aValoresRecibidos[0].split(",");
    String[] aPartida = aValoresRecibidos[3].split(",");
    //String[] aSintesis = aValoresRecibidos[24].split("rrrrr");
    String cFechaIns = "";
    String[] aEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto").split("/");
    String[] aDatosEntidad;
    String cRamo = aValoresRecibidos[8];
    String[] aEntidadIns = aValoresRecibidos[23].split(",");
    int iCveEntidadIns = Integer.parseInt(aEntidadIns[0]);
    String cDscEntidad = aValoresRecibidos[21];
    int iCveEntidadUsr = Integer.parseInt(aValoresRecibidos[48]);
    String cDscMunicipio = aValoresRecibidos[20];
    String cDscMunicipioUsr = aValoresRecibidos[50];
    int iNumOfic = Integer.parseInt(aValoresRecibidos[51]);
    String cNumOfic = "";

    if(iNumOfic<10)
      cNumOfic = "000"+iNumOfic;
    else if (iNumOfic<100)
      cNumOfic = "00"+iNumOfic;
    else if (iNumOfic<1000)
      cNumOfic = "0"+iNumOfic;
    else
      cNumOfic = ""+iNumOfic;
    cNumOfic +=  "/" + aValoresRecibidos[52].substring(2) ;

    for (int i=0; i<aEntidadRemplazaTexto.length; i++){
      aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
      if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntidadIns ){
        cDscMunicipio = aDatosEntidad[2];
        break;
      }
    }
    for (int i=0; i<aEntidadRemplazaTexto.length; i++){
      aDatosEntidad = aEntidadRemplazaTexto[i].split(",");
      if(Integer.parseInt(aDatosEntidad[1],10) == iCveEntidadUsr ){
        cDscMunicipioUsr = aDatosEntidad[2];
        break;
      }
    }

    tWord.iniciaReporte();

    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    String cFechaPago = "";
    String cPartidas = "";

    for(int conPart=0;conPart<aPartida.length;conPart++){
      if (cPartidas=="") cPartidas = aPartida[conPart];
      else cPartidas += ", "+aPartida[conPart];
    }

    if(!aValoresRecibidos[45].equals(""))
        cFechaPago = fecha.getDateSPN(fecha.getDateSQL(aValoresRecibidos[45]));

    tWord.comRemplaza("[cNumFolio]",aValoresRecibidos[2]);
    tWord.comRemplaza("[cNombre]",aValoresRecibidos[14]);
    tWord.comRemplaza("[cSolicitante]",aValoresRecibidos[14]);
    tWord.comRemplaza("[cEmboEmpresa]",aValoresRecibidos[8]);
    tWord.comRemplaza("[cFraseTipoCertif]",aValoresRecibidos[43]);
    tWord.comRemplaza("[cImporte]",aValoresRecibidos[44]);
    tWord.comRemplaza("[cFechaPago]",cFechaPago);
    tWord.comRemplaza("[cBanco]",aValoresRecibidos[47]);
    tWord.comRemplaza("[cRefAlfanumerica]",aValoresRecibidos[46]);
    tWord.comRemplaza("[cMunicipioEmision]",cDscMunicipioUsr);
    tWord.comRemplaza("[cEntidadEmision]",aValoresRecibidos[49]);
    tWord.comRemplaza("[cFechaEmision]",fecha.getDateSPN(fecha.TodaySQL()));
    tWord.comRemplaza("[iNumCertificado]",cNumOfic);
    tWord.comRemplaza("[cRegistrador]",aValoresRecibidos[53]);
    tWord.comRemplaza("[cPartidas]",cPartidas);
    tWord.comRemplaza("[cRamo]",cRamo);
    tWord.comRemplaza("[cMunicipioIns]",cDscMunicipio);
    tWord.comRemplaza("[cEntidadIns]",cDscEntidad.split(",")[0]);
    tWord.comRemplaza("[cNumSolicitud]",aValoresRecibidos[56]);

    return tWord.getEtiquetas(true);

  }

/*------------------------------------------------------*/


/*******************************************---------------------------*/
  public Vector genPermisoServicios(String cValores)  throws Exception{

         StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
         TWord tWord = new TWord();
         TFechas tFecha     = new TFechas();
         Vector vRegsM      = new Vector();
         Vector vRegsE      = new Vector();
         Vector vRegsP      = new Vector();
         Vector vRegsPS     = new Vector();
         Vector cLugares    = new Vector();
         Vector vRegsMat    = new Vector();
         Vector vRequisitos = new Vector();

          String[] aValRec = cValores.split("=");

          String cDir=aValRec[10];
          String cRutas="";
          String cLugar="";
          int numMotores=0;
          TVDinRep vDatosE=new TVDinRep();
          TVDinRep vDatosM=new TVDinRep();
          TVDinRep vDatosP=new TVDinRep();
          TVDinRep vDatosPS=new TVDinRep();
          TVDinRep vDatosMat=new TVDinRep();
          String[] anioconst=null;

          String cFechaActual=tFecha.getDateSPN(tFecha.TodaySQL());


          //0-cve persona, 1-ejercicio sol, 2-num solicitud
          //3-cve puerto, 4-folio rpmn, 5-cve vehiculo
          //6-nom embarcación, 7-cve matrícula, 8-anios vigencia
          //9-nom persona, 10-dir persona, 11-servicio
          //12-arqueo bruto, 13-eslora, 14-arqueo neto
          //15-manga, 16-peso muerto, 17-puntal, 18-lugares
          //19-rep legal, 20-ejercicio permiso, 21-consec permiso
          //22-lugares, 24-cvetipo permiso
//System.out.print(cValores);
           String cMotor=  "SELECT "+
           "  VEHVehiculo.iCveVehiculo, "+
           "  VEHMotor.iConsecutivo, "+
           "  VEHMotor.iCveMarcaMotor, "+
           "  VEHMarcaMotor.cDscMarcaMotor, "+
           "  VEHMotor.cNumSerie, "+
           "  VEHMotor.iPotencia, " +
           "  VEHMotor.iCveUniMedPotencia, " +
           "  VEHUnidadMedida.cDscUnidadMedida, " +
           "  VEHMotor.iRevXMinuto, " +
           "  VEHMotor.dValTironPuntoFijo, " +
           "  VEHMotor.iCveUnidadMedTiron, " +
           "  UniMedTiron.cDscUnidadMedida, " +
           "  VEHMotor.cCombustible " +
           "  FROM VEHVehiculo " +
           "  JOIN VEHMotor ON VEHVehiculo.iCveVehiculo = VEHMotor.iCveVehiculo " +
           "  JOIN VEHMarcaMotor ON VEHMotor.iCveMarcaMotor = VEHMarcaMotor.iCveMarcaMotor " +
           "  JOIN VEHUnidadMedida ON VEHMotor.iCveUniMedPotencia = VEHUnidadMedida.iCveUnidadMedida " +
           "  JOIN VEHUnidadMedida UniMedTiron ON VEHMotor.iCveUnidadMedTiron = UniMedTiron.iCveUnidadMedida " +
           " JOIN VEHEmbarcacion ON VEHVehiculo.iCveVehiculo=VEHEmbarcacion.iCveVehiculo " +
           " JOIN VEHTipoEmbarcacion ON VEHEmbarcacion.iCveTipoEmbarcacion = VEHTipoEmbarcacion.iCveTipoEmbarcacion " +
           "  WHERE VEHEmbarcacion.iCveVehiculo="+aValRec[5];


       String cEmbarc= "SELECT  distinct (VEHEmbarcacion.iCveVehiculo),VEHEmbarcacion.cNomEmbarcacion, "+
               "VEHEmbarcacion.iCveTipoEmbarcacion,VEHTipoEmbarcacion.cDscTipoEmbarcacion, "+
               "VEHEmbarcacion.iNumTanques,VEHEmbarcacion.iTripulacionMin, VEHEmbarcacion.iTripulacionMax,iPotenciaTotal, "+
               "  MYRSenalDistint.cGrupoSenalFijo || MYRSenalDistint.cGrupoSenalConsec AS cSenalDist, " +
               "MYRRPMN.iPartida,MYRRPMN.iEjercicio,MYRRPMN.dtRegistro,dtConstruccion, "+//11
               "neto.cdscunidadmedida as unimedneto,bruto.cdscunidadmedida as unimedbruto,VEHVEHICULO.cMatricula,cDscTipoNavega "+
               "FROM VEHEmbarcacion "+
               "JOIN VEHTipoEmbarcacion ON VEHEmbarcacion.iCveTipoEmbarcacion = VEHTipoEmbarcacion.iCveTipoEmbarcacion "+
               "JOIN VEHTipoServicio ON VEHEmbarcacion.iCveTipoServ = VEHTipoServicio.iCveTipoServ "+
               "LEFT JOIN GRLPersona ON VEHEmbarcacion.iCvePropietario = GRLPersona.iCvePersona "+
               "JOIN VEHVehiculo ON VEHEmbarcacion.iCveVehiculo = VEHVehiculo.iCveVehiculo "+
               "  LEFT JOIN MYRRegSenalDist ON VEHEmbarcacion.iCveVehiculo = MYRRegSenalDist.iCveVehiculo AND MYRRegSenalDist.dtBajaSenal IS NULL " +
               "  LEFT JOIN MYRSenalDistint ON MYRRegSenalDist.iConsecutivSenal = MYRSenalDistint.iConsecutivSenal " +
               " LEFT JOIN MYREmbarcacion ON VEHEmbarcacion.iCveVehiculo = MYREmbarcacion.iCveVehiculo "+
               " LEFT JOIN MYRRPMN ON MYREmbarcacion.ICVEOFICINA = MYRRPMN.ICVEOFICINA AND MYREMBARCACION.IFOLIORPMN = MYRRPMN.IFOLIORPMN AND MYREMBARCACION.IPARTIDA = MYRRPMN.IPARTIDA "+
               "LEFT JOIN VEHUnidadMedida neto ON VEHEmbarcacion.iUniMedArqueoNeto = neto.iCveUnidadMedida "+
               "LEFT JOIN VEHUnidadMedida bruto ON VEHEmbarcacion.iUniMedArqueoBruto = bruto.iCveUnidadMedida "+
               " LEFT JOIN VEHTipoNavegacion ON VEHEmbarcacion.iCveTipoNavega = VEHTipoNavegacion.iCveTipoNavega "+
               "WHERE VEHEmbarcacion.iCveVehiculo="+aValRec[5];

       String cPuerto="SELECT MYRPermisosServ.iCvePuerto, cDscPuerto from MYRPermisosServ "+
                      " JOIN GrlPuerto on GrLPuerto.iCvePuerto=MYRPermisosServ.iCvePuerto "+
                      " WHERE MYRPermisosServ.iCvePuerto= "+aValRec[3];

       String cPermisoServ="SELECT cDscLugar,cDscTipoPermiso,cDscCapitania   "+
           "from MYRPermisosServ "+
           "JOIN MYRTipoPermiso on MYRPermisosServ.iCveTipoPermiso=MYRTipoPermiso.iCveTipoPermiso "+
           "JOIN MYRLugaresOpera on MYRPermisosServ.iConsecPermiso= MYRLugaresOpera.iConsecPermiso "+
           " AND MYRPermisosserv.iEjercicioPermiso= MYRLugaresOpera.iEjercicioPermiso "+
           " JOIN MYRCapitania on MYRPermisosServ.iCveOficExtiende=MYRCapitania.iCveOficina "+
           "  WHERE MYRPermisosServ.iConsecPermiso="+aValRec[21]+
           " and MYRPermisosServ.iEjercicioPermiso= "+aValRec[20]+
           " and MYRTipoPermiso.lActivo=1  and MYRLugaresOpera.lActivo=1";

       String cMatricula= " SELECT distinct MYRMatricula.iCveOficina, cDscCapitania "+
                          " from myrmatricula "+
                          " join myrembarcacionesxperm on myrmatricula.icveembarcacion=myrembarcacionesxperm.icvevehiculo "+
                          " join myrcapitania on myrmatricula.icveoficina=myrcapitania.icveoficina  "+
                          " and myrmatricula.icvecapitaniaparamat=myrcapitania.icvecapitaniaparamat " +
                         " where dtbaja is null  AND MYRMATRICULA.ICVEEMBARCACION = "+aValRec[5];

       String cRequisitos = "SELECT " +
                            "  rrt.IEJERCICIO, " +
                            "  rrt.INUMSOLICITUD, " +
                            "  rrt.ICVETRAMITE, " +
                            "  rrt.ICVEMODALIDAD, " +
                            "  rrt.ICVEREQUISITO, " +
                            "  rrt.DTRECEPCION, " +
                            "  rrt.LVALIDO, " +
                            "  r.CDSCREQUISITO, " +
                            "  r.CDSCBREVE " +
                            "FROM TRAREGREQXTRAM rrt " +
                            "JOIN TRAREQUISITO r ON r.ICVEREQUISITO = rrt.ICVEREQUISITO " +
                            "WHERE rrt.IEJERCICIO = "+aValRec[1]+" AND rrt.INUMSOLICITUD = "+ aValRec[2]+
                            " AND rrt.DTRECEPCION IS NOT null AND rrt.LVALIDO = 1 ";


        vRegsM = super.FindByGeneric("",cMotor,dataSourceName);
        vRegsE = super.FindByGeneric("",cEmbarc,dataSourceName);
        vRegsP= super.FindByGeneric("",cPuerto,dataSourceName);
        vRegsPS= super.FindByGeneric("",cPermisoServ,dataSourceName);
        vRegsMat= super.FindByGeneric("",cMatricula,dataSourceName);
        vRequisitos = super.FindByGeneric("",cRequisitos,dataSourceName);

        if(vRegsE.size()>0){
          vDatosE = (TVDinRep) vRegsE.get(0);
          anioconst=vDatosE.getString("dtConstruccion").split("-");
        }

       if(vRegsM.size()>0){
          vDatosM = (TVDinRep) vRegsM.get(0);
         numMotores=vRegsM.size();
       }
       if(vRegsP.size()>0){
          vDatosP = (TVDinRep) vRegsP.get(0);    }

        if(vRegsPS.size()>0){
          vDatosPS = (TVDinRep) vRegsPS.get(0);
         for(int i=0;i<vRegsPS.size();i++){
           vDatosPS = (TVDinRep) vRegsPS.get(i);
           if(i==0)
            cRutas=vDatosPS.getString("cDscLugar");
           else
             cRutas=cRutas+", "+vDatosPS.getString("cDscLugar");

       }
      }

      if(vRegsMat.size()>0){
               vDatosMat = (TVDinRep) vRegsMat.get(0);

            }

         tWord.iniciaReporte();
//         //System.out.print("Fecha Actual vDatosE.getString(dtRegistro): "+vDatosE.getString("dtRegistro"));
         sbBuscaAdic.append("[iPermiso]|[cPermiso]|[cAnioPermiso]|[cNumSolicitud]|[NombreSolicitante]|[NombreEmbarcacion]|")
                    .append("[NombrePuerto]|[DirSolicitante]|[FolioRPMN]|[AniosVigencia]|[cTipoEmbarcacion]|[cTipoServ]|")
                    .append("[cTrafico]|[iArqueoBruto]|[iArqueoNeto]|[iPesomuerto]|[iEslora]|[iManga]|")
                    .append("[iPuntal]|[cSenalDist]|[iNumMotores]|[iPotencia]|[iNumSerieMotor]|[iNumPasajeros]|")
                    .append("[cNomRepLegal]|[iPartida]|[iFecha]|[cMatricula]|[cPrtoMat]|[AnnioConstrucc]|")
                    .append("[iMaxPasajeros]|[cFechaActual]|[cLugar]|[OficExtiende]|[cDscLugar]|");
         sbReemplazaAdic.append(aValRec[21]+"|"+vDatosPS.getString("cDscTipoPermiso")+"|"+aValRec[20]+"|"+aValRec[2]+"|"+aValRec[9]+"|"+aValRec[6]+"|")
                        .append(vDatosP.getString("cDscPuerto")+"|"+cDir+"|"+aValRec[4]+"|"+aValRec[8]+"|"+vDatosE.getString("cDscTipoEmbarcacion")+"|"+aValRec[11]+"|")
                        .append(vDatosE.getString("cDscTipoNavega")+"|"+aValRec[12]+"|"+aValRec[14]+"|"+aValRec[16]+"|"+aValRec[13]+"|"+aValRec[15]+"|")
                        .append(aValRec[17]+"|"+vDatosE.getString("cSenalDist")+"|"+numMotores+"|"+vDatosE.getString("iPotenciaTotal")+"|"+vDatosM.getString("cNumSerie")+"|"+vDatosE.getString("iTripulacionMax")+"|")
                        .append(aValRec[19]+"|"+vDatosE.getString("iPartida")+"|"+vDatosE.getString("dtRegistro")+"|"+vDatosE.getString("cMatricula")+"|"+vDatosMat.getString("cDscCapitania")+"|"+anioconst[0]+"|")
                        .append(aValRec[8]+"|"+cFechaActual+"|"+cRutas+"|"+vDatosPS.getString("cDscCapitania")+"|"+aValRec[18]+"|");

         tWord.anexaEtiquetas(sbBuscaAdic,sbReemplazaAdic);



         if(vRequisitos.size()>0){
           tWord.comEligeTabla("cRequsitos");
           tWord.comAgregaRenglonTabla(vRequisitos.size()+1);
           String cNumReq="";
           for(int conReq=0;conReq<vRequisitos.size();conReq++){
             TVDinRep vDatos = new TVDinRep();
             vDatos = (TVDinRep) vRequisitos.get(conReq);
             cNumReq = new Integer(conReq+1).toString();
             tWord.comDespliegaCelda(cNumReq);
             tWord.comDespliegaCelda(vDatos.getString("cDscRequisito"));
           }

         }

         Vector vDatosTemp = tWord.getVectorDatos(true);;
         Vector vDatosTablas = tWord.getDatosTablas();
         for(int iCont=0;iCont<vDatosTablas.size();iCont++){
           vDatosTemp.add(vDatosTablas.get(iCont));
         }

         return vDatosTemp;
   }

   public StringBuffer genMatriculasGeneradas (String cValores)  throws Exception{
     TExcel rep = new TExcel();
     String[] aValores = cValores.split("=");
     String[] aEnt = aValores[0].split(",");
     String[] aOfi = aValores[1].split(",");
     String[] aMat = aValores[2].split(",");

     int iRenIni = 5;
     rep.iniciaReporte();
     for(int iCont=0;iCont<aEnt.length;iCont++){
       rep.comDespliega("B",iRenIni,aEnt[iCont]);
       rep.comDespliega("C",iRenIni,aOfi[iCont]);
       rep.comDespliega("D",iRenIni,aMat[iCont]);
       iRenIni++;
     }
     rep.comBordeTotal("B",5,"D",iRenIni,2);
     return rep.getSbDatos();
   }

   public StringBuffer genRepTelecomm (String cValores)  throws Exception{
     TExcel rep = new TExcel();
     //0-MMSI, 1-Nombre, 2-Domicilio, 3-Colonia, 4-Localidad
     //5-Municipio, 6-Entidad, 7-Cod Postal, 8-Correo, 9-Nombre embarcación
     //10-Tipo Emb, 11-Matrícula, 12-Pais, 13-Señal Dist, 14-UAB

     String[] aVal = cValores.split("=");
     StringBuffer sbRep = new StringBuffer();
     int iRenIni = 3;
     rep.iniciaReporte();
     rep.comDespliega("B",iRenIni,aVal[0]);
     rep.comDespliega("C",iRenIni,aVal[1]);
     rep.comDespliega("D",iRenIni,aVal[2]);
     rep.comDespliega("E",iRenIni,aVal[3]);
     rep.comDespliega("F",iRenIni,aVal[4]);
     rep.comDespliega("G",iRenIni,aVal[5]);
     rep.comDespliega("H",iRenIni,aVal[6]);
     rep.comDespliega("I",iRenIni,aVal[7]);
     rep.comDespliega("J",iRenIni,aVal[8]);
     rep.comDespliega("K",iRenIni,aVal[9]);
     rep.comDespliega("L",iRenIni,aVal[10]);
     rep.comDespliega("M",iRenIni,aVal[11]);
     rep.comDespliega("N",iRenIni,aVal[12]);
     rep.comDespliega("O",iRenIni,aVal[13]);
     rep.comDespliega("P",iRenIni,aVal[14]);

     sbRep = rep.getSbDatos();
     return sbRep;
   }

   /**********************************************/
   /*******************************************---------------------------*/
  public Vector genOficRespTarifas(String cValores)  throws Exception{
         TDObtenDatos dDatos = new TDObtenDatos();
         TDObtenDatos dDatosPersona = new TDObtenDatos();
         StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
         TWord tWord = new TWord();
         TFechas tFecha = new TFechas();

         Vector vRegsC = new Vector();
         Vector vRegsM = new Vector();
         Vector vRegsR = new Vector();
         Vector vRegsU = new Vector();

         TVDinRep vDatosC=new TVDinRep();
         TVDinRep vDatosM=new TVDinRep();
         TVDinRep vDatosR=new TVDinRep();
         TVDinRep vDatosU=new TVDinRep();

         String cFechaActual=tFecha.getDateSPN(tFecha.TodaySQL());
         StringBuffer sbRetorno = new StringBuffer("");
         String[] aValoresRecibidos = cValores.split("@");
         String[] aOficDeptoDirige = VParametros.getPropEspecifica("SMCapitaniaDependeOficDeptoCentral").split(",");
         String cRutas="",cEntidadRemplazaTexto = "";

         //System.out.print("-------------------------aValoresRecibidos[7]: "+aValoresRecibidos[7]);
         String[] cDir=aValoresRecibidos[7].split(",") ;
         int cOfic=0, cDpto=0;
         //Vector vRutaRemitente = new Vector();
         cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");

         //0-iEjercicioSolicitud,1-iNumSolicitud,2-cDscModalidad,3-cDscTramite,4-iCvePersona,
         //5-cRFC,6-cNomPersona,7-cDirPersona,8-Entidad,9-Municipio,11-RepLegal,12-iEjerTarifa,
         //13-iCveTarifa,14-usuario,15-cFechaSolicitud

         String cRegTarifas="Select iEjercicioTarifa,iConsecutivoTarifa,MYRRegTarifa.iEjercicioSolicitud,MYRRegTarifa.iNumSolicitud,iCvePersona, "+
          " dtIniVigencia,lAutorizada,cLugarServicio,cRutasOperacion,cPaginasModificadas,lTarifaPasaje,cNomConferencia,cDscBreve,cDscModalidad,   "+
          "MYRRegTarifa.iCveVehiculo,cNomEmbarcacion "+
          " from MYRRegTarifa "+
          " join TRARegSolicitud ON TRARegSolicitud.iEjercicio = MYRRegTarifa.iEjercicioTarifa "+
          " AND TRARegSolicitud.iNumSolicitud = MYRRegTarifa.iNumSolicitud "+
          " join TRAModalidad ON TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad "+
          " join TRACatTramite ON TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite  "+
          " left join VEHEmbarcacion ON MYRRegTarifa.iCveVehiculo = VEHEmbarcacion.iCveVehiculo"+
          " where TRARegSolicitud.iEjercicio ="+aValoresRecibidos[0]+" AND TRARegSolicitud.iNumSolicitud ="+aValoresRecibidos[1];

         String cUsuario=" Select distinct GRLUsuarioXOfic.iCveOficina,GRLUsuarioXOfic.iCveDepartamento, "+
                      " GRLUsuarioXOfic.iCveUsuario,cNombre,cApMaterno,cDscOficina,cDscDepartamento"+
                      " from grlusuarioxofic "+
                      " join segusuario on grlusuarioxofic.icveusuario= segusuario.icveusuario "+
                      " join grldeptoxofic on grlusuarioxofic.icveoficina= grldeptoxofic.icveoficina "+
                      " join grloficina on grlusuarioxofic.icveoficina=grloficina.icveoficina "+
                      " join grldepartamento on grlusuarioxofic.icvedepartamento=grldepartamento.icvedepartamento "+
                      " where grlusuarioxofic.icveusuario=  "+ aValoresRecibidos[11];


          vRegsR = super.FindByGeneric("",cRegTarifas,dataSourceName);
          vRegsU = super.FindByGeneric("",cUsuario,dataSourceName);

          if(vRegsR.size()>0){
            //System.out.print("entra a tarifas2");
            vDatosU = (TVDinRep) vRegsU.get(0);
            vDatosR = (TVDinRep) vRegsR.get(0);
            cOfic = vDatosU.getInt("iCveOficina");
            cDpto = vDatosU.getInt("iCveDepartamento");

            //vRutaRemitente = dDatos.getOrganigrama(cOfic, cDpto, vRutaRemitente);
            dDatos.dOficina.setOficina(cOfic);
            //dDatos.dPersona.setPersona();
            dDatos.dOficDepto.setOficinaDepto(cOfic, cDpto);
            String cMunicipioEmision = dDatos.dOficDepto.vDatoOfic.getNomMunicipio();
            String[] aEntidadRemplaza = cEntidadRemplazaTexto.split("/");
            String[] aDatos;
            for (int i=0; i<aEntidadRemplaza.length; i++){
              aDatos = aEntidadRemplaza[i].split(",");
              if (Integer.parseInt(aDatos[0],10) == dDatos.dOficDepto.vDatoOfic.getCvePais() &&
                  Integer.parseInt(aDatos[1],10) == dDatos.dOficDepto.vDatoOfic.getCveEntidadFed()){
                cMunicipioEmision = aDatos[2];
                break;
              }
            }
            String vigencia=tFecha.getDateSPN(vDatosR.getDate("dtIniVigencia"));
            //System.out.print("---++++++cDir[0]: "+cDir[0]+"   cDir[1]: "+cDir[1]+"  cDir[2]: "+cDir[2]);
            //System.out.print("---++++++cDir[0].trim(): "+cDir[0].trim()+"   cDir[1].trim: "+cDir[1].trim()+"  cDir[2].trim: "+cDir[2].trim());
            cRutas=vDatosR.getString("cRutasOperacion");
            dDatos.dOficDepto.setOficinaDepto(Integer.parseInt(aOficDeptoDirige[0],10),Integer.parseInt(aOficDeptoDirige[1],10));
            tWord.iniciaReporte();
            sbBuscaAdic.append("[iNumSolicitud]|[cMunicipioEmision]|[cEntidadEmision]|[cFechaEmision]|")
                       .append("[dtFechaSol]|[cNombreEmbarcacion]|[cNombreRemitente]|")
                       .append("[cSolicitante]|[cPasajeAltura]|[cFechaIniVigencia]|[cRutaOpera]|")
                       .append("[cDir1]|[cDir2]|[cDir3]|")
                       ;
            sbReemplazaAdic.append(aValoresRecibidos[1]+"|"+cMunicipioEmision+"|"+dDatos.dOficDepto.vDatoOfic.getAbrevEntidad()+"|"+cFechaActual+"|")
                           .append(aValoresRecibidos[12]+"|"+aValoresRecibidos[13]+"|"+dDatos.dOficDepto.getTitular()+"|")
                           .append(aValoresRecibidos[6]+"|"+aValoresRecibidos[14]+"|"+vigencia+"|"+cRutas+"|")
                           .append(cDir[0].trim()+"|"+cDir[1].trim()+"|"+cDir[2].trim()+"|")
                ;
            tWord.anexaEtiquetas(sbBuscaAdic,sbReemplazaAdic);


            String cConceptos= " SELECT iConsecConcepto,cDscConcepto,lSencilloRedondo "+
                        " from MYRConcepCobro "+
                        " WHERE iEjercicioTarifa= "+vDatosR.getInt("iEjercicioTarifa")+" AND iConsecutivoTarifa="+vDatosR.getInt("iConsecutivoTarifa")+" AND lVigente=1 "+
                        " ORDER BY cDscConcepto" ;

            vRegsC = super.FindByGeneric("",cConceptos,dataSourceName);
             if(vRegsC.size()>0){
               tWord.comEligeTabla("tarifa");
               for(int i = 0;i < vRegsC.size();i++){
                 vDatosC = (TVDinRep) vRegsC.get(i);

                 String cModalidades=
                     "SELECT distinct MYRModalidadConc.iConsecconcepto,cDscModalidad,MYRModalidadConc.dTarifaAutorizada as ddtarifaAutorizada,lSencilloRedondo, MYRModalidadConc.DTARIFAAUTORIZADARED as ddTarifaRed " +
                     " from MYRModalidadConc " +
                     " JOIN MYRConcepCobro on MYRModalidadConc.iConsecutivoTarifa=MYRConcepCobro.iConsecutivoTarifa " +
                     " and MYRModalidadConc.iEjercicioTarifa=MYRConcepCobro.iEjercicioTarifa " +
                     " and MYRConcepCobro.iConsecConcepto=MYRModalidadConc.iConsecConcepto " +
                     " WHERE MYRModalidadConc.iEjercicioTarifa="+vDatosR.getInt("iEjercicioTarifa")+"  AND MYRModalidadConc.iConsecutivoTarifa= "+vDatosR.getInt("iConsecutivoTarifa")+
                     " AND MYRModalidadConc.iConsecConcepto="+vDatosC.getInt("iConsecConcepto")+"  AND MYRModalidadConc.lVigente=1 ";

                    vRegsM = super.FindByGeneric("",cModalidades,dataSourceName);
                    tWord.comDespliegaCelda(vDatosC.getString("cDscConcepto"));
                    tWord.comDespliegaCelda("");
                    tWord.comDespliegaCelda("");

                    if(vRegsM.size()>0){
                      for(int j=0;j<vRegsM.size();j++){
                        vDatosM = (TVDinRep) vRegsM.get(j);

                        tWord.comDespliegaCelda(vDatosM.getString("cDscModalidad"));
                        if(vDatosM.getFloat("ddtarifaAutorizada")!=0)
                          tWord.comDespliegaCelda(""+vDatosM.getFloat("ddtarifaAutorizada"));
                        else
                          tWord.comDespliegaCelda("N/A");
                        if(vDatosM.getFloat("ddTarifaRed")!=0)
                          tWord.comDespliegaCelda(""+vDatosM.getFloat("ddTarifaRed"));
                        else
                          tWord.comDespliegaCelda("N/A");


                      }
                    }
                    tWord.comDespliegaCelda("");
                    tWord.comDespliegaCelda("");
                    tWord.comDespliegaCelda("");
               }
             }
          }

          Vector vDatosTemp = tWord.getVectorDatos(true);;
          Vector vDatosTablas = tWord.getDatosTablas();
          for(int iCont=0;iCont<vDatosTablas.size();iCont++){
            vDatosTemp.add(vDatosTablas.get(iCont));
          }

          return vDatosTemp;

   }

   public boolean fEncuentraPalabra(String cPalabra){
     boolean lEncontrada = false;
     if(cPalabra.toUpperCase().equals("DE"))lEncontrada=true;
     if(cPalabra.toUpperCase().equals("DEL"))lEncontrada=true;
     if(cPalabra.toUpperCase().equals("A"))lEncontrada=true;
     if(cPalabra.toUpperCase().equals("EN"))lEncontrada=true;
     if(cPalabra.toUpperCase().equals("LOS"))lEncontrada=true;
     if(cPalabra.toUpperCase().equals("LA"))lEncontrada=true;
     if(cPalabra.toUpperCase().equals("EL"))lEncontrada=true;
     if(cPalabra.toUpperCase().equals("LAS"))lEncontrada=true;
     return lEncontrada;
   }
   public HashMap folioLiteral(String cFiltro) throws Exception{
     HashMap hParametros = new HashMap();
     String[] cDatos = cFiltro.split(",");
     hParametros.put("iEjercicioIns",new Integer(cDatos[0]));
     hParametros.put("iCveOficina",new Integer(cDatos[1]));
     hParametros.put("iFolio",new Integer(cDatos[2]));
     hParametros.put("iRamo",new Integer(cDatos[3]));
     return hParametros;
   }

   public Vector certificadoRPMN(String cValores,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen) throws Exception{
     TWord rep = new TWord();
     TFechas fecha = new TFechas();
     Vector vcCuerpo=new Vector();
     Vector vcCopiasPara=new Vector();
     //Valores recibidos ()
     String[] aValoresRecibidos = cValores.split(",");
     TDMYRRPMN RPMN = new TDMYRRPMN();

     rep.iniciaReporte();
     int iEjercicio  =Integer.parseInt(aValoresRecibidos[0],10);
     int iCveOficina =Integer.parseInt(aValoresRecibidos[1],10);
     int iFolioRPMN  =Integer.parseInt(aValoresRecibidos[2],10);
     int iRamo       =Integer.parseInt(aValoresRecibidos[3],10);
     int iPartida    =Integer.parseInt(aValoresRecibidos[4],10);
     int iEjercicioSol =Integer.parseInt(aValoresRecibidos[5],10);
     int iNumSolicitud =Integer.parseInt(aValoresRecibidos[6],10);
     rep.comRemplaza("[Ejercicio]",iEjercicio+"");
     rep.comRemplaza("[Oficina]",iCveOficina+"");
     rep.comRemplaza("[Folio]",iFolioRPMN+"");
     rep.comRemplaza("[Ramo]",iRamo+"");
     rep.comRemplaza("[Partida]",iPartida+"");
     rep.comRemplaza("[cSolicitud]",iEjercicioSol+"/"+iNumSolicitud);
     rep.comRemplaza("[nomOficina]","");//Falta este valor de cRPMN
     String cRPMN = "SELECT " +
                    "  RP.IEJERCICIOINS,RP.ICVEOFICINA,C.CSIGLASOFICINA,RP.IFOLIORPMN,RP.IPARTIDA, " +
                    "  RP.DTREGISTRO,RP.CDSCDOCUMENTO,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CPERSONA,RP.CINSCRIPCION,RP.CTITULAR, " +
                    "  R.CDSCRAMO,R.LREGPERSONA,R.LREGEMBARCACION,S.CDSCSECCION,TAC.CDSCTIPOACTO,TAS.CDSCTIPOASIENTO, " +
                    "  RP.IPARTIDACANCELA,RP.DTCANCELAFOLIO,OL.CDSCOBSLARGA,C.CNOMREGISTRADOR,C.CDSCCAPITANIA " +
                    "FROM MYRRPMN RP " +
                    "JOIN MYRCAPITANIA C ON C.ICVEOFICINA=RP.ICVEOFICINA " +
                    "LEFT JOIN GRLPERSONA P ON P.ICVEPERSONA=RP.ICVEPERSONA " +
                    "LEFT JOIN MYRRAMO R ON R.ICVERAMO=RP.ICVERAMO " +
                    "LEFT JOIN MYRSECCION S ON S.ICVERAMO=RP.ICVERAMO AND S.ICVESECCION=RP.ICVESECCION " +
                    "LEFT JOIN MYRTIPOACTO TAC ON TAC.ICVERAMO=RP.ICVERAMO AND TAC.ICVESECCION=RP.ICVESECCION AND TAC.ICVETIPOACTO=RP.ICVETIPOACTO " +
                    "LEFT JOIN MYRTIPOASIENTO TAS ON TAS.ICVETIPOASIENTO=RP.ICVETIPOASIENTO " +
                    "LEFT JOIN GRLOBSERVALARGA OL ON OL.IEJERCICIOOBSLARGA=RP.IEJERCICIOOBSLARGA AND OL.ICVEOBSLARGA=RP.ICVEOBSLARGA " +
                    "WHERE RP.IEJERCICIOINS= " +iEjercicio+
                    "  AND RP.ICVEOFICINA= " +iCveOficina+
                    "  AND RP.IFOLIORPMN= "+iFolioRPMN;
     String cEmbarcacion = "SELECT " +
                           "E.IEJERCICIOINS,E.ICVEOFICINA,E.IFOLIORPMN,E.IPARTIDA, " +
                           "E.CNOMEMBARCACION,E.ICVEVEHICULO,E.CPROPIETARIO,TE.CDSCTIPOEMBARCACION,TN.CDSCTIPONAVEGA, " +
                           "TS.CDSCTIPOSERV,E.DESLORA, " +
                           "UME.CDSCUNIDADMEDIDA AS UME, " +
                           "E.DMANGA,UMM.CDSCUNIDADMEDIDA AS UMM, " +
                           "E.DPUNTAL,UMP.CDSCUNIDADMEDIDA AS UMP, " +
                           "E.DARQUEOBRUTO,UMAB.CDSCUNIDADMEDIDA AS UMAB, " +
                           "E.DARQUEONETO,UMAN.CDSCUNIDADMEDIDA AS UMAN, " +
                           "EF.CNOMBRE AS CENTIDADMAT, MU.CNOMBRE AS CMUNMAT,E.CMATRICULA " +
                           "FROM MYREMBARCACION E " +
                           "LEFT JOIN GRLPERSONA P ON P.ICVEPERSONA=E.ICVEPROPIETARIO " +
                           "LEFT JOIN VEHTIPOEMBARCACION TE ON TE.ICVETIPOEMBARCACION=E.ICVETIPOEMBARCACION " +
                           "LEFT JOIN VEHEMBARCACION EM ON EM.ICVEVEHICULO=E.ICVEVEHICULO " +
                           "LEFT JOIN VEHTIPONAVEGACION TN ON TN.ICVETIPONAVEGA=EM.ICVETIPONAVEGA " +
                           "LEFT JOIN VEHTIPOSERVICIO TS ON TS.ICVETIPOSERV=EM.ICVETIPOSERV " +
                           "LEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA=E.IUNIMEDESLORA " +
                           "LEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA=E.IUNIMEDMANGA " +
                           "LEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA=E.IUNIMEDPUNTAL " +
                           "LEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA=E.IUNIMEDARQUEOBRUTO " +
                           "LEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA=E.IUNIMEDARQUEONETO " +
                           "LEFT JOIN GRLENTIDADFED EF ON EF.ICVEPAIS=1 AND EF.ICVEENTIDADFED=E.ICVEENTIDADMATRIC " +
                           "LEFT JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS=1 AND MU.ICVEENTIDADFED=E.ICVEENTIDADMATRIC AND MU.ICVEMUNICIPIO=E.ICVEMUNICIPIOMATRIC " +
                           " WHERE E.IEJERCICIOINS=" +iEjercicio+
                           " AND E.ICVEOFICINA=" +iCveOficina+
                           " AND E.IFOLIORPMN="+iFolioRPMN;

     String cEmpresa = "SELECT " +
                       "E.IEJERCICIOINS,E.ICVEOFICINA,E.IFOLIORPMN,E.IPARTIDA, " +
                       "E.CRFC,E.CNOMRAZONSOCIAL,E.CCALLE,E.CNUMEXTERIOR,E.CNUMINTERIOR,E.CCOLONIA,E.CCODPOSTAL, " +
                       "P.CNOMBRE AS CPAIS, EF.CNOMBRE AS CENTIDADFED, M.CNOMBRE AS CMUNICIPIO " +
                       "FROM myrempresa e " +
                       "LEFT JOIN GRLPAIS P ON P.ICVEPAIS=E.ICVEPAIS " +
                       "LEFT JOIN GRLENTIDADFED EF ON EF.ICVEPAIS=E.ICVEPAIS AND EF.ICVEENTIDADFED=E.ICVEENTIDADFED " +
                       "LEFT JOIN GRLMUNICIPIO M ON M.ICVEPAIS=E.ICVEPAIS AND M.ICVEENTIDADFED=E.ICVEENTIDADFED AND M.ICVEMUNICIPIO=E.ICVEMUNICIPIO " +
                       " WHERE E.IEJERCICIOINS=" +iEjercicio+
                       " AND E.ICVEOFICINA=" +iCveOficina+
                       " AND E.IFOLIORPMN="+iFolioRPMN;

     String cPartida = "SELECT DTREGISTRO,OL.CDSCOBSLARGA FROM MYRRPMN R " +
                       "LEFT JOIN GRLOBSERVALARGA OL ON OL.IEJERCICIOOBSLARGA=R.IEJERCICIOOBSLARGA AND OL.ICVEOBSLARGA=R.ICVEOBSLARGA " +
                       "WHERE R.IEJERCICIOINS="+iEjercicio+
                       " AND R.ICVEOFICINA="+iCveOficina+
                       " AND R.IFOLIORPMN="+iFolioRPMN+
                       " AND R.IPARTIDA = "+iPartida;

     String cSolicitud = "SELECT P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CSOLICITANTE,R.CREFALFANUM,R.DIMPORTEPAGADO,R.DTPAGO,B.CDSCBANCO " +
                         "FROM TRAREGSOLICITUD S " +
                         "JOIN GRLPERSONA P ON P.ICVEPERSONA=S.ICVESOLICITANTE " +
                         "LEFT JOIN TRAREGREFPAGO R ON R.IEJERCICIO=S.IEJERCICIO AND R.INUMSOLICITUD=S.INUMSOLICITUD " +
                         "LEFT JOIN GRLBANCO B ON B.ICVEBANCO=R.ICVEBANCO " +
                         "WHERE S.IEJERCICIO="+iEjercicioSol+
                         " AND S.INUMSOLICITUD = "+iNumSolicitud;


     Vector vcRPMN = RPMN.findByCustom("",cRPMN);
     Vector vcPartida = RPMN.findByCustom("",cPartida);
     Vector vcSolicitud = RPMN.findByCustom("",cSolicitud);
     Vector vcEmbarcacion = new Vector();
     Vector vcEmpresa = new Vector();
     TVDinRep vEmbarcacion = new TVDinRep();
     TVDinRep vEmpresa = new TVDinRep();
     TVDinRep vRPMN = new TVDinRep();
     TVDinRep vPartida = new TVDinRep();
     if(vcRPMN.size()>0){
       vRPMN = (TVDinRep) vcRPMN.get(0);
       int iFolio = vRPMN.getInt("IFOLIORPMN");
       String cFolio=iFolio>999?iFolio+"":iFolio>99?"0"+iFolio:iFolio>9?"00"+iFolio:"000"+iFolio;
       cFolio = cFolio+"-"+vRPMN.getString("CSIGLASOFICINA")+"-"+vRPMN.getString("IEJERCICIOINS").substring(2,4);
       rep.comRemplaza("[cFolioRPMN]",cFolio);
       rep.comRemplaza("[dtFechaRegistro]",fecha.getDateSPN(vRPMN.getDate("DTREGISTRO")));
       rep.comRemplaza("[cPersona]",vRPMN.getString("CPERSONA"));
       rep.comRemplaza("[cFechaActual]",fecha.getDateSPN(fecha.getDateSQL(fecha.getThisTime())));
       rep.comRemplaza("[cRegistrador]",vRPMN.getString("CNOMREGISTRADOR"));
       rep.comRemplaza("[cDscOficina]",vRPMN.getString("CDSCCAPITANIA"));
       //rep.comRemplaza("[]",vRPMN.getString(""));
     }
     
     if(vcPartida.size()>0){
       vPartida = (TVDinRep) vcPartida.get(0);
       rep.comRemplaza("[fechaPartida]",fecha.getDateSPN(vPartida.getDate("DTREGISTRO")));
       rep.comRemplaza("[cSintesis]",vPartida.getString("CDSCOBSLARGA"));
       rep.comRemplaza("[iPartida]",iPartida+"");
     }
     if(vcSolicitud.size()>0){
       TVDinRep vSolicitud = (TVDinRep) vcSolicitud.get(0);
       rep.comRemplaza("[cSolicitante]",vSolicitud.getString("CSOLICITANTE"));
       rep.comRemplaza("[cMontoPago]",vSolicitud.getDouble("DIMPORTEPAGADO")+"");
       //rep.comRemplaza("[fechaPago]",fecha.getDateSPN(vSolicitud.getDate("DTPAGO")));
       
       if(vSolicitud.getString("DTPAGO").equals("") || vSolicitud.getString("DTPAGO").equals("null"))
    	   rep.comRemplaza("[fechaPago]","");
       else rep.comRemplaza("[fechaPago]",fecha.getDateSPN(vSolicitud.getDate("DTPAGO")));
       
       rep.comRemplaza("[cRefAlfaNum]",vSolicitud.getString("CREFALFANUM"));
       rep.comRemplaza("[cBanco]",vSolicitud.getString("CDSCBANCO"));
     }
     //En caso que sea Empresa
     if(iRamo==1){
       vcEmbarcacion = this.FindByGeneric("",cEmbarcacion,dataSourceName);
       if(vcEmbarcacion.size()>0){
         vEmbarcacion = (TVDinRep) vcEmbarcacion.get(0);
         rep.comRemplaza("[Ramo1]","Embarcaciónes o Aretefactos Navales");
         rep.comRemplaza("[cNombreObjeto]",vEmbarcacion.getString("CNOMEMBARCACION"));
         rep.comRemplaza("[cPropietario]",vEmbarcacion.getString("CPROPIETARIO"));
         rep.comRemplaza("[cTipoEmbarcacion]",vEmbarcacion.getString("CDSCTIPOEMBARCACION"));
         rep.comRemplaza("[cTipoNavegacion]",vEmbarcacion.getString("CDSCTIPONAVEGA"));
         rep.comRemplaza("[cTipoServicio]",vEmbarcacion.getString("CDSCTIPOSERV"));
         rep.comRemplaza("[cEslora]",vEmbarcacion.getString("DESLORA")+" "+vEmbarcacion.getString("UME"));
         rep.comRemplaza("[cManga]",vEmbarcacion.getString("DMANGA")+" "+vEmbarcacion.getString("UMM"));
         rep.comRemplaza("[cPuntal]",vEmbarcacion.getString("DPUNTAL")+" "+vEmbarcacion.getString("UMP"));
         rep.comRemplaza("[cArqueoBruto]",vEmbarcacion.getString("DARQUEOBRUTO")+" "+vEmbarcacion.getString("UMAB"));
         rep.comRemplaza("[cArqueoNeto]",vEmbarcacion.getString("DARQUEONETO")+" "+vEmbarcacion.getString("UMAN"));
         rep.comRemplaza("[cEntidadMatricula]",vEmbarcacion.getString("CENTIDADMAT"));
         rep.comRemplaza("[cMunicipioMatricula]",vEmbarcacion.getString("CMUNMAT"));
         rep.comRemplaza("[cMatricula]",vEmbarcacion.getString("CMATRICULA"));
         //rep.comRemplaza("[]",vEmbarcacion.getString(""));
       }
     }
     //En caso que sea Embarcacion
     if(iRamo==2){
       vcEmpresa = this.FindByGeneric("",cEmpresa,dataSourceName);
       if(vcEmpresa.size()>0){
         vEmpresa = (TVDinRep) vcEmpresa.get(0);
         rep.comRemplaza("[Ramo1]","Empresa");
         rep.comRemplaza("[cRFC]",vEmpresa.getString("CRFC"));
         rep.comRemplaza("[cNombreObjeto]",vEmpresa.getString("CNOMRAZONSOCIAL"));
         rep.comRemplaza("[cCalleDireccion]",vEmpresa.getString("CCALLE"));
         rep.comRemplaza("[cNumExteriorDireccion]",vEmpresa.getString("CNUMEXTERIOR"));
         rep.comRemplaza("[cNumInteriorDireccion]",vEmpresa.getString("CNUMINTERIOR"));
         rep.comRemplaza("[cColoniaDireccion]",vEmpresa.getString("CCOLONIA"));
         rep.comRemplaza("[cCodPostalDireccion]",vEmpresa.getString("CCODPOSTAL"));
         rep.comRemplaza("[cPaisDireccion]",vEmpresa.getString("CPAIS"));
         rep.comRemplaza("[cEntidadFedDireccion]",vEmpresa.getString("CENTIDADFED"));
         rep.comRemplaza("[cMunicipioDireccion]",vEmpresa.getString("CMUNICIPIO"));
       }
     }
     String cGravamen = "SELECT " +
                        "RP.IEJERCICIOINS,RP.ICVEOFICINA,RP.IFOLIORPMN, " +
                        "RP.IPARTIDA,S.CDSCSECCION,RP.IPARTIDACANCELA " +
                        "FROM MYRRPMN RP " +
                        "JOIN MYRSECCION S ON S.ICVERAMO=RP.ICVERAMO AND S.ICVESECCION=RP.ICVESECCION " +
                        "WHERE IEJERCICIOINS=" +iEjercicio+
                        " AND RP.ICVEOFICINA=" +iCveOficina+
                        " AND RP.IFOLIORPMN=" +iFolioRPMN+
                        " AND S.LGRAVAMEN=1 "+
                        " AND RP.IPARTIDACANCELA IS NULL ";
     Vector vcGravamen = this.FindByGeneric("",cGravamen,dataSourceName);
     String cGravamenes = "";
     if(vcGravamen.size()>0){
       for(int i=0;i<vcGravamen.size();i++){
         TVDinRep vGravamen= (TVDinRep)vcGravamen.get(i);
         cGravamenes += vGravamen.getString("IPARTIDA") + " - " + vGravamen.getString("CDSCSECCION");
         if((i+1)==vGravamen.size()) cGravamenes+=",";
         else cGravamenes+=".";
       }
       rep.comRemplaza("[cGravamen]","El Folio tiene gravamen activo en las partidas: "+cGravamenes);
     }else{
       rep.comRemplaza("[cGravamen]","El Folio no tiene ningun Gravamen");
     }

     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
         Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
         0,0,
         0,0,0,
         "","",
         "","",
         true,"cCuerpo",vcCuerpo,
         true,vcCopiasPara,
         rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());
     return vRetorno;
   }

}

package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;
import java.sql.Date;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.excepciones.*;
import java.util.Vector;
import java.sql.*;
import java.util.*;


/**
 * <p>Title: TDDPOPOA.java</p>
 * <p>Description: DAO con métodos para reportes de PMDP</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */

public class TDDPOPMDP
    extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TWord rep = new TWord();

  public TDDPOPMDP(){
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

  public Vector GenObsPMDP(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{
    cError = "";
    int iCveSolicitante, iCveRepLegal, iEjercicioSolicitud, iNumSolicitud;
    String cNumOficioSol = "",cSolicitante = "",cdtOficioSol = "", cOficialiaSol = "", cComentarios="";
    Date dtOficioSol,dtToday;
    int iCvePMDP;
    TVDinRep vData = new TVDinRep();
    TDObtenDatos dObtenDatos = new TDObtenDatos();

    String[] aFiltros = cFiltro.split(",");
    iCvePMDP = Integer.parseInt(aFiltros[3],10);

    try{
      rep.iniciaReporte();

      dObtenDatos.dPMDP.setCvePMDP(iCvePMDP);
      cNumOficioSol = dObtenDatos.dPMDP.getNumOficioSolicitud();
      dtOficioSol = dObtenDatos.dPMDP.getDateOficioSolicitud();
      cOficialiaSol = dObtenDatos.dPMDP.getNumOficialiaSol();
      cComentarios  = dObtenDatos.dPMDP.getComentarios();
      iCveSolicitante = dObtenDatos.dPMDP.getCveSolicitante();
      iCveRepLegal = dObtenDatos.dPMDP.getCveRepLegal();
      iEjercicioSolicitud = dObtenDatos.dPMDP.getEjercicio();
      iNumSolicitud = dObtenDatos.dPMDP.getSolicitud();
      vData.put("iEjercicioSolicitud",iEjercicioSolicitud);
      vData.put("iNumSolicitud",iNumSolicitud);
      vData.put("iCveSistema",11);
      vData.put("iCveModulo",9);
      vData.put("iNumReporte",5);
      vData.put("cFolio",cFolio);
      vData.put("cAsunto","Observaciones al Programa Maestro de Desarrollo Portuario");

      this.insertReportexFolio(vData, conn);


      dObtenDatos.dPersona.setPersona(iCveSolicitante,0);
      cSolicitante = dObtenDatos.dPersona.getNomCompleto();

      dtToday = tFecha.TodaySQL();
      if(tFecha.getIntYear(dtToday) != tFecha.getIntYear(dtOficioSol))
        cdtOficioSol = tFecha.getFechaDDMMMMMYYYY(dtOficioSol," de ");
      else
        cdtOficioSol = tFecha.getStringDay(dtOficioSol) +" de "+ tFecha.getMonthName(dtOficioSol)+ " del año en curso";

      rep.comRemplaza("[cNumOficioSol]",cNumOficioSol);
      rep.comRemplaza("[dtOficioSol]",cdtOficioSol);
      rep.comRemplaza("[cSolicitante]",cSolicitante.trim());
      rep.comRemplaza("[cComentariosPMDP]",cComentarios);

      //Falta sacar las referencias y Numeros Internos

      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              0,0,
                                              iCveSolicitante,0,iCveRepLegal,
                                              "","","REF.: "+cNumOficioSol,"INT.: "+cOficialiaSol,
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());


    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);

  }

  public Vector GenOpnIntPMDP(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{
    cError = "";
    int iCveSolicitante, iCveOficina=0, iCveDepto=0,iEjercicioSolicitud,iNumSolicitud;
    String cSolicitante="", cComentario="", cConsecutivoFolio = "", cRefTemp="", cReferencia = "",
        cNumInterno="";
    int iCveSegtoEntidad, iCveOpinionEntidad, lExt, iConsecutivoFolio, iEjercicio;
    TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();

    try{
      rep.iniciaReporte();

      datosOpinion.dDatosOpnTra.setFiltrosOpn(cFiltro);
      iEjercicioSolicitud = datosOpinion.dDatosOpnTra.getiEjercicio();
      iNumSolicitud = datosOpinion.dDatosOpnTra.getiNumSolicitud();
      iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
      iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();

      Vector vcData = findByCustom("","SELECT " +
                                   "CCOMENTARIOSBREVESPMDP " +
                                   "FROM DPOPROGMAESTRODESPORT " +
                                   "where IEJERCICIOSOLICITUD = " +iEjercicioSolicitud+
                                   " and INUMSOLICITUD = "+iNumSolicitud);
      if(vcData.size() > 0){
        TVDinRep vPMDP = (TVDinRep) vcData.get(0);
        cComentario = vPMDP.getString("CCOMENTARIOSBREVESPMDP");
      }

      datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cFolio);

      datosOpinion.dDatosOficPer.setFiltroOpnEnt(iCveOpinionEntidad);
      iCveDepto = datosOpinion.dDatosOficPer.getiCveDepto();
      iCveOficina = datosOpinion.dDatosOficPer.getiCveOficina();

      datosOpinion.dDatosOpnSol.setFiltro(iEjercicioSolicitud,iNumSolicitud);
      cSolicitante = datosOpinion.dDatosOpnSol.getcNombre();

      String cQuery1 = "Select " +
        "GRLFolioXSegtoEnt.cNumOfPartesRefExterna as cNumPartesRefExt, " +
        "GRLFolioXSegtoEnt.cNumOficioRefExterna as cNumOfRefExt, " +
        "GRLFolioXSegtoEnt.iEjercicioFolio as iEjer, " +
        "GRLFolioXSegtoEnt.iCveOficina as iCveOfi, " +
        "GRLFolioXSegtoEnt.iCveDepartamento as iCveDepto, " +
        "GRLFolioXSegtoEnt.cDigitosFolio as cDigFol, " +
        "GRLFolioXSegtoEnt.iConsecutivo as iConsec, " +
        "GRLFOLIO.CNUMOFICIALIAPARTES as cNumOficPart, " +
        "GRLFolioXSegtoEnt.lExterno as lExt " +
        "from GRLFolioXSegtoEnt " +
        "left join GRLFOLIO on GRLFOLIO.IEJERCICIO = GRLFOLIOXSEGTOENT.IEJERCICIOFOLIO " +
        "and GRLFOLIo.ICVEOFICINA = GRLFOLIOXSEGTOENT.ICVEOFICINA " +
        "and grlfolio.ICVEDEPARTAMENTO = GRLFOLIOXSEGTOENT.ICVEDEPARTAMENTO " +
        "and GRLFOLIO.CDIGITOSFOLIO = GRLFOLIOXSEGTOENT.CDIGITOSFOLIO " +
        "and grlfolio.ICONSECUTIVO = GRLFOLIOXSEGTOENT.ICONSECUTIVO " +
        "WHERE GRLFOLIOXSEGTOENT.ICVESEGTOENTIDAD = " +iCveSegtoEntidad+
        " and GRLFOLIOXSEGTOENT.LFOLIOREFERENINTERNO = 1 ";

    Vector vcData2 = findByCustom("",cQuery1);

      for(int i = 0; i < vcData2.size(); i++){
        TVDinRep vDatosFolioxSeg = (TVDinRep) vcData2.get(i);
        lExt = vDatosFolioxSeg.getInt("lExt");
        iConsecutivoFolio = vDatosFolioxSeg.getInt("iConsec");
        iEjercicio = vDatosFolioxSeg.getInt("iEjer");

        if(iConsecutivoFolio >= 10 && iConsecutivoFolio < 100)
          cConsecutivoFolio = "0" + iConsecutivoFolio;
        else if(iConsecutivoFolio < 10)
          cConsecutivoFolio = "00" + iConsecutivoFolio;

        cRefTemp = vDatosFolioxSeg.getString("cDigFol") + "."+cConsecutivoFolio + "/"+iEjercicio;

        if(lExt == 1){
          if(cReferencia.equals(""))
            cReferencia = vDatosFolioxSeg.getString("cNumOfRefExt");
          else
            cReferencia += ", "+vDatosFolioxSeg.getString("cNumOfRefExt");

          if(cNumInterno.equals(""))
            cNumInterno = vDatosFolioxSeg.getString("cNumPartesRefExt");
          else
            cNumInterno += ", "+vDatosFolioxSeg.getString("cNumPartesRefExt");
        }else{
          if(cReferencia.equals(""))
            cReferencia = cRefTemp;
          else
            cReferencia += ", "+cRefTemp;

          if(cNumInterno.equals(""))
            cNumInterno = vDatosFolioxSeg.getString("cNumOficPart");
          else
            cNumInterno += ", "+vDatosFolioxSeg.getString("cNumOficPart");
        }

      }

      if(cComentario.equalsIgnoreCase("null") || cComentario.equalsIgnoreCase(""))
        rep.comRemplaza("[cComentariosPMDP]"," -- ");
      else
        rep.comRemplaza("[cComentariosPMDP]",cComentario);

      rep.comRemplaza("[cSolicitante]",cSolicitante.trim());

      //Falta sacar las referencias y Numeros Internos

      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              iCveOficina,iCveDepto,
                                              0,0,0,
                                              "","","REF.: "+cReferencia,"INT.: "+cNumInterno,
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());


    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);

  }

  /*Método para generar el Reporte de DPO Opinion Externa*/
  public Vector GenOpnExtPMDP(String cFiltro,String cFolio,String cCveOfic,
                                    String cCveDepto) throws Exception{
    Vector vRegsFolioxSeg = new Vector();
    String cReferencia = "",cConsecutivoFolio = "",cRefTemp = "", cNumInterno = "",cNombre="", cOpnDirigidoA="", cPtoOpinion="";
    int lExt = 0,iConsecutivoFolio = 0, iEjercicio = 0, iCveSegtoEntidad = 0, iCveDepto = 0, iCveOficina = 0,
        iCveOpinionEntidad, iEjercicioSol,iNumSolicitud;
    TWord rep = new TWord();
    TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();

    try{
    rep.iniciaReporte();

    datosOpinion.dDatosOpnTra.setFiltrosOpn(cFiltro);
    iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
    iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();
    iEjercicioSol = datosOpinion.dDatosOpnTra.getiEjercicio();
    iNumSolicitud = datosOpinion.dDatosOpnTra.getiNumSolicitud();
    cOpnDirigidoA = datosOpinion.dDatosOpnTra.getOpnDirigidoA();
    cPtoOpinion = datosOpinion.dDatosOpnTra.getPtoOpn();

    datosOpinion.dDatosOficPer.setFiltroOpnEnt(iCveOpinionEntidad);


    datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cFolio);

    datosOpinion.dDatosOpnSol.setFiltro(iEjercicioSol,iNumSolicitud);
    cNombre = datosOpinion.dDatosOpnSol.getcNombre();


    String cQuery1 = "Select " +
        "GRLFolioXSegtoEnt.cNumOfPartesRefExterna as cNumPartesRefExt, " +
        "GRLFolioXSegtoEnt.cNumOficioRefExterna as cNumOfRefExt, " +
        "GRLFolioXSegtoEnt.iEjercicioFolio as iEjer, " +
        "GRLFolioXSegtoEnt.iCveOficina as iCveOfi, " +
        "GRLFolioXSegtoEnt.iCveDepartamento as iCveDepto, " +
        "GRLFolioXSegtoEnt.cDigitosFolio as cDigFol, " +
        "GRLFolioXSegtoEnt.iConsecutivo as iConsec, " +
        "GRLFOLIO.CNUMOFICIALIAPARTES as cNumOficPart, " +
        "GRLFolioXSegtoEnt.lExterno as lExt " +
        "from GRLFolioXSegtoEnt " +
        "left join GRLFOLIO on GRLFOLIO.IEJERCICIO = GRLFOLIOXSEGTOENT.IEJERCICIOFOLIO " +
        "and GRLFOLIo.ICVEOFICINA = GRLFOLIOXSEGTOENT.ICVEOFICINA " +
        "and grlfolio.ICVEDEPARTAMENTO = GRLFOLIOXSEGTOENT.ICVEDEPARTAMENTO " +
        "and GRLFOLIO.CDIGITOSFOLIO = GRLFOLIOXSEGTOENT.CDIGITOSFOLIO " +
        "and grlfolio.ICONSECUTIVO = GRLFOLIOXSEGTOENT.ICONSECUTIVO " +
        "WHERE GRLFOLIOXSEGTOENT.ICVESEGTOENTIDAD = " +iCveSegtoEntidad+
        " and GRLFOLIOXSEGTOENT.LFOLIOREFERENINTERNO = 1 ";

    Vector vcData2 = findByCustom("",cQuery1);


    if(vcData2.size() > 0){
      for(int i = 0; i < vcData2.size(); i++){
        TVDinRep vDatosFolioxSeg = (TVDinRep) vcData2.get(i);
        lExt = vDatosFolioxSeg.getInt("lExt");
        iConsecutivoFolio = vDatosFolioxSeg.getInt("iConsec");
        iEjercicio = vDatosFolioxSeg.getInt("iEjer");

        if(iConsecutivoFolio >= 10 && iConsecutivoFolio < 100)
          cConsecutivoFolio = "0" + iConsecutivoFolio;
        else if(iConsecutivoFolio < 10)
          cConsecutivoFolio = "00" + iConsecutivoFolio;

        cRefTemp = vDatosFolioxSeg.getString("cDigFol") + "."+cConsecutivoFolio + "/"+iEjercicio;

        if(lExt == 1){
          if(cReferencia.equals(""))
            cReferencia = vDatosFolioxSeg.getString("cNumOfRefExt");
          else
            cReferencia += ", "+vDatosFolioxSeg.getString("cNumOfRefExt");

          if(cNumInterno.equals(""))
            cNumInterno = vDatosFolioxSeg.getString("cNumPartesRefExt");
          else
            cNumInterno += ", "+vDatosFolioxSeg.getString("cNumPartesRefExt");
        }else{
          if(cReferencia.equals(""))
            cReferencia = cRefTemp;
          else
            cReferencia += ", "+cRefTemp;

          if(cNumInterno.equals(""))
            cNumInterno = vDatosFolioxSeg.getString("cNumOficPart");
          else
            cNumInterno += ", "+vDatosFolioxSeg.getString("cNumOficPart");
        }

      }
    }

    rep.comRemplaza("[NombreSolicitante]",cNombre.trim());
    rep.comRemplaza("[cNombrePersonaDestino]",cOpnDirigidoA);
    rep.comRemplaza("[cPuestoDestino]",cPtoOpinion);

    return new TDGeneral().generaOficioWord(cFolio,
                                            Integer.parseInt(cCveOfic,10),
                                            Integer.parseInt(cCveDepto,10),
                                            0,
                                            0,
                                            0,0,0,
                                            "","","REF.: "+cReferencia,"INT.: "+cNumInterno,
                                            false,"",new Vector(),
                                            false,new Vector(),
                                            rep.getEtiquetasBuscar(),
                                            rep.getEtiquetasRemplazar());

  } catch(Exception e){
    e.printStackTrace();
    cMensaje += e.getMessage();
  }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }


  public Vector GenAutorizaPMDP(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{

    int iCveSolicitante, iCveRepLegal, iEjercicioSolicitud, iNumSolicitud;
    String cNumOficioSol, cOficialiaSol, cComentarios, cdtOficioSol = "";
    Date dtOficioSol,dtToday;
    int iCvePMDP;
    TDObtenDatos dObtenDatos = new TDObtenDatos();
    TVDinRep vData = new TVDinRep();

    String[] aFiltros = cFiltro.split(",");
    iCvePMDP = Integer.parseInt(aFiltros[3],10);

    try{
      rep.iniciaReporte();

      dObtenDatos.dPMDP.setCvePMDP(iCvePMDP);
      cNumOficioSol = dObtenDatos.dPMDP.getNumOficioSolicitud();
      cOficialiaSol = dObtenDatos.dPMDP.getNumOficialiaSol();
      cComentarios  = dObtenDatos.dPMDP.getComentarios();
      dtOficioSol = dObtenDatos.dPMDP.getDateOficioSolicitud();
      iEjercicioSolicitud = dObtenDatos.dPMDP.getEjercicio();
      iNumSolicitud = dObtenDatos.dPMDP.getSolicitud();
      iCveSolicitante = dObtenDatos.dPMDP.getCveSolicitante();
      iCveRepLegal = dObtenDatos.dPMDP.getCveRepLegal();
      vData.put("iEjercicioSolicitud",iEjercicioSolicitud);
      vData.put("iNumSolicitud",iNumSolicitud);
      vData.put("iCveSistema",11);
      vData.put("iCveModulo",9);
      vData.put("iNumReporte",8);
      vData.put("cFolio",cFolio);
      vData.put("cAsunto","Autorización del Programa Maestro de Desarrollo Portuario");

      this.insertReportexFolio(vData, conn);

      dtToday = tFecha.TodaySQL();
      if(tFecha.getIntYear(dtToday) != tFecha.getIntYear(dtOficioSol))
        cdtOficioSol = tFecha.getFechaDDMMMMMYYYY(dtOficioSol," de ");
      else
        cdtOficioSol = tFecha.getStringDay(dtOficioSol) +" de "+ tFecha.getMonthName(dtOficioSol)+ " del año en curso";

      rep.comRemplaza("[cComentariosPMDP]",cComentarios);
      rep.comRemplaza("[dtOficioSol]",cdtOficioSol);


      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10), Integer.parseInt(cCveDepto,10),
                                              0,0,
                                              iCveSolicitante,0,iCveRepLegal,
                                              "","",
                                              "REF.: "+cNumOficioSol,"INT.: "+cOficialiaSol,
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());


    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);

  }

  public Vector GenDifusionPMDP(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{
    cError = "";
    int iCveSolicitante, iCveRepLegal, iEjercicioSolicitud, iNumSolicitud;
    String cComentarios, cOficio, cFechaDesplegar = "";
    int iCvePMDP;
    Date dtToday, dtAsignacion;
    TVDinRep vData = new TVDinRep();
    TDObtenDatos dObtenDatos = new TDObtenDatos();

    String[] aFiltros = cFiltro.split(",");
    iCvePMDP = Integer.parseInt(aFiltros[3],10);
    cOficio = aFiltros[1];
    dtAsignacion = tFecha.getDateSQL(aFiltros[2]);
    dtToday = tFecha.TodaySQL();

    try{
      rep.iniciaReporte();
      dObtenDatos.dPMDP.setCvePMDP(iCvePMDP);
      cComentarios = dObtenDatos.dPMDP.getComentarios();
      iEjercicioSolicitud = dObtenDatos.dPMDP.getEjercicio();
      iNumSolicitud = dObtenDatos.dPMDP.getSolicitud();
      iCveSolicitante = dObtenDatos.dPMDP.getCveSolicitante();
      iCveRepLegal = dObtenDatos.dPMDP.getCveRepLegal();
      vData.put("iEjercicioSolicitud",iEjercicioSolicitud);
      vData.put("iNumSolicitud",iNumSolicitud);
      vData.put("iCveSistema",11);
      vData.put("iCveModulo",9);
      vData.put("iNumReporte",9);
      vData.put("cFolio",cFolio);
      vData.put("cAsunto","Difusión del Programa Maestro de Desarrollo Portuario");

      this.insertReportexFolio(vData, conn);

      if(tFecha.getIntYear(dtToday) != tFecha.getIntYear(dtAsignacion))
        cFechaDesplegar = tFecha.getFechaDDMMMMMYYYY(dtAsignacion," de ");
      else
        cFechaDesplegar = tFecha.getStringDay(dtAsignacion) +" de "+ tFecha.getMonthName(dtAsignacion)+ " del año en curso";

      rep.comRemplaza("[cOficio]",cOficio);
      rep.comRemplaza("[dtAsignacion]",cFechaDesplegar);
      rep.comRemplaza("[cComentariosPMDP]",cComentarios);

      //Falta sacar las referencias y Numeros Internos

      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              0,0,
                                              iCveSolicitante,0,iCveRepLegal,
                                              "","","","",
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());


    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);

  }

  public TVDinRep insertReportexFolio(TVDinRep vData, Connection cnNested) throws
           DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    PreparedStatement lPStmt2 = null;
    boolean lSuccess = true;
    int iEjercicio, iCveOficina, iCveDepto, iConsecutivo;
    String cDigitosFolio;
    TDObtenDatos obtenerDatos = new TDObtenDatos();
    obtenerDatos.dFolio.setDatosFolio(vData.getString("cFolio"));
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      iEjercicio = obtenerDatos.dFolio.getCveEjercicio();
      iCveOficina = obtenerDatos.dFolio.getCveOficina();
      iCveDepto = obtenerDatos.dFolio.getCveDepartamento();
      cDigitosFolio = obtenerDatos.dFolio.getCveDigitosFolio();
      iConsecutivo = obtenerDatos.dFolio.getCveConsecutivo();
      //Insertar Registro en GRLReporteXFolio

      String lSQL =
          "insert into GRLReporteXFolio("+
          "iEjercicioFolio,"+
          "iCveOficina,"+
          "iCveDepartamento,"+
          "cDigitosFolio,"+
          "iConsecutivo,"+
          "iEjercicioSolicitud,"+
          "iNumSolicitud,"+
          "iCveSistema,"+
          "iCveModulo,"+
          "iNumReporte"+
          ") "+
          "values ("+
          iEjercicio+","+
          iCveOficina+","+
          iCveDepto+","
          +" '"+
          cDigitosFolio+"'"+" ,"+
          iConsecutivo+","+
          vData.getInt("iEjercicioSolicitud")+","+
          vData.getInt("iNumSolicitud")+","+
          vData.getInt("iCveSistema")+","+
          vData.getInt("iCveModulo")+","+
          vData.getInt("iNumReporte")+
          ")";
      //System.out.print("**************" + lSQL + "**************");
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }

      String lSQL2 = "update GRLFolio set cAsunto = '"+vData.getString("cAsunto")+"'"+
          " where iEjercicio = "+iEjercicio+
          " and iCveOficina = "+iCveOficina+
          " and iCveDepartamento = "+iCveDepto+
          " and cDigitosFolio like '"+cDigitosFolio+"'"+
          " and iConsecutivo = "+iConsecutivo;

      lPStmt2 = conn.prepareStatement(lSQL2);
      lPStmt2.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }


    } catch(Exception ex){
      warn("insert",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(lPStmt2 != null){
          lPStmt2.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");
      return vData;
    }
  }

}

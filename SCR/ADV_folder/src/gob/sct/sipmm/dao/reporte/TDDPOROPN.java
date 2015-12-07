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
 * @author Levi Equihua
 * @version 1.0
 */

public class TDDPOROPN
    extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TWord rep = new TWord();

  public TDDPOROPN(){
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


  public Vector GenObservacionROPN(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{
  int iCveOficina, iCveDepto, iEjercicioSolicitud, iNumSolicitud, iCveReglaOperacion;
  String cNumOficioSolROP,cdtOficioSolROP,cPuertos,cNumOficialiaROP = "";
  Date dtOficioSolROP,dtToday;
  int iCvePMDP;
  int iCveSolicitante, iCveRepLegal;
  TDObtenDatos dObtenDatos = new TDObtenDatos();
  TVDinRep vData = new TVDinRep();

  String[] aFiltros = cFiltro.split(",");
  cNumOficioSolROP = String.valueOf(aFiltros[3]);
  cNumOficialiaROP = String.valueOf(aFiltros[4]);
  cdtOficioSolROP = String.valueOf(aFiltros[5]);
  iEjercicioSolicitud = Integer.parseInt(aFiltros[6],10);
  iNumSolicitud = Integer.parseInt(aFiltros[7],10);
  iCveReglaOperacion = Integer.parseInt(aFiltros[8],10);

  dtOficioSolROP = tFecha.getDateSQL(cdtOficioSolROP);

  try{
    rep.iniciaReporte();

    Vector vcData7 = findByCustom("","SELECT " +
                                  "ICVESOLICITANTE, " +
                                  "ICVEREPLEGAL " +
                                  "FROM TRAREGSOLICITUD " +
                                  "where IEJERCICIO = " +iEjercicioSolicitud+
                                  " and INUMSOLICITUD = "+iNumSolicitud);
    TVDinRep vSoli = (TVDinRep) vcData7.get(0);
   // iCveOficina = vOfiDep.getInt("ICVEOFICINA");
   // iCveDepto = vOfiDep.getInt("ICVEDEPARTAMENTO");
    iCveSolicitante = vSoli.getInt("ICVESOLICITANTE");
    iCveRepLegal = vSoli.getInt("ICVEREPLEGAL");
    Vector vctData8 = findByCustom("","SELECT "+
                               "GRLPUERTO.ICVEPUERTO, GRLPUERTO.CDSCPUERTO, " +
                               "GRLENTIDADFED.CABREVIATURA FROM DPOPUERTOXREGOPN " +
      "JOIN GRLPUERTO ON GRLPUERTO.ICVEPUERTO = DPOPUERTOXREGOPN.ICVEPUERTO " +
      "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEENTIDADFED = GRLPUERTO.ICVEENTIDADFED " +
      "WHERE DPOPUERTOXREGOPN.ICVEREGLAOPERACION = " + iCveReglaOperacion +
      " ORDER BY GRLPUERTO.CDSCPUERTO");
      int intPuertos = vctData8.size();
      TVDinRep vPuertos;
      if(intPuertos > 1)
         cPuertos = "de los puertos de ";
      else
         cPuertos = "del puerto de ";
      for(int intPos = 0; intPos < intPuertos; intPos++){
        vPuertos = (TVDinRep) vctData8.get(intPos);
        cPuertos += vPuertos.getString("CDSCPUERTO") + ", " +
           vPuertos.getString("CABREVIATURA") + "., ";
      }

    vData.put("iEjercicioSolicitud",iEjercicioSolicitud);
    vData.put("iNumSolicitud",iNumSolicitud);
    vData.put("iCveSistema",11);
    vData.put("iCveModulo",9);
    vData.put("iNumReporte",12);
    vData.put("cFolio",cFolio);
    vData.put("cAsunto","Oficio de Observaciones a las Reglas de Operación.");

    this.insertReportexFolio(vData, conn);

    dtToday = tFecha.TodaySQL();
    if(tFecha.getIntYear(dtToday) != tFecha.getIntYear(dtOficioSolROP))
      cdtOficioSolROP = tFecha.getFechaDDMMMMMYYYY(dtOficioSolROP," de ");
    else
      cdtOficioSolROP = tFecha.getStringDay(dtOficioSolROP) +" de "+ tFecha.getMonthName(dtOficioSolROP)+ " del año en curso";

    rep.comRemplaza("[cNumOficioSolicitudROP]",cNumOficioSolROP);
    rep.comRemplaza("[cNumOficialiaROP]",cNumOficialiaROP);
    rep.comRemplaza("[cNumOficioSol]",cNumOficioSolROP);
    rep.comRemplaza("[dtOficioSol]",cdtOficioSolROP);
    rep.comRemplaza("[cPuertos]",cPuertos);

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

  public Vector GenAutorizaROPN(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{
    int iCveOficina, iCveDepto, iEjercicioSolicitud, iNumSolicitud, iCveReglaOperacion;
    int iCveSolicitante, iCveRepLegal;
    String cNumOficioSolROP,cdtOficioSolROP,cPuertos,cNumOficialiaROP = "";
    Date dtOficioSolROP,dtToday;
    int iCvePMDP;
    TDObtenDatos dObtenDatos = new TDObtenDatos();
    TVDinRep vData = new TVDinRep();

    String[] aFiltros = cFiltro.split(",");
    cNumOficioSolROP = String.valueOf(aFiltros[3]);
    cNumOficialiaROP = String.valueOf(aFiltros[4]);
    cdtOficioSolROP = String.valueOf(aFiltros[5]);
    iEjercicioSolicitud = Integer.parseInt(aFiltros[6],10);
    iNumSolicitud = Integer.parseInt(aFiltros[7],10);
    iCveReglaOperacion = Integer.parseInt(aFiltros[8],10);

    dtOficioSolROP = tFecha.getDateSQL(cdtOficioSolROP);

    try{
      rep.iniciaReporte();

      Vector vcData3 = findByCustom("","SELECT " +
                                    "ICVESOLICITANTE, " +
                                    "ICVEREPLEGAL " +
                                    "FROM TRAREGSOLICITUD " +
                                    "where IEJERCICIO = " +iEjercicioSolicitud+
                                    " and INUMSOLICITUD = "+iNumSolicitud);
      TVDinRep vSoli = (TVDinRep) vcData3.get(0);
    //  iCveOficina = vOfiDep.getInt("ICVEOFICINA");
    //  iCveDepto = vOfiDep.getInt("ICVEDEPARTAMENTO");
      iCveSolicitante = vSoli.getInt("ICVESOLICITANTE");
      iCveRepLegal = vSoli.getInt("ICVEREPLEGAL");
      Vector vctData4 = findByCustom("","SELECT "+
                                     "GRLPUERTO.ICVEPUERTO, GRLPUERTO.CDSCPUERTO, " +
                                     "GRLENTIDADFED.CABREVIATURA FROM DPOPUERTOXREGOPN " +
            "JOIN GRLPUERTO ON GRLPUERTO.ICVEPUERTO = DPOPUERTOXREGOPN.ICVEPUERTO " +
            "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEENTIDADFED = GRLPUERTO.ICVEENTIDADFED " +
            "WHERE DPOPUERTOXREGOPN.ICVEREGLAOPERACION = " + iCveReglaOperacion +
            " ORDER BY GRLPUERTO.CDSCPUERTO");
       int intPuertos = vctData4.size();
       TVDinRep vPuertos;
       if(intPuertos > 1)
          cPuertos = "de los puertos de ";
       else
          cPuertos = "del puerto de ";
       for(int intPos = 0; intPos < intPuertos; intPos++){
         vPuertos = (TVDinRep) vctData4.get(intPos);
         cPuertos += vPuertos.getString("CDSCPUERTO") + ", " +
            vPuertos.getString("CABREVIATURA") + "., ";
       }

      vData.put("iEjercicioSolicitud",iEjercicioSolicitud);
      vData.put("iNumSolicitud",iNumSolicitud);
      vData.put("iCveSistema",11);
      vData.put("iCveModulo",9);
      vData.put("iNumReporte",10);
      vData.put("cFolio",cFolio);
      vData.put("cAsunto","Autorización de las Reglas de Operación");

      this.insertReportexFolio(vData, conn);

      dtToday = tFecha.TodaySQL();
      if(tFecha.getIntYear(dtToday) != tFecha.getIntYear(dtOficioSolROP))
        cdtOficioSolROP = tFecha.getFechaDDMMMMMYYYY(dtOficioSolROP," de ");
      else
        cdtOficioSolROP = tFecha.getStringDay(dtOficioSolROP) +" de "+ tFecha.getMonthName(dtOficioSolROP)+ " del año en curso";

      rep.comRemplaza("[cNumOficioSolicitudROP]",cNumOficioSolROP);
      rep.comRemplaza("[cNumOficialiaROP]",cNumOficialiaROP);
      rep.comRemplaza("[cNumOficioSol]",cNumOficioSolROP);
      rep.comRemplaza("[dtOficioSol]",cdtOficioSolROP);
      rep.comRemplaza("[cPuertos]",cPuertos);

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

  public Vector GenDifusionROPN(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{

     cError = "";
     int iCveOficina, iCveDepto, iEjercicioSolicitud, iNumSolicitud;
     String cComentarios, cOficio, cFechaDesplegar, cNumOficioSolROP, cNumOficialiaROP = "";
     String cdtOficioSolROP, cPuertos = "";
     int iCvePMDP, iCveReglaOperacion;
     int iCveSolicitante, iCveRepLegal;
     Date dtToday, dtAsignacion;
     TVDinRep vData = new TVDinRep();
     TDObtenDatos dObtenDatos = new TDObtenDatos();
     String[] aFiltros = cFiltro.split(",");
     iCvePMDP = Integer.parseInt(aFiltros[0],10);
     cOficio = aFiltros[1];
     dtAsignacion = tFecha.getDateSQL(aFiltros[2]);
     dtToday = tFecha.TodaySQL();
     cNumOficioSolROP = String.valueOf(aFiltros[3]);
     cNumOficialiaROP = String.valueOf(aFiltros[4]);
     cdtOficioSolROP = String.valueOf(aFiltros[5]);
     iEjercicioSolicitud = Integer.parseInt(aFiltros[6],10);
     iNumSolicitud = Integer.parseInt(aFiltros[7],10);
     iCveReglaOperacion = Integer.parseInt(aFiltros[8],10);
     try{
        rep.iniciaReporte();

        Vector vcData5 = findByCustom("","SELECT " +
                             "ICVESOLICITANTE, " +
                             "ICVEREPLEGAL " +
                             "FROM TRAREGSOLICITUD " +
                             "where IEJERCICIO = " +iEjercicioSolicitud+
                             " and INUMSOLICITUD = "+iNumSolicitud);
        TVDinRep vSoli = (TVDinRep) vcData5.get(0);
    //    iCveOficina = vOfiDep.getInt("ICVEOFICINA");
    //    iCveDepto = vOfiDep.getInt("ICVEDEPARTAMENTO");
        iCveSolicitante = vSoli.getInt("ICVESOLICITANTE");
        iCveRepLegal = vSoli.getInt("ICVEREPLEGAL");
        Vector vctData6 = findByCustom("","SELECT "+
                              "GRLPUERTO.ICVEPUERTO, GRLPUERTO.CDSCPUERTO, " +
                              "GRLENTIDADFED.CABREVIATURA FROM DPOPUERTOXREGOPN " +
        "JOIN GRLPUERTO ON GRLPUERTO.ICVEPUERTO = DPOPUERTOXREGOPN.ICVEPUERTO " +
        "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEENTIDADFED = GRLPUERTO.ICVEENTIDADFED " +
        "WHERE DPOPUERTOXREGOPN.ICVEREGLAOPERACION = " + iCveReglaOperacion +
        " ORDER BY GRLPUERTO.CDSCPUERTO");
        int intPuertos = vctData6.size();
        TVDinRep vPuertos;
        if(intPuertos > 1)
           cPuertos = "de los puertos de ";
        else
           cPuertos = "del puerto de ";
        for(int intPos = 0; intPos < intPuertos; intPos++){
           vPuertos = (TVDinRep) vctData6.get(intPos);
           cPuertos += vPuertos.getString("CDSCPUERTO") + ", " +
           vPuertos.getString("CABREVIATURA") + "., ";
        }

       //  dObtenDatos.dPMDP.setCvePMDP(iCvePMDP);
       //  cComentarios = dObtenDatos.dPMDP.getComentarios();
   /*     iCveOficina = dObtenDatos.dPMDP.getCveOficina();
        iCveDepto = dObtenDatos.dPMDP.getCveDepto();
        iEjercicioSolicitud = dObtenDatos.dPMDP.getEjercicio();
        iNumSolicitud = dObtenDatos.dPMDP.getSolicitud(); */
        vData.put("iEjercicioSolicitud",iEjercicioSolicitud);
        vData.put("iNumSolicitud",iNumSolicitud);
        vData.put("iCveSistema",11);
        vData.put("iCveModulo",9);
        vData.put("iNumReporte",11);
        vData.put("cFolio",cFolio);
        vData.put("cAsunto","Difusión de las Reglas de Operación");

        this.insertReportexFolio(vData, conn);

        if(tFecha.getIntYear(dtToday) != tFecha.getIntYear(dtAsignacion))
           cFechaDesplegar = tFecha.getFechaDDMMMMMYYYY(dtAsignacion," de ");
        else
           cFechaDesplegar = tFecha.getStringDay(dtAsignacion) +" de "+ tFecha.getMonthName(dtAsignacion)+ " del año en curso";


//Falta el Numero de oficio que viene de la pantalla que lo manda a llamar como filtro al parecer, y el dtAsignacion,
// que esta en GRLFOlio
        rep.comRemplaza("[cOficio]",cOficio);
        rep.comRemplaza("[dtAsignacion]",cFechaDesplegar);
        rep.comRemplaza("[cPuertos]",cPuertos);

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


     public Vector GenOpnIntROPN(String cFiltro,String cFolio,String cCveOfic,
                                    String cCveDepto) throws Exception{
          cError = "";
          int iCveSolicitante, iCveOficina=0, iCveDepto=0,iEjercicioSolicitud,iNumSolicitud;
          String cSolicitante="", cComentario="", cConsecutivoFolio = "", cRefTemp="", cReferencia = "",
              cNumInterno="", cPuertos="";
          int iCveSegtoEntidad, iCveOpinionEntidad, lExt, iConsecutivoFolio, iEjercicio, iCveReglaOperacion;
          TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();

          try{
            rep.iniciaReporte();

            datosOpinion.dDatosOpnTra.setFiltrosOpn(cFiltro);
            iEjercicioSolicitud = datosOpinion.dDatosOpnTra.getiEjercicio();
            iNumSolicitud = datosOpinion.dDatosOpnTra.getiNumSolicitud();
            iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
            iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();
            String[] aFiltros = cFiltro.split(",");
            iCveReglaOperacion = Integer.parseInt(aFiltros[9],10);
    /*        Vector vcData = findByCustom("","SELECT " +
                                         "CCOMENTARIOSBREVESPMDP " +
                                         "FROM DPOPROGMAESTRODESPORT " +
                                         "where IEJERCICIOSOLICITUD = " +iEjercicioSolicitud+
                                         " and INUMSOLICITUD = "+iNumSolicitud);
            if(vcData.size() > 0){
              TVDinRep vPMDP = (TVDinRep) vcData.get(0);
              cComentario = vPMDP.getString("CCOMENTARIOSBREVESPMDP");
            } */

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

            Vector vctData8 = findByCustom("","SELECT "+
                      "GRLPUERTO.ICVEPUERTO, GRLPUERTO.CDSCPUERTO, " +
                      "GRLENTIDADFED.CABREVIATURA FROM DPOPUERTOXREGOPN " +
                      "JOIN GRLPUERTO ON GRLPUERTO.ICVEPUERTO = DPOPUERTOXREGOPN.ICVEPUERTO " +
                      "JOIN GRLENTIDADFED ON GRLENTIDADFED.ICVEENTIDADFED = GRLPUERTO.ICVEENTIDADFED " +
                      "WHERE DPOPUERTOXREGOPN.ICVEREGLAOPERACION = " + iCveReglaOperacion +
                      " ORDER BY GRLPUERTO.CDSCPUERTO");
           int intPuertos = vctData8.size();
           TVDinRep vPuertos;
           if(intPuertos > 1)
              cPuertos = "de los puertos de ";
           else
              cPuertos = "del puerto de ";
           for(int intPos = 0; intPos < intPuertos; intPos++){
              vPuertos = (TVDinRep) vctData8.get(intPos);
              cPuertos += vPuertos.getString("CDSCPUERTO") + ", " +
              vPuertos.getString("CABREVIATURA") + "., ";
           }

      /*
            if(cComentario.equalsIgnoreCase("null") || cComentario.equalsIgnoreCase(""))
              rep.comRemplaza("[cComentariosPMDP]"," -- ");
            else
              rep.comRemplaza("[cComentariosPMDP]",cComentario); */

            rep.comRemplaza("[cSolicitante]",cSolicitante);
            rep.comRemplaza("[cPuertos]",cPuertos);

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

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }else{
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
      }else{
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

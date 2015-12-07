package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.excepciones.*;
import java.sql.SQLException;
import java.util.Vector;
import gob.sct.sipmm.dao.reporte.*;
import java.sql.*;
import java.util.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;



/**
 * <p>Title: TDOpnConcesion.java</p>
 * <p>Description: DAO de reportes para la contestación de opiniones empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Ithamar Caballero Altamirano
 * @version 1.0
 */


public class TDOpnContestacion extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  TDObtenDatos obtenerDatos = new TDObtenDatos();
  TFechas tfecha = new TFechas();

  //private Object vData;

  public TDOpnContestacion(){
  }
/*Método para generar el reporte de "REVISIÓN DE DOCUMENTACIÓN TÉCNICA"*/
  public Vector genOMP03(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    TWord rep = new TWord();
    String cFecha;

    rep.iniciaReporte();

    cFecha = tfecha.getFechaDDMMMMMYYYY(tfecha.TodaySQL()," de ");
    rep.comRemplaza("[FechaHoy]",cFecha);

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }
/*Método para generar el reporte de "ANÁLISIS DE LA INFORMACIÓN TÉCNICA"*/
  public Vector genOMP04(String cQuery) throws Exception{
    TWord rep = new TWord();
    String cFecha;

    cFecha = tfecha.getFechaDDMMMMMYYYY(tfecha.TodaySQL()," de ");
    rep.comRemplaza("[FechaHoy]",cFecha);

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }
/*Método para generar Reporte de "VOLANTE DE CONTROL INTERNO" */
  public Vector genOMP08(String cQuery) throws Exception{
    java.sql.Date dtOficio = null;
    Vector vRegsFolio = new Vector();
    TWord rep = new TWord();
    TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();
    rep.iniciaReporte();
    String cNumOficio = "",cConsecutivoFolio = "", cNumFolio = "", cDigitoFolio = "",dOficioRemplazar = "";
    int iConsecutivoFolio = 0, iEjercicio = 0, iCveSegtoEntidad = 0, iConsecutivoSegto = 0;

    String[] aFiltros = cQuery.split(",");
    iConsecutivoSegto = Integer.parseInt(aFiltros[5],10);

    datosOpinion.dDatosOpnTra.setFiltrosOpn(cQuery);
    iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();

    String cQuery1 = "Select " +
        "GRLFolioXSegtoEnt.iEjercicioFolio as iEjer, " +
        "GRLFolioXSegtoEnt.iCveOficina as iCveOfic, " +
        "GRLFolioXSegtoEnt.iCveDepartamento as iCveDepto, " +
        "GRLFolioXSegtoEnt.cDigitosFolio as cDigFolio, " +
        "GRLFolioXSegtoEnt.iConsecutivo as iCons, " +
        "GRLFOLIO.CNUMOFICIALIAPARTES as cOficPartes, " +
        "GRLFOLIO.DTASIGNACION as dtAsig " +
        "from GRLFolioXSegtoEnt " +
        "left join GRLFOLIO on GRLFOLIO.IEJERCICIO = GRLFOLIOXSEGTOENT.IEJERCICIOFOLIO " +
        "and GRLFOLIo.ICVEOFICINA = GRLFOLIOXSEGTOENT.ICVEOFICINA " +
        "and grlfolio.ICVEDEPARTAMENTO = GRLFOLIOXSEGTOENT.ICVEDEPARTAMENTO " +
        "and GRLFOLIO.CDIGITOSFOLIO = GRLFOLIOXSEGTOENT.CDIGITOSFOLIO " +
        "and grlfolio.ICONSECUTIVO = GRLFOLIOXSEGTOENT.ICONSECUTIVO " +
        "where GRLFOLIOXSEGTOENT.ICVESEGTOENTIDAD = " +iCveSegtoEntidad+
        " and GRLFOLIOXSEGTOENT.LFOLIOREFERENINTERNO = 0 "+
        " and GRLFOLIOXSEGTOENT.ICONSECUTIVOSEGTOREF = "+iConsecutivoSegto+
        " ORDER BY dtAsig ASC,cDigFolio ASC,iCons ASC ";


    vRegsFolio = GenSQLGenerico(vRegsFolio,cQuery1);

    if(vRegsFolio.size() > 0){
      TVDinRep vDatosFolio = (TVDinRep) vRegsFolio.get(0);
      iConsecutivoFolio = vDatosFolio.getInt("iCons");
      iEjercicio = vDatosFolio.getInt("iEjer");

      if(iConsecutivoFolio >= 10 && iConsecutivoFolio < 100)
        cConsecutivoFolio = "0" + iConsecutivoFolio;
      else if(iConsecutivoFolio < 10)
        cConsecutivoFolio = "00" + iConsecutivoFolio;

      cDigitoFolio = vDatosFolio.getString("cDigFolio");
      if(cDigitoFolio.equalsIgnoreCase("null"))
        cDigitoFolio = " ";

      cNumOficio = cDigitoFolio + "."+cConsecutivoFolio + "/"+iEjercicio;
      dtOficio = vDatosFolio.getDate("dtAsig");
      cNumFolio = vDatosFolio.getString("cOficPartes");

      dOficioRemplazar = tfecha.getFechaDDMMMYYYY(dtOficio," de ");

      if(!cNumOficio.equalsIgnoreCase("null"))
        rep.comRemplaza("[NumOficio]",cNumOficio);
      else
        rep.comRemplaza("[NumOficio]"," ");

      if(!dOficioRemplazar.equalsIgnoreCase("null"))
        rep.comRemplaza("[FechaOficio]",dOficioRemplazar);
      else
        rep.comRemplaza("[FechaOficio]"," ");

      if(!cNumFolio.equalsIgnoreCase("null"))
        rep.comRemplaza("[NumFolio]",cNumFolio);
      else
        rep.comRemplaza("[NumFolio]"," ");
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }
/*Método para generar el Reporte de "APROBACIÓN TÉCNICA"*/
  public Vector genOMPAprobTec(String cFiltro,String cFolio,String cCveOfic,
                                    String cCveDepto) throws Exception{
    Vector vRegsFolioxSeg = new Vector();
    String cReferencia = "",cConsecutivoFolio = "",cRefTemp = "", cNumInterno = "",cNombre;
    int lExt = 0,iConsecutivoFolio = 0, iEjercicio = 0, iCveSegtoEntidad = 0, iCveDepto = 0, iCveOficina = 0,
        iCveOpinionEntidad, iCveSegtoEntidadContes,iEjercicioSol,iNumSolicitud;
    TWord rep = new TWord();
    TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();
    rep.iniciaReporte();

    String[] aFiltros = cFiltro.split(",");
    iCveSegtoEntidadContes = Integer.parseInt(aFiltros[4],10);

    datosOpinion.dDatosOpnTra.setFiltrosOpn(cFiltro);
    iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
    iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();
    iEjercicioSol = datosOpinion.dDatosOpnTra.getiEjercicio();
    iNumSolicitud = datosOpinion.dDatosOpnTra.getiNumSolicitud();

    datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cFolio);

    datosOpinion.dDatosOpnCon.setFiltro(iCveSegtoEntidadContes);
    iCveOficina = datosOpinion.dDatosOpnCon.getiOficinaCon();
    iCveDepto = datosOpinion.dDatosOpnCon.getiDeptoCon();


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

    vRegsFolioxSeg = GenSQLGenerico(vRegsFolioxSeg,cQuery1);

    if(vRegsFolioxSeg.size() > 0){
      for(int i = 0; i < vRegsFolioxSeg.size(); i++){
        TVDinRep vDatosFolioxSeg = (TVDinRep) vRegsFolioxSeg.get(i);
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

//        rep.comRemplaza("[cReferencias]",cReferencia);
//        rep.comRemplaza("[cNumerosInternos]",cNumInterno);
        rep.comRemplaza("[NombreSolicitante]",cNombre);

      }

      // Llamado 2 empleado para oficina y depto fijos destino externo
      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              iCveOficina,
                                              iCveDepto,
                                              0,0,0,
                                              "","","REF.: "+cReferencia,"INT.: "+cNumInterno,
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());

    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }
/*Método para generar el Reporte de "Solicitud de Información Complementaria"*/
  public Vector genOMPSolCompl(String cFiltro,String cFolio,String cCveOfic,
                                    String cCveDepto) throws Exception{
    Vector vRegsFolioxSeg = new Vector();
    String cReferencia = "",cConsecutivoFolio = "",cRefTemp = "", cNumInterno = "",cFecha, cNombre;
    int lExt = 0,iConsecutivoFolio = 0, iEjercicio = 0, iCveSegtoEntidad = 0, iCveOpinionEntidad, iCveDepto, iCveOficina,
        iCveSegtoEntidadContes = 0, iEjercicioSol, iNumSolicitud;
    TWord rep = new TWord();
    TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();
    rep.iniciaReporte();


    cFecha = tfecha.getFechaDDMMMMMYYYY(tfecha.TodaySQL()," de ");

    String[] aFiltros = cFiltro.split(",");
    iCveSegtoEntidadContes = Integer.parseInt(aFiltros[4],10);



    datosOpinion.dDatosOpnTra.setFiltrosOpn(cFiltro);
    iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
    iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();
    iEjercicioSol = datosOpinion.dDatosOpnTra.getiEjercicio();
    iNumSolicitud = datosOpinion.dDatosOpnTra.getiNumSolicitud();

    datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cFolio);

    datosOpinion.dDatosOpnCon.setFiltro(iCveSegtoEntidadContes);
    iCveOficina = datosOpinion.dDatosOpnCon.getiOficinaCon();
    iCveDepto = datosOpinion.dDatosOpnCon.getiDeptoCon();

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

    vRegsFolioxSeg = GenSQLGenerico(vRegsFolioxSeg,cQuery1);

    if(vRegsFolioxSeg.size() > 0){
      for(int i = 0; i < vRegsFolioxSeg.size(); i++){
        TVDinRep vDatosFolioxSeg = (TVDinRep) vRegsFolioxSeg.get(i);
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

        rep.comRemplaza("[FechaMemorandum]",cFecha);
        rep.comRemplaza("[NombreSolicitante]",cNombre);

      }


      // Llamado 2 empleado para oficina y depto fijos destino externo
      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              iCveOficina,
                                              iCveDepto,
                                              0,0,0,
                                              "","","REF.: "+cReferencia,"INT.: "+cNumInterno,
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());

    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }


  public Vector GenSQLGenerico(Vector vRegistros,String SentenciaSQL) throws
      Exception{
    try{
      vRegistros = super.FindByGeneric("",SentenciaSQL,dataSourceName);
    } catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return vRegistros;
  }

}



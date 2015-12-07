package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.excepciones.*;
import java.sql.SQLException;
import java.util.Vector;
import gob.sct.sipmm.dao.TDNAVPermisos;

/**
 * <p>Title: TDTramite.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICE. Arturo Lopez Peña
 * @version 1.0
 */

public class TDNavegacion
    extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  // public TDObtenDatos.TDDatosOficinaDepto dOficDepto;
  public TDNavegacion(){
  }

  public StringBuffer ArribosWord(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    TWord rep = new TWord();
    TFechas Fecha = new TFechas();
    rep.iniciaReporte();
    try{
      vRegs = super.FindByGeneric("",cQuery,dataSourceName);
    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }

    if(vRegs.size() > 0){
      TVDinRep vDatos1 = (TVDinRep) vRegs.get(0);
      //[iEjercicio]|[iNumArribo]|[cNavegacion]|[cNombreEmbarcacion]|[cUAB]|[cUAN]|[cBandera]|[cNombreCapitan]|[cPuertoDestino]|[cNumeroTripulantes]|[cMunicipioCapitania]|[cAbrevEntidadFedCapitania]|");
      rep.comRemplaza("[iEjercicio]",vDatos1.get("iEjercicio").toString());
      rep.comRemplaza("[iNumArribo]",vDatos1.get("iNumArribo").toString());
      rep.comRemplaza("[cNavegacion]",vDatos1.get("TipoNavega").toString());
      rep.comRemplaza("[cNombreEmbarcacion]",vDatos1.get("cNomEmbarcacion").toString());
      rep.comRemplaza("[cUAB]",vDatos1.get("dArqueoBruto").toString());
      rep.comRemplaza("[cUAN]",vDatos1.get("dArqueoNeto").toString());
      rep.comRemplaza("[cBandera]",vDatos1.get("cNombre").toString());
      rep.comRemplaza("[cNombreCapitan]",vDatos1.get("cCapitan").toString());
      rep.comRemplaza("[cPuertoDestino]",vDatos1.get("cPuertoDestino").toString());
      rep.comRemplaza("[cNumeroTripulantes]",
                      vDatos1.get("iNumTripulantes").toString());
      rep.comRemplaza("[cMunicipioCapitania]",vDatos1.get("Municipio").toString());
      rep.comRemplaza("[cAbrevEntidadFedCapitania]",vDatos1.get("EntFed").toString());

      //[cNumPasajerosEmbarcados]|[cCargaEmbarada]|[cNumPasajerosTransito]|[cCargaTransito]|[cTitularOficina]|[cFechaZarpePrevisto]|[cHoraZarpePrevisto]|");//[cFechaZarpePrevisto]|
      rep.comRemplaza("[cNumPasajerosEmbarcados]",vDatos1.get("iNumPasajerosEmb").toString());
      rep.comRemplaza("[cCargaEmbarada]",vDatos1.get("iNumTonCargaEmb").toString());
      rep.comRemplaza("[cNumPasajerosTransito]",vDatos1.get("iNumPasajerosTransito").toString());
      rep.comRemplaza("[cCargaTransito]",vDatos1.get("iNumTonCargaTransito").toString());
      rep.comRemplaza("[cTitularOficina]",vDatos1.get("cTitular").toString());
      rep.comRemplaza("[cHoraZarpePrevisto]",Fecha.getStringHoraHHMM_24(vDatos1.getTimeStamp("tsZarpeProgramado"),":"));
      rep.comRemplaza("[cFechaZarpePrevisto]",Fecha.getDateSPN(Fecha.getDateSQL(vDatos1.getTimeStamp("tsZarpeProgramado"))).toString());
      //         rep.comRemplaza(sbResBuscar.toString(),                            .toString(),"01");

    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getEtiquetas(true);
  }

  public StringBuffer arribosExcel(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    StringBuffer sbRes = new StringBuffer("");
    cError = "";
    TFechas Fecha = new TFechas();
    TExcel rep = new TExcel();

    try{
      vRegs = super.FindByGeneric("",cQuery,dataSourceName);

    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }

    rep.iniciaReporte();
    try{
      if(vRegs.size() > 0){
        TVDinRep vDatos1 = (TVDinRep) vRegs.get(0);
        rep.comDespliega("I",4,vDatos1.get("Municipio").toString() + ", " +vDatos1.get("EntFed").toString());
        rep.comDespliega("I",6,vDatos1.getString("iNumArribo")+"/"+vDatos1.getString("iejercicio"));
        rep.comDespliega("C",9,vDatos1.get("cNomEmbarcacion").toString());
        rep.comDespliega("I",9,vDatos1.get("dArqueoBruto").toString());
        rep.comDespliega("B",11,vDatos1.get("dArqueoNeto").toString());
        rep.comDespliega("G",11,vDatos1.get("cNombre").toString());
        rep.comDespliega("G",15,vDatos1.get("cCapitan").toString());
        rep.comDespliega("I",17,vDatos1.get("cPuertoDestino").toString());
        rep.comDespliega("B",19,vDatos1.get("iNumTripulantes").toString());
        rep.comDespliega("K",19,vDatos1.get("iNumPasajerosEmb").toString());
        rep.comDespliega("C",21,vDatos1.get("iNumTonCargaEmb").toString());
        rep.comDespliega("I",21,vDatos1.get("iNumPasajerosTransito").toString());
        rep.comDespliega("A",23,vDatos1.get("iNumTonCargaTransito").toString());
        rep.comDespliega("A",26,vDatos1.get("Municipio").toString() + ", " +vDatos1.get("EntFed").toString());
        rep.comDespliega("D",26,Fecha.getDateSPN(Fecha.TodaySQL()));
        rep.comDespliega("F",32,vDatos1.get("cTitular").toString());
        rep.comDespliega("C",34,vDatos1.getString("CAGENCIACONSIGNATARIA"));
        rep.comDespliega("D",36,Fecha.getStringHoraHHMM_24(vDatos1.getTimeStamp("tsZarpeProgramado"),":"));
        rep.comDespliega("F",36,Fecha.getDateSPN(Fecha.getDateSQL(vDatos1.getTimeStamp("tsZarpeProgramado"))).toString());
        rep.comDespliega("D",37,Fecha.getStringHoraHHMM_24(vDatos1.getTimeStamp("tsZarpeProgramado"),":"));
        rep.comDespliega("F",37,Fecha.getDateSPN(Fecha.aumentaDisminuyeDias(Fecha.getDateSQL(vDatos1.getTimeStamp("tsZarpeProgramado")),2)));
      }
    }catch(Exception ex3){
      ex3.printStackTrace();
    }

    return rep.getSbDatos();
  }

  public StringBuffer arribosExcel2(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    StringBuffer sbRes = new StringBuffer("");
    cError = "";
    TFechas Fecha = new TFechas();
    TExcel rep = new TExcel();

    try{
      vRegs = super.FindByGeneric("",cQuery,dataSourceName);

    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }

    rep.iniciaReporte();
    try{
      if(vRegs.size() > 0){

        TVDinRep vDatos1 = (TVDinRep) vRegs.get(0);

        rep.comDespliega("I",4,vDatos1.get("Municipio").toString() + ", " +vDatos1.get("EntFed").toString());
        rep.comDespliega("I",6,vDatos1.getString("iNumArribo")+"/"+vDatos1.getString("iejercicio"));
        rep.comDespliega("C",9,vDatos1.get("cNomEmbarcacion").toString());
        rep.comDespliega("I",9,vDatos1.get("dArqueoBruto").toString());
        rep.comDespliega("B",11,vDatos1.get("dArqueoNeto").toString());
        rep.comDespliega("G",11,vDatos1.get("cNombre").toString());
        rep.comDespliega("G",15,vDatos1.get("cCapitan").toString());
        //rep.comDespliega("A",18,"DSADFAFSDEFDa");
        //System.out.println("vDatos1.get(cCapitan).toString(): "+vDatos1.get("cCapitan").toString());
        rep.comDespliega("A",19,vDatos1.get("cPuertoDestino").toString());
        rep.comDespliega("F",19,vDatos1.get("iNumTripulantes").toString());
        //rep.comDespliega("K",19,vDatos1.get("iNumPasajerosEmb").toString());
        //rep.comDespliega("C",21,vDatos1.get("iNumTonCargaEmb").toString());
        //rep.comDespliega("I",21,vDatos1.get("iNumPasajerosTransito").toString());
        //rep.comDespliega("A",23,vDatos1.get("iNumTonCargaTransito").toString());
        rep.comDespliega("A",26,vDatos1.get("Municipio").toString() + ", " +vDatos1.get("EntFed").toString());
        rep.comDespliega("D",26,Fecha.getDateSPN(Fecha.TodaySQL()));
        rep.comDespliega("F",32,vDatos1.get("cTitular").toString());
        rep.comDespliega("C",34,vDatos1.getString("CAGENCIACONSIGNATARIA"));
        rep.comDespliega("D",36,Fecha.getStringHoraHHMM_24(vDatos1.getTimeStamp("tsZarpeProgramado"),":"));
        rep.comDespliega("F",36,Fecha.getDateSPN(Fecha.getDateSQL(vDatos1.getTimeStamp("tsZarpeProgramado"))).toString());


      }
    }catch(Exception ex3){
      ex3.printStackTrace();
    }
    //System.out.println("-------*****rep.getSbDatos(): "+rep.getSbDatos().toString());
    return rep.getSbDatos();
  }



  public StringBuffer arribosExcel3(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    StringBuffer sbRes = new StringBuffer("");
    cError = "";
    TFechas Fecha = new TFechas();
    TExcel rep = new TExcel();

    try{
      vRegs = super.FindByGeneric("",cQuery,dataSourceName);

    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }

    rep.iniciaReporte();
    try{
      if(vRegs.size() > 0){

        TVDinRep vDatos1 = (TVDinRep) vRegs.get(0);

        rep.comDespliega("I",4,vDatos1.get("Municipio").toString() + ", " +vDatos1.get("EntFed").toString());
        rep.comDespliega("I",6,vDatos1.get("iNumArribo").toString()+"/"+vDatos1.getString("iejercicio"));
        rep.comDespliega("C",9,vDatos1.get("cNomEmbarcacion").toString());
        rep.comDespliega("I",9,vDatos1.get("dArqueoBruto").toString());
        rep.comDespliega("B",11,vDatos1.get("dArqueoNeto").toString());
        rep.comDespliega("G",11,vDatos1.get("cNombre").toString());
        rep.comDespliega("G",15,vDatos1.get("cCapitan").toString());
        //rep.comDespliega("A",18,"DSADFAFSDEFDa");
        //System.out.println("vDatos1.get(cCapitan).toString(): "+vDatos1.get("cCapitan").toString());
        //rep.comDespliega("A",19,vDatos1.get("cPuertoDestino").toString());
        rep.comDespliega("A",19,vDatos1.getString("CPERMISOPESCA"));
        rep.comDespliega("D",19,vDatos1.get("iNumTripulantes").toString());
        rep.comDespliega("K",19,vDatos1.getString("IOBSERVADORESPESCA"));
        //rep.comDespliega("C",21,vDatos1.get("iNumTonCargaEmb").toString());
        //rep.comDespliega("I",21,vDatos1.get("iNumPasajerosTransito").toString());
        //rep.comDespliega("A",23,vDatos1.get("iNumTonCargaTransito").toString());
        rep.comDespliega("A",26,vDatos1.get("Municipio").toString() + ", " +vDatos1.get("EntFed").toString());
        rep.comDespliega("D",26,Fecha.getDateSPN(Fecha.TodaySQL()));
        rep.comDespliega("F",32,vDatos1.get("cTitular").toString());
        rep.comDespliega("C",33,vDatos1.getString("CAGENCIACONSIGNATARIA"));
        rep.comDespliega("D",35,Fecha.getStringHoraHHMM_24(vDatos1.getTimeStamp("tsZarpeProgramado"),":"));
        rep.comDespliega("F",35,Fecha.getDateSPN(Fecha.getDateSQL(vDatos1.getTimeStamp("tsZarpeProgramado"))).toString());


      }
    }catch(Exception ex3){
      ex3.printStackTrace();
    }
    //System.out.println("-------*****rep.getSbDatos(): "+rep.getSbDatos().toString());
    return rep.getSbDatos();
  }



  public Vector AutorizPermanenciaAguasNac(String cDatos) throws
      Exception{
    TWord rep = new TWord();
    Vector vRegsE = new Vector(), vRegsD = new Vector(), vRegsDg = new Vector();
    TFechas tFecha = new TFechas();
    String cEmbarcacion = "", cDepto = "", cTitularDGMM="",cDigitos="";
    String[] cValores = cDatos.split("=");

    int iCveMarinaMercante = Integer.parseInt(VParametros.getPropEspecifica("DireccionGeneralMM"));

    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector();
    String cTipoArtefacto="",cPaisOrigenEmbarcacion="",cFolio="", cDigitosFolio = "", cOficinaResuelve = "", cDeptoResuelve = "";


    try{
      rep.iniciaReporte();

      cEmbarcacion = "SELECT  GRLPais.cNombre, VEHTipoEmbarcacion.cDscTipoEmbarcacion " +
          " FROM VEHEmbarcacion    " +
          " JOIN GRLPais ON VEHEmbarcacion.iCvePaisAbanderamiento = GRLPais.iCvePais   " +
          " JOIN VEHTipoEmbarcacion ON VEHEmbarcacion.iCveTipoEmbarcacion = VEHTipoEmbarcacion.iCveTipoEmbarcacion " +
          " WHERE VEHEmbarcacion.iCveVehiculo= " + cValores[0];

      vRegsE = super.FindByGeneric("",cEmbarcacion,dataSourceName);


      cDepto = "SELECT cTitular, cPuestoTitular FROM GRLDEPTOXOFIC where ICVEOFICINA = " + cValores[21] +
           " and ICVEDEPARTAMENTO = " + iCveMarinaMercante;

      vRegsD = super.FindByGeneric("",cDepto,dataSourceName);


      cDigitos = "select CDIGITOSFOLIO, ICVEOFICINARESUELVE, ICVEDEPTORESUELVE " +
          "  from GRLDIGITOSFOLIOXDEPTO " +
          "  join GRLUSUARIOXOFIC on GRLUSUARIOXOFIC.icveoficina = GRLDIGITOSFOLIOXDEPTO.ICVEOFICINA " +
          "   and GRLUSUARIOXOFIC.ICVEDEPARTAMENTO = GRLDIGITOSFOLIOXDEPTO.ICVEDEPARTAMENTO " +
          "  join TRATRAMITEXOFIC on TRATRAMITEXOFIC.icveoficina = GRLDIGITOSFOLIOXDEPTO.ICVEOFICINA " +
          "   and TRATRAMITEXOFIC.icvedeptoresuelve = GRLDIGITOSFOLIOXDEPTO.ICVEDEPARTAMENTO " +
          " where GRLUSUARIOXOFIC.ICVEUSUARIO = " + cValores[23] +
          "   and TRATRAMITEXOFIC.ICVETRAMITE = " + cValores[22] +
          " order by GRLDIGITOSFOLIOXDEPTO.DTASIGNACION desc ";

      vRegsDg = super.FindByGeneric("",cDigitos,dataSourceName);


    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    }


    if(vRegsE.size() > 0){
       TVDinRep vDatosE = (TVDinRep) vRegsE.get(0);
       cTipoArtefacto = vDatosE.get("cDscTipoEmbarcacion").toString();
       cPaisOrigenEmbarcacion = vDatosE.get("cNombre").toString();

    }

    if(vRegsD.size() > 0){
             TVDinRep vDatosD = (TVDinRep) vRegsD.get(0);
             cTitularDGMM = vDatosD.get("cTitular").toString();
    }

    if(vRegsDg.size() > 0){
             TVDinRep vDatosDg = (TVDinRep) vRegsDg.get(0);
             cDigitosFolio = vDatosDg.get("cDigitosFolio").toString();
             cOficinaResuelve = vDatosDg.get("ICVEOFICINARESUELVE").toString();
             cDeptoResuelve = vDatosDg.get("ICVEDEPTORESUELVE").toString();
    }


/*
       0. iCveVehiculo       1. iEjercicioPermiso       2. iConsecutivoPermiso
       3. cNomEmbarcacion       4. cFechasolicitud      5. iHolograma
       6. cPrestaServAEmp       7. cNumContrato       8. cMotivoContrato

       9. dtIniServicio       10. dtFinServicio       11. dtAutorizacion       12. iGradosLatitud       13. iMinutosLatitud       14. dSegundosLatitud
       15. iGradosLongitud       16. iMinutosLongitud       17. dSegundosLongitud
 */

    rep.comRemplazaVerifica("[MotivoContrato]",cValores[8],true);
    StringBuffer sbBuscaTemp = rep.getEtiquetasBuscar();
    StringBuffer sbReemplazaTemp = rep.getEtiquetasRemplazar();

    rep.comRemplaza("[TipoArtefactoNaval]",cTipoArtefacto);
    rep.comRemplaza("[PaisOrigenEmbarcacion]",cPaisOrigenEmbarcacion);
    rep.comRemplaza("[EjercicioPermiso]",cValores[1]);
    rep.comRemplaza("[ConsecutivoPermiso]",cValores[2]);
    rep.comRemplaza("[NombreArtefactoNaval]",cValores[3]);
    rep.comRemplaza("[FechaSolicitud]",cValores[4]);
    rep.comRemplaza("[Holograma]",cValores[5]);
    rep.comRemplaza("[PrestaServEmp]",cValores[6]);
    rep.comRemplaza("[NumContrato]",cValores[7]);
    rep.comRemplaza("[IniServicio]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[9])));
    rep.comRemplaza("[FinServicio]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[10])));
    rep.comRemplaza("[Autorizacion]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[11])));
    rep.comRemplaza("[GradosLatitud]",cValores[12]);
    rep.comRemplaza("[MinutosLatitud]",cValores[13]);
    rep.comRemplaza("[SegundosLatitud]",cValores[14]);
    rep.comRemplaza("[GradosLongitud]",cValores[15]);
    rep.comRemplaza("[MinutosLongitud]",cValores[16]);
    rep.comRemplaza("[SegundosLongitud]",cValores[17]);
    rep.comRemplaza("[TitularDGMM]",cTitularDGMM);
    rep.comRemplaza("[cDigitosDepto]",cDigitosFolio);
    rep.comRemplaza("[Expedicion]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[24])));

    sbBuscaAdic = rep.getEtiquetasBuscar();
    sbReemplazaAdic = rep.getEtiquetasRemplazar();

    /*
    sbBuscaAdic.append("[TipoArtefactoNaval]|[PaisOrigenEmbarcacion]|[EjercicioPermiso]|[ConsecutivoPermiso]|[NombreArtefactoNaval]|")
            .append("[FechaSolicitud]|[Holograma]|[PrestaServEmp]|[NumContrato]|[IniServicio]|[FinServicio]|[Autorizacion]|")
            .append("[GradosLatitud]|[MinutosLatitud]|[SegundosLatitud]|[GradosLongitud]|[MinutosLongitud]|[SegundosLongitud]|")
             .append("[TitularDGMM]|[cDigitosDepto]|[Expedicion]|")
             .append(sbBuscaTemp.toString());

    sbReemplazaAdic.append(cTipoArtefacto+"|"+cPaisOrigenEmbarcacion+"|"+cValores[1]+"|"+cValores[2]+"|"+cValores[3]+"|")
                .append(cValores[4]+"|"+cValores[5]+"|"+cValores[6]+"|"+cValores[7]+"|")
                .append(tFecha.getDateSPN(tFecha.getDateSQL(cValores[9]))+"|"+tFecha.getDateSPN(tFecha.getDateSQL(cValores[10]))+"|"+tFecha.getDateSPN(tFecha.getDateSQL(cValores[11]))+"|")
                .append(cValores[12]+"|"+cValores[13]+"|"+cValores[14]+"|"+cValores[15]+"|"+cValores[16]+"|"+cValores[17]+"|"+cTitularDGMM+"|"+cDigitosFolio+"|"+tFecha.getDateSPN(tFecha.getDateSQL(cValores[24]))+"|")
                .append(sbReemplazaTemp.toString());*/

     return new TDGeneral().generaOficioWord("0",
                                            Integer.parseInt(cOficinaResuelve),Integer.parseInt(cDeptoResuelve),
                                            0,0,
                                            Integer.parseInt(cValores[18]),Integer.parseInt(cValores[19]),Integer.parseInt(cValores[20]),
                                            "","",
                                            "", "",
                                            true,"cCuerpo",vcCuerpo,
                                            true, vcCopiasPara,
                                            sbBuscaAdic, sbReemplazaAdic);


  }



  public Vector PermisoNavCabotaje(String cDatos) throws
      Exception{
    TWord rep = new TWord();

    Vector vRegsE = new Vector(), vRegsD = new Vector(), vRegsDg = new Vector(), vRegsPu = new Vector(), vRegsConsulta= new Vector(), vRegsContes = new Vector (), vcRegPermiso=new Vector();
     TFechas tFecha = new TFechas();
     String cEmbarcacion = "", cDepto = "", cTitularDGMM="",cDigitos="", cPuertos="", cPuertosT="", cConsulta = "", cContestacion = "";
     String[] cValores = cDatos.split("=");
     String cFechaSolicitud="";
     String cPoderNotarial="",cOnotariaPublica="",cEntidadFed="",cSiglas="";
     String cHologOrig = "";
     String dtRespNotificado = "";
     String cSolPrimerPermiso = "";
     String cFolioPrimerPermiso = "";

     int iCveMarinaMercante = Integer.parseInt(VParametros.getPropEspecifica("DireccionGeneralMM"));

     StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
     Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector();
     String cTipoArtefacto="",cPaisOrigenEmbarcacion="",cFolio="", cDigitosFolio = "", cOficinaResuelve = "", cDeptoResuelve = "";
     String cOficioConsulta = "", cOficialia = "", cFechaOficioConsulta = "", cOficioContestacion = "", cFechaContestacion = "" ;

     try{
       rep.iniciaReporte();

       cEmbarcacion = "SELECT  GRLPais.cNombre, VEHTipoEmbarcacion.cDscTipoEmbarcacion " +
           " FROM VEHEmbarcacion    " +
           " JOIN GRLPais ON VEHEmbarcacion.iCvePaisAbanderamiento = GRLPais.iCvePais   " +
           " JOIN VEHTipoEmbarcacion ON VEHEmbarcacion.iCveTipoEmbarcacion = VEHTipoEmbarcacion.iCveTipoEmbarcacion " +
           " WHERE VEHEmbarcacion.iCveVehiculo= " + cValores[0];

       vRegsE = super.FindByGeneric("",cEmbarcacion,dataSourceName);


       cDepto = "SELECT cTitular, cPuestoTitular FROM GRLDEPTOXOFIC where ICVEOFICINA = " + cValores[21] +
            " and ICVEDEPARTAMENTO = " + iCveMarinaMercante;

       vRegsD = super.FindByGeneric("",cDepto,dataSourceName);


       cDigitos = "select CDIGITOSFOLIO, ICVEOFICINARESUELVE, ICVEDEPTORESUELVE " +
           "  from GRLDIGITOSFOLIOXDEPTO " +
           "  join GRLUSUARIOXOFIC on GRLUSUARIOXOFIC.icveoficina = GRLDIGITOSFOLIOXDEPTO.ICVEOFICINA " +
           "   and GRLUSUARIOXOFIC.ICVEDEPARTAMENTO = GRLDIGITOSFOLIOXDEPTO.ICVEDEPARTAMENTO " +
           "  join TRATRAMITEXOFIC on TRATRAMITEXOFIC.icveoficina = GRLDIGITOSFOLIOXDEPTO.ICVEOFICINA " +
           "   and TRATRAMITEXOFIC.icvedeptoresuelve = GRLDIGITOSFOLIOXDEPTO.ICVEDEPARTAMENTO " +
           " where GRLUSUARIOXOFIC.ICVEUSUARIO = " + cValores[23] +
           "   and TRATRAMITEXOFIC.ICVETRAMITE = " + cValores[22] +
           " order by GRLDIGITOSFOLIOXDEPTO.DTASIGNACION desc ";

       vRegsDg = super.FindByGeneric("",cDigitos,dataSourceName);
       cPuertos = "Select iEjercicioPermiso,iConsecutivoPermiso,NAVPuertoOpera.iCvePuerto,cDscPuerto,cAbreviatura " +
           " from NAVPuertoOpera " +
           " join GRLPuerto on NAVPuertoOpera.icvepuerto=grlPuerto.icvepuerto " +
           " join GrlEntidadFed on GrlPuerto.iCveentidadFed=GrlEntidadFed.iCveEntidadFed "+
               "AND GRLENTIDADFED.ICVEPAIS = GRLPUERTO.ICVEPAIS" +
           " where iEjercicioPermiso= " + cValores[1] +
           " and iConsecutivoPermiso=" + cValores[2];
       vRegsPu = super.FindByGeneric("",cPuertos,dataSourceName);

       String cPermiso =
           "SELECT " +
           "  N.CPODERNOTARIAL, N.CNOTARIAPUBLICA,N.ICVEENTIDADNOTARIA, " +
           "  N.CSIGLAS, EF.CNOMBRE AS CENTIDADFED " +
           "FROM NAVPERMISOS N " +
           "LEFT JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = 1 AND EF.ICVEENTIDADFED = N.ICVEENTIDADNOTARIA " +
           "WHERE N.IEJERCICIOPERMISO=  "+cValores[1]+
           " AND N.ICONSECUTIVOPERMISO= "+cValores[2];

       vcRegPermiso = super.FindByGeneric("",cPermiso,dataSourceName);
       TVDinRep vPermiso = new TVDinRep();
       if(vcRegPermiso.size()>0){
         vPermiso = (TVDinRep) vcRegPermiso.get(0);
         cPoderNotarial= vPermiso.getString("CPODERNOTARIAL")=="null"?"":vPermiso.getString("CPODERNOTARIAL");
         cOnotariaPublica= vPermiso.getString("CNOTARIAPUBLICA")=="null"?"":vPermiso.getString("CNOTARIAPUBLICA");
         cEntidadFed= vPermiso.getString("CENTIDADFED")=="null"?"":vPermiso.getString("CENTIDADFED");
         cSiglas= vPermiso.getString("CSIGLAS")=="null"?"":vPermiso.getString("CSIGLAS");
       }

       cConsulta =  "SELECT  GRLFOLIOXSEGTOENT.CDigitosFolio, GRLFOLIOXSEGTOENT.ICONSECUTIVO, GRLFOLIO.CNUMOFICIALIAPARTES, GRLFolio.DTASIGNACION " +
           "FROM TRAOPNENTTRAMITE " +
           "JOIN GRLSEGTOENTIDAD ON GRLSEGTOENTIDAD.ICVESEGTOENTIDAD = TRAOPNENTTRAMITE.ICVESEGTOENTIDAD " +
           "     and GRLSEGTOENTIDAD.LESCONTESTACION = 0 " +
           "JOIN GRLFOLIOXSEGTOENT ON GRLSEGTOENTIDAD.ICVESEGTOENTIDAD = GRLFOLIOXSEGTOENT.ICVESEGTOENTIDAD " +
           "JOIN GRLFolio ON GRLFOLIO.IEJERCICIO = GRLFOLIOXSEGTOENT.IEJERCICIOFOLIO " +
           "    AND GRLFOLIO.ICVEOFICINA = GRLFOLIOXSEGTOENT.ICVEOFICINA " +
           "    AND GRLFOLIO.ICVEDEPARTAMENTO = GRLFOLIOXSEGTOENT.ICVEDEPARTAMENTO " +
           "    AND GRLFOLIO.CDIGITOSFOLIO = GRLFOLIOXSEGTOENT.CDIGITOSFOLIO " +
           "    AND GRLFOLIO.ICONSECUTIVO = GRLFOLIOXSEGTOENT.ICONSECUTIVO " +
           "JOIN GRLOPINIONENTIDAD ON TRAOPNENTTRAMITE.ICVEOPINIONENTIDAD=GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD "+
           " AND GRLOPINIONENTIDAD.LESOPINIONINTERNA = 0 " +
           "WHERE TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD =  " + cValores[25] +
           " and TRAOPNENTTRAMITE.INUMSOLICITUD =  " +  cValores[26] +
           " order by TRAOPNENTTRAMITE.ICVESEGTOENTIDAD DESC, GRLFOLIOXSEGTOENT.ICONSECUTIVOSEGTOREF DESC ";

       vRegsConsulta = super.FindByGeneric("",cConsulta,dataSourceName);

       cContestacion = "SELECT GRLSEGTOENTIDAD.DTOFICIOCON, GRLSEGTOENTIDAD.CNUMOFICIOCON FROM TRAOPNENTTRAMITE " +
           "JOIN GRLSEGTOENTIDAD ON GRLSEGTOENTIDAD.ICVESEGTOENTIDAD = TRAOPNENTTRAMITE.ICVESEGTOENTIDAD " +
           "     and GRLSEGTOENTIDAD.LESCONTESTACION = 1 " +
           "WHERE TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD =  " +  cValores[25] +
           " and TRAOPNENTTRAMITE.INUMSOLICITUD =  "  + cValores[26] +
           " order by TRAOPNENTTRAMITE.ICVESEGTOENTIDAD DESC ";

       String cSolicitud = " SELECT TSREGISTRO FROM TRAREGSOLICITUD " +
                           " WHERE IEJERCICIO="+  cValores[25] +
                           " AND INUMSOLICITUD="+ cValores[26];
       String cResNotificado = "SELECT TSREGISTRO FROM TRAREGETAPASXMODTRAM " +
                               " Where iEjercicio = "+cValores[25]+
                               "   and INUMSOLICITUD="+cValores[26]+
                               "   and ICVEETAPA=" +VParametros.getPropEspecifica("EtapaModificarTramite")+
                               " order by TSREGISTRO DESC ";


       vRegsContes = super.FindByGeneric("",cContestacion,dataSourceName);

       Vector vResNotificado = super.FindByGeneric("",cResNotificado,dataSourceName);
       if(vResNotificado.size()>0){
         dtRespNotificado = ((TVDinRep)vResNotificado.get(0)).getString("TSREGISTRO");
       }
       TDNAVPermisos Nav = new TDNAVPermisos();
       cSolPrimerPermiso = cValores[2]+"/"+cValores[1];
       cFolioPrimerPermiso = cDigitosFolio;
       Nav.fGuardaFolios(cSolPrimerPermiso,cFolioPrimerPermiso,Integer.parseInt(cValores[0],10));

       String cPermisoOrig =
           " SELECT iEjercicioPermiso, iConsecutivoPermiso,iHolograma,cSolicitudPrimerPermiso,cFolioPrimerPermiso " +
           " FROM NAVPERMISOS " +
           " WHERE iCveVehiculo = " +cValores[0]+
           " ORDER BY dtIniServicio ";

       //SE GUARDA LOS VALORES DE LA PRIMERA SOLICITUD Y FOLIO DE ESTE PERMISO.

       Vector vcHologOrig = this.FindByGeneric("",cPermisoOrig,dataSourceName);
       if(vcHologOrig.size()>0){
         cHologOrig = ( (TVDinRep) vcHologOrig.get(0)).getString("iHolograma");
         cSolPrimerPermiso = ( (TVDinRep) vcHologOrig.get(0)).getString("cSolicitudPrimerPermiso");
         cFolioPrimerPermiso = ( (TVDinRep) vcHologOrig.get(0)).getString("cFolioPrimerPermiso");
       }


       TVDinRep vSolicitud = new TVDinRep();
       Vector vcSolicitud = super.FindByGeneric("",cSolicitud,dataSourceName);
       if(vcSolicitud.size()>0){
         vSolicitud = (TVDinRep) vcSolicitud.get(0);
         cFechaSolicitud = ""+tFecha.getDateSPN(tFecha.getDateSQL(vSolicitud.getTimeStamp("TSREGISTRO")));
       }
     } catch(SQLException ex){
       cMensaje = ex.getMessage();
     }


     if(vRegsE.size() > 0){
        TVDinRep vDatosE = (TVDinRep) vRegsE.get(0);
        cTipoArtefacto = vDatosE.get("cDscTipoEmbarcacion").toString();
        cPaisOrigenEmbarcacion = vDatosE.get("cNombre").toString();
     }

     if(vRegsD.size() > 0){
              TVDinRep vDatosD = (TVDinRep) vRegsD.get(0);
              cTitularDGMM = vDatosD.get("cTitular").toString();
     }

     if(vRegsDg.size() > 0){
              TVDinRep vDatosDg = (TVDinRep) vRegsDg.get(0);
              cDigitosFolio = vDatosDg.get("cDigitosFolio").toString();
              cOficinaResuelve = vDatosDg.get("ICVEOFICINARESUELVE").toString();
              cDeptoResuelve = vDatosDg.get("ICVEDEPTORESUELVE").toString();
     }

     if(vRegsPu.size() > 0){
       TVDinRep vDatosPto = (TVDinRep) vRegsPu.get(0);
       for(int i = 0;i < vRegsPu.size();i++){
         vDatosPto = (TVDinRep) vRegsPu.get(i);
         cPuertosT += vDatosPto.get("cDscPuerto").toString() + ", " +
               vDatosPto.get("cAbreviatura").toString() + "." + ", ";
       }
     }

     if(vRegsConsulta.size() > 0){
       TVDinRep vDatosConsulta = (TVDinRep) vRegsConsulta.get(0);
       cOficioConsulta = vDatosConsulta.get("CDigitosFolio").toString() + "." + vDatosConsulta.get("ICONSECUTIVO").toString();
       if (vDatosConsulta.get("CNUMOFICIALIAPARTES") != null && vDatosConsulta.get("CNUMOFICIALIAPARTES") != "")
          cOficialia = vDatosConsulta.get("CNUMOFICIALIAPARTES").toString();
       if (vDatosConsulta.getDate("DTASIGNACION") != null)
       cFechaOficioConsulta = tFecha.getDateSPN(vDatosConsulta.getDate("DTASIGNACION"));
     }

     if(vRegsContes.size() > 0){
       TVDinRep vDatosContestacion = (TVDinRep) vRegsContes.get(0);
       cOficioContestacion = vDatosContestacion.get("CNUMOFICIOCON").toString();
       if (vDatosContestacion.getDate("DTOFICIOCON") != null)
         cFechaContestacion  = tFecha.getDateSPN(vDatosContestacion.getDate("DTOFICIOCON"));
     }



     String cOficioPrimerPermiso="",cHologramaPrimerPermiso="";
 /*
        0. iCveVehiculo       1. iEjercicioPermiso       2. iConsecutivoPermiso
        3. cNomEmbarcacion       4. cFechasolicitud      5. iHolograma
        6. cPrestaServAEmp       7. cNumContrato       8. cMotivoContrato

        9. dtIniServicio       10. dtFinServicio       11. dtAutorizacion
        12. iGradosLatitud       13. iMinutosLatitud       14. dSegundosLatitud
        15. iGradosLongitud       16. iMinutosLongitud       17. dSegundosLongitud
  */

     rep.comRemplazaVerifica("[MotivoContrato]",cValores[8],true);
     StringBuffer sbBuscaTemp = rep.getEtiquetasBuscar();
     StringBuffer sbReemplazaTemp = rep.getEtiquetasRemplazar();
     String cNumTripulantes="", cDebera="";

     if(Integer.parseInt(cValores[24])>0){
       cDebera = ",  que deberá contar con ";
       cNumTripulantes = cValores[24]+" TRIPULANTES MEXICANOS";
     }

     rep.comRemplaza("[TipoEmbarcacion]",cTipoArtefacto);
     rep.comRemplaza("[PaisOrigenEmbarcacion]",cPaisOrigenEmbarcacion);
     rep.comRemplaza("[EjercicioPermiso]",cValores[1]);
     rep.comRemplaza("[ConsecutivoPermiso]",cValores[2]);
     rep.comRemplaza("[NombreEmbarcacion]",cValores[3]);
     rep.comRemplaza("[FechaSolicitud]",cValores[4]);
     rep.comRemplaza("[Holograma]",cValores[5]);
     rep.comRemplaza("[PrestaServEmp]",cValores[6]);
     rep.comRemplaza("[NumContrato]",cValores[7]);
     rep.comRemplaza("[IniServicio]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[9])));
     rep.comRemplaza("[FinServicio]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[10])));
     rep.comRemplaza("[Autorizacion]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[11])));
     rep.comRemplaza("[GradosLatitud]",cValores[12]);
     rep.comRemplaza("[MinutosLatitud]",cValores[13]);
     rep.comRemplaza("[SegundosLatitud]",cValores[14]);
     rep.comRemplaza("[GradosLongitud]",cValores[15]);
     rep.comRemplaza("[MinutosLongitud]",cValores[16]);
     rep.comRemplaza("[SegundosLongitud]",cValores[17]);
     rep.comRemplaza("[TitularDGMM]",cTitularDGMM);
     rep.comRemplaza("[cDigitosDepto]",cDigitosFolio);
     rep.comRemplaza("[Puertos]",cPuertosT);
     rep.comRemplaza("[NumTripulantesMex]",cNumTripulantes);
     rep.comRemplaza("[cDebera]",cDebera);
     rep.comRemplaza("[cOficioConsulta]",cOficioConsulta);
     rep.comRemplaza("[cOficialia]",cOficialia);
     rep.comRemplaza("[cFechaOficioConsulta]",cFechaOficioConsulta);
     rep.comRemplaza("[cOficioContestacion]",cOficioContestacion);
     rep.comRemplaza("[cFechaContestacion]",cFechaContestacion);
     rep.comRemplaza("[Expedicion]",tFecha.getDateSPN(tFecha.getDateSQL(cValores[27])));
     rep.comRemplaza("[cTestNotarial]",cValores[28]);
     rep.comRemplaza("[cObsPermiso]",cValores[29]);
     rep.comRemplaza("[cFechaSolicitud]",cFechaSolicitud);
     rep.comRemplaza("[cFechaEntregaNotificados]",dtRespNotificado);
     rep.comRemplaza("[cSolPrimerPermiso]",cSolPrimerPermiso);
     rep.comRemplaza("[cFolioPrimerPermiso]",cFolioPrimerPermiso);


     String cParrafoRevalidacion = "";
     if(cValores[30]!=""){
       cParrafoRevalidacion = "Este permiso constituye la "+cValores[30]+
           " del permiso concedido mediante oficio NO. "+cSolPrimerPermiso+
           ", holograma No. "+cHologOrig+" folio No "+cFolioPrimerPermiso+
           ", en términos de lo señalado en el artículo 40 de la "+
           "ley de Navegación y Comercio Marítimos.";
       rep.comRemplaza("[ParrafoRevalidacion]",cParrafoRevalidacion);
     }
     else rep.comRemplaza("[ParrafoRevalidacion]","");

     rep.comRemplaza("[cPoderNotarial]",cPoderNotarial);
     rep.comRemplaza("[cOnotariaPublica]",cOnotariaPublica);
     rep.comRemplaza("[cEntidadFed]",cEntidadFed);
     rep.comRemplaza("[cSiglas]",cSiglas);

     sbBuscaAdic = rep.getEtiquetasBuscar();
     sbReemplazaAdic = rep.getEtiquetasRemplazar();



    /* sbBuscaAdic.append("[TipoEmbarcacion]|[PaisOrigenEmbarcacion]|[EjercicioPermiso]|[ConsecutivoPermiso]|[NombreEmbarcacion]|")
             .append("[FechaSolicitud]|[Holograma]|[PrestaServEmp]|[NumContrato]|[IniServicio]|[FinServicio]|[Autorizacion]|")
             .append("[GradosLatitud]|[MinutosLatitud]|[SegundosLatitud]|[GradosLongitud]|[MinutosLongitud]|[SegundosLongitud]|")
             .append("[TitularDGMM]|[cDigitosDepto]|[Puertos]|[NumTripulantesMex]|[cDebera]|")
             .append("[cOficioConsulta]|[cOficialia]|[cFechaOficioConsulta]|[cOficioContestacion]|[cFechaContestacion]|[Expedicion]|[cTestNotarial]|[cObsPermiso]|")
             .append(sbBuscaTemp.toString());
         System.out.println("*****    "+cValores[29]);
     sbReemplazaAdic.append(cTipoArtefacto+"|"+cPaisOrigenEmbarcacion+"|"+cValores[1]+"|"+cValores[2]+"|"+cValores[3]+"|")
                 .append(cValores[4]+"|"+cValores[5]+"|"+cValores[6]+"|"+cValores[7]+"|")
                 .append(tFecha.getDateSPN(tFecha.getDateSQL(cValores[9]))+"|"+tFecha.getDateSPN(tFecha.getDateSQL(cValores[10]))+"|"+tFecha.getDateSPN(tFecha.getDateSQL(cValores[11]))+"|")
                 .append(cValores[12]+"|"+cValores[13]+"|"+cValores[14]+"|"+cValores[15]+"|"+cValores[16]+"|"+cValores[17]+"|"+
                         cTitularDGMM+"|"+cDigitosFolio+"|"+cPuertosT+"|"+cNumTripulantes+"|"+cDebera+"|")
                 .append(cOficioConsulta+"|"+cOficialia+"|"+cFechaOficioConsulta+"|"+cOficioContestacion+"|"+cFechaContestacion+"|"+tFecha.getDateSPN(tFecha.getDateSQL(cValores[27]))+"|"+cValores[28]+"|"+cValores[29]+"|")
                 .append(sbReemplazaTemp.toString());
*/
      Vector respuesta = new TDGeneral().generaOficioWord("0",
                                             Integer.parseInt(cOficinaResuelve),Integer.parseInt(cDeptoResuelve),
                                             0,0,
                                             Integer.parseInt(cValores[18]),Integer.parseInt(cValores[19]),Integer.parseInt(cValores[20]),
                                             "","",
                                             "", "",
                                             true,"cCuerpo",vcCuerpo,
                                             true, vcCopiasPara,
                                             sbBuscaAdic, sbReemplazaAdic);

       return respuesta;

  }



  public Vector ConsultaCamara(String cDatos,String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen) throws
      Exception{
    TWord rep = new TWord();
    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector();

    Vector vRegsE = new Vector();
    Vector vRegsP = new Vector();
    Vector vRegsD = new Vector();
    Vector vRegsPu = new Vector();
    Vector vOpini = new Vector();

    String cEmbarcacion = "", cPermisos = "", cPuertos = "", cPuertosT = "", cDepto = "", cOpinion = "";
//    String[] cValores = cDatos.split(",");
    String[] cValores = cDatos.split("=");
    String cValoresAdic = cValores[0].replaceAll(",","=");

    for (int i=1; i< cValores.length; i++){
       cValoresAdic+= "=" + cValores[i] ;
     }
    cValoresAdic+= "=";

    cValores = cValoresAdic.split("=");

//    System.out.println("cValoresAdic   : "+cValoresAdic);
    System.out.println("-....********************************Valores:      " );
    for (int inc = 0; inc < cValores.length ; inc ++)
      System.out.println("*****    Valores["+inc+"] = "+cValores[inc]);

    int iCvePersona, iCveDomicilio;
    int iCveSubdireccion = Integer.parseInt(VParametros.getPropEspecifica("SubdirNavegacion"));

    String cTipoEmbarcacion="",cNombreEmbarcacion="",cPaisOrigenEmbarcacion="",cArqueoBruto="",cArqueoNeto="",cEslora="",cManga="",cPuntal="",cCaladoMax="",cEjerConstruccion="",cPesoMuerto="",cPasajeros="",cBodegas="",cTanques="",cTripulantes="",cDescServicio="",cTitulo="",cTitular="",cSolicitante="";
    String cObservaciones = "";

    try{
      rep.iniciaReporte();

      cSolicitante =  cValores[12];
      cEmbarcacion = "SELECT  distinct (VEHEmbarcacion.iCveVehiculo),VEHEmbarcacion.cNomEmbarcacion, " +
          " VEHEmbarcacion.iCvePaisAbanderamiento, GRLPais.cNombre,VEHEmbarcacion.iCveTipoEmbarcacion, " +
          "VEHTipoEmbarcacion.cDscTipoEmbarcacion,VEHEmbarcacion.iCveTipoNavega,VEHTipoNavegacion.cDscTipoNavega," +
          " VEHEmbarcacion.iCveTipoServ,VEHTipoServicio.cDscTipoServ,VEHEmbarcacion.dEslora,  " +
          " VEHEmbarcacion.dManga,VEHEmbarcacion.iUniMedManga,VEHEmbarcacion.dPuntal,   " +
          " VEHEmbarcacion.dCaladoMax,VEHEmbarcacion.iUniMedCaladoMax,VEHEmbarcacion.dArqueoBruto," +
          " VEHEmbarcacion.iUniMedArqueoBruto,VEHEmbarcacion.dArqueoNeto,VEHEmbarcacion.iUniMedArqueoNeto," +
          " VEHEmbarcacion.dPesoMuerto,VEHEmbarcacion.iUniMedPesoMuerto,VEHEmbarcacion.iNumBodegas, " +
          " VEHEmbarcacion.iNumTanques,VEHEmbarcacion.iTripulacionMin, VEHEmbarcacion.iTripulacionMax,  " +
          " VEHEmbarcacion.dtConstruccion " +
          " FROM VEHEmbarcacion    " +
          " JOIN GRLPais ON VEHEmbarcacion.iCvePaisAbanderamiento = GRLPais.iCvePais   " +
          " JOIN VEHTipoEmbarcacion ON VEHEmbarcacion.iCveTipoEmbarcacion = VEHTipoEmbarcacion.iCveTipoEmbarcacion " +
          "  JOIN VEHTipoNavegacion ON VEHEmbarcacion.iCveTipoNavega = VEHTipoNavegacion.iCveTipoNavega    " +
          " JOIN VEHTipoServicio ON VEHEmbarcacion.iCveTipoServ = VEHTipoServicio.iCveTipoServ   " +
          " LEFT JOIN GRLPersona ON VEHEmbarcacion.iCvePropietario = GRLPersona.iCvePersona   " +
          " JOIN VEHVehiculo ON VEHEmbarcacion.iCveVehiculo = VEHVehiculo.iCveVehiculo  " +
          " WHERE VEHEmbarcacion.iCveVehiculo= " + cValores[4];

      cPermisos = "Select iEjercicioPermiso,iConsecutivoPermiso,iEjercicioSolicitud,iNumSolicitud,NAVPermisos.iCveVehiculo, " + //4
          " cPrestaServAEmp,cPrestaServAEmp,dtIniServicio,dtFinServicio,dtIniContFlet,dtFinContFlet, " + //10
          " dtIniContServ,dtFinContServ,dtIniSegProt,dtFinSegProt,iNumViajes,cMotivoContrato,dtAutorizacion, " + //17
          " dtFinVigencia,NAVPERMISOS.iNumPasajeros,iNumTripulantesMex,cObs,cNomEmbarcacion " + //22
          " from NAVPermisos " +
          " Join VEHEmbarcacion on NAVPermisos.iCveVehiculo=VEHEmbarcacion.iCveVehiculo " +
          " where iEjercicioPermiso= " + cValores[5] +
          " and iConsecutivoPermiso=" + cValores[6];

      cPuertos = "Select iEjercicioPermiso,iConsecutivoPermiso,NAVPuertoOpera.iCvePuerto,cDscPuerto,cAbreviatura " +
          " from NAVPuertoOpera " +
          " join GRLPuerto on NAVPuertoOpera.icvepuerto=grlPuerto.icvepuerto " +
          " join GrlEntidadFed on GrlPuerto.iCveentidadFed=GrlEntidadFed.iCveEntidadFed  AND GRLENTIDADFED.ICVEPAIS=GRLPUERTO.ICVEPAIS" +
          " where iEjercicioPermiso= " + cValores[5] +
          " and iConsecutivoPermiso=" + cValores[6];

      cDepto = "SELECT cTitular, cPuestoTitular FROM GRLDEPTOXOFIC where ICVEOFICINA = " + cValores[11] +
           " and ICVEDEPARTAMENTO = " + iCveSubdireccion;

       cOpinion = "SELECT TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD,TRAOPNENTTRAMITE.INUMSOLICITUD, GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD, " +
                  "GRLSEGTOENTIDAD.ICVESEGTOENTIDAD, GRLSEGTOENTIDAD.COBSESSEGTO, TRAOPNENTTRAMITE.COPNDIRIGIDOA, " +
                  "TRAOPNENTTRAMITE.CPTOOPINION, GRLOPINIONENTIDAD.COPINIONDIRIGIDOA, GRLOPINIONENTIDAD.CPUESTOOPINION " +
                  "FROM TRAOPNENTTRAMITE " +
                  "  JOIN GRLOPINIONENTIDAD ON GRLOPINIONENTIDAD.ICVEOPINIONENTIDAD = TRAOPNENTTRAMITE.ICVEOPINIONENTIDAD " +
                  "  JOIN GRLSEGTOENTIDAD ON GRLSEGTOENTIDAD.ICVESEGTOENTIDAD = TRAOPNENTTRAMITE.ICVESEGTOENTIDAD " +
                  "    WHERE  TRAOPNENTTRAMITE.IEJERCICIOSOLICITUD = " + cValores[0] +
                  "    AND TRAOPNENTTRAMITE.INUMSOLICITUD = " + cValores[1] +
                  "    AND TRAOPNENTTRAMITE.ICVESEGTOENTIDAD = " + cValores[3] +
                  "  ORDER BY  GRLSEGTOENTIDAD.ICVESEGTOENTIDAD ";

      vRegsD = super.FindByGeneric("",cDepto,dataSourceName);
      vRegsE = super.FindByGeneric("",cEmbarcacion,dataSourceName);
      vRegsP = super.FindByGeneric("",cPermisos,dataSourceName);
      vRegsPu = super.FindByGeneric("",cPuertos,dataSourceName);
      vOpini = super.FindByGeneric("",cOpinion,dataSourceName);

    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }

    if(vRegsE.size() > 0){
      TVDinRep vDatosE = (TVDinRep) vRegsE.get(0);
      String[] FechConst = vDatosE.get("dtConstruccion").toString().split("-");
      cTipoEmbarcacion = vDatosE.get("cDscTipoEmbarcacion").toString();
      cNombreEmbarcacion = vDatosE.get("cNomEmbarcacion").toString();
      cPaisOrigenEmbarcacion = vDatosE.get("cNombre").toString();
      cArqueoBruto = (vDatosE.getDouble("dArqueoBruto")==0?"- ":""+vDatosE.getDouble("dArqueoBruto"));
      cArqueoNeto = (vDatosE.getDouble("dArqueoNeto")==0?"- ":""+vDatosE.getDouble("dArqueoNeto"));
      cEslora = (vDatosE.getDouble("dEslora")==0?"- ":""+vDatosE.getDouble("dEslora"));
      cManga = (vDatosE.getDouble("dManga")==0?"- ":""+vDatosE.getDouble("dManga"));
      cPuntal = (vDatosE.getDouble("dPuntal")==0?"- ":""+vDatosE.getDouble("dPuntal"));
      cCaladoMax= (vDatosE.getDouble("dCaladoMax")==0?"- ":""+vDatosE.getDouble("dCaladoMax"));
      cEjerConstruccion = FechConst[0];
      cPesoMuerto = (vDatosE.getDouble("dPesoMuerto")==0?"- ":""+vDatosE.getDouble("dPesoMuerto"));
      cPasajeros = (vDatosE.getInt("iNumPasajeros")==0?"- ":""+vDatosE.getInt("iNumPasajeros"));
      cBodegas = (vDatosE.getDouble("dBodegas")==0?"- ":""+vDatosE.getDouble("dBodegas"));
      cTanques = (vDatosE.getInt("iNumTanques")==0?"- ":""+vDatosE.getInt("iNumTanques"));
      cTripulantes  = (vDatosE.getInt("iTripulacionMax")==0?"- ":""+vDatosE.getInt("iTripulacionMax"));
    }

    if(vOpini.size() > 0){
      TVDinRep vDatosO = (TVDinRep) vOpini.get(0);
      cDescServicio = vDatosO.getString("COBSESSEGTO");
    }
    if(vRegsPu.size() > 0){
      TVDinRep vDatosPto = (TVDinRep) vRegsPu.get(0);
      for(int i = 0;i < vRegsPu.size();i++){
        vDatosPto = (TVDinRep) vRegsPu.get(i);
        cPuertosT += vDatosPto.get("cDscPuerto").toString() + "," +
            vDatosPto.get("cAbreviatura").toString() + "." + ", ";
      }
      cPuertos=cPuertosT;
    }

    if(vRegsD.size() > 0){
             TVDinRep vDatosD = (TVDinRep) vRegsD.get(0);
             cTitular = vDatosD.get("cTitular").toString();
             cTitulo = vDatosD.get("cPuestoTitular").toString();
    }


     TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
     dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cDatos);
     dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());
     dObtenDatosOpiniones.dDatosSegtoEntidad.setFiltroSegtoEnt(cDatos);

     iCvePersona = dObtenDatosOpiniones.dDatosOficPer.getiCvePersona();
     iCveDomicilio = dObtenDatosOpiniones.dDatosOficPer.getiCveDomicilio();
     String cSiglas = dObtenDatosOpiniones.dDatosSegtoEntidad.getcSiglas();
     String cDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
     String cPuestoDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

     try{
       dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
     } catch (Exception e){
       e.printStackTrace();
       cMensaje = e.getMessage();
     }

     rep.comRemplaza("[TipoEmbarcacion]",cTipoEmbarcacion);
     rep.comRemplaza("[NombreEmbarcacion]",cNombreEmbarcacion);
     rep.comRemplaza("[PaisOrigenEmbarcacion]",cPaisOrigenEmbarcacion);
     rep.comRemplaza("[ArqueoBruto]",cArqueoBruto);
     rep.comRemplaza("[ArqueoNeto]",cArqueoNeto);
     rep.comRemplaza("[Eslora]",cEslora);
     rep.comRemplaza("[Manga]",cManga);
     rep.comRemplaza("[Puntal]",cPuntal);
     rep.comRemplaza("[CaladoMax]",cCaladoMax);
     rep.comRemplaza("[EjerConstruccion]",cEjerConstruccion);
     rep.comRemplaza("[PesoMuerto]",cPesoMuerto);
     rep.comRemplaza("[Pasajeros]",cPasajeros);
     rep.comRemplaza("[Bodegas]",cBodegas);
     rep.comRemplaza("[Tanques]",cTanques);
     rep.comRemplaza("[Tripulantes]",cTripulantes);
     rep.comRemplaza("[DescServicio]",cDescServicio);
     rep.comRemplaza("[Puertos]",cPuertosT);
     rep.comRemplaza("[DirigidoA]",cDirigidoA);
     rep.comRemplaza("[PuestoDirigidoA]",cPuestoDirigidoA);
     rep.comRemplaza("[Titulo]",cTitulo);
     rep.comRemplaza("[Titular]",cTitular);
     rep.comRemplaza("[Solicitante]",cSolicitante);
     rep.comRemplaza("[Siglas]",cSiglas);
     rep.comRemplaza("[cObservaciones]",cSiglas);

     sbBuscaAdic = rep.getEtiquetasBuscar();
     sbReemplazaAdic = rep.getEtiquetasRemplazar();

    return new TDGeneral().generaOficioWord(cNumFolio,
                                        Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
                                        0,0,
                                        iCvePersona,iCveDomicilio,0,
                                        "","Consulta a la Cámara",
                                        "", "",
                                        true,"cCuerpo",vcCuerpo,
                                        true, vcCopiasPara,
                                        sbBuscaAdic, sbReemplazaAdic);
  }

/*--------------------------------------------------------*/
public Vector autorizacionArribo(String cDatos,String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen) throws
    Exception{
  TWord rep = new TWord();
  String cFechaArribo = "--/--/----";
  StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
  TFechas fecha = new TFechas();
  Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector(), vDatosEmb = new Vector();
  String[] cValores = cDatos.split("·");
  String cSQL="SELECT e.ICVEVEHICULO, e.CNOMEMBARCACION, e.DESLORA, e.DCALADOMAX, " +
              "       e.DARQUEOBRUTO, e.DARQUEONETO, p.CNOMBRE " +
              "FROM  VEHEMBARCACION e " +
              "JOIN GRLPAIS p ON p.ICVEPAIS = e.ICVEPAISABANDERAMIENTO "+
              "WHERE e.ICVEVEHICULO =  "+cValores[1];
  vDatosEmb = super.FindByGeneric("",cSQL,dataSourceName);
  String cNombreBuque="",cEslora="",cCaladoMax="",cArqueoBruto="",cArqueoNeto="",cPaisAban="";
  //System.out.println("......vDatosEmb.size(): "+vDatosEmb.size());
  if(vDatosEmb.size()>0){
    TVDinRep vDinEmb = new TVDinRep();
    vDinEmb = (TVDinRep)vDatosEmb.get(0);
    if(vDinEmb.size()>0){
      //System.out.println("......................asigando valores de embarcacion");
      cNombreBuque = vDinEmb.getString("CNOMEMBARCACION");
      cEslora      = vDinEmb.getString("DESLORA");
      cCaladoMax   = vDinEmb.getString("DCALADOMAX");
      cArqueoBruto = vDinEmb.getString("DARQUEOBRUTO");
      cArqueoNeto  = vDinEmb.getString("DARQUEONETO");
      cPaisAban    = vDinEmb.getString("CNOMBRE");
    }

  }

  TDObtenDatos dObten = new TDObtenDatos();
  dObten.dOficDepto.setOficinaDepto(Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen));
  dObten.dOficina.setOficina(Integer.parseInt(iCveOficinaOrigen));
  rep.iniciaReporte();
  TVDinRep vDinRep = new TVDinRep();
  vDinRep.put("dtArribo",cValores[0]);
  if(vDinRep.getDate("dtArribo")!=null && !vDinRep.getDate("dtArribo").equals("null"))
    cFechaArribo = fecha.getDateSPN(vDinRep.getDate("dtArribo"));

  rep.comRemplaza("[cLocalidad]",dObten.dOficina.getNomMunicipio()+" "+dObten.dOficina.getNomEntidad());
  rep.comRemplaza("[cNombrePaís]",cPaisAban);
  rep.comRemplaza("[cNombreBuque]",cNombreBuque);
  rep.comRemplaza("[cUAB]",cArqueoBruto);
  rep.comRemplaza("[cUAN]",cArqueoNeto);
  rep.comRemplaza("[cEslora]",cEslora);
  rep.comRemplaza("[cCaladoMaximo]",cCaladoMax);
  rep.comRemplaza("[cFechadeArribo]",cFechaArribo);
  rep.comRemplaza("[cFechaSolicitud]",cValores[2]);
  rep.comRemplaza("[cNombreSolicitante]",cValores[3]);
  rep.comRemplaza("[cNombreAgencia]",cValores[4]);

  sbBuscaAdic = rep.getEtiquetasBuscar();
  sbReemplazaAdic = rep.getEtiquetasRemplazar();
/*
  sbBuscaAdic.append("[cLocalidad]|[cNombrePaís]|[cNombreBuque]|[cUAB]|[cUAN]|")
      .append("[cEslora]|[cCaladoMaximo]|[cFechadeArribo]|[cFechaSolicitud]|[cNombreSolicitante]|[cNombreAgencia]|");
  sbReemplazaAdic.append(dObten.dOficina.getNomMunicipio()+" "+dObten.dOficina.getNomEntidad()+"|"+cPaisAban+"|"+cNombreBuque+"|"+cArqueoBruto+"|"+cArqueoNeto+"|")
      .append(cEslora+"|"+cCaladoMax+"|"+ cFechaArribo +"|"+cValores[2]+"|"+cValores[3]+"|"+cValores[4]+"|");*/

  int iCvePersona, iCveDomicilio, iCveRepLegal;
  int iCveSubdireccion = Integer.parseInt(VParametros.getPropEspecifica("SubdirNavegacion"));

   iCvePersona   = Integer.parseInt(cValores[5]);
   iCveDomicilio = Integer.parseInt(cValores[6]);
   iCveRepLegal  = Integer.parseInt(cValores[7]);

  return new TDGeneral().generaOficioWord(cNumFolio,
                                      Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
                                      0,0,
                                      iCvePersona,iCveDomicilio,iCveRepLegal,
                                      "","Consulta a la Cámara",
                                      "", "",
                                      true,"cCuerpo",vcCuerpo,
                                      true, vcCopiasPara,
                                      sbBuscaAdic, sbReemplazaAdic);
}

  /**********************************************************/
  public Vector GralNavegacion(String cDatos, String cFolio, String cOficina, String cDepartamento) throws
      Exception{
    TWord rep = new TWord();
    TWord rep2 = new TWord();

    Vector vRegsU = new Vector();
    Vector vRegsD = new Vector();
    Vector vDatos = new Vector();

    String cCapitania = "";
    String[] cValores = cDatos.split(",");
    /*1-EjercicioSolicitud, 2-iNumSolicitud,3-iCveUsuario*/

    int iCveOficinaOrigen = 0,
        iCveDeptoOrigen = 0;

    try{
      rep.iniciaReporte();
      rep2.iniciaReporte();

      cCapitania = "Select distinct GRLUsuarioxOfic.iCveOficina,GRLOficina.cTitular,GRLOficina.cDscBreve,cCalleyNo,cColonia,cCodPostal, " +
          " GRLDepartamento.iCveDepartamento,GRLEntidadFed.cAbreviatura as EntidadFed,GRLMunicipio.cNombre as Municipio " +
          " from GRLOficina  " +
          " join GRLUsuarioxOfic on GRLOficina.iCveOficina=GRLUsuarioxOfic.iCveOficina  " +
          " join GRLDeptoxOfic on GRLUsuarioxOfic.iCveOficina= GRLDeptoxOfic.iCveOficina  " +
          " and GRLUsuarioxOfic.iCveDepartamento=GRLDeptoxOfic.iCveDepartamento " +
          " join GRLDepartamento on GRLUsuarioxOfic.iCveDepartamento=GRLDepartamento.iCveDepartamento " +
          " join GRLEntidadFed on GRLOficina.iCveEntidadFed=GRLEntidadFed.iCveEntidadFed " +
          " join GRLMunicipio on GRLOficina.iCveMunicipio=GRLMunicipio.iCveMunicipio " +
          " and  GRLOficina.iCveEntidadFed=GRLMunicipio.iCveEntidadFed " +
          " where GRLUsuarioxOfic.iCveUsuario= " + cValores[2] +
          " order by iCveOficina";

      vRegsU = super.FindByGeneric("",cCapitania,dataSourceName);

    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }

   if(vRegsU.size() > 0){
      TVDinRep vDatosU = (TVDinRep) vRegsU.get(0);
      iCveOficinaOrigen = vDatosU.getInt("iCveOficina");
      iCveDeptoOrigen = vDatosU.getInt("iCveDepartamento");
      String cDepto = "Select d.iCveDepartamento from GRLDeptoDepend dd " +
          " join GRLDepartamento d on d.iCveDepartamento = dd.iCveDepartamento " +
          " where iCveOficina = " + iCveOficinaOrigen + " and iCveDeptoHijo = " +
          iCveDeptoOrigen; //1,64 iCveOficinaOrigen,iCveDeptoOrigen

      vRegsD = super.FindByGeneric("",cDepto,dataSourceName);

      if(vRegsD.size() > 0){
        TVDinRep vDatosD = (TVDinRep) vRegsD.get(0);
        TVDinRep vDatosT = (TVDinRep)this.getDatosTitular(iCveOficinaOrigen,
            vDatosD.getInt("iCveDepartamento"));

//        rep2.comRemplaza("[cNombreRemit]",vDatosT.getString("cTitular"));
        rep2.comRemplaza("[cPuestoTit]",vDatosT.getString("cPuestoTitular"));
        rep2.comRemplaza("[iNumSolicitud]",cValores[1]);
        rep2.comRemplaza("[dtFechaSol]",cValores[0]);

      }
      vDatos = new TDGeneral().generaOficioWord(cFolio,
                                                Integer.parseInt(cOficina),Integer.parseInt(cDepartamento),
                                                0,0,
                                                1,1,1,
                                                "Oficio Memo","Asunto","","",
                                                false,"Etiqueta base de cuerpo",new Vector(),
                                                false,new Vector(),
                                                rep2.getEtiquetasBuscar(),rep2.getEtiquetasRemplazar());

    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return vDatos;
  }

  private TVDinRep getDatosTitular(int iCveOficina,int iCveDepto){
    Vector vRegsDatos = new Vector();
    String cSql = "SELECT cTitular, cTelefono, cCorreoE, cPuestoTitular, cDscDepartamento, cDscBreve " +
        " FROM GRLDeptoXOfic " +
        " JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLDeptoXOfic.iCveDepartamento " +
        " WHERE GRLDeptoXOfic.iCveOficina = " + iCveOficina + " " +
        "   AND GRLDeptoXOfic.iCveDepartamento = " + iCveDepto;
    try{
      vRegsDatos = super.FindByGeneric("",cSql,super.dataSourceName);
    } catch(SQLException ex){
      cMensaje = ex.getMessage();
    } catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }
    if(vRegsDatos.size() > 0)
      return(TVDinRep) vRegsDatos.get(0);
    else
      return null;
  }
  public Vector Navegacion(String cDatos, String cFolio, String cOficina, String cDepartamento) throws
        Exception{
	  System.out.println("+++++++++++++++++ TDNavegación");
      TWord rep2 = new TWord();
      TFechas Fecha = new TFechas();
      rep2.iniciaReporte();
      Vector vDatos1 = new Vector();
      Vector vDatos2 = new Vector();
      Vector vDatos = new Vector();
      String[] cValores = cDatos.split(",");
      String cSql = "SELECT TSREGISTRO,ICVESOLICITANTE,ICVEREPLEGAL,ICVEDOMICILIOSOLICITANTE, ct.CDSCBREVE " +
          "FROM TRAREGSOLICITUD rs " +
          "Join TRACATTRAMITE ct on ct.ICVETRAMITE = rs.ICVETRAMITE " +
          "where IEJERCICIO = "+cValores[0]+" and INUMSOLICITUD = "+cValores[1];


      vDatos1 = super.FindByGeneric("",cSql,dataSourceName);
      TVDinRep vDatosD = (TVDinRep) vDatos1.get(0);
          rep2.comRemplaza("[dtFechaSol]",""+Fecha.getDateSQL(vDatosD.getTimeStamp("TSREGISTRO")));
          rep2.comRemplaza("[iNumSolicitud]",cValores[1]);

      cSql = "SELECT ICVEOFICINA,ICVEDEPARTAMENTO FROM TRAREGETAPASXMODTRAM  " +
          " Where IEJERCICIO = "+cValores[0]+
          " And INUMSOLICITUD = "+cValores[1]+
          " And ICVEETAPA = 1 ";
      vDatos2 = super.FindByGeneric("",cSql,dataSourceName);
      TVDinRep vDatosT = (TVDinRep) vDatos2.get(0);
      rep2.comRemplaza("[cPuestoTit]",vDatosT.getString("COFICINA"));
      vDatos = new TDGeneral().generaOficioWord(cFolio,
                                                Integer.parseInt(cOficina),Integer.parseInt(cDepartamento),
                                                0,0,
                                                vDatosD.getInt("ICVESOLICITANTE"),vDatosD.getInt("ICVEDOMICILIOSOLICITANTE"),vDatosD.getInt("ICVEREPLEGAL"),
                                                "",vDatosD.getString("CDSCBREVE"),"","",
                                                false,"",new Vector(),
                                                false,new Vector(),
                                                rep2.getEtiquetasBuscar(),rep2.getEtiquetasRemplazar());
//      vDatos = new TDGeneral().generaOficioWord(1,1,1,1,1,1,1,1,"OficioMemo","Asunto")
      return vDatos;
    }

    public void fDespliegaSB(StringBuffer sb1, StringBuffer sb2){
      String st1[],st2[];
      int max=0;
      st1=sb1.toString().split("|");
      st2=sb2.toString().split("|");
      System.out.println("*****    "+sb1.toString());
      System.out.println("*****    "+sb2.toString());
      if(st1.length==st2.length) max = st1.length;
      if(st1.length<st2.length) max = st1.length;
      if(st1.length>st2.length) max = st2.length;
      System.out.println("***********     "+max+"     ***********");
      for(int icount = 0; icount<max;icount++){
        System.out.println("\n*****    "+st2+"\t"+st1);
      }
    }
}

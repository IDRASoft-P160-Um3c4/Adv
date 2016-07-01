package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.sql.SQLException;
import java.util.Vector;
import gob.sct.sipmm.dao.reporte.*;
import java.sql.*;
import java.util.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.*;




/**
 * <p>Title: TDZDPDeclaratoria.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame
 * @version 1.0
 */

public class TDZDPConvenio extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
//  private String cDirSEMARNAT = VParametros.getPropEspecifica("ZPDCveSEMARNAT");
  String Folio;
  private int iCveSegtoEntidad=0;
  private int iCveOpinionEntidad = 0;
  private int iEjercicioSolicitud = 0;
  private int iNumSolicitud = 0;
  private String cOpnDirigidoA;
  private String cPtoOpinion;
  TDObtenDatos obtenerDatos = new TDObtenDatos();
  private int iCveDepto;
  private int iCveOficina;

  public TDZDPConvenio(){

  }

//  public StringBuffer GenConvenio(String cQuery) throws Exception{
  public StringBuffer GenConvenio(String cFiltro) throws Exception{

    Vector vResultado = new Vector();
    Vector vRegs = new Vector();
    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    String cEstado, cMunicipio, cPuerto, cGobernador, cPresidenteM, cDomEstado, cDomMunicipio;
    TFechas Fecha = new TFechas("44");
    TLetterNumberFormat tLetterNumberFormat = new TLetterNumberFormat();

    String cQuery1 = "";
    String[] aFiltros = cFiltro.split(",");

    TWord rep = new TWord();  //Reporte Principal.

    rep.iniciaReporte(); //Reporte Principal.
    rep.comRemplaza("[dtEmision]",
                    "" + tLetterNumberFormat.getCantidad((long) Fecha.getIntDay(Fecha.TodaySQL())) + " de " +
                    "" + Fecha.getMonthName(Fecha.TodaySQL()) + " del año " +
                    "" + tLetterNumberFormat.getCantidad((long) Fecha.getIntYear(Fecha.TodaySQL())) + ".");

    cQuery1 = "Select " +
        "PR.cDscPuerto as cPuerto, " +
        "EF.cNombre as cEstado, " +
        "MU.cNombre as cMunicipio " +
        "from grlPuerto PR " +
        "left join grlEntidadFed EF on PR.iCveEntidadFed = EF.iCveEntidadFed " +
        "left join grlMunicipio MU on MU.iCvePais = PR.iCvePais " +
        "and MU.iCveEntidadFed = EF.iCveEntidadFed " +
        "and MU.iCveMunicipio = PR.iCvePuerto " +
        "where iCvePuerto = " + aFiltros[0];

    Vector vcData1 = findByCustom("",cQuery1);
    if(vcData1.size() > 0){
      TVDinRep vEntidadFed = (TVDinRep) vcData1.get(0);
      cPuerto = vEntidadFed.getString("cPuerto");
      cMunicipio = vEntidadFed.getString("cMunicipio");
      cEstado = vEntidadFed.getString("cEstado");
      cGobernador = aFiltros[1];
      cPresidenteM = aFiltros[2];
      cDomEstado = aFiltros[3];
      cDomMunicipio = aFiltros[4];
      rep.comRemplaza("[nombrePuerto]", this.minusculas(cPuerto));
      rep.comRemplaza("[nombreMunicipio]", this.minusculas(cMunicipio));
      rep.comRemplaza("[nombreEstado]", this.minusculas(cEstado));
      rep.comRemplaza("[nombreGobernador]", cGobernador);
      rep.comRemplaza("[nombrePresidenteMunicipal]", cPresidenteM);
//      //System.out.print("\n\n\ncDirSEMARNAT: " + cDirSEMARNAT);
 //     rep.comRemplaza("[domicilioSEMARNAT]",cDirSEMARNAT);
      rep.comRemplaza("[domicilioEstado]",this.minusculas(cDomEstado));
      rep.comRemplaza("[domicilioMunicipio]",this.minusculas(cDomMunicipio));
    }


    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return rep.getEtiquetas(true);
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


  public Vector GenOpnInterna(String cFiltro,String cFolio,String cCveOfic,
                                    String cCveDepto) throws Exception{
    Vector vcRegsRep = new Vector();
    Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector();
    String cPuerto="",cEntidad="",cMunicipio="", cGobernador="";


    TWord rep = new TWord();
    TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();
    rep.iniciaReporte();

    datosOpinion.dDatosOpnTra.setFiltrosOpn(cFiltro);
    iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
    iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();

    datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cFolio);
    datosOpinion.dDatosOficPer.setFiltroOpnEnt(iCveOpinionEntidad);
    iCveDepto = datosOpinion.dDatosOficPer.getiCveDepto();
    iCveOficina = datosOpinion.dDatosOficPer.getiCveOficina();

    String[] aValores = cFiltro.split(",");
    String cQuery1 = "SELECT P.CDSCPUERTO, EF.CNOMBRE AS CENTIDAD, M.CNOMBRE AS CMUNICIPIO " +
                     "FROM GRLPUERTO P " +
                     "JOIN GRLPAIS PA ON PA.ICVEPAIS = P.ICVEPAIS " +
                     "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = P.ICVEPAIS AND EF.ICVEENTIDADFED = P.ICVEENTIDADFED " +
                     "JOIN GRLMUNICIPIO M ON M.ICVEPAIS = P.ICVEPAIS AND M.ICVEENTIDADFED = P.ICVEENTIDADFED AND " +
                             "M.ICVEMUNICIPIO = P.ICVEMUNICIPIO " +
                     "WHERE ICVEPUERTO = "+ aValores[4] ;


    vcRegsRep = GenSQLGenerico(vcRegsRep,cQuery1);

    if(vcRegsRep.size() > 0){
      TVDinRep vDatosRep = (TVDinRep) vcRegsRep.get(0);
      cPuerto = vDatosRep.getString("cDscPuerto").toLowerCase();
      cEntidad = vDatosRep.getString("cEntidad").toLowerCase();
      cMunicipio = vDatosRep.getString("cMunicipio").toLowerCase();

      rep.comRemplaza("[cPuerto]",cPuerto);
      rep.comRemplaza("[cMunicipioPto]",cMunicipio);
      rep.comRemplaza("[cEstado]",cEntidad);

      // Llamado 2 empleado para oficina y depto fijos destino externo
      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              iCveOficina,
                                              iCveDepto,
                                              0,0,0,
                                              "","","","",
                                              true,"",vcCuerpo,
                                              true, vcCopiasPara,
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());

    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }

  public Vector GenOpnExterna(String cFiltro,String cFolio,String cCveOfic,
                                    String cCveDepto) throws Exception{

    Vector vcRegsRep = new Vector();
    Vector vcCuerpo = new Vector(), vcCopiasPara = new Vector();
    String cPuerto="",cEntidad="",cMunicipio="";
    int iCvePersona, iCveDomicilio;

    TWord rep = new TWord();
    TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
     //Integridad con el Modulo de Opiniones.
     dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cFiltro);
     dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());


     iCvePersona = dObtenDatosOpiniones.dDatosOficPer.getiCvePersona();
     iCveDomicilio = dObtenDatosOpiniones.dDatosOficPer.getiCveDomicilio();
     String cDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
     String cPuestoDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

     try{
       dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cFolio);
     } catch (Exception e){
       e.printStackTrace();
       cMensaje = e.getMessage();
     }

    String[] aValores = cFiltro.split(",");
    String cQuery1 = "SELECT P.CDSCPUERTO, EF.CNOMBRE AS CENTIDAD, M.CNOMBRE AS CMUNICIPIO " +
                     "FROM GRLPUERTO P " +
                     "JOIN GRLPAIS PA ON PA.ICVEPAIS = P.ICVEPAIS " +
                     "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = P.ICVEPAIS AND EF.ICVEENTIDADFED = P.ICVEENTIDADFED " +
                     "JOIN GRLMUNICIPIO M ON M.ICVEPAIS = P.ICVEPAIS AND M.ICVEENTIDADFED = P.ICVEENTIDADFED AND " +
                             "M.ICVEMUNICIPIO = P.ICVEMUNICIPIO " +
                     "WHERE ICVEPUERTO = "+ aValores[4] ;


    vcRegsRep = GenSQLGenerico(vcRegsRep,cQuery1);

    if(vcRegsRep.size() > 0){
      TVDinRep vDatosRep = (TVDinRep) vcRegsRep.get(0);
      cPuerto = vDatosRep.getString("cDscPuerto").toLowerCase();
      cEntidad = vDatosRep.getString("cEntidad").toLowerCase();
      cMunicipio = vDatosRep.getString("cMunicipio").toLowerCase();

      rep.comRemplaza("[cPuerto]",cPuerto);
      rep.comRemplaza("[cMunicipioPto]",cMunicipio);
      rep.comRemplaza("[cEstado]",cEntidad);
      rep.comRemplaza("[cDirigidoA]",cDirigidoA);
      rep.comRemplaza("[cPuestoDirigidoA]",cPuestoDirigidoA);

      // Llamado 2 empleado para oficina y depto fijos destino externo
      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              0,0,
                                              iCvePersona,iCveDomicilio,0,
                                              "","","","",
                                              true,"",vcCuerpo,
                                              true, vcCopiasPara,
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

  private String minusculas(String cNom){
     StringTokenizer st = new StringTokenizer(cNom);
     cNom = "";
     while (st.hasMoreTokens()) {
       String cToken = st.nextToken();
       cNom = cNom  + ' ' +
                    cToken.charAt(0) +
                    cToken.substring(1,cToken.length()).toLowerCase();
     }
     return cNom;
  }

}



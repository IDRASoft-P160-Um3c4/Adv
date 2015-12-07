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
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Ithamar Caballero Altamirano
 * @version 1.0
 */


public class TDOpnConcesion extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  String Folio;
  private int iCveSegtoEntidad=0;
  private int iCveOpinionEntidad = 0;
  private int iEjercicioSolicitud = 0;
  private int iNumSolicitud = 0;
  private int iConsecutivo;
  private String cOpnDirigidoA;
  private String cPtoOpinion;
  TDObtenDatos obtenerDatos = new TDObtenDatos();
  private int iCveDepto;
  private int iCveOficina;

  //private Object vData;

  public TDOpnConcesion(){
  }

  public Vector GenOpnDGAJ(String cQuery,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{
    Vector vRegs = new Vector();
    Vector vRegsSolTitulo = new Vector();
    Vector vRegsCPATitulo = new Vector();
    Vector vRegsTituloPropiedad = new Vector();
    Vector vRegsTituloDocumento = new Vector();
    Vector vRegsFolioUpdate = new Vector();
    Vector vRegsFolio = new Vector();



    TWord rep = new TWord();
    TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();
    rep.iniciaReporte();


    datosOpinion.dDatosOpnTra.setFiltrosOpn(cQuery);
    iEjercicioSolicitud = datosOpinion.dDatosOpnTra.getiEjercicio();
    iNumSolicitud = datosOpinion.dDatosOpnTra.getiNumSolicitud();
    iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
    cOpnDirigidoA = datosOpinion.dDatosOpnTra.getOpnDirigidoA();
    cPtoOpinion = datosOpinion.dDatosOpnTra.getPtoOpn();



    if(iEjercicioSolicitud > 0){
      vRegsSolTitulo = getCPASolTitulo(iEjercicioSolicitud,iNumSolicitud);
      datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cFolio);

      if(vRegsSolTitulo.size() > 0){
        TVDinRep vDatosSolTitulo = (TVDinRep) vRegsSolTitulo.get(0);

        cQuery = "select CPATitulo.iCveTitulo, " +
            "CPATitulo.cNumTitulo, " +
            "CPATitulo.cNumTituloAnterior, " +
            "CPATitulo.dtIniVigenciaTitulo, " +
            "CPATitulo.cZonaFedAfectadaTerrestre, " +
            "CPATitulo.cZonaFedAfectadaAgua, " +
            "CPATitulo.cObjetoTitulo, " +
            "CPATitulo.cMontoInversion, " +
            "CPATitulo.dtPublicacionDOF " +
            "from CPATitulo " +
            "where CPATitulo.iCveTitulo = " +
            vDatosSolTitulo.getInt("iCveTitulo");

        vRegsCPATitulo = GenSQLGenerico(vRegsCPATitulo,cQuery);

        if(vRegsCPATitulo.size() > 0){
          TVDinRep vDatosCPATitulo = (TVDinRep) vRegsCPATitulo.get(0);

          rep.comRemplaza("[cUbiZonFedTerrestre]",
                          (vDatosCPATitulo.getString("cZonaFedAfectadaTerrestre").
                            compareTo("") != 0) ?
                          vDatosCPATitulo.getString("cZonaFedAfectadaTerrestre") :
                          "");
        }


        TVDinRep vDatosTituloPropiedad = (TVDinRep) vRegsSolTitulo.get(0);

        cQuery = " select CPATituloPropiedad.cSuperficiePropiedad, " +
            " CPATituloPropiedad.cLotePropiedad, " +
            " CPATituloPropiedad.cSeccionPropiedad, " +
            " CPATituloPropiedad.cManzanaPropiedad, " +
            " CPATituloPropiedad.cNumOficialPropiedad, " +
            " CPATituloPropiedad.cCallePropiedad, " +
            " CPATituloPropiedad.cKmPropiedad, " +
            " CPATituloPropiedad.cColoniaPropiedad, " +
            " GRLPuerto.cDscPuerto, " +
            " GRLPais.cNombre as cDscPais, " +
            " GRLEntidadFed.cNombre as cDscEntidadFed, " +
            " GRLMunicipio.cNombre as cDscMunicipio, " +
            " GRLLocalidad.cNombre as cDscLocalidad, " +
            " CPATitProEscrituraPublica.cNumTitProEscPublica, " +
            " CPATitProEscrituraPublica.dtEscPublica, " +
            " CPATitProEscrituraPublica.cNumNotariaPublica, " +
            " Notario.cNomRazonSocial, " +
            " Notario.cApPaterno, " +
            " Notario.cApMaterno, " +
            " PaisEscPub.cNombre as cDscPaisEscPub, " +
            " EntidadEscPub.cNombre as cDscEntidadFedEscPub, " +
            " MunicipioEscPub.cNombre as cDscMunicipioEscPub, " +
            " LocalidadEscPub.cNombre as cDscLocalidadEscPub, " +
            " CPATitProRegPubCom.dtTitProRegPubCom, " +
            " CPATitProRegPubCom.cNumTitProRegPubCom, " +
            " CPATitProRegPubCom.cNumRegPubComFoja, " +
            " CPATitProRegPubCom.cNumRegPubComTomo, " +
            " CPATitProRegPubCom.cNumRegPubComSeccion, " +
            " CPATitProRegPubCom.cNumRegPubComLibro, " +
            " CPATitProRegPubCom.cNumRegPubComVolumen, " +
            " PaisRegPubCom.cNombre as cDscPaisRegPubCom, " +
            " EntidadRegPubCom.cNombre as cDscEntidadFedRegPubCom, " +
            " MunicipioRegPubCom.cNombre as cDscMunicipioRegPubCom, " +
            " LocalidadRegPubCom.cNombre as cDscLocalidadRegPubCom " +
            " from CPATitulo " +
            " join CPATitular on CPATitular.iCveTitulo = CPATitulo.iCveTitulo " +
            " join CPATituloPropiedad on CPATituloPropiedad.iCveTitulo = CPATitular.iCveTitulo " +
            " and CPATituloPropiedad.iCveTitular = CPATitular.iCveTitular " +
            " left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloPropiedad.iCvePuerto " +
            " join GRLPais on GRLPais.iCvePais = CPATituloPropiedad.iCvePais " +
            " join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloPropiedad.iCvePais " +
            " and GRLEntidadFed.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed " +
            " join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloPropiedad.iCvePais " +
            " and GRLMunicipio.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed " +
            " and GRLMunicipio.iCveMunicipio = CPATituloPropiedad.iCveMunicipio " +
            " join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloPropiedad.iCvePais " +
            " and GRLLocalidad.iCveEntidadFed = CPATituloPropiedad.iCveEntidadFed " +
            " and GRLLocalidad.iCveMunicipio = CPATituloPropiedad.iCveMunicipio " +
            " and GRLLocalidad.iCveLocalidad = CPATituloPropiedad.iCveLocalidad " +
            " left join CPATitProEscrituraPublica on CPATitProEscrituraPublica.iCveTitulo = CPATituloPropiedad.iCveTitulo " +
            " and CPATitProEscrituraPublica.iCveTitular = CPATituloPropiedad.iCveTitular " +
            " and CPATitProEscrituraPublica.iNumTituloPropiedad = CPATituloPropiedad.iNumTituloPropiedad " +
            " left join GRLPersona Notario on Notario.iCvePersona = CPATitProEscrituraPublica.iCveNotarioPub " +
            " left join GRLPais PaisEscPub on PaisEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
            " left join GRLEntidadFed EntidadEscPub on EntidadEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
            " and EntidadEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed " +
            " left join GRLMunicipio MunicipioEscPub on MunicipioEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
            " and MunicipioEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed " +
            " and MunicipioEscPub.iCveMunicipio = CPATitProEscrituraPublica.iCveMunicipio " +
            " left join GRLLocalidad LocalidadEscPub on LocalidadEscPub.iCvePais = CPATitProEscrituraPublica.iCvePais " +
            " and LocalidadEscPub.iCveEntidadFed = CPATitProEscrituraPublica.iCveEntidadFed " +
            " and LocalidadEscPub.iCveMunicipio = CPATitProEscrituraPublica.iCveMunicipio " +
            " and LocalidadEscPub.iCveLocalidad = CPATitProEscrituraPublica.iCveLocalidad " +
            " left join CPATitProRegPubCom on CPATitProRegPubCom.iCveTitulo = CPATitProEscrituraPublica.iCveTitulo " +
            " and CPATitProRegPubCom.iCveTitular = CPATitProEscrituraPublica.iCveTitular " +
            " and CPATitProRegPubCom.iNumTituloPropiedad = CPATitProEscrituraPublica.iNumTituloPropiedad " +
            " left join GRLPais PaisRegPubCom on PaisRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
            " left join GRLEntidadFed EntidadRegPubCom on EntidadRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
            " and EntidadRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed " +
            " left join GRLMunicipio MunicipioRegPubCom on MunicipioRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
            " and MunicipioRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed " +
            " and MunicipioRegPubCom.iCveMunicipio = CPATitProRegPubCom.iCveMunicipio " +
            " left join GRLLocalidad LocalidadRegPubCom on LocalidadRegPubCom.iCvePais = CPATitProRegPubCom.iCvePais " +
            " and LocalidadRegPubCom.iCveEntidadFed = CPATitProRegPubCom.iCveEntidadFed " +
            " and LocalidadRegPubCom.iCveMunicipio = CPATitProRegPubCom.iCveMunicipio " +
            " and LocalidadRegPubCom.iCveLocalidad = CPATitProRegPubCom.iCveLocalidad " +
            " where CPATitulo.iCveTitulo = " +
            vDatosTituloPropiedad.getInt("iCveTitulo");

        vRegsTituloPropiedad = GenSQLGenerico(vRegsTituloPropiedad,cQuery);
        if(vRegsTituloPropiedad.size() > 0){
          TVDinRep vDatosCPATituloProp = (TVDinRep) vRegsTituloPropiedad.get(0);

          rep.comRemplaza("[cSupZF]",
                          (vDatosCPATituloProp.getString("cSuperficiePropiedad").
                            compareTo("") != 0) ?
                          " Superficie: " +
                          vDatosCPATituloProp.getString("cSuperficiePropiedad") :
                          "");
          rep.comRemplaza("[cLoteZF]",
                          (vDatosCPATituloProp.getString("cLotePropiedad").
                            compareTo("") != 0) ?
                          " Lote: " +
                          vDatosCPATituloProp.getString("cLotePropiedad") : "");
          rep.comRemplaza("[cSecZF]",
                          (vDatosCPATituloProp.getString("cSeccionPropiedad").
                            compareTo("") != 0) ?
                          " Sección: " +
                          vDatosCPATituloProp.getString("cSeccionPropiedad") :
                          "");
          rep.comRemplaza("[cManZF]",
                          (vDatosCPATituloProp.getString("cManzanaPropiedad").
                            compareTo("") != 0) ?
                          " Manzana: " +
                          vDatosCPATituloProp.getString("cManzanaPropiedad") :
                          "");
          rep.comRemplaza("[cNoZF]",
                          (vDatosCPATituloProp.getString("cNumOficialPropiedad").
                            compareTo("") != 0) ?
                          " Número Oficial: " +
                          vDatosCPATituloProp.getString("cNumOficialPropiedad") :
                          "");
          rep.comRemplaza("[cCalZF]",
                          (vDatosCPATituloProp.getString("cCallePropiedad").
                            compareTo("") != 0) ?
                          " Calle: " +
                          vDatosCPATituloProp.getString("cCallePropiedad") : "");
          rep.comRemplaza("[cKMZF]",
                          (vDatosCPATituloProp.getString("cKmPropiedad").
                            compareTo("") != 0) ?
                          " Km. " +
                          vDatosCPATituloProp.getString("cKmPropiedad") : "");
          rep.comRemplaza("[cColZF]",
                          (vDatosCPATituloProp.getString("cColoniaPropiedad").
                            compareTo("") != 0) ?
                          " Col. " +
                          vDatosCPATituloProp.getString("cColoniaPropiedad") :
                          "");
          rep.comRemplaza("[cPuertoZF]",
                          (vDatosCPATituloProp.getString("cDscPuerto").
                            compareTo("") != 0) ?
                          " Puerto: " +
                          vDatosCPATituloProp.getString("cDscPuerto") : "");
          rep.comRemplaza("[cMunZF]",
                          (vDatosCPATituloProp.getString("cDscMunicipio").
                            compareTo("") != 0) ?
                          " Municipio: " +
                          vDatosCPATituloProp.getString("cDscMunicipio") : "");
          rep.comRemplaza("[cEntFedZF]",
                          (vDatosCPATituloProp.getString("cDscEntidadFed").
                            compareTo("") != 0) ?
                          " Entidad Federativa: " +
                          vDatosCPATituloProp.getString("cDscEntidadFed") : "");
        }

        TVDinRep vDatosTituloDocumento = (TVDinRep) vRegsSolTitulo.get(0);

        cQuery = " select CPATituloDocumento.iCveTipoDocumento, " +
            " CPATipoDocumento.cDscTipoDocumento, " +
            " CPATituloDocumento.cNumDocumento, " +
            " CPATituloDocumento.dtDocumento, " +
            " CPATituloDocumento.dtVigenciaDocumento, " +
            " GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cDepEmite, " +
            " CPATituloDocumento.cObjetoDocumento, " +
            " CPATituloDocumento.cSuperficieDocumento, " +
            " CPATituloDocumento.lMaritimoTerrestre, " +
            " CPAClasificacionDocumento.cDscClasificacionDocto " +
            " from CPATitulo " +
            " join CPATituloDocumento on CPATituloDocumento.iCveTitulo = CPATitulo.iCveTitulo " +
            " join CPATipoDocumento on CPATipoDocumento.iCveTipoDocumento = CPATituloDocumento.iCveTipoDocumento " +
            " left join GRLPersona on GRLPersona.iCvePersona = CPATituloDocumento.iCveDepEmiteDoc " +
            " left join CPAClasificacionDocumento on CPAClasificacionDocumento.iCveClasificacionDocto = CPATituloDocumento.iCveClasificacionDocto " +
            " where CPATitulo.iCveTitulo = " +
            vDatosTituloDocumento.getInt("iCveTitulo");

        vRegsTituloDocumento = GenSQLGenerico(vRegsTituloDocumento,cQuery);
        if(vRegsTituloPropiedad.size() > 0){
          TVDinRep vDatosCPATituloDoc = (TVDinRep) vRegsTituloDocumento.get(0);

          rep.comRemplaza("[cSupTitConZF]",
                          (vDatosCPATituloDoc.getString("cSuperficieDocumento").
                            compareTo("") != 0) ?
                          vDatosCPATituloDoc.getString("cSuperficieDocumento") :
                          "");
        }

      }

      rep.comRemplaza("[cPersonaDestino]",
                      (cOpnDirigidoA.compareTo("") != 0) ? cOpnDirigidoA : "");
      rep.comRemplaza("[cPuestoDestino]",
                      (cPtoOpinion.compareTo("") != 0) ? cPtoOpinion : "");
      // Llamado 2 empleado para oficina y depto fijos destino externo
      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              0,0,
                                              0,0,0,
                                              "","","","",
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());

    } else
      System.out.print("No Encontro Datos");

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }


  public Vector GenOpnInterna(String cFiltro,String cFolio,String cCveOfic,
                                    String cCveDepto) throws Exception{
    Vector vRegs = new Vector();
    Vector vRegsOpinionEntidad = new Vector();
    Vector vRegsDepto = new Vector();
    Vector vRegsFolio = new Vector();



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

    String cQuery1 = "select * " +
        "from GRLDEPARTAMENTO " +
        "where ICVEDEPARTAMENTO = " +iCveDepto;

    vRegsDepto = GenSQLGenerico(vRegsDepto,cQuery1);

    if(vRegsDepto.size() > 0){
      TVDinRep vDatosDep = (TVDinRep) vRegsDepto.get(0);

      rep.comRemplaza("[cDepartamento]",
                      (vDatosDep.getString("cDscDepartamento").compareTo("") !=
                       0) ? vDatosDep.getString("cDscDepartamento") : "");

      // Llamado 2 empleado para oficina y depto fijos destino externo
      return new TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              iCveOficina,
                                              iCveDepto,
                                              0,0,0,
                                              "","","","",
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep.getEtiquetasBuscar(),
                                              rep.getEtiquetasRemplazar());

    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }

  public Vector getCPASolTitulo(int iEjercicioSolicitud,int iNumSolicitud) throws
      Exception{
    Vector vRegs = new Vector();

    vRegs = GenSQLGenerico(vRegs,
                           " SELECT iCveTitulo " +
                           " FROM CPASolTitulo " +
                           " WHERE iEjercicio = " + iEjercicioSolicitud +
                           " And iNumSolicitud = " + iNumSolicitud);

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return vRegs;
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



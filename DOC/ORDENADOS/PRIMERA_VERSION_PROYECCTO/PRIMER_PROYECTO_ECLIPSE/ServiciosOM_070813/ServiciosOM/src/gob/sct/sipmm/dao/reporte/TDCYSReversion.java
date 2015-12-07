package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.seguridad.dao.CFGAccion;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;
import gob.sct.sipmm.dao.ws.TDActualizaRecargo;

/**
 * <p>Title: TDCYSReversion.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame.
 * @version 1.0
 */

public class TDCYSReversion extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas    tFecha      = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
  private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
  private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
  private int iEjercicioOficio = 0;
  private String cDigitosFolio = "";
  private int iConsecutivo=0;
  public TDCYSReversion(){
  }

 public Vector genProrroga(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
    Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
    Vector vRegs = new Vector();
    Vector vRegsTituloProp = new Vector();
    Vector vRegsTituloDocumentos = new Vector();
    Vector vRegsTitPoliza = new Vector();
    Vector vRegsSerPor = new Vector();
    Vector vRegsSerCon = new Vector();
    TWord rep = new TWord();
    TFechas Fechas = new TFechas("44");
    TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

    int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
    String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Se concede Prorroga de Plazo";
    String cTipoContrato="";
    String[] aFiltros = cQuery.split(",");
    int iCveTitulo = Integer.parseInt(aFiltros[0]);
    int iEjercicio = Integer.parseInt(aFiltros[1]);
    int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
    int iMovFolioProc = Integer.parseInt(aFiltros[3]);
    //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
    //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

    this.setDatosFolio(cNumFolio);



    // Query de Consulta del Reporte.
    try{
      vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                      "CPATitulo.iCveTipoTitulo, " +
                                      "CPATitulo.dtIniVigenciaTitulo, " +
                                      "CPATitulo.dtVigenciaTitulo, " +
                                      "CPATituloUbicacion.lDentroRecintoPort, " +
                                      "CPATitulo.cZonaFedTotalTerrestre, " +
                                      "CPATitulo.cZonaFedTotalAgua, " +
                                      "CPATitulo.iPropiedad, " +
                                      "CPATitulo.iCveClasificacion " +
                                      "from CPATitulo " +
                                      "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                      "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

    }catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      ex2.printStackTrace();
    }

    //Infomacion del Titulo de Propiedad
    try{ vRegsTituloProp = super.FindByGeneric("",
                         " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                         " Notario.cApPaterno, "+
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
                         " where CPATitulo.iCveTitulo = " + iCveTitulo,
                         dataSourceName);
    } catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
    }catch(Exception ex2){
       ex2.printStackTrace();
       cMensaje += ex2.getMessage();
    }



    // Elaboración del Reporte.
    rep.iniciaReporte();
    if (vRegs.size() > 0){
      TVDinRep vDatos = (TVDinRep) vRegs.get(0);

      //Identificación que se trata de Concesión.
      if (vDatos.getInt("iCveTipoTiulo") == 1){
        //Concesion de Puerto.
        if(vDatos.getInt("iCveClasificacion") == 1){
          rep.comRemplaza("[cp]","X");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Concesion de Terminal.
        if(vDatos.getInt("iCveClasificacion") == 2){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]","X");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Concesion de Marina.
        if(vDatos.getInt("iCveClasificacion") == 3){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]","X");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Concesion de Instalación.
        if(vDatos.getInt("iCveClasificacion") == 4){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]","X");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
      }
      //Identificación que se trata de Permiso.
      if (vDatos.getInt("iCveTipoTiulo") == 2){
        //Permiso de Embarcadero.
        if(vDatos.getInt("iCveClasificacion") == 5){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]","X");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Atracadero.
        if(vDatos.getInt("iCveClasificacion") == 6){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]","X");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Botadero.
        if(vDatos.getInt("iCveClasificacion") == 7){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]","X");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Similar.
        if(vDatos.getInt("iCveClasificacion") == 8){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]","X");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Servicio Portuario.
        if(vDatos.getInt("iCveClasificacion") == 9){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]","X");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Servicio Conexo.
        if(vDatos.getInt("iCveClasificacion") == 10){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]","X");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
      }

      //Identificación que se trata de Autorizacion.
      if (vDatos.getInt("iCveTipoTiulo") == 3){
        //Autorización de Obra Marítima.
        if(vDatos.getInt("iCveClasificacion") == 11){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]","X");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Autorización de Dragado de Construcción.
        if(vDatos.getInt("iCveClasificacion") == 12){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]","X");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Autorización de Dragado de Mantenimiento.
        if(vDatos.getInt("iCveClasificacion") == 13){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]","X");
          rep.comRemplaza("[aa]"," ");
        }
        //Autorización de Dragado de Mantenimiento.
        if(vDatos.getInt("iCveClasificacion") == 14){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]","X");
        }
      }

      rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
      rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
      rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
      rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

      //Fuera de Recinto Portuario
      if (vDatos.getInt("lDentroRecintoPort") == 0){
        rep.comRemplaza("[ir]"," ");
        rep.comRemplaza("[if]","X");
        rep.comRemplaza("[it]","X");
        rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
        rep.comRemplaza("[ia]","X");
        //Maritima
        rep.comRemplaza("[im]","X");
        rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
        //Fluvial
        rep.comRemplaza("[iu]","");
        rep.comRemplaza("[flu]","");
        //Lacustre
        rep.comRemplaza("[il]","");
        rep.comRemplaza("[lac]","");
        //Total
        rep.comRemplaza("[tot]","");
      }
      //Dentro de Recinto Portuario.
      if (vDatos.getInt("lDentroRecintoPort") == 1){
        rep.comRemplaza("[ir]","X");
        rep.comRemplaza("[if]"," ");
        rep.comRemplaza("[it]"," ");
        rep.comRemplaza("[cTerrestre]","");
        rep.comRemplaza("[ia]"," ");
        //Maritima
        rep.comRemplaza("[im]"," ");
        rep.comRemplaza("[mar]"," ");
        //Fluvial
        rep.comRemplaza("[iu]","");
        rep.comRemplaza("[flu]","");
        //Lacustre
        rep.comRemplaza("[il]","");
        rep.comRemplaza("[lac]","");
        //Total
        rep.comRemplaza("[tot]","");
      }

      //Propiedad Nacional.
      if (vDatos.getInt("[iPropiedad]") == 0){
        rep.comRemplaza("[pn]","X");
        rep.comRemplaza("[pr]"," ");
      }
      //Propiedad Particular.
      if (vDatos.getInt("[iPropiedad]") == 1){
        rep.comRemplaza("[pn]"," ");
        rep.comRemplaza("[pr]","X");
      }

      //Frente a la Zona Federal Marítimo Terrestre.
      if (vRegsTituloProp.size()>0){
        TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
        String cZonaFederal = "";
        if (vDatosTitProp.get("cLotePropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
        if (vDatosTitProp.get("cSeccionPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
        if (vDatosTitProp.get("cManzanaPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
        if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
        if (vDatosTitProp.get("cCallePropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
        if (vDatosTitProp.get("cKmPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
        if (vDatosTitProp.get("cColoniaPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
        if (vDatosTitProp.get("cDscMunicipio")!= null)
          cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
        if (vDatosTitProp.get("cDscEntidadFed")!= null)
          cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
        if (vDatosTitProp.get("cDscPuerto")!= null)
          cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
        rep.comRemplaza("[cFrenteZona]",cZonaFederal);
      }

      if (vRegsTituloDocumentos.size()>0){
        for(int i=0;i<vRegsTituloDocumentos.size();i++){
          TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
          switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

            //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
            case 1:case 2:{
              rep.comRemplaza("[tc]","X");
              rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
              rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
              rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              break;
            }

            //Autorizacion en Materia de Impacto Ambiental.
            case 5:{
              rep.comRemplaza("[ai]","X");
              rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
              rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
              rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              break;
            }
          }
        }
      }

      if (vRegsTitPoliza.size()>0){
        for(int i=0;i<vRegsTitPoliza.size();i++){
          TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
          //Pólizas de Seguro.
          if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
            rep.comRemplaza("[pg]","");
            rep.comRemplaza("[re]","");
            //Responsabilidad Civil.
            if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
              rep.comRemplaza("[in]","X");
              rep.comRemplaza("[rc]"," ");
            }
            //Instalaciones Portuarias.
            if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
              rep.comRemplaza("[in]"," ");
              rep.comRemplaza("[rc]","X");
            }
            rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
            rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
            rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
            rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
            rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
            rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
            rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
          }

          //Polizas de Fianzas
          if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
            rep.comRemplaza("[pf]","X");
            rep.comRemplaza("[ed]","X");
            rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
            rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
            rep.comRemplaza("[ImpEndoso]","");
            rep.comRemplaza("[ActEndoso]","");
            rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
            rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
            rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
            rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
          }
        }
      }

    } else {
      //Sin Valores en la Base.



    }

    //return ;
    // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
    Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                           Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                           iCveOficinaDest,iCveDeptoDest,
                           0,0,0,
                           "",cAsunto,
                           "", "",
                           true,"cCuerpo",vcCuerpo,
                           true, vcCopiasPara,
                           rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

    TVDinRep vDinRep = new TVDinRep();
    vDinRep.put("iEjercicio",iEjercicio);
    vDinRep.put("iMovProcedimiento",iMovProcedimiento);
    vDinRep.put("iMovFolioProc",iMovFolioProc);
    vDinRep.put("iEjercicioFol",iEjercicioOficio);
    vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
    vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
    vDinRep.put("cDigitosFolio",cDigitosFolio);
    vDinRep.put("iConsecutivo",iConsecutivo);
    vDinRep.put("lEsProrroga",0);
    vDinRep.put("lEsAlegato",0);
    vDinRep.put("lEsSinEfecto",0);

   try{
     vDinRep = dCYSFolioProc.updateProrroga(vDinRep,null);
   } catch(Exception ex){
     ex.printStackTrace();
   }

   return vRetorno;

}

 public Vector genAlegatos(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
   Vector vcCuerpo = new Vector();
   Vector vcCopiasPara = new Vector();
   Vector vRegs = new Vector();
   Vector vRegsTituloProp = new Vector();
   Vector vRegsTituloDocumentos = new Vector();
   Vector vRegsTitPoliza = new Vector();
   Vector vRegsSerPor = new Vector();
   Vector vRegsSerCon = new Vector();
   TWord rep = new TWord();
   TFechas Fechas = new TFechas("44");
   TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

   int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
   String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Se concede plazo para Producir Alegatos";
   String cTipoContrato="";
   String[] aFiltros = cQuery.split(",");
   int iCveTitulo = Integer.parseInt(aFiltros[0]);
   int iEjercicio = Integer.parseInt(aFiltros[1]);
   int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
   int iMovFolioProc = Integer.parseInt(aFiltros[3]);
   //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
   //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

   this.setDatosFolio(cNumFolio);



   // Query de Consulta del Reporte.
   try{
     vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                     "CPATitulo.iCveTipoTitulo, " +
                                     "CPATitulo.dtIniVigenciaTitulo, " +
                                     "CPATitulo.dtVigenciaTitulo, " +
                                     "CPATituloUbicacion.lDentroRecintoPort, " +
                                     "CPATitulo.cZonaFedTotalTerrestre, " +
                                     "CPATitulo.cZonaFedTotalAgua, " +
                                     "CPATitulo.iPropiedad, " +
                                     "CPATitulo.iCveClasificacion " +
                                     "from CPATitulo " +
                                     "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                     "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

   }catch(SQLException ex){
     ex.printStackTrace();
     cMensaje = ex.getMessage();
   }catch(Exception ex2){
     ex2.printStackTrace();
   }

   //Infomacion del Titulo de Propiedad
   try{ vRegsTituloProp = super.FindByGeneric("",
                        " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                        " Notario.cApPaterno, "+
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
                        " where CPATitulo.iCveTitulo = " + iCveTitulo,
                        dataSourceName);
   } catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
   }catch(Exception ex2){
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
   }



   // Elaboración del Reporte.
   rep.iniciaReporte();
   if (vRegs.size() > 0){
     TVDinRep vDatos = (TVDinRep) vRegs.get(0);

     //Identificación que se trata de Concesión.
     if (vDatos.getInt("iCveTipoTiulo") == 1){
       //Concesion de Puerto.
       if(vDatos.getInt("iCveClasificacion") == 1){
         rep.comRemplaza("[cp]","X");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Concesion de Terminal.
       if(vDatos.getInt("iCveClasificacion") == 2){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]","X");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Concesion de Marina.
       if(vDatos.getInt("iCveClasificacion") == 3){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]","X");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Concesion de Instalación.
       if(vDatos.getInt("iCveClasificacion") == 4){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]","X");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
     }
     //Identificación que se trata de Permiso.
     if (vDatos.getInt("iCveTipoTiulo") == 2){
       //Permiso de Embarcadero.
       if(vDatos.getInt("iCveClasificacion") == 5){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]","X");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Permiso de Atracadero.
       if(vDatos.getInt("iCveClasificacion") == 6){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]","X");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Permiso de Botadero.
       if(vDatos.getInt("iCveClasificacion") == 7){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]","X");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Permiso de Similar.
       if(vDatos.getInt("iCveClasificacion") == 8){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]","X");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Permiso de Servicio Portuario.
       if(vDatos.getInt("iCveClasificacion") == 9){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]","X");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Permiso de Servicio Conexo.
       if(vDatos.getInt("iCveClasificacion") == 10){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]","X");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
     }

     //Identificación que se trata de Autorizacion.
     if (vDatos.getInt("iCveTipoTiulo") == 3){
       //Autorización de Obra Marítima.
       if(vDatos.getInt("iCveClasificacion") == 11){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]","X");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Autorización de Dragado de Construcción.
       if(vDatos.getInt("iCveClasificacion") == 12){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]","X");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]"," ");
       }
       //Autorización de Dragado de Mantenimiento.
       if(vDatos.getInt("iCveClasificacion") == 13){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]","X");
         rep.comRemplaza("[aa]"," ");
       }
       //Autorización de Dragado de Mantenimiento.
       if(vDatos.getInt("iCveClasificacion") == 14){
         rep.comRemplaza("[cp]"," ");
         rep.comRemplaza("[ct]"," ");
         rep.comRemplaza("[cn]"," ");
         rep.comRemplaza("[ci]"," ");
         rep.comRemplaza("[cv]"," ");
         rep.comRemplaza("[pe]"," ");
         rep.comRemplaza("[pa]"," ");
         rep.comRemplaza("[pb]"," ");
         rep.comRemplaza("[ps]"," ");
         rep.comRemplaza("[pp]"," ");
         rep.comRemplaza("[pc]"," ");
         rep.comRemplaza("[ao]"," ");
         rep.comRemplaza("[ad]"," ");
         rep.comRemplaza("[am]"," ");
         rep.comRemplaza("[aa]","X");
       }
     }

     rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
     rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
     rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
     rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

     //Fuera de Recinto Portuario
     if (vDatos.getInt("lDentroRecintoPort") == 0){
       rep.comRemplaza("[ir]"," ");
       rep.comRemplaza("[if]","X");
       rep.comRemplaza("[it]","X");
       rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
       rep.comRemplaza("[ia]","X");
       //Maritima
       rep.comRemplaza("[im]","X");
       rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
       //Fluvial
       rep.comRemplaza("[iu]","");
       rep.comRemplaza("[flu]","");
       //Lacustre
       rep.comRemplaza("[il]","");
       rep.comRemplaza("[lac]","");
       //Total
       rep.comRemplaza("[tot]","");
     }
     //Dentro de Recinto Portuario.
     if (vDatos.getInt("lDentroRecintoPort") == 1){
       rep.comRemplaza("[ir]","X");
       rep.comRemplaza("[if]"," ");
       rep.comRemplaza("[it]"," ");
       rep.comRemplaza("[cTerrestre]","");
       rep.comRemplaza("[ia]"," ");
       //Maritima
       rep.comRemplaza("[im]"," ");
       rep.comRemplaza("[mar]"," ");
       //Fluvial
       rep.comRemplaza("[iu]","");
       rep.comRemplaza("[flu]","");
       //Lacustre
       rep.comRemplaza("[il]","");
       rep.comRemplaza("[lac]","");
       //Total
       rep.comRemplaza("[tot]","");
     }

     //Propiedad Nacional.
     if (vDatos.getInt("[iPropiedad]") == 0){
       rep.comRemplaza("[pn]","X");
       rep.comRemplaza("[pr]"," ");
     }
     //Propiedad Particular.
     if (vDatos.getInt("[iPropiedad]") == 1){
       rep.comRemplaza("[pn]"," ");
       rep.comRemplaza("[pr]","X");
     }

     //Frente a la Zona Federal Marítimo Terrestre.
     if (vRegsTituloProp.size()>0){
       TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
       String cZonaFederal = "";
       if (vDatosTitProp.get("cLotePropiedad")!= null)
         cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
       if (vDatosTitProp.get("cSeccionPropiedad")!= null)
         cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
       if (vDatosTitProp.get("cManzanaPropiedad")!= null)
         cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
       if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
         cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
       if (vDatosTitProp.get("cCallePropiedad")!= null)
         cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
       if (vDatosTitProp.get("cKmPropiedad")!= null)
         cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
       if (vDatosTitProp.get("cColoniaPropiedad")!= null)
         cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
       if (vDatosTitProp.get("cDscMunicipio")!= null)
         cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
       if (vDatosTitProp.get("cDscEntidadFed")!= null)
         cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
       if (vDatosTitProp.get("cDscPuerto")!= null)
         cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
       rep.comRemplaza("[cFrenteZona]",cZonaFederal);
     }

     if (vRegsTituloDocumentos.size()>0){
       for(int i=0;i<vRegsTituloDocumentos.size();i++){
         TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
         switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

           //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
           case 1:case 2:{
             rep.comRemplaza("[tc]","X");
             rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
             rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
             rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
             rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
             break;
           }

           //Autorizacion en Materia de Impacto Ambiental.
           case 5:{
             rep.comRemplaza("[ai]","X");
             rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
             rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
             rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
             rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
             break;
           }
         }
       }
     }

     if (vRegsTitPoliza.size()>0){
       for(int i=0;i<vRegsTitPoliza.size();i++){
         TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
         //Pólizas de Seguro.
         if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
           rep.comRemplaza("[pg]","");
           rep.comRemplaza("[re]","");
           //Responsabilidad Civil.
           if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
             rep.comRemplaza("[in]","X");
             rep.comRemplaza("[rc]"," ");
           }
           //Instalaciones Portuarias.
           if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
             rep.comRemplaza("[in]"," ");
             rep.comRemplaza("[rc]","X");
           }
           rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
           rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
           rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
           rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
           rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
           rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
           rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
         }

         //Polizas de Fianzas
         if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
           rep.comRemplaza("[pf]","X");
           rep.comRemplaza("[ed]","X");
           rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
           rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
           rep.comRemplaza("[ImpEndoso]","");
           rep.comRemplaza("[ActEndoso]","");
           rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
           rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
           rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
           rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
         }
       }
     }

   } else {
     //Sin Valores en la Base.



   }

   //return ;
   // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
   Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                          Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                          iCveOficinaDest,iCveDeptoDest,
                          0,0,0,
                          "",cAsunto,
                          "", "",
                          true,"cCuerpo",vcCuerpo,
                          true, vcCopiasPara,
                          rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

   TVDinRep vDinRep = new TVDinRep();
   vDinRep.put("iEjercicio",iEjercicio);
   vDinRep.put("iMovProcedimiento",iMovProcedimiento);
   vDinRep.put("iMovFolioProc",iMovFolioProc);
   vDinRep.put("iEjercicioFol",iEjercicioOficio);
   vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
   vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
   vDinRep.put("cDigitosFolio",cDigitosFolio);
   vDinRep.put("iConsecutivo",iConsecutivo);
   vDinRep.put("lEsProrroga",0);
   vDinRep.put("lEsAlegato",1);
   vDinRep.put("lEsSinEfecto",0);

  try{
    vDinRep = dCYSFolioProc.updateAlegatos(vDinRep,null);
  } catch(Exception ex){
    ex.printStackTrace();
  }

  return vRetorno;

 }

 public Vector genSinEfectos(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
   Vector vcCuerpo = new Vector();
   Vector vcCopiasPara = new Vector();
   Vector vRegs = new Vector();
   Vector vRegsTituloProp = new Vector();
   Vector vRegsTituloDocumentos = new Vector();
   Vector vRegsTitPoliza = new Vector();
   Vector vRegsSerPor = new Vector();
   Vector vRegsSerCon = new Vector();
   TWord rep = new TWord();
   TFechas Fechas = new TFechas("44");
   TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

   int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
   String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Se deja sin efectos el Procedimiento Administrativo de ";
   String cTipoContrato="";
   String[] aFiltros = cQuery.split(",");
   int iCveTitulo = Integer.parseInt(aFiltros[0]);
   int iEjercicio = Integer.parseInt(aFiltros[1]);
   int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
   int iMovFolioProc = Integer.parseInt(aFiltros[3]);
   //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
   //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

   this.setDatosFolio(cNumFolio);

   // Query de Consulta del Reporte.
   try{
     vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                     "CPATitulo.iCveTipoTitulo, " +
                                     "CPATitulo.dtIniVigenciaTitulo, " +
                                     "CPATitulo.dtVigenciaTitulo, " +
                                     "CPATituloUbicacion.lDentroRecintoPort, " +
                                     "CPATitulo.cZonaFedTotalTerrestre, " +
                                     "CPATitulo.cZonaFedTotalAgua, " +
                                     "CPATitulo.iPropiedad, " +
                                     "CPATitulo.iCveClasificacion " +
                                     "from CPATitulo " +
                                     "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                     "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

   }catch(SQLException ex){
     ex.printStackTrace();
     cMensaje = ex.getMessage();
   }catch(Exception ex2){
     ex2.printStackTrace();
   }




   // Elaboración del Reporte.
   rep.iniciaReporte();
   if (vRegs.size() > 0){
     TVDinRep vDatos = (TVDinRep) vRegs.get(0);










   } else {
     //Sin Valores en la Base.



   }

   //return ;
   // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
   Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                          Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                          iCveOficinaDest,iCveDeptoDest,
                          0,0,0,
                          "",cAsunto,
                          "", "",
                          true,"cCuerpo",vcCuerpo,
                          true, vcCopiasPara,
                          rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

   TVDinRep vDinRep = new TVDinRep();
   vDinRep.put("iEjercicio",iEjercicio);
   vDinRep.put("iMovProcedimiento",iMovProcedimiento);
   vDinRep.put("iMovFolioProc",iMovFolioProc);
   vDinRep.put("iEjercicioFol",iEjercicioOficio);
   vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
   vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
   vDinRep.put("cDigitosFolio",cDigitosFolio);
   vDinRep.put("iConsecutivo",iConsecutivo);
   vDinRep.put("lEsProrroga",0);
   vDinRep.put("lEsAlegato",0);
   vDinRep.put("lEsSinEfecto",1);

  try{
    vDinRep = dCYSFolioProc.updateSinEfectos(vDinRep,null);
  } catch(Exception ex){
    ex.printStackTrace();
  }

  return vRetorno;

 }

 public Vector genNotificacion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
   Vector vcCuerpo = new Vector();
   Vector vcCopiasPara = new Vector();
   Vector vRegs = new Vector();
   Vector vRegsTituloProp = new Vector();
   Vector vRegsTituloDocumentos = new Vector();
   Vector vRegsTitPoliza = new Vector();
   Vector vRegsSerPor = new Vector();
   Vector vRegsSerCon = new Vector();
   TWord rep = new TWord();
   TFechas Fechas = new TFechas("44");
   TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
   TDCYSNotOficio dCYSNotOficio = new TDCYSNotOficio ();

   int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
   String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="";
   String cTipoContrato="";
   String[] aFiltros = cQuery.split(",");
   int iCveTitulo = Integer.parseInt(aFiltros[0]);
   int iEjercicio = Integer.parseInt(aFiltros[1]);
   int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
   int iMovFolioProc = Integer.parseInt(aFiltros[3]);
   int iMovCitaNotificacion = Integer.parseInt(aFiltros[4]);
   //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
   //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

   cAsunto = "Se solicita realizar Notificación";
   this.setDatosFolio(cNumFolio);

   // Query de Consulta del Reporte.
   try{
     vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                     "CPATitulo.iCveTipoTitulo, " +
                                     "CPATitulo.dtIniVigenciaTitulo, " +
                                     "CPATitulo.dtVigenciaTitulo, " +
                                     "CPATituloUbicacion.lDentroRecintoPort, " +
                                     "CPATitulo.cZonaFedTotalTerrestre, " +
                                     "CPATitulo.cZonaFedTotalAgua, " +
                                     "CPATitulo.iPropiedad, " +
                                     "CPATitulo.iCveClasificacion " +
                                     "from CPATitulo " +
                                     "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                     "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

   }catch(SQLException ex){
     ex.printStackTrace();
     cMensaje = ex.getMessage();
   }catch(Exception ex2){
     ex2.printStackTrace();
   }

   // Elaboración del Reporte.
   rep.iniciaReporte();
   if (vRegs.size() > 0){
     TVDinRep vDatos = (TVDinRep) vRegs.get(0);

   } else {
     //Sin Valores en la Base.



   }

   rep.comRemplaza("[cp]","X");

   //return ;
   // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
   Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                          Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                          iCveOficinaDest,iCveDeptoDest,
                          0,0,0,
                          "",cAsunto,
                          "", "",
                          true,"cCuerpo",vcCuerpo,
                          true, vcCopiasPara,
                          rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

   TVDinRep vDinRep = new TVDinRep();
   vDinRep.put("iEjercicio",iEjercicio);
   vDinRep.put("iMovProcedimiento",iMovProcedimiento);
   //vDinRep.put("iMovFolioProc",iMovFolioProc);
   //vDinRep.put("iMovCitaNotificacion",iMovCitaNotificacion);
   vDinRep.put("iEjercicioFol",iEjercicioOficio);
   vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
   vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
   vDinRep.put("cDigitosFolio",cDigitosFolio);
   vDinRep.put("iConsecutivo",iConsecutivo);
   vDinRep.put("lEsProrroga",0);
   vDinRep.put("lEsAlegato",0);
   vDinRep.put("lEsSinEfecto",0);

  try{

    TVDinRep vDinRep2 = dCYSFolioProc.insertNotificacion(vDinRep,null);
    TVDinRep vDinRep3 = new TVDinRep();
    vDinRep3.put("iEjercicio",          vDinRep.getInt("iEjercicio"));
    vDinRep3.put("iMovProcedimiento",   vDinRep.getInt("iMovProcedimiento"));
    vDinRep3.put("iMovFolioProc",       vDinRep2.getInt("iMovFolioProc"));
    vDinRep3.put("iMovCitaNotificacion",iMovCitaNotificacion);
    vDinRep3.put("iEjercicioNot",       vDinRep2.getInt("iEjercicio"));
    vDinRep3.put("iMovProcedimientoNot",vDinRep2.getInt("iMovProcedimiento"));
    vDinRep3.put("iMovFolioProcNot",    vDinRep2.getInt("iMovFolioProc"));
    vDinRep3 = dCYSNotOficio.updNotificacion(vDinRep3,null);

  } catch(Exception ex){
    ex.printStackTrace();
  }

  return vRetorno;

 }

 public Vector genRecordatorio(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
   Vector vcCuerpo = new Vector();
   Vector vcCopiasPara = new Vector();
   Vector vRegs = new Vector();
   Vector vRegsTituloProp = new Vector();
   Vector vRegsTituloDocumentos = new Vector();
   Vector vRegsTitPoliza = new Vector();
   Vector vRegsSerPor = new Vector();
   Vector vRegsSerCon = new Vector();
   TWord rep = new TWord();
   TFechas Fechas = new TFechas("44");
   TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
   TDCYSNotOficio dCYSNotOficio = new TDCYSNotOficio ();

   int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
   String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Se solicita realizar Notificación";
   String cTipoContrato="";
   String[] aFiltros = cQuery.split(",");
   int iCveTitulo = Integer.parseInt(aFiltros[0]);
   int iEjercicio = Integer.parseInt(aFiltros[1]);
   int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
   int iMovFolioProc = Integer.parseInt(aFiltros[3]);
   int iMovCitaNotificacion = Integer.parseInt(aFiltros[4]);
   //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
   //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

   this.setDatosFolio(cNumFolio);

   // Query de Consulta del Reporte.
   try{
     vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                     "CPATitulo.iCveTipoTitulo, " +
                                     "CPATitulo.dtIniVigenciaTitulo, " +
                                     "CPATitulo.dtVigenciaTitulo, " +
                                     "CPATituloUbicacion.lDentroRecintoPort, " +
                                     "CPATitulo.cZonaFedTotalTerrestre, " +
                                     "CPATitulo.cZonaFedTotalAgua, " +
                                     "CPATitulo.iPropiedad, " +
                                     "CPATitulo.iCveClasificacion " +
                                     "from CPATitulo " +
                                     "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                     "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

   }catch(SQLException ex){
     ex.printStackTrace();
     cMensaje = ex.getMessage();
   }catch(Exception ex2){
     ex2.printStackTrace();
   }

   // Elaboración del Reporte.
   rep.iniciaReporte();
   if (vRegs.size() > 0){
     TVDinRep vDatos = (TVDinRep) vRegs.get(0);

   } else {
     //Sin Valores en la Base.

   }

   //return ;
   // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
   Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                          Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                          iCveOficinaDest,iCveDeptoDest,
                          0,0,0,
                          "",cAsunto,
                          "", "",
                          true,"cCuerpo",vcCuerpo,
                          true, vcCopiasPara,
                          rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

   TVDinRep vDinRep = new TVDinRep();
   vDinRep.put("iEjercicio",iEjercicio);
   vDinRep.put("iMovProcedimiento",iMovProcedimiento);
   vDinRep.put("iMovFolioProc",iMovFolioProc);
   vDinRep.put("iMovCitaNotificacion",iMovCitaNotificacion);
   vDinRep.put("iEjercicioFol",iEjercicioOficio);
   vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
   vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
   vDinRep.put("cDigitosFolio",cDigitosFolio);
   vDinRep.put("iConsecutivo",iConsecutivo);
   vDinRep.put("lEsProrroga",0);
   vDinRep.put("lEsAlegato",0);
   vDinRep.put("lEsSinEfecto",0);

  try{
    TVDinRep vDinRep2 = dCYSFolioProc.insert(vDinRep,null);
    //falta la parte de escbir la notificacoin o recordaotrio

   vDinRep2 = dCYSNotOficio.updRecordatorio(vDinRep,null);

  } catch(Exception ex){
    ex.printStackTrace();
  }

  return vRetorno;

 }

  public Vector genReversionPFP(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
            Vector vcCuerpo = new Vector();
            Vector vcCopiasPara = new Vector();
            Vector vRegs = new Vector();
            Vector vRegsTituloProp = new Vector();
            Vector vRegsTituloDocumentos = new Vector();
            Vector vRegsTitPoliza = new Vector();
            Vector vRegsSerPor = new Vector();
            Vector vRegsSerCon = new Vector();
            TWord rep = new TWord();
            TFechas Fechas = new TFechas("44");
            TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

            int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
            String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Reversión dirigida a la PFP";
            String cTipoContrato="";
            String[] aFiltros = cQuery.split(",");
            int iCveTitulo = Integer.parseInt(aFiltros[0]);
            int iEjercicio = Integer.parseInt(aFiltros[1]);
            int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
            //int iMovFolioProc = Integer.parseInt(aFiltros[3]);
            //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
            //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

            this.setDatosFolio(cNumFolio);



            // Query de Consulta del Reporte.
            try{
              vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                              "CPATitulo.iCveTipoTitulo, " +
                                              "CPATitulo.dtIniVigenciaTitulo, " +
                                              "CPATitulo.dtVigenciaTitulo, " +
                                              "CPATituloUbicacion.lDentroRecintoPort, " +
                                              "CPATitulo.cZonaFedTotalTerrestre, " +
                                              "CPATitulo.cZonaFedTotalAgua, " +
                                              "CPATitulo.iPropiedad, " +
                                              "CPATitulo.iCveClasificacion " +
                                              "from CPATitulo " +
                                              "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                              "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

            }catch(SQLException ex){
              ex.printStackTrace();
              cMensaje = ex.getMessage();
            }catch(Exception ex2){
              ex2.printStackTrace();
            }

            //Infomacion del Titulo de Propiedad
            try{ vRegsTituloProp = super.FindByGeneric("",
                                 " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                                 " Notario.cApPaterno, "+
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
                                 " where CPATitulo.iCveTitulo = " + iCveTitulo,
                                 dataSourceName);
            } catch(SQLException ex){
               ex.printStackTrace();
               cMensaje = ex.getMessage();
            }catch(Exception ex2){
               ex2.printStackTrace();
               cMensaje += ex2.getMessage();
            }



            // Elaboración del Reporte.
            rep.iniciaReporte();
            if (vRegs.size() > 0){
              TVDinRep vDatos = (TVDinRep) vRegs.get(0);

              //Identificación que se trata de Concesión.
              if (vDatos.getInt("iCveTipoTiulo") == 1){
                //Concesion de Puerto.
                if(vDatos.getInt("iCveClasificacion") == 1){
                  rep.comRemplaza("[cp]","X");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Concesion de Terminal.
                if(vDatos.getInt("iCveClasificacion") == 2){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]","X");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Concesion de Marina.
                if(vDatos.getInt("iCveClasificacion") == 3){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]","X");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Concesion de Instalación.
                if(vDatos.getInt("iCveClasificacion") == 4){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]","X");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
              }
              //Identificación que se trata de Permiso.
              if (vDatos.getInt("iCveTipoTiulo") == 2){
                //Permiso de Embarcadero.
                if(vDatos.getInt("iCveClasificacion") == 5){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]","X");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Permiso de Atracadero.
                if(vDatos.getInt("iCveClasificacion") == 6){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]","X");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Permiso de Botadero.
                if(vDatos.getInt("iCveClasificacion") == 7){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]","X");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Permiso de Similar.
                if(vDatos.getInt("iCveClasificacion") == 8){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]","X");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Permiso de Servicio Portuario.
                if(vDatos.getInt("iCveClasificacion") == 9){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]","X");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Permiso de Servicio Conexo.
                if(vDatos.getInt("iCveClasificacion") == 10){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]","X");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
              }

              //Identificación que se trata de Autorizacion.
              if (vDatos.getInt("iCveTipoTiulo") == 3){
                //Autorización de Obra Marítima.
                if(vDatos.getInt("iCveClasificacion") == 11){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]","X");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Autorización de Dragado de Construcción.
                if(vDatos.getInt("iCveClasificacion") == 12){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]","X");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]"," ");
                }
                //Autorización de Dragado de Mantenimiento.
                if(vDatos.getInt("iCveClasificacion") == 13){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]","X");
                  rep.comRemplaza("[aa]"," ");
                }
                //Autorización de Dragado de Mantenimiento.
                if(vDatos.getInt("iCveClasificacion") == 14){
                  rep.comRemplaza("[cp]"," ");
                  rep.comRemplaza("[ct]"," ");
                  rep.comRemplaza("[cn]"," ");
                  rep.comRemplaza("[ci]"," ");
                  rep.comRemplaza("[cv]"," ");
                  rep.comRemplaza("[pe]"," ");
                  rep.comRemplaza("[pa]"," ");
                  rep.comRemplaza("[pb]"," ");
                  rep.comRemplaza("[ps]"," ");
                  rep.comRemplaza("[pp]"," ");
                  rep.comRemplaza("[pc]"," ");
                  rep.comRemplaza("[ao]"," ");
                  rep.comRemplaza("[ad]"," ");
                  rep.comRemplaza("[am]"," ");
                  rep.comRemplaza("[aa]","X");
                }
              }

              rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
              rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
              rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
              rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

              //Fuera de Recinto Portuario
              if (vDatos.getInt("lDentroRecintoPort") == 0){
                rep.comRemplaza("[ir]"," ");
                rep.comRemplaza("[if]","X");
                rep.comRemplaza("[it]","X");
                rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
                rep.comRemplaza("[ia]","X");
                //Maritima
                rep.comRemplaza("[im]","X");
                rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
                //Fluvial
                rep.comRemplaza("[iu]","");
                rep.comRemplaza("[flu]","");
                //Lacustre
                rep.comRemplaza("[il]","");
                rep.comRemplaza("[lac]","");
                //Total
                rep.comRemplaza("[tot]","");
              }
              //Dentro de Recinto Portuario.
              if (vDatos.getInt("lDentroRecintoPort") == 1){
                rep.comRemplaza("[ir]","X");
                rep.comRemplaza("[if]"," ");
                rep.comRemplaza("[it]"," ");
                rep.comRemplaza("[cTerrestre]","");
                rep.comRemplaza("[ia]"," ");
                //Maritima
                rep.comRemplaza("[im]"," ");
                rep.comRemplaza("[mar]"," ");
                //Fluvial
                rep.comRemplaza("[iu]","");
                rep.comRemplaza("[flu]","");
                //Lacustre
                rep.comRemplaza("[il]","");
                rep.comRemplaza("[lac]","");
                //Total
                rep.comRemplaza("[tot]","");
              }

              //Propiedad Nacional.
              if (vDatos.getInt("[iPropiedad]") == 0){
                rep.comRemplaza("[pn]","X");
                rep.comRemplaza("[pr]"," ");
              }
              //Propiedad Particular.
              if (vDatos.getInt("[iPropiedad]") == 1){
                rep.comRemplaza("[pn]"," ");
                rep.comRemplaza("[pr]","X");
              }

              //Frente a la Zona Federal Marítimo Terrestre.
              if (vRegsTituloProp.size()>0){
                TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
                String cZonaFederal = "";
                if (vDatosTitProp.get("cLotePropiedad")!= null)
                  cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
                if (vDatosTitProp.get("cSeccionPropiedad")!= null)
                  cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
                if (vDatosTitProp.get("cManzanaPropiedad")!= null)
                  cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
                if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
                  cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
                if (vDatosTitProp.get("cCallePropiedad")!= null)
                  cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
                if (vDatosTitProp.get("cKmPropiedad")!= null)
                  cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
                if (vDatosTitProp.get("cColoniaPropiedad")!= null)
                  cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
                if (vDatosTitProp.get("cDscMunicipio")!= null)
                  cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
                if (vDatosTitProp.get("cDscEntidadFed")!= null)
                  cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
                if (vDatosTitProp.get("cDscPuerto")!= null)
                  cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
                rep.comRemplaza("[cFrenteZona]",cZonaFederal);
              }

              if (vRegsTituloDocumentos.size()>0){
                for(int i=0;i<vRegsTituloDocumentos.size();i++){
                  TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
                  switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

                    //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
                    case 1:case 2:{
                      rep.comRemplaza("[tc]","X");
                      rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
                      rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                      rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
                      rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                      break;
                    }

                    //Autorizacion en Materia de Impacto Ambiental.
                    case 5:{
                      rep.comRemplaza("[ai]","X");
                      rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
                      rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                      rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
                      rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                      break;
                    }
                  }
                }
              }

              if (vRegsTitPoliza.size()>0){
                for(int i=0;i<vRegsTitPoliza.size();i++){
                  TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
                  //Pólizas de Seguro.
                  if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
                    rep.comRemplaza("[pg]","");
                    rep.comRemplaza("[re]","");
                    //Responsabilidad Civil.
                    if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
                      rep.comRemplaza("[in]","X");
                      rep.comRemplaza("[rc]"," ");
                    }
                    //Instalaciones Portuarias.
                    if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
                      rep.comRemplaza("[in]"," ");
                      rep.comRemplaza("[rc]","X");
                    }
                    rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
                    rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
                    rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
                    rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
                    rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
                    rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
                    rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
                  }

                  //Polizas de Fianzas
                  if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
                    rep.comRemplaza("[pf]","X");
                    rep.comRemplaza("[ed]","X");
                    rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
                    rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
                    rep.comRemplaza("[ImpEndoso]","");
                    rep.comRemplaza("[ActEndoso]","");
                    rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
                    rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
                    rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
                    rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
                  }
                }
              }

            } else            	
            	System.out.println("Sin Valores en la base");
            

            //return ;
            // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
            Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                                   Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                                   iCveOficinaDest,iCveDeptoDest,
                                   0,0,0,
                                   "",cAsunto,
                                   "", "",
                                   true,"cCuerpo",vcCuerpo,
                                   true, vcCopiasPara,
                                   rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

            TVDinRep vDinRep = new TVDinRep();
            vDinRep.put("iEjercicio",iEjercicio);
            vDinRep.put("iMovProcedimiento",iMovProcedimiento);
            vDinRep.put("iEjercicioFol",iEjercicioOficio);
            vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
            vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
            vDinRep.put("cDigitosFolio",cDigitosFolio);
            vDinRep.put("iConsecutivo",iConsecutivo);
            vDinRep.put("lEsProrroga",0);
            vDinRep.put("lEsAlegato",0);
            vDinRep.put("lEsSinEfecto",0);

           try{
             vDinRep = dCYSFolioProc.insert(vDinRep,null);
           } catch(Exception ex){
             ex.printStackTrace();
           }

           return vRetorno;

          }

  public Vector genReversionAPI(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
    Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
    Vector vRegs = new Vector();
    Vector vRegsTituloProp = new Vector();
    Vector vRegsTituloDocumentos = new Vector();
    Vector vRegsTitPoliza = new Vector();
    Vector vRegsSerPor = new Vector();
    Vector vRegsSerCon = new Vector();
    TWord rep = new TWord();
    TFechas Fechas = new TFechas("44");
    TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

    int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
    String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Reversión dirigida a la API";
    String cTipoContrato="";
    String[] aFiltros = cQuery.split(",");
    int iCveTitulo = Integer.parseInt(aFiltros[0]);
    int iEjercicio = Integer.parseInt(aFiltros[1]);
    int iMovProcedimiento = Integer.parseInt(aFiltros[2]);

    this.setDatosFolio(cNumFolio);

    // Query de Consulta del Reporte.
    try{
       vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                       "CPATitulo.iCveTipoTitulo, " +
                                       "CPATitulo.dtIniVigenciaTitulo, " +
                                       "CPATitulo.dtVigenciaTitulo, " +
                                       "CPATituloUbicacion.lDentroRecintoPort, " +
                                       "CPATitulo.cZonaFedTotalTerrestre, " +
                                       "CPATitulo.cZonaFedTotalAgua, " +
                                       "CPATitulo.iPropiedad, " +
                                       "CPATitulo.iCveClasificacion " +
                                       "from CPATitulo " +
                                       "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                       "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);
    }catch(SQLException ex){
        ex.printStackTrace();
        cMensaje = ex.getMessage();
    }catch(Exception ex2){
        ex2.printStackTrace();
    }

                 //Infomacion del Titulo de Propiedad
                 try{ vRegsTituloProp = super.FindByGeneric("",
                                      " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                                      " Notario.cApPaterno, "+
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
                                      " where CPATitulo.iCveTitulo = " + iCveTitulo,
                                      dataSourceName);
                 } catch(SQLException ex){
                    ex.printStackTrace();
                    cMensaje = ex.getMessage();
                 }catch(Exception ex2){
                    ex2.printStackTrace();
                    cMensaje += ex2.getMessage();
                 }



                 // Elaboración del Reporte.
                 rep.iniciaReporte();
                 if (vRegs.size() > 0){
                   TVDinRep vDatos = (TVDinRep) vRegs.get(0);

                   //Identificación que se trata de Concesión.
                   if (vDatos.getInt("iCveTipoTiulo") == 1){
                     //Concesion de Puerto.
                     if(vDatos.getInt("iCveClasificacion") == 1){
                       rep.comRemplaza("[cp]","X");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Concesion de Terminal.
                     if(vDatos.getInt("iCveClasificacion") == 2){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]","X");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Concesion de Marina.
                     if(vDatos.getInt("iCveClasificacion") == 3){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]","X");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Concesion de Instalación.
                     if(vDatos.getInt("iCveClasificacion") == 4){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]","X");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                   }
                   //Identificación que se trata de Permiso.
                   if (vDatos.getInt("iCveTipoTiulo") == 2){
                     //Permiso de Embarcadero.
                     if(vDatos.getInt("iCveClasificacion") == 5){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]","X");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Permiso de Atracadero.
                     if(vDatos.getInt("iCveClasificacion") == 6){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]","X");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Permiso de Botadero.
                     if(vDatos.getInt("iCveClasificacion") == 7){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]","X");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Permiso de Similar.
                     if(vDatos.getInt("iCveClasificacion") == 8){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]","X");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Permiso de Servicio Portuario.
                     if(vDatos.getInt("iCveClasificacion") == 9){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]","X");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Permiso de Servicio Conexo.
                     if(vDatos.getInt("iCveClasificacion") == 10){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]","X");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                   }

                   //Identificación que se trata de Autorizacion.
                   if (vDatos.getInt("iCveTipoTiulo") == 3){
                     //Autorización de Obra Marítima.
                     if(vDatos.getInt("iCveClasificacion") == 11){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]","X");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Autorización de Dragado de Construcción.
                     if(vDatos.getInt("iCveClasificacion") == 12){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]","X");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Autorización de Dragado de Mantenimiento.
                     if(vDatos.getInt("iCveClasificacion") == 13){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]","X");
                       rep.comRemplaza("[aa]"," ");
                     }
                     //Autorización de Dragado de Mantenimiento.
                     if(vDatos.getInt("iCveClasificacion") == 14){
                       rep.comRemplaza("[cp]"," ");
                       rep.comRemplaza("[ct]"," ");
                       rep.comRemplaza("[cn]"," ");
                       rep.comRemplaza("[ci]"," ");
                       rep.comRemplaza("[cv]"," ");
                       rep.comRemplaza("[pe]"," ");
                       rep.comRemplaza("[pa]"," ");
                       rep.comRemplaza("[pb]"," ");
                       rep.comRemplaza("[ps]"," ");
                       rep.comRemplaza("[pp]"," ");
                       rep.comRemplaza("[pc]"," ");
                       rep.comRemplaza("[ao]"," ");
                       rep.comRemplaza("[ad]"," ");
                       rep.comRemplaza("[am]"," ");
                       rep.comRemplaza("[aa]","X");
                     }
                   }

                   rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
                   rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
                   rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
                   rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

                   //Fuera de Recinto Portuario
                   if (vDatos.getInt("lDentroRecintoPort") == 0){
                     rep.comRemplaza("[ir]"," ");
                     rep.comRemplaza("[if]","X");
                     rep.comRemplaza("[it]","X");
                     rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
                     rep.comRemplaza("[ia]","X");
                     //Maritima
                     rep.comRemplaza("[im]","X");
                     rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
                     //Fluvial
                     rep.comRemplaza("[iu]","");
                     rep.comRemplaza("[flu]","");
                     //Lacustre
                     rep.comRemplaza("[il]","");
                     rep.comRemplaza("[lac]","");
                     //Total
                     rep.comRemplaza("[tot]","");
                   }
                   //Dentro de Recinto Portuario.
                   if (vDatos.getInt("lDentroRecintoPort") == 1){
                     rep.comRemplaza("[ir]","X");
                     rep.comRemplaza("[if]"," ");
                     rep.comRemplaza("[it]"," ");
                     rep.comRemplaza("[cTerrestre]","");
                     rep.comRemplaza("[ia]"," ");
                     //Maritima
                     rep.comRemplaza("[im]"," ");
                     rep.comRemplaza("[mar]"," ");
                     //Fluvial
                     rep.comRemplaza("[iu]","");
                     rep.comRemplaza("[flu]","");
                     //Lacustre
                     rep.comRemplaza("[il]","");
                     rep.comRemplaza("[lac]","");
                     //Total
                     rep.comRemplaza("[tot]","");
                   }

                   //Propiedad Nacional.
                   if (vDatos.getInt("[iPropiedad]") == 0){
                     rep.comRemplaza("[pn]","X");
                     rep.comRemplaza("[pr]"," ");
                   }
                   //Propiedad Particular.
                   if (vDatos.getInt("[iPropiedad]") == 1){
                     rep.comRemplaza("[pn]"," ");
                     rep.comRemplaza("[pr]","X");
                   }

                   //Frente a la Zona Federal Marítimo Terrestre.
                   if (vRegsTituloProp.size()>0){
                     TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
                     String cZonaFederal = "";
                     if (vDatosTitProp.get("cLotePropiedad")!= null)
                       cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
                     if (vDatosTitProp.get("cSeccionPropiedad")!= null)
                       cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
                     if (vDatosTitProp.get("cManzanaPropiedad")!= null)
                       cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
                     if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
                       cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
                     if (vDatosTitProp.get("cCallePropiedad")!= null)
                       cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
                     if (vDatosTitProp.get("cKmPropiedad")!= null)
                       cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
                     if (vDatosTitProp.get("cColoniaPropiedad")!= null)
                       cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
                     if (vDatosTitProp.get("cDscMunicipio")!= null)
                       cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
                     if (vDatosTitProp.get("cDscEntidadFed")!= null)
                       cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
                     if (vDatosTitProp.get("cDscPuerto")!= null)
                       cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
                     rep.comRemplaza("[cFrenteZona]",cZonaFederal);
                   }

                   if (vRegsTituloDocumentos.size()>0){
                     for(int i=0;i<vRegsTituloDocumentos.size();i++){
                       TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
                       switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

                         //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
                         case 1:case 2:{
                           rep.comRemplaza("[tc]","X");
                           rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
                           rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                           rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
                           rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                           break;
                         }

                         //Autorizacion en Materia de Impacto Ambiental.
                         case 5:{
                           rep.comRemplaza("[ai]","X");
                           rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
                           rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                           rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
                           rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
                           break;
                         }
                       }
                     }
                   }

                   if (vRegsTitPoliza.size()>0){
                     for(int i=0;i<vRegsTitPoliza.size();i++){
                       TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
                       //Pólizas de Seguro.
                       if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
                         rep.comRemplaza("[pg]","");
                         rep.comRemplaza("[re]","");
                         //Responsabilidad Civil.
                         if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
                           rep.comRemplaza("[in]","X");
                           rep.comRemplaza("[rc]"," ");
                         }
                         //Instalaciones Portuarias.
                         if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
                           rep.comRemplaza("[in]"," ");
                           rep.comRemplaza("[rc]","X");
                         }
                         rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
                         rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
                         rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
                         rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
                         rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
                         rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
                         rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
                       }

                       //Polizas de Fianzas
                       if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
                         rep.comRemplaza("[pf]","X");
                         rep.comRemplaza("[ed]","X");
                         rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
                         rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
                         rep.comRemplaza("[ImpEndoso]","");
                         rep.comRemplaza("[ActEndoso]","");
                         rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
                         rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
                         rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
                         rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
                       }
                     }
                   }

                 } else {
                   //Sin Valores en la Base.



                 }

                 //return ;
                 // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
                 Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                                        Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                                        iCveOficinaDest,iCveDeptoDest,
                                        0,0,0,
                                        "",cAsunto,
                                        "", "",
                                        true,"cCuerpo",vcCuerpo,
                                        true, vcCopiasPara,
                                        rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

                 TVDinRep vDinRep = new TVDinRep();
                 vDinRep.put("iEjercicio",iEjercicio);
                 vDinRep.put("iMovProcedimiento",iMovProcedimiento);
                 vDinRep.put("iEjercicioFol",iEjercicioOficio);
                 vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
                 vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
                 vDinRep.put("cDigitosFolio",cDigitosFolio);
                 vDinRep.put("iConsecutivo",iConsecutivo);
                 vDinRep.put("lEsProrroga",0);
                 vDinRep.put("lEsAlegato",0);
                 vDinRep.put("lEsSinEfecto",0);

                try{
                  vDinRep = dCYSFolioProc.insert(vDinRep,null);
                } catch(Exception ex){
                  ex.printStackTrace();
                }

                return vRetorno;

  }

  public Vector genReversionPM(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
    Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
    Vector vRegs = new Vector();
    Vector vRegsTituloProp = new Vector();
    Vector vRegsTituloDocumentos = new Vector();
    Vector vRegsTitPoliza = new Vector();
    Vector vRegsSerPor = new Vector();
    Vector vRegsSerCon = new Vector();
    TWord rep = new TWord();
    TFechas Fechas = new TFechas("44");
    TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

    int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
    String cNombreAPI="",cNomCesionario="",cNumTitulo="",
           cAsunto="Se solicita su intervención en la diligencia de aseguramiento de Area.";
    String cTipoContrato="";
    String[] aFiltros = cQuery.split(",");
    int iCveTitulo = Integer.parseInt(aFiltros[0]);
    int iEjercicio = Integer.parseInt(aFiltros[1]);
    int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
    //int iMovFolioProc = Integer.parseInt(aFiltros[3]);
    //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
    //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

    this.setDatosFolio(cNumFolio);



    // Query de Consulta del Reporte.
    try{
      vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                      "CPATitulo.iCveTipoTitulo, " +
                                      "CPATitulo.dtIniVigenciaTitulo, " +
                                      "CPATitulo.dtVigenciaTitulo, " +
                                      "CPATituloUbicacion.lDentroRecintoPort, " +
                                      "CPATitulo.cZonaFedTotalTerrestre, " +
                                      "CPATitulo.cZonaFedTotalAgua, " +
                                      "CPATitulo.iPropiedad, " +
                                      "CPATitulo.iCveClasificacion " +
                                      "from CPATitulo " +
                                      "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                      "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

    }catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      ex2.printStackTrace();
    }

    //Infomacion del Titulo de Propiedad
    try{ vRegsTituloProp = super.FindByGeneric("",
                         " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                         " Notario.cApPaterno, "+
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
                         " where CPATitulo.iCveTitulo = " + iCveTitulo,
                         dataSourceName);
    } catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
    }catch(Exception ex2){
       ex2.printStackTrace();
       cMensaje += ex2.getMessage();
    }



    // Elaboración del Reporte.
    rep.iniciaReporte();
    if (vRegs.size() > 0){
      TVDinRep vDatos = (TVDinRep) vRegs.get(0);

      //Identificación que se trata de Concesión.
      if (vDatos.getInt("iCveTipoTiulo") == 1){
        //Concesion de Puerto.
        if(vDatos.getInt("iCveClasificacion") == 1){
          rep.comRemplaza("[cp]","X");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Concesion de Terminal.
        if(vDatos.getInt("iCveClasificacion") == 2){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]","X");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Concesion de Marina.
        if(vDatos.getInt("iCveClasificacion") == 3){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]","X");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Concesion de Instalación.
        if(vDatos.getInt("iCveClasificacion") == 4){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]","X");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
      }
      //Identificación que se trata de Permiso.
      if (vDatos.getInt("iCveTipoTiulo") == 2){
        //Permiso de Embarcadero.
        if(vDatos.getInt("iCveClasificacion") == 5){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]","X");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Atracadero.
        if(vDatos.getInt("iCveClasificacion") == 6){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]","X");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Botadero.
        if(vDatos.getInt("iCveClasificacion") == 7){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]","X");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Similar.
        if(vDatos.getInt("iCveClasificacion") == 8){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]","X");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Servicio Portuario.
        if(vDatos.getInt("iCveClasificacion") == 9){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]","X");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Permiso de Servicio Conexo.
        if(vDatos.getInt("iCveClasificacion") == 10){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]","X");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
      }

      //Identificación que se trata de Autorizacion.
      if (vDatos.getInt("iCveTipoTiulo") == 3){
        //Autorización de Obra Marítima.
        if(vDatos.getInt("iCveClasificacion") == 11){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]","X");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Autorización de Dragado de Construcción.
        if(vDatos.getInt("iCveClasificacion") == 12){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]","X");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]"," ");
        }
        //Autorización de Dragado de Mantenimiento.
        if(vDatos.getInt("iCveClasificacion") == 13){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]","X");
          rep.comRemplaza("[aa]"," ");
        }
        //Autorización de Dragado de Mantenimiento.
        if(vDatos.getInt("iCveClasificacion") == 14){
          rep.comRemplaza("[cp]"," ");
          rep.comRemplaza("[ct]"," ");
          rep.comRemplaza("[cn]"," ");
          rep.comRemplaza("[ci]"," ");
          rep.comRemplaza("[cv]"," ");
          rep.comRemplaza("[pe]"," ");
          rep.comRemplaza("[pa]"," ");
          rep.comRemplaza("[pb]"," ");
          rep.comRemplaza("[ps]"," ");
          rep.comRemplaza("[pp]"," ");
          rep.comRemplaza("[pc]"," ");
          rep.comRemplaza("[ao]"," ");
          rep.comRemplaza("[ad]"," ");
          rep.comRemplaza("[am]"," ");
          rep.comRemplaza("[aa]","X");
        }
      }

      rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
      rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
      rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
      rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

      //Fuera de Recinto Portuario
      if (vDatos.getInt("lDentroRecintoPort") == 0){
        rep.comRemplaza("[ir]"," ");
        rep.comRemplaza("[if]","X");
        rep.comRemplaza("[it]","X");
        rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
        rep.comRemplaza("[ia]","X");
        //Maritima
        rep.comRemplaza("[im]","X");
        rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
        //Fluvial
        rep.comRemplaza("[iu]","");
        rep.comRemplaza("[flu]","");
        //Lacustre
        rep.comRemplaza("[il]","");
        rep.comRemplaza("[lac]","");
        //Total
        rep.comRemplaza("[tot]","");
      }
      //Dentro de Recinto Portuario.
      if (vDatos.getInt("lDentroRecintoPort") == 1){
        rep.comRemplaza("[ir]","X");
        rep.comRemplaza("[if]"," ");
        rep.comRemplaza("[it]"," ");
        rep.comRemplaza("[cTerrestre]","");
        rep.comRemplaza("[ia]"," ");
        //Maritima
        rep.comRemplaza("[im]"," ");
        rep.comRemplaza("[mar]"," ");
        //Fluvial
        rep.comRemplaza("[iu]","");
        rep.comRemplaza("[flu]","");
        //Lacustre
        rep.comRemplaza("[il]","");
        rep.comRemplaza("[lac]","");
        //Total
        rep.comRemplaza("[tot]","");
      }

      //Propiedad Nacional.
      if (vDatos.getInt("[iPropiedad]") == 0){
        rep.comRemplaza("[pn]","X");
        rep.comRemplaza("[pr]"," ");
      }
      //Propiedad Particular.
      if (vDatos.getInt("[iPropiedad]") == 1){
        rep.comRemplaza("[pn]"," ");
        rep.comRemplaza("[pr]","X");
      }

      //Frente a la Zona Federal Marítimo Terrestre.
      if (vRegsTituloProp.size()>0){
        TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
        String cZonaFederal = "";
        if (vDatosTitProp.get("cLotePropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
        if (vDatosTitProp.get("cSeccionPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
        if (vDatosTitProp.get("cManzanaPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
        if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
        if (vDatosTitProp.get("cCallePropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
        if (vDatosTitProp.get("cKmPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
        if (vDatosTitProp.get("cColoniaPropiedad")!= null)
          cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
        if (vDatosTitProp.get("cDscMunicipio")!= null)
          cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
        if (vDatosTitProp.get("cDscEntidadFed")!= null)
          cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
        if (vDatosTitProp.get("cDscPuerto")!= null)
          cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
        rep.comRemplaza("[cFrenteZona]",cZonaFederal);
      }

      if (vRegsTituloDocumentos.size()>0){
        for(int i=0;i<vRegsTituloDocumentos.size();i++){
          TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
          switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

            //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
            case 1:case 2:{
              rep.comRemplaza("[tc]","X");
              rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
              rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
              rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              break;
            }

            //Autorizacion en Materia de Impacto Ambiental.
            case 5:{
              rep.comRemplaza("[ai]","X");
              rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
              rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
              rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
              break;
            }
          }
        }
      }

      if (vRegsTitPoliza.size()>0){
        for(int i=0;i<vRegsTitPoliza.size();i++){
          TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
          //Pólizas de Seguro.
          if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
            rep.comRemplaza("[pg]","");
            rep.comRemplaza("[re]","");
            //Responsabilidad Civil.
            if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
              rep.comRemplaza("[in]","X");
              rep.comRemplaza("[rc]"," ");
            }
            //Instalaciones Portuarias.
            if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
              rep.comRemplaza("[in]"," ");
              rep.comRemplaza("[rc]","X");
            }
            rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
            rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
            rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
            rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
            rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
            rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
            rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
          }

          //Polizas de Fianzas
          if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
            rep.comRemplaza("[pf]","X");
            rep.comRemplaza("[ed]","X");
            rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
            rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
            rep.comRemplaza("[ImpEndoso]","");
            rep.comRemplaza("[ActEndoso]","");
            rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
            rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
            rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
            rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
          }
        }
      }

    } else {
      //Sin Valores en la Base.



    }

    //return ;
    // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
    Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                           Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                           iCveOficinaDest,iCveDeptoDest,
                           0,0,0,
                           "",cAsunto,
                           "", "",
                           true,"cCuerpo",vcCuerpo,
                           true, vcCopiasPara,
                           rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

    TVDinRep vDinRep = new TVDinRep();
    vDinRep.put("iEjercicio",iEjercicio);
    vDinRep.put("iMovProcedimiento",iMovProcedimiento);
    vDinRep.put("iEjercicioFol",iEjercicioOficio);
    vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
    vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
    vDinRep.put("cDigitosFolio",cDigitosFolio);
    vDinRep.put("iConsecutivo",iConsecutivo);
    vDinRep.put("lEsProrroga",0);
    vDinRep.put("lEsAlegato",0);
    vDinRep.put("lEsSinEfecto",0);

   try{
     vDinRep = dCYSFolioProc.insert(vDinRep,null);
   } catch(Exception ex){
     ex.printStackTrace();
   }

   return vRetorno;

  }

  public Vector genReversionSecSegPubFed(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     Vector vRegsTituloProp = new Vector();
     Vector vRegsTituloDocumentos = new Vector();
     Vector vRegsTitPoliza = new Vector();
     Vector vRegsSerPor = new Vector();
     Vector vRegsSerCon = new Vector();
     TWord rep = new TWord();
     TFechas Fechas = new TFechas("44");
     TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

     int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
     String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Reversión dirigida a la Secretaria de Seguridad Publica Federal";
     String cTipoContrato="";
     String[] aFiltros = cQuery.split(",");
     int iCveTitulo = Integer.parseInt(aFiltros[0]);
     int iEjercicio = Integer.parseInt(aFiltros[1]);
     int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
     //int iMovFolioProc = Integer.parseInt(aFiltros[3]);
     //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
     //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

     this.setDatosFolio(cNumFolio);



     // Query de Consulta del Reporte.
     try{
       vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                       "CPATitulo.iCveTipoTitulo, " +
                                       "CPATitulo.dtIniVigenciaTitulo, " +
                                       "CPATitulo.dtVigenciaTitulo, " +
                                       "CPATituloUbicacion.lDentroRecintoPort, " +
                                       "CPATitulo.cZonaFedTotalTerrestre, " +
                                       "CPATitulo.cZonaFedTotalAgua, " +
                                       "CPATitulo.iPropiedad, " +
                                       "CPATitulo.iCveClasificacion " +
                                       "from CPATitulo " +
                                       "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                       "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     //Infomacion del Titulo de Propiedad
     try{ vRegsTituloProp = super.FindByGeneric("",
                          " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                          " Notario.cApPaterno, "+
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
                          " where CPATitulo.iCveTitulo = " + iCveTitulo,
                          dataSourceName);
     } catch(SQLException ex){
        ex.printStackTrace();
        cMensaje = ex.getMessage();
     }catch(Exception ex2){
        ex2.printStackTrace();
        cMensaje += ex2.getMessage();
     }



     // Elaboración del Reporte.
     rep.iniciaReporte();
     if (vRegs.size() > 0){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);

       //Identificación que se trata de Concesión.
       if (vDatos.getInt("iCveTipoTiulo") == 1){
         //Concesion de Puerto.
         if(vDatos.getInt("iCveClasificacion") == 1){
           rep.comRemplaza("[cp]","X");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Terminal.
         if(vDatos.getInt("iCveClasificacion") == 2){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]","X");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Marina.
         if(vDatos.getInt("iCveClasificacion") == 3){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]","X");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Instalación.
         if(vDatos.getInt("iCveClasificacion") == 4){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]","X");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }
       //Identificación que se trata de Permiso.
       if (vDatos.getInt("iCveTipoTiulo") == 2){
         //Permiso de Embarcadero.
         if(vDatos.getInt("iCveClasificacion") == 5){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]","X");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Atracadero.
         if(vDatos.getInt("iCveClasificacion") == 6){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]","X");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Botadero.
         if(vDatos.getInt("iCveClasificacion") == 7){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]","X");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Similar.
         if(vDatos.getInt("iCveClasificacion") == 8){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]","X");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Portuario.
         if(vDatos.getInt("iCveClasificacion") == 9){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]","X");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Conexo.
         if(vDatos.getInt("iCveClasificacion") == 10){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]","X");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }

       //Identificación que se trata de Autorizacion.
       if (vDatos.getInt("iCveTipoTiulo") == 3){
         //Autorización de Obra Marítima.
         if(vDatos.getInt("iCveClasificacion") == 11){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]","X");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Construcción.
         if(vDatos.getInt("iCveClasificacion") == 12){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]","X");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 13){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]","X");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 14){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]","X");
         }
       }

       rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
       rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

       //Fuera de Recinto Portuario
       if (vDatos.getInt("lDentroRecintoPort") == 0){
         rep.comRemplaza("[ir]"," ");
         rep.comRemplaza("[if]","X");
         rep.comRemplaza("[it]","X");
         rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
         rep.comRemplaza("[ia]","X");
         //Maritima
         rep.comRemplaza("[im]","X");
         rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }
       //Dentro de Recinto Portuario.
       if (vDatos.getInt("lDentroRecintoPort") == 1){
         rep.comRemplaza("[ir]","X");
         rep.comRemplaza("[if]"," ");
         rep.comRemplaza("[it]"," ");
         rep.comRemplaza("[cTerrestre]","");
         rep.comRemplaza("[ia]"," ");
         //Maritima
         rep.comRemplaza("[im]"," ");
         rep.comRemplaza("[mar]"," ");
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }

       //Propiedad Nacional.
       if (vDatos.getInt("[iPropiedad]") == 0){
         rep.comRemplaza("[pn]","X");
         rep.comRemplaza("[pr]"," ");
       }
       //Propiedad Particular.
       if (vDatos.getInt("[iPropiedad]") == 1){
         rep.comRemplaza("[pn]"," ");
         rep.comRemplaza("[pr]","X");
       }

       //Frente a la Zona Federal Marítimo Terrestre.
       if (vRegsTituloProp.size()>0){
         TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
         String cZonaFederal = "";
         if (vDatosTitProp.get("cLotePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
         if (vDatosTitProp.get("cSeccionPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
         if (vDatosTitProp.get("cManzanaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
         if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
         if (vDatosTitProp.get("cCallePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
         if (vDatosTitProp.get("cKmPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
         if (vDatosTitProp.get("cColoniaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
         if (vDatosTitProp.get("cDscMunicipio")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
         if (vDatosTitProp.get("cDscEntidadFed")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
         if (vDatosTitProp.get("cDscPuerto")!= null)
           cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
         rep.comRemplaza("[cFrenteZona]",cZonaFederal);
       }

       if (vRegsTituloDocumentos.size()>0){
         for(int i=0;i<vRegsTituloDocumentos.size();i++){
           TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
           switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

             //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
             case 1:case 2:{
               rep.comRemplaza("[tc]","X");
               rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }

             //Autorizacion en Materia de Impacto Ambiental.
             case 5:{
               rep.comRemplaza("[ai]","X");
               rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }
           }
         }
       }

       if (vRegsTitPoliza.size()>0){
         for(int i=0;i<vRegsTitPoliza.size();i++){
           TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
           //Pólizas de Seguro.
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
             rep.comRemplaza("[pg]","");
             rep.comRemplaza("[re]","");
             //Responsabilidad Civil.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
               rep.comRemplaza("[in]","X");
               rep.comRemplaza("[rc]"," ");
             }
             //Instalaciones Portuarias.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
               rep.comRemplaza("[in]"," ");
               rep.comRemplaza("[rc]","X");
             }
             rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
             rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
           }

           //Polizas de Fianzas
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
             rep.comRemplaza("[pf]","X");
             rep.comRemplaza("[ed]","X");
             rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[ImpEndoso]","");
             rep.comRemplaza("[ActEndoso]","");
             rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
           }
         }
       }

     } else {
       //Sin Valores en la Base.



     }

     //return ;
     // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                            Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                            iCveOficinaDest,iCveDeptoDest,
                            0,0,0,
                            "",cAsunto,
                            "", "",
                            true,"cCuerpo",vcCuerpo,
                            true, vcCopiasPara,
                            rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

     TVDinRep vDinRep = new TVDinRep();
     vDinRep.put("iEjercicio",iEjercicio);
     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
     vDinRep.put("iEjercicioFol",iEjercicioOficio);
     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
     vDinRep.put("cDigitosFolio",cDigitosFolio);
     vDinRep.put("iConsecutivo",iConsecutivo);
     vDinRep.put("lEsProrroga",0);
     vDinRep.put("lEsAlegato",0);
     vDinRep.put("lEsSinEfecto",0);

    try{
      vDinRep = dCYSFolioProc.insert(vDinRep,null);
    } catch(Exception ex){
      ex.printStackTrace();
    }

    return vRetorno;

   }

   public Vector genReversionSEGOB(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     Vector vRegsTituloProp = new Vector();
     Vector vRegsTituloDocumentos = new Vector();
     Vector vRegsTitPoliza = new Vector();
     Vector vRegsSerPor = new Vector();
     Vector vRegsSerCon = new Vector();
     TWord rep = new TWord();
     TFechas Fechas = new TFechas("44");
     TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

     int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
     String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Reversión dirigida a la Secretaria de Gobernación";
     String cTipoContrato="";
     String[] aFiltros = cQuery.split(",");
     int iCveTitulo = Integer.parseInt(aFiltros[0]);
     int iEjercicio = Integer.parseInt(aFiltros[1]);
     int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
     //int iMovFolioProc = Integer.parseInt(aFiltros[3]);
     //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
     //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

     this.setDatosFolio(cNumFolio);



     // Query de Consulta del Reporte.
     try{
       vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                       "CPATitulo.iCveTipoTitulo, " +
                                       "CPATitulo.dtIniVigenciaTitulo, " +
                                       "CPATitulo.dtVigenciaTitulo, " +
                                       "CPATituloUbicacion.lDentroRecintoPort, " +
                                       "CPATitulo.cZonaFedTotalTerrestre, " +
                                       "CPATitulo.cZonaFedTotalAgua, " +
                                       "CPATitulo.iPropiedad, " +
                                       "CPATitulo.iCveClasificacion " +
                                       "from CPATitulo " +
                                       "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                       "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     //Infomacion del Titulo de Propiedad
     try{ vRegsTituloProp = super.FindByGeneric("",
                          " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                          " Notario.cApPaterno, "+
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
                          " where CPATitulo.iCveTitulo = " + iCveTitulo,
                          dataSourceName);
     } catch(SQLException ex){
        ex.printStackTrace();
        cMensaje = ex.getMessage();
     }catch(Exception ex2){
        ex2.printStackTrace();
        cMensaje += ex2.getMessage();
     }



     // Elaboración del Reporte.
     rep.iniciaReporte();
     if (vRegs.size() > 0){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);

       //Identificación que se trata de Concesión.
       if (vDatos.getInt("iCveTipoTiulo") == 1){
         //Concesion de Puerto.
         if(vDatos.getInt("iCveClasificacion") == 1){
           rep.comRemplaza("[cp]","X");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Terminal.
         if(vDatos.getInt("iCveClasificacion") == 2){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]","X");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Marina.
         if(vDatos.getInt("iCveClasificacion") == 3){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]","X");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Instalación.
         if(vDatos.getInt("iCveClasificacion") == 4){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]","X");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }
       //Identificación que se trata de Permiso.
       if (vDatos.getInt("iCveTipoTiulo") == 2){
         //Permiso de Embarcadero.
         if(vDatos.getInt("iCveClasificacion") == 5){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]","X");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Atracadero.
         if(vDatos.getInt("iCveClasificacion") == 6){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]","X");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Botadero.
         if(vDatos.getInt("iCveClasificacion") == 7){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]","X");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Similar.
         if(vDatos.getInt("iCveClasificacion") == 8){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]","X");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Portuario.
         if(vDatos.getInt("iCveClasificacion") == 9){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]","X");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Conexo.
         if(vDatos.getInt("iCveClasificacion") == 10){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]","X");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }

       //Identificación que se trata de Autorizacion.
       if (vDatos.getInt("iCveTipoTiulo") == 3){
         //Autorización de Obra Marítima.
         if(vDatos.getInt("iCveClasificacion") == 11){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]","X");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Construcción.
         if(vDatos.getInt("iCveClasificacion") == 12){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]","X");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 13){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]","X");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 14){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]","X");
         }
       }

       rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
       rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

       //Fuera de Recinto Portuario
       if (vDatos.getInt("lDentroRecintoPort") == 0){
         rep.comRemplaza("[ir]"," ");
         rep.comRemplaza("[if]","X");
         rep.comRemplaza("[it]","X");
         rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
         rep.comRemplaza("[ia]","X");
         //Maritima
         rep.comRemplaza("[im]","X");
         rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }
       //Dentro de Recinto Portuario.
       if (vDatos.getInt("lDentroRecintoPort") == 1){
         rep.comRemplaza("[ir]","X");
         rep.comRemplaza("[if]"," ");
         rep.comRemplaza("[it]"," ");
         rep.comRemplaza("[cTerrestre]","");
         rep.comRemplaza("[ia]"," ");
         //Maritima
         rep.comRemplaza("[im]"," ");
         rep.comRemplaza("[mar]"," ");
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }

       //Propiedad Nacional.
       if (vDatos.getInt("[iPropiedad]") == 0){
         rep.comRemplaza("[pn]","X");
         rep.comRemplaza("[pr]"," ");
       }
       //Propiedad Particular.
       if (vDatos.getInt("[iPropiedad]") == 1){
         rep.comRemplaza("[pn]"," ");
         rep.comRemplaza("[pr]","X");
       }

       //Frente a la Zona Federal Marítimo Terrestre.
       if (vRegsTituloProp.size()>0){
         TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
         String cZonaFederal = "";
         if (vDatosTitProp.get("cLotePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
         if (vDatosTitProp.get("cSeccionPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
         if (vDatosTitProp.get("cManzanaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
         if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
         if (vDatosTitProp.get("cCallePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
         if (vDatosTitProp.get("cKmPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
         if (vDatosTitProp.get("cColoniaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
         if (vDatosTitProp.get("cDscMunicipio")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
         if (vDatosTitProp.get("cDscEntidadFed")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
         if (vDatosTitProp.get("cDscPuerto")!= null)
           cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
         rep.comRemplaza("[cFrenteZona]",cZonaFederal);
       }

       if (vRegsTituloDocumentos.size()>0){
         for(int i=0;i<vRegsTituloDocumentos.size();i++){
           TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
           switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

             //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
             case 1:case 2:{
               rep.comRemplaza("[tc]","X");
               rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }

             //Autorizacion en Materia de Impacto Ambiental.
             case 5:{
               rep.comRemplaza("[ai]","X");
               rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }
           }
         }
       }

       if (vRegsTitPoliza.size()>0){
         for(int i=0;i<vRegsTitPoliza.size();i++){
           TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
           //Pólizas de Seguro.
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
             rep.comRemplaza("[pg]","");
             rep.comRemplaza("[re]","");
             //Responsabilidad Civil.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
               rep.comRemplaza("[in]","X");
               rep.comRemplaza("[rc]"," ");
             }
             //Instalaciones Portuarias.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
               rep.comRemplaza("[in]"," ");
               rep.comRemplaza("[rc]","X");
             }
             rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
             rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
           }

           //Polizas de Fianzas
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
             rep.comRemplaza("[pf]","X");
             rep.comRemplaza("[ed]","X");
             rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[ImpEndoso]","");
             rep.comRemplaza("[ActEndoso]","");
             rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
           }
         }
       }

     } else {
       //Sin Valores en la Base.



     }

     //return ;
     // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                            Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                            iCveOficinaDest,iCveDeptoDest,
                            0,0,0,
                            "",cAsunto,
                            "", "",
                            true,"cCuerpo",vcCuerpo,
                            true, vcCopiasPara,
                            rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

     TVDinRep vDinRep = new TVDinRep();
     vDinRep.put("iEjercicio",iEjercicio);
     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
     vDinRep.put("iEjercicioFol",iEjercicioOficio);
     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
     vDinRep.put("cDigitosFolio",cDigitosFolio);
     vDinRep.put("iConsecutivo",iConsecutivo);
     vDinRep.put("lEsProrroga",0);
     vDinRep.put("lEsAlegato",0);
     vDinRep.put("lEsSinEfecto",0);

    try{
      vDinRep = dCYSFolioProc.insert(vDinRep,null);
    } catch(Exception ex){
      ex.printStackTrace();
    }

    return vRetorno;

   }

   public Vector genReversionPGR(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     Vector vRegsTituloProp = new Vector();
     Vector vRegsTituloDocumentos = new Vector();
     Vector vRegsTitPoliza = new Vector();
     Vector vRegsSerPor = new Vector();
     Vector vRegsSerCon = new Vector();
     TWord rep = new TWord();
     TFechas Fechas = new TFechas("44");
     TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

     int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
     String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Reversión dirigida a la Procuraduria General de la Republica";
     String cTipoContrato="";
     String[] aFiltros = cQuery.split(",");
     int iCveTitulo = Integer.parseInt(aFiltros[0]);
     int iEjercicio = Integer.parseInt(aFiltros[1]);
     int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
     //int iMovFolioProc = Integer.parseInt(aFiltros[3]);
     //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
     //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

     this.setDatosFolio(cNumFolio);



     // Query de Consulta del Reporte.
     try{
       vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                       "CPATitulo.iCveTipoTitulo, " +
                                       "CPATitulo.dtIniVigenciaTitulo, " +
                                       "CPATitulo.dtVigenciaTitulo, " +
                                       "CPATituloUbicacion.lDentroRecintoPort, " +
                                       "CPATitulo.cZonaFedTotalTerrestre, " +
                                       "CPATitulo.cZonaFedTotalAgua, " +
                                       "CPATitulo.iPropiedad, " +
                                       "CPATitulo.iCveClasificacion " +
                                       "from CPATitulo " +
                                       "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                       "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     //Infomacion del Titulo de Propiedad
     try{ vRegsTituloProp = super.FindByGeneric("",
                          " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                          " Notario.cApPaterno, "+
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
                          " where CPATitulo.iCveTitulo = " + iCveTitulo,
                          dataSourceName);
     } catch(SQLException ex){
        ex.printStackTrace();
        cMensaje = ex.getMessage();
     }catch(Exception ex2){
        ex2.printStackTrace();
        cMensaje += ex2.getMessage();
     }



     // Elaboración del Reporte.
     rep.iniciaReporte();
     if (vRegs.size() > 0){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);

       //Identificación que se trata de Concesión.
       if (vDatos.getInt("iCveTipoTiulo") == 1){
         //Concesion de Puerto.
         if(vDatos.getInt("iCveClasificacion") == 1){
           rep.comRemplaza("[cp]","X");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Terminal.
         if(vDatos.getInt("iCveClasificacion") == 2){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]","X");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Marina.
         if(vDatos.getInt("iCveClasificacion") == 3){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]","X");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Instalación.
         if(vDatos.getInt("iCveClasificacion") == 4){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]","X");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }
       //Identificación que se trata de Permiso.
       if (vDatos.getInt("iCveTipoTiulo") == 2){
         //Permiso de Embarcadero.
         if(vDatos.getInt("iCveClasificacion") == 5){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]","X");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Atracadero.
         if(vDatos.getInt("iCveClasificacion") == 6){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]","X");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Botadero.
         if(vDatos.getInt("iCveClasificacion") == 7){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]","X");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Similar.
         if(vDatos.getInt("iCveClasificacion") == 8){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]","X");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Portuario.
         if(vDatos.getInt("iCveClasificacion") == 9){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]","X");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Conexo.
         if(vDatos.getInt("iCveClasificacion") == 10){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]","X");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }

       //Identificación que se trata de Autorizacion.
       if (vDatos.getInt("iCveTipoTiulo") == 3){
         //Autorización de Obra Marítima.
         if(vDatos.getInt("iCveClasificacion") == 11){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]","X");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Construcción.
         if(vDatos.getInt("iCveClasificacion") == 12){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]","X");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 13){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]","X");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 14){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]","X");
         }
       }

       rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
       rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

       //Fuera de Recinto Portuario
       if (vDatos.getInt("lDentroRecintoPort") == 0){
         rep.comRemplaza("[ir]"," ");
         rep.comRemplaza("[if]","X");
         rep.comRemplaza("[it]","X");
         rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
         rep.comRemplaza("[ia]","X");
         //Maritima
         rep.comRemplaza("[im]","X");
         rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }
       //Dentro de Recinto Portuario.
       if (vDatos.getInt("lDentroRecintoPort") == 1){
         rep.comRemplaza("[ir]","X");
         rep.comRemplaza("[if]"," ");
         rep.comRemplaza("[it]"," ");
         rep.comRemplaza("[cTerrestre]","");
         rep.comRemplaza("[ia]"," ");
         //Maritima
         rep.comRemplaza("[im]"," ");
         rep.comRemplaza("[mar]"," ");
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }

       //Propiedad Nacional.
       if (vDatos.getInt("[iPropiedad]") == 0){
         rep.comRemplaza("[pn]","X");
         rep.comRemplaza("[pr]"," ");
       }
       //Propiedad Particular.
       if (vDatos.getInt("[iPropiedad]") == 1){
         rep.comRemplaza("[pn]"," ");
         rep.comRemplaza("[pr]","X");
       }

       //Frente a la Zona Federal Marítimo Terrestre.
       if (vRegsTituloProp.size()>0){
         TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
         String cZonaFederal = "";
         if (vDatosTitProp.get("cLotePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
         if (vDatosTitProp.get("cSeccionPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
         if (vDatosTitProp.get("cManzanaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
         if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
         if (vDatosTitProp.get("cCallePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
         if (vDatosTitProp.get("cKmPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
         if (vDatosTitProp.get("cColoniaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
         if (vDatosTitProp.get("cDscMunicipio")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
         if (vDatosTitProp.get("cDscEntidadFed")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
         if (vDatosTitProp.get("cDscPuerto")!= null)
           cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
         rep.comRemplaza("[cFrenteZona]",cZonaFederal);
       }

       if (vRegsTituloDocumentos.size()>0){
         for(int i=0;i<vRegsTituloDocumentos.size();i++){
           TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
           switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

             //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
             case 1:case 2:{
               rep.comRemplaza("[tc]","X");
               rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }

             //Autorizacion en Materia de Impacto Ambiental.
             case 5:{
               rep.comRemplaza("[ai]","X");
               rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }
           }
         }
       }

       if (vRegsTitPoliza.size()>0){
         for(int i=0;i<vRegsTitPoliza.size();i++){
           TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
           //Pólizas de Seguro.
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
             rep.comRemplaza("[pg]","");
             rep.comRemplaza("[re]","");
             //Responsabilidad Civil.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
               rep.comRemplaza("[in]","X");
               rep.comRemplaza("[rc]"," ");
             }
             //Instalaciones Portuarias.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
               rep.comRemplaza("[in]"," ");
               rep.comRemplaza("[rc]","X");
             }
             rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
             rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
           }

           //Polizas de Fianzas
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
             rep.comRemplaza("[pf]","X");
             rep.comRemplaza("[ed]","X");
             rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[ImpEndoso]","");
             rep.comRemplaza("[ActEndoso]","");
             rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
           }
         }
       }

     } else {
       //Sin Valores en la Base.



     }

     //return ;
     // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                            Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                            iCveOficinaDest,iCveDeptoDest,
                            0,0,0,
                            "",cAsunto,
                            "", "",
                            true,"cCuerpo",vcCuerpo,
                            true, vcCopiasPara,
                            rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

     TVDinRep vDinRep = new TVDinRep();
     vDinRep.put("iEjercicio",iEjercicio);
     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
     vDinRep.put("iEjercicioFol",iEjercicioOficio);
     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
     vDinRep.put("cDigitosFolio",cDigitosFolio);
     vDinRep.put("iConsecutivo",iConsecutivo);
     vDinRep.put("lEsProrroga",0);
     vDinRep.put("lEsAlegato",0);
     vDinRep.put("lEsSinEfecto",0);

    try{
      vDinRep = dCYSFolioProc.insert(vDinRep,null);
    } catch(Exception ex){
      ex.printStackTrace();
    }

    return vRetorno;

   }

   public Vector genReversionMARINA(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     Vector vRegsTituloProp = new Vector();
     Vector vRegsTituloDocumentos = new Vector();
     Vector vRegsTitPoliza = new Vector();
     Vector vRegsSerPor = new Vector();
     Vector vRegsSerCon = new Vector();
     TWord rep = new TWord();
     TFechas Fechas = new TFechas("44");
     TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

     int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
     String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Se solicita su intervención en la diligencia de reversión";
     String cTipoContrato="";
     String[] aFiltros = cQuery.split(",");
     int iCveTitulo = Integer.parseInt(aFiltros[0]);
     int iEjercicio = Integer.parseInt(aFiltros[1]);
     int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
     //int iMovFolioProc = Integer.parseInt(aFiltros[3]);
     //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
     //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

     this.setDatosFolio(cNumFolio);



     // Query de Consulta del Reporte.
     try{
       vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                       "CPATitulo.iCveTipoTitulo, " +
                                       "CPATitulo.dtIniVigenciaTitulo, " +
                                       "CPATitulo.dtVigenciaTitulo, " +
                                       "CPATituloUbicacion.lDentroRecintoPort, " +
                                       "CPATitulo.cZonaFedTotalTerrestre, " +
                                       "CPATitulo.cZonaFedTotalAgua, " +
                                       "CPATitulo.iPropiedad, " +
                                       "CPATitulo.iCveClasificacion " +
                                       "from CPATitulo " +
                                       "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                       "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     //Infomacion del Titulo de Propiedad
     try{ vRegsTituloProp = super.FindByGeneric("",
                          " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                          " Notario.cApPaterno, "+
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
                          " where CPATitulo.iCveTitulo = " + iCveTitulo,
                          dataSourceName);
     } catch(SQLException ex){
        ex.printStackTrace();
        cMensaje = ex.getMessage();
     }catch(Exception ex2){
        ex2.printStackTrace();
        cMensaje += ex2.getMessage();
     }



     // Elaboración del Reporte.
     rep.iniciaReporte();
     if (vRegs.size() > 0){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);

       //Identificación que se trata de Concesión.
       if (vDatos.getInt("iCveTipoTiulo") == 1){
         //Concesion de Puerto.
         if(vDatos.getInt("iCveClasificacion") == 1){
           rep.comRemplaza("[cp]","X");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Terminal.
         if(vDatos.getInt("iCveClasificacion") == 2){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]","X");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Marina.
         if(vDatos.getInt("iCveClasificacion") == 3){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]","X");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Instalación.
         if(vDatos.getInt("iCveClasificacion") == 4){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]","X");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }
       //Identificación que se trata de Permiso.
       if (vDatos.getInt("iCveTipoTiulo") == 2){
         //Permiso de Embarcadero.
         if(vDatos.getInt("iCveClasificacion") == 5){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]","X");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Atracadero.
         if(vDatos.getInt("iCveClasificacion") == 6){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]","X");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Botadero.
         if(vDatos.getInt("iCveClasificacion") == 7){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]","X");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Similar.
         if(vDatos.getInt("iCveClasificacion") == 8){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]","X");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Portuario.
         if(vDatos.getInt("iCveClasificacion") == 9){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]","X");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Conexo.
         if(vDatos.getInt("iCveClasificacion") == 10){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]","X");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }

       //Identificación que se trata de Autorizacion.
       if (vDatos.getInt("iCveTipoTiulo") == 3){
         //Autorización de Obra Marítima.
         if(vDatos.getInt("iCveClasificacion") == 11){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]","X");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Construcción.
         if(vDatos.getInt("iCveClasificacion") == 12){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]","X");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 13){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]","X");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 14){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]","X");
         }
       }

       rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
       rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

       //Fuera de Recinto Portuario
       if (vDatos.getInt("lDentroRecintoPort") == 0){
         rep.comRemplaza("[ir]"," ");
         rep.comRemplaza("[if]","X");
         rep.comRemplaza("[it]","X");
         rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
         rep.comRemplaza("[ia]","X");
         //Maritima
         rep.comRemplaza("[im]","X");
         rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }
       //Dentro de Recinto Portuario.
       if (vDatos.getInt("lDentroRecintoPort") == 1){
         rep.comRemplaza("[ir]","X");
         rep.comRemplaza("[if]"," ");
         rep.comRemplaza("[it]"," ");
         rep.comRemplaza("[cTerrestre]","");
         rep.comRemplaza("[ia]"," ");
         //Maritima
         rep.comRemplaza("[im]"," ");
         rep.comRemplaza("[mar]"," ");
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }

       //Propiedad Nacional.
       if (vDatos.getInt("[iPropiedad]") == 0){
         rep.comRemplaza("[pn]","X");
         rep.comRemplaza("[pr]"," ");
       }
       //Propiedad Particular.
       if (vDatos.getInt("[iPropiedad]") == 1){
         rep.comRemplaza("[pn]"," ");
         rep.comRemplaza("[pr]","X");
       }

       //Frente a la Zona Federal Marítimo Terrestre.
       if (vRegsTituloProp.size()>0){
         TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
         String cZonaFederal = "";
         if (vDatosTitProp.get("cLotePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
         if (vDatosTitProp.get("cSeccionPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
         if (vDatosTitProp.get("cManzanaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
         if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
         if (vDatosTitProp.get("cCallePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
         if (vDatosTitProp.get("cKmPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
         if (vDatosTitProp.get("cColoniaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
         if (vDatosTitProp.get("cDscMunicipio")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
         if (vDatosTitProp.get("cDscEntidadFed")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
         if (vDatosTitProp.get("cDscPuerto")!= null)
           cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
         rep.comRemplaza("[cFrenteZona]",cZonaFederal);
       }

       if (vRegsTituloDocumentos.size()>0){
         for(int i=0;i<vRegsTituloDocumentos.size();i++){
           TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
           switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

             //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
             case 1:case 2:{
               rep.comRemplaza("[tc]","X");
               rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }

             //Autorizacion en Materia de Impacto Ambiental.
             case 5:{
               rep.comRemplaza("[ai]","X");
               rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }
           }
         }
       }

       if (vRegsTitPoliza.size()>0){
         for(int i=0;i<vRegsTitPoliza.size();i++){
           TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
           //Pólizas de Seguro.
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
             rep.comRemplaza("[pg]","");
             rep.comRemplaza("[re]","");
             //Responsabilidad Civil.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
               rep.comRemplaza("[in]","X");
               rep.comRemplaza("[rc]"," ");
             }
             //Instalaciones Portuarias.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
               rep.comRemplaza("[in]"," ");
               rep.comRemplaza("[rc]","X");
             }
             rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
             rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
           }

           //Polizas de Fianzas
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
             rep.comRemplaza("[pf]","X");
             rep.comRemplaza("[ed]","X");
             rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[ImpEndoso]","");
             rep.comRemplaza("[ActEndoso]","");
             rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
           }
         }
       }

     } else {
       //Sin Valores en la Base.



     }

     //return ;
     // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                            Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                            iCveOficinaDest,iCveDeptoDest,
                            0,0,0,
                            "",cAsunto,
                            "", "",
                            true,"cCuerpo",vcCuerpo,
                            true, vcCopiasPara,
                            rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

     TVDinRep vDinRep = new TVDinRep();
     vDinRep.put("iEjercicio",iEjercicio);
     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
     vDinRep.put("iEjercicioFol",iEjercicioOficio);
     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
     vDinRep.put("cDigitosFolio",cDigitosFolio);
     vDinRep.put("iConsecutivo",iConsecutivo);
     vDinRep.put("lEsProrroga",0);
     vDinRep.put("lEsAlegato",0);
     vDinRep.put("lEsSinEfecto",0);

    try{
      vDinRep = dCYSFolioProc.insert(vDinRep,null);
    } catch(Exception ex){
      ex.printStackTrace();
    }

    return vRetorno;

   }

   public Vector genReversionComision(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
     TWord rep = new TWord();
     TFechas Fechas = new TFechas("44");
     TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
     TVDinRep vRegDilig = new TVDinRep();
     TDCYSDiligenciaReversion dCYSDilRev = new TDCYSDiligenciaReversion();

     int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0,iAniosDuracion=0;
     String cTitular="", cUsoTitulo="", cFechaNotif="", cPropiedad="";
     String cEntidad="", cTipoTitulo="",cPublicDOF="",cAsunto="Se asigna comision", cComisionados = "";
     String[] aFiltros = cQuery.split(",");
     StringTokenizer st = null;
     int iCveTitulo = Integer.parseInt(aFiltros[0]);
     java.sql.Timestamp tsDiligencia = Fechas.getTimeStampTC(aFiltros[1]);
     Vector vNotifRec = new Vector();
     this.setDatosFolio(cNumFolio);
     try{
       vNotifRec = dSegPRoc.findByOficioNotRec(Integer.parseInt(aFiltros[2]),Integer.parseInt(aFiltros[3]),0);
       vRegDilig = dCYSDilRev.regresaDiligencia(Integer.parseInt(aFiltros[2]),Integer.parseInt(aFiltros[3]));
     }catch(Exception eNotifRec){
       eNotifRec.printStackTrace();
     }
     String cSQL = "select CPATitulo.iCveTitulo, " +
              "GRLPersona.iTipoPersona, " +
              "GRLPersona.cNomRazonSocial, " +
              "GRLPersona.cApPaterno, " +
              "GRLPersona.cApMaterno, " +
              "GRLPersona.cRFC, " +
              "CPAActaNacimiento.cNumActaNacimiento, " +
              "CPAActaNacimiento.dtNacimiento, " +
              "CPAActaNacimiento.cNumJuzgado, " +
              "Juez.cNomRazonSocial cNomJuez, " +
              "Juez.cApPaterno cApPatJuez, " +
              "Juez.cApMaterno cApMatJuez, " +
              "CPAActaNacimiento.cNumLibroActa, " +
              "CPAActaNacimiento.cNumFojaActa, " +
              "GRLDomicilio.cCalle, " +
              "GRLDomicilio.cNumExterior, " +
              "GRLDomicilio.cNumInterior, " +
              "GRLDomicilio.cColonia, " +
              "GRLDomicilio.cCodPostal, " +
              "GRLDomicilio.cTelefono, " +
              "GRLPais.cNombre as cNomPais, " +
              "GRLEntidadFed.cNombre as cNomEntidadFed, " +
              "GRLMunicipio.cNombre as cNomMunicipio, " +
              "GRLLocalidad.cNombre as cNomLocalidad, " +
              "CPATitulo.cNumTitulo, " +
              "CPATitulo.cNumTituloAnterior, " +
              "CPATitulo.dtIniVigenciaTitulo, " +
              "CPATitulo.cZonaFedAfectadaTerrestre, " +
              "CPATitulo.cZonaFedAfectadaAgua, " +
              "CPATitulo.cObjetoTitulo, " +
              "CPATitulo.cMontoInversion, " +
              "CPATitulo.dtPublicacionDOF, " +
              "CPATitulo.iCveClasificacion, " +
              "CPAUsoTitulo.iCveUsoTitulo, " +
              "CPAUsoTitulo.cDscUsoTitulo, " +
              "CPATitulo.iCveTituloAnterior, " +
              "CPATitulo.dtVigenciaTitulo, " +
              "CPATitulo.iCveTipoTitulo, "+
              "CPATitulo.iPropiedad, "+
              "CPATituloUbicacion.cCalleTitulo, " +
              "CPATituloUbicacion.cKm, " +
              "CPATituloUbicacion.cColoniaTitulo, " +
              "entTitUbic.cNombre as cEntidadTitUbic " +
              "from CPATitulo " +
              "join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
              "join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
              "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
              "left join GRLEntidadFed entTitUbic on entTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
              "and entTitUbic.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
              "join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo " +
              "join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
              "left join CPAActaNacimiento on CPAActaNacimiento.iCvePersona = GRLPersona.iCvePersona " +
              "left join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
              "and GRLDomicilio.lPredeterminado = 1 " +
              "left join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
              "left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
              "and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
              "left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
              "and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
              "and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
              "left join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
              "and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
              "and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
              "and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
              "left join GRLPersona Juez on Juez.iCvePersona = CPAActaNacimiento.iCveJuez " +
              "where CPATitulo.iCveTitulo = " + iCveTitulo;


     // Query de Consulta del Reporte.
     try{
       vRegs = super.FindByGeneric("", cSQL, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     // Elaboración del Reporte.
     rep.iniciaReporte();
     if (vRegs.size() > 0){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);

       //Identificación que se trata de Concesión.
       if (vDatos.getInt("iCveTipoTitulo") == 1){
         cTipoTitulo = "la Concesión";
       }
       //Identificación que se trata de Permiso.
       if (vDatos.getInt("iCveTipoTitulo") == 2){
         cTipoTitulo = "el Permiso";
       }

       if(vNotifRec.size()>0){
         TVDinRep vDinNotifRec = new TVDinRep();
         vDinNotifRec = (TVDinRep) vNotifRec.get(0);
         rep.comRemplaza("[cFolio]",vDinNotifRec.getString("cDigitosFolio")+"."+vDinNotifRec.getString("iConsecutivo")+"/"+vDinNotifRec.getString("iEjercicioFol"));
         rep.comRemplaza("[cFechaFolio]",Fechas.getDateSPN(vDinNotifRec.getDate("dtAsignacion")));

         if(vDatos.getDate("dtRecAsignacion")!=null)
           cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtRecAsignacion"));
         else if(vDinNotifRec.getDate("dtNorAsignacion")!=null)
           cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtNorAsignacion"));
         else
           cFechaNotif = "";
         rep.comRemplaza("[cFechaNotif]",cFechaNotif);
       }


       //Identificación que se trata de Autorizacion.
       if (vDatos.getInt("iCveTipoTitulo") == 3){
         cTipoTitulo = "la Autorización";
       }

       rep.comRemplaza("[cTipoTitulo]",cTipoTitulo);
       rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));

       cTitular = vDatos.getString("cNomRazonSocial")+" "+vDatos.getString("cApPaterno")+" "+vDatos.getString("cApMaterno");

       st = new StringTokenizer(cTitular);
       cTitular="";

       while (st.hasMoreTokens()) {
          String cToken = st.nextToken();
          if(cToken.indexOf("S.A.")==-1 && cToken.indexOf("C.V.")==-1)
            cTitular += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
          else
            cTitular += " " + cToken;
       }

       rep.comRemplaza("[cTitular]",cTitular);
       if(vDatos.getDate("dtPublicacionDOF")!=null)
         cPublicDOF = Fechas.getDateSPN(vDatos.getDate("dtPublicacionDOF"));
       else
         cPublicDOF = "";
       rep.comRemplaza("[dtPublicacionDOF]",cPublicDOF);
       rep.comRemplaza("[cObjetoTitulo]",vDatos.getString("cObjetoTitulo").toLowerCase());

       if(vDatos.getString("cDscUsoTitulo").indexOf("USO")==-1)
         cUsoTitulo = "uso "+vDatos.getString("cDscUsoTitulo").toLowerCase();
       else
         cUsoTitulo = vDatos.getString("cDscUsoTitulo").toLowerCase();
       rep.comRemplaza("[cUsoTitulo]",cUsoTitulo);

       if(Integer.parseInt(vDatos.getString("iPropiedad"))==1)
         cPropiedad = "Propiedad Nacional";
       else if(Integer.parseInt(vDatos.getString("iPropiedad"))==2)
         cPropiedad = "Propiedad Particular";
       else
         cPropiedad = "";
       rep.comRemplaza("[cPropiedad]",""+cPropiedad);
       rep.comRemplaza("[cAreaSolic]",""+vDatos.getString("cCalleTitulo")+" "+vDatos.getString("cKm")+" "+vDatos.getString("cColoniaTitulo"));

       st = new StringTokenizer(vDatos.getString("cEntidadTitUbic"));
       cEntidad="";

       while (st.hasMoreTokens()) {
          String cToken = st.nextToken();
          cEntidad += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
       }

       rep.comRemplaza("[cEntidadFed]",cEntidad);
       iAniosDuracion = Fechas.getDaysBetweenDates(vDatos.getDate("dtIniVigenciaTitulo"),vDatos.getDate("dtVigenciaTitulo"));
       iAniosDuracion = iAniosDuracion/365;
       rep.comRemplaza("[cAniosDuracion]",""+iAniosDuracion);
       rep.comRemplaza("[cFechaDiligencia]",Fechas.getDateSPN( Fechas.getDateSQL(tsDiligencia) ));
       rep.comRemplaza("[cHoraDiligencia]",Fechas.getHora24H(tsDiligencia) + ":"+Fechas.getMinuto(tsDiligencia));


     } else
       System.err.println("Sin valores en la base");

     
     String [] aComisionados = vRegDilig.getString("cUsuarios").split(",");
     for(int iCom=0;iCom<aComisionados.length;iCom++){
       if(iCom==0)
         cComisionados = aComisionados[iCom];
       else if(iCom < aComisionados.length - 1 ){
         cComisionados += ", " + aComisionados[iCom];
       }else if(iCom == aComisionados.length - 1 )
         cComisionados += " y " + aComisionados[iCom];
     }
     
     while (st.hasMoreTokens()) {
         String cToken = st.nextToken();
         cComisionados += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
      }

     rep.comRemplaza("[cComisionados]",""+cComisionados);

     //return ;
     // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                            Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                            iCveOficinaDest,iCveDeptoDest,
                            0,0,0,
                            "",cAsunto,
                            "", "",
                            true,"cCuerpo",vcCuerpo,
                            true, vcCopiasPara,
                            rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

     TVDinRep vDinRep = new TVDinRep();
     /*vDinRep.put("iEjercicio",iEjercicio);
     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
     vDinRep.put("iEjercicioFol",iEjercicioOficio);
     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
     vDinRep.put("cDigitosFolio",cDigitosFolio);
     vDinRep.put("iConsecutivo",iConsecutivo);
     vDinRep.put("lEsProrroga",0);
     vDinRep.put("lEsAlegato",0);
     vDinRep.put("lEsSinEfecto",0);*/

    /*try{
      vDinRep = dCYSFolioProc.insert(vDinRep,null);
    } catch(Exception ex){
      ex.printStackTrace();
    }*/

    return vRetorno;

   }

   public Vector genReversionCapitania(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     Vector vRegsTituloProp = new Vector();
     Vector vRegsTituloDocumentos = new Vector();
     Vector vRegsTitPoliza = new Vector();
     Vector vRegsSerPor = new Vector();
     Vector vRegsSerCon = new Vector();
     TWord rep = new TWord();
     TFechas Fechas = new TFechas("44");
     TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

     int iCveOficinaDest=0,iCveDeptoDest=0,iCveTipoContrato=0;
     String cNombreAPI="",cNomCesionario="",cNumTitulo="",cAsunto="Oficio de dirigido a la Capitania de Puerto";
     String cTipoContrato="";
     String[] aFiltros = cQuery.split(",");
     int iCveTitulo = Integer.parseInt(aFiltros[0]);
     int iEjercicio = Integer.parseInt(aFiltros[1]);
     int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
     //int iMovFolioProc = Integer.parseInt(aFiltros[3]);
     //String cRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
     //String cPustoRepLegal = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

     this.setDatosFolio(cNumFolio);



     // Query de Consulta del Reporte.
     try{
       vRegs = super.FindByGeneric("", "select CPATitulo.cNumTitulo, " +
                                       "CPATitulo.iCveTipoTitulo, " +
                                       "CPATitulo.dtIniVigenciaTitulo, " +
                                       "CPATitulo.dtVigenciaTitulo, " +
                                       "CPATituloUbicacion.lDentroRecintoPort, " +
                                       "CPATitulo.cZonaFedTotalTerrestre, " +
                                       "CPATitulo.cZonaFedTotalAgua, " +
                                       "CPATitulo.iPropiedad, " +
                                       "CPATitulo.iCveClasificacion " +
                                       "from CPATitulo " +
                                       "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
                                       "where CPATitulo.iCveTitulo = " + iCveTitulo, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     //Infomacion del Titulo de Propiedad
     try{ vRegsTituloProp = super.FindByGeneric("",
                          " select CPATituloPropiedad.cSuperficiePropiedad, " +
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
                          " Notario.cApPaterno, "+
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
                          " where CPATitulo.iCveTitulo = " + iCveTitulo,
                          dataSourceName);
     } catch(SQLException ex){
        ex.printStackTrace();
        cMensaje = ex.getMessage();
     }catch(Exception ex2){
        ex2.printStackTrace();
        cMensaje += ex2.getMessage();
     }



     // Elaboración del Reporte.
     rep.iniciaReporte();
     if (vRegs.size() > 0){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);

       //Identificación que se trata de Concesión.
       if (vDatos.getInt("iCveTipoTiulo") == 1){
         //Concesion de Puerto.
         if(vDatos.getInt("iCveClasificacion") == 1){
           rep.comRemplaza("[cp]","X");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Terminal.
         if(vDatos.getInt("iCveClasificacion") == 2){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]","X");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Marina.
         if(vDatos.getInt("iCveClasificacion") == 3){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]","X");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Concesion de Instalación.
         if(vDatos.getInt("iCveClasificacion") == 4){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]","X");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }
       //Identificación que se trata de Permiso.
       if (vDatos.getInt("iCveTipoTiulo") == 2){
         //Permiso de Embarcadero.
         if(vDatos.getInt("iCveClasificacion") == 5){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]","X");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Atracadero.
         if(vDatos.getInt("iCveClasificacion") == 6){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]","X");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Botadero.
         if(vDatos.getInt("iCveClasificacion") == 7){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]","X");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Similar.
         if(vDatos.getInt("iCveClasificacion") == 8){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]","X");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Portuario.
         if(vDatos.getInt("iCveClasificacion") == 9){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]","X");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Permiso de Servicio Conexo.
         if(vDatos.getInt("iCveClasificacion") == 10){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]","X");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
       }

       //Identificación que se trata de Autorizacion.
       if (vDatos.getInt("iCveTipoTiulo") == 3){
         //Autorización de Obra Marítima.
         if(vDatos.getInt("iCveClasificacion") == 11){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]","X");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Construcción.
         if(vDatos.getInt("iCveClasificacion") == 12){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]","X");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 13){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]","X");
           rep.comRemplaza("[aa]"," ");
         }
         //Autorización de Dragado de Mantenimiento.
         if(vDatos.getInt("iCveClasificacion") == 14){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cv]"," ");
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]","X");
         }
       }

       rep.comRemplaza("[cNumTitulo]",vDatos.getString("cNumTitulo"));
       rep.comRemplaza("[dtTitulo]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtVigencia]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"));
       rep.comRemplaza("[dtInicio]", Fechas.getFechaDDMMYYYY(vDatos.getDate("dtIniVigenciaTitulo"),"/"));

       //Fuera de Recinto Portuario
       if (vDatos.getInt("lDentroRecintoPort") == 0){
         rep.comRemplaza("[ir]"," ");
         rep.comRemplaza("[if]","X");
         rep.comRemplaza("[it]","X");
         rep.comRemplaza("[cTerrestre]",vDatos.getString("cZonaFedTotalTerrestre"));
         rep.comRemplaza("[ia]","X");
         //Maritima
         rep.comRemplaza("[im]","X");
         rep.comRemplaza("[mar]",vDatos.getString("cZonaFedTotalAgua"));
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }
       //Dentro de Recinto Portuario.
       if (vDatos.getInt("lDentroRecintoPort") == 1){
         rep.comRemplaza("[ir]","X");
         rep.comRemplaza("[if]"," ");
         rep.comRemplaza("[it]"," ");
         rep.comRemplaza("[cTerrestre]","");
         rep.comRemplaza("[ia]"," ");
         //Maritima
         rep.comRemplaza("[im]"," ");
         rep.comRemplaza("[mar]"," ");
         //Fluvial
         rep.comRemplaza("[iu]","");
         rep.comRemplaza("[flu]","");
         //Lacustre
         rep.comRemplaza("[il]","");
         rep.comRemplaza("[lac]","");
         //Total
         rep.comRemplaza("[tot]","");
       }

       //Propiedad Nacional.
       if (vDatos.getInt("[iPropiedad]") == 0){
         rep.comRemplaza("[pn]","X");
         rep.comRemplaza("[pr]"," ");
       }
       //Propiedad Particular.
       if (vDatos.getInt("[iPropiedad]") == 1){
         rep.comRemplaza("[pn]"," ");
         rep.comRemplaza("[pr]","X");
       }

       //Frente a la Zona Federal Marítimo Terrestre.
       if (vRegsTituloProp.size()>0){
         TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);
         String cZonaFederal = "";
         if (vDatosTitProp.get("cLotePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Lote " + vDatosTitProp.get("cLotePropiedad");
         if (vDatosTitProp.get("cSeccionPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Sección " + vDatosTitProp.get("cSeccionPropiedad");
         if (vDatosTitProp.get("cManzanaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Manzana " + vDatosTitProp.get("cManzanaPropiedad");
         if (vDatosTitProp.get("cNumOficialPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " No. Oficial " + vDatosTitProp.get("cNumOficialPropiedad");
         if (vDatosTitProp.get("cCallePropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Calle " + vDatosTitProp.get("cCallePropiedad");
         if (vDatosTitProp.get("cKmPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Km. " + vDatosTitProp.get("cKmPropiedad");
         if (vDatosTitProp.get("cColoniaPropiedad")!= null)
           cZonaFederal = cZonaFederal  + " Col. " + vDatosTitProp.get("cColoniaPropiedad");
         if (vDatosTitProp.get("cDscMunicipio")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscMunicipio");
         if (vDatosTitProp.get("cDscEntidadFed")!= null)
           cZonaFederal = cZonaFederal  + "," + vDatosTitProp.get("cDscEntidadFed");
         if (vDatosTitProp.get("cDscPuerto")!= null)
           cZonaFederal = cZonaFederal  + " en el Puerto de " + vDatosTitProp.get("cDscPuerto");
         rep.comRemplaza("[cFrenteZona]",cZonaFederal);
       }

       if (vRegsTituloDocumentos.size()>0){
         for(int i=0;i<vRegsTituloDocumentos.size();i++){
           TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
           switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

             //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
             case 1:case 2:{
               rep.comRemplaza("[tc]","X");
               rep.comRemplaza("[cNumTitZOFEMAT]" ,vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtTitZOFEMAT]"   ,Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniTitZOFEMAT]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }

             //Autorizacion en Materia de Impacto Ambiental.
             case 5:{
               rep.comRemplaza("[ai]","X");
               rep.comRemplaza("[cNumAutImpAmb]",vDatosTitDoctos.getString("cNumDocumento"));
               rep.comRemplaza("[dtAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               rep.comRemplaza("[dtVigAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"));
               rep.comRemplaza("[dtIniAutImpAmb]",Fechas.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"));
               break;
             }
           }
         }
       }

       if (vRegsTitPoliza.size()>0){
         for(int i=0;i<vRegsTitPoliza.size();i++){
           TVDinRep vDatosTitPoliza = (TVDinRep) vRegsTitPoliza.get(i);
           //Pólizas de Seguro.
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==1){
             rep.comRemplaza("[pg]","");
             rep.comRemplaza("[re]","");
             //Responsabilidad Civil.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==1){
               rep.comRemplaza("[in]","X");
               rep.comRemplaza("[rc]"," ");
             }
             //Instalaciones Portuarias.
             if (vDatosTitPoliza.getInt("iCveTipoPoliza") ==2){
               rep.comRemplaza("[in]"," ");
               rep.comRemplaza("[rc]","X");
             }
             rep.comRemplaza("[cNumPoliza]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[cAseguradora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioRespCivil]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
             rep.comRemplaza("[dtPresPoliza]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtPresentacion"),"/"));
           }

           //Polizas de Fianzas
           if (vDatosTitPoliza.getInt("lSeguroFianza") ==2){
             rep.comRemplaza("[pf]","X");
             rep.comRemplaza("[ed]","X");
             rep.comRemplaza("[cNumEndoso]",vDatosTitPoliza.getString("cNumDocto"));
             rep.comRemplaza("[dtEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtEmiDocto"),"/"));
             rep.comRemplaza("[ImpEndoso]","");
             rep.comRemplaza("[ActEndoso]","");
             rep.comRemplaza("[cAfianzadora]",vDatosTitPoliza.getString("cAsegAfian"));
             rep.comRemplaza("[cBeneficiarioEndoso]",vDatosTitPoliza.getString("cBeneficiario"));
             rep.comRemplaza("[dtVigEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtIniVigencia"),"/"));
             rep.comRemplaza("[dtIniEndoso]",Fechas.getFechaDDMMYYYY(vDatosTitPoliza.getDate("dtFinVigencia"),"/"));
           }
         }
       }

     } else {
       //Sin Valores en la Base.



     }

     //return ;
     // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                            Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                            iCveOficinaDest,iCveDeptoDest,
                            0,0,0,
                            "",cAsunto,
                            "", "",
                            true,"cCuerpo",vcCuerpo,
                            true, vcCopiasPara,
                            rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

     TVDinRep vDinRep = new TVDinRep();
     vDinRep.put("iEjercicio",iEjercicio);
     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
     vDinRep.put("iEjercicioFol",iEjercicioOficio);
     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
     vDinRep.put("cDigitosFolio",cDigitosFolio);
     vDinRep.put("iConsecutivo",iConsecutivo);
     vDinRep.put("lEsProrroga",0);
     vDinRep.put("lEsAlegato",0);
     vDinRep.put("lEsSinEfecto",0);

    try{
      vDinRep = dCYSFolioProc.insert(vDinRep,null);
    } catch(Exception ex){
      ex.printStackTrace();
    }

    return vRetorno;

   }


  private void setDatosFolio(String cNumFolio){
     String[] cDatosFolio = cNumFolio.split("/");
     String cTemp = cDatosFolio[0].replace('.', '/');
     String[] cDigitosTemp = cTemp.split("/");
     int Ejercicio = Integer.parseInt(cDatosFolio[cDatosFolio.length-1], 10);
     int Consecutivo = Integer.parseInt(cDigitosTemp[cDigitosTemp.length-1], 10);
     String Digitos = "";
     for(int i=0; i<cDigitosTemp.length-1; i++){
       Digitos += cDigitosTemp[i];
       if (i<cDigitosTemp.length-2)
         Digitos += ".";
     }
     iEjercicioOficio = Ejercicio;
     cDigitosFolio = Digitos;
     iConsecutivo = Consecutivo;
   }
}

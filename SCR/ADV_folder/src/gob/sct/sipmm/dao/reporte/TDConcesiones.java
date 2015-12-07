package gob.sct.sipmm.dao.reporte;
import java.sql.*;
import java.util.*;

import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDConcesiones.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame
 * @version 1.0
 */

public class TDConcesiones extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TExcel rep = new TExcel();

  public TDConcesiones(){
  }

  public StringBuffer GenConcesion(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    Vector vRegsEscrituraConst = new Vector();
    Vector vRegsAcredRepLegal = new Vector();
    Vector vRegsTituloProp = new Vector();
    Vector vRegsTituloUbicacion = new Vector();
    Vector vRegsTituloSolicitud = new Vector();
    Vector vRegsTituloObras = new Vector();
    Vector vRegsTituloServPortuarios = new Vector();
    Vector vRegsTituloDocumentos = new Vector();
    Vector vRegsTituloAnexos = new Vector();
    Vector vRegsTituloAnterior = new Vector();
    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TFechas Fecha = new TFechas("44");

    TWord rep = new TWord();
    rep.iniciaReporte();
    int iCveTituloFiltro = Integer.parseInt(cQuery);
    int lEntroZOFEMAT = 0,lEntroImpAmb = 0,lEntroPEMEX = 0,lEntroCONAGUA = 0,lEntroSEMARNAT = 0;
    String cRepLegalCompleto="";

    cQuery = "select CPATitulo.iCveTitulo, " +
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
             "CPATitulo.iCveTituloAnterior, " +
             "CPATitulo.dtVigenciaTitulo " +
             "from CPATitulo " +
             "join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
             "join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
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
             "where CPATitulo.iCveTitulo = " + iCveTituloFiltro;

    try{
      vRegs = super.FindByGeneric("", cQuery, dataSourceName);
    }catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    if (vRegs.size() > 0){
         TVDinRep vDatos = (TVDinRep) vRegs.get(0);

         int iCveTitulo = Integer.parseInt(vDatos.get("iCveTitulo").toString());
         int iCveTituloAnt = vDatos.getInt("iCveTituloAnterior");


         // Escritura Constitutiva de las Personas Morales.
         try{
           vRegsEscrituraConst = super.FindByGeneric("",
           " select CPAEscConsMov.iNumMovimiento, " +
           " CPAEscConsMov.cNumEscrituraConst, " +
           " CPATipoMovEscritura.cDscTipoMovEscritura, " +
           " CPAEscConsMov.dtEscrituraMov, " +
           " CPAEscConsMov.cNumNotariaPub, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " CPARegPubComercio.dtRegPubComercio, " +
           " CPARegPubComercio.cNumRegPubComercio, " +
           " CPARegPubComercio.cNumFoja, " +
           " CPARegPubComercio.cNumTomo, " +
           " CPARegPubComercio.cNumSeccion, " +
           " CPARegPubComercio.cNumLibro, " +
           " CPARegPubComercio.cNumVolumen, " +
           " CPARegPubComercio.cNumSeccion, " +
           " GRLPais.cNombre as cDscPais, " +
           " GRLEntidadFed.cNombre as cDscEntidadFed, " +
           " GRLMunicipio.cNombre as cDscMunicipio, " +
           " GRLLocalidad.cNombre as cDscLocalidad " +
           " from CPATitulo " +
           " join CPAEscConsMov on CPAEscConsMov.iCveTitulo = CPATitulo.iCveTitulo " +
           " join CPATipoMovEscritura on CPATipoMovEscritura.iCveTipoMovEscritura = CPAEscConsMov.iCveTipoMovEscritura " +
           " left join CPARegPubComercio on CPARegPubComercio.iCveTitulo = CPAEscConsMov.iCveTitulo " +
           " and CPARegPubComercio.iNumMovimiento = CPAEscConsMov.iNumMovimiento " +
           " left join GRLPersona on GRLPersona.iCvePersona = CPAEscConsMov.iCveNotarioPublico " +
           " left join GRLPais on GRLPais.iCvePais = CPARegPubComercio.iCvePais " +
           " left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " left join GRLMunicipio on GRLMunicipio.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
           " left join GRLLocalidad on GRLLocalidad.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = CPARegPubComercio.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
          ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Acreditacion del Representante Legal.
         try{
           vRegsAcredRepLegal = super.FindByGeneric("",
           " select CPAAcreditacionRepLegal.dtAcredRepLegal, " +
           " CPAAcreditacionRepLegal.cNumNotPubAcred, " +
           " CPAAcreditacionRepLegal.cDocumento, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " Notario.cNomRazonSocial as cNomNotario, " +
           " Notario.cApPaterno as cApPatNotario, " +
           " Notario.cApMaterno as cApMatNotario, " +
           " GRLPais.cNombre as cNomPais, " +
           " GRLEntidadFed.cNombre as cNomEntidadFed, " +
           " GRLMunicipio.cNombre as cNomMunicipio, " +
           " GRLLocalidad.cNombre as cNomLocalidad " +
           " from CPATitulo " +
           " join CPAAcreditacionRepLegal on CPAAcreditacionRepLegal.iCveTitulo = CPATitulo.iCveTitulo " +
           " join GRLPersona on GRLPersona.iCvePersona = CPAAcreditacionRepLegal.iCveRepLegal " +
           " join GRLPersona Notario on Notario.iCvePersona = CPAAcreditacionRepLegal.iCveNotarioPubAcred " +
           " join GRLDomicilio on GRLDomicilio.iCvePersona = Notario.iCvePersona " +
           " and GRLDomicilio.lPredeterminado = 1 " +
           " join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
           " join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Infomacion del Titulo de Propiedad
         try{
           vRegsTituloProp = super.FindByGeneric("",
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
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Ubicacion del Area Solicitada
         try{
           vRegsTituloUbicacion = super.FindByGeneric("",
           " select CPATituloUbicacion.lDentroRecintoPort, " +
           " CPATituloUbicacion.cCalleTitulo, " +
           " CPATituloUbicacion.cKm, " +
           " CPATituloUbicacion.cColoniaTitulo, " +
           " GRLPuerto.cDscPuerto, " +
           " GRLPais.cNombre as cDscPais, " +
           " GRLEntidadFed.cNombre as cDscEntidadFed, " +
           " GRLMunicipio.cNombre as cDscMunicipio, " +
           " GRLLocalidad.cNombre as cDscLocalidad " +
           " from CPATitulo " +
           " join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
           " left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
           " left join GRLPais on GRLPais.iCvePais = CPATituloUbicacion.iCvePais " +
           " left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloUbicacion.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
           " left join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloUbicacion.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
           " left join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloUbicacion.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = CPATituloUbicacion.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Solicitud
         try{
           vRegsTituloSolicitud = super.FindByGeneric("",
               " select CPASolTitulo.iEjercicio, " +
               " CPASolTitulo.iNumSolicitud, " +
               " TRACatTramite.cDscBreve, " +
               " TRAModalidad.cDscModalidad, " +
               " date(TRARegSolicitud.tsRegistro) as dtRegistro, " +
               " GRLOficina.cCalleyNo, " +
               " GRLOficina.cColonia, " +
               " GRLOficina.cCodPostal, " +
               " GRLOficina.cTelefono, " +
               " GRLOficina.cCorreoE, " +
               " GRLPais.cNombre as cDscPais, " +
               " GRLEntidadFed.cNombre as cDscEntidadFed, " +
               " GRLMunicipio.cNombre as cDscMunicipio, " +
               " TRACatTramite.iCveTramite, " +
               " TRAModalidad.iCveModalidad " +
               " from CPATitulo " +
               " join CPASolTitulo on CPASolTitulo.iCveTitulo = CPATitulo.iCveTitulo " +
               " join TRARegSolicitud on TRARegSolicitud.iEjercicio = CPASolTitulo.iEjercicio " +
               " and TRARegSolicitud.iNumSolicitud = CPASolTitulo.iNumSolicitud " +
               " join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite " +
               " join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad " +
               " join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina " +
               " join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais "+
               " join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais " +
               " and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais " +
               " and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
               " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de las Obras del Título.
         try{
           vRegsTituloObras = super.FindByGeneric("",
           " select CPATituloObra.cDscObra, " +
           " CPATituloObra.lPropiedadNacional, " +
           " CPATituloObra.lConstruccion, " +
           " CPATituloObra.lOperacion, " +
           " CPATituloObra.cTiempoEstEjecucion, " +
           " CPATituloObra.dtIniObra " +
           " from CPATitulo " +
           " join CPATituloObra on CPATituloObra.iCveTitulo = CPATitulo.iCveTitulo " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de Servicios Portuarios Relacionados con los Títulos.
         try{
           vRegsTituloServPortuarios = super.FindByGeneric("",
           " select CPAServicioPortuario.cDscServicioPortuario " +
           " from CPATitulo " +
           " join CPATituloServicioPortuario on CPATituloServicioPortuario.iCveTitulo = CPATitulo.iCveTitulo " +
           " join CPAServicioPortuario on CPAServicioPortuario.iCveServicioPortuario = CPATituloServicioPortuario.iCveServicioPortuario " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de Documentos Relacionados con el Título.
         try{
           vRegsTituloDocumentos = super.FindByGeneric("",
           " select CPATituloDocumento.iCveTipoDocumento, " +
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
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de los Anexos del Título.
         try{
           vRegsTituloAnexos = super.FindByGeneric("",
           " select CPAAnexo.cDscAnexo, " +
           " CPAAnexoTramite.iOrden, " +
           " CPAAnexo.iNivel, " +
           " CPAAnexo.iCveAnexo " +
           " from CPATitulo " +
           " join CPATituloAnexo on CPATituloAnexo.iCveTitulo = CPATitulo.iCveTitulo " +
           " join CPAAnexoTramite on CPAAnexoTramite.iCveAnexo = CPATituloAnexo.iCveAnexo " +
           " and CPAAnexoTramite.iCveTramite = CPATituloAnexo.iCveTramite " +
           " and CPAAnexoTramite.iCveModalidad = CPATituloAnexo.iCveModalidad " +
           " and CPAAnexoTramite.dtIniVigencia = CPATituloAnexo.dtIniVigencia " +
           " join CPAAnexo on CPAAnexo.iCveAnexo = CPATituloAnexo.iCveAnexo " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion del Titulo Anterior.
         if (iCveTituloAnt>0){
           try{
             vRegsTituloAnterior = super.FindByGeneric("",
                 " select CPATitulo.iCveTipoTitulo, " +
                 " CPATipoTitulo.cDscTipoTitulo, " +
                 " CPATitulo.cNumTitulo, " +
                 " CPATitulo.dtIniVigenciaTitulo, " +
                 " CPATitulo.dtVigenciaTitulo, " +
                 " CPATitulo.dtPublicacionDOF " +
                 " from CPATitulo " +
                 " join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
                 " where CPATitulo.iCveTitulo = " + iCveTituloAnt,
                 dataSourceName);
           } catch(Exception ex){
             ex.printStackTrace();
             cMensaje += ex.getMessage();
           }
         }

         rep.iniciaReporte();

         if (vDatos.getInt("iTipoPersona")== 1){
           //Valores para las Personas Físicas.
           rep.comRemplaza("[cNombre]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString():"");
           rep.comRemplaza("[cApPaterno]",(vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString():"");
           rep.comRemplaza("[cApMaterno]",(vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():"");
           rep.comRemplaza("[cNoActa]",(vDatos.get("cNumActaNacimiento")!= null)?vDatos.get("cNumActaNacimiento").toString():"");
           rep.comRemplaza("[cFechaActa]",(vDatos.get("dtNacimiento")!= null)?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtNacimiento"),"/"):"");
           rep.comRemplaza("[cNoJuzgado]",(vDatos.get("cNumJuzgado")!= null)?vDatos.get("cNumJuzgado").toString():"");
           rep.comRemplaza("[cNomJuez]",(vDatos.get("cNomJuez")!= null)?vDatos.get("cNomJuez").toString():"");
           rep.comRemplaza("[cApPatJuez]",(vDatos.get("cApPatJuez")!= null)?vDatos.get("cApPatJuez").toString():"");
           rep.comRemplaza("[cApMatJuez]",(vDatos.get("cApMatJuez")!= null)?vDatos.get("cApMatJuez").toString():"");
           rep.comRemplaza("[cLibroActa]",(vDatos.get("cNumLibroActa")!= null)?vDatos.get("cNumLibroActa").toString():"");
           rep.comRemplaza("[cFojaActa]",(vDatos.get("cNumFojaActa")!= null)?vDatos.get("cNumFojaActa").toString():"");
           //Para quien es la Concesion.
           rep.comRemplaza("[cPara]",((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                                     ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                                     ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():""));
           cRepLegalCompleto = ((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                               ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                               ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():"");

         } else {
           rep.comRemplaza("[cNombre]","");
           rep.comRemplaza("[cApPaterno]","");
           rep.comRemplaza("[cApMaterno]","");
           rep.comRemplaza("[cNoActa]","");
           rep.comRemplaza("[cFechaActa]","");
           rep.comRemplaza("[cNoJuzgado]","");
           rep.comRemplaza("[cNomJuez]","");
           rep.comRemplaza("[cApPatJuez]","");
           rep.comRemplaza("[cApMatJuez]","");
           rep.comRemplaza("[cLibroActa]","");
           rep.comRemplaza("[cFojaActa]","");
         }


         if (vDatos.getInt("iTipoPersona")== 2){

           if(vRegsEscrituraConst.size()>0){
             TVDinRep vDatosEscConst = (TVDinRep) vRegsEscrituraConst.get(0);

             //Para quien es la Concesion.
             rep.comRemplaza("[cPara]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"");

             //Valores de las Personas Morales con su Escritura Constitutiva.
             rep.comRemplaza("[cRazonSocial]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString():"");
             rep.comRemplaza("[NoEscConstitutiva]",(vDatosEscConst.get("cNumEscrituraConst")!= null)?vDatosEscConst.get("cNumEscrituraConst").toString():"");
             rep.comRemplaza("[cFechaEscConstitutiva]",(vDatosEscConst.get("dtEscrituraMov")!= null)?Fecha.getFechaDDMMYYYY(vDatosEscConst.getDate("dtEscrituraMov"),"/"):"");
             rep.comRemplaza("[cNoNotariaPub]",(vDatosEscConst.get("cNumNotariaPub")!= null)?vDatosEscConst.get("cNumNotariaPub").toString():"");
             rep.comRemplaza("[cCiudad]",((vDatosEscConst.get("cDscLocalidad")!= null)?vDatosEscConst.get("cDscLocalidad").toString():"")+
                                         ((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
             rep.comRemplaza("[cEntidadFed]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");
             rep.comRemplaza("[[cNomNotPub]]",(vDatosEscConst.get("cNomRazonSocial")!= null)?vDatosEscConst.get("cNomRazonSocial").toString():"");
             rep.comRemplaza("[cApPatNotPub]",(vDatosEscConst.get("cApPaterno")!= null)?vDatosEscConst.get("cApPaterno").toString():"");
             rep.comRemplaza("[cApMatNotPub]",(vDatosEscConst.get("cApMaterno")!= null)?vDatosEscConst.get("cApMaterno").toString():"");
             rep.comRemplaza("[cRFC]",(vDatos.get("cRFC")!= null)?vDatos.get("cRFC").toString():"");

             //Valores para el Registro Público de la Comercio de la Escritura Publica.
             rep.comRemplaza("[cFechaRP]",(vDatosEscConst.get("dtRegPubComercio")!= null)?Fecha.getFechaDDMMYYYY(vDatosEscConst.getDate("dtRegPubComercio"),"/"):"");
             rep.comRemplaza("[FolioRealRP]",(vDatosEscConst.get("cNumRegPubComercio")!= null)?vDatosEscConst.get("cNumRegPubComercio").toString():"");
             rep.comRemplaza("[FojaRP]",(vDatosEscConst.get("cNumFoja")!= null)?vDatosEscConst.get("cNumFoja").toString():"");
             rep.comRemplaza("[cToRP]",(vDatosEscConst.get("cNumTomo")!= null)?vDatosEscConst.get("cNumTomo").toString():"");
             rep.comRemplaza("[cSecRP]",(vDatosEscConst.get("cNumSeccion")!= null)?vDatosEscConst.get("cNumSeccion").toString():"");
             rep.comRemplaza("[cLibRP]",(vDatosEscConst.get("cNumLibro")!= null)?vDatosEscConst.get("cNumLibro").toString():"");
             rep.comRemplaza("[cVolRP]",(vDatosEscConst.get("cNumVolumen")!= null)?vDatosEscConst.get("cNumVolumen").toString():"");
             rep.comRemplaza("[cCiudadRP]",((vDatosEscConst.get("cDscLocalidad")!= null)?vDatosEscConst.get("cDscLocalidad").toString():"")+
                                           ((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
             rep.comRemplaza("[cEntidadFedRP]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");

             if (vRegsEscrituraConst.size() > 1){
               TVDinRep vDatosModEscConst = (TVDinRep) vRegsEscrituraConst.get(1);

               //Modificaciones a la Escritura Constitutiva.
               rep.comRemplaza("[cNoEscMRP]",(vDatosModEscConst.get("cNumEscrituraConst")!= null)?vDatosModEscConst.get("cNumEscrituraConst").toString():"");
               rep.comRemplaza("[cFechaMRP]",(vDatosModEscConst.get("dtEscrituraMov")!= null)?Fecha.getFechaDDMMYYYY(vDatosModEscConst.getDate("dtEscrituraMov"),"/"):"");
               rep.comRemplaza("[cNoNotPubMRP]",(vDatosModEscConst.get("cNumNotariaPub")!= null)?vDatosModEscConst.get("cNumNotariaPub").toString():"");
               rep.comRemplaza("[cCiudadMRP]",((vDatosModEscConst.get("cDscLocalidad")!= null)?vDatosModEscConst.get("cDscLocalidad").toString():"")+
                                              ((vDatosModEscConst.get("cDscMunicipio")!= null)?vDatosModEscConst.get("cDscMunicipio").toString():""));
               rep.comRemplaza("[cEntFedMRP]",(vDatosModEscConst.get("cDscEntidadFed")!= null)?vDatosModEscConst.get("cDscEntidadFed").toString():"");
               rep.comRemplaza("[cNotPubMRP]",((vDatosModEscConst.get("cNomRazonSocial")!= null)?vDatosModEscConst.get("cNomRazonSocial").toString():"")+
                                              ((vDatosModEscConst.get("cApPaterno")!= null)?vDatosModEscConst.get("cApPaterno").toString():"")+
                                              ((vDatosModEscConst.get("cApMaterno")!= null)?vDatosModEscConst.get("cApMaterno").toString():""));

               //Registro Publico de Comercio de las Modificaciones a la Escritura Constitutiva.
               rep.comRemplaza("[cFecRPC]",(vDatosModEscConst.get("dtRegPubComercio")!= null)?Fecha.getFechaDDMMYYYY(vDatosModEscConst.getDate("dtRegPubComercio"),"/"):"");
               rep.comRemplaza("[cFolRPC]",(vDatosModEscConst.get("cNumRegPubComercio")!= null)?vDatosModEscConst.get("cNumRegPubComercio").toString():"");
               rep.comRemplaza("[cFojRPC]",(vDatosModEscConst.get("cNumFoja")!= null)?vDatosModEscConst.get("cNumFoja").toString():"");
               rep.comRemplaza("[cTomRPC]",(vDatosModEscConst.get("cNumTomo")!= null)?vDatosModEscConst.get("cNumTomo").toString():"");
               rep.comRemplaza("[cSecRPC]",(vDatosModEscConst.get("cNumSeccion")!= null)?vDatosModEscConst.get("cNumSeccion").toString():"");
               rep.comRemplaza("[cLibRPC]",(vDatosModEscConst.get("cNumLibro")!= null)?vDatosModEscConst.get("cNumLibro").toString():"");
               rep.comRemplaza("[cVolRPC]",(vDatosModEscConst.get("cNumVolumen")!= null)?vDatosModEscConst.get("cNumVolumen").toString():"");
               rep.comRemplaza("[cCiudadRPC]",((vDatosModEscConst.get("cDscLocalidad")!= null)?vDatosModEscConst.get("cDscLocalidad").toString():"")+
                                              ((vDatosModEscConst.get("cDscMunicipio")!= null)?vDatosModEscConst.get("cDscMunicipio").toString():""));
               rep.comRemplaza("[cEntFedRPC]",(vDatosModEscConst.get("cDscEntidadFed")!= null)?vDatosModEscConst.get("cDscEntidadFed").toString():"");
             }
           }

           if (vRegsAcredRepLegal.size()> 0){
             TVDinRep vDatosRepLegal = (TVDinRep) vRegsAcredRepLegal.get(0);

             //Información del Representante Legal.
             rep.comRemplaza("[cNombreRL]",(vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString():"");
             rep.comRemplaza("[cApPatRL]",(vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString():"");
             rep.comRemplaza("[cApMatRL]",(vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");
             rep.comRemplaza("[cFechaRL]",(vDatosRepLegal.get("dtAcredRepLegal")!= null)?Fecha.getFechaDDMMYYYY(vDatosRepLegal.getDate("dtAcredRepLegal"),"/"):"");
             rep.comRemplaza("[cNoNotPubRL]",(vDatosRepLegal.get("cNumNotPubAcred")!= null)?vDatosRepLegal.get("cNumNotPubAcred").toString():"");
             rep.comRemplaza("[cCiudadRL]",((vDatosRepLegal.get("cNomLocalidad")!= null)?vDatosRepLegal.get("cNomLocalidad").toString():"")+
                                           ((vDatosRepLegal.get("cNomMunicipio")!= null)?vDatosRepLegal.get("cNomMunicipio").toString():""));
             rep.comRemplaza("[cEntFedRL]",(vDatosRepLegal.get("cNomEntidadFed")!= null)?vDatosRepLegal.get("cNomEntidadFed").toString():"");
             rep.comRemplaza("[cNotPubRL]",((vDatosRepLegal.get("cNomNotario")!= null)?vDatosRepLegal.get("cNomNotario").toString():"")+
                                           ((vDatosRepLegal.get("cApPatNotario")!= null)?vDatosRepLegal.get("cApPatNotario").toString():"")+
                                           ((vDatosRepLegal.get("cApMatNotario")!= null)?vDatosRepLegal.get("cApMatNotario").toString():""));

             cRepLegalCompleto = ((vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");


           } else {
             rep.comRemplaza("[cNombreRL]","");
             rep.comRemplaza("[cApPatRL]","");
             rep.comRemplaza("[cApMatRL]","");
             rep.comRemplaza("[cFechaRL]","");
             rep.comRemplaza("[cNoNotPubRL]","");
             rep.comRemplaza("[cCiudadRL]","");
             rep.comRemplaza("[cEntFedRL]","");
             rep.comRemplaza("[cNotPubRL]","");
           }
         } else {

           //Valores de las Personas Morales con su Escritura Constitutiva.
           rep.comRemplaza("[cRazonSocial]","");
           rep.comRemplaza("[NoEscConstitutiva]","");
           rep.comRemplaza("[cFechaEscConstitutiva]","");
           rep.comRemplaza("[cNoNotariaPub]","");
           rep.comRemplaza("[cCiudad]","");
           rep.comRemplaza("[cEntidadFed]","");
           rep.comRemplaza("[cNomNotPub]","");
           rep.comRemplaza("[cApPatNotPub]","");
           rep.comRemplaza("[cApMatNotPub]","");
           rep.comRemplaza("[cRFC]","");

           //Valores para el Registro Público de la Comercio de la Escritura Publica.
           rep.comRemplaza("[cFechaRP]","");
           rep.comRemplaza("[FolioRealRP]","");
           rep.comRemplaza("[FojaRP]","");
           rep.comRemplaza("[cToRP]","");
           rep.comRemplaza("[cSecRP]","");
           rep.comRemplaza("[cLibRP]","");
           rep.comRemplaza("[cVolRP]","");
           rep.comRemplaza("[cCiudadRP]","");
           rep.comRemplaza("[cEntidadFedRP]","");

           //Modificaciones a la Escritura Constitutiva.
           rep.comRemplaza("[cNoEscMRP]","");
           rep.comRemplaza("[cFechaMRP]","");
           rep.comRemplaza("[cNoNotPubMRP]","");
           rep.comRemplaza("[cCiudadMRP]","");
           rep.comRemplaza("[cEntFedMRP]","");
           rep.comRemplaza("[cNotPubMRP]","");

           //Registro Publico de Comercio de las Modificaciones a la Escritura Constitutiva.
           rep.comRemplaza("[cFecRPC]","");
           rep.comRemplaza("[cFolRPC]","");
           rep.comRemplaza("[cFojRPC]","");
           rep.comRemplaza("[cTomRPC]","");
           rep.comRemplaza("[cSecRPC]","");
           rep.comRemplaza("[cLibRPC]","");
           rep.comRemplaza("[cVolRPC]","");
           rep.comRemplaza("[cCiudadRPC]","");
           rep.comRemplaza("[cEntFedRPC]","");

           //Información del Representante Legal.
           rep.comRemplaza("[cNombreRL]","");
           rep.comRemplaza("[cApPatRL]","");
           rep.comRemplaza("[cApMatRL]","");
           rep.comRemplaza("[cFechaRL]","");
           rep.comRemplaza("[cNoNotPubRL]","");
           rep.comRemplaza("[cCiudadRL]","");
           rep.comRemplaza("[cEntFedRL]","");
           rep.comRemplaza("[cNotPubRL]","");
         }

         //Informacion del Domicilio para Recibir Notificaciones.
         rep.comRemplaza("[cCalleDN]",(vDatos.get("cCalle")!= null)?vDatos.get("cCalle").toString():"");
         rep.comRemplaza("[cNoExtDN]",(vDatos.get("cNumExterior")!= null)?vDatos.get("cNumExterior").toString():"");
         rep.comRemplaza("[cNoIntDN]",(vDatos.get("cNumInterior")!= null)?vDatos.get("cNumInterior").toString():"");
         rep.comRemplaza("[cColDN]",(vDatos.get("cColonia")!= null)?vDatos.get("cColonia").toString():"");
         rep.comRemplaza("[cDelDN]",(vDatos.get("cNomMunicipio")!= null)?vDatos.get("cNomMunicipio").toString():"");
         rep.comRemplaza("[cCiudadDN]",(vDatos.get("cNomLocalidad")!= null)?vDatos.get("cNomLocalidad").toString():"");
         rep.comRemplaza("[cCodPosDN]",(vDatos.get("cCodPostal")!= null)?vDatos.get("cCodPostal").toString():"");
         rep.comRemplaza("[cEntFedDN]",(vDatos.get("cNomEntidadFed")!= null)?vDatos.get("cNomEntidadFed").toString():"");

         if (vRegsTituloProp.size()>0){
           TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);

           //Informacion del Título de Propiedad.
           rep.comRemplaza("[cSupZF]",(vDatosTitProp.get("cSuperficiePropiedad")!= null)?vDatosTitProp.get("cSuperficiePropiedad").toString():"");
           rep.comRemplaza("[cLoteZF]",(vDatosTitProp.get("cLotePropiedad")!= null)?vDatosTitProp.get("cLotePropiedad").toString():"");
           rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cSeccionPropiedad")!= null)?vDatosTitProp.get("cSeccionPropiedad").toString():"");
           rep.comRemplaza("[cManZF]",(vDatosTitProp.get("cManzanaPropiedad")!= null)?vDatosTitProp.get("cManzanaPropiedad").toString():"");
           rep.comRemplaza("[cNoZF]",(vDatosTitProp.get("cNumOficialPropiedad")!= null)?vDatosTitProp.get("cNumOficialPropiedad").toString():"");
           rep.comRemplaza("[cCalZF]",(vDatosTitProp.get("cCallePropiedad")!= null)?vDatosTitProp.get("cCallePropiedad").toString():"");
           rep.comRemplaza("[cKMZF]",(vDatosTitProp.get("cKmPropiedad")!= null)?vDatosTitProp.get("cKmPropiedad").toString():"");
           rep.comRemplaza("[cColZF]",(vDatosTitProp.get("cColoniaPropiedad")!= null)?vDatosTitProp.get("cColoniaPropiedad").toString():"");
           rep.comRemplaza("[cPuertoZF]",(vDatosTitProp.get("cDscPuerto")!= null)?vDatosTitProp.get("cDscPuerto").toString():"");
           rep.comRemplaza("[cMunZF]",(vDatosTitProp.get("cDscMunicipio")!= null)?vDatosTitProp.get("cDscMunicipio").toString():"");
           rep.comRemplaza("[cEntFedZF]",(vDatosTitProp.get("cDscEntidadFed")!= null)?vDatosTitProp.get("cDscEntidadFed").toString():"");

           //Informacion de la Escritura Publica del Título de Propiedad.
           rep.comRemplaza("[cNoEscZF]",(vDatosTitProp.get("cNumTitProEscPublica") != null && vDatosTitProp.get("cNumTitProEscPublica")!= null)?vDatosTitProp.get("cNumTitProEscPublica").toString():"");
           rep.comRemplaza("[cFechaZF]",(vDatosTitProp.get("dtEscPublica") != null)?Fecha.getFechaDDMMYYYY(vDatosTitProp.getDate("dtEscPublica"),"/"):"");
           rep.comRemplaza("[cNoNotPubZF]",(vDatosTitProp.get("cNumNotariaPublica") != null && vDatosTitProp.get("cNumNotariaPublica")!= null)?vDatosTitProp.get("cNumNotariaPublica").toString():"");
           rep.comRemplaza("[cCiuZF]",((vDatosTitProp.get("cDscLocalidadEscPub") != null && vDatosTitProp.get("cDscLocalidadEscPub")!= null)?vDatosTitProp.get("cDscLocalidadEscPub").toString():"")+
                            ((vDatosTitProp.get("cDscMunicipioEscPub") != null && vDatosTitProp.get("cDscMunicipioEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():""));
           rep.comRemplaza("[cEntFedEPZF]",(vDatosTitProp.get("cDscEntidadFedEscPub") != null && vDatosTitProp.get("cDscEntidadFedEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():"");
           rep.comRemplaza("[cNotarioPublicoZF]",((vDatosTitProp.get("cNomRazonSocial") != null && vDatosTitProp.get("cNomRazonSocial")!= null)?vDatosTitProp.get("cNomRazonSocial").toString():"")+
                            ((vDatosTitProp.get("cApPaterno") != null && vDatosTitProp.get("cApPaterno")!= null)?vDatosTitProp.get("cApPaterno").toString():"")+
                            ((vDatosTitProp.get("cApMaterno") != null && vDatosTitProp.get("cApMaterno")!= null)?vDatosTitProp.get("cApMaterno").toString():""));

           //Informacion del Registro Publico de Comercio del Título de Propiedad.
           rep.comRemplaza("[cFecZF]",(vDatosTitProp.get("dtTitProRegPubCom") != null)?Fecha.getFechaDDMMYYYY(vDatosTitProp.getDate("dtTitProRegPubCom"),"/"):"");
           rep.comRemplaza("[cFolioZF]",(vDatosTitProp.get("cNumTitProRegPubCom") != null && vDatosTitProp.get("cNumTitProRegPubCom")!= null)?vDatosTitProp.get("cNumTitProRegPubCom").toString():"");
           rep.comRemplaza("[cFojaZF]",(vDatosTitProp.get("cNumRegPubComFoja") != null && vDatosTitProp.get("cNumRegPubComFoja")!= null)?vDatosTitProp.get("cNumRegPubComFoja").toString():"");
           rep.comRemplaza("[cToZF]",(vDatosTitProp.get("cNumRegPubComTomo") != null && vDatosTitProp.get("cNumRegPubComTomo")!= null)?vDatosTitProp.get("cNumRegPubComTomo").toString():"");
           rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cNumRegPubComSeccion") != null && vDatosTitProp.get("cNumRegPubComSeccion")!= null)?vDatosTitProp.get("cNumRegPubComSeccion").toString():"");
           rep.comRemplaza("[cLibroZF]",(vDatosTitProp.get("cNumRegPubComLibro") != null && vDatosTitProp.get("cNumRegPubComLibro")!= null)?vDatosTitProp.get("cNumRegPubComLibro").toString():"");
           rep.comRemplaza("[cVolZF]",(vDatosTitProp.get("cNumRegPubComVolumen") != null && vDatosTitProp.get("cNumRegPubComVolumen")!= null)?vDatosTitProp.get("cNumRegPubComVolumen").toString():"");
           rep.comRemplaza("[cCiudadZF]",((vDatosTitProp.get("cDscLocalidadRegPubCom") != null && vDatosTitProp.get("cDscLocalidadRegPubCom")!= null)?vDatosTitProp.get("cDscLocalidadRegPubCom").toString():"")+
                            ((vDatosTitProp.get("cDscMunicipioRegPubCom") != null && vDatosTitProp.get("cDscMunicipioRegPubCom")!= null)?vDatosTitProp.get("cDscMunicipioRegPubCom").toString():""));
           rep.comRemplaza("[cEntFedRPCZF]",(vDatosTitProp.get("cDscEntidadFedRegPubCom") != null && vDatosTitProp.get("cDscEntidadFedRegPubCom")!= null)?vDatosTitProp.get("cDscEntidadFedRegPubCom").toString():"");
         } else {
           //Informacion del Título de Propiedad.
           rep.comRemplaza("[cSupZF]","");
           rep.comRemplaza("[cLoteZF]","");
           rep.comRemplaza("[cSecZF]","");
           rep.comRemplaza("[cManZF]","");
           rep.comRemplaza("[cNoZF]","");
           rep.comRemplaza("[cCalZF]","");
           rep.comRemplaza("[cKMZF]","");
           rep.comRemplaza("[cColZF]","");
           rep.comRemplaza("[cPuertoZF]","");
           rep.comRemplaza("[cMunZF]","");
           rep.comRemplaza("[cEntFedZF]","");

           //Informacion de la Escritura Publica del Título de Propiedad.
           rep.comRemplaza("[cNoEscZF]","");
           rep.comRemplaza("[cFechaZF]","");
           rep.comRemplaza("[cNoNotPubZF]","");
           rep.comRemplaza("[cCiuZF]","");
           rep.comRemplaza("[cEntFedEPZF]","");
           rep.comRemplaza("[cNotarioPublicoZF]","");

           //Informacion del Registro Publico de Comercio del Título de Propiedad.
           rep.comRemplaza("[cFecZF]","");
           rep.comRemplaza("[cFolioZF]","");
           rep.comRemplaza("[cFojaZF]","");
           rep.comRemplaza("[cToZF]","");
           rep.comRemplaza("[cSecZF]","");
           rep.comRemplaza("[cLibroZF]","");
           rep.comRemplaza("[cVolZF]","");
           rep.comRemplaza("[cCiudadZF]","");
           rep.comRemplaza("[cEntFedRPCZF]","");
         }

         if (vRegsTituloDocumentos.size()>0){
           for(int i=0;i<vRegsTituloDocumentos.size();i++){
             TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
             switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

               //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
               case 1:case 2:case 3:case 4:{
                 if (vDatosTitDoctos.get("lMaritimoTerrestre") != null &&
                     Integer.parseInt(vDatosTitDoctos.get("lMaritimoTerrestre").toString()) == 1){
                    rep.comRemplaza("[lMarTitConZF]","X");
                    rep.comRemplaza("[lFluvialTitConZF]","");
                 } else {
                    rep.comRemplaza("[lMarTitConZF]","");
                    rep.comRemplaza("[lFluvialTitConZF]","X");
                 }
                 rep.comRemplaza("[cNoTitConZF]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFecTitConZF]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cDepEmiteTitConZF]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
                 rep.comRemplaza("[cObjetoTitConZF]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
                 rep.comRemplaza("[cVigenciaTitConZF]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 rep.comRemplaza("[cSupTitConZF]",(vDatosTitDoctos.get("cSuperficieDocumento")!= null)?vDatosTitDoctos.get("cSuperficieDocumento").toString():"");
                 lEntroZOFEMAT = 1;
                 break;
               }

               //Autorizacion en Materia de Impacto Ambiental.
               case 5:case 6:{
                 rep.comRemplaza("[cNoAutIA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutIA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cDepEmiteAutIA]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
                 rep.comRemplaza("[cObjetoAutIA]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
                 rep.comRemplaza("[cVigenciaAutIA]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroImpAmb = 1;
                 break;
               }

               //Franquicia de PEMEX.
               case 7:{
                 rep.comRemplaza("[cNoFPEMEX]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaFPEMEX]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaFPEMEX]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroPEMEX = 1;
                 break;
               }

               //Autorizacion de la CONAGUA.
               case 8:{
                 rep.comRemplaza("[cNoAutCONAGUA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutCONAGUA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaAutCONAGUA]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroCONAGUA = 1;
                 break;
               }
               //Autorizacion de la SEMARNAT.
               case 9:{
                 rep.comRemplaza("[cNoAutSEMARNAT]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutSEMARNAT]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaAutSEMARNAT]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroSEMARNAT = 1;
                 break;
               }
             }
           }
         }

         if (lEntroZOFEMAT == 0){
           rep.comRemplaza("[cNoTitConZF]","");
           rep.comRemplaza("[cFecTitConZF]","");
           rep.comRemplaza("[cDepEmiteTitConZF]","");
           rep.comRemplaza("[cObjetoTitConZF]","");
           rep.comRemplaza("[cVigenciaTitConZF]","");
           rep.comRemplaza("[cSupTitConZF]","");
         }

         if (lEntroImpAmb == 0){
           rep.comRemplaza("[cNoAutIA]","");
           rep.comRemplaza("[cFechaAutIA]","");
           rep.comRemplaza("[cDepEmiteAutIA]","");
           rep.comRemplaza("[cObjetoAutIA]","");
           rep.comRemplaza("[cVigenciaAutIA]","");
         }

         if (lEntroPEMEX == 0){
           rep.comRemplaza("[cNoFPEMEX]","");
           rep.comRemplaza("[cFechaFPEMEX]","");
           rep.comRemplaza("[cVigenciaFPEMEX]","");
         }

         if (lEntroCONAGUA == 0){
           rep.comRemplaza("[cNoAutCONAGUA]","");
           rep.comRemplaza("[cFechaAutCONAGUA]","");
           rep.comRemplaza("[cVigenciaAutCONAGUA]","");

         }

         if (lEntroSEMARNAT == 0){
           rep.comRemplaza("[cNoAutSEMARNAT]","");
           rep.comRemplaza("[cFechaAutSEMARNAT]","");
           rep.comRemplaza("[cVigenciaAutSEMARNAT]","");
         }

         //Informacion de la Solicitud
         if (vRegsTituloSolicitud.size()>0){
            TVDinRep vDatosTitSolicutud = (TVDinRep) vRegsTituloSolicitud.get(0);
            rep.comRemplaza("[cNoSolicitud]",((vDatosTitSolicutud.get("iEjercicio")!= null)?vDatosTitSolicutud.get("iEjercicio").toString():"")+
                             ((vDatosTitSolicutud.get("iNumSolicitud")!= null)?vDatosTitSolicutud.get("iNumSolicitud").toString():""));
            rep.comRemplaza("[cLugarSolicitud]",((vDatosTitSolicutud.get("cCalleyNo")!= null)?vDatosTitSolicutud.get("cCalleyNo").toString():"")+
                            ((vDatosTitSolicutud.get("cColonia")!= null)?vDatosTitSolicutud.get("cColonia").toString():"")+
                            ((vDatosTitSolicutud.get("cCodPostal")!= null)?vDatosTitSolicutud.get("cCodPostal").toString():"")+
                            ((vDatosTitSolicutud.get("cDscMunicipio")!= null)?vDatosTitSolicutud.get("cDscMunicipio").toString():"")+
                            ((vDatosTitSolicutud.get("cDscEntidadFed")!= null)?vDatosTitSolicutud.get("cDscEntidadFed").toString():""));
            rep.comRemplaza("[cFechaSolicitud]",(vDatosTitSolicutud.get("dtRegistro")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitSolicutud.getDate("dtRegistro"),"/"):"");

            //Modalidad del Tramite.
            int lRegistro=0,lModificacion=0,lAmpliacion=0,lProrroga=0,lRenovacion=0;
            switch (vDatosTitSolicutud.getInt("iCveModalidad")){
              //Registro.
              case 1:{
                rep.comRemplaza("[no]"," ");
                rep.comRemplaza("[na]"," ");
                rep.comRemplaza("[np]"," ");
                rep.comRemplaza("[nr]"," ");
                lRegistro=1;
                break;
              }
              //Modificacion.
              case 2:{
                rep.comRemplaza("[no]","X");
                rep.comRemplaza("[na]"," ");
                rep.comRemplaza("[np]"," ");
                rep.comRemplaza("[nr]"," ");
                lModificacion=1;
                break;
              }
              //Ampliación.
              case 3:{
                rep.comRemplaza("[no]"," ");
                rep.comRemplaza("[na]","X");
                rep.comRemplaza("[np]"," ");
                rep.comRemplaza("[nr]"," ");
                lAmpliacion=1;
                break;
              }
              //Prorroga.
              case 4:{
                rep.comRemplaza("[no]"," ");
                rep.comRemplaza("[na]"," ");
                rep.comRemplaza("[np]","X");
                rep.comRemplaza("[nr]"," ");
                lProrroga=1;
                break;
              }
              //Renovacion.
              case 5:{
                rep.comRemplaza("[no]"," ");
                rep.comRemplaza("[na]"," ");
                rep.comRemplaza("[np]"," ");
                rep.comRemplaza("[nr]","X");
                lRenovacion=1;
                break;
              }
            }


         } else {
           rep.comRemplaza("[cNoSolicitud]","");
           rep.comRemplaza("[cLugarSolicitud]","");
           rep.comRemplaza("[cFechaSolicitud]","");
         }

         //Titulo Anterior.
         if (vRegsTituloAnterior.size()>0){
           for(int i=0;i<vRegsTituloAnterior.size();i++){
             TVDinRep vTituloAnt = (TVDinRep) vRegsTituloAnterior.get(i);

             //Titulo Anterior de Tipo Concesión.
             if(vTituloAnt.getInt("iCveTipoTitulo") == 1){
               rep.comRemplaza("[l10]","X");
               rep.comRemplaza("[l11]"," ");
               rep.comRemplaza("[l12]"," ");
             }
             //Titulo Anterior de Tipo Permiso.
             if(vTituloAnt.getInt("iCveTipoTitulo") == 2){
               rep.comRemplaza("[l10]"," ");
               rep.comRemplaza("[l11]","X");
               rep.comRemplaza("[l12]"," ");
             }
             //Titulo Anterior de Tipo Autorización.
             if(vTituloAnt.getInt("iCveTipoTitulo") == 3){
               rep.comRemplaza("[l10]"," ");
               rep.comRemplaza("[l11]"," ");
               rep.comRemplaza("[l12]","X");
             }
             rep.comRemplaza("[cNumTitAnt]", vTituloAnt.getString("cNumTitulo")!=null?vTituloAnt.getString("cNumTitulo"):"");
             rep.comRemplaza("[cFecTitAnt]", vTituloAnt.getDate("dtPublicacionDOF")!=null?Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtPublicacionDOF"),"/"):"");
             rep.comRemplaza("[cDepEmiTitAnt]","Dirección General de Puertos");
             rep.comRemplaza("[cFVigTitAnt]", vTituloAnt.getDate("dtVigenciaTitulo")!=null?Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtVigenciaTitulo"),"/"):"");
             rep.comRemplaza("[cIVigTitAnt]", vTituloAnt.getDate("dtIniVigenciaTitulo")!=null?Fecha.getFechaDDMMYYYY(vTituloAnt.getDate("dtIniVigenciaTitulo"),"/"):"");
           }
         } else {
           rep.comRemplaza("[l10]", " ");
           rep.comRemplaza("[l11]"," ");
           rep.comRemplaza("[l12]"," ");
           rep.comRemplaza("[cNumTitAnt]","");
           rep.comRemplaza("[cFecTitAnt]","");
           rep.comRemplaza("[cDepEmiTitAnt]","");
           rep.comRemplaza("[cFVigTitAnt]","");
           rep.comRemplaza("[cIVigTitAnt]","");
         }

         //Uso Publico o Particular.
         if(vDatos.getInt("iCveUsoTitulo") == 1){
           rep.comRemplaza("[U1]","X");
           rep.comRemplaza("[U2]"," ");
         }
         if(vDatos.getInt("iCveUsoTitulo") == 2){
           rep.comRemplaza("[U1]"," ");
           rep.comRemplaza("[U2]","X");
         }

         //Tipo de Concesion, Permiso o Autorización.
         int lConcesion=0,lPermiso=0,lAutorizacion=0;
         switch (vDatos.getInt("iCveClasificacion")){
           //Concesiones.
           //Puerto.
           case(1):{
             rep.comRemplaza("[cp]","X");
             rep.comRemplaza("[ct]"," ");
             rep.comRemplaza("[cn]"," ");
             rep.comRemplaza("[ci]"," ");
             rep.comRemplaza("[cp]"," ");
             rep.comRemplaza("[cv]"," ");
             lConcesion=1;
             break;
           }
           //Terminal.
           case(2):{
             rep.comRemplaza("[cp]"," ");
             rep.comRemplaza("[ct]","X");
             rep.comRemplaza("[cn]"," ");
             rep.comRemplaza("[ci]"," ");
             rep.comRemplaza("[cv]"," ");
             lConcesion=1;
             break;
           }
           //Marina.
           case(3):{
             rep.comRemplaza("[cp]"," ");
             rep.comRemplaza("[ct]"," ");
             rep.comRemplaza("[cn]","X");
             rep.comRemplaza("[ci]"," ");
             rep.comRemplaza("[cv]"," ");
             lConcesion=1;
             break;
           }
           //Instalación Portuaria.
           case(4):{
             rep.comRemplaza("[cp]"," ");
             rep.comRemplaza("[ct]"," ");
             rep.comRemplaza("[cn]"," ");
             rep.comRemplaza("[ci]","X");
             rep.comRemplaza("[cv]"," ");
             lConcesion=1;
             break;
           }
           //Permisos.
           //Embarcadero.
           case(5):{
             rep.comRemplaza("[pe]","X");
             rep.comRemplaza("[pa]"," ");
             rep.comRemplaza("[pb]"," ");
             rep.comRemplaza("[ps]"," ");
             rep.comRemplaza("[pp]"," ");
             rep.comRemplaza("[pc]"," ");
             lPermiso=1;
             break;
           }
           //Atracadero.
           case(6):{
             rep.comRemplaza("[pe]"," ");
             rep.comRemplaza("[pa]","X");
             rep.comRemplaza("[pb]"," ");
             rep.comRemplaza("[ps]"," ");
             rep.comRemplaza("[pp]"," ");
             rep.comRemplaza("[pc]"," ");
             lPermiso=1;
             break;
           }
           //Botadero.
           case(7):{
             rep.comRemplaza("[pe]"," ");
             rep.comRemplaza("[pa]"," ");
             rep.comRemplaza("[pb]","X");
             rep.comRemplaza("[ps]"," ");
             rep.comRemplaza("[pp]"," ");
             rep.comRemplaza("[pc]"," ");
             lPermiso=1;
             break;
           }
           //Similar.
           case(8):{
             rep.comRemplaza("[pe]"," ");
             rep.comRemplaza("[pa]"," ");
             rep.comRemplaza("[pb]"," ");
             rep.comRemplaza("[ps]","X");
             rep.comRemplaza("[pp]"," ");
             rep.comRemplaza("[pc]"," ");
             lPermiso=1;
             break;
           }
           //Servicio Portuario.
           case(9):{
             rep.comRemplaza("[pe]"," ");
             rep.comRemplaza("[pa]"," ");
             rep.comRemplaza("[pb]"," ");
             rep.comRemplaza("[ps]"," ");
             rep.comRemplaza("[pp]","X");
             rep.comRemplaza("[pc]"," ");
             lPermiso=1;
             break;
           }
           //Servicio Conexo.
           case(10):{
             rep.comRemplaza("[pe]"," ");
             rep.comRemplaza("[pa]"," ");
             rep.comRemplaza("[pb]"," ");
             rep.comRemplaza("[ps]"," ");
             rep.comRemplaza("[pp]"," ");
             rep.comRemplaza("[pc]","X");
             lPermiso=1;
             break;
           }
           //Autorizaciones.
           //Obra Marítima.
           case(11):{
             rep.comRemplaza("[ao]","X");
             rep.comRemplaza("[ad]"," ");
             rep.comRemplaza("[am]"," ");
             rep.comRemplaza("[aa]"," ");
             rep.comRemplaza("[ac]"," ");
             rep.comRemplaza("[ap]"," ");
             rep.comRemplaza("[ae]"," ");
             lAutorizacion=1;
             break;
           }
           //Dragado de Construcción.
           case(12):{
             rep.comRemplaza("[ao]"," ");
             rep.comRemplaza("[ad]","X");
             rep.comRemplaza("[am]"," ");
             rep.comRemplaza("[aa]"," ");
             rep.comRemplaza("[ac]"," ");
             rep.comRemplaza("[ap]"," ");
             rep.comRemplaza("[ae]"," ");
             lAutorizacion=1;
             break;
           }
           //Dragado de Mantenimiento.
           case(13):{
             rep.comRemplaza("[ao]"," ");
             rep.comRemplaza("[ad]"," ");
             rep.comRemplaza("[am]","X");
             rep.comRemplaza("[aa]"," ");
             rep.comRemplaza("[ac]"," ");
             rep.comRemplaza("[ap]"," ");
             rep.comRemplaza("[ae]"," ");
             lAutorizacion=1;
             break;
           }
           //Obra a Cargo de un AP.
           case(14):{
             rep.comRemplaza("[ao]"," ");
             rep.comRemplaza("[ad]"," ");
             rep.comRemplaza("[am]"," ");
             rep.comRemplaza("[aa]","X");
             rep.comRemplaza("[ac]"," ");
             rep.comRemplaza("[ap]"," ");
             rep.comRemplaza("[ae]"," ");
             lAutorizacion=1;
             break;
           }
           //Inicio de Construcción.
           case(15):{
             rep.comRemplaza("[ao]"," ");
             rep.comRemplaza("[ad]"," ");
             rep.comRemplaza("[am]"," ");
             rep.comRemplaza("[aa]"," ");
             rep.comRemplaza("[ac]","X");
             rep.comRemplaza("[ap]"," ");
             rep.comRemplaza("[ae]"," ");
             lAutorizacion=1;
             break;
           }
           //Inicio de Operación.
           case(16):{
             rep.comRemplaza("[ao]"," ");
             rep.comRemplaza("[ad]"," ");
             rep.comRemplaza("[am]"," ");
             rep.comRemplaza("[aa]"," ");
             rep.comRemplaza("[ac]"," ");
             rep.comRemplaza("[ap]","X");
             rep.comRemplaza("[ae]"," ");
             lAutorizacion=1;
             break;
           }
           //Cesión de Derechos.
           case(17):{
             rep.comRemplaza("[ao]"," ");
             rep.comRemplaza("[ad]"," ");
             rep.comRemplaza("[am]"," ");
             rep.comRemplaza("[aa]"," ");
             rep.comRemplaza("[ac]"," ");
             rep.comRemplaza("[ap]"," ");
             rep.comRemplaza("[ae]","X");
             lAutorizacion=1;
             break;
           }
         }

         if (lConcesion==0 ){
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[ct]"," ");
           rep.comRemplaza("[cn]"," ");
           rep.comRemplaza("[ci]"," ");
           rep.comRemplaza("[cp]"," ");
           rep.comRemplaza("[cv]"," ");
         }
         if (lPermiso==0){
           rep.comRemplaza("[pe]"," ");
           rep.comRemplaza("[pa]"," ");
           rep.comRemplaza("[pb]"," ");
           rep.comRemplaza("[ps]"," ");
           rep.comRemplaza("[pp]"," ");
           rep.comRemplaza("[pc]"," ");
         }
         if (lAutorizacion==0){
           rep.comRemplaza("[ao]"," ");
           rep.comRemplaza("[ad]"," ");
           rep.comRemplaza("[am]"," ");
           rep.comRemplaza("[aa]"," ");
           rep.comRemplaza("[ac]"," ");
           rep.comRemplaza("[ap]"," ");
           rep.comRemplaza("[ae]"," ");
         }

         //Objeto del Título y Ubicacion del Area Solicitada.
         rep.comRemplaza("[cObjetoTitulo]",(vDatos.get("cObjetoTitulo")!= null)?vDatos.get("cObjetoTitulo").toString():"");
         rep.comRemplaza("[cUbiZonFedTerrestre]",(vDatos.get("cZonaFedAfectadaTerrestre")!= null)?vDatos.get("cZonaFedAfectadaTerrestre").toString():"");
         rep.comRemplaza("[cUbiZonFedAgua]",(vDatos.get("cZonaFedAfectadaAgua")!= null)?vDatos.get("cZonaFedAfectadaAgua").toString():"");

         if (vRegsTituloUbicacion.size()>0){
            TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);
            rep.comRemplaza("[cCalleFRP]",(vDatosTitUbi.get("cCalleTitulo")!= null)?vDatosTitUbi.get("cCalleTitulo").toString():"");
            rep.comRemplaza("[cKmFRP]",(vDatosTitUbi.get("cKm")!= null)?vDatosTitUbi.get("cKm").toString():"");
            rep.comRemplaza("[cColFRP]",(vDatosTitUbi.get("cColoniaTitulo")!= null)?vDatosTitUbi.get("cColoniaTitulo").toString():"");
            rep.comRemplaza("[cPuertoFRP]",(vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
            rep.comRemplaza("[cMunFRP]",(vDatosTitUbi.get("cDscMunicipio")!= null)?vDatosTitUbi.get("cDscMunicipio").toString():"");
            rep.comRemplaza("[cEntFedFRP]",(vDatosTitUbi.get("cDscEntidadFed")!= null)?vDatosTitUbi.get("cDscEntidadFed").toString():"");

            //Ubicación del Area Solicitada.
            rep.comRemplaza("[U3]","X");

            if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1){
              rep.comRemplaza("[U4]","X");
              rep.comRemplaza("[U5]"," ");
            } else {
              rep.comRemplaza("[U4]"," ");
              rep.comRemplaza("[U5]","X");
            }
         } else {
           rep.comRemplaza("[cCalleFRP]","");
           rep.comRemplaza("[cKmFRP]","");
           rep.comRemplaza("[cColFRP]","");
           rep.comRemplaza("[cPuertoFRP]","");
           rep.comRemplaza("[cMunFRP]","");
           rep.comRemplaza("[cEntFedFRP]","");

           //Ubicación del Area Solicitada.
           rep.comRemplaza("[U3]"," ");
           rep.comRemplaza("[U4]"," ");
           rep.comRemplaza("[U5]"," ");
         }

         //Obras del Título.
         int lPropNacional= 0,lConst=0,lOper=0;
         String cObras = "";
         if (vRegsTituloObras.size()>0){
           for(int i=0;i<vRegsTituloObras.size();i++){
             TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(i);
             if (cObras.compareTo("") != 0)
               cObras = cObras  + ", " + vDatosTitObras.getString("cDscObra");
             else
               cObras = vDatosTitObras.getString("cDscObra");

             if (vDatosTitObras.getInt("lPropiedadNacional") == 1 && lPropNacional == 0){
                rep.comRemplaza("[o1]","X");
                lPropNacional = 1;
             }
             if (vDatosTitObras.getInt("lConstruccion") == 1 && lConst == 0){
                rep.comRemplaza("[o2]","X");
                lConst = 1;
             }
             if (vDatosTitObras.getInt("lOperacion") == 1 && lOper == 0){
                rep.comRemplaza("[o3]","X");
                lOper = 1;
             }
           }
           if (cObras.compareTo("")!= 0)
             cObras = cObras + ".";
           rep.comRemplaza("[cObras]",cObras);

           TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(0);
           rep.comRemplaza("[cTiempoEstimadoObra]",(vDatosTitObras.get("cTiempoEstEjecucion")!= null)?vDatosTitObras.get("cTiempoEstEjecucion").toString():"");
         }
         if (cObras.compareTo("")==0){
             rep.comRemplaza("[cObras]","");
           rep.comRemplaza("[cTiempoEstimadoObra]","");
         }
         if (lPropNacional == 0)
           rep.comRemplaza("[o1]"," ");
         if (lConst == 0)
           rep.comRemplaza("[o2]"," ");
         if (lOper == 0)
           rep.comRemplaza("[o3]"," ");

         //Servicios Portuarios Relacionados al Título.
         if (vRegsTituloServPortuarios.size()>0){
           String cServicios = "";
           for(int i=0;i<vRegsTituloServPortuarios.size();i++){
             TVDinRep vDatosTitServ = (TVDinRep) vRegsTituloServPortuarios.get(i);
             if (cServicios.compareTo("") != 0)
               cServicios = cServicios  + ", " + vDatosTitServ.getString("cDscServicioPortuario");
             else
               cServicios = vDatosTitServ.getString("cDscServicioPortuario");
           }
           if (cServicios.compareTo("")!= 0)
             cServicios = cServicios + ".";
           rep.comRemplaza("[cSerPortuarios1]",cServicios);
         } else
           rep.comRemplaza("[cSerPortuarios1]"," ");

         //Monto de Inversion.
         rep.comRemplaza("[cMontoInversion]",(vDatos.get("cMontoInversion")!= null)?vDatos.get("cMontoInversion").toString():"");

         int i1=0,i2=0,i3=0,i4=0,i5=0,i6=0,i7=0,i8=0,i9=0,i10=0;
         int i11=0,i12=0,i13=0,i14=0,i15=0,i16=0,i17=0,i18=0,i19=0,i20=0;
         int i21=0,i22=0,i23=0,i24=0,i25=0,i26=0,i27=0,i28=0,i29=0;

        //Anexos del Título.
        if (vRegsTituloAnexos.size()>0){
           for(int i=0;i<vRegsTituloAnexos.size();i++){
             TVDinRep vDatosTitAnexo = (TVDinRep) vRegsTituloAnexos.get(i);
             switch (Integer.parseInt(vDatosTitAnexo.get("iCveAnexo").toString())){
               //Personalidad.
               case 1:{
                 rep.comRemplaza("[a1]","X");
                 i1 = 1;
                 break;
               }
               //Persona Física.
               case 2:{
                 rep.comRemplaza("[a2]","X");
                 rep.comRemplaza("[a3]"," ");
                 i2 = 1;
                 break;
               }
               //Persona Moral.
               case 3:{
                 rep.comRemplaza("[a2]"," ");
                 rep.comRemplaza("[a3]","X");
                 i3 = 1;
                 break;
               }
               //Copia de la Cedula de RFC.
               case 4:{
                 rep.comRemplaza("[b1]","X");
                 i4 = 1;
                 break;
               }
               //Copia Certificada del Poder del Representante Legal.
               case 5:{
                 rep.comRemplaza("[c1]","X");
                 i5 = 1;
                 break;
               }
               //Proyecto Ejecutivo.
               case 6:{
                 rep.comRemplaza("[d1]","X");
                 i6 = 1;
                 break;
               }
               //Copia Certificada del Título de Propiedad del Terreno Colindante.
               case 7:{
                 rep.comRemplaza("[e1]","X");
                 i7 = 1;
                 break;
               }
               //Copia del Título de Concesión de la Zona Federal Marítimoterrestre
               case 8:{
                 rep.comRemplaza("[f1]","X");
                 i8 = 1;
                 break;
               }
               //Copia de la Autorización en Materia de Impacto Ambiental.
               case 9:{
                 rep.comRemplaza("[g1]","X");
                 i9 = 1;
                 break;
               }
               //Copia del oficio por el que la Secretaría de Hacienda y Crédito Público.
               case 10:{
                 rep.comRemplaza("[h1]","X");
                 i10 = 1;
                 break;
               }
               //Documentación con la cual se acredite contar con los Recursos Financieros Materiales y Humanos.
               case 11:{
                 rep.comRemplaza("[i1]","X");
                 i11 = 1;
                 break;
               }
               //Carta(s) de institución(es) bancaria(s)
               case 12:{
                 rep.comRemplaza("[i2]","X");
                 i12 = 1;
                 break;
               }
               //Línea(s) de Crédito Abiertas con Disposición Inmediata.
               case 13:{
                 rep.comRemplaza("[i3]","X");
                 i13 = 1;
                 break;
               }
               //Estados Financieros Auditados del último ejercicio.
               case 14:{
                 rep.comRemplaza("[i4]","X");
                 i14 = 1;
                 break;
               }
               //Contrato de Obra.
               case 15:{
                 rep.comRemplaza("[i5]","X");
                 i15 = 1;
                 break;
               }
               //Precontrato con Costo y Materiales a Ocupar.
               case 16:{
                 rep.comRemplaza("[i6]","X");
                 i16 = 1;
                 break;
               }
               //Especificación de los Materiales de Construcción con que cuentan.
               case 17:{
                 rep.comRemplaza("[i7]","X");
                 i17 = 1;
                 break;
               }
               //De ser embarcaciones, se deberá anexar Copia del Certificado de Matrícula y de Seguridad Marítima vigentes.
               case 18:{
                 rep.comRemplaza("[i8]","X");
                 i18 = 1;
                 break;
               }
               //Fotografías del equipo.
               case 19:{
                 rep.comRemplaza("[i9]","X");
                 i19 = 1;
                 break;
               }
               //Comprobante de pago al Instituto Mexicano del Seguro Social.
               case 20:{
                 rep.comRemplaza("[ii1]","X");
                 i20 = 1;
                 break;
               }
               //Ultima Nómina.
               case 21:{
                 rep.comRemplaza("[ii2]","X");
                 i21 = 1;
                 break;
               }
               //Precontrato o Carta de Intención de Obra.
               case 22:{
                 rep.comRemplaza("[ii3]","X");
                 i22 = 1;
                 break;
               }
               //En caso de Servicios Portuarios, propuesta de Plantilla de Personal.
               case 23:{
                 rep.comRemplaza("[ii4]","X");
                 i23 = 1;
                 break;
               }
               //Compromisos de Calidad.
               case 24:{
                 rep.comRemplaza("[j1]","X");
                 i24 = 1;
                 break;
               }
               //Metas de Productividad Calendarizadas.
               case 25:{
                 rep.comRemplaza("[k1]","X");
                 i25 = 1;
                 break;
               }
               //Características Técnicas.
               case 26:{
                 rep.comRemplaza("[l1]","X");
                 i26 = 1;
                 break;
               }
               //Plano(s) aprobado(s) y/o croquis del área donde se prestara el servicio(s).
               case 27:{
                 rep.comRemplaza("[n1]","X");
                 i27 = 1;
                 break;
               }
               //Dentro del Recinto Portuario.
               case 28:{
                 rep.comRemplaza("[n2]","X");
                 rep.comRemplaza("[n3]"," ");
                 i28 = 1;
                 break;
               }
               //Fuera del Recinto Portuario.
               case 29:{
                 rep.comRemplaza("[n2]"," ");
                 rep.comRemplaza("[n3]","X");
                 i29 = 1;
                 break;
               }
             }
           }
        }
        if (i1==0) rep.comRemplaza("[a1]"," ");
        if (i2==0) rep.comRemplaza("[a2]"," ");
        if (i3==0) rep.comRemplaza("[a3]"," ");
        if (i4==0) rep.comRemplaza("[b1]"," ");
        if (i5==0) rep.comRemplaza("[c1]"," ");
        if (i6==0) rep.comRemplaza("[d1]"," ");
        if (i7==0) rep.comRemplaza("[e1]"," ");
        if (i8==0) rep.comRemplaza("[f1]"," ");
        if (i9==0) rep.comRemplaza("[g1]"," ");
        if (i10==0) rep.comRemplaza("[h1]"," ");
        if (i11==0) rep.comRemplaza("[i1]"," ");
        if (i12==0) rep.comRemplaza("[i2]"," ");
        if (i13==0) rep.comRemplaza("[i3]"," ");
        if (i14==0) rep.comRemplaza("[i4]"," ");
        if (i15==0) rep.comRemplaza("[i5]"," ");
        if (i16==0) rep.comRemplaza("[i6]"," ");
        if (i17==0) rep.comRemplaza("[i7]"," ");
        if (i18==0) rep.comRemplaza("[i8]"," ");
        if (i19==0) rep.comRemplaza("[i9]"," ");
        if (i20==0) rep.comRemplaza("[ii1]"," ");
        if (i21==0) rep.comRemplaza("[ii2]"," ");
        if (i22==0) rep.comRemplaza("[ii3]"," ");
        if (i23==0) rep.comRemplaza("[ii4]"," ");
        if (i24==0) rep.comRemplaza("[j1]"," ");
        if (i25==0) rep.comRemplaza("[k1]"," ");
        if (i26==0) rep.comRemplaza("[l1]"," ");
        if (i27==0) rep.comRemplaza("[n1]"," ");
        if (i28==0) rep.comRemplaza("[n2]"," ");
        if (i29==0) rep.comRemplaza("[n3]"," ");

      //Fecha de Expedición.
      rep.comRemplaza("[cFecExpedicion]",Fecha.getFechaDDMMYYYY(Fecha.TodaySQL(),"/"));
      rep.comRemplaza("[cFecVigenciaTit]",vDatos.getDate("dtVigenciaTitulo")!=null?Fecha.getFechaDDMMYYYY(vDatos.getDate("dtVigenciaTitulo"),"/"):"");
      rep.comRemplaza("[cRepLegalCompleto]",cRepLegalCompleto);

    } else {
      //System.out.print("No Encontro Datos");
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return rep.getEtiquetas(true);
  }

  public StringBuffer GenConcesionAPI(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    Vector vRegsEscrituraConst = new Vector();
    Vector vRegsAcredRepLegal = new Vector();
    Vector vRegsTituloProp = new Vector();
    Vector vRegsTituloUbicacion = new Vector();
    Vector vRegsTituloSolicitud = new Vector();
    Vector vRegsDelimitaPuerto = new Vector();

    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TFechas Fecha = new TFechas("44");

    TWord rep = new TWord();
    rep.iniciaReporte();
    int iCveTituloFiltro = Integer.parseInt(cQuery);

    String cRepLegalCompleto="";
    String cDirCompletaSolic = "";
    
    String cCalleDN  = "";
    String cNoExtDN  = ""; 
    String cNoIntDN  = ""; 
    String cColDN    = "";
    String cDelDN    = ""; 
    String cCiudadDN = "";
    String cCodPosDN = "";
    String cEntFedDN = "";
    String cNumEscrituraPub = "";
    String cFechaEscrituraPub = "";
    String cNumNotarioRepLegal = "";
    String cMunicipioNotarioRepLegal = "";
    String cNombreNotarioRepLegal = "";
    String cNomNotario = "";
    String cApPatNotario = "";
    String cApMatNotario = "";
    String cDirCompletaRepLegal = "";
    double dTotalAgua = 0;
    double dTotalTierra = 0;

    cQuery = "select CPATitulo.iCveTitulo, " +
             "GRLPersona.iTipoPersona, " +
             "GRLPersona.cNomRazonSocial, " +
             "GRLPersona.cApPaterno, " +
             "GRLPersona.cApMaterno, " +
             "GRLDomicilio.cCalle, " +
             "GRLDomicilio.cNumExterior, " +
             "GRLDomicilio.cNumInterior, " +
             "GRLDomicilio.cColonia, " +
             "GRLDomicilio.cCodPostal, " +
             "GRLDomicilio.cTelefono, " +
             "GRLEntidadFed.cNombre as cNomEntidadFed, " +
             "GRLMunicipio.cNombre as cNomMunicipio, " +
             "GRLLocalidad.cNombre as cNomLocalidad, " +
             "CPATIPOPARTACC.CDSCTIPOPARTACC, " +
             "(YEAR(DTVIGENCIATITULO) - YEAR(DTINIVIGENCIATITULO)) AS IANIOSVIGENCIA, " +
             "CPATITULO.CZONAFEDTOTALTERRESTRE, "+
             "CPATITULO.CZONAFEDTOTALAGUA "+
             "from CPATitulo " +
             "join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo " +
             "join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
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
             "left join CPATIPOPARTACC on CPATIPOPARTACC.ICVETIPOPARTACC = CPATITULO.ICVETIPOPARTACC " +
             "where CPATitulo.iCveTitulo = " + iCveTituloFiltro;

    try{
      vRegs = super.FindByGeneric("", cQuery, dataSourceName);
    }catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    if(vRegs.size() > 0){
         TVDinRep vDatos = (TVDinRep) vRegs.get(0);

         int iCveTitulo = Integer.parseInt(vDatos.get("iCveTitulo").toString());

         // Escritura Constitutiva de las Personas Morales.
         try{
           vRegsEscrituraConst = super.FindByGeneric("",
           " select CPAEscConsMov.iNumMovimiento, " +
           " CPAEscConsMov.cNumEscrituraConst, " +
           " CPAEscConsMov.dtEscrituraMov, " +
           " CPAEscConsMov.cNumNotariaPub, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " GRLEntidadFed.cNombre as cDscEntidadFed, " +
           " GRLMunicipio.cNombre as cDscMunicipio " +
           " from CPATitulo " +
           " join CPAEscConsMov on CPAEscConsMov.iCveTitulo = CPATitulo.iCveTitulo " +
           "  and CPAEscConsMov.iCveTipoMovEscritura = 1 " +
           " left join GRLPersona on GRLPersona.iCvePersona = CPAEscConsMov.iCveNotarioPublico " +
           " left join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
           " and GRLDomicilio.lPredeterminado = 1 " +
           " left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
          ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Acreditacion del Representante Legal.
         try{
           vRegsAcredRepLegal = super.FindByGeneric("",
           " select CPAAcreditacionRepLegal.dtAcredRepLegal, " +
           " CPAAcreditacionRepLegal.cNumNotPubAcred, " +
           " CPAAcreditacionRepLegal.cDocumento, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " Notario.cNomRazonSocial as cNomNotario, " +
           " Notario.cApPaterno as cApPatNotario, " +
           " Notario.cApMaterno as cApMatNotario, " +
           " GRLPais.cNombre as cNomPais, " +
           " GRLEntidadFed.cNombre as cNomEntidadFed, " +
           " GRLMunicipio.cNombre as cNomMunicipio, " +
           " GRLLocalidad.cNombre as cNomLocalidad, " +
           " GRLDOMICILIO.CCALLE as cCalleRepLeg, " +
           " GRLDOMICILIO.CNUMEXTERIOR as cNumExtRepLeg, " +
           " GRLDOMICILIO.CNUMINTERIOR as cNumIntRepLeg, " +
           " GRLDOMICILIO.CCOLONIA as cColRepLeg, " +
           " GRLMUNICIPIO.CNOMBRE as cMunRepLegal, " +
           " GRLLOCALIDAD.CNOMBRE as cCiudadRepLeg, " +
           " GRLENTIDADFED.CNOMBRE as cEntidadRepLeg " +
           " from CPATitulo " +
           " join CPAAcreditacionRepLegal on CPAAcreditacionRepLegal.iCveTitulo = CPATitulo.iCveTitulo " +
           " join GRLPersona on GRLPersona.iCvePersona = CPAAcreditacionRepLegal.iCveRepLegal " +
           " join GRLPersona Notario on Notario.iCvePersona = CPAAcreditacionRepLegal.iCveNotarioPubAcred " +
           " join GRLDomicilio on GRLDomicilio.iCvePersona = Notario.iCvePersona " +
           " and GRLDomicilio.lPredeterminado = 1 " +
           " join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
           " join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Ubicacion del Area Solicitada
         try{
           vRegsTituloUbicacion = super.FindByGeneric("",
               " select " +
               " GRLPuerto.iCvePuerto, " +
               " GRLPuerto.cDscPuerto, " +
               " GRLEntidadFed.cNombre as cDscEntidadFed, " +
               " GRLMunicipio.cNombre as cDscMunicipio, " +
               " GRLLocalidad.cNombre as cDscLocalidad " +
               " from CPATitulo " +
               " join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
               " and CPATituloUbicacion.ICVEPUERTO > 0 " +
               " left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
               " left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLPuerto.iCvePais " +
               " and GRLEntidadFed.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
               " left join GRLMunicipio on GRLMunicipio.iCvePais = GRLPuerto.iCvePais " +
               " and GRLMunicipio.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
               " and GRLMunicipio.iCveMunicipio = GRLPuerto.iCveMunicipio " +
               " left join GRLLocalidad on GRLLocalidad.iCvePais = GRLPuerto.iCvePais " +
               " and GRLLocalidad.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
               " and GRLLocalidad.iCveMunicipio = GRLPuerto.iCveMunicipio " +
               " and GRLLocalidad.iCveLocalidad = GRLPuerto.iCveLocalidad " +
               " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Solicitud
         try{
           vRegsTituloSolicitud = super.FindByGeneric("",
               " select CPASolTitulo.iEjercicio, " +
               " CPASolTitulo.iNumSolicitud, " +
               " TRACatTramite.cDscBreve, " +
               " TRAModalidad.cDscModalidad, " +
               " date(TRARegSolicitud.tsRegistro) as dtRegistro, " +
               " GRLOficina.cCalleyNo, " +
               " GRLOficina.cColonia, " +
               " GRLOficina.cCodPostal, " +
               " GRLOficina.cTelefono, " +
               " GRLOficina.cCorreoE, " +
               " GRLPais.cNombre as cDscPais, " +
               " GRLEntidadFed.cNombre as cDscEntidadFed, " +
               " GRLMunicipio.cNombre as cDscMunicipio, " +
               " TRACatTramite.iCveTramite, " +
               " TRAModalidad.iCveModalidad " +
               " from CPATitulo " +
               " join CPASolTitulo on CPASolTitulo.iCveTitulo = CPATitulo.iCveTitulo " +
               " join TRARegSolicitud on TRARegSolicitud.iEjercicio = CPASolTitulo.iEjercicio " +
               " and TRARegSolicitud.iNumSolicitud = CPASolTitulo.iNumSolicitud " +
               " join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite " +
               " join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad " +
               " join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina " +
               " join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais "+
               " join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais " +
               " and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais " +
               " and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
               " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         rep.iniciaReporte();


         if (vDatos.getInt("iTipoPersona")== 1){ // Persona Física
           //Para quien es la Concesion.
           rep.comRemplaza("[cSolicitante]",((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                                     ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                                     ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():""));
           cRepLegalCompleto = ((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                               ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                               ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():"");

         }

         if (vDatos.getInt("iTipoPersona")== 2){ // Persona Moral

           //Para quien es la Concesion.
           rep.comRemplaza("[cSolicitante]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"");

           if(vRegsEscrituraConst.size()>0){
             TVDinRep vDatosEscConst = (TVDinRep) vRegsEscrituraConst.get(0);

             //Valores de las Personas Morales con su Escritura Constitutiva. NoEscConstitutiva
             rep.comRemplaza("[cNumEscrituraConst]",(vDatosEscConst.get("cNumEscrituraConst")!= null)?vDatosEscConst.get("cNumEscrituraConst").toString():"");
             rep.comRemplaza("[cFechaEscrituraConst]",(vDatosEscConst.get("dtEscrituraMov")!= null)?Fecha.getDateSPN(vDatosEscConst.getDate("dtEscrituraMov")).toLowerCase():"");
             rep.comRemplaza("[cNotariaEscrituraConst]",(vDatosEscConst.get("cNumNotariaPub")!= null)?vDatosEscConst.get("cNumNotariaPub").toString():"");
             rep.comRemplaza("[cCiudad]",((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
             rep.comRemplaza("[cEntidadFed]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");
             String cNomNotPub = (vDatosEscConst.get("cNomRazonSocial")!= null)?vDatosEscConst.get("cNomRazonSocial").toString():"";
             String cApPatNotPub = (vDatosEscConst.get("cApPaterno")!= null)?vDatosEscConst.get("cApPaterno").toString():"";
             String cApMatNotPub = (vDatosEscConst.get("cApMaterno")!= null)?vDatosEscConst.get("cApMaterno").toString():"";
             
             rep.comRemplaza("[cNotarioEscrituraConst]",cNomNotPub+ " "+cApPatNotPub+" "+cApMatNotPub);

           }

           if(vRegsAcredRepLegal.size()> 0){
             TVDinRep vDatosRepLegal = (TVDinRep) vRegsAcredRepLegal.get(0);

             //Información del Representante Legal.
             cRepLegalCompleto = ((vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");
             
             cNumEscrituraPub = ((vDatosRepLegal.get("cDocumento")!= null)?vDatosRepLegal.get("cDocumento").toString():"");
             cFechaEscrituraPub = (vDatosRepLegal.get("dtAcredRepLegal")!= null)?Fecha.getDateSPN(vDatosRepLegal.getDate("dtAcredRepLegal")).toLowerCase():"";
             cNumNotarioRepLegal = ((vDatosRepLegal.get("cDocumento")!= null)?vDatosRepLegal.get("cDocumento").toString():"");
             cMunicipioNotarioRepLegal = ((vDatosRepLegal.get("cNomMunicipio")!= null)?vDatosRepLegal.get("cNomMunicipio").toString():"");
             
             cNomNotario   = ((vDatosRepLegal.get("cNomNotario")!= null)?vDatosRepLegal.get("cNomNotario").toString():"");
             cApPatNotario = ((vDatosRepLegal.get("cApPatNotario")!= null)?vDatosRepLegal.get("cApPatNotario").toString():"");
             cApMatNotario = ((vDatosRepLegal.get("cApMatNotario")!= null)?vDatosRepLegal.get("cApMatNotario").toString():"");
             
             cDirCompletaRepLegal =((vDatosRepLegal.get("cCalleRepLeg")!= null)?vDatosRepLegal.get("cCalleRepLeg").toString():"");
             cDirCompletaRepLegal+=", " + ((vDatosRepLegal.get("cNumExtRepLeg")!= null)?vDatosRepLegal.get("cNumExtRepLeg").toString():"");
             cDirCompletaRepLegal+=", " + ((vDatosRepLegal.get("cNumIntRepLeg")!= null)?vDatosRepLegal.get("cNumIntRepLeg").toString():"");
             cDirCompletaRepLegal+=", " + ((vDatosRepLegal.get("cColRepLeg")!= null)?vDatosRepLegal.get("cColRepLeg").toString():"");
             cDirCompletaRepLegal+=", " + ((vDatosRepLegal.get("cCiudadRepLeg")!= null)?vDatosRepLegal.get("cCiudadRepLeg").toString():"");
             cDirCompletaRepLegal+=", " + ((vDatosRepLegal.get("cEntidadRepLeg")!= null)?vDatosRepLegal.get("cEntidadRepLeg").toString():"");
             
             cNombreNotarioRepLegal =  cNomNotario + " " + cApPatNotario + " " + cApMatNotario;

           }else
             cRepLegalCompleto = "";

         }else{
           rep.comRemplaza("[cSolicitante]","");
           cRepLegalCompleto = "";

           //Valores de las Personas Morales con su Escritura Constitutiva.
           rep.comRemplaza("[cNumEscrituraConst]","");
           rep.comRemplaza("[cFechaEscConstitutiva]","");
           rep.comRemplaza("[cNotariaEscrituraConst]","");
           rep.comRemplaza("[cCiudad]","");
           rep.comRemplaza("[cEntidadFed]","");
           rep.comRemplaza("[cNomNotPub]","");
           rep.comRemplaza("[cApPatNotPub]","");
           rep.comRemplaza("[cApMatNotPub]","");

         }

         //Informacion del Domicilio para Recibir Notificaciones.
         rep.comRemplaza("[cCalleDN]",(vDatos.get("cCalle")!= null)?vDatos.get("cCalle").toString():"");
         rep.comRemplaza("[cNoExtDN]",(vDatos.get("cNumExterior")!= null)?vDatos.get("cNumExterior").toString():"");
         rep.comRemplaza("[cNoIntDN]",(vDatos.get("cNumInterior")!= null)?vDatos.get("cNumInterior").toString():"");
         rep.comRemplaza("[cColDN]",(vDatos.get("cColonia")!= null)?vDatos.get("cColonia").toString():"");
         rep.comRemplaza("[cDelDN]",(vDatos.get("cNomMunicipio")!= null)?vDatos.get("cNomMunicipio").toString():"");
         rep.comRemplaza("[cCiudadDN]",(vDatos.get("cNomLocalidad")!= null)?vDatos.get("cNomLocalidad").toString():"");
         rep.comRemplaza("[cCodPosDN]",(vDatos.get("cCodPostal")!= null)?vDatos.get("cCodPostal").toString():"");
         rep.comRemplaza("[cEntFedDN]",(vDatos.get("cNomEntidadFed")!= null)?vDatos.get("cNomEntidadFed").toString():"");
         
         cCalleDN  = (vDatos.get("cCalle")!= null)?vDatos.get("cCalle").toString():"";
         cNoExtDN  = (vDatos.get("cNumExterior")!= null)?vDatos.get("cNumExterior").toString():"";
         cNoIntDN  = (vDatos.get("cNumInterior")!= null)?vDatos.get("cNumInterior").toString():"";
         cColDN    = (vDatos.get("cColonia")!= null)?vDatos.get("cColonia").toString():"";
         cDelDN    = (vDatos.get("cNomMunicipio")!= null)?vDatos.get("cNomMunicipio").toString():"";
         cCiudadDN = (vDatos.get("cNomLocalidad")!= null)?vDatos.get("cNomLocalidad").toString():"";
         cCodPosDN = (vDatos.get("cCodPostal")!= null)?vDatos.get("cCodPostal").toString():"";
         cEntFedDN = (vDatos.get("cNomEntidadFed")!= null)?vDatos.get("cNomEntidadFed").toString():"";
         
         cDirCompletaSolic = cCalleDN + ", " + cNoExtDN + ", " + cNoIntDN + 
         ", Col. " + cColDN + ", " +	cCiudadDN +  ", " + cEntFedDN;

         rep.comRemplaza("[cDirCompletoSolicitante]",cDirCompletaSolic);
         
         rep.comRemplaza("[cParticipacionAccionaria]",(vDatos.get("CDSCTIPOPARTACC")!= null)?vDatos.get("CDSCTIPOPARTACC").toString():"");
         rep.comRemplaza("[cAniosVigencia]",(vDatos.get("IANIOSVIGENCIA")!= null)?vDatos.get("IANIOSVIGENCIA").toString():"");
         
         dTotalAgua   = vDatos.getInt("CZONAFEDTOTALTERRESTRE");
         dTotalTierra = vDatos.getInt("CZONAFEDTOTALAGUA");

         rep.comRemplaza("[iTotalAguaTierra]",(dTotalAgua+dTotalTierra)+"");
         
         //Informacion de la Solicitud
         if (vRegsTituloSolicitud.size()>0){
            TVDinRep vDatosTitSolicutud = (TVDinRep) vRegsTituloSolicitud.get(0);
            rep.comRemplaza("[cLugarSolicitud]",((vDatosTitSolicutud.get("cCalleyNo")!= null)?vDatosTitSolicutud.get("cCalleyNo").toString():"")+
                            ((vDatosTitSolicutud.get("cColonia")!= null)?vDatosTitSolicutud.get("cColonia").toString():"")+
                            ((vDatosTitSolicutud.get("cCodPostal")!= null)?vDatosTitSolicutud.get("cCodPostal").toString():"")+
                            ((vDatosTitSolicutud.get("cDscMunicipio")!= null)?vDatosTitSolicutud.get("cDscMunicipio").toString():"")+
                            ((vDatosTitSolicutud.get("cDscEntidadFed")!= null)?vDatosTitSolicutud.get("cDscEntidadFed").toString():""));
            rep.comRemplaza("[cFechaSolicitud]",(vDatosTitSolicutud.get("dtRegistro")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitSolicutud.getDate("dtRegistro"),"/"):"");

            //Modalidad del Tramite.
            int lRegistro=0,lModificacion=0,lAmpliacion=0,lProrroga=0,lRenovacion=0;

         } else {
           rep.comRemplaza("[cLugarSolicitud]","");
           rep.comRemplaza("[cFechaSolicitud]","");
         }

         String cPuertosUbicacion = "", cEntidadFedPuertos = ""; 
         String cDelimitaPuertos = "", cMunicipioPuerto = "";
         for(int i=0;i<vRegsTituloUbicacion.size();i++){
            TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(i);

            if(cPuertosUbicacion == "")
              cPuertosUbicacion = ((vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
            else
              cPuertosUbicacion = cPuertosUbicacion + ", " + ((vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");

            cEntidadFedPuertos = ((vDatosTitUbi.get("cDscEntidadFed")!= null)?vDatosTitUbi.get("cDscEntidadFed").toString():"");
            cMunicipioPuerto   = ((vDatosTitUbi.get("cDscMunicipio")!= null)?vDatosTitUbi.get("cDscMunicipio").toString():"");

            //Informacion de la Delimitación del Recinto Portuario
            try{
              vRegsDelimitaPuerto = super.FindByGeneric("",
                  " SELECT RPO.DTACUERDO,PTO.CDSCPUERTO,EF.CNOMBRE AS CDSCENTFEDERATIVA,MRE.CVALORSUPERFICIE,UM.CDSCUNIDADMEDIDA " +
                  " FROM RPORECINTOPUERTO RPO " +
                  " JOIN GRLPUERTO PTO ON PTO.ICVEPUERTO = RPO.ICVEPUERTO " +
                  " LEFT JOIN GRLEntidadFed EF ON EF.iCvePais = PTO.iCvePais " +
                  " AND EF.iCveEntidadFed = PTO.iCveEntidadFed " +
                  " JOIN RPOSUPXRECINTO SRE ON SRE.ICVEMOVRECINTOPORTUARIO = RPO.ICVEMOVRECINTOPORTUARIO " +
                  " AND SRE.ITIPOSUPERFICIE = 3 " +
                  " JOIN RPOMEDSUPXRECINTO MRE ON MRE.ICVEMOVRECINTOPORTUARIO = SRE.ICVEMOVRECINTOPORTUARIO " +
                  " AND MRE.INUMSUPERFICIE = SRE.INUMSUPERFICIE " +
                  " AND MRE.ICVEUNIDADMEDIDA = 1 " +
                  " JOIN VEHUNIDADMEDIDA UM ON UM.ICVEUNIDADMEDIDA = MRE.ICVEUNIDADMEDIDA " +
                  " WHERE RPO.ICVEPUERTO = " + vDatosTitUbi.getInt("iCvePuerto") +
                  "  AND RPO.LRECINTOMOVPRINCIPAL = 1 " +
                  "  AND RPO.ICVEMODRECINTO = 1 " +
                  "  AND RPO.DTACUERDO IS NOT NULL ",
              dataSourceName);
            }catch(SQLException ex){
              ex.printStackTrace();
             cMensaje = ex.getMessage();
            }catch(Exception ex2){
              ex2.printStackTrace();
             cMensaje += ex2.getMessage();
            }

            if(vRegsDelimitaPuerto.size()>0){
              TVDinRep vDatosDelimitaPuerto = (TVDinRep) vRegsDelimitaPuerto.get(0);
              cDelimitaPuertos = cDelimitaPuertos + " el " + Fecha.getDateSPN(vDatosDelimitaPuerto.getDate("DTACUERDO")).toLowerCase() +
                                 ", se determinó el recinto portuario de " + ((vDatosDelimitaPuerto.get("cDscPuerto")!= null)?vDatosDelimitaPuerto.get("cDscPuerto").toString():"") +
                                 ", estado de " + vDatosDelimitaPuerto.getString("CDSCENTFEDERATIVA") + ", con una superficie total de " + vDatosDelimitaPuerto.getString("CVALORSUPERFICIE") + " " +
                                 vDatosDelimitaPuerto.getString("CDSCUNIDADMEDIDA") + "S,";
            }

         }

         rep.comRemplaza("[cEdoPuerto]",cEntidadFedPuertos);
         rep.comRemplaza("[cPuerto]",cPuertosUbicacion);
         rep.comRemplaza("[cMunPuerto]",cMunicipioPuerto);
         rep.comRemplaza("[cDelimitaPuertos]",cDelimitaPuertos);

      //Fecha de Expedición.
      rep.comRemplaza("[cFecExpedicion]",Fecha.getFechaDDMMYYYY(Fecha.TodaySQL(),"/"));
      rep.comRemplaza("[cRepLegal]",cRepLegalCompleto);
      rep.comRemplaza("[cMunicipioNotarioRepLegal]",cMunicipioNotarioRepLegal);
      rep.comRemplaza("[cNombreNotarioRepLegal]",cNombreNotarioRepLegal);
      rep.comRemplaza("[cDirCompletaRepLegal]",cDirCompletaRepLegal);
      
      rep.comRemplaza("[cNumEscrituraPub]",cNumEscrituraPub);
      rep.comRemplaza("[cFechaEscrituraPub]",cFechaEscrituraPub);
      rep.comRemplaza("[cNumNotarioRepLegal]",cNumNotarioRepLegal);

      rep.comRemplaza("[CDSCTIPOPARTACC]",vDatos.getString("CDSCTIPOPARTACC")!=null?vDatos.getString("CDSCTIPOPARTACC"):"");

    } else {
      //System.out.print("No Encontraron Datos");
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return rep.getEtiquetas(true);
  }

  public StringBuffer GenConcesionNOAPI(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    Vector vRegsEscrituraConst = new Vector();
    Vector vRegsAcredRepLegal = new Vector();
    Vector vRegsTituloProp = new Vector();
    Vector vRegsTituloUbicacion = new Vector();
    Vector vRegsTituloSolicitud = new Vector();
    Vector vRegsDelimitaPuerto = new Vector();
    Vector vRegsTituloDocumentos = new Vector();
    Vector vRegsTituloPlano = new Vector();

    int lEntroZOFEMAT = 0,lEntroImpAmb = 0,lEntroPEMEX = 0,lEntroCONAGUA = 0,lEntroSEMARNAT = 0;
    int iInicioVigenciaTitulo = 0, iFinVigenciatitulo = 0, iVigenciaTitulo = 0;

    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TFechas Fecha = new TFechas("44");

    TWord rep = new TWord();
    rep.iniciaReporte();
    int iCveTituloFiltro = Integer.parseInt(cQuery);

    String cRepLegalCompleto="";

    cQuery =
        "select CPATitulo.iCveTitulo, " +
        "CPATitulo.cMontoInversion, " +
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
        "CPATitulo.cZonaFedAfectadaTerrestre, " +
        "CPATitulo.cZonaFedAfectadaAgua, " +
        "CPATIPOPARTACC.CDSCTIPOPARTACC, " +
        "CPAUSOTITULO.CDSCUSOTITULO, " +
        "(YEAR(DTVIGENCIATITULO) - YEAR(DTINIVIGENCIATITULO)) AS IANIOSVIGENCIA " +
        "from CPATitulo " +
        "join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo " +
        "join CPAUSOTITULO on CPAUSOTITULO.ICVEUSOTITULO = CPATitulo.ICVEUSOTITULO " +
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
        "left join CPATIPOPARTACC on CPATIPOPARTACC.ICVETIPOPARTACC = CPATITULO.ICVETIPOPARTACC " +
        "where CPATitulo.iCveTitulo = " + iCveTituloFiltro;

    try{
      vRegs = super.FindByGeneric("", cQuery, dataSourceName);
    }catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    if(vRegs.size() > 0){
         TVDinRep vDatos = (TVDinRep) vRegs.get(0);

         int iCveTitulo = Integer.parseInt(vDatos.get("iCveTitulo").toString());

         // Escritura Constitutiva de las Personas Morales.
         try{
           vRegsEscrituraConst = super.FindByGeneric("",
           " select CPAEscConsMov.iNumMovimiento, " +
           " CPAEscConsMov.cNumEscrituraConst, " +
           " CPATipoMovEscritura.cDscTipoMovEscritura, " +
           " CPAEscConsMov.dtEscrituraMov, " +
           " CPAEscConsMov.cNumNotariaPub, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " CPARegPubComercio.dtRegPubComercio, " +
           " CPARegPubComercio.cNumRegPubComercio, " +
           " CPARegPubComercio.cNumFoja, " +
           " CPARegPubComercio.cNumTomo, " +
           " CPARegPubComercio.cNumSeccion, " +
           " CPARegPubComercio.cNumLibro, " +
           " CPARegPubComercio.cNumVolumen, " +
           " CPARegPubComercio.cNumSeccion, " +
           " GRLPais.cNombre as cDscPais, " +
           " GRLEntidadFed.cNombre as cDscEntidadFed, " +
           " GRLMunicipio.cNombre as cDscMunicipio, " +
           " GRLLocalidad.cNombre as cDscLocalidad " +
           " from CPATitulo " +
           " join CPAEscConsMov on CPAEscConsMov.iCveTitulo = CPATitulo.iCveTitulo " +
           " and CPAEscConsMov.iCveTipoMovEscritura = 1 " +
           " join CPATipoMovEscritura on CPATipoMovEscritura.iCveTipoMovEscritura = CPAEscConsMov.iCveTipoMovEscritura " +
           " left join CPARegPubComercio on CPARegPubComercio.iCveTitulo = CPAEscConsMov.iCveTitulo " +
           " and CPARegPubComercio.iNumMovimiento = CPAEscConsMov.iNumMovimiento " +
           " left join GRLPersona on GRLPersona.iCvePersona = CPAEscConsMov.iCveNotarioPublico " +
           " left join GRLPais on GRLPais.iCvePais = CPARegPubComercio.iCvePais " +
           " left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " left join GRLMunicipio on GRLMunicipio.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
           " left join GRLLocalidad on GRLLocalidad.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = CPARegPubComercio.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
          ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Acreditacion del Representante Legal.
         try{
           vRegsAcredRepLegal = super.FindByGeneric("",
           " select CPAAcreditacionRepLegal.dtAcredRepLegal, " +
           " CPAAcreditacionRepLegal.cNumNotPubAcred, " +
           " CPAAcreditacionRepLegal.cDocumento, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " Notario.cNomRazonSocial as cNomNotario, " +
           " Notario.cApPaterno as cApPatNotario, " +
           " Notario.cApMaterno as cApMatNotario, " +
           " GRLPais.cNombre as cNomPais, " +
           " GRLEntidadFed.cNombre as cNomEntidadFed, " +
           " GRLMunicipio.cNombre as cNomMunicipio, " +
           " GRLLocalidad.cNombre as cNomLocalidad " +
           " from CPATitulo " +
           " join CPAAcreditacionRepLegal on CPAAcreditacionRepLegal.iCveTitulo = CPATitulo.iCveTitulo " +
           " join GRLPersona on GRLPersona.iCvePersona = CPAAcreditacionRepLegal.iCveRepLegal " +
           " join GRLPersona Notario on Notario.iCvePersona = CPAAcreditacionRepLegal.iCveNotarioPubAcred " +
           " join GRLDomicilio on GRLDomicilio.iCvePersona = Notario.iCvePersona " +
           " and GRLDomicilio.lPredeterminado = 1 " +
           " join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
           " join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Ubicacion del Area Solicitada
         try{
           vRegsTituloUbicacion = super.FindByGeneric("",
               " select CPATituloUbicacion.lDentroRecintoPort, " +
               " CPATituloUbicacion.cCalleTitulo, " +
               " CPATituloUbicacion.cKm, " +
               " CPATituloUbicacion.cColoniaTitulo, " +
               " GRLPuerto.iCvePuerto, " +
               " GRLPuerto.cDscPuerto, " +
               " UPais.cNombre as cUDscPais, " +
               " UEntFed.cNombre as cUDscEntidadFed, " +
               " UMun.cNombre as cUDscMunicipio, " +
               " ULoc.cNombre as cUDscLocalidad, " +
               " PPais.cNombre as cPDscPais, " +
               " PEntFed.cNombre as cPDscEntidadFed, " +
               " PMun.cNombre as cPDscMunicipio, " +
               " PLoc.cNombre as cPDscLocalidad " +
               " from CPATitulo " +
               " join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
               " left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
               " left join GRLPais UPais on UPais.iCvePais = CPATituloUbicacion.iCvePais " +
               " left join GRLEntidadFed UEntFed on UEntFed.iCvePais = CPATituloUbicacion.iCvePais " +
               " and UEntFed.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
               " left join GRLMunicipio UMun on UMun.iCvePais = CPATituloUbicacion.iCvePais " +
               " and UMun.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
               " and UMun.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
               " left join GRLLocalidad ULoc on ULoc.iCvePais = CPATituloUbicacion.iCvePais " +
               " and ULoc.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
               " and ULoc.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
               " and ULoc.iCveLocalidad = CPATituloUbicacion.iCveLocalidad " +
               " left join GRLPais PPais on PPais.iCvePais = GRLPuerto.iCvePais " +
               " left join GRLEntidadFed PEntFed on PEntFed.iCvePais = GRLPuerto.iCvePais " +
               " and PEntFed.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
               " left join GRLMunicipio PMun on PMun.iCvePais = GRLPuerto.iCvePais " +
               " and PMun.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
               " and PMun.iCveMunicipio = GRLPuerto.iCveMunicipio " +
               " left join GRLLocalidad PLoc on PLoc.iCvePais =GRLPuerto.iCvePais " +
               " and PLoc.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
               " and PLoc.iCveMunicipio = GRLPuerto.iCveMunicipio " +
               " and PLoc.iCveLocalidad = GRLPuerto.iCveLocalidad " +
               " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de Documentos Relacionados con el Título.
         try{
           vRegsTituloDocumentos = super.FindByGeneric("",
           " select CPATituloDocumento.iCveTipoDocumento, " +
           " CPATipoDocumento.cDscTipoDocumento, " +
           " CPATituloDocumento.cNumDocumento, " +
           " CPATituloDocumento.dtDocumento, " +
           " CPATituloDocumento.dtVigenciaDocumento, " +
           " GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cDepEmite, " +
           " CPATituloDocumento.cObjetoDocumento, " +
           " CPATituloDocumento.cSuperficieDocumento, " +
           " CPATituloDocumento.lMaritimoTerrestre, " +
           " CPAClasificacionDocumento.cDscClasificacionDocto, " +
           " CPATituloDocumento.DTINIVIGENCIADOCTO " +
           " from CPATitulo " +
           " join CPATituloDocumento on CPATituloDocumento.iCveTitulo = CPATitulo.iCveTitulo " +
           " join CPATipoDocumento on CPATipoDocumento.iCveTipoDocumento = CPATituloDocumento.iCveTipoDocumento " +
           " left join GRLPersona on GRLPersona.iCvePersona = CPATituloDocumento.iCveDepEmiteDoc " +
           " left join CPAClasificacionDocumento on CPAClasificacionDocumento.iCveClasificacionDocto = CPATituloDocumento.iCveClasificacionDocto " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Solicitud
         try{
           vRegsTituloSolicitud = super.FindByGeneric("",
               " select CPASolTitulo.iEjercicio, " +
               " CPASolTitulo.iNumSolicitud, " +
               " TRACatTramite.cDscBreve, " +
               " TRAModalidad.cDscModalidad, " +
               " date(TRARegSolicitud.tsRegistro) as dtRegistro, " +
               " GRLOficina.cCalleyNo, " +
               " GRLOficina.cColonia, " +
               " GRLOficina.cCodPostal, " +
               " GRLOficina.cTelefono, " +
               " GRLOficina.cCorreoE, " +
               " GRLPais.cNombre as cDscPais, " +
               " GRLEntidadFed.cNombre as cDscEntidadFed, " +
               " GRLMunicipio.cNombre as cDscMunicipio, " +
               " TRACatTramite.iCveTramite, " +
               " TRAModalidad.iCveModalidad " +
               " from CPATitulo " +
               " join CPASolTitulo on CPASolTitulo.iCveTitulo = CPATitulo.iCveTitulo " +
               " join TRARegSolicitud on TRARegSolicitud.iEjercicio = CPASolTitulo.iEjercicio " +
               " and TRARegSolicitud.iNumSolicitud = CPASolTitulo.iNumSolicitud " +
               " join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite " +
               " join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad " +
               " join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina " +
               " join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais "+
               " join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais " +
               " and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais " +
               " and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
               " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Infomacion del Titulo de Propiedad
         try{
           vRegsTituloProp = super.FindByGeneric("",
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
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         rep.iniciaReporte();

         //Uso del Título.
         rep.comRemplaza("[cUso]",(vDatos.get("CDSCUSOTITULO")!= null)?vDatos.get("CDSCUSOTITULO").toString().toLowerCase() + " ":"");
         rep.comRemplaza("[cRFC]",(vDatos.get("cRFC")!= null)?vDatos.get("cRFC").toString():"");

         if (vDatos.getInt("iTipoPersona")== 1){ // Persona Física
           //Para quien es la Concesion.
           rep.comRemplaza("[cPara]",((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                                     ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                                     ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():""));
           cRepLegalCompleto = ((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                               ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                               ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():"");

           rep.comRemplaza("[cNoActa]",(vDatos.get("cNumActaNacimiento")!= null)?vDatos.get("cNumActaNacimiento").toString():"");
           rep.comRemplaza("[cFechaActa]",(vDatos.get("dtNacimiento")!= null)?Fecha.getDateSPN(vDatos.getDate("dtNacimiento")).toLowerCase():"");
           rep.comRemplaza("[cNoJuzgado]",(vDatos.get("cNumJuzgado")!= null)?vDatos.get("cNumJuzgado").toString():"");
           rep.comRemplaza("[cNomJuez]",(vDatos.get("cNomJuez")!= null)?vDatos.get("cNomJuez").toString():"");
           rep.comRemplaza("[cApPatJuez]",(vDatos.get("cApPatJuez")!= null)?vDatos.get("cApPatJuez").toString():"");
           rep.comRemplaza("[cApMatJuez]",(vDatos.get("cApMatJuez")!= null)?vDatos.get("cApMatJuez").toString():"");
           rep.comRemplaza("[cLibroActa]",(vDatos.get("cNumLibroActa")!= null)?vDatos.get("cNumLibroActa").toString():"");
           rep.comRemplaza("[cFojaActa]",(vDatos.get("cNumFojaActa")!= null)?vDatos.get("cNumFojaActa").toString():"");
         }else{
           rep.comRemplaza("[cNoActa]","");
           rep.comRemplaza("[cFechaActa]","");
           rep.comRemplaza("[cNoJuzgado]","");
           rep.comRemplaza("[cNomJuez]","");
           rep.comRemplaza("[cApPatJuez]","");
           rep.comRemplaza("[cApMatJuez]","");
           rep.comRemplaza("[cLibroActa]","");
           rep.comRemplaza("[cFojaActa]","");
         }

         if (vDatos.getInt("iTipoPersona")== 2){ // Persona Moral

           //Para quien es la Concesion.
           rep.comRemplaza("[cPara]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"");

           if(vRegsEscrituraConst.size()>0){
             TVDinRep vDatosEscConst = (TVDinRep) vRegsEscrituraConst.get(0);

             //Valores de las Personas Morales con su Escritura Constitutiva.
             rep.comRemplaza("[NoEscConstitutiva]",(vDatosEscConst.get("cNumEscrituraConst")!= null)?vDatosEscConst.get("cNumEscrituraConst").toString():"");
             rep.comRemplaza("[cFechaEscConstitutiva]",(vDatosEscConst.get("dtEscrituraMov")!= null)?Fecha.getDateSPN(vDatosEscConst.getDate("dtEscrituraMov")).toLowerCase():"");
             rep.comRemplaza("[cNoNotariaPub]",(vDatosEscConst.get("cNumNotariaPub")!= null)?vDatosEscConst.get("cNumNotariaPub").toString():"");
             rep.comRemplaza("[cCiudad]",((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
             rep.comRemplaza("[cEntidadFed]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");
             rep.comRemplaza("[cNomNotPub]",(vDatosEscConst.get("cNomRazonSocial")!= null)?vDatosEscConst.get("cNomRazonSocial").toString():"");
             rep.comRemplaza("[cApPatNotPub]",(vDatosEscConst.get("cApPaterno")!= null)?vDatosEscConst.get("cApPaterno").toString():"");
             rep.comRemplaza("[cApMatNotPub]",(vDatosEscConst.get("cApMaterno")!= null)?vDatosEscConst.get("cApMaterno").toString():"");

             //Valores para el Registro Público de Comercio de la Escritura Publica.
             rep.comRemplaza("[cFechaRP]",(vDatosEscConst.get("dtRegPubComercio")!= null)?Fecha.getDateSPN(vDatosEscConst.getDate("dtRegPubComercio")).toLowerCase():"");
             rep.comRemplaza("[FolioRealRP]",(vDatosEscConst.get("cNumRegPubComercio")!= null)?vDatosEscConst.get("cNumRegPubComercio").toString():"");
             rep.comRemplaza("[FojaRP]",(vDatosEscConst.get("cNumFoja")!= null)?vDatosEscConst.get("cNumFoja").toString():"");
             rep.comRemplaza("[cToRP]",(vDatosEscConst.get("cNumTomo")!= null)?vDatosEscConst.get("cNumTomo").toString():"");
             rep.comRemplaza("[cSecRP]",(vDatosEscConst.get("cNumSeccion")!= null)?vDatosEscConst.get("cNumSeccion").toString():"");
             rep.comRemplaza("[cLibRP]",(vDatosEscConst.get("cNumLibro")!= null)?vDatosEscConst.get("cNumLibro").toString():"");
             rep.comRemplaza("[cVolRP]",(vDatosEscConst.get("cNumVolumen")!= null)?vDatosEscConst.get("cNumVolumen").toString():"");
             rep.comRemplaza("[cCiudadRP]",((vDatosEscConst.get("cDscLocalidad")!= null)?vDatosEscConst.get("cDscLocalidad").toString():"")+
                                           ((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
             rep.comRemplaza("[cEntidadFedRP]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");
             rep.comRemplaza("[CNUMTESTIMONIO]",(vDatosEscConst.get("CNUMTESTIMONIO")!= null)?vDatosEscConst.get("CNUMTESTIMONIO").toString():"");

           }

           if(vRegsAcredRepLegal.size()> 0){
             TVDinRep vDatosRepLegal = (TVDinRep) vRegsAcredRepLegal.get(0);

             //Información del Representante Legal.
             cRepLegalCompleto = ((vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");

           }else
             cRepLegalCompleto = "";

         }else{
           //rep.comRemplaza("[cPara]","");
           //cRepLegalCompleto = "";

           //Valores de las Personas Morales con su Escritura Constitutiva.
           rep.comRemplaza("[NoEscConstitutiva]","");
           rep.comRemplaza("[cFechaEscConstitutiva]","");
           rep.comRemplaza("[cNoNotariaPub]","");
           rep.comRemplaza("[cCiudad]","");
           rep.comRemplaza("[cEntidadFed]","");
           rep.comRemplaza("[cNomNotPub]","");
           rep.comRemplaza("[cApPatNotPub]","");
           rep.comRemplaza("[cApMatNotPub]","");

           //Valores para el Registro Público de la Comercio de la Escritura Publica.
           rep.comRemplaza("[cFechaRP]","");
           rep.comRemplaza("[FolioRealRP]","");
           rep.comRemplaza("[FojaRP]","");
           rep.comRemplaza("[cToRP]","");
           rep.comRemplaza("[cSecRP]","");
           rep.comRemplaza("[cLibRP]","");
           rep.comRemplaza("[cVolRP]","");
           rep.comRemplaza("[cCiudadRP]","");
           rep.comRemplaza("[cEntidadFedRP]","");
           rep.comRemplaza("[CNUMTESTIMONIO]","");

         }

         //Informacion del Domicilio para Recibir Notificaciones.
         rep.comRemplaza("[cCalleDN]",(vDatos.get("cCalle")!= null)?vDatos.get("cCalle").toString():"");
         rep.comRemplaza("[cNoExtDN]",(vDatos.get("cNumExterior")!= null)?vDatos.get("cNumExterior").toString():"");
         rep.comRemplaza("[cNoIntDN]",(vDatos.get("cNumInterior")!= null)?vDatos.get("cNumInterior").toString():"");
         rep.comRemplaza("[cColDN]",(vDatos.get("cColonia")!= null)?vDatos.get("cColonia").toString():"");
         rep.comRemplaza("[cDelDN]",(vDatos.get("cNomMunicipio")!= null)?vDatos.get("cNomMunicipio").toString():"");
         rep.comRemplaza("[cCiudadDN]",(vDatos.get("cNomLocalidad")!= null)?vDatos.get("cNomLocalidad").toString():"");
         rep.comRemplaza("[cCodPosDN]",(vDatos.get("cCodPostal")!= null)?vDatos.get("cCodPostal").toString():"");
         rep.comRemplaza("[cEntFedDN]",(vDatos.get("cNomEntidadFed")!= null)?vDatos.get("cNomEntidadFed").toString():"");

         rep.comRemplaza("[cParticipacionAccionaria]",(vDatos.get("CDSCTIPOPARTACC")!= null)?vDatos.get("CDSCTIPOPARTACC").toString():"");
         rep.comRemplaza("[cAniosVigencia]",(vDatos.get("IANIOSVIGENCIA")!= null)?vDatos.get("IANIOSVIGENCIA").toString():"");

         if (vRegsTituloDocumentos.size()>0){
           for(int i=0;i<vRegsTituloDocumentos.size();i++){
             TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
             int iVigencia = 0;

             switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

               //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
               case 1:case 2:case 3:case 4:{
                 if (vDatosTitDoctos.get("lMaritimoTerrestre") != null &&
                     Integer.parseInt(vDatosTitDoctos.get("lMaritimoTerrestre").toString()) == 1){
                    rep.comRemplaza("[lMarTitConZF]","X");
                    rep.comRemplaza("[lFluvialTitConZF]","");
                 } else {
                    rep.comRemplaza("[lMarTitConZF]","");
                    rep.comRemplaza("[lFluvialTitConZF]","X");
                 }
                 rep.comRemplaza("[cNoTitConZF]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFecTitConZF]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getDateSPN(vDatosTitDoctos.getDate("dtDocumento")).toLowerCase():"");
                 rep.comRemplaza("[cDepEmiteTitConZF]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
                 rep.comRemplaza("[cObjetoTitConZF]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");

                 if(vDatosTitDoctos.get("DTVIGENCIADOCUMENTO")!=null && vDatosTitDoctos.get("DTINIVIGENCIADOCTO")!=null)
                   iVigencia = Fecha.getIntYear(vDatosTitDoctos.getDate("DTVIGENCIADOCUMENTO")) - Fecha.getIntYear(vDatosTitDoctos.getDate("DTINIVIGENCIADOCTO"));

                 rep.comRemplaza("[cVigenciaTitConZF]",""+iVigencia);

                 rep.comRemplaza("[cSupTitConZF]",(vDatosTitDoctos.get("cSuperficieDocumento")!= null)?vDatosTitDoctos.get("cSuperficieDocumento").toString():"");
                 lEntroZOFEMAT = 1;
                 break;
               }

               //Autorización en Materia de Impacto Ambiental.
               case 5:case 6:{
                 rep.comRemplaza("[cNoAutIA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutIA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getDateSPN(vDatosTitDoctos.getDate("dtDocumento")).toLowerCase():"");
                 rep.comRemplaza("[cDepEmiteAutIA]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
                 rep.comRemplaza("[cObjetoAutIA]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");
                 rep.comRemplaza("[cSuperficieAutIA]",(vDatosTitDoctos.get("cSuperficieDocumento")!= null)?vDatosTitDoctos.get("cSuperficieDocumento").toString():"");

                 if(vDatosTitDoctos.get("DTVIGENCIADOCUMENTO")!=null && vDatosTitDoctos.get("DTINIVIGENCIADOCTO")!=null)
                   iVigencia = Fecha.getIntYear(vDatosTitDoctos.getDate("DTVIGENCIADOCUMENTO")) - Fecha.getIntYear(vDatosTitDoctos.getDate("DTINIVIGENCIADOCTO"));

                 rep.comRemplaza("[cVigenciaAutIA]",""+iVigencia);
                 lEntroImpAmb = 1;
                 break;
               }

               //Franquicia de PEMEX.
               case 7:{
                 rep.comRemplaza("[cNoFPEMEX]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaFPEMEX]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaFPEMEX]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroPEMEX = 1;
                 break;
               }

               //Autorizacion de la CONAGUA.
               case 8:{
                 rep.comRemplaza("[cNoAutCONAGUA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutCONAGUA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getDateSPN(vDatosTitDoctos.getDate("dtDocumento")):"");
                 rep.comRemplaza("[cVigenciaAutCONAGUA]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");

                 if(vDatosTitDoctos.get("DTVIGENCIADOCUMENTO")!=null && vDatosTitDoctos.get("DTINIVIGENCIADOCTO")!=null)
                   iVigencia = Fecha.getIntYear(vDatosTitDoctos.getDate("DTVIGENCIADOCUMENTO")) - Fecha.getIntYear(vDatosTitDoctos.getDate("DTINIVIGENCIADOCTO"));

                 rep.comRemplaza("[cVigenciaAutCONAGUA]",""+iVigencia);

                 lEntroCONAGUA = 1;
                 break;
               }
               //Autorizacion de la SEMARNAT.
               case 9:{
                 rep.comRemplaza("[cNoAutSEMARNAT]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutSEMARNAT]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaAutSEMARNAT]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroSEMARNAT = 1;
                 break;
               }
             }
           }
         }

         if (lEntroZOFEMAT == 0){
           rep.comRemplaza("[cNoTitConZF]","");
           rep.comRemplaza("[cFecTitConZF]","");
           rep.comRemplaza("[cDepEmiteTitConZF]","");
           rep.comRemplaza("[cObjetoTitConZF]","");
           rep.comRemplaza("[cVigenciaTitConZF]","");
           rep.comRemplaza("[cSupTitConZF]","");
         }

         if (lEntroImpAmb == 0){
           rep.comRemplaza("[cNoAutIA]","");
           rep.comRemplaza("[cFechaAutIA]","");
           rep.comRemplaza("[cDepEmiteAutIA]","");
           rep.comRemplaza("[cObjetoAutIA]","");
           rep.comRemplaza("[cVigenciaAutIA]","");
           rep.comRemplaza("[cSuperficieAutIA]","");
         }

         if (lEntroPEMEX == 0){
           rep.comRemplaza("[cNoFPEMEX]","");
           rep.comRemplaza("[cFechaFPEMEX]","");
           rep.comRemplaza("[cVigenciaFPEMEX]","");
         }

         if (lEntroCONAGUA == 0){
           rep.comRemplaza("[cNoAutCONAGUA]","");
           rep.comRemplaza("[cFechaAutCONAGUA]","");
           rep.comRemplaza("[cVigenciaAutCONAGUA]","");

         }

         if (lEntroSEMARNAT == 0){
           rep.comRemplaza("[cNoAutSEMARNAT]","");
           rep.comRemplaza("[cFechaAutSEMARNAT]","");
           rep.comRemplaza("[cVigenciaAutSEMARNAT]","");
         }

         //Informacion de la Solicitud
         if (vRegsTituloSolicitud.size()>0){
            TVDinRep vDatosTitSolicutud = (TVDinRep) vRegsTituloSolicitud.get(0);
            rep.comRemplaza("[cLugarSolicitud]",((vDatosTitSolicutud.get("cCalleyNo")!= null)?vDatosTitSolicutud.get("cCalleyNo").toString():"")+
                            ((vDatosTitSolicutud.get("cColonia")!= null)?vDatosTitSolicutud.get("cColonia").toString():"")+
                            ((vDatosTitSolicutud.get("cCodPostal")!= null)?vDatosTitSolicutud.get("cCodPostal").toString():"")+
                            ((vDatosTitSolicutud.get("cDscMunicipio")!= null)?vDatosTitSolicutud.get("cDscMunicipio").toString():"")+
                            ((vDatosTitSolicutud.get("cDscEntidadFed")!= null)?vDatosTitSolicutud.get("cDscEntidadFed").toString():""));
            rep.comRemplaza("[cFechaSolicitud]",(vDatosTitSolicutud.get("dtRegistro")!= null)?Fecha.getDateSPN(vDatosTitSolicutud.getDate("dtRegistro")):"");

            //Información de los Planos
            try{
              vRegsTituloPlano = super.FindByGeneric("",
                  " SELECT PLA.CPLANONUM, PLA.CDENOMINACION, PLA.DTPLANO FROM CPASOLTITULO SOLT " +
                  " JOIN TRAPLANOXTRAMITE PLNT ON PLNT.IEJERCICIO = SOLT.IEJERCICIO AND PLNT.INUMSOLICITUD = SOLT.INUMSOLICITUD " +
                  " JOIN OMPPLANO PLA ON PLA.ICVEPLANO = PLNT.ICVEPLANO AND PLA.LAPROBADOXOBRAS = 1 " +
                  " WHERE SOLT.ICVETITULO = " + iCveTitulo,
                  dataSourceName);
            }catch(SQLException ex){
              ex.printStackTrace();
             cMensaje = ex.getMessage();
            }catch(Exception ex2){
              ex2.printStackTrace();
             cMensaje += ex2.getMessage();
            }

            String cPlanos = "";
            for(int i=0;i<vRegsTituloPlano.size();i++){
              TVDinRep vDatosTitPlanos = (TVDinRep) vRegsTituloPlano.get(i);

              cPlanos = cPlanos + ((vDatosTitPlanos.get("CPLANONUM")!= null)?vDatosTitPlanos.get("CPLANONUM").toString():"") + " de " +
                        Fecha.getDateSPN(vDatosTitPlanos.getDate("DTPLANO")) + ", " + ((vDatosTitPlanos.get("CDENOMINACION")!= null)?vDatosTitPlanos.get("CDENOMINACION").toString().toLowerCase():"") + ", ";
            }
            rep.comRemplaza("[cPlanos]",cPlanos);

         } else {
           rep.comRemplaza("[cLugarSolicitud]","");
           rep.comRemplaza("[cFechaSolicitud]","");
         }

         String cPuertosUbicacion = "", cEntidadFedPuertos = "", cMunFRP = "", cDelimitaPuertos = "";

         if (vRegsTituloUbicacion.size()>0){
            TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);

            if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1)
              rep.comRemplaza("[cUbicacionConcesion]","dentro de puerto");
            else
              rep.comRemplaza("[cUbicacionConcesion]","fuera de puerto");

            rep.comRemplaza("[cCalleFRP]",(vDatosTitUbi.get("cCalleTitulo")!= null)?vDatosTitUbi.get("cCalleTitulo").toString():"");
            rep.comRemplaza("[cKmFRP]",(vDatosTitUbi.get("cKm")!= null)?vDatosTitUbi.get("cKm").toString():"");
            rep.comRemplaza("[cColFRP]",(vDatosTitUbi.get("cColoniaTitulo")!= null)?vDatosTitUbi.get("cColoniaTitulo").toString():"");

            if(vDatosTitUbi.get("iCvePuerto")!= null){
              if(vDatosTitUbi.getInt("iCvePuerto")>0){
                rep.comRemplaza("[cPuertoFRP]",(vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
                rep.comRemplaza("[cMunFRP]",(vDatosTitUbi.get("cPDscMunicipio")!= null)?vDatosTitUbi.get("cPDscMunicipio").toString():"");
                rep.comRemplaza("[cEntFedFRP]",(vDatosTitUbi.get("cPDscEntidadFed")!= null)?vDatosTitUbi.get("cPDscEntidadFed").toString():"");
              }else{
                rep.comRemplaza("[cPuertoFRP]","");
                rep.comRemplaza("[cMunFRP]",(vDatosTitUbi.get("cUDscMunicipio")!= null)?vDatosTitUbi.get("cUDscMunicipio").toString():"");
                rep.comRemplaza("[cEntFedFRP]",(vDatosTitUbi.get("cUDscEntidadFed")!= null)?vDatosTitUbi.get("cUDscEntidadFed").toString():"");
              }
            }else{
              rep.comRemplaza("[cCalleFRP]","");
              rep.comRemplaza("[cKmFRP]","");
              rep.comRemplaza("[cColFRP]","");
              rep.comRemplaza("[cPuertoFRP]","");
              rep.comRemplaza("[cMunFRP]","");
              rep.comRemplaza("[cEntFedFRP]","");
            }

         } else {
           rep.comRemplaza("[cCalleFRP]","");
           rep.comRemplaza("[cKmFRP]","");
           rep.comRemplaza("[cColFRP]","");
           rep.comRemplaza("[cPuertoFRP]","");
           rep.comRemplaza("[cMunFRP]","");
           rep.comRemplaza("[cEntFedFRP]","");
         }

         if (vRegsTituloProp.size()>0){
           TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);

           //Informacion del Título de Propiedad.
           rep.comRemplaza("[cSupZF]",(vDatosTitProp.get("cSuperficiePropiedad")!= null)?vDatosTitProp.get("cSuperficiePropiedad").toString():"");
           rep.comRemplaza("[cLoteZF]",(vDatosTitProp.get("cLotePropiedad")!= null)?vDatosTitProp.get("cLotePropiedad").toString():"");
           rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cSeccionPropiedad")!= null)?vDatosTitProp.get("cSeccionPropiedad").toString():"");
           rep.comRemplaza("[cManZF]",(vDatosTitProp.get("cManzanaPropiedad")!= null)?vDatosTitProp.get("cManzanaPropiedad").toString():"");
           rep.comRemplaza("[cNoZF]",(vDatosTitProp.get("cNumOficialPropiedad")!= null)?vDatosTitProp.get("cNumOficialPropiedad").toString():"");
           rep.comRemplaza("[cCalZF]",(vDatosTitProp.get("cCallePropiedad")!= null)?vDatosTitProp.get("cCallePropiedad").toString():"");
           rep.comRemplaza("[cKMZF]",(vDatosTitProp.get("cKmPropiedad")!= null)?vDatosTitProp.get("cKmPropiedad").toString():"");
           rep.comRemplaza("[cColZF]",(vDatosTitProp.get("cColoniaPropiedad")!= null)?vDatosTitProp.get("cColoniaPropiedad").toString():"");
           rep.comRemplaza("[cPuertoZF]",(vDatosTitProp.get("cDscPuerto")!= null)?vDatosTitProp.get("cDscPuerto").toString():"");
           rep.comRemplaza("[cMunZF]",(vDatosTitProp.get("cDscMunicipio")!= null)?vDatosTitProp.get("cDscMunicipio").toString():"");
           rep.comRemplaza("[cEntFedZF]",(vDatosTitProp.get("cDscEntidadFed")!= null)?vDatosTitProp.get("cDscEntidadFed").toString():"");

           //Informacion de la Escritura Publica del Título de Propiedad.
           rep.comRemplaza("[cNoEscZF]",(vDatosTitProp.get("cNumTitProEscPublica") != null && vDatosTitProp.get("cNumTitProEscPublica")!= null)?vDatosTitProp.get("cNumTitProEscPublica").toString():"");
           rep.comRemplaza("[cFechaZF]",(vDatosTitProp.get("dtEscPublica") != null)?Fecha.getDateSPN(vDatosTitProp.getDate("dtEscPublica")):"");
           rep.comRemplaza("[cNoNotPubZF]",(vDatosTitProp.get("cNumNotariaPublica") != null && vDatosTitProp.get("cNumNotariaPublica")!= null)?vDatosTitProp.get("cNumNotariaPublica").toString():"");
           rep.comRemplaza("[cCiuZF]",((vDatosTitProp.get("cDscLocalidadEscPub") != null && vDatosTitProp.get("cDscLocalidadEscPub")!= null)?vDatosTitProp.get("cDscLocalidadEscPub").toString():"")+
                            ((vDatosTitProp.get("cDscMunicipioEscPub") != null && vDatosTitProp.get("cDscMunicipioEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():""));
           rep.comRemplaza("[cEntFedEPZF]",(vDatosTitProp.get("cDscEntidadFedEscPub") != null && vDatosTitProp.get("cDscEntidadFedEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():"");
           rep.comRemplaza("[cNotarioPublicoZF]",((vDatosTitProp.get("cNomRazonSocial") != null && vDatosTitProp.get("cNomRazonSocial")!= null)?vDatosTitProp.get("cNomRazonSocial").toString():"") + " " +
                            ((vDatosTitProp.get("cApPaterno") != null && vDatosTitProp.get("cApPaterno")!= null)?vDatosTitProp.get("cApPaterno").toString():"") + " " +
                            ((vDatosTitProp.get("cApMaterno") != null && vDatosTitProp.get("cApMaterno")!= null)?vDatosTitProp.get("cApMaterno").toString():""));

           //Informacion del Registro Publico de Comercio del Título de Propiedad.
           rep.comRemplaza("[cFecZF]",(vDatosTitProp.get("dtTitProRegPubCom") != null)?Fecha.getFechaDDMMYYYY(vDatosTitProp.getDate("dtTitProRegPubCom"),"/"):"");
           rep.comRemplaza("[cFolioZF]",(vDatosTitProp.get("cNumTitProRegPubCom") != null && vDatosTitProp.get("cNumTitProRegPubCom")!= null)?vDatosTitProp.get("cNumTitProRegPubCom").toString():"");
           rep.comRemplaza("[cFojaZF]",(vDatosTitProp.get("cNumRegPubComFoja") != null && vDatosTitProp.get("cNumRegPubComFoja")!= null)?vDatosTitProp.get("cNumRegPubComFoja").toString():"");
           rep.comRemplaza("[cToZF]",(vDatosTitProp.get("cNumRegPubComTomo") != null && vDatosTitProp.get("cNumRegPubComTomo")!= null)?vDatosTitProp.get("cNumRegPubComTomo").toString():"");
           rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cNumRegPubComSeccion") != null && vDatosTitProp.get("cNumRegPubComSeccion")!= null)?vDatosTitProp.get("cNumRegPubComSeccion").toString():"");
           rep.comRemplaza("[cLibroZF]",(vDatosTitProp.get("cNumRegPubComLibro") != null && vDatosTitProp.get("cNumRegPubComLibro")!= null)?vDatosTitProp.get("cNumRegPubComLibro").toString():"");
           rep.comRemplaza("[cVolZF]",(vDatosTitProp.get("cNumRegPubComVolumen") != null && vDatosTitProp.get("cNumRegPubComVolumen")!= null)?vDatosTitProp.get("cNumRegPubComVolumen").toString():"");
           rep.comRemplaza("[cCiudadZF]",((vDatosTitProp.get("cDscLocalidadRegPubCom") != null && vDatosTitProp.get("cDscLocalidadRegPubCom")!= null)?vDatosTitProp.get("cDscLocalidadRegPubCom").toString():"")+
                            ((vDatosTitProp.get("cDscMunicipioRegPubCom") != null && vDatosTitProp.get("cDscMunicipioRegPubCom")!= null)?vDatosTitProp.get("cDscMunicipioRegPubCom").toString():""));
           rep.comRemplaza("[cEntFedRPCZF]",(vDatosTitProp.get("cDscEntidadFedRegPubCom") != null && vDatosTitProp.get("cDscEntidadFedRegPubCom")!= null)?vDatosTitProp.get("cDscEntidadFedRegPubCom").toString():"");
         } else {
           //Informacion del Título de Propiedad.
           rep.comRemplaza("[cSupZF]","");
           rep.comRemplaza("[cLoteZF]","");
           rep.comRemplaza("[cSecZF]","");
           rep.comRemplaza("[cManZF]","");
           rep.comRemplaza("[cNoZF]","");
           rep.comRemplaza("[cCalZF]","");
           rep.comRemplaza("[cKMZF]","");
           rep.comRemplaza("[cColZF]","");
           rep.comRemplaza("[cPuertoZF]","");
           rep.comRemplaza("[cMunZF]","");
           rep.comRemplaza("[cEntFedZF]","");

           //Informacion de la Escritura Publica del Título de Propiedad.
           rep.comRemplaza("[cNoEscZF]","");
           rep.comRemplaza("[cFechaZF]","");
           rep.comRemplaza("[cNoNotPubZF]","");
           rep.comRemplaza("[cCiuZF]","");
           rep.comRemplaza("[cEntFedEPZF]","");
           rep.comRemplaza("[cNotarioPublicoZF]","");

           //Informacion del Registro Publico de Comercio del Título de Propiedad.
           rep.comRemplaza("[cFecZF]","");
           rep.comRemplaza("[cFolioZF]","");
           rep.comRemplaza("[cFojaZF]","");
           rep.comRemplaza("[cToZF]","");
           rep.comRemplaza("[cSecZF]","");
           rep.comRemplaza("[cLibroZF]","");
           rep.comRemplaza("[cVolZF]","");
           rep.comRemplaza("[cCiudadZF]","");
           rep.comRemplaza("[cEntFedRPCZF]","");
         }

      //Monto de Inversión.
      rep.comRemplaza("[cMontoInversion]",(vDatos.get("cMontoInversion")!= null)?vDatos.get("cMontoInversion").toString():"");

      //Monto de Inversión con Letra.
      TLetterNumberFormat tNumeroLetra = new TLetterNumberFormat();
      tNumeroLetra.getCantidad(vDatos.getLong("cMontoInversion"));
      rep.comRemplaza("[cMontoInversionConLetra]",tNumeroLetra.getCantidad(vDatos.getLong("cMontoInversion")));

      //Fecha de Expedición.
      rep.comRemplaza("[cFecExpedicion]",Fecha.getFechaDDMMYYYY(Fecha.TodaySQL(),"/"));
      rep.comRemplaza("[cRepLegalCompleto]",cRepLegalCompleto);

      rep.comRemplaza("[CDSCTIPOPARTACC]",vDatos.getString("CDSCTIPOPARTACC")!=null?vDatos.getString("CDSCTIPOPARTACC"):"");

    } else {
      //System.out.print("No Encontraron Datos");
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return rep.getEtiquetas(true);
  }

  public StringBuffer GenAutorizacionObras(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    Vector vRegsEscrituraConst = new Vector();
    Vector vRegsAcredRepLegal = new Vector();
    Vector vRegsTituloProp = new Vector();
    Vector vRegsTituloUbicacion = new Vector();
    Vector vRegsTituloSolicitud = new Vector();
    Vector vRegsTituloObras = new Vector();
    Vector vRegsTituloServPortuarios = new Vector();
    Vector vRegsTituloDocumentos = new Vector();
    Vector vRegsTituloAnterior = new Vector();
    Vector vRegsTituloPlano = new Vector();

    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TFechas Fecha = new TFechas("44");

    TWord rep = new TWord();
    rep.iniciaReporte();
    int iCveTituloFiltro = Integer.parseInt(cQuery);
    int lEntroZOFEMAT = 0,lEntroImpAmb = 0,lEntroPEMEX = 0,lEntroCONAGUA = 0,lEntroSEMARNAT = 0;
    int iInicioVigenciaTitulo = 0, iFinVigenciatitulo = 0, iVigenciaTitulo = 0;
    String cRepLegalCompleto="";

    cQuery = "select CPATitulo.iCveTitulo, " +
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
             "CPATitulo.iCveTituloAnterior, " +
             "CPATitulo.dtVigenciaTitulo " +
             "from CPATitulo " +
             "join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
             "join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
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
             "where CPATitulo.iCveTitulo = " + iCveTituloFiltro;

    try{
      vRegs = super.FindByGeneric("", cQuery, dataSourceName);
    }catch(SQLException ex){
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    if (vRegs.size() > 0){
         TVDinRep vDatos = (TVDinRep) vRegs.get(0);

         int iCveTitulo = Integer.parseInt(vDatos.get("iCveTitulo").toString());
         int iCveTituloAnt = vDatos.getInt("iCveTituloAnterior");

         iInicioVigenciaTitulo = (vDatos.get("DTINIVIGENCIATITULO")!= null)?Fecha.getIntYear(vDatos.getDate("DTINIVIGENCIATITULO")):0;
         iFinVigenciatitulo    = (vDatos.get("DTVIGENCIATITULO")!= null)?Fecha.getIntYear(vDatos.getDate("DTVIGENCIATITULO")):0;
         iVigenciaTitulo       = iFinVigenciatitulo - iInicioVigenciaTitulo;

         // Escritura Constitutiva de las Personas Morales.
         try{
           vRegsEscrituraConst = super.FindByGeneric("",
           " select CPAEscConsMov.iNumMovimiento, " +
           " CPAEscConsMov.cNumEscrituraConst, " +
           " CPATipoMovEscritura.cDscTipoMovEscritura, " +
           " CPAEscConsMov.dtEscrituraMov, " +
           " CPAEscConsMov.cNumNotariaPub, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " CPARegPubComercio.dtRegPubComercio, " +
           " CPARegPubComercio.cNumRegPubComercio, " +
           " CPARegPubComercio.cNumFoja, " +
           " CPARegPubComercio.cNumTomo, " +
           " CPARegPubComercio.cNumSeccion, " +
           " CPARegPubComercio.cNumLibro, " +
           " CPARegPubComercio.cNumVolumen, " +
           " CPARegPubComercio.cNumSeccion, " +
           " CPARegPubComercio.CNUMTESTIMONIO, " +
           " GRLPais.cNombre as cDscPais, " +
           " GRLEntidadFed.cNombre as cDscEntidadFed, " +
           " GRLMunicipio.cNombre as cDscMunicipio, " +
           " GRLLocalidad.cNombre as cDscLocalidad " +
           " from CPATitulo " +
           " join CPAEscConsMov on CPAEscConsMov.iCveTitulo = CPATitulo.iCveTitulo " +
           " join CPATipoMovEscritura on CPATipoMovEscritura.iCveTipoMovEscritura = CPAEscConsMov.iCveTipoMovEscritura " +
           " left join CPARegPubComercio on CPARegPubComercio.iCveTitulo = CPAEscConsMov.iCveTitulo " +
           " and CPARegPubComercio.iNumMovimiento = CPAEscConsMov.iNumMovimiento " +
           " left join GRLPersona on GRLPersona.iCvePersona = CPAEscConsMov.iCveNotarioPublico " +
           " left join GRLPais on GRLPais.iCvePais = CPARegPubComercio.iCvePais " +
           " left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " left join GRLMunicipio on GRLMunicipio.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
           " left join GRLLocalidad on GRLLocalidad.iCvePais = CPARegPubComercio.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = CPARegPubComercio.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = CPARegPubComercio.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = CPARegPubComercio.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTituloAnt,
           dataSourceName);
         }catch(SQLException ex){
          ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Acreditacion del Representante Legal.
         try{
           vRegsAcredRepLegal = super.FindByGeneric("",
           " select CPAAcreditacionRepLegal.dtAcredRepLegal, " +
           " CPAAcreditacionRepLegal.cNumNotPubAcred, " +
           " CPAAcreditacionRepLegal.cDocumento, " +
           " GRLPersona.cNomRazonSocial, " +
           " GRLPersona.cApPaterno, " +
           " GRLPersona.cApMaterno, " +
           " Notario.cNomRazonSocial as cNomNotario, " +
           " Notario.cApPaterno as cApPatNotario, " +
           " Notario.cApMaterno as cApMatNotario, " +
           " GRLPais.cNombre as cNomPais, " +
           " GRLEntidadFed.cNombre as cNomEntidadFed, " +
           " GRLMunicipio.cNombre as cNomMunicipio, " +
           " GRLLocalidad.cNombre as cNomLocalidad " +
           " from CPATitulo " +
           " join CPAAcreditacionRepLegal on CPAAcreditacionRepLegal.iCveTitulo = CPATitulo.iCveTitulo " +
           " join GRLPersona on GRLPersona.iCvePersona = CPAAcreditacionRepLegal.iCveRepLegal " +
           " join GRLPersona Notario on Notario.iCvePersona = CPAAcreditacionRepLegal.iCveNotarioPubAcred " +
           " left join GRLDomicilio on GRLDomicilio.iCvePersona = Notario.iCvePersona " +
           " and GRLDomicilio.lPredeterminado = 1 " +
           " left join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
           " left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " left join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTituloAnt,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Infomacion del Titulo de Propiedad
         try{
           vRegsTituloProp = super.FindByGeneric("",
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
           " where CPATitulo.iCveTitulo = " + iCveTituloAnt,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Ubicacion del Area Solicitada
         try{
           vRegsTituloUbicacion = super.FindByGeneric("",
           " select CPATituloUbicacion.lDentroRecintoPort, " +
           " CPATituloUbicacion.cCalleTitulo, " +
           " CPATituloUbicacion.cKm, " +
           " CPATituloUbicacion.cColoniaTitulo, " +
           " GRLPuerto.cDscPuerto, " +
           " GRLPais.cNombre as cDscPais, " +
           " GRLEntidadFed.cNombre as cDscEntidadFed, " +
           " GRLMunicipio.cNombre as cDscMunicipio, " +
           " GRLLocalidad.cNombre as cDscLocalidad " +
           " from CPATitulo " +
           " join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
           " left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
           " left join GRLPais on GRLPais.iCvePais = CPATituloUbicacion.iCvePais " +
           " left join GRLEntidadFed on GRLEntidadFed.iCvePais = CPATituloUbicacion.iCvePais " +
           " and GRLEntidadFed.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
           " left join GRLMunicipio on GRLMunicipio.iCvePais = CPATituloUbicacion.iCvePais " +
           " and GRLMunicipio.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
           " and GRLMunicipio.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
           " left join GRLLocalidad on GRLLocalidad.iCvePais = CPATituloUbicacion.iCvePais " +
           " and GRLLocalidad.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
           " and GRLLocalidad.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " +
           " and GRLLocalidad.iCveLocalidad = CPATituloUbicacion.iCveLocalidad " +
           " where CPATitulo.iCveTitulo = " + iCveTituloAnt,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de la Solicitud
         try{
           vRegsTituloSolicitud = super.FindByGeneric("",
               " select CPASolTitulo.iEjercicio, " +
               " CPASolTitulo.iNumSolicitud, " +
               " TRACatTramite.cDscBreve, " +
               " TRAModalidad.cDscModalidad, " +
               " date(TRARegSolicitud.tsRegistro) as dtRegistro, " +
               " GRLOficina.cCalleyNo, " +
               " GRLOficina.cColonia, " +
               " GRLOficina.cCodPostal, " +
               " GRLOficina.cTelefono, " +
               " GRLOficina.cCorreoE, " +
               " GRLPais.cNombre as cDscPais, " +
               " GRLEntidadFed.cNombre as cDscEntidadFed, " +
               " GRLMunicipio.cNombre as cDscMunicipio, " +
               " TRACatTramite.iCveTramite, " +
               " TRAModalidad.iCveModalidad " +
               " from CPATitulo " +
               " join CPASolTitulo on CPASolTitulo.iCveTitulo = CPATitulo.iCveTitulo " +
               " join TRARegSolicitud on TRARegSolicitud.iEjercicio = CPASolTitulo.iEjercicio " +
               " and TRARegSolicitud.iNumSolicitud = CPASolTitulo.iNumSolicitud " +
               " join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite " +
               " join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad " +
               " join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina " +
               " join GRLPais on GRLPais.iCvePais = GRLOficina.iCvePais "+
               " join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLOficina.iCvePais " +
               " and GRLEntidadFed.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " join GRLMunicipio on GRLMunicipio.iCvePais = GRLOficina.iCvePais " +
               " and GRLMunicipio.iCveEntidadFed = GRLOficina.iCveEntidadFed " +
               " and GRLMunicipio.iCveMunicipio = GRLOficina.iCveEntidadFed " +
               " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de las Obras del Título.
         try{
           vRegsTituloObras = super.FindByGeneric("",
           " select CPATituloObra.cDscObra, " +
           " CPATituloObra.lPropiedadNacional, " +
           " CPATituloObra.lConstruccion, " +
           " CPATituloObra.lOperacion, " +
           " CPATituloObra.cTiempoEstEjecucion, " +
           " CPATituloObra.dtIniObra " +
           " from CPATitulo " +
           " join CPATituloObra on CPATituloObra.iCveTitulo = CPATitulo.iCveTitulo " +
           " where CPATitulo.iCveTitulo = " + iCveTitulo,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion de Documentos Relacionados con el Título.
         try{
           vRegsTituloDocumentos = super.FindByGeneric("",
           " select CPATituloDocumento.iCveTipoDocumento, " +
           " CPATipoDocumento.cDscTipoDocumento, " +
           " CPATituloDocumento.cNumDocumento, " +
           " CPATituloDocumento.dtDocumento, " +
           " CPATituloDocumento.dtVigenciaDocumento, " +
           " GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as cDepEmite, " +
           " CPATituloDocumento.cObjetoDocumento, " +
           " CPATituloDocumento.cSuperficieDocumento, " +
           " CPATituloDocumento.lMaritimoTerrestre, " +
           " CPAClasificacionDocumento.cDscClasificacionDocto, " +
           " CPATituloDocumento.DTINIVIGENCIADOCTO " +
           " from CPATitulo " +
           " join CPATituloDocumento on CPATituloDocumento.iCveTitulo = CPATitulo.iCveTitulo " +
           " join CPATipoDocumento on CPATipoDocumento.iCveTipoDocumento = CPATituloDocumento.iCveTipoDocumento " +
           " left join GRLPersona on GRLPersona.iCvePersona = CPATituloDocumento.iCveDepEmiteDoc " +
           " left join CPAClasificacionDocumento on CPAClasificacionDocumento.iCveClasificacionDocto = CPATituloDocumento.iCveClasificacionDocto " +
           " where CPATitulo.iCveTitulo = " + iCveTituloAnt,
           dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         //Informacion del Titulo Anterior.
         if (iCveTituloAnt>0){
           try{
             vRegsTituloAnterior = super.FindByGeneric("",
                 " select CPATitulo.iCveTipoTitulo, " +
                 " CPATipoTitulo.cDscTipoTitulo, " +
                 " CPATitulo.cNumTitulo, " +
                 " CPATitulo.dtIniVigenciaTitulo, " +
                 " CPATitulo.dtVigenciaTitulo, " +
                 " CPATitulo.dtPublicacionDOF " +
                 " from CPATitulo " +
                 " join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
                 " where CPATitulo.iCveTitulo = " + iCveTituloAnt,
                 dataSourceName);
           } catch(Exception ex){
             ex.printStackTrace();
             cMensaje += ex.getMessage();
           }
         }

         rep.iniciaReporte();

         if (vDatos.getInt("iTipoPersona")== 1){
           //Valores para las Personas Físicas.
           rep.comRemplaza("[cPara]",((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                                     ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                                     ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():""));
           cRepLegalCompleto = ((vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"") +
                               ((vDatos.get("cApPaterno")!= null)?vDatos.get("cApPaterno").toString() + " ":"") +
                               ((vDatos.get("cApMaterno")!= null)?vDatos.get("cApMaterno").toString():"");

         }

         if (vDatos.getInt("iTipoPersona")== 2){

           if(vRegsEscrituraConst.size()>0){
             TVDinRep vDatosEscConst = (TVDinRep) vRegsEscrituraConst.get(0);

             //Para quien es la Concesion.
             rep.comRemplaza("[cPara]",(vDatos.get("cNomRazonSocial")!= null)?vDatos.get("cNomRazonSocial").toString() + " ":"");

             //Valores de las Personas Morales con su Escritura Constitutiva.
             rep.comRemplaza("[NoEscConstitutiva]",(vDatosEscConst.get("cNumEscrituraConst")!= null)?vDatosEscConst.get("cNumEscrituraConst").toString():"");
             rep.comRemplaza("[cFechaEscConstitutiva]",(vDatosEscConst.get("dtEscrituraMov")!= null)?Fecha.getDateSPN(vDatosEscConst.getDate("dtEscrituraMov")).toLowerCase():"");
             rep.comRemplaza("[cNoNotariaPub]",(vDatosEscConst.get("cNumNotariaPub")!= null)?vDatosEscConst.get("cNumNotariaPub").toString():"");
             rep.comRemplaza("[cCiudad]",((vDatosEscConst.get("cDscLocalidad")!= null)?vDatosEscConst.get("cDscLocalidad").toString():"")+ " " +
                                         ((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
             rep.comRemplaza("[cEntidadFed]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");
             rep.comRemplaza("[cNomNotPub]",(vDatosEscConst.get("cNomRazonSocial")!= null)?vDatosEscConst.get("cNomRazonSocial").toString():"");
             rep.comRemplaza("[cApPatNotPub]",(vDatosEscConst.get("cApPaterno")!= null)?vDatosEscConst.get("cApPaterno").toString():"");
             rep.comRemplaza("[cApMatNotPub]",(vDatosEscConst.get("cApMaterno")!= null)?vDatosEscConst.get("cApMaterno").toString():"");
             rep.comRemplaza("[cRFC]",(vDatos.get("cRFC")!= null)?vDatos.get("cRFC").toString():"");

             //Valores para el Registro Público de la Comercio de la Escritura Publica.
             rep.comRemplaza("[cFechaRP]",(vDatosEscConst.get("dtRegPubComercio")!= null)?Fecha.getDateSPN(vDatosEscConst.getDate("dtRegPubComercio")).toLowerCase():"");
             rep.comRemplaza("[FolioRealRP]",(vDatosEscConst.get("cNumRegPubComercio")!= null)?vDatosEscConst.get("cNumRegPubComercio").toString():"");
             rep.comRemplaza("[FojaRP]",(vDatosEscConst.get("cNumFoja")!= null)?vDatosEscConst.get("cNumFoja").toString():"");
             rep.comRemplaza("[cToRP]",(vDatosEscConst.get("cNumTomo")!= null)?vDatosEscConst.get("cNumTomo").toString():"");
             rep.comRemplaza("[cSecRP]",(vDatosEscConst.get("cNumSeccion")!= null)?vDatosEscConst.get("cNumSeccion").toString():"");
             rep.comRemplaza("[cLibRP]",(vDatosEscConst.get("cNumLibro")!= null)?vDatosEscConst.get("cNumLibro").toString():"");
             rep.comRemplaza("[cVolRP]",(vDatosEscConst.get("cNumVolumen")!= null)?vDatosEscConst.get("cNumVolumen").toString():"");
             rep.comRemplaza("[cCiudadRP]",((vDatosEscConst.get("cDscLocalidad")!= null)?vDatosEscConst.get("cDscLocalidad").toString():"")+
                                           ((vDatosEscConst.get("cDscMunicipio")!= null)?vDatosEscConst.get("cDscMunicipio").toString():""));
             rep.comRemplaza("[cEntidadFedRP]",(vDatosEscConst.get("cDscEntidadFed")!= null)?vDatosEscConst.get("cDscEntidadFed").toString():"");
             rep.comRemplaza("[CNUMTESTIMONIO]",(vDatosEscConst.get("CNUMTESTIMONIO")!= null)?vDatosEscConst.get("CNUMTESTIMONIO").toString():"");

           }

           if (vRegsAcredRepLegal.size()> 0){
             TVDinRep vDatosRepLegal = (TVDinRep) vRegsAcredRepLegal.get(0);

             //Información del Representante Legal.
             rep.comRemplaza("[cNombreRL]",(vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString():"");
             rep.comRemplaza("[cApPatRL]",(vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString():"");
             rep.comRemplaza("[cApMatRL]",(vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");
             rep.comRemplaza("[cFechaRL]",(vDatosRepLegal.get("dtAcredRepLegal")!= null)?Fecha.getDateSPN(vDatosRepLegal.getDate("dtAcredRepLegal")).toLowerCase():"");
             rep.comRemplaza("[cNoNotPubRL]",(vDatosRepLegal.get("cNumNotPubAcred")!= null)?vDatosRepLegal.get("cNumNotPubAcred").toString():"");
             rep.comRemplaza("[cCiudadRL]",((vDatosRepLegal.get("cNomLocalidad")!= null)?vDatosRepLegal.get("cNomLocalidad").toString():"")+ " " +
                                           ((vDatosRepLegal.get("cNomMunicipio")!= null)?vDatosRepLegal.get("cNomMunicipio").toString():""));
             rep.comRemplaza("[cEntFedRL]",(vDatosRepLegal.get("cNomEntidadFed")!= null)?vDatosRepLegal.get("cNomEntidadFed").toString():"");
             rep.comRemplaza("[cNotPubRL]",((vDatosRepLegal.get("cNomNotario")!= null)?vDatosRepLegal.get("cNomNotario").toString():"")+ " " +
                                           ((vDatosRepLegal.get("cApPatNotario")!= null)?vDatosRepLegal.get("cApPatNotario").toString():"")+ " " +
                                           ((vDatosRepLegal.get("cApMatNotario")!= null)?vDatosRepLegal.get("cApMatNotario").toString():""));

             cRepLegalCompleto = ((vDatosRepLegal.get("cNomRazonSocial")!= null)?vDatosRepLegal.get("cNomRazonSocial").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApPaterno")!= null)?vDatosRepLegal.get("cApPaterno").toString() + " ":"") +
                                 ((vDatosRepLegal.get("cApMaterno")!= null)?vDatosRepLegal.get("cApMaterno").toString():"");


           } else {
             rep.comRemplaza("[cNombreRL]","");
             rep.comRemplaza("[cApPatRL]","");
             rep.comRemplaza("[cApMatRL]","");
             rep.comRemplaza("[cFechaRL]","");
             rep.comRemplaza("[cNoNotPubRL]","");
             rep.comRemplaza("[cCiudadRL]","");
             rep.comRemplaza("[cEntFedRL]","");
             rep.comRemplaza("[cNotPubRL]","");
           }
         } else {

           //Valores de las Personas Morales con su Escritura Constitutiva.
           rep.comRemplaza("[cRazonSocial]","");
           rep.comRemplaza("[NoEscConstitutiva]","");
           rep.comRemplaza("[cFechaEscConstitutiva]","");
           rep.comRemplaza("[cNoNotariaPub]","");
           rep.comRemplaza("[cCiudad]","");
           rep.comRemplaza("[cEntidadFed]","");
           rep.comRemplaza("[cNomNotPub]","");
           rep.comRemplaza("[cApPatNotPub]","");
           rep.comRemplaza("[cApMatNotPub]","");
           rep.comRemplaza("[cRFC]","");

           //Valores para el Registro Público de la Comercio de la Escritura Publica.
           rep.comRemplaza("[cFechaRP]","");
           rep.comRemplaza("[FolioRealRP]","");
           rep.comRemplaza("[FojaRP]","");
           rep.comRemplaza("[cToRP]","");
           rep.comRemplaza("[cSecRP]","");
           rep.comRemplaza("[cLibRP]","");
           rep.comRemplaza("[cVolRP]","");
           rep.comRemplaza("[cCiudadRP]","");
           rep.comRemplaza("[cEntidadFedRP]","");

           //Información del Representante Legal.
           rep.comRemplaza("[cNombreRL]","");
           rep.comRemplaza("[cApPatRL]","");
           rep.comRemplaza("[cApMatRL]","");
           rep.comRemplaza("[cFechaRL]","");
           rep.comRemplaza("[cNoNotPubRL]","");
           rep.comRemplaza("[cCiudadRL]","");
           rep.comRemplaza("[cEntFedRL]","");
           rep.comRemplaza("[cNotPubRL]","");
         }

         //Informacion del Domicilio para Recibir Notificaciones.
         rep.comRemplaza("[cCalleDN]",(vDatos.get("cCalle")!= null)?vDatos.get("cCalle").toString():"");
         rep.comRemplaza("[cNoExtDN]",(vDatos.get("cNumExterior")!= null)?vDatos.get("cNumExterior").toString():"");
         rep.comRemplaza("[cNoIntDN]",(vDatos.get("cNumInterior")!= null)?vDatos.get("cNumInterior").toString():"");
         rep.comRemplaza("[cColDN]",(vDatos.get("cColonia")!= null)?vDatos.get("cColonia").toString():"");
         rep.comRemplaza("[cDelDN]",(vDatos.get("cNomMunicipio")!= null)?vDatos.get("cNomMunicipio").toString():"");
         rep.comRemplaza("[cCiudadDN]",(vDatos.get("cNomLocalidad")!= null)?vDatos.get("cNomLocalidad").toString():"");
         rep.comRemplaza("[cCodPosDN]",(vDatos.get("cCodPostal")!= null)?vDatos.get("cCodPostal").toString():"");
         rep.comRemplaza("[cEntFedDN]",(vDatos.get("cNomEntidadFed")!= null)?vDatos.get("cNomEntidadFed").toString():"");

         if (vRegsTituloProp.size()>0){
           TVDinRep vDatosTitProp = (TVDinRep) vRegsTituloProp.get(0);

           //Informacion del Título de Propiedad.
           rep.comRemplaza("[cSupZF]",(vDatosTitProp.get("cSuperficiePropiedad")!= null)?vDatosTitProp.get("cSuperficiePropiedad").toString():"");
           rep.comRemplaza("[cLoteZF]",(vDatosTitProp.get("cLotePropiedad")!= null)?vDatosTitProp.get("cLotePropiedad").toString():"");
           rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cSeccionPropiedad")!= null)?vDatosTitProp.get("cSeccionPropiedad").toString():"");
           rep.comRemplaza("[cManZF]",(vDatosTitProp.get("cManzanaPropiedad")!= null)?vDatosTitProp.get("cManzanaPropiedad").toString():"");
           rep.comRemplaza("[cNoZF]",(vDatosTitProp.get("cNumOficialPropiedad")!= null)?vDatosTitProp.get("cNumOficialPropiedad").toString():"");
           rep.comRemplaza("[cCalZF]",(vDatosTitProp.get("cCallePropiedad")!= null)?vDatosTitProp.get("cCallePropiedad").toString():"");
           rep.comRemplaza("[cKMZF]",(vDatosTitProp.get("cKmPropiedad")!= null)?vDatosTitProp.get("cKmPropiedad").toString():"");
           rep.comRemplaza("[cColZF]",(vDatosTitProp.get("cColoniaPropiedad")!= null)?vDatosTitProp.get("cColoniaPropiedad").toString():"");
           rep.comRemplaza("[cPuertoZF]",(vDatosTitProp.get("cDscPuerto")!= null)?vDatosTitProp.get("cDscPuerto").toString():"");
           rep.comRemplaza("[cMunZF]",(vDatosTitProp.get("cDscMunicipio")!= null)?vDatosTitProp.get("cDscMunicipio").toString():"");
           rep.comRemplaza("[cEntFedZF]",(vDatosTitProp.get("cDscEntidadFed")!= null)?vDatosTitProp.get("cDscEntidadFed").toString():"");

           //Informacion de la Escritura Publica del Título de Propiedad.
           rep.comRemplaza("[cNoEscZF]",(vDatosTitProp.get("cNumTitProEscPublica") != null && vDatosTitProp.get("cNumTitProEscPublica")!= null)?vDatosTitProp.get("cNumTitProEscPublica").toString():"");
           rep.comRemplaza("[cFechaZF]",(vDatosTitProp.get("dtEscPublica") != null)?Fecha.getFechaDDMMYYYY(vDatosTitProp.getDate("dtEscPublica"),"/"):"");
           rep.comRemplaza("[cNoNotPubZF]",(vDatosTitProp.get("cNumNotariaPublica") != null && vDatosTitProp.get("cNumNotariaPublica")!= null)?vDatosTitProp.get("cNumNotariaPublica").toString():"");
           rep.comRemplaza("[cCiuZF]",((vDatosTitProp.get("cDscLocalidadEscPub") != null && vDatosTitProp.get("cDscLocalidadEscPub")!= null)?vDatosTitProp.get("cDscLocalidadEscPub").toString():"")+
                            ((vDatosTitProp.get("cDscMunicipioEscPub") != null && vDatosTitProp.get("cDscMunicipioEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():""));
           rep.comRemplaza("[cEntFedEPZF]",(vDatosTitProp.get("cDscEntidadFedEscPub") != null && vDatosTitProp.get("cDscEntidadFedEscPub")!= null)?vDatosTitProp.get("cDscMunicipioEscPub").toString():"");
           rep.comRemplaza("[cNotarioPublicoZF]",((vDatosTitProp.get("cNomRazonSocial") != null && vDatosTitProp.get("cNomRazonSocial")!= null)?vDatosTitProp.get("cNomRazonSocial").toString():"")+
                            ((vDatosTitProp.get("cApPaterno") != null && vDatosTitProp.get("cApPaterno")!= null)?vDatosTitProp.get("cApPaterno").toString():"")+
                            ((vDatosTitProp.get("cApMaterno") != null && vDatosTitProp.get("cApMaterno")!= null)?vDatosTitProp.get("cApMaterno").toString():""));

           //Informacion del Registro Publico de Comercio del Título de Propiedad.
           rep.comRemplaza("[cFecZF]",(vDatosTitProp.get("dtTitProRegPubCom") != null)?Fecha.getFechaDDMMYYYY(vDatosTitProp.getDate("dtTitProRegPubCom"),"/"):"");
           rep.comRemplaza("[cFolioZF]",(vDatosTitProp.get("cNumTitProRegPubCom") != null && vDatosTitProp.get("cNumTitProRegPubCom")!= null)?vDatosTitProp.get("cNumTitProRegPubCom").toString():"");
           rep.comRemplaza("[cFojaZF]",(vDatosTitProp.get("cNumRegPubComFoja") != null && vDatosTitProp.get("cNumRegPubComFoja")!= null)?vDatosTitProp.get("cNumRegPubComFoja").toString():"");
           rep.comRemplaza("[cToZF]",(vDatosTitProp.get("cNumRegPubComTomo") != null && vDatosTitProp.get("cNumRegPubComTomo")!= null)?vDatosTitProp.get("cNumRegPubComTomo").toString():"");
           rep.comRemplaza("[cSecZF]",(vDatosTitProp.get("cNumRegPubComSeccion") != null && vDatosTitProp.get("cNumRegPubComSeccion")!= null)?vDatosTitProp.get("cNumRegPubComSeccion").toString():"");
           rep.comRemplaza("[cLibroZF]",(vDatosTitProp.get("cNumRegPubComLibro") != null && vDatosTitProp.get("cNumRegPubComLibro")!= null)?vDatosTitProp.get("cNumRegPubComLibro").toString():"");
           rep.comRemplaza("[cVolZF]",(vDatosTitProp.get("cNumRegPubComVolumen") != null && vDatosTitProp.get("cNumRegPubComVolumen")!= null)?vDatosTitProp.get("cNumRegPubComVolumen").toString():"");
           rep.comRemplaza("[cCiudadZF]",((vDatosTitProp.get("cDscLocalidadRegPubCom") != null && vDatosTitProp.get("cDscLocalidadRegPubCom")!= null)?vDatosTitProp.get("cDscLocalidadRegPubCom").toString():"")+
                            ((vDatosTitProp.get("cDscMunicipioRegPubCom") != null && vDatosTitProp.get("cDscMunicipioRegPubCom")!= null)?vDatosTitProp.get("cDscMunicipioRegPubCom").toString():""));
           rep.comRemplaza("[cEntFedRPCZF]",(vDatosTitProp.get("cDscEntidadFedRegPubCom") != null && vDatosTitProp.get("cDscEntidadFedRegPubCom")!= null)?vDatosTitProp.get("cDscEntidadFedRegPubCom").toString():"");
         } else {
           //Informacion del Título de Propiedad.
           rep.comRemplaza("[cSupZF]","");
           rep.comRemplaza("[cLoteZF]","");
           rep.comRemplaza("[cSecZF]","");
           rep.comRemplaza("[cManZF]","");
           rep.comRemplaza("[cNoZF]","");
           rep.comRemplaza("[cCalZF]","");
           rep.comRemplaza("[cKMZF]","");
           rep.comRemplaza("[cColZF]","");
           rep.comRemplaza("[cPuertoZF]","");
           rep.comRemplaza("[cMunZF]","");
           rep.comRemplaza("[cEntFedZF]","");

           //Informacion de la Escritura Publica del Título de Propiedad.
           rep.comRemplaza("[cNoEscZF]","");
           rep.comRemplaza("[cFechaZF]","");
           rep.comRemplaza("[cNoNotPubZF]","");
           rep.comRemplaza("[cCiuZF]","");
           rep.comRemplaza("[cEntFedEPZF]","");
           rep.comRemplaza("[cNotarioPublicoZF]","");

           //Informacion del Registro Publico de Comercio del Título de Propiedad.
           rep.comRemplaza("[cFecZF]","");
           rep.comRemplaza("[cFolioZF]","");
           rep.comRemplaza("[cFojaZF]","");
           rep.comRemplaza("[cToZF]","");
           rep.comRemplaza("[cSecZF]","");
           rep.comRemplaza("[cLibroZF]","");
           rep.comRemplaza("[cVolZF]","");
           rep.comRemplaza("[cCiudadZF]","");
           rep.comRemplaza("[cEntFedRPCZF]","");
         }

         if (vRegsTituloDocumentos.size()>0){
           for(int i=0;i<vRegsTituloDocumentos.size();i++){
             TVDinRep vDatosTitDoctos = (TVDinRep) vRegsTituloDocumentos.get(i);
             int iVigencia = 0;

             switch (Integer.parseInt(vDatosTitDoctos.get("iCveTipoDocumento").toString())){

               //Titulo de Concesion de la Zona Federal Marítimo Terrestre.
               case 1:case 2:case 3:case 4:{
                 if (vDatosTitDoctos.get("lMaritimoTerrestre") != null &&
                     Integer.parseInt(vDatosTitDoctos.get("lMaritimoTerrestre").toString()) == 1){
                    rep.comRemplaza("[lMarTitConZF]","X");
                    rep.comRemplaza("[lFluvialTitConZF]","");
                 } else {
                    rep.comRemplaza("[lMarTitConZF]","");
                    rep.comRemplaza("[lFluvialTitConZF]","X");
                 }
                 rep.comRemplaza("[cNoTitConZF]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFecTitConZF]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getDateSPN(vDatosTitDoctos.getDate("dtDocumento")).toLowerCase():"");
                 rep.comRemplaza("[cDepEmiteTitConZF]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
                 rep.comRemplaza("[cObjetoTitConZF]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");

                 iVigencia = Fecha.getIntYear(vDatosTitDoctos.getDate("DTVIGENCIADOCUMENTO")) - Fecha.getIntYear(vDatosTitDoctos.getDate("DTINIVIGENCIADOCTO"));

                 rep.comRemplaza("[cVigenciaTitConZF]",""+iVigencia);

                 rep.comRemplaza("[cSupTitConZF]",(vDatosTitDoctos.get("cSuperficieDocumento")!= null)?vDatosTitDoctos.get("cSuperficieDocumento").toString():"");
                 lEntroZOFEMAT = 1;
                 break;
               }

               //Autorizacion en Materia de Impacto Ambiental.
               case 5:case 6:{
                 rep.comRemplaza("[cNoAutIA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutIA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getDateSPN(vDatosTitDoctos.getDate("dtDocumento")).toLowerCase():"");
                 rep.comRemplaza("[cDepEmiteAutIA]",(vDatosTitDoctos.get("cDepEmite")!= null)?vDatosTitDoctos.get("cDepEmite").toString():"");
                 rep.comRemplaza("[cObjetoAutIA]",(vDatosTitDoctos.get("cObjetoDocumento")!= null)?vDatosTitDoctos.get("cObjetoDocumento").toString():"");

                 iVigencia = Fecha.getIntYear(vDatosTitDoctos.getDate("DTVIGENCIADOCUMENTO")) - Fecha.getIntYear(vDatosTitDoctos.getDate("DTINIVIGENCIADOCTO"));

                 rep.comRemplaza("[cVigenciaAutIA]",""+iVigencia);
                 lEntroImpAmb = 1;
                 break;
               }

               //Franquicia de PEMEX.
               case 7:{
                 rep.comRemplaza("[cNoFPEMEX]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaFPEMEX]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaFPEMEX]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroPEMEX = 1;
                 break;
               }

               //Autorizacion de la CONAGUA.
               case 8:{
                 rep.comRemplaza("[cNoAutCONAGUA]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutCONAGUA]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaAutCONAGUA]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroCONAGUA = 1;
                 break;
               }
               //Autorizacion de la SEMARNAT.
               case 9:{
                 rep.comRemplaza("[cNoAutSEMARNAT]",(vDatosTitDoctos.get("cNumDocumento")!= null)?vDatosTitDoctos.get("cNumDocumento").toString():"");
                 rep.comRemplaza("[cFechaAutSEMARNAT]",(vDatosTitDoctos.get("dtDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtDocumento"),"/"):"");
                 rep.comRemplaza("[cVigenciaAutSEMARNAT]",(vDatosTitDoctos.get("dtVigenciaDocumento")!= null)?Fecha.getFechaDDMMYYYY(vDatosTitDoctos.getDate("dtVigenciaDocumento"),"/"):"");
                 lEntroSEMARNAT = 1;
                 break;
               }
             }
           }
         }

         if (lEntroZOFEMAT == 0){
           rep.comRemplaza("[cNoTitConZF]","");
           rep.comRemplaza("[cFecTitConZF]","");
           rep.comRemplaza("[cDepEmiteTitConZF]","");
           rep.comRemplaza("[cObjetoTitConZF]","");
           rep.comRemplaza("[cVigenciaTitConZF]","");
           rep.comRemplaza("[cSupTitConZF]","");
         }

         if (lEntroImpAmb == 0){
           rep.comRemplaza("[cNoAutIA]","");
           rep.comRemplaza("[cFechaAutIA]","");
           rep.comRemplaza("[cDepEmiteAutIA]","");
           rep.comRemplaza("[cObjetoAutIA]","");
           rep.comRemplaza("[cVigenciaAutIA]","");
         }

         if (lEntroPEMEX == 0){
           rep.comRemplaza("[cNoFPEMEX]","");
           rep.comRemplaza("[cFechaFPEMEX]","");
           rep.comRemplaza("[cVigenciaFPEMEX]","");
         }

         if (lEntroCONAGUA == 0){
           rep.comRemplaza("[cNoAutCONAGUA]","");
           rep.comRemplaza("[cFechaAutCONAGUA]","");
           rep.comRemplaza("[cVigenciaAutCONAGUA]","");

         }

         if (lEntroSEMARNAT == 0){
           rep.comRemplaza("[cNoAutSEMARNAT]","");
           rep.comRemplaza("[cFechaAutSEMARNAT]","");
           rep.comRemplaza("[cVigenciaAutSEMARNAT]","");
         }

         //Informacion de la Solicitud
         if (vRegsTituloSolicitud.size()>0){
            TVDinRep vDatosTitSolicutud = (TVDinRep) vRegsTituloSolicitud.get(0);
            rep.comRemplaza("[cNoSolicitud]",((vDatosTitSolicutud.get("iEjercicio")!= null)?vDatosTitSolicutud.get("iEjercicio").toString():"")+
                             ((vDatosTitSolicutud.get("iNumSolicitud")!= null)?vDatosTitSolicutud.get("iNumSolicitud").toString():""));
            rep.comRemplaza("[cLugarSolicitud]",((vDatosTitSolicutud.get("cCalleyNo")!= null)?vDatosTitSolicutud.get("cCalleyNo").toString():"")+
                            ((vDatosTitSolicutud.get("cColonia")!= null)?vDatosTitSolicutud.get("cColonia").toString():"")+
                            ((vDatosTitSolicutud.get("cCodPostal")!= null)?vDatosTitSolicutud.get("cCodPostal").toString():"")+
                            ((vDatosTitSolicutud.get("cDscMunicipio")!= null)?vDatosTitSolicutud.get("cDscMunicipio").toString():"")+
                            ((vDatosTitSolicutud.get("cDscEntidadFed")!= null)?vDatosTitSolicutud.get("cDscEntidadFed").toString():""));
            rep.comRemplaza("[cFechaSolicitud]",(vDatosTitSolicutud.get("dtRegistro")!= null)?Fecha.getDateSPN(vDatosTitSolicutud.getDate("dtRegistro")).toLowerCase():"");

         } else {
           rep.comRemplaza("[cNoSolicitud]","");
           rep.comRemplaza("[cLugarSolicitud]","");
           rep.comRemplaza("[cFechaSolicitud]","");
         }

         //Uso Publico o Particular.
         if(vDatos.getInt("iCveUsoTitulo") == 1){
           rep.comRemplaza("[U1]","X");
           rep.comRemplaza("[U2]"," ");
         }
         if(vDatos.getInt("iCveUsoTitulo") == 2){
           rep.comRemplaza("[U1]"," ");
           rep.comRemplaza("[U2]","X");
         }

         //Objeto del Título y Ubicacion del Area Solicitada.
         rep.comRemplaza("[cObjetoTitulo]",(vDatos.get("cObjetoTitulo")!= null)?vDatos.get("cObjetoTitulo").toString():"");
         rep.comRemplaza("[cUbiZonFedTerrestre]",(vDatos.get("cZonaFedAfectadaTerrestre")!= null)?vDatos.get("cZonaFedAfectadaTerrestre").toString():"");
         rep.comRemplaza("[cUbiZonFedAgua]",(vDatos.get("cZonaFedAfectadaAgua")!= null)?vDatos.get("cZonaFedAfectadaAgua").toString():"");

         if (vRegsTituloUbicacion.size()>0){
            TVDinRep vDatosTitUbi = (TVDinRep) vRegsTituloUbicacion.get(0);
            rep.comRemplaza("[cCalleFRP]",(vDatosTitUbi.get("cCalleTitulo")!= null)?vDatosTitUbi.get("cCalleTitulo").toString():"");
            rep.comRemplaza("[cKmFRP]",(vDatosTitUbi.get("cKm")!= null)?vDatosTitUbi.get("cKm").toString():"");
            rep.comRemplaza("[cColFRP]",(vDatosTitUbi.get("cColoniaTitulo")!= null)?vDatosTitUbi.get("cColoniaTitulo").toString():"");
            rep.comRemplaza("[cPuertoFRP]",(vDatosTitUbi.get("cDscPuerto")!= null)?vDatosTitUbi.get("cDscPuerto").toString():"");
            rep.comRemplaza("[cMunFRP]",(vDatosTitUbi.get("cDscMunicipio")!= null)?vDatosTitUbi.get("cDscMunicipio").toString():"");
            rep.comRemplaza("[cEntFedFRP]",(vDatosTitUbi.get("cDscEntidadFed")!= null)?vDatosTitUbi.get("cDscEntidadFed").toString():"");

            //Ubicación del Area Solicitada.
            rep.comRemplaza("[U3]","X");

            if (vDatosTitUbi.getInt("lDentroRecintoPort") == 1){
              rep.comRemplaza("[U4]","X");
              rep.comRemplaza("[U5]"," ");
            } else {
              rep.comRemplaza("[U4]"," ");
              rep.comRemplaza("[U5]","X");
            }
         } else {
           rep.comRemplaza("[cCalleFRP]","");
           rep.comRemplaza("[cKmFRP]","");
           rep.comRemplaza("[cColFRP]","");
           rep.comRemplaza("[cPuertoFRP]","");
           rep.comRemplaza("[cMunFRP]","");
           rep.comRemplaza("[cEntFedFRP]","");

           //Ubicación del Area Solicitada.
           rep.comRemplaza("[U3]"," ");
           rep.comRemplaza("[U4]"," ");
           rep.comRemplaza("[U5]"," ");
         }

         //Obras del Título.
         int lPropNacional= 0,lConst=0,lOper=0;
         String cObras = "";
         if (vRegsTituloObras.size()>0){
           for(int i=0;i<vRegsTituloObras.size();i++){
             TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(i);
             if (cObras.compareTo("") != 0)
               cObras = cObras  + ", " + vDatosTitObras.getString("cDscObra");
             else
               cObras = vDatosTitObras.getString("cDscObra");

             if (vDatosTitObras.getInt("lPropiedadNacional") == 1 && lPropNacional == 0){
                rep.comRemplaza("[o1]","X");
                lPropNacional = 1;
             }
             if (vDatosTitObras.getInt("lConstruccion") == 1 && lConst == 0){
                rep.comRemplaza("[o2]","X");
                lConst = 1;
             }
             if (vDatosTitObras.getInt("lOperacion") == 1 && lOper == 0){
                rep.comRemplaza("[o3]","X");
                lOper = 1;
             }
           }
           if (cObras.compareTo("")!= 0)
             cObras = cObras + ".";
           rep.comRemplaza("[cObras]",cObras);

           TVDinRep vDatosTitObras = (TVDinRep) vRegsTituloObras.get(0);
           rep.comRemplaza("[cTiempoEstimadoObra]",(vDatosTitObras.get("cTiempoEstEjecucion")!= null)?vDatosTitObras.get("cTiempoEstEjecucion").toString():"");
         }
         if (cObras.compareTo("")==0){
             rep.comRemplaza("[cObras]","");
           rep.comRemplaza("[cTiempoEstimadoObra]","");
         }

         //Información de los Planos
         try{
           vRegsTituloPlano = super.FindByGeneric("",
               " SELECT PLA.CPLANONUM, PLA.CDENOMINACION, PLA.DTPLANO FROM CPASOLTITULO SOLT " +
               " JOIN TRAPLANOXTRAMITE PLNT ON PLNT.IEJERCICIO = SOLT.IEJERCICIO AND PLNT.INUMSOLICITUD = SOLT.INUMSOLICITUD " +
               " JOIN OMPPLANO PLA ON PLA.ICVEPLANO = PLNT.ICVEPLANO AND PLA.LAPROBADOXOBRAS = 1 " +
               " WHERE SOLT.ICVETITULO = " + iCveTitulo,
               dataSourceName);
         }catch(SQLException ex){
           ex.printStackTrace();
          cMensaje = ex.getMessage();
         }catch(Exception ex2){
           ex2.printStackTrace();
          cMensaje += ex2.getMessage();
         }

         String cPlanos = "";
         for(int i=0;i<vRegsTituloPlano.size();i++){
           TVDinRep vDatosTitPlanos = (TVDinRep) vRegsTituloPlano.get(i);

           cPlanos = cPlanos + ((vDatosTitPlanos.get("CPLANONUM")!= null)?vDatosTitPlanos.get("CPLANONUM").toString():"") + " de " +
                     Fecha.getDateSPN(vDatosTitPlanos.getDate("DTPLANO")) + ", " + ((vDatosTitPlanos.get("CDENOMINACION")!= null)?vDatosTitPlanos.get("CDENOMINACION").toString().toLowerCase():"") + ", ";
         }

      rep.comRemplaza("[cPlanos]",cPlanos);

      //Monto de Inversion.
      rep.comRemplaza("[cMontoInversion]",(vDatos.get("cMontoInversion")!= null)?vDatos.get("cMontoInversion").toString():"");

      rep.comRemplaza("[cFecExpedicion]",Fecha.getDateSPN(Fecha.TodaySQL()).toLowerCase());
      rep.comRemplaza("[cVigenciaTitulo]",""+iVigenciaTitulo);
      rep.comRemplaza("[cRepLegalCompleto]",cRepLegalCompleto);
      rep.comRemplaza("[cNumTitulo]",(vDatos.get("cNumTitulo")!= null)?vDatos.get("cNumTitulo").toString():"");

    } else {
      //System.out.print("No Encontro Datos");
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return rep.getEtiquetas(true);
  }

}


package gob.sct.sipmm.dao.ws;

//Java imports
import java.util.*;
import java.net.*;
import com.micper.ingsw.*;
import com.micper.sql.DAOBase;
import ingresosws.generated.*;
import gob.sct.ingresosws.ws.ConAreaRec.*;
import gob.sct.ingresosws.ws.ConUsrIng.*;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

/**
 * <p>Title: TDIngresos</p>
 * <p>Description: Clase que llama a los m�todos de los WebServices de Consulta y que se utiliza
 * como interfaz para evitar el llamado de primitivas, regresando colecciones con Value Objects
 * de TVCiudadano</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Tecnolog�a Inred S.A. de C.V.</p>
 * @author
 * @version 1.0
 */

public class TDIngresos extends DAOBase {
  public final static int TODAS = 0;
  public final static int S_TARIFAS = 1;
  public final static int S_PORCENTAJES = 2;
  public final static int S_TARIFAS_DIF_CERO = 3;
  public final static int RFC = 1;
  public final static int CURP = 2;
  public final static int RPA = 3;
  private TParametro parametros = null;
  private String ingWSURL = "";
  private String cUsuario = "";
  private String cContrasenia = "";

  /**
       * Constructor que configura a la clase por sistema, para el llamado del Logger
   */
  public TDIngresos() {
    this.setSistema("44");
    parametros = new TParametro("44");
    ingWSURL = parametros.getPropEspecifica("URLWSING");
  }

  /**
   * M�todo que busca todas las Tarifas
   * @param pIEjercicio Ejercicio.
   * @param pICveCategoria Clave Categoria (TODAS o un valor dado).
   * @param pICveConcepto Clave Concepto (TODAS o un valor dado).
   * @param pITipoRef Clave TipoRef (TODAS, S_TARIFAS, S_PORCENTAJES, S_TARIFAS_DIF_CERO).
   * @return Colecci�n de Tarifas encontrados.
   */
  public Vector findTarifaConcepto(int pIEjercicio, int pICveCategoria, int pICveConcepto, int pITipoRef) {
    Vector vResultado = new Vector();
    Object obj[] = null;
    TVDinRep vDinRep;

    try {
      String Usuario = parametros.getPropEspecifica("ConWSIngUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSIngPwd");
      int Recaudacion = Integer.parseInt(parametros.getPropEspecifica("WSIngRecauda"));
      if (ingWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = ingWSURL + "ConTarifaConcepto?WSDL";
        TDConTarifaConcepto service = new TDConTarifaConcepto_Impl( wsdlUrl );
        TDConTarifaConceptoPort conTarifaConcepto = service.getTDConTarifaConceptoPort();
        obj = conTarifaConcepto.findTarifaConcepto(Usuario,Contrasenia,pIEjercicio,Recaudacion,pICveCategoria,pICveConcepto,pITipoRef);
        for(int i=0;obj!=null && i<obj.length;i++){
            vDinRep = new TVDinRep();
            TVConceptoII vC = (TVConceptoII) obj[i];
          if(vC.getDtVigencia()!=null)
            vDinRep.put("cVigencia",URLDecoder.decode(vC.getDtVigencia(),"UTF-8"));
         // if(vC.getICveCategoria()!=null)
         //   vDinRep.put("iCveCategoria",vC.getICveCategoria());
          if(vC.getICveConcepto()!=null)
            vDinRep.put("iCveConcepto",vC.getICveConcepto());
          if(vC.getIReferencia()!=null)
            vDinRep.put("iRef",vC.getIReferencia());
          if(vC.getDTarifaCAjuste()!=null)
              vDinRep.put("iTarifaCAjuste",vC.getDTarifaCAjuste());
          if(vC.getDTarifaSAjuste()!=null)
            vDinRep.put("iTarifaSAjuste",vC.getDTarifaSAjuste());
          if(vC.getCDscConcepto()!=null)
            vDinRep.put("cDscConcepto",URLDecoder.decode(vC.getCDscConcepto(),"UTF-8"));
          if(vC.getICveCategoria()!=null)
            vDinRep.put("iCveCategoria",vC.getICveCategoria());
          if(vC.getLPorcentaje()!=null)
            vDinRep.put("lPorcentaje",vC.getLPorcentaje());
          if(vC.getLTarifa()!=null)
            vDinRep.put("lTarifa",vC.getLTarifa());
          vResultado.addElement(vDinRep);
        }
      }
      else {
        info("findTarifaConcepto-WSIN no cuenta con ingWSURL.");
      }
    }
    catch (Exception ex) {
      warn("findTarifaConcepto-WSIN", ex);
    }
    finally {
    }
    return vResultado;
  }

  /**
   * M�todo que genera un movimiento
   * @param iReferencia Referecia.
   * @param iUnidades Unidades.
   * @param iCveUsuario Clave del Usuario (cliente) de la SCT.
   * @param cObservacion Observaciones.
   * @return Referencia del movimiento actualizado.
   */
  public Object generaMov(int iReferencia,int iUnidades,int iCveUsuario,String cObservacion, int iCveUAdmva, int iCveOficina) {
    Vector vResultado = new Vector();
    Object obj[] = null;
    String sResultado = "";
    try {

      String Usuario = parametros.getPropEspecifica("ConWSIngUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSIngPwd");
      //int iCveUAdmva = Integer.parseInt(parametros.getPropEspecifica("WSIngUAdmva"));
      //int iCveOficina = Integer.parseInt(parametros.getPropEspecifica("WSIngOficina"));
      if (ingWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = ingWSURL + "GenMovimiento?WSDL";
        TDGenMovimiento service = new TDGenMovimiento_Impl( wsdlUrl );
        TDGenMovimientoPort genMovimiento = service.getTDGenMovimientoPort();
        if (cObservacion != null)   cObservacion = URLEncoder.encode(cObservacion, "UTF-8");
        obj = genMovimiento.generaMov(Usuario,Contrasenia,iReferencia,iUnidades,iCveUAdmva,iCveOficina,iCveUsuario,cObservacion);
        for(int i=0;obj!=null && i<obj.length;i++){
            TVMovGenerado vMG = (TVMovGenerado) obj[i];
            if(vMG.getCRefAlfanum()!=null)
                sResultado = vMG.getCRefAlfanum();
        }
      }
      else {
        info("generaMov-WSIN no cuenta con ingWSURL.");
      }
    }
    catch (Exception ex) {
      warn("generaMov-WSIN", ex);
    }
    finally {
    }
    return sResultado;
  }

  /**
   * M�todo que inserta un registro de usuario
   * @param cRPA Dato RPA.
   * @param cRFC Dato RFC.
   * @param cCURP Dato CURP.
   * @param cNombre Nombre.
   * @param cApPaterno Apellido Paterno.
   * @param cApMaterno Apellido Materno.
   * @param lFisicoMoral Persona Fisica o Moral.
   * @param cCalle Dato Calle.
   * @param cColonia Dato Colonia.
   * @param iCP Codigo Postal.
   * @param cTelefono Dato Telefono.
   * @param iCvePais Clave Pais.
   * @param iCveEntidadFed Clave Entidad Federativa.
   * @param iCveMunicipio Clave Municipio.
   * @param cCiudad Dato Ciudad.
   * @return Clave del dato insertado.
   */
/*  public Object insertUsr(String cRPA,String cRFC,String cCURP,String cNombre,String cApPaterno,String cApMaterno,int lFisicoMoral,
                      String cCalle,String cColonia,int iCP,String cTelefono,int iCvePais,int iCveEntidadFed,int iCveMunicipio,String cCiudad) {*/
public Object insertUsr(TVDinRep vdDatosSol) {
    Vector vResultado = new Vector();
    String cRPA="", cRFC="", cCURP="", cNombre="", cApPaterno="", cApMaterno="", cCalle="", cColonia="", cTelefono="", cCiudad="";
    int iCvePais = vdDatosSol.getInt("iCvePais");
    int iCP = vdDatosSol.getInt("cCodPostal");
    int iCveEntidadFed = vdDatosSol.getInt("iCveEntidadFed");
    int iCveMunicipio = vdDatosSol.getInt("iCveMunicipio");
    int lFisicoMoral = vdDatosSol.getInt("iTipoPersona");
    long obj = 0;
    String sResultado = "";
    try {
      String Usuario = parametros.getPropEspecifica("ConWSIngUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSIngPwd");
      int iCveUAdmva = Integer.parseInt(parametros.getPropEspecifica("WSIngUAdmva"));
      //int iCveUAdmva = 662; unidad administrativa sipymm
      if (ingWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = ingWSURL + "ConUsrIng?WSDL";
        TDConUsrIng service = new TDConUsrIng_Impl( wsdlUrl );
        TDConUsrIngPort conUsrIng = service.getTDConUsrIngPort();
        if (vdDatosSol.getString("cRPA") != null)   cRPA = URLEncoder.encode(vdDatosSol.getString("cRPA"), "UTF-8");
        if (vdDatosSol.getString("cRFC") != null)   cRFC = URLEncoder.encode(vdDatosSol.getString("cRFC"), "UTF-8");
        if (vdDatosSol.getString("cCURP") != null)  cCURP = URLEncoder.encode(vdDatosSol.getString("cCURP"), "UTF-8");
        if (vdDatosSol.getString("cNombre") != null)   cNombre = URLEncoder.encode(vdDatosSol.getString("cNombre"), "UTF-8");
        if (vdDatosSol.getString("cApPaterno") != null)   cApPaterno = URLEncoder.encode(vdDatosSol.getString("cApPaterno"), "UTF-8");
        if (vdDatosSol.getString("cApMaterno") != null)   cApMaterno = URLEncoder.encode(vdDatosSol.getString("cApMaterno"), "UTF-8");
        if (vdDatosSol.getString("cCalle") != null){
          cCalle = vdDatosSol.getString("cCalle");
        if(vdDatosSol.getString("cNumExterior") != null &&
           !vdDatosSol.getString("cNumExterior").equals(""))
          cCalle += " No. " + vdDatosSol.getString("cNumExterior");
        if(vdDatosSol.getString("cNumInterior") != null &&
           !vdDatosSol.getString("cNumInterior").equals(""))
          cCalle += " Int. " + vdDatosSol.getString("cNumInterior");
          cCalle = URLEncoder.encode(cCalle, "UTF-8");
        }
        if (vdDatosSol.getString("cColonia") != null)   cColonia = URLEncoder.encode(vdDatosSol.getString("cColonia"), "UTF-8");
        if (vdDatosSol.getString("cTelefono") != null)   cTelefono = URLEncoder.encode(vdDatosSol.getString("cTelefono"), "UTF-8");
        cCiudad = URLEncoder.encode("", "UTF-8");
        obj = conUsrIng.insertUsr(Usuario,Contrasenia,iCveUAdmva,cRPA,cRFC,cCURP,cNombre,cApPaterno,cApMaterno,lFisicoMoral,
                            cCalle,cColonia,iCP,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,cCiudad);
        sResultado = obj+"";
      }
      else {
        info("generaMov-WSIN no cuenta con ingWSURL.");
      }
    }
    catch (Exception ex) {
      warn("generaMov-WSIN", ex);
      ex.printStackTrace();
    }
    finally {
    }
    return sResultado;
  }

  /**
   * M�todo que busca un usuario en base al RFC, CURP, RPA
   * @param cValor Cadena conel valor por el cual se va a realizar la b�squeda (RFC, CURP, RPA).
   * @param iTipoBusqueda Por qu� se va a realizar la b�squeda, 1 por RFC, 2 por CURP, 3 por RPA.
   * @return datos del valor buscado.
   */
  public Object findRFC(String cValor,int iTipoBusqueda) {
    Vector vResultado = new Vector();
    Object obj[] = null;
    TVDinRep vUsuario;
    try {
      String Usuario = parametros.getPropEspecifica("ConWSIngUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSIngPwd");
      int iCveUAdmva = Integer.parseInt(parametros.getPropEspecifica("WSIngUAdmva"));
      if (ingWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = ingWSURL + "ConUsrIng?WSDL";
        TDConUsrIng service = new TDConUsrIng_Impl( wsdlUrl );
        TDConUsrIngPort conUsrIng = service.getTDConUsrIngPort();
        if (cValor != null)   cValor = URLEncoder.encode(cValor, "UTF-8");
        obj = conUsrIng.findRFC(Usuario,Contrasenia,cValor,iTipoBusqueda);
        for(int i=0;obj!=null && i<obj.length;i++){
            TVINGUsuario vU = (TVINGUsuario) obj[i];
            vUsuario = new TVDinRep();
            if(vU.getCApMaterno()!=null)
                vUsuario.put("cApMaterno",URLDecoder.decode(vU.getCApMaterno(),"UTF-8").replaceAll("'","")); //0
            if(vU.getCApPaterno()!=null)
                vUsuario.put("cApPaterno",URLDecoder.decode(vU.getCApPaterno(),"UTF-8").replaceAll("'","")); //1
            if(vU.getCCURP()!=null)
                vUsuario.put("cCurp",URLDecoder.decode(vU.getCCURP(),"UTF-8").replaceAll("'","")); //2
            if(vU.getCCalle()!=null)
                vUsuario.put("cCalle",URLDecoder.decode(vU.getCCalle(),"UTF-8").replaceAll("'","")); //3
            if(vU.getCCiudad()!=null)
                vUsuario.put("cCiudad",URLDecoder.decode(vU.getCCiudad(),"UTF-8").replaceAll("'","")); //4
            if(vU.getCColonia()!=null)
                vUsuario.put("cColonia",URLDecoder.decode(vU.getCColonia(),"UTF-8").replaceAll("'","")); //5
            if(vU.getCDscEntidadFed()!=null)
                vUsuario.put("cDscEntidadFed",URLDecoder.decode(vU.getCDscEntidadFed(),"UTF-8").replaceAll("'","")); //6
            if(vU.getCDscMunicipio()!=null)
                vUsuario.put("cDscMunicipio",URLDecoder.decode(vU.getCDscMunicipio(),"UTF-8").replaceAll("'","")); //7
            if(vU.getCDscPais()!=null)
                vUsuario.put("cDscPais",URLDecoder.decode(vU.getCDscPais(),"UTF-8").replaceAll("'",""));  //8
            if(vU.getCNombre()!=null)
                vUsuario.put("cNombre",URLDecoder.decode(vU.getCNombre(),"UTF-8").replaceAll("'",""));  //9
            if(vU.getCRFC()!=null)
                vUsuario.put("cRFC",URLDecoder.decode(vU.getCRFC(),"UTF-8").replaceAll("'",""));   //10
            if(vU.getCRPA()!=null)
                vUsuario.put("cRPA",URLDecoder.decode(vU.getCRPA(),"UTF-8").replaceAll("'",""));  //11
            if(vU.getCTelefono()!=null)
                vUsuario.put("cTel",URLDecoder.decode(vU.getCTelefono(),"UTF-8").replaceAll("'",""));   //12
            if(vU.getCNombre()!=null){
              String Nombre = URLDecoder.decode(vU.getCNombre(),"UTF-8").replaceAll("'","");
              if(vU.getCApPaterno()!=null)
                Nombre += " "+URLDecoder.decode(vU.getCApPaterno(),"UTF-8").replaceAll("'","");
              if(vU.getCApMaterno()!=null)
                Nombre += " "+URLDecoder.decode(vU.getCApMaterno(),"UTF-8").replaceAll("'","");
              vUsuario.put("cNombreConcat",Nombre); //13
              vUsuario.put("cCP",vU.getICP()); //14

              String cDom = URLDecoder.decode(vU.getCCalle(),"UTF-8");
              if(!URLDecoder.decode(vU.getCColonia(),"UTF-8").equalsIgnoreCase("null"))
                cDom += " COL. "+URLDecoder.decode(vU.getCColonia(),"UTF-8");
              if(!URLDecoder.decode(vU.getCCiudad(),"UTF-8").equalsIgnoreCase("null") &&
                 !URLDecoder.decode(vU.getCCiudad(),"UTF-8").equals(""))
                cDom += ", " + URLDecoder.decode(vU.getCCiudad(),"UTF-8");
              if(!URLDecoder.decode(vU.getCDscMunicipio(),"UTF-8").equalsIgnoreCase("null"))
                cDom += ", " + URLDecoder.decode(vU.getCDscMunicipio(),"UTF-8");
              if(!URLDecoder.decode(vU.getCDscEntidadFed(),"UTF-8").equalsIgnoreCase("null"))
                cDom += ", " + URLDecoder.decode(vU.getCDscEntidadFed(),"UTF-8");
              if(vU.getICP()>0)
                cDom += " C.P. " + vU.getICP();
              vUsuario.put("cDomicilio",cDom.replaceAll("'",""));   //15
            }
            vUsuario.put("iCvePersona",vU.getICveUsuario()); //16
              vResultado.addElement(vUsuario);

        }
      }
      else {
        info("generaMov-WSIN no cuenta con ingWSURL.");
      }
    }
    catch (Exception ex) {
      warn("generaMov-WSIN", ex);
    }
    finally {
    }
    return vResultado;
  }

  /**
   * M�todo que busca un usuario en base a su clave
   * @param iCveUsuario Clave del usuario.
   * @return datos del valor buscado.
   */
  public Object getUsuarioSCT(int iCveUsuario) {
    TVINGUsuario obj = null;
    try {
      String Usuario = parametros.getPropEspecifica("ConWSIngUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSIngPwd");
      int iCveUAdmva = Integer.parseInt(parametros.getPropEspecifica("WSIngUAdmva"));
      if (ingWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = ingWSURL + "ConUsrIng?WSDL";
        TDConUsrIng service = new TDConUsrIng_Impl( wsdlUrl );
        TDConUsrIngPort conUsrIng = service.getTDConUsrIngPort();
        obj = conUsrIng.getUsuarioSCT(Usuario,Contrasenia,iCveUsuario);
        if(obj.getCApMaterno()!=null)
            obj.setCApMaterno(URLDecoder.decode(obj.getCApMaterno(),"UTF-8"));
        if(obj.getCApPaterno()!=null)
            obj.setCApPaterno(URLDecoder.decode(obj.getCApPaterno(),"UTF-8"));
        if(obj.getCCURP()!=null)
            obj.setCCURP(URLDecoder.decode(obj.getCCURP(),"UTF-8"));
        if(obj.getCCalle()!=null)
            obj.setCCalle(URLDecoder.decode(obj.getCCalle(),"UTF-8"));
        if(obj.getCCiudad()!=null)
            obj.setCCiudad(URLDecoder.decode(obj.getCCiudad(),"UTF-8"));
        if(obj.getCColonia()!=null)
            obj.setCColonia(URLDecoder.decode(obj.getCColonia(),"UTF-8"));
        if(obj.getCDscEntidadFed()!=null)
            obj.setCDscEntidadFed(URLDecoder.decode(obj.getCDscEntidadFed(),"UTF-8"));
        if(obj.getCDscMunicipio()!=null)
            obj.setCDscMunicipio(URLDecoder.decode(obj.getCDscMunicipio(),"UTF-8"));
        if(obj.getCDscPais()!=null)
            obj.setCDscPais(URLDecoder.decode(obj.getCDscPais(),"UTF-8"));
        if(obj.getCNombre()!=null)
            obj.setCNombre(URLDecoder.decode(obj.getCNombre(),"UTF-8"));
        if(obj.getCRFC()!=null)
            obj.setCRFC(URLDecoder.decode(obj.getCRFC(),"UTF-8"));
        if(obj.getCRPA()!=null)
            obj.setCRPA(URLDecoder.decode(obj.getCRPA(),"UTF-8"));
        if(obj.getCTelefono()!=null)
            obj.setCTelefono(URLDecoder.decode(obj.getCTelefono(),"UTF-8"));
      }
      else {
        info("generaMov-WSIN no cuenta con ingWSURL.");
      }
    }
    catch (Exception ex) {
      warn("generaMov-WSIN", ex);
    }
    finally {
    }
    return obj;
  }

}

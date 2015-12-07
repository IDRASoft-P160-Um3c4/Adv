package gob.sct.sipmm.dao.ws;

//Java imports
import java.util.*;
import java.net.*;
import com.micper.ingsw.*;
import com.micper.sql.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.TVDinRep;
import cisws.generated.*;

/**
 * <p>Title: TDCis</p>
 * <p>Description: Clase que llama a los métodos de los WebServices del CIS </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Tecnología Inred S.A. de C.V.</p>
 * @author Gabriela Pérez Espinoza.<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDCis extends DAOBase {

  private TParametro parametros = null;
  private String cisWSURL = "";
  /**
   * Constructor que configura a la clase por sistema
   */
  public TDCis() {
    this.setSistema("44");
    parametros = new TParametro("44");
    cisWSURL = parametros.getPropEspecifica("URLWSCis");
  }

  /**
   * Método que consulta las citas de un área (SIPYMM)
   * @param pCInicio Inicio del periodo a consultar.
   * @param pCFin Fin del periodo a consultar.
   * @return vcAgenda Vector con el grupo de días que cumplen con el criterio de búsqueda.
   */
  public Vector consultaCitas (String pCInicio, String pCFin, String pCParam) {
     Vector vResultado = new Vector ();
     Object obj[] = null;
    try {
      String Usuario = parametros.getPropEspecifica("ConWSCisUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSCisPwd");
      if (cisWSURL.compareTo("") != 0) {

        String wsdlUrl = cisWSURL + "ConsultaCitas?WSDL";
        WSConsultaCitas service = new WSConsultaCitas_Impl( wsdlUrl );
        WSConsultaCitasPort wsConsultaCitas = service.getWSConsultaCitasPort();
        obj = wsConsultaCitas.consultaCitas(Usuario,Contrasenia,pCInicio,pCFin,pCParam);
        for(int i=0;obj!=null && i<obj.length;i++){
           TVDinRep vDatos = (TVDinRep) obj[i];
           vResultado.addElement(vDatos);
        }
      }
      else {
        info("WSConsultaCitas no cuenta con cisWSURL.");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      warn("WSConsultaCitas", ex);
    }
    finally {
    }
    return vResultado;
  }

  /**
   * Método que permite la consulta de interesados a través del SIPYMM
   * @param iTipoBusqueda Especifica el campo por el que se realizará la b{usqueda y debe ser alguno
   * de los siguientes valores: 1 = Clave del Interesado, 2 = RFC, 3 = CURP.
   * @param cBusca  Cadena sobre la que se efectuar{a la búsqueda.
   * @return Grupo de interesados que cumplen con el criterio de búsqueda.
   */
  public Vector consultaInteresado (int iTipoBusqueda, String cBusca) {
    Vector vResultado = new Vector();
    Object obj[] = null;
    try {
      String Usuario = parametros.getPropEspecifica("ConWSCisUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSCisPwd");
      if (cisWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = cisWSURL + "ConsultaInteresado?WSDL";
        WSConsultaInteresado service = new WSConsultaInteresado_Impl( wsdlUrl );
        WSConsultaInteresadoPort wsConsultaInteresado = service.getWSConsultaInteresadoPort();
        if (cBusca != null)   cBusca = URLEncoder.encode(cBusca, "UTF-8");
        obj = wsConsultaInteresado.consultaInteresado (Usuario,Contrasenia, iTipoBusqueda, cBusca);
        for(int i=0;obj!=null && i<obj.length;i++){
          TVDinRep vDatos = (TVDinRep) obj[i];
          vResultado.addElement(vDatos);
        }
      }
      else {
        info("consultaInteresado no cuenta con cisWSURL.");
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
   * Se encarga de realizar el llamado para buscar por Id de Interesado
   * @param cBusca String Cadena correspondiente al Id del Interesado
   * @return Vector Vector de objetos de las coincidencias encontradas.
   */
  public Vector consultaInteresadoId (String cBusca) {
    return this.consultaInteresado(1, cBusca);
  }

  /**
   * Se encarga de realizar el llamado para buscar por RFC de Interesado
   * @param cBusca String Cadena correspondiente al RFC del Interesado
   * @return Vector Vector de objetos de las coincidencias encontradas.
   */
  public Vector consultaInteresadoRFC (String cBusca) {
    return this.consultaInteresado(2, cBusca);
  }

  /**
   * Se encarga de realizar el llamado para buscar por la CURP de Interesado
   * @param cBusca String Cadena correspondiente a la CURP del Interesado
   * @return Vector Vector de objetos de las coincidencias encontradas.
   */
  public Vector consultaInteresadoCURP (String cBusca) {
    return this.consultaInteresado(3, cBusca);
  }

  /**
   * Método que permite la inserción de Citas a través del SIPYMM
   * @param vCita Vector con las citas a insertar.
   * @param iCveInteresado Clave del interesado.
   * @param iNumTramites Número de trámites a realizar en la cita
   * @return Clave de la cita asignada.
   */
  public int insertaCita(int iCveTramite,int iCveInteresado, int iNumTramites, String cDscCita, int iCveAreaCis) {
    int iResultado = 0;
    try {
      String Usuario = parametros.getPropEspecifica("ConWSCisUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSCisPwd");
      if (cisWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = cisWSURL + "InsertaCita?WSDL";
        WSInsertaCita service = new WSInsertaCita_Impl( wsdlUrl );
        WSInsertaCitaPort wsInsertaCita = service.getWSInsertaCitaPort();
        TVDinRep vDatos = new TVDinRep ();
        vDatos.put("iCveTramite",iCveTramite);
        vDatos.put("iCveInteresado",iCveInteresado);
        vDatos.put("iNumTramites",iNumTramites);
        iResultado = wsInsertaCita.insertaCita(Usuario,Contrasenia,iCveTramite,0,1,cDscCita,iCveAreaCis);
      }
      else {
        info("insertaCita no cuenta con cisWSURL.");
      }
    }
    catch (Exception ex) {
      warn("insertaCita", ex);
    }
    finally {
    }
    return iResultado;
  }


  /**
   * Método que permite la inserción del Estado de un Trámite de una cita a través del SIPYMM
   * @param iCveCita Clave de la Cita a la que està asignado el trámite a actualizar.
   * @param iCveTramite Clave del trámite en el catálogo de trámites.
   * @param iNoTramite Clave del trámite específico en curso.
   * @param iCveEstado Clave del nuevo estado del trámite.
   * @param cObservacion Observaciones pertinentes al trámite para el nuevo estado.
   * @return Valor lógico que indica si el estado fue asignado o no.
   */
  public boolean insertaEstadoTramite(int iCveCita,int iCveTramite,int iNoTramite,int iCveEstado,String cObservacion, int iCveAreaCis) {
    boolean lResultado = false;
    try {
      String Usuario = parametros.getPropEspecifica("ConWSCisUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSCisPwd");
      if (cisWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = cisWSURL + "InsertaEstadoTramite?WSDL";
        WSInsertaEstadoTramite service = new WSInsertaEstadoTramite_Impl( wsdlUrl );
        WSInsertaEstadoTramitePort wsInsertaEstadoTramite = service.getWSInsertaEstadoTramitePort();
        if(cObservacion != null){
          if(cObservacion.length() > 250)
            cObservacion = cObservacion.substring(0,249);
            //cObservacion = URLEncoder.encode(cObservacion,"UTF-8");
        }
        
        //System.out.print(">>>  Observacion:  "+cObservacion);

        lResultado = wsInsertaEstadoTramite.insertaEstadoTramite(Usuario,Contrasenia, iCveCita, iCveTramite, iNoTramite, iCveEstado, cObservacion,iCveAreaCis);
        
        //System.out.print(">>>  etapa:  "+ iCveEstado);
        //System.out.print(">>>  Resultado:  "+lResultado);

      }
      else {
        info("insertaEstadoTramite no cuenta con cisWSURL.");
      }
    }
    catch (Exception ex) {
      warn("insertaEstadoTramite", ex);
    }
    finally {
    }
    return lResultado;
  }




  /**
   * Método que permit la inserción de Interesados a través del SIPYMM
   * @param cRFC Dato Registro Federal de Contribuyentes.
   * @param cCURP Dato Clave Única del Registro de Población.
   * @param cNombre Nombre.
   * @param cApPaterno Apellido Paterno.
   * @param cApMaterno Apellido Materno.
   * @param iCvePais Clave del Pais de residencia.
   * @param iCveEntidadFed Clave Entidad Federativa de residencia.
   * @param iCveMunicipio Clave Municipio de residencia.
   * @param cColonia Dato Colonia de residencia.
   * @param cDomicilio Dato Domicilio de residencia.
   * @param cCP Codigo Postal correspondiente al domicilio de residencia.
   * @param cTelefono Dato Telefono particular.
   * @param cEMail Dirección de correo electrónico.
   * @return Clave del interesado insertado.
*/
  public int insertaInteresado(String cRFC,String cCURP,String cNombre,String cAPPaterno,String cAPMaterno,
                                  int iCvePais,int iCveEntidadFed,int iCveMunicipio,String cColonia,
                                  String cDomicilio,String cCP,String cTelefono, String cEMail) {
    int iResultado = 0;
    try {
      String Usuario = parametros.getPropEspecifica("ConWSCisUsr");
      String Contrasenia = parametros.getPropEspecifica("ConWSCisPwd");

      if (cisWSURL.compareTo("") != 0) {

        String wsdlUrl = cisWSURL + "InsertaInteresado?WSDL";
        WSInsertaInteresado service = new WSInsertaInteresado_Impl( wsdlUrl );
        WSInsertaInteresadoPort wsInsertaInteresado = service.getWSInsertaInteresadoPort();

        if (cRFC != null)    cRFC = URLEncoder.encode(cRFC, "UTF-8");
        if (cCURP != null)   cCURP = URLEncoder.encode(cCURP, "UTF-8");
        if (cNombre != null)   cNombre = URLEncoder.encode(cNombre, "UTF-8");
        if (cAPPaterno != null)   cAPPaterno = URLEncoder.encode(cAPPaterno, "UTF-8");
        if (cAPMaterno != null)   cAPMaterno = URLEncoder.encode(cAPMaterno, "UTF-8");
        if (cColonia != null)   cColonia = URLEncoder.encode(cColonia, "UTF-8");
        if (cDomicilio != null)   cDomicilio = URLEncoder.encode(cDomicilio, "UTF-8");
        if (cCP != null)   cCP = URLEncoder.encode(cCP, "UTF-8");
        if (cTelefono != null)   cTelefono = URLEncoder.encode(cTelefono, "UTF-8");
        if (cEMail != null)   cEMail = URLEncoder.encode(cEMail, "UTF-8");

        iResultado = wsInsertaInteresado.insertaInteresado(Usuario,Contrasenia,cRFC,cCURP,cNombre,cAPPaterno,cAPMaterno,
                                                           iCvePais,iCveEntidadFed,iCveMunicipio,cColonia,cDomicilio,cCP,cTelefono,cEMail);
      }
      else {
        info("insertaInteresado no cuenta con cisWSURL.");
      }
    }
    catch (Exception ex) {
      warn("insertaInteresado", ex);
    }
    finally {
    }
    return iResultado;
  }

  }

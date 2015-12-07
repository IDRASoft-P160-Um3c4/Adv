package gob.sct.sipmm.dao.reporte;
import java.sql.*;
import java.util.*;

import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;


/**
 * <p>Title: TDHTPDecreto.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame
 * @author icaballero
 * @version 1.0
 */

public class TDHTPDecreto extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  public TDHTPDecreto(){
  }

  public Vector GenDecreto(String cQuery) throws Exception{
    Vector vResultado = new Vector();
    Vector vRegs = new Vector();
    Vector vRegsNav = new Vector();
    Vector vRegsLoc = new Vector();
    Vector vRegsIncRecinto = new Vector();
    Vector vDatTablas = new Vector();
    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TFechas Fecha = new TFechas("44");
    TLetterNumberFormat tLetterNumberFormat = new TLetterNumberFormat();
    String cDenPuerto = "",cNomMunicipio = "",cEntFedPuerto = "";
    String cDenTerminal="",cNomMunTerminal="",cEntFedTerminal="";
    String cNavegacion="";
    String cTipoLocOrden1Puerto="",cTipoLocOrden2Puerto="";
    String cLocValGradosLatPuerto="",cLocValMinutosLatPuerto="",cLocValSegundosLatPuerto="";
    String cLocValGradosLonPuerto="",cLocValMinutosLonPuerto="",cLocValSegundosLonPuerto="";
    String cTipoLocOrden1Terminal="",cTipoLocOrden2Terminal="";
    String cLocValGradosLatTerminal="",cLocValMinutosLatTerminal="",cLocValSegundosLatTerminal="";
    String cLocValGradosLonTerminal="",cLocValMinutosLonTerminal="",cLocValSegundosLonTerminal="";
    String cDenIniModificacion = "", cMunIniModificacion = "",
           cEntFedIniModificacion = "", cDenFinModificacion = "";
    String cDenPuertosOA="",cDenTerminalesOA="",cCapPuertoAdscrita="";
    String cDenPuertoDesh="",cMunPuertoDesh="",cEntFedPuertoDesh="",cDenPuerto1IncDesh="";
    String cDenTerminalDesh="",cMunTerminalDesh="",cEntFedTerminalDesh="",cDenPuerto2IncDesh="";
    int iContPtoIncRecinto=0,iContTermIncRecinto=0;

    String cQuery1 = "";


    TWord rep = new TWord();  //Reporte Principal.
    TWord rep1 = new TWord(); //Tabla de Puertos Habilitados.
    TWord rep2 = new TWord(); //Tabla de Terminales Habilitados.
    TWord rep3 = new TWord(); //Tabla de Puertos incorporados a Polígono.
    TWord rep4 = new TWord(); //Tabla de Terminales incorporados a Polígono.

    cQuery1 = "select HTPHabilitacion.iCveHabilitacion, " +
              "HTPPuertoTerminal.iMovHabilitacion, " +
              "HTPPuertoTerminal.iCveModHabilitacion, " +
              "HTPPuertoTerminal.iCveTipoPuerto, " +
              "HTPPuertoTerminal.cDenominacion, " +
              "HTPPuertoTerminal.iCvePais, " +
              "GRLPais.cNombre as cNomPais, " +
              "HTPPuertoTerminal.iCveEntidadFed, " +
              "GRLEntidadFed.cNombre as cNomEntidadFed, " +
              "HTPPuertoTerminal.iCveMunicipio, " +
              "GRLMunicipio.cNombre as cNomMunicipio, " +
              "HTPPuertoTerminal.iCvePuerto, " +
              "GRLPuerto.cDscPuerto, " +
              "GRLPuerto.iCvePais iCvePaisPuerto, " +
              "PaisPuerto.cNombre as cNomPaisPuerto, " +
              "GRLPuerto.iCveEntidadFed iCveEntidadFedPuerto, " +
              "EntidadPuerto.cNombre as cNomEntidadPuerto, " +
              "GRLPuerto.iCveMunicipio iCveMunicipioPuerto, " +
              "MunicipioPuerto.cNombre as cNomMunicipioPuerto, " +
              "HTPPuertoTerminal.iCveOficinaAdscrita, " +
              "GRLOficina.cDscOficina " +
              "from HTPHabilitacion " +
              "join HTPPuertoTerminal on HTPPuertoTerminal.iCveHabilitacion = HTPHabilitacion.iCveHabilitacion " +
              "join GRLPais on GRLPais.iCvePais = HTPPuertoTerminal.iCvePais " +
              "join GRLEntidadFed on GRLEntidadFed.iCvePais = HTPPuertoTerminal.iCvePais " +
              "and GRLEntidadFed.iCveEntidadFed = HTPPuertoTerminal.iCveEntidadFed " +
              "join GRLMunicipio on GRLMunicipio.iCvePais = HTPPuertoTerminal.iCvePais " +
              "and GRLMunicipio.iCveEntidadFed = HTPPuertoTerminal.iCveEntidadFed " +
              "and GRLMunicipio.iCveMunicipio = HTPPuertoTerminal.iCveMunicipio " +
              "join GRLPuerto on GRLPuerto.iCvePuerto = HTPPuertoTerminal.iCvePuerto " +
              "join GRLPais PaisPuerto on PaisPuerto.iCvePais = GRLPuerto.iCvePais " +
              "join GRLEntidadFed EntidadPuerto on EntidadPuerto.iCvePais = GRLPuerto.iCvePais " +
              "and EntidadPuerto.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
              "join GRLMunicipio MunicipioPuerto on MunicipioPuerto.iCvePais = GRLPuerto.iCvePais " +
              "and MunicipioPuerto.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
              "and MunicipioPuerto.iCveMunicipio = GRLPuerto.iCveMunicipio " +
              "join GRLOficina on GRLOficina.iCveOficina = HTPPuertoTerminal.iCveOficinaAdscrita " +
              "where HTPHabilitacion.iCveHabilitacion = " + cQuery + " " +
              "order by HTPPuertoTerminal.iCveModHabilitacion " ;

    try{
      vRegs = super.FindByGeneric("", cQuery1, dataSourceName);
    }catch(SQLException ex){
      System.out.print("" + ex.getMessage());
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      System.out.print("" + ex2.getMessage());
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    //Tipo de Navegación.
    cQuery1 = "select HTPHabilitacion.iCveHabilitacion, " +
              "HTPPuertoTerminal.iMovHabilitacion, " +
              "HTPNavPtoTerminal.iCveTipoNavega, " +
              "VEHTipoNavegacion.cDscTipoNavega " +
              "from HTPHabilitacion " +
              "join HTPPuertoTerminal on HTPPuertoTerminal.iCveHabilitacion = HTPHabilitacion.iCveHabilitacion " +
              "join HTPNavPtoTerminal on HTPNavPtoTerminal.iCveHabilitacion = HTPPuertoTerminal.iCveHabilitacion " +
              "and HTPNavPtoTerminal.iMovHabilitacion = HTPPuertoTerminal.iMovHabilitacion " +
              "join VEHTipoNavegacion on VEHTipoNavegacion.iCveTipoNavega = HTPNavPtoTerminal.iCveTipoNavega " +
              "where HTPHabilitacion.iCveHabilitacion = " + cQuery;

    try{
      vRegsNav = super.FindByGeneric("", cQuery1, dataSourceName);
    }catch(SQLException ex){
      System.out.print("" + ex.getMessage());
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      System.out.print("" + ex2.getMessage());
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    //Localizacion Geografica.
    cQuery1 = "select HTPHabilitacion.iCveHabilitacion, " +
              "HTPPuertoTerminal.iMovHabilitacion, " +
              "HTPLocGeoPtoTerm.iCveTipoLocalizacion, " +
              "HTPTipoLocalizacion.cDscTipoLocalizacion, " +
              "HTPLocGeoPtoTerm.cValorGrados, " +
              "HTPLocGeoPtoTerm.cValorMinutos, " +
              "HTPLocGeoPtoTerm.cValorSegundos, " +
              "HTPLocGeoPtoTerm.iOrden " +
              "from HTPHabilitacion " +
              "join HTPPuertoTerminal on HTPPuertoTerminal.iCveHabilitacion = HTPHabilitacion.iCveHabilitacion " +
              "join HTPLocGeoPtoTerm on HTPLocGeoPtoTerm.iCveHabilitacion = HTPHabilitacion.iCveHabilitacion " +
              "and HTPLocGeoPtoTerm.iMovHabilitacion = HTPPuertoTerminal.iMovHabilitacion " +
              "join HTPTipoLocalizacion on HTPTipoLocalizacion.iCveTipoLocalizacion = HTPLocGeoPtoTerm.iCveTipoLocalizacion " +
              "where HTPHabilitacion.iCveHabilitacion = " + cQuery + " " +
              "order by HTPLocGeoPtoTerm.iOrden " ;

    try{
      vRegsLoc = super.FindByGeneric("", cQuery1, dataSourceName);
    }catch(SQLException ex){
      System.out.print("" + ex.getMessage());
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      System.out.print("" + ex2.getMessage());
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    //Incorporacion a Recinto Portuario de Puerto.
    cQuery1 = "select HTPHabilitacion.iCveHabilitacion, " +
              "HTPPuertoTerminal.iMovHabilitacion, " +
              "HTPPuertoTerminal.iCvePuerto as iCvePuertoIni, " +
              "PuertoIni.cDscPuerto as cDscPuertoIni, " +
              "PuertoIni.iCvePais as iCvePaisPuertoIni, " +
              "PaisPuertoIni.cNombre as cNomPaisPuertoIni, " +
              "PuertoIni.iCveEntidadFed as iCveEntidadPuertoIni, " +
              "EntidadPuertoIni.cNombre as cNomEntidadFedPuertoIni, " +
              "PuertoIni.iCveMunicipio as iCveMunicipioPuertoIni, " +
              "MunicipioPuertoIni.cNombre as cNomMunicipioPuertoIni, " +

              "PuertoDes.iCvePuerto as iCvePuertoDes, " +
              "PuertoDes.cDscPuerto as cDscPuertoDes, " +
              "PuertoDes.iCvePais as iCvePaisPuertoDes, " +
              "PaisPuertoDes.cNombre as cNomPaisPuertoDes, " +
              "PuertoDes.iCveEntidadFed as iCveEntidadPuertoDes, " +
              "EntidadPuertoDes.cNombre as cNomEntidadFedPuertoDes, " +
              "PuertoDes.iCveMunicipio as iCveMunicipioPuertoDes, " +
              "MunicipioPuertoDes.cNombre as cNomMunicipioPuertoDes " +

              "from HTPHabilitacion " +
              "join HTPPuertoTerminal on HTPPuertoTerminal.iCveHabilitacion = HTPHabilitacion.iCveHabilitacion " +

              "join GRLPuerto PuertoIni on PuertoIni.iCvePuerto = HTPPuertoTerminal.iCvePuerto " +
              "join GRLPais PaisPuertoIni on PaisPuertoIni.iCvePais = PuertoIni.iCvePais " +
              "join GRLEntidadFed EntidadPuertoIni on EntidadPuertoIni.iCvePais = PuertoIni.iCvePais " +
              "and EntidadPuertoIni.iCveEntidadFed = PuertoIni.iCveEntidadFed " +
              "join GRLMunicipio MunicipioPuertoIni on MunicipioPuertoIni.iCvePais = PuertoIni.iCvePais " +
              "and MunicipioPuertoIni.iCveEntidadFed = PuertoIni.iCveEntidadFed " +
              "and MunicipioPuertoIni.iCveMunicipio = PuertoIni.iCveMunicipio " +

              "join HTPIncorporaPuerto on HTPIncorporaPuerto.iCveHabilitacion = HTPHabilitacion.iCveHabilitacion " +
              "and  HTPIncorporaPuerto.iMovHabilitacion = HTPPuertoTerminal.iMovHabilitacion " +
              "join GRLPuerto PuertoDes on PuertoDes.iCvePuerto = HTPIncorporaPuerto.iCvePuerto " +
              "join GRLPais PaisPuertoDes on PaisPuertoDes.iCvePais = PuertoDes.iCvePais " +
              "join GRLEntidadFed EntidadPuertoDes on EntidadPuertoDes.iCvePais = PuertoDes.iCvePais " +
              "and EntidadPuertoDes.iCveEntidadFed = PuertoDes.iCveEntidadFed " +
              "join GRLMunicipio MunicipioPuertoDes on MunicipioPuertoDes.iCvePais = PuertoDes.iCvePais " +
              "and MunicipioPuertoDes.iCveEntidadFed = PuertoDes.iCveEntidadFed " +
              "and MunicipioPuertoDes.iCveMunicipio = PuertoDes.iCveMunicipio " +
              "where HTPHabilitacion.iCveHabilitacion = " + cQuery + " " +
              "";

    try{
      vRegsIncRecinto = super.FindByGeneric("", cQuery1, dataSourceName);
    }catch(SQLException ex){
      System.out.print("" + ex.getMessage());
      ex.printStackTrace();
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      System.out.print("" + ex2.getMessage());
      ex2.printStackTrace();
      cMensaje += ex2.getMessage();
    }

    rep.iniciaReporte(); //Reporte Principal.
    rep1.iniciaReporte(); //Reporte para la Tabla de Puertos.
    rep2.iniciaReporte(); //Reporte para la Tabla de Terminales.
    rep3.iniciaReporte(); //Reporte para la Tabla de Terminales.
    rep4.iniciaReporte(); //Reporte para la Tabla de Terminales.
    if (vRegs.size() > 0){
      for(int i=0,iContPuertos=0,iContTerminales=0;i<vRegs.size();i++){
        TVDinRep vDatos = (TVDinRep) vRegs.get(i);
        int iCveHabilitacion = Integer.parseInt(vDatos.get("iCveHabilitacion").toString());
        int iMovHabilitacion = Integer.parseInt(vDatos.get("iMovHabilitacion").toString());

        //Puertos
        if (vDatos.getInt("iCveTipoPuerto") == 1){

            //Navegacion de los Puertos
          if(!vRegsNav.isEmpty()){
            //cNavegacion = "";
            for(int j = 0;j < vRegsNav.size();j++){
              TVDinRep vDatosNav = (TVDinRep) vRegsNav.get(j);
              if(vDatosNav.getInt("iCveHabilitacion") ==
                 vDatos.getInt("iCveHabilitacion") &&
                 vDatosNav.getInt("iMovHabilitacion") ==
                 vDatos.getInt("iMovHabilitacion")){
                if(cNavegacion != "")
                  cNavegacion = cNavegacion + " y " +
                      vDatosNav.getString("cDscTipoNavega");
                else
                  cNavegacion = "" + vDatosNav.getString("cDscTipoNavega");
              }
            }
          }

          //Localizacion Geografica de los Puertos
          if(!vRegsLoc.isEmpty()){
            for(int j = 0;j < vRegsLoc.size();j++){
              TVDinRep vDatosLoc = (TVDinRep) vRegsLoc.get(j);
              if(vDatosLoc.getInt("iCveHabilitacion") == iCveHabilitacion &&
                 vDatosLoc.getInt("iMovHabilitacion") == iMovHabilitacion){
                //Valores para la Latitud.
                if(vDatosLoc.getInt("iOrden") == 1){
                  cLocValGradosLatPuerto = vDatosLoc.getString("cValorGrados");
                  cLocValMinutosLatPuerto = vDatosLoc.getString("cValorMinutos");
                  cLocValSegundosLatPuerto=vDatosLoc.getString("cValorSegundos");
                  cTipoLocOrden1Puerto=vDatosLoc.getString("cDscTipoLocalizacion");
                }
                //Valores para la Longitud.
                if(vDatosLoc.getInt("iOrden") == 2){
                  cLocValGradosLonPuerto = vDatosLoc.getString("cValorGrados");
                  cLocValMinutosLonPuerto = vDatosLoc.getString("cValorMinutos");
                  cLocValSegundosLonPuerto=vDatosLoc.getString("cValorSegundos");
                  cTipoLocOrden2Puerto=vDatosLoc.getString("cDscTipoLocalizacion");
                }
              }
            }
          }

          //rep.comAgregaRenglonTabla(3);

          //Modalidad de Modificacion.
          if (vDatos.getInt("iCveModHabilitacion") == 1){
            cDenIniModificacion = vDatos.getString("cDscPuerto");
            cMunIniModificacion = vDatos.getString("cNomMunicipioPuerto");
            cEntFedIniModificacion = vDatos.getString("cNomEntidadPuerto");
            cDenFinModificacion = vDatos.getString("cDenominacion");



          }

          //Modalidad de Habilitacion.
          if(vDatos.getInt("iCveModHabilitacion") == 2){
            if(cDenPuerto != "")
              cDenPuerto = cDenPuerto + "," + vDatos.getString("cDenominacion");
            else
              cDenPuerto = "" + vDatos.getString("cDenominacion");

            if(cNomMunicipio != "")
              cNomMunicipio = cNomMunicipio + "," +
                  vDatos.getString("cNomMunicipio");
            else
              cNomMunicipio = "" + vDatos.getString("cNomMunicipio");

            if(cEntFedPuerto != "")
              cEntFedPuerto = cEntFedPuerto + "," +
                  vDatos.getString("cNomEntidadFed");
            else
              cEntFedPuerto = "" + vDatos.getString("cNomEntidadFed");
            iContPuertos++;
            if(iContPuertos == 1){
              rep1.comEligeTabla("cPuertoMarcador");
              rep1.comDespliegaCelda(cTipoLocOrden1Puerto.toLowerCase());
              rep1.comDespliegaCelda(cTipoLocOrden2Puerto.toLowerCase());
            } //else
              //rep1.comAgregaRenglonTabla(1);
            rep1.comDespliegaCelda("");
            rep1.comDespliegaCelda("PUERTO");
            rep1.comDespliegaCelda(vDatos.getString("cDenominacion"));
            rep1.comDespliegaCelda(cNavegacion);
            rep1.comDespliegaCeldaApostrofe(cLocValGradosLatPuerto   + " ° " +
                                            cLocValMinutosLatPuerto  + " ' " +
                                            cLocValSegundosLatPuerto + " '' ");
            rep1.comDespliegaCeldaApostrofe(cLocValGradosLonPuerto   + " ° " +
                                            cLocValMinutosLonPuerto  + " ' " +
                                            cLocValSegundosLonPuerto + " '' ");
          }
        }

        //Terminales
        if (vDatos.getInt("iCveTipoPuerto") == 2){

          //Navegacion de las Terminales.
          if (!vRegsNav.isEmpty()){
            cNavegacion = "";
            for(int j=0;j<vRegsNav.size();j++){
              TVDinRep vDatosNav = (TVDinRep) vRegsNav.get(j);
              if (vDatosNav.getInt("iCveHabilitacion") == vDatos.getInt("iCveHabilitacion") &&
                  vDatosNav.getInt("iMovHabilitacion") == vDatos.getInt("iMovHabilitacion")){
                if(cNavegacion != "")
                  cNavegacion = cNavegacion + " y " + vDatosNav.getString("cDscTipoNavega");
                else
                  cNavegacion = "" + vDatosNav.getString("cDscTipoNavega");
              }
            }
          }

          //Localización Geografica de las Terminales.
          if (!vRegsLoc.isEmpty()){
             cLocValGradosLatTerminal="";cLocValMinutosLatTerminal="";cLocValSegundosLatTerminal="";
             cLocValGradosLonTerminal="";cLocValMinutosLonTerminal="";cLocValSegundosLonTerminal="";
             for(int j=0;j<vRegsLoc.size();j++){
               TVDinRep vDatosLoc = (TVDinRep) vRegsLoc.get(j);
               if (vDatosLoc.getInt("iCveHabilitacion") == vDatos.getInt("iCveHabilitacion") &&
                   vDatosLoc.getInt("iMovHabilitacion") == vDatos.getInt("iMovHabilitacion")){
                 //Valores para la Latitud.
                 if(vDatosLoc.getInt("iOrden") == 1){
                   cLocValGradosLatTerminal = vDatosLoc.getString("cValorGrados");
                   cLocValMinutosLatTerminal = vDatosLoc.getString("cValorMinutos");
                   cLocValSegundosLatTerminal = vDatosLoc.getString("cValorSegundos");
                   cTipoLocOrden1Terminal=vDatosLoc.getString("cDscTipoLocalizacion");
                 }
                 //Valores para la Longitud.
                 if(vDatosLoc.getInt("iOrden") == 2){
                   cLocValGradosLonTerminal = vDatosLoc.getString("cValorGrados");
                   cLocValMinutosLonTerminal = vDatosLoc.getString("cValorMinutos");
                   cLocValSegundosLonTerminal=vDatosLoc.getString("cValorSegundos");
                  cTipoLocOrden2Terminal=vDatosLoc.getString("cDscTipoLocalizacion");
                 }
               }
             }
          }

          //rep.comAgregaRenglonTabla(3);
          if(vDatos.getInt("iCveModHabilitacion") == 3){

            if (cDenTerminal != "")
              cDenTerminal = cDenTerminal + "," + vDatos.getString("cDenominacion");
            else
              cDenTerminal = "" + vDatos.getString("cDenominacion").toLowerCase();

            if (cNomMunTerminal != "")
               cNomMunTerminal = cNomMunTerminal + "," + vDatos.getString("cNomMunicipio");
            else
               cNomMunTerminal = "" + vDatos.getString("cNomMunicipio");

            if (cEntFedTerminal != "")
              cEntFedTerminal = cEntFedTerminal + "," + vDatos.getString("cNomEntidadFed");
            else
              cEntFedTerminal = "" + vDatos.getString("cNomEntidadFed");

            iContTerminales++;
            if (iContTerminales==1){
              rep2.comEligeTabla("cTerminalMarcador");
              rep2.comDespliegaCelda(cTipoLocOrden1Terminal.toLowerCase());
              rep2.comDespliegaCelda(cTipoLocOrden2Terminal.toLowerCase());
            } //else
              //rep2.comAgregaRenglonTabla(1);
            rep2.comDespliegaCelda("");
            rep2.comDespliegaCelda("TERMINAL");
            rep2.comDespliegaCelda(vDatos.getString("cDenominacion"));
            rep2.comDespliegaCelda(cNavegacion);
            rep2.comDespliegaCeldaApostrofe(cLocValGradosLatTerminal   + " ° " +
                                            cLocValMinutosLatTerminal  + " ' " +
                                            cLocValSegundosLatTerminal + " '' ");
            rep2.comDespliegaCeldaApostrofe(cLocValGradosLonTerminal   + " ° " +
                                            cLocValMinutosLonTerminal  + " ' " +
                                            cLocValSegundosLonTerminal + " '' ");

          }
        }
        cCapPuertoAdscrita = vDatos.getString("cDscOficina").toLowerCase();

        //Incorporacion a Pólígono de Recinto Portuario
        if(vDatos.getInt("iCveModHabilitacion") == 4){
          if (!vRegsIncRecinto.isEmpty()){
            if (vDatos.getInt("iCveTipoPuerto") == 1){
              for(int j=0;j<vRegsIncRecinto.size();j++){
                TVDinRep vDatIncRecinto = (TVDinRep) vRegsIncRecinto.get(j);
                if(vDatIncRecinto.getInt("iCveHabilitacion")==iCveHabilitacion &&
                   vDatIncRecinto.getInt("iMovHabilitacion")==iMovHabilitacion){
                  iContPtoIncRecinto++;
                  if (iContPtoIncRecinto==1){
                    rep3.comEligeTabla("cPtoIncRecintoMarcador");
                  }
                  rep3.comDespliegaCelda("");
                  rep3.comDespliegaCelda(vDatIncRecinto.getString("cDscPuertoIni"));
                  rep3.comDespliegaCelda(vDatIncRecinto.getString("cDscPuertoDes"));
                  cDenPuertoDesh= cDenPuertoDesh + (cDenPuertoDesh != ""?",":"") +
                                  vDatIncRecinto.getString("cDscPuertoIni");
                  cMunPuertoDesh= cMunPuertoDesh + (cMunPuertoDesh != ""?",":"") +
                                  vDatIncRecinto.getString("cNomMunicipioPuertoIni");
                  cEntFedPuertoDesh = cEntFedPuertoDesh + (cEntFedPuertoDesh != ""?",":"") +
                                      vDatIncRecinto.getString("cNomEntidadFedPuertoIni");
                  cDenPuerto1IncDesh = cDenPuerto1IncDesh + (cDenPuerto1IncDesh != ""?",":"") +
                                       vDatIncRecinto.getString("cDscPuertoDes");
                }
              }
            }

            if (vDatos.getInt("iCveTipoPuerto") == 2){
              for(int j=0;j<vRegsIncRecinto.size();j++){
                TVDinRep vDatIncRecinto = (TVDinRep) vRegsIncRecinto.get(j);
                if(vDatIncRecinto.getInt("iCveHabilitacion")==iCveHabilitacion &&
                   vDatIncRecinto.getInt("iMovHabilitacion")==iMovHabilitacion){
                  iContTermIncRecinto++;
                  if (iContTermIncRecinto==1){
                    rep4.comEligeTabla("cTermIncRecintoMarcador");
                  }
                  rep4.comDespliegaCelda("");
                  rep4.comDespliegaCelda(vDatIncRecinto.getString("cDscPuertoIni"));
                  rep4.comDespliegaCelda(vDatIncRecinto.getString("cDscPuertoDes"));

                  cDenTerminalDesh= cDenTerminalDesh + (cDenTerminalDesh != ""?",":"") +
                                  vDatIncRecinto.getString("cDscPuertoIni");
                  cMunTerminalDesh= cMunTerminalDesh + (cMunTerminalDesh != ""?",":"") +
                                  vDatIncRecinto.getString("cNomMunicipioPuertoIni");
                  cEntFedTerminalDesh = cEntFedTerminalDesh + (cEntFedTerminalDesh != ""?",":"") +
                                      vDatIncRecinto.getString("cNomEntidadFedPuertoIni");
                  cDenPuerto2IncDesh = cDenPuerto2IncDesh + (cDenPuerto2IncDesh != ""?",":"") +
                                       vDatIncRecinto.getString("cDscPuertoDes");
                }
              }
            }
          }
        }
      }

      rep.comRemplaza("[cDenPuerto]",cDenPuerto.toLowerCase());
      rep.comRemplaza("[cMunPuerto]",cNomMunicipio.toLowerCase());
      rep.comRemplaza("[cEntFedPuerto]",cEntFedPuerto.toLowerCase());

      rep.comRemplaza("[cDenTerminal]",cDenTerminal.toLowerCase());
      rep.comRemplaza("[cMunTerminal]",cNomMunTerminal.toLowerCase());
      rep.comRemplaza("[cEntFedTerminal]",cEntFedTerminal.toLowerCase());

      rep.comRemplaza("[cDenIniModificacion]",cDenIniModificacion.toLowerCase());
      rep.comRemplaza("[cMunIniModificacion]",cMunIniModificacion.toLowerCase());
      rep.comRemplaza("[cEntFedIniModificacion]",cEntFedIniModificacion.toLowerCase());
      rep.comRemplaza("[cDenFinModificacion]",cDenFinModificacion.toLowerCase());

      rep.comRemplaza("[cDenPuertosOA]",cDenPuerto.toLowerCase());
      rep.comRemplaza("[cDenTerminalesOA]",cDenTerminal.toLowerCase());
      rep.comRemplaza("[cCapPuertoAdscrita]",cCapPuertoAdscrita);

      rep.comRemplaza("[cDenPuertoDesh]",cDenPuertoDesh.toLowerCase());
      rep.comRemplaza("[cMunPuertoDesh]",cMunPuertoDesh.toLowerCase());
      rep.comRemplaza("[cEntFedPuertoDesh]",cEntFedPuertoDesh.toLowerCase());
      rep.comRemplaza("[cDenPuerto1IncDesh]",cDenPuerto1IncDesh.toLowerCase());

      rep.comRemplaza("[cDenTerminalDesh]",cDenTerminalDesh.toLowerCase());
      rep.comRemplaza("[cMunTerminalDesh]",cMunTerminalDesh.toLowerCase());
      rep.comRemplaza("[cEntFedTerminalDesh]",cEntFedTerminalDesh.toLowerCase());
      rep.comRemplaza("[cDenPuerto2IncDesh]",cDenPuerto2IncDesh.toLowerCase());

      rep.comRemplaza("[dtEmision]",
                      "" + tLetterNumberFormat.getCantidad((long) Fecha.getIntDay(Fecha.TodaySQL())) + " de " +
                      "" + Fecha.getMonthName(Fecha.TodaySQL()) + " del año " +
                      "" + tLetterNumberFormat.getCantidad((long) Fecha.getIntYear(Fecha.TodaySQL())) + ".");

    } else {
      System.out.print("No Encontro Datos");
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    Vector vTabPuertos = rep1.getDatosTablas();
    Vector vTabTerminales = rep2.getDatosTablas();
    Vector vTabPtoIncRecinto = rep3.getDatosTablas();
    Vector vTabTermIncRecinto = rep4.getDatosTablas();
    if (!vTabPuertos.isEmpty())
      for(int i=0;i<vTabPuertos.size();i++)
        vDatTablas.add(vTabPuertos.get(i));
    if (!vTabTerminales.isEmpty())
      for(int i=0;i<vTabTerminales.size();i++)
        vDatTablas.add(vTabTerminales.get(i));
    if (!vTabPtoIncRecinto.isEmpty())
      for(int i=0;i<vTabPtoIncRecinto.size();i++)
        vDatTablas.add(vTabPtoIncRecinto.get(i));
    if (!vTabTermIncRecinto.isEmpty())
      for(int i=0;i<vTabTermIncRecinto.size();i++)
        vDatTablas.add(vTabTermIncRecinto.get(i));

    vResultado.add(0,rep.getEtiquetas(true));
    vResultado.add(1,vDatTablas);
    return vResultado;
  }
  
  public Vector generaOpinionIntObras(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

		TWord rep = new TWord();
		int iCveOficinaDest=0,iCveDeptoDest=0;

		TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
		dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cQuery);
		dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());
		iCveOficinaDest = dObtenDatosOpiniones.dDatosOficPer.getiCveOficina();
		iCveDeptoDest = dObtenDatosOpiniones.dDatosOficPer.getiCveDepto();

		try{
			dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
		} catch (Exception e){
			e.printStackTrace();
			cMensaje = e.getMessage();
		}

		// Elaboración del Reporte.
		rep.iniciaReporte();

		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		return new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				iCveOficinaDest,iCveDeptoDest,
				0,0,0,
				"","",
				"", "",
				true,"cCuerpo",new Vector(),
				true, new Vector(),
				rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

	}

public Vector generaOpinionExtDGAJ(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){

	TWord rep = new TWord();
	
	TDObtenDatosOpiniones dObtenDatosOpiniones = new TDObtenDatosOpiniones();
	dObtenDatosOpiniones.dDatosOpnTra.setFiltrosOpn(cQuery);
	dObtenDatosOpiniones.dDatosOficPer.setFiltroOpnEnt(dObtenDatosOpiniones.dDatosOpnTra.getiCveOpinionEntidad());
	
	String cDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getOpnDirigidoA();
	String cPuestoDirigidoA = dObtenDatosOpiniones.dDatosOpnTra.getPtoOpn();

	try{
		dObtenDatosOpiniones.dDatosFolioOpn.setFolio(dObtenDatosOpiniones.dDatosOpnTra.getiCveSegtoEntidad(),cNumFolio);
	} catch (Exception e){
		e.printStackTrace();
		cMensaje = e.getMessage();
	}

	// Elaboración del Reporte.
	rep.iniciaReporte();
	rep.comRemplaza("[cNomDestino]", cDirigidoA);
	rep.comRemplaza("[cPtoDestino]", cPuestoDirigidoA);

	// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
	return new TDGeneral().generaOficioWord(cNumFolio,
			Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
			0,0,
			0,0,0,
			"","",
			"", "",
			true,"cCuerpo",new Vector(),
			true, new Vector(),
			rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

}
}

package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.excepciones.*;
import java.sql.SQLException;
import java.util.Vector;

/**
 * <p>Title: TDHTPDoctos.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Angel Zamora Portugal
 * @version 1.0
 */

public class TDHTPDoctos extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TDOficio dOficio = new TDOficio();

  public TDHTPDoctos(){
  }

public StringBuffer GenOpinionDecreto(String cQuery, String cFolio, String cCveOfic, String cCveDepto) throws Exception{ // Select
    Vector vRegs = new Vector();
    Vector vRegsPtoTerminal = new Vector();

//System.out.print("GenOpiniones 1");
     TWord rep = new TWord();
     rep.iniciaReporte();

     try{
       vRegs = super.FindByGeneric("", cQuery, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
       cMensaje += ex2.getMessage();
     }
//System.out.print("GenOpiniones 2");
     if (vRegs.size() > 0){
          TVDinRep vDatos = (TVDinRep) vRegs.get(0);
          //System.out.print("GenOpiniones 3");
          int iCveHabilitacion = Integer.parseInt(vDatos.get("iCveHabilitacion").toString());
          int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());
          int lEsContestacion = Integer.parseInt(vDatos.get("lEsContestacion").toString());

          // Obten los Puertos y Terminales
          try{
            vRegsPtoTerminal = super.FindByGeneric("",
            " select GRLTipoPuerto.iCveTipoPuerto, " +
            " GRLTipoPuerto.cDscTipoPuerto, " +
            " HTPPuertoTerminal.cDenominacion " +
            " from HTPPuertoTerminal " +
            " join GRLTipoPuerto on GRLTipoPuerto.iCveTipoPuerto = HTPPuertoTerminal.iCveTipoPuerto " +
            " where HTPPuertoTerminal.iCveHabilitacion = " + iCveHabilitacion,
            dataSourceName);

          }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
          }catch(Exception ex2){
            ex2.printStackTrace();
            cMensaje += ex2.getMessage();
          }

          rep.comRemplaza("[cNumeroOficio]",(cFolio.toString().compareTo("") != 0)?cFolio.toString():"");

          if(vRegsPtoTerminal.size()>0){
            int iCuantas = 0;
            int iCuentaPuerto = 0;
            int iCuentaTerminal = 0;
            int iCuentaMarina = 0;
            //System.out.print("GenOpiniones 4");
            String cDscTipoPuerto = new String("");
            String cDenominacion = new String("");

            String cTextoPuertos = new String("");
            String cNombrePuertos = new String("");
            String cTextoTerminales = new String("");
            String cNombreTerminales = new String("");
            String cTextoMarinas = new String("");
            String cNombreMarinas = new String("");

            for(int i=0;i<vRegsPtoTerminal.size();i++){
              TVDinRep vDatosPtoTerminal = (TVDinRep) vRegsPtoTerminal.get(i);

              cDscTipoPuerto = ((vDatosPtoTerminal.get("cDscTipoPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscTipoPuerto").toString():"");
              cDenominacion = ((vDatosPtoTerminal.get("cDenominacion").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDenominacion").toString():"");

              switch (Integer.parseInt(vDatosPtoTerminal.get("iCveTipoPuerto").toString())){
                //Tipo : Puerto
               case 1:{
                 iCuentaPuerto = iCuentaPuerto + 1;
                 if (iCuentaPuerto > 1)
                    cTextoPuertos = "a los " + cDscTipoPuerto + "S de ";
                 else
                    cTextoPuertos = "al " + cDscTipoPuerto + " de ";

                 cNombrePuertos = cNombrePuertos + cDenominacion + ", ";

                 break;
               }
               //Tipo : Terminal
               case 2:{
                 iCuentaTerminal = iCuentaTerminal + 1;
                 if (iCuentaTerminal > 1)
                    cTextoTerminales = "a las " + cDscTipoPuerto +
                                       "ES de uso público fuera de puerto correspondientes " +
                                       "a los artefactos navales denominados ";
                 else
                    cTextoTerminales = "a la " + cDscTipoPuerto + " de uso público fuera de puerto correspondiente " +
                                       "al artefacto naval denominado ";

                 cNombreTerminales = cNombreTerminales + cDenominacion + ", ";

                 break;
               }
               //Tipo : Marina
               case 3:{
                 iCuentaMarina = iCuentaMarina + 1;
                 if (iCuentaMarina > 1)
                    cTextoMarinas = "a las " + cDscTipoPuerto + "S de ";
                 else
                    cTextoMarinas = "a la " + cDscTipoPuerto + " de ";

                 cNombreMarinas = cNombreMarinas + cDenominacion + ", ";

                 break;
               }
              }
            }

            cTextoPuertos = cTextoPuertos + cNombrePuertos;
            cTextoTerminales = cTextoTerminales + cNombreTerminales;
            cTextoMarinas = cTextoMarinas + cNombreMarinas;
            //System.out.print("GenOpiniones 4.1 --" + cTextoMarinas + "--");
            if (cTextoPuertos.compareTo("") != 0){
               if (cTextoTerminales.compareTo("") != 0){
                  cTextoTerminales = "así como " + cTextoTerminales;
                  if (cTextoMarinas.compareTo("") != 0)
                     cTextoMarinas = "y " + cTextoMarinas;
               }else
                  if (cTextoMarinas.compareTo("") != 0)
                     cTextoMarinas = "así como " + cTextoMarinas;
            }else
               if (cTextoTerminales.compareTo("") != 0)
                  if (cTextoMarinas.compareTo("") != 0)
                     cTextoMarinas = "así como " + cTextoMarinas;

            //Valores de los Puertos y Terminales.
            rep.comRemplaza("[cNombrePuertos]",(cTextoPuertos.compareTo("") != 0)?cTextoPuertos:"");
            rep.comRemplaza("[cNombreTerminales]",(cTextoTerminales.compareTo("") != 0)?cTextoTerminales:"");
            rep.comRemplaza("[cNombreMarinas]",(cTextoMarinas.compareTo("") != 0)?cTextoMarinas:"");

            //System.out.print("GenOpiniones 5 -- " + cTextoPuertos + "--" + cTextoTerminales + "--" + cTextoMarinas);
          }

          if (lEsOpinionInterna == 1) { //Es una Opinión Interna
             if (lEsContestacion == 0)
                ;
          }else { //Opinión Externa
            if (lEsContestacion == 0)
                ;
          }

          //System.out.print("GenOpiniones 6");
     }else {
       //System.out.print("No Encontro Datos");
     }

     if(!cMensaje.equals(""))
       throw new Exception(cMensaje);
     //System.out.print("GenOpiniones 7");
     return rep.getEtiquetas(true);

}



  public StringBuffer GenOpiniones(String cQuery, String cFolio, String cCveOfic, String cCveDepto) throws Exception{ // Select
    Vector vRegs = new Vector();
    Vector vRegsPtoTerminal = new Vector();

//System.out.print("GenOpiniones 1");
     TWord rep = new TWord();
     rep.iniciaReporte();

     try{
       vRegs = super.FindByGeneric("", cQuery, dataSourceName);

     }catch(SQLException ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
       cMensaje += ex2.getMessage();
     }
//System.out.print("GenOpiniones 2");
     if (vRegs.size() > 0){
          TVDinRep vDatos = (TVDinRep) vRegs.get(0);
          //System.out.print("GenOpiniones 3");
          int iCveHabilitacion = Integer.parseInt(vDatos.get("iCveHabilitacion").toString());
          int lEsOpinionInterna = Integer.parseInt(vDatos.get("lEsOpinionInterna").toString());

          // Obten los Puertos y Terminales
          try{
            vRegsPtoTerminal = super.FindByGeneric("",
            " select GRLTipoPuerto.iCveTipoPuerto, " +
            " GRLTipoPuerto.cDscTipoPuerto, " +
            " HTPPuertoTerminal.cDenominacion " +
            " from HTPPuertoTerminal " +
            " join GRLTipoPuerto on GRLTipoPuerto.iCveTipoPuerto = HTPPuertoTerminal.iCveTipoPuerto " +
            " where HTPPuertoTerminal.iCveHabilitacion = " + iCveHabilitacion,
            dataSourceName);

          }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
          }catch(Exception ex2){
            ex2.printStackTrace();
            cMensaje += ex2.getMessage();
          }

          rep.comRemplaza("[cNumeroOficio]",(cFolio.toString().compareTo("") != 0)?cFolio.toString():"");

          if(vRegsPtoTerminal.size()>0){
            int iCuantas = 0;
            int iCuentaPuerto = 0;
            int iCuentaTerminal = 0;
            int iCuentaMarina = 0;
            //System.out.print("GenOpiniones 4");
            String cDscTipoPuerto = new String("");
            String cDenominacion = new String("");

            String cTextoPuertos = new String("");
            String cNombrePuertos = new String("");
            String cTextoTerminales = new String("");
            String cNombreTerminales = new String("");
            String cTextoMarinas = new String("");
            String cNombreMarinas = new String("");

            for(int i=0;i<vRegsPtoTerminal.size();i++){
              TVDinRep vDatosPtoTerminal = (TVDinRep) vRegsPtoTerminal.get(i);

              cDscTipoPuerto = ((vDatosPtoTerminal.get("cDscTipoPuerto").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDscTipoPuerto").toString():"");
              cDenominacion = ((vDatosPtoTerminal.get("cDenominacion").toString().compareTo("") != 0)?vDatosPtoTerminal.get("cDenominacion").toString():"");

              switch (Integer.parseInt(vDatosPtoTerminal.get("iCveTipoPuerto").toString())){
                //Tipo : Puerto
               case 1:{
                 iCuentaPuerto = iCuentaPuerto + 1;
                 if (iCuentaPuerto > 1)
                    cTextoPuertos = "a los " + cDscTipoPuerto + "S de ";
                 else
                    cTextoPuertos = "al " + cDscTipoPuerto + " de ";

                 cNombrePuertos = cNombrePuertos + cDenominacion + ", ";

                 break;
               }
               //Tipo : Terminal
               case 2:{
                 iCuentaTerminal = iCuentaTerminal + 1;
                 if (iCuentaTerminal > 1)
                    cTextoTerminales = "a las " + cDscTipoPuerto +
                                       "ES de uso público fuera de puerto correspondientes " +
                                       "a los artefactos navales denominados ";
                 else
                    cTextoTerminales = "a la " + cDscTipoPuerto + " de uso público fuera de puerto correspondiente " +
                                       "al artefacto naval denominado ";

                 cNombreTerminales = cNombreTerminales + cDenominacion + ", ";

                 break;
               }
               //Tipo : Marina
               case 3:{
                 iCuentaMarina = iCuentaMarina + 1;
                 if (iCuentaMarina > 1)
                    cTextoMarinas = "a las " + cDscTipoPuerto + "S de ";
                 else
                    cTextoMarinas = "a la " + cDscTipoPuerto + " de ";

                 cNombreMarinas = cNombreMarinas + cDenominacion + ", ";

                 break;
               }
              }
            }

            cTextoPuertos = cTextoPuertos + cNombrePuertos;
            cTextoTerminales = cTextoTerminales + cNombreTerminales;
            cTextoMarinas = cTextoMarinas + cNombreMarinas;
            //System.out.print("GenOpiniones 4.1 --" + cTextoMarinas + "--");
            if (cTextoPuertos.compareTo("") != 0){
               if (cTextoTerminales.compareTo("") != 0){
                  cTextoTerminales = "así como " + cTextoTerminales;
                  if (cTextoMarinas.compareTo("") != 0)
                     cTextoMarinas = "y " + cTextoMarinas;
               }else
                  if (cTextoMarinas.compareTo("") != 0)
                     cTextoMarinas = "así como " + cTextoMarinas;
            }else
               if (cTextoTerminales.compareTo("") != 0)
                  if (cTextoMarinas.compareTo("") != 0)
                     cTextoMarinas = "así como " + cTextoMarinas;

            //Valores de los Puertos y Terminales.
            rep.comRemplaza("[cNombrePuertos]",(cTextoPuertos.compareTo("") != 0)?cTextoPuertos:"");
            rep.comRemplaza("[cNombreTerminales]",(cTextoTerminales.compareTo("") != 0)?cTextoTerminales:"");
            rep.comRemplaza("[cNombreMarinas]",(cTextoMarinas.compareTo("") != 0)?cTextoMarinas:"");

            //System.out.print("GenOpiniones 5 -- " + cTextoPuertos + "--" + cTextoTerminales + "--" + cTextoMarinas);
          }

          if (lEsOpinionInterna == 1) { //Es una Opinión Interna


          } else {

          }

          //System.out.print("GenOpiniones 6");
     }else {
       //System.out.print("No Encontro Datos");
     }

     if(!cMensaje.equals(""))
       throw new Exception(cMensaje);
     //System.out.print("GenOpiniones 7");
     return rep.getEtiquetas(true);

  }

  public StringBuffer GenDoctoSimple(String cQuery) throws Exception{
    TWord rep = new TWord();

    rep.iniciaReporte();

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);

    return rep.getEtiquetas(true);

  }

}
